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


/**
 *
 * @author perpustakaan
 */
public final class RMDataLepasPerawatanRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private String namaPenyakit="",namaPenyakitt="",Listpenyakit="",Listpenyakitt=""; 
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
    public RMDataLepasPerawatanRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "Tgl.Rawat","Status","No.Rawat","No.RM","Nama Pasien","Kode Dokter PJ","Dokter Penanggung Jawab","Tgl.Lahir","J.K","Ruang/Kelas","Tanggal Masuk","Jam Masuk",
            "Tanggal Keluar","Jam Keluar","Kode Dokter Pengirim","Nama Dokter Pengirim","Kode Cara Bayar","Cara Bayar","Diagnosa Awal","Terapi","Diagnosa Utama","ICD10 Utama","Diagnosa Sekunder 1",
            "ICD10 Sek 1","Diagnosa Sekunder 2","ICD10 Sek 2","Diagnosa Sekunder 3","ICD10 Sek 3","Diagnosa Sekunder 4","ICD10 Sek 4","Diagnosa Sekunder 5","ICD10 Sek 5",
            "Diagnosa Sekunder 6","ICD10 Sek 6","Diagnosa Sekunder 7","ICD10 Sek 7","Diagnosa Klinis","Keadaan Pulang","Intruksi Jika Terjadi Keadaan Gawat Darurat",
            "Tanggal Kontrol","Tempat","Tanggal Instruksi Tambahan","Instruksi Tambahan","Instruksi Tambahan Lainnya"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 44; i++) {
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
                column.setPreferredWidth(40);
            }else if(i==34){
                column.setPreferredWidth(105);
            }else if(i==35){
                column.setPreferredWidth(65);
            }else if(i==36){
                column.setPreferredWidth(150);
            }else if(i==37){
                column.setPreferredWidth(90);
            }else if(i==38){
                column.setPreferredWidth(150);
            }else if(i==39){
                column.setPreferredWidth(60);
            }else if(i==40){
                column.setPreferredWidth(250);
            }else if(i==41){
                column.setPreferredWidth(250);
            }else if(i==42){
                column.setPreferredWidth(250);
            }else if(i==43){
                column.setPreferredWidth(250);
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
        DiagnosaUtama.setDocument(new batasInput((int)200).getKata(DiagnosaUtama));
        DiagnosaSekunder1.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder1));
        DiagnosaSekunder2.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder2));
        DiagnosaSekunder3.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder3));
        DiagnosaSekunder4.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder4));
        DiagnosaSekunder5.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder5));
        DiagnosaSekunder6.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder6));
        DiagnosaSekunder7.setDocument(new batasInput((int)200).getKata(DiagnosaSekunder7));
        KodeDiagnosaUtama.setDocument(new batasInput((int)10).getKata(KodeDiagnosaUtama));
        KodeDiagnosaSekunder1.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder1));
        KodeDiagnosaSekunder2.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder2));
        KodeDiagnosaSekunder3.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder3));
        KodeDiagnosaSekunder4.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder4));
        KodeDiagnosaSekunder5.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder5));
        KodeDiagnosaSekunder6.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder6));
        KodeDiagnosaSekunder7.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder7));
        TambahanLainnya.setDocument(new batasInput((int)100).getKata(TambahanLainnya));
        
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
        
        rmcaridiagnosa1.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcaridiagnosa1.getTable().getSelectedRow()!= -1){
                    KodeDiagnosaUtama.setText(rmcaridiagnosa1.getTable().getValueAt(rmcaridiagnosa1.getTable().getSelectedRow(),0).toString());
                    DiagnosaUtama.setText(rmcaridiagnosa1.getTable().getValueAt(rmcaridiagnosa1.getTable().getSelectedRow(),1).toString());
                    KodeDiagnosaUtama.requestFocus();
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
        
        rmcaridiagnosa2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcaridiagnosa2.getTable().getSelectedRow()!= -1){
                    KodeDiagnosaSekunder1.setText(rmcaridiagnosa2.getTable().getValueAt(rmcaridiagnosa2.getTable().getSelectedRow(),0).toString());
                    DiagnosaSekunder1.setText(rmcaridiagnosa2.getTable().getValueAt(rmcaridiagnosa2.getTable().getSelectedRow(),1).toString());
                    KodeDiagnosaSekunder1.requestFocus();
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
        
        rmcaridiagnosa3.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcaridiagnosa3.getTable().getSelectedRow()!= -1){
                    KodeDiagnosaSekunder2.setText(rmcaridiagnosa3.getTable().getValueAt(rmcaridiagnosa3.getTable().getSelectedRow(),0).toString());
                    DiagnosaSekunder2.setText(rmcaridiagnosa3.getTable().getValueAt(rmcaridiagnosa3.getTable().getSelectedRow(),1).toString());
                    KodeDiagnosaSekunder2.requestFocus();
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
        
        rmcaridiagnosa4.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcaridiagnosa4.getTable().getSelectedRow()!= -1){
                    KodeDiagnosaSekunder3.setText(rmcaridiagnosa4.getTable().getValueAt(rmcaridiagnosa4.getTable().getSelectedRow(),0).toString());
                    DiagnosaSekunder3.setText(rmcaridiagnosa4.getTable().getValueAt(rmcaridiagnosa4.getTable().getSelectedRow(),1).toString());
                    KodeDiagnosaSekunder3.requestFocus();
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
        
        rmcaridiagnosa5.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcaridiagnosa5.getTable().getSelectedRow()!= -1){
                    KodeDiagnosaSekunder4.setText(rmcaridiagnosa5.getTable().getValueAt(rmcaridiagnosa5.getTable().getSelectedRow(),0).toString());
                    DiagnosaSekunder4.setText(rmcaridiagnosa5.getTable().getValueAt(rmcaridiagnosa5.getTable().getSelectedRow(),1).toString());
                    KodeDiagnosaSekunder4.requestFocus();
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
        
        rmcaridiagnosa6.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcaridiagnosa6.getTable().getSelectedRow()!= -1){
                    KodeDiagnosaSekunder5.setText(rmcaridiagnosa6.getTable().getValueAt(rmcaridiagnosa6.getTable().getSelectedRow(),0).toString());
                    DiagnosaSekunder5.setText(rmcaridiagnosa6.getTable().getValueAt(rmcaridiagnosa6.getTable().getSelectedRow(),1).toString());
                    KodeDiagnosaSekunder5.requestFocus();
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
        
        rmcaridiagnosa7.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcaridiagnosa7.getTable().getSelectedRow()!= -1){
                    KodeDiagnosaSekunder6.setText(rmcaridiagnosa7.getTable().getValueAt(rmcaridiagnosa7.getTable().getSelectedRow(),0).toString());
                    DiagnosaSekunder6.setText(rmcaridiagnosa7.getTable().getValueAt(rmcaridiagnosa7.getTable().getSelectedRow(),1).toString());
                    KodeDiagnosaSekunder6.requestFocus();
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
        
        rmcaridiagnosa8.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(rmcaridiagnosa8.getTable().getSelectedRow()!= -1){
                    KodeDiagnosaSekunder7.setText(rmcaridiagnosa8.getTable().getValueAt(rmcaridiagnosa8.getTable().getSelectedRow(),0).toString());
                    DiagnosaSekunder7.setText(rmcaridiagnosa8.getTable().getValueAt(rmcaridiagnosa8.getTable().getSelectedRow(),1).toString());
                    KodeDiagnosaSekunder7.requestFocus();
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
        jLabel25 = new widget.Label();
        DiagnosaSekunder2 = new widget.TextBox();
        jLabel26 = new widget.Label();
        DiagnosaUtama = new widget.TextBox();
        jLabel27 = new widget.Label();
        DiagnosaSekunder3 = new widget.TextBox();
        jLabel28 = new widget.Label();
        DiagnosaSekunder4 = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        DiagnosaSekunder1 = new widget.TextBox();
        jLabel31 = new widget.Label();
        KodeDiagnosaUtama = new widget.TextBox();
        KodeDiagnosaSekunder1 = new widget.TextBox();
        KodeDiagnosaSekunder2 = new widget.TextBox();
        KodeDiagnosaSekunder3 = new widget.TextBox();
        KodeDiagnosaSekunder4 = new widget.TextBox();
        label14 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel37 = new widget.Label();
        Tambahan = new widget.ComboBox();
        jLabel36 = new widget.Label();
        Keadaan = new widget.ComboBox();
        BtnDokter6 = new widget.Button();
        BtnDokter7 = new widget.Button();
        BtnDokter8 = new widget.Button();
        BtnDokter9 = new widget.Button();
        BtnDokter10 = new widget.Button();
        jLabel13 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel14 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel15 = new widget.Label();
        Ruang = new widget.TextBox();
        jLabel16 = new widget.Label();
        Masuk = new widget.TextBox();
        jLabel17 = new widget.Label();
        Keluar = new widget.TextBox();
        jLabel18 = new widget.Label();
        JamMasuk = new widget.TextBox();
        jLabel20 = new widget.Label();
        JamKeluar = new widget.TextBox();
        jLabel22 = new widget.Label();
        CaraBayar = new widget.TextBox();
        label15 = new widget.Label();
        KodeDokter1 = new widget.TextBox();
        NamaDokter1 = new widget.TextBox();
        BtnDokter15 = new widget.Button();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel24 = new widget.Label();
        DiagnosaAwal = new widget.TextBox();
        BtnDokter16 = new widget.Button();
        scrollPane7 = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        TambahanLainnya = new widget.TextBox();
        jLabel41 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        jLabel42 = new widget.Label();
        Tempat = new widget.ComboBox();
        label11 = new widget.Label();
        Kontrol = new widget.Tanggal();
        label13 = new widget.Label();
        CaraBayar1 = new widget.TextBox();
        Ruang1 = new widget.TextBox();
        BtnImplementasiKeperawatanRanap = new widget.Button();
        jLabel47 = new widget.Label();
        DiagnosaSekunder5 = new widget.TextBox();
        jLabel48 = new widget.Label();
        DiagnosaSekunder6 = new widget.TextBox();
        jLabel49 = new widget.Label();
        DiagnosaSekunder7 = new widget.TextBox();
        KodeDiagnosaSekunder5 = new widget.TextBox();
        KodeDiagnosaSekunder6 = new widget.TextBox();
        KodeDiagnosaSekunder7 = new widget.TextBox();
        BtnDokter20 = new widget.Button();
        BtnDokter21 = new widget.Button();
        BtnDokter22 = new widget.Button();
        scrollPane11 = new widget.ScrollPane();
        DiagnosaKlinis = new widget.TextArea();
        jLabel50 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel23 = new widget.Label();
        Tanggal = new widget.Tanggal();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLaporanResume.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResume.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResume.setText("Laporan Lepas Perawatan");
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Lepas Perawatan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-06-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-06-2023" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 750));
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

        jLabel25.setText("Diagnosa Sekunder 2 :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(0, 340, 145, 23);

        DiagnosaSekunder2.setEditable(false);
        DiagnosaSekunder2.setHighlighter(null);
        DiagnosaSekunder2.setName("DiagnosaSekunder2"); // NOI18N
        DiagnosaSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder2KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder2);
        DiagnosaSekunder2.setBounds(150, 340, 520, 23);

        jLabel26.setText("Diagnosa Sekunder 3 :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 370, 145, 23);

        DiagnosaUtama.setEditable(false);
        DiagnosaUtama.setHighlighter(null);
        DiagnosaUtama.setName("DiagnosaUtama"); // NOI18N
        DiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaUtamaKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaUtama);
        DiagnosaUtama.setBounds(150, 280, 520, 23);

        jLabel27.setText("Terapi Yang Diberikan :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 180, 145, 23);

        DiagnosaSekunder3.setEditable(false);
        DiagnosaSekunder3.setHighlighter(null);
        DiagnosaSekunder3.setName("DiagnosaSekunder3"); // NOI18N
        DiagnosaSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder3KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder3);
        DiagnosaSekunder3.setBounds(150, 370, 520, 23);

        jLabel28.setText("Diagnosa Sekunder 4 :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 400, 145, 23);

        DiagnosaSekunder4.setEditable(false);
        DiagnosaSekunder4.setHighlighter(null);
        DiagnosaSekunder4.setName("DiagnosaSekunder4"); // NOI18N
        DiagnosaSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder4KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder4);
        DiagnosaSekunder4.setBounds(150, 400, 520, 23);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 100, 23);

        jLabel29.setText("Diagnosa Akhir :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 260, 97, 23);

        jLabel30.setText("Diagnosa Sekunder 1 :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 310, 145, 23);

        DiagnosaSekunder1.setEditable(false);
        DiagnosaSekunder1.setHighlighter(null);
        DiagnosaSekunder1.setName("DiagnosaSekunder1"); // NOI18N
        DiagnosaSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder1KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder1);
        DiagnosaSekunder1.setBounds(150, 310, 520, 23);

        jLabel31.setText("Kode ICD :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(530, 260, 210, 23);

        KodeDiagnosaUtama.setEditable(false);
        KodeDiagnosaUtama.setHighlighter(null);
        KodeDiagnosaUtama.setName("KodeDiagnosaUtama"); // NOI18N
        KodeDiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaUtamaKeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaUtama);
        KodeDiagnosaUtama.setBounds(710, 280, 75, 23);

        KodeDiagnosaSekunder1.setEditable(false);
        KodeDiagnosaSekunder1.setHighlighter(null);
        KodeDiagnosaSekunder1.setName("KodeDiagnosaSekunder1"); // NOI18N
        KodeDiagnosaSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder1KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder1);
        KodeDiagnosaSekunder1.setBounds(710, 310, 75, 23);

        KodeDiagnosaSekunder2.setEditable(false);
        KodeDiagnosaSekunder2.setHighlighter(null);
        KodeDiagnosaSekunder2.setName("KodeDiagnosaSekunder2"); // NOI18N
        KodeDiagnosaSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder2KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder2);
        KodeDiagnosaSekunder2.setBounds(710, 340, 75, 23);

        KodeDiagnosaSekunder3.setEditable(false);
        KodeDiagnosaSekunder3.setHighlighter(null);
        KodeDiagnosaSekunder3.setName("KodeDiagnosaSekunder3"); // NOI18N
        KodeDiagnosaSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder3KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder3);
        KodeDiagnosaSekunder3.setBounds(710, 370, 75, 23);

        KodeDiagnosaSekunder4.setEditable(false);
        KodeDiagnosaSekunder4.setHighlighter(null);
        KodeDiagnosaSekunder4.setName("KodeDiagnosaSekunder4"); // NOI18N
        KodeDiagnosaSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder4KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder4);
        KodeDiagnosaSekunder4.setBounds(710, 400, 75, 23);

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

        jLabel37.setText("Instruksi Tambahan :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(5, 710, 130, 23);

        Tambahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lab", "RO", "USG", "Lainnya" }));
        Tambahan.setName("Tambahan"); // NOI18N
        Tambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahanActionPerformed(evt);
            }
        });
        Tambahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TambahanKeyPressed(evt);
            }
        });
        FormInput.add(Tambahan);
        Tambahan.setBounds(300, 710, 120, 23);

        jLabel36.setText("Keadaan Pulang :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(-4, 580, 150, 23);

        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Membaik", "Sembuh", "Keadaan Khusus", "Meninggal", "Atas Permintaan Sendiri" }));
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
        FormInput.add(Keadaan);
        Keadaan.setBounds(150, 580, 220, 23);

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
        FormInput.add(BtnDokter6);
        BtnDokter6.setBounds(790, 280, 28, 23);

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
        FormInput.add(BtnDokter7);
        BtnDokter7.setBounds(790, 310, 28, 23);

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
        FormInput.add(BtnDokter8);
        BtnDokter8.setBounds(790, 340, 28, 23);

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
        FormInput.add(BtnDokter9);
        BtnDokter9.setBounds(790, 370, 28, 23);

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
        FormInput.add(BtnDokter10);
        BtnDokter10.setBounds(790, 400, 28, 23);

        jLabel13.setText("Tgl.Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(550, 40, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(610, 40, 80, 23);

        jLabel14.setText("J.K. :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(710, 40, 30, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(740, 40, 80, 23);

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
        Masuk.setBounds(110, 100, 110, 23);

        jLabel17.setText("Tanggal Keluar :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(10, 130, 90, 23);

        Keluar.setText("1945-08-17");
        Keluar.setHighlighter(null);
        Keluar.setName("Keluar"); // NOI18N
        Keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KeluarActionPerformed(evt);
            }
        });
        FormInput.add(Keluar);
        Keluar.setBounds(110, 130, 110, 23);

        jLabel18.setText("Jam Masuk :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(220, 100, 70, 23);

        JamMasuk.setEditable(false);
        JamMasuk.setHighlighter(null);
        JamMasuk.setName("JamMasuk"); // NOI18N
        FormInput.add(JamMasuk);
        JamMasuk.setBounds(300, 100, 110, 23);

        jLabel20.setText("Jam Keluar :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(220, 130, 70, 23);

        JamKeluar.setText("00:00:00");
        JamKeluar.setHighlighter(null);
        JamKeluar.setName("JamKeluar"); // NOI18N
        JamKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JamKeluarActionPerformed(evt);
            }
        });
        FormInput.add(JamKeluar);
        JamKeluar.setBounds(300, 130, 110, 23);

        jLabel22.setText("PJ. Pembayaran :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(420, 100, 90, 23);

        CaraBayar.setEditable(false);
        CaraBayar.setHighlighter(null);
        CaraBayar.setName("CaraBayar"); // NOI18N
        FormInput.add(CaraBayar);
        CaraBayar.setBounds(520, 100, 60, 23);

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
        KodeDokter1.setBounds(450, 70, 141, 23);

        NamaDokter1.setEditable(false);
        NamaDokter1.setName("NamaDokter1"); // NOI18N
        NamaDokter1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter1);
        NamaDokter1.setBounds(590, 70, 270, 23);

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
        BtnDokter15.setBounds(870, 70, 28, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 170, 880, 1);

        jLabel24.setText("Diagnosa Awal Masuk :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(420, 130, 120, 23);

        DiagnosaAwal.setHighlighter(null);
        DiagnosaAwal.setName("DiagnosaAwal"); // NOI18N
        FormInput.add(DiagnosaAwal);
        DiagnosaAwal.setBounds(550, 130, 280, 23);

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
        BtnDokter16.setBounds(120, 200, 28, 23);

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
        scrollPane7.setBounds(150, 180, 630, 50);

        TambahanLainnya.setHighlighter(null);
        TambahanLainnya.setName("TambahanLainnya"); // NOI18N
        FormInput.add(TambahanLainnya);
        TambahanLainnya.setBounds(420, 710, 360, 23);

        jLabel41.setText("Intruksi Jika Terjadi Keadaan Gawat Darurat :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 620, 240, 23);

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
        scrollPane9.setBounds(250, 620, 530, 50);

        jLabel42.setText("Tempat :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(390, 680, 70, 23);

        Tempat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RSUD Cipayung", "Puskesmas (Faskes I)", "-" }));
        Tempat.setName("Tempat"); // NOI18N
        Tempat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TempatActionPerformed(evt);
            }
        });
        Tempat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatKeyPressed(evt);
            }
        });
        FormInput.add(Tempat);
        Tempat.setBounds(460, 680, 200, 23);

        label11.setText("WIB");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(350, 680, 30, 23);

        Kontrol.setForeground(new java.awt.Color(50, 70, 50));
        Kontrol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-06-2023 12:55:49" }));
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
        FormInput.add(Kontrol);
        Kontrol.setBounds(200, 680, 150, 23);

        label13.setText("Instruksi Tindak Lanjut, Kontrol :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label13);
        label13.setBounds(20, 680, 170, 23);

        CaraBayar1.setEditable(false);
        CaraBayar1.setHighlighter(null);
        CaraBayar1.setName("CaraBayar1"); // NOI18N
        FormInput.add(CaraBayar1);
        CaraBayar1.setBounds(580, 100, 250, 23);

        Ruang1.setEditable(false);
        Ruang1.setHighlighter(null);
        Ruang1.setName("Ruang1"); // NOI18N
        FormInput.add(Ruang1);
        Ruang1.setBounds(180, 70, 170, 23);

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
        BtnImplementasiKeperawatanRanap.setBounds(340, 240, 240, 30);

        jLabel47.setText("Diagnosa Sekunder 5 :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 430, 145, 23);

        DiagnosaSekunder5.setEditable(false);
        DiagnosaSekunder5.setHighlighter(null);
        DiagnosaSekunder5.setName("DiagnosaSekunder5"); // NOI18N
        DiagnosaSekunder5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder5KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder5);
        DiagnosaSekunder5.setBounds(150, 430, 520, 23);

        jLabel48.setText("Diagnosa Sekunder 6 :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 460, 145, 23);

        DiagnosaSekunder6.setEditable(false);
        DiagnosaSekunder6.setHighlighter(null);
        DiagnosaSekunder6.setName("DiagnosaSekunder6"); // NOI18N
        DiagnosaSekunder6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder6KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder6);
        DiagnosaSekunder6.setBounds(150, 460, 520, 23);

        jLabel49.setText("Diagnosa Klinis :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 520, 145, 23);

        DiagnosaSekunder7.setEditable(false);
        DiagnosaSekunder7.setHighlighter(null);
        DiagnosaSekunder7.setName("DiagnosaSekunder7"); // NOI18N
        DiagnosaSekunder7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder7KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder7);
        DiagnosaSekunder7.setBounds(150, 490, 520, 23);

        KodeDiagnosaSekunder5.setEditable(false);
        KodeDiagnosaSekunder5.setHighlighter(null);
        KodeDiagnosaSekunder5.setName("KodeDiagnosaSekunder5"); // NOI18N
        KodeDiagnosaSekunder5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder5KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder5);
        KodeDiagnosaSekunder5.setBounds(710, 430, 75, 23);

        KodeDiagnosaSekunder6.setEditable(false);
        KodeDiagnosaSekunder6.setHighlighter(null);
        KodeDiagnosaSekunder6.setName("KodeDiagnosaSekunder6"); // NOI18N
        KodeDiagnosaSekunder6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder6KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder6);
        KodeDiagnosaSekunder6.setBounds(710, 460, 75, 23);

        KodeDiagnosaSekunder7.setEditable(false);
        KodeDiagnosaSekunder7.setHighlighter(null);
        KodeDiagnosaSekunder7.setName("KodeDiagnosaSekunder7"); // NOI18N
        KodeDiagnosaSekunder7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder7KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder7);
        KodeDiagnosaSekunder7.setBounds(710, 490, 75, 23);

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
        FormInput.add(BtnDokter20);
        BtnDokter20.setBounds(790, 430, 28, 23);

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
        FormInput.add(BtnDokter21);
        BtnDokter21.setBounds(790, 460, 28, 23);

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
        FormInput.add(BtnDokter22);
        BtnDokter22.setBounds(790, 490, 28, 23);

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

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(150, 520, 630, 50);

        jLabel50.setText("Diagnosa Sekunder 7 :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 490, 145, 23);

        jLabel32.setText("Diagnosa Utama :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 280, 145, 23);

        jLabel23.setText("Tanggal :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel23);
        jLabel23.setBounds(140, 710, 50, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-06-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(200, 710, 90, 23);

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
            Valid.textKosong(Terapi,"Terapi Yang Diberikan");
        }else{
            if(Sequel.menyimpantf("lepas_perawatan_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",38,new String[]{
                    TNoRw.getText(),KodeDokter.getText(),TglLahir.getText(),Jk.getText(),Ruang.getText(),Masuk.getText(),JamMasuk.getText(),Keluar.getText(),JamKeluar.getText(),KodeDokter1.getText(),NamaDokter1.getText(),CaraBayar.getText(),DiagnosaAwal.getText(),
                    Terapi.getText(),DiagnosaUtama.getText(),KodeDiagnosaUtama.getText(),DiagnosaSekunder1.getText(),KodeDiagnosaSekunder1.getText(),DiagnosaSekunder2.getText(),KodeDiagnosaSekunder2.getText(),DiagnosaSekunder3.getText(),KodeDiagnosaSekunder3.getText(),
                    DiagnosaSekunder4.getText(),KodeDiagnosaSekunder4.getText(),DiagnosaSekunder5.getText(),KodeDiagnosaSekunder5.getText(),DiagnosaSekunder6.getText(),KodeDiagnosaSekunder6.getText(),DiagnosaSekunder7.getText(),KodeDiagnosaSekunder7.getText(),DiagnosaKlinis.getText(),
                    Keadaan.getSelectedItem().toString(),Edukasi.getText(),Valid.SetTgl(Kontrol.getSelectedItem()+"")+" "+Kontrol.getSelectedItem().toString().substring(11,19),Tempat.getSelectedItem().toString(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Tambahan.getSelectedItem().toString(),
                    TambahanLainnya.getText()
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
            Valid.pindah(evt,TambahanLainnya,BtnBatal);
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
            if(Sequel.queryu2tf("delete from lepas_perawatan_ranap where no_rawat=?",1,new String[]{
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
        }else if(Terapi.getText().equals("")){
            Valid.textKosong(Terapi,"Terapi Yang Diberikan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("lepas_perawatan_ranap","no_rawat=?","no_rawat=?,kd_dokter=?,tgl_lahir=?,jk=?,kd_kamar=?,masuk=?,jam_masuk=?,keluar=?,jam_keluar=?,kd_dokter1=?,nm_dokter1=?,kd_pj=?,diagnosa_awal=?,terapi=?,diagnosa_utama=?,kd_diagnosa_utama=?,diagnosa_sekunder=?,kd_diagnosa_sekunder=?,diagnosa_sekunder2=?,kd_diagnosa_sekunder2=?,diagnosa_sekunder3=?,kd_diagnosa_sekunder3=?,diagnosa_sekunder4=?,kd_diagnosa_sekunder4=?,diagnosa_sekunder5=?,kd_diagnosa_sekunder5=?,diagnosa_sekunder6=?,kd_diagnosa_sekunder6=?,diagnosa_sekunder7=?,kd_diagnosa_sekunder7=?,diagnosa_klinis=?,keadaan=?,instruksi=?,kontrol=?,tempat=?,tanggal_tambahan=?,tambahan=?,tambahan_lainnya=?",39,new String[]{
                    TNoRw.getText(),KodeDokter.getText(),TglLahir.getText(),Jk.getText(),Ruang.getText(),Masuk.getText(),JamMasuk.getText(),Keluar.getText(),JamKeluar.getText(),KodeDokter1.getText(),NamaDokter1.getText(),CaraBayar.getText(),DiagnosaAwal.getText(),
                    Terapi.getText(),DiagnosaUtama.getText(),KodeDiagnosaUtama.getText(),DiagnosaSekunder1.getText(),KodeDiagnosaSekunder1.getText(),DiagnosaSekunder2.getText(),KodeDiagnosaSekunder2.getText(),DiagnosaSekunder3.getText(),KodeDiagnosaSekunder3.getText(),
                    DiagnosaSekunder4.getText(),KodeDiagnosaSekunder4.getText(),DiagnosaSekunder5.getText(),KodeDiagnosaSekunder5.getText(),DiagnosaSekunder6.getText(),KodeDiagnosaSekunder6.getText(),DiagnosaSekunder7.getText(),KodeDiagnosaSekunder7.getText(),DiagnosaKlinis.getText(),
                    Keadaan.getSelectedItem().toString(),Edukasi.getText(),Valid.SetTgl(Kontrol.getSelectedItem()+"")+" "+Kontrol.getSelectedItem().toString().substring(11,19),Tempat.getSelectedItem().toString(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Tambahan.getSelectedItem().toString(),
                    TambahanLainnya.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()
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
                        "lepas_perawatan_ranap.kd_dokter,dokter.nm_dokter,lepas_perawatan_ranap.kondisi_pulang,lepas_perawatan_ranap.keluhan_utama,lepas_perawatan_ranap.jalannya_penyakit, "+
                        "lepas_perawatan_ranap.pemeriksaan_penunjang,lepas_perawatan_ranap.hasil_laborat,lepas_perawatan_ranap.diagnosa_utama,lepas_perawatan_ranap.kd_diagnosa_utama, "+
                        "lepas_perawatan_ranap.diagnosa_sekunder,lepas_perawatan_ranap.kd_diagnosa_sekunder,lepas_perawatan_ranap.diagnosa_sekunder2,lepas_perawatan_ranap.kd_diagnosa_sekunder2, "+
                        "lepas_perawatan_ranap.diagnosa_sekunder3,lepas_perawatan_ranap.kd_diagnosa_sekunder3,lepas_perawatan_ranap.diagnosa_sekunder4,lepas_perawatan_ranap.kd_diagnosa_sekunder4, "+
                        "lepas_perawatan_ranap.prosedur_utama,lepas_perawatan_ranap.kd_prosedur_utama,lepas_perawatan_ranap.prosedur_sekunder,lepas_perawatan_ranap.kd_prosedur_sekunder, "+
                        "lepas_perawatan_ranap.prosedur_sekunder2,lepas_perawatan_ranap.kd_prosedur_sekunder2,lepas_perawatan_ranap.prosedur_sekunder3,lepas_perawatan_ranap.kd_prosedur_sekunder3, "+
                        "lepas_perawatan_ranap.obat_pulang,lepas_perawatan_ranap.tindak_lanjut,lepas_perawatan_ranap.asal_pasien from lepas_perawatan_ranap inner join reg_periksa on lepas_perawatan_ranap.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on lepas_perawatan_ranap.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
                }else{
                    Valid.MyReportqry("rptDataResumePasien.jasper","report","::[ Data Resume Pasien ]::",
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                        "lepas_perawatan_ranap.kd_dokter,dokter.nm_dokter,lepas_perawatan_ranap.kondisi_pulang,lepas_perawatan_ranap.keluhan_utama,lepas_perawatan_ranap.jalannya_penyakit, "+
                        "lepas_perawatan_ranap.pemeriksaan_penunjang,lepas_perawatan_ranap.hasil_laborat,lepas_perawatan_ranap.diagnosa_utama,lepas_perawatan_ranap.kd_diagnosa_utama, "+
                        "lepas_perawatan_ranap.diagnosa_sekunder,lepas_perawatan_ranap.kd_diagnosa_sekunder,lepas_perawatan_ranap.diagnosa_sekunder2,lepas_perawatan_ranap.kd_diagnosa_sekunder2, "+
                        "lepas_perawatan_ranap.diagnosa_sekunder3,lepas_perawatan_ranap.kd_diagnosa_sekunder3,lepas_perawatan_ranap.diagnosa_sekunder4,lepas_perawatan_ranap.kd_diagnosa_sekunder4, "+
                        "lepas_perawatan_ranap.prosedur_utama,lepas_perawatan_ranap.kd_prosedur_utama,lepas_perawatan_ranap.prosedur_sekunder,lepas_perawatan_ranap.kd_prosedur_sekunder, "+
                        "lepas_perawatan_ranap.prosedur_sekunder2,lepas_perawatan_ranap.kd_prosedur_sekunder2,lepas_perawatan_ranap.prosedur_sekunder3,lepas_perawatan_ranap.kd_prosedur_sekunder3, "+
                        "lepas_perawatan_ranap.obat_pulang,lepas_perawatan_ranap.tindak_lanjut,lepas_perawatan_ranap.asal_pasien from lepas_perawatan_ranap inner join reg_periksa on lepas_perawatan_ranap.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on lepas_perawatan_ranap.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.status_lanjut like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and lepas_perawatan_ranap.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and lepas_perawatan_ranap.kondisi_pulang like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and lepas_perawatan_ranap.kd_diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and lepas_perawatan_ranap.diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and lepas_perawatan_ranap.prosedur_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and lepas_perawatan_ranap.kd_prosedur_utama like '%"+TCari.getText().trim()+"%' "+
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

    private void DiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaUtamaKeyPressed
       Valid.pindah(evt,Tambahan,KodeDiagnosaUtama);
    }//GEN-LAST:event_DiagnosaUtamaKeyPressed

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
        Valid.pindah(evt,DiagnosaSekunder4,DiagnosaSekunder5);
    }//GEN-LAST:event_KodeDiagnosaSekunder4KeyPressed

    private void KodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterKeyPressed
        Valid.pindah(evt,TCari,Tambahan);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
       Valid.pindah(evt,TCari,Tambahan);
    }//GEN-LAST:event_BtnDokterKeyPressed

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
            Valid.MyReport("rptLepasPerawatanRanap.jasper","report","::[ Laporan Lepas Perawatan ]::",param);
        }
    }//GEN-LAST:event_MnLaporanResumeActionPerformed

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

    private void TambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TambahanActionPerformed

    private void TambahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TambahanKeyPressed
        Valid.pindah(evt, KodeDokter, Terapi);
    }//GEN-LAST:event_TambahanKeyPressed

    private void KeadaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KeadaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanActionPerformed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanKeyPressed

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

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdukasiKeyPressed

    private void TempatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TempatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TempatActionPerformed

    private void TempatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TempatKeyPressed

    private void KontrolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontrolKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontrolKeyPressed

    private void KontrolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KontrolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontrolActionPerformed

    private void KeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KeluarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeluarActionPerformed

    private void JamKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JamKeluarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamKeluarActionPerformed

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

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataLepasPerawatanRanap dialog = new RMDataLepasPerawatanRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnDokter10;
    private widget.Button BtnDokter15;
    private widget.Button BtnDokter16;
    private widget.Button BtnDokter20;
    private widget.Button BtnDokter21;
    private widget.Button BtnDokter22;
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
    private widget.CekBox ChkInput;
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
    private widget.TextBox DiagnosaUtama;
    private widget.TextArea Edukasi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JamKeluar;
    private widget.TextBox JamMasuk;
    private widget.TextBox Jk;
    private widget.ComboBox Keadaan;
    private widget.TextBox Keluar;
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
    private widget.Tanggal Kontrol;
    private widget.Label LCount;
    private widget.TextBox Masuk;
    private javax.swing.JMenuItem MnInputDiagnosa;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox NamaDokter;
    private widget.TextBox NamaDokter1;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Ruang;
    private widget.TextBox Ruang1;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.ComboBox Tambahan;
    private widget.TextBox TambahanLainnya;
    private widget.Tanggal Tanggal;
    private widget.ComboBox Tempat;
    private widget.TextArea Terapi;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBerkasDigital;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "lepas_perawatan_ranap.kd_dokter,dokter.nm_dokter,lepas_perawatan_ranap.tgl_lahir,lepas_perawatan_ranap.jk,lepas_perawatan_ranap.kd_kamar,lepas_perawatan_ranap.masuk,lepas_perawatan_ranap.jam_masuk,lepas_perawatan_ranap.keluar, "+
                    "lepas_perawatan_ranap.jam_keluar,lepas_perawatan_ranap.kd_dokter1,lepas_perawatan_ranap.nm_dokter1,lepas_perawatan_ranap.kd_pj,penjab.png_jawab,lepas_perawatan_ranap.diagnosa_awal, "+
                    "lepas_perawatan_ranap.terapi,lepas_perawatan_ranap.diagnosa_utama,lepas_perawatan_ranap.kd_diagnosa_utama, "+
                    "lepas_perawatan_ranap.diagnosa_sekunder,lepas_perawatan_ranap.kd_diagnosa_sekunder,lepas_perawatan_ranap.diagnosa_sekunder2,lepas_perawatan_ranap.kd_diagnosa_sekunder2, "+
                    "lepas_perawatan_ranap.diagnosa_sekunder3,lepas_perawatan_ranap.kd_diagnosa_sekunder3,lepas_perawatan_ranap.diagnosa_sekunder4,lepas_perawatan_ranap.kd_diagnosa_sekunder4, "+
                    "lepas_perawatan_ranap.diagnosa_sekunder5,lepas_perawatan_ranap.kd_diagnosa_sekunder5,lepas_perawatan_ranap.diagnosa_sekunder6,lepas_perawatan_ranap.kd_diagnosa_sekunder6, "+
                    "lepas_perawatan_ranap.diagnosa_sekunder7,lepas_perawatan_ranap.kd_diagnosa_sekunder7,lepas_perawatan_ranap.diagnosa_klinis, "+
                    "lepas_perawatan_ranap.keadaan,lepas_perawatan_ranap.instruksi,lepas_perawatan_ranap.kontrol,lepas_perawatan_ranap.tempat,lepas_perawatan_ranap.tanggal_tambahan,lepas_perawatan_ranap.tambahan, "+
                    "lepas_perawatan_ranap.tambahan_lainnya from lepas_perawatan_ranap inner join reg_periksa on lepas_perawatan_ranap.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on lepas_perawatan_ranap.kd_dokter=dokter.kd_dokter "+
//                    "inner join dokter as nm_dokter1 ON nm_dokter1.kd_dokter=lepas_perawatan_ranap.kd_dokter1 "+
                    "inner join penjab on lepas_perawatan_ranap.kd_pj=penjab.kd_pj "+
//                    "inner join databarang on lepas_perawatan_ranap.kode_brng=databarang.kode_brng "+
//                    "LEFT JOIN databarang as obat1 ON obat1.kode_brng=lepas_perawatan_ranap.kode_brng1 "+
//                    "LEFT JOIN databarang as obat2 ON obat2.kode_brng=lepas_perawatan_ranap.kode_brng2 "+
//                    "LEFT JOIN databarang as obat3 ON obat3.kode_brng=lepas_perawatan_ranap.kode_brng3 "+
//                    "LEFT JOIN databarang as obat4 ON obat4.kode_brng=lepas_perawatan_ranap.kode_brng4 "+
//                    "LEFT JOIN databarang as obat5 ON obat5.kode_brng=lepas_perawatan_ranap.kode_brng5 "+
//                    "LEFT JOIN databarang as obat6 ON obat6.kode_brng=lepas_perawatan_ranap.kode_brng6 "+
//                    "LEFT JOIN databarang as obat7 ON obat7.kode_brng=lepas_perawatan_ranap.kode_brng7 "+
//                    "LEFT JOIN databarang as obat8 ON obat8.kode_brng=lepas_perawatan_ranap.kode_brng8 "+
//                    "LEFT JOIN databarang as obat9 ON obat9.kode_brng=lepas_perawatan_ranap.kode_brng9 "+
                    "where reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "lepas_perawatan_ranap.kd_dokter,dokter.nm_dokter,lepas_perawatan_ranap.tgl_lahir,lepas_perawatan_ranap.jk,lepas_perawatan_ranap.kd_kamar,lepas_perawatan_ranap.masuk,lepas_perawatan_ranap.jam_masuk,lepas_perawatan_ranap.keluar, "+
                    "lepas_perawatan_ranap.jam_keluar,lepas_perawatan_ranap.kd_dokter1,lepas_perawatan_ranap.nm_dokter1,lepas_perawatan_ranap.kd_pj,penjab.png_jawab,lepas_perawatan_ranap.diagnosa_awal, "+
                    "lepas_perawatan_ranap.terapi,lepas_perawatan_ranap.diagnosa_utama,lepas_perawatan_ranap.kd_diagnosa_utama, "+
                    "lepas_perawatan_ranap.diagnosa_sekunder,lepas_perawatan_ranap.kd_diagnosa_sekunder,lepas_perawatan_ranap.diagnosa_sekunder2,lepas_perawatan_ranap.kd_diagnosa_sekunder2, "+
                    "lepas_perawatan_ranap.diagnosa_sekunder3,lepas_perawatan_ranap.kd_diagnosa_sekunder3,lepas_perawatan_ranap.diagnosa_sekunder4,lepas_perawatan_ranap.kd_diagnosa_sekunder4, "+
                    "lepas_perawatan_ranap.diagnosa_sekunder5,lepas_perawatan_ranap.kd_diagnosa_sekunder5,lepas_perawatan_ranap.diagnosa_sekunder6,lepas_perawatan_ranap.kd_diagnosa_sekunder6, "+
                    "lepas_perawatan_ranap.diagnosa_sekunder7,lepas_perawatan_ranap.kd_diagnosa_sekunder7,lepas_perawatan_ranap.diagnosa_klinis, "+
                    "lepas_perawatan_ranap.keadaan,lepas_perawatan_ranap.instruksi,lepas_perawatan_ranap.kontrol,lepas_perawatan_ranap.tempat,lepas_perawatan_ranap.tanggal_tambahan,lepas_perawatan_ranap.tambahan, "+
                    "lepas_perawatan_ranap.tambahan_lainnya from lepas_perawatan_ranap inner join reg_periksa on lepas_perawatan_ranap.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on lepas_perawatan_ranap.kd_dokter=dokter.kd_dokter "+
//                    "inner join dokter as nm_dokter1 ON nm_dokter1.kd_dokter=lepas_perawatan_ranap.kd_dokter1 "+
                    "inner join penjab on lepas_perawatan_ranap.kd_pj=penjab.kd_pj "+
//                    "inner join databarang on lepas_perawatan_ranap.kode_brng=databarang.kode_brng "+
//                    "LEFT JOIN databarang as obat1 ON obat1.kode_brng=lepas_perawatan_ranap.kode_brng1 "+
//                    "LEFT JOIN databarang as obat2 ON obat2.kode_brng=lepas_perawatan_ranap.kode_brng2 "+
//                    "LEFT JOIN databarang as obat3 ON obat3.kode_brng=lepas_perawatan_ranap.kode_brng3 "+
//                    "LEFT JOIN databarang as obat4 ON obat4.kode_brng=lepas_perawatan_ranap.kode_brng4 "+
//                    "LEFT JOIN databarang as obat5 ON obat5.kode_brng=lepas_perawatan_ranap.kode_brng5 "+
//                    "LEFT JOIN databarang as obat6 ON obat6.kode_brng=lepas_perawatan_ranap.kode_brng6 "+
//                    "LEFT JOIN databarang as obat7 ON obat7.kode_brng=lepas_perawatan_ranap.kode_brng7 "+
//                    "LEFT JOIN databarang as obat8 ON obat8.kode_brng=lepas_perawatan_ranap.kode_brng8 "+
//                    "LEFT JOIN databarang as obat9 ON obat9.kode_brng=lepas_perawatan_ranap.kode_brng9 "+
                    "where reg_periksa.tgl_registrasi between ? and ? and reg_periksa.status_lanjut like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and lepas_perawatan_ranap.kd_dokter like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and dokter.nm_dokter like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and lepas_perawatan_ranap.diagnosa_awal like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and lepas_perawatan_ranap.kd_diagnosa_utama like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and lepas_perawatan_ranap.diagnosa_utama like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and lepas_perawatan_ranap.instruksi like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rawat like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and lepas_perawatan_ranap.tambahan like ? "+
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
                        rs.getString("jam_keluar"),rs.getString("kd_dokter1"),rs.getString("nm_dokter1"),rs.getString("kd_pj"),rs.getString("png_jawab"),rs.getString("diagnosa_awal"),
                        rs.getString("terapi"),rs.getString("diagnosa_utama"),rs.getString("kd_diagnosa_utama"),rs.getString("diagnosa_sekunder"),rs.getString("kd_diagnosa_sekunder"),rs.getString("diagnosa_sekunder2"),
                        rs.getString("kd_diagnosa_sekunder2"),rs.getString("diagnosa_sekunder3"),rs.getString("kd_diagnosa_sekunder3"),rs.getString("diagnosa_sekunder4"),rs.getString("kd_diagnosa_sekunder4"),
                        rs.getString("diagnosa_sekunder5"),rs.getString("kd_diagnosa_sekunder5"),rs.getString("diagnosa_sekunder6"),rs.getString("kd_diagnosa_sekunder6"),rs.getString("diagnosa_sekunder7"),rs.getString("kd_diagnosa_sekunder7"),rs.getString("diagnosa_klinis"),
                        rs.getString("keadaan"),rs.getString("instruksi"),rs.getString("kontrol"),rs.getString("tempat"),rs.getString("tanggal_tambahan"),rs.getString("tambahan"),rs.getString("tambahan_lainnya")
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
        Keluar.setText("1945-08-17");
        JamKeluar.setText("00:00:00");
        Terapi.setText("");
        DiagnosaUtama.setText("");
        DiagnosaSekunder1.setText("");
        DiagnosaSekunder2.setText("");
        DiagnosaSekunder3.setText("");
        DiagnosaSekunder4.setText("");
        DiagnosaSekunder5.setText("");
        DiagnosaSekunder6.setText("");
        DiagnosaSekunder7.setText("");
        KodeDiagnosaUtama.setText("");
        KodeDiagnosaSekunder1.setText("");
        KodeDiagnosaSekunder2.setText("");
        KodeDiagnosaSekunder3.setText("");
        KodeDiagnosaSekunder4.setText("");
        KodeDiagnosaSekunder5.setText("");
        KodeDiagnosaSekunder6.setText("");
        KodeDiagnosaSekunder7.setText("");
        DiagnosaKlinis.setText("");
        Keadaan.setSelectedIndex(0);
        Edukasi.setText("");
        Tempat.setSelectedIndex(0);
        Tambahan.setSelectedIndex(0);
        TambahanLainnya.setText("");
        TNoRw.requestFocus();
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
            Keluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            JamKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            KodeDokter1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());  
            NamaDokter1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            CaraBayar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            CaraBayar1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            DiagnosaAwal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            DiagnosaUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());  
            KodeDiagnosaUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            DiagnosaSekunder1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());  
            KodeDiagnosaSekunder1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());    
            DiagnosaSekunder2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());  
            KodeDiagnosaSekunder2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());    
            DiagnosaSekunder3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());  
            KodeDiagnosaSekunder3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());    
            DiagnosaSekunder4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());  
            KodeDiagnosaSekunder4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());  
            DiagnosaSekunder5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());  
            KodeDiagnosaSekunder5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());    
            DiagnosaSekunder6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());  
            KodeDiagnosaSekunder6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());    
            DiagnosaSekunder7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());  
            KodeDiagnosaSekunder7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            DiagnosaKlinis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString()); 
            Keadaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());  
            Valid.SetTgl2(Kontrol,tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Tempat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Tambahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            TambahanLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString()); 
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
//                    Agama.setText(rs.getString("agama"));
//                    Bahasa.setText(rs.getString("nama_bahasa"));
//                    CacatFisik.setText(rs.getString("nama_cacat"));
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
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat='"+TNoRw.getText()+"' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='1'");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                namaPenyakitt="";
                while(rs.next()){
                    Listpenyakit=rs.getString("nm_penyakit");
                    Listpenyakitt=rs.getString("kd_penyakit");
                    namaPenyakit=Listpenyakit;
                    namaPenyakitt=Listpenyakitt;
//                    namaPenyakit=namaPenyakit+""+Listpenyakit+",";
                }
