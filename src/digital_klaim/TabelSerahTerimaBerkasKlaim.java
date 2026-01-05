/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package digital_klaim;

import fungsi.*;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class TabelSerahTerimaBerkasKlaim extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         String status = table.getValueAt(row, 7).toString();
        if (row % 2 == 1){
            component.setBackground(new Color(247,255,243));
        }else{
            component.setBackground(new Color(255,255,255));
        } 
        if( status.equals("Lengkap")){component.setBackground(new Color(77, 255, 77));}
        if( status.equals("Belum Lengkap")){component.setBackground(new Color(255, 204, 204));}
        return component;
    }

}