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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Timer;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPegawai;
import laporan.DlgBerkasRawat;
import laporan.DlgDiagnosaPenyakit;
import rekammedis.RMCariEdukasiDokter;
import rekammedis.RMCariEdukasiPerawat;
import rekammedis.RMCariEdukasiFarmasi;
import rekammedis.RMCariEdukasiNutrisionis;
import rekammedis.RMCariEdukasiRehabMedik;
import rekammedis.RMCariJumlahObat;


/**
 *
 * @author perpustakaan
 */
public final class RMPemberianObatRanap extends javax.swing.JDialog {
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
    private RMCariJumlahObatDosis cariobatdosis=new RMCariJumlahObatDosis(null,false);
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
    private RMCariEdukasiDokter cariedukasidokter=new RMCariEdukasiDokter(null,false);
    private RMCariEdukasiPerawat cariedukasiperawat=new RMCariEdukasiPerawat(null,false);
    private RMCariEdukasiFarmasi cariedukasifarmasi=new RMCariEdukasiFarmasi(null,false);
    private RMCariEdukasiNutrisionis cariedukasinutrisionis=new RMCariEdukasiNutrisionis(null,false);
    private RMCariEdukasiRehabMedik cariedukasirehabmedik=new RMCariEdukasiRehabMedik(null,false);
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPemberianObatRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        pagi.setSize(710,200);
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","No Surat","Tanggal Pembuatan","Jam Pembuatan","Tanggal Pemberian","Jam Pemberian","Kode Pengkaji","Nama Pengkaji",
            "Obat 1","Dosis 1","Rute 1","Obat 2","Dosis 2","Rute 2","Obat 3","Dosis 3","Rute 3","Obat 4","Dosis 4","Rute 4","Obat 5","Dosis 5","Rute 5",
            "Obat 6","Dosis 6","Rute 6","Obat 7","Dosis 7","Rute 7","Obat 8","Dosis 8","Rute 8","Obat 9","Dosis 9","Rute 9","Obat 10","Dosis 10","Rute 10",
            "Obat 11","Dosis 11","Rute 11","Obat 12","Dosis 12","Rute 12","Obat 13","Dosis 13","Rute 13","Obat 14","Dosis 14","Rute 14","Obat 15","Dosis 15","Rute 15",
            "Hubungan Dengan Pasien","Kebutuhan Pendapat Kedua","ACC Edukasi","Waktu"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 59; i++) {
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
                column.setPreferredWidth(40);
            }else if(i==27){
                column.setPreferredWidth(105);
            }else if(i==28){
                column.setPreferredWidth(65);
            }else if(i==29){
                column.setPreferredWidth(150);
            }else if(i==30){
                column.setPreferredWidth(90);
            }else if(i==31){
                column.setPreferredWidth(150);
            }else if(i==32){
                column.setPreferredWidth(60);
            }else if(i==33){
                column.setPreferredWidth(250);
            }else if(i==34){
                column.setPreferredWidth(250);
            }else if(i==35){
                column.setPreferredWidth(250);
            }else if(i==36){
                column.setPreferredWidth(250);
            }else if(i==37){
                column.setPreferredWidth(170);
            }else if(i==38){
                column.setPreferredWidth(75);
            }else if(i==39){
                column.setPreferredWidth(170);
            }else if(i==40){
                column.setPreferredWidth(75);
            }else if(i==41){
                column.setPreferredWidth(170);
            }else if(i==42){
                column.setPreferredWidth(75);
            }else if(i==43){
                column.setPreferredWidth(170);
            }else if(i==44){
                column.setPreferredWidth(75);
            }else if(i==45){
                column.setPreferredWidth(170);
            }else if(i==46){
                column.setPreferredWidth(75);
            }else if(i==47){
                column.setPreferredWidth(170);
            }else if(i==48){
                column.setPreferredWidth(75);
            }else if(i==49){
                column.setPreferredWidth(75);
            }else if(i==50){
                column.setPreferredWidth(75);
            }else if(i==51){
                column.setPreferredWidth(40);
            }else if(i==52){
                column.setPreferredWidth(105);
            }else if(i==53){
                column.setPreferredWidth(65);
            }else if(i==54){
                column.setPreferredWidth(150);
            }else if(i==55){
                column.setPreferredWidth(90);
            }else if(i==56){
                column.setPreferredWidth(150);
            }else if(i==57){
                column.setPreferredWidth(60);
            }else if(i==58){
                column.setPreferredWidth(60);
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("RMPemberianObatRanap")){
                    if(pegawai.getTable().getSelectedRow()!= -1){   
//                        KdPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
//                        TPegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
//                        Jabatan.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),3).toString());
//                        KdPeg.requestFocus();  
                        KodeDokter.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        NamaDokter.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                        KodeDokter.requestFocus();
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
        
        cariobatdosis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariobatdosis.getTable().getSelectedRow()!= -1){ 
                    if(i==1){
                        Obat1.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis1.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }else if(i==2){
                        Obat2.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis2.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }else if(i==3){
                        Obat3.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis3.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==4){
                        Obat4.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis4.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==5){
                        Obat5.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis5.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==6){
                        Obat6.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis6.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==7){
                        Obat7.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis7.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==8){
                        Obat8.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis8.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==9){
                        Obat9.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis9.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==10){
                        Obat10.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis10.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==11){
                        Obat11.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis11.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==12){
                        Obat12.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis12.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==13){
                        Obat13.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis13.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==14){
                        Obat14.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis14.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                    }
                    else if(i==15){
                        Obat15.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis15.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
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
        jam();
      
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
        pagi = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn1 = new widget.Button();
        BtnSimpan = new widget.Button();
        label14 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel11 = new widget.Label();
        NoSurat = new widget.TextBox();
        jLabel9 = new widget.Label();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnBatal1 = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        jCBPagi = new javax.swing.JCheckBox();
        jCBSiang = new javax.swing.JCheckBox();
        jCBSore = new javax.swing.JCheckBox();
        jCBMalam = new javax.swing.JCheckBox();
        label15 = new widget.Label();
        BtnEdit1 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        btnAmbilPetugas = new widget.Button();
        BtnBatal = new widget.Button();
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
        jLabel5 = new widget.Label();
        jLabel13 = new widget.Label();
        TanggalSurat = new widget.Tanggal();
        jLabel15 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel27 = new widget.Label();
        accep = new widget.TextBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        jSeparator1 = new javax.swing.JSeparator();
        Dosis1 = new widget.TextBox();
        Obat1 = new widget.TextBox();
        Rute1 = new widget.ComboBox();
        jLabel61 = new widget.Label();
        jLabel8 = new widget.Label();
        DTPReg = new widget.Tanggal();
        BtnObat1 = new widget.Button();
        jLabel45 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel51 = new widget.Label();
        Dosis2 = new widget.TextBox();
        Obat2 = new widget.TextBox();
        Rute2 = new widget.ComboBox();
        BtnObat2 = new widget.Button();
        jLabel46 = new widget.Label();
        Dosis3 = new widget.TextBox();
        Obat3 = new widget.TextBox();
        Rute3 = new widget.ComboBox();
        BtnObat3 = new widget.Button();
        jLabel47 = new widget.Label();
        Dosis4 = new widget.TextBox();
        Obat4 = new widget.TextBox();
        Rute4 = new widget.ComboBox();
        BtnObat4 = new widget.Button();
        jLabel50 = new widget.Label();
        Dosis5 = new widget.TextBox();
        Obat5 = new widget.TextBox();
        Rute5 = new widget.ComboBox();
        BtnObat5 = new widget.Button();
        jLabel52 = new widget.Label();
        Dosis6 = new widget.TextBox();
        Obat6 = new widget.TextBox();
        Rute6 = new widget.ComboBox();
        BtnObat6 = new widget.Button();
        jLabel53 = new widget.Label();
        Dosis7 = new widget.TextBox();
        Obat7 = new widget.TextBox();
        Rute7 = new widget.ComboBox();
        BtnObat7 = new widget.Button();
        jLabel54 = new widget.Label();
        Dosis8 = new widget.TextBox();
        Obat8 = new widget.TextBox();
        Rute8 = new widget.ComboBox();
        BtnObat8 = new widget.Button();
        jLabel55 = new widget.Label();
        Dosis9 = new widget.TextBox();
        Obat9 = new widget.TextBox();
        Rute9 = new widget.ComboBox();
        BtnObat9 = new widget.Button();
        jLabel56 = new widget.Label();
        Dosis10 = new widget.TextBox();
        Obat10 = new widget.TextBox();
        Rute10 = new widget.ComboBox();
        BtnObat10 = new widget.Button();
        jLabel57 = new widget.Label();
        Dosis11 = new widget.TextBox();
        Obat11 = new widget.TextBox();
        Rute11 = new widget.ComboBox();
        BtnObat11 = new widget.Button();
        jLabel58 = new widget.Label();
        Dosis12 = new widget.TextBox();
        Obat12 = new widget.TextBox();
        Rute12 = new widget.ComboBox();
        BtnObat12 = new widget.Button();
        jLabel59 = new widget.Label();
        Dosis13 = new widget.TextBox();
        Obat13 = new widget.TextBox();
        Rute13 = new widget.ComboBox();
        BtnObat13 = new widget.Button();
        jLabel60 = new widget.Label();
        Dosis14 = new widget.TextBox();
        Obat14 = new widget.TextBox();
        Rute14 = new widget.ComboBox();
        BtnObat14 = new widget.Button();
        jLabel62 = new widget.Label();
        Dosis15 = new widget.TextBox();
        Obat15 = new widget.TextBox();
        Rute15 = new widget.ComboBox();
        BtnObat15 = new widget.Button();
        jLabel63 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        pagi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        pagi.setName("pagi"); // NOI18N
        pagi.setUndecorated(true);
        pagi.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Waktu Pemberian Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(null);

        BtnCloseIn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn1.setMnemonic('U');
        BtnCloseIn1.setText("Tutup");
        BtnCloseIn1.setToolTipText("Alt+U");
        BtnCloseIn1.setName("BtnCloseIn1"); // NOI18N
        BtnCloseIn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn1ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn1);
        BtnCloseIn1.setBounds(510, 140, 100, 30);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan);
        BtnSimpan.setBounds(110, 140, 100, 30);

        label14.setText("Waktu :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        internalFrame5.add(label14);
        label14.setBounds(330, 70, 80, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setName("KodeDokter"); // NOI18N
        KodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterKeyPressed(evt);
            }
        });
        internalFrame5.add(KodeDokter);
        KodeDokter.setBounds(130, 40, 141, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        NamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        internalFrame5.add(NamaDokter);
        NamaDokter.setBounds(270, 40, 270, 23);

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
        internalFrame5.add(BtnDokter);
        BtnDokter.setBounds(540, 40, 28, 23);

        jLabel11.setText("No. Pemberian :");
        jLabel11.setName("jLabel11"); // NOI18N
        internalFrame5.add(jLabel11);
        jLabel11.setBounds(20, 70, 100, 23);

        NoSurat.setEditable(false);
        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoSuratActionPerformed(evt);
            }
        });
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        internalFrame5.add(NoSurat);
        NoSurat.setBounds(130, 70, 170, 23);

        jLabel9.setText("Jam Pemberian :");
        jLabel9.setName("jLabel9"); // NOI18N
        internalFrame5.add(jLabel9);
        jLabel9.setBounds(30, 100, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJamKeyPressed(evt);
            }
        });
        internalFrame5.add(CmbJam);
        CmbJam.setBounds(130, 100, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        CmbMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenitKeyPressed(evt);
            }
        });
        internalFrame5.add(CmbMenit);
        CmbMenit.setBounds(200, 100, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        CmbDetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetikKeyPressed(evt);
            }
        });
        internalFrame5.add(CmbDetik);
        CmbDetik.setBounds(270, 100, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        internalFrame5.add(ChkJln);
        ChkJln.setBounds(330, 100, 23, 23);

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
        internalFrame5.add(BtnHapus);
        BtnHapus.setBounds(410, 140, 100, 30);

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
        internalFrame5.add(BtnEdit);
        BtnEdit.setBounds(310, 140, 100, 30);

        BtnBatal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal1.setMnemonic('B');
        BtnBatal1.setText("Baru");
        BtnBatal1.setToolTipText("Alt+B");
        BtnBatal1.setName("BtnBatal1"); // NOI18N
        BtnBatal1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatal1ActionPerformed(evt);
            }
        });
        BtnBatal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatal1KeyPressed(evt);
            }
        });
        internalFrame5.add(BtnBatal1);
        BtnBatal1.setBounds(210, 140, 100, 30);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnRefreshPhoto1);
        BtnRefreshPhoto1.setBounds(300, 70, 30, 23);

        jCBPagi.setSelected(true);
        jCBPagi.setText("Pagi");
        jCBPagi.setName("jCBPagi"); // NOI18N
        jCBPagi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBPagiActionPerformed(evt);
            }
        });
        internalFrame5.add(jCBPagi);
        jCBPagi.setBounds(420, 70, 90, 23);

        jCBSiang.setSelected(true);
        jCBSiang.setText("Siang");
        jCBSiang.setName("jCBSiang"); // NOI18N
        jCBSiang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBSiangActionPerformed(evt);
            }
        });
        internalFrame5.add(jCBSiang);
        jCBSiang.setBounds(420, 100, 84, 23);

        jCBSore.setSelected(true);
        jCBSore.setText("Sore");
        jCBSore.setName("jCBSore"); // NOI18N
        jCBSore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBSoreActionPerformed(evt);
            }
        });
        internalFrame5.add(jCBSore);
        jCBSore.setBounds(510, 70, 90, 23);

        jCBMalam.setSelected(true);
        jCBMalam.setText("Malam");
        jCBMalam.setName("jCBMalam"); // NOI18N
        jCBMalam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBMalamActionPerformed(evt);
            }
        });
        internalFrame5.add(jCBMalam);
        jCBMalam.setBounds(510, 100, 100, 23);

        label15.setText("Pengkaji :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        internalFrame5.add(label15);
        label15.setBounds(30, 40, 90, 23);

        pagi.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        BtnEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit1.setMnemonic('G');
        BtnEdit1.setText("Ganti");
        BtnEdit1.setToolTipText("Alt+G");
        BtnEdit1.setName("BtnEdit1"); // NOI18N
        BtnEdit1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdit1ActionPerformed(evt);
            }
        });
        BtnEdit1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEdit1KeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Daftar Pemberian Obat Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(50, 50, 50))); // NOI18N
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

        btnAmbilPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbilPetugas.setMnemonic('U');
        btnAmbilPetugas.setText("Petugas");
        btnAmbilPetugas.setToolTipText("Alt+U");
        btnAmbilPetugas.setName("btnAmbilPetugas"); // NOI18N
        btnAmbilPetugas.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbilPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilPetugasActionPerformed(evt);
            }
        });
        panelGlass8.add(btnAmbilPetugas);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2026" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 700));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(140, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(390, 10, 424, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(280, 10, 112, 23);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(30, 10, 100, 23);

        jLabel13.setText("Tanggal Pembuatan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(20, 40, 110, 23);

        TanggalSurat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2026" }));
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
        TanggalSurat.setBounds(140, 40, 90, 23);

        jLabel15.setText("Hubungan Dgn pasien :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(250, 40, 130, 23);

        Hubungan.setEditable(false);
        Hubungan.setHighlighter(null);
        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(390, 40, 270, 23);

        jLabel27.setText("Saya Sudah Membaca, Mengerti dan Menyetujui “Daftar Pemberian Obat”  ");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(240, 80, 380, 20);

        accep.setEditable(false);
        accep.setHighlighter(null);
        accep.setName("accep"); // NOI18N
        accep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                accepKeyPressed(evt);
            }
        });
        FormInput.add(accep);
        accep.setBounds(620, 80, 140, 24);

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
        FormPhoto.setBounds(780, 80, 340, 280);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        Dosis1.setFocusTraversalPolicyProvider(true);
        Dosis1.setName("Dosis1"); // NOI18N
        Dosis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis1KeyPressed(evt);
            }
        });
        FormInput.add(Dosis1);
        Dosis1.setBounds(420, 150, 210, 23);

        Obat1.setFocusTraversalPolicyProvider(true);
        Obat1.setName("Obat1"); // NOI18N
        Obat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat1KeyPressed(evt);
            }
        });
        FormInput.add(Obat1);
        Obat1.setBounds(130, 150, 290, 23);

        Rute1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute1.setName("Rute1"); // NOI18N
        Rute1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute1ActionPerformed(evt);
            }
        });
        Rute1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute1KeyPressed(evt);
            }
        });
        FormInput.add(Rute1);
        Rute1.setBounds(670, 150, 80, 23);

        jLabel61.setText("INPUT OBAT");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(10, 120, 120, 23);

        jLabel8.setText("Tgl. Pemberian :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(30, 80, 100, 23);

        DTPReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2026" }));
        DTPReg.setDisplayFormat("dd-MM-yyyy");
        DTPReg.setName("DTPReg"); // NOI18N
        DTPReg.setOpaque(false);
        DTPReg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPRegItemStateChanged(evt);
            }
        });
        DTPReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPRegKeyPressed(evt);
            }
        });
        FormInput.add(DTPReg);
        DTPReg.setBounds(140, 80, 90, 23);

        BtnObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat1.setMnemonic('2');
        BtnObat1.setToolTipText("Alt+2");
        BtnObat1.setName("BtnObat1"); // NOI18N
        BtnObat1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat1ActionPerformed(evt);
            }
        });
        BtnObat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat1KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat1);
        BtnObat1.setBounds(630, 150, 28, 23);

        jLabel45.setText("Obat 1 :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(10, 150, 110, 23);

        jLabel48.setText("NAMA OBAT");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(250, 120, 60, 23);

        jLabel49.setText("DOSIS");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(480, 120, 60, 23);

        jLabel51.setText("RUTE");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(640, 120, 80, 23);

        Dosis2.setFocusTraversalPolicyProvider(true);
        Dosis2.setName("Dosis2"); // NOI18N
        Dosis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis2KeyPressed(evt);
            }
        });
        FormInput.add(Dosis2);
        Dosis2.setBounds(420, 180, 210, 23);

        Obat2.setFocusTraversalPolicyProvider(true);
        Obat2.setName("Obat2"); // NOI18N
        Obat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat2KeyPressed(evt);
            }
        });
        FormInput.add(Obat2);
        Obat2.setBounds(130, 180, 290, 23);

        Rute2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute2.setName("Rute2"); // NOI18N
        Rute2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute2ActionPerformed(evt);
            }
        });
        Rute2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute2KeyPressed(evt);
            }
        });
        FormInput.add(Rute2);
        Rute2.setBounds(670, 180, 80, 23);

        BtnObat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat2.setMnemonic('2');
        BtnObat2.setToolTipText("Alt+2");
        BtnObat2.setName("BtnObat2"); // NOI18N
        BtnObat2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat2ActionPerformed(evt);
            }
        });
        BtnObat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat2KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat2);
        BtnObat2.setBounds(630, 180, 28, 23);

        jLabel46.setText("Obat 2 :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(10, 180, 110, 23);

        Dosis3.setFocusTraversalPolicyProvider(true);
        Dosis3.setName("Dosis3"); // NOI18N
        Dosis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis3KeyPressed(evt);
            }
        });
        FormInput.add(Dosis3);
        Dosis3.setBounds(420, 210, 210, 23);

        Obat3.setFocusTraversalPolicyProvider(true);
        Obat3.setName("Obat3"); // NOI18N
        Obat3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat3KeyPressed(evt);
            }
        });
        FormInput.add(Obat3);
        Obat3.setBounds(130, 210, 290, 23);

        Rute3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute3.setName("Rute3"); // NOI18N
        Rute3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute3ActionPerformed(evt);
            }
        });
        Rute3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute3KeyPressed(evt);
            }
        });
        FormInput.add(Rute3);
        Rute3.setBounds(670, 210, 80, 23);

        BtnObat3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat3.setMnemonic('2');
        BtnObat3.setToolTipText("Alt+2");
        BtnObat3.setName("BtnObat3"); // NOI18N
        BtnObat3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat3ActionPerformed(evt);
            }
        });
        BtnObat3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat3KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat3);
        BtnObat3.setBounds(630, 210, 28, 23);

        jLabel47.setText("Obat 3 :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(10, 210, 110, 23);

        Dosis4.setFocusTraversalPolicyProvider(true);
        Dosis4.setName("Dosis4"); // NOI18N
        Dosis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis4KeyPressed(evt);
            }
        });
        FormInput.add(Dosis4);
        Dosis4.setBounds(420, 240, 210, 23);

        Obat4.setFocusTraversalPolicyProvider(true);
        Obat4.setName("Obat4"); // NOI18N
        Obat4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat4KeyPressed(evt);
            }
        });
        FormInput.add(Obat4);
        Obat4.setBounds(130, 240, 290, 23);

        Rute4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute4.setName("Rute4"); // NOI18N
        Rute4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute4ActionPerformed(evt);
            }
        });
        Rute4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute4KeyPressed(evt);
            }
        });
        FormInput.add(Rute4);
        Rute4.setBounds(670, 240, 80, 23);

        BtnObat4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat4.setMnemonic('2');
        BtnObat4.setToolTipText("Alt+2");
        BtnObat4.setName("BtnObat4"); // NOI18N
        BtnObat4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat4ActionPerformed(evt);
            }
        });
        BtnObat4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat4KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat4);
        BtnObat4.setBounds(630, 240, 28, 23);

        jLabel50.setText("Obat 4 :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(10, 240, 110, 23);

        Dosis5.setFocusTraversalPolicyProvider(true);
        Dosis5.setName("Dosis5"); // NOI18N
        Dosis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis5KeyPressed(evt);
            }
        });
        FormInput.add(Dosis5);
        Dosis5.setBounds(420, 270, 210, 23);

        Obat5.setFocusTraversalPolicyProvider(true);
        Obat5.setName("Obat5"); // NOI18N
        Obat5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat5KeyPressed(evt);
            }
        });
        FormInput.add(Obat5);
        Obat5.setBounds(130, 270, 290, 23);

        Rute5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute5.setName("Rute5"); // NOI18N
        Rute5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute5ActionPerformed(evt);
            }
        });
        Rute5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute5KeyPressed(evt);
            }
        });
        FormInput.add(Rute5);
        Rute5.setBounds(670, 270, 80, 23);

        BtnObat5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat5.setMnemonic('2');
        BtnObat5.setToolTipText("Alt+2");
        BtnObat5.setName("BtnObat5"); // NOI18N
        BtnObat5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat5ActionPerformed(evt);
            }
        });
        BtnObat5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat5KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat5);
        BtnObat5.setBounds(630, 270, 28, 23);

        jLabel52.setText("Obat 5 :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(10, 270, 110, 20);

        Dosis6.setFocusTraversalPolicyProvider(true);
        Dosis6.setName("Dosis6"); // NOI18N
        Dosis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis6KeyPressed(evt);
            }
        });
        FormInput.add(Dosis6);
        Dosis6.setBounds(420, 300, 210, 23);

        Obat6.setFocusTraversalPolicyProvider(true);
        Obat6.setName("Obat6"); // NOI18N
        Obat6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat6KeyPressed(evt);
            }
        });
        FormInput.add(Obat6);
        Obat6.setBounds(130, 300, 290, 23);

        Rute6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute6.setName("Rute6"); // NOI18N
        Rute6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute6ActionPerformed(evt);
            }
        });
        Rute6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute6KeyPressed(evt);
            }
        });
        FormInput.add(Rute6);
        Rute6.setBounds(670, 300, 80, 23);

        BtnObat6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat6.setMnemonic('2');
        BtnObat6.setToolTipText("Alt+2");
        BtnObat6.setName("BtnObat6"); // NOI18N
        BtnObat6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat6ActionPerformed(evt);
            }
        });
        BtnObat6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat6KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat6);
        BtnObat6.setBounds(630, 300, 28, 23);

        jLabel53.setText("Obat 6 :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 300, 110, 23);

        Dosis7.setFocusTraversalPolicyProvider(true);
        Dosis7.setName("Dosis7"); // NOI18N
        Dosis7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis7KeyPressed(evt);
            }
        });
        FormInput.add(Dosis7);
        Dosis7.setBounds(420, 330, 210, 23);

        Obat7.setFocusTraversalPolicyProvider(true);
        Obat7.setName("Obat7"); // NOI18N
        Obat7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat7KeyPressed(evt);
            }
        });
        FormInput.add(Obat7);
        Obat7.setBounds(130, 330, 290, 23);

        Rute7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute7.setName("Rute7"); // NOI18N
        Rute7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute7ActionPerformed(evt);
            }
        });
        Rute7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute7KeyPressed(evt);
            }
        });
        FormInput.add(Rute7);
        Rute7.setBounds(670, 330, 80, 23);

        BtnObat7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat7.setMnemonic('2');
        BtnObat7.setToolTipText("Alt+2");
        BtnObat7.setName("BtnObat7"); // NOI18N
        BtnObat7.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat7ActionPerformed(evt);
            }
        });
        BtnObat7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat7KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat7);
        BtnObat7.setBounds(630, 330, 28, 23);

        jLabel54.setText("Obat 7 :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 330, 110, 23);

        Dosis8.setFocusTraversalPolicyProvider(true);
        Dosis8.setName("Dosis8"); // NOI18N
        Dosis8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis8KeyPressed(evt);
            }
        });
        FormInput.add(Dosis8);
        Dosis8.setBounds(420, 360, 210, 23);

        Obat8.setFocusTraversalPolicyProvider(true);
        Obat8.setName("Obat8"); // NOI18N
        Obat8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat8KeyPressed(evt);
            }
        });
        FormInput.add(Obat8);
        Obat8.setBounds(130, 360, 290, 23);

        Rute8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute8.setName("Rute8"); // NOI18N
        Rute8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute8ActionPerformed(evt);
            }
        });
        Rute8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute8KeyPressed(evt);
            }
        });
        FormInput.add(Rute8);
        Rute8.setBounds(670, 360, 80, 23);

        BtnObat8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat8.setMnemonic('2');
        BtnObat8.setToolTipText("Alt+2");
        BtnObat8.setName("BtnObat8"); // NOI18N
        BtnObat8.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat8ActionPerformed(evt);
            }
        });
        BtnObat8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat8KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat8);
        BtnObat8.setBounds(630, 360, 28, 23);

        jLabel55.setText("Obat 8 :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 360, 110, 23);

        Dosis9.setFocusTraversalPolicyProvider(true);
        Dosis9.setName("Dosis9"); // NOI18N
        Dosis9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis9KeyPressed(evt);
            }
        });
        FormInput.add(Dosis9);
        Dosis9.setBounds(420, 390, 210, 23);

        Obat9.setFocusTraversalPolicyProvider(true);
        Obat9.setName("Obat9"); // NOI18N
        Obat9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat9KeyPressed(evt);
            }
        });
        FormInput.add(Obat9);
        Obat9.setBounds(130, 390, 290, 23);

        Rute9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute9.setName("Rute9"); // NOI18N
        Rute9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute9ActionPerformed(evt);
            }
        });
        Rute9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute9KeyPressed(evt);
            }
        });
        FormInput.add(Rute9);
        Rute9.setBounds(670, 390, 80, 23);

        BtnObat9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat9.setMnemonic('2');
        BtnObat9.setToolTipText("Alt+2");
        BtnObat9.setName("BtnObat9"); // NOI18N
        BtnObat9.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat9ActionPerformed(evt);
            }
        });
        BtnObat9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat9KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat9);
        BtnObat9.setBounds(630, 390, 28, 23);

        jLabel56.setText("Obat 9 :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 390, 110, 23);

        Dosis10.setFocusTraversalPolicyProvider(true);
        Dosis10.setName("Dosis10"); // NOI18N
        Dosis10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis10KeyPressed(evt);
            }
        });
        FormInput.add(Dosis10);
        Dosis10.setBounds(420, 420, 210, 23);

        Obat10.setFocusTraversalPolicyProvider(true);
        Obat10.setName("Obat10"); // NOI18N
        Obat10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat10KeyPressed(evt);
            }
        });
        FormInput.add(Obat10);
        Obat10.setBounds(130, 420, 290, 23);

        Rute10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute10.setName("Rute10"); // NOI18N
        Rute10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute10ActionPerformed(evt);
            }
        });
        Rute10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute10KeyPressed(evt);
            }
        });
        FormInput.add(Rute10);
        Rute10.setBounds(670, 420, 80, 23);

        BtnObat10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat10.setMnemonic('2');
        BtnObat10.setToolTipText("Alt+2");
        BtnObat10.setName("BtnObat10"); // NOI18N
        BtnObat10.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat10ActionPerformed(evt);
            }
        });
        BtnObat10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat10KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat10);
        BtnObat10.setBounds(630, 420, 28, 23);

        jLabel57.setText("Obat 10 :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(10, 420, 110, 23);

        Dosis11.setFocusTraversalPolicyProvider(true);
        Dosis11.setName("Dosis11"); // NOI18N
        Dosis11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis11KeyPressed(evt);
            }
        });
        FormInput.add(Dosis11);
        Dosis11.setBounds(420, 450, 210, 23);

        Obat11.setFocusTraversalPolicyProvider(true);
        Obat11.setName("Obat11"); // NOI18N
        Obat11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat11KeyPressed(evt);
            }
        });
        FormInput.add(Obat11);
        Obat11.setBounds(130, 450, 290, 23);

        Rute11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute11.setName("Rute11"); // NOI18N
        Rute11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute11ActionPerformed(evt);
            }
        });
        Rute11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute11KeyPressed(evt);
            }
        });
        FormInput.add(Rute11);
        Rute11.setBounds(670, 450, 80, 23);

        BtnObat11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat11.setMnemonic('2');
        BtnObat11.setToolTipText("Alt+2");
        BtnObat11.setName("BtnObat11"); // NOI18N
        BtnObat11.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat11ActionPerformed(evt);
            }
        });
        BtnObat11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat11KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat11);
        BtnObat11.setBounds(630, 450, 28, 23);

        jLabel58.setText("Obat 11 :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(10, 450, 110, 23);

        Dosis12.setFocusTraversalPolicyProvider(true);
        Dosis12.setName("Dosis12"); // NOI18N
        Dosis12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis12KeyPressed(evt);
            }
        });
        FormInput.add(Dosis12);
        Dosis12.setBounds(420, 480, 210, 23);

        Obat12.setFocusTraversalPolicyProvider(true);
        Obat12.setName("Obat12"); // NOI18N
        Obat12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat12KeyPressed(evt);
            }
        });
        FormInput.add(Obat12);
        Obat12.setBounds(130, 480, 290, 23);

        Rute12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute12.setName("Rute12"); // NOI18N
        Rute12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute12ActionPerformed(evt);
            }
        });
        Rute12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute12KeyPressed(evt);
            }
        });
        FormInput.add(Rute12);
        Rute12.setBounds(670, 480, 80, 23);

        BtnObat12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat12.setMnemonic('2');
        BtnObat12.setToolTipText("Alt+2");
        BtnObat12.setName("BtnObat12"); // NOI18N
        BtnObat12.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat12ActionPerformed(evt);
            }
        });
        BtnObat12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat12KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat12);
        BtnObat12.setBounds(630, 480, 28, 23);

        jLabel59.setText("Obat 12 :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(10, 480, 110, 23);

        Dosis13.setFocusTraversalPolicyProvider(true);
        Dosis13.setName("Dosis13"); // NOI18N
        Dosis13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis13KeyPressed(evt);
            }
        });
        FormInput.add(Dosis13);
        Dosis13.setBounds(420, 510, 210, 23);

        Obat13.setFocusTraversalPolicyProvider(true);
        Obat13.setName("Obat13"); // NOI18N
        Obat13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat13KeyPressed(evt);
            }
        });
        FormInput.add(Obat13);
        Obat13.setBounds(130, 510, 290, 23);

        Rute13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute13.setName("Rute13"); // NOI18N
        Rute13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute13ActionPerformed(evt);
            }
        });
        Rute13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute13KeyPressed(evt);
            }
        });
        FormInput.add(Rute13);
        Rute13.setBounds(670, 510, 80, 23);

        BtnObat13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat13.setMnemonic('2');
        BtnObat13.setToolTipText("Alt+2");
        BtnObat13.setName("BtnObat13"); // NOI18N
        BtnObat13.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat13ActionPerformed(evt);
            }
        });
        BtnObat13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat13KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat13);
        BtnObat13.setBounds(630, 510, 28, 23);

        jLabel60.setText("Obat 13 :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(10, 510, 110, 23);

        Dosis14.setFocusTraversalPolicyProvider(true);
        Dosis14.setName("Dosis14"); // NOI18N
        Dosis14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis14KeyPressed(evt);
            }
        });
        FormInput.add(Dosis14);
        Dosis14.setBounds(420, 540, 210, 23);

        Obat14.setFocusTraversalPolicyProvider(true);
        Obat14.setName("Obat14"); // NOI18N
        Obat14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat14KeyPressed(evt);
            }
        });
        FormInput.add(Obat14);
        Obat14.setBounds(130, 540, 290, 23);

        Rute14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute14.setName("Rute14"); // NOI18N
        Rute14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute14ActionPerformed(evt);
            }
        });
        Rute14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute14KeyPressed(evt);
            }
        });
        FormInput.add(Rute14);
        Rute14.setBounds(670, 540, 80, 23);

        BtnObat14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat14.setMnemonic('2');
        BtnObat14.setToolTipText("Alt+2");
        BtnObat14.setName("BtnObat14"); // NOI18N
        BtnObat14.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat14ActionPerformed(evt);
            }
        });
        BtnObat14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat14KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat14);
        BtnObat14.setBounds(630, 540, 28, 23);

        jLabel62.setText("Obat 14 :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(10, 540, 110, 23);

        Dosis15.setFocusTraversalPolicyProvider(true);
        Dosis15.setName("Dosis15"); // NOI18N
        Dosis15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis15KeyPressed(evt);
            }
        });
        FormInput.add(Dosis15);
        Dosis15.setBounds(420, 570, 210, 23);

        Obat15.setFocusTraversalPolicyProvider(true);
        Obat15.setName("Obat15"); // NOI18N
        Obat15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat15KeyPressed(evt);
            }
        });
        FormInput.add(Obat15);
        Obat15.setBounds(130, 570, 290, 23);

        Rute15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Oral", "IV", "IM", "IC" }));
        Rute15.setName("Rute15"); // NOI18N
        Rute15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rute15ActionPerformed(evt);
            }
        });
        Rute15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rute15KeyPressed(evt);
            }
        });
        FormInput.add(Rute15);
        Rute15.setBounds(670, 570, 80, 23);

        BtnObat15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat15.setMnemonic('2');
        BtnObat15.setToolTipText("Alt+2");
        BtnObat15.setName("BtnObat15"); // NOI18N
        BtnObat15.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat15ActionPerformed(evt);
            }
        });
        BtnObat15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat15KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat15);
        BtnObat15.setBounds(630, 570, 28, 23);

        jLabel63.setText("Obat 15 :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(10, 570, 110, 23);

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

}//GEN-LAST:event_TPasienKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(Sequel.queryu2tf("delete from pemberian_obat_ranap where no_surat=?",1,new String[]{
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
        Valid.textKosong(BtnDokter,"Nama Pengkaji");
    }else if(Obat1.getText().equals("")){
        Valid.textKosong(Obat1,"Obat 1");
    }else{
        if(tbObat.getSelectedRow()>-1){
            // Ambil waktu dari checkbox aktif
            String waktuEdit = "";
            if(jCBPagi.isSelected()) waktuEdit = "Pagi";
            else if(jCBSiang.isSelected()) waktuEdit = "Siang";
            else if(jCBSore.isSelected()) waktuEdit = "Sore";
            else if(jCBMalam.isSelected()) waktuEdit = "Malam";
            else {
                JOptionPane.showMessageDialog(rootPane,"Pilih waktu!");
                return;
            }
            
            if(Sequel.mengedittf("pemberian_obat_ranap","no_surat=?",
                "no_surat=?,no_rawat=?,tanggal=?,jam=?,tanggal_pemberian=?,jam_pemberian=?,nik=?,obat1=?,dosis1=?,rute1=?,obat2=?,dosis2=?,rute2=?,obat3=?,dosis3=?,rute3=?,obat4=?,dosis4=?,rute4=?,obat5=?,dosis5=?,rute5=?,obat6=?,dosis6=?,rute6=?,obat7=?,dosis7=?,rute7=?,obat8=?,dosis8=?,rute8=?,obat9=?,dosis9=?,rute9=?,obat10=?,dosis10=?,rute10=?,obat11=?,dosis11=?,rute11=?,obat12=?,dosis12=?,rute12=?,obat13=?,dosis13=?,rute13=?,obat14=?,dosis14=?,rute14=?,obat15=?,dosis15=?,rute15=?,waktu=?",
                54,
                new String[]{
                    NoSurat.getText(),  // SET no_surat=?
                    TNoRw.getText(),
                    Valid.SetTgl(TanggalSurat.getSelectedItem()+""),
                    jamNow.format(new Date()),
                    Valid.SetTgl(DTPReg.getSelectedItem()+""),
                    CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                    KodeDokter.getText(),
                    Obat1.getText(),Dosis1.getText(),Rute1.getSelectedItem().toString(),
                    Obat2.getText(),Dosis2.getText(),Rute2.getSelectedItem().toString(),
                    Obat3.getText(),Dosis3.getText(),Rute3.getSelectedItem().toString(),
                    Obat4.getText(),Dosis4.getText(),Rute4.getSelectedItem().toString(),
                    Obat5.getText(),Dosis5.getText(),Rute5.getSelectedItem().toString(),
                    Obat6.getText(),Dosis6.getText(),Rute6.getSelectedItem().toString(),
                    Obat7.getText(),Dosis7.getText(),Rute7.getSelectedItem().toString(),
                    Obat8.getText(),Dosis8.getText(),Rute8.getSelectedItem().toString(),
                    Obat9.getText(),Dosis9.getText(),Rute9.getSelectedItem().toString(),
                    Obat10.getText(),Dosis10.getText(),Rute10.getSelectedItem().toString(),
                    Obat11.getText(),Dosis11.getText(),Rute11.getSelectedItem().toString(),
                    Obat12.getText(),Dosis12.getText(),Rute12.getSelectedItem().toString(),
                    Obat13.getText(),Dosis13.getText(),Rute13.getSelectedItem().toString(),
                    Obat14.getText(),Dosis14.getText(),Rute14.getSelectedItem().toString(),
                    Obat15.getText(),Dosis15.getText(),Rute15.getSelectedItem().toString(),
                    waktuEdit,
                    NoSurat.getText()  // WHERE no_surat=?
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
                        "surat_ic_rajal.kd_dokter,dokter.nm_dokter,surat_ic_rajal.kondisi_pulang,surat_ic_rajal.keluhan_utama,surat_ic_rajal.jalannya_penyakit, "+
                        "surat_ic_rajal.pemeriksaan_penunjang,surat_ic_rajal.hasil_laborat,surat_ic_rajal.diagnosa_utama,surat_ic_rajal.kd_diagnosa_utama, "+
                        "surat_ic_rajal.diagnosa_sekunder,surat_ic_rajal.kd_diagnosa_sekunder,surat_ic_rajal.diagnosa_sekunder2,surat_ic_rajal.kd_diagnosa_sekunder2, "+
                        "surat_ic_rajal.diagnosa_sekunder3,surat_ic_rajal.kd_diagnosa_sekunder3,surat_ic_rajal.diagnosa_sekunder4,surat_ic_rajal.kd_diagnosa_sekunder4, "+
                        "surat_ic_rajal.prosedur_utama,surat_ic_rajal.kd_prosedur_utama,surat_ic_rajal.prosedur_sekunder,surat_ic_rajal.kd_prosedur_sekunder, "+
                        "surat_ic_rajal.prosedur_sekunder2,surat_ic_rajal.kd_prosedur_sekunder2,surat_ic_rajal.prosedur_sekunder3,surat_ic_rajal.kd_prosedur_sekunder3, "+
                        "surat_ic_rajal.obat_pulang,surat_ic_rajal.tindak_lanjut,surat_ic_rajal.asal_pasien,surat_ic_rajal.tindakan from surat_ic_rajal inner join reg_periksa on surat_ic_rajal.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_ic_rajal.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
                }else{
                    Valid.MyReportqry("rptDataResumePasien.jasper","report","::[ Data Resume Pasien ]::",
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                        "surat_ic_rajal.kd_dokter,dokter.nm_dokter,surat_ic_rajal.kondisi_pulang,surat_ic_rajal.keluhan_utama,surat_ic_rajal.jalannya_penyakit, "+
                        "surat_ic_rajal.pemeriksaan_penunjang,surat_ic_rajal.hasil_laborat,surat_ic_rajal.diagnosa_utama,surat_ic_rajal.kd_diagnosa_utama, "+
                        "surat_ic_rajal.diagnosa_sekunder,surat_ic_rajal.kd_diagnosa_sekunder,surat_ic_rajal.diagnosa_sekunder2,surat_ic_rajal.kd_diagnosa_sekunder2, "+
                        "surat_ic_rajal.diagnosa_sekunder3,surat_ic_rajal.kd_diagnosa_sekunder3,surat_ic_rajal.diagnosa_sekunder4,surat_ic_rajal.kd_diagnosa_sekunder4, "+
                        "surat_ic_rajal.prosedur_utama,surat_ic_rajal.kd_prosedur_utama,surat_ic_rajal.prosedur_sekunder,surat_ic_rajal.kd_prosedur_sekunder, "+
                        "surat_ic_rajal.prosedur_sekunder2,surat_ic_rajal.kd_prosedur_sekunder2,surat_ic_rajal.prosedur_sekunder3,surat_ic_rajal.kd_prosedur_sekunder3, "+
                        "surat_ic_rajal.obat_pulang,surat_ic_rajal.tindak_lanjut,surat_ic_rajal.asal_pasien,surat_ic_rajal.tindakan from surat_ic_rajal inner join reg_periksa on surat_ic_rajal.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_ic_rajal.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.status_lanjut like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.kondisi_pulang like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.kd_diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.prosedur_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_ic_rajal.kd_prosedur_utama like '%"+TCari.getText().trim()+"%' "+
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

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalSuratActionPerformed

    private void TanggalSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSuratKeyPressed
        Valid.pindah(evt,NoSurat,Obat1);
    }//GEN-LAST:event_TanggalSuratKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,TCari,Obat1);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HubunganKeyPressed

    private void accepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_accepKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_accepKeyPressed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,TCari,Obat1);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        akses.setform("RMPemberianObatRanap");
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void KodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterKeyPressed
        Valid.pindah(evt,TCari,Obat1);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void Dosis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis1KeyPressed

    private void Obat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat1KeyPressed

    private void Rute1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute1KeyPressed

    private void Rute1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute1ActionPerformed

    private void DTPRegItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPRegItemStateChanged
//        isNumber();
    }//GEN-LAST:event_DTPRegItemStateChanged

    private void DTPRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPRegKeyPressed
        Valid.pindah(evt,TNoRw,CmbJam);
    }//GEN-LAST:event_DTPRegKeyPressed

    private void CmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJamKeyPressed
        Valid.pindah(evt,DTPReg,CmbMenit);
    }//GEN-LAST:event_CmbJamKeyPressed

    private void CmbMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenitKeyPressed
        Valid.pindah(evt,CmbJam,CmbDetik);
    }//GEN-LAST:event_CmbMenitKeyPressed

    private void CmbDetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetikKeyPressed
        Valid.pindah(evt,CmbMenit,Obat1);
    }//GEN-LAST:event_CmbDetikKeyPressed

    private void BtnObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat1ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=1;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat1ActionPerformed

    private void BtnObat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat1KeyPressed

    private void Dosis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis2KeyPressed

    private void Obat2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat2KeyPressed

    private void Rute2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute2ActionPerformed

    private void Rute2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute2KeyPressed

    private void BtnObat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat2ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=2;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat2ActionPerformed

    private void BtnObat2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat2KeyPressed

    private void Dosis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis3KeyPressed

    private void Obat3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat3KeyPressed

    private void Rute3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute3ActionPerformed

    private void Rute3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute3KeyPressed

    private void BtnObat3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat3ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=3;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat3ActionPerformed

    private void BtnObat3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat3KeyPressed

    private void Dosis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis4KeyPressed

    private void Obat4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat4KeyPressed

    private void Rute4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute4ActionPerformed
        
    }//GEN-LAST:event_Rute4ActionPerformed

    private void Rute4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute4KeyPressed

    private void BtnObat4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat4ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=4;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat4ActionPerformed

    private void BtnObat4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat4KeyPressed

    private void Dosis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis5KeyPressed

    private void Obat5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat5KeyPressed

    private void Rute5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute5ActionPerformed
        
    }//GEN-LAST:event_Rute5ActionPerformed

    private void Rute5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute5KeyPressed

    private void BtnObat5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat5ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=5;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat5ActionPerformed

    private void BtnObat5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat5KeyPressed

    private void Dosis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis6KeyPressed

    private void Obat6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat6KeyPressed

    private void Rute6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute6ActionPerformed
        
    }//GEN-LAST:event_Rute6ActionPerformed

    private void Rute6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute6KeyPressed

    private void BtnObat6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat6ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=6;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat6ActionPerformed

    private void BtnObat6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat6KeyPressed

    private void Dosis7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis7KeyPressed

    private void Obat7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat7KeyPressed

    private void Rute7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute7ActionPerformed
        
    }//GEN-LAST:event_Rute7ActionPerformed

    private void Rute7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute7KeyPressed

    private void BtnObat7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat7ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=7;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat7ActionPerformed

    private void BtnObat7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat7KeyPressed

    private void Dosis8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis8KeyPressed

    private void Obat8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat8KeyPressed

    private void Rute8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute8ActionPerformed
        
    }//GEN-LAST:event_Rute8ActionPerformed

    private void Rute8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute8KeyPressed

    private void BtnObat8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat8ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=8;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat8ActionPerformed

    private void BtnObat8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat8KeyPressed

    private void Dosis9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis9KeyPressed

    private void Obat9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat9KeyPressed

    private void Rute9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute9ActionPerformed

    private void Rute9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute9KeyPressed

    private void BtnObat9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat9ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=9;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat9ActionPerformed

    private void BtnObat9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat9KeyPressed

    private void Dosis10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis10KeyPressed

    private void Obat10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat10KeyPressed

    private void Rute10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute10ActionPerformed
        
    }//GEN-LAST:event_Rute10ActionPerformed

    private void Rute10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute10KeyPressed

    private void BtnObat10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat10ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=10;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat10ActionPerformed

    private void BtnObat10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat10KeyPressed

    private void Dosis11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis11KeyPressed

    private void Obat11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat11KeyPressed

    private void Rute11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute11ActionPerformed
        
    }//GEN-LAST:event_Rute11ActionPerformed

    private void Rute11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute11KeyPressed

    private void BtnObat11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat11ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=11;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat11ActionPerformed

    private void BtnObat11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat11KeyPressed

    private void Dosis12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis12KeyPressed

    private void Obat12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat12KeyPressed

    private void Rute12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute12ActionPerformed
        
    }//GEN-LAST:event_Rute12ActionPerformed

    private void Rute12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute12KeyPressed

    private void BtnObat12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat12ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=12;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat12ActionPerformed

    private void BtnObat12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat12KeyPressed

    private void Dosis13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis13KeyPressed

    private void Obat13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat13KeyPressed

    private void Rute13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute13ActionPerformed
        
    }//GEN-LAST:event_Rute13ActionPerformed

    private void Rute13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute13KeyPressed

    private void BtnObat13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat13ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=13;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat13ActionPerformed

    private void BtnObat13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat13KeyPressed

    private void Dosis14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis14KeyPressed

    private void Obat14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat14KeyPressed

    private void Rute14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute14ActionPerformed
        
    }//GEN-LAST:event_Rute14ActionPerformed

    private void Rute14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute14KeyPressed

    private void BtnObat14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat14ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=14;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat14ActionPerformed

    private void BtnObat14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat14KeyPressed

    private void Dosis15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis15KeyPressed

    private void Obat15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat15KeyPressed

    private void Rute15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rute15ActionPerformed
        
    }//GEN-LAST:event_Rute15ActionPerformed

    private void Rute15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rute15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rute15KeyPressed

    private void BtnObat15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat15ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=15;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
