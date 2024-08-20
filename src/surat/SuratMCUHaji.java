/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package surat;

import rekammedis.*;
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
import java.text.SimpleDateFormat;


/**
 *
 * @author perpustakaan
 */
public final class SuratMCUHaji extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String FileName;
    private int i=0;    
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
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
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
//    private SimpleDateFormat tanggalNow = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public SuratMCUHaji(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "Tgl.Rawat","Status","No.Rawat","No.RM","Nama Pasien","Kode Dokter","Dokter Pemeriksa/MCU","Tanggal","Jam","Keluhan","Riwayat Alergi",
            "Riwayat Kesehatan Dahulu","Riwayat Kesehatan Keluarga","Kesadaran","TD(x/menit)","Nadi(Frekuensi)","Nadi(Isi)",
            "Nadi(Tegangan)","Nadi(Ritme)","Nafas(Frekuensi)","Nafas(Ritme)","Suhu","BB","TB","IMT","Kulit","A.Kepala","B.Kepala",
            "C.Kepala","Mata Kanan","Mata Kiri","A.THT","B.THT","C.THT","A.Kelenjar","B.Kelenjar","A.Dada(Umum)","B.Dada(Umum)","C.Dada(Umum)","D.Dada(Umum)",
            "A.Dada(Jantung)","B.Dada(Jantung)","C.Dada(Jantung)","D.Dada(Jantung)","A.Dada(Paru)","B.Dada(Paru)","C.Dada(Paru)","D.Dada(Paru)",
            "A.Perut(Umum)","B.Perut(Umum)","C.Perut(Umum)","D.Perut(Umum)","A.Perut(Sistem/Khusus)","B.Perut(Sistem/Khusus)","C.Perut(Sistem/Khusus)","D.Perut(Sistem/Khusus)",
            "A.Eksternitas","B.Eksternitas","C.Eksternitas","EKG","Radiologi","Laboratorium","Kesimpulan","Anjuran"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 64; i++) {
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
                column.setPreferredWidth(170);
            }else if(i==25){
                column.setPreferredWidth(75);
            }else if(i==26){
                column.setPreferredWidth(170);
            }else if(i==27){
                column.setPreferredWidth(75);
            }else if(i==28){
                column.setPreferredWidth(170);
            }else if(i==29){
                column.setPreferredWidth(75);
            }else if(i==30){
                column.setPreferredWidth(250);
            }else if(i==31){
                column.setPreferredWidth(75);
            }else if(i==32){
                column.setPreferredWidth(75);
            }else if(i==33){
                column.setPreferredWidth(250);
            }else if(i==34){
                column.setPreferredWidth(40);
            }else if(i==35){
                column.setPreferredWidth(105);
            }else if(i==36){
                column.setPreferredWidth(65);
            }else if(i==37){
                column.setPreferredWidth(150);
            }else if(i==38){
                column.setPreferredWidth(90);
            }else if(i==39){
                column.setPreferredWidth(150);
            }else if(i==40){
                column.setPreferredWidth(60);
            }else if(i==41){
                column.setPreferredWidth(250);
            }else if(i==42){
                column.setPreferredWidth(250);
            }else if(i==43){
                column.setPreferredWidth(250);
            }else if(i==44){
                column.setPreferredWidth(250);
            }else if(i==45){
                column.setPreferredWidth(170);
            }else if(i==46){
                column.setPreferredWidth(75);
            }else if(i==47){
                column.setPreferredWidth(170);
            }else if(i==48){
                column.setPreferredWidth(75);
            }else if(i==49){
                column.setPreferredWidth(170);
            }else if(i==50){
                column.setPreferredWidth(75);
            }else if(i==51){
                column.setPreferredWidth(170);
            }else if(i==52){
                column.setPreferredWidth(75);
            }else if(i==53){
                column.setPreferredWidth(170);
            }else if(i==54){
                column.setPreferredWidth(75);
            }else if(i==55){
                column.setPreferredWidth(170);
            }else if(i==56){
                column.setPreferredWidth(75);
            }else if(i==57){
                column.setPreferredWidth(170);
            }else if(i==58){
                column.setPreferredWidth(75);
            }else if(i==59){
                column.setPreferredWidth(170);
            }else if(i==60){
                column.setPreferredWidth(75);
            }else if(i==61){
                column.setPreferredWidth(170);
            }else if(i==62){
                column.setPreferredWidth(75);
            }else if(i==63){
                column.setPreferredWidth(170);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TAlergi.setDocument(new batasInput((int)255).getKata(TAlergi));
        Kesadaran.setDocument(new batasInput((int)100).getKata(Kesadaran));
        TTensi.setDocument(new batasInput((byte)8).getKata(TTensi));
        TNadi.setDocument(new batasInput((byte)3).getKata(TNadi));
        Nafas.setDocument(new batasInput((int)10).getKata(Nafas));
        TSuhu.setDocument(new batasInput((byte)5).getKata(TSuhu));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        BMI.setDocument(new batasInput((int)10).getKata(BMI));
        Visuskanan.setDocument(new batasInput((int)255).getKata(Visuskanan));
        Visuskiri.setDocument(new batasInput((int)255).getKata(Visuskiri));
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
        
        carikeluhanass.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carikeluhanass.getTable().getSelectedRow()!= -1){
                    Keluhan.append(carikeluhanass.getTable().getValueAt(carikeluhanass.getTable().getSelectedRow(),2).toString()+", ");
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
                    EKG.append(caripemeriksaan.getTable().getValueAt(caripemeriksaan.getTable().getSelectedRow(),2).toString()+", ");
                    EKG.requestFocus();
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
        
        caripemeriksaanass.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(caripemeriksaanass.getTable().getSelectedRow()!= -1){
                    EKG.append(caripemeriksaanass.getTable().getValueAt(caripemeriksaanass.getTable().getSelectedRow(),2).toString()+", ");
                    EKG.requestFocus();
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
        
        cariradiologi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariradiologi.getTable().getSelectedRow()!= -1){
                    PemeriksaanPenunjang.append(cariradiologi.getTable().getValueAt(cariradiologi.getTable().getSelectedRow(),2).toString()+", ");
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
        
        carilaborat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carilaborat.getTable().getSelectedRow()!= -1){
                    HasilLaborat.append(carilaborat.getTable().getValueAt(carilaborat.getTable().getSelectedRow(),2).toString()+", ");
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
        
        BB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isBMI();
            }
        });
        
        TB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isBMI();
            }
        });
        
        cariobat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariobat.getTable().getSelectedRow()!= -1){
                    Anjuran.append(cariobat.getTable().getValueAt(cariobat.getTable().getSelectedRow(),2).toString()+", ");
                    Anjuran.requestFocus();
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
        
        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                tampil();
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
        
        rmcariradralan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcariradralan.getTable().getSelectedRow()!= -1){
                    PemeriksaanPenunjang.append(rmcariradralan.getTable().getValueAt(rmcariradralan.getTable().getSelectedRow(),2).toString()+", ");
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
        
        rmcarilabralan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcarilabralan.getTable().getSelectedRow()!= -1){
                    HasilLaborat.append(rmcarilabralan.getTable().getValueAt(rmcarilabralan.getTable().getSelectedRow(),2).toString()+", ");
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
        
        caritindakan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(caritindakan.getTable().getSelectedRow()!= -1){
                    Kesimpulan.append(caritindakan.getTable().getValueAt(caritindakan.getTable().getSelectedRow(),2).toString()+", ");
                    Kesimpulan.requestFocus();
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
        MnDigitalTTE = new javax.swing.JMenuItem();
        MnLaporanResume = new javax.swing.JMenuItem();
        MnInputDiagnosa = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
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
        Keluhan = new widget.TextArea();
        jLabel5 = new widget.Label();
        jLabel8 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        EKG = new widget.TextArea();
        jLabel9 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
        jLabel10 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        HasilLaborat = new widget.TextArea();
        jLabel11 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        Anjuran = new widget.TextArea();
        label14 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        BtnDokter1 = new widget.Button();
        BtnDokter2 = new widget.Button();
        BtnDokter3 = new widget.Button();
        jLabel12 = new widget.Label();
        Isi = new widget.ComboBox();
        jLabel13 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        Kesimpulan = new widget.TextArea();
        jLabel14 = new widget.Label();
        jLabel38 = new widget.Label();
        TAlergi = new widget.TextBox();
        jLabel39 = new widget.Label();
        TRpd = new widget.TextBox();
        jLabel41 = new widget.Label();
        TRpk = new widget.TextBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel16 = new widget.Label();
        jLabel40 = new widget.Label();
        Kesadaran = new widget.TextBox();
        jLabel17 = new widget.Label();
        TTensi = new widget.TextBox();
        jLabel42 = new widget.Label();
        jLabel18 = new widget.Label();
        TNadi = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        jLabel23 = new widget.Label();
        Tegangan = new widget.ComboBox();
        jLabel24 = new widget.Label();
        Ritme = new widget.ComboBox();
        jLabel43 = new widget.Label();
        Nafas = new widget.TextBox();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        Ritme1 = new widget.ComboBox();
        jLabel46 = new widget.Label();
        TSuhu = new widget.TextBox();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        BB = new widget.TextBox();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        TB = new widget.TextBox();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        BMI = new widget.TextBox();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        KepalaA = new widget.ComboBox();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        Kulit = new widget.ComboBox();
        jLabel58 = new widget.Label();
        KepalaB = new widget.ComboBox();
        PanelWall = new usu.widget.glass.PanelGlass();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        jSeparator35 = new javax.swing.JSeparator();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        Kacamata = new widget.ComboBox();
        Visuskanan = new widget.TextBox();
        jLabel63 = new widget.Label();
        Visuskiri = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        Telinga = new widget.ComboBox();
        jLabel66 = new widget.Label();
        Hidung = new widget.ComboBox();
        jLabel67 = new widget.Label();
        Tenggorokan = new widget.ComboBox();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        Inspeksi = new widget.ComboBox();
        jLabel70 = new widget.Label();
        Palpasi = new widget.ComboBox();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        Inspeksi1 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        Palpasi1 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        Perkusi1 = new widget.ComboBox();
        jLabel76 = new widget.Label();
        Auskultasi1 = new widget.ComboBox();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        Inspeksi2 = new widget.ComboBox();
        jLabel79 = new widget.Label();
        Palpasi2 = new widget.ComboBox();
        jLabel80 = new widget.Label();
        Perkusi2 = new widget.ComboBox();
        jLabel81 = new widget.Label();
        Auskultasi2 = new widget.ComboBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        Inspeksi3 = new widget.ComboBox();
        jLabel84 = new widget.Label();
        Palpasi3 = new widget.ComboBox();
        jLabel85 = new widget.Label();
        Perkusi3 = new widget.ComboBox();
        jLabel86 = new widget.Label();
        Auskultasi3 = new widget.ComboBox();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        Inspeksi4 = new widget.ComboBox();
        jLabel90 = new widget.Label();
        Palpasi4 = new widget.ComboBox();
        jLabel91 = new widget.Label();
        Perkusi4 = new widget.ComboBox();
        jLabel92 = new widget.Label();
        Auskultasi4 = new widget.ComboBox();
        jLabel93 = new widget.Label();
        jLabel94 = new widget.Label();
        Hati = new widget.ComboBox();
        jLabel95 = new widget.Label();
        Limpa = new widget.ComboBox();
        jLabel96 = new widget.Label();
        Gastro = new widget.ComboBox();
        jLabel97 = new widget.Label();
        Ginjal = new widget.ComboBox();
        jLabel98 = new widget.Label();
        jLabel99 = new widget.Label();
        Inspeksi5 = new widget.ComboBox();
        jLabel100 = new widget.Label();
        Otot = new widget.ComboBox();
        jLabel101 = new widget.Label();
        Refleks = new widget.ComboBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

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

        MnLaporanResume.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResume.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResume.setText("Cetak MCU Haji");
        MnLaporanResume.setName("MnLaporanResume"); // NOI18N
        MnLaporanResume.setPreferredSize(new java.awt.Dimension(220, 26));
        MnLaporanResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanResumeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanResume);

        MnInputDiagnosa.setBackground(new java.awt.Color(255, 255, 254));
        MnInputDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputDiagnosa.setForeground(new java.awt.Color(50, 50, 50));
        MnInputDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputDiagnosa.setText("Input Diagnosa Pasien");
        MnInputDiagnosa.setName("MnInputDiagnosa"); // NOI18N
        MnInputDiagnosa.setPreferredSize(new java.awt.Dimension(220, 26));
        MnInputDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputDiagnosaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnInputDiagnosa);

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(220, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBerkasDigital);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Surat MCU Haji ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-03-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-03-2023" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1300));
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
        scrollPane2.setBounds(210, 70, 541, 50);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 100, 23);

        jLabel8.setText("EKG :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 990, 240, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        EKG.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        EKG.setColumns(20);
        EKG.setRows(5);
        EKG.setName("EKG"); // NOI18N
        EKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EKGKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(EKG);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(250, 990, 541, 50);

        jLabel9.setText("Pemeriksaan Radiologi :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 1050, 240, 23);

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
        scrollPane4.setBounds(250, 1050, 541, 50);

        jLabel10.setText("Pemeriksaan laboratorium :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 1110, 240, 23);

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
        scrollPane5.setBounds(250, 1110, 541, 50);

        jLabel11.setText("Anjuran :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 1230, 240, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Anjuran.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Anjuran.setColumns(20);
        Anjuran.setRows(5);
        Anjuran.setName("Anjuran"); // NOI18N
        Anjuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnjuranKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Anjuran);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(250, 1230, 541, 50);

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
        KodeDokter.setBounds(104, 40, 141, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        NamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(247, 40, 270, 23);

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
        BtnDokter.setBounds(519, 40, 28, 23);

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
        BtnDokter1.setBounds(170, 90, 28, 23);

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
        BtnDokter2.setBounds(210, 1080, 28, 23);

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
        BtnDokter3.setBounds(210, 1140, 28, 23);

        jLabel12.setText("Isi :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(330, 330, 30, 23);

        Isi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cukup", "Kurang" }));
        Isi.setName("Isi"); // NOI18N
        Isi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IsiActionPerformed(evt);
            }
        });
        Isi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IsiKeyPressed(evt);
            }
        });
        FormInput.add(Isi);
        Isi.setBounds(370, 330, 80, 23);

        jLabel13.setText("Kesimpulan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 1170, 240, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Kesimpulan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulan.setColumns(20);
        Kesimpulan.setRows(5);
        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(Kesimpulan);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(250, 1170, 541, 50);

        jLabel14.setText("PEMERIKSAAN FISIK :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 240, 150, 23);

        jLabel38.setText("Alergi :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(160, 130, 40, 23);

        TAlergi.setHighlighter(null);
        TAlergi.setName("TAlergi"); // NOI18N
        TAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlergiKeyPressed(evt);
            }
        });
        FormInput.add(TAlergi);
        TAlergi.setBounds(210, 130, 540, 23);

        jLabel39.setText("RPD :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(160, 160, 40, 23);

        TRpd.setHighlighter(null);
        TRpd.setName("TRpd"); // NOI18N
        TRpd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRpdKeyPressed(evt);
            }
        });
        FormInput.add(TRpd);
        TRpd.setBounds(210, 160, 540, 23);

        jLabel41.setText("RPK :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(160, 190, 40, 23);

        TRpk.setHighlighter(null);
        TRpk.setName("TRpk"); // NOI18N
        TRpk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TRpkActionPerformed(evt);
            }
        });
        TRpk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRpkKeyPressed(evt);
            }
        });
        FormInput.add(TRpk);
        TRpk.setBounds(210, 190, 540, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 225, 880, 1);

        jLabel16.setText("S (SUBJECTIVE) / Keluhan Utama:");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 70, 200, 23);

        jLabel40.setText("5. Kepala :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(60, 480, 70, 23);

        Kesadaran.setHighlighter(null);
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(160, 270, 130, 23);

        jLabel17.setText("A. Tekanan Darah :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(160, 300, 100, 23);

        TTensi.setHighlighter(null);
        TTensi.setName("TTensi"); // NOI18N
        TTensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTensiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TTensiKeyTyped(evt);
            }
        });
        FormInput.add(TTensi);
        TTensi.setBounds(270, 300, 60, 23);

        jLabel42.setText("1. Kesadaran :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(52, 270, 90, 23);

        jLabel18.setText("x/menit");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(290, 330, 40, 23);

        TNadi.setFocusTraversalPolicyProvider(true);
        TNadi.setName("TNadi"); // NOI18N
        TNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNadiKeyPressed(evt);
            }
        });
        FormInput.add(TNadi);
        TNadi.setBounds(220, 330, 60, 23);

        jLabel20.setText("B. Nadi :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(158, 330, 50, 23);

        jLabel22.setText("mmHg");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(330, 300, 40, 23);

        jLabel23.setText("Tegangan :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(460, 330, 60, 23);

        Tegangan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kuat", "Cukup", "Lemah" }));
        Tegangan.setName("Tegangan"); // NOI18N
        Tegangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TeganganActionPerformed(evt);
            }
        });
        Tegangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeganganKeyPressed(evt);
            }
        });
        FormInput.add(Tegangan);
        Tegangan.setBounds(530, 330, 80, 23);

        jLabel24.setText("Ritme :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(620, 330, 50, 23);

        Ritme.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Reguler", "Irregular" }));
        Ritme.setName("Ritme"); // NOI18N
        Ritme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RitmeActionPerformed(evt);
            }
        });
        Ritme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RitmeKeyPressed(evt);
            }
        });
        FormInput.add(Ritme);
        Ritme.setBounds(680, 330, 100, 23);

        jLabel43.setText("x/menit");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(290, 360, 40, 23);

        Nafas.setFocusTraversalPolicyProvider(true);
        Nafas.setName("Nafas"); // NOI18N
        Nafas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NafasKeyPressed(evt);
            }
        });
        FormInput.add(Nafas);
        Nafas.setBounds(220, 360, 60, 23);

        jLabel44.setText("C. Nafas :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(160, 360, 50, 23);

        jLabel45.setText("Ritme :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(340, 360, 50, 23);

        Ritme1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Reguler", "Irregular" }));
        Ritme1.setName("Ritme1"); // NOI18N
        Ritme1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ritme1ActionPerformed(evt);
            }
        });
        Ritme1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Ritme1KeyPressed(evt);
            }
        });
        FormInput.add(Ritme1);
        Ritme1.setBounds(400, 360, 100, 23);

        jLabel46.setText("C");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(290, 390, 10, 23);

        TSuhu.setFocusTraversalPolicyProvider(true);
        TSuhu.setName("TSuhu"); // NOI18N
        TSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSuhuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TSuhuKeyTyped(evt);
            }
        });
        FormInput.add(TSuhu);
        TSuhu.setBounds(220, 390, 60, 23);

        jLabel47.setText("D. Suhu :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(115, 390, 97, 23);

        jLabel48.setText("c. Mata :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(125, 510, 70, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(180, 420, 60, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Kg");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(240, 420, 30, 23);

        jLabel50.setText("TB :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(260, 420, 40, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(310, 420, 60, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText(" cm");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(380, 420, 30, 23);

        jLabel52.setText("BMI :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(410, 420, 40, 23);

        BMI.setEditable(false);
        BMI.setFocusTraversalPolicyProvider(true);
        BMI.setName("BMI"); // NOI18N
        BMI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BMIKeyPressed(evt);
            }
        });
        FormInput.add(BMI);
        BMI.setBounds(460, 420, 60, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Kg/m²");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(520, 420, 50, 23);

        jLabel54.setText("2. Tanda Vital  :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(60, 300, 90, 23);

        jLabel55.setText("3. Postur :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(50, 420, 80, 23);

        KepalaA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        KepalaA.setName("KepalaA"); // NOI18N
        KepalaA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaAKeyPressed(evt);
            }
        });
        FormInput.add(KepalaA);
        KepalaA.setBounds(350, 480, 128, 23);

        jLabel56.setText("4. Kulit :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(50, 450, 70, 23);

        jLabel57.setText("BB :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(130, 420, 40, 23);

        Kulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kulit.setName("Kulit"); // NOI18N
        Kulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKeyPressed(evt);
            }
        });
        FormInput.add(Kulit);
        Kulit.setBounds(150, 450, 128, 23);

        jLabel58.setText("b. Pemeriksaan syaraf kranial :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(480, 480, 180, 23);

        KepalaB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        KepalaB.setName("KepalaB"); // NOI18N
        KepalaB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaBKeyPressed(evt);
            }
        });
        FormInput.add(KepalaB);
        KepalaB.setBounds(670, 480, 128, 23);

        PanelWall.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/mata.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setOpaque(true);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(640, 550, 200, 80);

        PanelWall1.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/mata.png"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setOpaque(true);
        PanelWall1.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall1.setRound(false);
        PanelWall1.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall1.setLayout(null);
        FormInput.add(PanelWall1);
        PanelWall1.setBounds(210, 550, 200, 80);

        jLabel59.setText("OD : Mata Kanan");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(110, 530, 126, 23);

        jLabel60.setText("OS : Mata Kiri");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(580, 530, 80, 23);

        jSeparator35.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator35.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator35.setName("jSeparator35"); // NOI18N
        FormInput.add(jSeparator35);
        jSeparator35.setBounds(110, 630, 780, 1);

        jLabel61.setText("a. Inspeksi termasuk bentuk simetris :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(130, 480, 210, 23);

        jLabel62.setText("Kacamata :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(200, 510, 60, 23);

        Kacamata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "-", "+" }));
        Kacamata.setName("Kacamata"); // NOI18N
        Kacamata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KacamataKeyPressed(evt);
            }
        });
        FormInput.add(Kacamata);
        Kacamata.setBounds(270, 510, 60, 23);

        Visuskanan.setFocusTraversalPolicyProvider(true);
        Visuskanan.setName("Visuskanan"); // NOI18N
        Visuskanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VisuskananActionPerformed(evt);
            }
        });
        Visuskanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VisuskananKeyPressed(evt);
            }
        });
        FormInput.add(Visuskanan);
        Visuskanan.setBounds(160, 650, 300, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Visus SC");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(440, 650, 170, 23);

        Visuskiri.setFocusTraversalPolicyProvider(true);
        Visuskiri.setName("Visuskiri"); // NOI18N
        Visuskiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VisuskiriKeyPressed(evt);
            }
        });
        FormInput.add(Visuskiri);
        Visuskiri.setBounds(590, 650, 300, 23);

        jLabel64.setText("6. THT :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(60, 690, 70, 23);

        jLabel65.setText("a. Telinga :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(140, 690, 60, 23);

        Telinga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Telinga.setName("Telinga"); // NOI18N
        Telinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TelingaKeyPressed(evt);
            }
        });
        FormInput.add(Telinga);
        Telinga.setBounds(210, 690, 128, 23);

        jLabel66.setText("b. Hidung :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(340, 690, 70, 23);

        Hidung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Hidung.setName("Hidung"); // NOI18N
        Hidung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidungKeyPressed(evt);
            }
        });
        FormInput.add(Hidung);
        Hidung.setBounds(420, 690, 128, 23);

        jLabel67.setText("c. Tenggorokan :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(550, 690, 100, 23);

        Tenggorokan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Tenggorokan.setName("Tenggorokan"); // NOI18N
        Tenggorokan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TenggorokanKeyPressed(evt);
            }
        });
        FormInput.add(Tenggorokan);
        Tenggorokan.setBounds(660, 690, 128, 23);

        jLabel68.setText("7. Kelenjar dan pembulu getah bening :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(85, 720, 200, 23);

        jLabel69.setText("a. Inspeksi :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(290, 720, 60, 23);

        Inspeksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tampak Membesar" }));
        Inspeksi.setName("Inspeksi"); // NOI18N
        Inspeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InspeksiKeyPressed(evt);
            }
        });
        FormInput.add(Inspeksi);
        Inspeksi.setBounds(360, 720, 128, 23);

        jLabel70.setText("b. Palpasi :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(490, 720, 70, 23);

        Palpasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Membesar" }));
        Palpasi.setName("Palpasi"); // NOI18N
        Palpasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PalpasiKeyPressed(evt);
            }
        });
        FormInput.add(Palpasi);
        Palpasi.setBounds(570, 720, 128, 23);

        jLabel71.setText("8. Dada :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(87, 750, 50, 23);

        jLabel72.setText("a. Umum :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(140, 750, 60, 23);

        jLabel73.setText("Inspeksi :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(210, 750, 50, 23);

        Inspeksi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Simetris", "Tidak Simetris" }));
        Inspeksi1.setName("Inspeksi1"); // NOI18N
        Inspeksi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inspeksi1KeyPressed(evt);
            }
        });
        FormInput.add(Inspeksi1);
        Inspeksi1.setBounds(270, 750, 128, 23);

        jLabel74.setText("Palpasi :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(410, 750, 50, 23);

        Palpasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Palpasi1.setName("Palpasi1"); // NOI18N
        Palpasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Palpasi1KeyPressed(evt);
            }
        });
        FormInput.add(Palpasi1);
        Palpasi1.setBounds(470, 750, 128, 23);

        jLabel75.setText("Perkusi :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(610, 750, 50, 23);

        Perkusi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Perkusi1.setName("Perkusi1"); // NOI18N
        Perkusi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Perkusi1KeyPressed(evt);
            }
        });
        FormInput.add(Perkusi1);
        Perkusi1.setBounds(670, 750, 128, 23);

        jLabel76.setText("Auskultasi :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(810, 750, 70, 23);

        Auskultasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Auskultasi1.setName("Auskultasi1"); // NOI18N
        Auskultasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Auskultasi1KeyPressed(evt);
            }
        });
        FormInput.add(Auskultasi1);
        Auskultasi1.setBounds(890, 750, 128, 23);

        jLabel77.setText("b. Jantung :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(140, 780, 60, 23);

        jLabel78.setText("Inspeksi :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(210, 780, 50, 23);

        Inspeksi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Inspeksi2.setName("Inspeksi2"); // NOI18N
        Inspeksi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inspeksi2KeyPressed(evt);
            }
        });
        FormInput.add(Inspeksi2);
        Inspeksi2.setBounds(270, 780, 128, 23);

        jLabel79.setText("Palpasi :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(410, 780, 50, 23);

        Palpasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Palpasi2.setName("Palpasi2"); // NOI18N
        Palpasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Palpasi2KeyPressed(evt);
            }
        });
        FormInput.add(Palpasi2);
        Palpasi2.setBounds(470, 780, 128, 23);

        jLabel80.setText("Perkusi :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(610, 780, 50, 23);

        Perkusi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Perkusi2.setName("Perkusi2"); // NOI18N
        Perkusi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Perkusi2KeyPressed(evt);
            }
        });
        FormInput.add(Perkusi2);
        Perkusi2.setBounds(670, 780, 128, 23);

        jLabel81.setText("Auskultasi :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(810, 780, 70, 23);

        Auskultasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Auskultasi2.setName("Auskultasi2"); // NOI18N
        Auskultasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Auskultasi2KeyPressed(evt);
            }
        });
        FormInput.add(Auskultasi2);
        Auskultasi2.setBounds(890, 780, 128, 23);

        jLabel82.setText("c. Paru :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(140, 810, 60, 23);

        jLabel83.setText("Inspeksi :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(210, 810, 50, 23);

        Inspeksi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Inspeksi3.setName("Inspeksi3"); // NOI18N
        Inspeksi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inspeksi3KeyPressed(evt);
            }
        });
        FormInput.add(Inspeksi3);
        Inspeksi3.setBounds(270, 810, 128, 23);

        jLabel84.setText("Palpasi :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(410, 810, 50, 23);

        Palpasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Palpasi3.setName("Palpasi3"); // NOI18N
        Palpasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Palpasi3KeyPressed(evt);
            }
        });
        FormInput.add(Palpasi3);
        Palpasi3.setBounds(470, 810, 128, 23);

        jLabel85.setText("Perkusi :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(610, 810, 50, 23);

        Perkusi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Perkusi3.setName("Perkusi3"); // NOI18N
        Perkusi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Perkusi3KeyPressed(evt);
            }
        });
        FormInput.add(Perkusi3);
        Perkusi3.setBounds(670, 810, 128, 23);

        jLabel86.setText("Auskultasi :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(810, 810, 70, 23);

        Auskultasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Auskultasi3.setName("Auskultasi3"); // NOI18N
        Auskultasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Auskultasi3KeyPressed(evt);
            }
        });
        FormInput.add(Auskultasi3);
        Auskultasi3.setBounds(890, 810, 128, 23);

        jLabel87.setText("9. Perut (Semua organ dalam perut) :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(65, 840, 210, 23);

        jLabel88.setText("a. Umum :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(140, 870, 60, 23);

        jLabel89.setText("Inspeksi :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(210, 870, 50, 23);

        Inspeksi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Simetris", "Tidak Simetris" }));
        Inspeksi4.setName("Inspeksi4"); // NOI18N
        Inspeksi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inspeksi4KeyPressed(evt);
            }
        });
        FormInput.add(Inspeksi4);
        Inspeksi4.setBounds(270, 870, 128, 23);

        jLabel90.setText("Palpasi :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(410, 870, 50, 23);

        Palpasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Palpasi4.setName("Palpasi4"); // NOI18N
        Palpasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Palpasi4KeyPressed(evt);
            }
        });
        FormInput.add(Palpasi4);
        Palpasi4.setBounds(470, 870, 128, 23);

        jLabel91.setText("Perkusi :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(610, 870, 50, 23);

        Perkusi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Perkusi4.setName("Perkusi4"); // NOI18N
        Perkusi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Perkusi4KeyPressed(evt);
            }
        });
        FormInput.add(Perkusi4);
        Perkusi4.setBounds(670, 870, 128, 23);

        jLabel92.setText("Auskultasi :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(810, 870, 70, 23);

        Auskultasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Auskultasi4.setName("Auskultasi4"); // NOI18N
        Auskultasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Auskultasi4KeyPressed(evt);
            }
        });
        FormInput.add(Auskultasi4);
        Auskultasi4.setBounds(890, 870, 128, 23);

        jLabel93.setText("b. Sistem/Khusus :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(100, 900, 100, 23);

        jLabel94.setText("Hati (Liver) :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(200, 900, 70, 23);

        Hati.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teraba", "Tidak Teraba" }));
        Hati.setName("Hati"); // NOI18N
        Hati.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HatiKeyPressed(evt);
            }
        });
        FormInput.add(Hati);
        Hati.setBounds(280, 900, 128, 23);

        jLabel95.setText("Limpa (Spleen) :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(420, 900, 90, 23);

        Limpa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teraba", "Tidak Teraba" }));
        Limpa.setName("Limpa"); // NOI18N
        Limpa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LimpaKeyPressed(evt);
            }
        });
        FormInput.add(Limpa);
        Limpa.setBounds(520, 900, 128, 23);

        jLabel96.setText("Gastro-Intestina :");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(670, 900, 90, 23);

        Gastro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Massa Negative", "Massa Positive" }));
        Gastro.setName("Gastro"); // NOI18N
        Gastro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastroKeyPressed(evt);
            }
        });
        FormInput.add(Gastro);
        Gastro.setBounds(770, 900, 128, 23);

        jLabel97.setText("Ginjal :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(910, 900, 40, 23);

        Ginjal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teraba", "Tidak Teraba" }));
        Ginjal.setName("Ginjal"); // NOI18N
        Ginjal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GinjalKeyPressed(evt);
            }
        });
        FormInput.add(Ginjal);
        Ginjal.setBounds(960, 900, 128, 23);

        jLabel98.setText("10. Eksternitas :");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(80, 940, 90, 23);

        jLabel99.setText("a. Inspeksi :");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(180, 940, 60, 23);

        Inspeksi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Odem (-)", "Odem (+)" }));
        Inspeksi5.setName("Inspeksi5"); // NOI18N
        Inspeksi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inspeksi5KeyPressed(evt);
            }
        });
        FormInput.add(Inspeksi5);
        Inspeksi5.setBounds(250, 940, 80, 23);

        jLabel100.setText("b. Kekuatan Otot :");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(330, 940, 100, 23);

        Otot.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Otot.setName("Otot"); // NOI18N
        Otot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OtotKeyPressed(evt);
            }
        });
        FormInput.add(Otot);
        Otot.setBounds(440, 940, 128, 23);

        jLabel101.setText("c. Refleks :");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(570, 940, 70, 23);

        Refleks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal" }));
        Refleks.setName("Refleks"); // NOI18N
        Refleks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RefleksKeyPressed(evt);
            }
        });
        FormInput.add(Refleks);
        Refleks.setBounds(650, 940, 128, 23);

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
        }else if(Keluhan.getText().equals("")){
            Valid.textKosong(Keluhan,"Keluhan");
        }else{
            if(Sequel.menyimpantf("surat_mcu_haji","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",59,new String[]{
                    TNoRw.getText(),KodeDokter.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),Keluhan.getText(),TAlergi.getText(),TRpd.getText(),TRpk.getText(),Kesadaran.getText(),
                    TTensi.getText(),TNadi.getText(),Isi.getSelectedItem().toString(),Tegangan.getSelectedItem().toString(),Ritme.getSelectedItem().toString(),Nafas.getText(),Ritme1.getSelectedItem().toString(),
                    TSuhu.getText(),BB.getText(),TB.getText(),BMI.getText(),Kulit.getSelectedItem().toString(),KepalaA.getSelectedItem().toString(),KepalaB.getSelectedItem().toString(),Kacamata.getSelectedItem().toString(),
                    Visuskanan.getText(),Visuskiri.getText(),Telinga.getSelectedItem().toString(),Hidung.getSelectedItem().toString(),Tenggorokan.getSelectedItem().toString(),Inspeksi.getSelectedItem().toString(),
                    Palpasi.getSelectedItem().toString(),Inspeksi1.getSelectedItem().toString(),Palpasi1.getSelectedItem().toString(),Perkusi1.getSelectedItem().toString(),Auskultasi1.getSelectedItem().toString(),
                    Inspeksi2.getSelectedItem().toString(),Palpasi2.getSelectedItem().toString(),Perkusi2.getSelectedItem().toString(),Auskultasi2.getSelectedItem().toString(),
                    Inspeksi3.getSelectedItem().toString(),Palpasi3.getSelectedItem().toString(),Perkusi3.getSelectedItem().toString(),Auskultasi3.getSelectedItem().toString(),
                    Inspeksi4.getSelectedItem().toString(),Palpasi4.getSelectedItem().toString(),Perkusi4.getSelectedItem().toString(),Auskultasi4.getSelectedItem().toString(),
                    Hati.getSelectedItem().toString(),Limpa.getSelectedItem().toString(),Gastro.getSelectedItem().toString(),Ginjal.getSelectedItem().toString(),Inspeksi5.getSelectedItem().toString(),
                    Otot.getSelectedItem().toString(),Refleks.getSelectedItem().toString(),EKG.getText(),PemeriksaanPenunjang.getText(),HasilLaborat.getText(),Kesimpulan.getText(),Anjuran.getText()
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
            Valid.pindah(evt,Anjuran,BtnBatal);
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
            if(Sequel.queryu2tf("delete from surat_mcu_haji where no_rawat=?",1,new String[]{
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
        }else if(Keluhan.getText().equals("")){
            Valid.textKosong(Keluhan,"Keluhan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("surat_mcu_haji","no_rawat=?","no_rawat=?,kd_dokter=?,tanggal=?,jam=?,keluhan_utama=?,alergi=?,rpd=?,rpk=?,kesadaran=?,tensi=?,nadi=?,isi=?,tegangan=?,ritme=?,nafas=?,ritme1=?,suhu=?,berat=?,tinggi=?,bmi=?,kulit=?,kepala_a=?,kepala_b=?,kacamata=?,visus_kanan=?,visus_kiri=?,telinga=?,hidung=?,tenggorokan=?,inspeksi=?,palpasi=?,inspeksi1=?,palpasi1=?,perkusi1=?,auskultasi1=?,inspeksi2=?,palpasi2=?,perkusi2=?,auskultasi2=?,inspeksi3=?,palpasi3=?,perkusi3=?,auskultasi3=?,inspeksi4=?,palpasi4=?,perkusi4=?,auskultasi4=?,hati=?,limpa=?,gastro=?,ginjal=?,inspeksi5=?,otot=?,refleks=?,ekg=?,radiologi=?,lab=?,kesimpulan=?,anjuran=?",60,new String[]{
                    TNoRw.getText(),KodeDokter.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),Keluhan.getText(),TAlergi.getText(),TRpd.getText(),TRpk.getText(),Kesadaran.getText(),
                    TTensi.getText(),TNadi.getText(),Isi.getSelectedItem().toString(),Tegangan.getSelectedItem().toString(),Ritme.getSelectedItem().toString(),Nafas.getText(),Ritme1.getSelectedItem().toString(),
                    TSuhu.getText(),BB.getText(),TB.getText(),BMI.getText(),Kulit.getSelectedItem().toString(),KepalaA.getSelectedItem().toString(),KepalaB.getSelectedItem().toString(),Kacamata.getSelectedItem().toString(),
                    Visuskanan.getText(),Visuskiri.getText(),Telinga.getSelectedItem().toString(),Hidung.getSelectedItem().toString(),Tenggorokan.getSelectedItem().toString(),Inspeksi.getSelectedItem().toString(),
                    Palpasi.getSelectedItem().toString(),Inspeksi1.getSelectedItem().toString(),Palpasi1.getSelectedItem().toString(),Perkusi1.getSelectedItem().toString(),Auskultasi1.getSelectedItem().toString(),
                    Inspeksi2.getSelectedItem().toString(),Palpasi2.getSelectedItem().toString(),Perkusi2.getSelectedItem().toString(),Auskultasi2.getSelectedItem().toString(),
                    Inspeksi3.getSelectedItem().toString(),Palpasi3.getSelectedItem().toString(),Perkusi3.getSelectedItem().toString(),Auskultasi3.getSelectedItem().toString(),
                    Inspeksi4.getSelectedItem().toString(),Palpasi4.getSelectedItem().toString(),Perkusi4.getSelectedItem().toString(),Auskultasi4.getSelectedItem().toString(),
                    Hati.getSelectedItem().toString(),Limpa.getSelectedItem().toString(),Gastro.getSelectedItem().toString(),Ginjal.getSelectedItem().toString(),Inspeksi5.getSelectedItem().toString(),
                    Otot.getSelectedItem().toString(),Refleks.getSelectedItem().toString(),EKG.getText(),PemeriksaanPenunjang.getText(),HasilLaborat.getText(),Kesimpulan.getText(),Anjuran.getText(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()
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
                        "surat_mcu_haji.kd_dokter,dokter.nm_dokter,surat_mcu_haji.kondisi_pulang,surat_mcu_haji.keluhan_utama,surat_mcu_haji.jalannya_penyakit, "+
                        "surat_mcu_haji.pemeriksaan_penunjang,surat_mcu_haji.hasil_laborat,surat_mcu_haji.diagnosa_utama,surat_mcu_haji.kd_diagnosa_utama, "+
                        "surat_mcu_haji.diagnosa_sekunder,surat_mcu_haji.kd_diagnosa_sekunder,surat_mcu_haji.diagnosa_sekunder2,surat_mcu_haji.kd_diagnosa_sekunder2, "+
                        "surat_mcu_haji.diagnosa_sekunder3,surat_mcu_haji.kd_diagnosa_sekunder3,surat_mcu_haji.diagnosa_sekunder4,surat_mcu_haji.kd_diagnosa_sekunder4, "+
                        "surat_mcu_haji.prosedur_utama,surat_mcu_haji.kd_prosedur_utama,surat_mcu_haji.prosedur_sekunder,surat_mcu_haji.kd_prosedur_sekunder, "+
                        "surat_mcu_haji.prosedur_sekunder2,surat_mcu_haji.kd_prosedur_sekunder2,surat_mcu_haji.prosedur_sekunder3,surat_mcu_haji.kd_prosedur_sekunder3, "+
                        "surat_mcu_haji.obat_pulang,surat_mcu_haji.tindak_lanjut,surat_mcu_haji.asal_pasien,surat_mcu_haji.tindakan from surat_mcu_haji inner join reg_periksa on surat_mcu_haji.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_mcu_haji.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
                }else{
                    Valid.MyReportqry("rptDataResumePasien.jasper","report","::[ Data Resume Pasien ]::",
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                        "surat_mcu_haji.kd_dokter,dokter.nm_dokter,surat_mcu_haji.kondisi_pulang,surat_mcu_haji.keluhan_utama,surat_mcu_haji.jalannya_penyakit, "+
                        "surat_mcu_haji.pemeriksaan_penunjang,surat_mcu_haji.hasil_laborat,surat_mcu_haji.diagnosa_utama,surat_mcu_haji.kd_diagnosa_utama, "+
                        "surat_mcu_haji.diagnosa_sekunder,surat_mcu_haji.kd_diagnosa_sekunder,surat_mcu_haji.diagnosa_sekunder2,surat_mcu_haji.kd_diagnosa_sekunder2, "+
                        "surat_mcu_haji.diagnosa_sekunder3,surat_mcu_haji.kd_diagnosa_sekunder3,surat_mcu_haji.diagnosa_sekunder4,surat_mcu_haji.kd_diagnosa_sekunder4, "+
                        "surat_mcu_haji.prosedur_utama,surat_mcu_haji.kd_prosedur_utama,surat_mcu_haji.prosedur_sekunder,surat_mcu_haji.kd_prosedur_sekunder, "+
                        "surat_mcu_haji.prosedur_sekunder2,surat_mcu_haji.kd_prosedur_sekunder2,surat_mcu_haji.prosedur_sekunder3,surat_mcu_haji.kd_prosedur_sekunder3, "+
                        "surat_mcu_haji.obat_pulang,surat_mcu_haji.tindak_lanjut,surat_mcu_haji.asal_pasien,surat_mcu_haji.tindakan from surat_mcu_haji inner join reg_periksa on surat_mcu_haji.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_mcu_haji.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.status_lanjut like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_mcu_haji.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_mcu_haji.kondisi_pulang like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_mcu_haji.kd_diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_mcu_haji.diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_mcu_haji.prosedur_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and surat_mcu_haji.kd_prosedur_utama like '%"+TCari.getText().trim()+"%' "+
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
        Valid.pindah(evt,TCari,Anjuran);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
       Valid.pindah(evt,TCari,Anjuran);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void KeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                EKG.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TNoRw.requestFocus();
        }
    }//GEN-LAST:event_KeluhanKeyPressed

    private void EKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EKGKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                PemeriksaanPenunjang.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Keluhan.requestFocus();
        }
    }//GEN-LAST:event_EKGKeyPressed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                HasilLaborat.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            EKG.requestFocus();
        }
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void HasilLaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilLaboratKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                TNoRw.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            PemeriksaanPenunjang.requestFocus();
        }
    }//GEN-LAST:event_HasilLaboratKeyPressed

    private void AnjuranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnjuranKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                BtnSimpan.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TNoRw.requestFocus();
        }
    }//GEN-LAST:event_AnjuranKeyPressed

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
            Valid.MyReport("rptSuratMcuHaji.jasper","report","::[ Cetak MCU Haji ]::",param);
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
            rmcariradralan.setNoRawat(TNoRw.getText());
            rmcariradralan.tampil();
            rmcariradralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariradralan.setLocationRelativeTo(internalFrame1);
            rmcariradralan.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            rmcarilabralan.setNoRawat(TNoRw.getText());
            rmcarilabralan.tampil();
            rmcarilabralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcarilabralan.setLocationRelativeTo(internalFrame1);
            rmcarilabralan.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void MnInputDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputDiagnosaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            penyakit.setLocationRelativeTo(internalFrame1);
            penyakit.isCek();
            penyakit.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),Sequel.cariIsi("select status_lanjut from reg_periksa where no_rawat=?",TNoRw.getText()));
            penyakit.panelDiagnosa1.tampil();
            penyakit.setVisible(true);
        }
    }//GEN-LAST:event_MnInputDiagnosaActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(!tbObat.getValueAt(tbObat.getSelectedRow(),1).toString().equals("")){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
                    berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
                    try {
                        berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : "+ex);
                    }

                    berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    berkas.setLocationRelativeTo(internalFrame1);
                    berkas.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void IsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IsiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsiActionPerformed

    private void IsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IsiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsiKeyPressed

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KesimpulanKeyPressed

    private void MnDigitalTTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDigitalTTEActionPerformed
        if(tbObat.getSelectedRow()>-1){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        FileName="surat_mcu_haji-"+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString().replaceAll("/","")+".pdf";
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

    private void TAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlergiKeyPressed
        Valid.pindah(evt,TRpd,TRpk);
    }//GEN-LAST:event_TAlergiKeyPressed

    private void TRpdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRpdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpdKeyPressed

    private void TRpkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TRpkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpkActionPerformed

    private void TRpkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRpkKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpkKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KesadaranKeyPressed

    private void TTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyPressed
        Valid.pindah(evt,TSuhu,TNadi);
    }//GEN-LAST:event_TTensiKeyPressed

    private void TTensiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyTyped
        char kata= evt.getKeyChar();
        if(!((kata>='0') && (kata<='9') || (kata==KeyEvent.VK_SLASH)  || (kata== KeyEvent.VK_BACK_SPACE))){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Hanya diperbolehkan menginputkan Angka dan Garis Miring");
        }
    }//GEN-LAST:event_TTensiKeyTyped

    private void TNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNadiKeyPressed
        Valid.pindah(evt,TNadi,Nafas);
    }//GEN-LAST:event_TNadiKeyPressed

    private void TeganganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TeganganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeganganActionPerformed

    private void TeganganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeganganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeganganKeyPressed

    private void RitmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RitmeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RitmeActionPerformed

    private void RitmeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RitmeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RitmeKeyPressed

    private void NafasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NafasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NafasKeyPressed

    private void Ritme1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ritme1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Ritme1ActionPerformed

    private void Ritme1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Ritme1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Ritme1KeyPressed

    private void TSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyPressed
        Valid.pindah(evt,TNadi,TTensi);
    }//GEN-LAST:event_TSuhuKeyPressed

    private void TSuhuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyTyped
        char kata= evt.getKeyChar();
        if(!((kata>='0') && (kata<='9') || (kata== KeyEvent.VK_PERIOD) || (kata== KeyEvent.VK_BACK_SPACE))){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Hanya diperbolehkan menginputkan Angka dan Titik");
        }
    }//GEN-LAST:event_TSuhuKeyTyped

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,TNadi,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,BMI);
    }//GEN-LAST:event_TBKeyPressed

    private void BMIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BMIKeyPressed
        Valid.pindah(evt,TB,TNadi);
    }//GEN-LAST:event_BMIKeyPressed

    private void KepalaAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KepalaAKeyPressed

    private void KulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitKeyPressed

    private void KepalaBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KepalaBKeyPressed

    private void KacamataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KacamataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KacamataKeyPressed

    private void VisuskananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VisuskananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VisuskananActionPerformed

    private void VisuskananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VisuskananKeyPressed
        Valid.pindah(evt,Visuskanan,Visuskiri);
    }//GEN-LAST:event_VisuskananKeyPressed

    private void VisuskiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VisuskiriKeyPressed
        Valid.pindah(evt,Visuskanan,Visuskiri);
    }//GEN-LAST:event_VisuskiriKeyPressed

    private void TelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TelingaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TelingaKeyPressed

    private void HidungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HidungKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HidungKeyPressed

    private void TenggorokanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TenggorokanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TenggorokanKeyPressed

    private void InspeksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InspeksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InspeksiKeyPressed

    private void PalpasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PalpasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PalpasiKeyPressed

    private void Inspeksi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inspeksi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Inspeksi1KeyPressed

    private void Palpasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Palpasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Palpasi1KeyPressed

    private void Perkusi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Perkusi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Perkusi1KeyPressed

    private void Auskultasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Auskultasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Auskultasi1KeyPressed

    private void Inspeksi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inspeksi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Inspeksi2KeyPressed

    private void Palpasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Palpasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Palpasi2KeyPressed

    private void Perkusi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Perkusi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Perkusi2KeyPressed

    private void Auskultasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Auskultasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Auskultasi2KeyPressed

    private void Inspeksi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inspeksi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Inspeksi3KeyPressed

    private void Palpasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Palpasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Palpasi3KeyPressed

    private void Perkusi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Perkusi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Perkusi3KeyPressed

    private void Auskultasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Auskultasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Auskultasi3KeyPressed

    private void Inspeksi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inspeksi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Inspeksi4KeyPressed

    private void Palpasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Palpasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Palpasi4KeyPressed

    private void Perkusi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Perkusi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Perkusi4KeyPressed

    private void Auskultasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Auskultasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Auskultasi4KeyPressed

    private void HatiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HatiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HatiKeyPressed

    private void LimpaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LimpaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LimpaKeyPressed

    private void GastroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GastroKeyPressed

    private void GinjalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GinjalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GinjalKeyPressed

    private void Inspeksi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Inspeksi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Inspeksi5KeyPressed

    private void OtotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OtotKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OtotKeyPressed

    private void RefleksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RefleksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RefleksKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratMCUHaji dialog = new SuratMCUHaji(new javax.swing.JFrame(), true);
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
    private widget.TextArea Anjuran;
    private widget.ComboBox Auskultasi1;
    private widget.ComboBox Auskultasi2;
    private widget.ComboBox Auskultasi3;
    private widget.ComboBox Auskultasi4;
    private widget.TextBox BB;
    private widget.TextBox BMI;
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
    private widget.TextArea EKG;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Gastro;
    private widget.ComboBox Ginjal;
    private widget.TextArea HasilLaborat;
    private widget.ComboBox Hati;
    private widget.ComboBox Hidung;
    private widget.ComboBox Inspeksi;
    private widget.ComboBox Inspeksi1;
    private widget.ComboBox Inspeksi2;
    private widget.ComboBox Inspeksi3;
    private widget.ComboBox Inspeksi4;
    private widget.ComboBox Inspeksi5;
    private widget.ComboBox Isi;
    private widget.ComboBox Kacamata;
    private widget.TextArea Keluhan;
    private widget.ComboBox KepalaA;
    private widget.ComboBox KepalaB;
    private widget.TextBox Kesadaran;
    private widget.TextArea Kesimpulan;
    private widget.TextBox KodeDokter;
    private widget.ComboBox Kulit;
    private widget.Label LCount;
    private widget.ComboBox Limpa;
    private javax.swing.JMenuItem MnDigitalTTE;
    private javax.swing.JMenuItem MnInputDiagnosa;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox Nafas;
    private widget.TextBox NamaDokter;
    private widget.ComboBox Otot;
    private widget.ComboBox Palpasi;
    private widget.ComboBox Palpasi1;
    private widget.ComboBox Palpasi2;
    private widget.ComboBox Palpasi3;
    private widget.ComboBox Palpasi4;
    private javax.swing.JPanel PanelInput;
    private usu.widget.glass.PanelGlass PanelWall;
    private usu.widget.glass.PanelGlass PanelWall1;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.ComboBox Perkusi1;
    private widget.ComboBox Perkusi2;
    private widget.ComboBox Perkusi3;
    private widget.ComboBox Perkusi4;
    private widget.ComboBox Refleks;
    private widget.ComboBox Ritme;
    private widget.ComboBox Ritme1;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlergi;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TNadi;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TRpd;
    private widget.TextBox TRpk;
    private widget.TextBox TSuhu;
    private widget.TextBox TTensi;
    private widget.ComboBox Tegangan;
    private widget.ComboBox Telinga;
    private widget.ComboBox Tenggorokan;
    private widget.TextBox Visuskanan;
    private widget.TextBox Visuskiri;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
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
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
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
    private widget.Label jLabel9;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator35;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBerkasDigital;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "surat_mcu_haji.kd_dokter,dokter.nm_dokter,surat_mcu_haji.tanggal,surat_mcu_haji.jam,surat_mcu_haji.keluhan_utama,surat_mcu_haji.alergi,surat_mcu_haji.rpd, "+
                    "surat_mcu_haji.rpk,surat_mcu_haji.kesadaran,surat_mcu_haji.tensi,surat_mcu_haji.nadi,surat_mcu_haji.isi,surat_mcu_haji.tegangan,surat_mcu_haji.ritme, "+
                    "surat_mcu_haji.nafas,surat_mcu_haji.ritme1,surat_mcu_haji.suhu,surat_mcu_haji.berat,surat_mcu_haji.tinggi,surat_mcu_haji.bmi,surat_mcu_haji.kulit, "+
                    "surat_mcu_haji.kepala_a,surat_mcu_haji.kepala_b,surat_mcu_haji.kacamata,surat_mcu_haji.visus_kanan,surat_mcu_haji.visus_kiri,surat_mcu_haji.telinga, "+
                    "surat_mcu_haji.hidung,surat_mcu_haji.tenggorokan,surat_mcu_haji.inspeksi,surat_mcu_haji.palpasi,surat_mcu_haji.inspeksi1,surat_mcu_haji.palpasi1, "+
                    "surat_mcu_haji.perkusi1,surat_mcu_haji.auskultasi1,surat_mcu_haji.inspeksi2,surat_mcu_haji.palpasi2,surat_mcu_haji.perkusi2,surat_mcu_haji.auskultasi2, "+
                    "surat_mcu_haji.inspeksi3,surat_mcu_haji.palpasi3,surat_mcu_haji.perkusi3,surat_mcu_haji.auskultasi3,surat_mcu_haji.inspeksi4,surat_mcu_haji.palpasi4, "+
                    "surat_mcu_haji.perkusi4,surat_mcu_haji.auskultasi4,surat_mcu_haji.hati,surat_mcu_haji.limpa,surat_mcu_haji.gastro,surat_mcu_haji.ginjal,surat_mcu_haji.inspeksi5, "+
                    "surat_mcu_haji.otot,surat_mcu_haji.refleks,surat_mcu_haji.ekg,surat_mcu_haji.radiologi,surat_mcu_haji.lab,surat_mcu_haji.kesimpulan,surat_mcu_haji.anjuran "+        
                    "from surat_mcu_haji inner join reg_periksa on surat_mcu_haji.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_mcu_haji.kd_dokter=dokter.kd_dokter "+
                    "where reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "surat_mcu_haji.kd_dokter,dokter.nm_dokter,surat_mcu_haji.tanggal,surat_mcu_haji.jam,surat_mcu_haji.keluhan_utama,surat_mcu_haji.alergi,surat_mcu_haji.rpd, "+
                    "surat_mcu_haji.rpk,surat_mcu_haji.kesadaran,surat_mcu_haji.tensi,surat_mcu_haji.nadi,surat_mcu_haji.isi,surat_mcu_haji.tegangan,surat_mcu_haji.ritme, "+
                    "surat_mcu_haji.nafas,surat_mcu_haji.ritme1,surat_mcu_haji.suhu,surat_mcu_haji.berat,surat_mcu_haji.tinggi,surat_mcu_haji.bmi,surat_mcu_haji.kulit, "+
                    "surat_mcu_haji.kepala_a,surat_mcu_haji.kepala_b,surat_mcu_haji.kacamata,surat_mcu_haji.visus_kanan,surat_mcu_haji.visus_kiri,surat_mcu_haji.telinga, "+
                    "surat_mcu_haji.hidung,surat_mcu_haji.tenggorokan,surat_mcu_haji.inspeksi,surat_mcu_haji.palpasi,surat_mcu_haji.inspeksi1,surat_mcu_haji.palpasi1, "+
                    "surat_mcu_haji.perkusi1,surat_mcu_haji.auskultasi1,surat_mcu_haji.inspeksi2,surat_mcu_haji.palpasi2,surat_mcu_haji.perkusi2,surat_mcu_haji.auskultasi2, "+
                    "surat_mcu_haji.inspeksi3,surat_mcu_haji.palpasi3,surat_mcu_haji.perkusi3,surat_mcu_haji.auskultasi3,surat_mcu_haji.inspeksi4,surat_mcu_haji.palpasi4, "+
                    "surat_mcu_haji.perkusi4,surat_mcu_haji.auskultasi4,surat_mcu_haji.hati,surat_mcu_haji.limpa,surat_mcu_haji.gastro,surat_mcu_haji.ginjal,surat_mcu_haji.inspeksi5, "+
                    "surat_mcu_haji.otot,surat_mcu_haji.refleks,surat_mcu_haji.ekg,surat_mcu_haji.radiologi,surat_mcu_haji.lab,surat_mcu_haji.kesimpulan,surat_mcu_haji.anjuran "+        
                    "from surat_mcu_haji inner join reg_periksa on surat_mcu_haji.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on surat_mcu_haji.kd_dokter=dokter.kd_dokter "+
                    "where reg_periksa.tgl_registrasi between ? and ? and reg_periksa.status_lanjut like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and surat_mcu_haji.kd_dokter like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and dokter.nm_dokter like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and surat_mcu_haji.keluhan_utama like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and surat_mcu_haji.alergi like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and surat_mcu_haji.rpd like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and surat_mcu_haji.rpk like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rawat like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and surat_mcu_haji.kesadaran like ? "+
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
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("jam"),rs.getString("keluhan_utama"),rs.getString("alergi"),rs.getString("rpd"),rs.getString("rpk"),
                        rs.getString("kesadaran"),rs.getString("tensi"),rs.getString("nadi"),rs.getString("isi"),rs.getString("tegangan"),rs.getString("ritme"),rs.getString("nafas"),
                        rs.getString("ritme1"),rs.getString("suhu"),rs.getString("berat"),rs.getString("tinggi"),rs.getString("bmi"),rs.getString("kulit"),rs.getString("kepala_a"),
                        rs.getString("kepala_b"),rs.getString("kacamata"),rs.getString("visus_kanan"),rs.getString("visus_kiri"),rs.getString("telinga"),rs.getString("hidung"),
                        rs.getString("tenggorokan"),rs.getString("inspeksi"),rs.getString("palpasi"),rs.getString("inspeksi1"),rs.getString("palpasi1"),rs.getString("perkusi1"),rs.getString("auskultasi1"),
                        rs.getString("inspeksi2"),rs.getString("palpasi2"),rs.getString("perkusi2"),rs.getString("auskultasi2"),rs.getString("inspeksi3"),rs.getString("palpasi3"),rs.getString("perkusi3"),rs.getString("auskultasi3"),
                        rs.getString("inspeksi4"),rs.getString("palpasi4"),rs.getString("perkusi4"),rs.getString("auskultasi4"),rs.getString("hati"),rs.getString("limpa"),rs.getString("gastro"),rs.getString("ginjal"),
                        rs.getString("inspeksi5"),rs.getString("otot"),rs.getString("refleks"),rs.getString("ekg"),rs.getString("radiologi"),rs.getString("lab"),rs.getString("kesimpulan"),rs.getString("anjuran")
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
        Keluhan.setText("");
        TAlergi.setText("");
        TRpd.setText("");
        TRpk.setText("");
        Kesadaran.setText("");
        TTensi.setText("");
        TNadi.setText("");
        Isi.setSelectedIndex(0);
        Tegangan.setSelectedIndex(0);
        Ritme.setSelectedIndex(0);
        Nafas.setText("");
        Ritme1.setSelectedIndex(0);
        TSuhu.setText("");
        BB.setText("");
        TB.setText("");
        BMI.setText("");
        Kulit.setSelectedIndex(0);
        KepalaA.setSelectedIndex(0);
        KepalaB.setSelectedIndex(0);
        Kacamata.setSelectedIndex(0);
        Visuskanan.setText("");
        Visuskiri.setText("");
        Telinga.setSelectedIndex(0);
        Hidung.setSelectedIndex(0);
        Tenggorokan.setSelectedIndex(0);
        Inspeksi.setSelectedIndex(0);
        Palpasi.setSelectedIndex(0);
        Inspeksi1.setSelectedIndex(0);
        Palpasi1.setSelectedIndex(0);
        Perkusi1.setSelectedIndex(0);
        Auskultasi1.setSelectedIndex(0);
        Inspeksi2.setSelectedIndex(0);
        Palpasi2.setSelectedIndex(0);
        Perkusi2.setSelectedIndex(0);
        Auskultasi2.setSelectedIndex(0);
        Inspeksi3.setSelectedIndex(0);
        Palpasi3.setSelectedIndex(0);
        Perkusi3.setSelectedIndex(0);
        Auskultasi3.setSelectedIndex(0);
        Inspeksi4.setSelectedIndex(0);
        Palpasi4.setSelectedIndex(0);
        Perkusi4.setSelectedIndex(0);
        Auskultasi4.setSelectedIndex(0);
        Hati.setSelectedIndex(0);
        Limpa.setSelectedIndex(0);
        Gastro.setSelectedIndex(0);
        Ginjal.setSelectedIndex(0);
        Inspeksi5.setSelectedIndex(0);
        Otot.setSelectedIndex(0);
        EKG.setText("");
        PemeriksaanPenunjang.setText("");
        HasilLaborat.setText("");
        Kesimpulan.setText("");
        Anjuran.setText("");
        TNoRw.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());  
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());  
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());  
            KodeDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());  
            NamaDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Keluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            TAlergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            TRpd.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            TRpk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Kesadaran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            TTensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            TNadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Isi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Tegangan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Ritme.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Nafas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Ritme1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            TSuhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            BMI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Kulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            KepalaA.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            KepalaB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Kacamata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Visuskanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Visuskiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Telinga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Hidung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Tenggorokan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Inspeksi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Palpasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Inspeksi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Palpasi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Perkusi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Auskultasi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Inspeksi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Palpasi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Perkusi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Auskultasi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Inspeksi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Palpasi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Perkusi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Auskultasi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Inspeksi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Palpasi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Perkusi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Auskultasi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Hati.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Limpa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Gastro.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Ginjal.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            Inspeksi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            Otot.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Refleks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            EKG.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            PemeriksaanPenunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            HasilLaborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            Kesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            Anjuran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
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
        TAlergi.setText(Sequel.cariIsi("select alergi from pemeriksaan_ralan where no_rawat=?",norwt));
        TRpd.setText(Sequel.cariIsi("select rpd from pemeriksaan_ralan where no_rawat=?",norwt));
        TRpk.setText(Sequel.cariIsi("select rpk from pemeriksaan_ralan where no_rawat=?",norwt));
        Kesadaran.setText(Sequel.cariIsi("select kesadaran from pemeriksaan_ralan where no_rawat=?",norwt));
        TTensi.setText(Sequel.cariIsi("select tensi from pemeriksaan_ralan where no_rawat=?",norwt));
        TNadi.setText(Sequel.cariIsi("select nadi from pemeriksaan_ralan where no_rawat=?",norwt));
        Nafas.setText(Sequel.cariIsi("select respirasi from pemeriksaan_ralan where no_rawat=?",norwt));
        TSuhu.setText(Sequel.cariIsi("select suhu_tubuh from pemeriksaan_ralan where no_rawat=?",norwt));
        BB.setText(Sequel.cariIsi("select berat from pemeriksaan_ralan where no_rawat=?",norwt));
        TB.setText(Sequel.cariIsi("select tinggi from pemeriksaan_ralan where no_rawat=?",norwt));
        EKG.setText(Sequel.cariIsi("select kesimpulan from hasil_ekg_rajal where no_rawat=?",norwt));
