/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package inventory;

import rekammedis.*;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class DlgTelaahObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String status, resep, identitas, obat,  campuran, jumlah, dosis, rute, tidak1, tidak2, tidak3, benar1, benar2, benar3, benar4, benar5, NOKDO, AP, IKO, PO, CPO, WPO, KAO;
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
//    private SimpleDateFormat tanggalNow = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public DlgTelaahObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Resep","Tanggal Resep","Jam Resep","No.Rawat","No.RM","Nama Pasien","Kode Dokter","Dokter","NIP","Petugas","Status","Resep Lengkap","Identitas Pasien Sesuai","Obat Tepat",
            "Campuran Obat Stabil","Jumlah Tepat","Dosis/Kekuatan/Frekuensi Tepat","Rute Pemberian Tepat","Tidak Ada Interaksi Obat","Tidak Ada Duplikasi","Tidak Ada Alergi/Kontraindikasi",
            "Benar Pasien","Benar Obat","Benar Dosis Pemberian","Benar Rute Pemberian","Benar Waktu Pemberian",
            "Nama Obat & Dosis Obat","Aturan Pakai","Indikasi/Kegunaan Obat","Penyimpanan Obat","Cara Pemakaian Obat","Waktu Penggunaan Obat","Kemungkinan Alergi Obat","Tanggal Telaah","Jam Telaah"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 35; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(180);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(180);
            }else if(i==10){
                column.setPreferredWidth(180);
            }else if(i==11){
                column.setPreferredWidth(180);
            }else if(i==12){
                column.setPreferredWidth(180);
            }else if(i==13){
                column.setPreferredWidth(180);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setPreferredWidth(180);
            }else if(i==16){
                column.setPreferredWidth(180);
            }else if(i==17){
                column.setPreferredWidth(180);
            }else if(i==18){
                column.setPreferredWidth(180);
            }else if(i==19){
                column.setPreferredWidth(180);
            }else if(i==20){
                column.setPreferredWidth(180);
            }else if(i==21){
                column.setPreferredWidth(180);
            }else if(i==22){
                column.setPreferredWidth(180);
            }else if(i==23){
                column.setPreferredWidth(180);
            }else if(i==24){
                column.setPreferredWidth(180);
            }else if(i==25){
                column.setPreferredWidth(180);
            }else if(i==26){
                column.setPreferredWidth(180);
            }else if(i==27){
                column.setPreferredWidth(180);
            }else if(i==28){
                column.setPreferredWidth(180);
            }else if(i==29){
                column.setPreferredWidth(180);
            }else if(i==30){
                column.setPreferredWidth(180);
            }else if(i==31){
                column.setPreferredWidth(180);
            }else if(i==32){
                column.setPreferredWidth(180);
            }else if(i==33){
                column.setPreferredWidth(180);
            }else if(i==34){
                column.setPreferredWidth(180);
            }else if(i==35){
                column.setPreferredWidth(180);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoResep.setDocument(new batasInput((byte)14).getKata(TNoResep));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KodeDokter.setDocument(new batasInput((byte)20).getKata(KodeDokter));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        
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
        
        ChkInput.setSelected(false);
        isForm();
      
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        buttonGroup10 = new javax.swing.ButtonGroup();
        buttonGroup11 = new javax.swing.ButtonGroup();
        buttonGroup12 = new javax.swing.ButtonGroup();
        buttonGroup13 = new javax.swing.ButtonGroup();
        buttonGroup14 = new javax.swing.ButtonGroup();
        buttonGroup15 = new javax.swing.ButtonGroup();
        buttonGroup16 = new javax.swing.ButtonGroup();
        buttonGroup17 = new javax.swing.ButtonGroup();
        buttonGroup18 = new javax.swing.ButtonGroup();
        buttonGroup19 = new javax.swing.ButtonGroup();
        buttonGroup20 = new javax.swing.ButtonGroup();
        buttonGroup21 = new javax.swing.ButtonGroup();
        buttonGroup22 = new javax.swing.ButtonGroup();
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
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel10 = new widget.Label();
        jLabel5 = new widget.Label();
        TNoResep = new widget.TextBox();
        label15 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        label16 = new widget.Label();
        label17 = new widget.Label();
        label18 = new widget.Label();
        ResepYa = new widget.RadioButton();
        ResepTidak = new widget.RadioButton();
        label19 = new widget.Label();
        IdentitasYa = new widget.RadioButton();
        IdentitasTidak = new widget.RadioButton();
        label20 = new widget.Label();
        label21 = new widget.Label();
        label22 = new widget.Label();
        label23 = new widget.Label();
        label24 = new widget.Label();
        label25 = new widget.Label();
        label26 = new widget.Label();
        label27 = new widget.Label();
        label28 = new widget.Label();
        label29 = new widget.Label();
        label30 = new widget.Label();
        label31 = new widget.Label();
        label32 = new widget.Label();
        label33 = new widget.Label();
        label34 = new widget.Label();
        label35 = new widget.Label();
        ObatYa = new widget.RadioButton();
        ObatTidak = new widget.RadioButton();
        CampuranYa = new widget.RadioButton();
        CampuranTidak = new widget.RadioButton();
        JumlahYa = new widget.RadioButton();
        JumlahTidak = new widget.RadioButton();
        DosisYa = new widget.RadioButton();
        DosisTidak = new widget.RadioButton();
        RuteYa = new widget.RadioButton();
        RuteTidak = new widget.RadioButton();
        Tidak1Ya = new widget.RadioButton();
        Tidak1Tidak = new widget.RadioButton();
        Tidak2Ya = new widget.RadioButton();
        Tidak2Tidak = new widget.RadioButton();
        Tidak3Ya = new widget.RadioButton();
        Tidak3Tidak = new widget.RadioButton();
        Benar1Ya = new widget.RadioButton();
        Benar1Tidak = new widget.RadioButton();
        Benar2Ya = new widget.RadioButton();
        Benar2Tidak = new widget.RadioButton();
        Benar3Ya = new widget.RadioButton();
        Benar3Tidak = new widget.RadioButton();
        Benar4Ya = new widget.RadioButton();
        Benar4Tidak = new widget.RadioButton();
        Benar5Ya = new widget.RadioButton();
        Benar5Tidak = new widget.RadioButton();
        jLabel8 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        TglRw = new widget.TextBox();
        jLabel9 = new widget.Label();
        JamRw = new widget.TextBox();
        jLabel11 = new widget.Label();
        Status = new widget.TextBox();
        label36 = new widget.Label();
        label37 = new widget.Label();
        label38 = new widget.Label();
        label39 = new widget.Label();
        label40 = new widget.Label();
        label41 = new widget.Label();
        label42 = new widget.Label();
        label43 = new widget.Label();
        Benar5Tidak1 = new widget.RadioButton();
        NOKDOy = new widget.RadioButton();
        NOKDOt = new widget.RadioButton();
        APy = new widget.RadioButton();
        APt = new widget.RadioButton();
        IKOy = new widget.RadioButton();
        IKOt = new widget.RadioButton();
        POy = new widget.RadioButton();
        POt = new widget.RadioButton();
        CPOy = new widget.RadioButton();
        CPOt = new widget.RadioButton();
        WPOy = new widget.RadioButton();
        WPOt = new widget.RadioButton();
        KAOy = new widget.RadioButton();
        KAOt = new widget.RadioButton();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Telaah Resep Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
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

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-07-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-07-2023" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 448));
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

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 555));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(331, 10, 280, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 112, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 70, 60, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(70, 70, 140, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(210, 70, 270, 23);

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
        BtnDokter.setBounds(480, 70, 28, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel5.setText("No.Resep :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(490, 40, 60, 23);

        TNoResep.setEditable(false);
        TNoResep.setHighlighter(null);
        TNoResep.setName("TNoResep"); // NOI18N
        TNoResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoResepKeyPressed(evt);
            }
        });
        FormInput.add(TNoResep);
        TNoResep.setBounds(560, 40, 141, 23);

        label15.setText("Telaah Obat");
        label15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(670, 130, 80, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setName("KodeDokter"); // NOI18N
        KodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterKeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(70, 40, 141, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        NamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(210, 40, 270, 23);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label16.setText("7. Kemungkinan Alergi Obat :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(10, 270, 150, 23);

        label17.setText("Dokter  :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(0, 40, 60, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label18.setText("Aspek Administratif");
        label18.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(390, 150, 100, 23);

        buttonGroup1.add(ResepYa);
        ResepYa.setText("Ya");
        ResepYa.setName("ResepYa"); // NOI18N
        ResepYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(ResepYa);
        ResepYa.setBounds(560, 170, 45, 23);

        buttonGroup1.add(ResepTidak);
        ResepTidak.setText("Tidak");
        ResepTidak.setName("ResepTidak"); // NOI18N
        FormInput.add(ResepTidak);
        ResepTidak.setBounds(600, 170, 60, 23);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label19.setText("2. Identitas Pasien Sesuai :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(390, 190, 140, 23);

        buttonGroup2.add(IdentitasYa);
        IdentitasYa.setText("Ya");
        IdentitasYa.setName("IdentitasYa"); // NOI18N
        IdentitasYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(IdentitasYa);
        IdentitasYa.setBounds(560, 190, 45, 23);

        buttonGroup2.add(IdentitasTidak);
        IdentitasTidak.setText("Tidak");
        IdentitasTidak.setName("IdentitasTidak"); // NOI18N
        FormInput.add(IdentitasTidak);
        IdentitasTidak.setBounds(600, 190, 60, 23);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label20.setText("Aspek Farmasetik");
        label20.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(390, 210, 90, 23);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("1. Obat Tepat :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(390, 230, 100, 23);

        label22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label22.setText("3. Jumlah Tepat :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(390, 270, 110, 23);

        label23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label23.setText("2. Campuran Obat Stabil :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(390, 250, 140, 23);

        label24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label24.setText("Aspek Klinis");
        label24.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(390, 290, 70, 23);

        label25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label25.setText("1. Dosis/Kekuatan/Frekuensi :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(390, 310, 170, 23);

        label26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label26.setText("2. Rute Pemberian Tepat :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(390, 330, 140, 23);

        label27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label27.setText("3. Tidak Ada Interaksi Obat :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(390, 350, 160, 23);

        label28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label28.setText("4. Tidak Ada Duplikasi :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(390, 370, 140, 23);

        label29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label29.setText("5. Tidak Ada Alergi/Kontradikasi :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(390, 390, 170, 23);

        label30.setText("Pemberian Informasi Obat (PIO)");
        label30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(10, 130, 190, 23);

        label31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label31.setText("1. Benar Pasien :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(670, 150, 110, 23);

        label32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label32.setText("2. Benar Obat :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(670, 170, 90, 23);

        label33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label33.setText("3. Benar Dosis Pemberian :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label33);
        label33.setBounds(670, 190, 160, 23);

        label34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label34.setText("4. Benar Rute Pemberian :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(670, 210, 140, 23);

        label35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label35.setText("5. Benar Waktu Pemberian :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label35);
        label35.setBounds(670, 230, 170, 23);

        buttonGroup3.add(ObatYa);
        ObatYa.setText("Ya");
        ObatYa.setName("ObatYa"); // NOI18N
        ObatYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(ObatYa);
        ObatYa.setBounds(560, 230, 45, 23);

        buttonGroup3.add(ObatTidak);
        ObatTidak.setText("Tidak");
        ObatTidak.setName("ObatTidak"); // NOI18N
        FormInput.add(ObatTidak);
        ObatTidak.setBounds(600, 230, 60, 23);

        buttonGroup4.add(CampuranYa);
        CampuranYa.setText("Ya");
        CampuranYa.setName("CampuranYa"); // NOI18N
        CampuranYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(CampuranYa);
        CampuranYa.setBounds(560, 250, 45, 23);

        buttonGroup4.add(CampuranTidak);
        CampuranTidak.setText("Tidak");
        CampuranTidak.setName("CampuranTidak"); // NOI18N
        FormInput.add(CampuranTidak);
        CampuranTidak.setBounds(600, 250, 60, 23);

        buttonGroup5.add(JumlahYa);
        JumlahYa.setText("Ya");
        JumlahYa.setName("JumlahYa"); // NOI18N
        JumlahYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(JumlahYa);
        JumlahYa.setBounds(560, 270, 45, 23);

        buttonGroup5.add(JumlahTidak);
        JumlahTidak.setText("Tidak");
        JumlahTidak.setName("JumlahTidak"); // NOI18N
        FormInput.add(JumlahTidak);
        JumlahTidak.setBounds(600, 270, 60, 23);

        buttonGroup6.add(DosisYa);
        DosisYa.setText("Ya");
        DosisYa.setName("DosisYa"); // NOI18N
        DosisYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(DosisYa);
        DosisYa.setBounds(560, 310, 45, 23);

        buttonGroup6.add(DosisTidak);
        DosisTidak.setText("Tidak");
        DosisTidak.setName("DosisTidak"); // NOI18N
        FormInput.add(DosisTidak);
        DosisTidak.setBounds(600, 310, 60, 20);

        buttonGroup7.add(RuteYa);
        RuteYa.setText("Ya");
        RuteYa.setName("RuteYa"); // NOI18N
        RuteYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(RuteYa);
        RuteYa.setBounds(560, 330, 45, 23);

        buttonGroup7.add(RuteTidak);
        RuteTidak.setText("Tidak");
        RuteTidak.setName("RuteTidak"); // NOI18N
        FormInput.add(RuteTidak);
        RuteTidak.setBounds(600, 330, 60, 23);

        buttonGroup8.add(Tidak1Ya);
        Tidak1Ya.setText("Ya");
        Tidak1Ya.setName("Tidak1Ya"); // NOI18N
        Tidak1Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Tidak1Ya);
        Tidak1Ya.setBounds(560, 350, 45, 23);

        buttonGroup8.add(Tidak1Tidak);
        Tidak1Tidak.setText("Tidak");
        Tidak1Tidak.setName("Tidak1Tidak"); // NOI18N
        FormInput.add(Tidak1Tidak);
        Tidak1Tidak.setBounds(600, 350, 60, 23);

        buttonGroup9.add(Tidak2Ya);
        Tidak2Ya.setText("Ya");
        Tidak2Ya.setName("Tidak2Ya"); // NOI18N
        Tidak2Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Tidak2Ya);
        Tidak2Ya.setBounds(560, 370, 45, 23);

        buttonGroup9.add(Tidak2Tidak);
        Tidak2Tidak.setText("Tidak");
        Tidak2Tidak.setName("Tidak2Tidak"); // NOI18N
        FormInput.add(Tidak2Tidak);
        Tidak2Tidak.setBounds(600, 370, 60, 23);

        buttonGroup10.add(Tidak3Ya);
        Tidak3Ya.setText("Ya");
        Tidak3Ya.setName("Tidak3Ya"); // NOI18N
        Tidak3Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Tidak3Ya);
        Tidak3Ya.setBounds(560, 390, 45, 23);

        buttonGroup10.add(Tidak3Tidak);
        Tidak3Tidak.setText("Tidak");
        Tidak3Tidak.setName("Tidak3Tidak"); // NOI18N
        FormInput.add(Tidak3Tidak);
        Tidak3Tidak.setBounds(600, 390, 60, 23);

        buttonGroup11.add(Benar1Ya);
        Benar1Ya.setText("Ya");
        Benar1Ya.setName("Benar1Ya"); // NOI18N
        Benar1Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Benar1Ya);
        Benar1Ya.setBounds(830, 150, 45, 23);

        buttonGroup11.add(Benar1Tidak);
        Benar1Tidak.setText("Tidak");
        Benar1Tidak.setName("Benar1Tidak"); // NOI18N
        FormInput.add(Benar1Tidak);
        Benar1Tidak.setBounds(870, 150, 60, 23);

        buttonGroup12.add(Benar2Ya);
        Benar2Ya.setText("Ya");
        Benar2Ya.setName("Benar2Ya"); // NOI18N
        Benar2Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Benar2Ya);
        Benar2Ya.setBounds(830, 170, 45, 23);

        buttonGroup12.add(Benar2Tidak);
        Benar2Tidak.setText("Tidak");
        Benar2Tidak.setName("Benar2Tidak"); // NOI18N
        Benar2Tidak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Benar2TidakActionPerformed(evt);
            }
        });
        FormInput.add(Benar2Tidak);
        Benar2Tidak.setBounds(870, 170, 60, 23);

        buttonGroup13.add(Benar3Ya);
        Benar3Ya.setText("Ya");
        Benar3Ya.setName("Benar3Ya"); // NOI18N
        Benar3Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Benar3Ya);
        Benar3Ya.setBounds(830, 190, 45, 23);

        buttonGroup13.add(Benar3Tidak);
        Benar3Tidak.setText("Tidak");
        Benar3Tidak.setName("Benar3Tidak"); // NOI18N
        FormInput.add(Benar3Tidak);
        Benar3Tidak.setBounds(870, 190, 60, 23);

        buttonGroup14.add(Benar4Ya);
        Benar4Ya.setText("Ya");
        Benar4Ya.setName("Benar4Ya"); // NOI18N
        Benar4Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Benar4Ya);
        Benar4Ya.setBounds(830, 210, 45, 23);

        buttonGroup14.add(Benar4Tidak);
        Benar4Tidak.setText("Tidak");
        Benar4Tidak.setName("Benar4Tidak"); // NOI18N
        FormInput.add(Benar4Tidak);
        Benar4Tidak.setBounds(870, 210, 60, 23);

        buttonGroup15.add(Benar5Ya);
        Benar5Ya.setText("Ya");
        Benar5Ya.setName("Benar5Ya"); // NOI18N
        Benar5Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Benar5Ya);
        Benar5Ya.setBounds(830, 230, 45, 23);

        buttonGroup15.add(Benar5Tidak);
        Benar5Tidak.setText("Tidak");
        Benar5Tidak.setName("Benar5Tidak"); // NOI18N
        FormInput.add(Benar5Tidak);
        Benar5Tidak.setBounds(870, 230, 60, 23);

        jLabel8.setText("Tgl. Resep :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(10, 100, 60, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 129, 1010, 2);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 149, 1010, 2);

        TglRw.setEditable(false);
        TglRw.setHighlighter(null);
        TglRw.setName("TglRw"); // NOI18N
        TglRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglRwKeyPressed(evt);
            }
        });
        FormInput.add(TglRw);
        TglRw.setBounds(80, 100, 141, 23);

        jLabel9.setText("Jam. Resep :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(210, 100, 80, 23);

        JamRw.setEditable(false);
        JamRw.setHighlighter(null);
        JamRw.setName("JamRw"); // NOI18N
        JamRw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JamRwActionPerformed(evt);
            }
        });
        JamRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamRwKeyPressed(evt);
            }
        });
        FormInput.add(JamRw);
        JamRw.setBounds(300, 100, 141, 23);

        jLabel11.setText("Status Resep:");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(450, 100, 80, 23);

        Status.setEditable(false);
        Status.setHighlighter(null);
        Status.setName("Status"); // NOI18N
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        FormInput.add(Status);
        Status.setBounds(540, 100, 141, 23);

        label36.setText("Telaah Resep");
        label36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label36);
        label36.setBounds(400, 130, 80, 23);

        label37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label37.setText("1. Resep Lengkap :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label37);
        label37.setBounds(390, 170, 100, 23);

        label38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label38.setText("1. Nama Obat dan Ketentuan Dosis Obat :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label38);
        label38.setBounds(10, 150, 210, 23);

        label39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label39.setText("2. Aturan Pakai :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label39);
        label39.setBounds(10, 170, 90, 23);

        label40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label40.setText("3. Indikasi / Kegunaan Obat :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label40);
        label40.setBounds(10, 190, 150, 23);

        label41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label41.setText("4. Penyimpanan Obat :");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label41);
        label41.setBounds(10, 210, 120, 23);

        label42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label42.setText("5. Cara Pemakaian Obat :");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label42);
        label42.setBounds(10, 230, 130, 23);

        label43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label43.setText("6. Waktu Penggunaan Obat :");
        label43.setName("label43"); // NOI18N
        label43.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label43);
        label43.setBounds(10, 250, 150, 23);

        buttonGroup15.add(Benar5Tidak1);
        Benar5Tidak1.setText("Tidak");
        Benar5Tidak1.setName("Benar5Tidak1"); // NOI18N
        FormInput.add(Benar5Tidak1);
        Benar5Tidak1.setBounds(870, 230, 60, 23);

        buttonGroup16.add(NOKDOy);
        NOKDOy.setText("Ya");
        NOKDOy.setName("NOKDOy"); // NOI18N
        NOKDOy.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(NOKDOy);
        NOKDOy.setBounds(220, 150, 45, 23);

        buttonGroup16.add(NOKDOt);
        NOKDOt.setText("Tidak");
        NOKDOt.setName("NOKDOt"); // NOI18N
        FormInput.add(NOKDOt);
        NOKDOt.setBounds(260, 150, 60, 23);

        buttonGroup17.add(APy);
        APy.setText("Ya");
        APy.setName("APy"); // NOI18N
        APy.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(APy);
        APy.setBounds(220, 170, 45, 23);

        buttonGroup17.add(APt);
        APt.setText("Tidak");
        APt.setName("APt"); // NOI18N
        FormInput.add(APt);
        APt.setBounds(260, 170, 60, 23);

        buttonGroup18.add(IKOy);
        IKOy.setText("Ya");
        IKOy.setName("IKOy"); // NOI18N
        IKOy.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(IKOy);
        IKOy.setBounds(220, 190, 45, 23);

        buttonGroup18.add(IKOt);
        IKOt.setText("Tidak");
        IKOt.setName("IKOt"); // NOI18N
        FormInput.add(IKOt);
        IKOt.setBounds(260, 190, 60, 23);

        buttonGroup19.add(POy);
        POy.setText("Ya");
        POy.setName("POy"); // NOI18N
        POy.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(POy);
        POy.setBounds(220, 210, 45, 23);

        buttonGroup19.add(POt);
        POt.setText("Tidak");
        POt.setName("POt"); // NOI18N
        FormInput.add(POt);
        POt.setBounds(260, 210, 60, 23);

        buttonGroup20.add(CPOy);
        CPOy.setText("Ya");
        CPOy.setName("CPOy"); // NOI18N
        CPOy.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(CPOy);
        CPOy.setBounds(220, 230, 45, 23);

        buttonGroup20.add(CPOt);
        CPOt.setText("Tidak");
        CPOt.setName("CPOt"); // NOI18N
        FormInput.add(CPOt);
        CPOt.setBounds(260, 230, 60, 23);

        buttonGroup21.add(WPOy);
        WPOy.setText("Ya");
        WPOy.setName("WPOy"); // NOI18N
        WPOy.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(WPOy);
        WPOy.setBounds(220, 250, 45, 23);

        buttonGroup21.add(WPOt);
        WPOt.setText("Tidak");
        WPOt.setName("WPOt"); // NOI18N
        FormInput.add(WPOt);
        WPOt.setBounds(260, 250, 60, 23);

        buttonGroup22.add(KAOy);
        KAOy.setText("Ya");
        KAOy.setName("KAOy"); // NOI18N
        KAOy.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(KAOy);
        KAOy.setBounds(220, 270, 45, 23);

        buttonGroup22.add(KAOt);
        KAOt.setText("Tidak");
        KAOt.setName("KAOt"); // NOI18N
        FormInput.add(KAOt);
        KAOt.setBounds(260, 270, 60, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            //Valid.pindah(evt,TCari,Kejadian);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(TNoResep.getText().trim().equals("")){
            Valid.textKosong(TNoResep,"No.Resep");
        }else if(TNoResep.getText().trim().equals("")){
            Valid.textKosong(TNoResep,"No.Resep");
        }else if(KodeDokter.getText().trim().equals("")){
            Valid.textKosong(KodeDokter,"Dokter");
        }else if(KdPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{
            resep="Tidak"; 
            identitas="Tidak";
            obat="Tidak";
            campuran="Tidak";
            jumlah="Tidak";
            dosis="Tidak";
            rute="Tidak";
            tidak1="Tidak";
            tidak2="Tidak";
            tidak3="Tidak";
            benar1="Tidak";
            benar2="Tidak";
            benar3="Tidak";
            benar4="Tidak";
            benar5="Tidak";
            NOKDO="Tidak";
            AP="Tidak";
            IKO="Tidak";
            PO="Tidak";
            CPO="Tidak";
            WPO="Tidak";
            KAO="Tidak";
            if(ResepYa.isSelected()==true){
                resep="Ya";
            }
            if(IdentitasYa.isSelected()==true){
                identitas="Ya";
            }
            if(ObatYa.isSelected()==true){
                obat="Ya";
            }
            if(CampuranYa.isSelected()==true){
                campuran="Ya";
            }
            if(JumlahYa.isSelected()==true){
                jumlah="Ya";
            }
            if(DosisYa.isSelected()==true){
                dosis="Ya";
            }
            if(RuteYa.isSelected()==true){
                rute="Ya";
            }
            if(Tidak1Ya.isSelected()==true){
                tidak1="Ya";
            }
            if(Tidak2Ya.isSelected()==true){
                tidak2="Ya";
            }
            if(Tidak3Ya.isSelected()==true){
                tidak3="Ya";
            }
            if(Benar1Ya.isSelected()==true){
                benar1="Ya";
            }
            if(Benar2Ya.isSelected()==true){
                benar2="Ya";
            }
            if(Benar3Ya.isSelected()==true){
                benar3="Ya";
            }
            if(Benar4Ya.isSelected()==true){
                benar4="Ya";
            }
            if(Benar5Ya.isSelected()==true){
                benar5="Ya";
            }
            if(NOKDOy.isSelected()==true){
                NOKDO="Ya";
            }
            if(APy.isSelected()==true){
                AP="Ya";
            }
            if(IKOy.isSelected()==true){
                IKO="Ya";
            }
            if(POy.isSelected()==true){
                PO="Ya";
            }
            if(CPOy.isSelected()==true){
                CPO="Ya";
            }
            if(NOKDOy.isSelected()==true){
                NOKDO="Ya";
            }
            if(WPOy.isSelected()==true){
                WPO="Ya";
            }
            if(KAOy.isSelected()==true){
                KAO="Ya";
            }
            if(Sequel.menyimpantf("telaah_resep_obat","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Resep",31,new String[]{
               TNoResep.getText(),TglRw.getText(),JamRw.getText(),
               TNoRw.getText(),KodeDokter.getText(),KdPetugas.getText(),Status.getText(),resep,identitas,obat,campuran,jumlah,dosis,
               rute,tidak1,tidak2,tidak3,benar1,benar2,benar3,benar4,benar5,NOKDO,AP,IKO,PO,CPO,WPO,KAO,tanggalNow.format(new Date()),jamNow.format(new Date())
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
            //Valid.pindah(evt,Obat2an,BtnBatal);
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
            if(Sequel.queryu2tf("delete from telaah_resep_obat where no_resep=? and tanggal=?",2,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()
            })==true){
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
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
            Valid.textKosong(TNoRw,"Pasien");
        }else if(TNoResep.getText().trim().equals("")){
            Valid.textKosong(TNoResep,"No.Resep");
        }else if(TNoResep.getText().trim().equals("")){
            Valid.textKosong(TNoResep,"No.Resep");
        }else if(KodeDokter.getText().trim().equals("")){
            Valid.textKosong(KodeDokter,"Dokter");
        }else if(KdPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
            resep="Tidak"; 
            identitas="Tidak";
            obat="Tidak";
            campuran="Tidak";
            jumlah="Tidak";
            dosis="Tidak";
            rute="Tidak";
            tidak1="Tidak";
            tidak2="Tidak";
            tidak3="Tidak";
            benar1="Tidak";
            benar2="Tidak";
            benar3="Tidak";
            benar4="Tidak";
            benar5="Tidak";
            NOKDO="Tidak";
            AP="Tidak";
            IKO="Tidak";
            PO="Tidak";
            CPO="Tidak";
            WPO="Tidak";
            KAO="Tidak";
            if(ResepYa.isSelected()==true){
                resep="Ya";
            }
            if(IdentitasYa.isSelected()==true){
                identitas="Ya";
            }
            if(ObatYa.isSelected()==true){
                obat="Ya";
            }
            if(CampuranYa.isSelected()==true){
                campuran="Ya";
            }
            if(JumlahYa.isSelected()==true){
                jumlah="Ya";
            }
            if(DosisYa.isSelected()==true){
                dosis="Ya";
            }
            if(RuteYa.isSelected()==true){
                rute="Ya";
            }
            if(Tidak1Ya.isSelected()==true){
                tidak1="Ya";
            }
            if(Tidak2Ya.isSelected()==true){
                tidak2="Ya";
            }
            if(Tidak3Ya.isSelected()==true){
                tidak3="Ya";
            }
            if(Benar1Ya.isSelected()==true){
                benar1="Ya";
            }
            if(Benar2Ya.isSelected()==true){
                benar2="Ya";
            }
            if(Benar3Ya.isSelected()==true){
                benar3="Ya";
            }
            if(Benar4Ya.isSelected()==true){
                benar4="Ya";
            }
            if(Benar5Ya.isSelected()==true){
                benar5="Ya";
            }
            if(NOKDOy.isSelected()==true){
                NOKDO="Ya";
            }
            if(APy.isSelected()==true){
                AP="Ya";
            }
            if(IKOy.isSelected()==true){
                IKO="Ya";
            }
            if(POy.isSelected()==true){
                PO="Ya";
            }
            if(CPOy.isSelected()==true){
                CPO="Ya";
            }
            if(WPOy.isSelected()==true){
                WPO="Ya";
            }
            if(KAOy.isSelected()==true){
                KAO="Ya";
            }
            
                
                if(Sequel.mengedittf("telaah_resep_obat","no_resep=? and tanggal=?","no_resep=?,tanggal=?,jam=?,no_rawat=?,kd_dokter=?,nip=?,status=?,resep=?,identitas=?,obat=?,campuran=?,jumlah=?,dosis=?,rute=?,tidak1=?,tidak2=?,tidak3=?,benar1=?,benar2=?,benar3=?,benar4=?,benar5=?,NOKDO=?,AP=?,IKO=?,PO=?,CPO=?,WPO=?,KAO=?,tgl=?,jam_telaah=?",33,new String[]{
                TNoResep.getText(),TglRw.getText(),JamRw.getText(),
                TNoRw.getText(),KodeDokter.getText(),KdPetugas.getText(),Status.getText(),resep,identitas,obat,campuran,jumlah,dosis,
                rute,tidak1,tidak2,tidak3,benar1,benar2,benar3,benar4,benar5,NOKDO,AP,IKO,PO,CPO,WPO,KAO,tanggalNow.format(new Date()),jamNow.format(new Date()),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()
                    })==true){
                        tampil();
                        emptTeks();
                }
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
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                if(TCari.getText().equals("")){
                    Valid.MyReportqry("rptDataAsuhanGiziPasien.jasper","report","::[ Data Asuhan Gizi Pasien ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,asuhan_gizi.tanggal,"+
                        "asuhan_gizi.antropometri_bb,asuhan_gizi.antropometri_tb,asuhan_gizi.antropometri_imt,asuhan_gizi.antropometri_lla,"+
                        "asuhan_gizi.antropometri_tl,asuhan_gizi.antropometri_ulna,asuhan_gizi.antropometri_bbideal,asuhan_gizi.antropometri_bbperu,"+
                        "asuhan_gizi.antropometri_tbperu,asuhan_gizi.antropometri_bbpertb,asuhan_gizi.antropometri_llaperu,asuhan_gizi.biokimia,"+
                        "asuhan_gizi.fisik_klinis,asuhan_gizi.alergi_telur,asuhan_gizi.alergi_susu_sapi,asuhan_gizi.alergi_kacang,asuhan_gizi.alergi_gluten,"+
                        "asuhan_gizi.alergi_udang,asuhan_gizi.alergi_ikan,asuhan_gizi.alergi_hazelnut,asuhan_gizi.pola_makan,asuhan_gizi.riwayat_personal,"+
                        "asuhan_gizi.diagnosis,asuhan_gizi.intervensi_gizi,asuhan_gizi.monitoring_evaluasi,asuhan_gizi.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join asuhan_gizi on reg_periksa.no_rawat=asuhan_gizi.no_rawat "+
                        "inner join petugas on asuhan_gizi.nip=petugas.nip where "+
                        "asuhan_gizi.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by asuhan_gizi.tanggal",param);
                }else{
                    Valid.MyReportqry("rptDataAsuhanGiziPasien.jasper","report","::[ Data Asuhan Gizi Pasien ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,asuhan_gizi.tanggal,"+
                        "asuhan_gizi.antropometri_bb,asuhan_gizi.antropometri_tb,asuhan_gizi.antropometri_imt,asuhan_gizi.antropometri_lla,"+
                        "asuhan_gizi.antropometri_tl,asuhan_gizi.antropometri_ulna,asuhan_gizi.antropometri_bbideal,asuhan_gizi.antropometri_bbperu,"+
                        "asuhan_gizi.antropometri_tbperu,asuhan_gizi.antropometri_bbpertb,asuhan_gizi.antropometri_llaperu,asuhan_gizi.biokimia,"+
                        "asuhan_gizi.fisik_klinis,asuhan_gizi.alergi_telur,asuhan_gizi.alergi_susu_sapi,asuhan_gizi.alergi_kacang,asuhan_gizi.alergi_gluten,"+
                        "asuhan_gizi.alergi_udang,asuhan_gizi.alergi_ikan,asuhan_gizi.alergi_hazelnut,asuhan_gizi.pola_makan,asuhan_gizi.riwayat_personal,"+
                        "asuhan_gizi.diagnosis,asuhan_gizi.intervensi_gizi,asuhan_gizi.monitoring_evaluasi,asuhan_gizi.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join asuhan_gizi on reg_periksa.no_rawat=asuhan_gizi.no_rawat "+
                        "inner join petugas on asuhan_gizi.nip=petugas.nip where "+
                        "asuhan_gizi.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                        "asuhan_gizi.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                        "asuhan_gizi.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        "asuhan_gizi.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and asuhan_gizi.nip like '%"+TCari.getText().trim()+"%' or "+
                        "asuhan_gizi.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and asuhan_gizi.diagnosis like '%"+TCari.getText().trim()+"%' or "+
                        "asuhan_gizi.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and petugas.nama like '%"+TCari.getText().trim()+"%' order by asuhan_gizi.tanggal",param);
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
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    ChkInput.setSelected(true);
                    isForm(); 
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,KdPetugas,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TNoResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoResepKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoResepKeyPressed

    private void KodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterKeyPressed
        Valid.pindah(evt,TCari,KdPetugas);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void Benar2TidakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Benar2TidakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Benar2TidakActionPerformed

    private void TglRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglRwKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglRwKeyPressed

    private void JamRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamRwKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamRwKeyPressed

    private void JamRwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JamRwActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamRwActionPerformed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgTelaahObat dialog = new DlgTelaahObat(new javax.swing.JFrame(), true);
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
    private widget.RadioButton APt;
    private widget.RadioButton APy;
    private widget.RadioButton Benar1Tidak;
    private widget.RadioButton Benar1Ya;
    private widget.RadioButton Benar2Tidak;
    private widget.RadioButton Benar2Ya;
    private widget.RadioButton Benar3Tidak;
    private widget.RadioButton Benar3Ya;
    private widget.RadioButton Benar4Tidak;
    private widget.RadioButton Benar4Ya;
    private widget.RadioButton Benar5Tidak;
    private widget.RadioButton Benar5Tidak1;
    private widget.RadioButton Benar5Ya;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.RadioButton CPOt;
    private widget.RadioButton CPOy;
    private widget.RadioButton CampuranTidak;
    private widget.RadioButton CampuranYa;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.RadioButton DosisTidak;
    private widget.RadioButton DosisYa;
    private widget.PanelBiasa FormInput;
    private widget.RadioButton IKOt;
    private widget.RadioButton IKOy;
    private widget.RadioButton IdentitasTidak;
    private widget.RadioButton IdentitasYa;
    private widget.TextBox JamRw;
    private widget.RadioButton JumlahTidak;
    private widget.RadioButton JumlahYa;
    private widget.RadioButton KAOt;
    private widget.RadioButton KAOy;
    private widget.TextBox KdPetugas;
    private widget.TextBox KodeDokter;
    private widget.Label LCount;
    private widget.RadioButton NOKDOt;
    private widget.RadioButton NOKDOy;
    private widget.TextBox NamaDokter;
    private widget.TextBox NmPetugas;
    private widget.RadioButton ObatTidak;
    private widget.RadioButton ObatYa;
    private widget.RadioButton POt;
    private widget.RadioButton POy;
    private javax.swing.JPanel PanelInput;
    private widget.RadioButton ResepTidak;
    private widget.RadioButton ResepYa;
    private widget.RadioButton RuteTidak;
    private widget.RadioButton RuteYa;
    private widget.ScrollPane Scroll;
    private widget.TextBox Status;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoResep;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TglRw;
    private widget.RadioButton Tidak1Tidak;
    private widget.RadioButton Tidak1Ya;
    private widget.RadioButton Tidak2Tidak;
    private widget.RadioButton Tidak2Ya;
    private widget.RadioButton Tidak3Tidak;
    private widget.RadioButton Tidak3Ya;
    private widget.RadioButton WPOt;
    private widget.RadioButton WPOy;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup10;
    private javax.swing.ButtonGroup buttonGroup11;
    private javax.swing.ButtonGroup buttonGroup12;
    private javax.swing.ButtonGroup buttonGroup13;
    private javax.swing.ButtonGroup buttonGroup14;
    private javax.swing.ButtonGroup buttonGroup15;
    private javax.swing.ButtonGroup buttonGroup16;
    private javax.swing.ButtonGroup buttonGroup17;
    private javax.swing.ButtonGroup buttonGroup18;
    private javax.swing.ButtonGroup buttonGroup19;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup20;
    private javax.swing.ButtonGroup buttonGroup21;
    private javax.swing.ButtonGroup buttonGroup22;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.ButtonGroup buttonGroup9;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.Label label42;
    private widget.Label label43;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            /*"No.Resep","Tanggal Resep","Jam Resep","No.Rawat","No.RM","Nama Pasien","Kode Dokter","Dokter","NIP","Petugas","Status","Resep Lengkap","Identitas Pasien Sesuai","Obat Tepat",
            "Campuran Obat Stabil","Jumlah Tepat","Dosis/Kekuatan/Frekuensi Tepat","Rute Pemberian Tepat","Tidak Ada Interaksi Obat","Tidak Ada Duplikasi","Tidak Ada Alergi/Kontraindikasi",
            "Benar Pasien","Benar Obat","Benar Dosis Pemberian","Benar Rute Pemberian","Benar Waktu Pemberian","Tanggal Telaah","Jam Telaah*/
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
//                        "select resep_obat.no_resep,resep_obat.no_rawat,resep_obat.kd_dokter,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_obat.status,"+
//                        "telaah_resep_obat.no_resep,telaah_resep_obat.tanggal,telaah_resep_obat.jam,telaah_resep_obat.no_rawat,"+
//                        "telaah_resep_obat.kd_dokter,telaah_resep_obat.nip,telaah_resep_obat.status,telaah_resep_obat.resep,"+
//                        "telaah_resep_obat.identitas,telaah_resep_obat.obat,telaah_resep_obat.campuran,telaah_resep_obat.jumlah,"+
//                        "telaah_resep_obat.dosis,telaah_resep_obat.rute,telaah_resep_obat.tidak1,telaah_resep_obat.tidak2,telaah_resep_obat.tidak3,"+
//                        "telaah_resep_obat.benar1,telaah_resep_obat.benar2,telaah_resep_obat.benar3,telaah_resep_obat.benar4,telaah_resep_obat.benar5,"+
//                        "telaah_resep_obat.tgl,telaah_resep_obat.jam_telaah,dokter.nm_dokter,petugas.nama "+
//                        "from resep_obat inner join telaah_resep_obat on resep_obat.no_resep=telaah_resep_obat.no_resep "+
//                        "inner join dokter on telaah_resep_obat.kd_dokter=dokter.kd_dokter "+
//                        "inner join petugas on telaah_resep_obat.nip=petugas.nip where "+
//                        "telaah_resep_obat.tanggal between ? and ? order by telaah_resep_obat.tanggal");
                        "select resep_obat.no_resep,resep_obat.no_rawat,resep_obat.kd_dokter,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_obat.status,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,"+
                        "telaah_resep_obat.no_resep,telaah_resep_obat.tanggal,telaah_resep_obat.jam,telaah_resep_obat.no_rawat,"+
                        "telaah_resep_obat.kd_dokter,telaah_resep_obat.nip,telaah_resep_obat.status,telaah_resep_obat.resep,"+
                        "telaah_resep_obat.identitas,telaah_resep_obat.obat,telaah_resep_obat.campuran,telaah_resep_obat.jumlah,"+
                        "telaah_resep_obat.dosis,telaah_resep_obat.rute,telaah_resep_obat.tidak1,telaah_resep_obat.tidak2,telaah_resep_obat.tidak3,"+
                        "telaah_resep_obat.benar1,telaah_resep_obat.benar2,telaah_resep_obat.benar3,telaah_resep_obat.benar4,telaah_resep_obat.benar5,telaah_resep_obat.NOKDO,telaah_resep_obat.AP,telaah_resep_obat.IKO,telaah_resep_obat.PO,telaah_resep_obat.CPO,telaah_resep_obat.WPO,telaah_resep_obat.KAO,"+
                        "telaah_resep_obat.tgl,telaah_resep_obat.jam_telaah,dokter.nm_dokter,petugas.nama "+
                        "from resep_obat inner join telaah_resep_obat on resep_obat.no_resep=telaah_resep_obat.no_resep "+
                        "inner join dokter on telaah_resep_obat.kd_dokter=dokter.kd_dokter "+
                        "inner join petugas on telaah_resep_obat.nip=petugas.nip "+
                        "inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "telaah_resep_obat.tanggal between ? and ? order by telaah_resep_obat.tanggal");
            }else{
                ps=koneksi.prepareStatement(
//                        "select resep_obat.no_resep,resep_obat.no_rawat,resep_obat.kd_dokter,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_obat.status,"+
//                        "telaah_resep_obat.no_resep,telaah_resep_obat.tanggal,telaah_resep_obat.jam,telaah_resep_obat.no_rawat,"+
//                        "telaah_resep_obat.kd_dokter,telaah_resep_obat.nip,telaah_resep_obat.status,telaah_resep_obat.resep,"+
//                        "telaah_resep_obat.identitas,telaah_resep_obat.obat,telaah_resep_obat.campuran,telaah_resep_obat.jumlah,"+
//                        "telaah_resep_obat.dosis,telaah_resep_obat.rute,telaah_resep_obat.tidak1,telaah_resep_obat.tidak2,telaah_resep_obat.tidak3,"+
//                        "telaah_resep_obat.benar1,telaah_resep_obat.benar2,telaah_resep_obat.benar3,telaah_resep_obat.benar4,telaah_resep_obat.benar5,"+
//                        "telaah_resep_obat.tgl,telaah_resep_obat.jam_telaah,dokter.nm_dokter,petugas.nama "+
//                        "from resep_obat inner join telaah_resep_obat on resep_obat.no_resep=telaah_resep_obat.no_resep "+
//                        "inner join dokter on telaah_resep_obat.kd_dokter=dokter.kd_dokter "+
//                        "inner join petugas on telaah_resep_obat.nip=petugas.nip where "+
//                        "telaah_resep_obat.tanggal between ? and ? and resep_obat.no_resep like ? or "+
//                        "telaah_resep_obat.tanggal between ? and ? and resep_obat.no_rawat like ? or "+
//                        "telaah_resep_obat.tanggal between ? and ? and telaah_resep_obat.jam like ? or "+
//                        "telaah_resep_obat.tanggal between ? and ? and telaah_resep_obat.nip like ? or "+
//                        "telaah_resep_obat.tanggal between ? and ? and telaah_resep_obat.kd_dokter like ? or "+
//                        "telaah_resep_obat.tanggal between ? and ? and petugas.nama like ? order by telaah_resep_obat.tanggal");
                        "select resep_obat.no_resep,resep_obat.no_rawat,resep_obat.kd_dokter,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_obat.status,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,"+
                        "telaah_resep_obat.no_resep,telaah_resep_obat.tanggal,telaah_resep_obat.jam,telaah_resep_obat.no_rawat,"+
                        "telaah_resep_obat.kd_dokter,telaah_resep_obat.nip,telaah_resep_obat.status,telaah_resep_obat.resep,"+
                        "telaah_resep_obat.identitas,telaah_resep_obat.obat,telaah_resep_obat.campuran,telaah_resep_obat.jumlah,"+
                        "telaah_resep_obat.dosis,telaah_resep_obat.rute,telaah_resep_obat.tidak1,telaah_resep_obat.tidak2,telaah_resep_obat.tidak3,"+
                        "telaah_resep_obat.benar1,telaah_resep_obat.benar2,telaah_resep_obat.benar3,telaah_resep_obat.benar4,telaah_resep_obat.benar5,telaah_resep_obat.NOKDO,telaah_resep_obat.AP,telaah_resep_obat.IKO,telaah_resep_obat.PO,telaah_resep_obat.CPO,telaah_resep_obat.WPO,telaah_resep_obat.KAO,"+
                        "telaah_resep_obat.tgl,telaah_resep_obat.jam_telaah,dokter.nm_dokter,petugas.nama "+
                        "from resep_obat inner join telaah_resep_obat on resep_obat.no_resep=telaah_resep_obat.no_resep "+
                        "inner join dokter on telaah_resep_obat.kd_dokter=dokter.kd_dokter "+
                        "inner join petugas on telaah_resep_obat.nip=petugas.nip "+
                        "inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "telaah_resep_obat.tanggal between ? and ? and resep_obat.no_resep like ? or "+
                        "telaah_resep_obat.tanggal between ? and ? and resep_obat.no_rawat like ? or "+
                        "telaah_resep_obat.tanggal between ? and ? and telaah_resep_obat.jam like ? or "+
                        "telaah_resep_obat.tanggal between ? and ? and telaah_resep_obat.nip like ? or "+
                        "telaah_resep_obat.tanggal between ? and ? and telaah_resep_obat.kd_dokter like ? or "+
                        "telaah_resep_obat.tanggal between ? and ? and petugas.nama like ? order by telaah_resep_obat.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(15,"%"+TCari.getText()+"%");
                    ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(18,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_resep"),rs.getString("tanggal"),rs.getString("jam"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("status"),rs.getString("resep"),rs.getString("identitas"),
                        rs.getString("obat"),rs.getString("campuran"),rs.getString("jumlah"),rs.getString("dosis"),
                        rs.getString("rute"),rs.getString("tidak1"),rs.getString("tidak2"),rs.getString("tidak3"),
                        rs.getString("benar1"),rs.getString("benar2"),rs.getString("benar3"),rs.getString("benar4"),
                        rs.getString("benar5"),rs.getString("NOKDO"),rs.getString("AP"),rs.getString("IKO"),rs.getString("PO"),rs.getString("CPO"),
                        rs.getString("WPO"),rs.getString("KAO"),
                        rs.getString("tgl"),rs.getString("jam_telaah")
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
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
//        TNoResep.setText("");
        ResepYa.setSelected(true);
        IdentitasYa.setSelected(true);
        ObatYa.setSelected(true);
        CampuranYa.setSelected(true);
        JumlahYa.setSelected(true);
        DosisYa.setSelected(true);
        RuteYa.setSelected(true);
        Tidak1Ya.setSelected(true);
        Tidak2Ya.setSelected(true);
        Tidak3Ya.setSelected(true);
        Benar1Ya.setSelected(true);
        Benar2Ya.setSelected(true);
        Benar3Ya.setSelected(true);
        Benar4Ya.setSelected(true);
        Benar5Ya.setSelected(true);
        NOKDOy.setSelected(true);
        APy.setSelected(true);
        IKOy.setSelected(true);
        POy.setSelected(true);
        CPOy.setSelected(true);
        WPOy.setSelected(true);
        KAOy.setSelected(true);
        TNoResep.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoResep.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TglRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            JamRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            KodeDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NamaDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
            Status.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());          
            if(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().equals("Ya")){
                ResepYa.setSelected(true);
            }else{
                ResepTidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString().equals("Ya")){
                IdentitasYa.setSelected(true);
            }else{
                IdentitasTidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().equals("Ya")){
                ObatYa.setSelected(true);
            }else{
                ObatTidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString().equals("Ya")){
                CampuranYa.setSelected(true);
            }else{
                CampuranTidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString().equals("Ya")){
                JumlahYa.setSelected(true);
            }else{
                JumlahTidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString().equals("Ya")){
                DosisYa.setSelected(true);
            }else{
                DosisTidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString().equals("Ya")){
                RuteYa.setSelected(true);
            }else{
                RuteTidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString().equals("Ya")){
                Tidak1Ya.setSelected(true);
            }else{
                Tidak1Tidak.setSelected(true);
            } 
            if(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString().equals("Ya")){
                Tidak2Ya.setSelected(true);
            }else{
                Tidak2Tidak.setSelected(true);
            } 
            if(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString().equals("Ya")){
                Tidak3Ya.setSelected(true);
            }else{
                Tidak3Tidak.setSelected(true);
            } 
            if(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString().equals("Ya")){
                Benar1Ya.setSelected(true);
            }else{
                Benar1Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString().equals("Ya")){
                Benar2Ya.setSelected(true);
            }else{
                Benar2Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString().equals("Ya")){
                Benar3Ya.setSelected(true);
            }else{
                Benar3Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString().equals("Ya")){
                Benar4Ya.setSelected(true);
            }else{
                Benar4Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString().equals("Ya")){
                Benar5Ya.setSelected(true);
            }else{
                Benar5Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString().equals("Ya")){
                NOKDOy.setSelected(true);
            }else{
                NOKDOt.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString().equals("Ya")){
                APy.setSelected(true);
            }else{
                APt.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString().equals("Ya")){
                IKOy.setSelected(true);
            }else{
                IKOt.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString().equals("Ya")){
                POy.setSelected(true);
            }else{
                POt.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString().equals("Ya")){
                CPOy.setSelected(true);
            }else{
                CPOt.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString().equals("Ya")){
                WPOy.setSelected(true);
            }else{
                WPOt.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString().equals("Ya")){
                KAOy.setSelected(true);
            }else{
                KAOt.setSelected(true);
            }
//            TglRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
//            JamRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
//            Valid.SetTgl(TglRw,tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
        }
    }

    private void isRawat() {
//         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
//         Sequel.cariIsi("select no_resep from resep_obat where no_resep=? ",TNoResep.getText());
         Sequel.cariIsi("select kd_dokter from resep_obat where no_resep=? ",KodeDokter,TNoResep.getText());
         Sequel.cariIsi("select no_rawat from resep_obat where no_resep=? ",TNoRw,TNoResep.getText());
         Sequel.cariIsi("select tgl_peresepan from resep_obat where no_resep=? ",TglRw,TNoResep.getText());
         Sequel.cariIsi("select jam_peresepan from resep_obat where no_resep=? ",JamRw,TNoResep.getText());
         Sequel.cariIsi("select status from resep_obat where no_resep=? ",Status,TNoResep.getText());
//         Sequel.cariIsi("select no_resep from resep_obat where tgl_perawatan=? ",TNoResep,TglRw.getText());
//         Sequel.cariIsi("select jam_peresepan from resep_obat where no_resep=? ",JamRw,TNoResep.getText());
//         Sequel.cariIsi("select reg_periksa.kd_dokter,dokter.nm_dokter from reg_periksa INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter where no_rawat=? ",KodeDokter,NamaDokter.getText());
//         Sequel.cariIsi("select diagnosa_awal from kamar_inap where diagnosa_awal<>'' and no_rawat=? ",DiagnosaMasukRanap,TNoRw.getText());
    }

    private void isPsien() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=? ",NamaDokter,KodeDokter.getText());
//        Sequel.cariIsi("select no_rawat from resep_obat where no_resep=?",TNoRw,TNoResep.getText());
//        Sequel.cariIsi("select tgl_peresepan from resep_obat where no_resep=? ",TglRw,TNoResep.getText());
//        Sequel.cariIsi("select jam_peresepan from resep_obat where no_resep=? ",JamRw,TNoResep.getText());
//        Sequel.cariIsi("select no_resep from resep_obat where no_resep=? ",TNoResep,TNoResep.getText());
//        Sequel.cariIsi("select reg_periksa.kd_dokter,dokter.nm_dokter from reg_periksa INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter where no_rawat=? ",KodeDokter,NamaDokter.getText());
//        Sequel.cariIsi("select if(jk='L','Laki-Laki','Perempuan') from pasien where no_rkm_medis=? ",Jk,TNoRM.getText());
//        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl2) {
//        TNoRw.setText(norwt);
        TNoResep.setText(norwt);
        TCari.setText(norwt);
//        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        Sequel.cariIsi("select tgl_perawatan from resep_obat where no_resep='"+norwt+"'", DTPCari1);
//        Sequel.cariIsi("select no_rawat from resep_obat where no_resep=? ",TNoRw,TNoResep.getText());
//        Sequel.cariIsi("select tgl_peresepan from resep_obat where no_resep where no_rawat='"+noresep+"'", DTPCari1);
//        Sequel.cariIsi("select reg_periksa.kd_dokter,dokter.nm_dokter from reg_periksa INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter where no_rawat=? ",KodeDokter,NamaDokter.getText());
        DTPCari2.setDate(tgl2);    
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
    }
    
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
//            PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-122));
            PanelInput.setPreferredSize(new Dimension(WIDTH,440));
            scrollInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            scrollInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getresep_obat());
        BtnHapus.setEnabled(akses.getresep_obat());
        BtnEdit.setEnabled(akses.getresep_obat());
        BtnPrint.setEnabled(akses.getresep_obat());   
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPetugas,KdPetugas.getText());
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }

    
}
