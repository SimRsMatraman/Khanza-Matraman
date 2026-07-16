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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Rectangle;
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
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;
import keuangan.DlgKamar;
import static org.apache.commons.lang.BooleanUtils.toBoolean;
import simrskhanza.DlgCariPoli;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianPasienTerminal extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgKamar kamar=new DlgKamar(null,false);
    private DlgCariPoli ralan=new DlgCariPoli(null,false);
    private String finger="";
    private StringBuilder htmlContent;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianPasienTerminal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        updateFormSize();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Tgl.Lahir","JK","Tanggal","Kd Ruang","Ruangan", 
            "NIP","Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(124);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(200);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
        Diagnosa.setDocument(new batasInput((int)100).getKata(Diagnosa));
        RPS.setDocument(new batasInput((int)300).getKata(RPS));
        RPD.setDocument(new batasInput((int)100).getKata(RPD));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        SPO.setDocument(new batasInput((byte)5).getKata(SPO));
        KebutuhanSpiritual.setDocument(new batasInput((int)300).getKata(KebutuhanSpiritual));
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
        
        kamar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kamar.getTable().getSelectedRow()!= -1){
//                    if(pilihan==1){
                        KdRuang.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),1).toString());
                        NamaRuang.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),3).toString());
                        KdRuang.requestFocus();
