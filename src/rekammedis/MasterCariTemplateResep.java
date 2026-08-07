package rekammedis;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.validasi;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public final class MasterCariTemplateResep extends javax.swing.JDialog {
    public interface TemplateResepListener {
        void templateDipilih(String noTemplate);
    }

    private final DefaultTableModel tabMode, tabModeObatUmum, tabModeObatRacikan, tabModeDetailObatRacikan;
    private final validasi Valid = new validasi();
    private final Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private String kodedokter = "";
    private TemplateResepListener listener;
    private final MasterTemplateResep masterTemplate = new MasterTemplateResep(null, true);

    public MasterCariTemplateResep(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tabMode = model(new Object[] { "No.Template", "Nama Template", "Keterangan", "Kode Dokter", "Nama Dokter" });
        tbDokter.setModel(tabMode);
        aturKolom(tbDokter, new int[] { 110, 220, 280, 90, 180 });
        tabModeObatUmum = model(new Object[] { "Jumlah", "Aturan Pakai", "Kode Barang", "Nama Barang", "Satuan",
                "Komposisi", "Jenis Obat", "I.F.", "Kapasitas" });
        tbObatNonRacikan.setModel(tabModeObatUmum);
        aturKolom(tbObatNonRacikan, new int[] { 55, 150, 80, 220, 60, 140, 110, 110, 0 });
        tabModeObatRacikan = model(new Object[] { "No", "Nama Racikan", "Kode Racik", "Metode Racik", "Jml.Racik",
                "Aturan Pakai", "Keterangan" });
        tbObatRacikan.setModel(tabModeObatRacikan);
        aturKolom(tbObatRacikan, new int[] { 35, 210, 0, 110, 65, 150, 180 });
        tabModeDetailObatRacikan = model(new Object[] { "No", "Kode Barang", "Nama Barang", "Satuan", "Jenis Obat",
                "Kps", "P1", "/", "P2", "Kandungan", "Jml", "I.F.", "Komposisi" });
        tbDetailObatRacikan.setModel(tabModeDetailObatRacikan);
        aturKolom(tbDetailObatRacikan, new int[] { 35, 80, 220, 60, 110, 45, 40, 15, 40, 65, 50, 110, 150 });
        TCari.setDocument(new fungsi.batasInput((byte) 100).getKata(TCari));
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    pakaiTemplate();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && tbDokter.getSelectedRow() >= 0) {
                    tampilDetail(value(tbDokter.getSelectedRow(), 0));
                }
            }
        });
        masterTemplate.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                tampil();
            }
        });
    }

    private DefaultTableModel model(Object[] k) {
        return new DefaultTableModel(null, k) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
    }

    private void aturKolom(JTable t, int[] w) {
        t.setPreferredScrollableViewportSize(new Dimension(500, 500));
        t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        t.setDefaultRenderer(Object.class, new WarnaTable());
        for (int i = 0; i < w.length; i++) {
            TableColumn k = t.getColumnModel().getColumn(i);
            if (w[i] == 0) {
                k.setMinWidth(0);
                k.setMaxWidth(0);
            } else
                k.setPreferredWidth(w[i]);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnTambah = new widget.Button();
        BtnKeluar = new widget.Button();
        scrollPane2 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel19 = new widget.Label();
        ObatNonRacik = new widget.ScrollPane();
        tbObatNonRacikan = new widget.Table();
        jLabel20 = new widget.Label();
        JudulRacikan = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        ObatRacik = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Template Resep Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(310, 402));

        tbDokter.setAutoCreateRowSorter(true);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        Scroll.setViewportView(tbDokter);

        internalFrame1.add(Scroll, java.awt.BorderLayout.WEST);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(312, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi3.add(BtnAll);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Copy Template");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(132, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(LCount);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setText("Buat Template");
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(158, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(155, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        scrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), "Detail Template :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane2.setName("scrollPane2"); // NOI18N

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(730, 2850));
        FormInput.setLayout(null);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Obat Non Racikan :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(30, 20, 270, 23);

        ObatNonRacik.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        ObatNonRacik.setName("ObatNonRacik"); // NOI18N
        ObatNonRacik.setOpaque(true);

        tbObatNonRacikan.setName("tbObatNonRacikan"); // NOI18N
        tbObatNonRacikan.getTableHeader().setReorderingAllowed(false);
        ObatNonRacik.setViewportView(tbObatNonRacikan);

        FormInput.add(ObatNonRacik);
        ObatNonRacik.setBounds(30, 40, 900, 223);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Obat Racikan :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(30, 270, 270, 23);

        JudulRacikan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        JudulRacikan.setName("JudulRacikan"); // NOI18N
        JudulRacikan.setOpaque(true);

        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        tbObatRacikan.getTableHeader().setReorderingAllowed(false);
        JudulRacikan.setViewportView(tbObatRacikan);

        FormInput.add(JudulRacikan);
        JudulRacikan.setBounds(30, 290, 900, 103);

        ObatRacik.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        ObatRacik.setName("ObatRacik"); // NOI18N
        ObatRacik.setOpaque(true);

        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        tbDetailObatRacikan.getTableHeader().setReorderingAllowed(false);
        ObatRacik.setViewportView(tbDetailObatRacikan);

        FormInput.add(ObatRacik);
        ObatRacik.setBounds(30, 400, 900, 223);

        scrollPane2.setViewportView(FormInput);

        internalFrame1.add(scrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            tampil();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            dispose();
    }

    private void BtnCariActionPerformed(java.awt.event.ActionEvent e) {
        tampil();
    }

    private void BtnCariKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            tampil();
    }

    private void BtnAllActionPerformed(java.awt.event.ActionEvent e) {
        TCari.setText("");
        tampil();
    }

    private void BtnAllKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            BtnAllActionPerformed(null);
    }

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent e) {
        dispose();
    }

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent e) {
        masterTemplate.setDokter(kodedokter);
        masterTemplate.isCek();
        masterTemplate.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        masterTemplate.setLocationRelativeTo(internalFrame1);
        masterTemplate.setVisible(true);
        tampil();
    }

    private void formWindowActivated(java.awt.event.WindowEvent e) {
        if (tabMode.getRowCount() == 0)
            tampil();
    }

    private void tbDokterMouseClicked(java.awt.event.MouseEvent e) {
        if (tbDokter.getSelectedRow() >= 0) {
            tampilDetail(value(tbDokter.getSelectedRow(), 0));
            if (e.getClickCount() == 2)
                pakaiTemplate();
        }
    }

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent e) {
        pakaiTemplate();
    }

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            pakaiTemplate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.PanelBiasa FormInput;
    private widget.ScrollPane JudulRacikan;
    private widget.Label LCount;
    private widget.ScrollPane ObatNonRacik;
    private widget.ScrollPane ObatRacik;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane2;
    public widget.Table tbDetailObatRacikan;
    private widget.Table tbDokter;
    public widget.Table tbObatNonRacikan;
    public widget.Table tbObatRacikan;
    // End of variables declaration//GEN-END:variables

    public void setTemplateResepListener(TemplateResepListener x) {
        listener = x;
    }

    public void setDokter(String kode) {
        kodedokter = kode == null ? "" : kode.trim();
        tampil();
    }

    public void setDokter(String kode, String tanggal, String jam, String norawat, String nomorrm) {
        setDokter(kode);
    }

    public void tampil() {
        Valid.tabelKosong(tabMode);
        Valid.tabelKosong(tabModeObatUmum);
        Valid.tabelKosong(tabModeObatRacikan);
        Valid.tabelKosong(tabModeDetailObatRacikan);
        if (kodedokter.isEmpty()) {
            LCount.setText("0");
            return;
        }
        String cari = "%" + TCari.getText().trim() + "%";
        try {
            ps = koneksi.prepareStatement(
                    "select t.no_template,t.nama_template,t.keterangan,t.kd_dokter,d.nm_dokter from template_resep_dokter t inner join dokter d on d.kd_dokter=t.kd_dokter where t.kd_dokter=? and t.aktif='Ya' and (t.no_template like ? or t.nama_template like ? or t.keterangan like ?) order by t.nama_template,t.no_template");
            ps.setString(1, kodedokter);
            ps.setString(2, cari);
            ps.setString(3, cari);
            ps.setString(4, cari);
            rs = ps.executeQuery();
            while (rs.next())
                tabMode.addRow(new Object[] { rs.getString("no_template"), rs.getString("nama_template"),
                        rs.getString("keterangan"), rs.getString("kd_dokter"), rs.getString("nm_dokter") });
        } catch (Exception e) {
            System.out.println("Notif Template Resep : " + e);
        } finally {
            tutup();
        }
        LCount.setText(String.valueOf(tabMode.getRowCount()));
        if (tabMode.getRowCount() > 0) {
            tbDokter.setRowSelectionInterval(0, 0);
            tampilDetail(value(0, 0));
        }
    }

    private void tampilDetail(String n) {
        Valid.tabelKosong(tabModeObatUmum);
        Valid.tabelKosong(tabModeObatRacikan);
        Valid.tabelKosong(tabModeDetailObatRacikan);
        if (n.isEmpty())
            return;
        try {
            ps = koneksi.prepareStatement(
                    "select x.jml,x.kode_brng,b.nama_brng,s.satuan,b.letak_barang,j.nama,x.aturan_pakai,f.nama_industri,b.kapasitas from template_resep_dokter_detail x inner join databarang b on b.kode_brng=x.kode_brng inner join kodesatuan s on s.kode_sat=b.kode_sat inner join jenis j on j.kdjns=b.kdjns inner join industrifarmasi f on f.kode_industri=b.kode_industri where x.no_template=? order by b.nama_brng");
            ps.setString(1, n);
            rs = ps.executeQuery();
            while (rs.next())
                tabModeObatUmum.addRow(new Object[] { rs.getDouble("jml"), rs.getString("aturan_pakai"),
                        rs.getString("kode_brng"), rs.getString("nama_brng"), rs.getString("satuan"),
                        rs.getString("letak_barang"), rs.getString("nama"), rs.getString("nama_industri"),
                        rs.getDouble("kapasitas") });
        } catch (Exception e) {
            System.out.println("Notif Detail Template Resep : " + e);
        } finally {
            tutup();
        }
        try {
            ps = koneksi.prepareStatement(
                    "select r.no_racik,r.nama_racik,r.kd_racik,m.nm_racik,r.jml_dr,r.aturan_pakai,r.keterangan from template_resep_dokter_racikan r left join metode_racik m on m.kd_racik=r.kd_racik where r.no_template=? order by cast(r.no_racik as unsigned)");
            ps.setString(1, n);
            rs = ps.executeQuery();
            while (rs.next())
                tabModeObatRacikan.addRow(new Object[] { rs.getString("no_racik"), rs.getString("nama_racik"),
                        rs.getString("kd_racik"), rs.getString("nm_racik"), rs.getDouble("jml_dr"),
                        rs.getString("aturan_pakai"), rs.getString("keterangan") });
        } catch (Exception e) {
            System.out.println("Notif Racikan Template Resep : " + e);
        } finally {
            tutup();
        }
        try {
            ps = koneksi.prepareStatement(
                    "select x.no_racik,x.kode_brng,b.nama_brng,s.satuan,j.nama,b.kapasitas,x.p1,x.p2,x.kandungan,x.jml,f.nama_industri,b.letak_barang from template_resep_dokter_racikan_detail x inner join databarang b on b.kode_brng=x.kode_brng inner join kodesatuan s on s.kode_sat=b.kode_sat inner join jenis j on j.kdjns=b.kdjns inner join industrifarmasi f on f.kode_industri=b.kode_industri where x.no_template=? order by cast(x.no_racik as unsigned),b.nama_brng");
            ps.setString(1, n);
            rs = ps.executeQuery();
            while (rs.next())
                tabModeDetailObatRacikan.addRow(
                        new Object[] { rs.getString("no_racik"), rs.getString("kode_brng"), rs.getString("nama_brng"),
                                rs.getString("satuan"), rs.getString("nama"), rs.getDouble("kapasitas"),
                                rs.getDouble("p1"), "/", rs.getDouble("p2"), rs.getDouble("kandungan"),
                                rs.getDouble("jml"), rs.getString("nama_industri"), rs.getString("letak_barang") });
        } catch (Exception e) {
            System.out.println("Notif Detail Racikan Template Resep : " + e);
        } finally {
            tutup();
        }
    }

    private void pakaiTemplate() {
        int r = tbDokter.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(null, "Silakan pilih template resep terlebih dahulu.");
            return;
        }
        if (listener == null) {
            JOptionPane.showMessageDialog(null, "Form peresepan belum terhubung.");
            return;
        }
        listener.templateDipilih(value(r, 0));
        dispose();
    }

    private String value(int r, int c) {
        Object x = tbDokter.getValueAt(r, c);
        return x == null ? "" : x.toString();
    }

    private void tutup() {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception e) {
        }
        try {
            if (ps != null)
                ps.close();
        } catch (Exception e) {
        }
        rs = null;
        ps = null;
    }

    public JTable getTable() {
        return tbDokter;
    }

    public void isCek() {
        BtnSimpan.setEnabled(true);
    }
}