//        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat15ActionPerformed

    private void BtnObat15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat15KeyPressed

    private void NoSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoSuratActionPerformed

    private void TanggalSuratItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalSuratItemStateChanged
        autoNumberX(TanggalSurat.getSelectedItem()+"");
    }//GEN-LAST:event_TanggalSuratItemStateChanged

    private void BtnCloseIn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn1ActionPerformed
        pagi.dispose();
    }//GEN-LAST:event_BtnCloseIn1ActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodeDokter.getText().equals("")||NamaDokter.getText().equals("")){
            Valid.textKosong(BtnDokter,"Nama Pengkaji");
        }else if(Obat1.getText().equals("")){
            Valid.textKosong(Obat1,"Obat 1");
        }else {
        // Validasi & ambil waktu multiple dari checkbox
            List<String> waktuList = new ArrayList<>();
            if(jCBPagi.isSelected()) waktuList.add("Pagi");
            if(jCBSiang.isSelected()) waktuList.add("Siang");
            if(jCBSore.isSelected()) waktuList.add("Sore");
            if(jCBMalam.isSelected()) waktuList.add("Malam");

            if(waktuList.isEmpty()) {
                Valid.textKosong(jCBPagi,"Waktu");
                return;
            }
            
            // Ekstrak prefix dan nomor (PORI202601200001 → prefix="PORI20260120", nomor="0001")
            String noSuratFull = NoSurat.getText();
            String nomorBagian = noSuratFull.substring(noSuratFull.length() - 4); // 4 digit akhir
            String prefix = noSuratFull.substring(0, noSuratFull.length() - 4);
            int baseNomor = Integer.parseInt(nomorBagian);

            boolean semuaSukses = true;
            for(int i = 0; i < waktuList.size(); i++) {
                int nomorBaru = baseNomor + i;
                String noSuratBaru = prefix + String.format("%04d", nomorBaru); // 4 digit zero-padded
                
            if(Sequel.menyimpantf("pemberian_obat_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Pemberiaan",56,new String[]{
                    noSuratBaru,TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),KodeDokter.getText(),
                    Obat1.getText(),Dosis1.getText(),Rute1.getSelectedItem().toString(),
                    Obat2.getText(),Dosis2.getText(),Rute2.getSelectedItem().toString(),
                    Obat3.getText(),Dosis3.getText(),Rute3.getSelectedItem().toString(),
                    Obat4.getText(),Dosis4.getText(),Rute4.getSelectedItem().toString(),
                    Obat5.getText(),Dosis5.getText(),Rute5.getSelectedItem().toString(),
                    Obat6.getText(),Dosis6.getText(),Rute6.getSelectedItem().toString(),
                    Obat7.getText(),Dosis7.getText(),Rute7.getSelectedItem().toString(),
                    Obat8.getText(),Dosis8.getText(),Rute8.getSelectedItem().toString(),
                    Obat9.getText(),Dosis9.getText(),Rute9.getSelectedItem().toString(),
                    Obat10.getText(),Dosis10.getText(),Rute10.getSelectedItem().toString(),
                    Obat11.getText(),Dosis11.getText(),Rute11.getSelectedItem().toString(),
                    Obat12.getText(),Dosis12.getText(),Rute12.getSelectedItem().toString(),
                    Obat13.getText(),Dosis13.getText(),Rute13.getSelectedItem().toString(),
                    Obat14.getText(),Dosis14.getText(),Rute14.getSelectedItem().toString(),
                    Obat15.getText(),Dosis15.getText(),Rute15.getSelectedItem().toString(),
                    "-","-","",waktuList.get(i)
                })==false){
                semuaSukses = false;
                }
            }
            if(semuaSukses){
                int nextNomor = baseNomor + waktuList.size();
                NoSurat.setText(prefix + String.format("%04d", nextNomor));
                tampil();
                emptTeks();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnBatal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatal1ActionPerformed
//        emptTeks();
        emptTekswaktu();
        ChkInput.setSelected(true);
        isForm();
    }//GEN-LAST:event_BtnBatal1ActionPerformed

    private void BtnBatal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatal1KeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
            emptTekswaktu();
        }
    }//GEN-LAST:event_BtnBatal1KeyPressed

    private void btnAmbilPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilPetugasActionPerformed
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(Obat1.getText().equals("")){
            Valid.textKosong(Obat1,"Obat 1");
        }else{
            if(!TNoRw.equals("")){
                if(TNoRw.equals("")){
                    Valid.textKosong(TCari,"No.Pemberian");
                }else{ 
//                    TanggalPulang.setDate(new Date());
                    NoSurat.requestFocus();
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                    pagi.setLocationRelativeTo(internalFrame1);
                    pagi.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            }   
        }
    }//GEN-LAST:event_btnAmbilPetugasActionPerformed

    private void BtnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdit1ActionPerformed
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodeDokter.getText().equals("")||NamaDokter.getText().equals("")){
            Valid.textKosong(BtnDokter,"Nama Pengkaji");
        }else if(Obat1.getText().equals("")){
            Valid.textKosong(Obat1,"Obat 1");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("pemberian_obat_ranap","no_surat=?","no_surat=?,no_rawat=?,tanggal=?,jam=?,tanggal_pemberian=?,jam_pemberian=?,nik=?,obat1=?,dosis1=?,rute1=?,obat2=?,dosis2=?,rute2=?,obat3=?,dosis3=?,rute3=?,obat4=?,dosis4=?,rute4=?,obat5=?,dosis5=?,rute5=?,obat6=?,dosis6=?,rute6=?,obat7=?,dosis7=?,rute7=?,obat8=?,dosis8=?,rute8=?,obat9=?,dosis9=?,rute9=?,obat10=?,dosis10=?,rute10=?,obat11=?,dosis11=?,rute11=?,obat12=?,dosis12=?,rute12=?,obat13=?,dosis13=?,rute13=?,obat14=?,dosis14=?,rute14=?,obat15=?,dosis15=?,rute15=?,hubungan=?,acc_po=?,waktu=?",56,new String[]{
                        NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),KodeDokter.getText(),
                        Obat1.getText(),Dosis1.getText(),Rute1.getSelectedItem().toString(),
                        Obat2.getText(),Dosis2.getText(),Rute2.getSelectedItem().toString(),
                        Obat3.getText(),Dosis3.getText(),Rute3.getSelectedItem().toString(),
                        Obat4.getText(),Dosis4.getText(),Rute4.getSelectedItem().toString(),
                        Obat5.getText(),Dosis5.getText(),Rute5.getSelectedItem().toString(),
                        Obat6.getText(),Dosis6.getText(),Rute6.getSelectedItem().toString(),
                        Obat7.getText(),Dosis7.getText(),Rute7.getSelectedItem().toString(),
                        Obat8.getText(),Dosis8.getText(),Rute8.getSelectedItem().toString(),
                        Obat9.getText(),Dosis9.getText(),Rute9.getSelectedItem().toString(),
                        Obat10.getText(),Dosis10.getText(),Rute10.getSelectedItem().toString(),
                        Obat11.getText(),Dosis11.getText(),Rute11.getSelectedItem().toString(),
                        Obat12.getText(),Dosis12.getText(),Rute12.getSelectedItem().toString(),
                        Obat13.getText(),Dosis13.getText(),Rute13.getSelectedItem().toString(),
                        Obat14.getText(),Dosis14.getText(),Rute14.getSelectedItem().toString(),
                        Obat15.getText(),Dosis15.getText(),Rute15.getSelectedItem().toString(),
//                        Hubungan.getText(),accep.getText(),waktu.getSelectedItem().toString(),
//                        tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                        NoSurat.getText()
                    })==true){
                       tampil();
                       emptTeks();
                       pagi.dispose();
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_BtnEdit1ActionPerformed

    private void BtnEdit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEdit1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEdit1KeyPressed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from pemberian_obat_ranap where tanggal='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
                "PORI"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),4,NoSurat);
