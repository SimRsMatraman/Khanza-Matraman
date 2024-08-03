package rekammedis;

import bridging.BPJSCekKartu;
import bridging.BPJSCekNIK2;
import bridging.BPJSCekRujukanKartuPCare;
import bridging.BPJSCekRujukanKartuRS;
import bridging.CoronaPasien;
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
import java.awt.event.KeyListener;
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
import simrskhanza.DlgCatatan;
import simrskhanza.DlgIGD;
import simrskhanza.DlgPasien;
import simrskhanza.DlgReg;

/**
 *
 * @author dosen
 */
public class RMSKriningMandiri extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgPasien pasien=new DlgPasien(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCatatan catatan=new DlgCatatan(null,false);
    private String pilihan="",nokartu="",validasiregistrasi=Sequel.cariIsi("select wajib_closing_kasir from set_validasi_registrasi"),
            validasicatatan=Sequel.cariIsi("select tampilkan_catatan from set_validasi_catatan");
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public RMSKriningMandiri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Tgl.Skrining","Jam Skrining","No Skrining","Kategori","No Registrasi","Nama","NIK","No. Telp","Alamat","Suhu",
                "Demam","Batuk","Pilek","Menggigil","Diare","Sesak Napas","Sakit Kepala","Buang Air Besar Lebih Dari 3 kali sehari",
                "Sakit Tenggorokan","Kehilangan Indra Penciuman","Kehilangan Indra Perasa","Hidung Tersumbat","Mual/muntah",
                "Kesulitan Bernapas","Pusing","Nyeri Menelan","Tidak Dapat Merasakan Lezatnya Makanan","Apakah saat ini Anda merasa sehat tanpa ada keluhan apapun",
                "Apakah terdapat anggota keluarga yang tinggal satu rumah sedang menderita Covid-19 dalam 14 hari terakhir","Apakah pernah berinteraksi dengan penderita Covid-19 tanpa memakai masker dalam 14 hari terakhir",
                "Status"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 31; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(140);
            }else if(i==6){
                column.setPreferredWidth(30);
            }else if(i==7){
                column.setPreferredWidth(45);
            }else if(i==8){
                column.setPreferredWidth(170);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(170);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(150);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(150);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(150);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());


        TNoRM.setDocument(new batasInput((byte)17).getKata(TNoRM));
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
        
        ChkInput.setSelected(false);
        isForm();
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasien.getTable().getSelectedRow()!= -1){  
                    TNoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                    TPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),2).toString());   
                    Kategori.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),4).toString().replaceAll("L","LAKI-LAKI").replaceAll("P","PEREMPUAN")); 
                    Jam.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),6).toString()); 
                    NoTelp.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),7).toString()); 
                }  
                if(pasien.getTable2().getSelectedRow()!= -1){  
                    TNoRM.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),1).toString());
                    TPasien.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),2).toString());   
                    Kategori.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),4).toString().replaceAll("L","LAKI-LAKI").replaceAll("P","PEREMPUAN"));
                    Jam.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),6).toString());  
                    NoTelp.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),7).toString()); 
                }  
                if(pasien.getTable3().getSelectedRow()!= -1){  
                    TNoRM.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),1).toString());
                    TPasien.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),2).toString());   
                    Kategori.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),4).toString().replaceAll("L","LAKI-LAKI").replaceAll("P","PEREMPUAN"));
                    Jam.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),6).toString());  
                    NoTelp.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),7).toString()); 
                }  
                TNoRM.requestFocus();
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
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pasien.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });    
        
        pasien.getTable2().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pasien.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        pasien.getTable3().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pasien.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
