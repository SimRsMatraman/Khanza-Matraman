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
    // ===== UI KOTAK v9 - database baru =====
    private javax.swing.JTable tbKotakPantau;
    private javax.swing.table.DefaultTableModel modelKotakPantau;
    private javax.swing.JSpinner spTanggalKotak;
    private javax.swing.JLabel lblRingkasKotak;
    private javax.swing.JButton btnHariIniV18,btnPrevV18,btnNextV18,btnRiwayat7V18;
    private javax.swing.JLabel lblTanggalAktifV18;
    // Header Excel V12 SAFE - ditambahkan setelah UI kotak V9 berhasil dipasang
    private javax.swing.JLabel hxNoRM, hxNama, hxNoRawat, hxTglLahir, hxJK, hxTglMasuk, hxDPJP, hxAlergi;
    private String nikPetugasKotak="";
    private String namaPetugasKotak="";
    private final java.util.Map<Integer,Long> kotakDetailPerBaris=new java.util.HashMap<Integer,Long>();
    private final java.util.Map<String,Long> kotakJadwal=new java.util.HashMap<String,Long>();
    private final java.util.Map<String,String> tooltipKotakV35=new java.util.HashMap<String,String>();
    private final java.util.Map<Integer,Long> tteJadwalPerBarisV38=new java.util.HashMap<Integer,Long>();
    private final String[] jamKotakUtama={"06:00","08:00","12:00","14:00","18:00","20:00","22:00"};
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
        pasangModeKotakV3();
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
        JOptionPane.showMessageDialog(rootPane,"Mode Kotak aktif. Perubahan pemberian dilakukan langsung dengan klik kotak jam pada obat yang dipilih.");
        muatKotakPemberian();
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
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Pilih pasien terlebih dahulu.");
            return;
        }
        String no=pastikanNoSuratKotak();
        if(!no.equals("")){
            NoSurat.setText(no);
            muatKotakPemberian();
            JOptionPane.showMessageDialog(rootPane,"Dokumen pemberian obat siap. Gunakan tombol 'Ambil Obat dari Resep' lalu klik kotak jam untuk mencatat pemberian.");
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
        JOptionPane.showMessageDialog(rootPane,"Mode Kotak aktif. Perubahan pemberian dilakukan langsung dengan klik kotak jam pada obat yang dipilih.");
        muatKotakPemberian();
    }//GEN-LAST:event_BtnEdit1ActionPerformed

    private void BtnEdit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEdit1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEdit1KeyPressed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from pemberian_obat_ranap where tanggal_catat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
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
        String cari=TCari.getText().trim();
        String sql="SELECT h.no_surat,h.no_rawat,h.tanggal_catat,h.jam_catat,h.nik_pencatat,"+
                   "IFNULL(pg.nama,'') nama_pencatat,rp.no_rkm_medis,p.nm_pasien,"+
                   "IFNULL(h.hubungan_pasien,'') hubungan_pasien,h.status_dokumen " +
                   "FROM pemberian_obat_ranap h " +
                   "INNER JOIN reg_periksa rp ON rp.no_rawat=h.no_rawat " +
                   "INNER JOIN pasien p ON p.no_rkm_medis=rp.no_rkm_medis " +
                   "LEFT JOIN pegawai pg ON pg.nik=h.nik_pencatat " +
                   "WHERE h.tanggal_catat BETWEEN ? AND ? ";
        if(!cari.equals("")){
            sql += "AND (h.no_surat LIKE ? OR h.no_rawat LIKE ? OR p.nm_pasien LIKE ? OR h.nik_pencatat LIKE ? " +
                   "OR EXISTS(SELECT 1 FROM pemberian_obat_ranap_detail d WHERE d.no_surat=h.no_surat AND d.nama_obat LIKE ?)) ";
        }
        sql += "ORDER BY h.tanggal_catat DESC,h.jam_catat DESC LIMIT 500";
        try{
            ps=koneksi.prepareStatement(sql);
            ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
            ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
            if(!cari.equals("")){
                String k="%"+cari+"%";
                ps.setString(3,k);ps.setString(4,k);ps.setString(5,k);ps.setString(6,k);ps.setString(7,k);
            }
            rs=ps.executeQuery();
            while(rs.next()){
                String[] row=new String[59];
                java.util.Arrays.fill(row,"");
                row[0]=rs.getString("no_rawat");
                row[1]=rs.getString("no_rkm_medis");
                row[2]=rs.getString("nm_pasien");
                row[3]=rs.getString("no_surat");
                row[4]=rs.getString("tanggal_catat");
                row[5]=rs.getString("jam_catat");
                row[8]=rs.getString("nik_pencatat");
                row[9]=rs.getString("nama_pencatat");
                row[55]=rs.getString("hubungan_pasien");
                row[56]=rs.getString("status_dokumen");
                tabMode.addRow(row);
            }
        }catch(Exception e){
            System.out.println("Notif tampil pemberian obat baru : "+e);
        }finally{
            try{if(rs!=null)rs.close();}catch(Exception e){}
            try{if(ps!=null)ps.close();}catch(Exception e){}
        }
        LCount.setText(""+tabMode.getRowCount());
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
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from pemberian_obat_ranap where tanggal_catat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
                "PORI"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),4,NoSurat);
