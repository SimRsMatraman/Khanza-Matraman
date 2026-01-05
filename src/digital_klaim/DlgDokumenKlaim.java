package digital_klaim;

import simrskhanza.*;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariDokter2;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import antrian.AntrianApi;
//import custom.DlgAlasanBatal;
//import custom.DlgJadwalOpenReg;
//import menu_registrasi.DlgKonfirmasiKehadiran;
//import custom.DlgPilihanCetakDokumenReg;
//import custom.GBRPemeriksaanHybrid;
//import custom.fungsiOtomatis;
//import fungsi.TabelRegistrasi;
//import fungsi.TabelRegistrasiNew;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import java.util.Locale;

/**
 *
 * @author dosen
 */
public final class DlgDokumenKlaim extends javax.swing.JDialog {

    private AntrianApi api = new AntrianApi();
    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps, ps2, ps3, pscaripiutang, psTele;
    private Properties prop = new Properties();
    private ResultSet rs, rsTele;
    private int pilihan = 0, i = 0, kuota = 0, jmlparsial = 0, noAkhir = 0, noAntrian = 0;
    private Date cal = new Date();
    private boolean ceksukses = false;
    private String tgl_pelayanan = "", jam_now = "", nm_dokter = "", datestring = "", jam_pelayanan = "", jam_pelayanan_save = "", antrian = "", kd_poli_antrian = "", no_antrian = "", link = "", requestJson, URL = "", username = "", nosisrute = "", aktifkanparsial = "no", BASENOREG = "",
            URUTNOREG = "", status = "Baru", order = "reg_periksa.tgl_registrasi,reg_periksa.jam_reg desc", alamatperujuk = "-", aktifjadwal = "", IPPRINTERTRACER = "", umur = "0", sttsumur = "Th",
            validasiregistrasi = Sequel.cariIsi("select wajib_closing_kasir from set_validasi_registrasi"),
            validasicatatan = Sequel.cariIsi("select tampilkan_catatan from set_validasi_catatan");

    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    Calendar cale = Calendar.getInstance();
    Locale lokal = null;
    String pola = "HH:mm:ss";
    Date waktuPermulaan = new Date();
    int jmlTambahanWaktu = 5;
    String satuan = "hari";
    public DlgDokumenKlaim(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        if (akses.getkode() == "Admin Utama") {
            username = akses.getkode();
        } else {
            username = Sequel.cariIsi("select nama from pegawai where nik=?", akses.getkode());
        }
        this.setLocation(8, 1);
        setSize(885, 674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "P", "No.Antrian", "No.Rawat", "Tanggal", "Jam", "Kd.Dokter", "Dokter Dituju", "Nomer RM",
            "Pasien", "J.K.", "Umur", "Poliklinik", "Jenis Bayar", "Penanggung Jawab", "Alamat P.J.", "Hubungan P.J.",
            "Biaya Regristrasi", "Status", "No.Telp", "Stts Rawat", "Stts Poli", "Kode Poli", "Kode PJ", "Kebutuhan Layanan", "No. Reg", "Asal Pasien", "No Kartu"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbPetugas.setModel(tabMode);

        tbPetugas.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbPetugas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 27; i++) {
            TableColumn column = tbPetugas.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(90);
            } else if (i == 2) {
                column.setPreferredWidth(120);
            } else if (i == 3) {
                column.setPreferredWidth(70);
            } else if (i == 4) {
                column.setPreferredWidth(50);
            } else if (i == 5) {
                column.setPreferredWidth(70);
            } else if (i == 6) {
                column.setPreferredWidth(200);
            } else if (i == 7) {
                column.setPreferredWidth(70);
            } else if (i == 8) {
                column.setPreferredWidth(200);
            } else if (i == 9) {
                column.setPreferredWidth(30);
            } else if (i == 10) {
                column.setPreferredWidth(50);
            } else if (i == 11) {
                column.setPreferredWidth(140);
            } else if (i == 12) {
                column.setPreferredWidth(140);
            } else if (i == 13) {
                column.setPreferredWidth(200);
            } else if (i == 14) {
                column.setPreferredWidth(140);
            } else if (i == 15) {
                column.setPreferredWidth(90);
            } else if (i == 16) {
                column.setPreferredWidth(100);
            } else if (i == 17) {
                column.setPreferredWidth(80);
            } else if (i == 18) {
                column.setPreferredWidth(100);
            } else if (i == 19) {
                column.setPreferredWidth(100);
            } else if (i == 20) {
                column.setPreferredWidth(100);
            } else if (i == 21) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 22) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 23) {
                column.setPreferredWidth(120);
            } else if (i == 25) {
                column.setPreferredWidth(90);
            } else if (i == 26) {
                column.setPreferredWidth(100);
            }
        }
        tbPetugas.setDefaultRenderer(Object.class, new TabelRegistrasiNew());

       
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }


        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));

            try {
                aktifjadwal = prop.getProperty("JADWALDOKTERDIREGISTRASI");
            } catch (Exception e) {
                aktifjadwal = "nonaktif";
            }

            try {
                IPPRINTERTRACER = prop.getProperty("IPPRINTERTRACER");
            } catch (Exception e) {
                IPPRINTERTRACER = "127.0.0.1";
            }

            try {
                URUTNOREG = prop.getProperty("URUTNOREG");
            } catch (Exception e) {
                URUTNOREG = "dokter";
            }

            try {
                BASENOREG = prop.getProperty("BASENOREG");
            } catch (Exception e) {
                BASENOREG = "registrasi";
            }

            try {
                aktifkanparsial = prop.getProperty("AKTIFKANBILLINGPARSIAL");
            } catch (Exception e) {
                aktifkanparsial = "no";
            }

            try {
                if (prop.getProperty("MENUTRANSPARAN").equals("yes")) {
                   
                }
            } catch (Exception e) {
                System.out.println("Notif Tansparant : " + e);
            }
        } catch (Exception ex) {
            System.out.println("Notif Load XML : " + ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnCari1 = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelGlass8 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrDokter = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        TabRawat1 = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbPetugas = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbPetugas1 = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        TabRawat2 = new javax.swing.JTabbedPane();
        Scroll3 = new widget.ScrollPane();
        tbPetugas3 = new widget.Table();
        Scroll4 = new widget.ScrollPane();
        tbPetugas4 = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Berkas Dokumen Klaim Pelayanan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica Neue", 0, 13), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(1300, 803));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
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
        panelGlass6.add(BtnSimpan);

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
        panelGlass6.add(BtnBatal);

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
        panelGlass6.add(BtnEdit);

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
        panelGlass6.add(BtnHapus);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass6.add(BtnAll);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inbox.png"))); // NOI18N
        BtnCari1.setMnemonic('7');
        BtnCari1.setText("Cetak Dokumen");
        BtnCari1.setToolTipText("Alt+7");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(150, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelGlass6.add(BtnCari1);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass6.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
        panelGlass6.add(LCount);

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
        panelGlass6.add(BtnKeluar);

        jPanel2.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-05-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-05-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(158, 23));
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass7.add(BtnCari);

        jPanel2.add(panelGlass7, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(jLabel14);

        CrDokter.setEditable(false);
        CrDokter.setName("CrDokter"); // NOI18N
        CrDokter.setPreferredSize(new java.awt.Dimension(300, 23));
        panelGlass8.add(CrDokter);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('6');
        BtnSeek3.setToolTipText("ALt+6");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnSeek3);

        jLabel16.setText("Unit :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(125, 23));
        panelGlass8.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(300, 23));
        panelGlass8.add(CrPoli);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnSeek4);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));
        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout());

        TabRawat1.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat1.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat1.setName("TabRawat1"); // NOI18N
        TabRawat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawat1MouseClicked(evt);
            }
        });

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPetugas.setAutoCreateRowSorter(true);
        tbPetugas.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbPetugas.setName("tbPetugas"); // NOI18N
        tbPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugasMouseClicked(evt);
            }
        });
        tbPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPetugasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPetugasKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbPetugas);

        TabRawat1.addTab("Rawat Jalan", Scroll);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPetugas1.setAutoCreateRowSorter(true);
        tbPetugas1.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbPetugas1.setName("tbPetugas1"); // NOI18N
        tbPetugas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugas1MouseClicked(evt);
            }
        });
        tbPetugas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPetugas1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPetugas1KeyReleased(evt);
            }
        });
        Scroll2.setViewportView(tbPetugas1);

        TabRawat1.addTab("Rawat Inap", Scroll2);

        internalFrame2.add(TabRawat1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Dokumen Belum Diajukan", internalFrame2);

        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        TabRawat2.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat2.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat2.setName("TabRawat2"); // NOI18N
        TabRawat2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawat2MouseClicked(evt);
            }
        });

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPetugas3.setAutoCreateRowSorter(true);
        tbPetugas3.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbPetugas3.setName("tbPetugas3"); // NOI18N
        tbPetugas3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugas3MouseClicked(evt);
            }
        });
        tbPetugas3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPetugas3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPetugas3KeyReleased(evt);
            }
        });
        Scroll3.setViewportView(tbPetugas3);

        TabRawat2.addTab("Rawat Jalan", Scroll3);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbPetugas4.setAutoCreateRowSorter(true);
        tbPetugas4.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbPetugas4.setName("tbPetugas4"); // NOI18N
        tbPetugas4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugas4MouseClicked(evt);
            }
        });
        tbPetugas4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPetugas4KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPetugas4KeyReleased(evt);
            }
        });
        Scroll4.setViewportView(tbPetugas4);

        TabRawat2.addTab("Rawat Inap", Scroll4);

        internalFrame3.add(TabRawat2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Proccess Data", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed

}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed

        for (i = 0; i < tbPetugas.getRowCount(); i++) {
            if (tbPetugas.getValueAt(i, 0).toString().equals("true")) {
                Sequel.meghapus("reg_periksa", "no_rawat", tbPetugas.getValueAt(i, 2).toString());
//                DeleteAktifitasUser();
                if (akses.getkode().equals("Admin Utama")) {
                    Sequel.meghapus("nota_inap", "no_rawat", tbPetugas.getValueAt(i, 2).toString());
                    Sequel.meghapus("nota_jalan", "no_rawat", tbPetugas.getValueAt(i, 2).toString());
//                    DeleteAktifitasUser();
                }
            }
        }

        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptReg.jasper", "report", "::[ Data Registrasi Periksa ]::", "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                    + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,ifnull((select perujuk from rujuk_masuk where rujuk_masuk.no_rawat=reg_periksa.no_rawat),'') as perujuk,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_tlp "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab  "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_poli=poliklinik.kd_poli  "
                    + "where poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.no_reg like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.no_rawat like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.tgl_registrasi like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.kd_dokter like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.stts_daftar like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and poliklinik.nm_poli like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.p_jawab like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.almt_pj like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and penjab.png_jawab like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.hubunganpj like '%" + TCari.getText().trim() + "%' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg desc", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        
        akses.setAktif(false);
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        CrPoli.setText("");
        CrDokter.setText("");
        TCari.setText("");
        if (TabRawat.getSelectedIndex() == 0) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampil2();
        }
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
       
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if (TabRawat.getSelectedIndex() == 0) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampil2();
        } else if (TabRawat.getSelectedIndex() == 2) {
          
        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugasMouseClicked
       
}//GEN-LAST:event_tbPetugasMouseClicked

    private void tbPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugasKeyPressed
        
}//GEN-LAST:event_tbPetugasKeyPressed

