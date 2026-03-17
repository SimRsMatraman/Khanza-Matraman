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
        
        if(table.getValueAt(row,12).toString().equals("Obat ini sudah diberikan dalam waktu kurang dari 1 bulan terakhir.")){
            component.setBackground(new Color(255, 15, 15));
            component.setForeground(new Color(255, 255, 255));
        }
        if (isSelected){
            component.setBackground(new Color(245, 222, 179));
            component.setForeground(new Color(220, 20, 60));
        }

        return component;
    }

}
