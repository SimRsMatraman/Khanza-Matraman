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
public class WarnaTableJakPro extends DefaultTableCellRenderer {

@Override
public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {

    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    // Reset default dulu (WAJIB biar warna tidak nempel)
    c.setBackground(Color.WHITE);
    c.setForeground(Color.BLACK);

    // Kalau row sedang dipilih → jangan override warna
    if (isSelected) {
        return c;
    }

    // ================== WARNA STATUS JOURNEY ==================
    if (column == 6) {
        String status = value == null ? "" : value.toString();

        switch (status) {
            case "Belum Checkin":
                c.setBackground(new Color(255, 204, 204)); // merah muda
                break;

            case "Checkin":
            case "Nurse Station":
            case "Poliklinik":
                c.setBackground(new Color(255, 255, 153)); // kuning
                break;

            case "Checkin Selesai":
            case "Nurse Station Selesai":
            case "Poliklinik Selesai":
                c.setBackground(new Color(204, 255, 204)); // hijau muda
                break;

            case "Permintaan Resep":
            case "Validasi Resep":
                c.setBackground(new Color(255, 230, 153)); // kuning tua
                break;

            case "Penyerahan Resep":
                c.setBackground(new Color(204, 255, 204));
                break;

            case "Hasil Lab":
            case "Hasil Radiologi":
                c.setBackground(new Color(204, 255, 255)); // biru muda
                break;

            case "Check Out":
                c.setBackground(new Color(153, 255, 153)); // hijau terang
                break;

            case "Batal":
                c.setBackground(Color.RED);
                c.setForeground(Color.WHITE);
                break;

            default:
                c.setBackground(Color.WHITE);
                break;
        }
    }

    return c;
}
}

