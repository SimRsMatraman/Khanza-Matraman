package inventory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;
import kepegawaian.DlgCariPegawai;

public class DlgEtter extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps, ps2, pscarikapasitas, psstok;
    private ResultSet rs, rs2, carikapasitas, rsstok;
    private int jml = 0, i = 0, row = 0, index = 0, min = 0, max = 0, row2, r, z=0;
    private String[] jumlah, stokasal, kodebarang, namabarang, satuan, aturan, letak, norw;
    private double[] j, sebelum, harga, harga_b, harga_k, harga_r, harga_l, etter, resep;
    private WarnaTable2 warna = new WarnaTable2();
    private riwayatobat Trackobat=new riwayatobat();
    private DlgPindahGudang pindah=new DlgPindahGudang(null,false);
    private DlgCariPegawai pegawai = new DlgCariPegawai(null, false);
    public DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariBangsal caribangsal = new DlgCariBangsal(null, false);
    private DlgCariPermintaan form = new DlgCariPermintaan(null, false);
    private DlgBarang barang = new DlgBarang(null, false);
    private boolean sukses = true;
    public boolean tampilkanpermintaan=false;
    private File file;
    private FileWriter fileWriter;
    private String iyem, DEPOAKTIFOBAT = "",STOKKOSONGRESEP="no",nomorpermintaan="",signa1="1",signa2="1",kodedokter="",namadokter="",noresep="",norawat="";
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private String qrystok = "", aktifkanbatch = "no", hppfarmasi = "",bangsal="",bangsaldefault=Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi limit 1"),tampilkan_ppnobat_ralan="",VALIDASIULANGBERIOBAT="";
    private double ttltotaljual = 0, totaljual = 0, jumlahjual = 0, ttltotalbeli = 0, totalbeli = 0, jumlahbeli = 0, jumlahbeli1 = 0, jumlahbeli2 = 0, jumlahbeli3 = 0, totalbeli1 = 0, totalbeli2 = 0, totalbeli3 = 0,
            ttltotalpesan = 0, totalpesan = 0, jumlahpesan = 0, jumlahutd, totalutd, ttltotalutd, jumlahkeluar, totalkeluar, ttltotalkeluar,
            ttltotalpiutang = 0, totalpiutang = 0, jumlahpiutang = 0, ttltotalretbeli = 0, totalretbeli = 0, jumlahretbeli = 0,
            ttltotalretjual = 0, totalretjual = 0, jumlahretjual = 0, ttltotalretpiut = 0, totalretpiut = 0, jumlahretpiut = 0,
            jumlahpasin = 0, totalpasien = 0, ttltotalpasien = 0, stok = 0, aset = 0, ttlaset = 0, jumlahrespulang = 0, totalrespulang = 0,
            ttltotalrespulang = 0, jumlahmutasimasuk = 0, jumlahmutasikeluar = 0, totalmutasimasuk = 0, totalmutasikeluar = 0,
            ttltotalmutasimasuk = 0, ttltotalmutasikeluar = 0, jumlahhibah = 0, totalhibah = 0, ttltotalhibah = 0, h_belicari=0, hargacari=0, sisacari=0,x=0,y=0,embalase=Sequel.cariIsiAngka("select set_embalase.embalase_per_obat from set_embalase"),
            tuslah=Sequel.cariIsiAngka("select set_embalase.tuslah_per_obat from set_embalase"),kenaikan=0,stokbarang=0,ttl=0,ppnobat=0,ttlhpp,ttljual;

    /**
     * Creates new form DlgProgramStudi
     *
     * @param parent
     * @param modal
     */
    public DlgEtter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] judul = {"Jml", "Sebelum", "Kode Barang", "Nama Barang", "Aturan", "Satuan", "Letak Barang", "Stok", "Harga", "Harga Beli", "Kayrawan", "ralan", "Luar", "Jumlah Iterasi", "Resep di Iter", "No. Rawat"};
        tabMode = new DefaultTableModel(null, judul) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0)) {
                    a = true;
                }

                return a;
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(100);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(200);
            } else if (i == 5) {
                column.setPreferredWidth(70);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(60);
            } else if (i == 8) {
                column.setPreferredWidth(60);
            } else if (i == 9) {
                column.setPreferredWidth(60);
            } else if (i == 10) {
                column.setPreferredWidth(60);
            } else if (i == 11) {
                column.setPreferredWidth(60);
            } else if (i == 12) {
                column.setPreferredWidth(60);
            } else if (i == 13) {
                column.setPreferredWidth(100);
            } else if (i == 14) {
                column.setPreferredWidth(110);
            } else if (i == 15) {
                column.setPreferredWidth(120);
            }
        }
        warna.kolom = 0;
        tbDokter.setDefaultRenderer(Object.class, warna);

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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){                   
                    KdDokter.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                }            
                KdDokter.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){        
                     KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                     NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }  
                KdDokter.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        caribangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(caribangsal.getTable().getSelectedRow()!= -1){                   
                    kdgudang.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(),0).toString());
                    nmgudang.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(),1).toString());
                } 
                kdgudang.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        try {
            DEPOAKTIFOBAT = koneksiDB.DEPOAKTIFOBAT();
            STOKKOSONGRESEP = koneksiDB.STOKKOSONGRESEP();
        } catch (Exception e) {
            System.out.println("E : " + e);
            DEPOAKTIFOBAT = "";
            STOKKOSONGRESEP="no";
        }
        
        tampilkan_ppnobat_ralan=Sequel.cariIsi("select set_nota.tampilkan_ppnobat_ralan from set_nota"); 
        jam();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok1 = new javax.swing.JMenuItem();
        jLabel7 = new widget.Label();
        jLabel6 = new widget.Label();
        LTotalTagihan = new widget.Label();
        LPpn = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi3 = new widget.panelisi();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        btnDokter = new widget.Button();
        NmDokter = new widget.TextBox();
        KdDokter = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel8 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        LTotal = new widget.Label();
        jLabel5 = new widget.Label();
        label21 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();
        Jeniskelas = new widget.ComboBox();
        label12 = new widget.Label();
        ChkNoResep = new widget.CekBox();
        NoResep = new widget.TextBox();
        ChkRM = new widget.CekBox();
        jLabel11 = new widget.Label();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(180, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppStok1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok1.setForeground(new java.awt.Color(50, 50, 50));
        ppStok1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok1.setText("Cek Stok Lokasi");
        ppStok1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok1.setName("ppStok1"); // NOI18N
        ppStok1.setPreferredSize(new java.awt.Dimension(180, 25));
        ppStok1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStok1ActionPerformed(evt);
            }
        });
        Popup.add(ppStok1);

        jLabel7.setText("Total+PPN :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));

        jLabel6.setText("PPN :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(35, 23));

        LTotalTagihan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotalTagihan.setText("0");
        LTotalTagihan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotalTagihan.setName("LTotalTagihan"); // NOI18N
        LTotalTagihan.setPreferredSize(new java.awt.Dimension(80, 23));

        LPpn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPpn.setText("0");
        LPpn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPpn.setName("LPpn"); // NOI18N
        LPpn.setPreferredSize(new java.awt.Dimension(65, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pemberian Obat, Alkes & BHP Medis Iterasi 7/23 ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

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
        tbDokter.setToolTipText("Masukkan jumlah pengajuan di ujung paling kiri pada warna biru kemudian geser kanan");
        tbDokter.setComponentPopupMenu(Popup);
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
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnSimpan);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(265, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi1.add(BtnCari1);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('C');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+C");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelisi1.add(BtnCari);

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
        panelisi1.add(BtnKeluar);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 170));
        panelisi3.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelisi3.add(jLabel3);
        jLabel3.setBounds(0, 12, 72, 23);

        TNoRw.setName("TNoRw"); // NOI18N
        panelisi3.add(TNoRw);
        TNoRw.setBounds(81, 10, 170, 24);

        TNoRM.setEditable(false);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(TNoRM);
        TNoRM.setBounds(256, 10, 90, 23);

        TPasien.setEditable(false);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(TPasien);
        TPasien.setBounds(355, 10, 330, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('3');
        btnDokter.setToolTipText("Alt+3");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        panelisi3.add(btnDokter);
        btnDokter.setBounds(430, 40, 28, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        panelisi3.add(NmDokter);
        NmDokter.setBounds(200, 40, 230, 23);

        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        panelisi3.add(KdDokter);
        KdDokter.setBounds(80, 40, 120, 23);

        jLabel15.setText("Peresep :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelisi3.add(jLabel15);
        jLabel15.setBounds(0, 40, 72, 23);

        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(jLabel8);
        jLabel8.setBounds(0, 80, 65, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-03-2025" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        panelisi3.add(DTPTgl);
        DTPTgl.setBounds(70, 80, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        panelisi3.add(cmbJam);
        cmbJam.setBounds(160, 80, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        panelisi3.add(cmbMnt);
        cmbMnt.setBounds(230, 80, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        panelisi3.add(cmbDtk);
        cmbDtk.setBounds(290, 80, 62, 23);

        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        panelisi3.add(ChkJln);
        ChkJln.setBounds(360, 80, 22, 23);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(LTotal);
        LTotal.setBounds(70, 110, 200, 23);

        jLabel5.setText("Total :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi3.add(jLabel5);
        jLabel5.setBounds(0, 110, 65, 23);

        label21.setText("Depo :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label21);
        label21.setBounds(300, 110, 50, 23);

        kdgudang.setEditable(false);
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(360, 110, 55, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(420, 110, 150, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        panelisi3.add(BtnGudang);
        BtnGudang.setBounds(570, 110, 28, 23);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rawat Jalan", "Beli Luar", "Karyawan", "Utama/BPJS" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(100, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        Jeniskelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JeniskelasKeyPressed(evt);
            }
        });
        panelisi3.add(Jeniskelas);
        Jeniskelas.setBounds(440, 80, 150, 23);

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label12);
        label12.setBounds(390, 80, 50, 23);

        ChkNoResep.setSelected(true);
        ChkNoResep.setText("No.Resep   ");
        ChkNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkNoResep.setName("ChkNoResep"); // NOI18N
        ChkNoResep.setOpaque(false);
        ChkNoResep.setPreferredSize(new java.awt.Dimension(85, 23));
        ChkNoResep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkNoResepItemStateChanged(evt);
            }
        });
        panelisi3.add(ChkNoResep);
        ChkNoResep.setBounds(600, 80, 100, 23);

        NoResep.setName("NoResep"); // NOI18N
        NoResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepKeyPressed(evt);
            }
        });
        panelisi3.add(NoResep);
        NoResep.setBounds(530, 40, 130, 23);

        ChkRM.setSelected(true);
        ChkRM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRM.setName("ChkRM"); // NOI18N
        ChkRM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRMItemStateChanged(evt);
            }
        });
        ChkRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkRMActionPerformed(evt);
            }
        });
        panelisi3.add(ChkRM);
        ChkRM.setBounds(670, 40, 23, 23);

        jLabel11.setText("No.Resep :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelisi3.add(jLabel11);
        jLabel11.setBounds(460, 40, 70, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        pindah.tampil(" order by mutasibarang.tanggal");
        pindah.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pindah.setLocationRelativeTo(internalFrame1);
        pindah.setAlwaysOnTop(false);
        pindah.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        form.dispose();
        pegawai.dispose();
        caribangsal.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnSimpan, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed
    /*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
     jml = 0;
        for (i = 0; i < tbDokter.getRowCount(); i++) {
            if (!tbDokter.getValueAt(i, 0).toString().equals("")) {
                jml++;
            }
        }
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"No Rm Kosong");
        }else if(NoResep.getText().trim().equals("")){
            Valid.textKosong(NoResep,"No Resep Kosong");
        } else if (jml <= 0) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan masukkan permintaan...!!!!");
            tbDokter.requestFocus();
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses = true;
                if (Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?,?,?","Nomer Resep",12,new String[]{
                        NoResep.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        "ralan",Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),"","Umum"
                }) == true) {
                    jml = tbDokter.getRowCount();
                    for (i = 0; i < jml; i++) {
                        try {
                            if (Valid.SetAngka(tbDokter.getValueAt(i, 0).toString()) > 0) {
                                if (Sequel.menyimpantf2("detail_pemberian_obat","?,?,?,?,?,?,?,?,?,?,?,?,?,?","data",14,new String[]{
                                            Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),tbDokter.getValueAt(i,2).toString(),tbDokter.getValueAt(i,8).toString(),
                                            tbDokter.getValueAt(i,8).toString(),""+(Double.parseDouble(tbDokter.getValueAt(i,0).toString())),
                                            "0","0",""+Math.round(Double.parseDouble("0")+
                                                Double.parseDouble("0")+(Double.parseDouble(tbDokter.getValueAt(i,8).toString())*
                                                (Double.parseDouble(tbDokter.getValueAt(i,0).toString())))),
                                            "Ralan",kdgudang.getText(),"",""
                                })==true){
                                            ttljual=ttljual+Math.round(Double.parseDouble("0")+
                                                    Double.parseDouble("0")+(Double.parseDouble(tbDokter.getValueAt(i,8).toString())*
                                                            (Double.parseDouble(tbDokter.getValueAt(i,0).toString()))));
                                            ttlhpp=ttlhpp+Math.round(Double.parseDouble(tbDokter.getValueAt(i,8).toString())*
                                                            (Double.parseDouble(tbDokter.getValueAt(i,0).toString())));
                                            if(!tbDokter.getValueAt(i,4).toString().equals("")){
                                                Sequel.menyimpan("aturan_pakai","?,?,?,?,?",5,new String[]{
                                                    Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),tbDokter.getValueAt(i,2).toString(),tbDokter.getValueAt(i,4).toString()
                                                });  
                                            }                                            
                                                  
                                            if(aktifkanbatch.equals("yes")){
                                                Sequel.mengedit("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                                    "","",tbDokter.getValueAt(i,2).toString(),""
                                                });
                                                Trackobat.catatRiwayat(tbDokter.getValueAt(i,2).toString(),0,(Double.parseDouble(tbDokter.getValueAt(i,0).toString())),"Pemberian Obat",akses.getkode(),kdgudang.getText(),"Simpan","","",TNoRw.getText()+" "+TNoRM.getText()+" "+TPasien.getText());
                                                Sequel.menyimpan("gudangbarang","'"+tbDokter.getValueAt(i,2).toString()+"','"+kdgudang.getText()+"','-"+(Double.parseDouble(tbDokter.getValueAt(i,0).toString()))+"','',''", 
                                                    "stok=stok-'"+(Double.parseDouble(tbDokter.getValueAt(i,0).toString()))+"'","kode_brng='"+tbDokter.getValueAt(i,2).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='' and no_faktur=''");
                                            }else{
                                                Trackobat.catatRiwayat(tbDokter.getValueAt(i,2).toString(),0,(Double.parseDouble(tbDokter.getValueAt(i,0).toString())),"Pemberian Obat",akses.getkode(),kdgudang.getText(),"Simpan","","",TNoRw.getText()+" "+TNoRM.getText()+" "+TPasien.getText());
                                                Sequel.menyimpan("gudangbarang","'"+tbDokter.getValueAt(i,2).toString()+"','"+kdgudang.getText()+"','-"+(Double.parseDouble(tbDokter.getValueAt(i,0).toString()))+"','',''", 
                                                    "stok=stok-'"+(Double.parseDouble(tbDokter.getValueAt(i,0).toString()))+"'","kode_brng='"+tbDokter.getValueAt(i,2).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='' and no_faktur=''"); 
                                            }
                                            
                                            if(Valid.SetAngka(tbDokter.getValueAt(i, 14).toString()) > 0){
                                                Sequel.mengedit("resep_dokter","no_resep=? and kode_brng=? and aturan_pakai=?","etter=etter-1",3,new String[]{
                                                tbDokter.getValueAt(i,14).toString(),tbDokter.getValueAt(i,2).toString(),tbDokter.getValueAt(i,4).toString()
//                                                        ,"'"+(Double.parseDouble(tbDokter.getValueAt(i,13).toString()))+"'-1"
                                                });  
                                            }
                                            
                                            if(!NoResep.getText().equals("")){
                                                Sequel.menyimpan("resep_dokter","?,?,?,?,?,?","data",6,new String[]{
                                                NoResep.getText(),tbDokter.getValueAt(i,2).toString(),
                                                ""+(Double.parseDouble(tbDokter.getValueAt(i,0).toString())),
                                                tbDokter.getValueAt(i,4).toString(),"false",""
                                                });  
                                            }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        }
                    } 
                }
                if(sukses==true){
                        if(ChkNoResep.isSelected()==true){
                            DlgResepObat resep=new DlgResepObat(null,false);
                            resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                            resep.setLocationRelativeTo(internalFrame1);
                            resep.emptTeks(); 
                            resep.isCek();
                            if(!namadokter.equals("")){
                                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),kodedokter,namadokter,"ralan");
                            }else{
                                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),"ralan");
                                resep.setDokterRalan();
                            }
                            resep.tampil();
                            resep.setVisible(true);
                        }
                        dispose();
                    } else {
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
//                autoNomor();
            }
        } 
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void getData() {
        int row = tbDokter.getSelectedRow();
        if (row != -1) {
//            KdBarang.setText(tbDokter.getValueAt(row, 1).toString());
//            StokMin.setText(tbDokter.getValueAt(row, 7).toString());
//            StokMax.setText(tbDokter.getValueAt(row, 8).toString());
        }
    }

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnKeluar, TCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        tampil();
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        BtnCari1.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
        tbDokter.requestFocus();
    }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
    tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        tampil();
    } else {
        Valid.pindah(evt, BtnSimpan, BtnKeluar);
    }
}//GEN-LAST:event_BtnCari1KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    for (i = 0; i < tbDokter.getRowCount(); i++) {
        tbDokter.setValueAt("", i, 0);
        tbDokter.setValueAt("", i, 9);
        tbDokter.setValueAt("", i, 10);
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
    if(tbDokter.getSelectedRow()!= 0){
        try {
            getData();
            getCekStok();
        } catch (java.lang.NullPointerException e) {
        }
    }
}//GEN-LAST:event_tbDokterMouseClicked

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
    if (tbDokter.getRowCount() != 0) {
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
            hitungObat();
            getCekStok();
        } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            i = tbDokter.getSelectedRow();
            if (i != -1) {
                tbDokter.setValueAt("", i, 0);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
            TCari.setText("");
            TCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            i = tbDokter.getSelectedRow();
            if (i != -1) {
                tbDokter.setValueAt("", i, 0);
            }
        } else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
           hitungObat();  
           getCekStok();
        }
    }
}//GEN-LAST:event_tbDokterKeyPressed

    private void tbDokterKeyReleased(java.awt.event.KeyEvent evt) {
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }
    
    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {                                       
        if(this.isVisible()==true){
          hitungObat();
          getCekStok();  
        }
    }

    public void keyReleased(java.awt.event.KeyEvent evt) {
        tbDokterKeyReleased(evt);
    }
    
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        tbDokterPropertyChange(evt);
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(tampilkanpermintaan==true){
            tampil();
            emptTeksobat();
        }   

    }//GEN-LAST:event_formWindowOpened

    private void ppStok1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCekStok ceksetok = new DlgCekStok(null, false);
        ceksetok.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ceksetok.setLocationRelativeTo(internalFrame1);
        ceksetok.setAlwaysOnTop(false);
        ceksetok.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppStok1ActionPerformed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.isCek();
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        Valid.pindah(evt,KdDokter,BtnSimpan);
    }//GEN-LAST:event_btnDokterKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        //        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            //            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            //        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            //            btnDokterActionPerformed(null);
            //        }else{
            //            Valid.pindah(evt,NoResep,BtnSimpan);
            //        }
    }//GEN-LAST:event_KdDokterKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,BtnKeluar,cmbJam);
    }//GEN-LAST:event_DTPTglKeyPressed

    private void DTPTglItemStateChanged(java.awt.event.ItemEvent evt) {                                         
        try {
            emptTeksobat();
        } catch (Exception e) {
        }
            
    }
    
    public void itemStateChanged(java.awt.event.ItemEvent evt) {
        DTPTglItemStateChanged(evt);
    }
    
    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,Jeniskelas);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_UP:
            TCari.requestFocus();
            break;
            case KeyEvent.VK_ENTER:
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            BtnSimpan.requestFocus();
            break;
            case KeyEvent.VK_UP:
            BtnGudangActionPerformed(null);
            break;
        }
    }//GEN-LAST:event_kdgudangKeyPressed

    private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
        caribangsal.isCek();
        caribangsal.emptTeks();
        caribangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        caribangsal.setLocationRelativeTo(internalFrame1);
        caribangsal.setAlwaysOnTop(false);
        caribangsal.setVisible(true);
    }//GEN-LAST:event_BtnGudangActionPerformed

    private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
  
    }//GEN-LAST:event_JeniskelasItemStateChanged

    private void JeniskelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JeniskelasKeyPressed
        Valid.pindah(evt, TCari,BtnKeluar);
    }//GEN-LAST:event_JeniskelasKeyPressed

    private void ChkNoResepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkNoResepItemStateChanged
        if(ChkNoResep.isSelected()==true){
            DlgResepObat resep=new DlgResepObat(null,false);
            resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resep.setLocationRelativeTo(internalFrame1);
            resep.emptTeks();
            resep.isCek();
            if(!namadokter.equals("")){
                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),kodedokter,namadokter,"ralan");
            }else{
                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),"ralan");
                resep.setDokterRalan();
            }
            resep.tampil();
            resep.setVisible(true);
        }
    }//GEN-LAST:event_ChkNoResepItemStateChanged

    private void NoResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepKeyPressed
        Valid.pindah(evt,cmbDtk,KdDokter);
    }//GEN-LAST:event_NoResepKeyPressed

    private void ChkRMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRMItemStateChanged
        if(ChkRM.isSelected()==true){
            NoResep.setEditable(false);
            NoResep.setBackground(new Color(245,250,240));
            emptTeksobat();
        }else if(ChkRM.isSelected()==false){
            NoResep.setEditable(true);
            NoResep.setBackground(new Color(250,255,245));
            NoResep.setText("");
        }
    }//GEN-LAST:event_ChkRMItemStateChanged

    public void emptTeksobat() {
        if(ChkRM.isSelected()==true){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(resep_obat.no_resep,4),signed)),0) from resep_obat where resep_obat.tgl_peresepan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' or resep_obat.tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' ",
                DTPTgl.getSelectedItem().toString().substring(6,10)+DTPTgl.getSelectedItem().toString().substring(3,5)+DTPTgl.getSelectedItem().toString().substring(0,2),4,NoResep);        
        } 
    }
    
    private void ChkRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRMActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPermintaan dialog = new DlgPermintaan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnGudang;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkNoResep;
    private widget.CekBox ChkRM;
    private widget.Tanggal DTPTgl;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox KdDokter;
    private widget.Label LPpn;
    private widget.Label LTotal;
    private widget.Label LTotalTagihan;
    private widget.TextBox NmDokter;
    private widget.TextBox NoResep;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Button btnDokter;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel15;
    private widget.Label jLabel3;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.TextBox kdgudang;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label21;
    private widget.TextBox nmgudang;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok1;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void setNoPermintaan(String nopermintaan) {
        tampil();
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "SELECT " +
                    "pasien.nm_pasien " +
                    "from reg_periksa " +
                    "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis " +
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
        
    }

    private void tampil() {
      z=0;
        for(i=0;i<tbDokter.getRowCount();i++){
            if(!tbDokter.getValueAt(i,0).toString().equals("")){
                z++;
            }
        }    
        
        j=null;
        j=new double[z];
        sebelum=null;
        sebelum=new double[z];
        kodebarang=null;
        kodebarang=new String[z];
        namabarang=null;
        namabarang=new String[z];
        aturan=null;
        aturan=new String[z];
        satuan=null;
        satuan=new String[z];
        letak=null;
        letak=new String[z];
        stokasal=null;
        stokasal=new String[z];                   
        harga=null;
        harga=new double[z];           
        harga_b=null;
        harga_b=new double[z];         
        harga_k=null;
        harga_k=new double[z];
        harga_r=null;
        harga_r=new double[z]; 
        harga_l=null;
        harga_l=new double[z];  
        etter=null;
        etter=new double[z];  
        resep=null;
        resep=new double[z];
        norw=null;
        norw=new String[z];   
        z=0;        
        for(i=0;i<tbDokter.getRowCount();i++){
            if(!tbDokter.getValueAt(i,0).toString().equals("")){
               try {
                    j[z]=Double.parseDouble(tbDokter.getValueAt(i,0).toString());
                } catch (Exception e) {
                    j[z]=0;
                } 
                sebelum[z]=Double.parseDouble(tbDokter.getValueAt(i,1).toString());
                kodebarang[z]=tbDokter.getValueAt(i,2).toString();
                namabarang[z]=tbDokter.getValueAt(i,3).toString();
                aturan[z]=tbDokter.getValueAt(i,4).toString();
                satuan[z]=tbDokter.getValueAt(i,5).toString();
                letak[z]=tbDokter.getValueAt(i,6).toString();
                stokasal[z]=tbDokter.getValueAt(i,7).toString();
                harga[z]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                harga_b[z]=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                harga_k[z]=Double.parseDouble(tbDokter.getValueAt(i,10).toString());
                harga_r[z]=Double.parseDouble(tbDokter.getValueAt(i,11).toString());
                harga_l[z]=Double.parseDouble(tbDokter.getValueAt(i,12).toString());
                etter[z]=Double.parseDouble(tbDokter.getValueAt(i,13).toString());
                resep[z]=Double.parseDouble(tbDokter.getValueAt(i,14).toString());
                norw[z]=tbDokter.getValueAt(i,15).toString();
                z++;
            }
        }
        
        Valid.tabelKosong(tabMode);
        
        for(i=0;i<z;i++){
            tabMode.addRow(new Object[] {
                j[i],sebelum[i],kodebarang[i],namabarang[i],aturan[i],satuan[i],letak[i],stokasal[i],
                harga[i],harga_b[i],harga_k[i],harga_r[i],harga_l[i],etter[i],resep[i],norw[i]
            });
        }
        try {
            if(TCari.getText().trim().equals("")){
                norawat = TNoRM.getText().trim();
            ps = koneksi.prepareStatement(
                "select " +
                "resep_dokter.jml, " +
                "resep_dokter.kode_brng, " +
                "databarang.nama_brng, " +
                "resep_dokter.aturan_pakai, " +
                "kodesatuan.satuan, " +
                "databarang.letak_barang, " +
                "databarang.dasar, " +
                "databarang.h_beli, " +
                "databarang.karyawan, " +
                "databarang.ralan, " +
                "databarang.beliluar, " +
                "databarang.utama, " +
                "resep_dokter.etter, " +
                "resep_dokter.no_resep, " +
                "reg_periksa.no_rawat " +
                "from resep_dokter " +
                "inner join resep_obat on resep_obat.no_resep=resep_dokter.no_resep " +
                "inner join reg_periksa on reg_periksa.no_rawat=resep_obat.no_rawat " +
                "inner join databarang on databarang.kode_brng=resep_dokter.kode_brng " +
                "inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat " +
                "inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng " +
                "where reg_periksa.no_rkm_medis=? and resep_dokter.ulang='true' and gudangbarang.stok>'0' and resep_dokter.etter='3' or " +
                "reg_periksa.no_rkm_medis="+norawat+" and resep_dokter.ulang='true' and gudangbarang.stok>'0' and resep_dokter.etter='2' or " +
                "reg_periksa.no_rkm_medis="+norawat+" and resep_dokter.ulang='true' and gudangbarang.stok>'0' and resep_dokter.etter='1' " +
                "group by databarang.nama_brng order by resep_obat.tgl_perawatan");

            }else{
            ps = koneksi.prepareStatement(
                "select " +
                "resep_dokter.jml, " +
                "resep_dokter.kode_brng, " +
                "databarang.nama_brng, " +
                "resep_dokter.aturan_pakai, " +
                "kodesatuan.satuan, " +
                "databarang.letak_barang, " +
                "databarang.dasar, " +
                "databarang.h_beli, " +
                "databarang.karyawan, " +
                "databarang.ralan, " +
                "databarang.beliluar, " +
                "databarang.utama, " +
                "resep_dokter.etter, " +
                "resep_dokter.no_resep, " +
                "reg_periksa.no_rawat " +
                "from resep_dokter " +
                "inner join resep_obat on resep_obat.no_resep=resep_dokter.no_resep " +
                "inner join reg_periksa on reg_periksa.no_rawat=resep_obat.no_rawat " +
                "inner join databarang on databarang.kode_brng=resep_dokter.kode_brng " +
                "inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat " +
                "inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng " +
                "where resep_dokter.ulang='true' and databarang.kode_brng like ? and gudangbarang.stok>'0' or " +
                "resep_dokter.ulang='true' and databarang.nama_brng like ? and gudangbarang.stok>'0' or " +
                "resep_dokter.ulang='true' and reg_periksa.no_rkm_medis=? and gudangbarang.stok>'0' " +
                "group by databarang.nama_brng order by resep_obat.tgl_perawatan");    
            }
            try {
                if(!TCari.getText().trim().equals("")){
                    ps.setString(1, "%" + TCari.getText().trim() + "%");
                    ps.setString(2, "%" + TCari.getText().trim() + "%");
                    ps.setString(3, "%" + TNoRM.getText().trim() + "%"); 
                }
                ttltotaljual = 0;
                ttltotalbeli = 0;
                ttltotalpesan = 0;
                ttltotalpiutang = 0;
                ttltotalretbeli = 0;
                ttltotalretjual = 0;
                ttltotalretpiut = 0;
                ttltotalpasien = 0;
                ttlaset = 0;
                ttltotalutd = 0;
                ttltotalkeluar = 0;
                ttltotalrespulang = 0;
                ttltotalmutasikeluar = 0;
                ttltotalmutasimasuk = 0;
                ttltotalhibah = 0;

                ps.setString(1, TNoRM.getText().trim());
                rs = ps.executeQuery();
                if (kdgudang.equals("yes")) {
                    qrystok = "select gudangbarang.stok "
                            + "from gudangbarang where gudangbarang.kode_brng=?";
                } else {
                    qrystok = "select gudangbarang.stok "
                            + "from gudangbarang where gudangbarang.kode_brng=? and gudangbarang.kd_bangsal=?";
                }
                while (rs.next()) {
                    totaljual = 0;
                    jumlahjual = 0;
                    totalbeli = 0;
                    jumlahbeli = 0;
                    totalpiutang = 0;
                    jumlahpiutang = 0;
                    totalpesan = 0;
                    jumlahpesan = 0;
                    jumlahrespulang = 0;
                    jumlahhibah = 0;
                    totalhibah = 0;
                    totalretbeli = 0;
                    jumlahretbeli = 0;
                    totalretjual = 0;
                    jumlahretjual = 0;
                    totalretpiut = 0;
                    jumlahretpiut = 0;
                    jumlahpasin = 0;
                    stok = 0;
                    aset = 0;
                    totalrespulang = 0;
                    jumlahutd = 0;
                    jumlahkeluar = 0;
                    totalkeluar = 0;
                    totalutd = 0;
                    jumlahmutasikeluar = 0;
                    totalmutasikeluar = 0;
                    jumlahmutasimasuk = 0;
                    totalmutasimasuk = 0;

                    ps2 = koneksi.prepareStatement(qrystok);
                    try {
                        ps2.setString(1, rs.getString(2));
                        ps2.setString(2, kdgudang.getText().trim());
                        rs2 = ps2.executeQuery();
                        if (rs2.next()) {
//                            stok = rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Stok : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                    
                    //Permintaan Stok  
                    ps2 = koneksi.prepareStatement(
                            "select gudangbarang.stok "
                            + "from gudangbarang where gudangbarang.kode_brng=? and gudangbarang.kd_bangsal=?");
                    try {
                        ps2.setString(1, rs.getString(2));
                        ps2.setString(2, kdgudang.getText().trim());
                        rs2 = ps2.executeQuery();
                        if (rs2.next()) {

                            stok = rs2.getDouble(1);
                        }
                    
                    tabMode.addRow(new Object[]{
                        "",rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs2.getString(1), rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), 
                        rs.getString(13)+" X Etter Lagi", rs.getString(14), rs.getString(15)
                    });
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                 }
            } catch (Exception e) {
                System.out.println("Notifikasi Data Barang : " + e);
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
//        LCount.setText(""+tabMode.getRowCount());
    }

    public void isCek() {
        if(!DEPOAKTIFOBAT.equals("")){
            kdgudang.setText(DEPOAKTIFOBAT);
            nmgudang.setText(caribangsal.tampil3(DEPOAKTIFOBAT));
        }else{
            bangsal=Sequel.cariIsi("select set_depo_ralan.kd_bangsal from set_depo_ralan where set_depo_ralan.kd_poli=?",Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
            if(bangsal.equals("")){
                bangsal=bangsaldefault;
            }     
            kdgudang.setText(bangsal);
            nmgudang.setText(caribangsal.tampil3(kdgudang.getText()));
        }
        tampil();
                    
        BtnSimpan.setEnabled(akses.getobat());
        TCari.requestFocus();
        BtnGudang.setEnabled(akses.getakses_depo_obat());
    }
    
    public void setNoRm(String norwt,Date tanggal, String jam,String menit,String detik,String KodeDokter,String NamaDokter,String status) {        
        TNoRw.setText(norwt);
        Sequel.cariIsi("select concat(pasien.nm_pasien,' (',pasien.umur,')') from reg_periksa inner join pasien "+
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=? ",TPasien,TNoRw.getText());
        Sequel.cariIsi("select pasien.no_rkm_medis from reg_periksa inner join pasien "+
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=? ",TNoRM,TNoRw.getText());
        DTPTgl.setDate(tanggal);
        cmbJam.setSelectedItem(jam);
        cmbMnt.setSelectedItem(menit);
        cmbDtk.setSelectedItem(detik); 
        KdDokter.setText(KodeDokter);
        NmDokter.setText(NamaDokter);
        if(ChkRM.isSelected()==true){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(resep_obat.no_resep,4),signed)),0) from resep_obat where resep_obat.tgl_peresepan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' or resep_obat.tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' ",
                DTPTgl.getSelectedItem().toString().substring(6,10)+DTPTgl.getSelectedItem().toString().substring(3,5)+DTPTgl.getSelectedItem().toString().substring(0,2),4,NoResep);        
        }
//        KdPj.setText(Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",norwt));
        TCari.requestFocus();
    }
    
    public void setNoRm(String norwt,String KodeDokter,String NamaDokter,String Pasien,String kodepj,String status) {        
        TNoRw.setText(norwt);
        TPasien.setText(Pasien);
        KdDokter.setText(KodeDokter);
        NmDokter.setText(NamaDokter);
//        KdPj.setText(kodepj);
        TCari.requestFocus();
    }
    
    public void setNoRm(String norwt,Date tanggal,String status) {        
        TNoRw.setText(norwt);
        Sequel.cariIsi("select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=? ",TPasien,TNoRw.getText());
        
        DTPTgl.setDate(tanggal);
        KdDokter.setText(Sequel.cariIsi("select dpjp_ranap.kd_dokter from dpjp_ranap where dpjp_ranap.no_rawat=?",norwt));
        if(KdDokter.getText().equals("")){
            KdDokter.setText(Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",norwt));
        }
        NmDokter.setText(dokter.tampil3(KdDokter.getText()));
        if(ChkRM.isSelected()==true){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(resep_obat.no_resep,4),signed)),0) from resep_obat where resep_obat.tgl_peresepan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' or resep_obat.tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' ",
                DTPTgl.getSelectedItem().toString().substring(6,10)+DTPTgl.getSelectedItem().toString().substring(3,5)+DTPTgl.getSelectedItem().toString().substring(0,2),4,NoResep);        
        }
//        KdPj.setText(Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",norwt));
        TCari.requestFocus();
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
       private void hitungObat() {
        ttl=0;
        y=0;
        row2=tabMode.getRowCount();
        for(r=0;r<row2;r++){ 
            try {
                if(Double.parseDouble(tabMode.getValueAt(r,0).toString())>0){
                    try {                
                        y=Math.round(Double.parseDouble(tabMode.getValueAt(r,0).toString())*
                          Double.parseDouble(tabMode.getValueAt(r,8).toString()));                                                
                    } catch (Exception e) {
                        y=0;
                    }
                    ttl=ttl+y;
                }  
            } catch (Exception e) {
            }                           
        }
        
        LTotal.setText(Valid.SetAngka(ttl));
        ppnobat=0;
        if(tampilkan_ppnobat_ralan.equals("Yes")){
            ppnobat=Math.round(ttl*0.11);
            ttl=ttl+ppnobat;
            LPpn.setText(Valid.SetAngka(ppnobat));
            LTotalTagihan.setText(Valid.SetAngka(ttl));
        }
    }
       
    public void setDokter(String kodedokter,String namadokter){
        this.kodedokter=kodedokter;
        this.namadokter=namadokter;
    }
        
    private void getDataobat(int data) {        
        try {            
            stokbarang=0;  
            if(aktifkanbatch.equals("yes")){
                psstok=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=?");
                try {
                    psstok.setString(1,kdgudang.getText());
                    psstok.setString(2,tbDokter.getValueAt(data,2).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stokbarang=rsstok.getDouble(1);
                    }                                
                } catch (Exception e) {
                    stokbarang=0;
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsstok != null){
                        rsstok.close();
                    }
                    if(psstok != null){
                        psstok.close();
                    }
                }
            }else{
                psstok=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                try {
                    psstok.setString(1,kdgudang.getText());
                    psstok.setString(2,tbDokter.getValueAt(data,2).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stokbarang=rsstok.getDouble(1);
                    }                                
                } catch (Exception e) {
                    stokbarang=0;
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsstok != null){
                        rsstok.close();
                    }
                    if(psstok != null){
                        psstok.close();
                    }
                }
            }
                

            tbDokter.setValueAt(stokbarang,data,10);

            y=0;
            try {
                if(tbDokter.getValueAt(data,0).toString().equals("true")){
                    pscarikapasitas= koneksi.prepareStatement("select IFNULL(databarang.kapasitas,1) from databarang where databarang.kode_brng=?");                                      
                    try {
                        pscarikapasitas.setString(1,tbDokter.getValueAt(data,2).toString());
                        carikapasitas=pscarikapasitas.executeQuery();
                        if(carikapasitas.next()){ 
                            y=Double.parseDouble(tbDokter.getValueAt(data,1).toString())/carikapasitas.getDouble(1);
                        }else{
                            y=Double.parseDouble(tbDokter.getValueAt(data,1).toString());
                        }
                    } catch (Exception e) {
                        y=Double.parseDouble(tbDokter.getValueAt(data,1).toString());
                        System.out.println("Kapasitasmu masih kosong broooh : "+e);
                    } finally{
                        if(carikapasitas!=null){
                            carikapasitas.close();
                        }
                        if(pscarikapasitas!=null){
                            pscarikapasitas.close();
                        }
                    }
                }else{
                    y=Double.parseDouble(tbDokter.getValueAt(data,1).toString());
                }                        
            } catch (Exception e) {
                y=0;
            }
            if(stokbarang<y){
                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
            }
        } catch (Exception e) {
            tbDokter.setValueAt(0,data,10);
        } 
    } 
    
    private void getCekStok() {
        if(tbDokter.getSelectedRow()!= -1){
            if(STOKKOSONGRESEP.equals("no")){
                try {
                    if(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                        if(Valid.SetAngka(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>Valid.SetAngka(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())){
                            JOptionPane.showMessageDialog(rootPane,"Maaf jumlah melebihi stok..!!");
                            tbDokter.setValueAt("",tbDokter.getRowCount(),0);
                        }
                    }
                } catch (Exception e) {
                    tbDokter.setValueAt("",tbDokter.getSelectedRow(),0);
                } 
            } 
        }
    }
}
