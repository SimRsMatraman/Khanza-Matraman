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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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


/**
 *
 * @author perpustakaan
 */
public final class RMDataTerapiCairan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMDataTerapiCairan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLama();
            }
        };

        Jam1.addActionListener(listener);
        Menit1.addActionListener(listener);
        Detik1.addActionListener(listener);
        Jam2.addActionListener(listener);
        Menit2.addActionListener(listener);
        Detik2.addActionListener(listener);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Umur","JK","Tgl.Lahir","Tanggal","Jam","Jenis",
            "Volume","Tetesan","Jam Mulai","Jam Selesai","Lama","Obat","NIP Petugas",
            "Nama Petugas","Keterangan","NIP Verifikator","Nama Verifikator"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 20; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(35);
            }else if(i==4){
                column.setPreferredWidth(20);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(60);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(160);
            }else if(i==15){
                column.setPreferredWidth(60);
            }else if(i==16){
                column.setPreferredWidth(200);
            }else if(i==17){
                column.setPreferredWidth(200);
            }else if(i==18){
                column.setPreferredWidth(60);
            }else if(i==19){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
        Infus.setDocument(new batasInput((byte)4).getOnlyAngka(Infus));
        Oral.setDocument(new batasInput((byte)4).getOnlyAngka(Oral));
        BAB.setDocument(new batasInput((byte)4).getOnlyAngka(BAB));
        BAK.setDocument(new batasInput((byte)4).getOnlyAngka(BAK));
        Drain.setDocument(new batasInput((byte)4).getOnlyAngka(Drain));
        IWL.setDocument(new batasInput((byte)4).getOnlyAngka(IWL));
        CairanMasuk.setDocument(new batasInput((byte)4).getOnlyAngka(CairanMasuk));
        Keterangan.setDocument(new batasInput((int)200).getKata(Keterangan));
        
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
        
        tabModeMasalah=new DefaultTableModel(null,new Object[]{
                "No Rawat","JAM","KESADARAN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDaftarCairan.setModel(tabModeMasalah);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDaftarCairan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDaftarCairan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 3; i++) {
            TableColumn column = tbDaftarCairan.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(330);
            }
        }
        tbDaftarCairan.setDefaultRenderer(Object.class, new WarnaTable());
        
        ChkInput.setSelected(false);
        isForm();
        jam();
        jam1();
        jam2();
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCatatanKeseimbanganCairan = new javax.swing.JMenuItem();
        JK = new widget.TextBox();
        Umur = new widget.TextBox();
        TanggalRegistrasi = new widget.TextBox();
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
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel12 = new widget.Label();
        Infus = new widget.TextBox();
        jLabel23 = new widget.Label();
        Oral = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel22 = new widget.Label();
        Drain = new widget.TextBox();
        BAK = new widget.TextBox();
        jLabel24 = new widget.Label();
        BAB = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel25 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel26 = new widget.Label();
        IWL = new widget.TextBox();
        CairanMasuk = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel29 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        Obat = new widget.TextArea();
        Jenis = new widget.ComboBox();
        jLabel14 = new widget.Label();
        Volume = new widget.TextBox();
        jLabel15 = new widget.Label();
        Tetesan = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        Lama = new widget.TextBox();
        jLabel34 = new widget.Label();
        Jam1 = new widget.ComboBox();
        Menit1 = new widget.ComboBox();
        Detik1 = new widget.ComboBox();
        ChkJam = new widget.CekBox();
        jLabel35 = new widget.Label();
        Jam2 = new widget.ComboBox();
        Menit2 = new widget.ComboBox();
        Detik2 = new widget.ComboBox();
        ChkJam1 = new widget.CekBox();
        jLabel36 = new widget.Label();
        NIP1 = new widget.TextBox();
        NamaPetugas1 = new widget.TextBox();
        btnPetugas1 = new widget.Button();
        JamTerapi = new widget.ComboBox();
        jLabel37 = new widget.Label();
        Kesadaran = new widget.TextBox();
        jLabel38 = new widget.Label();
        Muntah = new widget.TextBox();
        jLabel39 = new widget.Label();
        PERDRH = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        CairanKeluar = new widget.TextBox();
        jLabel42 = new widget.Label();
        Balance = new widget.TextBox();
        Deurisis = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel28 = new widget.Label();
        NGT = new widget.TextBox();
        Scroll6 = new widget.ScrollPane();
        tbDaftarCairan = new widget.Table();
        BtnSimpan1 = new widget.Button();
        BtnEdit1 = new widget.Button();
        BtnHapus1 = new widget.Button();
        BtnBatal1 = new widget.Button();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCatatanKeseimbanganCairan.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanKeseimbanganCairan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanKeseimbanganCairan.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanKeseimbanganCairan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanKeseimbanganCairan.setText("Formulir Catatan Keseimbangan Cairan");
        MnCatatanKeseimbanganCairan.setName("MnCatatanKeseimbanganCairan"); // NOI18N
        MnCatatanKeseimbanganCairan.setPreferredSize(new java.awt.Dimension(230, 26));
        MnCatatanKeseimbanganCairan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanKeseimbanganCairanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCatatanKeseimbanganCairan);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Daftar Pemberian Teraphi Cairan / Infus ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-06-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-06-2026" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 174));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(250, 225));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(326, 10, 295, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-06-2026" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(84, 40, 80, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(212, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 70, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(168, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(233, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(298, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        ChkKejadian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkKejadianActionPerformed(evt);
            }
        });
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(363, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

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

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Infus : ");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(30, 300, 35, 23);

        Infus.setFocusTraversalPolicyProvider(true);
        Infus.setName("Infus"); // NOI18N
        Infus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InfusActionPerformed(evt);
            }
        });
        Infus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InfusKeyPressed(evt);
            }
        });
        FormInput.add(Infus);
        Infus.setBounds(70, 300, 60, 23);

        jLabel23.setText("Oral : ");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(140, 300, 40, 23);

        Oral.setFocusTraversalPolicyProvider(true);
        Oral.setName("Oral"); // NOI18N
        Oral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OralActionPerformed(evt);
            }
        });
        Oral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OralKeyPressed(evt);
            }
        });
        FormInput.add(Oral);
        Oral.setBounds(180, 300, 50, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Intake");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel17);
        jLabel17.setBounds(20, 280, 70, 23);

        jLabel22.setText("BAK : ");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(20, 380, 40, 23);

        Drain.setFocusTraversalPolicyProvider(true);
        Drain.setName("Drain"); // NOI18N
        Drain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DrainKeyPressed(evt);
            }
        });
        FormInput.add(Drain);
        Drain.setBounds(180, 380, 50, 23);

        BAK.setFocusTraversalPolicyProvider(true);
        BAK.setName("BAK"); // NOI18N
        BAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BAKKeyPressed(evt);
            }
        });
        FormInput.add(BAK);
        BAK.setBounds(60, 380, 50, 23);

        jLabel24.setText("Drain : ");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(140, 380, 40, 23);

        BAB.setFocusTraversalPolicyProvider(true);
        BAB.setName("BAB"); // NOI18N
        BAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BABKeyPressed(evt);
            }
        });
        FormInput.add(BAB);
        BAB.setBounds(60, 350, 50, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("BAB : ");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(30, 350, 40, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Output");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel25);
        jLabel25.setBounds(20, 330, 50, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 800, 1);

        jLabel26.setText("IWL : ");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(360, 350, 40, 23);

        IWL.setFocusTraversalPolicyProvider(true);
        IWL.setName("IWL"); // NOI18N
        IWL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IWLKeyPressed(evt);
            }
        });
        FormInput.add(IWL);
        IWL.setBounds(400, 350, 50, 23);

        CairanMasuk.setFocusTraversalPolicyProvider(true);
        CairanMasuk.setName("CairanMasuk"); // NOI18N
        CairanMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CairanMasukKeyPressed(evt);
            }
        });
        FormInput.add(CairanMasuk);
        CairanMasuk.setBounds(560, 300, 230, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Cairan Masuk : ");
        jLabel27.setName("jLabel27"); // NOI18N
        jLabel27.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel27);
        jLabel27.setBounds(480, 300, 80, 23);

        jLabel29.setText("Keterangan : ");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(320, 160, 100, 23);

        Keterangan.setFocusTraversalPolicyProvider(true);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(420, 160, 380, 23);

        jLabel30.setText("Jenis :");
        jLabel30.setName("jLabel30"); // NOI18N
        jLabel30.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 70, 70, 20);

        scrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Obat Yang Ditambahkan"));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Obat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Obat.setColumns(20);
        Obat.setRows(5);
        Obat.setName("Obat"); // NOI18N
        Obat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Obat);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(429, 70, 360, 80);

        Jenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Bahasa", "Gangguan Biasa", "Penglihatan Terganggu", "Budaya", "Kognotif Terbatas", "Pendengaran Terganggu", "Fisik Lemah", "Lain-lain" }));
        Jenis.setName("Jenis"); // NOI18N
        Jenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JenisActionPerformed(evt);
            }
        });
        Jenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKeyPressed(evt);
            }
        });
        FormInput.add(Jenis);
        Jenis.setBounds(80, 70, 280, 23);

        jLabel14.setText("Volume : ");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(10, 103, 62, 23);

        Volume.setFocusTraversalPolicyProvider(true);
        Volume.setName("Volume"); // NOI18N
        Volume.setPreferredSize(new java.awt.Dimension(64, 23));
        Volume.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VolumeKeyPressed(evt);
            }
        });
        FormInput.add(Volume);
        Volume.setBounds(80, 100, 70, 23);

        jLabel15.setText("Tetesan : ");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(180, 100, 60, 23);

        Tetesan.setFocusTraversalPolicyProvider(true);
        Tetesan.setName("Tetesan"); // NOI18N
        Tetesan.setPreferredSize(new java.awt.Dimension(64, 23));
        Tetesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TetesanKeyPressed(evt);
            }
        });
        FormInput.add(Tetesan);
        Tetesan.setBounds(240, 100, 80, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText(" ML");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(150, 100, 30, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText(" Menit/J");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(321, 100, 38, 23);

        jLabel33.setText("Lama : ");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(10, 190, 62, 23);

        Lama.setEditable(false);
        Lama.setFocusTraversalPolicyProvider(true);
        Lama.setName("Lama"); // NOI18N
        Lama.setPreferredSize(new java.awt.Dimension(64, 23));
        Lama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaKeyPressed(evt);
            }
        });
        FormInput.add(Lama);
        Lama.setBounds(80, 190, 220, 23);

        jLabel34.setText("Mulai : ");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(10, 133, 62, 20);

        Jam1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam1.setName("Jam1"); // NOI18N
        Jam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jam1KeyPressed(evt);
            }
        });
        FormInput.add(Jam1);
        Jam1.setBounds(80, 130, 62, 23);

        Menit1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit1.setName("Menit1"); // NOI18N
        Menit1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Menit1KeyPressed(evt);
            }
        });
        FormInput.add(Menit1);
        Menit1.setBounds(150, 130, 62, 23);

        Detik1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik1.setName("Detik1"); // NOI18N
        Detik1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Detik1KeyPressed(evt);
            }
        });
        FormInput.add(Detik1);
        Detik1.setBounds(220, 130, 62, 23);

        ChkJam.setBorder(null);
        ChkJam.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJam.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJam.setName("ChkJam"); // NOI18N
        FormInput.add(ChkJam);
        ChkJam.setBounds(280, 130, 23, 23);

        jLabel35.setText("Selesai : ");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(10, 160, 62, 20);

        Jam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam2.setName("Jam2"); // NOI18N
        Jam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jam2KeyPressed(evt);
            }
        });
        FormInput.add(Jam2);
        Jam2.setBounds(80, 160, 62, 23);

        Menit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit2.setName("Menit2"); // NOI18N
        Menit2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Menit2KeyPressed(evt);
            }
        });
        FormInput.add(Menit2);
        Menit2.setBounds(150, 160, 62, 23);

        Detik2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik2.setName("Detik2"); // NOI18N
        Detik2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Detik2KeyPressed(evt);
            }
        });
        FormInput.add(Detik2);
        Detik2.setBounds(220, 160, 62, 23);

        ChkJam1.setBorder(null);
        ChkJam1.setSelected(true);
        ChkJam1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJam1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJam1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJam1.setName("ChkJam1"); // NOI18N
        FormInput.add(ChkJam1);
        ChkJam1.setBounds(280, 160, 23, 23);

        jLabel36.setText("Verifikator : ");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(350, 190, 70, 23);

        NIP1.setEditable(false);
        NIP1.setHighlighter(null);
        NIP1.setName("NIP1"); // NOI18N
        NIP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIP1KeyPressed(evt);
            }
        });
        FormInput.add(NIP1);
        NIP1.setBounds(420, 190, 94, 23);

        NamaPetugas1.setEditable(false);
        NamaPetugas1.setName("NamaPetugas1"); // NOI18N
        FormInput.add(NamaPetugas1);
        NamaPetugas1.setBounds(520, 190, 187, 23);

        btnPetugas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas1.setMnemonic('2');
        btnPetugas1.setToolTipText("ALt+2");
        btnPetugas1.setName("btnPetugas1"); // NOI18N
        btnPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugas1ActionPerformed(evt);
            }
        });
        btnPetugas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugas1KeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas1);
        btnPetugas1.setBounds(710, 190, 28, 23);

        JamTerapi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07.00", "08.00", "09.00", "10.00", "11.00", "12.00", "13.00", "14.00", "15.00", "16.00", "17.00", "18.00", "19.00", "20.00", "21.00", "22.00", "23.00", "24.00", "00.00", "01.00", "02.00", "03.00", "04.00", "05.00", "06.00" }));
        JamTerapi.setName("JamTerapi"); // NOI18N
        JamTerapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JamTerapiActionPerformed(evt);
            }
        });
        JamTerapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamTerapiKeyPressed(evt);
            }
        });
        FormInput.add(JamTerapi);
        JamTerapi.setBounds(20, 250, 340, 23);

        jLabel37.setText("Kesadaran : ");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(370, 250, 90, 23);

        Kesadaran.setFocusTraversalPolicyProvider(true);
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(460, 250, 340, 23);

        jLabel38.setText("Muntah : ");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(130, 350, 50, 23);

        Muntah.setFocusTraversalPolicyProvider(true);
        Muntah.setName("Muntah"); // NOI18N
        Muntah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuntahKeyPressed(evt);
            }
        });
        FormInput.add(Muntah);
        Muntah.setBounds(180, 350, 50, 23);

        jLabel39.setText("PERDRH : ");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(240, 350, 60, 23);

        PERDRH.setFocusTraversalPolicyProvider(true);
        PERDRH.setName("PERDRH"); // NOI18N
        PERDRH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PERDRHKeyPressed(evt);
            }
        });
        FormInput.add(PERDRH);
        PERDRH.setBounds(300, 350, 50, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Balance Cairan");
        jLabel40.setName("jLabel40"); // NOI18N
        jLabel40.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel40);
        jLabel40.setBounds(470, 280, 130, 20);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Cairan Keluar : ");
        jLabel41.setName("jLabel41"); // NOI18N
        jLabel41.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel41);
        jLabel41.setBounds(480, 330, 80, 23);

        CairanKeluar.setFocusTraversalPolicyProvider(true);
        CairanKeluar.setName("CairanKeluar"); // NOI18N
        CairanKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CairanKeluarKeyPressed(evt);
            }
        });
        FormInput.add(CairanKeluar);
        CairanKeluar.setBounds(560, 330, 230, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Balance : ");
        jLabel42.setName("jLabel42"); // NOI18N
        jLabel42.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel42);
        jLabel42.setBounds(480, 360, 50, 23);

        Balance.setFocusTraversalPolicyProvider(true);
        Balance.setName("Balance"); // NOI18N
        Balance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BalanceActionPerformed(evt);
            }
        });
        Balance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BalanceKeyPressed(evt);
            }
        });
        FormInput.add(Balance);
        Balance.setBounds(530, 360, 260, 23);
        Balance.getAccessibleContext().setAccessibleDescription("");

        Deurisis.setFocusTraversalPolicyProvider(true);
        Deurisis.setName("Deurisis"); // NOI18N
        Deurisis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeurisisActionPerformed(evt);
            }
        });
        Deurisis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DeurisisKeyPressed(evt);
            }
        });
        FormInput.add(Deurisis);
        Deurisis.setBounds(530, 390, 260, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Deurisis : ");
        jLabel43.setName("jLabel43"); // NOI18N
        jLabel43.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel43);
        jLabel43.setBounds(480, 390, 60, 23);

        jLabel28.setText("NGT : ");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(260, 380, 40, 23);

        NGT.setFocusTraversalPolicyProvider(true);
        NGT.setName("NGT"); // NOI18N
        NGT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NGTKeyPressed(evt);
            }
        });
        FormInput.add(NGT);
        NGT.setBounds(300, 380, 50, 23);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbDaftarCairan.setName("tbDaftarCairan"); // NOI18N
        tbDaftarCairan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDaftarCairanMouseClicked(evt);
            }
        });
        tbDaftarCairan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDaftarCairanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDaftarCairanKeyReleased(evt);
            }
        });
        Scroll6.setViewportView(tbDaftarCairan);

        FormInput.add(Scroll6);
        Scroll6.setBounds(830, 250, 400, 143);

        BtnSimpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan1.setMnemonic('S');
        BtnSimpan1.setText("Simpan");
        BtnSimpan1.setToolTipText("Alt+S");
        BtnSimpan1.setName("BtnSimpan1"); // NOI18N
        BtnSimpan1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan1ActionPerformed(evt);
            }
        });
        BtnSimpan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan1KeyPressed(evt);
            }
        });
        FormInput.add(BtnSimpan1);
        BtnSimpan1.setBounds(10, 420, 100, 30);

        BtnEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit1.setMnemonic('G');
        BtnEdit1.setText("Ganti");
        BtnEdit1.setToolTipText("Alt+G");
        BtnEdit1.setName("BtnEdit1"); // NOI18N
        BtnEdit1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdit1ActionPerformed(evt);
            }
        });
        BtnEdit1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEdit1KeyPressed(evt);
            }
        });
        FormInput.add(BtnEdit1);
        BtnEdit1.setBounds(190, 420, 100, 30);

        BtnHapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus1.setMnemonic('H');
        BtnHapus1.setText("Hapus");
        BtnHapus1.setToolTipText("Alt+H");
        BtnHapus1.setName("BtnHapus1"); // NOI18N
        BtnHapus1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapus1ActionPerformed(evt);
            }
        });
        BtnHapus1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapus1KeyPressed(evt);
            }
        });
        FormInput.add(BtnHapus1);
        BtnHapus1.setBounds(280, 420, 100, 30);

        BtnBatal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal1.setMnemonic('B');
        BtnBatal1.setText("Baru");
        BtnBatal1.setToolTipText("Alt+B");
        BtnBatal1.setName("BtnBatal1"); // NOI18N
        BtnBatal1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatal1ActionPerformed(evt);
            }
        });
        BtnBatal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatal1KeyPressed(evt);
            }
        });
        FormInput.add(BtnBatal1);
        BtnBatal1.setBounds(100, 420, 100, 30);

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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else if(Infus.getText().trim().equals("")){
            Valid.textKosong(Infus,"Infus");
        }else if(Oral.getText().trim().equals("")){
            Valid.textKosong(Oral,"Tranfusi");
        }else if(BAB.getText().trim().equals("")){
            Valid.textKosong(BAB,"Urine/Feses");
        }else if(BAK.getText().trim().equals("")){
            Valid.textKosong(BAK,"Drain");
        }else if(Drain.getText().trim().equals("")){
            Valid.textKosong(Drain,"NGT/Oral");
        }else if(IWL.getText().trim().equals("")){
            Valid.textKosong(IWL,"IWL");
        }else if(CairanMasuk.getText().trim().equals("")){
            Valid.textKosong(CairanMasuk,"Keseimbangan Cairan");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Keterangan,BtnBatal);
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
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+" "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
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
        }else if(Infus.getText().trim().equals("")){
            Valid.textKosong(Infus,"Infus");
        }else if(Oral.getText().trim().equals("")){
            Valid.textKosong(Oral,"Tranfusi");
        }else if(BAB.getText().trim().equals("")){
            Valid.textKosong(BAB,"Urine/Feses");
        }else if(BAK.getText().trim().equals("")){
            Valid.textKosong(BAK,"Drain");
        }else if(Drain.getText().trim().equals("")){
            Valid.textKosong(Drain,"NGT/Oral");
        }else if(IWL.getText().trim().equals("")){
            Valid.textKosong(IWL,"IWL");
        }else if(CairanMasuk.getText().trim().equals("")){
            Valid.textKosong(CairanMasuk,"Keseimbangan Cairan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+" "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                                ganti();
                            }
                        }
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
                Valid.MyReportqry("rptDataCatatanKeseimbanganCairan.jasper","report","::[ Data Catatan Keseimbangan Cairan ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pasien.tgl_lahir,catatan_keseimbangan_cairan.tgl_perawatan,catatan_keseimbangan_cairan.jam_rawat,catatan_keseimbangan_cairan.infus,"+
                    "catatan_keseimbangan_cairan.tranfusi,catatan_keseimbangan_cairan.minum,catatan_keseimbangan_cairan.urine,catatan_keseimbangan_cairan.drain,"+
                    "catatan_keseimbangan_cairan.ngt,catatan_keseimbangan_cairan.iwl,catatan_keseimbangan_cairan.keseimbangan,catatan_keseimbangan_cairan.keterangan,"+
                    "catatan_keseimbangan_cairan.nip,petugas.nama "+
                    "from catatan_keseimbangan_cairan inner join reg_periksa on catatan_keseimbangan_cairan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on catatan_keseimbangan_cairan.nip=petugas.nip where "+
                    "catatan_keseimbangan_cairan.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by catatan_keseimbangan_cairan.tgl_perawatan,catatan_keseimbangan_cairan.jam_rawat",param);
            }else{
                Valid.MyReportqry("rptDataCatatanKeseimbanganCairan.jasper","report","::[ Data Catatan Keseimbangan Cairan ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pasien.tgl_lahir,catatan_keseimbangan_cairan.tgl_perawatan,catatan_keseimbangan_cairan.jam_rawat,catatan_keseimbangan_cairan.infus,"+
                    "catatan_keseimbangan_cairan.tranfusi,catatan_keseimbangan_cairan.minum,catatan_keseimbangan_cairan.urine,catatan_keseimbangan_cairan.drain,"+
                    "catatan_keseimbangan_cairan.ngt,catatan_keseimbangan_cairan.iwl,catatan_keseimbangan_cairan.keseimbangan,catatan_keseimbangan_cairan.keterangan,"+
                    "catatan_keseimbangan_cairan.nip,petugas.nama "+
                    "from catatan_keseimbangan_cairan inner join reg_periksa on catatan_keseimbangan_cairan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on catatan_keseimbangan_cairan.nip=petugas.nip where "+
                    "catatan_keseimbangan_cairan.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and "+
                    "(reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    "pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or catatan_keseimbangan_cairan.nip like '%"+TCari.getText().trim()+"%' or petugas.nama like '%"+TCari.getText().trim()+"%') "+
                    "order by catatan_keseimbangan_cairan.tgl_perawatan,catatan_keseimbangan_cairan.jam_rawat ",param);
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

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

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
            Infus.requestFocus();
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
        Valid.pindah(evt,Detik,Infus);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnCatatanKeseimbanganCairanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanKeseimbanganCairanActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptFormulirCatatanKeseimbanganCairan.jasper","report","::[ Formulir Catatan Keseimbangan Cairan ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                    "pasien.jk,pasien.tgl_lahir,catatan_keseimbangan_cairan.tgl_perawatan,catatan_keseimbangan_cairan.jam_rawat,catatan_keseimbangan_cairan.infus,"+
                    "catatan_keseimbangan_cairan.tranfusi,catatan_keseimbangan_cairan.minum,catatan_keseimbangan_cairan.urine,catatan_keseimbangan_cairan.drain,"+
                    "catatan_keseimbangan_cairan.ngt,catatan_keseimbangan_cairan.iwl,catatan_keseimbangan_cairan.keseimbangan,catatan_keseimbangan_cairan.keterangan,"+
                    "catatan_keseimbangan_cairan.nip,petugas.nama "+
                    "from catatan_keseimbangan_cairan inner join reg_periksa on catatan_keseimbangan_cairan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on catatan_keseimbangan_cairan.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' order by catatan_keseimbangan_cairan.tgl_perawatan,catatan_keseimbangan_cairan.jam_rawat",param);
        }
    }//GEN-LAST:event_MnCatatanKeseimbanganCairanActionPerformed

    private void InfusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InfusKeyPressed
        Valid.pindah(evt,btnPetugas,Oral);
    }//GEN-LAST:event_InfusKeyPressed

    private void OralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OralKeyPressed
 
    }//GEN-LAST:event_OralKeyPressed

    private void DrainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DrainKeyPressed
        Valid.pindah(evt,BAK,IWL);
    }//GEN-LAST:event_DrainKeyPressed

    private void BAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BAKKeyPressed
        Valid.pindah(evt,BAB,Drain);
    }//GEN-LAST:event_BAKKeyPressed

    private void BABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BABKeyPressed

    }//GEN-LAST:event_BABKeyPressed

    private void IWLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IWLKeyPressed
        Valid.pindah(evt,Drain,CairanMasuk);
    }//GEN-LAST:event_IWLKeyPressed

    private void CairanMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CairanMasukKeyPressed
        Valid.pindah(evt,IWL,Keterangan);
    }//GEN-LAST:event_CairanMasukKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,CairanMasuk,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void ObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatKeyPressed
        Valid.pindah2(evt,NIP,BtnSimpan);
    }//GEN-LAST:event_ObatKeyPressed

    private void JenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JenisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisActionPerformed

    private void JenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisKeyPressed

    private void VolumeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VolumeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_VolumeKeyPressed

    private void TetesanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TetesanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TetesanKeyPressed

    private void LamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LamaKeyPressed

    private void Jam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jam1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jam1KeyPressed

    private void Menit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Menit1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Menit1KeyPressed

    private void Detik1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Detik1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Detik1KeyPressed

    private void Jam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jam2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jam2KeyPressed

    private void Menit2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Menit2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Menit2KeyPressed

    private void Detik2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Detik2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Detik2KeyPressed

    private void NIP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIP1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIP1KeyPressed

    private void btnPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPetugas1ActionPerformed

    private void btnPetugas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugas1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPetugas1KeyPressed

    private void JamTerapiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JamTerapiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamTerapiActionPerformed

    private void JamTerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamTerapiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamTerapiKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KesadaranKeyPressed

    private void OralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OralActionPerformed

    private void MuntahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuntahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MuntahKeyPressed

    private void InfusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InfusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InfusActionPerformed

    private void PERDRHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PERDRHKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PERDRHKeyPressed

    private void CairanKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CairanKeluarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CairanKeluarKeyPressed

    private void BalanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BalanceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BalanceKeyPressed

    private void BalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BalanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BalanceActionPerformed

    private void DeurisisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeurisisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeurisisActionPerformed

    private void DeurisisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DeurisisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeurisisKeyPressed

    private void NGTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NGTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NGTKeyPressed

    private void tbDaftarCairanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDaftarCairanMouseClicked
        if(tabModeMasalah.getRowCount()!=0){
            try {
                getDataCairan();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDaftarCairanMouseClicked

    private void tbDaftarCairanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDaftarCairanKeyPressed
        if(tabModeMasalah.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataCairan();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDaftarCairanKeyPressed

    private void tbDaftarCairanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDaftarCairanKeyReleased
        //        if(tabModeMasalah.getRowCount()!=0){
            //            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                //                try {
                    //                    tampilRencana2();
                    //                } catch (java.lang.NullPointerException e) {
                    //                }
                //            }
            //        }
    }//GEN-LAST:event_tbDaftarCairanKeyReleased

    private void BtnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSimpan1ActionPerformed

    private void BtnSimpan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSimpan1KeyPressed

    private void BtnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdit1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEdit1ActionPerformed

    private void BtnEdit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEdit1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEdit1KeyPressed

    private void BtnHapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapus1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHapus1ActionPerformed

    private void BtnHapus1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapus1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHapus1KeyPressed

    private void BtnBatal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBatal1ActionPerformed

    private void BtnBatal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBatal1KeyPressed

    private void ChkKejadianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkKejadianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkKejadianActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataCatatanKeseimbanganCairan dialog = new RMDataCatatanKeseimbanganCairan(new javax.swing.JFrame(), true);
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
    private widget.TextBox BAB;
    private widget.TextBox BAK;
    private widget.TextBox Balance;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnBatal1;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnEdit1;
    private widget.Button BtnHapus;
    private widget.Button BtnHapus1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan1;
    private widget.TextBox CairanKeluar;
    private widget.TextBox CairanMasuk;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJam;
    private widget.CekBox ChkJam1;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.ComboBox Detik1;
    private widget.ComboBox Detik2;
    private widget.TextBox Deurisis;
    private widget.TextBox Drain;
    private widget.PanelBiasa FormInput;
    private widget.TextBox IWL;
    private widget.TextBox Infus;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.ComboBox Jam1;
    private widget.ComboBox Jam2;
    private widget.ComboBox JamTerapi;
    private widget.ComboBox Jenis;
    private widget.TextBox Kesadaran;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.TextBox Lama;
    private widget.ComboBox Menit;
    private widget.ComboBox Menit1;
    private widget.ComboBox Menit2;
    private javax.swing.JMenuItem MnCatatanKeseimbanganCairan;
    private widget.TextBox Muntah;
    private widget.TextBox NGT;
    private widget.TextBox NIP;
    private widget.TextBox NIP1;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NamaPetugas1;
    private widget.TextArea Obat;
    private widget.TextBox Oral;
    private widget.TextBox PERDRH;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox Tetesan;
    private widget.TextBox TglLahir;
    private widget.TextBox Umur;
    private widget.TextBox Volume;
    private widget.Button btnPetugas;
    private widget.Button btnPetugas1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbDaftarCairan;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select a.*,b.no_rawat,c.no_rkm_medis,c.nm_pasien,b.umurdaftar,b.sttsumur,c.jk,c.tgl_lahir,d.nama as petugas,e.nama as verifikator " +
                    "from terapi_cairan a " +
                    "inner join reg_periksa b on a.no_rawat=b.no_rawat " +
                    "inner join pasien c on b.no_rkm_medis=c.no_rkm_medis " +
                    "inner join petugas d on a.nip=d.nip " +
                    "inner join petugas e on a.nip1=e.nip " +
                    "where "+
                    "a.tgl_perawatan between ? and ? order by a.tgl_perawatan,a.jam_rawat");
            }else{
                ps=koneksi.prepareStatement(
                    "select a.*,b.no_rawat,c.no_rkm_medis,c.nm_pasien,b.umurdaftar,b.sttsumur,c.jk,c.tgl_lahir,d.nama as petugas,e.nama as verifikator " +
                    "from terapi_cairan a " +
                    "inner join reg_periksa b on a.no_rawat=b.no_rawat " +
                    "inner join pasien c on b.no_rkm_medis=c.no_rkm_medis " +
                    "inner join petugas d on a.nip=d.nip " +
                    "inner join petugas e on a.nip1=e.nip " +
                    "where "+
                    "a.tgl_perawatan between ? and ? and (b.no_rawat like ? or c.no_rkm_medis like ? "+
                    "or c.nm_pasien like ? or a.nip like ? or d.nama like ? or a.nip1 like ? or e.nama like ?) "+
                    "order by a.tgl_perawatan,a.jam_rawat ");
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
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),rs.getString("tgl_lahir"),
                        rs.getString("tgl_perawatan"),rs.getString("jam_rawat"),rs.getString("jenis"),rs.getString("volume"),
                        rs.getString("tetesan"),rs.getString("jam_mulai"),rs.getString("jam_selesai"),rs.getString("lama"),rs.getString("obat"),
                        rs.getString("nip"),rs.getString("petugas"),rs.getString("keterangan"),rs.getString("nip1"),rs.getString("verifikator")
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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void emptTeks() {
        Volume.setText("0");
        Tetesan.setText("0");
        Keterangan.setText("-");
        Obat.setText("-");
        JamTerapi.setSelectedItem("07.00");
        Kesadaran.setText("-");
        Infus.setText("0");
        Oral.setText("0");
        BAB.setText("0");
        BAK.setText("0");
        Muntah.setText("0");
        Drain.setText("0");
        PERDRH.setText("0");
        NGT.setText("0");
        IWL.setText("0");
        CairanMasuk.setText("0");
        CairanKeluar.setText("0");
        Balance.setText("0");
        Deurisis.setText("0");
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        Infus.requestFocus();
        
        Date now = Calendar.getInstance().getTime();

        int nilai_jam = now.getHours();
        int nilai_menit = now.getMinutes();
        int nilai_detik = now.getSeconds();

        String nol_jam = (nilai_jam <= 9) ? "0" : "";
        String nol_menit = (nilai_menit <= 9) ? "0" : "";
        String nol_detik = (nilai_detik <= 9) ? "0" : "";

        String jam = nol_jam + Integer.toString(nilai_jam);
        String menit = nol_menit + Integer.toString(nilai_menit);
        String detik = nol_detik + Integer.toString(nilai_detik);

        Jam1.setSelectedItem(jam);
        Menit1.setSelectedItem(menit);
        Detik1.setSelectedItem(detik);
        
        Jam2.setSelectedItem(jam);
        Menit2.setSelectedItem(menit);
        Detik2.setSelectedItem(detik);
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(0,2));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(3,5));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(6,8));
            Jenis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Volume.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Tetesan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Jam1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(0,2));
            Menit1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(3,5));
            Detik1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(6,8));
            Jam2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString().substring(0,2));
            Menit2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString().substring(3,5));
            Detik2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString().substring(6,8));
            Lama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Obat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NIP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            NamaPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            NIP1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NamaPetugas1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()); 
            
            ChkKejadian.setSelected(false);
            ChkJam1.setSelected(false);
        }
    }
    
     

    private void getDataCairan() {
        if(tbDaftarCairan.getSelectedRow()!= -1){
            JamTerapi.setSelectedItem(tbDaftarCairan.getValueAt(tbDaftarCairan.getSelectedRow(),1).toString());
            Kesadaran.setText(tbDaftarCairan.getValueAt(tbDaftarCairan.getSelectedRow(),2).toString());
            
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
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
        tampil();
        tampilCairan();
        
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,510));
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
        BtnSimpan.setEnabled(akses.getbalance_cairan());
        BtnHapus.setEnabled(akses.getbalance_cairan());
        BtnEdit.setEnabled(akses.getbalance_cairan());
        BtnPrint.setEnabled(akses.getbalance_cairan()); 
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
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
                ChkKejadian.setEnabled(false);
                ChkJam.setEnabled(false);
                ChkJam1.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
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
    
    private void jam1(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam1;
            private int nilai_menit1;
            private int nilai_detik1;
            public void actionPerformed(ActionEvent e) {
                String nol_jam1 = "";
                String nol_menit1 = "";
                String nol_detik1 = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJam.isSelected()==true){
                    nilai_jam1 = now.getHours();
                    nilai_menit1 = now.getMinutes();
                    nilai_detik1 = now.getSeconds();
                }else if(ChkJam.isSelected()==false){
                    nilai_jam1 =Jam1.getSelectedIndex();
                    nilai_menit1 =Menit1.getSelectedIndex();
                    nilai_detik1 =Detik1.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam1 <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam1 = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit1 <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit1 = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik1 <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik1 = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam1 = nol_jam1 + Integer.toString(nilai_jam1);
                String menit1 = nol_menit1 + Integer.toString(nilai_menit1);
                String detik1 = nol_detik1 + Integer.toString(nilai_detik1);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam1.setSelectedItem(jam1);
                Menit1.setSelectedItem(menit1);
                Detik1.setSelectedItem(detik1);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    private void jam2(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam2;
            private int nilai_menit2;
            private int nilai_detik2;
            public void actionPerformed(ActionEvent e) {
                String nol_jam2 = "";
                String nol_menit2 = "";
                String nol_detik2 = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJam1.isSelected()==true){
                    nilai_jam2 = now.getHours();
                    nilai_menit2 = now.getMinutes();
                    nilai_detik2 = now.getSeconds();
                }else if(ChkJam1.isSelected()==false){
                    nilai_jam2 =Jam2.getSelectedIndex();
                    nilai_menit2 =Menit2.getSelectedIndex();
                    nilai_detik2 =Detik2.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam2 <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam2 = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit2 <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit2 = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik2 <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik2 = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam2 = nol_jam2 + Integer.toString(nilai_jam2);
                String menit2 = nol_menit2 + Integer.toString(nilai_menit2);
                String detik2 = nol_detik2 + Integer.toString(nilai_detik2);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam2.setSelectedItem(jam2);
                Menit2.setSelectedItem(menit2);
                Detik2.setSelectedItem(detik2);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void ganti() {
//        if(Sequel.mengedittf("catatan_keseimbangan_cairan","tgl_perawatan=? and jam_rawat=? and no_rawat=?","no_rawat=?,tgl_perawatan=?,jam_rawat=?,infus=?,"+
//                "tranfusi=?,minum=?,urine=?,drain=?,ngt=?,iwl=?,keseimbangan=?,keterangan=?,nip=?",16,new String[]{
//                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
//                Infus.getText(),Oral.getText(),Minum.getText(),BAB.getText(),BAK.getText(),Drain.getText(),IWL.getText(),Keseimbangan.getText(),
//                Keterangan.getText(),NIP.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),
//                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
//            })==true){
//            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
//            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
//            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
//            tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),3);
//            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
//            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),5);
//            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+""),tbObat.getSelectedRow(),6);
//            tbObat.setValueAt(Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
//            tbObat.setValueAt(Infus.getText(),tbObat.getSelectedRow(),8);
//            tbObat.setValueAt(Oral.getText(),tbObat.getSelectedRow(),9);
////            tbObat.setValueAt(Minum.getText(),tbObat.getSelectedRow(),10);
//            tbObat.setValueAt(BAB.getText(),tbObat.getSelectedRow(),11);
//            tbObat.setValueAt(BAK.getText(),tbObat.getSelectedRow(),12);
//            tbObat.setValueAt(Drain.getText(),tbObat.getSelectedRow(),13);
//            tbObat.setValueAt(IWL.getText(),tbObat.getSelectedRow(),14);
//            tbObat.setValueAt(Keseimbangan.getText(),tbObat.getSelectedRow(),15);
//            tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),16);
//            tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),17);
//            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),18);
//            emptTeks();
//        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from catatan_keseimbangan_cairan where tgl_perawatan=? and jam_rawat=? and no_rawat=?",3,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void simpan() {
//        if(Sequel.menyimpantf("catatan_keseimbangan_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?","Data",13,new String[]{
//            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
//            Infus.getText(),Oral.getText(),Minum.getText(),BAB.getText(),BAK.getText(),Drain.getText(),IWL.getText(),Keseimbangan.getText(),
//            Keterangan.getText(),NIP.getText()
//        })==true){
//            tabMode.addRow(new String[]{
//                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),JK.getText(),TglLahir.getText(),
//                Valid.SetTgl(Tanggal.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
//                Infus.getText(),Oral.getText(),Minum.getText(),BAB.getText(),BAK.getText(),Drain.getText(),IWL.getText(),Keseimbangan.getText(),
//                Keterangan.getText(),NIP.getText(),NamaPetugas.getText()
//            });
//            LCount.setText(""+tabMode.getRowCount());
//            emptTeks();
//        }
    }
    
    private void tampilCairan() {
        try{
            ps=koneksi.prepareStatement("select * from pemberian_cairan where no_rawat like ?");
            try {
                ps.setString(1,"%"+TNoRw.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeMasalah.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3)});
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
    
    private void updateLama() {
        try {
            String mulai = Jam1.getSelectedItem().toString() + ":" +
                           Menit1.getSelectedItem().toString() + ":" +
                           Detik1.getSelectedItem().toString();

            String selesai = Jam2.getSelectedItem().toString() + ":" +
                             Menit2.getSelectedItem().toString() + ":" +
                             Detik2.getSelectedItem().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date d1 = sdf.parse(mulai);
            Date d2 = sdf.parse(selesai);

            long diff = d2.getTime() - d1.getTime();
            if (diff < 0) diff = 0;

            long jam = diff / (1000 * 60 * 60);
            long menit = (diff / (1000 * 60)) % 60;
            long detik = (diff / 1000) % 60;

            Lama.setText(String.format("%02d:%02d:%02d", jam, menit, detik));
        } catch (Exception e) {
            Lama.setText("00:00:00");
        }
    }
    
    private void mulaiTimerLama() {
        new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // update Jam1, Menit1, Detik1 di sini kalau memang realtime
                updateLama();
            }
        }).start();
    }
    
}
