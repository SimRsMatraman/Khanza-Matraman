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
import kepegawaian.DlgCariDokter;
import laporan.LaporanSisaDietPasien;
import setting.DlgCariJamDiet;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author dosen
 */
public class DlgCatatanPemberianObatRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private PreparedStatement ps,ps2;
    private ResultSet rs;
    private int i=0,pilih=0;
    private String status="";
    private RMCariJumlahObat cariobat=new RMCariJumlahObat(null,false);

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgCatatanPemberianObatRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "P","Nama Obat","Aturan Pakai"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataDiet.setModel(tabMode);
        tbDataDiet.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataDiet.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbDataDiet.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(400);
            }else if(i==2){
                column.setPreferredWidth(300);
            }
        }
        tbDataDiet.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2=new DefaultTableModel(null,new Object[]{
                "No.Rawat","Nama Pasien","Kamar","Tanggal","Jam","Obat","Jumlah","Waktu","Keterangan","Petugas","Kd Petugas"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataDiet1.setModel(tabMode2);
        tbDataDiet1.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataDiet1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbDataDiet1.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(240);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(750);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(270);
            }else if(i==9){
                column.setPreferredWidth(270);
            }else if(i==10){
                column.setPreferredWidth(90);
            }
        }
        tbDataDiet1.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode3=new DefaultTableModel(null,new Object[]{
                "No","Nama Diet","Jumlah Diet"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TNoRw.getText().length()>2){
                        tampil();
                        tampil2();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TNoRw.getText().length()>2){
                        tampil();
                        tampil2();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TNoRw.getText().length()>2){
                        tampil();
                        tampil2();
                    }
                }
            });
        } 
        
        ChkInput.setSelected(false);
        isForm();
        ChkJln.setSelected(true);
        jam();
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());   
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(dokter.getTable().getSelectedRow()!= -1){
                        if(TabRawat.getSelectedIndex()==0){
                            KdDok.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDok.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KdDok.requestFocus();
                        }                        
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
        
        cariobat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariobat.getTable().getSelectedRow()!= -1){
                    NmObat.append(cariobat.getTable().getValueAt(cariobat.getTable().getSelectedRow(),2).toString()+", ");
                    NmObat.requestFocus();
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
        
        cariobat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(tbDataDiet.getSelectedRow()!= -1){
                    NmObat.append(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),1).toString()+", ");
                    NmObat.requestFocus();
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
//        
//        bangsal.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(akses.getform().equals("DlgPemberianDiet")){
//                    if(bangsal.getTable().getSelectedRow()!= -1){                          
//                        NmBangsalCari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
//                        NmBangsalCari.requestFocus();                           
//                    }                         
//                }
//            }
//            @Override
//            public void windowIconified(WindowEvent e) {}
//            @Override
//            public void windowDeiconified(WindowEvent e) {}
//            @Override
//            public void windowActivated(WindowEvent e) {}
//            @Override
//            public void windowDeactivated(WindowEvent e) {}
//        });
//        
//        jamdiet.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(akses.getform().equals("DlgPemberianDiet")){
//                    if(jamdiet.getTable().getSelectedRow()!= -1){  
//                        if(pilih==1){
//                            Umur.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),0).toString());
//                            JamDiet.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),1).toString());
//                            BtnJam.requestFocus(); 
//                        }else if(pilih==2){
//                            Umur2.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),0).toString());
//                            JamDiet2.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),1).toString());
//                            BtnJam2.requestFocus(); 
//                        }                             
//                    }                         
//                }
//            }
//            @Override
//            public void windowIconified(WindowEvent e) {}
//            @Override
//            public void windowDeiconified(WindowEvent e) {}
//            @Override
//            public void windowActivated(WindowEvent e) {}
//            @Override
//            public void windowDeactivated(WindowEvent e) {}
//        });
    }
    
    
    private DlgCariJamDiet jamdiet=new DlgCariJamDiet(null,false);
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPermintaanAmbulance = new javax.swing.JMenuItem();
        Diagnosa = new widget.TextBox();
        RsTujuan = new widget.TextBox();
        jLabel16 = new widget.Label();
        NoKartu = new widget.TextBox();
        jLabel10 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        Penyakit = new widget.TextBox();
        jLabel15 = new widget.Label();
        scrollPane = new widget.ScrollPane();
        jLabel17 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel4 = new widget.Label();
        Alamat = new widget.TextArea();
        tampil_jam = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        Rujuk = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Kamar = new widget.TextBox();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel3 = new widget.Label();
        jLabel11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        ChkJln = new widget.CekBox();
        jLabel18 = new widget.Label();
        Waktu = new widget.ComboBox();
        jLabel20 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        Keterangan = new widget.TextArea();
        JmlObat = new widget.TextArea();
        BtnDokter16 = new widget.Button();
        scrollPane7 = new widget.ScrollPane();
        NmObat = new widget.TextArea();
        CmbDetik = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbJam = new widget.ComboBox();
        jLabel12 = new widget.Label();
        KdDok = new widget.TextBox();
        NmDok = new widget.TextBox();
        BtnDokter1 = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbDataDiet = new widget.Table();
        Scroll1 = new widget.ScrollPane();
        tbDataDiet1 = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPermintaanAmbulance.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanAmbulance.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanAmbulance.setForeground(java.awt.Color.darkGray);
        MnPermintaanAmbulance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanAmbulance.setText("Cetak Pernyataan Penggunaan Ambulance");
        MnPermintaanAmbulance.setName("MnPermintaanAmbulance"); // NOI18N
        MnPermintaanAmbulance.setPreferredSize(new java.awt.Dimension(450, 28));
        MnPermintaanAmbulance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanAmbulanceActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPermintaanAmbulance);

        Diagnosa.setEditable(false);
        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N

        RsTujuan.setHighlighter(null);
        RsTujuan.setName("RsTujuan"); // NOI18N
        RsTujuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RsTujuanKeyPressed(evt);
            }
        });

        jLabel16.setText("RS Rujukan :");
        jLabel16.setName("jLabel16"); // NOI18N

        NoKartu.setEditable(false);
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2024" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });

        Penyakit.setHighlighter(null);
        Penyakit.setName("Penyakit"); // NOI18N
        Penyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKeyPressed(evt);
            }
        });

        jLabel15.setText("No. Kartu :");
        jLabel15.setName("jLabel15"); // NOI18N

        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane.setName("scrollPane"); // NOI18N

        jLabel17.setText("Alamat :");
        jLabel17.setName("jLabel17"); // NOI18N

        jLabel14.setText("Diagnosa :");
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel9.setText("Petugas :");
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N

        Alamat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Alamat.setColumns(20);
        Alamat.setRows(5);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });

        tampil_jam.setFocusTraversalPolicyProvider(true);
        tampil_jam.setName("tampil_jam"); // NOI18N
        tampil_jam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampil_jamActionPerformed(evt);
            }
        });
        tampil_jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tampil_jamKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tampil_jamKeyTyped(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Pemberian Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 144));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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
        panelGlass8.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass10.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
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
        panelGlass10.add(BtnCari);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
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

        Rujuk.setBackground(new java.awt.Color(244, 244, 244));
        Rujuk.setName("Rujuk"); // NOI18N
        Rujuk.setPreferredSize(new java.awt.Dimension(160, 160));
        Rujuk.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        Rujuk.add(TNoRw);
        TNoRw.setBounds(93, 12, 130, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        Rujuk.add(TPasien);
        TPasien.setBounds(225, 12, 290, 23);

        Kamar.setEditable(false);
        Kamar.setHighlighter(null);
        Kamar.setName("Kamar"); // NOI18N
        Rujuk.add(Kamar);
        Kamar.setBounds(520, 10, 250, 23);

        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        Rujuk.add(KdPetugas);
        KdPetugas.setBounds(95, 40, 130, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setHighlighter(null);
        NmPetugas.setName("NmPetugas"); // NOI18N
        Rujuk.add(NmPetugas);
        NmPetugas.setBounds(232, 40, 240, 23);

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
        Rujuk.add(BtnDokter);
        BtnDokter.setBounds(478, 40, 30, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        Rujuk.add(jLabel3);
        jLabel3.setBounds(0, 12, 92, 23);

        jLabel11.setText("Petugas :");
        jLabel11.setName("jLabel11"); // NOI18N
        Rujuk.add(jLabel11);
        jLabel11.setBounds(0, 42, 92, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
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
        Rujuk.add(Tanggal);
        Tanggal.setBounds(610, 40, 90, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        Rujuk.add(ChkJln);
        ChkJln.setBounds(910, 40, 23, 23);

        jLabel18.setText("Tgl.Pencatatan :");
        jLabel18.setName("jLabel18"); // NOI18N
        Rujuk.add(jLabel18);
        jLabel18.setBounds(510, 40, 92, 23);

        Waktu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pagi", "Siang", "Sore", "Malam" }));
        Waktu.setName("Waktu"); // NOI18N
        Waktu.setPreferredSize(new java.awt.Dimension(62, 28));
        Waktu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuKeyPressed(evt);
            }
        });
        Rujuk.add(Waktu);
        Waktu.setBounds(100, 80, 80, 23);

        jLabel20.setText("Waktu :");
        jLabel20.setName("jLabel20"); // NOI18N
        Rujuk.add(jLabel20);
        jLabel20.setBounds(0, 80, 92, 20);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder("Keterangan"));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Keterangan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Keterangan.setColumns(20);
        Keterangan.setRows(5);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Keterangan);

        Rujuk.add(scrollPane6);
        scrollPane6.setBounds(670, 110, 250, 50);

        JmlObat.setBorder(javax.swing.BorderFactory.createTitledBorder("Jumlah"));
        JmlObat.setColumns(20);
        JmlObat.setRows(5);
        JmlObat.setName("JmlObat"); // NOI18N
        JmlObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JmlObatKeyPressed(evt);
            }
        });
        Rujuk.add(JmlObat);
        JmlObat.setBounds(670, 70, 80, 40);

        BtnDokter16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter16.setMnemonic('2');
        BtnDokter16.setToolTipText("Alt+2");
        BtnDokter16.setName("BtnDokter16"); // NOI18N
        BtnDokter16.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter16ActionPerformed(evt);
            }
        });
        Rujuk.add(BtnDokter16);
        BtnDokter16.setBounds(630, 100, 28, 30);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        NmObat.setBorder(javax.swing.BorderFactory.createTitledBorder("Obat Yang Diberikan"));
        NmObat.setColumns(20);
        NmObat.setRows(5);
        NmObat.setName("NmObat"); // NOI18N
        NmObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmObatKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(NmObat);

        Rujuk.add(scrollPane7);
        scrollPane7.setBounds(191, 80, 430, 70);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        Rujuk.add(CmbDetik);
        CmbDetik.setBounds(840, 40, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        Rujuk.add(CmbMenit);
        CmbMenit.setBounds(770, 40, 62, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbJamActionPerformed(evt);
            }
        });
        Rujuk.add(CmbJam);
        CmbJam.setBounds(710, 40, 62, 23);

        jLabel12.setText("DPJP :");
        jLabel12.setName("jLabel12"); // NOI18N
        Rujuk.add(jLabel12);
        jLabel12.setBounds(770, 10, 80, 23);

        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        Rujuk.add(KdDok);
        KdDok.setBounds(860, 10, 130, 23);

        NmDok.setEditable(false);
        NmDok.setHighlighter(null);
        NmDok.setName("NmDok"); // NOI18N
        Rujuk.add(NmDok);
        NmDok.setBounds(1000, 10, 240, 23);

        BtnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter1.setMnemonic('2');
        BtnDokter1.setToolTipText("Alt+2");
        BtnDokter1.setName("BtnDokter1"); // NOI18N
        BtnDokter1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter1ActionPerformed(evt);
            }
        });
        BtnDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter1KeyPressed(evt);
            }
        });
        Rujuk.add(BtnDokter1);
        BtnDokter1.setBounds(1240, 10, 30, 23);

        PanelInput.add(Rujuk, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbDataDiet.setAutoCreateRowSorter(true);
        tbDataDiet.setComponentPopupMenu(jPopupMenu1);
        tbDataDiet.setName("tbDataDiet"); // NOI18N
        tbDataDiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataDietMouseClicked(evt);
            }
        });
        tbDataDiet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataDietKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbDataDiet);

        TabRawat.addTab("Data Resep", Scroll);

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbDataDiet1.setAutoCreateRowSorter(true);
        tbDataDiet1.setComponentPopupMenu(jPopupMenu1);
        tbDataDiet1.setName("tbDataDiet1"); // NOI18N
        tbDataDiet1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataDiet1MouseClicked(evt);
            }
        });
        tbDataDiet1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataDiet1KeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbDataDiet1);

        TabRawat.addTab("Data Catatan Pemberian Obat", Scroll1);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed

}//GEN-LAST:event_TNoRwKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        
}//GEN-LAST:event_DTPTglKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
                Valid.textKosong(TNoRw,"pasien");
            }else if(NmPetugas.getText().trim().equals("")){
                Valid.textKosong(KdPetugas,"Petugas");
            }else{
                if(Sequel.menyimpantf("catatan_pemberian_obat","'"+TNoRw.getText()+"','"+Kamar.getText()+"','"+
                        Valid.SetTgl(Tanggal.getSelectedItem()+"")+"','"+
                        CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem()+"','"+
//                        tampil_jam.getText()+"','"+
                        NmObat.getText()+"','"+
                        JmlObat.getText()+"','"+
                        Waktu.getSelectedItem()+"','"+
                        Keterangan.getText()+"','"+
                        KdPetugas.getText()+"','"+
                        "'","data")==true){
                    tabMode2.addRow(new String[]{
//                        TNoRw.getText(),TPasien.getText(),Ruang.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),Penyakit.getText(),NmPetugas.getText(),"-",Kamar.getText(),KdPetugas.getText()
                    });
                    LCount.setText(""+tabMode2.getRowCount());
                    emptTeks();
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan buka data resep pasien");
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
//        if(TabRawat.getSelectedIndex()==0){
//            if(tabMode2.getRowCount()==0){
//                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
//                DTPTgl.requestFocus();
//            }else if(TPasien.getText().trim().equals("")){
//                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
//            }else if(!(TPasien.getText().trim().equals(""))){
//                if(tbDataDiet1.getSelectedRow()!= -1){
//                    if(Sequel.queryutf("delete from catatan_pemberian_obat " +
//                            "where no_rawat='"+TNoRw.getText()+"' " +
//                            "and kd_kamar='"+Kamar.getText()+"' " +
//                            "and tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' " +
//                            "and jam='"+tampil_jam.getText()+"' " +
//                            "and obat='"+NmObat.getText()+"' " +
//                            "and jml='"+JmlObat.getText()+"' " +
//                            "and waktu='"+Waktu.getSelectedItem()+"' " +
//                            "and keterangan='"+Keterangan.getText()+"' " +
//                            "and nip='"+KdPetugas.getText()+"'")==true){
//                        tabMode2.removeRow(tbDataDiet1.getSelectedRow());
//                        LCount.setText(""+tabMode2.getRowCount());
//                    }
//                }
//            }
//        }else{
//            JOptionPane.showMessageDialog(null,"Silahkan buka data");
//        }
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            switch (TabRawat.getSelectedIndex()) {
            case 1:
            if(tabMode2.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    DTPTgl.requestFocus();
                }else if(TPasien.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
                }else if(!(TPasien.getText().trim().equals(""))){
                    if(tbDataDiet1.getSelectedRow()!= -1){
                        if(Sequel.queryutf("delete from catatan_pemberian_obat " +
                            "where no_rawat='"+TNoRw.getText()+"' " +
                            "and kd_kamar='"+Kamar.getText()+"' " +
                            "and tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' " +
                            "and jam='"+tampil_jam.getText()+"' " +
                            "and obat='"+NmObat.getText()+"' " +
                            "and jml='"+JmlObat.getText()+"' " +
                            "and waktu='"+Waktu.getSelectedItem()+"' " +
                            "and keterangan='"+Keterangan.getText()+"' " +
                            "and nip='"+KdPetugas.getText()+"'")==true){
                            tabMode2.removeRow(tbDataDiet1.getSelectedRow());
                            LCount.setText(""+tabMode2.getRowCount());
                            emptTeks();
                        }
                    }
                }break;
                    default:
                break;
            }
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

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
//       if(TabRawat.getSelectedIndex()==0){
//            tampil();
//            tampil2();
//        }else if(TabRawat.getSelectedIndex()==1){
//        }
       switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampil();
                break;
            case 1:
                tampil2();
                break;
            default:
                break;
        }
       if(TabRawat.getSelectedIndex()==1){
        }
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
        KdPetugas.setText("");
        NmPetugas.setText("");
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampil();
                break;
            case 1:
                tampil2();
                break;
            default:
                break;
        }
       if(TabRawat.getSelectedIndex()==1){
        }
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
//            tampil();
//            tampil2();
//            TCari.setText("");
//        }else{
//            Valid.pindah(evt, BtnCari, TPasien);
//        }
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampil();
                break;
            case 1:
                tampil2();
                break;
            default:
                break;
        }
       if(TabRawat.getSelectedIndex()==1){
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbDataDietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataDietMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDataDietMouseClicked

    private void tbDataDietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataDietKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDataDietKeyPressed

private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select diet.nama_diet from diet where diet.kd_diet=? ",NmPetugas,KdPetugas.getText());
        }else{

        }
}//GEN-LAST:event_KdPetugasKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void MnPermintaanAmbulanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanAmbulanceActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPernyataanAmbulance.jasper","report","::[ Pernyataan Penggunaan Ambulance ]::",
                "SELECT a.no_rawat, c.no_rkm_medis, c.nm_pasien, c.umur, a.no_peserta, concat(c.alamat, ', ', e.nm_kel, ', ', f.nm_kec, ', ', g.nm_kab, ' - ', h.nm_prop) as alamat, a.diagnosa, a.tanggal, a.rstujuan, a.kd_kamar, a.nip, j.nama " +
                "FROM detail_permintaan_ambulance a "+
                "INNER JOIN reg_periksa b on b.no_rawat=a.no_rawat "+
                "LEFT JOIN pasien c on c.no_rkm_medis=b.no_rkm_medis " +
                "LEFT JOIN kamar_inap d on d.no_rawat=a.no_rawat "+
                "LEFT JOIN kelurahan e on e.kd_kel=c.kd_kel "+
                "LEFT JOIN kecamatan f on f.kd_kec=c.kd_kec "+
                "LEFT JOIN kabupaten g on g.kd_kab=c.kd_kab  "+
                "LEFT JOIN propinsi h on h.kd_prop=c.kd_prop "+
                "LEFT JOIN kamar_inap i on i.no_rawat=a.no_rawat "+
                "LEFT JOIN petugas j on j.nip=a.nip "+
                "where a.tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' "+
                "and a.no_rawat='"+TNoRw.getText()+"' and a.no_peserta='"+NoKartu.getText()+"' and a.rstujuan='"+RsTujuan.getText()+"'",param);
        }
    }//GEN-LAST:event_MnPermintaanAmbulanceActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampil();
                break;
            case 1:
                tampil2();
                break;
            default:
                break;
        }
       if(TabRawat.getSelectedIndex()==1){
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed

    }//GEN-LAST:event_AlamatKeyPressed

    private void RsTujuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RsTujuanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RsTujuanKeyPressed

    private void PenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed

    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        try {
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalItemStateChanged

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed

    }//GEN-LAST:event_TanggalKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void WaktuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuKeyPressed

    }//GEN-LAST:event_WaktuKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKeyPressed

    private void JmlObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JmlObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JmlObatKeyPressed

    private void BtnDokter16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter16ActionPerformed
        if(TNoRw.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            cariobat.setNoRawat(TNoRw.getText());
            cariobat.tampil();
            cariobat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobat.setLocationRelativeTo(internalFrame1);
            cariobat.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter16ActionPerformed

    private void NmObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmObatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
//                Keadaan.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
//            KodeDiagnosaUtama.requestFocus();
        }
    }//GEN-LAST:event_NmObatKeyPressed

    private void tbDataDiet1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataDiet1MouseClicked
        if(tabMode2.getRowCount()!=0){
            try {
                getData();
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDataDiet1MouseClicked

    private void tbDataDiet1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataDiet1KeyPressed
        if(tabMode2.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDataDiet1KeyPressed

    private void tampil_jamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampil_jamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tampil_jamActionPerformed

    private void tampil_jamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tampil_jamKeyReleased

    }//GEN-LAST:event_tampil_jamKeyReleased

    private void tampil_jamKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tampil_jamKeyTyped
        char kata= evt.getKeyChar();
        if(!((kata>='0') && (kata<='9') || (kata== KeyEvent.VK_BACK_SPACE))){
            JOptionPane.showMessageDialog(null,"Hanya diperbolehkan menginputkan Angka");
        }
    }//GEN-LAST:event_tampil_jamKeyTyped

    private void CmbJamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbJamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJamActionPerformed

    private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokKeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        akses.setform("DlgRawatJalan");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCatatanPemberianObatRalan dialog = new DlgCatatanPemberianObatRalan(new javax.swing.JFrame(), true);
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
    private widget.TextArea Alamat;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter16;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.TextBox Diagnosa;
    private widget.TextArea JmlObat;
    private widget.TextBox Kamar;
    private widget.TextBox KdDok;
    private widget.TextBox KdPetugas;
    private widget.TextArea Keterangan;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnPermintaanAmbulance;
    private widget.TextBox NmDok;
    private widget.TextArea NmObat;
    private widget.TextBox NmPetugas;
    private widget.TextBox NoKartu;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Penyakit;
    private widget.TextBox RsTujuan;
    private widget.PanelBiasa Rujuk;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.ComboBox Waktu;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollPane;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.TextBox tampil_jam;
    private widget.Table tbDataDiet;
    private widget.Table tbDataDiet1;
    // End of variables declaration//GEN-END:variables

    public void tampil() { 
        Valid.tabelKosong(tabMode);  
        try{
            ps=koneksi.prepareStatement(
                "SELECT c.nama_brng,b.no_rawat, b.aturan, b.kode_brng, b.tgl_perawatan, " +
                "CONCAT(c.nama_brng,' - ',b.aturan) as obat FROM reg_periksa a " +
                "INNER JOIN aturan_pakai b on b.no_rawat=a.no_rawat " +
                "INNER JOIN databarang c on c.kode_brng=b.kode_brng " +
                "WHERE  b.no_rawat like ?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("obat"),rs.getString("aturan"),rs.getString("kode_brng")
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
    
    public void tampil2() {   
        try{
            Valid.tabelKosong(tabMode2);
            ps=koneksi.prepareStatement(
                "SELECT a.no_rawat, c.nm_pasien, b.kd_kamar, b.tanggal, b.jam, b.obat, b.jml,b.waktu, d.nama, b.nip, b.keterangan "+
                "FROM reg_periksa a "+
                "INNER JOIN catatan_pemberian_obat b on b.no_rawat=a.no_rawat "+
                "INNER JOIN pasien c on c.no_rkm_medis=a.no_rkm_medis "+
                "INNER JOIN petugas d on d.nip=b.nip "+
                "WHERE b.no_rawat like ? "+
//                "WHERE b.tanggal between ? and ? "+
//                (TCari.getText().trim().equals("")?"":"and (b.no_rawat like ? or d.nama like ? or c.nm_pasien like ?) ")+
//                "order by b.tanggal,b.waktu"+
                "");
            try {
                ps.setString(1,TNoRw.getText());
//                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
//                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
//                if(!TCari.getText().trim().equals("")){
//                    ps.setString(3,"%"+TCari.getText().trim()+"%");
//                    ps.setString(4,"%"+TCari.getText().trim()+"%");
//                    ps.setString(5,"%"+TCari.getText().trim()+"%");
//                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode2.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("nm_pasien"),rs.getString("kd_kamar"),rs.getString("tanggal"),rs.getString("jam"),
                        rs.getString("obat"),rs.getString("jml"),rs.getString("waktu"),rs.getString("keterangan"),rs.getString("nama"),rs.getString("nip")
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
            LCount.setText(""+tabMode2.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }


    public void emptTeks() {
        NmObat.setText("");
        JmlObat.setText("1");
        Keterangan.setText("-");
        DTPTgl.setDate(new Date());
        Tanggal.setDate(new Date());
    }

    private void getData() {
//        if(tbDataDiet.getSelectedRow()!= -1){
//            NmObat.append(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),1).toString()+", ");
//        }
        switch (TabRawat.getSelectedIndex()) {
            case 0:
            if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    DTPTgl.requestFocus();
                }else if(!(TPasien.getText().trim().equals(""))){
                    if(tbDataDiet.getSelectedRow()!= -1){
                        NmObat.append(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),1).toString()+", ");
                    }
                }
                break;
            
            case 1:
            if(tabMode2.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    DTPTgl.requestFocus();
                }else if(!(TPasien.getText().trim().equals(""))){
                    if(tbDataDiet1.getSelectedRow()!= -1){
                    TNoRw.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),0).toString());
                    TPasien.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),1).toString());    
                    Kamar.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),2).toString());
                    tampil_jam.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),4).toString());
                    NmObat.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),5).toString()); 
                    JmlObat.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),6).toString());
                    Waktu.setSelectedItem(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),7).toString());
                    NmPetugas.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),9).toString());
                    KdPetugas.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),10).toString());
                    Keterangan.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),8).toString());
                    Valid.SetTgl(Tanggal,tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),3).toString());
                    }
                }
            break;
                    default:
                break;
            }
    }
    
        private void getData2() {
//        if(tbDataDiet1.getSelectedRow()!= -1){
//            TNoRw.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),0).toString());
//            TPasien.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),1).toString());    
//            Kamar.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),2).toString());
//            tampil_jam.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),4).toString());
//            NmObat.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),5).toString()); 
//            JmlObat.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),6).toString());
//            Waktu.setSelectedItem(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),7).toString());
//            NmPetugas.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),9).toString());
//            KdPetugas.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),10).toString());
//            Keterangan.setText(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),8).toString());
//            Valid.SetTgl(Tanggal,tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),3).toString());
//        }
    }
        
