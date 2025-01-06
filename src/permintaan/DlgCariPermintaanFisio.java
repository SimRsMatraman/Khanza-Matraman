package permintaan;
import fungsi.BackgroundMusic;
import fungsi.WarnaTableFISIO;
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
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgPeriksaRadiologi;

public class DlgCariPermintaanFisio extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3,tabMode4;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Connection koneksifuji;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariBangsal ruang=new DlgCariBangsal(null,false);
    private int i,nilai_detik,permintaanbaru=0;
    private PreparedStatement ps,ps2;
    private final Properties prop = new Properties();
    private BackgroundMusic music;
    private ResultSet rs,rs2;
    private Date now;
    private boolean aktif=false,semua;
    private String alarm="",formalarm="",nol_detik,detik,tglsampel="",tglhasil="",norm="",kamar="",namakamar="",
            NoPermintaan="",NoRawat="",Pasien="",Permintaan="",JamPermintaan="",Sampel="",JamSampel="",Hasil="",JamHasil="",KodeDokter="",DokterPerujuk="",Ruang="",
            InformasiTambahan="",Klinis="",finger="";
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPermintaanFisio(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        WindowAmbilSampel.setSize(600,80);
        WindowGanti.setSize(530,80);
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","No.Rawat","Pasien","Permintaan","Jam","Validasi","Jam",
            "Kode Dokter","Dokter Perujuk","Poli Registrasi","Informasi Tambahan","Diagnosis Klinis",
            "Kode Bayar","Jenis Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbFisioRalan.setModel(tabMode);

        tbFisioRalan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbFisioRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbFisioRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(300);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setPreferredWidth(110);
            }
        }
        tbFisioRalan.setDefaultRenderer(Object.class, new WarnaTableFISIO());
        
        tabMode2=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","No.Rawat","Pasien","Pemeriksaan",
            "Permintaan","Jam","Validasi","Jam","Kode Dokter",
            "Dokter Perujuk","Poli Registrasi","Informasi Tambahan",
            "Diagnosis Klinis","Kode Bayar","Jenis Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbFisioRalan2.setModel(tabMode2);

        tbFisioRalan2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbFisioRalan2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbFisioRalan2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(110);
            }
        }
        tbFisioRalan2.setDefaultRenderer(Object.class, new WarnaTableFISIO());
        
        tabMode3=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","No.Rawat","Pasien","Permintaan","Jam","Validasi","Jam","Kode Dokter","Dokter Perujuk","Kamar Terakhir","Informasi Tambahan","Diagnosis Klinis","Kode Bayar","Jenis Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbFisioRanap.setModel(tabMode3);

        tbFisioRanap.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbFisioRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbFisioRanap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(300);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setPreferredWidth(110);
            }
        }
        tbFisioRanap.setDefaultRenderer(Object.class, new WarnaTableFISIO());
        
        tabMode4=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","No.Rawat","Pasien","Pemeriksaan","Permintaan","Jam","Validasi","Jam","Kode Dokter","Dokter Perujuk","Kamar Terakhir","Informasi Tambahan","Diagnosis Klinis","Kode Bayar","Jenis Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbFisioRanap2.setModel(tabMode4);

        tbFisioRanap2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbFisioRanap2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbFisioRanap2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(110);
            }
        }
        tbFisioRanap2.setDefaultRenderer(Object.class, new WarnaTableFISIO());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
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
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){ 
                    if(TabPilihRawat.getSelectedIndex()==0){
                        CrDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        CrDokter.requestFocus();
                    }else if(TabPilihRawat.getSelectedIndex()==1){
                        CrDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        CrDokter2.requestFocus();
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){   
                    CrPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    CrPoli.requestFocus();
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
        
        ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruang.getTable().getSelectedRow()!= -1){   
                    Kamar.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(),1).toString());  
                    Kamar.requestFocus();
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
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            alarm=prop.getProperty("ALARMRADIOLOGI");
            formalarm=prop.getProperty("FORMALARMRADIOLOGI");
        } catch (Exception ex) {
            alarm="no";
            formalarm="ralan + ranap";
        }
        
        if(alarm.equals("yes")){
            jam();
        }
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WindowAmbilSampel = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel26 = new widget.Label();
        TanggalPulang = new widget.Tanggal();
        listDok = new javax.swing.JComboBox<>();
        label30 = new widget.Label();
        WindowGanti = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        BtnCloseIn5 = new widget.Button();
        BtnSimpan5 = new widget.Button();
        jLabel27 = new widget.Label();
        TanggalPulang1 = new widget.Tanggal();
        TNoPermintaanPR = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        BtnHasil = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        TabPilihRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass9 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrDokter = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        TabRawatJalan = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbFisioRalan = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbFisioRalan2 = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        panelGlass10 = new widget.panelisi();
        jLabel15 = new widget.Label();
        CrDokter2 = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        jLabel17 = new widget.Label();
        Kamar = new widget.TextBox();
        BtnSeek6 = new widget.Button();
        jLabel18 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        TabRawatInap = new javax.swing.JTabbedPane();
        scrollPane3 = new widget.ScrollPane();
        tbFisioRanap = new widget.Table();
        scrollPane4 = new widget.ScrollPane();
        tbFisioRanap2 = new widget.Table();

        WindowAmbilSampel.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowAmbilSampel.setName("WindowAmbilSampel"); // NOI18N
        WindowAmbilSampel.setUndecorated(true);
        WindowAmbilSampel.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update Waktu Validasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(230, 80, 100, 30);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(110, 80, 100, 30);

        jLabel26.setText("Tanggal & Jam :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame5.add(jLabel26);
        jLabel26.setBounds(6, 32, 100, 23);

        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-01-2025 14:56:41" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame5.add(TanggalPulang);
        TanggalPulang.setBounds(110, 32, 150, 23);

        listDok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua" }));
        listDok.setName("listDok"); // NOI18N
        listDok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listDokMouseClicked(evt);
            }
        });
        internalFrame5.add(listDok);
        listDok.setBounds(340, 30, 160, 22);

        label30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label30.setText("Petugas :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(55, 23));
        internalFrame5.add(label30);
        label30.setBounds(270, 30, 80, 23);

        WindowAmbilSampel.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        WindowGanti.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGanti.setName("WindowGanti"); // NOI18N
        WindowGanti.setUndecorated(true);
        WindowGanti.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update Tanggal Permintaan Radiologi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(null);

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('U');
        BtnCloseIn5.setToolTipText("Alt+U");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnCloseIn5);
        BtnCloseIn5.setBounds(440, 30, 30, 30);

        BtnSimpan5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan5.setMnemonic('S');
        BtnSimpan5.setToolTipText("Alt+S");
        BtnSimpan5.setName("BtnSimpan5"); // NOI18N
        BtnSimpan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan5ActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnSimpan5);
        BtnSimpan5.setBounds(390, 30, 40, 30);

        jLabel27.setText("Tanggal :");
        jLabel27.setName("jLabel27"); // NOI18N
        internalFrame6.add(jLabel27);
        jLabel27.setBounds(6, 32, 100, 23);

        TanggalPulang1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPulang1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-01-2025" }));
        TanggalPulang1.setDisplayFormat("dd-MM-yyyy");
        TanggalPulang1.setName("TanggalPulang1"); // NOI18N
        TanggalPulang1.setOpaque(false);
        TanggalPulang1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalPulang1ItemStateChanged(evt);
            }
        });
        TanggalPulang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalPulang1ActionPerformed(evt);
            }
        });
        TanggalPulang1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPulang1KeyPressed(evt);
            }
        });
        internalFrame6.add(TanggalPulang1);
        TanggalPulang1.setBounds(120, 30, 90, 23);

        TNoPermintaanPR.setHighlighter(null);
        TNoPermintaanPR.setName("TNoPermintaanPR"); // NOI18N
        TNoPermintaanPR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoPermintaanPRActionPerformed(evt);
            }
        });
        TNoPermintaanPR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPermintaanPRKeyPressed(evt);
            }
        });
        internalFrame6.add(TNoPermintaanPR);
        TNoPermintaanPR.setBounds(240, 30, 130, 23);

        WindowGanti.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Permintaan Fisioterapi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelGlass8.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass8.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelGlass8.add(Tgl2);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(112, 23));
        panelGlass8.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(318, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass8.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelGlass8.add(BtnCari);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnHapus);

        BtnHasil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        BtnHasil.setMnemonic('P');
        BtnHasil.setText("Validasi");
        BtnHasil.setToolTipText("Alt+P");
        BtnHasil.setName("BtnHasil"); // NOI18N
        BtnHasil.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilActionPerformed(evt);
            }
        });
        BtnHasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHasilKeyPressed(evt);
            }
        });
        panelisi1.add(BtnHasil);

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
        panelisi1.add(BtnAll);

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
        panelisi1.add(BtnPrint);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(53, 23));
        panelisi1.add(LCount);

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabPilihRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabPilihRawat.setName("TabPilihRawat"); // NOI18N
        TabPilihRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPilihRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout());

        panelGlass9.setBorder(null);
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel14);

        CrDokter.setEditable(false);
        CrDokter.setName("CrDokter"); // NOI18N
        CrDokter.setPreferredSize(new java.awt.Dimension(245, 23));
        panelGlass9.add(CrDokter);

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
        panelGlass9.add(BtnSeek3);

        jLabel16.setText("Unit/Poli :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(245, 23));
        panelGlass9.add(CrPoli);

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
        panelGlass9.add(BtnSeek4);

        internalFrame2.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawatJalan.setBackground(new java.awt.Color(255, 255, 254));
        TabRawatJalan.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatJalan.setName("TabRawatJalan"); // NOI18N
        TabRawatJalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatJalanMouseClicked(evt);
            }
        });

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbFisioRalan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbFisioRalan.setName("tbFisioRalan"); // NOI18N
        tbFisioRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFisioRalanMouseClicked(evt);
            }
        });
        tbFisioRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFisioRalanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbFisioRalan);

        TabRawatJalan.addTab("Data Permintaan", scrollPane1);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbFisioRalan2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbFisioRalan2.setName("tbFisioRalan2"); // NOI18N
        tbFisioRalan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFisioRalan2MouseClicked(evt);
            }
        });
        tbFisioRalan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFisioRalan2KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbFisioRalan2);

        TabRawatJalan.addTab("Item Permintaan", scrollPane2);

        internalFrame2.add(TabRawatJalan, java.awt.BorderLayout.CENTER);

        TabPilihRawat.addTab("Rawat Jalan", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Dokter :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel15);

        CrDokter2.setEditable(false);
        CrDokter2.setName("CrDokter2"); // NOI18N
        CrDokter2.setPreferredSize(new java.awt.Dimension(170, 23));
        panelGlass10.add(CrDokter2);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeek5);

        jLabel17.setText("Ruang :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(54, 23));
        panelGlass10.add(jLabel17);

        Kamar.setEditable(false);
        Kamar.setName("Kamar"); // NOI18N
        Kamar.setPreferredSize(new java.awt.Dimension(170, 23));
        panelGlass10.add(Kamar);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('5');
        BtnSeek6.setToolTipText("ALt+5");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeek6);

        jLabel18.setText("Status :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel18);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Belum Pulang" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass10.add(cmbStatus);

        internalFrame3.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        TabRawatInap.setBackground(new java.awt.Color(255, 255, 254));
        TabRawatInap.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatInap.setName("TabRawatInap"); // NOI18N
        TabRawatInap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatInapMouseClicked(evt);
            }
        });

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbFisioRanap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbFisioRanap.setName("tbFisioRanap"); // NOI18N
        tbFisioRanap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFisioRanapMouseClicked(evt);
            }
        });
        tbFisioRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFisioRanapKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(tbFisioRanap);

        TabRawatInap.addTab("Data Permintaan", scrollPane3);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

        tbFisioRanap2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbFisioRanap2.setName("tbFisioRanap2"); // NOI18N
        tbFisioRanap2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFisioRanap2MouseClicked(evt);
            }
        });
        tbFisioRanap2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFisioRanap2KeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(tbFisioRanap2);

        TabRawatInap.addTab("Item Permintaan", scrollPane4);

        internalFrame3.add(TabRawatInap, java.awt.BorderLayout.CENTER);

        TabPilihRawat.addTab("Rawat Inap", internalFrame3);

        internalFrame1.add(TabPilihRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,TCari);
    }//GEN-LAST:event_Tgl2KeyPressed

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
        pilihTab();
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
        if(TabPilihRawat.getSelectedIndex()==0){
            CrDokter.setText("");
            CrPoli.setText("");
            pilihRalan();
        }else if(TabPilihRawat.getSelectedIndex()==1){
            CrDokter2.setText("");
            Kamar.setText("");
            pilihRanap();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode.getRowCount()!=0){
                    
                    Sequel.queryu("delete from temporary_permintaan_fisio");
                    int row=tabMode.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode.getValueAt(i,5).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode.getValueAt(i,7).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_fisio","'0','"+
                            tabMode.getValueAt(i,0).toString()+"','"+
                            tabMode.getValueAt(i,1).toString()+"','"+
                            tabMode.getValueAt(i,2).toString()+"','"+
                            tabMode.getValueAt(i,3).toString()+"','"+
                            tabMode.getValueAt(i,4).toString()+"','"+
                            tglsampel+"','"+
                            tabMode.getValueAt(i,6).toString()+"','"+
                            tglhasil+"','"+
                            tabMode.getValueAt(i,8).toString()+"','"+
                            tabMode.getValueAt(i,9).toString()+"','"+
                            tabMode.getValueAt(i,10).toString()+"','"+
                            tabMode.getValueAt(i,11).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReport("rptLapPermintaanRadiologi.jasper","report","::[ Data Permintaan Radiologi ]::",param);
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                if(tabMode2.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode2.getRowCount()!=0){
                    
                    Sequel.queryu("delete from temporary_permintaan_fisio");
                    int row=tabMode2.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode2.getValueAt(i,6).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode2.getValueAt(i,8).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_fisio","'0','"+
                                        tabMode2.getValueAt(i,0).toString()+"','"+
                                        tabMode2.getValueAt(i,1).toString()+"','"+
                                        tabMode2.getValueAt(i,2).toString()+"','"+
                                        tabMode2.getValueAt(i,3).toString()+"','"+
                                        tabMode2.getValueAt(i,4).toString()+"','"+
                                        tabMode2.getValueAt(i,5).toString()+"','"+
                                        tglsampel+"','"+
                                        tabMode2.getValueAt(i,7).toString()+"','"+
                                        tglhasil+"','"+
                                        tabMode2.getValueAt(i,9).toString()+"','"+
                                        tabMode2.getValueAt(i,10).toString()+"','"+
                                        tabMode2.getValueAt(i,11).toString()+"','"+
                                        tabMode2.getValueAt(i,12).toString()+"','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReport("rptLapPermintaanRadiologi2.jasper","report","::[ Data Detail Permintaan Radiologi ]::",param);
                }
            }            
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode3.getRowCount()!=0){
                    
                    Sequel.queryu("delete from temporary_permintaan_fisio");
                    int row=tabMode3.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode.getValueAt(i,5).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode.getValueAt(i,7).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_fisio","'0','"+
                            tabMode3.getValueAt(i,0).toString()+"','"+
                            tabMode3.getValueAt(i,1).toString()+"','"+
                            tabMode3.getValueAt(i,2).toString()+"','"+
                            tabMode3.getValueAt(i,3).toString()+"','"+
                            tabMode3.getValueAt(i,4).toString()+"','"+
                            tglsampel+"','"+
                            tabMode3.getValueAt(i,6).toString()+"','"+
                            tglhasil+"','"+
                            tabMode3.getValueAt(i,8).toString()+"','"+
                            tabMode3.getValueAt(i,9).toString()+"','"+
                            tabMode3.getValueAt(i,10).toString()+"','"+
                            tabMode3.getValueAt(i,11).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReport("rptLapPermintaanRadiologi3.jasper","report","::[ Data Permintaan Radiologi ]::",param);
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                if(tabMode4.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode4.getRowCount()!=0){
                    
                    Sequel.queryu("delete from temporary_permintaan_fisio");
                    int row=tabMode4.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode4.getValueAt(i,6).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode4.getValueAt(i,8).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_fisio","'0','"+
                                        tabMode4.getValueAt(i,0).toString()+"','"+
                                        tabMode4.getValueAt(i,1).toString()+"','"+
                                        tabMode4.getValueAt(i,2).toString()+"','"+
                                        tabMode4.getValueAt(i,3).toString()+"','"+
                                        tabMode4.getValueAt(i,4).toString()+"','"+
                                        tabMode4.getValueAt(i,5).toString()+"','"+
                                        tglsampel+"','"+
                                        tabMode4.getValueAt(i,7).toString()+"','"+
                                        tglhasil+"','"+
                                        tabMode4.getValueAt(i,9).toString()+"','"+
                                        tabMode4.getValueAt(i,10).toString()+"','"+
                                        tabMode4.getValueAt(i,11).toString()+"','"+
                                        tabMode4.getValueAt(i,12).toString()+"','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReport("rptLapPermintaanRadiologi4.jasper","report","::[ Data Detail Permintaan Radiologi ]::",param);
                }
            }            
        }            
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowAmbilSampel.dispose();
        WindowGanti.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowAmbilSampel.dispose();
            WindowGanti.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnHapus);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    if(TabPilihRawat.getSelectedIndex()==0){
        if(TabRawatJalan.getSelectedIndex()==0){
            if(tbFisioRalan.getSelectedRow()!= -1){
                if(tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{
                    if(Sampel.equals("")||akses.getkode().equals("Admin Utama")){
                        if(Sequel.cariInteger("select count(noorder) from permintaan_pemeriksaan_fisio where stts_bayar='Sudah' and noorder=?",tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),0).toString())>0){
                            JOptionPane.showMessageDialog(null,"Maaf, Tidak boleh dihapus karena sudah ada tindakan yang sudah dibayar.\nSilahkan hubungi kasir...!!!!");
                        }else{
                            Sequel.meghapus("permintaan_fisio","noorder",tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),0).toString());
                            tampil();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Maaf, Sudah dilakukan pengambilan sampel...!!!!");
                    }                     
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            }
        }else if(TabRawatJalan.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Hanya bisa dilakukan hapus di Data Permintaan..!!!");
            TabRawatJalan.setSelectedIndex(0);
            TCari.requestFocus();
        } 
    }else if(TabPilihRawat.getSelectedIndex()==1){
        if(TabRawatInap.getSelectedIndex()==0){
            if(tbFisioRanap.getSelectedRow()!= -1){
                if(tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{
                    if(Sampel.equals("")||akses.getkode().equals("Admin Utama")){
                        if(Sequel.cariInteger("select count(noorder) from permintaan_pemeriksaan_fisio where stts_bayar='Sudah' and noorder=?",tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),0).toString())>0){
                            JOptionPane.showMessageDialog(null,"Maaf, Tidak boleh dihapus karena sudah ada tindakan yang sudah dibayar.\nSilahkan hubungi kasir...!!!!");
                        }else{
                            Sequel.meghapus("permintaan_fisio","noorder",tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),0).toString());
                            tampil3();
                        } 
                    }else{
                        JOptionPane.showMessageDialog(null,"Maaf, Sudah dilakukan pengambilan sampel...!!!!");
                    }                    
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            }
        }else if(TabRawatInap.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Hanya bisa dilakukan hapus di Data Permintaan..!!!");
            TabRawatInap.setSelectedIndex(0);
            TCari.requestFocus();
        } 
    }                
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        pilihTab();
    }//GEN-LAST:event_formWindowOpened

    private void BtnHasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tbFisioRalan.getSelectedRow()!= -1){
                    if(tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),0).toString().trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        TanggalPulang.setDate(new Date());
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                        WindowAmbilSampel.setLocationRelativeTo(internalFrame1);
                        WindowAmbilSampel.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                }   
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatJalan.setSelectedIndex(0);
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tbFisioRanap.getSelectedRow()!= -1){
                    if(tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),0).toString().trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        TanggalPulang.setDate(new Date());
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                        WindowAmbilSampel.setLocationRelativeTo(internalFrame1);
                        WindowAmbilSampel.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                }   
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatInap.setSelectedIndex(0);
                TCari.requestFocus();
            } 
        }                       
    }//GEN-LAST:event_BtnHasilActionPerformed

    private void BtnHasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHasilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHasilKeyPressed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowAmbilSampel.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(tbFisioRalan.getSelectedRow()!= -1){
                if(tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TanggalPulang,"No.Permintaan");
                }else{
                    if(Sequel.mengedittf("permintaan_fisio","noorder=?","tgl_periksa=?,jam_periksa=?",3,new String[]{
                        Valid.SetTgl(TanggalPulang.getSelectedItem()+""),TanggalPulang.getSelectedItem().toString().substring(11,19),tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),0).toString()
                    })==true){
                        Sequel.queryu("delete from antrifisio");
                        Sequel.queryu("insert into antrifisio values('1')");
                        tbFisioRalan.setValueAt(Valid.SetTgl(TanggalPulang.getSelectedItem()+""),tbFisioRalan.getSelectedRow(),5);
                        tbFisioRalan.setValueAt(TanggalPulang.getSelectedItem().toString().substring(11,19),tbFisioRalan.getSelectedRow(),6);                        
                        WindowAmbilSampel.dispose();
                    }
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(tbFisioRanap.getSelectedRow()!= -1){
                if(tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TanggalPulang,"No.Permintaan");
                }else{
                    if(Sequel.mengedittf("permintaan_fisio","noorder=?","tgl_periksa=?,jam_periksa=?",3,new String[]{
                        Valid.SetTgl(TanggalPulang.getSelectedItem()+""),TanggalPulang.getSelectedItem().toString().substring(11,19),tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),0).toString()
                    })==true){
                        tbFisioRanap.setValueAt(Valid.SetTgl(TanggalPulang.getSelectedItem()+""),tbFisioRanap.getSelectedRow(),5);
                        tbFisioRanap.setValueAt(TanggalPulang.getSelectedItem().toString().substring(11,19),tbFisioRanap.getSelectedRow(),6);
                        WindowAmbilSampel.dispose();
                    }
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }             
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        ruang.isCek();
        ruang.emptTeks();
        ruang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void TabPilihRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPilihRawatMouseClicked
        TeksKosong();
        pilihTab();
    }//GEN-LAST:event_TabPilihRawatMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        aktif=true;
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        aktif=false;
    }//GEN-LAST:event_formWindowDeactivated

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        WindowGanti.dispose();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void BtnSimpan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan5ActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(tbFisioRalan.getSelectedRow()!= -1){
                if(tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{
                    if(Sequel.mengedittf("permintaan_fisio","noorder=?","noorder=?,tgl_permintaan=?",3,new String[]{
                        TNoPermintaanPR.getText(),Valid.SetTgl(TanggalPulang1.getSelectedItem()+""),
//              Jika Mau merubah jam juga
                        //TanggalPulang.getSelectedItem().toString().substring(11,19),
                        tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),0).toString()
                    })==true){
                        WindowGanti.dispose();
                        TeksKosong();
                        tbFisioRalan.setValueAt(TNoPermintaanPR.getText(),tbFisioRalan.getSelectedRow(),0);
                        tbFisioRalan.setValueAt(Valid.SetTgl(TanggalPulang1.getSelectedItem()+""),tbFisioRalan.getSelectedRow(),3);
//              Jika Mau merubah jam juga
                        //tbRadiologiRalan.setValueAt(TanggalPulang1.getSelectedItem().toString().substring(11,19),tbRadiologiRalan.getSelectedRow(),6);
                    }
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(tbFisioRanap.getSelectedRow()!= -1){
                if(tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{
                    if(Sequel.mengedittf("permintaan_fisio","noorder=?","noorder=?,tgl_permintaan=?",3,new String[]{
                        TNoPermintaanPR.getText(),Valid.SetTgl(TanggalPulang1.getSelectedItem()+""),
//              Jika Mau merubah jam juga
                        //TanggalPulang1.getSelectedItem().toString().substring(11,19),
                        tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),0).toString()
                    })==true){
                        WindowGanti.dispose();
                        TeksKosong();
                        tbFisioRalan.setValueAt(TNoPermintaanPR.getText(),tbFisioRalan.getSelectedRow(),0);
                        tbFisioRanap.setValueAt(Valid.SetTgl(TanggalPulang1.getSelectedItem()+""),tbFisioRanap.getSelectedRow(),3);
//              Jika Mau merubah jam juga
                        //tbRadiologiRanap.setValueAt(TanggalPulang1.getSelectedItem().toString().substring(11,19),tbRadiologiRanap.getSelectedRow(),6);
                    }
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }
    }//GEN-LAST:event_BtnSimpan5ActionPerformed

    private void TanggalPulang1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalPulang1ItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalPulang1ItemStateChanged

    private void TanggalPulang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalPulang1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPulang1ActionPerformed

    private void TanggalPulang1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPulang1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPulang1KeyPressed

    private void TNoPermintaanPRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoPermintaanPRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoPermintaanPRActionPerformed

    private void TNoPermintaanPRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPermintaanPRKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoPermintaanPRKeyPressed

    private void TabRawatInapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatInapMouseClicked
        TeksKosong();
        pilihRanap();
    }//GEN-LAST:event_TabRawatInapMouseClicked

    private void tbFisioRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFisioRanapKeyPressed
        if(tabMode3.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbFisioRanapKeyPressed

    private void tbFisioRanapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFisioRanapMouseClicked
        if(tabMode3.getRowCount()!=0){
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFisioRanapMouseClicked

    private void TabRawatJalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatJalanMouseClicked
        TeksKosong();
        pilihRalan();
    }//GEN-LAST:event_TabRawatJalanMouseClicked

    private void tbFisioRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFisioRalanKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbFisioRalanKeyPressed

    private void tbFisioRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFisioRalanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFisioRalanMouseClicked

    private void tbFisioRanap2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFisioRanap2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbFisioRanap2KeyPressed

    private void tbFisioRanap2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFisioRanap2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbFisioRanap2MouseClicked

    private void tbFisioRalan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFisioRalan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbFisioRalan2KeyPressed

    private void tbFisioRalan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFisioRalan2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbFisioRalan2MouseClicked

    private void listDokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listDokMouseClicked

    }//GEN-LAST:event_listDokMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPermintaanFisio dialog = new DlgCariPermintaanFisio(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnHapus;
    private widget.Button BtnHasil;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpan5;
    private widget.TextBox CrDokter;
    private widget.TextBox CrDokter2;
    private widget.TextBox CrPoli;
    private widget.TextBox Kamar;
    private widget.Label LCount;
    private widget.TextBox TCari;
    private widget.TextBox TNoPermintaanPR;
    private javax.swing.JTabbedPane TabPilihRawat;
    private javax.swing.JTabbedPane TabRawatInap;
    private javax.swing.JTabbedPane TabRawatJalan;
    private widget.Tanggal TanggalPulang;
    private widget.Tanggal TanggalPulang1;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.JDialog WindowAmbilSampel;
    private javax.swing.JDialog WindowGanti;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel10;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private javax.swing.JPanel jPanel2;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.Label label30;
    private javax.swing.JComboBox<String> listDok;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.Table tbFisioRalan;
    private widget.Table tbFisioRalan2;
    private widget.Table tbFisioRanap;
    private widget.Table tbFisioRanap2;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            semua=CrDokter.getText().trim().equals("")&&CrPoli.getText().trim().equals("")&&TCari.getText().trim().equals("");
            ps=koneksi.prepareStatement(
                    "select permintaan_fisio.noorder,permintaan_fisio.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,permintaan_fisio.tgl_permintaan,"+
                    "if(permintaan_fisio.jam_permintaan='00:00:00','',permintaan_fisio.jam_permintaan) as jam_permintaan,reg_periksa.kd_pj,penjab.png_jawab,"+
                    "if(permintaan_fisio.tgl_periksa='0000-00-00','',permintaan_fisio.tgl_periksa) as tgl_periksa,if(permintaan_fisio.jam_periksa='00:00:00','',permintaan_fisio.jam_periksa) as jam_periksa,"+                    
                    "permintaan_fisio.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli,permintaan_fisio.informasi_tambahan,permintaan_fisio.diagnosa_klinis "+
                    "from permintaan_fisio inner join reg_periksa on permintaan_fisio.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on permintaan_fisio.dokter_perujuk=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where permintaan_fisio.status='ralan' and permintaan_fisio.tgl_permintaan between ? and ? "+
                    (semua?"":"and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and "+
                    "(permintaan_fisio.noorder like ? or permintaan_fisio.no_rawat like ? or reg_periksa.no_rkm_medis like ? "+
                    "or pasien.nm_pasien like ? or permintaan_fisio.diagnosa_klinis like ? or dokter.nm_dokter like ? or penjab.png_jawab like ?) ")+
                    "order by permintaan_fisio.tgl_permintaan,permintaan_fisio.jam_permintaan desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter.getText().trim()+"%");
                    ps.setString(4,"%"+CrPoli.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
//                    ps.setString(16,"%"+TCari.getText()+"%");
                } 
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+
                        rs.getString("nm_pasien")+" ("+rs.getString("tgl_lahir")+") ",rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_periksa"),rs.getString("jam_periksa"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),
                        rs.getString("nm_poli"),rs.getString("informasi_tambahan"),rs.getString("diagnosa_klinis"),
                        rs.getString("kd_pj"),rs.getString("png_jawab")
                    });
                    ps2=koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_fisio.kd_jenis_prw,jns_perawatan.nm_perawatan "+
                            "from permintaan_pemeriksaan_fisio inner join jns_perawatan on "+
                            "permintaan_pemeriksaan_fisio.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                            "where permintaan_pemeriksaan_fisio.noorder=?");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                "","",rs2.getString("nm_perawatan"),"","","","","","","","","","","",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }
                rs.last();
                LCount.setText(""+rs.getRow());
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
    
    private void tampil2() {
        Valid.tabelKosong(tabMode2);  
        try {
            semua=CrDokter.getText().trim().equals("")&&CrPoli.getText().trim().equals("")&&TCari.getText().trim().equals("");
            ps=koneksi.prepareStatement(
                    "select permintaan_fisio.noorder,permintaan_fisio.no_rawat,reg_periksa.no_rkm_medis,"+
                    "pasien.nm_pasien,pasien.tgl_lahir,jns_perawatan.nm_perawatan,permintaan_fisio.tgl_permintaan,reg_periksa.kd_pj,penjab.png_jawab,"+
                    "if(permintaan_fisio.jam_permintaan='00:00:00','',permintaan_fisio.jam_permintaan) as jam_permintaan,permintaan_fisio.tgl_periksa,"+
                    "if(permintaan_fisio.jam_periksa='00:00:00','',permintaan_fisio.jam_periksa) as jam_periksa,"+                    
                    "permintaan_fisio.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli,permintaan_fisio.informasi_tambahan,permintaan_fisio.diagnosa_klinis "+
                    "from permintaan_fisio inner join reg_periksa on permintaan_fisio.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join permintaan_pemeriksaan_fisio on permintaan_fisio.noorder=permintaan_pemeriksaan_fisio.noorder "+
                    "inner join jns_perawatan on jns_perawatan.kd_jenis_prw=permintaan_pemeriksaan_fisio.kd_jenis_prw "+
                    "inner join dokter on permintaan_fisio.dokter_perujuk=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli  "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where permintaan_fisio.status='ralan' and permintaan_fisio.tgl_permintaan between ? and ? "+
                    (semua?"":"and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and "+
                    "(permintaan_fisio.noorder like ? or permintaan_fisio.no_rawat like ? or "+
                    "reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                    "permintaan_fisio.diagnosa_klinis like ? or jns_perawatan.nm_perawatan like ? "+
                    "or dokter.nm_dokter like ? or penjab.png_jawab like ?)")+" order by permintaan_fisio.tgl_permintaan,permintaan_fisio.jam_permintaan desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter.getText().trim()+"%");
                    ps.setString(4,"%"+CrPoli.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode2.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("tgl_lahir")+") ",
                        rs.getString("nm_perawatan"),rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_periksa"),rs.getString("jam_periksa"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),
                        rs.getString("nm_poli"),rs.getString("informasi_tambahan"),rs.getString("diagnosa_klinis"),
                        rs.getString("kd_pj"),rs.getString("png_jawab")
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    
    private void getData() {
        if(tbFisioRalan.getSelectedRow()!= -1){
            NoPermintaan=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),0).toString();
            NoRawat=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),1).toString();
            Pasien=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),2).toString();
            Permintaan=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),3).toString();
            JamPermintaan=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),4).toString();
            Sampel=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),5).toString();
            JamSampel=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),6).toString();
