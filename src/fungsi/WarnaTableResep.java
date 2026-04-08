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
public class WarnaTableResep extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        }
        
        if (!isSelected) {
            if (column  == 1) {
                component.setBackground(new Color(171, 161, 161));
            } else {
                component.setBackground(new Color(255, 255, 255));
            }
            component.setForeground(new Color(50, 50, 50));
        }
        
        if (!table.getValueAt(row, 13).toString().trim().isEmpty() &&
            !table.getValueAt(row, 14).toString().trim().isEmpty()) {
            try {
                double jml13 = Double.parseDouble(table.getValueAt(row, 13).toString());
                double jml14 = Double.parseDouble(table.getValueAt(row, 14).toString());

                double selisih = Math.abs(jml14 - jml13);
                if (selisih <= 7) { 
                    component.setBackground(new Color(255, 15, 15));
                    component.setForeground(new Color(255, 255, 255));
                }
            } catch (NumberFormatException e) {
            }
        }
        
        if (!table.getValueAt(row, 12).toString().trim().isEmpty() &&
            !table.getValueAt(row, 13).toString().trim().isEmpty()) {
            component.setBackground(new Color(255, 195, 15));
            component.setForeground(new Color(220, 20, 60));
        }

        if (isSelected){
            component.setBackground(new Color(245, 222, 179));
            component.setForeground(new Color(220, 20, 60));
        }
        
        if (column == 15) {
            try {
                    double jml13 = Double.parseDouble(table.getValueAt(row, 13).toString());
                    double jml14 = Double.parseDouble(table.getValueAt(row, 14).toString());

                    double selisih = Math.abs(jml14 - jml13);
                    if (selisih <= 7) { 
                        Object val15 = table.getValueAt(row, 15);
                        String s15 = (val15 == null) ? "" : val15.toString().trim();
                        if (!s15.isEmpty()) {
                            setBackground(new Color(255, 15, 15));
                            setForeground(new Color(50, 50, 50));
                            return this;
                        }
                    }
                } catch (NumberFormatException e) {
                }
        } 

        return component;
    }

}