//                 TPenilaian.setText(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
                 DiagnosaUtama.setText(namaPenyakit.length()>0 ? namaPenyakit:"");
                 KodeDiagnosaUtama.setText(namaPenyakitt.length()>0 ? namaPenyakitt:"");
//                   DiagnosaUtama.setText(namaPenyakit);
//                   KodeDiagnosaUtama.setText(namaPenyakitt);
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
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat='"+TNoRw.getText()+"' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='2'");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                namaPenyakitt="";
                while(rs.next()){
                    Listpenyakit=rs.getString("nm_penyakit");
                    Listpenyakitt=rs.getString("kd_penyakit");
                    namaPenyakit=Listpenyakit;
                    namaPenyakitt=Listpenyakitt;
//                    namaPenyakit=namaPenyakit+""+Listpenyakit+",";
                }
//                 TPenilaian.setText(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
                 DiagnosaSekunder1.setText(namaPenyakit.length()>0 ? namaPenyakit:"");
                 KodeDiagnosaSekunder1.setText(namaPenyakitt.length()>0 ? namaPenyakitt:"");
//                   DiagnosaUtama.setText(namaPenyakit);
//                   KodeDiagnosaUtama.setText(namaPenyakitt);
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
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat='"+TNoRw.getText()+"' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='3'");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                namaPenyakitt="";
                while(rs.next()){
                    Listpenyakit=rs.getString("nm_penyakit");
                    Listpenyakitt=rs.getString("kd_penyakit");
                    namaPenyakit=Listpenyakit;
                    namaPenyakitt=Listpenyakitt;
//                    namaPenyakit=namaPenyakit+""+Listpenyakit+",";
                }
