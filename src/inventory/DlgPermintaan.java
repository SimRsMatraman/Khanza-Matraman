package inventory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
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
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;
import kepegawaian.DlgCariPegawai;
import fungsi.WarnaTable;
import fungsi.WarnaTableApt;
import java.awt.Color;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;

public class DlgPermintaan extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2;
    private int jml = 0, i = 0, row = 0, index = 0, min = 0, max = 0;
    private String[] jumlah, kodebarang, namabarang, satuan, jenis, kategori, golongan, keterangan, stokbangsal;
    private WarnaTable2 warna = new WarnaTable2();
    private DlgCariPegawai pegawai = new DlgCariPegawai(null, false);
    private DlgCariBangsal caribangsal = new DlgCariBangsal(null, false);
    private DlgCariPermintaan form = new DlgCariPermintaan(null, false);
    private DlgBarang barang = new DlgBarang(null, false);
    private boolean sukses = true;
    private File file;
    private FileWriter fileWriter;
    private String iyem, DEPOAKTIFOBAT = "",STOKKOSONGRESEP="no";
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private String qrystok = "", aktifkanbatch = "no", hppfarmasi = "";
    private double ttltotaljual = 0, totaljual = 0, jumlahjual = 0, ttltotalbeli = 0, totalbeli = 0, jumlahbeli = 0, jumlahbeli1 = 0, jumlahbeli2 = 0, jumlahbeli3 = 0, totalbeli1 = 0, totalbeli2 = 0, totalbeli3 = 0,
            ttltotalpesan = 0, totalpesan = 0, jumlahpesan = 0, jumlahutd, totalutd, ttltotalutd, jumlahkeluar, totalkeluar, ttltotalkeluar,
            ttltotalpiutang = 0, totalpiutang = 0, jumlahpiutang = 0, ttltotalretbeli = 0, totalretbeli = 0, jumlahretbeli = 0,
            ttltotalretjual = 0, totalretjual = 0, jumlahretjual = 0, ttltotalretpiut = 0, totalretpiut = 0, jumlahretpiut = 0,
            jumlahpasin = 0, totalpasien = 0, ttltotalpasien = 0, stok = 0, aset = 0, ttlaset = 0, jumlahrespulang = 0, totalrespulang = 0,
            ttltotalrespulang = 0, jumlahmutasimasuk = 0, jumlahmutasikeluar = 0, totalmutasimasuk = 0, totalmutasikeluar = 0,
            ttltotalmutasimasuk = 0, ttltotalmutasikeluar = 0, jumlahhibah = 0, totalhibah = 0, ttltotalhibah = 0;

    /**
     * Creates new form DlgProgramStudi
     *
     * @param parent
     * @param modal
     */
    public DlgPermintaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] judul = {"Jml", "Kode Barang", "Nama Barang", "Satuan", "Jenis Obat", "Kategori", "Golongan", "Min. Permintaan", "Max. Permintaan", "Keterangan", "Stok Bangsal", "Stok Asal", ""};
        tabMode = new DefaultTableModel(null, judul) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0) || (colIndex == 9) || (colIndex == 11)) {
                    a = true;
                }

                return a;
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(42);
            } else if (i == 1) {
                column.setPreferredWidth(90);
            } else if (i == 2) {
                column.setPreferredWidth(200);
            } else if (i == 3) {
                column.setPreferredWidth(50);
            } else if (i == 4) {
                column.setPreferredWidth(100);
            } else if (i == 5) {
                column.setPreferredWidth(100);
            } else if (i == 6) {
                column.setPreferredWidth(100);
            } else if (i == 7) {
                column.setPreferredWidth(80);
            } else if (i == 8) {
                column.setPreferredWidth(80);
            } else if (i == 9) {
                column.setPreferredWidth(180);
            } else if (i == 10) {
                column.setPreferredWidth(90);
            } else if (i == 11) {
                column.setPreferredWidth(90);
            } else if (i == 12) {
                column.setPreferredWidth(250);
            }
        }
        warna.kolom = 0;