//        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0) from surat_ic_rajal where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
//                "ICRJ"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),3,NoSurat); 
        NoSurat.requestFocus();
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void jCBMalamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBMalamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBMalamActionPerformed

    private void jCBSoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBSoreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBSoreActionPerformed

    private void jCBPagiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBPagiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBPagiActionPerformed

    private void jCBSiangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBSiangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBSiangActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPemberianObatRanap dialog = new RMPemberianObatRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal1;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn1;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnEdit1;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnObat1;
    private widget.Button BtnObat10;
    private widget.Button BtnObat11;
    private widget.Button BtnObat12;
    private widget.Button BtnObat13;
    private widget.Button BtnObat14;
    private widget.Button BtnObat15;
    private widget.Button BtnObat2;
    private widget.Button BtnObat3;
    private widget.Button BtnObat4;
    private widget.Button BtnObat5;
    private widget.Button BtnObat6;
    private widget.Button BtnObat7;
    private widget.Button BtnObat8;
    private widget.Button BtnObat9;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPReg;
    private widget.TextBox Dosis1;
    private widget.TextBox Dosis10;
    private widget.TextBox Dosis11;
    private widget.TextBox Dosis12;
    private widget.TextBox Dosis13;
    private widget.TextBox Dosis14;
    private widget.TextBox Dosis15;
    private widget.TextBox Dosis2;
    private widget.TextBox Dosis3;
    private widget.TextBox Dosis4;
    private widget.TextBox Dosis5;
    private widget.TextBox Dosis6;
    private widget.TextBox Dosis7;
    private widget.TextBox Dosis8;
    private widget.TextBox Dosis9;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox Hubungan;
    private widget.TextBox KodeDokter;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox NamaDokter;
    private widget.TextBox NoSurat;
    private widget.TextBox Obat1;
    private widget.TextBox Obat10;
    private widget.TextBox Obat11;
    private widget.TextBox Obat12;
    private widget.TextBox Obat13;
    private widget.TextBox Obat14;
    private widget.TextBox Obat15;
    private widget.TextBox Obat2;
    private widget.TextBox Obat3;
    private widget.TextBox Obat4;
    private widget.TextBox Obat5;
    private widget.TextBox Obat6;
    private widget.TextBox Obat7;
    private widget.TextBox Obat8;
    private widget.TextBox Obat9;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Rute1;
    private widget.ComboBox Rute10;
    private widget.ComboBox Rute11;
    private widget.ComboBox Rute12;
    private widget.ComboBox Rute13;
    private widget.ComboBox Rute14;
    private widget.ComboBox Rute15;
    private widget.ComboBox Rute2;
    private widget.ComboBox Rute3;
    private widget.ComboBox Rute4;
    private widget.ComboBox Rute5;
    private widget.ComboBox Rute6;
    private widget.ComboBox Rute7;
    private widget.ComboBox Rute8;
    private widget.ComboBox Rute9;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalSurat;
    private widget.TextBox accep;
    private widget.Button btnAmbilPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame5;
    private javax.swing.JCheckBox jCBMalam;
    private javax.swing.JCheckBox jCBPagi;
    private javax.swing.JCheckBox jCBSiang;
    private javax.swing.JCheckBox jCBSore;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel27;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
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
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label14;
    private widget.Label label15;
    private javax.swing.JDialog pagi;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "pemberian_obat_ranap.no_surat,pemberian_obat_ranap.no_rawat,pemberian_obat_ranap.tanggal,pemberian_obat_ranap.jam,pemberian_obat_ranap.tanggal_pemberian,pemberian_obat_ranap.jam_pemberian,pemberian_obat_ranap.nik,pegawai.nama,"+
                    "pemberian_obat_ranap.obat1,pemberian_obat_ranap.dosis1,pemberian_obat_ranap.rute1,pemberian_obat_ranap.obat2,pemberian_obat_ranap.dosis2,"+
                    "pemberian_obat_ranap.rute2,pemberian_obat_ranap.obat3,pemberian_obat_ranap.dosis3,pemberian_obat_ranap.rute3,pemberian_obat_ranap.obat4,"+
                    "pemberian_obat_ranap.dosis4,pemberian_obat_ranap.rute4,pemberian_obat_ranap.obat5,pemberian_obat_ranap.dosis5,pemberian_obat_ranap.rute5,"+
                    "pemberian_obat_ranap.obat6,pemberian_obat_ranap.dosis6,pemberian_obat_ranap.rute6,pemberian_obat_ranap.obat7,pemberian_obat_ranap.dosis7,"+
                    "pemberian_obat_ranap.rute7,pemberian_obat_ranap.obat8,pemberian_obat_ranap.dosis8,pemberian_obat_ranap.rute8,pemberian_obat_ranap.obat9,"+
                    "pemberian_obat_ranap.dosis9,pemberian_obat_ranap.rute9,pemberian_obat_ranap.obat10,pemberian_obat_ranap.dosis10,pemberian_obat_ranap.rute10,"+
                    "pemberian_obat_ranap.obat11,pemberian_obat_ranap.dosis11,pemberian_obat_ranap.rute11,pemberian_obat_ranap.obat12,pemberian_obat_ranap.dosis12,"+
                    "pemberian_obat_ranap.rute12,pemberian_obat_ranap.obat13,pemberian_obat_ranap.dosis13,pemberian_obat_ranap.rute13,pemberian_obat_ranap.obat14,"+
                    "pemberian_obat_ranap.dosis14,pemberian_obat_ranap.rute14,pemberian_obat_ranap.obat15,pemberian_obat_ranap.dosis15,pemberian_obat_ranap.rute15,"+
                    "pemberian_obat_ranap.hubungan,pemberian_obat_ranap.acc_po,pemberian_obat_ranap.tte,pemberian_obat_ranap.waktu from pemberian_obat_ranap inner join reg_periksa on pemberian_obat_ranap.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pemberian_obat_ranap.nik=pegawai.nik "+
                    "where pemberian_obat_ranap.tanggal between ? and ? order by pemberian_obat_ranap.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "pemberian_obat_ranap.no_surat,pemberian_obat_ranap.no_rawat,pemberian_obat_ranap.tanggal,pemberian_obat_ranap.jam,pemberian_obat_ranap.tanggal_pemberian,pemberian_obat_ranap.jam_pemberian,pemberian_obat_ranap.nik,pegawai.nama,"+
                    "pemberian_obat_ranap.obat1,pemberian_obat_ranap.dosis1,pemberian_obat_ranap.rute1,pemberian_obat_ranap.obat2,pemberian_obat_ranap.dosis2,"+
                    "pemberian_obat_ranap.rute2,pemberian_obat_ranap.obat3,pemberian_obat_ranap.dosis3,pemberian_obat_ranap.rute3,pemberian_obat_ranap.obat4,"+
                    "pemberian_obat_ranap.dosis4,pemberian_obat_ranap.rute4,pemberian_obat_ranap.obat5,pemberian_obat_ranap.dosis5,pemberian_obat_ranap.rute5,"+
                    "pemberian_obat_ranap.obat6,pemberian_obat_ranap.dosis6,pemberian_obat_ranap.rute6,pemberian_obat_ranap.obat7,pemberian_obat_ranap.dosis7,"+
                    "pemberian_obat_ranap.rute7,pemberian_obat_ranap.obat8,pemberian_obat_ranap.dosis8,pemberian_obat_ranap.rute8,pemberian_obat_ranap.obat9,"+
                    "pemberian_obat_ranap.dosis9,pemberian_obat_ranap.rute9,pemberian_obat_ranap.obat10,pemberian_obat_ranap.dosis10,pemberian_obat_ranap.rute10,"+
                    "pemberian_obat_ranap.obat11,pemberian_obat_ranap.dosis11,pemberian_obat_ranap.rute11,pemberian_obat_ranap.obat12,pemberian_obat_ranap.dosis12,"+
                    "pemberian_obat_ranap.rute12,pemberian_obat_ranap.obat13,pemberian_obat_ranap.dosis13,pemberian_obat_ranap.rute13,pemberian_obat_ranap.obat14,"+
                    "pemberian_obat_ranap.dosis14,pemberian_obat_ranap.rute14,pemberian_obat_ranap.obat15,pemberian_obat_ranap.dosis15,pemberian_obat_ranap.rute15,"+
                    "pemberian_obat_ranap.hubungan,pemberian_obat_ranap.acc_po,pemberian_obat_ranap.tte,pemberian_obat_ranap.waktu from pemberian_obat_ranap inner join reg_periksa on pemberian_obat_ranap.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pemberian_obat_ranap.nik=pegawai.nik "+
                    "where pemberian_obat_ranap.tanggal between ? and ? and pemberian_obat_ranap.acc_po like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pemberian_obat_ranap.obat1 like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pemberian_obat_ranap.no_surat like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pemberian_obat_ranap.nik like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pegawai.nama like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pemberian_obat_ranap.nik like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pemberian_obat_ranap.obat1 like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pemberian_obat_ranap.obat2 like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pemberian_obat_ranap.obat3 like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pemberian_obat_ranap.obat4 like ? or "+
                    "pemberian_obat_ranap.tanggal between ? and ? and pemberian_obat_ranap.no_rawat like ? "+
                    "order by pemberian_obat_ranap.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("no_surat"),rs.getString("tanggal"),
                        rs.getString("jam"),rs.getString("tanggal_pemberian"),rs.getString("jam_pemberian"),rs.getString("nik"),rs.getString("nama"),
                        rs.getString("obat1"),rs.getString("dosis1"),rs.getString("rute1"),
                        rs.getString("obat2"),rs.getString("dosis2"),rs.getString("rute2"),
                        rs.getString("obat3"),rs.getString("dosis3"),rs.getString("rute3"),
                        rs.getString("obat4"),rs.getString("dosis4"),rs.getString("rute4"),
                        rs.getString("obat5"),rs.getString("dosis5"),rs.getString("rute5"),
                        rs.getString("obat6"),rs.getString("dosis6"),rs.getString("rute6"),
                        rs.getString("obat7"),rs.getString("dosis7"),rs.getString("rute7"),
                        rs.getString("obat8"),rs.getString("dosis8"),rs.getString("rute8"),
                        rs.getString("obat9"),rs.getString("dosis9"),rs.getString("rute9"),
                        rs.getString("obat10"),rs.getString("dosis10"),rs.getString("rute10"),
                        rs.getString("obat11"),rs.getString("dosis11"),rs.getString("rute11"),
                        rs.getString("obat12"),rs.getString("dosis12"),rs.getString("rute12"),
                        rs.getString("obat13"),rs.getString("dosis13"),rs.getString("rute13"),
                        rs.getString("obat14"),rs.getString("dosis14"),rs.getString("rute14"),
                        rs.getString("obat15"),rs.getString("dosis15"),rs.getString("rute15"),
                        rs.getString("hubungan"),rs.getString("acc_po"),"",rs.getString("waktu")
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
        KodeDokter.setText("");
        NamaDokter.setText("");
        Obat1.setText("");
        Dosis1.setText("");
        Rute1.setSelectedIndex(0);
        Obat2.setText("");
        Dosis2.setText("");
        Rute2.setSelectedIndex(0);
        Obat3.setText("");
        Dosis3.setText("");
        Rute3.setSelectedIndex(0);
        Obat4.setText("");
        Dosis4.setText("");
        Rute4.setSelectedIndex(0);
        Obat5.setText("");
        Dosis5.setText("");
        Rute5.setSelectedIndex(0);
        Obat6.setText("");
        Dosis6.setText("");
        Rute6.setSelectedIndex(0);
        Obat7.setText("");
        Dosis7.setText("");
        Rute7.setSelectedIndex(0);
        Obat8.setText("");
        Dosis8.setText("");
        Rute8.setSelectedIndex(0);
        Obat9.setText("");
        Dosis9.setText("");
        Rute9.setSelectedIndex(0);
        Obat10.setText("");
        Dosis10.setText("");
        Rute10.setSelectedIndex(0);
        Obat11.setText("");
        Dosis11.setText("");
        Rute11.setSelectedIndex(0);
        Obat12.setText("");
        Dosis12.setText("");
        Rute12.setSelectedIndex(0);
        Obat13.setText("");
        Dosis13.setText("");
        Rute13.setSelectedIndex(0);
        Obat14.setText("");
        Dosis14.setText("");
        Rute14.setSelectedIndex(0);
        Obat15.setText("");
        Dosis15.setText("");
        Rute15.setSelectedIndex(0);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from pemberian_obat_ranap where tanggal='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
                "PORI"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),4,NoSurat);