//        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0) from surat_ic_rajal where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
//                "ICRJ"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),3,NoSurat); 
        NoSurat.requestFocus();
    } 
    
    private void autoNumberX(String tglPilih) {
    Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from pemberian_obat_ranap where tanggal_catat='"+Valid.SetTgl(tglPilih+"")+"' ",
                "PORI"+Valid.SetTgl(tglPilih+"").replaceAll("-", ""),4,NoSurat);
    }
    
    public void emptTekswaktu() {
        KodeDokter.setText("");
//        NamaDokter.setText("");
//        waktu.setSelectedIndex(0);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from pemberian_obat_ranap where tanggal_catat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
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

    // =====================================================================
    // UI KOTAK v15 - basis V14; nama obat dan nama petugas dibuat selalu terbaca jelas
    // =====================================================================
    private void pasangModeKotakV3(){
        try{
            internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52,120,190)),
                "::[ PEMBERIAN OBAT RAWAT INAP - MODE KOTAK v39 - TTE PER PEMBERIAN"));

            // V26: lepaskan seluruh UI legacy dari internalFrame1.
            // Ini menghilangkan Tgl.Rawat, Keyword, Petugas, Baru, Cetak lama,
            // Record, Keluar lama, PanelInput, serta ruang kosongnya.
            try{
                internalFrame1.remove(Scroll);
                internalFrame1.remove(jPanel3);
                internalFrame1.remove(PanelInput);

                jPanel3.setVisible(false);
                jPanel3.setMinimumSize(new java.awt.Dimension(0,0));
                jPanel3.setPreferredSize(new java.awt.Dimension(0,0));
                jPanel3.setMaximumSize(new java.awt.Dimension(0,0));

                PanelInput.setVisible(false);
                PanelInput.setMinimumSize(new java.awt.Dimension(0,0));
                PanelInput.setPreferredSize(new java.awt.Dimension(0,0));
                PanelInput.setMaximumSize(new java.awt.Dimension(0,0));
            }catch(Exception ignored){}

            final javax.swing.JPanel rootKotak=new javax.swing.JPanel(new java.awt.BorderLayout(10,10));
            rootKotak.setBorder(javax.swing.BorderFactory.createEmptyBorder(8,10,8,10));
            rootKotak.setBackground(new java.awt.Color(242,246,250));

            javax.swing.JPanel atas=new javax.swing.JPanel(new java.awt.BorderLayout(8,0));
            atas.setBackground(java.awt.Color.WHITE);
            atas.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(1,1,1,1,new java.awt.Color(218,226,236)),
                javax.swing.BorderFactory.createEmptyBorder(4,6,4,6)));

            javax.swing.JPanel barTanggalV20=new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT,5,3));
            barTanggalV20.setOpaque(false);
            javax.swing.JLabel lblTanggalV23=new javax.swing.JLabel("PANTAUAN TANGGAL");
            lblTanggalV23.setFont(lblTanggalV23.getFont().deriveFont(java.awt.Font.BOLD,10.5f));
            lblTanggalV23.setForeground(new java.awt.Color(31,78,121));
            barTanggalV20.add(lblTanggalV23);

            javax.swing.JPanel barAksiV20=new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT,5,3));
            barAksiV20.setOpaque(false);

            barTanggalV20.add(new javax.swing.JLabel("Tanggal"));
            spTanggalKotak=new javax.swing.JSpinner(new javax.swing.SpinnerDateModel());
            spTanggalKotak.setEditor(new javax.swing.JSpinner.DateEditor(spTanggalKotak,"dd-MM-yyyy"));
            spTanggalKotak.setPreferredSize(new java.awt.Dimension(120,28));
            barTanggalV20.add(spTanggalKotak);

            // Navigasi tanggal V18.1 - semua komponen dibuat sebelum di-add ke panel.
            btnPrevV18=new javax.swing.JButton("‹");
            btnHariIniV18=new javax.swing.JButton("Hari Ini");
            btnNextV18=new javax.swing.JButton("›");
            btnRiwayat7V18=new javax.swing.JButton("Riwayat 7 Hari");
            lblTanggalAktifV18=new javax.swing.JLabel("");

            for(javax.swing.JButton bx:new javax.swing.JButton[]{btnPrevV18,btnHariIniV18,btnNextV18,btnRiwayat7V18}){
                bx.setUI(new javax.swing.plaf.basic.BasicButtonUI());
                bx.setOpaque(true);
                bx.setContentAreaFilled(true);
                bx.setFocusPainted(false);
                bx.setBackground(new java.awt.Color(245,247,250));
                bx.setForeground(new java.awt.Color(45,58,72));
                bx.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(new java.awt.Color(205,214,224)),
                    javax.swing.BorderFactory.createEmptyBorder(5,9,5,9)));
            }
            btnPrevV18.setToolTipText("Tanggal sebelumnya");
            btnHariIniV18.setToolTipText("Kembali ke hari ini");
            btnNextV18.setToolTipText("Tanggal berikutnya");
            btnRiwayat7V18.setVisible(false);

            btnPrevV18.addActionListener(e->{
                java.util.Date d=(java.util.Date)spTanggalKotak.getValue();
                spTanggalKotak.setValue(new java.util.Date(d.getTime()-86400000L));
                muatKotakPemberian();
            });
            btnNextV18.addActionListener(e->{
                java.util.Date d=(java.util.Date)spTanggalKotak.getValue();
                spTanggalKotak.setValue(new java.util.Date(d.getTime()+86400000L));
                muatKotakPemberian();
            });
            btnHariIniV18.addActionListener(e->{
                spTanggalKotak.setValue(new java.util.Date());
                muatKotakPemberian();
            });
            btnRiwayat7V18.addActionListener(e->lihatRiwayat7HariV18());

            barTanggalV20.add(btnPrevV18);
            barTanggalV20.add(btnHariIniV18);
            barTanggalV20.add(btnNextV18);

            javax.swing.JButton btnAmbil=new javax.swing.JButton("Pilih Obat dari Resep");
            javax.swing.JButton btnManual=new javax.swing.JButton("Tambah Obat Manual");
            javax.swing.JButton btnRefresh=new javax.swing.JButton("Refresh Kotak");
            javax.swing.JButton btnTambahJadwal=new javax.swing.JButton("Jadwal Pemberian Obat");
            javax.swing.JButton btnHapusObatV32=new javax.swing.JButton("Hapus Obat");
            javax.swing.JButton btnRangkumanV24=new javax.swing.JButton("Rangkuman Pemberian");
            javax.swing.JButton btnCetakV21=new javax.swing.JButton("Cetak");
            javax.swing.JButton btnKeluarV22=new javax.swing.JButton("Keluar");
            javax.swing.JLabel lblAksiV14=new javax.swing.JLabel("AKSI");
            lblAksiV14.setFont(lblAksiV14.getFont().deriveFont(java.awt.Font.BOLD,10.8f));
            lblAksiV14.setForeground(new java.awt.Color(31,78,121));
            lblAksiV14.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,4,0,4));
            barAksiV20.add(lblAksiV14);

            btnAmbil.setText("Pilih dari Resep");
            btnManual.setText("Tambah Manual");
            btnTambahJadwal.setText("Atur Jadwal");
            btnRefresh.setText("Refresh");
            btnAmbil.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnManual.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnTambahJadwal.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnRefresh.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnCetakV21.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnHapusObatV32.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnRangkumanV24.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnKeluarV22.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnAmbil.setOpaque(true); btnManual.setOpaque(true); btnTambahJadwal.setOpaque(true); btnRefresh.setOpaque(true);
            btnAmbil.setContentAreaFilled(true); btnManual.setContentAreaFilled(true); btnTambahJadwal.setContentAreaFilled(true); btnRefresh.setContentAreaFilled(true); btnCetakV21.setContentAreaFilled(true);
            btnHapusObatV32.setOpaque(true); btnHapusObatV32.setContentAreaFilled(true);
            btnHapusObatV32.setPreferredSize(new java.awt.Dimension(105,30));
            btnHapusObatV32.setToolTipText("Hapus obat yang Anda input sendiri, selama belum memiliki realisasi klinis");
            btnRangkumanV24.setOpaque(true); btnRangkumanV24.setContentAreaFilled(true);
            btnRangkumanV24.setPreferredSize(new java.awt.Dimension(145,30));
            btnRangkumanV24.setToolTipText("Lihat rangkuman obat yang diberikan, tanggal, jam, petugas dan validasi");
            btnCetakV21.setOpaque(true);
            btnCetakV21.setPreferredSize(new java.awt.Dimension(72,30));
            btnCetakV21.setToolTipText("Cetak pantauan pemberian obat pasien pada tanggal yang dipilih");
            btnKeluarV22.setOpaque(true); btnKeluarV22.setContentAreaFilled(true);
            btnKeluarV22.setPreferredSize(new java.awt.Dimension(72,30));
            btnKeluarV22.setToolTipText("Tutup Catatan Pemberian Obat");
            btnAmbil.setPreferredSize(new java.awt.Dimension(155,32));
            btnManual.setPreferredSize(new java.awt.Dimension(150,32));
            btnTambahJadwal.setPreferredSize(new java.awt.Dimension(170,32));
            btnRefresh.setPreferredSize(new java.awt.Dimension(82,32));

            styleTombolUtamaV13(btnAmbil,new java.awt.Color(30,111,190),java.awt.Color.WHITE);
            styleTombolUtamaV13(btnManual,new java.awt.Color(44,142,96),java.awt.Color.WHITE);
            styleTombolUtamaV13(btnTambahJadwal,new java.awt.Color(96,79,170),java.awt.Color.WHITE);
            styleTombolUtamaV13(btnRefresh,new java.awt.Color(245,247,250),new java.awt.Color(45,58,72));
            styleTombolUtamaV13(btnHapusObatV32,new java.awt.Color(170,84,70),java.awt.Color.WHITE);
            styleTombolUtamaV13(btnRangkumanV24,new java.awt.Color(46,121,139),java.awt.Color.WHITE);
            styleTombolUtamaV13(btnCetakV21,new java.awt.Color(67,83,98),java.awt.Color.WHITE);
            styleTombolUtamaV13(btnKeluarV22,new java.awt.Color(174,72,72),java.awt.Color.WHITE);
            btnHapusObatV32.addActionListener(e->hapusObatByUserV32());
            btnRangkumanV24.addActionListener(e->lihatRangkumanPemberianV24());
            btnCetakV21.addActionListener(e->cetakPantauanV21());
            btnKeluarV22.addActionListener(e->{
                try{BtnKeluar.doClick();}catch(Exception ex){dispose();}
            });
            barAksiV20.add(btnAmbil);
            barAksiV20.add(btnManual);
            barAksiV20.add(btnTambahJadwal);
            barAksiV20.add(btnHapusObatV32);
            barAksiV20.add(btnRefresh);
            barAksiV20.add(btnRangkumanV24);
            barAksiV20.add(btnCetakV21);
            barAksiV20.add(btnKeluarV22);
            lblRingkasKotak=new javax.swing.JLabel("  Pilih pasien untuk melihat pantauan pemberian");
            lblRingkasKotak.setFont(lblRingkasKotak.getFont().deriveFont(java.awt.Font.BOLD,11f));
            lblRingkasKotak.setForeground(new java.awt.Color(46,62,80));
            lblRingkasKotak.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,8,0,0));
            barAksiV20.add(lblRingkasKotak);
            atas.add(barTanggalV20,java.awt.BorderLayout.WEST);
            atas.add(barAksiV20,java.awt.BorderLayout.CENTER);

            // HEADER-ONLY V12:
            // UI kotak V9 sudah dibuat terlebih dahulu. Header dipasang dalam try/catch terpisah
            // sehingga kegagalan header tidak pernah mengembalikan form ke layout lama.
            pasangHeaderExcelSafeV12(rootKotak, atas);

            modelKotakPantau=new javax.swing.table.DefaultTableModel(){
                public boolean isCellEditable(int r,int c){return false;}
            };
            tbKotakPantau=new javax.swing.JTable(modelKotakPantau);
            tbKotakPantau.setRowHeight(62);
            tbKotakPantau.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tbKotakPantau.setShowGrid(true);
            tbKotakPantau.setGridColor(new java.awt.Color(224,230,237));
            tbKotakPantau.setIntercellSpacing(new java.awt.Dimension(1,1));
            tbKotakPantau.setSelectionBackground(new java.awt.Color(214,232,250));
            tbKotakPantau.setSelectionForeground(new java.awt.Color(25,45,70));
            tbKotakPantau.setFont(tbKotakPantau.getFont().deriveFont(11f));
            tbKotakPantau.getTableHeader().setReorderingAllowed(false);
            tbKotakPantau.getTableHeader().setPreferredSize(new java.awt.Dimension(0,36));
            tbKotakPantau.getTableHeader().setFont(tbKotakPantau.getTableHeader().getFont().deriveFont(java.awt.Font.BOLD,11f));
            tbKotakPantau.getTableHeader().setBackground(new java.awt.Color(31,78,121));
            tbKotakPantau.getTableHeader().setForeground(java.awt.Color.WHITE);
            tbKotakPantau.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            tbKotakPantau.setDefaultRenderer(Object.class,new javax.swing.table.DefaultTableCellRenderer(){
                @Override public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table,Object value,boolean selected,boolean focus,int row,int col){
                    java.awt.Component c=super.getTableCellRendererComponent(table,value,selected,focus,row,col);
                    setBorder(javax.swing.BorderFactory.createEmptyBorder(5,7,5,7));
                    if(col>=2) setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    else setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

                    String vv=value==null?"":value.toString();
                    boolean kelompok=false;
                    try{
                        Object first=table.getValueAt(row,0);
                        kelompok=first!=null && first.toString().startsWith("KELOMPOK::");
                    }catch(Exception ignored){}
                    if(kelompok){
                        setFont(table.getFont().deriveFont(java.awt.Font.BOLD,11.5f));
                        setForeground(new java.awt.Color(31,78,121));
                        setBackground(new java.awt.Color(224,236,248));
                        if(col==0) setText(vv.replace("KELOMPOK::",""));
                        else setText("");
                        return c;
                    }

                    if(col==4){
                        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        setIcon(null);
                        setText(value==null?"":value.toString());
                        setToolTipText(value!=null && value.toString().contains("Sudah")?
                            "Klik untuk melihat tanda tangan pasien/keluarga dan detail verifikasi.":
                            "Belum ada TTE pasien/keluarga.");
                    }else{
                        setIcon(null);
                    }

                    if(col==0){
                        setFont(table.getFont().deriveFont(java.awt.Font.BOLD,11f));
                        setForeground(new java.awt.Color(26,58,88));
                        setToolTipText(value==null?null:value.toString());
                    }else{
                        setFont(table.getFont().deriveFont(java.awt.Font.PLAIN,11f));
                        if(col!=4) setToolTipText(value==null || value.toString().trim().equals("")?null:value.toString());
                    }

                    if(selected) return c;
                    c.setForeground(new java.awt.Color(35,45,55));
                    c.setBackground((row%2==0)?java.awt.Color.WHITE:new java.awt.Color(249,251,253));
                    if(col==4){
                        Long jt=tteJadwalPerBarisV38.get(row);
                        if(jt!=null){
                            c.setBackground(new java.awt.Color(237,248,241));
                            setForeground(new java.awt.Color(31,122,76));
                            setFont(table.getFont().deriveFont(java.awt.Font.BOLD,10.5f));
                        }else{
                            c.setBackground(new java.awt.Color(248,250,252));
                            setForeground(new java.awt.Color(125,134,142));
                            setFont(table.getFont().deriveFont(java.awt.Font.PLAIN,10.3f));
                        }
                    }
                    if(col>=5 && col<=8){
                        String tip=tooltipKotakV35.get(row+":"+col);
                        setToolTipText(tip);
                    }
                    if(col>=5 && col<=8 && value!=null){
                        String s=value.toString();
                        if(s.contains("DIBERIKAN")) c.setBackground(new java.awt.Color(211,245,224));
                        else if(s.contains("DITUNDA")) c.setBackground(new java.awt.Color(218,235,252));
                        else if(s.contains("MMO")) c.setBackground(new java.awt.Color(236,222,250));
                        else if(s.contains("STOP") || s.contains("TIDAK DIBERIKAN")) c.setBackground(new java.awt.Color(252,218,218));
                        else if(s.contains("TERJADWAL")) c.setBackground(new java.awt.Color(255,242,204));
                        else c.setBackground(new java.awt.Color(246,248,250));
                    }
                    return c;
                }
            });
            tbKotakPantau.addMouseListener(new java.awt.event.MouseAdapter(){
                @Override public void mouseClicked(java.awt.event.MouseEvent e){
                    if(e.getClickCount()==1){
                        int r=tbKotakPantau.rowAtPoint(e.getPoint());
                        int c=tbKotakPantau.columnAtPoint(e.getPoint());
                        if(r>=0 && c==4){
                            Long jt=tteJadwalPerBarisV38.get(r);
                            if(jt!=null) lihatTtePasienV37(jt);
                        }else if(r>=0 && c>=5 && c<=8){
                            aksiKotakPemberian(r,c);
                        }
                    }
                }
            });
            javax.swing.JScrollPane scKotak=new javax.swing.JScrollPane(tbKotakPantau);
            scKotak.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(212,221,232)));
            scKotak.getViewport().setBackground(java.awt.Color.WHITE);

            javax.swing.JPanel areaKotakV14=new javax.swing.JPanel(new java.awt.BorderLayout(0,5));
            areaKotakV14.setOpaque(false);
            javax.swing.JLabel judulKotakV14=new javax.swing.JLabel("PANTAUAN PEMBERIAN OBAT");
            judulKotakV14.setFont(judulKotakV14.getFont().deriveFont(java.awt.Font.BOLD,11.5f));
            judulKotakV14.setForeground(new java.awt.Color(31,78,121));
            judulKotakV14.setBorder(javax.swing.BorderFactory.createEmptyBorder(2,2,1,2));
            javax.swing.JPanel judulAreaV23=new javax.swing.JPanel(new java.awt.BorderLayout());
            judulAreaV23.setOpaque(false);
            judulAreaV23.add(judulKotakV14,java.awt.BorderLayout.WEST);
            javax.swing.JLabel subPantauV23=new javax.swing.JLabel("Klik data P/S/SO/M untuk melihat detail pemberian dan TTE khusus kejadian tersebut");
            subPantauV23.setForeground(new java.awt.Color(110,120,132));
            subPantauV23.setFont(subPantauV23.getFont().deriveFont(java.awt.Font.PLAIN,10f));
            judulAreaV23.add(subPantauV23,java.awt.BorderLayout.EAST);
            areaKotakV14.add(judulAreaV23,java.awt.BorderLayout.NORTH);
            areaKotakV14.add(scKotak,java.awt.BorderLayout.CENTER);
            rootKotak.add(areaKotakV14,java.awt.BorderLayout.CENTER);

            javax.swing.JPanel bawahV13=new javax.swing.JPanel();
            bawahV13.setLayout(new javax.swing.BoxLayout(bawahV13,javax.swing.BoxLayout.Y_AXIS));
            bawahV13.setBackground(java.awt.Color.WHITE);
            bawahV13.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(214,223,233)),
                javax.swing.BorderFactory.createEmptyBorder(5,7,5,7)));

            // Kontrol utama satu area di bawah, agar header dan pantauan lebih lapang.
            bawahV13.add(atas);

            javax.swing.JPanel legendaV21=new javax.swing.JPanel(new java.awt.BorderLayout(8,0));
            legendaV21.setOpaque(false);
            javax.swing.JLabel petunjuk=new javax.swing.JLabel(
                "<html><b>Klik P/S/SO/M</b> untuk mencatat atau melihat pemberian. &nbsp;&nbsp;"+
                "<font color='#228B55'>● Diberikan</font> &nbsp; "+
                "<font color='#D99500'>● Terjadwal</font> &nbsp; "+
                "<font color='#2D76B8'>● Ditunda</font> &nbsp; "+
                "<font color='#7B4AB3'>● MMO</font> &nbsp; "+
                "<font color='#C84444'>● STOP / Tidak Diberikan</font></html>");
            petunjuk.setForeground(new java.awt.Color(70,80,92));
            legendaV21.add(petunjuk,java.awt.BorderLayout.CENTER);
            javax.swing.JLabel hint=new javax.swing.JLabel("Tanggal pantauan menentukan data yang dicetak");
            hint.setForeground(new java.awt.Color(120,130,142));
            hint.setFont(hint.getFont().deriveFont(java.awt.Font.ITALIC,10f));
            legendaV21.add(hint,java.awt.BorderLayout.EAST);
            bawahV13.add(legendaV21);
            rootKotak.add(bawahV13,java.awt.BorderLayout.SOUTH);

            internalFrame1.remove(Scroll);
            internalFrame1.add(rootKotak,java.awt.BorderLayout.CENTER);
            internalFrame1.setPreferredSize(null);
            internalFrame1.revalidate();
            internalFrame1.repaint();
            revalidate();
            repaint();

            btnRefresh.addActionListener(new java.awt.event.ActionListener(){public void actionPerformed(java.awt.event.ActionEvent e){muatKotakPemberian();}});
            spTanggalKotak.addChangeListener(new javax.swing.event.ChangeListener(){public void stateChanged(javax.swing.event.ChangeEvent e){muatKotakPemberian();}});
            btnAmbil.addActionListener(new java.awt.event.ActionListener(){public void actionPerformed(java.awt.event.ActionEvent e){ambilObatResepKotak();}});
            btnManual.addActionListener(new java.awt.event.ActionListener(){public void actionPerformed(java.awt.event.ActionEvent e){tambahObatManualKotak();}});
            btnTambahJadwal.addActionListener(new java.awt.event.ActionListener(){public void actionPerformed(java.awt.event.ActionEvent e){buatJadwalCepatKotak();}});
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Gagal memasang UI Kotak v39: "+ex.getMessage());
        }
    }

    private java.sql.Date tanggalKotak(){
        java.util.Date d=spTanggalKotak==null?new java.util.Date():(java.util.Date)spTanggalKotak.getValue();
        return new java.sql.Date(d.getTime());
    }


    // =====================================================================
    // HEADER PROFESIONAL V13 (basis V12 SAFE)
    // HANYA HEADER. Matriks, tombol, renderer warna, dan event klik kotak
    // tetap menggunakan implementasi V9.
    // =====================================================================
    private void pasangHeaderExcelSafeV12(final javax.swing.JPanel rootKotak, final javax.swing.JPanel toolbarV9){
        try{
            javax.swing.JPanel wrap=new javax.swing.JPanel();
            wrap.setLayout(new javax.swing.BoxLayout(wrap,javax.swing.BoxLayout.Y_AXIS));
            wrap.setBackground(new java.awt.Color(245,248,252));

            final java.awt.Color navy=new java.awt.Color(31,78,121);
            final java.awt.Color navySoft=new java.awt.Color(235,242,249);
            final java.awt.Color border=new java.awt.Color(211,221,232);
            final java.awt.Color text=new java.awt.Color(38,52,68);
            final java.awt.Color muted=new java.awt.Color(96,108,122);

            // ===== KARTU HEADER UTAMA =====
            javax.swing.JPanel kartuUtama=new javax.swing.JPanel(new java.awt.BorderLayout(12,0));
            kartuUtama.setBackground(java.awt.Color.WHITE);
            kartuUtama.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(border),
                javax.swing.BorderFactory.createEmptyBorder(8,12,8,12)));

            javax.swing.JPanel judulBox=new javax.swing.JPanel();
            judulBox.setLayout(new javax.swing.BoxLayout(judulBox,javax.swing.BoxLayout.Y_AXIS));
            judulBox.setOpaque(false);
            judulBox.setPreferredSize(new java.awt.Dimension(330,76));

            javax.swing.JLabel judul=new javax.swing.JLabel("CATATAN PEMBERIAN OBAT");
            judul.setFont(judul.getFont().deriveFont(java.awt.Font.BOLD,18f));
            judul.setForeground(navy);
            judul.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

            javax.swing.JLabel subjudul=new javax.swing.JLabel("Rawat Inap • Monitoring Pemberian & Verifikasi");
            subjudul.setFont(subjudul.getFont().deriveFont(10.5f));
            subjudul.setForeground(muted);
            subjudul.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

            javax.swing.JLabel badge=new javax.swing.JLabel("  7 TEPAT & TERDOKUMENTASI  ");
            badge.setOpaque(true);
            badge.setBackground(new java.awt.Color(232,247,239));
            badge.setForeground(new java.awt.Color(35,125,82));
            badge.setFont(badge.getFont().deriveFont(java.awt.Font.BOLD,10f));
            badge.setBorder(javax.swing.BorderFactory.createEmptyBorder(4,6,4,6));
            badge.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

            judulBox.add(judul);
            judulBox.add(javax.swing.Box.createVerticalStrut(3));
            judulBox.add(subjudul);
            judulBox.add(javax.swing.Box.createVerticalStrut(9));
            judulBox.add(badge);
            kartuUtama.add(judulBox,java.awt.BorderLayout.WEST);

            javax.swing.JPanel identitas=new javax.swing.JPanel(new java.awt.GridLayout(4,2,10,4));
            identitas.setOpaque(false);
            identitas.setBorder(javax.swing.BorderFactory.createEmptyBorder(1,12,1,2));

            hxNoRM=labelNilaiV13("-");
            hxNama=labelNilaiV13("-");
            hxTglLahir=labelNilaiV13("-");
            hxJK=labelNilaiV13("-");
            hxNoRawat=labelNilaiV13("-");

            identitas.add(labelKunciV13("No. RM")); identitas.add(hxNoRM);
            identitas.add(labelKunciV13("Nama Pasien")); identitas.add(hxNama);
            identitas.add(labelKunciV13("Tanggal Lahir / JK"));
            javax.swing.JPanel lj=new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT,0,0));
            lj.setOpaque(false);
            lj.add(hxTglLahir); 
            javax.swing.JLabel slash=new javax.swing.JLabel("  /  "); slash.setForeground(muted); lj.add(slash); 
            lj.add(hxJK);
            identitas.add(lj);
            identitas.add(labelKunciV13("No. Rawat")); identitas.add(hxNoRawat);
            kartuUtama.add(identitas,java.awt.BorderLayout.CENTER);
            wrap.add(kartuUtama);
            wrap.add(javax.swing.Box.createVerticalStrut(6));

            // ===== BARIS INFORMASI KLINIS =====
            javax.swing.JPanel infoRow=new javax.swing.JPanel(new java.awt.GridLayout(1,3,8,0));
            infoRow.setOpaque(false);

            javax.swing.JPanel klinis=kartuInfoV13("INFORMASI KLINIS", navy);
            javax.swing.JPanel klinisIsi=new javax.swing.JPanel(new java.awt.GridLayout(3,2,8,4));
            klinisIsi.setOpaque(false);
            hxTglMasuk=labelNilaiV13("-");
            hxDPJP=labelNilaiV13("-");
            hxAlergi=labelNilaiV13("-");
            klinisIsi.add(labelKunciV13("Tanggal Masuk")); klinisIsi.add(hxTglMasuk);
            klinisIsi.add(labelKunciV13("Dokter DPJP")); klinisIsi.add(hxDPJP);
            klinisIsi.add(labelKunciV13("Alergi Obat")); klinisIsi.add(hxAlergi);
            klinis.add(klinisIsi,java.awt.BorderLayout.CENTER);
            infoRow.add(klinis);

            javax.swing.JPanel tepatCard=kartuInfoV13("PERHATIAN 7 TEPAT", new java.awt.Color(38,131,89));
            javax.swing.JLabel tepat=new javax.swing.JLabel(
                "<html><table cellpadding='1' cellspacing='0'>"+
                "<tr><td>1. Tepat Pasien</td><td>&nbsp;&nbsp;5. Tepat Cara</td></tr>"+
                "<tr><td>2. Tepat Obat</td><td>&nbsp;&nbsp;6. Tepat Informasi</td></tr>"+
                "<tr><td>3. Tepat Dosis</td><td>&nbsp;&nbsp;7. Tepat Dokumentasi</td></tr>"+
                "<tr><td>4. Tepat Waktu</td><td></td></tr></table></html>");
            tepat.setForeground(text);
            tepat.setBorder(javax.swing.BorderFactory.createEmptyBorder(4,2,2,2));
            tepatCard.add(tepat,java.awt.BorderLayout.CENTER);
            infoRow.add(tepatCard);

            javax.swing.JPanel waktuCard=kartuInfoV13("PETUNJUK WAKTU", new java.awt.Color(101,74,172));
            javax.swing.JLabel waktu=new javax.swing.JLabel(
                "<html><table cellpadding='1' cellspacing='0'>"+
                "<tr><td width='78'><b>1×1 Pagi</b></td><td width='65'>:</td><td width='115'>06–07</td>"+
                "<td width='48'><b>3×1</b></td><td width='18'>:</td><td>06–07&nbsp;&nbsp;12–13&nbsp;&nbsp;19–20</td></tr>"+
                "<tr><td><b>1×1 Malam</b></td><td>:</td><td>19–20</td>"+
                "<td><b>4×1</b></td><td>:</td><td>06–07&nbsp;&nbsp;12–13&nbsp;&nbsp;18–19&nbsp;&nbsp;22–23</td></tr>"+
                "<tr><td><b>2×1</b></td><td>:</td><td>06–07&nbsp;&nbsp;18–19</td>"+
                "<td><b>5×1</b></td><td>:</td><td>06–07&nbsp;&nbsp;10–11&nbsp;&nbsp;15–16&nbsp;&nbsp;20–21</td></tr>"+
                "</table></html>");
            waktu.setForeground(text);
            waktu.setBorder(javax.swing.BorderFactory.createEmptyBorder(4,2,2,2));
            waktuCard.add(waktu,java.awt.BorderLayout.CENTER);
            infoRow.add(waktuCard);

            wrap.add(infoRow);
            wrap.add(javax.swing.Box.createVerticalStrut(6));

            // ===== PETUNJUK PENGISIAN RINGKAS =====
            javax.swing.JPanel petunjukCard=new javax.swing.JPanel(new java.awt.BorderLayout(8,5));
            petunjukCard.setBackground(new java.awt.Color(255,249,232));
            petunjukCard.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(238,208,126)),
                javax.swing.BorderFactory.createEmptyBorder(7,10,7,10)));

            javax.swing.JLabel judulPetunjuk=new javax.swing.JLabel("PETUNJUK PENGISIAN");
            judulPetunjuk.setFont(judulPetunjuk.getFont().deriveFont(java.awt.Font.BOLD,10.5f));
            judulPetunjuk.setForeground(new java.awt.Color(153,100,12));
            petunjukCard.add(judulPetunjuk,java.awt.BorderLayout.NORTH);

            javax.swing.JLabel isi=new javax.swing.JLabel(
                "<html>"
                +"1. Isi dengan lengkap dan jelas catatan pemberian obat sesuai dengan kolom yang disediakan.<br>"
                +"2. Jam dan paraf pemberian obat diisi setelah obat diberikan kepada pasien.<br>"
                +"3. Tulis dengan kode <b>MMO</b> pada kolom jam pemberian, jika pasien menolak minum obat.<br>"
                +"4. Jika ada perubahan dosis pemberian, aturan pakai dan rute pemberian obat, pada kolom jam pemberian tulis <b>STOP</b>, lalu lanjutkan pada baris yang baru.<br>"
                +"5. Jika ada penghentian obat, tuliskan <b>STOP</b> pada kolom dan jam pemberian. Pada kolom keterangan tuliskan alasan penghentian pemberian obat."
                +"</html>");
            isi.setForeground(new java.awt.Color(78,68,45));
            isi.setFont(isi.getFont().deriveFont(10.2f));
            petunjukCard.add(isi,java.awt.BorderLayout.CENTER);
            wrap.add(petunjukCard);
            wrap.add(javax.swing.Box.createVerticalStrut(7));

            // V21: header tetap di atas, kontrol operasional dipindahkan ke bawah.
            rootKotak.remove(toolbarV9);
            rootKotak.add(wrap,java.awt.BorderLayout.NORTH);
            rootKotak.revalidate();
            rootKotak.repaint();
            muatHeaderExcelSafeV12();
        }catch(Exception ex){
            try{
                rootKotak.removeAll();
                rootKotak.setLayout(new java.awt.BorderLayout(8,8));
                rootKotak.add(toolbarV9,java.awt.BorderLayout.SOUTH);
                javax.swing.JScrollPane safeScroll=new javax.swing.JScrollPane(tbKotakPantau);
                rootKotak.add(safeScroll,java.awt.BorderLayout.CENTER);
                javax.swing.JLabel info=new javax.swing.JLabel(
                    "  Header profesional gagal dimuat, tetapi MODE KOTAK tetap aktif. Detail: "+ex.getMessage());
                rootKotak.add(info,java.awt.BorderLayout.SOUTH);
                rootKotak.revalidate();
                rootKotak.repaint();
            }catch(Exception ignored){}
            System.out.println("Header V13: "+ex);
        }
    }

    private javax.swing.JLabel labelKunciV13(String teks){
        javax.swing.JLabel l=new javax.swing.JLabel(teks+" :");
        l.setForeground(new java.awt.Color(100,112,126));
        l.setFont(l.getFont().deriveFont(java.awt.Font.PLAIN,10.5f));
        return l;
    }

    private javax.swing.JLabel labelNilaiV13(String teks){
        javax.swing.JLabel l=new javax.swing.JLabel(teks);
        l.setForeground(new java.awt.Color(34,48,63));
        l.setFont(l.getFont().deriveFont(java.awt.Font.BOLD,11f));
        return l;
    }

    private javax.swing.JPanel kartuInfoV13(String judul, java.awt.Color aksen){
        javax.swing.JPanel card=new javax.swing.JPanel(new java.awt.BorderLayout(0,5));
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(218,226,236)),
            javax.swing.BorderFactory.createEmptyBorder(7,9,7,9)));
        javax.swing.JLabel h=new javax.swing.JLabel(judul);
        h.setForeground(aksen);
        h.setFont(h.getFont().deriveFont(java.awt.Font.BOLD,10f));
        h.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,1,0,new java.awt.Color(231,236,242)));
        card.add(h,java.awt.BorderLayout.NORTH);
        return card;
    }

    private void styleTombolUtamaV13(javax.swing.JButton b, java.awt.Color bg, java.awt.Color fg){
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setFont(b.getFont().deriveFont(java.awt.Font.BOLD,10.5f));
        b.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(205,214,224)),
            javax.swing.BorderFactory.createEmptyBorder(5,10,5,10)));
        b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void muatHeaderExcelSafeV12(){
        if(hxNoRM==null) return;
        try{
            hxNoRM.setText(TNoRM.getText().trim().isEmpty()?"-":TNoRM.getText().trim());
            hxNama.setText(TPasien.getText().trim().isEmpty()?"-":TPasien.getText().trim());
            hxNoRawat.setText(TNoRw.getText().trim().isEmpty()?"-":TNoRw.getText().trim());
        }catch(Exception e){}

        if(TNoRw.getText().trim().isEmpty()){
            hxTglLahir.setText("-"); hxJK.setText("-"); hxTglMasuk.setText("-");
            hxDPJP.setText("-"); hxAlergi.setText("-");
            return;
        }

        // Query ini hanya untuk mengisi header; jika gagal tidak mempengaruhi matriks kotak.
        String q="SELECT rp.tgl_registrasi,p.tgl_lahir,p.jk,IFNULL(d.nm_dokter,'-') nm_dokter,"+
                 "IFNULL((SELECT h.alergi_obat FROM pemberian_obat_ranap h "+
                 "WHERE h.no_rawat=rp.no_rawat AND h.status_dokumen<>'Batal' "+
                 "ORDER BY h.tanggal_catat DESC,h.jam_catat DESC LIMIT 1),'-') alergi "+
                 "FROM reg_periksa rp INNER JOIN pasien p ON p.no_rkm_medis=rp.no_rkm_medis "+
                 "LEFT JOIN dokter d ON d.kd_dokter=rp.kd_dokter WHERE rp.no_rawat=? LIMIT 1";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(q)){
            p.setString(1,TNoRw.getText().trim());
            try(java.sql.ResultSet r=p.executeQuery()){
                if(r.next()){
                    hxTglLahir.setText(r.getDate("tgl_lahir")==null?"-":r.getDate("tgl_lahir").toString());
                    hxJK.setText(r.getString("jk")==null?"-":r.getString("jk"));
                    hxTglMasuk.setText(r.getDate("tgl_registrasi")==null?"-":r.getDate("tgl_registrasi").toString());
                    hxDPJP.setText(r.getString("nm_dokter"));
                    String al=r.getString("alergi");
                    hxAlergi.setText(al==null||al.trim().isEmpty()?"-":al);
                    if(al!=null && !al.trim().isEmpty() && !al.trim().equals("-")){
                        hxAlergi.setForeground(new java.awt.Color(190,55,55));
                    }else{
                        hxAlergi.setForeground(new java.awt.Color(34,48,63));
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("Header Excel V12 data: "+ex);
        }
    }

    private String shiftV18(java.sql.Time t){
        if(t==null) return "";
        int h=t.toLocalTime().getHour();
        if(h>=5 && h<10) return "P";
        if(h>=10 && h<16) return "S";
        if(h>=16 && h<21) return "SO";
        return "M";
    }

    private int colShiftV18(String s){
        if("P".equals(s)) return 5;
        if("S".equals(s)) return 6;
        if("SO".equals(s)) return 7;
        if("M".equals(s)) return 8;
        return -1;
    }

    private String kategoriV18(String s){
        if(s==null) return "Lainnya";
        String x=s.trim().toLowerCase();
        if(x.equals("oral")) return "Oral";
        if(x.equals("parenteral")) return "Parenteral";
        if(x.equals("inhalasi")) return "Inhalasi";
        if(x.equals("topikal")) return "Topikal";
        return "Lainnya";
    }

    private String judulKelompokV18(String k){
        if("Oral".equals(k)) return "OBAT ORAL";
        if("Parenteral".equals(k)) return "OBAT PARENTERAL";
        if("Inhalasi".equals(k)) return "OBAT INHALASI";
        if("Topikal".equals(k)) return "OBAT TOPIKAL";
        return "OBAT LAINNYA";
    }

    private javax.swing.ImageIcon thumbnailTteV38(String signaturePath){
        java.awt.image.BufferedImage img=bacaGambarTteV37(signaturePath);
        if(img==null) return null;
        int maxW=118,maxH=38;
        double sc=Math.min((double)maxW/img.getWidth(),(double)maxH/img.getHeight());
        sc=Math.min(1.0,sc);
        int w=Math.max(1,(int)Math.round(img.getWidth()*sc));
        int h=Math.max(1,(int)Math.round(img.getHeight()*sc));
        return new javax.swing.ImageIcon(img.getScaledInstance(w,h,java.awt.Image.SCALE_SMOOTH));
    }

    private void muatKotakPemberian(){
        if(tbKotakPantau==null) return;
        muatHeaderExcelSafeV12();

        kotakDetailPerBaris.clear();
        kotakJadwal.clear();
        tooltipKotakV35.clear();
        tteJadwalPerBarisV38.clear();

        Object[] cols={
            "NAMA OBAT / DOSIS / RUTE",
            "ATURAN PAKAI",
            "PARAF DPJP",
            "PARAF FARMASI",
            "TTE PASIEN",
            "P",
            "S",
            "SO",
            "M",
            "KETERANGAN"
        };

        modelKotakPantau.setDataVector(new Object[0][cols.length],cols);
        java.sql.Date tanggal=tanggalKotak();

        if(lblTanggalAktifV18!=null)
            lblTanggalAktifV18.setText(new java.text.SimpleDateFormat("dd MMMM yyyy",new java.util.Locale("id","ID")).format(tanggal));

        if(TNoRw.getText().trim().isEmpty()){
            lblRingkasKotak.setText("Pilih pasien untuk melihat pemberian obat hari ini");
            return;
        }

        String q=
            "SELECT d.id_detail,d.urut,d.kategori,d.nama_obat,d.dosis_sediaan,d.aturan_pakai,d.rute,d.keterangan AS ket_detail,"+
            "j.id_jadwal,j.jam_rencana,j.jam_realisasi,j.status_pemberian,j.nama_petugas,j.keterangan AS ket_jadwal,"+
            "IFNULL(j.dosis_pemberian,'') dosis_pemberian,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_validasi v WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='DPJP' AND v.status_validasi='Valid') AS dpjp,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_validasi v WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='Farmasi' AND v.status_validasi='Valid') AS farmasi,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi AND vv.status_verifikasi='Aktif' WHERE vi.id_jadwal=j.id_jadwal) AS tte,"+
            "(SELECT vv.signature_path FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) AS tte_path "+
            "FROM pemberian_obat_ranap h "+
            "INNER JOIN pemberian_obat_ranap_detail d ON d.no_surat=h.no_surat "+
            "LEFT JOIN pemberian_obat_ranap_jadwal j ON j.id_detail=d.id_detail AND j.tanggal_pemberian=? "+
            "WHERE h.no_rawat=? AND h.status_dokumen<>'Batal' AND d.status_obat<>'Selesai' "+
            "ORDER BY FIELD(d.kategori,'Oral','Parenteral','Inhalasi','Topikal','Lainnya'),d.urut,j.jam_rencana";

        class DV18{
            long id; String kat,nama,dosis,aturan,rute,ket;
            java.util.Map<String,String> isi=new java.util.HashMap<String,String>();
            java.util.Map<String,Long> ids=new java.util.HashMap<String,Long>();
            boolean dpjp=false,farmasi=false,tte=false;
            long tteJadwal=0L;
            String ttePath="";
        }
        java.util.LinkedHashMap<Long,DV18> map=new java.util.LinkedHashMap<Long,DV18>();
        int total=0,diberikan=0,belum=0,perlu=0,totalTte=0;

        try(java.sql.PreparedStatement p=koneksi.prepareStatement(q)){
            p.setDate(1,tanggal);
            p.setString(2,TNoRw.getText().trim());
            try(java.sql.ResultSet r=p.executeQuery()){
                while(r.next()){
                    long id=r.getLong("id_detail");
                    DV18 d=map.get(id);
                    if(d==null){
                        d=new DV18(); d.id=id; d.kat=kategoriV18(r.getString("kategori"));
                        d.nama=r.getString("nama_obat"); d.dosis=r.getString("dosis_sediaan");
                        d.aturan=r.getString("aturan_pakai"); d.rute=r.getString("rute");
                        d.ket=r.getString("ket_detail"); map.put(id,d);
                    }
                    long ij=r.getLong("id_jadwal");
                    if(!r.wasNull() && ij>0){
                        String sh=shiftV18(r.getTime("jam_rencana"));
                        String st=r.getString("status_pemberian");
                        String real=r.getTime("jam_realisasi")==null?"":new java.text.SimpleDateFormat("HH:mm").format(r.getTime("jam_realisasi"));
                        String pet=r.getString("nama_petugas")==null?"":r.getString("nama_petugas").trim();
                        boolean tteJadwal=r.getBoolean("tte");
                        String isi=(st==null?"":st.toUpperCase()) + (!real.equals("")?" "+real:"") + (!pet.equals("")?" • "+pet:"") + (tteJadwal?" • TTE ✓":"");
                        if(d.isi.containsKey(sh) && !d.isi.get(sh).equals("")) d.isi.put(sh,d.isi.get(sh)+" / "+isi);
                        else { d.isi.put(sh,isi); d.ids.put(sh,ij); }
                        d.dpjp |= r.getBoolean("dpjp");
                        d.farmasi |= r.getBoolean("farmasi");
                        d.tte |= r.getBoolean("tte");
                        if(tteJadwal){
                            d.tteJadwal=ij;
                            String tp=r.getString("tte_path");
                            if(tp!=null) d.ttePath=tp.trim();
                        }
                        String kj=r.getString("ket_jadwal");
                        if(kj!=null && !kj.trim().equals("")) d.ket=(d.ket==null||d.ket.trim().equals(""))?kj.trim():d.ket+" | "+kj.trim();

                        total++;
                        if("Diberikan".equals(st)) diberikan++;
                        else if("Terjadwal".equals(st)) belum++;
                        else perlu++;
                        if(tteJadwal) totalTte++;
                    }
                }
            }
        }catch(Exception ex){
            lblRingkasKotak.setText("Database: "+ex.getMessage());
        }

        String[] urut={"Oral","Parenteral","Inhalasi","Topikal","Lainnya"};
        int row=0;
        for(String kat:urut){
            java.util.List<DV18> list=new java.util.ArrayList<DV18>();
            for(DV18 d:map.values()) if(kat.equals(d.kat)) list.add(d);
            if(list.isEmpty()) continue;

            Object[] g=new Object[cols.length];
            g[0]="KELOMPOK::"+judulKelompokV18(kat);
            modelKotakPantau.addRow(g); row++;

            for(DV18 d:list){
                Object[] rr=new Object[cols.length];
                String nm=d.nama==null?"[Nama obat belum terisi]":d.nama;
                if(d.dosis!=null&&!d.dosis.trim().equals("")) nm+=" | "+d.dosis.trim();
                if(d.rute!=null&&!d.rute.trim().equals("")) nm+=" | "+d.rute.trim();
                rr[0]=nm;
                rr[1]=d.aturan==null?"":d.aturan;
                rr[2]=d.dpjp?"✓":"Belum";
                rr[3]=d.farmasi?"✓":"Belum";
                if(d.tte){
                    rr[4]="✓ Sudah TTE";
                }else{
                    rr[4]="Belum TTE";
                }
                rr[5]=d.isi.get("P"); rr[6]=d.isi.get("S"); rr[7]=d.isi.get("SO"); rr[8]=d.isi.get("M");
                rr[9]=d.ket==null?"":d.ket;
                modelKotakPantau.addRow(rr);
                kotakDetailPerBaris.put(row,d.id);
                if(d.tteJadwal>0L) tteJadwalPerBarisV38.put(row,d.tteJadwal);
                if(d.ids.get("P")!=null){ kotakJadwal.put(row+":5",d.ids.get("P")); tooltipKotakV35.put(row+":5",tooltipJadwalV35(d.ids.get("P"))); }
                if(d.ids.get("S")!=null){ kotakJadwal.put(row+":6",d.ids.get("S")); tooltipKotakV35.put(row+":6",tooltipJadwalV35(d.ids.get("S"))); }
                if(d.ids.get("SO")!=null){ kotakJadwal.put(row+":7",d.ids.get("SO")); tooltipKotakV35.put(row+":7",tooltipJadwalV35(d.ids.get("SO"))); }
                if(d.ids.get("M")!=null){ kotakJadwal.put(row+":8",d.ids.get("M")); tooltipKotakV35.put(row+":8",tooltipJadwalV35(d.ids.get("M"))); }
                row++;
            }
        }

        int[] widths={300,115,78,88,0,118,118,118,118,190};
        for(int c=0;c<tbKotakPantau.getColumnCount();c++) tbKotakPantau.getColumnModel().getColumn(c).setPreferredWidth(widths[c]);
        // V39: TTE tidak memakai kolom khusus. Bukti TTE tampil saat data pemberian P/S/SO/M diklik.
        tbKotakPantau.getColumnModel().getColumn(4).setMinWidth(0);
        tbKotakPantau.getColumnModel().getColumn(4).setMaxWidth(0);
        tbKotakPantau.getColumnModel().getColumn(4).setPreferredWidth(0);
        tbKotakPantau.setRowHeight(48);
        lblRingkasKotak.setText("Jadwal "+total+"  •  Diberikan "+diberikan+"  •  TTE "+totalTte+"  •  Belum "+belum+"  •  Perlu dipantau "+perlu);
    }


    private void lihatRiwayat7HariV18(){
        if(TNoRw.getText().trim().equals("")){
            javax.swing.JOptionPane.showMessageDialog(this,"Pilih pasien terlebih dahulu.");
            return;
        }

        java.sql.Date akhir=tanggalKotak();
        java.sql.Date awal=new java.sql.Date(akhir.getTime()-6L*86400000L);
        java.text.SimpleDateFormat fd=new java.text.SimpleDateFormat("dd MMM",new java.util.Locale("id","ID"));
        java.text.SimpleDateFormat ffull=new java.text.SimpleDateFormat("dd MMMM yyyy",new java.util.Locale("id","ID"));

        // DATA LANGSUNG DARI TABEL INTI, BUKAN VIEW, agar pemberian/validasi terbaru langsung terlihat.
        String q=
            "SELECT j.id_jadwal,j.tanggal_pemberian,j.jam_rencana,j.jam_realisasi,j.status_pemberian,"+
            "IFNULL(j.nama_petugas,'') nama_petugas,IFNULL(j.keterangan,'') ket_jadwal,"+
            "d.kategori,d.nama_obat,d.dosis_sediaan,d.aturan_pakai,d.rute,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_validasi v "+
            "       WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='DPJP' AND v.status_validasi='Valid') dpjp,"+
            "(SELECT v.nama_validator FROM pemberian_obat_ranap_validasi v "+
            " WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='DPJP' AND v.status_validasi='Valid' "+
            " ORDER BY v.id_validasi DESC LIMIT 1) nama_dpjp,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_validasi v "+
            "       WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='Farmasi' AND v.status_validasi='Valid') farmasi,"+
            "(SELECT v.nama_validator FROM pemberian_obat_ranap_validasi v "+
            " WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='Farmasi' AND v.status_validasi='Valid' "+
            " ORDER BY v.id_validasi DESC LIMIT 1) nama_farmasi,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_verifikasi_item vi "+
            " INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi "+
            " WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif') tte "+
            "FROM pemberian_obat_ranap_jadwal j "+
            "INNER JOIN pemberian_obat_ranap_detail d ON d.id_detail=j.id_detail "+
            "INNER JOIN pemberian_obat_ranap h ON h.no_surat=d.no_surat "+
            "WHERE h.no_rawat=? AND h.status_dokumen<>'Batal' "+
            "AND j.tanggal_pemberian BETWEEN ? AND ? "+
            "ORDER BY j.tanggal_pemberian,FIELD(d.kategori,'Oral','Parenteral','Inhalasi','Topikal','Lainnya'),d.urut,j.jam_rencana";

        javax.swing.table.DefaultTableModel detailModel=new javax.swing.table.DefaultTableModel(
            new Object[]{"Tanggal","Kelompok","Obat","Aturan/Rute","Rencana","Realisasi","Status","Petugas","DPJP","Farmasi","TTE","Keterangan"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };

        int[] total=new int[7], diberikan=new int[7], belum=new int[7], ditunda=new int[7], mmo=new int[7], stop=new int[7];
        int sumTotal=0,sumDiberikan=0,sumBelum=0,sumDitunda=0,sumMmo=0,sumStop=0,sumDpjp=0,sumFarmasi=0,sumTte=0;
        java.sql.Date[] dates=new java.sql.Date[7];
        for(int i=0;i<7;i++) dates[i]=new java.sql.Date(awal.getTime()+i*86400000L);

        try(java.sql.PreparedStatement p=koneksi.prepareStatement(q)){
            p.setString(1,TNoRw.getText().trim());
            p.setDate(2,awal);
            p.setDate(3,akhir);
            try(java.sql.ResultSet r=p.executeQuery()){
                while(r.next()){
                    java.sql.Date dt=r.getDate("tanggal_pemberian");
                    int ix=-1;
                    for(int i=0;i<7;i++) if(dates[i].equals(dt)){ix=i;break;}
                    if(ix<0) continue;

                    String st=r.getString("status_pemberian");
                    total[ix]++; sumTotal++;
                    if("Diberikan".equals(st)){diberikan[ix]++;sumDiberikan++;}
                    else if("Terjadwal".equals(st)){belum[ix]++;sumBelum++;}
                    else if("Ditunda".equals(st)){ditunda[ix]++;sumDitunda++;}
                    else if("MMO".equals(st)){mmo[ix]++;sumMmo++;}
                    else if("STOP".equals(st) || "Tidak Diberikan".equals(st)){stop[ix]++;sumStop++;}

                    boolean vd=r.getBoolean("dpjp");
                    boolean vf=r.getBoolean("farmasi");
                    boolean tte=r.getBoolean("tte");
                    if(vd)sumDpjp++; if(vf)sumFarmasi++; if(tte)sumTte++;

                    String obat=r.getString("nama_obat");
                    String dosis=r.getString("dosis_sediaan");
                    if(dosis!=null && !dosis.trim().equals("")) obat+=" | "+dosis;
                    String ar=(r.getString("aturan_pakai")==null?"":r.getString("aturan_pakai"));
                    String rt=(r.getString("rute")==null?"":r.getString("rute"));
                    if(!rt.trim().equals("")) ar+=(ar.trim().equals("")?"":" | ")+rt;
                    String jr=r.getTime("jam_rencana")==null?"":new java.text.SimpleDateFormat("HH:mm").format(r.getTime("jam_rencana"));
                    String real=r.getTime("jam_realisasi")==null?"":new java.text.SimpleDateFormat("HH:mm").format(r.getTime("jam_realisasi"));
                    String dp=vd ? "✓ "+(r.getString("nama_dpjp")==null?"":r.getString("nama_dpjp")) : "Belum";
                    String fm=vf ? "✓ "+(r.getString("nama_farmasi")==null?"":r.getString("nama_farmasi")) : "Belum";

                    detailModel.addRow(new Object[]{
                        fd.format(dt),
                        r.getString("kategori"),
                        obat,
                        ar,
                        jr,
                        real,
                        st,
                        r.getString("nama_petugas"),
                        dp,
                        fm,
                        tte?"✓":"Belum",
                        r.getString("ket_jadwal")
                    });
                }
            }
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Riwayat 7 Hari gagal dibaca:\n"+ex.toString(),
                "Riwayat Pemberian",javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ---- ROOT DIALOG ----
        javax.swing.JPanel root=new javax.swing.JPanel(new java.awt.BorderLayout(10,10));
        root.setBackground(new java.awt.Color(245,248,252));
        root.setBorder(javax.swing.BorderFactory.createEmptyBorder(12,12,12,12));

        javax.swing.JPanel title=new javax.swing.JPanel(new java.awt.BorderLayout());
        title.setBackground(java.awt.Color.WHITE);
        title.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215,224,234)),
            javax.swing.BorderFactory.createEmptyBorder(10,12,10,12)));
        javax.swing.JLabel h=new javax.swing.JLabel("EVALUASI PEMBERIAN OBAT — 7 HARI");
        h.setFont(h.getFont().deriveFont(java.awt.Font.BOLD,16f));
        h.setForeground(new java.awt.Color(31,78,121));
        javax.swing.JLabel sub=new javax.swing.JLabel(
            "<html>"+TPasien.getText()+" &nbsp; • &nbsp; RM "+TNoRM.getText()+
            " &nbsp; • &nbsp; "+ffull.format(awal)+" s/d "+ffull.format(akhir)+"</html>");
        sub.setForeground(new java.awt.Color(100,110,120));
        title.add(h,java.awt.BorderLayout.NORTH);
        title.add(sub,java.awt.BorderLayout.SOUTH);

        javax.swing.JPanel north=new javax.swing.JPanel();
        north.setLayout(new javax.swing.BoxLayout(north,javax.swing.BoxLayout.Y_AXIS));
        north.setOpaque(false);
        north.add(title);
        north.add(javax.swing.Box.createVerticalStrut(8));

        javax.swing.JPanel cards=new javax.swing.JPanel(new java.awt.GridLayout(1,6,7,0));
        cards.setOpaque(false);
        cards.add(kartuRingkasV22("Jadwal",sumTotal,new java.awt.Color(70,100,130),""));
        cards.add(kartuRingkasV22("Diberikan",sumDiberikan,new java.awt.Color(45,135,85),""));
        cards.add(kartuRingkasV22("Belum",sumBelum,new java.awt.Color(190,140,35),""));
        cards.add(kartuRingkasV22("DPJP",sumDpjp,new java.awt.Color(58,105,175),"✓"));
        cards.add(kartuRingkasV22("Farmasi",sumFarmasi,new java.awt.Color(90,120,180),"✓"));
        int kep=sumTotal==0?0:(int)Math.round(sumDiberikan*100.0/sumTotal);
        cards.add(kartuRingkasV22("Kepatuhan",kep,new java.awt.Color(45,135,85),"%"));
        north.add(cards);
        root.add(north,java.awt.BorderLayout.NORTH);

        // ---- TAB RINGKASAN + DETAIL ----
        javax.swing.JTabbedPane tabs=new javax.swing.JTabbedPane();

        // Ringkasan 7 hari: chart + table
        javax.swing.JPanel ringkas=new javax.swing.JPanel(new java.awt.BorderLayout(8,8));
        ringkas.setBackground(java.awt.Color.WHITE);

        javax.swing.JPanel chart=new javax.swing.JPanel(){
            @Override protected void paintComponent(java.awt.Graphics g){
                super.paintComponent(g);
                java.awt.Graphics2D g2=(java.awt.Graphics2D)g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                int w=getWidth(),hh=getHeight(),left=52,right=20,top=28,bottom=42;
                int max=1; for(int x:total) if(x>max)max=x;
                g2.setColor(new java.awt.Color(226,232,239));
                for(int k=0;k<=4;k++){
                    int y=top+(hh-top-bottom)*k/4;
                    g2.drawLine(left,y,w-right,y);
                }
                int slot=Math.max(1,(w-left-right)/7);
                int bw=Math.max(10,Math.min(22,slot/5));
                for(int i=0;i<7;i++){
                    int base=hh-bottom;
                    int ht=(hh-top-bottom)*total[i]/max;
                    int hg=(hh-top-bottom)*diberikan[i]/max;
                    int x=left+i*slot+slot/2-bw;
                    g2.setColor(new java.awt.Color(197,207,219));
                    g2.fillRoundRect(x,base-ht,bw,ht,6,6);
                    g2.setColor(new java.awt.Color(55,149,94));
                    g2.fillRoundRect(x+bw+4,base-hg,bw,hg,6,6);
                    g2.setColor(new java.awt.Color(70,78,88));
                    g2.drawString(fd.format(dates[i]),left+i*slot+4,hh-14);
                }
                g2.setColor(new java.awt.Color(197,207,219));
                g2.fillRect(left,7,12,12);
                g2.setColor(new java.awt.Color(80,88,98));
                g2.drawString("Jadwal",left+17,18);
                g2.setColor(new java.awt.Color(55,149,94));
                g2.fillRect(left+85,7,12,12);
                g2.setColor(new java.awt.Color(80,88,98));
                g2.drawString("Diberikan",left+102,18);
                g2.dispose();
            }
        };
        chart.setPreferredSize(new java.awt.Dimension(900,230));
        chart.setBackground(java.awt.Color.WHITE);
        chart.setBorder(javax.swing.BorderFactory.createTitledBorder("Tren Jadwal vs Diberikan"));

        javax.swing.table.DefaultTableModel sumModel=new javax.swing.table.DefaultTableModel(
            new Object[]{"Tanggal","Jadwal","Diberikan","Belum","Ditunda","MMO","STOP","Kepatuhan"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for(int i=0;i<7;i++){
            int pct=total[i]==0?0:(int)Math.round(diberikan[i]*100.0/total[i]);
            sumModel.addRow(new Object[]{fd.format(dates[i]),total[i],diberikan[i],belum[i],ditunda[i],mmo[i],stop[i],pct+"%"});
        }
        javax.swing.JTable sumTable=new javax.swing.JTable(sumModel);
        sumTable.setRowHeight(28);
        sumTable.setAutoCreateRowSorter(true);
        javax.swing.JScrollPane sumScroll=new javax.swing.JScrollPane(sumTable);
        sumScroll.setPreferredSize(new java.awt.Dimension(900,195));
        sumScroll.setBorder(javax.swing.BorderFactory.createTitledBorder("Ringkasan Harian"));

        ringkas.add(chart,java.awt.BorderLayout.CENTER);
        ringkas.add(sumScroll,java.awt.BorderLayout.SOUTH);

        // Detail obat yang benar-benar bisa dibaca user
        javax.swing.JTable detailTable=new javax.swing.JTable(detailModel){
            @Override public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer,int row,int col){
                java.awt.Component c=super.prepareRenderer(renderer,row,col);
                if(!isRowSelected(row)){
                    c.setBackground(row%2==0?java.awt.Color.WHITE:new java.awt.Color(248,250,252));
                    String st=String.valueOf(getModel().getValueAt(row,6));
                    if("Diberikan".equals(st)) c.setBackground(new java.awt.Color(235,249,240));
                    else if("MMO".equals(st) || "Ditunda".equals(st)) c.setBackground(new java.awt.Color(250,244,231));
                    else if("STOP".equals(st) || "Tidak Diberikan".equals(st)) c.setBackground(new java.awt.Color(252,235,235));
                }
                return c;
            }
        };
        detailTable.setRowHeight(30);
        detailTable.setAutoCreateRowSorter(true);
        detailTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        int[] wd={75,90,210,170,70,75,100,130,145,145,65,190};
        for(int i=0;i<wd.length;i++) detailTable.getColumnModel().getColumn(i).setPreferredWidth(wd[i]);
        javax.swing.JScrollPane detailScroll=new javax.swing.JScrollPane(detailTable);
        detailScroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        javax.swing.JPanel detailPanel=new javax.swing.JPanel(new java.awt.BorderLayout(5,5));
        detailPanel.setBackground(java.awt.Color.WHITE);
        javax.swing.JLabel detailInfo=new javax.swing.JLabel(
            "<html><b>Detail pemberian:</b> hijau = sudah diberikan &nbsp; • &nbsp; "+
            "kolom DPJP/Farmasi menunjukkan validasi aktif &nbsp; • &nbsp; TTE menunjukkan verifikasi pasien.</html>");
        detailInfo.setBorder(javax.swing.BorderFactory.createEmptyBorder(7,8,7,8));
        detailPanel.add(detailInfo,java.awt.BorderLayout.NORTH);
        detailPanel.add(detailScroll,java.awt.BorderLayout.CENTER);

        tabs.addTab("Ringkasan 7 Hari",ringkas);
        tabs.addTab("Detail Obat & Validasi",detailPanel);
        tabs.setPreferredSize(new java.awt.Dimension(1000,455));
        root.add(tabs,java.awt.BorderLayout.CENTER);

        javax.swing.JPanel foot=new javax.swing.JPanel(new java.awt.BorderLayout());
        foot.setBorder(javax.swing.BorderFactory.createEmptyBorder(2,2,0,2));
        foot.setOpaque(false);
        String evaluasi=sumTotal==0 ?
            "Belum ada jadwal pemberian pada periode ini." :
            "Diberikan "+sumDiberikan+" dari "+sumTotal+" jadwal ("+kep+"%). "+
            "Validasi DPJP "+sumDpjp+" • Farmasi "+sumFarmasi+" • TTE "+sumTte+".";
        javax.swing.JLabel f=new javax.swing.JLabel("<html><b>Evaluasi:</b> "+evaluasi+"</html>");
        f.setBorder(javax.swing.BorderFactory.createEmptyBorder(6,4,2,4));
        foot.add(f,java.awt.BorderLayout.CENTER);
        root.add(foot,java.awt.BorderLayout.SOUTH);

        javax.swing.JScrollPane wrap=new javax.swing.JScrollPane(root);
        wrap.setBorder(null);
        wrap.setPreferredSize(new java.awt.Dimension(1060,630));
        javax.swing.JOptionPane.showMessageDialog(this,wrap,
            "Riwayat & Evaluasi Pemberian Obat — 7 Hari",
            javax.swing.JOptionPane.PLAIN_MESSAGE);
    }

    private javax.swing.JPanel kartuRingkasV22(String title,int value,java.awt.Color accent,String suffix){
        javax.swing.JPanel p=new javax.swing.JPanel(new java.awt.BorderLayout());
        p.setBackground(java.awt.Color.WHITE);
        p.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(3,0,0,0,accent),
            javax.swing.BorderFactory.createEmptyBorder(7,9,7,9)));
        javax.swing.JLabel a=new javax.swing.JLabel(title);
        a.setForeground(new java.awt.Color(90,100,110));
        javax.swing.JLabel b=new javax.swing.JLabel(value+suffix);
        b.setFont(b.getFont().deriveFont(java.awt.Font.BOLD,18f));
        b.setForeground(accent);
        p.add(a,java.awt.BorderLayout.NORTH);
        p.add(b,java.awt.BorderLayout.CENTER);
        return p;
    }

    private javax.swing.JPanel kartuRingkasV19(String title,int value,java.awt.Color accent){
        return kartuRingkasV19(title,value,accent,"");
    }
    private javax.swing.JPanel kartuRingkasV19(String title,int value,java.awt.Color accent,String suffix){
        javax.swing.JPanel p=new javax.swing.JPanel(new java.awt.BorderLayout());
        p.setBackground(java.awt.Color.WHITE);
        p.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(3,0,0,0,accent),
            javax.swing.BorderFactory.createEmptyBorder(7,8,7,8)));
        javax.swing.JLabel a=new javax.swing.JLabel(title); a.setForeground(new java.awt.Color(90,100,110));
        javax.swing.JLabel b=new javax.swing.JLabel(value+suffix);
        b.setFont(b.getFont().deriveFont(java.awt.Font.BOLD,18f)); b.setForeground(accent);
        p.add(a,java.awt.BorderLayout.NORTH); p.add(b,java.awt.BorderLayout.CENTER);
        return p;
    }

    private void autoFitRangkumanV352(javax.swing.JTable table,int fixedCount,int colKet){
        try{
            for(int col=0;col<table.getColumnCount();col++){
                int width=40;

                javax.swing.table.TableCellRenderer hr=table.getTableHeader().getDefaultRenderer();
                java.awt.Component hc=hr.getTableCellRendererComponent(
                    table,table.getColumnName(col),false,false,-1,col);
                width=Math.max(width,hc.getPreferredSize().width+14);

                int maxRows=Math.min(table.getRowCount(),100);
                for(int row=0;row<maxRows;row++){
                    javax.swing.table.TableCellRenderer rr=table.getCellRenderer(row,col);
                    java.awt.Component cc=table.prepareRenderer(rr,row,col);
                    width=Math.max(width,cc.getPreferredSize().width+16);
                }

                boolean kolPantauan=(col>=fixedCount && col<colKet);
                boolean kosongPantauan=false;
                if(kolPantauan){
                    kosongPantauan=true;
                    for(int rr=0;rr<table.getRowCount();rr++){
                        Object vv=table.getValueAt(rr,col);
                        if(vv!=null && !vv.toString().trim().equals("")){
                            kosongPantauan=false;
                            break;
                        }
                    }
                }

                int min=kolPantauan ? (kosongPantauan?38:92) : 55;
                int max;
                if(col==1) max=320;
                else if(col==2) max=220;
                else if(col==colKet) max=280;
                else if(kolPantauan) max=190;
                else max=140;

                // Jika seluruh kolom P/S/SO/M kosong, cukup selebar judul periodenya.
                if(kolPantauan && kosongPantauan){
                    int headerCompact=table.getFontMetrics(table.getTableHeader().getFont())
                        .stringWidth(table.getColumnName(col))+22;
                    width=Math.max(min,headerCompact);
                }else{
                    width=Math.max(min,Math.min(width,max));
                }
                javax.swing.table.TableColumn tc=table.getColumnModel().getColumn(col);
                tc.setPreferredWidth(width);
                tc.setWidth(width);
            }
        }catch(Exception ex){
            System.out.println("Auto-fit Rangkuman V35.2: "+ex);
        }
    }

    private void lihatRangkumanPemberianV24(){
        if(TNoRw.getText().trim().equals("")){
            javax.swing.JOptionPane.showMessageDialog(this,"Pilih pasien terlebih dahulu.");
            return;
        }

        // Ambil hanya tanggal yang benar-benar mempunyai jadwal/input untuk pasien.
        final java.util.ArrayList<java.sql.Date> daftarTanggal=new java.util.ArrayList<java.sql.Date>();
        String qt=
            "SELECT DISTINCT j.tanggal_pemberian "+
            "FROM pemberian_obat_ranap_jadwal j "+
            "INNER JOIN pemberian_obat_ranap_detail d ON d.id_detail=j.id_detail "+
            "INNER JOIN pemberian_obat_ranap h ON h.no_surat=d.no_surat "+
            "WHERE h.no_rawat=? AND COALESCE(h.status_dokumen,'Aktif')<>'Batal' "+
            "ORDER BY j.tanggal_pemberian";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(qt)){
            p.setString(1,TNoRw.getText().trim());
            try(java.sql.ResultSet r=p.executeQuery()){
                while(r.next()){
                    java.sql.Date t=r.getDate(1);
                    if(t!=null) daftarTanggal.add(t);
                }
            }
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Tanggal rangkuman gagal dibaca:\n"+ex.toString());
            return;
        }
        if(daftarTanggal.isEmpty()){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Belum ada jadwal/pemberian obat yang tercatat untuk pasien ini.");
            return;
        }
        final java.sql.Date[] hari=daftarTanggal.toArray(new java.sql.Date[daftarTanggal.size()]);

        String q=
            "SELECT d.id_detail,d.kategori,d.nama_obat,IFNULL(d.dosis_sediaan,'') dosis_sediaan,"+
            "IFNULL(d.aturan_pakai,'') aturan_pakai,IFNULL(d.rute,'') rute,"+
            "j.id_jadwal,j.tanggal_pemberian,j.jam_rencana,j.jam_realisasi,j.status_pemberian,"+
            "IFNULL(j.dosis_pemberian,'') dosis_pemberian,"+
            "IFNULL(j.nama_petugas,'') nama_petugas,IFNULL(j.keterangan,'') ket,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_validasi v "+
            " WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='DPJP' AND v.status_validasi='Valid') dpjp,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_validasi v "+
            " WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='Farmasi' AND v.status_validasi='Valid') farmasi,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif') tte,"+
            "(SELECT vv.nama_penandatangan FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_nama,"+
            "(SELECT vv.hubungan FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_hubungan,"+
            "(SELECT vv.waktu_verifikasi FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_waktu "+
            "FROM pemberian_obat_ranap_detail d "+
            "INNER JOIN pemberian_obat_ranap h ON h.no_surat=d.no_surat "+
            "LEFT JOIN pemberian_obat_ranap_jadwal j ON j.id_detail=d.id_detail "+
            "WHERE h.no_rawat=? AND COALESCE(h.status_dokumen,'Aktif')<>'Batal' "+
            "ORDER BY FIELD(d.kategori,'Oral','Parenteral','Inhalasi','Topikal','Lainnya'),"+
            "d.urut,j.tanggal_pemberian,j.jam_rencana";

        class RV34{
            long id;
            String kategori="",obat="",aturan="",dpjp="–",farmasi="–",ket="";
            String[] cell=new String[hari.length*4];
            String[] tip=new String[hari.length*4];
            RV34(){for(int i=0;i<cell.length;i++){cell[i]="";tip[i]=null;}}
        }

        java.util.LinkedHashMap<Long,RV34> map=new java.util.LinkedHashMap<Long,RV34>();
        int total=0,diberikan=0,terjadwal=0,mmo=0,ditunda=0,stop=0,tidak=0;

        try(java.sql.PreparedStatement p=koneksi.prepareStatement(q)){
            p.setString(1,TNoRw.getText().trim());
            try(java.sql.ResultSet r=p.executeQuery()){
                while(r.next()){
                    long id=r.getLong("id_detail");
                    RV34 x=map.get(id);
                    if(x==null){
                        x=new RV34(); x.id=id;
                        x.kategori=r.getString("kategori")==null?"Lainnya":r.getString("kategori");
                        String nm=r.getString("nama_obat")==null?"":r.getString("nama_obat");
                        String ds=r.getString("dosis_sediaan");
                        x.obat=nm+(ds.trim().equals("")?"":" • "+ds.trim());
                        String ap=r.getString("aturan_pakai");
                        String rt=r.getString("rute");
                        x.aturan=ap+(rt.trim().equals("")?"":" • "+rt.trim());
                        map.put(id,x);
                    }

                    java.sql.Date dt=r.getDate("tanggal_pemberian");
                    if(dt==null) continue;
                    int di=-1;
                    for(int i=0;i<hari.length;i++) if(hari[i].equals(dt)){di=i;break;}
                    if(di<0) continue;

                    java.sql.Time jr=r.getTime("jam_rencana");
                    int hh=jr==null?0:Integer.parseInt(new java.text.SimpleDateFormat("HH").format(jr));
                    int sh=(hh>=5&&hh<10)?0:(hh>=10&&hh<16)?1:(hh>=16&&hh<21)?2:3;
                    int ci=di*4+sh;

                    String st=r.getString("status_pemberian");
                    java.sql.Time real=r.getTime("jam_realisasi");
                    String jam=real!=null?new java.text.SimpleDateFormat("HH:mm").format(real):
                               (jr!=null?new java.text.SimpleDateFormat("HH:mm").format(jr):"");

                    String kode="Diberikan".equals(st)?"✓ Diberikan":
                                "Terjadwal".equals(st)?"○ Terjadwal":
                                "MMO".equals(st)?"MMO":
                                "Ditunda".equals(st)?"Ditunda":
                                "STOP".equals(st)?"STOP":
                                "Tidak Diberikan".equals(st)?"Tidak Diberikan":st;
                    String pet=r.getString("nama_petugas");
                    if(pet==null)pet="";

                    String dosisAkt=r.getString("dosis_pemberian");
                    String isi=(jam.equals("")?"":jam+" ")+kode;
                    if(dosisAkt!=null && !dosisAkt.trim().equals("")) isi+=" • "+dosisAkt.trim();
                    if(!pet.trim().equals("")) isi+=" • "+pet.trim();
                    if(r.getBoolean("tte")) isi+=" • TTE ✓";
                    if(!x.cell[ci].equals("")) x.cell[ci]+=" / "+isi;
                    else x.cell[ci]=isi;

                    String tglTip=new java.text.SimpleDateFormat("dd-MM-yyyy").format(dt);
                    String tip="<html><b>"+x.obat+"</b><br>"+
                        "Tanggal: "+tglTip+"<br>"+
                        "Jam rencana: "+(jr==null?"-":new java.text.SimpleDateFormat("HH:mm").format(jr))+"<br>"+
                        "Jam realisasi: "+(real==null?"-":new java.text.SimpleDateFormat("HH:mm").format(real))+"<br>"+
                        "Dosis diberikan: "+((dosisAkt==null||dosisAkt.trim().equals(""))?"-":dosisAkt)+"<br>"+
                        "Status: "+st+"<br>"+
                        "Petugas: "+(pet.trim().equals("")?"-":pet)+"<br>"+
                        "DPJP: "+(r.getBoolean("dpjp")?"Sudah validasi":"Belum validasi")+"<br>"+
                        "Farmasi: "+(r.getBoolean("farmasi")?"Sudah validasi":"Belum validasi")+"<br>"+
                        "TTE pasien: "+(r.getBoolean("tte")?
                            ("Sudah ✓ — "+(r.getString("tte_nama")==null?"-":r.getString("tte_nama"))+
                             " ("+(r.getString("tte_hubungan")==null?"-":r.getString("tte_hubungan"))+")"+
                             (r.getTimestamp("tte_waktu")==null?"":" • "+new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm").format(r.getTimestamp("tte_waktu"))))
                            :"Belum")+"<br>"+
                        "Keterangan: "+(r.getString("ket").trim().equals("")?"-":r.getString("ket"))+"</html>";
                    x.tip[ci]=tip;

                    if(r.getBoolean("dpjp"))x.dpjp="✓";
                    if(r.getBoolean("farmasi"))x.farmasi="✓";
                    String k=r.getString("ket");
                    if(k!=null&&!k.trim().equals(""))x.ket=k.trim();

                    total++;
                    if("Diberikan".equals(st))diberikan++;
                    else if("Terjadwal".equals(st))terjadwal++;
                    else if("MMO".equals(st))mmo++;
                    else if("Ditunda".equals(st))ditunda++;
                    else if("STOP".equals(st))stop++;
                    else if("Tidak Diberikan".equals(st))tidak++;
                }
            }
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Rangkuman pemberian gagal dibaca:\n"+ex.toString());
            return;
        }

        final int fixedCount=5;
        final int colKet=fixedCount+(hari.length*4);
        String[] cols=new String[colKet+1];
        cols[0]="Kelompok";
        cols[1]="Nama Obat / Dosis";
        cols[2]="Aturan / Rute";
        cols[3]="DPJP";
        cols[4]="Farmasi";
        int c=fixedCount;
        for(int d=0;d<hari.length;d++){
            cols[c++]="P";
            cols[c++]="S";
            cols[c++]="SO";
            cols[c++]="M";
        }
        cols[colKet]="Keterangan";

        final java.util.Map<String,String> tipRangkumanV35=new java.util.HashMap<String,String>();
        javax.swing.table.DefaultTableModel model=new javax.swing.table.DefaultTableModel(cols,0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        int rowIndexV35=0;
        for(RV34 x:map.values()){
            Object[] row=new Object[colKet+1];
            row[0]=x.kategori;
            row[1]=x.obat;
            row[2]=x.aturan;
            row[3]=x.dpjp;
            row[4]=x.farmasi;
            for(int i=0;i<x.cell.length;i++){
                row[fixedCount+i]=x.cell[i];
                if(x.tip[i]!=null)tipRangkumanV35.put(rowIndexV35+":"+(fixedCount+i),x.tip[i]);
            }
            row[colKet]=x.ket;
            model.addRow(row);
            rowIndexV35++;
        }

        final javax.swing.JTable table=new javax.swing.JTable(model){
            @Override public String getToolTipText(java.awt.event.MouseEvent e){
                int vr=rowAtPoint(e.getPoint());
                int vc=columnAtPoint(e.getPoint());
                if(vr<0 || vc<0) return null;
                int mr=convertRowIndexToModel(vr);
                int mc=convertColumnIndexToModel(vc);
                String tip=tipRangkumanV35.get(mr+":"+mc);
                if(tip!=null && !tip.trim().equals("")) return tip;

                Object value=getModel().getValueAt(mr,mc);
                if(value!=null && !value.toString().trim().equals(""))
                    return "<html>"+value.toString().replace("\n","<br>")+"</html>";
                return null;
            }

            @Override public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer rr,int row,int col){
                java.awt.Component cc=super.prepareRenderer(rr,row,col);
                if(!isRowSelected(row)){
                    cc.setBackground(row%2==0?java.awt.Color.WHITE:new java.awt.Color(248,250,252));
                    if(col>=fixedCount && col<colKet){
                        String v=String.valueOf(getValueAt(row,col));
                        if(v.contains("Diberikan"))cc.setBackground(new java.awt.Color(230,248,238));
                        else if(v.contains("Terjadwal"))cc.setBackground(new java.awt.Color(255,248,223));
                        else if(v.contains("MMO")||v.contains("Ditunda"))cc.setBackground(new java.awt.Color(251,241,225));
                        else if(v.contains("STOP")||v.contains("Tidak Diberikan"))cc.setBackground(new java.awt.Color(252,233,233));
                    }
                }
                return cc;
            }
        };
        table.setRowHeight(48);
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.setFont(table.getFont().deriveFont(10.5f));
        

        int[] fixedWidths={82,220,150,55,65};
        for(int i=0;i<fixedCount;i++)table.getColumnModel().getColumn(i).setPreferredWidth(fixedWidths[i]);
        for(int i=fixedCount;i<colKet;i++)table.getColumnModel().getColumn(i).setPreferredWidth(118);
        table.getColumnModel().getColumn(colKet).setPreferredWidth(185);
        table.getTableHeader().setResizingAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);

        // Tooltip manager dibuat responsif dan klik sel pemberian membuka detail lengkap.
        javax.swing.ToolTipManager.sharedInstance().registerComponent(table);
        javax.swing.ToolTipManager.sharedInstance().setInitialDelay(250);
        javax.swing.ToolTipManager.sharedInstance().setDismissDelay(15000);

        table.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override public void mouseClicked(java.awt.event.MouseEvent e){
                int vr=table.rowAtPoint(e.getPoint());
                int vc=table.columnAtPoint(e.getPoint());
                if(vr<0 || vc<0) return;
                int mr=table.convertRowIndexToModel(vr);
                int mc=table.convertColumnIndexToModel(vc);
                if(mc<fixedCount || mc>=colKet) return;
                String tip=tipRangkumanV35.get(mr+":"+mc);
                if(tip==null || tip.trim().equals("")) return;

                javax.swing.JLabel detail=new javax.swing.JLabel(tip);
                detail.setBorder(javax.swing.BorderFactory.createEmptyBorder(10,12,10,12));
                javax.swing.JOptionPane.showMessageDialog(
                    RMPemberianObatRanap.this,detail,
                    "Detail Pemberian Obat",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Auto-fit berdasarkan header + isi, namun user tetap dapat drag batas kolom secara manual.
        autoFitRangkumanV352(table,fixedCount,colKet);

        // V36.3: header tanggal native, seluruhnya digambar dalam satu JTableHeader.
        // Baris atas = tanggal pemberian, baris bawah = Kelompok/Obat/... + P/S/SO/M.
        final java.text.SimpleDateFormat fDateV363=new java.text.SimpleDateFormat("dd-MM-yyyy");

        javax.swing.table.JTableHeader headerV363=new javax.swing.table.JTableHeader(table.getColumnModel()){
            @Override public java.awt.Dimension getPreferredSize(){
                java.awt.Dimension d=super.getPreferredSize();
                return new java.awt.Dimension(d.width,56);
            }

            @Override protected void paintComponent(java.awt.Graphics g){
                java.awt.Graphics2D g2=(java.awt.Graphics2D)g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                int hTop=28;
                int hBottom=28;
                int x=0;

                // Background seluruh header.
                g2.setColor(java.awt.Color.WHITE);
                g2.fillRect(0,0,getWidth(),getHeight());

                // 5 kolom identitas kiri.
                String[] left={"Kelompok","Nama Obat / Dosis","Aturan / Rute","DPJP","Farmasi"};
                for(int i=0;i<fixedCount;i++){
                    int w=getColumnModel().getColumn(i).getWidth();

                    g2.setColor(new java.awt.Color(235,240,246));
                    g2.fillRect(x,0,w,hTop+hBottom);
                    g2.setColor(new java.awt.Color(190,200,211));
                    g2.drawRect(x,0,w-1,hTop+hBottom-1);

                    g2.setFont(getFont().deriveFont(java.awt.Font.BOLD,10f));
                    g2.setColor(new java.awt.Color(45,58,72));
                    java.awt.FontMetrics fm=g2.getFontMetrics();
                    String tx=left[i];
                    int tw=fm.stringWidth(tx);
                    g2.drawString(tx,x+Math.max(3,(w-tw)/2),hTop+18);

                    x+=w;
                }

                // Tanggal + P/S/SO/M.
                String[] shift={"P","S","SO","M"};
                for(int d=0;d<hari.length;d++){
                    int groupStart=x;
                    int groupW=0;
                    for(int j=0;j<4;j++)
                        groupW+=getColumnModel().getColumn(fixedCount+d*4+j).getWidth();

                    // Baris tanggal.
                    g2.setColor(new java.awt.Color(31,78,121));
                    g2.fillRect(groupStart,0,groupW,hTop);
                    g2.setColor(new java.awt.Color(22,60,94));
                    g2.drawRect(groupStart,0,groupW-1,hTop-1);

                    g2.setFont(getFont().deriveFont(java.awt.Font.BOLD,10.5f));
                    g2.setColor(java.awt.Color.WHITE);
                    String tg=fDateV363.format(hari[d]);
                    java.awt.FontMetrics fmT=g2.getFontMetrics();
                    g2.drawString(tg,groupStart+(groupW-fmT.stringWidth(tg))/2,18);

                    // Baris P/S/SO/M.
                    for(int j=0;j<4;j++){
                        int w=getColumnModel().getColumn(fixedCount+d*4+j).getWidth();

                        g2.setColor(new java.awt.Color(225,235,245));
                        g2.fillRect(x,hTop,w,hBottom);
                        g2.setColor(new java.awt.Color(180,195,210));
                        g2.drawRect(x,hTop,w-1,hBottom-1);

                        g2.setFont(getFont().deriveFont(java.awt.Font.BOLD,10f));
                        g2.setColor(new java.awt.Color(31,78,121));
                        java.awt.FontMetrics fmS=g2.getFontMetrics();
                        g2.drawString(shift[j],x+(w-fmS.stringWidth(shift[j]))/2,hTop+18);

                        x+=w;
                    }
                }

                // Keterangan kanan.
                int wKet=getColumnModel().getColumn(colKet).getWidth();
                g2.setColor(new java.awt.Color(235,240,246));
                g2.fillRect(x,0,wKet,hTop+hBottom);
                g2.setColor(new java.awt.Color(190,200,211));
                g2.drawRect(x,0,wKet-1,hTop+hBottom-1);

                g2.setFont(getFont().deriveFont(java.awt.Font.BOLD,10f));
                g2.setColor(new java.awt.Color(45,58,72));
                java.awt.FontMetrics fmK=g2.getFontMetrics();
                String kt="Keterangan";
                g2.drawString(kt,x+Math.max(3,(wKet-fmK.stringWidth(kt))/2),hTop+18);

                g2.dispose();
            }
        };

        headerV363.setResizingAllowed(true);
        headerV363.setReorderingAllowed(false);
        headerV363.setPreferredSize(new java.awt.Dimension(0,56));
        table.setTableHeader(headerV363);

        // Saat kolom berubah lebar, header langsung digambar ulang.
        table.getColumnModel().addColumnModelListener(new javax.swing.event.TableColumnModelListener(){
            public void columnAdded(javax.swing.event.TableColumnModelEvent e){}
            public void columnRemoved(javax.swing.event.TableColumnModelEvent e){}
            public void columnMoved(javax.swing.event.TableColumnModelEvent e){}
            public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e){}
            public void columnMarginChanged(javax.swing.event.ChangeEvent e){
                headerV363.revalidate();
                headerV363.repaint();
            }
        });

        javax.swing.JScrollPane sp=new javax.swing.JScrollPane(table);
        sp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210,220,230)));
        sp.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        javax.swing.JPanel root=new javax.swing.JPanel(new java.awt.BorderLayout(7,7));
        root.setBorder(javax.swing.BorderFactory.createEmptyBorder(9,9,9,9));
        root.setBackground(new java.awt.Color(244,248,252));

        javax.swing.JPanel head=new javax.swing.JPanel(new java.awt.BorderLayout());
        head.setBackground(java.awt.Color.WHITE);
        head.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(212,222,232)),
            javax.swing.BorderFactory.createEmptyBorder(8,10,8,10)));

        javax.swing.JLabel title=new javax.swing.JLabel("RANGKUMAN PEMBERIAN OBAT");
        title.setFont(title.getFont().deriveFont(java.awt.Font.BOLD,16f));
        title.setForeground(new java.awt.Color(31,78,121));

        javax.swing.JLabel sub=new javax.swing.JLabel(
            "<html><b>"+TPasien.getText()+"</b> • RM "+TNoRM.getText()+
            " &nbsp;&nbsp; | &nbsp;&nbsp; Baca: <b>Obat → Tanggal → P/S/SO/M → Jam → Status → Petugas</b>. Hover/klik kotak untuk detail.</html>");
        sub.setForeground(new java.awt.Color(85,98,110));

        head.add(title,java.awt.BorderLayout.NORTH);
        head.add(sub,java.awt.BorderLayout.SOUTH);
        root.add(head,java.awt.BorderLayout.NORTH);
        root.add(sp,java.awt.BorderLayout.CENTER);

        javax.swing.JLabel foot=new javax.swing.JLabel(
            "<html><b>Diberikan "+diberikan+"</b> • Terjadwal "+terjadwal+
            " • MMO "+mmo+" • Ditunda "+ditunda+" • STOP "+stop+
            " • Tidak Diberikan "+tidak+
            " &nbsp;&nbsp; | &nbsp;&nbsp; Hijau=diberikan, kuning=terjadwal, oranye=MMO/ditunda, merah=STOP/tidak diberikan.</html>");
        foot.setOpaque(true);
        foot.setBackground(java.awt.Color.WHITE);
        foot.setBorder(javax.swing.BorderFactory.createEmptyBorder(7,8,7,8));
        root.add(foot,java.awt.BorderLayout.SOUTH);

        javax.swing.JScrollPane wrap=new javax.swing.JScrollPane(root);
        wrap.setBorder(null);
        wrap.setPreferredSize(new java.awt.Dimension(1200,640));
        javax.swing.JOptionPane.showMessageDialog(this,wrap,
            "Rangkuman Pemberian Obat",javax.swing.JOptionPane.PLAIN_MESSAGE);
    }

    private void cetakPantauanV21(){
        if(tbKotakPantau==null || tbKotakPantau.getRowCount()==0){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Tidak ada data pantauan yang dapat dicetak.",
                "Cetak Pantauan",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try{
            String pasien=TPasien.getText().trim();
            String rm=TNoRM.getText().trim();
            String tgl=new java.text.SimpleDateFormat("dd-MM-yyyy").format(tanggalKotak());

            java.text.MessageFormat header=new java.text.MessageFormat(
                "CATATAN PEMBERIAN OBAT - "+pasien+" | RM "+rm+" | "+tgl);
            java.text.MessageFormat footer=new java.text.MessageFormat(
                "Pantauan Pemberian Obat - Halaman {0}");

            boolean ok=tbKotakPantau.print(
                javax.swing.JTable.PrintMode.FIT_WIDTH,
                header,
                footer
            );
            if(ok){
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Pantauan pemberian obat berhasil dikirim ke printer.",
                    "Cetak Pantauan",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(java.awt.print.PrinterException ex){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Gagal mencetak pantauan:\\n"+ex.toString(),
                "Cetak Pantauan",javax.swing.JOptionPane.ERROR_MESSAGE);
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Gagal menyiapkan cetakan:\\n"+ex.toString(),
                "Cetak Pantauan",javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean pegawaiAdaKotak(String nik){
        if(nik==null || nik.trim().equals("")) return false;
        try(java.sql.PreparedStatement ps=koneksi.prepareStatement("SELECT nama FROM pegawai WHERE nik=? LIMIT 1")){
            ps.setString(1,nik.trim());
            try(java.sql.ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    nikPetugasKotak=nik.trim();
                    namaPetugasKotak=rs.getString("nama");
                    return true;
                }
            }
        }catch(Exception ignored){}
        return false;
    }

    private String pastikanNikPegawaiKotak(){
        // 1) Gunakan petugas yang sudah tervalidasi selama form ini terbuka.
        if(pegawaiAdaKotak(nikPetugasKotak)) return nikPetugasKotak;

        // 2) User login normal: akses.getkode() biasanya berisi NIK pegawai.
        String login="";
        try{ login=akses.getkode(); }catch(Exception ignored){}
        if(pegawaiAdaKotak(login)) return nikPetugasKotak;

        // 3) Bila form lama sudah mempunyai petugas terpilih, gunakan NIK tersebut.
        try{
            if(KodeDokter!=null && pegawaiAdaKotak(KodeDokter.getText())) return nikPetugasKotak;
        }catch(Exception ignored){}

        // 4) Akun administratif tidak selalu merupakan pegawai. Minta NIK klinis yang mendampingi.
        while(true){
            String nik=javax.swing.JOptionPane.showInputDialog(
                this,
                "User login ("+(login==null?"":login)+") tidak terdaftar sebagai NIK pegawai.\n"+
                "Masukkan NIK petugas/perawat yang melakukan pencatatan:",
                "Pilih Petugas Pencatat",
                javax.swing.JOptionPane.QUESTION_MESSAGE
            );
            if(nik==null) return "";
            nik=nik.trim();
            if(pegawaiAdaKotak(nik)){
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Petugas aktif: "+namaPetugasKotak+" ("+nikPetugasKotak+")");
                return nikPetugasKotak;
            }
            javax.swing.JOptionPane.showMessageDialog(this,
                "NIK '"+nik+"' tidak ditemukan pada tabel pegawai.\nSilakan masukkan NIK petugas yang valid.",
                "NIK Tidak Ditemukan",
                javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private String shiftDariKolomV20(int col){
        if(col==5) return "P";
        if(col==6) return "S";
        if(col==7) return "SO";
        if(col==8) return "M";
        return "";
    }

    private java.sql.Time jamDefaultShiftV20(String shift){
        if("P".equals(shift)) return java.sql.Time.valueOf("06:00:00");
        if("S".equals(shift)) return java.sql.Time.valueOf("12:00:00");
        if("SO".equals(shift)) return java.sql.Time.valueOf("18:00:00");
        if("M".equals(shift)) return java.sql.Time.valueOf("22:00:00");
        return null;
    }

    private String namaShiftV20(String shift){
        if("P".equals(shift)) return "Pagi";
        if("S".equals(shift)) return "Siang";
        if("SO".equals(shift)) return "Sore";
        if("M".equals(shift)) return "Malam";
        return "";
    }

    private void catatDariKotak(int row,int col){
        Long idDetail=kotakDetailPerBaris.get(row);
        if(idDetail==null) return;

        String shift=shiftDariKolomV20(col);
        if(shift.equals("")) return;

        // V20: map jadwal memang disimpan berdasarkan row:col pada tampilan harian.
        Long idJadwal=kotakJadwal.get(row+":"+col);
        java.sql.Time jamDefault=jamDefaultShiftV20(shift);

        javax.swing.JComboBox<String> status=new javax.swing.JComboBox<String>(
            new String[]{"Diberikan","Terjadwal","Ditunda","MMO","STOP","Tidak Diberikan"});
        javax.swing.JTextField jamRencana=new javax.swing.JTextField(
            jamDefault==null?"":new java.text.SimpleDateFormat("HH:mm").format(jamDefault));
        javax.swing.JTextField real=new javax.swing.JTextField(
            new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date()));
        javax.swing.JTextField dosisPemberianV35=new javax.swing.JTextField();
        javax.swing.JTextField ket=new javax.swing.JTextField();

        // default dosis aktual dari dosis/sediaan obat
        try(java.sql.PreparedStatement pd=koneksi.prepareStatement(
            "SELECT IFNULL(dosis_sediaan,'') FROM pemberian_obat_ranap_detail WHERE id_detail=?")){
            pd.setLong(1,idDetail);
            try(java.sql.ResultSet rd=pd.executeQuery()){if(rd.next())dosisPemberianV35.setText(rd.getString(1));}
        }catch(Exception ignored){}

        if(idJadwal!=null){
            try(java.sql.PreparedStatement ps=koneksi.prepareStatement(
                "SELECT DATE_FORMAT(jam_rencana,'%H:%i'),status_pemberian,"+
                "IFNULL(DATE_FORMAT(jam_realisasi,'%H:%i'),''),IFNULL(keterangan,''),IFNULL(dosis_pemberian,'') "+
                "FROM pemberian_obat_ranap_jadwal WHERE id_jadwal=?")){
                ps.setLong(1,idJadwal);
                try(java.sql.ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        jamRencana.setText(rs.getString(1));
                        status.setSelectedItem(rs.getString(2));
                        if(!rs.getString(3).equals("")) real.setText(rs.getString(3));
                        ket.setText(rs.getString(4));
                        if(!rs.getString(5).trim().equals("")) dosisPemberianV35.setText(rs.getString(5));
                    }
                }
            }catch(Exception ex){
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Gagal membaca jadwal pemberian:\\n"+ex.toString());
                return;
            }
        }

        String namaObat="";
        try{
            Object v=tbKotakPantau.getValueAt(row,0);
            namaObat=v==null?"":v.toString();
        }catch(Exception ignored){}

        javax.swing.JPanel panel=new javax.swing.JPanel(new java.awt.GridLayout(0,2,7,7));
        panel.add(new javax.swing.JLabel("Obat")); panel.add(new javax.swing.JLabel(namaObat));
        panel.add(new javax.swing.JLabel("Tanggal")); panel.add(new javax.swing.JLabel(
            new java.text.SimpleDateFormat("dd-MM-yyyy").format(tanggalKotak())));
        panel.add(new javax.swing.JLabel("Periode")); panel.add(new javax.swing.JLabel(
            shift+" - "+namaShiftV20(shift)));
        panel.add(new javax.swing.JLabel("Jam rencana")); panel.add(jamRencana);
        panel.add(new javax.swing.JLabel("Status")); panel.add(status);
        panel.add(new javax.swing.JLabel("Jam realisasi")); panel.add(real);
        panel.add(new javax.swing.JLabel("Dosis diberikan")); panel.add(dosisPemberianV35);
        panel.add(new javax.swing.JLabel("Keterangan")); panel.add(ket);

        if(javax.swing.JOptionPane.showConfirmDialog(this,panel,"Catat Pemberian Obat",
            javax.swing.JOptionPane.OK_CANCEL_OPTION,javax.swing.JOptionPane.PLAIN_MESSAGE)
            !=javax.swing.JOptionPane.OK_OPTION) return;

        try{
            java.sql.Time jr=java.sql.Time.valueOf(jamRencana.getText().trim()+":00");
            String st=status.getSelectedItem().toString();

            java.sql.Time tm=null;
            if("Diberikan".equals(st)){
                String x=real.getText().trim();
                if(x.equals("")){
                    javax.swing.JOptionPane.showMessageDialog(this,
                        "Jam realisasi wajib diisi untuk status Diberikan.");
                    return;
                }
                tm=java.sql.Time.valueOf(x+":00");
            }

            String nik=pastikanNikPegawaiKotak();
            if(nik.equals("")) return;
            String nama=namaPetugasKotak;

            if(idJadwal==null){
                try(java.sql.PreparedStatement ps=koneksi.prepareStatement(
                    "INSERT INTO pemberian_obat_ranap_jadwal"+
                    "(id_detail,tanggal_pemberian,jam_rencana,jam_realisasi,status_pemberian,nik_petugas,nama_petugas,keterangan,dosis_pemberian) "+
                    "VALUES(?,?,?,?,?,?,?,?,?)")){
                    ps.setLong(1,idDetail);
                    ps.setDate(2,tanggalKotak());
                    ps.setTime(3,jr);
                    ps.setTime(4,tm);
                    ps.setString(5,st);
                    ps.setString(6,nik);
                    ps.setString(7,nama);
                    ps.setString(8,ket.getText().trim());
                    ps.setString(9,dosisPemberianV35.getText().trim());
                    ps.executeUpdate();
                }
            }else{
                try(java.sql.PreparedStatement ps=koneksi.prepareStatement(
                    "UPDATE pemberian_obat_ranap_jadwal SET "+
                    "jam_rencana=?,jam_realisasi=?,status_pemberian=?,nik_petugas=?,nama_petugas=?,keterangan=?,dosis_pemberian=? "+
                    "WHERE id_jadwal=?")){
                    ps.setTime(1,jr);
                    ps.setTime(2,tm);
                    ps.setString(3,st);
                    ps.setString(4,nik);
                    ps.setString(5,nama);
                    ps.setString(6,ket.getText().trim());
                    ps.setString(7,dosisPemberianV35.getText().trim());
                    ps.setLong(8,idJadwal);
                    ps.executeUpdate();
                }
            }
            muatKotakPemberian();
        }catch(Exception ex){
            String msg=ex.getMessage();
            if(msg==null || msg.trim().equals("")) msg=ex.toString();
            javax.swing.JOptionPane.showMessageDialog(this,
                "Gagal menyimpan pemberian:\\n"+msg,
                "Pemberian Obat",javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private String cariNoSuratKotak(){
        try(java.sql.PreparedStatement p=koneksi.prepareStatement("SELECT no_surat FROM pemberian_obat_ranap WHERE no_rawat=? AND status_dokumen<>'Batal' ORDER BY tanggal_catat DESC,jam_catat DESC LIMIT 1")){p.setString(1,TNoRw.getText());try(java.sql.ResultSet r=p.executeQuery()){if(r.next())return r.getString(1);}}catch(Exception ignored){} return "";
    }

    private String pastikanNoSuratKotak(){
        String no=cariNoSuratKotak(); if(!no.equals(""))return no;
        no="POR"+new java.text.SimpleDateFormat("yyMMddHHmmss").format(new java.util.Date());
        String nik=pastikanNikPegawaiKotak(); if(nik.equals("")) return "";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement("INSERT INTO pemberian_obat_ranap(no_surat,no_rawat,tanggal_catat,jam_catat,nik_pencatat,status_dokumen) VALUES(?,?,CURDATE(),CURTIME(),?,'Aktif')")){p.setString(1,no);p.setString(2,TNoRw.getText());p.setString(3,nik);p.executeUpdate();return no;}catch(Exception ex){javax.swing.JOptionPane.showMessageDialog(this,"Gagal membuat dokumen: "+ex.getMessage());return "";}
    }

    private boolean pemberianBisaDihapusV33(long idJadwal){
        String login=kodeLoginV32();
        if(login.equals("")){
            javax.swing.JOptionPane.showMessageDialog(this,"ID user login tidak ditemukan.");
            return false;
        }

        String nik="",status="";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "SELECT IFNULL(nik_petugas,''),IFNULL(status_pemberian,'') "+
            "FROM pemberian_obat_ranap_jadwal WHERE id_jadwal=?")){
            p.setLong(1,idJadwal);
            try(java.sql.ResultSet r=p.executeQuery()){
                if(!r.next()) return false;
                nik=r.getString(1); status=r.getString(2);
            }
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Gagal membaca catatan pemberian:\n"+ex.toString());
            return false;
        }

        if(nik.equals("")){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Catatan ini belum mempunyai ID petugas pemberi.");
            return false;
        }
        if(!nik.equals(login)){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Catatan pemberian hanya dapat dihapus/dibatalkan oleh user yang mencatatnya.\n"+
                "ID pencatat: "+nik+"\nID login Anda: "+login,
                "Akses Ditolak",javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int audit=0;
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "SELECT "+
            "(SELECT COUNT(*) FROM pemberian_obat_ranap_validasi v WHERE v.id_jadwal=? AND v.status_validasi='Valid') + "+
            "(SELECT COUNT(*) FROM pemberian_obat_ranap_verifikasi_item vi "+
            " INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi "+
            " WHERE vi.id_jadwal=? AND vv.status_verifikasi='Aktif')")){
            p.setLong(1,idJadwal); p.setLong(2,idJadwal);
            try(java.sql.ResultSet r=p.executeQuery()){if(r.next()) audit=r.getInt(1);}
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Gagal memeriksa validasi/TTE:\n"+ex.toString());
            return false;
        }

        if(audit>0){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Catatan pemberian tidak dapat dihapus karena sudah memiliki Validasi DPJP/Farmasi atau TTE pasien.\n"+
                "Untuk menjaga audit trail, gunakan koreksi/status klinis yang sesuai.",
                "Catatan Sudah Tervalidasi",javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void hapusPemberianV33(long idJadwal){
        if(!pemberianBisaDihapusV33(idJadwal)) return;

        int ok=javax.swing.JOptionPane.showConfirmDialog(this,
            "Batalkan catatan pemberian ini?\n\n"+
            "Jam realisasi dan identitas petugas akan dibersihkan,\n"+
            "sedangkan jadwal obat tetap dipertahankan sebagai TERJADWAL.",
            "Konfirmasi Hapus Pemberian",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE);
        if(ok!=javax.swing.JOptionPane.YES_OPTION) return;

        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "UPDATE pemberian_obat_ranap_jadwal SET "+
            "jam_realisasi=NULL,status_pemberian='Terjadwal',nik_petugas=NULL,nama_petugas=NULL,keterangan='' "+
            "WHERE id_jadwal=? AND nik_petugas=?")){
            p.setLong(1,idJadwal);
            p.setString(2,kodeLoginV32());
            int n=p.executeUpdate();
            if(n>0){
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Catatan pemberian berhasil dibatalkan.\nJadwal kembali menjadi TERJADWAL.");
                muatKotakPemberian();
            }else{
                javax.swing.JOptionPane.showMessageDialog(this,"Catatan pemberian tidak berubah.");
            }
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Gagal membatalkan pemberian:\n"+ex.toString(),
                "Hapus Pemberian",javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private String tooltipJadwalV35(long idJadwal){
        String q=
            "SELECT j.tanggal_pemberian,j.jam_rencana,j.jam_realisasi,j.status_pemberian,"+
            "IFNULL(j.dosis_pemberian,'') dosis_pemberian,IFNULL(j.nama_petugas,'') nama_petugas,"+
            "IFNULL(j.keterangan,'') keterangan,d.nama_obat,IFNULL(d.dosis_sediaan,'') dosis_sediaan,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_validasi v WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='DPJP' AND v.status_validasi='Valid') dpjp,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_validasi v WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='Farmasi' AND v.status_validasi='Valid') farmasi,"+
            "(SELECT vv.nama_penandatangan FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_nama,"+
            "(SELECT vv.hubungan FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_hubungan,"+
            "(SELECT vv.waktu_verifikasi FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_waktu,"+
            "(SELECT vv.nama_pendamping FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_pendamping "+
            "FROM pemberian_obat_ranap_jadwal j INNER JOIN pemberian_obat_ranap_detail d ON d.id_detail=j.id_detail "+
            "WHERE j.id_jadwal=?";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(q)){
            p.setLong(1,idJadwal);
            try(java.sql.ResultSet r=p.executeQuery()){
                if(r.next()){
                    String tgl=r.getDate("tanggal_pemberian")==null?"-":new java.text.SimpleDateFormat("dd-MM-yyyy").format(r.getDate("tanggal_pemberian"));
                    String jr=r.getTime("jam_rencana")==null?"-":new java.text.SimpleDateFormat("HH:mm").format(r.getTime("jam_rencana"));
                    String real=r.getTime("jam_realisasi")==null?"-":new java.text.SimpleDateFormat("HH:mm").format(r.getTime("jam_realisasi"));
                    String dosis=r.getString("dosis_pemberian");
                    if(dosis==null||dosis.trim().equals(""))dosis=r.getString("dosis_sediaan");
                    String tteNama=r.getString("tte_nama");
                    String tteHub=r.getString("tte_hubungan");
                    java.sql.Timestamp tteWaktu=r.getTimestamp("tte_waktu");
                    String ttePend=r.getString("tte_pendamping");
                    boolean adaTte=tteNama!=null && !tteNama.trim().equals("");
                    return "<html><b>"+r.getString("nama_obat")+"</b><br>"+
                        "Tanggal: "+tgl+"<br>"+
                        "Jam rencana: "+jr+"<br>"+
                        "Jam realisasi: "+real+"<br>"+
                        "Dosis diberikan: "+(dosis==null||dosis.trim().equals("")?"-":dosis)+"<br>"+
                        "Status: "+r.getString("status_pemberian")+"<br>"+
                        "Petugas: "+(r.getString("nama_petugas").trim().equals("")?"-":r.getString("nama_petugas"))+"<br>"+
                        "DPJP: "+(r.getBoolean("dpjp")?"Sudah validasi":"Belum validasi")+"<br>"+
                        "Farmasi: "+(r.getBoolean("farmasi")?"Sudah validasi":"Belum validasi")+"<br>"+
                        "<b>TTE Pasien: "+(adaTte?"SUDAH ✓":"BELUM")+"</b><br>"+
                        (adaTte?("Penandatangan: "+tteNama+" ("+(tteHub==null?"-":tteHub)+")<br>"+
                                 "Waktu TTE: "+new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm").format(tteWaktu)+"<br>"+
                                 "Pendamping: "+(ttePend==null||ttePend.trim().equals("")?"-":ttePend)+"<br>"):"")+
                        "Keterangan: "+(r.getString("keterangan").trim().equals("")?"-":r.getString("keterangan"))+
                        "</html>";
                }
            }
        }catch(Exception ignored){}
        return null;
    }

    private boolean sudahTtePasienV37(long idJadwal){
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "SELECT COUNT(*) FROM pemberian_obat_ranap_verifikasi_item vi "+
            "INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi "+
            "WHERE vi.id_jadwal=? AND vv.status_verifikasi='Aktif'")){
            p.setLong(1,idJadwal);
            try(java.sql.ResultSet r=p.executeQuery()){return r.next() && r.getInt(1)>0;}
        }catch(Exception ex){return false;}
    }

    private java.awt.image.BufferedImage bacaGambarTteV37(String signaturePath){
        if(signaturePath==null || signaturePath.trim().equals("")) return null;
        String p=signaturePath.trim();
        try{
            if(p.startsWith("http://") || p.startsWith("https://"))
                return javax.imageio.ImageIO.read(new java.net.URL(p));
        }catch(Exception ignored){}

        java.util.List<java.io.File> files=new java.util.ArrayList<java.io.File>();
        files.add(new java.io.File(p));
        if(p.startsWith("/")){
            files.add(new java.io.File("/Applications/XAMPP/xamppfiles/htdocs"+p));
            files.add(new java.io.File("/Applications/XAMPP/xamppfiles/htdocs/verified-2"+p));
        }
        for(java.io.File f:files){
            try{if(f.exists() && f.isFile()) return javax.imageio.ImageIO.read(f);}catch(Exception ignored){}
        }

        String webPath=p.startsWith("/")?p:"/"+p;
        for(String base:new String[]{"http://127.0.0.1","http://localhost"}){
            try{
                java.awt.image.BufferedImage im=javax.imageio.ImageIO.read(new java.net.URL(base+webPath));
                if(im!=null)return im;
            }catch(Exception ignored){}
        }
        return null;
    }

    private void lihatTtePasienV37(long idJadwal){
        String q=
            "SELECT vv.kode_verifikasi,vv.waktu_verifikasi,vv.nama_penandatangan,vv.hubungan,"+
            "IFNULL(vv.nama_pendamping,'') nama_pendamping,IFNULL(vv.signature_path,'') signature_path,"+
            "IFNULL(vv.pernyataan_version,'') pernyataan_version "+
            "FROM pemberian_obat_ranap_verifikasi_item vi "+
            "INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi "+
            "WHERE vi.id_jadwal=? AND vv.status_verifikasi='Aktif' "+
            "ORDER BY vv.waktu_verifikasi DESC LIMIT 1";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(q)){
            p.setLong(1,idJadwal);
            try(java.sql.ResultSet r=p.executeQuery()){
                if(!r.next()){
                    javax.swing.JOptionPane.showMessageDialog(this,"Pemberian ini belum ditandatangani pasien/keluarga.");
                    return;
                }

                String nama=r.getString("nama_penandatangan");
                String hub=r.getString("hubungan");
                String pend=r.getString("nama_pendamping");
                String path=r.getString("signature_path");
                java.sql.Timestamp wt=r.getTimestamp("waktu_verifikasi");

                javax.swing.JPanel root=new javax.swing.JPanel(new java.awt.BorderLayout(8,8));
                root.setBorder(javax.swing.BorderFactory.createEmptyBorder(10,10,10,10));
                root.setBackground(java.awt.Color.WHITE);

                javax.swing.JLabel info=new javax.swing.JLabel(
                    "<html><font color='#1F7A4C'><b>✓ TTE PASIEN / KELUARGA TERVERIFIKASI</b></font><br><br>"+
                    "Penandatangan : <b>"+nama+"</b><br>"+
                    "Hubungan : "+(hub==null?"-":hub)+"<br>"+
                    "Waktu TTE : "+(wt==null?"-":new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(wt))+"<br>"+
                    "Petugas pendamping : "+(pend==null||pend.trim().equals("")?"-":pend)+"<br>"+
                    "Kode verifikasi : "+r.getString("kode_verifikasi")+"</html>");
                info.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207,227,216)),
                    javax.swing.BorderFactory.createEmptyBorder(8,10,8,10)));
                info.setOpaque(true);info.setBackground(new java.awt.Color(239,249,243));
                root.add(info,java.awt.BorderLayout.NORTH);

                javax.swing.JPanel imagePanel=new javax.swing.JPanel(new java.awt.BorderLayout());
                imagePanel.setBackground(java.awt.Color.WHITE);
                imagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                    javax.swing.BorderFactory.createLineBorder(new java.awt.Color(205,218,228)),
                    "Bukti Tanda Tangan Pasien/Keluarga"));
                java.awt.image.BufferedImage img=bacaGambarTteV37(path);
                if(img!=null){
                    int maxW=560,maxH=250;
                    double sc=Math.min((double)maxW/img.getWidth(),(double)maxH/img.getHeight());
                    sc=Math.min(1.0,sc);
                    int w=Math.max(1,(int)(img.getWidth()*sc));
                    int h=Math.max(1,(int)(img.getHeight()*sc));
                    java.awt.Image scaled=img.getScaledInstance(w,h,java.awt.Image.SCALE_SMOOTH);
                    javax.swing.JLabel pic=new javax.swing.JLabel(new javax.swing.ImageIcon(scaled),javax.swing.SwingConstants.CENTER);
                    pic.setPreferredSize(new java.awt.Dimension(620,285));
                    imagePanel.add(pic,java.awt.BorderLayout.CENTER);
                }else{
                    javax.swing.JLabel noImg=new javax.swing.JLabel(
                        "<html><center><b>File tanda tangan tersimpan, tetapi gambar belum dapat dibaca dari komputer ini.</b><br>"+
                        "Lokasi: "+path+"<br><br>Pastikan folder Verified/XAMPP dapat diakses.</center></html>",
                        javax.swing.SwingConstants.CENTER);
                    noImg.setPreferredSize(new java.awt.Dimension(620,210));
                    imagePanel.add(noImg,java.awt.BorderLayout.CENTER);
                }
                root.add(imagePanel,java.awt.BorderLayout.CENTER);

                javax.swing.JLabel footV38=new javax.swing.JLabel(
                    "<html><font color='#657789'>Bukti TTE berasal dari aplikasi Verified dan terhubung ke catatan pemberian obat ini.</font></html>");
                footV38.setBorder(javax.swing.BorderFactory.createEmptyBorder(4,4,0,4));
                root.add(footV38,java.awt.BorderLayout.SOUTH);
                javax.swing.JOptionPane.showMessageDialog(this,root,"Bukti TTE Pasien/Keluarga",javax.swing.JOptionPane.PLAIN_MESSAGE);
            }
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Gagal membaca TTE pasien:\n"+ex.toString());
        }
    }

    private void lihatDetailJadwalV35(long idJadwal){
        String html=tooltipJadwalV35(idJadwal);
        if(html==null) return;
        javax.swing.JLabel l=new javax.swing.JLabel(html);
        l.setBorder(javax.swing.BorderFactory.createEmptyBorder(10,12,10,12));
        javax.swing.JOptionPane.showMessageDialog(this,l,"Detail Pemberian Obat",javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void lihatDetailPemberianTteV39(long idJadwal){
        String q=
            "SELECT d.nama_obat,IFNULL(d.dosis_sediaan,'') dosis_sediaan,IFNULL(d.aturan_pakai,'') aturan_pakai,"+
            "IFNULL(d.rute,'') rute,j.tanggal_pemberian,j.jam_rencana,j.jam_realisasi,j.status_pemberian,"+
            "IFNULL(j.dosis_pemberian,'') dosis_pemberian,IFNULL(j.nama_petugas,'') nama_petugas,IFNULL(j.keterangan,'') keterangan,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_validasi v WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='DPJP' AND v.status_validasi='Valid') dpjp,"+
            "EXISTS(SELECT 1 FROM pemberian_obat_ranap_validasi v WHERE v.id_jadwal=j.id_jadwal AND v.jenis_validasi='Farmasi' AND v.status_validasi='Valid') farmasi,"+
            "(SELECT vv.kode_verifikasi FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) kode_verifikasi,"+
            "(SELECT vv.nama_penandatangan FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_nama,"+
            "(SELECT vv.hubungan FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_hubungan,"+
            "(SELECT vv.waktu_verifikasi FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_waktu,"+
            "(SELECT vv.nama_pendamping FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_pendamping,"+
            "(SELECT vv.signature_path FROM pemberian_obat_ranap_verifikasi_item vi INNER JOIN pemberian_obat_ranap_verifikasi vv ON vv.kode_verifikasi=vi.kode_verifikasi WHERE vi.id_jadwal=j.id_jadwal AND vv.status_verifikasi='Aktif' ORDER BY vv.waktu_verifikasi DESC LIMIT 1) tte_path "+
            "FROM pemberian_obat_ranap_jadwal j INNER JOIN pemberian_obat_ranap_detail d ON d.id_detail=j.id_detail WHERE j.id_jadwal=?";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(q)){
            p.setLong(1,idJadwal);
            try(java.sql.ResultSet r=p.executeQuery()){
                if(!r.next()) return;

                String dosis=r.getString("dosis_pemberian");
                if(dosis==null || dosis.trim().equals("")) dosis=r.getString("dosis_sediaan");
                String tgl=r.getDate("tanggal_pemberian")==null?"-":new java.text.SimpleDateFormat("dd-MM-yyyy").format(r.getDate("tanggal_pemberian"));
                String jr=r.getTime("jam_rencana")==null?"-":new java.text.SimpleDateFormat("HH:mm").format(r.getTime("jam_rencana"));
                String real=r.getTime("jam_realisasi")==null?"-":new java.text.SimpleDateFormat("HH:mm").format(r.getTime("jam_realisasi"));

                javax.swing.JPanel root=new javax.swing.JPanel(new java.awt.BorderLayout(8,8));
                root.setBorder(javax.swing.BorderFactory.createEmptyBorder(9,9,9,9));
                root.setBackground(java.awt.Color.WHITE);

                String info="<html><table cellpadding='3' cellspacing='0'>"+
                    "<tr><td><b>Obat</b></td><td>:</td><td><b>"+r.getString("nama_obat")+"</b></td></tr>"+
                    "<tr><td>Dosis diberikan</td><td>:</td><td><b>"+(dosis==null?"-":dosis)+"</b></td></tr>"+
                    "<tr><td>Aturan / Rute</td><td>:</td><td>"+r.getString("aturan_pakai")+" / "+r.getString("rute")+"</td></tr>"+
                    "<tr><td>Tanggal</td><td>:</td><td>"+tgl+"</td></tr>"+
                    "<tr><td>Jam rencana</td><td>:</td><td>"+jr+"</td></tr>"+
                    "<tr><td>Jam realisasi</td><td>:</td><td><b>"+real+"</b></td></tr>"+
                    "<tr><td>Status</td><td>:</td><td>"+r.getString("status_pemberian")+"</td></tr>"+
                    "<tr><td>Petugas pemberi</td><td>:</td><td>"+(r.getString("nama_petugas").trim().equals("")?"-":r.getString("nama_petugas"))+"</td></tr>"+
                    "<tr><td>Validasi DPJP</td><td>:</td><td>"+(r.getBoolean("dpjp")?"✓ Sudah":"Belum")+"</td></tr>"+
                    "<tr><td>Validasi Farmasi</td><td>:</td><td>"+(r.getBoolean("farmasi")?"✓ Sudah":"Belum")+"</td></tr>"+
                    "<tr><td>Keterangan</td><td>:</td><td>"+(r.getString("keterangan").trim().equals("")?"-":r.getString("keterangan"))+"</td></tr>"+
                    "</table></html>";
                javax.swing.JLabel detail=new javax.swing.JLabel(info);
                detail.setBorder(javax.swing.BorderFactory.createTitledBorder("Detail Pemberian • ID Jadwal "+idJadwal));
                root.add(detail,java.awt.BorderLayout.NORTH);

                String tteNama=r.getString("tte_nama");
                boolean adaTte=tteNama!=null && !tteNama.trim().equals("");
                javax.swing.JPanel tte=new javax.swing.JPanel(new java.awt.BorderLayout(6,6));
                tte.setBackground(java.awt.Color.WHITE);
                tte.setBorder(javax.swing.BorderFactory.createTitledBorder("TTE Khusus Pemberian Ini"));

                if(adaTte){
                    String meta="<html><font color='#1F7A4C'><b>✓ SUDAH DITANDATANGANI PASIEN/KELUARGA</b></font><br>"+
                        "Penandatangan: <b>"+tteNama+"</b> ("+(r.getString("tte_hubungan")==null?"-":r.getString("tte_hubungan"))+")<br>"+
                        "Waktu TTE: "+(r.getTimestamp("tte_waktu")==null?"-":new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm").format(r.getTimestamp("tte_waktu")))+"<br>"+
                        "Pendamping: "+(r.getString("tte_pendamping")==null?"-":r.getString("tte_pendamping"))+"<br>"+
                        "Kode verifikasi: "+(r.getString("kode_verifikasi")==null?"-":r.getString("kode_verifikasi"))+"</html>";
                    tte.add(new javax.swing.JLabel(meta),java.awt.BorderLayout.NORTH);

                    java.awt.image.BufferedImage img=bacaGambarTteV37(r.getString("tte_path"));
                    if(img!=null){
                        int maxW=570,maxH=205;
                        double sc=Math.min((double)maxW/img.getWidth(),(double)maxH/img.getHeight());
                        sc=Math.min(1.0,sc);
                        int w=Math.max(1,(int)Math.round(img.getWidth()*sc));
                        int h=Math.max(1,(int)Math.round(img.getHeight()*sc));
                        javax.swing.JLabel pic=new javax.swing.JLabel(
                            new javax.swing.ImageIcon(img.getScaledInstance(w,h,java.awt.Image.SCALE_SMOOTH)),
                            javax.swing.SwingConstants.CENTER);
                        pic.setPreferredSize(new java.awt.Dimension(610,225));
                        tte.add(pic,java.awt.BorderLayout.CENTER);
                    }else{
                        javax.swing.JLabel noImg=new javax.swing.JLabel(
                            "<html><center><b>TTE tersimpan tetapi gambar belum dapat dibaca.</b><br>"+
                            "Path: "+(r.getString("tte_path")==null?"-":r.getString("tte_path"))+"</center></html>",
                            javax.swing.SwingConstants.CENTER);
                        noImg.setPreferredSize(new java.awt.Dimension(610,100));
                        tte.add(noImg,java.awt.BorderLayout.CENTER);
                    }
                }else{
                    javax.swing.JLabel belum=new javax.swing.JLabel(
                        "<html><center><font color='#8A6A12'><b>BELUM ADA TTE UNTUK PEMBERIAN INI</b></font><br>"+
                        "Setiap jam/kejadian pemberian mempunyai tanda tangan pasien sendiri.</center></html>",
                        javax.swing.SwingConstants.CENTER);
                    belum.setPreferredSize(new java.awt.Dimension(610,100));
                    tte.add(belum,java.awt.BorderLayout.CENTER);
                }
                root.add(tte,java.awt.BorderLayout.CENTER);

                Object[] opsi={"Tutup","Validasi Farmasi","Validasi DPJP","Lihat Validasi"};
                int x=javax.swing.JOptionPane.showOptionDialog(this,root,"Detail Pemberian Obat",
                    javax.swing.JOptionPane.DEFAULT_OPTION,javax.swing.JOptionPane.PLAIN_MESSAGE,null,opsi,opsi[0]);
                if(x==1) validasiKotak(idJadwal,"Farmasi");
                else if(x==2) validasiKotak(idJadwal,"DPJP");
                else if(x==3) lihatValidasiKotak(idJadwal);
            }
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Gagal membaca detail pemberian/TTE:\n"+ex.toString());
        }
    }

    private void aksiKotakPemberian(int row,int col){
        Long idDetail=kotakDetailPerBaris.get(row);
        if(idDetail==null || col<5 || col>8) return;

        Long idJadwal=kotakJadwal.get(row+":"+col);
        String shift=shiftDariKolomV20(col);

        if(idJadwal==null){
            catatDariKotak(row,col);
            return;
        }

        String st="";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "SELECT status_pemberian FROM pemberian_obat_ranap_jadwal WHERE id_jadwal=?")){
            p.setLong(1,idJadwal);
            try(java.sql.ResultSet r=p.executeQuery()){if(r.next()) st=r.getString(1);}
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Gagal membaca pemberian: "+ex.toString());
            return;
        }

        if(!"Diberikan".equals(st)){
            catatDariKotak(row,col);
            return;
        }

        // V39: klik data jam pemberian langsung menampilkan detail + TTE untuk id_jadwal tersebut.
        lihatDetailPemberianTteV39(idJadwal);
    }

    private boolean userDokterV30(){
        String kode="";
        try{kode=akses.getkode();}catch(Exception ignored){}
        if(kode==null || kode.trim().equals("")) return false;
        // User dokter dianggap valid bila kode login terdaftar sebagai kd_dokter.
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "SELECT COUNT(*) FROM dokter WHERE kd_dokter=?")){
            p.setString(1,kode.trim());
            try(java.sql.ResultSet r=p.executeQuery()){return r.next() && r.getInt(1)>0;}
        }catch(Exception ex){return false;}
    }

    private boolean userFarmasiV30(){
        String kode="";
        try{kode=akses.getkode();}catch(Exception ignored){}
        if(kode==null || kode.trim().equals("")) return false;

        // Validasi Farmasi: akun harus pegawai yang tercatat di petugas/pegawai
        // dan memiliki keterkaitan ke unit/departemen farmasi/apotek.
        String q=
            "SELECT COUNT(*) FROM pegawai p "+
            "LEFT JOIN petugas pt ON pt.nip=p.nik "+
            "LEFT JOIN departemen d ON d.dep_id=p.departemen "+
            "WHERE p.nik=? AND ("+
            "LOWER(IFNULL(d.nama,'') ) LIKE '%farmasi%' OR "+
            "LOWER(IFNULL(d.nama,'') ) LIKE '%apotek%' OR "+
            "LOWER(IFNULL(p.jbtn,'')) LIKE '%farmasi%' OR "+
            "LOWER(IFNULL(p.jbtn,'')) LIKE '%apoteker%' OR "+
            "LOWER(IFNULL(p.jbtn,'')) LIKE '%asisten apoteker%')";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(q)){
            p.setString(1,kode.trim());
            try(java.sql.ResultSet r=p.executeQuery()){return r.next() && r.getInt(1)>0;}
        }catch(Exception ex){
            // Fallback: bila struktur departemen berbeda, cek jabatan pegawai saja.
            try(java.sql.PreparedStatement p=koneksi.prepareStatement(
                "SELECT COUNT(*) FROM pegawai WHERE nik=? AND ("+
                "LOWER(IFNULL(jbtn,'')) LIKE '%farmasi%' OR "+
                "LOWER(IFNULL(jbtn,'')) LIKE '%apoteker%' OR "+
                "LOWER(IFNULL(jbtn,'')) LIKE '%asisten apoteker%')")){
                p.setString(1,kode.trim());
                try(java.sql.ResultSet r=p.executeQuery()){return r.next() && r.getInt(1)>0;}
            }catch(Exception ignored2){return false;}
        }
    }

    private boolean bolehValidasiV30(String jenis){
        if("DPJP".equalsIgnoreCase(jenis)){
            if(!userDokterV30()){
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Validasi DPJP hanya dapat dilakukan oleh user dokter.",
                    "Akses Validasi",javax.swing.JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        }
        if("Farmasi".equalsIgnoreCase(jenis)){
            if(!userFarmasiV30()){
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Validasi Farmasi hanya dapat dilakukan oleh user farmasi/apoteker.",
                    "Akses Validasi",javax.swing.JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        }
        return false;
    }

    private void validasiKotak(long idJadwal,String jenis){
        if(!bolehValidasiV30(jenis)) return;
        try{
            String kode="",nama="";
            if("Farmasi".equals(jenis)){
                kode=pastikanNikPegawaiKotak(); if(kode.equals(""))return; nama=namaPetugasKotak;
            }else{
                String kdDpjp="";
                try(java.sql.PreparedStatement p=koneksi.prepareStatement("SELECT kd_dokter FROM reg_periksa WHERE no_rawat=?")){p.setString(1,TNoRw.getText());try(java.sql.ResultSet r=p.executeQuery()){if(r.next())kdDpjp=r.getString(1);}}
                String input=javax.swing.JOptionPane.showInputDialog(this,"Masukkan kode DPJP untuk validasi:",kdDpjp); if(input==null)return; input=input.trim();
                if(!input.equals(kdDpjp)){javax.swing.JOptionPane.showMessageDialog(this,"Validasi DPJP ditolak. Kode harus sesuai DPJP pasien ("+kdDpjp+").");return;}
                kode=input;
                try(java.sql.PreparedStatement p=koneksi.prepareStatement("SELECT nm_dokter FROM dokter WHERE kd_dokter=?")){p.setString(1,kode);try(java.sql.ResultSet r=p.executeQuery()){if(r.next())nama=r.getString(1);}}
                if(nama.equals("")){javax.swing.JOptionPane.showMessageDialog(this,"Dokter DPJP tidak ditemukan.");return;}
            }
            String cat=javax.swing.JOptionPane.showInputDialog(this,"Catatan validasi "+jenis+" (opsional):",""); if(cat==null)return;
            try(java.sql.PreparedStatement cek=koneksi.prepareStatement("SELECT 1 FROM pemberian_obat_ranap_validasi WHERE id_jadwal=? AND jenis_validasi=? AND status_validasi='Valid' LIMIT 1")){cek.setLong(1,idJadwal);cek.setString(2,jenis);try(java.sql.ResultSet rr=cek.executeQuery()){if(rr.next()){javax.swing.JOptionPane.showMessageDialog(this,"Pemberian ini sudah divalidasi "+jenis+".");return;}}}
            String raw=idJadwal+"|"+jenis+"|"+kode+"|"+System.currentTimeMillis();
            String hash=sha256Kotak(raw);
            try(java.sql.PreparedStatement p=koneksi.prepareStatement("INSERT INTO pemberian_obat_ranap_validasi(id_jadwal,jenis_validasi,kode_validator,nama_validator,catatan_validasi,validasi_hash) VALUES(?,?,?,?,?,?)")){
                p.setLong(1,idJadwal);p.setString(2,jenis);p.setString(3,kode);p.setString(4,nama);p.setString(5,cat);p.setString(6,hash);p.executeUpdate();
            }
            javax.swing.JOptionPane.showMessageDialog(this,"Validasi "+jenis+" berhasil oleh "+nama+".");muatKotakPemberian();
        }catch(Exception ex){javax.swing.JOptionPane.showMessageDialog(this,"Validasi "+jenis+" gagal: "+ex.getMessage());}
    }

    private String sha256Kotak(String txt){
        try{java.security.MessageDigest md=java.security.MessageDigest.getInstance("SHA-256");byte[] b=md.digest(txt.getBytes("UTF-8"));StringBuilder sb=new StringBuilder();for(byte x:b)sb.append(String.format("%02x",x));return sb.toString();}catch(Exception e){return "";}
    }

    private void lihatValidasiKotak(long idJadwal){
        StringBuilder sb=new StringBuilder("VALIDASI PEMBERIAN\n\n");
        try(java.sql.PreparedStatement p=koneksi.prepareStatement("SELECT jenis_validasi,nama_validator,waktu_validasi,catatan_validasi FROM pemberian_obat_ranap_validasi WHERE id_jadwal=? AND status_validasi='Valid' ORDER BY waktu_validasi")){
            p.setLong(1,idJadwal);try(java.sql.ResultSet r=p.executeQuery()){boolean ada=false;while(r.next()){ada=true;sb.append(r.getString(1)).append(" ✓\n  ").append(r.getString(2)).append("\n  ").append(r.getString(3)).append("\n  ").append(r.getString(4)==null?"":r.getString(4)).append("\n\n");}if(!ada)sb.append("Belum ada validasi Farmasi/DPJP.");}
        }catch(Exception ex){sb.append("Gagal membaca: ").append(ex.getMessage());}
        javax.swing.JOptionPane.showMessageDialog(this,sb.toString(),"Validasi Pemberian",javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private String kodeLoginV32(){
        try{
            String k=akses.getkode();
            return k==null?"":k.trim();
        }catch(Exception e){return "";}
    }

    private String namaLoginV32(){
        String k=kodeLoginV32();
        if(k.equals("")) return "";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "SELECT nm_dokter nama FROM dokter WHERE kd_dokter=? LIMIT 1")){
            p.setString(1,k); try(java.sql.ResultSet r=p.executeQuery()){if(r.next())return r.getString(1);}
        }catch(Exception ignored){}
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "SELECT nama FROM pegawai WHERE nik=? LIMIT 1")){
            p.setString(1,k); try(java.sql.ResultSet r=p.executeQuery()){if(r.next())return r.getString(1);}
        }catch(Exception ignored){}
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "SELECT nama FROM petugas WHERE nip=? LIMIT 1")){
            p.setString(1,k); try(java.sql.ResultSet r=p.executeQuery()){if(r.next())return r.getString(1);}
        }catch(Exception ignored){}
        return k;
    }

    private void hapusObatByUserV32(){
        int row=tbKotakPantau.getSelectedRow();
        if(row<0){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Pilih baris obat yang akan dihapus terlebih dahulu.");
            return;
        }
        row=tbKotakPantau.convertRowIndexToModel(row);
        Long idDetail=kotakDetailPerBaris.get(row);
        if(idDetail==null){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Pilih baris obat, bukan baris kelompok.");
            return;
        }

        String userInput="",namaObat="";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "SELECT IFNULL(user_input,''),nama_obat FROM pemberian_obat_ranap_detail WHERE id_detail=?")){
            p.setLong(1,idDetail);
            try(java.sql.ResultSet r=p.executeQuery()){
                if(r.next()){userInput=r.getString(1);namaObat=r.getString(2);}
                else return;
            }
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Gagal membaca pemilik input obat: "+ex.toString());
            return;
        }

        String login=kodeLoginV32();
        if(userInput.equals("")){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Obat ini merupakan data lama dan belum memiliki ID penginput.\\n"+
                "Untuk menjaga audit trail, data ini tidak dapat dihapus otomatis.");
            return;
        }
        if(!userInput.equals(login)){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Obat ini hanya dapat dihapus oleh user yang menginputnya.\\n"+
                "ID penginput: "+userInput+"\\nID login Anda: "+login,
                "Akses Ditolak",javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        int realisasi=0;
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "SELECT COUNT(*) FROM pemberian_obat_ranap_jadwal WHERE id_detail=? "+
            "AND status_pemberian IN('Diberikan','MMO','STOP','Ditunda','Tidak Diberikan')")){
            p.setLong(1,idDetail);
            try(java.sql.ResultSet r=p.executeQuery()){if(r.next())realisasi=r.getInt(1);}
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Gagal memeriksa riwayat obat: "+ex.toString());return;
        }

        if(realisasi>0){
            javax.swing.JOptionPane.showMessageDialog(this,
                "Obat tidak dapat dihapus karena sudah mempunyai catatan/realisasi pemberian.\\n"+
                "Gunakan status STOP/Selesai agar jejak audit tetap tersimpan.",
                "Tidak Dapat Dihapus",javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ok=javax.swing.JOptionPane.showConfirmDialog(this,
            "Hapus obat berikut?\\n\\n"+namaObat+
            "\\n\\nJadwal yang masih Terjadwal akan ikut terhapus.",
            "Konfirmasi Hapus Obat",javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE);
        if(ok!=javax.swing.JOptionPane.YES_OPTION)return;

        try(java.sql.PreparedStatement p=koneksi.prepareStatement(
            "DELETE FROM pemberian_obat_ranap_detail WHERE id_detail=? AND user_input=?")){
            p.setLong(1,idDetail); p.setString(2,login);
            int n=p.executeUpdate();
            if(n>0){
                javax.swing.JOptionPane.showMessageDialog(this,"Obat berhasil dihapus.");
                muatKotakPemberian();
            }else javax.swing.JOptionPane.showMessageDialog(this,"Obat tidak terhapus.");
        }catch(Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"Gagal menghapus obat: "+ex.toString());
        }
    }

    private void ambilObatResepKotak(){
        if(TNoRw.getText().trim().equals("")){javax.swing.JOptionPane.showMessageDialog(this,"Pilih pasien terlebih dahulu.");return;}
        final javax.swing.table.DefaultTableModel m=new javax.swing.table.DefaultTableModel(new Object[]{"Pilih","Kode","Nama Obat","Dosis/Sediaan","Aturan Pakai","Rute","Kelompok"},0){
            @Override public Class<?> getColumnClass(int c){return c==0?Boolean.class:String.class;}
            @Override public boolean isCellEditable(int r,int c){return c==0||c>=3;}
        };
        String q="SELECT dpo.kode_brng,db.nama_brng,MAX(IFNULL(ap.aturan,'')) aturan,MAX(IFNULL(db.kode_sat,'')) satuan FROM detail_pemberian_obat dpo INNER JOIN databarang db ON db.kode_brng=dpo.kode_brng LEFT JOIN aturan_pakai ap ON ap.no_rawat=dpo.no_rawat AND ap.tgl_perawatan=dpo.tgl_perawatan AND ap.jam=dpo.jam AND ap.kode_brng=dpo.kode_brng WHERE dpo.no_rawat=? GROUP BY dpo.kode_brng,db.nama_brng ORDER BY db.nama_brng";
        try(java.sql.PreparedStatement p=koneksi.prepareStatement(q)){p.setString(1,TNoRw.getText());try(java.sql.ResultSet r=p.executeQuery()){while(r.next())m.addRow(new Object[]{Boolean.FALSE,r.getString("kode_brng"),r.getString("nama_brng"),"",r.getString("aturan"),"Oral","Oral"});}}
        catch(Exception ex){javax.swing.JOptionPane.showMessageDialog(this,"Gagal membaca resep: "+ex.getMessage());return;}
        if(m.getRowCount()==0){javax.swing.JOptionPane.showMessageDialog(this,"Tidak ditemukan obat/resep pada pasien ini.");return;}
        javax.swing.JTable t=new javax.swing.JTable(m);t.setRowHeight(24);t.getColumnModel().getColumn(0).setPreferredWidth(45);t.getColumnModel().getColumn(1).setPreferredWidth(85);t.getColumnModel().getColumn(2).setPreferredWidth(240);
        javax.swing.JScrollPane sc=new javax.swing.JScrollPane(t);sc.setPreferredSize(new java.awt.Dimension(850,360));
        int ok=javax.swing.JOptionPane.showConfirmDialog(this,sc,"Pilih Obat dari Resep — centang obat yang akan dimasukkan",javax.swing.JOptionPane.OK_CANCEL_OPTION,javax.swing.JOptionPane.PLAIN_MESSAGE);
        if(ok!=javax.swing.JOptionPane.OK_OPTION)return;
        int pilih=0;for(int i=0;i<m.getRowCount();i++)if(Boolean.TRUE.equals(m.getValueAt(i,0)))pilih++;
        if(pilih==0){javax.swing.JOptionPane.showMessageDialog(this,"Belum ada obat yang dipilih.");return;}
        String no=pastikanNoSuratKotak();if(no.equals(""))return;int tambah=0,skip=0;
        for(int i=0;i<m.getRowCount();i++){
            if(!Boolean.TRUE.equals(m.getValueAt(i,0)))continue;
            String kode=String.valueOf(m.getValueAt(i,1)), nama=String.valueOf(m.getValueAt(i,2));
            try(java.sql.PreparedStatement cek=koneksi.prepareStatement("SELECT 1 FROM pemberian_obat_ranap_detail WHERE no_surat=? AND kode_brng=? AND status_obat<>'Selesai' LIMIT 1")){cek.setString(1,no);cek.setString(2,kode);try(java.sql.ResultSet rr=cek.executeQuery()){if(rr.next()){skip++;continue;}}}catch(Exception ex){}
            try{
                int ur=1;try(java.sql.PreparedStatement pu=koneksi.prepareStatement("SELECT IFNULL(MAX(urut),0)+1 FROM pemberian_obat_ranap_detail WHERE no_surat=?")){pu.setString(1,no);try(java.sql.ResultSet ru=pu.executeQuery()){if(ru.next())ur=ru.getInt(1);}}
                try(java.sql.PreparedStatement ins=koneksi.prepareStatement("INSERT INTO pemberian_obat_ranap_detail(no_surat,urut,kategori,kode_brng,nama_obat,dosis_sediaan,aturan_pakai,rute,tanggal_mulai,status_obat,user_input,nama_input) VALUES(?,?,?,?,?,?,?,?,?,'Aktif',?,?)")){
                    ins.setString(1,no);ins.setInt(2,ur);ins.setString(3,String.valueOf(m.getValueAt(i,6)));ins.setString(4,kode);ins.setString(5,nama);ins.setString(6,String.valueOf(m.getValueAt(i,3)));ins.setString(7,String.valueOf(m.getValueAt(i,4)));ins.setString(8,String.valueOf(m.getValueAt(i,5)));ins.setDate(9,tanggalKotak());ins.setString(10,kodeLoginV32());ins.setString(11,namaLoginV32());ins.executeUpdate();tambah++;
                }
            }catch(Exception ex){javax.swing.JOptionPane.showMessageDialog(this,"Gagal menambah "+nama+": "+ex.getMessage());}
        }
        muatKotakPemberian();javax.swing.JOptionPane.showMessageDialog(this,tambah+" obat ditambahkan"+(skip>0?", "+skip+" sudah ada dan dilewati.":"."));
    }

    private void tambahObatManualKotak(){
        if(TNoRw.getText().trim().equals("")){javax.swing.JOptionPane.showMessageDialog(this,"Pilih pasien terlebih dahulu.");return;}
        javax.swing.JTextField nama=new javax.swing.JTextField(),dosis=new javax.swing.JTextField(),aturan=new javax.swing.JTextField();
        javax.swing.JComboBox<String> rute=new javax.swing.JComboBox<String>(new String[]{"Oral","IV","IM","SC","Topikal","Inhalasi","Sublingual","Rektal","Lainnya"});
        javax.swing.JComboBox<String> kelompok=new javax.swing.JComboBox<String>(new String[]{"Oral","Parenteral","Topikal","Inhalasi","Lainnya"});
        javax.swing.JPanel p=new javax.swing.JPanel(new java.awt.GridLayout(0,2,6,6));p.add(new javax.swing.JLabel("Nama Obat *"));p.add(nama);p.add(new javax.swing.JLabel("Dosis/Sediaan"));p.add(dosis);p.add(new javax.swing.JLabel("Aturan Pakai"));p.add(aturan);p.add(new javax.swing.JLabel("Rute"));p.add(rute);p.add(new javax.swing.JLabel("Kelompok"));p.add(kelompok);
        if(javax.swing.JOptionPane.showConfirmDialog(this,p,"Tambah Obat Manual",javax.swing.JOptionPane.OK_CANCEL_OPTION,javax.swing.JOptionPane.PLAIN_MESSAGE)!=javax.swing.JOptionPane.OK_OPTION)return;
        if(nama.getText().trim().equals("")){javax.swing.JOptionPane.showMessageDialog(this,"Nama obat wajib diisi.");return;}
        String no=pastikanNoSuratKotak();if(no.equals(""))return;
        try{int ur=1;try(java.sql.PreparedStatement pu=koneksi.prepareStatement("SELECT IFNULL(MAX(urut),0)+1 FROM pemberian_obat_ranap_detail WHERE no_surat=?")){pu.setString(1,no);try(java.sql.ResultSet ru=pu.executeQuery()){if(ru.next())ur=ru.getInt(1);}}
            try(java.sql.PreparedStatement ins=koneksi.prepareStatement("INSERT INTO pemberian_obat_ranap_detail(no_surat,urut,kategori,kode_brng,nama_obat,dosis_sediaan,aturan_pakai,rute,tanggal_mulai,status_obat,user_input,nama_input) VALUES(?,?,?,'',?,?,?,?,?,'Aktif',?,?)")){
                ins.setString(1,no);ins.setInt(2,ur);ins.setString(3,String.valueOf(kelompok.getSelectedItem()));ins.setString(4,nama.getText().trim());ins.setString(5,dosis.getText().trim());ins.setString(6,aturan.getText().trim());ins.setString(7,String.valueOf(rute.getSelectedItem()));ins.setDate(8,tanggalKotak());ins.setString(9,kodeLoginV32());ins.setString(10,namaLoginV32());ins.executeUpdate();
            }muatKotakPemberian();
        }catch(Exception ex){javax.swing.JOptionPane.showMessageDialog(this,"Gagal menambah obat manual: "+ex.getMessage());}
    }

    private void buatJadwalCepatKotak(){
        if(tbKotakPantau==null || tbKotakPantau.getSelectedRow()<0){javax.swing.JOptionPane.showMessageDialog(this,"Pilih baris obat terlebih dahulu.");return;}
        Long id=kotakDetailPerBaris.get(tbKotakPantau.getSelectedRow()); if(id==null)return;
        String jam=javax.swing.JOptionPane.showInputDialog(this,"Jam rencana (HH:mm)","08:00"); if(jam==null||jam.trim().equals(""))return;
        try{java.sql.Time.valueOf(jam.trim()+":00");}catch(Exception ex){javax.swing.JOptionPane.showMessageDialog(this,"Format jam harus HH:mm, contoh 08:00");return;}
        try(java.sql.PreparedStatement p=koneksi.prepareStatement("INSERT INTO pemberian_obat_ranap_jadwal(id_detail,tanggal_pemberian,jam_rencana,status_pemberian) VALUES(?,?,?,'Terjadwal')")){p.setLong(1,id);p.setDate(2,tanggalKotak());p.setTime(3,java.sql.Time.valueOf(jam.trim()+":00"));p.executeUpdate();muatKotakPemberian();}catch(Exception ex){javax.swing.JOptionPane.showMessageDialog(this,"Gagal membuat jadwal: "+ex.getMessage());}
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
        muatKotakPemberian();
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
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from pemberian_obat_ranap where tanggal_catat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
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
