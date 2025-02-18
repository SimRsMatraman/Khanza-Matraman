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
public class WarnaTableApt extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        } 
        if(table.getValueAt(row,0).toString().equals("0")){
            component.setBackground(new Color(255,0,0));
            component.setForeground(new Color(0,0,0));
        } 
        if(table.getSelectedRow()!= 0){
            try {
                if(Double.parseDouble(table.getValueAt(row,11).toString())>0){
                    if(Double.parseDouble(table.getValueAt(row,11).toString()) < Double.parseDouble(table.getValueAt(row,7).toString())){
                        component.setBackground(new Color(252,165,3));
                        component.setForeground(new Color(0,0,0));
                        table.setValueAt("Stok Asal Kurang Dari Min. Permintaan",row,12);
                    }
                }
            } catch (Exception e) {
                } 
        }
//        if(table.getSelectedRow()!= 0){
//            try {
//                if(Double.parseDouble(table.getValueAt(row,11).toString())>0){
//                    if(Double.parseDouble(table.getValueAt(row,11).toString()) < Double.parseDouble(table.getValueAt(row,7).toString())){
//                        component.setBackground(new Color(252,165,3));
//                        component.setForeground(new Color(0,0,0));
//                    }
//                }
//            } catch (Exception e) {
//            }
//        }
        if (isSelected){
            component.setBackground(new Color(245, 222, 179));
            component.setForeground(new Color(220, 20, 60));
        }

        return component;
    }

}
