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
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPegawai;
import laporan.DlgBerkasRawat;
import laporan.DlgDiagnosaPenyakit;


/**
 *
 * @author perpustakaan
 */
public final class RMDataICRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
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
    public RMDataICRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","No Surat","Tanggal","Jam","Kode Dokter Pelaksana Tindakan","Dokter Pelaksana Tindakan",
            "Kode Pemberi Informasi","Nama Pemberi Informasi","Kode Saksi1/Perawat","Nama Saksi1/Perawat","Status Informed Consent","Informasi 1","Informasi 2",
            "Informasi 3","Informasi 4","Informasi 5","Informasi 6","Informasi 7","Informasi 8","Informasi 9","Informasi 10","Informasi 11"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
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
                    Informasi1.append("( "+ rmcaridiagnosa1.getTable().getValueAt(rmcaridiagnosa1.getTable().getSelectedRow(),0).toString()+") ");
                    Informasi1.append(rmcaridiagnosa1.getTable().getValueAt(rmcaridiagnosa1.getTable().getSelectedRow(),1).toString()+", ");
                    Informasi1.requestFocus();
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
                    Informasi1.append(carikeluhan.getTable().getValueAt(carikeluhan.getTable().getSelectedRow(),2).toString()+", ");
                    Informasi1.requestFocus();
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
                    Informasi1.append(carikeluhanass.getTable().getValueAt(carikeluhanass.getTable().getSelectedRow(),2).toString()+", ");
                    Informasi1.requestFocus();
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
        Informasi1 = new widget.TextArea();
        jLabel5 = new widget.Label();
        label14 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel12 = new widget.Label();
        IC = new widget.ComboBox();
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
        scrollPane6 = new widget.ScrollPane();
        Informasi2 = new widget.TextArea();
        jLabel18 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        Informasi3 = new widget.TextArea();
        jLabel20 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        Informasi4 = new widget.TextArea();
        jLabel22 = new widget.Label();
        Informasi5 = new widget.ComboBox();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Informasi6 = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        Informasi7 = new widget.TextArea();
        jLabel33 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        Informasi8 = new widget.TextArea();
        jLabel34 = new widget.Label();
        jLabel25 = new widget.Label();
        Informasi9 = new widget.ComboBox();
        jLabel26 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        Informasi10 = new widget.TextArea();
        jLabel35 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Informasi11 = new widget.TextArea();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLaporanResume.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResume.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResume.setText("Informed Consent Rawat Inap");
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Informed Consent Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-03-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-03-2022" }));
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

        Informasi1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Informasi1.setColumns(20);
        Informasi1.setRows(5);
        Informasi1.setName("Informasi1"); // NOI18N
        Informasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi1KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Informasi1);

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
        jLabel12.setBounds(630, 100, 130, 23);

        IC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PERSETUJUAN TINDAKAN MEDIS", "PENOLAKAN TINDAKAN MEDIS" }));
        IC.setName("IC"); // NOI18N
        IC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ICActionPerformed(evt);
            }
        });
        IC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ICKeyPressed(evt);
            }
        });
        FormInput.add(IC);
        IC.setBounds(770, 100, 190, 23);

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

        jLabel14.setText("1. Diagnosa (WD & DD) :");
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
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-03-2022" }));
        TanggalSurat.setDisplayFormat("dd-MM-yyyy");
        TanggalSurat.setName("TanggalSurat"); // NOI18N
        TanggalSurat.setOpaque(false);
        TanggalSurat.setPreferredSize(new java.awt.Dimension(141, 18));
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
        jLabel11.setBounds(650, 70, 70, 23);

        NoSurat.setEditable(false);
        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(730, 70, 170, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Informasi2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Informasi2.setColumns(20);
        Informasi2.setRows(5);
        Informasi2.setName("Informasi2"); // NOI18N
        Informasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi2KeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Informasi2);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(250, 200, 541, 50);

        jLabel18.setText("2. Dasar Diagnosis :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(10, 200, 240, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Informasi3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Informasi3.setColumns(20);
        Informasi3.setRows(5);
        Informasi3.setName("Informasi3"); // NOI18N
        Informasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi3KeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(Informasi3);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(250, 260, 541, 50);

        jLabel20.setText("3. Tindakan Medis :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(10, 260, 240, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Informasi4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Informasi4.setColumns(20);
        Informasi4.setRows(5);
        Informasi4.setName("Informasi4"); // NOI18N
        Informasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi4KeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(Informasi4);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(250, 320, 541, 50);

        jLabel22.setText("Singkat Prosedur Dan Tahapan Yang Penting)");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(10, 400, 240, 23);

        Informasi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Sedasi", "Non Sedasi" }));
        Informasi5.setName("Informasi5"); // NOI18N
        Informasi5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Informasi5ActionPerformed(evt);
            }
        });
        Informasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi5KeyPressed(evt);
            }
        });
        FormInput.add(Informasi5);
        Informasi5.setBounds(250, 380, 170, 23);

        jLabel23.setText("4. Indikasi Tindakan :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(10, 320, 240, 23);

        jLabel24.setText("5. Tata Cara (Tipe Sedasi/Anestesi. Uraian :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(10, 380, 240, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Informasi6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Informasi6.setColumns(20);
        Informasi6.setRows(5);
        Informasi6.setName("Informasi6"); // NOI18N
        Informasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi6KeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Informasi6);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(250, 430, 541, 50);

        jLabel32.setText("6. Tujuan :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(10, 430, 240, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        Informasi7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Informasi7.setColumns(20);
        Informasi7.setRows(5);
        Informasi7.setName("Informasi7"); // NOI18N
        Informasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi7KeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(Informasi7);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(250, 490, 541, 50);

        jLabel33.setText("7. Risiko :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(10, 490, 240, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        Informasi8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Informasi8.setColumns(20);
        Informasi8.setRows(5);
        Informasi8.setName("Informasi8"); // NOI18N
        Informasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi8KeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(Informasi8);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(250, 550, 541, 50);

        jLabel34.setText("8. Komplikasi :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(10, 550, 240, 23);

        jLabel25.setText("Fungsi Dan Prognosis Kesembuhan)");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(10, 630, 240, 23);

        Informasi9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Baik", "Dubia et bonam/cenderung", "Dubia et malam/cenderung", "Tidak baik" }));
        Informasi9.setName("Informasi9"); // NOI18N
        Informasi9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Informasi9ActionPerformed(evt);
            }
        });
        Informasi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi9KeyPressed(evt);
            }
        });
        FormInput.add(Informasi9);
        Informasi9.setBounds(250, 610, 230, 23);

        jLabel26.setText("9. Prognosis (Prognosis Vital, Prognosis:");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(10, 610, 240, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Informasi10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Informasi10.setColumns(20);
        Informasi10.setRows(5);
        Informasi10.setName("Informasi10"); // NOI18N
        Informasi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi10KeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Informasi10);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(250, 660, 541, 50);

        jLabel35.setText("10. Alternatif & Risiko :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(10, 660, 240, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Informasi11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Informasi11.setColumns(20);
        Informasi11.setRows(5);
        Informasi11.setName("Informasi11"); // NOI18N
        Informasi11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi11KeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Informasi11);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(250, 720, 541, 50);

        jLabel36.setText("Konsultasi Selama Tindakan Resusitasi");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(10, 760, 240, 23);

        jLabel37.setText("11. Hal Lain Yang Akan Dilakukan Untuk :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(10, 720, 240, 23);

        jLabel38.setText("Menyelamatkan Pasien Perluasan Tindakan");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(10, 740, 240, 23);

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
        }else if(KodeDokter2.getText().equals("")||NamaDokter1.getText().equals("")){
            Valid.textKosong(BtnDokter2,"Saksi 1/Perawat");
        }else if(Informasi1.getText().equals("")){
            Valid.textKosong(Informasi1,"Informasi 1");
        }else if(Informasi2.getText().equals("")){
            Valid.textKosong(Informasi2,"Informasi 2");
        }else if(Informasi3.getText().equals("")){
            Valid.textKosong(Informasi3,"Informasi 3");
        }else if(Informasi4.getText().equals("")){
            Valid.textKosong(Informasi4,"Informasi 4");
        }else if(Informasi6.getText().equals("")){
            Valid.textKosong(Informasi6,"Informasi 6");
        }else if(Informasi7.getText().equals("")){
            Valid.textKosong(Informasi7,"Informasi 7");
        }else if(Informasi8.getText().equals("")){
            Valid.textKosong(Informasi8,"Informasi 8");
        }else if(Informasi10.getText().equals("")){
            Valid.textKosong(Informasi10,"Informasi 10");
        }else if(Informasi11.getText().equals("")){
            Valid.textKosong(Informasi11,"Informasi 11");
        }else{
            if(Sequel.menyimpantf("surat_ic_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Surat",19,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),KodeDokter.getText(),KodeDokter1.getText(),KodeDokter2.getText(),
                    IC.getSelectedItem().toString(),Informasi1.getText(),Informasi2.getText(),Informasi3.getText(),Informasi4.getText(),Informasi5.getSelectedItem().toString(),
                    Informasi6.getText(),Informasi7.getText(),Informasi8.getText(),Informasi9.getSelectedItem().toString(),Informasi10.getText(),Informasi11.getText()
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
            Valid.pindah(evt,Informasi1,BtnBatal);
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
            if(Sequel.queryu2tf("delete from surat_ic_ranap where no_surat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()
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
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodeDokter.getText().equals("")||NamaDokter.getText().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Pelaksana Tindakan");
        }else if(KodeDokter1.getText().equals("")||NamaDokter1.getText().equals("")){
            Valid.textKosong(BtnDokter1,"Pemberi Informasi");
        }else if(KodeDokter2.getText().equals("")||NamaDokter1.getText().equals("")){
            Valid.textKosong(BtnDokter2,"Saksi 1/Perawat");
        }else if(Informasi1.getText().equals("")){
            Valid.textKosong(Informasi1,"Informasi 1");
        }else if(Informasi2.getText().equals("")){
            Valid.textKosong(Informasi2,"Informasi 2");
        }else if(Informasi3.getText().equals("")){
            Valid.textKosong(Informasi3,"Informasi 3");
        }else if(Informasi4.getText().equals("")){
            Valid.textKosong(Informasi4,"Informasi 4");
        }else if(Informasi6.getText().equals("")){
            Valid.textKosong(Informasi6,"Informasi 6");
        }else if(Informasi7.getText().equals("")){
            Valid.textKosong(Informasi7,"Informasi 7");
        }else if(Informasi8.getText().equals("")){
            Valid.textKosong(Informasi8,"Informasi 8");
        }else if(Informasi10.getText().equals("")){
            Valid.textKosong(Informasi10,"Informasi 10");
        }else if(Informasi11.getText().equals("")){
            Valid.textKosong(Informasi11,"Informasi 11");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("surat_ic_ranap","no_surat=?","no_surat=?,no_rawat=?,tanggal_surat=?,jam=?,kd_dokter=?,nik=?,nik1=?,status_ic=?,informasi1=?,informasi2=?,informasi3=?,informasi4=?,informasi5=?,informasi6=?,informasi7=?,informasi8=?,informasi9=?,informasi10=?,informasi11=?",20,new String[]{
                        NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),KodeDokter.getText(),KodeDokter1.getText(),KodeDokter2.getText(),
                        IC.getSelectedItem().toString(),Informasi1.getText(),Informasi2.getText(),Informasi3.getText(),Informasi4.getText(),Informasi5.getSelectedItem().toString(),
                        Informasi6.getText(),Informasi7.getText(),Informasi8.getText(),Informasi9.getSelectedItem().toString(),Informasi10.getText(),Informasi11.getText(),
//                        tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                        NoSurat.getText()
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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
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
                    Valid.MyReportqry("rptDataResumePasien.jasper","report","::[ Data Resume Pasien ]::",
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                        "surat_ic_ranap.kd_dokter,dokter.nm_dokter,surat_ic_ranap.kondisi_pulang,surat_ic_ranap.keluhan_utama,surat_ic_ranap.jalannya_penyakit, "+
                        "surat_ic_ranap.pemeriksaan_penunjang,surat_ic_ranap.hasil_laborat,surat_ic_ranap.diagnosa_utama,surat_ic_ranap.kd_diagnosa_utama, "+
                        "surat_ic_ranap.diagnosa_sekunder,surat_ic_ranap.kd_diagnosa_sekunder,surat_ic_ranap.diagnosa_sekunder2,surat_ic_ranap.kd_diagnosa_sekunder2, "+
                        "surat_ic_ranap.diagnosa_sekunder3,surat_ic_ranap.kd_diagnosa_sekunder3,surat_ic_ranap.diagnosa_sekunder4,surat_ic_ranap.kd_diagnosa_sekunder4, "+
                        "surat_ic_ranap.prosedur_utama,surat_ic_ranap.kd_prosedur_utama,surat_ic_ranap.prosedur_sekunder,surat_ic_ranap.kd_prosedur_sekunder, "+
                        "surat_ic_ranap.prosedur_sekunder2,surat_ic_ranap.kd_prosedur_sekunder2,surat_ic_ranap.prosedur_sekunder3,surat_ic_ranap.kd_prosedur_sekunder3, "+
                        "surat_ic_ranap.obat_pulang,surat_ic_ranap.tindak_lanjut,surat_ic_ranap.asal_pasien,surat_ic_ranap.tindakan from surat_ic_ranap inner join reg_periksa on surat_ic_ranap.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_ic_ranap.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
                }else{
                    Valid.MyReportqry("rptDataResumePasien.jasper","report","::[ Data Resume Pasien ]::",
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                        "surat_ic_ranap.kd_dokter,dokter.nm_dokter,surat_ic_ranap.kondisi_pulang,surat_ic_ranap.keluhan_utama,surat_ic_ranap.jalannya_penyakit, "+
                        "surat_ic_ranap.pemeriksaan_penunjang,surat_ic_ranap.hasil_laborat,surat_ic_ranap.diagnosa_utama,surat_ic_ranap.kd_diagnosa_utama, "+
                        "surat_ic_ranap.diagnosa_sekunder,surat_ic_ranap.kd_diagnosa_sekunder,surat_ic_ranap.diagnosa_sekunder2,surat_ic_ranap.kd_diagnosa_sekunder2, "+
                        "surat_ic_ranap.diagnosa_sekunder3,surat_ic_ranap.kd_diagnosa_sekunder3,surat_ic_ranap.diagnosa_sekunder4,surat_ic_ranap.kd_diagnosa_sekunder4, "+
                        "surat_ic_ranap.prosedur_utama,surat_ic_ranap.kd_prosedur_utama,surat_ic_ranap.prosedur_sekunder,surat_ic_ranap.kd_prosedur_sekunder, "+
                        "surat_ic_ranap.prosedur_sekunder2,surat_ic_ranap.kd_prosedur_sekunder2,surat_ic_ranap.prosedur_sekunder3,surat_ic_ranap.kd_prosedur_sekunder3, "+
                        "surat_ic_ranap.obat_pulang,surat_ic_ranap.tindak_lanjut,surat_ic_ranap.asal_pasien,surat_ic_ranap.tindakan from surat_ic_ranap inner join reg_periksa on surat_ic_ranap.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_ic_ranap.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.status_lanjut like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_ranap.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_ranap.kondisi_pulang like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_ranap.kd_diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_ranap.diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_ranap.prosedur_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_ranap.kd_prosedur_utama like '%"+TCari.getText().trim()+"%' "+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
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
        Valid.pindah(evt,TCari,IC);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
       Valid.pindah(evt,TCari,IC);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void Informasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi1KeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            if(evt.isShiftDown()){
//                JalannyaPenyakit.requestFocus();
//            }
//        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
//            Kondisi.requestFocus();
//        }
    }//GEN-LAST:event_Informasi1KeyPressed

    private void MnLaporanResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanResumeActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            param.put("norawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            param.put("nosurat",tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())); 
//            if(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString().equals("Ralan")){
//                param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
//                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
//            }else{
//                param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
//                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
//            }
            Valid.MyReport("rptLaporanICRanap.jasper","report","::[ Informed Consent Rawat Inap ]::",param);
        }
    }//GEN-LAST:event_MnLaporanResumeActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void ICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ICActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ICActionPerformed

    private void ICKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ICKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ICKeyPressed

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
        Valid.pindah(evt,NoSurat,IC);
    }//GEN-LAST:event_TanggalSuratKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,TCari,IC);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void Informasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi2KeyPressed

    private void Informasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi3KeyPressed

    private void Informasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi4KeyPressed

    private void Informasi5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Informasi5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi5ActionPerformed

    private void Informasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi5KeyPressed

    private void Informasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi6KeyPressed

    private void Informasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi7KeyPressed

    private void Informasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi8KeyPressed

    private void Informasi9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Informasi9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi9ActionPerformed

    private void Informasi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi9KeyPressed

    private void Informasi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi10KeyPressed

    private void Informasi11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi11KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataICRanap dialog = new RMDataICRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox IC;
    private widget.TextArea Informasi1;
    private widget.TextArea Informasi10;
    private widget.TextArea Informasi11;
    private widget.TextArea Informasi2;
    private widget.TextArea Informasi3;
    private widget.TextArea Informasi4;
    private widget.ComboBox Informasi5;
    private widget.TextArea Informasi6;
    private widget.TextArea Informasi7;
    private widget.TextArea Informasi8;
    private widget.ComboBox Informasi9;
    private widget.TextBox KodeDokter;
    private widget.TextBox KodeDokter1;
    private widget.TextBox KodeDokter2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox NamaDokter;
    private widget.TextBox NamaDokter1;
    private widget.TextBox NamaDokter2;
    private widget.TextBox NoSurat;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalSurat;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
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
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "surat_ic_ranap.no_surat,surat_ic_ranap.tanggal_surat,surat_ic_ranap.jam,surat_ic_ranap.kd_dokter,dokter.nm_dokter, " +
                    "surat_ic_ranap.nik,surat_ic_ranap.nik,nama1.nama as nama1,surat_ic_ranap.nik1,nama2.nama as nama2,surat_ic_ranap.status_ic, "+
                    "surat_ic_ranap.informasi1,surat_ic_ranap.informasi2,surat_ic_ranap.informasi3,surat_ic_ranap.informasi4,surat_ic_ranap.informasi5, "+
                    "surat_ic_ranap.informasi6,surat_ic_ranap.informasi7,surat_ic_ranap.informasi8,surat_ic_ranap.informasi9,surat_ic_ranap.informasi10, "+
                    "surat_ic_ranap.informasi11 from surat_ic_ranap inner join reg_periksa on surat_ic_ranap.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_ic_ranap.kd_dokter=dokter.kd_dokter "+
                    "inner join pegawai as nama1 on surat_ic_ranap.nik=nama1.nik "+
                    "inner join pegawai as nama2 on surat_ic_ranap.nik1=nama2.nik "+
                    "where surat_ic_ranap.tanggal_surat between ? and ? order by surat_ic_ranap.tanggal_surat");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "surat_ic_ranap.no_surat,surat_ic_ranap.tanggal_surat,surat_ic_ranap.jam,surat_ic_ranap.kd_dokter,dokter.nm_dokter, " +
                    "surat_ic_ranap.nik,surat_ic_ranap.nik,nama1.nama as nama1,surat_ic_ranap.nik1,nama2.nama as nama2,surat_ic_ranap.status_ic, "+
                    "surat_ic_ranap.informasi1,surat_ic_ranap.informasi2,surat_ic_ranap.informasi3,surat_ic_ranap.informasi4,surat_ic_ranap.informasi5, "+
                    "surat_ic_ranap.informasi6,surat_ic_ranap.informasi7,surat_ic_ranap.informasi8,surat_ic_ranap.informasi9,surat_ic_ranap.informasi10, "+
                    "surat_ic_ranap.informasi11 from surat_ic_ranap inner join reg_periksa on surat_ic_ranap.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_ic_ranap.kd_dokter=dokter.kd_dokter "+
                    "inner join pegawai as nama1 on surat_ic_ranap.nik=nama1.nik "+
                    "inner join pegawai as nama2 on surat_ic_ranap.nik1=nama2.nik "+
                    "where surat_ic_ranap.tanggal_surat between ? and ? and surat_ic_ranap.status_ic like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and surat_ic_ranap.informasi2 like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and surat_ic_ranap.no_surat like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and pasien.nm_pasien like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and surat_ic_ranap.kd_dokter like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and dokter.nm_dokter like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and surat_ic_ranap.nik like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and surat_ic_ranap.nik1 like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and surat_ic_ranap.informasi4 like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and surat_ic_ranap.informasi9 like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and surat_ic_ranap.informasi1 like ? or "+
                    "surat_ic_ranap.tanggal_surat between ? and ? and surat_ic_ranap.no_rawat like ? "+
                    "order by surat_ic_ranap.tanggal_surat");
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
                    ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(21,"%"+TCari.getText()+"%");
                    ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(24,"%"+TCari.getText()+"%");
                    ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(27,"%"+TCari.getText()+"%");
                    ps.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(30,"%"+TCari.getText()+"%");
                    ps.setString(31,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(33,"%"+TCari.getText()+"%");
                    ps.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(36,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("no_surat"),rs.getString("tanggal_surat"),
                        rs.getString("jam"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("nik"),rs.getString("nama1"),rs.getString("nik1"),rs.getString("nama2"),
                        rs.getString("status_ic"),rs.getString("informasi1"),rs.getString("informasi2"),rs.getString("informasi3"),rs.getString("informasi4"),rs.getString("informasi5"),
                        rs.getString("informasi6"),rs.getString("informasi7"),rs.getString("informasi8"),rs.getString("informasi9"),rs.getString("informasi10"),rs.getString("informasi11")
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
        Informasi1.setText("");
        Informasi2.setText("");
        Informasi3.setText("");
        Informasi4.setText("");
        Informasi5.setSelectedIndex(0);
        Informasi6.setText("");
        Informasi7.setText("");
        Informasi8.setText("");
        Informasi9.setSelectedIndex(0);
        Informasi10.setText("");
        Informasi11.setText("");
//        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0) from surat_ic_ranap where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
//                "ICRJ"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),3,NoSurat); 
        NoSurat.requestFocus();
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
            IC.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());  
            Informasi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Informasi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Informasi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Informasi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Informasi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Informasi6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Informasi7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Informasi8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Informasi9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Informasi10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Informasi11.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);    
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
//        Kondisi.requestFocus();
//        try {
//            ps=koneksi.prepareStatement(
//                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
//                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
//                    "where diagnosa_pasien.no_rawat=? order by diagnosa_pasien.prioritas ");
//            try {
//                ps.setString(1,norwt);
//                rs=ps.executeQuery();
//                while(rs.next()){
//                    if(rs.getInt("prioritas")==1){
//                        KodeDiagnosaUtama.setText(rs.getString("kd_penyakit"));
//                        DiagnosaUtama.setText(rs.getString("nm_penyakit"));
//                    }
//                    
//                    if(rs.getInt("prioritas")==2){
//                        KodeDiagnosaSekunder1.setText(rs.getString("kd_penyakit"));
//                        DiagnosaSekunder1.setText(rs.getString("nm_penyakit"));
//                    }
//                    
//                    if(rs.getInt("prioritas")==3){
//                        KodeDiagnosaSekunder2.setText(rs.getString("kd_penyakit"));
//                        DiagnosaSekunder2.setText(rs.getString("nm_penyakit"));
//                    }
//                    
//                    if(rs.getInt("prioritas")==4){
//                        KodeDiagnosaSekunder3.setText(rs.getString("kd_penyakit"));
//                        DiagnosaSekunder3.setText(rs.getString("nm_penyakit"));
//                    }
//                    
//                    if(rs.getInt("prioritas")==5){
//                        KodeDiagnosaSekunder4.setText(rs.getString("kd_penyakit"));
//                        DiagnosaSekunder4.setText(rs.getString("nm_penyakit"));
//                    }
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
//        } catch (Exception e) {
//            System.out.println("Notif : "+e);
//        } 
//        
//        try {
//            ps=koneksi.prepareStatement(
//                    "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.prioritas "+
//                    "from prosedur_pasien inner join icd9 on prosedur_pasien.kode=icd9.kode "+
//                    "where prosedur_pasien.no_rawat=? order by prosedur_pasien.prioritas ");
//            try {
//                ps.setString(1,norwt);
//                rs=ps.executeQuery();
//                while(rs.next()){
//                    if(rs.getInt("prioritas")==1){
//                        KodeProsedurUtama.setText(rs.getString("kode"));
//                        ProsedurUtama.setText(rs.getString("deskripsi_panjang"));
//                    }
//                    
//                    if(rs.getInt("prioritas")==2){
//                        KodeProsedurSekunder1.setText(rs.getString("kode"));
//                        ProsedurSekunder1.setText(rs.getString("deskripsi_panjang"));
//                    }
//                    
//                    if(rs.getInt("prioritas")==3){
//                        KodeProsedurSekunder2.setText(rs.getString("kode"));
//                        ProsedurSekunder2.setText(rs.getString("deskripsi_panjang"));
//                    }
//                    
//                    if(rs.getInt("prioritas")==4){
//                        KodeProsedurSekunder3.setText(rs.getString("kode"));
//                        ProsedurSekunder3.setText(rs.getString("deskripsi_panjang"));
//                    }
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
//        } catch (Exception e) {
//            System.out.println("Notif : "+e);
//        } 
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-122));
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
//        MnInputDiagnosa.setEnabled(akses.getdiagnosa_pasien());   
//        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());   
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0) from surat_ic_ranap where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
                "ICRI"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),4,NoSurat); 
        if(akses.getjml2()>=1){
            KodeDokter1.setEditable(false);
            BtnDokter1.setEnabled(false);
            KodeDokter1.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", NamaDokter1,KodeDokter1.getText());
//            if(NmPetugas.getText().equals("")){
//                KdPetugas.setText("");
//                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
//            }
        }            
    }

    
}
