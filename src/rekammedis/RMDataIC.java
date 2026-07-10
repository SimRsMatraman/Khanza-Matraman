/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPegawai;
import laporan.DlgBerkasRawat;
import laporan.DlgDiagnosaPenyakit;


/**
 *
 * @author perpustakaan
 */
public final class RMDataIC extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;   
    private String jenisRawat = "ralan";
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    private RMCariKeluhan carikeluhan=new RMCariKeluhan(null,false);
    private RMCariPemeriksaan caripemeriksaan=new RMCariPemeriksaan(null,false);
    private RMCariHasilRadiologi cariradiologi=new RMCariHasilRadiologi(null,false);
    private RMCariHasilLaborat carilaborat=new RMCariHasilLaborat(null,false);
    private RMCariJumlahObat cariobat=new RMCariJumlahObat(null,false);
    private DlgDiagnosaPenyakit penyakit=new DlgDiagnosaPenyakit(null,false);
    private RMCariDiagnosa1 rmcaridiagnosa1=new RMCariDiagnosa1(null,false);
    private RMCariDiagnosa2 rmcaridiagnosa2=new RMCariDiagnosa2(null,false);
    private RMCariDiagnosa3 rmcaridiagnosa3=new RMCariDiagnosa3(null,false);
    private RMCariDiagnosa4 rmcaridiagnosa4=new RMCariDiagnosa4(null,false);
    private RMCariDiagnosa5 rmcaridiagnosa5=new RMCariDiagnosa5(null,false);
    private RMCariProsedur1 rmcariprosedur1=new RMCariProsedur1(null,false);
    private RMCariProsedur2 rmcariprosedur2=new RMCariProsedur2(null,false);
    private RMCariProsedur3 rmcariprosedur3=new RMCariProsedur3(null,false);
    private RMCariProsedur4 rmcariprosedur4=new RMCariProsedur4(null,false);
    private RMCariRadRalan rmcariradralan=new RMCariRadRalan(null,false);
    private RMCariLabRalan rmcarilabralan=new RMCariLabRalan(null,false);
    private RMCariTindakan caritindakan=new RMCariTindakan(null,false);
    private RMCariKeluhanAssMedis carikeluhanass=new RMCariKeluhanAssMedis(null,false);
    private RMCariPemeriksaanAssMedis caripemeriksaanass=new RMCariPemeriksaanAssMedis(null,false);
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMDataIC(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","No Surat","Tanggal","Jam","Kode Dokter Pelaksana Tindakan","Dokter Pelaksana Tindakan",
            "Kode Pemberi Informasi","Nama Pemberi Informasi","Kode Saksi1/Perawat","Nama Saksi1/Perawat","Status Informed Consent","Diagnosa Kerja","Diagnosa Banding",
            "Kondisi Pasien","Tindakan Kedokteran","Tata Cara","Tujuan","Komplikasi","Prognosis","Alternatif","Kemungkinan Hasil Yang Tidak Terduga","Kemungkian Hasil Bila Tidak Dilakukan Tindakan","Hubungan Dgn Pasien","Nama TTD","Umur","Alamat","Acc IC"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 29; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(40);
            }else if(i==2){
                column.setPreferredWidth(105);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(60);
            }else if(i==8){
                column.setPreferredWidth(250);
            }else if(i==9){
                column.setPreferredWidth(250);
            }else if(i==10){
                column.setPreferredWidth(250);
            }else if(i==11){
                column.setPreferredWidth(250);
            }else if(i==12){
                column.setPreferredWidth(170);
            }else if(i==13){
                column.setPreferredWidth(75);
            }else if(i==14){
                column.setPreferredWidth(170);
            }else if(i==15){
                column.setPreferredWidth(75);
            }else if(i==16){
                column.setPreferredWidth(170);
            }else if(i==17){
                column.setPreferredWidth(75);
            }else if(i==18){
                column.setPreferredWidth(170);
            }else if(i==19){
                column.setPreferredWidth(75);
            }else if(i==20){
                column.setPreferredWidth(170);
            }else if(i==21){
                column.setPreferredWidth(75);
            }else if(i==22){
                column.setPreferredWidth(170);
            }else if(i==23){
                column.setPreferredWidth(75);
            }else if(i==24){
                column.setPreferredWidth(75);
            }else if(i==25){
                column.setPreferredWidth(75);
            }else if(i==26){
                column.setPreferredWidth(75);
            }else if(i==27){
                column.setPreferredWidth(75);
            }else if(i==28){
                column.setPreferredWidth(75);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KodeDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NamaDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KodeDokter.requestFocus();
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
        
        rmcaridiagnosa1.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcaridiagnosa1.getTable().getSelectedRow()!= -1){
//                    Informasi1.setText(rmcaridiagnosa1.getTable().getValueAt(rmcaridiagnosa1.getTable().getSelectedRow(),0).toString());
//                    Informasi1.setText(rmcaridiagnosa1.getTable().getValueAt(rmcaridiagnosa1.getTable().getSelectedRow(),1).toString());
                    DiagnosaKerja.append("( "+ rmcaridiagnosa1.getTable().getValueAt(rmcaridiagnosa1.getTable().getSelectedRow(),0).toString()+") ");
                    DiagnosaKerja.append(rmcaridiagnosa1.getTable().getValueAt(rmcaridiagnosa1.getTable().getSelectedRow(),1).toString()+", ");
                    DiagnosaKerja.requestFocus();
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
        
        carikeluhan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carikeluhan.getTable().getSelectedRow()!= -1){
                    DiagnosaKerja.append(carikeluhan.getTable().getValueAt(carikeluhan.getTable().getSelectedRow(),2).toString()+", ");
                    DiagnosaKerja.requestFocus();
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
        
        carikeluhanass.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carikeluhanass.getTable().getSelectedRow()!= -1){
                    DiagnosaKerja.append(carikeluhanass.getTable().getValueAt(carikeluhanass.getTable().getSelectedRow(),2).toString()+", ");
                    DiagnosaKerja.requestFocus();
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
                if(pegawai.getTable().getSelectedRow()!= -1){ 
                    if(i==1){
                        KodeDokter1.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        NamaDokter1.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());  
                    }else{
                        KodeDokter2.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        NamaDokter2.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());  
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
        
//        caripemeriksaan.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(caripemeriksaan.getTable().getSelectedRow()!= -1){
//                    JalannyaPenyakit.append(caripemeriksaan.getTable().getValueAt(caripemeriksaan.getTable().getSelectedRow(),2).toString()+", ");
//                    JalannyaPenyakit.requestFocus();
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
//        caripemeriksaanass.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(caripemeriksaanass.getTable().getSelectedRow()!= -1){
//                    JalannyaPenyakit.append(caripemeriksaanass.getTable().getValueAt(caripemeriksaanass.getTable().getSelectedRow(),2).toString()+", ");
//                    JalannyaPenyakit.requestFocus();
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
//        cariradiologi.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(cariradiologi.getTable().getSelectedRow()!= -1){
//                    PemeriksaanPenunjang.append(cariradiologi.getTable().getValueAt(cariradiologi.getTable().getSelectedRow(),2).toString()+", ");
//                    PemeriksaanPenunjang.requestFocus();
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
//        carilaborat.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(carilaborat.getTable().getSelectedRow()!= -1){
//                    HasilLaborat.append(carilaborat.getTable().getValueAt(carilaborat.getTable().getSelectedRow(),2).toString()+", ");
//                    HasilLaborat.requestFocus();
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
//        cariobat.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(cariobat.getTable().getSelectedRow()!= -1){
//                    Obat2an.append(cariobat.getTable().getValueAt(cariobat.getTable().getSelectedRow(),2).toString()+", ");
//                    Obat2an.requestFocus();
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
//        penyakit.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                tampil();
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
//        rmcaridiagnosa1.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcaridiagnosa1.getTable().getSelectedRow()!= -1){
//                    KodeDiagnosaUtama.setText(rmcaridiagnosa1.getTable().getValueAt(rmcaridiagnosa1.getTable().getSelectedRow(),0).toString());
//                    DiagnosaUtama.setText(rmcaridiagnosa1.getTable().getValueAt(rmcaridiagnosa1.getTable().getSelectedRow(),1).toString());
//                    KodeDiagnosaUtama.requestFocus();
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
//        rmcaridiagnosa2.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcaridiagnosa2.getTable().getSelectedRow()!= -1){
//                    KodeDiagnosaSekunder1.setText(rmcaridiagnosa2.getTable().getValueAt(rmcaridiagnosa2.getTable().getSelectedRow(),0).toString());
//                    DiagnosaSekunder1.setText(rmcaridiagnosa2.getTable().getValueAt(rmcaridiagnosa2.getTable().getSelectedRow(),1).toString());
//                    KodeDiagnosaSekunder1.requestFocus();
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
//        rmcaridiagnosa3.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcaridiagnosa3.getTable().getSelectedRow()!= -1){
//                    KodeDiagnosaSekunder2.setText(rmcaridiagnosa3.getTable().getValueAt(rmcaridiagnosa3.getTable().getSelectedRow(),0).toString());
//                    DiagnosaSekunder2.setText(rmcaridiagnosa3.getTable().getValueAt(rmcaridiagnosa3.getTable().getSelectedRow(),1).toString());
//                    KodeDiagnosaSekunder2.requestFocus();
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
//        rmcaridiagnosa4.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcaridiagnosa4.getTable().getSelectedRow()!= -1){
//                    KodeDiagnosaSekunder3.setText(rmcaridiagnosa4.getTable().getValueAt(rmcaridiagnosa4.getTable().getSelectedRow(),0).toString());
//                    DiagnosaSekunder3.setText(rmcaridiagnosa4.getTable().getValueAt(rmcaridiagnosa4.getTable().getSelectedRow(),1).toString());
//                    KodeDiagnosaSekunder3.requestFocus();
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
//        rmcaridiagnosa5.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcaridiagnosa5.getTable().getSelectedRow()!= -1){
//                    KodeDiagnosaSekunder4.setText(rmcaridiagnosa5.getTable().getValueAt(rmcaridiagnosa5.getTable().getSelectedRow(),0).toString());
//                    DiagnosaSekunder4.setText(rmcaridiagnosa5.getTable().getValueAt(rmcaridiagnosa5.getTable().getSelectedRow(),1).toString());
//                    KodeDiagnosaSekunder4.requestFocus();
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
//        rmcariprosedur1.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcariprosedur1.getTable().getSelectedRow()!= -1){
//                    KodeProsedurUtama.setText(rmcariprosedur1.getTable().getValueAt(rmcariprosedur1.getTable().getSelectedRow(),0).toString());
//                    ProsedurUtama.setText(rmcariprosedur1.getTable().getValueAt(rmcariprosedur1.getTable().getSelectedRow(),1).toString());
//                    KodeProsedurUtama.requestFocus();
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
//        rmcariprosedur2.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcariprosedur2.getTable().getSelectedRow()!= -1){
//                    KodeProsedurSekunder1.setText(rmcariprosedur2.getTable().getValueAt(rmcariprosedur2.getTable().getSelectedRow(),0).toString());
//                    ProsedurSekunder1.setText(rmcariprosedur2.getTable().getValueAt(rmcariprosedur2.getTable().getSelectedRow(),1).toString());
//                    KodeProsedurSekunder1.requestFocus();
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
//        rmcariprosedur3.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcariprosedur3.getTable().getSelectedRow()!= -1){
//                    KodeProsedurSekunder2.setText(rmcariprosedur3.getTable().getValueAt(rmcariprosedur3.getTable().getSelectedRow(),0).toString());
//                    ProsedurSekunder2.setText(rmcariprosedur3.getTable().getValueAt(rmcariprosedur3.getTable().getSelectedRow(),1).toString());
//                    KodeProsedurSekunder2.requestFocus();
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
//        rmcariprosedur4.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcariprosedur4.getTable().getSelectedRow()!= -1){
//                    KodeProsedurSekunder3.setText(rmcariprosedur4.getTable().getValueAt(rmcariprosedur4.getTable().getSelectedRow(),0).toString());
//                    ProsedurSekunder3.setText(rmcariprosedur4.getTable().getValueAt(rmcariprosedur4.getTable().getSelectedRow(),1).toString());
//                    KodeProsedurSekunder3.requestFocus();
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
//        rmcariradralan.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcariradralan.getTable().getSelectedRow()!= -1){
//                    PemeriksaanPenunjang.append(rmcariradralan.getTable().getValueAt(rmcariradralan.getTable().getSelectedRow(),2).toString()+", ");
//                    PemeriksaanPenunjang.requestFocus();
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
//        rmcarilabralan.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcarilabralan.getTable().getSelectedRow()!= -1){
//                    HasilLaborat.append(rmcarilabralan.getTable().getValueAt(rmcarilabralan.getTable().getSelectedRow(),2).toString()+", ");
//                    HasilLaborat.requestFocus();
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
//        caritindakan.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(caritindakan.getTable().getSelectedRow()!= -1){
//                    Tindakan.append(caritindakan.getTable().getValueAt(caritindakan.getTable().getSelectedRow(),2).toString()+", ");
//                    Tindakan.requestFocus();
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
        MnLaporanResume = new javax.swing.JMenuItem();
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
        scrollPane2 = new widget.ScrollPane();
        DiagnosaKerja = new widget.TextArea();
        jLabel5 = new widget.Label();
        label14 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel12 = new widget.Label();
        BtnDokter3 = new widget.Button();
        jLabel14 = new widget.Label();
        label15 = new widget.Label();
        KodeDokter1 = new widget.TextBox();
        NamaDokter1 = new widget.TextBox();
        BtnDokter1 = new widget.Button();
        label16 = new widget.Label();
        KodeDokter2 = new widget.TextBox();
        NamaDokter2 = new widget.TextBox();
        BtnDokter2 = new widget.Button();
        jLabel13 = new widget.Label();
        TanggalSurat = new widget.Tanggal();
        jLabel11 = new widget.Label();
        NoSurat = new widget.TextBox();
        jLabel18 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        TindakanKedokteran = new widget.TextArea();
        jLabel20 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        Kemungkinan = new widget.TextArea();
        jLabel22 = new widget.Label();
        TataCara = new widget.ComboBox();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Tujuan = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        KondisiPasien = new widget.TextArea();
        scrollPane11 = new widget.ScrollPane();
        Komplikasi = new widget.TextArea();
        jLabel34 = new widget.Label();
        jLabel25 = new widget.Label();
        Prognosis = new widget.ComboBox();
        jLabel26 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        Alternatif = new widget.TextArea();
        jLabel35 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Kemungkinan1 = new widget.TextArea();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        Status = new widget.TextBox();
        jLabel15 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel27 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        DiagnosaBanding = new widget.TextArea();
        accic = new widget.TextBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        jLabel28 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        Alamat = new widget.TextBox();
        Nama = new widget.TextBox();
        Umur = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel16 = new widget.Label();
        JenisRawat = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLaporanResume.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResume.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResume.setText("Informed Consent Rawat Jalan");
        MnLaporanResume.setName("MnLaporanResume"); // NOI18N
        MnLaporanResume.setPreferredSize(new java.awt.Dimension(220, 26));
        MnLaporanResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanResumeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanResume);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Informed Consent ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-08-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-08-2025" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 800));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(104, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(361, 10, 424, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(247, 10, 112, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        DiagnosaKerja.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaKerja.setColumns(20);
        DiagnosaKerja.setRows(5);
        DiagnosaKerja.setName("DiagnosaKerja"); // NOI18N
        DiagnosaKerja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKerjaKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(DiagnosaKerja);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(250, 140, 541, 50);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 100, 23);

        label14.setText("Dokter Pelaksana Tindakan :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(7, 40, 180, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setName("KodeDokter"); // NOI18N
        KodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterKeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(190, 40, 141, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        NamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(330, 40, 270, 23);

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
        BtnDokter.setBounds(600, 40, 28, 23);

        jLabel12.setText("Status Informed Consent :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(645, 70, 130, 23);

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
        FormInput.add(BtnDokter3);
        BtnDokter3.setBounds(220, 160, 28, 23);

        jLabel14.setText("1. Diagnosa Kerja :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(10, 140, 240, 23);

        label15.setText("Pemberi Informasi :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(7, 70, 180, 23);

        KodeDokter1.setEditable(false);
        KodeDokter1.setName("KodeDokter1"); // NOI18N
        KodeDokter1.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokter1KeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter1);
        KodeDokter1.setBounds(190, 70, 141, 23);

        NamaDokter1.setEditable(false);
        NamaDokter1.setName("NamaDokter1"); // NOI18N
        NamaDokter1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter1);
        NamaDokter1.setBounds(330, 70, 270, 23);

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
        FormInput.add(BtnDokter1);
        BtnDokter1.setBounds(600, 70, 28, 23);

        label16.setText("Saksi 1/Perawat :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(7, 100, 180, 23);

        KodeDokter2.setEditable(false);
        KodeDokter2.setName("KodeDokter2"); // NOI18N
        KodeDokter2.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokter2KeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter2);
        KodeDokter2.setBounds(190, 100, 141, 23);

        NamaDokter2.setEditable(false);
        NamaDokter2.setName("NamaDokter2"); // NOI18N
        NamaDokter2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter2);
        NamaDokter2.setBounds(330, 100, 270, 23);

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
        BtnDokter2.setBounds(600, 100, 28, 23);

        jLabel13.setText("Tanggal Surat :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(630, 40, 90, 23);

        TanggalSurat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-08-2025" }));
        TanggalSurat.setDisplayFormat("dd-MM-yyyy");
        TanggalSurat.setName("TanggalSurat"); // NOI18N
        TanggalSurat.setOpaque(false);
        TanggalSurat.setPreferredSize(new java.awt.Dimension(141, 18));
        TanggalSurat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalSuratItemStateChanged(evt);
            }
        });
        TanggalSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalSuratActionPerformed(evt);
            }
        });
        TanggalSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSuratKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSurat);
        TanggalSurat.setBounds(730, 40, 90, 23);

        jLabel11.setText("No. Surat :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(790, 10, 70, 23);

        NoSurat.setEditable(false);
        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(870, 10, 170, 23);

        jLabel18.setText("3. Kondisi Pasien :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(10, 270, 240, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        TindakanKedokteran.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TindakanKedokteran.setColumns(20);
        TindakanKedokteran.setRows(5);
        TindakanKedokteran.setName("TindakanKedokteran"); // NOI18N
        TindakanKedokteran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKedokteranKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(TindakanKedokteran);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(250, 320, 541, 50);

        jLabel20.setText("4. Tindakan Kedokteran :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(10, 320, 240, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Kemungkinan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kemungkinan.setColumns(20);
        Kemungkinan.setRows(5);
        Kemungkinan.setName("Kemungkinan"); // NOI18N
        Kemungkinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KemungkinanKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(Kemungkinan);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(250, 660, 541, 50);

        jLabel22.setText("(Tipe Sedasi/Anestesi. Uraian Singkat Prosedur Dan Tahapan Yang Penting)");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(190, 400, 362, 23);

        TataCara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Sedasi", "Non Sedasi" }));
        TataCara.setName("TataCara"); // NOI18N
        TataCara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TataCaraActionPerformed(evt);
            }
        });
        TataCara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TataCaraKeyPressed(evt);
            }
        });
        FormInput.add(TataCara);
        TataCara.setBounds(250, 380, 170, 23);

        jLabel23.setText("10. Kemungkinan Hasil Yang Tidak Terduga :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(10, 660, 240, 23);

        jLabel24.setText("5. Tata Cara  :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(10, 380, 240, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Tujuan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tujuan.setColumns(20);
        Tujuan.setRows(5);
        Tujuan.setName("Tujuan"); // NOI18N
        Tujuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TujuanKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Tujuan);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(250, 430, 541, 50);

        jLabel32.setText("6. Tujuan & Manfaat :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(10, 430, 240, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        KondisiPasien.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KondisiPasien.setColumns(20);
        KondisiPasien.setRows(5);
        KondisiPasien.setName("KondisiPasien"); // NOI18N
        KondisiPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiPasienKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(KondisiPasien);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(250, 260, 541, 50);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        Komplikasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Komplikasi.setColumns(20);
        Komplikasi.setRows(5);
        Komplikasi.setName("Komplikasi"); // NOI18N
        Komplikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KomplikasiKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(Komplikasi);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(250, 490, 541, 50);

        jLabel34.setText("7. Komplikasi & Resiko Tindakan:");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(10, 490, 240, 23);

        jLabel25.setText("(Prognosis Vital, Prognosis Fungsi Dan Prognosis Kesembuhan)");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(190, 570, 310, 23);

        Prognosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Baik", "Dubia et bonam/cenderung", "Dubia et malam/cenderung", "Tidak baik" }));
        Prognosis.setName("Prognosis"); // NOI18N
        Prognosis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrognosisActionPerformed(evt);
            }
        });
        Prognosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrognosisKeyPressed(evt);
            }
        });
        FormInput.add(Prognosis);
        Prognosis.setBounds(250, 550, 230, 23);

        jLabel26.setText("8. Prognosis :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(10, 550, 240, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Alternatif.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Alternatif.setColumns(20);
        Alternatif.setRows(5);
        Alternatif.setName("Alternatif"); // NOI18N
        Alternatif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlternatifKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Alternatif);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(250, 600, 541, 50);

        jLabel35.setText("9. Alternatif Pengobatan & Risiko :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(10, 600, 240, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Kemungkinan1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kemungkinan1.setColumns(20);
        Kemungkinan1.setRows(5);
        Kemungkinan1.setName("Kemungkinan1"); // NOI18N
        Kemungkinan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kemungkinan1KeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Kemungkinan1);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(250, 720, 541, 50);

        jLabel36.setText("Bila Tidak Dilakukan Tindakan");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(10, 740, 240, 10);

        jLabel37.setText("11. kemungkinan Hasil :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(10, 720, 240, 23);

        Status.setEditable(false);
        Status.setHighlighter(null);
        Status.setName("Status"); // NOI18N
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        FormInput.add(Status);
        Status.setBounds(780, 70, 270, 23);

        jLabel15.setText("Hubungan Dgn pasien :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(830, 40, 130, 23);

        Hubungan.setEditable(false);
        Hubungan.setHighlighter(null);
        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(970, 40, 270, 23);

        jLabel27.setText("Saya Sudah Membaca, Mengerti dan Menyetujui “Informed Consent”  ");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(640, 100, 350, 20);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        DiagnosaBanding.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaBanding.setColumns(20);
        DiagnosaBanding.setRows(5);
        DiagnosaBanding.setName("DiagnosaBanding"); // NOI18N
        DiagnosaBanding.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaBandingKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(DiagnosaBanding);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(250, 200, 541, 50);

        accic.setEditable(false);
        accic.setHighlighter(null);
        accic.setName("accic"); // NOI18N
        accic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                accicKeyPressed(evt);
            }
        });
        FormInput.add(accic);
        accic.setBounds(990, 100, 140, 24);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "TTE Pasien : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass2.setBackground(new java.awt.Color(255, 255, 255));
        FormPass2.setBorder(null);
        FormPass2.setName("FormPass2"); // NOI18N
        FormPass2.setPreferredSize(new java.awt.Dimension(115, 40));

        BtnRefreshPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto.setMnemonic('U');
        BtnRefreshPhoto.setText("Refresh");
        BtnRefreshPhoto.setToolTipText("Alt+U");
        BtnRefreshPhoto.setName("BtnRefreshPhoto"); // NOI18N
        BtnRefreshPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(BtnRefreshPhoto);

        FormPhoto.add(FormPass2, java.awt.BorderLayout.PAGE_END);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        FormInput.add(FormPhoto);
        FormPhoto.setBounds(820, 260, 370, 350);

        jLabel28.setText("2. Diagnosa Banding :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(10, 200, 240, 23);

        jLabel33.setText("Alamat :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(810, 210, 150, 23);

        jLabel38.setText("Nama yang bertanda tangan :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(810, 150, 150, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Tahun");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(1020, 180, 30, 23);

        Alamat.setEditable(false);
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlamatActionPerformed(evt);
            }
        });
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        FormInput.add(Alamat);
        Alamat.setBounds(970, 210, 270, 23);

        Nama.setEditable(false);
        Nama.setHighlighter(null);
        Nama.setName("Nama"); // NOI18N
        Nama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaKeyPressed(evt);
            }
        });
        FormInput.add(Nama);
        Nama.setBounds(970, 150, 270, 23);

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N
        Umur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UmurActionPerformed(evt);
            }
        });
        Umur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurKeyPressed(evt);
            }
        });
        FormInput.add(Umur);
        Umur.setBounds(970, 180, 50, 23);

        jLabel40.setText("Umur :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(810, 180, 150, 23);

        jLabel16.setText("Jns. Rawat :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(1040, 10, 80, 23);

        JenisRawat.setEditable(false);
        JenisRawat.setHighlighter(null);
        JenisRawat.setName("JenisRawat"); // NOI18N
        JenisRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisRawatKeyPressed(evt);
            }
        });
        FormInput.add(JenisRawat);
        JenisRawat.setBounds(1120, 10, 130, 23);

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
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){ 
        Valid.textKosong(TNoRw,"Pasien");
        }else if(KodeDokter.getText().equals("")||NamaDokter.getText().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Pelaksana Tindakan");
        }else if(KodeDokter1.getText().equals("")||NamaDokter1.getText().equals("")){
            Valid.textKosong(BtnDokter1,"Pemberi Informasi");
        }else if(DiagnosaKerja.getText().equals("")){
            Valid.textKosong(DiagnosaKerja,"DiagnosaKerja");
        }else if(DiagnosaBanding.getText().equals("")){
            Valid.textKosong(DiagnosaBanding,"DiagnosaBanding");
        }else if(KondisiPasien.getText().equals("")){
            Valid.textKosong(KondisiPasien,"KondisiPasien");
        }else if(TindakanKedokteran.getText().equals("")){
            Valid.textKosong(TindakanKedokteran,"TindakanKedokteran");
        }else if(TataCara.getSelectedItem().equals("")){
            Valid.textKosong(TataCara,"TataCara");
        }else if(Tujuan.getText().equals("")){
            Valid.textKosong(Tujuan,"Tujuan");
        }else if(Komplikasi.getText().equals("")){
            Valid.textKosong(Komplikasi,"Komplikasi");
        }else if(Prognosis.getSelectedItem().equals("")){
            Valid.textKosong(Prognosis,"Prognosis");
        }else if(Alternatif.getText().equals("")){
            Valid.textKosong(Alternatif,"Alternatif");
        }else if(Kemungkinan.getText().equals("")){
            Valid.textKosong(Kemungkinan,"Kemungkinan");
        }else if(Kemungkinan1.getText().equals("")){
            Valid.textKosong(Kemungkinan1,"Kemungkinan1");
        }else{
            String jenis = JenisRawat.getText();
            String tabelTujuan = "";

            if(jenis.equalsIgnoreCase("Rawat Jalan / IGD")){
                tabelTujuan = "surat_ic_rajal";
            }else if(jenis.equalsIgnoreCase("Rawat Inap")){
                tabelTujuan = "surat_ic_ranap";
            }

            if(!tabelTujuan.equals("")){
                if(Sequel.menyimpantf(tabelTujuan,
                        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                        "No.Surat",25,new String[]{
                        NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),
                        KodeDokter.getText(),KodeDokter1.getText(),KodeDokter2.getText(),"-",DiagnosaKerja.getText(),
                        DiagnosaBanding.getText(),KondisiPasien.getText(),TindakanKedokteran.getText(),
                        TataCara.getSelectedItem().toString(),Tujuan.getText(),Komplikasi.getText(),
                        Prognosis.getSelectedItem().toString(),Alternatif.getText(),Kemungkinan.getText(),
                        Kemungkinan1.getText(),"-","","","","-",""
                })==true){
                    tampil();
                    emptTeks();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Jenis Rawat tidak dikenali. Harus Rawat Jalan / IGD atau Rawat Inap.");
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,DiagnosaKerja,BtnBatal);
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
            String jenis = JenisRawat.getText();
            String tabelTujuan = "";

            if(jenis.equalsIgnoreCase("Rawat Jalan / IGD")){
                tabelTujuan = "surat_ic_rajal";
            }else if(jenis.equalsIgnoreCase("Rawat Inap")){
                tabelTujuan = "surat_ic_ranap";
            }

            if(!tabelTujuan.equals("")){
                if(Sequel.queryu2tf("delete from "+tabelTujuan+" where no_surat=?",1,new String[]{
                    tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()
                })==true){
                    tampil();
                    emptTeks();
                }else{
                    JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Jenis Rawat tidak dikenali. Harus Rawat Jalan / IGD atau Rawat Inap.");
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
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodeDokter.getText().equals("")||NamaDokter.getText().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Pelaksana Tindakan");
        }else if(KodeDokter1.getText().equals("")||NamaDokter1.getText().equals("")){
            Valid.textKosong(BtnDokter1,"Pemberi Informasi");
        }else if(KodeDokter2.getText().equals("")||NamaDokter2.getText().equals("")){
            Valid.textKosong(BtnDokter2,"Saksi 1/Perawat");
        }else if(DiagnosaKerja.getText().equals("")){
            Valid.textKosong(DiagnosaKerja,"DiagnosaKerja");
        }else if(DiagnosaBanding.getText().equals("")){
            Valid.textKosong(DiagnosaBanding,"DiagnosaBanding");
        }else if(KondisiPasien.getText().equals("")){
            Valid.textKosong(KondisiPasien,"KondisiPasien");
        }else if(TindakanKedokteran.getText().equals("")){
            Valid.textKosong(TindakanKedokteran,"TindakanKedokteran");
        }else if(TataCara.getSelectedItem().equals("")){
            Valid.textKosong(TataCara,"TataCara");
        }else if(Tujuan.getText().equals("")){
            Valid.textKosong(Tujuan,"Tujuan");
        }else if(Komplikasi.getText().equals("")){
            Valid.textKosong(Komplikasi,"Komplikasi");
        }else if(Prognosis.getSelectedItem().equals("")){
            Valid.textKosong(Prognosis,"Prognosis");
        }else if(Alternatif.getText().equals("")){
            Valid.textKosong(Alternatif,"Alternatif");
        }else if(Kemungkinan.getText().equals("")){
            Valid.textKosong(Kemungkinan,"Kemungkinan");
        }else if(Kemungkinan1.getText().equals("")){
            Valid.textKosong(Kemungkinan1,"Kemungkinan1");
        }else{
            if(tbObat.getSelectedRow()>-1){
                String jenis = JenisRawat.getText();
                String tabelTujuan = "";

                if(jenis.equalsIgnoreCase("Rawat Jalan / IGD")){
                    tabelTujuan = "surat_ic_rajal";
                }else if(jenis.equalsIgnoreCase("Rawat Inap")){
                    tabelTujuan = "surat_ic_ranap";
                }

                if(!tabelTujuan.equals("")){
                    if(Sequel.mengedittf(tabelTujuan,"no_surat=?",
                            "no_surat=?,no_rawat=?,tanggal_surat=?,jam=?,kd_dokter=?,nik=?,nik1=?,status_ic=?,DiagnosaKerja=?,DiagnosaBanding=?,KondisiPasien=?,TindakanKedokteran=?,TataCara=?,Tujuan=?,Komplikasi=?,Prognosis=?,Alternatif=?,Kemungkinan=?,Kemungkinan1=?,hubungan=?,Nama=?,Umur=?,Alamat=?,acc_ic=?",
                            25,new String[]{
                                NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),
                                KodeDokter.getText(),KodeDokter1.getText(),KodeDokter2.getText(),
                                Status.getText(),DiagnosaKerja.getText(),DiagnosaBanding.getText(),KondisiPasien.getText(),TindakanKedokteran.getText(),
                                TataCara.getSelectedItem().toString(),Tujuan.getText(),Komplikasi.getText(),
                                Prognosis.getSelectedItem().toString(),Alternatif.getText(),Kemungkinan.getText(),Kemungkinan1.getText(),
                                Hubungan.getText(),Nama.getText(),Umur.getText(),Alamat.getText(),accic.getText(),
                                // WHERE condition
                                tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()
                            })==true){
                        tampil();
                        emptTeks();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Jenis Rawat tidak dikenali. Harus Rawat Jalan / IGD atau Rawat Inap.");
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
        dokter.dispose();
        carikeluhan.dispose();
        caripemeriksaan.dispose();
        carilaborat.dispose();
        cariobat.dispose();
        cariradiologi.dispose();
        penyakit.dispose();
        rmcaridiagnosa1.dispose();
        rmcaridiagnosa2.dispose();
        rmcaridiagnosa3.dispose();
        rmcaridiagnosa4.dispose();
        rmcaridiagnosa5.dispose();
        rmcariprosedur1.dispose();
        rmcariprosedur2.dispose();
        rmcariprosedur3.dispose();
        rmcariprosedur4.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
//        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        if(! TCari.getText().trim().equals("")){
//            BtnCariActionPerformed(evt);
//        }
//        if(tabMode.getRowCount()==0){
//            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
//            BtnBatal.requestFocus();
//        }else if(tabMode.getRowCount()!=0){
//            Map<String, Object> param = new HashMap<>(); 
//                param.put("namars",akses.getnamars());
//                param.put("alamatrs",akses.getalamatrs());
//                param.put("kotars",akses.getkabupatenrs());
//                param.put("propinsirs",akses.getpropinsirs());
//                param.put("kontakrs",akses.getkontakrs());
//                param.put("emailrs",akses.getemailrs());   
//                param.put("logo",Sequel.cariGambar("select logo from setting")); 
//                if(TCari.getText().equals("")){
//                    Valid.MyReportqry("rptDataResumePasien.jasper","report","::[ Data Resume Pasien ]::",
//                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
//                        "surat_ic_rajal.kd_dokter,dokter.nm_dokter,surat_ic_rajal.kondisi_pulang,surat_ic_rajal.keluhan_utama,surat_ic_rajal.jalannya_penyakit, "+
//                        "surat_ic_rajal.pemeriksaan_penunjang,surat_ic_rajal.hasil_laborat,surat_ic_rajal.diagnosa_utama,surat_ic_rajal.kd_diagnosa_utama, "+
//                        "surat_ic_rajal.diagnosa_sekunder,surat_ic_rajal.kd_diagnosa_sekunder,surat_ic_rajal.diagnosa_sekunder2,surat_ic_rajal.kd_diagnosa_sekunder2, "+
//                        "surat_ic_rajal.diagnosa_sekunder3,surat_ic_rajal.kd_diagnosa_sekunder3,surat_ic_rajal.diagnosa_sekunder4,surat_ic_rajal.kd_diagnosa_sekunder4, "+
//                        "surat_ic_rajal.prosedur_utama,surat_ic_rajal.kd_prosedur_utama,surat_ic_rajal.prosedur_sekunder,surat_ic_rajal.kd_prosedur_sekunder, "+
//                        "surat_ic_rajal.prosedur_sekunder2,surat_ic_rajal.kd_prosedur_sekunder2,surat_ic_rajal.prosedur_sekunder3,surat_ic_rajal.kd_prosedur_sekunder3, "+
//                        "surat_ic_rajal.obat_pulang,surat_ic_rajal.tindak_lanjut,surat_ic_rajal.asal_pasien,surat_ic_rajal.tindakan from surat_ic_rajal inner join reg_periksa on surat_ic_rajal.no_rawat=reg_periksa.no_rawat  "+
//                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_ic_rajal.kd_dokter=dokter.kd_dokter "+
//                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
//                }else{
//                    Valid.MyReportqry("rptDataResumePasien.jasper","report","::[ Data Resume Pasien ]::",
//                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
//                        "surat_ic_rajal.kd_dokter,dokter.nm_dokter,surat_ic_rajal.kondisi_pulang,surat_ic_rajal.keluhan_utama,surat_ic_rajal.jalannya_penyakit, "+
//                        "surat_ic_rajal.pemeriksaan_penunjang,surat_ic_rajal.hasil_laborat,surat_ic_rajal.diagnosa_utama,surat_ic_rajal.kd_diagnosa_utama, "+
//                        "surat_ic_rajal.diagnosa_sekunder,surat_ic_rajal.kd_diagnosa_sekunder,surat_ic_rajal.diagnosa_sekunder2,surat_ic_rajal.kd_diagnosa_sekunder2, "+
//                        "surat_ic_rajal.diagnosa_sekunder3,surat_ic_rajal.kd_diagnosa_sekunder3,surat_ic_rajal.diagnosa_sekunder4,surat_ic_rajal.kd_diagnosa_sekunder4, "+
//                        "surat_ic_rajal.prosedur_utama,surat_ic_rajal.kd_prosedur_utama,surat_ic_rajal.prosedur_sekunder,surat_ic_rajal.kd_prosedur_sekunder, "+
//                        "surat_ic_rajal.prosedur_sekunder2,surat_ic_rajal.kd_prosedur_sekunder2,surat_ic_rajal.prosedur_sekunder3,surat_ic_rajal.kd_prosedur_sekunder3, "+
//                        "surat_ic_rajal.obat_pulang,surat_ic_rajal.tindak_lanjut,surat_ic_rajal.asal_pasien,surat_ic_rajal.tindakan from surat_ic_rajal inner join reg_periksa on surat_ic_rajal.no_rawat=reg_periksa.no_rawat  "+
//                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_ic_rajal.kd_dokter=dokter.kd_dokter "+
//                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.status_lanjut like '%"+TCari.getText().trim()+"%' or "+
//                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
//                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
//                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
//                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
//                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.kondisi_pulang like '%"+TCari.getText().trim()+"%' or "+
//                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.kd_diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
//                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
//                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.prosedur_utama like '%"+TCari.getText().trim()+"%' or "+
//                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
//                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.kd_prosedur_utama like '%"+TCari.getText().trim()+"%' "+
//                        "order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
//                }
//                    
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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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

    private void KodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterKeyPressed
        Valid.pindah(evt,TCari,Status);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
       Valid.pindah(evt,TCari,Status);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void DiagnosaKerjaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKerjaKeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            if(evt.isShiftDown()){
//                JalannyaPenyakit.requestFocus();
//            }
//        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
//            Kondisi.requestFocus();
//        }
    }//GEN-LAST:event_DiagnosaKerjaKeyPressed

    private void MnLaporanResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanResumeActionPerformed
//        if(tbObat.getSelectedRow()>-1){
//            Map<String, Object> param = new HashMap<>();    
//            param.put("namars",akses.getnamars());
//            param.put("alamatrs",akses.getalamatrs());
//            param.put("kotars",akses.getkabupatenrs());
//            param.put("propinsirs",akses.getpropinsirs());
//            param.put("kontakrs",akses.getkontakrs());
//            param.put("emailrs",akses.getemailrs());   
//            param.put("logo",Sequel.cariGambar("select logo from setting")); 
//            param.put("norawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
//            param.put("nosurat",tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
//            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())); 
////            if(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString().equals("Ralan")){
////                param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
////                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
////            }else{
////                param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
////                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
////            }
//            Valid.MyReport("rptLaporanICRajal.jasper","report","::[ Informed Consent Rawat Jalan ]::",param);
//        }
    }//GEN-LAST:event_MnLaporanResumeActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcaridiagnosa1.setNoRawat(TNoRw.getText());
            rmcaridiagnosa1.tampil();
            rmcaridiagnosa1.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcaridiagnosa1.setLocationRelativeTo(internalFrame1);
            rmcaridiagnosa1.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void KodeDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDokter1KeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        i=1;
//        pegawai.isCek();
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1KeyPressed

    private void KodeDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDokter2KeyPressed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        i=2;
//        pegawai.isCek();
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter2KeyPressed

    private void TanggalSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalSuratActionPerformed

    private void TanggalSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSuratKeyPressed
        Valid.pindah(evt,NoSurat,Status);
    }//GEN-LAST:event_TanggalSuratKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,TCari,Status);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void DiagnosaBandingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaBandingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaBandingKeyPressed

    private void TindakanKedokteranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKedokteranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakanKedokteranKeyPressed

    private void KemungkinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KemungkinanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KemungkinanKeyPressed

    private void TataCaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TataCaraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TataCaraActionPerformed

    private void TataCaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TataCaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TataCaraKeyPressed

    private void TujuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TujuanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TujuanKeyPressed

    private void KondisiPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KondisiPasienKeyPressed

    private void KomplikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KomplikasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KomplikasiKeyPressed

    private void PrognosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrognosisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrognosisActionPerformed

    private void PrognosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrognosisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrognosisKeyPressed

    private void AlternatifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlternatifKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlternatifKeyPressed

    private void Kemungkinan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kemungkinan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kemungkinan1KeyPressed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HubunganKeyPressed

    private void accicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_accicKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_accicKeyPressed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void TanggalSuratItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalSuratItemStateChanged
//        autoNumberX(TanggalSurat.getSelectedItem()+"");
    }//GEN-LAST:event_TanggalSuratItemStateChanged

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlamatKeyPressed

    private void NamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NamaKeyPressed

    private void AlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlamatActionPerformed

    private void UmurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UmurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UmurActionPerformed

    private void UmurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UmurKeyPressed

    private void JenisRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisRawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisRawatKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataIC dialog = new RMDataIC(new javax.swing.JFrame(), true);
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
    private widget.TextArea Alternatif;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter3;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DiagnosaBanding;
    private widget.TextArea DiagnosaKerja;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox Hubungan;
    private widget.TextBox JenisRawat;
    private widget.TextArea Kemungkinan;
    private widget.TextArea Kemungkinan1;
    private widget.TextBox KodeDokter;
    private widget.TextBox KodeDokter1;
    private widget.TextBox KodeDokter2;
    private widget.TextArea Komplikasi;
    private widget.TextArea KondisiPasien;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox Nama;
    private widget.TextBox NamaDokter;
    private widget.TextBox NamaDokter1;
    private widget.TextBox NamaDokter2;
    private widget.TextBox NoSurat;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Prognosis;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.TextBox Status;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalSurat;
    private widget.ComboBox TataCara;
    private widget.TextArea TindakanKedokteran;
    private widget.TextArea Tujuan;
    private widget.TextBox Umur;
    private widget.TextBox accic;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
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
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            String tableIC = jenisRawat.equalsIgnoreCase("ralan") ? "surat_ic_rajal" : "surat_ic_ranap";

            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    tableIC+".no_surat,"+tableIC+".tanggal_surat,"+tableIC+".jam,"+tableIC+".kd_dokter,dokter.nm_dokter, " +
                    tableIC+".nik,"+tableIC+".nik,nama1.nama as nama1,"+tableIC+".nik1,nama2.nama as nama2,"+tableIC+".status_ic, "+
                    tableIC+".diagnosaKerja,"+tableIC+".DiagnosaBanding,"+tableIC+".TindakanKedokteran,"+tableIC+".TataCara,"+tableIC+".Tujuan,  "+
                    tableIC+".KondisiPasien,"+tableIC+".Komplikasi,"+tableIC+".Prognosis,"+tableIC+".Alternatif,"+tableIC+".Kemungkinan,  "+
                    tableIC+".Kemungkinan1,"+tableIC+".hubungan,"+tableIC+".Nama,"+tableIC+".Umur,"+tableIC+".Alamat,"+tableIC+".acc_ic,"+tableIC+".tte "+
                    "from "+tableIC+" inner join reg_periksa on "+tableIC+".no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on "+tableIC+".kd_dokter=dokter.kd_dokter "+
                    "inner join pegawai as nama1 on "+tableIC+".nik=nama1.nik "+
                    "inner join pegawai as nama2 on "+tableIC+".nik1=nama2.nik "+
                    "where "+tableIC+".tanggal_surat between ? and ? order by "+tableIC+".tanggal_surat");
            } else {
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    tableIC+".no_surat,"+tableIC+".tanggal_surat,"+tableIC+".jam,"+tableIC+".kd_dokter,dokter.nm_dokter, " +
                    tableIC+".nik,"+tableIC+".nik,nama1.nama as nama1,"+tableIC+".nik1,nama2.nama as nama2,"+tableIC+".status_ic, "+
                    tableIC+".diagnosaKerja,"+tableIC+".DiagnosaBanding,"+tableIC+".TindakanKedokteran,"+tableIC+".TataCara,"+tableIC+".Tujuan,  "+
                    tableIC+".KondisiPasien,"+tableIC+".Komplikasi,"+tableIC+".Prognosis,"+tableIC+".Alternatif,"+tableIC+".Kemungkinan,  "+
                    tableIC+".Kemungkinan1,"+tableIC+".hubungan,"+tableIC+".Nama,"+tableIC+".Umur,"+tableIC+".Alamat,"+tableIC+".acc_ic,"+tableIC+".tte "+
                    "from "+tableIC+" inner join reg_periksa on "+tableIC+".no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on "+tableIC+".kd_dokter=dokter.kd_dokter "+
                    "inner join pegawai as nama1 on "+tableIC+".nik=nama1.nik "+
                    "inner join pegawai as nama2 on "+tableIC+".nik1=nama2.nik "+
                    "where "+tableIC+".tanggal_surat between ? and ? and ("+tableIC+".status_ic like ? or "+
                    tableIC+".DiagnosaBanding like ? or "+tableIC+".no_surat like ? or "+
                    "pasien.nm_pasien like ? or "+tableIC+".kd_dokter like ? or "+
                    "dokter.nm_dokter like ? or "+tableIC+".nik like ? or "+
                    tableIC+".nik1 like ? or "+tableIC+".TataCara like ? or "+
                    tableIC+".Alternatif like ? or "+tableIC+".DiagnosaKerja like ? or "+
                    tableIC+".no_rawat like ?) "+
                    "order by "+tableIC+".tanggal_surat");
            }
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3, "%"+TCari.getText()+"%");
                    ps.setString(4, "%"+TCari.getText()+"%");
                    ps.setString(5, "%"+TCari.getText()+"%");
                    ps.setString(6, "%"+TCari.getText()+"%");
                    ps.setString(7, "%"+TCari.getText()+"%");
                    ps.setString(8, "%"+TCari.getText()+"%");
                    ps.setString(9, "%"+TCari.getText()+"%");
                    ps.setString(10, "%"+TCari.getText()+"%");
                    ps.setString(11, "%"+TCari.getText()+"%");
                    ps.setString(12, "%"+TCari.getText()+"%");
                    ps.setString(13, "%"+TCari.getText()+"%");
                    ps.setString(14, "%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("no_surat"),rs.getString("tanggal_surat"),
                        rs.getString("jam"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("nik"),rs.getString("nama1"),rs.getString("nik1"),rs.getString("nama2"),
                        rs.getString("status_ic"),rs.getString("DiagnosaKerja"),rs.getString("DiagnosaBanding"),rs.getString("KondisiPasien"),rs.getString("TindakanKedokteran"),rs.getString("TataCara"),
                        rs.getString("Tujuan"),rs.getString("Komplikasi"),rs.getString("Prognosis"),rs.getString("Alternatif"),rs.getString("Kemungkinan"),rs.getString("Kemungkinan1"),
                        rs.getString("hubungan"),rs.getString("Nama"),rs.getString("Umur"),rs.getString("Alamat"),rs.getString("acc_ic"),rs.getString("no_surat")
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
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        DiagnosaKerja.setText("-");
        DiagnosaBanding.setText("-");
        KondisiPasien.setText("-");
        TindakanKedokteran.setText("-");
        TataCara.setSelectedIndex(0);
        Tujuan.setText("-");
        Komplikasi.setText("-");
        Prognosis.setSelectedIndex(0);
        Alternatif.setText("-");
        Kemungkinan.setText("-");
        Kemungkinan1.setText("-");
        Hubungan.setText("");
        Nama.setText("");
        Alamat.setText("");
        Umur.setText("");
        accic.setText("");
        Status.setText("");
        String jenis = JenisRawat.getText();
        String prefix = "";
        String tabelTujuan = "";

        if(jenis.equalsIgnoreCase("Rawat Jalan / IGD")){
            prefix = "ICRJ";
            tabelTujuan = "surat_ic_rajal";
        }else if(jenis.equalsIgnoreCase("Rawat Inap")){
            prefix = "ICRI";
            tabelTujuan = "surat_ic_ranap";
        }

        if(!tabelTujuan.equals("")){
            Valid.autoNomer3(
                "select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from "+tabelTujuan+" where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
                prefix + TanggalSurat.getSelectedItem().toString().substring(6,10) + 
                         TanggalSurat.getSelectedItem().toString().substring(3,5) + 
                         TanggalSurat.getSelectedItem().toString().substring(0,2),
                4, NoSurat
            );
            NoSurat.requestFocus();
        }
    } 
    
    private void autoNumberX(String tglPilih, String jenis) {
        String prefix;
        String tableIC = "surat_ic_rajal"; // ⬅️ sesuai permintaan, selalu surat_ic_rajal

        if ("ralan".equalsIgnoreCase(jenis)) {
            prefix = "ICRJ";
        } else if ("ranap".equalsIgnoreCase(jenis)) {
            prefix = "ICRI";
        } else {
            // fallback default
            prefix = "ICRJ";
        }

        String tgl = TanggalSurat.getSelectedItem().toString();
        String nomorPrefix = prefix 
                + tgl.substring(6,10)  // tahun
                + tgl.substring(3,5)   // bulan
                + tgl.substring(0,2);  // hari

        Valid.autoNomer3(
            "SELECT IFNULL(MAX(CONVERT(RIGHT(no_surat,4),SIGNED)),0) "
            + "FROM " + tableIC + " "
            + "WHERE tanggal_surat='" + Valid.SetTgl(tgl+"") + "'",
            nomorPrefix, 4, NoSurat
        );
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());  
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());  
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Valid.SetTgl(TanggalSurat,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            KodeDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());  
            NamaDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            KodeDokter1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());  
            NamaDokter1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KodeDokter2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());  
            NamaDokter2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Status.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            DiagnosaKerja.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            DiagnosaBanding.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            KondisiPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            TindakanKedokteran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            TataCara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Tujuan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Komplikasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Prognosis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Alternatif.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Kemungkinan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Kemungkinan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Nama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Alamat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            accic.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            panggilPhoto();
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt, Date tgl2, String jenis) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        Sequel.cariIsi("SELECT petugas.nip from pemeriksaan_ralan inner join petugas on petugas.nip=pemeriksaan_ralan.nik WHERE pemeriksaan_ralan.no_rawat='"+norwt+"'", KodeDokter2);
        Sequel.cariIsi("SELECT petugas.nama from pemeriksaan_ralan INNER JOIN petugas on petugas.nip=pemeriksaan_ralan.nik WHERE pemeriksaan_ralan.no_rawat='"+norwt+"'", NamaDokter2);
        DTPCari2.setDate(tgl2);    
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();

        // Simpan jenis rawat agar bisa dipakai di tampil()
        autoNumberX(Valid.SetTgl(tgl2+""), jenis);
        this.JenisRawat.setText(jenis.equalsIgnoreCase("ralan") ? "Rawat Jalan / IGD" : "Rawat Inap");
        this.jenisRawat = jenis; // variabel global (String jenisRawat) di RMDataIC
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-240));
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
        BtnSimpan.setEnabled(akses.getdata_resume_pasien());
        BtnHapus.setEnabled(akses.getdata_resume_pasien());
        BtnEdit.setEnabled(akses.getdata_resume_pasien());
        BtnPrint.setEnabled(akses.getdata_resume_pasien()); 
        
        String jenis = JenisRawat.getText().toLowerCase();
        String tableIC, prefix;

        if (jenis.contains("Rawat Jalan / IGD")) {
            tableIC = "surat_ic_rajal";
            prefix = "ICRJ";
        } else if (jenis.contains("Rawat Inap")) {
            tableIC = "surat_ic_ranap";
            prefix = "ICRI";
        } else {
            // default jika tidak sesuai
            tableIC = "surat_ic_rajal";
            prefix = "ICRJ";
        }

        String tgl = TanggalSurat.getSelectedItem().toString();
        String nomorPrefix = prefix 
                + tgl.substring(6,10)  // tahun
                + tgl.substring(3,5)   // bulan
                + tgl.substring(0,2);  // hari

        Valid.autoNomer3(
            "SELECT IFNULL(MAX(CONVERT(RIGHT(no_surat,4),SIGNED)),0) "
            + "FROM " + tableIC + " "
            + "WHERE tanggal_surat='" + Valid.SetTgl(tgl+"") + "'",
            nomorPrefix, 4, NoSurat
        );
        if(akses.getjml2()>=1){
            KodeDokter1.setEditable(false);
//            BtnDokter1.setEnabled(false);
            KodeDokter1.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", NamaDokter1,KodeDokter1.getText());
            KodeDokter.setEditable(false);
//            BtnDokter.setEnabled(false);
            KodeDokter.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", NamaDokter,KodeDokter.getText());

        }            
    }
    
    private void panggilPhoto() {
        String tableIC;
        if ("ralan".equalsIgnoreCase(jenisRawat)) {
            tableIC = "surat_ic_rajal";
        } else if ("ranap".equalsIgnoreCase(jenisRawat)) {
            tableIC = "surat_ic_ranap";
        } else {
            tableIC = "surat_ic_rajal"; // default
        }

        try {
            ps = koneksi.prepareStatement(
                "SELECT tte FROM " + tableIC + " WHERE no_surat=?"
            );
            ps.setString(1, NoSurat.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                String tte = rs.getString("tte");
                if (tte == null || tte.equals("") || tte.equals("-")) {
                    LoadHTML.setText("<html><body><center><br><br>"
                        + "<font face='tahoma' size='2' color='#434343'>Kosong</font>"
                        + "</center></body></html>");
                } else {
                    LoadHTML.setText("<html><body><center>"
                        + "<img src='http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + tte 
                        + "' alt='photo' width='300' height='280'/>"
                        + "</center></body></html>");
                }
            } else {
                LoadHTML.setText("<html><body><center><br><br>"
                    + "<font face='tahoma' size='2' color='#434343'>Kosong</font>"
                    + "</center></body></html>");
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {}
        }
    }
  
}