//                 TPenilaian.setText(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
                 DiagnosaSekunder2.setText(namaPenyakit.length()>0 ? namaPenyakit:"");
                 KodeDiagnosaSekunder2.setText(namaPenyakitt.length()>0 ? namaPenyakitt:"");
//                   DiagnosaUtama.setText(namaPenyakit);
//                   KodeDiagnosaUtama.setText(namaPenyakitt);
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
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat='"+TNoRw.getText()+"' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='4'");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                namaPenyakitt="";
                while(rs.next()){
                    Listpenyakit=rs.getString("nm_penyakit");
                    Listpenyakitt=rs.getString("kd_penyakit");
                    namaPenyakit=Listpenyakit;
                    namaPenyakitt=Listpenyakitt;
//                    namaPenyakit=namaPenyakit+""+Listpenyakit+",";
                }
//                 TPenilaian.setText(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
                 DiagnosaSekunder3.setText(namaPenyakit.length()>0 ? namaPenyakit:"");
                 KodeDiagnosaSekunder3.setText(namaPenyakitt.length()>0 ? namaPenyakitt:"");
//                   DiagnosaUtama.setText(namaPenyakit);
//                   KodeDiagnosaUtama.setText(namaPenyakitt);
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
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat='"+TNoRw.getText()+"' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='5'");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                namaPenyakitt="";
                while(rs.next()){
                    Listpenyakit=rs.getString("nm_penyakit");
                    Listpenyakitt=rs.getString("kd_penyakit");
                    namaPenyakit=Listpenyakit;
                    namaPenyakitt=Listpenyakitt;
//                    namaPenyakit=namaPenyakit+""+Listpenyakit+",";
                }
