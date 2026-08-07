package rekammedis;

import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.akses;
import fungsi.koneksiDB;
import fungsi.validasi;
import inventory.DlgCariAturanPakai;
import inventory.DlgCariMetodeRacik;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;

public class MasterTemplateResep extends javax.swing.JDialog {
    private final DefaultTableModel tabMode, tabModeObatUmum, tabModeObatRacikan, tabModeDetailObatRacikan;
    private final validasi Valid = new validasi();
    private final Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private final DlgCariMetodeRacik metoderacik = new DlgCariMetodeRacik(null, false);
    private final DlgCariAturanPakai aturanpakai = new DlgCariAturanPakai(null, false);
    private final DlgCariDokter dokter = new DlgCariDokter(null, false);
    private int targetAturan = 0;
    private final widget.TextBox NamaTemplate = new widget.TextBox();
    private final widget.TextBox KeteranganTemplate = new widget.TextBox();
    private final javax.swing.JComboBox<String> Aktif = new javax.swing.JComboBox<>(new String[] { "Ya", "Tidak" });
    private final javax.swing.JLabel LabelNama = new javax.swing.JLabel("Nama Template :");
    private final javax.swing.JLabel LabelKeterangan = new javax.swing.JLabel("Keterangan :");
    private final javax.swing.JLabel LabelAktif = new javax.swing.JLabel("Aktif :");

