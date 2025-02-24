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
import rekammedis.RMCariEdukasiDokter;
import rekammedis.RMCariEdukasiPerawat;
import rekammedis.RMCariEdukasiFarmasi;
import rekammedis.RMCariEdukasiNutrisionis;
import rekammedis.RMCariEdukasiRehabMedik;


/**
 *
 * @author perpustakaan
 */
public final class RMDataEdukasiRanap extends javax.swing.JDialog {
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
    private RMCariEdukasiDokter cariedukasidokter=new RMCariEdukasiDokter(null,false);
    private RMCariEdukasiPerawat cariedukasiperawat=new RMCariEdukasiPerawat(null,false);
    private RMCariEdukasiFarmasi cariedukasifarmasi=new RMCariEdukasiFarmasi(null,false);
    private RMCariEdukasiNutrisionis cariedukasinutrisionis=new RMCariEdukasiNutrisionis(null,false);
    private RMCariEdukasiRehabMedik cariedukasirehabmedik=new RMCariEdukasiRehabMedik(null,false);
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMDataEdukasiRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","No Surat","Tanggal Surat","Jam Surat","Tanggal Pengkajian","Jam Pengkajian","Kode Pengkaji","Nama Pengkaji",
            "Pengkajian","Pengkajian Lainnya","Bicara","Kemampuan Membaca","Perlu Penerjemah","Bahasa Isyarat","Nilai-nilai Kepercayaan","Tingkat Pengetahuan Tingkat Kesehatan Pasien",
            "Nilai-nilai Pasien Dan Budaya","Merokok","Alkohol","Kesediaan Menerima Informasi","Alasan","Rencana Pendidikan Kesehatan","Jelaskan","Profesi",
            "Kode Edukasi 1","Edukasi 1","Penerima Edukasi 1","Metode Edukasi 1","Frekuensi Edukasi 1","Evaluasi Edukasi 1",
            "Kode Edukasi 2","Edukasi 2","Penerima Edukasi 2","Metode Edukasi 2","Frekuensi Edukasi 2","Evaluasi Edukasi 2",
            "Kode Edukasi 3","Edukasi 3","Penerima Edukasi 3","Metode Edukasi 3","Frekuensi Edukasi 3","Evaluasi Edukasi 3",
            "Kode Edukasi 4","Edukasi 4","Penerima Edukasi 4","Metode Edukasi 4","Frekuensi Edukasi 4","Evaluasi Edukasi 4",
            "Kode Edukasi 5","Edukasi 5","Penerima Edukasi 5","Metode Edukasi 5","Frekuensi Edukasi 5","Evaluasi Edukasi 5",
            "Kode Edukasi 6","Edukasi 6","Penerima Edukasi 6","Metode Edukasi 6","Frekuensi Edukasi 6","Evaluasi Edukasi 6",
            "Kode Edukasi 7","Edukasi 7","Penerima Edukasi 7","Metode Edukasi 7","Frekuensi Edukasi 7","Evaluasi Edukasi 7",
            "Kode Edukasi 8","Edukasi 8","Penerima Edukasi 8","Metode Edukasi 8","Frekuensi Edukasi 8","Evaluasi Edukasi 8",
            "Kode Edukasi 9","Edukasi 9","Penerima Edukasi 9","Metode Edukasi 9","Frekuensi Edukasi 9","Evaluasi Edukasi 9",
            "Kode Edukasi 10","Edukasi 10","Penerima Edukasi 10","Metode Edukasi 10","Frekuensi Edukasi 10","Evaluasi Edukasi 10",
            "Edukasi Lainnya","Kolaborasi Dengan Profesi","Edukasi Kolaborasi","Penerima Edukasi Kolaborasi","Metode Edukasi Kolaborasi","Frekuensi Edukasi Kolaborasi","Evaluasi Edukasi Kolaborasi",
            "Hubungan Dengan Pasien","Kebutuhan Pendapat Kedua","ACC Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 96; i++) {
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
                column.setPreferredWidth(250);
            }else if(i==59){
                column.setPreferredWidth(250);
            }else if(i==60){
                column.setPreferredWidth(250);
            }else if(i==61){
                column.setPreferredWidth(250);
            }else if(i==62){
                column.setPreferredWidth(170);
            }else if(i==63){
                column.setPreferredWidth(75);
            }else if(i==64){
                column.setPreferredWidth(170);
            }else if(i==65){
                column.setPreferredWidth(75);
            }else if(i==66){
                column.setPreferredWidth(170);
            }else if(i==67){
                column.setPreferredWidth(75);
            }else if(i==68){
                column.setPreferredWidth(170);
            }else if(i==69){
                column.setPreferredWidth(75);
            }else if(i==70){
                column.setPreferredWidth(170);
            }else if(i==71){
                column.setPreferredWidth(75);
            }else if(i==72){
                column.setPreferredWidth(170);
            }else if(i==73){
                column.setPreferredWidth(75);
            }else if(i==74){
                column.setPreferredWidth(75);
            }else if(i==75){
                column.setPreferredWidth(75);
            }else if(i==76){
                column.setPreferredWidth(40);
            }else if(i==77){
                column.setPreferredWidth(105);
            }else if(i==78){
                column.setPreferredWidth(65);
            }else if(i==79){
                column.setPreferredWidth(150);
            }else if(i==80){
                column.setPreferredWidth(90);
            }else if(i==81){
                column.setPreferredWidth(150);
            }else if(i==82){
                column.setPreferredWidth(60);
            }else if(i==83){
                column.setPreferredWidth(250);
            }else if(i==84){
                column.setPreferredWidth(250);
            }else if(i==85){
                column.setPreferredWidth(250);
            }else if(i==86){
                column.setPreferredWidth(250);
            }else if(i==87){
                column.setPreferredWidth(170);
            }else if(i==88){
                column.setPreferredWidth(75);
            }else if(i==89){
                column.setPreferredWidth(170);
            }else if(i==90){
                column.setPreferredWidth(75);
            }else if(i==91){
                column.setPreferredWidth(170);
            }else if(i==92){
                column.setPreferredWidth(75);
            }else if(i==93){
                column.setPreferredWidth(170);
            }else if(i==94){
                column.setPreferredWidth(75);
            }else if(i==95){
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("RMDataEdukasi")){
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
        
        cariedukasidokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariedukasidokter.getTable().getSelectedRow()!= -1){ 
                    if(i==1){
                        Kode1.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
                        Edukasi1.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
                    }else if(i==2){
                        Kode2.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
                        Edukasi2.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
                    }else if(i==3){
                        Kode3.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
                        Edukasi3.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
                    }else if(i==4){
                        Kode4.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
                        Edukasi4.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
                    }else if(i==5){
                        Kode5.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
                        Edukasi5.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
                    }else if(i==6){
                        Kode6.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
                        Edukasi6.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
                    }else if(i==7){
                        Kode7.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
                        Edukasi7.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
                    }else if(i==8){
                        Kode8.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
                        Edukasi8.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
                    }else if(i==9){
                        Kode9.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
                        Edukasi9.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
                    }else if(i==10){
                        Kode10.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
                        Edukasi10.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
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
        
        cariedukasiperawat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariedukasiperawat.getTable().getSelectedRow()!= -1){ 
                    if(i==1){
                        Kode1.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
                        Edukasi1.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
                    }else if(i==2){
                        Kode2.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
                        Edukasi2.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
                    }else if(i==3){
                        Kode3.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
                        Edukasi3.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
                    }else if(i==4){
                        Kode4.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
                        Edukasi4.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
                    }else if(i==5){
                        Kode5.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
                        Edukasi5.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
                    }else if(i==6){
                        Kode6.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
                        Edukasi6.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
                    }else if(i==7){
                        Kode7.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
                        Edukasi7.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
                    }else if(i==8){
                        Kode8.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
                        Edukasi8.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
                    }else if(i==9){
                        Kode9.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
                        Edukasi9.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
                    }else if(i==10){
                        Kode10.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
                        Edukasi10.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
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
        
        cariedukasifarmasi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariedukasifarmasi.getTable().getSelectedRow()!= -1){ 
                    if(i==1){
                        Kode1.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
                        Edukasi1.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
                    }else if(i==2){
                        Kode2.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
                        Edukasi2.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
                    }else if(i==3){
                        Kode3.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
                        Edukasi3.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
                    }else if(i==4){
                        Kode4.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
                        Edukasi4.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
                    }else if(i==5){
                        Kode5.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
                        Edukasi5.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
                    }else if(i==6){
                        Kode6.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
                        Edukasi6.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
                    }else if(i==7){
                        Kode7.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
                        Edukasi7.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
                    }else if(i==8){
                        Kode8.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
                        Edukasi8.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
                    }else if(i==9){
                        Kode9.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
                        Edukasi9.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
                    }else if(i==10){
                        Kode10.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
                        Edukasi10.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
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
        
        cariedukasinutrisionis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariedukasinutrisionis.getTable().getSelectedRow()!= -1){ 
                    if(i==1){
                        Kode1.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
                        Edukasi1.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
                    }else if(i==2){
                        Kode2.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
                        Edukasi2.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
                    }else if(i==3){
                        Kode3.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
                        Edukasi3.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
                    }else if(i==4){
                        Kode4.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
                        Edukasi4.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
                    }else if(i==5){
                        Kode5.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
                        Edukasi5.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
                    }else if(i==6){
                        Kode6.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
                        Edukasi6.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
                    }else if(i==7){
                        Kode7.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
                        Edukasi7.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
                    }else if(i==8){
                        Kode8.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
                        Edukasi8.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
                    }else if(i==9){
                        Kode9.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
                        Edukasi9.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
                    }else if(i==10){
                        Kode10.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
                        Edukasi10.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
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
        
        cariedukasirehabmedik.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariedukasirehabmedik.getTable().getSelectedRow()!= -1){ 
                    if(i==1){
                        Kode1.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
                        Edukasi1.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
                    }else if(i==2){
                        Kode2.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
                        Edukasi2.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
                    }else if(i==3){
                        Kode3.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
                        Edukasi3.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
                    }else if(i==4){
                        Kode4.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
                        Edukasi4.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
                    }else if(i==5){
                        Kode5.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
                        Edukasi5.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
                    }else if(i==6){
                        Kode6.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
                        Edukasi6.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
                    }else if(i==7){
                        Kode7.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
                        Edukasi7.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
                    }else if(i==8){
                        Kode8.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
                        Edukasi8.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
                    }else if(i==9){
                        Kode9.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
                        Edukasi9.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
                    }else if(i==10){
                        Kode10.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
                        Edukasi10.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
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
        jLabel5 = new widget.Label();
        label14 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        TanggalSurat = new widget.Tanggal();
        jLabel11 = new widget.Label();
        NoSurat = new widget.TextBox();
        Pengkajian = new widget.ComboBox();
        jLabel24 = new widget.Label();
        Kebutuhan = new widget.TextBox();
        jLabel15 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel27 = new widget.Label();
        accep = new widget.TextBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        jLabel14 = new widget.Label();
        PengkajianLainnya = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel25 = new widget.Label();
        Bicara = new widget.ComboBox();
        Bahasa = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel28 = new widget.Label();
        Membaca = new widget.ComboBox();
        jLabel29 = new widget.Label();
        Penerjemah = new widget.ComboBox();
        jLabel30 = new widget.Label();
        Isyarat = new widget.ComboBox();
        jLabel31 = new widget.Label();
        Pendidikan = new widget.TextBox();
        jLabel32 = new widget.Label();
        Agama = new widget.TextBox();
        jLabel33 = new widget.Label();
        Kepercayaan = new widget.ComboBox();
        jLabel34 = new widget.Label();
        Pengetahuan = new widget.ComboBox();
        jLabel35 = new widget.Label();
        Budaya = new widget.ComboBox();
        jLabel36 = new widget.Label();
        Merokok = new widget.ComboBox();
        jLabel37 = new widget.Label();
        Alkohol = new widget.ComboBox();
        jLabel38 = new widget.Label();
        Informasi = new widget.ComboBox();
        Alasan = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        Rencana = new widget.ComboBox();
        jLabel41 = new widget.Label();
        Jelaskan = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel42 = new widget.Label();
        Profesi = new widget.ComboBox();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        Edukasi1 = new widget.TextBox();
        Kode1 = new widget.TextBox();
        Metode1 = new widget.ComboBox();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        Frekuensi1 = new widget.ComboBox();
        jLabel49 = new widget.Label();
        Evaluasi1 = new widget.ComboBox();
        BtnLainnya1 = new widget.Button();
        BtnDokter1 = new widget.Button();
        BtnPerawat1 = new widget.Button();
        BtnFarmasi1 = new widget.Button();
        BtnNutrisionis1 = new widget.Button();
        jLabel50 = new widget.Label();
        Penerima1 = new widget.ComboBox();
        jLabel51 = new widget.Label();
        Edukasi2 = new widget.TextBox();
        Kode2 = new widget.TextBox();
        Metode2 = new widget.ComboBox();
        Frekuensi2 = new widget.ComboBox();
        Evaluasi2 = new widget.ComboBox();
        BtnLainnya2 = new widget.Button();
        BtnDokter2 = new widget.Button();
        BtnPerawat2 = new widget.Button();
        BtnFarmasi2 = new widget.Button();
        BtnNutrisionis2 = new widget.Button();
        Penerima2 = new widget.ComboBox();
        jLabel52 = new widget.Label();
        Edukasi3 = new widget.TextBox();
        Kode3 = new widget.TextBox();
        Metode3 = new widget.ComboBox();
        Frekuensi3 = new widget.ComboBox();
        Evaluasi3 = new widget.ComboBox();
        BtnLainnya3 = new widget.Button();
        BtnDokter3 = new widget.Button();
        BtnPerawat3 = new widget.Button();
        BtnFarmasi3 = new widget.Button();
        BtnNutrisionis3 = new widget.Button();
        Penerima3 = new widget.ComboBox();
        jLabel53 = new widget.Label();
        Edukasi4 = new widget.TextBox();
        Kode4 = new widget.TextBox();
        Metode4 = new widget.ComboBox();
        Frekuensi4 = new widget.ComboBox();
        Evaluasi4 = new widget.ComboBox();
        BtnLainnya4 = new widget.Button();
        BtnDokter4 = new widget.Button();
        BtnPerawat4 = new widget.Button();
        BtnFarmasi4 = new widget.Button();
        BtnNutrisionis4 = new widget.Button();
        Penerima4 = new widget.ComboBox();
        jLabel54 = new widget.Label();
        Edukasi5 = new widget.TextBox();
        Kode5 = new widget.TextBox();
        Metode5 = new widget.ComboBox();
        Frekuensi5 = new widget.ComboBox();
        Evaluasi5 = new widget.ComboBox();
        BtnLainnya5 = new widget.Button();
        BtnDokter5 = new widget.Button();
        BtnPerawat5 = new widget.Button();
        BtnFarmasi5 = new widget.Button();
        BtnNutrisionis5 = new widget.Button();
        Penerima5 = new widget.ComboBox();
        jLabel55 = new widget.Label();
        Edukasi6 = new widget.TextBox();
        Kode6 = new widget.TextBox();
        Metode6 = new widget.ComboBox();
        Frekuensi6 = new widget.ComboBox();
        Evaluasi6 = new widget.ComboBox();
        BtnLainnya6 = new widget.Button();
        BtnDokter6 = new widget.Button();
        BtnPerawat6 = new widget.Button();
        BtnFarmasi6 = new widget.Button();
        BtnNutrisionis6 = new widget.Button();
        Penerima6 = new widget.ComboBox();
        jLabel56 = new widget.Label();
        Edukasi7 = new widget.TextBox();
        Kode7 = new widget.TextBox();
        Metode7 = new widget.ComboBox();
        Frekuensi7 = new widget.ComboBox();
        Evaluasi7 = new widget.ComboBox();
        BtnLainnya7 = new widget.Button();
        BtnDokter7 = new widget.Button();
        BtnPerawat7 = new widget.Button();
        BtnFarmasi7 = new widget.Button();
        BtnNutrisionis7 = new widget.Button();
        Penerima7 = new widget.ComboBox();
        jLabel57 = new widget.Label();
        Edukasi8 = new widget.TextBox();
        Kode8 = new widget.TextBox();
        Metode8 = new widget.ComboBox();
        Frekuensi8 = new widget.ComboBox();
        Evaluasi8 = new widget.ComboBox();
        BtnLainnya8 = new widget.Button();
        BtnDokter8 = new widget.Button();
        BtnPerawat8 = new widget.Button();
        BtnFarmasi8 = new widget.Button();
        BtnNutrisionis8 = new widget.Button();
        Penerima8 = new widget.ComboBox();
        jLabel58 = new widget.Label();
        Edukasi9 = new widget.TextBox();
        Kode9 = new widget.TextBox();
        Metode9 = new widget.ComboBox();
        Frekuensi9 = new widget.ComboBox();
        Evaluasi9 = new widget.ComboBox();
        BtnLainnya9 = new widget.Button();
        BtnDokter9 = new widget.Button();
        BtnPerawat9 = new widget.Button();
        BtnFarmasi9 = new widget.Button();
        BtnNutrisionis9 = new widget.Button();
        Penerima9 = new widget.ComboBox();
        jLabel59 = new widget.Label();
        Edukasi10 = new widget.TextBox();
        Kode10 = new widget.TextBox();
        Metode10 = new widget.ComboBox();
        Frekuensi10 = new widget.ComboBox();
        Evaluasi10 = new widget.ComboBox();
        BtnLainnya10 = new widget.Button();
        BtnDokter10 = new widget.Button();
        BtnPerawat10 = new widget.Button();
        BtnFarmasi10 = new widget.Button();
        BtnNutrisionis10 = new widget.Button();
        Penerima10 = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        EdukasiLainnya = new widget.TextArea();
        jLabel60 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel61 = new widget.Label();
        Kode11 = new widget.TextBox();
        Edukasi11 = new widget.TextBox();
        Penerima11 = new widget.ComboBox();
        Metode11 = new widget.ComboBox();
        Frekuensi11 = new widget.ComboBox();
        Evaluasi11 = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Edukasi Pasien/Keluarga Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica Neue", 0, 13), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-01-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-01-2024" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1430));
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

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 100, 23);

        label14.setText("Pengkaji :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(10, 40, 90, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setName("KodeDokter"); // NOI18N
        KodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterKeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(107, 40, 141, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        NamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(250, 40, 270, 23);

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
        BtnDokter.setBounds(520, 40, 28, 23);

        jLabel12.setText("Kebutuhan Pendapat Kedua :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(665, 80, 150, 23);

        jLabel13.setText("Tanggal Surat :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(630, 40, 90, 23);

        TanggalSurat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-01-2024" }));
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

        Pengkajian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Bahasa", "Pendengaran", "Masalah Penglihatan", "Bicara", "Cemas", "Emosi", "Motivasi Buruk", "Hilang Memori", "Kognitif", "Tidak Ada Partisipasi", "Secara Fisiologi Tidak Mampu Belajar", "Tidak Ditemukan Hambatan Belajar", "Lain-lain" }));
        Pengkajian.setName("Pengkajian"); // NOI18N
        Pengkajian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PengkajianActionPerformed(evt);
            }
        });
        Pengkajian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengkajianKeyPressed(evt);
            }
        });
        FormInput.add(Pengkajian);
        Pengkajian.setBounds(190, 80, 460, 23);

        jLabel24.setText("Pengkajian Hambatan :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(0, 80, 180, 23);

        Kebutuhan.setEditable(false);
        Kebutuhan.setHighlighter(null);
        Kebutuhan.setName("Kebutuhan"); // NOI18N
        Kebutuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebutuhanKeyPressed(evt);
            }
        });
        FormInput.add(Kebutuhan);
        Kebutuhan.setBounds(830, 80, 110, 23);

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

        jLabel27.setText("Saya Sudah Membaca, Mengerti dan Menyetujui “Edukasi Pasien”  ");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(670, 110, 330, 20);

        accep.setEditable(false);
        accep.setHighlighter(null);
        accep.setName("accep"); // NOI18N
        accep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                accepKeyPressed(evt);
            }
        });
        FormInput.add(accep);
        accep.setBounds(1010, 110, 140, 24);

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
        FormPhoto.setBounds(930, 150, 340, 280);

        jLabel14.setText("Pengkajian Hambatan Lainnya :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 110, 180, 23);

        PengkajianLainnya.setHighlighter(null);
        PengkajianLainnya.setName("PengkajianLainnya"); // NOI18N
        PengkajianLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengkajianLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(PengkajianLainnya);
        PengkajianLainnya.setBounds(190, 110, 460, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel25.setText("Bahasa Sehari-hari :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(340, 140, 110, 23);

        Bicara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Gangguan Bicara" }));
        Bicara.setName("Bicara"); // NOI18N
        Bicara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BicaraActionPerformed(evt);
            }
        });
        Bicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BicaraKeyPressed(evt);
            }
        });
        FormInput.add(Bicara);
        Bicara.setBounds(190, 140, 150, 23);

        Bahasa.setEditable(false);
        Bahasa.setFocusTraversalPolicyProvider(true);
        Bahasa.setName("Bahasa"); // NOI18N
        Bahasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BahasaKeyPressed(evt);
            }
        });
        FormInput.add(Bahasa);
        Bahasa.setBounds(460, 140, 190, 23);

        jLabel26.setText("Bicara :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 140, 180, 23);

        jLabel28.setText("Kemampuan Membaca :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 170, 180, 23);

        Membaca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mampu", "Tidak Mampu" }));
        Membaca.setName("Membaca"); // NOI18N
        Membaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MembacaActionPerformed(evt);
            }
        });
        Membaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MembacaKeyPressed(evt);
            }
        });
        FormInput.add(Membaca);
        Membaca.setBounds(190, 170, 150, 23);

        jLabel29.setText("Perlu Penerjemah :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(340, 170, 110, 23);

        Penerjemah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Penerjemah.setName("Penerjemah"); // NOI18N
        Penerjemah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PenerjemahActionPerformed(evt);
            }
        });
        Penerjemah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenerjemahKeyPressed(evt);
            }
        });
        FormInput.add(Penerjemah);
        Penerjemah.setBounds(460, 170, 60, 23);

        jLabel30.setText("Bahasa Isyarat :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(520, 170, 90, 23);

        Isyarat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Isyarat.setName("Isyarat"); // NOI18N
        Isyarat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IsyaratActionPerformed(evt);
            }
        });
        Isyarat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IsyaratKeyPressed(evt);
            }
        });
        FormInput.add(Isyarat);
        Isyarat.setBounds(620, 170, 60, 23);

        jLabel31.setText("Pendidikan :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(70, 200, 110, 23);

        Pendidikan.setEditable(false);
        Pendidikan.setFocusTraversalPolicyProvider(true);
        Pendidikan.setName("Pendidikan"); // NOI18N
        Pendidikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendidikanKeyPressed(evt);
            }
        });
        FormInput.add(Pendidikan);
        Pendidikan.setBounds(190, 200, 190, 23);

        jLabel32.setText("Agama :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(380, 200, 60, 23);

        Agama.setEditable(false);
        Agama.setFocusTraversalPolicyProvider(true);
        Agama.setName("Agama"); // NOI18N
        Agama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgamaKeyPressed(evt);
            }
        });
        FormInput.add(Agama);
        Agama.setBounds(450, 200, 190, 23);

        jLabel33.setText("Nilai-nilai Kepercayaan :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 230, 180, 23);

        Kepercayaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Tidak mau dilakukan transfusi", "Tidak mau pulang dihari tertentu", "Tidak mau imunisasi", "Tidak boleh menyusui (ASI)", "Tidak memakan daging/ikan yang bersisik", "Lain-lain" }));
        Kepercayaan.setName("Kepercayaan"); // NOI18N
        Kepercayaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KepercayaanActionPerformed(evt);
            }
        });
        Kepercayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepercayaanKeyPressed(evt);
            }
        });
        FormInput.add(Kepercayaan);
        Kepercayaan.setBounds(190, 230, 460, 23);

        jLabel34.setText("Tingkat Pengetahuan Tingkat Kesehatan Pasien :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 260, 270, 23);

        Pengetahuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Paham", "Kurang Paham", "Tidak Paham" }));
        Pengetahuan.setName("Pengetahuan"); // NOI18N
        Pengetahuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PengetahuanActionPerformed(evt);
            }
        });
        Pengetahuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengetahuanKeyPressed(evt);
            }
        });
        FormInput.add(Pengetahuan);
        Pengetahuan.setBounds(280, 260, 130, 23);

        jLabel35.setText("Nilai-nilai Pasien Dan Budaya :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(420, 260, 170, 23);

        Budaya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Modern", "Moderat" }));
        Budaya.setName("Budaya"); // NOI18N
        Budaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BudayaActionPerformed(evt);
            }
        });
        Budaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BudayaKeyPressed(evt);
            }
        });
        FormInput.add(Budaya);
        Budaya.setBounds(600, 260, 130, 23);

        jLabel36.setText("Merokok :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 290, 180, 23);

        Merokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Merokok.setName("Merokok"); // NOI18N
        Merokok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MerokokActionPerformed(evt);
            }
        });
        Merokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MerokokKeyPressed(evt);
            }
        });
        FormInput.add(Merokok);
        Merokok.setBounds(190, 290, 130, 23);

        jLabel37.setText("Alasan :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(320, 320, 70, 23);

        Alkohol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Alkohol.setName("Alkohol"); // NOI18N
        Alkohol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlkoholActionPerformed(evt);
            }
        });
        Alkohol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlkoholKeyPressed(evt);
            }
        });
        FormInput.add(Alkohol);
        Alkohol.setBounds(400, 290, 130, 23);

        jLabel38.setText("Kesediaan Menerima Informasi :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 320, 180, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Informasi.setName("Informasi"); // NOI18N
        Informasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InformasiActionPerformed(evt);
            }
        });
        Informasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiKeyPressed(evt);
            }
        });
        FormInput.add(Informasi);
        Informasi.setBounds(190, 320, 130, 23);

        Alasan.setFocusTraversalPolicyProvider(true);
        Alasan.setName("Alasan"); // NOI18N
        Alasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanKeyPressed(evt);
            }
        });
        FormInput.add(Alasan);
        Alasan.setBounds(400, 320, 190, 23);

        jLabel39.setText("Alkohol :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(320, 290, 70, 23);

        jLabel40.setText("Rencana Pendidikan Kesehatan :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 350, 180, 23);

        Rencana.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Proses Penyakit", "Nutrisi", "Edukasi Kolaborasi", "Pengobatan/Tindakan", "Terapi/Obat", "Alat Bantu Medik", "Lain-lain" }));
        Rencana.setName("Rencana"); // NOI18N
        Rencana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RencanaActionPerformed(evt);
            }
        });
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        FormInput.add(Rencana);
        Rencana.setBounds(190, 350, 190, 23);

        jLabel41.setText("Jelaskan :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(380, 350, 70, 23);

        Jelaskan.setFocusTraversalPolicyProvider(true);
        Jelaskan.setName("Jelaskan"); // NOI18N
        Jelaskan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JelaskanKeyPressed(evt);
            }
        });
        FormInput.add(Jelaskan);
        Jelaskan.setBounds(460, 350, 270, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 390, 880, 1);

        jLabel42.setText("INPUT EDUKASI KOLABORASI");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 1310, 170, 23);

        Profesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Dokter", "Perawat/Bidan", "Farmasi", "Nutrisionis", "Rehab Medik", "Lainnya" }));
        Profesi.setName("Profesi"); // NOI18N
        Profesi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ProfesiItemStateChanged(evt);
            }
        });
        Profesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProfesiActionPerformed(evt);
            }
        });
        Profesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProfesiKeyPressed(evt);
            }
        });
        FormInput.add(Profesi);
        Profesi.setBounds(120, 430, 190, 23);

        jLabel43.setText("METODE YANG DISUKAI");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(770, 460, 120, 23);

        jLabel44.setText("Edukasi 1 :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 490, 110, 23);

        Edukasi1.setEditable(false);
        Edukasi1.setFocusTraversalPolicyProvider(true);
        Edukasi1.setName("Edukasi1"); // NOI18N
        Edukasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi1KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi1);
        Edukasi1.setBounds(220, 490, 400, 23);

        Kode1.setEditable(false);
        Kode1.setFocusTraversalPolicyProvider(true);
        Kode1.setName("Kode1"); // NOI18N
        Kode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode1KeyPressed(evt);
            }
        });
        FormInput.add(Kode1);
        Kode1.setBounds(120, 490, 100, 23);

        Metode1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode1.setName("Metode1"); // NOI18N
        Metode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode1ActionPerformed(evt);
            }
        });
        Metode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode1KeyPressed(evt);
            }
        });
        FormInput.add(Metode1);
        Metode1.setBounds(770, 490, 130, 23);

        jLabel45.setText("Profesi :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(40, 430, 70, 23);

        jLabel46.setText("KODE");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(120, 460, 60, 23);

        jLabel47.setText("EDUKASI");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(370, 460, 60, 23);

        jLabel48.setText("FREKUENSI EDUKASI");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(970, 460, 110, 23);

        Frekuensi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi1.setName("Frekuensi1"); // NOI18N
        Frekuensi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi1ActionPerformed(evt);
            }
        });
        Frekuensi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi1KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi1);
        Frekuensi1.setBounds(910, 490, 240, 23);

        jLabel49.setText("EVALUASI RESPON");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(1240, 460, 100, 23);

        Evaluasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi1.setName("Evaluasi1"); // NOI18N
        Evaluasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi1ActionPerformed(evt);
            }
        });
        Evaluasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi1KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi1);
        Evaluasi1.setBounds(1160, 490, 260, 23);

        BtnLainnya1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale2.png"))); // NOI18N
        BtnLainnya1.setMnemonic('G');
        BtnLainnya1.setText("Lainnya");
        BtnLainnya1.setToolTipText("Alt+G");
        BtnLainnya1.setName("BtnLainnya1"); // NOI18N
        BtnLainnya1.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnLainnya1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLainnya1ActionPerformed(evt);
            }
        });
        BtnLainnya1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLainnya1KeyPressed(evt);
            }
        });
        FormInput.add(BtnLainnya1);
        BtnLainnya1.setBounds(120, 520, 120, 30);

        BtnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/UserSetup.png"))); // NOI18N
        BtnDokter1.setMnemonic('G');
        BtnDokter1.setText("Dokter");
        BtnDokter1.setToolTipText("Alt+G");
        BtnDokter1.setName("BtnDokter1"); // NOI18N
        BtnDokter1.setPreferredSize(new java.awt.Dimension(120, 30));
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
        BtnDokter1.setBounds(120, 520, 120, 30);

        BtnPerawat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnPerawat1.setMnemonic('G');
        BtnPerawat1.setText("Perawat/Bidan");
        BtnPerawat1.setToolTipText("Alt+G");
        BtnPerawat1.setName("BtnPerawat1"); // NOI18N
        BtnPerawat1.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPerawat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawat1ActionPerformed(evt);
            }
        });
        BtnPerawat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawat1KeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat1);
        BtnPerawat1.setBounds(120, 520, 150, 30);

        BtnFarmasi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/pills-5-32x32.png"))); // NOI18N
        BtnFarmasi1.setMnemonic('G');
        BtnFarmasi1.setText("Farmasi");
        BtnFarmasi1.setToolTipText("Alt+G");
        BtnFarmasi1.setName("BtnFarmasi1"); // NOI18N
        BtnFarmasi1.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnFarmasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFarmasi1ActionPerformed(evt);
            }
        });
        BtnFarmasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFarmasi1KeyPressed(evt);
            }
        });
        FormInput.add(BtnFarmasi1);
        BtnFarmasi1.setBounds(120, 520, 120, 30);

        BtnNutrisionis1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/breakfast24.png"))); // NOI18N
        BtnNutrisionis1.setMnemonic('G');
        BtnNutrisionis1.setText("Nutrisionis");
        BtnNutrisionis1.setToolTipText("Alt+G");
        BtnNutrisionis1.setName("BtnNutrisionis1"); // NOI18N
        BtnNutrisionis1.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnNutrisionis1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNutrisionis1ActionPerformed(evt);
            }
        });
        BtnNutrisionis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNutrisionis1KeyPressed(evt);
            }
        });
        FormInput.add(BtnNutrisionis1);
        BtnNutrisionis1.setBounds(120, 520, 120, 30);

        jLabel50.setText("PENERIMA EDUKASI");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(625, 460, 120, 23);

        Penerima1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima1.setName("Penerima1"); // NOI18N
        Penerima1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima1ActionPerformed(evt);
            }
        });
        Penerima1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima1KeyPressed(evt);
            }
        });
        FormInput.add(Penerima1);
        Penerima1.setBounds(630, 490, 130, 23);

        jLabel51.setText("Edukasi 2 :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 560, 110, 23);

        Edukasi2.setEditable(false);
        Edukasi2.setFocusTraversalPolicyProvider(true);
        Edukasi2.setName("Edukasi2"); // NOI18N
        Edukasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi2KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi2);
        Edukasi2.setBounds(220, 560, 400, 23);

        Kode2.setEditable(false);
        Kode2.setFocusTraversalPolicyProvider(true);
        Kode2.setName("Kode2"); // NOI18N
        Kode2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode2KeyPressed(evt);
            }
        });
        FormInput.add(Kode2);
        Kode2.setBounds(120, 560, 100, 23);

        Metode2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode2.setName("Metode2"); // NOI18N
        Metode2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode2ActionPerformed(evt);
            }
        });
        Metode2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode2KeyPressed(evt);
            }
        });
        FormInput.add(Metode2);
        Metode2.setBounds(770, 560, 130, 23);

        Frekuensi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi2.setName("Frekuensi2"); // NOI18N
        Frekuensi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi2ActionPerformed(evt);
            }
        });
        Frekuensi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi2KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi2);
        Frekuensi2.setBounds(910, 560, 240, 23);

        Evaluasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi2.setName("Evaluasi2"); // NOI18N
        Evaluasi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi2ActionPerformed(evt);
            }
        });
        Evaluasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi2KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi2);
        Evaluasi2.setBounds(1160, 560, 260, 23);

        BtnLainnya2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale2.png"))); // NOI18N
        BtnLainnya2.setMnemonic('G');
        BtnLainnya2.setText("Lainnya");
        BtnLainnya2.setToolTipText("Alt+G");
        BtnLainnya2.setName("BtnLainnya2"); // NOI18N
        BtnLainnya2.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnLainnya2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLainnya2ActionPerformed(evt);
            }
        });
        BtnLainnya2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLainnya2KeyPressed(evt);
            }
        });
        FormInput.add(BtnLainnya2);
        BtnLainnya2.setBounds(120, 590, 120, 30);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/UserSetup.png"))); // NOI18N
        BtnDokter2.setMnemonic('G');
        BtnDokter2.setText("Dokter");
        BtnDokter2.setToolTipText("Alt+G");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.setPreferredSize(new java.awt.Dimension(120, 30));
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
        BtnDokter2.setBounds(120, 590, 120, 30);

        BtnPerawat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnPerawat2.setMnemonic('G');
        BtnPerawat2.setText("Perawat/Bidan");
        BtnPerawat2.setToolTipText("Alt+G");
        BtnPerawat2.setName("BtnPerawat2"); // NOI18N
        BtnPerawat2.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPerawat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawat2ActionPerformed(evt);
            }
        });
        BtnPerawat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawat2KeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat2);
        BtnPerawat2.setBounds(120, 590, 150, 30);

        BtnFarmasi2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/pills-5-32x32.png"))); // NOI18N
        BtnFarmasi2.setMnemonic('G');
        BtnFarmasi2.setText("Farmasi");
        BtnFarmasi2.setToolTipText("Alt+G");
        BtnFarmasi2.setName("BtnFarmasi2"); // NOI18N
        BtnFarmasi2.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnFarmasi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFarmasi2ActionPerformed(evt);
            }
        });
        BtnFarmasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFarmasi2KeyPressed(evt);
            }
        });
        FormInput.add(BtnFarmasi2);
        BtnFarmasi2.setBounds(120, 590, 120, 30);

        BtnNutrisionis2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/breakfast24.png"))); // NOI18N
        BtnNutrisionis2.setMnemonic('G');
        BtnNutrisionis2.setText("Nutrisionis");
        BtnNutrisionis2.setToolTipText("Alt+G");
        BtnNutrisionis2.setName("BtnNutrisionis2"); // NOI18N
        BtnNutrisionis2.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnNutrisionis2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNutrisionis2ActionPerformed(evt);
            }
        });
        BtnNutrisionis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNutrisionis2KeyPressed(evt);
            }
        });
        FormInput.add(BtnNutrisionis2);
        BtnNutrisionis2.setBounds(120, 590, 120, 30);

        Penerima2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima2.setName("Penerima2"); // NOI18N
        Penerima2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima2ActionPerformed(evt);
            }
        });
        Penerima2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima2KeyPressed(evt);
            }
        });
        FormInput.add(Penerima2);
        Penerima2.setBounds(630, 560, 130, 23);

        jLabel52.setText("Edukasi 3 :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(0, 630, 110, 23);

        Edukasi3.setEditable(false);
        Edukasi3.setFocusTraversalPolicyProvider(true);
        Edukasi3.setName("Edukasi3"); // NOI18N
        Edukasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi3KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi3);
        Edukasi3.setBounds(220, 630, 400, 23);

        Kode3.setEditable(false);
        Kode3.setFocusTraversalPolicyProvider(true);
        Kode3.setName("Kode3"); // NOI18N
        Kode3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode3KeyPressed(evt);
            }
        });
        FormInput.add(Kode3);
        Kode3.setBounds(120, 630, 100, 23);

        Metode3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode3.setName("Metode3"); // NOI18N
        Metode3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode3ActionPerformed(evt);
            }
        });
        Metode3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode3KeyPressed(evt);
            }
        });
        FormInput.add(Metode3);
        Metode3.setBounds(770, 630, 130, 23);

        Frekuensi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi3.setName("Frekuensi3"); // NOI18N
        Frekuensi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi3ActionPerformed(evt);
            }
        });
        Frekuensi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi3KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi3);
        Frekuensi3.setBounds(910, 630, 240, 23);

        Evaluasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi3.setName("Evaluasi3"); // NOI18N
        Evaluasi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi3ActionPerformed(evt);
            }
        });
        Evaluasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi3KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi3);
        Evaluasi3.setBounds(1160, 630, 260, 23);

        BtnLainnya3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale2.png"))); // NOI18N
        BtnLainnya3.setMnemonic('G');
        BtnLainnya3.setText("Lainnya");
        BtnLainnya3.setToolTipText("Alt+G");
        BtnLainnya3.setName("BtnLainnya3"); // NOI18N
        BtnLainnya3.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnLainnya3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLainnya3ActionPerformed(evt);
            }
        });
        BtnLainnya3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLainnya3KeyPressed(evt);
            }
        });
        FormInput.add(BtnLainnya3);
        BtnLainnya3.setBounds(120, 660, 120, 30);

        BtnDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/UserSetup.png"))); // NOI18N
        BtnDokter3.setMnemonic('G');
        BtnDokter3.setText("Dokter");
        BtnDokter3.setToolTipText("Alt+G");
        BtnDokter3.setName("BtnDokter3"); // NOI18N
        BtnDokter3.setPreferredSize(new java.awt.Dimension(120, 30));
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
        BtnDokter3.setBounds(120, 660, 120, 30);

        BtnPerawat3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnPerawat3.setMnemonic('G');
        BtnPerawat3.setText("Perawat/Bidan");
        BtnPerawat3.setToolTipText("Alt+G");
        BtnPerawat3.setName("BtnPerawat3"); // NOI18N
        BtnPerawat3.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPerawat3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawat3ActionPerformed(evt);
            }
        });
        BtnPerawat3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawat3KeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat3);
        BtnPerawat3.setBounds(120, 660, 150, 30);

        BtnFarmasi3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/pills-5-32x32.png"))); // NOI18N
        BtnFarmasi3.setMnemonic('G');
        BtnFarmasi3.setText("Farmasi");
        BtnFarmasi3.setToolTipText("Alt+G");
        BtnFarmasi3.setName("BtnFarmasi3"); // NOI18N
        BtnFarmasi3.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnFarmasi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFarmasi3ActionPerformed(evt);
            }
        });
        BtnFarmasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFarmasi3KeyPressed(evt);
            }
        });
        FormInput.add(BtnFarmasi3);
        BtnFarmasi3.setBounds(120, 660, 120, 30);

        BtnNutrisionis3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/breakfast24.png"))); // NOI18N
        BtnNutrisionis3.setMnemonic('G');
        BtnNutrisionis3.setText("Nutrisionis");
        BtnNutrisionis3.setToolTipText("Alt+G");
        BtnNutrisionis3.setName("BtnNutrisionis3"); // NOI18N
        BtnNutrisionis3.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnNutrisionis3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNutrisionis3ActionPerformed(evt);
            }
        });
        BtnNutrisionis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNutrisionis3KeyPressed(evt);
            }
        });
        FormInput.add(BtnNutrisionis3);
        BtnNutrisionis3.setBounds(120, 660, 120, 30);

        Penerima3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima3.setName("Penerima3"); // NOI18N
        Penerima3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima3ActionPerformed(evt);
            }
        });
        Penerima3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima3KeyPressed(evt);
            }
        });
        FormInput.add(Penerima3);
        Penerima3.setBounds(630, 630, 130, 23);

        jLabel53.setText("Edukasi 4 :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 700, 110, 23);

        Edukasi4.setEditable(false);
        Edukasi4.setFocusTraversalPolicyProvider(true);
        Edukasi4.setName("Edukasi4"); // NOI18N
        Edukasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi4KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi4);
        Edukasi4.setBounds(220, 700, 400, 23);

        Kode4.setEditable(false);
        Kode4.setFocusTraversalPolicyProvider(true);
        Kode4.setName("Kode4"); // NOI18N
        Kode4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode4KeyPressed(evt);
            }
        });
        FormInput.add(Kode4);
        Kode4.setBounds(120, 700, 100, 23);

        Metode4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode4.setName("Metode4"); // NOI18N
        Metode4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode4ActionPerformed(evt);
            }
        });
        Metode4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode4KeyPressed(evt);
            }
        });
        FormInput.add(Metode4);
        Metode4.setBounds(770, 700, 130, 23);

        Frekuensi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi4.setName("Frekuensi4"); // NOI18N
        Frekuensi4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi4ActionPerformed(evt);
            }
        });
        Frekuensi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi4KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi4);
        Frekuensi4.setBounds(910, 700, 240, 23);

        Evaluasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi4.setName("Evaluasi4"); // NOI18N
        Evaluasi4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi4ActionPerformed(evt);
            }
        });
        Evaluasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi4KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi4);
        Evaluasi4.setBounds(1160, 700, 260, 23);

        BtnLainnya4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale2.png"))); // NOI18N
        BtnLainnya4.setMnemonic('G');
        BtnLainnya4.setText("Lainnya");
        BtnLainnya4.setToolTipText("Alt+G");
        BtnLainnya4.setName("BtnLainnya4"); // NOI18N
        BtnLainnya4.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnLainnya4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLainnya4ActionPerformed(evt);
            }
        });
        BtnLainnya4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLainnya4KeyPressed(evt);
            }
        });
        FormInput.add(BtnLainnya4);
        BtnLainnya4.setBounds(120, 730, 120, 30);

        BtnDokter4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/UserSetup.png"))); // NOI18N
        BtnDokter4.setMnemonic('G');
        BtnDokter4.setText("Dokter");
        BtnDokter4.setToolTipText("Alt+G");
        BtnDokter4.setName("BtnDokter4"); // NOI18N
        BtnDokter4.setPreferredSize(new java.awt.Dimension(120, 30));
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
        FormInput.add(BtnDokter4);
        BtnDokter4.setBounds(120, 730, 120, 30);

        BtnPerawat4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnPerawat4.setMnemonic('G');
        BtnPerawat4.setText("Perawat/Bidan");
        BtnPerawat4.setToolTipText("Alt+G");
        BtnPerawat4.setName("BtnPerawat4"); // NOI18N
        BtnPerawat4.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPerawat4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawat4ActionPerformed(evt);
            }
        });
        BtnPerawat4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawat4KeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat4);
        BtnPerawat4.setBounds(120, 730, 150, 30);

        BtnFarmasi4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/pills-5-32x32.png"))); // NOI18N
        BtnFarmasi4.setMnemonic('G');
        BtnFarmasi4.setText("Farmasi");
        BtnFarmasi4.setToolTipText("Alt+G");
        BtnFarmasi4.setName("BtnFarmasi4"); // NOI18N
        BtnFarmasi4.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnFarmasi4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFarmasi4ActionPerformed(evt);
            }
        });
        BtnFarmasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFarmasi4KeyPressed(evt);
            }
        });
        FormInput.add(BtnFarmasi4);
        BtnFarmasi4.setBounds(120, 730, 120, 30);

        BtnNutrisionis4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/breakfast24.png"))); // NOI18N
        BtnNutrisionis4.setMnemonic('G');
        BtnNutrisionis4.setText("Nutrisionis");
        BtnNutrisionis4.setToolTipText("Alt+G");
        BtnNutrisionis4.setName("BtnNutrisionis4"); // NOI18N
        BtnNutrisionis4.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnNutrisionis4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNutrisionis4ActionPerformed(evt);
            }
        });
        BtnNutrisionis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNutrisionis4KeyPressed(evt);
            }
        });
        FormInput.add(BtnNutrisionis4);
        BtnNutrisionis4.setBounds(120, 730, 120, 30);

        Penerima4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima4.setName("Penerima4"); // NOI18N
        Penerima4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima4ActionPerformed(evt);
            }
        });
        Penerima4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima4KeyPressed(evt);
            }
        });
        FormInput.add(Penerima4);
        Penerima4.setBounds(630, 700, 130, 23);

        jLabel54.setText("Edukasi 5 :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(0, 770, 110, 23);

        Edukasi5.setEditable(false);
        Edukasi5.setFocusTraversalPolicyProvider(true);
        Edukasi5.setName("Edukasi5"); // NOI18N
        Edukasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi5KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi5);
        Edukasi5.setBounds(220, 770, 400, 23);

        Kode5.setEditable(false);
        Kode5.setFocusTraversalPolicyProvider(true);
        Kode5.setName("Kode5"); // NOI18N
        Kode5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode5KeyPressed(evt);
            }
        });
        FormInput.add(Kode5);
        Kode5.setBounds(120, 770, 100, 23);

        Metode5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode5.setName("Metode5"); // NOI18N
        Metode5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode5ActionPerformed(evt);
            }
        });
        Metode5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode5KeyPressed(evt);
            }
        });
        FormInput.add(Metode5);
        Metode5.setBounds(770, 770, 130, 23);

        Frekuensi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi5.setName("Frekuensi5"); // NOI18N
        Frekuensi5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi5ActionPerformed(evt);
            }
        });
        Frekuensi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi5KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi5);
        Frekuensi5.setBounds(910, 770, 240, 23);

        Evaluasi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi5.setName("Evaluasi5"); // NOI18N
        Evaluasi5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi5ActionPerformed(evt);
            }
        });
        Evaluasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi5KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi5);
        Evaluasi5.setBounds(1160, 770, 260, 23);

        BtnLainnya5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale2.png"))); // NOI18N
        BtnLainnya5.setMnemonic('G');
        BtnLainnya5.setText("Lainnya");
        BtnLainnya5.setToolTipText("Alt+G");
        BtnLainnya5.setName("BtnLainnya5"); // NOI18N
        BtnLainnya5.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnLainnya5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLainnya5ActionPerformed(evt);
            }
        });
        BtnLainnya5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLainnya5KeyPressed(evt);
            }
        });
        FormInput.add(BtnLainnya5);
        BtnLainnya5.setBounds(120, 800, 120, 30);

        BtnDokter5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/UserSetup.png"))); // NOI18N
        BtnDokter5.setMnemonic('G');
        BtnDokter5.setText("Dokter");
        BtnDokter5.setToolTipText("Alt+G");
        BtnDokter5.setName("BtnDokter5"); // NOI18N
        BtnDokter5.setPreferredSize(new java.awt.Dimension(120, 30));
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
        FormInput.add(BtnDokter5);
        BtnDokter5.setBounds(120, 800, 120, 30);

        BtnPerawat5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnPerawat5.setMnemonic('G');
        BtnPerawat5.setText("Perawat/Bidan");
        BtnPerawat5.setToolTipText("Alt+G");
        BtnPerawat5.setName("BtnPerawat5"); // NOI18N
        BtnPerawat5.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPerawat5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawat5ActionPerformed(evt);
            }
        });
        BtnPerawat5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawat5KeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat5);
        BtnPerawat5.setBounds(120, 800, 150, 30);

        BtnFarmasi5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/pills-5-32x32.png"))); // NOI18N
        BtnFarmasi5.setMnemonic('G');
        BtnFarmasi5.setText("Farmasi");
        BtnFarmasi5.setToolTipText("Alt+G");
        BtnFarmasi5.setName("BtnFarmasi5"); // NOI18N
        BtnFarmasi5.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnFarmasi5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFarmasi5ActionPerformed(evt);
            }
        });
        BtnFarmasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFarmasi5KeyPressed(evt);
            }
        });
        FormInput.add(BtnFarmasi5);
        BtnFarmasi5.setBounds(120, 800, 120, 30);

        BtnNutrisionis5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/breakfast24.png"))); // NOI18N
        BtnNutrisionis5.setMnemonic('G');
        BtnNutrisionis5.setText("Nutrisionis");
        BtnNutrisionis5.setToolTipText("Alt+G");
        BtnNutrisionis5.setName("BtnNutrisionis5"); // NOI18N
        BtnNutrisionis5.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnNutrisionis5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNutrisionis5ActionPerformed(evt);
            }
        });
        BtnNutrisionis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNutrisionis5KeyPressed(evt);
            }
        });
        FormInput.add(BtnNutrisionis5);
        BtnNutrisionis5.setBounds(120, 800, 120, 30);

        Penerima5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima5.setName("Penerima5"); // NOI18N
        Penerima5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima5ActionPerformed(evt);
            }
        });
        Penerima5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima5KeyPressed(evt);
            }
        });
        FormInput.add(Penerima5);
        Penerima5.setBounds(630, 770, 130, 23);

        jLabel55.setText("Edukasi 6 :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 840, 110, 23);

        Edukasi6.setEditable(false);
        Edukasi6.setFocusTraversalPolicyProvider(true);
        Edukasi6.setName("Edukasi6"); // NOI18N
        Edukasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi6KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi6);
        Edukasi6.setBounds(220, 840, 400, 23);

        Kode6.setEditable(false);
        Kode6.setFocusTraversalPolicyProvider(true);
        Kode6.setName("Kode6"); // NOI18N
        Kode6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode6KeyPressed(evt);
            }
        });
        FormInput.add(Kode6);
        Kode6.setBounds(120, 840, 100, 23);

        Metode6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode6.setName("Metode6"); // NOI18N
        Metode6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode6ActionPerformed(evt);
            }
        });
        Metode6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode6KeyPressed(evt);
            }
        });
        FormInput.add(Metode6);
        Metode6.setBounds(770, 840, 130, 23);

        Frekuensi6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi6.setName("Frekuensi6"); // NOI18N
        Frekuensi6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi6ActionPerformed(evt);
            }
        });
        Frekuensi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi6KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi6);
        Frekuensi6.setBounds(910, 840, 240, 23);

        Evaluasi6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi6.setName("Evaluasi6"); // NOI18N
        Evaluasi6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi6ActionPerformed(evt);
            }
        });
        Evaluasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi6KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi6);
        Evaluasi6.setBounds(1160, 840, 260, 23);

        BtnLainnya6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale2.png"))); // NOI18N
        BtnLainnya6.setMnemonic('G');
        BtnLainnya6.setText("Lainnya");
        BtnLainnya6.setToolTipText("Alt+G");
        BtnLainnya6.setName("BtnLainnya6"); // NOI18N
        BtnLainnya6.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnLainnya6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLainnya6ActionPerformed(evt);
            }
        });
        BtnLainnya6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLainnya6KeyPressed(evt);
            }
        });
        FormInput.add(BtnLainnya6);
        BtnLainnya6.setBounds(120, 870, 120, 30);

        BtnDokter6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/UserSetup.png"))); // NOI18N
        BtnDokter6.setMnemonic('G');
        BtnDokter6.setText("Dokter");
        BtnDokter6.setToolTipText("Alt+G");
        BtnDokter6.setName("BtnDokter6"); // NOI18N
        BtnDokter6.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnDokter6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter6ActionPerformed(evt);
            }
        });
        BtnDokter6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter6KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter6);
        BtnDokter6.setBounds(120, 870, 120, 30);

        BtnPerawat6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnPerawat6.setMnemonic('G');
        BtnPerawat6.setText("Perawat/Bidan");
        BtnPerawat6.setToolTipText("Alt+G");
        BtnPerawat6.setName("BtnPerawat6"); // NOI18N
        BtnPerawat6.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPerawat6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawat6ActionPerformed(evt);
            }
        });
        BtnPerawat6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawat6KeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat6);
        BtnPerawat6.setBounds(120, 870, 150, 30);

        BtnFarmasi6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/pills-5-32x32.png"))); // NOI18N
        BtnFarmasi6.setMnemonic('G');
        BtnFarmasi6.setText("Farmasi");
        BtnFarmasi6.setToolTipText("Alt+G");
        BtnFarmasi6.setName("BtnFarmasi6"); // NOI18N
        BtnFarmasi6.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnFarmasi6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFarmasi6ActionPerformed(evt);
            }
        });
        BtnFarmasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFarmasi6KeyPressed(evt);
            }
        });
        FormInput.add(BtnFarmasi6);
        BtnFarmasi6.setBounds(120, 870, 120, 30);

        BtnNutrisionis6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/breakfast24.png"))); // NOI18N
        BtnNutrisionis6.setMnemonic('G');
        BtnNutrisionis6.setText("Nutrisionis");
        BtnNutrisionis6.setToolTipText("Alt+G");
        BtnNutrisionis6.setName("BtnNutrisionis6"); // NOI18N
        BtnNutrisionis6.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnNutrisionis6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNutrisionis6ActionPerformed(evt);
            }
        });
        BtnNutrisionis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNutrisionis6KeyPressed(evt);
            }
        });
        FormInput.add(BtnNutrisionis6);
        BtnNutrisionis6.setBounds(120, 870, 120, 30);

        Penerima6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima6.setName("Penerima6"); // NOI18N
        Penerima6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima6ActionPerformed(evt);
            }
        });
        Penerima6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima6KeyPressed(evt);
            }
        });
        FormInput.add(Penerima6);
        Penerima6.setBounds(630, 840, 130, 23);

        jLabel56.setText("Edukasi 7 :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(0, 910, 110, 23);

        Edukasi7.setEditable(false);
        Edukasi7.setFocusTraversalPolicyProvider(true);
        Edukasi7.setName("Edukasi7"); // NOI18N
        Edukasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi7KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi7);
        Edukasi7.setBounds(220, 910, 400, 23);

        Kode7.setEditable(false);
        Kode7.setFocusTraversalPolicyProvider(true);
        Kode7.setName("Kode7"); // NOI18N
        Kode7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode7KeyPressed(evt);
            }
        });
        FormInput.add(Kode7);
        Kode7.setBounds(120, 910, 100, 23);

        Metode7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode7.setName("Metode7"); // NOI18N
        Metode7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode7ActionPerformed(evt);
            }
        });
        Metode7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode7KeyPressed(evt);
            }
        });
        FormInput.add(Metode7);
        Metode7.setBounds(770, 910, 130, 23);

        Frekuensi7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi7.setName("Frekuensi7"); // NOI18N
        Frekuensi7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi7ActionPerformed(evt);
            }
        });
        Frekuensi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi7KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi7);
        Frekuensi7.setBounds(910, 910, 240, 23);

        Evaluasi7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi7.setName("Evaluasi7"); // NOI18N
        Evaluasi7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi7ActionPerformed(evt);
            }
        });
        Evaluasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi7KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi7);
        Evaluasi7.setBounds(1160, 910, 260, 23);

        BtnLainnya7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale2.png"))); // NOI18N
        BtnLainnya7.setMnemonic('G');
        BtnLainnya7.setText("Lainnya");
        BtnLainnya7.setToolTipText("Alt+G");
        BtnLainnya7.setName("BtnLainnya7"); // NOI18N
        BtnLainnya7.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnLainnya7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLainnya7ActionPerformed(evt);
            }
        });
        BtnLainnya7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLainnya7KeyPressed(evt);
            }
        });
        FormInput.add(BtnLainnya7);
        BtnLainnya7.setBounds(120, 940, 120, 30);

        BtnDokter7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/UserSetup.png"))); // NOI18N
        BtnDokter7.setMnemonic('G');
        BtnDokter7.setText("Dokter");
        BtnDokter7.setToolTipText("Alt+G");
        BtnDokter7.setName("BtnDokter7"); // NOI18N
        BtnDokter7.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnDokter7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter7ActionPerformed(evt);
            }
        });
        BtnDokter7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter7KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter7);
        BtnDokter7.setBounds(120, 940, 120, 30);

        BtnPerawat7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnPerawat7.setMnemonic('G');
        BtnPerawat7.setText("Perawat/Bidan");
        BtnPerawat7.setToolTipText("Alt+G");
        BtnPerawat7.setName("BtnPerawat7"); // NOI18N
        BtnPerawat7.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPerawat7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawat7ActionPerformed(evt);
            }
        });
        BtnPerawat7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawat7KeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat7);
        BtnPerawat7.setBounds(120, 940, 150, 30);

        BtnFarmasi7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/pills-5-32x32.png"))); // NOI18N
        BtnFarmasi7.setMnemonic('G');
        BtnFarmasi7.setText("Farmasi");
        BtnFarmasi7.setToolTipText("Alt+G");
        BtnFarmasi7.setName("BtnFarmasi7"); // NOI18N
        BtnFarmasi7.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnFarmasi7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFarmasi7ActionPerformed(evt);
            }
        });
        BtnFarmasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFarmasi7KeyPressed(evt);
            }
        });
        FormInput.add(BtnFarmasi7);
        BtnFarmasi7.setBounds(120, 940, 120, 30);

        BtnNutrisionis7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/breakfast24.png"))); // NOI18N
        BtnNutrisionis7.setMnemonic('G');
        BtnNutrisionis7.setText("Nutrisionis");
        BtnNutrisionis7.setToolTipText("Alt+G");
        BtnNutrisionis7.setName("BtnNutrisionis7"); // NOI18N
        BtnNutrisionis7.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnNutrisionis7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNutrisionis7ActionPerformed(evt);
            }
        });
        BtnNutrisionis7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNutrisionis7KeyPressed(evt);
            }
        });
        FormInput.add(BtnNutrisionis7);
        BtnNutrisionis7.setBounds(120, 940, 120, 30);

        Penerima7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima7.setName("Penerima7"); // NOI18N
        Penerima7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima7ActionPerformed(evt);
            }
        });
        Penerima7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima7KeyPressed(evt);
            }
        });
        FormInput.add(Penerima7);
        Penerima7.setBounds(630, 910, 130, 23);

        jLabel57.setText("Edukasi 8 :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 980, 110, 23);

        Edukasi8.setEditable(false);
        Edukasi8.setFocusTraversalPolicyProvider(true);
        Edukasi8.setName("Edukasi8"); // NOI18N
        Edukasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi8KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi8);
        Edukasi8.setBounds(220, 980, 400, 23);

        Kode8.setEditable(false);
        Kode8.setFocusTraversalPolicyProvider(true);
        Kode8.setName("Kode8"); // NOI18N
        Kode8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode8KeyPressed(evt);
            }
        });
        FormInput.add(Kode8);
        Kode8.setBounds(120, 980, 100, 23);

        Metode8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode8.setName("Metode8"); // NOI18N
        Metode8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode8ActionPerformed(evt);
            }
        });
        Metode8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode8KeyPressed(evt);
            }
        });
        FormInput.add(Metode8);
        Metode8.setBounds(770, 980, 130, 23);

        Frekuensi8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi8.setName("Frekuensi8"); // NOI18N
        Frekuensi8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi8ActionPerformed(evt);
            }
        });
        Frekuensi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi8KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi8);
        Frekuensi8.setBounds(910, 980, 240, 23);

        Evaluasi8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi8.setName("Evaluasi8"); // NOI18N
        Evaluasi8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi8ActionPerformed(evt);
            }
        });
        Evaluasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi8KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi8);
        Evaluasi8.setBounds(1160, 980, 260, 23);

        BtnLainnya8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale2.png"))); // NOI18N
        BtnLainnya8.setMnemonic('G');
        BtnLainnya8.setText("Lainnya");
        BtnLainnya8.setToolTipText("Alt+G");
        BtnLainnya8.setName("BtnLainnya8"); // NOI18N
        BtnLainnya8.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnLainnya8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLainnya8ActionPerformed(evt);
            }
        });
        BtnLainnya8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLainnya8KeyPressed(evt);
            }
        });
        FormInput.add(BtnLainnya8);
        BtnLainnya8.setBounds(120, 1010, 120, 30);

        BtnDokter8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/UserSetup.png"))); // NOI18N
        BtnDokter8.setMnemonic('G');
        BtnDokter8.setText("Dokter");
        BtnDokter8.setToolTipText("Alt+G");
        BtnDokter8.setName("BtnDokter8"); // NOI18N
        BtnDokter8.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnDokter8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter8ActionPerformed(evt);
            }
        });
        BtnDokter8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter8KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter8);
        BtnDokter8.setBounds(120, 1010, 120, 30);

        BtnPerawat8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnPerawat8.setMnemonic('G');
        BtnPerawat8.setText("Perawat/Bidan");
        BtnPerawat8.setToolTipText("Alt+G");
        BtnPerawat8.setName("BtnPerawat8"); // NOI18N
        BtnPerawat8.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPerawat8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawat8ActionPerformed(evt);
            }
        });
        BtnPerawat8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawat8KeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat8);
        BtnPerawat8.setBounds(120, 1010, 150, 30);

        BtnFarmasi8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/pills-5-32x32.png"))); // NOI18N
        BtnFarmasi8.setMnemonic('G');
        BtnFarmasi8.setText("Farmasi");
        BtnFarmasi8.setToolTipText("Alt+G");
        BtnFarmasi8.setName("BtnFarmasi8"); // NOI18N
        BtnFarmasi8.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnFarmasi8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFarmasi8ActionPerformed(evt);
            }
        });
        BtnFarmasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFarmasi8KeyPressed(evt);
            }
        });
        FormInput.add(BtnFarmasi8);
        BtnFarmasi8.setBounds(120, 1010, 120, 30);

        BtnNutrisionis8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/breakfast24.png"))); // NOI18N
        BtnNutrisionis8.setMnemonic('G');
        BtnNutrisionis8.setText("Nutrisionis");
        BtnNutrisionis8.setToolTipText("Alt+G");
        BtnNutrisionis8.setName("BtnNutrisionis8"); // NOI18N
        BtnNutrisionis8.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnNutrisionis8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNutrisionis8ActionPerformed(evt);
            }
        });
        BtnNutrisionis8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNutrisionis8KeyPressed(evt);
            }
        });
        FormInput.add(BtnNutrisionis8);
        BtnNutrisionis8.setBounds(120, 1010, 120, 30);

        Penerima8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima8.setName("Penerima8"); // NOI18N
        Penerima8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima8ActionPerformed(evt);
            }
        });
        Penerima8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima8KeyPressed(evt);
            }
        });
        FormInput.add(Penerima8);
        Penerima8.setBounds(630, 980, 130, 23);

        jLabel58.setText("Edukasi 9 :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 1050, 110, 23);

        Edukasi9.setEditable(false);
        Edukasi9.setFocusTraversalPolicyProvider(true);
        Edukasi9.setName("Edukasi9"); // NOI18N
        Edukasi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi9KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi9);
        Edukasi9.setBounds(220, 1050, 400, 23);

        Kode9.setEditable(false);
        Kode9.setFocusTraversalPolicyProvider(true);
        Kode9.setName("Kode9"); // NOI18N
        Kode9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode9KeyPressed(evt);
            }
        });
        FormInput.add(Kode9);
        Kode9.setBounds(120, 1050, 100, 23);

        Metode9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode9.setName("Metode9"); // NOI18N
        Metode9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode9ActionPerformed(evt);
            }
        });
        Metode9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode9KeyPressed(evt);
            }
        });
        FormInput.add(Metode9);
        Metode9.setBounds(770, 1050, 130, 23);

        Frekuensi9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi9.setName("Frekuensi9"); // NOI18N
        Frekuensi9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi9ActionPerformed(evt);
            }
        });
        Frekuensi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi9KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi9);
        Frekuensi9.setBounds(910, 1050, 240, 23);

        Evaluasi9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi9.setName("Evaluasi9"); // NOI18N
        Evaluasi9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi9ActionPerformed(evt);
            }
        });
        Evaluasi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi9KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi9);
        Evaluasi9.setBounds(1160, 1050, 260, 23);

        BtnLainnya9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale2.png"))); // NOI18N
        BtnLainnya9.setMnemonic('G');
        BtnLainnya9.setText("Lainnya");
        BtnLainnya9.setToolTipText("Alt+G");
        BtnLainnya9.setName("BtnLainnya9"); // NOI18N
        BtnLainnya9.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnLainnya9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLainnya9ActionPerformed(evt);
            }
        });
        BtnLainnya9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLainnya9KeyPressed(evt);
            }
        });
        FormInput.add(BtnLainnya9);
        BtnLainnya9.setBounds(120, 1080, 120, 30);

        BtnDokter9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/UserSetup.png"))); // NOI18N
        BtnDokter9.setMnemonic('G');
        BtnDokter9.setText("Dokter");
        BtnDokter9.setToolTipText("Alt+G");
        BtnDokter9.setName("BtnDokter9"); // NOI18N
        BtnDokter9.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnDokter9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter9ActionPerformed(evt);
            }
        });
        BtnDokter9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter9KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter9);
        BtnDokter9.setBounds(120, 1080, 120, 30);

        BtnPerawat9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnPerawat9.setMnemonic('G');
        BtnPerawat9.setText("Perawat/Bidan");
        BtnPerawat9.setToolTipText("Alt+G");
        BtnPerawat9.setName("BtnPerawat9"); // NOI18N
        BtnPerawat9.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPerawat9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawat9ActionPerformed(evt);
            }
        });
        BtnPerawat9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawat9KeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat9);
        BtnPerawat9.setBounds(120, 1080, 150, 30);

        BtnFarmasi9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/pills-5-32x32.png"))); // NOI18N
        BtnFarmasi9.setMnemonic('G');
        BtnFarmasi9.setText("Farmasi");
        BtnFarmasi9.setToolTipText("Alt+G");
        BtnFarmasi9.setName("BtnFarmasi9"); // NOI18N
        BtnFarmasi9.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnFarmasi9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFarmasi9ActionPerformed(evt);
            }
        });
        BtnFarmasi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFarmasi9KeyPressed(evt);
            }
        });
        FormInput.add(BtnFarmasi9);
        BtnFarmasi9.setBounds(120, 1080, 120, 30);

        BtnNutrisionis9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/breakfast24.png"))); // NOI18N
        BtnNutrisionis9.setMnemonic('G');
        BtnNutrisionis9.setText("Nutrisionis");
        BtnNutrisionis9.setToolTipText("Alt+G");
        BtnNutrisionis9.setName("BtnNutrisionis9"); // NOI18N
        BtnNutrisionis9.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnNutrisionis9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNutrisionis9ActionPerformed(evt);
            }
        });
        BtnNutrisionis9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNutrisionis9KeyPressed(evt);
            }
        });
        FormInput.add(BtnNutrisionis9);
        BtnNutrisionis9.setBounds(120, 1080, 120, 30);

        Penerima9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima9.setName("Penerima9"); // NOI18N
        Penerima9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima9ActionPerformed(evt);
            }
        });
        Penerima9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima9KeyPressed(evt);
            }
        });
        FormInput.add(Penerima9);
        Penerima9.setBounds(630, 1050, 130, 23);

        jLabel59.setText("Edukasi Lainnya :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 1190, 110, 23);

        Edukasi10.setEditable(false);
        Edukasi10.setFocusTraversalPolicyProvider(true);
        Edukasi10.setName("Edukasi10"); // NOI18N
        Edukasi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi10KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi10);
        Edukasi10.setBounds(220, 1120, 400, 23);

        Kode10.setEditable(false);
        Kode10.setFocusTraversalPolicyProvider(true);
        Kode10.setName("Kode10"); // NOI18N
        Kode10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode10KeyPressed(evt);
            }
        });
        FormInput.add(Kode10);
        Kode10.setBounds(120, 1120, 100, 23);

        Metode10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode10.setName("Metode10"); // NOI18N
        Metode10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode10ActionPerformed(evt);
            }
        });
        Metode10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode10KeyPressed(evt);
            }
        });
        FormInput.add(Metode10);
        Metode10.setBounds(770, 1120, 130, 23);

        Frekuensi10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi10.setName("Frekuensi10"); // NOI18N
        Frekuensi10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi10ActionPerformed(evt);
            }
        });
        Frekuensi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi10KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi10);
        Frekuensi10.setBounds(910, 1120, 240, 23);

        Evaluasi10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi10.setName("Evaluasi10"); // NOI18N
        Evaluasi10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi10ActionPerformed(evt);
            }
        });
        Evaluasi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi10KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi10);
        Evaluasi10.setBounds(1160, 1120, 260, 23);

        BtnLainnya10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale2.png"))); // NOI18N
        BtnLainnya10.setMnemonic('G');
        BtnLainnya10.setText("Lainnya");
        BtnLainnya10.setToolTipText("Alt+G");
        BtnLainnya10.setName("BtnLainnya10"); // NOI18N
        BtnLainnya10.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnLainnya10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLainnya10ActionPerformed(evt);
            }
        });
        BtnLainnya10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLainnya10KeyPressed(evt);
            }
        });
        FormInput.add(BtnLainnya10);
        BtnLainnya10.setBounds(120, 1150, 120, 30);

        BtnDokter10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/UserSetup.png"))); // NOI18N
        BtnDokter10.setMnemonic('G');
        BtnDokter10.setText("Dokter");
        BtnDokter10.setToolTipText("Alt+G");
        BtnDokter10.setName("BtnDokter10"); // NOI18N
        BtnDokter10.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnDokter10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter10ActionPerformed(evt);
            }
        });
        BtnDokter10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter10KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter10);
        BtnDokter10.setBounds(120, 1150, 120, 30);

        BtnPerawat10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnPerawat10.setMnemonic('G');
        BtnPerawat10.setText("Perawat/Bidan");
        BtnPerawat10.setToolTipText("Alt+G");
        BtnPerawat10.setName("BtnPerawat10"); // NOI18N
        BtnPerawat10.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPerawat10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawat10ActionPerformed(evt);
            }
        });
        BtnPerawat10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawat10KeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat10);
        BtnPerawat10.setBounds(120, 1150, 150, 30);

        BtnFarmasi10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/pills-5-32x32.png"))); // NOI18N
        BtnFarmasi10.setMnemonic('G');
        BtnFarmasi10.setText("Farmasi");
        BtnFarmasi10.setToolTipText("Alt+G");
        BtnFarmasi10.setName("BtnFarmasi10"); // NOI18N
        BtnFarmasi10.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnFarmasi10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFarmasi10ActionPerformed(evt);
            }
        });
        BtnFarmasi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFarmasi10KeyPressed(evt);
            }
        });
        FormInput.add(BtnFarmasi10);
        BtnFarmasi10.setBounds(120, 1150, 120, 30);

        BtnNutrisionis10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/breakfast24.png"))); // NOI18N
        BtnNutrisionis10.setMnemonic('G');
        BtnNutrisionis10.setText("Nutrisionis");
        BtnNutrisionis10.setToolTipText("Alt+G");
        BtnNutrisionis10.setName("BtnNutrisionis10"); // NOI18N
        BtnNutrisionis10.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnNutrisionis10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNutrisionis10ActionPerformed(evt);
            }
        });
        BtnNutrisionis10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNutrisionis10KeyPressed(evt);
            }
        });
        FormInput.add(BtnNutrisionis10);
        BtnNutrisionis10.setBounds(120, 1150, 120, 30);

        Penerima10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima10.setName("Penerima10"); // NOI18N
        Penerima10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima10ActionPerformed(evt);
            }
        });
        Penerima10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima10KeyPressed(evt);
            }
        });
        FormInput.add(Penerima10);
        Penerima10.setBounds(630, 1120, 130, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        EdukasiLainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        EdukasiLainnya.setColumns(20);
        EdukasiLainnya.setRows(5);
        EdukasiLainnya.setName("EdukasiLainnya"); // NOI18N
        EdukasiLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiLainnyaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(EdukasiLainnya);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(130, 1190, 500, 90);

        jLabel60.setText("Edukasi 10 :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(0, 1120, 110, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 1300, 880, 1);

        jLabel61.setText("INPUT EDUKASI");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 400, 120, 23);

        Kode11.setFocusTraversalPolicyProvider(true);
        Kode11.setName("Kode11"); // NOI18N
        Kode11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kode11KeyPressed(evt);
            }
        });
        FormInput.add(Kode11);
        Kode11.setBounds(20, 1380, 200, 23);

        Edukasi11.setFocusTraversalPolicyProvider(true);
        Edukasi11.setName("Edukasi11"); // NOI18N
        Edukasi11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi11KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi11);
        Edukasi11.setBounds(220, 1380, 400, 23);

        Penerima11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pasien", "Keluarga", "Lain-lain" }));
        Penerima11.setName("Penerima11"); // NOI18N
        Penerima11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Penerima11ActionPerformed(evt);
            }
        });
        Penerima11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penerima11KeyPressed(evt);
            }
        });
        FormInput.add(Penerima11);
        Penerima11.setBounds(630, 1380, 130, 23);

        Metode11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Audio", "Demonstrasi", "Lisan", "Tulisan", "Visual" }));
        Metode11.setName("Metode11"); // NOI18N
        Metode11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Metode11ActionPerformed(evt);
            }
        });
        Metode11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metode11KeyPressed(evt);
            }
        });
        FormInput.add(Metode11);
        Metode11.setBounds(770, 1380, 130, 23);

        Frekuensi11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Edukasi Pertama", "Re-edukasi ke 2/3 atau lebih" }));
        Frekuensi11.setName("Frekuensi11"); // NOI18N
        Frekuensi11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Frekuensi11ActionPerformed(evt);
            }
        });
        Frekuensi11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Frekuensi11KeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi11);
        Frekuensi11.setBounds(910, 1380, 240, 23);

        Evaluasi11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Mengerti", "Menyatakan Paham", "Mampu Menjelaskan", "Mampu Demonstrasi/Simulasi" }));
        Evaluasi11.setName("Evaluasi11"); // NOI18N
        Evaluasi11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evaluasi11ActionPerformed(evt);
            }
        });
        Evaluasi11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi11KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi11);
        Evaluasi11.setBounds(1160, 1380, 260, 23);

        jLabel63.setText("KOLABORASI DENGAN PROFESI");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(30, 1350, 170, 23);

        jLabel64.setText("EDUKASI");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(380, 1350, 60, 23);

        jLabel65.setText("PENERIMA EDUKASI");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(625, 1350, 120, 23);

        jLabel66.setText("METODE YANG DISUKAI");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(770, 1350, 120, 23);

        jLabel67.setText("FREKUENSI EDUKASI");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(980, 1350, 110, 23);

        jLabel68.setText("EVALUASI RESPON");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(1250, 1350, 100, 23);

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
            Valid.textKosong(BtnDokter,"Nama Pengkaji");
        }else if(Kode1.getText().equals("")){
            Valid.textKosong(Kode1,"Kode Edukasi 1");
        }else if(Edukasi1.getText().equals("")){
            Valid.textKosong(Edukasi1,"Edukasi 1");
        }else{
            if(Sequel.menyimpantf("edukasi_pasien_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Surat",94,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),
                    KodeDokter.getText(),Pengkajian.getSelectedItem().toString(),PengkajianLainnya.getText(),Bicara.getSelectedItem().toString(),Membaca.getSelectedItem().toString(),
                    Penerjemah.getSelectedItem().toString(),Isyarat.getSelectedItem().toString(),Kepercayaan.getSelectedItem().toString(),Pengetahuan.getSelectedItem().toString(),Budaya.getSelectedItem().toString(),Merokok.getSelectedItem().toString(),
                    Alkohol.getSelectedItem().toString(),Informasi.getSelectedItem().toString(),Alasan.getText(),Rencana.getSelectedItem().toString(),Jelaskan.getText(),Profesi.getSelectedItem().toString(),
                    Kode1.getText(),Edukasi1.getText(),Penerima1.getSelectedItem().toString(),Metode1.getSelectedItem().toString(),Frekuensi1.getSelectedItem().toString(),Evaluasi1.getSelectedItem().toString(),
                    Kode2.getText(),Edukasi2.getText(),Penerima2.getSelectedItem().toString(),Metode2.getSelectedItem().toString(),Frekuensi2.getSelectedItem().toString(),Evaluasi2.getSelectedItem().toString(),
                    Kode3.getText(),Edukasi3.getText(),Penerima3.getSelectedItem().toString(),Metode3.getSelectedItem().toString(),Frekuensi3.getSelectedItem().toString(),Evaluasi3.getSelectedItem().toString(),
                    Kode4.getText(),Edukasi4.getText(),Penerima4.getSelectedItem().toString(),Metode4.getSelectedItem().toString(),Frekuensi4.getSelectedItem().toString(),Evaluasi4.getSelectedItem().toString(),
                    Kode5.getText(),Edukasi5.getText(),Penerima5.getSelectedItem().toString(),Metode5.getSelectedItem().toString(),Frekuensi5.getSelectedItem().toString(),Evaluasi5.getSelectedItem().toString(),
                    Kode6.getText(),Edukasi6.getText(),Penerima6.getSelectedItem().toString(),Metode6.getSelectedItem().toString(),Frekuensi6.getSelectedItem().toString(),Evaluasi6.getSelectedItem().toString(),
                    Kode7.getText(),Edukasi7.getText(),Penerima7.getSelectedItem().toString(),Metode7.getSelectedItem().toString(),Frekuensi7.getSelectedItem().toString(),Evaluasi7.getSelectedItem().toString(),
                    Kode8.getText(),Edukasi8.getText(),Penerima8.getSelectedItem().toString(),Metode8.getSelectedItem().toString(),Frekuensi8.getSelectedItem().toString(),Evaluasi8.getSelectedItem().toString(),
                    Kode9.getText(),Edukasi9.getText(),Penerima9.getSelectedItem().toString(),Metode9.getSelectedItem().toString(),Frekuensi9.getSelectedItem().toString(),Evaluasi9.getSelectedItem().toString(),
                    Kode10.getText(),Edukasi10.getText(),Penerima10.getSelectedItem().toString(),Metode10.getSelectedItem().toString(),Frekuensi10.getSelectedItem().toString(),Evaluasi10.getSelectedItem().toString(),
                    EdukasiLainnya.getText(),Kode11.getText(),Edukasi11.getText(),Penerima11.getSelectedItem().toString(),Metode11.getSelectedItem().toString(),Frekuensi11.getSelectedItem().toString(),Evaluasi11.getSelectedItem().toString(),
                    "-","-","-",""
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
            Valid.pindah(evt,PengkajianLainnya,BtnBatal);
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
            if(Sequel.queryu2tf("delete from edukasi_pasien_ranap where no_surat=?",1,new String[]{
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
        }else if(Kode1.getText().equals("")){
            Valid.textKosong(Kode1,"Kode Edukasi 1");
        }else if(Edukasi1.getText().equals("")){
            Valid.textKosong(Edukasi1,"Edukasi 1");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("edukasi_pasien_ranap","no_surat=?","no_surat=?,no_rawat=?,tanggal_surat=?,jam=?,tanggal_pengkajian=?,jam_pengkajian=?,nik=?,pengkajian=?,pengkajian_lainnya=?,bicara=?,membaca=?,penerjemah=?,isyarat=?,kepercayaan=?,pengetahuan=?,budaya=?,merokok=?,alkohol=?,informasi=?,alasan=?,rencana=?,jelaskan=?,profesi=?,kd_edukasi1=?,edukasi1=?,penerima1=?,metode1=?,frekuensi1=?,evaluasi1=?,kd_edukasi2=?,edukasi2=?,penerima2=?,metode2=?,frekuensi2=?,evaluasi2=?,kd_edukasi3=?,edukasi3=?,penerima3=?,metode3=?,frekuensi3=?,evaluasi3=?,kd_edukasi4=?,edukasi4=?,penerima4=?,metode4=?,frekuensi4=?,evaluasi4=?,kd_edukasi5=?,edukasi5=?,penerima5=?,metode5=?,frekuensi5=?,evaluasi5=?,kd_edukasi6=?,edukasi6=?,penerima6=?,metode6=?,frekuensi6=?,evaluasi6=?,kd_edukasi7=?,edukasi7=?,penerima7=?,metode7=?,frekuensi7=?,evaluasi7=?,kd_edukasi8=?,edukasi8=?,penerima8=?,metode8=?,frekuensi8=?,evaluasi8=?,kd_edukasi9=?,edukasi9=?,penerima9=?,metode9=?,frekuensi9=?,evaluasi9=?,kd_edukasi10=?,edukasi10=?,penerima10=?,metode10=?,frekuensi10=?,evaluasi10=?,edukasi_lainnya=?,kolaborasi=?,edukasi_kolaborasi=?,penerima11=?,metode11=?,frekuensi11=?,evaluasi11=?,hubungan=?,kedua=?,acc_ep=?",94,new String[]{
                        NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),
                        KodeDokter.getText(),Pengkajian.getSelectedItem().toString(),PengkajianLainnya.getText(),Bicara.getSelectedItem().toString(),Membaca.getSelectedItem().toString(),
                        Penerjemah.getSelectedItem().toString(),Isyarat.getSelectedItem().toString(),Kepercayaan.getSelectedItem().toString(),Pengetahuan.getSelectedItem().toString(),Budaya.getSelectedItem().toString(),Merokok.getSelectedItem().toString(),
                        Alkohol.getSelectedItem().toString(),Informasi.getSelectedItem().toString(),Alasan.getText(),Rencana.getSelectedItem().toString(),Jelaskan.getText(),Profesi.getSelectedItem().toString(),
                        Kode1.getText(),Edukasi1.getText(),Penerima1.getSelectedItem().toString(),Metode1.getSelectedItem().toString(),Frekuensi1.getSelectedItem().toString(),Evaluasi1.getSelectedItem().toString(),
                        Kode2.getText(),Edukasi2.getText(),Penerima2.getSelectedItem().toString(),Metode2.getSelectedItem().toString(),Frekuensi2.getSelectedItem().toString(),Evaluasi2.getSelectedItem().toString(),
                        Kode3.getText(),Edukasi3.getText(),Penerima3.getSelectedItem().toString(),Metode3.getSelectedItem().toString(),Frekuensi3.getSelectedItem().toString(),Evaluasi3.getSelectedItem().toString(),
                        Kode4.getText(),Edukasi4.getText(),Penerima4.getSelectedItem().toString(),Metode4.getSelectedItem().toString(),Frekuensi4.getSelectedItem().toString(),Evaluasi4.getSelectedItem().toString(),
                        Kode5.getText(),Edukasi5.getText(),Penerima5.getSelectedItem().toString(),Metode5.getSelectedItem().toString(),Frekuensi5.getSelectedItem().toString(),Evaluasi5.getSelectedItem().toString(),
                        Kode6.getText(),Edukasi6.getText(),Penerima6.getSelectedItem().toString(),Metode6.getSelectedItem().toString(),Frekuensi6.getSelectedItem().toString(),Evaluasi6.getSelectedItem().toString(),
                        Kode7.getText(),Edukasi7.getText(),Penerima7.getSelectedItem().toString(),Metode7.getSelectedItem().toString(),Frekuensi7.getSelectedItem().toString(),Evaluasi7.getSelectedItem().toString(),
                        Kode8.getText(),Edukasi8.getText(),Penerima8.getSelectedItem().toString(),Metode8.getSelectedItem().toString(),Frekuensi8.getSelectedItem().toString(),Evaluasi8.getSelectedItem().toString(),
                        Kode9.getText(),Edukasi9.getText(),Penerima9.getSelectedItem().toString(),Metode9.getSelectedItem().toString(),Frekuensi9.getSelectedItem().toString(),Evaluasi9.getSelectedItem().toString(),
                        Kode10.getText(),Edukasi10.getText(),Penerima10.getSelectedItem().toString(),Metode10.getSelectedItem().toString(),Frekuensi10.getSelectedItem().toString(),Evaluasi10.getSelectedItem().toString(),
                        EdukasiLainnya.getText(),Kode11.getText(),Edukasi11.getText(),Penerima11.getSelectedItem().toString(),Metode11.getSelectedItem().toString(),Frekuensi11.getSelectedItem().toString(),Evaluasi11.getSelectedItem().toString(),
                        Hubungan.getText(),Kebutuhan.getText(),accep.getText(),
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
//            Valid.MyReport("rptLaporanICRajal.jasper","report","::[ Informed Consent Rawat Jalan ]::",param);
//        }
    }//GEN-LAST:event_MnLaporanResumeActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalSuratActionPerformed

    private void TanggalSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSuratKeyPressed
        Valid.pindah(evt,NoSurat,Kebutuhan);
    }//GEN-LAST:event_TanggalSuratKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,TCari,Kebutuhan);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void PengkajianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengkajianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengkajianActionPerformed

    private void PengkajianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengkajianKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengkajianKeyPressed

    private void KebutuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebutuhanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebutuhanKeyPressed

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
        Valid.pindah(evt,TCari,Kebutuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        akses.setform("RMDataEdukasi");
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void KodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterKeyPressed
        Valid.pindah(evt,TCari,Kebutuhan);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void PengkajianLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengkajianLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengkajianLainnyaKeyPressed

    private void BicaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BicaraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BicaraActionPerformed

    private void BicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BicaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BicaraKeyPressed

    private void BahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BahasaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BahasaKeyPressed

    private void MembacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MembacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MembacaActionPerformed

    private void MembacaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MembacaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MembacaKeyPressed

    private void PenerjemahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PenerjemahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenerjemahActionPerformed

    private void PenerjemahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenerjemahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenerjemahKeyPressed

    private void IsyaratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IsyaratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsyaratActionPerformed

    private void IsyaratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IsyaratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsyaratKeyPressed

    private void PendidikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendidikanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendidikanKeyPressed

    private void AgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AgamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgamaKeyPressed

    private void KepercayaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KepercayaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KepercayaanActionPerformed

    private void KepercayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepercayaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KepercayaanKeyPressed

    private void PengetahuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengetahuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengetahuanActionPerformed

    private void PengetahuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengetahuanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengetahuanKeyPressed

    private void BudayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BudayaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BudayaActionPerformed

    private void BudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BudayaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BudayaKeyPressed

    private void MerokokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MerokokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MerokokActionPerformed

    private void MerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MerokokKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MerokokKeyPressed

    private void AlkoholActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlkoholActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlkoholActionPerformed

    private void AlkoholKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlkoholKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlkoholKeyPressed

    private void InformasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InformasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InformasiActionPerformed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InformasiKeyPressed

    private void AlasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlasanKeyPressed

    private void RencanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RencanaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RencanaActionPerformed

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RencanaKeyPressed

    private void JelaskanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JelaskanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JelaskanKeyPressed

    private void ProfesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfesiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProfesiActionPerformed

    private void ProfesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProfesiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProfesiKeyPressed

    private void Edukasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi1KeyPressed

    private void Kode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode1KeyPressed

    private void Metode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode1ActionPerformed

    private void Metode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode1KeyPressed

    private void Frekuensi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi1ActionPerformed

    private void Frekuensi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi1KeyPressed

    private void Evaluasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi1ActionPerformed

    private void Evaluasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi1KeyPressed

    private void BtnLainnya1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLainnya1ActionPerformed
        i=1;
//        cariedukasidokter.isCek();
        cariedukasirehabmedik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasirehabmedik.setLocationRelativeTo(internalFrame1);
        cariedukasirehabmedik.setAlwaysOnTop(false);
        cariedukasirehabmedik.setVisible(true);
    }//GEN-LAST:event_BtnLainnya1ActionPerformed

    private void BtnLainnya1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLainnya1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnLainnya1KeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        i=1;
//        cariedukasidokter.isCek();
        cariedukasidokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasidokter.setLocationRelativeTo(internalFrame1);
        cariedukasidokter.setAlwaysOnTop(false);
        cariedukasidokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1KeyPressed

    private void BtnPerawat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawat1ActionPerformed
        i=1;
//        cariedukasidokter.isCek();
        cariedukasiperawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasiperawat.setLocationRelativeTo(internalFrame1);
        cariedukasiperawat.setAlwaysOnTop(false);
        cariedukasiperawat.setVisible(true);
    }//GEN-LAST:event_BtnPerawat1ActionPerformed

    private void BtnPerawat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawat1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawat1KeyPressed

    private void BtnFarmasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFarmasi1ActionPerformed
        i=1;
//        cariedukasidokter.isCek();
        cariedukasifarmasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasifarmasi.setLocationRelativeTo(internalFrame1);
        cariedukasifarmasi.setAlwaysOnTop(false);
        cariedukasifarmasi.setVisible(true);
    }//GEN-LAST:event_BtnFarmasi1ActionPerformed

    private void BtnFarmasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFarmasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnFarmasi1KeyPressed

    private void BtnNutrisionis1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNutrisionis1ActionPerformed
        i=1;
//        cariedukasidokter.isCek();
        cariedukasinutrisionis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasinutrisionis.setLocationRelativeTo(internalFrame1);
        cariedukasinutrisionis.setAlwaysOnTop(false);
        cariedukasinutrisionis.setVisible(true);
    }//GEN-LAST:event_BtnNutrisionis1ActionPerformed

    private void BtnNutrisionis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNutrisionis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNutrisionis1KeyPressed

    private void Penerima1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima1ActionPerformed

    private void Penerima1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima1KeyPressed

    private void Edukasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi2KeyPressed

    private void Kode2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode2KeyPressed

    private void Metode2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode2ActionPerformed

    private void Metode2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode2KeyPressed

    private void Frekuensi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi2ActionPerformed

    private void Frekuensi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi2KeyPressed

    private void Evaluasi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi2ActionPerformed

    private void Evaluasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi2KeyPressed

    private void BtnLainnya2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLainnya2ActionPerformed
        i=2;
//        cariedukasidokter.isCek();
        cariedukasirehabmedik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasirehabmedik.setLocationRelativeTo(internalFrame1);
        cariedukasirehabmedik.setAlwaysOnTop(false);
        cariedukasirehabmedik.setVisible(true);
    }//GEN-LAST:event_BtnLainnya2ActionPerformed

    private void BtnLainnya2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLainnya2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnLainnya2KeyPressed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        i=2;