//                 TPenilaian.setText(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
                 DiagnosaSekunder4.setText(namaPenyakit.length()>0 ? namaPenyakit:"");
                 KodeDiagnosaSekunder4.setText(namaPenyakitt.length()>0 ? namaPenyakitt:"");
//                   DiagnosaUtama.setText(namaPenyakit);
//                   KodeDiagnosaUtama.setText(namaPenyakitt);
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
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat='"+TNoRw.getText()+"' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='6'");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                namaPenyakitt="";
                while(rs.next()){
                    Listpenyakit=rs.getString("nm_penyakit");
                    Listpenyakitt=rs.getString("kd_penyakit");
                    namaPenyakit=Listpenyakit;
                    namaPenyakitt=Listpenyakitt;
//                    namaPenyakit=namaPenyakit+""+Listpenyakit+",";
                }
//                 TPenilaian.setText(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
                 DiagnosaSekunder5.setText(namaPenyakit.length()>0 ? namaPenyakit:"");
                 KodeDiagnosaSekunder5.setText(namaPenyakitt.length()>0 ? namaPenyakitt:"");
//                   DiagnosaUtama.setText(namaPenyakit);
//                   KodeDiagnosaUtama.setText(namaPenyakitt);
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
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat='"+TNoRw.getText()+"' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='7'");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                namaPenyakitt="";
                while(rs.next()){
                    Listpenyakit=rs.getString("nm_penyakit");
                    Listpenyakitt=rs.getString("kd_penyakit");
                    namaPenyakit=Listpenyakit;
                    namaPenyakitt=Listpenyakitt;
//                    namaPenyakit=namaPenyakit+""+Listpenyakit+",";
                }
