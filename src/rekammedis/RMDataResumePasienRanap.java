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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import laporan.DlgBerkasRawat;
import laporan.DlgDiagnosaPenyakit;
import digitalsignature.DlgViewPdf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.Timer;


/**
 *
 * @author perpustakaan
 */
public final class RMDataResumePasienRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private String namaPenyakit="",namaPenyakitt="",Listpenyakit="",Listpenyakitt="",NamaObat="",NamaObatt=""
            ,ListObat="",ListObatt="",NamaObattt="",NamaObatttt="",ListObattt="",ListObatttt=""; 
    private String noRawatSumberRanap="";
    private String FileName;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private RMCariRPSAssMedisRanap carirps=new RMCariRPSAssMedisRanap(null,false);
    private RMCariKetFisikAssMedisRanap carifisik=new RMCariKetFisikAssMedisRanap(null,false);
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
    private RMCariDiagnosa6 rmcaridiagnosa6=new RMCariDiagnosa6(null,false);
    private RMCariDiagnosa7 rmcaridiagnosa7=new RMCariDiagnosa7(null,false);
    private RMCariDiagnosa8 rmcaridiagnosa8=new RMCariDiagnosa8(null,false);
    private RMCariProsedur1 rmcariprosedur1=new RMCariProsedur1(null,false);
    private RMCariProsedur2 rmcariprosedur2=new RMCariProsedur2(null,false);
    private RMCariProsedur3 rmcariprosedur3=new RMCariProsedur3(null,false);
    private RMCariProsedur4 rmcariprosedur4=new RMCariProsedur4(null,false);
    private RMCariProsedur5 rmcariprosedur5=new RMCariProsedur5(null,false);
    private RMCariProsedur6 rmcariprosedur6=new RMCariProsedur6(null,false);
    private RMCariProsedur7 rmcariprosedur7=new RMCariProsedur7(null,false);
    private RMCariObatPulang rmcariobatpulang=new RMCariObatPulang(null,false);
    private RMCariObatPulang1 rmcariobatpulang1=new RMCariObatPulang1(null,false);
    private RMCariObatPulang2 rmcariobatpulang2=new RMCariObatPulang2(null,false);
    private RMCariObatPulang3 rmcariobatpulang3=new RMCariObatPulang3(null,false);
    private RMCariObatPulang4 rmcariobatpulang4=new RMCariObatPulang4(null,false);
    private RMCariObatPulang5 rmcariobatpulang5=new RMCariObatPulang5(null,false);
    private RMCariObatPulang6 rmcariobatpulang6=new RMCariObatPulang6(null,false);
    private RMCariObatPulang7 rmcariobatpulang7=new RMCariObatPulang7(null,false);
    private RMCariObatPulang8 rmcariobatpulang8=new RMCariObatPulang8(null,false);
    private RMCariObatPulang9 rmcariobatpulang9=new RMCariObatPulang9(null,false);
    private RMCariTerapiRanap rmcariterapiranap=new RMCariTerapiRanap(null,false);
    private RMCariLabPending rmcarilabpending=new RMCariLabPending(null,false);
    private RMCariDiet rmcaridiet=new RMCariDiet(null,false);
    private RMCariRadRanap rmcariradranap=new RMCariRadRanap(null,false);
    private RMCariLabRanap rmcarilabranap=new RMCariLabRanap(null,false);
    
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMDataResumePasienRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "Tgl.Rawat","Status","No.Rawat","No.RM","Nama Pasien","Kode Dokter PJ","Dokter Penanggung Jawab","Tgl.Lahir","J.K","Ruang/Kelas","Tanggal Masuk","Jam Masuk",
            "Tanggal Keluar","Jam Keluar","Kode Dokter Pengirim","Nama Dokter Pengirim","Kode Cara Bayar","Cara Bayar","Diagnosa Awal","Alasan Masuk Dirawat","Ringkasan Riwayat Penyakit",
            "Pemeriksaan Fisik","Pemeriksaan Penunjang Rad Terpenting","Pemeriksaan Penunjang Lab Terpenting","Terapi","Diagnosa Utama","ICD10 Utama","Diagnosa Sekunder 1",
            "ICD10 Sek 1","Diagnosa Sekunder 2","ICD10 Sek 2","Diagnosa Sekunder 3","ICD10 Sek 3","Diagnosa Sekunder 4","ICD10 Sek 4","Diagnosa Sekunder 5","ICD10 Sek 5",
            "Diagnosa Sekunder 6","ICD10 Sek 6","Diagnosa Sekunder 7","ICD10 Sek 7","Diagnosa Klinis","Prosedur Utama","ICD9 1","Prosedur 2","ICD9 2","Prosedur 3","ICD9 3","Prosedur 4","ICD9 4",
            "Prosedur 5","ICD9 5","Prosedur 6","ICD9 6","Prosedur 7","ICD9 7",
            "Alergi(Reaksi Obat)","Diet","Hasil Lab Yang Belum Selesai(Pending)","Instruksi/Anjuran Dan Edukasi(Follow Up)",
            "Status Pulang","Cara Keluar Lainnya","Keadaan Pulang","Keadaan Pulang Khusus","Pengobatan Dilanjutkan","Pengobatan Dilanjutkan Lainnya","Tanggal&Jam Kontrol(WIB)",
            "Obat Pulang","Nama Obat(1)","Jumlah Obat(1)","Dosis Obat(1)","Kode Obat(2)","Nama Obat(2)","Jumlah Obat(2)","Dosis Obat(2)","Kode Obat(3)","Nama Obat(3)","Jumlah Obat(3)","Dosis Obat(3)",
            "Kode Obat(4)","Nama Obat(4)","Jumlah Obat(4)","Dosis Obat(4)","Kode Obat(5)","Nama Obat(5)","Jumlah Obat(5)","Dosis Obat(5)","Kode Obat(6)","Nama Obat(6)","Jumlah Obat(6)","Dosis Obat(6)",
            "Kode Obat(7)","Nama Obat(7)","Jumlah Obat(7)","Dosis Obat(7)","Kode Obat(8)","Nama Obat(8)","Jumlah Obat(8)","Dosis Obat(8)","Kode Obat(9)","Nama Obat(9)","Jumlah Obat(9)","Dosis Obat(9)",
            "Kode Obat(10)", "Nama Obat(10)","Jumlah Obat(10)","Dosis Obat(10)","Sudah Dijelaskan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 108; i++) {
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
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(75);
            }else if(i==18){
                column.setPreferredWidth(200);
            }else if(i==20){
                column.setPreferredWidth(200);
            }else if(i==21){
                column.setPreferredWidth(200);
            }else if(i==22){
                column.setPreferredWidth(200);
            }else if(i==23){
                column.setPreferredWidth(200);
            }else if(i==24){
                column.setPreferredWidth(200);
            }
            //Diagnosa Utama
            else if(i==25){
                column.setPreferredWidth(170);
            }
            //Diagnosa Klinis
//            else if(i==41){
//                column.setPreferredWidth(250);
//            }
            //Prosedur Utama
            else if(i==42){
                column.setPreferredWidth(250);
            }
            else if(i==56){
                column.setPreferredWidth(170);
            }else if(i==57){
                column.setPreferredWidth(75);
            }else if(i==58){
                column.setPreferredWidth(170);
            }else if(i==59){
                column.setPreferredWidth(150);
            }else if(i==60){
                column.setPreferredWidth(170);
            }else if(i==64){
                column.setPreferredWidth(170);
            }else if(i==65){
                column.setPreferredWidth(170);
            }else if(i==67){
                column.setPreferredWidth(250);
            }else if(i==107){
                column.setPreferredWidth(100);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KodeDokter.setDocument(new batasInput((int)20).getKata(KodeDokter));
        Jk.setDocument(new batasInput((int)10).getKata(Jk));
        Ruang.setDocument(new batasInput((int)15).getKata(Ruang));
        KodeDokter1.setDocument(new batasInput((int)20).getKata(KodeDokter1));
        CaraBayar.setDocument(new batasInput((byte)3).getKata(CaraBayar));
        DiagnosaAwal.setDocument(new batasInput((int)200).getKata(DiagnosaAwal));
        Alasan.setDocument(new batasInput((int)200).getKata(Alasan));
//        Alasan.setVisible(false);
//        jLabel23.setVisible(false);
        DiagnosaUtama.setDocument(new batasInput((int)1000).getKata(DiagnosaUtama));
        DiagnosaSekunder1.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder1));
        DiagnosaSekunder2.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder2));
        DiagnosaSekunder3.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder3));
        DiagnosaSekunder4.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder4));
        DiagnosaSekunder5.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder5));
        DiagnosaSekunder6.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder6));
        DiagnosaSekunder7.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder7));
        ProsedurUtama.setDocument(new batasInput((int)200).getKata(ProsedurUtama));
        ProsedurSekunder1.setDocument(new batasInput((int)200).getKata(ProsedurSekunder1));
        ProsedurSekunder2.setDocument(new batasInput((int)200).getKata(ProsedurSekunder2));
        ProsedurSekunder3.setDocument(new batasInput((int)200).getKata(ProsedurSekunder3));
        ProsedurSekunder4.setDocument(new batasInput((int)200).getKata(ProsedurSekunder4));
        ProsedurSekunder5.setDocument(new batasInput((int)200).getKata(ProsedurSekunder5));
        ProsedurSekunder6.setDocument(new batasInput((int)200).getKata(ProsedurSekunder6));
        KodeDiagnosaUtama.setDocument(new batasInput((int)10).getKata(KodeDiagnosaUtama));
        KodeDiagnosaSekunder1.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder1));
        KodeDiagnosaSekunder2.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder2));
        KodeDiagnosaSekunder3.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder3));
        KodeDiagnosaSekunder4.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder4));
        KodeDiagnosaSekunder5.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder5));
        KodeDiagnosaSekunder6.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder6));
        KodeDiagnosaSekunder7.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder7));
        KodeProsedurUtama.setDocument(new batasInput((int)8).getKata(KodeProsedurUtama));
        KodeProsedurSekunder1.setDocument(new batasInput((int)8).getKata(KodeProsedurSekunder1));
        KodeProsedurSekunder2.setDocument(new batasInput((int)8).getKata(KodeProsedurSekunder2));
        KodeProsedurSekunder3.setDocument(new batasInput((int)8).getKata(KodeProsedurSekunder3));
        KodeProsedurSekunder4.setDocument(new batasInput((int)8).getKata(KodeProsedurSekunder4));
        KodeProsedurSekunder5.setDocument(new batasInput((int)8).getKata(KodeProsedurSekunder5));
        KodeProsedurSekunder6.setDocument(new batasInput((int)8).getKata(KodeProsedurSekunder6));
        Alergi.setDocument(new batasInput((int)200).getKata(Alergi));
        KeluarLainnya.setDocument(new batasInput((int)100).getKata(KeluarLainnya));
        KeadaanLainnya.setDocument(new batasInput((int)100).getKata(KeadaanLainnya));
        PengobatanLainnya.setDocument(new batasInput((int)100).getKata(PengobatanLainnya));
        Obat.setDocument(new batasInput((int)1000).getKata(Obat));