//            Hasil=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),7).toString();
//            JamHasil=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),8).toString();
            KodeDokter=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),7).toString();
            DokterPerujuk=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),8).toString();
            Ruang=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),9).toString();
            InformasiTambahan=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),10).toString();
            Klinis=tbFisioRalan.getValueAt(tbFisioRalan.getSelectedRow(),11).toString();
        }
    }
    
    private void getData2() {
        if(tbFisioRanap.getSelectedRow()!= -1){
            NoPermintaan=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),0).toString();
            NoRawat=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),1).toString();
            Pasien=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),2).toString();
            Permintaan=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),3).toString();
            JamPermintaan=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),4).toString();
            Sampel=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),5).toString();
            JamSampel=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),6).toString();
//            Hasil=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),7).toString();
//            JamHasil=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),8).toString();
            KodeDokter=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),7).toString();
            DokterPerujuk=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),8).toString();
            Ruang=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),9).toString();
            InformasiTambahan=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),10).toString();
            Klinis=tbFisioRanap.getValueAt(tbFisioRanap.getSelectedRow(),11).toString();
        }
    }
    
    public void isCek(){
        BtnHasil.setEnabled(akses.getperiksa_radiologi());
        BtnHapus.setEnabled(akses.getpermintaan_radiologi());
        BtnPrint.setEnabled(akses.getpermintaan_radiologi());
    }
    
    public void setPasien(String pasien){
        TCari.setText(pasien);
    }

    public void pilihTab(){
        if(TabPilihRawat.getSelectedIndex()==0){
            pilihRalan();
        }else if(TabPilihRawat.getSelectedIndex()==1){
            pilihRanap();
        }
    }
    
    public void pilihRalan(){
        if(TabRawatJalan.getSelectedIndex()==0){
            tampil();
        }else if(TabRawatJalan.getSelectedIndex()==1){
            tampil2();
        }
    }
    
    public void pilihRanap(){
        if(TabRawatInap.getSelectedIndex()==0){
            tampil3();
        }else if(TabRawatInap.getSelectedIndex()==1){
            tampil4();
        }
    }
    
    private void tampil3() {
        Valid.tabelKosong(tabMode3);
        try {
            semua=CrDokter2.getText().trim().equals("")&&Kamar.getText().trim().equals("")&&TCari.getText().trim().equals("");
            if(cmbStatus.getSelectedIndex()==0){
                ps=koneksi.prepareStatement(
                        "select permintaan_fisio.noorder,permintaan_fisio.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,permintaan_fisio.tgl_permintaan,"+
                        "if(permintaan_fisio.jam_permintaan='00:00:00','',permintaan_fisio.jam_permintaan) as jam_permintaan,reg_periksa.kd_pj,penjab.png_jawab,"+
                        "if(permintaan_fisio.tgl_periksa='0000-00-00','',permintaan_fisio.tgl_periksa) as tgl_periksa,if(permintaan_fisio.jam_periksa='00:00:00','',permintaan_fisio.jam_periksa) as jam_periksa,"+
                        "permintaan_fisio.dokter_perujuk,dokter.nm_dokter,ifnull(bangsal.nm_bangsal,'Ranap Gabung') as nm_bangsal,permintaan_fisio.informasi_tambahan,permintaan_fisio.diagnosa_klinis "+
                        "from permintaan_fisio inner join reg_periksa on permintaan_fisio.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join dokter on permintaan_fisio.dokter_perujuk=dokter.kd_dokter "+
                        "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "where permintaan_fisio.status='ranap' and permintaan_fisio.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_fisio.noorder like ? or permintaan_fisio.no_rawat like ? or "+
                        "reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "permintaan_fisio.diagnosa_klinis like ? or dokter.nm_dokter like ? or penjab.png_jawab like ?)")+
                        "group by permintaan_fisio.noorder order by permintaan_fisio.tgl_permintaan desc,permintaan_fisio.jam_permintaan desc,kamar_inap.tgl_masuk desc");
            }else{
                ps=koneksi.prepareStatement(
                        "select permintaan_fisio.noorder,permintaan_fisio.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,permintaan_fisio.tgl_permintaan,"+
                        "if(permintaan_fisio.jam_permintaan='00:00:00','',permintaan_fisio.jam_permintaan) as jam_permintaan,reg_periksa.kd_pj,penjab.png_jawab,"+
                        "if(permintaan_fisio.tgl_periksa='0000-00-00','',permintaan_fisio.tgl_periksa) as tgl_periksa,if(permintaan_fisio.jam_periksa='00:00:00','',permintaan_fisio.jam_periksa) as jam_periksa,"+
                        "permintaan_fisio.dokter_perujuk,dokter.nm_dokter,ifnull(bangsal.nm_bangsal,'Ranap Gabung') as nm_bangsal,permintaan_fisio.informasi_tambahan,permintaan_fisio.diagnosa_klinis "+
                        "from permintaan_fisio inner join reg_periksa on permintaan_fisio.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join dokter on permintaan_fisio.dokter_perujuk=dokter.kd_dokter "+
                        "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "where permintaan_fisio.status='ranap' and kamar_inap.stts_pulang='-' and permintaan_fisio.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_fisio.noorder like ? or permintaan_fisio.no_rawat like ? or "+
                        "reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "permintaan_fisio.diagnosa_klinis like ? or dokter.nm_dokter like ? or penjab.png_jawab like ?)")+
                        "group by permintaan_fisio.noorder order by permintaan_fisio.tgl_permintaan desc,permintaan_fisio.jam_permintaan desc,kamar_inap.tgl_masuk desc");
            }
                
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                    ps.setString(4,"%"+Kamar.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode3.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+
                        rs.getString("nm_pasien")+" ("+rs.getString("tgl_lahir")+") ",rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_periksa"),rs.getString("jam_periksa"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),
                        rs.getString("nm_bangsal"),rs.getString("informasi_tambahan"),rs.getString("diagnosa_klinis"),
                        rs.getString("kd_pj"),rs.getString("png_jawab")
                    });
                    ps2=koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_fisio.kd_jenis_prw,jns_perawatan.nm_perawatan "+
                            "from permintaan_pemeriksaan_fisio inner join jns_perawatan on "+
                            "permintaan_pemeriksaan_fisio.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                            "where permintaan_pemeriksaan_fisio.noorder=?");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode3.addRow(new Object[]{
                                "","",rs2.getString("nm_perawatan"),"","","","","","","","","","",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }
                rs.last();
                LCount.setText(""+rs.getRow());
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
    
    private void tampil4() {
        Valid.tabelKosong(tabMode4);  
        try {
            semua=CrDokter2.getText().trim().equals("")&&Kamar.getText().trim().equals("")&&TCari.getText().trim().equals("");
            if(cmbStatus.getSelectedIndex()==0){
                ps=koneksi.prepareStatement(
                        "select permintaan_fisio.noorder,permintaan_fisio.no_rawat,reg_periksa.no_rkm_medis,"+
                        "pasien.nm_pasien,pasien.tgl_lahir,jns_perawatan.nm_perawatan,permintaan_fisio.tgl_permintaan,reg_periksa.kd_pj,penjab.png_jawab,"+
                        "if(permintaan_fisio.jam_permintaan='00:00:00','',permintaan_fisio.jam_permintaan) as jam_permintaan,permintaan_fisio.tgl_periksa,"+
                        "if(permintaan_fisio.jam_periksa='00:00:00','',permintaan_fisio.jam_periksa) as jam_periksa, "+
                        "permintaan_fisio.dokter_perujuk,dokter.nm_dokter,ifnull(bangsal.nm_bangsal,'Ranap Gabung') as nm_bangsal,permintaan_fisio.informasi_tambahan,permintaan_fisio.diagnosa_klinis from permintaan_fisio "+
                        "inner join reg_periksa on permintaan_fisio.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join permintaan_pemeriksaan_fisio on permintaan_fisio.noorder=permintaan_pemeriksaan_fisio.noorder "+
                        "inner join jns_perawatan on jns_perawatan.kd_jenis_prw=permintaan_pemeriksaan_fisio.kd_jenis_prw "+
                        "inner join dokter on permintaan_fisio.dokter_perujuk=dokter.kd_dokter "+
                        "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar  "+
                        "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "where permintaan_fisio.status='ranap' and permintaan_fisio.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_fisio.noorder like ? or permintaan_fisio.no_rawat like ? or "+
                        "reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "permintaan_fisio.diagnosa_klinis like ? or jns_perawatan.nm_perawatan like ? or "+
                        "dokter.nm_dokter like ? or penjab.png_jawab like ?)")+" group by permintaan_fisio.noorder,jns_perawatan.kd_jenis_prw order by permintaan_fisio.tgl_permintaan desc,permintaan_fisio.jam_permintaan desc,kamar_inap.tgl_masuk desc");
            }else{
                ps=koneksi.prepareStatement(
                        "select permintaan_fisio.noorder,permintaan_fisio.no_rawat,reg_periksa.no_rkm_medis,"+
                        "pasien.nm_pasien,pasien.tgl_lahir,jns_perawatan.nm_perawatan,permintaan_fisio.tgl_permintaan,reg_periksa.kd_pj,penjab.png_jawab,"+
                        "if(permintaan_fisio.jam_permintaan='00:00:00','',permintaan_fisio.jam_permintaan) as jam_permintaan,permintaan_fisio.tgl_periksa,"+
                        "if(permintaan_fisio.jam_periksa='00:00:00','',permintaan_fisio.jam_periksa) as jam_periksa, "+
                        "permintaan_fisio.dokter_perujuk,dokter.nm_dokter,ifnull(bangsal.nm_bangsal,'Ranap Gabung') as nm_bangsal,permintaan_fisio.informasi_tambahan,permintaan_fisio.diagnosa_klinis from permintaan_fisio "+
                        "inner join reg_periksa on permintaan_fisio.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join permintaan_pemeriksaan_fisio on permintaan_fisio.noorder=permintaan_pemeriksaan_fisio.noorder "+
                        "inner join jns_perawatan on jns_perawatan.kd_jenis_prw=permintaan_pemeriksaan_fisio.kd_jenis_prw "+
                        "inner join dokter on permintaan_fisio.dokter_perujuk=dokter.kd_dokter "+
                        "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar  "+
                        "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "where permintaan_fisio.status='ranap' and kamar_inap.stts_pulang='-' and permintaan_fisio.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_fisio.noorder like ? or permintaan_fisio.no_rawat like ? or "+
                        "reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "permintaan_fisio.diagnosa_klinis like ? or jns_perawatan.nm_perawatan like ? or "+
                        "dokter.nm_dokter like ? or penjab.png_jawab like ?)")+" group by permintaan_fisio.noorder,jns_perawatan.kd_jenis_prw order by permintaan_fisio.tgl_permintaan desc,permintaan_fisio.jam_permintaan desc,kamar_inap.tgl_masuk desc");
            }
                
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                    ps.setString(4,"%"+Kamar.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode4.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("tgl_lahir")+") ",
                        rs.getString("nm_perawatan"),rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_periksa"),rs.getString("jam_periksa"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),
                        rs.getString("nm_bangsal"),rs.getString("informasi_tambahan"),rs.getString("diagnosa_klinis"),
                        rs.getString("kd_pj"),rs.getString("png_jawab")
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
            LCount.setText(""+tabMode4.getRowCount());
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    private void jam(){
        ActionListener taskPerformer = (ActionEvent e) -> {
            if(aktif==true){
                nol_detik = "";
                now = Calendar.getInstance().getTime();
                nilai_detik = now.getSeconds();
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }

                detik = nol_detik + Integer.toString(nilai_detik);
                if(detik.equals("05")){
                    permintaanbaru=0;
                    if(formalarm.contains("ralan")){
                        tampil();
                        for(i=0;i<tbFisioRalan.getRowCount();i++){
                            if((!tbFisioRalan.getValueAt(i,0).toString().equals(""))&&tbFisioRalan.getValueAt(i,16).toString().equals("Cito")){
                                permintaanbaru++;
                            }
                        }
                    }

                    if(formalarm.contains("ranap")){
                        tampil3();
                        for(i=0;i<tbFisioRanap.getRowCount();i++){
                            if((!tbFisioRanap.getValueAt(i,0).toString().equals(""))&&tbFisioRanap.getValueAt(i,5).toString().equals("")){
                                permintaanbaru++;
                            }
                        }
                    }

                    if(permintaanbaru>0){
                        try {
                            music = new BackgroundMusic("./suara/alarm.mp3");
                            music.start();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
 
    private void TeksKosong() {
        NoPermintaan="";
        NoRawat="";
        Pasien="";
        Permintaan="";
        JamPermintaan="";
        Sampel="";
        JamSampel="";
        Hasil="";
        JamHasil="";
        KodeDokter="";
        DokterPerujuk="";
        Ruang="";
        InformasiTambahan="";
        Klinis="";
    }
        
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_fisio.noorder,4),signed)),0) from permintaan_fisio where permintaan_fisio.tgl_permintaan='"+Valid.SetTgl(TanggalPulang1.getSelectedItem()+"")+"' ","PR"+Valid.SetTgl(TanggalPulang1.getSelectedItem()+"").replaceAll("-",""),4,TNoPermintaanPR);           
    }
    
    public void emptTeks() {
        TCari.setText("");
        TanggalPulang1.setDate(new Date());
        autoNomor();
        
    }
    
    private void listDokter(){
       try{
            ps=koneksi.prepareStatement("SELECT nip,nama FROM petugas WHERE petugas.status='1' and petugas.kd_jbtn='20' ORDER BY nama asc");
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                 listDok.addItem(rs.getString("nama"));
            }               
            rs.close();                
        }

        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
