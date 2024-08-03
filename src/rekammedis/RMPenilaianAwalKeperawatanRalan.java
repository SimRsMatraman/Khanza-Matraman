/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
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
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;
import rekammedis.DlgMasterRencanaKeperawatan;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalKeperawatanRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeDetailMasalah;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,jml=0,index=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahkeperawatan=""; 
    private StringBuilder htmlContent;
    public DlgMasterRencanaKeperawatan masterr=new DlgMasterRencanaKeperawatan(null,false);
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalKeperawatanRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Agama","Bahasa","Cacat Fisik","Tgl.Lahir","Tgl.Asuhan","Informasi","TD","Nadi","RR","Suhu",
            "GCS","BB","TB","BMI","Keluhan Utama","Riwayat Penyakit Dahulu","Riwayat Penyakit Keluarga","Riwayat Pengobatan",
            "Alergi","Alat Bantu","Ket. Alat Bantu","Prothesa","Ket. Prothesa","ADL","Stts Psikologi","Ket. Psikologi","Hubungan Keluarga","Tinggal Dengan",
            "Ket. Tinggal","Ekonomi","Budaya","Ket. Budaya","Edukasi","Ket. Edukasi","Cara Berjalan A","Cara Berjalan B","Cara Berjalan C",
            "Hasil Penilaian Resiko Jatuh","Lapor Dokter","Ket. Lapor","Skrining Gizi 1","Nilai 1","Skrining Gizi 2","Nilai 2","Total Skor","Tingkat Nyeri","Provokes",
            "Ket. Provokes","Kualitas","Ket. Kualitas","Lokasi","Menyebar","Skala Nyeri","Durasi","Nyeri Hilang","Ket. Hilang Nyeri","Lapor Ke Dokter",
            "Jam Lapor","Rencana","NIP","Nama Petugas","1. Riwayat Jatuh","Nilai 1","2. Diagnosis Sekunder (≥ 2 Diagnosis Medis)",
            "Nilai 2","3. Alat Bantu","Nilai 3","4. Terpasang Infuse","Nilai 4","5. Gaya Berjalan","Nilai 5","6. Status Mental","Nilai 6","Total Nilai Morse",
            "1. Umur","Nilai 1","2. Jenis Kelamin","Nilai 2","3. Diagnosa","Nilai 3","4. Gangguan Kognitif","Nilai 4","5. Faktor Lingkungan","Nilai 5",
            "6. Pembedahan/Sedasi/Anestesi","Nilai 6","Total Nilai Humpty Dumpty","1. Usia","Nilai 1","2. Status Mental","Nilai 2","3. Kliminasi","Nilai 3","4. Pengobatan","Nilai 4","5. Diagnosa","Nilai 5",
            "6. Ambulasi / Keseimbangan","Nilai 6","7. Nutrisi","Nilai 7","8. Gangguan Pola Tidur","Nilai 8","9. Riwayat Jatuh","Nilai 9","Total Nilai Edmonson"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 65; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(35);
            }else if(i==11){
                column.setPreferredWidth(40);
            }else if(i==12){
                column.setPreferredWidth(35);
            }else if(i==13){
                column.setPreferredWidth(40);
            }else if(i==14){
                column.setPreferredWidth(35);
            }else if(i==15){
                column.setPreferredWidth(35);
            }else if(i==16){
                column.setPreferredWidth(35);
            }else if(i==17){
                column.setPreferredWidth(35);
            }else if(i==18){
                column.setPreferredWidth(180);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(100);
            }else if(i==23){
                column.setPreferredWidth(60);
            }else if(i==24){
                column.setPreferredWidth(90);
            }else if(i==25){
                column.setPreferredWidth(60);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(60);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(100);
            }else if(i==30){
                column.setPreferredWidth(103);
            }else if(i==31){
                column.setPreferredWidth(87);
            }else if(i==32){
                column.setPreferredWidth(90);
            }else if(i==33){
                column.setPreferredWidth(50);
            }else if(i==34){
                column.setPreferredWidth(58);
            }else if(i==35){
                column.setPreferredWidth(90);
            }else if(i==36){
                column.setPreferredWidth(60);
            }else if(i==37){
                column.setPreferredWidth(90);
            }else if(i==38){
                column.setPreferredWidth(87);
            }else if(i==39){
                column.setPreferredWidth(87);
            }else if(i==40){
                column.setPreferredWidth(87);
            }else if(i==41){
                column.setPreferredWidth(206);
            }else if(i==42){
                column.setPreferredWidth(75);
            }else if(i==43){
                column.setPreferredWidth(90);
            }else if(i==44){
                column.setPreferredWidth(80);
            }else if(i==45){
                column.setPreferredWidth(40);
            }else if(i==46){
                column.setPreferredWidth(80);
            }else if(i==47){
                column.setPreferredWidth(40);
            }else if(i==48){
                column.setPreferredWidth(60);
            }else if(i==49){
                column.setPreferredWidth(87);
            }else if(i==50){
                column.setPreferredWidth(87);
            }else if(i==51){
                column.setPreferredWidth(87);
            }else if(i==52){
                column.setPreferredWidth(90);
            }else if(i==53){
                column.setPreferredWidth(90);
            }else if(i==54){
                column.setPreferredWidth(110);
            }else if(i==55){
                column.setPreferredWidth(56);
            }else if(i==56){
                column.setPreferredWidth(60);
            }else if(i==57){
                column.setPreferredWidth(50);
            }else if(i==58){
                column.setPreferredWidth(90);
            }else if(i==59){
                column.setPreferredWidth(90);
            }else if(i==60){
                column.setPreferredWidth(90);
            }else if(i==61){
                column.setPreferredWidth(70);
            }else if(i==62){
                column.setPreferredWidth(200);
            }else if(i==63){
                column.setPreferredWidth(80);
            }else if(i==64){
                column.setPreferredWidth(150);
            }else if(i==65){
                column.setPreferredWidth(105);
            }else if(i==66){
                column.setPreferredWidth(65);
            }else if(i==67){
                column.setPreferredWidth(160);
            }else if(i==68){
                column.setPreferredWidth(50);
            }else if(i==69){
                column.setPreferredWidth(60);
            }else if(i==70){
                column.setPreferredWidth(90);
            }else if(i==71){
                column.setPreferredWidth(90);
            }else if(i==72){
                column.setPreferredWidth(65);
            }else if(i==73){
                column.setPreferredWidth(120);
            }else if(i==74){
                column.setPreferredWidth(82);
            }else if(i==75){
                column.setPreferredWidth(35);
            }else if(i==76){
                column.setPreferredWidth(40);
            }else if(i==77){
                column.setPreferredWidth(35);
            }else if(i==78){
                column.setPreferredWidth(40);
            }else if(i==79){
                column.setPreferredWidth(35);
            }else if(i==80){
                column.setPreferredWidth(35);
            }else if(i==81){
                column.setPreferredWidth(40);
            }else if(i==82){
                column.setPreferredWidth(200);
            }else if(i==83){
                column.setPreferredWidth(91);
            }else if(i==84){
                column.setPreferredWidth(150);
            }else if(i==85){
                column.setPreferredWidth(150);
            }else if(i==86){
                column.setPreferredWidth(82);
            }else if(i==87){
                column.setPreferredWidth(150);
            }else if(i==88){
                column.setPreferredWidth(106);
            }else if(i==89){
                column.setPreferredWidth(126);
            }else if(i==90){
                column.setPreferredWidth(150);
            }else if(i==91){
                column.setPreferredWidth(150);
            }else if(i==92){
                column.setPreferredWidth(59);
            }else if(i==93){
                column.setPreferredWidth(150);
            }else if(i==94){
                column.setPreferredWidth(51);
            }else if(i==95){
                column.setPreferredWidth(150);
            }else if(i==96){
                column.setPreferredWidth(90);
            }else if(i==97){
                column.setPreferredWidth(150);
            }else if(i==98){
                column.setPreferredWidth(103);
            }else if(i==99){
                column.setPreferredWidth(84);
            }else if(i==100){
                column.setPreferredWidth(150);
            }else if(i==101){
                column.setPreferredWidth(50);
            }else if(i==102){
                column.setPreferredWidth(53);
            }else if(i==103){
                column.setPreferredWidth(150);
            }else if(i==104){
                column.setPreferredWidth(49);
            }else if(i==105){
                column.setPreferredWidth(150);
            }else if(i==106){
                column.setPreferredWidth(84);
            }else if(i==107){
                column.setPreferredWidth(84);
            }else if(i==108){
                column.setPreferredWidth(84);
            }else if(i==109){
                column.setPreferredWidth(210);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeMasalah=new DefaultTableModel(null,new Object[]{
                "P","KODE","MASALAH KEPERAWATAN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbMasalahKeperawatan.setModel(tabModeMasalah);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahKeperawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMasalahKeperawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 3; i++) {
            TableColumn column = tbMasalahKeperawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbMasalahKeperawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMasalah=new DefaultTableModel(null,new Object[]{
                "Kode","Masalah Keperawatan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbMasalahDetailMasalah.setModel(tabModeDetailMasalah);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahDetailMasalah.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMasalahDetailMasalah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbMasalahDetailMasalah.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(420);
            }
        }
        tbMasalahDetailMasalah.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        GCS.setDocument(new batasInput((byte)5).getKata(GCS));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        BMI.setDocument(new batasInput((byte)5).getKata(BMI));
        RPD.setDocument(new batasInput((int)1000).getKata(RPD));
        RPK.setDocument(new batasInput((int)1000).getKata(RPK));
        RPO.setDocument(new batasInput((int)1000).getKata(RPO));
        Alergi.setDocument(new batasInput((int)50).getKata(Alergi));
        KetBantu.setDocument(new batasInput((int)50).getKata(KetBantu));
        KetProthesa.setDocument(new batasInput((int)50).getKata(KetProthesa));
        KetBudaya.setDocument(new batasInput((int)50).getKata(KetBudaya));
        KetPsiko.setDocument(new batasInput((int)70).getKata(KetPsiko));
        KetTinggal.setDocument(new batasInput((int)40).getKata(KetTinggal));
        KetEdukasi.setDocument(new batasInput((int)50).getKata(KetEdukasi));
        KetLapor.setDocument(new batasInput((int)15).getKata(KetLapor));
        KetProvokes.setDocument(new batasInput((int)40).getKata(KetProvokes));
        KetQuality.setDocument(new batasInput((int)50).getKata(KetQuality));
        Lokasi.setDocument(new batasInput((int)50).getKata(Lokasi));
        Durasi.setDocument(new batasInput((int)25).getKata(Durasi));
        KetNyeri.setDocument(new batasInput((int)40).getKata(KetNyeri));
        KetDokter.setDocument(new batasInput((int)15).getKata(KetDokter));
        Rencana.setDocument(new batasInput((int)200).getKata(Rencana));
        
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
        
        BB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isBMI();
            }
        });
        
        TB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isBMI();
            }
        });
        
        masterr.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("RMPenilaianAwalKeperawatanRalan")){
                    if(masterr.getTable().getSelectedRow()!= -1){
                        Rencana.setText(masterr.getTable().getValueAt(masterr.getTable().getSelectedRow(),3).toString());
                    }  
                    Rencana.requestFocus();
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
        
        masterr.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("RMPenilaianAwalKeperawatanRalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        masterr.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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
        
        
        ChkAccor.setSelected(false);
        isMenu();
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
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        TB = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel14 = new widget.Label();
        BMI = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel52 = new widget.Label();
        Informasi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        AlatBantu = new widget.ComboBox();
        KetBantu = new widget.TextBox();
        jLabel54 = new widget.Label();
        Prothesa = new widget.ComboBox();
        KetProthesa = new widget.TextBox();
        jLabel55 = new widget.Label();
        ADL = new widget.ComboBox();
        jLabel57 = new widget.Label();
        StatusPsiko = new widget.ComboBox();
        KetPsiko = new widget.TextBox();
        jLabel58 = new widget.Label();
        HubunganKeluarga = new widget.ComboBox();
        jLabel59 = new widget.Label();
        TinggalDengan = new widget.ComboBox();
        KetTinggal = new widget.TextBox();
        jLabel60 = new widget.Label();
        Ekonomi = new widget.ComboBox();
        jLabel61 = new widget.Label();
        Edukasi = new widget.ComboBox();
        KetEdukasi = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        Lapor = new widget.ComboBox();
        ATS = new widget.ComboBox();
        BJM = new widget.ComboBox();
        jLabel67 = new widget.Label();
        Hasil = new widget.ComboBox();
        jLabel68 = new widget.Label();
        SG2 = new widget.ComboBox();
        KetLapor = new widget.TextBox();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        SG1 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        Nilai1 = new widget.ComboBox();
        MSA = new widget.ComboBox();
        jLabel73 = new widget.Label();
        Nilai2 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        Nyeri = new widget.ComboBox();
        jLabel79 = new widget.Label();
        Provokes = new widget.ComboBox();
        KetProvokes = new widget.TextBox();
        jLabel80 = new widget.Label();
        Quality = new widget.ComboBox();
        KetQuality = new widget.TextBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        Lokasi = new widget.TextBox();
        jLabel83 = new widget.Label();
        Menyebar = new widget.ComboBox();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        SkalaNyeri = new widget.ComboBox();
        jLabel86 = new widget.Label();
        Durasi = new widget.TextBox();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        NyeriHilang = new widget.ComboBox();
        KetNyeri = new widget.TextBox();
        jLabel89 = new widget.Label();
        PadaDokter = new widget.ComboBox();
        KetDokter = new widget.TextBox();
        scrollPane5 = new widget.ScrollPane();
        Rencana = new widget.TextArea();
        jLabel92 = new widget.Label();
        TotalHasil = new widget.TextBox();
        TglAsuhan = new widget.Tanggal();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jLabel93 = new widget.Label();
        jLabel94 = new widget.Label();
        jLabel51 = new widget.Label();
        CacatFisik = new widget.TextBox();
        jLabel56 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel95 = new widget.Label();
        StatusBudaya = new widget.ComboBox();
        KetBudaya = new widget.TextBox();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        jLabel63 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel74 = new widget.Label();
        PanelWall = new usu.widget.glass.PanelGlass();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel71 = new widget.Label();
        jSeparator10 = new javax.swing.JSeparator();
        Scroll6 = new widget.ScrollPane();
        tbMasalahKeperawatan = new widget.Table();
        label12 = new widget.Label();
        TCariMasalah = new widget.TextBox();
        BtnCariPemeriksaan1 = new widget.Button();
        BtnTambahMasalah = new widget.Button();
        Bahasa = new widget.TextBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        Agama = new widget.TextBox();
        BtnDokter1 = new widget.Button();
        jLabel170 = new widget.Label();
        jLabel181 = new widget.Label();
        SkalaResiko1 = new widget.ComboBox();
        jLabel182 = new widget.Label();
        NilaiResiko1 = new widget.TextBox();
        jLabel183 = new widget.Label();
        jLabel184 = new widget.Label();
        jLabel185 = new widget.Label();
        SkalaResiko2 = new widget.ComboBox();
        NilaiResiko2 = new widget.TextBox();
        jLabel186 = new widget.Label();
        jLabel187 = new widget.Label();
        jLabel188 = new widget.Label();
        SkalaResiko3 = new widget.ComboBox();
        jLabel189 = new widget.Label();
        NilaiResiko3 = new widget.TextBox();
        jLabel190 = new widget.Label();
        jLabel191 = new widget.Label();
        SkalaResiko4 = new widget.ComboBox();
        jLabel192 = new widget.Label();
        NilaiResiko4 = new widget.TextBox();
        jLabel193 = new widget.Label();
        jLabel194 = new widget.Label();
        SkalaResiko5 = new widget.ComboBox();
        jLabel195 = new widget.Label();
        NilaiResiko5 = new widget.TextBox();
        jLabel196 = new widget.Label();
        jLabel197 = new widget.Label();
        SkalaResiko6 = new widget.ComboBox();
        jLabel198 = new widget.Label();
        NilaiResiko6 = new widget.TextBox();
        jLabel199 = new widget.Label();
        NilaiResikoTotal = new widget.TextBox();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel171 = new widget.Label();
        jLabel200 = new widget.Label();
        SkalaHumpty1 = new widget.ComboBox();
        jLabel201 = new widget.Label();
        NilaiHumpty1 = new widget.TextBox();
        jLabel202 = new widget.Label();
        jLabel203 = new widget.Label();
        jLabel204 = new widget.Label();
        SkalaHumpty2 = new widget.ComboBox();
        NilaiHumpty2 = new widget.TextBox();
        jLabel205 = new widget.Label();
        jLabel206 = new widget.Label();
        jLabel207 = new widget.Label();
        SkalaHumpty3 = new widget.ComboBox();
        jLabel208 = new widget.Label();
        NilaiHumpty3 = new widget.TextBox();
        jLabel209 = new widget.Label();
        jLabel210 = new widget.Label();
        SkalaHumpty4 = new widget.ComboBox();
        jLabel211 = new widget.Label();
        NilaiHumpty4 = new widget.TextBox();
        jLabel212 = new widget.Label();
        jLabel213 = new widget.Label();
        SkalaHumpty5 = new widget.ComboBox();
        jLabel214 = new widget.Label();
        NilaiHumpty5 = new widget.TextBox();
        jLabel215 = new widget.Label();
        jLabel216 = new widget.Label();
        SkalaHumpty6 = new widget.ComboBox();
        jLabel217 = new widget.Label();
        NilaiHumpty6 = new widget.TextBox();
        jLabel218 = new widget.Label();
        NilaiResikoHumptyTotal = new widget.TextBox();
        TingkatResikoHumpty = new widget.Label();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel172 = new widget.Label();
        jLabel219 = new widget.Label();
        SkalaEdmonson1 = new widget.ComboBox();
        jLabel220 = new widget.Label();
        NilaiEdmonson1 = new widget.TextBox();
        jLabel221 = new widget.Label();
        jLabel222 = new widget.Label();
        jLabel223 = new widget.Label();
        SkalaEdmonson2 = new widget.ComboBox();
        NilaiEdmonson2 = new widget.TextBox();
        jLabel224 = new widget.Label();
        jLabel225 = new widget.Label();
        jLabel226 = new widget.Label();
        SkalaEdmonson3 = new widget.ComboBox();
        jLabel227 = new widget.Label();
        NilaiEdmonson3 = new widget.TextBox();
        jLabel228 = new widget.Label();
        jLabel229 = new widget.Label();
        SkalaEdmonson4 = new widget.ComboBox();
        jLabel230 = new widget.Label();
        NilaiEdmonson4 = new widget.TextBox();
        jLabel231 = new widget.Label();
        jLabel232 = new widget.Label();
        SkalaEdmonson5 = new widget.ComboBox();
        jLabel233 = new widget.Label();
        NilaiEdmonson5 = new widget.TextBox();
        jLabel234 = new widget.Label();
        jLabel235 = new widget.Label();
        SkalaEdmonson6 = new widget.ComboBox();
        jLabel236 = new widget.Label();
        NilaiEdmonson6 = new widget.TextBox();
        jLabel237 = new widget.Label();
        NilaiResikoEdmonsonTotal = new widget.TextBox();
        jLabel238 = new widget.Label();
        jLabel239 = new widget.Label();
        SkalaEdmonson7 = new widget.ComboBox();
        jLabel240 = new widget.Label();
        NilaiEdmonson7 = new widget.TextBox();
        jLabel241 = new widget.Label();
        jLabel242 = new widget.Label();
        SkalaEdmonson8 = new widget.ComboBox();
        jLabel243 = new widget.Label();
        NilaiEdmonson8 = new widget.TextBox();
        jLabel244 = new widget.Label();
        jLabel245 = new widget.Label();
        SkalaEdmonson9 = new widget.ComboBox();
        jLabel246 = new widget.Label();
        NilaiEdmonson9 = new widget.TextBox();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel173 = new widget.Label();
        TingkatResiko = new widget.Label();
        jLabel262 = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        TingkatResikoEdmonson = new widget.TextArea();
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
        FormMenu = new widget.PanelBiasa();
        jLabel34 = new widget.Label();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        BtnPrint1 = new widget.Button();
        FormMasalahRencana = new widget.PanelBiasa();
        Scroll7 = new widget.ScrollPane();
        tbMasalahDetailMasalah = new widget.Table();
        scrollPane6 = new widget.ScrollPane();
        DetailRencana = new widget.TextArea();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Keperawatan Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 2250));
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
        TPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPasienActionPerformed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(74, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(176, 40, 180, 23);

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
        BtnDokter.setBounds(358, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel9.setText("Riwayat Pengobatan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(440, 240, 150, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(395, 40, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 140, 70, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(74, 140, 60, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(137, 140, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(250, 140, 60, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(206, 140, 40, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(313, 90, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(250, 90, 60, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(206, 90, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(566, 90, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                SuhuKeyTyped(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(610, 90, 60, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 90, 70, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TDKeyTyped(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(74, 90, 60, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(673, 90, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(137, 90, 50, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText(" cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(313, 140, 30, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(485, 90, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(422, 90, 60, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(378, 90, 40, 23);

        jLabel14.setText("BMI :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(378, 140, 40, 23);

        BMI.setFocusTraversalPolicyProvider(true);
        BMI.setName("BMI"); // NOI18N
        BMI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BMIKeyPressed(evt);
            }
        });
        FormInput.add(BMI);
        BMI.setBounds(422, 140, 60, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Kg/m²");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(485, 140, 50, 23);

        jLabel36.setText("Informasi didapat dari :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(592, 40, 130, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 290, 175, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(179, 290, 260, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("2. Apakah nafsu makan berkurang karena tidak nafsu makan ?");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(40, 740, 460, 23);

        jLabel50.setText("Prothesa :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(476, 340, 60, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("VIII. PENILAIAN TINGKAT NYERI");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(10, 800, 380, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Informasi.setName("Informasi"); // NOI18N
        Informasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiKeyPressed(evt);
            }
        });
        FormInput.add(Informasi);
        Informasi.setBounds(726, 40, 128, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("I. KEADAAN UMUM");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(179, 190, 260, 43);

        jLabel30.setText("Keluhan Utama :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 190, 175, 20);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPD);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(179, 240, 260, 43);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 240, 175, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RPK);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(594, 190, 260, 42);

        jLabel32.setText("Riwayat Penyakit Keluarga :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(440, 190, 150, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RPO.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPO.setColumns(20);
        RPO.setRows(5);
        RPO.setName("RPO"); // NOI18N
        RPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RPO);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(594, 240, 260, 42);

        AlatBantu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        AlatBantu.setName("AlatBantu"); // NOI18N
        AlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantuKeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu);
        AlatBantu.setBounds(124, 340, 90, 23);

        KetBantu.setFocusTraversalPolicyProvider(true);
        KetBantu.setName("KetBantu"); // NOI18N
        KetBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBantuKeyPressed(evt);
            }
        });
        FormInput.add(KetBantu);
        KetBantu.setBounds(218, 340, 220, 23);

        jLabel54.setText("a. Hubungan pasien dengan anggota keluarga :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(35, 470, 250, 23);

        Prothesa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Prothesa.setName("Prothesa"); // NOI18N
        Prothesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProthesaKeyPressed(evt);
            }
        });
        FormInput.add(Prothesa);
        Prothesa.setBounds(540, 340, 90, 23);

        KetProthesa.setFocusTraversalPolicyProvider(true);
        KetProthesa.setName("KetProthesa"); // NOI18N
        KetProthesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProthesaKeyPressed(evt);
            }
        });
        FormInput.add(KetProthesa);
        KetProthesa.setBounds(634, 340, 220, 23);

        jLabel55.setText("Alat Bantu :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 340, 120, 23);

        ADL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Dibantu" }));
        ADL.setName("ADL"); // NOI18N
        ADL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLKeyPressed(evt);
            }
        });
        FormInput.add(ADL);
        ADL.setBounds(724, 370, 130, 23);

        jLabel57.setText("Aktivitas Kehidupan Sehari-hari ( ADL ) :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(440, 370, 280, 23);

        StatusPsiko.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tenang", "Takut", "Cemas", "Depresi", "Lain-lain" }));
        StatusPsiko.setName("StatusPsiko"); // NOI18N
        StatusPsiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusPsikoKeyPressed(evt);
            }
        });
        FormInput.add(StatusPsiko);
        StatusPsiko.setBounds(134, 420, 110, 23);

        KetPsiko.setFocusTraversalPolicyProvider(true);
        KetPsiko.setName("KetPsiko"); // NOI18N
        KetPsiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPsikoKeyPressed(evt);
            }
        });
        FormInput.add(KetPsiko);
        KetPsiko.setBounds(248, 420, 230, 23);

        jLabel58.setText("Edukasi diberikan kepada :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(226, 530, 140, 23);

        HubunganKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Tidak Baik" }));
        HubunganKeluarga.setName("HubunganKeluarga"); // NOI18N
        HubunganKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(HubunganKeluarga);
        HubunganKeluarga.setBounds(289, 470, 100, 23);

        jLabel59.setText("Status Sosial dan ekonomi :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 450, 176, 23);

        TinggalDengan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sendiri", "Orang Tua", "Suami / Istri", "Lainnya" }));
        TinggalDengan.setName("TinggalDengan"); // NOI18N
        TinggalDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalDenganKeyPressed(evt);
            }
        });
        FormInput.add(TinggalDengan);
        TinggalDengan.setBounds(497, 470, 110, 23);

        KetTinggal.setFocusTraversalPolicyProvider(true);
        KetTinggal.setName("KetTinggal"); // NOI18N
        KetTinggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetTinggalKeyPressed(evt);
            }
        });
        FormInput.add(KetTinggal);
        KetTinggal.setBounds(610, 470, 85, 23);

        jLabel60.setText("b. Tinggal dengan :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(393, 470, 100, 23);

        Ekonomi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Cukup", "Kurang" }));
        Ekonomi.setName("Ekonomi"); // NOI18N
        Ekonomi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkonomiKeyPressed(evt);
            }
        });
        FormInput.add(Ekonomi);
        Ekonomi.setBounds(770, 470, 84, 23);

        jLabel61.setText("c. Ekonomi :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(695, 470, 71, 23);

        Edukasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasien", "Keluarga" }));
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        FormInput.add(Edukasi);
        Edukasi.setBounds(370, 530, 110, 23);

        KetEdukasi.setFocusTraversalPolicyProvider(true);
        KetEdukasi.setName("KetEdukasi"); // NOI18N
        KetEdukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetEdukasiKeyPressed(evt);
            }
        });
        FormInput.add(KetEdukasi);
        KetEdukasi.setBounds(484, 530, 370, 23);

        jLabel64.setText("Jam  :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(720, 970, 50, 23);

        jLabel65.setText("1. Tidak seimbang / sempoyongan / limbung :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(35, 600, 250, 23);

        jLabel66.setText("2. Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain) :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(370, 600, 400, 23);

        Lapor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Lapor.setName("Lapor"); // NOI18N
        Lapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaporKeyPressed(evt);
            }
        });
        FormInput.add(Lapor);
        Lapor.setBounds(575, 660, 80, 23);

        ATS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ATS.setName("ATS"); // NOI18N
        ATS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATSKeyPressed(evt);
            }
        });
        FormInput.add(ATS);
        ATS.setBounds(289, 600, 80, 23);

        BJM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BJM.setName("BJM"); // NOI18N
        BJM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BJMKeyPressed(evt);
            }
        });
        FormInput.add(BJM);
        BJM.setBounds(774, 600, 80, 23);

        jLabel67.setText("Menyebar :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(690, 900, 79, 23);

        Hasil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak beresiko (tidak ditemukan a dan b)", "Resiko rendah (ditemukan a/b)", "Resiko tinggi (ditemukan a dan b)" }));
        Hasil.setName("Hasil"); // NOI18N
        Hasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilKeyPressed(evt);
            }
        });
        FormInput.add(Hasil);
        Hasil.setBounds(76, 660, 293, 23);

        jLabel68.setText("Hasil :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 660, 72, 23);

        SG2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG2.setName("SG2"); // NOI18N
        SG2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG2ItemStateChanged(evt);
            }
        });
        SG2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG2KeyPressed(evt);
            }
        });
        FormInput.add(SG2);
        SG2.setBounds(520, 740, 160, 23);

        KetLapor.setFocusTraversalPolicyProvider(true);
        KetLapor.setName("KetLapor"); // NOI18N
        KetLapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLaporKeyPressed(evt);
            }
        });
        FormInput.add(KetLapor);
        KetLapor.setBounds(774, 660, 80, 23);

        jLabel69.setText("Nilai :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(695, 740, 75, 23);

        jLabel70.setText("b. Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja / benda lain sebagai penopang :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 630, 571, 23);

        SG1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Tidak Yakin", "Ya, 1-5 Kg", "Ya, 6-10 Kg", "Ya, 11-15 Kg", "Ya, >15 Kg" }));
        SG1.setName("SG1"); // NOI18N
        SG1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG1ItemStateChanged(evt);
            }
        });
        SG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG1KeyPressed(evt);
            }
        });
        FormInput.add(SG1);
        SG1.setBounds(520, 710, 160, 23);

        jLabel72.setText("a. Cara Berjalan :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 580, 126, 23);

        Nilai1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "2", "1", "2", "3", "4" }));
        Nilai1.setName("Nilai1"); // NOI18N
        Nilai1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Nilai1ItemStateChanged(evt);
            }
        });
        Nilai1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nilai1KeyPressed(evt);
            }
        });
        FormInput.add(Nilai1);
        Nilai1.setBounds(774, 710, 80, 23);

        MSA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MSA.setName("MSA"); // NOI18N
        MSA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MSAKeyPressed(evt);
            }
        });
        FormInput.add(MSA);
        MSA.setBounds(575, 630, 80, 23);

        jLabel73.setText("Total Skor :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(695, 770, 75, 23);

        Nilai2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1" }));
        Nilai2.setName("Nilai2"); // NOI18N
        Nilai2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nilai2KeyPressed(evt);
            }
        });
        FormInput.add(Nilai2);
        Nilai2.setBounds(774, 740, 80, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("1. Apakah ada penurunan berat badan yang tidak diinginkan selama 6 bulan terakhir ?");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(40, 710, 460, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Nyeri", "Nyeri Akut", "Nyeri Kronis" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(375, 820, 130, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Rencana Keperawatan :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(460, 2050, 120, 23);

        Provokes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Proses Penyakit", "Benturan", "Lain-lain" }));
        Provokes.setName("Provokes"); // NOI18N
        Provokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProvokesKeyPressed(evt);
            }
        });
        FormInput.add(Provokes);
        Provokes.setBounds(574, 820, 130, 23);

        KetProvokes.setFocusTraversalPolicyProvider(true);
        KetProvokes.setName("KetProvokes"); // NOI18N
        KetProvokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProvokesKeyPressed(evt);
            }
        });
        FormInput.add(KetProvokes);
        KetProvokes.setBounds(708, 820, 146, 23);

        jLabel80.setText("Penyebab :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(510, 820, 60, 23);

        Quality.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seperti Tertusuk", "Berdenyut", "Teriris", "Tertindih", "Tertiban", "Lain-lain" }));
        Quality.setName("Quality"); // NOI18N
        Quality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QualityKeyPressed(evt);
            }
        });
        FormInput.add(Quality);
        Quality.setBounds(429, 850, 140, 23);

        KetQuality.setFocusTraversalPolicyProvider(true);
        KetQuality.setName("KetQuality"); // NOI18N
        KetQuality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetQualityKeyPressed(evt);
            }
        });
        FormInput.add(KetQuality);
        KetQuality.setBounds(573, 850, 280, 23);

        jLabel81.setText("Kualitas :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(370, 850, 55, 23);

        jLabel82.setText("Wilayah :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(370, 880, 55, 23);

        Lokasi.setFocusTraversalPolicyProvider(true);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(458, 900, 220, 23);

        jLabel83.setText("Lokasi :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(394, 900, 60, 23);

        Menyebar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Menyebar.setName("Menyebar"); // NOI18N
        Menyebar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyebarKeyPressed(evt);
            }
        });
        FormInput.add(Menyebar);
        Menyebar.setBounds(773, 900, 80, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Menit");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(815, 930, 35, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setText("Skala Nyeri");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(428, 930, 60, 23);

        SkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(491, 930, 70, 23);

        jLabel86.setText("Diberitahukan pada dokter ?");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(480, 970, 150, 23);

        Durasi.setFocusTraversalPolicyProvider(true);
        Durasi.setName("Durasi"); // NOI18N
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });
        FormInput.add(Durasi);
        Durasi.setBounds(721, 930, 90, 23);

        jLabel87.setText("Waktu / Durasi :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(627, 930, 90, 23);

        jLabel88.setText("Severity :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(370, 930, 55, 23);

        NyeriHilang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Istirahat", "Medengar Musik", "Minum Obat" }));
        NyeriHilang.setName("NyeriHilang"); // NOI18N
        NyeriHilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriHilangKeyPressed(evt);
            }
        });
        FormInput.add(NyeriHilang);
        NyeriHilang.setBounds(134, 970, 130, 23);

        KetNyeri.setFocusTraversalPolicyProvider(true);
        KetNyeri.setName("KetNyeri"); // NOI18N
        KetNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNyeriKeyPressed(evt);
            }
        });
        FormInput.add(KetNyeri);
        KetNyeri.setBounds(268, 970, 150, 23);

        jLabel89.setText("Nyeri hilang bila :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 970, 130, 23);

        PadaDokter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PadaDokter.setName("PadaDokter"); // NOI18N
        PadaDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PadaDokterKeyPressed(evt);
            }
        });
        FormInput.add(PadaDokter);
        PadaDokter.setBounds(634, 970, 80, 23);

        KetDokter.setFocusTraversalPolicyProvider(true);
        KetDokter.setName("KetDokter"); // NOI18N
        KetDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetDokterKeyPressed(evt);
            }
        });
        FormInput.add(KetDokter);
        KetDokter.setBounds(774, 970, 80, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Rencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Rencana.setColumns(20);
        Rencana.setRows(5);
        Rencana.setName("Rencana"); // NOI18N
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Rencana);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(460, 2070, 400, 143);

        jLabel92.setText("Nilai :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(695, 710, 75, 23);

        TotalHasil.setEditable(false);
        TotalHasil.setText("0");
        TotalHasil.setFocusTraversalPolicyProvider(true);
        TotalHasil.setName("TotalHasil"); // NOI18N
        TotalHasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalHasilKeyPressed(evt);
            }
        });
        FormInput.add(TotalHasil);
        TotalHasil.setBounds(774, 770, 80, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-06-2023 05:14:08" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(456, 40, 130, 23);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(700, 90, 90, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(794, 90, 60, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("II. STATUS NUTRISI");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(10, 120, 180, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. RIWAYAT KESEHATAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 170, 180, 23);

        jLabel51.setText("Cacat Fisik :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 370, 120, 23);

        CacatFisik.setEditable(false);
        CacatFisik.setFocusTraversalPolicyProvider(true);
        CacatFisik.setName("CacatFisik"); // NOI18N
        CacatFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CacatFisikKeyPressed(evt);
            }
        });
        FormInput.add(CacatFisik);
        CacatFisik.setBounds(124, 370, 314, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. FUNGSIONAL");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 320, 230, 23);

        jLabel62.setText("Status Psikologis :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 420, 130, 23);

        jLabel95.setText("Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(0, 500, 366, 23);

        StatusBudaya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        StatusBudaya.setName("StatusBudaya"); // NOI18N
        StatusBudaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusBudayaKeyPressed(evt);
            }
        });
        FormInput.add(StatusBudaya);
        StatusBudaya.setBounds(370, 500, 110, 23);

        KetBudaya.setFocusTraversalPolicyProvider(true);
        KetBudaya.setName("KetBudaya"); // NOI18N
        KetBudaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBudayaKeyPressed(evt);
            }
        });
        FormInput.add(KetBudaya);
        KetBudaya.setBounds(484, 500, 370, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("V. RIWAYAT PSIKO-SOSIAL, SPIRITUAL DAN BUDAYA");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(10, 400, 380, 23);

        jLabel97.setText("Dilaporkan kepada dokter ?");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(381, 660, 190, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("VI. PENILAIAN RESIKO JATUH");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(10, 560, 380, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 120, 880, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 170, 880, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 320, 880, 1);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 400, 880, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 560, 880, 1);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 690, 880, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 800, 880, 1);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("VII. SKRINING GIZI");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(10, 690, 380, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(40, 820, 320, 130);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(365, 815, 1, 140);

        jLabel71.setText("Jam dilaporkan :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(680, 660, 90, 23);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 1000, 880, 1);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbMasalahKeperawatan.setName("tbMasalahKeperawatan"); // NOI18N
        Scroll6.setViewportView(tbMasalahKeperawatan);

        FormInput.add(Scroll6);
        Scroll6.setBounds(20, 2050, 400, 133);

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label12);
        label12.setBounds(30, 2190, 60, 23);

        TCariMasalah.setToolTipText("Alt+C");
        TCariMasalah.setName("TCariMasalah"); // NOI18N
        TCariMasalah.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(TCariMasalah);
        TCariMasalah.setBounds(90, 2190, 245, 23);

        BtnCariPemeriksaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPemeriksaan1.setMnemonic('1');
        BtnCariPemeriksaan1.setToolTipText("Alt+1");
        BtnCariPemeriksaan1.setName("BtnCariPemeriksaan1"); // NOI18N
        BtnCariPemeriksaan1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPemeriksaan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPemeriksaan1ActionPerformed(evt);
            }
        });
        BtnCariPemeriksaan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariPemeriksaan1KeyPressed(evt);
            }
        });
        FormInput.add(BtnCariPemeriksaan1);
        BtnCariPemeriksaan1.setBounds(340, 2190, 28, 23);

        BtnTambahMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahMasalah.setMnemonic('3');
        BtnTambahMasalah.setToolTipText("Alt+3");
        BtnTambahMasalah.setName("BtnTambahMasalah"); // NOI18N
        BtnTambahMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahMasalahActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahMasalah);
        BtnTambahMasalah.setBounds(370, 2190, 28, 23);

        Bahasa.setEditable(false);
        Bahasa.setFocusTraversalPolicyProvider(true);
        Bahasa.setName("Bahasa"); // NOI18N
        Bahasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BahasaKeyPressed(evt);
            }
        });
        FormInput.add(Bahasa);
        Bahasa.setBounds(684, 420, 170, 23);

        jLabel76.setText("Bahasa yang digunakan sehari-hari :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(450, 420, 230, 23);

        jLabel77.setText("Agama :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 530, 82, 23);

        Agama.setEditable(false);
        Agama.setFocusTraversalPolicyProvider(true);
        Agama.setName("Agama"); // NOI18N
        Agama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgamaKeyPressed(evt);
            }
        });
        FormInput.add(Agama);
        Agama.setBounds(86, 530, 110, 23);

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
        FormInput.add(BtnDokter1);
        BtnDokter1.setBounds(430, 2070, 28, 23);

        jLabel170.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel170.setText("Morse Fall Scale");
        jLabel170.setName("jLabel170"); // NOI18N
        FormInput.add(jLabel170);
        jLabel170.setBounds(30, 1040, 180, 23);

        jLabel181.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel181.setText("1. Riwayat Jatuh");
        jLabel181.setName("jLabel181"); // NOI18N
        FormInput.add(jLabel181);
        jLabel181.setBounds(30, 1080, 300, 23);

        SkalaResiko1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak", "Ya" }));
        SkalaResiko1.setName("SkalaResiko1"); // NOI18N
        SkalaResiko1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko1ItemStateChanged(evt);
            }
        });
        SkalaResiko1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkalaResiko1ActionPerformed(evt);
            }
        });
        SkalaResiko1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko1KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko1);
        SkalaResiko1.setBounds(410, 1080, 280, 23);

        jLabel182.setText("Nilai :");
        jLabel182.setName("jLabel182"); // NOI18N
        FormInput.add(jLabel182);
        jLabel182.setBounds(700, 1080, 75, 23);

        NilaiResiko1.setEditable(false);
        NilaiResiko1.setFocusTraversalPolicyProvider(true);
        NilaiResiko1.setName("NilaiResiko1"); // NOI18N
        FormInput.add(NilaiResiko1);
        NilaiResiko1.setBounds(780, 1080, 60, 23);

        jLabel183.setText("Skala :");
        jLabel183.setName("jLabel183"); // NOI18N
        FormInput.add(jLabel183);
        jLabel183.setBounds(330, 1080, 80, 23);

        jLabel184.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel184.setText("2. Diagnosis Sekunder (≥ 2 Diagnosis Medis)");
        jLabel184.setName("jLabel184"); // NOI18N
        FormInput.add(jLabel184);
        jLabel184.setBounds(30, 1110, 300, 23);

        jLabel185.setText("Skala :");
        jLabel185.setName("jLabel185"); // NOI18N
        FormInput.add(jLabel185);
        jLabel185.setBounds(330, 1110, 80, 23);

        SkalaResiko2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak", "Ya" }));
        SkalaResiko2.setName("SkalaResiko2"); // NOI18N
        SkalaResiko2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko2ItemStateChanged(evt);
            }
        });
        SkalaResiko2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko2KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko2);
        SkalaResiko2.setBounds(410, 1110, 280, 23);

        NilaiResiko2.setEditable(false);
        NilaiResiko2.setFocusTraversalPolicyProvider(true);
        NilaiResiko2.setName("NilaiResiko2"); // NOI18N
        FormInput.add(NilaiResiko2);
        NilaiResiko2.setBounds(780, 1110, 60, 23);

        jLabel186.setText("Nilai :");
        jLabel186.setName("jLabel186"); // NOI18N
        FormInput.add(jLabel186);
        jLabel186.setBounds(700, 1110, 75, 23);

        jLabel187.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel187.setText("3. Alat Bantu");
        jLabel187.setName("jLabel187"); // NOI18N
        FormInput.add(jLabel187);
        jLabel187.setBounds(30, 1140, 300, 23);

        jLabel188.setText("Skala :");
        jLabel188.setName("jLabel188"); // NOI18N
        FormInput.add(jLabel188);
        jLabel188.setBounds(330, 1140, 80, 23);

        SkalaResiko3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Ada/Kursi Roda/Perawat/Tirah Baring", "Tongkat/Alat Penopang", "Berpegangan Pada Perabot" }));
        SkalaResiko3.setName("SkalaResiko3"); // NOI18N
        SkalaResiko3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko3ItemStateChanged(evt);
            }
        });
        SkalaResiko3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko3KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko3);
        SkalaResiko3.setBounds(410, 1140, 280, 23);

        jLabel189.setText("Nilai :");
        jLabel189.setName("jLabel189"); // NOI18N
        FormInput.add(jLabel189);
        jLabel189.setBounds(700, 1140, 75, 23);

        NilaiResiko3.setEditable(false);
        NilaiResiko3.setFocusTraversalPolicyProvider(true);
        NilaiResiko3.setName("NilaiResiko3"); // NOI18N
        FormInput.add(NilaiResiko3);
        NilaiResiko3.setBounds(780, 1140, 60, 23);

        jLabel190.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel190.setText("4. Terpasang Infuse");
        jLabel190.setName("jLabel190"); // NOI18N
        FormInput.add(jLabel190);
        jLabel190.setBounds(30, 1170, 300, 23);

        jLabel191.setText("Skala :");
        jLabel191.setName("jLabel191"); // NOI18N
        FormInput.add(jLabel191);
        jLabel191.setBounds(330, 1170, 80, 23);

        SkalaResiko4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak", "Ya" }));
        SkalaResiko4.setName("SkalaResiko4"); // NOI18N
        SkalaResiko4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko4ItemStateChanged(evt);
            }
        });
        SkalaResiko4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko4KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko4);
        SkalaResiko4.setBounds(410, 1170, 280, 23);

        jLabel192.setText("Nilai :");
        jLabel192.setName("jLabel192"); // NOI18N
        FormInput.add(jLabel192);
        jLabel192.setBounds(700, 1170, 75, 23);

        NilaiResiko4.setEditable(false);
        NilaiResiko4.setFocusTraversalPolicyProvider(true);
        NilaiResiko4.setName("NilaiResiko4"); // NOI18N
        FormInput.add(NilaiResiko4);
        NilaiResiko4.setBounds(780, 1170, 60, 23);

        jLabel193.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel193.setText("5. Gaya Berjalan");
        jLabel193.setName("jLabel193"); // NOI18N
        FormInput.add(jLabel193);
        jLabel193.setBounds(30, 1200, 300, 23);

        jLabel194.setText("Skala :");
        jLabel194.setName("jLabel194"); // NOI18N
        FormInput.add(jLabel194);
        jLabel194.setBounds(330, 1200, 80, 23);

        SkalaResiko5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Normal/Tirah Baring/Imobilisasi", "Lemah", "Terganggu" }));
        SkalaResiko5.setName("SkalaResiko5"); // NOI18N
        SkalaResiko5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko5ItemStateChanged(evt);
            }
        });
        SkalaResiko5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko5KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko5);
        SkalaResiko5.setBounds(410, 1200, 280, 23);

        jLabel195.setText("Nilai :");
        jLabel195.setName("jLabel195"); // NOI18N
        FormInput.add(jLabel195);
        jLabel195.setBounds(700, 1200, 75, 23);

        NilaiResiko5.setEditable(false);
        NilaiResiko5.setFocusTraversalPolicyProvider(true);
        NilaiResiko5.setName("NilaiResiko5"); // NOI18N
        FormInput.add(NilaiResiko5);
        NilaiResiko5.setBounds(780, 1200, 60, 23);

        jLabel196.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel196.setText("6. Status Mental");
        jLabel196.setName("jLabel196"); // NOI18N
        FormInput.add(jLabel196);
        jLabel196.setBounds(30, 1230, 300, 23);

        jLabel197.setText("Skala :");
        jLabel197.setName("jLabel197"); // NOI18N
        FormInput.add(jLabel197);
        jLabel197.setBounds(330, 1230, 80, 23);

        SkalaResiko6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Sadar Akan Kemampuan Diri Sendiri", "Sering Lupa Akan Keterbatasan Yang Dimiliki" }));
        SkalaResiko6.setName("SkalaResiko6"); // NOI18N
        SkalaResiko6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko6ItemStateChanged(evt);
            }
        });
        SkalaResiko6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko6KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko6);
        SkalaResiko6.setBounds(410, 1230, 280, 23);

        jLabel198.setText("Nilai :");
        jLabel198.setName("jLabel198"); // NOI18N
        FormInput.add(jLabel198);
        jLabel198.setBounds(700, 1230, 75, 23);

        NilaiResiko6.setEditable(false);
        NilaiResiko6.setFocusTraversalPolicyProvider(true);
        NilaiResiko6.setName("NilaiResiko6"); // NOI18N
        FormInput.add(NilaiResiko6);
        NilaiResiko6.setBounds(780, 1230, 60, 23);

        jLabel199.setText("Total :");
        jLabel199.setName("jLabel199"); // NOI18N
        FormInput.add(jLabel199);
        jLabel199.setBounds(700, 1260, 75, 23);

        NilaiResikoTotal.setEditable(false);
        NilaiResikoTotal.setFocusTraversalPolicyProvider(true);
        NilaiResikoTotal.setName("NilaiResikoTotal"); // NOI18N
        FormInput.add(NilaiResikoTotal);
        NilaiResikoTotal.setBounds(780, 1260, 60, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 1360, 880, 1);

        jLabel171.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel171.setText("Humpty Dumpty Scale ");
        jLabel171.setName("jLabel171"); // NOI18N
        FormInput.add(jLabel171);
        jLabel171.setBounds(30, 1370, 180, 23);

        jLabel200.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel200.setText("1. Umur");
        jLabel200.setName("jLabel200"); // NOI18N
        FormInput.add(jLabel200);
        jLabel200.setBounds(30, 1410, 300, 23);

        SkalaHumpty1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Dibawah 3 Tahun", "3-7 Tahun", "7-13 Tahun", ">13 Tahun" }));
        SkalaHumpty1.setName("SkalaHumpty1"); // NOI18N
        SkalaHumpty1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaHumpty1ItemStateChanged(evt);
            }
        });
        SkalaHumpty1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaHumpty1KeyPressed(evt);
            }
        });
        FormInput.add(SkalaHumpty1);
        SkalaHumpty1.setBounds(410, 1410, 280, 23);

        jLabel201.setText("Nilai :");
        jLabel201.setName("jLabel201"); // NOI18N
        FormInput.add(jLabel201);
        jLabel201.setBounds(700, 1410, 75, 23);

        NilaiHumpty1.setEditable(false);
        NilaiHumpty1.setFocusTraversalPolicyProvider(true);
        NilaiHumpty1.setName("NilaiHumpty1"); // NOI18N
        FormInput.add(NilaiHumpty1);
        NilaiHumpty1.setBounds(780, 1410, 60, 23);

        jLabel202.setText("Skala :");
        jLabel202.setName("jLabel202"); // NOI18N
        FormInput.add(jLabel202);
        jLabel202.setBounds(330, 1410, 80, 23);

        jLabel203.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel203.setText("2. Jenis Kelamin");
        jLabel203.setName("jLabel203"); // NOI18N
        FormInput.add(jLabel203);
        jLabel203.setBounds(30, 1440, 300, 23);

        jLabel204.setText("Skala :");
        jLabel204.setName("jLabel204"); // NOI18N
        FormInput.add(jLabel204);
        jLabel204.setBounds(330, 1440, 80, 23);

        SkalaHumpty2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Perempuan", "Laki-laki" }));
        SkalaHumpty2.setName("SkalaHumpty2"); // NOI18N
        SkalaHumpty2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaHumpty2ItemStateChanged(evt);
            }
        });
        SkalaHumpty2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaHumpty2KeyPressed(evt);
            }
        });
        FormInput.add(SkalaHumpty2);
        SkalaHumpty2.setBounds(410, 1440, 280, 23);

        NilaiHumpty2.setEditable(false);
        NilaiHumpty2.setFocusTraversalPolicyProvider(true);
        NilaiHumpty2.setName("NilaiHumpty2"); // NOI18N
        FormInput.add(NilaiHumpty2);
        NilaiHumpty2.setBounds(780, 1440, 60, 23);

        jLabel205.setText("Nilai :");
        jLabel205.setName("jLabel205"); // NOI18N
        FormInput.add(jLabel205);
        jLabel205.setBounds(700, 1440, 75, 23);

        jLabel206.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel206.setText("3. Diagnosa");
        jLabel206.setName("jLabel206"); // NOI18N
        FormInput.add(jLabel206);
        jLabel206.setBounds(30, 1470, 300, 23);

        jLabel207.setText("Skala :");
        jLabel207.setName("jLabel207"); // NOI18N
        FormInput.add(jLabel207);
        jLabel207.setBounds(330, 1470, 80, 23);

        SkalaHumpty3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Kelainan Neurologi", "Perubahan Dalam Oksigenasi (Masalah saluran nafas/Dehidrasi/Anemia/Anoreksia/Sinkop/Sakit kepala/Dll)", "Kelainan Psikis/Perilaku", "Diagnosa Lain" }));
        SkalaHumpty3.setName("SkalaHumpty3"); // NOI18N
        SkalaHumpty3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaHumpty3ItemStateChanged(evt);
            }
        });
        SkalaHumpty3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaHumpty3KeyPressed(evt);
            }
        });
        FormInput.add(SkalaHumpty3);
        SkalaHumpty3.setBounds(410, 1470, 280, 23);

        jLabel208.setText("Nilai :");
        jLabel208.setName("jLabel208"); // NOI18N
        FormInput.add(jLabel208);
        jLabel208.setBounds(700, 1470, 75, 23);

        NilaiHumpty3.setEditable(false);
        NilaiHumpty3.setFocusTraversalPolicyProvider(true);
        NilaiHumpty3.setName("NilaiHumpty3"); // NOI18N
        FormInput.add(NilaiHumpty3);
        NilaiHumpty3.setBounds(780, 1470, 60, 23);

        jLabel209.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel209.setText("4. Gangguan Kognitif");
        jLabel209.setName("jLabel209"); // NOI18N
        FormInput.add(jLabel209);
        jLabel209.setBounds(30, 1500, 300, 23);

        jLabel210.setText("Skala :");
        jLabel210.setName("jLabel210"); // NOI18N
        FormInput.add(jLabel210);
        jLabel210.setBounds(330, 1500, 80, 23);

        SkalaHumpty4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Sadar Terhadap Keterbatasan", "Lupa Keterbatasan", "Mengetahui Kemampuan Diri" }));
        SkalaHumpty4.setName("SkalaHumpty4"); // NOI18N
        SkalaHumpty4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaHumpty4ItemStateChanged(evt);
            }
        });
        SkalaHumpty4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaHumpty4KeyPressed(evt);
            }
        });
        FormInput.add(SkalaHumpty4);
        SkalaHumpty4.setBounds(410, 1500, 280, 23);

        jLabel211.setText("Nilai :");
        jLabel211.setName("jLabel211"); // NOI18N
        FormInput.add(jLabel211);
        jLabel211.setBounds(700, 1500, 75, 23);

        NilaiHumpty4.setEditable(false);
        NilaiHumpty4.setFocusTraversalPolicyProvider(true);
        NilaiHumpty4.setName("NilaiHumpty4"); // NOI18N
        FormInput.add(NilaiHumpty4);
        NilaiHumpty4.setBounds(780, 1500, 60, 23);

        jLabel212.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel212.setText("5. Faktor Lingkungan");
        jLabel212.setName("jLabel212"); // NOI18N
        FormInput.add(jLabel212);
        jLabel212.setBounds(30, 1530, 300, 23);

        jLabel213.setText("Skala :");
        jLabel213.setName("jLabel213"); // NOI18N
        FormInput.add(jLabel213);
        jLabel213.setBounds(330, 1530, 80, 23);

        SkalaHumpty5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Riwayat Jatuh Dari Tempat Tidur Saat Bayi", "Pasien Menggunakan Alat Bantu/Box/Mebel", "Pasien Berada Ditempat Tidur", "Diluar Ruang Rawat" }));
        SkalaHumpty5.setName("SkalaHumpty5"); // NOI18N
        SkalaHumpty5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaHumpty5ItemStateChanged(evt);
            }
        });
        SkalaHumpty5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaHumpty5KeyPressed(evt);
            }
        });
        FormInput.add(SkalaHumpty5);
        SkalaHumpty5.setBounds(410, 1530, 280, 23);

        jLabel214.setText("Nilai :");
        jLabel214.setName("jLabel214"); // NOI18N
        FormInput.add(jLabel214);
        jLabel214.setBounds(700, 1530, 75, 23);

        NilaiHumpty5.setEditable(false);
        NilaiHumpty5.setFocusTraversalPolicyProvider(true);
        NilaiHumpty5.setName("NilaiHumpty5"); // NOI18N
        FormInput.add(NilaiHumpty5);
        NilaiHumpty5.setBounds(780, 1530, 60, 23);

        jLabel215.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel215.setText("6. Pembedahan/Sedasi/Anestesi");
        jLabel215.setName("jLabel215"); // NOI18N
        FormInput.add(jLabel215);
        jLabel215.setBounds(30, 1560, 300, 23);

        jLabel216.setText("Skala :");
        jLabel216.setName("jLabel216"); // NOI18N
        FormInput.add(jLabel216);
        jLabel216.setBounds(330, 1560, 80, 23);

        SkalaHumpty6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Bermacam-macam yang digunakan : Obat sedatif (Kecuali pasien ICU yang menggunakan sedasi dan paralisis)/Hipnosis/Barbiturat/Fenotiazin/Antidepresan/Pencahar/Diuretik/Narkotika", "Salah Satu Pengobatan Diatas", "Pengobatan Lain" }));
        SkalaHumpty6.setName("SkalaHumpty6"); // NOI18N
        SkalaHumpty6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaHumpty6ItemStateChanged(evt);
            }
        });
        SkalaHumpty6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaHumpty6KeyPressed(evt);
            }
        });
        FormInput.add(SkalaHumpty6);
        SkalaHumpty6.setBounds(410, 1560, 280, 23);

        jLabel217.setText("Nilai :");
        jLabel217.setName("jLabel217"); // NOI18N
        FormInput.add(jLabel217);
        jLabel217.setBounds(700, 1560, 75, 23);

        NilaiHumpty6.setEditable(false);
        NilaiHumpty6.setFocusTraversalPolicyProvider(true);
        NilaiHumpty6.setName("NilaiHumpty6"); // NOI18N
        FormInput.add(NilaiHumpty6);
        NilaiHumpty6.setBounds(780, 1560, 60, 23);

        jLabel218.setText("Total :");
        jLabel218.setName("jLabel218"); // NOI18N
        FormInput.add(jLabel218);
        jLabel218.setBounds(700, 1590, 75, 23);

        NilaiResikoHumptyTotal.setEditable(false);
        NilaiResikoHumptyTotal.setFocusTraversalPolicyProvider(true);
        NilaiResikoHumptyTotal.setName("NilaiResikoHumptyTotal"); // NOI18N
        FormInput.add(NilaiResikoHumptyTotal);
        NilaiResikoHumptyTotal.setBounds(780, 1590, 60, 23);

        TingkatResikoHumpty.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResikoHumpty.setText("Keterangan : Risiko Rendah (7-11)");
        TingkatResikoHumpty.setName("TingkatResikoHumpty"); // NOI18N
        FormInput.add(TingkatResikoHumpty);
        TingkatResikoHumpty.setBounds(30, 1620, 810, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 1650, 880, 1);

        jLabel172.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel172.setText("Edmonson Psychiatric Fall Risk Assessment");
        jLabel172.setName("jLabel172"); // NOI18N
        FormInput.add(jLabel172);
        jLabel172.setBounds(20, 1670, 220, 23);

        jLabel219.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel219.setText("1. Usia");
        jLabel219.setName("jLabel219"); // NOI18N
        FormInput.add(jLabel219);
        jLabel219.setBounds(30, 1710, 300, 23);

        SkalaEdmonson1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "< 50 Tahun", "50-70 Tahun", ">80 Tahun" }));
        SkalaEdmonson1.setName("SkalaEdmonson1"); // NOI18N
        SkalaEdmonson1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaEdmonson1ItemStateChanged(evt);
            }
        });
        SkalaEdmonson1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaEdmonson1KeyPressed(evt);
            }
        });
        FormInput.add(SkalaEdmonson1);
        SkalaEdmonson1.setBounds(410, 1710, 280, 23);

        jLabel220.setText("Keterangan :");
        jLabel220.setName("jLabel220"); // NOI18N
        FormInput.add(jLabel220);
        jLabel220.setBounds(840, 1670, 75, 23);

        NilaiEdmonson1.setEditable(false);
        NilaiEdmonson1.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson1.setName("NilaiEdmonson1"); // NOI18N
        FormInput.add(NilaiEdmonson1);
        NilaiEdmonson1.setBounds(780, 1710, 60, 23);

        jLabel221.setText("Skala :");
        jLabel221.setName("jLabel221"); // NOI18N
        FormInput.add(jLabel221);
        jLabel221.setBounds(330, 1710, 80, 23);

        jLabel222.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel222.setText("2. Status Mental");
        jLabel222.setName("jLabel222"); // NOI18N
        FormInput.add(jLabel222);
        jLabel222.setBounds(30, 1740, 300, 23);

        jLabel223.setText("Skala :");
        jLabel223.setName("jLabel223"); // NOI18N
        FormInput.add(jLabel223);
        jLabel223.setBounds(330, 1740, 80, 23);

        SkalaEdmonson2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Kesadaran baik/Orientasi baik setiap", "Agitasi/Ansietas", "Kadang-kadang bingung", "Bingung / Disorientasi" }));
        SkalaEdmonson2.setName("SkalaEdmonson2"); // NOI18N
        SkalaEdmonson2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaEdmonson2ItemStateChanged(evt);
            }
        });
        SkalaEdmonson2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaEdmonson2KeyPressed(evt);
            }
        });
        FormInput.add(SkalaEdmonson2);
        SkalaEdmonson2.setBounds(410, 1740, 280, 23);

        NilaiEdmonson2.setEditable(false);
        NilaiEdmonson2.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson2.setName("NilaiEdmonson2"); // NOI18N
        FormInput.add(NilaiEdmonson2);
        NilaiEdmonson2.setBounds(780, 1740, 60, 23);

        jLabel224.setText("Nilai :");
        jLabel224.setName("jLabel224"); // NOI18N
        FormInput.add(jLabel224);
        jLabel224.setBounds(700, 1740, 75, 23);

        jLabel225.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel225.setText("3. Kliminasi");
        jLabel225.setName("jLabel225"); // NOI18N
        FormInput.add(jLabel225);
        jLabel225.setBounds(30, 1770, 300, 23);

        jLabel226.setText("Skala :");
        jLabel226.setName("jLabel226"); // NOI18N
        FormInput.add(jLabel226);
        jLabel226.setBounds(330, 1770, 80, 23);

        SkalaEdmonson3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Mandiri dan mampu mengontrol BAB/BAK", "Dower catheter/Colostomy", "Eliminasi dengan bantuan", "Gangguan eliminasi (Inkontinensia/Nukturia/Frekuensi)", "Inkontinensia tetapi mampu untuk mobilisasi" }));
        SkalaEdmonson3.setName("SkalaEdmonson3"); // NOI18N
        SkalaEdmonson3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaEdmonson3ItemStateChanged(evt);
            }
        });
        SkalaEdmonson3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaEdmonson3KeyPressed(evt);
            }
        });
        FormInput.add(SkalaEdmonson3);
        SkalaEdmonson3.setBounds(410, 1770, 280, 23);

        jLabel227.setText("Nilai :");
        jLabel227.setName("jLabel227"); // NOI18N
        FormInput.add(jLabel227);
        jLabel227.setBounds(700, 1770, 75, 23);

        NilaiEdmonson3.setEditable(false);
        NilaiEdmonson3.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson3.setName("NilaiEdmonson3"); // NOI18N
        FormInput.add(NilaiEdmonson3);
        NilaiEdmonson3.setBounds(780, 1770, 60, 23);

        jLabel228.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel228.setText("4. Pengobatan");
        jLabel228.setName("jLabel228"); // NOI18N
        FormInput.add(jLabel228);
        jLabel228.setBounds(30, 1800, 300, 23);

        jLabel229.setText("Skala :");
        jLabel229.setName("jLabel229"); // NOI18N
        FormInput.add(jLabel229);
        jLabel229.setBounds(330, 1800, 80, 23);

        SkalaEdmonson4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tanpa obat-obatan", "Obat-obatan jantung", "Obat-obat Psikotropika (termasuk Benzodiazepine dan Antidepresan)", "Mendapat tambahan obat-obatan dan / atau obat-obat PRN (psikiatri/anti nyeri) yang diberikan dalam 24 jam terakhir" }));
        SkalaEdmonson4.setName("SkalaEdmonson4"); // NOI18N
        SkalaEdmonson4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaEdmonson4ItemStateChanged(evt);
            }
        });
        SkalaEdmonson4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaEdmonson4KeyPressed(evt);
            }
        });
        FormInput.add(SkalaEdmonson4);
        SkalaEdmonson4.setBounds(410, 1800, 280, 23);

        jLabel230.setText("Nilai :");
        jLabel230.setName("jLabel230"); // NOI18N
        FormInput.add(jLabel230);
        jLabel230.setBounds(700, 1800, 75, 23);

        NilaiEdmonson4.setEditable(false);
        NilaiEdmonson4.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson4.setName("NilaiEdmonson4"); // NOI18N
        FormInput.add(NilaiEdmonson4);
        NilaiEdmonson4.setBounds(780, 1800, 60, 23);

        jLabel231.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel231.setText("5. Diagnosa");
        jLabel231.setName("jLabel231"); // NOI18N
        FormInput.add(jLabel231);
        jLabel231.setBounds(30, 1830, 300, 23);

        jLabel232.setText("Skala :");
        jLabel232.setName("jLabel232"); // NOI18N
        FormInput.add(jLabel232);
        jLabel232.setBounds(330, 1830, 80, 23);

        SkalaEdmonson5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Bipolar / Gangguan Schizoaffective", "Penggunaan obat-obatan terlarang/ketergantungan alcohol", "Gangguan depresi mayor", "Dimensia / delirium" }));
        SkalaEdmonson5.setName("SkalaEdmonson5"); // NOI18N
        SkalaEdmonson5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaEdmonson5ItemStateChanged(evt);
            }
        });
        SkalaEdmonson5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaEdmonson5KeyPressed(evt);
            }
        });
        FormInput.add(SkalaEdmonson5);
        SkalaEdmonson5.setBounds(410, 1830, 280, 23);

        jLabel233.setText("Nilai :");
        jLabel233.setName("jLabel233"); // NOI18N
        FormInput.add(jLabel233);
        jLabel233.setBounds(700, 1830, 75, 23);

        NilaiEdmonson5.setEditable(false);
        NilaiEdmonson5.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson5.setName("NilaiEdmonson5"); // NOI18N
        FormInput.add(NilaiEdmonson5);
        NilaiEdmonson5.setBounds(780, 1830, 60, 23);

        jLabel234.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel234.setText("6. Ambulasi / Keseimbangan");
        jLabel234.setName("jLabel234"); // NOI18N
        FormInput.add(jLabel234);
        jLabel234.setBounds(30, 1860, 300, 23);

        jLabel235.setText("Skala :");
        jLabel235.setName("jLabel235"); // NOI18N
        FormInput.add(jLabel235);
        jLabel235.setBounds(330, 1860, 80, 23);

        SkalaEdmonson6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Mandiri/keseimbangan baik/Immobilisasi", "Dengan alat bantu (kursi roda/walker/dll)", "Vertigo/Kelemahan", "Goyah/membutuhkan bantuan dan menyadari kemampuan", "Goyah tapi lupa keterbatasan" }));
        SkalaEdmonson6.setName("SkalaEdmonson6"); // NOI18N
        SkalaEdmonson6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaEdmonson6ItemStateChanged(evt);
            }
        });
        SkalaEdmonson6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaEdmonson6KeyPressed(evt);
            }
        });
        FormInput.add(SkalaEdmonson6);
        SkalaEdmonson6.setBounds(410, 1860, 280, 23);

        jLabel236.setText("Nilai :");
        jLabel236.setName("jLabel236"); // NOI18N
        FormInput.add(jLabel236);
        jLabel236.setBounds(700, 1860, 75, 23);

        NilaiEdmonson6.setEditable(false);
        NilaiEdmonson6.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson6.setName("NilaiEdmonson6"); // NOI18N
        FormInput.add(NilaiEdmonson6);
        NilaiEdmonson6.setBounds(780, 1860, 60, 23);

        jLabel237.setText("Total :");
        jLabel237.setName("jLabel237"); // NOI18N
        FormInput.add(jLabel237);
        jLabel237.setBounds(700, 1980, 75, 23);

        NilaiResikoEdmonsonTotal.setEditable(false);
        NilaiResikoEdmonsonTotal.setFocusTraversalPolicyProvider(true);
        NilaiResikoEdmonsonTotal.setName("NilaiResikoEdmonsonTotal"); // NOI18N
        FormInput.add(NilaiResikoEdmonsonTotal);
        NilaiResikoEdmonsonTotal.setBounds(780, 1980, 60, 23);

        jLabel238.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel238.setText("7. Nutrisi");
        jLabel238.setName("jLabel238"); // NOI18N
        FormInput.add(jLabel238);
        jLabel238.setBounds(30, 1890, 300, 23);

        jLabel239.setText("Skala :");
        jLabel239.setName("jLabel239"); // NOI18N
        FormInput.add(jLabel239);
        jLabel239.setBounds(330, 1890, 80, 23);

        SkalaEdmonson7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Mengkonsumsi sedikit makanan atau minuman dalam 24 jam terakhir", "Tidak ada kelainan dengan nafsu makan" }));
        SkalaEdmonson7.setName("SkalaEdmonson7"); // NOI18N
        SkalaEdmonson7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaEdmonson7ItemStateChanged(evt);
            }
        });
        SkalaEdmonson7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaEdmonson7KeyPressed(evt);
            }
        });
        FormInput.add(SkalaEdmonson7);
        SkalaEdmonson7.setBounds(410, 1890, 280, 23);

        jLabel240.setText("Nilai :");
        jLabel240.setName("jLabel240"); // NOI18N
        FormInput.add(jLabel240);
        jLabel240.setBounds(700, 1890, 75, 23);

        NilaiEdmonson7.setEditable(false);
        NilaiEdmonson7.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson7.setName("NilaiEdmonson7"); // NOI18N
        FormInput.add(NilaiEdmonson7);
        NilaiEdmonson7.setBounds(780, 1890, 60, 23);

        jLabel241.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel241.setText("8. Gangguan Pola Tidur");
        jLabel241.setName("jLabel241"); // NOI18N
        FormInput.add(jLabel241);
        jLabel241.setBounds(30, 1920, 300, 23);

        jLabel242.setText("Skala :");
        jLabel242.setName("jLabel242"); // NOI18N
        FormInput.add(jLabel242);
        jLabel242.setBounds(330, 1920, 80, 23);

        SkalaEdmonson8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak ada gangguan tidur", "Ada keluhan gangguan tidur yang dilaporkan oleh pasien/keluarga atau petugas" }));
        SkalaEdmonson8.setName("SkalaEdmonson8"); // NOI18N
        SkalaEdmonson8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaEdmonson8ItemStateChanged(evt);
            }
        });
        SkalaEdmonson8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaEdmonson8KeyPressed(evt);
            }
        });
        FormInput.add(SkalaEdmonson8);
        SkalaEdmonson8.setBounds(410, 1920, 280, 23);

        jLabel243.setText("Nilai :");
        jLabel243.setName("jLabel243"); // NOI18N
        FormInput.add(jLabel243);
        jLabel243.setBounds(700, 1920, 75, 23);

        NilaiEdmonson8.setEditable(false);
        NilaiEdmonson8.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson8.setName("NilaiEdmonson8"); // NOI18N
        FormInput.add(NilaiEdmonson8);
        NilaiEdmonson8.setBounds(780, 1920, 60, 23);

        jLabel244.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel244.setText("9. Riwayat Jatuh");
        jLabel244.setName("jLabel244"); // NOI18N
        FormInput.add(jLabel244);
        jLabel244.setBounds(30, 1950, 300, 23);

        jLabel245.setText("Skala :");
        jLabel245.setName("jLabel245"); // NOI18N
        FormInput.add(jLabel245);
        jLabel245.setBounds(330, 1950, 80, 23);

        SkalaEdmonson9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak ada riwayat jatuh", "Ada riwayat jatuh dalam 3 bulan terakhir" }));
        SkalaEdmonson9.setName("SkalaEdmonson9"); // NOI18N
        SkalaEdmonson9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaEdmonson9ItemStateChanged(evt);
            }
        });
        SkalaEdmonson9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaEdmonson9KeyPressed(evt);
            }
        });
        FormInput.add(SkalaEdmonson9);
        SkalaEdmonson9.setBounds(410, 1950, 280, 23);

        jLabel246.setText("Nilai :");
        jLabel246.setName("jLabel246"); // NOI18N
        FormInput.add(jLabel246);
        jLabel246.setBounds(700, 1950, 75, 23);

        NilaiEdmonson9.setEditable(false);
        NilaiEdmonson9.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson9.setName("NilaiEdmonson9"); // NOI18N
        FormInput.add(NilaiEdmonson9);
        NilaiEdmonson9.setBounds(780, 1950, 60, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 2040, 880, 1);

        jLabel173.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel173.setText("KAJIAN RISIKO JATUH");
        jLabel173.setName("jLabel173"); // NOI18N
        FormInput.add(jLabel173);
        jLabel173.setBounds(20, 1010, 180, 23);

        TingkatResiko.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko.setText("Tingkat Resiko : Risiko Rendah (0-24), Tindakan : Intervensi pencegahan risiko jatuh standar");
        TingkatResiko.setName("TingkatResiko"); // NOI18N
        FormInput.add(TingkatResiko);
        TingkatResiko.setBounds(40, 1290, 810, 23);

        jLabel262.setText("Nilai :");
        jLabel262.setName("jLabel262"); // NOI18N
        FormInput.add(jLabel262);
        jLabel262.setBounds(700, 1710, 75, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        TingkatResikoEdmonson.setEditable(false);
        TingkatResikoEdmonson.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TingkatResikoEdmonson.setColumns(20);
        TingkatResikoEdmonson.setRows(5);
        TingkatResikoEdmonson.setName("TingkatResikoEdmonson"); // NOI18N
        TingkatResikoEdmonson.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TingkatResikoEdmonsonKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(TingkatResikoEdmonson);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(880, 1700, 550, 290);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
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

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-06-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-06-2023" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
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
        PanelAccor.setPreferredSize(new java.awt.Dimension(470, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
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

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel34.setText("Pasien :");
        jLabel34.setName("jLabel34"); // NOI18N
        jLabel34.setPreferredSize(new java.awt.Dimension(55, 23));
        FormMenu.add(jLabel34);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.setPreferredSize(new java.awt.Dimension(100, 23));
        FormMenu.add(TNoRM1);

        TPasien1.setEditable(false);
        TPasien1.setBackground(new java.awt.Color(245, 250, 240));
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(250, 23));
        FormMenu.add(TPasien1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPrint1);

        PanelAccor.add(FormMenu, java.awt.BorderLayout.NORTH);

        FormMasalahRencana.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        FormMasalahRencana.setName("FormMasalahRencana"); // NOI18N
        FormMasalahRencana.setLayout(new java.awt.GridLayout(2, 0, 1, 1));

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbMasalahDetailMasalah.setName("tbMasalahDetailMasalah"); // NOI18N
        Scroll7.setViewportView(tbMasalahDetailMasalah);

        FormMasalahRencana.add(Scroll7);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)), "Rencana Keperawatan :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane6.setName("scrollPane6"); // NOI18N

        DetailRencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        DetailRencana.setColumns(20);
        DetailRencana.setRows(5);
        DetailRencana.setName("DetailRencana"); // NOI18N
        DetailRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetailRencanaKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(DetailRencana);

        FormMasalahRencana.add(scrollPane6);

        PanelAccor.add(FormMasalahRencana, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD(mmHg)");
        }else if(Nadi.getText().trim().equals("")){
            Valid.textKosong(Nadi,"Nadi(x/menit)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu(C)");
        }else if(GCS.getText().trim().equals("")){
            Valid.textKosong(GCS,"GCS");
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"BB(Kg)");
        }else if(TB.getText().trim().equals("")){
            Valid.textKosong(TB,"TB(Cm)");
        }else if(BMI.getText().trim().equals("")){
            Valid.textKosong(BMI,"BMI(Kg/m2)");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengobatan");
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Alergi");
        }else if(TotalHasil.getText().trim().equals("")){
            Valid.textKosong(TotalHasil,"Total Hasil");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Lokasi");
        }else if(Rencana.getText().trim().equals("")){
            Valid.textKosong(Rencana,"Rencana");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else{
            if(Sequel.menyimpantf("penilaian_awal_keperawatan_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",102,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),
                    Suhu.getText(),GCS.getText(),BB.getText(),TB.getText(),BMI.getText(),KeluhanUtama.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),AlatBantu.getSelectedItem().toString(),KetBantu.getText(), 
                    Prothesa.getSelectedItem().toString(),KetProthesa.getText(),ADL.getSelectedItem().toString(),StatusPsiko.getSelectedItem().toString(),KetPsiko.getText(),HubunganKeluarga.getSelectedItem().toString(), 
                    TinggalDengan.getSelectedItem().toString(),KetTinggal.getText(),Ekonomi.getSelectedItem().toString(),StatusBudaya.getSelectedItem().toString(),KetBudaya.getText(),Edukasi.getSelectedItem().toString(), 
                    KetEdukasi.getText(),ATS.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),Hasil.getSelectedItem().toString(),Lapor.getSelectedItem().toString(),KetLapor.getText(), 
                    SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),TotalHasil.getText(),Nyeri.getSelectedItem().toString(),
                    Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),
                    Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),PadaDokter.getSelectedItem().toString(),KetDokter.getText(),Rencana.getText(),KdPetugas.getText(),SkalaResiko1.getSelectedItem().toString(),NilaiResiko1.getText(),SkalaResiko2.getSelectedItem().toString(),
                    NilaiResiko2.getText(),SkalaResiko3.getSelectedItem().toString(),NilaiResiko3.getText(),SkalaResiko4.getSelectedItem().toString(),NilaiResiko4.getText(),SkalaResiko5.getSelectedItem().toString(),
                    NilaiResiko5.getText(),SkalaResiko6.getSelectedItem().toString(),NilaiResiko6.getText(),NilaiResikoTotal.getText(),SkalaHumpty1.getSelectedItem().toString(),NilaiHumpty1.getText(),
                    SkalaHumpty2.getSelectedItem().toString(),NilaiHumpty2.getText(),SkalaHumpty3.getSelectedItem().toString(),NilaiHumpty3.getText(),SkalaHumpty4.getSelectedItem().toString(),NilaiHumpty4.getText(),
                    SkalaHumpty5.getSelectedItem().toString(),NilaiHumpty5.getText(),SkalaHumpty6.getSelectedItem().toString(),NilaiHumpty6.getText(),NilaiResikoHumptyTotal.getText(),
                    SkalaEdmonson1.getSelectedItem().toString(),NilaiEdmonson1.getText(),SkalaEdmonson2.getSelectedItem().toString(),NilaiEdmonson2.getText(),SkalaEdmonson3.getSelectedItem().toString(),NilaiEdmonson3.getText(),
                    SkalaEdmonson4.getSelectedItem().toString(),NilaiEdmonson4.getText(),SkalaEdmonson5.getSelectedItem().toString(),NilaiEdmonson5.getText(),SkalaEdmonson6.getSelectedItem().toString(),NilaiEdmonson6.getText(),
                    SkalaEdmonson7.getSelectedItem().toString(),NilaiEdmonson7.getText(),SkalaEdmonson8.getSelectedItem().toString(),NilaiEdmonson8.getText(),SkalaEdmonson9.getSelectedItem().toString(),NilaiEdmonson9.getText(),
                    NilaiResikoEdmonsonTotal.getText()
                })==true){
                    for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                        if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                            Sequel.menyimpan2("penilaian_awal_keperawatan_ralan_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()});
                        }
                    }
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Rencana,BtnBatal);
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
            if(Sequel.queryu2tf("delete from penilaian_awal_keperawatan_ralan where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                TNoRM1.setText("");
                TPasien1.setText("");
                Sequel.meghapus("penilaian_awal_keperawatan_ralan_masalah","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                Valid.tabelKosong(tabModeDetailMasalah);
                ChkAccor.setSelected(false);
                isMenu();
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
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD(mmHg)");
        }else if(Nadi.getText().trim().equals("")){
            Valid.textKosong(Nadi,"Nadi(x/menit)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu(C)");
        }else if(GCS.getText().trim().equals("")){
            Valid.textKosong(GCS,"GCS");
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"BB(Kg)");
        }else if(TB.getText().trim().equals("")){
            Valid.textKosong(TB,"TB(Cm)");
        }else if(BMI.getText().trim().equals("")){
            Valid.textKosong(BMI,"BMI(Kg/m2)");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengobatan");
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Alergi");
        }else if(TotalHasil.getText().trim().equals("")){
            Valid.textKosong(TotalHasil,"Total Hasil");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Lokasi");
        }else if(Rencana.getText().trim().equals("")){
            Valid.textKosong(Rencana,"Rencana");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("penilaian_awal_keperawatan_ralan","no_rawat=?","no_rawat=?,tanggal=?,informasi=?,td=?,nadi=?,rr=?,suhu=?,gcs=?,bb=?,tb=?,bmi=?,keluhan_utama=?,rpd=?,rpk=?,rpo=?,alergi=?,alat_bantu=?,ket_bantu=?,prothesa=?,ket_pro=?,adl=?,status_psiko=?,ket_psiko=?,hub_keluarga=?,tinggal_dengan=?,ket_tinggal=?,ekonomi=?,budaya=?,ket_budaya=?,edukasi=?,ket_edukasi=?,berjalan_a=?,berjalan_b=?,berjalan_c=?,hasil=?,lapor=?,ket_lapor=?,sg1=?,nilai1=?,sg2=?,nilai2=?,total_hasil=?,nyeri=?,provokes=?,ket_provokes=?,quality=?,ket_quality=?,lokasi=?,menyebar=?,skala_nyeri=?,durasi=?,nyeri_hilang=?,ket_nyeri=?,pada_dokter=?,ket_dokter=?,rencana=?,nip=?,penilaian_jatuh_morse1=?,penilaian_jatuh_nilai_morse1=?,penilaian_jatuh_morse2=?,penilaian_jatuh_nilai_morse2=?,penilaian_jatuh_morse3=?,penilaian_jatuh_nilai_morse3=?,penilaian_jatuh_morse4=?,penilaian_jatuh_nilai_morse4=?,penilaian_jatuh_morse5=?,penilaian_jatuh_nilai_morse5=?,penilaian_jatuh_morse6=?,penilaian_jatuh_nilai_morse6=?,penilaian_jatuh_totalnilai=?,penilaian_jatuh_humty1=?,penilaian_jatuh_nilai_humty1=?,penilaian_jatuh_humty2=?,penilaian_jatuh_nilai_humty2=?,penilaian_jatuh_humty3=?,penilaian_jatuh_nilai_humty3=?,penilaian_jatuh_humty4=?,penilaian_jatuh_nilai_humty4=?,penilaian_jatuh_humty5=?,penilaian_jatuh_nilai_humty5=?,penilaian_jatuh_humty6=?,penilaian_jatuh_nilai_humty6=?,penilaian_jatuh_totalnilai_humpty=?,penilaian_jatuh_edmonson1=?,penilaian_jatuh_nilai_edmonson1=?,penilaian_jatuh_edmonson2=?,penilaian_jatuh_nilai_edmonson2=?,penilaian_jatuh_edmonson3=?,penilaian_jatuh_nilai_edmonson3=?,penilaian_jatuh_edmonson4=?,penilaian_jatuh_nilai_edmonson4=?,penilaian_jatuh_edmonson5=?,penilaian_jatuh_nilai_edmonson5=?,penilaian_jatuh_edmonson6=?,penilaian_jatuh_nilai_edmonson6=?,penilaian_jatuh_edmonson7=?,penilaian_jatuh_nilai_edmonson7=?,penilaian_jatuh_edmonson8=?,penilaian_jatuh_nilai_edmonson8=?,penilaian_jatuh_edmonson9=?,penilaian_jatuh_nilai_edmonson9=?,penilaian_jatuh_totalnilai_edmonson=?",103,new String[]{
                        TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),
                        Suhu.getText(),GCS.getText(),BB.getText(),TB.getText(),BMI.getText(),KeluhanUtama.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),AlatBantu.getSelectedItem().toString(),KetBantu.getText(), 
                        Prothesa.getSelectedItem().toString(),KetProthesa.getText(),ADL.getSelectedItem().toString(),StatusPsiko.getSelectedItem().toString(),KetPsiko.getText(),HubunganKeluarga.getSelectedItem().toString(), 
                        TinggalDengan.getSelectedItem().toString(),KetTinggal.getText(),Ekonomi.getSelectedItem().toString(),StatusBudaya.getSelectedItem().toString(),KetBudaya.getText(),Edukasi.getSelectedItem().toString(), 
                        KetEdukasi.getText(),ATS.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),Hasil.getSelectedItem().toString(),Lapor.getSelectedItem().toString(),KetLapor.getText(), 
                        SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),TotalHasil.getText(),Nyeri.getSelectedItem().toString(),
                        Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),
                        Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),PadaDokter.getSelectedItem().toString(),KetDokter.getText(),Rencana.getText(),KdPetugas.getText(),SkalaResiko1.getSelectedItem().toString(),NilaiResiko1.getText(),SkalaResiko2.getSelectedItem().toString(),
                        NilaiResiko2.getText(),SkalaResiko3.getSelectedItem().toString(),NilaiResiko3.getText(),SkalaResiko4.getSelectedItem().toString(),NilaiResiko4.getText(),SkalaResiko5.getSelectedItem().toString(),
                        NilaiResiko5.getText(),SkalaResiko6.getSelectedItem().toString(),NilaiResiko6.getText(),NilaiResikoTotal.getText(),SkalaHumpty1.getSelectedItem().toString(),NilaiHumpty1.getText(),
                        SkalaHumpty2.getSelectedItem().toString(),NilaiHumpty2.getText(),SkalaHumpty3.getSelectedItem().toString(),NilaiHumpty3.getText(),SkalaHumpty4.getSelectedItem().toString(),NilaiHumpty4.getText(),
                        SkalaHumpty5.getSelectedItem().toString(),NilaiHumpty5.getText(),SkalaHumpty6.getSelectedItem().toString(),NilaiHumpty6.getText(),NilaiResikoHumptyTotal.getText(),
                        SkalaEdmonson1.getSelectedItem().toString(),NilaiEdmonson1.getText(),SkalaEdmonson2.getSelectedItem().toString(),NilaiEdmonson2.getText(),SkalaEdmonson3.getSelectedItem().toString(),NilaiEdmonson3.getText(),
                        SkalaEdmonson4.getSelectedItem().toString(),NilaiEdmonson4.getText(),SkalaEdmonson5.getSelectedItem().toString(),NilaiEdmonson5.getText(),SkalaEdmonson6.getSelectedItem().toString(),NilaiEdmonson6.getText(),
                        SkalaEdmonson7.getSelectedItem().toString(),NilaiEdmonson7.getText(),SkalaEdmonson8.getSelectedItem().toString(),NilaiEdmonson8.getText(),SkalaEdmonson9.getSelectedItem().toString(),NilaiEdmonson9.getText(),
                        NilaiResikoEdmonsonTotal.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                     })==true){
                        Sequel.meghapus("penilaian_awal_keperawatan_ralan_masalah","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                        for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                            if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                                Sequel.menyimpan2("penilaian_awal_keperawatan_ralan_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()});
                            }
                        }
                        getMasalah();
                        tampil();
                        emptTeks();
                        TabRawat.setSelectedIndex(1);
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
                if(TCari.getText().equals("")){
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                            "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                            "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                            "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                            "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                            "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                            "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                            "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                            "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                            "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                            "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                            "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                            "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? order by penilaian_awal_keperawatan_ralan.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                            "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                            "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                            "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                            "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                            "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                            "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                            "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                            "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                            "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                            "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                            "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                            "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and penilaian_awal_keperawatan_ralan.nip like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and petugas.nama like ? order by penilaian_awal_keperawatan_ralan.tanggal");
                }

                try {
                    if(TCari.getText().equals("")){
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    }else{
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(3,"%"+TCari.getText()+"%");
                        ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(6,"%"+TCari.getText()+"%");
                        ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(9,"%"+TCari.getText()+"%");
                        ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(12,"%"+TCari.getText()+"%");
                        ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(15,"%"+TCari.getText()+"%");
                    }   
                    rs=ps.executeQuery();
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'><b>PASIEN & PETUGAS</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'><b>I. KEADAAN UMUM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'><b>II. STATUS NUTRISI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='13%'><b>III. RIWAYAT KESEHATAN</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'><b>IV. FUNGSIONAL</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='16%'><b>V. RIWAYAT PSIKO-SOSIAL SPIRITUAL DAN BUDAYA</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='16%'><b>VI. PENILAIAN RESIKO JATUH</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'><b>VII. SKRINING GIZI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'><b>VIII. PENILAIAN TINGKAT NYERI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'><b>MASALAH & RENCANA KEPERAWATAN</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        masalahkeperawatan="";
                        ps2=koneksi.prepareStatement(
                            "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                            "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                            "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                masalahkeperawatan=rs2.getString("nama_masalah")+", "+masalahkeperawatan;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        htmlContent.append(
                            "<tr class='isi'>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>No.Rawat</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>No.R.M.</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Nama Pasien</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>J.K.</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("jk")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Agama</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("agama")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Bahasa</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nama_bahasa")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Tgl.Lahir</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nama_cacat")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Cacat Fisik</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Petugas</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nip")+" "+rs.getString("nama")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Tgl.Asuhan</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("tanggal")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Informasi</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("informasi")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>TD</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("td")+"mmHg</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Nadi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("nadi")+"x/menit</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>RR</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("rr")+"x/menit</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Suhu</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("suhu")+"°C</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>GCS</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("gcs")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>BB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("bb")+"Kg</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>TB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("tb")+"cm</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>BMI</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("bmi")+"Kg/m²</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Keluhan Utama</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("keluhan_utama")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>RPD</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("rpd")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>RPK</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("rpk")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>RPO</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("rpo")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Alergi</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("alergi")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Alat Bantu</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("alat_bantu")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Alat Bantu</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_bantu")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Prothesa</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("prothesa")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Prothesa</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_pro")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>ADL</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("adl")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Status Psikologis</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("status_psiko")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Psikologi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_psiko")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Hubungan pasien dengan anggota keluarga</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("hub_keluarga")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Tinggal dengan</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("tinggal_dengan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Tinggal</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_tinggal")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ekonomi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ekonomi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("budaya")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Budaya</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_budaya")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Edukasi diberikan kepada </td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("edukasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Edukasi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_edukasi")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Tidak seimbang/sempoyongan/limbung</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_a")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain)</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_b")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja/benda lain sebagai penopang</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_c")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Hasil</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("hasil")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Dilaporan ke dokter?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("lapor")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Jam Lapor</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_lapor")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Apakah ada penurunan berat badanyang tidak diinginkan selama enam bulan terakhir?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("sg1")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Apakah nafsu makan berkurang karena tidak nafsu makan?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("sg2")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Nilai 1</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("nilai1")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Nilai 2</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("nilai2")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Total Skor</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("total_hasil")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Tingkat Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("nyeri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Provokes</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("provokes")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Provokes</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_provokes")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Kualitas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("quality")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Kualitas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_quality")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Lokas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("lokasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Menyebar</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("menyebar")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Skala Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("skala_nyeri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Durasi</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("durasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Nyeri Hilang</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("nyeri_hilang")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Hilang Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_nyeri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Lapor Ke Dokter</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("pada_dokter")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Jam Lapor</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_dokter")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "Masalah Keperawatan : "+masalahkeperawatan+"<br><br>"+
                                    "Rencana Keperawatan : "+rs.getString("rencana")+
                                "</td>"+
                            "</tr>"
                        );
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='1800px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalKeperawatanRalan.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='1800px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL KEPERAWATAN RAWAT JALAN<br><br></font>"+        
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
            try {
                ChkAccor.setSelected(true);
                isMenu();
                getMasalah();
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    ChkAccor.setSelected(true);
                    isMenu();
                    getMasalah();
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
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,GCS,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,BMI);
    }//GEN-LAST:event_TBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,GCS);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Informasi,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void BMIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BMIKeyPressed
        Valid.pindah(evt,TB,KeluhanUtama);
    }//GEN-LAST:event_BMIKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPO,AlatBantu);
    }//GEN-LAST:event_AlergiKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        Valid.pindah(evt,TglAsuhan,TD);
    }//GEN-LAST:event_InformasiKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah(evt,BMI,RPK);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah(evt,RPK,RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah(evt,KeluhanUtama,RPD);
    }//GEN-LAST:event_RPKKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah(evt,RPD,Alergi);
    }//GEN-LAST:event_RPOKeyPressed

    private void AlatBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantuKeyPressed
        Valid.pindah(evt,Alergi,KetBantu);
    }//GEN-LAST:event_AlatBantuKeyPressed

    private void KetBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBantuKeyPressed
        Valid.pindah(evt,AlatBantu,Prothesa);
    }//GEN-LAST:event_KetBantuKeyPressed

    private void ProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProthesaKeyPressed
        Valid.pindah(evt,KetBantu,KetProthesa);
    }//GEN-LAST:event_ProthesaKeyPressed

    private void KetProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetProthesaKeyPressed
        Valid.pindah(evt,Prothesa,ADL);
    }//GEN-LAST:event_KetProthesaKeyPressed

    private void ADLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLKeyPressed
        Valid.pindah(evt,KetProthesa,StatusPsiko);
    }//GEN-LAST:event_ADLKeyPressed

    private void StatusPsikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusPsikoKeyPressed
        Valid.pindah(evt,ADL,KetPsiko);
    }//GEN-LAST:event_StatusPsikoKeyPressed

    private void KetPsikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPsikoKeyPressed
        Valid.pindah(evt,StatusPsiko,HubunganKeluarga);
    }//GEN-LAST:event_KetPsikoKeyPressed

    private void HubunganKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeluargaKeyPressed
        Valid.pindah(evt,KetPsiko,TinggalDengan);
    }//GEN-LAST:event_HubunganKeluargaKeyPressed

    private void TinggalDenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggalDenganKeyPressed
        Valid.pindah(evt,HubunganKeluarga,KetTinggal);
    }//GEN-LAST:event_TinggalDenganKeyPressed

    private void KetTinggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetTinggalKeyPressed
        Valid.pindah(evt,TinggalDengan,Ekonomi);
    }//GEN-LAST:event_KetTinggalKeyPressed

    private void EkonomiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkonomiKeyPressed
        Valid.pindah(evt,KetTinggal,StatusBudaya);
    }//GEN-LAST:event_EkonomiKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah(evt,KetBudaya,KetEdukasi);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void KetEdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetEdukasiKeyPressed
        Valid.pindah(evt,Edukasi,ATS);
    }//GEN-LAST:event_KetEdukasiKeyPressed

    private void LaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaporKeyPressed
        Valid.pindah(evt,Hasil,KetLapor);
    }//GEN-LAST:event_LaporKeyPressed

    private void ATSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATSKeyPressed
        Valid.pindah(evt,KetEdukasi,BJM);
    }//GEN-LAST:event_ATSKeyPressed

    private void BJMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BJMKeyPressed
        Valid.pindah(evt,ATS,MSA);
    }//GEN-LAST:event_BJMKeyPressed

    private void HasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilKeyPressed
        Valid.pindah(evt,MSA,Lapor);
    }//GEN-LAST:event_HasilKeyPressed

    private void SG2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG2KeyPressed
        Valid.pindah(evt,Nilai1,Nilai2);
    }//GEN-LAST:event_SG2KeyPressed

    private void KetLaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLaporKeyPressed
        Valid.pindah(evt,Lapor,SG1);
    }//GEN-LAST:event_KetLaporKeyPressed

    private void SG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG1KeyPressed
        Valid.pindah(evt,KetLapor,Nilai1);
    }//GEN-LAST:event_SG1KeyPressed

    private void Nilai1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nilai1KeyPressed
        Valid.pindah(evt,SG1,SG2);
    }//GEN-LAST:event_Nilai1KeyPressed

    private void MSAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MSAKeyPressed
        Valid.pindah(evt,BJM,Hasil);
    }//GEN-LAST:event_MSAKeyPressed

    private void Nilai2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nilai2KeyPressed
        Valid.pindah(evt,SG2,Nyeri);
    }//GEN-LAST:event_Nilai2KeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,Nilai2,Provokes);
    }//GEN-LAST:event_NyeriKeyPressed

    private void ProvokesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProvokesKeyPressed
        Valid.pindah(evt,Nyeri,KetProvokes);
    }//GEN-LAST:event_ProvokesKeyPressed

    private void KetProvokesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetProvokesKeyPressed
        Valid.pindah(evt,Provokes,Quality);
    }//GEN-LAST:event_KetProvokesKeyPressed

    private void QualityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QualityKeyPressed
        Valid.pindah(evt,KetProvokes,KetQuality);
    }//GEN-LAST:event_QualityKeyPressed

    private void KetQualityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetQualityKeyPressed
        Valid.pindah(evt,Quality,Lokasi);
    }//GEN-LAST:event_KetQualityKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        Valid.pindah(evt,KetQuality,Menyebar);
    }//GEN-LAST:event_LokasiKeyPressed

    private void MenyebarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenyebarKeyPressed
        Valid.pindah(evt,Lokasi,SkalaNyeri);
    }//GEN-LAST:event_MenyebarKeyPressed

    private void SkalaNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriKeyPressed
        Valid.pindah(evt,Menyebar,Durasi);
    }//GEN-LAST:event_SkalaNyeriKeyPressed

    private void DurasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DurasiKeyPressed
        Valid.pindah(evt,SkalaNyeri,NyeriHilang);
    }//GEN-LAST:event_DurasiKeyPressed

    private void NyeriHilangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriHilangKeyPressed
        Valid.pindah(evt,Durasi,KetNyeri);
    }//GEN-LAST:event_NyeriHilangKeyPressed

    private void KetNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNyeriKeyPressed
        Valid.pindah(evt,NyeriHilang,PadaDokter);
    }//GEN-LAST:event_KetNyeriKeyPressed

    private void PadaDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PadaDokterKeyPressed
        Valid.pindah(evt,KetNyeri,KetDokter);
    }//GEN-LAST:event_PadaDokterKeyPressed

    private void KetDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetDokterKeyPressed
        Valid.pindah(evt,PadaDokter,Rencana);
    }//GEN-LAST:event_KetDokterKeyPressed

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RencanaKeyPressed

    private void TotalHasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalHasilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalHasilKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt,Suhu,BB);
    }//GEN-LAST:event_GCSKeyPressed

    private void CacatFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CacatFisikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CacatFisikKeyPressed

    private void StatusBudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusBudayaKeyPressed
        Valid.pindah(evt,Ekonomi,KetBudaya);
    }//GEN-LAST:event_StatusBudayaKeyPressed

    private void KetBudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBudayaKeyPressed
        Valid.pindah(evt,StatusBudaya,Edukasi);
    }//GEN-LAST:event_KetBudayaKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KetDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariMasalahKeyPressed

    private void BtnCariPemeriksaan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaan1ActionPerformed
        tampilMasalah();
    }//GEN-LAST:event_BtnCariPemeriksaan1ActionPerformed

    private void BtnCariPemeriksaan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPemeriksaan1KeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterMasalahKeperawatan form=new MasterMasalahKeperawatan(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampilMasalah();
    }//GEN-LAST:event_formWindowOpened

    private void BahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BahasaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BahasaKeyPressed

    private void AgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AgamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgamaKeyPressed

    private void SG1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG1ItemStateChanged
        Nilai1.setSelectedIndex(SG1.getSelectedIndex());
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())));
    }//GEN-LAST:event_SG1ItemStateChanged

    private void Nilai1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Nilai1ItemStateChanged
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())));
    }//GEN-LAST:event_Nilai1ItemStateChanged

    private void SG2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG2ItemStateChanged
        Nilai2.setSelectedIndex(SG2.getSelectedIndex());
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())));
    }//GEN-LAST:event_SG2ItemStateChanged

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isMenu();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
       if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            param.put("nyeri",Sequel.cariGambar("select nyeri from gambar")); 
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),63).toString()));
            try {
                masalahkeperawatan="";
                ps2=koneksi.prepareStatement(
                    "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                    "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                    "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
                try {
                    ps2.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        masalahkeperawatan=rs2.getString("nama_masalah")+", "+masalahkeperawatan;
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
            param.put("masalah",masalahkeperawatan);  
            Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanRalan.jasper","report","::[ Laporan Penilaian Awal Keperawatan Ralan ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                        "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                        "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                        "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                        "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                        "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                        "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                        "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                        "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }  
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void DetailRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetailRencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DetailRencanaKeyPressed

    private void TPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPasienActionPerformed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        akses.setform("RMPenilaianAwalKeperawatanRalan");
        masterr.isCek();
        masterr.onCari();
        masterr.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        masterr.setLocationRelativeTo(internalFrame1);
        masterr.setVisible(true);
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void TDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyTyped
        char kata= evt.getKeyChar();
        if(!((kata>='0') && (kata<='9') || (kata==KeyEvent.VK_SLASH)  || (kata== KeyEvent.VK_BACK_SPACE))){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Hanya diperbolehkan menginputkan Angka dan Garis Miring");
        }
    }//GEN-LAST:event_TDKeyTyped

    private void SuhuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyTyped
        char kata= evt.getKeyChar();
        if(!((kata>='0') && (kata<='9') || (kata== KeyEvent.VK_PERIOD) || (kata== KeyEvent.VK_BACK_SPACE))){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Hanya diperbolehkan menginputkan Angka dan Titik");
        }
    }//GEN-LAST:event_SuhuKeyTyped

    private void SkalaResiko1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko1ItemStateChanged
        if(SkalaResiko1.getSelectedIndex()==0){
            NilaiResiko1.setText("-");
        }else if(SkalaResiko1.getSelectedIndex()==1){
            NilaiResiko1.setText("0");
        }else if(SkalaResiko1.getSelectedIndex()==2){
            NilaiResiko1.setText("25");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko1ItemStateChanged

    private void SkalaResiko1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkalaResiko1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaResiko1ActionPerformed

    private void SkalaResiko1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko1KeyPressed
        Valid.pindah(evt,SkalaResiko2,SkalaResiko3);
    }//GEN-LAST:event_SkalaResiko1KeyPressed

    private void SkalaResiko2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko2ItemStateChanged
        if(SkalaResiko2.getSelectedIndex()==0){
            NilaiResiko2.setText("-");
        }else if(SkalaResiko2.getSelectedIndex()==1){
            NilaiResiko2.setText("0");
        }else if(SkalaResiko2.getSelectedIndex()==2){
            NilaiResiko2.setText("15");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko2ItemStateChanged

    private void SkalaResiko2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko2KeyPressed
        Valid.pindah(evt,SkalaResiko1,SkalaResiko3);
    }//GEN-LAST:event_SkalaResiko2KeyPressed

    private void SkalaResiko3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko3ItemStateChanged
        if(SkalaResiko3.getSelectedIndex()==0){
            NilaiResiko3.setText("-");
        }else if(SkalaResiko3.getSelectedIndex()==1){
            NilaiResiko3.setText("0");
        }else if(SkalaResiko3.getSelectedIndex()==2){
            NilaiResiko3.setText("15");
        }else if(SkalaResiko3.getSelectedIndex()==3){
            NilaiResiko3.setText("30");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko3ItemStateChanged

    private void SkalaResiko3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko3KeyPressed
        Valid.pindah(evt,SkalaResiko2,SkalaResiko4);
    }//GEN-LAST:event_SkalaResiko3KeyPressed

    private void SkalaResiko4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko4ItemStateChanged
        if(SkalaResiko4.getSelectedIndex()==0){
            NilaiResiko4.setText("-");
        }else if(SkalaResiko4.getSelectedIndex()==1){
            NilaiResiko4.setText("0");
        }else if(SkalaResiko4.getSelectedIndex()==2){
            NilaiResiko4.setText("20");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko4ItemStateChanged

    private void SkalaResiko4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko4KeyPressed
        Valid.pindah(evt,SkalaResiko3,SkalaResiko5);
    }//GEN-LAST:event_SkalaResiko4KeyPressed

    private void SkalaResiko5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko5ItemStateChanged
        if(SkalaResiko5.getSelectedIndex()==0){
            NilaiResiko5.setText("-");
        }else if(SkalaResiko5.getSelectedIndex()==1){
            NilaiResiko5.setText("0");
        }else if(SkalaResiko5.getSelectedIndex()==2){
            NilaiResiko5.setText("10");
        }else if(SkalaResiko5.getSelectedIndex()==3){
            NilaiResiko5.setText("20");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko5ItemStateChanged

    private void SkalaResiko5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko5KeyPressed
        Valid.pindah(evt,SkalaResiko4,SkalaResiko6);
    }//GEN-LAST:event_SkalaResiko5KeyPressed

    private void SkalaResiko6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko6ItemStateChanged
        if(SkalaResiko6.getSelectedIndex()==0){
            NilaiResiko6.setText("-");
        }else if(SkalaResiko6.getSelectedIndex()==1){
            NilaiResiko6.setText("0");
        }else if(SkalaResiko6.getSelectedIndex()==2){
            NilaiResiko6.setText("15");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko6ItemStateChanged

    private void SkalaResiko6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko6KeyPressed
        Valid.pindah(evt,SkalaResiko5,SkalaHumpty1);
    }//GEN-LAST:event_SkalaResiko6KeyPressed

    private void SkalaHumpty1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaHumpty1ItemStateChanged
        if(SkalaHumpty1.getSelectedIndex()==0){
            NilaiHumpty1.setText("-");
        }else if(SkalaHumpty1.getSelectedIndex()==1){
            NilaiHumpty1.setText("4");
        }else if(SkalaHumpty1.getSelectedIndex()==2){
            NilaiHumpty1.setText("3");
        }else if(SkalaHumpty1.getSelectedIndex()==3){
            NilaiHumpty1.setText("2");
        }else if(SkalaHumpty1.getSelectedIndex()==4){
            NilaiHumpty1.setText("1");
        }
        isTotalResikoHumpty();
    }//GEN-LAST:event_SkalaHumpty1ItemStateChanged

    private void SkalaHumpty1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaHumpty1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaHumpty1KeyPressed

    private void SkalaHumpty2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaHumpty2ItemStateChanged
        if(SkalaHumpty2.getSelectedIndex()==0){
            NilaiHumpty2.setText("-");
        }else if(SkalaHumpty2.getSelectedIndex()==1){
            NilaiHumpty2.setText("1");
        }else if(SkalaHumpty2.getSelectedIndex()==2){
            NilaiHumpty2.setText("2");
        }
        isTotalResikoHumpty();
    }//GEN-LAST:event_SkalaHumpty2ItemStateChanged

    private void SkalaHumpty2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaHumpty2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaHumpty2KeyPressed

    private void SkalaHumpty3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaHumpty3ItemStateChanged
        if(SkalaHumpty3.getSelectedIndex()==0){
            NilaiHumpty3.setText("-");
        }else if(SkalaHumpty3.getSelectedIndex()==1){
            NilaiHumpty3.setText("4");
        }else if(SkalaHumpty3.getSelectedIndex()==2){
            NilaiHumpty3.setText("3");
        }else if(SkalaHumpty3.getSelectedIndex()==3){
            NilaiHumpty3.setText("2");
        }else if(SkalaHumpty3.getSelectedIndex()==4){
            NilaiHumpty3.setText("1");
        }
        isTotalResikoHumpty();
    }//GEN-LAST:event_SkalaHumpty3ItemStateChanged

    private void SkalaHumpty3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaHumpty3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaHumpty3KeyPressed

    private void SkalaHumpty4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaHumpty4ItemStateChanged
        if(SkalaHumpty4.getSelectedIndex()==0){
            NilaiHumpty4.setText("-");
        }else if(SkalaHumpty4.getSelectedIndex()==1){
            NilaiHumpty4.setText("3");
        }else if(SkalaHumpty4.getSelectedIndex()==2){
            NilaiHumpty4.setText("2");
        }else if(SkalaHumpty4.getSelectedIndex()==3){
            NilaiHumpty4.setText("1");
        }
        isTotalResikoHumpty();
    }//GEN-LAST:event_SkalaHumpty4ItemStateChanged

    private void SkalaHumpty4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaHumpty4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaHumpty4KeyPressed

    private void SkalaHumpty5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaHumpty5ItemStateChanged
        if(SkalaHumpty5.getSelectedIndex()==0){
            NilaiHumpty5.setText("-");
        }else if(SkalaHumpty5.getSelectedIndex()==1){
            NilaiHumpty5.setText("4");
        }else if(SkalaHumpty5.getSelectedIndex()==2){
            NilaiHumpty5.setText("3");
        }else if(SkalaHumpty5.getSelectedIndex()==3){
            NilaiHumpty5.setText("2");
        }else if(SkalaHumpty5.getSelectedIndex()==4){
            NilaiHumpty5.setText("1");
        }
        isTotalResikoHumpty();
    }//GEN-LAST:event_SkalaHumpty5ItemStateChanged

    private void SkalaHumpty5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaHumpty5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaHumpty5KeyPressed

    private void SkalaHumpty6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaHumpty6ItemStateChanged
        if(SkalaHumpty6.getSelectedIndex()==0){
            NilaiHumpty6.setText("-");
        }else if(SkalaHumpty6.getSelectedIndex()==1){
            NilaiHumpty6.setText("3");
        }else if(SkalaHumpty6.getSelectedIndex()==2){
            NilaiHumpty6.setText("2");
        }else if(SkalaHumpty6.getSelectedIndex()==3){
            NilaiHumpty6.setText("1");
        }
        isTotalResikoHumpty();
    }//GEN-LAST:event_SkalaHumpty6ItemStateChanged

    private void SkalaHumpty6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaHumpty6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaHumpty6KeyPressed

    private void SkalaEdmonson1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaEdmonson1ItemStateChanged
        if(SkalaEdmonson1.getSelectedIndex()==0){
            NilaiEdmonson1.setText("-");
        }else if(SkalaEdmonson1.getSelectedIndex()==1){
            NilaiEdmonson1.setText("8");
        }else if(SkalaEdmonson1.getSelectedIndex()==2){
            NilaiEdmonson1.setText("10");
        }else if(SkalaEdmonson1.getSelectedIndex()==3){
            NilaiEdmonson1.setText("26");
        }
        isTotalResikoEdmonson();
    }//GEN-LAST:event_SkalaEdmonson1ItemStateChanged

    private void SkalaEdmonson1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaEdmonson1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaEdmonson1KeyPressed

    private void SkalaEdmonson2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaEdmonson2ItemStateChanged
        if(SkalaEdmonson2.getSelectedIndex()==0){
            NilaiEdmonson2.setText("-");
        }else if(SkalaEdmonson2.getSelectedIndex()==1){
            NilaiEdmonson2.setText("4");
        }else if(SkalaEdmonson2.getSelectedIndex()==2){
            NilaiEdmonson2.setText("12");
        }else if(SkalaEdmonson2.getSelectedIndex()==3){
            NilaiEdmonson2.setText("13");
        }else if(SkalaEdmonson2.getSelectedIndex()==4){
            NilaiEdmonson2.setText("14");
        }
        isTotalResikoEdmonson();
    }//GEN-LAST:event_SkalaEdmonson2ItemStateChanged

    private void SkalaEdmonson2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaEdmonson2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaEdmonson2KeyPressed

    private void SkalaEdmonson3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaEdmonson3ItemStateChanged
        if(SkalaEdmonson3.getSelectedIndex()==0){
            NilaiEdmonson3.setText("-");
        }else if(SkalaEdmonson3.getSelectedIndex()==1){
            NilaiEdmonson3.setText("8");
        }else if(SkalaEdmonson3.getSelectedIndex()==2){
            NilaiEdmonson3.setText("12");
        }else if(SkalaEdmonson3.getSelectedIndex()==3){
            NilaiEdmonson3.setText("10");
        }else if(SkalaEdmonson3.getSelectedIndex()==4){
            NilaiEdmonson3.setText("12");
        }else if(SkalaEdmonson3.getSelectedIndex()==5){
            NilaiEdmonson3.setText("12");
        }
        isTotalResikoEdmonson();
    }//GEN-LAST:event_SkalaEdmonson3ItemStateChanged

    private void SkalaEdmonson3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaEdmonson3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaEdmonson3KeyPressed

    private void SkalaEdmonson4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaEdmonson4ItemStateChanged
        if(SkalaEdmonson4.getSelectedIndex()==0){
            NilaiEdmonson4.setText("-");
        }else if(SkalaEdmonson4.getSelectedIndex()==1){
            NilaiEdmonson4.setText("10");
        }else if(SkalaEdmonson4.getSelectedIndex()==2){
            NilaiEdmonson4.setText("10");
        }else if(SkalaEdmonson4.getSelectedIndex()==3){
            NilaiEdmonson4.setText("8");
        }else if(SkalaEdmonson4.getSelectedIndex()==4){
            NilaiEdmonson4.setText("12");
        }
        isTotalResikoEdmonson();
    }//GEN-LAST:event_SkalaEdmonson4ItemStateChanged

    private void SkalaEdmonson4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaEdmonson4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaEdmonson4KeyPressed

    private void SkalaEdmonson5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaEdmonson5ItemStateChanged
        if(SkalaEdmonson5.getSelectedIndex()==0){
            NilaiEdmonson5.setText("-");
        }else if(SkalaEdmonson5.getSelectedIndex()==1){
            NilaiEdmonson5.setText("10");
        }else if(SkalaEdmonson5.getSelectedIndex()==2){
            NilaiEdmonson5.setText("8");
        }else if(SkalaEdmonson5.getSelectedIndex()==3){
            NilaiEdmonson5.setText("10");
        }else if(SkalaEdmonson5.getSelectedIndex()==4){
            NilaiEdmonson5.setText("12");
        }
        isTotalResikoEdmonson();
    }//GEN-LAST:event_SkalaEdmonson5ItemStateChanged

    private void SkalaEdmonson5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaEdmonson5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaEdmonson5KeyPressed

    private void SkalaEdmonson6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaEdmonson6ItemStateChanged
        if(SkalaEdmonson6.getSelectedIndex()==0){
            NilaiEdmonson6.setText("-");
        }else if(SkalaEdmonson6.getSelectedIndex()==1){
            NilaiEdmonson6.setText("7");
        }else if(SkalaEdmonson6.getSelectedIndex()==2){
            NilaiEdmonson6.setText("8");
        }else if(SkalaEdmonson6.getSelectedIndex()==3){
            NilaiEdmonson6.setText("10");
        }else if(SkalaEdmonson6.getSelectedIndex()==4){
            NilaiEdmonson6.setText("8");
        }else if(SkalaEdmonson6.getSelectedIndex()==5){
            NilaiEdmonson6.setText("15");
        }
        isTotalResikoEdmonson();
    }//GEN-LAST:event_SkalaEdmonson6ItemStateChanged

    private void SkalaEdmonson6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaEdmonson6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaEdmonson6KeyPressed

    private void SkalaEdmonson7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaEdmonson7ItemStateChanged
        if(SkalaEdmonson7.getSelectedIndex()==0){
            NilaiEdmonson7.setText("-");
        }else if(SkalaEdmonson7.getSelectedIndex()==1){
            NilaiEdmonson7.setText("12");
        }else if(SkalaEdmonson7.getSelectedIndex()==2){
            NilaiEdmonson7.setText("0");
        }
        isTotalResikoEdmonson();
    }//GEN-LAST:event_SkalaEdmonson7ItemStateChanged

    private void SkalaEdmonson7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaEdmonson7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaEdmonson7KeyPressed

    private void SkalaEdmonson8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaEdmonson8ItemStateChanged
        if(SkalaEdmonson8.getSelectedIndex()==0){
            NilaiEdmonson8.setText("-");
        }else if(SkalaEdmonson8.getSelectedIndex()==1){
            NilaiEdmonson8.setText("8");
        }else if(SkalaEdmonson8.getSelectedIndex()==2){
            NilaiEdmonson8.setText("12");
        }
        isTotalResikoEdmonson();
    }//GEN-LAST:event_SkalaEdmonson8ItemStateChanged

    private void SkalaEdmonson8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaEdmonson8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaEdmonson8KeyPressed

    private void SkalaEdmonson9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaEdmonson9ItemStateChanged
        if(SkalaEdmonson9.getSelectedIndex()==0){
            NilaiEdmonson9.setText("-");
        }else if(SkalaEdmonson9.getSelectedIndex()==1){
            NilaiEdmonson9.setText("8");
        }else if(SkalaEdmonson9.getSelectedIndex()==2){
            NilaiEdmonson9.setText("12");
        }
        isTotalResikoEdmonson();
    }//GEN-LAST:event_SkalaEdmonson9ItemStateChanged

    private void SkalaEdmonson9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaEdmonson9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaEdmonson9KeyPressed

    private void TingkatResikoEdmonsonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TingkatResikoEdmonsonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TingkatResikoEdmonsonKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanRalan dialog = new RMPenilaianAwalKeperawatanRalan(new javax.swing.JFrame(), true);
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
    private widget.ComboBox ADL;
    private widget.ComboBox ATS;
    private widget.TextBox Agama;
    private widget.ComboBox AlatBantu;
    private widget.TextBox Alergi;
    private widget.TextBox BB;
    private widget.ComboBox BJM;
    private widget.TextBox BMI;
    private widget.TextBox Bahasa;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariPemeriksaan1;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahMasalah;
    private widget.TextBox CacatFisik;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DetailRencana;
    private widget.TextBox Durasi;
    private widget.ComboBox Edukasi;
    private widget.ComboBox Ekonomi;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox GCS;
    private widget.ComboBox Hasil;
    private widget.ComboBox HubunganKeluarga;
    private widget.ComboBox Informasi;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KetBantu;
    private widget.TextBox KetBudaya;
    private widget.TextBox KetDokter;
    private widget.TextBox KetEdukasi;
    private widget.TextBox KetLapor;
    private widget.TextBox KetNyeri;
    private widget.TextBox KetProthesa;
    private widget.TextBox KetProvokes;
    private widget.TextBox KetPsiko;
    private widget.TextBox KetQuality;
    private widget.TextBox KetTinggal;
    private widget.Label LCount;
    private widget.ComboBox Lapor;
    private widget.editorpane LoadHTML;
    private widget.TextBox Lokasi;
    private widget.ComboBox MSA;
    private widget.ComboBox Menyebar;
    private widget.TextBox Nadi;
    private widget.ComboBox Nilai1;
    private widget.ComboBox Nilai2;
    private widget.TextBox NilaiEdmonson1;
    private widget.TextBox NilaiEdmonson2;
    private widget.TextBox NilaiEdmonson3;
    private widget.TextBox NilaiEdmonson4;
    private widget.TextBox NilaiEdmonson5;
    private widget.TextBox NilaiEdmonson6;
    private widget.TextBox NilaiEdmonson7;
    private widget.TextBox NilaiEdmonson8;
    private widget.TextBox NilaiEdmonson9;
    private widget.TextBox NilaiHumpty1;
    private widget.TextBox NilaiHumpty2;
    private widget.TextBox NilaiHumpty3;
    private widget.TextBox NilaiHumpty4;
    private widget.TextBox NilaiHumpty5;
    private widget.TextBox NilaiHumpty6;
    private widget.TextBox NilaiResiko1;
    private widget.TextBox NilaiResiko2;
    private widget.TextBox NilaiResiko3;
    private widget.TextBox NilaiResiko4;
    private widget.TextBox NilaiResiko5;
    private widget.TextBox NilaiResiko6;
    private widget.TextBox NilaiResikoEdmonsonTotal;
    private widget.TextBox NilaiResikoHumptyTotal;
    private widget.TextBox NilaiResikoTotal;
    private widget.TextBox NmPetugas;
    private widget.ComboBox Nyeri;
    private widget.ComboBox NyeriHilang;
    private widget.ComboBox PadaDokter;
    private widget.PanelBiasa PanelAccor;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.ComboBox Prothesa;
    private widget.ComboBox Provokes;
    private widget.ComboBox Quality;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextBox RR;
    private widget.TextArea Rencana;
    private widget.ComboBox SG1;
    private widget.ComboBox SG2;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ComboBox SkalaEdmonson1;
    private widget.ComboBox SkalaEdmonson2;
    private widget.ComboBox SkalaEdmonson3;
    private widget.ComboBox SkalaEdmonson4;
    private widget.ComboBox SkalaEdmonson5;
    private widget.ComboBox SkalaEdmonson6;
    private widget.ComboBox SkalaEdmonson7;
    private widget.ComboBox SkalaEdmonson8;
    private widget.ComboBox SkalaEdmonson9;
    private widget.ComboBox SkalaHumpty1;
    private widget.ComboBox SkalaHumpty2;
    private widget.ComboBox SkalaHumpty3;
    private widget.ComboBox SkalaHumpty4;
    private widget.ComboBox SkalaHumpty5;
    private widget.ComboBox SkalaHumpty6;
    private widget.ComboBox SkalaNyeri;
    private widget.ComboBox SkalaResiko1;
    private widget.ComboBox SkalaResiko2;
    private widget.ComboBox SkalaResiko3;
    private widget.ComboBox SkalaResiko4;
    private widget.ComboBox SkalaResiko5;
    private widget.ComboBox SkalaResiko6;
    private widget.ComboBox StatusBudaya;
    private widget.ComboBox StatusPsiko;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TCariMasalah;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TinggalDengan;
    private widget.Label TingkatResiko;
    private widget.TextArea TingkatResikoEdmonson;
    private widget.Label TingkatResikoHumpty;
    private widget.TextBox TotalHasil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel170;
    private widget.Label jLabel171;
    private widget.Label jLabel172;
    private widget.Label jLabel173;
    private widget.Label jLabel18;
    private widget.Label jLabel181;
    private widget.Label jLabel182;
    private widget.Label jLabel183;
    private widget.Label jLabel184;
    private widget.Label jLabel185;
    private widget.Label jLabel186;
    private widget.Label jLabel187;
    private widget.Label jLabel188;
    private widget.Label jLabel189;
    private widget.Label jLabel19;
    private widget.Label jLabel190;
    private widget.Label jLabel191;
    private widget.Label jLabel192;
    private widget.Label jLabel193;
    private widget.Label jLabel194;
    private widget.Label jLabel195;
    private widget.Label jLabel196;
    private widget.Label jLabel197;
    private widget.Label jLabel198;
    private widget.Label jLabel199;
    private widget.Label jLabel20;
    private widget.Label jLabel200;
    private widget.Label jLabel201;
    private widget.Label jLabel202;
    private widget.Label jLabel203;
    private widget.Label jLabel204;
    private widget.Label jLabel205;
    private widget.Label jLabel206;
    private widget.Label jLabel207;
    private widget.Label jLabel208;
    private widget.Label jLabel209;
    private widget.Label jLabel21;
    private widget.Label jLabel210;
    private widget.Label jLabel211;
    private widget.Label jLabel212;
    private widget.Label jLabel213;
    private widget.Label jLabel214;
    private widget.Label jLabel215;
    private widget.Label jLabel216;
    private widget.Label jLabel217;
    private widget.Label jLabel218;
    private widget.Label jLabel219;
    private widget.Label jLabel22;
    private widget.Label jLabel220;
    private widget.Label jLabel221;
    private widget.Label jLabel222;
    private widget.Label jLabel223;
    private widget.Label jLabel224;
    private widget.Label jLabel225;
    private widget.Label jLabel226;
    private widget.Label jLabel227;
    private widget.Label jLabel228;
    private widget.Label jLabel229;
    private widget.Label jLabel23;
    private widget.Label jLabel230;
    private widget.Label jLabel231;
    private widget.Label jLabel232;
    private widget.Label jLabel233;
    private widget.Label jLabel234;
    private widget.Label jLabel235;
    private widget.Label jLabel236;
    private widget.Label jLabel237;
    private widget.Label jLabel238;
    private widget.Label jLabel239;
    private widget.Label jLabel24;
    private widget.Label jLabel240;
    private widget.Label jLabel241;
    private widget.Label jLabel242;
    private widget.Label jLabel243;
    private widget.Label jLabel244;
    private widget.Label jLabel245;
    private widget.Label jLabel246;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel262;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel43;
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
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel9;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbMasalahDetailMasalah;
    private widget.Table tbMasalahKeperawatan;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                        "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                        "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                        "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                        "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                        "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                        "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                        "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                        "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya,penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse1,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse1,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse2,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse2,penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse3,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse3,penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse4,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse4,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse5,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse5,penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse6,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse6,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_totalnilai,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty1,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty1,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty2,"+       
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty2,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty3,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty3,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty4,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty4,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty5,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty5,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty6,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty6,penilaian_awal_keperawatan_ralan.penilaian_jatuh_totalnilai_humpty,penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson1,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson1,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson2,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson2,penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson3,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson3,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson4,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson4,penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson5,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson5,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson6,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson6,penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson7,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson7,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson8,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson8,penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson9,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson9,penilaian_awal_keperawatan_ralan.penilaian_jatuh_totalnilai_edmonson "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? order by penilaian_awal_keperawatan_ralan.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                        "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                        "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                        "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                        "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                        "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                        "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                        "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                        "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya,penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse1,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse1,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse2,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse2,penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse3,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse3,penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse4,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse4,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse5,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse5,penilaian_awal_keperawatan_ralan.penilaian_jatuh_morse6,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_morse6,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_totalnilai,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty1,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty1,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty2,"+       
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty2,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty3,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty3,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty4,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty4,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty5,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty5,penilaian_awal_keperawatan_ralan.penilaian_jatuh_humty6,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_humty6,penilaian_awal_keperawatan_ralan.penilaian_jatuh_totalnilai_humpty,penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson1,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson1,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson2,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson2,penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson3,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson3,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson4,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson4,penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson5,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson5,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson6,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson6,penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson7,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson7,"+
                        "penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson8,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson8,penilaian_awal_keperawatan_ralan.penilaian_jatuh_edmonson9,penilaian_awal_keperawatan_ralan.penilaian_jatuh_nilai_edmonson9,penilaian_awal_keperawatan_ralan.penilaian_jatuh_totalnilai_edmonson "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and penilaian_awal_keperawatan_ralan.nip like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and petugas.nama like ? order by penilaian_awal_keperawatan_ralan.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(15,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("agama"),rs.getString("nama_bahasa"),rs.getString("nama_cacat"),
                        rs.getString("tgl_lahir"),rs.getString("tanggal"),rs.getString("informasi"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),
                        rs.getString("gcs"),rs.getString("bb"),rs.getString("tb"),rs.getString("bmi"),rs.getString("keluhan_utama"),rs.getString("rpd"),rs.getString("rpk"),rs.getString("rpo"),
                        rs.getString("alergi"),rs.getString("alat_bantu"),rs.getString("ket_bantu"),rs.getString("prothesa"),rs.getString("ket_pro"),rs.getString("adl"),rs.getString("status_psiko"),
                        rs.getString("ket_psiko"),rs.getString("hub_keluarga"),rs.getString("tinggal_dengan"),rs.getString("ket_tinggal"),rs.getString("ekonomi"),rs.getString("budaya"),
                        rs.getString("ket_budaya"),rs.getString("edukasi"),rs.getString("ket_edukasi"),rs.getString("berjalan_a"),rs.getString("berjalan_b"),rs.getString("berjalan_c"),
                        rs.getString("hasil"),rs.getString("lapor"),rs.getString("ket_lapor"),rs.getString("sg1"),rs.getString("nilai1"),rs.getString("sg2"),rs.getString("nilai2"),
                        rs.getString("total_hasil"),rs.getString("nyeri"),rs.getString("provokes"),rs.getString("ket_provokes"),rs.getString("quality"),rs.getString("ket_quality"),
                        rs.getString("lokasi"),rs.getString("menyebar"),rs.getString("skala_nyeri"),rs.getString("durasi"),rs.getString("nyeri_hilang"),rs.getString("ket_nyeri"),
                        rs.getString("pada_dokter"),rs.getString("ket_dokter"),rs.getString("rencana"),rs.getString("nip"),rs.getString("nama"),rs.getString("penilaian_jatuh_morse1"),rs.getString("penilaian_jatuh_nilai_morse1"),rs.getString("penilaian_jatuh_morse2"),rs.getString("penilaian_jatuh_nilai_morse2"),rs.getString("penilaian_jatuh_morse3"),rs.getString("penilaian_jatuh_nilai_morse3"),
                        rs.getString("penilaian_jatuh_morse4"),rs.getString("penilaian_jatuh_nilai_morse4"),rs.getString("penilaian_jatuh_morse5"),rs.getString("penilaian_jatuh_nilai_morse5"),rs.getString("penilaian_jatuh_morse6"),rs.getString("penilaian_jatuh_nilai_morse6"),
                        rs.getString("penilaian_jatuh_totalnilai"),rs.getString("penilaian_jatuh_humty1"),rs.getString("penilaian_jatuh_nilai_humty1"),rs.getString("penilaian_jatuh_humty2"),rs.getString("penilaian_jatuh_nilai_humty2"),rs.getString("penilaian_jatuh_humty3"),
                        rs.getString("penilaian_jatuh_nilai_humty3"),rs.getString("penilaian_jatuh_humty4"),rs.getString("penilaian_jatuh_nilai_humty4"),rs.getString("penilaian_jatuh_humty5"),rs.getString("penilaian_jatuh_nilai_humty5"),
                        rs.getString("penilaian_jatuh_humty6"),rs.getString("penilaian_jatuh_nilai_humty6"),rs.getString("penilaian_jatuh_totalnilai_humpty"),rs.getString("penilaian_jatuh_edmonson1"),rs.getString("penilaian_jatuh_nilai_edmonson1"),
                        rs.getString("penilaian_jatuh_edmonson2"),rs.getString("penilaian_jatuh_nilai_edmonson2"),rs.getString("penilaian_jatuh_edmonson3"),rs.getString("penilaian_jatuh_nilai_edmonson3"),rs.getString("penilaian_jatuh_edmonson4"),
                        rs.getString("penilaian_jatuh_nilai_edmonson4"),rs.getString("penilaian_jatuh_edmonson5"),rs.getString("penilaian_jatuh_nilai_edmonson5"),rs.getString("penilaian_jatuh_edmonson6"),rs.getString("penilaian_jatuh_nilai_edmonson6"),
                        rs.getString("penilaian_jatuh_edmonson7"),rs.getString("penilaian_jatuh_nilai_edmonson7"),rs.getString("penilaian_jatuh_edmonson8"),rs.getString("penilaian_jatuh_nilai_edmonson8"),rs.getString("penilaian_jatuh_edmonson9"),
                        rs.getString("penilaian_jatuh_nilai_edmonson9"),rs.getString("penilaian_jatuh_totalnilai_edmonson")
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
    
    private void isTotalResikoJatuh(){
        try {
            NilaiResikoTotal.setText((Integer.parseInt(NilaiResiko1.getText())+Integer.parseInt(NilaiResiko2.getText())+Integer.parseInt(NilaiResiko3.getText())+Integer.parseInt(NilaiResiko4.getText())+Integer.parseInt(NilaiResiko5.getText())+Integer.parseInt(NilaiResiko6.getText()))+"");
            if(Integer.parseInt(NilaiResikoTotal.getText())<25){
                TingkatResiko.setText("Tingkat Resiko : Risiko Rendah (0-24), Tindakan : Intervensi pencegahan risiko jatuh standar");
            }else if(Integer.parseInt(NilaiResikoTotal.getText())<45){
                TingkatResiko.setText("Tingkat Resiko : Risiko Sedang (25-44), Tindakan : Intervensi pencegahan risiko jatuh standar");
            }else if(Integer.parseInt(NilaiResikoTotal.getText())>=45){
                TingkatResiko.setText("Tingkat Resiko : Risiko Tinggi (> 45), Tindakan : Intervensi pencegahan risiko jatuh standar dan Intervensi risiko jatuh tinggi");
            }
        } catch (Exception e) {
            NilaiResikoTotal.setText("-");
            TingkatResiko.setText("-");
        }
    }
    
    private void isTotalResikoHumpty(){
        try {
            NilaiResikoHumptyTotal.setText((Integer.parseInt(NilaiHumpty1.getText())+Integer.parseInt(NilaiHumpty2.getText())+Integer.parseInt(NilaiHumpty3.getText())+Integer.parseInt(NilaiHumpty4.getText())+Integer.parseInt(NilaiHumpty5.getText())+Integer.parseInt(NilaiHumpty6.getText()))+"");
            if(Integer.parseInt(NilaiResikoHumptyTotal.getText())<11){
                TingkatResikoHumpty.setText("Keterangan : Risiko Rendah (7-11)");
            }else if(Integer.parseInt(NilaiResikoHumptyTotal.getText())>12){
                TingkatResikoHumpty.setText("Keterangan : Risiko Tinggi (>12)");
            }
        } catch (Exception e) {
            NilaiResikoHumptyTotal.setText("-");
            TingkatResikoHumpty.setText("-");
        }
    }
    
    private void isTotalResikoEdmonson(){
        try {
            NilaiResikoEdmonsonTotal.setText((Integer.parseInt(NilaiEdmonson1.getText())+Integer.parseInt(NilaiEdmonson2.getText())+Integer.parseInt(NilaiEdmonson3.getText())+Integer.parseInt(NilaiEdmonson4.getText())+Integer.parseInt(NilaiEdmonson5.getText())+Integer.parseInt(NilaiEdmonson6.getText())+Integer.parseInt(NilaiEdmonson7.getText())+Integer.parseInt(NilaiEdmonson8.getText())+Integer.parseInt(NilaiEdmonson9.getText()))+"");
            if(Integer.parseInt(NilaiResikoEdmonsonTotal.getText())<90){
                TingkatResikoEdmonson.setText("Tidak Berisiko (<90) \n"
                        + "1. Orientasikan pasien pada lingkungan kamar / bangsal \n"
                        + "2. Pastikan rem tempat tidur terkunci \n"
                        + "3. Pastikan bel terjangkau \n"
                        + "4. Singkirkan barang yang berbahaya terutama pada malam hari (kursi tambahan dan lain-lain) \n"
                        + "5. Minta persetujuan pasien agar lampu malam tetap menyala karena lingkungan masih asing \n"
                        + "6. Pastikan alat bantu jalan dalam jangkauan (bila menggunakan) \n"
                        + "7. Pastikan alas kaki tidak licin \n"
                        + "8. Pastikan kebutuhan pribadi dalam jangkauan \n"
                        + "9. Tempatkan meja pasien dengan baik agar tidak menghalangi \n"
                        + "10. Tempat pasien sesuai dengan tinggi badannya \n"
                        + "CATATAN : \n"
                        + "- Kolaborasikan untuk mengatasi area masalah pasien dengan tim kesehatan lain \n"
                        + "- Komunikasikan status risiko tinggi jatuh pasien setiap pergantian shift dan setiap pindah keruangan lain \n"
                        + "- Berikan perhatian khusus terhadap hasil penilaian risiko jatuh pasien \n"
);
            }else if(Integer.parseInt(NilaiResikoEdmonsonTotal.getText())>90){
                TingkatResikoEdmonson.setText("Berisiko (>90) \n"
                        + "1. Lakukan tindakan sesuai skor (≥ 90) \n"
                        + "2. Pasang penanda risiko jatuh pada pintu kamar bagian atas / brankard \n"
                        + "3. Awasi atau bantu sebagian ADL pasien \n"
                        + "4. Cepat menanggapi keluhan pasien \n"
                        + "5. Review kembali obat-obatan yang berisiko \n"
                        + "6. Beritahu pasien agar mobilisasi secara bertahap : duduk perlahan-lahan sebelum berdiri \n"
                        + "7. Libatkan pasien secara aktif \n"
                        + "CATATAN : \n"
                        + "- Kolaborasikan untuk mengatasi area masalah pasien dengan tim kesehatan lain \n"
                        + "- Komunikasikan status risiko tinggi jatuh pasien setiap pergantian shift dan setiap pindah keruangan lain \n"
                        + "- Berikan perhatian khusus terhadap hasil penilaian risiko jatuh pasien \n"
                );
            }
        } catch (Exception e) {
            NilaiResikoHumptyTotal.setText("-");
            TingkatResikoEdmonson.setText("");
        }
    }

    public void emptTeks() {
        TglAsuhan.setDate(new Date());
        Informasi.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        GCS.setText("");
        BB.setText("");
        TB.setText("");
        BMI.setText("");
        KeluhanUtama.setText("-");
        RPK.setText("-");
        RPD.setText("-");
        RPO.setText("-");
        Alergi.setText("-");
        AlatBantu.setSelectedIndex(0);
        KetBantu.setText("-");
        Prothesa.setSelectedIndex(0);
        KetProthesa.setText("-");
        ADL.setSelectedIndex(0);
        StatusPsiko.setSelectedIndex(0);
        KetPsiko.setText("-");
        HubunganKeluarga.setSelectedIndex(0);
        TinggalDengan.setSelectedIndex(0);
        KetTinggal.setText("-");
        Ekonomi.setSelectedIndex(0);
        StatusBudaya.setSelectedIndex(0);
        KetBudaya.setText("-");
        Edukasi.setSelectedIndex(0);
        KetEdukasi.setText("-");
        ATS.setSelectedIndex(0);
        BJM.setSelectedIndex(0);
        MSA.setSelectedIndex(0);
        Hasil.setSelectedIndex(0);
        Lapor.setSelectedIndex(0);
        KetLapor.setText("-");
        SG1.setSelectedIndex(0);
        Nilai1.setSelectedIndex(0);
        SG2.setSelectedIndex(0);
        Nilai2.setSelectedIndex(0);
        TotalHasil.setText("0");
        Nyeri.setSelectedIndex(0);
        Provokes.setSelectedIndex(0);
        KetProvokes.setText("-");
        Quality.setSelectedIndex(0);
        KetQuality.setText("-");
        Lokasi.setText("-");
        Menyebar.setSelectedIndex(0);
        SkalaNyeri.setSelectedIndex(0);
        Durasi.setText("-");
        NyeriHilang.setSelectedIndex(0);
        KetNyeri.setText("-");
        PadaDokter.setSelectedIndex(0);
        KetDokter.setText("-");
        Rencana.setText("-");
        SkalaResiko1.setSelectedIndex(0);
        NilaiResiko1.setText("-");
        SkalaResiko2.setSelectedIndex(0);
        NilaiResiko2.setText("-");
        SkalaResiko3.setSelectedIndex(0);
        NilaiResiko3.setText("-");
        SkalaResiko4.setSelectedIndex(0);
        NilaiResiko4.setText("-");
        SkalaResiko5.setSelectedIndex(0);
        NilaiResiko5.setText("-");
        SkalaResiko6.setSelectedIndex(0);
        NilaiResiko6.setText("-");
        NilaiResikoTotal.setText("-");
        SkalaHumpty1.setSelectedIndex(0);
        NilaiHumpty1.setText("-");
        SkalaHumpty2.setSelectedIndex(0);
        NilaiHumpty2.setText("-");
        SkalaHumpty3.setSelectedIndex(0);
        NilaiHumpty3.setText("-");
        SkalaHumpty4.setSelectedIndex(0);
        NilaiHumpty4.setText("-");
        SkalaHumpty5.setSelectedIndex(0);
        NilaiHumpty5.setText("-");
        SkalaHumpty6.setSelectedIndex(0);
        NilaiHumpty6.setText("-");
        NilaiResikoHumptyTotal.setText("-");
        SkalaEdmonson1.setSelectedIndex(0);
        NilaiEdmonson1.setText("-");
        SkalaEdmonson2.setSelectedIndex(0);
        NilaiEdmonson2.setText("-");
        SkalaEdmonson3.setSelectedIndex(0);
        NilaiEdmonson3.setText("-");
        SkalaEdmonson4.setSelectedIndex(0);
        NilaiEdmonson4.setText("-");
        SkalaEdmonson5.setSelectedIndex(0);
        NilaiEdmonson5.setText("-");
        SkalaEdmonson6.setSelectedIndex(0);
        NilaiEdmonson6.setText("-");
        SkalaEdmonson7.setSelectedIndex(0);
        NilaiEdmonson7.setText("-");
        SkalaEdmonson8.setSelectedIndex(0);
        NilaiEdmonson8.setText("-");
        SkalaEdmonson9.setSelectedIndex(0);
        NilaiEdmonson9.setText("-");
        NilaiResikoEdmonsonTotal.setText("-");
        for (i = 0; i < tabModeMasalah.getRowCount(); i++) {
            tabModeMasalah.setValueAt(false,i,0);
        }
        TabRawat.setSelectedIndex(0);
        Informasi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            Agama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Bahasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            CacatFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            Informasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            BMI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            AlatBantu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            KetBantu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Prothesa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            KetProthesa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            ADL.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            StatusPsiko.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            KetPsiko.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            HubunganKeluarga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            TinggalDengan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            KetTinggal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Ekonomi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            StatusBudaya.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            KetBudaya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Edukasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            KetEdukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            ATS.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            BJM.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            MSA.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Hasil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Lapor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            KetLapor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            SG1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Nilai1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            SG2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Nilai2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            TotalHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Provokes.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            KetProvokes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Quality.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            KetQuality.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Menyebar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            SkalaNyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            Durasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            NyeriHilang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            KetNyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            PadaDokter.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            KetDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            Rencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            SkalaResiko1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            NilaiResiko1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            SkalaResiko2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            NilaiResiko2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            SkalaResiko3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            NilaiResiko3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            SkalaResiko4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            NilaiResiko4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            SkalaResiko5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            NilaiResiko5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            SkalaResiko6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            NilaiResiko6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            NilaiResikoTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            SkalaHumpty1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            NilaiHumpty1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            SkalaHumpty2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            NilaiHumpty2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            SkalaHumpty3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            NilaiHumpty3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            SkalaHumpty4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            NilaiHumpty4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            SkalaHumpty5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            NilaiHumpty5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            SkalaHumpty6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            NilaiHumpty6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            NilaiResikoHumptyTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            SkalaEdmonson1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            NilaiEdmonson1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString());
            SkalaEdmonson2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),93).toString());
            NilaiEdmonson2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),94).toString());
            SkalaEdmonson3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),95).toString());
            NilaiEdmonson3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),96).toString());
            SkalaEdmonson4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),97).toString());
            NilaiEdmonson4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),98).toString());
            SkalaEdmonson5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),99).toString());
            NilaiEdmonson5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),100).toString());
            SkalaEdmonson6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),101).toString());
            NilaiEdmonson6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),102).toString());
            SkalaEdmonson7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),103).toString());
            NilaiEdmonson7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),104).toString());
            SkalaEdmonson8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),105).toString());
            NilaiEdmonson8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),106).toString());
            SkalaEdmonson9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),107).toString());
            NilaiEdmonson9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),108).toString());
            NilaiResikoEdmonsonTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),109).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            
            try {
                Valid.tabelKosong(tabModeMasalah);
                
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                        "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                        "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeMasalah.addRow(new Object[]{true,rs.getString(1),rs.getString(2)});
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

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
        try {
            ps=koneksi.prepareStatement(
                    "select nm_pasien, if(jk='L','Laki-Laki','Perempuan') as jk,tgl_lahir,agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                    "where no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Agama.setText(rs.getString("agama"));
                    Bahasa.setText(rs.getString("nama_bahasa"));
                    CacatFisik.setText(rs.getString("nama_cacat"));
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
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2); 
        KeluhanUtama.setText(Sequel.cariIsi("select keluhan from pemeriksaan_mandiri where no_rawat='"+norwt+"'"));
        RPD.setText(Sequel.cariIsi("select rpd from pemeriksaan_mandiri where no_rawat='"+norwt+"'"));
        RPK.setText(Sequel.cariIsi("select rpk from pemeriksaan_mandiri where no_rawat='"+norwt+"'"));
        RPO.setText(Sequel.cariIsi("select rpo from pemeriksaan_mandiri where no_rawat='"+norwt+"'"));
        Alergi.setText(Sequel.cariIsi("select alergi from pemeriksaan_mandiri where no_rawat='"+norwt+"'"));
        isRawat(); 
           
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        BtnTambahMasalah.setEnabled(akses.getmaster_masalah_keperawatan());  
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

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }
    
    private void tampilMasalah() {
        try{
            jml=0;
            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            masalah=null;
            masalah=new String[jml];

            index=0;        
            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbMasalahKeperawatan.getValueAt(i,1).toString();
                    masalah[index]=tbMasalahKeperawatan.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeMasalah);

            for(i=0;i<jml;i++){
                tabModeMasalah.addRow(new Object[] {
                    pilih[i],kode[i],masalah[i]
                });
            }
            ps=koneksi.prepareStatement("select * from master_masalah_keperawatan where kode_masalah like ? or nama_masalah like ? order by kode_masalah");
            try {
                ps.setString(1,"%"+TCariMasalah.getText().trim()+"%");
                ps.setString(2,"%"+TCariMasalah.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeMasalah.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
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
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
            FormMenu.setVisible(true);  
            FormMasalahRencana.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){   
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);  
            FormMasalahRencana.setVisible(false);   
            ChkAccor.setVisible(true);
        }
    }

    private void getMasalah() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRM1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            DetailRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            try {
                Valid.tabelKosong(tabModeDetailMasalah);
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                        "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                        "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeDetailMasalah.addRow(new Object[]{rs.getString(1),rs.getString(2)});
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
    
    private void isBMI(){
        if((!TB.getText().equals(""))&&(!BB.getText().equals(""))){
            BMI.setText(Valid.SetAngka7(Valid.SetAngka(BB.getText())/((Valid.SetAngka(TB.getText())/100)*(Valid.SetAngka(TB.getText())/100)))+"");
        }
    }
}
