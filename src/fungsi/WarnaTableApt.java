package fungsi;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTableApt extends DefaultTableCellRenderer {
    private final JTextField nmgudangasal;
    private final JTextField kdgudangTujuan;
    private final JTextField nmgudangTujuan;
    private double parseAngka(String val) {
        if (val == null || val.trim().isEmpty()) return 0;
        val = val.replaceAll("[^0-9.,-]", "");
        val = val.replace(",", ".");
        try {
            return Double.parseDouble(val);
        } catch (Exception e) {
            return 0;
        }
    }

    public WarnaTableApt(JTextField nmgudangasal, JTextField kdgudangTujuan, JTextField nmgudangTujuan) {
        this.nmgudangasal = nmgudangasal;
        this.kdgudangTujuan = kdgudangTujuan;
        this.nmgudangTujuan = nmgudangTujuan;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row % 2 == 0) {
            component.setBackground(Color.WHITE);
        } else {
            component.setBackground(new Color(255, 248, 248));
        }
        component.setForeground(new Color(50, 50, 50));
        
        if (column == 10 || column == 13) {
            Object val10 = table.getValueAt(row, 10);
            boolean kosong = (val10 == null || val10.toString().trim().isEmpty());
            if (kosong) {
                if (row % 2 == 0) {
                    component.setBackground(new Color(230, 230, 230));
                } else {
                    component.setBackground(new Color(240, 240, 240));
                }
                component.setForeground(new Color(50, 50, 50));
            }
        }

        try {
            String stokAsalText = table.getValueAt(row, 11) == null ? "" : table.getValueAt(row, 11).toString().trim();
            String minPermintaanText  = table.getValueAt(row, 6)  == null ? "" : table.getValueAt(row, 6).toString().trim();
            String maxPermintaanText = table.getValueAt(row, 7) == null ? "" : table.getValueAt(row, 7).toString().trim();
            String stokPemintaText  = table.getValueAt(row, 9)  == null ? "" : table.getValueAt(row, 9).toString().trim();

            double stokAsal = parseAngka(stokAsalText);
            double minPermintaan = parseAngka(minPermintaanText);
            double maxPermintaan = parseAngka(maxPermintaanText);
            double stokPeminta = parseAngka(stokPemintaText);

            String namaGudangAsal = nmgudangasal.getText().trim();
            if (namaGudangAsal.isEmpty()) {
                namaGudangAsal = "Asal";
            }

            String namaGudangTujuan = nmgudangTujuan.getText().trim();
            if (namaGudangTujuan.isEmpty()) {
                namaGudangTujuan = "Peminta";
            }

            Object val10 = table.getValueAt(row, 10);
            if (val10 != null && !val10.toString().trim().isEmpty()) {
                double jml = parseAngka(val10.toString().trim());

                if (stokPeminta < minPermintaan) {
                    if (jml < minPermintaan) {
                        table.setValueAt("Auto Jml dikosongkan karena hasil tetap kurang dari min. permintaan (" + minPermintaanText + ")", row, 12);
                        if (column == 10 || column == 12) {
                            component.setBackground(new Color(255, 245, 157));
                            component.setForeground(Color.BLACK);
                        }
                    } else if (jml > stokAsal) {
                        table.setValueAt("Auto Permintaan lebih dari Stok " + namaGudangAsal + " (" + stokAsalText + ")", row, 12);
                        if (column == 10 || column == 12) {
                            component.setBackground(new Color(255, 220, 150));
                            component.setForeground(Color.BLACK);
                        }
                    } else if (jml > maxPermintaan) {
                        table.setValueAt("Auto Jml disesuaikan agar tidak melebihi maksimal permintaan (" + maxPermintaanText + ")", row, 12);
                        if (column == 10 || column == 12) {
                            component.setBackground(new Color(255, 200, 150));
                            component.setForeground(Color.BLACK);
                        }
                    } else {
                        table.setValueAt("Stok " + namaGudangTujuan + " (" + stokPemintaText + "), kurang dari minimal Jml permintaan (" + minPermintaanText + ")", row, 12);
                        if (column == 6 || column == 9 || column == 12) {
                            component.setBackground(new Color(255, 204, 153));
                            component.setForeground(Color.BLACK);
                        }
                        if (column == 10) {
                            component.setBackground(new Color(230,145,56));
                            component.setForeground(Color.BLACK);
                        }
                    }
                }
            }

            if (stokAsal < minPermintaan) {
                table.setValueAt("Stok " + namaGudangAsal + " (" + stokAsalText + "), kurang dari minimal permintaan (" + minPermintaanText + ")", row, 12);
                if (column == 6 || column == 11 || column == 12) {
                    component.setBackground(new Color(153, 0, 0));
                    component.setForeground(new Color(243, 246, 244));
                }
            }

            if (stokPeminta > maxPermintaan) {
            boolean validMax = (maxPermintaanText != null 
                                && !maxPermintaanText.trim().isEmpty()
                                && !maxPermintaanText.trim().equals("0")
                                && !maxPermintaanText.trim().equals("0.0"));

            if (validMax) {
                table.setValueAt("Stok " + namaGudangTujuan + " (" + stokPemintaText +
                                 "), lebih dari maksimal stok (" + maxPermintaanText + ")", row, 12);

                if (column == 7 || column == 9 || column == 12) {
                    component.setBackground(new Color(217, 234, 211));
                    component.setForeground(new Color(68, 68, 68));
                }
            } else {
                table.setValueAt("", row, 12);

                if (!isSelected) {
                    if (row % 2 == 1) {
                        component.setBackground(new Color(255, 244, 244));
                    } else {
                        component.setBackground(new Color(255, 255, 255));
                    }
                    component.setForeground(new Color(50, 50, 50));
                }
            }
        }

        } catch (Exception e) {
        }

        if (isSelected) {
            component.setBackground(new Color(111,168,220));
            component.setForeground(new Color(220, 20, 60));
        }

        return component;
    }
}
