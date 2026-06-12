/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTableSEP extends DefaultTableCellRenderer {

    // Warna Ranap — merah muda pastel
    private static final Color COLOR_RANAP       = new Color(255, 214, 220); // pink muda
    private static final Color COLOR_RANAP_ALT   = new Color(255, 196, 205); // pink muda stripe

    // Warna Ralan — hijau pastel soft
    private static final Color COLOR_RALAN       = new Color(210, 242, 210); // hijau muda
    private static final Color COLOR_RALAN_ALT   = new Color(193, 232, 193); // hijau muda stripe

    // Warna selected — abu netral agar tidak bentrok dengan pink maupun hijau
    private static final Color COLOR_SELECTED    = new Color(170, 170, 200); // ungu abu netral

    // Index kolom "Jenis" di tabModeInternal (0-based = kolom ke-12, index 11)
    private static final int COL_JENIS = 11;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component component = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            component.setBackground(COLOR_SELECTED);
            component.setForeground(Color.WHITE);
            return component;
        }

        // Reset foreground ke default
        component.setForeground(Color.BLACK);

        // Baca nilai kolom Jenis dari model (konversi row view → model untuk aman saat sorting)
        int modelRow = table.convertRowIndexToModel(row);
        Object jenisObj = table.getModel().getValueAt(modelRow, COL_JENIS);
        String jenis = (jenisObj != null) ? jenisObj.toString().trim() : "";

        if (jenis.startsWith("1")) {
            // Ranap → Biru pastel, stripe genap/ganjil
            component.setBackground(row % 2 == 0 ? COLOR_RANAP : COLOR_RANAP_ALT);
        } else {
            // Ralan (atau kosong) → Hijau pastel, stripe genap/ganjil
            component.setBackground(row % 2 == 0 ? COLOR_RALAN : COLOR_RALAN_ALT);
        }

        return component;
    }
}
