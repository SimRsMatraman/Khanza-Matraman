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
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
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
import kepegawaian.DlgCariPetugas;
import keuangan.DlgKamar;
import simrskhanza.DlgCariPoli;


/**
 *
 * @author perpustakaan
 */
public final class RMTransferPasienOk extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariPetugas petugas1=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariDokter dokter1=new DlgCariDokter(null,false);
    private DlgCariPoli ralan=new DlgCariPoli(null,false);
    public  DlgKamar ranap=new DlgKamar(null,false);
    private StringBuilder htmlContent;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMTransferPasienOk(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal Masuk OK","Tanggal Masuk Ruangan","Kd Ruang","Asal Ruang Rawat / Poliklinik","Rencana",
            "Diagnosa Pre Op","Diagnosa Post Op","Kd Dr.Operator","Dokter Operator","Kd Dr.Anastesi","Dokter Anastesi","Kd Pengirim ke Ok","Pengirim ke Ok",
            "Kd Penerima di Ok","Penerima di OK","Kd Pengirim ke Ruangan","Pengirim ke Ruangan","Kd Penerima di Ruangan","Penerima di Ruangan"
            
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(105);
            }else if(i==9){
                column.setPreferredWidth(140);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(100);
            }else if(i==14){
                column.setPreferredWidth(65);
            }else if(i==15){
                column.setPreferredWidth(100);
            }else if(i==16){
                column.setPreferredWidth(65);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setPreferredWidth(65);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(65);
            }else if(i==21){
                column.setPreferredWidth(100);
            }else if(i==22){
                column.setPreferredWidth(65);
            }else if(i==23){
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        DiagnosaPre.setDocument(new batasInput((int)50).getKata(DiagnosaPre));
        DiagnosaPost.setDocument(new batasInput((int)100).getKata(DiagnosaPost));
        
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
        jam();
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    if(pilihan==1){
                        KdPetugasMenyerahkan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasMenyerahkan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KdPetugasMenyerahkan.requestFocus();
                    }else{
                        KdPetugasMenerima.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasMenerima.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KdPetugasMenerima.requestFocus();
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
        
        petugas1.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas1.getTable().getSelectedRow()!= -1){
                    if(pilihan==1){
                        KdPetugasMenyerahkan1.setText(petugas1.getTable().getValueAt(petugas1.getTable().getSelectedRow(),0).toString());
                        NmPetugasMenyerahkan1.setText(petugas1.getTable().getValueAt(petugas1.getTable().getSelectedRow(),1).toString());
                        KdPetugasMenyerahkan1.requestFocus();
                    }else{
                        KdPetugasMenerima1.setText(petugas1.getTable().getValueAt(petugas1.getTable().getSelectedRow(),0).toString());
                        NmPetugasMenerima1.setText(petugas1.getTable().getValueAt(petugas1.getTable().getSelectedRow(),1).toString());
                        KdPetugasMenerima1.requestFocus();
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("RMTransferPasienOk")){
                    if(dokter.getTable().getSelectedRow()!= -1){ 
                        if(pilihan==1){
                            KdDokterOperator.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterOperator.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KdDokterOperator.requestFocus();
                        }else{
                            KdDokterAnastesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterAnastesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KdDokterAnastesi.requestFocus();
                        }
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
        
        dokter1.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("RMTransferPasienOk")){
                    if(dokter1.getTable().getSelectedRow()!= -1){ 
                        if(pilihan==1){
                            KdDokterOperator1.setText(dokter1.getTable().getValueAt(dokter1.getTable().getSelectedRow(),0).toString());
                            NmDokterOperator1.setText(dokter1.getTable().getValueAt(dokter1.getTable().getSelectedRow(),1).toString());
                            KdDokterOperator1.requestFocus();
                        }else{
                            KdDokterAnastesi1.setText(dokter1.getTable().getValueAt(dokter1.getTable().getSelectedRow(),0).toString());
                            NmDokterAnastesi1.setText(dokter1.getTable().getValueAt(dokter1.getTable().getSelectedRow(),1).toString());
                            KdDokterAnastesi1.requestFocus();
                        }
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
        
        ralan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ralan.getTable().getSelectedRow()!= -1){
                    if(pilihan==1){
                        KdRuang.setText(ralan.getTable().getValueAt(ralan.getTable().getSelectedRow(),0).toString());
                        NmRuang.setText(ralan.getTable().getValueAt(ralan.getTable().getSelectedRow(),1).toString());
                        KdRuang.requestFocus();
                    }else{
                        KdRuang1.setText(ralan.getTable().getValueAt(ralan.getTable().getSelectedRow(),0).toString());
                        NmRuang1.setText(ralan.getTable().getValueAt(ralan.getTable().getSelectedRow(),1).toString());
                        KdRuang1.requestFocus();
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
        
        ranap.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ranap.getTable().getSelectedRow()!= -1){
                    if(pilihan==1){
                        KdRuang.setText(ranap.getTable().getValueAt(ranap.getTable().getSelectedRow(),1).toString());
                        NmRuang.setText(ranap.getTable().getValueAt(ranap.getTable().getSelectedRow(),3).toString());
                        KdRuang.requestFocus();
                    }else{
                        KdRuang1.setText(ranap.getTable().getValueAt(ranap.getTable().getSelectedRow(),1).toString());
                        NmRuang1.setText(ranap.getTable().getValueAt(ranap.getTable().getSelectedRow(),3).toString());
                        KdRuang1.requestFocus();
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
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
        LoadHTML3.setEditable(true);
        LoadHTML3.setEditorKit(kit);
        LoadHTML4.setEditable(true);
        LoadHTML4.setEditorKit(kit);
        LoadHTML5.setEditable(true);
        LoadHTML5.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
        LoadHTML3.setDocument(doc);
        LoadHTML4.setDocument(doc);
        LoadHTML5.setDocument(doc);
        
        ChkAccor.setSelected(false);
        isPhoto();
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
        BtnSimpan = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        btnMenyerahkan = new widget.Button();
        btnMenerima = new widget.Button();
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
        jLabel13 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel54 = new widget.Label();
        label13 = new widget.Label();
        TanggalMasuk = new widget.Tanggal();
        jSeparator5 = new javax.swing.JSeparator();
        Rencana = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        KdRuang = new widget.TextBox();
        NmRuang = new widget.TextBox();
        jLabel28 = new widget.Label();
        DiagnosaPre = new widget.TextBox();
        jLabel96 = new widget.Label();
        DiagnosaPost = new widget.TextBox();
        label44 = new widget.Label();
        KdDokterOperator = new widget.TextBox();
        NmDokterOperator = new widget.TextBox();
        BtnDokter2 = new widget.Button();
        label58 = new widget.Label();
        KdDokterAnastesi = new widget.TextBox();
        NmDokterAnastesi = new widget.TextBox();
        BtnDokter3 = new widget.Button();
        BtnRuang2 = new widget.Button();
        BtnRuang3 = new widget.Button();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        label14 = new widget.Label();
        KdPetugasMenyerahkan = new widget.TextBox();
        NmPetugasMenyerahkan = new widget.TextBox();
        BtnDokter = new widget.Button();
        label15 = new widget.Label();
        KdPetugasMenerima = new widget.TextBox();
        NmPetugasMenerima = new widget.TextBox();
        BtnMenerima = new widget.Button();
        label16 = new widget.Label();
        label23 = new widget.Label();
        label24 = new widget.Label();
        IzinOp = new javax.swing.JCheckBox();
        label25 = new widget.Label();
        Lab = new javax.swing.JCheckBox();
        Dpl = new javax.swing.JCheckBox();
        Gds = new javax.swing.JCheckBox();
        BtCt = new javax.swing.JCheckBox();
        Uc = new javax.swing.JCheckBox();
        SpgtSgot = new javax.swing.JCheckBox();
        LabLainnya = new widget.TextBox();
        Rontgen = new javax.swing.JCheckBox();
        RontgenThorax = new javax.swing.JCheckBox();
        RontgenKepala = new javax.swing.JCheckBox();
        RontgenIVP = new javax.swing.JCheckBox();
        RontgenBNO = new javax.swing.JCheckBox();
        BNOLainnya = new widget.TextBox();
        EKG = new javax.swing.JCheckBox();
        TanggalEKG = new widget.Tanggal();
        USG = new javax.swing.JCheckBox();
        USGAbdomen = new javax.swing.JCheckBox();
        USGGinjal = new javax.swing.JCheckBox();
        USGHepar = new javax.swing.JCheckBox();
        USGThorax = new javax.swing.JCheckBox();
        label27 = new widget.Label();
        USGLainnya = new widget.TextBox();
        CTScan = new javax.swing.JCheckBox();
        CTKepala = new javax.swing.JCheckBox();
        CTAbdomen = new javax.swing.JCheckBox();
        CTThorax = new javax.swing.JCheckBox();
        MRI = new javax.swing.JCheckBox();
        label28 = new widget.Label();
        MRILainnya = new widget.TextBox();
        label29 = new widget.Label();
        Antibiotik = new javax.swing.JCheckBox();
        AntibiotikLainnya = new widget.TextBox();
        TanggalAntibiotik = new widget.Tanggal();
        Transfusi = new javax.swing.JCheckBox();
        TransfusiLainnya = new widget.TextBox();
        TanggalTransfusi = new widget.Tanggal();
        label30 = new widget.Label();
        TerapiSebelumLainnya = new widget.TextBox();
        label26 = new widget.Label();
        Kesadaran = new javax.swing.JCheckBox();
        Observasi = new javax.swing.JCheckBox();
        label31 = new widget.Label();
        TD = new widget.TextBox();
        label32 = new widget.Label();
        Nd = new widget.TextBox();
        Sh = new widget.TextBox();
        Puasa = new javax.swing.JCheckBox();
        CmbJamPuasa = new widget.ComboBox();
        CmbMenitPuasa = new widget.ComboBox();
        CmbDetikPuasa = new widget.ComboBox();
        ChkJlnPuasa = new widget.CekBox();
        Klisma = new javax.swing.JCheckBox();
        CmbJamKlisma = new widget.ComboBox();
        CmbMenitKlisma = new widget.ComboBox();
        CmbDetikKlisma = new widget.ComboBox();
        ChkJlnKlisma = new widget.CekBox();
        Cukur = new javax.swing.JCheckBox();
        LukaSebelumOP = new javax.swing.JCheckBox();
        LukaSebelumOPLainnya = new widget.TextBox();
        Mens = new javax.swing.JCheckBox();
        DTPMens = new widget.Tanggal();
        label34 = new widget.Label();
        IUFD = new widget.TextBox();
        label35 = new widget.Label();
        Balon = new widget.TextBox();
        label36 = new widget.Label();
        Vol = new widget.TextBox();
        label37 = new widget.Label();
        Warna = new widget.TextBox();
        label38 = new widget.Label();
        DCLainnya = new widget.TextBox();
        label39 = new widget.Label();
        Alkes = new widget.TextBox();
        label40 = new widget.Label();
        GantiBaju = new javax.swing.JCheckBox();
        GigiPalsu = new javax.swing.JCheckBox();
        BantuDengar = new javax.swing.JCheckBox();
        Perhiasan = new javax.swing.JCheckBox();
        Pengembalian = new javax.swing.JCheckBox();
        DTPSerah = new widget.Tanggal();
        label67 = new widget.Label();
        Status = new widget.TextBox();
        internalFrame4 = new widget.InternalFrame();
        scrollInput1 = new widget.ScrollPane();
        FormInput1 = new widget.PanelBiasa();
        jSeparator15 = new javax.swing.JSeparator();
        TNoRw1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        TNoRM1 = new widget.TextBox();
        jLabel9 = new widget.Label();
        TglLahir1 = new widget.TextBox();
        Jk1 = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel53 = new widget.Label();
        label12 = new widget.Label();
        TanggalMasuk1 = new widget.Tanggal();
        jSeparator2 = new javax.swing.JSeparator();
        Rencana1 = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel22 = new widget.Label();
        jLabel23 = new widget.Label();
        jSeparator4 = new javax.swing.JSeparator();
        KdRuang1 = new widget.TextBox();
        NmRuang1 = new widget.TextBox();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel24 = new widget.Label();
        DiagnosaPre1 = new widget.TextBox();
        jLabel95 = new widget.Label();
        DiagnosaPost1 = new widget.TextBox();
        label33 = new widget.Label();
        KdDokterOperator1 = new widget.TextBox();
        NmDokterOperator1 = new widget.TextBox();
        BtnDokter4 = new widget.Button();
        label41 = new widget.Label();
        KdDokterAnastesi1 = new widget.TextBox();
        NmDokterAnastesi1 = new widget.TextBox();
        BtnDokter5 = new widget.Button();
        label17 = new widget.Label();
        KdPetugasMenyerahkan1 = new widget.TextBox();
        NmPetugasMenyerahkan1 = new widget.TextBox();
        BtnDokter1 = new widget.Button();
        label18 = new widget.Label();
        KdPetugasMenerima1 = new widget.TextBox();
        NmPetugasMenerima1 = new widget.TextBox();
        BtnMenerima1 = new widget.Button();
        label19 = new widget.Label();
        label42 = new widget.Label();
        label43 = new widget.Label();
        LapOp = new javax.swing.JCheckBox();
        LapPA = new javax.swing.JCheckBox();
        SamplePA = new javax.swing.JCheckBox();
        LapAnastesi = new javax.swing.JCheckBox();
        LapTimeOut = new javax.swing.JCheckBox();
        Lab1 = new javax.swing.JCheckBox();
        Dpl1 = new javax.swing.JCheckBox();
        Gds1 = new javax.swing.JCheckBox();
        BtCt1 = new javax.swing.JCheckBox();
        Uc1 = new javax.swing.JCheckBox();
        SpgtSgot1 = new javax.swing.JCheckBox();
        LabLainnya1 = new widget.TextBox();
        Rontgen1 = new javax.swing.JCheckBox();
        RontgenThorax1 = new javax.swing.JCheckBox();
        RontgenKepala1 = new javax.swing.JCheckBox();
        RontgenIVP1 = new javax.swing.JCheckBox();
        RontgenBNO1 = new javax.swing.JCheckBox();
        BNOLainnya1 = new widget.TextBox();
        EKG1 = new javax.swing.JCheckBox();
        TanggalEKG1 = new widget.Tanggal();
        USG1 = new javax.swing.JCheckBox();
        USGAbdomen1 = new javax.swing.JCheckBox();
        USGGinjal1 = new javax.swing.JCheckBox();
        USGHepar1 = new javax.swing.JCheckBox();
        USGThorax1 = new javax.swing.JCheckBox();
        label45 = new widget.Label();
        USGLainnya1 = new widget.TextBox();
        CTScan1 = new javax.swing.JCheckBox();
        CTKepala1 = new javax.swing.JCheckBox();
        CTAbdomen1 = new javax.swing.JCheckBox();
        CTThorax1 = new javax.swing.JCheckBox();
        MRI1 = new javax.swing.JCheckBox();
        label46 = new widget.Label();
        MRILainnya1 = new widget.TextBox();
        label47 = new widget.Label();
        label59 = new widget.Label();
        ObatObatan = new widget.TextBox();
        ResepPostOp = new widget.TextBox();
        label60 = new widget.Label();
        label61 = new widget.Label();
        TransfusiMasuk = new widget.TextBox();
        label48 = new widget.Label();
        TerapiSebelumLainnya1 = new widget.TextBox();
        label49 = new widget.Label();
        Kesadaran1 = new javax.swing.JCheckBox();
        Observasi1 = new javax.swing.JCheckBox();
        label50 = new widget.Label();
        TD1 = new widget.TextBox();
        label51 = new widget.Label();
        Nd1 = new widget.TextBox();
        Sh1 = new widget.TextBox();
        PasangAlat = new javax.swing.JCheckBox();
        Spoeling = new widget.TextBox();
        label52 = new widget.Label();
        IUFD1 = new widget.TextBox();
        label53 = new widget.Label();
        Balon1 = new widget.TextBox();
        label54 = new widget.Label();
        Vol1 = new widget.TextBox();
        label55 = new widget.Label();
        Warna1 = new widget.TextBox();
        label56 = new widget.Label();
        DCLainnya1 = new widget.TextBox();
        label57 = new widget.Label();
        Alkes1 = new widget.TextBox();
        DTPSerah1 = new widget.Tanggal();
        LukaOP = new widget.TextBox();
        label62 = new widget.Label();
        label63 = new widget.Label();
        Drainase = new widget.TextBox();
        label64 = new widget.Label();
        label65 = new widget.Label();
        AlatWarna = new widget.TextBox();
        BtnRuang = new widget.Button();
        BtnRuang1 = new widget.Button();
        label66 = new widget.Label();
        Status1 = new widget.TextBox();
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
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        TTDMenyerahkan = new widget.ScrollPane();
        LoadHTML3 = new widget.editorpane();
        TTDMenerima = new widget.ScrollPane();
        LoadHTML4 = new widget.editorpane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        TTDMenyerahkan1 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        TTDMenerima1 = new widget.ScrollPane();
        LoadHTML5 = new widget.editorpane();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transfer Pasien Kamar Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        btnMenyerahkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/tasksgroup.png"))); // NOI18N
        btnMenyerahkan.setMnemonic('2');
        btnMenyerahkan.setText("Menyerahkan");
        btnMenyerahkan.setToolTipText("Alt+2");
        btnMenyerahkan.setMargin(new java.awt.Insets(1, 7, 1, 7));
        btnMenyerahkan.setName("btnMenyerahkan"); // NOI18N
        btnMenyerahkan.setPreferredSize(new java.awt.Dimension(155, 30));
        btnMenyerahkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenyerahkanActionPerformed(evt);
            }
        });
        panelGlass8.add(btnMenyerahkan);

        btnMenerima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/tasksgroup.png"))); // NOI18N
        btnMenerima.setMnemonic('2');
        btnMenerima.setText("Menerima");
        btnMenerima.setToolTipText("Alt+2");
        btnMenerima.setMargin(new java.awt.Insets(1, 7, 1, 7));
        btnMenerima.setName("btnMenerima"); // NOI18N
        btnMenerima.setPreferredSize(new java.awt.Dimension(155, 30));
        btnMenerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenerimaActionPerformed(evt);
            }
        });
        panelGlass8.add(btnMenerima);

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
        scrollInput.setPreferredSize(new java.awt.Dimension(970, 863));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(970, 863));
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
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        jLabel13.setText("Tgl.Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(580, 10, 60, 23);

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

        jLabel14.setText("No.Rawat :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 10, 70, 23);

        jLabel54.setText("J.K. :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(740, 10, 30, 23);

        label13.setText("Tanggal :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label13);
        label13.setBounds(0, 40, 70, 23);

        TanggalMasuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026 11:25:45" }));
        TanggalMasuk.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalMasuk.setName("TanggalMasuk"); // NOI18N
        TanggalMasuk.setOpaque(false);
        TanggalMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMasukKeyPressed(evt);
            }
        });
        FormInput.add(TanggalMasuk);
        TanggalMasuk.setBounds(74, 40, 130, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 70, 880, 1);

        Rencana.setHighlighter(null);
        Rencana.setName("Rencana"); // NOI18N
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        FormInput.add(Rencana);
        Rencana.setBounds(630, 140, 240, 23);

        jLabel25.setText("Rencana :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(550, 140, 70, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Asal Ruang Rawat / Poliklinik");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(220, 40, 149, 23);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(360, 40, 10, 23);

        KdRuang.setEditable(false);
        KdRuang.setHighlighter(null);
        KdRuang.setName("KdRuang"); // NOI18N
        KdRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRuangKeyPressed(evt);
            }
        });
        FormInput.add(KdRuang);
        KdRuang.setBounds(380, 40, 110, 23);

        NmRuang.setEditable(false);
        NmRuang.setHighlighter(null);
        NmRuang.setName("NmRuang"); // NOI18N
        NmRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmRuangKeyPressed(evt);
            }
        });
        FormInput.add(NmRuang);
        NmRuang.setBounds(500, 40, 200, 23);

        jLabel28.setText("Diagnosa Pre OP :");
        jLabel28.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(20, 80, 100, 23);

        DiagnosaPre.setHighlighter(null);
        DiagnosaPre.setName("DiagnosaPre"); // NOI18N
        DiagnosaPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaPreKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaPre);
        DiagnosaPre.setBounds(130, 80, 730, 23);

        jLabel96.setText("Diagnosa Post OP :");
        jLabel96.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(20, 110, 100, 23);

        DiagnosaPost.setHighlighter(null);
        DiagnosaPost.setName("DiagnosaPost"); // NOI18N
        DiagnosaPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaPostKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaPost);
        DiagnosaPost.setBounds(130, 110, 730, 23);

        label44.setText("dr. Operator / Asisten :");
        label44.setName("label44"); // NOI18N
        label44.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label44);
        label44.setBounds(30, 140, 130, 23);

        KdDokterOperator.setEditable(false);
        KdDokterOperator.setName("KdDokterOperator"); // NOI18N
        KdDokterOperator.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokterOperator);
        KdDokterOperator.setBounds(170, 140, 100, 23);

        NmDokterOperator.setEditable(false);
        NmDokterOperator.setName("NmDokterOperator"); // NOI18N
        NmDokterOperator.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokterOperator);
        NmDokterOperator.setBounds(270, 140, 240, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter2.setMnemonic('2');
        BtnDokter2.setToolTipText("Alt+2");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter2ActionPerformed(evt);
            }
        });
        BtnDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter2KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter2);
        BtnDokter2.setBounds(510, 140, 28, 23);

        label58.setText("dr. Anastesi / Penata :");
        label58.setName("label58"); // NOI18N
        label58.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label58);
        label58.setBounds(30, 170, 130, 23);

        KdDokterAnastesi.setEditable(false);
        KdDokterAnastesi.setName("KdDokterAnastesi"); // NOI18N
        KdDokterAnastesi.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokterAnastesi);
        KdDokterAnastesi.setBounds(170, 170, 100, 23);

        NmDokterAnastesi.setEditable(false);
        NmDokterAnastesi.setName("NmDokterAnastesi"); // NOI18N
        NmDokterAnastesi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokterAnastesi);
        NmDokterAnastesi.setBounds(270, 170, 240, 23);

        BtnDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter3.setMnemonic('2');
        BtnDokter3.setToolTipText("Alt+2");
        BtnDokter3.setName("BtnDokter3"); // NOI18N
        BtnDokter3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter3ActionPerformed(evt);
            }
        });
        BtnDokter3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter3KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter3);
        BtnDokter3.setBounds(510, 170, 28, 23);

        BtnRuang2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnRuang2.setMnemonic('2');
        BtnRuang2.setToolTipText("Alt+2");
        BtnRuang2.setName("BtnRuang2"); // NOI18N
        BtnRuang2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnRuang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRuang2ActionPerformed(evt);
            }
        });
        BtnRuang2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRuang2KeyPressed(evt);
            }
        });
        FormInput.add(BtnRuang2);
        BtnRuang2.setBounds(710, 40, 20, 23);

        BtnRuang3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnRuang3.setMnemonic('2');
        BtnRuang3.setToolTipText("Alt+2");
        BtnRuang3.setName("BtnRuang3"); // NOI18N
        BtnRuang3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnRuang3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRuang3ActionPerformed(evt);
            }
        });
        BtnRuang3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRuang3KeyPressed(evt);
            }
        });
        FormInput.add(BtnRuang3);
        BtnRuang3.setBounds(740, 40, 20, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 201, 880, 0);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(10, 700, 880, 10);

        label14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label14.setText("Petugas / Perawat :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(40, 720, 130, 22);

        KdPetugasMenyerahkan.setEditable(false);
        KdPetugasMenyerahkan.setName("KdPetugasMenyerahkan"); // NOI18N
        KdPetugasMenyerahkan.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugasMenyerahkan);
        KdPetugasMenyerahkan.setBounds(150, 750, 100, 22);

        NmPetugasMenyerahkan.setEditable(false);
        NmPetugasMenyerahkan.setName("NmPetugasMenyerahkan"); // NOI18N
        NmPetugasMenyerahkan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugasMenyerahkan);
        NmPetugasMenyerahkan.setBounds(250, 750, 180, 22);

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
        BtnDokter.setBounds(430, 750, 28, 22);

        label15.setText("Menerima :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(480, 750, 70, 22);

        KdPetugasMenerima.setEditable(false);
        KdPetugasMenerima.setName("KdPetugasMenerima"); // NOI18N
        KdPetugasMenerima.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugasMenerima);
        KdPetugasMenerima.setBounds(550, 750, 100, 22);

        NmPetugasMenerima.setEditable(false);
        NmPetugasMenerima.setName("NmPetugasMenerima"); // NOI18N
        NmPetugasMenerima.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugasMenerima);
        NmPetugasMenerima.setBounds(660, 750, 180, 22);

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
        BtnMenerima.setBounds(840, 750, 28, 22);

        label16.setText("Menyerahkan :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(60, 750, 90, 22);

        label23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label23.setText("SERAH TERIMA DARI RUANGAN KE KAMAR OPERASI");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(30, 210, 350, 30);

        label24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label24.setText("1. Status Pasien / Kelengkapan Pasien");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(40, 240, 330, 22);

        IzinOp.setText("Izin Operasi");
        IzinOp.setName("IzinOp"); // NOI18N
        FormInput.add(IzinOp);
        IzinOp.setBounds(60, 260, 130, 22);

        label25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label25.setText("Pemeriksaan Penunjang :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(60, 280, 140, 22);

        Lab.setText("Laboratorium");
        Lab.setName("Lab"); // NOI18N
        Lab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LabActionPerformed(evt);
            }
        });
        FormInput.add(Lab);
        Lab.setBounds(60, 300, 110, 22);

        Dpl.setText("DPL");
        Dpl.setName("Dpl"); // NOI18N
        Dpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DplActionPerformed(evt);
            }
        });
        FormInput.add(Dpl);
        Dpl.setBounds(170, 300, 50, 22);

        Gds.setText("GDS");
        Gds.setName("Gds"); // NOI18N
        Gds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GdsActionPerformed(evt);
            }
        });
        FormInput.add(Gds);
        Gds.setBounds(230, 300, 50, 22);

        BtCt.setText("BT / CT");
        BtCt.setName("BtCt"); // NOI18N
        BtCt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCtActionPerformed(evt);
            }
        });
        FormInput.add(BtCt);
        BtCt.setBounds(290, 300, 70, 22);

        Uc.setText("UC");
        Uc.setName("Uc"); // NOI18N
        Uc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UcActionPerformed(evt);
            }
        });
        FormInput.add(Uc);
        Uc.setBounds(370, 300, 50, 22);

        SpgtSgot.setText("SGPT/SGOT,   Lainnya :");
        SpgtSgot.setName("SpgtSgot"); // NOI18N
        SpgtSgot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SpgtSgotActionPerformed(evt);
            }
        });
        FormInput.add(SpgtSgot);
        SpgtSgot.setBounds(60, 330, 180, 22);

        LabLainnya.setHighlighter(null);
        LabLainnya.setName("LabLainnya"); // NOI18N
        LabLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(LabLainnya);
        LabLainnya.setBounds(240, 330, 170, 22);

        Rontgen.setText("Rontgen;");
        Rontgen.setName("Rontgen"); // NOI18N
        Rontgen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RontgenActionPerformed(evt);
            }
        });
        FormInput.add(Rontgen);
        Rontgen.setBounds(60, 360, 80, 22);

        RontgenThorax.setText("Thorax");
        RontgenThorax.setName("RontgenThorax"); // NOI18N
        RontgenThorax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RontgenThoraxActionPerformed(evt);
            }
        });
        FormInput.add(RontgenThorax);
        RontgenThorax.setBounds(140, 360, 70, 22);

        RontgenKepala.setText("Kepala");
        RontgenKepala.setName("RontgenKepala"); // NOI18N
        RontgenKepala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RontgenKepalaActionPerformed(evt);
            }
        });
        FormInput.add(RontgenKepala);
        RontgenKepala.setBounds(210, 360, 70, 22);

        RontgenIVP.setText("IVP");
        RontgenIVP.setName("RontgenIVP"); // NOI18N
        RontgenIVP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RontgenIVPActionPerformed(evt);
            }
        });
        FormInput.add(RontgenIVP);
        RontgenIVP.setBounds(280, 360, 60, 22);

        RontgenBNO.setText("BNO,  Lainnya :");
        RontgenBNO.setName("RontgenBNO"); // NOI18N
        RontgenBNO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RontgenBNOActionPerformed(evt);
            }
        });
        FormInput.add(RontgenBNO);
        RontgenBNO.setBounds(60, 390, 110, 22);

        BNOLainnya.setHighlighter(null);
        BNOLainnya.setName("BNOLainnya"); // NOI18N
        BNOLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BNOLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(BNOLainnya);
        BNOLainnya.setBounds(170, 390, 240, 22);

        EKG.setText("EKG ;  Tanggal ");
        EKG.setName("EKG"); // NOI18N
        EKG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EKGActionPerformed(evt);
            }
        });
        FormInput.add(EKG);
        EKG.setBounds(60, 420, 110, 22);

        TanggalEKG.setForeground(new java.awt.Color(50, 70, 50));
        TanggalEKG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026 11:25:45" }));
        TanggalEKG.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalEKG.setName("TanggalEKG"); // NOI18N
        TanggalEKG.setOpaque(false);
        TanggalEKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalEKGKeyPressed(evt);
            }
        });
        FormInput.add(TanggalEKG);
        TanggalEKG.setBounds(180, 420, 150, 22);

        USG.setText("USG;");
        USG.setName("USG"); // NOI18N
        USG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USGActionPerformed(evt);
            }
        });
        FormInput.add(USG);
        USG.setBounds(60, 450, 60, 22);

        USGAbdomen.setText("Abdomen");
        USGAbdomen.setName("USGAbdomen"); // NOI18N
        USGAbdomen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USGAbdomenActionPerformed(evt);
            }
        });
        FormInput.add(USGAbdomen);
        USGAbdomen.setBounds(120, 450, 90, 22);

        USGGinjal.setText("Ginjal");
        USGGinjal.setName("USGGinjal"); // NOI18N
        USGGinjal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USGGinjalActionPerformed(evt);
            }
        });
        FormInput.add(USGGinjal);
        USGGinjal.setBounds(210, 450, 70, 22);

        USGHepar.setText("Hepar");
        USGHepar.setName("USGHepar"); // NOI18N
        USGHepar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USGHeparActionPerformed(evt);
            }
        });
        FormInput.add(USGHepar);
        USGHepar.setBounds(280, 450, 70, 22);

        USGThorax.setText("Thorax");
        USGThorax.setName("USGThorax"); // NOI18N
        USGThorax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USGThoraxActionPerformed(evt);
            }
        });
        FormInput.add(USGThorax);
        USGThorax.setBounds(350, 450, 70, 22);

        label27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label27.setText("Lainnya :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(60, 480, 60, 22);

        USGLainnya.setHighlighter(null);
        USGLainnya.setName("USGLainnya"); // NOI18N
        USGLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                USGLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(USGLainnya);
        USGLainnya.setBounds(110, 480, 300, 22);

        CTScan.setText("CT Scan;");
        CTScan.setName("CTScan"); // NOI18N
        CTScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTScanActionPerformed(evt);
            }
        });
        FormInput.add(CTScan);
        CTScan.setBounds(60, 510, 80, 22);

        CTKepala.setText("Kepala");
        CTKepala.setName("CTKepala"); // NOI18N
        CTKepala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTKepalaActionPerformed(evt);
            }
        });
        FormInput.add(CTKepala);
        CTKepala.setBounds(140, 510, 70, 22);

        CTAbdomen.setText("Abdomen");
        CTAbdomen.setName("CTAbdomen"); // NOI18N
        CTAbdomen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTAbdomenActionPerformed(evt);
            }
        });
        FormInput.add(CTAbdomen);
        CTAbdomen.setBounds(210, 510, 90, 22);

        CTThorax.setText("Thorax");
        CTThorax.setName("CTThorax"); // NOI18N
        CTThorax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTThoraxActionPerformed(evt);
            }
        });
        FormInput.add(CTThorax);
        CTThorax.setBounds(300, 510, 100, 22);

        MRI.setText("MRI  ;");
        MRI.setName("MRI"); // NOI18N
        MRI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRIActionPerformed(evt);
            }
        });
        FormInput.add(MRI);
        MRI.setBounds(60, 530, 70, 22);

        label28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label28.setText("Lainnya :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(60, 550, 60, 22);

        MRILainnya.setHighlighter(null);
        MRILainnya.setName("MRILainnya"); // NOI18N
        MRILainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MRILainnyaKeyPressed(evt);
            }
        });
        FormInput.add(MRILainnya);
        MRILainnya.setBounds(120, 550, 290, 22);

        label29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label29.setText("2. Terapi Yang Diberikan Sebelum Operasi");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(40, 580, 280, 22);

        Antibiotik.setText("Antibiotok; ");
        Antibiotik.setName("Antibiotik"); // NOI18N
        Antibiotik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AntibiotikActionPerformed(evt);
            }
        });
        FormInput.add(Antibiotik);
        Antibiotik.setBounds(60, 610, 100, 22);

        AntibiotikLainnya.setHighlighter(null);
        AntibiotikLainnya.setName("AntibiotikLainnya"); // NOI18N
        AntibiotikLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntibiotikLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(AntibiotikLainnya);
        AntibiotikLainnya.setBounds(160, 610, 150, 22);

        TanggalAntibiotik.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAntibiotik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026 11:25:45" }));
        TanggalAntibiotik.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalAntibiotik.setName("TanggalAntibiotik"); // NOI18N
        TanggalAntibiotik.setOpaque(false);
        TanggalAntibiotik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalAntibiotikActionPerformed(evt);
            }
        });
        TanggalAntibiotik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAntibiotikKeyPressed(evt);
            }
        });
        FormInput.add(TanggalAntibiotik);
        TanggalAntibiotik.setBounds(320, 610, 110, 22);

        Transfusi.setText("Transfusi;");
        Transfusi.setName("Transfusi"); // NOI18N
        Transfusi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransfusiActionPerformed(evt);
            }
        });
        FormInput.add(Transfusi);
        Transfusi.setBounds(60, 640, 100, 22);

        TransfusiLainnya.setHighlighter(null);
        TransfusiLainnya.setName("TransfusiLainnya"); // NOI18N
        TransfusiLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransfusiLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(TransfusiLainnya);
        TransfusiLainnya.setBounds(160, 640, 150, 22);

        TanggalTransfusi.setForeground(new java.awt.Color(50, 70, 50));
        TanggalTransfusi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026 11:25:45" }));
        TanggalTransfusi.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalTransfusi.setName("TanggalTransfusi"); // NOI18N
        TanggalTransfusi.setOpaque(false);
        TanggalTransfusi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalTransfusiActionPerformed(evt);
            }
        });
        TanggalTransfusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalTransfusiKeyPressed(evt);
            }
        });
        FormInput.add(TanggalTransfusi);
        TanggalTransfusi.setBounds(320, 640, 110, 22);

        label30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label30.setText("Lainnya :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(60, 670, 60, 22);

        TerapiSebelumLainnya.setHighlighter(null);
        TerapiSebelumLainnya.setName("TerapiSebelumLainnya"); // NOI18N
        TerapiSebelumLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiSebelumLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(TerapiSebelumLainnya);
        TerapiSebelumLainnya.setBounds(110, 670, 300, 22);

        label26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label26.setText("3. Keadaan Umum Pasien");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(450, 240, 410, 22);

        Kesadaran.setText("Kesadaran");
        Kesadaran.setName("Kesadaran"); // NOI18N
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(470, 260, 110, 22);

        Observasi.setText("Observasi,");
        Observasi.setName("Observasi"); // NOI18N
        Observasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ObservasiActionPerformed(evt);
            }
        });
        FormInput.add(Observasi);
        Observasi.setBounds(470, 280, 90, 22);

        label31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label31.setText("TD :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(570, 280, 30, 22);

        TD.setHighlighter(null);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(600, 280, 70, 22);

        label32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label32.setText("Nd :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(680, 280, 30, 22);

        Nd.setHighlighter(null);
        Nd.setName("Nd"); // NOI18N
        Nd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NdKeyPressed(evt);
            }
        });
        FormInput.add(Nd);
        Nd.setBounds(710, 280, 70, 22);

        Sh.setHighlighter(null);
        Sh.setName("Sh"); // NOI18N
        Sh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ShKeyPressed(evt);
            }
        });
        FormInput.add(Sh);
        Sh.setBounds(820, 280, 80, 22);

        Puasa.setText("Puasa, Mulai jam :");
        Puasa.setName("Puasa"); // NOI18N
        Puasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PuasaActionPerformed(evt);
            }
        });
        FormInput.add(Puasa);
        Puasa.setBounds(470, 310, 130, 22);

        CmbJamPuasa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJamPuasa.setName("CmbJamPuasa"); // NOI18N
        CmbJamPuasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJamPuasaKeyPressed(evt);
            }
        });
        FormInput.add(CmbJamPuasa);
        CmbJamPuasa.setBounds(600, 310, 62, 22);

        CmbMenitPuasa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenitPuasa.setName("CmbMenitPuasa"); // NOI18N
        CmbMenitPuasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenitPuasaKeyPressed(evt);
            }
        });
        FormInput.add(CmbMenitPuasa);
        CmbMenitPuasa.setBounds(670, 310, 62, 22);

        CmbDetikPuasa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetikPuasa.setName("CmbDetikPuasa"); // NOI18N
        CmbDetikPuasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetikPuasaKeyPressed(evt);
            }
        });
        FormInput.add(CmbDetikPuasa);
        CmbDetikPuasa.setBounds(730, 310, 62, 22);

        ChkJlnPuasa.setBorder(null);
        ChkJlnPuasa.setSelected(true);
        ChkJlnPuasa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJlnPuasa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJlnPuasa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJlnPuasa.setName("ChkJlnPuasa"); // NOI18N
        ChkJlnPuasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnPuasaActionPerformed(evt);
            }
        });
        FormInput.add(ChkJlnPuasa);
        ChkJlnPuasa.setBounds(800, 310, 23, 22);

        Klisma.setText("Klisma , Jam :");
        Klisma.setName("Klisma"); // NOI18N
        Klisma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KlismaActionPerformed(evt);
            }
        });
        FormInput.add(Klisma);
        Klisma.setBounds(470, 340, 120, 22);

        CmbJamKlisma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJamKlisma.setName("CmbJamKlisma"); // NOI18N
        CmbJamKlisma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJamKlismaKeyPressed(evt);
            }
        });
        FormInput.add(CmbJamKlisma);
        CmbJamKlisma.setBounds(600, 340, 62, 22);

        CmbMenitKlisma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenitKlisma.setName("CmbMenitKlisma"); // NOI18N
        CmbMenitKlisma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenitKlismaKeyPressed(evt);
            }
        });
        FormInput.add(CmbMenitKlisma);
        CmbMenitKlisma.setBounds(670, 340, 62, 22);

        CmbDetikKlisma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetikKlisma.setName("CmbDetikKlisma"); // NOI18N
        CmbDetikKlisma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetikKlismaKeyPressed(evt);
            }
        });
        FormInput.add(CmbDetikKlisma);
        CmbDetikKlisma.setBounds(730, 340, 62, 22);

        ChkJlnKlisma.setBorder(null);
        ChkJlnKlisma.setSelected(true);
        ChkJlnKlisma.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJlnKlisma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJlnKlisma.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJlnKlisma.setName("ChkJlnKlisma"); // NOI18N
        ChkJlnKlisma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnKlismaActionPerformed(evt);
            }
        });
        FormInput.add(ChkJlnKlisma);
        ChkJlnKlisma.setBounds(800, 340, 23, 22);

        Cukur.setText("Cukur daerah operasi");
        Cukur.setName("Cukur"); // NOI18N
        Cukur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CukurActionPerformed(evt);
            }
        });
        FormInput.add(Cukur);
        Cukur.setBounds(470, 370, 150, 22);

        LukaSebelumOP.setText("Luka sebelum operasi :");
        LukaSebelumOP.setName("LukaSebelumOP"); // NOI18N
        LukaSebelumOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LukaSebelumOPActionPerformed(evt);
            }
        });
        FormInput.add(LukaSebelumOP);
        LukaSebelumOP.setBounds(470, 390, 170, 22);

        LukaSebelumOPLainnya.setHighlighter(null);
        LukaSebelumOPLainnya.setName("LukaSebelumOPLainnya"); // NOI18N
        LukaSebelumOPLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LukaSebelumOPLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(LukaSebelumOPLainnya);
        LukaSebelumOPLainnya.setBounds(640, 390, 260, 22);

        Mens.setText("Menstruasi terakhir :");
        Mens.setName("Mens"); // NOI18N
        Mens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MensActionPerformed(evt);
            }
        });
        FormInput.add(Mens);
        Mens.setBounds(470, 420, 170, 22);

        DTPMens.setForeground(new java.awt.Color(50, 70, 50));
        DTPMens.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026" }));
        DTPMens.setDisplayFormat("dd-MM-yyyy");
        DTPMens.setName("DTPMens"); // NOI18N
        DTPMens.setOpaque(false);
        DTPMens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPMensKeyPressed(evt);
            }
        });
        FormInput.add(DTPMens);
        DTPMens.setBounds(640, 420, 120, 22);

        label34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label34.setText("IUFD:");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(470, 450, 40, 22);

        IUFD.setHighlighter(null);
        IUFD.setName("IUFD"); // NOI18N
        IUFD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IUFDKeyPressed(evt);
            }
        });
        FormInput.add(IUFD);
        IUFD.setBounds(500, 450, 400, 22);

        label35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label35.setText("DC, Balon :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label35);
        label35.setBounds(470, 480, 60, 22);

        Balon.setHighlighter(null);
        Balon.setName("Balon"); // NOI18N
        Balon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BalonKeyPressed(evt);
            }
        });
        FormInput.add(Balon);
        Balon.setBounds(530, 480, 80, 22);

        label36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label36.setText("cc Vol :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label36);
        label36.setBounds(620, 480, 40, 22);

        Vol.setHighlighter(null);
        Vol.setName("Vol"); // NOI18N
        Vol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VolKeyPressed(evt);
            }
        });
        FormInput.add(Vol);
        Vol.setBounds(660, 480, 80, 22);

        label37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label37.setText("cc Warna :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label37);
        label37.setBounds(750, 480, 60, 22);

        Warna.setHighlighter(null);
        Warna.setName("Warna"); // NOI18N
        Warna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaKeyPressed(evt);
            }
        });
        FormInput.add(Warna);
        Warna.setBounds(810, 480, 90, 22);

        label38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label38.setText("Lainnya :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label38);
        label38.setBounds(470, 510, 50, 22);

        DCLainnya.setHighlighter(null);
        DCLainnya.setName("DCLainnya"); // NOI18N
        DCLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DCLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(DCLainnya);
        DCLainnya.setBounds(520, 510, 380, 22);

        label39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label39.setText("4. Alhes Yang Diserahkan di Kamar Operasi");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label39);
        label39.setBounds(450, 550, 410, 22);

        Alkes.setHighlighter(null);
        Alkes.setName("Alkes"); // NOI18N
        Alkes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlkesKeyPressed(evt);
            }
        });
        FormInput.add(Alkes);
        Alkes.setBounds(470, 570, 430, 22);

        label40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label40.setText("5. Kesiapan Pasien");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label40);
        label40.setBounds(450, 610, 410, 22);

        GantiBaju.setText("Ganti Baju Kamar Operasi");
        GantiBaju.setName("GantiBaju"); // NOI18N
        GantiBaju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GantiBajuActionPerformed(evt);
            }
        });
        FormInput.add(GantiBaju);
        GantiBaju.setBounds(470, 630, 180, 22);

        GigiPalsu.setText("Gigi Palsu");
        GigiPalsu.setName("GigiPalsu"); // NOI18N
        GigiPalsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GigiPalsuActionPerformed(evt);
            }
        });
        FormInput.add(GigiPalsu);
        GigiPalsu.setBounds(660, 630, 90, 22);

        BantuDengar.setText("Alat Bantu Dengar");
        BantuDengar.setName("BantuDengar"); // NOI18N
        BantuDengar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BantuDengarActionPerformed(evt);
            }
        });
        FormInput.add(BantuDengar);
        BantuDengar.setBounds(470, 650, 180, 22);

        Perhiasan.setText("Perhiasan");
        Perhiasan.setName("Perhiasan"); // NOI18N
        Perhiasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerhiasanActionPerformed(evt);
            }
        });
        FormInput.add(Perhiasan);
        Perhiasan.setBounds(660, 650, 150, 22);

        Pengembalian.setText("Pengembalian ke Keluarga Dengan Tanda Terima");
        Pengembalian.setName("Pengembalian"); // NOI18N
        Pengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PengembalianActionPerformed(evt);
            }
        });
        FormInput.add(Pengembalian);
        Pengembalian.setBounds(470, 670, 320, 22);

        DTPSerah.setForeground(new java.awt.Color(50, 70, 50));
        DTPSerah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026" }));
        DTPSerah.setDisplayFormat("dd-MM-yyyy");
        DTPSerah.setName("DTPSerah"); // NOI18N
        DTPSerah.setOpaque(false);
        DTPSerah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPSerahKeyPressed(evt);
            }
        });
        FormInput.add(DTPSerah);
        DTPSerah.setBounds(160, 720, 90, 22);

        label67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label67.setText("Sh :");
        label67.setName("label67"); // NOI18N
        label67.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label67);
        label67.setBounds(790, 280, 30, 22);

        Status.setEditable(false);
        Status.setHighlighter(null);
        Status.setName("Status"); // NOI18N
        FormInput.add(Status);
        Status.setBounds(780, 40, 80, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Ruangan ke OK", internalFrame2);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput1.setName("scrollInput1"); // NOI18N
        scrollInput1.setPreferredSize(new java.awt.Dimension(900, 853));

        FormInput1.setBackground(new java.awt.Color(255, 255, 255));
        FormInput1.setBorder(null);
        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(900, 853));
        FormInput1.setLayout(null);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput1.add(jSeparator15);
        jSeparator15.setBounds(0, 861, 880, 0);

        TNoRw1.setEditable(false);
        TNoRw1.setHighlighter(null);
        TNoRw1.setName("TNoRw1"); // NOI18N
        TNoRw1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRw1KeyPressed(evt);
            }
        });
        FormInput1.add(TNoRw1);
        TNoRw1.setBounds(74, 10, 131, 23);

        TPasien1.setEditable(false);
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        FormInput1.add(TPasien1);
        TPasien1.setBounds(309, 10, 260, 23);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        FormInput1.add(TNoRM1);
        TNoRM1.setBounds(207, 10, 100, 23);

        jLabel9.setText("Tgl.Lahir :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput1.add(jLabel9);
        jLabel9.setBounds(580, 10, 60, 23);

        TglLahir1.setEditable(false);
        TglLahir1.setHighlighter(null);
        TglLahir1.setName("TglLahir1"); // NOI18N
        FormInput1.add(TglLahir1);
        TglLahir1.setBounds(644, 10, 80, 23);

        Jk1.setEditable(false);
        Jk1.setHighlighter(null);
        Jk1.setName("Jk1"); // NOI18N
        FormInput1.add(Jk1);
        Jk1.setBounds(774, 10, 80, 23);

        jLabel12.setText("No.Rawat :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput1.add(jLabel12);
        jLabel12.setBounds(0, 10, 70, 23);

        jLabel53.setText("J.K. :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput1.add(jLabel53);
        jLabel53.setBounds(740, 10, 30, 23);

        label12.setText("Tanggal :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label12);
        label12.setBounds(0, 40, 70, 23);

        TanggalMasuk1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMasuk1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026 11:25:45" }));
        TanggalMasuk1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalMasuk1.setName("TanggalMasuk1"); // NOI18N
        TanggalMasuk1.setOpaque(false);
        TanggalMasuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMasuk1KeyPressed(evt);
            }
        });
        FormInput1.add(TanggalMasuk1);
        TanggalMasuk1.setBounds(74, 40, 130, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput1.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 880, 1);

        Rencana1.setEditable(false);
        Rencana1.setHighlighter(null);
        Rencana1.setName("Rencana1"); // NOI18N
        Rencana1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rencana1KeyPressed(evt);
            }
        });
        FormInput1.add(Rencana1);
        Rencana1.setBounds(630, 140, 240, 23);

        jLabel17.setText("Rencana :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput1.add(jLabel17);
        jLabel17.setBounds(550, 140, 70, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Asal Ruang Rawat / Poliklinik");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput1.add(jLabel22);
        jLabel22.setBounds(220, 40, 149, 23);

        jLabel23.setText(":");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput1.add(jLabel23);
        jLabel23.setBounds(360, 40, 10, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput1.add(jSeparator4);
        jSeparator4.setBounds(0, 201, 880, 0);

        KdRuang1.setEditable(false);
        KdRuang1.setHighlighter(null);
        KdRuang1.setName("KdRuang1"); // NOI18N
        KdRuang1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRuang1KeyPressed(evt);
            }
        });
        FormInput1.add(KdRuang1);
        KdRuang1.setBounds(380, 40, 110, 23);

        NmRuang1.setEditable(false);
        NmRuang1.setHighlighter(null);
        NmRuang1.setName("NmRuang1"); // NOI18N
        NmRuang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmRuang1ActionPerformed(evt);
            }
        });
        NmRuang1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmRuang1KeyPressed(evt);
            }
        });
        FormInput1.add(NmRuang1);
        NmRuang1.setBounds(500, 40, 200, 23);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput1.add(jSeparator8);
        jSeparator8.setBounds(10, 730, 880, 3);

        jLabel24.setText("Diagnosa Pre OP :");
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput1.add(jLabel24);
        jLabel24.setBounds(20, 80, 100, 23);

        DiagnosaPre1.setHighlighter(null);
        DiagnosaPre1.setName("DiagnosaPre1"); // NOI18N
        DiagnosaPre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaPre1KeyPressed(evt);
            }
        });
        FormInput1.add(DiagnosaPre1);
        DiagnosaPre1.setBounds(130, 80, 730, 23);

        jLabel95.setText("Diagnosa Post OP :");
        jLabel95.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput1.add(jLabel95);
        jLabel95.setBounds(20, 110, 100, 23);

        DiagnosaPost1.setHighlighter(null);
        DiagnosaPost1.setName("DiagnosaPost1"); // NOI18N
        DiagnosaPost1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaPost1KeyPressed(evt);
            }
        });
        FormInput1.add(DiagnosaPost1);
        DiagnosaPost1.setBounds(130, 110, 730, 23);

        label33.setText("dr. Operator / Asisten :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label33);
        label33.setBounds(30, 140, 130, 23);

        KdDokterOperator1.setEditable(false);
        KdDokterOperator1.setName("KdDokterOperator1"); // NOI18N
        KdDokterOperator1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput1.add(KdDokterOperator1);
        KdDokterOperator1.setBounds(170, 140, 100, 23);

        NmDokterOperator1.setEditable(false);
        NmDokterOperator1.setName("NmDokterOperator1"); // NOI18N
        NmDokterOperator1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput1.add(NmDokterOperator1);
        NmDokterOperator1.setBounds(270, 140, 240, 23);

        BtnDokter4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter4.setMnemonic('2');
        BtnDokter4.setToolTipText("Alt+2");
        BtnDokter4.setName("BtnDokter4"); // NOI18N
        BtnDokter4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter4ActionPerformed(evt);
            }
        });
        BtnDokter4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter4KeyPressed(evt);
            }
        });
        FormInput1.add(BtnDokter4);
        BtnDokter4.setBounds(510, 140, 28, 23);

        label41.setText("dr. Anastesi / Penata :");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label41);
        label41.setBounds(30, 170, 130, 23);

        KdDokterAnastesi1.setEditable(false);
        KdDokterAnastesi1.setName("KdDokterAnastesi1"); // NOI18N
        KdDokterAnastesi1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput1.add(KdDokterAnastesi1);
        KdDokterAnastesi1.setBounds(170, 170, 100, 23);

        NmDokterAnastesi1.setEditable(false);
        NmDokterAnastesi1.setName("NmDokterAnastesi1"); // NOI18N
        NmDokterAnastesi1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput1.add(NmDokterAnastesi1);
        NmDokterAnastesi1.setBounds(270, 170, 240, 23);

        BtnDokter5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter5.setMnemonic('2');
        BtnDokter5.setToolTipText("Alt+2");
        BtnDokter5.setName("BtnDokter5"); // NOI18N
        BtnDokter5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter5ActionPerformed(evt);
            }
        });
        BtnDokter5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter5KeyPressed(evt);
            }
        });
        FormInput1.add(BtnDokter5);
        BtnDokter5.setBounds(510, 170, 28, 23);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label17.setText("Petugas / Perawat :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label17);
        label17.setBounds(40, 740, 130, 22);

        KdPetugasMenyerahkan1.setEditable(false);
        KdPetugasMenyerahkan1.setName("KdPetugasMenyerahkan1"); // NOI18N
        KdPetugasMenyerahkan1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput1.add(KdPetugasMenyerahkan1);
        KdPetugasMenyerahkan1.setBounds(150, 770, 100, 22);

        NmPetugasMenyerahkan1.setEditable(false);
        NmPetugasMenyerahkan1.setName("NmPetugasMenyerahkan1"); // NOI18N
        NmPetugasMenyerahkan1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput1.add(NmPetugasMenyerahkan1);
        NmPetugasMenyerahkan1.setBounds(250, 770, 180, 22);

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
        BtnDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter1KeyPressed(evt);
            }
        });
        FormInput1.add(BtnDokter1);
        BtnDokter1.setBounds(430, 770, 28, 22);

        label18.setText("Menerima :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label18);
        label18.setBounds(480, 770, 70, 22);

        KdPetugasMenerima1.setEditable(false);
        KdPetugasMenerima1.setName("KdPetugasMenerima1"); // NOI18N
        KdPetugasMenerima1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput1.add(KdPetugasMenerima1);
        KdPetugasMenerima1.setBounds(550, 770, 100, 22);

        NmPetugasMenerima1.setEditable(false);
        NmPetugasMenerima1.setName("NmPetugasMenerima1"); // NOI18N
        NmPetugasMenerima1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput1.add(NmPetugasMenerima1);
        NmPetugasMenerima1.setBounds(660, 770, 180, 22);

        BtnMenerima1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMenerima1.setMnemonic('2');
        BtnMenerima1.setToolTipText("Alt+2");
        BtnMenerima1.setName("BtnMenerima1"); // NOI18N
        BtnMenerima1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnMenerima1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenerima1ActionPerformed(evt);
            }
        });
        BtnMenerima1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnMenerima1KeyPressed(evt);
            }
        });
        FormInput1.add(BtnMenerima1);
        BtnMenerima1.setBounds(840, 770, 28, 22);

        label19.setText("Menyerahkan :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label19);
        label19.setBounds(60, 770, 90, 22);

        label42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label42.setText("SERAH TERIMA DARI RUANGAN KE KAMAR OPERASI");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label42);
        label42.setBounds(30, 210, 350, 22);

        label43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label43.setText("1. Status Pasien / Kelengkapan Pasien");
        label43.setName("label43"); // NOI18N
        label43.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label43);
        label43.setBounds(40, 240, 330, 22);

        LapOp.setText("Laporan Operasi");
        LapOp.setName("LapOp"); // NOI18N
        FormInput1.add(LapOp);
        LapOp.setBounds(60, 260, 140, 22);

        LapPA.setText("Laporan PA");
        LapPA.setName("LapPA"); // NOI18N
        FormInput1.add(LapPA);
        LapPA.setBounds(60, 280, 130, 22);

        SamplePA.setText("Sample PA");
        SamplePA.setName("SamplePA"); // NOI18N
        FormInput1.add(SamplePA);
        SamplePA.setBounds(60, 300, 130, 22);

        LapAnastesi.setText("Laporan Anastesi");
        LapAnastesi.setName("LapAnastesi"); // NOI18N
        FormInput1.add(LapAnastesi);
        LapAnastesi.setBounds(200, 260, 170, 22);

        LapTimeOut.setText("Laporan Time Out");
        LapTimeOut.setName("LapTimeOut"); // NOI18N
        FormInput1.add(LapTimeOut);
        LapTimeOut.setBounds(200, 280, 170, 22);

        Lab1.setText("Laboratorium");
        Lab1.setName("Lab1"); // NOI18N
        Lab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Lab1ActionPerformed(evt);
            }
        });
        FormInput1.add(Lab1);
        Lab1.setBounds(60, 330, 110, 22);

        Dpl1.setText("DPL");
        Dpl1.setName("Dpl1"); // NOI18N
        Dpl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dpl1ActionPerformed(evt);
            }
        });
        FormInput1.add(Dpl1);
        Dpl1.setBounds(170, 330, 50, 22);

        Gds1.setText("GDS");
        Gds1.setName("Gds1"); // NOI18N
        Gds1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Gds1ActionPerformed(evt);
            }
        });
        FormInput1.add(Gds1);
        Gds1.setBounds(230, 330, 50, 22);

        BtCt1.setText("BT / CT");
        BtCt1.setName("BtCt1"); // NOI18N
        BtCt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCt1ActionPerformed(evt);
            }
        });
        FormInput1.add(BtCt1);
        BtCt1.setBounds(290, 330, 70, 22);

        Uc1.setText("UC");
        Uc1.setName("Uc1"); // NOI18N
        Uc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Uc1ActionPerformed(evt);
            }
        });
        FormInput1.add(Uc1);
        Uc1.setBounds(370, 330, 50, 22);

        SpgtSgot1.setText("SGPT/SGOT,   Lainnya :");
        SpgtSgot1.setName("SpgtSgot1"); // NOI18N
        SpgtSgot1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SpgtSgot1ActionPerformed(evt);
            }
        });
        FormInput1.add(SpgtSgot1);
        SpgtSgot1.setBounds(60, 360, 180, 22);

        LabLainnya1.setHighlighter(null);
        LabLainnya1.setName("LabLainnya1"); // NOI18N
        LabLainnya1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabLainnya1KeyPressed(evt);
            }
        });
        FormInput1.add(LabLainnya1);
        LabLainnya1.setBounds(240, 360, 170, 22);

        Rontgen1.setText("Rontgen;");
        Rontgen1.setName("Rontgen1"); // NOI18N
        Rontgen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rontgen1ActionPerformed(evt);
            }
        });
        FormInput1.add(Rontgen1);
        Rontgen1.setBounds(60, 390, 80, 22);

        RontgenThorax1.setText("Thorax");
        RontgenThorax1.setName("RontgenThorax1"); // NOI18N
        RontgenThorax1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RontgenThorax1ActionPerformed(evt);
            }
        });
        FormInput1.add(RontgenThorax1);
        RontgenThorax1.setBounds(140, 390, 70, 22);

        RontgenKepala1.setText("Kepala");
        RontgenKepala1.setName("RontgenKepala1"); // NOI18N
        RontgenKepala1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RontgenKepala1ActionPerformed(evt);
            }
        });
        FormInput1.add(RontgenKepala1);
        RontgenKepala1.setBounds(210, 390, 70, 22);

        RontgenIVP1.setText("IVP");
        RontgenIVP1.setName("RontgenIVP1"); // NOI18N
        RontgenIVP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RontgenIVP1ActionPerformed(evt);
            }
        });
        FormInput1.add(RontgenIVP1);
        RontgenIVP1.setBounds(280, 390, 60, 22);

        RontgenBNO1.setText("BNO,  Lainnya :");
        RontgenBNO1.setName("RontgenBNO1"); // NOI18N
        RontgenBNO1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RontgenBNO1ActionPerformed(evt);
            }
        });
        FormInput1.add(RontgenBNO1);
        RontgenBNO1.setBounds(60, 420, 110, 22);

        BNOLainnya1.setHighlighter(null);
        BNOLainnya1.setName("BNOLainnya1"); // NOI18N
        BNOLainnya1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BNOLainnya1KeyPressed(evt);
            }
        });
        FormInput1.add(BNOLainnya1);
        BNOLainnya1.setBounds(170, 420, 240, 22);

        EKG1.setText("EKG ;  Tanggal ");
        EKG1.setName("EKG1"); // NOI18N
        EKG1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EKG1ActionPerformed(evt);
            }
        });
        FormInput1.add(EKG1);
        EKG1.setBounds(60, 450, 110, 22);

        TanggalEKG1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalEKG1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026 11:25:45" }));
        TanggalEKG1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalEKG1.setName("TanggalEKG1"); // NOI18N
        TanggalEKG1.setOpaque(false);
        TanggalEKG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalEKG1KeyPressed(evt);
            }
        });
        FormInput1.add(TanggalEKG1);
        TanggalEKG1.setBounds(180, 450, 150, 22);

        USG1.setText("USG;");
        USG1.setName("USG1"); // NOI18N
        USG1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USG1ActionPerformed(evt);
            }
        });
        FormInput1.add(USG1);
        USG1.setBounds(60, 480, 60, 22);

        USGAbdomen1.setText("Abdomen");
        USGAbdomen1.setName("USGAbdomen1"); // NOI18N
        USGAbdomen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USGAbdomen1ActionPerformed(evt);
            }
        });
        FormInput1.add(USGAbdomen1);
        USGAbdomen1.setBounds(120, 480, 90, 22);

        USGGinjal1.setText("Ginjal");
        USGGinjal1.setName("USGGinjal1"); // NOI18N
        USGGinjal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USGGinjal1ActionPerformed(evt);
            }
        });
        FormInput1.add(USGGinjal1);
        USGGinjal1.setBounds(210, 480, 70, 22);

        USGHepar1.setText("Hepar");
        USGHepar1.setName("USGHepar1"); // NOI18N
        USGHepar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USGHepar1ActionPerformed(evt);
            }
        });
        FormInput1.add(USGHepar1);
        USGHepar1.setBounds(280, 480, 70, 22);

        USGThorax1.setText("Thorax");
        USGThorax1.setName("USGThorax1"); // NOI18N
        USGThorax1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USGThorax1ActionPerformed(evt);
            }
        });
        FormInput1.add(USGThorax1);
        USGThorax1.setBounds(350, 480, 70, 22);

        label45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label45.setText("Lainnya :");
        label45.setName("label45"); // NOI18N
        label45.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label45);
        label45.setBounds(60, 510, 60, 22);

        USGLainnya1.setHighlighter(null);
        USGLainnya1.setName("USGLainnya1"); // NOI18N
        USGLainnya1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                USGLainnya1KeyPressed(evt);
            }
        });
        FormInput1.add(USGLainnya1);
        USGLainnya1.setBounds(110, 510, 300, 22);

        CTScan1.setText("CT Scan;");
        CTScan1.setName("CTScan1"); // NOI18N
        CTScan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTScan1ActionPerformed(evt);
            }
        });
        FormInput1.add(CTScan1);
        CTScan1.setBounds(60, 540, 80, 22);

        CTKepala1.setText("Kepala");
        CTKepala1.setName("CTKepala1"); // NOI18N
        CTKepala1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTKepala1ActionPerformed(evt);
            }
        });
        FormInput1.add(CTKepala1);
        CTKepala1.setBounds(140, 540, 70, 22);

        CTAbdomen1.setText("Abdomen");
        CTAbdomen1.setName("CTAbdomen1"); // NOI18N
        CTAbdomen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTAbdomen1ActionPerformed(evt);
            }
        });
        FormInput1.add(CTAbdomen1);
        CTAbdomen1.setBounds(210, 540, 90, 22);

        CTThorax1.setText("Thorax");
        CTThorax1.setName("CTThorax1"); // NOI18N
        CTThorax1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTThorax1ActionPerformed(evt);
            }
        });
        FormInput1.add(CTThorax1);
        CTThorax1.setBounds(300, 540, 100, 22);

        MRI1.setText("MRI  ;");
        MRI1.setName("MRI1"); // NOI18N
        MRI1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRI1ActionPerformed(evt);
            }
        });
        FormInput1.add(MRI1);
        MRI1.setBounds(60, 560, 70, 22);

        label46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label46.setText("Lainnya :");
        label46.setName("label46"); // NOI18N
        label46.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label46);
        label46.setBounds(60, 590, 60, 22);

        MRILainnya1.setHighlighter(null);
        MRILainnya1.setName("MRILainnya1"); // NOI18N
        MRILainnya1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MRILainnya1KeyPressed(evt);
            }
        });
        FormInput1.add(MRILainnya1);
        MRILainnya1.setBounds(110, 590, 300, 22);

        label47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label47.setText("2. Terapi Yang Diberikan Sebelum Operasi");
        label47.setName("label47"); // NOI18N
        label47.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label47);
        label47.setBounds(40, 630, 280, 22);

        label59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label59.setText("Obat -obatan :");
        label59.setName("label59"); // NOI18N
        label59.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label59);
        label59.setBounds(60, 650, 80, 22);

        ObatObatan.setHighlighter(null);
        ObatObatan.setName("ObatObatan"); // NOI18N
        ObatObatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatObatanKeyPressed(evt);
            }
        });
        FormInput1.add(ObatObatan);
        ObatObatan.setBounds(140, 650, 280, 22);

        ResepPostOp.setHighlighter(null);
        ResepPostOp.setName("ResepPostOp"); // NOI18N
        ResepPostOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResepPostOpActionPerformed(evt);
            }
        });
        ResepPostOp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResepPostOpKeyPressed(evt);
            }
        });
        FormInput1.add(ResepPostOp);
        ResepPostOp.setBounds(170, 680, 250, 22);

        label60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label60.setText("Resep Post Operasi :");
        label60.setName("label60"); // NOI18N
        label60.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label60);
        label60.setBounds(60, 680, 110, 22);

        label61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label61.setText("Transfusi Masuk :");
        label61.setName("label61"); // NOI18N
        label61.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label61);
        label61.setBounds(470, 650, 90, 22);

        TransfusiMasuk.setHighlighter(null);
        TransfusiMasuk.setName("TransfusiMasuk"); // NOI18N
        TransfusiMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransfusiMasukActionPerformed(evt);
            }
        });
        TransfusiMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransfusiMasukKeyPressed(evt);
            }
        });
        FormInput1.add(TransfusiMasuk);
        TransfusiMasuk.setBounds(560, 650, 340, 22);

        label48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label48.setText("Lainnya :");
        label48.setName("label48"); // NOI18N
        label48.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label48);
        label48.setBounds(470, 680, 60, 22);

        TerapiSebelumLainnya1.setHighlighter(null);
        TerapiSebelumLainnya1.setName("TerapiSebelumLainnya1"); // NOI18N
        TerapiSebelumLainnya1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiSebelumLainnya1KeyPressed(evt);
            }
        });
        FormInput1.add(TerapiSebelumLainnya1);
        TerapiSebelumLainnya1.setBounds(530, 680, 370, 22);

        label49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label49.setText("3. Keadaan Umum Pasien");
        label49.setName("label49"); // NOI18N
        label49.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label49);
        label49.setBounds(450, 240, 410, 22);

        Kesadaran1.setText("Kesadaran");
        Kesadaran1.setName("Kesadaran1"); // NOI18N
        FormInput1.add(Kesadaran1);
        Kesadaran1.setBounds(470, 260, 110, 22);

        Observasi1.setText("Observasi,");
        Observasi1.setName("Observasi1"); // NOI18N
        Observasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Observasi1ActionPerformed(evt);
            }
        });
        FormInput1.add(Observasi1);
        Observasi1.setBounds(470, 280, 90, 22);

        label50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label50.setText("TD :");
        label50.setName("label50"); // NOI18N
        label50.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label50);
        label50.setBounds(570, 280, 30, 22);

        TD1.setHighlighter(null);
        TD1.setName("TD1"); // NOI18N
        TD1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TD1KeyPressed(evt);
            }
        });
        FormInput1.add(TD1);
        TD1.setBounds(600, 280, 70, 22);

        label51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label51.setText("Nd :");
        label51.setName("label51"); // NOI18N
        label51.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label51);
        label51.setBounds(680, 280, 30, 22);

        Nd1.setHighlighter(null);
        Nd1.setName("Nd1"); // NOI18N
        Nd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nd1KeyPressed(evt);
            }
        });
        FormInput1.add(Nd1);
        Nd1.setBounds(710, 280, 70, 22);

        Sh1.setHighlighter(null);
        Sh1.setName("Sh1"); // NOI18N
        Sh1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sh1KeyPressed(evt);
            }
        });
        FormInput1.add(Sh1);
        Sh1.setBounds(820, 280, 80, 22);

        PasangAlat.setText("Pemasangan Alat");
        PasangAlat.setName("PasangAlat"); // NOI18N
        PasangAlat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasangAlatActionPerformed(evt);
            }
        });
        FormInput1.add(PasangAlat);
        PasangAlat.setBounds(470, 340, 150, 22);

        Spoeling.setHighlighter(null);
        Spoeling.setName("Spoeling"); // NOI18N
        Spoeling.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpoelingKeyPressed(evt);
            }
        });
        FormInput1.add(Spoeling);
        Spoeling.setBounds(530, 400, 180, 22);

        label52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label52.setText("IUFD:");
        label52.setName("label52"); // NOI18N
        label52.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label52);
        label52.setBounds(470, 430, 40, 22);

        IUFD1.setHighlighter(null);
        IUFD1.setName("IUFD1"); // NOI18N
        IUFD1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IUFD1KeyPressed(evt);
            }
        });
        FormInput1.add(IUFD1);
        IUFD1.setBounds(500, 430, 400, 22);

        label53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label53.setText("DC, Balon :");
        label53.setName("label53"); // NOI18N
        label53.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label53);
        label53.setBounds(470, 460, 60, 22);

        Balon1.setHighlighter(null);
        Balon1.setName("Balon1"); // NOI18N
        Balon1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Balon1KeyPressed(evt);
            }
        });
        FormInput1.add(Balon1);
        Balon1.setBounds(530, 460, 80, 22);

        label54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label54.setText("cc Vol :");
        label54.setName("label54"); // NOI18N
        label54.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label54);
        label54.setBounds(620, 460, 40, 22);

        Vol1.setHighlighter(null);
        Vol1.setName("Vol1"); // NOI18N
        Vol1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Vol1KeyPressed(evt);
            }
        });
        FormInput1.add(Vol1);
        Vol1.setBounds(660, 460, 80, 22);

        label55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label55.setText("cc Warna :");
        label55.setName("label55"); // NOI18N
        label55.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label55);
        label55.setBounds(750, 460, 60, 22);

        Warna1.setHighlighter(null);
        Warna1.setName("Warna1"); // NOI18N
        Warna1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Warna1KeyPressed(evt);
            }
        });
        FormInput1.add(Warna1);
        Warna1.setBounds(810, 460, 90, 22);

        label56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label56.setText("Lainnya :");
        label56.setName("label56"); // NOI18N
        label56.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label56);
        label56.setBounds(470, 490, 50, 22);

        DCLainnya1.setHighlighter(null);
        DCLainnya1.setName("DCLainnya1"); // NOI18N
        DCLainnya1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DCLainnya1KeyPressed(evt);
            }
        });
        FormInput1.add(DCLainnya1);
        DCLainnya1.setBounds(520, 490, 380, 22);

        label57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label57.setText("4. Alhes Yang Diserahkan di Kamar Operasi");
        label57.setName("label57"); // NOI18N
        label57.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label57);
        label57.setBounds(450, 530, 410, 22);

        Alkes1.setHighlighter(null);
        Alkes1.setName("Alkes1"); // NOI18N
        Alkes1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alkes1KeyPressed(evt);
            }
        });
        FormInput1.add(Alkes1);
        Alkes1.setBounds(470, 550, 430, 22);

        DTPSerah1.setForeground(new java.awt.Color(50, 70, 50));
        DTPSerah1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026" }));
        DTPSerah1.setDisplayFormat("dd-MM-yyyy");
        DTPSerah1.setName("DTPSerah1"); // NOI18N
        DTPSerah1.setOpaque(false);
        DTPSerah1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPSerah1KeyPressed(evt);
            }
        });
        FormInput1.add(DTPSerah1);
        DTPSerah1.setBounds(160, 740, 90, 22);

        LukaOP.setHighlighter(null);
        LukaOP.setName("LukaOP"); // NOI18N
        LukaOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LukaOPActionPerformed(evt);
            }
        });
        LukaOP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LukaOPKeyPressed(evt);
            }
        });
        FormInput1.add(LukaOP);
        LukaOP.setBounds(550, 310, 350, 22);

        label62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label62.setText("Luka Operasi :");
        label62.setName("label62"); // NOI18N
        label62.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label62);
        label62.setBounds(470, 310, 80, 22);

        label63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label63.setText("Spoeling :");
        label63.setName("label63"); // NOI18N
        label63.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label63);
        label63.setBounds(470, 400, 60, 22);

        Drainase.setHighlighter(null);
        Drainase.setName("Drainase"); // NOI18N
        Drainase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DrainaseActionPerformed(evt);
            }
        });
        Drainase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DrainaseKeyPressed(evt);
            }
        });
        FormInput1.add(Drainase);
        Drainase.setBounds(550, 370, 350, 22);

        label64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label64.setText("Drainnase :");
        label64.setName("label64"); // NOI18N
        label64.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label64);
        label64.setBounds(470, 370, 80, 22);

        label65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label65.setText("Warna :");
        label65.setName("label65"); // NOI18N
        label65.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label65);
        label65.setBounds(730, 400, 50, 22);

        AlatWarna.setHighlighter(null);
        AlatWarna.setName("AlatWarna"); // NOI18N
        AlatWarna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatWarnaKeyPressed(evt);
            }
        });
        FormInput1.add(AlatWarna);
        AlatWarna.setBounds(780, 400, 120, 22);

        BtnRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnRuang.setMnemonic('2');
        BtnRuang.setToolTipText("Alt+2");
        BtnRuang.setName("BtnRuang"); // NOI18N
        BtnRuang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRuangActionPerformed(evt);
            }
        });
        BtnRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRuangKeyPressed(evt);
            }
        });
        FormInput1.add(BtnRuang);
        BtnRuang.setBounds(710, 40, 20, 23);

        BtnRuang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnRuang1.setMnemonic('2');
        BtnRuang1.setToolTipText("Alt+2");
        BtnRuang1.setName("BtnRuang1"); // NOI18N
        BtnRuang1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnRuang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRuang1ActionPerformed(evt);
            }
        });
        BtnRuang1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRuang1KeyPressed(evt);
            }
        });
        FormInput1.add(BtnRuang1);
        BtnRuang1.setBounds(740, 40, 20, 23);

        label66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label66.setText("Sh :");
        label66.setName("label66"); // NOI18N
        label66.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label66);
        label66.setBounds(790, 280, 30, 22);

        Status1.setEditable(false);
        Status1.setHighlighter(null);
        Status1.setName("Status1"); // NOI18N
        FormInput1.add(Status1);
        Status1.setBounds(780, 40, 80, 23);

        scrollInput1.setViewportView(FormInput1);

        internalFrame4.add(scrollInput1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("RR ke Ruangan", internalFrame4);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-05-2026" }));
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

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(630, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
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

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(null);
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("::[ Serah Terima Dari Ruangan Ke Kamar Operasi ]::"));
        jTabbedPane2.setName("jTabbedPane2"); // NOI18N
        jTabbedPane2.setPreferredSize(new java.awt.Dimension(200, 350));

        TTDMenyerahkan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        TTDMenyerahkan.setName("TTDMenyerahkan"); // NOI18N
        TTDMenyerahkan.setOpaque(true);
        TTDMenyerahkan.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML3.setBorder(null);
        LoadHTML3.setName("LoadHTML3"); // NOI18N
        TTDMenyerahkan.setViewportView(LoadHTML3);

        jTabbedPane2.addTab("TTD Menyerahkan", TTDMenyerahkan);

        TTDMenerima.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        TTDMenerima.setName("TTDMenerima"); // NOI18N
        TTDMenerima.setOpaque(true);
        TTDMenerima.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML4.setBorder(null);
        LoadHTML4.setName("LoadHTML4"); // NOI18N
        TTDMenerima.setViewportView(LoadHTML4);

        jTabbedPane2.addTab("TTD Penerima", TTDMenerima);

        FormPhoto.add(jTabbedPane2, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("::[ Serah Terima Dari Ruang RR Ke Ruangan"));
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(200, 350));

        TTDMenyerahkan1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        TTDMenyerahkan1.setName("TTDMenyerahkan1"); // NOI18N
        TTDMenyerahkan1.setOpaque(true);
        TTDMenyerahkan1.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        TTDMenyerahkan1.setViewportView(LoadHTML2);

        jTabbedPane1.addTab("TTD Menyerahkan", TTDMenyerahkan1);

        TTDMenerima1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        TTDMenerima1.setName("TTDMenerima1"); // NOI18N
        TTDMenerima1.setOpaque(true);
        TTDMenerima1.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML5.setBorder(null);
        LoadHTML5.setName("LoadHTML5"); // NOI18N
        TTDMenerima1.setViewportView(LoadHTML5);

        jTabbedPane1.addTab("TTD Menerima", TTDMenerima1);

        FormPhoto.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Transfer Pasien Ok", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    if (TNoRM.getText().trim().equals("")) {
        Valid.textKosong(TNoRw, "Nama Pasien");
    } else if (NmPetugasMenyerahkan.getText().trim().equals("")) {
        Valid.textKosong(BtnDokter, "Petugas Yang Menyerahkan");
    } else if (NmDokterOperator.getText().trim().equals("")) {
        Valid.textKosong(BtnDokter2, "Dokter Operator");
    } else if (DiagnosaPre.getText().trim().equals("")) {
        Valid.textKosong(DiagnosaPre, "Diagnosa Pre  Operasi");
    } else {
        if (akses.getkode().equals("Admin Utama")) {
            prosesSimpanBerdasarkanTab();
        } else {
            if (akses.getkode().equals(KdPetugasMenerima.getText()) || akses.getkode().equals(KdPetugasMenyerahkan.getText())) {
                prosesSimpanBerdasarkanTab();
            } else {
                JOptionPane.showMessageDialog(null, "Harus salah satu petugas sesuai user login..!!");
            }
        }
    }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void prosesSimpanBerdasarkanTab() {
    switch (TabRawat.getSelectedIndex()) {
        case 0:
            simpan();
            break;
        case 1:
            ganti();
            break;
        default:
            JOptionPane.showMessageDialog(null, "Tab tidak dikenali");
    }
}
    
    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnMenerima,BtnBatal);
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
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString())){
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
        }else if(NmPetugasMenyerahkan1.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menyerahkan");
        }else if(NmPetugasMenerima1.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menerima");
        }else if(NmDokterAnastesi.getText().trim().equals("")){
            Valid.textKosong(BtnDokter3,"Dokter Anastesi");
        }else if(NmDokterOperator.getText().trim().equals("")){
            Valid.textKosong(BtnDokter2,"Dokter Operator");
        }else if(DiagnosaPre.getText().trim().equals("")){
            Valid.textKosong(DiagnosaPre,"Diagnosa Pre Operasi");
        }else if(DiagnosaPost.getText().trim().equals("")){
            Valid.textKosong(DiagnosaPost,"Diagnosa Post Operasi");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if 
                        (akses.getkode().equals(KdPetugasMenyerahkan1.getText()) || akses.getkode().equals(KdPetugasMenerima.getText()) || akses.getkode().equals(KdPetugasMenerima1.getText())) 
                    {
                        ganti();
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
            try {
                isPhoto();
                panggilPhoto();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(1);
                isPhoto();
                panggilPhoto();
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
                    TabRawat.setSelectedIndex(1);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==2){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        pilihan=1;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed

    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnMenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenerimaActionPerformed
        pilihan=2;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnMenerimaActionPerformed

    private void BtnMenerimaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnMenerimaKeyPressed

    }//GEN-LAST:event_BtnMenerimaKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Rawat..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        try {
            String noRawat = TNoRw.getText().trim();
            if(noRawat.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No.Rawat kosong!");
                return;
            }

            // 🔥 CEK ADA DI KAMAR_INAP?
            boolean adaDiKamarInap = Sequel.cariIsi("SELECT COUNT(*) FROM kamar_inap WHERE no_rawat=?", noRawat).equals("1");

            String baseUrl = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB();
            String page;

            if(adaDiKamarInap) {
                // Rawat Inap → epri.php
                page = "/webapps/verified/transferpasienokRI.php?norawat=" + URLEncoder.encode(noRawat, "UTF-8");
            } else {
                // Rawat Jalan → ep.php
                page = "/webapps/verified/transferpasienokRJ.php?norawat=" + URLEncoder.encode(noRawat, "UTF-8");
            }

            String fullUrl = baseUrl + page;
            java.awt.Desktop.getDesktop().browse(new java.net.URI(fullUrl));

        } catch (Exception e) {
            System.out.println("Error open browser: " + e);
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void LabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LabActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LabActionPerformed

    private void DplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DplActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DplActionPerformed

    private void GdsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GdsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GdsActionPerformed

    private void BtCtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtCtActionPerformed

    private void UcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UcActionPerformed

    private void SpgtSgotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SpgtSgotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SpgtSgotActionPerformed

    private void LabLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LabLainnyaKeyPressed

    private void RontgenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RontgenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RontgenActionPerformed

    private void RontgenThoraxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RontgenThoraxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RontgenThoraxActionPerformed

    private void RontgenKepalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RontgenKepalaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RontgenKepalaActionPerformed

    private void RontgenIVPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RontgenIVPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RontgenIVPActionPerformed

    private void RontgenBNOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RontgenBNOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RontgenBNOActionPerformed

    private void BNOLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BNOLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BNOLainnyaKeyPressed

    private void EKGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EKGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EKGActionPerformed

    private void TanggalEKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalEKGKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalEKGKeyPressed

    private void USGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGActionPerformed

    private void USGAbdomenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USGAbdomenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGAbdomenActionPerformed

    private void USGGinjalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USGGinjalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGGinjalActionPerformed

    private void USGHeparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USGHeparActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGHeparActionPerformed

    private void USGThoraxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USGThoraxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGThoraxActionPerformed

    private void USGLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_USGLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGLainnyaKeyPressed

    private void CTScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTScanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTScanActionPerformed

    private void CTKepalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTKepalaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTKepalaActionPerformed

    private void CTAbdomenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTAbdomenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTAbdomenActionPerformed

    private void CTThoraxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTThoraxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTThoraxActionPerformed

    private void MRIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MRIActionPerformed

    private void MRILainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MRILainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MRILainnyaKeyPressed

    private void AntibiotikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AntibiotikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AntibiotikActionPerformed

    private void AntibiotikLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntibiotikLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AntibiotikLainnyaKeyPressed

    private void TanggalAntibiotikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAntibiotikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAntibiotikKeyPressed

    private void TanggalAntibiotikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalAntibiotikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAntibiotikActionPerformed

    private void TransfusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransfusiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TransfusiActionPerformed

    private void TransfusiLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransfusiLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TransfusiLainnyaKeyPressed

    private void TanggalTransfusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalTransfusiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalTransfusiActionPerformed

    private void TanggalTransfusiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalTransfusiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalTransfusiKeyPressed

    private void TerapiSebelumLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiSebelumLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TerapiSebelumLainnyaKeyPressed

    private void ObservasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ObservasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ObservasiActionPerformed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDKeyPressed

    private void NdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NdKeyPressed

    private void ShKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ShKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ShKeyPressed

    private void PuasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PuasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PuasaActionPerformed

    private void CmbJamPuasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJamPuasaKeyPressed

    }//GEN-LAST:event_CmbJamPuasaKeyPressed

    private void CmbMenitPuasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenitPuasaKeyPressed
        Valid.pindah(evt,CmbJamPuasa,CmbDetikPuasa);
    }//GEN-LAST:event_CmbMenitPuasaKeyPressed

    private void CmbDetikPuasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetikPuasaKeyPressed

    }//GEN-LAST:event_CmbDetikPuasaKeyPressed

    private void ChkJlnPuasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnPuasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnPuasaActionPerformed

    private void CukurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CukurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CukurActionPerformed

    private void CmbJamKlismaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJamKlismaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJamKlismaKeyPressed

    private void CmbMenitKlismaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenitKlismaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbMenitKlismaKeyPressed

    private void CmbDetikKlismaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetikKlismaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbDetikKlismaKeyPressed

    private void ChkJlnKlismaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnKlismaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnKlismaActionPerformed

    private void KlismaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KlismaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KlismaActionPerformed

    private void LukaSebelumOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LukaSebelumOPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LukaSebelumOPActionPerformed

    private void LukaSebelumOPLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LukaSebelumOPLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LukaSebelumOPLainnyaKeyPressed

    private void MensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MensActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MensActionPerformed

    private void IUFDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IUFDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IUFDKeyPressed

    private void BalonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BalonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BalonKeyPressed

    private void VolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VolKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_VolKeyPressed

    private void WarnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WarnaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_WarnaKeyPressed

    private void DCLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DCLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DCLainnyaKeyPressed

    private void AlkesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlkesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlkesKeyPressed

    private void GantiBajuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GantiBajuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GantiBajuActionPerformed

    private void GigiPalsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GigiPalsuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GigiPalsuActionPerformed

    private void BantuDengarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BantuDengarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BantuDengarActionPerformed

    private void PerhiasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerhiasanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerhiasanActionPerformed

    private void PengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengembalianActionPerformed

    private void DTPSerahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPSerahKeyPressed
        Valid.pindah(evt,TNoRw,CmbJamPuasa);
    }//GEN-LAST:event_DTPSerahKeyPressed

    private void DTPMensKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPMensKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPMensKeyPressed

    private void TNoRw1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRw1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRw1KeyPressed

    private void TanggalMasuk1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMasuk1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMasuk1KeyPressed

    private void Rencana1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rencana1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rencana1KeyPressed

    private void KdRuang1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRuang1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdRuang1KeyPressed

    private void DiagnosaPre1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaPre1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaPre1KeyPressed

    private void DiagnosaPost1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaPost1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaPost1KeyPressed

    private void BtnDokter4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter4ActionPerformed
        akses.setform("RMTransferPasienOk");
        pilihan=1;
        dokter1.isCek();        
        dokter1.TCari.requestFocus();
        dokter1.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dokter1.setLocationRelativeTo(internalFrame1);
        dokter1.setVisible(true);
    }//GEN-LAST:event_BtnDokter4ActionPerformed

    private void BtnDokter4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter4KeyPressed

    private void BtnDokter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter5ActionPerformed
        akses.setform("RMTransferPasienOk");
        pilihan=2;
        dokter1.isCek();        
        dokter1.TCari.requestFocus();
        dokter1.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dokter1.setLocationRelativeTo(internalFrame1);
        dokter1.setVisible(true);
    }//GEN-LAST:event_BtnDokter5ActionPerformed

    private void BtnDokter5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter5KeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        pilihan=1;
        petugas1.isCek();
        petugas1.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas1.setLocationRelativeTo(internalFrame1);
        petugas1.setAlwaysOnTop(false);
        petugas1.setVisible(true);
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1KeyPressed

    private void BtnMenerima1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenerima1ActionPerformed
        pilihan=2;
        petugas1.isCek();
        petugas1.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas1.setLocationRelativeTo(internalFrame1);
        petugas1.setAlwaysOnTop(false);
        petugas1.setVisible(true);
    }//GEN-LAST:event_BtnMenerima1ActionPerformed

    private void BtnMenerima1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnMenerima1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnMenerima1KeyPressed

    private void Lab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Lab1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Lab1ActionPerformed

    private void Dpl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Dpl1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dpl1ActionPerformed

    private void Gds1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Gds1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Gds1ActionPerformed

    private void BtCt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtCt1ActionPerformed

    private void Uc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Uc1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Uc1ActionPerformed

    private void SpgtSgot1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SpgtSgot1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SpgtSgot1ActionPerformed

    private void LabLainnya1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabLainnya1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LabLainnya1KeyPressed

    private void Rontgen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rontgen1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rontgen1ActionPerformed

    private void RontgenThorax1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RontgenThorax1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RontgenThorax1ActionPerformed

    private void RontgenKepala1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RontgenKepala1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RontgenKepala1ActionPerformed

    private void RontgenIVP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RontgenIVP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RontgenIVP1ActionPerformed

    private void RontgenBNO1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RontgenBNO1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RontgenBNO1ActionPerformed

    private void BNOLainnya1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BNOLainnya1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BNOLainnya1KeyPressed

    private void EKG1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EKG1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EKG1ActionPerformed

    private void TanggalEKG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalEKG1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalEKG1KeyPressed

    private void USG1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USG1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USG1ActionPerformed

    private void USGAbdomen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USGAbdomen1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGAbdomen1ActionPerformed

    private void USGGinjal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USGGinjal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGGinjal1ActionPerformed

    private void USGHepar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USGHepar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGHepar1ActionPerformed

    private void USGThorax1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USGThorax1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGThorax1ActionPerformed

    private void USGLainnya1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_USGLainnya1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGLainnya1KeyPressed

    private void CTScan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTScan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTScan1ActionPerformed

    private void CTKepala1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTKepala1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTKepala1ActionPerformed

    private void CTAbdomen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTAbdomen1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTAbdomen1ActionPerformed

    private void CTThorax1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTThorax1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTThorax1ActionPerformed

    private void MRI1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRI1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MRI1ActionPerformed

    private void MRILainnya1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MRILainnya1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MRILainnya1KeyPressed

    private void ObatObatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatObatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ObatObatanKeyPressed

    private void ResepPostOpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResepPostOpKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResepPostOpKeyPressed

    private void TerapiSebelumLainnya1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiSebelumLainnya1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TerapiSebelumLainnya1KeyPressed

    private void Observasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Observasi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Observasi1ActionPerformed

    private void TD1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TD1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TD1KeyPressed

    private void Nd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nd1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nd1KeyPressed

    private void Sh1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sh1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sh1KeyPressed

    private void PasangAlatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasangAlatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasangAlatActionPerformed

    private void SpoelingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpoelingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SpoelingKeyPressed

    private void IUFD1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IUFD1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IUFD1KeyPressed

    private void Balon1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Balon1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Balon1KeyPressed

    private void Vol1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Vol1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Vol1KeyPressed

    private void Warna1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Warna1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Warna1KeyPressed

    private void DCLainnya1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DCLainnya1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DCLainnya1KeyPressed

    private void Alkes1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alkes1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alkes1KeyPressed

    private void DTPSerah1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPSerah1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPSerah1KeyPressed

    private void ResepPostOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResepPostOpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResepPostOpActionPerformed

    private void TransfusiMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransfusiMasukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TransfusiMasukActionPerformed

    private void TransfusiMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransfusiMasukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TransfusiMasukKeyPressed

    private void LukaOPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LukaOPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LukaOPKeyPressed

    private void LukaOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LukaOPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LukaOPActionPerformed

    private void DrainaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DrainaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DrainaseActionPerformed

    private void DrainaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DrainaseKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DrainaseKeyPressed

    private void AlatWarnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatWarnaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlatWarnaKeyPressed

    private void NmRuang1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmRuang1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmRuang1KeyPressed

    private void BtnRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRuangActionPerformed
        akses.setform("RMTransferPasienOk");
        pilihan=2;
        ralan.isCek();        
        ralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ralan.setLocationRelativeTo(internalFrame1);
        ralan.setVisible(true);
    }//GEN-LAST:event_BtnRuangActionPerformed

    private void BtnRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRuangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRuangKeyPressed

    private void BtnRuang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRuang1ActionPerformed
        akses.setform("RMTransferPasienOk");
        pilihan=2;
        ranap.isCek();        
        ranap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ranap.setLocationRelativeTo(internalFrame1);
        ranap.setVisible(true);
    }//GEN-LAST:event_BtnRuang1ActionPerformed

    private void BtnRuang1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRuang1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRuang1KeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TanggalMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMasukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMasukKeyPressed

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RencanaKeyPressed

    private void KdRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRuangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdRuangKeyPressed

    private void NmRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmRuangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmRuangKeyPressed

    private void DiagnosaPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaPreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaPreKeyPressed

    private void DiagnosaPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaPostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaPostKeyPressed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        akses.setform("RMTransferPasienOk");
        pilihan=1;
        dokter.isCek();        
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter2KeyPressed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        akses.setform("RMTransferPasienOk");
        pilihan=2;
        dokter.isCek();        
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void BtnDokter3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter3KeyPressed

    private void BtnRuang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRuang2ActionPerformed
        akses.setform("RMTransferPasienOk");
        pilihan=1;
        ralan.isCek();        
        ralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ralan.setLocationRelativeTo(internalFrame1);
        ralan.setVisible(true);
    }//GEN-LAST:event_BtnRuang2ActionPerformed

    private void BtnRuang2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRuang2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRuang2KeyPressed

    private void BtnRuang3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRuang3ActionPerformed
        akses.setform("RMTransferPasienOk");
        pilihan=1;
        ranap.load();
        ranap.isCek();
        ranap.emptTeks();
        ranap.tampil();
        ranap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        ranap.setLocationRelativeTo(internalFrame1);
        ranap.setVisible(true);
    }//GEN-LAST:event_BtnRuang3ActionPerformed

    private void BtnRuang3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRuang3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRuang3KeyPressed

    private void NmRuang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmRuang1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmRuang1ActionPerformed

    private void btnMenyerahkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenyerahkanActionPerformed
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
        } else if (NmPetugasMenyerahkan.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Petugas Yang Menyerahkan");
        } else if (NmDokterOperator.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter2, "Dokter Operator");
        } else if (DiagnosaPre.getText().trim().equals("")) {
            Valid.textKosong(DiagnosaPre, "Diagnosa Pre  Operasi");
        } else {
            if (akses.getkode().equals("Admin Utama")) {
                prosesSimpanBerdasarkanTab();
            } else {
                if 
                    (akses.getkode().equals(KdPetugasMenerima.getText()) || akses.getkode().equals(KdPetugasMenyerahkan.getText())) 
                {
                    prosesSimpanBerdasarkanTab();
                } else {
                    JOptionPane.showMessageDialog(null, "Harus salah satu petugas sesuai user login..!!");
                }
            }
        }

    }//GEN-LAST:event_btnMenyerahkanActionPerformed

    private void btnMenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenerimaActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugasMenyerahkan1.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menyerahkan");
        }else if(NmPetugasMenerima1.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menerima");
        }else if(DiagnosaPre.getText().trim().equals("")){
            Valid.textKosong(DiagnosaPre,"Diagnosa Utama");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if 
                        (akses.getkode().equals(KdPetugasMenyerahkan1.getText()) || akses.getkode().equals(KdPetugasMenerima.getText()) || akses.getkode().equals(KdPetugasMenerima1.getText())) 
                    {
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_btnMenerimaActionPerformed

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
    private widget.TextBox AlatWarna;
    private widget.TextBox Alkes;
    private widget.TextBox Alkes1;
    private javax.swing.JCheckBox Antibiotik;
    private widget.TextBox AntibiotikLainnya;
    private widget.TextBox BNOLainnya;
    private widget.TextBox BNOLainnya1;
    private widget.TextBox Balon;
    private widget.TextBox Balon1;
    private javax.swing.JCheckBox BantuDengar;
    private javax.swing.JCheckBox BtCt;
    private javax.swing.JCheckBox BtCt1;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter3;
    private widget.Button BtnDokter4;
    private widget.Button BtnDokter5;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnMenerima;
    private widget.Button BtnMenerima1;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnRuang;
    private widget.Button BtnRuang1;
    private widget.Button BtnRuang2;
    private widget.Button BtnRuang3;
    private widget.Button BtnSimpan;
    private javax.swing.JCheckBox CTAbdomen;
    private javax.swing.JCheckBox CTAbdomen1;
    private javax.swing.JCheckBox CTKepala;
    private javax.swing.JCheckBox CTKepala1;
    private javax.swing.JCheckBox CTScan;
    private javax.swing.JCheckBox CTScan1;
    private javax.swing.JCheckBox CTThorax;
    private javax.swing.JCheckBox CTThorax1;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkJlnKlisma;
    private widget.CekBox ChkJlnPuasa;
    private widget.ComboBox CmbDetikKlisma;
    private widget.ComboBox CmbDetikPuasa;
    private widget.ComboBox CmbJamKlisma;
    private widget.ComboBox CmbJamPuasa;
    private widget.ComboBox CmbMenitKlisma;
    private widget.ComboBox CmbMenitPuasa;
    private javax.swing.JCheckBox Cukur;
    private widget.TextBox DCLainnya;
    private widget.TextBox DCLainnya1;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPMens;
    private widget.Tanggal DTPSerah;
    private widget.Tanggal DTPSerah1;
    private widget.TextBox DiagnosaPost;
    private widget.TextBox DiagnosaPost1;
    private widget.TextBox DiagnosaPre;
    private widget.TextBox DiagnosaPre1;
    private javax.swing.JCheckBox Dpl;
    private javax.swing.JCheckBox Dpl1;
    private widget.TextBox Drainase;
    private javax.swing.JCheckBox EKG;
    private javax.swing.JCheckBox EKG1;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private javax.swing.JCheckBox GantiBaju;
    private javax.swing.JCheckBox Gds;
    private javax.swing.JCheckBox Gds1;
    private javax.swing.JCheckBox GigiPalsu;
    private widget.TextBox IUFD;
    private widget.TextBox IUFD1;
    private javax.swing.JCheckBox IzinOp;
    private widget.TextBox Jk;
    private widget.TextBox Jk1;
    private widget.TextBox KdDokterAnastesi;
    private widget.TextBox KdDokterAnastesi1;
    private widget.TextBox KdDokterOperator;
    private widget.TextBox KdDokterOperator1;
    private widget.TextBox KdPetugasMenerima;
    private widget.TextBox KdPetugasMenerima1;
    private widget.TextBox KdPetugasMenyerahkan;
    private widget.TextBox KdPetugasMenyerahkan1;
    private widget.TextBox KdRuang;
    private widget.TextBox KdRuang1;
    private javax.swing.JCheckBox Kesadaran;
    private javax.swing.JCheckBox Kesadaran1;
    private javax.swing.JCheckBox Klisma;
    private widget.Label LCount;
    private javax.swing.JCheckBox Lab;
    private javax.swing.JCheckBox Lab1;
    private widget.TextBox LabLainnya;
    private widget.TextBox LabLainnya1;
    private javax.swing.JCheckBox LapAnastesi;
    private javax.swing.JCheckBox LapOp;
    private javax.swing.JCheckBox LapPA;
    private javax.swing.JCheckBox LapTimeOut;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.editorpane LoadHTML3;
    private widget.editorpane LoadHTML4;
    private widget.editorpane LoadHTML5;
    private widget.TextBox LukaOP;
    private javax.swing.JCheckBox LukaSebelumOP;
    private widget.TextBox LukaSebelumOPLainnya;
    private javax.swing.JCheckBox MRI;
    private javax.swing.JCheckBox MRI1;
    private widget.TextBox MRILainnya;
    private widget.TextBox MRILainnya1;
    private javax.swing.JCheckBox Mens;
    private widget.TextBox Nd;
    private widget.TextBox Nd1;
    private widget.TextBox NmDokterAnastesi;
    private widget.TextBox NmDokterAnastesi1;
    private widget.TextBox NmDokterOperator;
    private widget.TextBox NmDokterOperator1;
    private widget.TextBox NmPetugasMenerima;
    private widget.TextBox NmPetugasMenerima1;
    private widget.TextBox NmPetugasMenyerahkan;
    private widget.TextBox NmPetugasMenyerahkan1;
    private widget.TextBox NmRuang;
    private widget.TextBox NmRuang1;
    private widget.TextBox ObatObatan;
    private javax.swing.JCheckBox Observasi;
    private javax.swing.JCheckBox Observasi1;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JCheckBox PasangAlat;
    private javax.swing.JCheckBox Pengembalian;
    private javax.swing.JCheckBox Perhiasan;
    private javax.swing.JCheckBox Puasa;
    private widget.TextBox Rencana;
    private widget.TextBox Rencana1;
    private widget.TextBox ResepPostOp;
    private javax.swing.JCheckBox Rontgen;
    private javax.swing.JCheckBox Rontgen1;
    private javax.swing.JCheckBox RontgenBNO;
    private javax.swing.JCheckBox RontgenBNO1;
    private javax.swing.JCheckBox RontgenIVP;
    private javax.swing.JCheckBox RontgenIVP1;
    private javax.swing.JCheckBox RontgenKepala;
    private javax.swing.JCheckBox RontgenKepala1;
    private javax.swing.JCheckBox RontgenThorax;
    private javax.swing.JCheckBox RontgenThorax1;
    private javax.swing.JCheckBox SamplePA;
    private widget.ScrollPane Scroll;
    private widget.TextBox Sh;
    private widget.TextBox Sh1;
    private javax.swing.JCheckBox SpgtSgot;
    private javax.swing.JCheckBox SpgtSgot1;
    private widget.TextBox Spoeling;
    private widget.TextBox Status;
    private widget.TextBox Status1;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TD1;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw1;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private widget.ScrollPane TTDMenerima;
    private widget.ScrollPane TTDMenerima1;
    private widget.ScrollPane TTDMenyerahkan;
    private widget.ScrollPane TTDMenyerahkan1;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalAntibiotik;
    private widget.Tanggal TanggalEKG;
    private widget.Tanggal TanggalEKG1;
    private widget.Tanggal TanggalMasuk;
    private widget.Tanggal TanggalMasuk1;
    private widget.Tanggal TanggalTransfusi;
    private widget.TextBox TerapiSebelumLainnya;
    private widget.TextBox TerapiSebelumLainnya1;
    private widget.TextBox TglLahir;
    private widget.TextBox TglLahir1;
    private javax.swing.JCheckBox Transfusi;
    private widget.TextBox TransfusiLainnya;
    private widget.TextBox TransfusiMasuk;
    private javax.swing.JCheckBox USG;
    private javax.swing.JCheckBox USG1;
    private javax.swing.JCheckBox USGAbdomen;
    private javax.swing.JCheckBox USGAbdomen1;
    private javax.swing.JCheckBox USGGinjal;
    private javax.swing.JCheckBox USGGinjal1;
    private javax.swing.JCheckBox USGHepar;
    private javax.swing.JCheckBox USGHepar1;
    private widget.TextBox USGLainnya;
    private widget.TextBox USGLainnya1;
    private javax.swing.JCheckBox USGThorax;
    private javax.swing.JCheckBox USGThorax1;
    private javax.swing.JCheckBox Uc;
    private javax.swing.JCheckBox Uc1;
    private widget.TextBox Vol;
    private widget.TextBox Vol1;
    private widget.TextBox Warna;
    private widget.TextBox Warna1;
    private widget.Button btnAmbil;
    public widget.Button btnMenerima;
    public widget.Button btnMenyerahkan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
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
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollInput1;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {        
        Valid.tabelKosong(tabMode);
        try{
            String cariValue = TCari.getText().trim();

            ps=koneksi.prepareStatement(
                        "SELECT " +
                            "    b.no_rkm_medis, " +
                            "    c.nm_pasien, " +
                            "    c.tgl_lahir, " +
                            "    c.jk, " +
                            "    a.*, " +
                            "    COALESCE(d.nm_poli, f.nm_bangsal, '-') as nama_ruangan, " +
                            "    COALESCE(p1.nama,'-') as menyerahkan, " +
                            "    COALESCE(p2.nama,'-') as menyerahkan1, " +
                            "    COALESCE(p3.nama,'-') as menerima, " +
                            "    COALESCE(p4.nama,'-') as menerima1, " +
                            "    COALESCE(d1.nm_dokter,'-') as operator, " +
                            "    COALESCE(d2.nm_dokter,'-') as anastesi " +
                            "FROM " +
                            "    transfer_pasien_ok a " +
                            "LEFT JOIN reg_periksa b ON b.no_rawat=a.no_rawat " +
                            "LEFT JOIN pasien c ON c.no_rkm_medis=b.no_rkm_medis " +
                            "LEFT JOIN poliklinik d ON d.kd_poli=a.asal_ruang " +
                            "LEFT JOIN kamar e ON e.kd_kamar=a.asal_ruang " +
                            "LEFT JOIN petugas p1 ON p1.nip=a.nip_menyerahkan " +
                            "LEFT JOIN petugas p2 ON p2.nip=a.nip_menyerahkan1 " +
                            "LEFT JOIN petugas p3 ON p3.nip=a.nip_menerima " +
                            "LEFT JOIN petugas p4 ON p4.nip=a.nip_menerima1 " +
                            "LEFT JOIN dokter d1 ON d1.kd_dokter=a.kd_operator " +
                            "LEFT JOIN dokter d2 ON d2.kd_dokter=a.kd_anastesi " +
                            "LEFT JOIN bangsal_kamar f ON f.kd_bangsal=e.kd_bangsal " +
                        "WHERE a.no_rawat LIKE ? " +
                            "AND a.stts LIKE ? " +
                            "AND (" +
                            "   DATE(a.tanggal_masuk) BETWEEN ? AND ? " +
                            "   OR DATE(a.tanggal_keluar) BETWEEN ? AND ? "+
                            "   )" +
                            "ORDER BY a.tanggal_masuk DESC");

            ps.setString(1,"%"+cariValue+"%");
            ps.setString(2,"%"+Status.getText().trim()+"%");
            
            ps.setString(3,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
            ps.setString(4,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
            ps.setString(5,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
            ps.setString(6,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");

            rs=ps.executeQuery();

            int rowCount = 0;
            while(rs.next()){
                rowCount++;
                tabMode.addRow(new String[]{
                    rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal_masuk"),
                            rs.getString("tanggal_keluar"),rs.getString("asal_ruang"),rs.getString("nama_ruangan"),rs.getString("rencana"),rs.getString("diagnosa_pre"),rs.getString("diagnosa_post"),
                            rs.getString("kd_operator"),rs.getString("operator"),rs.getString("kd_anastesi"),rs.getString("anastesi"),rs.getString("nip_menyerahkan"),
                            rs.getString("menyerahkan"),rs.getString("nip_menerima"),rs.getString("menerima"),rs.getString("nip_menyerahkan1"),
                            rs.getString("menyerahkan1"),rs.getString("nip_menerima1"),rs.getString("menerima1")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        // Date & ComboBox
        TanggalMasuk.setDate(new Date());
        TanggalMasuk1.setDate(new Date());
        TanggalEKG.setDate(new Date());
        TanggalAntibiotik.setDate(new Date());
        TanggalTransfusi.setDate(new Date());
        TanggalEKG1.setDate(new Date());
        DTPMens.setDate(new Date());

        // ComboBox jam/menit reset
        CmbJamPuasa.setSelectedIndex(0);
        CmbMenitPuasa.setSelectedIndex(0);
        CmbDetikPuasa.setSelectedIndex(0);
        CmbJamKlisma.setSelectedIndex(0);
        CmbMenitKlisma.setSelectedIndex(0);
        CmbDetikKlisma.setSelectedIndex(0);

        // TextField kosongkan
        DiagnosaPre.setText("");
        DiagnosaPost.setText("");
        KdDokterOperator.setText("");
        KdDokterAnastesi.setText("");
        NmDokterOperator.setText("");
        NmDokterAnastesi.setText("");
        Rencana.setText("");
        LabLainnya.setText("");
        BNOLainnya.setText("");
        USGLainnya.setText("");
        MRILainnya.setText("");
        AntibiotikLainnya.setText("");
        TransfusiLainnya.setText("");
        TerapiSebelumLainnya.setText("");
        TD.setText("");
        Nd.setText("");
        Sh.setText("");
        IUFD.setText("");
        Balon.setText("");
        Vol.setText("");
        Warna.setText("");
        DCLainnya.setText("");
        Alkes.setText("");
        LukaSebelumOPLainnya.setText("");
        KdPetugasMenyerahkan.setText("");
        KdPetugasMenerima.setText("");
        NmPetugasMenyerahkan.setText("");
        NmPetugasMenerima.setText("");
        LabLainnya1.setText("");
        BNOLainnya1.setText("");
        USGLainnya1.setText("");
        MRILainnya1.setText("");
        ObatObatan.setText("");
        ResepPostOp.setText("");
        TransfusiMasuk.setText("");
        TerapiSebelumLainnya1.setText("");
        TD1.setText("");
        Nd1.setText("");
        Sh1.setText("");
        LukaOP.setText("");
        Drainase.setText("");
        Spoeling.setText("");
        AlatWarna.setText("");
        IUFD1.setText("");
        Balon1.setText("");
        Vol1.setText("");
        Warna1.setText("");
        DCLainnya1.setText("");
        Alkes1.setText("");
        KdPetugasMenyerahkan1.setText("");
        KdPetugasMenerima1.setText("");
        NmPetugasMenyerahkan1.setText("");
        NmPetugasMenerima1.setText("");

        // ✅ CHECKBOX: Semua SET FALSE
        IzinOp.setSelected(false);
        Lab.setSelected(false);
        Dpl.setSelected(false);
        Gds.setSelected(false);
        BtCt.setSelected(false);
        Uc.setSelected(false);
        SpgtSgot.setSelected(false);
        Rontgen.setSelected(false);
        RontgenThorax.setSelected(false);
        RontgenKepala.setSelected(false);
        RontgenIVP.setSelected(false);
        RontgenBNO.setSelected(false);
        EKG.setSelected(false);
        USG.setSelected(false);
        USGAbdomen.setSelected(false);
        USGGinjal.setSelected(false);
        USGHepar.setSelected(false);
        USGThorax.setSelected(false);
        CTScan.setSelected(false);
        CTKepala.setSelected(false);
        CTAbdomen.setSelected(false);
        CTThorax.setSelected(false);
        MRI.setSelected(false);
        Antibiotik.setSelected(false);
        Transfusi.setSelected(false);
        Kesadaran.setSelected(false);
        Observasi.setSelected(false);
        Puasa.setSelected(false);
        Klisma.setSelected(false);
        Cukur.setSelected(false);
        LukaSebelumOP.setSelected(false);
        Mens.setSelected(false);
        GantiBaju.setSelected(false);
        GigiPalsu.setSelected(false);
        BantuDengar.setSelected(false);
        Perhiasan.setSelected(false);
        Pengembalian.setSelected(false);
        LapOp.setSelected(false);
        Lab1.setSelected(false);
        Dpl1.setSelected(false);
        Gds1.setSelected(false);
        BtCt1.setSelected(false);
        Uc1.setSelected(false);
        SpgtSgot1.setSelected(false);
        Rontgen1.setSelected(false);
        RontgenThorax1.setSelected(false);
        RontgenKepala1.setSelected(false);
        RontgenIVP1.setSelected(false);
        RontgenBNO1.setSelected(false);
        EKG1.setSelected(false);
        USG1.setSelected(false);
        USGAbdomen1.setSelected(false);
        USGGinjal1.setSelected(false);
        USGHepar1.setSelected(false);
        USGThorax1.setSelected(false);
        CTScan1.setSelected(false);
        CTKepala1.setSelected(false);
        CTAbdomen1.setSelected(false);
        CTThorax1.setSelected(false);
        MRI1.setSelected(false);
        Kesadaran1.setSelected(false);
        Observasi1.setSelected(false);
        PasangAlat.setSelected(false);
        LapPA.setSelected(false);
        SamplePA.setSelected(false);
        LapAnastesi.setSelected(false);
        LapTimeOut.setSelected(false);

        // Kembali ke Tab 1
//        TabRawat.setSelectedIndex(0);
    }

    private void setTgl2Datetime(com.toedter.calendar.JDateChooser tgl, String nilai) {
        try {
            if (nilai == null || nilai.trim().isEmpty()) return;

            java.text.SimpleDateFormat in = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date dt = in.parse(nilai.trim());
            tgl.setDate(dt);
        } catch (Exception e) {
            System.out.println("setTgl2Datetime error: " + e);
        }
    }
    
    private void getData() {
        if(tbObat.getSelectedRow() != -1) {
            int row = tbObat.getSelectedRow();

            // Header Data (Kolom 0-9)
            TNoRw.setText(tbObat.getValueAt(row,0).toString());                    // No.Rawat
            TNoRM.setText(tbObat.getValueAt(row,1).toString());                    // No.RM  
            TPasien.setText(tbObat.getValueAt(row,2).toString());                  // Nama Pasien
            TglLahir.setText(tbObat.getValueAt(row,3).toString());                 // Tgl.Lahir
            Jk.setText(tbObat.getValueAt(row,4).toString());                       // J.K.
//            TanggalMasuk.getTimestamp(tbObat.getValueAt(row, 5).toString());
//            setTgl2Datetime(TanggalMasuk, tbObat.getValueAt(row, 5).toString());       // Tanggal Masuk OK
            KdRuang.setText(tbObat.getValueAt(row,7).toString());                  // Kd Ruang
            NmRuang.setText(tbObat.getValueAt(row,8).toString());                  // Nm Ruang
            Rencana.setText(tbObat.getValueAt(row,9).toString());                  // Rencana
            
            TNoRw1.setText(tbObat.getValueAt(row,0).toString());                    // No.Rawat
            TNoRM1.setText(tbObat.getValueAt(row,1).toString());                    // No.RM  
            TPasien1.setText(tbObat.getValueAt(row,2).toString());                  // Nama Pasien
            TglLahir1.setText(tbObat.getValueAt(row,3).toString());                 // Tgl.Lahir
            Jk1.setText(tbObat.getValueAt(row,4).toString());                       // J.K.
//            Valid.SetTgl2(TanggalMasuk1,tbObat.getValueAt(row,6).toString());      // Tanggal Masuk Ruangan
            KdRuang1.setText(tbObat.getValueAt(row,7).toString());                  // Kd Ruang
            NmRuang1.setText(tbObat.getValueAt(row,8).toString());                  // Nm Ruang
            Rencana1.setText(tbObat.getValueAt(row,9).toString());                  // Rencana

            // Diagnosa & Dokter (Kolom 10-15)
            DiagnosaPre.setText(tbObat.getValueAt(row,10).toString());             // Diagnosa Pre Op
            DiagnosaPost.setText(tbObat.getValueAt(row,11).toString());            // Diagnosa Post Op
            DiagnosaPre1.setText(tbObat.getValueAt(row,10).toString());             // Diagnosa Pre Op
            DiagnosaPost1.setText(tbObat.getValueAt(row,11).toString());            // Diagnosa Post Op
            KdDokterOperator.setText(tbObat.getValueAt(row,12).toString());         // Kd Dr.Operator
            NmDokterOperator.setText(tbObat.getValueAt(row,13).toString());         // Nm Dr.Operator
            KdDokterAnastesi.setText(tbObat.getValueAt(row,14).toString());        // Kd Dr.Anastesi
            NmDokterAnastesi.setText(tbObat.getValueAt(row,15).toString());        // Nm Dr.Anastesi
            
            KdDokterOperator1.setText(tbObat.getValueAt(row,12).toString());         // Kd Dr.Operator
            NmDokterOperator1.setText(tbObat.getValueAt(row,13).toString());         // Nm Dr.Operator
            KdDokterAnastesi1.setText(tbObat.getValueAt(row,14).toString());        // Kd Dr.Anastesi
            NmDokterAnastesi1.setText(tbObat.getValueAt(row,15).toString());        // Nm Dr.Anastesi

            // Petugas (Kolom 16-23)
            KdPetugasMenyerahkan.setText(tbObat.getValueAt(row,16).toString());    // Kd Pengirim ke OK
            NmPetugasMenyerahkan.setText(tbObat.getValueAt(row,17).toString());    // Pengirim ke OK
            KdPetugasMenerima.setText(tbObat.getValueAt(row,18).toString());       // Kd Penerima di OK
            NmPetugasMenerima.setText(tbObat.getValueAt(row,19).toString());       // Penerima di OK

            // Petugas Ruangan (Kolom 20-23 - jika ada)
            KdPetugasMenyerahkan1.setText(tbObat.getValueAt(row,20).toString());
            NmPetugasMenyerahkan1.setText(tbObat.getValueAt(row,21).toString());
            KdPetugasMenerima1.setText(tbObat.getValueAt(row,22).toString());
            NmPetugasMenerima1.setText(tbObat.getValueAt(row,23).toString());
            
            Valid.SetTgl2(TanggalMasuk,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Valid.SetTgl2(TanggalMasuk1,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            
            try {
                ps = koneksi.prepareStatement(
                    "select * from transfer_pasien_ok " +
                    "where no_rawat = ?"
                );

                try {
                    ps.setString(1, TNoRw.getText().trim());  // asumsi TNoRw = no_rawat
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        // Tanggal
                        TanggalEKG.setDate(rs.getTimestamp("tanggal_ekg"));
                        TanggalAntibiotik.setDate(rs.getTimestamp("tanggal_antibiotik"));
                        TanggalTransfusi.setDate(rs.getTimestamp("tanggal_transfusi"));
                        TanggalEKG1.setDate(rs.getTimestamp("tanggal_ekg1"));
                        DTPMens.setDate(rs.getDate("tanggal_mens"));

                        // String (text field)
                        KdRuang.setText(rs.getString("asal_ruang"));
                        Status.setText(rs.getString("stts"));
                        Status1.setText(rs.getString("stts"));
                        DiagnosaPre.setText(rs.getString("diagnosa_pre"));
                        DiagnosaPost.setText(rs.getString("diagnosa_post"));
                        KdDokterOperator.setText(rs.getString("kd_operator"));
                        KdDokterAnastesi.setText(rs.getString("kd_anastesi"));
                        Rencana.setText(rs.getString("rencana"));
                        // LANJUTAN text field lainnya
                        LabLainnya.setText(rs.getString("lab_lainnya"));
                        BNOLainnya.setText(rs.getString("bno_lainnya"));
                        USGLainnya.setText(rs.getString("usg_lainnya"));
                        MRILainnya.setText(rs.getString("mri_lainnya"));
                        AntibiotikLainnya.setText(rs.getString("antibiotik_lainnya"));
                        TransfusiLainnya.setText(rs.getString("transfusi_lainnya"));
                        TerapiSebelumLainnya.setText(rs.getString("terapi_sebelum_lainnya"));
                        TerapiSebelumLainnya1.setText(rs.getString("terapi_sebelum_lainnya1"));

                        TD.setText(rs.getString("td"));
                        Nd.setText(rs.getString("nd"));
                        Sh.setText(rs.getString("sh"));
                        TD1.setText(rs.getString("td1"));
                        Nd1.setText(rs.getString("nd1"));
                        Sh1.setText(rs.getString("sh1"));

                        IUFD.setText(rs.getString("iufd"));
                        Balon.setText(rs.getString("dc_balon"));
                        Vol.setText(rs.getString("dc_vol"));
                        Warna.setText(rs.getString("dv_warna"));
                        DCLainnya.setText(rs.getString("dc_lainnya"));
                        Alkes.setText(rs.getString("alkes"));

                        LukaSebelumOPLainnya.setText(rs.getString("luka_sebelum_op_lainnya"));

                        LabLainnya1.setText(rs.getString("lab_lainnya1"));
                        BNOLainnya1.setText(rs.getString("bno_lainnya1"));
                        USGLainnya1.setText(rs.getString("usg_lainnya1"));
                        MRILainnya1.setText(rs.getString("mri_lainnya1"));

                        ObatObatan.setText(rs.getString("obat"));
                        ResepPostOp.setText(rs.getString("resep_post"));
                        TransfusiMasuk.setText(rs.getString("Transfusi_masuk"));

                        LukaOP.setText(rs.getString("luka_op"));
                        Drainase.setText(rs.getString("drainese"));
                        Spoeling.setText(rs.getString("spoeling"));
                        AlatWarna.setText(rs.getString("alat_warna"));

                        IUFD1.setText(rs.getString("iufd1"));
                        Balon1.setText(rs.getString("balon1"));
                        Vol1.setText(rs.getString("vol1"));
                        Warna1.setText(rs.getString("warna1"));
                        DCLainnya1.setText(rs.getString("dc_lainnya1"));
                        Alkes1.setText(rs.getString("alkes1"));

                        // Checkbox (1/0 → true/false)
                        IzinOp.setSelected(toBoolean(rs.getString("izin_op")));
                        Lab.setSelected(toBoolean(rs.getString("lab")));
                        Dpl.setSelected(toBoolean(rs.getString("dpl")));
                        Gds.setSelected(toBoolean(rs.getString("gds")));
                        BtCt.setSelected(toBoolean(rs.getString("btct")));
                        Uc.setSelected(toBoolean(rs.getString("uc")));
                        SpgtSgot.setSelected(toBoolean(rs.getString("sgot")));
                        Rontgen.setSelected(toBoolean(rs.getString("rontgen")));
                        RontgenThorax.setSelected(toBoolean(rs.getString("rontgen_thorax")));
                        RontgenKepala.setSelected(toBoolean(rs.getString("rontgen_kepala")));
                        RontgenIVP.setSelected(toBoolean(rs.getString("rontgen_ivp")));
                        RontgenBNO.setSelected(toBoolean(rs.getString("rontgen_bno")));

                        EKG.setSelected(toBoolean(rs.getString("ekg")));
                        USG.setSelected(toBoolean(rs.getString("usg")));
                        USGAbdomen.setSelected(toBoolean(rs.getString("usg_abdomen")));
                        USGGinjal.setSelected(toBoolean(rs.getString("usg_ginjal")));
                        USGHepar.setSelected(toBoolean(rs.getString("usg_haper")));
                        USGThorax.setSelected(toBoolean(rs.getString("usg_thorax")));

                        CTScan.setSelected(toBoolean(rs.getString("ctscan")));
                        CTKepala.setSelected(toBoolean(rs.getString("ct_kepala")));
                        CTAbdomen.setSelected(toBoolean(rs.getString("ct_abdomen")));
                        CTThorax.setSelected(toBoolean(rs.getString("ct_thorax")));

                        MRI.setSelected(toBoolean(rs.getString("mri")));
                        Antibiotik.setSelected(toBoolean(rs.getString("antibiotik")));
                        Transfusi.setSelected(toBoolean(rs.getString("transfusi")));

                        Kesadaran.setSelected(toBoolean(rs.getString("kesadaran")));
                        Observasi.setSelected(toBoolean(rs.getString("observasi")));
                        Puasa.setSelected(toBoolean(rs.getString("puasa")));
                        Klisma.setSelected(toBoolean(rs.getString("klisma")));
                        Cukur.setSelected(toBoolean(rs.getString("cukur")));
                        LukaSebelumOP.setSelected(toBoolean(rs.getString("luka_sebelum_op")));
                        Mens.setSelected(toBoolean(rs.getString("mens")));
                        GantiBaju.setSelected(toBoolean(rs.getString("ganti_baju")));
                        GigiPalsu.setSelected(toBoolean(rs.getString("gigi_palsu")));
                        BantuDengar.setSelected(toBoolean(rs.getString("alat_bantu_dengar")));
                        Perhiasan.setSelected(toBoolean(rs.getString("perhiasan")));
                        Pengembalian.setSelected(toBoolean(rs.getString("pengembalian")));

                        LapOp.setSelected(toBoolean(rs.getString("laporan_op")));
                        Lab1.setSelected(toBoolean(rs.getString("lab1")));
                        Dpl1.setSelected(toBoolean(rs.getString("dpl1")));
                        Gds1.setSelected(toBoolean(rs.getString("gds1")));
                        BtCt1.setSelected(toBoolean(rs.getString("btct1")));
                        Uc1.setSelected(toBoolean(rs.getString("uc1")));
                        SpgtSgot1.setSelected(toBoolean(rs.getString("sgot1")));
                        Rontgen1.setSelected(toBoolean(rs.getString("rontgen1")));
                        RontgenThorax1.setSelected(toBoolean(rs.getString("rontgen_thorax1")));
                        RontgenKepala1.setSelected(toBoolean(rs.getString("rontgen_kepala1")));
                        RontgenIVP1.setSelected(toBoolean(rs.getString("rontgen_ivp1")));
                        RontgenBNO1.setSelected(toBoolean(rs.getString("rontgen_bno1")));

                        EKG1.setSelected(toBoolean(rs.getString("ekg1")));
                        USG1.setSelected(toBoolean(rs.getString("usg1")));
                        USGAbdomen1.setSelected(toBoolean(rs.getString("usg_abdomen1")));
                        USGGinjal1.setSelected(toBoolean(rs.getString("usg_ginjal1")));
                        USGHepar1.setSelected(toBoolean(rs.getString("usg_haper1")));
                        USGThorax1.setSelected(toBoolean(rs.getString("usg_thorax1")));

                        CTScan1.setSelected(toBoolean(rs.getString("ctscan1")));
                        CTKepala1.setSelected(toBoolean(rs.getString("ct_kepala1")));
                        CTAbdomen1.setSelected(toBoolean(rs.getString("ct_abdomen1")));
                        CTThorax1.setSelected(toBoolean(rs.getString("ct_thorax1")));

                        MRI1.setSelected(toBoolean(rs.getString("mri1")));
                        Kesadaran1.setSelected(toBoolean(rs.getString("kesadaran1")));
                        Observasi1.setSelected(toBoolean(rs.getString("observasi1")));

                        PasangAlat.setSelected(toBoolean(rs.getString("pasang_alat")));
                        LapPA.setSelected(toBoolean(rs.getString("laporan_pa")));
                        SamplePA.setSelected(toBoolean(rs.getString("sample_pa")));
                        LapAnastesi.setSelected(toBoolean(rs.getString("laporan_anastesi")));
                        LapTimeOut.setSelected(toBoolean(rs.getString("laporan_to")));
                        
                        //Jam
                        ChkJlnPuasa.setSelected(false);
                        ChkJlnKlisma.setSelected(false);
                        String jamPuasa = rs.getTime("jam_puasa").toString();
                        String jamKlisma = rs.getTime("jam_klisma").toString();

                        CmbJamPuasa.setSelectedItem(jamPuasa.substring(0, 2));
                        CmbMenitPuasa.setSelectedItem(jamPuasa.substring(3, 5));
                        CmbDetikPuasa.setSelectedItem(jamPuasa.substring(6, 8));
                        CmbJamKlisma.setSelectedItem(jamKlisma.substring(0, 2));
                        CmbMenitKlisma.setSelectedItem(jamKlisma.substring(3, 5));
                        CmbDetikKlisma.setSelectedItem(jamKlisma.substring(6, 8));

                    } else {
                        // Jika tidak ada data, reset form
                        emptTeks();
                    }
                } catch (Exception e) {
                    System.out.println("Notif loadTransferPasienOK : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif loadTransferPasienOK : " + e);
            }
                isPhoto();
                panggilPhoto();
                cekDanAturTabRawat(TNoRw.getText().trim());
        
            TNoRw.requestFocus();

        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,reg_periksa.status_lanjut,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
                    TNoRM1.setText(rs.getString("no_rkm_medis"));
//                    DTPCari2.setDate(rs.getDate("tgl_registrasi"));
                    TPasien1.setText(rs.getString("nm_pasien"));
                    Jk1.setText(rs.getString("jk"));
                    TglLahir1.setText(rs.getString("tgl_lahir"));
                    Status.setText(rs.getString("status_lanjut"));
                    Status1.setText(rs.getString("status_lanjut"));
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
        
        try {
            ps=koneksi.prepareStatement(
                    "SELECT " +
                    "    CASE " +
                    "        WHEN b.no_rawat IS NOT NULL THEN b.kd_kamar " +
                    "        ELSE a.kd_poli " +
                    "    END AS kolom1, " +
                    "    CASE " +
                    "        WHEN b.no_rawat IS NOT NULL THEN e.nm_bangsal " +
                    "        ELSE c.nm_poli " +
                    "    END AS kolom2 " +
                    "FROM reg_periksa a " +
                    "    LEFT JOIN kamar_inap b ON b.no_rawat = a.no_rawat " +
                    "    INNER JOIN poliklinik c ON c.kd_poli = a.kd_poli " +
                    "    LEFT JOIN kamar d ON d.kd_kamar = b.kd_kamar " +
                    "    LEFT JOIN bangsal_kamar e ON e.kd_bangsal = d.kd_bangsal " +
                    "WHERE a.no_rawat =?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    KdRuang.setText(rs.getString("kolom1"));
                    NmRuang.setText(rs.getString("kolom2"));
                    KdRuang1.setText(rs.getString("kolom1"));
                    NmRuang1.setText(rs.getString("kolom2"));
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
        TNoRw1.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
        cekDanAturTabRawat(norwt);
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnHapus.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnEdit.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnPrint.setEnabled(akses.gettransfer_pasien_antar_ruang());
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(2);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from transfer_pasien_ok where no_rawat=? and tanggal_masuk=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(2);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
        
    }

    private void ganti() {
        if(Sequel.mengedittf("transfer_pasien_ok","no_rawat=? and tanggal_masuk=?","no_rawat=?,tanggal_masuk=?,tanggal_keluar=?,asal_ruang=?,stts=?,diagnosa_pre=?,diagnosa_post=?,kd_operator=?,kd_anastesi=?,rencana=?,\n" +
                "izin_op=?,lab=?,dpl=?,gds=?,btct=?,uc=?,sgot=?,lab_lainnya=?,rontgen=?,rontgen_thorax=?," +
                "rontgen_kepala=?,rontgen_ivp=?,rontgen_bno=?,bno_lainnya=?,ekg=?,tanggal_ekg=?,usg=?,usg_abdomen=?,usg_ginjal=?,usg_haper=?," +
                "usg_thorax=?,usg_lainnya=?,ctscan=?,ct_kepala=?,ct_abdomen=?,ct_thorax=?,mri=?,mri_lainnya=?,antibiotik=?,antibiotik_lainnya=?," +
                "tanggal_antibiotik=?,transfusi=?,transfusi_lainnya=?,tanggal_transfusi=?,terapi_sebelum_lainnya=?,kesadaran=?,observasi=?,td=?,nd=?,sh=?," +
                "puasa=?,jam_puasa=?,klisma=?,jam_klisma=?,cukur=?,luka_sebelum_op=?,luka_sebelum_op_lainnya=?,mens=?,tanggal_mens=?,iufd=?," +
                "dc_balon=?,dc_vol=?,dv_warna=?,dc_lainnya=?,alkes=?,ganti_baju=?,gigi_palsu=?,alat_bantu_dengar=?,perhiasan=?,pengembalian=?," +
                "nip_menyerahkan=?,nip_menerima=?,laporan_op=?,lab1=?,dpl1=?,gds1=?,btct1=?,uc1=?,sgot1=?,lab_lainnya1=?," +
                "rontgen1=?,rontgen_thorax1=?,rontgen_kepala1=?,rontgen_ivp1=?,rontgen_bno1=?,bno_lainnya1=?,ekg1=?,tanggal_ekg1=?,usg1=?,usg_abdomen1=?," +
                "usg_ginjal1=?,usg_haper1=?,usg_thorax1=?,usg_lainnya1=?,ctscan1=?,ct_kepala1=?,ct_abdomen1=?,ct_thorax1=?,mri1=?,mri_lainnya1=?," +
                "obat=?,resep_post=?,Transfusi_masuk=?,terapi_sebelum_lainnya1=?,kesadaran1=?,observasi1=?,td1=?,nd1=?,sh1=?,luka_op=?," +
                "pasang_alat=?,drainese=?,spoeling=?,alat_warna=?,iufd1=?,balon1=?,vol1=?,warna1=?,dc_lainnya1=?,alkes1=?," +
                "nip_menyerahkan1=?,nip_menerima1=?,laporan_pa=?,sample_pa=?,laporan_anastesi=?,laporan_to=?,ttd_menyerahkan1=?,ttd_menerima1=?",130,new String[]{
    
                TNoRw.getText(),tglJam(TanggalMasuk.getDate()),tglJam(TanggalMasuk1.getDate()),KdRuang.getText(),Status.getText(),DiagnosaPre.getText(),DiagnosaPost.getText(),KdDokterOperator.getText(),KdDokterAnastesi.getText(),Rencana.getText(), 
                IzinOp.isSelected() ? "true" : "false",Lab.isSelected() ? "true" : "false",Dpl.isSelected() ? "true" : "false",Gds.isSelected() ? "true" : "false",BtCt.isSelected() ? "true" : "false",Uc.isSelected() ? "true" : "false",SpgtSgot.isSelected() ? "true" : "false",LabLainnya.getText(),Rontgen.isSelected() ? "true" : "false",RontgenThorax.isSelected() ? "true" : "false"
                ,RontgenKepala.isSelected() ? "true" : "false",RontgenIVP.isSelected() ? "true" : "false",RontgenBNO.isSelected() ? "true" : "false",BNOLainnya.getText(),EKG.isSelected() ? "true" : "false",tglJam(TanggalEKG.getDate()),USG.isSelected() ? "true" : "false",USGAbdomen.isSelected() ? "true" : "false",USGGinjal.isSelected() ? "true" : "false",USGHepar.isSelected() ? "true" : "false"
                ,USGThorax.isSelected() ? "true" : "false",USGLainnya.getText(),CTScan.isSelected() ? "true" : "false",CTKepala.isSelected() ? "true" : "false",CTAbdomen.isSelected() ? "true" : "false",CTThorax.isSelected() ? "true" : "false",MRI.isSelected() ? "true" : "false",MRILainnya.getText(),Antibiotik.isSelected() ? "true" : "false",AntibiotikLainnya.getText()
                ,tglJam(TanggalAntibiotik.getDate()),Transfusi.isSelected() ? "true" : "false",TransfusiLainnya.getText(),tglJam(TanggalTransfusi.getDate()),TerapiSebelumLainnya.getText(),Kesadaran.isSelected() ? "true" : "false",Observasi.isSelected() ? "true" : "false",TD.getText(),Nd.getText(),Sh.getText()
                ,Puasa.isSelected() ? "true" : "false",CmbJamPuasa.getSelectedItem()+":"+CmbMenitPuasa.getSelectedItem()+":"+CmbDetikPuasa.getSelectedItem(),Klisma.isSelected() ? "true" : "false",CmbJamKlisma.getSelectedItem()+":"+CmbMenitKlisma.getSelectedItem()+":"+CmbDetikKlisma.getSelectedItem(),Cukur.isSelected() ? "true" : "false",LukaSebelumOP.isSelected() ? "true" : "false",LukaSebelumOPLainnya.getText(),Mens.isSelected() ? "true" : "false",Valid.SetTgl(DTPMens.getSelectedItem()+""),IUFD.getText()
                ,Balon.getText(),Vol.getText(),Warna.getText(),DCLainnya.getText(),Alkes.getText(),GantiBaju.isSelected() ? "true" : "false",GigiPalsu.isSelected() ? "true" : "false",BantuDengar.isSelected() ? "true" : "false",Perhiasan.isSelected() ? "true" : "false",Pengembalian.isSelected() ? "true" : "false"
                ,KdPetugasMenyerahkan.getText(),KdPetugasMenerima.getText(),LapOp.isSelected() ? "true" : "false",Lab1.isSelected() ? "true" : "false",Dpl1.isSelected() ? "true" : "false",Gds1.isSelected() ? "true" : "false",BtCt1.isSelected() ? "true" : "false",Uc1.isSelected() ? "true" : "false",SpgtSgot1.isSelected() ? "true" : "false",LabLainnya1.getText()
                ,Rontgen1.isSelected() ? "true" : "false",RontgenThorax1.isSelected() ? "true" : "false",RontgenKepala1.isSelected() ? "true" : "false",RontgenIVP1.isSelected() ? "true" : "false",RontgenBNO1.isSelected() ? "true" : "false",BNOLainnya1.getText(),EKG1.isSelected() ? "true" : "false",tglJam(TanggalEKG1.getDate()),USG1.isSelected() ? "true" : "false",USGAbdomen1.isSelected() ? "true" : "false"
                ,USGGinjal1.isSelected() ? "true" : "false",USGHepar1.isSelected() ? "true" : "false",USGThorax1.isSelected() ? "true" : "false",USGLainnya1.getText(),CTScan1.isSelected() ? "true" : "false",CTKepala1.isSelected() ? "true" : "false",CTAbdomen1.isSelected() ? "true" : "false",CTThorax1.isSelected() ? "true" : "false",MRI1.isSelected() ? "true" : "false",MRILainnya1.getText()
                ,ObatObatan.getText(),ResepPostOp.getText(),TransfusiMasuk.getText(),TerapiSebelumLainnya1.getText(),Kesadaran1.isSelected() ? "true" : "false",Observasi1.isSelected() ? "true" : "false",TD1.getText(),Nd1.getText(),Sh1.getText(),LukaOP.getText()
                ,PasangAlat.isSelected() ? "true" : "false",Drainase.getText(),Spoeling.getText(),AlatWarna.getText(),IUFD1.getText(),Balon1.getText(),Vol1.getText(),Warna1.getText(),DCLainnya1.getText(),Alkes1.getText()
                ,KdPetugasMenyerahkan1.getText(),KdPetugasMenerima1.getText(),LapPA.isSelected() ? "true" : "false",SamplePA.isSelected() ? "true" : "false",LapAnastesi.isSelected() ? "true" : "false",LapTimeOut.isSelected() ? "true" : "false","/uploads/transferpasienok/"+KdPetugasMenyerahkan1.getText()+".png","/uploads/transferpasienok/"+KdPetugasMenerima1.getText()+".png"
                ,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
            })==true){
                emptTeks();
                tampil();
                TabRawat.setSelectedIndex(2);
        }
    }
    
    private String tglJam(java.util.Date nilai) {
        if (nilai == null) return "";
        java.text.SimpleDateFormat out = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return out.format(nilai);
    }
    
    private void simpan() {
        if(Sequel.menyimpantf("transfer_pasien_ok","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat & Tanggal Masuk",130,new String[]{
                TNoRw.getText(),tglJam(TanggalMasuk.getDate()),tglJam(TanggalMasuk1.getDate()),KdRuang.getText(),Status.getText(),DiagnosaPre.getText(),DiagnosaPost.getText(),KdDokterOperator.getText(),KdDokterAnastesi.getText(),Rencana.getText(), 
                IzinOp.isSelected() ? "true" : "false",Lab.isSelected() ? "true" : "false",Dpl.isSelected() ? "true" : "false",Gds.isSelected() ? "true" : "false",BtCt.isSelected() ? "true" : "false",Uc.isSelected() ? "true" : "false",SpgtSgot.isSelected() ? "true" : "false",LabLainnya.getText(),Rontgen.isSelected() ? "true" : "false",RontgenThorax.isSelected() ? "true" : "false"
                ,RontgenKepala.isSelected() ? "true" : "false",RontgenIVP.isSelected() ? "true" : "false",RontgenBNO.isSelected() ? "true" : "false",BNOLainnya.getText(),EKG.isSelected() ? "true" : "false",tglJam(TanggalEKG.getDate()),USG.isSelected() ? "true" : "false",USGAbdomen.isSelected() ? "true" : "false",USGGinjal.isSelected() ? "true" : "false",USGHepar.isSelected() ? "true" : "false"
                ,USGThorax.isSelected() ? "true" : "false",USGLainnya.getText(),CTScan.isSelected() ? "true" : "false",CTKepala.isSelected() ? "true" : "false",CTAbdomen.isSelected() ? "true" : "false",CTThorax.isSelected() ? "true" : "false",MRI.isSelected() ? "true" : "false",MRILainnya.getText(),Antibiotik.isSelected() ? "true" : "false",AntibiotikLainnya.getText()
                ,tglJam(TanggalAntibiotik.getDate()),Transfusi.isSelected() ? "true" : "false",TransfusiLainnya.getText(),tglJam(TanggalTransfusi.getDate()),TerapiSebelumLainnya.getText(),Kesadaran.isSelected() ? "true" : "false",Observasi.isSelected() ? "true" : "false",TD.getText(),Nd.getText(),Sh.getText()
                ,Puasa.isSelected() ? "true" : "false",CmbJamPuasa.getSelectedItem()+":"+CmbMenitPuasa.getSelectedItem()+":"+CmbDetikPuasa.getSelectedItem(),Klisma.isSelected() ? "true" : "false",CmbJamKlisma.getSelectedItem()+":"+CmbMenitKlisma.getSelectedItem()+":"+CmbDetikKlisma.getSelectedItem(),Cukur.isSelected() ? "true" : "false",LukaSebelumOP.isSelected() ? "true" : "false",LukaSebelumOPLainnya.getText(),Mens.isSelected() ? "true" : "false",Valid.SetTgl(DTPMens.getSelectedItem()+""),IUFD.getText()
                ,Balon.getText(),Vol.getText(),Warna.getText(),DCLainnya.getText(),Alkes.getText(),GantiBaju.isSelected() ? "true" : "false",GigiPalsu.isSelected() ? "true" : "false",BantuDengar.isSelected() ? "true" : "false",Perhiasan.isSelected() ? "true" : "false",Pengembalian.isSelected() ? "true" : "false"
                ,KdPetugasMenyerahkan.getText(),KdPetugasMenerima.getText(),LapOp.isSelected() ? "true" : "false",Lab1.isSelected() ? "true" : "false",Dpl1.isSelected() ? "true" : "false",Gds1.isSelected() ? "true" : "false",BtCt1.isSelected() ? "true" : "false",Uc1.isSelected() ? "true" : "false",SpgtSgot1.isSelected() ? "true" : "false",LabLainnya1.getText()
                ,Rontgen1.isSelected() ? "true" : "false",RontgenThorax1.isSelected() ? "true" : "false",RontgenKepala1.isSelected() ? "true" : "false",RontgenIVP1.isSelected() ? "true" : "false",RontgenBNO1.isSelected() ? "true" : "false",BNOLainnya1.getText(),EKG1.isSelected() ? "true" : "false",tglJam(TanggalEKG1.getDate()),USG1.isSelected() ? "true" : "false",USGAbdomen1.isSelected() ? "true" : "false"
                ,USGGinjal1.isSelected() ? "true" : "false",USGHepar1.isSelected() ? "true" : "false",USGThorax1.isSelected() ? "true" : "false",USGLainnya1.getText(),CTScan1.isSelected() ? "true" : "false",CTKepala1.isSelected() ? "true" : "false",CTAbdomen1.isSelected() ? "true" : "false",CTThorax1.isSelected() ? "true" : "false",MRI1.isSelected() ? "true" : "false",MRILainnya1.getText()
                ,ObatObatan.getText(),ResepPostOp.getText(),TransfusiMasuk.getText(),TerapiSebelumLainnya1.getText(),Kesadaran1.isSelected() ? "true" : "false",Observasi1.isSelected() ? "true" : "false",TD1.getText(),Nd1.getText(),Sh1.getText(),LukaOP.getText()
                ,PasangAlat.isSelected() ? "true" : "false",Drainase.getText(),Spoeling.getText(),AlatWarna.getText(),IUFD1.getText(),Balon1.getText(),Vol1.getText(),Warna1.getText(),DCLainnya1.getText(),Alkes1.getText()
                ,KdPetugasMenyerahkan1.getText(),KdPetugasMenerima1.getText(),LapPA.isSelected() ? "true" : "false",SamplePA.isSelected() ? "true" : "false",LapAnastesi.isSelected() ? "true" : "false",LapTimeOut.isSelected() ? "true" : "false","/uploads/transferpasienok/"+KdPetugasMenyerahkan.getText()+".png","/uploads/transferpasienok/"+KdPetugasMenerima.getText()+".png","",""
            })==true){
                emptTeks();
                tampil();
                TabRawat.setSelectedIndex(1);
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(680,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

private void panggilPhoto() {
    if (FormPhoto.isVisible()) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = koneksi.prepareStatement(
                "SELECT a.ttd_menerima, a.ttd_menerima1, a.ttd_menyerahkan, a.ttd_menyerahkan1 " +
                "FROM transfer_pasien_ok a WHERE a.no_rawat=?"
            );
            ps.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());

            rs = ps.executeQuery();

            if (rs.next()) {
                setHtmlPhoto(LoadHTML3, rs.getString("ttd_menyerahkan"));
                setHtmlPhoto(LoadHTML4, rs.getString("ttd_menerima"));
                setHtmlPhoto(LoadHTML2, rs.getString("ttd_menyerahkan1"));
                setHtmlPhoto(LoadHTML5, rs.getString("ttd_menerima1"));
            } else {
                setKosong(LoadHTML3);
                setKosong(LoadHTML4);
                setKosong(LoadHTML2);
                setKosong(LoadHTML5);
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                System.out.println("Notif close : " + e);
            }
        }
    }
}

private void setHtmlPhoto(javax.swing.JEditorPane pane, String path) {
    try {
        String clean = (path == null) ? "" : path.trim();
        if (clean.isEmpty() || "-".equals(clean)) {
            setKosong(pane);
            return;
        }

        String base = "http://100.10.1.4:80/webapps/verified/";
        String url = base + clean.replaceFirst("^/+", "");

        pane.setContentType("text/html");
        pane.setEditable(false);
        pane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());

        String html = "<html><body style='margin:0;padding:0;'>" +
                      "<img src='" + url + "' style='width:100%;'>" +
                      "</body></html>";

        pane.setText(html);

        javax.swing.text.Document doc = pane.getDocument();
        if (doc instanceof javax.swing.text.html.HTMLDocument) {
            ((javax.swing.text.html.HTMLDocument) doc).setBase(new java.net.URL(base));
        }

        pane.setCaretPosition(0);
        pane.revalidate();
        pane.repaint();
    } catch (Exception e) {
        System.out.println("setHtmlPhoto error: " + e);
        setKosong(pane);
    }
}

private void setKosong(javax.swing.JEditorPane pane) {
    pane.setContentType("text/html");
    pane.setEditable(false);
    pane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
    pane.setText("<html><body><center><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
    pane.setCaretPosition(0);
    pane.revalidate();
    pane.repaint();
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
                if(ChkJlnPuasa.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJlnPuasa.isSelected()==false){
                    nilai_jam =CmbJamPuasa.getSelectedIndex();
                    nilai_menit =CmbMenitPuasa.getSelectedIndex();
                    nilai_detik =CmbDetikPuasa.getSelectedIndex();
                }

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJlnKlisma.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJlnKlisma.isSelected()==false){
                    nilai_jam =CmbJamKlisma.getSelectedIndex();
                    nilai_menit =CmbMenitKlisma.getSelectedIndex();
                    nilai_detik =CmbDetikKlisma.getSelectedIndex();
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
                CmbJamPuasa.setSelectedItem(jam);
                CmbMenitPuasa.setSelectedItem(menit);
                CmbDetikPuasa.setSelectedItem(detik);
                CmbJamKlisma.setSelectedItem(jam);
                CmbMenitKlisma.setSelectedItem(menit);
                CmbDetikKlisma.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    private boolean toBoolean(String value) {
        if (value == null) return false;
        return "1".equals(value) || "true".equalsIgnoreCase(value);
    }
    
    private void cekDanAturTabRawat(String noRawat) {
    if (noRawat == null || noRawat.trim().isEmpty()) {
        System.out.println("noRawat masih kosong, skip cek tab");
        return;
    }

    boolean adaNoRawat = false;
    java.sql.PreparedStatement ps = null;
    java.sql.ResultSet rs = null;

    try {
        ps = koneksi.prepareStatement(
            "SELECT 1 FROM transfer_pasien_ok WHERE no_rawat = ? LIMIT 1"
        );
        ps.setString(1, noRawat.trim());
        rs = ps.executeQuery();
        adaNoRawat = rs.next();
    } catch (Exception e) {
        System.out.println("cekDanAturTabRawat error: " + e);
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
    }

    int idx = TabRawat.indexOfComponent(internalFrame4);

    if (adaNoRawat) {
        if (idx == -1) {
            TabRawat.insertTab("RR ke Ruangan", null, internalFrame4, null, 1);
        }
    } else {
        if (idx != -1) {
            TabRawat.remove(internalFrame4);
        }
    }
}
    
}
