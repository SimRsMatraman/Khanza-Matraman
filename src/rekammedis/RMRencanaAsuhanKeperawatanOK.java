/*
 * Kontribusi dari M. Syukur RS. Jiwa Prov Sultra
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Color;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public final class RMRencanaAsuhanKeperawatanOK extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=0;
    private DlgCariPetugas petugasO=new DlgCariPetugas(null,false);
    private DlgCariPetugas petugasA=new DlgCariPetugas(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMRencanaAsuhanKeperawatanOK(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        // 1. Buat list nama kolom
        List<Object> columns = new ArrayList<>(List.of(
            "No.Rawat", "No.RM", "Nama Pasien", "Tgl.Lahir", "J.K.",
            "Tanggal Masuk", "Kd. Operator", "Dokter Operator",
            "Kd. Anastesi", "Dokter Anastesi", "Kd. Petugas", "Petugas", "Jam Pre"
        ));

        // 2. Tambah kolom "1" sebanyak 66 kolom
        for (int i = 0; i < 66; i++) {
            columns.add("Pre");
        }

        // 3. Tambah kolom "Jam Intra" dan kolom "2" sebanyak 77
        columns.add("Jam Intra");
        for (int i = 0; i < 77; i++) {
            columns.add("Intra");
        }

        // 4. Tambah kolom "Jam Post" dan kolom "3" sebanyak 71
        columns.add("Jam Post");
        for (int i = 0; i < 71; i++) {
            columns.add("Post");
        }

        // 5. Tambah kolom "Jam Glass Gow" dan kolom "4" sebanyak 6
        columns.add("Jam Glass Gow");
        for (int i = 0; i < 6; i++) {
            columns.add("GG");
        }

        // 6. Ubah menjadi array dan buat tabMode
        Object[] columnNames = columns.toArray();
        tabMode = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        // 7. Atur JTable
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // 8. Atur lebar kolom (otomatis atau manual sebagian)
        for (int i = 0; i < tbObat.getColumnCount(); i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i < 12) {
                column.setPreferredWidth(100 + (i * 12)); // kolom awal lebih lebar
            } else {
                column.setPreferredWidth(50); // sisanya rata
            }
        }

        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        
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
        
        petugasO.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugasO.getTable().getSelectedRow()!= -1){
                    if(pilihan==1){
                        KdPetugasOperator.setText(petugasO.getTable().getValueAt(petugasO.getTable().getSelectedRow(),0).toString());
                        NmPetugasOperator.setText(petugasO.getTable().getValueAt(petugasO.getTable().getSelectedRow(),1).toString());
                        KdPetugasOperator.requestFocus();
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
        
        petugasA.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugasA.getTable().getSelectedRow()!= -1){
                    if(pilihan==1){
                        KdPetugasAnastesi.setText(petugasA.getTable().getValueAt(petugasA.getTable().getSelectedRow(),0).toString());
                        NmPetugasAnastesi.setText(petugasA.getTable().getValueAt(petugasA.getTable().getSelectedRow(),1).toString());
                        KdPetugasAnastesi.requestFocus();
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    if(pilihan==1){
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KdPetugas.requestFocus();
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

        LoadHTML = new widget.editorpane();
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
        jLabel11 = new widget.Label();
        TanggalOperasi = new widget.Tanggal();
        TanggalPengkajian = new widget.Tanggal();
        label14 = new widget.Label();
        KdPetugasOperator = new widget.TextBox();
        NmPetugasOperator = new widget.TextBox();
        BtnDokterOperator = new widget.Button();
        label15 = new widget.Label();
        KdPetugasAnastesi = new widget.TextBox();
        NmPetugasAnastesi = new widget.TextBox();
        BtnDokterAnastesi = new widget.Button();
        label16 = new widget.Label();
        jLabel12 = new widget.Label();
        label17 = new widget.Label();
        label18 = new widget.Label();
        label19 = new widget.Label();
        OtotBantuNafas = new javax.swing.JCheckBox();
        Deformitas = new javax.swing.JCheckBox();
        Empisema = new javax.swing.JCheckBox();
        label20 = new widget.Label();
        PengembanganDadaR = new javax.swing.JCheckBox();
        PengembanganDadaL = new javax.swing.JCheckBox();
        label21 = new widget.Label();
        label22 = new widget.Label();
        RR = new widget.TextBox();
        Spo2 = new widget.TextBox();
        label23 = new widget.Label();
        label24 = new widget.Label();
        Normal = new javax.swing.JCheckBox();
        label25 = new widget.Label();
        label26 = new widget.Label();
        Capillary1 = new javax.swing.JCheckBox();
        Capillary2 = new javax.swing.JCheckBox();
        label27 = new widget.Label();
        label28 = new widget.Label();
        Ket_Pendarahan = new widget.TextBox();
        label29 = new widget.Label();
        Pendarahan = new javax.swing.JCheckBox();
        Tensi = new widget.TextBox();
        label30 = new widget.Label();
        label31 = new widget.Label();
        Nd = new widget.TextBox();
        label32 = new widget.Label();
        label33 = new widget.Label();
        Suhu = new widget.TextBox();
        label34 = new widget.Label();
        label35 = new widget.Label();
        KulitDingin = new javax.swing.JCheckBox();
        KulitLainnya = new javax.swing.JCheckBox();
        KulitHangat = new javax.swing.JCheckBox();
        label36 = new widget.Label();
        ProduksiUrine = new widget.TextBox();
        label37 = new widget.Label();
        SkalaNyeri = new widget.TextBox();
        PenggunaanKateter = new javax.swing.JCheckBox();
        label48 = new widget.Label();
        label38 = new widget.Label();
        KemihLainnyaPre = new widget.TextBox();
        label39 = new widget.Label();
        label40 = new widget.Label();
        Deltrium = new javax.swing.JCheckBox();
        Stuper = new javax.swing.JCheckBox();
        Apatis = new javax.swing.JCheckBox();
        Koma = new javax.swing.JCheckBox();
        ComposMentis = new javax.swing.JCheckBox();
        label41 = new widget.Label();
        label42 = new widget.Label();
        Bb = new widget.TextBox();
        label43 = new widget.Label();
        Mual = new javax.swing.JCheckBox();
        PatahTulang = new javax.swing.JCheckBox();
        Nangis = new javax.swing.JCheckBox();
        Gelisah = new javax.swing.JCheckBox();
        Distensil = new javax.swing.JCheckBox();
        Muntah = new javax.swing.JCheckBox();
        Tenang = new javax.swing.JCheckBox();
        Puasa = new javax.swing.JCheckBox();
        label44 = new widget.Label();
        label47 = new widget.Label();
        label45 = new widget.Label();
        Regio = new widget.TextBox();
        MentalLainnya = new widget.TextBox();
        label46 = new widget.Label();
        jSeparator = new javax.swing.JSeparator();
        label49 = new widget.Label();
        label50 = new widget.Label();
        label51 = new widget.Label();
        MobilitasFisik = new javax.swing.JCheckBox();
        IntegritasKulit = new javax.swing.JCheckBox();
        KomunikasiVerbal = new javax.swing.JCheckBox();
        TidakEfektifPolaNafas = new javax.swing.JCheckBox();
        TidakEfektifKupingIndividu = new javax.swing.JCheckBox();
        DefisiPengetahuan = new javax.swing.JCheckBox();
        PotensialInfeksi = new javax.swing.JCheckBox();
        Nyeri = new javax.swing.JCheckBox();
        Kecemasan = new javax.swing.JCheckBox();
        PertukaranGas = new javax.swing.JCheckBox();
        DiagnosaLainnya = new widget.TextBox();
        KelengkapanDokumenPraOperasi = new javax.swing.JCheckBox();
        LakukanOrientasi = new javax.swing.JCheckBox();
        LakukanInteraksiSosial = new javax.swing.JCheckBox();
        label52 = new widget.Label();
        SignIn = new javax.swing.JCheckBox();
        TTV = new javax.swing.JCheckBox();
        scrollPane1 = new widget.ScrollPane();
        PerencanaanLainnya = new widget.TextArea();
        scrollPane2 = new widget.ScrollPane();
        S = new widget.TextArea();
        scrollPane3 = new widget.ScrollPane();
        O = new widget.TextArea();
        label53 = new widget.Label();
        TD = new widget.TextBox();
        N = new widget.TextBox();
        label54 = new widget.Label();
        label55 = new widget.Label();
        PLanjutkan = new widget.TextBox();
        label56 = new widget.Label();
        label57 = new widget.Label();
        R = new widget.TextBox();
        label58 = new widget.Label();
        label59 = new widget.Label();
        ATercapaiSebagian = new javax.swing.JCheckBox();
        ABelumTercapai = new javax.swing.JCheckBox();
        P = new javax.swing.JCheckBox();
        PPertahankan = new javax.swing.JCheckBox();
        ATercapai = new javax.swing.JCheckBox();
        label60 = new widget.Label();
        label61 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        label62 = new widget.Label();
        label63 = new widget.Label();
        TanggalPengkajianIntra = new widget.Tanggal();
        label64 = new widget.Label();
        label65 = new widget.Label();
        label66 = new widget.Label();
        label67 = new widget.Label();
        label68 = new widget.Label();
        label69 = new widget.Label();
        label70 = new widget.Label();
        label71 = new widget.Label();
        label72 = new widget.Label();
        AnastesiUmum = new javax.swing.JCheckBox();
        AnastesiBlok = new javax.swing.JCheckBox();
        AnastesiRegional = new javax.swing.JCheckBox();
        AnastesiMulai = new widget.Tanggal();
        AnastesiKeluar = new widget.Tanggal();
        OperasiMulai = new widget.Tanggal();
        OperasiSelesai = new widget.Tanggal();
        WaktuMasuk = new widget.Tanggal();
        WaktuKeluar = new widget.Tanggal();
        Asa = new widget.TextBox();
        label73 = new widget.Label();
        KulitPreOPUtuh = new javax.swing.JCheckBox();
        label74 = new widget.Label();
        LukaKotor = new javax.swing.JCheckBox();
        label75 = new widget.Label();
        PersiapanKulitOleh = new widget.TextBox();
        label76 = new widget.Label();
        LukaBersih = new javax.swing.JCheckBox();
        LukaTerkontaminasi = new javax.swing.JCheckBox();
        label77 = new widget.Label();
        PosisiLiteral = new javax.swing.JCheckBox();
        PosisiSupin = new javax.swing.JCheckBox();
        PosisiUthotomi = new javax.swing.JCheckBox();
        label78 = new widget.Label();
        PosisiLainnya = new widget.TextBox();
        label79 = new widget.Label();
        label80 = new widget.Label();
        PemasangElek = new widget.TextBox();
        label81 = new widget.Label();
        LetakGround = new widget.TextBox();
        label82 = new widget.Label();
        Coagulant = new widget.TextBox();
        label83 = new widget.Label();
        Cutting = new widget.TextBox();
        MesinSuction = new javax.swing.JCheckBox();
        BlanketWarmer = new javax.swing.JCheckBox();
        label84 = new widget.Label();
        label85 = new widget.Label();
        label86 = new widget.Label();
        label87 = new widget.Label();
        JamMulaiTor = new widget.Tanggal();
        JamSelesaiTor = new widget.Tanggal();
        PemasangTor = new widget.TextBox();
        Graft = new javax.swing.JCheckBox();
        label88 = new widget.Label();
        Lokasi = new widget.TextBox();
        label89 = new widget.Label();
        label90 = new widget.Label();
        PendarahanIntraOP = new widget.TextBox();
        label91 = new widget.Label();
        ProdukDarah = new javax.swing.JCheckBox();
        label92 = new widget.Label();
        label94 = new widget.Label();
        label95 = new widget.Label();
        JamMulaiDarah = new widget.Tanggal();
        JamMulaiDarah1 = new widget.Tanggal();
        label96 = new widget.Label();
        JamSelesaiDarah = new widget.Tanggal();
        JamSelesaiDarah1 = new widget.Tanggal();
        label97 = new widget.Label();
        label98 = new widget.Label();
        Rutin = new javax.swing.JCheckBox();
        PotongBeku = new javax.swing.JCheckBox();
        Kultur = new javax.swing.JCheckBox();
        label99 = new widget.Label();
        PenggunaanKateterIntra = new javax.swing.JCheckBox();
        label100 = new widget.Label();
        ProduksiUrineIntra = new widget.TextBox();
        label101 = new widget.Label();
        label102 = new widget.Label();
        KemihLainnyaIntra = new widget.TextBox();
        label103 = new widget.Label();
        NangisIntra = new javax.swing.JCheckBox();
        TenangIntra = new javax.swing.JCheckBox();
        GelisahIntra = new javax.swing.JCheckBox();
        label104 = new widget.Label();
        MentalLainnyaIntra = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        label93 = new widget.Label();
        label105 = new widget.Label();
        label106 = new widget.Label();
        ResikoHipotermi = new javax.swing.JCheckBox();
        KecemasanIntra = new javax.swing.JCheckBox();
        IntegritasKulitIntra = new javax.swing.JCheckBox();
        TidakEfektifPolaNafasIntra = new javax.swing.JCheckBox();
        ResikoSyok = new javax.swing.JCheckBox();
        ResikoInfeksi = new javax.swing.JCheckBox();
        ResikoCidera = new javax.swing.JCheckBox();
        scrollPane4 = new widget.ScrollPane();
        DiagnosaLainnyaIntra = new widget.TextArea();
        SiapkanPasienDimejaOP = new javax.swing.JCheckBox();
        ObservasiTTVIntra = new javax.swing.JCheckBox();
        AlatLinen = new javax.swing.JCheckBox();
        Elektromedis = new javax.swing.JCheckBox();
        PosisiPasien = new javax.swing.JCheckBox();
        PersiapanOP = new javax.swing.JCheckBox();
        PersiapanKulit = new javax.swing.JCheckBox();
        TimeOut = new javax.swing.JCheckBox();
        SiapkanOK = new javax.swing.JCheckBox();
        SiapkanSelimut = new javax.swing.JCheckBox();
        label107 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        PerencanaanLainnyaIntra = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        SIntra = new widget.TextArea();
        scrollPane7 = new widget.ScrollPane();
        OIntra = new widget.TextArea();
        label108 = new widget.Label();
        TDIntra = new widget.TextBox();
        label109 = new widget.Label();
        label110 = new widget.Label();
        NIntra = new widget.TextBox();
        label111 = new widget.Label();
        label112 = new widget.Label();
        RIntra = new widget.TextBox();
        label113 = new widget.Label();
        ABelumTercapaiIntra = new javax.swing.JCheckBox();
        ATercapaiSebagianIntra = new javax.swing.JCheckBox();
        ATercapaiIntra = new javax.swing.JCheckBox();
        label114 = new widget.Label();
        label115 = new widget.Label();
        PIntra = new javax.swing.JCheckBox();
        PPertahankanIntra = new javax.swing.JCheckBox();
        label116 = new widget.Label();
        PLanjutkanIntra = new widget.TextBox();
        jSeparator3 = new javax.swing.JSeparator();
        label117 = new widget.Label();
        JamPengkajianPost = new widget.Tanggal();
        label118 = new widget.Label();
        label119 = new widget.Label();
        label120 = new widget.Label();
        OtotBantuNafasPost = new javax.swing.JCheckBox();
        DeformitasPost = new javax.swing.JCheckBox();
        EmpisemaPost = new javax.swing.JCheckBox();
        label121 = new widget.Label();
        PengembanganDadaRPost = new javax.swing.JCheckBox();
        PengembanganDadaLPost = new javax.swing.JCheckBox();
        label122 = new widget.Label();
        label123 = new widget.Label();
        RRPost = new widget.TextBox();
        Spo2Post = new widget.TextBox();
        label124 = new widget.Label();
        label125 = new widget.Label();
        NormalPost = new javax.swing.JCheckBox();
        label126 = new widget.Label();
        label127 = new widget.Label();
        Capillary1Post = new javax.swing.JCheckBox();
        Capillary2Post = new javax.swing.JCheckBox();
        label128 = new widget.Label();
        label129 = new widget.Label();
        Ket_PendarahanPost = new widget.TextBox();
        label130 = new widget.Label();
        PendarahanPost = new javax.swing.JCheckBox();
        TensiPost = new widget.TextBox();
        label131 = new widget.Label();
        label132 = new widget.Label();
        NdPost = new widget.TextBox();
        label133 = new widget.Label();
        label134 = new widget.Label();
        SuhuPost = new widget.TextBox();
        label135 = new widget.Label();
        label136 = new widget.Label();
        KulitDinginPost = new javax.swing.JCheckBox();
        KulitLainnyaPost = new javax.swing.JCheckBox();
        KulitHangatPost = new javax.swing.JCheckBox();
        label137 = new widget.Label();
        ProduksiUrinePost = new widget.TextBox();
        label138 = new widget.Label();
        SkalaNyeriPost = new widget.TextBox();
        PenggunaanKateterPost = new javax.swing.JCheckBox();
        label139 = new widget.Label();
        label140 = new widget.Label();
        KemihLainnyaPost = new widget.TextBox();
        label141 = new widget.Label();
        label142 = new widget.Label();
        DeltriumPost = new javax.swing.JCheckBox();
        StuperPost = new javax.swing.JCheckBox();
        ApatisPost = new javax.swing.JCheckBox();
        KomaPost = new javax.swing.JCheckBox();
        ComposMentisPost = new javax.swing.JCheckBox();
        label143 = new widget.Label();
        label144 = new widget.Label();
        BbPost = new widget.TextBox();
        label145 = new widget.Label();
        MualPost = new javax.swing.JCheckBox();
        CideraKulitPost = new javax.swing.JCheckBox();
        YaPost = new javax.swing.JCheckBox();
        TidakPost = new javax.swing.JCheckBox();
        DistensilPost = new javax.swing.JCheckBox();
        MuntahPost = new javax.swing.JCheckBox();
        DrainPost = new javax.swing.JCheckBox();
        PuasaPost = new javax.swing.JCheckBox();
        label146 = new widget.Label();
        label147 = new widget.Label();
        label148 = new widget.Label();
        JenisBalutanPost = new widget.TextBox();
        ProduksiDrainPost = new widget.TextBox();
        label149 = new widget.Label();
        PemasanganAlatPost = new javax.swing.JCheckBox();
        label150 = new widget.Label();
        label151 = new widget.Label();
        label152 = new widget.Label();
        label153 = new widget.Label();
        label154 = new widget.Label();
        LokasiPost = new widget.TextBox();
        label155 = new widget.Label();
        JenisPost = new widget.TextBox();
        label156 = new widget.Label();
        GCSPost = new widget.TextBox();
        jSeparator4 = new javax.swing.JSeparator();
        RestiHypotermiPost = new javax.swing.JCheckBox();
        ResikoCideraPost = new javax.swing.JCheckBox();
        RestiPendarahanPost = new javax.swing.JCheckBox();
        RestiSyokPost = new javax.swing.JCheckBox();
        MobilitasFisikPost = new javax.swing.JCheckBox();
        PotensiInfeksi = new javax.swing.JCheckBox();
        PerluasanInfeksiPost = new javax.swing.JCheckBox();
        IntegritasKulitPost = new javax.swing.JCheckBox();
        RestiNyeriPost = new javax.swing.JCheckBox();
        RestiInefektifPost = new javax.swing.JCheckBox();
        scrollPane8 = new widget.ScrollPane();
        DiagnosaLainnyaPost = new widget.TextArea();
        SelimutHangatPost = new javax.swing.JCheckBox();
        TerapiOksigen = new javax.swing.JCheckBox();
        SerahTerimaPost = new javax.swing.JCheckBox();
        BerikanPasienPadaKeluargaPost = new javax.swing.JCheckBox();
        SignOut = new javax.swing.JCheckBox();
        TTVPost = new javax.swing.JCheckBox();
        PosisiNyamanPost = new javax.swing.JCheckBox();
        label157 = new widget.Label();
        label158 = new widget.Label();
        label159 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        PerencanaanLainnyaPost = new widget.TextArea();
        label160 = new widget.Label();
        label161 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        SPost = new widget.TextArea();
        scrollPane11 = new widget.ScrollPane();
        OPost = new widget.TextArea();
        TDPost = new widget.TextBox();
        label162 = new widget.Label();
        label163 = new widget.Label();
        NPost = new widget.TextBox();
        label164 = new widget.Label();
        label165 = new widget.Label();
        RPost = new widget.TextBox();
        label166 = new widget.Label();
        ABelumTercapaiPost = new javax.swing.JCheckBox();
        ATercapaiSebagianPost = new javax.swing.JCheckBox();
        ATercapaiPost = new javax.swing.JCheckBox();
        label167 = new widget.Label();
        PPost = new javax.swing.JCheckBox();
        PPertahankanPost = new javax.swing.JCheckBox();
        label168 = new widget.Label();
        PLanjutkanPost = new widget.TextBox();
        jSeparator5 = new javax.swing.JSeparator();
        label169 = new widget.Label();
        JamPengkajianGlow = new widget.Tanggal();
        label170 = new widget.Label();
        label171 = new widget.Label();
        label172 = new widget.Label();
        MembukaMata = new widget.ComboBox();
        ResponMotor = new widget.ComboBox();
        Respon = new widget.ComboBox();
        SkalaMembukaMata = new widget.TextBox();
        SkalaResponMotor = new widget.TextBox();
        SkalaRespon = new widget.TextBox();
        label173 = new widget.Label();
        label174 = new widget.Label();
        label175 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        PanelWall = new usu.widget.glass.PanelGlass();
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

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rencana Asuhan Keperawatan Kamar Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(900, 2500));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(90, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(330, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(230, 10, 100, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(600, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(660, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(790, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 80, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(760, 10, 30, 23);

        TanggalOperasi.setForeground(new java.awt.Color(50, 70, 50));
        TanggalOperasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-06-2025 15:32:57" }));
        TanggalOperasi.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalOperasi.setName("TanggalOperasi"); // NOI18N
        TanggalOperasi.setOpaque(false);
        TanggalOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalOperasiKeyPressed(evt);
            }
        });
        FormInput.add(TanggalOperasi);
        TanggalOperasi.setBounds(90, 40, 130, 23);

        TanggalPengkajian.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPengkajian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-06-2025 15:32:57" }));
        TanggalPengkajian.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPengkajian.setName("TanggalPengkajian"); // NOI18N
        TanggalPengkajian.setOpaque(false);
        TanggalPengkajian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPengkajianKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPengkajian);
        TanggalPengkajian.setBounds(150, 100, 130, 20);

        label14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label14.setText("1. Pernafasan");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(20, 130, 130, 23);

        KdPetugasOperator.setEditable(false);
        KdPetugasOperator.setName("KdPetugasOperator"); // NOI18N
        KdPetugasOperator.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugasOperator);
        KdPetugasOperator.setBounds(320, 40, 100, 23);

        NmPetugasOperator.setEditable(false);
        NmPetugasOperator.setName("NmPetugasOperator"); // NOI18N
        NmPetugasOperator.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugasOperator);
        NmPetugasOperator.setBounds(420, 40, 180, 23);

        BtnDokterOperator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterOperator.setMnemonic('2');
        BtnDokterOperator.setToolTipText("Alt+2");
        BtnDokterOperator.setName("BtnDokterOperator"); // NOI18N
        BtnDokterOperator.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterOperator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterOperatorActionPerformed(evt);
            }
        });
        BtnDokterOperator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterOperatorKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokterOperator);
        BtnDokterOperator.setBounds(600, 40, 28, 23);

        label15.setText("Anastesi :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(650, 40, 70, 23);

        KdPetugasAnastesi.setEditable(false);
        KdPetugasAnastesi.setName("KdPetugasAnastesi"); // NOI18N
        KdPetugasAnastesi.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugasAnastesi);
        KdPetugasAnastesi.setBounds(720, 40, 100, 23);

        NmPetugasAnastesi.setEditable(false);
        NmPetugasAnastesi.setName("NmPetugasAnastesi"); // NOI18N
        NmPetugasAnastesi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugasAnastesi);
        NmPetugasAnastesi.setBounds(820, 40, 180, 23);

        BtnDokterAnastesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterAnastesi.setMnemonic('2');
        BtnDokterAnastesi.setToolTipText("Alt+2");
        BtnDokterAnastesi.setName("BtnDokterAnastesi"); // NOI18N
        BtnDokterAnastesi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterAnastesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterAnastesiActionPerformed(evt);
            }
        });
        BtnDokterAnastesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterAnastesiKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokterAnastesi);
        BtnDokterAnastesi.setBounds(1000, 40, 28, 23);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label16.setText("PRE OPERATIF");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(10, 80, 1070, 23);

        jLabel12.setText("Tgl. Operasi :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 40, 80, 20);

        label17.setText("Operator :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(220, 40, 90, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label18.setText("PENGKAJIAN");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(20, 100, 90, 20);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label19.setText("JAM :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(110, 100, 30, 20);

        OtotBantuNafas.setBackground(new java.awt.Color(255, 255, 255));
        OtotBantuNafas.setText("Otot bantu nafas");
        OtotBantuNafas.setName("OtotBantuNafas"); // NOI18N
        OtotBantuNafas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OtotBantuNafasActionPerformed(evt);
            }
        });
        FormInput.add(OtotBantuNafas);
        OtotBantuNafas.setBounds(30, 150, 120, 20);

        Deformitas.setBackground(new java.awt.Color(255, 255, 255));
        Deformitas.setText("Deformitas");
        Deformitas.setName("Deformitas"); // NOI18N
        Deformitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeformitasActionPerformed(evt);
            }
        });
        FormInput.add(Deformitas);
        Deformitas.setBounds(30, 170, 120, 20);

        Empisema.setBackground(new java.awt.Color(255, 255, 255));
        Empisema.setText("Empisema");
        Empisema.setName("Empisema"); // NOI18N
        Empisema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmpisemaActionPerformed(evt);
            }
        });
        FormInput.add(Empisema);
        Empisema.setBounds(30, 190, 120, 20);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label20.setText("Pengembangan Dada");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(30, 210, 120, 20);

        PengembanganDadaR.setBackground(null);
        PengembanganDadaR.setText("R");
        PengembanganDadaR.setName("PengembanganDadaR"); // NOI18N
        PengembanganDadaR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PengembanganDadaRActionPerformed(evt);
            }
        });
        FormInput.add(PengembanganDadaR);
        PengembanganDadaR.setBounds(30, 230, 40, 20);

        PengembanganDadaL.setBackground(null);
        PengembanganDadaL.setText("L");
        PengembanganDadaL.setName("PengembanganDadaL"); // NOI18N
        PengembanganDadaL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PengembanganDadaLActionPerformed(evt);
            }
        });
        FormInput.add(PengembanganDadaL);
        PengembanganDadaL.setBounds(80, 230, 50, 20);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("SpO2 :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(30, 280, 40, 20);

        label22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label22.setText("%");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(200, 280, 40, 20);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(70, 250, 120, 23);

        Spo2.setFocusTraversalPolicyProvider(true);
        Spo2.setName("Spo2"); // NOI18N
        Spo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Spo2KeyPressed(evt);
            }
        });
        FormInput.add(Spo2);
        Spo2.setBounds(70, 280, 120, 23);

        label23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label23.setText("RR :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(40, 250, 40, 20);

        label24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label24.setText("x/m");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(200, 250, 40, 20);

        Normal.setBackground(new java.awt.Color(255, 255, 255));
        Normal.setText("Normal");
        Normal.setName("Normal"); // NOI18N
        Normal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NormalActionPerformed(evt);
            }
        });
        FormInput.add(Normal);
        Normal.setBounds(160, 150, 70, 20);

        label25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label25.setText("2. Darah");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(240, 130, 70, 23);

        label26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label26.setText("Capillary Refill");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(250, 150, 80, 20);

        Capillary1.setBackground(new java.awt.Color(255, 255, 255));
        Capillary1.setText("< 2 Detik");
        Capillary1.setName("Capillary1"); // NOI18N
        Capillary1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Capillary1ActionPerformed(evt);
            }
        });
        FormInput.add(Capillary1);
        Capillary1.setBounds(250, 170, 90, 20);

        Capillary2.setBackground(new java.awt.Color(255, 255, 255));
        Capillary2.setText("> 2 Detik");
        Capillary2.setName("Capillary2"); // NOI18N
        Capillary2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Capillary2ActionPerformed(evt);
            }
        });
        FormInput.add(Capillary2);
        Capillary2.setBounds(250, 190, 90, 20);

        label27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label27.setText("ST/CT :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(250, 230, 60, 20);

        label28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label28.setText("Tensi :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(250, 280, 80, 20);

        Ket_Pendarahan.setFocusTraversalPolicyProvider(true);
        Ket_Pendarahan.setName("Ket_Pendarahan"); // NOI18N
        Ket_Pendarahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ket_PendarahanActionPerformed(evt);
            }
        });
        Ket_Pendarahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Ket_PendarahanKeyPressed(evt);
            }
        });
        FormInput.add(Ket_Pendarahan);
        Ket_Pendarahan.setBounds(360, 250, 100, 23);

        label29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label29.setText(" cc");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(460, 250, 30, 20);

        Pendarahan.setBackground(new java.awt.Color(255, 255, 255));
        Pendarahan.setText("Perdarahan :");
        Pendarahan.setName("Pendarahan"); // NOI18N
        Pendarahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PendarahanActionPerformed(evt);
            }
        });
        FormInput.add(Pendarahan);
        Pendarahan.setBounds(250, 250, 110, 20);

        Tensi.setFocusTraversalPolicyProvider(true);
        Tensi.setName("Tensi"); // NOI18N
        Tensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TensiActionPerformed(evt);
            }
        });
        Tensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TensiKeyPressed(evt);
            }
        });
        FormInput.add(Tensi);
        Tensi.setBounds(340, 280, 100, 23);

        label30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label30.setText(" mmhg");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(440, 280, 50, 20);

        label31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label31.setText("Nd :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(260, 310, 80, 23);

        Nd.setFocusTraversalPolicyProvider(true);
        Nd.setName("Nd"); // NOI18N
        Nd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NdActionPerformed(evt);
            }
        });
        Nd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NdKeyPressed(evt);
            }
        });
        FormInput.add(Nd);
        Nd.setBounds(340, 310, 100, 23);

        label32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label32.setText(" K/mmt");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(440, 310, 50, 23);

        label33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label33.setText("Suhu :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label33);
        label33.setBounds(260, 340, 80, 30);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuhuActionPerformed(evt);
            }
        });
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(340, 340, 100, 23);

        label34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label34.setText(" C");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(440, 340, 50, 20);

        label35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label35.setText("Kulit");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label35);
        label35.setBounds(340, 150, 70, 20);

        KulitDingin.setBackground(new java.awt.Color(255, 255, 255));
        KulitDingin.setText("Dingin");
        KulitDingin.setName("KulitDingin"); // NOI18N
        KulitDingin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KulitDinginActionPerformed(evt);
            }
        });
        FormInput.add(KulitDingin);
        KulitDingin.setBounds(340, 190, 80, 20);

        KulitLainnya.setBackground(new java.awt.Color(255, 255, 255));
        KulitLainnya.setText("Lainnya");
        KulitLainnya.setName("KulitLainnya"); // NOI18N
        KulitLainnya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KulitLainnyaActionPerformed(evt);
            }
        });
        FormInput.add(KulitLainnya);
        KulitLainnya.setBounds(340, 210, 80, 20);

        KulitHangat.setBackground(new java.awt.Color(255, 255, 255));
        KulitHangat.setText("Hangat");
        KulitHangat.setName("KulitHangat"); // NOI18N
        KulitHangat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KulitHangatActionPerformed(evt);
            }
        });
        FormInput.add(KulitHangat);
        KulitHangat.setBounds(340, 170, 80, 20);

        label36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label36.setText("3. Skala Nyeri :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label36);
        label36.setBounds(490, 140, 80, 23);

        ProduksiUrine.setFocusTraversalPolicyProvider(true);
        ProduksiUrine.setName("ProduksiUrine"); // NOI18N
        ProduksiUrine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProduksiUrineActionPerformed(evt);
            }
        });
        ProduksiUrine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProduksiUrineKeyPressed(evt);
            }
        });
        FormInput.add(ProduksiUrine);
        ProduksiUrine.setBounds(510, 230, 180, 23);

        label37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label37.setText("4. Kandung Kemih");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label37);
        label37.setBounds(490, 170, 110, 20);

        SkalaNyeri.setFocusTraversalPolicyProvider(true);
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkalaNyeriActionPerformed(evt);
            }
        });
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(570, 140, 120, 23);

        PenggunaanKateter.setBackground(new java.awt.Color(255, 255, 255));
        PenggunaanKateter.setText("Penggunaan Kateter");
        PenggunaanKateter.setName("PenggunaanKateter"); // NOI18N
        PenggunaanKateter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PenggunaanKateterActionPerformed(evt);
            }
        });
        FormInput.add(PenggunaanKateter);
        PenggunaanKateter.setBounds(500, 190, 150, 20);

        label48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label48.setText("Produksi Urine :");
        label48.setName("label48"); // NOI18N
        label48.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label48);
        label48.setBounds(510, 210, 90, 20);

        label38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label38.setText("Lain - lain :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label38);
        label38.setBounds(500, 260, 60, 20);

        KemihLainnyaPre.setFocusTraversalPolicyProvider(true);
        KemihLainnyaPre.setName("KemihLainnyaPre"); // NOI18N
        KemihLainnyaPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KemihLainnyaPreActionPerformed(evt);
            }
        });
        KemihLainnyaPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KemihLainnyaPreKeyPressed(evt);
            }
        });
        FormInput.add(KemihLainnyaPre);
        KemihLainnyaPre.setBounds(560, 260, 130, 23);

        label39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label39.setText("5. Otak");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label39);
        label39.setBounds(720, 130, 110, 23);

        label40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label40.setText("Kesadaran");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label40);
        label40.setBounds(730, 150, 100, 20);

        Deltrium.setBackground(new java.awt.Color(255, 255, 255));
        Deltrium.setText("Deltrium");
        Deltrium.setName("Deltrium"); // NOI18N
        Deltrium.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeltriumActionPerformed(evt);
            }
        });
        FormInput.add(Deltrium);
        Deltrium.setBounds(730, 190, 120, 20);

        Stuper.setBackground(new java.awt.Color(255, 255, 255));
        Stuper.setText("Stuper");
        Stuper.setName("Stuper"); // NOI18N
        Stuper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StuperActionPerformed(evt);
            }
        });
        FormInput.add(Stuper);
        Stuper.setBounds(730, 210, 120, 20);

        Apatis.setBackground(new java.awt.Color(255, 255, 255));
        Apatis.setText("Apatis");
        Apatis.setName("Apatis"); // NOI18N
        Apatis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApatisActionPerformed(evt);
            }
        });
        FormInput.add(Apatis);
        Apatis.setBounds(730, 230, 120, 20);

        Koma.setBackground(new java.awt.Color(255, 255, 255));
        Koma.setText("Koma");
        Koma.setName("Koma"); // NOI18N
        Koma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KomaActionPerformed(evt);
            }
        });
        FormInput.add(Koma);
        Koma.setBounds(730, 250, 120, 20);

        ComposMentis.setBackground(new java.awt.Color(255, 255, 255));
        ComposMentis.setText("Compos mentis");
        ComposMentis.setName("ComposMentis"); // NOI18N
        ComposMentis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComposMentisActionPerformed(evt);
            }
        });
        FormInput.add(ComposMentis);
        ComposMentis.setBounds(730, 170, 120, 20);

        label41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label41.setText("6. Abdomen");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label41);
        label41.setBounds(870, 130, 90, 23);

        label42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label42.setText("BB :");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label42);
        label42.setBounds(880, 150, 30, 23);

        Bb.setFocusTraversalPolicyProvider(true);
        Bb.setName("Bb"); // NOI18N
        Bb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BbActionPerformed(evt);
            }
        });
        Bb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BbKeyPressed(evt);
            }
        });
        FormInput.add(Bb);
        Bb.setBounds(910, 150, 70, 23);

        label43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label43.setText(" kg");
        label43.setName("label43"); // NOI18N
        label43.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label43);
        label43.setBounds(980, 150, 40, 23);

        Mual.setBackground(new java.awt.Color(255, 255, 255));
        Mual.setText("Mual");
        Mual.setName("Mual"); // NOI18N
        Mual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MualActionPerformed(evt);
            }
        });
        FormInput.add(Mual);
        Mual.setBounds(880, 200, 70, 20);

        PatahTulang.setBackground(new java.awt.Color(255, 255, 255));
        PatahTulang.setText("Patah Tulang");
        PatahTulang.setName("PatahTulang"); // NOI18N
        PatahTulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PatahTulangActionPerformed(evt);
            }
        });
        FormInput.add(PatahTulang);
        PatahTulang.setBounds(880, 240, 100, 20);

        Nangis.setBackground(new java.awt.Color(255, 255, 255));
        Nangis.setText("Nangis");
        Nangis.setName("Nangis"); // NOI18N
        Nangis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NangisActionPerformed(evt);
            }
        });
        FormInput.add(Nangis);
        Nangis.setBounds(880, 320, 100, 20);

        Gelisah.setBackground(new java.awt.Color(255, 255, 255));
        Gelisah.setText("Gelisah");
        Gelisah.setName("Gelisah"); // NOI18N
        Gelisah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GelisahActionPerformed(evt);
            }
        });
        FormInput.add(Gelisah);
        Gelisah.setBounds(880, 340, 100, 20);

        Distensil.setBackground(new java.awt.Color(255, 255, 255));
        Distensil.setText("Distensil");
        Distensil.setName("Distensil"); // NOI18N
        Distensil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DistensilActionPerformed(evt);
            }
        });
        FormInput.add(Distensil);
        Distensil.setBounds(950, 180, 100, 20);

        Muntah.setBackground(new java.awt.Color(255, 255, 255));
        Muntah.setText("Muntah");
        Muntah.setName("Muntah"); // NOI18N
        Muntah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MuntahActionPerformed(evt);
            }
        });
        FormInput.add(Muntah);
        Muntah.setBounds(950, 200, 80, 20);

        Tenang.setBackground(new java.awt.Color(255, 255, 255));
        Tenang.setText("Tenang");
        Tenang.setName("Tenang"); // NOI18N
        Tenang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TenangActionPerformed(evt);
            }
        });
        FormInput.add(Tenang);
        Tenang.setBounds(880, 300, 100, 20);

        Puasa.setBackground(new java.awt.Color(255, 255, 255));
        Puasa.setText("Puasa");
        Puasa.setName("Puasa"); // NOI18N
        Puasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PuasaActionPerformed(evt);
            }
        });
        FormInput.add(Puasa);
        Puasa.setBounds(880, 180, 70, 20);

        label44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label44.setText("7. Tulang");
        label44.setName("label44"); // NOI18N
        label44.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label44);
        label44.setBounds(870, 220, 90, 20);

        label47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label47.setText("8. Status Mental");
        label47.setName("label47"); // NOI18N
        label47.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label47);
        label47.setBounds(870, 280, 90, 20);

        label45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label45.setText("Regio :");
        label45.setName("label45"); // NOI18N
        label45.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label45);
        label45.setBounds(880, 260, 50, 20);

        Regio.setFocusTraversalPolicyProvider(true);
        Regio.setName("Regio"); // NOI18N
        Regio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegioActionPerformed(evt);
            }
        });
        Regio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegioKeyPressed(evt);
            }
        });
        FormInput.add(Regio);
        Regio.setBounds(920, 260, 180, 23);

        MentalLainnya.setFocusTraversalPolicyProvider(true);
        MentalLainnya.setName("MentalLainnya"); // NOI18N
        MentalLainnya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MentalLainnyaActionPerformed(evt);
            }
        });
        MentalLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MentalLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(MentalLainnya);
        MentalLainnya.setBounds(940, 360, 160, 23);

        label46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label46.setText("Lain - lain :");
        label46.setName("label46"); // NOI18N
        label46.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label46);
        label46.setBounds(880, 360, 60, 20);

        jSeparator.setBackground(null);
        jSeparator.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator.setName("jSeparator"); // NOI18N
        FormInput.add(jSeparator);
        jSeparator.setBounds(10, 130, 1100, 260);

        label49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label49.setText("Evaluasi");
        label49.setName("label49"); // NOI18N
        label49.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label49);
        label49.setBounds(610, 390, 500, 20);

        label50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label50.setText("Diagnosa Keperawatan");
        label50.setName("label50"); // NOI18N
        label50.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label50);
        label50.setBounds(10, 390, 260, 20);

        label51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label51.setText("Perencanaan/Implementasi");
        label51.setName("label51"); // NOI18N
        label51.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label51);
        label51.setBounds(280, 390, 310, 20);

        MobilitasFisik.setBackground(new java.awt.Color(255, 255, 255));
        MobilitasFisik.setText("Gangguan mobbilitas fisik");
        MobilitasFisik.setName("MobilitasFisik"); // NOI18N
        MobilitasFisik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MobilitasFisikActionPerformed(evt);
            }
        });
        FormInput.add(MobilitasFisik);
        MobilitasFisik.setBounds(30, 460, 230, 20);

        IntegritasKulit.setBackground(new java.awt.Color(255, 255, 255));
        IntegritasKulit.setText("Gangguan integritas kulit");
        IntegritasKulit.setName("IntegritasKulit"); // NOI18N
        IntegritasKulit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntegritasKulitActionPerformed(evt);
            }
        });
        FormInput.add(IntegritasKulit);
        IntegritasKulit.setBounds(30, 480, 220, 20);

        KomunikasiVerbal.setBackground(new java.awt.Color(255, 255, 255));
        KomunikasiVerbal.setText("Gangguan komunikasi verbal");
        KomunikasiVerbal.setName("KomunikasiVerbal"); // NOI18N
        KomunikasiVerbal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KomunikasiVerbalActionPerformed(evt);
            }
        });
        FormInput.add(KomunikasiVerbal);
        KomunikasiVerbal.setBounds(30, 500, 240, 20);

        TidakEfektifPolaNafas.setBackground(new java.awt.Color(255, 255, 255));
        TidakEfektifPolaNafas.setText("Tidak efektif pola nafas");
        TidakEfektifPolaNafas.setName("TidakEfektifPolaNafas"); // NOI18N
        TidakEfektifPolaNafas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TidakEfektifPolaNafasActionPerformed(evt);
            }
        });
        FormInput.add(TidakEfektifPolaNafas);
        TidakEfektifPolaNafas.setBounds(30, 520, 210, 20);

        TidakEfektifKupingIndividu.setBackground(new java.awt.Color(255, 255, 255));
        TidakEfektifKupingIndividu.setText("Tidak efektif kuping individu");
        TidakEfektifKupingIndividu.setName("TidakEfektifKupingIndividu"); // NOI18N
        TidakEfektifKupingIndividu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TidakEfektifKupingIndividuActionPerformed(evt);
            }
        });
        FormInput.add(TidakEfektifKupingIndividu);
        TidakEfektifKupingIndividu.setBounds(30, 540, 240, 20);

        DefisiPengetahuan.setBackground(new java.awt.Color(255, 255, 255));
        DefisiPengetahuan.setText("Defisi pengetahuan");
        DefisiPengetahuan.setName("DefisiPengetahuan"); // NOI18N
        DefisiPengetahuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefisiPengetahuanActionPerformed(evt);
            }
        });
        FormInput.add(DefisiPengetahuan);
        DefisiPengetahuan.setBounds(30, 560, 200, 20);

        PotensialInfeksi.setBackground(new java.awt.Color(255, 255, 255));
        PotensialInfeksi.setText("Potensial infeksi");
        PotensialInfeksi.setName("PotensialInfeksi"); // NOI18N
        PotensialInfeksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PotensialInfeksiActionPerformed(evt);
            }
        });
        FormInput.add(PotensialInfeksi);
        PotensialInfeksi.setBounds(30, 580, 190, 20);

        Nyeri.setBackground(new java.awt.Color(255, 255, 255));
        Nyeri.setText("Nyeri");
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NyeriActionPerformed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(30, 600, 80, 20);

        Kecemasan.setBackground(new java.awt.Color(255, 255, 255));
        Kecemasan.setText("Kecemasan");
        Kecemasan.setName("Kecemasan"); // NOI18N
        Kecemasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KecemasanActionPerformed(evt);
            }
        });
        FormInput.add(Kecemasan);
        Kecemasan.setBounds(30, 420, 220, 20);

        PertukaranGas.setBackground(new java.awt.Color(255, 255, 255));
        PertukaranGas.setText("Gangguan pertukan gas");
        PertukaranGas.setName("PertukaranGas"); // NOI18N
        PertukaranGas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PertukaranGasActionPerformed(evt);
            }
        });
        FormInput.add(PertukaranGas);
        PertukaranGas.setBounds(30, 440, 220, 20);

        DiagnosaLainnya.setFocusTraversalPolicyProvider(true);
        DiagnosaLainnya.setName("DiagnosaLainnya"); // NOI18N
        DiagnosaLainnya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiagnosaLainnyaActionPerformed(evt);
            }
        });
        DiagnosaLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaLainnya);
        DiagnosaLainnya.setBounds(30, 620, 150, 23);

        KelengkapanDokumenPraOperasi.setBackground(new java.awt.Color(255, 255, 255));
        KelengkapanDokumenPraOperasi.setText("Cek kelengkapan dokumen pra operasi");
        KelengkapanDokumenPraOperasi.setName("KelengkapanDokumenPraOperasi"); // NOI18N
        KelengkapanDokumenPraOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KelengkapanDokumenPraOperasiActionPerformed(evt);
            }
        });
        FormInput.add(KelengkapanDokumenPraOperasi);
        KelengkapanDokumenPraOperasi.setBounds(280, 440, 270, 20);

        LakukanOrientasi.setBackground(new java.awt.Color(255, 255, 255));
        LakukanOrientasi.setText("Lakukan orientasi");
        LakukanOrientasi.setName("LakukanOrientasi"); // NOI18N
        LakukanOrientasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LakukanOrientasiActionPerformed(evt);
            }
        });
        FormInput.add(LakukanOrientasi);
        LakukanOrientasi.setBounds(280, 460, 270, 20);

        LakukanInteraksiSosial.setBackground(new java.awt.Color(255, 255, 255));
        LakukanInteraksiSosial.setText("Lakukan interaksi sosial");
        LakukanInteraksiSosial.setName("LakukanInteraksiSosial"); // NOI18N
        LakukanInteraksiSosial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LakukanInteraksiSosialActionPerformed(evt);
            }
        });
        FormInput.add(LakukanInteraksiSosial);
        LakukanInteraksiSosial.setBounds(280, 420, 270, 20);

        label52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label52.setText("Pre orientasi dan penken");
        label52.setName("label52"); // NOI18N
        label52.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label52);
        label52.setBounds(280, 480, 280, 20);

        SignIn.setBackground(new java.awt.Color(255, 255, 255));
        SignIn.setText("Lakukan sign in");
        SignIn.setName("SignIn"); // NOI18N
        SignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignInActionPerformed(evt);
            }
        });
        FormInput.add(SignIn);
        SignIn.setBounds(280, 520, 270, 20);

        TTV.setBackground(new java.awt.Color(255, 255, 255));
        TTV.setText("Observasi TTV dari keadaan umum pasien");
        TTV.setName("TTV"); // NOI18N
        TTV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TTVActionPerformed(evt);
            }
        });
        FormInput.add(TTV);
        TTV.setBounds(280, 500, 270, 20);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        PerencanaanLainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PerencanaanLainnya.setColumns(20);
        PerencanaanLainnya.setRows(5);
        PerencanaanLainnya.setName("PerencanaanLainnya"); // NOI18N
        PerencanaanLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerencanaanLainnyaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(PerencanaanLainnya);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(280, 540, 240, 80);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        S.setBorder(javax.swing.BorderFactory.createTitledBorder("S"));
        S.setColumns(20);
        S.setRows(5);
        S.setName("S"); // NOI18N
        S.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(S);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(610, 420, 490, 40);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        O.setBorder(javax.swing.BorderFactory.createTitledBorder("O"));
        O.setColumns(20);
        O.setRows(5);
        O.setName("O"); // NOI18N
        O.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(O);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(610, 470, 490, 70);

        label53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label53.setText("Lanjutkan");
        label53.setName("label53"); // NOI18N
        label53.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label53);
        label53.setBounds(790, 610, 60, 20);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TDActionPerformed(evt);
            }
        });
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(640, 550, 80, 23);

        N.setFocusTraversalPolicyProvider(true);
        N.setName("N"); // NOI18N
        N.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NActionPerformed(evt);
            }
        });
        N.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NKeyPressed(evt);
            }
        });
        FormInput.add(N);
        N.setBounds(790, 550, 80, 23);

        label54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label54.setText(" mmHg");
        label54.setName("label54"); // NOI18N
        label54.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label54);
        label54.setBounds(720, 550, 40, 20);

        label55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label55.setText(" x/mnt");
        label55.setName("label55"); // NOI18N
        label55.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label55);
        label55.setBounds(870, 550, 40, 20);

        PLanjutkan.setFocusTraversalPolicyProvider(true);
        PLanjutkan.setName("PLanjutkan"); // NOI18N
        PLanjutkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLanjutkanActionPerformed(evt);
            }
        });
        PLanjutkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PLanjutkanKeyPressed(evt);
            }
        });
        FormInput.add(PLanjutkan);
        PLanjutkan.setBounds(840, 610, 260, 23);

        label56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label56.setText("N :");
        label56.setName("label56"); // NOI18N
        label56.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label56);
        label56.setBounds(770, 550, 20, 20);

        label57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label57.setText("R :");
        label57.setName("label57"); // NOI18N
        label57.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label57);
        label57.setBounds(920, 550, 20, 20);

        R.setFocusTraversalPolicyProvider(true);
        R.setName("R"); // NOI18N
        R.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RActionPerformed(evt);
            }
        });
        R.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RKeyPressed(evt);
            }
        });
        FormInput.add(R);
        R.setBounds(940, 550, 80, 23);

        label58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label58.setText(" x/mnt");
        label58.setName("label58"); // NOI18N
        label58.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label58);
        label58.setBounds(1020, 550, 40, 20);

        label59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label59.setText("TD :");
        label59.setName("label59"); // NOI18N
        label59.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label59);
        label59.setBounds(610, 550, 30, 20);

        ATercapaiSebagian.setBackground(new java.awt.Color(255, 255, 255));
        ATercapaiSebagian.setText("Tercapai sebagian");
        ATercapaiSebagian.setName("ATercapaiSebagian"); // NOI18N
        ATercapaiSebagian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATercapaiSebagianActionPerformed(evt);
            }
        });
        FormInput.add(ATercapaiSebagian);
        ATercapaiSebagian.setBounds(730, 580, 140, 20);

        ABelumTercapai.setBackground(new java.awt.Color(255, 255, 255));
        ABelumTercapai.setText("Belum tercapai");
        ABelumTercapai.setName("ABelumTercapai"); // NOI18N
        ABelumTercapai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ABelumTercapaiActionPerformed(evt);
            }
        });
        FormInput.add(ABelumTercapai);
        ABelumTercapai.setBounds(890, 580, 150, 20);

        P.setBackground(new java.awt.Color(255, 255, 255));
        P.setText("-");
        P.setName("P"); // NOI18N
        P.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PActionPerformed(evt);
            }
        });
        FormInput.add(P);
        P.setBounds(630, 610, 50, 20);

        PPertahankan.setBackground(new java.awt.Color(255, 255, 255));
        PPertahankan.setText("Pertahankan");
        PPertahankan.setName("PPertahankan"); // NOI18N
        PPertahankan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPertahankanActionPerformed(evt);
            }
        });
        FormInput.add(PPertahankan);
        PPertahankan.setBounds(680, 610, 110, 20);

        ATercapai.setBackground(new java.awt.Color(255, 255, 255));
        ATercapai.setText("Tercapai");
        ATercapai.setName("ATercapai"); // NOI18N
        ATercapai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATercapaiActionPerformed(evt);
            }
        });
        FormInput.add(ATercapai);
        ATercapai.setBounds(630, 580, 90, 20);

        label60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label60.setText("A :");
        label60.setName("label60"); // NOI18N
        label60.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label60);
        label60.setBounds(610, 580, 20, 20);

        label61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label61.setText("P :");
        label61.setName("label61"); // NOI18N
        label61.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label61);
        label61.setBounds(610, 610, 20, 20);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(10, 410, 1100, 240);

        label62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label62.setText("INTRA OPERATIF");
        label62.setName("label62"); // NOI18N
        label62.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label62);
        label62.setBounds(10, 650, 1100, 23);

        label63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label63.setText("PENGKAJIAN");
        label63.setName("label63"); // NOI18N
        label63.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label63);
        label63.setBounds(20, 670, 90, 20);

        TanggalPengkajianIntra.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPengkajianIntra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 14:50:31" }));
        TanggalPengkajianIntra.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPengkajianIntra.setName("TanggalPengkajianIntra"); // NOI18N
        TanggalPengkajianIntra.setOpaque(false);
        TanggalPengkajianIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalPengkajianIntraActionPerformed(evt);
            }
        });
        TanggalPengkajianIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPengkajianIntraKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPengkajianIntra);
        TanggalPengkajianIntra.setBounds(150, 670, 130, 20);

        label64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label64.setText("JAM :");
        label64.setName("label64"); // NOI18N
        label64.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label64);
        label64.setBounds(110, 670, 30, 20);

        label65.setText("Anastesi mulai jam :");
        label65.setName("label65"); // NOI18N
        label65.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label65);
        label65.setBounds(0, 750, 120, 20);

        label66.setText("Anastesi keluar jam :");
        label66.setName("label66"); // NOI18N
        label66.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label66);
        label66.setBounds(0, 770, 120, 20);

        label67.setText("Operasi mulai kam :");
        label67.setName("label67"); // NOI18N
        label67.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label67);
        label67.setBounds(0, 790, 120, 20);

        label68.setText("Operasi keluar jam :");
        label68.setName("label68"); // NOI18N
        label68.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label68);
        label68.setBounds(0, 810, 120, 20);

        label69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label69.setText("Jenis Anastesi");
        label69.setName("label69"); // NOI18N
        label69.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label69);
        label69.setBounds(20, 830, 190, 20);

        label70.setText("ASA :");
        label70.setName("label70"); // NOI18N
        label70.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label70);
        label70.setBounds(10, 870, 40, 20);

        label71.setText("Waktu masuk OK :");
        label71.setName("label71"); // NOI18N
        label71.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label71);
        label71.setBounds(0, 710, 120, 20);

        label72.setText("Waktu keluar OK :");
        label72.setName("label72"); // NOI18N
        label72.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label72);
        label72.setBounds(0, 730, 120, 20);

        AnastesiUmum.setBackground(new java.awt.Color(255, 255, 255));
        AnastesiUmum.setText("Umum");
        AnastesiUmum.setName("AnastesiUmum"); // NOI18N
        AnastesiUmum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnastesiUmumActionPerformed(evt);
            }
        });
        FormInput.add(AnastesiUmum);
        AnastesiUmum.setBounds(30, 850, 70, 20);

        AnastesiBlok.setBackground(new java.awt.Color(255, 255, 255));
        AnastesiBlok.setText("Blok");
        AnastesiBlok.setName("AnastesiBlok"); // NOI18N
        AnastesiBlok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnastesiBlokActionPerformed(evt);
            }
        });
        FormInput.add(AnastesiBlok);
        AnastesiBlok.setBounds(100, 850, 50, 20);

        AnastesiRegional.setBackground(new java.awt.Color(255, 255, 255));
        AnastesiRegional.setText("Regional");
        AnastesiRegional.setName("AnastesiRegional"); // NOI18N
        AnastesiRegional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnastesiRegionalActionPerformed(evt);
            }
        });
        FormInput.add(AnastesiRegional);
        AnastesiRegional.setBounds(150, 850, 110, 20);

        AnastesiMulai.setForeground(new java.awt.Color(50, 70, 50));
        AnastesiMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:02:12" }));
        AnastesiMulai.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        AnastesiMulai.setName("AnastesiMulai"); // NOI18N
        AnastesiMulai.setOpaque(false);
        AnastesiMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnastesiMulaiKeyPressed(evt);
            }
        });
        FormInput.add(AnastesiMulai);
        AnastesiMulai.setBounds(120, 750, 130, 20);

        AnastesiKeluar.setForeground(new java.awt.Color(50, 70, 50));
        AnastesiKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:03:19" }));
        AnastesiKeluar.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        AnastesiKeluar.setName("AnastesiKeluar"); // NOI18N
        AnastesiKeluar.setOpaque(false);
        AnastesiKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnastesiKeluarKeyPressed(evt);
            }
        });
        FormInput.add(AnastesiKeluar);
        AnastesiKeluar.setBounds(120, 770, 130, 20);

        OperasiMulai.setForeground(new java.awt.Color(50, 70, 50));
        OperasiMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:03:19" }));
        OperasiMulai.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        OperasiMulai.setName("OperasiMulai"); // NOI18N
        OperasiMulai.setOpaque(false);
        OperasiMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OperasiMulaiKeyPressed(evt);
            }
        });
        FormInput.add(OperasiMulai);
        OperasiMulai.setBounds(120, 790, 130, 20);

        OperasiSelesai.setForeground(new java.awt.Color(50, 70, 50));
        OperasiSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:03:21" }));
        OperasiSelesai.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        OperasiSelesai.setName("OperasiSelesai"); // NOI18N
        OperasiSelesai.setOpaque(false);
        OperasiSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OperasiSelesaiKeyPressed(evt);
            }
        });
        FormInput.add(OperasiSelesai);
        OperasiSelesai.setBounds(120, 810, 130, 20);

        WaktuMasuk.setForeground(new java.awt.Color(50, 70, 50));
        WaktuMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:03:22" }));
        WaktuMasuk.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        WaktuMasuk.setName("WaktuMasuk"); // NOI18N
        WaktuMasuk.setOpaque(false);
        WaktuMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuMasukKeyPressed(evt);
            }
        });
        FormInput.add(WaktuMasuk);
        WaktuMasuk.setBounds(120, 710, 130, 20);

        WaktuKeluar.setForeground(new java.awt.Color(50, 70, 50));
        WaktuKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:03:26" }));
        WaktuKeluar.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        WaktuKeluar.setName("WaktuKeluar"); // NOI18N
        WaktuKeluar.setOpaque(false);
        WaktuKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuKeluarKeyPressed(evt);
            }
        });
        FormInput.add(WaktuKeluar);
        WaktuKeluar.setBounds(120, 730, 130, 20);

        Asa.setFocusTraversalPolicyProvider(true);
        Asa.setName("Asa"); // NOI18N
        Asa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsaActionPerformed(evt);
            }
        });
        Asa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsaKeyPressed(evt);
            }
        });
        FormInput.add(Asa);
        Asa.setBounds(60, 870, 150, 23);

        label73.setText("1. Persiapan");
        label73.setName("label73"); // NOI18N
        label73.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label73);
        label73.setBounds(260, 700, 70, 20);

        KulitPreOPUtuh.setBackground(new java.awt.Color(255, 255, 255));
        KulitPreOPUtuh.setText("Kulit pre Op utuh");
        KulitPreOPUtuh.setName("KulitPreOPUtuh"); // NOI18N
        KulitPreOPUtuh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KulitPreOPUtuhActionPerformed(evt);
            }
        });
        FormInput.add(KulitPreOPUtuh);
        KulitPreOPUtuh.setBounds(280, 740, 130, 20);

        label74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label74.setText("Persiapan kulit");
        label74.setName("label74"); // NOI18N
        label74.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label74);
        label74.setBounds(280, 720, 80, 20);

        LukaKotor.setBackground(new java.awt.Color(255, 255, 255));
        LukaKotor.setText("Kotor");
        LukaKotor.setName("LukaKotor"); // NOI18N
        LukaKotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LukaKotorActionPerformed(evt);
            }
        });
        FormInput.add(LukaKotor);
        LukaKotor.setBounds(390, 800, 80, 20);

        label75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label75.setText("Persiapan kulit oleh :");
        label75.setName("label75"); // NOI18N
        label75.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label75);
        label75.setBounds(280, 760, 100, 20);

        PersiapanKulitOleh.setFocusTraversalPolicyProvider(true);
        PersiapanKulitOleh.setName("PersiapanKulitOleh"); // NOI18N
        PersiapanKulitOleh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PersiapanKulitOlehActionPerformed(evt);
            }
        });
        PersiapanKulitOleh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PersiapanKulitOlehKeyPressed(evt);
            }
        });
        FormInput.add(PersiapanKulitOleh);
        PersiapanKulitOleh.setBounds(380, 760, 150, 23);

        label76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label76.setText("Klasifikasi luka");
        label76.setName("label76"); // NOI18N
        label76.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label76);
        label76.setBounds(280, 780, 100, 20);

        LukaBersih.setBackground(new java.awt.Color(255, 255, 255));
        LukaBersih.setText("Bersih");
        LukaBersih.setName("LukaBersih"); // NOI18N
        LukaBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LukaBersihActionPerformed(evt);
            }
        });
        FormInput.add(LukaBersih);
        LukaBersih.setBounds(280, 800, 100, 20);

        LukaTerkontaminasi.setBackground(new java.awt.Color(255, 255, 255));
        LukaTerkontaminasi.setText("Terkontaminasi");
        LukaTerkontaminasi.setName("LukaTerkontaminasi"); // NOI18N
        LukaTerkontaminasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LukaTerkontaminasiActionPerformed(evt);
            }
        });
        FormInput.add(LukaTerkontaminasi);
        LukaTerkontaminasi.setBounds(280, 820, 120, 20);

        label77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label77.setText("Posisi");
        label77.setName("label77"); // NOI18N
        label77.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label77);
        label77.setBounds(280, 840, 100, 20);

        PosisiLiteral.setBackground(new java.awt.Color(255, 255, 255));
        PosisiLiteral.setText("Lateral kn/kl");
        PosisiLiteral.setName("PosisiLiteral"); // NOI18N
        PosisiLiteral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PosisiLiteralActionPerformed(evt);
            }
        });
        FormInput.add(PosisiLiteral);
        PosisiLiteral.setBounds(360, 860, 130, 20);

        PosisiSupin.setBackground(new java.awt.Color(255, 255, 255));
        PosisiSupin.setText("Supine");
        PosisiSupin.setName("PosisiSupin"); // NOI18N
        PosisiSupin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PosisiSupinActionPerformed(evt);
            }
        });
        FormInput.add(PosisiSupin);
        PosisiSupin.setBounds(280, 860, 70, 20);

        PosisiUthotomi.setBackground(new java.awt.Color(255, 255, 255));
        PosisiUthotomi.setText("Uthomi");
        PosisiUthotomi.setName("PosisiUthotomi"); // NOI18N
        PosisiUthotomi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PosisiUthotomiActionPerformed(evt);
            }
        });
        FormInput.add(PosisiUthotomi);
        PosisiUthotomi.setBounds(280, 880, 70, 20);

        label78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label78.setText("Lain -lain :");
        label78.setName("label78"); // NOI18N
        label78.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label78);
        label78.setBounds(360, 880, 60, 20);

        PosisiLainnya.setFocusTraversalPolicyProvider(true);
        PosisiLainnya.setName("PosisiLainnya"); // NOI18N
        PosisiLainnya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PosisiLainnyaActionPerformed(evt);
            }
        });
        PosisiLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PosisiLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(PosisiLainnya);
        PosisiLainnya.setBounds(410, 880, 120, 23);

        label79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label79.setText("Electrocouter");
        label79.setName("label79"); // NOI18N
        label79.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label79);
        label79.setBounds(280, 900, 100, 20);

        label80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label80.setText("Pemasang :");
        label80.setName("label80"); // NOI18N
        label80.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label80);
        label80.setBounds(280, 920, 60, 20);

        PemasangElek.setFocusTraversalPolicyProvider(true);
        PemasangElek.setName("PemasangElek"); // NOI18N
        PemasangElek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PemasangElekActionPerformed(evt);
            }
        });
        PemasangElek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemasangElekKeyPressed(evt);
            }
        });
        FormInput.add(PemasangElek);
        PemasangElek.setBounds(340, 920, 190, 23);

        label81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label81.setText("Letak ground :");
        label81.setName("label81"); // NOI18N
        label81.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label81);
        label81.setBounds(280, 950, 70, 20);

        LetakGround.setFocusTraversalPolicyProvider(true);
        LetakGround.setName("LetakGround"); // NOI18N
        LetakGround.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LetakGroundActionPerformed(evt);
            }
        });
        LetakGround.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LetakGroundKeyPressed(evt);
            }
        });
        FormInput.add(LetakGround);
        LetakGround.setBounds(350, 950, 180, 23);

        label82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label82.setText("Coagulant :");
        label82.setName("label82"); // NOI18N
        label82.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label82);
        label82.setBounds(280, 980, 70, 20);

        Coagulant.setFocusTraversalPolicyProvider(true);
        Coagulant.setName("Coagulant"); // NOI18N
        Coagulant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoagulantActionPerformed(evt);
            }
        });
        Coagulant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CoagulantKeyPressed(evt);
            }
        });
        FormInput.add(Coagulant);
        Coagulant.setBounds(340, 980, 90, 23);

        label83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label83.setText("Cutting :");
        label83.setName("label83"); // NOI18N
        label83.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label83);
        label83.setBounds(440, 980, 50, 20);

        Cutting.setFocusTraversalPolicyProvider(true);
        Cutting.setName("Cutting"); // NOI18N
        Cutting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CuttingActionPerformed(evt);
            }
        });
        Cutting.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CuttingKeyPressed(evt);
            }
        });
        FormInput.add(Cutting);
        Cutting.setBounds(490, 980, 100, 23);

        MesinSuction.setBackground(new java.awt.Color(255, 255, 255));
        MesinSuction.setText("Mesin Suction");
        MesinSuction.setName("MesinSuction"); // NOI18N
        MesinSuction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MesinSuctionActionPerformed(evt);
            }
        });
        FormInput.add(MesinSuction);
        MesinSuction.setBounds(610, 710, 150, 20);

        BlanketWarmer.setBackground(new java.awt.Color(255, 255, 255));
        BlanketWarmer.setText("Blanket Warmer");
        BlanketWarmer.setName("BlanketWarmer"); // NOI18N
        BlanketWarmer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlanketWarmerActionPerformed(evt);
            }
        });
        FormInput.add(BlanketWarmer);
        BlanketWarmer.setBounds(610, 730, 150, 20);

        label84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label84.setText("Torniquet");
        label84.setName("label84"); // NOI18N
        label84.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label84);
        label84.setBounds(610, 750, 60, 20);

        label85.setText("Pemasang :");
        label85.setName("label85"); // NOI18N
        label85.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label85);
        label85.setBounds(610, 820, 70, 20);

        label86.setText("Jam mulai :");
        label86.setName("label86"); // NOI18N
        label86.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label86);
        label86.setBounds(620, 770, 60, 20);

        label87.setText("Jam Selesai :");
        label87.setName("label87"); // NOI18N
        label87.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label87);
        label87.setBounds(610, 790, 70, 20);

        JamMulaiTor.setForeground(new java.awt.Color(50, 70, 50));
        JamMulaiTor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:40:22" }));
        JamMulaiTor.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        JamMulaiTor.setName("JamMulaiTor"); // NOI18N
        JamMulaiTor.setOpaque(false);
        JamMulaiTor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamMulaiTorKeyPressed(evt);
            }
        });
        FormInput.add(JamMulaiTor);
        JamMulaiTor.setBounds(680, 770, 130, 20);

        JamSelesaiTor.setForeground(new java.awt.Color(50, 70, 50));
        JamSelesaiTor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:40:36" }));
        JamSelesaiTor.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        JamSelesaiTor.setName("JamSelesaiTor"); // NOI18N
        JamSelesaiTor.setOpaque(false);
        JamSelesaiTor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamSelesaiTorKeyPressed(evt);
            }
        });
        FormInput.add(JamSelesaiTor);
        JamSelesaiTor.setBounds(680, 790, 130, 22);

        PemasangTor.setFocusTraversalPolicyProvider(true);
        PemasangTor.setName("PemasangTor"); // NOI18N
        PemasangTor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PemasangTorActionPerformed(evt);
            }
        });
        PemasangTor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemasangTorKeyPressed(evt);
            }
        });
        FormInput.add(PemasangTor);
        PemasangTor.setBounds(680, 820, 150, 23);

        Graft.setBackground(new java.awt.Color(255, 255, 255));
        Graft.setText("Pemasangan Graft");
        Graft.setName("Graft"); // NOI18N
        Graft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GraftActionPerformed(evt);
            }
        });
        FormInput.add(Graft);
        Graft.setBounds(610, 840, 150, 20);

        label88.setText("Lokasi :");
        label88.setName("label88"); // NOI18N
        label88.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label88);
        label88.setBounds(610, 860, 70, 20);

        Lokasi.setFocusTraversalPolicyProvider(true);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LokasiActionPerformed(evt);
            }
        });
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(680, 860, 150, 23);

        label89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label89.setText("2. Darah");
        label89.setName("label89"); // NOI18N
        label89.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label89);
        label89.setBounds(600, 890, 170, 20);

        label90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label90.setText("Pendarahan intra op :");
        label90.setName("label90"); // NOI18N
        label90.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label90);
        label90.setBounds(610, 910, 110, 20);

        PendarahanIntraOP.setFocusTraversalPolicyProvider(true);
        PendarahanIntraOP.setName("PendarahanIntraOP"); // NOI18N
        PendarahanIntraOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PendarahanIntraOPActionPerformed(evt);
            }
        });
        PendarahanIntraOP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendarahanIntraOPKeyPressed(evt);
            }
        });
        FormInput.add(PendarahanIntraOP);
        PendarahanIntraOP.setBounds(720, 910, 90, 23);

        label91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label91.setText(" cc");
        label91.setName("label91"); // NOI18N
        label91.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label91);
        label91.setBounds(810, 910, 30, 20);

        ProdukDarah.setBackground(new java.awt.Color(255, 255, 255));
        ProdukDarah.setText("Produk darah");
        ProdukDarah.setName("ProdukDarah"); // NOI18N
        ProdukDarah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdukDarahActionPerformed(evt);
            }
        });
        FormInput.add(ProdukDarah);
        ProdukDarah.setBounds(610, 930, 110, 20);

        label92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label92.setText("No. Label/Kantung");
        label92.setName("label92"); // NOI18N
        label92.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label92);
        label92.setBounds(610, 940, 150, 40);

        label94.setText("Mulai :");
        label94.setName("label94"); // NOI18N
        label94.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label94);
        label94.setBounds(600, 970, 40, 20);

        label95.setText("Mulai :");
        label95.setName("label95"); // NOI18N
        label95.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label95);
        label95.setBounds(600, 990, 40, 20);

        JamMulaiDarah.setForeground(new java.awt.Color(50, 70, 50));
        JamMulaiDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:44:36" }));
        JamMulaiDarah.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        JamMulaiDarah.setName("JamMulaiDarah"); // NOI18N
        JamMulaiDarah.setOpaque(false);
        JamMulaiDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamMulaiDarahKeyPressed(evt);
            }
        });
        FormInput.add(JamMulaiDarah);
        JamMulaiDarah.setBounds(640, 970, 130, 20);

        JamMulaiDarah1.setForeground(new java.awt.Color(50, 70, 50));
        JamMulaiDarah1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:44:36" }));
        JamMulaiDarah1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        JamMulaiDarah1.setName("JamMulaiDarah1"); // NOI18N
        JamMulaiDarah1.setOpaque(false);
        JamMulaiDarah1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamMulaiDarah1KeyPressed(evt);
            }
        });
        FormInput.add(JamMulaiDarah1);
        JamMulaiDarah1.setBounds(640, 990, 130, 22);

        label96.setText("Selesai :");
        label96.setName("label96"); // NOI18N
        label96.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label96);
        label96.setBounds(780, 970, 40, 20);

        JamSelesaiDarah.setForeground(new java.awt.Color(50, 70, 50));
        JamSelesaiDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:45:55" }));
        JamSelesaiDarah.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        JamSelesaiDarah.setName("JamSelesaiDarah"); // NOI18N
        JamSelesaiDarah.setOpaque(false);
        JamSelesaiDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamSelesaiDarahKeyPressed(evt);
            }
        });
        FormInput.add(JamSelesaiDarah);
        JamSelesaiDarah.setBounds(820, 970, 130, 20);

        JamSelesaiDarah1.setForeground(new java.awt.Color(50, 70, 50));
        JamSelesaiDarah1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2025 15:45:55" }));
        JamSelesaiDarah1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        JamSelesaiDarah1.setName("JamSelesaiDarah1"); // NOI18N
        JamSelesaiDarah1.setOpaque(false);
        JamSelesaiDarah1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamSelesaiDarah1KeyPressed(evt);
            }
        });
        FormInput.add(JamSelesaiDarah1);
        JamSelesaiDarah1.setBounds(820, 990, 130, 22);

        label97.setText("Selesai :");
        label97.setName("label97"); // NOI18N
        label97.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label97);
        label97.setBounds(780, 990, 40, 20);

        label98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label98.setText("3. Spesimen");
        label98.setName("label98"); // NOI18N
        label98.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label98);
        label98.setBounds(900, 700, 130, 20);

        Rutin.setBackground(new java.awt.Color(255, 255, 255));
        Rutin.setText("Rutin");
        Rutin.setName("Rutin"); // NOI18N
        Rutin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RutinActionPerformed(evt);
            }
        });
        FormInput.add(Rutin);
        Rutin.setBounds(910, 720, 60, 20);

        PotongBeku.setBackground(new java.awt.Color(255, 255, 255));
        PotongBeku.setText("Potong beku");
        PotongBeku.setName("PotongBeku"); // NOI18N
        PotongBeku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PotongBekuActionPerformed(evt);
            }
        });
        FormInput.add(PotongBeku);
        PotongBeku.setBounds(910, 740, 100, 20);

        Kultur.setBackground(new java.awt.Color(255, 255, 255));
        Kultur.setText("Kultur");
        Kultur.setName("Kultur"); // NOI18N
        Kultur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KulturActionPerformed(evt);
            }
        });
        FormInput.add(Kultur);
        Kultur.setBounds(980, 720, 70, 20);

        label99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label99.setText("4. Kandung kemih");
        label99.setName("label99"); // NOI18N
        label99.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label99);
        label99.setBounds(900, 760, 130, 20);

        PenggunaanKateterIntra.setBackground(new java.awt.Color(255, 255, 255));
        PenggunaanKateterIntra.setText("Penggunaan kateter");
        PenggunaanKateterIntra.setName("PenggunaanKateterIntra"); // NOI18N
        PenggunaanKateterIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PenggunaanKateterIntraActionPerformed(evt);
            }
        });
        FormInput.add(PenggunaanKateterIntra);
        PenggunaanKateterIntra.setBounds(910, 780, 170, 20);

        label100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label100.setText("Produksi urine :");
        label100.setName("label100"); // NOI18N
        label100.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label100);
        label100.setBounds(910, 800, 80, 20);

        ProduksiUrineIntra.setFocusTraversalPolicyProvider(true);
        ProduksiUrineIntra.setName("ProduksiUrineIntra"); // NOI18N
        ProduksiUrineIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProduksiUrineIntraActionPerformed(evt);
            }
        });
        ProduksiUrineIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProduksiUrineIntraKeyPressed(evt);
            }
        });
        FormInput.add(ProduksiUrineIntra);
        ProduksiUrineIntra.setBounds(990, 800, 90, 23);

        label101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label101.setText(" cc");
        label101.setName("label101"); // NOI18N
        label101.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label101);
        label101.setBounds(1080, 800, 30, 20);

        label102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label102.setText("Lain-lain :");
        label102.setName("label102"); // NOI18N
        label102.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label102);
        label102.setBounds(910, 830, 50, 20);

        KemihLainnyaIntra.setFocusTraversalPolicyProvider(true);
        KemihLainnyaIntra.setName("KemihLainnyaIntra"); // NOI18N
        KemihLainnyaIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KemihLainnyaIntraActionPerformed(evt);
            }
        });
        KemihLainnyaIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KemihLainnyaIntraKeyPressed(evt);
            }
        });
        FormInput.add(KemihLainnyaIntra);
        KemihLainnyaIntra.setBounds(960, 830, 140, 23);

        label103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label103.setText("5. Status Mental");
        label103.setName("label103"); // NOI18N
        label103.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label103);
        label103.setBounds(900, 860, 130, 20);

        NangisIntra.setBackground(new java.awt.Color(255, 255, 255));
        NangisIntra.setText("Nangis");
        NangisIntra.setName("NangisIntra"); // NOI18N
        NangisIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NangisIntraActionPerformed(evt);
            }
        });
        FormInput.add(NangisIntra);
        NangisIntra.setBounds(1000, 880, 80, 20);

        TenangIntra.setBackground(new java.awt.Color(255, 255, 255));
        TenangIntra.setText("Tenang");
        TenangIntra.setName("TenangIntra"); // NOI18N
        TenangIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TenangIntraActionPerformed(evt);
            }
        });
        FormInput.add(TenangIntra);
        TenangIntra.setBounds(910, 880, 80, 20);

        GelisahIntra.setBackground(new java.awt.Color(255, 255, 255));
        GelisahIntra.setText("Gelisah");
        GelisahIntra.setName("GelisahIntra"); // NOI18N
        GelisahIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GelisahIntraActionPerformed(evt);
            }
        });
        FormInput.add(GelisahIntra);
        GelisahIntra.setBounds(910, 900, 130, 20);

        label104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label104.setText("Lain-lain :");
        label104.setName("label104"); // NOI18N
        label104.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label104);
        label104.setBounds(910, 920, 50, 20);

        MentalLainnyaIntra.setFocusTraversalPolicyProvider(true);
        MentalLainnyaIntra.setName("MentalLainnyaIntra"); // NOI18N
        MentalLainnyaIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MentalLainnyaIntraActionPerformed(evt);
            }
        });
        MentalLainnyaIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MentalLainnyaIntraKeyPressed(evt);
            }
        });
        FormInput.add(MentalLainnyaIntra);
        MentalLainnyaIntra.setBounds(960, 920, 140, 23);

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(10, 700, 1100, 320);

        label93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label93.setText("Diagnosa Keperawatan");
        label93.setName("label93"); // NOI18N
        label93.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label93);
        label93.setBounds(10, 1020, 240, 20);

        label105.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label105.setText("Perencanaan/Implementasi");
        label105.setName("label105"); // NOI18N
        label105.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label105);
        label105.setBounds(270, 1020, 320, 20);

        label106.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label106.setText("Evaluasi");
        label106.setName("label106"); // NOI18N
        label106.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label106);
        label106.setBounds(600, 1020, 510, 20);

        ResikoHipotermi.setBackground(new java.awt.Color(255, 255, 255));
        ResikoHipotermi.setText("Resiko hipotermi");
        ResikoHipotermi.setName("ResikoHipotermi"); // NOI18N
        ResikoHipotermi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResikoHipotermiActionPerformed(evt);
            }
        });
        FormInput.add(ResikoHipotermi);
        ResikoHipotermi.setBounds(30, 1090, 220, 20);

        KecemasanIntra.setBackground(new java.awt.Color(255, 255, 255));
        KecemasanIntra.setText("Kecemasan");
        KecemasanIntra.setName("KecemasanIntra"); // NOI18N
        KecemasanIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KecemasanIntraActionPerformed(evt);
            }
        });
        FormInput.add(KecemasanIntra);
        KecemasanIntra.setBounds(30, 1110, 240, 20);

        IntegritasKulitIntra.setBackground(new java.awt.Color(255, 255, 255));
        IntegritasKulitIntra.setText("Gangguan integritas kulit");
        IntegritasKulitIntra.setName("IntegritasKulitIntra"); // NOI18N
        IntegritasKulitIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntegritasKulitIntraActionPerformed(evt);
            }
        });
        FormInput.add(IntegritasKulitIntra);
        IntegritasKulitIntra.setBounds(30, 1130, 240, 20);

        TidakEfektifPolaNafasIntra.setBackground(new java.awt.Color(255, 255, 255));
        TidakEfektifPolaNafasIntra.setText("Tidak efektif pola nafas");
        TidakEfektifPolaNafasIntra.setName("TidakEfektifPolaNafasIntra"); // NOI18N
        TidakEfektifPolaNafasIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TidakEfektifPolaNafasIntraActionPerformed(evt);
            }
        });
        FormInput.add(TidakEfektifPolaNafasIntra);
        TidakEfektifPolaNafasIntra.setBounds(30, 1150, 240, 20);

        ResikoSyok.setBackground(new java.awt.Color(255, 255, 255));
        ResikoSyok.setText("Resiko syok hypovolemic");
        ResikoSyok.setName("ResikoSyok"); // NOI18N
        ResikoSyok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResikoSyokActionPerformed(evt);
            }
        });
        FormInput.add(ResikoSyok);
        ResikoSyok.setBounds(30, 1170, 240, 20);

        ResikoInfeksi.setBackground(new java.awt.Color(255, 255, 255));
        ResikoInfeksi.setText("Resiko infeksi");
        ResikoInfeksi.setName("ResikoInfeksi"); // NOI18N
        ResikoInfeksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResikoInfeksiActionPerformed(evt);
            }
        });
        FormInput.add(ResikoInfeksi);
        ResikoInfeksi.setBounds(30, 1050, 230, 20);

        ResikoCidera.setBackground(new java.awt.Color(255, 255, 255));
        ResikoCidera.setText("Resiko cidera");
        ResikoCidera.setName("ResikoCidera"); // NOI18N
        ResikoCidera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResikoCideraActionPerformed(evt);
            }
        });
        FormInput.add(ResikoCidera);
        ResikoCidera.setBounds(30, 1070, 220, 20);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        DiagnosaLainnyaIntra.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaLainnyaIntra.setColumns(20);
        DiagnosaLainnyaIntra.setRows(5);
        DiagnosaLainnyaIntra.setName("DiagnosaLainnyaIntra"); // NOI18N
        DiagnosaLainnyaIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaLainnyaIntraKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(DiagnosaLainnyaIntra);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(30, 1196, 220, 74);

        SiapkanPasienDimejaOP.setBackground(new java.awt.Color(255, 255, 255));
        SiapkanPasienDimejaOP.setText("Siapkan pasien dimeja operasi");
        SiapkanPasienDimejaOP.setName("SiapkanPasienDimejaOP"); // NOI18N
        SiapkanPasienDimejaOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SiapkanPasienDimejaOPActionPerformed(evt);
            }
        });
        FormInput.add(SiapkanPasienDimejaOP);
        SiapkanPasienDimejaOP.setBounds(270, 1090, 330, 20);

        ObservasiTTVIntra.setBackground(new java.awt.Color(255, 255, 255));
        ObservasiTTVIntra.setText("Observasi TTV");
        ObservasiTTVIntra.setName("ObservasiTTVIntra"); // NOI18N
        ObservasiTTVIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ObservasiTTVIntraActionPerformed(evt);
            }
        });
        FormInput.add(ObservasiTTVIntra);
        ObservasiTTVIntra.setBounds(270, 1110, 330, 20);

        AlatLinen.setBackground(new java.awt.Color(255, 255, 255));
        AlatLinen.setText("Siapkan alat dan linen");
        AlatLinen.setName("AlatLinen"); // NOI18N
        AlatLinen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlatLinenActionPerformed(evt);
            }
        });
        FormInput.add(AlatLinen);
        AlatLinen.setBounds(270, 1130, 330, 20);

        Elektromedis.setBackground(new java.awt.Color(255, 255, 255));
        Elektromedis.setText("Siapkan peralatan elektromedis");
        Elektromedis.setName("Elektromedis"); // NOI18N
        Elektromedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ElektromedisActionPerformed(evt);
            }
        });
        FormInput.add(Elektromedis);
        Elektromedis.setBounds(270, 1150, 330, 20);

        PosisiPasien.setBackground(new java.awt.Color(255, 255, 255));
        PosisiPasien.setText("Posisikan pasien sesuai dengan jenis operasi");
        PosisiPasien.setName("PosisiPasien"); // NOI18N
        PosisiPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PosisiPasienActionPerformed(evt);
            }
        });
        FormInput.add(PosisiPasien);
        PosisiPasien.setBounds(270, 1170, 330, 20);

        PersiapanOP.setBackground(new java.awt.Color(255, 255, 255));
        PersiapanOP.setText("Lakukan persiapan sebelum operasi (cuci tangan,");
        PersiapanOP.setName("PersiapanOP"); // NOI18N
        PersiapanOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PersiapanOPActionPerformed(evt);
            }
        });
        FormInput.add(PersiapanOP);
        PersiapanOP.setBounds(270, 1190, 330, 20);

        PersiapanKulit.setBackground(new java.awt.Color(255, 255, 255));
        PersiapanKulit.setText("Lakukan persiapan kulit");
        PersiapanKulit.setName("PersiapanKulit"); // NOI18N
        PersiapanKulit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PersiapanKulitActionPerformed(evt);
            }
        });
        FormInput.add(PersiapanKulit);
        PersiapanKulit.setBounds(270, 1240, 330, 20);

        TimeOut.setBackground(new java.awt.Color(255, 255, 255));
        TimeOut.setText("Lakukan time out");
        TimeOut.setName("TimeOut"); // NOI18N
        TimeOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimeOutActionPerformed(evt);
            }
        });
        FormInput.add(TimeOut);
        TimeOut.setBounds(270, 1260, 330, 20);

        SiapkanOK.setBackground(new java.awt.Color(255, 255, 255));
        SiapkanOK.setText("Siapkan kamar operasi");
        SiapkanOK.setName("SiapkanOK"); // NOI18N
        SiapkanOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SiapkanOKActionPerformed(evt);
            }
        });
        FormInput.add(SiapkanOK);
        SiapkanOK.setBounds(270, 1050, 330, 20);

        SiapkanSelimut.setBackground(new java.awt.Color(255, 255, 255));
        SiapkanSelimut.setText("Siapkan selimut hangat");
        SiapkanSelimut.setName("SiapkanSelimut"); // NOI18N
        SiapkanSelimut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SiapkanSelimutActionPerformed(evt);
            }
        });
        FormInput.add(SiapkanSelimut);
        SiapkanSelimut.setBounds(270, 1070, 330, 20);

        label107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label107.setText("memakai jas operasi dan sarung tangan)");
        label107.setName("label107"); // NOI18N
        label107.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label107);
        label107.setBounds(290, 1210, 310, 20);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        PerencanaanLainnyaIntra.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PerencanaanLainnyaIntra.setColumns(20);
        PerencanaanLainnyaIntra.setRows(5);
        PerencanaanLainnyaIntra.setName("PerencanaanLainnyaIntra"); // NOI18N
        PerencanaanLainnyaIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerencanaanLainnyaIntraKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(PerencanaanLainnyaIntra);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(270, 1280, 310, 50);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        SIntra.setBorder(javax.swing.BorderFactory.createTitledBorder("S"));
        SIntra.setColumns(20);
        SIntra.setRows(5);
        SIntra.setName("SIntra"); // NOI18N
        SIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SIntraKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(SIntra);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(610, 1050, 490, 50);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        OIntra.setBorder(javax.swing.BorderFactory.createTitledBorder("O"));
        OIntra.setColumns(20);
        OIntra.setRows(5);
        OIntra.setName("OIntra"); // NOI18N
        OIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OIntraKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(OIntra);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(610, 1110, 490, 130);

        label108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label108.setText("TD :");
        label108.setName("label108"); // NOI18N
        label108.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label108);
        label108.setBounds(610, 1250, 30, 20);

        TDIntra.setFocusTraversalPolicyProvider(true);
        TDIntra.setName("TDIntra"); // NOI18N
        TDIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TDIntraActionPerformed(evt);
            }
        });
        TDIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDIntraKeyPressed(evt);
            }
        });
        FormInput.add(TDIntra);
        TDIntra.setBounds(640, 1250, 80, 23);

        label109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label109.setText(" mmHg");
        label109.setName("label109"); // NOI18N
        label109.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label109);
        label109.setBounds(720, 1250, 40, 20);

        label110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label110.setText("N :");
        label110.setName("label110"); // NOI18N
        label110.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label110);
        label110.setBounds(770, 1250, 20, 20);

        NIntra.setFocusTraversalPolicyProvider(true);
        NIntra.setName("NIntra"); // NOI18N
        NIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NIntraActionPerformed(evt);
            }
        });
        NIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIntraKeyPressed(evt);
            }
        });
        FormInput.add(NIntra);
        NIntra.setBounds(790, 1250, 80, 23);

        label111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label111.setText(" x/mnt");
        label111.setName("label111"); // NOI18N
        label111.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label111);
        label111.setBounds(870, 1250, 40, 20);

        label112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label112.setText("R :");
        label112.setName("label112"); // NOI18N
        label112.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label112);
        label112.setBounds(920, 1250, 20, 20);

        RIntra.setFocusTraversalPolicyProvider(true);
        RIntra.setName("RIntra"); // NOI18N
        RIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RIntraActionPerformed(evt);
            }
        });
        RIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RIntraKeyPressed(evt);
            }
        });
        FormInput.add(RIntra);
        RIntra.setBounds(940, 1250, 80, 23);

        label113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label113.setText(" x/mnt");
        label113.setName("label113"); // NOI18N
        label113.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label113);
        label113.setBounds(1020, 1250, 40, 20);

        ABelumTercapaiIntra.setBackground(new java.awt.Color(255, 255, 255));
        ABelumTercapaiIntra.setText("Belum tercapai");
        ABelumTercapaiIntra.setName("ABelumTercapaiIntra"); // NOI18N
        ABelumTercapaiIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ABelumTercapaiIntraActionPerformed(evt);
            }
        });
        FormInput.add(ABelumTercapaiIntra);
        ABelumTercapaiIntra.setBounds(890, 1280, 150, 20);

        ATercapaiSebagianIntra.setBackground(new java.awt.Color(255, 255, 255));
        ATercapaiSebagianIntra.setText("Tercapai sebagian");
        ATercapaiSebagianIntra.setName("ATercapaiSebagianIntra"); // NOI18N
        ATercapaiSebagianIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATercapaiSebagianIntraActionPerformed(evt);
            }
        });
        FormInput.add(ATercapaiSebagianIntra);
        ATercapaiSebagianIntra.setBounds(730, 1280, 140, 20);

        ATercapaiIntra.setBackground(new java.awt.Color(255, 255, 255));
        ATercapaiIntra.setText("Tercapai");
        ATercapaiIntra.setName("ATercapaiIntra"); // NOI18N
        ATercapaiIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATercapaiIntraActionPerformed(evt);
            }
        });
        FormInput.add(ATercapaiIntra);
        ATercapaiIntra.setBounds(630, 1280, 90, 20);

        label114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label114.setText("A :");
        label114.setName("label114"); // NOI18N
        label114.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label114);
        label114.setBounds(610, 1280, 20, 20);

        label115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label115.setText("P :");
        label115.setName("label115"); // NOI18N
        label115.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label115);
        label115.setBounds(610, 1310, 20, 20);

        PIntra.setBackground(new java.awt.Color(255, 255, 255));
        PIntra.setText("-");
        PIntra.setName("PIntra"); // NOI18N
        PIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PIntraActionPerformed(evt);
            }
        });
        FormInput.add(PIntra);
        PIntra.setBounds(630, 1310, 50, 20);

        PPertahankanIntra.setBackground(new java.awt.Color(255, 255, 255));
        PPertahankanIntra.setText("Pertahankan");
        PPertahankanIntra.setName("PPertahankanIntra"); // NOI18N
        PPertahankanIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPertahankanIntraActionPerformed(evt);
            }
        });
        FormInput.add(PPertahankanIntra);
        PPertahankanIntra.setBounds(680, 1310, 110, 20);

        label116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label116.setText("Lanjutkan");
        label116.setName("label116"); // NOI18N
        label116.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label116);
        label116.setBounds(790, 1310, 60, 20);

        PLanjutkanIntra.setFocusTraversalPolicyProvider(true);
        PLanjutkanIntra.setName("PLanjutkanIntra"); // NOI18N
        PLanjutkanIntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLanjutkanIntraActionPerformed(evt);
            }
        });
        PLanjutkanIntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PLanjutkanIntraKeyPressed(evt);
            }
        });
        FormInput.add(PLanjutkanIntra);
        PLanjutkanIntra.setBounds(840, 1310, 260, 23);

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(10, 1040, 1100, 300);

        label117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label117.setText("POST OPERASI");
        label117.setName("label117"); // NOI18N
        label117.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label117);
        label117.setBounds(10, 1340, 1100, 30);

        JamPengkajianPost.setForeground(new java.awt.Color(50, 70, 50));
        JamPengkajianPost.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-06-2025 11:43:53" }));
        JamPengkajianPost.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        JamPengkajianPost.setName("JamPengkajianPost"); // NOI18N
        JamPengkajianPost.setOpaque(false);
        JamPengkajianPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamPengkajianPostKeyPressed(evt);
            }
        });
        FormInput.add(JamPengkajianPost);
        JamPengkajianPost.setBounds(150, 1360, 130, 22);

        label118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label118.setText("PENGKAJIAN");
        label118.setName("label118"); // NOI18N
        label118.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label118);
        label118.setBounds(20, 1360, 90, 20);

        label119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label119.setText("JAM :");
        label119.setName("label119"); // NOI18N
        label119.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label119);
        label119.setBounds(110, 1360, 30, 20);

        label120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label120.setText("1. Pernafasan");
        label120.setName("label120"); // NOI18N
        label120.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label120);
        label120.setBounds(20, 1390, 130, 20);

        OtotBantuNafasPost.setBackground(new java.awt.Color(255, 255, 255));
        OtotBantuNafasPost.setText("Otot bantu nafas");
        OtotBantuNafasPost.setName("OtotBantuNafasPost"); // NOI18N
        OtotBantuNafasPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OtotBantuNafasPostActionPerformed(evt);
            }
        });
        FormInput.add(OtotBantuNafasPost);
        OtotBantuNafasPost.setBounds(30, 1410, 120, 20);

        DeformitasPost.setBackground(new java.awt.Color(255, 255, 255));
        DeformitasPost.setText("Deformitas");
        DeformitasPost.setName("DeformitasPost"); // NOI18N
        DeformitasPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeformitasPostActionPerformed(evt);
            }
        });
        FormInput.add(DeformitasPost);
        DeformitasPost.setBounds(30, 1430, 120, 20);

        EmpisemaPost.setBackground(new java.awt.Color(255, 255, 255));
        EmpisemaPost.setText("Empisema");
        EmpisemaPost.setName("EmpisemaPost"); // NOI18N
        EmpisemaPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmpisemaPostActionPerformed(evt);
            }
        });
        FormInput.add(EmpisemaPost);
        EmpisemaPost.setBounds(30, 1450, 120, 20);

        label121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label121.setText("Pengembangan Dada");
        label121.setName("label121"); // NOI18N
        label121.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label121);
        label121.setBounds(30, 1470, 120, 20);

        PengembanganDadaRPost.setText("R");
        PengembanganDadaRPost.setName("PengembanganDadaRPost"); // NOI18N
        PengembanganDadaRPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PengembanganDadaRPostActionPerformed(evt);
            }
        });
        FormInput.add(PengembanganDadaRPost);
        PengembanganDadaRPost.setBounds(30, 1490, 40, 20);

        PengembanganDadaLPost.setText("L");
        PengembanganDadaLPost.setName("PengembanganDadaLPost"); // NOI18N
        PengembanganDadaLPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PengembanganDadaLPostActionPerformed(evt);
            }
        });
        FormInput.add(PengembanganDadaLPost);
        PengembanganDadaLPost.setBounds(80, 1490, 50, 20);

        label122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label122.setText("SpO2 :");
        label122.setName("label122"); // NOI18N
        label122.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label122);
        label122.setBounds(30, 1540, 40, 20);

        label123.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label123.setText(" %");
        label123.setName("label123"); // NOI18N
        label123.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label123);
        label123.setBounds(190, 1540, 40, 20);

        RRPost.setFocusTraversalPolicyProvider(true);
        RRPost.setName("RRPost"); // NOI18N
        RRPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRPostKeyPressed(evt);
            }
        });
        FormInput.add(RRPost);
        RRPost.setBounds(70, 1510, 120, 23);

        Spo2Post.setFocusTraversalPolicyProvider(true);
        Spo2Post.setName("Spo2Post"); // NOI18N
        Spo2Post.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Spo2PostKeyPressed(evt);
            }
        });
        FormInput.add(Spo2Post);
        Spo2Post.setBounds(70, 1540, 120, 23);

        label124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label124.setText("RR :");
        label124.setName("label124"); // NOI18N
        label124.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label124);
        label124.setBounds(40, 1510, 40, 20);

        label125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label125.setText(" x/m");
        label125.setName("label125"); // NOI18N
        label125.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label125);
        label125.setBounds(190, 1510, 40, 20);

        NormalPost.setBackground(new java.awt.Color(255, 255, 255));
        NormalPost.setText("Normal");
        NormalPost.setName("NormalPost"); // NOI18N
        NormalPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NormalPostActionPerformed(evt);
            }
        });
        FormInput.add(NormalPost);
        NormalPost.setBounds(160, 1410, 70, 20);

        label126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label126.setText("2. Darah");
        label126.setName("label126"); // NOI18N
        label126.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label126);
        label126.setBounds(240, 1390, 70, 20);

        label127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label127.setText("Capillary Refill");
        label127.setName("label127"); // NOI18N
        label127.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label127);
        label127.setBounds(250, 1410, 80, 20);

        Capillary1Post.setBackground(new java.awt.Color(255, 255, 255));
        Capillary1Post.setText("< 2 Detik");
        Capillary1Post.setName("Capillary1Post"); // NOI18N
        Capillary1Post.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Capillary1PostActionPerformed(evt);
            }
        });
        FormInput.add(Capillary1Post);
        Capillary1Post.setBounds(250, 1430, 90, 20);

        Capillary2Post.setBackground(new java.awt.Color(255, 255, 255));
        Capillary2Post.setText("> 2 Detik");
        Capillary2Post.setName("Capillary2Post"); // NOI18N
        Capillary2Post.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Capillary2PostActionPerformed(evt);
            }
        });
        FormInput.add(Capillary2Post);
        Capillary2Post.setBounds(250, 1450, 90, 20);

        label128.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label128.setText("ST/CT :");
        label128.setName("label128"); // NOI18N
        label128.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label128);
        label128.setBounds(250, 1480, 60, 20);

        label129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label129.setText("Tensi :");
        label129.setName("label129"); // NOI18N
        label129.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label129);
        label129.setBounds(250, 1530, 80, 20);

        Ket_PendarahanPost.setFocusTraversalPolicyProvider(true);
        Ket_PendarahanPost.setName("Ket_PendarahanPost"); // NOI18N
        Ket_PendarahanPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ket_PendarahanPostActionPerformed(evt);
            }
        });
        Ket_PendarahanPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Ket_PendarahanPostKeyPressed(evt);
            }
        });
        FormInput.add(Ket_PendarahanPost);
        Ket_PendarahanPost.setBounds(350, 1510, 100, 23);

        label130.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label130.setText(" cc");
        label130.setName("label130"); // NOI18N
        label130.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label130);
        label130.setBounds(450, 1510, 30, 20);

        PendarahanPost.setBackground(new java.awt.Color(255, 255, 255));
        PendarahanPost.setText("Perdarahan :");
        PendarahanPost.setName("PendarahanPost"); // NOI18N
        PendarahanPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PendarahanPostActionPerformed(evt);
            }
        });
        FormInput.add(PendarahanPost);
        PendarahanPost.setBounds(250, 1510, 110, 20);

        TensiPost.setFocusTraversalPolicyProvider(true);
        TensiPost.setName("TensiPost"); // NOI18N
        TensiPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TensiPostActionPerformed(evt);
            }
        });
        TensiPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TensiPostKeyPressed(evt);
            }
        });
        FormInput.add(TensiPost);
        TensiPost.setBounds(320, 1540, 100, 23);

        label131.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label131.setText(" mmhg");
        label131.setName("label131"); // NOI18N
        label131.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label131);
        label131.setBounds(420, 1540, 50, 20);

        label132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label132.setText("Nd :");
        label132.setName("label132"); // NOI18N
        label132.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label132);
        label132.setBounds(260, 1560, 80, 20);

        NdPost.setFocusTraversalPolicyProvider(true);
        NdPost.setName("NdPost"); // NOI18N
        NdPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NdPostActionPerformed(evt);
            }
        });
        NdPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NdPostKeyPressed(evt);
            }
        });
        FormInput.add(NdPost);
        NdPost.setBounds(320, 1570, 100, 23);

        label133.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label133.setText(" K/mmt");
        label133.setName("label133"); // NOI18N
        label133.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label133);
        label133.setBounds(420, 1570, 50, 20);

        label134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label134.setText("Suhu :");
        label134.setName("label134"); // NOI18N
        label134.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label134);
        label134.setBounds(250, 1600, 80, 20);

        SuhuPost.setFocusTraversalPolicyProvider(true);
        SuhuPost.setName("SuhuPost"); // NOI18N
        SuhuPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuhuPostActionPerformed(evt);
            }
        });
        SuhuPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuPostKeyPressed(evt);
            }
        });
        FormInput.add(SuhuPost);
        SuhuPost.setBounds(320, 1600, 100, 23);

        label135.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label135.setText(" C");
        label135.setName("label135"); // NOI18N
        label135.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label135);
        label135.setBounds(420, 1600, 50, 20);

        label136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label136.setText("Kulit");
        label136.setName("label136"); // NOI18N
        label136.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label136);
        label136.setBounds(340, 1410, 70, 20);

        KulitDinginPost.setBackground(new java.awt.Color(255, 255, 255));
        KulitDinginPost.setText("Dingin");
        KulitDinginPost.setName("KulitDinginPost"); // NOI18N
        KulitDinginPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KulitDinginPostActionPerformed(evt);
            }
        });
        FormInput.add(KulitDinginPost);
        KulitDinginPost.setBounds(340, 1450, 80, 20);

        KulitLainnyaPost.setBackground(new java.awt.Color(255, 255, 255));
        KulitLainnyaPost.setText("Lainnya");
        KulitLainnyaPost.setName("KulitLainnyaPost"); // NOI18N
        KulitLainnyaPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KulitLainnyaPostActionPerformed(evt);
            }
        });
        FormInput.add(KulitLainnyaPost);
        KulitLainnyaPost.setBounds(340, 1470, 80, 20);

        KulitHangatPost.setBackground(new java.awt.Color(255, 255, 255));
        KulitHangatPost.setText("Hangat");
        KulitHangatPost.setName("KulitHangatPost"); // NOI18N
        KulitHangatPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KulitHangatPostActionPerformed(evt);
            }
        });
        FormInput.add(KulitHangatPost);
        KulitHangatPost.setBounds(340, 1430, 80, 20);

        label137.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label137.setText("3. Skala Nyeri :");
        label137.setName("label137"); // NOI18N
        label137.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label137);
        label137.setBounds(490, 1400, 80, 20);

        ProduksiUrinePost.setFocusTraversalPolicyProvider(true);
        ProduksiUrinePost.setName("ProduksiUrinePost"); // NOI18N
        ProduksiUrinePost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProduksiUrinePostActionPerformed(evt);
            }
        });
        ProduksiUrinePost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProduksiUrinePostKeyPressed(evt);
            }
        });
        FormInput.add(ProduksiUrinePost);
        ProduksiUrinePost.setBounds(510, 1490, 180, 23);

        label138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label138.setText("4. Kandung Kemih");
        label138.setName("label138"); // NOI18N
        label138.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label138);
        label138.setBounds(490, 1430, 110, 20);

        SkalaNyeriPost.setFocusTraversalPolicyProvider(true);
        SkalaNyeriPost.setName("SkalaNyeriPost"); // NOI18N
        SkalaNyeriPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkalaNyeriPostActionPerformed(evt);
            }
        });
        SkalaNyeriPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriPostKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeriPost);
        SkalaNyeriPost.setBounds(570, 1400, 120, 23);

        PenggunaanKateterPost.setBackground(new java.awt.Color(255, 255, 255));
        PenggunaanKateterPost.setText("Penggunaan Kateter");
        PenggunaanKateterPost.setName("PenggunaanKateterPost"); // NOI18N
        PenggunaanKateterPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PenggunaanKateterPostActionPerformed(evt);
            }
        });
        FormInput.add(PenggunaanKateterPost);
        PenggunaanKateterPost.setBounds(500, 1450, 150, 20);

        label139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label139.setText("Produksi Urine :");
        label139.setName("label139"); // NOI18N
        label139.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label139);
        label139.setBounds(510, 1470, 90, 20);

        label140.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label140.setText("Lain - lain :");
        label140.setName("label140"); // NOI18N
        label140.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label140);
        label140.setBounds(500, 1520, 60, 20);

        KemihLainnyaPost.setFocusTraversalPolicyProvider(true);
        KemihLainnyaPost.setName("KemihLainnyaPost"); // NOI18N
        KemihLainnyaPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KemihLainnyaPostActionPerformed(evt);
            }
        });
        KemihLainnyaPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KemihLainnyaPostKeyPressed(evt);
            }
        });
        FormInput.add(KemihLainnyaPost);
        KemihLainnyaPost.setBounds(560, 1520, 130, 23);

        label141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label141.setText("5. Otak");
        label141.setName("label141"); // NOI18N
        label141.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label141);
        label141.setBounds(720, 1390, 110, 20);

        label142.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label142.setText("Kesadaran");
        label142.setName("label142"); // NOI18N
        label142.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label142);
        label142.setBounds(730, 1410, 100, 20);

        DeltriumPost.setBackground(new java.awt.Color(255, 255, 255));
        DeltriumPost.setText("Deltrium");
        DeltriumPost.setName("DeltriumPost"); // NOI18N
        DeltriumPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeltriumPostActionPerformed(evt);
            }
        });
        FormInput.add(DeltriumPost);
        DeltriumPost.setBounds(730, 1450, 120, 20);

        StuperPost.setBackground(new java.awt.Color(255, 255, 255));
        StuperPost.setText("Stuper");
        StuperPost.setName("StuperPost"); // NOI18N
        StuperPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StuperPostActionPerformed(evt);
            }
        });
        FormInput.add(StuperPost);
        StuperPost.setBounds(730, 1470, 120, 20);

        ApatisPost.setBackground(new java.awt.Color(255, 255, 255));
        ApatisPost.setText("Apatis");
        ApatisPost.setName("ApatisPost"); // NOI18N
        ApatisPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApatisPostActionPerformed(evt);
            }
        });
        FormInput.add(ApatisPost);
        ApatisPost.setBounds(730, 1490, 120, 20);

        KomaPost.setBackground(new java.awt.Color(255, 255, 255));
        KomaPost.setText("Koma");
        KomaPost.setName("KomaPost"); // NOI18N
        KomaPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KomaPostActionPerformed(evt);
            }
        });
        FormInput.add(KomaPost);
        KomaPost.setBounds(730, 1510, 120, 20);

        ComposMentisPost.setBackground(new java.awt.Color(255, 255, 255));
        ComposMentisPost.setText("Compos mentis");
        ComposMentisPost.setName("ComposMentisPost"); // NOI18N
        ComposMentisPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComposMentisPostActionPerformed(evt);
            }
        });
        FormInput.add(ComposMentisPost);
        ComposMentisPost.setBounds(730, 1430, 120, 20);

        label143.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label143.setText("6. Abdomen");
        label143.setName("label143"); // NOI18N
        label143.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label143);
        label143.setBounds(870, 1390, 90, 20);

        label144.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label144.setText("BB :");
        label144.setName("label144"); // NOI18N
        label144.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label144);
        label144.setBounds(880, 1410, 30, 20);

        BbPost.setFocusTraversalPolicyProvider(true);
        BbPost.setName("BbPost"); // NOI18N
        BbPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BbPostActionPerformed(evt);
            }
        });
        BbPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BbPostKeyPressed(evt);
            }
        });
        FormInput.add(BbPost);
        BbPost.setBounds(910, 1410, 70, 23);

        label145.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label145.setText(" kg");
        label145.setName("label145"); // NOI18N
        label145.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label145);
        label145.setBounds(980, 1410, 40, 20);

        MualPost.setBackground(new java.awt.Color(255, 255, 255));
        MualPost.setText("Mual");
        MualPost.setName("MualPost"); // NOI18N
        MualPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MualPostActionPerformed(evt);
            }
        });
        FormInput.add(MualPost);
        MualPost.setBounds(880, 1460, 70, 20);

        CideraKulitPost.setBackground(new java.awt.Color(255, 255, 255));
        CideraKulitPost.setText("Cidera kulit POST OP");
        CideraKulitPost.setName("CideraKulitPost"); // NOI18N
        CideraKulitPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CideraKulitPostActionPerformed(evt);
            }
        });
        FormInput.add(CideraKulitPost);
        CideraKulitPost.setBounds(880, 1500, 200, 20);

        YaPost.setBackground(new java.awt.Color(255, 255, 255));
        YaPost.setText("Ya");
        YaPost.setName("YaPost"); // NOI18N
        YaPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YaPostActionPerformed(evt);
            }
        });
        FormInput.add(YaPost);
        YaPost.setBounds(960, 1570, 60, 20);

        TidakPost.setBackground(new java.awt.Color(255, 255, 255));
        TidakPost.setText("Tidak");
        TidakPost.setName("TidakPost"); // NOI18N
        TidakPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TidakPostActionPerformed(evt);
            }
        });
        FormInput.add(TidakPost);
        TidakPost.setBounds(1020, 1570, 80, 20);

        DistensilPost.setBackground(new java.awt.Color(255, 255, 255));
        DistensilPost.setText("Distensil");
        DistensilPost.setName("DistensilPost"); // NOI18N
        DistensilPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DistensilPostActionPerformed(evt);
            }
        });
        FormInput.add(DistensilPost);
        DistensilPost.setBounds(950, 1440, 120, 20);

        MuntahPost.setBackground(new java.awt.Color(255, 255, 255));
        MuntahPost.setText("Muntah");
        MuntahPost.setName("MuntahPost"); // NOI18N
        MuntahPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MuntahPostActionPerformed(evt);
            }
        });
        FormInput.add(MuntahPost);
        MuntahPost.setBounds(950, 1460, 80, 20);

        DrainPost.setBackground(new java.awt.Color(255, 255, 255));
        DrainPost.setText("Drain");
        DrainPost.setName("DrainPost"); // NOI18N
        DrainPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DrainPostActionPerformed(evt);
            }
        });
        FormInput.add(DrainPost);
        DrainPost.setBounds(880, 1570, 80, 20);

        PuasaPost.setBackground(new java.awt.Color(255, 255, 255));
        PuasaPost.setText("Puasa");
        PuasaPost.setName("PuasaPost"); // NOI18N
        PuasaPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PuasaPostActionPerformed(evt);
            }
        });
        FormInput.add(PuasaPost);
        PuasaPost.setBounds(880, 1440, 70, 20);

        label146.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label146.setText("7. Kulit");
        label146.setName("label146"); // NOI18N
        label146.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label146);
        label146.setBounds(870, 1480, 90, 20);

        label147.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label147.setText("8. Lain-lain");
        label147.setName("label147"); // NOI18N
        label147.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label147);
        label147.setBounds(870, 1540, 90, 20);

        label148.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label148.setText("Jenis balutan :");
        label148.setName("label148"); // NOI18N
        label148.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label148);
        label148.setBounds(880, 1520, 70, 20);

        JenisBalutanPost.setFocusTraversalPolicyProvider(true);
        JenisBalutanPost.setName("JenisBalutanPost"); // NOI18N
        JenisBalutanPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JenisBalutanPostActionPerformed(evt);
            }
        });
        JenisBalutanPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisBalutanPostKeyPressed(evt);
            }
        });
        FormInput.add(JenisBalutanPost);
        JenisBalutanPost.setBounds(950, 1530, 150, 23);

        ProduksiDrainPost.setFocusTraversalPolicyProvider(true);
        ProduksiDrainPost.setName("ProduksiDrainPost"); // NOI18N
        ProduksiDrainPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProduksiDrainPostActionPerformed(evt);
            }
        });
        ProduksiDrainPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProduksiDrainPostKeyPressed(evt);
            }
        });
        FormInput.add(ProduksiDrainPost);
        ProduksiDrainPost.setBounds(960, 1590, 100, 23);

        label149.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label149.setText("Produksi Drain :");
        label149.setName("label149"); // NOI18N
        label149.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label149);
        label149.setBounds(880, 1590, 80, 20);

        PemasanganAlatPost.setBackground(new java.awt.Color(255, 255, 255));
        PemasanganAlatPost.setText("Pemasangan alat");
        PemasanganAlatPost.setName("PemasanganAlatPost"); // NOI18N
        PemasanganAlatPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PemasanganAlatPostActionPerformed(evt);
            }
        });
        FormInput.add(PemasanganAlatPost);
        PemasanganAlatPost.setBounds(880, 1610, 180, 20);

        label150.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label150.setText("Evaluasi");
        label150.setName("label150"); // NOI18N
        label150.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label150);
        label150.setBounds(630, 1690, 480, 20);

        label151.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label151.setText("Diagnosa Keperawatan");
        label151.setName("label151"); // NOI18N
        label151.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label151);
        label151.setBounds(10, 1690, 310, 20);

        label152.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label152.setText("Perencanaan/Implementasi");
        label152.setName("label152"); // NOI18N
        label152.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label152);
        label152.setBounds(330, 1690, 280, 20);

        label153.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label153.setText(" cc");
        label153.setName("label153"); // NOI18N
        label153.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label153);
        label153.setBounds(1060, 1590, 30, 20);

        label154.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label154.setText("Lokasi :");
        label154.setName("label154"); // NOI18N
        label154.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label154);
        label154.setBounds(890, 1630, 40, 23);

        LokasiPost.setFocusTraversalPolicyProvider(true);
        LokasiPost.setName("LokasiPost"); // NOI18N
        LokasiPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LokasiPostActionPerformed(evt);
            }
        });
        LokasiPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiPostKeyPressed(evt);
            }
        });
        FormInput.add(LokasiPost);
        LokasiPost.setBounds(930, 1630, 170, 23);

        label155.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label155.setText("Jenis :");
        label155.setName("label155"); // NOI18N
        label155.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label155);
        label155.setBounds(890, 1660, 40, 23);

        JenisPost.setFocusTraversalPolicyProvider(true);
        JenisPost.setName("JenisPost"); // NOI18N
        JenisPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JenisPostActionPerformed(evt);
            }
        });
        JenisPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPostKeyPressed(evt);
            }
        });
        FormInput.add(JenisPost);
        JenisPost.setBounds(930, 1660, 170, 23);

        label156.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label156.setText("GCS :");
        label156.setName("label156"); // NOI18N
        label156.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label156);
        label156.setBounds(730, 1530, 40, 23);

        GCSPost.setFocusTraversalPolicyProvider(true);
        GCSPost.setName("GCSPost"); // NOI18N
        GCSPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GCSPostActionPerformed(evt);
            }
        });
        GCSPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSPostKeyPressed(evt);
            }
        });
        FormInput.add(GCSPost);
        GCSPost.setBounds(730, 1550, 130, 23);

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(10, 1390, 1100, 300);

        RestiHypotermiPost.setBackground(new java.awt.Color(255, 255, 255));
        RestiHypotermiPost.setText("Resti hyportemi / Hypotermi");
        RestiHypotermiPost.setName("RestiHypotermiPost"); // NOI18N
        RestiHypotermiPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestiHypotermiPostActionPerformed(evt);
            }
        });
        FormInput.add(RestiHypotermiPost);
        RestiHypotermiPost.setBounds(30, 1760, 270, 20);

        ResikoCideraPost.setBackground(new java.awt.Color(255, 255, 255));
        ResikoCideraPost.setText("Resiko cidera / Cidera");
        ResikoCideraPost.setName("ResikoCideraPost"); // NOI18N
        ResikoCideraPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResikoCideraPostActionPerformed(evt);
            }
        });
        FormInput.add(ResikoCideraPost);
        ResikoCideraPost.setBounds(30, 1780, 270, 20);

        RestiPendarahanPost.setBackground(new java.awt.Color(255, 255, 255));
        RestiPendarahanPost.setText("Resti pendarahan / Pendarahan");
        RestiPendarahanPost.setName("RestiPendarahanPost"); // NOI18N
        RestiPendarahanPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestiPendarahanPostActionPerformed(evt);
            }
        });
        FormInput.add(RestiPendarahanPost);
        RestiPendarahanPost.setBounds(30, 1800, 270, 20);

        RestiSyokPost.setBackground(new java.awt.Color(255, 255, 255));
        RestiSyokPost.setText("Resti syok hypovolemik");
        RestiSyokPost.setName("RestiSyokPost"); // NOI18N
        RestiSyokPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestiSyokPostActionPerformed(evt);
            }
        });
        FormInput.add(RestiSyokPost);
        RestiSyokPost.setBounds(30, 1820, 270, 20);

        MobilitasFisikPost.setBackground(new java.awt.Color(255, 255, 255));
        MobilitasFisikPost.setText("Hambatan mobilitas fisik");
        MobilitasFisikPost.setName("MobilitasFisikPost"); // NOI18N
        MobilitasFisikPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MobilitasFisikPostActionPerformed(evt);
            }
        });
        FormInput.add(MobilitasFisikPost);
        MobilitasFisikPost.setBounds(30, 1860, 270, 20);

        PotensiInfeksi.setBackground(new java.awt.Color(255, 255, 255));
        PotensiInfeksi.setText("Resti / Potensi infeksi");
        PotensiInfeksi.setName("PotensiInfeksi"); // NOI18N
        PotensiInfeksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PotensiInfeksiActionPerformed(evt);
            }
        });
        FormInput.add(PotensiInfeksi);
        PotensiInfeksi.setBounds(30, 1880, 270, 20);

        PerluasanInfeksiPost.setBackground(new java.awt.Color(255, 255, 255));
        PerluasanInfeksiPost.setText("Resti infeksi / Perluasan infeksi");
        PerluasanInfeksiPost.setName("PerluasanInfeksiPost"); // NOI18N
        PerluasanInfeksiPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerluasanInfeksiPostActionPerformed(evt);
            }
        });
        FormInput.add(PerluasanInfeksiPost);
        PerluasanInfeksiPost.setBounds(30, 1900, 270, 20);

        IntegritasKulitPost.setBackground(new java.awt.Color(255, 255, 255));
        IntegritasKulitPost.setText("Kerusakan integritas kullit / jaringan");
        IntegritasKulitPost.setName("IntegritasKulitPost"); // NOI18N
        IntegritasKulitPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntegritasKulitPostActionPerformed(evt);
            }
        });
        FormInput.add(IntegritasKulitPost);
        IntegritasKulitPost.setBounds(30, 1840, 270, 20);

        RestiNyeriPost.setBackground(new java.awt.Color(255, 255, 255));
        RestiNyeriPost.setText("Resti nyeri / Nyeri");
        RestiNyeriPost.setName("RestiNyeriPost"); // NOI18N
        RestiNyeriPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestiNyeriPostActionPerformed(evt);
            }
        });
        FormInput.add(RestiNyeriPost);
        RestiNyeriPost.setBounds(30, 1720, 270, 20);

        RestiInefektifPost.setBackground(new java.awt.Color(255, 255, 255));
        RestiInefektifPost.setText("Resti inefektif bersihan jalan nanfas");
        RestiInefektifPost.setName("RestiInefektifPost"); // NOI18N
        RestiInefektifPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestiInefektifPostActionPerformed(evt);
            }
        });
        FormInput.add(RestiInefektifPost);
        RestiInefektifPost.setBounds(30, 1740, 270, 20);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        DiagnosaLainnyaPost.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaLainnyaPost.setColumns(20);
        DiagnosaLainnyaPost.setRows(5);
        DiagnosaLainnyaPost.setName("DiagnosaLainnyaPost"); // NOI18N
        DiagnosaLainnyaPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaLainnyaPostKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(DiagnosaLainnyaPost);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(30, 1926, 290, 80);

        SelimutHangatPost.setBackground(new java.awt.Color(255, 255, 255));
        SelimutHangatPost.setText("Berikan selimut hangat");
        SelimutHangatPost.setName("SelimutHangatPost"); // NOI18N
        SelimutHangatPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelimutHangatPostActionPerformed(evt);
            }
        });
        FormInput.add(SelimutHangatPost);
        SelimutHangatPost.setBounds(350, 1760, 200, 20);

        TerapiOksigen.setBackground(new java.awt.Color(255, 255, 255));
        TerapiOksigen.setText("Berikan terapi oksigen");
        TerapiOksigen.setName("TerapiOksigen"); // NOI18N
        TerapiOksigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerapiOksigenActionPerformed(evt);
            }
        });
        FormInput.add(TerapiOksigen);
        TerapiOksigen.setBounds(350, 1780, 200, 20);

        SerahTerimaPost.setBackground(new java.awt.Color(255, 255, 255));
        SerahTerimaPost.setText("Lakukan serah terima dengan penata");
        SerahTerimaPost.setName("SerahTerimaPost"); // NOI18N
        SerahTerimaPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SerahTerimaPostActionPerformed(evt);
            }
        });
        FormInput.add(SerahTerimaPost);
        SerahTerimaPost.setBounds(350, 1800, 260, 20);

        BerikanPasienPadaKeluargaPost.setBackground(new java.awt.Color(255, 255, 255));
        BerikanPasienPadaKeluargaPost.setText("Berikan pantas pada keluarga");
        BerikanPasienPadaKeluargaPost.setName("BerikanPasienPadaKeluargaPost"); // NOI18N
        BerikanPasienPadaKeluargaPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BerikanPasienPadaKeluargaPostActionPerformed(evt);
            }
        });
        FormInput.add(BerikanPasienPadaKeluargaPost);
        BerikanPasienPadaKeluargaPost.setBounds(350, 1870, 220, 20);

        SignOut.setBackground(new java.awt.Color(255, 255, 255));
        SignOut.setText("Lakukan Sign Out");
        SignOut.setName("SignOut"); // NOI18N
        SignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignOutActionPerformed(evt);
            }
        });
        FormInput.add(SignOut);
        SignOut.setBounds(350, 1920, 200, 20);

        TTVPost.setBackground(new java.awt.Color(255, 255, 255));
        TTVPost.setText("Observasi TTV");
        TTVPost.setName("TTVPost"); // NOI18N
        TTVPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TTVPostActionPerformed(evt);
            }
        });
        FormInput.add(TTVPost);
        TTVPost.setBounds(350, 1720, 200, 20);

        PosisiNyamanPost.setBackground(new java.awt.Color(255, 255, 255));
        PosisiNyamanPost.setText("Berikan posisi yang nyaman");
        PosisiNyamanPost.setName("PosisiNyamanPost"); // NOI18N
        PosisiNyamanPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PosisiNyamanPostActionPerformed(evt);
            }
        });
        FormInput.add(PosisiNyamanPost);
        PosisiNyamanPost.setBounds(350, 1740, 250, 20);

        label157.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label157.setText("Anastesi dan atau perawat (PACU)");
        label157.setName("label157"); // NOI18N
        label157.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label157);
        label157.setBounds(370, 1820, 250, 20);

        label158.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label158.setText("(pasien plus)");
        label158.setName("label158"); // NOI18N
        label158.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label158);
        label158.setBounds(350, 1850, 260, 20);

        label159.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label159.setText("(pasien lokal)");
        label159.setName("label159"); // NOI18N
        label159.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label159);
        label159.setBounds(350, 1900, 260, 20);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        PerencanaanLainnyaPost.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PerencanaanLainnyaPost.setColumns(20);
        PerencanaanLainnyaPost.setRows(5);
        PerencanaanLainnyaPost.setName("PerencanaanLainnyaPost"); // NOI18N
        PerencanaanLainnyaPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerencanaanLainnyaPostKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(PerencanaanLainnyaPost);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(350, 1946, 240, 80);

        label160.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label160.setText("TD :");
        label160.setName("label160"); // NOI18N
        label160.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label160);
        label160.setBounds(630, 1920, 30, 20);

        label161.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label161.setText("P :");
        label161.setName("label161"); // NOI18N
        label161.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label161);
        label161.setBounds(630, 1980, 20, 20);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        SPost.setBorder(javax.swing.BorderFactory.createTitledBorder("S"));
        SPost.setColumns(20);
        SPost.setRows(5);
        SPost.setName("SPost"); // NOI18N
        SPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPostKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(SPost);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(630, 1720, 470, 50);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        OPost.setBorder(javax.swing.BorderFactory.createTitledBorder("O"));
        OPost.setColumns(20);
        OPost.setRows(5);
        OPost.setName("OPost"); // NOI18N
        OPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OPostKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(OPost);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(630, 1780, 470, 130);

        TDPost.setFocusTraversalPolicyProvider(true);
        TDPost.setName("TDPost"); // NOI18N
        TDPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TDPostActionPerformed(evt);
            }
        });
        TDPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDPostKeyPressed(evt);
            }
        });
        FormInput.add(TDPost);
        TDPost.setBounds(660, 1920, 80, 23);

        label162.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label162.setText(" mmHg");
        label162.setName("label162"); // NOI18N
        label162.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label162);
        label162.setBounds(740, 1920, 40, 20);

        label163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label163.setText("N :");
        label163.setName("label163"); // NOI18N
        label163.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label163);
        label163.setBounds(790, 1920, 20, 20);

        NPost.setFocusTraversalPolicyProvider(true);
        NPost.setName("NPost"); // NOI18N
        NPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NPostActionPerformed(evt);
            }
        });
        NPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NPostKeyPressed(evt);
            }
        });
        FormInput.add(NPost);
        NPost.setBounds(810, 1920, 80, 23);

        label164.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label164.setText(" x/mnt");
        label164.setName("label164"); // NOI18N
        label164.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label164);
        label164.setBounds(890, 1920, 40, 20);

        label165.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label165.setText("R :");
        label165.setName("label165"); // NOI18N
        label165.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label165);
        label165.setBounds(940, 1920, 20, 20);

        RPost.setFocusTraversalPolicyProvider(true);
        RPost.setName("RPost"); // NOI18N
        RPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RPostActionPerformed(evt);
            }
        });
        RPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPostKeyPressed(evt);
            }
        });
        FormInput.add(RPost);
        RPost.setBounds(960, 1920, 80, 23);

        label166.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label166.setText(" x/mnt");
        label166.setName("label166"); // NOI18N
        label166.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label166);
        label166.setBounds(1040, 1920, 40, 20);

        ABelumTercapaiPost.setBackground(new java.awt.Color(255, 255, 255));
        ABelumTercapaiPost.setText("Belum tercapai");
        ABelumTercapaiPost.setName("ABelumTercapaiPost"); // NOI18N
        ABelumTercapaiPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ABelumTercapaiPostActionPerformed(evt);
            }
        });
        FormInput.add(ABelumTercapaiPost);
        ABelumTercapaiPost.setBounds(910, 1950, 150, 20);

        ATercapaiSebagianPost.setBackground(new java.awt.Color(255, 255, 255));
        ATercapaiSebagianPost.setText("Tercapai sebagian");
        ATercapaiSebagianPost.setName("ATercapaiSebagianPost"); // NOI18N
        ATercapaiSebagianPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATercapaiSebagianPostActionPerformed(evt);
            }
        });
        FormInput.add(ATercapaiSebagianPost);
        ATercapaiSebagianPost.setBounds(750, 1950, 140, 20);

        ATercapaiPost.setBackground(new java.awt.Color(255, 255, 255));
        ATercapaiPost.setText("Tercapai");
        ATercapaiPost.setName("ATercapaiPost"); // NOI18N
        ATercapaiPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATercapaiPostActionPerformed(evt);
            }
        });
        FormInput.add(ATercapaiPost);
        ATercapaiPost.setBounds(650, 1950, 90, 20);

        label167.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label167.setText("A :");
        label167.setName("label167"); // NOI18N
        label167.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label167);
        label167.setBounds(630, 1950, 20, 20);

        PPost.setBackground(new java.awt.Color(255, 255, 255));
        PPost.setText("-");
        PPost.setName("PPost"); // NOI18N
        PPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPostActionPerformed(evt);
            }
        });
        FormInput.add(PPost);
        PPost.setBounds(650, 1980, 50, 20);

        PPertahankanPost.setBackground(new java.awt.Color(255, 255, 255));
        PPertahankanPost.setText("Pertahankan");
        PPertahankanPost.setName("PPertahankanPost"); // NOI18N
        PPertahankanPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPertahankanPostActionPerformed(evt);
            }
        });
        FormInput.add(PPertahankanPost);
        PPertahankanPost.setBounds(700, 1980, 110, 20);

        label168.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label168.setText("Lanjutkan");
        label168.setName("label168"); // NOI18N
        label168.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label168);
        label168.setBounds(810, 1980, 60, 20);

        PLanjutkanPost.setFocusTraversalPolicyProvider(true);
        PLanjutkanPost.setName("PLanjutkanPost"); // NOI18N
        PLanjutkanPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLanjutkanPostActionPerformed(evt);
            }
        });
        PLanjutkanPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PLanjutkanPostKeyPressed(evt);
            }
        });
        FormInput.add(PLanjutkanPost);
        PLanjutkanPost.setBounds(860, 1980, 230, 23);

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(10, 1710, 1100, 330);

        label169.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label169.setText("GLASSGOW COMA SCALE");
        label169.setName("label169"); // NOI18N
        label169.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label169);
        label169.setBounds(20, 2070, 1100, 30);

        JamPengkajianGlow.setForeground(new java.awt.Color(50, 70, 50));
        JamPengkajianGlow.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-06-2025 15:08:14" }));
        JamPengkajianGlow.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        JamPengkajianGlow.setName("JamPengkajianGlow"); // NOI18N
        JamPengkajianGlow.setOpaque(false);
        JamPengkajianGlow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamPengkajianGlowKeyPressed(evt);
            }
        });
        FormInput.add(JamPengkajianGlow);
        JamPengkajianGlow.setBounds(160, 2090, 130, 20);

        label170.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label170.setText("JAM :");
        label170.setName("label170"); // NOI18N
        label170.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label170);
        label170.setBounds(120, 2090, 30, 20);

        label171.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label171.setText("PENGKAJIAN");
        label171.setName("label171"); // NOI18N
        label171.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label171);
        label171.setBounds(30, 2090, 90, 20);

        label172.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label172.setText("1. MEMBUKA MATA");
        label172.setName("label172"); // NOI18N
        label172.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label172);
        label172.setBounds(40, 2130, 260, 23);

        MembukaMata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Ada", "Pada Nyeri", "Pada Perintah", "Spontan" }));
        MembukaMata.setName("MembukaMata"); // NOI18N
        MembukaMata.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MembukaMataItemStateChanged(evt);
            }
        });
        MembukaMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MembukaMataKeyPressed(evt);
            }
        });
        FormInput.add(MembukaMata);
        MembukaMata.setBounds(60, 2160, 160, 23);

        ResponMotor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tanpa Respon", "Ekstensi", "Flexi Abnormal", "Flexi Menarik", "Pada Rangsangan", "Menurut Perintah" }));
        ResponMotor.setName("ResponMotor"); // NOI18N
        ResponMotor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ResponMotorItemStateChanged(evt);
            }
        });
        ResponMotor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResponMotorKeyPressed(evt);
            }
        });
        FormInput.add(ResponMotor);
        ResponMotor.setBounds(390, 2160, 160, 23);

        Respon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tanpa Respon", "Tanpa Arti", "Bicara Ngacau", "Orientasi Buruk", "Orientasi Baik" }));
        Respon.setName("Respon"); // NOI18N
        Respon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ResponItemStateChanged(evt);
            }
        });
        Respon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResponKeyPressed(evt);
            }
        });
        FormInput.add(Respon);
        Respon.setBounds(730, 2160, 160, 23);

        SkalaMembukaMata.setFocusTraversalPolicyProvider(true);
        SkalaMembukaMata.setName("SkalaMembukaMata"); // NOI18N
        SkalaMembukaMata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkalaMembukaMataActionPerformed(evt);
            }
        });
        SkalaMembukaMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaMembukaMataKeyPressed(evt);
            }
        });
        FormInput.add(SkalaMembukaMata);
        SkalaMembukaMata.setBounds(230, 2160, 60, 23);

        SkalaResponMotor.setFocusTraversalPolicyProvider(true);
        SkalaResponMotor.setName("SkalaResponMotor"); // NOI18N
        SkalaResponMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkalaResponMotorActionPerformed(evt);
            }
        });
        SkalaResponMotor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResponMotorKeyPressed(evt);
            }
        });
        FormInput.add(SkalaResponMotor);
        SkalaResponMotor.setBounds(560, 2160, 60, 23);

        SkalaRespon.setFocusTraversalPolicyProvider(true);
        SkalaRespon.setName("SkalaRespon"); // NOI18N
        SkalaRespon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkalaResponActionPerformed(evt);
            }
        });
        SkalaRespon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResponKeyPressed(evt);
            }
        });
        FormInput.add(SkalaRespon);
        SkalaRespon.setBounds(900, 2160, 60, 23);

        label173.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label173.setText("2. RESPON MOTOR");
        label173.setName("label173"); // NOI18N
        label173.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label173);
        label173.setBounds(370, 2130, 310, 23);

        label174.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label174.setText("3. RESPON");
        label174.setName("label174"); // NOI18N
        label174.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label174);
        label174.setBounds(710, 2130, 300, 23);

        label175.setText("Petugas :");
        label175.setName("label175"); // NOI18N
        label175.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label175);
        label175.setBounds(0, 2420, 90, 20);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(100, 2420, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(200, 2420, 310, 23);

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
        BtnDokter.setBounds(520, 2420, 28, 20);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(40, 2200, 390, 190);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Asuhan", internalFrame2);

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

        jLabel19.setText("Tgl.Pindah :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-06-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-06-2025" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(197, 23));
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

        TabRawat.addTab("Data Asuhan", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugasOperator.getText().trim().equals("")){
            Valid.textKosong(BtnDokterOperator,"Dokter Operator");
        }else if(NmPetugasAnastesi.getText().trim().equals("")){
            Valid.textKosong(BtnDokterOperator,"Petugas Yang Menerima");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpanpre();
                simpanintra();
                simpanpost();
                simpanglow();
            }else {
                if(akses.getkode().equals(KdPetugasAnastesi.getText())||akses.getkode().equals(KdPetugasOperator.getText())||akses.getkode().equals(KdPetugas.getText())){
                    simpanpre();
                simpanintra();
                simpanpost();
                simpanglow();
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnDokterAnastesi,BtnBatal);
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
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString())){
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugasOperator.getText().trim().equals("")){
            Valid.textKosong(BtnDokterOperator,"Petugas Yang Menyerahkan");
        }else if(NmPetugasAnastesi.getText().trim().equals("")){
            Valid.textKosong(BtnDokterOperator,"Petugas Yang Menerima");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    gantipre();
                    gantiintra();
                    gantipost();
                    gantiglow();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString())){
                    gantipre();
                    gantiintra();
                    gantipost();
                    gantiglow();
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                            "transfer_pasien_antar_ruang.tanggal_masuk,transfer_pasien_antar_ruang.tanggal_pindah,transfer_pasien_antar_ruang.asal_ruang,"+
                            "transfer_pasien_antar_ruang.ruang_selanjutnya,transfer_pasien_antar_ruang.diagnosa_utama,transfer_pasien_antar_ruang.diagnosa_sekunder,"+
                            "transfer_pasien_antar_ruang.indikasi_pindah_ruang,transfer_pasien_antar_ruang.keterangan_indikasi_pindah_ruang,"+
                            "transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan,transfer_pasien_antar_ruang.obat_yang_telah_diberikan,"+
                            "transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.peralatan_yang_menyertai,"+
                            "transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,"+
                            "transfer_pasien_antar_ruang.pasien_keluarga_menyetujui,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,"+
                            "transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer,transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.td_sebelum_transfer,transfer_pasien_antar_ruang.nadi_sebelum_transfer,transfer_pasien_antar_ruang.rr_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.suhu_sebelum_transfer,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer,transfer_pasien_antar_ruang.td_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nadi_sesudah_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer,transfer_pasien_antar_ruang.suhu_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan,transfer_pasien_antar_ruang.nip_menerima,"+
                            "petugasmenerima.nama as petugasmenerima "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join transfer_pasien_antar_ruang on reg_periksa.no_rawat=transfer_pasien_antar_ruang.no_rawat "+
                            "inner join petugas as petugasmenyerahkan on transfer_pasien_antar_ruang.nip_menyerahkan=petugasmenyerahkan.nip "+
                            "inner join petugas as petugasmenerima on transfer_pasien_antar_ruang.nip_menerima=petugasmenerima.nip where "+
                            "transfer_pasien_antar_ruang.tanggal_pindah between ? and ? order by transfer_pasien_antar_ruang.tanggal_pindah");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                            "transfer_pasien_antar_ruang.tanggal_masuk,transfer_pasien_antar_ruang.tanggal_pindah,transfer_pasien_antar_ruang.asal_ruang,"+
                            "transfer_pasien_antar_ruang.ruang_selanjutnya,transfer_pasien_antar_ruang.diagnosa_utama,transfer_pasien_antar_ruang.diagnosa_sekunder,"+
                            "transfer_pasien_antar_ruang.indikasi_pindah_ruang,transfer_pasien_antar_ruang.keterangan_indikasi_pindah_ruang,"+
                            "transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan,transfer_pasien_antar_ruang.obat_yang_telah_diberikan,"+
                            "transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.peralatan_yang_menyertai,"+
                            "transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,"+
                            "transfer_pasien_antar_ruang.pasien_keluarga_menyetujui,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,"+
                            "transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer,transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.td_sebelum_transfer,transfer_pasien_antar_ruang.nadi_sebelum_transfer,transfer_pasien_antar_ruang.rr_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.suhu_sebelum_transfer,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer,transfer_pasien_antar_ruang.td_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nadi_sesudah_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer,transfer_pasien_antar_ruang.suhu_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan,transfer_pasien_antar_ruang.nip_menerima,"+
                            "petugasmenerima.nama as petugasmenerima "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join transfer_pasien_antar_ruang on reg_periksa.no_rawat=transfer_pasien_antar_ruang.no_rawat "+
                            "inner join petugas as petugasmenyerahkan on transfer_pasien_antar_ruang.nip_menyerahkan=petugasmenyerahkan.nip "+
                            "inner join petugas as petugasmenerima on transfer_pasien_antar_ruang.nip_menerima=petugasmenerima.nip where "+
                            "transfer_pasien_antar_ruang.tanggal_pindah between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            "transfer_pasien_antar_ruang.nip_menyerahkan like ? or petugasmenyerahkan.nama like ?) order by transfer_pasien_antar_ruang.tanggal_pindah");
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
                    }
                    rs=ps.executeQuery();
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                           "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal Masuk</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal Pindah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Indikasi Pindah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Indikasi Pindah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asal Ruang Rawat / Poliklinik</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ruang Rawat Selanjutnya</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Metode Pemindahan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Utama</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Sekunder</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Prosedur Yang Sudah Dilakukan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Obat Yang Telah Diberikan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Penunjang Yang Sudah Dilakukan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Peralatan Yang Menyertai</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Peralatan Menyertai</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Menyetujui Pemindahan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Keluarga/Penanggung Jawab</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Umum SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu Sbt</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Utama Sebelum Transfer</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Umum StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu Stt</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Utama Setelah Transfer</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Menyerahkan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Yang Menyerahkan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Menerima</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Yang Menerima</b></td>"+
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
                               "<td valign='top'>"+rs.getString("tanggal_masuk")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal_pindah")+"</td>"+
                               "<td valign='top'>"+rs.getString("indikasi_pindah_ruang")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_indikasi_pindah_ruang")+"</td>"+
                               "<td valign='top'>"+rs.getString("asal_ruang")+"</td>"+
                               "<td valign='top'>"+rs.getString("ruang_selanjutnya")+"</td>"+
                               "<td valign='top'>"+rs.getString("metode_pemindahan_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa_utama")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa_sekunder")+"</td>"+
                               "<td valign='top'>"+rs.getString("prosedur_yang_sudah_dilakukan")+"</td>"+
                               "<td valign='top'>"+rs.getString("obat_yang_telah_diberikan")+"</td>"+
                               "<td valign='top'>"+rs.getString("pemeriksaan_penunjang_yang_dilakukan")+"</td>"+
                               "<td valign='top'>"+rs.getString("peralatan_yang_menyertai")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_peralatan_yang_menyertai")+"</td>"+
                               "<td valign='top'>"+rs.getString("pasien_keluarga_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("hubungan_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan_umum_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("td_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("keluhan_utama_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan_umum_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("td_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("keluhan_utama_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip_menyerahkan")+"</td>"+
                               "<td valign='top'>"+rs.getString("petugasmenyerahkan")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip_menerima")+"</td>"+
                               "<td valign='top'>"+rs.getString("petugasmenerima")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='4000' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("TransferPasienAntarRuang.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='4000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA TRANSFER PASIEN ANTAR RUANG<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
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

    private void BtnDokterAnastesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterAnastesiKeyPressed

    }//GEN-LAST:event_BtnDokterAnastesiKeyPressed

    private void BtnDokterAnastesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterAnastesiActionPerformed
        pilihan=1;
        petugasA.isCek();
        petugasA.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugasA.setLocationRelativeTo(internalFrame1);
        petugasA.setAlwaysOnTop(false);
        petugasA.setVisible(true);
    }//GEN-LAST:event_BtnDokterAnastesiActionPerformed

    private void BtnDokterOperatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterOperatorKeyPressed

    }//GEN-LAST:event_BtnDokterOperatorKeyPressed

    private void BtnDokterOperatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterOperatorActionPerformed
        pilihan=1;
        petugasO.isCek();
        petugasO.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugasO.setLocationRelativeTo(internalFrame1);
        petugasO.setAlwaysOnTop(false);
        petugasO.setVisible(true);
    }//GEN-LAST:event_BtnDokterOperatorActionPerformed

    private void Spo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Spo2KeyPressed

    }//GEN-LAST:event_Spo2KeyPressed

    private void MembukaMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MembukaMataKeyPressed

    }//GEN-LAST:event_MembukaMataKeyPressed

    private void TanggalPengkajianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPengkajianKeyPressed
        Valid.pindah2(evt,TanggalOperasi,MembukaMata);
    }//GEN-LAST:event_TanggalPengkajianKeyPressed

    private void TanggalOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalOperasiKeyPressed
        Valid.pindah2(evt,TNoRw,TanggalPengkajian);
    }//GEN-LAST:event_TanggalOperasiKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            //Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void OtotBantuNafasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OtotBantuNafasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OtotBantuNafasActionPerformed

    private void DeformitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeformitasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeformitasActionPerformed

    private void EmpisemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpisemaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmpisemaActionPerformed

    private void PengembanganDadaRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengembanganDadaRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengembanganDadaRActionPerformed

    private void PengembanganDadaLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengembanganDadaLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengembanganDadaLActionPerformed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RRKeyPressed

    private void NormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NormalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NormalActionPerformed

    private void Capillary1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Capillary1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Capillary1ActionPerformed

    private void Capillary2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Capillary2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Capillary2ActionPerformed

    private void Ket_PendarahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Ket_PendarahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Ket_PendarahanKeyPressed

    private void PendarahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PendarahanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendarahanActionPerformed

    private void Ket_PendarahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ket_PendarahanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Ket_PendarahanActionPerformed

    private void TensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TensiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TensiActionPerformed

    private void TensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TensiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TensiKeyPressed

    private void NdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NdActionPerformed

    private void NdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NdKeyPressed

    private void SuhuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuhuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuhuActionPerformed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuhuKeyPressed

    private void KulitDinginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KulitDinginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitDinginActionPerformed

    private void KulitLainnyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KulitLainnyaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitLainnyaActionPerformed

    private void KulitHangatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KulitHangatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitHangatActionPerformed

    private void ProduksiUrineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProduksiUrineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProduksiUrineActionPerformed

    private void ProduksiUrineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProduksiUrineKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProduksiUrineKeyPressed

    private void PenggunaanKateterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PenggunaanKateterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenggunaanKateterActionPerformed

    private void SkalaNyeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkalaNyeriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNyeriActionPerformed

    private void SkalaNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNyeriKeyPressed

    private void KemihLainnyaPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KemihLainnyaPreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KemihLainnyaPreActionPerformed

    private void KemihLainnyaPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KemihLainnyaPreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KemihLainnyaPreKeyPressed

    private void DeltriumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeltriumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeltriumActionPerformed

    private void StuperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StuperActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StuperActionPerformed

    private void ApatisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApatisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ApatisActionPerformed

    private void KomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KomaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KomaActionPerformed

    private void ComposMentisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComposMentisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComposMentisActionPerformed

    private void BbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BbActionPerformed

    private void BbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BbKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BbKeyPressed

    private void MualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MualActionPerformed

    private void PatahTulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PatahTulangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PatahTulangActionPerformed

    private void NangisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NangisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NangisActionPerformed

    private void GelisahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GelisahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GelisahActionPerformed

    private void DistensilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DistensilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DistensilActionPerformed

    private void MuntahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MuntahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MuntahActionPerformed

    private void TenangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TenangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TenangActionPerformed

    private void PuasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PuasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PuasaActionPerformed

    private void RegioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RegioActionPerformed

    private void RegioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RegioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RegioKeyPressed

    private void MentalLainnyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MentalLainnyaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MentalLainnyaActionPerformed

    private void MentalLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MentalLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MentalLainnyaKeyPressed

    private void MobilitasFisikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MobilitasFisikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MobilitasFisikActionPerformed

    private void IntegritasKulitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntegritasKulitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntegritasKulitActionPerformed

    private void KomunikasiVerbalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KomunikasiVerbalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KomunikasiVerbalActionPerformed

    private void TidakEfektifPolaNafasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TidakEfektifPolaNafasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TidakEfektifPolaNafasActionPerformed

    private void TidakEfektifKupingIndividuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TidakEfektifKupingIndividuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TidakEfektifKupingIndividuActionPerformed

    private void DefisiPengetahuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DefisiPengetahuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DefisiPengetahuanActionPerformed

    private void PotensialInfeksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PotensialInfeksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PotensialInfeksiActionPerformed

    private void NyeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NyeriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NyeriActionPerformed

    private void KecemasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KecemasanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KecemasanActionPerformed

    private void PertukaranGasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PertukaranGasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PertukaranGasActionPerformed

    private void DiagnosaLainnyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiagnosaLainnyaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaLainnyaActionPerformed

    private void DiagnosaLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaLainnyaKeyPressed

    private void KelengkapanDokumenPraOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KelengkapanDokumenPraOperasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KelengkapanDokumenPraOperasiActionPerformed

    private void LakukanOrientasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LakukanOrientasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LakukanOrientasiActionPerformed

    private void LakukanInteraksiSosialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LakukanInteraksiSosialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LakukanInteraksiSosialActionPerformed

    private void SignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SignInActionPerformed

    private void TTVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TTVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TTVActionPerformed

    private void PerencanaanLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerencanaanLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerencanaanLainnyaKeyPressed

    private void SKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SKeyPressed

    private void OKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OKeyPressed

    private void TDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDActionPerformed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDKeyPressed

    private void PLanjutkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLanjutkanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PLanjutkanActionPerformed

    private void PLanjutkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PLanjutkanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PLanjutkanKeyPressed

    private void RActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RActionPerformed

    private void RKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RKeyPressed

    private void ATercapaiSebagianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATercapaiSebagianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATercapaiSebagianActionPerformed

    private void ABelumTercapaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ABelumTercapaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ABelumTercapaiActionPerformed

    private void PActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PActionPerformed

    private void PPertahankanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPertahankanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PPertahankanActionPerformed

    private void ATercapaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATercapaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATercapaiActionPerformed

    private void NActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NActionPerformed

    private void NKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NKeyPressed

    private void TanggalPengkajianIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPengkajianIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPengkajianIntraKeyPressed

    private void AnastesiUmumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnastesiUmumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnastesiUmumActionPerformed

    private void AnastesiBlokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnastesiBlokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnastesiBlokActionPerformed

    private void AnastesiRegionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnastesiRegionalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnastesiRegionalActionPerformed

    private void AnastesiMulaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnastesiMulaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnastesiMulaiKeyPressed

    private void AnastesiKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnastesiKeluarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnastesiKeluarKeyPressed

    private void OperasiMulaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OperasiMulaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OperasiMulaiKeyPressed

    private void OperasiSelesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OperasiSelesaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OperasiSelesaiKeyPressed

    private void WaktuMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuMasukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_WaktuMasukKeyPressed

    private void WaktuKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuKeluarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_WaktuKeluarKeyPressed

    private void AsaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AsaActionPerformed

    private void AsaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AsaKeyPressed

    private void KulitPreOPUtuhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KulitPreOPUtuhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitPreOPUtuhActionPerformed

    private void LukaKotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LukaKotorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LukaKotorActionPerformed

    private void PersiapanKulitOlehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PersiapanKulitOlehActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PersiapanKulitOlehActionPerformed

    private void PersiapanKulitOlehKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PersiapanKulitOlehKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PersiapanKulitOlehKeyPressed

    private void LukaBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LukaBersihActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LukaBersihActionPerformed

    private void LukaTerkontaminasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LukaTerkontaminasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LukaTerkontaminasiActionPerformed

    private void PosisiLiteralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosisiLiteralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosisiLiteralActionPerformed

    private void PosisiSupinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosisiSupinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosisiSupinActionPerformed

    private void PosisiUthotomiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosisiUthotomiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosisiUthotomiActionPerformed

    private void PosisiLainnyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosisiLainnyaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosisiLainnyaActionPerformed

    private void PosisiLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PosisiLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosisiLainnyaKeyPressed

    private void PemasangElekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PemasangElekActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemasangElekActionPerformed

    private void PemasangElekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemasangElekKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemasangElekKeyPressed

    private void LetakGroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LetakGroundActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LetakGroundActionPerformed

    private void LetakGroundKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LetakGroundKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LetakGroundKeyPressed

    private void CoagulantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoagulantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CoagulantActionPerformed

    private void CoagulantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CoagulantKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CoagulantKeyPressed

    private void CuttingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CuttingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CuttingActionPerformed

    private void CuttingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CuttingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CuttingKeyPressed

    private void MesinSuctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MesinSuctionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MesinSuctionActionPerformed

    private void BlanketWarmerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlanketWarmerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BlanketWarmerActionPerformed

    private void JamMulaiTorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamMulaiTorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamMulaiTorKeyPressed

    private void JamSelesaiTorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamSelesaiTorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamSelesaiTorKeyPressed

    private void PemasangTorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PemasangTorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemasangTorActionPerformed

    private void PemasangTorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemasangTorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemasangTorKeyPressed

    private void GraftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GraftActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GraftActionPerformed

    private void LokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LokasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LokasiActionPerformed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LokasiKeyPressed

    private void PendarahanIntraOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PendarahanIntraOPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendarahanIntraOPActionPerformed

    private void PendarahanIntraOPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendarahanIntraOPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendarahanIntraOPKeyPressed

    private void ProdukDarahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdukDarahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProdukDarahActionPerformed

    private void JamMulaiDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamMulaiDarahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamMulaiDarahKeyPressed

    private void JamMulaiDarah1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamMulaiDarah1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamMulaiDarah1KeyPressed

    private void JamSelesaiDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamSelesaiDarahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamSelesaiDarahKeyPressed

    private void JamSelesaiDarah1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamSelesaiDarah1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamSelesaiDarah1KeyPressed

    private void RutinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RutinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RutinActionPerformed

    private void PotongBekuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PotongBekuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PotongBekuActionPerformed

    private void KulturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KulturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulturActionPerformed

    private void PenggunaanKateterIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PenggunaanKateterIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenggunaanKateterIntraActionPerformed

    private void ProduksiUrineIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProduksiUrineIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProduksiUrineIntraActionPerformed

    private void ProduksiUrineIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProduksiUrineIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProduksiUrineIntraKeyPressed

    private void KemihLainnyaIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KemihLainnyaIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KemihLainnyaIntraActionPerformed

    private void KemihLainnyaIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KemihLainnyaIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KemihLainnyaIntraKeyPressed

    private void NangisIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NangisIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NangisIntraActionPerformed

    private void TenangIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TenangIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TenangIntraActionPerformed

    private void GelisahIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GelisahIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GelisahIntraActionPerformed

    private void MentalLainnyaIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MentalLainnyaIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MentalLainnyaIntraActionPerformed

    private void MentalLainnyaIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MentalLainnyaIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MentalLainnyaIntraKeyPressed

    private void ResikoHipotermiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResikoHipotermiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResikoHipotermiActionPerformed

    private void KecemasanIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KecemasanIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KecemasanIntraActionPerformed

    private void IntegritasKulitIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntegritasKulitIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntegritasKulitIntraActionPerformed

    private void TidakEfektifPolaNafasIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TidakEfektifPolaNafasIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TidakEfektifPolaNafasIntraActionPerformed

    private void ResikoSyokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResikoSyokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResikoSyokActionPerformed

    private void ResikoInfeksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResikoInfeksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResikoInfeksiActionPerformed

    private void ResikoCideraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResikoCideraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResikoCideraActionPerformed

    private void DiagnosaLainnyaIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaLainnyaIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaLainnyaIntraKeyPressed

    private void SiapkanPasienDimejaOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SiapkanPasienDimejaOPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SiapkanPasienDimejaOPActionPerformed

    private void ObservasiTTVIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ObservasiTTVIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ObservasiTTVIntraActionPerformed

    private void AlatLinenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlatLinenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlatLinenActionPerformed

    private void ElektromedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ElektromedisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ElektromedisActionPerformed

    private void PosisiPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosisiPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosisiPasienActionPerformed

    private void PersiapanOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PersiapanOPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PersiapanOPActionPerformed

    private void PersiapanKulitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PersiapanKulitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PersiapanKulitActionPerformed

    private void TimeOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimeOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TimeOutActionPerformed

    private void SiapkanOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SiapkanOKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SiapkanOKActionPerformed

    private void SiapkanSelimutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SiapkanSelimutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SiapkanSelimutActionPerformed

    private void PerencanaanLainnyaIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerencanaanLainnyaIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerencanaanLainnyaIntraKeyPressed

    private void SIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SIntraKeyPressed

    private void OIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OIntraKeyPressed

    private void TDIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TDIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDIntraActionPerformed

    private void TDIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDIntraKeyPressed

    private void NIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIntraActionPerformed

    private void NIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIntraKeyPressed

    private void RIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RIntraActionPerformed

    private void RIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RIntraKeyPressed

    private void ABelumTercapaiIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ABelumTercapaiIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ABelumTercapaiIntraActionPerformed

    private void ATercapaiSebagianIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATercapaiSebagianIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATercapaiSebagianIntraActionPerformed

    private void ATercapaiIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATercapaiIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATercapaiIntraActionPerformed

    private void PIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PIntraActionPerformed

    private void PPertahankanIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPertahankanIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PPertahankanIntraActionPerformed

    private void PLanjutkanIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLanjutkanIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PLanjutkanIntraActionPerformed

    private void PLanjutkanIntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PLanjutkanIntraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PLanjutkanIntraKeyPressed

    private void JamPengkajianPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamPengkajianPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamPengkajianPostKeyPressed

    private void OtotBantuNafasPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OtotBantuNafasPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OtotBantuNafasPostActionPerformed

    private void DeformitasPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeformitasPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeformitasPostActionPerformed

    private void EmpisemaPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpisemaPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmpisemaPostActionPerformed

    private void PengembanganDadaRPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengembanganDadaRPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengembanganDadaRPostActionPerformed

    private void PengembanganDadaLPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengembanganDadaLPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengembanganDadaLPostActionPerformed

    private void RRPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RRPostKeyPressed

    private void Spo2PostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Spo2PostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Spo2PostKeyPressed

    private void NormalPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NormalPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NormalPostActionPerformed

    private void Capillary1PostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Capillary1PostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Capillary1PostActionPerformed

    private void Capillary2PostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Capillary2PostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Capillary2PostActionPerformed

    private void Ket_PendarahanPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ket_PendarahanPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Ket_PendarahanPostActionPerformed

    private void Ket_PendarahanPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Ket_PendarahanPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Ket_PendarahanPostKeyPressed

    private void PendarahanPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PendarahanPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendarahanPostActionPerformed

    private void TensiPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TensiPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TensiPostActionPerformed

    private void TensiPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TensiPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TensiPostKeyPressed

    private void NdPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NdPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NdPostActionPerformed

    private void NdPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NdPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NdPostKeyPressed

    private void SuhuPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuhuPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuhuPostActionPerformed

    private void SuhuPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuhuPostKeyPressed

    private void KulitDinginPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KulitDinginPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitDinginPostActionPerformed

    private void KulitLainnyaPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KulitLainnyaPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitLainnyaPostActionPerformed

    private void KulitHangatPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KulitHangatPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitHangatPostActionPerformed

    private void ProduksiUrinePostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProduksiUrinePostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProduksiUrinePostActionPerformed

    private void ProduksiUrinePostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProduksiUrinePostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProduksiUrinePostKeyPressed

    private void SkalaNyeriPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkalaNyeriPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNyeriPostActionPerformed

    private void SkalaNyeriPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNyeriPostKeyPressed

    private void PenggunaanKateterPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PenggunaanKateterPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenggunaanKateterPostActionPerformed

    private void KemihLainnyaPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KemihLainnyaPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KemihLainnyaPostActionPerformed

    private void KemihLainnyaPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KemihLainnyaPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KemihLainnyaPostKeyPressed

    private void DeltriumPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeltriumPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeltriumPostActionPerformed

    private void StuperPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StuperPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StuperPostActionPerformed

    private void ApatisPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApatisPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ApatisPostActionPerformed

    private void KomaPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KomaPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KomaPostActionPerformed

    private void ComposMentisPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComposMentisPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComposMentisPostActionPerformed

    private void BbPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BbPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BbPostActionPerformed

    private void BbPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BbPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BbPostKeyPressed

    private void MualPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MualPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MualPostActionPerformed

    private void CideraKulitPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CideraKulitPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CideraKulitPostActionPerformed

    private void YaPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YaPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YaPostActionPerformed

    private void TidakPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TidakPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TidakPostActionPerformed

    private void DistensilPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DistensilPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DistensilPostActionPerformed

    private void MuntahPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MuntahPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MuntahPostActionPerformed

    private void DrainPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DrainPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DrainPostActionPerformed

    private void PuasaPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PuasaPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PuasaPostActionPerformed

    private void JenisBalutanPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JenisBalutanPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisBalutanPostActionPerformed

    private void JenisBalutanPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisBalutanPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisBalutanPostKeyPressed

    private void ProduksiDrainPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProduksiDrainPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProduksiDrainPostActionPerformed

    private void ProduksiDrainPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProduksiDrainPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProduksiDrainPostKeyPressed

    private void PemasanganAlatPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PemasanganAlatPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemasanganAlatPostActionPerformed

    private void LokasiPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LokasiPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LokasiPostActionPerformed

    private void LokasiPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LokasiPostKeyPressed

    private void JenisPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JenisPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisPostActionPerformed

    private void JenisPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisPostKeyPressed

    private void GCSPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GCSPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GCSPostActionPerformed

    private void GCSPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GCSPostKeyPressed

    private void RestiHypotermiPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestiHypotermiPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RestiHypotermiPostActionPerformed

    private void ResikoCideraPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResikoCideraPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResikoCideraPostActionPerformed

    private void RestiPendarahanPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestiPendarahanPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RestiPendarahanPostActionPerformed

    private void RestiSyokPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestiSyokPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RestiSyokPostActionPerformed

    private void MobilitasFisikPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MobilitasFisikPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MobilitasFisikPostActionPerformed

    private void PotensiInfeksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PotensiInfeksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PotensiInfeksiActionPerformed

    private void PerluasanInfeksiPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerluasanInfeksiPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerluasanInfeksiPostActionPerformed

    private void IntegritasKulitPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntegritasKulitPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntegritasKulitPostActionPerformed

    private void RestiNyeriPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestiNyeriPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RestiNyeriPostActionPerformed

    private void RestiInefektifPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestiInefektifPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RestiInefektifPostActionPerformed

    private void DiagnosaLainnyaPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaLainnyaPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaLainnyaPostKeyPressed

    private void SelimutHangatPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelimutHangatPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelimutHangatPostActionPerformed

    private void TerapiOksigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerapiOksigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TerapiOksigenActionPerformed

    private void SerahTerimaPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SerahTerimaPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SerahTerimaPostActionPerformed

    private void BerikanPasienPadaKeluargaPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BerikanPasienPadaKeluargaPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BerikanPasienPadaKeluargaPostActionPerformed

    private void SignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SignOutActionPerformed

    private void TTVPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TTVPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TTVPostActionPerformed

    private void PosisiNyamanPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosisiNyamanPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosisiNyamanPostActionPerformed

    private void PerencanaanLainnyaPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerencanaanLainnyaPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerencanaanLainnyaPostKeyPressed

    private void SPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SPostKeyPressed

    private void OPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OPostKeyPressed

    private void TDPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TDPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDPostActionPerformed

    private void TDPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDPostKeyPressed

    private void NPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NPostActionPerformed

    private void NPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NPostKeyPressed

    private void RPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RPostActionPerformed

    private void RPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RPostKeyPressed

    private void ABelumTercapaiPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ABelumTercapaiPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ABelumTercapaiPostActionPerformed

    private void ATercapaiSebagianPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATercapaiSebagianPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATercapaiSebagianPostActionPerformed

    private void ATercapaiPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATercapaiPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATercapaiPostActionPerformed

    private void PPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PPostActionPerformed

    private void PPertahankanPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPertahankanPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PPertahankanPostActionPerformed

    private void PLanjutkanPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLanjutkanPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PLanjutkanPostActionPerformed

    private void PLanjutkanPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PLanjutkanPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PLanjutkanPostKeyPressed

    private void JamPengkajianGlowKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamPengkajianGlowKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamPengkajianGlowKeyPressed

    private void ResponMotorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResponMotorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResponMotorKeyPressed

    private void ResponKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResponKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResponKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        pilihan=1;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TanggalPengkajianIntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalPengkajianIntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPengkajianIntraActionPerformed

    private void SkalaMembukaMataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkalaMembukaMataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaMembukaMataActionPerformed

    private void SkalaMembukaMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaMembukaMataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaMembukaMataKeyPressed

    private void SkalaResponMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkalaResponMotorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaResponMotorActionPerformed

    private void SkalaResponMotorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResponMotorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaResponMotorKeyPressed

    private void SkalaResponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkalaResponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaResponActionPerformed

    private void SkalaResponKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResponKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaResponKeyPressed

    private void MembukaMataItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MembukaMataItemStateChanged
        isCombo1();
    }//GEN-LAST:event_MembukaMataItemStateChanged

    private void ResponMotorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ResponMotorItemStateChanged
        isCombo1();
    }//GEN-LAST:event_ResponMotorItemStateChanged

    private void ResponItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ResponItemStateChanged
        isCombo1();
    }//GEN-LAST:event_ResponItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMTransferPasienAntarRuang dialog = new RMTransferPasienAntarRuang(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox ABelumTercapai;
    private javax.swing.JCheckBox ABelumTercapaiIntra;
    private javax.swing.JCheckBox ABelumTercapaiPost;
    private javax.swing.JCheckBox ATercapai;
    private javax.swing.JCheckBox ATercapaiIntra;
    private javax.swing.JCheckBox ATercapaiPost;
    private javax.swing.JCheckBox ATercapaiSebagian;
    private javax.swing.JCheckBox ATercapaiSebagianIntra;
    private javax.swing.JCheckBox ATercapaiSebagianPost;
    private javax.swing.JCheckBox AlatLinen;
    private javax.swing.JCheckBox AnastesiBlok;
    private widget.Tanggal AnastesiKeluar;
    private widget.Tanggal AnastesiMulai;
    private javax.swing.JCheckBox AnastesiRegional;
    private javax.swing.JCheckBox AnastesiUmum;
    private javax.swing.JCheckBox Apatis;
    private javax.swing.JCheckBox ApatisPost;
    private widget.TextBox Asa;
    private widget.TextBox Bb;
    private widget.TextBox BbPost;
    private javax.swing.JCheckBox BerikanPasienPadaKeluargaPost;
    private javax.swing.JCheckBox BlanketWarmer;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokterAnastesi;
    private widget.Button BtnDokterOperator;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private javax.swing.JCheckBox Capillary1;
    private javax.swing.JCheckBox Capillary1Post;
    private javax.swing.JCheckBox Capillary2;
    private javax.swing.JCheckBox Capillary2Post;
    private javax.swing.JCheckBox CideraKulitPost;
    private widget.TextBox Coagulant;
    private javax.swing.JCheckBox ComposMentis;
    private javax.swing.JCheckBox ComposMentisPost;
    private widget.TextBox Cutting;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JCheckBox DefisiPengetahuan;
    private javax.swing.JCheckBox Deformitas;
    private javax.swing.JCheckBox DeformitasPost;
    private javax.swing.JCheckBox Deltrium;
    private javax.swing.JCheckBox DeltriumPost;
    private widget.TextBox DiagnosaLainnya;
    private widget.TextArea DiagnosaLainnyaIntra;
    private widget.TextArea DiagnosaLainnyaPost;
    private javax.swing.JCheckBox Distensil;
    private javax.swing.JCheckBox DistensilPost;
    private javax.swing.JCheckBox DrainPost;
    private javax.swing.JCheckBox Elektromedis;
    private javax.swing.JCheckBox Empisema;
    private javax.swing.JCheckBox EmpisemaPost;
    private widget.PanelBiasa FormInput;
    private widget.TextBox GCSPost;
    private javax.swing.JCheckBox Gelisah;
    private javax.swing.JCheckBox GelisahIntra;
    private javax.swing.JCheckBox Graft;
    private javax.swing.JCheckBox IntegritasKulit;
    private javax.swing.JCheckBox IntegritasKulitIntra;
    private javax.swing.JCheckBox IntegritasKulitPost;
    private widget.Tanggal JamMulaiDarah;
    private widget.Tanggal JamMulaiDarah1;
    private widget.Tanggal JamMulaiTor;
    private widget.Tanggal JamPengkajianGlow;
    private widget.Tanggal JamPengkajianPost;
    private widget.Tanggal JamSelesaiDarah;
    private widget.Tanggal JamSelesaiDarah1;
    private widget.Tanggal JamSelesaiTor;
    private widget.TextBox JenisBalutanPost;
    private widget.TextBox JenisPost;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugasAnastesi;
    private widget.TextBox KdPetugasOperator;
    private javax.swing.JCheckBox Kecemasan;
    private javax.swing.JCheckBox KecemasanIntra;
    private javax.swing.JCheckBox KelengkapanDokumenPraOperasi;
    private widget.TextBox KemihLainnyaIntra;
    private widget.TextBox KemihLainnyaPost;
    private widget.TextBox KemihLainnyaPre;
    private widget.TextBox Ket_Pendarahan;
    private widget.TextBox Ket_PendarahanPost;
    private javax.swing.JCheckBox Koma;
    private javax.swing.JCheckBox KomaPost;
    private javax.swing.JCheckBox KomunikasiVerbal;
    private javax.swing.JCheckBox KulitDingin;
    private javax.swing.JCheckBox KulitDinginPost;
    private javax.swing.JCheckBox KulitHangat;
    private javax.swing.JCheckBox KulitHangatPost;
    private javax.swing.JCheckBox KulitLainnya;
    private javax.swing.JCheckBox KulitLainnyaPost;
    private javax.swing.JCheckBox KulitPreOPUtuh;
    private javax.swing.JCheckBox Kultur;
    private widget.Label LCount;
    private javax.swing.JCheckBox LakukanInteraksiSosial;
    private javax.swing.JCheckBox LakukanOrientasi;
    private widget.TextBox LetakGround;
    private widget.editorpane LoadHTML;
    private widget.TextBox Lokasi;
    private widget.TextBox LokasiPost;
    private javax.swing.JCheckBox LukaBersih;
    private javax.swing.JCheckBox LukaKotor;
    private javax.swing.JCheckBox LukaTerkontaminasi;
    private widget.ComboBox MembukaMata;
    private widget.TextBox MentalLainnya;
    private widget.TextBox MentalLainnyaIntra;
    private javax.swing.JCheckBox MesinSuction;
    private javax.swing.JCheckBox MobilitasFisik;
    private javax.swing.JCheckBox MobilitasFisikPost;
    private javax.swing.JCheckBox Mual;
    private javax.swing.JCheckBox MualPost;
    private javax.swing.JCheckBox Muntah;
    private javax.swing.JCheckBox MuntahPost;
    private widget.TextBox N;
    private widget.TextBox NIntra;
    private widget.TextBox NPost;
    private javax.swing.JCheckBox Nangis;
    private javax.swing.JCheckBox NangisIntra;
    private widget.TextBox Nd;
    private widget.TextBox NdPost;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugasAnastesi;
    private widget.TextBox NmPetugasOperator;
    private javax.swing.JCheckBox Normal;
    private javax.swing.JCheckBox NormalPost;
    private javax.swing.JCheckBox Nyeri;
    private widget.TextArea O;
    private widget.TextArea OIntra;
    private widget.TextArea OPost;
    private javax.swing.JCheckBox ObservasiTTVIntra;
    private widget.Tanggal OperasiMulai;
    private widget.Tanggal OperasiSelesai;
    private javax.swing.JCheckBox OtotBantuNafas;
    private javax.swing.JCheckBox OtotBantuNafasPost;
    private javax.swing.JCheckBox P;
    private javax.swing.JCheckBox PIntra;
    private widget.TextBox PLanjutkan;
    private widget.TextBox PLanjutkanIntra;
    private widget.TextBox PLanjutkanPost;
    private javax.swing.JCheckBox PPertahankan;
    private javax.swing.JCheckBox PPertahankanIntra;
    private javax.swing.JCheckBox PPertahankanPost;
    private javax.swing.JCheckBox PPost;
    private usu.widget.glass.PanelGlass PanelWall;
    private javax.swing.JCheckBox PatahTulang;
    private widget.TextBox PemasangElek;
    private widget.TextBox PemasangTor;
    private javax.swing.JCheckBox PemasanganAlatPost;
    private javax.swing.JCheckBox Pendarahan;
    private widget.TextBox PendarahanIntraOP;
    private javax.swing.JCheckBox PendarahanPost;
    private javax.swing.JCheckBox PengembanganDadaL;
    private javax.swing.JCheckBox PengembanganDadaLPost;
    private javax.swing.JCheckBox PengembanganDadaR;
    private javax.swing.JCheckBox PengembanganDadaRPost;
    private javax.swing.JCheckBox PenggunaanKateter;
    private javax.swing.JCheckBox PenggunaanKateterIntra;
    private javax.swing.JCheckBox PenggunaanKateterPost;
    private widget.TextArea PerencanaanLainnya;
    private widget.TextArea PerencanaanLainnyaIntra;
    private widget.TextArea PerencanaanLainnyaPost;
    private javax.swing.JCheckBox PerluasanInfeksiPost;
    private javax.swing.JCheckBox PersiapanKulit;
    private widget.TextBox PersiapanKulitOleh;
    private javax.swing.JCheckBox PersiapanOP;
    private javax.swing.JCheckBox PertukaranGas;
    private widget.TextBox PosisiLainnya;
    private javax.swing.JCheckBox PosisiLiteral;
    private javax.swing.JCheckBox PosisiNyamanPost;
    private javax.swing.JCheckBox PosisiPasien;
    private javax.swing.JCheckBox PosisiSupin;
    private javax.swing.JCheckBox PosisiUthotomi;
    private javax.swing.JCheckBox PotensiInfeksi;
    private javax.swing.JCheckBox PotensialInfeksi;
    private javax.swing.JCheckBox PotongBeku;
    private javax.swing.JCheckBox ProdukDarah;
    private widget.TextBox ProduksiDrainPost;
    private widget.TextBox ProduksiUrine;
    private widget.TextBox ProduksiUrineIntra;
    private widget.TextBox ProduksiUrinePost;
    private javax.swing.JCheckBox Puasa;
    private javax.swing.JCheckBox PuasaPost;
    private widget.TextBox R;
    private widget.TextBox RIntra;
    private widget.TextBox RPost;
    private widget.TextBox RR;
    private widget.TextBox RRPost;
    private widget.TextBox Regio;
    private javax.swing.JCheckBox ResikoCidera;
    private javax.swing.JCheckBox ResikoCideraPost;
    private javax.swing.JCheckBox ResikoHipotermi;
    private javax.swing.JCheckBox ResikoInfeksi;
    private javax.swing.JCheckBox ResikoSyok;
    private widget.ComboBox Respon;
    private widget.ComboBox ResponMotor;
    private javax.swing.JCheckBox RestiHypotermiPost;
    private javax.swing.JCheckBox RestiInefektifPost;
    private javax.swing.JCheckBox RestiNyeriPost;
    private javax.swing.JCheckBox RestiPendarahanPost;
    private javax.swing.JCheckBox RestiSyokPost;
    private javax.swing.JCheckBox Rutin;
    private widget.TextArea S;
    private widget.TextArea SIntra;
    private widget.TextArea SPost;
    private widget.ScrollPane Scroll;
    private javax.swing.JCheckBox SelimutHangatPost;
    private javax.swing.JCheckBox SerahTerimaPost;
    private javax.swing.JCheckBox SiapkanOK;
    private javax.swing.JCheckBox SiapkanPasienDimejaOP;
    private javax.swing.JCheckBox SiapkanSelimut;
    private javax.swing.JCheckBox SignIn;
    private javax.swing.JCheckBox SignOut;
    private widget.TextBox SkalaMembukaMata;
    private widget.TextBox SkalaNyeri;
    private widget.TextBox SkalaNyeriPost;
    private widget.TextBox SkalaRespon;
    private widget.TextBox SkalaResponMotor;
    private widget.TextBox Spo2;
    private widget.TextBox Spo2Post;
    private javax.swing.JCheckBox Stuper;
    private javax.swing.JCheckBox StuperPost;
    private widget.TextBox Suhu;
    private widget.TextBox SuhuPost;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TDIntra;
    private widget.TextBox TDPost;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JCheckBox TTV;
    private javax.swing.JCheckBox TTVPost;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalOperasi;
    private widget.Tanggal TanggalPengkajian;
    private widget.Tanggal TanggalPengkajianIntra;
    private javax.swing.JCheckBox Tenang;
    private javax.swing.JCheckBox TenangIntra;
    private widget.TextBox Tensi;
    private widget.TextBox TensiPost;
    private javax.swing.JCheckBox TerapiOksigen;
    private widget.TextBox TglLahir;
    private javax.swing.JCheckBox TidakEfektifKupingIndividu;
    private javax.swing.JCheckBox TidakEfektifPolaNafas;
    private javax.swing.JCheckBox TidakEfektifPolaNafasIntra;
    private javax.swing.JCheckBox TidakPost;
    private javax.swing.JCheckBox TimeOut;
    private widget.Tanggal WaktuKeluar;
    private widget.Tanggal WaktuMasuk;
    private javax.swing.JCheckBox YaPost;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JSeparator jSeparator;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private widget.Label label100;
    private widget.Label label101;
    private widget.Label label102;
    private widget.Label label103;
    private widget.Label label104;
    private widget.Label label105;
    private widget.Label label106;
    private widget.Label label107;
    private widget.Label label108;
    private widget.Label label109;
    private widget.Label label110;
    private widget.Label label111;
    private widget.Label label112;
    private widget.Label label113;
    private widget.Label label114;
    private widget.Label label115;
    private widget.Label label116;
    private widget.Label label117;
    private widget.Label label118;
    private widget.Label label119;
    private widget.Label label120;
    private widget.Label label121;
    private widget.Label label122;
    private widget.Label label123;
    private widget.Label label124;
    private widget.Label label125;
    private widget.Label label126;
    private widget.Label label127;
    private widget.Label label128;
    private widget.Label label129;
    private widget.Label label130;
    private widget.Label label131;
    private widget.Label label132;
    private widget.Label label133;
    private widget.Label label134;
    private widget.Label label135;
    private widget.Label label136;
    private widget.Label label137;
    private widget.Label label138;
    private widget.Label label139;
    private widget.Label label14;
    private widget.Label label140;
    private widget.Label label141;
    private widget.Label label142;
    private widget.Label label143;
    private widget.Label label144;
    private widget.Label label145;
    private widget.Label label146;
    private widget.Label label147;
    private widget.Label label148;
    private widget.Label label149;
    private widget.Label label15;
    private widget.Label label150;
    private widget.Label label151;
    private widget.Label label152;
    private widget.Label label153;
    private widget.Label label154;
    private widget.Label label155;
    private widget.Label label156;
    private widget.Label label157;
    private widget.Label label158;
    private widget.Label label159;
    private widget.Label label16;
    private widget.Label label160;
    private widget.Label label161;
    private widget.Label label162;
    private widget.Label label163;
    private widget.Label label164;
    private widget.Label label165;
    private widget.Label label166;
    private widget.Label label167;
    private widget.Label label168;
    private widget.Label label169;
    private widget.Label label17;
    private widget.Label label170;
    private widget.Label label171;
    private widget.Label label172;
    private widget.Label label173;
    private widget.Label label174;
    private widget.Label label175;
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
    private widget.Label label44;
    private widget.Label label45;
    private widget.Label label46;
    private widget.Label label47;
    private widget.Label label48;
    private widget.Label label49;
    private widget.Label label50;
    private widget.Label label51;
    private widget.Label label52;
    private widget.Label label53;
    private widget.Label label54;
    private widget.Label label55;
    private widget.Label label56;
    private widget.Label label57;
    private widget.Label label58;
    private widget.Label label59;
    private widget.Label label60;
    private widget.Label label61;
    private widget.Label label62;
    private widget.Label label63;
    private widget.Label label64;
    private widget.Label label65;
    private widget.Label label66;
    private widget.Label label67;
    private widget.Label label68;
    private widget.Label label69;
    private widget.Label label70;
    private widget.Label label71;
    private widget.Label label72;
    private widget.Label label73;
    private widget.Label label74;
    private widget.Label label75;
    private widget.Label label76;
    private widget.Label label77;
    private widget.Label label78;
    private widget.Label label79;
    private widget.Label label80;
    private widget.Label label81;
    private widget.Label label82;
    private widget.Label label83;
    private widget.Label label84;
    private widget.Label label85;
    private widget.Label label86;
    private widget.Label label87;
    private widget.Label label88;
    private widget.Label label89;
    private widget.Label label90;
    private widget.Label label91;
    private widget.Label label92;
    private widget.Label label93;
    private widget.Label label94;
    private widget.Label label95;
    private widget.Label label96;
    private widget.Label label97;
    private widget.Label label98;
    private widget.Label label99;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "SELECT " +
                        "pasien.no_rkm_medis, " +
                        "pasien.nm_pasien, " +
                        "pasien.tgl_lahir, " +
                        "pasien.jk, "+
                        "rencana_asuhan_keperawatan_operasi_pre.*, " +
                        "rencana_asuhan_keperawatan_operasi_intra.*, " +
                        "rencana_asuhan_keperawatan_operasi_post.*, " +
                        "rencana_asuhan_keperawatan_operasi_glassgow.*, " +
                        "a.nama as operator, " +
                        "b.nama as anastesi, " +
                        "c.nama as petugas " +
                        "FROM reg_periksa " +
                        "LEFT JOIN pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis " +
                        "LEFT JOIN rencana_asuhan_keperawatan_operasi_pre on reg_periksa.no_rawat=rencana_asuhan_keperawatan_operasi_pre.no_rawat " +
                        "LEFT JOIN rencana_asuhan_keperawatan_operasi_intra on rencana_asuhan_keperawatan_operasi_intra.no_rawat=rencana_asuhan_keperawatan_operasi_pre.no_rawat " +
                        "LEFT JOIN rencana_asuhan_keperawatan_operasi_post on rencana_asuhan_keperawatan_operasi_post.no_rawat=rencana_asuhan_keperawatan_operasi_intra.no_rawat " +
                        "LEFT JOIN rencana_asuhan_keperawatan_operasi_glassgow on rencana_asuhan_keperawatan_operasi_glassgow.no_rawat=rencana_asuhan_keperawatan_operasi_post.no_rawat " +
                        "INNER JOIN pegawai a on a.nik=rencana_asuhan_keperawatan_operasi_pre.kd_dr_operator " +
                        "INNER JOIN pegawai b on b.nik=rencana_asuhan_keperawatan_operasi_pre.kd_dr_anastesi " +
                        "INNER JOIN pegawai c on c.nik=rencana_asuhan_keperawatan_operasi_pre.kd_petugas " +
                        "WHERE "+
                        "rencana_asuhan_keperawatan_operasi_pre.tanggal_masuk between ? and ? order by rencana_asuhan_keperawatan_operasi_pre.tanggal_masuk");
            }else{
                ps=koneksi.prepareStatement(
                        "SELECT " +
                        "pasien.no_rkm_medis, " +
                        "pasien.nm_pasien, " +
                        "pasien.tgl_lahir, " +
                        "pasien.jk, "+
                        "rencana_asuhan_keperawatan_operasi_pre.*, " +
                        "rencana_asuhan_keperawatan_operasi_intra.*, " +
                        "rencana_asuhan_keperawatan_operasi_post.*, " +
                        "rencana_asuhan_keperawatan_operasi_glassgow.*, " +
                        "a.nama as operator, " +
                        "b.nama as anastesi, " +
                        "c.nama as petugas " +
                        "FROM reg_periksa " +
                        "INNER JOIN pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis " +
                        "INNER JOIN rencana_asuhan_keperawatan_operasi_pre on reg_periksa.no_rawat=rencana_asuhan_keperawatan_operasi_pre.no_rawat " +
                        "LEFT JOIN rencana_asuhan_keperawatan_operasi_intra on rencana_asuhan_keperawatan_operasi_intra.no_rawat=rencana_asuhan_keperawatan_operasi_pre.no_rawat " +
                        "LEFT JOIN rencana_asuhan_keperawatan_operasi_post on rencana_asuhan_keperawatan_operasi_post.no_rawat=rencana_asuhan_keperawatan_operasi_intra.no_rawat " +
                        "LEFT JOIN rencana_asuhan_keperawatan_operasi_glassgow on rencana_asuhan_keperawatan_operasi_glassgow.no_rawat=rencana_asuhan_keperawatan_operasi_post.no_rawat " +
                        "INNER JOIN pegawai a on a.nik=rencana_asuhan_keperawatan_operasi_pre.kd_dr_operator " +
                        "INNER JOIN pegawai b on b.nik=rencana_asuhan_keperawatan_operasi_pre.kd_dr_anastesi " +
                        "INNER JOIN pegawai c on c.nik=rencana_asuhan_keperawatan_operasi_pre.kd_petugas " +
                        "WHERE "+
                        "rencana_asuhan_keperawatan_operasi_pre.tanggal_masuk between ? and ? and (rencana_asuhan_keperawatan_operasi_pre.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "rencana_asuhan_keperawatan_operasi_pre.kd_petugas like ? or a.nama like ?) order by rencana_asuhan_keperawatan_operasi_pre.tanggal_masuk");
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
                }   
                rs=ps.executeQuery();
                while(rs.next()){
//                    int columnCount = 0;
//                    String[] rowData = new String[columnCount];
//                        for (int i = 1; i <= columnCount; i++) {
//                            rowData[i - 1] = rs.getString(i);
//                        }
//                        tabMode.addRow(rowData);
                    
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal_masuk"),rs.getString("kd_dr_operator"),rs.getString("operator"),rs.getString("kd_dr_anastesi"),rs.getString("anastesi"),
                        rs.getString("kd_petugas"),rs.getString("petugas"),rs.getString("jam_pengkajian_pre"),rs.getString("otot_bantu_nafas"),rs.getString("deformitas"),rs.getString("empisema"),rs.getString("pengembangan_dada_r"),rs.getString("pengembangan_dada_l"),rs.getString("rr"),rs.getString("spo2"),
                        rs.getString("normal"),rs.getString("capilary1"),rs.getString("capilary2"),rs.getString("ket_perdarahan"),rs.getString("perdarahan"),rs.getString("tensi"),rs.getString("nd"),rs.getString("suhu"),rs.getString("kulit_hangat"),rs.getString("kulit_dingin"),
                        rs.getString("kulit_lainnya"),rs.getString("skala_nyeri"),rs.getString("penggunaan_kateter"),rs.getString("produksi_urine"),rs.getString("kemih_lainnya_pre"),rs.getString("compos_mentis"),rs.getString("deltrium"),rs.getString("stuper"),rs.getString("apatis"),rs.getString("koma"),
                        rs.getString("bb"),rs.getString("puasa"),rs.getString("mual"),rs.getString("distensil"),rs.getString("muntah"),rs.getString("patah_tulang"),rs.getString("regio"),rs.getString("tenang"),rs.getString("nangis"),rs.getString("gelisah"),
                        rs.getString("mental_lainnya"),rs.getString("kecemasan"),rs.getString("pertukaran_gas"),rs.getString("mobilitas_fisik"),rs.getString("integritas_kulit"),rs.getString("komunikasi_verbal"),rs.getString("tidak_efektif_pola_nafas"),rs.getString("tidak_efektif_kuping_individual"),rs.getString("defisi_pengetahuan"),rs.getString("potensial_infeksi"),
                        rs.getString("nyeri"),rs.getString("diagnosa_lainnya"),rs.getString("interaksi_sosial"),rs.getString("dokumen_pra_operasi"),rs.getString("orientasi"),rs.getString("ttv"),rs.getString("sign_in"),rs.getString("perencanaan_lainnya"),rs.getString("s"),rs.getString("o"),
                        rs.getString("td"),rs.getString("n"),rs.getString("r"),rs.getString("a_tercapai"),rs.getString("a_tercapai_sebagian"),rs.getString("a_belum"),rs.getString("p"),rs.getString("p_pertahankan"),rs.getString("p_lanjutkan"), rs.getString("Jam_pengkajian_intra"),
                        rs.getString("waktu_masuk"),rs.getString("waktu_keluar"),rs.getString("anastesi_mulai"),rs.getString("anastesi_selesai"),rs.getString("operasi_mulai"),rs.getString("operasi_selesai"),rs.getString("anastesi_umum"),rs.getString("anastesi_blok"),rs.getString("anastesi_regional"),rs.getString("asa"),
                        rs.getString("kulit_pre_op_utuh"),rs.getString("persiapan_kulit_oleh"),rs.getString("luka_bersih"),rs.getString("luka_kotor"),rs.getString("luka_terkontaminasi"),rs.getString("posisi_supin"),rs.getString("posisi_uthotomi"),rs.getString("posisi_lateral"),rs.getString("posisi_lainnya"),rs.getString("pemasang_elek"),rs.getString("letak_ground"),
                        rs.getString("coagulant"),rs.getString("cutting"),rs.getString("mesin_suction"),rs.getString("blanket_warmer"),rs.getString("jam_mulai_tor"),rs.getString("jam_selesai_tor"),rs.getString("pemasang_tor"),rs.getString("graft"),rs.getString("lokasi"),rs.getString("pendarahan_intra_op"),
                        rs.getString("produk_darah"),rs.getString("jam_mulai_darah"),rs.getString("jam_mulai_darah1"),rs.getString("jam_selesai_darah"),rs.getString("jam_selesai_darah1"),rs.getString("rutin"),rs.getString("potong_beku"),rs.getString("kultur"),rs.getString("penggunaan_kateter_intra"),rs.getString("produksi_urine_intra"),
                        rs.getString("kemih_lainnya_intra"),rs.getString("tenang_intra"),rs.getString("gelisah_intra"),rs.getString("nangis_intra"),rs.getString("mental_lainnya_intra"),rs.getString("resiko_infeksi"),rs.getString("resiko_cidera"),rs.getString("resiko_hipotermi"),rs.getString("kecemasan_intra"),rs.getString("integritas_kulit_intra"),
                        rs.getString("tidak_efektif_pola_nafas_intra"),rs.getString("resiko_syok"),rs.getString("diagnosa_lainnya_intra"),rs.getString("siapkan_ok"),rs.getString("siapkan_slimut"),rs.getString("siapkan_pasien_dimeja_op"),rs.getString("observasi_ttv_intra"),rs.getString("alat_linen"),rs.getString("elektromedis"),rs.getString("posisi_pasien"),
                        rs.getString("persiapan_op"),rs.getString("persiapan_kulit"),rs.getString("time_out"),rs.getString("perencanaan_lainnya_intra"),rs.getString("s_intra"),rs.getString("o_intra"),rs.getString("td_intra"),rs.getString("n_intra"),rs.getString("r_intra"),rs.getString("a_tercapai_intra"),
                        rs.getString("a_tercapai_sebagian_intra"),rs.getString("a_belum_intra"),rs.getString("p_intra"),rs.getString("p_pertahankan_intra"),rs.getString("P_lanjutkan_intra"),rs.getString("jam_pengkajian_post"),rs.getString("otot_bantu_nafas_post"),rs.getString("deformitas_post"),rs.getString("empisema_post"),rs.getString("pengembangan_dada_r_post"),
                        rs.getString("pengembangan_dada_l_post"),rs.getString("rr_post"),rs.getString("spo2_post"),rs.getString("normal_post"),rs.getString("capilary1_post"),rs.getString("capilary2_post"),rs.getString("ket_perdarahan_post"),rs.getString("perdarahan_post"),rs.getString("tensi_post"),rs.getString("nd_post"),
                        rs.getString("suhu_post"),rs.getString("kulit_hangat_post"),rs.getString("kulit_dingin_post"),rs.getString("kulit_lainnya_post"),rs.getString("skala_nyeri_post"),rs.getString("penggunaan_kateter_post"),rs.getString("produksi_urine_post"),rs.getString("kemih_lainnya_pre_post"),rs.getString("compos_mentis_post"),rs.getString("deltrium_post"),
                        rs.getString("stuper_post"),rs.getString("apatis_post"),rs.getString("koma_post"),rs.getString("bb_post"),rs.getString("puasa_post"),rs.getString("mual_post"),rs.getString("distensil_post"),rs.getString("muntah_post"),rs.getString("cidera_kulit_post"),rs.getString("jenis_balutan_post"),
                        rs.getString("drain_post"),rs.getString("ya_post"),rs.getString("tidak_post"),rs.getString("produksi_drain_post"),rs.getString("pemasangan_alat_post"),rs.getString("lokasi_post"),rs.getString("jenis_post"),rs.getString("resti_nyeri_post"),rs.getString("resti_inefektif_post"),
                        rs.getString("resti_hypotermi_post"),rs.getString("resti_cidera_post"),rs.getString("resti_pendarahan_post"),rs.getString("resti_syok_post"),rs.getString("intergritas_kulit_post"),rs.getString("mobilitas_fisik_post"),rs.getString("potensi_infeksi_post"),rs.getString("perluasan_infeksi_post"),rs.getString("diagnosa_lainnya_post"),rs.getString("ttv_post"),
                        rs.getString("posisi_nyaman_post"),rs.getString("selimut_hangat_post"),rs.getString("terapi_oksigen_post"),rs.getString("serah_terima_post"),rs.getString("berikan_pasien_pada_keluarga_post"),rs.getString("signout_post"),rs.getString("perencanaan_lainnya_post"),rs.getString("s_post"),rs.getString("o_post"),rs.getString("td_post"),
                        rs.getString("n_post"),rs.getString("r_post"),rs.getString("a_tercapai_post"),rs.getString("a_tercapai_sebagian_post"),rs.getString("a_belum_post"),rs.getString("p_post"),rs.getString("p_pertahankan_post"),rs.getString("p_lanjutkan_post"),rs.getString("jam_pengkajian_glow"),rs.getString("membuka_mata"),
                        rs.getString("scale_membuka_mata"),rs.getString("respon_motor"),rs.getString("scale_respon_motor"),rs.getString("respon"),rs.getString("scale_respon")
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
        TanggalOperasi.setDate(new Date());
        TanggalPengkajian.setDate(new Date());
        MembukaMata.setSelectedIndex(0);
        TabRawat.setSelectedIndex(0);
            KdPetugasOperator.setText("");
            NmPetugasOperator.setText("");
            KdPetugasAnastesi.setText("");
            NmPetugasAnastesi.setText("");
            
//            PRE
            OtotBantuNafas.setSelected(false);
            Deformitas.setSelected(false);
            Empisema.setSelected(false);
            PengembanganDadaR.setSelected(false);
            PengembanganDadaL.setSelected(false);
            RR.setText("");
            Spo2.setText("");
            Normal.setSelected(false);
            Capillary1.setSelected(false);
            Capillary2.setSelected(false);
            Ket_Pendarahan.setText("");
            Pendarahan.setSelected(false);
            Tensi.setText("");
            Nd.setText("");
            Suhu.setText("");
            KulitHangat.setSelected(false);
            KulitDingin.setSelected(false);
            KulitLainnya.setSelected(false);
            SkalaNyeri.setText("");
            PenggunaanKateter.setSelected(false);
            ProduksiUrine.setText("");
            KemihLainnyaPre.setText("");
            ComposMentis.setSelected(false);
            Deltrium.setSelected(false);
            Stuper.setSelected(false);
            Apatis.setSelected(false);
            Koma.setSelected(false);
            Bb.setText("");
            Puasa.setSelected(false);
            Mual.setSelected(false);
            Distensil.setSelected(false);
            Muntah.setSelected(false);
            PatahTulang.setSelected(false);
            Regio.setText("");
            Tenang.setSelected(false);
            Nangis.setSelected(false);
            Gelisah.setSelected(false);
            MentalLainnya.setText("");
            
            Kecemasan.setSelected(false);
            PertukaranGas.setSelected(false);
            MobilitasFisik.setSelected(false);
            IntegritasKulit.setSelected(false);
            KomunikasiVerbal.setSelected(false);
            TidakEfektifPolaNafas.setSelected(false);
            TidakEfektifKupingIndividu.setSelected(false);
            DefisiPengetahuan.setSelected(false);
            PotensialInfeksi.setSelected(false);
            Nyeri.setSelected(false);
            DiagnosaLainnya.setText("");
            LakukanInteraksiSosial.setSelected(false);
            KelengkapanDokumenPraOperasi.setSelected(false);
            LakukanOrientasi.setSelected(false);
            TTV.setSelected(false);
            SignIn.setSelected(false);
            PerencanaanLainnya.setText("");
            S.setText("");
            O.setText("");
            TD.setText("");
            N.setText("");
            R.setText("");
            ATercapai.setSelected(false);
            ATercapaiSebagian.setSelected(false);
            ABelumTercapai.setSelected(false);
            P.setSelected(false);
            PPertahankan.setSelected(false);
            PLanjutkan.setText("");
            
            
//            INTRA
            AnastesiUmum.setSelected(false);
            AnastesiBlok.setSelected(false);
            AnastesiRegional.setSelected(false);
            Asa.setText("");
            KulitPreOPUtuh.setSelected(false);
            PersiapanKulit.setText("");
            LukaBersih.setSelected(false);
            LukaKotor.setSelected(false);
            LukaTerkontaminasi.setSelected(false);
            PosisiSupin.setSelected(false);
            PosisiUthotomi.setSelected(false);
            PosisiLiteral.setSelected(false);
            PosisiLainnya.setText("");
            PemasangElek.setText("");
            LetakGround.setText("");
            Coagulant.setText("");
            Cutting.setText("");
            MesinSuction.setSelected(false);
            BlanketWarmer.setSelected(false);
            PemasangTor.setText("");
            Graft.setSelected(false);
            Lokasi.setText("");
            PendarahanIntraOP.setText("");
            ProdukDarah.setSelected(false);
            Rutin.setSelected(false);
            PotongBeku.setSelected(false);
            Kultur.setSelected(false);
            PenggunaanKateterIntra.setSelected(false);
            ProduksiUrineIntra.setText("");
            KemihLainnyaIntra.setText("");
            TenangIntra.setSelected(false);
            GelisahIntra.setSelected(false);
            NangisIntra.setSelected(false);
            MentalLainnyaIntra.setText("");
            
            ResikoInfeksi.setSelected(false);
            ResikoCidera.setSelected(false);
            ResikoHipotermi.setSelected(false);
            KecemasanIntra.setSelected(false);
            IntegritasKulitIntra.setSelected(false);
            TidakEfektifPolaNafasIntra.setSelected(false);
            ResikoSyok.setSelected(false);
            DiagnosaLainnyaIntra.setText("");
            SiapkanOK.setSelected(false);
            SiapkanSelimut.setSelected(false);
            SiapkanPasienDimejaOP.setSelected(false);
            ObservasiTTVIntra.setSelected(false);
            AlatLinen.setSelected(false);
            Elektromedis.setSelected(false);
            PosisiPasien.setSelected(false);
            PersiapanOP.setSelected(false);
            PersiapanKulit.setSelected(false);
            TimeOut.setSelected(false);
            PerencanaanLainnyaIntra.setText("");
            SIntra.setText("");
            OIntra.setText("");
            TDIntra.setText("");
            NIntra.setText("");
            RIntra.setText("");
            ATercapaiIntra.setSelected(false);
            ATercapaiSebagianIntra.setSelected(false);
            ABelumTercapaiIntra.setSelected(false);
            PIntra.setSelected(false);
            PPertahankanIntra.setSelected(false);
            PLanjutkanIntra.setText("");
            
            
//            POST
            OtotBantuNafasPost.setSelected(false);
            DeformitasPost.setSelected(false);
            EmpisemaPost.setSelected(false);
            PengembanganDadaRPost.setSelected(false);
            PengembanganDadaLPost.setSelected(false);
            RRPost.setText("");
            Spo2Post.setText("");
            NormalPost.setSelected(false);
            Capillary1Post.setSelected(false);
            Capillary2Post.setSelected(false);
            Ket_PendarahanPost.setText("");
            PendarahanPost.setSelected(false);
            TensiPost.setText("");
            NdPost.setText("");
            SuhuPost.setText("");
            KulitHangatPost.setSelected(false);
            KulitDinginPost.setSelected(false);
            KulitLainnyaPost.setSelected(false);
            SkalaNyeriPost.setText("");
            PenggunaanKateterPost.setSelected(false);
            ProduksiUrinePost.setText("");
            KemihLainnyaPost.setText("");
            ComposMentisPost.setSelected(false);
            DeltriumPost.setSelected(false);
            StuperPost.setSelected(false);
            ApatisPost.setSelected(false);
            KomaPost.setSelected(false);
            BbPost.setText("");
            PuasaPost.setSelected(false);
            MualPost.setSelected(false);
            DistensilPost.setSelected(false);
            MuntahPost.setSelected(false);
            CideraKulitPost.setSelected(false);
            JenisBalutanPost.setText("");
            DrainPost.setSelected(false);
            YaPost.setSelected(false);
            TidakPost.setSelected(false);
            ProduksiDrainPost.setText("");
            PemasanganAlatPost.setSelected(false);
            LokasiPost.setText("");
            JenisPost.setText("");
            
            RestiNyeriPost.setSelected(false);
            RestiInefektifPost.setSelected(false);
            RestiHypotermiPost.setSelected(false);
            ResikoCideraPost.setSelected(false);
            RestiPendarahanPost.setSelected(false);
            RestiSyokPost.setSelected(false);
            IntegritasKulitPost.setSelected(false);
            MobilitasFisikPost.setSelected(false);
            PotensiInfeksi.setSelected(false);
            PerluasanInfeksiPost.setSelected(false);
            DiagnosaLainnyaPost.setText("");
            TTVPost.setSelected(false);
            PosisiNyamanPost.setSelected(false);
            SelimutHangatPost.setSelected(false);
            TerapiOksigen.setSelected(false);
            SerahTerimaPost.setSelected(false);
            BerikanPasienPadaKeluargaPost.setSelected(false);
            SignOut.setSelected(false);
            PerencanaanLainnyaPost.setText(""); 
            SPost.setText("");
            OPost.setText("");
            TDPost.setText("");
            NPost.setText("");
            RPost.setText("");
            ATercapaiPost.setSelected(false);
            ATercapaiSebagianPost.setSelected(false);
            ABelumTercapaiPost.setSelected(false);
            PPost.setSelected(false);
            PPertahankanPost.setSelected(false);
            PLanjutkanPost.setText("");
            
            
//            GLASSGOW COMA SCALE
            MembukaMata.setSelectedItem("Tidak Ada");
            SkalaMembukaMata.setText("");
            ResponMotor.setSelectedItem("Tanpa Respon");
            SkalaResponMotor.setText("");
            Respon.setSelectedItem("Tanpa Respon");
            SkalaRespon.setText("");
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl2(TanggalOperasi,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            KdPetugasOperator.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NmPetugasOperator.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KdPetugasAnastesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NmPetugasAnastesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            
//            PRE
            Valid.SetTgl2(TanggalPengkajian,tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            OtotBantuNafas.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString()));
            Deformitas.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString()));
            Empisema.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString()));
            PengembanganDadaR.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString()));
            PengembanganDadaL.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString()));
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Spo2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Normal.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString()));
            Capillary1.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString()));
            Capillary2.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString()));
            Ket_Pendarahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Pendarahan.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString()));
            Tensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Nd.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            KulitHangat.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString()));
            KulitDingin.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString()));
            KulitLainnya.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString()));
            SkalaNyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            PenggunaanKateter.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString()));
            ProduksiUrine.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            KemihLainnyaPre.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            ComposMentis.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 35).toString()));
            Deltrium.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString()));
            Stuper.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 37).toString()));
            Apatis.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 38).toString()));
            Koma.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 39).toString()));
            Bb.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Puasa.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 41).toString()));
            Mual.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 42).toString()));
            Distensil.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 43).toString()));
            Muntah.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 44).toString()));
            PatahTulang.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 45).toString()));
            Regio.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Tenang.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 47).toString()));
            Nangis.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 48).toString()));
            Gelisah.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 49).toString()));
            MentalLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            
            Kecemasan.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 51).toString()));
            PertukaranGas.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 52).toString()));
            MobilitasFisik.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 53).toString()));
            IntegritasKulit.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 54).toString()));
            KomunikasiVerbal.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 55).toString()));
            TidakEfektifPolaNafas.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 56).toString()));
            TidakEfektifKupingIndividu.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 57).toString()));
            DefisiPengetahuan.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 58).toString()));
            PotensialInfeksi.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 56).toString()));
            Nyeri.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 60).toString()));
            DiagnosaLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            LakukanInteraksiSosial.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 62).toString()));
            KelengkapanDokumenPraOperasi.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 63).toString()));
            LakukanOrientasi.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 64).toString()));
            TTV.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 65).toString()));
            SignIn.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 66).toString()));
            PerencanaanLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            S.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            O.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            N.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            R.setText(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            ATercapai.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 73).toString()));
            ATercapaiSebagian.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 74).toString()));
            ABelumTercapai.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 75).toString()));
            P.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 76).toString()));
            PPertahankan.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 77).toString()));
            PLanjutkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            
            
//            INTRA
            Valid.SetTgl2(TanggalPengkajianIntra,tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            Valid.SetTgl2(WaktuMasuk,tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            Valid.SetTgl2(WaktuKeluar,tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            Valid.SetTgl2(AnastesiMulai,tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            Valid.SetTgl2(AnastesiKeluar,tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            Valid.SetTgl2(OperasiMulai,tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            Valid.SetTgl2(OperasiSelesai,tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            AnastesiUmum.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 86).toString()));
            AnastesiBlok.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 87).toString()));
            AnastesiRegional.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 88).toString()));
            Asa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            KulitPreOPUtuh.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 90).toString()));
            PersiapanKulit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            LukaBersih.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 92).toString()));
            LukaKotor.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 93).toString()));
            LukaTerkontaminasi.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 94).toString()));
            PosisiSupin.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 95).toString()));
            PosisiUthotomi.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 96).toString()));
            PosisiLiteral.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 97).toString()));
            PosisiLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),98).toString());
            PemasangElek.setText(tbObat.getValueAt(tbObat.getSelectedRow(),99).toString());
            LetakGround.setText(tbObat.getValueAt(tbObat.getSelectedRow(),100).toString());
            Coagulant.setText(tbObat.getValueAt(tbObat.getSelectedRow(),101).toString());
            Cutting.setText(tbObat.getValueAt(tbObat.getSelectedRow(),102).toString());
            MesinSuction.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 103).toString()));
            BlanketWarmer.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 104).toString()));
            Valid.SetTgl2(JamMulaiTor,tbObat.getValueAt(tbObat.getSelectedRow(),105).toString());
            Valid.SetTgl2(JamSelesaiTor,tbObat.getValueAt(tbObat.getSelectedRow(),106).toString());
            PemasangTor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),107).toString());
            Graft.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 108).toString()));
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),109).toString());
            PendarahanIntraOP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),110).toString());
            ProdukDarah.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 111).toString()));
            Valid.SetTgl2(JamMulaiDarah,tbObat.getValueAt(tbObat.getSelectedRow(),112).toString());
            Valid.SetTgl2(JamMulaiDarah1,tbObat.getValueAt(tbObat.getSelectedRow(),113).toString());
            Valid.SetTgl2(JamSelesaiDarah,tbObat.getValueAt(tbObat.getSelectedRow(),114).toString());
            Valid.SetTgl2(JamSelesaiDarah1,tbObat.getValueAt(tbObat.getSelectedRow(),115).toString());
            Rutin.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 116).toString()));
            PotongBeku.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 117).toString()));
            Kultur.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 118).toString()));
            PenggunaanKateterIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 119).toString()));
            ProduksiUrineIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),120).toString());
            KemihLainnyaIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),121).toString());
            TenangIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 122).toString()));
            GelisahIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 123).toString()));
            NangisIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 124).toString()));
            MentalLainnyaIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),125).toString());
            
            ResikoInfeksi.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 126).toString()));
            ResikoCidera.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 127).toString()));
            ResikoHipotermi.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 128).toString()));
            KecemasanIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 129).toString()));
            IntegritasKulitIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 130).toString()));
            TidakEfektifPolaNafasIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 131).toString()));
            ResikoSyok.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 132).toString()));
            DiagnosaLainnyaIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),133).toString());
            SiapkanOK.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 134).toString()));
            SiapkanSelimut.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 135).toString()));
            SiapkanPasienDimejaOP.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 136).toString()));
            ObservasiTTVIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 137).toString()));
            AlatLinen.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 138).toString()));
            Elektromedis.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 139).toString()));
            PosisiPasien.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 140).toString()));
            PersiapanOP.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 141).toString()));
            PersiapanKulit.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 142).toString()));
            TimeOut.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 143).toString()));
            PerencanaanLainnyaIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),144).toString());
            SIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),145).toString());
            OIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),146).toString());
            TDIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),147).toString());
            NIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),148).toString());
            RIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),149).toString());
            ATercapaiIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 150).toString()));
            ATercapaiSebagianIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 151).toString()));
            ABelumTercapaiIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 152).toString()));
            PIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 153).toString()));
            PPertahankanIntra.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 154).toString()));
            PLanjutkanIntra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),155).toString());
            
            
//            POST
            Valid.SetTgl2(JamPengkajianPost,tbObat.getValueAt(tbObat.getSelectedRow(),156).toString());
            OtotBantuNafasPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 157).toString()));
            DeformitasPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 158).toString()));
            EmpisemaPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 159).toString()));
            PengembanganDadaRPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 160).toString()));
            PengembanganDadaLPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 161).toString()));
            RRPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),162).toString());
            Spo2Post.setText(tbObat.getValueAt(tbObat.getSelectedRow(),163).toString());
            NormalPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 164).toString()));
            Capillary1Post.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 165).toString()));
            Capillary2Post.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 166).toString()));
            Ket_PendarahanPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),167).toString());
            PendarahanPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 168).toString()));
            TensiPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),169).toString());
            NdPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),170).toString());
            SuhuPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),171).toString());
            KulitHangatPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 172).toString()));
            KulitDinginPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 173).toString()));
            KulitLainnyaPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 174).toString()));
            SkalaNyeriPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),175).toString());
            PenggunaanKateterPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 176).toString()));
            ProduksiUrinePost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),177).toString());
            KemihLainnyaPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),178).toString());
            ComposMentisPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 179).toString()));
            DeltriumPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 180).toString()));
            StuperPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 181).toString()));
            ApatisPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 182).toString()));
            KomaPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 183).toString()));
            BbPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),184).toString());
            PuasaPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 185).toString()));
            MualPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 186).toString()));
            DistensilPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 187).toString()));
            MuntahPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 188).toString()));
            CideraKulitPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 189).toString()));
            JenisBalutanPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),190).toString());
            DrainPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(),191).toString()));
            YaPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 192).toString()));
            TidakPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 193).toString()));
            ProduksiDrainPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),194).toString());
            PemasanganAlatPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 195).toString()));
            LokasiPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),196).toString());
            JenisPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),197).toString());
            
            RestiNyeriPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 198).toString()));
            RestiInefektifPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 199).toString()));
            RestiHypotermiPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 200).toString()));
            ResikoCideraPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 201).toString()));
            RestiPendarahanPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 202).toString()));
            RestiSyokPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 203).toString()));
            IntegritasKulitPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 204).toString()));
            MobilitasFisikPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 205).toString()));
            PotensiInfeksi.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 206).toString()));
            PerluasanInfeksiPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 207).toString()));
            DiagnosaLainnyaPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),208).toString());
            TTVPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 209).toString()));
            PosisiNyamanPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 210).toString()));
            SelimutHangatPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 211).toString()));
            TerapiOksigen.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 212).toString()));
            SerahTerimaPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 213).toString()));
            BerikanPasienPadaKeluargaPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 214).toString()));
            SignOut.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 215).toString()));
            PerencanaanLainnyaPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),216).toString()); 
            SPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),217).toString());
            OPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),218).toString());
            TDPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),219).toString());
            NPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),220).toString());
            RPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),221).toString());
            ATercapaiPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 222).toString()));
            ATercapaiSebagianPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 223).toString()));
            ABelumTercapaiPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 224).toString()));
            PPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 225).toString()));
            PPertahankanPost.setSelected(Boolean.parseBoolean(tbObat.getValueAt(tbObat.getSelectedRow(), 226).toString()));
            PLanjutkanPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),227).toString());
            
            
//            GLASSGOW COMA SCALE
            Valid.SetTgl2(JamPengkajianGlow,tbObat.getValueAt(tbObat.getSelectedRow(),228).toString());
            MembukaMata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),229).toString());
            SkalaMembukaMata.setText(tbObat.getValueAt(tbObat.getSelectedRow(),230).toString());
            ResponMotor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),231).toString());
            SkalaResponMotor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),232).toString());
            Respon.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),233).toString());
            SkalaRespon.setText(tbObat.getValueAt(tbObat.getSelectedRow(),234).toString());
            
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", NmPetugas,KdPetugas.getText());
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnHapus.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnEdit.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnPrint.setEnabled(akses.gettransfer_pasien_antar_ruang());
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from rencana_asuhan_keperawatan_operasi_pre where no_rawat=? and tanggal_masuk=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    
    private void gantipre() {
        if(Sequel.mengedittf("rencana_asuhan_keperawatan_operasi_pre","no_rawat=?","no_rawat=?,tanggal_masuk=?,kd_dr_operator=?,kd_dr_anastesi=?,jam_pengkajian_pre=?,otot_bantu_nafas=?,deformitas=?,empisema=?,pengembangan_dada_r=?,pengembangan_dada_l=?,"
                + "rr=?,spo2=?,normal=?,capilary1=?,capilary2=?,ket_perdarahan=?,perdarahan=?,tensi=?,nd=?,suhu=?,"
                + "kulit_hangat=?,kulit_dingin=?,kulit_lainnya=?,skala_nyeri=?,penggunaan_kateter=?,produksi_urine=?,kemih_lainnya_pre=?,compos_mentis=?,deltrium=?,stuper=?,"
                + "apatis=?,koma=?,bb=?,puasa=?,mual=?,distensil=?,muntah=?,patah_tulang=?,regio=?,tenang=?,"
                + "nangis=?,gelisah=?,mental_lainnya=?,kecemasan=?,pertukaran_gas=?,mobilitas_fisik=?,integritas_kulit=?,komunikasi_verbal=?,tidak_efektif_pola_nafas=?,tidak_efektif_kuping_individual=?,"
                + "defisi_pengetahuan=?,potensial_infeksi=?,nyeri=?,diagnosa_lainnya=?,interaksi_sosial=?,dokumen_pra_operasi=?,orientasi=?,ttv=?,sign_in=?,perencanaan_lainnya=?,"
                + "s=?,o=?,td=?,n=?,r=?,a_tercapai=?,a_tercapai_sebagian=?,a_belum=?,p=?,p_pertahankan=?,"
                + "p_lanjutkan=?,kd_petugas=?",73,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalOperasi.getSelectedItem()+"")+" "+TanggalOperasi.getSelectedItem().toString().substring(11,19),KdPetugasOperator.getText(),KdPetugasAnastesi.getText(),Valid.SetTgl(TanggalPengkajian.getSelectedItem()+"")+" "+TanggalPengkajian.getSelectedItem().toString().substring(11,19),(OtotBantuNafas.isSelected() ? "true" : ""),(Deformitas.isSelected() ? "true" : ""),(Empisema.isSelected() ? "true" : ""),(PengembanganDadaR.isSelected() ? "true" : ""),(PengembanganDadaL.isSelected() ? "true" : ""), 
                RR.getText(),Spo2.getText(),(Normal.isSelected() ? "true" : ""),(Capillary1.isSelected() ? "true" : ""),(Capillary2.isSelected() ? "true" : ""),Ket_Pendarahan.getText(),(Pendarahan.isSelected() ? "true" : ""),Tensi.getText(),Nd.getText(),Suhu.getText(),
                (KulitHangat.isSelected() ? "true" : ""),(KulitDingin.isSelected() ? "true" : ""),(KulitLainnya.isSelected() ? "true" : ""),SkalaNyeri.getText(),(PenggunaanKateter.isSelected() ? "true" : ""),ProduksiUrine.getText(),KemihLainnyaPre.getText(),(ComposMentis.isSelected() ? "true" : ""),(Deltrium.isSelected() ? "true" : ""),(Stuper.isSelected() ? "true" : ""),
                (Apatis.isSelected() ? "true" : ""),(Koma.isSelected() ? "true" : ""),Bb.getText(),(Puasa.isSelected() ? "true" : ""),(Mual.isSelected() ? "true" : ""),(Distensil.isSelected() ? "true" : ""),(Muntah.isSelected() ? "true" : ""),(PatahTulang.isSelected() ? "true" : ""),Regio.getText(),(Tenang.isSelected() ? "true" : ""),
                (Nangis.isSelected() ? "true" : ""),(Gelisah.isSelected() ? "true" : ""),MentalLainnya.getText(),(Kecemasan.isSelected() ? "true" : ""),(PertukaranGas.isSelected() ? "true" : ""),(MobilitasFisik.isSelected() ? "true" : ""),(IntegritasKulit.isSelected() ? "true" : ""),(KomunikasiVerbal.isSelected() ? "true" : ""),(TidakEfektifPolaNafas.isSelected() ? "true" : ""),(TidakEfektifKupingIndividu.isSelected() ? "true" : ""),
                (DefisiPengetahuan.isSelected() ? "true" : ""),(PotensialInfeksi.isSelected() ? "true" : ""),(Nyeri.isSelected() ? "true" : ""),DiagnosaLainnya.getText(),(LakukanInteraksiSosial.isSelected() ? "true" : ""),(KelengkapanDokumenPraOperasi.isSelected() ? "true" : ""),(LakukanOrientasi.isSelected() ? "true" : ""),(TTV.isSelected() ? "true" : ""),(SignIn.isSelected() ? "true" : ""),PerencanaanLainnya.getText(),
                S.getText(),O.getText(),TD.getText(),N.getText(),R.getText(),(ATercapai.isSelected() ? "true" : ""),(ATercapaiSebagian.isSelected() ? "true" : ""),(ABelumTercapai.isSelected() ? "true" : ""),(P.isSelected() ? "true" : ""),(PPertahankan.isSelected() ? "true" : ""),
                PLanjutkan.getText(),KdPetugas.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
        }
    }

    
    private void gantiintra() {
        if(Sequel.mengedittf("rencana_asuhan_keperawatan_operasi_intra","no_rawat=?","no_rawat=?,tanggal_masuk=?,kd_dr_operator=?,kd_dr_anastesi=?,jam_pengkajian_intra=?,waktu_masuk=?,waktu_keluar=?,anastesi_mulai=?,anastesi_selesai=?,operasi_mulai=?,"
                + "operasi_selesai=?,anastesi_umum=?,anastesi_blok=?,anastesi_regional=?,asa=?,kulit_pre_op_utuh=?,persiapan_kulit_oleh=?,luka_bersih=?,luka_kotor=?,luka_terkontaminasi=?,"
                + "posisi_supin=?,posisi_uthotomi=?,posisi_lateral=?,posisi_lainnya=?,pemasang_elek=?,letak_ground=?,coagulant=?,cutting=?,mesin_suction=?,blanket_warmer=?,"
                + "jam_mulai_tor=?,jam_selesai_tor=?,pemasang_tor=?,graft=?,lokasi=?,pendarahan_intra_op=?,produk_darah=?,jam_mulai_darah=?,jam_mulai_darah1=?,jam_selesai_darah=?,"
                + "jam_selesai_darah1=?,rutin=?,potong_beku=?,kultur=?,penggunaan_kateter_intra=?,produksi_urine_intra=?,kemih_lainnya_intra=?,tenang_intra=?,gelisah_intra=?,nangis_intra=?,"
                + "mental_lainnya_intra=?,resiko_infeksi=?,resiko_cidera=?,resiko_hipotermi=?,kecemasan_intra=?,integritas_kulit_intra=?,tidak_efektif_pola_nafas_intra=?,resiko_syok=?,diagnosa_lainnya_intra=?,siapkan_ok=?,"
                + "siapkan_slimut=?,siapkan_pasien_dimeja_op=?,observasi_ttv_intra=?,alat_linen=?,elektromedis=?,posisi_pasien=?,persiapan_op=?,persiapan_kulit=?,time_out=?,perencanaan_lainnya_intra=?,"
                + "s_intra=?,o_intra=?,td_intra=?,n_intra=?,r_intra=?,a_tercapai_intra=?,a_tercapai_sebagian_intra=?,a_belum_intra=?,p_intra=?,"
                + "p_pertahankan_intra=?,p_lanjutkan_intra=?,kd_petugas=?",83,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalOperasi.getSelectedItem()+"")+" "+TanggalOperasi.getSelectedItem().toString().substring(11,19),KdPetugasOperator.getText(),KdPetugasAnastesi.getText(),Valid.SetTgl(TanggalPengkajianIntra.getSelectedItem()+"")+" "+TanggalPengkajianIntra.getSelectedItem().toString().substring(11,19),Valid.SetTgl(WaktuMasuk.getSelectedItem()+"")+" "+WaktuMasuk.getSelectedItem().toString().substring(11,19),Valid.SetTgl(WaktuKeluar.getSelectedItem()+"")+" "+WaktuKeluar.getSelectedItem().toString().substring(11,19),Valid.SetTgl(AnastesiMulai.getSelectedItem()+"")+" "+AnastesiMulai.getSelectedItem().toString().substring(11,19),Valid.SetTgl(AnastesiKeluar.getSelectedItem()+"")+" "+AnastesiKeluar.getSelectedItem().toString().substring(11,19),Valid.SetTgl(OperasiMulai.getSelectedItem()+"")+" "+OperasiMulai.getSelectedItem().toString().substring(11,19), 
                Valid.SetTgl(OperasiSelesai.getSelectedItem()+"")+" "+OperasiSelesai.getSelectedItem().toString().substring(11,19),(AnastesiUmum.isSelected() ? "true" : ""),(AnastesiBlok.isSelected() ? "true" : ""),(AnastesiRegional.isSelected() ? "true" : ""),Asa.getText(),(KulitPreOPUtuh.isSelected() ? "true" : ""),PersiapanKulitOleh.getText(),(LukaBersih.isSelected() ? "true" : ""),(LukaKotor.isSelected() ? "true" : ""),(LukaTerkontaminasi.isSelected() ? "true" : ""),
                (PosisiSupin.isSelected() ? "true" : ""),(PosisiUthotomi.isSelected() ? "true" : ""),(PosisiLiteral.isSelected() ? "true" : ""),PosisiLainnya.getText(),PemasangElek.getText(),LetakGround.getText(),Coagulant.getText(),Cutting.getText(),(MesinSuction.isSelected() ? "true" : ""),(BlanketWarmer.isSelected() ? "true" : ""),
                Valid.SetTgl(JamMulaiTor.getSelectedItem()+"")+" "+JamMulaiTor.getSelectedItem().toString().substring(11,19),Valid.SetTgl(JamSelesaiTor.getSelectedItem()+"")+" "+JamSelesaiTor.getSelectedItem().toString().substring(11,19),PemasangTor.getText(),(Graft.isSelected() ? "true" : ""),Lokasi.getText(),PendarahanIntraOP.getText(),(ProdukDarah.isSelected() ? "true" : ""),Valid.SetTgl(JamMulaiDarah.getSelectedItem()+"")+" "+JamMulaiDarah.getSelectedItem().toString().substring(11,19),Valid.SetTgl(JamMulaiDarah1.getSelectedItem()+"")+" "+JamMulaiDarah1.getSelectedItem().toString().substring(11,19),Valid.SetTgl(JamSelesaiDarah.getSelectedItem()+"")+" "+JamSelesaiDarah.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(JamSelesaiDarah1.getSelectedItem()+"")+" "+JamSelesaiDarah1.getSelectedItem().toString().substring(11,19),(Rutin.isSelected() ? "true" : ""),(PotongBeku.isSelected() ? "true" : ""),(Kultur.isSelected() ? "true" : ""),(PenggunaanKateterIntra.isSelected() ? "true" : ""),ProduksiUrineIntra.getText(),KemihLainnyaIntra.getText(),(TenangIntra.isSelected() ? "true" : ""),(GelisahIntra.isSelected() ? "true" : ""),(NangisIntra.isSelected() ? "true" : ""),
                MentalLainnyaIntra.getText(),(ResikoInfeksi.isSelected() ? "true" : ""),(ResikoCidera.isSelected() ? "true" : ""),(ResikoHipotermi.isSelected() ? "true" : ""),(KecemasanIntra.isSelected() ? "true" : ""),(IntegritasKulitIntra.isSelected() ? "true" : ""),(TidakEfektifPolaNafasIntra.isSelected() ? "true" : ""),(ResikoSyok.isSelected() ? "true" : ""),DiagnosaLainnyaIntra.getText(),(SiapkanOK.isSelected() ? "true" : ""),
                (SiapkanSelimut.isSelected() ? "true" : ""),(SiapkanPasienDimejaOP.isSelected() ? "true" : ""),(ObservasiTTVIntra.isSelected() ? "true" : ""),(AlatLinen.isSelected() ? "true" : ""),(Elektromedis.isSelected() ? "true" : ""),(PosisiPasien.isSelected() ? "true" : ""),(PersiapanOP.isSelected() ? "true" : ""),(PersiapanKulit.isSelected() ? "true" : ""),(TimeOut.isSelected() ? "true" : ""),PerencanaanLainnyaIntra.getText(),
                SIntra.getText(),OIntra.getText(),TDIntra.getText(),NIntra.getText(),RIntra.getText(),(ATercapaiIntra.isSelected() ? "true" : ""),(ATercapaiSebagianIntra.isSelected() ? "true" : ""),(ABelumTercapaiIntra.isSelected() ? "true" : ""),(PIntra.isSelected() ? "true" : ""),(PPertahankanIntra.isSelected() ? "true" : ""),
                PLanjutkanIntra.getText(),KdPetugas.getText(),
//                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                TNoRw.getText()
            })==true){
        }
    }

    
    private void gantipost() {
        if(Sequel.mengedittf("rencana_asuhan_keperawatan_operasi_post","no_rawat=?","no_rawat=?,tanggal_masuk=?,kd_dr_operator=?,kd_dr_anastesi=?,jam_pengkajian_post=?,otot_bantu_nafas_post=?,deformitas_post=?,empisema_post=?,pengembangan_dada_r_post=?,pengembangan_dada_l_post=?,"
                + "rr_post=?,spo2_post=?,normal_post=?,capilary1_post=?,capilary2_post=?,ket_perdarahan_post=?,perdarahan_post=?,tensi_post=?,nd_post=?,suhu_post=?,"
                + "kulit_hangat_post=?,kulit_dingin_post=?,kulit_lainnya_post=?,skala_nyeri_post=?,penggunaan_kateter_post=?,produksi_urine_post=?,kemih_lainnya_pre_post=?,compos_mentis_post=?,deltrium_post=?,stuper_post=?,"
                + "apatis_post=?,koma_post=?,bb_post=?,puasa_post=?,mual_post=?,distensil_post=?,muntah_post=?,cidera_kulit_post=?,jenis_balutan_post=?,drain_post=?,"
                + "ya_post=?,tidak_post=?,produksi_drain_post=?,Pemasangan_alat_post=?,lokasi_post=?,jenis_post=?,resti_nyeri_post=?,resti_inefektif_post=?,resti_hypotermi_post=?,resti_cidera_post=?,"
                + "resti_pendarahan_post=?,resti_syok_post=?,intergritas_kulit_post=?,mobilitas_fisik_post=?,potensi_infeksi_post=?,perluasan_infeksi_post=?,diagnosa_lainnya_post=?,ttv_post=?,posisi_nyaman_post=?,selimut_hangat_post=?,"
                + "terapi_oksigen_post=?,serah_terima_post=?,berikan_pasien_pada_keluarga_post=?,signout_post=?,perencanaan_lainnya_post=?,s_post=?,o_post=?,td_post=?,n_post=?,r_post=?,"
                + "a_tercapai_post=?,a_tercapai_sebagian_post=?,a_belum_post=?,p_post=?,p_pertahankan_post=?,p_lanjutkan_post=?,kd_petugas=?",78,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalOperasi.getSelectedItem()+"")+" "+TanggalOperasi.getSelectedItem().toString().substring(11,19),KdPetugasOperator.getText(),KdPetugasAnastesi.getText(),Valid.SetTgl(JamPengkajianPost.getSelectedItem()+"")+" "+JamPengkajianPost.getSelectedItem().toString().substring(11,19),(OtotBantuNafasPost.isSelected() ? "true" : ""),(DeformitasPost.isSelected() ? "true" : ""),(EmpisemaPost.isSelected() ? "true" : ""),(PengembanganDadaRPost.isSelected() ? "true" : ""),(PengembanganDadaLPost.isSelected() ? "true" : ""), 
                RRPost.getText(),Spo2Post.getText(),(NormalPost.isSelected() ? "true" : ""),(Capillary1Post.isSelected() ? "true" : ""),(Capillary2Post.isSelected() ? "true" : ""),Ket_PendarahanPost.getText(),(PendarahanPost.isSelected() ? "true" : ""),TensiPost.getText(),NdPost.getText(),SuhuPost.getText(),
                (KulitHangatPost.isSelected() ? "true" : ""),(KulitDinginPost.isSelected() ? "true" : ""),(KulitLainnyaPost.isSelected() ? "true" : ""),SkalaNyeriPost.getText(),(PenggunaanKateterPost.isSelected() ? "true" : ""),ProduksiUrinePost.getText(),KemihLainnyaPost.getText(),(ComposMentisPost.isSelected() ? "true" : ""),(DeltriumPost.isSelected() ? "true" : ""),(StuperPost.isSelected() ? "true" : ""),
                (ApatisPost.isSelected() ? "true" : ""),(KomaPost.isSelected() ? "true" : ""),BbPost.getText(),(PuasaPost.isSelected() ? "true" : ""),(MualPost.isSelected() ? "true" : ""),(DistensilPost.isSelected() ? "true" : ""),(MuntahPost.isSelected() ? "true" : ""),(CideraKulitPost.isSelected() ? "true" : ""),JenisBalutanPost.getText(),(DrainPost.isSelected() ? "true" : ""),
                (YaPost.isSelected() ? "true" : ""),(TidakPost.isSelected() ? "true" : ""),ProduksiDrainPost.getText(),(PemasanganAlatPost.isSelected() ? "true" : ""),LokasiPost.getText(),JenisPost.getText(),(RestiNyeriPost.isSelected() ? "true" : ""),(RestiInefektifPost.isSelected() ? "true" : ""),(RestiHypotermiPost.isSelected() ? "true" : ""),(ResikoCideraPost.isSelected() ? "true" : ""),
                (RestiPendarahanPost.isSelected() ? "true" : ""),(RestiSyokPost.isSelected() ? "true" : ""),(IntegritasKulitPost.isSelected() ? "true" : ""),(MobilitasFisikPost.isSelected() ? "true" : ""),(PotensiInfeksi.isSelected() ? "true" : ""),(PerluasanInfeksiPost.isSelected() ? "true" : ""),DiagnosaLainnyaPost.getText(),(TTVPost.isSelected() ? "true" : ""),(PosisiNyamanPost.isSelected() ? "true" : ""),(SelimutHangatPost.isSelected() ? "true" : ""),
                (TerapiOksigen.isSelected() ? "true" : ""),(SerahTerimaPost.isSelected() ? "true" : ""),(BerikanPasienPadaKeluargaPost.isSelected() ? "true" : ""),(SignOut.isSelected() ? "true" : ""),PerencanaanLainnyaPost.getText(),SPost.getText(),OPost.getText(),TDPost.getText(),NPost.getText(),RPost.getText(),
                (ATercapaiPost.isSelected() ? "true" : ""),(ATercapaiSebagianPost.isSelected() ? "true" : ""),(ABelumTercapaiPost.isSelected() ? "true" : ""),(PPost.isSelected() ? "true" : ""),(PPertahankanPost.isSelected() ? "true" : ""),PLanjutkanPost.getText(),KdPetugas.getText(),
//                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                TNoRw.getText()
            })==true){
        }
    }

    
    private void gantiglow() {
        if(Sequel.mengedittf("rencana_asuhan_keperawatan_operasi_glassgow","no_rawat=?","no_rawat=?,tanggal_masuk=?,kd_dr_operator=?,kd_dr_anastesi=?,jam_pengkajian_glow=?,membuka_mata=?,scale_membuka_mata=?,respon_motor=?,scale_respon_motor=?,respon=?,scale_respon=?,kd_petugas=?",13,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalOperasi.getSelectedItem()+"")+" "+TanggalOperasi.getSelectedItem().toString().substring(11,19),KdPetugasOperator.getText(),KdPetugasAnastesi.getText(),Valid.SetTgl(JamPengkajianGlow.getSelectedItem()+"")+" "+JamPengkajianGlow.getSelectedItem().toString().substring(11,19),MembukaMata.getSelectedItem().toString(),SkalaMembukaMata.getText(),ResponMotor.getSelectedItem().toString(),SkalaResponMotor.getText(),Respon.getSelectedItem().toString(), 
                SkalaRespon.getText(),KdPetugas.getText(),
//                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                TNoRw.getText()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }

    
    private void simpanpre() {
        if(Sequel.menyimpantf("rencana_asuhan_keperawatan_operasi_pre","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat & Tanggal Masuk",72,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalOperasi.getSelectedItem()+"")+" "+TanggalOperasi.getSelectedItem().toString().substring(11,19),KdPetugasOperator.getText(),KdPetugasAnastesi.getText(),Valid.SetTgl(TanggalPengkajian.getSelectedItem()+"")+" "+TanggalPengkajian.getSelectedItem().toString().substring(11,19),(OtotBantuNafas.isSelected() ? "true" : ""),(Deformitas.isSelected() ? "true" : ""),(Empisema.isSelected() ? "true" : ""),(PengembanganDadaR.isSelected() ? "true" : ""),(PengembanganDadaL.isSelected() ? "true" : ""), 
                RR.getText(),Spo2.getText(),(Normal.isSelected() ? "true" : ""),(Capillary1.isSelected() ? "true" : ""),(Capillary2.isSelected() ? "true" : ""),Ket_Pendarahan.getText(),(Pendarahan.isSelected() ? "true" : ""),Tensi.getText(),Nd.getText(),Suhu.getText(),
                (KulitHangat.isSelected() ? "true" : ""),(KulitDingin.isSelected() ? "true" : ""),(KulitLainnya.isSelected() ? "true" : ""),SkalaNyeri.getText(),(PenggunaanKateter.isSelected() ? "true" : ""),ProduksiUrine.getText(),KemihLainnyaPre.getText(),(ComposMentis.isSelected() ? "true" : ""),(Deltrium.isSelected() ? "true" : ""),(Stuper.isSelected() ? "true" : ""),
                (Apatis.isSelected() ? "true" : ""),(Koma.isSelected() ? "true" : ""),Bb.getText(),(Puasa.isSelected() ? "true" : ""),(Mual.isSelected() ? "true" : ""),(Distensil.isSelected() ? "true" : ""),(Muntah.isSelected() ? "true" : ""),(PatahTulang.isSelected() ? "true" : ""),Regio.getText(),(Tenang.isSelected() ? "true" : ""),
                (Nangis.isSelected() ? "true" : ""),(Gelisah.isSelected() ? "true" : ""),MentalLainnya.getText(),(Kecemasan.isSelected() ? "true" : ""),(PertukaranGas.isSelected() ? "true" : ""),(MobilitasFisik.isSelected() ? "true" : ""),(IntegritasKulit.isSelected() ? "true" : ""),(KomunikasiVerbal.isSelected() ? "true" : ""),(TidakEfektifPolaNafas.isSelected() ? "true" : ""),(TidakEfektifKupingIndividu.isSelected() ? "true" : ""),
                (DefisiPengetahuan.isSelected() ? "true" : ""),(PotensialInfeksi.isSelected() ? "true" : ""),(Nyeri.isSelected() ? "true" : ""),DiagnosaLainnya.getText(),(LakukanInteraksiSosial.isSelected() ? "true" : ""),(KelengkapanDokumenPraOperasi.isSelected() ? "true" : ""),(LakukanOrientasi.isSelected() ? "true" : ""),(TTV.isSelected() ? "true" : ""),(SignIn.isSelected() ? "true" : ""),PerencanaanLainnya.getText(),
                S.getText(),O.getText(),TD.getText(),N.getText(),R.getText(),(ATercapai.isSelected() ? "true" : ""),(ATercapaiSebagian.isSelected() ? "true" : ""),(ABelumTercapai.isSelected() ? "true" : ""),(P.isSelected() ? "true" : ""),(PPertahankan.isSelected() ? "true" : ""),
                PLanjutkan.getText(),KdPetugas.getText()
            })==true){
        }
    }
    
    
    private void simpanintra() {
        if(Sequel.menyimpantf("rencana_asuhan_keperawatan_operasi_intra","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat & Tanggal Masuk",82,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalOperasi.getSelectedItem()+"")+" "+TanggalOperasi.getSelectedItem().toString().substring(11,19),KdPetugasOperator.getText(),KdPetugasAnastesi.getText(),Valid.SetTgl(TanggalPengkajianIntra.getSelectedItem()+"")+" "+TanggalPengkajianIntra.getSelectedItem().toString().substring(11,19),Valid.SetTgl(WaktuMasuk.getSelectedItem()+"")+" "+WaktuMasuk.getSelectedItem().toString().substring(11,19),Valid.SetTgl(WaktuKeluar.getSelectedItem()+"")+" "+WaktuKeluar.getSelectedItem().toString().substring(11,19),Valid.SetTgl(AnastesiMulai.getSelectedItem()+"")+" "+AnastesiMulai.getSelectedItem().toString().substring(11,19),Valid.SetTgl(AnastesiKeluar.getSelectedItem()+"")+" "+AnastesiKeluar.getSelectedItem().toString().substring(11,19),Valid.SetTgl(OperasiMulai.getSelectedItem()+"")+" "+OperasiMulai.getSelectedItem().toString().substring(11,19), 
                Valid.SetTgl(OperasiSelesai.getSelectedItem()+"")+" "+OperasiSelesai.getSelectedItem().toString().substring(11,19),(AnastesiUmum.isSelected() ? "true" : ""),(AnastesiBlok.isSelected() ? "true" : ""),(AnastesiRegional.isSelected() ? "true" : ""),Asa.getText(),(KulitPreOPUtuh.isSelected() ? "true" : ""),PersiapanKulitOleh.getText(),(LukaBersih.isSelected() ? "true" : ""),(LukaKotor.isSelected() ? "true" : ""),(LukaTerkontaminasi.isSelected() ? "true" : ""),
                (PosisiSupin.isSelected() ? "true" : ""),(PosisiUthotomi.isSelected() ? "true" : ""),(PosisiLiteral.isSelected() ? "true" : ""),PosisiLainnya.getText(),PemasangElek.getText(),LetakGround.getText(),Coagulant.getText(),Cutting.getText(),(MesinSuction.isSelected() ? "true" : ""),(BlanketWarmer.isSelected() ? "true" : ""),
                Valid.SetTgl(JamMulaiTor.getSelectedItem()+"")+" "+JamMulaiTor.getSelectedItem().toString().substring(11,19),Valid.SetTgl(JamSelesaiTor.getSelectedItem()+"")+" "+JamSelesaiTor.getSelectedItem().toString().substring(11,19),PemasangTor.getText(),(Graft.isSelected() ? "true" : ""),Lokasi.getText(),PendarahanIntraOP.getText(),(ProdukDarah.isSelected() ? "true" : ""),Valid.SetTgl(JamMulaiDarah.getSelectedItem()+"")+" "+JamMulaiDarah.getSelectedItem().toString().substring(11,19),Valid.SetTgl(JamMulaiDarah1.getSelectedItem()+"")+" "+JamMulaiDarah1.getSelectedItem().toString().substring(11,19),Valid.SetTgl(JamSelesaiDarah.getSelectedItem()+"")+" "+JamSelesaiDarah.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(JamSelesaiDarah1.getSelectedItem()+"")+" "+JamSelesaiDarah1.getSelectedItem().toString().substring(11,19),(Rutin.isSelected() ? "true" : ""),(PotongBeku.isSelected() ? "true" : ""),(Kultur.isSelected() ? "true" : ""),(PenggunaanKateterIntra.isSelected() ? "true" : ""),ProduksiUrineIntra.getText(),KemihLainnyaIntra.getText(),(TenangIntra.isSelected() ? "true" : ""),(GelisahIntra.isSelected() ? "true" : ""),(NangisIntra.isSelected() ? "true" : ""),
                MentalLainnyaIntra.getText(),(ResikoInfeksi.isSelected() ? "true" : ""),(ResikoCidera.isSelected() ? "true" : ""),(ResikoHipotermi.isSelected() ? "true" : ""),(KecemasanIntra.isSelected() ? "true" : ""),(IntegritasKulitIntra.isSelected() ? "true" : ""),(TidakEfektifPolaNafasIntra.isSelected() ? "true" : ""),(ResikoSyok.isSelected() ? "true" : ""),DiagnosaLainnyaIntra.getText(),(SiapkanOK.isSelected() ? "true" : ""),
                (SiapkanSelimut.isSelected() ? "true" : ""),(SiapkanPasienDimejaOP.isSelected() ? "true" : ""),(ObservasiTTVIntra.isSelected() ? "true" : ""),(AlatLinen.isSelected() ? "true" : ""),(Elektromedis.isSelected() ? "true" : ""),(PosisiPasien.isSelected() ? "true" : ""),(PersiapanOP.isSelected() ? "true" : ""),(PersiapanKulit.isSelected() ? "true" : ""),(TimeOut.isSelected() ? "true" : ""),PerencanaanLainnyaIntra.getText(),
                SIntra.getText(),OIntra.getText(),TDIntra.getText(),NIntra.getText(),RIntra.getText(),(ATercapaiIntra.isSelected() ? "true" : ""),(ATercapaiSebagianIntra.isSelected() ? "true" : ""),(ABelumTercapaiIntra.isSelected() ? "true" : ""),(PIntra.isSelected() ? "true" : ""),(PPertahankanIntra.isSelected() ? "true" : ""),
                PLanjutkanIntra.getText(),KdPetugas.getText()
            })==true){
        }
    }
    
    
    private void simpanpost() {
        if(Sequel.menyimpantf("rencana_asuhan_keperawatan_operasi_post","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat & Tanggal Masuk",77,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalOperasi.getSelectedItem()+"")+" "+TanggalOperasi.getSelectedItem().toString().substring(11,19),KdPetugasOperator.getText(),KdPetugasAnastesi.getText(),Valid.SetTgl(JamPengkajianPost.getSelectedItem()+"")+" "+JamPengkajianPost.getSelectedItem().toString().substring(11,19),(OtotBantuNafasPost.isSelected() ? "true" : ""),(DeformitasPost.isSelected() ? "true" : ""),(EmpisemaPost.isSelected() ? "true" : ""),(PengembanganDadaRPost.isSelected() ? "true" : ""),(PengembanganDadaLPost.isSelected() ? "true" : ""), 
                RRPost.getText(),Spo2Post.getText(),(NormalPost.isSelected() ? "true" : ""),(Capillary1Post.isSelected() ? "true" : ""),(Capillary2Post.isSelected() ? "true" : ""),Ket_PendarahanPost.getText(),(PendarahanPost.isSelected() ? "true" : ""),TensiPost.getText(),NdPost.getText(),SuhuPost.getText(),
                (KulitHangatPost.isSelected() ? "true" : ""),(KulitDinginPost.isSelected() ? "true" : ""),(KulitLainnyaPost.isSelected() ? "true" : ""),SkalaNyeriPost.getText(),(PenggunaanKateterPost.isSelected() ? "true" : ""),ProduksiUrinePost.getText(),KemihLainnyaPost.getText(),(ComposMentisPost.isSelected() ? "true" : ""),(DeltriumPost.isSelected() ? "true" : ""),(StuperPost.isSelected() ? "true" : ""),
                (ApatisPost.isSelected() ? "true" : ""),(KomaPost.isSelected() ? "true" : ""),BbPost.getText(),(PuasaPost.isSelected() ? "true" : ""),(MualPost.isSelected() ? "true" : ""),(DistensilPost.isSelected() ? "true" : ""),(MuntahPost.isSelected() ? "true" : ""),(CideraKulitPost.isSelected() ? "true" : ""),JenisBalutanPost.getText(),(DrainPost.isSelected() ? "true" : ""),
                (YaPost.isSelected() ? "true" : ""),(TidakPost.isSelected() ? "true" : ""),ProduksiDrainPost.getText(),(PemasanganAlatPost.isSelected() ? "true" : ""),LokasiPost.getText(),JenisPost.getText(),(RestiNyeriPost.isSelected() ? "true" : ""),(RestiInefektifPost.isSelected() ? "true" : ""),(RestiHypotermiPost.isSelected() ? "true" : ""),(ResikoCideraPost.isSelected() ? "true" : ""),
                (RestiPendarahanPost.isSelected() ? "true" : ""),(RestiSyokPost.isSelected() ? "true" : ""),(IntegritasKulitPost.isSelected() ? "true" : ""),(MobilitasFisikPost.isSelected() ? "true" : ""),(PotensiInfeksi.isSelected() ? "true" : ""),(PerluasanInfeksiPost.isSelected() ? "true" : ""),DiagnosaLainnyaPost.getText(),(TTVPost.isSelected() ? "true" : ""),(PosisiNyamanPost.isSelected() ? "true" : ""),(SelimutHangatPost.isSelected() ? "true" : ""),
                (TerapiOksigen.isSelected() ? "true" : ""),(SerahTerimaPost.isSelected() ? "true" : ""),(BerikanPasienPadaKeluargaPost.isSelected() ? "true" : ""),(SignOut.isSelected() ? "true" : ""),PerencanaanLainnyaPost.getText(),SPost.getText(),OPost.getText(),TDPost.getText(),NPost.getText(),RPost.getText(),
                (ATercapaiPost.isSelected() ? "true" : ""),(ATercapaiSebagianPost.isSelected() ? "true" : ""),(ABelumTercapaiPost.isSelected() ? "true" : ""),(PPost.isSelected() ? "true" : ""),(PPertahankanPost.isSelected() ? "true" : ""),PLanjutkanPost.getText(),KdPetugas.getText()
            })==true){
        }
    }
    
    
    private void simpanglow() {
        if(Sequel.menyimpantf("rencana_asuhan_keperawatan_operasi_glassgow","?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat & Tanggal Masuk",12,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalOperasi.getSelectedItem()+"")+" "+TanggalOperasi.getSelectedItem().toString().substring(11,19),KdPetugasOperator.getText(),KdPetugasAnastesi.getText(),Valid.SetTgl(JamPengkajianGlow.getSelectedItem()+"")+" "+JamPengkajianGlow.getSelectedItem().toString().substring(11,19),MembukaMata.getSelectedItem().toString(),SkalaMembukaMata.getText(),ResponMotor.getSelectedItem().toString(),SkalaResponMotor.getText(),Respon.getSelectedItem().toString(), 
                SkalaRespon.getText(),KdPetugas.getText()
            })==true){
                emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
    
    private void isCombo1(){
        if(MembukaMata.getSelectedItem().equals("Tidak Ada")){
            SkalaMembukaMata.setText("1");
        }else if(MembukaMata.getSelectedItem().equals("Pada Nyeri")){
            SkalaMembukaMata.setText("2");
        }else if(MembukaMata.getSelectedItem().equals("Pada Perintah")){
            SkalaMembukaMata.setText("3");
        }else if(MembukaMata.getSelectedItem().equals("Spontan")){
            SkalaMembukaMata.setText("4");
        }else{
            SkalaMembukaMata.setText("0");
        }

         if(ResponMotor.getSelectedItem().equals("Tanpa Respon")){
            SkalaResponMotor.setText("1");
        }else if(ResponMotor.getSelectedItem().equals("Ekstensi")){
            SkalaResponMotor.setText("2");
        }else if(ResponMotor.getSelectedItem().equals("Flexi Abnormal")){
            SkalaResponMotor.setText("3");
        }else if(ResponMotor.getSelectedItem().equals("Flexi Menarik")){
            SkalaResponMotor.setText("4");
        }else if(ResponMotor.getSelectedItem().equals("Pada Rangsangan")){
            SkalaResponMotor.setText("5");
        }else if(ResponMotor.getSelectedItem().equals("Menurut Perintah")){
            SkalaResponMotor.setText("6");
        }else{
            SkalaResponMotor.setText("0");
        }
  
         if(Respon.getSelectedItem().equals("Tanpa Respon")){
            SkalaRespon.setText("1");
        }else if(Respon.getSelectedItem().equals("Tanpa Arti")){
            SkalaRespon.setText("2");
        }else if(Respon.getSelectedItem().equals("Bicara Ngacau")){
            SkalaRespon.setText("3");
        }else if(Respon.getSelectedItem().equals("Orientasi Buruk")){
            SkalaRespon.setText("4");
        }else if(Respon.getSelectedItem().equals("Orientasi Baik")){
            SkalaRespon.setText("5");
        }else{
            SkalaRespon.setText("0");
        }
    }
    
}