//        cariedukasidokter.isCek();
        cariedukasidokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasidokter.setLocationRelativeTo(internalFrame1);
        cariedukasidokter.setAlwaysOnTop(false);
        cariedukasidokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter2KeyPressed

    private void BtnPerawat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawat2ActionPerformed
        i=2;
//        cariedukasidokter.isCek();
        cariedukasiperawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasiperawat.setLocationRelativeTo(internalFrame1);
        cariedukasiperawat.setAlwaysOnTop(false);
        cariedukasiperawat.setVisible(true);
    }//GEN-LAST:event_BtnPerawat2ActionPerformed

    private void BtnPerawat2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawat2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawat2KeyPressed

    private void BtnFarmasi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFarmasi2ActionPerformed
        i=2;
//        cariedukasidokter.isCek();
        cariedukasifarmasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasifarmasi.setLocationRelativeTo(internalFrame1);
        cariedukasifarmasi.setAlwaysOnTop(false);
        cariedukasifarmasi.setVisible(true);
    }//GEN-LAST:event_BtnFarmasi2ActionPerformed

    private void BtnFarmasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFarmasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnFarmasi2KeyPressed

    private void BtnNutrisionis2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNutrisionis2ActionPerformed
        i=2;
//        cariedukasidokter.isCek();
        cariedukasinutrisionis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasinutrisionis.setLocationRelativeTo(internalFrame1);
        cariedukasinutrisionis.setAlwaysOnTop(false);
        cariedukasinutrisionis.setVisible(true);
    }//GEN-LAST:event_BtnNutrisionis2ActionPerformed

    private void BtnNutrisionis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNutrisionis2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNutrisionis2KeyPressed

    private void Penerima2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima2ActionPerformed

    private void Penerima2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima2KeyPressed

    private void Edukasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi3KeyPressed

    private void Kode3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode3KeyPressed

    private void Metode3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode3ActionPerformed

    private void Metode3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode3KeyPressed

    private void Frekuensi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi3ActionPerformed

    private void Frekuensi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi3KeyPressed

    private void Evaluasi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi3ActionPerformed

    private void Evaluasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi3KeyPressed

    private void BtnLainnya3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLainnya3ActionPerformed
        i=3;
