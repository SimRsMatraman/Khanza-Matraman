/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import rekammedis.RMCariDiagnosa1;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


/**
 *
 * @author perpustakaan
 */
public final class RMChecklistKriteriaMasukICU extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private RMCariDiagnosa1 diagnosa=new RMCariDiagnosa1(null,false);
    private String finger="";
    private StringBuilder htmlContent;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMChecklistKriteriaMasukICU(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat",
            "No.RM",
            "Nama Pasien",
            "Tgl.Lahir",
            "J.K.",
            "Tanggal",
            "NIP/Kode DPJP",
            "DPJP/Dokter Jaga/IGD",
            "Kode Dokter ICU",
            "Dokter Konsultan ICU",
            "Diagnosa",
            "Prioritas 1",
            "Tensi",
            "Nadi",
            "RR",
            "GCS",
            "Memerlukan Bantuan Ventilasi",
            "Masker NRM",
            "Masker RM",
            "Ventilator",
            "Memerlukan Obat Vasoaktif",
            "Dopamin",
            "Dobutamin",
            "Adrenaline",
            "Prioritas 2",
            "Kode Penyakit Jantung",
            "Nama Penyakit Jantung",
            "Kode Penyakit Paru",
            "Nama Penyakit Paru",
            "Kode Penyakit Neurologi",
            "Nama Penyakit Neurologi",
            "Diagnosa Lainnya"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 32; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);

            if (i == 0) {
                column.setPreferredWidth(105); // No. Rawat
            } else if (i == 1) {
                column.setPreferredWidth(70);  // No. RM
            } else if (i == 2) {
                column.setPreferredWidth(150); // Nama pasien
            } else if (i == 3) {
                column.setPreferredWidth(70);  // Tanggal lahir
            } else if (i == 4) {
                column.setPreferredWidth(35);  // Jenis kelamin
            } else if (i == 5) {
                column.setPreferredWidth(120); // Tanggal pengisian
            } else if (i == 6 || i == 8) {
                column.setPreferredWidth(100); // Kode dokter
            } else if (i == 7 || i == 9) {
                column.setPreferredWidth(160); // Nama dokter
            } else if (i == 10) {
                column.setPreferredWidth(250); // Diagnosa
            } else if (i == 11 || i == 16 || i == 20 || i == 24) {
                column.setPreferredWidth(180); // Keterangan prioritas
            } else if (i == 12) {
                column.setPreferredWidth(80);  // Tensi
            } else if (i >= 13 && i <= 15) {
                column.setPreferredWidth(60);  // Nadi, RR, dan GCS
            } else if (i >= 17 && i <= 19) {
                column.setPreferredWidth(80);  // NRM, RM, ventilator
            } else if (i >= 21 && i <= 23) {
                column.setPreferredWidth(80);  // Obat vasoaktif
            } else if (i == 25 || i == 27 || i == 29) {
                column.setPreferredWidth(110); // Kode penyakit
            } else if (i == 26 || i == 28 || i == 30) {
                column.setPreferredWidth(180); // Nama penyakit
            } else if (i == 31) {
                column.setPreferredWidth(250); // Diagnosa lainnya
            } else {
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    if (i == 1) {
                        KodeDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NamaDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        btnDPJP.requestFocus();
                    } else if (i == 2) {
                        KodeDrICU.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NamaDrICU.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        btnDrICU.requestFocus();
                    }  
                }                    
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
                if (i == 1) {
                    if(diagnosa.getTable().getSelectedRow()!= -1){                   
                        PJKdPenyakit.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),0).toString());
                        PJNmPenyakit.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),1).toString()); 
                        PJKdPenyakit.requestFocus();
                    }   
                }
                else if (i == 2) {
                    if(diagnosa.getTable().getSelectedRow()!= -1){                   
                        PPKdPenyakit.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),0).toString());
                        PPNmPenyakit.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),1).toString()); 
                        PPKdPenyakit.requestFocus();
                    }   
                }
                else if (i == 3) {
                    if(diagnosa.getTable().getSelectedRow()!= -1){                   
                        PNKdPenyakit.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),0).toString());
                        PNNmPenyakit.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),1).toString()); 
                        PNKdPenyakit.requestFocus();
                    }   
                }
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
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
        MnKriteriaMasukICU = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
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
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TPasien = new widget.TextBox();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TempatLahir = new widget.TextBox();
        TglLahir = new widget.TextBox();
        Umur = new widget.TextBox();
        JK = new widget.TextBox();
        KodeDPJP = new widget.TextBox();
        NamaDPJP = new widget.TextBox();
        btnDPJP = new widget.Button();
        KodeDrICU = new widget.TextBox();
        NamaDrICU = new widget.TextBox();
        btnDrICU = new widget.Button();
        scrollPane2 = new widget.ScrollPane();
        Diagnosa = new widget.TextArea();
        Prioritas1_1 = new widget.ComboBox();
        Tensi = new widget.TextBox();
        Nadi = new widget.TextBox();
        RR = new widget.TextBox();
        GCS = new widget.TextBox();
        Prioritas1_2 = new widget.ComboBox();
        NRM = new javax.swing.JCheckBox();
        RM = new javax.swing.JCheckBox();
        Ventilator = new javax.swing.JCheckBox();
        Prioritas1_3 = new widget.ComboBox();
        Dopamin = new javax.swing.JCheckBox();
        Bobutamin = new javax.swing.JCheckBox();
        Adrenaline = new javax.swing.JCheckBox();
        Prioritas2_1 = new widget.ComboBox();
        Prioritas3_1 = new widget.ComboBox();
        PJKdPenyakit = new widget.TextBox();
        PJNmPenyakit = new widget.TextBox();
        btnPJPenyakit = new widget.Button();
        PPKdPenyakit = new widget.TextBox();
        PPNmPenyakit = new widget.TextBox();
        btnPPPenyakit = new widget.Button();
        PNKdPenyakit = new widget.TextBox();
        PNNmPenyakit = new widget.TextBox();
        btnPNPenyakit = new widget.Button();
        scrollPane3 = new widget.ScrollPane();
        DiagnosisLainnya = new widget.TextArea();
        jLabel4 = new widget.Label();
        jLabel16 = new widget.Label();
        jLabel8 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel5 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel63 = new widget.Label();
        jLabel65 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel9 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        jLabel119 = new widget.Label();
        jLabel120 = new widget.Label();
        jLabel121 = new widget.Label();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel59 = new widget.Label();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel62 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel122 = new widget.Label();
        jLabel123 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel60 = new widget.Label();
        jSeparator17 = new javax.swing.JSeparator();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKriteriaMasukICU.setBackground(new java.awt.Color(255, 255, 254));
        MnKriteriaMasukICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKriteriaMasukICU.setForeground(new java.awt.Color(50, 50, 50));
        MnKriteriaMasukICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKriteriaMasukICU.setText("Formulir Checklist Kriteria Masuk ICU");
        MnKriteriaMasukICU.setName("MnKriteriaMasukICU"); // NOI18N
        MnKriteriaMasukICU.setPreferredSize(new java.awt.Dimension(260, 26));
        MnKriteriaMasukICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKriteriaMasukICUActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKriteriaMasukICU);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Kriteria Masuk ICU ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(462, 886));
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-08-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-08-2026" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 886));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

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

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 857));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 523));
        FormInput.setLayout(null);

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

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

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

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-08-2026 14:50:21" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(80, 40, 130, 23);

        TempatLahir.setHighlighter(null);
        TempatLahir.setName("TempatLahir"); // NOI18N
        FormInput.add(TempatLahir);
        TempatLahir.setBounds(260, 40, 160, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(430, 40, 100, 23);

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N
        FormInput.add(Umur);
        Umur.setBounds(720, 10, 90, 23);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(620, 10, 50, 24);

        KodeDPJP.setEditable(false);
        KodeDPJP.setHighlighter(null);
        KodeDPJP.setName("KodeDPJP"); // NOI18N
        FormInput.add(KodeDPJP);
        KodeDPJP.setBounds(130, 70, 127, 23);

        NamaDPJP.setEditable(false);
        NamaDPJP.setName("NamaDPJP"); // NOI18N
        FormInput.add(NamaDPJP);
        NamaDPJP.setBounds(260, 70, 245, 23);

        btnDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDPJP.setMnemonic('2');
        btnDPJP.setToolTipText("ALt+2");
        btnDPJP.setName("btnDPJP"); // NOI18N
        btnDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDPJPActionPerformed(evt);
            }
        });
        btnDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDPJPKeyPressed(evt);
            }
        });
        FormInput.add(btnDPJP);
        btnDPJP.setBounds(510, 70, 28, 23);

        KodeDrICU.setEditable(false);
        KodeDrICU.setHighlighter(null);
        KodeDrICU.setName("KodeDrICU"); // NOI18N
        FormInput.add(KodeDrICU);
        KodeDrICU.setBounds(130, 100, 127, 23);

        NamaDrICU.setEditable(false);
        NamaDrICU.setName("NamaDrICU"); // NOI18N
        FormInput.add(NamaDrICU);
        NamaDrICU.setBounds(260, 100, 245, 23);

        btnDrICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDrICU.setMnemonic('2');
        btnDrICU.setToolTipText("ALt+2");
        btnDrICU.setName("btnDrICU"); // NOI18N
        btnDrICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrICUActionPerformed(evt);
            }
        });
        btnDrICU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDrICUKeyPressed(evt);
            }
        });
        FormInput.add(btnDrICU);
        btnDrICU.setBounds(510, 100, 28, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Diagnosa"));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Diagnosa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosa.setColumns(20);
        Diagnosa.setRows(5);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Diagnosa);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(540, 40, 270, 80);

        Prioritas1_1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas1_1.setSelectedIndex(1);
        Prioritas1_1.setName("Prioritas1_1"); // NOI18N
        Prioritas1_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas1_1KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas1_1);
        Prioritas1_1.setBounds(200, 160, 90, 23);

        Tensi.setHighlighter(null);
        Tensi.setName("Tensi"); // NOI18N
        FormInput.add(Tensi);
        Tensi.setBounds(120, 190, 80, 23);

        Nadi.setHighlighter(null);
        Nadi.setName("Nadi"); // NOI18N
        FormInput.add(Nadi);
        Nadi.setBounds(260, 190, 80, 23);

        RR.setHighlighter(null);
        RR.setName("RR"); // NOI18N
        FormInput.add(RR);
        RR.setBounds(120, 220, 80, 23);

        GCS.setHighlighter(null);
        GCS.setName("GCS"); // NOI18N
        FormInput.add(GCS);
        GCS.setBounds(290, 220, 80, 23);

        Prioritas1_2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas1_2.setSelectedIndex(1);
        Prioritas1_2.setName("Prioritas1_2"); // NOI18N
        Prioritas1_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Prioritas1_2ActionPerformed(evt);
            }
        });
        Prioritas1_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas1_2KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas1_2);
        Prioritas1_2.setBounds(590, 160, 90, 23);

        NRM.setText("Masker NRM");
        NRM.setName("NRM"); // NOI18N
        FormInput.add(NRM);
        NRM.setBounds(440, 190, 120, 20);

        RM.setText("Masker RM");
        RM.setName("RM"); // NOI18N
        RM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RMActionPerformed(evt);
            }
        });
        FormInput.add(RM);
        RM.setBounds(560, 190, 120, 20);

        Ventilator.setText("Ventilator");
        Ventilator.setName("Ventilator"); // NOI18N
        Ventilator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentilatorActionPerformed(evt);
            }
        });
        FormInput.add(Ventilator);
        Ventilator.setBounds(680, 190, 120, 20);

        Prioritas1_3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas1_3.setSelectedIndex(1);
        Prioritas1_3.setName("Prioritas1_3"); // NOI18N
        Prioritas1_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas1_3KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas1_3);
        Prioritas1_3.setBounds(620, 220, 90, 23);

        Dopamin.setText("Dopamin");
        Dopamin.setName("Dopamin"); // NOI18N
        Dopamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DopaminActionPerformed(evt);
            }
        });
        FormInput.add(Dopamin);
        Dopamin.setBounds(440, 250, 120, 23);

        Bobutamin.setText("Dobutamin");
        Bobutamin.setName("Bobutamin"); // NOI18N
        Bobutamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BobutaminActionPerformed(evt);
            }
        });
        FormInput.add(Bobutamin);
        Bobutamin.setBounds(560, 250, 120, 23);

        Adrenaline.setText("Adrenaline");
        Adrenaline.setName("Adrenaline"); // NOI18N
        Adrenaline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdrenalineActionPerformed(evt);
            }
        });
        FormInput.add(Adrenaline);
        Adrenaline.setBounds(680, 250, 120, 23);

        Prioritas2_1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas2_1.setSelectedIndex(1);
        Prioritas2_1.setName("Prioritas2_1"); // NOI18N
        Prioritas2_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas2_1KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas2_1);
        Prioritas2_1.setBounds(420, 310, 90, 23);

        Prioritas3_1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas3_1.setSelectedIndex(1);
        Prioritas3_1.setName("Prioritas3_1"); // NOI18N
        Prioritas3_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas3_1KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas3_1);
        Prioritas3_1.setBounds(460, 370, 90, 23);

        PJKdPenyakit.setHighlighter(null);
        PJKdPenyakit.setName("PJKdPenyakit"); // NOI18N
        FormInput.add(PJKdPenyakit);
        PJKdPenyakit.setBounds(150, 450, 60, 23);

        PJNmPenyakit.setHighlighter(null);
        PJNmPenyakit.setName("PJNmPenyakit"); // NOI18N
        FormInput.add(PJNmPenyakit);
        PJNmPenyakit.setBounds(220, 450, 200, 23);

        btnPJPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPJPenyakit.setMnemonic('2');
        btnPJPenyakit.setToolTipText("ALt+2");
        btnPJPenyakit.setName("btnPJPenyakit"); // NOI18N
        btnPJPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPJPenyakitActionPerformed(evt);
            }
        });
        btnPJPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPJPenyakitKeyPressed(evt);
            }
        });
        FormInput.add(btnPJPenyakit);
        btnPJPenyakit.setBounds(420, 450, 40, 22);

        PPKdPenyakit.setHighlighter(null);
        PPKdPenyakit.setName("PPKdPenyakit"); // NOI18N
        FormInput.add(PPKdPenyakit);
        PPKdPenyakit.setBounds(150, 500, 60, 23);

        PPNmPenyakit.setHighlighter(null);
        PPNmPenyakit.setName("PPNmPenyakit"); // NOI18N
        FormInput.add(PPNmPenyakit);
        PPNmPenyakit.setBounds(220, 500, 200, 23);

        btnPPPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPPPenyakit.setMnemonic('2');
        btnPPPenyakit.setToolTipText("ALt+2");
        btnPPPenyakit.setName("btnPPPenyakit"); // NOI18N
        btnPPPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPPPenyakitActionPerformed(evt);
            }
        });
        btnPPPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPPPenyakitKeyPressed(evt);
            }
        });
        FormInput.add(btnPPPenyakit);
        btnPPPenyakit.setBounds(420, 500, 40, 23);

        PNKdPenyakit.setHighlighter(null);
        PNKdPenyakit.setName("PNKdPenyakit"); // NOI18N
        FormInput.add(PNKdPenyakit);
        PNKdPenyakit.setBounds(150, 550, 60, 23);

        PNNmPenyakit.setHighlighter(null);
        PNNmPenyakit.setName("PNNmPenyakit"); // NOI18N
        FormInput.add(PNNmPenyakit);
        PNNmPenyakit.setBounds(220, 550, 200, 23);

        btnPNPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPNPenyakit.setMnemonic('2');
        btnPNPenyakit.setToolTipText("ALt+2");
        btnPNPenyakit.setName("btnPNPenyakit"); // NOI18N
        btnPNPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPNPenyakitActionPerformed(evt);
            }
        });
        btnPNPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPNPenyakitKeyPressed(evt);
            }
        });
        FormInput.add(btnPNPenyakit);
        btnPNPenyakit.setBounds(420, 550, 40, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Diagnosis :"));
        scrollPane3.setName("scrollPane3"); // NOI18N

        DiagnosisLainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosisLainnya.setColumns(20);
        DiagnosisLainnya.setRows(5);
        DiagnosisLainnya.setName("DiagnosisLainnya"); // NOI18N
        DiagnosisLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisLainnyaKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(DiagnosisLainnya);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(490, 450, 310, 120);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("No.Rawat");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(21, 10, 75, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        jLabel8.setText("Ttl :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(220, 40, 40, 23);

        jLabel23.setText("Dokter yang merawat :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(10, 70, 120, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Memerlukan observasi ketat karena kondisi sewaktu-waktu dapat berubah :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(50, 310, 370, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("I. PRIORITAS 1");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(30, 140, 180, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("2. Memerlukan bantuan ventilasi : ");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(420, 160, 170, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Tensi : ");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(80, 190, 40, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(20, 270, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(20, 270, 810, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("II. PRIORITAS 2");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(30, 290, 180, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("III. PRIORITAS 3");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(30, 350, 180, 23);

        jLabel24.setText("Dokter konsulan ICU :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(10, 100, 120, 20);

        jLabel9.setText("Umur :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(680, 10, 40, 23);

        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(20, 130, 790, 10);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("1. Pasien kritis tidak stabil : ");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(60, 160, 140, 23);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("Nadi : ");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(230, 190, 31, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("x/m");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(210, 220, 30, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("GCS : ");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(260, 220, 30, 23);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("RR : ");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(90, 220, 30, 23);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("x/m");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(350, 190, 30, 23);

        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(20, 280, 790, 10);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Diagnosis : ");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(90, 450, 60, 23);

        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(20, 340, 790, 10);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("3. Memerlukan obat-obatan vasoaktif : ");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(420, 220, 200, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("A. Pasien Jantung");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(60, 430, 100, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("B. Pasien Paru");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(60, 480, 100, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("C. Pasien Neurologi");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(60, 530, 160, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("D. Penyakit lainnya");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(470, 430, 170, 23);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel122.setText("Diagnosis : ");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(90, 500, 60, 23);

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel123.setText("Diagnosis : ");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(90, 550, 60, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("SESUAI DIAGNOSIS PENYAKIT");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(30, 410, 180, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("Pasien kritis dengan penyakit berat/terminal tanpa memerlukan intubasi dan RJP : ");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(60, 370, 400, 23);

        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(20, 400, 790, 10);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    if (TNoRw.getText().trim().equals("")
            || TPasien.getText().trim().equals("")) {
        Valid.textKosong(TNoRw, "pasien");
    } else if (KodeDPJP.getText().trim().equals("")
            || NamaDPJP.getText().trim().equals("")) {
        Valid.textKosong(btnDPJP, "DPJP/Dokter Jaga/IGD");
    } else if (KodeDrICU.getText().trim().equals("")
            || NamaDrICU.getText().trim().equals("")) {
        Valid.textKosong(btnDrICU, "Dokter Konsultan ICU");
    } else {
        String tanggal = Valid.SetTgl(
                Tanggal.getSelectedItem().toString()
        ) + " " + Tanggal.getSelectedItem().toString().substring(11, 19);

        if (Sequel.menyimpantf(
                "checklist_kriteria_masuk_icu",
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                "Data",
                23,
                new String[]{
                    // 1. no_rawat
                    TNoRw.getText().trim(),

                    // 2. tanggal
                    tanggal,

                    // 3. kd_pj
                    KodeDPJP.getText().trim(),

                    // 4. kd_dokter_icu
                    KodeDrICU.getText().trim(),

                    // 5. diagnosa
                    Diagnosa.getText().trim(),

                    // 6. prioritas_1
                    Prioritas1_1.getSelectedItem().toString(),

                    // 7–10. Tanda vital
                    Tensi.getText().trim(),
                    Nadi.getText().trim(),
                    RR.getText().trim(),
                    GCS.getText().trim(),

                    // 11. prioritas_1_2
                    Prioritas1_2.getSelectedItem().toString(),

                    // 12–14. Bantuan ventilasi
                    NRM.isSelected() ? "1" : "0",
                    RM.isSelected() ? "1" : "0",
                    Ventilator.isSelected() ? "1" : "0",

                    // 15. prioritas_1_3
                    Prioritas1_3.getSelectedItem().toString(),

                    // 16–18. Obat vasoaktif
                    Dopamin.isSelected() ? "1" : "0",
                    Bobutamin.isSelected() ? "1" : "0",
                    Adrenaline.isSelected() ? "1" : "0",

                    // 19. prioritas_2
                    Prioritas2_1.getSelectedItem().toString(),

                    // 20–22. Kode penyakit
                    PJKdPenyakit.getText().trim(),
                    PPKdPenyakit.getText().trim(),
                    PNKdPenyakit.getText().trim(),

                    // 23. diagnosa_lainnya
                    DiagnosisLainnya.getText().trim()
                }
        )) {
            tampil();
            emptTeks();
        }
    }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Infeksi,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
        emptTeks();
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
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
        }else if(KodeDPJP.getText().trim().equals("")||NamaDPJP.getText().trim().equals("")){
            Valid.textKosong(btnDPJP,"DPJP/Dokter Jaga/IGD");
        }else{  
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
        dokter.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private String nilaiTabel(int row, int column) {
        Object nilai = tbObat.getValueAt(row, column);
        return nilai == null ? "" : nilai.toString();
    }

    private String kotakCentang(String nilai) {
        boolean terpilih = nilai.equals("1")
                || nilai.equalsIgnoreCase("true")
                || nilai.equalsIgnoreCase("ya");

        return terpilih
                ? "<span class='box'>X</span>"
                : "<span class='box'>&nbsp;</span>";
    }

    private String kotakPilihan(String nilai, String pilihan) {
        return nilai.equalsIgnoreCase(pilihan)
                ? kotakCentang("1")
                : kotakCentang("0");
    }

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        int row = tbObat.getSelectedRow();

        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "Maaf, tidak ada data yang bisa dicetak...!!!!"
            );
            BtnBatal.requestFocus();
            this.setCursor(Cursor.getDefaultCursor());
            return;
        }

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    null,
                    "Silakan pilih data yang akan dicetak terlebih dahulu..!!"
            );
            this.setCursor(Cursor.getDefaultCursor());
            return;
        }

        try {
            String noRM = nilaiTabel(row, 1);
            String namaPasien = nilaiTabel(row, 2);
            String tanggalLahir = nilaiTabel(row, 3);
            String jenisKelamin = nilaiTabel(row, 4);

            String namaDPJP = nilaiTabel(row, 7);
            String namaDokterICU = nilaiTabel(row, 9);
            String diagnosa = nilaiTabel(row, 10).replace("\n", "<br>");

            String prioritas1 = nilaiTabel(row, 11);
            String prioritas12 = nilaiTabel(row, 16);
            String prioritas13 = nilaiTabel(row, 20);
            String prioritas2 = nilaiTabel(row, 24);

            String kodeJantung = nilaiTabel(row, 25);
            String namaJantung = nilaiTabel(row, 26);
            String kodeParu = nilaiTabel(row, 27);
            String namaParu = nilaiTabel(row, 28);
            String kodeNeurologi = nilaiTabel(row, 29);
            String namaNeurologi = nilaiTabel(row, 30);
            String diagnosaLainnya = nilaiTabel(row, 31)
                    .replace("\n", "<br>");
            String isiQRCode =
                    "Ditandatangani oleh "
                    + namaDPJP
                    + " selaku dokter DPJP pasien "
                    + namaPasien
                    + " yang dirawat di RSUD Matraman";
            String lokasiQRCode = buatQRCodeTandaTangan(isiQRCode);

            boolean adaPrioritas3 =
                    !kodeJantung.trim().equals("")
                    || !kodeParu.trim().equals("")
                    || !kodeNeurologi.trim().equals("")
                    || !diagnosaLainnya.trim().equals("");

            String prioritas3Ya = adaPrioritas3
                    ? kotakCentang("1") : kotakCentang("0");
            String prioritas3Tidak = adaPrioritas3
                    ? kotakCentang("0") : kotakCentang("1");

            StringBuilder html = new StringBuilder();

            html.append("<html>");
            html.append("<head>");
            html.append("<meta charset='UTF-8'>");
            html.append("<style>");

            html.append("@page{size:A4 portrait;margin:10mm;}");
            html.append("body{font-family:'Times New Roman',serif;");
            html.append("font-size:12px;color:#000;}");

            html.append("table{border-collapse:collapse;width:100%;}");
            html.append(".form td{border:1px solid #000;");
            html.append("padding:5px;vertical-align:top;}");

            html.append(".judul{font-size:18px;font-weight:bold;");
            html.append("text-align:center;}");

            html.append(".rs{font-family:Arial,sans-serif;");
            html.append("font-size:10px;font-weight:bold;}");

            html.append(".nomor{width:25px;text-align:center;}");
            html.append(".pilihan{width:48px;text-align:center;");
            html.append("vertical-align:middle!important;}");

            html.append(".box{display:inline-block;width:11px;");
            html.append("height:11px;border:1px solid #000;");
            html.append("text-align:center;line-height:11px;");
            html.append("font-family:Arial;font-size:10px;");
            html.append("font-weight:bold;}");

            html.append(".bagian{font-weight:bold;");
            html.append("background:#f3f3f3;}");

            html.append(".item{display:inline-block;");
            html.append("margin:3px 18px 3px 0;}");

            html.append(".sub{padding-left:24px;line-height:1.45;}");
            html.append(".kesimpulan{margin-top:7px;text-align:center;}");

            html.append("</style>");
            html.append("</head>");
            html.append("<body>");

            html.append("<table class='form'>");

            // Kepala formulir
            html.append("<tr>");
            html.append("<td colspan='2' style='width:72%;'>");
            html.append("<table style='border:0;'>");
            html.append("<tr>");

            html.append("<td style='border:0;width:130px;'>");
            html.append("<div class='rs'>");
            html.append(akses.getnamars());
            html.append("<br>");
            html.append(akses.getalamatrs());
            html.append("</div>");
            html.append("</td>");

            html.append("<td style='border:0;' class='judul'>");
            html.append("KRITERIA PASIEN MASUK ICU");
            html.append("</td>");

            html.append("</tr>");
            html.append("</table>");
            html.append("</td>");

            html.append("<td colspan='2'>No. RM: <b>");
            html.append(noRM);
            html.append("</b></td>");
            html.append("</tr>");

            // Identitas pasien
            html.append("<tr>");
            html.append("<td colspan='2'>NAMA: <b>");
            html.append(namaPasien);
            html.append("</b></td>");

            html.append("<td colspan='2'>DIAGNOSA: ");
            html.append(diagnosa);
            html.append("</td>");
            html.append("</tr>");

            html.append("<tr>");
            html.append("<td colspan='2'>TGL. LAHIR: <b>");
            html.append(tanggalLahir);
            html.append("</b> &nbsp;&nbsp; J.K.: <b>");
            html.append(jenisKelamin);
            html.append("</b></td>");

            html.append("<td colspan='2'>RUANGAN: <b>ICU</b></td>");
            html.append("</tr>");

            html.append("<tr>");
            html.append("<td colspan='2'>DOKTER YANG MERAWAT: <b>");
            html.append(namaDPJP);
            html.append("</b></td>");

            html.append("<td colspan='2'>DOKTER KONSULTAN ICU: <b>");
            html.append(namaDokterICU);
            html.append("</b></td>");
            html.append("</tr>");

            // Header kriteria
            html.append("<tr>");
            html.append("<td class='nomor'><b>NO</b></td>");
            html.append("<td class='bagian'>Prioritas 1</td>");
            html.append("<td class='pilihan'><b>YA</b></td>");
            html.append("<td class='pilihan'><b>TIDAK</b></td>");
            html.append("</tr>");

            // Prioritas 1 - Kriteria 1
            html.append("<tr>");
            html.append("<td class='nomor'>I</td>");
            html.append("<td>");
            html.append("<b>1. Pasien kritis tidak stabil</b><br>");

            html.append("<div class='sub'>");
            html.append("Tensi: <b>").append(nilaiTabel(row, 12));
            html.append("</b>&nbsp;&nbsp;&nbsp;");

            html.append("Nadi: <b>").append(nilaiTabel(row, 13));
            html.append(" x/menit</b>&nbsp;&nbsp;&nbsp;");

            html.append("RR: <b>").append(nilaiTabel(row, 14));
            html.append(" x/menit</b><br>");

            html.append("GCS: <b>").append(nilaiTabel(row, 15));
            html.append("</b>");
            html.append("</div>");

            html.append("</td>");
            html.append("<td class='pilihan'>");
            html.append(kotakPilihan(prioritas1, "Ya"));
            html.append("</td>");

            html.append("<td class='pilihan'>");
            html.append(kotakPilihan(prioritas1, "Tidak"));
            html.append("</td>");
            html.append("</tr>");

            // Prioritas 1 - Kriteria 2
            html.append("<tr>");
            html.append("<td class='nomor'>&nbsp;</td>");
            html.append("<td>");
            html.append("<b>2. Pasien memerlukan bantuan ventilasi</b><br>");

            html.append("<div class='sub'>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 17)));
            html.append(" Masker NRM</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 18)));
            html.append(" Masker RM</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 19)));
            html.append(" Ventilator</span>");

            html.append("</div>");
            html.append("</td>");

            html.append("<td class='pilihan'>");
            html.append(kotakPilihan(prioritas12, "Ya"));
            html.append("</td>");

            html.append("<td class='pilihan'>");
            html.append(kotakPilihan(prioritas12, "Tidak"));
            html.append("</td>");
            html.append("</tr>");

            // Prioritas 1 - Kriteria 3
            html.append("<tr>");
            html.append("<td class='nomor'>&nbsp;</td>");
            html.append("<td>");
            html.append("<b>3. Pasien memerlukan obat-obat vasoaktif</b><br>");

            html.append("<div class='sub'>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 21)));
            html.append(" Dopamin</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 22)));
            html.append(" Dobutamin</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 23)));
            html.append(" Adrenalin</span>");

            html.append("</div>");
            html.append("</td>");

            html.append("<td class='pilihan'>");
            html.append(kotakPilihan(prioritas13, "Ya"));
            html.append("</td>");

            html.append("<td class='pilihan'>");
            html.append(kotakPilihan(prioritas13, "Tidak"));
            html.append("</td>");
            html.append("</tr>");

            // Prioritas 2
            html.append("<tr>");
            html.append("<td class='nomor'>II</td>");
            html.append("<td>");
            html.append("<div class='bagian'>Prioritas 2</div>");
            html.append("Pasien yang memerlukan observasi ketat dan ");
            html.append("kondisinya sewaktu-waktu dapat berubah.");
            html.append("</td>");

            html.append("<td class='pilihan'>");
            html.append(kotakPilihan(prioritas2, "Ya"));
            html.append("</td>");

            html.append("<td class='pilihan'>");
            html.append(kotakPilihan(prioritas2, "Tidak"));
            html.append("</td>");
            html.append("</tr>");

            // Prioritas 3
            html.append("<tr>");
            html.append("<td class='nomor'>III</td>");
            html.append("<td>");
            html.append("<div class='bagian'>Prioritas 3</div>");
            html.append("Pasien dengan penyakit primer berat atau terminal ");
            html.append("dengan komplikasi penyakit akut, kritis yang ");
            html.append("memerlukan pertolongan untuk penyakit kritisnya ");
            html.append("tetapi tidak sampai intubasi dan RJP.");
            html.append("</td>");

            html.append("<td class='pilihan'>");
            html.append(prioritas3Ya);
            html.append("</td>");

            html.append("<td class='pilihan'>");
            html.append(prioritas3Tidak);
            html.append("</td>");
            html.append("</tr>");

            // Diagnosis penyakit
            html.append("<tr>");
            html.append("<td class='nomor'>&nbsp;</td>");
            html.append("<td class='bagian'>SESUAI DIAGNOSIS PENYAKIT</td>");
            html.append("<td class='pilihan'>&nbsp;</td>");
            html.append("<td class='pilihan'>&nbsp;</td>");
            html.append("</tr>");

            html.append("<tr>");
            html.append("<td class='nomor'>&nbsp;</td>");
            html.append("<td>");
            html.append("<b>A. Pasien Jantung</b><br>");
            html.append("<div class='sub'>Diagnosis: ");
            html.append(kodeJantung);
            if (!kodeJantung.equals("") && !namaJantung.equals("")) {
                html.append(" - ");
            }
            html.append(namaJantung);
            html.append("</div>");
            html.append("</td>");
            html.append("<td class='pilihan'>&nbsp;</td>");
            html.append("<td class='pilihan'>&nbsp;</td>");
            html.append("</tr>");

            html.append("<tr>");
            html.append("<td class='nomor'>&nbsp;</td>");
            html.append("<td>");
            html.append("<b>B. Pasien Paru</b><br>");
            html.append("<div class='sub'>Diagnosis: ");
            html.append(kodeParu);
            if (!kodeParu.equals("") && !namaParu.equals("")) {
                html.append(" - ");
            }
            html.append(namaParu);
            html.append("</div>");
            html.append("</td>");
            html.append("<td class='pilihan'>&nbsp;</td>");
            html.append("<td class='pilihan'>&nbsp;</td>");
            html.append("</tr>");

            html.append("<tr>");
            html.append("<td class='nomor'>&nbsp;</td>");
            html.append("<td>");
            html.append("<b>C. Pasien Neurologi</b><br>");
            html.append("<div class='sub'>Diagnosis: ");
            html.append(kodeNeurologi);
            if (!kodeNeurologi.equals("")
                    && !namaNeurologi.equals("")) {
                html.append(" - ");
            }
            html.append(namaNeurologi);
            html.append("</div>");
            html.append("</td>");
            html.append("<td class='pilihan'>&nbsp;</td>");
            html.append("<td class='pilihan'>&nbsp;</td>");
            html.append("</tr>");

            html.append("<tr>");
            html.append("<td class='nomor'>&nbsp;</td>");
            html.append("<td>");
            html.append("<b>D. Penyakit Lainnya</b><br>");
            html.append("<div class='sub'>Diagnosis: ");
            html.append(diagnosaLainnya);
            html.append("</div>");
            html.append("</td>");
            html.append("<td class='pilihan'>&nbsp;</td>");
            html.append("<td class='pilihan'>&nbsp;</td>");
            html.append("</tr>");

            html.append("</table>");

            html.append("<div class='kesimpulan'>");
            html.append("Berdasarkan kondisi di atas maka pasien tersebut ");
            html.append("memenuhi kriteria untuk masuk ICU.");
            html.append("</div>");

            html.append("<table style='width:100%;border:0;");
            html.append("margin-top:28px;border-collapse:collapse;'>");

            html.append("<tr>");
            html.append("<td style='width:55%;border:0;'>&nbsp;</td>");

            html.append("<td style='width:45%;border:0;");
            html.append("text-align:center;vertical-align:top;'>");

            html.append("<b>Dokter yang merawat</b><br>");

            html.append("<img src='");
            html.append(lokasiQRCode);
            html.append("' width='170' height='170'><br>");

            html.append("(<b>");
            html.append(namaDPJP);
            html.append("</b>)");

            html.append("</td>");
            html.append("</tr>");
            html.append("</table>");
            html.append("</body>");
            html.append("</html>");

            LoadHTML.setText(html.toString());

            File f = new File("FormKriteriaPasienMasukICU.html");

            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(f)
            );

            bw.write(LoadHTML.getText());
            bw.close();

            Desktop.getDesktop().browse(f.toURI());

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
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

    private void MnKriteriaMasukICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKriteriaMasukICUActionPerformed
//        if(tbObat.getSelectedRow()>-1){
//            Map<String, Object> param = new HashMap<>();
//            param.put("namars",akses.getnamars());
//            param.put("alamatrs",akses.getalamatrs());
//            param.put("kotars",akses.getkabupatenrs());
//            param.put("propinsirs",akses.getpropinsirs());
//            param.put("kontakrs",akses.getkontakrs());
//            param.put("emailrs",akses.getemailrs());   
//            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
//            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
//            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),46).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),45).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
//            Valid.MyReportqry("rptFormulirChecklistKriteriaMasukICU.jasper","report","::[ Formulir Check List Kriteria Masuk ICU ]::",
//                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_icu.tanggal,"+
//                    "checklist_kriteria_masuk_icu.prioritas1_1,checklist_kriteria_masuk_icu.prioritas1_2,checklist_kriteria_masuk_icu.prioritas1_3,"+
//                    "checklist_kriteria_masuk_icu.prioritas1_4,checklist_kriteria_masuk_icu.prioritas1_5,checklist_kriteria_masuk_icu.prioritas1_6,"+
//                    "checklist_kriteria_masuk_icu.prioritas2_1,checklist_kriteria_masuk_icu.prioritas2_2,checklist_kriteria_masuk_icu.prioritas2_3,"+
//                    "checklist_kriteria_masuk_icu.prioritas2_4,checklist_kriteria_masuk_icu.prioritas2_5,checklist_kriteria_masuk_icu.prioritas2_6,"+
//                    "checklist_kriteria_masuk_icu.prioritas2_7,checklist_kriteria_masuk_icu.prioritas2_8,checklist_kriteria_masuk_icu.prioritas3_1,"+
//                    "checklist_kriteria_masuk_icu.prioritas3_2,checklist_kriteria_masuk_icu.prioritas3_3,checklist_kriteria_masuk_icu.prioritas3_4,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_1,checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_2,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_3,checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_4,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_5,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_1,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_3,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_4,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_5,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_6,checklist_kriteria_masuk_icu.kriteria_fisiologis_radiologi_1,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_radiologi_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_1,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_3,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_4,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_5,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_6,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_7,"+
//                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_8,checklist_kriteria_masuk_icu.nik,pegawai.nama "+
//                    "from checklist_kriteria_masuk_icu inner join reg_periksa on checklist_kriteria_masuk_icu.no_rawat=reg_periksa.no_rawat "+
//                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
//                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_icu.nik "+
//                    "where checklist_kriteria_masuk_icu.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_kriteria_masuk_icu.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
//        }
    }//GEN-LAST:event_MnKriteriaMasukICUActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed

    }//GEN-LAST:event_DiagnosaKeyPressed

    private void btnDrICUKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDrICUKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDrICUKeyPressed

    private void btnDrICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrICUActionPerformed
        i = 2;
        dokter.emptTeks();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDrICUActionPerformed

    private void Prioritas1_1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas1_1KeyPressed
        Valid.pindah(evt,btnDPJP,Prioritas1_2);
    }//GEN-LAST:event_Prioritas1_1KeyPressed

    private void Prioritas1_3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas1_3KeyPressed

    }//GEN-LAST:event_Prioritas1_3KeyPressed

    private void btnDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDPJPKeyPressed
        Valid.pindah(evt,Tanggal,Prioritas1_1);
    }//GEN-LAST:event_btnDPJPKeyPressed

    private void btnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJPActionPerformed
        i = 1;
        dokter.emptTeks();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDPJPActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,btnDPJP);
    }//GEN-LAST:event_TanggalKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,Tanggal);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void Prioritas1_2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas1_2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas1_2KeyPressed

    private void VentilatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentilatorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VentilatorActionPerformed

    private void RMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RMActionPerformed

    private void Prioritas1_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Prioritas1_2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas1_2ActionPerformed

    private void AdrenalineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdrenalineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdrenalineActionPerformed

    private void BobutaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BobutaminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BobutaminActionPerformed

    private void DopaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DopaminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DopaminActionPerformed

    private void Prioritas2_1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas2_1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas2_1KeyPressed

    private void btnPJPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPJPenyakitActionPerformed
       i = 1;
       diagnosa.emptTeks();
       diagnosa.setNoRawat(TNoRw.getText().trim());
       diagnosa.setSize(
           internalFrame1.getWidth() - 20,
           internalFrame1.getHeight() - 20
       );
       diagnosa.setLocationRelativeTo(internalFrame1);
       diagnosa.setVisible(true);
    }//GEN-LAST:event_btnPJPenyakitActionPerformed

    private void btnPJPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPJPenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPJPenyakitKeyPressed

    private void btnPPPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPPenyakitActionPerformed
       i = 2;
       diagnosa.emptTeks();
       diagnosa.setNoRawat(TNoRw.getText().trim());
       diagnosa.setSize(
           internalFrame1.getWidth() - 20,
           internalFrame1.getHeight() - 20
       );
       diagnosa.setLocationRelativeTo(internalFrame1);
       diagnosa.setVisible(true);
    }//GEN-LAST:event_btnPPPenyakitActionPerformed

    private void btnPPPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPPenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPPPenyakitKeyPressed

    private void btnPNPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPNPenyakitActionPerformed
       i = 3;
       diagnosa.emptTeks();
       diagnosa.setNoRawat(TNoRw.getText().trim());
       diagnosa.setSize(
           internalFrame1.getWidth() - 20,
           internalFrame1.getHeight() - 20
       );
       diagnosa.setLocationRelativeTo(internalFrame1);
       diagnosa.setVisible(true);
    }//GEN-LAST:event_btnPNPenyakitActionPerformed

    private void btnPNPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPNPenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPNPenyakitKeyPressed

    private void DiagnosisLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosisLainnyaKeyPressed

    private void Prioritas3_1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas3_1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas3_1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistKriteriaMasukICU dialog = new RMChecklistKriteriaMasukICU(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox Adrenaline;
    private javax.swing.JCheckBox Bobutamin;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Diagnosa;
    private widget.TextArea DiagnosisLainnya;
    private javax.swing.JCheckBox Dopamin;
    private widget.PanelBiasa FormInput;
    private widget.TextBox GCS;
    private widget.TextBox JK;
    private widget.TextBox KodeDPJP;
    private widget.TextBox KodeDrICU;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnKriteriaMasukICU;
    private javax.swing.JCheckBox NRM;
    private widget.TextBox Nadi;
    private widget.TextBox NamaDPJP;
    private widget.TextBox NamaDrICU;
    private widget.TextBox PJKdPenyakit;
    private widget.TextBox PJNmPenyakit;
    private widget.TextBox PNKdPenyakit;
    private widget.TextBox PNNmPenyakit;
    private widget.TextBox PPKdPenyakit;
    private widget.TextBox PPNmPenyakit;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Prioritas1_1;
    private widget.ComboBox Prioritas1_2;
    private widget.ComboBox Prioritas1_3;
    private widget.ComboBox Prioritas2_1;
    private widget.ComboBox Prioritas3_1;
    private javax.swing.JCheckBox RM;
    private widget.TextBox RR;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TempatLahir;
    private widget.TextBox Tensi;
    private widget.TextBox TglLahir;
    private widget.TextBox Umur;
    private javax.swing.JCheckBox Ventilator;
    private widget.Button btnDPJP;
    private widget.Button btnDrICU;
    private widget.Button btnPJPenyakit;
    private widget.Button btnPNPenyakit;
    private widget.Button btnPPPenyakit;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel68;
    private widget.Label jLabel7;
    private widget.Label jLabel72;
    private widget.Label jLabel77;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "SELECT "+
                    "    c.no_rawat, "+
                    "    p.no_rkm_medis, "+
                    "    p.nm_pasien, "+
                    "    p.tgl_lahir, "+
                    "    p.jk, "+
                    "    c.tanggal, "+
                    "    c.kd_pj, "+
                    "    dpjp.nama AS nama_dpjp, "+
                    "    c.kd_dokter_icu, "+
                    "    IFNULL(dricu.nm_dokter, '') AS nama_dokter_icu, "+
                    "    c.diagnosa, "+
                    "    c.prioritas_1, "+
                    "    c.tensi, "+
                    "    c.nadi, "+
                    "    c.rr, "+
                    "    c.gcs, "+
                    "    c.prioritas_1_2, "+
                    "    c.nrm, "+
                    "    c.rm, "+
                    "    c.ventilator, "+
                    "    c.prioritas_1_3, "+
                    "    c.dopamin, "+
                    "    c.dobutamin, "+
                    "    c.adrenaline, "+
                    "    c.prioritas_2, "+
                    "    c.pj_kd_penyakit, "+
                    "    IFNULL(pj.nm_penyakit, '') AS nama_penyakit_jantung, "+
                    "    c.pp_kd_penyakit, "+
                    "    IFNULL(pp.nm_penyakit, '') AS nama_penyakit_paru, "+
                    "    c.pn_kd_penyakit, "+
                    "    IFNULL(pn.nm_penyakit, '') AS nama_penyakit_neurologi, "+
                    "    c.diagnosa_lainnya "+
                    "FROM checklist_kriteria_masuk_icu AS c "+
                    "INNER JOIN reg_periksa AS rp ON c.no_rawat = rp.no_rawat "+
                    "INNER JOIN pasien AS p ON rp.no_rkm_medis = p.no_rkm_medis "+
                    "INNER JOIN pegawai AS dpjp ON c.kd_pj = dpjp.nik "+
                    "LEFT JOIN dokter AS dricu ON c.kd_dokter_icu = dricu.kd_dokter "+
                    "LEFT JOIN penyakit AS pj ON c.pj_kd_penyakit = pj.kd_penyakit "+
                    "LEFT JOIN penyakit AS pp ON c.pp_kd_penyakit = pp.kd_penyakit "+
                    "LEFT JOIN penyakit AS pn ON c.pn_kd_penyakit = pn.kd_penyakit "+
                    "WHERE c.tanggal between ? and ? order by c.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "SELECT "+
                    "    c.no_rawat, "+
                    "    p.no_rkm_medis, "+
                    "    p.nm_pasien, "+
                    "    p.tgl_lahir, "+
                    "    p.jk, "+
                    "    c.tanggal, "+
                    "    c.kd_pj, "+
                    "    dpjp.nama AS nama_dpjp, "+
                    "    c.kd_dokter_icu, "+
                    "    IFNULL(dricu.nm_dokter, '') AS nama_dokter_icu, "+
                    "    c.diagnosa, "+
                    "    c.prioritas_1, "+
                    "    c.tensi, "+
                    "    c.nadi, "+
                    "    c.rr, "+
                    "    c.gcs, "+
                    "    c.prioritas_1_2, "+
                    "    c.nrm, "+
                    "    c.rm, "+
                    "    c.ventilator, "+
                    "    c.prioritas_1_3, "+
                    "    c.dopamin, "+
                    "    c.dobutamin, "+
                    "    c.adrenaline, "+
                    "    c.prioritas_2, "+
                    "    c.pj_kd_penyakit, "+
                    "    IFNULL(pj.nm_penyakit, '') AS nama_penyakit_jantung, "+
                    "    c.pp_kd_penyakit, "+
                    "    IFNULL(pp.nm_penyakit, '') AS nama_penyakit_paru, "+
                    "    c.pn_kd_penyakit, "+
                    "    IFNULL(pn.nm_penyakit, '') AS nama_penyakit_neurologi, "+
                    "    c.diagnosa_lainnya "+
                    "FROM checklist_kriteria_masuk_icu AS c "+
                    "INNER JOIN reg_periksa AS rp ON c.no_rawat = rp.no_rawat "+
                    "INNER JOIN pasien AS p ON rp.no_rkm_medis = p.no_rkm_medis "+
                    "INNER JOIN pegawai AS dpjp ON c.kd_pj = dpjp.nik "+
                    "LEFT JOIN dokter AS dricu ON c.kd_dokter_icu = dricu.kd_dokter "+
                    "LEFT JOIN penyakit AS pj ON c.pj_kd_penyakit = pj.kd_penyakit "+
                    "LEFT JOIN penyakit AS pp ON c.pp_kd_penyakit = pp.kd_penyakit "+
                    "LEFT JOIN penyakit AS pn ON c.pn_kd_penyakit = pn.kd_penyakit "+
                    "WHERE c.tanggal between ? and ? and (rp.no_rawat like ? or p.no_rkm_medis like ? or "+
                    "p.nm_pasien like ? or dpjp.nama like ? or c.kd_pj like ?) order by c.tanggal ");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("tgl_lahir"),
                        rs.getString("jk"),
                        rs.getString("tanggal"),

                        rs.getString("kd_pj"),
                        rs.getString("nama_dpjp"),

                        rs.getString("kd_dokter_icu"),
                        rs.getString("nama_dokter_icu"),

                        rs.getString("diagnosa"),
                        rs.getString("prioritas_1"),

                        rs.getString("tensi"),
                        rs.getString("nadi"),
                        rs.getString("rr"),
                        rs.getString("gcs"),

                        rs.getString("prioritas_1_2"),
                        rs.getString("nrm"),
                        rs.getString("rm"),
                        rs.getString("ventilator"),

                        rs.getString("prioritas_1_3"),
                        rs.getString("dopamin"),
                        rs.getString("dobutamin"),
                        rs.getString("adrenaline"),

                        rs.getString("prioritas_2"),

                        rs.getString("pj_kd_penyakit"),
                        rs.getString("nama_penyakit_jantung"),

                        rs.getString("pp_kd_penyakit"),
                        rs.getString("nama_penyakit_paru"),

                        rs.getString("pn_kd_penyakit"),
                        rs.getString("nama_penyakit_neurologi"),

                        rs.getString("diagnosa_lainnya")
                    });
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void emptTeks() {
        // Diagnosis
        Diagnosa.setText("");

        // Prioritas
        Prioritas1_1.setSelectedIndex(1);
        Prioritas1_2.setSelectedIndex(1);
        Prioritas1_3.setSelectedIndex(1);
        Prioritas2_1.setSelectedIndex(1);

        // Tanda vital
        Tensi.setText("");
        Nadi.setText("");
        RR.setText("");
        GCS.setText("");

        // Bantuan ventilasi
        NRM.setSelected(false);
        RM.setSelected(false);
        Ventilator.setSelected(false);

        // Obat vasoaktif
        Dopamin.setSelected(false);
        Bobutamin.setSelected(false);
        Adrenaline.setSelected(false);

        // Penyakit jantung
        PJKdPenyakit.setText("");
        PJNmPenyakit.setText("");

        // Penyakit paru
        PPKdPenyakit.setText("");
        PPNmPenyakit.setText("");

        // Penyakit neurologi
        PNKdPenyakit.setText("");
        PNNmPenyakit.setText("");

        // Diagnosis penyakit lainnya
        DiagnosisLainnya.setText("");

        // Tanggal dan fokus awal
        Tanggal.setDate(new Date());
        Prioritas1_1.requestFocus();
    }

    private void getData() {
        int row = tbObat.getSelectedRow();

        if (row != -1) {
            TNoRw.setText(tbObat.getValueAt(row, 0).toString());
            TNoRM.setText(tbObat.getValueAt(row, 1).toString());
            TPasien.setText(tbObat.getValueAt(row, 2).toString());
            TglLahir.setText(tbObat.getValueAt(row, 3).toString());
            JK.setText(tbObat.getValueAt(row, 4).toString());

            Valid.SetTgl2(
                    Tanggal,
                    tbObat.getValueAt(row, 5).toString()
            );

            KodeDPJP.setText(tbObat.getValueAt(row, 6).toString());
            NamaDPJP.setText(tbObat.getValueAt(row, 7).toString());

            KodeDrICU.setText(tbObat.getValueAt(row, 8).toString());
            NamaDrICU.setText(tbObat.getValueAt(row, 9).toString());

            Diagnosa.setText(tbObat.getValueAt(row, 10).toString());

            Prioritas1_1.setSelectedItem(
                    tbObat.getValueAt(row, 11).toString()
            );

            Tensi.setText(tbObat.getValueAt(row, 12).toString());
            Nadi.setText(tbObat.getValueAt(row, 13).toString());
            RR.setText(tbObat.getValueAt(row, 14).toString());
            GCS.setText(tbObat.getValueAt(row, 15).toString());

            Prioritas1_2.setSelectedItem(
                    tbObat.getValueAt(row, 16).toString()
            );

            NRM.setSelected(
                    tbObat.getValueAt(row, 17).toString().equals("1")
            );

            RM.setSelected(
                    tbObat.getValueAt(row, 18).toString().equals("1")
            );

            Ventilator.setSelected(
                    tbObat.getValueAt(row, 19).toString().equals("1")
            );

            Prioritas1_3.setSelectedItem(
                    tbObat.getValueAt(row, 20).toString()
            );

            Dopamin.setSelected(
                    tbObat.getValueAt(row, 21).toString().equals("1")
            );

            Bobutamin.setSelected(
                    tbObat.getValueAt(row, 22).toString().equals("1")
            );

            Adrenaline.setSelected(
                    tbObat.getValueAt(row, 23).toString().equals("1")
            );

            Prioritas2_1.setSelectedItem(
                    tbObat.getValueAt(row, 24).toString()
            );

            PJKdPenyakit.setText(
                    tbObat.getValueAt(row, 25).toString()
            );

            PJNmPenyakit.setText(
                    tbObat.getValueAt(row, 26).toString()
            );

            PPKdPenyakit.setText(
                    tbObat.getValueAt(row, 27).toString()
            );

            PPNmPenyakit.setText(
                    tbObat.getValueAt(row, 28).toString()
            );

            PNKdPenyakit.setText(
                    tbObat.getValueAt(row, 29).toString()
            );

            PNNmPenyakit.setText(
                    tbObat.getValueAt(row, 30).toString()
            );

            DiagnosisLainnya.setText(
                    tbObat.getValueAt(row, 31).toString()
            );
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "SELECT "+
                    "    rp.no_rkm_medis, "+
                    "    p.nm_pasien, "+
                    "    p.jk, "+
                    "    p.tmp_lahir, "+
                    "    p.tgl_lahir, "+
                    "    p.umur, "+
                    "    rp.tgl_registrasi, "+
                    "    IFNULL(d.diagnosa, '') AS semua_diagnosa "+
                    "FROM reg_periksa AS rp "+
                    "INNER JOIN pasien AS p "+
                    "    ON rp.no_rkm_medis = p.no_rkm_medis "+
                    "LEFT JOIN ( "+
                    "    SELECT "+
                    "        dp.no_rawat, "+
                    "        GROUP_CONCAT( "+
                    "            CONCAT( "+
                    "                dp.kd_penyakit, "+
                    "                ' - ', "+
                    "                py.nm_penyakit "+
                    "            ) "+
                    "            ORDER BY dp.prioritas "+
                    "            SEPARATOR ', ' "+
                    "        ) AS diagnosa "+
                    "    FROM diagnosa_pasien AS dp "+
                    "    INNER JOIN penyakit AS py "+
                    "        ON dp.kd_penyakit = py.kd_penyakit "+
                    "    GROUP BY dp.no_rawat "+
                    ") AS d "+
                    "    ON rp.no_rawat = d.no_rawat "+
                    "WHERE rp.no_rawat =?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TempatLahir.setText(rs.getString("tmp_lahir"));
                    Umur.setText(rs.getString("umur"));
                    Diagnosa.setText(rs.getString("semua_diagnosa"));
                    DiagnosisLainnya.setText(rs.getString("semua_diagnosa"));
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,620));
//            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-282));
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
        BtnSimpan.setEnabled(akses.getchecklist_kriteria_masuk_icu());
        BtnHapus.setEnabled(akses.getchecklist_kriteria_masuk_icu());
        BtnEdit.setEnabled(akses.getchecklist_kriteria_masuk_icu());
        BtnPrint.setEnabled(akses.getchecklist_kriteria_masuk_icu()); 
        if(akses.getjml2()>=1){
            btnDPJP.setEnabled(false);
            KodeDPJP.setText(akses.getkode());
            NamaDPJP.setText(dokter.tampil3(akses.getkode()));
        }
    }

    private void ganti() {
        int row = tbObat.getSelectedRow();

        if (row == -1) {
            return;
        }

        String tanggal = Valid.SetTgl(
                Tanggal.getSelectedItem().toString()
        ) + " " + Tanggal.getSelectedItem().toString().substring(11, 19);

        String nrm = NRM.isSelected() ? "1" : "0";
        String rm = RM.isSelected() ? "1" : "0";
        String ventilator = Ventilator.isSelected() ? "1" : "0";
        String dopamin = Dopamin.isSelected() ? "1" : "0";
        String dobutamin = Bobutamin.isSelected() ? "1" : "0";
        String adrenaline = Adrenaline.isSelected() ? "1" : "0";

        if (Sequel.mengedittf(
                "checklist_kriteria_masuk_icu",
                "no_rawat=? AND tanggal=?",
                "no_rawat=?,"
                + "tanggal=?,"
                + "kd_pj=?,"
                + "kd_dokter_icu=?,"
                + "diagnosa=?,"
                + "prioritas_1=?,"
                + "tensi=?,"
                + "nadi=?,"
                + "rr=?,"
                + "gcs=?,"
                + "prioritas_1_2=?,"
                + "nrm=?,"
                + "rm=?,"
                + "ventilator=?,"
                + "prioritas_1_3=?,"
                + "dopamin=?,"
                + "dobutamin=?,"
                + "adrenaline=?,"
                + "prioritas_2=?,"
                + "pj_kd_penyakit=?,"
                + "pp_kd_penyakit=?,"
                + "pn_kd_penyakit=?,"
                + "diagnosa_lainnya=?",
                25,
                new String[]{
                    // Data baru
                    TNoRw.getText().trim(),
                    tanggal,
                    KodeDPJP.getText().trim(),
                    KodeDrICU.getText().trim(),
                    Diagnosa.getText().trim(),
                    Prioritas1_1.getSelectedItem().toString(),
                    Tensi.getText().trim(),
                    Nadi.getText().trim(),
                    RR.getText().trim(),
                    GCS.getText().trim(),
                    Prioritas1_2.getSelectedItem().toString(),
                    nrm,
                    rm,
                    ventilator,
                    Prioritas1_3.getSelectedItem().toString(),
                    dopamin,
                    dobutamin,
                    adrenaline,
                    Prioritas2_1.getSelectedItem().toString(),
                    PJKdPenyakit.getText().trim(),
                    PPKdPenyakit.getText().trim(),
                    PNKdPenyakit.getText().trim(),
                    DiagnosisLainnya.getText().trim(),

                    // Primary key data lama untuk WHERE
                    tbObat.getValueAt(row, 0).toString(),
                    tbObat.getValueAt(row, 5).toString()
                }
        )) {
            tbObat.setValueAt(TNoRw.getText().trim(), row, 0);
            tbObat.setValueAt(TNoRM.getText().trim(), row, 1);
            tbObat.setValueAt(TPasien.getText().trim(), row, 2);
            tbObat.setValueAt(TglLahir.getText().trim(), row, 3);
            tbObat.setValueAt(JK.getText().trim(), row, 4);
            tbObat.setValueAt(tanggal, row, 5);

            tbObat.setValueAt(KodeDPJP.getText().trim(), row, 6);
            tbObat.setValueAt(NamaDPJP.getText().trim(), row, 7);

            tbObat.setValueAt(KodeDrICU.getText().trim(), row, 8);
            tbObat.setValueAt(NamaDrICU.getText().trim(), row, 9);

            tbObat.setValueAt(Diagnosa.getText().trim(), row, 10);
            tbObat.setValueAt(
                    Prioritas1_1.getSelectedItem().toString(), row, 11
            );

            tbObat.setValueAt(Tensi.getText().trim(), row, 12);
            tbObat.setValueAt(Nadi.getText().trim(), row, 13);
            tbObat.setValueAt(RR.getText().trim(), row, 14);
            tbObat.setValueAt(GCS.getText().trim(), row, 15);

            tbObat.setValueAt(
                    Prioritas1_2.getSelectedItem().toString(), row, 16
            );

            tbObat.setValueAt(nrm, row, 17);
            tbObat.setValueAt(rm, row, 18);
            tbObat.setValueAt(ventilator, row, 19);

            tbObat.setValueAt(
                    Prioritas1_3.getSelectedItem().toString(), row, 20
            );

            tbObat.setValueAt(dopamin, row, 21);
            tbObat.setValueAt(dobutamin, row, 22);
            tbObat.setValueAt(adrenaline, row, 23);

            tbObat.setValueAt(
                    Prioritas2_1.getSelectedItem().toString(), row, 24
            );

            tbObat.setValueAt(PJKdPenyakit.getText().trim(), row, 25);
            tbObat.setValueAt(PJNmPenyakit.getText().trim(), row, 26);

            tbObat.setValueAt(PPKdPenyakit.getText().trim(), row, 27);
            tbObat.setValueAt(PPNmPenyakit.getText().trim(), row, 28);

            tbObat.setValueAt(PNKdPenyakit.getText().trim(), row, 29);
            tbObat.setValueAt(PNNmPenyakit.getText().trim(), row, 30);

            tbObat.setValueAt(DiagnosisLainnya.getText().trim(), row, 31);

            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_kriteria_masuk_icu where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private String buatQRCodeTandaTangan(String isiQRCode) throws Exception {
        int ukuran = 180;

        BitMatrix matrix = new MultiFormatWriter().encode(
                isiQRCode,
                BarcodeFormat.QR_CODE,
                ukuran,
                ukuran
        );

        BufferedImage gambar = new BufferedImage(
                ukuran,
                ukuran,
                BufferedImage.TYPE_INT_RGB
        );

        for (int x = 0; x < ukuran; x++) {
            for (int y = 0; y < ukuran; y++) {
                gambar.setRGB(
                        x,
                        y,
                        matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF
                );
            }
        }

        File fileQRCode = File.createTempFile(
                "ttd_dpjp_",
                ".png"
        );

        ImageIO.write(gambar, "PNG", fileQRCode);

        return fileQRCode.toURI().toString();
    }
}