//        jam();
        
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
        MnLembarSkriningRalan = new javax.swing.JMenuItem();
        MnPDFSkriningRalan = new javax.swing.JMenuItem();
        ppPasienCorona = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel11 = new widget.Label();
        Jam = new widget.TextBox();
        jLabel5 = new widget.Label();
        Kategori = new widget.TextBox();
        jLabel7 = new widget.Label();
        NoTelp = new widget.TextBox();
        jLabel19 = new widget.Label();
        jLabel8 = new widget.Label();
        NIK = new widget.TextBox();
        jLabel20 = new widget.Label();
        Alamat = new widget.TextBox();
        NoReg = new widget.TextBox();
        Tanggal = new widget.TextBox();
        jLabel12 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel13 = new widget.Label();
        Demam = new widget.TextBox();
        jLabel14 = new widget.Label();
        Pilek = new widget.TextBox();
        jLabel16 = new widget.Label();
        Menggigil = new widget.TextBox();
        jLabel18 = new widget.Label();
        Diare = new widget.TextBox();
        jLabel21 = new widget.Label();
        Napas = new widget.TextBox();
        jLabel22 = new widget.Label();
        Kepala = new widget.TextBox();
        jLabel23 = new widget.Label();
        Bab = new widget.TextBox();
        jLabel24 = new widget.Label();
        Tenggorokan = new widget.TextBox();
        jLabel25 = new widget.Label();
        Penciuman = new widget.TextBox();
        jLabel26 = new widget.Label();
        Perasa = new widget.TextBox();
        jLabel27 = new widget.Label();
        Hidung = new widget.TextBox();
        jLabel28 = new widget.Label();
        Mual = new widget.TextBox();
        jLabel29 = new widget.Label();
        Kesulitan = new widget.TextBox();
        jLabel30 = new widget.Label();
        Pusing = new widget.TextBox();
        jLabel31 = new widget.Label();
        Menelan = new widget.TextBox();
        jLabel32 = new widget.Label();
        Makanan = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        pertanyaan_2 = new widget.TextBox();
        pertanyaan_3 = new widget.TextBox();
        pertanyaan_4 = new widget.TextBox();
        jLabel36 = new widget.Label();
        Status = new widget.TextBox();
        jLabel37 = new widget.Label();
        Batuk = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLembarSkriningRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarSkriningRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarSkriningRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnLembarSkriningRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarSkriningRalan.setText("Lembar Skrining Ralan");
        MnLembarSkriningRalan.setName("MnLembarSkriningRalan"); // NOI18N
        MnLembarSkriningRalan.setPreferredSize(new java.awt.Dimension(230, 26));
        MnLembarSkriningRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarSkriningRalanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLembarSkriningRalan);

        MnPDFSkriningRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnPDFSkriningRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPDFSkriningRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnPDFSkriningRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPDFSkriningRalan.setText("PDF Skrining Ralan");
        MnPDFSkriningRalan.setName("MnPDFSkriningRalan"); // NOI18N
        MnPDFSkriningRalan.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPDFSkriningRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPDFSkriningRalanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPDFSkriningRalan);

        ppPasienCorona.setBackground(new java.awt.Color(255, 255, 254));
        ppPasienCorona.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPasienCorona.setForeground(new java.awt.Color(50, 50, 50));
        ppPasienCorona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPasienCorona.setText("Bridging Pasien Corona Kemenkes");
        ppPasienCorona.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPasienCorona.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPasienCorona.setName("ppPasienCorona"); // NOI18N
        ppPasienCorona.setPreferredSize(new java.awt.Dimension(230, 26));
        ppPasienCorona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPasienCoronaBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPasienCorona);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Skrining Mandiri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Regist");
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

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-09-2021" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-09-2021" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(230, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass7.add(BtnCari);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass7.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass7.add(LCount);

        jPanel3.add(panelGlass7, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 280));
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(190, 180));
        FormInput.setLayout(null);

        jLabel4.setText("Nama :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 80, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(84, 10, 90, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(177, 10, 200, 23);

        jLabel9.setText("Jam Skrining :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(390, 40, 90, 23);

        jLabel11.setText("Tanggal Skrining :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(380, 10, 100, 23);

        Jam.setEditable(false);
        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N
        FormInput.add(Jam);
        Jam.setBounds(480, 40, 100, 23);

        jLabel5.setText("No Reg :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(260, 70, 50, 23);

        Kategori.setEditable(false);
        Kategori.setHighlighter(null);
        Kategori.setName("Kategori"); // NOI18N
        FormInput.add(Kategori);
        Kategori.setBounds(84, 40, 120, 23);

        jLabel7.setText("Kategori :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(0, 40, 80, 23);

        NoTelp.setEditable(false);
        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N
        FormInput.add(NoTelp);
        NoTelp.setBounds(90, 100, 170, 23);

        jLabel19.setText("No. Telp :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(30, 100, 50, 23);

        jLabel8.setText("NIK :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 70, 80, 23);

        NIK.setEditable(false);
        NIK.setHighlighter(null);
        NIK.setName("NIK"); // NOI18N
        FormInput.add(NIK);
        NIK.setBounds(90, 70, 170, 23);

        jLabel20.setText("Alamat :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(30, 130, 50, 23);

        Alamat.setEditable(false);
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        FormInput.add(Alamat);
        Alamat.setBounds(90, 130, 290, 23);

        NoReg.setEditable(false);
        NoReg.setHighlighter(null);
        NoReg.setName("NoReg"); // NOI18N
        FormInput.add(NoReg);
        NoReg.setBounds(320, 70, 60, 23);

        Tanggal.setEditable(false);
        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N
        FormInput.add(Tanggal);
        Tanggal.setBounds(480, 10, 100, 23);

        jLabel12.setText("Suhu :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(390, 70, 90, 23);

        Suhu.setEditable(false);
        Suhu.setHighlighter(null);
        Suhu.setName("Suhu"); // NOI18N
        FormInput.add(Suhu);
        Suhu.setBounds(480, 70, 100, 23);

        jLabel13.setText("Demam :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(390, 100, 90, 23);

        Demam.setEditable(false);
        Demam.setHighlighter(null);
        Demam.setName("Demam"); // NOI18N
        FormInput.add(Demam);
        Demam.setBounds(480, 100, 100, 23);

        jLabel14.setText("Apakah pernah berinteraksi dengan penderita Covid-19 tanpa memakai masker dalam 14 hari terakhir?");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(15, 230, 540, 23);

        Pilek.setEditable(false);
        Pilek.setHighlighter(null);
        Pilek.setName("Pilek"); // NOI18N
        FormInput.add(Pilek);
        Pilek.setBounds(480, 130, 100, 23);

        jLabel16.setText("Menggigil :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(590, 10, 70, 23);

        Menggigil.setEditable(false);
        Menggigil.setHighlighter(null);
        Menggigil.setName("Menggigil"); // NOI18N
        FormInput.add(Menggigil);
        Menggigil.setBounds(660, 10, 100, 23);

        jLabel18.setText("Diare :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(590, 40, 70, 23);

        Diare.setEditable(false);
        Diare.setHighlighter(null);
        Diare.setName("Diare"); // NOI18N
        FormInput.add(Diare);
        Diare.setBounds(660, 40, 100, 23);

        jLabel21.setText("Sesak Napas :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(580, 70, 80, 23);

        Napas.setEditable(false);
        Napas.setHighlighter(null);
        Napas.setName("Napas"); // NOI18N
        FormInput.add(Napas);
        Napas.setBounds(660, 70, 100, 23);

        jLabel22.setText("Sakit Kepala :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(580, 100, 80, 23);

        Kepala.setEditable(false);
        Kepala.setHighlighter(null);
        Kepala.setName("Kepala"); // NOI18N
        FormInput.add(Kepala);
        Kepala.setBounds(660, 100, 100, 23);

        jLabel23.setText("Bab > 3/Hari :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(580, 130, 80, 23);

        Bab.setEditable(false);
        Bab.setHighlighter(null);
        Bab.setName("Bab"); // NOI18N
        FormInput.add(Bab);
        Bab.setBounds(660, 130, 100, 23);

        jLabel24.setText("Sakit Tenggorokan:");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(830, 10, 100, 23);

        Tenggorokan.setEditable(false);
        Tenggorokan.setHighlighter(null);
        Tenggorokan.setName("Tenggorokan"); // NOI18N
        FormInput.add(Tenggorokan);
        Tenggorokan.setBounds(930, 10, 100, 23);

        jLabel25.setText("Kehilangan Indra Penciuman:");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(780, 40, 150, 23);

        Penciuman.setEditable(false);
        Penciuman.setHighlighter(null);
        Penciuman.setName("Penciuman"); // NOI18N
        FormInput.add(Penciuman);
        Penciuman.setBounds(930, 40, 100, 23);

        jLabel26.setText("Kehilangan Indra Perasa:");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(780, 70, 150, 23);

        Perasa.setEditable(false);
        Perasa.setHighlighter(null);
        Perasa.setName("Perasa"); // NOI18N
        FormInput.add(Perasa);
        Perasa.setBounds(930, 70, 100, 23);

        jLabel27.setText("Hidung Tersumbat:");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(780, 100, 150, 23);

        Hidung.setEditable(false);
        Hidung.setHighlighter(null);
        Hidung.setName("Hidung"); // NOI18N
        FormInput.add(Hidung);
        Hidung.setBounds(930, 100, 100, 23);

        jLabel28.setText("Mual/muntah:");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(780, 130, 150, 23);

        Mual.setEditable(false);
        Mual.setHighlighter(null);
        Mual.setName("Mual"); // NOI18N
        FormInput.add(Mual);
        Mual.setBounds(930, 130, 100, 23);

        jLabel29.setText("Kesulitan Bernapas:");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(830, 160, 100, 23);

        Kesulitan.setEditable(false);
        Kesulitan.setHighlighter(null);
        Kesulitan.setName("Kesulitan"); // NOI18N
        FormInput.add(Kesulitan);
        Kesulitan.setBounds(930, 160, 100, 23);

        jLabel30.setText("Pusing:");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(860, 190, 70, 23);

        Pusing.setEditable(false);
        Pusing.setHighlighter(null);
        Pusing.setName("Pusing"); // NOI18N
        FormInput.add(Pusing);
        Pusing.setBounds(930, 190, 100, 23);

        jLabel31.setText("Nyeri Menelan:");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(680, 230, 90, 23);

        Menelan.setEditable(false);
        Menelan.setHighlighter(null);
        Menelan.setName("Menelan"); // NOI18N
        FormInput.add(Menelan);
        Menelan.setBounds(780, 230, 100, 23);

        jLabel32.setText("Tidak Dapat Merasakan Lezatnya Makanan:");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(480, 170, 220, 23);

        Makanan.setEditable(false);
        Makanan.setHighlighter(null);
        Makanan.setName("Makanan"); // NOI18N
        FormInput.add(Makanan);
        Makanan.setBounds(710, 170, 100, 23);

        jLabel33.setText("Pilek :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(390, 130, 90, 23);

        jLabel34.setText("Apakah saat ini Anda merasa sehat tanpa ada keluhan apapun?");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(40, 170, 320, 23);

        jLabel35.setText(" Apakah terdapat anggota keluarga yang tinggal satu rumah sedang menderita Covid-19 dalam 14 hari terakhir?");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(20, 200, 580, 23);

        pertanyaan_2.setEditable(false);
        pertanyaan_2.setHighlighter(null);
        pertanyaan_2.setName("pertanyaan_2"); // NOI18N
        FormInput.add(pertanyaan_2);
        pertanyaan_2.setBounds(370, 170, 100, 23);

        pertanyaan_3.setEditable(false);
        pertanyaan_3.setHighlighter(null);
        pertanyaan_3.setName("pertanyaan_3"); // NOI18N
        FormInput.add(pertanyaan_3);
        pertanyaan_3.setBounds(610, 200, 100, 23);

        pertanyaan_4.setEditable(false);
        pertanyaan_4.setHighlighter(null);
        pertanyaan_4.setName("pertanyaan_4"); // NOI18N
        FormInput.add(pertanyaan_4);
        pertanyaan_4.setBounds(570, 230, 100, 23);

        jLabel36.setText("Status :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(210, 40, 50, 23);

        Status.setEditable(false);
        Status.setHighlighter(null);
        Status.setName("Status"); // NOI18N
        FormInput.add(Status);
        Status.setBounds(270, 40, 110, 23);

        jLabel37.setText("Batuk:");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(880, 220, 50, 23);

        Batuk.setEditable(false);
        Batuk.setHighlighter(null);
        Batuk.setName("Batuk"); // NOI18N
        FormInput.add(Batuk);
        Batuk.setBounds(930, 220, 100, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCari.requestFocus();
//        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
//            BtnPasienActionPerformed(null);
//        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            isPas();
//            DTPReg.requestFocus();
        }
        
}//GEN-LAST:event_TNoRMKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
//        if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
//            Valid.textKosong(TNoRM,"Pasien");
//        }else if(kdptg.getText().trim().equals("")||nmptg.getText().trim().equals("")){
//            Valid.textKosong(BtnPtg,"Petugas");
//        }else{
//            if(Sequel.menyimpantf("skrining_mandiri","?,?,?,?,?,?,?,?,?,?","Skrining Rawat Jalan",10,new String[]{
//                Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),TNoRM.getText(),
//                Geriatri.getSelectedItem().toString(),Kesadaran.getSelectedItem().toString(),Pernapasan.getSelectedItem().toString(),NyeriDada.getSelectedItem().toString(),
//                SkalaNyeri.getSelectedItem().toString(),Keputusan.getSelectedItem().toString(),kdptg.getText()
//                })==true){
//                    emptTeks();
//                    tampil();
//            }
//        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
//        }else{
//           Valid.pindah(evt,BtnPtg,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed
//w cek bentar udah ya
    //gua test bang
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

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(tbObat.getSelectedRow()> -1){ 
            try{
                pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih cara registrasi..!!","Pilihan Registrasi",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Via Registrasi","Via IGD","Via Cek No.Kartu VClaim","Via Cek NIK VClaim","Via Cek Rujukan Kartu PCare di VClaim","Via Cek Rujukan Kartu RS di VClaim"},"Via Registrasi");
                switch (pilihan) {
                    case "Via Registrasi":  
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgReg reg=new DlgReg(null,false);
                        reg.emptTeks();    
                        reg.isCek();
                        reg.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                        reg.setLocationRelativeTo(internalFrame1);
                        reg.SetPasien(TNoRM.getText());
                        reg.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor()); 
                        break;
                    case "Via IGD":
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgIGD igd=new DlgIGD(null,false);
                        igd.emptTeks();    
                        igd.isCek();
                        igd.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                        igd.setLocationRelativeTo(internalFrame1);
                        igd.SetPasien(TNoRM.getText());
                        igd.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor()); 
                        break;
                    case "Via Cek No.Kartu VClaim":
                        nokartu=Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?",TNoRM.getText());
                        if(nokartu.equals("")){
                            Valid.textKosong(TCari,"No.Kartu JKN");
                        }else{
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSCekKartu form=new BPJSCekKartu(null,false);
                            form.isCek();
                            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                            form.setLocationRelativeTo(internalFrame1);
                            form.SetNoKartu(nokartu);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }                                
                        break;
                    case "Via Cek NIK VClaim":
                        nokartu=Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?",TNoRM.getText());
                        if(nokartu.equals("")){
                            Valid.textKosong(TCari,"No.KTP");
                        }else{
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSCekNIK2 form=new BPJSCekNIK2(null,false);
                            form.isCek();
                            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                            form.setLocationRelativeTo(internalFrame1);
                            form.SetNoKTP(nokartu);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }     
                        break;
                    case "Via Cek Rujukan Kartu PCare di VClaim":
                        nokartu=Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?",TNoRM.getText());
                        if(nokartu.equals("")){
                            Valid.textKosong(TCari,"No.Kartu JKN");
                        }else{
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSCekRujukanKartuPCare form=new BPJSCekRujukanKartuPCare(null,false);
                            form.isCek();
                            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                            form.setLocationRelativeTo(internalFrame1);
                            form.SetNoKartu(nokartu);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }    
                        break;
                    case "Via Cek Rujukan Kartu RS di VClaim":
                        nokartu=Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?",TNoRM.getText());
                        if(nokartu.equals("")){
                            Valid.textKosong(TCari,"No.Kartu JKN");
                        }else{
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSCekRujukanKartuRS form=new BPJSCekRujukanKartuRS(null,false);
                            form.isCek();
                            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                            form.setLocationRelativeTo(internalFrame1);
                            form.SetNoKartu(nokartu);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        } 
                        break;
                }
            }catch(Exception e){
                System.out.println("Notif : "+e);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

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

    private void MnLembarSkriningRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarSkriningRalanActionPerformed
        if(tbObat.getSelectedRow()> -1){ 
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
//            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kdptg.getText()));  
            Valid.MyReportqry("rptLembarSkriningRalan.jasper","report","::[ Lembar Skrining Rawat Jalan ]::",
                    "select skrining_mandiri.tanggal,skrining_mandiri.jam,skrining_mandiri.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"+
                    "pasien.nm_ibu,pasien.jk,skrining_mandiri.geriatri,skrining_mandiri.kesadaran,skrining_mandiri.pernapasan,"+
                    "skrining_mandiri.nyeri_dada,skrining_mandiri.skala_nyeri,skrining_mandiri.keputusan,skrining_mandiri.nip,petugas.nama "+
                    "from skrining_mandiri inner join pasien inner join petugas on skrining_mandiri.no_rkm_medis=pasien.no_rkm_medis and skrining_mandiri.nip=petugas.nip where skrining_mandiri.no_rkm_medis='"+TNoRM.getText()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_MnLembarSkriningRalanActionPerformed

    private void MnPDFSkriningRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPDFSkriningRalanActionPerformed
        if(tbObat.getSelectedRow()> -1){ 
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
//            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kdptg.getText()));  
            Valid.MyReportqrypdf("rptLembarSkriningRalan.jasper","report","::[ Lembar Skrining Rawat Jalan ]::",
                    "select skrining_mandiri.tanggal,skrining_mandiri.jam,skrining_mandiri.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"+
                    "pasien.nm_ibu,pasien.jk,skrining_mandiri.geriatri,skrining_mandiri.kesadaran,skrining_mandiri.pernapasan,"+
                    "skrining_mandiri.nyeri_dada,skrining_mandiri.skala_nyeri,skrining_mandiri.keputusan,skrining_mandiri.nip,petugas.nama "+
                    "from skrining_mandiri inner join pasien inner join petugas on skrining_mandiri.no_rkm_medis=pasien.no_rkm_medis and skrining_mandiri.nip=petugas.nip where skrining_mandiri.no_rkm_medis='"+TNoRM.getText()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_MnPDFSkriningRalanActionPerformed

    private void ppPasienCoronaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPasienCoronaBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbObat.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            CoronaPasien form=new CoronaPasien(null,false);
            form.setPasien(TNoRM.getText());
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppPasienCoronaBtnPrintActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm();
        emptTeks();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        //        if(tbObat.getSelectedRow()> -1){
            //            if(Sequel.queryu2tf("delete from skrining_mandiri where tanggal=? and jam=? and no_rkm_medis=?",3,new String[]{
                //                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()
                //                })==true){
        //                tampil();
        //            }
        //        }else{
        //            JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
        //        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

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
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptSkriningRalan.jasper","report","::[ Data Skrining Rawat Jalan ]::",
                "select skrining_mandiri.tanggal,skrining_mandiri.jam,skrining_mandiri.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"+
                "pasien.nm_ibu,pasien.jk,skrining_mandiri.geriatri,skrining_mandiri.kesadaran,skrining_mandiri.pernapasan,"+
                "skrining_mandiri.nyeri_dada,skrining_mandiri.skala_nyeri,skrining_mandiri.keputusan,skrining_mandiri.nip,petugas.nama "+
                "from skrining_mandiri inner join pasien inner join petugas on skrining_mandiri.no_rkm_medis=pasien.no_rkm_medis and skrining_mandiri.nip=petugas.nip where "+
                "skrining_mandiri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_mandiri.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                "skrining_mandiri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                "skrining_mandiri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_ibu like '%"+TCari.getText().trim()+"%' or "+
                "skrining_mandiri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_mandiri.kesadaran like '%"+TCari.getText().trim()+"%' or "+
                "skrining_mandiri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_mandiri.pernapasan like '%"+TCari.getText().trim()+"%' or "+
                "skrining_mandiri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_mandiri.nyeri_dada like '%"+TCari.getText().trim()+"%' or "+
                "skrining_mandiri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_mandiri.keputusan like '%"+TCari.getText().trim()+"%' or "+
                "skrining_mandiri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_mandiri.nip like '%"+TCari.getText().trim()+"%' or "+
                "skrining_mandiri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and petugas.nama like '%"+TCari.getText().trim()+"%' order by skrining_mandiri.tanggal desc",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSKriningMandiri dialog = new RMSKriningMandiri(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.TextBox Bab;
    private widget.TextBox Batuk;
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
    private widget.TextBox Demam;
    private widget.TextBox Diare;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Hidung;
    private widget.TextBox Jam;
    private widget.TextBox Kategori;
    private widget.TextBox Kepala;
    private widget.TextBox Kesulitan;
    private widget.Label LCount;
    private widget.TextBox Makanan;
    private widget.TextBox Menelan;
    private widget.TextBox Menggigil;
    private javax.swing.JMenuItem MnLembarSkriningRalan;
    private javax.swing.JMenuItem MnPDFSkriningRalan;
    private widget.TextBox Mual;
    private widget.TextBox NIK;
    private widget.TextBox Napas;
    private widget.TextBox NoReg;
    private widget.TextBox NoTelp;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Penciuman;
    private widget.TextBox Perasa;
    private widget.TextBox Pilek;
    private widget.TextBox Pusing;
    private widget.ScrollPane Scroll;
    private widget.TextBox Status;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TPasien;
    private widget.TextBox Tanggal;
    private widget.TextBox Tenggorokan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
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
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.TextBox pertanyaan_2;
    private widget.TextBox pertanyaan_3;
    private widget.TextBox pertanyaan_4;
    private javax.swing.JMenuItem ppPasienCorona;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {     
        try {
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                    "select skrining_mandiri.tanggal,skrining_mandiri.jam,skrining_mandiri.id_daftar,skrining_mandiri.kategori,skrining_mandiri.no_reg,skrining_mandiri.nama, "+
                    "skrining_mandiri.nik,skrining_mandiri.no_telp,skrining_mandiri.alamat,skrining_mandiri.suhu,skrining_mandiri.demam, "+
                    "skrining_mandiri.batuk,skrining_mandiri.pilek,skrining_mandiri.menggigil,skrining_mandiri.diare,skrining_mandiri.napas, "+
                    "skrining_mandiri.kepala,skrining_mandiri.bab,skrining_mandiri.tenggorokan,skrining_mandiri.penciuman,skrining_mandiri.perasa, "+  
                    "skrining_mandiri.hidung,skrining_mandiri.mual,skrining_mandiri.kesulitan,skrining_mandiri.pusing,skrining_mandiri.menelan, "+
                    "skrining_mandiri.makanan,skrining_mandiri.pertanyaan_2,skrining_mandiri.pertanyaan_3,skrining_mandiri.pertanyaan_4,skrining_mandiri.status "+        
                    "from skrining_mandiri where "+
                    "skrining_mandiri.tanggal between ? and ? and skrining_mandiri.id_daftar like ? or "+
                    "skrining_mandiri.tanggal between ? and ? and skrining_mandiri.kategori like ? or "+
                    "skrining_mandiri.tanggal between ? and ? and skrining_mandiri.nik like ? or "+
                    "skrining_mandiri.tanggal between ? and ? and skrining_mandiri.nama like ? or "+
                    "skrining_mandiri.tanggal between ? and ? and skrining_mandiri.no_reg like ? or "+
                    "skrining_mandiri.tanggal between ? and ? and skrining_mandiri.alamat like ? or "+
                    "skrining_mandiri.tanggal between ? and ? and skrining_mandiri.suhu like ? or "+
                    "skrining_mandiri.tanggal between ? and ? and skrining_mandiri.demam like ? or "+
                    "skrining_mandiri.tanggal between ? and ? and skrining_mandiri.jam like ? order by skrining_mandiri.tanggal desc");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("tanggal"),rs.getString("jam"),rs.getString("id_daftar"),rs.getString("kategori"),rs.getString("no_reg"),rs.getString("nama"),
                        rs.getString("nik"),rs.getString("no_telp"),rs.getString("alamat"),rs.getString("suhu"),rs.getString("demam"),
                        rs.getString("batuk"),rs.getString("pilek"),rs.getString("menggigil"),rs.getString("diare"),rs.getString("napas"),
                        rs.getString("kepala"),rs.getString("bab"),rs.getString("tenggorokan"),rs.getString("penciuman"),rs.getString("perasa"),
                        rs.getString("hidung"),rs.getString("mual"),rs.getString("kesulitan"),rs.getString("pusing"),rs.getString("menelan"),rs.getString("makanan"),
                        rs.getString("pertanyaan_2"),rs.getString("pertanyaan_3"),rs.getString("pertanyaan_4"),rs.getString("status")
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
        LCount.setText(""+tbObat.getRowCount());
    }


    public void emptTeks() {
        TNoRM.setText("");
        TPasien.setText("");
        Kategori.setText("");
        NoTelp.setText("");
        Jam.setText("");
        TNoRM.requestFocus();
    }
    
    

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){            
            Tanggal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            Jam.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            Kategori.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            NoReg.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            NIK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NoTelp.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Alamat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Demam.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Batuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Pilek.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Menggigil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Diare.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Napas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            Kepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Bab.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Tenggorokan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Penciuman.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Perasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Hidung.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Mual.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Kesulitan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString()); 
            Pusing.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Menelan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Makanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            pertanyaan_2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            pertanyaan_3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            pertanyaan_4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Status.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,280));
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
        BtnSimpan.setEnabled(akses.getadmin());
        BtnHapus.setEnabled(akses.getadmin());
        BtnEdit.setEnabled(akses.getadmin());
        ppPasienCorona.setEnabled(akses.getadmin());
//        if(akses.getjml2()>=1){
//            kdptg.setEditable(false);
//            BtnPtg.setEnabled(false);
//            kdptg.setText(akses.getkode());
//            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
//        }   
    }

//    private void jam(){
//        ActionListener taskPerformer = new ActionListener(){
//            private int nilai_jam;
//            private int nilai_menit;
//            private int nilai_detik;
//            public void actionPerformed(ActionEvent e) {
//                String nol_jam = "";
//                String nol_menit = "";
//                String nol_detik = "";
//                
//                Date now = Calendar.getInstance().getTime();
//
//                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
//                if(ChkJln.isSelected()==true){
//                    nilai_jam = now.getHours();
//                    nilai_menit = now.getMinutes();
//                    nilai_detik = now.getSeconds();
//                }else if(ChkJln.isSelected()==false){
//                    nilai_jam =CmbJam.getSelectedIndex();
//                    nilai_menit =CmbMenit.getSelectedIndex();
//                    nilai_detik =CmbDetik.getSelectedIndex();
//                }
//
//                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
//                if (nilai_jam <= 9) {
//                    // Tambahkan "0" didepannya
//                    nol_jam = "0";
//                }
//                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
//                if (nilai_menit <= 9) {
//                    // Tambahkan "0" didepannya
//                    nol_menit = "0";
//                }
//                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
//                if (nilai_detik <= 9) {
//                    // Tambahkan "0" didepannya
//                    nol_detik = "0";
//                }
//                // Membuat String JAM, MENIT, DETIK
//                String jam = nol_jam + Integer.toString(nilai_jam);
//                String menit = nol_menit + Integer.toString(nilai_menit);
//                String detik = nol_detik + Integer.toString(nilai_detik);
//                // Menampilkan pada Layar
//                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
//                CmbJam.setSelectedItem(jam);
//                CmbMenit.setSelectedItem(menit);
//                CmbDetik.setSelectedItem(detik);
//            }
//        };
//        // Timer
//        new Timer(1000, taskPerformer).start();
//    }

    private void isPas(){
        if(validasiregistrasi.equals("Yes")){
            if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",TNoRM.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
            }else{
                if(validasicatatan.equals("Yes")){
                    if(Sequel.cariInteger("select count(no_rkm_medis) from catatan_pasien where no_rkm_medis=?",TNoRM.getText())>0){
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        catatan.setNoRm(TNoRM.getText());
                        catatan.setSize(720,330);
                        catatan.setLocationRelativeTo(internalFrame1);
                        catatan.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }                    
                isCekPasien();
            }
        }else{
            if(validasicatatan.equals("Yes")){
                if(Sequel.cariInteger("select count(no_rkm_medis) from catatan_pasien where no_rkm_medis=?",TNoRM.getText())>0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    catatan.setNoRm(TNoRM.getText());
                    catatan.setSize(720,330);
                    catatan.setLocationRelativeTo(internalFrame1);
                    catatan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
            isCekPasien();
        }        
    }

    private void isCekPasien() {
        if(!TNoRM.equals("")){
            try {
                ps=koneksi.prepareStatement("select nm_pasien,jk,tgl_lahir,nm_ibu from pasien where no_rkm_medis=?");
                try {
                    ps.setString(1,TNoRM.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        TPasien.setText(rs.getString("nm_pasien"));
                        Kategori.setText(rs.getString("jk"));
                        Jam.setText(rs.getString("tgl_lahir"));
                        NoTelp.setText(rs.getString("nm_ibu"));
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