//        cariedukasidokter.isCek();
        cariedukasirehabmedik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasirehabmedik.setLocationRelativeTo(internalFrame1);
        cariedukasirehabmedik.setAlwaysOnTop(false);
        cariedukasirehabmedik.setVisible(true);
    }//GEN-LAST:event_BtnLainnya3ActionPerformed

    private void BtnLainnya3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLainnya3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnLainnya3KeyPressed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        i=3;
//        cariedukasidokter.isCek();
        cariedukasidokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasidokter.setLocationRelativeTo(internalFrame1);
        cariedukasidokter.setAlwaysOnTop(false);
        cariedukasidokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void BtnDokter3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter3KeyPressed

    private void BtnPerawat3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawat3ActionPerformed
        i=3;
//        cariedukasidokter.isCek();
        cariedukasiperawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasiperawat.setLocationRelativeTo(internalFrame1);
        cariedukasiperawat.setAlwaysOnTop(false);
        cariedukasiperawat.setVisible(true);
    }//GEN-LAST:event_BtnPerawat3ActionPerformed

    private void BtnPerawat3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawat3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawat3KeyPressed

    private void BtnFarmasi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFarmasi3ActionPerformed
        i=3;
//        cariedukasidokter.isCek();
        cariedukasifarmasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasifarmasi.setLocationRelativeTo(internalFrame1);
        cariedukasifarmasi.setAlwaysOnTop(false);
        cariedukasifarmasi.setVisible(true);
    }//GEN-LAST:event_BtnFarmasi3ActionPerformed

    private void BtnFarmasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFarmasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnFarmasi3KeyPressed

    private void BtnNutrisionis3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNutrisionis3ActionPerformed
        i=3;
