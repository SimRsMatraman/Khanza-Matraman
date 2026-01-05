/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package digital_klaim;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class TabelRegistrasiNew extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         String status = table.getValueAt(row, 1).toString();
         String statusBatal = table.getValueAt(row, 19).toString();
         String asalPasien = table.getValueAt(row, 25).toString();
         String caraPulang = table.getValueAt(row, 27).toString();
        if (row % 2 == 1){
            component.setBackground(new Color(247,255,243));
        }else{
            component.setBackground(new Color(255,255,255));
        } 
        if( statusBatal.equals("Batal")){component.setBackground(new Color(255, 128, 128));}
        else if( statusBatal.equals("Belum Datang")){component.setBackground(new Color(179, 179, 255));}
        else if( !caraPulang.equals("-")){component.setBackground(new Color(153, 255, 102));}
        else if( status.equals("xxx")){component.setBackground(new Color(255, 204, 0));}
//        else if( !asalPasien.equals("On-Site")){component.setBackground(new Color(179, 179, 255));}
        
        return component;
    }

}
