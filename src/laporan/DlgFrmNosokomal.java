/*
 * Kontribusi dari M. Syukur RS. Jiwa Prov Sultra
 */


package laporan;

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


/**
 *
 * @author perpustakaan
 */
public final class DlgFrmNosokomal extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariPetugas petugas1=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    private String finger="",namaPenyakit="",namaPenyakitt="",Listpenyakit="",Listpenyakitt="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public DlgFrmNosokomal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Alamat","Tanggal Masuk","Cara Rawat","Diagnosa Awal","Kamar","Pindah Ruangan",
            "Tgl. Pindah","Pindah Ruangan 2","Tgl. Pindah 2","Lokasi Intra Vena Kateter Vena Sentral","Total Hari","Tgl. Infeksi","Lokasi Vena Perifer","Total Hari",
            "Tgl. Infeksi","Lokasi Arteri","Total Hari","Tgl. Infeksi","Lokasi Umblikal","Total Hari","Tgl. Infeksi",
            "Lokasi Urine Kateter","Total Hari","Tgl. Infeksi","Lokasi Suprepuik Kateter","Total Hari","Tgl. Infeksi","Lokasi Ventilasi Mekanik Tuba Endrotrakeal",
            "Total Hari","Tgl. Infeksi","Lokasi Trakeostomi","Total Hari","Tgl. Infeksi","Lokasi Lainnya ..... Drain/IABP/CVVH","Total Hari","Tgl. Infeksi",
            "HBS Ag","Anti HCV","Anti HIV","Lainnya","Leukocyt","LED","GDS","Hasil Radiologi","Diagnosa","Tgl. Operasi",
            "Jenis Operasi","Tindakan Operasi","ASA Score","ILO","Hasil Kulture","Hari Ke","ISK","Hasil Kulture","Hari Ke","Pneumonia",
            "Hasil Kulture","Hari Ke","IADP","Hasil Kulture","Hari Ke","Lainnya (Plebilitis/dikubtus)","Hasil Kulture","Hari Ke","Antimikroba","Dosis",
            "Tgl. Mulai","Antimikroba 2","Dosis","Tgl. Mulai","Antimikroba 3","Dosis","Tgl. Mulai","Antimikroba 4","Dosis","Tgl. Mulai",
            "Tgl. Keluar","Diagnosa Akhir","Pindah Ke RS","Petugas","Ka. Ruangan","Kode Petugas","Kode Ka. Ruangan","Tgl_input"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 89; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(280);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(150);
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
                column.setPreferredWidth(70);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(65);
            }else if(i==24){
                column.setPreferredWidth(55);
            }else if(i==25){
                column.setPreferredWidth(280);
            }else if(i==26){
                column.setPreferredWidth(100);
            }else if(i==27){
                column.setPreferredWidth(100);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(150);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(150);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(150);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(150);
            }else if(i==36){
                column.setPreferredWidth(150);
            }else if(i==37){
                column.setPreferredWidth(150);
            }else if(i==38){
                column.setPreferredWidth(150);
            }else if(i==39){
                column.setPreferredWidth(150);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(70);
            }else if(i==42){
                column.setPreferredWidth(150);
            }else if(i==43){
                column.setPreferredWidth(65);
            }else if(i==44){
                column.setPreferredWidth(55);
            }else if(i==45){
                column.setPreferredWidth(280);
            }else if(i==46){
                column.setPreferredWidth(100);
            }else if(i==47){
                column.setPreferredWidth(100);
            }else if(i==48){
                column.setPreferredWidth(150);
            }else if(i==49){
                column.setPreferredWidth(150);
            }else if(i==51){
                column.setPreferredWidth(70);
            }else if(i==52){
                column.setPreferredWidth(150);
            }else if(i==53){
                column.setPreferredWidth(65);
            }else if(i==54){
                column.setPreferredWidth(55);
            }else if(i==55){
                column.setPreferredWidth(280);
            }else if(i==56){
                column.setPreferredWidth(100);
            }else if(i==57){
                column.setPreferredWidth(100);
            }else if(i==58){
                column.setPreferredWidth(150);
            }else if(i==59){
                column.setPreferredWidth(150);
            }else if(i==61){
                column.setPreferredWidth(70);
            }else if(i==62){
                column.setPreferredWidth(150);
            }else if(i==63){
                column.setPreferredWidth(65);
            }else if(i==64){
                column.setPreferredWidth(55);
            }else if(i==65){
                column.setPreferredWidth(280);
            }else if(i==66){
                column.setPreferredWidth(100);
            }else if(i==67){
                column.setPreferredWidth(100);
            }else if(i==68){
                column.setPreferredWidth(150);
            }else if(i==69){
                column.setPreferredWidth(150);
            }else if(i==71){
                column.setPreferredWidth(70);
            }else if(i==72){
                column.setPreferredWidth(150);
            }else if(i==73){
                column.setPreferredWidth(65);
            }else if(i==74){
                column.setPreferredWidth(55);
            }else if(i==75){
                column.setPreferredWidth(280);
            }else if(i==76){
                column.setPreferredWidth(100);
            }else if(i==77){
                column.setPreferredWidth(100);
            }else if(i==78){
                column.setPreferredWidth(150);
            }else if(i==79){
                column.setPreferredWidth(150);
            }else if(i==81){
                column.setPreferredWidth(70);
            }else if(i==82){
                column.setPreferredWidth(150);
            }else if(i==83){
                column.setPreferredWidth(65);
            }else if(i==84){
                column.setPreferredWidth(55);
            }else if(i==85){
                column.setPreferredWidth(280);
            }else if(i==86){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==87){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==88){
                column.setPreferredWidth(280);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Alamat.setDocument(new batasInput((int)2000).getKata(Alamat));
//        Ciriyangmenyolok.setDocument(new batasInput((int)1000).getKata(Ciriyangmenyolok));
//        Hasilpsikotes.setDocument(new batasInput((int)2000).getKata(Hasilpsikotes));
//        Kepribadian.setDocument(new batasInput((int)3000).getKata(Kepribadian));
//        Psikodinamika.setDocument(new batasInput((int)3000).getKata(Psikodinamika));
//        Kesimpulanpsikolog.setDocument(new batasInput((int)50).getKata(Kesimpulanpsikolog));
//        KetLokalis.setDocument(new batasInput((int)3000).getKata(KetLokalis));
//        Hasilpsikotes.setDocument(new batasInput((int)3000).getKata(Hasilpsikotes));
//        Kepribadian.setDocument(new batasInput((int)500).getKata(Kepribadian));
//        Psikodinamika.setDocument(new batasInput((int)5000).getKata(Psikodinamika));
//        Kesimpulanpsikolog.setDocument(new batasInput((int)1000).getKata(Kesimpulanpsikolog));
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
                    KdPetugas.requestFocus();
                }else{
                    KdPetugas1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    KdPetugas1.requestFocus();
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
        
        petugas1.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas1.getTable().getSelectedRow()!= -1){
                    KdPetugas1.setText(petugas1.getTable().getValueAt(petugas1.getTable().getSelectedRow(),0).toString());
                    NmPetugas1.setText(petugas1.getTable().getValueAt(petugas1.getTable().getSelectedRow(),1).toString());
                    KdPetugas1.requestFocus();
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
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
        KetLokalis = new widget.TextArea();
        jLabel125 = new widget.Label();
        jLabel124 = new widget.Label();
        TglMulaiLokasi = new widget.Tanggal();
        TglSdLokasi = new widget.Tanggal();
        TglMulaiLokasi1 = new widget.Tanggal();
        TglSdLokasi1 = new widget.Tanggal();
        TglMulaiLokasi2 = new widget.Tanggal();
        TglSdLokasi2 = new widget.Tanggal();
        TglSdLokasi3 = new widget.Tanggal();
        TglMulaiLokasi3 = new widget.Tanggal();
        TglMulaiLokasi4 = new widget.Tanggal();
        TglSdLokasi4 = new widget.Tanggal();
        TglSdLokasi5 = new widget.Tanggal();
        TglMulaiLokasi5 = new widget.Tanggal();
        TglMulaiLokasi6 = new widget.Tanggal();
        TglMulaiLokasi7 = new widget.Tanggal();
        TglMulaiLokasi8 = new widget.Tanggal();
        TglSdLokasi8 = new widget.Tanggal();
        TglSdLokasi7 = new widget.Tanggal();
        TglSdLokasi6 = new widget.Tanggal();
        TglSelesai2 = new widget.Tanggal();
        jLabel84 = new widget.Label();
        jLabel86 = new widget.Label();
        TglSelesai = new widget.Tanggal();
        jLabel78 = new widget.Label();
        TglSelesai1 = new widget.Tanggal();
        TglSelesai3 = new widget.Tanggal();
        jLabel89 = new widget.Label();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        scrollPane7 = new widget.ScrollPane();
        Alamat = new javax.swing.JTextArea();
        TanggalMasuk = new widget.TextBox();
        DiagnosaAwal = new widget.TextBox();
        Kamar = new widget.TextBox();
        CaraRawat = new widget.ComboBox();
        TglLahir = new widget.TextBox();
        BtnDokter = new widget.Button();
        Jk = new widget.TextBox();
        jSeparator3 = new javax.swing.JSeparator();
        PindahKe = new widget.TextBox();
        Tglke = new widget.Tanggal();
        PindahKe1 = new widget.TextBox();
        Tglke1 = new widget.Tanggal();
        jLabel99 = new widget.Label();
        jLabel101 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel57 = new widget.Label();
        label14 = new widget.Label();
        jLabel8 = new widget.Label();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        label12 = new widget.Label();
        label13 = new widget.Label();
        label15 = new widget.Label();
        label16 = new widget.Label();
        jLabel134 = new widget.Label();
        jLabel133 = new widget.Label();
        jLabel132 = new widget.Label();
        jLabel131 = new widget.Label();
        jLabel130 = new widget.Label();
        jLabel129 = new widget.Label();
        jLabel128 = new widget.Label();
        jLabel127 = new widget.Label();
        jLabel121 = new widget.Label();
        jLabel122 = new widget.Label();
        jLabel123 = new widget.Label();
        jLabel126 = new widget.Label();
        jLabel120 = new widget.Label();
        Lokasi = new widget.TextBox();
        Thari = new widget.TextBox();
        TglInfeksi = new widget.Tanggal();
        Lokasi1 = new widget.TextBox();
        Thari1 = new widget.TextBox();
        TglInfeksi1 = new widget.Tanggal();
        TglInfeksi2 = new widget.Tanggal();
        Lokasi2 = new widget.TextBox();
        Thari2 = new widget.TextBox();
        Thari3 = new widget.TextBox();
        Lokasi3 = new widget.TextBox();
        TglInfeksi3 = new widget.Tanggal();
        TglInfeksi4 = new widget.Tanggal();
        Thari4 = new widget.TextBox();
        Lokasi4 = new widget.TextBox();
        Thari5 = new widget.TextBox();
        Lokasi5 = new widget.TextBox();
        TglInfeksi5 = new widget.Tanggal();
        Lokasi6 = new widget.TextBox();
        Thari6 = new widget.TextBox();
        TglInfeksi6 = new widget.Tanggal();
        Lokasi7 = new widget.TextBox();
        Thari7 = new widget.TextBox();
        TglInfeksi7 = new widget.Tanggal();
        Thari8 = new widget.TextBox();
        Lokasi8 = new widget.TextBox();
        TglInfeksi8 = new widget.Tanggal();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        FaktorPenyakit = new widget.ComboBox();
        FaktorPenyakit1 = new widget.ComboBox();
        FaktorPenyakit2 = new widget.ComboBox();
        jLabel56 = new widget.Label();
        FaktorPenyakit3 = new widget.TextBox();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        FaktorPenyakit4 = new widget.TextBox();
        FaktorPenyakit5 = new widget.TextBox();
        FaktorPenyakit6 = new widget.TextBox();
        jLabel36 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        HRadiologi = new widget.TextArea();
        jLabel102 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        TglOperasi = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        DiagnosaSekunder = new widget.TextArea();
        jLabel31 = new widget.Label();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        jLabel105 = new widget.Label();
        JenisOperasi = new widget.ComboBox();
        TindakanOperasi = new widget.ComboBox();
        ASAScore = new widget.ComboBox();
        jLabel106 = new widget.Label();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        jLabel111 = new widget.Label();
        jLabel109 = new widget.Label();
        jLabel110 = new widget.Label();
        Komplikasi = new widget.ComboBox();
        Komplikasi1 = new widget.ComboBox();
        Komplikasi2 = new widget.ComboBox();
        Komplikasi3 = new widget.ComboBox();
        Komplikasi4 = new widget.ComboBox();
        jLabel79 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel112 = new widget.Label();
        Kultur = new widget.TextBox();
        Kultur1 = new widget.TextBox();
        Kultur2 = new widget.TextBox();
        Kultur3 = new widget.TextBox();
        Kultur4 = new widget.TextBox();
        jLabel74 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel113 = new widget.Label();
        HariKe = new widget.TextBox();
        HariKe1 = new widget.TextBox();
        HariKe2 = new widget.TextBox();
        HariKe3 = new widget.TextBox();
        HariKe4 = new widget.TextBox();
        jLabel114 = new widget.Label();
        Antimikroba = new widget.TextBox();
        Antimikroba1 = new widget.TextBox();
        Antimikroba2 = new widget.TextBox();
        Antimikroba3 = new widget.TextBox();
        jLabel115 = new widget.Label();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        dosis = new widget.TextBox();
        dosis1 = new widget.TextBox();
        dosis2 = new widget.TextBox();
        dosis3 = new widget.TextBox();
        jLabel83 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        TglMulai1 = new widget.Tanggal();
        TglMulai2 = new widget.Tanggal();
        TglMulai3 = new widget.Tanggal();
        TglMulai4 = new widget.Tanggal();
        jLabel119 = new widget.Label();
        jLabel90 = new widget.Label();
        TanggalKeluar = new widget.Tanggal();
        jLabel135 = new widget.Label();
        Pindah = new widget.TextBox();
        jLabel136 = new widget.Label();
        DiagnosaAkhir = new widget.TextBox();
        label18 = new widget.Label();
        KdPetugas1 = new widget.TextBox();
        NmPetugas1 = new widget.TextBox();
        BtnMenerima = new widget.Button();
        TglInput = new widget.Tanggal();
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

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Formulir Surveilans Infeksi Nosokomal");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        KetLokalis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetLokalis.setColumns(20);
        KetLokalis.setRows(5);
        KetLokalis.setName("KetLokalis"); // NOI18N
        KetLokalis.setPreferredSize(new java.awt.Dimension(182, 92));
        KetLokalis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLokalisKeyPressed(evt);
            }
        });

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText("s/d");
        jLabel125.setName("jLabel125"); // NOI18N

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText("Mulai");
        jLabel124.setName("jLabel124"); // NOI18N

        TglMulaiLokasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulaiLokasi.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        TglMulaiLokasi.setDisplayFormat("dd-MM-yyyy");
        TglMulaiLokasi.setName("TglMulaiLokasi"); // NOI18N
        TglMulaiLokasi.setOpaque(false);
        TglMulaiLokasi.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulaiLokasi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulaiLokasiItemStateChanged(evt);
            }
        });
        TglMulaiLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglMulaiLokasiActionPerformed(evt);
            }
        });
        TglMulaiLokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaiLokasiKeyPressed(evt);
            }
        });

        TglSdLokasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSdLokasi.setDisplayFormat("dd-MM-yyyy");
        TglSdLokasi.setName("TglSdLokasi"); // NOI18N
        TglSdLokasi.setOpaque(false);
        TglSdLokasi.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSdLokasi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSdLokasiItemStateChanged(evt);
            }
        });
        TglSdLokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSdLokasiKeyPressed(evt);
            }
        });

        TglMulaiLokasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulaiLokasi1.setDisplayFormat("dd-MM-yyyy");
        TglMulaiLokasi1.setName("TglMulaiLokasi1"); // NOI18N
        TglMulaiLokasi1.setOpaque(false);
        TglMulaiLokasi1.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulaiLokasi1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulaiLokasi1ItemStateChanged(evt);
            }
        });
        TglMulaiLokasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglMulaiLokasi1ActionPerformed(evt);
            }
        });
        TglMulaiLokasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaiLokasi1KeyPressed(evt);
            }
        });

        TglSdLokasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSdLokasi1.setDisplayFormat("dd-MM-yyyy");
        TglSdLokasi1.setName("TglSdLokasi1"); // NOI18N
        TglSdLokasi1.setOpaque(false);
        TglSdLokasi1.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSdLokasi1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSdLokasi1ItemStateChanged(evt);
            }
        });
        TglSdLokasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSdLokasi1KeyPressed(evt);
            }
        });

        TglMulaiLokasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulaiLokasi2.setDisplayFormat("dd-MM-yyyy");
        TglMulaiLokasi2.setName("TglMulaiLokasi2"); // NOI18N
        TglMulaiLokasi2.setOpaque(false);
        TglMulaiLokasi2.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulaiLokasi2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulaiLokasi2ItemStateChanged(evt);
            }
        });
        TglMulaiLokasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaiLokasi2KeyPressed(evt);
            }
        });

        TglSdLokasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSdLokasi2.setDisplayFormat("dd-MM-yyyy");
        TglSdLokasi2.setName("TglSdLokasi2"); // NOI18N
        TglSdLokasi2.setOpaque(false);
        TglSdLokasi2.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSdLokasi2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSdLokasi2ItemStateChanged(evt);
            }
        });
        TglSdLokasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSdLokasi2KeyPressed(evt);
            }
        });

        TglSdLokasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSdLokasi3.setDisplayFormat("dd-MM-yyyy");
        TglSdLokasi3.setName("TglSdLokasi3"); // NOI18N
        TglSdLokasi3.setOpaque(false);
        TglSdLokasi3.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSdLokasi3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSdLokasi3ItemStateChanged(evt);
            }
        });
        TglSdLokasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSdLokasi3KeyPressed(evt);
            }
        });

        TglMulaiLokasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulaiLokasi3.setDisplayFormat("dd-MM-yyyy");
        TglMulaiLokasi3.setName("TglMulaiLokasi3"); // NOI18N
        TglMulaiLokasi3.setOpaque(false);
        TglMulaiLokasi3.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulaiLokasi3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulaiLokasi3ItemStateChanged(evt);
            }
        });
        TglMulaiLokasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaiLokasi3KeyPressed(evt);
            }
        });

        TglMulaiLokasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulaiLokasi4.setDisplayFormat("dd-MM-yyyy");
        TglMulaiLokasi4.setName("TglMulaiLokasi4"); // NOI18N
        TglMulaiLokasi4.setOpaque(false);
        TglMulaiLokasi4.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulaiLokasi4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulaiLokasi4ItemStateChanged(evt);
            }
        });
        TglMulaiLokasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaiLokasi4KeyPressed(evt);
            }
        });

        TglSdLokasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSdLokasi4.setDisplayFormat("dd-MM-yyyy");
        TglSdLokasi4.setName("TglSdLokasi4"); // NOI18N
        TglSdLokasi4.setOpaque(false);
        TglSdLokasi4.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSdLokasi4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSdLokasi4ItemStateChanged(evt);
            }
        });
        TglSdLokasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSdLokasi4KeyPressed(evt);
            }
        });

        TglSdLokasi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSdLokasi5.setDisplayFormat("dd-MM-yyyy");
        TglSdLokasi5.setName("TglSdLokasi5"); // NOI18N
        TglSdLokasi5.setOpaque(false);
        TglSdLokasi5.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSdLokasi5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSdLokasi5ItemStateChanged(evt);
            }
        });
        TglSdLokasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSdLokasi5KeyPressed(evt);
            }
        });

        TglMulaiLokasi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulaiLokasi5.setDisplayFormat("dd-MM-yyyy");
        TglMulaiLokasi5.setName("TglMulaiLokasi5"); // NOI18N
        TglMulaiLokasi5.setOpaque(false);
        TglMulaiLokasi5.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulaiLokasi5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulaiLokasi5ItemStateChanged(evt);
            }
        });
        TglMulaiLokasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaiLokasi5KeyPressed(evt);
            }
        });

        TglMulaiLokasi6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulaiLokasi6.setDisplayFormat("dd-MM-yyyy");
        TglMulaiLokasi6.setName("TglMulaiLokasi6"); // NOI18N
        TglMulaiLokasi6.setOpaque(false);
        TglMulaiLokasi6.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulaiLokasi6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulaiLokasi6ItemStateChanged(evt);
            }
        });
        TglMulaiLokasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaiLokasi6KeyPressed(evt);
            }
        });

        TglMulaiLokasi7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulaiLokasi7.setDisplayFormat("dd-MM-yyyy");
        TglMulaiLokasi7.setName("TglMulaiLokasi7"); // NOI18N
        TglMulaiLokasi7.setOpaque(false);
        TglMulaiLokasi7.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulaiLokasi7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulaiLokasi7ItemStateChanged(evt);
            }
        });
        TglMulaiLokasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaiLokasi7KeyPressed(evt);
            }
        });

        TglMulaiLokasi8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulaiLokasi8.setDisplayFormat("dd-MM-yyyy");
        TglMulaiLokasi8.setName("TglMulaiLokasi8"); // NOI18N
        TglMulaiLokasi8.setOpaque(false);
        TglMulaiLokasi8.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulaiLokasi8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulaiLokasi8ItemStateChanged(evt);
            }
        });
        TglMulaiLokasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaiLokasi8KeyPressed(evt);
            }
        });

        TglSdLokasi8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSdLokasi8.setDisplayFormat("dd-MM-yyyy");
        TglSdLokasi8.setName("TglSdLokasi8"); // NOI18N
        TglSdLokasi8.setOpaque(false);
        TglSdLokasi8.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSdLokasi8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSdLokasi8ItemStateChanged(evt);
            }
        });
        TglSdLokasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSdLokasi8KeyPressed(evt);
            }
        });

        TglSdLokasi7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSdLokasi7.setDisplayFormat("dd-MM-yyyy");
        TglSdLokasi7.setName("TglSdLokasi7"); // NOI18N
        TglSdLokasi7.setOpaque(false);
        TglSdLokasi7.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSdLokasi7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSdLokasi7ItemStateChanged(evt);
            }
        });
        TglSdLokasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSdLokasi7KeyPressed(evt);
            }
        });

        TglSdLokasi6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSdLokasi6.setDisplayFormat("dd-MM-yyyy");
        TglSdLokasi6.setName("TglSdLokasi6"); // NOI18N
        TglSdLokasi6.setOpaque(false);
        TglSdLokasi6.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSdLokasi6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSdLokasi6ItemStateChanged(evt);
            }
        });
        TglSdLokasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSdLokasi6KeyPressed(evt);
            }
        });

        TglSelesai2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSelesai2.setDisplayFormat("dd-MM-yyyy");
        TglSelesai2.setName("TglSelesai2"); // NOI18N
        TglSelesai2.setOpaque(false);
        TglSelesai2.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSelesai2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSelesai2ItemStateChanged(evt);
            }
        });
        TglSelesai2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSelesai2KeyPressed(evt);
            }
        });

        jLabel84.setText("s/d");
        jLabel84.setName("jLabel84"); // NOI18N

        jLabel86.setText("s/d");
        jLabel86.setName("jLabel86"); // NOI18N

        TglSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSelesai.setDisplayFormat("dd-MM-yyyy");
        TglSelesai.setName("TglSelesai"); // NOI18N
        TglSelesai.setOpaque(false);
        TglSelesai.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSelesai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSelesaiItemStateChanged(evt);
            }
        });
        TglSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSelesaiKeyPressed(evt);
            }
        });

        jLabel78.setText("s/d");
        jLabel78.setName("jLabel78"); // NOI18N

        TglSelesai1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSelesai1.setDisplayFormat("dd-MM-yyyy");
        TglSelesai1.setName("TglSelesai1"); // NOI18N
        TglSelesai1.setOpaque(false);
        TglSelesai1.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSelesai1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSelesai1ItemStateChanged(evt);
            }
        });
        TglSelesai1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSelesai1KeyPressed(evt);
            }
        });

        TglSelesai3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglSelesai3.setDisplayFormat("dd-MM-yyyy");
        TglSelesai3.setName("TglSelesai3"); // NOI18N
        TglSelesai3.setOpaque(false);
        TglSelesai3.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSelesai3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSelesai3ItemStateChanged(evt);
            }
        });
        TglSelesai3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSelesai3KeyPressed(evt);
            }
        });

        jLabel89.setText("s/d");
        jLabel89.setName("jLabel89"); // NOI18N

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Surveilans Infeksi Nosokomal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(1870, 1883));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(319, 10, 250, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 100, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(84, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(186, 40, 185, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Alamat.setColumns(20);
        Alamat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Alamat.setRows(5);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(Alamat);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(84, 70, 770, 30);

        TanggalMasuk.setEditable(false);
        TanggalMasuk.setHighlighter(null);
        TanggalMasuk.setName("TanggalMasuk"); // NOI18N
        FormInput.add(TanggalMasuk);
        TanggalMasuk.setBounds(510, 40, 140, 20);

        DiagnosaAwal.setEditable(false);
        DiagnosaAwal.setHighlighter(null);
        DiagnosaAwal.setName("DiagnosaAwal"); // NOI18N
        DiagnosaAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiagnosaAwalActionPerformed(evt);
            }
        });
        FormInput.add(DiagnosaAwal);
        DiagnosaAwal.setBounds(170, 104, 270, 20);

        Kamar.setEditable(false);
        Kamar.setHighlighter(null);
        Kamar.setName("Kamar"); // NOI18N
        Kamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KamarActionPerformed(evt);
            }
        });
        FormInput.add(Kamar);
        Kamar.setBounds(550, 104, 120, 20);

        CaraRawat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Emergency", "Efektif" }));
        CaraRawat.setName("CaraRawat"); // NOI18N
        CaraRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraRawatKeyPressed(evt);
            }
        });
        FormInput.add(CaraRawat);
        CaraRawat.setBounds(744, 40, 110, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

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
        BtnDokter.setBounds(373, 40, 28, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 130, 880, 1);

        PindahKe.setHighlighter(null);
        PindahKe.setName("PindahKe"); // NOI18N
        PindahKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PindahKeKeyPressed(evt);
            }
        });
        FormInput.add(PindahKe);
        PindahKe.setBounds(30, 180, 470, 23);

        Tglke.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        Tglke.setDisplayFormat("dd-MM-yyyy");
        Tglke.setName("Tglke"); // NOI18N
        Tglke.setOpaque(false);
        Tglke.setPreferredSize(new java.awt.Dimension(95, 23));
        Tglke.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglkeItemStateChanged(evt);
            }
        });
        Tglke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglkeKeyPressed(evt);
            }
        });
        FormInput.add(Tglke);
        Tglke.setBounds(550, 150, 140, 23);

        PindahKe1.setHighlighter(null);
        PindahKe1.setName("PindahKe1"); // NOI18N
        PindahKe1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PindahKe1KeyPressed(evt);
            }
        });
        FormInput.add(PindahKe1);
        PindahKe1.setBounds(30, 150, 470, 23);

        Tglke1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        Tglke1.setDisplayFormat("dd-MM-yyyy");
        Tglke1.setName("Tglke1"); // NOI18N
        Tglke1.setOpaque(false);
        Tglke1.setPreferredSize(new java.awt.Dimension(95, 23));
        Tglke1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Tglke1ItemStateChanged(evt);
            }
        });
        Tglke1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tglke1KeyPressed(evt);
            }
        });
        FormInput.add(Tglke1);
        Tglke1.setBounds(550, 177, 140, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("PINDAH KE RUANGAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 130, 180, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("FAKTOR RESIKO SELAMAT DIRAWAT");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 210, 190, 23);

        jLabel34.setText("Alamat :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(30, 70, 50, 23);

        jLabel57.setText("Cara Rawat : ");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(620, 40, 120, 23);

        label14.setText("Petugas : ");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 80, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 80, 23);

        label11.setText("Tanggal Masuk :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(410, 40, 90, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        label12.setText("Diagnosa Awal :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(0, 103, 160, 20);

        label13.setText("Tgl ");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label13);
        label13.setBounds(480, 150, 50, 20);

        label15.setText("Kamar Awal : ");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(450, 103, 80, 20);

        label16.setText("Tgl ");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(480, 180, 50, 20);

        jLabel134.setText("Lain-lain");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(20, 500, 170, 20);

        jLabel133.setText("Trakeostomi");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(20, 470, 170, 20);

        jLabel132.setText("Ventilasi Mekanik Tuba Endrotrakeal");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(20, 440, 170, 20);

        jLabel131.setText("Suprepuik Kateter");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(20, 410, 170, 20);

        jLabel130.setText("Urine Kateter");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(20, 380, 170, 20);

        jLabel129.setText("Umbilikal");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(20, 350, 170, 20);

        jLabel128.setText("Arteri");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(20, 320, 170, 20);

        jLabel127.setText("Vena Perifer");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(20, 290, 170, 20);

        jLabel121.setText("Intra vena Kateter Vena Sentral ");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(20, 260, 170, 20);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel122.setText("Jenis Tindakan/alkes");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(20, 230, 170, 20);

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel123.setText("Lokasi");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(210, 230, 300, 20);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel126.setText("Total Hari");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(530, 230, 110, 20);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel120.setText("Tanggal Infeksi");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(650, 230, 100, 20);

        Lokasi.setHighlighter(null);
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
        Lokasi.setBounds(200, 260, 320, 23);

        Thari.setHighlighter(null);
        Thari.setName("Thari"); // NOI18N
        Thari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThariKeyPressed(evt);
            }
        });
        FormInput.add(Thari);
        Thari.setBounds(530, 260, 110, 23);

        TglInfeksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglInfeksi.setDisplayFormat("dd-MM-yyyy");
        TglInfeksi.setName("TglInfeksi"); // NOI18N
        TglInfeksi.setOpaque(false);
        TglInfeksi.setPreferredSize(new java.awt.Dimension(95, 23));
        TglInfeksi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInfeksiItemStateChanged(evt);
            }
        });
        TglInfeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInfeksiKeyPressed(evt);
            }
        });
        FormInput.add(TglInfeksi);
        TglInfeksi.setBounds(650, 260, 100, 23);

        Lokasi1.setHighlighter(null);
        Lokasi1.setName("Lokasi1"); // NOI18N
        Lokasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Lokasi1ActionPerformed(evt);
            }
        });
        Lokasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lokasi1KeyPressed(evt);
            }
        });
        FormInput.add(Lokasi1);
        Lokasi1.setBounds(200, 290, 320, 23);

        Thari1.setHighlighter(null);
        Thari1.setName("Thari1"); // NOI18N
        Thari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Thari1KeyPressed(evt);
            }
        });
        FormInput.add(Thari1);
        Thari1.setBounds(530, 290, 110, 23);

        TglInfeksi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglInfeksi1.setDisplayFormat("dd-MM-yyyy");
        TglInfeksi1.setName("TglInfeksi1"); // NOI18N
        TglInfeksi1.setOpaque(false);
        TglInfeksi1.setPreferredSize(new java.awt.Dimension(95, 23));
        TglInfeksi1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInfeksi1ItemStateChanged(evt);
            }
        });
        TglInfeksi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInfeksi1KeyPressed(evt);
            }
        });
        FormInput.add(TglInfeksi1);
        TglInfeksi1.setBounds(650, 290, 100, 23);

        TglInfeksi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglInfeksi2.setDisplayFormat("dd-MM-yyyy");
        TglInfeksi2.setName("TglInfeksi2"); // NOI18N
        TglInfeksi2.setOpaque(false);
        TglInfeksi2.setPreferredSize(new java.awt.Dimension(95, 23));
        TglInfeksi2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInfeksi2ItemStateChanged(evt);
            }
        });
        TglInfeksi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInfeksi2KeyPressed(evt);
            }
        });
        FormInput.add(TglInfeksi2);
        TglInfeksi2.setBounds(650, 320, 100, 23);

        Lokasi2.setHighlighter(null);
        Lokasi2.setName("Lokasi2"); // NOI18N
        Lokasi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Lokasi2ActionPerformed(evt);
            }
        });
        Lokasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lokasi2KeyPressed(evt);
            }
        });
        FormInput.add(Lokasi2);
        Lokasi2.setBounds(200, 320, 320, 23);

        Thari2.setHighlighter(null);
        Thari2.setName("Thari2"); // NOI18N
        Thari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Thari2KeyPressed(evt);
            }
        });
        FormInput.add(Thari2);
        Thari2.setBounds(530, 320, 110, 23);

        Thari3.setHighlighter(null);
        Thari3.setName("Thari3"); // NOI18N
        Thari3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Thari3KeyPressed(evt);
            }
        });
        FormInput.add(Thari3);
        Thari3.setBounds(530, 350, 110, 23);

        Lokasi3.setHighlighter(null);
        Lokasi3.setName("Lokasi3"); // NOI18N
        Lokasi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Lokasi3ActionPerformed(evt);
            }
        });
        Lokasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lokasi3KeyPressed(evt);
            }
        });
        FormInput.add(Lokasi3);
        Lokasi3.setBounds(200, 350, 320, 23);

        TglInfeksi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglInfeksi3.setDisplayFormat("dd-MM-yyyy");
        TglInfeksi3.setName("TglInfeksi3"); // NOI18N
        TglInfeksi3.setOpaque(false);
        TglInfeksi3.setPreferredSize(new java.awt.Dimension(95, 23));
        TglInfeksi3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInfeksi3ItemStateChanged(evt);
            }
        });
        TglInfeksi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInfeksi3KeyPressed(evt);
            }
        });
        FormInput.add(TglInfeksi3);
        TglInfeksi3.setBounds(650, 350, 100, 23);

        TglInfeksi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglInfeksi4.setDisplayFormat("dd-MM-yyyy");
        TglInfeksi4.setName("TglInfeksi4"); // NOI18N
        TglInfeksi4.setOpaque(false);
        TglInfeksi4.setPreferredSize(new java.awt.Dimension(95, 23));
        TglInfeksi4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInfeksi4ItemStateChanged(evt);
            }
        });
        TglInfeksi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInfeksi4KeyPressed(evt);
            }
        });
        FormInput.add(TglInfeksi4);
        TglInfeksi4.setBounds(650, 380, 100, 23);

        Thari4.setHighlighter(null);
        Thari4.setName("Thari4"); // NOI18N
        Thari4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Thari4KeyPressed(evt);
            }
        });
        FormInput.add(Thari4);
        Thari4.setBounds(530, 380, 110, 23);

        Lokasi4.setHighlighter(null);
        Lokasi4.setName("Lokasi4"); // NOI18N
        Lokasi4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Lokasi4ActionPerformed(evt);
            }
        });
        Lokasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lokasi4KeyPressed(evt);
            }
        });
        FormInput.add(Lokasi4);
        Lokasi4.setBounds(200, 380, 320, 23);

        Thari5.setHighlighter(null);
        Thari5.setName("Thari5"); // NOI18N
        Thari5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Thari5KeyPressed(evt);
            }
        });
        FormInput.add(Thari5);
        Thari5.setBounds(530, 410, 110, 23);

        Lokasi5.setHighlighter(null);
        Lokasi5.setName("Lokasi5"); // NOI18N
        Lokasi5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Lokasi5ActionPerformed(evt);
            }
        });
        Lokasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lokasi5KeyPressed(evt);
            }
        });
        FormInput.add(Lokasi5);
        Lokasi5.setBounds(200, 410, 320, 23);

        TglInfeksi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglInfeksi5.setDisplayFormat("dd-MM-yyyy");
        TglInfeksi5.setName("TglInfeksi5"); // NOI18N
        TglInfeksi5.setOpaque(false);
        TglInfeksi5.setPreferredSize(new java.awt.Dimension(95, 23));
        TglInfeksi5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInfeksi5ItemStateChanged(evt);
            }
        });
        TglInfeksi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInfeksi5KeyPressed(evt);
            }
        });
        FormInput.add(TglInfeksi5);
        TglInfeksi5.setBounds(650, 410, 100, 23);

        Lokasi6.setHighlighter(null);
        Lokasi6.setName("Lokasi6"); // NOI18N
        Lokasi6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Lokasi6ActionPerformed(evt);
            }
        });
        Lokasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lokasi6KeyPressed(evt);
            }
        });
        FormInput.add(Lokasi6);
        Lokasi6.setBounds(200, 440, 320, 23);

        Thari6.setHighlighter(null);
        Thari6.setName("Thari6"); // NOI18N
        Thari6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Thari6KeyPressed(evt);
            }
        });
        FormInput.add(Thari6);
        Thari6.setBounds(530, 440, 110, 23);

        TglInfeksi6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglInfeksi6.setDisplayFormat("dd-MM-yyyy");
        TglInfeksi6.setName("TglInfeksi6"); // NOI18N
        TglInfeksi6.setOpaque(false);
        TglInfeksi6.setPreferredSize(new java.awt.Dimension(95, 23));
        TglInfeksi6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInfeksi6ItemStateChanged(evt);
            }
        });
        TglInfeksi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInfeksi6KeyPressed(evt);
            }
        });
        FormInput.add(TglInfeksi6);
        TglInfeksi6.setBounds(650, 440, 100, 23);

        Lokasi7.setHighlighter(null);
        Lokasi7.setName("Lokasi7"); // NOI18N
        Lokasi7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Lokasi7ActionPerformed(evt);
            }
        });
        Lokasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lokasi7KeyPressed(evt);
            }
        });
        FormInput.add(Lokasi7);
        Lokasi7.setBounds(200, 470, 320, 23);

        Thari7.setHighlighter(null);
        Thari7.setName("Thari7"); // NOI18N
        Thari7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Thari7KeyPressed(evt);
            }
        });
        FormInput.add(Thari7);
        Thari7.setBounds(530, 470, 110, 23);

        TglInfeksi7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglInfeksi7.setDisplayFormat("dd-MM-yyyy");
        TglInfeksi7.setName("TglInfeksi7"); // NOI18N
        TglInfeksi7.setOpaque(false);
        TglInfeksi7.setPreferredSize(new java.awt.Dimension(95, 23));
        TglInfeksi7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInfeksi7ItemStateChanged(evt);
            }
        });
        TglInfeksi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInfeksi7KeyPressed(evt);
            }
        });
        FormInput.add(TglInfeksi7);
        TglInfeksi7.setBounds(650, 470, 100, 23);

        Thari8.setHighlighter(null);
        Thari8.setName("Thari8"); // NOI18N
        Thari8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Thari8KeyPressed(evt);
            }
        });
        FormInput.add(Thari8);
        Thari8.setBounds(530, 500, 110, 23);

        Lokasi8.setHighlighter(null);
        Lokasi8.setName("Lokasi8"); // NOI18N
        Lokasi8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Lokasi8ActionPerformed(evt);
            }
        });
        Lokasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lokasi8KeyPressed(evt);
            }
        });
        FormInput.add(Lokasi8);
        Lokasi8.setBounds(200, 500, 320, 23);

        TglInfeksi8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglInfeksi8.setDisplayFormat("dd-MM-yyyy");
        TglInfeksi8.setName("TglInfeksi8"); // NOI18N
        TglInfeksi8.setOpaque(false);
        TglInfeksi8.setPreferredSize(new java.awt.Dimension(95, 23));
        TglInfeksi8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInfeksi8ItemStateChanged(evt);
            }
        });
        TglInfeksi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInfeksi8KeyPressed(evt);
            }
        });
        FormInput.add(TglInfeksi8);
        TglInfeksi8.setBounds(650, 500, 100, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("HBS Ag ");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(30, 530, 60, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Anti HCV ");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(30, 560, 60, 20);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Anti HIV ");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(30, 590, 60, 20);

        FaktorPenyakit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Positif", "Negatif", "Tidak diperiksa" }));
        FaktorPenyakit.setName("FaktorPenyakit"); // NOI18N
        FaktorPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorPenyakitKeyPressed(evt);
            }
        });
        FormInput.add(FaktorPenyakit);
        FaktorPenyakit.setBounds(90, 530, 90, 20);

        FaktorPenyakit1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Positif", "Negatif", "Tidak diperiksa" }));
        FaktorPenyakit1.setName("FaktorPenyakit1"); // NOI18N
        FaktorPenyakit1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorPenyakit1KeyPressed(evt);
            }
        });
        FormInput.add(FaktorPenyakit1);
        FaktorPenyakit1.setBounds(90, 560, 90, 20);

        FaktorPenyakit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Positif", "Negatif", "Tidak diperiksa" }));
        FaktorPenyakit2.setName("FaktorPenyakit2"); // NOI18N
        FaktorPenyakit2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorPenyakit2KeyPressed(evt);
            }
        });
        FormInput.add(FaktorPenyakit2);
        FaktorPenyakit2.setBounds(90, 590, 90, 20);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Lainnya");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(220, 530, 60, 20);

        FaktorPenyakit3.setHighlighter(null);
        FaktorPenyakit3.setName("FaktorPenyakit3"); // NOI18N
        FaktorPenyakit3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorPenyakit3KeyPressed(evt);
            }
        });
        FormInput.add(FaktorPenyakit3);
        FaktorPenyakit3.setBounds(270, 530, 200, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Leukocyt");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(490, 530, 60, 20);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("LED");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(490, 560, 60, 20);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("GDS");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(490, 590, 60, 20);

        FaktorPenyakit4.setHighlighter(null);
        FaktorPenyakit4.setName("FaktorPenyakit4"); // NOI18N
        FaktorPenyakit4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorPenyakit4KeyPressed(evt);
            }
        });
        FormInput.add(FaktorPenyakit4);
        FaktorPenyakit4.setBounds(550, 530, 160, 23);

        FaktorPenyakit5.setHighlighter(null);
        FaktorPenyakit5.setName("FaktorPenyakit5"); // NOI18N
        FaktorPenyakit5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorPenyakit5KeyPressed(evt);
            }
        });
        FormInput.add(FaktorPenyakit5);
        FaktorPenyakit5.setBounds(550, 560, 160, 23);

        FaktorPenyakit6.setHighlighter(null);
        FaktorPenyakit6.setName("FaktorPenyakit6"); // NOI18N
        FaktorPenyakit6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorPenyakit6KeyPressed(evt);
            }
        });
        FormInput.add(FaktorPenyakit6);
        FaktorPenyakit6.setBounds(550, 590, 160, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Hasil Radiologi :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(20, 630, 130, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        HRadiologi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        HRadiologi.setColumns(20);
        HRadiologi.setRows(5);
        HRadiologi.setName("HRadiologi"); // NOI18N
        HRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HRadiologiKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(HRadiologi);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(110, 630, 610, 60);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("TINDAKAN / OPERASI");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 700, 190, 30);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        TglOperasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TglOperasi.setColumns(20);
        TglOperasi.setRows(5);
        TglOperasi.setName("TglOperasi"); // NOI18N
        TglOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglOperasiKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(TglOperasi);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(320, 750, 270, 43);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Diagnosa :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(20, 730, 170, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        DiagnosaSekunder.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaSekunder.setColumns(20);
        DiagnosaSekunder.setRows(5);
        DiagnosaSekunder.setName("DiagnosaSekunder"); // NOI18N
        DiagnosaSekunder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunderKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(DiagnosaSekunder);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(20, 750, 280, 43);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Tanggal Operasi :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(320, 730, 190, 23);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("Jenis Operasi");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(620, 730, 100, 23);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Tindakan Operasi ");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(620, 750, 100, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("ASA Score");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(620, 770, 100, 23);

        JenisOperasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bersih", "Bersih tercemar", "Tercemar", "Kotor" }));
        JenisOperasi.setName("JenisOperasi"); // NOI18N
        JenisOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisOperasiKeyPressed(evt);
            }
        });
        FormInput.add(JenisOperasi);
        JenisOperasi.setBounds(730, 730, 90, 20);

        TindakanOperasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cito", "Elektif" }));
        TindakanOperasi.setName("TindakanOperasi"); // NOI18N
        TindakanOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanOperasiKeyPressed(evt);
            }
        });
        FormInput.add(TindakanOperasi);
        TindakanOperasi.setBounds(730, 750, 90, 20);

        ASAScore.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
        ASAScore.setName("ASAScore"); // NOI18N
        ASAScore.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ASAScoreKeyPressed(evt);
            }
        });
        FormInput.add(ASAScore);
        ASAScore.setBounds(730, 770, 90, 20);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("KOMPLIKASI  / INFEKSI NOSOKOMIAL");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(10, 800, 190, 30);

        jLabel107.setText("ILO :");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(10, 830, 70, 23);

        jLabel108.setText("ISK :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(10, 860, 70, 23);

        jLabel111.setText("Pneumonia :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(10, 890, 70, 23);

        jLabel109.setText("IADP :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 920, 70, 23);

        jLabel110.setText("Lain-lain :");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 950, 70, 23);

        Komplikasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        Komplikasi.setName("Komplikasi"); // NOI18N
        Komplikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KomplikasiKeyPressed(evt);
            }
        });
        FormInput.add(Komplikasi);
        Komplikasi.setBounds(90, 830, 80, 23);

        Komplikasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        Komplikasi1.setName("Komplikasi1"); // NOI18N
        Komplikasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Komplikasi1KeyPressed(evt);
            }
        });
        FormInput.add(Komplikasi1);
        Komplikasi1.setBounds(90, 860, 80, 23);

        Komplikasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        Komplikasi2.setName("Komplikasi2"); // NOI18N
        Komplikasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Komplikasi2KeyPressed(evt);
            }
        });
        FormInput.add(Komplikasi2);
        Komplikasi2.setBounds(90, 890, 80, 23);

        Komplikasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        Komplikasi3.setName("Komplikasi3"); // NOI18N
        Komplikasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Komplikasi3KeyPressed(evt);
            }
        });
        FormInput.add(Komplikasi3);
        Komplikasi3.setBounds(90, 920, 80, 23);

        Komplikasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        Komplikasi4.setName("Komplikasi4"); // NOI18N
        Komplikasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Komplikasi4KeyPressed(evt);
            }
        });
        FormInput.add(Komplikasi4);
        Komplikasi4.setBounds(90, 950, 80, 23);

        jLabel79.setText("Hasil Kultur :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(180, 830, 70, 23);

        jLabel75.setText("Hasil Kultur :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(180, 860, 70, 23);

        jLabel76.setText("Hasil Kultur :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(180, 890, 70, 23);

        jLabel77.setText("Hasil Kultur :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(180, 920, 70, 23);

        jLabel112.setText("Hasil Kultur :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(180, 950, 70, 23);

        Kultur.setFocusTraversalPolicyProvider(true);
        Kultur.setName("Kultur"); // NOI18N
        Kultur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulturKeyPressed(evt);
            }
        });
        FormInput.add(Kultur);
        Kultur.setBounds(260, 830, 390, 23);

        Kultur1.setFocusTraversalPolicyProvider(true);
        Kultur1.setName("Kultur1"); // NOI18N
        Kultur1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kultur1KeyPressed(evt);
            }
        });
        FormInput.add(Kultur1);
        Kultur1.setBounds(260, 860, 390, 23);

        Kultur2.setFocusTraversalPolicyProvider(true);
        Kultur2.setName("Kultur2"); // NOI18N
        Kultur2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kultur2KeyPressed(evt);
            }
        });
        FormInput.add(Kultur2);
        Kultur2.setBounds(260, 890, 390, 23);

        Kultur3.setFocusTraversalPolicyProvider(true);
        Kultur3.setName("Kultur3"); // NOI18N
        Kultur3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kultur3KeyPressed(evt);
            }
        });
        FormInput.add(Kultur3);
        Kultur3.setBounds(260, 920, 390, 23);

        Kultur4.setFocusTraversalPolicyProvider(true);
        Kultur4.setName("Kultur4"); // NOI18N
        Kultur4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kultur4KeyPressed(evt);
            }
        });
        FormInput.add(Kultur4);
        Kultur4.setBounds(260, 950, 390, 23);

        jLabel74.setText("Hari Ke :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(660, 830, 70, 23);

        jLabel80.setText("Hari Ke :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(660, 860, 70, 23);

        jLabel81.setText("Hari Ke :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(660, 890, 70, 23);

        jLabel82.setText("Hari Ke :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(660, 920, 70, 23);

        jLabel113.setText("Hari Ke :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(660, 950, 70, 23);

        HariKe.setFocusTraversalPolicyProvider(true);
        HariKe.setName("HariKe"); // NOI18N
        HariKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HariKeKeyPressed(evt);
            }
        });
        FormInput.add(HariKe);
        HariKe.setBounds(740, 830, 130, 23);

        HariKe1.setFocusTraversalPolicyProvider(true);
        HariKe1.setName("HariKe1"); // NOI18N
        HariKe1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HariKe1KeyPressed(evt);
            }
        });
        FormInput.add(HariKe1);
        HariKe1.setBounds(740, 860, 130, 23);

        HariKe2.setFocusTraversalPolicyProvider(true);
        HariKe2.setName("HariKe2"); // NOI18N
        HariKe2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HariKe2KeyPressed(evt);
            }
        });
        FormInput.add(HariKe2);
        HariKe2.setBounds(740, 890, 130, 23);

        HariKe3.setFocusTraversalPolicyProvider(true);
        HariKe3.setName("HariKe3"); // NOI18N
        HariKe3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HariKe3KeyPressed(evt);
            }
        });
        FormInput.add(HariKe3);
        HariKe3.setBounds(740, 920, 130, 23);

        HariKe4.setFocusTraversalPolicyProvider(true);
        HariKe4.setName("HariKe4"); // NOI18N
        HariKe4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HariKe4KeyPressed(evt);
            }
        });
        FormInput.add(HariKe4);
        HariKe4.setBounds(740, 950, 130, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("PEMAKAIAN ANTIMIKROBA");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(10, 980, 190, 30);

        Antimikroba.setFocusTraversalPolicyProvider(true);
        Antimikroba.setName("Antimikroba"); // NOI18N
        Antimikroba.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntimikrobaKeyPressed(evt);
            }
        });
        FormInput.add(Antimikroba);
        Antimikroba.setBounds(10, 1010, 210, 23);

        Antimikroba1.setFocusTraversalPolicyProvider(true);
        Antimikroba1.setName("Antimikroba1"); // NOI18N
        Antimikroba1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Antimikroba1KeyPressed(evt);
            }
        });
        FormInput.add(Antimikroba1);
        Antimikroba1.setBounds(10, 1040, 210, 23);

        Antimikroba2.setFocusTraversalPolicyProvider(true);
        Antimikroba2.setName("Antimikroba2"); // NOI18N
        Antimikroba2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Antimikroba2KeyPressed(evt);
            }
        });
        FormInput.add(Antimikroba2);
        Antimikroba2.setBounds(10, 1070, 210, 23);

        Antimikroba3.setFocusTraversalPolicyProvider(true);
        Antimikroba3.setName("Antimikroba3"); // NOI18N
        Antimikroba3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Antimikroba3KeyPressed(evt);
            }
        });
        FormInput.add(Antimikroba3);
        Antimikroba3.setBounds(10, 1100, 210, 23);

        jLabel115.setText("dosis");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(220, 1010, 30, 23);

        jLabel116.setText("dosis");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(220, 1040, 30, 23);

        jLabel117.setText("dosis");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(220, 1070, 30, 23);

        jLabel118.setText("dosis");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(220, 1100, 30, 23);

        dosis.setFocusTraversalPolicyProvider(true);
        dosis.setName("dosis"); // NOI18N
        dosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dosisKeyPressed(evt);
            }
        });
        FormInput.add(dosis);
        dosis.setBounds(260, 1010, 210, 23);

        dosis1.setFocusTraversalPolicyProvider(true);
        dosis1.setName("dosis1"); // NOI18N
        dosis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dosis1KeyPressed(evt);
            }
        });
        FormInput.add(dosis1);
        dosis1.setBounds(260, 1040, 210, 23);

        dosis2.setFocusTraversalPolicyProvider(true);
        dosis2.setName("dosis2"); // NOI18N
        dosis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dosis2KeyPressed(evt);
            }
        });
        FormInput.add(dosis2);
        dosis2.setBounds(260, 1070, 210, 23);

        dosis3.setFocusTraversalPolicyProvider(true);
        dosis3.setName("dosis3"); // NOI18N
        dosis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dosis3KeyPressed(evt);
            }
        });
        FormInput.add(dosis3);
        dosis3.setBounds(260, 1100, 210, 23);

        jLabel83.setText("mulai tgl ");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(460, 1010, 60, 23);

        jLabel85.setText("mulai tgl ");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(460, 1040, 60, 23);

        jLabel87.setText("mulai tgl ");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(460, 1070, 60, 23);

        jLabel88.setText("mulai tgl ");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(460, 1100, 60, 23);

        TglMulai1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulai1.setDisplayFormat("dd-MM-yyyy");
        TglMulai1.setName("TglMulai1"); // NOI18N
        TglMulai1.setOpaque(false);
        TglMulai1.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulai1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulai1ItemStateChanged(evt);
            }
        });
        TglMulai1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulai1KeyPressed(evt);
            }
        });
        FormInput.add(TglMulai1);
        TglMulai1.setBounds(530, 1010, 90, 23);

        TglMulai2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulai2.setDisplayFormat("dd-MM-yyyy");
        TglMulai2.setName("TglMulai2"); // NOI18N
        TglMulai2.setOpaque(false);
        TglMulai2.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulai2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulai2ItemStateChanged(evt);
            }
        });
        TglMulai2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulai2KeyPressed(evt);
            }
        });
        FormInput.add(TglMulai2);
        TglMulai2.setBounds(530, 1040, 90, 23);

        TglMulai3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulai3.setDisplayFormat("dd-MM-yyyy");
        TglMulai3.setName("TglMulai3"); // NOI18N
        TglMulai3.setOpaque(false);
        TglMulai3.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulai3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulai3ItemStateChanged(evt);
            }
        });
        TglMulai3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulai3KeyPressed(evt);
            }
        });
        FormInput.add(TglMulai3);
        TglMulai3.setBounds(530, 1070, 90, 23);

        TglMulai4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglMulai4.setDisplayFormat("dd-MM-yyyy");
        TglMulai4.setName("TglMulai4"); // NOI18N
        TglMulai4.setOpaque(false);
        TglMulai4.setPreferredSize(new java.awt.Dimension(95, 23));
        TglMulai4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMulai4ItemStateChanged(evt);
            }
        });
        TglMulai4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulai4KeyPressed(evt);
            }
        });
        FormInput.add(TglMulai4);
        TglMulai4.setBounds(530, 1100, 90, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("TANGGAL PASIEN KELUAR RS / MENINGGAL");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(10, 1130, 230, 30);

        jLabel90.setText("Tanggal : ");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(10, 1160, 90, 23);

        TanggalKeluar.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024 10:27:14" }));
        TanggalKeluar.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalKeluar.setName("TanggalKeluar"); // NOI18N
        TanggalKeluar.setOpaque(false);
        TanggalKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalKeluarActionPerformed(evt);
            }
        });
        TanggalKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeluarKeyPressed(evt);
            }
        });
        FormInput.add(TanggalKeluar);
        TanggalKeluar.setBounds(110, 1160, 120, 23);

        jLabel135.setText("Pindah ke RS : ");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(10, 1190, 90, 23);

        Pindah.setFocusTraversalPolicyProvider(true);
        Pindah.setName("Pindah"); // NOI18N
        Pindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PindahKeyPressed(evt);
            }
        });
        FormInput.add(Pindah);
        Pindah.setBounds(110, 1190, 630, 23);

        jLabel136.setText("Diagnosa Akhir : ");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(260, 1160, 90, 23);

        DiagnosaAkhir.setFocusTraversalPolicyProvider(true);
        DiagnosaAkhir.setName("DiagnosaAkhir"); // NOI18N
        DiagnosaAkhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaAkhirKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaAkhir);
        DiagnosaAkhir.setBounds(360, 1160, 330, 23);

        label18.setText("Ka. Ruangan : ");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(20, 1220, 80, 23);

        KdPetugas1.setEditable(false);
        KdPetugas1.setName("KdPetugas1"); // NOI18N
        KdPetugas1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas1);
        KdPetugas1.setBounds(110, 1220, 100, 23);

        NmPetugas1.setEditable(false);
        NmPetugas1.setName("NmPetugas1"); // NOI18N
        NmPetugas1.setPreferredSize(new java.awt.Dimension(207, 23));
        NmPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmPetugas1ActionPerformed(evt);
            }
        });
        FormInput.add(NmPetugas1);
        NmPetugas1.setBounds(220, 1220, 180, 23);

        BtnMenerima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMenerima.setMnemonic('2');
        BtnMenerima.setToolTipText("Alt+2");
        BtnMenerima.setName("BtnMenerima"); // NOI18N
        BtnMenerima.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnMenerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenerimaActionPerformed(evt);
            }
        });
        BtnMenerima.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnMenerimaKeyPressed(evt);
            }
        });
        FormInput.add(BtnMenerima);
        BtnMenerima.setBounds(400, 1220, 28, 23);

        TglInput.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
        TglInput.setDisplayFormat("dd-MM-yyyy");
        TglInput.setName("TglInput"); // NOI18N
        TglInput.setOpaque(false);
        TglInput.setPreferredSize(new java.awt.Dimension(95, 23));
        TglInput.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInputItemStateChanged(evt);
            }
        });
        TglInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInputKeyPressed(evt);
            }
        });
        FormInput.add(TglInput);
        TglInput.setBounds(680, 103, 170, 20);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Survei", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-12-2024" }));
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

        TabRawat.addTab("Data Survei", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);
        TabRawat.getAccessibleContext().setAccessibleName("Input Survei");

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(KdPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Nama Petugas");
        }else if(KdPetugas1.getText().trim().equals("")){
            Valid.textKosong(KdPetugas1,"Nama Ka. Ruangan");
        }else{
            if(Sequel.menyimpantf("survei_nosokomal1","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",79,new String[]{
                    TNoRw.getText(),KdPetugas.getText(),KdPetugas1.getText(),PindahKe1.getText(),Valid.SetTgl(Tglke.getSelectedItem()+""),PindahKe.getText(),Valid.SetTgl(Tglke1.getSelectedItem()+""),Lokasi.getText(),Thari.getText(),Valid.SetTgl(TglInfeksi.getSelectedItem()+""),
                    Lokasi1.getText(),Thari1.getText(),Valid.SetTgl(TglInfeksi1.getSelectedItem()+""),Lokasi2.getText(),Thari2.getText(),Valid.SetTgl(TglInfeksi2.getSelectedItem()+""),Lokasi3.getText(),Thari3.getText(),Valid.SetTgl(TglInfeksi3.getSelectedItem()+""),Lokasi4.getText(),
                    Thari4.getText(),Valid.SetTgl(TglInfeksi4.getSelectedItem()+""),Lokasi5.getText(),Thari5.getText(),Valid.SetTgl(TglInfeksi5.getSelectedItem()+""),Lokasi6.getText(),Thari6.getText(),Valid.SetTgl(TglInfeksi6.getSelectedItem()+""),Lokasi7.getText(),Thari7.getText(),
                    Valid.SetTgl(TglInfeksi7.getSelectedItem()+""),Lokasi8.getText(),Thari8.getText(),Valid.SetTgl(TglInfeksi8.getSelectedItem()+""),FaktorPenyakit.getSelectedItem().toString(),FaktorPenyakit1.getSelectedItem().toString(),FaktorPenyakit2.getSelectedItem().toString(),FaktorPenyakit4.getText(),FaktorPenyakit5.getText(),FaktorPenyakit6.getText(),
                    FaktorPenyakit3.getText(),HRadiologi.getText(),DiagnosaSekunder.getText(),TglOperasi.getText(),JenisOperasi.getSelectedItem().toString(),TindakanOperasi.getSelectedItem().toString(),ASAScore.getSelectedItem().toString(),Komplikasi.getSelectedItem().toString(),Kultur.getText(),HariKe.getText(),
                    Komplikasi1.getSelectedItem().toString(),Kultur1.getText(),HariKe1.getText(),Komplikasi2.getSelectedItem().toString(),Kultur2.getText(),HariKe2.getText(),Komplikasi3.getSelectedItem().toString(),Kultur3.getText(),HariKe3.getText(),Komplikasi4.getSelectedItem().toString(),
                    Kultur4.getText(),HariKe4.getText(),Antimikroba.getText(),dosis.getText(),Valid.SetTgl(TglMulai1.getSelectedItem()+""),Antimikroba1.getText(),dosis1.getText(),Valid.SetTgl(TglMulai2.getSelectedItem()+""),Antimikroba2.getText(),dosis2.getText(),
                    Valid.SetTgl(TglMulai3.getSelectedItem()+""),Antimikroba3.getText(),dosis3.getText(),Valid.SetTgl(TglMulai4.getSelectedItem()+""),Valid.SetTgl(TanggalKeluar.getSelectedItem()+""),DiagnosaAkhir.getText(),Pindah.getText(),CaraRawat.getSelectedItem().toString(),Valid.SetTgl(TglInput.getSelectedItem()+"")
                })==true){
                    tampil();
                    emptTeks();
                    TabRawat.setSelectedIndex(1);
            }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa input 1 per hari..!!");
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString())){
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
            Valid.textKosong(BtnDokter,"Dokter");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString())){
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
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
//        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        if(tabMode.getRowCount()==0){
//            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
//            BtnBatal.requestFocus();
//        }else if(tabMode.getRowCount()!=0){
//            try{
//                if(TCari.getText().trim().equals("")){
//                    ps=koneksi.prepareStatement(
//                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_psikologi.tanggal,"+
//                        "penilaian_psikologi.nip,penilaian_psikologi.anamnesis,penilaian_psikologi.dikirim_dari,penilaian_psikologi.tujuan_pemeriksaan,penilaian_psikologi.ket_anamnesis,penilaian_psikologi.rupa,penilaian_psikologi.bentuk_tubuh,penilaian_psikologi.tindakan,"+
//                        "penilaian_psikologi.pakaian,penilaian_psikologi.ekspresi,penilaian_psikologi.berbicara,penilaian_psikologi.penggunaan_kata,penilaian_psikologi.ciri_menyolok,penilaian_psikologi.hasil_psikotes,penilaian_psikologi.kepribadian,penilaian_psikologi.psikodinamika,penilaian_psikologi.kesimpulan_psikolog,petugas.nama "+
//                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
//                        "inner join penilaian_psikologi on reg_periksa.no_rawat=penilaian_psikologi.no_rawat "+
//                        "inner join petugas on penilaian_psikologi.nip=petugas.nip where "+
//                        "penilaian_psikologi.tanggal between ? and ? order by penilaian_psikologi.tanggal");
//                }else{
//                    ps=koneksi.prepareStatement(
//                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_psikologi.tanggal,"+
//                        "penilaian_psikologi.nip,penilaian_psikologi.anamnesis,penilaian_psikologi.dikirim_dari,penilaian_psikologi.tujuan_pemeriksaan,penilaian_psikologi.ket_anamnesis,penilaian_psikologi.rupa,penilaian_psikologi.bentuk_tubuh,penilaian_psikologi.tindakan,"+
//                        "penilaian_psikologi.pakaian,penilaian_psikologi.ekspresi,penilaian_psikologi.berbicara,penilaian_psikologi.penggunaan_kata,penilaian_psikologi.ciri_menyolok,penilaian_psikologi.hasil_psikotes,penilaian_psikologi.kepribadian,penilaian_psikologi.psikodinamika,penilaian_psikologi.kesimpulan_psikolog,petugas.nama "+
//                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
//                        "inner join penilaian_psikologi on reg_periksa.no_rawat=penilaian_psikologi.no_rawat "+
//                        "inner join petugas on penilaian_psikologi.nip=petugas.nip where "+
//                        "penilaian_psikologi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
//                        "penilaian_psikologi.nip like ? or petugas.nama like ?) order by penilaian_psikologi.tanggal");
//                }
//
//                try {
//                    if(TCari.getText().trim().equals("")){
//                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
//                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
//                    }else{
//                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
//                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
//                        ps.setString(3,"%"+TCari.getText()+"%");
//                        ps.setString(4,"%"+TCari.getText()+"%");
//                        ps.setString(5,"%"+TCari.getText()+"%");
//                        ps.setString(6,"%"+TCari.getText()+"%");
//                        ps.setString(7,"%"+TCari.getText()+"%");
//                    }  
//                    rs=ps.executeQuery();
//                    htmlContent = new StringBuilder();
//                    htmlContent.append(                             
//                        "<tr class='isi'>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>No.RM</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Pasien</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'><b>J.K.</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>NIP</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Dokter</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'><b>Tanggal</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='72px'><b>Dikirim Dari</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='62px'><b>Tujuan Pemeriksaan</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Informasi</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='165px'><b>Keterangan Informasi</b></td>"+
//			    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='71px'><b>Rupa</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='79px'><b>Bentuk Tubuh</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='103px'><b>Tindakan</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='99px'><b>Pakaian</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Penyampaian/Ekspresi</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='196px'><b>Berbicara</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='185px'><b>Penggunaan Kata</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Ciri Yang Menyolok</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Hasil Psikotes</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Kepribadian</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Psikodinamika</b></td>"+
//                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Kesimpulan Psikolog</b></td>"+
//                        "</tr>"
//                    );
//                    while(rs.next()){
//                        htmlContent.append(
//                            "<tr class='isi'>"+
//                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
//                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
//                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
//                               "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
//                               "<td valign='top'>"+rs.getString("jk")+"</td>"+
//                               "<td valign='top'>"+rs.getString("nip")+"</td>"+
//                               "<td valign='top'>"+rs.getString("nama")+"</td>"+
//                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
//                               "<td valign='top'>"+rs.getString("dikirim_dari")+"</td>"+
//                               "<td valign='top'>"+rs.getString("tujuan_pemeriksaan")+"</td>"+
//                               "<td valign='top'>"+rs.getString("anamnesis")+"</td>"+
//                               "<td valign='top'>"+rs.getString("ket_anamnesis")+"</td>"+
//                               "<td valign='top'>"+rs.getString("rupa")+"</td>"+
//                               "<td valign='top'>"+rs.getString("bentuk_tubuh")+"</td>"+
//                               "<td valign='top'>"+rs.getString("tindakan")+"</td>"+
//                               "<td valign='top'>"+rs.getString("pakaian")+"</td>"+
//                               "<td valign='top'>"+rs.getString("ekspresi")+"</td>"+
//                               "<td valign='top'>"+rs.getString("berbicara")+"</td>"+
//                               "<td valign='top'>"+rs.getString("penggunaan_kata")+"</td>"+
//                               "<td valign='top'>"+rs.getString("ciri_menyolok")+"</td>"+
//                               "<td valign='top'>"+rs.getString("hasil_psikotes")+"</td>"+
//                               "<td valign='top'>"+rs.getString("kepribadian")+"</td>"+
//                               "<td valign='top'>"+rs.getString("psikodinamika")+"</td>"+
//                               "<td valign='top'>"+rs.getString("kesimpulan_psikolog")+"</td>"+
//                            "</tr>");
//                    }
//                    LoadHTML.setText(
//                        "<html>"+
//                          "<table width='2900' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
//                           htmlContent.toString()+
//                          "</table>"+
//                        "</html>"
//                    );
//
//                    File g = new File("file2.css");            
//                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
//                    bg.write(
//                        ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
//                        ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
//                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
//                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
//                        ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
//                        ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
//                        ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
//                        ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
//                        ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
//                    );
//                    bg.close();
//
//                    File f = new File("DataPenilaianPsikolog.html");            
//                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
//                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
//                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
//                                "<table width='2900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
//                                    "<tr class='isi2'>"+
//                                        "<td valign='top' align='center'>"+
//                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
//                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
//                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
//                                            "<font size='2' face='Tahoma'>DATA PENILAIAN PSIKOLOG<br><br></font>"+        
//                                        "</td>"+
//                                   "</tr>"+
//                                "</table>")
//                    );
//                    bw.close();                         
//                    Desktop.getDesktop().browse(f.toURI());
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
//
//            }catch(Exception e){
//                System.out.println("Notifikasi : "+e);
//            }
//        }
//        this.setCursor(Cursor.getDefaultCursor());
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

    private void KetLokalisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLokalisKeyPressed

    }//GEN-LAST:event_KetLokalisKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
