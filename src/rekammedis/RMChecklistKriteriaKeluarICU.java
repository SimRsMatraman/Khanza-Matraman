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
import kepegawaian.DlgCariPegawai;
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
public final class RMChecklistKriteriaKeluarICU extends javax.swing.JDialog {
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
    public RMChecklistKriteriaKeluarICU(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat",
            "No.RM",
            "Nama Pasien",
            "Tempat Lahir",
            "Tgl.Lahir",
            "Umur",
            "J.K.",
            "Tanggal",
            "NIP/Kode DPJP",
            "DPJP/Dokter Jaga/IGD",
            "Kode Dokter ICU",
            "Dokter Konsultan ICU",
            "Diagnosa",
            "Poin 1",
            "Masker NRM",
            "Jackson Rees",
            "Masker RM",
            "Ventilator",
            "Dopamin",
            "Dobutamin",
            "Non Adrenaline",
            "Adrenaline",
            "Poin 2",
            "Nadi",
            "TD",
            "Diuresis",
            "MAP",
            "RR",
            "SpO2",
            "Poin 3",
            "Lainnya",
            "Rawat Inap",
            "Rujuk",
            "APS"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 34; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);

            // Kolom yang disembunyikan
            if ((i >= 14 && i <= 21)
                    || (i >= 23 && i <= 28)
                    || (i >= 31 && i <= 33)) {

                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setPreferredWidth(0);
                column.setResizable(false);

            } else {
                if (i == 0) {
                    column.setPreferredWidth(105); // No. Rawat
                } else if (i == 1) {
                    column.setPreferredWidth(70);  // No. RM
                } else if (i == 2) {
                    column.setPreferredWidth(150); // Nama pasien
                } else if (i == 3) {
                    column.setPreferredWidth(100); // Tempat lahir
                } else if (i == 4) {
                    column.setPreferredWidth(70);  // Tanggal lahir
                } else if (i == 5) {
                    column.setPreferredWidth(60);  // Umur
                } else if (i == 6) {
                    column.setPreferredWidth(35);  // Jenis kelamin
                } else if (i == 7) {
                    column.setPreferredWidth(120); // Tanggal
                } else if (i == 8 || i == 10) {
                    column.setPreferredWidth(100); // Kode dokter
                } else if (i == 9 || i == 11) {
                    column.setPreferredWidth(160); // Nama dokter
                } else if (i == 12) {
                    column.setPreferredWidth(250); // Diagnosa
                } else if (i == 13) {
                    column.setPreferredWidth(180); // Poin 1
                } else if (i == 22) {
                    column.setPreferredWidth(180); // Poin 2
                } else if (i == 29) {
                    column.setPreferredWidth(180); // Poin 3
                } else if (i == 30) {
                    column.setPreferredWidth(250); // Lainnya
                } else {
                    column.setPreferredWidth(100);
                }
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

        MnKriteriaKeluarICU = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
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
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
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
        Poin1 = new widget.ComboBox();
        NRM = new javax.swing.JCheckBox();
        JacsonRes = new javax.swing.JCheckBox();
        RM = new javax.swing.JCheckBox();
        Ventilator = new javax.swing.JCheckBox();
        Dopamin = new javax.swing.JCheckBox();
        Dobutamin = new javax.swing.JCheckBox();
        NonAdrenaline = new javax.swing.JCheckBox();
        Adrenaline = new javax.swing.JCheckBox();
        Poin2 = new widget.ComboBox();
        Nadi = new javax.swing.JCheckBox();
        Td = new javax.swing.JCheckBox();
        Diuresis = new javax.swing.JCheckBox();
        Map = new javax.swing.JCheckBox();
        Rr = new javax.swing.JCheckBox();
        SpO2 = new javax.swing.JCheckBox();
        Poin3 = new widget.ComboBox();
        jLabel119 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        Lainnya = new widget.TextArea();
        Ranap = new javax.swing.JCheckBox();
        Rujuk = new javax.swing.JCheckBox();
        APS = new javax.swing.JCheckBox();
        jLabel4 = new widget.Label();
        jLabel16 = new widget.Label();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel120 = new widget.Label();
        jLabel121 = new widget.Label();

        MnKriteriaKeluarICU.setBackground(new java.awt.Color(255, 255, 254));
        MnKriteriaKeluarICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKriteriaKeluarICU.setForeground(new java.awt.Color(50, 50, 50));
        MnKriteriaKeluarICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKriteriaKeluarICU.setText("Formulir Checklist Kriteria Keluar ICU");
        MnKriteriaKeluarICU.setName("MnKriteriaKeluarICU"); // NOI18N
        MnKriteriaKeluarICU.setPreferredSize(new java.awt.Dimension(260, 26));
        MnKriteriaKeluarICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKriteriaKeluarICUActionPerformed(evt);
            }
        });

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Kriteria Keluar ICU ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-08-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-08-2026" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 256));
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
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 203));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-08-2026 11:08:45" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 130, 23);

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

        Poin1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Poin1.setSelectedIndex(1);
        Poin1.setName("Poin1"); // NOI18N
        Poin1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Poin1KeyPressed(evt);
            }
        });
        FormInput.add(Poin1);
        Poin1.setBounds(350, 140, 90, 23);

        NRM.setText("Masker NRM");
        NRM.setName("NRM"); // NOI18N
        FormInput.add(NRM);
        NRM.setBounds(40, 170, 120, 20);

        JacsonRes.setText("Jacson Res");
        JacsonRes.setName("JacsonRes"); // NOI18N
        JacsonRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JacsonResActionPerformed(evt);
            }
        });
        FormInput.add(JacsonRes);
        JacsonRes.setBounds(280, 170, 100, 20);

        RM.setText("Masker RM");
        RM.setName("RM"); // NOI18N
        RM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RMActionPerformed(evt);
            }
        });
        FormInput.add(RM);
        RM.setBounds(160, 170, 120, 20);

        Ventilator.setText("Ventilator");
        Ventilator.setName("Ventilator"); // NOI18N
        Ventilator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentilatorActionPerformed(evt);
            }
        });
        FormInput.add(Ventilator);
        Ventilator.setBounds(420, 170, 120, 20);

        Dopamin.setText("Dopamin");
        Dopamin.setName("Dopamin"); // NOI18N
        Dopamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DopaminActionPerformed(evt);
            }
        });
        FormInput.add(Dopamin);
        Dopamin.setBounds(40, 200, 120, 23);

        Dobutamin.setText("Dobutamin");
        Dobutamin.setName("Dobutamin"); // NOI18N
        Dobutamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DobutaminActionPerformed(evt);
            }
        });
        FormInput.add(Dobutamin);
        Dobutamin.setBounds(160, 200, 120, 23);

        NonAdrenaline.setText("Non Adrenaline");
        NonAdrenaline.setName("NonAdrenaline"); // NOI18N
        NonAdrenaline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NonAdrenalineActionPerformed(evt);
            }
        });
        FormInput.add(NonAdrenaline);
        NonAdrenaline.setBounds(280, 200, 130, 23);

        Adrenaline.setText("Adrenaline");
        Adrenaline.setName("Adrenaline"); // NOI18N
        Adrenaline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdrenalineActionPerformed(evt);
            }
        });
        FormInput.add(Adrenaline);
        Adrenaline.setBounds(420, 200, 120, 23);

        Poin2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Poin2.setSelectedIndex(1);
        Poin2.setName("Poin2"); // NOI18N
        Poin2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Poin2KeyPressed(evt);
            }
        });
        FormInput.add(Poin2);
        Poin2.setBounds(720, 240, 90, 23);

        Nadi.setText("Nadi >60/<100 x/menit");
        Nadi.setName("Nadi"); // NOI18N
        FormInput.add(Nadi);
        Nadi.setBounds(40, 270, 190, 23);

        Td.setText("TD Diastolik <110 mmHg");
        Td.setName("Td"); // NOI18N
        Td.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TdActionPerformed(evt);
            }
        });
        FormInput.add(Td);
        Td.setBounds(260, 270, 180, 23);

        Diuresis.setText("Diuresis >0.5 cc/kgBB/Jam");
        Diuresis.setName("Diuresis"); // NOI18N
        Diuresis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiuresisActionPerformed(evt);
            }
        });
        FormInput.add(Diuresis);
        Diuresis.setBounds(480, 270, 190, 23);

        Map.setText("MAP >65 mmHg");
        Map.setName("Map"); // NOI18N
        Map.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MapActionPerformed(evt);
            }
        });
        FormInput.add(Map);
        Map.setBounds(40, 300, 190, 23);

        Rr.setText("RR <30 x/menit");
        Rr.setName("Rr"); // NOI18N
        Rr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RrActionPerformed(evt);
            }
        });
        FormInput.add(Rr);
        Rr.setBounds(260, 300, 180, 23);

        SpO2.setText("SpO2 >93% dengan nasal canule");
        SpO2.setName("SpO2"); // NOI18N
        SpO2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SpO2ActionPerformed(evt);
            }
        });
        FormInput.add(SpO2);
        SpO2.setBounds(480, 300, 230, 23);

        Poin3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Poin3.setSelectedIndex(1);
        Poin3.setName("Poin3"); // NOI18N
        Poin3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Poin3KeyPressed(evt);
            }
        });
        FormInput.add(Poin3);
        Poin3.setBounds(510, 360, 90, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText(" prognosis jangka pendek kecil dan tidak ada terapi potensial untuk memperbaiki prognosisnya : ");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(40, 360, 460, 20);

        scrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("LAIN - LAIN : "));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Lainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Lainnya.setColumns(20);
        Lainnya.setRows(5);
        Lainnya.setName("Lainnya"); // NOI18N
        Lainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainnyaKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(Lainnya);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(40, 400, 570, 120);

        Ranap.setText("Ruang Rawat Inap");
        Ranap.setName("Ranap"); // NOI18N
        FormInput.add(Ranap);
        Ranap.setBounds(150, 530, 140, 20);

        Rujuk.setText("Rumah Sakit Rujukan");
        Rujuk.setName("Rujuk"); // NOI18N
        Rujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RujukActionPerformed(evt);
            }
        });
        FormInput.add(Rujuk);
        Rujuk.setBounds(300, 530, 150, 20);

        APS.setText("Atas Permintaan Sendiri");
        APS.setName("APS"); // NOI18N
        APS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                APSActionPerformed(evt);
            }
        });
        FormInput.add(APS);
        APS.setBounds(460, 530, 160, 20);

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

        jLabel9.setText("Umur :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(680, 10, 40, 23);

        jLabel23.setText("Dokter yang merawat :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(10, 70, 120, 23);

        jLabel24.setText("Dokter konsulan ICU :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(10, 100, 120, 20);

        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(20, 130, 790, 10);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("1. Pasien tidak lagi memerlukan alat atau obat untuk life support  : ");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(30, 140, 320, 23);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("2. Pasien dalam kondisi stabil normal (sesuai parameter base line) dan kemungkinkan kebutuhan terapi intensif secara mendadak kecil/kurang : ");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(30, 240, 690, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("3. Manfaat terapi intensif kecil karena penyakit primernya sudah terminal, tidak berespons terhadap terapi ICU untuk penyakit akutnya, ");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(30, 340, 690, 23);

        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(20, 330, 790, 23);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("2. Pasien dalam kondisi stabil normal (sesuai parameter base line) dan kemungkinkan kebutuhan terapi intensif secara mendadak kecil/kurang : ");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(30, 240, 690, 23);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("Tujuan Keluar ICU : ");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(40, 530, 130, 20);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

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
        String nrm = NRM.isSelected() ? "1" : "0";
        String jacsonRes = JacsonRes.isSelected() ? "1" : "0";
        String rm = RM.isSelected() ? "1" : "0";
        String ventilator = Ventilator.isSelected() ? "1" : "0";
        String dopamin = Dopamin.isSelected() ? "1" : "0";
        String dobutamin = Dobutamin.isSelected() ? "1" : "0";
        String nonAdrenaline = NonAdrenaline.isSelected() ? "1" : "0";
        String adrenaline = Adrenaline.isSelected() ? "1" : "0";
        String ranap = Ranap.isSelected() ? "1" : "0";
        String rujuk = Rujuk.isSelected() ? "1" : "0";
        String aps = APS.isSelected() ? "1" : "0";
        String nadi = Nadi.isSelected() ? "1" : "0";
        String td = Td.isSelected() ? "1" : "0";
        String diuresis = Diuresis.isSelected() ? "1" : "0";
        String map = Map.isSelected() ? "1" : "0";
        String rr = Rr.isSelected() ? "1" : "0";
        String spo2 = SpO2.isSelected() ? "1" : "0";
        if (Sequel.menyimpantf(
                "checklist_kriteria_keluar_icu",
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                "Data",
                26,
                new String[]{
                    // 1–5
                    TNoRw.getText().trim(),
                    tanggal,
                    KodeDPJP.getText().trim(),
                    KodeDrICU.getText().trim(),
                    Diagnosa.getText().trim(),
                    Poin1.getSelectedItem().toString(),
                    nrm,
                    jacsonRes,
                    rm,
                    ventilator,
                    dopamin,
                    dobutamin,
                    nonAdrenaline,
                    adrenaline,
                    Poin2.getSelectedItem().toString(),
                    nadi,
                    td,
                    diuresis,
                    map,
                    rr,
                    spo2,
                    Poin3.getSelectedItem().toString(),
                    Lainnya.getText().trim(),
                    ranap,
                    rujuk,
                    aps
                }
        )) {
            tabMode.addRow(new String[]{
                TNoRw.getText().trim(),
                TNoRM.getText().trim(),
                TPasien.getText().trim(),
                TempatLahir.getText().trim(),
                TglLahir.getText().trim(),
                Umur.getText().trim(),
                JK.getText().trim(),
                tanggal,
                KodeDPJP.getText().trim(),
                NamaDPJP.getText().trim(),
                KodeDrICU.getText().trim(),
                NamaDrICU.getText().trim(),
                Diagnosa.getText().trim(),
                Poin1.getSelectedItem().toString(),
                nrm,
                jacsonRes,
                rm,
                ventilator,
                dopamin,
                dobutamin,
                nonAdrenaline,
                adrenaline,
                Poin2.getSelectedItem().toString(),
                nadi,
                td,
                diuresis,
                map,
                rr,
                spo2,
                Poin3.getSelectedItem().toString(),
                Lainnya.getText().trim(),
                ranap,
                rujuk,
                aps
            });

            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
        }
    }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
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
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString())){
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
    if (TNoRw.getText().trim().equals("")
            || TPasien.getText().trim().equals("")) {
        Valid.textKosong(TNoRw, "pasien");
    } else if (KodeDPJP.getText().trim().equals("")
            || NamaDPJP.getText().trim().equals("")) {
        Valid.textKosong(btnDPJP, "DPJP/Dokter Jaga/IGD");
    } else if (KodeDrICU.getText().trim().equals("")
            || NamaDrICU.getText().trim().equals("")) {
        Valid.textKosong(btnDrICU, "Dokter Konsultan ICU");
    } else if (tbObat.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(
                rootPane,
                "Silakan pilih data terlebih dahulu..!!"
        );
    } else {
        int row = tbObat.getSelectedRow();
        if (akses.getkode().equals("Admin Utama")) {
            ganti();
        } else if (akses.getkode().equals(
                tbObat.getValueAt(row, 8).toString()
        )) {
            ganti();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Harus salah satu petugas sesuai user login..!!"
            );
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
            String tempatLahir = nilaiTabel(row, 3);
            String tanggalLahir = nilaiTabel(row, 4);
            String umur = nilaiTabel(row, 5);
            String jenisKelamin = nilaiTabel(row, 6);

            String namaDPJP = nilaiTabel(row, 9);
            String namaDokterICU = nilaiTabel(row, 11);
            String diagnosa = nilaiTabel(row, 12).replace("\n", "<br>");

            String poin1 = nilaiTabel(row, 13);
            String poin2 = nilaiTabel(row, 22);
            String poin3 = nilaiTabel(row, 29);

            String lainnya = nilaiTabel(row, 30).replace("\n", "<br>");

            String poin1Ya = poin1.equalsIgnoreCase("Ya")
                    ? kotakCentang("1") : kotakCentang("0");
            String poin1Tidak = poin1.equalsIgnoreCase("Tidak")
                    ? kotakCentang("1") : kotakCentang("0");

            String poin2Ya = poin2.equalsIgnoreCase("Ya")
                    ? kotakCentang("1") : kotakCentang("0");
            String poin2Tidak = poin2.equalsIgnoreCase("Tidak")
                    ? kotakCentang("1") : kotakCentang("0");

            String poin3Ya = poin3.equalsIgnoreCase("Ya")
                    ? kotakCentang("1") : kotakCentang("0");
            String poin3Tidak = poin3.equalsIgnoreCase("Tidak")
                    ? kotakCentang("1") : kotakCentang("0");

            StringBuilder html = new StringBuilder();
            
            String isiQRCode =
                    "Ditandatangani oleh "
                    + namaDPJP
                    + " selaku dokter DPJP pasien "
                    + namaPasien
                    + " yang dirawat di RSUD Matraman";

            String lokasiQRCode = buatQRCodeTandaTangan(isiQRCode);

            html.append("<html>");
            html.append("<head>");
            html.append("<meta charset='UTF-8'>");
            html.append("<style>");
            html.append("@page{size:A4 portrait;margin:12mm;}");
            html.append("body{font-family:'Times New Roman',serif;font-size:12px;color:#000;}");
            html.append("table{border-collapse:collapse;width:100%;}");
            html.append(".form td{border:1px solid #000;padding:6px;vertical-align:top;}");
            html.append(".identitas td{height:24px;}");
            html.append(".judul{font-size:18px;font-weight:bold;text-align:center;}");
            html.append(".rs{font-family:Arial,sans-serif;font-size:10px;font-weight:bold;}");
            html.append(".nomor{width:24px;text-align:center;}");
            html.append(".pilihan{width:50px;text-align:center;vertical-align:middle!important;}");
            html.append(".box{display:inline-block;width:11px;height:11px;");
            html.append("border:1px solid #000;text-align:center;line-height:11px;");
            html.append("font-family:Arial;font-size:10px;font-weight:bold;}");
            html.append(".item{display:inline-block;margin:4px 18px 4px 0;}");
            html.append(".poin{line-height:1.45;min-height:92px;}");
            html.append(".lainnya{margin-top:36px;line-height:22px;}");
            html.append(".garis{border-bottom:1px dotted #000;height:20px;}");
            html.append(".tujuan{margin-top:18px;font-weight:bold;}");
            html.append("</style>");
            html.append("</head>");

            html.append("<body>");

            html.append("<table class='form identitas'>");

            // Kepala formulir
            html.append("<tr>");
            html.append("<td colspan='2' style='width:72%;'>");
            html.append("<table style='border:0;'>");
            html.append("<tr>");
            html.append("<td style='border:0;width:120px;'>");
            html.append("<div class='rs'>");
            html.append(akses.getnamars());
            html.append("<br>");
            html.append(akses.getalamatrs());
            html.append("</div>");
            html.append("</td>");
            html.append("<td style='border:0;' class='judul'>");
            html.append("KRITERIA PASIEN KELUAR ICU");
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
            html.append("<td colspan='2'>TEMPAT/TGL. LAHIR, UMUR: <b>");
            html.append(tempatLahir);
            html.append(", ");
            html.append(tanggalLahir);
            html.append(" / ");
            html.append(umur);
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

            // Kepala kriteria
            html.append("<tr>");
            html.append("<td class='nomor'>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("<td class='pilihan'><b>YA</b></td>");
            html.append("<td class='pilihan'><b>TIDAK</b></td>");
            html.append("</tr>");

            // Poin 1
            html.append("<tr>");
            html.append("<td class='nomor'>1</td>");
            html.append("<td class='poin'>");
            html.append("Pasien tidak lagi memerlukan alat atau obat untuk ");
            html.append("<i>life support</i><br>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 14)));
            html.append(" Masker NRM</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 15)));
            html.append(" Jackson Rees</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 17)));
            html.append(" Ventilator</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 16)));
            html.append(" Masker RM</span><br>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 18)));
            html.append(" Dopamin</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 19)));
            html.append(" Dobutamin</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 20)));
            html.append(" Nor-Adrenalin</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 21)));
            html.append(" Adrenalin</span>");

            html.append("</td>");
            html.append("<td class='pilihan'>").append(poin1Ya).append("</td>");
            html.append("<td class='pilihan'>").append(poin1Tidak).append("</td>");
            html.append("</tr>");

            // Poin 2
            html.append("<tr>");
            html.append("<td class='nomor'>2</td>");
            html.append("<td class='poin'>");
            html.append("Pasien dalam kondisi stabil normal ");
            html.append("(sesuai parameter <i>baseline</i>) dan memungkinkan ");
            html.append("kebutuhan terapi intensif secara mendadak kecil/kurang.<br>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 23)));
            html.append(" Nadi &gt;60/&lt;100 x/menit</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 26)));
            html.append(" MAP &gt;65 mmHg</span><br>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 24)));
            html.append(" TD Diastolik &lt;110 mmHg</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 27)));
            html.append(" RR &lt;30 x/menit</span><br>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 25)));
            html.append(" Diuresis &gt;0,5 cc/kgBB/jam</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 28)));
            html.append(" SpO2 &gt;93% dengan nasal kanul</span>");

            html.append("</td>");
            html.append("<td class='pilihan'>").append(poin2Ya).append("</td>");
            html.append("<td class='pilihan'>").append(poin2Tidak).append("</td>");
            html.append("</tr>");

            // Poin 3
            html.append("<tr>");
            html.append("<td class='nomor'>3</td>");
            html.append("<td class='poin' style='height:190px;'>");
            html.append("Manfaat terapi intensif kecil karena penyakit primernya ");
            html.append("sudah terminal, tidak berespons terhadap terapi ICU ");
            html.append("untuk penyakit akutnya, prognosis jangka pendek kecil ");
            html.append("dan tidak ada terapi potensial untuk memperbaiki prognosisnya.");

            html.append("<div class='lainnya'><b>LAIN-LAIN:</b>");
            html.append("<div class='garis'>").append(lainnya).append("</div>");
            html.append("<div class='garis'>&nbsp;</div>");
            html.append("<div class='garis'>&nbsp;</div>");
            html.append("</div>");

            html.append("</td>");
            html.append("<td class='pilihan'>").append(poin3Ya).append("</td>");
            html.append("<td class='pilihan'>").append(poin3Tidak).append("</td>");
            html.append("</tr>");

            html.append("</table>");

            html.append("<div style='margin-top:5px;'>");
            html.append("Berdasarkan kondisi di atas maka pasien tersebut ");
            html.append("memenuhi syarat untuk keluar ICU.");
            html.append("</div>");

            // Tujuan keluar ICU
            html.append("<div class='tujuan'>Tujuan Keluar ICU</div>");

            html.append("<div style='margin-top:8px;'>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 31)));
            html.append(" Ruang Rawat Inap</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 32)));
            html.append(" Rumah Sakit Rujukan</span>");

            html.append("<span class='item'>");
            html.append(kotakCentang(nilaiTabel(row, 33)));
            html.append(" Atas Permintaan Sendiri</span>");

            html.append("</div>");

            html.append("<div style='margin-top:8px;'>");
            html.append("Jenis kelamin: ").append(jenisKelamin);
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

            File f = new File("FormKriteriaPasienKeluarICU.html");

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

    private void MnKriteriaKeluarICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKriteriaKeluarICUActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),17).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirChecklistKriteriaKeluarICU.jasper","report","::[ Formulir Check List Kriteria Keluar ICU ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_keluar_icu.tanggal,"+
                    "checklist_kriteria_keluar_icu.kriteria1,checklist_kriteria_keluar_icu.kriteria2,checklist_kriteria_keluar_icu.kriteria3,"+
                    "checklist_kriteria_keluar_icu.kriteria4,checklist_kriteria_keluar_icu.kriteria5,checklist_kriteria_keluar_icu.kriteria6,"+
                    "checklist_kriteria_keluar_icu.kriteria7,checklist_kriteria_keluar_icu.kriteria8,checklist_kriteria_keluar_icu.kriteria9,"+
                    "checklist_kriteria_keluar_icu.kriteria10,checklist_kriteria_keluar_icu.kriteria11,checklist_kriteria_keluar_icu.nik,pegawai.nama "+
                    "from checklist_kriteria_keluar_icu inner join reg_periksa on checklist_kriteria_keluar_icu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_keluar_icu.nik "+
                    "where checklist_kriteria_keluar_icu.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_kriteria_keluar_icu.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnKriteriaKeluarICUActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed

    }//GEN-LAST:event_TanggalKeyPressed

    private void btnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJPActionPerformed
        i = 1;
        dokter.emptTeks();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDPJPActionPerformed

    private void btnDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDPJPKeyPressed
        Valid.pindah(evt,Tanggal,Poin1);
    }//GEN-LAST:event_btnDPJPKeyPressed

    private void btnDrICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrICUActionPerformed
        i = 2;
        dokter.emptTeks();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDrICUActionPerformed

    private void btnDrICUKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDrICUKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDrICUKeyPressed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed

    }//GEN-LAST:event_DiagnosaKeyPressed

    private void Poin1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Poin1KeyPressed

    }//GEN-LAST:event_Poin1KeyPressed

    private void RMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RMActionPerformed

    private void VentilatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentilatorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VentilatorActionPerformed

    private void DopaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DopaminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DopaminActionPerformed

    private void DobutaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DobutaminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DobutaminActionPerformed

    private void NonAdrenalineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NonAdrenalineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NonAdrenalineActionPerformed

    private void JacsonResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JacsonResActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JacsonResActionPerformed

    private void AdrenalineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdrenalineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdrenalineActionPerformed

    private void Poin2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Poin2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Poin2KeyPressed

    private void DiuresisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiuresisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiuresisActionPerformed

    private void TdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TdActionPerformed

    private void MapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MapActionPerformed

    private void RrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RrActionPerformed

    private void SpO2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SpO2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SpO2ActionPerformed

    private void Poin3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Poin3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Poin3KeyPressed

    private void LainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LainnyaKeyPressed

    private void RujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RujukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RujukActionPerformed

    private void APSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_APSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_APSActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistKriteriaKeluarICU dialog = new RMChecklistKriteriaKeluarICU(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox APS;
    private javax.swing.JCheckBox Adrenaline;
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
    private javax.swing.JCheckBox Diuresis;
    private javax.swing.JCheckBox Dobutamin;
    private javax.swing.JCheckBox Dopamin;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private javax.swing.JCheckBox JacsonRes;
    private widget.TextBox KodeDPJP;
    private widget.TextBox KodeDrICU;
    private widget.Label LCount;
    private widget.TextArea Lainnya;
    private widget.editorpane LoadHTML;
    private javax.swing.JCheckBox Map;
    private javax.swing.JMenuItem MnKriteriaKeluarICU;
    private javax.swing.JCheckBox NRM;
    private javax.swing.JCheckBox Nadi;
    private widget.TextBox NamaDPJP;
    private widget.TextBox NamaDrICU;
    private javax.swing.JCheckBox NonAdrenaline;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Poin1;
    private widget.ComboBox Poin2;
    private widget.ComboBox Poin3;
    private javax.swing.JCheckBox RM;
    private javax.swing.JCheckBox Ranap;
    private javax.swing.JCheckBox Rr;
    private javax.swing.JCheckBox Rujuk;
    private widget.ScrollPane Scroll;
    private javax.swing.JCheckBox SpO2;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private javax.swing.JCheckBox Td;
    private widget.TextBox TempatLahir;
    private widget.TextBox TglLahir;
    private widget.TextBox Umur;
    private javax.swing.JCheckBox Ventilator;
    private widget.Button btnDPJP;
    private widget.Button btnDrICU;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);

        String cari = TCari.getText().trim();

        try {
            String sql =
                    "SELECT "
                    + "c.no_rawat, "
                    + "p.no_rkm_medis, "
                    + "p.nm_pasien, "
                    + "p.tmp_lahir, "
                    + "p.tgl_lahir, "
                    + "p.umur, "
                    + "p.jk, "
                    + "c.tanggal, "

                    + "c.kd_pj, "
                    + "dpjp.nama AS nama_dpjp, "
                    + "IFNULL(c.kd_dokter_icu,'') AS kd_dokter_icu, "
                    + "IFNULL(dricu.nm_dokter,'') AS nama_dokter_icu, "

                    + "IFNULL(c.diagnosa,'') AS diagnosa, "
                    + "c.poin_1, "

                    + "c.nrm, "
                    + "c.jacson_res, "
                    + "c.rm, "
                    + "c.ventilator, "

                    + "c.dopamin, "
                    + "c.dobutamin, "
                    + "c.non_adrenaline, "
                    + "c.adrenaline, "

                    + "c.poin_2, "

                    + "c.nadi, "
                    + "c.td, "
                    + "c.diuresis, "
                    + "c.`map` AS map, "
                    + "c.rr, "
                    + "c.spo2, "

                    + "c.poin_3, "
                    + "IFNULL(c.lainnya,'') AS lainnya, "

                    + "c.ranap, "
                    + "c.rujuk, "
                    + "c.aps "

                    + "FROM checklist_kriteria_keluar_icu AS c "

                    + "INNER JOIN reg_periksa AS rp "
                    + "ON c.no_rawat=rp.no_rawat "

                    + "INNER JOIN pasien AS p "
                    + "ON rp.no_rkm_medis=p.no_rkm_medis "

                    + "INNER JOIN pegawai AS dpjp "
                    + "ON c.kd_pj=dpjp.nik "

                    + "LEFT JOIN dokter AS dricu "
                    + "ON c.kd_dokter_icu=dricu.kd_dokter "

                    + "WHERE c.tanggal BETWEEN ? AND ? ";

            if (!cari.equals("")) {
                sql +=
                        "AND ("
                        + "c.no_rawat LIKE ? OR "
                        + "p.no_rkm_medis LIKE ? OR "
                        + "p.nm_pasien LIKE ? OR "
                        + "c.kd_pj LIKE ? OR "
                        + "dpjp.nama LIKE ? OR "
                        + "c.kd_dokter_icu LIKE ? OR "
                        + "dricu.nm_dokter LIKE ? OR "
                        + "c.diagnosa LIKE ? OR "
                        + "c.lainnya LIKE ?"
                        + ") ";
            }

            sql += "ORDER BY c.tanggal";

            ps = koneksi.prepareStatement(sql);

            try {
                ps.setString(
                        1,
                        Valid.SetTgl(DTPCari1.getSelectedItem() + "")
                        + " 00:00:00"
                );

                ps.setString(
                        2,
                        Valid.SetTgl(DTPCari2.getSelectedItem() + "")
                        + " 23:59:59"
                );

                if (!cari.equals("")) {
                    String kataKunci = "%" + cari + "%";

                    ps.setString(3, kataKunci);
                    ps.setString(4, kataKunci);
                    ps.setString(5, kataKunci);
                    ps.setString(6, kataKunci);
                    ps.setString(7, kataKunci);
                    ps.setString(8, kataKunci);
                    ps.setString(9, kataKunci);
                    ps.setString(10, kataKunci);
                    ps.setString(11, kataKunci);
                }

                rs = ps.executeQuery();

                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        // 0–7: Data pasien
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("tmp_lahir"),
                        rs.getString("tgl_lahir"),
                        rs.getString("umur"),
                        rs.getString("jk"),
                        rs.getString("tanggal"),

                        // 8–11: Dokter
                        rs.getString("kd_pj"),
                        rs.getString("nama_dpjp"),
                        rs.getString("kd_dokter_icu"),
                        rs.getString("nama_dokter_icu"),

                        // 12–13: Diagnosis dan poin 1
                        rs.getString("diagnosa"),
                        rs.getString("poin_1"),

                        // 14–17: Alat bantu napas
                        rs.getString("nrm"),
                        rs.getString("jacson_res"),
                        rs.getString("rm"),
                        rs.getString("ventilator"),

                        // 18–21: Obat
                        rs.getString("dopamin"),
                        rs.getString("dobutamin"),
                        rs.getString("non_adrenaline"),
                        rs.getString("adrenaline"),

                        // 22: Poin 2
                        rs.getString("poin_2"),

                        // 23–28: Parameter stabil
                        rs.getString("nadi"),
                        rs.getString("td"),
                        rs.getString("diuresis"),
                        rs.getString("map"),
                        rs.getString("rr"),
                        rs.getString("spo2"),

                        // 29–30: Poin 3 dan lainnya
                        rs.getString("poin_3"),
                        rs.getString("lainnya"),

                        // 31–33: Tujuan keluar
                        rs.getString("ranap"),
                        rs.getString("rujuk"),
                        rs.getString("aps")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
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
    
    public void emptTeks() {
        Diagnosa.setText("");
        Poin1.setSelectedItem("Ya");
        Poin2.setSelectedItem("Ya");
        Poin3.setSelectedItem("Ya");
        NRM.setSelected(false);
        JacsonRes.setSelected(false);
        RM.setSelected(false);
        Ventilator.setSelected(false);
        Dopamin.setSelected(false);
        Dobutamin.setSelected(false);
        NonAdrenaline.setSelected(false);
        Adrenaline.setSelected(false);
        Nadi.setSelected(false);
        Td.setSelected(false);
        Diuresis.setSelected(false);
        Map.setSelected(false);
        Rr.setSelected(false);
        SpO2.setSelected(false);
        Lainnya.setText("");
        Ranap.setSelected(false);
        Rujuk.setSelected(false);
        APS.setSelected(false);
        Tanggal.setDate(new Date());
        Poin1.requestFocus();
    }

    private void getData() {
        int row = tbObat.getSelectedRow();
        if (row != -1) {
            TNoRw.setText(tbObat.getValueAt(row, 0).toString());
            TNoRM.setText(tbObat.getValueAt(row, 1).toString());
            TPasien.setText(tbObat.getValueAt(row, 2).toString());
            TempatLahir.setText(tbObat.getValueAt(row, 3).toString());
            TglLahir.setText(tbObat.getValueAt(row, 4).toString());
            Umur.setText(tbObat.getValueAt(row, 5).toString());
            JK.setText(tbObat.getValueAt(row, 6).toString());
            Valid.SetTgl2(
                    Tanggal,
                    tbObat.getValueAt(row, 7).toString()
            );
            KodeDPJP.setText(tbObat.getValueAt(row, 8).toString());
            NamaDPJP.setText(tbObat.getValueAt(row, 9).toString());
            KodeDrICU.setText(tbObat.getValueAt(row, 10).toString());
            NamaDrICU.setText(tbObat.getValueAt(row, 11).toString());
            Diagnosa.setText(tbObat.getValueAt(row, 12).toString());
            Poin1.setSelectedItem(
                    tbObat.getValueAt(row, 13).toString()
            );
            NRM.setSelected(
                    tbObat.getValueAt(row, 14).toString().equals("1")
            );
            JacsonRes.setSelected(
                    tbObat.getValueAt(row, 15).toString().equals("1")
            );
            RM.setSelected(
                    tbObat.getValueAt(row, 16).toString().equals("1")
            );
            Ventilator.setSelected(
                    tbObat.getValueAt(row, 17).toString().equals("1")
            );
            Dopamin.setSelected(
                    tbObat.getValueAt(row, 18).toString().equals("1")
            );
            Dobutamin.setSelected(
                    tbObat.getValueAt(row, 19).toString().equals("1")
            );
            NonAdrenaline.setSelected(
                    tbObat.getValueAt(row, 20).toString().equals("1")
            );
            Adrenaline.setSelected(
                    tbObat.getValueAt(row, 21).toString().equals("1")
            );
            Poin2.setSelectedItem(
                    tbObat.getValueAt(row, 22).toString()
            );
            Nadi.setSelected(
                    tbObat.getValueAt(row, 23).toString().equals("1")
            );
            Td.setSelected(
                    tbObat.getValueAt(row, 24).toString().equals("1")
            );
            Diuresis.setSelected(
                    tbObat.getValueAt(row, 25).toString().equals("1")
            );
            Map.setSelected(
                    tbObat.getValueAt(row, 26).toString().equals("1")
            );
            Rr.setSelected(
                    tbObat.getValueAt(row, 27).toString().equals("1")
            );
            SpO2.setSelected(
                    tbObat.getValueAt(row, 28).toString().equals("1")
            );
            Poin3.setSelectedItem(
                    tbObat.getValueAt(row, 29).toString()
            );
            Lainnya.setText(tbObat.getValueAt(row, 30).toString());
            Ranap.setSelected(
                    tbObat.getValueAt(row, 31).toString().equals("1")
            );
            Rujuk.setSelected(
                    tbObat.getValueAt(row, 32).toString().equals("1")
            );
            APS.setSelected(
                    tbObat.getValueAt(row, 33).toString().equals("1")
            );
        }
    }
    
    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "SELECT "
                    + "rp.no_rkm_medis, "
                    + "p.nm_pasien, "
                    + "p.jk, "
                    + "p.tmp_lahir, "
                    + "p.tgl_lahir, "
                    + "p.umur, "
                    + "rp.tgl_registrasi, "
                    + "IFNULL(dg.diagnosa, '') AS semua_diagnosa, "
                    + "IFNULL(dpjp.kd_dokter, '') AS kd_dpjp_ranap, "
                    + "IFNULL(dk.nm_dokter, '') AS nm_dpjp_ranap "
                    + "FROM reg_periksa AS rp "
                    + "INNER JOIN pasien AS p "
                    + "ON rp.no_rkm_medis=p.no_rkm_medis "
                    + "LEFT JOIN ("
                        + "SELECT "
                        + "dp.no_rawat, "
                        + "GROUP_CONCAT("
                            + "CONCAT("
                                + "dp.kd_penyakit, ' - ', py.nm_penyakit"
                            + ") "
                            + "ORDER BY dp.prioritas "
                            + "SEPARATOR ', '"
                        + ") AS diagnosa "
                        + "FROM diagnosa_pasien AS dp "
                        + "INNER JOIN penyakit AS py "
                        + "ON dp.kd_penyakit=py.kd_penyakit "
                        + "GROUP BY dp.no_rawat"
                    + ") AS dg "
                    + "ON rp.no_rawat=dg.no_rawat "

                    + "LEFT JOIN dpjp_ranap AS dpjp "
                    + "ON dpjp.no_rawat=rp.no_rawat "
                    + "AND dpjp.kd_dokter=("
                        + "SELECT dr2.kd_dokter "
                        + "FROM dpjp_ranap AS dr2 "
                        + "WHERE dr2.no_rawat=rp.no_rawat "
                        + "ORDER BY dr2.kd_dokter DESC "
                        + "LIMIT 1"
                    + ") "
                    + "LEFT JOIN dokter AS dk "
                    + "ON dpjp.kd_dokter=dk.kd_dokter "

                    + "WHERE rp.no_rawat=?"
            );
            try {
                ps.setString(1, TNoRw.getText().trim());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(
                            rs.getString("no_rkm_medis")
                    );
                    TPasien.setText(
                            rs.getString("nm_pasien")
                    );
                    JK.setText(
                            rs.getString("jk")
                    );
                    TempatLahir.setText(
                            rs.getString("tmp_lahir")
                    );
                    TglLahir.setText(
                            rs.getString("tgl_lahir")
                    );
                    Umur.setText(
                            rs.getString("umur")
                    );
                    DTPCari1.setDate(
                            rs.getDate("tgl_registrasi")
                    );
                    Diagnosa.setText(
                            rs.getString("semua_diagnosa")
                    );
                    KodeDPJP.setText(
                            rs.getString("kd_dpjp_ranap")
                    );
                    NamaDPJP.setText(
                            rs.getString("nm_dpjp_ranap")
                    );
                }
            } catch (Exception e) {
                System.out.println("Notif isRawat : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif isRawat : " + e);
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,600));
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
        BtnSimpan.setEnabled(akses.getchecklist_kriteria_keluar_icu());
        BtnHapus.setEnabled(akses.getchecklist_kriteria_keluar_icu());
        BtnEdit.setEnabled(akses.getchecklist_kriteria_keluar_icu());
        BtnPrint.setEnabled(akses.getchecklist_kriteria_keluar_icu()); 
//        if(akses.getjml2()>=1){
//            btnPetugas.setEnabled(false);
//            KodePetugas.setText(akses.getkode());
//            NamaPetugas.setText(pegawai.tampil3(akses.getkode()));
//        }
    }

    private void ganti() {
        int row = tbObat.getSelectedRow();
        String tanggal = Valid.SetTgl(
                Tanggal.getSelectedItem().toString()
        ) + " " + Tanggal.getSelectedItem().toString().substring(11, 19);
        String nrm = NRM.isSelected() ? "1" : "0";
        String jacsonRes = JacsonRes.isSelected() ? "1" : "0";
        String rm = RM.isSelected() ? "1" : "0";
        String ventilator = Ventilator.isSelected() ? "1" : "0";
        String dopamin = Dopamin.isSelected() ? "1" : "0";
        String dobutamin = Dobutamin.isSelected() ? "1" : "0";
        String nonAdrenaline = NonAdrenaline.isSelected() ? "1" : "0";
        String adrenaline = Adrenaline.isSelected() ? "1" : "0";
        String ranap = Ranap.isSelected() ? "1" : "0";
        String rujuk = Rujuk.isSelected() ? "1" : "0";
        String aps = APS.isSelected() ? "1" : "0";
        String nadi = Nadi.isSelected() ? "1" : "0";
        String td = Td.isSelected() ? "1" : "0";
        String diuresis = Diuresis.isSelected() ? "1" : "0";
        String map = Map.isSelected() ? "1" : "0";
        String rr = Rr.isSelected() ? "1" : "0";
        String spo2 = SpO2.isSelected() ? "1" : "0";
        if (Sequel.mengedittf(
                "checklist_kriteria_keluar_icu",
                "no_rawat=? AND tanggal=?",
                "no_rawat=?,"
                + "tanggal=?,"
                + "kd_pj=?,"
                + "kd_dokter_icu=?,"
                + "diagnosa=?,"
                + "poin_1=?,"
                + "nrm=?,"
                + "jacson_res=?,"
                + "rm=?,"
                + "ventilator=?,"
                + "dopamin=?,"
                + "dobutamin=?,"
                + "non_adrenaline=?,"
                + "adrenaline=?,"
                + "poin_2=?,"
                + "nadi=?,"
                + "td=?,"
                + "diuresis=?,"
                + "`map`=?,"
                + "rr=?,"
                + "spo2=?,"
                + "poin_3=?,"
                + "lainnya=?,"
                + "ranap=?,"
                + "rujuk=?,"
                + "aps=?",
                28,
                new String[]{
                    TNoRw.getText().trim(),
                    tanggal,
                    KodeDPJP.getText().trim(),
                    KodeDrICU.getText().trim(),
                    Diagnosa.getText().trim(),
                    Poin1.getSelectedItem().toString(),
                    nrm,
                    jacsonRes,
                    rm,
                    ventilator,
                    dopamin,
                    dobutamin,
                    nonAdrenaline,
                    adrenaline,
                    Poin2.getSelectedItem().toString(),
                    nadi,
                    td,
                    diuresis,
                    map,
                    rr,
                    spo2,
                    Poin3.getSelectedItem().toString(),
                    Lainnya.getText().trim(),
                    ranap,
                    rujuk,
                    aps,
                    tbObat.getValueAt(row, 0).toString(),
                    tbObat.getValueAt(row, 7).toString()
                }
        )) {
            // Data pasien
            tbObat.setValueAt(TNoRw.getText().trim(), row, 0);
            tbObat.setValueAt(TNoRM.getText().trim(), row, 1);
            tbObat.setValueAt(TPasien.getText().trim(), row, 2);
            tbObat.setValueAt(TempatLahir.getText().trim(), row, 3);
            tbObat.setValueAt(TglLahir.getText().trim(), row, 4);
            tbObat.setValueAt(Umur.getText().trim(), row, 5);
            tbObat.setValueAt(JK.getText().trim(), row, 6);
            tbObat.setValueAt(tanggal, row, 7);
            tbObat.setValueAt(KodeDPJP.getText().trim(), row, 8);
            tbObat.setValueAt(NamaDPJP.getText().trim(), row, 9);
            tbObat.setValueAt(KodeDrICU.getText().trim(), row, 10);
            tbObat.setValueAt(NamaDrICU.getText().trim(), row, 11);
            tbObat.setValueAt(Diagnosa.getText().trim(), row, 12);
            tbObat.setValueAt(
                    Poin1.getSelectedItem().toString(), row, 13
            );
            tbObat.setValueAt(nrm, row, 14);
            tbObat.setValueAt(jacsonRes, row, 15);
            tbObat.setValueAt(rm, row, 16);
            tbObat.setValueAt(ventilator, row, 17);
            tbObat.setValueAt(dopamin, row, 18);
            tbObat.setValueAt(dobutamin, row, 19);
            tbObat.setValueAt(nonAdrenaline, row, 20);
            tbObat.setValueAt(adrenaline, row, 21);
            tbObat.setValueAt(
                    Poin2.getSelectedItem().toString(), row, 22
            );
            tbObat.setValueAt(Nadi, row, 23);
            tbObat.setValueAt(Td, row, 24);
            tbObat.setValueAt(Diuresis, row, 25);
            tbObat.setValueAt(Map, row, 26);
            tbObat.setValueAt(Rr, row, 27);
            tbObat.setValueAt(SpO2, row, 28);
            tbObat.setValueAt(
                    Poin3.getSelectedItem().toString(), row, 29
            );
            tbObat.setValueAt(Lainnya.getText().trim(), row, 30);
            tbObat.setValueAt(ranap, row, 31);
            tbObat.setValueAt(rujuk, row, 32);
            tbObat.setValueAt(aps, row, 33);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_kriteria_keluar_icu where no_rawat=? and tanggal=?",2,new String[]{
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