//        cariedukasidokter.isCek();
        cariedukasinutrisionis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasinutrisionis.setLocationRelativeTo(internalFrame1);
        cariedukasinutrisionis.setAlwaysOnTop(false);
        cariedukasinutrisionis.setVisible(true);
    }//GEN-LAST:event_BtnNutrisionis3ActionPerformed

    private void BtnNutrisionis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNutrisionis3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNutrisionis3KeyPressed

    private void Penerima3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima3ActionPerformed

    private void Penerima3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima3KeyPressed

    private void Edukasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi4KeyPressed

    private void Kode4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode4KeyPressed

    private void Metode4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode4ActionPerformed

    private void Metode4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode4KeyPressed

    private void Frekuensi4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi4ActionPerformed

    private void Frekuensi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi4KeyPressed

    private void Evaluasi4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi4ActionPerformed

    private void Evaluasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi4KeyPressed

    private void BtnLainnya4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLainnya4ActionPerformed
        i=4;
//        cariedukasidokter.isCek();
        cariedukasirehabmedik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasirehabmedik.setLocationRelativeTo(internalFrame1);
        cariedukasirehabmedik.setAlwaysOnTop(false);
        cariedukasirehabmedik.setVisible(true);
    }//GEN-LAST:event_BtnLainnya4ActionPerformed

    private void BtnLainnya4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLainnya4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnLainnya4KeyPressed

    private void BtnDokter4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter4ActionPerformed
        i=4;