//        Jumlah.setDocument(new batasInput((int)5).getKata(Jumlah));
//        Dosis.setDocument(new batasInput((int)20).getKata(Dosis));
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
        
        carirps.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carirps.getTable().getSelectedRow()!= -1){
                    Keluhan.append(carirps.getTable().getValueAt(carirps.getTable().getSelectedRow(),2).toString()+", ");
                    Keluhan.requestFocus();
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
        
        carifisik.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carifisik.getTable().getSelectedRow()!= -1){
                    JalannyaPenyakit.append(carifisik.getTable().getValueAt(carifisik.getTable().getSelectedRow(),2).toString()+", ");
                    JalannyaPenyakit.requestFocus();
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
                    Keluhan.append(carikeluhan.getTable().getValueAt(carikeluhan.getTable().getSelectedRow(),2).toString()+", ");
                    Keluhan.requestFocus();
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
        
        caripemeriksaan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(caripemeriksaan.getTable().getSelectedRow()!= -1){
                    JalannyaPenyakit.append(caripemeriksaan.getTable().getValueAt(caripemeriksaan.getTable().getSelectedRow(),2).toString()+", ");
                    JalannyaPenyakit.requestFocus();
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
        
        rmcariradranap.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariradranap.getTable().getSelectedRow()!= -1){
                    PemeriksaanPenunjang.append(rmcariradranap.getTable().getValueAt(rmcariradranap.getTable().getSelectedRow(),2).toString()+", ");
                    PemeriksaanPenunjang.requestFocus();
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
        
        rmcarilabranap.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcarilabranap.getTable().getSelectedRow()!= -1){
                    HasilLaborat.append(rmcarilabranap.getTable().getValueAt(rmcarilabranap.getTable().getSelectedRow(),2).toString()+", ");
                    HasilLaborat.requestFocus();
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

        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                //if(akses.getform().equals("DlgRawatJalan")){
                   isRawat();
//                      JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
      
                //}
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
//        rmcaridiagnosa6.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcaridiagnosa6.getTable().getSelectedRow()!= -1){
//                    KodeDiagnosaSekunder5.setText(rmcaridiagnosa6.getTable().getValueAt(rmcaridiagnosa6.getTable().getSelectedRow(),0).toString());
//                    DiagnosaSekunder5.setText(rmcaridiagnosa6.getTable().getValueAt(rmcaridiagnosa6.getTable().getSelectedRow(),1).toString());
//                    KodeDiagnosaSekunder5.requestFocus();
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
//        rmcaridiagnosa7.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcaridiagnosa7.getTable().getSelectedRow()!= -1){
//                    KodeDiagnosaSekunder6.setText(rmcaridiagnosa7.getTable().getValueAt(rmcaridiagnosa7.getTable().getSelectedRow(),0).toString());
//                    DiagnosaSekunder6.setText(rmcaridiagnosa7.getTable().getValueAt(rmcaridiagnosa7.getTable().getSelectedRow(),1).toString());
//                    KodeDiagnosaSekunder6.requestFocus();
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
//        rmcaridiagnosa8.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcaridiagnosa8.getTable().getSelectedRow()!= -1){
//                    KodeDiagnosaSekunder7.setText(rmcaridiagnosa8.getTable().getValueAt(rmcaridiagnosa8.getTable().getSelectedRow(),0).toString());
//                    DiagnosaSekunder7.setText(rmcaridiagnosa8.getTable().getValueAt(rmcaridiagnosa8.getTable().getSelectedRow(),1).toString());
//                    KodeDiagnosaSekunder7.requestFocus();
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
//        rmcariprosedur5.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcariprosedur5.getTable().getSelectedRow()!= -1){
//                    KodeProsedurSekunder4.setText(rmcariprosedur5.getTable().getValueAt(rmcariprosedur5.getTable().getSelectedRow(),0).toString());
//                    ProsedurSekunder4.setText(rmcariprosedur5.getTable().getValueAt(rmcariprosedur5.getTable().getSelectedRow(),1).toString());
//                    KodeProsedurSekunder4.requestFocus();
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
//        rmcariprosedur6.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcariprosedur6.getTable().getSelectedRow()!= -1){
//                    KodeProsedurSekunder5.setText(rmcariprosedur6.getTable().getValueAt(rmcariprosedur6.getTable().getSelectedRow(),0).toString());
//                    ProsedurSekunder5.setText(rmcariprosedur6.getTable().getValueAt(rmcariprosedur6.getTable().getSelectedRow(),1).toString());
//                    KodeProsedurSekunder5.requestFocus();
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
//        rmcariprosedur7.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcariprosedur7.getTable().getSelectedRow()!= -1){
//                    KodeProsedurSekunder6.setText(rmcariprosedur7.getTable().getValueAt(rmcariprosedur7.getTable().getSelectedRow(),0).toString());
//                    ProsedurSekunder6.setText(rmcariprosedur7.getTable().getValueAt(rmcariprosedur7.getTable().getSelectedRow(),1).toString());
//                    KodeProsedurSekunder6.requestFocus();
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
        
        rmcariobatpulang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariobatpulang.getTable().getSelectedRow()!= -1){
                    Obat.setText(rmcariobatpulang.getTable().getValueAt(rmcariobatpulang.getTable().getSelectedRow(),2).toString());
//                    Nama.setText(rmcariobatpulang.getTable().getValueAt(rmcariobatpulang.getTable().getSelectedRow(),3).toString());
//                    Jumlah.setText(rmcariobatpulang.getTable().getValueAt(rmcariobatpulang.getTable().getSelectedRow(),4).toString());
//                    Dosis.setText(rmcariobatpulang.getTable().getValueAt(rmcariobatpulang.getTable().getSelectedRow(),5).toString());
                    Obat.requestFocus();
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
        
        rmcariobatpulang1.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariobatpulang1.getTable().getSelectedRow()!= -1){
                    Obat1.setText(rmcariobatpulang1.getTable().getValueAt(rmcariobatpulang1.getTable().getSelectedRow(),2).toString());
                    Nama1.setText(rmcariobatpulang1.getTable().getValueAt(rmcariobatpulang1.getTable().getSelectedRow(),3).toString());
                    Jumlah1.setText(rmcariobatpulang1.getTable().getValueAt(rmcariobatpulang1.getTable().getSelectedRow(),4).toString());
                    Dosis1.setText(rmcariobatpulang1.getTable().getValueAt(rmcariobatpulang1.getTable().getSelectedRow(),5).toString());
                    Obat1.requestFocus();
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
        
        rmcariobatpulang2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariobatpulang2.getTable().getSelectedRow()!= -1){
                    Obat2.setText(rmcariobatpulang2.getTable().getValueAt(rmcariobatpulang2.getTable().getSelectedRow(),2).toString());
                    Nama2.setText(rmcariobatpulang2.getTable().getValueAt(rmcariobatpulang2.getTable().getSelectedRow(),3).toString());
                    Jumlah2.setText(rmcariobatpulang2.getTable().getValueAt(rmcariobatpulang2.getTable().getSelectedRow(),4).toString());
                    Dosis2.setText(rmcariobatpulang2.getTable().getValueAt(rmcariobatpulang2.getTable().getSelectedRow(),5).toString());
                    Obat2.requestFocus();
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
        
        rmcariobatpulang3.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariobatpulang3.getTable().getSelectedRow()!= -1){
                    Obat3.setText(rmcariobatpulang3.getTable().getValueAt(rmcariobatpulang3.getTable().getSelectedRow(),2).toString());
                    Nama3.setText(rmcariobatpulang3.getTable().getValueAt(rmcariobatpulang3.getTable().getSelectedRow(),3).toString());
                    Jumlah3.setText(rmcariobatpulang3.getTable().getValueAt(rmcariobatpulang3.getTable().getSelectedRow(),4).toString());
                    Dosis3.setText(rmcariobatpulang3.getTable().getValueAt(rmcariobatpulang3.getTable().getSelectedRow(),5).toString());
                    Obat3.requestFocus();
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
        
        rmcariobatpulang4.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariobatpulang4.getTable().getSelectedRow()!= -1){
                    Obat4.setText(rmcariobatpulang4.getTable().getValueAt(rmcariobatpulang4.getTable().getSelectedRow(),2).toString());
                    Nama4.setText(rmcariobatpulang4.getTable().getValueAt(rmcariobatpulang4.getTable().getSelectedRow(),3).toString());
                    Jumlah4.setText(rmcariobatpulang4.getTable().getValueAt(rmcariobatpulang4.getTable().getSelectedRow(),4).toString());
                    Dosis4.setText(rmcariobatpulang4.getTable().getValueAt(rmcariobatpulang4.getTable().getSelectedRow(),5).toString());
                    Obat4.requestFocus();
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
        
        rmcariobatpulang5.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariobatpulang5.getTable().getSelectedRow()!= -1){
                    Obat5.setText(rmcariobatpulang5.getTable().getValueAt(rmcariobatpulang5.getTable().getSelectedRow(),2).toString());
                    Nama5.setText(rmcariobatpulang5.getTable().getValueAt(rmcariobatpulang5.getTable().getSelectedRow(),3).toString());
                    Jumlah5.setText(rmcariobatpulang5.getTable().getValueAt(rmcariobatpulang5.getTable().getSelectedRow(),4).toString());
                    Dosis5.setText(rmcariobatpulang5.getTable().getValueAt(rmcariobatpulang5.getTable().getSelectedRow(),5).toString());
                    Obat5.requestFocus();
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
        
        rmcariobatpulang6.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariobatpulang6.getTable().getSelectedRow()!= -1){
                    Obat6.setText(rmcariobatpulang6.getTable().getValueAt(rmcariobatpulang6.getTable().getSelectedRow(),2).toString());
                    Nama6.setText(rmcariobatpulang6.getTable().getValueAt(rmcariobatpulang6.getTable().getSelectedRow(),3).toString());
                    Jumlah6.setText(rmcariobatpulang6.getTable().getValueAt(rmcariobatpulang6.getTable().getSelectedRow(),4).toString());
                    Dosis6.setText(rmcariobatpulang6.getTable().getValueAt(rmcariobatpulang6.getTable().getSelectedRow(),5).toString());
                    Obat6.requestFocus();
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
        
        rmcariobatpulang7.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariobatpulang7.getTable().getSelectedRow()!= -1){
                    Obat7.setText(rmcariobatpulang7.getTable().getValueAt(rmcariobatpulang7.getTable().getSelectedRow(),2).toString());
                    Nama7.setText(rmcariobatpulang7.getTable().getValueAt(rmcariobatpulang7.getTable().getSelectedRow(),3).toString());
                    Jumlah7.setText(rmcariobatpulang7.getTable().getValueAt(rmcariobatpulang7.getTable().getSelectedRow(),4).toString());
                    Dosis7.setText(rmcariobatpulang7.getTable().getValueAt(rmcariobatpulang7.getTable().getSelectedRow(),5).toString());
                    Obat7.requestFocus();
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
        
        rmcariobatpulang8.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariobatpulang.getTable().getSelectedRow()!= -1){
                    Obat8.setText(rmcariobatpulang8.getTable().getValueAt(rmcariobatpulang8.getTable().getSelectedRow(),2).toString());
                    Nama8.setText(rmcariobatpulang8.getTable().getValueAt(rmcariobatpulang8.getTable().getSelectedRow(),3).toString());
                    Jumlah8.setText(rmcariobatpulang8.getTable().getValueAt(rmcariobatpulang8.getTable().getSelectedRow(),4).toString());
                    Dosis8.setText(rmcariobatpulang8.getTable().getValueAt(rmcariobatpulang8.getTable().getSelectedRow(),5).toString());
                    Obat8.requestFocus();
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
        
        rmcariobatpulang9.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariobatpulang9.getTable().getSelectedRow()!= -1){
                    Obat9.setText(rmcariobatpulang9.getTable().getValueAt(rmcariobatpulang9.getTable().getSelectedRow(),2).toString());
                    Nama9.setText(rmcariobatpulang9.getTable().getValueAt(rmcariobatpulang9.getTable().getSelectedRow(),3).toString());
                    Jumlah9.setText(rmcariobatpulang9.getTable().getValueAt(rmcariobatpulang9.getTable().getSelectedRow(),4).toString());
                    Dosis9.setText(rmcariobatpulang9.getTable().getValueAt(rmcariobatpulang9.getTable().getSelectedRow(),5).toString());
                    Obat9.requestFocus();
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
        
        rmcarilabpending.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcarilabpending.getTable().getSelectedRow()!= -1){
                    LabBelum.append(rmcarilabpending.getTable().getValueAt(rmcarilabpending.getTable().getSelectedRow(),2).toString()+", ");
                    LabBelum.requestFocus();
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
        
//        rmcariterapiranap.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if(rmcariterapiranap.getTable().getSelectedRow()!= -1){
//                    Terapi.append(rmcariterapiranap.getTable().getValueAt(rmcariterapiranap.getTable().getSelectedRow(),2).toString()+", ");
//                    Terapi.requestFocus();
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
        
        rmcaridiet.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcaridiet.getTable().getSelectedRow()!= -1){
                    Diet.append(rmcaridiet.getTable().getValueAt(rmcaridiet.getTable().getSelectedRow(),2).toString()+", ");
                    Diet.requestFocus();
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
        
        cariobat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariobat.getTable().getSelectedRow()!= -1){
                    Terapi.append(cariobat.getTable().getValueAt(cariobat.getTable().getSelectedRow(),2).toString()+", ");
                    Terapi.requestFocus();
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
        MnDigitalTTE = new javax.swing.JMenuItem();
        label12 = new widget.Label();
        Obat1 = new widget.TextBox();
        Nama1 = new widget.TextBox();
        Jumlah1 = new widget.TextBox();
        Dosis1 = new widget.TextBox();
        PObat = new widget.Button();
        label20 = new widget.Label();
        Obat2 = new widget.TextBox();
        Nama2 = new widget.TextBox();
        Jumlah2 = new widget.TextBox();
        Dosis2 = new widget.TextBox();
        PObat2 = new widget.Button();
        PObat3 = new widget.Button();
        PObat4 = new widget.Button();
        PObat5 = new widget.Button();
        PObat6 = new widget.Button();
        PObat7 = new widget.Button();
        PObat8 = new widget.Button();
        PObat9 = new widget.Button();
        Dosis9 = new widget.TextBox();
        Dosis8 = new widget.TextBox();
        Dosis7 = new widget.TextBox();
        Dosis6 = new widget.TextBox();
        Dosis5 = new widget.TextBox();
        Dosis4 = new widget.TextBox();
        Dosis3 = new widget.TextBox();
        Jumlah3 = new widget.TextBox();
        Jumlah4 = new widget.TextBox();
        Jumlah5 = new widget.TextBox();
        Jumlah6 = new widget.TextBox();
        Jumlah7 = new widget.TextBox();
        Jumlah8 = new widget.TextBox();
        Jumlah9 = new widget.TextBox();
        Nama9 = new widget.TextBox();
        Nama8 = new widget.TextBox();
        Nama7 = new widget.TextBox();
        Nama6 = new widget.TextBox();
        Nama5 = new widget.TextBox();
        Nama4 = new widget.TextBox();
        Nama3 = new widget.TextBox();
        Obat3 = new widget.TextBox();
        label21 = new widget.Label();
        Obat4 = new widget.TextBox();
        label22 = new widget.Label();
        label23 = new widget.Label();
        Obat5 = new widget.TextBox();
        Obat6 = new widget.TextBox();
        label24 = new widget.Label();
        label25 = new widget.Label();
        Obat7 = new widget.TextBox();
        Obat8 = new widget.TextBox();
        label26 = new widget.Label();
        label27 = new widget.Label();
        Obat9 = new widget.TextBox();
        PObat1 = new widget.Button();
        label13 = new widget.Label();
        Kontrol = new widget.Tanggal();
        label11 = new widget.Label();
        BtnDokter22 = new widget.Button();
        KodeDiagnosaSekunder7 = new widget.TextBox();
        DiagnosaSekunder7 = new widget.TextBox();
        BtnDokter21 = new widget.Button();
        KodeDiagnosaSekunder6 = new widget.TextBox();
        DiagnosaSekunder6 = new widget.TextBox();
        jLabel50 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel47 = new widget.Label();
        DiagnosaSekunder5 = new widget.TextBox();
        KodeDiagnosaSekunder5 = new widget.TextBox();
        BtnDokter20 = new widget.Button();
        jLabel52 = new widget.Label();
        ProsedurSekunder5 = new widget.TextBox();
        KodeProsedurSekunder5 = new widget.TextBox();
        BtnDokter24 = new widget.Button();
        BtnDokter25 = new widget.Button();
        KodeProsedurSekunder6 = new widget.TextBox();
        ProsedurSekunder6 = new widget.TextBox();
        jLabel53 = new widget.Label();
        jLabel23 = new widget.Label();
        Alasan = new widget.TextBox();
        CaraBayar = new widget.TextBox();
        KeadaanLainnya = new widget.TextBox();
        Keadaan = new widget.ComboBox();
        jLabel36 = new widget.Label();
        jLabel31 = new widget.Label();
        KodeDiagnosaUtama = new widget.TextBox();
        BtnDokter6 = new widget.Button();
        BtnDokter7 = new widget.Button();
        KodeDiagnosaSekunder1 = new widget.TextBox();
        KodeDiagnosaSekunder2 = new widget.TextBox();
        BtnDokter8 = new widget.Button();
        BtnDokter9 = new widget.Button();
        KodeDiagnosaSekunder3 = new widget.TextBox();
        KodeDiagnosaSekunder4 = new widget.TextBox();
        BtnDokter10 = new widget.Button();
        DiagnosaSekunder4 = new widget.TextBox();
        DiagnosaSekunder3 = new widget.TextBox();
        DiagnosaSekunder2 = new widget.TextBox();
        DiagnosaSekunder1 = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel26 = new widget.Label();
        jLabel28 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel51 = new widget.Label();
        ProsedurSekunder4 = new widget.TextBox();
        ProsedurSekunder3 = new widget.TextBox();
        ProsedurSekunder2 = new widget.TextBox();
        ProsedurSekunder1 = new widget.TextBox();
        KodeProsedurSekunder4 = new widget.TextBox();
        BtnDokter23 = new widget.Button();
        BtnDokter14 = new widget.Button();
        KodeProsedurSekunder3 = new widget.TextBox();
        KodeProsedurSekunder2 = new widget.TextBox();
        BtnDokter13 = new widget.Button();
        BtnDokter12 = new widget.Button();
        KodeProsedurSekunder1 = new widget.TextBox();
        KodeProsedurUtama = new widget.TextBox();
        BtnDokter11 = new widget.Button();
        JamKeluar = new widget.Tanggal();
        scrollPane11 = new widget.ScrollPane();
        DiagnosaKlinis = new widget.TextArea();
        jLabel54 = new widget.Label();
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
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        scrollPane2 = new widget.ScrollPane();
        Keluhan = new widget.TextArea();
        jLabel5 = new widget.Label();
        jLabel8 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        JalannyaPenyakit = new widget.TextArea();
        jLabel9 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
        jLabel10 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        HasilLaborat = new widget.TextArea();
        jLabel29 = new widget.Label();
        label14 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        BtnDokter1 = new widget.Button();
        BtnDokter2 = new widget.Button();
        BtnDokter3 = new widget.Button();
        jLabel37 = new widget.Label();
        CaraKeluar = new widget.ComboBox();
        BtnDokter5 = new widget.Button();
        jLabel13 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel14 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel15 = new widget.Label();
        Ruang = new widget.TextBox();
        jLabel16 = new widget.Label();
        Masuk = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        JamMasuk = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        label15 = new widget.Label();
        KodeDokter1 = new widget.TextBox();
        NamaDokter1 = new widget.TextBox();
        BtnDokter15 = new widget.Button();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel24 = new widget.Label();
        DiagnosaAwal = new widget.TextBox();
        jLabel12 = new widget.Label();
        BtnDokter16 = new widget.Button();
        scrollPane7 = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        jLabel38 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel39 = new widget.Label();
        KeluarLainnya = new widget.TextBox();
        jLabel40 = new widget.Label();
        BtnDokter17 = new widget.Button();
        scrollPane8 = new widget.ScrollPane();
        Diet = new widget.TextArea();
        jLabel41 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        label16 = new widget.Label();
        jLabel43 = new widget.Label();
        Sudah = new widget.ComboBox();
        CaraBayar1 = new widget.TextBox();
        Ruang1 = new widget.TextBox();
        scrollPane10 = new widget.ScrollPane();
        LabBelum = new widget.TextArea();
        jLabel11 = new widget.Label();
        jLabel44 = new widget.Label();
        BtnDokter4 = new widget.Button();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        BtnDokter19 = new widget.Button();
        BtnImplementasiKeperawatanRanap = new widget.Button();
        jLabel49 = new widget.Label();
        Keluar = new widget.Tanggal();
        scrollPane13 = new widget.ScrollPane();
        DiagnosaUtama = new widget.TextArea();
        scrollPane14 = new widget.ScrollPane();
        ProsedurUtama = new widget.TextArea();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        scrollPane12 = new widget.ScrollPane();
        Obat = new widget.TextArea();
        PengobatanLainnya = new widget.TextBox();
        Pengobatan = new widget.ComboBox();
        jLabel42 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLaporanResume.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResume.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResume.setText("Laporan Resume Pasien");
        MnLaporanResume.setName("MnLaporanResume"); // NOI18N
        MnLaporanResume.setPreferredSize(new java.awt.Dimension(220, 26));
        MnLaporanResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanResumeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanResume);

        MnDigitalTTE.setBackground(new java.awt.Color(255, 255, 254));
        MnDigitalTTE.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDigitalTTE.setForeground(new java.awt.Color(50, 50, 50));
        MnDigitalTTE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDigitalTTE.setText("Sign Digital Signature");
        MnDigitalTTE.setToolTipText("");
        MnDigitalTTE.setName("MnDigitalTTE"); // NOI18N
        MnDigitalTTE.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDigitalTTE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDigitalTTEActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDigitalTTE);

        label12.setText("1.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));

        Obat1.setHighlighter(null);
        Obat1.setName("Obat1"); // NOI18N

        Nama1.setHighlighter(null);
        Nama1.setName("Nama1"); // NOI18N

        Jumlah1.setHighlighter(null);
        Jumlah1.setName("Jumlah1"); // NOI18N

        Dosis1.setHighlighter(null);
        Dosis1.setName("Dosis1"); // NOI18N

        PObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        PObat.setMnemonic('2');
        PObat.setToolTipText("Alt+2");
        PObat.setName("PObat"); // NOI18N
        PObat.setPreferredSize(new java.awt.Dimension(28, 23));
        PObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PObatActionPerformed(evt);
            }
        });

        label20.setText("2.");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));

        Obat2.setHighlighter(null);
        Obat2.setName("Obat2"); // NOI18N

        Nama2.setHighlighter(null);
        Nama2.setName("Nama2"); // NOI18N

        Jumlah2.setHighlighter(null);
        Jumlah2.setName("Jumlah2"); // NOI18N

        Dosis2.setHighlighter(null);
        Dosis2.setName("Dosis2"); // NOI18N

        PObat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        PObat2.setMnemonic('2');
        PObat2.setToolTipText("Alt+2");
        PObat2.setName("PObat2"); // NOI18N
        PObat2.setPreferredSize(new java.awt.Dimension(28, 23));
        PObat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PObat2ActionPerformed(evt);
            }
        });

        PObat3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        PObat3.setMnemonic('2');
        PObat3.setToolTipText("Alt+2");
        PObat3.setName("PObat3"); // NOI18N
        PObat3.setPreferredSize(new java.awt.Dimension(28, 23));
        PObat3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PObat3ActionPerformed(evt);
            }
        });

        PObat4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        PObat4.setMnemonic('2');
        PObat4.setToolTipText("Alt+2");
        PObat4.setName("PObat4"); // NOI18N
        PObat4.setPreferredSize(new java.awt.Dimension(28, 23));
        PObat4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PObat4ActionPerformed(evt);
            }
        });

        PObat5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        PObat5.setMnemonic('2');
        PObat5.setToolTipText("Alt+2");
        PObat5.setName("PObat5"); // NOI18N
        PObat5.setPreferredSize(new java.awt.Dimension(28, 23));
        PObat5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PObat5ActionPerformed(evt);
            }
        });

        PObat6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        PObat6.setMnemonic('2');
        PObat6.setToolTipText("Alt+2");
        PObat6.setName("PObat6"); // NOI18N
        PObat6.setPreferredSize(new java.awt.Dimension(28, 23));
        PObat6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PObat6ActionPerformed(evt);
            }
        });

        PObat7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        PObat7.setMnemonic('2');
        PObat7.setToolTipText("Alt+2");
        PObat7.setName("PObat7"); // NOI18N
        PObat7.setPreferredSize(new java.awt.Dimension(28, 23));
        PObat7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PObat7ActionPerformed(evt);
            }
        });

        PObat8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        PObat8.setMnemonic('2');
        PObat8.setToolTipText("Alt+2");
        PObat8.setName("PObat8"); // NOI18N
        PObat8.setPreferredSize(new java.awt.Dimension(28, 23));
        PObat8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PObat8ActionPerformed(evt);
            }
        });

        PObat9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        PObat9.setMnemonic('2');
        PObat9.setToolTipText("Alt+2");
        PObat9.setName("PObat9"); // NOI18N
        PObat9.setPreferredSize(new java.awt.Dimension(28, 23));
        PObat9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PObat9ActionPerformed(evt);
            }
        });

        Dosis9.setHighlighter(null);
        Dosis9.setName("Dosis9"); // NOI18N

        Dosis8.setHighlighter(null);
        Dosis8.setName("Dosis8"); // NOI18N

        Dosis7.setHighlighter(null);
        Dosis7.setName("Dosis7"); // NOI18N

        Dosis6.setHighlighter(null);
        Dosis6.setName("Dosis6"); // NOI18N

        Dosis5.setHighlighter(null);
        Dosis5.setName("Dosis5"); // NOI18N

        Dosis4.setHighlighter(null);
        Dosis4.setName("Dosis4"); // NOI18N

        Dosis3.setHighlighter(null);
        Dosis3.setName("Dosis3"); // NOI18N

        Jumlah3.setHighlighter(null);
        Jumlah3.setName("Jumlah3"); // NOI18N

        Jumlah4.setHighlighter(null);
        Jumlah4.setName("Jumlah4"); // NOI18N

        Jumlah5.setHighlighter(null);
        Jumlah5.setName("Jumlah5"); // NOI18N

        Jumlah6.setHighlighter(null);
        Jumlah6.setName("Jumlah6"); // NOI18N

        Jumlah7.setHighlighter(null);
        Jumlah7.setName("Jumlah7"); // NOI18N

        Jumlah8.setHighlighter(null);
        Jumlah8.setName("Jumlah8"); // NOI18N

        Jumlah9.setHighlighter(null);
        Jumlah9.setName("Jumlah9"); // NOI18N

        Nama9.setHighlighter(null);
        Nama9.setName("Nama9"); // NOI18N

        Nama8.setHighlighter(null);
        Nama8.setName("Nama8"); // NOI18N

        Nama7.setHighlighter(null);
        Nama7.setName("Nama7"); // NOI18N

        Nama6.setHighlighter(null);
        Nama6.setName("Nama6"); // NOI18N

        Nama5.setHighlighter(null);
        Nama5.setName("Nama5"); // NOI18N

        Nama4.setHighlighter(null);
        Nama4.setName("Nama4"); // NOI18N

        Nama3.setHighlighter(null);
        Nama3.setName("Nama3"); // NOI18N

        Obat3.setHighlighter(null);
        Obat3.setName("Obat3"); // NOI18N

        label21.setText("3.");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));

        Obat4.setHighlighter(null);
        Obat4.setName("Obat4"); // NOI18N

        label22.setText("4.");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));

        label23.setText("5.");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));

        Obat5.setHighlighter(null);
        Obat5.setName("Obat5"); // NOI18N

        Obat6.setHighlighter(null);
        Obat6.setName("Obat6"); // NOI18N

        label24.setText("6.");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));

        label25.setText("7.");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));

        Obat7.setHighlighter(null);
        Obat7.setName("Obat7"); // NOI18N

        Obat8.setHighlighter(null);
        Obat8.setName("Obat8"); // NOI18N

        label26.setText("8.");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));

        label27.setText("9.");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));

        Obat9.setHighlighter(null);
        Obat9.setName("Obat9"); // NOI18N

        PObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        PObat1.setMnemonic('2');
        PObat1.setToolTipText("Alt+2");
        PObat1.setName("PObat1"); // NOI18N
        PObat1.setPreferredSize(new java.awt.Dimension(28, 23));
        PObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PObat1ActionPerformed(evt);
            }
        });

        label13.setText("Tanggal & Jam Kontrol :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));

        Kontrol.setForeground(new java.awt.Color(50, 70, 50));
        Kontrol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-11-2024 11:22:53" }));
        Kontrol.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Kontrol.setName("Kontrol"); // NOI18N
        Kontrol.setOpaque(false);
        Kontrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KontrolActionPerformed(evt);
            }
        });
        Kontrol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontrolKeyPressed(evt);
            }
        });

        label11.setText("WIB");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));

        BtnDokter22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter22.setMnemonic('2');
        BtnDokter22.setToolTipText("Alt+2");
        BtnDokter22.setName("BtnDokter22"); // NOI18N
        BtnDokter22.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter22ActionPerformed(evt);
            }
        });

        KodeDiagnosaSekunder7.setEditable(false);
        KodeDiagnosaSekunder7.setHighlighter(null);
        KodeDiagnosaSekunder7.setName("KodeDiagnosaSekunder7"); // NOI18N
        KodeDiagnosaSekunder7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder7KeyPressed(evt);
            }
        });

        DiagnosaSekunder7.setEditable(false);
        DiagnosaSekunder7.setHighlighter(null);
        DiagnosaSekunder7.setName("DiagnosaSekunder7"); // NOI18N
        DiagnosaSekunder7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder7KeyPressed(evt);
            }
        });

        BtnDokter21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter21.setMnemonic('2');
        BtnDokter21.setToolTipText("Alt+2");
        BtnDokter21.setName("BtnDokter21"); // NOI18N
        BtnDokter21.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter21ActionPerformed(evt);
            }
        });

        KodeDiagnosaSekunder6.setEditable(false);
        KodeDiagnosaSekunder6.setHighlighter(null);
        KodeDiagnosaSekunder6.setName("KodeDiagnosaSekunder6"); // NOI18N
        KodeDiagnosaSekunder6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder6KeyPressed(evt);
            }
        });

        DiagnosaSekunder6.setEditable(false);
        DiagnosaSekunder6.setHighlighter(null);
        DiagnosaSekunder6.setName("DiagnosaSekunder6"); // NOI18N
        DiagnosaSekunder6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder6KeyPressed(evt);
            }
        });

        jLabel50.setText("Diagnosa Sekunder 7 :");
        jLabel50.setName("jLabel50"); // NOI18N

        jLabel48.setText("Diagnosa Sekunder 6 :");
        jLabel48.setName("jLabel48"); // NOI18N

        jLabel47.setText("Diagnosa Sekunder 5 :");
        jLabel47.setName("jLabel47"); // NOI18N

        DiagnosaSekunder5.setEditable(false);
        DiagnosaSekunder5.setHighlighter(null);
        DiagnosaSekunder5.setName("DiagnosaSekunder5"); // NOI18N
        DiagnosaSekunder5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder5KeyPressed(evt);
            }
        });

        KodeDiagnosaSekunder5.setEditable(false);
        KodeDiagnosaSekunder5.setHighlighter(null);
        KodeDiagnosaSekunder5.setName("KodeDiagnosaSekunder5"); // NOI18N
        KodeDiagnosaSekunder5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder5KeyPressed(evt);
            }
        });

        BtnDokter20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter20.setMnemonic('2');
        BtnDokter20.setToolTipText("Alt+2");
        BtnDokter20.setName("BtnDokter20"); // NOI18N
        BtnDokter20.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter20ActionPerformed(evt);
            }
        });

        jLabel52.setText("Prosedur 6 :");
        jLabel52.setName("jLabel52"); // NOI18N

        ProsedurSekunder5.setEditable(false);
        ProsedurSekunder5.setHighlighter(null);
        ProsedurSekunder5.setName("ProsedurSekunder5"); // NOI18N
        ProsedurSekunder5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder5KeyPressed(evt);
            }
        });

        KodeProsedurSekunder5.setEditable(false);
        KodeProsedurSekunder5.setHighlighter(null);
        KodeProsedurSekunder5.setName("KodeProsedurSekunder5"); // NOI18N
        KodeProsedurSekunder5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder5KeyPressed(evt);
            }
        });

        BtnDokter24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter24.setMnemonic('2');
        BtnDokter24.setToolTipText("Alt+2");
        BtnDokter24.setName("BtnDokter24"); // NOI18N
        BtnDokter24.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter24ActionPerformed(evt);
            }
        });

        BtnDokter25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter25.setMnemonic('2');
        BtnDokter25.setToolTipText("Alt+2");
        BtnDokter25.setName("BtnDokter25"); // NOI18N
        BtnDokter25.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter25ActionPerformed(evt);
            }
        });

        KodeProsedurSekunder6.setEditable(false);
        KodeProsedurSekunder6.setHighlighter(null);
        KodeProsedurSekunder6.setName("KodeProsedurSekunder6"); // NOI18N
        KodeProsedurSekunder6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder6KeyPressed(evt);
            }
        });

        ProsedurSekunder6.setEditable(false);
        ProsedurSekunder6.setHighlighter(null);
        ProsedurSekunder6.setName("ProsedurSekunder6"); // NOI18N
        ProsedurSekunder6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder6KeyPressed(evt);
            }
        });

        jLabel53.setText("Prosedur 7 :");
        jLabel53.setName("jLabel53"); // NOI18N

        jLabel23.setText("Alasan Masuk Dirawat :");
        jLabel23.setName("jLabel23"); // NOI18N

        Alasan.setHighlighter(null);
        Alasan.setName("Alasan"); // NOI18N
        Alasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlasanActionPerformed(evt);
            }
        });

        CaraBayar.setEditable(false);
        CaraBayar.setHighlighter(null);
        CaraBayar.setName("CaraBayar"); // NOI18N

        KeadaanLainnya.setHighlighter(null);
        KeadaanLainnya.setName("KeadaanLainnya"); // NOI18N

        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Membaik", "Sembuh", "Keadaan Khusus", "Meninggal" }));
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KeadaanActionPerformed(evt);
            }
        });
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });

        jLabel36.setText("Keadaan Pulang :");
        jLabel36.setName("jLabel36"); // NOI18N

        jLabel31.setText("Kode ICD :");
        jLabel31.setName("jLabel31"); // NOI18N

        KodeDiagnosaUtama.setEditable(false);
        KodeDiagnosaUtama.setHighlighter(null);
        KodeDiagnosaUtama.setName("KodeDiagnosaUtama"); // NOI18N
        KodeDiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaUtamaKeyPressed(evt);
            }
        });

        BtnDokter6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter6.setMnemonic('2');
        BtnDokter6.setToolTipText("Alt+2");
        BtnDokter6.setName("BtnDokter6"); // NOI18N
        BtnDokter6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter6ActionPerformed(evt);
            }
        });

        BtnDokter7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter7.setMnemonic('2');
        BtnDokter7.setToolTipText("Alt+2");
        BtnDokter7.setName("BtnDokter7"); // NOI18N
        BtnDokter7.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter7ActionPerformed(evt);
            }
        });

        KodeDiagnosaSekunder1.setEditable(true);
        KodeDiagnosaSekunder1.setHighlighter(null);
        KodeDiagnosaSekunder1.setName("KodeDiagnosaSekunder1"); // NOI18N
        KodeDiagnosaSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder1KeyPressed(evt);
            }
        });

        KodeDiagnosaSekunder2.setEditable(true);
        KodeDiagnosaSekunder2.setHighlighter(null);
        KodeDiagnosaSekunder2.setName("KodeDiagnosaSekunder2"); // NOI18N
        KodeDiagnosaSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder2KeyPressed(evt);
            }
        });

        BtnDokter8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter8.setMnemonic('2');
        BtnDokter8.setToolTipText("Alt+2");
        BtnDokter8.setName("BtnDokter8"); // NOI18N
        BtnDokter8.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter8ActionPerformed(evt);
            }
        });

        BtnDokter9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter9.setMnemonic('2');
        BtnDokter9.setToolTipText("Alt+2");
        BtnDokter9.setName("BtnDokter9"); // NOI18N
        BtnDokter9.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter9ActionPerformed(evt);
            }
        });

        KodeDiagnosaSekunder3.setEditable(true);
        KodeDiagnosaSekunder3.setHighlighter(null);
        KodeDiagnosaSekunder3.setName("KodeDiagnosaSekunder3"); // NOI18N
        KodeDiagnosaSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder3KeyPressed(evt);
            }
        });

        KodeDiagnosaSekunder4.setEditable(true);
        KodeDiagnosaSekunder4.setHighlighter(null);
        KodeDiagnosaSekunder4.setName("KodeDiagnosaSekunder4"); // NOI18N
        KodeDiagnosaSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder4KeyPressed(evt);
            }
        });

        BtnDokter10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter10.setMnemonic('2');
        BtnDokter10.setToolTipText("Alt+2");
        BtnDokter10.setName("BtnDokter10"); // NOI18N
        BtnDokter10.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter10ActionPerformed(evt);
            }
        });

        DiagnosaSekunder4.setEditable(true);
        DiagnosaSekunder4.setHighlighter(null);
        DiagnosaSekunder4.setName("DiagnosaSekunder4"); // NOI18N
        DiagnosaSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder4KeyPressed(evt);
            }
        });

        DiagnosaSekunder3.setEditable(true);
        DiagnosaSekunder3.setHighlighter(null);
        DiagnosaSekunder3.setName("DiagnosaSekunder3"); // NOI18N
        DiagnosaSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder3KeyPressed(evt);
            }
        });

        DiagnosaSekunder2.setEditable(true);
        DiagnosaSekunder2.setHighlighter(null);
        DiagnosaSekunder2.setName("DiagnosaSekunder2"); // NOI18N
        DiagnosaSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder2KeyPressed(evt);
            }
        });

        DiagnosaSekunder1.setEditable(true);
        DiagnosaSekunder1.setToolTipText("");
        DiagnosaSekunder1.setHighlighter(null);
        DiagnosaSekunder1.setName("DiagnosaSekunder1"); // NOI18N
        DiagnosaSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder1KeyPressed(evt);
            }
        });

        jLabel30.setText("Diagnosa Sekunder 1 :");
        jLabel30.setName("jLabel30"); // NOI18N

        jLabel27.setText("Diagnosa Utama :");
        jLabel27.setName("jLabel27"); // NOI18N

        jLabel25.setText("Diagnosa Sekunder 2 :");
        jLabel25.setName("jLabel25"); // NOI18N

        jLabel26.setText("Diagnosa Sekunder 3 :");
        jLabel26.setName("jLabel26"); // NOI18N

        jLabel28.setText("Diagnosa Sekunder 4 :");
        jLabel28.setName("jLabel28"); // NOI18N

        jLabel32.setText("Prosedur 1 :");
        jLabel32.setName("jLabel32"); // NOI18N

        jLabel33.setText("Prosedur 2 :");
        jLabel33.setName("jLabel33"); // NOI18N

        jLabel34.setText("Prosedur 3 :");
        jLabel34.setName("jLabel34"); // NOI18N

        jLabel35.setText("Prosedur 4 :");
        jLabel35.setName("jLabel35"); // NOI18N

        jLabel51.setText("Prosedur 5 :");
        jLabel51.setName("jLabel51"); // NOI18N

        ProsedurSekunder4.setEditable(false);
        ProsedurSekunder4.setHighlighter(null);
        ProsedurSekunder4.setName("ProsedurSekunder4"); // NOI18N
        ProsedurSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder4KeyPressed(evt);
            }
        });

        ProsedurSekunder3.setEditable(false);
        ProsedurSekunder3.setHighlighter(null);
        ProsedurSekunder3.setName("ProsedurSekunder3"); // NOI18N
        ProsedurSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder3KeyPressed(evt);
            }
        });

        ProsedurSekunder2.setEditable(false);
        ProsedurSekunder2.setHighlighter(null);
        ProsedurSekunder2.setName("ProsedurSekunder2"); // NOI18N
        ProsedurSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder2KeyPressed(evt);
            }
        });

        ProsedurSekunder1.setEditable(false);
        ProsedurSekunder1.setHighlighter(null);
        ProsedurSekunder1.setName("ProsedurSekunder1"); // NOI18N
        ProsedurSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder1KeyPressed(evt);
            }
        });

        KodeProsedurSekunder4.setEditable(false);
        KodeProsedurSekunder4.setHighlighter(null);
        KodeProsedurSekunder4.setName("KodeProsedurSekunder4"); // NOI18N
        KodeProsedurSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder4KeyPressed(evt);
            }
        });

        BtnDokter23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter23.setMnemonic('2');
        BtnDokter23.setToolTipText("Alt+2");
        BtnDokter23.setName("BtnDokter23"); // NOI18N
        BtnDokter23.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter23ActionPerformed(evt);
            }
        });

        BtnDokter14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter14.setMnemonic('2');
        BtnDokter14.setToolTipText("Alt+2");
        BtnDokter14.setName("BtnDokter14"); // NOI18N
        BtnDokter14.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter14ActionPerformed(evt);
            }
        });

        KodeProsedurSekunder3.setEditable(false);
        KodeProsedurSekunder3.setHighlighter(null);
        KodeProsedurSekunder3.setName("KodeProsedurSekunder3"); // NOI18N
        KodeProsedurSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder3KeyPressed(evt);
            }
        });

        KodeProsedurSekunder2.setEditable(false);
        KodeProsedurSekunder2.setHighlighter(null);
        KodeProsedurSekunder2.setName("KodeProsedurSekunder2"); // NOI18N
        KodeProsedurSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder2KeyPressed(evt);
            }
        });

        BtnDokter13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter13.setMnemonic('2');
        BtnDokter13.setToolTipText("Alt+2");
        BtnDokter13.setName("BtnDokter13"); // NOI18N
        BtnDokter13.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter13ActionPerformed(evt);
            }
        });

        BtnDokter12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter12.setMnemonic('2');
        BtnDokter12.setToolTipText("Alt+2");
        BtnDokter12.setName("BtnDokter12"); // NOI18N
        BtnDokter12.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter12ActionPerformed(evt);
            }
        });

        KodeProsedurSekunder1.setEditable(false);
        KodeProsedurSekunder1.setHighlighter(null);
        KodeProsedurSekunder1.setName("KodeProsedurSekunder1"); // NOI18N
        KodeProsedurSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder1KeyPressed(evt);
            }
        });

        KodeProsedurUtama.setEditable(false);
        KodeProsedurUtama.setHighlighter(null);
        KodeProsedurUtama.setName("KodeProsedurUtama"); // NOI18N
        KodeProsedurUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurUtamaKeyPressed(evt);
            }
        });

        BtnDokter11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter11.setMnemonic('2');
        BtnDokter11.setToolTipText("Alt+2");
        BtnDokter11.setName("BtnDokter11"); // NOI18N
        BtnDokter11.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter11ActionPerformed(evt);
            }
        });

        JamKeluar.setForeground(new java.awt.Color(50, 70, 50));
        JamKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11:22:53" }));
        JamKeluar.setDisplayFormat("HH:mm:ss");
        JamKeluar.setName("JamKeluar"); // NOI18N
        JamKeluar.setOpaque(false);
        JamKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JamKeluarActionPerformed(evt);
            }
        });
        JamKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeluarKeyPressed(evt);
            }
        });

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        DiagnosaKlinis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaKlinis.setColumns(20);
        DiagnosaKlinis.setRows(5);
        DiagnosaKlinis.setName("DiagnosaKlinis"); // NOI18N
        DiagnosaKlinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKlinisKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(DiagnosaKlinis);

        jLabel54.setText("Diagnosa Klinis :");
        jLabel54.setName("jLabel54"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Resume Medis Pasien Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
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
        BtnBatal.setText("Reset");
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-11-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-11-2024" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1270));
        FormInput.setLayout(null);

        jLabel4.setText("SOAP :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(810, 190, 40, 23);

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

        Keluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Keluhan.setColumns(20);
        Keluhan.setRows(5);
        Keluhan.setName("Keluhan"); // NOI18N
        Keluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Keluhan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(250, 180, 541, 60);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 100, 23);

        jLabel8.setText("Pemeriksaan Fisik :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 250, 240, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        JalannyaPenyakit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        JalannyaPenyakit.setColumns(20);
        JalannyaPenyakit.setRows(5);
        JalannyaPenyakit.setName("JalannyaPenyakit"); // NOI18N
        JalannyaPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalannyaPenyakitKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(JalannyaPenyakit);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(250, 250, 541, 60);

        jLabel9.setText("Pemeriksaan Penunjang Rad Terpenting :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 320, 240, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        PemeriksaanPenunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanPenunjang.setColumns(20);
        PemeriksaanPenunjang.setRows(5);
        PemeriksaanPenunjang.setName("PemeriksaanPenunjang"); // NOI18N
        PemeriksaanPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPenunjangKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(PemeriksaanPenunjang);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(250, 320, 541, 50);

        jLabel10.setText("Pemeriksaan Penunjang Lab Terpenting:");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 380, 240, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        HasilLaborat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        HasilLaborat.setColumns(20);
        HasilLaborat.setRows(5);
        HasilLaborat.setName("HasilLaborat"); // NOI18N
        HasilLaborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilLaboratKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(HasilLaborat);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(250, 380, 541, 50);

        jLabel29.setText("Diagnosa Akhir (ICD10) :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 540, 150, 23);

        label14.setText("Dokter P.J. :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 100, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setName("KodeDokter"); // NOI18N
        KodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterKeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(104, 40, 100, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        NamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(207, 40, 290, 23);

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
        BtnDokter.setBounds(500, 40, 28, 23);

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
        BtnDokter1.setBounds(850, 190, 28, 23);

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
        FormInput.add(BtnDokter2);
        BtnDokter2.setBounds(210, 350, 28, 23);

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
        BtnDokter3.setBounds(210, 410, 28, 23);

        jLabel37.setText("Cara Keluar :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(30, 970, 140, 23);

        CaraKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Atas Izin Dokter", "Pindah RS", "Pulang Atas Permintaan Sendiri", "Meninggal", "Lainnya" }));
        CaraKeluar.setName("CaraKeluar"); // NOI18N
        CaraKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CaraKeluarActionPerformed(evt);
            }
        });
        CaraKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraKeluarKeyPressed(evt);
            }
        });
        FormInput.add(CaraKeluar);
        CaraKeluar.setBounds(180, 970, 200, 23);

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
        FormInput.add(BtnDokter5);
        BtnDokter5.setBounds(850, 260, 28, 23);

        jLabel13.setText("Tgl.Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(520, 40, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(580, 40, 80, 23);

        jLabel14.setText("J.K. :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(670, 40, 30, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(700, 40, 80, 23);

        jLabel15.setText("Ruang/Kelas :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(30, 70, 70, 23);

        Ruang.setEditable(false);
        Ruang.setHighlighter(null);
        Ruang.setName("Ruang"); // NOI18N
        FormInput.add(Ruang);
        Ruang.setBounds(110, 70, 70, 23);

        jLabel16.setText("Tanggal Masuk :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(10, 100, 90, 23);

        Masuk.setEditable(false);
        Masuk.setHighlighter(null);
        Masuk.setName("Masuk"); // NOI18N
        FormInput.add(Masuk);
        Masuk.setBounds(110, 100, 90, 23);

        jLabel17.setText("Tanggal Keluar :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(10, 130, 90, 23);

        jLabel18.setText("Jam Masuk :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(200, 100, 70, 23);

        JamMasuk.setEditable(false);
        JamMasuk.setHighlighter(null);
        JamMasuk.setName("JamMasuk"); // NOI18N
        FormInput.add(JamMasuk);
        JamMasuk.setBounds(280, 100, 90, 23);

        jLabel20.setText("Jam Keluar :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(200, 130, 70, 23);

        jLabel22.setText("Cara Bayar :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(430, 100, 120, 23);

        label15.setText("Dokter Pengirim :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(350, 70, 100, 23);

        KodeDokter1.setEditable(false);
        KodeDokter1.setName("KodeDokter1"); // NOI18N
        KodeDokter1.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokter1KeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter1);
        KodeDokter1.setBounds(450, 70, 90, 23);

        NamaDokter1.setEditable(false);
        NamaDokter1.setName("NamaDokter1"); // NOI18N
        NamaDokter1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter1);
        NamaDokter1.setBounds(550, 70, 230, 23);

        BtnDokter15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter15.setMnemonic('2');
        BtnDokter15.setToolTipText("Alt+2");
        BtnDokter15.setName("BtnDokter15"); // NOI18N
        BtnDokter15.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter15ActionPerformed(evt);
            }
        });
        BtnDokter15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter15KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter15);
        BtnDokter15.setBounds(780, 70, 28, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 170, 880, 1);

        jLabel24.setText("Diagnosa Masuk :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(460, 130, 90, 23);

        DiagnosaAwal.setHighlighter(null);
        DiagnosaAwal.setName("DiagnosaAwal"); // NOI18N
        FormInput.add(DiagnosaAwal);
        DiagnosaAwal.setBounds(550, 130, 310, 23);

        jLabel12.setText("Terapi  Pengobatan Selama Di RS:");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(50, 440, 180, 23);

        BtnDokter16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter16.setMnemonic('2');
        BtnDokter16.setToolTipText("Alt+2");
        BtnDokter16.setName("BtnDokter16"); // NOI18N
        BtnDokter16.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter16ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter16);
        BtnDokter16.setBounds(210, 470, 28, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Terapi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Terapi.setColumns(20);
        Terapi.setRows(5);
        Terapi.setName("Terapi"); // NOI18N
        Terapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(Terapi);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(250, 440, 541, 50);

        jLabel38.setText("Alergi (Reaksi Obat) :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(120, 740, 120, 23);

        Alergi.setHighlighter(null);
        Alergi.setName("Alergi"); // NOI18N
        FormInput.add(Alergi);
        Alergi.setBounds(250, 740, 540, 23);

        jLabel39.setText("Diet :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(120, 780, 120, 23);

        KeluarLainnya.setText("-");
        KeluarLainnya.setHighlighter(null);
        KeluarLainnya.setName("KeluarLainnya"); // NOI18N
        FormInput.add(KeluarLainnya);
        KeluarLainnya.setBounds(390, 970, 400, 23);

        jLabel40.setText("Hasil Lab Yang Belum Selesai (Pending):");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(20, 840, 220, 23);

        BtnDokter17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter17.setMnemonic('2');
        BtnDokter17.setToolTipText("Alt+2");
        BtnDokter17.setName("BtnDokter17"); // NOI18N
        BtnDokter17.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter17ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter17);
        BtnDokter17.setBounds(220, 870, 28, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Diet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diet.setColumns(20);
        Diet.setRows(5);
        Diet.setText("-");
        Diet.setName("Diet"); // NOI18N
        Diet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DietKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(Diet);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(250, 780, 540, 50);

        jLabel41.setText("Instruksi/Anjuran Dan Edukasi (Follow Up):");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(20, 900, 220, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Edukasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi.setColumns(20);
        Edukasi.setRows(5);
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Edukasi);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(250, 900, 541, 50);

        label16.setText("Terapi Pulang :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(40, 1010, 130, 23);

        jLabel43.setText("Sudah Dijelaskan :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(70, 1070, 100, 23);

        Sudah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Sudah.setName("Sudah"); // NOI18N
        Sudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SudahActionPerformed(evt);
            }
        });
        Sudah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SudahKeyPressed(evt);
            }
        });
        FormInput.add(Sudah);
        Sudah.setBounds(180, 1070, 60, 23);

        CaraBayar1.setEditable(false);
        CaraBayar1.setHighlighter(null);
        CaraBayar1.setName("CaraBayar1"); // NOI18N
        FormInput.add(CaraBayar1);
        CaraBayar1.setBounds(550, 100, 230, 23);

        Ruang1.setEditable(false);
        Ruang1.setHighlighter(null);
        Ruang1.setName("Ruang1"); // NOI18N
        FormInput.add(Ruang1);
        Ruang1.setBounds(182, 70, 170, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        LabBelum.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        LabBelum.setColumns(20);
        LabBelum.setRows(5);
        LabBelum.setName("LabBelum"); // NOI18N
        LabBelum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabBelumKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(LabBelum);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(250, 840, 541, 50);

        jLabel11.setText("Ringkasan Riwayat Penyakit :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(70, 180, 170, 23);

        jLabel44.setText("MEDIS IGD :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(800, 210, 70, 23);

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
        FormInput.add(BtnDokter4);
        BtnDokter4.setBounds(870, 210, 28, 23);

        jLabel45.setText("SOAP :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(810, 260, 40, 23);

        jLabel46.setText(" MEDIS IGD :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(800, 280, 70, 23);

        BtnDokter19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter19.setMnemonic('2');
        BtnDokter19.setToolTipText("Alt+2");
        BtnDokter19.setName("BtnDokter19"); // NOI18N
        BtnDokter19.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter19ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter19);
        BtnDokter19.setBounds(870, 280, 28, 23);

        BtnImplementasiKeperawatanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnImplementasiKeperawatanRanap.setMnemonic('K');
        BtnImplementasiKeperawatanRanap.setText("Input Diagnosa & Prosedure");
        BtnImplementasiKeperawatanRanap.setToolTipText("");
        BtnImplementasiKeperawatanRanap.setName("BtnImplementasiKeperawatanRanap"); // NOI18N
        BtnImplementasiKeperawatanRanap.setPreferredSize(new java.awt.Dimension(160, 30));
        BtnImplementasiKeperawatanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImplementasiKeperawatanRanapActionPerformed(evt);
            }
        });
        BtnImplementasiKeperawatanRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnImplementasiKeperawatanRanapKeyPressed(evt);
            }
        });
        FormInput.add(BtnImplementasiKeperawatanRanap);
        BtnImplementasiKeperawatanRanap.setBounds(270, 500, 400, 30);

        jLabel49.setText("Prosedur (ICD9) :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 630, 150, 23);

        Keluar.setForeground(new java.awt.Color(50, 70, 50));
        Keluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-11-2024" }));
        Keluar.setDisplayFormat("dd-MM-yyyy");
        Keluar.setName("Keluar"); // NOI18N
        Keluar.setOpaque(false);
        Keluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluarKeyPressed(evt);
            }
        });
        FormInput.add(Keluar);
        Keluar.setBounds(110, 130, 90, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        DiagnosaUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaUtama.setColumns(20);
        DiagnosaUtama.setRows(5);
        DiagnosaUtama.setName("DiagnosaUtama"); // NOI18N
        DiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaUtamaKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(DiagnosaUtama);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(160, 540, 630, 80);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        ProsedurUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ProsedurUtama.setColumns(20);
        ProsedurUtama.setRows(5);
        ProsedurUtama.setName("ProsedurUtama"); // NOI18N
        ProsedurUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurUtamaKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(ProsedurUtama);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(160, 630, 630, 80);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(280, 130, 50, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(330, 130, 50, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(380, 130, 50, 23);

        ChkJln.setBorder(null);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(433, 132, 20, 20);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Obat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Obat.setColumns(20);
        Obat.setRows(5);
        Obat.setName("Obat"); // NOI18N
        Obat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Obat);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(181, 1010, 610, 50);

        PengobatanLainnya.setHighlighter(null);
        PengobatanLainnya.setName("PengobatanLainnya"); // NOI18N
        FormInput.add(PengobatanLainnya);
        PengobatanLainnya.setBounds(460, 1100, 330, 24);

        Pengobatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Poliklinik RSUD Matraman", "RS Lain", "Dokter Luar", "Puskesmas", "Lainnya" }));
        Pengobatan.setName("Pengobatan"); // NOI18N
        Pengobatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PengobatanActionPerformed(evt);
            }
        });
        Pengobatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengobatanKeyPressed(evt);
            }
        });
        FormInput.add(Pengobatan);
        Pengobatan.setBounds(180, 1100, 270, 20);

        jLabel42.setText("Pengobatan Dilanjutkan :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(10, 1100, 160, 14);

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
            Valid.textKosong(BtnDokter,"Dokter Penanggung Jawab");
        }else if(Terapi.getText().equals("")){
            Valid.textKosong(Terapi,"Terapi  Pengobatan Selama Di RS");
        }else if(Keluhan.getText().equals("")){
            Valid.textKosong(Keluhan,"Ringkasan Riwayat Penyakit");
        }else if(JalannyaPenyakit.getText().equals("")){
            Valid.textKosong(JalannyaPenyakit,"Pemeriksaan Fisik");
        }else{
            if(Sequel.menyimpantf("resume_pasien_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",102,new String[]{
                    TNoRw.getText(),KodeDokter.getText(),TglLahir.getText(),Jk.getText(),Ruang.getText(),Masuk.getText(),JamMasuk.getText(),Keluar.getSelectedItem()+"",cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),KodeDokter1.getText(),NamaDokter1.getText(),CaraBayar.getText(),DiagnosaAwal.getText(),Alasan.getText(),
                    Keluhan.getText(),JalannyaPenyakit.getText(),PemeriksaanPenunjang.getText(),HasilLaborat.getText(),Terapi.getText(),DiagnosaUtama.getText(),KodeDiagnosaUtama.getText(),DiagnosaSekunder1.getText(),KodeDiagnosaSekunder1.getText(),DiagnosaSekunder2.getText(), 
                    KodeDiagnosaSekunder2.getText(),DiagnosaSekunder3.getText(),KodeDiagnosaSekunder3.getText(),DiagnosaSekunder4.getText(),KodeDiagnosaSekunder4.getText(),DiagnosaSekunder5.getText(),KodeDiagnosaSekunder5.getText(),DiagnosaSekunder6.getText(),KodeDiagnosaSekunder6.getText(),
                    DiagnosaSekunder7.getText(),KodeDiagnosaSekunder7.getText(),DiagnosaKlinis.getText(),ProsedurUtama.getText(),KodeProsedurUtama.getText(),ProsedurSekunder1.getText(),KodeProsedurSekunder1.getText(),
                    ProsedurSekunder2.getText(),KodeProsedurSekunder2.getText(),ProsedurSekunder3.getText(),KodeProsedurSekunder3.getText(),ProsedurSekunder4.getText(),KodeProsedurSekunder4.getText(),ProsedurSekunder5.getText(),KodeProsedurSekunder5.getText(),ProsedurSekunder6.getText(),KodeProsedurSekunder6.getText(),
                    Alergi.getText(),Diet.getText(),LabBelum.getText(),Edukasi.getText(),CaraKeluar.getSelectedItem().toString(),KeluarLainnya.getText(),
                    Keadaan.getSelectedItem().toString(),KeadaanLainnya.getText(),Pengobatan.getSelectedItem().toString(),PengobatanLainnya.getText(),Valid.SetTgl(Kontrol.getSelectedItem()+"")+" "+Kontrol.getSelectedItem().toString().substring(11,19),
                    Obat.getText(),"","","",Obat1.getText(),Nama1.getText(),Jumlah1.getText(),Dosis1.getText(),Obat2.getText(),Nama2.getText(),Jumlah2.getText(),Dosis2.getText(),Obat3.getText(),Nama3.getText(),Jumlah3.getText(),Dosis3.getText(),Obat4.getText(),Nama4.getText(),Jumlah4.getText(),Dosis4.getText(),
                    Obat5.getText(),Nama5.getText(),Jumlah5.getText(),Dosis5.getText(),Obat6.getText(),Nama6.getText(),Jumlah6.getText(),Dosis6.getText(),Obat7.getText(),Nama7.getText(),Jumlah7.getText(),Dosis7.getText(),Obat8.getText(),Nama8.getText(),Jumlah8.getText(),Dosis8.getText(),Obat9.getText(),Nama9.getText(),Jumlah9.getText(),Dosis9.getText(),
                    Sudah.getSelectedItem().toString()
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
            Valid.pindah(evt,Sudah,BtnBatal);
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
            if(Sequel.queryu2tf("delete from resume_pasien_ranap where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()
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
            Valid.textKosong(BtnDokter,"Dokter Penanggung Jawab");
//        }else if(Terapi.getText().equals("")){
//            Valid.textKosong(Terapi,"Terapi  Pengobatan Selama Di RS");
//        }else if(Keluhan.getText().equals("")){
//            Valid.textKosong(Keluhan,"Ringkasan Riwayat Penyakit");
//        }else if(JalannyaPenyakit.getText().equals("")){
//            Valid.textKosong(JalannyaPenyakit,"Pemeriksaan Fisik");
//        }else if(DiagnosaUtama.getText().equals("")){
//            Valid.textKosong(DiagnosaUtama,"Diagnosa Utama");
//         }else if(ProsedurUtama.getText().equals("")){
//            Valid.textKosong(ProsedurUtama,"Prosedur 1");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("resume_pasien_ranap","no_rawat=?","no_rawat=?,kd_dokter=?,tgl_lahir=?,jk=?,kd_kamar=?,masuk=?,jam_masuk=?,keluar=?,jam_keluar=?,kd_dokter1=?,nm_dokter1=?,kd_pj=?,diagnosa_awal=?,alasan=?,keluhan_utama=?,jalannya_penyakit=?,pemeriksaan_penunjang=?,hasil_laborat=?,terapi=?,diagnosa_utama=?,kd_diagnosa_utama=?,diagnosa_sekunder=?,kd_diagnosa_sekunder=?,diagnosa_sekunder2=?,kd_diagnosa_sekunder2=?,diagnosa_sekunder3=?,kd_diagnosa_sekunder3=?,diagnosa_sekunder4=?,kd_diagnosa_sekunder4=?,diagnosa_sekunder5=?,kd_diagnosa_sekunder5=?,diagnosa_sekunder6=?,kd_diagnosa_sekunder6=?,diagnosa_sekunder7=?,kd_diagnosa_sekunder7=?,diagnosa_klinis=?,prosedur_utama=?,kd_prosedur_utama=?,prosedur_sekunder=?,kd_prosedur_sekunder=?,prosedur_sekunder2=?,kd_prosedur_sekunder2=?,prosedur_sekunder3=?,kd_prosedur_sekunder3=?,prosedur_sekunder4=?,kd_prosedur_sekunder4=?,prosedur_sekunder5=?,kd_prosedur_sekunder5=?,prosedur_sekunder6=?,kd_prosedur_sekunder6=?,alergi=?,diet=?,lab_belum=?,edukasi=?,cara_keluar=?,keluar_lainnya=?,keadaan=?,keadaan_lainnya=?,pengobatan=?,pengobatan_lainnya=?,kontrol=?,kode_brng=?,nama=?,jml_barang=?,dosis=?,kode_brng1=?,nama1=?,jml_barang1=?,dosis1=?,kode_brng2=?,nama2=?,jml_barang2=?,dosis2=?,kode_brng3=?,nama3=?,jml_barang3=?,dosis3=?,kode_brng4=?,nama4=?,jml_barang4=?,dosis4=?,kode_brng5=?,nama5=?,jml_barang5=?,dosis5=?,kode_brng6=?,nama6=?,jml_barang6=?,dosis6=?,kode_brng7=?,nama7=?,jml_barang7=?,dosis7=?,kode_brng8=?,nama8=?,jml_barang8=?,dosis8=?,kode_brng9=?,nama9=?,jml_barang9=?,dosis9=?,sudah=?",103,new String[]{
                    TNoRw.getText(),KodeDokter.getText(),TglLahir.getText(),Jk.getText(),Ruang.getText(),Masuk.getText(),JamMasuk.getText(),Keluar.getSelectedItem()+"",cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),KodeDokter1.getText(),NamaDokter1.getText(),CaraBayar.getText(),DiagnosaAwal.getText(),Alasan.getText(),
                    Keluhan.getText(),JalannyaPenyakit.getText(),PemeriksaanPenunjang.getText(),HasilLaborat.getText(),Terapi.getText(),DiagnosaUtama.getText(),KodeDiagnosaUtama.getText(),DiagnosaSekunder1.getText(),KodeDiagnosaSekunder1.getText(),DiagnosaSekunder2.getText(), 
                    KodeDiagnosaSekunder2.getText(),DiagnosaSekunder3.getText(),KodeDiagnosaSekunder3.getText(),DiagnosaSekunder4.getText(),KodeDiagnosaSekunder4.getText(),DiagnosaSekunder5.getText(),KodeDiagnosaSekunder5.getText(),DiagnosaSekunder6.getText(),KodeDiagnosaSekunder6.getText(),
                    DiagnosaSekunder7.getText(),KodeDiagnosaSekunder7.getText(),DiagnosaKlinis.getText(),ProsedurUtama.getText(),KodeProsedurUtama.getText(),ProsedurSekunder1.getText(),KodeProsedurSekunder1.getText(),
                    ProsedurSekunder2.getText(),KodeProsedurSekunder2.getText(),ProsedurSekunder3.getText(),KodeProsedurSekunder3.getText(),ProsedurSekunder4.getText(),KodeProsedurSekunder4.getText(),ProsedurSekunder5.getText(),KodeProsedurSekunder5.getText(),ProsedurSekunder6.getText(),KodeProsedurSekunder6.getText(),
                    Alergi.getText(),Diet.getText(),LabBelum.getText(),Edukasi.getText(),CaraKeluar.getSelectedItem().toString(),KeluarLainnya.getText(),
                    Keadaan.getSelectedItem().toString(),KeadaanLainnya.getText(),Pengobatan.getSelectedItem().toString(),PengobatanLainnya.getText(),Valid.SetTgl(Kontrol.getSelectedItem()+"")+" "+Kontrol.getSelectedItem().toString().substring(11,19),
                    Obat.getText(),"","","",Obat1.getText(),Nama1.getText(),Jumlah1.getText(),Dosis1.getText(),Obat2.getText(),Nama2.getText(),Jumlah2.getText(),Dosis2.getText(),Obat3.getText(),Nama3.getText(),Jumlah3.getText(),Dosis3.getText(),Obat4.getText(),Nama4.getText(),Jumlah4.getText(),Dosis4.getText(),
                    Obat5.getText(),Nama5.getText(),Jumlah5.getText(),Dosis5.getText(),Obat6.getText(),Nama6.getText(),Jumlah6.getText(),Dosis6.getText(),Obat7.getText(),Nama7.getText(),Jumlah7.getText(),Dosis7.getText(),Obat8.getText(),Nama8.getText(),Jumlah8.getText(),Dosis8.getText(),Obat9.getText(),Nama9.getText(),Jumlah9.getText(),Dosis9.getText(),
                    Sudah.getSelectedItem().toString(),tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()
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
                        "resume_pasien_ranap.kd_dokter,dokter.nm_dokter,resume_pasien_ranap.kondisi_pulang,resume_pasien_ranap.keluhan_utama,resume_pasien_ranap.jalannya_penyakit, "+
                        "resume_pasien_ranap.pemeriksaan_penunjang,resume_pasien_ranap.hasil_laborat,resume_pasien_ranap.diagnosa_utama,resume_pasien_ranap.kd_diagnosa_utama, "+
                        "resume_pasien_ranap.diagnosa_sekunder,resume_pasien_ranap.kd_diagnosa_sekunder,resume_pasien_ranap.diagnosa_sekunder2,resume_pasien_ranap.kd_diagnosa_sekunder2, "+
                        "resume_pasien_ranap.diagnosa_sekunder3,resume_pasien_ranap.kd_diagnosa_sekunder3,resume_pasien_ranap.diagnosa_sekunder4,resume_pasien_ranap.kd_diagnosa_sekunder4, "+
                        "resume_pasien_ranap.prosedur_utama,resume_pasien_ranap.kd_prosedur_utama,resume_pasien_ranap.prosedur_sekunder,resume_pasien_ranap.kd_prosedur_sekunder, "+
                        "resume_pasien_ranap.prosedur_sekunder2,resume_pasien_ranap.kd_prosedur_sekunder2,resume_pasien_ranap.prosedur_sekunder3,resume_pasien_ranap.kd_prosedur_sekunder3, "+
                        "resume_pasien_ranap.obat_pulang,resume_pasien_ranap.tindak_lanjut,resume_pasien_ranap.asal_pasien from resume_pasien_ranap inner join reg_periksa on resume_pasien_ranap.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on resume_pasien_ranap.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
                }else{
                    Valid.MyReportqry("rptDataResumePasien.jasper","report","::[ Data Resume Pasien ]::",
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                        "resume_pasien_ranap.kd_dokter,dokter.nm_dokter,resume_pasien_ranap.kondisi_pulang,resume_pasien_ranap.keluhan_utama,resume_pasien_ranap.jalannya_penyakit, "+
                        "resume_pasien_ranap.pemeriksaan_penunjang,resume_pasien_ranap.hasil_laborat,resume_pasien_ranap.diagnosa_utama,resume_pasien_ranap.kd_diagnosa_utama, "+
                        "resume_pasien_ranap.diagnosa_sekunder,resume_pasien_ranap.kd_diagnosa_sekunder,resume_pasien_ranap.diagnosa_sekunder2,resume_pasien_ranap.kd_diagnosa_sekunder2, "+
                        "resume_pasien_ranap.diagnosa_sekunder3,resume_pasien_ranap.kd_diagnosa_sekunder3,resume_pasien_ranap.diagnosa_sekunder4,resume_pasien_ranap.kd_diagnosa_sekunder4, "+
                        "resume_pasien_ranap.prosedur_utama,resume_pasien_ranap.kd_prosedur_utama,resume_pasien_ranap.prosedur_sekunder,resume_pasien_ranap.kd_prosedur_sekunder, "+
                        "resume_pasien_ranap.prosedur_sekunder2,resume_pasien_ranap.kd_prosedur_sekunder2,resume_pasien_ranap.prosedur_sekunder3,resume_pasien_ranap.kd_prosedur_sekunder3, "+
                        "resume_pasien_ranap.obat_pulang,resume_pasien_ranap.tindak_lanjut,resume_pasien_ranap.asal_pasien from resume_pasien_ranap inner join reg_periksa on resume_pasien_ranap.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on resume_pasien_ranap.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.status_lanjut like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien_ranap.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien_ranap.kondisi_pulang like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien_ranap.kd_diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien_ranap.diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien_ranap.prosedur_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien_ranap.kd_prosedur_utama like '%"+TCari.getText().trim()+"%' "+
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

    private void DiagnosaSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder2KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder1,KodeDiagnosaSekunder2);
    }//GEN-LAST:event_DiagnosaSekunder2KeyPressed

    private void DiagnosaSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder3KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder2,KodeDiagnosaSekunder3);
    }//GEN-LAST:event_DiagnosaSekunder3KeyPressed

    private void DiagnosaSekunder4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder4KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder3,KodeDiagnosaSekunder4);
    }//GEN-LAST:event_DiagnosaSekunder4KeyPressed

    private void DiagnosaSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder1KeyPressed
        Valid.pindah(evt,KodeDiagnosaUtama,KodeDiagnosaSekunder1);
    }//GEN-LAST:event_DiagnosaSekunder1KeyPressed

    private void KodeDiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaUtamaKeyPressed
        Valid.pindah(evt,DiagnosaUtama,DiagnosaSekunder1);
    }//GEN-LAST:event_KodeDiagnosaUtamaKeyPressed

    private void KodeDiagnosaSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder1KeyPressed
        Valid.pindah(evt,DiagnosaSekunder1,DiagnosaSekunder2);
    }//GEN-LAST:event_KodeDiagnosaSekunder1KeyPressed

    private void KodeDiagnosaSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder2KeyPressed
        Valid.pindah(evt,DiagnosaSekunder2,DiagnosaSekunder3);
    }//GEN-LAST:event_KodeDiagnosaSekunder2KeyPressed

    private void KodeDiagnosaSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder3KeyPressed
        Valid.pindah(evt,DiagnosaSekunder3,DiagnosaSekunder4);
    }//GEN-LAST:event_KodeDiagnosaSekunder3KeyPressed

    private void KodeDiagnosaSekunder4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder4KeyPressed
        Valid.pindah(evt,DiagnosaSekunder4,ProsedurUtama);
    }//GEN-LAST:event_KodeDiagnosaSekunder4KeyPressed

    private void KodeProsedurUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurUtamaKeyPressed
        Valid.pindah(evt,ProsedurUtama,ProsedurSekunder1);
    }//GEN-LAST:event_KodeProsedurUtamaKeyPressed

    private void ProsedurSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder1KeyPressed
        Valid.pindah(evt,KodeProsedurUtama,KodeProsedurSekunder1);
    }//GEN-LAST:event_ProsedurSekunder1KeyPressed

    private void KodeProsedurSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder1KeyPressed
        Valid.pindah(evt,ProsedurSekunder1,ProsedurSekunder2);
    }//GEN-LAST:event_KodeProsedurSekunder1KeyPressed

    private void ProsedurSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder2KeyPressed
        Valid.pindah(evt,KodeProsedurSekunder1,KodeProsedurSekunder2);
    }//GEN-LAST:event_ProsedurSekunder2KeyPressed

    private void KodeProsedurSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder2KeyPressed
        Valid.pindah(evt,ProsedurSekunder2,ProsedurSekunder3);
    }//GEN-LAST:event_KodeProsedurSekunder2KeyPressed

    private void KodeProsedurSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder3KeyPressed
        Valid.pindah(evt,ProsedurSekunder3,Alergi);
    }//GEN-LAST:event_KodeProsedurSekunder3KeyPressed

    private void ProsedurSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder3KeyPressed
        Valid.pindah(evt,KodeProsedurSekunder2,KodeProsedurSekunder3);
    }//GEN-LAST:event_ProsedurSekunder3KeyPressed

    private void KodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterKeyPressed
        Valid.pindah(evt,TCari,CaraKeluar);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
       Valid.pindah(evt,TCari,CaraKeluar);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void KeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                JalannyaPenyakit.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CaraKeluar.requestFocus();
        }
    }//GEN-LAST:event_KeluhanKeyPressed

    private void JalannyaPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalannyaPenyakitKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                PemeriksaanPenunjang.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Keluhan.requestFocus();
        }
    }//GEN-LAST:event_JalannyaPenyakitKeyPressed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                HasilLaborat.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            JalannyaPenyakit.requestFocus();
        }
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void HasilLaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilLaboratKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                DiagnosaUtama.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            PemeriksaanPenunjang.requestFocus();
        }
    }//GEN-LAST:event_HasilLaboratKeyPressed

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
            param.put("norawat",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())); 
            if(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString().equals("Ralan")){
                param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
            }else{
                param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
            }
            Valid.MyReport("rptLaporanResumeRanap.jasper","report","::[ Laporan Resume Pasien ]::",param);
        }
    }//GEN-LAST:event_MnLaporanResumeActionPerformed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            carikeluhan.setNoRawat(TNoRw.getText());
            carikeluhan.tampil();
            carikeluhan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            carikeluhan.setLocationRelativeTo(internalFrame1);
            carikeluhan.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariradranap.setNoRawat(TNoRw.getText());
            rmcariradranap.tampil();
            rmcariradranap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariradranap.setLocationRelativeTo(internalFrame1);
            rmcariradranap.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcarilabranap.setNoRawat(TNoRw.getText());
            rmcarilabranap.tampil();
            rmcarilabranap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcarilabranap.setLocationRelativeTo(internalFrame1);
            rmcarilabranap.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void CaraKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CaraKeluarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CaraKeluarActionPerformed

    private void CaraKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraKeluarKeyPressed
        Valid.pindah(evt, KodeDokter, Keluhan);
    }//GEN-LAST:event_CaraKeluarKeyPressed

    private void KeadaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KeadaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanActionPerformed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanKeyPressed

    private void BtnDokter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter5ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            caripemeriksaan.setNoRawat(TNoRw.getText());
            caripemeriksaan.tampil();
            caripemeriksaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            caripemeriksaan.setLocationRelativeTo(internalFrame1);
            caripemeriksaan.setVisible(true);
        }    // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter5ActionPerformed

    private void BtnDokter6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter6ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcaridiagnosa1.setNoRawat(TNoRw.getText());
            rmcaridiagnosa1.tampil();
            rmcaridiagnosa1.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcaridiagnosa1.setLocationRelativeTo(internalFrame1);
            rmcaridiagnosa1.setVisible(true);
        }    // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter6ActionPerformed

    private void BtnDokter7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter7ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcaridiagnosa2.setNoRawat(TNoRw.getText());
            rmcaridiagnosa2.tampil();
            rmcaridiagnosa2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcaridiagnosa2.setLocationRelativeTo(internalFrame1);
            rmcaridiagnosa2.setVisible(true);
        }    // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter7ActionPerformed

    private void BtnDokter8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter8ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcaridiagnosa3.setNoRawat(TNoRw.getText());
            rmcaridiagnosa3.tampil();
            rmcaridiagnosa3.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcaridiagnosa3.setLocationRelativeTo(internalFrame1);
            rmcaridiagnosa3.setVisible(true);
        }    // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter8ActionPerformed

    private void BtnDokter9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter9ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcaridiagnosa4.setNoRawat(TNoRw.getText());
            rmcaridiagnosa4.tampil();
            rmcaridiagnosa4.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcaridiagnosa4.setLocationRelativeTo(internalFrame1);
            rmcaridiagnosa4.setVisible(true);
        }    // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter9ActionPerformed

    private void BtnDokter10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter10ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcaridiagnosa5.setNoRawat(TNoRw.getText());
            rmcaridiagnosa5.tampil();
            rmcaridiagnosa5.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcaridiagnosa5.setLocationRelativeTo(internalFrame1);
            rmcaridiagnosa5.setVisible(true);
        }    // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter10ActionPerformed

    private void BtnDokter11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter11ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariprosedur1.setNoRawat(TNoRw.getText());
            rmcariprosedur1.tampil();
            rmcariprosedur1.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariprosedur1.setLocationRelativeTo(internalFrame1);
            rmcariprosedur1.setVisible(true);
        }    // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter11ActionPerformed

    private void BtnDokter12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter12ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariprosedur2.setNoRawat(TNoRw.getText());
            rmcariprosedur2.tampil();
            rmcariprosedur2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariprosedur2.setLocationRelativeTo(internalFrame1);
            rmcariprosedur2.setVisible(true);
        }    // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter12ActionPerformed

    private void BtnDokter13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter13ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariprosedur3.setNoRawat(TNoRw.getText());
            rmcariprosedur3.tampil();
            rmcariprosedur3.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariprosedur3.setLocationRelativeTo(internalFrame1);
            rmcariprosedur3.setVisible(true);
        }    // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter13ActionPerformed

    private void BtnDokter14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter14ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariprosedur4.setNoRawat(TNoRw.getText());
            rmcariprosedur4.tampil();
            rmcariprosedur4.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariprosedur4.setLocationRelativeTo(internalFrame1);
            rmcariprosedur4.setVisible(true);
        }    // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter14ActionPerformed

    private void KodeDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDokter1KeyPressed

    private void BtnDokter15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter15ActionPerformed

    private void BtnDokter15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter15KeyPressed

    private void BtnDokter16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter16ActionPerformed
      if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            cariobat.setNoRawat(TNoRw.getText());
            cariobat.tampil();
            cariobat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobat.setLocationRelativeTo(internalFrame1);
            cariobat.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter16ActionPerformed

    private void TerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                Keadaan.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KodeDiagnosaUtama.requestFocus();
        }
    }//GEN-LAST:event_TerapiKeyPressed

    private void BtnDokter17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter17ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcarilabpending.setNoRawat(TNoRw.getText());
            rmcarilabpending.tampil();
            rmcarilabpending.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcarilabpending.setLocationRelativeTo(internalFrame1);
            rmcarilabpending.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter17ActionPerformed

    private void DietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DietKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                Keadaan.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KodeDiagnosaUtama.requestFocus();
        }
    }//GEN-LAST:event_DietKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdukasiKeyPressed

    private void PengobatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengobatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengobatanActionPerformed

    private void PengobatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengobatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengobatanKeyPressed

    private void KontrolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontrolKeyPressed
        Valid.pindah(evt,Obat,Obat1);
    }//GEN-LAST:event_KontrolKeyPressed

    private void KontrolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KontrolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontrolActionPerformed

    private void PObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PObatActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariobatpulang.setNoRawat(TNoRw.getText());
            rmcariobatpulang.tampil();
            rmcariobatpulang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariobatpulang.setLocationRelativeTo(internalFrame1);
            rmcariobatpulang.setVisible(true);
        }
    }//GEN-LAST:event_PObatActionPerformed

    private void PObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PObat1ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariobatpulang1.setNoRawat(TNoRw.getText());
            rmcariobatpulang1.tampil();
            rmcariobatpulang1.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariobatpulang1.setLocationRelativeTo(internalFrame1);
            rmcariobatpulang1.setVisible(true);
        }
    }//GEN-LAST:event_PObat1ActionPerformed

    private void PObat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PObat2ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariobatpulang2.setNoRawat(TNoRw.getText());
            rmcariobatpulang2.tampil();
            rmcariobatpulang2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariobatpulang2.setLocationRelativeTo(internalFrame1);
            rmcariobatpulang2.setVisible(true);
        }
    }//GEN-LAST:event_PObat2ActionPerformed

    private void PObat3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PObat3ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariobatpulang3.setNoRawat(TNoRw.getText());
            rmcariobatpulang3.tampil();
            rmcariobatpulang3.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariobatpulang3.setLocationRelativeTo(internalFrame1);
            rmcariobatpulang3.setVisible(true);
        }
    }//GEN-LAST:event_PObat3ActionPerformed

    private void PObat4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PObat4ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariobatpulang4.setNoRawat(TNoRw.getText());
            rmcariobatpulang4.tampil();
            rmcariobatpulang4.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariobatpulang4.setLocationRelativeTo(internalFrame1);
            rmcariobatpulang4.setVisible(true);
        }
    }//GEN-LAST:event_PObat4ActionPerformed

    private void PObat5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PObat5ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariobatpulang5.setNoRawat(TNoRw.getText());
            rmcariobatpulang5.tampil();
            rmcariobatpulang5.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariobatpulang5.setLocationRelativeTo(internalFrame1);
            rmcariobatpulang5.setVisible(true);
        }
    }//GEN-LAST:event_PObat5ActionPerformed

    private void PObat6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PObat6ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariobatpulang6.setNoRawat(TNoRw.getText());
            rmcariobatpulang6.tampil();
            rmcariobatpulang6.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariobatpulang6.setLocationRelativeTo(internalFrame1);
            rmcariobatpulang6.setVisible(true);
        }
    }//GEN-LAST:event_PObat6ActionPerformed

    private void PObat7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PObat7ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariobatpulang7.setNoRawat(TNoRw.getText());
            rmcariobatpulang7.tampil();
            rmcariobatpulang7.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariobatpulang7.setLocationRelativeTo(internalFrame1);
            rmcariobatpulang7.setVisible(true);
        }
    }//GEN-LAST:event_PObat7ActionPerformed

    private void PObat8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PObat8ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariobatpulang8.setNoRawat(TNoRw.getText());
            rmcariobatpulang8.tampil();
            rmcariobatpulang8.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariobatpulang8.setLocationRelativeTo(internalFrame1);
            rmcariobatpulang8.setVisible(true);
        }
    }//GEN-LAST:event_PObat8ActionPerformed

    private void PObat9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PObat9ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariobatpulang9.setNoRawat(TNoRw.getText());
            rmcariobatpulang9.tampil();
            rmcariobatpulang9.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariobatpulang9.setLocationRelativeTo(internalFrame1);
            rmcariobatpulang9.setVisible(true);
        }
    }//GEN-LAST:event_PObat9ActionPerformed

    private void SudahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SudahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SudahActionPerformed

    private void SudahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SudahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SudahKeyPressed

    private void LabBelumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabBelumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LabBelumKeyPressed

    private void BtnDokter4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter4ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            carirps.setNoRawat(TNoRw.getText());
            carirps.tampil();
            carirps.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            carirps.setLocationRelativeTo(internalFrame1);
            carirps.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter4ActionPerformed

    private void BtnDokter19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter19ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            carifisik.setNoRawat(TNoRw.getText());
            carifisik.tampil();
            carifisik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            carifisik.setLocationRelativeTo(internalFrame1);
            carifisik.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter19ActionPerformed

    private void BtnImplementasiKeperawatanRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImplementasiKeperawatanRanapActionPerformed
//        if(TNoRw.getText().trim().equals("")){
//            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
//            TCari.requestFocus();
//        }else{
//            DlgDiagnosaPenyakit resep=new DlgDiagnosaPenyakit(null,false);
//            resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
//            resep.setLocationRelativeTo(internalFrame1);
//            resep.isCek();
//            resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Ranap");
//            resep.panelDiagnosa1.tampil();
//            resep.setVisible(true);
//        }
        penyakit.isCek();
        penyakit.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Ranap");
//        penyakit.emptTeks();
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_BtnImplementasiKeperawatanRanapActionPerformed

    private void BtnImplementasiKeperawatanRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnImplementasiKeperawatanRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnImplementasiKeperawatanRanapKeyPressed

    private void DiagnosaSekunder5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaSekunder5KeyPressed

    private void DiagnosaSekunder6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaSekunder6KeyPressed

    private void DiagnosaSekunder7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaSekunder7KeyPressed

    private void KodeDiagnosaSekunder5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDiagnosaSekunder5KeyPressed

    private void KodeDiagnosaSekunder6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDiagnosaSekunder6KeyPressed

    private void KodeDiagnosaSekunder7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDiagnosaSekunder7KeyPressed

    private void BtnDokter20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter20ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcaridiagnosa6.setNoRawat(TNoRw.getText());
            rmcaridiagnosa6.tampil();
            rmcaridiagnosa6.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcaridiagnosa6.setLocationRelativeTo(internalFrame1);
            rmcaridiagnosa6.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter20ActionPerformed

    private void BtnDokter21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter21ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcaridiagnosa7.setNoRawat(TNoRw.getText());
            rmcaridiagnosa7.tampil();
            rmcaridiagnosa7.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcaridiagnosa7.setLocationRelativeTo(internalFrame1);
            rmcaridiagnosa7.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter21ActionPerformed

    private void BtnDokter22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter22ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcaridiagnosa8.setNoRawat(TNoRw.getText());
            rmcaridiagnosa8.tampil();
            rmcaridiagnosa8.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcaridiagnosa8.setLocationRelativeTo(internalFrame1);
            rmcaridiagnosa8.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter22ActionPerformed

    private void DiagnosaKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKlinisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaKlinisKeyPressed

    private void ProsedurSekunder4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProsedurSekunder4KeyPressed

    private void KodeProsedurSekunder4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeProsedurSekunder4KeyPressed

    private void ProsedurSekunder5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProsedurSekunder5KeyPressed

    private void KodeProsedurSekunder5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeProsedurSekunder5KeyPressed

    private void KodeProsedurSekunder6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeProsedurSekunder6KeyPressed

    private void ProsedurSekunder6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProsedurSekunder6KeyPressed

    private void BtnDokter23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter23ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariprosedur5.setNoRawat(TNoRw.getText());
            rmcariprosedur5.tampil();
            rmcariprosedur5.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariprosedur5.setLocationRelativeTo(internalFrame1);
            rmcariprosedur5.setVisible(true);
        } 
    }//GEN-LAST:event_BtnDokter23ActionPerformed

    private void BtnDokter24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter24ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariprosedur6.setNoRawat(TNoRw.getText());
            rmcariprosedur6.tampil();
            rmcariprosedur6.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariprosedur6.setLocationRelativeTo(internalFrame1);
            rmcariprosedur6.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter24ActionPerformed

    private void BtnDokter25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter25ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcariprosedur7.setNoRawat(TNoRw.getText());
            rmcariprosedur7.tampil();
            rmcariprosedur7.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariprosedur7.setLocationRelativeTo(internalFrame1);
            rmcariprosedur7.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter25ActionPerformed

    private void AlasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlasanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlasanActionPerformed

    private void KeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluarKeyPressed
//        Valid.pindah(evt,TNoRw,CmbJam);
    }//GEN-LAST:event_KeluarKeyPressed

    private void JamKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JamKeluarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamKeluarActionPerformed

    private void JamKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeluarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamKeluarKeyPressed

    private void MnDigitalTTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDigitalTTEActionPerformed
        if(tbObat.getSelectedRow()>-1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            FileName="resume_pasien_rawat_inap-"+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString().replaceAll("/","")+".pdf";
            DlgViewPdf berkas=new DlgViewPdf(null,true);
            if(Sequel.cariInteger("select count(no_rawat) from berkas_tte_matraman where no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()+"'")>0){
                berkas.tampilPdf("signed_"+FileName,"berkastte/resume");
                berkas.setButton(false);
            }else{
                createPdf(FileName);
                berkas.tampilPdfLocal(FileName,"local","berkastte/resume",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            };

            berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);

            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnDigitalTTEActionPerformed

    private void DiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaUtamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaUtamaKeyPressed

    private void ProsedurUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurUtamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProsedurUtamaKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,Keluar,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,DiagnosaAwal);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void ObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ObatKeyPressed

    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
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
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataResumePasienRanap dialog = new RMDataResumePasienRanap(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alasan;
    private widget.TextBox Alergi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter10;
    private widget.Button BtnDokter11;
    private widget.Button BtnDokter12;
    private widget.Button BtnDokter13;
    private widget.Button BtnDokter14;
    private widget.Button BtnDokter15;
    private widget.Button BtnDokter16;
    private widget.Button BtnDokter17;
    private widget.Button BtnDokter19;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter20;
    private widget.Button BtnDokter21;
    private widget.Button BtnDokter22;
    private widget.Button BtnDokter23;
    private widget.Button BtnDokter24;
    private widget.Button BtnDokter25;
    private widget.Button BtnDokter3;
    private widget.Button BtnDokter4;
    private widget.Button BtnDokter5;
    private widget.Button BtnDokter6;
    private widget.Button BtnDokter7;
    private widget.Button BtnDokter8;
    private widget.Button BtnDokter9;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnImplementasiKeperawatanRanap;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox CaraBayar;
    private widget.TextBox CaraBayar1;
    private widget.ComboBox CaraKeluar;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaAwal;
    private widget.TextArea DiagnosaKlinis;
    private widget.TextBox DiagnosaSekunder1;
    private widget.TextBox DiagnosaSekunder2;
    private widget.TextBox DiagnosaSekunder3;
    private widget.TextBox DiagnosaSekunder4;
    private widget.TextBox DiagnosaSekunder5;
    private widget.TextBox DiagnosaSekunder6;
    private widget.TextBox DiagnosaSekunder7;
    private widget.TextArea DiagnosaUtama;
    private widget.TextArea Diet;
    private widget.TextBox Dosis1;
    private widget.TextBox Dosis2;
    private widget.TextBox Dosis3;
    private widget.TextBox Dosis4;
    private widget.TextBox Dosis5;
    private widget.TextBox Dosis6;
    private widget.TextBox Dosis7;
    private widget.TextBox Dosis8;
    private widget.TextBox Dosis9;
    private widget.TextArea Edukasi;
    private widget.PanelBiasa FormInput;
    private widget.TextArea HasilLaborat;
    private widget.TextArea JalannyaPenyakit;
    private widget.Tanggal JamKeluar;
    private widget.TextBox JamMasuk;
    private widget.TextBox Jk;
    private widget.TextBox Jumlah1;
    private widget.TextBox Jumlah2;
    private widget.TextBox Jumlah3;
    private widget.TextBox Jumlah4;
    private widget.TextBox Jumlah5;
    private widget.TextBox Jumlah6;
    private widget.TextBox Jumlah7;
    private widget.TextBox Jumlah8;
    private widget.TextBox Jumlah9;
    private widget.ComboBox Keadaan;
    private widget.TextBox KeadaanLainnya;
    private widget.Tanggal Keluar;
    private widget.TextBox KeluarLainnya;
    private widget.TextArea Keluhan;
    private widget.TextBox KodeDiagnosaSekunder1;
    private widget.TextBox KodeDiagnosaSekunder2;
    private widget.TextBox KodeDiagnosaSekunder3;
    private widget.TextBox KodeDiagnosaSekunder4;
    private widget.TextBox KodeDiagnosaSekunder5;
    private widget.TextBox KodeDiagnosaSekunder6;
    private widget.TextBox KodeDiagnosaSekunder7;
    private widget.TextBox KodeDiagnosaUtama;
    private widget.TextBox KodeDokter;
    private widget.TextBox KodeDokter1;
    private widget.TextBox KodeProsedurSekunder1;
    private widget.TextBox KodeProsedurSekunder2;
    private widget.TextBox KodeProsedurSekunder3;
    private widget.TextBox KodeProsedurSekunder4;
    private widget.TextBox KodeProsedurSekunder5;
    private widget.TextBox KodeProsedurSekunder6;
    private widget.TextBox KodeProsedurUtama;
    private widget.Tanggal Kontrol;
    private widget.Label LCount;
    private widget.TextArea LabBelum;
    private widget.TextBox Masuk;
    private javax.swing.JMenuItem MnDigitalTTE;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox Nama1;
    private widget.TextBox Nama2;
    private widget.TextBox Nama3;
    private widget.TextBox Nama4;
    private widget.TextBox Nama5;
    private widget.TextBox Nama6;
    private widget.TextBox Nama7;
    private widget.TextBox Nama8;
    private widget.TextBox Nama9;
    private widget.TextBox NamaDokter;
    private widget.TextBox NamaDokter1;
    private widget.TextArea Obat;
    private widget.TextBox Obat1;
    private widget.TextBox Obat2;
    private widget.TextBox Obat3;
    private widget.TextBox Obat4;
    private widget.TextBox Obat5;
    private widget.TextBox Obat6;
    private widget.TextBox Obat7;
    private widget.TextBox Obat8;
    private widget.TextBox Obat9;
    private widget.Button PObat;
    private widget.Button PObat1;
    private widget.Button PObat2;
    private widget.Button PObat3;
    private widget.Button PObat4;
    private widget.Button PObat5;
    private widget.Button PObat6;
    private widget.Button PObat7;
    private widget.Button PObat8;
    private widget.Button PObat9;
    private javax.swing.JPanel PanelInput;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.ComboBox Pengobatan;
    private widget.TextBox PengobatanLainnya;
    private widget.TextBox ProsedurSekunder1;
    private widget.TextBox ProsedurSekunder2;
    private widget.TextBox ProsedurSekunder3;
    private widget.TextBox ProsedurSekunder4;
    private widget.TextBox ProsedurSekunder5;
    private widget.TextBox ProsedurSekunder6;
    private widget.TextArea ProsedurUtama;
    private widget.TextBox Ruang;
    private widget.TextBox Ruang1;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Sudah;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextArea Terapi;
    private widget.TextBox TglLahir;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
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
    private widget.Label jLabel4;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
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
                    "resume_pasien_ranap.kd_dokter,dokter.nm_dokter,resume_pasien_ranap.tgl_lahir,resume_pasien_ranap.jk,resume_pasien_ranap.kd_kamar,resume_pasien_ranap.masuk,resume_pasien_ranap.jam_masuk,resume_pasien_ranap.keluar, "+
                    "resume_pasien_ranap.jam_keluar,resume_pasien_ranap.kd_dokter1,resume_pasien_ranap.nm_dokter1,resume_pasien_ranap.kd_pj,penjab.png_jawab,resume_pasien_ranap.diagnosa_awal,resume_pasien_ranap.alasan,resume_pasien_ranap.keluhan_utama,resume_pasien_ranap.jalannya_penyakit, "+
                    "resume_pasien_ranap.pemeriksaan_penunjang,resume_pasien_ranap.hasil_laborat,resume_pasien_ranap.terapi,resume_pasien_ranap.diagnosa_utama,resume_pasien_ranap.kd_diagnosa_utama, "+
                    "resume_pasien_ranap.diagnosa_sekunder,resume_pasien_ranap.kd_diagnosa_sekunder,resume_pasien_ranap.diagnosa_sekunder2,resume_pasien_ranap.kd_diagnosa_sekunder2, "+
                    "resume_pasien_ranap.diagnosa_sekunder3,resume_pasien_ranap.kd_diagnosa_sekunder3,resume_pasien_ranap.diagnosa_sekunder4,resume_pasien_ranap.kd_diagnosa_sekunder4, "+
                    "resume_pasien_ranap.diagnosa_sekunder5,resume_pasien_ranap.kd_diagnosa_sekunder5,resume_pasien_ranap.diagnosa_sekunder6,resume_pasien_ranap.kd_diagnosa_sekunder6, "+
                    "resume_pasien_ranap.diagnosa_sekunder7,resume_pasien_ranap.kd_diagnosa_sekunder7,resume_pasien_ranap.diagnosa_klinis, "+
                    "resume_pasien_ranap.prosedur_utama,resume_pasien_ranap.kd_prosedur_utama,resume_pasien_ranap.prosedur_sekunder,resume_pasien_ranap.kd_prosedur_sekunder, "+
                    "resume_pasien_ranap.prosedur_sekunder2,resume_pasien_ranap.kd_prosedur_sekunder2,resume_pasien_ranap.prosedur_sekunder3,resume_pasien_ranap.kd_prosedur_sekunder3, "+
                    "resume_pasien_ranap.prosedur_sekunder4,resume_pasien_ranap.kd_prosedur_sekunder4,resume_pasien_ranap.prosedur_sekunder5,resume_pasien_ranap.kd_prosedur_sekunder5,resume_pasien_ranap.prosedur_sekunder6,resume_pasien_ranap.kd_prosedur_sekunder6, "+        
                    "resume_pasien_ranap.alergi,resume_pasien_ranap.diet,resume_pasien_ranap.lab_belum,resume_pasien_ranap.edukasi,resume_pasien_ranap.cara_keluar,resume_pasien_ranap.keluar_lainnya, "+
                    "resume_pasien_ranap.keadaan,resume_pasien_ranap.keadaan_lainnya,resume_pasien_ranap.pengobatan,resume_pasien_ranap.pengobatan_lainnya,resume_pasien_ranap.kontrol, "+
                    "resume_pasien_ranap.kode_brng,resume_pasien_ranap.nama,resume_pasien_ranap.jml_barang,resume_pasien_ranap.dosis,resume_pasien_ranap.kode_brng1,resume_pasien_ranap.nama1,resume_pasien_ranap.jml_barang1,resume_pasien_ranap.dosis1, "+
                    "resume_pasien_ranap.kode_brng2,resume_pasien_ranap.nama2,resume_pasien_ranap.jml_barang2,resume_pasien_ranap.dosis2,resume_pasien_ranap.kode_brng3,resume_pasien_ranap.nama3,resume_pasien_ranap.jml_barang3,resume_pasien_ranap.dosis3, "+
                    "resume_pasien_ranap.kode_brng4,resume_pasien_ranap.nama4,resume_pasien_ranap.jml_barang4,resume_pasien_ranap.dosis4,resume_pasien_ranap.kode_brng5,resume_pasien_ranap.nama5,resume_pasien_ranap.jml_barang5,resume_pasien_ranap.dosis5, "+
                    "resume_pasien_ranap.kode_brng6,resume_pasien_ranap.nama6,resume_pasien_ranap.jml_barang6,resume_pasien_ranap.dosis6,resume_pasien_ranap.kode_brng7,resume_pasien_ranap.nama7,resume_pasien_ranap.jml_barang7,resume_pasien_ranap.dosis7, "+
                    "resume_pasien_ranap.kode_brng8,resume_pasien_ranap.nama8,resume_pasien_ranap.jml_barang8,resume_pasien_ranap.dosis8,resume_pasien_ranap.kode_brng9,resume_pasien_ranap.nama9,resume_pasien_ranap.jml_barang9,resume_pasien_ranap.dosis9, "+
                    "resume_pasien_ranap.sudah from resume_pasien_ranap inner join reg_periksa on resume_pasien_ranap.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on resume_pasien_ranap.kd_dokter=dokter.kd_dokter "+
//                    "inner join dokter as nm_dokter1 ON nm_dokter1.kd_dokter=resume_pasien_ranap.kd_dokter1 "+
                    "inner join penjab on resume_pasien_ranap.kd_pj=penjab.kd_pj "+
//                    "inner join databarang on resume_pasien_ranap.kode_brng=databarang.kode_brng "+
//                    "LEFT JOIN databarang as obat1 ON obat1.kode_brng=resume_pasien_ranap.kode_brng1 "+
//                    "LEFT JOIN databarang as obat2 ON obat2.kode_brng=resume_pasien_ranap.kode_brng2 "+
//                    "LEFT JOIN databarang as obat3 ON obat3.kode_brng=resume_pasien_ranap.kode_brng3 "+
//                    "LEFT JOIN databarang as obat4 ON obat4.kode_brng=resume_pasien_ranap.kode_brng4 "+
//                    "LEFT JOIN databarang as obat5 ON obat5.kode_brng=resume_pasien_ranap.kode_brng5 "+
//                    "LEFT JOIN databarang as obat6 ON obat6.kode_brng=resume_pasien_ranap.kode_brng6 "+
//                    "LEFT JOIN databarang as obat7 ON obat7.kode_brng=resume_pasien_ranap.kode_brng7 "+
//                    "LEFT JOIN databarang as obat8 ON obat8.kode_brng=resume_pasien_ranap.kode_brng8 "+
//                    "LEFT JOIN databarang as obat9 ON obat9.kode_brng=resume_pasien_ranap.kode_brng9 "+
                    "where reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "resume_pasien_ranap.kd_dokter,dokter.nm_dokter,resume_pasien_ranap.tgl_lahir,resume_pasien_ranap.jk,resume_pasien_ranap.kd_kamar,resume_pasien_ranap.masuk,resume_pasien_ranap.jam_masuk,resume_pasien_ranap.keluar, "+
                    "resume_pasien_ranap.jam_keluar,resume_pasien_ranap.kd_dokter1,resume_pasien_ranap.nm_dokter1,resume_pasien_ranap.kd_pj,penjab.png_jawab,resume_pasien_ranap.diagnosa_awal,resume_pasien_ranap.alasan,resume_pasien_ranap.keluhan_utama,resume_pasien_ranap.jalannya_penyakit, "+
                    "resume_pasien_ranap.pemeriksaan_penunjang,resume_pasien_ranap.hasil_laborat,resume_pasien_ranap.terapi,resume_pasien_ranap.diagnosa_utama,resume_pasien_ranap.kd_diagnosa_utama, "+
                    "resume_pasien_ranap.diagnosa_sekunder,resume_pasien_ranap.kd_diagnosa_sekunder,resume_pasien_ranap.diagnosa_sekunder2,resume_pasien_ranap.kd_diagnosa_sekunder2, "+
                    "resume_pasien_ranap.diagnosa_sekunder3,resume_pasien_ranap.kd_diagnosa_sekunder3,resume_pasien_ranap.diagnosa_sekunder4,resume_pasien_ranap.kd_diagnosa_sekunder4, "+
                    "resume_pasien_ranap.diagnosa_sekunder5,resume_pasien_ranap.kd_diagnosa_sekunder5,resume_pasien_ranap.diagnosa_sekunder6,resume_pasien_ranap.kd_diagnosa_sekunder6, "+
                    "resume_pasien_ranap.diagnosa_sekunder7,resume_pasien_ranap.kd_diagnosa_sekunder7,resume_pasien_ranap.diagnosa_klinis, "+
                    "resume_pasien_ranap.prosedur_utama,resume_pasien_ranap.kd_prosedur_utama,resume_pasien_ranap.prosedur_sekunder,resume_pasien_ranap.kd_prosedur_sekunder, "+
                    "resume_pasien_ranap.prosedur_sekunder2,resume_pasien_ranap.kd_prosedur_sekunder2,resume_pasien_ranap.prosedur_sekunder3,resume_pasien_ranap.kd_prosedur_sekunder3, "+
                    "resume_pasien_ranap.prosedur_sekunder4,resume_pasien_ranap.kd_prosedur_sekunder4,resume_pasien_ranap.prosedur_sekunder5,resume_pasien_ranap.kd_prosedur_sekunder5,resume_pasien_ranap.prosedur_sekunder6,resume_pasien_ranap.kd_prosedur_sekunder6, "+
                    "resume_pasien_ranap.alergi,resume_pasien_ranap.diet,resume_pasien_ranap.lab_belum,resume_pasien_ranap.edukasi,resume_pasien_ranap.cara_keluar,resume_pasien_ranap.keluar_lainnya, "+
                    "resume_pasien_ranap.keadaan,resume_pasien_ranap.keadaan_lainnya,resume_pasien_ranap.pengobatan,resume_pasien_ranap.pengobatan_lainnya,resume_pasien_ranap.kontrol, "+
                    "resume_pasien_ranap.kode_brng,resume_pasien_ranap.nama,resume_pasien_ranap.jml_barang,resume_pasien_ranap.dosis,resume_pasien_ranap.kode_brng1,resume_pasien_ranap.nama1,resume_pasien_ranap.jml_barang1,resume_pasien_ranap.dosis1, "+
                    "resume_pasien_ranap.kode_brng2,resume_pasien_ranap.nama2,resume_pasien_ranap.jml_barang2,resume_pasien_ranap.dosis2,resume_pasien_ranap.kode_brng3,resume_pasien_ranap.nama3,resume_pasien_ranap.jml_barang3,resume_pasien_ranap.dosis3, "+
                    "resume_pasien_ranap.kode_brng4,resume_pasien_ranap.nama4,resume_pasien_ranap.jml_barang4,resume_pasien_ranap.dosis4,resume_pasien_ranap.kode_brng5,resume_pasien_ranap.nama5,resume_pasien_ranap.jml_barang5,resume_pasien_ranap.dosis5, "+
                    "resume_pasien_ranap.kode_brng6,resume_pasien_ranap.nama6,resume_pasien_ranap.jml_barang6,resume_pasien_ranap.dosis6,resume_pasien_ranap.kode_brng7,resume_pasien_ranap.nama7,resume_pasien_ranap.jml_barang7,resume_pasien_ranap.dosis7, "+
                    "resume_pasien_ranap.kode_brng8,resume_pasien_ranap.nama8,resume_pasien_ranap.jml_barang8,resume_pasien_ranap.dosis8,resume_pasien_ranap.kode_brng9,resume_pasien_ranap.nama9,resume_pasien_ranap.jml_barang9,resume_pasien_ranap.dosis9, "+
                    "resume_pasien_ranap.sudah from resume_pasien_ranap inner join reg_periksa on resume_pasien_ranap.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on resume_pasien_ranap.kd_dokter=dokter.kd_dokter "+
//                    "inner join dokter as nm_dokter1 ON nm_dokter1.kd_dokter=resume_pasien_ranap.kd_dokter1 "+
                    "inner join penjab on resume_pasien_ranap.kd_pj=penjab.kd_pj "+
//                    "inner join databarang on resume_pasien_ranap.kode_brng=databarang.kode_brng "+
//                    "LEFT JOIN databarang as obat1 ON obat1.kode_brng=resume_pasien_ranap.kode_brng1 "+
//                    "LEFT JOIN databarang as obat2 ON obat2.kode_brng=resume_pasien_ranap.kode_brng2 "+
//                    "LEFT JOIN databarang as obat3 ON obat3.kode_brng=resume_pasien_ranap.kode_brng3 "+
//                    "LEFT JOIN databarang as obat4 ON obat4.kode_brng=resume_pasien_ranap.kode_brng4 "+
//                    "LEFT JOIN databarang as obat5 ON obat5.kode_brng=resume_pasien_ranap.kode_brng5 "+
//                    "LEFT JOIN databarang as obat6 ON obat6.kode_brng=resume_pasien_ranap.kode_brng6 "+
//                    "LEFT JOIN databarang as obat7 ON obat7.kode_brng=resume_pasien_ranap.kode_brng7 "+
//                    "LEFT JOIN databarang as obat8 ON obat8.kode_brng=resume_pasien_ranap.kode_brng8 "+
//                    "LEFT JOIN databarang as obat9 ON obat9.kode_brng=resume_pasien_ranap.kode_brng9 "+
                    "where reg_periksa.tgl_registrasi between ? and ? and reg_periksa.status_lanjut like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien_ranap.kd_dokter like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and dokter.nm_dokter like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien_ranap.keluar like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien_ranap.kd_diagnosa_utama like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien_ranap.diagnosa_utama like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien_ranap.prosedur_utama like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rawat like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien_ranap.kd_prosedur_utama like ? "+
                    "order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut");
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
//                    ps.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
//                    ps.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
//                    ps.setString(36,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("tgl_registrasi"),rs.getString("status_lanjut"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("kd_kamar"),rs.getString("masuk"),rs.getString("jam_masuk"),rs.getString("keluar"),
                        rs.getString("jam_keluar"),rs.getString("kd_dokter1"),rs.getString("nm_dokter1"),rs.getString("kd_pj"),rs.getString("png_jawab"),rs.getString("diagnosa_awal"),rs.getString("alasan"),
                        rs.getString("keluhan_utama"),rs.getString("jalannya_penyakit"),rs.getString("pemeriksaan_penunjang"),rs.getString("hasil_laborat"),rs.getString("terapi"),rs.getString("diagnosa_utama"),
                        rs.getString("kd_diagnosa_utama"),rs.getString("diagnosa_sekunder"),rs.getString("kd_diagnosa_sekunder"),rs.getString("diagnosa_sekunder2"),
                        rs.getString("kd_diagnosa_sekunder2"),rs.getString("diagnosa_sekunder3"),rs.getString("kd_diagnosa_sekunder3"),rs.getString("diagnosa_sekunder4"),rs.getString("kd_diagnosa_sekunder4"),
                        rs.getString("diagnosa_sekunder5"),rs.getString("kd_diagnosa_sekunder5"),rs.getString("diagnosa_sekunder6"),rs.getString("kd_diagnosa_sekunder6"),rs.getString("diagnosa_sekunder7"),rs.getString("kd_diagnosa_sekunder7"),rs.getString("diagnosa_klinis"),
                        rs.getString("prosedur_utama"),rs.getString("kd_prosedur_utama"),rs.getString("prosedur_sekunder"),
                        rs.getString("kd_prosedur_sekunder"),rs.getString("prosedur_sekunder2"),rs.getString("kd_prosedur_sekunder2"),rs.getString("prosedur_sekunder3"),rs.getString("kd_prosedur_sekunder3"),
                        rs.getString("prosedur_sekunder4"),rs.getString("kd_prosedur_sekunder4"),rs.getString("prosedur_sekunder5"),rs.getString("kd_prosedur_sekunder5"),rs.getString("prosedur_sekunder6"),rs.getString("kd_prosedur_sekunder6"),
                        rs.getString("alergi"),rs.getString("diet"),rs.getString("lab_belum"),rs.getString("edukasi"),rs.getString("cara_keluar"),rs.getString("keluar_lainnya"),
                        rs.getString("keadaan"),rs.getString("keadaan_lainnya"),rs.getString("pengobatan"),rs.getString("pengobatan_lainnya"),rs.getString("kontrol"),
                        rs.getString("kode_brng"),rs.getString("nama"),rs.getString("jml_barang"),rs.getString("dosis"),rs.getString("kode_brng1"),rs.getString("nama1"),rs.getString("jml_barang1"),rs.getString("dosis1"),
                        rs.getString("kode_brng2"),rs.getString("nama2"),rs.getString("jml_barang2"),rs.getString("dosis2"),rs.getString("kode_brng3"),rs.getString("nama3"),rs.getString("jml_barang3"),rs.getString("dosis3"),
                        rs.getString("kode_brng4"),rs.getString("nama4"),rs.getString("jml_barang4"),rs.getString("dosis4"),rs.getString("kode_brng5"),rs.getString("nama5"),rs.getString("jml_barang5"),rs.getString("dosis5"),
                        rs.getString("kode_brng6"),rs.getString("nama6"),rs.getString("jml_barang6"),rs.getString("dosis6"),rs.getString("kode_brng7"),rs.getString("nama7"),rs.getString("jml_barang7"),rs.getString("dosis7"),
                        rs.getString("kode_brng8"),rs.getString("nama8"),rs.getString("jml_barang8"),rs.getString("dosis8"),rs.getString("kode_brng9"),rs.getString("nama9"),rs.getString("jml_barang9"),rs.getString("dosis9"),rs.getString("sudah")
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
        Keluar.getSelectedItem();
        Keluhan.setText("");
        JalannyaPenyakit.setText("");
//        PemeriksaanPenunjang.setText("");
//        HasilLaborat.setText("");
        DiagnosaUtama.setText("");
        DiagnosaSekunder1.setText("");
        DiagnosaSekunder2.setText("");
        DiagnosaSekunder3.setText("");
        DiagnosaSekunder4.setText("");
        DiagnosaSekunder5.setText("");
        DiagnosaSekunder6.setText("");
        DiagnosaSekunder7.setText("");
        ProsedurUtama.setText("");
        ProsedurSekunder1.setText("");
        ProsedurSekunder2.setText("");
        ProsedurSekunder3.setText("");
        ProsedurSekunder4.setText("");
        ProsedurSekunder5.setText("");
        ProsedurSekunder6.setText("");
        KodeDiagnosaUtama.setText("");
        KodeDiagnosaSekunder1.setText("");
        KodeDiagnosaSekunder2.setText("");
        KodeDiagnosaSekunder3.setText("");
        KodeDiagnosaSekunder4.setText("");
        KodeDiagnosaSekunder5.setText("");
        KodeDiagnosaSekunder6.setText("");
        KodeDiagnosaSekunder7.setText("");
        KodeProsedurUtama.setText("");
        KodeProsedurSekunder1.setText("");
        KodeProsedurSekunder2.setText("");
        KodeProsedurSekunder3.setText("");
        KodeProsedurSekunder4.setText("");
        KodeProsedurSekunder5.setText("");
        KodeProsedurSekunder6.setText("");
        DiagnosaKlinis.setText("");
        CaraKeluar.requestFocus();
        Keadaan.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());  
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());  
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());  
            KodeDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());  
            NamaDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()); 
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Ruang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Masuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            JamMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Keluar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
//            JamKeluar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            cmbJam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().substring(6,8));
            KodeDokter1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());  
            NamaDokter1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            CaraBayar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            CaraBayar1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            DiagnosaAwal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
            Alasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString()); 
            Keluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString()); 
            JalannyaPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());   
            PemeriksaanPenunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());  
            HasilLaborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());  
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            DiagnosaUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());  
            KodeDiagnosaUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            DiagnosaSekunder1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());  
            KodeDiagnosaSekunder1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());    
            DiagnosaSekunder2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());  
            KodeDiagnosaSekunder2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());    
            DiagnosaSekunder3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());  
            KodeDiagnosaSekunder3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());    
            DiagnosaSekunder4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());  
            KodeDiagnosaSekunder4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());  
            DiagnosaSekunder5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());  
            KodeDiagnosaSekunder5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());    
            DiagnosaSekunder6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());  
            KodeDiagnosaSekunder6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());    
            DiagnosaSekunder7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());  
            KodeDiagnosaSekunder7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            DiagnosaKlinis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString()); 
            ProsedurUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());  
            KodeProsedurUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());     
            ProsedurSekunder1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());  
            KodeProsedurSekunder1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString()); 
            ProsedurSekunder2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());  
            KodeProsedurSekunder2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString()); 
            ProsedurSekunder3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());  
            KodeProsedurSekunder3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString()); 
            ProsedurSekunder4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());  
            KodeProsedurSekunder4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString()); 
            ProsedurSekunder5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());  
            KodeProsedurSekunder5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString()); 
            ProsedurSekunder6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());  
            KodeProsedurSekunder6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString()); 
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            Diet.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            LabBelum.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            CaraKeluar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            KeluarLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            Keadaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            KeadaanLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            Pengobatan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            PengobatanLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            Valid.SetTgl2(Kontrol,tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            Obat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
//            Nama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
//            Jumlah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
//            Dosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            Obat1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            Nama1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            Jumlah1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            Dosis1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            Obat2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            Nama2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            Jumlah2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            Dosis2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            Obat3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            Nama3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            Jumlah3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            Dosis3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            Obat4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            Nama4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            Jumlah4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            Dosis4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            Obat5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            Nama5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            Jumlah5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            Dosis5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            Obat6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            Nama6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString());
            Jumlah6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),93).toString());
            Dosis6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),94).toString());
            Obat7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),95).toString());
            Nama7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),96).toString());
            Jumlah7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),97).toString());
            Dosis7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),98).toString());
            Obat8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),99).toString());
            Nama8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),100).toString());
            Jumlah8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),101).toString());
            Dosis8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),102).toString());
            Obat9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),103).toString());
            Nama9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),104).toString());
            Jumlah9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),105).toString());
            Dosis9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),106).toString());
            Sudah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),107).toString());
        }
    }
    
    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