    public MasterTemplateResep(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        pasangIdentitasTemplate();

        tabMode = model(
                new Object[] { "No.Template", "Kode Dokter", "Nama Dokter", "Nama Template", "Keterangan", "Aktif" },
                new int[] {});
        tbDokter.setModel(tabMode);
        aturKolom(tbDokter, new int[] { 110, 90, 180, 220, 280, 55 });

        tabModeObatUmum = model(new Object[] { "P", "Jumlah", "Aturan Pakai", "Kode Barang", "Nama Barang",
                "Satuan", "Komposisi", "Jenis Obat", "I.F.", "Kapasitas" }, new int[] { 1, 2 });
        tbObatNonRacikan.setModel(tabModeObatUmum);
        aturKolom(tbObatNonRacikan, new int[] { 0, 55, 150, 80, 220, 60, 140, 110, 110, 0 });
        WarnaTable2 w1 = new WarnaTable2();
        w1.kolom = 1;
        tbObatNonRacikan.setDefaultRenderer(Object.class, w1);

        tabModeObatRacikan = model(new Object[] { "No", "Nama Racikan", "Kode Racik", "Metode Racik", "Jml.Racik",
                "Aturan Pakai", "Keterangan" }, new int[] {});
        tbObatRacikan.setModel(tabModeObatRacikan);
        aturKolom(tbObatRacikan, new int[] { 35, 210, 0, 110, 65, 150, 180 });
        WarnaTable2 w2 = new WarnaTable2();
        w2.kolom = 4;
        tbObatRacikan.setDefaultRenderer(Object.class, w2);
        tbObatRacikan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && tbObatRacikan.getSelectedRow() >= 0) {
                    bukaDialogRacikan(true);
                }
            }
        });

        tabModeDetailObatRacikan = model(new Object[] { "No", "Kode Barang", "Nama Barang", "Satuan", "Jenis Obat",
                "Kps", "P1", "/", "P2", "Kandungan", "Jml", "I.F.", "Komposisi" }, new int[] {});
        tbDetailObatRacikan.setModel(tabModeDetailObatRacikan);
        aturKolom(tbDetailObatRacikan, new int[] { 35, 80, 220, 60, 110, 45, 40, 15, 40, 65, 50, 110, 150 });
        WarnaTable2 w3 = new WarnaTable2();
        w3.kolom = 10;
        tbDetailObatRacikan.setDefaultRenderer(Object.class, w3);

        TCari.setDocument(new fungsi.batasInput((byte) 100).getKata(TCari));
        NamaTemplate.setDocument(new fungsi.batasInput(100).getKata(NamaTemplate));
        KeteranganTemplate.setDocument(new fungsi.batasInput(250).getKata(KeteranganTemplate));
        pasangListenerPencarian();
        pasangListenerDialog();
        emptTeks();
    }

    private DefaultTableModel model(Object[] kolom, int[] editable) {
        return new DefaultTableModel(null, kolom) {
            @Override
            public boolean isCellEditable(int r, int c) {
                for (int x : editable)
                    if (x == c)
                        return true;
                return false;
            }

            @Override
            public Class getColumnClass(int c) {
                return c == 0 && kolom.length == 10 ? Boolean.class : Object.class;
            }
        };
    }

    private void aturKolom(JTable t, int[] w) {
        t.setPreferredScrollableViewportSize(new Dimension(500, 500));
        t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        t.getTableHeader().setReorderingAllowed(false);
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

    private void pasangIdentitasTemplate() {
        JComponent[] geser = { jLabel19, BtnCariObatNonRacikan, CariObatNonRacikan, Scroll9, jLabel20, Scroll10,
                CariObatRacikan, BtnCariObatRacikan, Scroll11, BtnAllObatNonRacikan, BtnAllObatRacikan, BtnTambah1,
                BtnHapus1 };
        for (JComponent c : geser)
            c.setLocation(c.getX(), c.getY() + 70);
        LabelNama.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LabelKeterangan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LabelAktif.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        FormInput.add(LabelNama);
        LabelNama.setBounds(0, 42, 105, 23);
        FormInput.add(NamaTemplate);
        NamaTemplate.setBounds(109, 42, 315, 23);
        FormInput.add(LabelAktif);
        LabelAktif.setBounds(430, 42, 55, 23);
        FormInput.add(Aktif);
        Aktif.setBounds(490, 42, 100, 23);
        FormInput.add(LabelKeterangan);
        LabelKeterangan.setBounds(0, 72, 85, 23);
        FormInput.add(KeteranganTemplate);
        KeteranganTemplate.setBounds(89, 72, 627, 23);
        CariObatRacikan.setVisible(false);
        BtnCariObatRacikan.setVisible(false);
        BtnAllObatRacikan.setVisible(false);
        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder("::[ Master Template Resep Dokter ]::"));
        FormDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(" Detail Template Resep : "));
    }

    private void pasangListenerPencarian() {
        TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                if (TCari.getText().length() > 2)
                    tampil();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                if (TCari.getText().isEmpty() || TCari.getText().length() > 2)
                    tampil();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
            }
        });
    }

    private void pasangListenerDialog() {
        dokter.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                int r = dokter.getTable().getSelectedRow();
                if (r >= 0) {
                    KdDokter.setText(dokter.getTable().getValueAt(r, 0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(r, 1).toString());
                    tampil();
                }
            }
        });
        aturanpakai.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                int r = aturanpakai.getTable().getSelectedRow();
                if (r >= 0) {
                    String a = aturanpakai.getTable().getValueAt(r, 0).toString();
                    if (targetAturan == 1 && tbObatNonRacikan.getSelectedRow() >= 0)
                        tbObatNonRacikan.setValueAt(a, tbObatNonRacikan.getSelectedRow(), 2);
                    else if (targetAturan == 2 && tbObatRacikan.getSelectedRow() >= 0)
                        tbObatRacikan.setValueAt(a, tbObatRacikan.getSelectedRow(), 5);
                }
            }
        });
        metoderacik.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                int r = metoderacik.getTable().getSelectedRow();
                if (r >= 0 && tbObatRacikan.getSelectedRow() >= 0) {
                    int t = tbObatRacikan.getSelectedRow();
                    tbObatRacikan.setValueAt(metoderacik.getTable().getValueAt(r, 1).toString(), t, 2);
                    tbObatRacikan.setValueAt(metoderacik.getTable().getValueAt(r, 2).toString(), t, 3);
                }
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppSemua = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        Kd = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel19 = new widget.Label();
        BtnCariObatNonRacikan = new widget.Button();
        CariObatNonRacikan = new widget.TextBox();
        Scroll9 = new widget.ScrollPane();
        tbObatNonRacikan = new widget.Table();
        jLabel20 = new widget.Label();
        Scroll10 = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        CariObatRacikan = new widget.TextBox();
        BtnCariObatRacikan = new widget.Button();
        Scroll11 = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();
        BtnAllObatNonRacikan = new widget.Button();
        BtnAllObatRacikan = new widget.Button();
        BtnTambah1 = new widget.Button();
        BtnHapus1 = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelGlass9 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormDetail = new widget.PanelBiasa();
        Scroll13 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua.setText("Pilih Semua");
        ppSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua.setName("ppSemua"); // NOI18N
        ppSemua.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaActionPerformed(evt);
            }
        });
        Popup.add(ppSemua);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Template Pemeriksaan Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(993, 670));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setName("TabRawat"); // NOI18N

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(730, 2966));
        FormInput.setLayout(null);

        label12.setText("No.Template :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label12);
        label12.setBounds(0, 10, 85, 23);

        Kd.setEditable(false);
        Kd.setName("Kd"); // NOI18N
        Kd.setPreferredSize(new java.awt.Dimension(207, 23));
        Kd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKeyPressed(evt);
            }
        });
        FormInput.add(Kd);
        Kd.setBounds(89, 10, 150, 23);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(230, 10, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(304, 10, 120, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(426, 10, 260, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(688, 10, 28, 23);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Resep Non Racikan :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(20, 50, 270, 23);

        BtnCariObatNonRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariObatNonRacikan.setMnemonic('1');
        BtnCariObatNonRacikan.setToolTipText("Alt+1");
        BtnCariObatNonRacikan.setName("BtnCariObatNonRacikan"); // NOI18N
        BtnCariObatNonRacikan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariObatNonRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariObatNonRacikanActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariObatNonRacikan);
        BtnCariObatNonRacikan.setBounds(660, 70, 28, 23);

        CariObatNonRacikan.setHighlighter(null);
        CariObatNonRacikan.setName("CariObatNonRacikan"); // NOI18N
        CariObatNonRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariObatNonRacikanKeyPressed(evt);
            }
        });
        FormInput.add(CariObatNonRacikan);
        CariObatNonRacikan.setBounds(20, 70, 640, 23);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbObatNonRacikan.setName("tbObatNonRacikan"); // NOI18N
        tbObatNonRacikan.getTableHeader().setReorderingAllowed(false);
        tbObatNonRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatNonRacikanKeyPressed(evt);
            }
        });
        Scroll9.setViewportView(tbObatNonRacikan);

        FormInput.add(Scroll9);
        Scroll9.setBounds(20, 100, 900, 216);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Resep Racikan :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(20, 320, 270, 23);

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        tbObatRacikan.getTableHeader().setReorderingAllowed(false);
        tbObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatRacikanKeyPressed(evt);
            }
        });
        Scroll10.setViewportView(tbObatRacikan);

        FormInput.add(Scroll10);
        Scroll10.setBounds(20, 340, 900, 96);

        CariObatRacikan.setHighlighter(null);
        CariObatRacikan.setName("CariObatRacikan"); // NOI18N
        CariObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariObatRacikanKeyPressed(evt);
            }
        });
        FormInput.add(CariObatRacikan);
        CariObatRacikan.setBounds(20, 440, 440, 23);

        BtnCariObatRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariObatRacikan.setMnemonic('1');
        BtnCariObatRacikan.setToolTipText("Alt+1");
        BtnCariObatRacikan.setName("BtnCariObatRacikan"); // NOI18N
        BtnCariObatRacikan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariObatRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariObatRacikanActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariObatRacikan);
        BtnCariObatRacikan.setBounds(660, 440, 28, 23);

        Scroll11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        tbDetailObatRacikan.getTableHeader().setReorderingAllowed(false);
        tbDetailObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailObatRacikanKeyPressed(evt);
            }
        });
        Scroll11.setViewportView(tbDetailObatRacikan);

        FormInput.add(Scroll11);
        Scroll11.setBounds(20, 470, 900, 216);

        BtnAllObatNonRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllObatNonRacikan.setMnemonic('2');
        BtnAllObatNonRacikan.setToolTipText("Alt+2");
        BtnAllObatNonRacikan.setName("BtnAllObatNonRacikan"); // NOI18N
        BtnAllObatNonRacikan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllObatNonRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllObatNonRacikanActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllObatNonRacikan);
        BtnAllObatNonRacikan.setBounds(690, 70, 28, 23);

        BtnAllObatRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllObatRacikan.setMnemonic('2');
        BtnAllObatRacikan.setToolTipText("Alt+2");
        BtnAllObatRacikan.setName("BtnAllObatRacikan"); // NOI18N
        BtnAllObatRacikan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllObatRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllObatRacikanActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllObatRacikan);
        BtnAllObatRacikan.setBounds(690, 440, 28, 23);

        BtnTambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        BtnTambah1.setMnemonic('3');
        BtnTambah1.setText("Tambah Racikan");
        BtnTambah1.setToolTipText("Alt+3");
        BtnTambah1.setName("BtnTambah1"); // NOI18N
        BtnTambah1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambah1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambah1);
        BtnTambah1.setBounds(470, 440, 160, 23);

        BtnHapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus1.setMnemonic('H');
        BtnHapus1.setToolTipText("Alt+H");
        BtnHapus1.setName("BtnHapus1"); // NOI18N
        BtnHapus1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapus1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnHapus1);
        BtnHapus1.setBounds(630, 440, 28, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Template", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbDokter.setAutoCreateRowSorter(true);
        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbDokter);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(530, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
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
        panelGlass9.add(BtnAll);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormDetail.setBackground(new java.awt.Color(255, 255, 255));
        FormDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Detail Template Pemeriksaan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        FormDetail.setName("FormDetail"); // NOI18N
        FormDetail.setPreferredSize(new java.awt.Dimension(115, 73));
        FormDetail.setLayout(new java.awt.BorderLayout());

        Scroll13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll13.setName("Scroll13"); // NOI18N
        Scroll13.setOpaque(true);
        Scroll13.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll13.setViewportView(LoadHTML);

        FormDetail.add(Scroll13, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormDetail, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Template", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass8.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            tampil();
    }

    private void BtnCariActionPerformed(java.awt.event.ActionEvent e) {
        tampil();
    }

    private void BtnCariKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            tampil();
    }

    private void tbDokterMouseClicked(java.awt.event.MouseEvent e) {
        if (tbDokter.getSelectedRow() >= 0)
            getData();
    }

    private void tbDokterKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && tbDokter.getSelectedRow() >= 0)
            getData();
    }

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent e) {
        hapus();
    }

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            hapus();
    }

    private void BtnEditActionPerformed(java.awt.event.ActionEvent e) {
        simpan(true);
    }

    private void BtnEditKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            simpan(true);
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

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            dispose();
    }

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent e) {
        simpan(false);
    }

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            simpan(false);
    }

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent e) {
        emptTeks();
    }

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            emptTeks();
    }

    private void KdKeyPressed(java.awt.event.KeyEvent e) {
    }

    private void KdDokterKeyPressed(java.awt.event.KeyEvent e) {
    }

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent e) {
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            BtnDokterActionPerformed(null);
    }

    private void BtnCariObatNonRacikanActionPerformed(java.awt.event.ActionEvent e) {
        cariObatNonRacikan();
    }

    private void CariObatNonRacikanKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            cariObatNonRacikan();
    }

    private void CariObatRacikanKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            cariDetailRacikan();
    }

    private void BtnCariObatRacikanActionPerformed(java.awt.event.ActionEvent e) {
        cariDetailRacikan();
    }

    private void BtnAllObatNonRacikanActionPerformed(java.awt.event.ActionEvent e) {
        CariObatNonRacikan.setText("");
        cariObatNonRacikan();
    }

    private void BtnAllObatRacikanActionPerformed(java.awt.event.ActionEvent e) {
        CariObatRacikan.setText("");
        cariDetailRacikan();
    }

    private void BtnTambah1ActionPerformed(java.awt.event.ActionEvent e) {
        bukaDialogRacikan(false);
    }

    private void BtnHapus1ActionPerformed(java.awt.event.ActionEvent e) {
        hapusRacikanTerpilih();
    }

    private void tbObatNonRacikanKeyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && tbObatNonRacikan.getSelectedRow() >= 0
                && tbObatNonRacikan.getSelectedColumn() == 2) {
            targetAturan = 1;
            bukaAturan();
        }
    }

    private void tbObatRacikanKeyPressed(java.awt.event.KeyEvent e) {
        if (tbObatRacikan.getSelectedRow() < 0)
            return;
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
            bukaDialogRacikan(true);
            e.consume();
        }
    }

    private void pilihMetodeRacik() {
        int baris = tbObatRacikan.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(null, "Pilih racikan terlebih dahulu.");
            return;
        }
        if (value(tbObatRacikan, baris, 1).trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Silakan isi nama racikan terlebih dahulu.");
            tbObatRacikan.changeSelection(baris, 1, false, false);
            tbObatRacikan.requestFocus();
            return;
        }
        bukaMetode();
    }

    private void tbDetailObatRacikanKeyPressed(java.awt.event.KeyEvent e) {
    }

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent e) {
        FormDetail.setVisible(ChkAccor.isSelected());
        PanelAccor.setPreferredSize(ChkAccor.isSelected() ? new Dimension(430, 43) : new Dimension(15, 43));
    }

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent e) {
        for (int i = 0; i < tabModeObatUmum.getRowCount(); i++) {
            tabModeObatUmum.setValueAt(false, i, 0);
            tabModeObatUmum.setValueAt("", i, 1);
            tabModeObatUmum.setValueAt("", i, 2);
        }
    }

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent e) {
        for (int i = 0; i < tabModeObatUmum.getRowCount(); i++)
            tabModeObatUmum.setValueAt(true, i, 0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnAllObatNonRacikan;
    private widget.Button BtnAllObatRacikan;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariObatNonRacikan;
    private widget.Button BtnCariObatRacikan;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnHapus1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah1;
    public widget.TextBox CariObatNonRacikan;
    public widget.TextBox CariObatRacikan;
    private widget.CekBox ChkAccor;
    private widget.PanelBiasa FormDetail;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kd;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox NmDokter;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll13;
    private widget.ScrollPane Scroll9;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppSemua;
    private widget.ScrollPane scrollInput;
    public widget.Table tbDetailObatRacikan;
    private widget.Table tbDokter;
    public widget.Table tbObatNonRacikan;
    public widget.Table tbObatRacikan;
    // End of variables declaration//GEN-END:variables

    public void setDokter(String kode) {
        if (kode != null && !kode.trim().isEmpty()) {
            KdDokter.setText(kode.trim());
            NmDokter.setText(dokter.tampil3(kode.trim()));
            BtnDokter.setEnabled(false);
        }
        tampil();
    }

    public void isCek() {
        if (KdDokter.getText().trim().isEmpty()) {
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
        }
        BtnDokter.setEnabled(KdDokter.getText().trim().isEmpty());
    }

    public void setTampil() {
        TabRawat.setSelectedIndex(1);
        tampil();
    }

    public JTable getTable() {
        return tbDokter;
    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        if (KdDokter.getText().trim().isEmpty()) {
            LCount.setText("0");
            return;
        }
        String q = "%" + TCari.getText().trim() + "%";
        try {
            ps = koneksi.prepareStatement(
                    "select t.no_template,t.kd_dokter,d.nm_dokter,t.nama_template,t.keterangan,t.aktif from template_resep_dokter t inner join dokter d on d.kd_dokter=t.kd_dokter where t.kd_dokter=? and (t.no_template like ? or t.nama_template like ? or t.keterangan like ?) order by t.nama_template,t.no_template");
            ps.setString(1, KdDokter.getText());
            ps.setString(2, q);
            ps.setString(3, q);
            ps.setString(4, q);
            rs = ps.executeQuery();
            while (rs.next())
                tabMode.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6) });
        } catch (Exception e) {
            System.out.println("Notif Master Template Resep : " + e);
        } finally {
            tutup();
        }
        LCount.setText(String.valueOf(tabMode.getRowCount()));
    }

    public void emptTeks() {
        Kd.setText("");
        NamaTemplate.setText("");
        KeteranganTemplate.setText("");
        Aktif.setSelectedItem("Ya");
        CariObatNonRacikan.setText("");
        CariObatRacikan.setText("");
        Valid.tabelKosong(tabModeObatUmum);
        Valid.tabelKosong(tabModeObatRacikan);
        Valid.tabelKosong(tabModeDetailObatRacikan);
        Valid.autoNomer("template_resep_dokter", "TRD", 16, Kd);
        TabRawat.setSelectedIndex(0);
        cariObatNonRacikan();
        NamaTemplate.requestFocus();
    }

    private void getData() {
        int r = tbDokter.getSelectedRow();
        if (r < 0)
            return;
        Kd.setText(value(tbDokter, r, 0));
        KdDokter.setText(value(tbDokter, r, 1));
        NmDokter.setText(value(tbDokter, r, 2));
        NamaTemplate.setText(value(tbDokter, r, 3));
        KeteranganTemplate.setText(value(tbDokter, r, 4));
        Aktif.setSelectedItem(value(tbDokter, r, 5));
        muatDetail(Kd.getText());
        TabRawat.setSelectedIndex(0);
    }

    private void muatDetail(String n) {
        Valid.tabelKosong(tabModeObatUmum);
        Valid.tabelKosong(tabModeObatRacikan);
        Valid.tabelKosong(tabModeDetailObatRacikan);
        try {
            ps = koneksi.prepareStatement(
                    "select x.jml,x.kode_brng,b.nama_brng,s.satuan,b.letak_barang,j.nama,x.aturan_pakai,f.nama_industri,b.kapasitas from template_resep_dokter_detail x inner join databarang b on b.kode_brng=x.kode_brng inner join kodesatuan s on s.kode_sat=b.kode_sat inner join jenis j on j.kdjns=b.kdjns inner join industrifarmasi f on f.kode_industri=b.kode_industri where x.no_template=? order by b.nama_brng");
            ps.setString(1, n);
            rs = ps.executeQuery();
            while (rs.next())
                tabModeObatUmum
                        .addRow(new Object[] { true, rs.getDouble(1), rs.getString(7), rs.getString(2), rs.getString(3),
                                rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(8), rs.getDouble(9) });
        } catch (Exception e) {
            System.out.println("Notif Detail Template : " + e);
        } finally {
            tutup();
        }
        try {
            ps = koneksi.prepareStatement(
                    "select r.no_racik,r.nama_racik,r.kd_racik,m.nm_racik,r.jml_dr,r.aturan_pakai,r.keterangan from template_resep_dokter_racikan r left join metode_racik m on m.kd_racik=r.kd_racik where r.no_template=? order by cast(r.no_racik as unsigned)");
            ps.setString(1, n);
            rs = ps.executeQuery();
            while (rs.next())
                tabModeObatRacikan.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getDouble(5), rs.getString(6), rs.getString(7) });
        } catch (Exception e) {
            System.out.println("Notif Racikan Template : " + e);
        } finally {
            tutup();
        }
        try {
            ps = koneksi.prepareStatement(
                    "select x.no_racik,x.kode_brng,b.nama_brng,s.satuan,j.nama,b.kapasitas,x.p1,x.p2,x.kandungan,x.jml,f.nama_industri,b.letak_barang from template_resep_dokter_racikan_detail x inner join databarang b on b.kode_brng=x.kode_brng inner join kodesatuan s on s.kode_sat=b.kode_sat inner join jenis j on j.kdjns=b.kdjns inner join industrifarmasi f on f.kode_industri=b.kode_industri where x.no_template=? order by cast(x.no_racik as unsigned),b.nama_brng");
            ps.setString(1, n);
            rs = ps.executeQuery();
            while (rs.next())
                tabModeDetailObatRacikan.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getDouble(7), "/", rs.getDouble(8),
                        rs.getDouble(9), rs.getDouble(10), rs.getString(11), rs.getString(12) });
        } catch (Exception e) {
            System.out.println("Notif Detail Racikan Template : " + e);
        } finally {
            tutup();
        }
        cariObatNonRacikan();
        tampilRingkasan();
    }

    private void simpan(boolean ganti) {
        if (Kd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No. template masih kosong.");
            return;
        }
        if (KdDokter.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Dokter belum dipilih.");
            return;
        }
        if (NamaTemplate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama template wajib diisi.");
            NamaTemplate.requestFocus();
            return;
        }
        if (!adaIsi()) {
            JOptionPane.showMessageDialog(null, "Masukkan minimal satu obat nonracik atau racikan.");
            return;
        }
        boolean auto = true;
        try {
            auto = koneksi.getAutoCommit();
            koneksi.setAutoCommit(false);
            if (ganti)
                hapusData(Kd.getText());
            else if (adaTemplate(Kd.getText())) {
                JOptionPane.showMessageDialog(null, "No. template sudah digunakan.");
                koneksi.rollback();
                return;
            }
            ps = koneksi.prepareStatement(
                    "insert into template_resep_dokter(no_template,kd_dokter,nama_template,keterangan,aktif) values(?,?,?,?,?)");
            ps.setString(1, Kd.getText());
            ps.setString(2, KdDokter.getText());
            ps.setString(3, NamaTemplate.getText().trim());
            ps.setString(4, KeteranganTemplate.getText().trim());
            ps.setString(5, Aktif.getSelectedItem().toString());
            ps.executeUpdate();
            ps.close();
            ps = null;
            simpanNonRacik();
            simpanRacikan();
            koneksi.commit();
            JOptionPane.showMessageDialog(null, "Template resep berhasil " + (ganti ? "diperbarui." : "disimpan."));
            tampil();
            emptTeks();
        } catch (Exception e) {
            try {
                koneksi.rollback();
            } catch (Exception x) {
            }
            JOptionPane.showMessageDialog(null, "Gagal menyimpan template resep: " + e.getMessage());
            System.out.println("Notif Simpan Template Resep : " + e);
        } finally {
            tutup();
            try {
                koneksi.setAutoCommit(auto);
            } catch (Exception e) {
            }
        }
    }

    private void simpanNonRacik() throws Exception {
        ps = koneksi.prepareStatement(
                "insert into template_resep_dokter_detail(no_template,kode_brng,jml,aturan_pakai) values(?,?,?,?)");
        for (int i = 0; i < tabModeObatUmum.getRowCount(); i++) {
            double j = angka(tabModeObatUmum.getValueAt(i, 1));
            if (j > 0) {
                ps.setString(1, Kd.getText());
                ps.setString(2, value(tbObatNonRacikan, i, 3));
                ps.setDouble(3, j);
                ps.setString(4, value(tbObatNonRacikan, i, 2));
                ps.addBatch();
            }
        }
        ps.executeBatch();
        ps.close();
        ps = null;
    }

    private void simpanRacikan() throws Exception {
        ps = koneksi.prepareStatement(
                "insert into template_resep_dokter_racikan(no_template,no_racik,nama_racik,kd_racik,jml_dr,aturan_pakai,keterangan) values(?,?,?,?,?,?,?)");
        for (int i = 0; i < tabModeObatRacikan.getRowCount(); i++) {
            if (angka(tabModeObatRacikan.getValueAt(i, 4)) > 0) {
                if (value(tbObatRacikan, i, 1).isEmpty() || value(tbObatRacikan, i, 2).isEmpty())
                    throw new Exception("Data racikan nomor " + value(tbObatRacikan, i, 0) + " belum lengkap");
                ps.setString(1, Kd.getText());
                ps.setString(2, value(tbObatRacikan, i, 0));
                ps.setString(3, value(tbObatRacikan, i, 1));
                ps.setString(4, value(tbObatRacikan, i, 2));
                ps.setDouble(5, angka(tabModeObatRacikan.getValueAt(i, 4)));
                ps.setString(6, value(tbObatRacikan, i, 5));
                ps.setString(7, value(tbObatRacikan, i, 6));
                ps.addBatch();
            }
        }
        ps.executeBatch();
        ps.close();
        ps = null;
        ps = koneksi.prepareStatement(
                "insert into template_resep_dokter_racikan_detail(no_template,no_racik,kode_brng,p1,p2,kandungan,jml) values(?,?,?,?,?,?,?)");
        for (int i = 0; i < tabModeDetailObatRacikan.getRowCount(); i++) {
            double j = angka(tabModeDetailObatRacikan.getValueAt(i, 10));
            if (j > 0) {
                ps.setString(1, Kd.getText());
                ps.setString(2, value(tbDetailObatRacikan, i, 0));
                ps.setString(3, value(tbDetailObatRacikan, i, 1));
                ps.setDouble(4, angka(tabModeDetailObatRacikan.getValueAt(i, 6)));
                ps.setDouble(5, angka(tabModeDetailObatRacikan.getValueAt(i, 8)));
                ps.setDouble(6, angka(tabModeDetailObatRacikan.getValueAt(i, 9)));
                ps.setDouble(7, j);
                ps.addBatch();
            }
        }
        ps.executeBatch();
        ps.close();
        ps = null;
    }

    private void hapus() {
        if (Kd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pilih template yang akan dihapus.");
            return;
        }
        if (JOptionPane.showConfirmDialog(null, "Hapus template " + NamaTemplate.getText() + "?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
            return;
        boolean auto = true;
        try {
            auto = koneksi.getAutoCommit();
            koneksi.setAutoCommit(false);
            hapusData(Kd.getText());
            koneksi.commit();
            tampil();
            emptTeks();
        } catch (Exception e) {
            try {
                koneksi.rollback();
            } catch (Exception x) {
            }
            JOptionPane.showMessageDialog(null, "Gagal menghapus template: " + e.getMessage());
        } finally {
            tutup();
            try {
                koneksi.setAutoCommit(auto);
            } catch (Exception e) {
            }
        }
    }

    private void hapusData(String n) throws Exception {
        String[] tabel = { "template_resep_dokter_racikan_detail", "template_resep_dokter_racikan",
                "template_resep_dokter_detail", "template_resep_dokter" };
        for (String t : tabel) {
            ps = koneksi.prepareStatement("delete from " + t + " where no_template=?");
            ps.setString(1, n);
            ps.executeUpdate();
            ps.close();
            ps = null;
        }
    }

    private boolean adaTemplate(String n) throws Exception {
        ps = koneksi.prepareStatement("select 1 from template_resep_dokter where no_template=? limit 1");
        ps.setString(1, n);
        rs = ps.executeQuery();
        boolean a = rs.next();
        rs.close();
        ps.close();
        rs = null;
        ps = null;
        return a;
    }

    private boolean adaIsi() {
        for (int i = 0; i < tabModeObatUmum.getRowCount(); i++)
            if (angka(tabModeObatUmum.getValueAt(i, 1)) > 0)
                return true;
        for (int i = 0; i < tabModeObatRacikan.getRowCount(); i++)
            if (angka(tabModeObatRacikan.getValueAt(i, 4)) > 0)
                return true;
        return false;
    }

    private void cariObatNonRacikan() {
        Map<String, Object[]> pilih = new LinkedHashMap<>();
        for (int i = 0; i < tabModeObatUmum.getRowCount(); i++)
            if (angka(tabModeObatUmum.getValueAt(i, 1)) > 0)
                pilih.put(value(tbObatNonRacikan, i, 3), row(tabModeObatUmum, i));
        Valid.tabelKosong(tabModeObatUmum);
        for (Object[] x : pilih.values())
            tabModeObatUmum.addRow(x);
        String q = "%" + CariObatNonRacikan.getText().trim() + "%";
        try {
            ps = koneksi.prepareStatement(
                    "select b.kode_brng,b.nama_brng,s.satuan,b.letak_barang,j.nama,f.nama_industri,b.kapasitas from databarang b inner join kodesatuan s on s.kode_sat=b.kode_sat inner join jenis j on j.kdjns=b.kdjns inner join industrifarmasi f on f.kode_industri=b.kode_industri where b.status='1' and (b.kode_brng like ? or b.nama_brng like ? or b.letak_barang like ? or j.nama like ?) order by b.nama_brng limit 300");
            for (int x = 1; x <= 4; x++)
                ps.setString(x, q);
            rs = ps.executeQuery();
            while (rs.next())
                if (!pilih.containsKey(rs.getString(1)))
                    tabModeObatUmum.addRow(new Object[] { false, "", "", rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7) });
        } catch (Exception e) {
            System.out.println("Notif Cari Obat Template : " + e);
        } finally {
            tutup();
        }
    }

    private void cariDetailRacikan() {
        int s = tbObatRacikan.getSelectedRow();
        if (s < 0) {
            JOptionPane.showMessageDialog(null, "Pilih racikan terlebih dahulu.");
            return;
        }
        String no = value(tbObatRacikan, s, 0);
        Map<String, Object[]> pilih = new LinkedHashMap<>();
        for (int i = 0; i < tabModeDetailObatRacikan.getRowCount(); i++)
            if (angka(tabModeDetailObatRacikan.getValueAt(i, 10)) > 0 || !value(tbDetailObatRacikan, i, 0).equals(no))
                pilih.put(value(tbDetailObatRacikan, i, 0) + "|" + value(tbDetailObatRacikan, i, 1),
                        row(tabModeDetailObatRacikan, i));
        Valid.tabelKosong(tabModeDetailObatRacikan);
        for (Object[] x : pilih.values())
            tabModeDetailObatRacikan.addRow(x);
        String q = "%" + CariObatRacikan.getText().trim() + "%";
        try {
            ps = koneksi.prepareStatement(
                    "select b.kode_brng,b.nama_brng,s.satuan,j.nama,b.kapasitas,f.nama_industri,b.letak_barang from databarang b inner join kodesatuan s on s.kode_sat=b.kode_sat inner join jenis j on j.kdjns=b.kdjns inner join industrifarmasi f on f.kode_industri=b.kode_industri where b.status='1' and (b.kode_brng like ? or b.nama_brng like ? or b.letak_barang like ? or j.nama like ?) order by b.nama_brng limit 300");
            for (int x = 1; x <= 4; x++)
                ps.setString(x, q);
            rs = ps.executeQuery();
            while (rs.next())
                if (!pilih.containsKey(no + "|" + rs.getString(1)))
                    tabModeDetailObatRacikan.addRow(
                            new Object[] { no, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                    rs.getDouble(5), 1D, "/", 1D, 0D, 0D, rs.getString(6), rs.getString(7) });
        } catch (Exception e) {
            System.out.println("Notif Cari Detail Racikan : " + e);
        } finally {
            tutup();
        }
    }

    private void bukaDialogRacikan(boolean edit) {
        int barisEdit = edit ? tbObatRacikan.getSelectedRow() : -1;
        boolean tambahLagi;
        do {
            int nomor = barisEdit >= 0
                    ? (int) angka(tabModeObatRacikan.getValueAt(barisEdit, 0))
                    : nomorRacikBerikut();
            if (nomor > 98) {
                JOptionPane.showMessageDialog(null, "Maksimal 98 racikan.");
                return;
            }

            String noRacik = String.valueOf(nomor);
            DlgInputTemplateRacikan dialog = new DlgInputTemplateRacikan(null, true);
            if (barisEdit >= 0) {
                dialog.setData(
                        noRacik,
                        value(tbObatRacikan, barisEdit, 1),
                        value(tbObatRacikan, barisEdit, 2),
                        angka(tabModeObatRacikan.getValueAt(barisEdit, 4)),
                        value(tbObatRacikan, barisEdit, 5),
                        value(tbObatRacikan, barisEdit, 6),
                        ambilDetailRacikan(noRacik));
            } else {
                dialog.setData(noRacik, "", "", 0, "", "", new ArrayList<>());
            }
            dialog.setLocationRelativeTo(internalFrame1);
            dialog.setVisible(true);
            if (!dialog.isDisimpan()) {
                return;
            }

            simpanCacheRacikan(dialog, barisEdit);
            tambahLagi = dialog.isTambahLagi();
            barisEdit = -1;
        } while (tambahLagi);
    }

    private List<DlgInputTemplateRacikan.Detail> ambilDetailRacikan(String noRacik) {
        List<DlgInputTemplateRacikan.Detail> detail = new ArrayList<>();
        for (int i = 0; i < tabModeDetailObatRacikan.getRowCount(); i++) {
            if (noRacik.equals(value(tbDetailObatRacikan, i, 0))) {
                DlgInputTemplateRacikan.Detail d = new DlgInputTemplateRacikan.Detail();
                d.kodeBarang = value(tbDetailObatRacikan, i, 1);
                d.namaBarang = value(tbDetailObatRacikan, i, 2);
                d.satuan = value(tbDetailObatRacikan, i, 3);
                d.jenis = value(tbDetailObatRacikan, i, 4);
                d.kapasitas = angka(tabModeDetailObatRacikan.getValueAt(i, 5));
                d.p1 = angka(tabModeDetailObatRacikan.getValueAt(i, 6));
                d.p2 = angka(tabModeDetailObatRacikan.getValueAt(i, 8));
                d.kandungan = angka(tabModeDetailObatRacikan.getValueAt(i, 9));
                d.jumlah = angka(tabModeDetailObatRacikan.getValueAt(i, 10));
                d.industri = value(tbDetailObatRacikan, i, 11);
                d.komposisi = value(tbDetailObatRacikan, i, 12);
                detail.add(d);
            }
        }
        return detail;
    }

    private void simpanCacheRacikan(DlgInputTemplateRacikan dialog, int barisEdit) {
        String noRacik = dialog.getNoRacik();
        Object[] header = new Object[] {
            noRacik,
            dialog.getNamaRacik(),
            dialog.getKodeMetode(),
            dialog.getNamaMetode(),
            dialog.getJumlahRacik(),
            dialog.getAturanPakai(),
            dialog.getKeterangan()
        };

        if (barisEdit >= 0) {
            for (int kolom = 0; kolom < header.length; kolom++) {
                tabModeObatRacikan.setValueAt(header[kolom], barisEdit, kolom);
            }
        } else {
            tabModeObatRacikan.addRow(header);
            barisEdit = tabModeObatRacikan.getRowCount() - 1;
        }

        for (int i = tabModeDetailObatRacikan.getRowCount() - 1; i >= 0; i--) {
            if (noRacik.equals(value(tbDetailObatRacikan, i, 0))) {
                tabModeDetailObatRacikan.removeRow(i);
            }
        }
        for (DlgInputTemplateRacikan.Detail d : dialog.getDetail()) {
            tabModeDetailObatRacikan.addRow(new Object[] {
                noRacik, d.kodeBarang, d.namaBarang, d.satuan, d.jenis,
                d.kapasitas, d.p1, "/", d.p2, d.kandungan, d.jumlah,
                d.industri, d.komposisi
            });
        }
        tbObatRacikan.setRowSelectionInterval(barisEdit, barisEdit);
        tampilRingkasan();
    }

    private void hapusRacikanTerpilih() {
        int r = tbObatRacikan.getSelectedRow();
        if (r < 0)
            return;
        String no = value(tbObatRacikan, r, 0);
        tabModeObatRacikan.removeRow(r);
        for (int i = tabModeDetailObatRacikan.getRowCount() - 1; i >= 0; i--)
            if (value(tbDetailObatRacikan, i, 0).equals(no))
                tabModeDetailObatRacikan.removeRow(i);
    }

    private int nomorRacikBerikut() {
        int m = 0;
        for (int i = 0; i < tabModeObatRacikan.getRowCount(); i++)
            try {
                m = Math.max(m, Integer.parseInt(value(tbObatRacikan, i, 0)));
            } catch (Exception e) {
            }
        return m + 1;
    }

    private void bukaAturan() {
        aturanpakai.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        aturanpakai.setLocationRelativeTo(internalFrame1);
        aturanpakai.setVisible(true);
    }

    private void bukaMetode() {
        metoderacik.isCek();
        metoderacik.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        metoderacik.setLocationRelativeTo(internalFrame1);
        metoderacik.setVisible(true);
    }

    private void tampilRingkasan() {
        LoadHTML.setText("<html><body><b>" + html(NamaTemplate.getText()) + "</b><br>Obat nonracik: "
                + jumlahTerisi(tabModeObatUmum, 1) + "<br>Racikan: " + jumlahTerisi(tabModeObatRacikan, 4)
                + "</body></html>");
    }

    private int jumlahTerisi(DefaultTableModel m, int c) {
        int n = 0;
        for (int i = 0; i < m.getRowCount(); i++)
            if (angka(m.getValueAt(i, c)) > 0)
                n++;
        return n;
    }

    private String html(String x) {
        return x.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private Object[] row(DefaultTableModel m, int r) {
        Object[] x = new Object[m.getColumnCount()];
        for (int i = 0; i < x.length; i++)
            x[i] = m.getValueAt(r, i);
        return x;
    }

    private String value(JTable t, int r, int c) {
        Object x = t.getValueAt(r, c);
        return x == null ? "" : x.toString();
    }

    private double angka(Object x) {
        try {
            return Double.parseDouble(x == null ? "0" : x.toString());
        } catch (Exception e) {
            return 0;
        }
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

}