private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
    akses.setform("DlgReg");
    pilihan = 2;

}//GEN-LAST:event_BtnSeek3ActionPerformed

private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
    akses.setform("DlgReg");
    pilihan = 2;
    
}//GEN-LAST:event_BtnSeek4ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened


    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 0) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampil2();
        } else if (TabRawat.getSelectedIndex() == 2) {
           
        }

    }//GEN-LAST:event_TabRawatMouseClicked

    
    private void tbPetugasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugasKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPetugasKeyReleased

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
       
    }//GEN-LAST:event_BtnCari1ActionPerformed
    
    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void TabRawat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawat1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabRawat1MouseClicked

    private void tbPetugas1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugas1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPetugas1MouseClicked

    private void tbPetugas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugas1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPetugas1KeyPressed

    private void tbPetugas1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugas1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPetugas1KeyReleased

    private void tbPetugas3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugas3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPetugas3MouseClicked

    private void tbPetugas3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugas3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPetugas3KeyPressed

    private void tbPetugas3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugas3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPetugas3KeyReleased

    private void tbPetugas4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugas4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPetugas4MouseClicked

    private void tbPetugas4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugas4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPetugas4KeyPressed

    private void tbPetugas4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugas4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPetugas4KeyReleased

    private void TabRawat2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawat2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabRawat2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDokumenKlaim dialog = new DlgDokumenKlaim(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSimpan;
    private widget.TextBox CrDokter;
    private widget.TextBox CrPoli;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRawat1;
    private javax.swing.JTabbedPane TabRawat2;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel6;
    private javax.swing.JPanel jPanel2;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.Table tbPetugas;
    private widget.Table tbPetugas1;
    private widget.Table tbPetugas3;
    private widget.Table tbPetugas4;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                    + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_tlp,reg_periksa.stts,reg_periksa.status_poli,"
                    + "reg_periksa.kd_poli,reg_periksa.kd_pj,kebutuhan_pasien.kebutuhan,antrian_poli.no_antrian,antrian_poli.asaldaftar,antrian_poli.jam_pelayanan,pasien.no_peserta from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab join kebutuhan_pasien join antrian_poli "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli and reg_periksa.no_rawat=kebutuhan_pasien.no_rawat and reg_periksa.no_rawat=antrian_poli.no_rawat  where  "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_reg like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_rawat like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.tgl_registrasi like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.kd_dokter like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  dokter.nm_dokter like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_rkm_medis like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.stts_daftar like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  pasien.nm_pasien like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  poliklinik.nm_poli like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.p_jawab like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.almt_pj like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.hubunganpj like ? or "
                    + " LEFT(reg_periksa.no_rkm_medis,3)!='PNJ' and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.tgl_registrasi between ? and ? and  penjab.png_jawab like ? order by " + order);
            try {
                ps.setString(1, "%" + CrPoli.getText() + "%");
                ps.setString(2, "%" + CrDokter.getText() + "%");
                ps.setString(3, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(4, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + CrPoli.getText() + "%");
                ps.setString(7, "%" + CrDokter.getText() + "%");
                ps.setString(8, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(9, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(10, "%" + TCari.getText().trim() + "%");
                ps.setString(11, "%" + CrPoli.getText() + "%");
                ps.setString(12, "%" + CrDokter.getText() + "%");
                ps.setString(13, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(14, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                ps.setString(16, "%" + CrPoli.getText() + "%");
                ps.setString(17, "%" + CrDokter.getText() + "%");
                ps.setString(18, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(19, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(20, "%" + TCari.getText().trim() + "%");
                ps.setString(21, "%" + CrPoli.getText() + "%");
                ps.setString(22, "%" + CrDokter.getText() + "%");
                ps.setString(23, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(24, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(25, "%" + TCari.getText().trim() + "%");
                ps.setString(26, "%" + CrPoli.getText() + "%");
                ps.setString(27, "%" + CrDokter.getText() + "%");
                ps.setString(28, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(29, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(30, "%" + TCari.getText().trim() + "%");
                ps.setString(31, "%" + CrPoli.getText() + "%");
                ps.setString(32, "%" + CrDokter.getText() + "%");
                ps.setString(33, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(34, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(35, "%" + TCari.getText().trim() + "%");
                ps.setString(36, "%" + CrPoli.getText() + "%");
                ps.setString(37, "%" + CrDokter.getText() + "%");
                ps.setString(38, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(39, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(40, "%" + TCari.getText().trim() + "%");
                ps.setString(41, "%" + CrPoli.getText() + "%");
                ps.setString(42, "%" + CrDokter.getText() + "%");
                ps.setString(43, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(44, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(45, "%" + TCari.getText().trim() + "%");
                ps.setString(46, "%" + CrPoli.getText() + "%");
                ps.setString(47, "%" + CrDokter.getText() + "%");
                ps.setString(48, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(49, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(50, "%" + TCari.getText().trim() + "%");
                ps.setString(51, "%" + CrPoli.getText() + "%");
                ps.setString(52, "%" + CrDokter.getText() + "%");
                ps.setString(53, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(54, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(55, "%" + TCari.getText().trim() + "%");
                ps.setString(56, "%" + CrPoli.getText() + "%");
                ps.setString(57, "%" + CrDokter.getText() + "%");
                ps.setString(58, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(59, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(60, "%" + TCari.getText().trim() + "%");
                ps.setString(61, "%" + CrPoli.getText() + "%");
                ps.setString(62, "%" + CrDokter.getText() + "%");
                ps.setString(63, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(64, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(65, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{false, rs.getString("no_antrian"),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString("jam_pelayanan"),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(17),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        Valid.SetAngka(rs.getDouble(15)),
                        rs.getString(16),
                        rs.getString("no_tlp"),
                        rs.getString("stts"), rs.getString("status_poli"),
                        rs.getString("kd_poli"), rs.getString("kd_pj"), rs.getString("kebutuhan"), rs.getString(1), rs.getString("asaldaftar"), rs.getString("no_peserta")

                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

        LCount.setText("" + tabMode.getRowCount());
    }

    private void tampil2() {
        
    }
public void emptTeks() {
        
        alamatperujuk = "";

        if (akses.getkode() == "Admin Utama") {
            username = akses.getkode();
            CrDokter.setText("");
            BtnSeek3.setVisible(true);
            BtnAll.setVisible(true);
            CrPoli.setVisible(true);
            BtnSeek4.setVisible(true);
            jLabel16.setVisible(true);
        } else {
            nm_dokter = Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", akses.getkode());
            if (nm_dokter == "") {
                CrDokter.setText("");
                CrDokter.setEnabled(true);
                BtnSeek3.setVisible(true);
                BtnAll.setVisible(true);
                CrPoli.setVisible(true);
                BtnSeek4.setVisible(true);
                jLabel16.setVisible(true);
            } else {
                CrDokter.setText(nm_dokter);
                CrDokter.setEnabled(false);
                BtnSeek3.setVisible(false);
                BtnAll.setVisible(false);
                CrPoli.setVisible(false);
                BtnSeek4.setVisible(false);
                jLabel16.setVisible(false);
            }

        }
        tampil();
    }

}
