package digital_klaim;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class DlgUpdateStatusKlaim extends JDialog {

    private static final long serialVersionUID = 1L;

    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();

    private PreparedStatement ps;
    private ResultSet rs;

    private String noRawat = "";
    private String noSep   = "";

    public DlgUpdateStatusKlaim(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
    }

    /**
     * Dipanggil dari luar untuk set data pasien yang mau di-update
     */
    public void setData(String noRawat, String noSep) {
        this.noRawat = noRawat;
        this.noSep   = noSep;

        lblNoRawatValue.setText(noRawat);
        lblNoSepValue.setText(noSep);

        loadStatusDariDB();
    }

    // Helper flag sama seperti di DlgManagemenFileKlaim
    private boolean isTrueFlag(String value) {
        if (value == null) {
            return false;
        }
        String v = value.trim();
        return v.equalsIgnoreCase("true")
                || v.equals("1")
                || v.equalsIgnoreCase("ya")
                || v.equalsIgnoreCase("sudah")
                || v.equalsIgnoreCase("y");
    }

    /**
     * Baca kirim_online + status_klaim dari tt_status_eklaim lalu set ke form
     */
    private void loadStatusDariDB() {
        if (noSep == null || noSep.trim().equals("") || noSep.equals("-")) {
            // Ga ada SEP, ya sudah, ga usah load
            return;
        }

        try {
            ps = koneksi.prepareStatement(
                "select kirim_online, status_klaim from tt_status_eklaim where no_sep=? limit 1"
            );
            ps.setString(1, noSep);
            rs = ps.executeQuery();

            if (rs.next()) {
                String kirimOnline = rs.getString("kirim_online");
                String statusKlaimRaw = rs.getString("status_klaim");

                chkKirimOnline.setSelected(isTrueFlag(kirimOnline));

                String statusUI = "Belum Kirim";
                String s = (statusKlaimRaw == null ? "" : statusKlaimRaw.trim().toLowerCase());

                if (!isTrueFlag(kirimOnline)) {
                    // kalau belum kirim, status simpel saja
                    statusUI = "Belum Kirim";
                } else {
                    // sudah kirim
                    if (s.contains("gagal") || s.contains("reject") || s.contains("tolak")) {
                        statusUI = "Gagal Klaim";
                    } else if (s.contains("final") || s.contains("lunas") || s.contains("ok")) {
                        statusUI = "Final Klaim";
                    } else {
                        statusUI = "Pending Klaim";
                    }
                }

                cmbStatus.setSelectedItem(statusUI);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi loadStatusDariDB : " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    /**
     * Simpan perubahan ke tt_status_eklaim
     */
    private void simpanStatus() {
        if (noSep == null || noSep.trim().equals("") || noSep.equals("-")) {
            JOptionPane.showMessageDialog(this,
                    "No. SEP belum ada.\nTidak bisa update status klaim.",
                    "Perhatian", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String statusPilihan = (String) cmbStatus.getSelectedItem();
        boolean kirimOnlineBool = chkKirimOnline.isSelected();
        String kirimOnline = kirimOnlineBool ? "1" : "0";

        String statusKlaim = null; // boleh null untuk pending jika mau

        if ("Belum Kirim".equals(statusPilihan)) {
            // Belum kirim → kirim_online=0, status_klaim NULL
            kirimOnline = "0";
            statusKlaim = null;
        } else if ("Pending Klaim".equals(statusPilihan)) {
            kirimOnline = "1";
            // Bisa kosong, tapi biar jelas, isi "PENDING"
            statusKlaim = "PENDING";
        } else if ("Gagal Klaim".equals(statusPilihan)) {
            kirimOnline = "1";
            statusKlaim = "GAGAL: di-set manual";
        } else if ("Final Klaim".equals(statusPilihan)) {
            kirimOnline = "1";
            statusKlaim = "FINAL";
        }

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            // cek sudah ada record tt_status_eklaim utk no_sep ini?
            int ada = Sequel.cariInteger(
                    "select count(*) from tt_status_eklaim where no_sep='" + noSep + "'"
            );

            if (ada > 0) {
                // UPDATE
                String sql = "update tt_status_eklaim set kirim_online=?, status_klaim=? where no_sep=?";
                ps = koneksi.prepareStatement(sql);
                ps.setString(1, kirimOnline);
                if (statusKlaim == null) {
                    ps.setNull(2, Types.VARCHAR);
                } else {
                    ps.setString(2, statusKlaim);
                }
                ps.setString(3, noSep);
                ps.executeUpdate();
            } else {
                // INSERT minimal (no_rawat, no_sep, kirim_online, status_klaim)
                String sql = "insert into tt_status_eklaim(no_rawat,no_sep,kirim_online,status_klaim) "
                           + "values(?,?,?,?)";
                ps = koneksi.prepareStatement(sql);
                ps.setString(1, noRawat);
                ps.setString(2, noSep);
                ps.setString(3, kirimOnline);
                if (statusKlaim == null) {
                    ps.setNull(4, Types.VARCHAR);
                } else {
                    ps.setString(4, statusKlaim);
                }
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this,
                    "Status klaim berhasil disimpan.",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan status klaim.\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Notifikasi simpanStatus : " + e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception ex) {}
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    // =====================
    // KOMPONEN UI
    // =====================
    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblNoRawat = new javax.swing.JLabel();
        lblNoRawatValue = new javax.swing.JLabel();
        lblNoSep = new javax.swing.JLabel();
        lblNoSepValue = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        chkKirimOnline = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Status Klaim Manual");
        setResizable(false);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); 
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Update Status Klaim Manual");

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc;

        lblNoRawat.setText("No. Rawat :");
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(lblNoRawat, gbc);

        lblNoRawatValue.setText("-");
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(lblNoRawatValue, gbc);

        lblNoSep.setText("No. SEP :");
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(lblNoSep, gbc);

        lblNoSepValue.setText("-");
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(lblNoSepValue, gbc);

        lblStatus.setText("Status Klaim :");
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(8, 2, 2, 2);
        jPanel1.add(lblStatus, gbc);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Belum Kirim", "Pending Klaim", "Gagal Klaim", "Final Klaim" }
        ));
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(8, 2, 2, 2);
        jPanel1.add(cmbStatus, gbc);

        chkKirimOnline.setText("Sudah Kirim Online InaCBG");
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(4, 2, 2, 2);
        jPanel1.add(chkKirimOnline, gbc);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(evt -> btnSimpanActionPerformed(evt));
        btnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                btnSimpanKeyPressed(evt);
            }
        });
        jPanel2.add(btnSimpan);

        btnBatal.setText("Batal");
        btnBatal.addActionListener(evt -> btnBatalActionPerformed(evt));
        jPanel2.add(btnBatal);

        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(lblTitle, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
        simpanStatus();
    }                                         

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {                                         
        dispose();
    }                                        

    private void btnSimpanKeyPressed(java.awt.event.KeyEvent evt) {                                     
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            simpanStatus();
        }
    }                                    

    // Variables declaration
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JCheckBox chkKirimOnline;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblNoRawat;
    private javax.swing.JLabel lblNoRawatValue;
    private javax.swing.JLabel lblNoSep;
    private javax.swing.JLabel lblNoSepValue;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration                   
}