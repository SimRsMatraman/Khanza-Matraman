/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTableEtter extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        }
        
        if(table.getValueAt(row,20).toString().equals("OBAT ITERASI ( 7/23 )")){
            component.setBackground(new Color(252,165,3));
            component.setForeground(new Color(0,0,0));
        }
        if (isSelected){
            component.setBackground(new Color(245, 222, 179));
            component.setForeground(new Color(220, 20, 60));
        }
        
        if (column == 8) {
            Object val8 = table.getValueAt(row, 8);
            String s8 = (val8 == null) ? "" : val8.toString().trim();
            if (!s8.isEmpty()) {
                setBackground(new Color(245, 222, 179));
                setForeground(new Color(0, 0, 0));
                return this;
            }
        }

        if (column == 9) {
            Object val9 = table.getValueAt(row, 9);
            String s9 = (val9 == null) ? "" : val9.toString().trim();
            if (!s9.isEmpty()) {
                setBackground(new Color(178, 202, 245));
                setForeground(new Color(0, 0, 0));
                return this;
            }
        }

        return component;
    }

}
