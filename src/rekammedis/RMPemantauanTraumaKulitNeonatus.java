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
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import keuangan.DlgKamar;


/**
 *
 * @author perpustakaan
 */
public final class RMPemantauanTraumaKulitNeonatus extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);  
    private DlgKamar ranap=new DlgKamar(null,false);
    private String dpjp="";
    private int skoringTerakhir = 0;
    private boolean sedangMengaturSkoring = false;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPemantauanTraumaKulitNeonatus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        javax.swing.text.html.HTMLEditorKit parafHtmlKit =
            new javax.swing.text.html.HTMLEditorKit();
        LoadHTML.setEditorKit(parafHtmlKit);
        LoadHTML.setDocument(parafHtmlKit.createDefaultDocument());
        LoadHTML.setEditable(false);
        LoadHTML.setOpaque(true);
        LoadHTML.setBackground(java.awt.Color.WHITE);

        LoadHTML.putClientProperty(
            javax.swing.JEditorPane.HONOR_DISPLAY_PROPERTIES,
            Boolean.TRUE
        );
        
        TotalSkor.setOpaque(true);
        TotalSkor.setEditable(false);
        TotalSkor.setEnabled(true);

        TotalSkor.getDocument().addDocumentListener(
            new javax.swing.event.DocumentListener() {

                @Override
                public void insertUpdate(
                        javax.swing.event.DocumentEvent e) {
                    updateWarnaTotalSkor();
                }

                @Override
                public void removeUpdate(
                        javax.swing.event.DocumentEvent e) {
                    updateWarnaTotalSkor();
                }

                @Override
                public void changedUpdate(
                        javax.swing.event.DocumentEvent e) {
                    updateWarnaTotalSkor();
                }
            }
        );

        updateWarnaTotalSkor();
        setEmptyParaf();
    
        this.setLocation(8,1);
        setSize(628,674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No. Rawat",              // 0
            "No. R.M.",               // 1
            "Nama Pasien",            // 2
            "Umur",                   // 3
            "JK",                     // 4
            "Tanggal Lahir",          // 5
            "Tanggal",                // 6
            "Kode Kamar",             // 7
            "Nama Kamar",             // 8
            "Skoring",                // 9
            "Tanggal Skoring",        // 10
            "Usia Gestasi",           // 11
            "Skor Usia Gestasi",      // 12
            "Status Mental",          // 13
            "Skor Status Mental",     // 14
            "Mobilisasi",             // 15
            "Skor Mobilisasi",        // 16
            "Aktivitas",              // 17
            "Skor Aktivitas",         // 18
            "Nutrisi",                // 19
            "Skor Nutrisi",           // 20
            "Kelembaban",             // 21
            "Skor Kelembaban",        // 22
            "Total Skor",             // 23
            "NIP",                    // 24
            "Nama Petugas",           // 25
            "Paraf"                   // 26
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(
            new Dimension(500, 500)
        );

        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 27; i++) {
            TableColumn column =
                tbObat.getColumnModel().getColumn(i);

            if (i == 0) {             // No. Rawat
                column.setPreferredWidth(105);
            } else if (i == 1) {      // No. R.M.
                column.setPreferredWidth(70);
            } else if (i == 2) {      // Nama Pasien
                column.setPreferredWidth(160);
            } else if (i == 3) {      // Umur
                column.setPreferredWidth(70);
            } else if (i == 4) {      // JK
                column.setPreferredWidth(35);
            } else if (i == 5) {      // Tanggal Lahir
                column.setPreferredWidth(90);
            } else if (i == 6) {      // Tanggal
                column.setPreferredWidth(125);
            } else if (i == 7) {      // Kode Kamar
                column.setPreferredWidth(80);
            } else if (i == 8) {      // Nama Kamar
                column.setPreferredWidth(140);
            } else if (i == 9) {      // Skoring
                column.setPreferredWidth(55);
            } else if (i == 10) {     // Tanggal Skoring
                column.setPreferredWidth(95);
            } else if (i == 11) {     // Usia Gestasi
                column.setPreferredWidth(160);
            } else if (i == 12) {     // Skor Usia Gestasi
                column.setPreferredWidth(110);
            } else if (i == 13) {     // Status Mental
                column.setPreferredWidth(230);
            } else if (i == 14) {     // Skor Status Mental
                column.setPreferredWidth(115);
            } else if (i == 15) {     // Mobilisasi
                column.setPreferredWidth(190);
            } else if (i == 16) {     // Skor Mobilisasi
                column.setPreferredWidth(100);
            } else if (i == 17) {     // Aktivitas
                column.setPreferredWidth(260);
            } else if (i == 18) {     // Skor Aktivitas
                column.setPreferredWidth(95);
            } else if (i == 19) {     // Nutrisi
                column.setPreferredWidth(300);
            } else if (i == 20) {     // Skor Nutrisi
                column.setPreferredWidth(90);
            } else if (i == 21) {     // Kelembaban
                column.setPreferredWidth(300);
            } else if (i == 22) {     // Skor Kelembaban
                column.setPreferredWidth(110);
            } else if (i == 23) {     // Total Skor
                column.setPreferredWidth(75);
            } else if (i == 24) {     // NIP
                column.setPreferredWidth(120);
            } else if (i == 25) {     // Nama Petugas
                column.setPreferredWidth(160);
            } else if (i == 26) {     // Paraf
                column.setPreferredWidth(220);
            }
        }

        tbObat.setDefaultRenderer(
            Object.class,
            new javax.swing.table.DefaultTableCellRenderer() {

                @Override
                public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table,
                        Object value,
                        boolean isSelected,
                        boolean hasFocus,
                        int row,
                        int column) {

                    java.awt.Component component =
                        super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                        );

                    int modelRow =
                        table.convertRowIndexToModel(row);

                    Object totalObject =
                        table.getModel().getValueAt(modelRow, 23);

                    int total = 0;

                    try {
                        if (totalObject != null) {
                            total = Integer.parseInt(
                                totalObject.toString().trim()
                            );
                        }
                    } catch (NumberFormatException e) {
                        total = 0;
                    }

                    /*
                     * Selalu atur foreground dan background
                     * agar warna baris sebelumnya tidak terbawa.
                     */
                    component.setForeground(java.awt.Color.BLACK);

                    if (total >= 0 && total <= 7) {
                        // Hijau
                        component.setBackground(
                            isSelected
                                ? new java.awt.Color(120, 200, 140)
                                : new java.awt.Color(198, 239, 206)
                        );

                    } else if (total >= 7 && total <= 13) {
                        // Kuning
                        component.setBackground(
                            isSelected
                                ? new java.awt.Color(230, 195, 70)
                                : new java.awt.Color(255, 235, 156)
                        );

                    } else if (total > 13) {
                        // Merah
                        component.setBackground(
                            isSelected
                                ? new java.awt.Color(210, 80, 90)
                                : new java.awt.Color(255, 199, 206)
                        );

                        if (isSelected) {
                            component.setForeground(
                                java.awt.Color.WHITE
                            );
                        }

                    }

                    /*
                     * Kolom skor dan total dibuat rata tengah.
                     */
                    if (column == 9 ||
                        column == 12 ||
                        column == 14 ||
                        column == 16 ||
                        column == 18 ||
                        column == 20 ||
                        column == 22 ||
                        column == 23) {

                        setHorizontalAlignment(
                            javax.swing.SwingConstants.CENTER
                        );
                    } else {
                        setHorizontalAlignment(
                            javax.swing.SwingConstants.LEFT
                        );
                    }

                    return component;
                }
            }
        );
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
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
        
        ranap.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ranap.getTable().getSelectedRow()!= -1){                   
                    KdKamar.setText(ranap.getTable().getValueAt(ranap.getTable().getSelectedRow(),1).toString());
                    NamaKamar.setText(ranap.getTable().getValueAt(ranap.getTable().getSelectedRow(),3).toString());
                }  
                KdKamar.requestFocus();
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
        
        Skoring.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                java.awt.Component component =
                        super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus
                        );

                if (value != null) {
                    try {
                        int nilai = Integer.parseInt(value.toString());

                        if (nilai <= skoringTerakhir) {
                            component.setForeground(java.awt.Color.GRAY);
                        }
                    } catch (NumberFormatException e) {
                        // Abaikan jika isinya bukan angka.
                    }
                }

                return component;
            }
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
        Cetak = new javax.swing.JMenuItem();
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
        JK = new widget.TextBox();
        TglLahir = new widget.TextBox();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        KdKamar = new widget.TextBox();
        NamaKamar = new widget.TextBox();
        btnKamar = new widget.Button();
        HariKe = new widget.TextBox();
        Skoring = new widget.ComboBox();
        TanggalSkoring = new widget.Tanggal();
        UsiaGestasi = new widget.ComboBox();
        SkorUsiaGestasi = new widget.TextBox();
        StatusMental = new widget.ComboBox();
        SkorStatusMental = new widget.TextBox();
        Mobilisasi = new widget.ComboBox();
        SkorMobilisasi = new widget.TextBox();
        Aktivitas = new widget.ComboBox();
        SkorAktivitas = new widget.TextBox();
        Nutrisi = new widget.ComboBox();
        SkorNutrisi = new widget.TextBox();
        Kelembaban = new widget.ComboBox();
        SkorKelembaban = new widget.TextBox();
        TotalSkor = new widget.TextBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        jLabel5 = new widget.Label();
        jLabel41 = new widget.Label();
        jLabel42 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        jLabel4 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel63 = new widget.Label();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel82 = new widget.Label();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        Cetak.setBackground(new java.awt.Color(255, 255, 254));
        Cetak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Cetak.setForeground(new java.awt.Color(50, 50, 50));
        Cetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Cetak.setText("Formulir Pemantauan Risiko Trauma Kulit Pada Pasien Neonatus");
        Cetak.setName("Cetak"); // NOI18N
        Cetak.setPreferredSize(new java.awt.Dimension(260, 26));
        Cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CetakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Cetak);

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pemantauan Risiko Trauma Kulit Pada Pasien Neonatus ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-08-2026" }));
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

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(634, 10, 50, 24);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(700, 10, 100, 23);

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

        KdKamar.setEditable(false);
        KdKamar.setHighlighter(null);
        KdKamar.setName("KdKamar"); // NOI18N
        KdKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKamarKeyPressed(evt);
            }
        });
        FormInput.add(KdKamar);
        KdKamar.setBounds(90, 70, 94, 23);

        NamaKamar.setEditable(false);
        NamaKamar.setName("NamaKamar"); // NOI18N
        FormInput.add(NamaKamar);
        NamaKamar.setBounds(190, 70, 187, 23);

        btnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKamar.setMnemonic('2');
        btnKamar.setToolTipText("ALt+2");
        btnKamar.setName("btnKamar"); // NOI18N
        btnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarActionPerformed(evt);
            }
        });
        btnKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKamarKeyPressed(evt);
            }
        });
        FormInput.add(btnKamar);
        btnKamar.setBounds(380, 70, 28, 23);

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

        Skoring.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6" }));
        Skoring.setName("Skoring"); // NOI18N
        Skoring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkoringActionPerformed(evt);
            }
        });
        Skoring.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkoringKeyPressed(evt);
            }
        });
        FormInput.add(Skoring);
        Skoring.setBounds(110, 100, 80, 23);

        TanggalSkoring.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSkoring.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-08-2026" }));
        TanggalSkoring.setDisplayFormat("dd-MM-yyyy");
        TanggalSkoring.setName("TanggalSkoring"); // NOI18N
        TanggalSkoring.setOpaque(false);
        TanggalSkoring.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSkoringKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSkoring);
        TanggalSkoring.setBounds(750, 140, 80, 20);

        UsiaGestasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "< 28 minggu", "28 minggu - < 33 minggu", "33 minggu - 38 minggu", "> 38 minggu" }));
        UsiaGestasi.setName("UsiaGestasi"); // NOI18N
        UsiaGestasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsiaGestasiActionPerformed(evt);
            }
        });
        UsiaGestasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaGestasiKeyPressed(evt);
            }
        });
        FormInput.add(UsiaGestasi);
        UsiaGestasi.setBounds(140, 180, 530, 23);

        SkorUsiaGestasi.setEditable(false);
        SkorUsiaGestasi.setFocusTraversalPolicyProvider(true);
        SkorUsiaGestasi.setName("SkorUsiaGestasi"); // NOI18N
        SkorUsiaGestasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorUsiaGestasiKeyPressed(evt);
            }
        });
        FormInput.add(SkorUsiaGestasi);
        SkorUsiaGestasi.setBounds(700, 180, 120, 23);

        StatusMental.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak berespon terhadap stimulus nyeri / koma", "Hanya berespon pada nyeri / sopor", "Letargi / apatis", "Sadar dan aktif / compos mentis" }));
        StatusMental.setName("StatusMental"); // NOI18N
        StatusMental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusMentalActionPerformed(evt);
            }
        });
        StatusMental.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusMentalKeyPressed(evt);
            }
        });
        FormInput.add(StatusMental);
        StatusMental.setBounds(140, 210, 530, 23);

        SkorStatusMental.setEditable(false);
        SkorStatusMental.setFocusTraversalPolicyProvider(true);
        SkorStatusMental.setName("SkorStatusMental"); // NOI18N
        SkorStatusMental.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorStatusMentalKeyPressed(evt);
            }
        });
        FormInput.add(SkorStatusMental);
        SkorStatusMental.setBounds(700, 210, 120, 23);

        Mobilisasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak mampu bergerak", "Bergerak sedikit dengan bantuan", "Bergerak sedikit tanpa bantuan", "Bergerak aktif" }));
        Mobilisasi.setName("Mobilisasi"); // NOI18N
        Mobilisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MobilisasiActionPerformed(evt);
            }
        });
        Mobilisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MobilisasiKeyPressed(evt);
            }
        });
        FormInput.add(Mobilisasi);
        Mobilisasi.setBounds(140, 240, 530, 23);

        SkorMobilisasi.setEditable(false);
        SkorMobilisasi.setFocusTraversalPolicyProvider(true);
        SkorMobilisasi.setName("SkorMobilisasi"); // NOI18N
        SkorMobilisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorMobilisasiKeyPressed(evt);
            }
        });
        FormInput.add(SkorMobilisasi);
        SkorMobilisasi.setBounds(700, 240, 120, 23);

        Aktivitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Dalam radiant warmer dengan plastik transparan", "Dalam radiant warmer tanpa plastik transparan", "Dalam a double walled isolette / inkubator dengan 2 jendela", "Dalam boks terbuka" }));
        Aktivitas.setName("Aktivitas"); // NOI18N
        Aktivitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AktivitasActionPerformed(evt);
            }
        });
        Aktivitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AktivitasKeyPressed(evt);
            }
        });
        FormInput.add(Aktivitas);
        Aktivitas.setBounds(140, 270, 530, 23);

        SkorAktivitas.setEditable(false);
        SkorAktivitas.setFocusTraversalPolicyProvider(true);
        SkorAktivitas.setName("SkorAktivitas"); // NOI18N
        SkorAktivitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorAktivitasKeyPressed(evt);
            }
        });
        FormInput.add(SkorAktivitas);
        SkorAktivitas.setBounds(700, 270, 120, 23);

        Nutrisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Nutrisi hanya dapat diberikan melalui intravena", "Mendapatkan nutrisi melalui gastric tube (susu formula / ASI) dan cairan intravena", "Mendapatkan nutrisi melalui gastric tube", "Bayi dapat menyusu langsung atau menggunakan botol setiap kali minum" }));
        Nutrisi.setName("Nutrisi"); // NOI18N
        Nutrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NutrisiActionPerformed(evt);
            }
        });
        Nutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NutrisiKeyPressed(evt);
            }
        });
        FormInput.add(Nutrisi);
        Nutrisi.setBounds(140, 300, 530, 23);

        SkorNutrisi.setEditable(false);
        SkorNutrisi.setFocusTraversalPolicyProvider(true);
        SkorNutrisi.setName("SkorNutrisi"); // NOI18N
        SkorNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(SkorNutrisi);
        SkorNutrisi.setBounds(700, 300, 120, 23);

        Kelembaban.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Kulit bayi selalu lembab, linen sering diganti", "Kulit bayi selalu lembab, linen sering diganti minimal setiap shift", "Kulit bayi selalu lembab, membutuhkan pergantian ekstra linen minimal sekali sehari", "Kulit bayi biasanya kering, membutuhkan pergantian linen hanya sekali sehari" }));
        Kelembaban.setName("Kelembaban"); // NOI18N
        Kelembaban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KelembabanActionPerformed(evt);
            }
        });
        Kelembaban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelembabanKeyPressed(evt);
            }
        });
        FormInput.add(Kelembaban);
        Kelembaban.setBounds(140, 330, 530, 23);

        SkorKelembaban.setEditable(false);
        SkorKelembaban.setFocusTraversalPolicyProvider(true);
        SkorKelembaban.setName("SkorKelembaban"); // NOI18N
        SkorKelembaban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorKelembabanKeyPressed(evt);
            }
        });
        FormInput.add(SkorKelembaban);
        SkorKelembaban.setBounds(700, 330, 120, 23);

        TotalSkor.setEditable(false);
        TotalSkor.setFocusTraversalPolicyProvider(true);
        TotalSkor.setName("TotalSkor"); // NOI18N
        TotalSkor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalSkorKeyPressed(evt);
            }
        });
        FormInput.add(TotalSkor);
        TotalSkor.setBounds(700, 370, 120, 23);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "PARAF & NAMA PETUGAS YANG MENILAI", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass2.setBackground(new java.awt.Color(255, 255, 255));
        FormPass2.setBorder(null);
        FormPass2.setName("FormPass2"); // NOI18N
        FormPass2.setPreferredSize(new java.awt.Dimension(115, 40));

        BtnRefreshPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto.setMnemonic('U');
        BtnRefreshPhoto.setText("Refresh");
        BtnRefreshPhoto.setToolTipText("Alt+U");
        BtnRefreshPhoto.setName("BtnRefreshPhoto"); // NOI18N
        BtnRefreshPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(BtnRefreshPhoto);

        FormPhoto.add(FormPass2, java.awt.BorderLayout.PAGE_END);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        FormInput.add(FormPhoto);
        FormPhoto.setBounds(860, 120, 380, 340);

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

        jLabel55.setText("Hari ke :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(410, 70, 60, 20);

        jLabel56.setText("Kamar :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 70, 70, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("PARAMETER");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(20, 140, 110, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 80, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("KRITERIA");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(130, 140, 550, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Usia Gestasi");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(20, 180, 110, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("SKORING KE : ");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(30, 100, 100, 20);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("TGL :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(720, 140, 30, 20);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setText("Status Mental");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(20, 210, 110, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("SKOR");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(680, 140, 40, 20);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setText("Mobilisasi");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(20, 240, 110, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel76.setText("Aktivitas");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(20, 270, 110, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setText("Nutrisi");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(20, 300, 110, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setText("( Bayi cukup Bulan 0 Hari sampai dengan 30 Hari atau Bayi Premature 0 Hari sampai dengan usia Gestasi < 40 Minggu ) ");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(200, 110, 630, 20);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel79.setText("Kelembaban");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(20, 330, 110, 23);

        jLabel80.setText("TOTAL SKOR :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(20, 370, 650, 23);

        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(20, 130, 810, 5);

        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(20, 170, 810, 10);

        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(20, 360, 810, 10);

        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(20, 400, 810, 10);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Jika skor > 13, lakukan protok penatalaksanaan risiko trauma kulit pada neonatus");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(20, 380, 540, 20);

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

        } else if (KdKamar.getText().trim().equals("") ||
                   NamaKamar.getText().trim().equals("")) {

            Valid.textKosong(KdKamar, "kamar");

        } else if (NIP.getText().trim().equals("") ||
                   NamaPetugas.getText().trim().equals("")) {

            Valid.textKosong(NIP, "petugas");

        } else {
            String tanggal =
                    Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " +
                    Jam.getSelectedItem() + ":" +
                    Menit.getSelectedItem() + ":" +
                    Detik.getSelectedItem();

            String tanggalSkoring =
                    Valid.SetTgl(TanggalSkoring.getSelectedItem() + "");

            String paraf = "/webapps/verified/uploads/ttdpetugas/" +
                NIP.getText().trim() +
                ".png";

            if (Sequel.menyimpantf(
                    "pemantauan_risiko_trauma_kulit_neonatus",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                    "Data",
                    20,
                    new String[]{
                        TNoRw.getText().trim(),                   // 1  no_rawat
                        tanggal,                                  // 2  tanggal
                        KdKamar.getText().trim(),                 // 3  kd_kamar
                        Skoring.getSelectedItem().toString(),     // 4  skoring
                        tanggalSkoring,                           // 5  tanggal_skoring
                        UsiaGestasi.getSelectedItem().toString(), // 6  usia_gestasi
                        SkorUsiaGestasi.getText().trim(),         // 7  skor_usia_gestasi
                        StatusMental.getSelectedItem().toString(),// 8  status_mental
                        SkorStatusMental.getText().trim(),        // 9  skor_status_mental
                        Mobilisasi.getSelectedItem().toString(),  // 10 mobilisasi
                        SkorMobilisasi.getText().trim(),          // 11 skor_mobilisasi
                        Aktivitas.getSelectedItem().toString(),   // 12 aktivitas
                        SkorAktivitas.getText().trim(),           // 13 skor_aktivitas
                        Nutrisi.getSelectedItem().toString(),     // 14 nutrisi
                        SkorNutrisi.getText().trim(),             // 15 skor_nutrisi
                        Kelembaban.getSelectedItem().toString(),  // 16 kelembaban
                        SkorKelembaban.getText().trim(),          // 17 skor_kelembaban
                        TotalSkor.getText().trim(),               // 18 total_skor
                        NIP.getText().trim(),                      // 19 nip
                        paraf                                     // 20 paraf
                    }
            )) {
                tabMode.addRow(new Object[]{
                    TNoRw.getText().trim(),                    // 0
                    TNoRM.getText().trim(),                    // 1
                    TPasien.getText().trim(),                  // 2
                    JK.getText().trim(),                  // 2
                    JK.getText().trim(),                       // 3
                    TglLahir.getText().trim(),                 // 4
                    tanggal,                                   // 5
                    KdKamar.getText().trim(),                  // 6
                    NamaKamar.getText().trim(),                // 7
                    Skoring.getSelectedItem().toString(),      // 8
                    tanggalSkoring,                            // 9
                    UsiaGestasi.getSelectedItem().toString(),  // 10
                    SkorUsiaGestasi.getText().trim(),          // 11
                    StatusMental.getSelectedItem().toString(), // 12
                    SkorStatusMental.getText().trim(),         // 13
                    Mobilisasi.getSelectedItem().toString(),   // 14
                    SkorMobilisasi.getText().trim(),           // 15
                    Aktivitas.getSelectedItem().toString(),    // 16
                    SkorAktivitas.getText().trim(),            // 17
                    Nutrisi.getSelectedItem().toString(),      // 18
                    SkorNutrisi.getText().trim(),              // 19
                    Kelembaban.getSelectedItem().toString(),   // 20
                    SkorKelembaban.getText().trim(),           // 21
                    TotalSkor.getText().trim(),                // 22
                    NIP.getText().trim(),                       // 23
                    NamaPetugas.getText().trim(),              // 24
                    paraf                                      // 25
                });

                LCount.setText(String.valueOf(tabMode.getRowCount()));
                tampilkanPilihanSkoring();
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
        if (tbObat.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(
                rootPane,
                "Silakan pilih data yang akan dihapus terlebih dahulu."
            );
            return;
        }

        int row = tbObat.convertRowIndexToModel(
            tbObat.getSelectedRow()
        );

        String nipPenyimpan = getValue(row, 24);

        if (akses.getkode().equals("Admin Utama")) {
            hapus();

        } else if (akses.getkode().equals(nipPenyimpan)) {
            hapus();

        } else {
            JOptionPane.showMessageDialog(
                null,
                "Data hanya dapat dihapus oleh petugas yang bersangkutan."
            );
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
        if (TNoRw.getText().trim().equals("") ||
            TPasien.getText().trim().equals("")) {

            Valid.textKosong(TNoRw, "pasien");

        } else if (KdKamar.getText().trim().equals("") ||
                   NamaKamar.getText().trim().equals("")) {

            Valid.textKosong(KdKamar, "kamar");

        } else if (NIP.getText().trim().equals("") ||
                   NamaPetugas.getText().trim().equals("")) {

            Valid.textKosong(NIP, "petugas");

        } else if (tbObat.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(
                rootPane,
                "Silakan pilih data yang akan diubah terlebih dahulu."
            );

        } else {
            int row = tbObat.convertRowIndexToModel(
                tbObat.getSelectedRow()
            );

            String nipPenyimpan = getValue(row, 24);

            if (akses.getkode().equals("Admin Utama")) {
                ganti();

            } else if (NIP.getText().trim().equals(nipPenyimpan)) {
                ganti();

            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "Data hanya dapat diubah oleh petugas yang bersangkutan."
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
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if (tbObat.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null,"Silakan pilih data pasien yang akan dicetak.");
            return;
        }

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            int row = tbObat.convertRowIndexToModel(tbObat.getSelectedRow());
            String noRawat = String.valueOf(tabMode.getValueAt(row,0));

            String noRM="",namaPasien="",jk="",tglLahir="",tglMasuk="",jamMasuk="",namaKamar="";
            String[] tanggalSkoring=new String[7];
            String[] namaPetugas=new String[7];
            String[] paraf=new String[7];
            int[] totalSkor=new int[7];
            int[][] skor=new int[7][6];

            ps=koneksi.prepareStatement(
                "select p.no_rkm_medis,p.nm_pasien,p.jk,p.tgl_lahir,"+
                "rp.tgl_registrasi,rp.jam_reg,bs.nm_bangsal,a.skoring,a.tanggal_skoring,"+
                "a.skor_usia_gestasi,a.skor_status_mental,a.skor_mobilisasi,"+
                "a.skor_aktivitas,a.skor_nutrisi,a.skor_kelembaban,a.total_skor,"+
                "pt.nama as nama_petugas,a.paraf "+
                "from pemantauan_risiko_trauma_kulit_neonatus a "+
                "inner join reg_periksa rp on rp.no_rawat=a.no_rawat "+
                "inner join pasien p on p.no_rkm_medis=rp.no_rkm_medis "+
                "left join petugas pt on pt.nip=a.nip "+
                "left join kamar km on km.kd_kamar=a.kd_kamar "+
                "left join bangsal bs on bs.kd_bangsal=km.kd_bangsal "+
                "where a.no_rawat=? order by a.skoring"
            );
            ps.setString(1,noRawat);
            rs=ps.executeQuery();
            boolean adaData=false;
            while(rs.next()){
                adaData=true;
                noRM=rs.getString("no_rkm_medis");
                namaPasien=rs.getString("nm_pasien");
                jk=rs.getString("jk");
                tglLahir=rs.getString("tgl_lahir");
                tglMasuk=rs.getString("tgl_registrasi");
                jamMasuk=rs.getString("jam_reg");
                namaKamar=rs.getString("nm_bangsal");
                int s=rs.getInt("skoring");
                if(s>=1 && s<=6){
                    tanggalSkoring[s]=rs.getString("tanggal_skoring");
                    skor[s][0]=rs.getInt("skor_usia_gestasi");
                    skor[s][1]=rs.getInt("skor_status_mental");
                    skor[s][2]=rs.getInt("skor_mobilisasi");
                    skor[s][3]=rs.getInt("skor_aktivitas");
                    skor[s][4]=rs.getInt("skor_nutrisi");
                    skor[s][5]=rs.getInt("skor_kelembaban");
                    totalSkor[s]=rs.getInt("total_skor");
                    namaPetugas[s]=rs.getString("nama_petugas");
                    paraf[s]=rs.getString("paraf");
                }
            }

            if(!adaData){
                JOptionPane.showMessageDialog(null,"Data pemantauan tidak ditemukan.");
                return;
            }

            String[][] kriteria={
                {"Usia Gestasi","< 28 minggu","28 minggu - < 33 minggu","33 minggu - 38 minggu","> 38 minggu"},
                {"Status Mental","Tidak berespon terhadap stimulus nyeri / koma","Hanya berespon pada nyeri / sopor","Letargi / apatis","Sadar dan aktif / compos mentis"},
                {"Mobilisasi","Tidak mampu bergerak","Bergerak sedikit dengan bantuan","Bergerak sedikit tanpa bantuan","Bergerak aktif"},
                {"Aktivitas","Dalam radiant warmer dengan plastik transparan","Dalam radiant warmer tanpa plastik transparan","Dalam a double walled isolette / inkubator dengan 2 jendela","Dalam boks terbuka"},
                {"Nutrisi","Nutrisi hanya dapat diberikan melalui intravena","Mendapatkan nutrisi melalui gastric tube (susu formula / ASI) dan cairan intravena","Mendapatkan nutrisi melalui gastric tube","Bayi dapat menyusu langsung atau menggunakan botol setiap kali minum"},
                {"Kelembaban","Kulit bayi selalu lembab, linen sering diganti","Kulit bayi selalu lembab, linen sering diganti minimal setiap shift","Kulit bayi selalu lembab, membutuhkan pergantian ekstra linen minimal sekali sehari","Kulit bayi biasanya kering, membutuhkan pergantian linen hanya sekali sehari"}
            };

            StringBuilder html=new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Pemantauan Risiko Trauma Kulit Neonatus</title>");
            html.append("<style>@page{size:A4 portrait;margin:8mm}body{font-family:Arial,sans-serif;font-size:9px;color:#000;margin:0}");
            html.append("table{border-collapse:collapse;width:100%;table-layout:fixed}td,th{border:1px solid #000;padding:2px;vertical-align:middle}");
            html.append(".tanpa td{border:0;padding:1px}.judul{font-size:14px;font-weight:bold;text-align:center}.center{text-align:center}.kecil{font-size:8px}");
            html.append(".parameter{width:12%;font-weight:bold;text-align:center}.kriteria{width:35%}.nilai{width:5%;text-align:center}.skoring{width:8%;text-align:center}");
            html.append("@media print{button{display:none}}</style></head><body>");
            html.append("<table><tr><td style='width:52%' class='judul'>").append(htmlEscape(akses.getnamars())).append("<br>Pemantauan Risiko Trauma Kulit<br>Pada Pasien Neonatus</td>");
            html.append("<td style='width:48%'><table class='tanpa'><tr><td>No. RM</td><td>: ").append(htmlEscape(noRM)).append("</td></tr>");
            html.append("<tr><td>Nama Pasien</td><td>: ").append(htmlEscape(namaPasien)).append("</td></tr>");
            html.append("<tr><td>Tanggal Lahir</td><td>: ").append(htmlEscape(tglLahir)).append(" &nbsp; ").append(htmlEscape(jk)).append("</td></tr></table></td></tr></table>");
            html.append("<div style='margin:6px 0'>Tanggal masuk ruang rawat: <b>").append(htmlEscape(tglMasuk)).append("</b> &nbsp; Jam: <b>").append(htmlEscape(jamMasuk)).append("</b><br>");
            html.append("Ruang rawat/Unit Kerja: <b>").append(htmlEscape(namaKamar)).append("</b></div>");
            html.append("<div class='center' style='font-weight:bold;margin:5px'>(Bayi cukup bulan 0 hari sampai dengan 30 hari atau<br>Bayi prematur 0 hari sampai dengan usia gestasi &lt; 40 minggu)</div>");
            html.append("<table><thead><tr><th class='parameter'>PARAMETER</th><th class='kriteria'>KRITERIA</th><th class='nilai'>SKOR</th>");
            for(int s=1;s<=6;s++) html.append("<th class='skoring'>SKORING ").append(s).append("<br><span class='kecil'>TGL: ").append(htmlEscape(tanggalSkoring[s])).append("</span></th>");
            html.append("</tr></thead><tbody>");

            for(int p=0;p<kriteria.length;p++){
                for(int n=1;n<=4;n++){
                    int nilai=5-n;
                    html.append("<tr>");
                    if(n==1) html.append("<td class='parameter' rowspan='4'>").append(htmlEscape(kriteria[p][0])).append("</td>");
                    html.append("<td>").append(htmlEscape(kriteria[p][n])).append("</td><td class='center'>").append(nilai).append("</td>");
                    for(int s=1;s<=6;s++) html.append("<td class='center'>").append(skor[s][p]==nilai?"X":"&nbsp;").append("</td>");
                    html.append("</tr>");
                }
            }

            html.append("<tr><td colspan='3' class='center'><b>TOTAL SKOR</b></td>");
            for(int s=1;s<=6;s++) html.append("<td class='center'><b>").append(totalSkor[s]==0?"":String.valueOf(totalSkor[s])).append("</b></td>");
            html.append("</tr><tr><td colspan='3' class='center'><b>PARAF &amp; NAMA PETUGAS YANG MENILAI</b></td>");
            String baseUrl="http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB();
            for(int s=1;s<=6;s++){
                html.append("<td class='center kecil'>");
                if(paraf[s]!=null && !paraf[s].trim().equals("")) html.append("<img src='").append(htmlEscape(baseUrl+paraf[s])).append("' style='max-width:55px;max-height:38px'><br>");
                html.append(htmlEscape(namaPetugas[s])).append("</td>");
            }
            html.append("</tr></tbody></table>");
            html.append("<div style='margin-top:6px;font-weight:bold'>Jika skor &gt; 13, lakukan protok penatalaksanaan risiko trauma kulit pada neonatus.</div>");
            html.append("<div class='center' style='margin-top:8px'><button onclick='window.print()'>Cetak</button></div></body></html>");

            File f=new File("FormPemantauanRisikoTraumaKulitNeonatus.html");
            BufferedWriter bw=new BufferedWriter(new FileWriter(f));
            bw.write(html.toString());
            bw.close();
            Desktop.getDesktop().browse(f.toURI());

        } catch (Exception e) {
            System.out.println("Notif cetak pemantauan trauma kulit neonatus: "+e);
            JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat membuat formulir HTML:\n"+e.getMessage());

        } finally {
            try{if(rs!=null)rs.close();}catch(Exception e){}
            try{if(ps!=null)ps.close();}catch(Exception e){}
            this.setCursor(Cursor.getDefaultCursor());
        }
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
        
    }//GEN-LAST:event_btnPetugasKeyPressed

    private String queryDasarPemantauanTraumaKulit() {
        return
            "SELECT " +
            "a.no_rawat," +
            "p.no_rkm_medis," +
            "p.nm_pasien," +
            "rp.umurdaftar," +
            "rp.sttsumur," +
            "p.jk," +
            "p.tgl_lahir," +
            "rp.tgl_registrasi," +
            "rp.jam_reg," +
            "a.tanggal," +
            "a.kd_kamar," +
            "bs.nm_bangsal AS nama_kamar," +
            "a.skoring," +
            "a.tanggal_skoring," +
            "a.usia_gestasi," +
            "a.skor_usia_gestasi," +
            "a.status_mental," +
            "a.skor_status_mental," +
            "a.mobilisasi," +
            "a.skor_mobilisasi," +
            "a.aktivitas," +
            "a.skor_aktivitas," +
            "a.nutrisi," +
            "a.skor_nutrisi," +
            "a.kelembaban," +
            "a.skor_kelembaban," +
            "a.total_skor," +
            "a.nip," +
            "pt.nama AS nama_petugas," +
            "a.paraf " +

            "FROM pemantauan_risiko_trauma_kulit_neonatus AS a " +

            "INNER JOIN reg_periksa AS rp " +
            "ON rp.no_rawat=a.no_rawat " +

            "INNER JOIN pasien AS p " +
            "ON p.no_rkm_medis=rp.no_rkm_medis " +

            "LEFT JOIN petugas AS pt " +
            "ON pt.nip=a.nip " +

            "LEFT JOIN kamar AS km " +
            "ON km.kd_kamar=a.kd_kamar " +

            "LEFT JOIN bangsal AS bs " +
            "ON bs.kd_bangsal=km.kd_bangsal ";
    }
    
    private void CetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CetakActionPerformed
        if (tbObat.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(
                null,
                "Silakan pilih pasien yang akan dicetak."
            );
            return;
        }

        this.setCursor(
            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
        );

        try {
            int row = tbObat.convertRowIndexToModel(
                tbObat.getSelectedRow()
            );

            String noRawat = getValue(row, 0)
                .replace("'", "''");

            Map<String, Object> param = new HashMap<>();

            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());

            param.put(
                "logo",
                Sequel.cariGambar(
                    "select setting.logo from setting"
                )
            );

            Valid.MyReportqry(
                "rptFormulirPemantauanRisikoTraumaKulitNeonatus.jasper",
                "report",
                "::[ Formulir Pemantauan Risiko Trauma Kulit " +
                "Pada Pasien Neonatus ]::",

                "SELECT " +
                "rp.no_rawat," +
                "p.no_rkm_medis," +
                "p.nm_pasien," +
                "rp.umurdaftar," +
                "rp.sttsumur," +
                "rp.tgl_registrasi," +
                "rp.jam_reg," +
                "p.jk," +
                "p.tgl_lahir," +

                "a.tanggal," +
                "a.kd_kamar," +
                "bs.nm_bangsal AS nama_kamar," +
                "a.skoring," +
                "a.tanggal_skoring," +

                "a.usia_gestasi," +
                "a.skor_usia_gestasi," +

                "a.status_mental," +
                "a.skor_status_mental," +

                "a.mobilisasi," +
                "a.skor_mobilisasi," +

                "a.aktivitas," +
                "a.skor_aktivitas," +

                "a.nutrisi," +
                "a.skor_nutrisi," +

                "a.kelembaban," +
                "a.skor_kelembaban," +

                "a.total_skor," +
                "a.nip," +
                "pt.nama AS nama_petugas," +
                "a.paraf " +

                "FROM pemantauan_risiko_trauma_kulit_neonatus AS a " +

                "INNER JOIN reg_periksa AS rp " +
                "ON rp.no_rawat=a.no_rawat " +

                "INNER JOIN pasien AS p " +
                "ON p.no_rkm_medis=rp.no_rkm_medis " +

                "LEFT JOIN petugas AS pt " +
                "ON pt.nip=a.nip " +

                "LEFT JOIN kamar AS km " +
                "ON km.kd_kamar=a.kd_kamar " +

                "LEFT JOIN bangsal AS bs " +
                "ON bs.kd_bangsal=km.kd_bangsal " +

                "WHERE a.no_rawat='" + noRawat + "' " +

                "ORDER BY a.skoring",

                param
            );

        } catch (Exception e) {
            System.out.println(
                "Notif cetak formulir pemantauan " +
                "risiko trauma kulit neonatus: " + e
            );

            JOptionPane.showMessageDialog(
                null,
                "Terjadi kesalahan saat mencetak:\n" +
                e.getMessage()
            );

        } finally {
            this.setCursor(
                Cursor.getDefaultCursor()
            );
        }
    }//GEN-LAST:event_CetakActionPerformed

    private void btnKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamarActionPerformed
        ranap.load();
        ranap.isCek();
        ranap.emptTeks();
        ranap.tampil();
        ranap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        ranap.setLocationRelativeTo(internalFrame1);
        ranap.setVisible(true);
    }//GEN-LAST:event_btnKamarActionPerformed

    private void btnKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKamarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKamarKeyPressed

    private void KdKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKamarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKamarKeyPressed

    private void HariKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HariKeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HariKeKeyPressed

    private void HariKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HariKeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HariKeActionPerformed

    private void UsiaGestasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaGestasiKeyPressed

    }//GEN-LAST:event_UsiaGestasiKeyPressed

    private void TanggalSkoringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSkoringKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalSkoringKeyPressed

    private void SkorUsiaGestasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorUsiaGestasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorUsiaGestasiKeyPressed

    private void SkorStatusMentalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorStatusMentalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorStatusMentalKeyPressed

    private void StatusMentalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusMentalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusMentalKeyPressed

    private void SkoringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkoringKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkoringKeyPressed

    private void SkoringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkoringActionPerformed
        if (sedangMengaturSkoring ||
            Skoring.getSelectedItem() == null) {
            return;
        }

        int pilihan = Integer.parseInt(
            Skoring.getSelectedItem().toString()
        );

        if (pilihan <= skoringTerakhir) {
            sedangMengaturSkoring = true;

            Skoring.setSelectedItem(
                String.valueOf(skoringTerakhir + 1)
            );

            sedangMengaturSkoring = false;

            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_SkoringActionPerformed

    private void MobilisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MobilisasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MobilisasiKeyPressed

    private void SkorMobilisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorMobilisasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorMobilisasiKeyPressed

    private void AktivitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AktivitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AktivitasKeyPressed

    private void SkorAktivitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorAktivitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorAktivitasKeyPressed

    private void SkorNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorNutrisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorNutrisiKeyPressed

    private void NutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NutrisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NutrisiKeyPressed

    private void TotalSkorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalSkorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalSkorKeyPressed

    private void KelembabanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelembabanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KelembabanKeyPressed

    private void SkorKelembabanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorKelembabanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorKelembabanKeyPressed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        loadSignaturePhotos();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void hitungTotalSkor() {
        int total =
            (int) Valid.SetAngka(
                SkorUsiaGestasi.getText()
            ) +
            (int) Valid.SetAngka(
                SkorStatusMental.getText()
            ) +
            (int) Valid.SetAngka(
                SkorMobilisasi.getText()
            ) +
            (int) Valid.SetAngka(
                SkorAktivitas.getText()
            ) +
            (int) Valid.SetAngka(
                SkorNutrisi.getText()
            ) +
            (int) Valid.SetAngka(
                SkorKelembaban.getText()
            );

        /*
         * DocumentListener otomatis dipanggil setelah
         * nilai TotalSkor berubah.
         */
        TotalSkor.setText(
            String.valueOf(total)
        );
    }
    
    private void UsiaGestasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsiaGestasiActionPerformed
        int index = UsiaGestasi.getSelectedIndex();

        if (index >= 1 && index <= 4) {
            SkorUsiaGestasi.setText(
                String.valueOf(5 - index)
            );
        } else {
            SkorUsiaGestasi.setText("0");
        }

        hitungTotalSkor();
    }//GEN-LAST:event_UsiaGestasiActionPerformed

    private void StatusMentalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusMentalActionPerformed
        int index = StatusMental.getSelectedIndex();

        if (index >= 1 && index <= 4) {
            SkorStatusMental.setText(
                String.valueOf(5 - index)
            );
        } else {
            SkorStatusMental.setText("0");
        }

        hitungTotalSkor();
    }//GEN-LAST:event_StatusMentalActionPerformed

    private void MobilisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MobilisasiActionPerformed
        int index = Mobilisasi.getSelectedIndex();

        if (index >= 1 && index <= 4) {
            SkorMobilisasi.setText(
                String.valueOf(5 - index)
            );
        } else {
            SkorMobilisasi.setText("0");
        }

        hitungTotalSkor();
    }//GEN-LAST:event_MobilisasiActionPerformed

    private void AktivitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AktivitasActionPerformed
        int index = Aktivitas.getSelectedIndex();

        if (index >= 1 && index <= 4) {
            SkorAktivitas.setText(
                String.valueOf(5 - index)
            );
        } else {
            SkorAktivitas.setText("0");
        }

        hitungTotalSkor();
    }//GEN-LAST:event_AktivitasActionPerformed

    private void NutrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NutrisiActionPerformed
        int index = Nutrisi.getSelectedIndex();

        if (index >= 1 && index <= 4) {
            SkorNutrisi.setText(
                String.valueOf(5 - index)
            );
        } else {
            SkorNutrisi.setText("0");
        }

        hitungTotalSkor();
    }//GEN-LAST:event_NutrisiActionPerformed

    private void KelembabanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KelembabanActionPerformed
        int index = Kelembaban.getSelectedIndex();

        if (index >= 1 && index <= 4) {
            SkorKelembaban.setText(
                String.valueOf(5 - index)
            );
        } else {
            SkorKelembaban.setText("0");
        }

        hitungTotalSkor();
    }//GEN-LAST:event_KelembabanActionPerformed

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
    private widget.ComboBox Aktivitas;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.Button BtnSimpan;
    private javax.swing.JMenuItem Cetak;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox HariKe;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.TextBox KdKamar;
    private widget.ComboBox Kelembaban;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private widget.ComboBox Mobilisasi;
    private widget.TextBox NIP;
    private widget.TextBox NamaKamar;
    private widget.TextBox NamaPetugas;
    private widget.ComboBox Nutrisi;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.TextBox SkorAktivitas;
    private widget.TextBox SkorKelembaban;
    private widget.TextBox SkorMobilisasi;
    private widget.TextBox SkorNutrisi;
    private widget.TextBox SkorStatusMental;
    private widget.TextBox SkorUsiaGestasi;
    private widget.ComboBox Skoring;
    private widget.ComboBox StatusMental;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalSkoring;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalSkor;
    private widget.TextBox Umur;
    private widget.ComboBox UsiaGestasi;
    private widget.Button btnKamar;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel5;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel6;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel7;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel80;
    private widget.Label jLabel82;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
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
            "    CONCAT(b.umurdaftar,' ',b.sttsumur) AS umur, " +     // 3
            "    c.jk, " +                                            // 4
            "    c.tgl_lahir, " +                                     // 5
            "    a.tanggal, " +                                       // 6
            "    a.kd_kamar, " +                                      // 7
            "    f.nm_bangsal AS nama_kamar, " +                      // 8
            "    a.skoring, " +                                       // 9
            "    a.tanggal_skoring, " +                               // 10
            "    a.usia_gestasi, " +                                  // 11
            "    a.skor_usia_gestasi, " +                             // 12
            "    a.status_mental, " +                                 // 13
            "    a.skor_status_mental, " +                            // 14
            "    a.mobilisasi, " +                                    // 15
            "    a.skor_mobilisasi, " +                               // 16
            "    a.aktivitas, " +                                     // 17
            "    a.skor_aktivitas, " +                                // 18
            "    a.nutrisi, " +                                       // 19
            "    a.skor_nutrisi, " +                                  // 20
            "    a.kelembaban, " +                                    // 21
            "    a.skor_kelembaban, " +                               // 22
            "    a.total_skor, " +                                    // 23
            "    a.nip, " +                                           // 24
            "    d.nama AS nama_petugas, " +                          // 25
            "    a.paraf " +                                          // 26
            "FROM pemantauan_risiko_trauma_kulit_neonatus AS a " +
            "INNER JOIN reg_periksa AS b " +
            "    ON b.no_rawat = a.no_rawat " +
            "INNER JOIN pasien AS c " +
            "    ON c.no_rkm_medis = b.no_rkm_medis " +
            "LEFT JOIN petugas AS d " +
            "    ON d.nip = a.nip " +
            "LEFT JOIN kamar AS e " +
            "    ON e.kd_kamar = a.kd_kamar " +
            "LEFT JOIN bangsal AS f " +
            "    ON f.kd_bangsal = e.kd_bangsal " +
            "WHERE a.tanggal BETWEEN ? AND ? ";

        if (!cari.equals("")) {
            sql +=
                "AND (" +
                "    a.no_rawat LIKE ? " +
                " OR c.no_rkm_medis LIKE ? " +
                " OR c.nm_pasien LIKE ? " +
                " OR a.kd_kamar LIKE ? " +
                " OR f.nm_bangsal LIKE ? " +
                " OR a.nip LIKE ? " +
                " OR d.nama LIKE ? " +
                ") ";
        }

        sql +=
            "ORDER BY " +
            "    a.tanggal DESC, " +
            "    a.skoring DESC";

        try {
            ps = koneksi.prepareStatement(sql);

            /*
             * Kolom tanggal bertipe DATETIME, sehingga pencarian
             * menggunakan awal dan akhir hari.
             */
            ps.setString(
                1,
                Valid.SetTgl(DTPCari1.getSelectedItem() + "") +
                " 00:00:00"
            );

            ps.setString(
                2,
                Valid.SetTgl(DTPCari2.getSelectedItem() + "") +
                " 23:59:59"
            );

            if (!cari.equals("")) {
                String kataKunci = "%" + cari + "%";

                ps.setString(3, kataKunci); // no_rawat
                ps.setString(4, kataKunci); // no_rkm_medis
                ps.setString(5, kataKunci); // nama pasien
                ps.setString(6, kataKunci); // kode kamar
                ps.setString(7, kataKunci); // nama kamar
                ps.setString(8, kataKunci); // NIP
                ps.setString(9, kataKunci); // nama petugas
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                tabMode.addRow(new Object[]{
                    rs.getString("no_rawat"),           // 0
                    rs.getString("no_rkm_medis"),       // 1
                    rs.getString("nm_pasien"),          // 2
                    rs.getString("umur"),               // 3
                    rs.getString("jk"),                 // 4
                    rs.getString("tgl_lahir"),          // 5
                    rs.getString("tanggal"),            // 6
                    rs.getString("kd_kamar"),           // 7
                    rs.getString("nama_kamar"),         // 8
                    rs.getString("skoring"),            // 9
                    rs.getString("tanggal_skoring"),    // 10
                    rs.getString("usia_gestasi"),       // 11
                    rs.getString("skor_usia_gestasi"),  // 12
                    rs.getString("status_mental"),      // 13
                    rs.getString("skor_status_mental"), // 14
                    rs.getString("mobilisasi"),         // 15
                    rs.getString("skor_mobilisasi"),    // 16
                    rs.getString("aktivitas"),          // 17
                    rs.getString("skor_aktivitas"),     // 18
                    rs.getString("nutrisi"),            // 19
                    rs.getString("skor_nutrisi"),       // 20
                    rs.getString("kelembaban"),         // 21
                    rs.getString("skor_kelembaban"),    // 22
                    rs.getString("total_skor"),         // 23
                    rs.getString("nip"),                // 24
                    rs.getString("nama_petugas"),       // 25
                    rs.getString("paraf")               // 26
                });
            }

        } catch (Exception e) {
            System.out.println(
                "Notif tampil pemantauan risiko trauma kulit neonatus: " +
                e
            );
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

        LCount.setText(
            String.valueOf(tabMode.getRowCount())
        );
    }
    
    public void emptTeks() {
        Date sekarang = new Date();
        
        NIP.setText("");
        NamaPetugas.setText("");

        Tanggal.setDate(sekarang);
        TanggalSkoring.setDate(sekarang);

        // Pilihan default "-"
        UsiaGestasi.setSelectedIndex(0);
        StatusMental.setSelectedIndex(0);
        Mobilisasi.setSelectedIndex(0);
        Aktivitas.setSelectedIndex(0);
        Nutrisi.setSelectedIndex(0);
        Kelembaban.setSelectedIndex(0);

        // Skor default 0
        SkorUsiaGestasi.setText("0");
        SkorStatusMental.setText("0");
        SkorMobilisasi.setText("0");
        SkorAktivitas.setText("0");
        SkorNutrisi.setText("0");
        SkorKelembaban.setText("0");
        TotalSkor.setText("0");

        ChkKejadian.setSelected(false);

        // Tentukan skoring berikutnya berdasarkan database.
        tampilkanPilihanSkoring();

        UsiaGestasi.requestFocus();
        
        setEmptyParaf();
    }

    private void getData() {
        if (tbObat.getSelectedRow() == -1) {
            return;
        }

        int row = tbObat.convertRowIndexToModel(
            tbObat.getSelectedRow()
        );

        TNoRw.setText(getValue(row, 0));
        TNoRM.setText(getValue(row, 1));
        TPasien.setText(getValue(row, 2));
        Umur.setText(getValue(row, 3));
        JK.setText(getValue(row, 4));
        TglLahir.setText(getValue(row, 5));

        /*
         * Kolom 6 berisi DATETIME:
         * yyyy-MM-dd HH:mm:ss
         */
        String tanggalData = getValue(row, 6);

        if (!tanggalData.equals("")) {
            if (tanggalData.length() >= 10) {
                Valid.SetTgl(
                    Tanggal,
                    tanggalData.substring(0, 10)
                );
            }

            if (tanggalData.length() >= 19) {
                Jam.setSelectedItem(
                    tanggalData.substring(11, 13)
                );

                Menit.setSelectedItem(
                    tanggalData.substring(14, 16)
                );

                Detik.setSelectedItem(
                    tanggalData.substring(17, 19)
                );
            }
        }

        KdKamar.setText(getValue(row, 7));
        NamaKamar.setText(getValue(row, 8));

        /*
         * Mencegah SkoringActionPerformed mengubah skoring
         * ketika data tabel sedang dimuat untuk diedit.
         */
        sedangMengaturSkoring = true;
        Skoring.setEnabled(true);
        Skoring.setSelectedItem(getValue(row, 9));
        sedangMengaturSkoring = false;

        if (!getValue(row, 10).equals("")) {
            Valid.SetTgl(
                TanggalSkoring,
                getValue(row, 10)
            );
        }

        UsiaGestasi.setSelectedItem(
            getValue(row, 11)
        );
        SkorUsiaGestasi.setText(
            getValue(row, 12)
        );

        StatusMental.setSelectedItem(
            getValue(row, 13)
        );
        SkorStatusMental.setText(
            getValue(row, 14)
        );

        Mobilisasi.setSelectedItem(
            getValue(row, 15)
        );
        SkorMobilisasi.setText(
            getValue(row, 16)
        );

        Aktivitas.setSelectedItem(
            getValue(row, 17)
        );
        SkorAktivitas.setText(
            getValue(row, 18)
        );

        Nutrisi.setSelectedItem(
            getValue(row, 19)
        );
        SkorNutrisi.setText(
            getValue(row, 20)
        );

        Kelembaban.setSelectedItem(
            getValue(row, 21)
        );
        SkorKelembaban.setText(
            getValue(row, 22)
        );

        TotalSkor.setText(
            getValue(row, 23)
        );

        NIP.setText(
            getValue(row, 24)
        );
        NamaPetugas.setText(
            getValue(row, 25)
        );

        /*
         * Kolom 26 adalah lokasi gambar paraf.
         * Tidak perlu dimasukkan ke komponen karena paraf
         * dibuat kembali berdasarkan NIP saat penyimpanan.
         */
        String lokasiParaf = getValue(row, 26);
        
        ChkInput.setSelected(false);

        // Memastikan warna form mengikuti TotalSkor.
        updateWarnaTotalSkor();
        loadSignaturePhotos();
    }

    private String getValue(int row, int column) {
        Object value = tbObat.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }

    private String htmlEscape(String value) {
        if(value==null){
            return "";
        }
        return value.replace("&","&amp;")
                    .replace("<","&lt;")
                    .replace(">","&gt;")
                    .replace("\"","&quot;")
                    .replace("'","&#39;");
    }
    
    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                "SELECT " +
                "    rp.no_rkm_medis, " +
                "    rp.tgl_registrasi, " +
                "    rp.umurdaftar, " +
                "    rp.sttsumur, " +
                "    p.nm_pasien, " +
                "    p.jk, " +
                "    p.tgl_lahir, " +
                "    IFNULL(ki.lama,0) AS lama, " +
                "    IFNULL(ki.kd_kamar,'') AS kd_kamar, " +
                "    IFNULL(bs.nm_bangsal,'') AS nama_kamar " +

                "FROM reg_periksa AS rp " +

                "INNER JOIN pasien AS p " +
                "    ON p.no_rkm_medis = rp.no_rkm_medis " +

                "LEFT JOIN kamar_inap AS ki " +
                "    ON ki.no_rawat = rp.no_rawat " +

                "LEFT JOIN kamar AS km " +
                "    ON km.kd_kamar = ki.kd_kamar " +

                "LEFT JOIN bangsal AS bs " +
                "    ON bs.kd_bangsal = km.kd_bangsal " +

                "WHERE rp.no_rawat = ? " +

                /*
                 * Jika pasien pernah pindah kamar, ambil kamar
                 * rawat inap yang paling terakhir.
                 */
                "ORDER BY " +
                "    ki.tgl_masuk DESC, " +
                "    ki.jam_masuk DESC " +

                "LIMIT 1"
            );

            try {
                ps.setString(
                    1,
                    TNoRw.getText().trim()
                );

                rs = ps.executeQuery();

                if (rs.next()) {
                    TNoRM.setText(
                        rs.getString("no_rkm_medis")
                    );

                    DTPCari1.setDate(
                        rs.getDate("tgl_registrasi")
                    );

                    TPasien.setText(
                        rs.getString("nm_pasien")
                    );

                    JK.setText(
                        rs.getString("jk")
                    );

                    Umur.setText(
                        rs.getString("umurdaftar") + " " +
                        rs.getString("sttsumur")
                    );

                    TglLahir.setText(
                        rs.getString("tgl_lahir")
                    );

                    HariKe.setText(
                        rs.getString("lama")
                    );

                    KdKamar.setText(
                        rs.getString("kd_kamar")
                    );

                    NamaKamar.setText(
                        rs.getString("nama_kamar")
                    );
                }

            } catch (Exception e) {
                System.out.println(
                    "Notif isRawat: " + e
                );
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception e) {
                    System.out.println(
                        "Notif close rs: " + e
                    );
                }

                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (Exception e) {
                    System.out.println(
                        "Notif close ps: " + e
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                "Notif prepare isRawat: " + e
            );
        }
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);

        isRawat();

        // Harus dipanggil setelah TNoRw terisi.
        tampilkanPilihanSkoring();

        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,500));
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
            Valid.textKosong(TNoRw, "data");

        } else if (TNoRw.getText().trim().equals("") ||
                   TPasien.getText().trim().equals("")) {

            Valid.textKosong(TNoRw, "pasien");

        } else if (KdKamar.getText().trim().equals("") ||
                   NamaKamar.getText().trim().equals("")) {

            Valid.textKosong(KdKamar, "kamar");

        } else if (NIP.getText().trim().equals("") ||
                   NamaPetugas.getText().trim().equals("")) {

            Valid.textKosong(NIP, "petugas");

        } else if (UsiaGestasi.getSelectedIndex() == 0) {
            Valid.textKosong(
                SkorUsiaGestasi,
                "usia gestasi"
            );

        } else if (StatusMental.getSelectedIndex() == 0) {
            Valid.textKosong(
                SkorStatusMental,
                "status mental"
            );

        } else if (Mobilisasi.getSelectedIndex() == 0) {
            Valid.textKosong(
                SkorMobilisasi,
                "mobilisasi"
            );

        } else if (Aktivitas.getSelectedIndex() == 0) {
            Valid.textKosong(
                SkorAktivitas,
                "aktivitas"
            );

        } else if (Nutrisi.getSelectedIndex() == 0) {
            Valid.textKosong(
                SkorNutrisi,
                "nutrisi"
            );

        } else if (Kelembaban.getSelectedIndex() == 0) {
            Valid.textKosong(
                SkorKelembaban,
                "kelembaban"
            );

        } else {
            int row = tbObat.convertRowIndexToModel(
                tbObat.getSelectedRow()
            );

            /*
             * Kunci data lama diambil dari tabel sebelum
             * nilainya diganti melalui form.
             */
            String noRawatLama = getValue(row, 0);
            String tanggalLama = getValue(row, 6);
            String skoringLama = getValue(row, 9);

            String tanggalBaru =
                Valid.SetTgl(
                    Tanggal.getSelectedItem() + ""
                ) + " " +
                Jam.getSelectedItem() + ":" +
                Menit.getSelectedItem() + ":" +
                Detik.getSelectedItem();

            String tanggalSkoringBaru =
                Valid.SetTgl(
                    TanggalSkoring.getSelectedItem() + ""
                );

            String paraf =
                "/webapps/verified/uploads/ttdpetugas/" +
                NIP.getText().trim() +
                ".png";

            if (Sequel.mengedittf(
                    "pemantauan_risiko_trauma_kulit_neonatus",

                    /*
                     * Kondisi berdasarkan data lama.
                     */
                    "no_rawat=? AND tanggal=? AND skoring=?",

                    /*
                     * Nilai baru.
                     */
                    "no_rawat=?," +
                    "tanggal=?," +
                    "kd_kamar=?," +
                    "skoring=?," +
                    "tanggal_skoring=?," +
                    "usia_gestasi=?," +
                    "skor_usia_gestasi=?," +
                    "status_mental=?," +
                    "skor_status_mental=?," +
                    "mobilisasi=?," +
                    "skor_mobilisasi=?," +
                    "aktivitas=?," +
                    "skor_aktivitas=?," +
                    "nutrisi=?," +
                    "skor_nutrisi=?," +
                    "kelembaban=?," +
                    "skor_kelembaban=?," +
                    "total_skor=?," +
                    "nip=?," +
                    "paraf=?",

                    23,

                    new String[]{
                        // Nilai baru: 1–20
                        TNoRw.getText().trim(),                    // 1
                        tanggalBaru,                               // 2
                        KdKamar.getText().trim(),                  // 3
                        Skoring.getSelectedItem().toString(),      // 4
                        tanggalSkoringBaru,                        // 5
                        UsiaGestasi.getSelectedItem().toString(),  // 6
                        SkorUsiaGestasi.getText().trim(),          // 7
                        StatusMental.getSelectedItem().toString(), // 8
                        SkorStatusMental.getText().trim(),         // 9
                        Mobilisasi.getSelectedItem().toString(),   // 10
                        SkorMobilisasi.getText().trim(),           // 11
                        Aktivitas.getSelectedItem().toString(),    // 12
                        SkorAktivitas.getText().trim(),            // 13
                        Nutrisi.getSelectedItem().toString(),      // 14
                        SkorNutrisi.getText().trim(),              // 15
                        Kelembaban.getSelectedItem().toString(),   // 16
                        SkorKelembaban.getText().trim(),           // 17
                        TotalSkor.getText().trim(),                // 18
                        NIP.getText().trim(),                       // 19
                        paraf,                                     // 20

                        // Kondisi WHERE: 21–23
                        noRawatLama,                               // 21
                        tanggalLama,                               // 22
                        skoringLama                                // 23
                    }
            )) {
                tampil();
                emptTeks();

                JOptionPane.showMessageDialog(
                    null,
                    "Data berhasil diubah."
                );
            }
        }
    }

    private void hapus() {
        if (tbObat.getSelectedRow() == -1) {
            Valid.textKosong(TNoRw, "data");
            return;
        }

        int pilihan = JOptionPane.showConfirmDialog(
            rootPane,
            "Apakah Anda yakin ingin menghapus data yang dipilih?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION
        );

        if (pilihan != JOptionPane.YES_OPTION) {
            return;
        }

        int row = tbObat.convertRowIndexToModel(
            tbObat.getSelectedRow()
        );

        String noRawatLama = getValue(row, 0);
        String tanggalLama = getValue(row, 6);
        String skoringLama = getValue(row, 9);

        if (Sequel.queryu2tf(
                "DELETE FROM " +
                "pemantauan_risiko_trauma_kulit_neonatus " +
                "WHERE no_rawat=? " +
                "AND tanggal=? " +
                "AND skoring=?",
                3,
                new String[]{
                    noRawatLama,
                    tanggalLama,
                    skoringLama
                }
        )) {
            tampil();
            emptTeks();

            JOptionPane.showMessageDialog(
                null,
                "Data berhasil dihapus."
            );
        }
    }
   
    private void tampilkanPilihanSkoring() {
        sedangMengaturSkoring = true;

        skoringTerakhir = (int) Sequel.cariIsiAngka(
            "select ifnull(max(skoring),0) " +
            "from pemantauan_risiko_trauma_kulit_neonatus " +
            "where no_rawat=?",
            TNoRw.getText().trim()
        );

        Skoring.removeAllItems();

        for (int i = 1; i <= 6; i++) {
            Skoring.addItem(String.valueOf(i));
        }

        if (skoringTerakhir < 6) {
            Skoring.setEnabled(true);
            Skoring.setSelectedItem(
                String.valueOf(skoringTerakhir + 1)
            );
        } else {
            Skoring.setSelectedItem("6");
            Skoring.setEnabled(false);
        }

        sedangMengaturSkoring = false;
        Skoring.repaint();
    }
    
    private void updateWarnaTotalSkor() {
        int nilai = 0;

        try {
            String teks = TotalSkor.getText().trim();

            if (!teks.equals("")) {
                nilai = Integer.parseInt(teks);
            }
        } catch (NumberFormatException e) {
            nilai = 0;
        }

        if (nilai >= 0 && nilai <= 7) {
            // Hijau
            TotalSkor.setBackground(
                new java.awt.Color(102, 204, 102)
            );
            TotalSkor.setForeground(
                java.awt.Color.BLACK
            );

        } else if (nilai >= 7 && nilai <= 13) {
            // Kuning
            TotalSkor.setBackground(
                new java.awt.Color(255, 215, 0)
            );
            TotalSkor.setForeground(
                java.awt.Color.BLACK
            );

        } else if (nilai > 13) {
            // Merah
            TotalSkor.setBackground(
                new java.awt.Color(220, 53, 69)
            );
            TotalSkor.setForeground(
                java.awt.Color.WHITE
            );

        }

        TotalSkor.repaint();
    }
    
    private void loadSignaturePhotos() {
        final String nip = NIP.getText().trim();

        if (nip.equals("")) {
            setEmptyParaf();
            return;
        }

        final String parafUrl =
            "http://" +
            koneksiDB.HOSTHYBRIDWEB() +
            ":" +
            koneksiDB.PORTWEB() +
            "/webapps/verified/uploads/ttdpetugas/" +
            nip +
            ".png?t=" +
            System.currentTimeMillis();
        
        SwingUtilities.invokeLater(
            new Runnable() {
                @Override
                public void run() {
                    javax.swing.text.html.HTMLEditorKit parafHtmlKit =
                        new javax.swing.text.html.HTMLEditorKit();
                    LoadHTML.setEditorKit(parafHtmlKit);
                    LoadHTML.setDocument(parafHtmlKit.createDefaultDocument());
                    LoadHTML.setEditable(false);

                    String html =
                        "<html>" +
                        "<head></head>" +
                        "<body bgcolor='#ffffff'>" +
                        "<div align='center'>" +
                        "<img src='" + parafUrl + "' " +
                        "width='300' height='280'>" +
                        "</div>" +
                        "</body>" +
                        "</html>";

                    LoadHTML.setText(html);
                    LoadHTML.setCaretPosition(0);
                    LoadHTML.revalidate();
                    LoadHTML.repaint();
                }
            }
        );
    }
    
    private void setEmptyParaf() {
        SwingUtilities.invokeLater(
            new Runnable() {
                @Override
                public void run() {
                    javax.swing.text.html.HTMLEditorKit parafHtmlKit =
                        new javax.swing.text.html.HTMLEditorKit();
                    LoadHTML.setEditorKit(parafHtmlKit);
                    LoadHTML.setDocument(parafHtmlKit.createDefaultDocument());
                    LoadHTML.setEditable(false);

                    LoadHTML.setText(
                        "<html>" +
                        "<body bgcolor='#ffffff'>" +
                        "<div align='center'>" +
                        "<br><br><br>" +
                        "<font face='Tahoma' size='2' color='#434343'>" +
                        "Paraf belum tersedia" +
                        "</font>" +
                        "</div>" +
                        "</body>" +
                        "</html>"
                    );

                    LoadHTML.setCaretPosition(0);
                    LoadHTML.revalidate();
                    LoadHTML.repaint();
                }
            }
        );
    }
    
}