//        cariedukasidokter.isCek();
        cariedukasidokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasidokter.setLocationRelativeTo(internalFrame1);
        cariedukasidokter.setAlwaysOnTop(false);
        cariedukasidokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter4ActionPerformed

    private void BtnDokter4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter4KeyPressed

    private void BtnPerawat4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawat4ActionPerformed
        i=4;
//        cariedukasidokter.isCek();
        cariedukasiperawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasiperawat.setLocationRelativeTo(internalFrame1);
        cariedukasiperawat.setAlwaysOnTop(false);
        cariedukasiperawat.setVisible(true);
    }//GEN-LAST:event_BtnPerawat4ActionPerformed

    private void BtnPerawat4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawat4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawat4KeyPressed

    private void BtnFarmasi4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFarmasi4ActionPerformed
        i=4;
//        cariedukasidokter.isCek();
        cariedukasifarmasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasifarmasi.setLocationRelativeTo(internalFrame1);
        cariedukasifarmasi.setAlwaysOnTop(false);
        cariedukasifarmasi.setVisible(true);
    }//GEN-LAST:event_BtnFarmasi4ActionPerformed

    private void BtnFarmasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFarmasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnFarmasi4KeyPressed

    private void BtnNutrisionis4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNutrisionis4ActionPerformed
        i=4;
//        cariedukasidokter.isCek();
        cariedukasinutrisionis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasinutrisionis.setLocationRelativeTo(internalFrame1);
        cariedukasinutrisionis.setAlwaysOnTop(false);
        cariedukasinutrisionis.setVisible(true);
    }//GEN-LAST:event_BtnNutrisionis4ActionPerformed

    private void BtnNutrisionis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNutrisionis4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNutrisionis4KeyPressed

    private void Penerima4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima4ActionPerformed

    private void Penerima4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima4KeyPressed

    private void Edukasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi5KeyPressed

    private void Kode5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode5KeyPressed

    private void Metode5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode5ActionPerformed

    private void Metode5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode5KeyPressed

    private void Frekuensi5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi5ActionPerformed

    private void Frekuensi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi5KeyPressed

    private void Evaluasi5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi5ActionPerformed

    private void Evaluasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi5KeyPressed

    private void BtnLainnya5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLainnya5ActionPerformed
        i=5;
//        cariedukasidokter.isCek();
        cariedukasirehabmedik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasirehabmedik.setLocationRelativeTo(internalFrame1);
        cariedukasirehabmedik.setAlwaysOnTop(false);
        cariedukasirehabmedik.setVisible(true);
    }//GEN-LAST:event_BtnLainnya5ActionPerformed

    private void BtnLainnya5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLainnya5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnLainnya5KeyPressed

    private void BtnDokter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter5ActionPerformed
        i=5;
//        cariedukasidokter.isCek();
        cariedukasidokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasidokter.setLocationRelativeTo(internalFrame1);
        cariedukasidokter.setAlwaysOnTop(false);
        cariedukasidokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter5ActionPerformed

    private void BtnDokter5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter5KeyPressed

    private void BtnPerawat5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawat5ActionPerformed
        i=5;
//        cariedukasidokter.isCek();
        cariedukasiperawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasiperawat.setLocationRelativeTo(internalFrame1);
        cariedukasiperawat.setAlwaysOnTop(false);
        cariedukasiperawat.setVisible(true);
    }//GEN-LAST:event_BtnPerawat5ActionPerformed

    private void BtnPerawat5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawat5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawat5KeyPressed

    private void BtnFarmasi5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFarmasi5ActionPerformed
        i=5;