//        Sequel.cariIsi("select suhu from penilaian_awal_keperawatan_ralan where no_rawat=?",TSuhu.getText());
        DTPCari2.setDate(tgl2);    
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
        TNoRw.requestFocus();
        try {
            ps=koneksi.prepareStatement(
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat=? order by diagnosa_pasien.prioritas ");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                while(rs.next()){
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
                    "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.prioritas "+
                    "from prosedur_pasien inner join icd9 on prosedur_pasien.kode=icd9.kode "+
                    "where prosedur_pasien.no_rawat=? order by prosedur_pasien.prioritas ");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                while(rs.next()){
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
    
    private void isBMI(){
        if((!TB.getText().equals(""))&&(!BB.getText().equals(""))){
            BMI.setText(Valid.SetAngka7(Valid.SetAngka(BB.getText())/((Valid.SetAngka(TB.getText())/100)*(Valid.SetAngka(TB.getText())/100)))+"");
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
           
            Valid.MyReportPDFWithName("rptSuratMcuHaji.jasper","report","tempfile",FileName,"::[ Cetak MCU Haji ]::",param);
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
        BtnSimpan.setEnabled(akses.getsurat_bebas_narkoba());
        BtnHapus.setEnabled(akses.getsurat_bebas_narkoba());
        BtnEdit.setEnabled(akses.getsurat_bebas_narkoba());
        BtnPrint.setEnabled(akses.getsurat_bebas_narkoba()); 
        MnInputDiagnosa.setEnabled(akses.getdiagnosa_pasien());   
        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());    
        if(akses.getjml2()>=1){
            KodeDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KodeDokter.setText(akses.getkode());
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NamaDokter,KodeDokter.getText());
            if(NamaDokter.getText().equals("")){
                KodeDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan dokter...!!");
            }
        }            
    }

    
}