//                 TPenilaian.setText(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
                 DiagnosaSekunder6.setText(namaPenyakit.length()>0 ? namaPenyakit:"");
                 KodeDiagnosaSekunder6.setText(namaPenyakitt.length()>0 ? namaPenyakitt:"");
//                   DiagnosaUtama.setText(namaPenyakit);
//                   KodeDiagnosaUtama.setText(namaPenyakitt);
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
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat='"+TNoRw.getText()+"' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='8'");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                namaPenyakitt="";
                while(rs.next()){
                    Listpenyakit=rs.getString("nm_penyakit");
                    Listpenyakitt=rs.getString("kd_penyakit");
                    namaPenyakit=Listpenyakit;
                    namaPenyakitt=Listpenyakitt;
//                    namaPenyakit=namaPenyakit+""+Listpenyakit+",";
                }
//                 TPenilaian.setText(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
                 DiagnosaSekunder7.setText(namaPenyakit.length()>0 ? namaPenyakit:"");
                 KodeDiagnosaSekunder7.setText(namaPenyakitt.length()>0 ? namaPenyakitt:"");
//                   DiagnosaUtama.setText(namaPenyakit);
//                   KodeDiagnosaUtama.setText(namaPenyakitt);
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

//    private void isRawat() {
//         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
//    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
        Sequel.cariIsi("select kd_kamar from kamar_inap where no_rawat='"+TNoRw.getText()+"' order by tgl_masuk desc limit 1",Ruang);
        Sequel.cariIsi("select concat(bangsal.nm_bangsal) as kd_kamar,kamar.kd_bangsal,kamar_inap.kd_kamar from kamar_inap INNER JOIN kamar ON kamar_inap.kd_kamar = kamar.kd_kamar INNER JOIN bangsal ON kamar.kd_bangsal = bangsal.kd_bangsal where no_rawat='"+TNoRw.getText()+"' order by tgl_masuk desc limit 1",Ruang1);
        Sequel.cariIsi("select diagnosa_awal from kamar_inap where no_rawat='"+TNoRw.getText()+"' ",DiagnosaAwal);
        Sequel.cariIsi("select tgl_masuk from kamar_inap where no_rawat='"+TNoRw.getText()+"' order by tgl_masuk asc limit 1",Masuk);
        Sequel.cariIsi("select jam_masuk from kamar_inap where no_rawat='"+TNoRw.getText()+"' order by jam_masuk asc limit 1",JamMasuk);
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
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);    
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
        Tambahan.requestFocus();
        try {
            ps=koneksi.prepareStatement(
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat=? order by diagnosa_pasien.prioritas ");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                while(rs.next()){
                    if(rs.getInt("prioritas")==1){
                        KodeDiagnosaUtama.setText(rs.getString("kd_penyakit"));
                        DiagnosaUtama.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==2){
                        KodeDiagnosaSekunder1.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder1.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==3){
                        KodeDiagnosaSekunder2.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder2.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==4){
                        KodeDiagnosaSekunder3.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder3.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==5){
                        KodeDiagnosaSekunder4.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder4.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==6){
                        KodeDiagnosaSekunder5.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder5.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==7){
                        KodeDiagnosaSekunder6.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder6.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==8){
                        KodeDiagnosaSekunder7.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder7.setText(rs.getString("nm_penyakit"));
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
        MnInputDiagnosa.setEnabled(akses.getdiagnosa_pasien());   
        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());    
//        if(akses.getjml2()>=1){
//            KodeDokter.setEditable(false);
//            BtnDokter.setEnabled(false);
//            KodeDokter.setText(akses.getkode());
//            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NamaDokter,KodeDokter.getText());
//            if(NamaDokter.getText().equals("")){
//                KodeDokter.setText("");
//                JOptionPane.showMessageDialog(null,"User login bukan dokter...!!");
//            }
//        }            
    }

    
}
