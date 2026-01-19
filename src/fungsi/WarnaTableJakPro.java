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
public class WarnaTableJakPro extends DefaultTableCellRenderer {

    @Override
public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {

    Component c = super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column);

    try {
        String noResep = table.getValueAt(row, 3) == null ? "" : table.getValueAt(row, 3).toString();
        String noLab   = table.getValueAt(row, 4) == null ? "" : table.getValueAt(row, 4).toString();
        String noRad   = table.getValueAt(row, 5) == null ? "" : table.getValueAt(row, 5).toString();

        if (!noResep.equals("") || !noLab.equals("") || !noRad.equals("")) {
            c.setBackground(new java.awt.Color(255, 255, 204)); // kuning muda
        } else {
            c.setBackground(Color.WHITE);
        }

        if (isSelected) {
            c.setBackground(new java.awt.Color(153, 204, 255));
        }

    } catch (Exception e) {
        c.setBackground(Color.WHITE);
    }

    return c;
}
}