//        cariedukasidokter.isCek();
        cariedukasifarmasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasifarmasi.setLocationRelativeTo(internalFrame1);
        cariedukasifarmasi.setAlwaysOnTop(false);
        cariedukasifarmasi.setVisible(true);
    }//GEN-LAST:event_BtnFarmasi5ActionPerformed

    private void BtnFarmasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFarmasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnFarmasi5KeyPressed

    private void BtnNutrisionis5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNutrisionis5ActionPerformed
        i=5;
//        cariedukasidokter.isCek();
        cariedukasinutrisionis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasinutrisionis.setLocationRelativeTo(internalFrame1);
        cariedukasinutrisionis.setAlwaysOnTop(false);
        cariedukasinutrisionis.setVisible(true);
    }//GEN-LAST:event_BtnNutrisionis5ActionPerformed

    private void BtnNutrisionis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNutrisionis5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNutrisionis5KeyPressed

    private void Penerima5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima5ActionPerformed

    private void Penerima5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima5KeyPressed

    private void Edukasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi6KeyPressed

    private void Kode6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode6KeyPressed

    private void Metode6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode6ActionPerformed

    private void Metode6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode6KeyPressed

    private void Frekuensi6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi6ActionPerformed

    private void Frekuensi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi6KeyPressed

    private void Evaluasi6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi6ActionPerformed

    private void Evaluasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi6KeyPressed

    private void BtnLainnya6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLainnya6ActionPerformed
        i=6;
//        cariedukasidokter.isCek();
        cariedukasirehabmedik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasirehabmedik.setLocationRelativeTo(internalFrame1);
        cariedukasirehabmedik.setAlwaysOnTop(false);
        cariedukasirehabmedik.setVisible(true);
    }//GEN-LAST:event_BtnLainnya6ActionPerformed

    private void BtnLainnya6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLainnya6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnLainnya6KeyPressed

    private void BtnDokter6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter6ActionPerformed
        i=6;
//        cariedukasidokter.isCek();
        cariedukasidokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasidokter.setLocationRelativeTo(internalFrame1);
        cariedukasidokter.setAlwaysOnTop(false);
        cariedukasidokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter6ActionPerformed

    private void BtnDokter6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter6KeyPressed

    private void BtnPerawat6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawat6ActionPerformed
        i=6;
//        cariedukasidokter.isCek();
        cariedukasiperawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasiperawat.setLocationRelativeTo(internalFrame1);
        cariedukasiperawat.setAlwaysOnTop(false);
        cariedukasiperawat.setVisible(true);
    }//GEN-LAST:event_BtnPerawat6ActionPerformed

    private void BtnPerawat6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawat6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawat6KeyPressed

    private void BtnFarmasi6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFarmasi6ActionPerformed
        i=6;
//        cariedukasidokter.isCek();
        cariedukasifarmasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasifarmasi.setLocationRelativeTo(internalFrame1);
        cariedukasifarmasi.setAlwaysOnTop(false);
        cariedukasifarmasi.setVisible(true);
    }//GEN-LAST:event_BtnFarmasi6ActionPerformed

    private void BtnFarmasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFarmasi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnFarmasi6KeyPressed

    private void BtnNutrisionis6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNutrisionis6ActionPerformed
        i=6;
//        cariedukasidokter.isCek();
        cariedukasinutrisionis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasinutrisionis.setLocationRelativeTo(internalFrame1);
        cariedukasinutrisionis.setAlwaysOnTop(false);
        cariedukasinutrisionis.setVisible(true);
    }//GEN-LAST:event_BtnNutrisionis6ActionPerformed

    private void BtnNutrisionis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNutrisionis6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNutrisionis6KeyPressed

    private void Penerima6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima6ActionPerformed

    private void Penerima6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima6KeyPressed

    private void Edukasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi7KeyPressed

    private void Kode7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode7KeyPressed

    private void Metode7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode7ActionPerformed

    private void Metode7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode7KeyPressed

    private void Frekuensi7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi7ActionPerformed

    private void Frekuensi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi7KeyPressed

    private void Evaluasi7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi7ActionPerformed

    private void Evaluasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi7KeyPressed

    private void BtnLainnya7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLainnya7ActionPerformed
        i=7;
//        cariedukasidokter.isCek();
        cariedukasirehabmedik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasirehabmedik.setLocationRelativeTo(internalFrame1);
        cariedukasirehabmedik.setAlwaysOnTop(false);
        cariedukasirehabmedik.setVisible(true);
    }//GEN-LAST:event_BtnLainnya7ActionPerformed

    private void BtnLainnya7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLainnya7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnLainnya7KeyPressed

    private void BtnDokter7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter7ActionPerformed
        i=7;
//        cariedukasidokter.isCek();
        cariedukasidokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasidokter.setLocationRelativeTo(internalFrame1);
        cariedukasidokter.setAlwaysOnTop(false);
        cariedukasidokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter7ActionPerformed

    private void BtnDokter7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter7KeyPressed

    private void BtnPerawat7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawat7ActionPerformed
        i=7;
//        cariedukasidokter.isCek();
        cariedukasiperawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasiperawat.setLocationRelativeTo(internalFrame1);
        cariedukasiperawat.setAlwaysOnTop(false);
        cariedukasiperawat.setVisible(true);
    }//GEN-LAST:event_BtnPerawat7ActionPerformed

    private void BtnPerawat7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawat7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawat7KeyPressed

    private void BtnFarmasi7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFarmasi7ActionPerformed
        i=7;
//        cariedukasidokter.isCek();
        cariedukasifarmasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasifarmasi.setLocationRelativeTo(internalFrame1);
        cariedukasifarmasi.setAlwaysOnTop(false);
        cariedukasifarmasi.setVisible(true);
    }//GEN-LAST:event_BtnFarmasi7ActionPerformed

    private void BtnFarmasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFarmasi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnFarmasi7KeyPressed

    private void BtnNutrisionis7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNutrisionis7ActionPerformed
        i=7;
//        cariedukasidokter.isCek();
        cariedukasinutrisionis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasinutrisionis.setLocationRelativeTo(internalFrame1);
        cariedukasinutrisionis.setAlwaysOnTop(false);
        cariedukasinutrisionis.setVisible(true);
    }//GEN-LAST:event_BtnNutrisionis7ActionPerformed

    private void BtnNutrisionis7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNutrisionis7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNutrisionis7KeyPressed

    private void Penerima7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima7ActionPerformed

    private void Penerima7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima7KeyPressed

    private void Edukasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi8KeyPressed

    private void Kode8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode8KeyPressed

    private void Metode8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode8ActionPerformed

    private void Metode8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode8KeyPressed

    private void Frekuensi8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi8ActionPerformed

    private void Frekuensi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi8KeyPressed

    private void Evaluasi8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi8ActionPerformed

    private void Evaluasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi8KeyPressed

    private void BtnLainnya8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLainnya8ActionPerformed
        i=8;
//        cariedukasidokter.isCek();
        cariedukasirehabmedik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasirehabmedik.setLocationRelativeTo(internalFrame1);
        cariedukasirehabmedik.setAlwaysOnTop(false);
        cariedukasirehabmedik.setVisible(true);
    }//GEN-LAST:event_BtnLainnya8ActionPerformed

    private void BtnLainnya8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLainnya8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnLainnya8KeyPressed

    private void BtnDokter8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter8ActionPerformed
        i=8;
//        cariedukasidokter.isCek();
        cariedukasidokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasidokter.setLocationRelativeTo(internalFrame1);
        cariedukasidokter.setAlwaysOnTop(false);
        cariedukasidokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter8ActionPerformed

    private void BtnDokter8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter8KeyPressed

    private void BtnPerawat8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawat8ActionPerformed
        i=8;
//        cariedukasidokter.isCek();
        cariedukasiperawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasiperawat.setLocationRelativeTo(internalFrame1);
        cariedukasiperawat.setAlwaysOnTop(false);
        cariedukasiperawat.setVisible(true);
    }//GEN-LAST:event_BtnPerawat8ActionPerformed

    private void BtnPerawat8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawat8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawat8KeyPressed

    private void BtnFarmasi8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFarmasi8ActionPerformed
        i=8;
//        cariedukasidokter.isCek();
        cariedukasifarmasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasifarmasi.setLocationRelativeTo(internalFrame1);
        cariedukasifarmasi.setAlwaysOnTop(false);
        cariedukasifarmasi.setVisible(true);
    }//GEN-LAST:event_BtnFarmasi8ActionPerformed

    private void BtnFarmasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFarmasi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnFarmasi8KeyPressed

    private void BtnNutrisionis8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNutrisionis8ActionPerformed
        i=8;
//        cariedukasidokter.isCek();
        cariedukasinutrisionis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasinutrisionis.setLocationRelativeTo(internalFrame1);
        cariedukasinutrisionis.setAlwaysOnTop(false);
        cariedukasinutrisionis.setVisible(true);
    }//GEN-LAST:event_BtnNutrisionis8ActionPerformed

    private void BtnNutrisionis8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNutrisionis8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNutrisionis8KeyPressed

    private void Penerima8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima8ActionPerformed

    private void Penerima8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima8KeyPressed

    private void Edukasi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi9KeyPressed

    private void Kode9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode9KeyPressed

    private void Metode9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode9ActionPerformed

    private void Metode9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode9KeyPressed

    private void Frekuensi9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi9ActionPerformed

    private void Frekuensi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi9KeyPressed

    private void Evaluasi9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi9ActionPerformed

    private void Evaluasi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi9KeyPressed

    private void BtnLainnya9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLainnya9ActionPerformed
        i=9;
//        cariedukasidokter.isCek();
        cariedukasirehabmedik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasirehabmedik.setLocationRelativeTo(internalFrame1);
        cariedukasirehabmedik.setAlwaysOnTop(false);
        cariedukasirehabmedik.setVisible(true);
    }//GEN-LAST:event_BtnLainnya9ActionPerformed

    private void BtnLainnya9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLainnya9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnLainnya9KeyPressed

    private void BtnDokter9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter9ActionPerformed
        i=9;
//        cariedukasidokter.isCek();
        cariedukasidokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasidokter.setLocationRelativeTo(internalFrame1);
        cariedukasidokter.setAlwaysOnTop(false);
        cariedukasidokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter9ActionPerformed

    private void BtnDokter9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter9KeyPressed

    private void BtnPerawat9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawat9ActionPerformed
        i=9;
//        cariedukasidokter.isCek();
        cariedukasiperawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasiperawat.setLocationRelativeTo(internalFrame1);
        cariedukasiperawat.setAlwaysOnTop(false);
        cariedukasiperawat.setVisible(true);
    }//GEN-LAST:event_BtnPerawat9ActionPerformed

    private void BtnPerawat9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawat9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawat9KeyPressed

    private void BtnFarmasi9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFarmasi9ActionPerformed
        i=9;
//        cariedukasidokter.isCek();
        cariedukasifarmasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasifarmasi.setLocationRelativeTo(internalFrame1);
        cariedukasifarmasi.setAlwaysOnTop(false);
        cariedukasifarmasi.setVisible(true);
    }//GEN-LAST:event_BtnFarmasi9ActionPerformed

    private void BtnFarmasi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFarmasi9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnFarmasi9KeyPressed

    private void BtnNutrisionis9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNutrisionis9ActionPerformed
        i=9;
//        cariedukasidokter.isCek();
        cariedukasinutrisionis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasinutrisionis.setLocationRelativeTo(internalFrame1);
        cariedukasinutrisionis.setAlwaysOnTop(false);
        cariedukasinutrisionis.setVisible(true);
    }//GEN-LAST:event_BtnNutrisionis9ActionPerformed

    private void BtnNutrisionis9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNutrisionis9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNutrisionis9KeyPressed

    private void Penerima9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima9ActionPerformed

    private void Penerima9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima9KeyPressed

    private void Edukasi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi10KeyPressed

    private void Kode10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode10KeyPressed

    private void Metode10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode10ActionPerformed

    private void Metode10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode10KeyPressed

    private void Frekuensi10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi10ActionPerformed

    private void Frekuensi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi10KeyPressed

    private void Evaluasi10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi10ActionPerformed

    private void Evaluasi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi10KeyPressed

    private void BtnLainnya10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLainnya10ActionPerformed
        i=10;
//        cariedukasidokter.isCek();
        cariedukasirehabmedik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasirehabmedik.setLocationRelativeTo(internalFrame1);
        cariedukasirehabmedik.setAlwaysOnTop(false);
        cariedukasirehabmedik.setVisible(true);
    }//GEN-LAST:event_BtnLainnya10ActionPerformed

    private void BtnLainnya10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLainnya10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnLainnya10KeyPressed

    private void BtnDokter10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter10ActionPerformed
        i=10;
//        cariedukasidokter.isCek();
        cariedukasidokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasidokter.setLocationRelativeTo(internalFrame1);
        cariedukasidokter.setAlwaysOnTop(false);
        cariedukasidokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter10ActionPerformed

    private void BtnDokter10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter10KeyPressed

    private void BtnPerawat10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawat10ActionPerformed
        i=10;
//        cariedukasidokter.isCek();
        cariedukasiperawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasiperawat.setLocationRelativeTo(internalFrame1);
        cariedukasiperawat.setAlwaysOnTop(false);
        cariedukasiperawat.setVisible(true);
    }//GEN-LAST:event_BtnPerawat10ActionPerformed

    private void BtnPerawat10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawat10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerawat10KeyPressed

    private void BtnFarmasi10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFarmasi10ActionPerformed
        i=10;
//        cariedukasidokter.isCek();
        cariedukasifarmasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasifarmasi.setLocationRelativeTo(internalFrame1);
        cariedukasifarmasi.setAlwaysOnTop(false);
        cariedukasifarmasi.setVisible(true);
    }//GEN-LAST:event_BtnFarmasi10ActionPerformed

    private void BtnFarmasi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFarmasi10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnFarmasi10KeyPressed

    private void BtnNutrisionis10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNutrisionis10ActionPerformed
        i=10;
//        cariedukasidokter.isCek();
        cariedukasinutrisionis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cariedukasinutrisionis.setLocationRelativeTo(internalFrame1);
        cariedukasinutrisionis.setAlwaysOnTop(false);
        cariedukasinutrisionis.setVisible(true);
    }//GEN-LAST:event_BtnNutrisionis10ActionPerformed

    private void BtnNutrisionis10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNutrisionis10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNutrisionis10KeyPressed

    private void Penerima10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima10ActionPerformed

    private void Penerima10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima10KeyPressed

    private void EdukasiLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiLainnyaKeyPressed
//        Valid.pindah(evt,BMI,RPK);
    }//GEN-LAST:event_EdukasiLainnyaKeyPressed

    private void Kode11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode11KeyPressed

    private void Edukasi11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi11KeyPressed

    private void Penerima11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima11ActionPerformed

    private void Penerima11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima11KeyPressed

    private void Metode11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode11ActionPerformed

    private void Metode11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode11KeyPressed

    private void Frekuensi11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Frekuensi11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi11ActionPerformed

    private void Frekuensi11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Frekuensi11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Frekuensi11KeyPressed

    private void Evaluasi11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi11ActionPerformed

    private void Evaluasi11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi11KeyPressed

    private void ProfesiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ProfesiItemStateChanged
        if(Profesi.getSelectedIndex()==0){
        BtnDokter1.setVisible(false);
        BtnDokter2.setVisible(false);
        BtnDokter3.setVisible(false);
        BtnDokter4.setVisible(false);
        BtnDokter5.setVisible(false);
        BtnDokter6.setVisible(false);
        BtnDokter7.setVisible(false);
        BtnDokter8.setVisible(false);
        BtnDokter9.setVisible(false);
        BtnDokter10.setVisible(false);
        BtnPerawat1.setVisible(false);
        BtnPerawat2.setVisible(false);
        BtnPerawat3.setVisible(false);
        BtnPerawat4.setVisible(false);
        BtnPerawat5.setVisible(false);
        BtnPerawat6.setVisible(false);
        BtnPerawat7.setVisible(false);
        BtnPerawat8.setVisible(false);
        BtnPerawat9.setVisible(false);
        BtnPerawat10.setVisible(false);
        BtnFarmasi1.setVisible(false);
        BtnFarmasi2.setVisible(false);
        BtnFarmasi3.setVisible(false);
        BtnFarmasi4.setVisible(false);
        BtnFarmasi5.setVisible(false);
        BtnFarmasi6.setVisible(false);
        BtnFarmasi7.setVisible(false);
        BtnFarmasi8.setVisible(false);
        BtnFarmasi9.setVisible(false);
        BtnFarmasi10.setVisible(false);
        BtnNutrisionis1.setVisible(false);
        BtnNutrisionis2.setVisible(false);
        BtnNutrisionis3.setVisible(false);
        BtnNutrisionis4.setVisible(false);
        BtnNutrisionis5.setVisible(false);
        BtnNutrisionis6.setVisible(false);
        BtnNutrisionis7.setVisible(false);
        BtnNutrisionis8.setVisible(false);
        BtnNutrisionis9.setVisible(false);
        BtnNutrisionis10.setVisible(false);
        BtnLainnya1.setVisible(false);
        BtnLainnya2.setVisible(false);
        BtnLainnya3.setVisible(false);
        BtnLainnya4.setVisible(false);
        BtnLainnya5.setVisible(false);
        BtnLainnya6.setVisible(false);
        BtnLainnya7.setVisible(false);
        BtnLainnya8.setVisible(false);
        BtnLainnya9.setVisible(false);
        BtnLainnya10.setVisible(false);
        }else if(Profesi.getSelectedIndex()==1){
        BtnDokter1.setVisible(true);
        BtnDokter2.setVisible(true);
        BtnDokter3.setVisible(true);
        BtnDokter4.setVisible(true);
        BtnDokter5.setVisible(true);
        BtnDokter6.setVisible(true);
        BtnDokter7.setVisible(true);
        BtnDokter8.setVisible(true);
        BtnDokter9.setVisible(true);
        BtnDokter10.setVisible(true);
        BtnPerawat1.setVisible(false);
        BtnPerawat2.setVisible(false);
        BtnPerawat3.setVisible(false);
        BtnPerawat4.setVisible(false);
        BtnPerawat5.setVisible(false);
        BtnPerawat6.setVisible(false);
        BtnPerawat7.setVisible(false);
        BtnPerawat8.setVisible(false);
        BtnPerawat9.setVisible(false);
        BtnPerawat10.setVisible(false);
        BtnFarmasi1.setVisible(false);
        BtnFarmasi2.setVisible(false);
        BtnFarmasi3.setVisible(false);
        BtnFarmasi4.setVisible(false);
        BtnFarmasi5.setVisible(false);
        BtnFarmasi6.setVisible(false);
        BtnFarmasi7.setVisible(false);
        BtnFarmasi8.setVisible(false);
        BtnFarmasi9.setVisible(false);
        BtnFarmasi10.setVisible(false);
        BtnNutrisionis1.setVisible(false);
        BtnNutrisionis2.setVisible(false);
        BtnNutrisionis3.setVisible(false);
        BtnNutrisionis4.setVisible(false);
        BtnNutrisionis5.setVisible(false);
        BtnNutrisionis6.setVisible(false);
        BtnNutrisionis7.setVisible(false);
        BtnNutrisionis8.setVisible(false);
        BtnNutrisionis9.setVisible(false);
        BtnNutrisionis10.setVisible(false);
        BtnLainnya1.setVisible(false);
        BtnLainnya2.setVisible(false);
        BtnLainnya3.setVisible(false);
        BtnLainnya4.setVisible(false);
        BtnLainnya5.setVisible(false);
        BtnLainnya6.setVisible(false);
        BtnLainnya7.setVisible(false);
        BtnLainnya8.setVisible(false);
        BtnLainnya9.setVisible(false);
        BtnLainnya10.setVisible(false);
        }else if(Profesi.getSelectedIndex()==2){
        BtnPerawat1.setVisible(true);
        BtnPerawat2.setVisible(true);
        BtnPerawat3.setVisible(true);
        BtnPerawat4.setVisible(true);
        BtnPerawat5.setVisible(true);
        BtnPerawat6.setVisible(true);
        BtnPerawat7.setVisible(true);
        BtnPerawat8.setVisible(true);
        BtnPerawat9.setVisible(true);
        BtnPerawat10.setVisible(true);
        BtnDokter1.setVisible(false);
        BtnDokter2.setVisible(false);
        BtnDokter3.setVisible(false);
        BtnDokter4.setVisible(false);
        BtnDokter5.setVisible(false);
        BtnDokter6.setVisible(false);
        BtnDokter7.setVisible(false);
        BtnDokter8.setVisible(false);
        BtnDokter9.setVisible(false);
        BtnDokter10.setVisible(false);
        BtnFarmasi1.setVisible(false);
        BtnFarmasi2.setVisible(false);
        BtnFarmasi3.setVisible(false);
        BtnFarmasi4.setVisible(false);
        BtnFarmasi5.setVisible(false);
        BtnFarmasi6.setVisible(false);
        BtnFarmasi7.setVisible(false);
        BtnFarmasi8.setVisible(false);
        BtnFarmasi9.setVisible(false);
        BtnFarmasi10.setVisible(false);
        BtnNutrisionis1.setVisible(false);
        BtnNutrisionis2.setVisible(false);
        BtnNutrisionis3.setVisible(false);
        BtnNutrisionis4.setVisible(false);
        BtnNutrisionis5.setVisible(false);
        BtnNutrisionis6.setVisible(false);
        BtnNutrisionis7.setVisible(false);
        BtnNutrisionis8.setVisible(false);
        BtnNutrisionis9.setVisible(false);
        BtnNutrisionis10.setVisible(false);
        BtnLainnya1.setVisible(false);
        BtnLainnya2.setVisible(false);
        BtnLainnya3.setVisible(false);
        BtnLainnya4.setVisible(false);
        BtnLainnya5.setVisible(false);
        BtnLainnya6.setVisible(false);
        BtnLainnya7.setVisible(false);
        BtnLainnya8.setVisible(false);
        BtnLainnya9.setVisible(false);
        BtnLainnya10.setVisible(false);
        }else if(Profesi.getSelectedIndex()==3){
        BtnFarmasi1.setVisible(true);
        BtnFarmasi2.setVisible(true);
        BtnFarmasi3.setVisible(true);
        BtnFarmasi4.setVisible(true);
        BtnFarmasi5.setVisible(true);
        BtnFarmasi6.setVisible(true);
        BtnFarmasi7.setVisible(true);
        BtnFarmasi8.setVisible(true);
        BtnFarmasi9.setVisible(true);
        BtnFarmasi10.setVisible(true);
        BtnDokter1.setVisible(false);
        BtnDokter2.setVisible(false);
        BtnDokter3.setVisible(false);
        BtnDokter4.setVisible(false);
        BtnDokter5.setVisible(false);
        BtnDokter6.setVisible(false);
        BtnDokter7.setVisible(false);
        BtnDokter8.setVisible(false);
        BtnDokter9.setVisible(false);
        BtnDokter10.setVisible(false);
        BtnPerawat1.setVisible(false);
        BtnPerawat2.setVisible(false);
        BtnPerawat3.setVisible(false);
        BtnPerawat4.setVisible(false);
        BtnPerawat5.setVisible(false);
        BtnPerawat6.setVisible(false);
        BtnPerawat7.setVisible(false);
        BtnPerawat8.setVisible(false);
        BtnPerawat9.setVisible(false);
        BtnPerawat10.setVisible(false);
        BtnNutrisionis1.setVisible(false);
        BtnNutrisionis2.setVisible(false);
        BtnNutrisionis3.setVisible(false);
        BtnNutrisionis4.setVisible(false);
        BtnNutrisionis5.setVisible(false);
        BtnNutrisionis6.setVisible(false);
        BtnNutrisionis7.setVisible(false);
        BtnNutrisionis8.setVisible(false);
        BtnNutrisionis9.setVisible(false);
        BtnNutrisionis10.setVisible(false);
        BtnLainnya1.setVisible(false);
        BtnLainnya2.setVisible(false);
        BtnLainnya3.setVisible(false);
        BtnLainnya4.setVisible(false);
        BtnLainnya5.setVisible(false);
        BtnLainnya6.setVisible(false);
        BtnLainnya7.setVisible(false);
        BtnLainnya8.setVisible(false);
        BtnLainnya9.setVisible(false);
        BtnLainnya10.setVisible(false);
        }else if(Profesi.getSelectedIndex()==4){
        BtnNutrisionis1.setVisible(true);
        BtnNutrisionis2.setVisible(true);
        BtnNutrisionis3.setVisible(true);
        BtnNutrisionis4.setVisible(true);
        BtnNutrisionis5.setVisible(true);
        BtnNutrisionis6.setVisible(true);
        BtnNutrisionis7.setVisible(true);
        BtnNutrisionis8.setVisible(true);
        BtnNutrisionis9.setVisible(true);
        BtnNutrisionis10.setVisible(true);
        BtnDokter1.setVisible(false);
        BtnDokter2.setVisible(false);
        BtnDokter3.setVisible(false);
        BtnDokter4.setVisible(false);
        BtnDokter5.setVisible(false);
        BtnDokter6.setVisible(false);
        BtnDokter7.setVisible(false);
        BtnDokter8.setVisible(false);
        BtnDokter9.setVisible(false);
        BtnDokter10.setVisible(false);
        BtnPerawat1.setVisible(false);
        BtnPerawat2.setVisible(false);
        BtnPerawat3.setVisible(false);
        BtnPerawat4.setVisible(false);
        BtnPerawat5.setVisible(false);
        BtnPerawat6.setVisible(false);
        BtnPerawat7.setVisible(false);
        BtnPerawat8.setVisible(false);
        BtnPerawat9.setVisible(false);
        BtnPerawat10.setVisible(false);
        BtnFarmasi1.setVisible(false);
        BtnFarmasi2.setVisible(false);
        BtnFarmasi3.setVisible(false);
        BtnFarmasi4.setVisible(false);
        BtnFarmasi5.setVisible(false);
        BtnFarmasi6.setVisible(false);
        BtnFarmasi7.setVisible(false);
        BtnFarmasi8.setVisible(false);
        BtnFarmasi9.setVisible(false);
        BtnFarmasi10.setVisible(false);
        BtnLainnya1.setVisible(false);
        BtnLainnya2.setVisible(false);
        BtnLainnya3.setVisible(false);
        BtnLainnya4.setVisible(false);
        BtnLainnya5.setVisible(false);
        BtnLainnya6.setVisible(false);
        BtnLainnya7.setVisible(false);
        BtnLainnya8.setVisible(false);
        BtnLainnya9.setVisible(false);
        BtnLainnya10.setVisible(false);
        }else if(Profesi.getSelectedIndex()==5){
        BtnLainnya1.setVisible(true);
        BtnLainnya2.setVisible(true);
        BtnLainnya3.setVisible(true);
        BtnLainnya4.setVisible(true);
        BtnLainnya5.setVisible(true);
        BtnLainnya6.setVisible(true);
        BtnLainnya7.setVisible(true);
        BtnLainnya8.setVisible(true);
        BtnLainnya9.setVisible(true);
        BtnLainnya10.setVisible(true);
        BtnDokter1.setVisible(false);
        BtnDokter2.setVisible(false);
        BtnDokter3.setVisible(false);
        BtnDokter4.setVisible(false);
        BtnDokter5.setVisible(false);
        BtnDokter6.setVisible(false);
        BtnDokter7.setVisible(false);
        BtnDokter8.setVisible(false);
        BtnDokter9.setVisible(false);
        BtnDokter10.setVisible(false);
        BtnPerawat1.setVisible(false);
        BtnPerawat2.setVisible(false);
        BtnPerawat3.setVisible(false);
        BtnPerawat4.setVisible(false);
        BtnPerawat5.setVisible(false);
        BtnPerawat6.setVisible(false);
        BtnPerawat7.setVisible(false);
        BtnPerawat8.setVisible(false);
        BtnPerawat9.setVisible(false);
        BtnPerawat10.setVisible(false);
        BtnFarmasi1.setVisible(false);
        BtnFarmasi2.setVisible(false);
        BtnFarmasi3.setVisible(false);
        BtnFarmasi4.setVisible(false);
        BtnFarmasi5.setVisible(false);
        BtnFarmasi6.setVisible(false);
        BtnFarmasi7.setVisible(false);
        BtnFarmasi8.setVisible(false);
        BtnFarmasi9.setVisible(false);
        BtnFarmasi10.setVisible(false);
        BtnNutrisionis1.setVisible(false);
        BtnNutrisionis2.setVisible(false);
        BtnNutrisionis3.setVisible(false);
        BtnNutrisionis4.setVisible(false);
        BtnNutrisionis5.setVisible(false);
        BtnNutrisionis6.setVisible(false);
        BtnNutrisionis7.setVisible(false);
        BtnNutrisionis8.setVisible(false);
        BtnNutrisionis9.setVisible(false);
        BtnNutrisionis10.setVisible(false);
        }else if(Profesi.getSelectedIndex()==6){
        BtnLainnya1.setVisible(true);
        BtnLainnya2.setVisible(true);
        BtnLainnya3.setVisible(true);
        BtnLainnya4.setVisible(true);
        BtnLainnya5.setVisible(true);
        BtnLainnya6.setVisible(true);
        BtnLainnya7.setVisible(true);
        BtnLainnya8.setVisible(true);
        BtnLainnya9.setVisible(true);
        BtnLainnya10.setVisible(true);
        BtnDokter1.setVisible(false);
        BtnDokter2.setVisible(false);
        BtnDokter3.setVisible(false);
        BtnDokter4.setVisible(false);
        BtnDokter5.setVisible(false);
        BtnDokter6.setVisible(false);
        BtnDokter7.setVisible(false);
        BtnDokter8.setVisible(false);
        BtnDokter9.setVisible(false);
        BtnDokter10.setVisible(false);
        BtnPerawat1.setVisible(false);
        BtnPerawat2.setVisible(false);
        BtnPerawat3.setVisible(false);
        BtnPerawat4.setVisible(false);
        BtnPerawat5.setVisible(false);
        BtnPerawat6.setVisible(false);
        BtnPerawat7.setVisible(false);
        BtnPerawat8.setVisible(false);
        BtnPerawat9.setVisible(false);
        BtnPerawat10.setVisible(false);
        BtnFarmasi1.setVisible(false);
        BtnFarmasi2.setVisible(false);
        BtnFarmasi3.setVisible(false);
        BtnFarmasi4.setVisible(false);
        BtnFarmasi5.setVisible(false);
        BtnFarmasi6.setVisible(false);
        BtnFarmasi7.setVisible(false);
        BtnFarmasi8.setVisible(false);
        BtnFarmasi9.setVisible(false);
        BtnFarmasi10.setVisible(false);
        BtnNutrisionis1.setVisible(false);
        BtnNutrisionis2.setVisible(false);
        BtnNutrisionis3.setVisible(false);
        BtnNutrisionis4.setVisible(false);
        BtnNutrisionis5.setVisible(false);
        BtnNutrisionis6.setVisible(false);
        BtnNutrisionis7.setVisible(false);
        BtnNutrisionis8.setVisible(false);
        BtnNutrisionis9.setVisible(false);
        BtnNutrisionis10.setVisible(false);
        }
    }//GEN-LAST:event_ProfesiItemStateChanged

    private void TanggalSuratItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalSuratItemStateChanged
        autoNumberX(TanggalSurat.getSelectedItem()+"");
    }//GEN-LAST:event_TanggalSuratItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataEdukasiRanap dialog = new RMDataEdukasiRanap(new javax.swing.JFrame(), true);
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
    private widget.TextBox Agama;
    private widget.TextBox Alasan;
    private widget.ComboBox Alkohol;
    private widget.TextBox Bahasa;
    private widget.ComboBox Bicara;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter10;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter3;
    private widget.Button BtnDokter4;
    private widget.Button BtnDokter5;
    private widget.Button BtnDokter6;
    private widget.Button BtnDokter7;
    private widget.Button BtnDokter8;
    private widget.Button BtnDokter9;
    private widget.Button BtnEdit;
    private widget.Button BtnFarmasi1;
    private widget.Button BtnFarmasi10;
    private widget.Button BtnFarmasi2;
    private widget.Button BtnFarmasi3;
    private widget.Button BtnFarmasi4;
    private widget.Button BtnFarmasi5;
    private widget.Button BtnFarmasi6;
    private widget.Button BtnFarmasi7;
    private widget.Button BtnFarmasi8;
    private widget.Button BtnFarmasi9;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnLainnya1;
    private widget.Button BtnLainnya10;
    private widget.Button BtnLainnya2;
    private widget.Button BtnLainnya3;
    private widget.Button BtnLainnya4;
    private widget.Button BtnLainnya5;
    private widget.Button BtnLainnya6;
    private widget.Button BtnLainnya7;
    private widget.Button BtnLainnya8;
    private widget.Button BtnLainnya9;
    private widget.Button BtnNutrisionis1;
    private widget.Button BtnNutrisionis10;
    private widget.Button BtnNutrisionis2;
    private widget.Button BtnNutrisionis3;
    private widget.Button BtnNutrisionis4;
    private widget.Button BtnNutrisionis5;
    private widget.Button BtnNutrisionis6;
    private widget.Button BtnNutrisionis7;
    private widget.Button BtnNutrisionis8;
    private widget.Button BtnNutrisionis9;
    private widget.Button BtnPerawat1;
    private widget.Button BtnPerawat10;
    private widget.Button BtnPerawat2;
    private widget.Button BtnPerawat3;
    private widget.Button BtnPerawat4;
    private widget.Button BtnPerawat5;
    private widget.Button BtnPerawat6;
    private widget.Button BtnPerawat7;
    private widget.Button BtnPerawat8;
    private widget.Button BtnPerawat9;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.Button BtnSimpan;
    private widget.ComboBox Budaya;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Edukasi1;
    private widget.TextBox Edukasi10;
    private widget.TextBox Edukasi11;
    private widget.TextBox Edukasi2;
    private widget.TextBox Edukasi3;
    private widget.TextBox Edukasi4;
    private widget.TextBox Edukasi5;
    private widget.TextBox Edukasi6;
    private widget.TextBox Edukasi7;
    private widget.TextBox Edukasi8;
    private widget.TextBox Edukasi9;
    private widget.TextArea EdukasiLainnya;
    private widget.ComboBox Evaluasi1;
    private widget.ComboBox Evaluasi10;
    private widget.ComboBox Evaluasi11;
    private widget.ComboBox Evaluasi2;
    private widget.ComboBox Evaluasi3;
    private widget.ComboBox Evaluasi4;
    private widget.ComboBox Evaluasi5;
    private widget.ComboBox Evaluasi6;
    private widget.ComboBox Evaluasi7;
    private widget.ComboBox Evaluasi8;
    private widget.ComboBox Evaluasi9;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox Frekuensi1;
    private widget.ComboBox Frekuensi10;
    private widget.ComboBox Frekuensi11;
    private widget.ComboBox Frekuensi2;
    private widget.ComboBox Frekuensi3;
    private widget.ComboBox Frekuensi4;
    private widget.ComboBox Frekuensi5;
    private widget.ComboBox Frekuensi6;
    private widget.ComboBox Frekuensi7;
    private widget.ComboBox Frekuensi8;
    private widget.ComboBox Frekuensi9;
    private widget.TextBox Hubungan;
    private widget.ComboBox Informasi;
    private widget.ComboBox Isyarat;
    private widget.TextBox Jelaskan;
    private widget.TextBox Kebutuhan;
    private widget.ComboBox Kepercayaan;
    private widget.TextBox Kode1;
    private widget.TextBox Kode10;
    private widget.TextBox Kode11;
    private widget.TextBox Kode2;
    private widget.TextBox Kode3;
    private widget.TextBox Kode4;
    private widget.TextBox Kode5;
    private widget.TextBox Kode6;
    private widget.TextBox Kode7;
    private widget.TextBox Kode8;
    private widget.TextBox Kode9;
    private widget.TextBox KodeDokter;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Membaca;
    private widget.ComboBox Merokok;
    private widget.ComboBox Metode1;
    private widget.ComboBox Metode10;
    private widget.ComboBox Metode11;
    private widget.ComboBox Metode2;
    private widget.ComboBox Metode3;
    private widget.ComboBox Metode4;
    private widget.ComboBox Metode5;
    private widget.ComboBox Metode6;
    private widget.ComboBox Metode7;
    private widget.ComboBox Metode8;
    private widget.ComboBox Metode9;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox NamaDokter;
    private widget.TextBox NoSurat;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pendidikan;
    private widget.ComboBox Penerima1;
    private widget.ComboBox Penerima10;
    private widget.ComboBox Penerima11;
    private widget.ComboBox Penerima2;
    private widget.ComboBox Penerima3;
    private widget.ComboBox Penerima4;
    private widget.ComboBox Penerima5;
    private widget.ComboBox Penerima6;
    private widget.ComboBox Penerima7;
    private widget.ComboBox Penerima8;
    private widget.ComboBox Penerima9;
    private widget.ComboBox Penerjemah;
    private widget.ComboBox Pengetahuan;
    private widget.ComboBox Pengkajian;
    private widget.TextBox PengkajianLainnya;
    private widget.ComboBox Profesi;
    private widget.ComboBox Rencana;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalSurat;
    private widget.TextBox accep;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
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
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "edukasi_pasien_ranap.no_surat,edukasi_pasien_ranap.no_rawat,edukasi_pasien_ranap.tanggal_surat,edukasi_pasien_ranap.jam,edukasi_pasien_ranap.tanggal_pengkajian, "+
                    "edukasi_pasien_ranap.jam_pengkajian,edukasi_pasien_ranap.nik,pegawai.nama,edukasi_pasien_ranap.pengkajian,edukasi_pasien_ranap.pengkajian_lainnya, "+
                    "edukasi_pasien_ranap.bicara,edukasi_pasien_ranap.membaca,edukasi_pasien_ranap.penerjemah,edukasi_pasien_ranap.isyarat, "+
                    "edukasi_pasien_ranap.kepercayaan,edukasi_pasien_ranap.pengetahuan,edukasi_pasien_ranap.budaya,edukasi_pasien_ranap.merokok, "+
                    "edukasi_pasien_ranap.alkohol,edukasi_pasien_ranap.informasi,edukasi_pasien_ranap.alasan,edukasi_pasien_ranap.rencana,edukasi_pasien_ranap.jelaskan, "+
                    "edukasi_pasien_ranap.profesi,edukasi_pasien_ranap.kd_edukasi1,edukasi_pasien_ranap.edukasi1,edukasi_pasien_ranap.penerima1, "+
                    "edukasi_pasien_ranap.metode1,edukasi_pasien_ranap.frekuensi1,edukasi_pasien_ranap.evaluasi1,edukasi_pasien_ranap.kd_edukasi2, "+
                    "edukasi_pasien_ranap.edukasi2,edukasi_pasien_ranap.penerima2,edukasi_pasien_ranap.metode2,edukasi_pasien_ranap.frekuensi2, "+
                    "edukasi_pasien_ranap.evaluasi2,edukasi_pasien_ranap.kd_edukasi3,edukasi_pasien_ranap.edukasi3,edukasi_pasien_ranap.penerima3, "+
                    "edukasi_pasien_ranap.metode3,edukasi_pasien_ranap.frekuensi3,edukasi_pasien_ranap.evaluasi3,edukasi_pasien_ranap.kd_edukasi4,edukasi_pasien_ranap.edukasi4, "+
                    "edukasi_pasien_ranap.penerima4,edukasi_pasien_ranap.metode4,edukasi_pasien_ranap.frekuensi4,edukasi_pasien_ranap.evaluasi4, "+
                    "edukasi_pasien_ranap.kd_edukasi5,edukasi_pasien_ranap.edukasi5,edukasi_pasien_ranap.penerima5,edukasi_pasien_ranap.metode5, "+
                    "edukasi_pasien_ranap.frekuensi5,edukasi_pasien_ranap.evaluasi5,edukasi_pasien_ranap.kd_edukasi6,edukasi_pasien_ranap.edukasi6, "+
                    "edukasi_pasien_ranap.penerima6,edukasi_pasien_ranap.metode6,edukasi_pasien_ranap.frekuensi6,edukasi_pasien_ranap.evaluasi6, "+
                    "edukasi_pasien_ranap.kd_edukasi7,edukasi_pasien_ranap.edukasi7,edukasi_pasien_ranap.penerima7,edukasi_pasien_ranap.metode7, "+
                    "edukasi_pasien_ranap.frekuensi7,edukasi_pasien_ranap.evaluasi7,edukasi_pasien_ranap.kd_edukasi8,edukasi_pasien_ranap.edukasi8, "+
                    "edukasi_pasien_ranap.penerima8,edukasi_pasien_ranap.metode8,edukasi_pasien_ranap.frekuensi8,edukasi_pasien_ranap.evaluasi8, "+
                    "edukasi_pasien_ranap.kd_edukasi9,edukasi_pasien_ranap.edukasi9,edukasi_pasien_ranap.penerima9,edukasi_pasien_ranap.metode9, "+
                    "edukasi_pasien_ranap.frekuensi9,edukasi_pasien_ranap.evaluasi9,edukasi_pasien_ranap.kd_edukasi10,edukasi_pasien_ranap.edukasi10, "+
                    "edukasi_pasien_ranap.penerima10,edukasi_pasien_ranap.metode10,edukasi_pasien_ranap.frekuensi10,edukasi_pasien_ranap.evaluasi10, "+
                    "edukasi_pasien_ranap.edukasi_lainnya,edukasi_pasien_ranap.kolaborasi,edukasi_pasien_ranap.edukasi_kolaborasi, "+
                    "edukasi_pasien_ranap.penerima11,edukasi_pasien_ranap.metode11,edukasi_pasien_ranap.frekuensi11,edukasi_pasien_ranap.evaluasi11, "+
                    "edukasi_pasien_ranap.hubungan,edukasi_pasien_ranap.kedua,edukasi_pasien_ranap.acc_ep,edukasi_pasien_ranap.tte from edukasi_pasien_ranap inner join reg_periksa on edukasi_pasien_ranap.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on edukasi_pasien_ranap.nik=pegawai.nik "+
                    "where edukasi_pasien_ranap.tanggal_surat between ? and ? order by edukasi_pasien_ranap.tanggal_surat");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "edukasi_pasien_ranap.no_surat,edukasi_pasien_ranap.no_rawat,edukasi_pasien_ranap.tanggal_surat,edukasi_pasien_ranap.jam,edukasi_pasien_ranap.tanggal_pengkajian, "+
                    "edukasi_pasien_ranap.jam_pengkajian,edukasi_pasien_ranap.nik,pegawai.nama,edukasi_pasien_ranap.pengkajian,edukasi_pasien_ranap.pengkajian_lainnya, "+
                    "edukasi_pasien_ranap.bicara,edukasi_pasien_ranap.membaca,edukasi_pasien_ranap.penerjemah,edukasi_pasien_ranap.isyarat, "+
                    "edukasi_pasien_ranap.kepercayaan,edukasi_pasien_ranap.pengetahuan,edukasi_pasien_ranap.budaya,edukasi_pasien_ranap.merokok, "+
                    "edukasi_pasien_ranap.alkohol,edukasi_pasien_ranap.informasi,edukasi_pasien_ranap.alasan,edukasi_pasien_ranap.rencana,edukasi_pasien_ranap.jelaskan, "+
                    "edukasi_pasien_ranap.profesi,edukasi_pasien_ranap.kd_edukasi1,edukasi_pasien_ranap.edukasi1,edukasi_pasien_ranap.penerima1, "+
                    "edukasi_pasien_ranap.metode1,edukasi_pasien_ranap.frekuensi1,edukasi_pasien_ranap.evaluasi1,edukasi_pasien_ranap.kd_edukasi2, "+
                    "edukasi_pasien_ranap.edukasi2,edukasi_pasien_ranap.penerima2,edukasi_pasien_ranap.metode2,edukasi_pasien_ranap.frekuensi2, "+
                    "edukasi_pasien_ranap.evaluasi2,edukasi_pasien_ranap.kd_edukasi3,edukasi_pasien_ranap.edukasi3,edukasi_pasien_ranap.penerima3, "+
                    "edukasi_pasien_ranap.metode3,edukasi_pasien_ranap.frekuensi3,edukasi_pasien_ranap.evaluasi3,edukasi_pasien_ranap.kd_edukasi4,edukasi_pasien_ranap.edukasi4, "+
                    "edukasi_pasien_ranap.penerima4,edukasi_pasien_ranap.metode4,edukasi_pasien_ranap.frekuensi4,edukasi_pasien_ranap.evaluasi4, "+
                    "edukasi_pasien_ranap.kd_edukasi5,edukasi_pasien_ranap.edukasi5,edukasi_pasien_ranap.penerima5,edukasi_pasien_ranap.metode5, "+
                    "edukasi_pasien_ranap.frekuensi5,edukasi_pasien_ranap.evaluasi5,edukasi_pasien_ranap.kd_edukasi6,edukasi_pasien_ranap.edukasi6, "+
                    "edukasi_pasien_ranap.penerima6,edukasi_pasien_ranap.metode6,edukasi_pasien_ranap.frekuensi6,edukasi_pasien_ranap.evaluasi6, "+
                    "edukasi_pasien_ranap.kd_edukasi7,edukasi_pasien_ranap.edukasi7,edukasi_pasien_ranap.penerima7,edukasi_pasien_ranap.metode7, "+
                    "edukasi_pasien_ranap.frekuensi7,edukasi_pasien_ranap.evaluasi7,edukasi_pasien_ranap.kd_edukasi8,edukasi_pasien_ranap.edukasi8, "+
                    "edukasi_pasien_ranap.penerima8,edukasi_pasien_ranap.metode8,edukasi_pasien_ranap.frekuensi8,edukasi_pasien_ranap.evaluasi8, "+
                    "edukasi_pasien_ranap.kd_edukasi9,edukasi_pasien_ranap.edukasi9,edukasi_pasien_ranap.penerima9,edukasi_pasien_ranap.metode9, "+
                    "edukasi_pasien_ranap.frekuensi9,edukasi_pasien_ranap.evaluasi9,edukasi_pasien_ranap.kd_edukasi10,edukasi_pasien_ranap.edukasi10, "+
                    "edukasi_pasien_ranap.penerima10,edukasi_pasien_ranap.metode10,edukasi_pasien_ranap.frekuensi10,edukasi_pasien_ranap.evaluasi10, "+
                    "edukasi_pasien_ranap.edukasi_lainnya,edukasi_pasien_ranap.kolaborasi,edukasi_pasien_ranap.edukasi_kolaborasi, "+
                    "edukasi_pasien_ranap.penerima11,edukasi_pasien_ranap.metode11,edukasi_pasien_ranap.frekuensi11,edukasi_pasien_ranap.evaluasi11, "+
                    "edukasi_pasien_ranap.hubungan,edukasi_pasien_ranap.kedua,edukasi_pasien_ranap.acc_ep,edukasi_pasien_ranap.tte from edukasi_pasien_ranap inner join reg_periksa on edukasi_pasien_ranap.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on edukasi_pasien_ranap.nik=pegawai.nik "+
                    "where edukasi_pasien_ranap.tanggal_surat between ? and ? and edukasi_pasien_ranap.acc_ep like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and edukasi_pasien_ranap.pengkajian like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and edukasi_pasien_ranap.no_surat like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and pasien.nm_pasien like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and edukasi_pasien_ranap.nik like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and pegawai.nama like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and edukasi_pasien_ranap.nik like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and edukasi_pasien_ranap.edukasi1 like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and edukasi_pasien_ranap.edukasi2 like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and edukasi_pasien_ranap.edukasi3 like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and edukasi_pasien_ranap.edukasi4 like ? or "+
                    "edukasi_pasien_ranap.tanggal_surat between ? and ? and edukasi_pasien_ranap.no_rawat like ? "+
                    "order by edukasi_pasien_ranap.tanggal_surat");
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
                        rs.getString("jam"),rs.getString("tanggal_pengkajian"),rs.getString("jam_pengkajian"),rs.getString("nik"),rs.getString("nama"),rs.getString("pengkajian"),
                        rs.getString("pengkajian_lainnya"),rs.getString("bicara"),rs.getString("membaca"),rs.getString("penerjemah"),rs.getString("isyarat"),rs.getString("kepercayaan"),
                        rs.getString("pengetahuan"),rs.getString("budaya"),rs.getString("merokok"),rs.getString("alkohol"),rs.getString("informasi"),rs.getString("alasan"),rs.getString("rencana"),rs.getString("jelaskan"),rs.getString("profesi"),
                        rs.getString("kd_edukasi1"),rs.getString("edukasi1"),rs.getString("penerima1"),rs.getString("metode1"),rs.getString("frekuensi1"),rs.getString("evaluasi1"),
                        rs.getString("kd_edukasi2"),rs.getString("edukasi2"),rs.getString("penerima2"),rs.getString("metode2"),rs.getString("frekuensi2"),rs.getString("evaluasi2"),
                        rs.getString("kd_edukasi3"),rs.getString("edukasi3"),rs.getString("penerima3"),rs.getString("metode3"),rs.getString("frekuensi3"),rs.getString("evaluasi3"),
                        rs.getString("kd_edukasi4"),rs.getString("edukasi4"),rs.getString("penerima4"),rs.getString("metode4"),rs.getString("frekuensi4"),rs.getString("evaluasi4"),
                        rs.getString("kd_edukasi5"),rs.getString("edukasi5"),rs.getString("penerima5"),rs.getString("metode5"),rs.getString("frekuensi5"),rs.getString("evaluasi5"),
                        rs.getString("kd_edukasi6"),rs.getString("edukasi6"),rs.getString("penerima6"),rs.getString("metode6"),rs.getString("frekuensi6"),rs.getString("evaluasi6"),
                        rs.getString("kd_edukasi7"),rs.getString("edukasi7"),rs.getString("penerima7"),rs.getString("metode7"),rs.getString("frekuensi7"),rs.getString("evaluasi7"),
                        rs.getString("kd_edukasi8"),rs.getString("edukasi8"),rs.getString("penerima8"),rs.getString("metode8"),rs.getString("frekuensi8"),rs.getString("evaluasi8"),
                        rs.getString("kd_edukasi9"),rs.getString("edukasi9"),rs.getString("penerima9"),rs.getString("metode9"),rs.getString("frekuensi9"),rs.getString("evaluasi9"),
                        rs.getString("kd_edukasi10"),rs.getString("edukasi10"),rs.getString("penerima10"),rs.getString("metode10"),rs.getString("frekuensi10"),rs.getString("evaluasi10"),
                        rs.getString("edukasi_lainnya"),rs.getString("kolaborasi"),rs.getString("edukasi_kolaborasi"),rs.getString("penerima11"),rs.getString("metode11"),
                        rs.getString("frekuensi11"),rs.getString("evaluasi11"),rs.getString("hubungan"),rs.getString("kedua"),rs.getString("acc_ep")
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
//        NoSurat.setText("");
//        TNoRw.setText("");
//        KodeDokter.setText("");
        Pengkajian.setSelectedIndex(0);
        PengkajianLainnya.setText("");
        Bicara.setSelectedIndex(0);
        Membaca.setSelectedIndex(0);
        Penerjemah.setSelectedIndex(0);
        Isyarat.setSelectedIndex(0);
        Kepercayaan.setSelectedIndex(0);
        Pengetahuan.setSelectedIndex(0);
        Budaya.setSelectedIndex(0);
        Merokok.setSelectedIndex(0);
        Alkohol.setSelectedIndex(0);
        Informasi.setSelectedIndex(0);
        Alasan.setText("");
        Rencana.setSelectedIndex(0);
        Jelaskan.setText("");
        Profesi.setSelectedIndex(0);
        Kode1.setText("");
        Edukasi1.setText("");
        Penerima1.setSelectedIndex(0);
        Metode1.setSelectedIndex(0);
        Frekuensi1.setSelectedIndex(0);
        Evaluasi1.setSelectedIndex(0);
        Kode2.setText("");
        Edukasi2.setText("");
        Penerima2.setSelectedIndex(0);
        Metode2.setSelectedIndex(0);
        Frekuensi2.setSelectedIndex(0);
        Evaluasi2.setSelectedIndex(0);
        Kode3.setText("");
        Edukasi3.setText("");
        Penerima3.setSelectedIndex(0);
        Metode3.setSelectedIndex(0);
        Frekuensi3.setSelectedIndex(0);
        Evaluasi3.setSelectedIndex(0);
        Kode4.setText("");
        Edukasi4.setText("");
        Penerima4.setSelectedIndex(0);
        Metode4.setSelectedIndex(0);
        Frekuensi4.setSelectedIndex(0);
        Evaluasi4.setSelectedIndex(0);
        Kode5.setText("");
        Edukasi5.setText("");
        Penerima5.setSelectedIndex(0);
        Metode5.setSelectedIndex(0);
        Frekuensi5.setSelectedIndex(0);
        Evaluasi5.setSelectedIndex(0);
        Kode6.setText("");
        Edukasi6.setText("");
        Penerima6.setSelectedIndex(0);
        Metode6.setSelectedIndex(0);
        Frekuensi6.setSelectedIndex(0);
        Evaluasi6.setSelectedIndex(0);
        Kode7.setText("");
        Edukasi7.setText("");
        Penerima7.setSelectedIndex(0);
        Metode7.setSelectedIndex(0);
        Frekuensi7.setSelectedIndex(0);
        Evaluasi7.setSelectedIndex(0);
        Kode8.setText("");
        Edukasi8.setText("");
        Penerima8.setSelectedIndex(0);
        Metode8.setSelectedIndex(0);
        Frekuensi8.setSelectedIndex(0);
        Evaluasi8.setSelectedIndex(0);
        Kode9.setText("");
        Edukasi9.setText("");
        Penerima9.setSelectedIndex(0);
        Metode9.setSelectedIndex(0);
        Frekuensi9.setSelectedIndex(0);
        Evaluasi9.setSelectedIndex(0);
        Kode10.setText("");
        Edukasi10.setText("");
        Penerima10.setSelectedIndex(0);
        Metode10.setSelectedIndex(0);
        Frekuensi10.setSelectedIndex(0);
        Evaluasi10.setSelectedIndex(0);
        EdukasiLainnya.setText("");
        Kode11.setText("");
        Edukasi11.setText("");
        Penerima11.setSelectedIndex(0);
        Metode11.setSelectedIndex(0);
        Frekuensi11.setSelectedIndex(0);
        Evaluasi11.setSelectedIndex(0);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from edukasi_pasien_ranap where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
                "EPRI"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),4,NoSurat);
