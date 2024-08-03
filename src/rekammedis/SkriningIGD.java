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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPegawai;
import kepegawaian.DlgCariPetugas;
import rekammedis.DlgMasterRencanaKeperawatan;


/**
 *
 * @author perpustakaan
 */
public final class SkriningIGD extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeDetailMasalah;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,jml=0,index=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahkeperawatan=""; 
    private StringBuilder htmlContent;
    public DlgMasterRencanaKeperawatan masterr=new DlgMasterRencanaKeperawatan(null,false);
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
//    private SimpleDateFormat tanggalNow = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public SkriningIGD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","1. Riwayat Jatuh","Nilai 1","2. Diagnosis Sekunder (≥ 2 Diagnosis Medis)",
            "Nilai 2","3. Alat Bantu","Nilai 3","4. Terpasang Infuse","Nilai 4","5. Gaya Berjalan","Nilai 5","6. Status Mental","Nilai 6","Total Nilai Morse",
            "1. Umur","Nilai 1","2. Jenis Kelamin","Nilai 2","3. Diagnosa","Nilai 3","4. Gangguan Kognitif","Nilai 4","5. Faktor Lingkungan","Nilai 5",
            "6. Pembedahan/Sedasi/Anestesi","Nilai 6","Total Nilai Humpty Dumpty","1. Usia","Nilai 1","2. Status Mental","Nilai 2","3. Kliminasi","Nilai 3","4. Pengobatan","Nilai 4","5. Diagnosa","Nilai 5",
            "6. Ambulasi / Keseimbangan","Nilai 6","7. Nutrisi","Nilai 7","8. Gangguan Pola Tidur","Nilai 8","9. Riwayat Jatuh","Nilai 9","Total Nilai Edmonson",
            "1. Kondisi Fisik Umum","Nilai 1","2. Kesadaran","Nilai 2","3. Aktivitas","Nilai 3","4. Mobilitas","Nilai 4","5. Inkontinensia","Nilai 5","Total Nilai Norton",
            "Riwayat Demam","Berkeringat Pada Malam Hari Tanpa Aktifitas","Riwayat Berpergian Dari Daerah Wabah","Riwayat Pemakaian Obat Jangka Panjang",
            "Riwayat BB Turun Tanpa Sebab Yang Diketahui","1. Penurunan BB Dalam Waktu 6 Bulan Terakhir","Ket Gizi 1","2. Penurunan asupan makanan karena nafsu makan berkurang",
            "Ket Gizi 2","3. Gejala gastrointestinal (mual, muntah, diare, anorexia)","Ket Gizi 3","4. Faktor pemberat (komorbid)","Ket Gizi 4","5. Penurunan kapasitas fungsional",
            "Ket Gizi 5"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 80; i++) {
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
                column.setPreferredWidth(75);
            }else if(i==44){
                column.setPreferredWidth(75);
            }else if(i==40){
                column.setPreferredWidth(35);
            }else if(i==41){
                column.setPreferredWidth(40);
            }else if(i==42){
                column.setPreferredWidth(35);
            }else if(i==43){
                column.setPreferredWidth(40);
            }else if(i==44){
                column.setPreferredWidth(35);
            }else if(i==45){
                column.setPreferredWidth(35);
            }else if(i==46){
                column.setPreferredWidth(35);
            }else if(i==47){
                column.setPreferredWidth(35);
            }else if(i==48){
                column.setPreferredWidth(180);
            }else if(i==49){
                column.setPreferredWidth(150);
            }else if(i==50){
                column.setPreferredWidth(150);
            }else if(i==51){
                column.setPreferredWidth(150);
            }else if(i==52){
                column.setPreferredWidth(100);
            }else if(i==53){
                column.setPreferredWidth(60);
            }else if(i==54){
                column.setPreferredWidth(90);
            }else if(i==55){
                column.setPreferredWidth(60);
            }else if(i==56){
                column.setPreferredWidth(90);
            }else if(i==57){
                column.setPreferredWidth(60);
            }else if(i==58){
                column.setPreferredWidth(80);
            }else if(i==59){
                column.setPreferredWidth(100);
            }else if(i==60){
                column.setPreferredWidth(103);
            }else if(i==61){
                column.setPreferredWidth(87);
            }else if(i==62){
                column.setPreferredWidth(90);
            }else if(i==63){
                column.setPreferredWidth(50);
            }else if(i==64){
                column.setPreferredWidth(58);
            }else if(i==65){
                column.setPreferredWidth(90);
            }else if(i==66){
                column.setPreferredWidth(60);
            }else if(i==67){
                column.setPreferredWidth(90);
            }else if(i==68){
                column.setPreferredWidth(87);
            }else if(i==69){
                column.setPreferredWidth(87);
            }else if(i==70){
                column.setPreferredWidth(87);
            }else if(i==71){
                column.setPreferredWidth(206);
            }else if(i==72){
                column.setPreferredWidth(75);
            }else if(i==73){
                column.setPreferredWidth(75);
            }else if(i==74){
                column.setPreferredWidth(75);
            }else if(i==75){
                column.setPreferredWidth(87);
            }else if(i==76){
                column.setPreferredWidth(206);
            }else if(i==77){
                column.setPreferredWidth(75);
            }else if(i==78){
                column.setPreferredWidth(75);
            }else if(i==79){
                column.setPreferredWidth(75);
            }else if(i==80){
                column.setPreferredWidth(75);
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
//        tbMasalahKeperawatan.setModel(tabModeMasalah);
//
//        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
//        tbMasalahKeperawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
//        tbMasalahKeperawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        
//        for (i = 0; i < 3; i++) {
//            TableColumn column = tbMasalahKeperawatan.getColumnModel().getColumn(i);
//            if(i==0){
//                column.setPreferredWidth(20);
//            }else if(i==1){
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
//            }else if(i==2){
//                column.setPreferredWidth(350);
//            }
//        }
//        tbMasalahKeperawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMasalah=new DefaultTableModel(null,new Object[]{
                "Kode","Masalah Keperawatan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
//        tbMasalahDetailMasalah.setModel(tabModeDetailMasalah);
//
//        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
//        tbMasalahDetailMasalah.setPreferredScrollableViewportSize(new Dimension(500,500));
//        tbMasalahDetailMasalah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//
//        for (i = 0; i < 2; i++) {
//            TableColumn column = tbMasalahDetailMasalah.getColumnModel().getColumn(i);
//            if(i==0){
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
//            }else if(i==1){
//                column.setPreferredWidth(420);
//            }
//        }
//        tbMasalahDetailMasalah.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdPetugas.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("SkriningIGD")){
                    if(pegawai.getTable().getSelectedRow()!= -1){   
//                        KdPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
//                        TPegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
//                        Jabatan.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),3).toString());
//                        KdPeg.requestFocus();  
                        KdPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
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
        
//        BB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                isBMI();
//            }
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                isBMI();
//            }
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                isBMI();
//            }
//        });
//        
//        TB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                isBMI();
//            }
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                isBMI();
//            }
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                isBMI();
//            }
//        });
        
//        masterr.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(akses.getform().equals("RMPenilaianAwalKeperawatanRalan")){
//                    if(masterr.getTable().getSelectedRow()!= -1){
//                        KetFisik.setText(masterr.getTable().getValueAt(masterr.getTable().getSelectedRow(),3).toString());
//                    }  
//                    KetFisik.requestFocus();
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
        
        
//        ChkAccor.setSelected(false);
//        isMenu();
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
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        KetGizi1 = new widget.TextArea();
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
        jLabel100 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
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
        jSeparator3 = new javax.swing.JSeparator();
        jLabel103 = new widget.Label();
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
        jSeparator4 = new javax.swing.JSeparator();
        jLabel104 = new widget.Label();
        jLabel247 = new widget.Label();
        SkalaNorton1 = new widget.ComboBox();
        jLabel248 = new widget.Label();
        NilaiNorton1 = new widget.TextBox();
        jLabel249 = new widget.Label();
        jLabel250 = new widget.Label();
        jLabel251 = new widget.Label();
        SkalaNorton2 = new widget.ComboBox();
        NilaiNorton2 = new widget.TextBox();
        jLabel252 = new widget.Label();
        jLabel253 = new widget.Label();
        jLabel254 = new widget.Label();
        SkalaNorton3 = new widget.ComboBox();
        jLabel255 = new widget.Label();
        NilaiNorton3 = new widget.TextBox();
        jLabel256 = new widget.Label();
        jLabel257 = new widget.Label();
        SkalaNorton4 = new widget.ComboBox();
        jLabel258 = new widget.Label();
        NilaiNorton4 = new widget.TextBox();
        jLabel259 = new widget.Label();
        jLabel260 = new widget.Label();
        SkalaNorton5 = new widget.ComboBox();
        jLabel261 = new widget.Label();
        NilaiNorton5 = new widget.TextBox();
        jLabel265 = new widget.Label();
        NilaiResikoTotalNorton = new widget.TextBox();
        TingkatResiko3 = new widget.Label();
        TingkatResikoNorton = new widget.Label();
        jLabel105 = new widget.Label();
        TingkatResiko8 = new widget.Label();
        TingkatResiko9 = new widget.Label();
        jLabel106 = new widget.Label();
        TingkatResiko10 = new widget.Label();
        TingkatResiko11 = new widget.Label();
        TingkatResiko12 = new widget.Label();
        Batuk2 = new widget.ComboBox();
        TingkatResiko13 = new widget.Label();
        TingkatResiko14 = new widget.Label();
        Batuk1 = new widget.ComboBox();
        Batuk3 = new widget.ComboBox();
        Batuk4 = new widget.ComboBox();
        Batuk5 = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel107 = new widget.Label();
        TingkatResiko16 = new widget.Label();
        TingkatResiko17 = new widget.Label();
        Gizi2 = new widget.ComboBox();
        TingkatResiko20 = new widget.Label();
        Gizi1 = new widget.ComboBox();
        scrollPane13 = new widget.ScrollPane();
        KetGizi2 = new widget.TextArea();
        TingkatResiko21 = new widget.Label();
        TingkatResiko22 = new widget.Label();
        Gizi3 = new widget.ComboBox();
        scrollPane14 = new widget.ScrollPane();
        KetGizi3 = new widget.TextArea();
        TingkatResiko23 = new widget.Label();
        TingkatResiko24 = new widget.Label();
        Gizi4 = new widget.ComboBox();
        scrollPane15 = new widget.ScrollPane();
        KetGizi4 = new widget.TextArea();
        TingkatResiko25 = new widget.Label();
        TingkatResiko26 = new widget.Label();
        Gizi5 = new widget.ComboBox();
        scrollPane16 = new widget.ScrollPane();
        KetGizi5 = new widget.TextArea();
        TingkatResiko27 = new widget.Label();
        TingkatResiko = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        TingkatResikoEdmonson = new widget.TextArea();
        jLabel262 = new widget.Label();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Screening IGD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 2030));
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
        KdPetugas.setBounds(74, 40, 140, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(215, 40, 220, 23);

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
        BtnDokter.setBounds(430, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 1490, 880, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("Morse Fall Scale");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(40, 110, 180, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        KetGizi1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetGizi1.setColumns(20);
        KetGizi1.setRows(5);
        KetGizi1.setName("KetGizi1"); // NOI18N
        KetGizi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGizi1KeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(KetGizi1);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(130, 1730, 260, 43);

        jLabel181.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel181.setText("1. Riwayat Jatuh");
        jLabel181.setName("jLabel181"); // NOI18N
        FormInput.add(jLabel181);
        jLabel181.setBounds(40, 150, 300, 23);

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
        SkalaResiko1.setBounds(420, 150, 280, 23);

        jLabel182.setText("Nilai :");
        jLabel182.setName("jLabel182"); // NOI18N
        FormInput.add(jLabel182);
        jLabel182.setBounds(710, 150, 75, 23);

        NilaiResiko1.setEditable(false);
        NilaiResiko1.setFocusTraversalPolicyProvider(true);
        NilaiResiko1.setName("NilaiResiko1"); // NOI18N
        FormInput.add(NilaiResiko1);
        NilaiResiko1.setBounds(790, 150, 60, 23);

        jLabel183.setText("Skala :");
        jLabel183.setName("jLabel183"); // NOI18N
        FormInput.add(jLabel183);
        jLabel183.setBounds(340, 150, 80, 23);

        jLabel184.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel184.setText("2. Diagnosis Sekunder (≥ 2 Diagnosis Medis)");
        jLabel184.setName("jLabel184"); // NOI18N
        FormInput.add(jLabel184);
        jLabel184.setBounds(40, 180, 300, 23);

        jLabel185.setText("Skala :");
        jLabel185.setName("jLabel185"); // NOI18N
        FormInput.add(jLabel185);
        jLabel185.setBounds(340, 180, 80, 23);

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
        SkalaResiko2.setBounds(420, 180, 280, 23);

        NilaiResiko2.setEditable(false);
        NilaiResiko2.setFocusTraversalPolicyProvider(true);
        NilaiResiko2.setName("NilaiResiko2"); // NOI18N
        FormInput.add(NilaiResiko2);
        NilaiResiko2.setBounds(790, 180, 60, 23);

        jLabel186.setText("Nilai :");
        jLabel186.setName("jLabel186"); // NOI18N
        FormInput.add(jLabel186);
        jLabel186.setBounds(710, 180, 75, 23);

        jLabel187.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel187.setText("3. Alat Bantu");
        jLabel187.setName("jLabel187"); // NOI18N
        FormInput.add(jLabel187);
        jLabel187.setBounds(40, 210, 300, 23);

        jLabel188.setText("Skala :");
        jLabel188.setName("jLabel188"); // NOI18N
        FormInput.add(jLabel188);
        jLabel188.setBounds(340, 210, 80, 23);

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
        SkalaResiko3.setBounds(420, 210, 280, 23);

        jLabel189.setText("Nilai :");
        jLabel189.setName("jLabel189"); // NOI18N
        FormInput.add(jLabel189);
        jLabel189.setBounds(710, 210, 75, 23);

        NilaiResiko3.setEditable(false);
        NilaiResiko3.setFocusTraversalPolicyProvider(true);
        NilaiResiko3.setName("NilaiResiko3"); // NOI18N
        FormInput.add(NilaiResiko3);
        NilaiResiko3.setBounds(790, 210, 60, 23);

        jLabel190.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel190.setText("4. Terpasang Infuse");
        jLabel190.setName("jLabel190"); // NOI18N
        FormInput.add(jLabel190);
        jLabel190.setBounds(40, 240, 300, 23);

        jLabel191.setText("Skala :");
        jLabel191.setName("jLabel191"); // NOI18N
        FormInput.add(jLabel191);
        jLabel191.setBounds(340, 240, 80, 23);

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
        SkalaResiko4.setBounds(420, 240, 280, 23);

        jLabel192.setText("Nilai :");
        jLabel192.setName("jLabel192"); // NOI18N
        FormInput.add(jLabel192);
        jLabel192.setBounds(710, 240, 75, 23);

        NilaiResiko4.setEditable(false);
        NilaiResiko4.setFocusTraversalPolicyProvider(true);
        NilaiResiko4.setName("NilaiResiko4"); // NOI18N
        FormInput.add(NilaiResiko4);
        NilaiResiko4.setBounds(790, 240, 60, 23);

        jLabel193.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel193.setText("5. Gaya Berjalan");
        jLabel193.setName("jLabel193"); // NOI18N
        FormInput.add(jLabel193);
        jLabel193.setBounds(40, 270, 300, 23);

        jLabel194.setText("Skala :");
        jLabel194.setName("jLabel194"); // NOI18N
        FormInput.add(jLabel194);
        jLabel194.setBounds(340, 270, 80, 23);

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
        SkalaResiko5.setBounds(420, 270, 280, 23);

        jLabel195.setText("Nilai :");
        jLabel195.setName("jLabel195"); // NOI18N
        FormInput.add(jLabel195);
        jLabel195.setBounds(710, 270, 75, 23);

        NilaiResiko5.setEditable(false);
        NilaiResiko5.setFocusTraversalPolicyProvider(true);
        NilaiResiko5.setName("NilaiResiko5"); // NOI18N
        FormInput.add(NilaiResiko5);
        NilaiResiko5.setBounds(790, 270, 60, 23);

        jLabel196.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel196.setText("6. Status Mental");
        jLabel196.setName("jLabel196"); // NOI18N
        FormInput.add(jLabel196);
        jLabel196.setBounds(40, 300, 300, 23);

        jLabel197.setText("Skala :");
        jLabel197.setName("jLabel197"); // NOI18N
        FormInput.add(jLabel197);
        jLabel197.setBounds(340, 300, 80, 23);

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
        SkalaResiko6.setBounds(420, 300, 280, 23);

        jLabel198.setText("Nilai :");
        jLabel198.setName("jLabel198"); // NOI18N
        FormInput.add(jLabel198);
        jLabel198.setBounds(710, 300, 75, 23);

        NilaiResiko6.setEditable(false);
        NilaiResiko6.setFocusTraversalPolicyProvider(true);
        NilaiResiko6.setName("NilaiResiko6"); // NOI18N
        FormInput.add(NilaiResiko6);
        NilaiResiko6.setBounds(790, 300, 60, 23);

        jLabel199.setText("Total :");
        jLabel199.setName("jLabel199"); // NOI18N
        FormInput.add(jLabel199);
        jLabel199.setBounds(710, 330, 75, 23);

        NilaiResikoTotal.setEditable(false);
        NilaiResikoTotal.setFocusTraversalPolicyProvider(true);
        NilaiResikoTotal.setName("NilaiResikoTotal"); // NOI18N
        FormInput.add(NilaiResikoTotal);
        NilaiResikoTotal.setBounds(790, 330, 60, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("III. BATUK");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(30, 1500, 180, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 430, 880, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("Humpty Dumpty Scale ");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(40, 440, 180, 23);

        jLabel200.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel200.setText("1. Umur");
        jLabel200.setName("jLabel200"); // NOI18N
        FormInput.add(jLabel200);
        jLabel200.setBounds(40, 480, 300, 23);

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
        SkalaHumpty1.setBounds(420, 480, 280, 23);

        jLabel201.setText("Nilai :");
        jLabel201.setName("jLabel201"); // NOI18N
        FormInput.add(jLabel201);
        jLabel201.setBounds(710, 480, 75, 23);

        NilaiHumpty1.setEditable(false);
        NilaiHumpty1.setFocusTraversalPolicyProvider(true);
        NilaiHumpty1.setName("NilaiHumpty1"); // NOI18N
        FormInput.add(NilaiHumpty1);
        NilaiHumpty1.setBounds(790, 480, 60, 23);

        jLabel202.setText("Skala :");
        jLabel202.setName("jLabel202"); // NOI18N
        FormInput.add(jLabel202);
        jLabel202.setBounds(340, 480, 80, 23);

        jLabel203.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel203.setText("2. Jenis Kelamin");
        jLabel203.setName("jLabel203"); // NOI18N
        FormInput.add(jLabel203);
        jLabel203.setBounds(40, 510, 300, 23);

        jLabel204.setText("Skala :");
        jLabel204.setName("jLabel204"); // NOI18N
        FormInput.add(jLabel204);
        jLabel204.setBounds(340, 510, 80, 23);

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
        SkalaHumpty2.setBounds(420, 510, 280, 23);

        NilaiHumpty2.setEditable(false);
        NilaiHumpty2.setFocusTraversalPolicyProvider(true);
        NilaiHumpty2.setName("NilaiHumpty2"); // NOI18N
        FormInput.add(NilaiHumpty2);
        NilaiHumpty2.setBounds(790, 510, 60, 23);

        jLabel205.setText("Nilai :");
        jLabel205.setName("jLabel205"); // NOI18N
        FormInput.add(jLabel205);
        jLabel205.setBounds(710, 510, 75, 23);

        jLabel206.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel206.setText("3. Diagnosa");
        jLabel206.setName("jLabel206"); // NOI18N
        FormInput.add(jLabel206);
        jLabel206.setBounds(40, 540, 300, 23);

        jLabel207.setText("Skala :");
        jLabel207.setName("jLabel207"); // NOI18N
        FormInput.add(jLabel207);
        jLabel207.setBounds(340, 540, 80, 23);

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
        SkalaHumpty3.setBounds(420, 540, 280, 23);

        jLabel208.setText("Nilai :");
        jLabel208.setName("jLabel208"); // NOI18N
        FormInput.add(jLabel208);
        jLabel208.setBounds(710, 540, 75, 23);

        NilaiHumpty3.setEditable(false);
        NilaiHumpty3.setFocusTraversalPolicyProvider(true);
        NilaiHumpty3.setName("NilaiHumpty3"); // NOI18N
        FormInput.add(NilaiHumpty3);
        NilaiHumpty3.setBounds(790, 540, 60, 23);

        jLabel209.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel209.setText("4. Gangguan Kognitif");
        jLabel209.setName("jLabel209"); // NOI18N
        FormInput.add(jLabel209);
        jLabel209.setBounds(40, 570, 300, 23);

        jLabel210.setText("Skala :");
        jLabel210.setName("jLabel210"); // NOI18N
        FormInput.add(jLabel210);
        jLabel210.setBounds(340, 570, 80, 23);

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
        SkalaHumpty4.setBounds(420, 570, 280, 23);

        jLabel211.setText("Nilai :");
        jLabel211.setName("jLabel211"); // NOI18N
        FormInput.add(jLabel211);
        jLabel211.setBounds(710, 570, 75, 23);

        NilaiHumpty4.setEditable(false);
        NilaiHumpty4.setFocusTraversalPolicyProvider(true);
        NilaiHumpty4.setName("NilaiHumpty4"); // NOI18N
        FormInput.add(NilaiHumpty4);
        NilaiHumpty4.setBounds(790, 570, 60, 23);

        jLabel212.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel212.setText("5. Faktor Lingkungan");
        jLabel212.setName("jLabel212"); // NOI18N
        FormInput.add(jLabel212);
        jLabel212.setBounds(40, 600, 300, 23);

        jLabel213.setText("Skala :");
        jLabel213.setName("jLabel213"); // NOI18N
        FormInput.add(jLabel213);
        jLabel213.setBounds(340, 600, 80, 23);

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
        SkalaHumpty5.setBounds(420, 600, 280, 23);

        jLabel214.setText("Nilai :");
        jLabel214.setName("jLabel214"); // NOI18N
        FormInput.add(jLabel214);
        jLabel214.setBounds(710, 600, 75, 23);

        NilaiHumpty5.setEditable(false);
        NilaiHumpty5.setFocusTraversalPolicyProvider(true);
        NilaiHumpty5.setName("NilaiHumpty5"); // NOI18N
        FormInput.add(NilaiHumpty5);
        NilaiHumpty5.setBounds(790, 600, 60, 23);

        jLabel215.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel215.setText("6. Pembedahan/Sedasi/Anestesi");
        jLabel215.setName("jLabel215"); // NOI18N
        FormInput.add(jLabel215);
        jLabel215.setBounds(40, 630, 300, 23);

        jLabel216.setText("Skala :");
        jLabel216.setName("jLabel216"); // NOI18N
        FormInput.add(jLabel216);
        jLabel216.setBounds(340, 630, 80, 23);

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
        SkalaHumpty6.setBounds(420, 630, 280, 23);

        jLabel217.setText("Nilai :");
        jLabel217.setName("jLabel217"); // NOI18N
        FormInput.add(jLabel217);
        jLabel217.setBounds(710, 630, 75, 23);

        NilaiHumpty6.setEditable(false);
        NilaiHumpty6.setFocusTraversalPolicyProvider(true);
        NilaiHumpty6.setName("NilaiHumpty6"); // NOI18N
        FormInput.add(NilaiHumpty6);
        NilaiHumpty6.setBounds(790, 630, 60, 23);

        jLabel218.setText("Total :");
        jLabel218.setName("jLabel218"); // NOI18N
        FormInput.add(jLabel218);
        jLabel218.setBounds(710, 660, 75, 23);

        NilaiResikoHumptyTotal.setEditable(false);
        NilaiResikoHumptyTotal.setFocusTraversalPolicyProvider(true);
        NilaiResikoHumptyTotal.setName("NilaiResikoHumptyTotal"); // NOI18N
        FormInput.add(NilaiResikoHumptyTotal);
        NilaiResikoHumptyTotal.setBounds(790, 660, 60, 23);

        TingkatResikoHumpty.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResikoHumpty.setText("Keterangan : Risiko Rendah (7-11)");
        TingkatResikoHumpty.setName("TingkatResikoHumpty"); // NOI18N
        FormInput.add(TingkatResikoHumpty);
        TingkatResikoHumpty.setBounds(40, 690, 810, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 720, 880, 1);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("Edmonson Psychiatric Fall Risk Assessment");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(30, 740, 220, 23);

        jLabel219.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel219.setText("1. Usia");
        jLabel219.setName("jLabel219"); // NOI18N
        FormInput.add(jLabel219);
        jLabel219.setBounds(40, 780, 300, 23);

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
        SkalaEdmonson1.setBounds(420, 780, 280, 23);

        jLabel220.setText("Keterangan :");
        jLabel220.setName("jLabel220"); // NOI18N
        FormInput.add(jLabel220);
        jLabel220.setBounds(850, 740, 75, 23);

        NilaiEdmonson1.setEditable(false);
        NilaiEdmonson1.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson1.setName("NilaiEdmonson1"); // NOI18N
        FormInput.add(NilaiEdmonson1);
        NilaiEdmonson1.setBounds(790, 780, 60, 23);

        jLabel221.setText("Skala :");
        jLabel221.setName("jLabel221"); // NOI18N
        FormInput.add(jLabel221);
        jLabel221.setBounds(340, 780, 80, 23);

        jLabel222.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel222.setText("2. Status Mental");
        jLabel222.setName("jLabel222"); // NOI18N
        FormInput.add(jLabel222);
        jLabel222.setBounds(40, 810, 300, 23);

        jLabel223.setText("Skala :");
        jLabel223.setName("jLabel223"); // NOI18N
        FormInput.add(jLabel223);
        jLabel223.setBounds(340, 810, 80, 23);

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
        SkalaEdmonson2.setBounds(420, 810, 280, 23);

        NilaiEdmonson2.setEditable(false);
        NilaiEdmonson2.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson2.setName("NilaiEdmonson2"); // NOI18N
        FormInput.add(NilaiEdmonson2);
        NilaiEdmonson2.setBounds(790, 810, 60, 23);

        jLabel224.setText("Nilai :");
        jLabel224.setName("jLabel224"); // NOI18N
        FormInput.add(jLabel224);
        jLabel224.setBounds(710, 810, 75, 23);

        jLabel225.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel225.setText("3. Kliminasi");
        jLabel225.setName("jLabel225"); // NOI18N
        FormInput.add(jLabel225);
        jLabel225.setBounds(40, 840, 300, 23);

        jLabel226.setText("Skala :");
        jLabel226.setName("jLabel226"); // NOI18N
        FormInput.add(jLabel226);
        jLabel226.setBounds(340, 840, 80, 23);

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
        SkalaEdmonson3.setBounds(420, 840, 280, 23);

        jLabel227.setText("Nilai :");
        jLabel227.setName("jLabel227"); // NOI18N
        FormInput.add(jLabel227);
        jLabel227.setBounds(710, 840, 75, 23);

        NilaiEdmonson3.setEditable(false);
        NilaiEdmonson3.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson3.setName("NilaiEdmonson3"); // NOI18N
        FormInput.add(NilaiEdmonson3);
        NilaiEdmonson3.setBounds(790, 840, 60, 23);

        jLabel228.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel228.setText("4. Pengobatan");
        jLabel228.setName("jLabel228"); // NOI18N
        FormInput.add(jLabel228);
        jLabel228.setBounds(40, 870, 300, 23);

        jLabel229.setText("Skala :");
        jLabel229.setName("jLabel229"); // NOI18N
        FormInput.add(jLabel229);
        jLabel229.setBounds(340, 870, 80, 23);

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
        SkalaEdmonson4.setBounds(420, 870, 280, 23);

        jLabel230.setText("Nilai :");
        jLabel230.setName("jLabel230"); // NOI18N
        FormInput.add(jLabel230);
        jLabel230.setBounds(710, 870, 75, 23);

        NilaiEdmonson4.setEditable(false);
        NilaiEdmonson4.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson4.setName("NilaiEdmonson4"); // NOI18N
        FormInput.add(NilaiEdmonson4);
        NilaiEdmonson4.setBounds(790, 870, 60, 23);

        jLabel231.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel231.setText("5. Diagnosa");
        jLabel231.setName("jLabel231"); // NOI18N
        FormInput.add(jLabel231);
        jLabel231.setBounds(40, 900, 300, 23);

        jLabel232.setText("Skala :");
        jLabel232.setName("jLabel232"); // NOI18N
        FormInput.add(jLabel232);
        jLabel232.setBounds(340, 900, 80, 23);

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
        SkalaEdmonson5.setBounds(420, 900, 280, 23);

        jLabel233.setText("Nilai :");
        jLabel233.setName("jLabel233"); // NOI18N
        FormInput.add(jLabel233);
        jLabel233.setBounds(710, 900, 75, 23);

        NilaiEdmonson5.setEditable(false);
        NilaiEdmonson5.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson5.setName("NilaiEdmonson5"); // NOI18N
        FormInput.add(NilaiEdmonson5);
        NilaiEdmonson5.setBounds(790, 900, 60, 23);

        jLabel234.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel234.setText("6. Ambulasi / Keseimbangan");
        jLabel234.setName("jLabel234"); // NOI18N
        FormInput.add(jLabel234);
        jLabel234.setBounds(40, 930, 300, 23);

        jLabel235.setText("Skala :");
        jLabel235.setName("jLabel235"); // NOI18N
        FormInput.add(jLabel235);
        jLabel235.setBounds(340, 930, 80, 23);

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
        SkalaEdmonson6.setBounds(420, 930, 280, 23);

        jLabel236.setText("Nilai :");
        jLabel236.setName("jLabel236"); // NOI18N
        FormInput.add(jLabel236);
        jLabel236.setBounds(710, 930, 75, 23);

        NilaiEdmonson6.setEditable(false);
        NilaiEdmonson6.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson6.setName("NilaiEdmonson6"); // NOI18N
        FormInput.add(NilaiEdmonson6);
        NilaiEdmonson6.setBounds(790, 930, 60, 23);

        jLabel237.setText("Total :");
        jLabel237.setName("jLabel237"); // NOI18N
        FormInput.add(jLabel237);
        jLabel237.setBounds(710, 1050, 75, 23);

        NilaiResikoEdmonsonTotal.setEditable(false);
        NilaiResikoEdmonsonTotal.setFocusTraversalPolicyProvider(true);
        NilaiResikoEdmonsonTotal.setName("NilaiResikoEdmonsonTotal"); // NOI18N
        FormInput.add(NilaiResikoEdmonsonTotal);
        NilaiResikoEdmonsonTotal.setBounds(790, 1050, 60, 23);

        jLabel238.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel238.setText("7. Nutrisi");
        jLabel238.setName("jLabel238"); // NOI18N
        FormInput.add(jLabel238);
        jLabel238.setBounds(40, 960, 300, 23);

        jLabel239.setText("Skala :");
        jLabel239.setName("jLabel239"); // NOI18N
        FormInput.add(jLabel239);
        jLabel239.setBounds(340, 960, 80, 23);

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
        SkalaEdmonson7.setBounds(420, 960, 280, 23);

        jLabel240.setText("Nilai :");
        jLabel240.setName("jLabel240"); // NOI18N
        FormInput.add(jLabel240);
        jLabel240.setBounds(710, 960, 75, 23);

        NilaiEdmonson7.setEditable(false);
        NilaiEdmonson7.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson7.setName("NilaiEdmonson7"); // NOI18N
        FormInput.add(NilaiEdmonson7);
        NilaiEdmonson7.setBounds(790, 960, 60, 23);

        jLabel241.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel241.setText("8. Gangguan Pola Tidur");
        jLabel241.setName("jLabel241"); // NOI18N
        FormInput.add(jLabel241);
        jLabel241.setBounds(40, 990, 300, 23);

        jLabel242.setText("Skala :");
        jLabel242.setName("jLabel242"); // NOI18N
        FormInput.add(jLabel242);
        jLabel242.setBounds(340, 990, 80, 23);

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
        SkalaEdmonson8.setBounds(420, 990, 280, 23);

        jLabel243.setText("Nilai :");
        jLabel243.setName("jLabel243"); // NOI18N
        FormInput.add(jLabel243);
        jLabel243.setBounds(710, 990, 75, 23);

        NilaiEdmonson8.setEditable(false);
        NilaiEdmonson8.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson8.setName("NilaiEdmonson8"); // NOI18N
        FormInput.add(NilaiEdmonson8);
        NilaiEdmonson8.setBounds(790, 990, 60, 23);

        jLabel244.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel244.setText("9. Riwayat Jatuh");
        jLabel244.setName("jLabel244"); // NOI18N
        FormInput.add(jLabel244);
        jLabel244.setBounds(40, 1020, 300, 23);

        jLabel245.setText("Skala :");
        jLabel245.setName("jLabel245"); // NOI18N
        FormInput.add(jLabel245);
        jLabel245.setBounds(340, 1020, 80, 23);

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
        SkalaEdmonson9.setBounds(420, 1020, 280, 23);

        jLabel246.setText("Nilai :");
        jLabel246.setName("jLabel246"); // NOI18N
        FormInput.add(jLabel246);
        jLabel246.setBounds(710, 1020, 75, 23);

        NilaiEdmonson9.setEditable(false);
        NilaiEdmonson9.setFocusTraversalPolicyProvider(true);
        NilaiEdmonson9.setName("NilaiEdmonson9"); // NOI18N
        FormInput.add(NilaiEdmonson9);
        NilaiEdmonson9.setBounds(790, 1020, 60, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 1110, 880, 1);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Norton Scale");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(40, 1150, 180, 23);

        jLabel247.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel247.setText("1. Kondisi Fisik Umum");
        jLabel247.setName("jLabel247"); // NOI18N
        FormInput.add(jLabel247);
        jLabel247.setBounds(40, 1190, 300, 23);

        SkalaNorton1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Lumayan", "Buruk", "Sangat Buruk" }));
        SkalaNorton1.setName("SkalaNorton1"); // NOI18N
        SkalaNorton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaNorton1ItemStateChanged(evt);
            }
        });
        SkalaNorton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNorton1KeyPressed(evt);
            }
        });
        FormInput.add(SkalaNorton1);
        SkalaNorton1.setBounds(420, 1190, 280, 23);

        jLabel248.setText("Nilai :");
        jLabel248.setName("jLabel248"); // NOI18N
        FormInput.add(jLabel248);
        jLabel248.setBounds(710, 1190, 75, 23);

        NilaiNorton1.setEditable(false);
        NilaiNorton1.setFocusTraversalPolicyProvider(true);
        NilaiNorton1.setName("NilaiNorton1"); // NOI18N
        FormInput.add(NilaiNorton1);
        NilaiNorton1.setBounds(790, 1190, 60, 23);

        jLabel249.setText("Skala :");
        jLabel249.setName("jLabel249"); // NOI18N
        FormInput.add(jLabel249);
        jLabel249.setBounds(340, 1190, 80, 23);

        jLabel250.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel250.setText("2. Kesadaran");
        jLabel250.setName("jLabel250"); // NOI18N
        FormInput.add(jLabel250);
        jLabel250.setBounds(40, 1220, 300, 23);

        jLabel251.setText("Skala :");
        jLabel251.setName("jLabel251"); // NOI18N
        FormInput.add(jLabel251);
        jLabel251.setBounds(340, 1220, 80, 23);

        SkalaNorton2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kompos Mentis", "Apatis", "Konfus/Soporis", "Stupor/Koma" }));
        SkalaNorton2.setName("SkalaNorton2"); // NOI18N
        SkalaNorton2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaNorton2ItemStateChanged(evt);
            }
        });
        SkalaNorton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNorton2KeyPressed(evt);
            }
        });
        FormInput.add(SkalaNorton2);
        SkalaNorton2.setBounds(420, 1220, 280, 23);

        NilaiNorton2.setEditable(false);
        NilaiNorton2.setFocusTraversalPolicyProvider(true);
        NilaiNorton2.setName("NilaiNorton2"); // NOI18N
        FormInput.add(NilaiNorton2);
        NilaiNorton2.setBounds(790, 1220, 60, 23);

        jLabel252.setText("Nilai :");
        jLabel252.setName("jLabel252"); // NOI18N
        FormInput.add(jLabel252);
        jLabel252.setBounds(710, 1220, 75, 23);

        jLabel253.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel253.setText("3. Aktivitas");
        jLabel253.setName("jLabel253"); // NOI18N
        FormInput.add(jLabel253);
        jLabel253.setBounds(40, 1250, 300, 23);

        jLabel254.setText("Skala :");
        jLabel254.setName("jLabel254"); // NOI18N
        FormInput.add(jLabel254);
        jLabel254.setBounds(340, 1250, 80, 23);

        SkalaNorton3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dapat Berpindah", "Berjalan Dengan Bantuan", "Terbatas Di Kursi", "Terbatas Di Tempat Tidur" }));
        SkalaNorton3.setName("SkalaNorton3"); // NOI18N
        SkalaNorton3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaNorton3ItemStateChanged(evt);
            }
        });
        SkalaNorton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNorton3KeyPressed(evt);
            }
        });
        FormInput.add(SkalaNorton3);
        SkalaNorton3.setBounds(420, 1250, 280, 23);

        jLabel255.setText("Nilai :");
        jLabel255.setName("jLabel255"); // NOI18N
        FormInput.add(jLabel255);
        jLabel255.setBounds(710, 1250, 75, 23);

        NilaiNorton3.setEditable(false);
        NilaiNorton3.setFocusTraversalPolicyProvider(true);
        NilaiNorton3.setName("NilaiNorton3"); // NOI18N
        FormInput.add(NilaiNorton3);
        NilaiNorton3.setBounds(790, 1250, 60, 23);

        jLabel256.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel256.setText("4. Mobilitas");
        jLabel256.setName("jLabel256"); // NOI18N
        FormInput.add(jLabel256);
        jLabel256.setBounds(40, 1280, 300, 23);

        jLabel257.setText("Skala :");
        jLabel257.setName("jLabel257"); // NOI18N
        FormInput.add(jLabel257);
        jLabel257.setBounds(340, 1280, 80, 23);

        SkalaNorton4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bergerak Bebas", "Sedikit Terbatas", "Sangat Terbatas", "Tak Bisa Bergerak" }));
        SkalaNorton4.setName("SkalaNorton4"); // NOI18N
        SkalaNorton4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaNorton4ItemStateChanged(evt);
            }
        });
        SkalaNorton4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNorton4KeyPressed(evt);
            }
        });
        FormInput.add(SkalaNorton4);
        SkalaNorton4.setBounds(420, 1280, 280, 23);

        jLabel258.setText("Nilai :");
        jLabel258.setName("jLabel258"); // NOI18N
        FormInput.add(jLabel258);
        jLabel258.setBounds(710, 1280, 75, 23);

        NilaiNorton4.setEditable(false);
        NilaiNorton4.setFocusTraversalPolicyProvider(true);
        NilaiNorton4.setName("NilaiNorton4"); // NOI18N
        FormInput.add(NilaiNorton4);
        NilaiNorton4.setBounds(790, 1280, 60, 23);

        jLabel259.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel259.setText("5. Inkontinensia");
        jLabel259.setName("jLabel259"); // NOI18N
        FormInput.add(jLabel259);
        jLabel259.setBounds(40, 1310, 300, 23);

        jLabel260.setText("Skala :");
        jLabel260.setName("jLabel260"); // NOI18N
        FormInput.add(jLabel260);
        jLabel260.setBounds(340, 1310, 80, 23);

        SkalaNorton5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ngompol", "Kadang-kadang", "Sering Inkontinensia Urine", "Sering Inkontinensia Alvi Dan Urine" }));
        SkalaNorton5.setName("SkalaNorton5"); // NOI18N
        SkalaNorton5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaNorton5ItemStateChanged(evt);
            }
        });
        SkalaNorton5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNorton5KeyPressed(evt);
            }
        });
        FormInput.add(SkalaNorton5);
        SkalaNorton5.setBounds(420, 1310, 280, 23);

        jLabel261.setText("Nilai :");
        jLabel261.setName("jLabel261"); // NOI18N
        FormInput.add(jLabel261);
        jLabel261.setBounds(710, 1310, 75, 23);

        NilaiNorton5.setEditable(false);
        NilaiNorton5.setFocusTraversalPolicyProvider(true);
        NilaiNorton5.setName("NilaiNorton5"); // NOI18N
        FormInput.add(NilaiNorton5);
        NilaiNorton5.setBounds(790, 1310, 60, 23);

        jLabel265.setText("Total :");
        jLabel265.setName("jLabel265"); // NOI18N
        FormInput.add(jLabel265);
        jLabel265.setBounds(710, 1340, 75, 23);

        NilaiResikoTotalNorton.setEditable(false);
        NilaiResikoTotalNorton.setFocusTraversalPolicyProvider(true);
        NilaiResikoTotalNorton.setName("NilaiResikoTotalNorton"); // NOI18N
        FormInput.add(NilaiResikoTotalNorton);
        NilaiResikoTotalNorton.setBounds(790, 1340, 60, 23);

        TingkatResiko3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko3.setText("5. Riwayat BB Turun Tanpa Sebab Yang Diketahui :");
        TingkatResiko3.setName("TingkatResiko3"); // NOI18N
        FormInput.add(TingkatResiko3);
        TingkatResiko3.setBounds(460, 1560, 260, 23);

        TingkatResikoNorton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResikoNorton.setText("Keterangan : Skor <14 Risiko Tinggi Terjadinya Ulkus Decubitus");
        TingkatResikoNorton.setName("TingkatResikoNorton"); // NOI18N
        FormInput.add(TingkatResikoNorton);
        TingkatResikoNorton.setBounds(54, 1400, 420, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("II. RISIKO LUKA DECUBITUS");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(30, 1120, 180, 23);

        TingkatResiko8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko8.setText("Skor <12 Peningkatan Risiko 50x Lebih Besar Terjadinya Ulkus Decubitus");
        TingkatResiko8.setName("TingkatResiko8"); // NOI18N
        FormInput.add(TingkatResiko8);
        TingkatResiko8.setBounds(120, 1420, 420, 23);

        TingkatResiko9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko9.setText("Skor 12-13 Risiko Sedang");
        TingkatResiko9.setName("TingkatResiko9"); // NOI18N
        FormInput.add(TingkatResiko9);
        TingkatResiko9.setBounds(120, 1440, 540, 23);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("I. KAJIAN RISIKO JATUH");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(30, 80, 180, 23);

        TingkatResiko10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko10.setText("Skor >14 Risiko Kecil");
        TingkatResiko10.setName("TingkatResiko10"); // NOI18N
        FormInput.add(TingkatResiko10);
        TingkatResiko10.setBounds(120, 1460, 540, 23);

        TingkatResiko11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko11.setText("1. Riwayat Demam :");
        TingkatResiko11.setName("TingkatResiko11"); // NOI18N
        FormInput.add(TingkatResiko11);
        TingkatResiko11.setBounds(50, 1530, 110, 23);

        TingkatResiko12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko12.setText("2. Berkeringat Pada Malam Hari Tanpa Aktifitas :");
        TingkatResiko12.setName("TingkatResiko12"); // NOI18N
        FormInput.add(TingkatResiko12);
        TingkatResiko12.setBounds(50, 1560, 240, 23);

        Batuk2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Batuk2.setName("Batuk2"); // NOI18N
        Batuk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batuk2KeyPressed(evt);
            }
        });
        FormInput.add(Batuk2);
        Batuk2.setBounds(300, 1560, 90, 23);

        TingkatResiko13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko13.setText("3. Riwayat Berpergian Dari Daerah Wabah :");
        TingkatResiko13.setName("TingkatResiko13"); // NOI18N
        FormInput.add(TingkatResiko13);
        TingkatResiko13.setBounds(50, 1590, 240, 23);

        TingkatResiko14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko14.setText("4. Riwayat Pemakaian Obat Jangka Panjang :");
        TingkatResiko14.setName("TingkatResiko14"); // NOI18N
        FormInput.add(TingkatResiko14);
        TingkatResiko14.setBounds(460, 1530, 240, 23);

        Batuk1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Batuk1.setName("Batuk1"); // NOI18N
        Batuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batuk1KeyPressed(evt);
            }
        });
        FormInput.add(Batuk1);
        Batuk1.setBounds(300, 1530, 90, 23);

        Batuk3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Batuk3.setName("Batuk3"); // NOI18N
        Batuk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batuk3KeyPressed(evt);
            }
        });
        FormInput.add(Batuk3);
        Batuk3.setBounds(300, 1590, 90, 23);

        Batuk4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Batuk4.setName("Batuk4"); // NOI18N
        Batuk4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batuk4KeyPressed(evt);
            }
        });
        FormInput.add(Batuk4);
        Batuk4.setBounds(720, 1530, 90, 23);

        Batuk5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Batuk5.setName("Batuk5"); // NOI18N
        Batuk5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batuk5KeyPressed(evt);
            }
        });
        FormInput.add(Batuk5);
        Batuk5.setBounds(720, 1560, 90, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 1625, 880, 1);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("IV. GIZI");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(30, 1640, 180, 23);

        TingkatResiko16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko16.setText("Keterangan :");
        TingkatResiko16.setName("TingkatResiko16"); // NOI18N
        FormInput.add(TingkatResiko16);
        TingkatResiko16.setBounds(60, 1730, 70, 23);

        TingkatResiko17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko17.setText("2. Penurunan asupan makanan karena nafsu makan berkurang :");
        TingkatResiko17.setName("TingkatResiko17"); // NOI18N
        FormInput.add(TingkatResiko17);
        TingkatResiko17.setBounds(50, 1780, 330, 23);

        Gizi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Gizi2.setName("Gizi2"); // NOI18N
        Gizi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Gizi2KeyPressed(evt);
            }
        });
        FormInput.add(Gizi2);
        Gizi2.setBounds(60, 1810, 90, 23);

        TingkatResiko20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko20.setText("1. Penurunan BB Dalam Waktu 6 Bulan Terakhir :");
        TingkatResiko20.setName("TingkatResiko20"); // NOI18N
        FormInput.add(TingkatResiko20);
        TingkatResiko20.setBounds(50, 1670, 250, 23);

        Gizi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Gizi1.setName("Gizi1"); // NOI18N
        Gizi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Gizi1KeyPressed(evt);
            }
        });
        FormInput.add(Gizi1);
        Gizi1.setBounds(60, 1700, 90, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        KetGizi2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetGizi2.setColumns(20);
        KetGizi2.setRows(5);
        KetGizi2.setName("KetGizi2"); // NOI18N
        KetGizi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGizi2KeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(KetGizi2);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(130, 1840, 260, 43);

        TingkatResiko21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko21.setText("Keterangan :");
        TingkatResiko21.setName("TingkatResiko21"); // NOI18N
        FormInput.add(TingkatResiko21);
        TingkatResiko21.setBounds(60, 1840, 70, 23);

        TingkatResiko22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko22.setText("3. Gejala gastrointestinal (mual, muntah, diare, anorexia) :");
        TingkatResiko22.setName("TingkatResiko22"); // NOI18N
        FormInput.add(TingkatResiko22);
        TingkatResiko22.setBounds(50, 1900, 330, 23);

        Gizi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Gizi3.setName("Gizi3"); // NOI18N
        Gizi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Gizi3KeyPressed(evt);
            }
        });
        FormInput.add(Gizi3);
        Gizi3.setBounds(60, 1930, 90, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        KetGizi3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetGizi3.setColumns(20);
        KetGizi3.setRows(5);
        KetGizi3.setName("KetGizi3"); // NOI18N
        KetGizi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGizi3KeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(KetGizi3);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(130, 1960, 260, 43);

        TingkatResiko23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko23.setText("Keterangan :");
        TingkatResiko23.setName("TingkatResiko23"); // NOI18N
        FormInput.add(TingkatResiko23);
        TingkatResiko23.setBounds(60, 1960, 70, 23);

        TingkatResiko24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko24.setText("4. Faktor pemberat (komorbid) :");
        TingkatResiko24.setName("TingkatResiko24"); // NOI18N
        FormInput.add(TingkatResiko24);
        TingkatResiko24.setBounds(450, 1670, 330, 23);

        Gizi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Gizi4.setName("Gizi4"); // NOI18N
        Gizi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Gizi4KeyPressed(evt);
            }
        });
        FormInput.add(Gizi4);
        Gizi4.setBounds(460, 1700, 90, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        KetGizi4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetGizi4.setColumns(20);
        KetGizi4.setRows(5);
        KetGizi4.setName("KetGizi4"); // NOI18N
        KetGizi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGizi4KeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(KetGizi4);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(530, 1730, 260, 43);

        TingkatResiko25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko25.setText("Keterangan :");
        TingkatResiko25.setName("TingkatResiko25"); // NOI18N
        FormInput.add(TingkatResiko25);
        TingkatResiko25.setBounds(460, 1730, 70, 23);

        TingkatResiko26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko26.setText("5. Penurunan kapasitas fungsional :");
        TingkatResiko26.setName("TingkatResiko26"); // NOI18N
        FormInput.add(TingkatResiko26);
        TingkatResiko26.setBounds(460, 1780, 330, 23);

        Gizi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Gizi5.setName("Gizi5"); // NOI18N
        Gizi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Gizi5KeyPressed(evt);
            }
        });
        FormInput.add(Gizi5);
        Gizi5.setBounds(470, 1810, 90, 23);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        KetGizi5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetGizi5.setColumns(20);
        KetGizi5.setRows(5);
        KetGizi5.setName("KetGizi5"); // NOI18N
        KetGizi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGizi5KeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(KetGizi5);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(530, 1840, 260, 43);

        TingkatResiko27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko27.setText("Keterangan :");
        TingkatResiko27.setName("TingkatResiko27"); // NOI18N
        FormInput.add(TingkatResiko27);
        TingkatResiko27.setBounds(460, 1840, 70, 23);

        TingkatResiko.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko.setText("Tingkat Resiko : Risiko Rendah (0-24), Tindakan : Intervensi pencegahan risiko jatuh standar");
        TingkatResiko.setName("TingkatResiko"); // NOI18N
        FormInput.add(TingkatResiko);
        TingkatResiko.setBounds(50, 360, 810, 23);

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
        scrollPane17.setBounds(880, 780, 550, 290);

        jLabel262.setText("Nilai :");
        jLabel262.setName("jLabel262"); // NOI18N
        FormInput.add(jLabel262);
        jLabel262.setBounds(710, 780, 75, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-06-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-06-2023" }));
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
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"NIP Petugas");
        }else{
            if(Sequel.menyimpantf("screening_igd","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",75,new String[]{
                    TNoRw.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),KdPetugas.getText(),SkalaResiko1.getSelectedItem().toString(),NilaiResiko1.getText(),SkalaResiko2.getSelectedItem().toString(),
                    NilaiResiko2.getText(),SkalaResiko3.getSelectedItem().toString(),NilaiResiko3.getText(),SkalaResiko4.getSelectedItem().toString(),NilaiResiko4.getText(),SkalaResiko5.getSelectedItem().toString(),
                    NilaiResiko5.getText(),SkalaResiko6.getSelectedItem().toString(),NilaiResiko6.getText(),NilaiResikoTotal.getText(),SkalaHumpty1.getSelectedItem().toString(),NilaiHumpty1.getText(),
                    SkalaHumpty2.getSelectedItem().toString(),NilaiHumpty2.getText(),SkalaHumpty3.getSelectedItem().toString(),NilaiHumpty3.getText(),SkalaHumpty4.getSelectedItem().toString(),NilaiHumpty4.getText(),
                    SkalaHumpty5.getSelectedItem().toString(),NilaiHumpty5.getText(),SkalaHumpty6.getSelectedItem().toString(),NilaiHumpty6.getText(),NilaiResikoHumptyTotal.getText(),
                    SkalaEdmonson1.getSelectedItem().toString(),NilaiEdmonson1.getText(),SkalaEdmonson2.getSelectedItem().toString(),NilaiEdmonson2.getText(),SkalaEdmonson3.getSelectedItem().toString(),NilaiEdmonson3.getText(),
                    SkalaEdmonson4.getSelectedItem().toString(),NilaiEdmonson4.getText(),SkalaEdmonson5.getSelectedItem().toString(),NilaiEdmonson5.getText(),SkalaEdmonson6.getSelectedItem().toString(),NilaiEdmonson6.getText(),
                    SkalaEdmonson7.getSelectedItem().toString(),NilaiEdmonson7.getText(),SkalaEdmonson8.getSelectedItem().toString(),NilaiEdmonson8.getText(),SkalaEdmonson9.getSelectedItem().toString(),NilaiEdmonson9.getText(),
                    NilaiResikoEdmonsonTotal.getText(),SkalaNorton1.getSelectedItem().toString(),NilaiNorton1.getText(),SkalaNorton2.getSelectedItem().toString(),NilaiNorton2.getText(),
                    SkalaNorton3.getSelectedItem().toString(),NilaiNorton3.getText(),SkalaNorton4.getSelectedItem().toString(),NilaiNorton4.getText(),SkalaNorton5.getSelectedItem().toString(),NilaiNorton5.getText(),
                    NilaiResikoTotalNorton.getText(),Batuk1.getSelectedItem().toString(),Batuk2.getSelectedItem().toString(),Batuk3.getSelectedItem().toString(),Batuk4.getSelectedItem().toString(),Batuk5.getSelectedItem().toString(),
                    Gizi1.getSelectedItem().toString(),KetGizi1.getText(),Gizi2.getSelectedItem().toString(),KetGizi2.getText(),Gizi3.getSelectedItem().toString(),KetGizi3.getText(),
                    Gizi4.getSelectedItem().toString(),KetGizi4.getText(),Gizi5.getSelectedItem().toString(),KetGizi5.getText()
                })==true){
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,SkalaResiko1,BtnBatal);
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
            if(Sequel.queryu2tf("delete from screening_igd where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
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
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"NIP Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("screening_igd","no_rawat=?","no_rawat=?,tanggal=?,jam=?,nik=?,penilaian_jatuh_morse1=?,penilaian_jatuh_nilai_morse1=?,penilaian_jatuh_morse2=?,penilaian_jatuh_nilai_morse2=?,penilaian_jatuh_morse3=?,penilaian_jatuh_nilai_morse3=?,penilaian_jatuh_morse4=?,penilaian_jatuh_nilai_morse4=?,penilaian_jatuh_morse5=?,penilaian_jatuh_nilai_morse5=?,penilaian_jatuh_morse6=?,penilaian_jatuh_nilai_morse6=?,penilaian_jatuh_totalnilai=?,penilaian_jatuh_humty1=?,penilaian_jatuh_nilai_humty1=?,penilaian_jatuh_humty2=?,penilaian_jatuh_nilai_humty2=?,penilaian_jatuh_humty3=?,penilaian_jatuh_nilai_humty3=?,penilaian_jatuh_humty4=?,penilaian_jatuh_nilai_humty4=?,penilaian_jatuh_humty5=?,penilaian_jatuh_nilai_humty5=?,penilaian_jatuh_humty6=?,penilaian_jatuh_nilai_humty6=?,penilaian_jatuh_totalnilai_humpty=?,penilaian_jatuh_edmonson1=?,penilaian_jatuh_nilai_edmonson1=?,penilaian_jatuh_edmonson2=?,penilaian_jatuh_nilai_edmonson2=?,penilaian_jatuh_edmonson3=?,penilaian_jatuh_nilai_edmonson3=?,penilaian_jatuh_edmonson4=?,penilaian_jatuh_nilai_edmonson4=?,penilaian_jatuh_edmonson5=?,penilaian_jatuh_nilai_edmonson5=?,penilaian_jatuh_edmonson6=?,penilaian_jatuh_nilai_edmonson6=?,penilaian_jatuh_edmonson7=?,penilaian_jatuh_nilai_edmonson7=?,penilaian_jatuh_edmonson8=?,penilaian_jatuh_nilai_edmonson8=?,penilaian_jatuh_edmonson9=?,penilaian_jatuh_nilai_edmonson9=?,penilaian_jatuh_totalnilai_edmonson=?,norton1=?,nilai_norton1=?,norton2=?,nilai_norton2=?,norton3=?,nilai_norton3=?,norton4=?,nilai_norton4=?,norton5=?,nilai_norton5=?,total_norton=?,batuk1=?,batuk2=?,batuk3=?,batuk4=?,batuk5=?,gizi1=?,ket_gizi1=?,gizi2=?,ket_gizi2=?,gizi3=?,ket_gizi3=?,gizi4=?,ket_gizi4=?,gizi5=?,ket_gizi5=?",76,new String[]{
                    TNoRw.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),KdPetugas.getText(),SkalaResiko1.getSelectedItem().toString(),NilaiResiko1.getText(),SkalaResiko2.getSelectedItem().toString(),
                    NilaiResiko2.getText(),SkalaResiko3.getSelectedItem().toString(),NilaiResiko3.getText(),SkalaResiko4.getSelectedItem().toString(),NilaiResiko4.getText(),SkalaResiko5.getSelectedItem().toString(),
                    NilaiResiko5.getText(),SkalaResiko6.getSelectedItem().toString(),NilaiResiko6.getText(),NilaiResikoTotal.getText(),SkalaHumpty1.getSelectedItem().toString(),NilaiHumpty1.getText(),
                    SkalaHumpty2.getSelectedItem().toString(),NilaiHumpty2.getText(),SkalaHumpty3.getSelectedItem().toString(),NilaiHumpty3.getText(),SkalaHumpty4.getSelectedItem().toString(),NilaiHumpty4.getText(),
                    SkalaHumpty5.getSelectedItem().toString(),NilaiHumpty5.getText(),SkalaHumpty6.getSelectedItem().toString(),NilaiHumpty6.getText(),NilaiResikoHumptyTotal.getText(),
                    SkalaEdmonson1.getSelectedItem().toString(),NilaiEdmonson1.getText(),SkalaEdmonson2.getSelectedItem().toString(),NilaiEdmonson2.getText(),SkalaEdmonson3.getSelectedItem().toString(),NilaiEdmonson3.getText(),
                    SkalaEdmonson4.getSelectedItem().toString(),NilaiEdmonson4.getText(),SkalaEdmonson5.getSelectedItem().toString(),NilaiEdmonson5.getText(),SkalaEdmonson6.getSelectedItem().toString(),NilaiEdmonson6.getText(),
                    SkalaEdmonson7.getSelectedItem().toString(),NilaiEdmonson7.getText(),SkalaEdmonson8.getSelectedItem().toString(),NilaiEdmonson8.getText(),SkalaEdmonson9.getSelectedItem().toString(),NilaiEdmonson9.getText(),
                    NilaiResikoEdmonsonTotal.getText(),SkalaNorton1.getSelectedItem().toString(),NilaiNorton1.getText(),SkalaNorton2.getSelectedItem().toString(),NilaiNorton2.getText(),
                    SkalaNorton3.getSelectedItem().toString(),NilaiNorton3.getText(),SkalaNorton4.getSelectedItem().toString(),NilaiNorton4.getText(),SkalaNorton5.getSelectedItem().toString(),NilaiNorton5.getText(),
                    NilaiResikoTotalNorton.getText(),Batuk1.getSelectedItem().toString(),Batuk2.getSelectedItem().toString(),Batuk3.getSelectedItem().toString(),Batuk4.getSelectedItem().toString(),Batuk5.getSelectedItem().toString(),
                    Gizi1.getSelectedItem().toString(),KetGizi1.getText(),Gizi2.getSelectedItem().toString(),KetGizi2.getText(),Gizi3.getSelectedItem().toString(),KetGizi3.getText(),
                    Gizi4.getSelectedItem().toString(),KetGizi4.getText(),Gizi5.getSelectedItem().toString(),KetGizi5.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                     })==true){
                       tampil();
                       emptTeks();
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
                            "penilaian_awal_keperawatan_ralan.nik,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                            "inner join petugas on penilaian_awal_keperawatan_ralan.nik=petugas.nik "+
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
                            "penilaian_awal_keperawatan_ralan.nik,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                            "inner join petugas on penilaian_awal_keperawatan_ralan.nik=petugas.nik "+
                            "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                            "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and penilaian_awal_keperawatan_ralan.nik like ? or "+
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
                                            "<td width='32%' valign='top'>Petugas</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nik")+" "+rs.getString("nama")+"</td>"+
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
//                ChkAccor.setSelected(true);
//                isMenu();
//                getMasalah();
                getData();