//        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NamaDokter1,KodeDokter1.getText());
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
        
        try {
            ps=koneksi.prepareStatement(
                    "select a.kd_dokter,b.nm_dokter from dpjp_ranap a  "+
                    "inner join dokter b on b.kd_dokter=a.kd_dokter "+
                    "where a.no_rawat=?");
            try {
                ps.setString(1,noRawatSumberRanap.isEmpty()?TNoRw.getText():noRawatSumberRanap);
                rs=ps.executeQuery();
                if(rs.next()){
                    KodeDokter.setText(rs.getString("kd_dokter"));
                    NamaDokter.setText(rs.getString("nm_dokter"));
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
        
//        if(DiagnosaUtama.getText().isEmpty()){
            try {
            DiagnosaUtama.setText("");
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
                 DiagnosaUtama.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
//        }
                
        try {
            ProsedurUtama.setText("");
            ps=koneksi.prepareStatement(
                    "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.prioritas "+
                    "from prosedur_pasien inner join icd9 on prosedur_pasien.kode=icd9.kode "+
                    "where prosedur_pasien.no_rawat='"+TNoRw.getText()+"' and prosedur_pasien.status='Ranap' order by prosedur_pasien.prioritas ASC");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                while(rs.next()){
                    Listpenyakit=rs.getString("kode")+"-"+rs.getString("deskripsi_panjang");
                    namaPenyakit=namaPenyakit+""+Listpenyakit+",  "+"\n";
                }
                 ProsedurUtama.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
        Alergi.setText("-");
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
        String sumberRanap=noRawatSumberRanap.isEmpty()?TNoRw.getText():noRawatSumberRanap;
        Sequel.cariIsi("select kd_kamar from kamar_inap where no_rawat='"+sumberRanap+"' order by tgl_masuk desc limit 1",Ruang);
        Sequel.cariIsi("select concat(bangsal.nm_bangsal) as kd_kamar,kamar.kd_bangsal,kamar_inap.kd_kamar from kamar_inap INNER JOIN kamar ON kamar_inap.kd_kamar = kamar.kd_kamar INNER JOIN bangsal ON kamar.kd_bangsal = bangsal.kd_bangsal where no_rawat='"+sumberRanap+"' order by tgl_masuk desc limit 1",Ruang1);
        if(sumberRanap.equals(TNoRw.getText())){
            Sequel.cariIsi("select diagnosa_awal from kamar_inap where no_rawat='"+sumberRanap+"' ",DiagnosaAwal);
        }else{
            DiagnosaAwal.setText("");
        }
        Sequel.cariIsi("select tgl_masuk from kamar_inap where no_rawat='"+sumberRanap+"' order by tgl_masuk asc limit 1",Masuk);
        Sequel.cariIsi("select jam_masuk from kamar_inap where no_rawat='"+sumberRanap+"' order by tgl_masuk asc,jam_masuk asc limit 1",JamMasuk);
//        Sequel.cariIsi("select tgl_keluar from kamar_inap where no_rawat='"+TNoRw.getText()+"' order by tgl_keluar desc limit 1",Keluar);
//        Sequel.cariIsi("select jam_keluar from kamar_inap where no_rawat='"+TNoRw.getText()+"' order by jam_keluar desc limit 1",JamKeluar);
//        Sequel.cariIsi("select reg_periksa.kd_dokter, dokter.nm_dokter from reg_periksa INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter where no_rawat='"+TNoRw.getText()+"' ",KodeDokter1,NamaDokter1);
        Sequel.cariIsi("select reg_periksa.kd_dokter, dokter.nm_dokter from reg_periksa INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter where no_rawat=?",KodeDokter1,TNoRw.getText());
        Sequel.cariIsi("select dokter.nm_dokter from reg_periksa INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter where no_rawat=?",NamaDokter1,TNoRw.getText());
        Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where no_rawat=?",CaraBayar,TNoRw.getText());
        Sequel.cariIsi("select penjab.png_jawab from reg_periksa INNER JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj where no_rawat=?",CaraBayar1,TNoRw.getText());
//        Sequel.cariIsi("select diet.nama_diet from detail_beri_diet INNER JOIN diet ON detail_beri_diet.kd_diet = diet.kd_diet where no_rawat=?",Diet,TNoRw.getText());
//        Sequel.cariIsi("select pemeriksaan_ranap.alergi from pemeriksaan_ranap INNER JOIN reg_periksa ON pemeriksaan_ranap.no_rawat = reg_periksa.no_rawat where pemeriksaan_ranap.no_rawat=? ORDER BY pemeriksaan_ranap.tgl_perawatan desc",Alergi,TNoRw.getText());
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        setNoRmDenganSumberRanap(norwt,norwt,tgl2);
    }

    public void setNoRmGabung(String norwt,String noRawatIbu,Date tgl2) {
        setNoRmDenganSumberRanap(norwt,noRawatIbu,tgl2);
    }

    private void setNoRmDenganSumberRanap(String norwt,String sumberRanap,Date tgl2) {
        noRawatSumberRanap=sumberRanap==null||sumberRanap.trim().isEmpty()?norwt:sumberRanap.trim();
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);    
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
        CaraKeluar.requestFocus();
        
        
//        Menampilkan Tanggal Jam Pulang
        try {
            ps=koneksi.prepareStatement(
                    "SELECT IF(DATE_FORMAT(kamar_inap.tgl_keluar, \"%d-%m-%Y\")='00-00-0000',DATE_FORMAT(CURRENT_DATE,\"%d-%m-%Y\"),DATE_FORMAT(kamar_inap.tgl_keluar, \"%d-%m-%Y\")) as tgl_keluar,IF(kamar_inap.jam_keluar='00:00:00',CURRENT_TIME,kamar_inap.jam_keluar) as jam_keluar FROM kamar_inap "
                            + "WHERE kamar_inap.no_rawat=?");
            try {
                ps.setString(1,noRawatSumberRanap);
                rs=ps.executeQuery();
                while(rs.next()){
//                    if(Keluar.getSelectedItem()){
                        Keluar.setSelectedItem(rs.getString("tgl_keluar"));
//                    }
                    
//                    if(JamKeluar.getSelectedItem()+""){
//                        JamKeluar.setSelectedItem(rs.getString("jam_keluar"));
//                    }
                cmbJam.setSelectedItem(rs.getString("jam_keluar").toString().substring(0,2));
                cmbMnt.setSelectedItem(rs.getString("jam_keluar").toString().substring(3,5));
                cmbDtk.setSelectedItem(rs.getString("jam_keluar").toString().substring(6,8));
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
        
        
//      Menampilkan Riwayat Perawatan
        try {
            ps=koneksi.prepareStatement(
                    "select rps, ket_fisik from asesmen_medis_igd inner join pegawai on asesmen_medis_igd.kd_dokter=pegawai.nik where "+
                    "no_rawat=? ");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                while(rs.next()){
                    if(Keluhan.getText().isEmpty()){
                        Keluhan.setText(rs.getString("rps")+", ");
                    }
                    
                    if(JalannyaPenyakit.getText().isEmpty()){
                        JalannyaPenyakit.setText(rs.getString("ket_fisik")+", ");
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
        
        try {
            ps=koneksi.prepareStatement(
                    "SELECT keluhan,pemeriksaan FROM pemeriksaan_ralan "
                            + "INNER JOIN dokter ON dokter.kd_dokter=pemeriksaan_ralan.nik "
                            + "WHERE no_rawat=?");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                while(rs.next()){
                    if(Keluhan.getText().isEmpty()){
                        Keluhan.setText(rs.getString("keluhan")+", ");
                    }
                    
                    if(JalannyaPenyakit.getText().isEmpty()){
                        JalannyaPenyakit.setText(rs.getString("pemeriksaan")+", ");
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
        
        //      Menampilkan Alasan Ranap
        try {
            ps=koneksi.prepareStatement(
                    "SELECT * FROM permintaan_ranap WHERE permintaan_ranap.no_rawat=?");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                while(rs.next()){
                    if(Alasan.getText().isEmpty()){
                        Alasan.setText(rs.getString("catatan"));
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
        
        
        // Menampilkan Laborat
        try {
            ps=koneksi.prepareStatement(
                    " SELECT a.no_rawat,IFNULL(c.nm_perawatan,'') as nm_lab FROM reg_periksa a "
                            + "LEFT JOIN periksa_lab b ON b.no_rawat = a.no_rawat "
                            + "LEFT JOIN jns_perawatan_lab c ON c.kd_jenis_prw = b.kd_jenis_prw  "
//                            + "LEFT JOIN template_laboratorium e ON e.id_template = b.id_template  "
                            + "WHERE a.no_rawat=?");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                namaPenyakit="";
                while(rs.next()){
                    Listpenyakit=rs.getString("nm_lab");
                    namaPenyakit=namaPenyakit+""+Listpenyakit+",  "+"\n";
//                    namaPenyakit=namaPenyakit+""+rs.getString("nm_lab")+", "+Listpenyakit+",  "+"\n";
                }
                 HasilLaborat.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                while(rs.next()){
                    if(PemeriksaanPenunjang.getText().isEmpty()){
//                        PemeriksaanPenunjang.setText(rs.getString("nm_perawatan")+" : "+rs.getString("hasil")+", ");
                        PemeriksaanPenunjang.setText(rs.getString("nm_perawatan")+", ");
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
        
        
        //      Menampilkan Resep Obat
        try {
            ps=koneksi.prepareStatement(
                    " select concat(databarang.nama_brng,' ',resep_dokter.jml,' ',kodesatuan.satuan,' ',resep_dokter.aturan_pakai) as obat "
                            + "FROM resep_dokter INNER JOIN resep_obat ON resep_dokter.no_resep = resep_obat.no_resep "
                            + "INNER JOIN databarang ON resep_dokter.kode_brng = databarang.kode_brng "
                            + "INNER JOIN kodesatuan ON databarang.kode_sat = kodesatuan.kode_sat "
                            + "where resep_obat.no_rawat=?");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                namaPenyakit="";
                while(rs.next()){
                    Listpenyakit=rs.getString("obat");
                    namaPenyakit=namaPenyakit+""+Listpenyakit+",  "+"\n";
                }
                 Terapi.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
        
        //      Menapilkan Racikan Obat
        try {
            ps=koneksi.prepareStatement(
                    " select concat(resep_dokter_racikan.nama_racik,' ',resep_dokter_racikan.jml_dr,' ',resep_dokter_racikan.aturan_pakai) as obat "
                            + "FROM resep_dokter_racikan INNER JOIN resep_obat ON resep_dokter_racikan.no_resep = resep_obat.no_resep where resep_obat.no_rawat=?");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                namaPenyakit="";
                while(rs.next()){
                    Listpenyakit=rs.getString("obat");
                    namaPenyakit=namaPenyakit+""+Listpenyakit+","+"\n";
                }
                 Terapi.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
        
        
        //      Menampilkan Resep Obat Pulang
        try {
            ps=koneksi.prepareStatement(
                    " select concat(databarang.nama_brng,' ',resep_pulang.jml_barang,' ',kodesatuan.satuan,' ',resep_pulang.dosis) as obat "
                            + "FROM resep_pulang INNER JOIN databarang ON resep_pulang.kode_brng = databarang.kode_brng "
                            + "INNER JOIN kodesatuan ON databarang.kode_sat = kodesatuan.kode_sat "
                            + "where resep_pulang.no_rawat=?");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                namaPenyakit="";
                while(rs.next()){
                    Listpenyakit=rs.getString("obat");
                    namaPenyakit=namaPenyakit+""+Listpenyakit+",  "+"\n";
                }
                 Obat.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
    
    void createPdf(String FileName){
    Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            param.put("norawat",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())); 
            if(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString().equals("Ralan")){
                param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
            }else{
                param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
            }
           
            Valid.MyReportPDFWithName("rptLaporanResumeRanap.jasper","report","tempfile",FileName,"::[ Laporan Resume Pasien ]::",param);
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
    }

    
}
