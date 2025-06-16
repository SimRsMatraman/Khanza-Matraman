/*
 * by MAs ElKhanza
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPerencanaanPemulangan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private RMCariJumlahObat cariobat=new RMCariJumlahObat(null,false);
    private StringBuilder htmlContent;
    private String pilihan="",namaPenyakit="",Listpenyakit="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPerencanaanPemulangan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Masuk Dirawat","Rencana Pulang","Diagnosa Medis","Diagnosa Akhir","Alasan Masuk / Dirawat","Kondisi",
            "Mobilisasi","Oksigen","Infus","NGT","Kateter","Drain","Tidak Ada","Lainnya","Hand Hyigyne","Evakuasi Kebakaran","Penggunaan APAR",
            "BHD","Pew. NGT","Pew. Kateter","Pew. Infus","Pew. Oksigen","Penyuluhan Diet","Pew. Luka","Pmb. Obat","Penyuluhan Lain","RO",
            "Ket. RO","CTScan","Ket. CTScan","USG","Ket. USG","EKG","LAB","Ket. LAB","Dok. Lain","Surat Sakit",
            "Surat Rawat","Lepas Rawat","Obat Pulang","Diet","Intruksi","Pasien/Keluarga","NIP","Nama Petugas"
        }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 50; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(87);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(180);
            }else if(i==10){
                column.setPreferredWidth(160);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(80);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(80);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setPreferredWidth(80);
            }else if(i==20){
                column.setPreferredWidth(80);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(80);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(80);
            }else if(i==25){
                column.setPreferredWidth(80);
            }else if(i==26){
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(80);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(80);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(80);
            }else if(i==33){
                column.setPreferredWidth(80);
            }else if(i==34){
                column.setPreferredWidth(80);
            }else if(i==35){
                column.setPreferredWidth(80);
            }else if(i==36){
                column.setPreferredWidth(80);
            }else if(i==37){
                column.setPreferredWidth(80);
            }else if(i==38){
                column.setPreferredWidth(80);
            }else if(i==39){
                column.setPreferredWidth(80);
            }else if(i==40){
                column.setPreferredWidth(80);
            }else if(i==41){
                column.setPreferredWidth(80);
            }else if(i==42){
                column.setPreferredWidth(80);
            }else if(i==43){
                column.setPreferredWidth(80);
            }else if(i==44){
                column.setPreferredWidth(300);
            }else if(i==45){
                column.setPreferredWidth(80);
            }else if(i==46){
                column.setPreferredWidth(100);
            }else if(i==47){
                column.setPreferredWidth(100);
            }else if(i==48){
                column.setPreferredWidth(100);
            }else if(i==49){
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        DiagnosaMedis.setDocument(new batasInput((int)50).getKata(DiagnosaMedis));
        AlasanMasuk.setDocument(new batasInput((int)150).getKata(AlasanMasuk));
        PenyuluhanLainnya.setDocument(new batasInput((int)100).getKata(PenyuluhanLainnya));
        SaksiKeluarga.setDocument(new batasInput((int)50).getKata(SaksiKeluarga));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        ChkAccor.setSelected(false);
        isPhoto();
        
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
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    KdPetugas.requestFocus();
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
                    Obat2an.append(cariobat.getTable().getValueAt(cariobat.getTable().getSelectedRow(),2).toString()+", ");
                    Obat2an.requestFocus();
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtnPrint = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        RencanaPemulangan = new widget.Tanggal();
        jSeparator1 = new javax.swing.JSeparator();
        PenyuluhanLainnya = new widget.TextBox();
        jLabel101 = new widget.Label();
        label15 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel42 = new widget.Label();
        SaksiKeluarga = new widget.TextBox();
        MasukDirawat = new widget.TextBox();
        label12 = new widget.Label();
        jLabel40 = new widget.Label();
        DiagnosaMedis = new widget.TextBox();
        jLabel41 = new widget.Label();
        AlasanMasuk = new widget.TextBox();
        DiagnosaMedis1 = new widget.TextBox();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        KondisiPulang = new widget.ComboBox();
        jLabel49 = new widget.Label();
        Mobilisasi = new widget.ComboBox();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel50 = new widget.Label();
        jLabel102 = new widget.Label();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        jLabel105 = new widget.Label();
        Oksigen = new widget.CekBox();
        Infus = new widget.CekBox();
        NGT = new widget.CekBox();
        Kateter = new widget.CekBox();
        Drain = new widget.CekBox();
        TidakAda = new widget.CekBox();
        AlatLainnya = new widget.TextBox();
        jLabel106 = new widget.Label();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        jSeparator15 = new javax.swing.JSeparator();
        HandHyigine = new widget.CekBox();
        EvakuasiKebakaran = new widget.CekBox();
        PenggunaanAPAR = new widget.CekBox();
        BantuanHidupDasar = new widget.CekBox();
        PerawatanNGT = new widget.CekBox();
        PerawatanKateter = new widget.CekBox();
        PerawatanInfus = new widget.CekBox();
        PerawatanOksigen = new widget.CekBox();
        PengaturanDiet = new widget.CekBox();
        PerawatanLuka = new widget.CekBox();
        PemberianObat = new widget.CekBox();
        jLabel109 = new widget.Label();
        jLabel110 = new widget.Label();
        jLabel111 = new widget.Label();
        jLabel112 = new widget.Label();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        jLabel115 = new widget.Label();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        jLabel119 = new widget.Label();
        jLabel120 = new widget.Label();
        jLabel121 = new widget.Label();
        jSeparator16 = new javax.swing.JSeparator();
        Ro = new widget.CekBox();
        KeteranganRo = new widget.TextBox();
        CTScan = new widget.CekBox();
        KeteranganCTScan = new widget.TextBox();
        USG = new widget.CekBox();
        KeteranganUSG = new widget.TextBox();
        EKG = new widget.CekBox();
        Lab = new widget.CekBox();
        KeteranganLab = new widget.TextBox();
        KeteranganLainnya = new widget.TextBox();
        SuratSakit = new widget.CekBox();
        SuratRawat = new widget.CekBox();
        SuratLepasRawat = new widget.CekBox();
        jLabel51 = new widget.Label();
        jLabel122 = new widget.Label();
        jLabel123 = new widget.Label();
        jLabel124 = new widget.Label();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        jLabel127 = new widget.Label();
        jLabel128 = new widget.Label();
        jLabel129 = new widget.Label();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel52 = new widget.Label();
        BtnObat = new widget.Button();
        scrollPane6 = new widget.ScrollPane();
        Obat2an = new widget.TextArea();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel65 = new widget.Label();
        jLabel131 = new widget.Label();
        Diet = new widget.TextBox();
        Intruksi = new widget.TextBox();
        jSeparator19 = new javax.swing.JSeparator();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Perencanaan Pemulangan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 913));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 300, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(610, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(670, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(800, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setText("Masuk Dirawat :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(0, 40, 94, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(770, 10, 30, 23);

        RencanaPemulangan.setForeground(new java.awt.Color(50, 70, 50));
        RencanaPemulangan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-06-2025" }));
        RencanaPemulangan.setDisplayFormat("dd-MM-yyyy");
        RencanaPemulangan.setName("RencanaPemulangan"); // NOI18N
        RencanaPemulangan.setOpaque(false);
        RencanaPemulangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaPemulanganKeyPressed(evt);
            }
        });
        FormInput.add(RencanaPemulangan);
        RencanaPemulangan.setBounds(364, 40, 190, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 880, 1);

        PenyuluhanLainnya.setHighlighter(null);
        PenyuluhanLainnya.setName("PenyuluhanLainnya"); // NOI18N
        PenyuluhanLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyuluhanLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(PenyuluhanLainnya);
        PenyuluhanLainnya.setBounds(680, 320, 260, 23);

        jLabel101.setText("Intruksi Tindak Lanjut :");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(20, 730, 120, 23);

        label15.setText("Perawat/Petugas :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(0, 810, 130, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(140, 810, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(240, 810, 193, 23);

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
        BtnDokter.setBounds(440, 810, 28, 23);

        jLabel42.setText("Pasien/Keluarga :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(460, 810, 110, 23);

        SaksiKeluarga.setHighlighter(null);
        SaksiKeluarga.setName("SaksiKeluarga"); // NOI18N
        SaksiKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaksiKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(SaksiKeluarga);
        SaksiKeluarga.setBounds(580, 810, 280, 23);

        MasukDirawat.setEditable(false);
        MasukDirawat.setHighlighter(null);
        MasukDirawat.setName("MasukDirawat"); // NOI18N
        FormInput.add(MasukDirawat);
        MasukDirawat.setBounds(98, 40, 131, 23);

        label12.setText("Rencana Pemulangan :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(240, 40, 120, 23);

        jLabel40.setText("Diagnosa Medis :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(570, 40, 100, 23);

        DiagnosaMedis.setHighlighter(null);
        DiagnosaMedis.setName("DiagnosaMedis"); // NOI18N
        DiagnosaMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaMedisKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaMedis);
        DiagnosaMedis.setBounds(670, 40, 297, 23);

        jLabel41.setText("Alasan Masuk / Dirawat :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 70, 137, 23);

        AlasanMasuk.setHighlighter(null);
        AlasanMasuk.setName("AlasanMasuk"); // NOI18N
        AlasanMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanMasukKeyPressed(evt);
            }
        });
        FormInput.add(AlasanMasuk);
        AlasanMasuk.setBounds(141, 70, 420, 23);

        DiagnosaMedis1.setHighlighter(null);
        DiagnosaMedis1.setName("DiagnosaMedis1"); // NOI18N
        DiagnosaMedis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaMedis1KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaMedis1);
        DiagnosaMedis1.setBounds(670, 70, 297, 23);

        jLabel46.setText("Diagnosa Keluar :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(570, 70, 100, 20);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("KONDISI PULANG");
        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(30, 110, 220, 20);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("PENYULUHAN KESEHATAN YANG TELAH DIBERIKAN");
        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(30, 230, 410, 30);

        KondisiPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sembuh", "Pulang APS", "Meninggal" }));
        KondisiPulang.setName("KondisiPulang"); // NOI18N
        KondisiPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiPulangKeyPressed(evt);
            }
        });
        FormInput.add(KondisiPulang);
        KondisiPulang.setBounds(30, 130, 130, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("MOBILISASI");
        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(30, 160, 210, 20);

        Mobilisasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jalan", "Tongkat", "Kursi Roda", "Brankar", "Digendong" }));
        Mobilisasi.setName("Mobilisasi"); // NOI18N
        Mobilisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MobilisasiKeyPressed(evt);
            }
        });
        FormInput.add(Mobilisasi);
        Mobilisasi.setBounds(30, 180, 130, 23);

        jSeparator14.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(10, 101, 240, 120);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("ALAT YANG TERPASANG SAAT PASIEN PULANG");
        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(280, 110, 340, 20);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("Lain - lain : ");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(480, 180, 140, 20);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("Oksigen");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(310, 130, 110, 20);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Infus");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(310, 150, 110, 20);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("NGT");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(310, 170, 110, 20);

        Oksigen.setBorder(null);
        Oksigen.setToolTipText("");
        Oksigen.setActionCommand("Oksigen");
        Oksigen.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Oksigen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Oksigen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Oksigen.setName("Oksigen"); // NOI18N
        Oksigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OksigenActionPerformed(evt);
            }
        });
        FormInput.add(Oksigen);
        Oksigen.setBounds(280, 130, 23, 23);

        Infus.setBorder(null);
        Infus.setActionCommand("Infus");
        Infus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Infus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Infus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Infus.setName("Infus"); // NOI18N
        Infus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InfusActionPerformed(evt);
            }
        });
        FormInput.add(Infus);
        Infus.setBounds(280, 150, 23, 23);

        NGT.setBorder(null);
        NGT.setActionCommand("NGT");
        NGT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        NGT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NGT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        NGT.setName("NGT"); // NOI18N
        NGT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NGTActionPerformed(evt);
            }
        });
        FormInput.add(NGT);
        NGT.setBounds(280, 170, 23, 23);

        Kateter.setBorder(null);
        Kateter.setActionCommand("Kateter");
        Kateter.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Kateter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Kateter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Kateter.setName("Kateter"); // NOI18N
        Kateter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KateterActionPerformed(evt);
            }
        });
        FormInput.add(Kateter);
        Kateter.setBounds(280, 190, 23, 23);

        Drain.setBorder(null);
        Drain.setActionCommand("Drain");
        Drain.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Drain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Drain.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Drain.setName("Drain"); // NOI18N
        Drain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DrainActionPerformed(evt);
            }
        });
        FormInput.add(Drain);
        Drain.setBounds(470, 130, 23, 23);

        TidakAda.setBorder(null);
        TidakAda.setActionCommand("Tidak Ada");
        TidakAda.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        TidakAda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TidakAda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TidakAda.setName("TidakAda"); // NOI18N
        TidakAda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TidakAdaActionPerformed(evt);
            }
        });
        FormInput.add(TidakAda);
        TidakAda.setBounds(470, 150, 23, 23);

        AlatLainnya.setHighlighter(null);
        AlatLainnya.setName("AlatLainnya"); // NOI18N
        AlatLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(AlatLainnya);
        AlatLainnya.setBounds(540, 180, 340, 23);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("Kateter");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(310, 190, 110, 20);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("Drain");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(500, 130, 140, 20);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Tidak Ada");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(500, 150, 140, 20);

        jSeparator15.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(260, 101, 690, 120);

        HandHyigine.setBorder(null);
        HandHyigine.setActionCommand("Hand Hyigiene");
        HandHyigine.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        HandHyigine.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HandHyigine.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        HandHyigine.setName("HandHyigine"); // NOI18N
        HandHyigine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HandHyigineActionPerformed(evt);
            }
        });
        FormInput.add(HandHyigine);
        HandHyigine.setBounds(30, 260, 23, 23);

        EvakuasiKebakaran.setBorder(null);
        EvakuasiKebakaran.setActionCommand("Evakuasi Kebakaran");
        EvakuasiKebakaran.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        EvakuasiKebakaran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EvakuasiKebakaran.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EvakuasiKebakaran.setName("EvakuasiKebakaran"); // NOI18N
        EvakuasiKebakaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EvakuasiKebakaranActionPerformed(evt);
            }
        });
        FormInput.add(EvakuasiKebakaran);
        EvakuasiKebakaran.setBounds(30, 280, 23, 23);

        PenggunaanAPAR.setBorder(null);
        PenggunaanAPAR.setActionCommand("Penggunaan APAR");
        PenggunaanAPAR.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PenggunaanAPAR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PenggunaanAPAR.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PenggunaanAPAR.setName("PenggunaanAPAR"); // NOI18N
        PenggunaanAPAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PenggunaanAPARActionPerformed(evt);
            }
        });
        FormInput.add(PenggunaanAPAR);
        PenggunaanAPAR.setBounds(30, 300, 23, 23);

        BantuanHidupDasar.setBorder(null);
        BantuanHidupDasar.setActionCommand("Bantuan Hidup Dasar");
        BantuanHidupDasar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BantuanHidupDasar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BantuanHidupDasar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BantuanHidupDasar.setName("BantuanHidupDasar"); // NOI18N
        BantuanHidupDasar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BantuanHidupDasarActionPerformed(evt);
            }
        });
        FormInput.add(BantuanHidupDasar);
        BantuanHidupDasar.setBounds(30, 320, 23, 23);

        PerawatanNGT.setBorder(null);
        PerawatanNGT.setActionCommand("Perawatan NGT");
        PerawatanNGT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PerawatanNGT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PerawatanNGT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PerawatanNGT.setName("PerawatanNGT"); // NOI18N
        PerawatanNGT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerawatanNGTActionPerformed(evt);
            }
        });
        FormInput.add(PerawatanNGT);
        PerawatanNGT.setBounds(310, 260, 23, 23);

        PerawatanKateter.setBorder(null);
        PerawatanKateter.setActionCommand("Perawatan Kateter");
        PerawatanKateter.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PerawatanKateter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PerawatanKateter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PerawatanKateter.setName("PerawatanKateter"); // NOI18N
        PerawatanKateter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerawatanKateterActionPerformed(evt);
            }
        });
        FormInput.add(PerawatanKateter);
        PerawatanKateter.setBounds(310, 280, 23, 23);

        PerawatanInfus.setBorder(null);
        PerawatanInfus.setActionCommand("Perawatan Infus");
        PerawatanInfus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PerawatanInfus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PerawatanInfus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PerawatanInfus.setName("PerawatanInfus"); // NOI18N
        PerawatanInfus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerawatanInfusActionPerformed(evt);
            }
        });
        FormInput.add(PerawatanInfus);
        PerawatanInfus.setBounds(310, 300, 23, 23);

        PerawatanOksigen.setBorder(null);
        PerawatanOksigen.setActionCommand("Perawatan Oksigen");
        PerawatanOksigen.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PerawatanOksigen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PerawatanOksigen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PerawatanOksigen.setName("PerawatanOksigen"); // NOI18N
        PerawatanOksigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerawatanOksigenActionPerformed(evt);
            }
        });
        FormInput.add(PerawatanOksigen);
        PerawatanOksigen.setBounds(310, 320, 23, 23);

        PengaturanDiet.setBorder(null);
        PengaturanDiet.setActionCommand("Pengaturan Diet");
        PengaturanDiet.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PengaturanDiet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PengaturanDiet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PengaturanDiet.setName("PengaturanDiet"); // NOI18N
        PengaturanDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PengaturanDietActionPerformed(evt);
            }
        });
        FormInput.add(PengaturanDiet);
        PengaturanDiet.setBounds(610, 260, 23, 23);

        PerawatanLuka.setBorder(null);
        PerawatanLuka.setActionCommand("Perawatan Luka");
        PerawatanLuka.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PerawatanLuka.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PerawatanLuka.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PerawatanLuka.setName("PerawatanLuka"); // NOI18N
        PerawatanLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerawatanLukaActionPerformed(evt);
            }
        });
        FormInput.add(PerawatanLuka);
        PerawatanLuka.setBounds(610, 280, 23, 23);

        PemberianObat.setBorder(null);
        PemberianObat.setActionCommand("Pemberian Obat");
        PemberianObat.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PemberianObat.setName("PemberianObat"); // NOI18N
        PemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PemberianObatActionPerformed(evt);
            }
        });
        FormInput.add(PemberianObat);
        PemberianObat.setBounds(610, 300, 23, 20);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("Lain - lain :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(620, 320, 60, 20);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("Lain - lain: ");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(390, 460, 80, 20);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("Hand Hyigiene");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(60, 260, 250, 20);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("Evakuasi Kebakaran");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(60, 280, 250, 20);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("Penggunaan APAR");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(60, 300, 250, 20);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Bantuan Hidup Dasar");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(60, 320, 250, 20);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("Perawatan NGT");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(340, 260, 250, 20);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("Perawatan Kateter");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(340, 280, 250, 20);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("Infus");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(340, 300, 250, 20);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("Oksigen");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(340, 320, 250, 20);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("Pengaturan Diet");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(640, 260, 250, 20);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("Perawatan Luka");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(640, 280, 250, 20);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("Pemberian Obat");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(640, 300, 250, 20);

        jSeparator16.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(10, 231, 940, 130);

        Ro.setBorder(null);
        Ro.setActionCommand("Ro");
        Ro.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Ro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Ro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Ro.setName("Ro"); // NOI18N
        Ro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoActionPerformed(evt);
            }
        });
        FormInput.add(Ro);
        Ro.setBounds(30, 400, 23, 23);

        KeteranganRo.setHighlighter(null);
        KeteranganRo.setName("KeteranganRo"); // NOI18N
        KeteranganRo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRoKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRo);
        KeteranganRo.setBounds(80, 400, 270, 23);

        CTScan.setBorder(null);
        CTScan.setActionCommand("CT Scan");
        CTScan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        CTScan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CTScan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CTScan.setName("CTScan"); // NOI18N
        CTScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTScanActionPerformed(evt);
            }
        });
        FormInput.add(CTScan);
        CTScan.setBounds(30, 430, 23, 23);

        KeteranganCTScan.setHighlighter(null);
        KeteranganCTScan.setName("KeteranganCTScan"); // NOI18N
        KeteranganCTScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KeteranganCTScanActionPerformed(evt);
            }
        });
        KeteranganCTScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganCTScanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganCTScan);
        KeteranganCTScan.setBounds(110, 430, 240, 23);

        USG.setBorder(null);
        USG.setActionCommand("USG");
        USG.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        USG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        USG.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        USG.setName("USG"); // NOI18N
        USG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USGActionPerformed(evt);
            }
        });
        FormInput.add(USG);
        USG.setBounds(30, 460, 23, 23);

        KeteranganUSG.setHighlighter(null);
        KeteranganUSG.setName("KeteranganUSG"); // NOI18N
        KeteranganUSG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganUSGKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganUSG);
        KeteranganUSG.setBounds(90, 460, 260, 23);

        EKG.setBorder(null);
        EKG.setActionCommand("EKG");
        EKG.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        EKG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EKG.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EKG.setName("EKG"); // NOI18N
        EKG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EKGActionPerformed(evt);
            }
        });
        FormInput.add(EKG);
        EKG.setBounds(380, 400, 23, 23);

        Lab.setBorder(null);
        Lab.setActionCommand("Lab");
        Lab.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Lab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lab.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Lab.setName("Lab"); // NOI18N
        Lab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LabActionPerformed(evt);
            }
        });
        FormInput.add(Lab);
        Lab.setBounds(380, 430, 23, 23);

        KeteranganLab.setHighlighter(null);
        KeteranganLab.setName("KeteranganLab"); // NOI18N
        KeteranganLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLabKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLab);
        KeteranganLab.setBounds(440, 430, 270, 23);

        KeteranganLainnya.setHighlighter(null);
        KeteranganLainnya.setName("KeteranganLainnya"); // NOI18N
        KeteranganLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLainnya);
        KeteranganLainnya.setBounds(450, 460, 260, 23);

        SuratSakit.setBorder(null);
        SuratSakit.setActionCommand("Surat istirahat / Surat sakit");
        SuratSakit.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        SuratSakit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SuratSakit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SuratSakit.setName("SuratSakit"); // NOI18N
        SuratSakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuratSakitActionPerformed(evt);
            }
        });
        FormInput.add(SuratSakit);
        SuratSakit.setBounds(740, 400, 23, 23);

        SuratRawat.setBorder(null);
        SuratRawat.setActionCommand("Surat keterangan dirawat");
        SuratRawat.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        SuratRawat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SuratRawat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SuratRawat.setName("SuratRawat"); // NOI18N
        SuratRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuratRawatActionPerformed(evt);
            }
        });
        FormInput.add(SuratRawat);
        SuratRawat.setBounds(740, 430, 23, 23);

        SuratLepasRawat.setBorder(null);
        SuratLepasRawat.setActionCommand("Surat Lepas Rawat");
        SuratLepasRawat.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        SuratLepasRawat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SuratLepasRawat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SuratLepasRawat.setName("SuratLepasRawat"); // NOI18N
        SuratLepasRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuratLepasRawatActionPerformed(evt);
            }
        });
        FormInput.add(SuratLepasRawat);
        SuratLepasRawat.setBounds(740, 460, 23, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("DOKUMEN / HASIL PEMERIKSAAN PENUNJANG YANG DIBERIKAN SAAT PULANG");
        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(30, 370, 530, 30);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel122.setText("Ro:");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(60, 400, 80, 20);

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel123.setText("CT Scan:");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(60, 430, 80, 20);

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel124.setText("USG:");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(60, 460, 80, 20);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("Surat Lepas Rawat");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(770, 460, 140, 20);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("EKG");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(410, 400, 80, 20);

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("Surat istirahat / Surat sakit");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(770, 400, 140, 20);

        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel128.setText("Surat keterangan dirawat");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(770, 430, 140, 20);

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("Lab:");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(410, 430, 80, 20);

        jSeparator17.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(10, 371, 940, 130);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("OBAT - OBATAN YANG DIBERIKAN SAAT PULANG");
        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(30, 510, 530, 30);

        BtnObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat.setMnemonic('2');
        BtnObat.setToolTipText("Alt+2");
        BtnObat.setName("BtnObat"); // NOI18N
        BtnObat.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObatActionPerformed(evt);
            }
        });
        FormInput.add(BtnObat);
        BtnObat.setBounds(30, 550, 28, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Obat2an.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Obat2an.setColumns(20);
        Obat2an.setRows(5);
        Obat2an.setName("Obat2an"); // NOI18N
        Obat2an.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat2anKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Obat2an);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(70, 540, 850, 130);

        jSeparator18.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator18.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(10, 511, 940, 170);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("PROSES TANYA JAWAB DILAKUKAN OLEH :");
        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(30, 770, 530, 30);

        jLabel131.setText("Diet Di Rumah :");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(10, 700, 120, 23);

        Diet.setHighlighter(null);
        Diet.setName("Diet"); // NOI18N
        Diet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DietKeyPressed(evt);
            }
        });
        FormInput.add(Diet);
        Diet.setBounds(140, 700, 780, 23);

        Intruksi.setHighlighter(null);
        Intruksi.setName("Intruksi"); // NOI18N
        Intruksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntruksiKeyPressed(evt);
            }
        });
        FormInput.add(Intruksi);
        Intruksi.setBounds(140, 730, 780, 23);

        jSeparator19.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator19.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(10, 691, 940, 160);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Perencanaan Pemulangan", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Masuk Dirawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-06-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-06-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(185, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

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

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Edukasi & Konfirmasi : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Perencanaan Pemulangan", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else if(DiagnosaMedis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaMedis,"Diagnosa Medis");
        }else if(AlasanMasuk.getText().trim().equals("")){
            Valid.textKosong(AlasanMasuk,"Alasan Masuk / Dirawat");
        }else if(SaksiKeluarga.getText().trim().equals("")){
            Valid.textKosong(SaksiKeluarga,"Pasien/Keluarga");
        }else{
            if(Sequel.menyimpantf("perencanaan_pemulangan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Pernyataan",44,new String[]{
                    TNoRw.getText(),Valid.SetTgl(RencanaPemulangan.getSelectedItem()+""),AlasanMasuk.getText(),DiagnosaMedis.getText(),SaksiKeluarga.getText(),KdPetugas.getText(),KondisiPulang.getSelectedItem().toString(),Mobilisasi.getSelectedItem().toString(),
                    (Oksigen.isSelected() ? "true" : ""),(Infus.isSelected() ? "true" : ""),(NGT.isSelected() ? "true" : ""),(Kateter.isSelected() ? "true" : ""),(Drain.isSelected() ? "true" : ""),(TidakAda.isSelected() ? "true" : ""),AlatLainnya.getText(),(HandHyigine.isSelected() ? "true" : ""),(EvakuasiKebakaran.isSelected() ? "true" : ""),(PenggunaanAPAR.isSelected() ? "true" : ""),(BantuanHidupDasar.isSelected() ? "true" : ""),
                    (PerawatanNGT.isSelected() ? "true" : ""),(PerawatanKateter.isSelected() ? "true" : ""),(PerawatanInfus.isSelected() ? "true" : ""),(PerawatanOksigen.isSelected() ? "true" : ""),(PengaturanDiet.isSelected() ? "true" : ""),PerawatanLuka.getText(),(PemberianObat.isSelected() ? "true" : ""),PenyuluhanLainnya.getText(),(Ro.isSelected() ? "true" : ""),
                    KeteranganRo.getText(),(CTScan.isSelected() ? "true" : ""),KeteranganCTScan.getText(),(USG.isSelected() ? "true" : ""),KeteranganUSG.getText(),(EKG.isSelected() ? "true" : ""),(Lab.isSelected() ? "true" : ""),KeteranganLab.getText(),KeteranganLainnya.getText(),(SuratSakit.isSelected() ? "true" : ""),(SuratRawat.isSelected() ? "true" : ""),(SuratLepasRawat.isSelected() ? "true" : ""),
                    Obat2an.getText(),Diet.getText(),Intruksi.getText(),DiagnosaMedis1.getText()
                    
                })==true){
                tampil();
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,SaksiKeluarga,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
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
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString())){
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else if(DiagnosaMedis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaMedis,"Diagnosa Medis");
        }else if(AlasanMasuk.getText().trim().equals("")){
            Valid.textKosong(AlasanMasuk,"Alasan Masuk / Dirawat");
        }else if(SaksiKeluarga.getText().trim().equals("")){
            Valid.textKosong(SaksiKeluarga,"Pasien/Keluarga");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
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
            try{
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f;            
                BufferedWriter bw; 
                
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                            "perencanaan_pemulangan.rencana_pulang,perencanaan_pemulangan.alasan_masuk,perencanaan_pemulangan.diagnosa_medis,perencanaan_pemulangan.pengaruh_ri_pasien_dan_keluarga,"+
                            "perencanaan_pemulangan.keterangan_pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.pengaruh_ri_pekerjaan_sekolah,perencanaan_pemulangan.keterangan_pengaruh_ri_pekerjaan_sekolah,"+
                            "perencanaan_pemulangan.pengaruh_ri_keuangan,perencanaan_pemulangan.keterangan_pengaruh_ri_keuangan,perencanaan_pemulangan.antisipasi_masalah_saat_pulang,"+
                            "perencanaan_pemulangan.keterangan_antisipasi_masalah_saat_pulang,perencanaan_pemulangan.bantuan_diperlukan_dalam,perencanaan_pemulangan.keterangan_bantuan_diperlukan_dalam,"+
                            "perencanaan_pemulangan.adakah_yang_membantu_keperluan,perencanaan_pemulangan.keterangan_adakah_yang_membantu_keperluan,perencanaan_pemulangan.pasien_tinggal_sendiri,"+
                            "perencanaan_pemulangan.keterangan_pasien_tinggal_sendiri,perencanaan_pemulangan.pasien_menggunakan_peralatan_medis,perencanaan_pemulangan.keterangan_pasien_menggunakan_peralatan_medis,"+
                            "perencanaan_pemulangan.pasien_memerlukan_alat_bantu,perencanaan_pemulangan.keterangan_pasien_memerlukan_alat_bantu,perencanaan_pemulangan.memerlukan_perawatan_khusus,"+
                            "perencanaan_pemulangan.keterangan_memerlukan_perawatan_khusus,perencanaan_pemulangan.bermasalah_memenuhi_kebutuhan,perencanaan_pemulangan.keterangan_bermasalah_memenuhi_kebutuhan,"+
                            "perencanaan_pemulangan.memiliki_nyeri_kronis,perencanaan_pemulangan.keterangan_memiliki_nyeri_kronis,perencanaan_pemulangan.memerlukan_edukasi_kesehatan,"+
                            "perencanaan_pemulangan.keterangan_memerlukan_edukasi_kesehatan,perencanaan_pemulangan.memerlukan_keterampilkan_khusus,perencanaan_pemulangan.keterangan_memerlukan_keterampilkan_khusus,"+
                            "perencanaan_pemulangan.nama_pasien_keluarga,perencanaan_pemulangan.nip,petugas.nama "+
                            "from perencanaan_pemulangan inner join reg_periksa on perencanaan_pemulangan.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                            "inner join petugas on petugas.nip=perencanaan_pemulangan.nip where "+
                            "reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                            "perencanaan_pemulangan.rencana_pulang,perencanaan_pemulangan.alasan_masuk,perencanaan_pemulangan.diagnosa_medis,perencanaan_pemulangan.pengaruh_ri_pasien_dan_keluarga,"+
                            "perencanaan_pemulangan.keterangan_pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.pengaruh_ri_pekerjaan_sekolah,perencanaan_pemulangan.keterangan_pengaruh_ri_pekerjaan_sekolah,"+
                            "perencanaan_pemulangan.pengaruh_ri_keuangan,perencanaan_pemulangan.keterangan_pengaruh_ri_keuangan,perencanaan_pemulangan.antisipasi_masalah_saat_pulang,"+
                            "perencanaan_pemulangan.keterangan_antisipasi_masalah_saat_pulang,perencanaan_pemulangan.bantuan_diperlukan_dalam,perencanaan_pemulangan.keterangan_bantuan_diperlukan_dalam,"+
                            "perencanaan_pemulangan.adakah_yang_membantu_keperluan,perencanaan_pemulangan.keterangan_adakah_yang_membantu_keperluan,perencanaan_pemulangan.pasien_tinggal_sendiri,"+
                            "perencanaan_pemulangan.keterangan_pasien_tinggal_sendiri,perencanaan_pemulangan.pasien_menggunakan_peralatan_medis,perencanaan_pemulangan.keterangan_pasien_menggunakan_peralatan_medis,"+
                            "perencanaan_pemulangan.pasien_memerlukan_alat_bantu,perencanaan_pemulangan.keterangan_pasien_memerlukan_alat_bantu,perencanaan_pemulangan.memerlukan_perawatan_khusus,"+
                            "perencanaan_pemulangan.keterangan_memerlukan_perawatan_khusus,perencanaan_pemulangan.bermasalah_memenuhi_kebutuhan,perencanaan_pemulangan.keterangan_bermasalah_memenuhi_kebutuhan,"+
                            "perencanaan_pemulangan.memiliki_nyeri_kronis,perencanaan_pemulangan.keterangan_memiliki_nyeri_kronis,perencanaan_pemulangan.memerlukan_edukasi_kesehatan,"+
                            "perencanaan_pemulangan.keterangan_memerlukan_edukasi_kesehatan,perencanaan_pemulangan.memerlukan_keterampilkan_khusus,perencanaan_pemulangan.keterangan_memerlukan_keterampilkan_khusus,"+
                            "perencanaan_pemulangan.nama_pasien_keluarga,perencanaan_pemulangan.nip,petugas.nama "+
                            "from perencanaan_pemulangan inner join reg_periksa on perencanaan_pemulangan.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                            "inner join petugas on petugas.nip=perencanaan_pemulangan.nip where "+
                            "reg_periksa.tgl_registrasi between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            "perencanaan_pemulangan.nip like ? or petugas.nama like ?) order by reg_periksa.tgl_registrasi");
                }

                try {
                    if(TCari.getText().trim().equals("")){
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    }else{
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                        ps.setString(3,"%"+TCari.getText()+"%");
                        ps.setString(4,"%"+TCari.getText()+"%");
                        ps.setString(5,"%"+TCari.getText()+"%");
                        ps.setString(6,"%"+TCari.getText()+"%");
                        ps.setString(7,"%"+TCari.getText()+"%");
                    } 
                    rs=ps.executeQuery();
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                    switch (pilihan) {
                        case "Laporan 1 (HTML)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Masuk Dirawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Rencana Pulang</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Diagnosa Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alasan Masuk / Dirawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Pasien & Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Pasien & Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Pekerjaan/Sekolah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Pekerjaan/Sekolah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Keuangan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Keuangan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Antisipasi Masalah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Antisipasi Masalah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Bantuan Diperlukan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Bantuan Diperlukan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Membantu Keperluan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Yang Membantu Keperluan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tinggal Sendiri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pasien Tinggal Sendiri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Peralatan Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Peralatan Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alat Bantu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Memerlukan Alat Bantu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Perawatan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Perawatan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Memenuhi Kebutuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Memenuhi Kebutuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri Kronis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Nyeri Kronis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Edukasi Kesehatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Edukasi Kesehatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterampilkan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Keterampilkan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pasien/Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>NIP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Petugas</td>"+
                                    "</tr>"
                                );
                                while(rs.next()){
                                    htmlContent.append(
                                        "<tr class='isi'>"+
                                            "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                                            "<td valign='top'>"+rs.getString("jk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rencana_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("diagnosa_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alasan_masuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_pasien_dan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_pasien_dan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_pekerjaan_sekolah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_pekerjaan_sekolah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_keuangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_keuangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("antisipasi_masalah_saat_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_antisipasi_masalah_saat_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("bantuan_diperlukan_dalam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_bantuan_diperlukan_dalam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("adakah_yang_membantu_keperluan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_adakah_yang_membantu_keperluan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_tinggal_sendiri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_tinggal_sendiri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_menggunakan_peralatan_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_menggunakan_peralatan_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_memerlukan_alat_bantu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_memerlukan_alat_bantu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_perawatan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_perawatan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("bermasalah_memenuhi_kebutuhan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_bermasalah_memenuhi_kebutuhan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memiliki_nyeri_kronis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memiliki_nyeri_kronis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_edukasi_kesehatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_edukasi_kesehatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_keterampilkan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_keterampilkan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama_pasien_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama")+"</td>"+
                                        "</tr>"
                                    );
                                }
                                f = new File("RencanaPemulangan.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write("<html>"+
                                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                            "<body>"+
                                                "<table width='5500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                    htmlContent.toString()+
                                                "</table>"+
                                            "</body>"+                   
                                         "</html>"
                                );

                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break;
                        case "Laporan 2 (WPS)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Masuk Dirawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Rencana Pulang</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Diagnosa Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alasan Masuk / Dirawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Pasien & Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Pasien & Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Pekerjaan/Sekolah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Pekerjaan/Sekolah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Keuangan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Keuangan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Antisipasi Masalah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Antisipasi Masalah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Bantuan Diperlukan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Bantuan Diperlukan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Membantu Keperluan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Yang Membantu Keperluan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tinggal Sendiri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pasien Tinggal Sendiri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Peralatan Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Peralatan Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alat Bantu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Memerlukan Alat Bantu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Perawatan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Perawatan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Memenuhi Kebutuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Memenuhi Kebutuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri Kronis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Nyeri Kronis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Edukasi Kesehatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Edukasi Kesehatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterampilkan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Keterampilkan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pasien/Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>NIP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Petugas</td>"+
                                    "</tr>"
                                );
                                while(rs.next()){
                                    htmlContent.append(
                                        "<tr class='isi'>"+
                                            "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                                            "<td valign='top'>"+rs.getString("jk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rencana_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("diagnosa_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alasan_masuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_pasien_dan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_pasien_dan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_pekerjaan_sekolah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_pekerjaan_sekolah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_keuangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_keuangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("antisipasi_masalah_saat_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_antisipasi_masalah_saat_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("bantuan_diperlukan_dalam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_bantuan_diperlukan_dalam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("adakah_yang_membantu_keperluan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_adakah_yang_membantu_keperluan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_tinggal_sendiri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_tinggal_sendiri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_menggunakan_peralatan_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_menggunakan_peralatan_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_memerlukan_alat_bantu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_memerlukan_alat_bantu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_perawatan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_perawatan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("bermasalah_memenuhi_kebutuhan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_bermasalah_memenuhi_kebutuhan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memiliki_nyeri_kronis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memiliki_nyeri_kronis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_edukasi_kesehatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_edukasi_kesehatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_keterampilkan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_keterampilkan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama_pasien_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama")+"</td>"+
                                        "</tr>"
                                    );
                                }
                                f = new File("RencanaPemulangan.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write("<html>"+
                                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                            "<body>"+
                                                "<table width='5500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                    htmlContent.toString()+
                                                "</table>"+
                                            "</body>"+                   
                                         "</html>"
                                );

                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break;
                        case "Laporan 3 (CSV)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"J.K.\";\"Masuk Dirawat\";\"Rencana Pulang\";\"Diagnosa Medis\";\"Alasan Masuk / Dirawat\";\"Pengaruh RI Pasien & Keluarga\";\"Keterangan Pengaruh RI Pasien & Keluarga\";\"Pengaruh RI Pekerjaan/Sekolah\";\"Keterangan Pengaruh RI Pekerjaan/Sekolah\";\"Pengaruh RI Keuangan\";\"Keterangan Pengaruh RI Keuangan\";\"Antisipasi Masalah\";\"Keterangan Antisipasi Masalah\";\"Bantuan Diperlukan\";\"Keterangan Bantuan Diperlukan\";\"Membantu Keperluan\";\"Keterangan Yang Membantu Keperluan\";\"Tinggal Sendiri\";\"Keterangan Pasien Tinggal Sendiri\";\"Peralatan Medis\";\"Keterangan Peralatan Medis\";\"Alat Bantu\";\"Keterangan Memerlukan Alat Bantu\";\"Perawatan Khusus\";\"Keterangan Perawatan Khusus\";\"Memenuhi Kebutuhan\";\"Keterangan Memenuhi Kebutuhan\";\"Nyeri Kronis\";\"Keterangan Nyeri Kronis\";\"Edukasi Kesehatan\";\"Keterangan Edukasi Kesehatan\";\"Keterampilkan Khusus\";\"Keterangan Keterampilkan Khusus\";\"Pasien/Keluarga\";\"NIP\";\"Nama Petugas\"\n"
                                ); 
                                while(rs.next()){
                                    htmlContent.append(
                                        "\""+rs.getString("no_rawat")+"\";\""+rs.getString("no_rkm_medis")+"\";\""+rs.getString("nm_pasien")+"\";\""+rs.getString("tgl_lahir")+"\";\""+rs.getString("jk")+"\";\""+rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg")+"\";\""+rs.getString("rencana_pulang")+"\";\""+rs.getString("diagnosa_medis")+"\";\""+rs.getString("alasan_masuk")+"\";\""+rs.getString("pengaruh_ri_pasien_dan_keluarga")+"\";\""+rs.getString("keterangan_pengaruh_ri_pasien_dan_keluarga")+"\";\""+rs.getString("pengaruh_ri_pekerjaan_sekolah")+"\";\""+rs.getString("keterangan_pengaruh_ri_pekerjaan_sekolah")+"\";\""+rs.getString("pengaruh_ri_keuangan")+"\";\""+rs.getString("keterangan_pengaruh_ri_keuangan")+"\";\""+rs.getString("antisipasi_masalah_saat_pulang")+"\";\""+rs.getString("keterangan_antisipasi_masalah_saat_pulang")+"\";\""+rs.getString("bantuan_diperlukan_dalam")+"\";\""+rs.getString("keterangan_bantuan_diperlukan_dalam")+"\";\""+rs.getString("adakah_yang_membantu_keperluan")+"\";\""+rs.getString("keterangan_adakah_yang_membantu_keperluan")+"\";\""+rs.getString("pasien_tinggal_sendiri")+"\";\""+rs.getString("keterangan_pasien_tinggal_sendiri")+"\";\""+rs.getString("pasien_menggunakan_peralatan_medis")+"\";\""+rs.getString("keterangan_pasien_menggunakan_peralatan_medis")+"\";\""+rs.getString("pasien_memerlukan_alat_bantu")+"\";\""+rs.getString("keterangan_pasien_memerlukan_alat_bantu")+"\";\""+rs.getString("memerlukan_perawatan_khusus")+"\";\""+rs.getString("keterangan_memerlukan_perawatan_khusus")+"\";\""+rs.getString("bermasalah_memenuhi_kebutuhan")+"\";\""+rs.getString("keterangan_bermasalah_memenuhi_kebutuhan")+"\";\""+rs.getString("memiliki_nyeri_kronis")+"\";\""+rs.getString("keterangan_memiliki_nyeri_kronis")+"\";\""+rs.getString("memerlukan_edukasi_kesehatan")+"\";\""+rs.getString("keterangan_memerlukan_edukasi_kesehatan")+"\";\""+rs.getString("memerlukan_keterampilkan_khusus")+"\";\""+rs.getString("keterangan_memerlukan_keterampilkan_khusus")+"\";\""+rs.getString("nama_pasien_keluarga")+"\";\""+rs.getString("nip")+"\";\""+rs.getString("nama")+"\"\n"
                                    );
                                }
                                f = new File("RencanaPemulangan.csv");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(htmlContent.toString());

                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break; 
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                isPhoto();
                panggilPhoto();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
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
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            //Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void RencanaPemulanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaPemulanganKeyPressed
        Valid.pindah(evt,SaksiKeluarga,DiagnosaMedis);
    }//GEN-LAST:event_RencanaPemulanganKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void SaksiKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaksiKeluargaKeyPressed
        
    }//GEN-LAST:event_SaksiKeluargaKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
//        if(tabMode.getRowCount()==0){
//            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
//            TCari.requestFocus();
//        }else{
//            if(tbObat.getSelectedRow()>-1){
//                Sequel.queryu("delete from antripemulangan");
//                Sequel.queryu("insert into antripemulangan values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"')");
//                Sequel.queryu("delete from bukti_perencanaan_pemulangan_saksikeluarga where no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
//            }else{
//                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
//            }   
//        }
        if(tabMode.getRowCount()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//            Valid.panggilUrl("perencanaanpemulangan/index.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()
//            +"Kamera");
            Valid.panggilUrl("perencanaanpemulangan/index.php?act=Kamera");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void PenyuluhanLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyuluhanLainnyaKeyPressed
  
    }//GEN-LAST:event_PenyuluhanLainnyaKeyPressed

    private void DiagnosaMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaMedisKeyPressed
        Valid.pindah(evt,RencanaPemulangan,AlasanMasuk);
    }//GEN-LAST:event_DiagnosaMedisKeyPressed

    private void AlasanMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanMasukKeyPressed
        
    }//GEN-LAST:event_AlasanMasukKeyPressed

    private void DiagnosaMedis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaMedis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaMedis1KeyPressed

    private void KateterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KateterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KateterActionPerformed

    private void MobilisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MobilisasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MobilisasiKeyPressed

    private void KondisiPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KondisiPulangKeyPressed

    private void InfusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InfusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InfusActionPerformed

    private void NGTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NGTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NGTActionPerformed

    private void OksigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OksigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OksigenActionPerformed

    private void DrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DrainActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DrainActionPerformed

    private void AlatLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlatLainnyaKeyPressed

    private void TidakAdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TidakAdaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TidakAdaActionPerformed

    private void SuratLepasRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuratLepasRawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuratLepasRawatActionPerformed

    private void HandHyigineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HandHyigineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HandHyigineActionPerformed

    private void EvakuasiKebakaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EvakuasiKebakaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EvakuasiKebakaranActionPerformed

    private void PenggunaanAPARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PenggunaanAPARActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenggunaanAPARActionPerformed

    private void BantuanHidupDasarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BantuanHidupDasarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BantuanHidupDasarActionPerformed

    private void PerawatanNGTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerawatanNGTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerawatanNGTActionPerformed

    private void PerawatanKateterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerawatanKateterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerawatanKateterActionPerformed

    private void PerawatanInfusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerawatanInfusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerawatanInfusActionPerformed

    private void PerawatanOksigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerawatanOksigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerawatanOksigenActionPerformed

    private void PengaturanDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengaturanDietActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengaturanDietActionPerformed

    private void PerawatanLukaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerawatanLukaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerawatanLukaActionPerformed

    private void PemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PemberianObatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemberianObatActionPerformed

    private void RoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RoActionPerformed

    private void CTScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTScanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTScanActionPerformed

    private void USGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGActionPerformed

    private void EKGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EKGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EKGActionPerformed

    private void LabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LabActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LabActionPerformed

    private void SuratSakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuratSakitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuratSakitActionPerformed

    private void SuratRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuratRawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuratRawatActionPerformed

    private void KeteranganUSGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganUSGKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganUSGKeyPressed

    private void KeteranganLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganLainnyaKeyPressed

    private void KeteranganRoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganRoKeyPressed

    private void KeteranganCTScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganCTScanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganCTScanKeyPressed

    private void KeteranganCTScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KeteranganCTScanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganCTScanActionPerformed

    private void KeteranganLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganLabKeyPressed

    private void IntruksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntruksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntruksiKeyPressed

    private void DietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DietKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DietKeyPressed

    private void BtnObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObatActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            cariobat.setNoRawat(TNoRw.getText());
            cariobat.tampil();
            cariobat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobat.setLocationRelativeTo(internalFrame1);
            cariobat.setVisible(true);
        }
    }//GEN-LAST:event_BtnObatActionPerformed

    private void Obat2anKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat2anKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                BtnSimpan.requestFocus();
            }
        }
//        else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
//            KodeProsedurSekunder3.requestFocus();
//        }
    }//GEN-LAST:event_Obat2anKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPerencanaanPemulangan dialog = new RMPerencanaanPemulangan(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlasanMasuk;
    private widget.TextBox AlatLainnya;
    private widget.CekBox BantuanHidupDasar;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnObat;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox CTScan;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaMedis;
    private widget.TextBox DiagnosaMedis1;
    private widget.TextBox Diet;
    private widget.CekBox Drain;
    private widget.CekBox EKG;
    private widget.CekBox EvakuasiKebakaran;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.CekBox HandHyigine;
    private widget.CekBox Infus;
    private widget.TextBox Intruksi;
    private widget.TextBox Jk;
    private widget.CekBox Kateter;
    private widget.TextBox KdPetugas;
    private widget.TextBox KeteranganCTScan;
    private widget.TextBox KeteranganLab;
    private widget.TextBox KeteranganLainnya;
    private widget.TextBox KeteranganRo;
    private widget.TextBox KeteranganUSG;
    private widget.ComboBox KondisiPulang;
    private widget.Label LCount;
    private widget.CekBox Lab;
    private widget.editorpane LoadHTML2;
    private widget.TextBox MasukDirawat;
    private widget.ComboBox Mobilisasi;
    private widget.CekBox NGT;
    private widget.TextBox NmPetugas;
    private widget.TextArea Obat2an;
    private widget.CekBox Oksigen;
    private widget.PanelBiasa PanelAccor;
    private widget.CekBox PemberianObat;
    private widget.CekBox PengaturanDiet;
    private widget.CekBox PenggunaanAPAR;
    private widget.TextBox PenyuluhanLainnya;
    private widget.CekBox PerawatanInfus;
    private widget.CekBox PerawatanKateter;
    private widget.CekBox PerawatanLuka;
    private widget.CekBox PerawatanNGT;
    private widget.CekBox PerawatanOksigen;
    private widget.Tanggal RencanaPemulangan;
    private widget.CekBox Ro;
    private widget.TextBox SaksiKeluarga;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.CekBox SuratLepasRawat;
    private widget.CekBox SuratRawat;
    private widget.CekBox SuratSakit;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TglLahir;
    private widget.CekBox TidakAda;
    private widget.CekBox USG;
    private widget.Button btnAmbil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel131;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel6;
    private widget.Label jLabel65;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label15;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select b.no_rawat,c.no_rkm_medis,c.nm_pasien,c.jk,c.tgl_lahir,b.tgl_registrasi,b.jam_reg,a.*,d.nama "+
                        "from perencanaan_pemulangan a "+
                        "inner join reg_periksa b on a.no_rawat=b.no_rawat "+
                        "inner join pasien c on c.no_rkm_medis=b.no_rkm_medis "+
                        "inner join petugas d on d.nip=a.nip where "+
                        "b.tgl_registrasi between ? and ? order by b.tgl_registrasi");
            }else{
                ps=koneksi.prepareStatement(
                        "select b.no_rawat,c.no_rkm_medis,c.nm_pasien,c.jk,c.tgl_lahir,b.tgl_registrasi,b.jam_reg,a.*,d.nama "+
                        "from perencanaan_pemulangan a "+
                        "inner join reg_periksa b on a.no_rawat=b.no_rawat "+
                        "inner join pasien c on c.no_rkm_medis=b.no_rkm_medis "+
                        "inner join petugas d on d.nip=a.nip where "+
                        "b.tgl_registrasi between ? and ? and (b.no_rawat like ? or c.no_rkm_medis like ? or c.nm_pasien like ? or "+
                        "a.nip like ? or d.nama like ?) order by b.tgl_registrasi");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"),
                        rs.getString("rencana_pulang"),rs.getString("diagnosa_medis"),rs.getString("diagnosa_keluar"),rs.getString("alasan_masuk"),
                        rs.getString("kondisi_pulang"),rs.getString("mobilisasi"),rs.getString("oksigen"),
                        rs.getString("infus"),rs.getString("ngt"),rs.getString("kateter"),rs.getString("drain"),
                        rs.getString("tidakada"),rs.getString("lainnya"),rs.getString("handhyigine"),
                        rs.getString("kebakaran"),rs.getString("apar"),rs.getString("bhd"),
                        rs.getString("prw_ngt"),rs.getString("prw_kateter"),rs.getString("prw_infus"),
                        rs.getString("prw_oksigen"),rs.getString("penyuluhan_diet"),rs.getString("prw_luka"),
                        rs.getString("pmb_obat"),rs.getString("pnyuluhan_lain"),rs.getString("ro"),
                        rs.getString("ket_ro"),rs.getString("ctscan"),rs.getString("ket_ctscan"),
                        rs.getString("usg"),rs.getString("ket_usg"),rs.getString("ekg"),
                        rs.getString("lab"),rs.getString("ket_lab"),rs.getString("dokumen_lain"),
                        rs.getString("surat_sakit"),rs.getString("surat_rawat"),rs.getString("surat_lepas_rawat"),
                        rs.getString("obat_pulang"),rs.getString("diet"),rs.getString("intruksi"),
                        rs.getString("nama_pasien_keluarga"),rs.getString("nip"),rs.getString("nama")             
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
        RencanaPemulangan.setDate(new Date());
//        DiagnosaMedis.setText("");
//        DiagnosaMedis1.setText("");
//        AlasanMasuk.setText("");
        
        KondisiPulang.setSelectedItem("Sembuh");
        Mobilisasi.setSelectedItem("Jalan");
        
        Oksigen.setSelected(false);
        Infus.setSelected(false);
        NGT.setSelected(false);
        Kateter.setSelected(false);
        Drain.setSelected(false);
        TidakAda.setSelected(false);
        AlatLainnya.setText("");
        
        
        HandHyigine.setSelected(false);
        EvakuasiKebakaran.setSelected(false);
        PenggunaanAPAR.setSelected(false);
        BantuanHidupDasar.setSelected(false);
        PerawatanNGT.setSelected(false);
        PerawatanKateter.setSelected(false);
        PerawatanInfus.setSelected(false);
        PerawatanOksigen.setSelected(false);
        PengaturanDiet.setSelected(false);
        PerawatanLuka.setSelected(false);
        PemberianObat.setSelected(false);
        PenyuluhanLainnya.setText("");
        
        
        Ro.setSelected(false);
        KeteranganRo.setText("");
        CTScan.setSelected(false);
        KeteranganCTScan.setText("");
        USG.setSelected(false);
        KeteranganUSG.setText("");
        EKG.setSelected(false);
        Lab.setSelected(false);
        KeteranganLab.setText("");
        KeteranganLainnya.setText("");
        SuratSakit.setSelected(false);
        SuratRawat.setSelected(false);
        SuratLepasRawat.setSelected(false);
        
        
        Obat2an.setText("");
        
        
        Diet.setText("");
        Intruksi.setText("");
//        KdPetugas.setText("");
//        NmPetugas.setText("");
        SaksiKeluarga.setText("");
        TabRawat.setSelectedIndex(0);
//        DiagnosaMedis.requestFocus();

    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString().replaceAll("L","Laki-laki").replaceAll("P","Perempuan"));
            Valid.SetTgl(RencanaPemulangan,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()); 
            MasukDirawat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            DiagnosaMedis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            DiagnosaMedis1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            AlasanMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            
            KondisiPulang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Mobilisasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            
            Oksigen.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString()));
            Infus.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString()));
            NGT.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString()));
            Kateter.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString()));
            Drain.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString()));
            TidakAda.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString()));
            AlatLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            
            
            HandHyigine.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString()));
            EvakuasiKebakaran.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString()));
            PenggunaanAPAR.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString()));
            BantuanHidupDasar.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString()));
            PerawatanNGT.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString()));
            PerawatanKateter.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString()));
            PerawatanInfus.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString()));
            PerawatanOksigen.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString()));
            PengaturanDiet.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString()));
            PerawatanLuka.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString()));
            PemberianObat.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString()));
            PenyuluhanLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            
            
            Ro.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString()));
            KeteranganRo.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            CTScan.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString()));
            KeteranganCTScan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            USG.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 35).toString()));
            KeteranganUSG.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            EKG.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 37).toString()));
            Lab.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 38).toString()));
            KeteranganLab.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            KeteranganLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            SuratSakit.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 41).toString()));
            SuratRawat.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 42).toString()));
            SuratLepasRawat.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 43).toString()));

            
            Obat2an.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Diet.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Intruksi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            SaksiKeluarga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            
            
       }
    }

    private void isRawat() {
        
//        MenampilkanData Kamar Inap
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg, "+
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun,"+
                    "pasien.no_tlp,pasien.umur, kamar_inap.diagnosa_awal, kamar_inap.diagnosa_akhir from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "inner join kamar_inap on kamar_inap.no_rawat=reg_periksa.no_rawat "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
//                    PenyuluhanLainnya.setText(rs.getString("asal"));
                    MasukDirawat.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
                    DiagnosaMedis.setText(rs.getString("diagnosa_awal"));
                    DiagnosaMedis1.setText(rs.getString("diagnosa_akhir"));
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
        
//        Menampilkan Alasan Masuk
        try {
            ps=koneksi.prepareStatement(
                    "select * from permintaan_ranap where no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    AlasanMasuk.setText(rs.getString("catatan"));
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
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 

//      Menampilkan Resep Pulang
        try {
            ps=koneksi.prepareStatement(
                    " SELECT concat(b.nama_brng,' ',a.jml_barang,' ',c.satuan,' ',a.dosis) as obat "+
                    " FROM "+
                    " resep_pulang a "+
                    " INNER JOIN databarang b ON b.kode_brng = a.kode_brng "+
                    " INNER JOIN kodesatuan c ON c.kode_sat = b.kode_sat "+
                    " WHERE a.no_rawat=?");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                namaPenyakit="";
                while(rs.next()){
                    Listpenyakit=rs.getString("obat");
                    namaPenyakit=namaPenyakit+""+Listpenyakit+",  "+"\n";
                }
                 Obat2an.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
        
//      Menampilkan Resep Obat
//        try {
//            ps=koneksi.prepareStatement(
//                    " select concat(databarang.nama_brng,' ',resep_dokter.jml,' ',kodesatuan.satuan,' ',resep_dokter.aturan_pakai) as obat "
//                            + "FROM resep_dokter INNER JOIN resep_obat ON resep_dokter.no_resep = resep_obat.no_resep "
//                            + "INNER JOIN databarang ON resep_dokter.kode_brng = databarang.kode_brng "
//                            + "INNER JOIN kodesatuan ON databarang.kode_sat = kodesatuan.kode_sat "
//                            + "where resep_obat.no_rawat=?");
//            try {
//                ps.setString(1,norwt);
//                rs=ps.executeQuery();
//                namaPenyakit="";
//                while(rs.next()){
//                    Listpenyakit=rs.getString("obat");
//                    namaPenyakit=namaPenyakit+""+Listpenyakit+",  "+"\n";
//                }
//                 Obat2an.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
//            } catch (Exception e) {
//                System.out.println("Notif : "+e);
//           } finally{
//                if(rs!=null){
//                    rs.close();
//                }
//                if(ps!=null){
//                    ps.close();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Notif : "+e);
//        }
        
//      Menapilkan Racikan Obat
//        try {
//            ps=koneksi.prepareStatement(
//                    " select concat(resep_dokter_racikan.nama_racik,' ',resep_dokter_racikan.jml_dr,' ',resep_dokter_racikan.aturan_pakai) as obat "
//                            + "FROM resep_dokter_racikan INNER JOIN resep_obat ON resep_dokter_racikan.no_resep = resep_obat.no_resep where resep_obat.no_rawat=?");
//            try {
//                ps.setString(1,norwt);
//                rs=ps.executeQuery();
//                namaPenyakit="";
//                while(rs.next()){
//                    Listpenyakit=rs.getString("obat");
//                    namaPenyakit=namaPenyakit+""+Listpenyakit+","+"\n";
//                }
//                 Obat2an.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
//            } catch (Exception e) {
//                System.out.println("Notif : "+e);
//            } finally{
//                if(rs!=null){
//                    rs.close();
//                }
//                if(ps!=null){
//                    ps.close();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Notif : "+e);
//        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getperencanaan_pemulangan());
        BtnHapus.setEnabled(akses.getperencanaan_pemulangan());
        BtnEdit.setEnabled(akses.getperencanaan_pemulangan());
        BtnEdit.setEnabled(akses.getperencanaan_pemulangan());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
//            BtnDokter.setEnabled(true);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from perencanaan_pemulangan where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("perencanaan_pemulangan","no_rawat=?","no_rawat=?,rencana_pulang=?,alasan_masuk=?,diagnosa_medis=?,nama_pasien_keluarga=?,nip=?,"
                + "kondisi_pulang=?,mobilisasi=?,oksigen=?,infus=?,ngt=?,kateter=?,drain=?,tidakada=?,lainnya=?,"
                + "handhyigine=?,kebakaran=?,apar=?,bhd=?,prw_ngt=?,prw_kateter=?,prw_infus=?,prw_oksigen=?,penyuluhan_diet=?,"
                + "prw_luka=?,pmb_obat=?,pnyuluhan_lain=?,ro=?,ket_ro=?,ctscan=?,ket_ctscan=?,usg=?,ket_usg=?,ekg=?,"
                + "lab=?,ket_lab=?,dokumen_lain=?,surat_sakit=?,surat_rawat=?,surat_lepas_rawat=?,obat_pulang=?,"
                + "diet=?,intruksi=?,diagnosa_keluar=?",45,new String[]{
                TNoRw.getText(),Valid.SetTgl(RencanaPemulangan.getSelectedItem()+""),AlasanMasuk.getText(),DiagnosaMedis.getText(),SaksiKeluarga.getText(),KdPetugas.getText(),KondisiPulang.getSelectedItem().toString(),Mobilisasi.getSelectedItem().toString(),
                    (Oksigen.isSelected() ? "true" : ""),(Infus.isSelected() ? "true" : ""),(NGT.isSelected() ? "true" : ""),(Kateter.isSelected() ? "true" : ""),(Drain.isSelected() ? "true" : ""),(TidakAda.isSelected() ? "true" : ""),AlatLainnya.getText(),(HandHyigine.isSelected() ? "true" : ""),(EvakuasiKebakaran.isSelected() ? "true" : ""),(PenggunaanAPAR.isSelected() ? "true" : ""),(BantuanHidupDasar.isSelected() ? "true" : ""),
                    (PerawatanNGT.isSelected() ? "true" : ""),(PerawatanKateter.isSelected() ? "true" : ""),(PerawatanInfus.isSelected() ? "true" : ""),(PerawatanOksigen.isSelected() ? "true" : ""),(PengaturanDiet.isSelected() ? "true" : ""),PerawatanLuka.getText(),(PemberianObat.isSelected() ? "true" : ""),PenyuluhanLainnya.getText(),(Ro.isSelected() ? "true" : ""),
                    KeteranganRo.getText(),(CTScan.isSelected() ? "true" : ""),KeteranganCTScan.getText(),(USG.isSelected() ? "true" : ""),KeteranganUSG.getText(),(EKG.isSelected() ? "true" : ""),(Lab.isSelected() ? "true" : ""),KeteranganLab.getText(),KeteranganLainnya.getText(),(SuratSakit.isSelected() ? "true" : ""),(SuratRawat.isSelected() ? "true" : ""),(SuratLepasRawat.isSelected() ? "true" : ""),
                    Obat2an.getText(),Diet.getText(),Intruksi.getText(),DiagnosaMedis1.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(430,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select bukti_perencanaan_pemulangan_saksikeluarga.photo from bukti_perencanaan_pemulangan_saksikeluarga where bukti_perencanaan_pemulangan_saksikeluarga.no_rawat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/ /"+rs.getString("photo")+"' alt='photo' width='450' height='500'/></center></body></html>");
                        }  
                    }else{
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
    }
}
