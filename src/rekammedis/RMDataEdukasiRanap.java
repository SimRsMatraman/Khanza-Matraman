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
import java.util.ArrayList;
import java.util.List;


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
    private RMCariHakPasiendanKeluarga cariedukasiHakPasiendanKeluarga=new RMCariHakPasiendanKeluarga(null,false);
    private RMCariRohaniawan cariedukasiRohaniawan=new RMCariRohaniawan(null,false);
    private RMCariNilaiKepercayaan cariedukasiNilaiKepercayaan=new RMCariNilaiKepercayaan(null,false);
    private RMCariGelang cariGelang=new RMCariGelang(null,false);
    private RMCariManajemenNyeri cariManajemenNyeri=new RMCariManajemenNyeri(null,false);
    private RMCariManajemenResikoJatuh cariManajemenResikoJatuh=new RMCariManajemenResikoJatuh(null,false);
    private RMCariCuciTangan cariCuciTangan=new RMCariCuciTangan(null,false);
    private RMCariEdukasiLain cariEdukasiLain=new RMCariEdukasiLain(null,false);
    private javax.swing.JTextField[] txtKode;
    private javax.swing.JTextField[] txtEdukasi;
    private javax.swing.JTextArea[] txtPenerima;
    private javax.swing.JTextArea[] txtMedia;
    private javax.swing.JTextArea[] txtEvaluasi;
    private javax.swing.JTextArea[] txtEdukasiLainnya;
    private javax.swing.JTextField[] txtKolaborasi;
    private javax.swing.JTextField[] txtKode1;
    private javax.swing.JTextField[] txtEdukasi1;
    private javax.swing.JTextArea[] txtPenerima1;
    private javax.swing.JTextArea[] txtMedia1;
    private javax.swing.JTextArea[] txtEvaluasi1;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMDataEdukasiRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inisialisasiArray();

        // Event ComboBox Profesi
        Profesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampilDataEdukasi();
            }
        });
        
tabMode = new DefaultTableModel(null, new Object[]{
    "No Surat",
    "No Rawat",
    "Nama Pasien",
    "Tanggal Surat",
    "NIK",
    "Nama",
    "Hambatan Edukasi",
    "Hambatan Lain",
    "Bicara",
    "Membaca",
    "Bahasa Sehari-hari",
    "Penerjemah",
    "Budaya",
    "Bahasa Isyarat",
    "Pendidikan",
    "Agama",
    "Tingkat Pengetahuan",
    "Hambatan Emosional",
    "Hambatan Motivasi",
    "Ketersediaan Pasien",
    "Alasan Kesediaan",
    "Kebutuhan Edukasi",
    "Hubungan",
    "ACC"
}) {
    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        return false;
    }
};

tbObat.setModel(tabMode);
tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

