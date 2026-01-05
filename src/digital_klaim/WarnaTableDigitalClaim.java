package digital_klaim;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTableDigitalClaim extends DefaultTableCellRenderer {

    // Palet warna tema RSUD
    private static final Color BIRU_RSUD          = new Color(0, 102, 153);   // biru utama
    private static final Color TOSCA_RSUD         = new Color(0, 150, 136);   // tosca / hijau kebiruan
    private static final Color HIJAU_OK           = new Color(56, 142, 60);   // sukses
    private static final Color KUNING_PENDING     = new Color(255, 193, 7);   // pending
    private static final Color MERAH_ERROR        = new Color(211, 47, 47);   // gagal/masalah
    private static final Color ABU_NETRAL         = new Color(117, 117, 117); // netral
    private static final Color BIRU_INFO          = new Color(25, 118, 210);  // info / terkirim

    private static final Color BG_ROW_EVEN        = new Color(245, 249, 252); // zebra row
    private static final Color BG_ROW_ODD         = Color.WHITE;

    private static final Color BG_BOOLEAN_TRUE    = new Color(209, 242, 235); // hijau tosca lembut
    private static final Color FG_BOOLEAN_TRUE    = new Color(0, 105, 92);
    private static final Color BG_BOOLEAN_FALSE   = new Color(250, 250, 250);
    private static final Color FG_BOOLEAN_FALSE   = new Color(158, 158, 158);

    private static final Color BG_SELECTED        = new Color(0, 120, 215);   // biru seleksi
    private static final Color FG_SELECTED        = Color.WHITE;

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Reset dasar
        setFont(table.getFont());
        setOpaque(true);
        setHorizontalAlignment(LEFT);

        String text = (value == null) ? "" : value.toString().trim();
        int modelColumn = table.convertColumnIndexToModel(column);

        // ====== Warna dasar (zebra) ======
        Color bg = (row % 2 == 0) ? BG_ROW_EVEN : BG_ROW_ODD;
        Color fg = new Color(33, 37, 41); // hampir hitam

        // ====== Kalau baris terpilih ======
        if (isSelected) {
            setBackground(BG_SELECTED);
            setForeground(FG_SELECTED);
            setFont(getFont().deriveFont(Font.BOLD));
            return this;
        }

        // ====== Penanganan khusus kolom status ======
        switch (modelColumn) {
            case 7: // Status Administrasi
                applyStatusAdministrasiStyle(text, this);
                return this;
            case 8: // Status Koding
                applyStatusKodingStyle(text, this);
                return this;
            case 9: // Status Kirim Online
                applyStatusKirimOnlineStyle(text, this);
                return this;
            case 10: // Status Klaim
                applyStatusKlaimStyle(text, this);
                return this;
            default:
                // lanjut cek kolom boolean
                break;
        }

        // ====== Penanganan khusus kolom boolean (SEP, Resume, dll) ======
        // Rajal: 11 s/d 20
        // Ranap: 11 s/d 17
        if (modelColumn >= 11) {
            setHorizontalAlignment(SwingConstants.CENTER);
            if (value instanceof Boolean && (Boolean) value) {
                bg = BG_BOOLEAN_TRUE;
                fg = FG_BOOLEAN_TRUE;
                setFont(getFont().deriveFont(Font.BOLD));
            } else {
                bg = BG_BOOLEAN_FALSE;
                fg = FG_BOOLEAN_FALSE;
            }
        }

        // Kolom nomor rawat, no RM, no SEP ditebalkan sedikit
        if (modelColumn == 1 || modelColumn == 2 || modelColumn == 5) {
            setFont(getFont().deriveFont(Font.BOLD));
        }

        setBackground(bg);
        setForeground(fg);
        return this;
    }

    private void applyStatusAdministrasiStyle(String text, DefaultTableCellRenderer c) {
        String lower = text.toLowerCase();
        Color bg;
        Color fg;
        Font f = c.getFont();

        if (lower.contains("belum ada")) {
            bg = new Color(255, 235, 238); // merah muda
            fg = MERAH_ERROR;
            f = f.deriveFont(Font.BOLD);
        } else if (lower.contains("lengkap") || lower.contains("siap")) {
            bg = new Color(232, 245, 233); // hijau muda
            fg = HIJAU_OK;
            f = f.deriveFont(Font.BOLD);
        } else if (lower.contains("proses") || lower.contains("belum lengkap")) {
            bg = new Color(255, 249, 196); // kuning lembut
            fg = new Color(245, 124, 0);   // oranye
            f = f.deriveFont(Font.BOLD);
        } else {
            // default netral
            bg = BG_ROW_EVEN;
            fg = ABU_NETRAL;
        }

        c.setBackground(bg);
        c.setForeground(fg);
        c.setFont(f);
    }

    private void applyStatusKodingStyle(String text, DefaultTableCellRenderer c) {
        String lower = text.toLowerCase();
        Color bg;
        Color fg;
        Font f = c.getFont().deriveFont(Font.BOLD);

        if (lower.contains("sudah")) {
            bg = new Color(225, 245, 234); // hijau lembut
            fg = HIJAU_OK;
        } else {
            bg = new Color(255, 243, 224); // krem/oranye lembut
            fg = new Color(230, 81, 0);
        }

        c.setBackground(bg);
        c.setForeground(fg);
        c.setFont(f);
    }

    /**
     * Kolom 9 - Status Kirim Online
     * Nilai yang dipakai:
     * - "belum kirim"
     * - "sudah kirim"
     */
    private void applyStatusKirimOnlineStyle(String text, DefaultTableCellRenderer c) {
        String lower = text.toLowerCase();
        Color bg;
        Color fg;
        Font f = c.getFont().deriveFont(Font.BOLD);

        if (lower.contains("sudah kirim")) {
            // SUDAH KIRIM → biru info
            bg = new Color(227, 242, 253); // biru muda
            fg = BIRU_INFO;

        } else if (lower.contains("belum kirim")) {
            // BELUM KIRIM → abu netral
            bg = new Color(245, 245, 245);
            fg = ABU_NETRAL;

        } else {
            // default netral kalau ada teks lain
            bg = BG_ROW_EVEN;
            fg = ABU_NETRAL;
            f  = c.getFont(); // tidak perlu bold
        }

        c.setBackground(bg);
        c.setForeground(fg);
        c.setFont(f);
    }

    /**
     * Kolom 10 - Status Klaim
     * Nilai yang mungkin:
     * - "belum kirim"
     * - "pending"
     * - "tidak layak"
     * - "terkirim"
     * - "perbaiki"
     * - atau teks lain (fallback)
     */
    private void applyStatusKlaimStyle(String text, DefaultTableCellRenderer c) {
        String lower = text.toLowerCase();
        Color bg;
        Color fg;
        Font f = c.getFont().deriveFont(Font.BOLD);

        if (lower.contains("tidak layak")) {
            // Status: TIDAK LAYAK
            bg = new Color(255, 235, 238); // merah muda
            fg = MERAH_ERROR;

        } else if (lower.contains("perbaiki")) {
            // Status: PERBAIKI (perlu revisi)
            bg = new Color(255, 243, 224); // oranye lembut
            fg = new Color(230, 81, 0);

        } else if (lower.contains("belum kirim") || lower.equals("belum")) {
            // Status: BELUM KIRIM
            bg = new Color(245, 245, 245); // abu muda
            fg = ABU_NETRAL;

        } else if (lower.contains("terkirim") || lower.contains("sudah kirim")) {
            // Status: TERKIRIM
            bg = new Color(232, 245, 233); // hijau muda
            fg = HIJAU_OK;

        } else if (lower.contains("pending") || lower.contains("proses")) {
            // Status: PENDING / PROSES
            bg = new Color(255, 249, 196); // kuning lembut
            fg = KUNING_PENDING.darker();

        } else {
            // default netral kalau ada teks lain
            bg = BG_ROW_EVEN;
            fg = ABU_NETRAL;
            f  = c.getFont(); // nggak usah bold
        }

        c.setBackground(bg);
        c.setForeground(fg);
        c.setFont(f);
        c.setHorizontalAlignment(LEFT);
    }
}