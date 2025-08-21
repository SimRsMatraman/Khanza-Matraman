/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Owner
 */
public class WarnaTableFDC extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        } 
        Object valuetgl = table.getValueAt(row, 4);
        if (valuetgl != null && !value.toString().trim().equals("")) {
            try {
                // format sesuai dengan format tanggal di tabel, misalnya "yyyy-MM-dd"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate tanggal = LocalDate.parse(valuetgl.toString(), formatter);

                long selisihHari = ChronoUnit.DAYS.between(tanggal, LocalDate.now());

                if (selisihHari > 30) {
                    component.setBackground(new Color(255, 102, 102)); // merah muda
                    component.setForeground(Color.BLACK);
                } else {
                    component.setBackground(new Color(255,255,255)); // default
                    component.setForeground(Color.BLACK);
                }
            } catch (Exception e) {
                // jika parsing gagal, beri warna default
                component.setBackground(new Color(255,255,255));
                component.setForeground(Color.BLACK);
            }
        } else {
            // jika kosong
            component.setBackground(new Color(255,255,255));
            component.setForeground(Color.BLACK);
        }
        if (isSelected){
            component.setBackground(new Color(245, 222, 179));
            component.setForeground(new Color(220, 20, 60));
        }

        return component;
    }

}
