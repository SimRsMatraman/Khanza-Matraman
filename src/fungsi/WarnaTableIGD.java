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
public class WarnaTableIGD extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(240,248,254));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(240,248,254));
            component.setForeground(new Color(50,50,50));
        } 
        if(table.getValueAt(row,18).toString().equals("Sudah")){
            component.setBackground(new Color(213, 242, 214));
            component.setForeground(new Color(0,0,0));
        }else if(table.getValueAt(row,18).toString().equals("Batal")){
            component.setBackground(new Color(255, 255, 204));
            component.setForeground(new Color(0,0,0));
        }
        else if(table.getValueAt(row,18).toString().equals("Dirujuk")||table.getValueAt(row,18).toString().equals("Meninggal")||table.getValueAt(row,18).toString().equals("Pulang Paksa")){
            component.setBackground(new Color(211, 211, 211));
            component.setForeground(new Color(0,0,0));
        }else if(table.getValueAt(row,18).toString().equals("Dirawat")){
            component.setBackground(new Color(252, 192, 203));
            component.setForeground(new Color(0,0,0));
        }
        return component;
    }

}