//        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0) from surat_ic_rajal where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
//                "ICRJ"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),3,NoSurat); 
        NoSurat.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());  
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());  
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Valid.SetTgl(TanggalSurat,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            KodeDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());  
            NamaDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Pengkajian.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            PengkajianLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Bicara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Membaca.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Penerjemah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Isyarat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Kepercayaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Pengetahuan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Budaya.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Merokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Alkohol.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Informasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Alasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Rencana.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Jelaskan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Profesi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Kode1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Edukasi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Penerima1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Metode1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Frekuensi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Evaluasi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Kode2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Edukasi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Penerima2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Metode2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Frekuensi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Evaluasi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Kode3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Edukasi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Penerima3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Metode3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Frekuensi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Evaluasi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Kode4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Edukasi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Penerima4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Metode4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Frekuensi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Evaluasi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Kode5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Edukasi5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Penerima5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Metode5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Frekuensi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Evaluasi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            Kode6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            Edukasi6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Penerima6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            Metode6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            Frekuensi6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            Evaluasi6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            Kode7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            Edukasi7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            Penerima7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            Metode7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            Frekuensi7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            Evaluasi7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            Kode8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            Edukasi8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            Penerima8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            Metode8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            Frekuensi8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            Evaluasi8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            Kode9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            Edukasi9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            Penerima9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            Metode9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            Frekuensi9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            Evaluasi9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            Kode10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            Edukasi10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            Penerima10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            Metode10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            Frekuensi10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            Evaluasi10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            EdukasiLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            Kode11.setText(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            Edukasi11.setText(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            Penerima11.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            Metode11.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            Frekuensi11.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            Evaluasi11.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),93).toString());
            Kebutuhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),94).toString());
            accep.setText(tbObat.getValueAt(tbObat.getSelectedRow(),95).toString());
            panggilPhoto();
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
                    Agama.setText(rs.getString("agama"));
                    Bahasa.setText(rs.getString("nama_bahasa"));
                    Pendidikan.setText(rs.getString("pnd"));
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
        BtnDokter1.setVisible(false);
        BtnDokter2.setVisible(false);
        BtnDokter3.setVisible(false);
        BtnDokter4.setVisible(false);
        BtnDokter5.setVisible(false);
        BtnDokter6.setVisible(false);
        BtnDokter7.setVisible(false);
        BtnDokter8.setVisible(false);
        BtnDokter9.setVisible(false);
        BtnDokter10.setVisible(false);
        BtnPerawat1.setVisible(false);
        BtnPerawat2.setVisible(false);
        BtnPerawat3.setVisible(false);
        BtnPerawat4.setVisible(false);
        BtnPerawat5.setVisible(false);
        BtnPerawat6.setVisible(false);
        BtnPerawat7.setVisible(false);
        BtnPerawat8.setVisible(false);
        BtnPerawat9.setVisible(false);
        BtnPerawat10.setVisible(false);
        BtnFarmasi1.setVisible(false);
        BtnFarmasi2.setVisible(false);
        BtnFarmasi3.setVisible(false);
        BtnFarmasi4.setVisible(false);
        BtnFarmasi5.setVisible(false);
        BtnFarmasi6.setVisible(false);
        BtnFarmasi7.setVisible(false);
        BtnFarmasi8.setVisible(false);
        BtnFarmasi9.setVisible(false);
        BtnFarmasi10.setVisible(false);
        BtnNutrisionis1.setVisible(false);
        BtnNutrisionis2.setVisible(false);
        BtnNutrisionis3.setVisible(false);
        BtnNutrisionis4.setVisible(false);
        BtnNutrisionis5.setVisible(false);
        BtnNutrisionis6.setVisible(false);
        BtnNutrisionis7.setVisible(false);
        BtnNutrisionis8.setVisible(false);
        BtnNutrisionis9.setVisible(false);
        BtnNutrisionis10.setVisible(false);
        BtnLainnya1.setVisible(false);
        BtnLainnya2.setVisible(false);
        BtnLainnya3.setVisible(false);
        BtnLainnya4.setVisible(false);
        BtnLainnya5.setVisible(false);
        BtnLainnya6.setVisible(false);
        BtnLainnya7.setVisible(false);
        BtnLainnya8.setVisible(false);
        BtnLainnya9.setVisible(false);
        BtnLainnya10.setVisible(false);
        BtnSimpan.setEnabled(akses.gettindakan_ranap());
        BtnHapus.setEnabled(akses.gettindakan_ranap());
        BtnEdit.setEnabled(akses.gettindakan_ranap());
        BtnPrint.setEnabled(akses.gettindakan_ranap()); 
//        MnInputDiagnosa.setEnabled(akses.getdiagnosa_pasien());   
//        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());   
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from edukasi_pasien_ranap where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
                "EPRI"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),4,NoSurat); 
        if(akses.getjml2()>=1){
            KodeDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KodeDokter.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", NamaDokter,KodeDokter.getText());
//            if(NmPetugas.getText().equals("")){
//                KdPetugas.setText("");
//                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
//            }
        }            
    }
    
    private void autoNumberX(String tglPilih) {
    Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from edukasi_pasien_ranap where tanggal_surat='"+Valid.SetTgl(tglPilih+"")+"' ",
                "EPRI"+Valid.SetTgl(tglPilih+"").replaceAll("-", ""),4,NoSurat);
    }
    
    private void panggilPhoto() {
//        if(FormPhotoPass.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select edukasi_pasien_ranap.tte from edukasi_pasien_ranap where edukasi_pasien_ranap.no_surat=?");
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

    
}
