
package integration_idrg;

import fungsi.sekuel;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DlgPemeriksaanPasien extends JDialog {

    private String noRawat = "";
    private sekuel Sequel = new sekuel();

    private JTable tbDokter, tbLab, tbRadiologi;

    public DlgPemeriksaanPasien(Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Pemeriksaan Pasien");
        setSize(900, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    public void setNoRawat(String noRawat) {
        this.noRawat = noRawat;
    }

    private void initUI() {
        JTabbedPane tab = new JTabbedPane();

        tbDokter = new JTable(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Tanggal", "Dokter", "Keluhan", "Diagnosa", "Tensi", "Suhu", "Catatan"}
        ));
        tab.add("Dokter", new JScrollPane(tbDokter));

        tbLab = new JTable(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Tanggal", "Pemeriksaan", "Hasil", "Rujukan", "Satuan", "Keterangan"}
        ));
        tab.add("Laboratorium", new JScrollPane(tbLab));

        tbRadiologi = new JTable(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Tanggal", "Pemeriksaan", "Hasil", "Dokter"}
        ));
        tab.add("Radiologi", new JScrollPane(tbRadiologi));

        add(tab, BorderLayout.CENTER);
    }

    public void tampil() {
        if (noRawat == null || noRawat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Rawat belum ada");
            return;
        }
        loadDokter();
        loadLab();
        loadRadiologi();
    }

    private void loadDokter() {
        DefaultTableModel m = (DefaultTableModel) tbDokter.getModel();
        m.setRowCount(0);
        try {
            ResultSet rs = Sequel.query(
                "SELECT p.tgl_perawatan, p.jam_rawat, d.nm_dokter, " +
                "p.keluhan, p.diagnosa, p.tensi, p.suhu_tubuh, p.catatan " +
                "FROM pemeriksaan_ralan p " +
                "LEFT JOIN dokter d ON p.kd_dokter=d.kd_dokter " +
                "WHERE p.no_rawat=? " +
                "UNION ALL " +
                "SELECT p.tgl_perawatan, p.jam_rawat, d.nm_dokter, " +
                "p.keluhan, p.diagnosa, p.tensi, p.suhu_tubuh, p.catatan " +
                "FROM pemeriksaan_ranap p " +
                "LEFT JOIN dokter d ON p.kd_dokter=d.kd_dokter " +
                "WHERE p.no_rawat=? " +
                "ORDER BY 1,2",
                noRawat, noRawat
            );

            while (rs.next()) {
                m.addRow(new Object[]{
                    rs.getString(1) + " " + rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadLab() {
        DefaultTableModel m = (DefaultTableModel) tbLab.getModel();
        m.setRowCount(0);
        try {
            ResultSet rs = Sequel.query(
                "SELECT pl.tgl_periksa, jpl.nm_perawatan, dpl.nilai, " +
                "dpl.nilai_rujukan, dpl.satuan, dpl.keterangan " +
                "FROM periksa_lab pl " +
                "INNER JOIN detail_periksa_lab dpl ON pl.no_rawat=dpl.no_rawat " +
                "INNER JOIN jns_perawatan_lab jpl ON dpl.kd_jenis_prw=jpl.kd_jenis_prw " +
                "WHERE pl.no_rawat=?",
                noRawat
            );

            while (rs.next()) {
                m.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadRadiologi() {
        DefaultTableModel m = (DefaultTableModel) tbRadiologi.getModel();
        m.setRowCount(0);
        try {
            ResultSet rs = Sequel.query(
                "SELECT pr.tgl_periksa, jpr.nm_perawatan, pr.hasil, d.nm_dokter " +
                "FROM periksa_radiologi pr " +
                "INNER JOIN jns_perawatan_radiologi jpr ON pr.kd_jenis_prw=jpr.kd_jenis_prw " +
                "LEFT JOIN dokter d ON pr.kd_dokter=d.kd_dokter " +
                "WHERE pr.no_rawat=?",
                noRawat
            );

            while (rs.next()) {
                m.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