//        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0) from surat_ic_rajal where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
//                "ICRJ"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),3,NoSurat); 
        NoSurat.requestFocus();
    } 
    
    private void autoNumberX(String tglPilih) {
    Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from pemberian_obat_ranap where tanggal='"+Valid.SetTgl(tglPilih+"")+"' ",
                "PORI"+Valid.SetTgl(tglPilih+"").replaceAll("-", ""),4,NoSurat);
    }
    
    public void emptTekswaktu() {
        KodeDokter.setText("");
//        NamaDokter.setText("");
//        waktu.setSelectedIndex(0);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from pemberian_obat_ranap where tanggal='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
                "PORI"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),4,NoSurat);
//        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0) from surat_ic_rajal where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
//                "ICRJ"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),3,NoSurat); 
        jCBPagi.setSelected(false);
        jCBSiang.setSelected(false);
        jCBSore.setSelected(false);
        jCBMalam.setSelected(false);        
        NoSurat.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());  
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());  
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Valid.SetTgl(TanggalSurat,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl(DTPReg,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            CmbJam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(0,2));
            CmbMenit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(3,5));
            CmbDetik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(6,8));
            KodeDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());  
            NamaDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Obat1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Dosis1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Rute1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Obat2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Dosis2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Rute2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Obat3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Dosis3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Rute3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Obat4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Dosis4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Rute4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Obat5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Dosis5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Rute5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Obat6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Dosis6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Rute6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Obat7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Dosis7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Rute7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Obat8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Dosis8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Rute8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Obat9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Dosis9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Rute9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Obat10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Dosis10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Rute10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Obat11.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Dosis11.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Rute11.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Obat12.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Dosis12.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Rute12.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Obat13.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Dosis13.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Rute13.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Obat14.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Dosis14.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Rute14.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Obat15.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Dosis15.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Rute15.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            accep.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            
            // Reset semua dulu
            jCBPagi.setSelected(false);
            jCBPagi.setEnabled(true);
            jCBSiang.setSelected(false);
            jCBSiang.setEnabled(true);
            jCBSore.setSelected(false);
            jCBSore.setEnabled(true);
            jCBMalam.setSelected(false);
            jCBMalam.setEnabled(true);

            // Ambil data waktu kolom 58
            String waktuData = tbObat.getValueAt(tbObat.getSelectedRow(),58) != null ? 
                                tbObat.getValueAt(tbObat.getSelectedRow(),58).toString() : "";

            // Set sesuai data DAN DISABLE yang lain
            if("Pagi".equals(waktuData)) {
                jCBPagi.setSelected(true);
                jCBSiang.setEnabled(false);
                jCBSore.setEnabled(false);
                jCBMalam.setEnabled(false);
            } else if("Siang".equals(waktuData)) {
                jCBSiang.setSelected(true);
                jCBPagi.setEnabled(false);
                jCBSore.setEnabled(false);
                jCBMalam.setEnabled(false);
            } else if("Sore".equals(waktuData)) {
                jCBSore.setSelected(true);
                jCBPagi.setEnabled(false);
                jCBSiang.setEnabled(false);
                jCBMalam.setEnabled(false);
            } else if("Malam".equals(waktuData)) {
                jCBMalam.setSelected(true);
                jCBPagi.setEnabled(false);
                jCBSiang.setEnabled(false);
                jCBSore.setEnabled(false);
            }

            panggilPhoto();
            
            NoSurat.requestFocus();
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
            pagi.setLocationRelativeTo(internalFrame1);
            pagi.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
         try {
            ps=koneksi.prepareStatement(
                    "select nm_pasien, if(jk='L','Laki-Laki','Perempuan') as jk,tgl_lahir,agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,pasien.pnd "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                    "where no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
//                    Jk.sefirdhar.setText(rs.getString("tgl_lahir"));
//                    Agama.setText(rs.getString("agama"));
//                    Bahasa.setText(rs.getString("nama_bahasa"));
//                    Pendidikan.setText(rs.getString("pnd"));
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
        BtnSimpan.setEnabled(akses.gettindakan_ranap());
        BtnHapus.setEnabled(akses.gettindakan_ranap());
        BtnEdit.setEnabled(akses.gettindakan_ranap());
        BtnPrint.setEnabled(akses.gettindakan_ranap()); 
//        MnInputDiagnosa.setEnabled(akses.getdiagnosa_pasien());   
//        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());   
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from pemberian_obat_ranap where tanggal='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
                "PORI"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),4,NoSurat); 
        if(akses.getjml2()>=1){
            KodeDokter.setEditable(false);
//            BtnDokter.setEnabled(false);
            KodeDokter.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", NamaDokter,KodeDokter.getText());
//            if(NmPetugas.getText().equals("")){
//                KdPetugas.setText("");
//                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
//            }
        }            
    }
    
    private void panggilPhoto() {
//        if(FormPhotoPass.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select pemberian_obat_ranap.tte from pemberian_obat_ranap where pemberian_obat_ranap.no_surat=?");
                try {
                    ps.setString(1,NoSurat.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("tte").equals("")||rs.getString("tte").equals("-")){
                            LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+rs.getString("tte")+"' alt='photo' width='300' height='280'/></center></body></html>");
                        }  
//                        PasswordPasien.setText(rs.getString("password"));
                    }else{
                        LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
//                        PasswordPasien.setText("");
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
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMenit.getSelectedIndex();
                    nilai_detik =CmbDetik.getSelectedIndex();
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
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    
}
