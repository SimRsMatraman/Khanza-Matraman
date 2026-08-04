/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import rekammedis.RMCariDiagnosa1;


/**
 *
 * @author perpustakaan
 */
public final class RMDataCatatanObservasiRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);  
    private RMCariDiagnosa1 diagnosa=new RMCariDiagnosa1(null,false);
    private String dpjp="";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMDataCatatanObservasiRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat",
            "No.R.M.",
            "Nama Pasien",
            "Umur",
            "JK",
            "Tgl.Lahir",
            "Tgl.Obser",
            "Jam Obser",
            "GCS (E,V,M)",
            "TD(mmHg)",
            "HR(x/menit)",
            "RR(x/menit)",
            "Suhu(°C)",
            "SpO2(%)",
            "Nadi",
            "Hari Ke",
            "Kode Diagnosa",
            "Nama Diagnosa",
            "HB",
            "HT",
            "Trombosit",
            "Leukosit",
            "Lainnya",
            "Cairan",
            "Terapi",
            "Pagi",
            "Siang",
            "Malam",
            "BB",
            "TB",
            "NIP",
            "Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 32; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);

            if (i == 0) {              // No.Rawat
                column.setPreferredWidth(105);
            } else if (i == 1) {       // No.R.M.
                column.setPreferredWidth(65);
            } else if (i == 2) {       // Nama Pasien
                column.setPreferredWidth(160);
            } else if (i == 3) {       // Umur
                column.setPreferredWidth(55);
            } else if (i == 4) {       // JK
                column.setPreferredWidth(30);
            } else if (i == 5) {       // Tgl.Lahir
                column.setPreferredWidth(75);
            } else if (i == 6) {       // Tgl.Obser
                column.setPreferredWidth(75);
            } else if (i == 7) {       // Jam Obser
                column.setPreferredWidth(65);
            } else if (i == 8) {       // GCS
                column.setPreferredWidth(70);
            } else if (i == 9) {       // TD
                column.setPreferredWidth(70);
            } else if (i == 10) {      // HR
                column.setPreferredWidth(70);
            } else if (i == 11) {      // RR
                column.setPreferredWidth(70);
            } else if (i == 12) {      // Suhu
                column.setPreferredWidth(60);
            } else if (i == 13) {      // SpO2
                column.setPreferredWidth(60);
            } else if (i == 14) {      // Nadi
                column.setPreferredWidth(60);
            } else if (i == 15) {      // Hari Ke
                column.setPreferredWidth(55);
            } else if (i == 16) {      // Kode Diagnosa
                column.setPreferredWidth(90);
            } else if (i == 17) {      // Nama Diagnosa
                column.setPreferredWidth(180);
            } else if (i == 18) {      // HB
                column.setPreferredWidth(55);
            } else if (i == 19) {      // HT
                column.setPreferredWidth(55);
            } else if (i == 20) {      // Trombosit
                column.setPreferredWidth(75);
            } else if (i == 21) {      // Leukosit
                column.setPreferredWidth(75);
            } else if (i == 22) {      // Lainnya
                column.setPreferredWidth(160);
            } else if (i == 23) {      // Cairan
                column.setPreferredWidth(180);
            } else if (i == 24) {      // Terapi
                column.setPreferredWidth(180);
            } else if (i == 25) {      // Pagi
                column.setPreferredWidth(140);
            } else if (i == 26) {      // Siang
                column.setPreferredWidth(140);
            } else if (i == 27) {      // Malam
                column.setPreferredWidth(140);
            } else if (i == 28) {      // BB
                column.setPreferredWidth(45);
            } else if (i == 29) {      // TB
                column.setPreferredWidth(45);
            } else if (i == 30) {      // NIP
                column.setPreferredWidth(120);
            } else if (i == 31) {      // Nama Petugas
                column.setPreferredWidth(160);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
        GCS.setDocument(new batasInput((byte)10).getKata(GCS));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        HR.setDocument(new batasInput((byte)5).getKata(HR));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        SPO.setDocument(new batasInput((byte)3).getKata(SPO));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                NIP.requestFocus();
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
        
        diagnosa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(diagnosa.getTable().getSelectedRow()!= -1){                   
                    KdDiagnosa.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),0).toString());
                    NamaDiagnosa.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),1).toString());
                }  
                KdDiagnosa.requestFocus();
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
        
        ChkInput.setSelected(false);
        isForm();
        jam();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCatatanObservasiIGD = new javax.swing.JMenuItem();
        JK = new widget.TextBox();
        Umur = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        TglLahir = new widget.TextBox();
        GCS = new widget.TextBox();
        HR = new widget.TextBox();
        Suhu = new widget.TextBox();
        TD = new widget.TextBox();
        RR = new widget.TextBox();
        SPO = new widget.TextBox();
        btnDiagnosa = new widget.Button();
        NamaDiagnosa = new widget.TextBox();
        KdDiagnosa = new widget.TextBox();
        HariKe = new widget.TextBox();
        Leukosit = new widget.TextBox();
        Hb = new widget.TextBox();
        Ht = new widget.TextBox();
        Trombosit = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        scrollPane2 = new widget.ScrollPane();
        Lainnya = new widget.TextArea();
        jSeparator2 = new javax.swing.JSeparator();
        Nadi = new widget.TextBox();
        jSeparator3 = new javax.swing.JSeparator();
        scrollPane3 = new widget.ScrollPane();
        Cairan = new widget.TextArea();
        scrollPane4 = new widget.ScrollPane();
        Teraphi = new widget.TextArea();
        jSeparator4 = new javax.swing.JSeparator();
        scrollPane5 = new widget.ScrollPane();
        Pagi = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        Siang = new widget.TextArea();
        scrollPane7 = new widget.ScrollPane();
        Malam = new widget.TextArea();
        Bb = new widget.TextBox();
        Tb = new widget.TextBox();
        TanggalCairan = new widget.Tanggal();
        TanggalBalance = new widget.Tanggal();
        jLabel40 = new widget.Label();
        jLabel5 = new widget.Label();
        jLabel41 = new widget.Label();
        jLabel42 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel38 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel15 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel31 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel29 = new widget.Label();
        jLabel28 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel26 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel22 = new widget.Label();
        jLabel20 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel12 = new widget.Label();
        jLabel8 = new widget.Label();
        jLabel18 = new widget.Label();
        jLabel16 = new widget.Label();
        jLabel4 = new widget.Label();
        jLabel62 = new widget.Label();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCatatanObservasiIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanObservasiIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanObservasiIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanObservasiIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanObservasiIGD.setText("Formulir Catatan Observasi Rawat Inap");
        MnCatatanObservasiIGD.setName("MnCatatanObservasiIGD"); // NOI18N
        MnCatatanObservasiIGD.setPreferredSize(new java.awt.Dimension(260, 26));
        MnCatatanObservasiIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanObservasiIGDActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCatatanObservasiIGD);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Observasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass8.add(BtnPrint);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-08-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-08-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 124));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-08-2026" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(84, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(178, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(243, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(308, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(373, 40, 23, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormInput.add(NIP);
        NIP.setBounds(474, 40, 94, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(570, 40, 187, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(761, 40, 28, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(110, 260, 80, 23);

        HR.setFocusTraversalPolicyProvider(true);
        HR.setName("HR"); // NOI18N
        HR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HRKeyPressed(evt);
            }
        });
        FormInput.add(HR);
        HR.setBounds(440, 260, 80, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(80, 290, 80, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(250, 260, 80, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(640, 260, 80, 23);

        SPO.setFocusTraversalPolicyProvider(true);
        SPO.setName("SPO"); // NOI18N
        SPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPOKeyPressed(evt);
            }
        });
        FormInput.add(SPO);
        SPO.setBounds(250, 290, 80, 23);

        btnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa.setMnemonic('2');
        btnDiagnosa.setToolTipText("ALt+2");
        btnDiagnosa.setName("btnDiagnosa"); // NOI18N
        btnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosaActionPerformed(evt);
            }
        });
        btnDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(btnDiagnosa);
        btnDiagnosa.setBounds(380, 70, 28, 23);

        NamaDiagnosa.setEditable(false);
        NamaDiagnosa.setName("NamaDiagnosa"); // NOI18N
        FormInput.add(NamaDiagnosa);
        NamaDiagnosa.setBounds(190, 70, 187, 23);

        KdDiagnosa.setEditable(false);
        KdDiagnosa.setHighlighter(null);
        KdDiagnosa.setName("KdDiagnosa"); // NOI18N
        KdDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(KdDiagnosa);
        KdDiagnosa.setBounds(90, 70, 94, 23);

        HariKe.setEditable(false);
        HariKe.setFocusTraversalPolicyProvider(true);
        HariKe.setName("HariKe"); // NOI18N
        HariKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HariKeActionPerformed(evt);
            }
        });
        HariKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HariKeKeyPressed(evt);
            }
        });
        FormInput.add(HariKe);
        HariKe.setBounds(474, 70, 94, 23);

        Leukosit.setFocusTraversalPolicyProvider(true);
        Leukosit.setName("Leukosit"); // NOI18N
        Leukosit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeukositActionPerformed(evt);
            }
        });
        Leukosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeukositKeyPressed(evt);
            }
        });
        FormInput.add(Leukosit);
        Leukosit.setBounds(90, 200, 270, 23);

        Hb.setFocusTraversalPolicyProvider(true);
        Hb.setName("Hb"); // NOI18N
        Hb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HbKeyPressed(evt);
            }
        });
        FormInput.add(Hb);
        Hb.setBounds(90, 110, 270, 23);

        Ht.setFocusTraversalPolicyProvider(true);
        Ht.setName("Ht"); // NOI18N
        Ht.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HtKeyPressed(evt);
            }
        });
        FormInput.add(Ht);
        Ht.setBounds(90, 140, 270, 23);

        Trombosit.setFocusTraversalPolicyProvider(true);
        Trombosit.setName("Trombosit"); // NOI18N
        Trombosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TrombositKeyPressed(evt);
            }
        });
        FormInput.add(Trombosit);
        Trombosit.setBounds(90, 170, 270, 23);

        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(20, 100, 780, 5);

        scrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lainnya :"));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Lainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Lainnya.setColumns(20);
        Lainnya.setRows(5);
        Lainnya.setName("Lainnya"); // NOI18N
        Lainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainnyaKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Lainnya);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(430, 110, 360, 110);

        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(20, 230, 780, 10);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(430, 290, 80, 23);

        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(20, 320, 780, 10);

        scrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("ml / Kg / Jam"));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Cairan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Cairan.setColumns(20);
        Cairan.setRows(5);
        Cairan.setName("Cairan"); // NOI18N
        Cairan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CairanKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(Cairan);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(20, 360, 360, 70);

        scrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("CC / JAM"));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Teraphi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Teraphi.setColumns(20);
        Teraphi.setRows(5);
        Teraphi.setName("Teraphi"); // NOI18N
        Teraphi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeraphiKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Teraphi);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(420, 360, 380, 70);

        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(20, 440, 780, 20);

        scrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder("Pagi (07.00 - 14.00)"));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Pagi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Pagi.setColumns(20);
        Pagi.setRows(5);
        Pagi.setName("Pagi"); // NOI18N
        Pagi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PagiKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Pagi);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(20, 480, 250, 90);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder("Siang (14.00 - 21.00)"));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Siang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Siang.setColumns(20);
        Siang.setRows(5);
        Siang.setName("Siang"); // NOI18N
        Siang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SiangKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Siang);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(280, 480, 260, 90);

        scrollPane7.setBorder(javax.swing.BorderFactory.createTitledBorder("Malam (21.00 - 07.00)"));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Malam.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Malam.setColumns(20);
        Malam.setRows(5);
        Malam.setName("Malam"); // NOI18N
        Malam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MalamKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(Malam);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(550, 480, 250, 90);

        Bb.setFocusTraversalPolicyProvider(true);
        Bb.setName("Bb"); // NOI18N
        Bb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BbKeyPressed(evt);
            }
        });
        FormInput.add(Bb);
        Bb.setBounds(60, 580, 80, 23);

        Tb.setFocusTraversalPolicyProvider(true);
        Tb.setName("Tb"); // NOI18N
        Tb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TbKeyPressed(evt);
            }
        });
        FormInput.add(Tb);
        Tb.setBounds(230, 580, 80, 23);

        TanggalCairan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalCairan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-08-2026" }));
        TanggalCairan.setDisplayFormat("dd-MM-yyyy");
        TanggalCairan.setName("TanggalCairan"); // NOI18N
        TanggalCairan.setOpaque(false);
        TanggalCairan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalCairanItemStateChanged(evt);
            }
        });
        TanggalCairan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalCairanActionPerformed(evt);
            }
        });
        TanggalCairan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalCairanKeyPressed(evt);
            }
        });
        FormInput.add(TanggalCairan);
        TanggalCairan.setBounds(20, 330, 90, 23);

        TanggalBalance.setForeground(new java.awt.Color(50, 70, 50));
        TanggalBalance.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-08-2026" }));
        TanggalBalance.setDisplayFormat("dd-MM-yyyy");
        TanggalBalance.setName("TanggalBalance"); // NOI18N
        TanggalBalance.setOpaque(false);
        TanggalBalance.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalBalanceItemStateChanged(evt);
            }
        });
        TanggalBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalBalanceActionPerformed(evt);
            }
        });
        TanggalBalance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalBalanceKeyPressed(evt);
            }
        });
        FormInput.add(TanggalBalance);
        TanggalBalance.setBounds(20, 450, 90, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Cm");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(320, 580, 30, 23);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 80, 23);

        jLabel41.setText("Tanggal :");
        jLabel41.setName("jLabel41"); // NOI18N
        jLabel41.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 40, 80, 23);

        jLabel42.setText("Petugas :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(400, 40, 70, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("TANDA VITAL");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(20, 230, 780, 23);

        jLabel9.setText("Tgl.Lahir :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(625, 10, 60, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("x/menit");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(530, 260, 50, 23);

        jLabel45.setText("HR :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(400, 260, 40, 23);

        jLabel46.setText("Suhu :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(40, 290, 40, 23);

        jLabel47.setText("TD :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(210, 260, 40, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("°C");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(170, 290, 30, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("mmHg");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(340, 260, 40, 23);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("x/menit");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(730, 260, 50, 23);

        jLabel51.setText("RR :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(600, 260, 40, 23);

        jLabel52.setText("SpO2 :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(210, 290, 40, 23);

        jLabel53.setText("Nadi : ");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(370, 290, 60, 23);

        jLabel54.setText("Leukosit :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 200, 70, 23);

        jLabel55.setText("Hari ke :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(410, 70, 60, 20);

        jLabel56.setText("Diagnosa :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 70, 70, 23);

        jLabel57.setText("HB :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(10, 110, 70, 23);

        jLabel58.setText("HT :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(10, 140, 70, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText(" /µL");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(370, 200, 50, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("gr/dL");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(370, 110, 50, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("%");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(370, 140, 50, 23);

        jLabel39.setText("TB : ");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(190, 580, 40, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("Kg");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(150, 580, 30, 23);

        jLabel37.setText("BB : ");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(20, 580, 40, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("BALANCE CAIRAN");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(120, 440, 580, 30);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("CAIRAN / TERAPI");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(120, 330, 550, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("%");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(340, 290, 30, 23);

        jLabel13.setText("GCS (E,V,M) :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(30, 260, 80, 23);

        jLabel34.setText("Trombosit :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(10, 170, 70, 23);

        jLabel33.setText("HT :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(10, 140, 70, 23);

        jLabel32.setText("HB :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(10, 110, 70, 23);

        jLabel31.setText("Diagnosa :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(10, 70, 70, 23);

        jLabel30.setText("Hari ke :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(410, 70, 60, 20);

        jLabel24.setText("Leukosit :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(10, 200, 70, 23);

        jLabel35.setText("Nadi : ");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(370, 290, 60, 23);

        jLabel29.setText("SpO2 :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(210, 290, 40, 23);

        jLabel28.setText("RR :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(600, 260, 40, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(730, 260, 50, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("mmHg");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(340, 260, 40, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("°C");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(170, 290, 30, 23);

        jLabel23.setText("TD :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(210, 260, 40, 23);

        jLabel22.setText("Suhu :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(40, 290, 40, 23);

        jLabel20.setText("HR :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(400, 260, 40, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("x/menit");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(530, 260, 50, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("TANDA VITAL");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(20, 230, 780, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 80, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 80, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("x10^3/µL");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(370, 170, 50, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("") ||
            TPasien.getText().trim().equals("")) {

            Valid.textKosong(TNoRw, "pasien");

        } else if (NIP.getText().trim().equals("") ||
                   NamaPetugas.getText().trim().equals("")) {

            Valid.textKosong(NIP, "Petugas");

        } else {
            String tanggal = Valid.SetTgl(Tanggal.getSelectedItem() + "");
            String jam = Jam.getSelectedItem() + ":" +
                         Menit.getSelectedItem() + ":" +
                         Detik.getSelectedItem();

            if (Sequel.menyimpantf(
                    "catatan_observasi",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                    "Data",
                    25,
                    new String[] {
                        TNoRw.getText().trim(),          // 1  no_rawat
                        tanggal,                         // 2  tgl_perawatan
                        jam,                             // 3  jam_rawat
                        GCS.getText().trim(),            // 4  gcs
                        TD.getText().trim(),             // 5  td
                        HR.getText().trim(),             // 6  hr
                        RR.getText().trim(),             // 7  rr
                        Suhu.getText().trim(),           // 8  suhu
                        SPO.getText().trim(),            // 9  spo2
                        Nadi.getText().trim(),           // 10 nadi
                        HariKe.getText().trim(),         // 11 hari_ke
                        KdDiagnosa.getText().trim(),     // 12 penyakit
                        Hb.getText().trim(),             // 13 hb
                        Ht.getText().trim(),             // 14 ht
                        Trombosit.getText().trim(),      // 15 trombosit
                        Leukosit.getText().trim(),       // 16 leukosit
                        Lainnya.getText().trim(),       // 17 lainnya
                        Cairan.getText().trim(),         // 18 cairan
                        Teraphi.getText().trim(),        // 19 terapi
                        Pagi.getText().trim(),           // 20 pagi
                        Siang.getText().trim(),          // 21 siang
                        Malam.getText().trim(),          // 22 malam
                        Bb.getText().trim(),             // 23 bb
                        Tb.getText().trim(),             // 24 tb
                        NIP.getText().trim()             // 25 nip
                    }
            )) {
                tabMode.addRow(new Object[] {
                    TNoRw.getText().trim(),          // 0
                    TNoRM.getText().trim(),          // 1
                    TPasien.getText().trim(),        // 2
                    Umur.getText().trim(),           // 3
                    JK.getText().trim(),             // 4
                    TglLahir.getText().trim(),       // 5
                    tanggal,                         // 6
                    jam,                             // 7
                    GCS.getText().trim(),            // 8
                    TD.getText().trim(),             // 9
                    HR.getText().trim(),             // 10
                    RR.getText().trim(),             // 11
                    Suhu.getText().trim(),           // 12
                    SPO.getText().trim(),            // 13
                    Nadi.getText().trim(),           // 14
                    HariKe.getText().trim(),         // 15
                    KdDiagnosa.getText().trim(),     // 16
                    NamaDiagnosa.getText().trim(),   // 17
                    Hb.getText().trim(),             // 18
                    Ht.getText().trim(),             // 19
                    Trombosit.getText().trim(),      // 20
                    Leukosit.getText().trim(),       // 21
                    Lainnya.getText().trim(),        // 22 lainnya
                    Cairan.getText().trim(),         // 23
                    Teraphi.getText().trim(),        // 24
                    Pagi.getText().trim(),           // 25
                    Siang.getText().trim(),          // 26
                    Malam.getText().trim(),          // 27
                    Bb.getText().trim(),             // 28
                    Tb.getText().trim(),             // 29
                    NIP.getText().trim(),            // 30
                    NamaPetugas.getText().trim()     // 31
                });

                LCount.setText(String.valueOf(tabMode.getRowCount()));
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,SPO,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }   
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{ 
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptDataCatatanObservasiRanap.jasper","report","::[ Data Catatan Observasi Rawat Inap ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pasien.tgl_lahir,catatan_observasi_ranap.tgl_perawatan,catatan_observasi_ranap.jam_rawat,catatan_observasi_ranap.gcs,"+
                    "catatan_observasi_ranap.td,catatan_observasi_ranap.hr,catatan_observasi_ranap.rr,catatan_observasi_ranap.suhu,catatan_observasi_ranap.spo2,"+
                    "catatan_observasi_ranap.nip,petugas.nama from catatan_observasi_ranap inner join reg_periksa on catatan_observasi_ranap.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on catatan_observasi_ranap.nip=petugas.nip where "+
                    "catatan_observasi_ranap.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by catatan_observasi_ranap.tgl_perawatan",param);
            }else{
                Valid.MyReportqry("rptDataCatatanObservasiRanap.jasper","report","::[ Data Catatan Observasi Rawat Inap ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pasien.tgl_lahir,catatan_observasi_ranap.tgl_perawatan,catatan_observasi_ranap.jam_rawat,catatan_observasi_ranap.gcs,"+
                    "catatan_observasi_ranap.td,catatan_observasi_ranap.hr,catatan_observasi_ranap.rr,catatan_observasi_ranap.suhu,catatan_observasi_ranap.spo2,"+
                    "catatan_observasi_ranap.nip,petugas.nama from catatan_observasi_ranap inner join reg_periksa on catatan_observasi_ranap.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on catatan_observasi_ranap.nip=petugas.nip where "+
                    "catatan_observasi_ranap.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and "+
                    "(reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    "pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or catatan_observasi_ranap.nip like '%"+TCari.getText().trim()+"%' or petugas.nama like '%"+TCari.getText().trim()+"%') "+
                    "order by catatan_observasi_ranap.tgl_perawatan ",param);
            }  
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            GCS.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_NIPKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,Detik,GCS);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnCatatanObservasiIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanObservasiIGDActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            dpjp=Sequel.cariIsi("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            if(dpjp.equals("")){
                dpjp=Sequel.cariIsi("select dokter.nm_dokter from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            }
            param.put("dpjp",dpjp);   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptFormulirCatatanObservasiRanap.jasper","report","::[ Formulir Catatan Observasi Rawat Inap ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                    "pasien.jk,pasien.tgl_lahir,catatan_observasi_ranap.tgl_perawatan,catatan_observasi_ranap.jam_rawat,catatan_observasi_ranap.gcs,"+
                    "catatan_observasi_ranap.td,catatan_observasi_ranap.hr,catatan_observasi_ranap.rr,catatan_observasi_ranap.suhu,catatan_observasi_ranap.spo2,"+
                    "petugas.nama from catatan_observasi_ranap inner join reg_periksa on catatan_observasi_ranap.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on catatan_observasi_ranap.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnCatatanObservasiIGDActionPerformed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt,btnPetugas,TD);
    }//GEN-LAST:event_GCSKeyPressed

    private void HRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HRKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_HRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,SPO);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,GCS,HR);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,HR,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPOKeyPressed
        Valid.pindah(evt,Suhu,BtnSimpan);
    }//GEN-LAST:event_SPOKeyPressed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
       diagnosa.emptTeks();
       diagnosa.setNoRawat(TNoRw.getText().trim());
       diagnosa.setSize(
           internalFrame1.getWidth() - 20,
           internalFrame1.getHeight() - 20
       );
       diagnosa.setLocationRelativeTo(internalFrame1);
       diagnosa.setVisible(true);
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDiagnosaKeyPressed

    private void KdDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDiagnosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDiagnosaKeyPressed

    private void HariKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HariKeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HariKeKeyPressed

    private void LeukositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeukositKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LeukositKeyPressed

    private void HbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HbKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HbKeyPressed

    private void HtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HtKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HtKeyPressed

    private void TrombositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TrombositKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TrombositKeyPressed

    private void LainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainnyaKeyPressed
        Valid.pindah2(evt,NIP,BtnSimpan);
    }//GEN-LAST:event_LainnyaKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NadiKeyPressed

    private void CairanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CairanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CairanKeyPressed

    private void TeraphiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeraphiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeraphiKeyPressed

    private void PagiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PagiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PagiKeyPressed

    private void SiangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SiangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SiangKeyPressed

    private void MalamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MalamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MalamKeyPressed

    private void BbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BbKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BbKeyPressed

    private void TbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TbKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TbKeyPressed

    private void HariKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HariKeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HariKeActionPerformed

    private void LeukositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeukositActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LeukositActionPerformed

    private void TanggalBalanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalBalanceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalBalanceKeyPressed

    private void TanggalCairanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalCairanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalCairanKeyPressed

    private void TanggalCairanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalCairanActionPerformed
        isRawat();
    }//GEN-LAST:event_TanggalCairanActionPerformed

    private void TanggalBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalBalanceActionPerformed
        isRawat();
    }//GEN-LAST:event_TanggalBalanceActionPerformed

    private void TanggalCairanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalCairanItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            isRawat();
        }
    }//GEN-LAST:event_TanggalCairanItemStateChanged

    private void TanggalBalanceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalBalanceItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            isRawat();
        }
    }//GEN-LAST:event_TanggalBalanceItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataCatatanObservasiRanap dialog = new RMDataCatatanObservasiRanap(new javax.swing.JFrame(), true);
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
    private widget.TextBox Bb;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextArea Cairan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox GCS;
    private widget.TextBox HR;
    private widget.TextBox HariKe;
    private widget.TextBox Hb;
    private widget.TextBox Ht;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.TextBox KdDiagnosa;
    private widget.Label LCount;
    private widget.TextArea Lainnya;
    private widget.TextBox Leukosit;
    private widget.TextArea Malam;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnCatatanObservasiIGD;
    private widget.TextBox NIP;
    private widget.TextBox Nadi;
    private widget.TextBox NamaDiagnosa;
    private widget.TextBox NamaPetugas;
    private widget.TextArea Pagi;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox RR;
    private widget.TextBox SPO;
    private widget.ScrollPane Scroll;
    private widget.TextArea Siang;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalBalance;
    private widget.Tanggal TanggalCairan;
    private widget.TextBox Tb;
    private widget.TextArea Teraphi;
    private widget.TextBox TglLahir;
    private widget.TextBox Trombosit;
    private widget.TextBox Umur;
    private widget.Button btnDiagnosa;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);

        String cari = TCari.getText().trim();

        String sql =
            "SELECT " +
            "    a.no_rawat, " +                                      // 0
            "    c.no_rkm_medis, " +                                  // 1
            "    c.nm_pasien, " +                                     // 2
            "    CONCAT(b.umurdaftar, ' ', b.sttsumur) AS umur, " +   // 3
            "    c.jk, " +                                            // 4
            "    c.tgl_lahir, " +                                     // 5
            "    a.tgl_perawatan, " +                                 // 6
            "    a.jam_rawat, " +                                     // 7
            "    a.gcs, " +                                           // 8
            "    a.td, " +                                            // 9
            "    a.hr, " +                                            // 10
            "    a.rr, " +                                            // 11
            "    a.suhu, " +                                          // 12
            "    a.spo2, " +                                          // 13
            "    a.nadi, " +                                          // 14
            "    a.hari_ke, " +                                       // 15
            "    a.penyakit AS kd_penyakit, " +                       // 16
            "    e.nm_penyakit, " +                                   // 17
            "    a.hb, " +                                            // 18
            "    a.ht, " +                                            // 19
            "    a.trombosit, " +                                     // 20
            "    a.leukosit, " +                                      // 21
            "    a.lainnya, " +                                       // 22
            "    a.cairan, " +                                        // 23
            "    a.terapi, " +                                        // 24
            "    a.pagi, " +                                          // 25
            "    a.siang, " +                                         // 26
            "    a.malam, " +                                         // 27
            "    a.bb, " +                                            // 28
            "    a.tb, " +                                            // 29
            "    a.nip, " +                                           // 30
            "    d.nama AS nama_petugas " +                           // 31
            "FROM catatan_observasi AS a " +
            "LEFT JOIN reg_periksa AS b " +
            "    ON b.no_rawat = a.no_rawat " +
            "LEFT JOIN pasien AS c " +
            "    ON c.no_rkm_medis = b.no_rkm_medis " +
            "LEFT JOIN petugas AS d " +
            "    ON d.nip = a.nip " +
            "LEFT JOIN penyakit AS e " +
            "    ON e.kd_penyakit = a.penyakit " +
            "WHERE a.tgl_perawatan BETWEEN ? AND ? ";

        if (!cari.isEmpty()) {
            sql +=
                "AND ( " +
                "    a.no_rawat LIKE ? " +
                " OR c.no_rkm_medis LIKE ? " +
                " OR c.nm_pasien LIKE ? " +
                " OR a.nip LIKE ? " +
                " OR d.nama LIKE ? " +
                " OR a.penyakit LIKE ? " +
                " OR e.nm_penyakit LIKE ? " +
                ") ";
        }

        sql +=
            "ORDER BY " +
            "    a.tgl_perawatan DESC, " +
            "    a.jam_rawat DESC";

        try {
            ps = koneksi.prepareStatement(sql);

            /*
             * tgl_perawatan bertipe DATE, jadi tidak perlu ditambah
             * 00:00:00 dan 23:59:59.
             */
            ps.setString(
                1,
                Valid.SetTgl(DTPCari1.getSelectedItem() + "")
            );

            ps.setString(
                2,
                Valid.SetTgl(DTPCari2.getSelectedItem() + "")
            );

            if (!cari.isEmpty()) {
                String kataKunci = "%" + cari + "%";

                ps.setString(3, kataKunci);
                ps.setString(4, kataKunci);
                ps.setString(5, kataKunci);
                ps.setString(6, kataKunci);
                ps.setString(7, kataKunci);
                ps.setString(8, kataKunci);
                ps.setString(9, kataKunci);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                tabMode.addRow(new Object[] {
                    rs.getString("no_rawat"),        // 0
                    rs.getString("no_rkm_medis"),    // 1
                    rs.getString("nm_pasien"),       // 2
                    rs.getString("umur"),            // 3
                    rs.getString("jk"),              // 4
                    rs.getString("tgl_lahir"),       // 5
                    rs.getString("tgl_perawatan"),   // 6
                    rs.getString("jam_rawat"),       // 7
                    rs.getString("gcs"),             // 8
                    rs.getString("td"),              // 9
                    rs.getString("hr"),              // 10
                    rs.getString("rr"),              // 11
                    rs.getString("suhu"),            // 12
                    rs.getString("spo2"),            // 13
                    rs.getString("nadi"),            // 14
                    rs.getString("hari_ke"),         // 15
                    rs.getString("kd_penyakit"),     // 16
                    rs.getString("nm_penyakit"),     // 17
                    rs.getString("hb"),              // 18
                    rs.getString("ht"),              // 19
                    rs.getString("trombosit"),       // 20
                    rs.getString("leukosit"),        // 21
                    rs.getString("lainnya"),         // 22
                    rs.getString("cairan"),          // 23
                    rs.getString("terapi"),           // 24
                    rs.getString("pagi"),            // 25
                    rs.getString("siang"),           // 26
                    rs.getString("malam"),           // 27
                    rs.getString("bb"),              // 28
                    rs.getString("tb"),              // 29
                    rs.getString("nip"),             // 30
                    rs.getString("nama_petugas")     // 31
                });
            }

        } catch (Exception e) {
            System.out.println("Notif tampil catatan observasi: " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println("Notif close rs: " + e);
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                System.out.println("Notif close ps: " + e);
            }
        }

        LCount.setText(String.valueOf(tabMode.getRowCount()));
    }
    
    public void emptTeks() {
//        TNoRw.setText("");
//        TPasien.setText("");
//        TNoRM.setText("");
//        TglLahir.setText("");
//
//        NIP.setText("");
//        NamaPetugas.setText("");

        GCS.setText("");
        TD.setText("");
        HR.setText("");
        RR.setText("");
        Suhu.setText("");
        SPO.setText("");
        Nadi.setText("");

        KdDiagnosa.setText("");
        NamaDiagnosa.setText("");
        HariKe.setText("");

        Hb.setText("");
        Ht.setText("");
        Trombosit.setText("");
        Leukosit.setText("");
        Lainnya.setText("");

        Cairan.setText("");
        Teraphi.setText("");

        Pagi.setText("");
        Siang.setText("");
        Malam.setText("");

        Bb.setText("");
        Tb.setText("");

        Date sekarang = new Date();

//        Tanggal.setDate(sekarang);
        TanggalCairan.setDate(sekarang);
        TanggalBalance.setDate(sekarang);

        Calendar now = Calendar.getInstance();

//        Jam.setSelectedItem(
//            String.format("%02d", now.get(Calendar.HOUR_OF_DAY))
//        );
//        Menit.setSelectedItem(
//            String.format("%02d", now.get(Calendar.MINUTE))
//        );
//        Detik.setSelectedItem(
//            String.format("%02d", now.get(Calendar.SECOND))
//        );
//
//        ChkKejadian.setSelected(false);

        Hb.requestFocus();
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            int row = tbObat.getSelectedRow();

            TNoRw.setText(getValue(row, 0));
            TNoRM.setText(getValue(row, 1));
            TPasien.setText(getValue(row, 2));
            Umur.setText(getValue(row, 3));
            JK.setText(getValue(row, 4));
            TglLahir.setText(getValue(row, 5));

            if (!getValue(row, 6).equals("")) {
                Valid.SetTgl(Tanggal, getValue(row, 6));
            }

            String jamRawat = getValue(row, 7);

            if (jamRawat.length() >= 8) {
                Jam.setSelectedItem(jamRawat.substring(0, 2));
                Menit.setSelectedItem(jamRawat.substring(3, 5));
                Detik.setSelectedItem(jamRawat.substring(6, 8));
            }

            GCS.setText(getValue(row, 8));
            TD.setText(getValue(row, 9));
            HR.setText(getValue(row, 10));
            RR.setText(getValue(row, 11));
            Suhu.setText(getValue(row, 12));
            SPO.setText(getValue(row, 13));

            Nadi.setText(getValue(row, 14));
            HariKe.setText(getValue(row, 15));

            KdDiagnosa.setText(getValue(row, 16));
            NamaDiagnosa.setText(getValue(row, 17));

            Hb.setText(getValue(row, 18));
            Ht.setText(getValue(row, 19));
            Trombosit.setText(getValue(row, 20));
            Leukosit.setText(getValue(row, 21));

            Lainnya.setText(getValue(row, 22));

            Cairan.setText(getValue(row, 23));
            Teraphi.setText(getValue(row, 24));

            Pagi.setText(getValue(row, 25));
            Siang.setText(getValue(row, 26));
            Malam.setText(getValue(row, 27));

            Bb.setText(getValue(row, 28));
            Tb.setText(getValue(row, 29));

            NIP.setText(getValue(row, 30));
            NamaPetugas.setText(getValue(row, 31));
        }
    }

    private String getValue(int row, int column) {
        Object value = tbObat.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }
    
    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                "SELECT " +
                "    rp.no_rawat, " +
                "    rp.no_rkm_medis, " +
                "    p.nm_pasien, " +
                "    p.jk, " +
                "    p.tgl_lahir, " +
                "    rp.tgl_registrasi, " +
                "    rp.umurdaftar, " +
                "    rp.sttsumur, " +
                "    ki.lama, " +
                "    dp.kd_penyakit, " +
                "    py.nm_penyakit, " +

                "    MAX(CASE " +
                "        WHEN dpl.id_template = 5 THEN dpl.nilai " +
                "    END) AS hb, " +

                "    MAX(CASE " +
                "        WHEN dpl.id_template = 6 THEN dpl.nilai " +
                "    END) AS ht, " +

                "    MAX(CASE " +
                "        WHEN dpl.id_template = 10 THEN dpl.nilai " +
                "    END) AS trombosit, " +

                "    MAX(CASE " +
                "        WHEN dpl.id_template = 3 THEN dpl.nilai " +
                "    END) AS leukosit, " +

                "    MAX(tc.cairan_volume) AS cairan_volume, " +
                "    MAX(tc.cairan_tetesan) AS cairan_tetesan, " +
                "    MAX(pc.balance_07_14) AS balance_07_14, " +
                "    MAX(pc.balance_14_21) AS balance_14_21, " +
                "    MAX(pc.balance_21_07) AS balance_21_07 " +

                "FROM reg_periksa AS rp " +

                "INNER JOIN pasien AS p " +
                "    ON p.no_rkm_medis = rp.no_rkm_medis " +

                "LEFT JOIN kamar_inap AS ki " +
                "    ON ki.no_rawat = rp.no_rawat " +

                "LEFT JOIN diagnosa_pasien AS dp " +
                "    ON dp.no_rawat = rp.no_rawat " +
                "   AND dp.prioritas = 1 " +

                "LEFT JOIN penyakit AS py " +
                "    ON py.kd_penyakit = dp.kd_penyakit " +

                "LEFT JOIN detail_periksa_lab AS dpl " +
                "    ON dpl.no_rawat = rp.no_rawat " +
                "   AND dpl.id_template IN (3,5,6,10) " +

                /* Terapi cairan berdasarkan tanggal */
                "LEFT JOIN ( " +
                "    SELECT " +
                "        tc1.no_rawat, " +

                "        GROUP_CONCAT( " +
                "            CONCAT( " +
                "                COALESCE(tc1.jenis,''), " +
                "                ' (', " +
                "                COALESCE(CAST(tc1.volume AS CHAR),''), " +
                "                ')' " +
                "            ) " +
                "            ORDER BY tc1.jenis " +
                "            SEPARATOR ', ' " +
                "        ) AS cairan_volume, " +

                "        GROUP_CONCAT( " +
                "            CONCAT( " +
                "                COALESCE(tc1.jenis,''), " +
                "                ' (', " +
                "                COALESCE(CAST(tc1.tetesan AS CHAR),''), " +
                "                ')' " +
                "            ) " +
                "            ORDER BY tc1.jenis " +
                "            SEPARATOR ', ' " +
                "        ) AS cairan_tetesan " +

                "    FROM terapi_cairan AS tc1 " +
                "    WHERE tc1.tgl_perawatan = ? " +
                "    GROUP BY tc1.no_rawat " +
                ") AS tc " +
                "    ON tc.no_rawat = rp.no_rawat " +

                /* Balance berdasarkan tanggal dan kategori jam */
                "LEFT JOIN ( " +
                "    SELECT " +
                "        pc1.no_rawat, " +

                "        GROUP_CONCAT( " +
                "            CASE " +
                "                WHEN pc1.tanggal = prm.tanggal_balance " +
                "                 AND pc1.jam >= '07.00' " +
                "                 AND pc1.jam <  '14.00' " +
                "                THEN pc1.balance " +
                "            END " +
                "            ORDER BY pc1.tanggal, pc1.jam " +
                "            SEPARATOR ', ' " +
                "        ) AS balance_07_14, " +

                "        GROUP_CONCAT( " +
                "            CASE " +
                "                WHEN pc1.tanggal = prm.tanggal_balance " +
                "                 AND pc1.jam >= '14.00' " +
                "                 AND pc1.jam <  '21.00' " +
                "                THEN pc1.balance " +
                "            END " +
                "            ORDER BY pc1.tanggal, pc1.jam " +
                "            SEPARATOR ', ' " +
                "        ) AS balance_14_21, " +

                "        GROUP_CONCAT( " +
                "            CASE " +
                "                WHEN ( " +
                "                    pc1.tanggal = prm.tanggal_balance " +
                "                    AND pc1.jam >= '21.00' " +
                "                ) " +
                "                OR ( " +
                "                    pc1.tanggal = DATE_ADD( " +
                "                        prm.tanggal_balance, INTERVAL 1 DAY " +
                "                    ) " +
                "                    AND pc1.jam < '07.00' " +
                "                ) " +
                "                THEN pc1.balance " +
                "            END " +
                "            ORDER BY pc1.tanggal, pc1.jam " +
                "            SEPARATOR ', ' " +
                "        ) AS balance_21_07 " +

                "    FROM pemberian_cairan AS pc1 " +

                "    CROSS JOIN ( " +
                "        SELECT CAST(? AS DATE) AS tanggal_balance " +
                "    ) AS prm " +

                "    WHERE ( " +
                "        pc1.tanggal = prm.tanggal_balance " +
                "        AND pc1.jam >= '07.00' " +
                "    ) " +
                "    OR ( " +
                "        pc1.tanggal = DATE_ADD( " +
                "            prm.tanggal_balance, INTERVAL 1 DAY " +
                "        ) " +
                "        AND pc1.jam < '07.00' " +
                "    ) " +

                "    GROUP BY pc1.no_rawat " +
                ") AS pc " +
                "    ON pc.no_rawat = rp.no_rawat " +

                "WHERE rp.no_rawat = ? " +

                "GROUP BY " +
                "    rp.no_rawat, " +
                "    rp.no_rkm_medis, " +
                "    p.nm_pasien, " +
                "    p.jk, " +
                "    p.tgl_lahir, " +
                "    rp.tgl_registrasi, " +
                "    rp.umurdaftar, " +
                "    rp.sttsumur, " +
                "    ki.lama, " +
                "    dp.kd_penyakit, " +
                "    py.nm_penyakit"
            );

            try {
                ps.setString(
                    1,
                    Valid.SetTgl(TanggalCairan.getSelectedItem() + "")
                );

                ps.setString(
                    2,
                    Valid.SetTgl(TanggalBalance.getSelectedItem() + "")
                );

                ps.setString(
                    3,
                    TNoRw.getText().trim()
                );

                rs = ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    HariKe.setText(rs.getString("lama"));
                    KdDiagnosa.setText(rs.getString("kd_penyakit"));
                    NamaDiagnosa.setText(rs.getString("nm_penyakit"));
                    Hb.setText(rs.getString("hb"));
                    Ht.setText(rs.getString("ht"));
                    Trombosit.setText(rs.getString("trombosit"));
                    Leukosit.setText(rs.getString("leukosit"));
                    Cairan.setText(rs.getString("cairan_volume"));
                    Teraphi.setText(rs.getString("cairan_tetesan"));
                    Pagi.setText(rs.getString("balance_07_14"));
                    Siang.setText(rs.getString("balance_14_21"));
                    Malam.setText(rs.getString("balance_21_07"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,630));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getcatatan_observasi_ranap());
        BtnHapus.setEnabled(akses.getcatatan_observasi_ranap());
        BtnEdit.setEnabled(akses.getcatatan_observasi_ranap());
        BtnPrint.setEnabled(akses.getcatatan_observasi_ranap()); 
        if(akses.getjml2()>=1){
            NIP.setEditable(false);
            btnPetugas.setEnabled(false);
            NIP.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
            if(NamaPetugas.getText().equals("")){
                NIP.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }

    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =Jam.getSelectedIndex();
                    nilai_menit =Menit.getSelectedIndex();
                    nilai_detik =Detik.getSelectedIndex();
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
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void ganti() {
        if (tbObat.getSelectedRow() == -1) {
            Valid.textKosong(TNoRw, "Data");
        } else if (TNoRw.getText().trim().equals("") ||
                   TPasien.getText().trim().equals("")) {

            Valid.textKosong(TNoRw, "pasien");

        } else if (NIP.getText().trim().equals("") ||
                   NamaPetugas.getText().trim().equals("")) {

            Valid.textKosong(NIP, "Petugas");

        } else {
            int row = tbObat.getSelectedRow();

            // Kunci lama dari tabel
            String noRawatLama = tbObat.getValueAt(row, 0).toString();
            String tanggalLama = tbObat.getValueAt(row, 6).toString();
            String jamLama = tbObat.getValueAt(row, 7).toString();

            // Nilai baru dari form
            String tanggalBaru =
                Valid.SetTgl(Tanggal.getSelectedItem() + "");

            String jamBaru =
                Jam.getSelectedItem() + ":" +
                Menit.getSelectedItem() + ":" +
                Detik.getSelectedItem();

            if (Sequel.mengedittf(
                    "catatan_observasi",
                    "no_rawat=? AND tgl_perawatan=? AND jam_rawat=?",
                    "no_rawat=?," +
                    "tgl_perawatan=?," +
                    "jam_rawat=?," +
                    "gcs=?," +
                    "td=?," +
                    "hr=?," +
                    "rr=?," +
                    "suhu=?," +
                    "spo2=?," +
                    "nadi=?," +
                    "hari_ke=?," +
                    "penyakit=?," +
                    "hb=?," +
                    "ht=?," +
                    "trombosit=?," +
                    "leukosit=?," +
                    "lainnya=?," +
                    "cairan=?," +
                    "terapi=?," +
                    "pagi=?," +
                    "siang=?," +
                    "malam=?," +
                    "bb=?," +
                    "tb=?," +
                    "nip=?",
                    28,
                    new String[] {
                        // Nilai baru
                        TNoRw.getText().trim(),
                        tanggalBaru,
                        jamBaru,
                        GCS.getText().trim(),
                        TD.getText().trim(),
                        HR.getText().trim(),
                        RR.getText().trim(),
                        Suhu.getText().trim(),
                        SPO.getText().trim(),
                        Nadi.getText().trim(),
                        HariKe.getText().trim(),
                        KdDiagnosa.getText().trim(),
                        Hb.getText().trim(),
                        Ht.getText().trim(),
                        Trombosit.getText().trim(),
                        Leukosit.getText().trim(),
                        "",
                        Cairan.getText().trim(),
                        Teraphi.getText().trim(),
                        Pagi.getText().trim(),
                        Siang.getText().trim(),
                        Malam.getText().trim(),
                        Bb.getText().trim(),
                        Tb.getText().trim(),
                        NIP.getText().trim(),

                        // WHERE: nilai lama
                        noRawatLama,
                        tanggalLama,
                        jamLama
                    }
            )) {
                tampil();
                emptTeks();
            }
        }
    }

    private void hapus() {
        if (tbObat.getSelectedRow() == -1) {
            Valid.textKosong(TNoRw, "Data");
        } else {
            int row = tbObat.getSelectedRow();

            String noRawatLama = tbObat.getValueAt(row, 0).toString();
            String tanggalLama = tbObat.getValueAt(row, 6).toString();
            String jamLama = tbObat.getValueAt(row, 7).toString();

            if (Sequel.queryu2tf(
                    "DELETE FROM catatan_observasi " +
                    "WHERE no_rawat=? " +
                    "AND tgl_perawatan=? " +
                    "AND jam_rawat=?",
                    3,
                    new String[] {
                        noRawatLama,
                        tanggalLama,
                        jamLama
                    }
            )) {
                tampil();
                emptTeks();
            }
        }
    }
    
    
}
