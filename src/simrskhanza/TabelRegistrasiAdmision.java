/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simrskhanza;

import fungsi.*;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class TabelRegistrasiAdmision extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//         String status = table.getValueAt(row, 1).toString();
         String statusBatal = table.getValueAt(row, 19).toString();
         String statusKonfirmasi = table.getValueAt(row, 19).toString();
         String statusBayar = table.getValueAt(row, 23).toString();
//         String statusRujuk = table.getValueAt(row, 19).toString();
         String statusAskep = table.getValueAt(row, 24).toString();
         String statusSoap = table.getValueAt(row, 25).toString();
//         String statusLanjut = table.getValueAt(row, 21).toString();
//         String asalPasien = table.getValueAt(row, 25).toString();
//         String caraPulang = table.getValueAt(row, 27).toString();
//         String jamMasukPoli = table.getValueAt(row, 29).toString();
        if (row % 2 == 1){
            component.setBackground(new Color(255,246,244));
        }else{
            component.setBackground(new Color(255,255,255));
        } 
        if( statusBatal.equals("Batal")){component.setBackground(new Color(255, 128, 128));}
        else if( statusBayar.equals("Sudah Bayar")){component.setBackground(new Color(0, 255, 255));}
        else if( statusBatal.equals("Dirujuk")){component.setBackground(new Color(115, 255, 216));}
        else if( statusSoap.equals("Sudah")){component.setBackground(new Color(127, 255, 1));}
        else if( statusAskep.equals("Sudah")){component.setBackground(new Color(255, 194, 102));}
        else if( statusKonfirmasi.equals("Konfirmasi")){component.setBackground(new Color(225, 255, 0));}
//        else if( statusLanjut.equals("Ranap")){component.setBackground(new Color(255, 255, 0));}
//        else if( jamMasukPoli.equals("")){component.setBackground(new Color(255, 204, 0));}
//        else if( !caraPulang.equals("-")){component.setBackground(new Color(153, 255, 102));}
//        else if( status.equals("xxx")){component.setBackground(new Color(255, 204, 0));}
//        else if( !asalPasien.equals("On-Site")){component.setBackground(new Color(179, 179, 255));}
        
        return component;
    }

}
