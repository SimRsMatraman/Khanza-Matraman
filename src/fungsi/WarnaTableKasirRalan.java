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
public class WarnaTableKasirRalan extends DefaultTableCellRenderer {
//    public int askep = 20;
//    public int soap = 21;
    public int sep = 4;
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(240,248,254));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(240,248,254));
            component.setForeground(new Color(50,50,50));
        } 
        if(table.getValueAt(row,11).toString().equals("Sudah")){
            component.setBackground(new Color(255, 255, 204));
            component.setForeground(new Color(0,0,0));
        }else if(table.getValueAt(row,11).toString().equals("Batal")){
            component.setBackground(new Color(143, 188, 144));
            component.setForeground(new Color(0,0,0));
        }else if(table.getValueAt(row,11).toString().equals("Dirujuk")||table.getValueAt(row,10).toString().equals("Meninggal")||table.getValueAt(row,10).toString().equals("Pulang Paksa")){
            component.setBackground(new Color(211, 211, 211));
            component.setForeground(new Color(0,0,0));
        }else if(table.getValueAt(row,11).toString().equals("Dirawat")){
            component.setBackground(new Color(252, 192, 203));
            component.setForeground(new Color(0,0,0));
        }
        //if(table.getValueAt(row,19).toString().equals("Assesment Ulang")){
          //  component.setBackground(new Color(240, 230, 140));
          //  component.setForeground(new Color(0,0,0));
        //}
        //if (table.getValueAt(row, askep).toString().equals("Belum")){
        //    component.setBackground(new Color(255, 192, 95));
        //    component.setForeground(new Color(0,0,0));
       // }
        //if (table.getValueAt(row, soap).toString().equals("Belum SOAP")){
         //   component.setBackground(new Color(128, 255, 0));
         //   component.setForeground(new Color(0,0,0));
       // }
//       if (table.getValueAt(row, sep).toString().equals("Belum Terbit")){
//           component.setBackground(new Color(245, 222, 179));
//            component.setBackground(new Color(128, 255, 0));
//            component.setForeground(new Color(0,0,0));
//            component.addNotify();
//        }
        if (isSelected){
            component.setBackground(new Color(245, 222, 179));
            component.setForeground(new Color(220, 20, 60));
            
        }
        
        return component;
    }

}