for (int i = 0; i < tbObat.getColumnCount(); i++) {
    tbObat.getColumnModel().getColumn(i).setPreferredWidth(150);
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
//                    }else if(i==6){
//                        Kode6.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
//                        Edukasi6.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==7){
//                        Kode7.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
//                        Edukasi7.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==8){
//                        Kode8.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
//                        Edukasi8.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==9){
//                        Kode9.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
//                        Edukasi9.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==10){
//                        Kode10.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),0).toString());
//                        Edukasi10.setText(cariedukasidokter.getTable().getValueAt(cariedukasidokter.getTable().getSelectedRow(),1).toString());  
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
//                        Kode6.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
//                        Edukasi6.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==7){
//                        Kode7.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
//                        Edukasi7.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==8){
//                        Kode8.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
//                        Edukasi8.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==9){
//                        Kode9.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
//                        Edukasi9.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==10){
//                        Kode10.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),0).toString());
//                        Edukasi10.setText(cariedukasiperawat.getTable().getValueAt(cariedukasiperawat.getTable().getSelectedRow(),1).toString());  
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
//                        Kode6.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
//                        Edukasi6.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==7){
//                        Kode7.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
//                        Edukasi7.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==8){
//                        Kode8.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
//                        Edukasi8.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==9){
//                        Kode9.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
//                        Edukasi9.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==10){
//                        Kode10.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),0).toString());
//                        Edukasi10.setText(cariedukasifarmasi.getTable().getValueAt(cariedukasifarmasi.getTable().getSelectedRow(),1).toString());  
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
//                    }else if(i==6){
//                        Kode6.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
//                        Edukasi6.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==7){
//                        Kode7.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
//                        Edukasi7.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==8){
//                        Kode8.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
//                        Edukasi8.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==9){
//                        Kode9.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
//                        Edukasi9.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==10){
//                        Kode10.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),0).toString());
//                        Edukasi10.setText(cariedukasinutrisionis.getTable().getValueAt(cariedukasinutrisionis.getTable().getSelectedRow(),1).toString());  
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
//                    }else if(i==6){
//                        Kode6.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
//                        Edukasi6.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==7){
//                        Kode7.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
//                        Edukasi7.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==8){
//                        Kode8.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
//                        Edukasi8.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==9){
//                        Kode9.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
//                        Edukasi9.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
//                    }else if(i==10){
//                        Kode10.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),0).toString());
//                        Edukasi10.setText(cariedukasirehabmedik.getTable().getValueAt(cariedukasirehabmedik.getTable().getSelectedRow(),1).toString());  
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
        jLabel34 = new widget.Label();
        Pengetahuan = new widget.ComboBox();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        Informasi = new widget.ComboBox();
        Alasan = new widget.TextBox();
        jLabel40 = new widget.Label();
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
        jLabel49 = new widget.Label();
        Evaluasi1 = new widget.ComboBox();
        jLabel50 = new widget.Label();
        Penerima1 = new widget.ComboBox();
        jLabel51 = new widget.Label();
        Edukasi2 = new widget.TextBox();
        Kode2 = new widget.TextBox();
        Metode2 = new widget.ComboBox();
        Evaluasi2 = new widget.ComboBox();
        Penerima2 = new widget.ComboBox();
        jLabel52 = new widget.Label();
        Edukasi3 = new widget.TextBox();
        Kode3 = new widget.TextBox();
        Metode3 = new widget.ComboBox();
        Evaluasi3 = new widget.ComboBox();
        Penerima3 = new widget.ComboBox();
        jLabel53 = new widget.Label();
        Edukasi4 = new widget.TextBox();
        Kode4 = new widget.TextBox();
        Metode4 = new widget.ComboBox();
        Evaluasi4 = new widget.ComboBox();
        Penerima4 = new widget.ComboBox();
        jLabel54 = new widget.Label();
        Edukasi5 = new widget.TextBox();
        Kode5 = new widget.TextBox();
        Metode5 = new widget.ComboBox();
        Evaluasi5 = new widget.ComboBox();
        Penerima5 = new widget.ComboBox();
        jLabel59 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        EdukasiLainnya = new widget.TextArea();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel61 = new widget.Label();
        Kode11 = new widget.TextBox();
        Edukasi11 = new widget.TextBox();
        Penerima11 = new widget.ComboBox();
        Metode11 = new widget.ComboBox();
        Evaluasi11 = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel62 = new widget.Label();
        Budaya = new widget.TextBox();
        jLabel69 = new widget.Label();
        Informasi1 = new widget.ComboBox();
        jLabel70 = new widget.Label();
        Informasi2 = new widget.ComboBox();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Edukasi Pasien/Keluarga Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-01-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-01-2026" }));
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
        TCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariActionPerformed(evt);
            }
        });
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
        jLabel12.setBounds(810, 80, 150, 23);

        jLabel13.setText("Tanggal Surat :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(630, 40, 90, 23);

        TanggalSurat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-01-2026" }));
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
        FormInput.add(NoSurat);
        NoSurat.setBounds(870, 10, 170, 23);

        Pengkajian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tidak Ada", "Bahasa", "Gangguan Biasa", "Penglihatan Terganggu", "Budaya", "Kognotif Terbatas", "Pendengaran Terganggu", "Fisik Lemah", "Lain-lain" }));
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

        jLabel24.setText("Hambatan Edukasi :");
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
        Kebutuhan.setBounds(970, 80, 120, 23);

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
        FormPhoto.setBounds(980, 150, 340, 280);

        jLabel14.setText("Hambatan Edukasi Lainnya :");
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
        Bahasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BahasaActionPerformed(evt);
            }
        });
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
        jLabel29.setBounds(70, 200, 110, 23);

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
        Penerjemah.setBounds(190, 200, 80, 23);

        jLabel30.setText("Bahasa Isyarat :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(90, 230, 90, 23);

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
        Isyarat.setBounds(190, 230, 80, 23);

        jLabel31.setText("Pendidikan :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(340, 200, 110, 23);

        Pendidikan.setEditable(false);
        Pendidikan.setFocusTraversalPolicyProvider(true);
        Pendidikan.setName("Pendidikan"); // NOI18N
        Pendidikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendidikanKeyPressed(evt);
            }
        });
        FormInput.add(Pendidikan);
        Pendidikan.setBounds(460, 200, 190, 23);

        jLabel32.setText("Agama :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(390, 230, 60, 23);

        Agama.setEditable(false);
        Agama.setFocusTraversalPolicyProvider(true);
        Agama.setName("Agama"); // NOI18N
        Agama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgamaKeyPressed(evt);
            }
        });
        FormInput.add(Agama);
        Agama.setBounds(460, 230, 190, 23);

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

        jLabel37.setText("Alasan :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(270, 290, 70, 23);

        jLabel38.setText("Kesediaan Menerima Informasi :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 290, 180, 23);

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
        Informasi.setBounds(190, 290, 70, 23);

        Alasan.setFocusTraversalPolicyProvider(true);
        Alasan.setName("Alasan"); // NOI18N
        Alasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanKeyPressed(evt);
            }
        });
        FormInput.add(Alasan);
        Alasan.setBounds(350, 290, 380, 23);

        jLabel40.setText("Kebutuhan Edukasi :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 320, 180, 23);

        Jelaskan.setFocusTraversalPolicyProvider(true);
        Jelaskan.setName("Jelaskan"); // NOI18N
        Jelaskan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JelaskanKeyPressed(evt);
            }
        });
        FormInput.add(Jelaskan);
        Jelaskan.setBounds(190, 320, 540, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 390, 880, 1);

        jLabel42.setText("INPUT EDUKASI KOLABORASI");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 970, 170, 23);

        Profesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Hak Pasien dan Keluarga", "Rohaniawan", "Nilai-nilai Kepercayaan", "Gelang Identitas/Gelang Resiko", "Dokter Spesialis/Dokter Umum", "Manajemen Nyeri", "Rehabilitasi Medik", "Manajemen Resiko Jatuh", "Cuci Tangan", "Nutrisi", "Farmasi", "Penggunaan Alat Kesehatan", "Lainnya" }));
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
        Profesi.setBounds(120, 430, 310, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("MEDIA EDUKASI");
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

        Metode1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Leaflet", "Pamflet", "Lembar Balik", "AudioVisual" }));
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

        jLabel45.setText("Topik Edukasi :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(40, 430, 80, 23);

        jLabel46.setText("KODE");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(120, 460, 60, 23);

        jLabel47.setText("EDUKASI");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(370, 460, 60, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("EVALUASI");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(1010, 460, 100, 23);

        Evaluasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mengerti", "Re Demonstrasi", "Re Edukasi" }));
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
        Evaluasi1.setBounds(930, 490, 260, 23);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("METODE EDUKASI");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(640, 460, 120, 23);

        Penerima1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ceramah", "Demontrasi", "Diskusi", "Observasi", "Simulasi" }));
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

        Metode2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Leaflet", "Pamflet", "Lembar Balik", "AudioVisual" }));
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

        Evaluasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mengerti", "Re Demonstrasi", "Re EdukasiSimulasi" }));
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
        Evaluasi2.setBounds(930, 560, 260, 23);

        Penerima2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ceramah", "Demontrasi", "Diskusi", "Observasi", "Simulasi" }));
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

        Metode3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Leaflet", "Pamflet", "Lembar Balik", "AudioVisual" }));
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

        Evaluasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mengerti", "Re Demonstrasi", "Re Edukasi" }));
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
        Evaluasi3.setBounds(930, 630, 260, 23);

        Penerima3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ceramah", "Demontrasi", "Diskusi", "Observasi", "Simulasi" }));
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

        Metode4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Leaflet", "Pamflet", "Lembar Balik", "AudioVisual" }));
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

        Evaluasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mengerti", "Re Demonstrasi", "Re Edukasi" }));
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
        Evaluasi4.setBounds(930, 700, 260, 23);

        Penerima4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ceramah", "Demontrasi", "Diskusi", "Observasi", "Simulasi" }));
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

        Metode5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Leaflet", "Pamflet", "Lembar Balik", "AudioVisual" }));
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

        Evaluasi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mengerti", "Re Demonstrasi", "Re Edukasi" }));
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
        Evaluasi5.setBounds(930, 770, 260, 23);

        Penerima5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ceramah", "Demontrasi", "Diskusi", "Observasi", "Simulasi" }));
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

        jLabel59.setText("Edukasi Lainnya :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 850, 110, 23);

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
        scrollPane1.setBounds(130, 850, 500, 90);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 960, 880, 1);

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
        Kode11.setBounds(20, 1040, 200, 23);

        Edukasi11.setFocusTraversalPolicyProvider(true);
        Edukasi11.setName("Edukasi11"); // NOI18N
        Edukasi11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi11KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi11);
        Edukasi11.setBounds(220, 1040, 400, 23);

        Penerima11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ceramah", "Demontrasi", "Diskusi", "Observasi", "Simulasi" }));
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
        Penerima11.setBounds(630, 1040, 130, 23);

        Metode11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Leaflet", "Pamflet", "Lembar Balik", "AudioVisual" }));
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
        Metode11.setBounds(770, 1040, 130, 23);

        Evaluasi11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mengerti", "Re Demonstrasi", "Re Edukasi" }));
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
        Evaluasi11.setBounds(920, 1040, 260, 23);

        jLabel63.setText("KOLABORASI DENGAN PROFESI");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(30, 1010, 170, 23);

        jLabel64.setText("EDUKASI");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(380, 1010, 60, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("METODE EDUKASI");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(630, 1010, 120, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("MEDIA EDUKASI");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(770, 1010, 120, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setText("EVALUASI");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(1010, 1010, 100, 23);

        jLabel62.setText("Budaya / Suku :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(370, 170, 80, 23);

        Budaya.setEditable(false);
        Budaya.setFocusTraversalPolicyProvider(true);
        Budaya.setName("Budaya"); // NOI18N
        Budaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BudayaKeyPressed(evt);
            }
        });
        FormInput.add(Budaya);
        Budaya.setBounds(460, 170, 190, 23);

        jLabel69.setText("Hambatan Emosional :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(410, 260, 120, 23);

        Informasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Informasi1.setName("Informasi1"); // NOI18N
        Informasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Informasi1ActionPerformed(evt);
            }
        });
        Informasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi1KeyPressed(evt);
            }
        });
        FormInput.add(Informasi1);
        Informasi1.setBounds(540, 260, 70, 23);

        jLabel70.setText("Hambatan Motivasi :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(610, 260, 110, 23);

        Informasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Informasi2.setName("Informasi2"); // NOI18N
        Informasi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Informasi2ActionPerformed(evt);
            }
        });
        Informasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi2KeyPressed(evt);
            }
        });
        FormInput.add(Informasi2);
        Informasi2.setBounds(730, 260, 70, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
                                         
    if (TNoRw.getText().equals("") || NoSurat.getText().equals("")) {
        JOptionPane.showMessageDialog(null, "Data belum lengkap !");
        return;
    }

    try {

        String tanggalSurat = Valid.SetTgl(TanggalSurat.getSelectedItem()+"");
        String jamSekarang  = jamNow.format(new Date());

        String sql = "INSERT INTO edukasi_pasien (" +
                "no_surat,no_rawat,tanggal_surat,jam," +
                "tanggal_pengkajian,jam_pengkajian," +
                "hambatan_edukasi,hambatan_edukasi_lain,nik," +
                "bicara,kemampuan_membaca,bahasa_sehari,penerjemah,budaya,bahasa_isyarat," +
                "pendidikan,agama,tingkat_pengetahuan,hambatan_emosional,hambatan_motivasi," +
                "ketersediaan_pasiein,alasan_kesediaan,kebutuhan_edukasi," +

                "Nama_kel,hubungan," +

                "acc_hpk,acc_roh,acc_kep,acc_gel,acc_dok,acc_nye,acc_med,acc_res,acc_cuc,acc_nut,acc_far,acc_kes,acc_kol,acc_pen," +

                // semua tte, nik, metode, media, evaluasi, nm kita kosongkan
                "tte_hpk_sasaran,tte_hpk_edukator,nik_hpk_edukator," +
                "tte_roh_sasaran,tte_roh_edukator,nik_roh_edukator," +
                "tte_kep_sasaran,tte_kep_edukator,nik_kep_edukator," +
                "tte_gel_sasaran,tte_gel_edukator,nik_gel_edukator," +
                "tte_dok_sasaran,tte_dok_edukator,nik_dok_edukator," +
                "tte_nye_sasaran,tte_nye_edukator,nik_nye_edukator," +
                "tte_med_sasaran,tte_med_edukator,nik_med_edukator," +
                "tte_res_sasaran,tte_res_edukator,nik_res_edukator," +
                "tte_cuc_sasaran,tte_cuc_edukator,nik_cuc_edukator," +
                "tte_nut_sasaran,tte_nut_edukator,nik_nut_edukator," +
                "tte_far_sasaran,tte_far_edukator,nik_far_edukator," +
                "tte_kes_sasaran,tte_kes_edukator,nik_kes_edukator," +
                "tte_kol_sasaran,tte_kol_edukator,nik_kol_edukator," +
                "tte_pen_sasaran,tte_pen_edukator,nik_pen_edukator," +

                // semua tanggal acc
                "tgl_hpk,tgl_roh,tgl_kep,tgl_gel,tgl_dok,tgl_nye,tgl_med,tgl_res,tgl_cuc,tgl_nut,tgl_far,tgl_kes,tgl_kol,tgl_pen," +

                "status" +

                ") VALUES (" +

                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +  // sampai kebutuhan_edukasi

                "'-','-'," +  // Nama_kel, hubungan

                "'-','-','-','-','-','-','-','-','-','-','-','-','-','-'," + // acc_*

                // semua tte, nik kosong
                "'','',''," +  // hpk
                "'','',''," +  // roh
                "'','',''," +  // kep
                "'','',''," +
                "'','',''," +
                "'','',''," +
                "'','',''," +
                "'','',''," +
                "'','',''," +
                "'','',''," +
                "'','',''," +
                "'','',''," +
                "'','',''," +
                "'','',''," +

                // semua tgl 0000
                "'0000-00-00','0000-00-00','0000-00-00','0000-00-00'," +
                "'0000-00-00','0000-00-00','0000-00-00','0000-00-00'," +
                "'0000-00-00','0000-00-00','0000-00-00','0000-00-00','0000-00-00','0000-00-00'," +

                "0" + // status
                ")";

        PreparedStatement ps = koneksi.prepareStatement(sql);

        int i = 1;

        ps.setString(i++, NoSurat.getText());
        ps.setString(i++, TNoRw.getText());
        ps.setString(i++, tanggalSurat);
        ps.setString(i++, jamSekarang);

        ps.setString(i++, tanggalSurat);
        ps.setString(i++, jamSekarang);

        ps.setString(i++, Pengkajian.getSelectedItem().toString());
        ps.setString(i++, PengkajianLainnya.getText());
        ps.setString(i++, KodeDokter.getText());

        ps.setString(i++, Bicara.getSelectedItem().toString());
        ps.setString(i++, Membaca.getSelectedItem().toString());
        ps.setString(i++, Bahasa.getText());
        ps.setString(i++, Penerjemah.getSelectedItem().toString());
        ps.setString(i++, Budaya.getText());
        ps.setString(i++, Isyarat.getSelectedItem().toString());

        ps.setString(i++, Pendidikan.getText());
        ps.setString(i++, Agama.getText());
        ps.setString(i++, Pengetahuan.getSelectedItem().toString());
        ps.setString(i++, Informasi1.getSelectedItem().toString());
        ps.setString(i++, Informasi2.getSelectedItem().toString());
        ps.setString(i++, Informasi.getSelectedItem().toString());

        ps.setString(i++, Alasan.getText());
        ps.setString(i++, Jelaskan.getText());

        ps.executeUpdate();
        ps.close();

        tampil();
        emptTeks();

        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error : "+e);
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
            if(Sequel.queryu2tf("delete from edukasi_pasien where no_surat=?",1,new String[]{
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
//        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
//            Valid.textKosong(TNoRw,"Pasien");
//        }else if(KodeDokter.getText().equals("")||NamaDokter.getText().equals("")){
//            Valid.textKosong(BtnDokter,"Nama Pengkaji");
//        }else if(Kode1.getText().equals("")){
//            Valid.textKosong(Kode1,"Kode Edukasi 1");
//        }else if(Edukasi1.getText().equals("")){
//            Valid.textKosong(Edukasi1,"Edukasi 1");
//        }else{
//            if(tbObat.getSelectedRow()>-1){
//                if(Sequel.mengedittf("edukasi_pasien_ranap","no_surat=?","no_surat=?,no_rawat=?,tanggal_surat=?,jam=?,tanggal_pengkajian=?,jam_pengkajian=?,nik=?,pengkajian=?,pengkajian_lainnya=?,bicara=?,membaca=?,penerjemah=?,isyarat=?,kepercayaan=?,pengetahuan=?,budaya=?,merokok=?,alkohol=?,informasi=?,alasan=?,rencana=?,jelaskan=?,profesi=?,kd_edukasi1=?,edukasi1=?,penerima1=?,metode1=?,frekuensi1=?,evaluasi1=?,kd_edukasi2=?,edukasi2=?,penerima2=?,metode2=?,frekuensi2=?,evaluasi2=?,kd_edukasi3=?,edukasi3=?,penerima3=?,metode3=?,frekuensi3=?,evaluasi3=?,kd_edukasi4=?,edukasi4=?,penerima4=?,metode4=?,frekuensi4=?,evaluasi4=?,kd_edukasi5=?,edukasi5=?,penerima5=?,metode5=?,frekuensi5=?,evaluasi5=?,kd_edukasi6=?,edukasi6=?,penerima6=?,metode6=?,frekuensi6=?,evaluasi6=?,kd_edukasi7=?,edukasi7=?,penerima7=?,metode7=?,frekuensi7=?,evaluasi7=?,kd_edukasi8=?,edukasi8=?,penerima8=?,metode8=?,frekuensi8=?,evaluasi8=?,kd_edukasi9=?,edukasi9=?,penerima9=?,metode9=?,frekuensi9=?,evaluasi9=?,kd_edukasi10=?,edukasi10=?,penerima10=?,metode10=?,frekuensi10=?,evaluasi10=?,edukasi_lainnya=?,kolaborasi=?,edukasi_kolaborasi=?,penerima11=?,metode11=?,frekuensi11=?,evaluasi11=?,hubungan=?,kedua=?,acc_ep=?",94,new String[]{
//                        NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),jamNow.format(new Date()),
//                        KodeDokter.getText(),Pengkajian.getSelectedItem().toString(),PengkajianLainnya.getText(),Bicara.getSelectedItem().toString(),Membaca.getSelectedItem().toString(),
//                        Penerjemah.getSelectedItem().toString(),Isyarat.getSelectedItem().toString(),Kepercayaan.getSelectedItem().toString(),Pengetahuan.getSelectedItem().toString(),Budaya.getSelectedItem().toString(),Merokok.getSelectedItem().toString(),
//                        Alkohol.getSelectedItem().toString(),Informasi.getSelectedItem().toString(),Alasan.getText(),Rencana.getSelectedItem().toString(),Jelaskan.getText(),Profesi.getSelectedItem().toString(),
//                        Kode1.getText(),Edukasi1.getText(),Penerima1.getSelectedItem().toString(),Metode1.getSelectedItem().toString(),Frekuensi1.getSelectedItem().toString(),Evaluasi1.getSelectedItem().toString(),
//                        Kode2.getText(),Edukasi2.getText(),Penerima2.getSelectedItem().toString(),Metode2.getSelectedItem().toString(),Frekuensi2.getSelectedItem().toString(),Evaluasi2.getSelectedItem().toString(),
//                        Kode3.getText(),Edukasi3.getText(),Penerima3.getSelectedItem().toString(),Metode3.getSelectedItem().toString(),Frekuensi3.getSelectedItem().toString(),Evaluasi3.getSelectedItem().toString(),
//                        Kode4.getText(),Edukasi4.getText(),Penerima4.getSelectedItem().toString(),Metode4.getSelectedItem().toString(),Frekuensi4.getSelectedItem().toString(),Evaluasi4.getSelectedItem().toString(),
//                        Kode5.getText(),Edukasi5.getText(),Penerima5.getSelectedItem().toString(),Metode5.getSelectedItem().toString(),Frekuensi5.getSelectedItem().toString(),Evaluasi5.getSelectedItem().toString(),
//                        Kode6.getText(),Edukasi6.getText(),Penerima6.getSelectedItem().toString(),Metode6.getSelectedItem().toString(),Frekuensi6.getSelectedItem().toString(),Evaluasi6.getSelectedItem().toString(),
//                        Kode7.getText(),Edukasi7.getText(),Penerima7.getSelectedItem().toString(),Metode7.getSelectedItem().toString(),Frekuensi7.getSelectedItem().toString(),Evaluasi7.getSelectedItem().toString(),
//                        Kode8.getText(),Edukasi8.getText(),Penerima8.getSelectedItem().toString(),Metode8.getSelectedItem().toString(),Frekuensi8.getSelectedItem().toString(),Evaluasi8.getSelectedItem().toString(),
//                        Kode9.getText(),Edukasi9.getText(),Penerima9.getSelectedItem().toString(),Metode9.getSelectedItem().toString(),Frekuensi9.getSelectedItem().toString(),Evaluasi9.getSelectedItem().toString(),
//                        Kode10.getText(),Edukasi10.getText(),Penerima10.getSelectedItem().toString(),Metode10.getSelectedItem().toString(),Frekuensi10.getSelectedItem().toString(),Evaluasi10.getSelectedItem().toString(),
//                        EdukasiLainnya.getText(),Kode11.getText(),Edukasi11.getText(),Penerima11.getSelectedItem().toString(),Metode11.getSelectedItem().toString(),Frekuensi11.getSelectedItem().toString(),Evaluasi11.getSelectedItem().toString(),
//                        Hubungan.getText(),Kebutuhan.getText(),accep.getText(),
////                        tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
//                        NoSurat.getText()
//                    })==true){
//                       tampil();
//                       emptTeks();
//                }
if (NoSurat.getText().equals("")) {
        JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit !");
        return;
    }

    String tanggalSurat = Valid.SetTgl(TanggalSurat.getSelectedItem()+"");
    String tanggalPengkajian = Valid.SetTgl(TanggalSurat.getSelectedItem()+"");
    String jamSekarang = jamNow.format(new Date());

    if (Sequel.mengedittf(
        "edukasi_pasien",
        "no_surat=?",
        "no_rawat=?,tanggal_surat=?,jam=?,tanggal_pengkajian=?,jam_pengkajian=?,"
      + "hambatan_edukasi=?,hambatan_edukasi_lain=?,nik=?,bicara=?,"
      + "kemampuan_membaca=?,bahasa_sehari=?,penerjemah=?,budaya=?,"
      + "bahasa_isyarat=?,pendidikan=?,agama=?,tingkat_pengetahuan=?,"
      + "hambatan_emosional=?,hambatan_motivasi=?,ketersediaan_pasiein=?,"
      + "alasan_kesediaan=?,kebutuhan_edukasi=?,status=?",
        24,
        new String[]{

            TNoRw.getText(),
            tanggalSurat,
            jamSekarang,
            tanggalPengkajian,
            jamSekarang,

            Pengkajian.getSelectedItem().toString(),
            PengkajianLainnya.getText(),
            KodeDokter.getText(),
            Bicara.getSelectedItem().toString(),
            Membaca.getSelectedItem().toString(),
            Bahasa.getText(),
            Penerjemah.getSelectedItem().toString(),
            Budaya.getText(),
            Isyarat.getSelectedItem().toString(),
            Pendidikan.getText(),
            Agama.getText(),
            Pengetahuan.getSelectedItem().toString(),
            Informasi1.getSelectedItem().toString(),
            Informasi2.getSelectedItem().toString(),
            Informasi.getSelectedItem().toString(),
            Alasan.getText(),
            Jelaskan.getText(),
            "0",

            // WHERE
            NoSurat.getText()
        }

    )) {
        tampil();
        emptTeks();
        JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
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

    private void Informasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi2KeyPressed

    private void Informasi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Informasi2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi2ActionPerformed

    private void Informasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi1KeyPressed

    private void Informasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Informasi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi1ActionPerformed

    private void BudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BudayaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BudayaKeyPressed

    private void Evaluasi11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi11KeyPressed

    private void Evaluasi11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi11ActionPerformed

    private void Metode11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode11KeyPressed

    private void Metode11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode11ActionPerformed

    private void Penerima11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima11KeyPressed

    private void Penerima11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima11ActionPerformed

    private void Edukasi11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi11KeyPressed

    private void Kode11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode11KeyPressed

    private void EdukasiLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiLainnyaKeyPressed
        //        Valid.pindah(evt,BMI,RPK);
    }//GEN-LAST:event_EdukasiLainnyaKeyPressed

    private void Penerima5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima5KeyPressed

    private void Penerima5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima5ActionPerformed

    private void Evaluasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi5KeyPressed

    private void Evaluasi5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi5ActionPerformed

    private void Metode5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode5KeyPressed

    private void Metode5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode5ActionPerformed

    private void Kode5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode5KeyPressed

    private void Edukasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi5KeyPressed

    private void Penerima4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima4KeyPressed

    private void Penerima4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima4ActionPerformed

    private void Evaluasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi4KeyPressed

    private void Evaluasi4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi4ActionPerformed

    private void Metode4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode4KeyPressed

    private void Metode4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode4ActionPerformed

    private void Kode4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode4KeyPressed

    private void Edukasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi4KeyPressed

    private void Penerima3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima3KeyPressed

    private void Penerima3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima3ActionPerformed

    private void Evaluasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi3KeyPressed

    private void Evaluasi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi3ActionPerformed

    private void Metode3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode3KeyPressed

    private void Metode3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode3ActionPerformed

    private void Kode3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode3KeyPressed

    private void Edukasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi3KeyPressed

    private void Penerima2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima2KeyPressed

    private void Penerima2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima2ActionPerformed

    private void Evaluasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi2KeyPressed

    private void Evaluasi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi2ActionPerformed

    private void Metode2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode2KeyPressed

    private void Metode2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode2ActionPerformed

    private void Kode2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode2KeyPressed

    private void Edukasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi2KeyPressed

    private void Penerima1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penerima1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima1KeyPressed

    private void Penerima1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Penerima1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Penerima1ActionPerformed

    private void Evaluasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi1KeyPressed

    private void Evaluasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Evaluasi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Evaluasi1ActionPerformed

    private void Metode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode1KeyPressed

    private void Metode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Metode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Metode1ActionPerformed

    private void Kode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kode1KeyPressed

    private void Edukasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi1KeyPressed

    private void ProfesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProfesiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProfesiKeyPressed

    private void ProfesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfesiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProfesiActionPerformed

    private void ProfesiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ProfesiItemStateChanged
        if(Profesi.getSelectedIndex()==0){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis1.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya1.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==1){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(true);
//            BtnHakPasienDanKeluarga1.setVisible(true);
//            BtnHakPasienDanKeluarga2.setVisible(true);
//            BtnHakPasienDanKeluarga3.setVisible(true);
//            BtnHakPasienDanKeluarga4.setVisible(true);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya1.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==2){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(true);
//            BtnRohaniawan1.setVisible(true);
//            BtnRohaniawan2.setVisible(true);
//            BtnRohaniawan3.setVisible(true);
//            BtnRohaniawan4.setVisible(true);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==3){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(true);
//            BtnNilaiKepercayaan1.setVisible(true);
//            BtnNilaiKepercayaan2.setVisible(true);
//            BtnNilaiKepercayaan3.setVisible(true);
//            BtnNilaiKepercayaan4.setVisible(true);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==4){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(true);
//            BtnGelang1.setVisible(true);
//            BtnGelang2.setVisible(true);
//            BtnGelang3.setVisible(true);
//            BtnGelang4.setVisible(true);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==5){
//            BtnDokter1.setVisible(true);
//            BtnDokter2.setVisible(true);
//            BtnDokter3.setVisible(true);
//            BtnDokter4.setVisible(true);
//            BtnDokter5.setVisible(true);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==6){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(true);
//            BtnManajemenNyeri1.setVisible(true);
//            BtnManajemenNyeri2.setVisible(true);
//            BtnManajemenNyeri3.setVisible(true);
//            BtnManajemenNyeri4.setVisible(true);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==7){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(true);
//            BtnRehabMedik1.setVisible(true);
//            BtnRehabMedik2.setVisible(true);
//            BtnRehabMedik3.setVisible(true);
//            BtnRehabMedik4.setVisible(true);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==8){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(true);
//            BtnManajemenResikoJatuh1.setVisible(true);
//            BtnManajemenResikoJatuh2.setVisible(true);
//            BtnManajemenResikoJatuh3.setVisible(true);
//            BtnManajemenResikoJatuh4.setVisible(true);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==9){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(true);
//            BtnCuciTangan1.setVisible(true);
//            BtnCuciTangan2.setVisible(true);
//            BtnCuciTangan3.setVisible(true);
//            BtnCuciTangan4.setVisible(true);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==10){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi1.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(true);
//            BtnNutrisionis2.setVisible(true);
//            BtnNutrisionis3.setVisible(true);
//            BtnNutrisionis4.setVisible(true);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==11){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(true);
//            BtnFarmasi1.setVisible(true);
//            BtnFarmasi2.setVisible(true);
//            BtnFarmasi3.setVisible(true);
//            BtnFarmasi4.setVisible(true);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==12){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
        }else if(Profesi.getSelectedIndex()==13){
//            BtnDokter1.setVisible(false);
//            BtnDokter2.setVisible(false);
//            BtnDokter3.setVisible(false);
//            BtnDokter4.setVisible(false);
//            BtnDokter5.setVisible(false);
//            BtnFarmasi.setVisible(false);
//            BtnFarmasi2.setVisible(false);
//            BtnFarmasi3.setVisible(false);
//            BtnFarmasi4.setVisible(false);
//            BtnNutrisionis.setVisible(false);
//            BtnNutrisionis2.setVisible(false);
//            BtnNutrisionis3.setVisible(false);
//            BtnNutrisionis4.setVisible(false);
//            BtnRehabMedik.setVisible(false);
//            BtnRehabMedik1.setVisible(false);
//            BtnRehabMedik2.setVisible(false);
//            BtnRehabMedik3.setVisible(false);
//            BtnRehabMedik4.setVisible(false);
//            BtnHakPasienDanKeluarga.setVisible(false);
//            BtnHakPasienDanKeluarga1.setVisible(false);
//            BtnHakPasienDanKeluarga2.setVisible(false);
//            BtnHakPasienDanKeluarga3.setVisible(false);
//            BtnHakPasienDanKeluarga4.setVisible(false);
//            BtnNilaiKepercayaan.setVisible(false);
//            BtnNilaiKepercayaan1.setVisible(false);
//            BtnNilaiKepercayaan2.setVisible(false);
//            BtnNilaiKepercayaan3.setVisible(false);
//            BtnNilaiKepercayaan4.setVisible(false);
//            BtnRohaniawan.setVisible(false);
//            BtnRohaniawan1.setVisible(false);
//            BtnRohaniawan2.setVisible(false);
//            BtnRohaniawan3.setVisible(false);
//            BtnRohaniawan4.setVisible(false);
//            BtnManajemenNyeri.setVisible(false);
//            BtnManajemenNyeri1.setVisible(false);
//            BtnManajemenNyeri2.setVisible(false);
//            BtnManajemenNyeri3.setVisible(false);
//            BtnManajemenNyeri4.setVisible(false);
//            BtnGelang.setVisible(false);
//            BtnGelang1.setVisible(false);
//            BtnGelang2.setVisible(false);
//            BtnGelang3.setVisible(false);
//            BtnGelang4.setVisible(false);
//            BtnCuciTangan.setVisible(false);
//            BtnCuciTangan1.setVisible(false);
//            BtnCuciTangan2.setVisible(false);
//            BtnCuciTangan3.setVisible(false);
//            BtnCuciTangan4.setVisible(false);
//            BtnManajemenResikoJatuh.setVisible(false);
//            BtnManajemenResikoJatuh1.setVisible(false);
//            BtnManajemenResikoJatuh2.setVisible(false);
//            BtnManajemenResikoJatuh3.setVisible(false);
//            BtnManajemenResikoJatuh4.setVisible(false);
//            BtnLainnya.setVisible(false);
//            BtnLainnya2.setVisible(false);
//            BtnLainnya3.setVisible(false);
//            BtnLainnya4.setVisible(false);
            //            }else if(Profesi.getSelectedIndex()==14){
            //            BtnDokter1.setVisible(false);
            //            BtnDokter2.setVisible(false);
            //            BtnDokter3.setVisible(false);
            //            BtnDokter4.setVisible(false);
            //            BtnDokter5.setVisible(false);
            //            BtnFarmasi.setVisible(false);
            //            BtnFarmasi2.setVisible(false);
            //            BtnFarmasi3.setVisible(false);
            //            BtnFarmasi4.setVisible(false);
            //            BtnNutrisionis.setVisible(false);
            //            BtnNutrisionis2.setVisible(false);
            //            BtnNutrisionis3.setVisible(false);
            //            BtnNutrisionis4.setVisible(false);
            //            BtnRehabMedik.setVisible(false);
            //            BtnRehabMedik1.setVisible(false);
            //            BtnRehabMedik2.setVisible(false);
            //            BtnRehabMedik3.setVisible(false);
            //            BtnRehabMedik4.setVisible(false);
            //            BtnHakPasienDanKeluarga.setVisible(false);
            //            BtnHakPasienDanKeluarga1.setVisible(false);
            //            BtnHakPasienDanKeluarga2.setVisible(false);
            //            BtnHakPasienDanKeluarga3.setVisible(false);
            //            BtnHakPasienDanKeluarga4.setVisible(false);
            //            BtnNilaiKepercayaan.setVisible(false);
            //            BtnNilaiKepercayaan1.setVisible(false);
            //            BtnNilaiKepercayaan2.setVisible(false);
            //            BtnNilaiKepercayaan3.setVisible(false);
            //            BtnNilaiKepercayaan4.setVisible(false);
            //            BtnRohaniawan.setVisible(false);
            //            BtnRohaniawan1.setVisible(false);
            //            BtnRohaniawan2.setVisible(false);
            //            BtnRohaniawan3.setVisible(false);
            //            BtnRohaniawan4.setVisible(false);
            //            BtnManajemenNyeri.setVisible(false);
            //            BtnManajemenNyeri1.setVisible(false);
            //            BtnManajemenNyeri2.setVisible(false);
            //            BtnManajemenNyeri3.setVisible(false);
            //            BtnManajemenNyeri4.setVisible(false);
            //            BtnGelang.setVisible(false);
            //            BtnGelang1.setVisible(false);
            //            BtnGelang2.setVisible(false);
            //            BtnGelang3.setVisible(false);
            //            BtnGelang4.setVisible(false);
            //            BtnCuciTangan.setVisible(false);
            //            BtnCuciTangan1.setVisible(false);
            //            BtnCuciTangan2.setVisible(false);
            //            BtnCuciTangan3.setVisible(false);
            //            BtnCuciTangan4.setVisible(false);
            //            BtnManajemenResikoJatuh.setVisible(false);
            //            BtnManajemenResikoJatuh1.setVisible(false);
            //            BtnManajemenResikoJatuh2.setVisible(false);
            //            BtnManajemenResikoJatuh3.setVisible(false);
            //            BtnManajemenResikoJatuh4.setVisible(false);
            //            BtnLainnya.setVisible(false);
            //            BtnLainnya2.setVisible(false);
            //            BtnLainnya3.setVisible(false);
            //            BtnLainnya4.setVisible(false);
        }
    }//GEN-LAST:event_ProfesiItemStateChanged

    private void JelaskanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JelaskanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JelaskanKeyPressed

    private void AlasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlasanKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InformasiKeyPressed

    private void InformasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InformasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InformasiActionPerformed

    private void PengetahuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengetahuanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengetahuanKeyPressed

    private void PengetahuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengetahuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengetahuanActionPerformed

    private void AgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AgamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgamaKeyPressed

    private void PendidikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendidikanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendidikanKeyPressed

    private void IsyaratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IsyaratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsyaratKeyPressed

    private void IsyaratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IsyaratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsyaratActionPerformed

    private void PenerjemahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenerjemahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenerjemahKeyPressed

    private void PenerjemahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PenerjemahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenerjemahActionPerformed

    private void MembacaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MembacaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MembacaKeyPressed

    private void MembacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MembacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MembacaActionPerformed

    private void BahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BahasaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BahasaKeyPressed

    private void BicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BicaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BicaraKeyPressed

    private void BicaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BicaraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BicaraActionPerformed

    private void PengkajianLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengkajianLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengkajianLainnyaKeyPressed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void accepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_accepKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_accepKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HubunganKeyPressed

    private void KebutuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebutuhanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebutuhanKeyPressed

    private void PengkajianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengkajianKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengkajianKeyPressed

    private void PengkajianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengkajianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengkajianActionPerformed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,TCari,Kebutuhan);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void TanggalSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSuratKeyPressed
        Valid.pindah(evt,NoSurat,Kebutuhan);
    }//GEN-LAST:event_TanggalSuratKeyPressed

    private void TanggalSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalSuratActionPerformed

    private void TanggalSuratItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalSuratItemStateChanged
        autoNumberX(TanggalSurat.getSelectedItem()+"");
    }//GEN-LAST:event_TanggalSuratItemStateChanged

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

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void NoSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoSuratActionPerformed

    private void BahasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BahasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BahasaActionPerformed

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
    private widget.TextBox Bahasa;
    private widget.ComboBox Bicara;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.Button BtnSimpan;
    private widget.TextBox Budaya;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Edukasi1;
    private widget.TextBox Edukasi11;
    private widget.TextBox Edukasi2;
    private widget.TextBox Edukasi3;
    private widget.TextBox Edukasi4;
    private widget.TextBox Edukasi5;
    private widget.TextArea EdukasiLainnya;
    private widget.ComboBox Evaluasi1;
    private widget.ComboBox Evaluasi11;
    private widget.ComboBox Evaluasi2;
    private widget.ComboBox Evaluasi3;
    private widget.ComboBox Evaluasi4;
    private widget.ComboBox Evaluasi5;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox Hubungan;
    private widget.ComboBox Informasi;
    private widget.ComboBox Informasi1;
    private widget.ComboBox Informasi2;
    private widget.ComboBox Isyarat;
    private widget.TextBox Jelaskan;
    private widget.TextBox Kebutuhan;
    private widget.TextBox Kode1;
    private widget.TextBox Kode11;
    private widget.TextBox Kode2;
    private widget.TextBox Kode3;
    private widget.TextBox Kode4;
    private widget.TextBox Kode5;
    private widget.TextBox KodeDokter;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Membaca;
    private widget.ComboBox Metode1;
    private widget.ComboBox Metode11;
    private widget.ComboBox Metode2;
    private widget.ComboBox Metode3;
    private widget.ComboBox Metode4;
    private widget.ComboBox Metode5;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox NamaDokter;
    private widget.TextBox NoSurat;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pendidikan;
    private widget.ComboBox Penerima1;
    private widget.ComboBox Penerima11;
    private widget.ComboBox Penerima2;
    private widget.ComboBox Penerima3;
    private widget.ComboBox Penerima4;
    private widget.ComboBox Penerima5;
    private widget.ComboBox Penerjemah;
    private widget.ComboBox Pengetahuan;
    private widget.ComboBox Pengkajian;
    private widget.TextBox PengkajianLainnya;
    private widget.ComboBox Profesi;
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
    private widget.Label jLabel34;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel40;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
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

    try {
        StringBuilder sql = new StringBuilder(
            "SELECT ep.no_rawat, rp.no_rkm_medis, ps.nm_pasien, " +
            "ep.no_surat, ep.tanggal_pengkajian, " +
            "ep.nik, pg.nama, " +
            "ep.hambatan_edukasi, ep.hambatan_edukasi_lain, " +
            "ep.bicara, ep.kemampuan_membaca, ep.bahasa_sehari, ep.penerjemah, ep.bahasa_isyarat, " +
            "sb.nama_suku_bangsa AS budaya, " +
            "ep.pendidikan, ep.agama, ep.tingkat_pengetahuan, " +
            "ep.hambatan_emosional, ep.hambatan_motivasi, " +
            "ep.ketersediaan_pasiein, ep.alasan_kesediaan, ep.kebutuhan_edukasi, " +
            "ep.hubungan, ep.acc_hpk " +
            "FROM edukasi_pasien ep " +
            "INNER JOIN reg_periksa rp ON ep.no_rawat = rp.no_rawat " +
            "INNER JOIN pasien ps ON rp.no_rkm_medis = ps.no_rkm_medis " +
            "INNER JOIN pegawai pg ON ep.nik = pg.nik " +
            "LEFT JOIN suku_bangsa sb ON ps.suku_bangsa = sb.id "
        );

        List<String> kondisi = new ArrayList<>();
        List<String> params = new ArrayList<>();

        // =====================
        // FILTER KEYWORD (TCari)
        // =====================
        if (!TCari.getText().trim().equals("")) {
            kondisi.add("(ep.no_rawat LIKE ? OR ps.nm_pasien LIKE ? OR ep.no_surat LIKE ?)");
            String cari = "%" + TCari.getText().trim() + "%";
            params.add(cari);
            params.add(cari);
            params.add(cari);
        }

        // =====================
        // FILTER TANGGAL
        // =====================
        if (DTPCari1.getSelectedItem() != null && DTPCari2.getSelectedItem() != null) {
            kondisi.add("DATE(ep.tanggal_surat) BETWEEN ? AND ?");
            params.add(Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
            params.add(Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
        }

        // =====================
        // GABUNGKAN WHERE
        // =====================
        if (!kondisi.isEmpty()) {
            sql.append(" WHERE ");
            sql.append(String.join(" AND ", kondisi));
        }

        sql.append(" ORDER BY ep.tanggal_surat");

        ps = koneksi.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            ps.setString(i + 1, params.get(i));
        }

        rs = ps.executeQuery();

        while (rs.next()) {
            tabMode.addRow(new Object[]{
                rs.getString("no_surat"),
                rs.getString("no_rawat"),
                rs.getString("nm_pasien"),
                rs.getString("tanggal_pengkajian"),
                rs.getString("nik"),
                rs.getString("nama"),
                rs.getString("hambatan_edukasi"),
                rs.getString("hambatan_edukasi_lain"),
                rs.getString("bicara"),
                rs.getString("kemampuan_membaca"),
                rs.getString("bahasa_sehari"),
                rs.getString("penerjemah"),
                rs.getString("budaya"),
                rs.getString("bahasa_isyarat"),
                rs.getString("pendidikan"),
                rs.getString("agama"),
                rs.getString("tingkat_pengetahuan"),
                rs.getString("hambatan_emosional"),
                rs.getString("hambatan_motivasi"),
                rs.getString("ketersediaan_pasiein"),
                rs.getString("alasan_kesediaan"),
                rs.getString("kebutuhan_edukasi"),
                rs.getString("hubungan"),
                rs.getString("acc_hpk")
            });
        }

    } catch (Exception e) {
        System.out.println("Notifikasi : " + e);
    }
}
    public void emptTeks() {
////        NoSurat.setText("");
////        TNoRw.setText("");
        KodeDokter.setText("");
        NamaDokter.setText("");
//        Pengkajian.setSelectedIndex(0);
//        PengkajianLainnya.setText("");
//        Bicara.setSelectedIndex(0);
//        Membaca.setSelectedIndex(0);
//        Penerjemah.setSelectedIndex(0);
//        Isyarat.setSelectedIndex(0);
//        Kepercayaan.setSelectedIndex(0);
//        Pengetahuan.setSelectedIndex(0);
//        Budaya.setSelectedIndex(0);
//        Informasi.setSelectedIndex(0);
//        Alasan.setText("");
//        Rencana.setSelectedIndex(0);
        Jelaskan.setText("");
//        Profesi.setSelectedIndex(0);
//        Kode1.setText("");
//        Edukasi1.setText("");
//        Penerima1.setSelectedIndex(0);
//        Metode1.setSelectedIndex(0);
////        Frekuensi1.setSelectedIndex(0);
//        Evaluasi1.setSelectedIndex(0);
//        Kode2.setText("");
//        Edukasi2.setText("");
//        Penerima2.setSelectedIndex(0);
//        Metode2.setSelectedIndex(0);
////        Frekuensi2.setSelectedIndex(0);
//        Evaluasi2.setSelectedIndex(0);
//        Kode3.setText("");
//        Edukasi3.setText("");
//        Penerima3.setSelectedIndex(0);
//        Metode3.setSelectedIndex(0);
////        Frekuensi3.setSelectedIndex(0);
//        Evaluasi3.setSelectedIndex(0);
//        Kode4.setText("");
//        Edukasi4.setText("");
//        Penerima4.setSelectedIndex(0);
//        Metode4.setSelectedIndex(0);
////        Frekuensi4.setSelectedIndex(0);
//        Evaluasi4.setSelectedIndex(0);
//        Kode5.setText("");
//        Edukasi5.setText("");
//        Penerima5.setSelectedIndex(0);
//        Metode5.setSelectedIndex(0);
////        Frekuensi5.setSelectedIndex(0);
//        Evaluasi5.setSelectedIndex(0);
////        Kode6.setText("");
////        Edukasi6.setText("");
////        Penerima6.setSelectedIndex(0);
////        Metode6.setSelectedIndex(0);
////        Frekuensi6.setSelectedIndex(0);
////        Evaluasi6.setSelectedIndex(0);
////        Kode7.setText("");
////        Edukasi7.setText("");
////        Penerima7.setSelectedIndex(0);
////        Metode7.setSelectedIndex(0);
////        Frekuensi7.setSelectedIndex(0);
////        Evaluasi7.setSelectedIndex(0);
////        Kode8.setText("");
////        Edukasi8.setText("");
////        Penerima8.setSelectedIndex(0);
////        Metode8.setSelectedIndex(0);
////        Frekuensi8.setSelectedIndex(0);
////        Evaluasi8.setSelectedIndex(0);
////        Kode9.setText("");
////        Edukasi9.setText("");
////        Penerima9.setSelectedIndex(0);
////        Metode9.setSelectedIndex(0);
////        Frekuensi9.setSelectedIndex(0);
////        Evaluasi9.setSelectedIndex(0);
////        Kode10.setText("");
////        Edukasi10.setText("");
////        Penerima10.setSelectedIndex(0);
////        Metode10.setSelectedIndex(0);
////        Frekuensi10.setSelectedIndex(0);
////        Evaluasi10.setSelectedIndex(0);
//        EdukasiLainnya.setText("");
//        Kode11.setText("");
//        Edukasi11.setText("");
//        Penerima11.setSelectedIndex(0);
//        Metode11.setSelectedIndex(0);
////        Frekuensi11.setSelectedIndex(0);
//        Evaluasi11.setSelectedIndex(0);
//        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from edukasi_pasien_ranap where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
//                "EPRI"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),4,NoSurat);
////        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0) from surat_ic_rajal where tanggal_surat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
////                "ICRJ"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),3,NoSurat); 
//        NoSurat.requestFocus();
    Bahasa.setText("");
    Budaya.setText("");
    Pendidikan.setText("");
    Agama.setText("");
    Pengkajian.setSelectedIndex(0);
    PengkajianLainnya.setText("");
    Bicara.setSelectedIndex(0);
    Membaca.setSelectedIndex(0);
    Penerjemah.setSelectedIndex(0);
    Isyarat.setSelectedIndex(0);
    Informasi.setSelectedIndex(0);
    Alasan.setText("");
//    Rencana.setSelectedIndex(0);
    Profesi.setSelectedIndex(0);

    // Edukasi 1
    Kode1.setText("");
    Edukasi1.setText("");
    Metode1.setSelectedIndex(0);
    Penerima1.setSelectedIndex(0);
    Evaluasi1.setSelectedIndex(0);

    // Edukasi 2
    Kode2.setText("");
    Edukasi2.setText("");
    Metode2.setSelectedIndex(0);
    Penerima2.setSelectedIndex(0);
    Evaluasi2.setSelectedIndex(0);

    // Edukasi 3
    Kode3.setText("");
    Edukasi3.setText("");
    Metode3.setSelectedIndex(0);
    Penerima3.setSelectedIndex(0);
    Evaluasi3.setSelectedIndex(0);

    // Edukasi 4
    Kode4.setText("");
    Edukasi4.setText("");
    Metode4.setSelectedIndex(0);
    Penerima4.setSelectedIndex(0);
    Evaluasi4.setSelectedIndex(0);

    // Edukasi 5
    Kode5.setText("");
    Edukasi5.setText("");
    Metode5.setSelectedIndex(0);
    Penerima5.setSelectedIndex(0);
    Evaluasi5.setSelectedIndex(0);

    // Tambahan
    EdukasiLainnya.setText("");
    Kode11.setText("");
    Edukasi11.setText("");
    Metode11.setSelectedIndex(0);
    Penerima11.setSelectedIndex(0);
    Evaluasi11.setSelectedIndex(0);
    
    // Auto nomor surat
    Valid.autoNomer3(
        "select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from edukasi_pasien " +
        "where tanggal_surat='" + Valid.SetTgl(TanggalSurat.getSelectedItem()+"") + "' ",
        "EPRI"
        + TanggalSurat.getSelectedItem().toString().substring(6,10)
        + TanggalSurat.getSelectedItem().toString().substring(3,5)
        + TanggalSurat.getSelectedItem().toString().substring(0,2),
        4,
        NoSurat
    );

    NoSurat.requestFocus();
    } 

private void getData() {
    int row = tbObat.getSelectedRow();
    if (row != -1) {

        TNoRw.setText(tbObat.getValueAt(row, 1).toString());
        NoSurat.setText(tbObat.getValueAt(row, 0).toString());
        TPasien.setText(tbObat.getValueAt(row, 2).toString());
        Valid.SetTgl(TanggalSurat, tbObat.getValueAt(row, 3).toString());

        KodeDokter.setText(tbObat.getValueAt(row, 4).toString());      // nik
        NamaDokter.setText(tbObat.getValueAt(row, 5).toString());     // nama_dokter

        Pengkajian.setSelectedItem(tbObat.getValueAt(row, 6).toString());
        PengkajianLainnya.setText(tbObat.getValueAt(row, 7).toString());

        Bicara.setSelectedItem(tbObat.getValueAt(row, 8).toString());
        Membaca.setSelectedItem(tbObat.getValueAt(row, 9).toString());
        Bahasa.setText(tbObat.getValueAt(row, 10).toString());
        Penerjemah.setSelectedItem(tbObat.getValueAt(row, 11).toString());
        Budaya.setText(tbObat.getValueAt(row, 12).toString());
        Isyarat.setSelectedItem(tbObat.getValueAt(row, 13).toString());
        Pendidikan.setText(tbObat.getValueAt(row, 14).toString());
        Agama.setText(tbObat.getValueAt(row, 15).toString());
        Pengetahuan.setSelectedItem(tbObat.getValueAt(row, 16).toString());

        Informasi1.setSelectedItem(tbObat.getValueAt(row, 17).toString());
        Informasi2.setSelectedItem(tbObat.getValueAt(row, 18).toString());
        Informasi.setSelectedItem(tbObat.getValueAt(row, 19).toString());

        Alasan.setText(tbObat.getValueAt(row, 20).toString());
        Jelaskan.setText(tbObat.getValueAt(row, 21).toString());
        Hubungan.setText(tbObat.getValueAt(row, 22).toString());
        accep.setText(tbObat.getValueAt(row, 23).toString());

        panggilPhoto();
    }
}

    private void isRawat() {
    Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    try {
        ps=koneksi.prepareStatement(
            "select nm_pasien, " +
            "if(jk='L','Laki-Laki','Perempuan') as jk, " +
            "tgl_lahir, agama, " +
            "bahasa_pasien.nama_bahasa, " +
            "cacat_fisik.nama_cacat, " +
            "pasien.pnd, " +
            "suku_bangsa.nama_suku_bangsa " +
            "from pasien " +
            "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien " +
            "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik " +
            "inner join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa " +
            "where no_rkm_medis=?"
        );

        try {
            ps.setString(1,TNoRM.getText());
            rs=ps.executeQuery();
            if(rs.next()){
                TPasien.setText(rs.getString("nm_pasien"));
                Agama.setText(rs.getString("agama"));
                Bahasa.setText(rs.getString("nama_bahasa"));
                Pendidikan.setText(rs.getString("pnd"));
                Budaya.setText(rs.getString("nama_suku_bangsa")); // <-- tambahkan ini
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
//        BtnDokter1.setVisible(false);
//        BtnDokter2.setVisible(false);
//        BtnDokter3.setVisible(false);
//        BtnDokter4.setVisible(false);
//        BtnDokter5.setVisible(false);
//        BtnFarmasi.setVisible(false);
//        BtnFarmasi2.setVisible(false);
//        BtnFarmasi3.setVisible(false);
//        BtnFarmasi4.setVisible(false);
//        BtnNutrisionis.setVisible(false);
//        BtnNutrisionis2.setVisible(false);
//        BtnNutrisionis3.setVisible(false);
//        BtnNutrisionis4.setVisible(false);
//        BtnRehabMedik.setVisible(false);
//        BtnLainnya.setVisible(false);
//        BtnLainnya3.setVisible(false);
//        BtnLainnya4.setVisible(false);
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

    


    // ===== INISIALISASI ARRAY =====
    private void inisialisasiArray(){
        txtKode = new javax.swing.JTextField[]{Kode1, Kode2, Kode3, Kode4, Kode5};
        txtEdukasi = new javax.swing.JTextField[]{Edukasi1, Edukasi2, Edukasi3, Edukasi4, Edukasi5};
        txtPenerima = new javax.swing.JTextArea[]{Penerima1, Penerima2, Penerima3, Penerima4, Penerima5};
        txtMedia = new javax.swing.JTextArea[]{Metode1, Metode2, Metode3, Metode4, Metode5};
        txtEvaluasi = new javax.swing.JTextArea[]{Evaluasi1, Evaluasi2, Evaluasi3, Evaluasi4, Evaluasi5};
        txtEdukasiLainnya = new javax.swing.JTextArea[]{EdukasiLainnya};
//        txtKolaborasi = new javax.swing.JTextField[]{kolaborasi1, kolaborasi2, kolaborasi3, kolaborasi4, kolaborasi5};
        txtKode1 = new javax.swing.JTextField[]{Kode11};
        txtEdukasi1 = new javax.swing.JTextField[]{Edukasi11};
        txtPenerima1 = new javax.swing.JTextArea[]{Penerima11};
        txtMedia1 = new javax.swing.JTextArea[]{Metode11};
        txtEvaluasi1 = new javax.swing.JTextArea[]{Evaluasi11};
    }

    // ===== KOSONGKAN FIELD =====
    private void kosongkanField(){
        for(int i = 0; i < 5; i++){
            txtKode[i].setText("");
            txtEdukasi[i].setText("");
            txtPenerima[i].setText("");
            txtMedia[i].setText("");
            txtEvaluasi[i].setText("");
            txtEdukasiLainnya[i].setText("");
            txtKolaborasi[i].setText("");
        }
    }

    // ===== TAMPIL DATA EDUKASI =====
    private void tampilDataEdukasi(){

        kosongkanField();

        String kategori = cmbProfesi.getSelectedItem().toString();

        try {

            String sql = "SELECT kode, edukasi, penerima, media, evaluasi, " +
                         "edukasi_lainnya, kolaborasi_profesi " +
                         "FROM detail_edukasi_pasien " +
                         "WHERE kategori_edukasi = ?";

            java.sql.PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, kategori);
            java.sql.ResultSet rs = ps.executeQuery();

            int i = 0;

            while(rs.next() && i < 5){

                txtKode[i].setText(rs.getString("kode"));
                txtEdukasi[i].setText(rs.getString("edukasi"));
                txtPenerima[i].setText(rs.getString("penerima"));
                txtMedia[i].setText(rs.getString("media"));
                txtEvaluasi[i].setText(rs.getString("evaluasi"));
                txtEdukasiLainnya[i].setText(rs.getString("edukasi_lainnya"));
                txtKolaborasi[i].setText(rs.getString("kolaborasi_profesi"));

                i++;
            }

        } catch(Exception e){
            System.out.println("Error tampilDataEdukasi : " + e);
        }
    }


}