//        if(tbObat.getSelectedRow()>-1){
//            Map<String, Object> param = new HashMap<>();
//            param.put("namars",akses.getnamars());
//            param.put("alamatrs",akses.getalamatrs());
//            param.put("kotars",akses.getkabupatenrs());
//            param.put("propinsirs",akses.getpropinsirs());
//            param.put("kontakrs",akses.getkontakrs());
//            param.put("emailrs",akses.getemailrs());          
//            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
//            param.put("lokalis",Sequel.cariGambar("select lokalis from gambar")); 
//            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
//            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
//            
//            Valid.MyReportqry("rptCetakPenilaianPsikolog.jasper","report","::[ Laporan Penilaian Psikolog ]::",
//                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_psikologi.tanggal,"+
//                        "penilaian_psikologi.nip,penilaian_psikologi.anamnesis,penilaian_psikologi.dikirim_dari,penilaian_psikologi.tujuan_pemeriksaan,penilaian_psikologi.ket_anamnesis,penilaian_psikologi.rupa,penilaian_psikologi.bentuk_tubuh,penilaian_psikologi.tindakan,"+
//                        "penilaian_psikologi.pakaian,penilaian_psikologi.ekspresi,penilaian_psikologi.berbicara,penilaian_psikologi.penggunaan_kata,penilaian_psikologi.ciri_menyolok,penilaian_psikologi.hasil_psikotes,penilaian_psikologi.kepribadian,penilaian_psikologi.psikodinamika,penilaian_psikologi.kesimpulan_psikolog,petugas.nama "+
//                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
//                        "inner join penilaian_psikologi on reg_periksa.no_rawat=penilaian_psikologi.no_rawat "+
//                        "inner join petugas on penilaian_psikologi.nip=petugas.nip where penilaian_psikologi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
//        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed

    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void CaraRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraRawatKeyPressed

    }//GEN-LAST:event_CaraRawatKeyPressed

    private void Tglke1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tglke1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tglke1KeyPressed

    private void Tglke1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Tglke1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_Tglke1ItemStateChanged

    private void TglkeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglkeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglkeKeyPressed

    private void TglkeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglkeItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglkeItemStateChanged

    private void KamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KamarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KamarActionPerformed

    private void DiagnosaAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiagnosaAwalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaAwalActionPerformed

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed

    }//GEN-LAST:event_AlamatKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void PindahKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PindahKeKeyPressed

    }//GEN-LAST:event_PindahKeKeyPressed

    private void PindahKe1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PindahKe1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PindahKe1KeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LokasiKeyPressed

    private void TglMulaiLokasiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulaiLokasiItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasiItemStateChanged

    private void TglMulaiLokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaiLokasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasiKeyPressed

    private void TglSdLokasiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSdLokasiItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasiItemStateChanged

    private void TglSdLokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSdLokasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasiKeyPressed

    private void ThariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ThariKeyPressed

    private void TglInfeksiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInfeksiItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksiItemStateChanged

    private void TglInfeksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInfeksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksiKeyPressed

    private void LokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LokasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LokasiActionPerformed

    private void Lokasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Lokasi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi1ActionPerformed

    private void Lokasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lokasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi1KeyPressed

    private void TglMulaiLokasi1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulaiLokasi1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi1ItemStateChanged

    private void TglMulaiLokasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaiLokasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi1KeyPressed

    private void TglSdLokasi1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSdLokasi1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi1ItemStateChanged

    private void TglSdLokasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSdLokasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi1KeyPressed

    private void Thari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Thari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Thari1KeyPressed

    private void TglInfeksi1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInfeksi1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi1ItemStateChanged

    private void TglInfeksi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInfeksi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi1KeyPressed

    private void TglSdLokasi2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSdLokasi2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi2ItemStateChanged

    private void TglSdLokasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSdLokasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi2KeyPressed

    private void TglInfeksi2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInfeksi2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi2ItemStateChanged

    private void TglInfeksi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInfeksi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi2KeyPressed

    private void Lokasi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Lokasi2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi2ActionPerformed

    private void Lokasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lokasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi2KeyPressed

    private void Thari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Thari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Thari2KeyPressed

    private void TglMulaiLokasi2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulaiLokasi2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi2ItemStateChanged

    private void TglMulaiLokasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaiLokasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi2KeyPressed

    private void Thari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Thari3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Thari3KeyPressed

    private void Lokasi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Lokasi3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi3ActionPerformed

    private void Lokasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lokasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi3KeyPressed

    private void TglSdLokasi3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSdLokasi3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi3ItemStateChanged

    private void TglSdLokasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSdLokasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi3KeyPressed

    private void TglInfeksi3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInfeksi3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi3ItemStateChanged

    private void TglInfeksi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInfeksi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi3KeyPressed

    private void TglMulaiLokasi3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulaiLokasi3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi3ItemStateChanged

    private void TglMulaiLokasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaiLokasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi3KeyPressed

    private void TglInfeksi4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInfeksi4ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi4ItemStateChanged

    private void TglInfeksi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInfeksi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi4KeyPressed

    private void TglMulaiLokasi4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulaiLokasi4ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi4ItemStateChanged

    private void TglMulaiLokasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaiLokasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi4KeyPressed

    private void Thari4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Thari4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Thari4KeyPressed

    private void TglSdLokasi4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSdLokasi4ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi4ItemStateChanged

    private void TglSdLokasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSdLokasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi4KeyPressed

    private void Lokasi4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Lokasi4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi4ActionPerformed

    private void Lokasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lokasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi4KeyPressed

    private void TglMulaiLokasi5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulaiLokasi5ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi5ItemStateChanged

    private void TglMulaiLokasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaiLokasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi5KeyPressed

    private void Thari5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Thari5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Thari5KeyPressed

    private void TglSdLokasi5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSdLokasi5ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi5ItemStateChanged

    private void TglSdLokasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSdLokasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi5KeyPressed

    private void Lokasi5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Lokasi5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi5ActionPerformed

    private void Lokasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lokasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi5KeyPressed

    private void TglInfeksi5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInfeksi5ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi5ItemStateChanged

    private void TglInfeksi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInfeksi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi5KeyPressed

    private void Lokasi6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Lokasi6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi6ActionPerformed

    private void Lokasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lokasi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi6KeyPressed

    private void TglMulaiLokasi6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulaiLokasi6ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi6ItemStateChanged

    private void TglMulaiLokasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaiLokasi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi6KeyPressed

    private void Thari6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Thari6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Thari6KeyPressed

    private void TglInfeksi6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInfeksi6ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi6ItemStateChanged

    private void TglInfeksi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInfeksi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi6KeyPressed

    private void TglSdLokasi6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSdLokasi6ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi6ItemStateChanged

    private void TglSdLokasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSdLokasi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi6KeyPressed

    private void TglMulaiLokasi7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulaiLokasi7ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi7ItemStateChanged

    private void TglMulaiLokasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaiLokasi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi7KeyPressed

    private void Lokasi7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Lokasi7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi7ActionPerformed

    private void Lokasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lokasi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi7KeyPressed

    private void TglSdLokasi7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSdLokasi7ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi7ItemStateChanged

    private void TglSdLokasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSdLokasi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi7KeyPressed

    private void Thari7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Thari7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Thari7KeyPressed

    private void TglInfeksi7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInfeksi7ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi7ItemStateChanged

    private void TglInfeksi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInfeksi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi7KeyPressed

    private void TglMulaiLokasi8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulaiLokasi8ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi8ItemStateChanged

    private void TglMulaiLokasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaiLokasi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi8KeyPressed

    private void Thari8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Thari8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Thari8KeyPressed

    private void Lokasi8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Lokasi8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi8ActionPerformed

    private void Lokasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lokasi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lokasi8KeyPressed

    private void TglInfeksi8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInfeksi8ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi8ItemStateChanged

    private void TglInfeksi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInfeksi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInfeksi8KeyPressed

    private void TglSdLokasi8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSdLokasi8ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi8ItemStateChanged

    private void TglSdLokasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSdLokasi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSdLokasi8KeyPressed

    private void FaktorPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorPenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaktorPenyakitKeyPressed

    private void FaktorPenyakit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorPenyakit1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaktorPenyakit1KeyPressed

    private void FaktorPenyakit2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorPenyakit2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaktorPenyakit2KeyPressed

    private void FaktorPenyakit3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorPenyakit3KeyPressed

    }//GEN-LAST:event_FaktorPenyakit3KeyPressed

    private void FaktorPenyakit4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorPenyakit4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaktorPenyakit4KeyPressed

    private void FaktorPenyakit5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorPenyakit5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaktorPenyakit5KeyPressed

    private void FaktorPenyakit6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorPenyakit6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaktorPenyakit6KeyPressed

    private void HRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HRadiologiKeyPressed
        //        Valid.pindah2(evt,SuhuSebelumTransfer,KeadaanUmumSetelahTransfer);
    }//GEN-LAST:event_HRadiologiKeyPressed

    private void TglOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglOperasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglOperasiKeyPressed

    private void DiagnosaSekunderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunderKeyPressed
        //        Valid.pindah2(evt,DiagnosaUtama,ProsedurDilakukan);
    }//GEN-LAST:event_DiagnosaSekunderKeyPressed

    private void JenisOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisOperasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisOperasiKeyPressed

    private void TindakanOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanOperasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakanOperasiKeyPressed

    private void ASAScoreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ASAScoreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ASAScoreKeyPressed

    private void KomplikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KomplikasiKeyPressed
        //        Valid.pindah(evt,HubunganMenyetujui,TDSebelumTransfer);
    }//GEN-LAST:event_KomplikasiKeyPressed

    private void Komplikasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Komplikasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Komplikasi1KeyPressed

    private void Komplikasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Komplikasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Komplikasi2KeyPressed

    private void Komplikasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Komplikasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Komplikasi3KeyPressed

    private void Komplikasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Komplikasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Komplikasi4KeyPressed

    private void KulturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulturKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulturKeyPressed

    private void Kultur1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kultur1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kultur1KeyPressed

    private void Kultur2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kultur2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kultur2KeyPressed

    private void Kultur3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kultur3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kultur3KeyPressed

    private void Kultur4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kultur4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kultur4KeyPressed

    private void HariKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HariKeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HariKeKeyPressed

    private void HariKe1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HariKe1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HariKe1KeyPressed

    private void HariKe2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HariKe2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HariKe2KeyPressed

    private void HariKe3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HariKe3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HariKe3KeyPressed

    private void HariKe4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HariKe4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HariKe4KeyPressed

    private void AntimikrobaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntimikrobaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AntimikrobaKeyPressed

    private void Antimikroba1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Antimikroba1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Antimikroba1KeyPressed

    private void Antimikroba2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Antimikroba2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Antimikroba2KeyPressed

    private void Antimikroba3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Antimikroba3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Antimikroba3KeyPressed

    private void dosis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dosis3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dosis3KeyPressed

    private void dosis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dosis2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dosis2KeyPressed

    private void dosis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dosis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dosis1KeyPressed

    private void dosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dosisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dosisKeyPressed

    private void TglMulai1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulai1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulai1ItemStateChanged

    private void TglMulai1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulai1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulai1KeyPressed

    private void TglMulai2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulai2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulai2ItemStateChanged

    private void TglMulai2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulai2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulai2KeyPressed

    private void TglMulai3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulai3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulai3ItemStateChanged

    private void TglMulai3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulai3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulai3KeyPressed

    private void TglMulai4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMulai4ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulai4ItemStateChanged

    private void TglMulai4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulai4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulai4KeyPressed

    private void TanggalKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalKeluarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalKeluarActionPerformed

    private void TanggalKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeluarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalKeluarKeyPressed

    private void PindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PindahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PindahKeyPressed

    private void DiagnosaAkhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaAkhirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaAkhirKeyPressed

    private void NmPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmPetugas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugas1ActionPerformed

    private void BtnMenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenerimaActionPerformed
        petugas1.emptTeks();
        petugas1.isCek();
        petugas1.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas1.setLocationRelativeTo(internalFrame1);
        petugas1.setVisible(true);
    }//GEN-LAST:event_BtnMenerimaActionPerformed

    private void BtnMenerimaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnMenerimaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnMenerimaKeyPressed

    private void TglMulaiLokasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglMulaiLokasi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasi1ActionPerformed

    private void TglMulaiLokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglMulaiLokasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglMulaiLokasiActionPerformed

    private void TglSelesai3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSelesai3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSelesai3KeyPressed

    private void TglSelesai3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSelesai3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSelesai3ItemStateChanged

    private void TglSelesai2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSelesai2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSelesai2KeyPressed

    private void TglSelesai2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSelesai2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSelesai2ItemStateChanged

    private void TglSelesai1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSelesai1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSelesai1KeyPressed

    private void TglSelesai1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSelesai1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSelesai1ItemStateChanged

    private void TglSelesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSelesaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSelesaiKeyPressed

    private void TglSelesaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSelesaiItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSelesaiItemStateChanged

    private void TglInputItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInputItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInputItemStateChanged

    private void TglInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInputKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglInputKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgFrmNosokomal dialog = new DlgFrmNosokomal(new javax.swing.JFrame(), true);
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
    private widget.ComboBox ASAScore;
    private javax.swing.JTextArea Alamat;
    private widget.TextBox Antimikroba;
    private widget.TextBox Antimikroba1;
    private widget.TextBox Antimikroba2;
    private widget.TextBox Antimikroba3;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnMenerima;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CaraRawat;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaAkhir;
    private widget.TextBox DiagnosaAwal;
    private widget.TextArea DiagnosaSekunder;
    private widget.ComboBox FaktorPenyakit;
    private widget.ComboBox FaktorPenyakit1;
    private widget.ComboBox FaktorPenyakit2;
    private widget.TextBox FaktorPenyakit3;
    private widget.TextBox FaktorPenyakit4;
    private widget.TextBox FaktorPenyakit5;
    private widget.TextBox FaktorPenyakit6;
    private widget.PanelBiasa FormInput;
    private widget.TextArea HRadiologi;
    private widget.TextBox HariKe;
    private widget.TextBox HariKe1;
    private widget.TextBox HariKe2;
    private widget.TextBox HariKe3;
    private widget.TextBox HariKe4;
    private widget.ComboBox JenisOperasi;
    private widget.TextBox Jk;
    private widget.TextBox Kamar;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugas1;
    private widget.TextArea KetLokalis;
    private widget.ComboBox Komplikasi;
    private widget.ComboBox Komplikasi1;
    private widget.ComboBox Komplikasi2;
    private widget.ComboBox Komplikasi3;
    private widget.ComboBox Komplikasi4;
    private widget.TextBox Kultur;
    private widget.TextBox Kultur1;
    private widget.TextBox Kultur2;
    private widget.TextBox Kultur3;
    private widget.TextBox Kultur4;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox Lokasi;
    private widget.TextBox Lokasi1;
    private widget.TextBox Lokasi2;
    private widget.TextBox Lokasi3;
    private widget.TextBox Lokasi4;
    private widget.TextBox Lokasi5;
    private widget.TextBox Lokasi6;
    private widget.TextBox Lokasi7;
    private widget.TextBox Lokasi8;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas1;
    private widget.TextBox Pindah;
    private widget.TextBox PindahKe;
    private widget.TextBox PindahKe1;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalKeluar;
    private widget.TextBox TanggalMasuk;
    private widget.Tanggal TglInfeksi;
    private widget.Tanggal TglInfeksi1;
    private widget.Tanggal TglInfeksi2;
    private widget.Tanggal TglInfeksi3;
    private widget.Tanggal TglInfeksi4;
    private widget.Tanggal TglInfeksi5;
    private widget.Tanggal TglInfeksi6;
    private widget.Tanggal TglInfeksi7;
    private widget.Tanggal TglInfeksi8;
    private widget.Tanggal TglInput;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglMulai1;
    private widget.Tanggal TglMulai2;
    private widget.Tanggal TglMulai3;
    private widget.Tanggal TglMulai4;
    private widget.Tanggal TglMulaiLokasi;
    private widget.Tanggal TglMulaiLokasi1;
    private widget.Tanggal TglMulaiLokasi2;
    private widget.Tanggal TglMulaiLokasi3;
    private widget.Tanggal TglMulaiLokasi4;
    private widget.Tanggal TglMulaiLokasi5;
    private widget.Tanggal TglMulaiLokasi6;
    private widget.Tanggal TglMulaiLokasi7;
    private widget.Tanggal TglMulaiLokasi8;
    private widget.TextArea TglOperasi;
    private widget.Tanggal TglSdLokasi;
    private widget.Tanggal TglSdLokasi1;
    private widget.Tanggal TglSdLokasi2;
    private widget.Tanggal TglSdLokasi3;
    private widget.Tanggal TglSdLokasi4;
    private widget.Tanggal TglSdLokasi5;
    private widget.Tanggal TglSdLokasi6;
    private widget.Tanggal TglSdLokasi7;
    private widget.Tanggal TglSdLokasi8;
    private widget.Tanggal TglSelesai;
    private widget.Tanggal TglSelesai1;
    private widget.Tanggal TglSelesai2;
    private widget.Tanggal TglSelesai3;
    private widget.Tanggal Tglke;
    private widget.Tanggal Tglke1;
    private widget.TextBox Thari;
    private widget.TextBox Thari1;
    private widget.TextBox Thari2;
    private widget.TextBox Thari3;
    private widget.TextBox Thari4;
    private widget.TextBox Thari5;
    private widget.TextBox Thari6;
    private widget.TextBox Thari7;
    private widget.TextBox Thari8;
    private widget.ComboBox TindakanOperasi;
    private widget.TextBox dosis;
    private widget.TextBox dosis1;
    private widget.TextBox dosis2;
    private widget.TextBox dosis3;
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
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
    private widget.Label jLabel136;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel7;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
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
    private widget.Label jLabel90;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label18;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "SELECT pasien.no_rkm_medis, pasien.nm_pasien, pasien.tgl_lahir, pasien.jk, concat(pasien.alamat, ', ', kelurahan.nm_kel, ', ', kecamatan.nm_kec, ', ', kabupaten.nm_kab, ' - ', propinsi.nm_prop) as alamat, kamar_inap.tgl_masuk, kamar_inap.diagnosa_awal, kamar_inap.kd_kamar, a.nama as petugas, b.nama as ka_ruangan, survei_nosokomal1.* " +
                        "FROM reg_periksa " +
                        "INNER JOIN pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "INNER JOIN kamar_inap on kamar_inap.no_rawat=reg_periksa.no_rawat "+
                        "inner join kelurahan on kelurahan.kd_kel=pasien.kd_kel "+
                        "inner join kecamatan on kecamatan.kd_kec=pasien.kd_kec "+
                        "inner join kabupaten on kabupaten.kd_kab=pasien.kd_kab "+
                        "inner join propinsi on propinsi.kd_prop=pasien.kd_prop "+
                        "INNER JOIN survei_nosokomal1 on survei_nosokomal1.no_rawat=kamar_inap.no_rawat "+
                        "LEFT JOIN petugas a on a.nip=survei_nosokomal1.nip "+
                        "LEFT JOIN petugas b on b.nip=survei_nosokomal1.nip1 "+
                        "WHERE "+
                        "survei_nosokomal1.tgl_input between ? and ? order by survei_nosokomal1.tgl_input");
            }else{
                ps=koneksi.prepareStatement(
                        "SELECT pasien.no_rkm_medis, pasien.nm_pasien, pasien.tgl_lahir, pasien.jk, concat(pasien.alamat, ', ', kelurahan.nm_kel, ', ', kecamatan.nm_kec, ', ', kabupaten.nm_kab, ' - ', propinsi.nm_prop) as alamat, kamar_inap.tgl_masuk, kamar_inap.diagnosa_awal, kamar_inap.kd_kamar, a.nama as petugas, b.nama as ka_ruangan, survei_nosokomal1.* " +
                        "FROM reg_periksa " +
                        "INNER JOIN pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "INNER JOIN kamar_inap on kamar_inap.no_rawat=reg_periksa.no_rawat "+
                        "inner join kelurahan on kelurahan.kd_kel=pasien.kd_kel "+
                        "inner join kecamatan on kecamatan.kd_kec=pasien.kd_kec "+
                        "inner join kabupaten on kabupaten.kd_kab=pasien.kd_kab "+
                        "inner join propinsi on propinsi.kd_prop=pasien.kd_prop "+
                        "INNER JOIN survei_nosokomal1 on survei_nosokomal1.no_rawat=kamar_inap.no_rawat "+ 
                        "LEFT JOIN petugas a on a.nip=survei_nosokomal1.nip "+
                        "LEFT JOIN petugas b on b.nip=survei_nosokomal1.nip1 "+ 
                        "WHERE "+
                        "survei_nosokomal1.tgl_input between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "survei_nosokomal1.nip like ?) order by survei_nosokomal1.tgl_input");
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
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("alamat"),rs.getString("tgl_masuk"),rs.getString("cara_rawat"),rs.getString("diagnosa_awal"),rs.getString("kd_kamar"),
                        rs.getString("pindah"),rs.getString("tgl_pindah"),rs.getString("pindah1"),rs.getString("tgl_pindah1"),rs.getString("lokasi"),rs.getString("total"),rs.getString("tgl_infeksi"),rs.getString("lokasi1"),rs.getString("total1"),rs.getString("tgl_infeksi1"),
                        rs.getString("lokasi2"),rs.getString("total2"),rs.getString("tgl_infeksi2"),rs.getString("lokasi3"),rs.getString("total3"),rs.getString("tgl_infeksi3"),rs.getString("lokasi4"),rs.getString("total4"),rs.getString("tgl_infeksi4"),rs.getString("lokasi5"),
                        rs.getString("total5"),rs.getString("tgl_infeksi5"),rs.getString("lokasi6"),rs.getString("total6"),rs.getString("tgl_infeksi6"),rs.getString("lokasi7"),rs.getString("total7"),rs.getString("tgl_infeksi7"),rs.getString("lokasi8"),rs.getString("total8"),
                        rs.getString("tgl_infeksi8"),rs.getString("hbsag"),rs.getString("antihcv"),rs.getString("antihiv"),rs.getString("leukocyt"),rs.getString("led"),rs.getString("gds"),rs.getString("lainnya"),rs.getString("radiologi"),rs.getString("diagnosa"),
                        rs.getString("tgl_operasi"),rs.getString("jenis_operasi"),rs.getString("tindakan_operasi"),rs.getString("asascore"),rs.getString("ilo"),rs.getString("kultur"),rs.getString("hari"),rs.getString("isk"),rs.getString("kultur1"),rs.getString("hari1"),
                        rs.getString("pneumonia"),rs.getString("kultur2"),rs.getString("hari2"),rs.getString("iapd"),rs.getString("kultur3"),rs.getString("hari3"),rs.getString("lain"),rs.getString("kultur4"),rs.getString("hari4"),rs.getString("antimikroba"),
                        rs.getString("dosis"),rs.getString("tgl_mulai"),rs.getString("antimikroba1"),rs.getString("dosis1"),rs.getString("tgl_mulai1"),rs.getString("antimikroba2"),rs.getString("dosis2"),rs.getString("tgl_mulai2"),rs.getString("antimikroba3"),rs.getString("dosis3"),
                        rs.getString("tgl_mulai3"),rs.getString("tgl_keluar"),rs.getString("diagnosa_akhir"),rs.getString("pindah_rs"),rs.getString("petugas"),rs.getString("ka_ruangan"),rs.getString("nip"),rs.getString("nip1"),rs.getString("tgl_input")
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
        
        CaraRawat.setSelectedIndex(0);
        PindahKe.setText("-");
        Tglke.setDate(new Date());
        PindahKe1.setText("-");
        Tglke1.setDate(new Date());
        Lokasi.setText("-");
        Thari.setText("0");
        TglInfeksi.setDate(new Date());
        Lokasi1.setText("-");
        Thari1.setText("0");
        TglInfeksi1.setDate(new Date());
        Lokasi2.setText("-");
        Thari2.setText("0");
        TglInfeksi2.setDate(new Date());
        Lokasi3.setText("-");
        Thari3.setText("0");
        TglInfeksi3.setDate(new Date());
        Lokasi4.setText("-");
        Thari4.setText("0");
        TglInfeksi4.setDate(new Date());
        Lokasi5.setText("-");
        Thari5.setText("0");
        TglInfeksi5.setDate(new Date());
        Lokasi6.setText("-");
        Thari6.setText("0");
        TglInfeksi6.setDate(new Date());
        Lokasi7.setText("-");
        Thari7.setText("0");
        TglInfeksi7.setDate(new Date());
        Lokasi8.setText("-");
        Thari8.setText("0");
        TglInfeksi8.setDate(new Date());
        FaktorPenyakit.setSelectedIndex(0);
        FaktorPenyakit1.setSelectedIndex(0);
        FaktorPenyakit2.setSelectedIndex(0);
        FaktorPenyakit4.setText("-");
        FaktorPenyakit5.setText("-");
        FaktorPenyakit6.setText("-");
        HRadiologi.setText("-");
        DiagnosaSekunder.setText("-");
        TglOperasi.setText("-");
        JenisOperasi.setSelectedIndex(0);
        TindakanOperasi.setSelectedIndex(0);
        ASAScore.setSelectedIndex(0);
        Komplikasi.setSelectedIndex(0);
        Kultur.setText("-");
        HariKe.setText("0");
        Komplikasi1.setSelectedIndex(0);
        Kultur1.setText("-");
        HariKe1.setText("0");
        Komplikasi2.setSelectedIndex(0);
        Kultur2.setText("-");
        HariKe2.setText("0");
        Komplikasi3.setSelectedIndex(0);
        Kultur3.setText("-");
        HariKe3.setText("0");
        Komplikasi4.setSelectedIndex(0);
        Kultur4.setText("-");
        HariKe4.setText("0");
        Antimikroba.setText("-");
        dosis.setText("-");
        TglMulai1.setDate(new Date());
        Antimikroba1.setText("-");
        dosis1.setText("-");
        TglMulai2.setDate(new Date());
        Antimikroba2.setText("-");
        dosis2.setText("-");
        TglMulai3.setDate(new Date());
        Antimikroba3.setText("-");
        dosis3.setText("-");
        TglMulai4.setDate(new Date());
        TanggalKeluar.setDate(new Date());
        DiagnosaAkhir.setText("-");
        KdPetugas1.setText("");
        NmPetugas1.setText("");
        Pindah.setText("-");
        TglInput.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Alamat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            TanggalMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            CaraRawat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            DiagnosaAwal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            Kamar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
            PindahKe.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            Valid.SetTgl(Tglke,tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            PindahKe1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
            Valid.SetTgl(Tglke1,tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
            Thari.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            Valid.SetTgl(TglInfeksi,tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Lokasi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString()); 
            Thari1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
            Valid.SetTgl(TglInfeksi1,tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Lokasi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString()); 
            Thari2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString()); 
            Valid.SetTgl(TglInfeksi2,tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Lokasi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString()); 
            Thari3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString()); 
            Valid.SetTgl(TglInfeksi3,tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Lokasi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString()); 
            Thari4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString()); 
            Valid.SetTgl(TglInfeksi4,tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Lokasi5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString()); 
            Thari5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString()); 
            Valid.SetTgl(TglInfeksi5,tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Lokasi6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString()); 
            Thari6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString()); 
            Valid.SetTgl(TglInfeksi6,tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Lokasi7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString()); 
            Thari7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString()); 
            Valid.SetTgl(TglInfeksi7,tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Lokasi8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString()); 
            Thari8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString()); 
            Valid.SetTgl(TglInfeksi8,tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            FaktorPenyakit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            FaktorPenyakit1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            FaktorPenyakit2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            FaktorPenyakit4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString()); 
            FaktorPenyakit5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString()); 
            FaktorPenyakit6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString()); 
            FaktorPenyakit3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString()); 
            HRadiologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString()); 
            DiagnosaSekunder.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString()); 
            TglOperasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString()); 
            JenisOperasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            TindakanOperasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            ASAScore.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Komplikasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Kultur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString()); 
            HariKe.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString()); 
            Komplikasi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Kultur1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString()); 
            HariKe1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString()); 
            Komplikasi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            Kultur2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString()); 
            HariKe2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString()); 
            Komplikasi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            Kultur3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString()); 
            HariKe3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString()); 
            Komplikasi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            Kultur4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString()); 
            HariKe4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString()); 
            Antimikroba.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString()); 
            dosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString()); 
            Valid.SetTgl(TglMulai1,tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            Antimikroba1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString()); 
            dosis1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString()); 
            Valid.SetTgl(TglMulai2,tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            Antimikroba2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString()); 
            dosis2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString()); 
            Valid.SetTgl(TglMulai3,tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            Antimikroba3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString()); 
            dosis3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString()); 
            Valid.SetTgl(TglMulai4,tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            Valid.SetTgl(TanggalKeluar,tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            DiagnosaAkhir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString()); 
            Pindah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString()); 
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString()); 
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString()); 
            KdPetugas1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString()); 
            NmPetugas1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString()); 
            TabRawat.setSelectedIndex(0);
            Valid.SetTgl(TglInput,tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis, "+
                    "pasien.nm_pasien, "+
                    "if(pasien.jk='L','Laki-Laki','Perempuan') as jk, "+
                    "pasien.tgl_lahir, "+
                    "reg_periksa.tgl_registrasi, "+
                    "concat(pasien.alamat, ', ', kelurahan.nm_kel, ', ', kecamatan.nm_kec, ', ', kabupaten.nm_kab, ' - ', propinsi.nm_prop) as alamat, "+
                    "kamar_inap.tgl_masuk, "+
                    "kamar_inap.diagnosa_awal, "+
                    "kamar_inap.kd_kamar, "+
                    "kamar_inap.diagnosa_akhir "+
                    "from reg_periksa "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join kelurahan on kelurahan.kd_kel=pasien.kd_kel "+
                    "inner join kecamatan on kecamatan.kd_kec=pasien.kd_kec "+
                    "inner join kabupaten on kabupaten.kd_kab=pasien.kd_kab "+
                    "inner join propinsi on propinsi.kd_prop=pasien.kd_prop "+
                    "inner join kamar_inap on kamar_inap.no_rawat=reg_periksa.no_rawat "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
//                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Alamat.setText(rs.getString("alamat"));
                    TanggalMasuk.setText(rs.getString("tgl_masuk"));
                    Kamar.setText(rs.getString("kd_kamar"));
                    DiagnosaAwal.setText(rs.getString("diagnosa_awal"));
                    DiagnosaAkhir.setText(rs.getString("diagnosa_akhir"));
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
        
//      Menapilkan Radiologi
        try {
            ps=koneksi.prepareStatement(
                    "SELECT a.no_rawat,a.no_rkm_medis,IFNULL(nm_perawatan,'')as nm_perawatan,hasil FROM reg_periksa a "
                            + "LEFT JOIN hasil_radiologi ON hasil_radiologi.no_rawat = a.no_rawat "
                            + "LEFT JOIN periksa_radiologi ON periksa_radiologi.no_rawat = a.no_rawat "
                            + "LEFT JOIN jns_perawatan_radiologi ON jns_perawatan_radiologi.kd_jenis_prw = periksa_radiologi.kd_jenis_prw "
                            + "WHERE a.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                while(rs.next()){
                    if(HRadiologi.getText().isEmpty()){
//                        HRadiologi.setText(rs.getString("nm_perawatan")+" : "+rs.getString("hasil")+", ");
                        HRadiologi.setText(rs.getString("nm_perawatan")+", ");
                    }
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
        
//        Menapilkan Diagnosa
            try {
            DiagnosaSekunder.setText("");
            ps=koneksi.prepareStatement(
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat='"+TNoRw.getText()+"' and diagnosa_pasien.status='Ranap' order by diagnosa_pasien.prioritas ASC ");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                while(rs.next()){
                    Listpenyakit=rs.getString("kd_penyakit")+"-"+rs.getString("nm_penyakit");
                    namaPenyakit=namaPenyakit+""+Listpenyakit+",  "+"\n";
                }
                 DiagnosaSekunder.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
        Sequel.cariIsi("select pasien.nm_pasien from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TPasien); 
        Sequel.cariIsi("select pasien.nm_pasien from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TPasien); 
        Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);              
    }
    
    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void isCek(){
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
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
        if(Sequel.queryu2tf("delete from survei_nosokomal1 where no_rawat=? and tgl_input=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),88).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("survei_nosokomal1","no_rawat=?","no_rawat=?,nip=?,nip1=?,pindah=?,tgl_pindah=?,pindah1=?,tgl_pindah1=?,lokasi=?,total=?,tgl_infeksi=?,"
                + "lokasi1=?,total1=?,tgl_infeksi1=?,lokasi2=?,total2=?,tgl_infeksi2=?,lokasi3=?,total3=?,tgl_infeksi3=?,lokasi4=?,"
                + "total4=?,tgl_infeksi4=?,lokasi5=?,total5=?,tgl_infeksi5=?,lokasi6=?,total6=?,tgl_infeksi6=?,lokasi7=?,total7=?,"
                + "tgl_infeksi7=?,lokasi8=?,total8=?,tgl_infeksi8=?,hbsag=?,antihcv=?,antihiv=?,leukocyt=?,led=?,gds=?,"
                + "lainnya=?,radiologi=?,diagnosa=?,tgl_operasi=?,jenis_operasi=?,tindakan_operasi=?,asascore=?,ilo=?,kultur=?,hari=?,"
                + "isk=?,kultur1=?,hari1=?,pneumonia=?,kultur2=?,hari2=?,iapd=?,kultur3=?,hari3=?,lain=?,"
                + "kultur4=?,hari4=?,antimikroba=?,dosis=?,tgl_mulai=?,antimikroba1=?,dosis1=?,tgl_mulai1=?,antimikroba2=?,dosis2=?,"
                + "tgl_mulai2=?,antimikroba3=?,dosis3=?,tgl_mulai3=?,tgl_keluar=?,diagnosa_akhir=?,pindah_rs=?,cara_rawat=?,tgl_input=?",80,new String[]{
            TNoRw.getText(),
            KdPetugas.getText(),
            KdPetugas1.getText(),
            PindahKe1.getText(),
            Valid.SetTgl(Tglke.getSelectedItem()+""),
            PindahKe.getText(),
            Valid.SetTgl(Tglke1.getSelectedItem()+""),
            Lokasi.getText(),
            Thari.getText(),
            Valid.SetTgl(TglInfeksi.getSelectedItem()+""),
            Lokasi1.getText(),
            Thari1.getText(),
            Valid.SetTgl(TglInfeksi1.getSelectedItem()+""),
            Lokasi2.getText(),
            Thari2.getText(),
            Valid.SetTgl(TglInfeksi2.getSelectedItem()+""),
            Lokasi3.getText(),
            Thari3.getText(),
            Valid.SetTgl(TglInfeksi3.getSelectedItem()+""),
            Lokasi4.getText(),
            Thari4.getText(),
            Valid.SetTgl(TglInfeksi4.getSelectedItem()+""),
            Lokasi5.getText(),
            Thari5.getText(),
            Valid.SetTgl(TglInfeksi5.getSelectedItem()+""),
            Lokasi6.getText(),
            Thari6.getText(),
            Valid.SetTgl(TglInfeksi6.getSelectedItem()+""),
            Lokasi7.getText(),
            Thari7.getText(),
            Valid.SetTgl(TglInfeksi7.getSelectedItem()+""),
            Lokasi8.getText(),
            Thari8.getText(),
            Valid.SetTgl(TglInfeksi8.getSelectedItem()+""),
            FaktorPenyakit.getSelectedItem().toString(),
            FaktorPenyakit1.getSelectedItem().toString(),
            FaktorPenyakit2.getSelectedItem().toString(),
            FaktorPenyakit4.getText(),
            FaktorPenyakit5.getText(),
            FaktorPenyakit6.getText(), 
            FaktorPenyakit3.getText(),
            HRadiologi.getText(),
            DiagnosaSekunder.getText(),
            TglOperasi.getText(),
            JenisOperasi.getSelectedItem().toString(),
            TindakanOperasi.getSelectedItem().toString(),
            ASAScore.getSelectedItem().toString(),
            Komplikasi.getSelectedItem().toString(),
            Kultur.getText(),
            HariKe.getText(),
            Komplikasi1.getSelectedItem().toString(),
            Kultur1.getText(),
            HariKe1.getText(),
            Komplikasi2.getSelectedItem().toString(),
            Kultur2.getText(),
            HariKe2.getText(), 
            Komplikasi3.getSelectedItem().toString(),
            Kultur3.getText(),
            HariKe3.getText(),
            Komplikasi4.getSelectedItem().toString(),
            Kultur4.getText(),
            HariKe4.getText(),
            Antimikroba.getText(),
            dosis.getText(),
            Valid.SetTgl(TglMulai1.getSelectedItem()+""),
            Antimikroba1.getText(),
            dosis1.getText(),
            Valid.SetTgl(TglMulai2.getSelectedItem()+""),
            Antimikroba2.getText(),
            dosis2.getText(),
            Valid.SetTgl(TglMulai3.getSelectedItem()+""),
            Antimikroba3.getText(), 
            dosis3.getText(),
            Valid.SetTgl(TglMulai4.getSelectedItem()+""),
            Valid.SetTgl(TanggalKeluar.getSelectedItem()+""),
            DiagnosaAkhir.getText(),
            Pindah.getText(),
            CaraRawat.getSelectedItem().toString(),
            Valid.SetTgl(TglInput.getSelectedItem()+""),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            
        })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
    
    
}
