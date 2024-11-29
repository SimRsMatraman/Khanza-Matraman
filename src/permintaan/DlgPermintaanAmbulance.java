package permintaan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import kepegawaian.DlgCariPetugas;
import laporan.LaporanSisaDietPasien;
import setting.DlgCariJamDiet;

/**
 *
 * @author dosen
 */
public class DlgPermintaanAmbulance extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private PreparedStatement ps,ps2;
    private ResultSet rs;
    private int i=0,pilih=0;

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgPermintaanAmbulance(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","Nama Pasien","Umur","Alamat","No. Kartu","Kamar","Tanggal","RS Tujuan","Diagnosa","Petugas","Kode Petugas"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataDiet.setModel(tabMode);
        tbDataDiet.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataDiet.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbDataDiet.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(230);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(230);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(155);
            }else if(i==8){
                column.setPreferredWidth(160);
            }else if(i==9){
                column.setPreferredWidth(160);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDataDiet.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2=new DefaultTableModel(null,new Object[]{
                "No","Nama Diet","Jumlah Diet"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KdPetugas.setDocument(new batasInput((byte)3).getKata(KdPetugas));
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
        
        ChkInput.setSelected(false);
        isForm();
        
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
        Ruang = new widget.TextBox();
        Diagnosa = new widget.TextBox();
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
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        jLabel9 = new widget.Label();
        NmPetugas = new widget.TextBox();
        TPasien = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        jLabel10 = new widget.Label();
        KdPetugas = new widget.TextBox();
        Kamar = new widget.TextBox();
        jLabel14 = new widget.Label();
        NoKartu = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        scrollPane = new widget.ScrollPane();
        Alamat = new widget.TextArea();
        RsTujuan = new widget.TextBox();
        Penyakit = new widget.TextBox();
        BtnDokter = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbDataDiet = new widget.Table();

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

        Ruang.setEditable(false);
        Ruang.setHighlighter(null);
        Ruang.setName("Ruang"); // NOI18N

        Diagnosa.setEditable(false);
        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Permintaan Penggunaan Ambulance ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-11-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-11-2024" }));
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

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        Rujuk.add(jLabel4);
        jLabel4.setBounds(0, 12, 74, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        Rujuk.add(TNoRw);
        TNoRw.setBounds(78, 12, 125, 23);

        jLabel9.setText("Petugas :");
        jLabel9.setName("jLabel9"); // NOI18N
        Rujuk.add(jLabel9);
        jLabel9.setBounds(0, 40, 70, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setHighlighter(null);
        NmPetugas.setName("NmPetugas"); // NOI18N
        Rujuk.add(NmPetugas);
        NmPetugas.setBounds(170, 40, 212, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        Rujuk.add(TPasien);
        TPasien.setBounds(205, 12, 290, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-11-2024" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        Rujuk.add(DTPTgl);
        DTPTgl.setBounds(510, 40, 90, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        Rujuk.add(jLabel10);
        jLabel10.setBounds(430, 40, 74, 23);

        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        Rujuk.add(KdPetugas);
        KdPetugas.setBounds(80, 40, 85, 23);

        Kamar.setEditable(false);
        Kamar.setHighlighter(null);
        Kamar.setName("Kamar"); // NOI18N
        Rujuk.add(Kamar);
        Kamar.setBounds(497, 12, 243, 23);

        jLabel14.setText("Diagnosa :");
        jLabel14.setName("jLabel14"); // NOI18N
        Rujuk.add(jLabel14);
        jLabel14.setBounds(0, 70, 70, 20);

        NoKartu.setEditable(false);
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N
        Rujuk.add(NoKartu);
        NoKartu.setBounds(370, 70, 300, 24);

        jLabel15.setText("No. Kartu :");
        jLabel15.setName("jLabel15"); // NOI18N
        Rujuk.add(jLabel15);
        jLabel15.setBounds(300, 70, 60, 20);

        jLabel16.setText("RS Rujukan :");
        jLabel16.setName("jLabel16"); // NOI18N
        Rujuk.add(jLabel16);
        jLabel16.setBounds(440, 100, 70, 20);

        jLabel17.setText("Alamat :");
        jLabel17.setName("jLabel17"); // NOI18N
        Rujuk.add(jLabel17);
        jLabel17.setBounds(0, 100, 70, 20);

        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane.setName("scrollPane"); // NOI18N

        Alamat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Alamat.setColumns(20);
        Alamat.setRows(5);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        scrollPane.setViewportView(Alamat);

        Rujuk.add(scrollPane);
        scrollPane.setBounds(81, 100, 350, 50);

        RsTujuan.setHighlighter(null);
        RsTujuan.setName("RsTujuan"); // NOI18N
        RsTujuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RsTujuanKeyPressed(evt);
            }
        });
        Rujuk.add(RsTujuan);
        RsTujuan.setBounds(520, 100, 220, 24);

        Penyakit.setHighlighter(null);
        Penyakit.setName("Penyakit"); // NOI18N
        Penyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKeyPressed(evt);
            }
        });
        Rujuk.add(Penyakit);
        Penyakit.setBounds(80, 70, 220, 24);

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
        BtnDokter.setBounds(388, 40, 30, 23);

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

        TabRawat.addTab("Data Permintaan", Scroll);

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
                if(Sequel.menyimpantf("detail_permintaan_ambulance","'"+TNoRw.getText()+"','"+Kamar.getText()+"','"+
                        Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"','"+
                        RsTujuan.getText()+"','"+
                        KdPetugas.getText()+"','"+
                        "'","data")==true){
                    tabMode.addRow(new String[]{
//                        TNoRw.getText(),TPasien.getText(),Ruang.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),Penyakit.getText(),NmPetugas.getText(),"-",Kamar.getText(),KdPetugas.getText()
                    });
                    LCount.setText(""+tabMode.getRowCount());
                    emptTeks();
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan buka data diet pasien");
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                DTPTgl.requestFocus();
            }else if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(TPasien.getText().trim().equals(""))){
                if(tbDataDiet.getSelectedRow()!= -1){
                    if(Sequel.queryutf("delete from detail_permintaan_ambulance " +
                            "where no_rawat='"+TNoRw.getText()+"' " +
                            "and tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' " +
                            "and rstujuan='"+RsTujuan.getText()+"'")==true){
                        tabMode.removeRow(tbDataDiet.getSelectedRow());
                        LCount.setText(""+tabMode.getRowCount());
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan buka data diet pasien");
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
       if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
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
//        NmBangsalCari.setText("");
        KdPetugas.setText("");
        NmPetugas.setText("");
//        JamDiet2.setText("");
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
        }
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbDataDietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataDietMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDataDietMouseClicked

    private void tbDataDietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataDietKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
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
                "SELECT a.no_rawat, c.no_rkm_medis, c.nm_pasien, c.umur, c.no_peserta, concat(c.alamat, ', ', e.nm_kel, ', ', f.nm_kec, ', ', g.nm_kab, ' - ', h.nm_prop) as alamat, d.diagnosa_awal, a.tanggal, a.rstujuan, a.kd_kamar, a.nip, j.nama " +
                "FROM detail_permintaan_ambulance a "+
                "INNER JOIN reg_periksa b on b.no_rawat=a.no_rawat "+
                "INNER JOIN pasien c on c.no_rkm_medis=b.no_rkm_medis " +
                "INNER JOIN kamar_inap d on d.no_rawat=a.no_rawat "+
                "INNER JOIN kelurahan e on e.kd_kel=c.kd_kel "+
                "INNER JOIN kecamatan f on f.kd_kec=c.kd_kec "+
                "INNER JOIN kabupaten g on g.kd_kab=c.kd_kab  "+
                "INNER JOIN propinsi h on h.kd_prop=c.kd_prop "+
                "INNER JOIN kamar_inap i on i.no_rawat=a.no_rawat "+
                "INNER JOIN petugas j on j.nip=a.nip "+
                "where a.tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' "+
                "and a.no_rawat='"+TNoRw.getText()+"' and a.rstujuan='"+RsTujuan.getText()+"'",param);
        }
    }//GEN-LAST:event_MnPermintaanAmbulanceActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
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

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPermintaanAmbulance dialog = new DlgPermintaanAmbulance(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.TextBox Diagnosa;
    private widget.TextBox Kamar;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnPermintaanAmbulance;
    private widget.TextBox NmPetugas;
    private widget.TextBox NoKartu;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Penyakit;
    private widget.TextBox RsTujuan;
    private widget.TextBox Ruang;
    private widget.PanelBiasa Rujuk;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollPane;
    private widget.Table tbDataDiet;
    // End of variables declaration//GEN-END:variables

    public void tampil() {   
        try{
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                "SELECT a.no_rawat, c.nm_pasien, c.umur, c.no_peserta, concat(c.alamat, ', ', e.nm_kel, ', ', f.nm_kec, ', ', g.nm_kab, ' - ', h.nm_prop) as alamat, d.diagnosa_awal, a.tanggal, a.rstujuan, a.kd_kamar, a.nip, j.nama " +
                "FROM detail_permintaan_ambulance a "+
                "INNER JOIN reg_periksa b on b.no_rawat=a.no_rawat "+
                "INNER JOIN pasien c on c.no_rkm_medis=b.no_rkm_medis " +
                "INNER JOIN kamar_inap d on d.no_rawat=a.no_rawat "+
                "INNER JOIN kelurahan e on e.kd_kel=c.kd_kel "+
                "INNER JOIN kecamatan f on f.kd_kec=c.kd_kec "+
                "INNER JOIN kabupaten g on g.kd_kab=c.kd_kab  "+
                "INNER JOIN propinsi h on h.kd_prop=c.kd_prop "+
                "INNER JOIN kamar_inap i on i.no_rawat=a.no_rawat "+
                "INNER JOIN petugas j on j.nip=a.nip "+
                "WHERE a.tanggal between ? and ? "+
                (TCari.getText().trim().equals("")?"":"and (a.no_rawat like ? or b.no_rkm_medis like ? or c.nm_pasien like ?) ")+
                "order by a.tanggal,a.rstujuan");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString(1),rs.getString(2),rs.getString("umur"),rs.getString("alamat"),rs.getString("no_peserta"),rs.getString("kd_kamar"),rs.getString("tanggal"),rs.getString("rstujuan"),
                        rs.getString("diagnosa_awal"),rs.getString("nama"),rs.getString("nip")
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
            LCount.setText(""+tabMode.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }


    public void emptTeks() {
        KdPetugas.setText("");
        NmPetugas.setText("");
        Penyakit.setText("");
        Alamat.setText("");
        NoKartu.setText("");
        RsTujuan.setText("");
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
    }

    private void getData() {
        if(tbDataDiet.getSelectedRow()!= -1){
            TNoRw.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),0).toString()); 
            TPasien.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),1).toString());    
            Alamat.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),3).toString());
            NoKartu.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),4).toString());          
            Kamar.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),5).toString());
            RsTujuan.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),7).toString());
            Penyakit.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),8).toString());
            NmPetugas.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),9).toString());
            KdPetugas.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),10).toString());
            Valid.SetTgl(DTPTgl,tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),6).toString());
        }
    }
    
    private void isRawat() {
         Sequel.cariIsi("select pasien.nm_pasien from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
         Sequel.cariIsi("select kamar_inap.kd_kamar from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1",Kamar,TNoRw.getText());
    }

    public void setNoRm(String norwt,String pasien,String kamar,String bangsal,String diagnosa,Date tgl1,Date tgl2) {
        TNoRw.setText(norwt);
        TPasien.setText(pasien);
        Kamar.setText(kamar);
        Ruang.setText(bangsal);
        Diagnosa.setText(diagnosa);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        ChkInput.setSelected(true);
        Sequel.cariIsi("select pasien.no_peserta from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",NoKartu,TNoRw.getText());
        Sequel.cariIsi("select concat(pasien.alamat, ', ', kelurahan.nm_kel, ', ', kecamatan.nm_kec, ', ', kabupaten.nm_kab, ' - ', propinsi.nm_prop) as alamat from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis inner join kelurahan on kelurahan.kd_kel=pasien.kd_kel inner join kecamatan on kecamatan.kd_kec=pasien.kd_kec inner join kabupaten on kabupaten.kd_kab=pasien.kd_kab  inner join propinsi on propinsi.kd_prop=pasien.kd_prop where reg_periksa.no_rawat=? ",Alamat,TNoRw.getText());
//        Sequel.cariIsi("select concat(diagnosa_pasien.kd_penyakit, ' - ', penyakit.nm_penyakit) as penyakit FROM reg_periksa INNER JOIN diagnosa_pasien on diagnosa_pasien.no_rawat=reg_periksa.no_rawat INNER JOIN penyakit on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit where reg_periksa.no_rawat=? ",Penyakit,TNoRw.getText());
        Sequel.cariIsi("select diagnosa_awal from kamar_inap where kamar_inap.no_rawat=? ",Penyakit,TNoRw.getText());
        isForm();
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_anak());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_anak());
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ranap());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ranap());
    }
}