//        private void getData3() {
//        if(tbDataDiet1.getSelectedRow()!= -1 && tbDataDiet.getSelectedRow()!= -1){
//            NmObat.append(tbDataDiet1.getValueAt(tbDataDiet1.getSelectedRow(),5).toString()+", "+tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),1).toString()+", "); 
//        }
//    }
    
    private void isRawat() {
        Sequel.cariIsi("select pasien.nm_pasien from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
//        Sequel.cariIsi("select bangsal.nm_bangsal from kamar_inap inner join kamar on kamar.kd_kamar=kamar_inap.kd_kamar inner join bangsal on bangsal.kd_bangsal=kamar.kd_bangsal where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1",Kamar,TNoRw.getText());
        Sequel.cariIsi("select kamar_inap.kd_kamar from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1",Kamar,TNoRw.getText());
    }
    
    public void setNoRm(String norwt,String posisi){
        TNoRw.setText(norwt);
        Sequel.cariIsi("select reg_periksa.no_rawat from reg_periksa where reg_periksa.no_rawat=? ",TCari,TNoRw.getText());
        this.status=posisi;        
        isRawat();
        isPsien();
        KdDok.setText(Sequel.cariIsi("select b.kd_dokter from kamar_inap a inner join dpjp_ranap b on b.no_rawat=a.no_rawat where a.no_rawat=?",norwt));
        NmDok.setText(dokter.tampil3(KdDok.getText()));
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,180));      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
//        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_anak());
//        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_anak());
//        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ranap());
//        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ranap());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", NmPetugas,KdPetugas.getText());
        }
    }
    
    private void isPsien(){
        try {
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rkm_medis,reg_periksa.kd_pj,reg_periksa.kd_dokter,dokter.nm_dokter,pasien.nm_pasien,pasien.jk,pasien.umur,"+
                "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                while(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    Alamat.setText(rs.getString("alamat"));
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
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            private int all;
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
                    all = now.getHours()+now.getMinutes()+now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMenit.getSelectedIndex();
                    nilai_detik =CmbDetik.getSelectedIndex();
//                    all =tampil_jam.setText();
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
//                tampil_jam.setText(jam+":"+menit+":"+detik);
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    public void setTampil(){
       switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampil();
                break;
            case 1:
                tampil2();
                break;
            default:
                break;
        }
       if(TabRawat.getSelectedIndex()==1){
        }
    }
}