//        tbDokter.setDefaultRenderer(Object.class, warna);
        try {
            if(koneksiDB.AKTIFKANWARNARALAN().equals("yes")){
                tbDokter.setDefaultRenderer(Object.class, new WarnaTableApt());
            }else{
                tbDokter.setDefaultRenderer(Object.class, warna);
            }
        } catch (Exception e) {
            tbDokter.setDefaultRenderer(Object.class, warna);
        }

        NoPermintaan.setDocument(new batasInput((byte) 15).getKata(NoPermintaan));
        kdgudangTujuan.setDocument(new batasInput((byte) 5).getKata(kdgudangTujuan));
        kdptg.setDocument(new batasInput((byte) 25).getKata(kdptg));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        StokMin.setDocument(new batasInput((byte) 3).getKata(StokMin));
        StokMax.setDocument(new batasInput((byte) 3).getKata(StokMax));
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

        caribangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (caribangsal.getTable().getSelectedRow() != -1) {
                    if (i == 1) {
                        kdgudangTujuan.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(), 0).toString());
                        nmgudangTujuan.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(), 1).toString());
                        kdgudangTujuan.requestFocus();
                        tampil();
                    } else if (i == 2) {
                        kdgudangasal.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(), 0).toString());
                        nmgudangasal.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(), 1).toString());
                        kdgudangasal.requestFocus();
                        tampil();
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (pegawai.getTable().getSelectedRow() != -1) {
                    kdptg.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(), 0).toString());
                    nmptg.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(), 1).toString());
                }
                kdptg.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        pegawai.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    pegawai.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        try {
            DEPOAKTIFOBAT = koneksiDB.DEPOAKTIFOBAT();
            STOKKOSONGRESEP = koneksiDB.STOKKOSONGRESEP();
        } catch (Exception e) {
            System.out.println("E : " + e);
            DEPOAKTIFOBAT = "";
            STOKKOSONGRESEP="no";
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

        KdBarang = new widget.TextBox();
        StokMin = new widget.TextBox();
        StokMax = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        label12 = new widget.Label();
        LCount = new widget.Label();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoPermintaan = new widget.TextBox();
        label11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        label13 = new widget.Label();
        kdgudangTujuan = new widget.TextBox();
        label16 = new widget.Label();
        kdptg = new widget.TextBox();
        nmgudangTujuan = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label14 = new widget.Label();
        btnSuplier1 = new widget.Button();
        nmgudangasal = new widget.TextBox();
        kdgudangasal = new widget.TextBox();

        KdBarang.setName("KdBarang"); // NOI18N
        KdBarang.setPreferredSize(new java.awt.Dimension(80, 23));
        KdBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdBarangKeyPressed(evt);
            }
        });

        StokMin.setName("StokMin"); // NOI18N
        StokMin.setPreferredSize(new java.awt.Dimension(80, 23));
        StokMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StokMinKeyPressed(evt);
            }
        });

        StokMax.setName("StokMax"); // NOI18N
        StokMax.setPreferredSize(new java.awt.Dimension(80, 23));
        StokMax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StokMaxKeyPressed(evt);
            }
        });

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Permintaan Obat/Alkes/BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
        BtnAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        panelisi1.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi1.add(BtnTambah);

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

        label12.setText("Record :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label12);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(LCount);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Permintaan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 92, 23);

        NoPermintaan.setName("NoPermintaan"); // NOI18N
        NoPermintaan.setPreferredSize(new java.awt.Dimension(207, 23));
        NoPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPermintaanKeyPressed(evt);
            }
        });
        panelisi3.add(NoPermintaan);
        NoPermintaan.setBounds(95, 10, 120, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label11);
        label11.setBounds(220, 10, 55, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalItemStateChanged(evt);
            }
        });
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi3.add(Tanggal);
        Tanggal.setBounds(279, 10, 90, 23);

        label13.setText("Pegawai :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(386, 10, 100, 23);

        kdgudangTujuan.setName("kdgudangTujuan"); // NOI18N
        kdgudangTujuan.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudangTujuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangTujuanKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudangTujuan);
        kdgudangTujuan.setBounds(95, 40, 70, 23);

        label16.setText("Ditujukan Ke :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(0, 40, 92, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(489, 10, 90, 23);

        nmgudangTujuan.setEditable(false);
        nmgudangTujuan.setName("nmgudangTujuan"); // NOI18N
        nmgudangTujuan.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudangTujuan);
        nmgudangTujuan.setBounds(167, 40, 170, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(581, 10, 150, 23);

        btnSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSuplier.setMnemonic('1');
        btnSuplier.setToolTipText("Alt+1");
        btnSuplier.setName("btnSuplier"); // NOI18N
        btnSuplier.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplierActionPerformed(evt);
            }
        });
        panelisi3.add(btnSuplier);
        btnSuplier.setBounds(340, 40, 28, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(734, 10, 28, 23);

        label14.setText("Asal Permintaan :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(386, 40, 100, 23);

        btnSuplier1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSuplier1.setMnemonic('1');
        btnSuplier1.setToolTipText("Alt+1");
        btnSuplier1.setName("btnSuplier1"); // NOI18N
        btnSuplier1.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSuplier1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplier1ActionPerformed(evt);
            }
        });
        panelisi3.add(btnSuplier1);
        btnSuplier1.setBounds(734, 40, 28, 23);

        nmgudangasal.setEditable(false);
        nmgudangasal.setName("nmgudangasal"); // NOI18N
        nmgudangasal.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudangasal);
        nmgudangasal.setBounds(561, 40, 170, 23);

        kdgudangasal.setName("kdgudangasal"); // NOI18N
        kdgudangasal.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudangasal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangasalKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudangasal);
        kdgudangasal.setBounds(489, 40, 70, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        form.emptTeks();
        form.isCek();
        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
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
        if (NoPermintaan.getText().trim().equals("")) {
            Valid.textKosong(NoPermintaan, "No.Permintaan");
        } else if (nmgudangTujuan.getText().trim().equals("")) {
            Valid.textKosong(kdgudangTujuan, "Ruangan/Depo");
        } else if (nmptg.getText().trim().equals("")) {
            Valid.textKosong(kdptg, "Petugas");
        } else if (tbDokter.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (jml <= 0) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan masukkan permintaan...!!!!");
            tbDokter.requestFocus();
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses = true;
                if (Sequel.menyimpantf2("permintaan_medis", "?,?,?,?,?,?", "No.Permintaan", 6, new String[]{
                    NoPermintaan.getText(), kdgudangasal.getText(), kdptg.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), "Baru", kdgudangTujuan.getText()
                }) == true) {
                    jml = tbDokter.getRowCount();
                    for (i = 0; i < jml; i++) {
                        try {
                            if (Valid.SetAngka(tbDokter.getValueAt(i, 0).toString()) > 0) {
                                if (Sequel.menyimpantf2("detail_permintaan_medis", "?,?,?,?,?", "Detail Permintaan", 5, new String[]{
                                    NoPermintaan.getText(),
                                    tbDokter.getValueAt(i, 1).toString(), tbDokter.getValueAt(i, 3).toString(),
                                    tbDokter.getValueAt(i, 0).toString(),
                                    tbDokter.getValueAt(i, 9).toString().replaceAll("'", "").replaceAll("\"", "")
                                }) == false) {
                                    sukses = false;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        }
                    }
                } else {
                    sukses = false;
                    JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan No.Permintaan sudah ada sebelumnya...!!");
                }
                if (sukses == true) {
                    Sequel.Commit();
                    jml = tbDokter.getRowCount();
                    for (i = 0; i < jml; i++) {
                        tbDokter.setValueAt("", i, 0);
                        tbDokter.setValueAt("", i, 9);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
                autoNomor();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void getData() {
        int row = tbDokter.getSelectedRow();
        if (row != -1) {
            KdBarang.setText(tbDokter.getValueAt(row, 1).toString());
            StokMin.setText(tbDokter.getValueAt(row, 7).toString());
            StokMax.setText(tbDokter.getValueAt(row, 8).toString());
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
        kdgudangTujuan.requestFocus();
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
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
    if(tbDokter.getSelectedRow()!= -1){
        try {
            getCekStok();
            getCekStokMin();
            getCekStokMax();
            getData();
        } catch (java.lang.NullPointerException e) {
        }
    }
}//GEN-LAST:event_tbDokterMouseClicked

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
    if (tbDokter.getRowCount() != 0) {
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getCekStok();  
            getCekStokMin();
            getCekStokMax();   
        } else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
            getCekStok();  
            getCekStokMin();
            getCekStokMax();   
        } else if(evt.getKeyCode()==KeyEvent.VK_UP){
            getCekStok();  
            getCekStokMin();
            getCekStokMax();   
        } else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            getCekStok();  
            getCekStokMin();
            getCekStokMax();   
        }
        else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
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
        }
    }
}//GEN-LAST:event_tbDokterKeyPressed

    private void tbDokterKeyReleased(java.awt.event.KeyEvent evt) {
        if (tabMode.getRowCount() != 0) {
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getCekStok();  
            getCekStokMin();
            getCekStokMax();   
        } else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
            getCekStok();  
            getCekStokMin();
            getCekStokMax();   
        } else if(evt.getKeyCode()==KeyEvent.VK_UP){
            getCekStok();  
            getCekStokMin();
            getCekStokMax();   
        } else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            getCekStok();  
            getCekStokMin();
            getCekStokMax();   
        }
        }
    }
    
    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {                                       
        if(this.isVisible()==true){
            getCekStok();
            getCekStokMin();
            getCekStokMax(); 
        }
    }

    public void keyReleased(java.awt.event.KeyEvent evt) {
        tbDokterKeyReleased(evt);
    }
    
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        tbDokterPropertyChange(evt);
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (tbDokter.getRowCount() != 0) {
            tampil();
        }

    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void ppStok1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCekStok ceksetok = new DlgCekStok(null, false);
        ceksetok.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ceksetok.setLocationRelativeTo(internalFrame1);
        ceksetok.setAlwaysOnTop(false);
        ceksetok.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppStok1ActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        pegawai.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        i = 1;
        caribangsal.isCek();
        caribangsal.emptTeks();
        caribangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        caribangsal.setLocationRelativeTo(internalFrame1);
        caribangsal.setAlwaysOnTop(false);
        caribangsal.setVisible(true);
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            nmptg.setText(pegawai.tampil3(kdptg.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            kdgudangTujuan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnSimpan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void kdgudangTujuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangTujuanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudangTujuan, kdgudangTujuan.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudangTujuan, kdgudangTujuan.getText());
            NoPermintaan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudangTujuan, kdgudangTujuan.getText());
            kdptg.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnSuplierActionPerformed(null);
        }
    }//GEN-LAST:event_kdgudangTujuanKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, NoPermintaan, kdgudangTujuan);
    }//GEN-LAST:event_TanggalKeyPressed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalItemStateChanged

    private void NoPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPermintaanKeyPressed
        Valid.pindah(evt, BtnSimpan, kdgudangTujuan);
    }//GEN-LAST:event_NoPermintaanKeyPressed

    private void btnSuplier1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplier1ActionPerformed
        i = 2;
        caribangsal.isCek();
        caribangsal.emptTeks();
        caribangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        caribangsal.setLocationRelativeTo(internalFrame1);
        caribangsal.setAlwaysOnTop(false);
        caribangsal.setVisible(true);
    }//GEN-LAST:event_btnSuplier1ActionPerformed

    private void kdgudangasalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangasalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdgudangasalKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void StokMaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StokMaxKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StokMaxKeyPressed

    private void StokMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StokMinKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StokMinKeyPressed

    private void KdBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdBarangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdBarangKeyPressed

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
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox KdBarang;
    private widget.Label LCount;
    private widget.TextBox NoPermintaan;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox StokMax;
    private widget.TextBox StokMin;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.Button btnSuplier1;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdgudangTujuan;
    private widget.TextBox kdgudangasal;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.TextBox nmgudangTujuan;
    private widget.TextBox nmgudangasal;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok1;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil1() {
        try {
            Valid.tabelKosong(tabMode);
            file = new File("./cache/permintaanobat.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem = "";
            if (kdgudangTujuan.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "select databarang.kode_brng,databarang.nama_brng,databarang.kode_sat,jenis.nama,"
                        + "kategori_barang.nama as kategori,golongan_barang.nama as golongan,gudangbarang.stok,databarang.minobat,databarang.maxobat "
                        + " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "
                        + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                        + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + " left join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "
                        + " where databarang.status='1' order by databarang.nama_brng");
            } else {
                ps = koneksi.prepareStatement(
                        "select databarang.kode_brng,databarang.nama_brng,databarang.kode_sat,jenis.nama,"
                        + "kategori_barang.nama as kategori,golongan_barang.nama as golongan,gudangbarang.stok,databarang.minobat,databarang.maxobat "
                        + " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "
                        + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                        + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + " left join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "
                        + " where databarang.status='1' and gudangbarang.kd_bangsal like ? order by databarang.nama_brng");
            }
            try {
                ps.setString(1, "%" + kdgudangTujuan.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        "", rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(8), rs.getString(9), "", rs.getString(7)
                    });
                    iyem = iyem + "{\"KodeBarang\":\"" + rs.getString(1) + "\",\"NamaBarang\":\"" + rs.getString(2).replaceAll("\"", "") + "\",\"Satuan\":\"" + rs.getString(3) + "\",\"JenisObat\":\"" + rs.getString(4) + "\",\"Kategori\":\"" + rs.getString(5) + "\",\"Golongan\":\"" + rs.getString(6) + "\",\"Min. Permintaan\":\"" + rs.getString(8) + "\",\"Max.Permintaan\":\"" + rs.getString(9) + "\",\"Stok\":\"" + rs.getString(7) + "\"},";
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
            fileWriter.write("{\"permintaanobat\":[" + iyem.substring(0, iyem.length() - 1) + "]}");
            fileWriter.flush();
            fileWriter.close();
            iyem = null;
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select databarang.kode_brng,databarang.nama_brng,databarang.kode_sat,jenis.nama,"
                    + "kategori_barang.nama as kategori,golongan_barang.nama as golongan,gudangbarang.stok,databarang.minobat,databarang.maxobat "
                    + " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "
                    + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                    + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                    + " left join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "
                    + " where databarang.status='1' and gudangbarang.kd_bangsal=? and gudangbarang.stok>'0' group by databarang.nama_brng order by databarang.nama_brng");
            try {
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

                ps.setString(1, kdgudangasal.getText().trim());       
                rs = ps.executeQuery();
                if (kdgudangTujuan.equals("yes")) {
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
                        ps2.setString(1, rs.getString(1));
                        ps2.setString(2, kdgudangTujuan.getText().trim());
                        rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            stok = rs2.getDouble(1);
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
                            "select gudangbangsal.stok_min, gudangbangsal.stok_max from gudangbangsal where gudangbangsal.kd_bangsal=? ");
                    try {
                        ps2.setString(1, kdgudangTujuan.getText().trim());
                        rs2 = ps2.executeQuery();
                        if (rs2.next()) {

                            jumlahbeli = rs2.getDouble(1);
                            totalbeli = rs2.getDouble(2);
                        }
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

                    //Stok Asal 
                    ps2 = koneksi.prepareStatement(
                            "select gudangbarang.stok "+
                            "from gudangbarang where gudangbarang.kode_brng=? and gudangbarang.kd_bangsal=?");
                    try {
                        ps2.setString(1, rs.getString(1));
                        ps2.setString(2, kdgudangasal.getText().trim());
                        rs2 = ps2.executeQuery();
                        if (rs2.next()) {

                            jumlahbeli1 = rs2.getDouble(1);
                        }
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
                    
//                        tabMode.addRow(new Object[]{
//                            "", rs.getString(1), rs.getString(2), rs.getString(3),
//                            rs.getString(4), rs.getString(5), rs.getString(6), Valid.SetAngka(jumlahbeli), Valid.SetAngka(totalbeli), "", Valid.SetAngka(stok), Valid.SetAngka(jumlahbeli1)
//                        });
                        
                        tabMode.addRow(new Object[]{
                            "", rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(8), rs.getString(9), "", Valid.SetAngka(stok), rs.getString(7), ""
                        });
                    iyem = iyem + "{\"KodeBarang\":\"" + rs.getString(1) + "\",\"NamaBarang\":\"" + rs.getString(2).replaceAll("\"", "") + "\",\"Satuan\":\"" + rs.getString(3) + "\",\"JenisObat\":\"" + rs.getString(4) + "\",\"Kategori\":\"" + rs.getString(5) + "\",\"Golongan\":\"" + rs.getString(6) + "\",\"Min. Permintaan\":\"" + rs.getString(8) + "\",\"Max.Permintaan\":\"" + rs.getString(9) + "\",\"Stok Bangsal\":\"" + Valid.SetAngka(stok) + "\",\"Stok Asal\":\"" + Valid.SetAngka(jumlahbeli1) + "\"},";
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
        LCount.setText(""+tabMode.getRowCount());
    }

    public void isCek() {
        autoNomor();
        tampil();
        kdgudangasal.setText("B0016");
        nmgudangasal.setText("GUDANG");
        TCari.requestFocus();
        if (akses.getjml2() >= 1) {
            kdptg.setEditable(false);
            btnPetugas.setEnabled(false);
            kdptg.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getpermintaan_medis());
            BtnTambah.setEnabled(akses.getobat());
            nmptg.setText(pegawai.tampil3(kdptg.getText()));
            if (!DEPOAKTIFOBAT.equals("")) {
                kdgudangasal.setText(DEPOAKTIFOBAT);
                nmgudangasal.setText(caribangsal.tampil3(DEPOAKTIFOBAT));
                btnSuplier1.setEnabled(false);
            }
        }
    }

    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_medis.no_permintaan,3),signed)),0) from permintaan_medis where permintaan_medis.tanggal='" + Valid.SetTgl(Tanggal.getSelectedItem() + "") + "' ",
                "PM" + Tanggal.getSelectedItem().toString().substring(6, 10) + Tanggal.getSelectedItem().toString().substring(3, 5) + Tanggal.getSelectedItem().toString().substring(0, 2), 3, NoPermintaan);
        tampil();
    }
    
    private void getCekStok() {
        if(tbDokter.getSelectedRow()!= -1){
            if(STOKKOSONGRESEP.equals("no")){
                try {
                    if(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                        if(Valid.SetAngka(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>Valid.SetAngka(tbDokter.getValueAt(tbDokter.getSelectedRow(),11).toString())){
                            JOptionPane.showMessageDialog(rootPane,"Maaf permintaan melebihi stok bangsal asal..!!");
                            tbDokter.setValueAt("",tbDokter.getRowCount(),0);
                        }
                    }
                } catch (Exception e) {
                    tbDokter.setValueAt("",tbDokter.getSelectedRow(),0);
                } 
            } 
        }
    }
    
    private void getCekStokMin() {
        if(tbDokter.getSelectedRow()!= -1){
            if(STOKKOSONGRESEP.equals("no")){
                try {
                    if(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                        if(Valid.SetAngka(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())<Valid.SetAngka(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())){
                            JOptionPane.showMessageDialog(rootPane,"Maaf permintaan kurang dari batas minimum..!!");
                            tbDokter.setValueAt("",tbDokter.getRowCount(),0);
                        }
                    }
                } catch (Exception e) {
                    tbDokter.setValueAt("",tbDokter.getSelectedRow(),0);
                } 
            } 
        }
    }
    
    private void getCekStokMax() {
        if(tbDokter.getSelectedRow()!= -1){
            if(STOKKOSONGRESEP.equals("no")){
                try {
                    if(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                        if(Valid.SetAngka(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>Valid.SetAngka(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString())){
                            JOptionPane.showMessageDialog(rootPane,"Maaf permintaan melebihi batas maximum..!!");
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