//                    }else{
//                        KdRuang1.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),1).toString());
//                        NmRuang1.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),3).toString());
//                        KdRuang1.requestFocus();
//                    }
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
        
        ralan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ralan.getTable().getSelectedRow()!= -1){
                        KdRuang.setText(ralan.getTable().getValueAt(ralan.getTable().getSelectedRow(),0).toString());
                        NamaRuang.setText(ralan.getTable().getValueAt(ralan.getTable().getSelectedRow(),1).toString());
                        KdRuang.requestFocus();
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
        jam();
        
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

        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        scrollPane3 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        KeadaanUmum = new widget.ComboBox();
        jLabel17 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel41 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel42 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        SkalaNyeri = new widget.ComboBox();
        jLabel28 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel35 = new widget.Label();
        SPO = new widget.TextBox();
        jLabel29 = new widget.Label();
        jLabel44 = new widget.Label();
        KlinisMenjelangKematian = new widget.ComboBox();
        MenjelangAjal = new widget.ComboBox();
        jLabel43 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        Diagnosa = new widget.TextArea();
        jLabel34 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        KebutuhanSpiritual = new widget.TextArea();
        jLabel31 = new widget.Label();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianPasienTerminal = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        JK = new widget.TextBox();
        BtnPrint = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        FormInput1 = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        jLabel16 = new widget.Label();
        jLabel18 = new widget.Label();
        jLabel8 = new widget.Label();
        jLabel36 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        TglLahir = new widget.TextBox();
        btnPetugas = new widget.Button();
        NamaPetugas = new widget.TextBox();
        NIP = new widget.TextBox();
        ChkKejadian = new widget.CekBox();
        Detik = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Jam = new widget.ComboBox();
        Tanggal = new widget.Tanggal();
        KdRuang = new widget.TextBox();
        NamaRuang = new widget.TextBox();
        btnRuang = new widget.Button();
        btnPoli = new widget.Button();
        jSeparator2 = new javax.swing.JSeparator();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel32 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        Dyspneu = new javax.swing.JCheckBox();
        TakTeratur = new javax.swing.JCheckBox();
        AdaSekret = new javax.swing.JCheckBox();
        NapasCepat = new javax.swing.JCheckBox();
        NapasMulut = new javax.swing.JCheckBox();
        Saturasi = new javax.swing.JCheckBox();
        NapasLambat = new javax.swing.JCheckBox();
        Mukrosa = new javax.swing.JCheckBox();
        TAK = new javax.swing.JCheckBox();
        Mual = new javax.swing.JCheckBox();
        SulitMenelan = new javax.swing.JCheckBox();
        Inkontinensia = new javax.swing.JCheckBox();
        PenurunanGerak = new javax.swing.JCheckBox();
        DistensiAbdomen = new javax.swing.JCheckBox();
        TAK1 = new javax.swing.JCheckBox();
        SulitBerbicara = new javax.swing.JCheckBox();
        InkontinensiaUrine = new javax.swing.JCheckBox();
        Nyeri = new javax.swing.JComboBox<>();
        KetNyeri = new widget.TextBox();
        Bercak = new javax.swing.JCheckBox();
        Gelisah = new javax.swing.JCheckBox();
        Lemas = new javax.swing.JCheckBox();
        TAK2 = new javax.swing.JCheckBox();
        KulitDingin = new javax.swing.JCheckBox();
        TekananDarah = new javax.swing.JCheckBox();
        NadiLambat = new javax.swing.JCheckBox();
        AktivitasFisik = new javax.swing.JCheckBox();
        FaktorLainnya = new widget.TextBox();
        PindahPosisi = new javax.swing.JCheckBox();
        Mual1 = new javax.swing.JCheckBox();
        Konstipasi = new javax.swing.JCheckBox();
        NyeriAkut = new javax.swing.JCheckBox();
        PolaNapas = new javax.swing.JCheckBox();
        PerubahanPersepsiSensori = new javax.swing.JCheckBox();
        NyeriKronis = new javax.swing.JCheckBox();
        BersihanJalan = new javax.swing.JCheckBox();
        DefisitPerawatan = new javax.swing.JCheckBox();
        Spiritual = new javax.swing.JComboBox<>();
        KetSpritual = new widget.TextBox();
        PerluDoa = new javax.swing.JComboBox<>();
        PerluBimbingan = new javax.swing.JComboBox<>();
        PerluPendamping = new javax.swing.JComboBox<>();
        InginDihubungi = new javax.swing.JComboBox<>();
        NamaDihubungi = new widget.TextBox();
        HubunganDihubungi = new widget.TextBox();
        AlamatDihubungi = new widget.TextBox();
        HPDihubungi = new widget.TextBox();
        TetapDirawat = new javax.swing.JCheckBox();
        RumahSiap = new javax.swing.JComboBox<>();
        DirawatDirumah = new javax.swing.JCheckBox();
        MerawatDirumah = new javax.swing.JComboBox<>();
        HomeCare = new javax.swing.JComboBox<>();
        Menyangkal = new javax.swing.JCheckBox();
        RasaBersalah = new javax.swing.JCheckBox();
        Ansietas = new javax.swing.JCheckBox();
        SedihMenangis = new javax.swing.JCheckBox();
        Takut = new javax.swing.JCheckBox();
        DistresSpiritual = new javax.swing.JCheckBox();
        Marah = new javax.swing.JCheckBox();
        Ketidakberdayaan = new javax.swing.JCheckBox();
        Marah2 = new javax.swing.JCheckBox();
        Letih = new javax.swing.JCheckBox();
        Bersalah = new javax.swing.JCheckBox();
        PenurunanKonsentrasi = new javax.swing.JCheckBox();
        GangguanTidur = new javax.swing.JCheckBox();
        PerubahanKebiasaan = new javax.swing.JCheckBox();
        KeluargaKurang = new javax.swing.JCheckBox();
        KetidakmampuanMemenuhi = new javax.swing.JCheckBox();
        KeputusanPerawatan = new javax.swing.JCheckBox();
        KopingIndividu = new javax.swing.JCheckBox();
        Distres = new javax.swing.JCheckBox();
        PerluDampingi = new javax.swing.JCheckBox();
        KeluargaDapat = new javax.swing.JCheckBox();
        SahabatDapat = new javax.swing.JCheckBox();
        KebutuhanLainnya = new widget.TextBox();
        KebutuhanTidak = new javax.swing.JCheckBox();
        KebutuhanAutopsi = new javax.swing.JCheckBox();
        KebutuhanDonasi = new javax.swing.JCheckBox();
        Marah3 = new javax.swing.JCheckBox();
        Letih1 = new javax.swing.JCheckBox();
        Bersalah1 = new javax.swing.JCheckBox();
        PenurunanKonsentrasi1 = new javax.swing.JCheckBox();
        KetidakMampuanMemenuhi1 = new javax.swing.JCheckBox();
        KopingIndividu1 = new javax.swing.JCheckBox();
        Distres1 = new javax.swing.JCheckBox();
        PerubahanKebiasaan2 = new javax.swing.JCheckBox();
        Sedih = new javax.swing.JCheckBox();
        Depresi = new javax.swing.JCheckBox();
        GangguanTidur1 = new javax.swing.JCheckBox();

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(RPS);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RPD);

        KeadaanUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sedang", "Jelek", "Sangat Jelek" }));
        KeadaanUmum.setName("KeadaanUmum"); // NOI18N
        KeadaanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumKeyPressed(evt);
            }
        });

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("x/menit");
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel41.setText(":");
        jLabel41.setName("jLabel41"); // NOI18N

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("°C");
        jLabel27.setName("jLabel27"); // NOI18N

        jLabel24.setText("Suhu :");
        jLabel24.setName("jLabel24"); // NOI18N

        jLabel42.setText("Kesadaran :");
        jLabel42.setName("jLabel42"); // NOI18N

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Delirium", "Samnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });

        jLabel22.setText("Tekanan Darah :");
        jLabel22.setName("jLabel22"); // NOI18N

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N

        SkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });

        jLabel28.setText("Skala Nyeri :");
        jLabel28.setName("jLabel28"); // NOI18N

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("%");
        jLabel35.setName("jLabel35"); // NOI18N

        SPO.setFocusTraversalPolicyProvider(true);
        SPO.setName("SPO"); // NOI18N
        SPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPOKeyPressed(evt);
            }
        });

        jLabel29.setText("SpO2 :");
        jLabel29.setName("jLabel29"); // NOI18N

        jLabel44.setText("Tanda-tanda Klinis Menjelang Kematian :");
        jLabel44.setName("jLabel44"); // NOI18N

        KlinisMenjelangKematian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kurang/Tidak Responsif", "Nadi Cepat & Melemah", "Pernapasan Tidak Teratur & Dangkal/Ngorok", "Kulit Pucat", "Ekstrimitas Dingin", "Defekasi/Berkemih Tidak Sengaja", "Mata Tidak Respon Cahaya", "Penurunan Tonus Otot" }));
        KlinisMenjelangKematian.setSelectedIndex(2);
        KlinisMenjelangKematian.setName("KlinisMenjelangKematian"); // NOI18N
        KlinisMenjelangKematian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KlinisMenjelangKematianKeyPressed(evt);
            }
        });

        MenjelangAjal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menolak", "Marah", "Menawar", "Depresi", "Menerima" }));
        MenjelangAjal.setName("MenjelangAjal"); // NOI18N
        MenjelangAjal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenjelangAjalKeyPressed(evt);
            }
        });

        jLabel43.setText(":");
        jLabel43.setName("jLabel43"); // NOI18N

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Diagnosa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosa.setColumns(20);
        Diagnosa.setRows(5);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(Diagnosa);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Diagnosa :");
        jLabel34.setName("jLabel34"); // NOI18N

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        KebutuhanSpiritual.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KebutuhanSpiritual.setColumns(20);
        KebutuhanSpiritual.setRows(5);
        KebutuhanSpiritual.setName("KebutuhanSpiritual"); // NOI18N
        KebutuhanSpiritual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebutuhanSpiritualKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(KebutuhanSpiritual);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Kebutuhan Spiritual Pasien/Keluarga :");
        jLabel31.setName("jLabel31"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianPasienTerminal.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianPasienTerminal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianPasienTerminal.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianPasienTerminal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianPasienTerminal.setText("Formulir Penilaian Pasien Terminal");
        MnPenilaianPasienTerminal.setName("MnPenilaianPasienTerminal"); // NOI18N
        MnPenilaianPasienTerminal.setPreferredSize(new java.awt.Dimension(290, 26));
        MnPenilaianPasienTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianPasienTerminalActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianPasienTerminal);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Pasien Terminal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-07-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-07-2026" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 326));
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

        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(50, 100));

        FormInput1.setBackground(new java.awt.Color(250, 255, 245));
        FormInput1.setBorder(null);
        FormInput1.setForeground(new java.awt.Color(255, 255, 255));
        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(80, 273));
        FormInput1.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput1.add(jLabel4);
        jLabel4.setBounds(20, 10, 80, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput1.add(jLabel16);
        jLabel16.setBounds(20, 40, 80, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput1.add(jLabel18);
        jLabel18.setBounds(420, 40, 70, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput1.add(jLabel8);
        jLabel8.setBounds(640, 10, 60, 23);

        jLabel36.setText("Ruang Rawat :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput1.add(jLabel36);
        jLabel36.setBounds(10, 70, 90, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput1.add(TNoRw);
        TNoRw.setBounds(100, 10, 136, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput1.add(TNoRM);
        TNoRM.setBounds(240, 10, 112, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput1.add(TPasien);
        TPasien.setBounds(350, 10, 285, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput1.add(TglLahir);
        TglLahir.setBounds(710, 10, 100, 23);

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
        FormInput1.add(btnPetugas);
        btnPetugas.setBounds(780, 40, 28, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput1.add(NamaPetugas);
        NamaPetugas.setBounds(590, 40, 187, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormInput1.add(NIP);
        NIP.setBounds(490, 40, 94, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput1.add(ChkKejadian);
        ChkKejadian.setBounds(390, 40, 23, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput1.add(Detik);
        Detik.setBounds(330, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput1.add(Menit);
        Menit.setBounds(260, 40, 62, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput1.add(Jam);
        Jam.setBounds(200, 40, 62, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-07-2026" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput1.add(Tanggal);
        Tanggal.setBounds(100, 40, 90, 23);

        KdRuang.setEditable(false);
        KdRuang.setHighlighter(null);
        KdRuang.setName("KdRuang"); // NOI18N
        KdRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdRuangActionPerformed(evt);
            }
        });
        KdRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRuangKeyPressed(evt);
            }
        });
        FormInput1.add(KdRuang);
        KdRuang.setBounds(110, 70, 94, 23);

        NamaRuang.setEditable(false);
        NamaRuang.setName("NamaRuang"); // NOI18N
        FormInput1.add(NamaRuang);
        NamaRuang.setBounds(210, 70, 187, 23);

        btnRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnRuang.setMnemonic('2');
        btnRuang.setToolTipText("ALt+2");
        btnRuang.setName("btnRuang"); // NOI18N
        btnRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRuangActionPerformed(evt);
            }
        });
        btnRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnRuangKeyPressed(evt);
            }
        });
        FormInput1.add(btnRuang);
        btnRuang.setBounds(400, 70, 28, 23);

        btnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli.setMnemonic('2');
        btnPoli.setToolTipText("ALt+2");
        btnPoli.setName("btnPoli"); // NOI18N
        btnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliActionPerformed(evt);
            }
        });
        btnPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoliKeyPressed(evt);
            }
        });
        FormInput1.add(btnPoli);
        btnPoli.setBounds(430, 70, 30, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput1.add(jSeparator2);
        jSeparator2.setBounds(0, 102, 810, 3);

        jScrollPane1.setViewportView(FormInput1);

        PanelInput.add(jScrollPane1, java.awt.BorderLayout.PAGE_START);

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(660, 273));
        FormInput.setLayout(null);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("1. Gejala seperti mau muntah dan kesulitan bernapas");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(20, 10, 270, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("1.1. Kegawatan pernapasan:");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(40, 30, 220, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("1.2. Kehilangan tonus otot");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(40, 120, 220, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("1.3. Nyeri");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(40, 210, 220, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("1.4. Perlambatan sirkulasi");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(40, 270, 220, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText(" 2. Faktor-faktor yang meningkatkan dan membangkitkan gejala fisik");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(20, 380, 450, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Lainnya:");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(370, 400, 50, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("3. Manajemen gejala saat ini dan respon pasien");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(20, 430, 330, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("4. Orientasi spiritual pasien dan keluarga");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(20, 520, 220, 23);

        jLabel47.setText("Apakah perlu pelayanan spiritual?");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(20, 540, 180, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("5. Urusan dan kebutuhan spiritual pasien dan keluarga seperti putus asa, penderitaan, rasa bersalah, dan pengampunan");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(20, 570, 740, 23);

        jLabel50.setText("Perlu didoakan : ");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(30, 590, 90, 23);

        jLabel51.setText("Perlu bimbingan rohani : ");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(250, 590, 130, 23);

        jLabel52.setText("Perlu pendampingan rohani : ");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(500, 590, 150, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("6.1. Apakah ada orang yang ingin dihubungi saat ini?");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(30, 640, 260, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("6. Status psikososial dan keluarga");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(20, 620, 740, 23);

        jLabel55.setText("Nama : ");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(50, 670, 40, 23);

        jLabel56.setText("Hubungan : ");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(420, 670, 60, 23);

        jLabel57.setText("Alamat : ");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(40, 700, 50, 23);

        jLabel58.setText("No Telephone : ");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(420, 700, 90, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("6.2. Bagalmana rencana perawatan selanjutnya");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(30, 730, 330, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("Apakah lingkungan rumah sudah disiapkan?");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(50, 780, 220, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("Jika Ya, apakah ada yang mampu merawat di rumah? ");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(50, 810, 280, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("Jika Tidak, apakah perlu difasilitasi RS (home care)?");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(50, 840, 280, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("6.3. Reaksi pasien atas penyakitnya");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(30, 870, 330, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("6.4. Reaksi keluarga atas penyakit pasien");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(30, 960, 330, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("7. Kebutuhan dukungan atau kelonggaran pelayanan bagi pasien, keluarga, dan pemberi layanan lain");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(20, 1120, 690, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("8. Apakah ada kebutuhan terhadap alternatif atau pelayanan lain:");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(20, 1250, 690, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("9. Faktor risiko bagi keluarga yang ditinggalkan:");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(20, 1340, 330, 23);

        Dyspneu.setText("Dyspneu");
        Dyspneu.setName("Dyspneu"); // NOI18N
        Dyspneu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DyspneuActionPerformed(evt);
            }
        });
        FormInput.add(Dyspneu);
        Dyspneu.setBounds(60, 50, 210, 22);

        TakTeratur.setText("Napas tak teratur");
        TakTeratur.setName("TakTeratur"); // NOI18N
        FormInput.add(TakTeratur);
        TakTeratur.setBounds(60, 70, 210, 22);

        AdaSekret.setText("Ada sekret");
        AdaSekret.setName("AdaSekret"); // NOI18N
        FormInput.add(AdaSekret);
        AdaSekret.setBounds(60, 90, 210, 22);

        NapasCepat.setText("Napas cepat dan dangkal");
        NapasCepat.setName("NapasCepat"); // NOI18N
        NapasCepat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NapasCepatActionPerformed(evt);
            }
        });
        FormInput.add(NapasCepat);
        NapasCepat.setBounds(280, 50, 260, 22);

        NapasMulut.setText("Napas melalui mulut");
        NapasMulut.setName("NapasMulut"); // NOI18N
        FormInput.add(NapasMulut);
        NapasMulut.setBounds(280, 70, 250, 22);

        Saturasi.setText("Saturasi O₂ < 90%");
        Saturasi.setName("Saturasi"); // NOI18N
        Saturasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaturasiActionPerformed(evt);
            }
        });
        FormInput.add(Saturasi);
        Saturasi.setBounds(280, 90, 260, 20);

        NapasLambat.setText("Napas lambat");
        NapasLambat.setName("NapasLambat"); // NOI18N
        FormInput.add(NapasLambat);
        NapasLambat.setBounds(550, 50, 200, 22);

        Mukrosa.setText("Mukosa oral kering");
        Mukrosa.setName("Mukrosa"); // NOI18N
        Mukrosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MukrosaActionPerformed(evt);
            }
        });
        FormInput.add(Mukrosa);
        Mukrosa.setBounds(550, 70, 210, 22);

        TAK.setText("T.A.K.");
        TAK.setName("TAK"); // NOI18N
        FormInput.add(TAK);
        TAK.setBounds(550, 90, 200, 22);

        Mual.setText("Mual");
        Mual.setName("Mual"); // NOI18N
        Mual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MualActionPerformed(evt);
            }
        });
        FormInput.add(Mual);
        Mual.setBounds(60, 140, 190, 22);

        SulitMenelan.setText("Sulit menelan");
        SulitMenelan.setName("SulitMenelan"); // NOI18N
        FormInput.add(SulitMenelan);
        SulitMenelan.setBounds(60, 160, 190, 22);

        Inkontinensia.setText("Inkontinensia alvi");
        Inkontinensia.setName("Inkontinensia"); // NOI18N
        FormInput.add(Inkontinensia);
        Inkontinensia.setBounds(60, 180, 190, 22);

        PenurunanGerak.setText("Penurunan gerakan tubuh");
        PenurunanGerak.setName("PenurunanGerak"); // NOI18N
        FormInput.add(PenurunanGerak);
        PenurunanGerak.setBounds(280, 140, 250, 22);

        DistensiAbdomen.setText("Distensi abdomen");
        DistensiAbdomen.setName("DistensiAbdomen"); // NOI18N
        FormInput.add(DistensiAbdomen);
        DistensiAbdomen.setBounds(280, 160, 190, 22);

        TAK1.setText("T.A.K.");
        TAK1.setName("TAK1"); // NOI18N
        FormInput.add(TAK1);
        TAK1.setBounds(280, 180, 190, 22);

        SulitBerbicara.setText("Sulit Berbicara");
        SulitBerbicara.setName("SulitBerbicara"); // NOI18N
        FormInput.add(SulitBerbicara);
        SulitBerbicara.setBounds(550, 140, 220, 22);

        InkontinensiaUrine.setText("Inkontinensia urine");
        InkontinensiaUrine.setName("InkontinensiaUrine"); // NOI18N
        InkontinensiaUrine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InkontinensiaUrineActionPerformed(evt);
            }
        });
        FormInput.add(InkontinensiaUrine);
        InkontinensiaUrine.setBounds(550, 160, 220, 22);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya", " " }));
        Nyeri.setName("Nyeri"); // NOI18N
        FormInput.add(Nyeri);
        Nyeri.setBounds(60, 230, 140, 23);

        KetNyeri.setName("KetNyeri"); // NOI18N
        FormInput.add(KetNyeri);
        KetNyeri.setBounds(220, 230, 540, 23);

        Bercak.setText("Bercak dan sianosis pada ekstremitas");
        Bercak.setName("Bercak"); // NOI18N
        Bercak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BercakActionPerformed(evt);
            }
        });
        FormInput.add(Bercak);
        Bercak.setBounds(60, 290, 290, 23);

        Gelisah.setText("Gelisah");
        Gelisah.setName("Gelisah"); // NOI18N
        FormInput.add(Gelisah);
        Gelisah.setBounds(60, 310, 290, 23);

        Lemas.setText("Lemas");
        Lemas.setName("Lemas"); // NOI18N
        FormInput.add(Lemas);
        Lemas.setBounds(60, 330, 290, 23);

        TAK2.setText("T.A.K.");
        TAK2.setName("TAK2"); // NOI18N
        FormInput.add(TAK2);
        TAK2.setBounds(60, 350, 290, 23);

        KulitDingin.setText("Kulit dingin dan berkeringat");
        KulitDingin.setName("KulitDingin"); // NOI18N
        FormInput.add(KulitDingin);
        KulitDingin.setBounds(390, 290, 340, 23);

        TekananDarah.setText("Tekanan darah menurun");
        TekananDarah.setName("TekananDarah"); // NOI18N
        FormInput.add(TekananDarah);
        TekananDarah.setBounds(390, 310, 280, 23);

        NadiLambat.setText("Nadi lambat dan lemah");
        NadiLambat.setName("NadiLambat"); // NOI18N
        FormInput.add(NadiLambat);
        NadiLambat.setBounds(390, 330, 310, 23);

        AktivitasFisik.setText("Melakukan aktivitas fisik");
        AktivitasFisik.setName("AktivitasFisik"); // NOI18N
        AktivitasFisik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AktivitasFisikActionPerformed(evt);
            }
        });
        FormInput.add(AktivitasFisik);
        AktivitasFisik.setBounds(40, 400, 190, 23);

        FaktorLainnya.setName("FaktorLainnya"); // NOI18N
        FormInput.add(FaktorLainnya);
        FaktorLainnya.setBounds(420, 400, 330, 23);

        PindahPosisi.setText("Pindah posisi");
        PindahPosisi.setName("PindahPosisi"); // NOI18N
        PindahPosisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PindahPosisiActionPerformed(evt);
            }
        });
        FormInput.add(PindahPosisi);
        PindahPosisi.setBounds(230, 400, 120, 23);

        Mual1.setText("Mual");
        Mual1.setName("Mual1"); // NOI18N
        Mual1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mual1ActionPerformed(evt);
            }
        });
        FormInput.add(Mual1);
        Mual1.setBounds(40, 450, 190, 23);

        Konstipasi.setText("Konstipasi");
        Konstipasi.setName("Konstipasi"); // NOI18N
        FormInput.add(Konstipasi);
        Konstipasi.setBounds(40, 470, 190, 23);

        NyeriAkut.setText("Nyeri akut");
        NyeriAkut.setName("NyeriAkut"); // NOI18N
        FormInput.add(NyeriAkut);
        NyeriAkut.setBounds(40, 490, 190, 23);

        PolaNapas.setText("Pola napas tidak efektif");
        PolaNapas.setName("PolaNapas"); // NOI18N
        FormInput.add(PolaNapas);
        PolaNapas.setBounds(260, 450, 250, 23);

        PerubahanPersepsiSensori.setText("Perubahan persepsi sensori");
        PerubahanPersepsiSensori.setName("PerubahanPersepsiSensori"); // NOI18N
        FormInput.add(PerubahanPersepsiSensori);
        PerubahanPersepsiSensori.setBounds(260, 470, 190, 23);

        NyeriKronis.setText("Nyeri kronis");
        NyeriKronis.setName("NyeriKronis"); // NOI18N
        FormInput.add(NyeriKronis);
        NyeriKronis.setBounds(260, 490, 190, 23);

        BersihanJalan.setText("Bersihan jalan napas tidak efektif");
        BersihanJalan.setName("BersihanJalan"); // NOI18N
        FormInput.add(BersihanJalan);
        BersihanJalan.setBounds(530, 450, 220, 23);

        DefisitPerawatan.setText("Defisit perawatan diri");
        DefisitPerawatan.setName("DefisitPerawatan"); // NOI18N
        FormInput.add(DefisitPerawatan);
        DefisitPerawatan.setBounds(530, 470, 220, 23);

        Spiritual.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya", " " }));
        Spiritual.setName("Spiritual"); // NOI18N
        FormInput.add(Spiritual);
        Spiritual.setBounds(220, 540, 140, 23);

        KetSpritual.setName("KetSpritual"); // NOI18N
        FormInput.add(KetSpritual);
        KetSpritual.setBounds(380, 540, 360, 23);

        PerluDoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya", " " }));
        PerluDoa.setName("PerluDoa"); // NOI18N
        FormInput.add(PerluDoa);
        PerluDoa.setBounds(120, 590, 80, 23);

        PerluBimbingan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya", " " }));
        PerluBimbingan.setName("PerluBimbingan"); // NOI18N
        FormInput.add(PerluBimbingan);
        PerluBimbingan.setBounds(380, 590, 80, 23);

        PerluPendamping.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya", " " }));
        PerluPendamping.setName("PerluPendamping"); // NOI18N
        FormInput.add(PerluPendamping);
        PerluPendamping.setBounds(650, 590, 80, 23);

        InginDihubungi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya", " " }));
        InginDihubungi.setName("InginDihubungi"); // NOI18N
        FormInput.add(InginDihubungi);
        InginDihubungi.setBounds(290, 640, 80, 23);

        NamaDihubungi.setName("NamaDihubungi"); // NOI18N
        FormInput.add(NamaDihubungi);
        NamaDihubungi.setBounds(90, 670, 320, 23);

        HubunganDihubungi.setName("HubunganDihubungi"); // NOI18N
        FormInput.add(HubunganDihubungi);
        HubunganDihubungi.setBounds(490, 670, 240, 23);

        AlamatDihubungi.setName("AlamatDihubungi"); // NOI18N
        FormInput.add(AlamatDihubungi);
        AlamatDihubungi.setBounds(90, 700, 320, 23);

        HPDihubungi.setName("HPDihubungi"); // NOI18N
        FormInput.add(HPDihubungi);
        HPDihubungi.setBounds(510, 700, 220, 23);

        TetapDirawat.setText("Tetap dirawat di RS");
        TetapDirawat.setName("TetapDirawat"); // NOI18N
        TetapDirawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TetapDirawatActionPerformed(evt);
            }
        });
        FormInput.add(TetapDirawat);
        TetapDirawat.setBounds(50, 750, 190, 23);

        RumahSiap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya", " " }));
        RumahSiap.setName("RumahSiap"); // NOI18N
        FormInput.add(RumahSiap);
        RumahSiap.setBounds(320, 780, 140, 23);

        DirawatDirumah.setText("Dirawat di rumah");
        DirawatDirumah.setName("DirawatDirumah"); // NOI18N
        DirawatDirumah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DirawatDirumahActionPerformed(evt);
            }
        });
        FormInput.add(DirawatDirumah);
        DirawatDirumah.setBounds(270, 750, 280, 23);

        MerawatDirumah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya", " " }));
        MerawatDirumah.setName("MerawatDirumah"); // NOI18N
        FormInput.add(MerawatDirumah);
        MerawatDirumah.setBounds(320, 810, 140, 23);

        HomeCare.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya", " " }));
        HomeCare.setName("HomeCare"); // NOI18N
        FormInput.add(HomeCare);
        HomeCare.setBounds(320, 840, 140, 23);

        Menyangkal.setText("Menyangkal");
        Menyangkal.setName("Menyangkal"); // NOI18N
        Menyangkal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenyangkalActionPerformed(evt);
            }
        });
        FormInput.add(Menyangkal);
        Menyangkal.setBounds(50, 890, 190, 23);

        RasaBersalah.setText("Rasa bersalah");
        RasaBersalah.setName("RasaBersalah"); // NOI18N
        FormInput.add(RasaBersalah);
        RasaBersalah.setBounds(50, 910, 190, 23);

        Ansietas.setText("Ansietas");
        Ansietas.setName("Ansietas"); // NOI18N
        FormInput.add(Ansietas);
        Ansietas.setBounds(50, 930, 190, 23);

        SedihMenangis.setText("Sedih/menangis");
        SedihMenangis.setName("SedihMenangis"); // NOI18N
        FormInput.add(SedihMenangis);
        SedihMenangis.setBounds(270, 890, 250, 23);

        Takut.setText("Takut");
        Takut.setName("Takut"); // NOI18N
        FormInput.add(Takut);
        Takut.setBounds(270, 910, 190, 23);

        DistresSpiritual.setText("Distres spiritual");
        DistresSpiritual.setName("DistresSpiritual"); // NOI18N
        FormInput.add(DistresSpiritual);
        DistresSpiritual.setBounds(270, 930, 190, 23);

        Marah.setText("Marah");
        Marah.setName("Marah"); // NOI18N
        FormInput.add(Marah);
        Marah.setBounds(540, 890, 220, 23);

        Ketidakberdayaan.setText("Ketidakberdayaan");
        Ketidakberdayaan.setName("Ketidakberdayaan"); // NOI18N
        FormInput.add(Ketidakberdayaan);
        Ketidakberdayaan.setBounds(540, 910, 220, 23);

        Marah2.setText("Marah");
        Marah2.setName("Marah2"); // NOI18N
        Marah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Marah2ActionPerformed(evt);
            }
        });
        FormInput.add(Marah2);
        Marah2.setBounds(50, 980, 240, 23);

        Letih.setText("Letih/lelah");
        Letih.setName("Letih"); // NOI18N
        FormInput.add(Letih);
        Letih.setBounds(50, 1000, 240, 23);

        Bersalah.setText("Rasa bersalah");
        Bersalah.setName("Bersalah"); // NOI18N
        FormInput.add(Bersalah);
        Bersalah.setBounds(50, 1020, 240, 23);

        PenurunanKonsentrasi.setText("Penurunan konsentrasi");
        PenurunanKonsentrasi.setName("PenurunanKonsentrasi"); // NOI18N
        FormInput.add(PenurunanKonsentrasi);
        PenurunanKonsentrasi.setBounds(50, 1040, 270, 23);

        GangguanTidur.setText("Gangguan tidur");
        GangguanTidur.setName("GangguanTidur"); // NOI18N
        FormInput.add(GangguanTidur);
        GangguanTidur.setBounds(330, 980, 380, 23);

        PerubahanKebiasaan.setText("Perubahan kebiasaan pola komunikasi");
        PerubahanKebiasaan.setName("PerubahanKebiasaan"); // NOI18N
        FormInput.add(PerubahanKebiasaan);
        PerubahanKebiasaan.setBounds(330, 1000, 390, 23);

        KeluargaKurang.setText("Keluarga kurang berkomunikasi dengan pasien");
        KeluargaKurang.setName("KeluargaKurang"); // NOI18N
        FormInput.add(KeluargaKurang);
        KeluargaKurang.setBounds(330, 1020, 430, 23);

        KetidakmampuanMemenuhi.setText("Ketidakmampuan memenuhi peran");
        KetidakmampuanMemenuhi.setName("KetidakmampuanMemenuhi"); // NOI18N
        FormInput.add(KetidakmampuanMemenuhi);
        KetidakmampuanMemenuhi.setBounds(330, 1040, 350, 23);

        KeputusanPerawatan.setText("Keluarga kurang berpartisipasi membuat keputusan dalam perawatan pasien");
        KeputusanPerawatan.setName("KeputusanPerawatan"); // NOI18N
        FormInput.add(KeputusanPerawatan);
        KeputusanPerawatan.setBounds(50, 1060, 590, 23);

        KopingIndividu.setText("Koping individu tak efektif");
        KopingIndividu.setName("KopingIndividu"); // NOI18N
        FormInput.add(KopingIndividu);
        KopingIndividu.setBounds(50, 1080, 270, 23);

        Distres.setText("Distres spiritual");
        Distres.setName("Distres"); // NOI18N
        FormInput.add(Distres);
        Distres.setBounds(330, 1080, 350, 23);

        PerluDampingi.setText("Pasien perlu didampingi keluarga");
        PerluDampingi.setName("PerluDampingi"); // NOI18N
        PerluDampingi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerluDampingiActionPerformed(evt);
            }
        });
        FormInput.add(PerluDampingi);
        PerluDampingi.setBounds(30, 1140, 480, 23);

        KeluargaDapat.setText("Keluarga dapat mengunjungi pasien di luar waktu berkunjung");
        KeluargaDapat.setName("KeluargaDapat"); // NOI18N
        FormInput.add(KeluargaDapat);
        KeluargaDapat.setBounds(30, 1160, 480, 23);

        SahabatDapat.setText("Sahabat dapat mengunjungi pasien di luar waktu berkunjung");
        SahabatDapat.setName("SahabatDapat"); // NOI18N
        FormInput.add(SahabatDapat);
        SahabatDapat.setBounds(30, 1180, 480, 23);

        KebutuhanLainnya.setName("KebutuhanLainnya"); // NOI18N
        FormInput.add(KebutuhanLainnya);
        KebutuhanLainnya.setBounds(30, 1210, 480, 23);

        KebutuhanTidak.setText("Tidak");
        KebutuhanTidak.setName("KebutuhanTidak"); // NOI18N
        KebutuhanTidak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KebutuhanTidakActionPerformed(evt);
            }
        });
        FormInput.add(KebutuhanTidak);
        KebutuhanTidak.setBounds(30, 1270, 480, 23);

        KebutuhanAutopsi.setText("Autopsi");
        KebutuhanAutopsi.setName("KebutuhanAutopsi"); // NOI18N
        FormInput.add(KebutuhanAutopsi);
        KebutuhanAutopsi.setBounds(30, 1290, 480, 23);

        KebutuhanDonasi.setText("Donasi organ");
        KebutuhanDonasi.setName("KebutuhanDonasi"); // NOI18N
        FormInput.add(KebutuhanDonasi);
        KebutuhanDonasi.setBounds(30, 1310, 480, 23);

        Marah3.setText("Marah");
        Marah3.setName("Marah3"); // NOI18N
        Marah3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Marah3ActionPerformed(evt);
            }
        });
        FormInput.add(Marah3);
        Marah3.setBounds(40, 1360, 240, 23);

        Letih1.setText("Letih/lelah");
        Letih1.setName("Letih1"); // NOI18N
        FormInput.add(Letih1);
        Letih1.setBounds(40, 1380, 240, 23);

        Bersalah1.setText("Rasa bersalah");
        Bersalah1.setName("Bersalah1"); // NOI18N
        FormInput.add(Bersalah1);
        Bersalah1.setBounds(40, 1400, 240, 23);

        PenurunanKonsentrasi1.setText("Penurunan konsentrasi");
        PenurunanKonsentrasi1.setName("PenurunanKonsentrasi1"); // NOI18N
        FormInput.add(PenurunanKonsentrasi1);
        PenurunanKonsentrasi1.setBounds(40, 1420, 270, 23);

        KetidakMampuanMemenuhi1.setText("Ketidakmampuan memenuhi peran yang diharapkan");
        KetidakMampuanMemenuhi1.setName("KetidakMampuanMemenuhi1"); // NOI18N
        KetidakMampuanMemenuhi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KetidakMampuanMemenuhi1ActionPerformed(evt);
            }
        });
        FormInput.add(KetidakMampuanMemenuhi1);
        KetidakMampuanMemenuhi1.setBounds(40, 1440, 590, 23);

        KopingIndividu1.setText("Koping individu tak efektif");
        KopingIndividu1.setName("KopingIndividu1"); // NOI18N
        FormInput.add(KopingIndividu1);
        KopingIndividu1.setBounds(40, 1460, 270, 23);

        Distres1.setText("Distres spiritual");
        Distres1.setName("Distres1"); // NOI18N
        FormInput.add(Distres1);
        Distres1.setBounds(320, 1460, 350, 23);

        PerubahanKebiasaan2.setText("Perubahan kebiasaan pola komunikasi");
        PerubahanKebiasaan2.setName("PerubahanKebiasaan2"); // NOI18N
        FormInput.add(PerubahanKebiasaan2);
        PerubahanKebiasaan2.setBounds(320, 1420, 350, 23);

        Sedih.setText("Sedih/Menangis");
        Sedih.setName("Sedih"); // NOI18N
        FormInput.add(Sedih);
        Sedih.setBounds(320, 1400, 430, 23);

        Depresi.setText("Depresi");
        Depresi.setName("Depresi"); // NOI18N
        FormInput.add(Depresi);
        Depresi.setBounds(320, 1380, 390, 23);

        GangguanTidur1.setText("Gangguan tidur");
        GangguanTidur1.setName("GangguanTidur1"); // NOI18N
        FormInput.add(GangguanTidur1);
        GangguanTidur1.setBounds(320, 1360, 380, 23);

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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{
            if(Sequel.menyimpantf(
                "penilaian_pasien_terminal",
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                "Data",
                93,
                new String[]{
                    TNoRw.getText(),
                    Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    KdRuang.getText(),

                    Dyspneu.isSelected()?"true":"false",
                    TakTeratur.isSelected()?"true":"false",
                    AdaSekret.isSelected()?"true":"false",
                    NapasCepat.isSelected()?"true":"false",
                    NapasMulut.isSelected()?"true":"false",
                    Saturasi.isSelected()?"true":"false",
                    NapasLambat.isSelected()?"true":"false",
                    Mukrosa.isSelected()?"true":"false",
                    TAK.isSelected()?"true":"false",
                    Mual.isSelected()?"true":"false",
                    SulitMenelan.isSelected()?"true":"false",
                    Inkontinensia.isSelected()?"true":"false",
                    PenurunanGerak.isSelected()?"true":"false",
                    DistensiAbdomen.isSelected()?"true":"false",
                    TAK1.isSelected()?"true":"false",
                    SulitBerbicara.isSelected()?"true":"false",
                    InkontinensiaUrine.isSelected()?"true":"false",

                    Nyeri.getSelectedItem().toString(),
                    KetNyeri.getText(),

                    Bercak.isSelected()?"true":"false",
                    Gelisah.isSelected()?"true":"false",
                    Lemas.isSelected()?"true":"false",
                    TAK2.isSelected()?"true":"false",
                    KulitDingin.isSelected()?"true":"false",

                    TekananDarah.isSelected()?"true":"false",
                    NadiLambat.isSelected()?"true":"false",
                    AktivitasFisik.isSelected()?"true":"false",

                    FaktorLainnya.getText(),

                    PindahPosisi.isSelected()?"true":"false",
                    Mual1.isSelected()?"true":"false",
                    Konstipasi.isSelected()?"true":"false",
                    NyeriAkut.isSelected()?"true":"false",
                    PolaNapas.isSelected()?"true":"false",
                    PerubahanPersepsiSensori.isSelected()?"true":"false",
                    NyeriKronis.isSelected()?"true":"false",
                    BersihanJalan.isSelected()?"true":"false",
                    DefisitPerawatan.isSelected()?"true":"false",

                    Spiritual.getSelectedItem().toString(),
                    KetSpritual.getText(),

                    PerluBimbingan.getSelectedItem().toString(),
                    PerluPendamping.getSelectedItem().toString(),
                    InginDihubungi.getSelectedItem().toString(),
                    PerluDoa.getSelectedItem().toString(),

                    NamaDihubungi.getText(),
                    HubunganDihubungi.getText(),
                    AlamatDihubungi.getText(),
                    HPDihubungi.getText(),

                    TetapDirawat.isSelected()?"true":"false",
                    RumahSiap.getSelectedItem().toString(),
                    DirawatDirumah.isSelected()?"true":"false",
                    MerawatDirumah.getSelectedItem().toString(),
                    HomeCare.getSelectedItem().toString(),

                    Menyangkal.isSelected()?"true":"false",
                    RasaBersalah.isSelected()?"true":"false",
                    Ansietas.isSelected()?"true":"false",
                    SedihMenangis.isSelected()?"true":"false",
                    Takut.isSelected()?"true":"false",
                    DistresSpiritual.isSelected()?"true":"false",
                    Marah.isSelected()?"true":"false",
                    Ketidakberdayaan.isSelected()?"true":"false",
                    Marah2.isSelected()?"true":"false",
                    Letih.isSelected()?"true":"false",
                    Bersalah.isSelected()?"true":"false",
                    PenurunanKonsentrasi.isSelected()?"true":"false",
                    GangguanTidur.isSelected()?"true":"false",
                    PerubahanKebiasaan.isSelected()?"true":"false",
                    KeluargaKurang.isSelected()?"true":"false",
                    KetidakmampuanMemenuhi.isSelected()?"true":"false",
                    KeputusanPerawatan.isSelected()?"true":"false",
                    KopingIndividu.isSelected()?"true":"false",
                    Distres.isSelected()?"true":"false",

                    PerluDampingi.isSelected()?"true":"false",
                    KeluargaDapat.isSelected()?"true":"false",
                    SahabatDapat.isSelected()?"true":"false",

                    KebutuhanLainnya.getText(),

                    KebutuhanTidak.isSelected()?"true":"false",
                    KebutuhanAutopsi.isSelected()?"true":"false",
                    KebutuhanDonasi.isSelected()?"true":"false",

                    Marah3.isSelected()?"true":"false",
                    Letih1.isSelected()?"true":"false",
                    Bersalah1.isSelected()?"true":"false",
                    PenurunanKonsentrasi1.isSelected()?"true":"false",
                    KetidakMampuanMemenuhi1.isSelected()?"true":"false",
                    KopingIndividu1.isSelected()?"true":"false",
                    Distres1.isSelected()?"true":"false",
                    PerubahanKebiasaan2.isSelected()?"true":"false",
                    Sedih.isSelected()?"true":"false",
                    Depresi.isSelected()?"true":"false",
                    GangguanTidur1.isSelected()?"true":"false",

                    NIP.getText()
                }
            )==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),Diagnosa.getText(),RPS.getText(), 
                    NIP.getText(),NamaPetugas.getText()
                });
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KebutuhanSpiritual,BtnBatal);
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
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString())){
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())){
                        ganti();
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
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.R.M.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>JK</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Uraian Penyakit/Kondisi Pasien Saat Ini</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit/Kondisi Sebelumnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesadaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu(°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SpO2(%)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Nyeri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tahap Menjelang Ajal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanda-tanda Klinis Menjelang Kematian</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kebutuhan Spiritual Pasien/Keluarga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas</b></td>"+
                    "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                           "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2400px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
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
                bg.close();

                File f = new File("DataPenilaianPasienTerminal.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2400px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN PASIEN TERMINAL<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
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
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            //GCS.requestFocus();
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
        Valid.pindah(evt,Detik,RPS);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnPenilaianPasienTerminalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianPasienTerminalActionPerformed
//        if(tbObat.getSelectedRow()>-1){
//            Map<String, Object> param = new HashMap<>();
//            param.put("namars",akses.getnamars());
//            param.put("alamatrs",akses.getalamatrs());
//            param.put("kotars",akses.getkabupatenrs());
//            param.put("propinsirs",akses.getpropinsirs());
//            param.put("kontakrs",akses.getkontakrs());
//            param.put("emailrs",akses.getemailrs());   
//            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
//            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
//            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),21).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),20).toString():finger)+"\n"+Tanggal.getSelectedItem());
//            Valid.MyReportqry("rptFormulirPenilaianPasienTerminal.jasper","report","::[ Formulir Penilaian Pasien Terminal ]::",
//                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_pasien_terminal.tanggal,"+
//                    "penilaian_pasien_terminal.diagnosa,penilaian_pasien_terminal.rps,penilaian_pasien_terminal.rpd,penilaian_pasien_terminal.keadaan_umum,"+
//                    "penilaian_pasien_terminal.kesadaran,penilaian_pasien_terminal.td,penilaian_pasien_terminal.nadi,penilaian_pasien_terminal.suhu,"+
//                    "penilaian_pasien_terminal.rr,penilaian_pasien_terminal.spo2,penilaian_pasien_terminal.skala_nyeri,penilaian_pasien_terminal.tahap_pasien_menjelang_ajal,"+
//                    "penilaian_pasien_terminal.tanda_klinis_menjelang_kematian,penilaian_pasien_terminal.kebutuhan_spiritual_pasien,penilaian_pasien_terminal.nip,petugas.nama "+
//                    "from penilaian_pasien_terminal inner join reg_periksa on penilaian_pasien_terminal.no_rawat=reg_periksa.no_rawat "+
//                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
//                    "inner join petugas on penilaian_pasien_terminal.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
//        }
    }//GEN-LAST:event_MnPenilaianPasienTerminalActionPerformed

    private void KebutuhanSpiritualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebutuhanSpiritualKeyPressed
        Valid.pindah2(evt,Diagnosa,BtnSimpan);
    }//GEN-LAST:event_KebutuhanSpiritualKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt,RPS,KeadaanUmum);
    }//GEN-LAST:event_RPDKeyPressed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        Valid.pindah2(evt,KlinisMenjelangKematian,KebutuhanSpiritual);
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void KeadaanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumKeyPressed
        Valid.pindah(evt,RPD,Kesadaran);
    }//GEN-LAST:event_KeadaanUmumKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,KeadaanUmum,TD);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Kesadaran,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,Suhu);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,SPO,SkalaNyeri);
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,Nadi,SPO);
    }//GEN-LAST:event_SuhuKeyPressed

    private void SPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPOKeyPressed
        Valid.pindah(evt,Suhu,RR);
    }//GEN-LAST:event_SPOKeyPressed

    private void SkalaNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriKeyPressed
        Valid.pindah(evt,RR,MenjelangAjal);
    }//GEN-LAST:event_SkalaNyeriKeyPressed

    private void MenjelangAjalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenjelangAjalKeyPressed
        Valid.pindah(evt,SkalaNyeri,KlinisMenjelangKematian);
    }//GEN-LAST:event_MenjelangAjalKeyPressed

    private void KlinisMenjelangKematianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KlinisMenjelangKematianKeyPressed
        Valid.pindah(evt,MenjelangAjal,Diagnosa);
    }//GEN-LAST:event_KlinisMenjelangKematianKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,btnPetugas,RPD);
    }//GEN-LAST:event_RPSKeyPressed

    private void KdRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRuangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdRuangKeyPressed

    private void btnRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRuangActionPerformed
        akses.setform("RMPenilaianPasienTerminal");
        kamar.emptTeks();
        kamar.isCek();
        kamar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kamar.setLocationRelativeTo(internalFrame1);
        kamar.setVisible(true);
    }//GEN-LAST:event_btnRuangActionPerformed

    private void btnRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnRuangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRuangKeyPressed

    private void KdRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdRuangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdRuangActionPerformed

    private void NapasCepatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NapasCepatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NapasCepatActionPerformed

    private void SaturasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaturasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SaturasiActionPerformed

    private void DyspneuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DyspneuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DyspneuActionPerformed

    private void MualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MualActionPerformed

    private void BercakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BercakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BercakActionPerformed

    private void AktivitasFisikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AktivitasFisikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AktivitasFisikActionPerformed

    private void PindahPosisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PindahPosisiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PindahPosisiActionPerformed

    private void Mual1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mual1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Mual1ActionPerformed

    private void TetapDirawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TetapDirawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TetapDirawatActionPerformed

    private void DirawatDirumahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DirawatDirumahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DirawatDirumahActionPerformed

    private void MenyangkalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenyangkalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenyangkalActionPerformed

    private void Marah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Marah2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Marah2ActionPerformed

    private void PerluDampingiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerluDampingiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerluDampingiActionPerformed

    private void KebutuhanTidakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KebutuhanTidakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebutuhanTidakActionPerformed

    private void Marah3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Marah3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Marah3ActionPerformed

    private void MukrosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MukrosaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MukrosaActionPerformed

    private void InkontinensiaUrineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InkontinensiaUrineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InkontinensiaUrineActionPerformed

    private void KetidakMampuanMemenuhi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KetidakMampuanMemenuhi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetidakMampuanMemenuhi1ActionPerformed

    private void btnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliActionPerformed
        akses.setform("RMPenilaianPasienTerminal");
        ralan.isCek();        
        ralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ralan.setLocationRelativeTo(internalFrame1);
        ralan.setVisible(true);
    }//GEN-LAST:event_btnPoliActionPerformed

    private void btnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoliKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianPasienTerminal dialog = new RMPenilaianPasienTerminal(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox AdaSekret;
    private javax.swing.JCheckBox AktivitasFisik;
    private widget.TextBox AlamatDihubungi;
    private javax.swing.JCheckBox Ansietas;
    private javax.swing.JCheckBox Bercak;
    private javax.swing.JCheckBox Bersalah;
    private javax.swing.JCheckBox Bersalah1;
    private javax.swing.JCheckBox BersihanJalan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JCheckBox DefisitPerawatan;
    private javax.swing.JCheckBox Depresi;
    private widget.ComboBox Detik;
    private widget.TextArea Diagnosa;
    private javax.swing.JCheckBox DirawatDirumah;
    private javax.swing.JCheckBox DistensiAbdomen;
    private javax.swing.JCheckBox Distres;
    private javax.swing.JCheckBox Distres1;
    private javax.swing.JCheckBox DistresSpiritual;
    private javax.swing.JCheckBox Dyspneu;
    private widget.TextBox FaktorLainnya;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private javax.swing.JCheckBox GangguanTidur;
    private javax.swing.JCheckBox GangguanTidur1;
    private javax.swing.JCheckBox Gelisah;
    private widget.TextBox HPDihubungi;
    private javax.swing.JComboBox<String> HomeCare;
    private widget.TextBox HubunganDihubungi;
    private javax.swing.JComboBox<String> InginDihubungi;
    private javax.swing.JCheckBox Inkontinensia;
    private javax.swing.JCheckBox InkontinensiaUrine;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.TextBox KdRuang;
    private widget.ComboBox KeadaanUmum;
    private javax.swing.JCheckBox KebutuhanAutopsi;
    private javax.swing.JCheckBox KebutuhanDonasi;
    private widget.TextBox KebutuhanLainnya;
    private widget.TextArea KebutuhanSpiritual;
    private javax.swing.JCheckBox KebutuhanTidak;
    private javax.swing.JCheckBox KeluargaDapat;
    private javax.swing.JCheckBox KeluargaKurang;
    private javax.swing.JCheckBox KeputusanPerawatan;
    private widget.ComboBox Kesadaran;
    private widget.TextBox KetNyeri;
    private widget.TextBox KetSpritual;
    private javax.swing.JCheckBox KetidakMampuanMemenuhi1;
    private javax.swing.JCheckBox Ketidakberdayaan;
    private javax.swing.JCheckBox KetidakmampuanMemenuhi;
    private widget.ComboBox KlinisMenjelangKematian;
    private javax.swing.JCheckBox Konstipasi;
    private javax.swing.JCheckBox KopingIndividu;
    private javax.swing.JCheckBox KopingIndividu1;
    private javax.swing.JCheckBox KulitDingin;
    private widget.Label LCount;
    private javax.swing.JCheckBox Lemas;
    private javax.swing.JCheckBox Letih;
    private javax.swing.JCheckBox Letih1;
    private widget.editorpane LoadHTML;
    private javax.swing.JCheckBox Marah;
    private javax.swing.JCheckBox Marah2;
    private javax.swing.JCheckBox Marah3;
    private widget.ComboBox Menit;
    private widget.ComboBox MenjelangAjal;
    private javax.swing.JCheckBox Menyangkal;
    private javax.swing.JComboBox<String> MerawatDirumah;
    private javax.swing.JMenuItem MnPenilaianPasienTerminal;
    private javax.swing.JCheckBox Mual;
    private javax.swing.JCheckBox Mual1;
    private javax.swing.JCheckBox Mukrosa;
    private widget.TextBox NIP;
    private widget.TextBox Nadi;
    private javax.swing.JCheckBox NadiLambat;
    private widget.TextBox NamaDihubungi;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NamaRuang;
    private javax.swing.JCheckBox NapasCepat;
    private javax.swing.JCheckBox NapasLambat;
    private javax.swing.JCheckBox NapasMulut;
    private javax.swing.JComboBox<String> Nyeri;
    private javax.swing.JCheckBox NyeriAkut;
    private javax.swing.JCheckBox NyeriKronis;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JCheckBox PenurunanGerak;
    private javax.swing.JCheckBox PenurunanKonsentrasi;
    private javax.swing.JCheckBox PenurunanKonsentrasi1;
    private javax.swing.JComboBox<String> PerluBimbingan;
    private javax.swing.JCheckBox PerluDampingi;
    private javax.swing.JComboBox<String> PerluDoa;
    private javax.swing.JComboBox<String> PerluPendamping;
    private javax.swing.JCheckBox PerubahanKebiasaan;
    private javax.swing.JCheckBox PerubahanKebiasaan2;
    private javax.swing.JCheckBox PerubahanPersepsiSensori;
    private javax.swing.JCheckBox PindahPosisi;
    private javax.swing.JCheckBox PolaNapas;
    private widget.TextArea RPD;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private javax.swing.JCheckBox RasaBersalah;
    private javax.swing.JComboBox<String> RumahSiap;
    private widget.TextBox SPO;
    private javax.swing.JCheckBox SahabatDapat;
    private javax.swing.JCheckBox Saturasi;
    private widget.ScrollPane Scroll;
    private javax.swing.JCheckBox Sedih;
    private javax.swing.JCheckBox SedihMenangis;
    private widget.ComboBox SkalaNyeri;
    private javax.swing.JComboBox<String> Spiritual;
    private widget.TextBox Suhu;
    private javax.swing.JCheckBox SulitBerbicara;
    private javax.swing.JCheckBox SulitMenelan;
    private javax.swing.JCheckBox TAK;
    private javax.swing.JCheckBox TAK1;
    private javax.swing.JCheckBox TAK2;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JCheckBox TakTeratur;
    private javax.swing.JCheckBox Takut;
    private widget.Tanggal Tanggal;
    private javax.swing.JCheckBox TekananDarah;
    private javax.swing.JCheckBox TetapDirawat;
    private widget.TextBox TglLahir;
    private widget.Button btnPetugas;
    private widget.Button btnPoli;
    private widget.Button btnRuang;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "SELECT " +
                    "a.no_rawat, " +
                    "b.no_rkm_medis, " +
                    "c.nm_pasien, " +
                    "c.tgl_lahir, " +
                    "c.jk, " +
                    "a.tanggal, " +
                    "COALESCE(d.kd_kamar, f.kd_poli) AS kode, " +
                    "COALESCE(e.nm_bangsal, f.nm_poli) AS ruang, " +
                    "a.nip, " +
                    "g.nama " +
                    "FROM penilaian_pasien_terminal a " +
                    "INNER JOIN reg_periksa b ON a.no_rawat=b.no_rawat " +
                    "INNER JOIN pasien c ON b.no_rkm_medis=c.no_rkm_medis " +
                    "LEFT JOIN kamar d ON d.kd_kamar=a.kd_ruangan " +
                    "LEFT JOIN bangsal e ON e.kd_bangsal=d.kd_bangsal " +
                    "LEFT JOIN poliklinik f ON f.kd_poli = a.kd_ruangan " +
                    "LEFT JOIN petugas g ON g.nip=a.nip where "+
                    "a.tanggal between ? and ? order by a.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "SELECT " +
                    "a.no_rawat, " +
                    "b.no_rkm_medis, " +
                    "c.nm_pasien, " +
                    "c.tgl_lahir, " +
                    "c.jk, " +
                    "a.tanggal, " +
                    "COALESCE(d.kd_kamar, f.kd_poli) AS kode, " +
                    "COALESCE(e.nm_bangsal, f.nm_poli) AS ruang, " +
                    "a.nip, " +
                    "g.nama " +
                    "FROM penilaian_pasien_terminal a " +
                    "INNER JOIN reg_periksa b ON a.no_rawat=b.no_rawat " +
                    "INNER JOIN pasien c ON b.no_rkm_medis=c.no_rkm_medis " +
                    "LEFT JOIN kamar d ON d.kd_kamar=a.kd_ruangan " +
                    "LEFT JOIN bangsal e ON e.kd_bangsal=d.kd_bangsal " +
                    "LEFT JOIN poliklinik f ON f.kd_poli = a.kd_ruangan " +
                    "LEFT JOIN petugas g ON g.nip=a.nip where "+
                    "a.tanggal between ? and ? and (b.no_rawat like ? or c.no_rkm_medis like ? or c.nm_pasien like ? or a.nip like ? or g.nama like ?) "+
                    "order by a.tanggal ");
            }
                
            try {
                if(TCari.getText().toString().trim().equals("")){
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal"),
                        rs.getString("kode"),rs.getString("ruang"),rs.getString("nip"),rs.getString("nama")
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
        Tanggal.setDate(new Date());

        KdRuang.setText("");
        NamaPetugas.setText("");
        NamaRuang.setText("");
        KdRuang.setText("");

        Dyspneu.setSelected(false);
        TakTeratur.setSelected(false);
        AdaSekret.setSelected(false);
        NapasCepat.setSelected(false);
        NapasMulut.setSelected(false);
        Saturasi.setSelected(false);
        NapasLambat.setSelected(false);
        Mukrosa.setSelected(false);
        TAK.setSelected(false);
        Mual.setSelected(false);
        SulitMenelan.setSelected(false);
        Inkontinensia.setSelected(false);
        PenurunanGerak.setSelected(false);
        DistensiAbdomen.setSelected(false);
        TAK1.setSelected(false);
        SulitBerbicara.setSelected(false);
        InkontinensiaUrine.setSelected(false);

        Nyeri.setSelectedIndex(0);
        KetNyeri.setText("");

        Bercak.setSelected(false);
        Gelisah.setSelected(false);
        Lemas.setSelected(false);
        TAK2.setSelected(false);
        KulitDingin.setSelected(false);

        TekananDarah.setSelected(false);
        NadiLambat.setSelected(false);
        AktivitasFisik.setSelected(false);

        FaktorLainnya.setText("");

        PindahPosisi.setSelected(false);
        Mual1.setSelected(false);
        Konstipasi.setSelected(false);
        NyeriAkut.setSelected(false);
        PolaNapas.setSelected(false);
        PerubahanPersepsiSensori.setSelected(false);
        NyeriKronis.setSelected(false);
        BersihanJalan.setSelected(false);
        DefisitPerawatan.setSelected(false);

        Spiritual.setSelectedIndex(0);
        KetSpritual.setText("");

        PerluBimbingan.setSelectedIndex(0);
        PerluPendamping.setSelectedIndex(0);
        InginDihubungi.setSelectedIndex(0);
        PerluDoa.setSelectedIndex(0);

        NamaDihubungi.setText("");
        HubunganDihubungi.setText("");
        AlamatDihubungi.setText("");
        HPDihubungi.setText("");

        TetapDirawat.setSelected(false);
        RumahSiap.setSelectedIndex(0);
        DirawatDirumah.setSelected(false);
        MerawatDirumah.setSelectedIndex(0);
        HomeCare.setSelectedIndex(0);

        Menyangkal.setSelected(false);
        RasaBersalah.setSelected(false);
        Ansietas.setSelected(false);
        SedihMenangis.setSelected(false);
        Takut.setSelected(false);
        DistresSpiritual.setSelected(false);
        Marah.setSelected(false);
        Ketidakberdayaan.setSelected(false);
        Marah2.setSelected(false);
        Letih.setSelected(false);
        Bersalah.setSelected(false);
        PenurunanKonsentrasi.setSelected(false);
        GangguanTidur.setSelected(false);
        PerubahanKebiasaan.setSelected(false);
        KeluargaKurang.setSelected(false);
        KetidakmampuanMemenuhi.setSelected(false);
        KeputusanPerawatan.setSelected(false);
        KopingIndividu.setSelected(false);
        Distres.setSelected(false);

        PerluDampingi.setSelected(false);
        KeluargaDapat.setSelected(false);
        SahabatDapat.setSelected(false);

        KebutuhanLainnya.setText("");

        KebutuhanTidak.setSelected(false);
        KebutuhanAutopsi.setSelected(false);
        KebutuhanDonasi.setSelected(false);

        Marah3.setSelected(false);
        Letih1.setSelected(false);
        Bersalah1.setSelected(false);
        PenurunanKonsentrasi1.setSelected(false);
        KetidakMampuanMemenuhi1.setSelected(false);
        KopingIndividu1.setSelected(false);
        Distres1.setSelected(false);
        PerubahanKebiasaan2.setSelected(false);
        Sedih.setSelected(false);
        Depresi.setSelected(false);
        GangguanTidur1.setSelected(false);

        Dyspneu.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            KdRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NamaRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NIP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NamaPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            
            try {
                ps = koneksi.prepareStatement(
                    "SELECT * FROM penilaian_pasien_terminal WHERE no_rawat=?"
                );

                ps.setString(1, TNoRw.getText().trim());
                rs = ps.executeQuery();

                if(rs.next()){

                    KdRuang.setText(rs.getString("kd_ruangan"));

                    Dyspneu.setSelected(toBoolean(rs.getString("Dyspneu")));
                    TakTeratur.setSelected(toBoolean(rs.getString("TakTerkur")));
                    AdaSekret.setSelected(toBoolean(rs.getString("AdaSekret")));
                    NapasCepat.setSelected(toBoolean(rs.getString("NapasCepat")));
                    NapasMulut.setSelected(toBoolean(rs.getString("NapasMulut")));
                    Saturasi.setSelected(toBoolean(rs.getString("Saturasi")));
                    NapasLambat.setSelected(toBoolean(rs.getString("NapasLambat")));
                    Mukrosa.setSelected(toBoolean(rs.getString("Mukrosa")));
                    TAK.setSelected(toBoolean(rs.getString("TAK")));
                    Mual.setSelected(toBoolean(rs.getString("Mual")));
                    SulitMenelan.setSelected(toBoolean(rs.getString("SulitMenelan")));
                    Inkontinensia.setSelected(toBoolean(rs.getString("Inkontinensia")));
                    PenurunanGerak.setSelected(toBoolean(rs.getString("PenurunanGerak")));
                    DistensiAbdomen.setSelected(toBoolean(rs.getString("DistensiAbdomen")));
                    TAK1.setSelected(toBoolean(rs.getString("TAK1")));
                    SulitBerbicara.setSelected(toBoolean(rs.getString("SulitBerbicara")));
                    InkontinensiaUrine.setSelected(toBoolean(rs.getString("InkontinensiaUrine")));

                    Nyeri.setSelectedItem(rs.getString("Nyeri"));
                    KetNyeri.setText(rs.getString("KetNyeri"));

                    Bercak.setSelected(toBoolean(rs.getString("Bercak")));
                    Gelisah.setSelected(toBoolean(rs.getString("Gelisah")));
                    Lemas.setSelected(toBoolean(rs.getString("Lemas")));
                    TAK2.setSelected(toBoolean(rs.getString("TAK2")));
                    KulitDingin.setSelected(toBoolean(rs.getString("KulitDingin")));

                    TekananDarah.setSelected(toBoolean(rs.getString("TekananDarah")));
                    NadiLambat.setSelected(toBoolean(rs.getString("NadiLambat")));
                    AktivitasFisik.setSelected(toBoolean(rs.getString("AktivitasFisik")));

                    FaktorLainnya.setText(rs.getString("FaktorLainnya"));

                    PindahPosisi.setSelected(toBoolean(rs.getString("PindahPosisi")));
                    Mual1.setSelected(toBoolean(rs.getString("Mual1")));
                    Konstipasi.setSelected(toBoolean(rs.getString("Konstipasi")));
                    NyeriAkut.setSelected(toBoolean(rs.getString("NyeriAkut")));
                    PolaNapas.setSelected(toBoolean(rs.getString("PolaNapas")));
                    PerubahanPersepsiSensori.setSelected(toBoolean(rs.getString("PerubahanPersepsiSensori")));
                    NyeriKronis.setSelected(toBoolean(rs.getString("NyeriKronis")));
                    BersihanJalan.setSelected(toBoolean(rs.getString("BersihanJalan")));
                    DefisitPerawatan.setSelected(toBoolean(rs.getString("DefisitPerawatan")));

                    Spiritual.setSelectedItem(rs.getString("Spiritual"));
                    KetSpritual.setText(rs.getString("KetSpiritual"));

                    PerluBimbingan.setSelectedItem(rs.getString("PerluBimbingan"));
                    PerluPendamping.setSelectedItem(rs.getString("PerluPendamping"));
                    InginDihubungi.setSelectedItem(rs.getString("InginDihubungi"));
                    PerluDoa.setSelectedItem(rs.getString("PerluDoa"));

                    NamaDihubungi.setText(rs.getString("NamaDihubungi"));
                    HubunganDihubungi.setText(rs.getString("HubunganDihubungi"));
                    AlamatDihubungi.setText(rs.getString("AlamatDihubungi"));
                    HPDihubungi.setText(rs.getString("HPDihubungi"));

                    TetapDirawat.setSelected(toBoolean(rs.getString("TetapDirawat")));
                    RumahSiap.setSelectedItem(rs.getString("RumahSiap"));
                    DirawatDirumah.setSelected(toBoolean(rs.getString("DirawatDirumah")));
                    MerawatDirumah.setSelectedItem(rs.getString("MerawatDirumah"));
                    HomeCare.setSelectedItem(rs.getString("HomeCare"));

                    Menyangkal.setSelected(toBoolean(rs.getString("Menyangkal")));
                    RasaBersalah.setSelected(toBoolean(rs.getString("RasaBersalah")));
                    Ansietas.setSelected(toBoolean(rs.getString("Ansietas")));
                    SedihMenangis.setSelected(toBoolean(rs.getString("SedihMenangis")));
                    Takut.setSelected(toBoolean(rs.getString("Takut")));
                    DistresSpiritual.setSelected(toBoolean(rs.getString("DistresSpiritual")));
                    Marah.setSelected(toBoolean(rs.getString("Marah")));
                    Ketidakberdayaan.setSelected(toBoolean(rs.getString("Ketidakberdayaan")));
                    Marah2.setSelected(toBoolean(rs.getString("Marah2")));
                    Letih.setSelected(toBoolean(rs.getString("Letih")));
                    Bersalah.setSelected(toBoolean(rs.getString("Bersalah")));
                    PenurunanKonsentrasi.setSelected(toBoolean(rs.getString("PenurunanKonsentrasi")));
                    GangguanTidur.setSelected(toBoolean(rs.getString("GangguanTidur")));
                    PerubahanKebiasaan.setSelected(toBoolean(rs.getString("PerubahanKebiasaan")));
                    KeluargaKurang.setSelected(toBoolean(rs.getString("KeluargaKurang")));
                    KetidakmampuanMemenuhi.setSelected(toBoolean(rs.getString("KetidakmampuanMemenuhi")));
                    KeputusanPerawatan.setSelected(toBoolean(rs.getString("KeputusanPerawatan")));
                    KopingIndividu.setSelected(toBoolean(rs.getString("KopingIndividu")));
                    Distres.setSelected(toBoolean(rs.getString("Distres")));

                    PerluDampingi.setSelected(toBoolean(rs.getString("PerluDampingi")));
                    KeluargaDapat.setSelected(toBoolean(rs.getString("KeluargaDapat")));
                    SahabatDapat.setSelected(toBoolean(rs.getString("SahabatDapat")));

                    KebutuhanLainnya.setText(rs.getString("KebutuhanLainnya"));

                    KebutuhanTidak.setSelected(toBoolean(rs.getString("KebutuhanTidak")));
                    KebutuhanAutopsi.setSelected(toBoolean(rs.getString("KebutuhanAutopsi")));
                    KebutuhanDonasi.setSelected(toBoolean(rs.getString("KebutuhanDonasi")));

                    Marah3.setSelected(toBoolean(rs.getString("Marah3")));
                    Letih1.setSelected(toBoolean(rs.getString("Letih1")));
                    Bersalah1.setSelected(toBoolean(rs.getString("Bersalah1")));
                    PenurunanKonsentrasi1.setSelected(toBoolean(rs.getString("PenurunanKonsentrasi1")));
                    KetidakMampuanMemenuhi1.setSelected(toBoolean(rs.getString("KetidakMampuanMemenuhi1")));
                    KopingIndividu1.setSelected(toBoolean(rs.getString("KopingIndividu1")));
                    Distres1.setSelected(toBoolean(rs.getString("Distres1")));
                    PerubahanKebiasaan2.setSelected(toBoolean(rs.getString("PerubahanKebiasaan2")));
                    Sedih.setSelected(toBoolean(rs.getString("Sedih")));
                    Depresi.setSelected(toBoolean(rs.getString("Depresi")));
                    GangguanTidur1.setSelected(toBoolean(rs.getString("GangguanTidur1")));

                }else{
                    emptTeks();
                }

            } catch(Exception e){
                System.out.println("Notif : "+e);
            }
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "SELECT  " +
                    "    b.no_rkm_medis, " +
                    "    b.nm_pasien, " +
                    "    b.jk, " +
                    "    b.tgl_lahir, " +
                    "    a.tgl_registrasi, " +
                    "    COALESCE(c.kd_kamar, f.kd_poli) AS kode, " +
                    "    COALESCE(e.nm_bangsal, f.nm_poli) AS ruang " +
                    "FROM reg_periksa a " +
                    "INNER JOIN pasien b ON a.no_rkm_medis = b.no_rkm_medis " +
                    "LEFT JOIN kamar_inap c  ON c.no_rawat = a.no_rawat " +
                    "LEFT JOIN kamar d ON d.kd_kamar = c.kd_kamar " +
                    "LEFT JOIN bangsal e ON e.kd_bangsal = d.kd_bangsal " +
                    "INNER JOIN poliklinik f ON f.kd_poli = a.kd_poli " +
                    "WHERE a.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    KdRuang.setText(rs.getString("kode"));
                    NamaRuang.setText(rs.getString("ruang"));
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
    
//    private void isForm(){
//        if(ChkInput.isSelected()==true){
//            if(internalFrame1.getHeight()>660){
//                ChkInput.setVisible(false);
//                PanelInput.setPreferredSize(new Dimension(WIDTH,626));
//                FormInput.setVisible(true); 
//                FormInput1.setVisible(true); 
//                ChkInput.setVisible(true);
//            }else{
//                ChkInput.setVisible(false);
//                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-172));
//                FormInput.setVisible(true); 
//                FormInput1.setVisible(true); 
//                ChkInput.setVisible(true);
//            }
//        }else if(ChkInput.isSelected()==false){           
//            ChkInput.setVisible(false);            
//            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
//            FormInput.setVisible(false); 
//            FormInput1.setVisible(true);     
//            ChkInput.setVisible(true);
//        }
//    }
    
    private void isForm() {

        int tinggi = Math.min(626, internalFrame1.getHeight() - 172);

        PanelInput.setPreferredSize(new Dimension(WIDTH,
                ChkInput.isSelected() ? tinggi : 20));

        FormInput.setVisible(ChkInput.isSelected());
        FormInput1.setVisible(true);

        updateFormSize();

        PanelInput.revalidate();
        PanelInput.repaint();

        scrollInput.revalidate();
        scrollInput.repaint();
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_pasien_terminal());
        BtnHapus.setEnabled(akses.getpenilaian_pasien_terminal());
        BtnEdit.setEnabled(akses.getpenilaian_pasien_terminal());
        BtnPrint.setEnabled(akses.getpenilaian_pasien_terminal()); 
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
        new Timer(1240, taskPerformer).start();
    }

    private void ganti() {
        if(Sequel.mengedittf(
            "penilaian_pasien_terminal",
            "no_rawat=?",
            "no_rawat=?,tanggal=?,kd_ruangan=?,"
            +"Dyspneu=?,TakTerkur=?,AdaSekret=?,NapasCepat=?,NapasMulut=?,Saturasi=?,NapasLambat=?,Mukrosa=?,"
            +"TAK=?,Mual=?,SulitMenelan=?,Inkontinensia=?,PenurunanGerak=?,DistensiAbdomen=?,TAK1=?,SulitBerbicara=?,InkontinensiaUrine=?,"
            +"Nyeri=?,KetNyeri=?,"
            +"Bercak=?,Gelisah=?,Lemas=?,TAK2=?,KulitDingin=?,TekananDarah=?,NadiLambat=?,AktivitasFisik=?,FaktorLainnya=?,"
            +"PindahPosisi=?,Mual1=?,Konstipasi=?,NyeriAkut=?,PolaNapas=?,PerubahanPersepsiSensori=?,NyeriKronis=?,BersihanJalan=?,DefisitPerawatan=?,"
            +"Spiritual=?,KetSpiritual=?,"
            +"PerluBimbingan=?,PerluPendamping=?,InginDihubungi=?,PerluDoa=?,"
            +"NamaDihubungi=?,HubunganDihubungi=?,AlamatDihubungi=?,HPDihubungi=?,"
            +"TetapDirawat=?,RumahSiap=?,DirawatDirumah=?,MerawatDirumah=?,HomeCare=?,"
            +"Menyangkal=?,RasaBersalah=?,Ansietas=?,SedihMenangis=?,Takut=?,DistresSpiritual=?,Marah=?,Ketidakberdayaan=?,Marah2=?,"
            +"Letih=?,Bersalah=?,PenurunanKonsentrasi=?,GangguanTidur=?,PerubahanKebiasaan=?,KeluargaKurang=?,KetidakmampuanMemenuhi=?,"
            +"KeputusanPerawatan=?,KopingIndividu=?,Distres=?,PerluDampingi=?,KeluargaDapat=?,SahabatDapat=?,KebutuhanLainnya=?,"
            +"KebutuhanTidak=?,KebutuhanAutopsi=?,KebutuhanDonasi=?,Marah3=?,Letih1=?,Bersalah1=?,PenurunanKonsentrasi1=?,"
            +"KetidakMampuanMemenuhi1=?,KopingIndividu1=?,Distres1=?,PerubahanKebiasaan2=?,Sedih=?,Depresi=?,GangguanTidur1=?,nip=?",
            94,
            new String[]{
                TNoRw.getText(),
                Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                KdRuang.getText(),

                Dyspneu.isSelected()?"true":"false",
                TakTeratur.isSelected()?"true":"false",
                AdaSekret.isSelected()?"true":"false",
                NapasCepat.isSelected()?"true":"false",
                NapasMulut.isSelected()?"true":"false",
                Saturasi.isSelected()?"true":"false",
                NapasLambat.isSelected()?"true":"false",
                Mukrosa.isSelected()?"true":"false",
                TAK.isSelected()?"true":"false",
                Mual.isSelected()?"true":"false",
                SulitMenelan.isSelected()?"true":"false",
                Inkontinensia.isSelected()?"true":"false",
                PenurunanGerak.isSelected()?"true":"false",
                DistensiAbdomen.isSelected()?"true":"false",
                TAK1.isSelected()?"true":"false",
                SulitBerbicara.isSelected()?"true":"false",
                InkontinensiaUrine.isSelected()?"true":"false",

                Nyeri.getSelectedItem().toString(),
                KetNyeri.getText(),

                Bercak.isSelected()?"true":"false",
                Gelisah.isSelected()?"true":"false",
                Lemas.isSelected()?"true":"false",
                TAK2.isSelected()?"true":"false",
                KulitDingin.isSelected()?"true":"false",
                TekananDarah.isSelected()?"true":"false",
                NadiLambat.isSelected()?"true":"false",
                AktivitasFisik.isSelected()?"true":"false",

                FaktorLainnya.getText(),

                PindahPosisi.isSelected()?"true":"false",
                Mual1.isSelected()?"true":"false",
                Konstipasi.isSelected()?"true":"false",
                NyeriAkut.isSelected()?"true":"false",
                PolaNapas.isSelected()?"true":"false",
                PerubahanPersepsiSensori.isSelected()?"true":"false",
                NyeriKronis.isSelected()?"true":"false",
                BersihanJalan.isSelected()?"true":"false",
                DefisitPerawatan.isSelected()?"true":"false",

                Spiritual.getSelectedItem().toString(),
                KetSpritual.getText(),

                PerluBimbingan.getSelectedItem().toString(),
                PerluPendamping.getSelectedItem().toString(),
                InginDihubungi.getSelectedItem().toString(),
                PerluDoa.getSelectedItem().toString(),

                NamaDihubungi.getText(),
                HubunganDihubungi.getText(),
                AlamatDihubungi.getText(),
                HPDihubungi.getText(),

                TetapDirawat.isSelected()?"true":"false",
                RumahSiap.getSelectedItem().toString(),
                DirawatDirumah.isSelected()?"true":"false",
                MerawatDirumah.getSelectedItem().toString(),
                HomeCare.getSelectedItem().toString(),

                Menyangkal.isSelected()?"true":"false",
                RasaBersalah.isSelected()?"true":"false",
                Ansietas.isSelected()?"true":"false",
                SedihMenangis.isSelected()?"true":"false",
                Takut.isSelected()?"true":"false",
                DistresSpiritual.isSelected()?"true":"false",
                Marah.isSelected()?"true":"false",
                Ketidakberdayaan.isSelected()?"true":"false",
                Marah2.isSelected()?"true":"false",
                Letih.isSelected()?"true":"false",
                Bersalah.isSelected()?"true":"false",
                PenurunanKonsentrasi.isSelected()?"true":"false",
                GangguanTidur.isSelected()?"true":"false",
                PerubahanKebiasaan.isSelected()?"true":"false",
                KeluargaKurang.isSelected()?"true":"false",
                KetidakmampuanMemenuhi.isSelected()?"true":"false",
                KeputusanPerawatan.isSelected()?"true":"false",
                KopingIndividu.isSelected()?"true":"false",
                Distres.isSelected()?"true":"false",

                PerluDampingi.isSelected()?"true":"false",
                KeluargaDapat.isSelected()?"true":"false",
                SahabatDapat.isSelected()?"true":"false",

                KebutuhanLainnya.getText(),

                KebutuhanTidak.isSelected()?"true":"false",
                KebutuhanAutopsi.isSelected()?"true":"false",
                KebutuhanDonasi.isSelected()?"true":"false",

                Marah3.isSelected()?"true":"false",
                Letih1.isSelected()?"true":"false",
                Bersalah1.isSelected()?"true":"false",
                PenurunanKonsentrasi1.isSelected()?"true":"false",
                KetidakMampuanMemenuhi1.isSelected()?"true":"false",
                KopingIndividu1.isSelected()?"true":"false",
                Distres1.isSelected()?"true":"false",
                PerubahanKebiasaan2.isSelected()?"true":"false",
                Sedih.isSelected()?"true":"false",
                Depresi.isSelected()?"true":"false",
                GangguanTidur1.isSelected()?"true":"false",

                NIP.getText(),

                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            }
        )==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(KdRuang.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(NamaRuang.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),9);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_pasien_terminal where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
private void updateFormSize() {

    int maxY = 0;

    for (Component c : FormInput.getComponents()) {
        Rectangle r = c.getBounds();
        maxY = Math.max(maxY, r.y + r.height);
    }

    FormInput.setPreferredSize(new Dimension(
            FormInput.getPreferredSize().width,
            maxY + 20));
    
    FormInput1.setPreferredSize(new Dimension(
        FormInput1.getPreferredSize().width,
        100
    ));

    FormInput1.revalidate();
    FormInput1.repaint();

    FormInput.revalidate();
    FormInput.repaint();

    scrollInput.revalidate();
    scrollInput.repaint();
}
    
}