//                TabRawat.setSelectedIndex(0);
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
//                    ChkAccor.setSelected(true);
//                    isMenu();
//                    getMasalah();
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

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
//        pegawai.isCek();
        akses.setform("SkriningIGD");
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        tampilMasalah();
    }//GEN-LAST:event_formWindowOpened

    private void TPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPasienActionPerformed

    private void KetGizi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGizi1KeyPressed
        Valid.pindah(evt,KetGizi2,KetGizi3);
    }//GEN-LAST:event_KetGizi1KeyPressed

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
        Valid.pindah(evt,SkalaResiko5,Gizi1);
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

    private void SkalaNorton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaNorton1ItemStateChanged
        if(SkalaNorton1.getSelectedIndex()==0){
            NilaiNorton1.setText("4");
        }else if(SkalaNorton1.getSelectedIndex()==1){
            NilaiNorton1.setText("3");
        }else if(SkalaNorton1.getSelectedIndex()==2){
            NilaiNorton1.setText("2");
        }else if(SkalaNorton1.getSelectedIndex()==3){
            NilaiNorton1.setText("1");
        }
        isTotalResikoNorton();
    }//GEN-LAST:event_SkalaNorton1ItemStateChanged

    private void SkalaNorton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNorton1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNorton1KeyPressed

    private void SkalaNorton2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaNorton2ItemStateChanged
        if(SkalaNorton2.getSelectedIndex()==0){
            NilaiNorton2.setText("4");
        }else if(SkalaNorton2.getSelectedIndex()==1){
            NilaiNorton2.setText("3");
        }else if(SkalaNorton2.getSelectedIndex()==2){
            NilaiNorton2.setText("2");
        }else if(SkalaNorton2.getSelectedIndex()==3){
            NilaiNorton2.setText("1");
        }
        isTotalResikoNorton();
    }//GEN-LAST:event_SkalaNorton2ItemStateChanged

    private void SkalaNorton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNorton2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNorton2KeyPressed

    private void SkalaNorton3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaNorton3ItemStateChanged
        if(SkalaNorton3.getSelectedIndex()==0){
            NilaiNorton3.setText("4");
        }else if(SkalaNorton3.getSelectedIndex()==1){
            NilaiNorton3.setText("3");
        }else if(SkalaNorton3.getSelectedIndex()==2){
            NilaiNorton3.setText("2");
        }else if(SkalaNorton3.getSelectedIndex()==3){
            NilaiNorton3.setText("1");
        }
        isTotalResikoNorton();
    }//GEN-LAST:event_SkalaNorton3ItemStateChanged

    private void SkalaNorton3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNorton3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNorton3KeyPressed

    private void SkalaNorton4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaNorton4ItemStateChanged
        if(SkalaNorton4.getSelectedIndex()==0){
            NilaiNorton4.setText("4");
        }else if(SkalaNorton4.getSelectedIndex()==1){
            NilaiNorton4.setText("3");
        }else if(SkalaNorton4.getSelectedIndex()==2){
            NilaiNorton4.setText("2");
        }else if(SkalaNorton4.getSelectedIndex()==3){
            NilaiNorton4.setText("1");
        }
        isTotalResikoNorton();
    }//GEN-LAST:event_SkalaNorton4ItemStateChanged

    private void SkalaNorton4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNorton4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNorton4KeyPressed

    private void SkalaNorton5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaNorton5ItemStateChanged
        if(SkalaNorton5.getSelectedIndex()==0){
            NilaiNorton5.setText("4");
        }else if(SkalaNorton5.getSelectedIndex()==1){
            NilaiNorton5.setText("3");
        }else if(SkalaNorton5.getSelectedIndex()==2){
            NilaiNorton5.setText("2");
        }else if(SkalaNorton5.getSelectedIndex()==3){
            NilaiNorton5.setText("1");
        }
        isTotalResikoNorton();
    }//GEN-LAST:event_SkalaNorton5ItemStateChanged

    private void SkalaNorton5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNorton5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNorton5KeyPressed

    private void Batuk2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batuk2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batuk2KeyPressed

    private void Batuk1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batuk1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batuk1KeyPressed

    private void Batuk3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batuk3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batuk3KeyPressed

    private void Batuk4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batuk4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batuk4KeyPressed

    private void Batuk5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batuk5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batuk5KeyPressed

    private void Gizi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Gizi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Gizi2KeyPressed

    private void Gizi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Gizi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Gizi1KeyPressed

    private void KetGizi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGizi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetGizi2KeyPressed

    private void Gizi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Gizi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Gizi3KeyPressed

    private void KetGizi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGizi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetGizi3KeyPressed

    private void Gizi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Gizi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Gizi4KeyPressed

    private void KetGizi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGizi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetGizi4KeyPressed

    private void Gizi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Gizi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Gizi5KeyPressed

    private void KetGizi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGizi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetGizi5KeyPressed

    private void SkalaResiko1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkalaResiko1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaResiko1ActionPerformed

    private void TingkatResikoEdmonsonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TingkatResikoEdmonsonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TingkatResikoEdmonsonKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SkriningIGD dialog = new SkriningIGD(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Batuk1;
    private widget.ComboBox Batuk2;
    private widget.ComboBox Batuk3;
    private widget.ComboBox Batuk4;
    private widget.ComboBox Batuk5;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Gizi1;
    private widget.ComboBox Gizi2;
    private widget.ComboBox Gizi3;
    private widget.ComboBox Gizi4;
    private widget.ComboBox Gizi5;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextArea KetGizi1;
    private widget.TextArea KetGizi2;
    private widget.TextArea KetGizi3;
    private widget.TextArea KetGizi4;
    private widget.TextArea KetGizi5;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
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
    private widget.TextBox NilaiNorton1;
    private widget.TextBox NilaiNorton2;
    private widget.TextBox NilaiNorton3;
    private widget.TextBox NilaiNorton4;
    private widget.TextBox NilaiNorton5;
    private widget.TextBox NilaiResiko1;
    private widget.TextBox NilaiResiko2;
    private widget.TextBox NilaiResiko3;
    private widget.TextBox NilaiResiko4;
    private widget.TextBox NilaiResiko5;
    private widget.TextBox NilaiResiko6;
    private widget.TextBox NilaiResikoEdmonsonTotal;
    private widget.TextBox NilaiResikoHumptyTotal;
    private widget.TextBox NilaiResikoTotal;
    private widget.TextBox NilaiResikoTotalNorton;
    private widget.TextBox NmPetugas;
    private widget.ScrollPane Scroll;
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
    private widget.ComboBox SkalaNorton1;
    private widget.ComboBox SkalaNorton2;
    private widget.ComboBox SkalaNorton3;
    private widget.ComboBox SkalaNorton4;
    private widget.ComboBox SkalaNorton5;
    private widget.ComboBox SkalaResiko1;
    private widget.ComboBox SkalaResiko2;
    private widget.ComboBox SkalaResiko3;
    private widget.ComboBox SkalaResiko4;
    private widget.ComboBox SkalaResiko5;
    private widget.ComboBox SkalaResiko6;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TglLahir;
    private widget.Label TingkatResiko;
    private widget.Label TingkatResiko10;
    private widget.Label TingkatResiko11;
    private widget.Label TingkatResiko12;
    private widget.Label TingkatResiko13;
    private widget.Label TingkatResiko14;
    private widget.Label TingkatResiko16;
    private widget.Label TingkatResiko17;
    private widget.Label TingkatResiko20;
    private widget.Label TingkatResiko21;
    private widget.Label TingkatResiko22;
    private widget.Label TingkatResiko23;
    private widget.Label TingkatResiko24;
    private widget.Label TingkatResiko25;
    private widget.Label TingkatResiko26;
    private widget.Label TingkatResiko27;
    private widget.Label TingkatResiko3;
    private widget.Label TingkatResiko8;
    private widget.Label TingkatResiko9;
    private widget.TextArea TingkatResikoEdmonson;
    private widget.Label TingkatResikoHumpty;
    private widget.Label TingkatResikoNorton;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel11;
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
    private widget.Label jLabel240;
    private widget.Label jLabel241;
    private widget.Label jLabel242;
    private widget.Label jLabel243;
    private widget.Label jLabel244;
    private widget.Label jLabel245;
    private widget.Label jLabel246;
    private widget.Label jLabel247;
    private widget.Label jLabel248;
    private widget.Label jLabel249;
    private widget.Label jLabel250;
    private widget.Label jLabel251;
    private widget.Label jLabel252;
    private widget.Label jLabel253;
    private widget.Label jLabel254;
    private widget.Label jLabel255;
    private widget.Label jLabel256;
    private widget.Label jLabel257;
    private widget.Label jLabel258;
    private widget.Label jLabel259;
    private widget.Label jLabel260;
    private widget.Label jLabel261;
    private widget.Label jLabel262;
    private widget.Label jLabel265;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel99;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,screening_igd.tanggal,screening_igd.nik,pegawai.nama,"+
                        "screening_igd.jam,screening_igd.nik,screening_igd.penilaian_jatuh_morse1,screening_igd.penilaian_jatuh_nilai_morse1,"+
                        "screening_igd.penilaian_jatuh_morse2,screening_igd.penilaian_jatuh_nilai_morse2,screening_igd.penilaian_jatuh_morse3,"+
                        "screening_igd.penilaian_jatuh_nilai_morse3,screening_igd.penilaian_jatuh_morse4,screening_igd.penilaian_jatuh_nilai_morse4,"+
                        "screening_igd.penilaian_jatuh_morse5,screening_igd.penilaian_jatuh_nilai_morse5,screening_igd.penilaian_jatuh_morse6,screening_igd.penilaian_jatuh_nilai_morse6,"+
                        "screening_igd.penilaian_jatuh_totalnilai,screening_igd.penilaian_jatuh_humty1,screening_igd.penilaian_jatuh_nilai_humty1,screening_igd.penilaian_jatuh_humty2,"+       
                        "screening_igd.penilaian_jatuh_nilai_humty2,screening_igd.penilaian_jatuh_humty3,screening_igd.penilaian_jatuh_nilai_humty3,screening_igd.penilaian_jatuh_humty4,"+
                        "screening_igd.penilaian_jatuh_nilai_humty4,screening_igd.penilaian_jatuh_humty5,screening_igd.penilaian_jatuh_nilai_humty5,screening_igd.penilaian_jatuh_humty6,"+
                        "screening_igd.penilaian_jatuh_nilai_humty6,screening_igd.penilaian_jatuh_totalnilai_humpty,screening_igd.penilaian_jatuh_edmonson1,screening_igd.penilaian_jatuh_nilai_edmonson1,"+
                        "screening_igd.penilaian_jatuh_edmonson2,screening_igd.penilaian_jatuh_nilai_edmonson2,screening_igd.penilaian_jatuh_edmonson3,screening_igd.penilaian_jatuh_nilai_edmonson3,"+
                        "screening_igd.penilaian_jatuh_edmonson4,screening_igd.penilaian_jatuh_nilai_edmonson4,screening_igd.penilaian_jatuh_edmonson5,screening_igd.penilaian_jatuh_nilai_edmonson5,"+
                        "screening_igd.penilaian_jatuh_edmonson6,screening_igd.penilaian_jatuh_nilai_edmonson6,screening_igd.penilaian_jatuh_edmonson7,screening_igd.penilaian_jatuh_nilai_edmonson7,"+
                        "screening_igd.penilaian_jatuh_edmonson8,screening_igd.penilaian_jatuh_nilai_edmonson8,screening_igd.penilaian_jatuh_edmonson9,screening_igd.penilaian_jatuh_nilai_edmonson9,"+
                        "screening_igd.penilaian_jatuh_totalnilai_edmonson,screening_igd.norton1,screening_igd.nilai_norton1,screening_igd.norton2,screening_igd.nilai_norton2,screening_igd.norton3,"+
                        "screening_igd.nilai_norton3,screening_igd.norton4,screening_igd.nilai_norton4,screening_igd.norton5,screening_igd.nilai_norton5,screening_igd.total_norton,screening_igd.batuk1,screening_igd.batuk2,"+
                        "screening_igd.batuk3,screening_igd.batuk4,screening_igd.batuk5,screening_igd.gizi1,screening_igd.ket_gizi1,screening_igd.gizi2,screening_igd.ket_gizi2,screening_igd.gizi3,screening_igd.ket_gizi3,"+
                        "screening_igd.gizi4,screening_igd.ket_gizi4,screening_igd.gizi5,screening_igd.ket_gizi5 "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join screening_igd on reg_periksa.no_rawat=screening_igd.no_rawat "+
                        "inner join pegawai on screening_igd.nik=pegawai.nik "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "screening_igd.tanggal between ? and ? order by screening_igd.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,screening_igd.tanggal,screening_igd.nik,pegawai.nama,"+
                        "screening_igd.jam,screening_igd.nik,screening_igd.penilaian_jatuh_morse1,screening_igd.penilaian_jatuh_nilai_morse1,"+
                        "screening_igd.penilaian_jatuh_morse2,screening_igd.penilaian_jatuh_nilai_morse2,screening_igd.penilaian_jatuh_morse3,"+
                        "screening_igd.penilaian_jatuh_nilai_morse3,screening_igd.penilaian_jatuh_morse4,screening_igd.penilaian_jatuh_nilai_morse4,"+
                        "screening_igd.penilaian_jatuh_morse5,screening_igd.penilaian_jatuh_nilai_morse5,screening_igd.penilaian_jatuh_morse6,screening_igd.penilaian_jatuh_nilai_morse6,"+
                        "screening_igd.penilaian_jatuh_totalnilai,screening_igd.penilaian_jatuh_humty1,screening_igd.penilaian_jatuh_nilai_humty1,screening_igd.penilaian_jatuh_humty2,"+       
                        "screening_igd.penilaian_jatuh_nilai_humty2,screening_igd.penilaian_jatuh_humty3,screening_igd.penilaian_jatuh_nilai_humty3,screening_igd.penilaian_jatuh_humty4,"+
                        "screening_igd.penilaian_jatuh_nilai_humty4,screening_igd.penilaian_jatuh_humty5,screening_igd.penilaian_jatuh_nilai_humty5,screening_igd.penilaian_jatuh_humty6,"+
                        "screening_igd.penilaian_jatuh_nilai_humty6,screening_igd.penilaian_jatuh_totalnilai_humpty,screening_igd.penilaian_jatuh_edmonson1,screening_igd.penilaian_jatuh_nilai_edmonson1,"+
                        "screening_igd.penilaian_jatuh_edmonson2,screening_igd.penilaian_jatuh_nilai_edmonson2,screening_igd.penilaian_jatuh_edmonson3,screening_igd.penilaian_jatuh_nilai_edmonson3,"+
                        "screening_igd.penilaian_jatuh_edmonson4,screening_igd.penilaian_jatuh_nilai_edmonson4,screening_igd.penilaian_jatuh_edmonson5,screening_igd.penilaian_jatuh_nilai_edmonson5,"+
                        "screening_igd.penilaian_jatuh_edmonson6,screening_igd.penilaian_jatuh_nilai_edmonson6,screening_igd.penilaian_jatuh_edmonson7,screening_igd.penilaian_jatuh_nilai_edmonson7,"+
                        "screening_igd.penilaian_jatuh_edmonson8,screening_igd.penilaian_jatuh_nilai_edmonson8,screening_igd.penilaian_jatuh_edmonson9,screening_igd.penilaian_jatuh_nilai_edmonson9,"+
                        "screening_igd.penilaian_jatuh_totalnilai_edmonson,screening_igd.norton1,screening_igd.nilai_norton1,screening_igd.norton2,screening_igd.nilai_norton2,screening_igd.norton3,"+
                        "screening_igd.nilai_norton3,screening_igd.norton4,screening_igd.nilai_norton4,screening_igd.norton5,screening_igd.nilai_norton5,screening_igd.total_norton,screening_igd.batuk1,screening_igd.batuk2,"+
                        "screening_igd.batuk3,screening_igd.batuk4,screening_igd.batuk5,screening_igd.gizi1,screening_igd.ket_gizi1,screening_igd.gizi2,screening_igd.ket_gizi2,screening_igd.gizi3,screening_igd.ket_gizi3,"+
                        "screening_igd.gizi4,screening_igd.ket_gizi4,screening_igd.gizi5,screening_igd.ket_gizi5 "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join screening_igd on reg_periksa.no_rawat=screening_igd.no_rawat "+
                        "inner join pegawai on screening_igd.nik=pegawai.nik "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "screening_igd.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                        "screening_igd.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                        "screening_igd.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                        "screening_igd.tanggal between ? and ? and screening_igd.nik like ? or "+
                        "screening_igd.tanggal between ? and ? and pegawai.nama like ? order by screening_igd.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("nik"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("jam"),
                        rs.getString("penilaian_jatuh_morse1"),rs.getString("penilaian_jatuh_nilai_morse1"),rs.getString("penilaian_jatuh_morse2"),rs.getString("penilaian_jatuh_nilai_morse2"),rs.getString("penilaian_jatuh_morse3"),rs.getString("penilaian_jatuh_nilai_morse3"),
                        rs.getString("penilaian_jatuh_morse4"),rs.getString("penilaian_jatuh_nilai_morse4"),rs.getString("penilaian_jatuh_morse5"),rs.getString("penilaian_jatuh_nilai_morse5"),rs.getString("penilaian_jatuh_morse6"),rs.getString("penilaian_jatuh_nilai_morse6"),
                        rs.getString("penilaian_jatuh_totalnilai"),rs.getString("penilaian_jatuh_humty1"),rs.getString("penilaian_jatuh_nilai_humty1"),rs.getString("penilaian_jatuh_humty2"),rs.getString("penilaian_jatuh_nilai_humty2"),rs.getString("penilaian_jatuh_humty3"),
                        rs.getString("penilaian_jatuh_nilai_humty3"),rs.getString("penilaian_jatuh_humty4"),rs.getString("penilaian_jatuh_nilai_humty4"),rs.getString("penilaian_jatuh_humty5"),rs.getString("penilaian_jatuh_nilai_humty5"),
                        rs.getString("penilaian_jatuh_humty6"),rs.getString("penilaian_jatuh_nilai_humty6"),rs.getString("penilaian_jatuh_totalnilai_humpty"),rs.getString("penilaian_jatuh_edmonson1"),rs.getString("penilaian_jatuh_nilai_edmonson1"),
                        rs.getString("penilaian_jatuh_edmonson2"),rs.getString("penilaian_jatuh_nilai_edmonson2"),rs.getString("penilaian_jatuh_edmonson3"),rs.getString("penilaian_jatuh_nilai_edmonson3"),rs.getString("penilaian_jatuh_edmonson4"),
                        rs.getString("penilaian_jatuh_nilai_edmonson4"),rs.getString("penilaian_jatuh_edmonson5"),rs.getString("penilaian_jatuh_nilai_edmonson5"),rs.getString("penilaian_jatuh_edmonson6"),rs.getString("penilaian_jatuh_nilai_edmonson6"),
                        rs.getString("penilaian_jatuh_edmonson7"),rs.getString("penilaian_jatuh_nilai_edmonson7"),rs.getString("penilaian_jatuh_edmonson8"),rs.getString("penilaian_jatuh_nilai_edmonson8"),rs.getString("penilaian_jatuh_edmonson9"),
                        rs.getString("penilaian_jatuh_nilai_edmonson9"),rs.getString("penilaian_jatuh_totalnilai_edmonson"),rs.getString("norton1"),rs.getString("nilai_norton1"),rs.getString("norton2"),rs.getString("nilai_norton2"),
                        rs.getString("norton3"),rs.getString("nilai_norton3"),rs.getString("norton4"),rs.getString("nilai_norton4"),rs.getString("norton5"),rs.getString("nilai_norton5"),rs.getString("total_norton"),
                        rs.getString("batuk1"),rs.getString("batuk2"),rs.getString("batuk3"),rs.getString("batuk4"),rs.getString("batuk5"),rs.getString("gizi1"),rs.getString("ket_gizi1"),rs.getString("gizi2"),rs.getString("ket_gizi2"),
                        rs.getString("gizi3"),rs.getString("ket_gizi3"),rs.getString("gizi4"),rs.getString("ket_gizi4"),rs.getString("gizi5"),rs.getString("ket_gizi5")
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
    
    private void isTotalResikoNorton(){
        try {
            NilaiResikoTotalNorton.setText((Integer.parseInt(NilaiNorton1.getText())+Integer.parseInt(NilaiNorton2.getText())+Integer.parseInt(NilaiNorton3.getText())+Integer.parseInt(NilaiNorton4.getText())+Integer.parseInt(NilaiNorton5.getText()))+"");
//            if(Integer.parseInt(NilaiResikoTotalNorton.getText())<14){
//                NilaiResikoTotalNorton.setText("Keterangan : Skor <14 Risiko Tinggi Terjadinya Ulkus Decubitus");
//            }else if(Integer.parseInt(NilaiResikoTotalNorton.getText())<12){
//                NilaiResikoTotalNorton.setText("Keterangan : Skor <12 Peningkatan Risiko 50x Lebih Besar Terjadinya Ulkus Decubitus");
//            }else if(Integer.parseInt(NilaiResikoTotalNorton.getText())<12){
//                NilaiResikoTotalNorton.setText("Keterangan : Skor 12-13 Risiko Sedang");
//            }else if(Integer.parseInt(NilaiResikoTotalNorton.getText())>14){
//                NilaiResikoTotalNorton.setText("Keterangan : Skor >14 Risiko Kecil");
//            }
        } catch (Exception e) {
            NilaiResikoTotalNorton.setText("0");
            NilaiResikoTotalNorton.setText("");
        }
    }

    public void emptTeks() {
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
        SkalaNorton1.setSelectedIndex(0);
        NilaiNorton1.setText("4");
        SkalaNorton2.setSelectedIndex(0);
        NilaiNorton2.setText("4");
        SkalaNorton3.setSelectedIndex(0);
        NilaiNorton3.setText("4");
        SkalaNorton4.setSelectedIndex(0);
        NilaiNorton4.setText("4");
        SkalaNorton5.setSelectedIndex(0);
        NilaiNorton5.setText("4");
        NilaiResikoTotalNorton.setText("20");
        Batuk1.setSelectedIndex(0);
        Batuk2.setSelectedIndex(0);
        Batuk3.setSelectedIndex(0);
        Batuk4.setSelectedIndex(0);
        Batuk5.setSelectedIndex(0);
        Gizi1.setSelectedIndex(0);
        KetGizi1.setText("-");
        Gizi2.setSelectedIndex(0);
        KetGizi2.setText("-");
        Gizi3.setSelectedIndex(0);
        KetGizi3.setText("-");
        Gizi4.setSelectedIndex(0);
        KetGizi4.setText("-");
        Gizi5.setSelectedIndex(0);
        KetGizi5.setText("-");
        TNoRw.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            SkalaResiko1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            NilaiResiko1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            SkalaResiko2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            NilaiResiko2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            SkalaResiko3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            NilaiResiko3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            SkalaResiko4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            NilaiResiko4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            SkalaResiko5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            NilaiResiko5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            SkalaResiko6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            NilaiResiko6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NilaiResikoTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            SkalaHumpty1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NilaiHumpty1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            SkalaHumpty2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            NilaiHumpty2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            SkalaHumpty3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            NilaiHumpty3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            SkalaHumpty4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            NilaiHumpty4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            SkalaHumpty5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            NilaiHumpty5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            SkalaHumpty6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            NilaiHumpty6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            NilaiResikoHumptyTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            SkalaEdmonson1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            NilaiEdmonson1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            SkalaEdmonson2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            NilaiEdmonson2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            SkalaEdmonson3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            NilaiEdmonson3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            SkalaEdmonson4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            NilaiEdmonson4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            SkalaEdmonson5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            NilaiEdmonson5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            SkalaEdmonson6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            NilaiEdmonson6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            SkalaEdmonson7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            NilaiEdmonson7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            SkalaEdmonson8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            NilaiEdmonson8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            SkalaEdmonson9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            NilaiEdmonson9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            NilaiResikoEdmonsonTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            SkalaNorton1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            NilaiNorton1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            SkalaNorton2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            NilaiNorton2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            SkalaNorton3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            NilaiNorton3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            SkalaNorton4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            NilaiNorton4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            SkalaNorton5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            NilaiNorton5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            NilaiResikoTotalNorton.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            Batuk1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            Batuk2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            Batuk3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            Batuk4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            Batuk5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            Gizi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            KetGizi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            Gizi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            KetGizi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            Gizi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            KetGizi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            Gizi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            KetGizi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            Gizi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            KetGizi5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            
//            try {
//                Valid.tabelKosong(tabModeMasalah);
//                
//                ps=koneksi.prepareStatement(
//                        "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
//                        "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
//                        "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
//                try {
//                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
//                    rs=ps.executeQuery();
//                    while(rs.next()){
//                        tabModeMasalah.addRow(new Object[]{true,rs.getString(1),rs.getString(2)});
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : "+e);
//                } finally{
//                    if(rs!=null){
//                        rs.close();
//                    }
//                    if(ps!=null){
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : "+e);
//            }
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
    
    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        Sequel.cariIsi("select if(jk='L','Laki-Laki','Perempuan') from pasien where no_rkm_medis=? ",Jk,TNoRM.getText());
        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
        isPsien();
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
//        BtnTambahMasalah.setEnabled(akses.getmaster_masalah_keperawatan());  
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", NmPetugas,KdPetugas.getText());
//            if(NmPetugas.getText().equals("")){
//                KdPetugas.setText("");
//                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
//            }
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }
    
//    private void tampilMasalah() {
//        try{
//            jml=0;
//            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
//                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
//                    jml++;
//                }
//            }
//
//            pilih=null;
//            pilih=new boolean[jml]; 
//            kode=null;
//            kode=new String[jml];
//            masalah=null;
//            masalah=new String[jml];
//
//            index=0;        
//            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
//                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
//                    pilih[index]=true;
//                    kode[index]=tbMasalahKeperawatan.getValueAt(i,1).toString();
//                    masalah[index]=tbMasalahKeperawatan.getValueAt(i,2).toString();
//                    index++;
//                }
//            } 
//
//            Valid.tabelKosong(tabModeMasalah);
//
//            for(i=0;i<jml;i++){
//                tabModeMasalah.addRow(new Object[] {
//                    pilih[i],kode[i],masalah[i]
//                });
//            }
//            ps=koneksi.prepareStatement("select * from master_masalah_keperawatan where kode_masalah like ? or nama_masalah like ? order by kode_masalah");
//            try {
//                ps.setString(1,"%"+TCariMasalah.getText().trim()+"%");
//                ps.setString(2,"%"+TCariMasalah.getText().trim()+"%");
//                rs=ps.executeQuery();
//                while(rs.next()){
//                    tabModeMasalah.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
//                }
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
//        }catch(Exception e){
//            System.out.println("Notifikasi : "+e);
//        }
//    }
    
//    private void isMenu(){
//        if(ChkAccor.isSelected()==true){
//            ChkAccor.setVisible(false);
//            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
//            FormMenu.setVisible(true);  
//            FormMasalahRencana.setVisible(true);  
//            ChkAccor.setVisible(true);
//        }else if(ChkAccor.isSelected()==false){   
//            ChkAccor.setVisible(false);
//            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
//            FormMenu.setVisible(false);  
//            FormMasalahRencana.setVisible(false);   
//            ChkAccor.setVisible(true);
//        }
//    }

//    private void getMasalah() {
//        if(tbObat.getSelectedRow()!= -1){
//            TNoRM1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
//            TPasien1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
//            DetailRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
//            try {
//                Valid.tabelKosong(tabModeDetailMasalah);
//                ps=koneksi.prepareStatement(
//                        "select screening_igd.tata from screening_igd "+
//                        "where screening_igd.no_rawat=? ");
//                try {
//                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
//                    rs=ps.executeQuery();
//                    while(rs.next()){
//                        tabModeDetailMasalah.addRow(new Object[]{rs.getString(1),rs.getString(2)});
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : "+e);
//                } finally{
//                    if(rs!=null){
//                        rs.close();
//                    }
//                    if(ps!=null){
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : "+e);
//            }
//        }
//    }
    
//    private void isBMI(){
//        if((!TB.getText().equals(""))&&(!BB.getText().equals(""))){
//            BMI.setText(Valid.SetAngka7(Valid.SetAngka(BB.getText())/((Valid.SetAngka(TB.getText())/100)*(Valid.SetAngka(TB.getText())/100)))+"");
//        }
//    }
}
