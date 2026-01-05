package integration_idrg;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTableIDRGClaim extends DefaultTableCellRenderer {

    // === Zebra background (tetap lembut) ===
    private final Color COLOR_ZEBRA1            = Color.WHITE;
    private final Color COLOR_ZEBRA2            = new Color(243, 247, 251);  // #F3F7FB

    // === Warna lebih tajam tetapi tidak menusuk mata ===

    // Set Data Klaim – tosca lebih tegas
    private final Color COLOR_SET_DATA_KLAIM    = new Color(178, 223, 219);  // #B2DFDB

    // Final iDRG – biru lembut tegas
    private final Color COLOR_FINAL_IDRG        = new Color(144, 202, 249);  // #90CAF9

    // Final InaCBG – ungu kebiruan lebih hidup
    private final Color COLOR_FINAL_INACBG      = new Color(179, 157, 219);  // #B39DDB

    // Final Klaim – hijau soft tetapi memberi efek "success"
    private final Color COLOR_FINAL_KLAIM       = new Color(165, 214, 167);  // #A5D6A7

    // Berkas Individual – ungu pastel lebih intens
    private final Color COLOR_BERKAS_INDIV      = new Color(206, 147, 216);  // #CE93D8

    // Kirim Online – oranye lembut, mudah dibedakan
    private final Color COLOR_KIRIM_ONLINE      = new Color(255, 204, 128);  // #FFCC80

    // Row terpilih
    private final Color COLOR_SELECTED          = new Color(187, 222, 251);  // #BBDEFB

    // Teks
    private final Color COLOR_TEXT_DARK         = new Color(38, 50, 56); // #263238

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        // Default zebra
        Color bg = (row % 2 == 0) ? COLOR_ZEBRA1 : COLOR_ZEBRA2;
        Color fg = COLOR_TEXT_DARK;

        if (!isSelected) {
            switch (column) {
                case 10: // Set Data Klaim
                    if (getBool(table, row, column)) bg = COLOR_SET_DATA_KLAIM;
                    break;
                case 11: // Final IDRG
                    if (getBool(table, row, column)) bg = COLOR_FINAL_IDRG;
                    break;
                case 12: // Final InaCBG
                    if (getBool(table, row, column)) bg = COLOR_FINAL_INACBG;
                    break;
                case 13: // Final Klaim
                    if (getBool(table, row, column)) bg = COLOR_FINAL_KLAIM;
                    break;
                case 14: // Berkas Individual
                    if (getBool(table, row, column)) bg = COLOR_BERKAS_INDIV;
                    break;
                case 15: // Kirim Online
                    if (getBool(table, row, column)) bg = COLOR_KIRIM_ONLINE;
                    break;
            }
        } else {
            bg = COLOR_SELECTED;
            fg = Color.BLACK;
        }

        c.setBackground(bg);
        c.setForeground(fg);

        return c;
    }

    private boolean getBool(JTable table, int row, int col) {
        if (col < 0 || col >= table.getColumnCount()) return false;
        Object val = table.getValueAt(row, col);
        if (val == null) return false;

        if (val instanceof Boolean) return (Boolean) val;

        String s = val.toString().trim().toLowerCase();
        if (s.isEmpty() || s.equals("-")) return false;

        if (s.equals("true") || s.equals("1") || s.equals("ya") || s.equals("yes")) return true;
        if (s.equals("false") || s.equals("0") || s.equals("tidak")) return false;

        if (s.startsWith("sudah")) return true;
        if (s.startsWith("belum")) return false;

        // Nama file dianggap “sudah”
        return true;
    }
}