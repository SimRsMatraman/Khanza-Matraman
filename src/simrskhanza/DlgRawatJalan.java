/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
//
package simrskhanza;

import bridging.ICareRiwayatPerawatan;
import bridging.ICareRiwayatPerawatanFKTP;
import surat.SuratKontrol;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import inventory.DlgPemberianObat;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariObat;
import inventory.DlgCopyResep;
import inventory.DlgPeresepanDokter;
import inventory.InventoryResepLuar;
import inventory.DlgTemplateResep;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;
import keuangan.DlgJnsPerawatanRalan;
import keuangan.Jurnal;
import laporan.DlgBerkasRawat;
import permintaan.DlgBookingOperasi;
import rekammedis.RMDataResumePasien;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanPelayananInformasiObat;
import permintaan.DlgPermintaanRadiologi;
import permintaan.DlgPermintaanKonsultasiMedik;
import rekammedis.MasterCariTemplatePemeriksaan;
import rekammedis.RMCari5SOAPTerakhir;
import rekammedis.RMChecklistPostOperasi;
import rekammedis.RMChecklistPreOperasi;
import rekammedis.RMDataAsuhanGizi;
import rekammedis.RMDataCatatanCekGDS;
import rekammedis.RMDataCatatanObservasiIGD;
import rekammedis.RMDataMonitoringAsuhanGizi;
import rekammedis.RMDataMonitoringReaksiTranfusi;
import rekammedis.RMDataSkriningGiziLanjut;
import rekammedis.RMEdukasiPasienKeluargaRawatJalan;
import rekammedis.RMHasilPemeriksaanUSG;
import rekammedis.RMKonselingFarmasi;
import rekammedis.RMMCU;
import rekammedis.RMPemantauanPEWS;
import rekammedis.RMPemantauanPEWSD;
import rekammedis.RMPenilaianAwalKeperawatanBayiAnak;
import rekammedis.RMPenilaianAwalKeperawatanGigi;
import rekammedis.RMPenilaianAwalKeperawatanIGD;
import rekammedis.RMPenilaianAwalKeperawatanKebidanan;
import rekammedis.RMPenilaianAwalKeperawatanRalan;
import rekammedis.RMPenilaianAwalKeperawatanMata;
import rekammedis.RMPenilaianAwalKeperawatanRalanPsikiatri;
import rekammedis.RMPenilaianAwalKeperawatanRalanGeriatri;
import rekammedis.AsesmenAwalMedisIGD;
import rekammedis.RMPenilaianAwalMedisRalanAnak;
import rekammedis.RMPenilaianAwalMedisRalanBedah;
import rekammedis.RMPenilaianAwalMedisRalanDewasa;
import rekammedis.RMPenilaianAwalMedisRalanGeriatri;
import rekammedis.RMPenilaianAwalMedisRalanKandungan;
import rekammedis.RMPenilaianAwalMedisRalanMata;
import rekammedis.RMPenilaianAwalMedisRalanNeurologi;
import rekammedis.RMPenilaianAwalMedisRalanOrthopedi;
import rekammedis.RMPenilaianAwalMedisRalanPenyakitDalam;
import rekammedis.RMPenilaianAwalMedisRalanPsikiatrik;
import rekammedis.RMPenilaianAwalMedisRalanTHT;
import rekammedis.RMPenilaianFisioterapi;
import rekammedis.RMPenilaianKorbanKekerasan;
import rekammedis.RMPenilaianLanjutanRisikoJatuhAnak;
import rekammedis.RMPenilaianLanjutanRisikoJatuhDewasa;
import rekammedis.RMPenilaianLanjutanRisikoJatuhLansia;
import rekammedis.RMPenilaianPasienPenyakitMenular;
import rekammedis.RMPenilaianPasienTerminal;
import rekammedis.RMPenilaianPreAnastesi;
import rekammedis.RMPenilaianPreOperasi;
import rekammedis.RMPenilaianPsikologi;
import rekammedis.RMPenilaianTambahanBunuhDiri;
import rekammedis.RMPenilaianTambahanGeriatri;
import rekammedis.RMPenilaianTambahanMelarikanDiri;
import rekammedis.RMPenilaianTambahanPerilakuKekerasan;
import rekammedis.RMRekonsiliasiObat;
import rekammedis.RMRiwayatPerawatan;
import rekammedis.RMRiwayatRadLab;
import rekammedis.RMSignInSebelumAnastesi;
import rekammedis.RMSignOutSebelumMenutupLuka;
import rekammedis.RMSkriningNutrisiAnak;
import rekammedis.RMSkriningNutrisiDewasa;
import rekammedis.RMSkriningNutrisiLansia;
import rekammedis.RMTimeOutSebelumInsisi;
import rekammedis.RMTransferPasienAntarRuang;
import rekammedis.RMTriaseIGD;
import rekammedis.RMUjiFungsiKFR;
import rekammedis.GambarOdontogram;
import rekammedis.DlgOdontogram;
import rekammedis.RMDataResumePasienRajal;
import rekammedis.RMInacbgRajal;
import rekammedis.RMHasilEKG;
import rekammedis.RMDataIC;
import rekammedis.DlgMasterS;
import rekammedis.DlgMasterO;
import rekammedis.DlgMasterP;
import rekammedis.DlgSOAPOld;
import rekammedis.DlgEWS;
import rekammedis.DlgPEWS;
import integration.DataPasienIntegration;
import laporan.DlgDiagnosaPenyakitSoap;
import rekammedis.GdsIgd;
import rekammedis.RMRujukanFisio;

/**
 *
 * @author dosen
 */
public final class DlgRawatJalan extends javax.swing.JDialog {
    private final DefaultTableModel tabModeDr,tabModePr,tabModeDrPr,
            tabModePemeriksaan,tabModePemeriksaanRM,tabModeObstetri,tabModeGinekologi,
            TabModeTindakan,TabModeTindakan2,TabModeTindakan3,TabModeCatatan;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgCariPasien pasien=new DlgCariPasien(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);    
    public  DlgCariPegawai pegawai=new DlgCariPegawai(null,false); 
    public DlgMasterS masters=new DlgMasterS(null,false);
    public DlgMasterO mastero=new DlgMasterO(null,false);
    public DlgMasterP masterp=new DlgMasterP(null,false);
    private RMCari5SOAPTerakhir soapterakhir=new RMCari5SOAPTerakhir(null,false);    
    private DlgDiagnosaPenyakitSoap penyakit=new DlgDiagnosaPenyakitSoap(null,false);
    private PreparedStatement ps,ps2,ps3,ps4,ps5,ps6,pstindakan,psset_tarif,psrekening;
    private ResultSet rs,rstindakan,rsset_tarif,rsrekening;
    private int i=0,jmlparsial=0,jml=0,index=0,tinggi=0;
    private String aktifkanparsial="no",kode_poli="",kd_pj="",poli_ralan="No",cara_bayar_ralan="No",
            Suspen_Piutang_Tindakan_Ralan="",Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",
            Beban_Jasa_Medik_Paramedis_Tindakan_Ralan="",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan="",Beban_KSO_Tindakan_Ralan="",Utang_KSO_Tindakan_Ralan="",
            Beban_Jasa_Sarana_Tindakan_Ralan="",Utang_Jasa_Sarana_Tindakan_Ralan="",HPP_BHP_Tindakan_Ralan="",Persediaan_BHP_Tindakan_Ralan="",
            Beban_Jasa_Menejemen_Tindakan_Ralan="",Utang_Jasa_Menejemen_Tindakan_Ralan="",namaPenyakit="",Listpenyakit="", variabel="", authEncrypt, auth;
    private final Properties prop = new Properties();
    private boolean[] pilih; 
    private String[] kode,nama,kategori;
    private double[] totaltnd,bagianrs,bhp,jmdokter,jmperawat,kso,menejemen;
    private boolean sukses=false;
    private double ttljmdokter=0,ttljmperawat=0,ttlkso=0,ttljasasarana=0,ttlbhp=0,ttlmenejemen=0,ttlpendapatan=0;
    private Jurnal jur=new Jurnal();

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public DlgRawatJalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);
        tabModeDr=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Perawatan/Tindakan","Kode Dokter","Dokter Yg Menangani","Tgl.Rawat","Jam Rawat","Biaya","Kode","Tarif Dokter","KSO","Jasa Sarana","BHP","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatDr.setModel(tabModeDr);
        //tampilDr();

        tbRawatDr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbRawatDr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatDr.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePr=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Perawatan/Tindakan","NIP","Petugas Yg Menangani","Tgl.Rawat","Jam Rawat","Biaya","Kode","Tarif Perawat","KSO","Jasa Sarana","BHP","Menejemen"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatPr.setModel(tabModePr);
        tbRawatPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbRawatPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatPr.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDrPr=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Perawatan/Tindakan","Kode Dokter","Dokter Yg Menangani","NIP","Petugas Yg Menangani","Tgl.Rawat","Jam Rawat",
            "Biaya","Kode","Tarif Dokter","Tarif Petugas","KSO","Jasa Sarana","BHP","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class,java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatDrPr.setModel(tabModeDrPr);
        tbRawatDrPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDrPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbRawatDrPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==16){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==17){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==18){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatDrPr.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePemeriksaan=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Tgl.Rawat","Jam Rawat","Suhu(C)","Tensi","Nadi(/menit)",
            "Respirasi(/menit)","Tinggi(Cm)","Berat(Kg)","GCS(E,V,M)","Kesadaran","S (SUBJECTIVE)","O (OBJECTIVE)","Alergi",
            "Imun Ke","P (PLAN)","A (ASSESSMENT)","RPD","RPK","RPO","SpO2(%)","Riwayat Operasi","NIP","Nama","Instruksi"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPemeriksaan.setModel(tabModePemeriksaan);
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 28; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(75);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(75);
            }else if(i==12){
                column.setPreferredWidth(75);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setPreferredWidth(180);
            }else if(i==16){
                column.setPreferredWidth(130);
            }else if(i==17){
                column.setPreferredWidth(50);
            }else if(i==18){
                column.setPreferredWidth(180);
            }else if(i==19){
                column.setPreferredWidth(180);
            }else if(i==20){
                column.setPreferredWidth(180);
            }else if(i==21){
                column.setPreferredWidth(180);
            }else if(i==22){
                column.setPreferredWidth(180);
            }else if(i==23){
                column.setPreferredWidth(180);
            }else if(i==24){
                column.setPreferredWidth(180);
            }else if(i==25){
                column.setPreferredWidth(180);
            }else if(i==26){
                column.setPreferredWidth(180);
            }else if(i==27){
                column.setPreferredWidth(180);
            }
        }
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePemeriksaanRM=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Tgl.Rawat","Jam Rawat","Kesadaran","Anamnesa","Pemeriksaan Fisik",
            "Pemeriksaan Penunjang","Anjuran","Diagnosa","Tata Laksana","Evaluasi","Suspek Penyakit","Ket. Suspek",
            "RPD","RPK","RPO","Riwayat Alergi","Instruksi","Riwayat Operasi","NIP","Nama",}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPemeriksaanRM.setModel(tabModePemeriksaanRM);
        tbPemeriksaanRM.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaanRM.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbPemeriksaanRM.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(130);
            }else if(i==7){
                column.setPreferredWidth(300);
            }else if(i==8){
                column.setPreferredWidth(300);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(300);
            }else if(i==11){
                column.setPreferredWidth(200);
            }else if(i==12){
                column.setPreferredWidth(250);
            }else if(i==13){
                column.setPreferredWidth(250);
            }else if(i==14){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(70);
            }else if(i==16){
                column.setPreferredWidth(70);
            }else if(i==17){
                column.setPreferredWidth(70);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(180);
            }else if(i==20){
                column.setPreferredWidth(180);
            }else {
                column.setPreferredWidth(180);
            }
        }
        tbPemeriksaanRM.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeObstetri=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M","Nama Pasien","Tgl.Rawat","Jam Rawat",
            "Tinggi Fundus","Janin", "Letak","Panggul","Denyut","Kontraksi",
            "Kualitas Mnt", "Kualitas Detik","Fluksus","Albus","Vulva",
            "Portio","Dalam","Tebal","Arah","Pembukaan","Penurunan",
            "Denominator","Ketuban","Feto","NIP","Nama Dokter"}) {
             @Override public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
            }
            Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
            };
            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        };
        
        tbPemeriksaanObstetri.setModel(tabModeObstetri);
        tbPemeriksaanObstetri.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaanObstetri.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 28; i++) {
            TableColumn column = tbPemeriksaanObstetri.getColumnModel().getColumn(i);
            if(i==0) {
                column.setPreferredWidth(20);
            }else if(i==1) {
                column.setPreferredWidth(105);
            }else if(i==2) {
                column.setPreferredWidth(70);
            }else if(i==3) {
                column.setPreferredWidth(180);
            }else if(i==4) {
                column.setPreferredWidth(80);
            }else if(i==5) {
                column.setPreferredWidth(70);
            }else if(i==6) {
                column.setPreferredWidth(80);
            }else if(i==7) {
                column.setPreferredWidth(60);
            }else if(i==8) {
                column.setPreferredWidth(60);
            }else if(i==9) {
                column.setPreferredWidth(60);
            }else if(i==10) {
                column.setPreferredWidth(60);
            }else if(i==11) {
                column.setPreferredWidth(60);
            }else if(i==12) {
                column.setPreferredWidth(70);
            }else if(i==13) {
                column.setPreferredWidth(80);
            }else if(i==14) {
                column.setPreferredWidth(50);
            }else if(i==15) {
                column.setPreferredWidth(40);
            }else if(i==16) {
                column.setPreferredWidth(170);
            }else if(i==17) {
                column.setPreferredWidth(170);
            }else if(i==18) {
                column.setPreferredWidth(60);
            }else if(i==19) {
                column.setPreferredWidth(50);
            }else if(i==20) {
                column.setPreferredWidth(60);
            }else if(i==21) {
                column.setPreferredWidth(170);
            }else if(i==22) {
                column.setPreferredWidth(170);
            }else if(i==23) {
                column.setPreferredWidth(170);
            }else if(i==24) {
                column.setPreferredWidth(50);
            }else if(i==25) {
                column.setPreferredWidth(70);
            }else if(i==26) {
                column.setPreferredWidth(180);
            }else if(i==27) {
                column.setPreferredWidth(180);
            }
        }
        tbPemeriksaanObstetri.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeGinekologi=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M","Nama Pasien","Tgl.Rawat","Jam Rawat",
            "Inpeksi","Inspeksi Vulva/Uretra/Vagina", "Inspekulo","Fluxus",
            "Fluor Albus", "Inspekulo Vulva/Vagina", "Inspekulo Portio", "Inspekulo Sondage",
            "Pemeriksaan Dalam Portio", "Pemeriksaan Dalam Bentuk","Pemeriksaan Dalam Cavum Uteri","Mobilitas",
            "Ukuran Cavum Uteri","Nyeri Tekan","Pemeriksaan Dalam Adnexa Kanan","Pemeriksaan Dalam Adnexa Kiri",
            "Pemeriksaan Dalam Cavum Douglas","NIP","Nama Dokter"}) {
             @Override public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class
                 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        
        tbPemeriksaanGinekologi.setModel(tabModeGinekologi);
        tbPemeriksaanGinekologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaanGinekologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 25; i++) {
            TableColumn column = tbPemeriksaanGinekologi.getColumnModel().getColumn(i);
            if(i==0) {
                column.setPreferredWidth(20);
            }else if(i==1) {
                column.setPreferredWidth(105);
            }else if(i==2) {
                column.setPreferredWidth(70);
            }else if(i==3) {
                column.setPreferredWidth(180);
            }else if(i==4) {
                column.setPreferredWidth(80);
            }else if(i==5) {
                column.setPreferredWidth(70);
            }else if(i==6) {
                column.setPreferredWidth(200);
            }else if(i==7) {
                column.setPreferredWidth(200);
            }else if(i==8) {
                column.setPreferredWidth(200);
            }else if(i==9) {
                column.setPreferredWidth(42);
            }else if(i==10) {
                column.setPreferredWidth(62);
            }else if(i==11) {
                column.setPreferredWidth(200);
            }else if(i==12) {
                column.setPreferredWidth(200);
            }else if(i==13) {
                column.setPreferredWidth(200);
            }else if(i==14) {
                column.setPreferredWidth(200);
            }else if(i==15) {
                column.setPreferredWidth(200);
            }else if(i==16) {
                column.setPreferredWidth(200);
            }else if(i==17) {
                column.setPreferredWidth(50);
            }else if(i==18) {
                column.setPreferredWidth(200);
            }else if(i==19) {
                column.setPreferredWidth(67);
            }else if(i==20) {
                column.setPreferredWidth(200);
            }else if(i==21) {
                column.setPreferredWidth(200);
            }else if(i==22) {
                column.setPreferredWidth(200);
            }else if(i==23) {
                column.setPreferredWidth(180);
            }else if(i==24) {
                column.setPreferredWidth(180);
            }  
        }
        tbPemeriksaanGinekologi.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakan=new DefaultTableModel(null,new Object[]{
            "P","Kode","Nama Perawatan","Kategori Perawatan","Tarif/Biaya","Bagian RS","BHP","JM Dokter","JM Perawat","KSO","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
             //
        };
        tbTindakan.setModel(TabModeTindakan);
        tbTindakan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(420);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeTindakan2=new DefaultTableModel(null,new Object[]{"P","Kode","Nama Perawatan","Kategori Perawatan","Tarif/Biaya","Bagian RS","BHP","JM Dokter","JM Perawat","KSO","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan2.setModel(TabModeTindakan2);
        tbTindakan2.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(420);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbTindakan2.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakan3=new DefaultTableModel(null,new Object[]{"P","Kode","Nama Perawatan","Kategori Perawatan","Tarif/Biaya","Bagian RS","BHP","JM Dokter","JM Perawat","KSO","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan3.setModel(TabModeTindakan3);
        tbTindakan3.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan3.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(420);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbTindakan3.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeCatatan=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Tanggal","Jam","Kode Dokter","Nama Dokter","Catatan"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbCatatan.setModel(TabModeCatatan);
        tbCatatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbCatatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbCatatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(700);
            }
        }
        tbCatatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        kdptg.setDocument(new batasInput((byte)20).getKata(kdptg));
        kdptg2.setDocument(new batasInput((byte)20).getKata(kdptg2));
        KdDok.setDocument(new batasInput((byte)20).getKata(KdDok));
        KdDok2.setDocument(new batasInput((byte)20).getKata(KdDok2));
        KodeDokter.setDocument(new batasInput((byte)20).getKata(KodeDokter));
        TSuhu.setDocument(new batasInput((byte)5).getKata(TSuhu));
        TTensi.setDocument(new batasInput((byte)8).getKata(TTensi));
        TKeluhan.setDocument(new batasInput((int)2000).getKata(TKeluhan));
        TPemeriksaan.setDocument(new batasInput((int)2000).getKata(TPemeriksaan));
        TPenilaian.setDocument(new batasInput((int)1000).getKata(TPenilaian));    
        Instruksi.setDocument(new batasInput((int)2000).getKata(Instruksi));     
        TAlergi.setDocument(new batasInput((int)50).getKata(TAlergi));        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));       
        TGCS.setDocument(new batasInput((byte)10).getKata(TGCS));
        TTinggi.setDocument(new batasInput((byte)5).getKata(TTinggi));
//        LingkarPerut.setDocument(new batasInput((byte)5).getKata(LingkarPerut));
        TBerat.setDocument(new batasInput((byte)5).getKata(TBerat));
        TindakLanjut.setDocument(new batasInput((int)1000).getKata(TindakLanjut));
//        TEvaluasi.setDocument(new batasInput((int)2000).getKata(TEvaluasi));
        TNadi.setDocument(new batasInput((byte)3).getOnlyAngka(TNadi));
//        SpO2.setDocument(new batasInput((byte)3).getOnlyAngka(SpO2));
        TRespirasi.setDocument(new batasInput((byte)3).getOnlyAngka(TRespirasi));
        TRpd.setDocument(new batasInput((int)1000).getKata(TRpd));
        TRpk.setDocument(new batasInput((int)1000).getKata(TRpk));
        TRpo.setDocument(new batasInput((int)1000).getKata(TRpo));
        TSpo2.setDocument(new batasInput((byte)5).getKata(TSpo2));
        TTinggi_uteri.setDocument(new batasInput((byte)5).getKata(TTinggi_uteri));
        TLetak.setDocument(new batasInput((byte)50).getKata(TLetak));
        TDenyut.setDocument(new batasInput((byte)5).getKata(TDenyut));
        TKualitas_dtk.setDocument(new batasInput((byte)5).getKata(TKualitas_dtk));
        TKualitas_mnt.setDocument(new batasInput((byte)5).getKata(TKualitas_mnt));
        TVulva.setDocument(new batasInput((byte)50).getKata(TVulva));
        TPortio.setDocument(new batasInput((byte)50).getKata(TPortio));
        TTebal.setDocument(new batasInput((byte)5).getKata(TTebal));
        TPembukaan.setDocument(new batasInput((byte)50).getKata(TPembukaan));
        TPenurunan.setDocument(new batasInput((byte)50).getKata(TPenurunan));
        TDenominator.setDocument(new batasInput((byte)50).getKata(TDenominator));
        TInspeksi.setDocument(new batasInput((byte)50).getKata(TInspeksi));
        TInspeksiVulva.setDocument(new batasInput((byte)50).getKata(TInspeksiVulva));
        TInspekuloGine.setDocument(new batasInput((byte)50).getKata(TInspekuloGine));
        TVulvaInspekulo.setDocument(new batasInput((byte)50).getKata(TVulvaInspekulo));
        TPortioInspekulo.setDocument(new batasInput((byte)50).getKata(TPortioInspekulo));
        TSondage.setDocument(new batasInput((byte)50).getKata(TSondage));
        TPortioDalam.setDocument(new batasInput((byte)50).getKata(TPortioDalam));
        TBentuk.setDocument(new batasInput((byte)50).getKata(TBentuk));
        TCavumUteri.setDocument(new batasInput((byte)50).getKata(TCavumUteri));
        TUkuran.setDocument(new batasInput((byte)50).getKata(TUkuran));
        TAdnexaKanan.setDocument(new batasInput((byte)50).getKata(TAdnexaKanan));
        TAdnexaKiri.setDocument(new batasInput((byte)50).getKata(TAdnexaKiri));
        TCavumDouglas.setDocument(new batasInput((byte)50).getKata(TCavumDouglas));
        Catatan.setDocument(new batasInput((int)700).getKata(Catatan));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TampilkanData();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TampilkanData();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TampilkanData();
                    }
                }
            });
        }  
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(pasien.getTable().getSelectedRow()!= -1){                   
                        TCariPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),0).toString());
                    } 
                    TCariPasien.requestFocus();
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
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(dokter.getTable().getSelectedRow()!= -1){
                        if(TabRawat.getSelectedIndex()==0){
                            KdDok.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KdDok.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==2){
                            KdDok2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            TDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KdDok2.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==4){
                            KodeDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NamaDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KodeDokter.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==5){
                            KodeDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NamaDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KodeDokter.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==8){
                            KdDok3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            TDokter3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KdDok3.requestFocus();
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(petugas.getTable().getSelectedRow()!= -1){   
                        if(TabRawat.getSelectedIndex()==1){
                            kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            TPerawat.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            kdptg.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==2){
                            kdptg2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            TPerawat2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            kdptg2.requestFocus();
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
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
        
        soapterakhir.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(soapterakhir.getTable().getSelectedRow()!= -1){   
                    TKeluhan.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),2).toString());
                    TPemeriksaan.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),3).toString());
                    TPenilaian.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),4).toString());
                    TindakLanjut.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),5).toString());
                    Instruksi.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),6).toString());
//                    TEvaluasi.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),7).toString());
                    TKeluhan.requestFocus();                    
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
        
        panelDiagnosa1.TabRawat.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LCount.setText(panelDiagnosa1.getRecord()+"");
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        panelDiagnosa1.tbDiagnosaPasien.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                if(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),4).toString());
                } 
            }
        });
        
        panelDiagnosa1.tbDiagnosaPasien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),4).toString());
                }                
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        panelDiagnosa1.tbTindakanPasien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(panelDiagnosa1.tbTindakanPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),4).toString());
                }                
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        panelDiagnosa1.tbTindakanPasien.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                if(panelDiagnosa1.tbTindakanPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),4).toString());
                } 
            }
        });
        
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
        
        masters.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(masters.getTable().getSelectedRow()!= -1){
                        TKeluhan.setText(masters.getTable().getValueAt(masters.getTable().getSelectedRow(),3).toString());
                    }  
                    TKeluhan.requestFocus();
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
        
        masters.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        masters.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        mastero.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(mastero.getTable().getSelectedRow()!= -1){
                        TPemeriksaan.setText(mastero.getTable().getValueAt(mastero.getTable().getSelectedRow(),3).toString());
                    }  
                    TPemeriksaan.requestFocus();
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
        
        mastero.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        mastero.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        masterp.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(masterp.getTable().getSelectedRow()!= -1){
                        TindakLanjut.setText(masterp.getTable().getValueAt(masterp.getTable().getSelectedRow(),3).toString());
                    }  
                    TindakLanjut.requestFocus();
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
        
        masterp.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        masterp.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        ChkInput.setSelected(false);
        isForm(); 
        ChkInput1.setSelected(false);
        isForm2(); 
        ChkInput2.setSelected(false);
        isForm3(); 
        ChkInput3.setSelected(false);
        isForm4();
        ChkAccor.setSelected(true);
        isMenu(); 
        jam();
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifkanparsial=prop.getProperty("AKTIFKANBILLINGPARSIAL");
        } catch (Exception ex) {            
            aktifkanparsial="no";
        }
        
        try {
            psrekening=koneksi.prepareStatement(
                    "select set_akun_ralan.Suspen_Piutang_Tindakan_Ralan,set_akun_ralan.Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,set_akun_ralan.Beban_KSO_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_KSO_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Sarana_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Menejemen_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Menejemen_Tindakan_Ralan,set_akun_ralan.HPP_BHP_Tindakan_Ralan,set_akun_ralan.Persediaan_BHP_Tindakan_Ralan from set_akun_ralan");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Tindakan_Ralan=rsrekening.getString("Suspen_Piutang_Tindakan_Ralan");
                    Tindakan_Ralan=rsrekening.getString("Tindakan_Ralan");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Beban_Jasa_Medik_Paramedis_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Beban_KSO_Tindakan_Ralan=rsrekening.getString("Beban_KSO_Tindakan_Ralan");
                    Utang_KSO_Tindakan_Ralan=rsrekening.getString("Utang_KSO_Tindakan_Ralan");
                    Beban_Jasa_Sarana_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Sarana_Tindakan_Ralan");
                    Utang_Jasa_Sarana_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Sarana_Tindakan_Ralan");
                    Beban_Jasa_Menejemen_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Menejemen_Tindakan_Ralan");
                    Utang_Jasa_Menejemen_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Menejemen_Tindakan_Ralan");
                    HPP_BHP_Tindakan_Ralan=rsrekening.getString("HPP_BHP_Tindakan_Ralan");
                    Persediaan_BHP_Tindakan_Ralan=rsrekening.getString("Persediaan_BHP_Tindakan_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        try {
            psset_tarif=koneksi.prepareStatement("select set_tarif.poli_ralan,set_tarif.cara_bayar_ralan from set_tarif");
            try {
                rsset_tarif=psset_tarif.executeQuery();
                if(rsset_tarif.next()){
                    poli_ralan=rsset_tarif.getString("poli_ralan");
                    cara_bayar_ralan=rsset_tarif.getString("cara_bayar_ralan");
                }else{
                    poli_ralan="Yes";
                    cara_bayar_ralan="Yes";
                }  
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsset_tarif != null){
                    rsset_tarif.close();
                }
                if(psset_tarif != null){
                    psset_tarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        } 
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel24 = new widget.Label();
        TCariPasien = new widget.TextBox();
        btnPasien = new widget.Button();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnTambahTindakan = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass7 = new widget.panelisi();
        jLabel5 = new widget.Label();
        KdDok = new widget.TextBox();
        BtnSeekDokter = new widget.Button();
        TDokter = new widget.TextBox();
        TabRawatTindakanDokter = new javax.swing.JTabbedPane();
        Scroll6 = new widget.ScrollPane();
        tbTindakan = new widget.Table();
        Scroll = new widget.ScrollPane();
        tbRawatDr = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        panelGlass10 = new widget.panelisi();
        jLabel13 = new widget.Label();
        kdptg = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        TPerawat = new widget.TextBox();
        TabRawatTindakanPetugas = new javax.swing.JTabbedPane();
        Scroll7 = new widget.ScrollPane();
        tbTindakan2 = new widget.Table();
        Scroll8 = new widget.ScrollPane();
        tbRawatPr = new widget.Table();
        internalFrame4 = new widget.InternalFrame();
        panelGlass11 = new widget.panelisi();
        jLabel14 = new widget.Label();
        kdptg2 = new widget.TextBox();
        BtnSeekPetugas2 = new widget.Button();
        TPerawat2 = new widget.TextBox();
        jLabel12 = new widget.Label();
        KdDok2 = new widget.TextBox();
        TDokter2 = new widget.TextBox();
        BtnSeekDokter2 = new widget.Button();
        TabRawatTindakanDokterPetugas = new javax.swing.JTabbedPane();
        Scroll9 = new widget.ScrollPane();
        tbTindakan3 = new widget.Table();
        Scroll10 = new widget.ScrollPane();
        tbRawatDrPr = new widget.Table();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        panelGlass12 = new widget.panelisi();
        jLabel7 = new widget.Label();
        TSuhu = new widget.TextBox();
        jLabel4 = new widget.Label();
        TTensi = new widget.TextBox();
        jLabel16 = new widget.Label();
        TBerat = new widget.TextBox();
        TNadi = new widget.TextBox();
        jLabel18 = new widget.Label();
        cmbImun = new widget.ComboBox();
        jLabel25 = new widget.Label();
        TTinggi = new widget.TextBox();
        jLabel17 = new widget.Label();
        TAlergi = new widget.TextBox();
        jLabel20 = new widget.Label();
        TRespirasi = new widget.TextBox();
        jLabel22 = new widget.Label();
        TGCS = new widget.TextBox();
        scrollPane1 = new widget.ScrollPane();
        TKeluhan = new widget.TextArea();
        scrollPane2 = new widget.ScrollPane();
        TPemeriksaan = new widget.TextArea();
        scrollPane3 = new widget.ScrollPane();
        TPenilaian = new widget.TextArea();
        scrollPane5 = new widget.ScrollPane();
        TindakLanjut = new widget.TextArea();
        TSpo2 = new widget.TextBox();
        jLabel28 = new widget.Label();
        TRpd = new widget.TextBox();
        TRpk = new widget.TextBox();
        TRpo = new widget.TextBox();
        jLabel37 = new widget.Label();
        jLabel41 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel56 = new widget.Label();
        cmbKesadaran = new widget.ComboBox();
        BtnDokter1 = new widget.Button();
        BtnDokter2 = new widget.Button();
        BtnDokter3 = new widget.Button();
        BtnCppt = new widget.Button();
        BtnRiwayatLabRad = new widget.Button();
        scrollPane6 = new widget.ScrollPane();
        Operasi = new widget.TextArea();
        BtnEWS = new widget.Button();
        Asesmen = new widget.Button();
        Obat = new widget.Button();
        jLabel26 = new widget.Label();
        BtnEWS1 = new widget.Button();
        scrollPane7 = new widget.ScrollPane();
        Instruksi = new widget.TextArea();
        BtnTemplatePemeriksaan = new widget.Button();
        ICareNIK = new widget.Button();
        ICareNoKartu = new widget.Button();
        internalFrame9 = new widget.InternalFrame();
        Scroll12 = new widget.ScrollPane();
        tbPemeriksaanRM = new widget.Table();
        PanelInput4 = new javax.swing.JPanel();
        ChkInput4 = new widget.CekBox();
        panelGlass16 = new widget.panelisi();
        TAlergi1 = new widget.TextBox();
        scrollPane8 = new widget.ScrollPane();
        Anamnesa = new widget.TextArea();
        scrollPane9 = new widget.ScrollPane();
        PemeriksaanFisik = new widget.TextArea();
        scrollPane10 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        scrollPane11 = new widget.ScrollPane();
        Anjuran = new widget.TextArea();
        TRpd1 = new widget.TextBox();
        TRpk1 = new widget.TextBox();
        TRpo1 = new widget.TextBox();
        jLabel66 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        cmbKesadaran1 = new widget.ComboBox();
        BtnRiwayatRadLab = new widget.Button();
        scrollPane12 = new widget.ScrollPane();
        Operasi1 = new widget.TextArea();
        Asesmen1 = new widget.Button();
        Obat1 = new widget.Button();
        jLabel84 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Instruksi1 = new widget.TextArea();
        scrollPane14 = new widget.ScrollPane();
        Tatalaksana = new widget.TextArea();
        Asesmen2 = new widget.Button();
        scrollPane15 = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
        scrollPane16 = new widget.ScrollPane();
        Evaluasi = new widget.TextArea();
        jLabel85 = new widget.Label();
        Suspek = new widget.ComboBox();
        KetSuspek = new widget.TextBox();
        jLabel86 = new widget.Label();
        BtnProsedurKFR = new widget.Button();
        internalFrame6 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbPemeriksaanObstetri = new widget.Table();
        PanelInput1 = new javax.swing.JPanel();
        ChkInput1 = new widget.CekBox();
        panelGlass13 = new widget.panelisi();
        jLabel27 = new widget.Label();
        TTinggi_uteri = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        TLetak = new widget.TextBox();
        jLabel32 = new widget.Label();
        TKualitas_dtk = new widget.TextBox();
        jLabel33 = new widget.Label();
        cmbPanggul = new widget.ComboBox();
        jLabel34 = new widget.Label();
        TTebal = new widget.TextBox();
        TDenyut = new widget.TextBox();
        jLabel36 = new widget.Label();
        TDenominator = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        TKualitas_mnt = new widget.TextBox();
        jLabel40 = new widget.Label();
        cmbFeto = new widget.ComboBox();
        jLabel42 = new widget.Label();
        cmbJanin = new widget.ComboBox();
        cmbKetuban = new widget.ComboBox();
        TPortio = new widget.TextBox();
        jLabel43 = new widget.Label();
        TVulva = new widget.TextBox();
        cmbKontraksi = new widget.ComboBox();
        cmbAlbus = new widget.ComboBox();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel44 = new widget.Label();
        cmbFluksus = new widget.ComboBox();
        jLabel48 = new widget.Label();
        cmbDalam = new widget.ComboBox();
        jLabel49 = new widget.Label();
        TPembukaan = new widget.TextBox();
        TPenurunan = new widget.TextBox();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        cmbArah = new widget.ComboBox();
        jLabel52 = new widget.Label();
        label15 = new widget.Label();
        internalFrame7 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        tbPemeriksaanGinekologi = new widget.Table();
        PanelInput2 = new javax.swing.JPanel();
        ChkInput2 = new widget.CekBox();
        panelGlass14 = new widget.panelisi();
        jLabel35 = new widget.Label();
        TInspeksiVulva = new widget.TextBox();
        TAdnexaKanan = new widget.TextBox();
        jLabel57 = new widget.Label();
        cmbMobilitas = new widget.ComboBox();
        jLabel60 = new widget.Label();
        TInspekuloGine = new widget.TextBox();
        jLabel62 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel67 = new widget.Label();
        TPortioInspekulo = new widget.TextBox();
        TCavumUteri = new widget.TextBox();
        cmbFluorGine = new widget.ComboBox();
        TInspeksi = new widget.TextBox();
        cmbFluxusGine = new widget.ComboBox();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        TVulvaInspekulo = new widget.TextBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        TPortioDalam = new widget.TextBox();
        TBentuk = new widget.TextBox();
        jLabel78 = new widget.Label();
        cmbNyeriTekan = new widget.ComboBox();
        TSondage = new widget.TextBox();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        TAdnexaKiri = new widget.TextBox();
        jLabel81 = new widget.Label();
        TCavumDouglas = new widget.TextBox();
        TUkuran = new widget.TextBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        panelDiagnosa1 = new laporan.PanelDiagnosa();
        internalFrame8 = new widget.InternalFrame();
        PanelInput3 = new javax.swing.JPanel();
        ChkInput3 = new widget.CekBox();
        panelGlass15 = new widget.panelisi();
        jLabel55 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Catatan = new widget.TextArea();
        jLabel11 = new widget.Label();
        KdDok3 = new widget.TextBox();
        TDokter3 = new widget.TextBox();
        BtnSeekDokter3 = new widget.Button();
        Scroll11 = new widget.ScrollPane();
        tbCatatan = new widget.Table();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel23 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        KodeDokter = new widget.TextBox();
        label14 = new widget.Label();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        MRalan = new widget.PanelBiasa();
        BtnRiwayat = new widget.Button();
        BtnResepObat = new widget.Button();
        BtnCopyResep = new widget.Button();
        BtnTmpResep = new widget.Button();
        BtnResepLuar = new widget.Button();
        BtnObatBhp = new widget.Button();
        BtnInformasiObat = new widget.Button();
        BtnPermintaanLab = new widget.Button();
        BtnPermintaanRad = new widget.Button();
        BtnJadwalOperasi = new widget.Button();
        BtnAwalKeperawatan = new widget.Button();
        BtnAwalMedis = new widget.Button();
        BtnResumeRajal = new widget.Button();
        BtnSKDP = new widget.Button();
        BtnKamar = new widget.Button();
        BtnRujukKeluar = new widget.Button();
        BtnRujukInternal = new widget.Button();
        BtnCatatan = new widget.Button();
        BtnHasilEKG = new widget.Button();
        BtnMedicalCheckUp = new widget.Button();
        BtnBerkasDigital = new widget.Button();
        BtnIEMR = new widget.Button();
        BtnPermintaanKonsultasiMedik = new widget.Button();
        MIgd = new widget.PanelBiasa();
        BtnRiwayat1 = new widget.Button();
        BtnResepObat1 = new widget.Button();
        BtnCopyResep1 = new widget.Button();
        BtnTmpResep1 = new widget.Button();
        BtnResepLuar1 = new widget.Button();
        BtnObatBhp1 = new widget.Button();
        BtnInformasiObat2 = new widget.Button();
        BtnPermintaanLab1 = new widget.Button();
        BtnPermintaanRad1 = new widget.Button();
        BtnJadwalOperasi1 = new widget.Button();
        BtnTriaseIGD = new widget.Button();
        BtnResume = new widget.Button();
        BtnAwalKeperawatanIGD = new widget.Button();
        BtnAwalMedisIGD = new widget.Button();
        BtnKamar1 = new widget.Button();
        BtnRujukIGD = new widget.Button();
        BtnCatatanObservasiIGD = new widget.Button();
        BtnCatatanCekGDS = new widget.Button();
        BtnPemantauanPEWSAnak = new widget.Button();
        BtnPemantauanPEWSDewasa = new widget.Button();
        BtnEdukasiPasienKeluarga = new widget.Button();
        BtnMonitoringReaksiTranfusi = new widget.Button();
        BtnHasilEKG1 = new widget.Button();
        BtnHasilPemeriksaanUSG1 = new widget.Button();
        BtnBerkasDigital1 = new widget.Button();
        BtnIEMR1 = new widget.Button();
        MPd = new widget.PanelBiasa();
        BtnAwalMedisPenyakitDalam = new widget.Button();
        BtnAwalKeperawatan1 = new widget.Button();
        MSyaraf = new widget.PanelBiasa();
        BtnAwalKeperawatan5 = new widget.Button();
        BtnAwalMedis2 = new widget.Button();
        MObgyn = new widget.PanelBiasa();
        BtnAwalKeperawatanKandungan = new widget.Button();
        BtnAwalMedisKandungan = new widget.Button();
        BtnHasilPemeriksaanUSG = new widget.Button();
        MAnak = new widget.PanelBiasa();
        BtnAwalKeperawatanAnak = new widget.Button();
        BtnAwalMedisAnak = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhAnak = new widget.Button();
        MBedah = new widget.PanelBiasa();
        BtnAwalMedisBedah = new widget.Button();
        BtnAwalKeperawatan2 = new widget.Button();
        BtnSignInSebelumAnestesi = new widget.Button();
        MTht = new widget.PanelBiasa();
        BtnAwalMedisTHT = new widget.Button();
        BtnAwalKeperawatan3 = new widget.Button();
        MMata = new widget.PanelBiasa();
        BtnAwalMedisMata = new widget.Button();
        BtnAwalKeperawatan4 = new widget.Button();
        MGigi = new widget.PanelBiasa();
        BtnAwalMedis1 = new widget.Button();
        BtnAwalKeperawatanGigi = new widget.Button();
        BtnOdontogram = new widget.Button();
        MGizi = new widget.PanelBiasa();
        BtnAsuhanGizi = new widget.Button();
        BtnSkriningNutrisiLansia = new widget.Button();
        BtnSkriningNutrisiDewasa = new widget.Button();
        BtnSkriningNutrisiAnak = new widget.Button();
        BtnMonitoringAsuhanGizi = new widget.Button();
        BtnSkriningGiziLanjut = new widget.Button();
        MFisio = new widget.PanelBiasa();
        BtnAwalFisioterapi = new widget.Button();
        BtnRujukanFisio = new widget.Button();
        MGeriatri = new widget.PanelBiasa();
        BtnAwalKeperawatanGeriatri = new widget.Button();
        BtnAwalMedisGeriatri = new widget.Button();
        BtnPenilaianTambahanGeriatri = new widget.Button();
        MPsikiatri = new widget.PanelBiasa();
        BtnAwalKeperawatanPsikiatri = new widget.Button();
        BtnAwalMedisPsikiatri = new widget.Button();
        BtnPenilaianPsikolog = new widget.Button();
        Mall = new widget.PanelBiasa();
        BtnAwalMedisNeurologi = new widget.Button();
        BtnAwalMedisOrthopedi = new widget.Button();
        BtnPenilaianPasienTerminal = new widget.Button();
        BtnUjiFungsiKFR = new widget.Button();
        BtnChecklistPostOperasi = new widget.Button();
        BtnPenilaianTambahanBunuhDiri = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhLansia = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhDewasa = new widget.Button();
        BtnPenilaianPreOperasi = new widget.Button();
        BtnTimeOutSebelumInsisi = new widget.Button();
        BtnPenilaianTambahanMelarikanDiri = new widget.Button();
        BtnTransferAntarRuang = new widget.Button();
        BtnPenilaianTambahanPerilakuKekerasan = new widget.Button();
        BtnPenilaianPasienPenyakitMenular = new widget.Button();
        BtnSignOutSebelumMenutupLuka = new widget.Button();
        BtnChecklistPreOperasi = new widget.Button();
        BtnPenilaianKorbanKekerasan = new widget.Button();
        BtnRekonsiliasiObat = new widget.Button();
        BtnInacbgRajal = new widget.Button();
        BtnPenilaianPreAnestesi = new widget.Button();
        BtnKonselingFarmasi = new widget.Button();
        BtnIC = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Perawatan/Tindakan Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(95, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(87, 30));
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
        jLabel19.setPreferredSize(new java.awt.Dimension(64, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-11-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-11-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel24.setText("No.RM :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel24);

        TCariPasien.setName("TCariPasien"); // NOI18N
        TCariPasien.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass9.add(TCariPasien);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('6');
        btnPasien.setToolTipText("Alt+6");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        btnPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPasienKeyPressed(evt);
            }
        });
        panelGlass9.add(btnPasien);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
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
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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

        BtnTambahTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahTindakan.setMnemonic('3');
        BtnTambahTindakan.setToolTipText("Alt+3");
        BtnTambahTindakan.setName("BtnTambahTindakan"); // NOI18N
        BtnTambahTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahTindakanActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnTambahTindakan);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setForeground(new java.awt.Color(50, 50, 50));
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass7.setBorder(null);
        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(null);

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 10, 55, 23);

        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        panelGlass7.add(KdDok);
        KdDok.setBounds(58, 10, 146, 23);

        BtnSeekDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter.setMnemonic('4');
        BtnSeekDokter.setToolTipText("ALt+4");
        BtnSeekDokter.setName("BtnSeekDokter"); // NOI18N
        BtnSeekDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokterActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeekDokter);
        BtnSeekDokter.setBounds(749, 10, 28, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); // NOI18N
        panelGlass7.add(TDokter);
        TDokter.setBounds(206, 10, 540, 23);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        TabRawatTindakanDokter.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatTindakanDokter.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatTindakanDokter.setName("TabRawatTindakanDokter"); // NOI18N
        TabRawatTindakanDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatTindakanDokterMouseClicked(evt);
            }
        });

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbTindakan.setAutoCreateRowSorter(true);
        tbTindakan.setToolTipText("");
        tbTindakan.setName("tbTindakan"); // NOI18N
        tbTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakanKeyPressed(evt);
            }
        });
        Scroll6.setViewportView(tbTindakan);

        TabRawatTindakanDokter.addTab("Daftar Tindakan/Tagihan", Scroll6);

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbRawatDr.setAutoCreateRowSorter(true);
        tbRawatDr.setName("tbRawatDr"); // NOI18N
        tbRawatDr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrMouseClicked(evt);
            }
        });
        tbRawatDr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbRawatDrKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbRawatDr);

        TabRawatTindakanDokter.addTab("Tindakan Dilakukan", Scroll);

        internalFrame2.add(TabRawatTindakanDokter, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Penanganan Dokter", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(null);

        jLabel13.setText("Petugas :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelGlass10.add(jLabel13);
        jLabel13.setBounds(0, 10, 63, 23);

        kdptg.setHighlighter(null);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kdptgActionPerformed(evt);
            }
        });
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelGlass10.add(kdptg);
        kdptg.setBounds(66, 10, 146, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.setName("BtnSeekPetugas"); // NOI18N
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(749, 10, 28, 23);

        TPerawat.setEditable(false);
        TPerawat.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat.setHighlighter(null);
        TPerawat.setName("TPerawat"); // NOI18N
        TPerawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPerawatActionPerformed(evt);
            }
        });
        panelGlass10.add(TPerawat);
        TPerawat.setBounds(214, 10, 532, 23);

        internalFrame3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        TabRawatTindakanPetugas.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatTindakanPetugas.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatTindakanPetugas.setName("TabRawatTindakanPetugas"); // NOI18N
        TabRawatTindakanPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatTindakanPetugasMouseClicked(evt);
            }
        });

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbTindakan2.setAutoCreateRowSorter(true);
        tbTindakan2.setToolTipText("");
        tbTindakan2.setName("tbTindakan2"); // NOI18N
        tbTindakan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakan2KeyPressed(evt);
            }
        });
        Scroll7.setViewportView(tbTindakan2);

        TabRawatTindakanPetugas.addTab("Daftar Tindakan/Tagihan", Scroll7);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbRawatPr.setAutoCreateRowSorter(true);
        tbRawatPr.setName("tbRawatPr"); // NOI18N
        tbRawatPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatPrMouseClicked(evt);
            }
        });
        tbRawatPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbRawatPrKeyReleased(evt);
            }
        });
        Scroll8.setViewportView(tbRawatPr);

        TabRawatTindakanPetugas.addTab("Tindakan Dilakukan", Scroll8);

        internalFrame3.add(TabRawatTindakanPetugas, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Penanganan Petugas", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass11.setBorder(null);
        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 74));
        panelGlass11.setLayout(null);

        jLabel14.setText("Petugas :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelGlass11.add(jLabel14);
        jLabel14.setBounds(0, 40, 65, 23);

        kdptg2.setHighlighter(null);
        kdptg2.setName("kdptg2"); // NOI18N
        kdptg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptg2KeyPressed(evt);
            }
        });
        panelGlass11.add(kdptg2);
        kdptg2.setBounds(68, 40, 130, 23);

        BtnSeekPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas2.setMnemonic('5');
        BtnSeekPetugas2.setToolTipText("ALt+5");
        BtnSeekPetugas2.setName("BtnSeekPetugas2"); // NOI18N
        BtnSeekPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekPetugas2);
        BtnSeekPetugas2.setBounds(749, 40, 28, 23);

        TPerawat2.setEditable(false);
        TPerawat2.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat2.setHighlighter(null);
        TPerawat2.setName("TPerawat2"); // NOI18N
        panelGlass11.add(TPerawat2);
        TPerawat2.setBounds(200, 40, 546, 23);

        jLabel12.setText("Dokter :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass11.add(jLabel12);
        jLabel12.setBounds(0, 10, 65, 23);

        KdDok2.setHighlighter(null);
        KdDok2.setName("KdDok2"); // NOI18N
        KdDok2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok2KeyPressed(evt);
            }
        });
        panelGlass11.add(KdDok2);
        KdDok2.setBounds(68, 10, 130, 23);

        TDokter2.setEditable(false);
        TDokter2.setHighlighter(null);
        TDokter2.setName("TDokter2"); // NOI18N
        panelGlass11.add(TDokter2);
        TDokter2.setBounds(200, 10, 546, 23);

        BtnSeekDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter2.setMnemonic('4');
        BtnSeekDokter2.setToolTipText("ALt+4");
        BtnSeekDokter2.setName("BtnSeekDokter2"); // NOI18N
        BtnSeekDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekDokter2);
        BtnSeekDokter2.setBounds(749, 10, 28, 23);

        internalFrame4.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        TabRawatTindakanDokterPetugas.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatTindakanDokterPetugas.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatTindakanDokterPetugas.setName("TabRawatTindakanDokterPetugas"); // NOI18N
        TabRawatTindakanDokterPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatTindakanDokterPetugasMouseClicked(evt);
            }
        });

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbTindakan3.setAutoCreateRowSorter(true);
        tbTindakan3.setToolTipText("");
        tbTindakan3.setName("tbTindakan3"); // NOI18N
        tbTindakan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakan3KeyPressed(evt);
            }
        });
        Scroll9.setViewportView(tbTindakan3);

        TabRawatTindakanDokterPetugas.addTab("Daftar Tindakan/Tagihan", Scroll9);

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbRawatDrPr.setAutoCreateRowSorter(true);
        tbRawatDrPr.setName("tbRawatDrPr"); // NOI18N
        tbRawatDrPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrPrMouseClicked(evt);
            }
        });
        tbRawatDrPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbRawatDrPrKeyReleased(evt);
            }
        });
        Scroll10.setViewportView(tbRawatDrPr);

        TabRawatTindakanDokterPetugas.addTab("Tindakan Dilakukan", Scroll10);

        internalFrame4.add(TabRawatTindakanDokterPetugas, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Penanganan Dokter & Petugas", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPemeriksaan.setAutoCreateRowSorter(true);
        tbPemeriksaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaan.setName("tbPemeriksaan"); // NOI18N
        tbPemeriksaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanMouseClicked(evt);
            }
        });
        tbPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanKeyReleased(evt);
            }
        });
        Scroll3.setViewportView(tbPemeriksaan);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 400));
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

        panelGlass12.setBorder(null);
        panelGlass12.setName("panelGlass12"); // NOI18N
        panelGlass12.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass12.setLayout(null);

        jLabel7.setText("Suhu Badan(C) :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass12.add(jLabel7);
        jLabel7.setBounds(827, 10, 100, 23);

        TSuhu.setFocusTraversalPolicyProvider(true);
        TSuhu.setName("TSuhu"); // NOI18N
        TSuhu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TSuhuActionPerformed(evt);
            }
        });
        TSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSuhuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TSuhuKeyTyped(evt);
            }
        });
        panelGlass12.add(TSuhu);
        TSuhu.setBounds(940, 10, 60, 23);

        jLabel4.setText("Tensi :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass12.add(jLabel4);
        jLabel4.setBounds(1010, 10, 40, 23);

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
        panelGlass12.add(TTensi);
        TTensi.setBounds(1060, 10, 70, 23);

        jLabel16.setText("Berat(Kg) :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelGlass12.add(jLabel16);
        jLabel16.setBounds(1129, 10, 70, 23);

        TBerat.setFocusTraversalPolicyProvider(true);
        TBerat.setName("TBerat"); // NOI18N
        TBerat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBeratKeyPressed(evt);
            }
        });
        panelGlass12.add(TBerat);
        TBerat.setBounds(1200, 10, 60, 23);

        TNadi.setFocusTraversalPolicyProvider(true);
        TNadi.setName("TNadi"); // NOI18N
        TNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNadiKeyPressed(evt);
            }
        });
        panelGlass12.add(TNadi);
        TNadi.setBounds(1080, 50, 60, 23);

        jLabel18.setText("Nadi(/menit) :");
        jLabel18.setName("jLabel18"); // NOI18N
        panelGlass12.add(jLabel18);
        jLabel18.setBounds(1000, 50, 79, 23);

        cmbImun.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" }));
        cmbImun.setName("cmbImun"); // NOI18N
        cmbImun.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbImun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbImunKeyPressed(evt);
            }
        });
        panelGlass12.add(cmbImun);
        cmbImun.setBounds(1200, 50, 60, 23);

        jLabel25.setText("Imun Ke :");
        jLabel25.setName("jLabel25"); // NOI18N
        panelGlass12.add(jLabel25);
        jLabel25.setBounds(1150, 50, 50, 23);

        TTinggi.setHighlighter(null);
        TTinggi.setName("TTinggi"); // NOI18N
        TTinggi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTinggiKeyPressed(evt);
            }
        });
        panelGlass12.add(TTinggi);
        TTinggi.setBounds(940, 50, 60, 23);

        jLabel17.setText("Tinggi Badan(Cm) :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelGlass12.add(jLabel17);
        jLabel17.setBounds(830, 50, 97, 23);

        TAlergi.setHighlighter(null);
        TAlergi.setName("TAlergi"); // NOI18N
        TAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlergiKeyPressed(evt);
            }
        });
        panelGlass12.add(TAlergi);
        TAlergi.setBounds(450, 100, 360, 23);

        jLabel20.setText("Respirasi(/menit) :");
        jLabel20.setName("jLabel20"); // NOI18N
        panelGlass12.add(jLabel20);
        jLabel20.setBounds(830, 90, 97, 23);

        TRespirasi.setHighlighter(null);
        TRespirasi.setName("TRespirasi"); // NOI18N
        TRespirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRespirasiKeyPressed(evt);
            }
        });
        panelGlass12.add(TRespirasi);
        TRespirasi.setBounds(940, 90, 60, 23);

        jLabel22.setText("GCS(E,V,M) :");
        jLabel22.setName("jLabel22"); // NOI18N
        panelGlass12.add(jLabel22);
        jLabel22.setBounds(1010, 90, 70, 23);

        TGCS.setFocusTraversalPolicyProvider(true);
        TGCS.setName("TGCS"); // NOI18N
        TGCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TGCSKeyPressed(evt);
            }
        });
        panelGlass12.add(TGCS);
        TGCS.setBounds(1080, 90, 180, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("S (SUBJECTIVE)"));
        scrollPane1.setName("scrollPane1"); // NOI18N

        TKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKeluhan.setColumns(20);
        TKeluhan.setRows(5);
        TKeluhan.setName("TKeluhan"); // NOI18N
        TKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKeluhanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(TKeluhan);

        panelGlass12.add(scrollPane1);
        scrollPane1.setBounds(10, 10, 360, 80);

        scrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("O (OBJECTIVE)"));
        scrollPane2.setName("scrollPane2"); // NOI18N

        TPemeriksaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPemeriksaan.setColumns(20);
        TPemeriksaan.setRows(5);
        TPemeriksaan.setName("TPemeriksaan"); // NOI18N
        TPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPemeriksaanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(TPemeriksaan);

        panelGlass12.add(scrollPane2);
        scrollPane2.setBounds(10, 100, 360, 80);

        scrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("A (ASSESSMENT)"));
        scrollPane3.setName("scrollPane3"); // NOI18N

        TPenilaian.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPenilaian.setColumns(20);
        TPenilaian.setRows(5);
        TPenilaian.setName("TPenilaian"); // NOI18N
        TPenilaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenilaianKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(TPenilaian);

        panelGlass12.add(scrollPane3);
        scrollPane3.setBounds(10, 190, 360, 80);

        scrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder("P (PLAN)"));
        scrollPane5.setName("scrollPane5"); // NOI18N

        TindakLanjut.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TindakLanjut.setColumns(20);
        TindakLanjut.setRows(5);
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(TindakLanjut);

        panelGlass12.add(scrollPane5);
        scrollPane5.setBounds(10, 280, 360, 80);

        TSpo2.setHighlighter(null);
        TSpo2.setName("TSpo2"); // NOI18N
        TSpo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TSpo2ActionPerformed(evt);
            }
        });
        TSpo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSpo2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TSpo2KeyTyped(evt);
            }
        });
        panelGlass12.add(TSpo2);
        TSpo2.setBounds(940, 130, 60, 23);

        jLabel28.setText("SpO2 (%) :");
        jLabel28.setName("jLabel28"); // NOI18N
        panelGlass12.add(jLabel28);
        jLabel28.setBounds(830, 130, 97, 23);

        TRpd.setHighlighter(null);
        TRpd.setName("TRpd"); // NOI18N
        TRpd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRpdKeyPressed(evt);
            }
        });
        panelGlass12.add(TRpd);
        TRpd.setBounds(450, 10, 360, 23);

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
        panelGlass12.add(TRpk);
        TRpk.setBounds(450, 40, 360, 23);

        TRpo.setHighlighter(null);
        TRpo.setName("TRpo"); // NOI18N
        TRpo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TRpoActionPerformed(evt);
            }
        });
        TRpo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRpoKeyPressed(evt);
            }
        });
        panelGlass12.add(TRpo);
        TRpo.setBounds(450, 70, 360, 23);

        jLabel37.setText("RPD :");
        jLabel37.setName("jLabel37"); // NOI18N
        panelGlass12.add(jLabel37);
        jLabel37.setBounds(400, 10, 40, 23);

        jLabel41.setText("RPK :");
        jLabel41.setName("jLabel41"); // NOI18N
        panelGlass12.add(jLabel41);
        jLabel41.setBounds(400, 40, 40, 23);

        jLabel53.setText("RPO :");
        jLabel53.setName("jLabel53"); // NOI18N
        panelGlass12.add(jLabel53);
        jLabel53.setBounds(400, 70, 40, 20);

        jLabel56.setText("Kesadaran :");
        jLabel56.setName("jLabel56"); // NOI18N
        panelGlass12.add(jLabel56);
        jLabel56.setBounds(990, 130, 80, 23);

        cmbKesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis 15-14", "Apatis 13-12", "Delirium 11-10", "Somnolen 6-4", "Sopor 3", "Coma 0" }));
        cmbKesadaran.setName("cmbKesadaran"); // NOI18N
        cmbKesadaran.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbKesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKesadaranKeyPressed(evt);
            }
        });
        panelGlass12.add(cmbKesadaran);
        cmbKesadaran.setBounds(1080, 130, 180, 23);

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
        panelGlass12.add(BtnDokter1);
        BtnDokter1.setBounds(370, 20, 28, 23);

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
        panelGlass12.add(BtnDokter2);
        BtnDokter2.setBounds(370, 110, 28, 23);

        BtnDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter3.setMnemonic('2');
        BtnDokter3.setText("   Template Plan");
        BtnDokter3.setToolTipText("Alt+2");
        BtnDokter3.setName("BtnDokter3"); // NOI18N
        BtnDokter3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter3ActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnDokter3);
        BtnDokter3.setBounds(370, 290, 130, 23);

        BtnCppt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnCppt.setMnemonic('K');
        BtnCppt.setText(" CPPT");
        BtnCppt.setToolTipText("");
        BtnCppt.setName("BtnCppt"); // NOI18N
        BtnCppt.setPreferredSize(new java.awt.Dimension(160, 30));
        BtnCppt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCpptActionPerformed(evt);
            }
        });
        BtnCppt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCpptKeyPressed(evt);
            }
        });
        panelGlass12.add(BtnCppt);
        BtnCppt.setBounds(830, 170, 120, 30);

        BtnRiwayatLabRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnRiwayatLabRad.setMnemonic('R');
        BtnRiwayatLabRad.setText(" Riwayat Pemeriksaan Radiologi & Laboratotium");
        BtnRiwayatLabRad.setToolTipText("");
        BtnRiwayatLabRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayatLabRad.setName("BtnRiwayatLabRad"); // NOI18N
        BtnRiwayatLabRad.setPreferredSize(new java.awt.Dimension(160, 30));
        BtnRiwayatLabRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatLabRadActionPerformed(evt);
            }
        });
        BtnRiwayatLabRad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRiwayatLabRadKeyPressed(evt);
            }
        });
        panelGlass12.add(BtnRiwayatLabRad);
        BtnRiwayatLabRad.setBounds(830, 220, 360, 30);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder("Riwayat Operasi"));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Operasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Operasi.setColumns(20);
        Operasi.setRows(5);
        Operasi.setName("Operasi"); // NOI18N
        Operasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OperasiKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Operasi);

        panelGlass12.add(scrollPane6);
        scrollPane6.setBounds(450, 200, 360, 60);

        BtnEWS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnEWS.setMnemonic('K');
        BtnEWS.setText(" EWS");
        BtnEWS.setToolTipText("");
        BtnEWS.setName("BtnEWS"); // NOI18N
        BtnEWS.setPreferredSize(new java.awt.Dimension(160, 30));
        BtnEWS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEWSActionPerformed(evt);
            }
        });
        BtnEWS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEWSKeyPressed(evt);
            }
        });
        panelGlass12.add(BtnEWS);
        BtnEWS.setBounds(1090, 170, 160, 30);

        Asesmen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        Asesmen.setMnemonic('2');
        Asesmen.setToolTipText("Alt+2");
        Asesmen.setName("Asesmen"); // NOI18N
        Asesmen.setPreferredSize(new java.awt.Dimension(28, 23));
        Asesmen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsesmenActionPerformed(evt);
            }
        });
        panelGlass12.add(Asesmen);
        Asesmen.setBounds(370, 200, 28, 23);

        Obat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        Obat.setMnemonic('2');
        Obat.setText("   Input Resep");
        Obat.setToolTipText("Alt+2");
        Obat.setName("Obat"); // NOI18N
        Obat.setPreferredSize(new java.awt.Dimension(28, 23));
        Obat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ObatActionPerformed(evt);
            }
        });
        panelGlass12.add(Obat);
        Obat.setBounds(370, 320, 120, 30);

        jLabel26.setText("Alergi :");
        jLabel26.setName("jLabel26"); // NOI18N
        panelGlass12.add(jLabel26);
        jLabel26.setBounds(400, 100, 40, 23);

        BtnEWS1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnEWS1.setMnemonic('K');
        BtnEWS1.setText(" PEWS");
        BtnEWS1.setToolTipText("");
        BtnEWS1.setName("BtnEWS1"); // NOI18N
        BtnEWS1.setPreferredSize(new java.awt.Dimension(160, 30));
        BtnEWS1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEWS1ActionPerformed(evt);
            }
        });
        BtnEWS1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEWS1KeyPressed(evt);
            }
        });
        panelGlass12.add(BtnEWS1);
        BtnEWS1.setBounds(960, 170, 120, 30);

        scrollPane7.setBorder(javax.swing.BorderFactory.createTitledBorder("Instruksi"));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Instruksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Instruksi.setColumns(20);
        Instruksi.setRows(5);
        Instruksi.setName("Instruksi"); // NOI18N
        Instruksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InstruksiKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(Instruksi);

        panelGlass12.add(scrollPane7);
        scrollPane7.setBounds(450, 130, 360, 60);

        BtnTemplatePemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnTemplatePemeriksaan.setMnemonic('4');
        BtnTemplatePemeriksaan.setText("   Template Pemeriksaan Dokter");
        BtnTemplatePemeriksaan.setToolTipText("ALt+4");
        BtnTemplatePemeriksaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTemplatePemeriksaan.setName("BtnTemplatePemeriksaan"); // NOI18N
        BtnTemplatePemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTemplatePemeriksaanActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnTemplatePemeriksaan);
        BtnTemplatePemeriksaan.setBounds(545, 290, 240, 22);

        ICareNIK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ICareNIK.setMnemonic('C');
        ICareNIK.setText(" Cek Riwayat Perawatan ICare BPJS Via NIK");
        ICareNIK.setToolTipText("");
        ICareNIK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ICareNIK.setName("ICareNIK"); // NOI18N
        ICareNIK.setPreferredSize(new java.awt.Dimension(160, 30));
        ICareNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ICareNIKActionPerformed(evt);
            }
        });
        ICareNIK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ICareNIKKeyPressed(evt);
            }
        });
        panelGlass12.add(ICareNIK);
        ICareNIK.setBounds(830, 260, 360, 30);

        ICareNoKartu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ICareNoKartu.setMnemonic('C');
        ICareNoKartu.setText(" Cek Riwayat Perawatan ICare BPJS Via No. Peserta");
        ICareNoKartu.setToolTipText("");
        ICareNoKartu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ICareNoKartu.setName("ICareNoKartu"); // NOI18N
        ICareNoKartu.setPreferredSize(new java.awt.Dimension(160, 30));
        ICareNoKartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ICareNoKartuActionPerformed(evt);
            }
        });
        ICareNoKartu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ICareNoKartuKeyPressed(evt);
            }
        });
        panelGlass12.add(ICareNoKartu);
        ICareNoKartu.setBounds(830, 300, 360, 30);

        PanelInput.add(panelGlass12, java.awt.BorderLayout.CENTER);

        internalFrame5.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan", internalFrame5);

        internalFrame9.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame9.setBorder(null);
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll12.setName("Scroll12"); // NOI18N
        Scroll12.setOpaque(true);

        tbPemeriksaanRM.setAutoCreateRowSorter(true);
        tbPemeriksaanRM.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanRM.setName("tbPemeriksaanRM"); // NOI18N
        tbPemeriksaanRM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanRMMouseClicked(evt);
            }
        });
        tbPemeriksaanRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanRMKeyReleased(evt);
            }
        });
        Scroll12.setViewportView(tbPemeriksaanRM);

        internalFrame9.add(Scroll12, java.awt.BorderLayout.CENTER);

        PanelInput4.setName("PanelInput4"); // NOI18N
        PanelInput4.setOpaque(false);
        PanelInput4.setPreferredSize(new java.awt.Dimension(192, 400));
        PanelInput4.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput4.setMnemonic('I');
        ChkInput4.setText(".: Input Data");
        ChkInput4.setToolTipText("Alt+I");
        ChkInput4.setBorderPainted(true);
        ChkInput4.setBorderPaintedFlat(true);
        ChkInput4.setFocusable(false);
        ChkInput4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput4.setName("ChkInput4"); // NOI18N
        ChkInput4.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput4.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput4ActionPerformed(evt);
            }
        });
        PanelInput4.add(ChkInput4, java.awt.BorderLayout.PAGE_END);

        panelGlass16.setBorder(null);
        panelGlass16.setName("panelGlass16"); // NOI18N
        panelGlass16.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass16.setLayout(null);

        TAlergi1.setHighlighter(null);
        TAlergi1.setName("TAlergi1"); // NOI18N
        TAlergi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlergi1KeyPressed(evt);
            }
        });
        panelGlass16.add(TAlergi1);
        TAlergi1.setBounds(890, 150, 300, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createTitledBorder("ANAMNESA"));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Anamnesa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Anamnesa.setColumns(20);
        Anamnesa.setRows(5);
        Anamnesa.setName("Anamnesa"); // NOI18N
        Anamnesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesaKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(Anamnesa);

        panelGlass16.add(scrollPane8);
        scrollPane8.setBounds(10, 10, 360, 80);

        scrollPane9.setBorder(javax.swing.BorderFactory.createTitledBorder("PEMERIKSAAN FISIK DAN UJI FUNGSI"));
        scrollPane9.setName("scrollPane9"); // NOI18N

        PemeriksaanFisik.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanFisik.setColumns(20);
        PemeriksaanFisik.setRows(5);
        PemeriksaanFisik.setName("PemeriksaanFisik"); // NOI18N
        PemeriksaanFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanFisikKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(PemeriksaanFisik);

        panelGlass16.add(scrollPane9);
        scrollPane9.setBounds(10, 100, 360, 80);

        scrollPane10.setBorder(javax.swing.BorderFactory.createTitledBorder("DIAGNOSIS (ICD-10)"));
        scrollPane10.setName("scrollPane10"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(5);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(Diagnosis);

        panelGlass16.add(scrollPane10);
        scrollPane10.setBounds(410, 10, 360, 80);

        scrollPane11.setBorder(javax.swing.BorderFactory.createTitledBorder("ANJURAN"));
        scrollPane11.setName("scrollPane11"); // NOI18N

        Anjuran.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Anjuran.setColumns(20);
        Anjuran.setRows(5);
        Anjuran.setName("Anjuran"); // NOI18N
        Anjuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnjuranKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(Anjuran);

        panelGlass16.add(scrollPane11);
        scrollPane11.setBounds(10, 280, 360, 80);

        TRpd1.setHighlighter(null);
        TRpd1.setName("TRpd1"); // NOI18N
        TRpd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRpd1KeyPressed(evt);
            }
        });
        panelGlass16.add(TRpd1);
        TRpd1.setBounds(890, 60, 300, 23);

        TRpk1.setHighlighter(null);
        TRpk1.setName("TRpk1"); // NOI18N
        TRpk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TRpk1ActionPerformed(evt);
            }
        });
        TRpk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRpk1KeyPressed(evt);
            }
        });
        panelGlass16.add(TRpk1);
        TRpk1.setBounds(890, 90, 300, 23);

        TRpo1.setHighlighter(null);
        TRpo1.setName("TRpo1"); // NOI18N
        TRpo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TRpo1ActionPerformed(evt);
            }
        });
        TRpo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRpo1KeyPressed(evt);
            }
        });
        panelGlass16.add(TRpo1);
        TRpo1.setBounds(890, 120, 300, 23);

        jLabel66.setText("RPD :");
        jLabel66.setName("jLabel66"); // NOI18N
        panelGlass16.add(jLabel66);
        jLabel66.setBounds(840, 60, 40, 23);

        jLabel68.setText("RPK :");
        jLabel68.setName("jLabel68"); // NOI18N
        panelGlass16.add(jLabel68);
        jLabel68.setBounds(840, 90, 40, 23);

        jLabel69.setText("RPO :");
        jLabel69.setName("jLabel69"); // NOI18N
        panelGlass16.add(jLabel69);
        jLabel69.setBounds(840, 120, 40, 20);

        jLabel70.setText("Keterangan :");
        jLabel70.setName("jLabel70"); // NOI18N
        panelGlass16.add(jLabel70);
        jLabel70.setBounds(400, 300, 160, 23);

        cmbKesadaran1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis 15-14", "Apatis 13-12", "Delirium 11-10", "Somnolen 6-4", "Sopor 3", "Coma 0" }));
        cmbKesadaran1.setName("cmbKesadaran1"); // NOI18N
        cmbKesadaran1.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbKesadaran1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKesadaran1KeyPressed(evt);
            }
        });
        panelGlass16.add(cmbKesadaran1);
        cmbKesadaran1.setBounds(910, 20, 180, 23);

        BtnRiwayatRadLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnRiwayatRadLab.setMnemonic('K');
        BtnRiwayatRadLab.setText("   Riwayat Radiologi & Laboratorium");
        BtnRiwayatRadLab.setToolTipText("");
        BtnRiwayatRadLab.setName("BtnRiwayatRadLab"); // NOI18N
        BtnRiwayatRadLab.setPreferredSize(new java.awt.Dimension(160, 30));
        BtnRiwayatRadLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatRadLabActionPerformed(evt);
            }
        });
        BtnRiwayatRadLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRiwayatRadLabKeyPressed(evt);
            }
        });
        panelGlass16.add(BtnRiwayatRadLab);
        BtnRiwayatRadLab.setBounds(540, 330, 250, 30);

        scrollPane12.setBorder(javax.swing.BorderFactory.createTitledBorder("Riwayat Operasi"));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Operasi1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Operasi1.setColumns(20);
        Operasi1.setRows(5);
        Operasi1.setName("Operasi1"); // NOI18N
        Operasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Operasi1KeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Operasi1);

        panelGlass16.add(scrollPane12);
        scrollPane12.setBounds(840, 260, 360, 60);

        Asesmen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        Asesmen1.setMnemonic('2');
        Asesmen1.setToolTipText("Alt+2");
        Asesmen1.setName("Asesmen1"); // NOI18N
        Asesmen1.setPreferredSize(new java.awt.Dimension(28, 23));
        Asesmen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Asesmen1ActionPerformed(evt);
            }
        });
        panelGlass16.add(Asesmen1);
        Asesmen1.setBounds(770, 110, 28, 23);

        Obat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        Obat1.setMnemonic('2');
        Obat1.setText("   Input Resep");
        Obat1.setToolTipText("Alt+2");
        Obat1.setName("Obat1"); // NOI18N
        Obat1.setPreferredSize(new java.awt.Dimension(28, 23));
        Obat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Obat1ActionPerformed(evt);
            }
        });
        panelGlass16.add(Obat1);
        Obat1.setBounds(410, 330, 120, 30);

        jLabel84.setText("Alergi :");
        jLabel84.setName("jLabel84"); // NOI18N
        panelGlass16.add(jLabel84);
        jLabel84.setBounds(840, 150, 40, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createTitledBorder("Instruksi"));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Instruksi1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Instruksi1.setColumns(20);
        Instruksi1.setRows(5);
        Instruksi1.setName("Instruksi1"); // NOI18N
        Instruksi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Instruksi1KeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Instruksi1);

        panelGlass16.add(scrollPane13);
        scrollPane13.setBounds(840, 190, 360, 60);

        scrollPane14.setBorder(javax.swing.BorderFactory.createTitledBorder("TATA LAKSANA KFR (ICD-9)"));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Tatalaksana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tatalaksana.setColumns(20);
        Tatalaksana.setRows(5);
        Tatalaksana.setName("Tatalaksana"); // NOI18N
        Tatalaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalaksanaKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Tatalaksana);

        panelGlass16.add(scrollPane14);
        scrollPane14.setBounds(410, 100, 360, 80);

        Asesmen2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        Asesmen2.setMnemonic('2');
        Asesmen2.setToolTipText("Alt+2");
        Asesmen2.setName("Asesmen2"); // NOI18N
        Asesmen2.setPreferredSize(new java.awt.Dimension(28, 23));
        Asesmen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Asesmen2ActionPerformed(evt);
            }
        });
        panelGlass16.add(Asesmen2);
        Asesmen2.setBounds(770, 20, 28, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createTitledBorder("PEMERIKSAAN PENUNJANG"));
        scrollPane15.setName("scrollPane15"); // NOI18N

        PemeriksaanPenunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanPenunjang.setColumns(20);
        PemeriksaanPenunjang.setRows(5);
        PemeriksaanPenunjang.setName("PemeriksaanPenunjang"); // NOI18N
        PemeriksaanPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPenunjangKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(PemeriksaanPenunjang);

        panelGlass16.add(scrollPane15);
        scrollPane15.setBounds(10, 190, 360, 80);

        scrollPane16.setBorder(javax.swing.BorderFactory.createTitledBorder("EVALUASI"));
        scrollPane16.setName("scrollPane16"); // NOI18N

        Evaluasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Evaluasi.setColumns(20);
        Evaluasi.setRows(5);
        Evaluasi.setName("Evaluasi"); // NOI18N
        Evaluasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EvaluasiKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(Evaluasi);

        panelGlass16.add(scrollPane16);
        scrollPane16.setBounds(410, 190, 360, 70);

        jLabel85.setText("Kesadaran :");
        jLabel85.setName("jLabel85"); // NOI18N
        panelGlass16.add(jLabel85);
        jLabel85.setBounds(820, 20, 80, 23);

        Suspek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Suspek.setName("Suspek"); // NOI18N
        Suspek.setPreferredSize(new java.awt.Dimension(62, 28));
        Suspek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuspekKeyPressed(evt);
            }
        });
        panelGlass16.add(Suspek);
        Suspek.setBounds(570, 270, 180, 23);

        KetSuspek.setText("-");
        KetSuspek.setHighlighter(null);
        KetSuspek.setName("KetSuspek"); // NOI18N
        KetSuspek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSuspekKeyPressed(evt);
            }
        });
        panelGlass16.add(KetSuspek);
        KetSuspek.setBounds(570, 300, 180, 23);

        jLabel86.setText("Suspek Penyakit Akibat Kerja :");
        jLabel86.setName("jLabel86"); // NOI18N
        panelGlass16.add(jLabel86);
        jLabel86.setBounds(400, 270, 160, 23);

        BtnProsedurKFR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/2.png"))); // NOI18N
        BtnProsedurKFR.setText("   Form Uji Fungsi");
        BtnProsedurKFR.setToolTipText("");
        BtnProsedurKFR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnProsedurKFR.setName("BtnProsedurKFR"); // NOI18N
        BtnProsedurKFR.setPreferredSize(new java.awt.Dimension(160, 30));
        BtnProsedurKFR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnProsedurKFRActionPerformed(evt);
            }
        });
        BtnProsedurKFR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnProsedurKFRKeyPressed(evt);
            }
        });
        panelGlass16.add(BtnProsedurKFR);
        BtnProsedurKFR.setBounds(840, 330, 180, 30);

        PanelInput4.add(panelGlass16, java.awt.BorderLayout.CENTER);

        internalFrame9.add(PanelInput4, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Rehabilitasi", internalFrame9);

        internalFrame6.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbPemeriksaanObstetri.setAutoCreateRowSorter(true);
        tbPemeriksaanObstetri.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanObstetri.setName("tbPemeriksaanObstetri"); // NOI18N
        tbPemeriksaanObstetri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanObstetriMouseClicked(evt);
            }
        });
        tbPemeriksaanObstetri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanObstetriKeyReleased(evt);
            }
        });
        Scroll4.setViewportView(tbPemeriksaanObstetri);

        internalFrame6.add(Scroll4, java.awt.BorderLayout.CENTER);

        PanelInput1.setName("PanelInput1"); // NOI18N
        PanelInput1.setOpaque(false);
        PanelInput1.setPreferredSize(new java.awt.Dimension(192, 190));
        PanelInput1.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput1.setMnemonic('I');
        ChkInput1.setText(".: Input Data");
        ChkInput1.setToolTipText("Alt+I");
        ChkInput1.setBorderPainted(true);
        ChkInput1.setBorderPaintedFlat(true);
        ChkInput1.setFocusable(false);
        ChkInput1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput1.setName("ChkInput1"); // NOI18N
        ChkInput1.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput1ActionPerformed(evt);
            }
        });
        PanelInput1.add(ChkInput1, java.awt.BorderLayout.PAGE_END);

        panelGlass13.setName("panelGlass13"); // NOI18N
        panelGlass13.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass13.setLayout(null);

        jLabel27.setText("Tinggi Fundus Uteri (Cm) :");
        jLabel27.setName("jLabel27"); // NOI18N
        panelGlass13.add(jLabel27);
        jLabel27.setBounds(0, 10, 135, 23);

        TTinggi_uteri.setHighlighter(null);
        TTinggi_uteri.setName("TTinggi_uteri"); // NOI18N
        TTinggi_uteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTinggi_uteriKeyPressed(evt);
            }
        });
        panelGlass13.add(TTinggi_uteri);
        TTinggi_uteri.setBounds(138, 10, 50, 23);

        jLabel30.setText("Janin :");
        jLabel30.setName("jLabel30"); // NOI18N
        panelGlass13.add(jLabel30);
        jLabel30.setBounds(194, 10, 45, 23);

        jLabel31.setText("Letak :");
        jLabel31.setName("jLabel31"); // NOI18N
        panelGlass13.add(jLabel31);
        jLabel31.setBounds(375, 10, 40, 23);

        TLetak.setHighlighter(null);
        TLetak.setName("TLetak"); // NOI18N
        TLetak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TLetakKeyPressed(evt);
            }
        });
        panelGlass13.add(TLetak);
        TLetak.setBounds(418, 10, 50, 23);

        jLabel32.setText("Bagian Bawah Panggul :");
        jLabel32.setName("jLabel32"); // NOI18N
        panelGlass13.add(jLabel32);
        jLabel32.setBounds(486, 10, 130, 23);

        TKualitas_dtk.setFocusTraversalPolicyProvider(true);
        TKualitas_dtk.setName("TKualitas_dtk"); // NOI18N
        TKualitas_dtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKualitas_dtkKeyPressed(evt);
            }
        });
        panelGlass13.add(TKualitas_dtk);
        TKualitas_dtk.setBounds(402, 40, 50, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("detik");
        jLabel33.setName("jLabel33"); // NOI18N
        panelGlass13.add(jLabel33);
        jLabel33.setBounds(455, 40, 30, 23);

        cmbPanggul.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "5/5", "4/5", "3/5", "2/5", "1/5" }));
        cmbPanggul.setName("cmbPanggul"); // NOI18N
        cmbPanggul.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbPanggul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPanggulKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbPanggul);
        cmbPanggul.setBounds(619, 10, 62, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("/10 menit/");
        jLabel34.setName("jLabel34"); // NOI18N
        panelGlass13.add(jLabel34);
        jLabel34.setBounds(343, 40, 58, 23);

        TTebal.setHighlighter(null);
        TTebal.setName("TTebal"); // NOI18N
        TTebal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTebalKeyPressed(evt);
            }
        });
        panelGlass13.add(TTebal);
        TTebal.setBounds(709, 70, 50, 23);

        TDenyut.setHighlighter(null);
        TDenyut.setName("TDenyut"); // NOI18N
        TDenyut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDenyutKeyPressed(evt);
            }
        });
        panelGlass13.add(TDenyut);
        TDenyut.setBounds(876, 10, 50, 23);

        jLabel36.setText("Denyut Jantung Fetus (x/mnt) :");
        jLabel36.setName("jLabel36"); // NOI18N
        panelGlass13.add(jLabel36);
        jLabel36.setBounds(693, 10, 170, 23);

        TDenominator.setHighlighter(null);
        TDenominator.setName("TDenominator"); // NOI18N
        TDenominator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDenominatorKeyPressed(evt);
            }
        });
        panelGlass13.add(TDenominator);
        TDenominator.setBounds(548, 100, 125, 23);

        jLabel38.setText("Penurunan :");
        jLabel38.setName("jLabel38"); // NOI18N
        panelGlass13.add(jLabel38);
        jLabel38.setBounds(267, 100, 70, 23);

        jLabel39.setText("Imbang Feto-Pelvik :");
        jLabel39.setName("jLabel39"); // NOI18N
        panelGlass13.add(jLabel39);
        jLabel39.setBounds(673, 100, 110, 23);

        TKualitas_mnt.setHighlighter(null);
        TKualitas_mnt.setName("TKualitas_mnt"); // NOI18N
        TKualitas_mnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKualitas_mntKeyPressed(evt);
            }
        });
        panelGlass13.add(TKualitas_mnt);
        TKualitas_mnt.setBounds(293, 40, 50, 23);

        jLabel40.setText("Portio Inspekulo :");
        jLabel40.setName("jLabel40"); // NOI18N
        panelGlass13.add(jLabel40);
        jLabel40.setBounds(272, 70, 90, 23);

        cmbFeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Susp.CPD-FPD", "CPD-FPD" }));
        cmbFeto.setName("cmbFeto"); // NOI18N
        cmbFeto.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFeto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFetoKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbFeto);
        cmbFeto.setBounds(786, 100, 140, 23);

        jLabel42.setText("Denominator :");
        jLabel42.setName("jLabel42"); // NOI18N
        jLabel42.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass13.add(jLabel42);
        jLabel42.setBounds(470, 100, 75, 23);

        cmbJanin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tunggal", "Gemelli" }));
        cmbJanin.setName("cmbJanin"); // NOI18N
        cmbJanin.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbJanin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJaninKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbJanin);
        cmbJanin.setBounds(242, 10, 115, 23);

        cmbKetuban.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "+" }));
        cmbKetuban.setName("cmbKetuban"); // NOI18N
        cmbKetuban.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbKetuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKetubanKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbKetuban);
        cmbKetuban.setBounds(864, 40, 62, 23);

        TPortio.setFocusTraversalPolicyProvider(true);
        TPortio.setName("TPortio"); // NOI18N
        TPortio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioKeyPressed(evt);
            }
        });
        panelGlass13.add(TPortio);
        TPortio.setBounds(365, 70, 125, 23);

        jLabel43.setText("Kualitas (x/mnt) : ");
        jLabel43.setName("jLabel43"); // NOI18N
        panelGlass13.add(jLabel43);
        jLabel43.setBounds(193, 40, 100, 23);

        TVulva.setHighlighter(null);
        TVulva.setName("TVulva"); // NOI18N
        TVulva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TVulvaKeyPressed(evt);
            }
        });
        panelGlass13.add(TVulva);
        TVulva.setBounds(138, 70, 125, 23);

        cmbKontraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbKontraksi.setName("cmbKontraksi"); // NOI18N
        cmbKontraksi.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbKontraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKontraksiKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbKontraksi);
        cmbKontraksi.setBounds(138, 40, 62, 23);

        cmbAlbus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbAlbus.setName("cmbAlbus"); // NOI18N
        cmbAlbus.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbAlbus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAlbusKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbAlbus);
        cmbAlbus.setBounds(698, 40, 62, 23);

        jLabel45.setText("Kontraksi :");
        jLabel45.setName("jLabel45"); // NOI18N
        panelGlass13.add(jLabel45);
        jLabel45.setBounds(0, 40, 135, 23);

        jLabel46.setText("Fluor Albus :");
        jLabel46.setName("jLabel46"); // NOI18N
        panelGlass13.add(jLabel46);
        jLabel46.setBounds(623, 40, 72, 23);

        jLabel47.setText("Vulva/Vagina :");
        jLabel47.setName("jLabel47"); // NOI18N
        panelGlass13.add(jLabel47);
        jLabel47.setBounds(0, 70, 135, 23);

        jLabel44.setText("Fluksus :");
        jLabel44.setName("jLabel44"); // NOI18N
        panelGlass13.add(jLabel44);
        jLabel44.setBounds(488, 40, 58, 23);

        cmbFluksus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluksus.setName("cmbFluksus"); // NOI18N
        cmbFluksus.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluksus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluksusKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbFluksus);
        cmbFluksus.setBounds(549, 40, 62, 23);

        jLabel48.setText("Dalam :");
        jLabel48.setName("jLabel48"); // NOI18N
        panelGlass13.add(jLabel48);
        jLabel48.setBounds(500, 70, 47, 23);

        cmbDalam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kenyal", "Lunak" }));
        cmbDalam.setName("cmbDalam"); // NOI18N
        cmbDalam.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbDalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDalamKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbDalam);
        cmbDalam.setBounds(550, 70, 95, 23);

        jLabel49.setText("Pembukaan :");
        jLabel49.setName("jLabel49"); // NOI18N
        panelGlass13.add(jLabel49);
        jLabel49.setBounds(0, 100, 135, 23);

        TPembukaan.setHighlighter(null);
        TPembukaan.setName("TPembukaan"); // NOI18N
        TPembukaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPembukaanKeyPressed(evt);
            }
        });
        panelGlass13.add(TPembukaan);
        TPembukaan.setBounds(138, 100, 125, 23);

        TPenurunan.setHighlighter(null);
        TPenurunan.setName("TPenurunan"); // NOI18N
        TPenurunan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenurunanKeyPressed(evt);
            }
        });
        panelGlass13.add(TPenurunan);
        TPenurunan.setBounds(340, 100, 125, 23);

        jLabel50.setText("Tebal/cm :");
        jLabel50.setName("jLabel50"); // NOI18N
        panelGlass13.add(jLabel50);
        jLabel50.setBounds(646, 70, 60, 23);

        jLabel51.setText("Selaput Ketuban :");
        jLabel51.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel51.setName("jLabel51"); // NOI18N
        panelGlass13.add(jLabel51);
        jLabel51.setBounds(771, 40, 90, 23);

        cmbArah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Depan", "Axial", "Belakang" }));
        cmbArah.setName("cmbArah"); // NOI18N
        cmbArah.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbArah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbArahKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbArah);
        cmbArah.setBounds(806, 70, 120, 23);

        jLabel52.setText("Arah :");
        jLabel52.setName("jLabel52"); // NOI18N
        panelGlass13.add(jLabel52);
        jLabel52.setBounds(763, 70, 40, 23);

        label15.setText("Dokter :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass13.add(label15);
        label15.setBounds(75, 130, 60, 23);

        PanelInput1.add(panelGlass13, java.awt.BorderLayout.CENTER);

        internalFrame6.add(PanelInput1, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Obstetri", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Scroll5KeyPressed(evt);
            }
        });

        tbPemeriksaanGinekologi.setAutoCreateRowSorter(true);
        tbPemeriksaanGinekologi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanGinekologi.setName("tbPemeriksaanGinekologi"); // NOI18N
        tbPemeriksaanGinekologi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanGinekologiMouseClicked(evt);
            }
        });
        tbPemeriksaanGinekologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanGinekologiKeyReleased(evt);
            }
        });
        Scroll5.setViewportView(tbPemeriksaanGinekologi);

        internalFrame7.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelInput2.setName("PanelInput2"); // NOI18N
        PanelInput2.setOpaque(false);
        PanelInput2.setPreferredSize(new java.awt.Dimension(192, 260));
        PanelInput2.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput2.setMnemonic('I');
        ChkInput2.setText(".: Input Data");
        ChkInput2.setToolTipText("Alt+I");
        ChkInput2.setBorderPainted(true);
        ChkInput2.setBorderPaintedFlat(true);
        ChkInput2.setFocusable(false);
        ChkInput2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput2.setName("ChkInput2"); // NOI18N
        ChkInput2.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput2ActionPerformed(evt);
            }
        });
        PanelInput2.add(ChkInput2, java.awt.BorderLayout.PAGE_END);

        panelGlass14.setName("panelGlass14"); // NOI18N
        panelGlass14.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass14.setLayout(null);

        jLabel35.setText("Inspeksi :");
        jLabel35.setName("jLabel35"); // NOI18N
        panelGlass14.add(jLabel35);
        jLabel35.setBounds(0, 10, 70, 23);

        TInspeksiVulva.setHighlighter(null);
        TInspeksiVulva.setName("TInspeksiVulva"); // NOI18N
        TInspeksiVulva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspeksiVulvaKeyPressed(evt);
            }
        });
        panelGlass14.add(TInspeksiVulva);
        TInspeksiVulva.setBounds(118, 40, 223, 23);

        TAdnexaKanan.setHighlighter(null);
        TAdnexaKanan.setName("TAdnexaKanan"); // NOI18N
        TAdnexaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAdnexaKananKeyPressed(evt);
            }
        });
        panelGlass14.add(TAdnexaKanan);
        TAdnexaKanan.setBounds(510, 120, 355, 23);

        jLabel57.setText("Fluor Albus :");
        jLabel57.setName("jLabel57"); // NOI18N
        panelGlass14.add(jLabel57);
        jLabel57.setBounds(206, 100, 70, 23);

        cmbMobilitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbMobilitas.setName("cmbMobilitas"); // NOI18N
        cmbMobilitas.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbMobilitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMobilitasKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbMobilitas);
        cmbMobilitas.setBounds(803, 60, 62, 23);

        jLabel60.setText("Sondage :");
        jLabel60.setName("jLabel60"); // NOI18N
        jLabel60.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel60);
        jLabel60.setBounds(20, 190, 95, 23);

        TInspekuloGine.setHighlighter(null);
        TInspekuloGine.setName("TInspekuloGine"); // NOI18N
        TInspekuloGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspekuloGineKeyPressed(evt);
            }
        });
        panelGlass14.add(TInspekuloGine);
        TInspekuloGine.setBounds(73, 70, 268, 23);

        jLabel62.setText("Vulva/Uretra/Vagina :");
        jLabel62.setName("jLabel62"); // NOI18N
        panelGlass14.add(jLabel62);
        jLabel62.setBounds(0, 40, 115, 23);

        jLabel64.setText("Inspekulo :");
        jLabel64.setName("jLabel64"); // NOI18N
        panelGlass14.add(jLabel64);
        jLabel64.setBounds(0, 70, 70, 23);

        jLabel67.setText("Fluxus :");
        jLabel67.setName("jLabel67"); // NOI18N
        panelGlass14.add(jLabel67);
        jLabel67.setBounds(0, 100, 115, 23);

        TPortioInspekulo.setHighlighter(null);
        TPortioInspekulo.setName("TPortioInspekulo"); // NOI18N
        TPortioInspekulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioInspekuloKeyPressed(evt);
            }
        });
        panelGlass14.add(TPortioInspekulo);
        TPortioInspekulo.setBounds(118, 160, 223, 23);

        TCavumUteri.setHighlighter(null);
        TCavumUteri.setName("TCavumUteri"); // NOI18N
        TCavumUteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCavumUteriKeyPressed(evt);
            }
        });
        panelGlass14.add(TCavumUteri);
        TCavumUteri.setBounds(468, 60, 272, 23);

        cmbFluorGine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluorGine.setName("cmbFluorGine"); // NOI18N
        cmbFluorGine.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluorGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluorGineKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbFluorGine);
        cmbFluorGine.setBounds(279, 100, 62, 23);

        TInspeksi.setHighlighter(null);
        TInspeksi.setName("TInspeksi"); // NOI18N
        TInspeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspeksiKeyPressed(evt);
            }
        });
        panelGlass14.add(TInspeksi);
        TInspeksi.setBounds(73, 10, 268, 23);

        cmbFluxusGine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluxusGine.setName("cmbFluxusGine"); // NOI18N
        cmbFluxusGine.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluxusGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluxusGineKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbFluxusGine);
        cmbFluxusGine.setBounds(118, 100, 62, 23);

        jLabel71.setText("Adnexa/Parametrium :");
        jLabel71.setName("jLabel71"); // NOI18N
        jLabel71.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel71);
        jLabel71.setBounds(340, 120, 125, 23);

        jLabel72.setText("Portio :");
        jLabel72.setName("jLabel72"); // NOI18N
        jLabel72.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel72);
        jLabel72.setBounds(20, 160, 95, 23);

        jLabel73.setText("Vulva/Vagina :");
        jLabel73.setName("jLabel73"); // NOI18N
        jLabel73.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel73);
        jLabel73.setBounds(20, 130, 95, 23);

        jLabel74.setText("Pemeriksaan Dalam :");
        jLabel74.setName("jLabel74"); // NOI18N
        jLabel74.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel74);
        jLabel74.setBounds(340, 10, 110, 23);

        jLabel75.setText("Kanan :");
        jLabel75.setName("jLabel75"); // NOI18N
        jLabel75.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel75);
        jLabel75.setBounds(437, 120, 70, 23);

        TVulvaInspekulo.setHighlighter(null);
        TVulvaInspekulo.setName("TVulvaInspekulo"); // NOI18N
        TVulvaInspekulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TVulvaInspekuloKeyPressed(evt);
            }
        });
        panelGlass14.add(TVulvaInspekulo);
        TVulvaInspekulo.setBounds(118, 130, 223, 23);

        jLabel76.setText(", Bentuk :");
        jLabel76.setName("jLabel76"); // NOI18N
        jLabel76.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel76);
        jLabel76.setBounds(640, 30, 50, 23);

        jLabel77.setText(", Mobilitas :");
        jLabel77.setName("jLabel77"); // NOI18N
        jLabel77.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel77);
        jLabel77.setBounds(740, 60, 60, 23);

        TPortioDalam.setHighlighter(null);
        TPortioDalam.setName("TPortioDalam"); // NOI18N
        TPortioDalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioDalamKeyPressed(evt);
            }
        });
        panelGlass14.add(TPortioDalam);
        TPortioDalam.setBounds(468, 30, 173, 23);

        TBentuk.setHighlighter(null);
        TBentuk.setName("TBentuk"); // NOI18N
        TBentuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBentukKeyPressed(evt);
            }
        });
        panelGlass14.add(TBentuk);
        TBentuk.setBounds(693, 30, 173, 23);

        jLabel78.setText("Ukuran :");
        jLabel78.setName("jLabel78"); // NOI18N
        jLabel78.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel78);
        jLabel78.setBounds(437, 90, 70, 23);

        cmbNyeriTekan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbNyeriTekan.setName("cmbNyeriTekan"); // NOI18N
        cmbNyeriTekan.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbNyeriTekan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbNyeriTekanKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbNyeriTekan);
        cmbNyeriTekan.setBounds(803, 90, 62, 23);

        TSondage.setHighlighter(null);
        TSondage.setName("TSondage"); // NOI18N
        TSondage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSondageKeyPressed(evt);
            }
        });
        panelGlass14.add(TSondage);
        TSondage.setBounds(118, 190, 223, 23);

        jLabel79.setText("Cavum Uteri :");
        jLabel79.setName("jLabel79"); // NOI18N
        jLabel79.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel79);
        jLabel79.setBounds(340, 60, 125, 23);

        jLabel80.setText("Kiri :");
        jLabel80.setName("jLabel80"); // NOI18N
        jLabel80.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel80);
        jLabel80.setBounds(437, 150, 70, 23);

        TAdnexaKiri.setHighlighter(null);
        TAdnexaKiri.setName("TAdnexaKiri"); // NOI18N
        TAdnexaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAdnexaKiriKeyPressed(evt);
            }
        });
        panelGlass14.add(TAdnexaKiri);
        TAdnexaKiri.setBounds(510, 150, 355, 23);

        jLabel81.setText("Cavum Douglas :");
        jLabel81.setName("jLabel81"); // NOI18N
        jLabel81.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel81);
        jLabel81.setBounds(340, 180, 125, 23);

        TCavumDouglas.setHighlighter(null);
        TCavumDouglas.setName("TCavumDouglas"); // NOI18N
        TCavumDouglas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCavumDouglasKeyPressed(evt);
            }
        });
        panelGlass14.add(TCavumDouglas);
        TCavumDouglas.setBounds(468, 180, 397, 23);

        TUkuran.setHighlighter(null);
        TUkuran.setName("TUkuran"); // NOI18N
        TUkuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUkuranKeyPressed(evt);
            }
        });
        panelGlass14.add(TUkuran);
        TUkuran.setBounds(510, 90, 217, 23);

        jLabel82.setText(", Nyeri Tekan :");
        jLabel82.setName("jLabel82"); // NOI18N
        jLabel82.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel82);
        jLabel82.setBounds(724, 90, 76, 23);

        jLabel83.setText("Portio :");
        jLabel83.setName("jLabel83"); // NOI18N
        jLabel83.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel83);
        jLabel83.setBounds(340, 30, 125, 23);

        PanelInput2.add(panelGlass14, java.awt.BorderLayout.CENTER);

        internalFrame7.add(PanelInput2, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Ginekologi", internalFrame7);

        panelDiagnosa1.setBorder(null);
        panelDiagnosa1.setName("panelDiagnosa1"); // NOI18N
        TabRawat.addTab("Diagnosa", panelDiagnosa1);

        internalFrame8.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        PanelInput3.setName("PanelInput3"); // NOI18N
        PanelInput3.setOpaque(false);
        PanelInput3.setPreferredSize(new java.awt.Dimension(192, 140));
        PanelInput3.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput3.setMnemonic('I');
        ChkInput3.setText(".: Input Data");
        ChkInput3.setToolTipText("Alt+I");
        ChkInput3.setBorderPainted(true);
        ChkInput3.setBorderPaintedFlat(true);
        ChkInput3.setFocusable(false);
        ChkInput3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput3.setName("ChkInput3"); // NOI18N
        ChkInput3.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput3.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput3ActionPerformed(evt);
            }
        });
        PanelInput3.add(ChkInput3, java.awt.BorderLayout.PAGE_END);

        panelGlass15.setName("panelGlass15"); // NOI18N
        panelGlass15.setPreferredSize(new java.awt.Dimension(44, 104));
        panelGlass15.setLayout(null);

        jLabel55.setText("Catatan :");
        jLabel55.setName("jLabel55"); // NOI18N
        panelGlass15.add(jLabel55);
        jLabel55.setBounds(0, 40, 60, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Catatan.setBorder(null);
        Catatan.setColumns(20);
        Catatan.setRows(5);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Catatan);

        panelGlass15.add(scrollPane4);
        scrollPane4.setBounds(64, 40, 713, 68);

        jLabel11.setText("Dokter :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelGlass15.add(jLabel11);
        jLabel11.setBounds(0, 10, 60, 23);

        KdDok3.setHighlighter(null);
        KdDok3.setName("KdDok3"); // NOI18N
        KdDok3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok3KeyPressed(evt);
            }
        });
        panelGlass15.add(KdDok3);
        KdDok3.setBounds(64, 10, 146, 23);

        TDokter3.setEditable(false);
        TDokter3.setHighlighter(null);
        TDokter3.setName("TDokter3"); // NOI18N
        panelGlass15.add(TDokter3);
        TDokter3.setBounds(212, 10, 534, 23);

        BtnSeekDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter3.setMnemonic('4');
        BtnSeekDokter3.setToolTipText("ALt+4");
        BtnSeekDokter3.setName("BtnSeekDokter3"); // NOI18N
        BtnSeekDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter3ActionPerformed(evt);
            }
        });
        panelGlass15.add(BtnSeekDokter3);
        BtnSeekDokter3.setBounds(749, 10, 28, 23);

        PanelInput3.add(panelGlass15, java.awt.BorderLayout.CENTER);

        internalFrame8.add(PanelInput3, java.awt.BorderLayout.PAGE_START);

        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbCatatan.setAutoCreateRowSorter(true);
        tbCatatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbCatatan.setName("tbCatatan"); // NOI18N
        tbCatatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCatatanMouseClicked(evt);
            }
        });
        tbCatatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbCatatanKeyReleased(evt);
            }
        });
        Scroll11.setViewportView(tbCatatan);

        internalFrame8.add(Scroll11, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Catatan Dokter", internalFrame8);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(260, 43));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TNoRwMouseClicked(evt);
            }
        });
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 125, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(201, 10, 80, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(283, 10, 270, 23);

        jLabel23.setText("Tanggal :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(554, 10, 60, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-11-2024" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(617, 10, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(711, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(776, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(841, 10, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(906, 10, 23, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        NamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(1220, 10, 320, 23);

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
        BtnDokter.setBounds(1540, 10, 28, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setName("KodeDokter"); // NOI18N
        KodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KodeDokterActionPerformed(evt);
            }
        });
        KodeDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterKeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(1090, 10, 130, 23);

        label14.setText("Nama Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(960, 10, 120, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setMaximumSize(new java.awt.Dimension(300, 700));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(300, 53));
        PanelAccor.setLayout(new java.awt.BorderLayout());

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 255, 248)));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.EAST);

        ScrollMenu.setBorder(null);
        ScrollMenu.setName("ScrollMenu"); // NOI18N
        ScrollMenu.setOpaque(true);
        ScrollMenu.setPreferredSize(new java.awt.Dimension(150, 383));

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(150, 483));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(500, 700));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(37, 24));
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(280, 700));

        MRalan.setBackground(new java.awt.Color(255, 255, 255));
        MRalan.setBorder(null);
        MRalan.setName("MRalan"); // NOI18N
        MRalan.setPreferredSize(new java.awt.Dimension(150, 483));
        MRalan.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRiwayat.setText("Riwayat Pasien");
        BtnRiwayat.setFocusPainted(false);
        BtnRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayat.setName("BtnRiwayat"); // NOI18N
        BtnRiwayat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRiwayat.setRoundRect(false);
        BtnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatActionPerformed(evt);
            }
        });
        MRalan.add(BtnRiwayat);

        BtnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepObat.setText("Input Resep Dokter");
        BtnResepObat.setFocusPainted(false);
        BtnResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepObat.setName("BtnResepObat"); // NOI18N
        BtnResepObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResepObat.setRoundRect(false);
        BtnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepObatActionPerformed(evt);
            }
        });
        MRalan.add(BtnResepObat);

        BtnCopyResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCopyResep.setText("Copy Resep");
        BtnCopyResep.setFocusPainted(false);
        BtnCopyResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCopyResep.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCopyResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCopyResep.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCopyResep.setName("BtnCopyResep"); // NOI18N
        BtnCopyResep.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCopyResep.setRoundRect(false);
        BtnCopyResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCopyResepActionPerformed(evt);
            }
        });
        MRalan.add(BtnCopyResep);

        BtnTmpResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTmpResep.setText("Template Resep");
        BtnTmpResep.setFocusPainted(false);
        BtnTmpResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTmpResep.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTmpResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTmpResep.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTmpResep.setName("BtnTmpResep"); // NOI18N
        BtnTmpResep.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTmpResep.setRoundRect(false);
        BtnTmpResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTmpResepActionPerformed(evt);
            }
        });
        BtnTmpResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTmpResepKeyPressed(evt);
            }
        });
        MRalan.add(BtnTmpResep);

        BtnResepLuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepLuar.setText("Resep Luar");
        BtnResepLuar.setFocusPainted(false);
        BtnResepLuar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepLuar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepLuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepLuar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepLuar.setName("BtnResepLuar"); // NOI18N
        BtnResepLuar.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResepLuar.setRoundRect(false);
        BtnResepLuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepLuarActionPerformed(evt);
            }
        });
        MRalan.add(BtnResepLuar);

        BtnObatBhp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnObatBhp.setText("Data Obat & BHP");
        BtnObatBhp.setFocusPainted(false);
        BtnObatBhp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnObatBhp.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObatBhp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnObatBhp.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnObatBhp.setName("BtnObatBhp"); // NOI18N
        BtnObatBhp.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnObatBhp.setRoundRect(false);
        BtnObatBhp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObatBhpActionPerformed(evt);
            }
        });
        MRalan.add(BtnObatBhp);

        BtnInformasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInformasiObat.setText("Informasi Obat");
        BtnInformasiObat.setFocusPainted(false);
        BtnInformasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInformasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInformasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInformasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInformasiObat.setName("BtnInformasiObat"); // NOI18N
        BtnInformasiObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInformasiObat.setRoundRect(false);
        BtnInformasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInformasiObatActionPerformed(evt);
            }
        });
        MRalan.add(BtnInformasiObat);

        BtnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanLab.setText("Permintaan Lab");
        BtnPermintaanLab.setFocusPainted(false);
        BtnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanLab.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanLab.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanLab.setName("BtnPermintaanLab"); // NOI18N
        BtnPermintaanLab.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanLab.setRoundRect(false);
        BtnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanLabActionPerformed(evt);
            }
        });
        MRalan.add(BtnPermintaanLab);

        BtnPermintaanRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanRad.setText("Permintaan Rad");
        BtnPermintaanRad.setFocusPainted(false);
        BtnPermintaanRad.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanRad.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanRad.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanRad.setName("BtnPermintaanRad"); // NOI18N
        BtnPermintaanRad.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanRad.setRoundRect(false);
        BtnPermintaanRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanRadActionPerformed(evt);
            }
        });
        MRalan.add(BtnPermintaanRad);

        BtnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnJadwalOperasi.setText("Jadwal Operasi");
        BtnJadwalOperasi.setFocusPainted(false);
        BtnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnJadwalOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnJadwalOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnJadwalOperasi.setName("BtnJadwalOperasi"); // NOI18N
        BtnJadwalOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnJadwalOperasi.setRoundRect(false);
        BtnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJadwalOperasiActionPerformed(evt);
            }
        });
        MRalan.add(BtnJadwalOperasi);

        BtnAwalKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatan.setText("Awal Keperawatan Ralan");
        BtnAwalKeperawatan.setFocusPainted(false);
        BtnAwalKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatan.setName("BtnAwalKeperawatan"); // NOI18N
        BtnAwalKeperawatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatan.setRoundRect(false);
        BtnAwalKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanActionPerformed(evt);
            }
        });
        MRalan.add(BtnAwalKeperawatan);

        BtnAwalMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedis.setText("Awal Medis Ralan");
        BtnAwalMedis.setFocusPainted(false);
        BtnAwalMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedis.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedis.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedis.setName("BtnAwalMedis"); // NOI18N
        BtnAwalMedis.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedis.setRoundRect(false);
        BtnAwalMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisActionPerformed(evt);
            }
        });
        MRalan.add(BtnAwalMedis);

        BtnResumeRajal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResumeRajal.setText("Resume Pasien Ralan");
        BtnResumeRajal.setFocusPainted(false);
        BtnResumeRajal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResumeRajal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResumeRajal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResumeRajal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResumeRajal.setName("BtnResumeRajal"); // NOI18N
        BtnResumeRajal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResumeRajal.setRoundRect(false);
        BtnResumeRajal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResumeRajalActionPerformed(evt);
            }
        });
        MRalan.add(BtnResumeRajal);

        BtnSKDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSKDP.setText("Surat Kontrol");
        BtnSKDP.setFocusPainted(false);
        BtnSKDP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSKDP.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSKDP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSKDP.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSKDP.setName("BtnSKDP"); // NOI18N
        BtnSKDP.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSKDP.setRoundRect(false);
        BtnSKDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSKDPActionPerformed(evt);
            }
        });
        MRalan.add(BtnSKDP);

        BtnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKamar.setText("Kamar Inap");
        BtnKamar.setFocusPainted(false);
        BtnKamar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKamar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKamar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKamar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKamar.setName("BtnKamar"); // NOI18N
        BtnKamar.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKamar.setRoundRect(false);
        BtnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKamarActionPerformed(evt);
            }
        });
        MRalan.add(BtnKamar);

        BtnRujukKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRujukKeluar.setText("Rujuk Keluar Ralan");
        BtnRujukKeluar.setFocusPainted(false);
        BtnRujukKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRujukKeluar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRujukKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRujukKeluar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRujukKeluar.setName("BtnRujukKeluar"); // NOI18N
        BtnRujukKeluar.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRujukKeluar.setRoundRect(false);
        BtnRujukKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRujukKeluarActionPerformed(evt);
            }
        });
        MRalan.add(BtnRujukKeluar);

        BtnRujukInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRujukInternal.setText("Rujuk Internal");
        BtnRujukInternal.setFocusPainted(false);
        BtnRujukInternal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRujukInternal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRujukInternal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRujukInternal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRujukInternal.setName("BtnRujukInternal"); // NOI18N
        BtnRujukInternal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRujukInternal.setRoundRect(false);
        BtnRujukInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRujukInternalActionPerformed(evt);
            }
        });
        MRalan.add(BtnRujukInternal);

        BtnCatatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatan.setText("Catatan Pasien");
        BtnCatatan.setFocusPainted(false);
        BtnCatatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatan.setName("BtnCatatan"); // NOI18N
        BtnCatatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatan.setRoundRect(false);
        BtnCatatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanActionPerformed(evt);
            }
        });
        MRalan.add(BtnCatatan);

        BtnHasilEKG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnHasilEKG.setText("Hasil EKG");
        BtnHasilEKG.setFocusPainted(false);
        BtnHasilEKG.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilEKG.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilEKG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilEKG.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilEKG.setName("BtnHasilEKG"); // NOI18N
        BtnHasilEKG.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilEKG.setRoundRect(false);
        BtnHasilEKG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilEKGActionPerformed(evt);
            }
        });
        MRalan.add(BtnHasilEKG);

        BtnMedicalCheckUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMedicalCheckUp.setText("Medical Check Up");
        BtnMedicalCheckUp.setFocusPainted(false);
        BtnMedicalCheckUp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMedicalCheckUp.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMedicalCheckUp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMedicalCheckUp.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMedicalCheckUp.setName("BtnMedicalCheckUp"); // NOI18N
        BtnMedicalCheckUp.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMedicalCheckUp.setRoundRect(false);
        BtnMedicalCheckUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMedicalCheckUpActionPerformed(evt);
            }
        });
        MRalan.add(BtnMedicalCheckUp);

        BtnBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnBerkasDigital.setText("Berkas Digital");
        BtnBerkasDigital.setFocusPainted(false);
        BtnBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnBerkasDigital.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnBerkasDigital.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnBerkasDigital.setName("BtnBerkasDigital"); // NOI18N
        BtnBerkasDigital.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnBerkasDigital.setRoundRect(false);
        BtnBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBerkasDigitalActionPerformed(evt);
            }
        });
        MRalan.add(BtnBerkasDigital);

        BtnIEMR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnIEMR.setText("Riwayat Antar Faskes");
        BtnIEMR.setFocusPainted(false);
        BtnIEMR.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnIEMR.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnIEMR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnIEMR.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnIEMR.setName("BtnIEMR"); // NOI18N
        BtnIEMR.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnIEMR.setRoundRect(false);
        BtnIEMR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIEMRActionPerformed(evt);
            }
        });
        MRalan.add(BtnIEMR);

        BtnPermintaanKonsultasiMedik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanKonsultasiMedik.setText("Konsultasi Medik");
        BtnPermintaanKonsultasiMedik.setFocusPainted(false);
        BtnPermintaanKonsultasiMedik.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanKonsultasiMedik.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanKonsultasiMedik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanKonsultasiMedik.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanKonsultasiMedik.setName("BtnPermintaanKonsultasiMedik"); // NOI18N
        BtnPermintaanKonsultasiMedik.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanKonsultasiMedik.setRoundRect(false);
        BtnPermintaanKonsultasiMedik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanKonsultasiMedikActionPerformed(evt);
            }
        });
        MRalan.add(BtnPermintaanKonsultasiMedik);

        jTabbedPane1.addTab("Rawat Jalan", MRalan);

        MIgd.setBackground(new java.awt.Color(255, 255, 255));
        MIgd.setBorder(null);
        MIgd.setName("MIgd"); // NOI18N
        MIgd.setPreferredSize(new java.awt.Dimension(150, 483));
        MIgd.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnRiwayat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRiwayat1.setText("Riwayat Pasien");
        BtnRiwayat1.setFocusPainted(false);
        BtnRiwayat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayat1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayat1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayat1.setName("BtnRiwayat1"); // NOI18N
        BtnRiwayat1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRiwayat1.setRoundRect(false);
        BtnRiwayat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayat1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnRiwayat1);

        BtnResepObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepObat1.setText("Input Resep Dokter");
        BtnResepObat1.setFocusPainted(false);
        BtnResepObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepObat1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepObat1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepObat1.setName("BtnResepObat1"); // NOI18N
        BtnResepObat1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResepObat1.setRoundRect(false);
        BtnResepObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepObat1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnResepObat1);

        BtnCopyResep1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCopyResep1.setText("Copy Resep");
        BtnCopyResep1.setFocusPainted(false);
        BtnCopyResep1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCopyResep1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCopyResep1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCopyResep1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCopyResep1.setName("BtnCopyResep1"); // NOI18N
        BtnCopyResep1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCopyResep1.setRoundRect(false);
        BtnCopyResep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCopyResep1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnCopyResep1);

        BtnTmpResep1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTmpResep1.setText("Template Resep");
        BtnTmpResep1.setFocusPainted(false);
        BtnTmpResep1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTmpResep1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTmpResep1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTmpResep1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTmpResep1.setName("BtnTmpResep1"); // NOI18N
        BtnTmpResep1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTmpResep1.setRoundRect(false);
        BtnTmpResep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTmpResep1ActionPerformed(evt);
            }
        });
        BtnTmpResep1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTmpResep1KeyPressed(evt);
            }
        });
        MIgd.add(BtnTmpResep1);

        BtnResepLuar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepLuar1.setText("Resep Luar");
        BtnResepLuar1.setFocusPainted(false);
        BtnResepLuar1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepLuar1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepLuar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepLuar1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepLuar1.setName("BtnResepLuar1"); // NOI18N
        BtnResepLuar1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResepLuar1.setRoundRect(false);
        BtnResepLuar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepLuar1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnResepLuar1);

        BtnObatBhp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnObatBhp1.setText("Data Obat & BHP");
        BtnObatBhp1.setFocusPainted(false);
        BtnObatBhp1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnObatBhp1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObatBhp1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnObatBhp1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnObatBhp1.setName("BtnObatBhp1"); // NOI18N
        BtnObatBhp1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnObatBhp1.setRoundRect(false);
        BtnObatBhp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObatBhp1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnObatBhp1);

        BtnInformasiObat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInformasiObat2.setText("Informasi Obat");
        BtnInformasiObat2.setFocusPainted(false);
        BtnInformasiObat2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInformasiObat2.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInformasiObat2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInformasiObat2.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInformasiObat2.setName("BtnInformasiObat2"); // NOI18N
        BtnInformasiObat2.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInformasiObat2.setRoundRect(false);
        BtnInformasiObat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInformasiObat2ActionPerformed(evt);
            }
        });
        MIgd.add(BtnInformasiObat2);

        BtnPermintaanLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanLab1.setText("Permintaan Lab");
        BtnPermintaanLab1.setFocusPainted(false);
        BtnPermintaanLab1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanLab1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanLab1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanLab1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanLab1.setName("BtnPermintaanLab1"); // NOI18N
        BtnPermintaanLab1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanLab1.setRoundRect(false);
        BtnPermintaanLab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanLab1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnPermintaanLab1);

        BtnPermintaanRad1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanRad1.setText("Permintaan Rad");
        BtnPermintaanRad1.setFocusPainted(false);
        BtnPermintaanRad1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanRad1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanRad1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanRad1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanRad1.setName("BtnPermintaanRad1"); // NOI18N
        BtnPermintaanRad1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanRad1.setRoundRect(false);
        BtnPermintaanRad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanRad1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnPermintaanRad1);

        BtnJadwalOperasi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnJadwalOperasi1.setText("Jadwal Operasi");
        BtnJadwalOperasi1.setFocusPainted(false);
        BtnJadwalOperasi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnJadwalOperasi1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnJadwalOperasi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnJadwalOperasi1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnJadwalOperasi1.setName("BtnJadwalOperasi1"); // NOI18N
        BtnJadwalOperasi1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnJadwalOperasi1.setRoundRect(false);
        BtnJadwalOperasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJadwalOperasi1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnJadwalOperasi1);

        BtnTriaseIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTriaseIGD.setText("Triase IGD");
        BtnTriaseIGD.setFocusPainted(false);
        BtnTriaseIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTriaseIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTriaseIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTriaseIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTriaseIGD.setName("BtnTriaseIGD"); // NOI18N
        BtnTriaseIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTriaseIGD.setRoundRect(false);
        BtnTriaseIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTriaseIGDActionPerformed(evt);
            }
        });
        MIgd.add(BtnTriaseIGD);

        BtnResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResume.setText("Resume Pasien IGD");
        BtnResume.setFocusPainted(false);
        BtnResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResume.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResume.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResume.setName("BtnResume"); // NOI18N
        BtnResume.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResume.setRoundRect(false);
        BtnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResumeActionPerformed(evt);
            }
        });
        MIgd.add(BtnResume);

        BtnAwalKeperawatanIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanIGD.setText("Awal Keperawatan IGD");
        BtnAwalKeperawatanIGD.setFocusPainted(false);
        BtnAwalKeperawatanIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanIGD.setName("BtnAwalKeperawatanIGD"); // NOI18N
        BtnAwalKeperawatanIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanIGD.setRoundRect(false);
        BtnAwalKeperawatanIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanIGDActionPerformed(evt);
            }
        });
        MIgd.add(BtnAwalKeperawatanIGD);

        BtnAwalMedisIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisIGD.setText("Awal Medis IGD");
        BtnAwalMedisIGD.setFocusPainted(false);
        BtnAwalMedisIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisIGD.setName("BtnAwalMedisIGD"); // NOI18N
        BtnAwalMedisIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisIGD.setRoundRect(false);
        BtnAwalMedisIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisIGDActionPerformed(evt);
            }
        });
        MIgd.add(BtnAwalMedisIGD);

        BtnKamar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKamar1.setText("Kamar Inap");
        BtnKamar1.setFocusPainted(false);
        BtnKamar1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKamar1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKamar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKamar1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKamar1.setName("BtnKamar1"); // NOI18N
        BtnKamar1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKamar1.setRoundRect(false);
        BtnKamar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKamar1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnKamar1);

        BtnRujukIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRujukIGD.setText("Rujuk Keluar IGD");
        BtnRujukIGD.setFocusPainted(false);
        BtnRujukIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRujukIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRujukIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRujukIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRujukIGD.setName("BtnRujukIGD"); // NOI18N
        BtnRujukIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRujukIGD.setRoundRect(false);
        BtnRujukIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRujukIGDActionPerformed(evt);
            }
        });
        MIgd.add(BtnRujukIGD);

        BtnCatatanObservasiIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanObservasiIGD.setText("Observasi IGD");
        BtnCatatanObservasiIGD.setFocusPainted(false);
        BtnCatatanObservasiIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanObservasiIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiIGD.setName("BtnCatatanObservasiIGD"); // NOI18N
        BtnCatatanObservasiIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiIGD.setRoundRect(false);
        BtnCatatanObservasiIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanObservasiIGDActionPerformed(evt);
            }
        });
        MIgd.add(BtnCatatanObservasiIGD);

        BtnCatatanCekGDS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanCekGDS.setText("GDS IGD");
        BtnCatatanCekGDS.setFocusPainted(false);
        BtnCatatanCekGDS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanCekGDS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanCekGDS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanCekGDS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanCekGDS.setName("BtnCatatanCekGDS"); // NOI18N
        BtnCatatanCekGDS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanCekGDS.setRoundRect(false);
        BtnCatatanCekGDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanCekGDSActionPerformed(evt);
            }
        });
        MIgd.add(BtnCatatanCekGDS);

        BtnPemantauanPEWSAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanPEWSAnak.setText("PEWS IGD & Ralan");
        BtnPemantauanPEWSAnak.setFocusPainted(false);
        BtnPemantauanPEWSAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanPEWSAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanPEWSAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanPEWSAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanPEWSAnak.setName("BtnPemantauanPEWSAnak"); // NOI18N
        BtnPemantauanPEWSAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanPEWSAnak.setRoundRect(false);
        BtnPemantauanPEWSAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanPEWSAnakActionPerformed(evt);
            }
        });
        MIgd.add(BtnPemantauanPEWSAnak);

        BtnPemantauanPEWSDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanPEWSDewasa.setText("EWS IGD & Ralan");
        BtnPemantauanPEWSDewasa.setFocusPainted(false);
        BtnPemantauanPEWSDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanPEWSDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanPEWSDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanPEWSDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanPEWSDewasa.setName("BtnPemantauanPEWSDewasa"); // NOI18N
        BtnPemantauanPEWSDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanPEWSDewasa.setRoundRect(false);
        BtnPemantauanPEWSDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanPEWSDewasaActionPerformed(evt);
            }
        });
        MIgd.add(BtnPemantauanPEWSDewasa);

        BtnEdukasiPasienKeluarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnEdukasiPasienKeluarga.setText("Edukasi Pasien & Keluarga");
        BtnEdukasiPasienKeluarga.setFocusPainted(false);
        BtnEdukasiPasienKeluarga.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnEdukasiPasienKeluarga.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnEdukasiPasienKeluarga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnEdukasiPasienKeluarga.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnEdukasiPasienKeluarga.setName("BtnEdukasiPasienKeluarga"); // NOI18N
        BtnEdukasiPasienKeluarga.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnEdukasiPasienKeluarga.setRoundRect(false);
        BtnEdukasiPasienKeluarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdukasiPasienKeluargaActionPerformed(evt);
            }
        });
        MIgd.add(BtnEdukasiPasienKeluarga);

        BtnMonitoringReaksiTranfusi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMonitoringReaksiTranfusi.setText("Monitoring Reaksi Tranfusi");
        BtnMonitoringReaksiTranfusi.setFocusPainted(false);
        BtnMonitoringReaksiTranfusi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMonitoringReaksiTranfusi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringReaksiTranfusi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringReaksiTranfusi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringReaksiTranfusi.setName("BtnMonitoringReaksiTranfusi"); // NOI18N
        BtnMonitoringReaksiTranfusi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMonitoringReaksiTranfusi.setRoundRect(false);
        BtnMonitoringReaksiTranfusi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringReaksiTranfusiActionPerformed(evt);
            }
        });
        MIgd.add(BtnMonitoringReaksiTranfusi);

        BtnHasilEKG1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnHasilEKG1.setText("Hasil EKG");
        BtnHasilEKG1.setFocusPainted(false);
        BtnHasilEKG1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilEKG1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilEKG1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilEKG1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilEKG1.setName("BtnHasilEKG1"); // NOI18N
        BtnHasilEKG1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilEKG1.setRoundRect(false);
        BtnHasilEKG1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilEKG1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnHasilEKG1);

        BtnHasilPemeriksaanUSG1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnHasilPemeriksaanUSG1.setText("Hasil Pemeriksaan USG");
        BtnHasilPemeriksaanUSG1.setFocusPainted(false);
        BtnHasilPemeriksaanUSG1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSG1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSG1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSG1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSG1.setName("BtnHasilPemeriksaanUSG1"); // NOI18N
        BtnHasilPemeriksaanUSG1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSG1.setRoundRect(false);
        BtnHasilPemeriksaanUSG1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilPemeriksaanUSG1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnHasilPemeriksaanUSG1);

        BtnBerkasDigital1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnBerkasDigital1.setText("Berkas Digital");
        BtnBerkasDigital1.setFocusPainted(false);
        BtnBerkasDigital1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnBerkasDigital1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnBerkasDigital1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnBerkasDigital1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnBerkasDigital1.setName("BtnBerkasDigital1"); // NOI18N
        BtnBerkasDigital1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnBerkasDigital1.setRoundRect(false);
        BtnBerkasDigital1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBerkasDigital1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnBerkasDigital1);

        BtnIEMR1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnIEMR1.setText("Riwayat Antar Faskes");
        BtnIEMR1.setFocusPainted(false);
        BtnIEMR1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnIEMR1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnIEMR1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnIEMR1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnIEMR1.setName("BtnIEMR1"); // NOI18N
        BtnIEMR1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnIEMR1.setRoundRect(false);
        BtnIEMR1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIEMR1ActionPerformed(evt);
            }
        });
        MIgd.add(BtnIEMR1);

        jTabbedPane1.addTab("IGD", MIgd);

        MPd.setBackground(new java.awt.Color(255, 255, 255));
        MPd.setBorder(null);
        MPd.setName("MPd"); // NOI18N
        MPd.setPreferredSize(new java.awt.Dimension(150, 483));
        MPd.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalMedisPenyakitDalam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisPenyakitDalam.setText("Awal Medis Penyakit Dalam");
        BtnAwalMedisPenyakitDalam.setFocusPainted(false);
        BtnAwalMedisPenyakitDalam.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisPenyakitDalam.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisPenyakitDalam.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisPenyakitDalam.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisPenyakitDalam.setName("BtnAwalMedisPenyakitDalam"); // NOI18N
        BtnAwalMedisPenyakitDalam.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisPenyakitDalam.setRoundRect(false);
        BtnAwalMedisPenyakitDalam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisPenyakitDalamActionPerformed(evt);
            }
        });
        MPd.add(BtnAwalMedisPenyakitDalam);

        BtnAwalKeperawatan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatan1.setText("Awal Keperawatan Penyakit Dalam");
        BtnAwalKeperawatan1.setFocusPainted(false);
        BtnAwalKeperawatan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatan1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatan1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatan1.setName("BtnAwalKeperawatan1"); // NOI18N
        BtnAwalKeperawatan1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatan1.setRoundRect(false);
        BtnAwalKeperawatan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatan1ActionPerformed(evt);
            }
        });
        MPd.add(BtnAwalKeperawatan1);

        jTabbedPane1.addTab("PD", MPd);

        MSyaraf.setBackground(new java.awt.Color(255, 255, 255));
        MSyaraf.setBorder(null);
        MSyaraf.setName("MSyaraf"); // NOI18N
        MSyaraf.setPreferredSize(new java.awt.Dimension(150, 483));
        MSyaraf.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalKeperawatan5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatan5.setText("Awal Keperawatan Syaraf");
        BtnAwalKeperawatan5.setFocusPainted(false);
        BtnAwalKeperawatan5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatan5.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatan5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatan5.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatan5.setName("BtnAwalKeperawatan5"); // NOI18N
        BtnAwalKeperawatan5.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatan5.setRoundRect(false);
        BtnAwalKeperawatan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatan5ActionPerformed(evt);
            }
        });
        MSyaraf.add(BtnAwalKeperawatan5);

        BtnAwalMedis2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedis2.setText("Awal Medis Syaraf");
        BtnAwalMedis2.setFocusPainted(false);
        BtnAwalMedis2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedis2.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedis2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedis2.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedis2.setName("BtnAwalMedis2"); // NOI18N
        BtnAwalMedis2.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedis2.setRoundRect(false);
        BtnAwalMedis2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedis2ActionPerformed(evt);
            }
        });
        MSyaraf.add(BtnAwalMedis2);

        jTabbedPane1.addTab("Syaraf", MSyaraf);

        MObgyn.setBackground(new java.awt.Color(255, 255, 255));
        MObgyn.setBorder(null);
        MObgyn.setName("MObgyn"); // NOI18N
        MObgyn.setPreferredSize(new java.awt.Dimension(140, 700));
        MObgyn.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalKeperawatanKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanKandungan.setText("Awal Keperawatan Kandungan");
        BtnAwalKeperawatanKandungan.setFocusPainted(false);
        BtnAwalKeperawatanKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanKandungan.setName("BtnAwalKeperawatanKandungan"); // NOI18N
        BtnAwalKeperawatanKandungan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanKandungan.setRoundRect(false);
        BtnAwalKeperawatanKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanKandunganActionPerformed(evt);
            }
        });
        MObgyn.add(BtnAwalKeperawatanKandungan);

        BtnAwalMedisKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisKandungan.setText("Awal Medis Kandungan");
        BtnAwalMedisKandungan.setFocusPainted(false);
        BtnAwalMedisKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisKandungan.setName("BtnAwalMedisKandungan"); // NOI18N
        BtnAwalMedisKandungan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisKandungan.setRoundRect(false);
        BtnAwalMedisKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisKandunganActionPerformed(evt);
            }
        });
        MObgyn.add(BtnAwalMedisKandungan);

        BtnHasilPemeriksaanUSG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnHasilPemeriksaanUSG.setText("Hasil Pemeriksaan USG");
        BtnHasilPemeriksaanUSG.setFocusPainted(false);
        BtnHasilPemeriksaanUSG.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSG.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSG.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSG.setName("BtnHasilPemeriksaanUSG"); // NOI18N
        BtnHasilPemeriksaanUSG.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSG.setRoundRect(false);
        BtnHasilPemeriksaanUSG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilPemeriksaanUSGActionPerformed(evt);
            }
        });
        MObgyn.add(BtnHasilPemeriksaanUSG);

        jTabbedPane1.addTab("Obgyn", MObgyn);

        MAnak.setBackground(new java.awt.Color(255, 255, 255));
        MAnak.setBorder(null);
        MAnak.setName("MAnak"); // NOI18N
        MAnak.setPreferredSize(new java.awt.Dimension(150, 483));
        MAnak.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalKeperawatanAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanAnak.setText("Awal Keperawatan Bayi/Anak");
        BtnAwalKeperawatanAnak.setFocusPainted(false);
        BtnAwalKeperawatanAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanAnak.setName("BtnAwalKeperawatanAnak"); // NOI18N
        BtnAwalKeperawatanAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanAnak.setRoundRect(false);
        BtnAwalKeperawatanAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanAnakActionPerformed(evt);
            }
        });
        MAnak.add(BtnAwalKeperawatanAnak);

        BtnAwalMedisAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisAnak.setText("Awal Medis Bayi/Anak");
        BtnAwalMedisAnak.setFocusPainted(false);
        BtnAwalMedisAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisAnak.setName("BtnAwalMedisAnak"); // NOI18N
        BtnAwalMedisAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisAnak.setRoundRect(false);
        BtnAwalMedisAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisAnakActionPerformed(evt);
            }
        });
        MAnak.add(BtnAwalMedisAnak);

        BtnPenilaianLanjutanRisikoJatuhAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhAnak.setText("Lanjutan Risiko Jatuh Anak");
        BtnPenilaianLanjutanRisikoJatuhAnak.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhAnak.setName("BtnPenilaianLanjutanRisikoJatuhAnak"); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhAnak.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhAnakActionPerformed(evt);
            }
        });
        MAnak.add(BtnPenilaianLanjutanRisikoJatuhAnak);

        jTabbedPane1.addTab("Anak", MAnak);

        MBedah.setBackground(new java.awt.Color(255, 255, 255));
        MBedah.setBorder(null);
        MBedah.setName("MBedah"); // NOI18N
        MBedah.setPreferredSize(new java.awt.Dimension(150, 483));
        MBedah.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalMedisBedah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisBedah.setText("Awal Medis Bedah");
        BtnAwalMedisBedah.setFocusPainted(false);
        BtnAwalMedisBedah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisBedah.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisBedah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisBedah.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisBedah.setName("BtnAwalMedisBedah"); // NOI18N
        BtnAwalMedisBedah.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisBedah.setRoundRect(false);
        BtnAwalMedisBedah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisBedahActionPerformed(evt);
            }
        });
        MBedah.add(BtnAwalMedisBedah);

        BtnAwalKeperawatan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatan2.setText("Awal Keperawatan Bedah");
        BtnAwalKeperawatan2.setFocusPainted(false);
        BtnAwalKeperawatan2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatan2.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatan2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatan2.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatan2.setName("BtnAwalKeperawatan2"); // NOI18N
        BtnAwalKeperawatan2.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatan2.setRoundRect(false);
        BtnAwalKeperawatan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatan2ActionPerformed(evt);
            }
        });
        MBedah.add(BtnAwalKeperawatan2);

        BtnSignInSebelumAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSignInSebelumAnestesi.setText("Sign-In Sebelum Anestesi");
        BtnSignInSebelumAnestesi.setFocusPainted(false);
        BtnSignInSebelumAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSignInSebelumAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSignInSebelumAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSignInSebelumAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSignInSebelumAnestesi.setName("BtnSignInSebelumAnestesi"); // NOI18N
        BtnSignInSebelumAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSignInSebelumAnestesi.setRoundRect(false);
        BtnSignInSebelumAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignInSebelumAnestesiActionPerformed(evt);
            }
        });
        MBedah.add(BtnSignInSebelumAnestesi);

        jTabbedPane1.addTab("Bedah", MBedah);

        MTht.setBackground(new java.awt.Color(255, 255, 255));
        MTht.setBorder(null);
        MTht.setName("MTht"); // NOI18N
        MTht.setPreferredSize(new java.awt.Dimension(150, 483));
        MTht.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalMedisTHT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisTHT.setText("Awal Medis THT");
        BtnAwalMedisTHT.setFocusPainted(false);
        BtnAwalMedisTHT.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisTHT.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisTHT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisTHT.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisTHT.setName("BtnAwalMedisTHT"); // NOI18N
        BtnAwalMedisTHT.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisTHT.setRoundRect(false);
        BtnAwalMedisTHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisTHTActionPerformed(evt);
            }
        });
        MTht.add(BtnAwalMedisTHT);

        BtnAwalKeperawatan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatan3.setText("Awal Keperawatan THT");
        BtnAwalKeperawatan3.setFocusPainted(false);
        BtnAwalKeperawatan3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatan3.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatan3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatan3.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatan3.setName("BtnAwalKeperawatan3"); // NOI18N
        BtnAwalKeperawatan3.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatan3.setRoundRect(false);
        BtnAwalKeperawatan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatan3ActionPerformed(evt);
            }
        });
        MTht.add(BtnAwalKeperawatan3);

        jTabbedPane1.addTab("THT", MTht);

        MMata.setBackground(new java.awt.Color(255, 255, 255));
        MMata.setBorder(null);
        MMata.setName("MMata"); // NOI18N
        MMata.setPreferredSize(new java.awt.Dimension(150, 483));
        MMata.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalMedisMata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisMata.setText("Awal Medis Mata");
        BtnAwalMedisMata.setFocusPainted(false);
        BtnAwalMedisMata.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisMata.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisMata.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisMata.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisMata.setName("BtnAwalMedisMata"); // NOI18N
        BtnAwalMedisMata.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisMata.setRoundRect(false);
        BtnAwalMedisMata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisMataActionPerformed(evt);
            }
        });
        MMata.add(BtnAwalMedisMata);

        BtnAwalKeperawatan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatan4.setText("Awal Keperawatan Mata");
        BtnAwalKeperawatan4.setFocusPainted(false);
        BtnAwalKeperawatan4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatan4.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatan4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatan4.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatan4.setName("BtnAwalKeperawatan4"); // NOI18N
        BtnAwalKeperawatan4.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatan4.setRoundRect(false);
        BtnAwalKeperawatan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatan4ActionPerformed(evt);
            }
        });
        MMata.add(BtnAwalKeperawatan4);

        jTabbedPane1.addTab("Mata", MMata);

        MGigi.setBackground(new java.awt.Color(255, 255, 255));
        MGigi.setBorder(null);
        MGigi.setName("MGigi"); // NOI18N
        MGigi.setPreferredSize(new java.awt.Dimension(150, 483));
        MGigi.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalMedis1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedis1.setText("Awal Medis Gigi");
        BtnAwalMedis1.setFocusPainted(false);
        BtnAwalMedis1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedis1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedis1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedis1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedis1.setName("BtnAwalMedis1"); // NOI18N
        BtnAwalMedis1.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedis1.setRoundRect(false);
        BtnAwalMedis1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedis1ActionPerformed(evt);
            }
        });
        MGigi.add(BtnAwalMedis1);

        BtnAwalKeperawatanGigi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanGigi.setText("Awal Keperawatan Gigi");
        BtnAwalKeperawatanGigi.setFocusPainted(false);
        BtnAwalKeperawatanGigi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanGigi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanGigi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanGigi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanGigi.setName("BtnAwalKeperawatanGigi"); // NOI18N
        BtnAwalKeperawatanGigi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanGigi.setRoundRect(false);
        BtnAwalKeperawatanGigi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanGigiActionPerformed(evt);
            }
        });
        MGigi.add(BtnAwalKeperawatanGigi);

        BtnOdontogram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnOdontogram.setText("Odontogram");
        BtnOdontogram.setFocusPainted(false);
        BtnOdontogram.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnOdontogram.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnOdontogram.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnOdontogram.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnOdontogram.setName("BtnOdontogram"); // NOI18N
        BtnOdontogram.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnOdontogram.setRoundRect(false);
        BtnOdontogram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOdontogramActionPerformed(evt);
            }
        });
        BtnOdontogram.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOdontogramKeyPressed(evt);
            }
        });
        MGigi.add(BtnOdontogram);

        jTabbedPane1.addTab("Gigi", MGigi);

        MGizi.setBackground(new java.awt.Color(255, 255, 255));
        MGizi.setBorder(null);
        MGizi.setName("MGizi"); // NOI18N
        MGizi.setPreferredSize(new java.awt.Dimension(150, 483));
        MGizi.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAsuhanGizi.setText("Asuhan Gizi");
        BtnAsuhanGizi.setFocusPainted(false);
        BtnAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAsuhanGizi.setName("BtnAsuhanGizi"); // NOI18N
        BtnAsuhanGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAsuhanGizi.setRoundRect(false);
        BtnAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsuhanGiziActionPerformed(evt);
            }
        });
        MGizi.add(BtnAsuhanGizi);

        BtnSkriningNutrisiLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiLansia.setText("Skrining Nutrisi Lansia");
        BtnSkriningNutrisiLansia.setFocusPainted(false);
        BtnSkriningNutrisiLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiLansia.setName("BtnSkriningNutrisiLansia"); // NOI18N
        BtnSkriningNutrisiLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiLansia.setRoundRect(false);
        BtnSkriningNutrisiLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiLansiaActionPerformed(evt);
            }
        });
        MGizi.add(BtnSkriningNutrisiLansia);

        BtnSkriningNutrisiDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiDewasa.setText("Skrining Nutrisi Dewasa");
        BtnSkriningNutrisiDewasa.setFocusPainted(false);
        BtnSkriningNutrisiDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiDewasa.setName("BtnSkriningNutrisiDewasa"); // NOI18N
        BtnSkriningNutrisiDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiDewasa.setRoundRect(false);
        BtnSkriningNutrisiDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiDewasaActionPerformed(evt);
            }
        });
        MGizi.add(BtnSkriningNutrisiDewasa);

        BtnSkriningNutrisiAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiAnak.setText("Skrining Nutrisi Anak");
        BtnSkriningNutrisiAnak.setFocusPainted(false);
        BtnSkriningNutrisiAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiAnak.setName("BtnSkriningNutrisiAnak"); // NOI18N
        BtnSkriningNutrisiAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiAnak.setRoundRect(false);
        BtnSkriningNutrisiAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiAnakActionPerformed(evt);
            }
        });
        MGizi.add(BtnSkriningNutrisiAnak);

        BtnMonitoringAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMonitoringAsuhanGizi.setText("Monitoring Gizi");
        BtnMonitoringAsuhanGizi.setFocusPainted(false);
        BtnMonitoringAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMonitoringAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringAsuhanGizi.setName("BtnMonitoringAsuhanGizi"); // NOI18N
        BtnMonitoringAsuhanGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMonitoringAsuhanGizi.setRoundRect(false);
        BtnMonitoringAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringAsuhanGiziActionPerformed(evt);
            }
        });
        MGizi.add(BtnMonitoringAsuhanGizi);

        BtnSkriningGiziLanjut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningGiziLanjut.setText("Skrining Gizi Lanjut");
        BtnSkriningGiziLanjut.setFocusPainted(false);
        BtnSkriningGiziLanjut.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningGiziLanjut.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningGiziLanjut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningGiziLanjut.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningGiziLanjut.setName("BtnSkriningGiziLanjut"); // NOI18N
        BtnSkriningGiziLanjut.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningGiziLanjut.setRoundRect(false);
        BtnSkriningGiziLanjut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningGiziLanjutActionPerformed(evt);
            }
        });
        MGizi.add(BtnSkriningGiziLanjut);

        jTabbedPane1.addTab("Gizi", MGizi);

        MFisio.setBackground(new java.awt.Color(255, 255, 255));
        MFisio.setBorder(null);
        MFisio.setName("MFisio"); // NOI18N
        MFisio.setPreferredSize(new java.awt.Dimension(150, 483));
        MFisio.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalFisioterapi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalFisioterapi.setText("Awal Fisioterapi");
        BtnAwalFisioterapi.setFocusPainted(false);
        BtnAwalFisioterapi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalFisioterapi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalFisioterapi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalFisioterapi.setName("BtnAwalFisioterapi"); // NOI18N
        BtnAwalFisioterapi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalFisioterapi.setRoundRect(false);
        BtnAwalFisioterapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalFisioterapiActionPerformed(evt);
            }
        });
        MFisio.add(BtnAwalFisioterapi);

        BtnRujukanFisio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRujukanFisio.setText("Rujukan Fisio");
        BtnRujukanFisio.setFocusPainted(false);
        BtnRujukanFisio.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRujukanFisio.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRujukanFisio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRujukanFisio.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRujukanFisio.setName("BtnRujukanFisio"); // NOI18N
        BtnRujukanFisio.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRujukanFisio.setRoundRect(false);
        BtnRujukanFisio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRujukanFisioActionPerformed(evt);
            }
        });
        MFisio.add(BtnRujukanFisio);

        jTabbedPane1.addTab("Fisio", MFisio);

        MGeriatri.setBackground(new java.awt.Color(255, 255, 255));
        MGeriatri.setBorder(null);
        MGeriatri.setName("MGeriatri"); // NOI18N
        MGeriatri.setPreferredSize(new java.awt.Dimension(150, 483));
        MGeriatri.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalKeperawatanGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanGeriatri.setText("Awal Keperawatan Geriatri");
        BtnAwalKeperawatanGeriatri.setFocusPainted(false);
        BtnAwalKeperawatanGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanGeriatri.setName("BtnAwalKeperawatanGeriatri"); // NOI18N
        BtnAwalKeperawatanGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanGeriatri.setRoundRect(false);
        BtnAwalKeperawatanGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanGeriatriActionPerformed(evt);
            }
        });
        MGeriatri.add(BtnAwalKeperawatanGeriatri);

        BtnAwalMedisGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisGeriatri.setText("Awal Medis Geriatri");
        BtnAwalMedisGeriatri.setFocusPainted(false);
        BtnAwalMedisGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisGeriatri.setName("BtnAwalMedisGeriatri"); // NOI18N
        BtnAwalMedisGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisGeriatri.setRoundRect(false);
        BtnAwalMedisGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisGeriatriActionPerformed(evt);
            }
        });
        MGeriatri.add(BtnAwalMedisGeriatri);

        BtnPenilaianTambahanGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanGeriatri.setText("Tambahan Pasien Geriatri");
        BtnPenilaianTambahanGeriatri.setFocusPainted(false);
        BtnPenilaianTambahanGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanGeriatri.setName("BtnPenilaianTambahanGeriatri"); // NOI18N
        BtnPenilaianTambahanGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanGeriatri.setRoundRect(false);
        BtnPenilaianTambahanGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanGeriatriActionPerformed(evt);
            }
        });
        MGeriatri.add(BtnPenilaianTambahanGeriatri);

        jTabbedPane1.addTab("Geriatri", MGeriatri);

        MPsikiatri.setBackground(new java.awt.Color(255, 255, 255));
        MPsikiatri.setBorder(null);
        MPsikiatri.setName("MPsikiatri"); // NOI18N
        MPsikiatri.setPreferredSize(new java.awt.Dimension(150, 483));
        MPsikiatri.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalKeperawatanPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanPsikiatri.setText("Awal Keperawatan Psikiatri");
        BtnAwalKeperawatanPsikiatri.setFocusPainted(false);
        BtnAwalKeperawatanPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanPsikiatri.setName("BtnAwalKeperawatanPsikiatri"); // NOI18N
        BtnAwalKeperawatanPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanPsikiatri.setRoundRect(false);
        BtnAwalKeperawatanPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanPsikiatriActionPerformed(evt);
            }
        });
        MPsikiatri.add(BtnAwalKeperawatanPsikiatri);

        BtnAwalMedisPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisPsikiatri.setText("Awal Medis Psikiatri");
        BtnAwalMedisPsikiatri.setFocusPainted(false);
        BtnAwalMedisPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisPsikiatri.setName("BtnAwalMedisPsikiatri"); // NOI18N
        BtnAwalMedisPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisPsikiatri.setRoundRect(false);
        BtnAwalMedisPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisPsikiatriActionPerformed(evt);
            }
        });
        MPsikiatri.add(BtnAwalMedisPsikiatri);

        BtnPenilaianPsikolog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPsikolog.setText("Penilaian Psikolog");
        BtnPenilaianPsikolog.setFocusPainted(false);
        BtnPenilaianPsikolog.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPsikolog.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPsikolog.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPsikolog.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPsikolog.setName("BtnPenilaianPsikolog"); // NOI18N
        BtnPenilaianPsikolog.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPsikolog.setRoundRect(false);
        BtnPenilaianPsikolog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPsikologActionPerformed(evt);
            }
        });
        MPsikiatri.add(BtnPenilaianPsikolog);

        jTabbedPane1.addTab("Psikiatri", MPsikiatri);

        Mall.setBackground(new java.awt.Color(255, 255, 255));
        Mall.setBorder(null);
        Mall.setName("Mall"); // NOI18N
        Mall.setPreferredSize(new java.awt.Dimension(150, 483));
        Mall.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnAwalMedisNeurologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisNeurologi.setText("Awal Medis Neurologi");
        BtnAwalMedisNeurologi.setFocusPainted(false);
        BtnAwalMedisNeurologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisNeurologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisNeurologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisNeurologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisNeurologi.setName("BtnAwalMedisNeurologi"); // NOI18N
        BtnAwalMedisNeurologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisNeurologi.setRoundRect(false);
        BtnAwalMedisNeurologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisNeurologiActionPerformed(evt);
            }
        });
        Mall.add(BtnAwalMedisNeurologi);

        BtnAwalMedisOrthopedi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisOrthopedi.setText("Awal Medis Orthopedi");
        BtnAwalMedisOrthopedi.setFocusPainted(false);
        BtnAwalMedisOrthopedi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisOrthopedi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisOrthopedi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisOrthopedi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisOrthopedi.setName("BtnAwalMedisOrthopedi"); // NOI18N
        BtnAwalMedisOrthopedi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisOrthopedi.setRoundRect(false);
        BtnAwalMedisOrthopedi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisOrthopediActionPerformed(evt);
            }
        });
        Mall.add(BtnAwalMedisOrthopedi);

        BtnPenilaianPasienTerminal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPasienTerminal.setText("Penilaian Pasien Terminal");
        BtnPenilaianPasienTerminal.setFocusPainted(false);
        BtnPenilaianPasienTerminal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPasienTerminal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienTerminal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienTerminal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienTerminal.setName("BtnPenilaianPasienTerminal"); // NOI18N
        BtnPenilaianPasienTerminal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienTerminal.setRoundRect(false);
        BtnPenilaianPasienTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienTerminalActionPerformed(evt);
            }
        });
        Mall.add(BtnPenilaianPasienTerminal);

        BtnUjiFungsiKFR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnUjiFungsiKFR.setText("Uji Fungsi/Prosedur KFR");
        BtnUjiFungsiKFR.setFocusPainted(false);
        BtnUjiFungsiKFR.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnUjiFungsiKFR.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnUjiFungsiKFR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnUjiFungsiKFR.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnUjiFungsiKFR.setName("BtnUjiFungsiKFR"); // NOI18N
        BtnUjiFungsiKFR.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnUjiFungsiKFR.setRoundRect(false);
        BtnUjiFungsiKFR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUjiFungsiKFRActionPerformed(evt);
            }
        });
        Mall.add(BtnUjiFungsiKFR);

        BtnChecklistPostOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistPostOperasi.setText("Check List Post Operasi");
        BtnChecklistPostOperasi.setFocusPainted(false);
        BtnChecklistPostOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistPostOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPostOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPostOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPostOperasi.setName("BtnChecklistPostOperasi"); // NOI18N
        BtnChecklistPostOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPostOperasi.setRoundRect(false);
        BtnChecklistPostOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistPostOperasiActionPerformed(evt);
            }
        });
        Mall.add(BtnChecklistPostOperasi);

        BtnPenilaianTambahanBunuhDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setText("Tambahan Bunuh Diri");
        BtnPenilaianTambahanBunuhDiri.setFocusPainted(false);
        BtnPenilaianTambahanBunuhDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanBunuhDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanBunuhDiri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanBunuhDiri.setName("BtnPenilaianTambahanBunuhDiri"); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanBunuhDiri.setRoundRect(false);
        BtnPenilaianTambahanBunuhDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanBunuhDiriActionPerformed(evt);
            }
        });
        Mall.add(BtnPenilaianTambahanBunuhDiri);

        BtnPenilaianLanjutanRisikoJatuhLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhLansia.setText("Lanjutan Risiko Jatuh Lansia");
        BtnPenilaianLanjutanRisikoJatuhLansia.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhLansia.setName("BtnPenilaianLanjutanRisikoJatuhLansia"); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhLansia.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhLansiaActionPerformed(evt);
            }
        });
        Mall.add(BtnPenilaianLanjutanRisikoJatuhLansia);

        BtnPenilaianLanjutanRisikoJatuhDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhDewasa.setText("Lanjutan Risiko Jatuh Dewasa");
        BtnPenilaianLanjutanRisikoJatuhDewasa.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhDewasa.setName("BtnPenilaianLanjutanRisikoJatuhDewasa"); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhDewasa.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhDewasaActionPerformed(evt);
            }
        });
        Mall.add(BtnPenilaianLanjutanRisikoJatuhDewasa);

        BtnPenilaianPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPreOperasi.setText("Penilaian Pre Operasi");
        BtnPenilaianPreOperasi.setFocusPainted(false);
        BtnPenilaianPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPreOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreOperasi.setName("BtnPenilaianPreOperasi"); // NOI18N
        BtnPenilaianPreOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreOperasi.setRoundRect(false);
        BtnPenilaianPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPreOperasiActionPerformed(evt);
            }
        });
        Mall.add(BtnPenilaianPreOperasi);

        BtnTimeOutSebelumInsisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTimeOutSebelumInsisi.setText("Time-Out Sebelum Insisi");
        BtnTimeOutSebelumInsisi.setFocusPainted(false);
        BtnTimeOutSebelumInsisi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTimeOutSebelumInsisi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTimeOutSebelumInsisi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTimeOutSebelumInsisi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTimeOutSebelumInsisi.setName("BtnTimeOutSebelumInsisi"); // NOI18N
        BtnTimeOutSebelumInsisi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTimeOutSebelumInsisi.setRoundRect(false);
        BtnTimeOutSebelumInsisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTimeOutSebelumInsisiActionPerformed(evt);
            }
        });
        Mall.add(BtnTimeOutSebelumInsisi);

        BtnPenilaianTambahanMelarikanDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setText("Tambahan Melarikan Diri");
        BtnPenilaianTambahanMelarikanDiri.setFocusPainted(false);
        BtnPenilaianTambahanMelarikanDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanMelarikanDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanMelarikanDiri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanMelarikanDiri.setName("BtnPenilaianTambahanMelarikanDiri"); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanMelarikanDiri.setRoundRect(false);
        BtnPenilaianTambahanMelarikanDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanMelarikanDiriActionPerformed(evt);
            }
        });
        Mall.add(BtnPenilaianTambahanMelarikanDiri);

        BtnTransferAntarRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTransferAntarRuang.setText("Transfer Antar Ruang");
        BtnTransferAntarRuang.setFocusPainted(false);
        BtnTransferAntarRuang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTransferAntarRuang.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTransferAntarRuang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTransferAntarRuang.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTransferAntarRuang.setName("BtnTransferAntarRuang"); // NOI18N
        BtnTransferAntarRuang.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTransferAntarRuang.setRoundRect(false);
        BtnTransferAntarRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTransferAntarRuangActionPerformed(evt);
            }
        });
        Mall.add(BtnTransferAntarRuang);

        BtnPenilaianTambahanPerilakuKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setText("Tambahan Perilaku Kekerasan");
        BtnPenilaianTambahanPerilakuKekerasan.setFocusPainted(false);
        BtnPenilaianTambahanPerilakuKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanPerilakuKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanPerilakuKekerasan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanPerilakuKekerasan.setName("BtnPenilaianTambahanPerilakuKekerasan"); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanPerilakuKekerasan.setRoundRect(false);
        BtnPenilaianTambahanPerilakuKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanPerilakuKekerasanActionPerformed(evt);
            }
        });
        Mall.add(BtnPenilaianTambahanPerilakuKekerasan);

        BtnPenilaianPasienPenyakitMenular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setText("Pasien Penyakit Menular");
        BtnPenilaianPasienPenyakitMenular.setFocusPainted(false);
        BtnPenilaianPasienPenyakitMenular.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienPenyakitMenular.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienPenyakitMenular.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienPenyakitMenular.setName("BtnPenilaianPasienPenyakitMenular"); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienPenyakitMenular.setRoundRect(false);
        BtnPenilaianPasienPenyakitMenular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienPenyakitMenularActionPerformed(evt);
            }
        });
        Mall.add(BtnPenilaianPasienPenyakitMenular);

        BtnSignOutSebelumMenutupLuka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSignOutSebelumMenutupLuka.setText("Sign-Out Sebelum Menutup Luka");
        BtnSignOutSebelumMenutupLuka.setFocusPainted(false);
        BtnSignOutSebelumMenutupLuka.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSignOutSebelumMenutupLuka.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSignOutSebelumMenutupLuka.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSignOutSebelumMenutupLuka.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSignOutSebelumMenutupLuka.setName("BtnSignOutSebelumMenutupLuka"); // NOI18N
        BtnSignOutSebelumMenutupLuka.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSignOutSebelumMenutupLuka.setRoundRect(false);
        BtnSignOutSebelumMenutupLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignOutSebelumMenutupLukaActionPerformed(evt);
            }
        });
        Mall.add(BtnSignOutSebelumMenutupLuka);

        BtnChecklistPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistPreOperasi.setText("Check List Pre Operasi");
        BtnChecklistPreOperasi.setFocusPainted(false);
        BtnChecklistPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistPreOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPreOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPreOperasi.setName("BtnChecklistPreOperasi"); // NOI18N
        BtnChecklistPreOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPreOperasi.setRoundRect(false);
        BtnChecklistPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistPreOperasiActionPerformed(evt);
            }
        });
        Mall.add(BtnChecklistPreOperasi);

        BtnPenilaianKorbanKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianKorbanKekerasan.setText("Penilaian Korban Kekerasan");
        BtnPenilaianKorbanKekerasan.setFocusPainted(false);
        BtnPenilaianKorbanKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianKorbanKekerasan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianKorbanKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianKorbanKekerasan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianKorbanKekerasan.setName("BtnPenilaianKorbanKekerasan"); // NOI18N
        BtnPenilaianKorbanKekerasan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianKorbanKekerasan.setRoundRect(false);
        BtnPenilaianKorbanKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianKorbanKekerasanActionPerformed(evt);
            }
        });
        Mall.add(BtnPenilaianKorbanKekerasan);

        BtnRekonsiliasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRekonsiliasiObat.setText("Rekonsiliasi Obat");
        BtnRekonsiliasiObat.setFocusPainted(false);
        BtnRekonsiliasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRekonsiliasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRekonsiliasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRekonsiliasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRekonsiliasiObat.setName("BtnRekonsiliasiObat"); // NOI18N
        BtnRekonsiliasiObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRekonsiliasiObat.setRoundRect(false);
        BtnRekonsiliasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRekonsiliasiObatActionPerformed(evt);
            }
        });
        Mall.add(BtnRekonsiliasiObat);

        BtnInacbgRajal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInacbgRajal.setText("INACBG Ralan");
        BtnInacbgRajal.setFocusPainted(false);
        BtnInacbgRajal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInacbgRajal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInacbgRajal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInacbgRajal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInacbgRajal.setName("BtnInacbgRajal"); // NOI18N
        BtnInacbgRajal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInacbgRajal.setRoundRect(false);
        BtnInacbgRajal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInacbgRajalActionPerformed(evt);
            }
        });
        Mall.add(BtnInacbgRajal);

        BtnPenilaianPreAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPreAnestesi.setText("Penilaian Pre Anestesi");
        BtnPenilaianPreAnestesi.setFocusPainted(false);
        BtnPenilaianPreAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPreAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreAnestesi.setName("BtnPenilaianPreAnestesi"); // NOI18N
        BtnPenilaianPreAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreAnestesi.setRoundRect(false);
        BtnPenilaianPreAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPreAnestesiActionPerformed(evt);
            }
        });
        Mall.add(BtnPenilaianPreAnestesi);

        BtnKonselingFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKonselingFarmasi.setText("Konseling Farmasi");
        BtnKonselingFarmasi.setFocusPainted(false);
        BtnKonselingFarmasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKonselingFarmasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKonselingFarmasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKonselingFarmasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKonselingFarmasi.setName("BtnKonselingFarmasi"); // NOI18N
        BtnKonselingFarmasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKonselingFarmasi.setRoundRect(false);
        BtnKonselingFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKonselingFarmasiActionPerformed(evt);
            }
        });
        Mall.add(BtnKonselingFarmasi);

        BtnIC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnIC.setText("Informed Consent");
        BtnIC.setFocusPainted(false);
        BtnIC.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnIC.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnIC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnIC.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnIC.setName("BtnIC"); // NOI18N
        BtnIC.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnIC.setRoundRect(false);
        BtnIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnICActionPerformed(evt);
            }
        });
        Mall.add(BtnIC);

        jTabbedPane1.addTab("Lainnya", Mall);

        FormMenu.add(jTabbedPane1);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.WEST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
            kd_pj=Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
            kode_poli=Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
        }else{         
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,DTPTgl,KdDok);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,DTPTgl,kdptg);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,DTPTgl,KdDok2);
            }else if(TabRawat.getSelectedIndex()==3){
                Valid.pindah(evt,DTPTgl,KodeDokter);
            }else if(TabRawat.getSelectedIndex()==4){
                Valid.pindah(evt,DTPTgl,TTinggi_uteri);
            }else if(TabRawat.getSelectedIndex()==5){
                Valid.pindah(evt,DTPTgl,TInspeksi);
            }else if(TabRawat.getSelectedIndex()==8){
                Valid.pindah(evt,DTPTgl,KdDok3);
            }
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    if(KdDok.getText().trim().equals("")||TDokter.getText().trim().equals("")){
                        Valid.textKosong(KdDok,"Dokter");
                    }else{
                        int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {                        
                        try {
                            jmlparsial=0;
                            if(aktifkanparsial.equals("yes")){
                                jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(jmlparsial>0){    
                                SimpanPenangananDokter();
                            }else{
                                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                }else{
                                    SimpanPenangananDokter();
                                }
                            } 
                        } catch (Exception e) {
                        }                      
                    } 
                    }
                    break;
                case 1:
                    if(kdptg.getText().trim().equals("")||TPerawat.getText().trim().equals("")){
                        Valid.textKosong(kdptg,"Petugas");
                    }else{
                        int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                        try {
                            jmlparsial=0;
                            if(aktifkanparsial.equals("yes")){
                                jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(jmlparsial>0){ 
                                SimpanPenangananPetugas();
                            }else{
                                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                }else{
                                    SimpanPenangananPetugas();
                                }
                            } 
                        } catch (Exception e) {
                        } 
                    } 
                    }
                    break;
                case 2:
                    if(KdDok2.getText().trim().equals("")||TDokter2.getText().trim().equals("")){
                        Valid.textKosong(KdDok2,"Dokter");
                    }else if(kdptg2.getText().trim().equals("")||TPerawat2.getText().trim().equals("")){
                        Valid.textKosong(kdptg2,"Petugas");
                    }else{
                        int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                        try {
                            jmlparsial=0;
                            if(aktifkanparsial.equals("yes")){
                                jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(jmlparsial>0){ 
                                SimpanPenangananDokterPetugas();
                            }else {
                                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                }else{
                                    SimpanPenangananDokterPetugas();
                                }
                            } 
                        } catch (Exception e) {
                        }                            
                    }  
                    }
                    break;
                case 3:
                    if((!TKeluhan.getText().trim().equals(""))||(!TPemeriksaan.getText().trim().equals(""))||
                            (!TSuhu.getText().trim().equals(""))||(!TTensi.getText().trim().equals(""))||
                            (!TAlergi.getText().trim().equals(""))||(!TTinggi.getText().trim().equals(""))||
                            (!TBerat.getText().trim().equals(""))||(!TRespirasi.getText().trim().equals(""))||
                            (!TNadi.getText().trim().equals(""))||(!TGCS.getText().trim().equals(""))||
                            (!TindakLanjut.getText().trim().equals(""))||(!TPenilaian.getText().trim().equals(""))||
                            (!TRpd.getText().trim().equals(""))||(!TRpk.getText().trim().equals(""))||
                            (!TRpo.getText().trim().equals(""))||(!TSpo2.getText().trim().equals(""))||(!Operasi.getText().trim().equals(""))||(!KodeDokter.getText().trim().equals(""))){
                        if(TAlergi.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"Alergi Wajib Diisi");
                            }else{
                        if(TKeluhan.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"Subjective Wajib Diisi");
                            }else{
                        if(TPemeriksaan.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"Objective Wajib Diisi");
                            }else{
                        if(TPenilaian.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"Assesment Wajib Diisi");
                            }else{
                        if(TindakLanjut.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"Plan Wajib Diisi");
                            }else{
                        if(TRpd.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"RPD");
                            }else{
                        if(TRpk.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"RPK");
                            }else{
                        if(TRpo.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"RPO");
                            }else{
                        if(KodeDokter.getText().trim().equals("")||NamaDokter.getText().trim().equals("")){
                            Valid.textKosong(KodeDokter,"Dokter/Paramedis masih kosong...!!");
                        }else{
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.menyimpantf("pemeriksaan_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",24,new String[]{
                                            TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                            TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),
                                            TGCS.getText(),cmbKesadaran.getSelectedItem().toString(),TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),
                                            cmbImun.getSelectedItem().toString(),TindakLanjut.getText(),TPenilaian.getText(),TRpd.getText(),
                                            TRpk.getText(),TRpo.getText(),TSpo2.getText(),Operasi.getText(),KodeDokter.getText(),Instruksi.getText()})==true){
                                        tabModePemeriksaan.addRow(new Object[]{
                                            false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                            TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),TGCS.getText(),cmbKesadaran.getSelectedItem().toString(),
                                            TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),cmbImun.getSelectedItem().toString(),TindakLanjut.getText(),TPenilaian.getText(),TRpd.getText(),TRpk.getText(),
                                            TRpo.getText(),TSpo2.getText(),Operasi.getText(),KodeDokter.getText(),NamaDokter.getText(),Instruksi.getText()
                                        });
                                        TSuhu.setText("");TTensi.setText("");TNadi.setText("");TRespirasi.setText("");
                                        TTinggi.setText("");TBerat.setText("");TGCS.setText("");TKeluhan.setText("");
                                        TPemeriksaan.setText("");TAlergi.setText("");cmbImun.setSelectedIndex(0);
                                        TindakLanjut.setText("");TPenilaian.setText("");cmbKesadaran.setSelectedIndex(0);
                                        TRpd.setText("");TRpk.setText("");TRpo.setText("");TSpo2.setText("");Operasi.setText("");Instruksi.setText("");
                                        LCount.setText(""+tabModePemeriksaan.getRowCount());
                                }
                            }else{
                                if(akses.getkode().equals(KodeDokter.getText())){
                                    if(Sequel.menyimpantf("pemeriksaan_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",24,new String[]{
                                            TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                            TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),
                                            TGCS.getText(),cmbKesadaran.getSelectedItem().toString(),TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),
                                            cmbImun.getSelectedItem().toString(),TindakLanjut.getText(),TPenilaian.getText(),TRpd.getText(),
                                            TRpk.getText(),TRpo.getText(),TSpo2.getText(),Operasi.getText(),KodeDokter.getText(),Instruksi.getText()})==true){
                                        tabModePemeriksaan.addRow(new Object[]{
                                            false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                            TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),TGCS.getText(),cmbKesadaran.getSelectedItem().toString(),
                                            TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),cmbImun.getSelectedItem().toString(),TindakLanjut.getText(),TPenilaian.getText(),TRpd.getText(),TRpk.getText(),
                                            TRpo.getText(),TSpo2.getText(),Operasi.getText(),KodeDokter.getText(),NamaDokter.getText(),Instruksi.getText()
                                        });
                                        TSuhu.setText("");TTensi.setText("");TNadi.setText("");TRespirasi.setText("");
                                        TTinggi.setText("");TBerat.setText("");TGCS.setText("");TKeluhan.setText("");
                                        TPemeriksaan.setText("");TAlergi.setText("");cmbImun.setSelectedIndex(0);
                                        TindakLanjut.setText("");TPenilaian.setText("");cmbKesadaran.setSelectedIndex(0);
                                        TRpd.setText("");TRpk.setText("");TRpo.setText("");TSpo2.setText("");Operasi.setText("");Instruksi.setText("");
                                        LCount.setText(""+tabModePemeriksaan.getRowCount());
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null,"Hanya bisa disimpan oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        }
                    }
                        }}}}}}}}
                    break;
                case 4:
                    if((!Anamnesa.getText().trim().equals(""))||
                            (!PemeriksaanFisik.getText().trim().equals(""))||(!PemeriksaanPenunjang.getText().trim().equals(""))||
                            (!Anjuran.getText().trim().equals(""))||(!Diagnosis.getText().trim().equals(""))||(!Tatalaksana.getText().trim().equals(""))||(!Evaluasi.getText().trim().equals(""))||
                            (!TRpd1.getText().trim().equals(""))||(!TRpk1.getText().trim().equals(""))||(!TRpo1.getText().trim().equals(""))||(!TAlergi1.getText().trim().equals(""))||
                            (!Instruksi1.getText().trim().equals(""))||(!Operasi1.getText().trim().equals(""))||(!KodeDokter.getText().trim().equals(""))){
                        if(Anamnesa.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"Anamnesa Wajib Diisi");
                            }else{
                        if(PemeriksaanFisik.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"PemeriksaanFisik Wajib Diisi");
                            }else{
                        if(PemeriksaanPenunjang.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"PemeriksaanPenunjang Wajib Diisi");
                            }else{
                        if(Anjuran.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"Anjuran Wajib Diisi");
                            }else{
                        if(Evaluasi.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"Evaluasi Wajib Diisi");
                            }else{
                        if(Diagnosis.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"Diagnosis Wajib Diisi");
                            }else{
                        if(Tatalaksana.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(null,"Tatalaksana Wajib Diisi");
                            }else{
                        if(KodeDokter.getText().trim().equals("")||NamaDokter.getText().trim().equals("")){
                            Valid.textKosong(KodeDokter,"Dokter/Paramedis masih kosong...!!");
                        }else{
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.menyimpantf("pemeriksaan_ralan_rehab","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",20,new String[]{
                                            TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),                                            
                                            cmbKesadaran.getSelectedItem().toString(),Anamnesa.getText(),PemeriksaanFisik.getText(),PemeriksaanPenunjang.getText(),
                                            Anjuran.getText(),Diagnosis.getText(),Tatalaksana.getText(),Evaluasi.getText(),Suspek.getSelectedItem().toString(),KetSuspek.getText(),TRpd1.getText(),TRpk1.getText(),TRpo1.getText(),
                                            TAlergi1.getText(),Instruksi1.getText(),Operasi1.getText(),KodeDokter.getText()})==true){
                                        tabModePemeriksaanRM.addRow(new Object[]{
                                            false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),                                            
                                            cmbKesadaran.getSelectedItem().toString(),Anamnesa.getText(),PemeriksaanFisik.getText(),PemeriksaanPenunjang.getText(),
                                            Anjuran.getText(),Diagnosis.getText(),Tatalaksana.getText(),Evaluasi.getText(),Suspek.getSelectedItem().toString(),KetSuspek.getText(),TRpd1.getText(),TRpk1.getText(),TRpo1.getText(),
                                            TAlergi1.getText(),Instruksi1.getText(),Operasi1.getText(),KodeDokter.getText(),NamaDokter.getText()
                                        });
                                        cmbKesadaran.setSelectedIndex(0);Anamnesa.setText("");PemeriksaanFisik.setText("");PemeriksaanPenunjang.setText("");Anjuran.setText("");
                                        Diagnosis.setText("");Tatalaksana.setText("");Evaluasi.setText("");Suspek.setSelectedIndex(0);KetSuspek.setText("");
                                        TRpd1.setText("");TRpk1.setText("");TRpo1.setText("");TAlergi1.setText("");Operasi1.setText("");Instruksi1.setText("");
                                        LCount.setText(""+tabModePemeriksaanRM.getRowCount());
                                }
                            }else{
                                if(akses.getkode().equals(KodeDokter.getText())){
                                    if(Sequel.menyimpantf("pemeriksaan_ralan_rehab","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",20,new String[]{
                                            TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),                                            
                                            cmbKesadaran.getSelectedItem().toString(),Anamnesa.getText(),PemeriksaanFisik.getText(),PemeriksaanPenunjang.getText(),
                                            Anjuran.getText(),Diagnosis.getText(),Tatalaksana.getText(),Evaluasi.getText(),Suspek.getSelectedItem().toString(),KetSuspek.getText(),TRpd1.getText(),TRpk1.getText(),TRpo1.getText(),
                                            TAlergi1.getText(),Instruksi1.getText(),Operasi1.getText(),KodeDokter.getText()})==true){
                                        tabModePemeriksaanRM.addRow(new Object[]{
                                            false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),                                            
                                            cmbKesadaran.getSelectedItem().toString(),Anamnesa.getText(),PemeriksaanFisik.getText(),PemeriksaanPenunjang.getText(),
                                            Anjuran.getText(),Diagnosis.getText(),Tatalaksana.getText(),Evaluasi.getText(),Suspek.getSelectedItem().toString(),KetSuspek.getText(),TRpd1.getText(),TRpk1.getText(),TRpo1.getText(),
                                            TAlergi1.getText(),Instruksi1.getText(),Operasi1.getText(),KodeDokter.getText(),NamaDokter.getText()
                                        });
                                        cmbKesadaran.setSelectedIndex(0);Anamnesa.setText("");PemeriksaanFisik.setText("");PemeriksaanPenunjang.setText("");Anjuran.setText("");
                                        Diagnosis.setText("");Tatalaksana.setText("");Evaluasi.setText("");Suspek.setSelectedIndex(0);KetSuspek.setText("");
                                        TRpd1.setText("");TRpk1.setText("");TRpo1.setText("");TAlergi1.setText("");Operasi1.setText("");Instruksi1.setText("");
                                        LCount.setText(""+tabModePemeriksaanRM.getRowCount());
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null,"Hanya bisa disimpan oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        }
                    }
                        }}}}}}}
                    break;
                case 5:
                    if((!TTinggi_uteri.getText().trim().equals(""))||(!TLetak.getText().trim().equals(""))||
                            (!TDenyut.getText().trim().equals(""))||(!TKualitas_mnt.getText().trim().equals(""))||
                            (!TKualitas_dtk.getText().trim().equals(""))||(!TVulva.getText().trim().equals(""))||
                            (!TPortio.getText().trim().equals(""))||(!TTebal.getText().trim().equals(""))||
                            (!TPembukaan.getText().trim().equals(""))||(!TPenurunan.getText().trim().equals(""))||
                            (!TDenominator.getText().trim().equals(""))||(!KodeDokter.getText().trim().equals(""))){
                        if(Sequel.menyimpantf("pemeriksaan_obstetri_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",24,new String[]{
                            TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            TTinggi_uteri.getText(),cmbJanin.getSelectedItem().toString(),TLetak.getText(),cmbPanggul.getSelectedItem().toString(),TDenyut.getText(),
                            cmbKontraksi.getSelectedItem().toString(),TKualitas_mnt.getText(),TKualitas_dtk.getText(),cmbFluksus.getSelectedItem().toString(),
                            cmbAlbus.getSelectedItem().toString(),TVulva.getText(),TPortio.getText(),cmbDalam.getSelectedItem().toString(),TTebal.getText(),
                            cmbArah.getSelectedItem().toString(),TPembukaan.getText(),TPenurunan.getText(),TDenominator.getText(),cmbKetuban.getSelectedItem().toString(),
                            cmbFeto.getSelectedItem().toString(),KodeDokter.getText()})==true){
                                tabModeObstetri.addRow(new Object[] {
                                    false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                    TTinggi_uteri.getText(),cmbJanin.getSelectedItem().toString(),TLetak.getText(),cmbPanggul.getSelectedItem().toString(),TDenyut.getText(),cmbKontraksi.getSelectedItem().toString(),
                                    TKualitas_mnt.getText(),TKualitas_dtk.getText(),cmbFluksus.getSelectedItem().toString(),cmbAlbus.getSelectedItem().toString(),TVulva.getText(),TPortio.getText(),
                                    cmbDalam.getSelectedItem().toString(),TTebal.getText(),cmbArah.getSelectedItem().toString(),TPembukaan.getText(),TPenurunan.getText(),TDenominator.getText(),
                                    cmbKetuban.getSelectedItem().toString(),cmbFeto.getSelectedItem().toString(),KodeDokter.getText(),NamaDokter.getText()
                                });
                                TTinggi_uteri.setText("");cmbJanin.setSelectedIndex(0);TLetak.setText("");cmbPanggul.setSelectedIndex(0);TDenyut.setText("");
                                cmbKontraksi.setSelectedIndex(0);TKualitas_mnt.setText("");TKualitas_dtk.setText("");cmbFluksus.setSelectedIndex(0);
                                cmbAlbus.setSelectedIndex(0);TVulva.setText("");TPortio.setText("");cmbDalam.setSelectedIndex(0);TTebal.setText("");
                                cmbArah.setSelectedIndex(0);TPembukaan.setText("");TPenurunan.setText("");TDenominator.setText("");cmbKetuban.setSelectedIndex(0);
                                cmbFeto.getSelectedItem().toString();
                                LCount.setText(""+tabModeObstetri.getRowCount());
                        }
                    }  
                    break;
                case 6:
                    if ((!TInspeksi.getText().trim().equals(""))||(!TInspeksiVulva.getText().trim().equals(""))||
                            (!TInspekuloGine.getText().trim().equals(""))||(!TUkuran.getText().trim().equals(""))||
                            (!TPortioInspekulo.getText().trim().equals(""))||(!TSondage.getText().trim().equals(""))||
                            (!TPortioDalam.getText().trim().equals(""))||(!TBentuk.getText().trim().equals(""))||
                            (!TCavumUteri.getText().trim().equals(""))||(!TUkuran.getText().trim().equals(""))||
                            (!TAdnexaKanan.getText().trim().equals(""))||(!TAdnexaKiri.getText().trim().equals(""))||
                            (!TCavumDouglas.getText().trim().equals(""))||(!KodeDokter.getText().trim().equals(""))) {
                        if(Sequel.menyimpantf("pemeriksaan_ginekologi_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",21, new String[] {
                            TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            TInspeksi.getText(),TInspeksiVulva.getText(),TInspekuloGine.getText(),
                            cmbFluxusGine.getSelectedItem().toString(),cmbFluorGine.getSelectedItem().toString(), TVulvaInspekulo.getText(),
                            TPortioInspekulo.getText(), TSondage.getText(), TPortioDalam.getText(),
                            TBentuk.getText(), TCavumUteri.getText(), cmbMobilitas.getSelectedItem().toString(),
                            TUkuran.getText(), cmbNyeriTekan.getSelectedItem().toString(),
                            TAdnexaKanan.getText(), TAdnexaKiri.getText(), TCavumDouglas.getText(), KodeDokter.getText()})==true){
                                tabModeGinekologi.addRow(new Object[] {
                                    false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                    TInspeksi.getText(),TInspeksiVulva.getText(),TInspekuloGine.getText(),cmbFluxusGine.getSelectedItem().toString(),cmbFluorGine.getSelectedItem().toString(),TVulvaInspekulo.getText(),
                                    TPortioInspekulo.getText(),TSondage.getText(),TPortioDalam.getText(),TBentuk.getText(),TCavumUteri.getText(),cmbMobilitas.getSelectedItem().toString(),TUkuran.getText(),
                                    cmbNyeriTekan.getSelectedItem().toString(),TAdnexaKanan.getText(),TAdnexaKiri.getText(),TCavumDouglas.getText(),KodeDokter.getText(),NamaDokter.getText()
                                });
                                TInspeksi.setText("");TInspeksiVulva.setText("");TInspekuloGine.setText("");
                                cmbFluxusGine.setSelectedIndex(0);cmbFluorGine.setSelectedIndex(0); TVulvaInspekulo.setText("");
                                TPortioInspekulo.setText(""); TSondage.setText(""); TPortioDalam.setText("");
                                TBentuk.setText(""); TCavumUteri.setText(""); cmbMobilitas.setSelectedIndex(0);
                                TUkuran.setText(""); cmbNyeriTekan.setSelectedIndex(0);
                                TAdnexaKanan.setText(""); TAdnexaKiri.setText(""); TCavumDouglas.getText();
                                LCount.setText(""+tabModeGinekologi.getRowCount());
                        }
                    }
                    break;
                case 7:
                    if(akses.getdiagnosa_pasien()==true){
                        panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Ralan",TCari.getText().trim());
                        panelDiagnosa1.simpan();
                    }
                    break;
                case 8:
                    if((!KdDok3.getText().trim().equals(""))&&(!TDokter3.getText().trim().equals(""))&&(!Catatan.getText().trim().equals(""))){
                        if(Sequel.menyimpantf("catatan_perawatan","?,?,?,?,?","Data",5,new String[]{
                            Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            TNoRw.getText(),KdDok3.getText(),Catatan.getText()
                        })==true){
                            TabModeCatatan.addRow(new Object[]{
                                false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),KdDok3.getText(),TDokter3.getText(),Catatan.getText()
                            });
                            Catatan.setText("");
                            LCount.setText(""+TabModeCatatan.getRowCount());
                        }
                    }
                    break;
                default:
                    break;
            }            
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,BtnSeekDokter,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,BtnSeekPetugas,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,BtnSeekPetugas2,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==3){
                Valid.pindah(evt,TindakLanjut,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==4){
                Valid.pindah(evt,cmbFeto,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==5){
                Valid.pindah(evt,TCavumDouglas,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==8){
                Valid.pindah(evt,Catatan,BtnBatal);
            }
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    //
    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        ChkInput1.setSelected(true);
        ChkInput2.setSelected(true);
        ChkInput3.setSelected(true);
        isForm(); 
        isForm2();
        isForm3();
        isForm4();
        TSuhu.setText("");    
        TTensi.setText("");
        TKeluhan.setText("");
        TPemeriksaan.setText("");
        TPenilaian.setText("");
        Instruksi.setText("");
        Instruksi1.setText("");
        TAlergi.setText("");
        TAlergi1.setText("");
        Anamnesa.setText("");
        Anjuran.setText("");
        Evaluasi.setText("");
        Diagnosis.setText("");
        Tatalaksana.setText("");
        PemeriksaanFisik.setText("");
        PemeriksaanPenunjang.setText("");
        KetSuspek.setText("-");
        TBerat.setText("");
        TTinggi.setText("");
        TNadi.setText("");
        TRespirasi.setText("");
        TGCS.setText("");
        TindakLanjut.setText("");
//        TRpd.setText("");
//        TRpk.setText("");
//        TRpo.setText("");
//        TRpd1.setText("");
//        TRpk1.setText("");
//        TRpo1.setText("");
        TSpo2.setText("");
        Operasi.setText("");
        Operasi1.setText("");
        TTinggi_uteri.setText("");
        TLetak.setText("");
        TDenyut.setText("");
        TVulva.setText("");
        TPortio.setText("");
        TTebal.setText("");
        TPembukaan.setText("");
        TPenurunan.setText("");
        TDenominator.setText("");
        TKualitas_mnt.setText("");
        TKualitas_dtk.setText("");
        TInspeksi.setText("");
        TInspeksiVulva.setText("");
        TInspekuloGine.setText("");
        TVulvaInspekulo.setText("");
        TPortioInspekulo.setText("");
        TSondage.setText("");
        TPortioDalam.setText("");
        TBentuk.setText("");
        TCavumUteri.setText("");
        TUkuran.setText("");
        TAdnexaKanan.setText("");
        TAdnexaKiri.setText("");
        TCavumDouglas.setText("");
        Catatan.setText("");
        cmbKesadaran.setSelectedIndex(0);
        TNoRw.requestFocus();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if(tabModeDr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    Sequel.AutoComitFalse();
                    sukses=true;
                    ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
                    for(i=0;i<tbRawatDr.getRowCount();i++){
                        if(tbRawatDr.getValueAt(i,0).toString().equals("true")){                            
                            if(Sequel.cariRegistrasi(tbRawatDr.getValueAt(i,1).toString())>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                tbRawatDr.setValueAt(false,i,0);
                                TCari.requestFocus();
                            }else{
                                if(Sequel.queryutf("delete from rawat_jl_dr where no_rawat='"+tbRawatDr.getValueAt(i,1).toString()+
                                        "' and kd_jenis_prw='"+tbRawatDr.getValueAt(i,10)+
                                        "' and kd_dokter='"+tbRawatDr.getValueAt(i,5).toString()+
                                        "' and tgl_perawatan='"+tbRawatDr.getValueAt(i,7).toString()+
                                        "' and jam_rawat='"+tbRawatDr.getValueAt(i,8).toString()+"'")==true){
                                    ttljmdokter=ttljmdokter+Double.parseDouble(tbRawatDr.getValueAt(i,11).toString());
                                    ttlkso=ttlkso+Double.parseDouble(tbRawatDr.getValueAt(i,12).toString());
                                    ttlpendapatan=ttlpendapatan+Double.parseDouble(tbRawatDr.getValueAt(i,9).toString());
                                    ttljasasarana=ttljasasarana+Double.parseDouble(tbRawatDr.getValueAt(i,13).toString());
                                    ttlbhp=ttlbhp+Double.parseDouble(tbRawatDr.getValueAt(i,14).toString());
                                    ttlmenejemen=ttlmenejemen+Double.parseDouble(tbRawatDr.getValueAt(i,15).toString());
                                }else{
                                    sukses=false;
                                }
                            }
                        }                            
                    }
                    
                    if(sukses==true){
                        Sequel.queryu("delete from tampjurnal");
                        if(ttlpendapatan>0){
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");   
                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Jalan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'");                             
                        }
                        if(ttljmdokter>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");                              
                        }
                        if(ttlkso>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");                               
                        }
                        if(ttlmenejemen>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");                                
                        }
                        if(ttljasasarana>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");                              
                        }
                        if(ttlbhp>0){
                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");                            
                        }
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                    }
                      
                    if(sukses==true){
                        Sequel.Commit();
                        for(i=0;i<tbRawatDr.getRowCount();i++){
                            if(tbRawatDr.getValueAt(i,0).toString().equals("true")){ 
                                tabModeDr.removeRow(i);
                                i--;
                            }
                        }
                        LCount.setText(""+tabModeDr.getRowCount());
                    }else{
                        sukses=false;
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue();
                }   break;
            case 1:
                if(tabModePr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    Sequel.AutoComitFalse();
                    sukses=true;
                    ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
                    for(i=0;i<tbRawatPr.getRowCount();i++){
                        if(tbRawatPr.getValueAt(i,0).toString().equals("true")){
                            if(Sequel.cariRegistrasi(tbRawatPr.getValueAt(i,1).toString())>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                tbRawatPr.setValueAt(false,i,0);
                                TCari.requestFocus();
                            }else{
                                if(Sequel.queryutf("delete from rawat_jl_pr where no_rawat='"+tbRawatPr.getValueAt(i,1).toString()+
                                        "' and kd_jenis_prw='"+tbRawatPr.getValueAt(i,10)+
                                        "' and nip='"+tbRawatPr.getValueAt(i,5).toString()+
                                        "' and tgl_perawatan='"+tbRawatPr.getValueAt(i,7).toString()+
                                        "' and jam_rawat='"+tbRawatPr.getValueAt(i,8).toString()+"' ")==true){
                                    ttljmperawat=ttljmperawat+Double.parseDouble(tbRawatPr.getValueAt(i,11).toString());
                                    ttlkso=ttlkso+Double.parseDouble(tbRawatPr.getValueAt(i,12).toString());
                                    ttlpendapatan=ttlpendapatan+Double.parseDouble(tbRawatPr.getValueAt(i,9).toString());
                                    ttljasasarana=ttljasasarana+Double.parseDouble(tbRawatPr.getValueAt(i,13).toString());
                                    ttlbhp=ttlbhp+Double.parseDouble(tbRawatPr.getValueAt(i,14).toString());
                                    ttlmenejemen=ttlmenejemen+Double.parseDouble(tbRawatPr.getValueAt(i,15).toString());
                                }else{
                                    sukses=false;
                                }
                            }
                        }
                    }
                    
                    if(sukses==true){
                        Sequel.queryu("delete from tampjurnal");
                        if(ttlpendapatan>0){
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");   
                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Jalan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'");                             
                        }
                        if(ttljmperawat>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");                              
                        }
                        if(ttlkso>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");                               
                        }
                        if(ttlmenejemen>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");                                
                        }
                        if(ttljasasarana>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");                              
                        }
                        if(ttlbhp>0){
                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");                            
                        }
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                    }
                        
                    if(sukses==true){
                        Sequel.Commit();
                        for(i=0;i<tbRawatPr.getRowCount();i++){
                            if(tbRawatPr.getValueAt(i,0).toString().equals("true")){ 
                                tabModePr.removeRow(i);
                                i--;
                            }
                        }
                        LCount.setText(""+tabModePr.getRowCount());
                    }else{
                        sukses=false;
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue();
                }   break;
            case 2:
                if(tabModeDrPr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    Sequel.AutoComitFalse();
                    sukses=true;
                    ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
                    for(i=0;i<tbRawatDrPr.getRowCount();i++){
                        if(tbRawatDrPr.getValueAt(i,0).toString().equals("true")){                            
                            if(Sequel.cariRegistrasi(tbRawatDrPr.getValueAt(i,1).toString())>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                tbRawatDrPr.setValueAt(false,i,0);
                                TCari.requestFocus();
                            }else{
                                if(Sequel.queryutf("delete from rawat_jl_drpr where no_rawat='"+tbRawatDrPr.getValueAt(i,1).toString()+
                                        "' and kd_jenis_prw='"+tbRawatDrPr.getValueAt(i,12)+
                                        "' and kd_dokter='"+tbRawatDrPr.getValueAt(i,5).toString()+
                                        "' and nip='"+tbRawatDrPr.getValueAt(i,7).toString()+
                                        "' and tgl_perawatan='"+tbRawatDrPr.getValueAt(i,9).toString()+
                                        "' and jam_rawat='"+tbRawatDrPr.getValueAt(i,10).toString()+"' ")==true){
                                    ttljmdokter=ttljmdokter+Double.parseDouble(tbRawatDrPr.getValueAt(i,13).toString());
                                    ttljmperawat=ttljmperawat+Double.parseDouble(tbRawatDrPr.getValueAt(i,14).toString());
                                    ttlkso=ttlkso+Double.parseDouble(tbRawatDrPr.getValueAt(i,15).toString());
                                    ttlpendapatan=ttlpendapatan+Double.parseDouble(tbRawatDrPr.getValueAt(i,11).toString());
                                    ttljasasarana=ttljasasarana+Double.parseDouble(tbRawatDrPr.getValueAt(i,16).toString());
                                    ttlbhp=ttlbhp+Double.parseDouble(tbRawatDrPr.getValueAt(i,17).toString());
                                    ttlmenejemen=ttlmenejemen+Double.parseDouble(tbRawatDrPr.getValueAt(i,18).toString());
                                }else{
                                    sukses=false;
                                }
                            }
                        }                            
                    }
                    
                    if(sukses==true){
                        Sequel.queryu("delete from tampjurnal");
                        if(ttlpendapatan>0){
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");   
                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Jalan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'");                             
                        }
                        if(ttljmdokter>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");                              
                        }
                        if(ttljmperawat>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");                              
                        }
                        if(ttlkso>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");                               
                        }
                        if(ttlmenejemen>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");                                
                        }
                        if(ttljasasarana>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");                              
                        }
                        if(ttlbhp>0){
                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");                            
                        }
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                    }
                        
                    if(sukses==true){
                        Sequel.Commit();
                        for(i=0;i<tbRawatDrPr.getRowCount();i++){
                            if(tbRawatDrPr.getValueAt(i,0).toString().equals("true")){ 
                                tabModeDrPr.removeRow(i);
                                i--;
                            }
                        }
                        LCount.setText(""+tabModeDrPr.getRowCount());
                    }else{
                        sukses=false;
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue();
                }   break;
//            case 3:
//                if(tabModePemeriksaan.getRowCount()==0){
//                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
//                    TNoRw.requestFocus();
//                }else{
//                    for(i=0;i<tbPemeriksaan.getRowCount();i++){
//                        if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
//                            if(akses.getkode().equals("Admin Utama")){
//                                Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"+tbPemeriksaan.getValueAt(i,1).toString()+
//                                        "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(i,4).toString()+
//                                        "' and jam_rawat='"+tbPemeriksaan.getValueAt(i,5).toString()+"' ");
//                                tabModePemeriksaan.removeRow(i);
//                                i--;
//                            }else{
//                                if(akses.getkode().equals(tbPemeriksaan.getValueAt(i,23).toString())){
//                                    Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"+tbPemeriksaan.getValueAt(i,1).toString()+
//                                            "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(i,4).toString()+
//                                            "' and jam_rawat='"+tbPemeriksaan.getValueAt(i,5).toString()+"' ");
//                                    tabModePemeriksaan.removeRow(i);
//                                    i--;
//                                }else{
//                                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter/petugas yang bersangkutan..!!");
//                                }
//                            }
//                        }
//                    }
//                    LCount.setText(""+tabModePemeriksaan.getRowCount());
//                }   break;
                case 4:
                if(tabModePemeriksaanRM.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan centang data yang mau dihapus...!!!!");
                    TNoRw.requestFocus();
                }else{
                    for(i=0;i<tbPemeriksaanRM.getRowCount();i++){
                        if(tbPemeriksaanRM.getValueAt(i,0).toString().equals("true")){
                            if(akses.getkode().equals("Admin Utama")){
                                Sequel.queryu("delete from pemeriksaan_ralan_rehab where no_rawat='"+tbPemeriksaanRM.getValueAt(i,1).toString()+
                                        "' and tgl_perawatan='"+tbPemeriksaanRM.getValueAt(i,4).toString()+
                                        "' and jam_rawat='"+tbPemeriksaanRM.getValueAt(i,5).toString()+"' ");
                                tabModePemeriksaanRM.removeRow(i);
                                i--;
                            }else{
                                if(akses.getkode().equals(tbPemeriksaanRM.getValueAt(i,22).toString())){
                                    Sequel.queryu("delete from pemeriksaan_ralan_rehab where no_rawat='"+tbPemeriksaanRM.getValueAt(i,1).toString()+
                                            "' and tgl_perawatan='"+tbPemeriksaanRM.getValueAt(i,4).toString()+
                                            "' and jam_rawat='"+tbPemeriksaanRM.getValueAt(i,5).toString()+"' ");
                                    tabModePemeriksaan.removeRow(i);
                                    i--;
                                }else{
                                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        }
                    }
                    LCount.setText(""+tabModePemeriksaan.getRowCount());
                }   break;
            case 5:
                if(tabModeObstetri.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    for(i=0;i<tbPemeriksaanObstetri.getRowCount();i++){
                        if(tbPemeriksaanObstetri.getValueAt(i,0).toString().equals("true")){
                            Sequel.queryu("delete from pemeriksaan_obstetri_ralan where no_rawat='"+tbPemeriksaanObstetri.getValueAt(i,1).toString()+
                                    "' and tgl_perawatan='"+tbPemeriksaanObstetri.getValueAt(i,4).toString()+
                                    "' and jam_rawat='"+tbPemeriksaanObstetri.getValueAt(i,5).toString()+"' ");
                            tabModeObstetri.removeRow(i);
                            i--;
                        }
                    }
                    LCount.setText(""+tabModeObstetri.getRowCount());
                }   break;
            case 6:
                if(tabModeGinekologi.getRowCount()==0){
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!");
                    TNoRw.requestFocus();                    
                }else {
                    for(i=0;i<tbPemeriksaanGinekologi.getRowCount();i++){
                        if(tbPemeriksaanGinekologi.getValueAt(i,0).toString().equals("true")){
                            Sequel.queryu("delete from pemeriksaan_ginekologi_ralan where no_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,1).toString()+
                                    "' and tgl_perawatan='"+tbPemeriksaanGinekologi.getValueAt(i,4).toString()+
                                    "' and jam_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,5).toString()+"' ");
                            tabModeGinekologi.removeRow(i);
                            i--;
                        }
                    }
                    LCount.setText(""+tabModeGinekologi.getRowCount());
                }   break;
            case 7:
                panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Ralan",TCari.getText().trim());
                panelDiagnosa1.hapus();
                LCount.setText(panelDiagnosa1.getRecord()+"");
                break;
            case 8:
                if(TabModeCatatan.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    for(i=0;i<tbCatatan.getRowCount();i++){
                        if(tbCatatan.getValueAt(i,0).toString().equals("true")){
                            Sequel.queryu("delete from catatan_perawatan where no_rawat='"+tbCatatan.getValueAt(i,1).toString()+
                                    "' and tanggal='"+tbCatatan.getValueAt(i,4).toString()+
                                    "' and jam='"+tbCatatan.getValueAt(i,5).toString()+
                                    "' and kd_dokter='"+tbCatatan.getValueAt(i,6).toString()+"' ");
                            TabModeCatatan.removeRow(i);
                            i--;
                        }
                    }
                    LCount.setText(""+TabModeCatatan.getRowCount());
                }   break;
            default:
                break;
        }

        BtnBatalActionPerformed(evt);
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if(tabModeDr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModeDr.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanDr.jasper","report","::[ Data Rawat Jalan Yang Ditangani Dokter ]::",
                            "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                    "jns_perawatan.nm_perawatan,rawat_jl_dr.kd_dokter,dokter.nm_dokter,"+
                                    "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.biaya_rawat " +
                                    "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                                    "dokter inner join rawat_jl_dr "+
                                    "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                    "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                                    "where "+tgl+" and rawat_jl_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_jl_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and tgl_perawatan like '%"+TCari.getText().trim()+"%' "+
                                            " order by rawat_jl_dr.no_rawat desc",param);
                    
                }   break;
            case 1:
                if(tabModePr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModePr.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanPr.jasper","report","::[ Data Rawat Jalan Yang Ditangani Perawat ]::",
                            "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                    "jns_perawatan.nm_perawatan,rawat_jl_pr.nip,petugas.nama,"+
                                    "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat,rawat_jl_pr.biaya_rawat " +
                                    "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                                    "petugas inner join rawat_jl_pr "+
                                    "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                    "and rawat_jl_pr.nip=petugas.nip where  "+
                                    tgl+"and rawat_jl_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_jl_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_jl_pr.tgl_perawatan like '%"+TCari.getText().trim()+"%'  "+
                                            "order by rawat_jl_pr.no_rawat desc",param);
                }   break;
            case 2:
                if(tabModeDrPr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModeDrPr.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanDrPr.jasper","report","::[ Data Rawat Jalan Yang Ditangani Dokter ]::",
                            "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                    "jns_perawatan.nm_perawatan,rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,"+
                                    "rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.biaya_rawat " +
                                    "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                                    "dokter inner join rawat_jl_drpr inner join "+
                                    "petugas on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                    "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                                    "and rawat_jl_drpr.nip=petugas.nip "+
                                    "where "+tgl+" and rawat_jl_drpr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_jl_drpr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_jl_drpr.nip like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and tgl_perawatan like '%"+TCari.getText().trim()+"%' "+
                                            " order by rawat_jl_drpr.no_rawat desc",param);
                }   break;
            case 3:
                if(tabModePemeriksaan.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModePemeriksaan.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" pemeriksaan_ralan.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanPemeriksaan.jasper","report","::[ Data Pemeriksaan Rawat Jalan ]::",
                            "select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi, " +
                            "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, " +
                            "pemeriksaan_ralan.berat,pemeriksaan_ralan.spo2,pemeriksaan_ralan.gcs,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, " +
                            "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.lingkar_perut,"+
                            "pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama "+
                            "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join pemeriksaan_ralan on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat "+
                            "inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where  "+
                            tgl+"and (pemeriksaan_ralan.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            "pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or pemeriksaan_ralan.alergi like '%"+TCari.getText().trim()+"%' or "+
                            "pemeriksaan_ralan.keluhan like '%"+TCari.getText().trim()+"%' or pemeriksaan_ralan.penilaian like '%"+TCari.getText().trim()+"%' or "+
                            "pemeriksaan_ralan.pemeriksaan like '%"+TCari.getText().trim()+"%' or pegawai.nama like '%"+TCari.getText().trim()+"%') "+
                            "order by pemeriksaan_ralan.no_rawat desc",param);
                }   break;
            case 5:
                if(tabModeObstetri.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModeObstetri.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" pemeriksaan_obstetri_ralan.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanObstetri.jasper","report","::[ Data Pemeriksaan Obstetri Rawat Jalan ]::",
                            "select pemeriksaan_obstetri_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat,pemeriksaan_obstetri_ralan.tinggi_uteri,pemeriksaan_obstetri_ralan.janin,pemeriksaan_obstetri_ralan.letak, " +
                            "pemeriksaan_obstetri_ralan.panggul,pemeriksaan_obstetri_ralan.denyut,pemeriksaan_obstetri_ralan.kontraksi, " +
                            "pemeriksaan_obstetri_ralan.kualitas_mnt,pemeriksaan_obstetri_ralan.kualitas_dtk,pemeriksaan_obstetri_ralan.fluksus,pemeriksaan_obstetri_ralan.albus, " +
                            "pemeriksaan_obstetri_ralan.vulva,pemeriksaan_obstetri_ralan.portio,pemeriksaan_obstetri_ralan.dalam, pemeriksaan_obstetri_ralan.tebal, pemeriksaan_obstetri_ralan.arah, pemeriksaan_obstetri_ralan.pembukaan," +
                            "pemeriksaan_obstetri_ralan.penurunan, pemeriksaan_obstetri_ralan.denominator, pemeriksaan_obstetri_ralan.ketuban, pemeriksaan_obstetri_ralan.feto " +
                            "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ralan "+
                            "on pemeriksaan_obstetri_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                            tgl+"and pemeriksaan_obstetri_ralan.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or  "+
                            tgl+"and pemeriksaan_obstetri_ralan.tinggi_uteri like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_obstetri_ralan.janin like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_obstetri_ralan.letak like '%"+TCari.getText().trim()+"%' "+
                            "order by pemeriksaan_obstetri_ralan.no_rawat desc",param);
                }   break;
            case 6:
                if(tabModeGinekologi.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModeGinekologi.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" pemeriksaan_ginekologi_ralan.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanGinekologi.jasper","report","::[ Data Pemeriksaan Ginekologi Rawat Jalan ]::",
                            "select pemeriksaan_ginekologi_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat,pemeriksaan_ginekologi_ralan.inspeksi,pemeriksaan_ginekologi_ralan.inspeksi_vulva,pemeriksaan_ginekologi_ralan.inspekulo_gine, " +
                            "pemeriksaan_ginekologi_ralan.fluxus_gine,pemeriksaan_ginekologi_ralan.fluor_gine,pemeriksaan_ginekologi_ralan.vulva_inspekulo, " +
                            "pemeriksaan_ginekologi_ralan.portio_inspekulo,pemeriksaan_ginekologi_ralan.sondage,pemeriksaan_ginekologi_ralan.portio_dalam,pemeriksaan_ginekologi_ralan.bentuk, " +
                            "pemeriksaan_ginekologi_ralan.cavum_uteri,pemeriksaan_ginekologi_ralan.mobilitas,pemeriksaan_ginekologi_ralan.ukuran, pemeriksaan_ginekologi_ralan.nyeri_tekan, pemeriksaan_ginekologi_ralan.adnexa_kanan, pemeriksaan_ginekologi_ralan.adnexa_kiri," +
                            "pemeriksaan_ginekologi_ralan.cavum_douglas " +
                            "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ralan "+
                            "on pemeriksaan_ginekologi_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                            tgl+"and pemeriksaan_ginekologi_ralan.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or  "+
                            tgl+"and pemeriksaan_ginekologi_ralan.inspeksi like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_ginekologi_ralan.inspeksi_vulva like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_ginekologi_ralan.inspekulo_gine like '%"+TCari.getText().trim()+"%' "+
                            "order by pemeriksaan_ginekologi_ralan.no_rawat desc",param);
                }   
                break;
            case 7:
                if(akses.getdiagnosa_pasien()==true){
                    panelDiagnosa1.cetak();
                } 
                break;
            case 8:
                if(TabModeCatatan.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(TabModeCatatan.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" catatan_perawatan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptCatatanDokter.jasper","report","::[ Data Catatan Dokter ]::",
                            "select catatan_perawatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "catatan_perawatan.tanggal,catatan_perawatan.jam,catatan_perawatan.kd_dokter,dokter.nm_dokter,"+
                            "catatan_perawatan.catatan from pasien inner join reg_periksa inner join catatan_perawatan inner join dokter "+
                            "on catatan_perawatan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "and catatan_perawatan.kd_dokter=dokter.kd_dokter where  "+
                            tgl+" and catatan_perawatan.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+" and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+" and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or  "+
                            tgl+" and catatan_perawatan.catatan like '%"+TCari.getText().trim()+"%' or "+
                            tgl+" and catatan_perawatan.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+" and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                            "order by catatan_perawatan.no_rawat desc",param);
                }   break;    
            default:
                break;
        }

        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dokter.dispose();
        pasien.dispose();
        try {
            i=JOptionPane.showConfirmDialog(null, "Mau skalian update status pasien sudah diperiksa ????","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if(i==JOptionPane.YES_OPTION){
                Sequel.mengedit("reg_periksa","no_rawat=?","stts=?",2,new String[]{"Sudah",TNoRw.getText()});
            }
        } catch (Exception e) {
        }
        dispose();
        emptTeks();
}//GEN-LAST:event_BtnKeluarActionPerformed

    
    //
    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TCariPasien.setText("");
        TampilkanData();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TampilkanData();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    if(TabRawatTindakanDokter.getSelectedIndex()==0){
                        tbTindakan.requestFocus();
                    }else if(TabRawatTindakanDokter.getSelectedIndex()==1){
                        tbRawatDr.requestFocus();
                    }
                    break;
                case 1:
                    if(TabRawatTindakanPetugas.getSelectedIndex()==0){
                        tbTindakan2.requestFocus();
                    }else if(TabRawatTindakanPetugas.getSelectedIndex()==1){
                        tbRawatPr.requestFocus();
                    }
                    break;
                case 2:
                    if(TabRawatTindakanDokterPetugas.getSelectedIndex()==0){
                        tbTindakan3.requestFocus();
                    }else if(TabRawatTindakanDokterPetugas.getSelectedIndex()==1){
                        tbRawatDrPr.requestFocus();
                    }
                    break;
                case 3:
                    tbPemeriksaan.requestFocus();
                    break;
                case 4:
                    tbPemeriksaanRM.requestFocus();
                    break;
                case 5:
                    tbPemeriksaanObstetri.requestFocus();
                    break;
                case 6:
                    tbPemeriksaanGinekologi.requestFocus();
                    break;
                default:
                    break;
            }
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        TampilkanData();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(true);
                TCari.setPreferredSize(new Dimension(207,23));            
                TabRawatTindakanDokterMouseClicked(null);
                break;
            case 1:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(true); 
                TCari.setPreferredSize(new Dimension(207,23));
                TabRawatTindakanPetugasMouseClicked(null);
                break;
            case 2:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(true); 
                TCari.setPreferredSize(new Dimension(207,23));
                TabRawatTindakanDokterPetugasMouseClicked(null);
                break;
            case 3:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(false); 
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                tampilPemeriksaan();
                break;
            case 4:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(false); 
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                tampilPemeriksaanRM();
                break;
            case 5:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(false); 
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                tampilPemeriksaanObstetri();
                break;
            case 6:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(false); 
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                tampilPemeriksaanGinekologi();
                break;
            case 7:
                BtnSimpan.setEnabled(akses.getdiagnosa_pasien());
                BtnHapus.setEnabled(akses.getdiagnosa_pasien());
                BtnEdit.setEnabled(akses.getdiagnosa_pasien());
                BtnPrint.setEnabled(akses.getdiagnosa_pasien());
                BtnTambahTindakan.setVisible(false);
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                if(akses.getdiagnosa_pasien()==true){
                    panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""), Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Ralan",TCari.getText().trim());
                    panelDiagnosa1.pilihTab();
                    LCount.setText(panelDiagnosa1.getRecord()+"");
                } 
                break;
            case 8:
                BtnSimpan.setEnabled(akses.getcatatan_perawatan());
                BtnHapus.setEnabled(akses.getcatatan_perawatan());
                BtnEdit.setEnabled(akses.getcatatan_perawatan());
                BtnPrint.setEnabled(akses.getcatatan_perawatan());
                BtnTambahTindakan.setVisible(false);
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                if(akses.getcatatan_perawatan()==true){
                    tampilCatatan();
                } 
                break;
            default:
                break;
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbRawatDrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrMouseClicked
        if(tabModeDr.getRowCount()!=0){
            try {
                getDataDr();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbRawatDrMouseClicked

    private void tbRawatPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatPrMouseClicked
        if(tabModePr.getRowCount()!=0){
            try {
                getDataPr();
            } catch (java.lang.NullPointerException e) {
            }
            
        }
}//GEN-LAST:event_tbRawatPrMouseClicked

private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TDokter.setText(dokter.tampil3(KdDok.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekDokterActionPerformed(null);
        }else{            
            Valid.pindah(evt,TNoRw,BtnSeekDokter);
        }
}//GEN-LAST:event_KdDokKeyPressed

private void BtnSeekDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokterActionPerformed
        akses.setform("DlgRawatJalan");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnSeekDokterActionPerformed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TPerawat.setText(petugas.tampil3(kdptg.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,TNoRw,BtnSeekPetugas);
        }
}//GEN-LAST:event_kdptgKeyPressed

private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugasActionPerformed
        akses.setform("DlgRawatJalan");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnSeekPetugasActionPerformed

private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            switch (TabRawat.getSelectedIndex()) {
                case 3:
                    if(KodeDokter.getText().trim().equals("")||NamaDokter.getText().trim().equals("")){
                            Valid.textKosong(KodeDokter,"Dokter/Paramedis masih kosong...!!");
                    }else if((!TKeluhan.getText().trim().equals(""))||(!TPemeriksaan.getText().trim().equals(""))||
                            (!TSuhu.getText().trim().equals(""))||(!TTensi.getText().trim().equals(""))||
                            (!TAlergi.getText().trim().equals(""))||(!TTinggi.getText().trim().equals(""))||
                            (!TBerat.getText().trim().equals(""))||(!TRespirasi.getText().trim().equals(""))||
                            (!TNadi.getText().trim().equals(""))||(!TGCS.getText().trim().equals(""))||
                            (!TindakLanjut.getText().trim().equals(""))||(!TPenilaian.getText().trim().equals(""))||
                            (!TRpd.getText().trim().equals(""))||(!TRpk.getText().trim().equals(""))||
                            (!TRpo.getText().trim().equals(""))||(!TSpo2.getText().trim().equals(""))||(!Operasi.getText().trim().equals(""))||(!KodeDokter.getText().trim().equals(""))){
                        if(tbPemeriksaan.getSelectedRow()>-1){
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.mengedittf("pemeriksaan_ralan","no_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1)+
                                "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4)+
                                "' and jam_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5)+"'",
                                "no_rawat='"+TNoRw.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+"',"+
                                "keluhan='"+TKeluhan.getText()+"',pemeriksaan='"+TPemeriksaan.getText()+"',"+
                                "nadi='"+TNadi.getText()+"',respirasi='"+TRespirasi.getText()+"',"+
                                "tinggi='"+TTinggi.getText()+"',berat='"+TBerat.getText()+"',"+
                                "gcs='"+TGCS.getText()+"',kesadaran='"+cmbKesadaran.getSelectedItem()+"',alergi='"+TAlergi.getText()+"',imun_ke='"+cmbImun.getSelectedItem()+"',"+
                                "tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                "rtl='"+TindakLanjut.getText()+"',penilaian='"+TPenilaian.getText()+"',rpd='"+TRpd.getText()+"',rpk='"+TRpk.getText()+"',rpo='"+TRpo.getText()+"',spo='"+TSpo2.getText()+"',operasi='"+Operasi.getText()+"',nik='"+KodeDokter.getText()+"',instruksi='"+Instruksi.getText()+"'")==true){
                                        tbPemeriksaan.setValueAt(TNoRw.getText(),tbPemeriksaan.getSelectedRow(), 1);
                                        tbPemeriksaan.setValueAt(TNoRM.getText(),tbPemeriksaan.getSelectedRow(), 2);
                                        tbPemeriksaan.setValueAt(TPasien.getText(),tbPemeriksaan.getSelectedRow(), 3);
                                        tbPemeriksaan.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaan.getSelectedRow(), 4);
                                        tbPemeriksaan.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaan.getSelectedRow(), 5);
                                        tbPemeriksaan.setValueAt(TSuhu.getText(),tbPemeriksaan.getSelectedRow(), 6);
                                        tbPemeriksaan.setValueAt(TTensi.getText(),tbPemeriksaan.getSelectedRow(), 7);
                                        tbPemeriksaan.setValueAt(TNadi.getText(),tbPemeriksaan.getSelectedRow(), 8);
                                        tbPemeriksaan.setValueAt(TRespirasi.getText(),tbPemeriksaan.getSelectedRow(), 9);
                                        tbPemeriksaan.setValueAt(TTinggi.getText(),tbPemeriksaan.getSelectedRow(), 10);
                                        tbPemeriksaan.setValueAt(TBerat.getText(),tbPemeriksaan.getSelectedRow(), 11);
                                        tbPemeriksaan.setValueAt(TGCS.getText(),tbPemeriksaan.getSelectedRow(), 12);
                                        tbPemeriksaan.setValueAt(cmbKesadaran.getSelectedItem().toString(),tbPemeriksaan.getSelectedRow(), 13);
                                        tbPemeriksaan.setValueAt(TKeluhan.getText(),tbPemeriksaan.getSelectedRow(), 14);
                                        tbPemeriksaan.setValueAt(TPemeriksaan.getText(),tbPemeriksaan.getSelectedRow(), 15);
                                        tbPemeriksaan.setValueAt(TAlergi.getText(),tbPemeriksaan.getSelectedRow(), 16);
                                        tbPemeriksaan.setValueAt(cmbImun.getSelectedItem().toString(),tbPemeriksaan.getSelectedRow(), 17);
                                        tbPemeriksaan.setValueAt(TindakLanjut.getText(),tbPemeriksaan.getSelectedRow(), 18);
                                        tbPemeriksaan.setValueAt(TPenilaian.getText(),tbPemeriksaan.getSelectedRow(), 19);
                                        tbPemeriksaan.setValueAt(TRpd.getText(),tbPemeriksaan.getSelectedRow(), 20);
                                        tbPemeriksaan.setValueAt(TRpk.getText(),tbPemeriksaan.getSelectedRow(), 21);
                                        tbPemeriksaan.setValueAt(TRpo.getText(),tbPemeriksaan.getSelectedRow(), 22);
                                        tbPemeriksaan.setValueAt(TSpo2.getText(),tbPemeriksaan.getSelectedRow(), 23);
                                        tbPemeriksaan.setValueAt(Operasi.getText(),tbPemeriksaan.getSelectedRow(), 24);
                                        tbPemeriksaan.setValueAt(KodeDokter.getText(),tbPemeriksaan.getSelectedRow(), 25);
                                        tbPemeriksaan.setValueAt(NamaDokter.getText(),tbPemeriksaan.getSelectedRow(), 26);
                                        tbPemeriksaan.setValueAt(Instruksi.getText(),tbPemeriksaan.getSelectedRow(), 27);
                                        TSuhu.setText("");TTensi.setText("");TNadi.setText("");TRespirasi.setText("");
                                        TTinggi.setText("");TBerat.setText("");TGCS.setText("");TKeluhan.setText("");
                                        TPemeriksaan.setText("");TAlergi.setText("");cmbImun.setSelectedIndex(0);
                                        TindakLanjut.setText("");TPenilaian.setText("");TRpd.setText("");
                                        TRpk.setText("");TRpo.setText("");TSpo2.setText("");Operasi.setText("");Instruksi.setText("");
                                }   
                            }else{
                                if(akses.getkode().equals(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),25).toString())){
                                    if(Sequel.mengedittf("pemeriksaan_ralan","no_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1)+
                                "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4)+
                                "' and jam_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5)+"'",
                                "no_rawat='"+TNoRw.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+"',"+
                                "keluhan='"+TKeluhan.getText()+"',pemeriksaan='"+TPemeriksaan.getText()+"',"+
                                "nadi='"+TNadi.getText()+"',respirasi='"+TRespirasi.getText()+"',"+
                                "tinggi='"+TTinggi.getText()+"',berat='"+TBerat.getText()+"',"+
                                "gcs='"+TGCS.getText()+"',kesadaran='"+cmbKesadaran.getSelectedItem()+"',alergi='"+TAlergi.getText()+"',imun_ke='"+cmbImun.getSelectedItem()+"',"+
                                "tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                "rtl='"+TindakLanjut.getText()+"',penilaian='"+TPenilaian.getText()+"',rpd='"+TRpd.getText()+"',rpk='"+TRpk.getText()+"',rpo='"+TRpo.getText()+"',spo='"+TSpo2.getText()+"',operasi='"+Operasi.getText()+"',nik='"+KodeDokter.getText()+"',instruksi='"+Instruksi.getText()+"'")==true){
                                        tbPemeriksaan.setValueAt(TNoRw.getText(),tbPemeriksaan.getSelectedRow(), 1);
                                        tbPemeriksaan.setValueAt(TNoRM.getText(),tbPemeriksaan.getSelectedRow(), 2);
                                        tbPemeriksaan.setValueAt(TPasien.getText(),tbPemeriksaan.getSelectedRow(), 3);
                                        tbPemeriksaan.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaan.getSelectedRow(), 4);
                                        tbPemeriksaan.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaan.getSelectedRow(), 5);
                                        tbPemeriksaan.setValueAt(TSuhu.getText(),tbPemeriksaan.getSelectedRow(), 6);
                                        tbPemeriksaan.setValueAt(TTensi.getText(),tbPemeriksaan.getSelectedRow(), 7);
                                        tbPemeriksaan.setValueAt(TNadi.getText(),tbPemeriksaan.getSelectedRow(), 8);
                                        tbPemeriksaan.setValueAt(TRespirasi.getText(),tbPemeriksaan.getSelectedRow(), 9);
                                        tbPemeriksaan.setValueAt(TTinggi.getText(),tbPemeriksaan.getSelectedRow(), 10);
                                        tbPemeriksaan.setValueAt(TBerat.getText(),tbPemeriksaan.getSelectedRow(), 11);
                                        tbPemeriksaan.setValueAt(TGCS.getText(),tbPemeriksaan.getSelectedRow(), 12);
                                        tbPemeriksaan.setValueAt(cmbKesadaran.getSelectedItem().toString(),tbPemeriksaan.getSelectedRow(), 13);
                                        tbPemeriksaan.setValueAt(TKeluhan.getText(),tbPemeriksaan.getSelectedRow(), 14);
                                        tbPemeriksaan.setValueAt(TPemeriksaan.getText(),tbPemeriksaan.getSelectedRow(), 15);
                                        tbPemeriksaan.setValueAt(TAlergi.getText(),tbPemeriksaan.getSelectedRow(), 16);
                                        tbPemeriksaan.setValueAt(cmbImun.getSelectedItem().toString(),tbPemeriksaan.getSelectedRow(), 17);
                                        tbPemeriksaan.setValueAt(TindakLanjut.getText(),tbPemeriksaan.getSelectedRow(), 18);
                                        tbPemeriksaan.setValueAt(TPenilaian.getText(),tbPemeriksaan.getSelectedRow(), 19);
                                        tbPemeriksaan.setValueAt(TRpd.getText(),tbPemeriksaan.getSelectedRow(), 20);
                                        tbPemeriksaan.setValueAt(TRpk.getText(),tbPemeriksaan.getSelectedRow(), 21);
                                        tbPemeriksaan.setValueAt(TRpo.getText(),tbPemeriksaan.getSelectedRow(), 22);
                                        tbPemeriksaan.setValueAt(TSpo2.getText(),tbPemeriksaan.getSelectedRow(), 23);
                                        tbPemeriksaan.setValueAt(Operasi.getText(),tbPemeriksaan.getSelectedRow(), 24);
                                        tbPemeriksaan.setValueAt(KodeDokter.getText(),tbPemeriksaan.getSelectedRow(), 25);
                                        tbPemeriksaan.setValueAt(NamaDokter.getText(),tbPemeriksaan.getSelectedRow(), 26);
                                        tbPemeriksaan.setValueAt(Instruksi.getText(),tbPemeriksaan.getSelectedRow(), 27);
                                        TSuhu.setText("");TTensi.setText("");TNadi.setText("");TRespirasi.setText("");
                                        TTinggi.setText("");TBerat.setText("");TGCS.setText("");TKeluhan.setText("");
                                        TPemeriksaan.setText("");TAlergi.setText("");cmbImun.setSelectedIndex(0);
                                        TindakLanjut.setText("");TPenilaian.setText("");TRpd.setText("");
                                        TRpk.setText("");TRpo.setText("");TSpo2.setText("");Operasi.setText("");Instruksi.setText("");
                                    }   
                                }else{
                                    JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }                         
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break;
                case 4:
                    if(KodeDokter.getText().trim().equals("")||NamaDokter.getText().trim().equals("")){
                            Valid.textKosong(KodeDokter,"Dokter/Paramedis masih kosong...!!");
                    }else if((!Anamnesa.getText().trim().equals(""))||(!PemeriksaanFisik.getText().trim().equals(""))||
                            (!PemeriksaanPenunjang.getText().trim().equals(""))||(!Anjuran.getText().trim().equals(""))||
                            (!Diagnosis.getText().trim().equals(""))||(!Tatalaksana.getText().trim().equals(""))||
                            (!Evaluasi.getText().trim().equals(""))||(!KetSuspek.getText().trim().equals(""))||
                            (!TRpd1.getText().trim().equals(""))||(!TRpk1.getText().trim().equals(""))||
                            (!TRpo1.getText().trim().equals(""))||(!TAlergi1.getText().trim().equals(""))||(!Instruksi1.getText().trim().equals(""))||(!Operasi1.getText().trim().equals(""))||(!KodeDokter.getText().trim().equals(""))){
                        if(tbPemeriksaanRM.getSelectedRow()>-1){
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.mengedittf("pemeriksaan_ralan_rehab","no_rawat='"+tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),1)+
                                "' and tgl_perawatan='"+tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),4)+
                                "' and jam_rawat='"+tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),5)+"'",
                                "no_rawat='"+TNoRw.getText()+"',kesadaran='"+cmbKesadaran.getSelectedItem()+"',anamnesa='"+Anamnesa.getText()+"',pemeriksaanfisik='"+PemeriksaanFisik.getText()+"',"+
                                "pemeriksaanpenunjang='"+PemeriksaanPenunjang.getText()+"',anjuran='"+Anjuran.getText()+"',"+
                                "diagnosis='"+Diagnosis.getText()+"',tatalaksana='"+Tatalaksana.getText()+"',"+
                                "evaluasi='"+Evaluasi.getText()+"',suspek='"+Suspek.getSelectedItem()+"',"+
                                "ket_suspek='"+KetSuspek.getText()+"',rpd='"+TRpd1.getText()+"',rpk='"+TRpk1.getText()+"',rpo='"+TRpo1.getText()+"',alergi='"+TAlergi1.getText()+"',"+
                                "tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                "instruksi='"+Instruksi1.getText()+"',operasi='"+Operasi1.getText()+"',nik='"+KodeDokter.getText()+"'")==true){
                                        tbPemeriksaanRM.setValueAt(TNoRw.getText(),tbPemeriksaanRM.getSelectedRow(), 1);
                                        tbPemeriksaanRM.setValueAt(TNoRM.getText(),tbPemeriksaanRM.getSelectedRow(), 2);
                                        tbPemeriksaanRM.setValueAt(TPasien.getText(),tbPemeriksaanRM.getSelectedRow(), 3);
                                        tbPemeriksaanRM.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaanRM.getSelectedRow(), 4);
                                        tbPemeriksaanRM.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaanRM.getSelectedRow(), 5);
                                        tbPemeriksaanRM.setValueAt(cmbKesadaran.getSelectedItem().toString(),tbPemeriksaanRM.getSelectedRow(), 6);
                                        tbPemeriksaanRM.setValueAt(Anamnesa.getText(),tbPemeriksaanRM.getSelectedRow(), 7);
                                        tbPemeriksaanRM.setValueAt(PemeriksaanFisik.getText(),tbPemeriksaanRM.getSelectedRow(), 8);
                                        tbPemeriksaanRM.setValueAt(PemeriksaanPenunjang.getText(),tbPemeriksaanRM.getSelectedRow(), 9);
                                        tbPemeriksaanRM.setValueAt(Anjuran.getText(),tbPemeriksaanRM.getSelectedRow(), 10);
                                        tbPemeriksaanRM.setValueAt(Diagnosis.getText(),tbPemeriksaanRM.getSelectedRow(), 11);
                                        tbPemeriksaanRM.setValueAt(Tatalaksana.getText(),tbPemeriksaanRM.getSelectedRow(), 12);
                                        tbPemeriksaanRM.setValueAt(Evaluasi.getText(),tbPemeriksaanRM.getSelectedRow(), 13);                                        
                                        tbPemeriksaanRM.setValueAt(Suspek.getSelectedItem().toString(),tbPemeriksaanRM.getSelectedRow(), 14);
                                        tbPemeriksaanRM.setValueAt(KetSuspek.getText(),tbPemeriksaanRM.getSelectedRow(), 15);
                                        tbPemeriksaanRM.setValueAt(TRpd1.getText(),tbPemeriksaanRM.getSelectedRow(), 16);
                                        tbPemeriksaanRM.setValueAt(TRpk1.getText(),tbPemeriksaanRM.getSelectedRow(), 17);
                                        tbPemeriksaanRM.setValueAt(TRpo1.getText(),tbPemeriksaanRM.getSelectedRow(), 18);
                                        tbPemeriksaanRM.setValueAt(TAlergi1.getText(),tbPemeriksaanRM.getSelectedRow(), 19);
                                        tbPemeriksaanRM.setValueAt(Instruksi1.getText(),tbPemeriksaanRM.getSelectedRow(), 20);
                                        tbPemeriksaanRM.setValueAt(Operasi1.getText(),tbPemeriksaanRM.getSelectedRow(), 21);
                                        tbPemeriksaanRM.setValueAt(KodeDokter.getText(),tbPemeriksaanRM.getSelectedRow(), 22);                                        
                                }   
                            }else{
                                if(akses.getkode().equals(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),22).toString())){
                                    if(Sequel.mengedittf("pemeriksaan_ralan_rehab","no_rawat='"+tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),1)+
                                "' and tgl_perawatan='"+tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),4)+
                                "' and jam_rawat='"+tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),5)+"'",
                                "no_rawat='"+TNoRw.getText()+"',kesadaran='"+cmbKesadaran.getSelectedItem()+"',anamnesa='"+Anamnesa.getText()+"',pemeriksaanfisik='"+PemeriksaanFisik.getText()+"',"+
                                "pemeriksaanpenunjang='"+PemeriksaanPenunjang.getText()+"',anjuran='"+Anjuran.getText()+"',"+
                                "diagnosis='"+Diagnosis.getText()+"',tatalaksana='"+Tatalaksana.getText()+"',"+
                                "evaluasi='"+Evaluasi.getText()+"',suspek='"+Suspek.getSelectedItem()+"',"+
                                "ket_suspek='"+KetSuspek.getText()+"',rpd='"+TRpd1.getText()+"',rpk='"+TRpk1.getText()+"',rpo='"+TRpo1.getText()+"',alergi='"+TAlergi1.getText()+"',"+
                                "tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                "instruksi='"+Instruksi1.getText()+"',operasi='"+Operasi1.getText()+"',nik='"+KodeDokter.getText()+"'")==true){
                                        tbPemeriksaanRM.setValueAt(TNoRw.getText(),tbPemeriksaanRM.getSelectedRow(), 1);
                                        tbPemeriksaanRM.setValueAt(TNoRM.getText(),tbPemeriksaanRM.getSelectedRow(), 2);
                                        tbPemeriksaanRM.setValueAt(TPasien.getText(),tbPemeriksaanRM.getSelectedRow(), 3);
                                        tbPemeriksaanRM.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaanRM.getSelectedRow(), 4);
                                        tbPemeriksaanRM.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaanRM.getSelectedRow(), 5);
                                        tbPemeriksaanRM.setValueAt(cmbKesadaran.getSelectedItem().toString(),tbPemeriksaanRM.getSelectedRow(), 6);
                                        tbPemeriksaanRM.setValueAt(Anamnesa.getText(),tbPemeriksaanRM.getSelectedRow(), 7);
                                        tbPemeriksaanRM.setValueAt(PemeriksaanFisik.getText(),tbPemeriksaanRM.getSelectedRow(), 8);
                                        tbPemeriksaanRM.setValueAt(PemeriksaanPenunjang.getText(),tbPemeriksaanRM.getSelectedRow(), 9);
                                        tbPemeriksaanRM.setValueAt(Anjuran.getText(),tbPemeriksaanRM.getSelectedRow(), 10);
                                        tbPemeriksaanRM.setValueAt(Diagnosis.getText(),tbPemeriksaanRM.getSelectedRow(), 11);
                                        tbPemeriksaanRM.setValueAt(Tatalaksana.getText(),tbPemeriksaanRM.getSelectedRow(), 12);
                                        tbPemeriksaanRM.setValueAt(Evaluasi.getText(),tbPemeriksaanRM.getSelectedRow(), 13);                                        
                                        tbPemeriksaanRM.setValueAt(Suspek.getSelectedItem().toString(),tbPemeriksaanRM.getSelectedRow(), 14);
                                        tbPemeriksaanRM.setValueAt(KetSuspek.getText(),tbPemeriksaanRM.getSelectedRow(), 15);
                                        tbPemeriksaanRM.setValueAt(TRpd1.getText(),tbPemeriksaanRM.getSelectedRow(), 16);
                                        tbPemeriksaanRM.setValueAt(TRpk1.getText(),tbPemeriksaanRM.getSelectedRow(), 17);
                                        tbPemeriksaanRM.setValueAt(TRpo1.getText(),tbPemeriksaanRM.getSelectedRow(), 18);
                                        tbPemeriksaanRM.setValueAt(TAlergi1.getText(),tbPemeriksaanRM.getSelectedRow(), 19);
                                        tbPemeriksaanRM.setValueAt(Instruksi1.getText(),tbPemeriksaanRM.getSelectedRow(), 20);
                                        tbPemeriksaanRM.setValueAt(Operasi1.getText(),tbPemeriksaanRM.getSelectedRow(), 21);
                                        tbPemeriksaanRM.setValueAt(KodeDokter.getText(),tbPemeriksaanRM.getSelectedRow(), 22);                                        
                                }   
                                }else{
                                    JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }                         
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break;
                case 5:
                   if((!TTinggi_uteri.getText().trim().equals(""))||(!TLetak.getText().trim().equals(""))||
                            (!TDenyut.getText().trim().equals(""))||(!TKualitas_mnt.getText().trim().equals(""))||
                            (!TKualitas_dtk.getText().trim().equals(""))||(!TVulva.getText().trim().equals(""))||
                            (!TPortio.getText().trim().equals(""))||(!TTebal.getText().trim().equals(""))||
                            (!TPembukaan.getText().trim().equals(""))||(!TPenurunan.getText().trim().equals(""))||
                            (!TDenominator.getText().trim().equals(""))||(!KodeDokter.getText().trim().equals(""))){
                        if(tbPemeriksaanObstetri.getSelectedRow()>-1){
                            if(Sequel.mengedittf("pemeriksaan_obstetri_ralan","no_rawat='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),1)+
                                "' and tgl_perawatan='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),4)+
                                "' and jam_rawat='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5)+"'",
                                "no_rawat='"+TNoRw.getText()+"', tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"', "+
                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"', "+
                                "tinggi_uteri='"+TTinggi_uteri.getText()+"', janin='"+cmbJanin.getSelectedItem()+"', letak='"+TLetak.getText()+"', "+
                                "panggul='"+cmbPanggul.getSelectedItem()+"', denyut='"+TDenyut.getText()+"', kontraksi='"+cmbKontraksi.getSelectedItem()+"', "+
                                "kualitas_mnt='"+TKualitas_mnt.getText()+"', kualitas_dtk='"+TKualitas_dtk.getText()+"', "+
                                "fluksus='"+cmbFluksus.getSelectedItem()+"', albus='"+cmbAlbus.getSelectedItem()+"', vulva='"+TVulva.getText()+"',"+
                                "portio='"+TPortio.getText()+"', dalam='"+cmbDalam.getSelectedItem()+"', tebal='"+TTebal.getText()+"', "+
                                "arah='"+cmbArah.getSelectedItem()+"', pembukaan='"+TPembukaan.getText()+"', penurunan='"+TPenurunan.getText()+"', "+
                                "denominator='"+TDenominator.getText()+"', ketuban='"+cmbKetuban.getSelectedItem()+"', feto='"+cmbFeto.getSelectedItem()+"', kd_dokter='"+KodeDokter.getText()+"'")==true){
                                    tbPemeriksaanObstetri.setValueAt(TNoRw.getText(),tbPemeriksaanObstetri.getSelectedRow(), 1);
                                    tbPemeriksaanObstetri.setValueAt(TNoRM.getText(),tbPemeriksaanObstetri.getSelectedRow(), 2);
                                    tbPemeriksaanObstetri.setValueAt(TPasien.getText(),tbPemeriksaanObstetri.getSelectedRow(), 3);
                                    tbPemeriksaanObstetri.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaanObstetri.getSelectedRow(), 4);
                                    tbPemeriksaanObstetri.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaanObstetri.getSelectedRow(), 5);
                                    tbPemeriksaanObstetri.setValueAt(TTinggi_uteri.getText(),tbPemeriksaanObstetri.getSelectedRow(), 6);
                                    tbPemeriksaanObstetri.setValueAt(cmbJanin.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 7);
                                    tbPemeriksaanObstetri.setValueAt(TLetak.getText(),tbPemeriksaanObstetri.getSelectedRow(), 8);
                                    tbPemeriksaanObstetri.setValueAt(cmbPanggul.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 9);
                                    tbPemeriksaanObstetri.setValueAt(TDenyut.getText(),tbPemeriksaanObstetri.getSelectedRow(), 10);
                                    tbPemeriksaanObstetri.setValueAt(cmbKontraksi.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 11);
                                    tbPemeriksaanObstetri.setValueAt(TKualitas_mnt.getText(),tbPemeriksaanObstetri.getSelectedRow(), 12);
                                    tbPemeriksaanObstetri.setValueAt(TKualitas_dtk.getText(),tbPemeriksaanObstetri.getSelectedRow(), 13);
                                    tbPemeriksaanObstetri.setValueAt(cmbFluksus.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 14);
                                    tbPemeriksaanObstetri.setValueAt(cmbAlbus.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 15);
                                    tbPemeriksaanObstetri.setValueAt(TVulva.getText(),tbPemeriksaanObstetri.getSelectedRow(), 16);
                                    tbPemeriksaanObstetri.setValueAt(TPortio.getText(),tbPemeriksaanObstetri.getSelectedRow(), 17);
                                    tbPemeriksaanObstetri.setValueAt(cmbDalam.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 18);
                                    tbPemeriksaanObstetri.setValueAt(TTebal.getText(),tbPemeriksaanObstetri.getSelectedRow(), 19);
                                    tbPemeriksaanObstetri.setValueAt(cmbArah.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 20);
                                    tbPemeriksaanObstetri.setValueAt(TPembukaan.getText(),tbPemeriksaanObstetri.getSelectedRow(), 21);
                                    tbPemeriksaanObstetri.setValueAt(TPenurunan.getText(),tbPemeriksaanObstetri.getSelectedRow(), 22);
                                    tbPemeriksaanObstetri.setValueAt(TDenominator.getText(),tbPemeriksaanObstetri.getSelectedRow(), 23);
                                    tbPemeriksaanObstetri.setValueAt(cmbKetuban.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 24);
                                    tbPemeriksaanObstetri.setValueAt(cmbFeto.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 25);
                                    tbPemeriksaanObstetri.setValueAt(KodeDokter.getText(),tbPemeriksaanObstetri.getSelectedRow(), 26);
                                    tbPemeriksaanObstetri.setValueAt(NamaDokter.getText().toString(),tbPemeriksaanObstetri.getSelectedRow(), 27);
                                    TTinggi_uteri.setText("");cmbJanin.setSelectedIndex(0);TLetak.setText("");cmbPanggul.setSelectedIndex(0);TDenyut.setText("");
                                    cmbKontraksi.setSelectedIndex(0);TKualitas_mnt.setText("");TKualitas_dtk.setText("");cmbFluksus.setSelectedIndex(0);
                                    cmbAlbus.setSelectedIndex(0);TVulva.setText("");TPortio.setText("");cmbDalam.setSelectedIndex(0);TTebal.setText("");
                                    cmbArah.setSelectedIndex(0);TPembukaan.setText("");TPenurunan.setText("");TDenominator.setText("");cmbKetuban.setSelectedIndex(0);
                                    cmbFeto.getSelectedItem().toString();KodeDokter.setText("");
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break;
                case 6:
                   if((!TInspeksi.getText().trim().equals(""))||(!TInspeksiVulva.getText().trim().equals(""))||
                            (!TInspekuloGine.getText().trim().equals(""))||(!TVulvaInspekulo.getText().trim().equals(""))||
                            (!TPortioInspekulo.getText().trim().equals(""))||(!TSondage.getText().trim().equals(""))||
                            (!TPortioDalam.getText().trim().equals(""))||(!TBentuk.getText().trim().equals(""))||
                            (!TCavumUteri.getText().trim().equals(""))||(!TUkuran.getText().trim().equals(""))||
                            (!TAdnexaKanan.getText().trim().equals(""))||(!TAdnexaKiri.getText().trim().equals(""))||
                            (!TCavumDouglas.getText().trim().equals(""))||(!KodeDokter.getText().trim().equals(""))){
                        if(tbPemeriksaanGinekologi.getSelectedRow()>-1){
                            if(Sequel.mengedittf("pemeriksaan_ginekologi_ralan","no_rawat='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),1)+
                                "' and tgl_perawatan='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),4)+
                                "' and jam_rawat='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5)+"'",
                                "no_rawat='"+TNoRw.getText()+"', tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"', "+
                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"', "+
                                "inspeksi='"+TInspeksi.getText()+"', inspeksi_vulva='"+TInspeksiVulva.getText()+"', inspekulo_gine='"+TInspekuloGine.getText()+"', "+
                                "fluxus_gine='"+cmbFluxusGine.getSelectedItem()+"', fluor_gine='"+cmbFluorGine.getSelectedItem()+"', "+
                                "vulva_inspekulo='"+TVulvaInspekulo.getText()+"', portio_inspekulo='"+TPortioInspekulo.getText()+"', sondage='"+TSondage.getText()+"', "+
                                "portio_dalam='"+TPortioDalam.getText()+"', bentuk='"+TBentuk.getText()+"', cavum_uteri='"+TCavumUteri.getText()+"', "+
                                "mobilitas='"+cmbMobilitas.getSelectedItem()+"', ukuran='"+TUkuran.getText()+"', nyeri_tekan='"+cmbNyeriTekan.getSelectedItem()+"',"+
                                "adnexa_kanan='"+TAdnexaKanan.getText()+"', adnexa_kiri='"+TAdnexaKiri.getText()+"', cavum_douglas='"+TCavumDouglas.getText()+"', kd_dokter='"+KodeDokter.getText()+"'")==true){
                                    tbPemeriksaanGinekologi.setValueAt(TNoRw.getText(),tbPemeriksaanGinekologi.getSelectedRow(), 1);
                                    tbPemeriksaanGinekologi.setValueAt(TNoRM.getText(),tbPemeriksaanGinekologi.getSelectedRow(), 2);
                                    tbPemeriksaanGinekologi.setValueAt(TPasien.getText(),tbPemeriksaanGinekologi.getSelectedRow(), 3);
                                    tbPemeriksaanGinekologi.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaanGinekologi.getSelectedRow(), 4);
                                    tbPemeriksaanGinekologi.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaanGinekologi.getSelectedRow(), 5);
                                    tbPemeriksaanGinekologi.setValueAt(TInspeksi.getText(),tbPemeriksaanGinekologi.getSelectedRow(),6);
                                    tbPemeriksaanGinekologi.setValueAt(TInspeksiVulva.getText(),tbPemeriksaanGinekologi.getSelectedRow(),7);
                                    tbPemeriksaanGinekologi.setValueAt(TInspekuloGine.getText(),tbPemeriksaanGinekologi.getSelectedRow(),8);
                                    tbPemeriksaanGinekologi.setValueAt(cmbFluxusGine.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),9);
                                    tbPemeriksaanGinekologi.setValueAt(cmbFluorGine.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),10);
                                    tbPemeriksaanGinekologi.setValueAt(TVulvaInspekulo.getText(),tbPemeriksaanGinekologi.getSelectedRow(),11);
                                    tbPemeriksaanGinekologi.setValueAt(TPortioInspekulo.getText(),tbPemeriksaanGinekologi.getSelectedRow(),12);
                                    tbPemeriksaanGinekologi.setValueAt(TSondage.getText(),tbPemeriksaanGinekologi.getSelectedRow(),13);
                                    tbPemeriksaanGinekologi.setValueAt(TPortioDalam.getText(),tbPemeriksaanGinekologi.getSelectedRow(),14);
                                    tbPemeriksaanGinekologi.setValueAt(TBentuk.getText(),tbPemeriksaanGinekologi.getSelectedRow(),15);
                                    tbPemeriksaanGinekologi.setValueAt(TCavumUteri.getText(),tbPemeriksaanGinekologi.getSelectedRow(),16);
                                    tbPemeriksaanGinekologi.setValueAt(cmbMobilitas.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),17);
                                    tbPemeriksaanGinekologi.setValueAt(TUkuran.getText(),tbPemeriksaanGinekologi.getSelectedRow(),18);
                                    tbPemeriksaanGinekologi.setValueAt(cmbNyeriTekan.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),19);
                                    tbPemeriksaanGinekologi.setValueAt(TAdnexaKanan.getText(),tbPemeriksaanGinekologi.getSelectedRow(),20);
                                    tbPemeriksaanGinekologi.setValueAt(TAdnexaKiri.getText(),tbPemeriksaanGinekologi.getSelectedRow(),21);
                                    tbPemeriksaanGinekologi.setValueAt(TCavumDouglas.getText(),tbPemeriksaanGinekologi.getSelectedRow(),22);
                                    tbPemeriksaanGinekologi.setValueAt(KodeDokter.getText(),tbPemeriksaanGinekologi.getSelectedRow(),23);
                                    tbPemeriksaanGinekologi.setValueAt(NamaDokter.getText(),tbPemeriksaanGinekologi.getSelectedRow(),24);
                                    TInspeksi.setText("");TInspeksiVulva.setText("");TInspekuloGine.setText("");
                                    cmbFluxusGine.setSelectedIndex(0);cmbFluorGine.setSelectedIndex(0); TVulvaInspekulo.setText("");
                                    TPortioInspekulo.setText(""); TSondage.setText(""); TPortioDalam.setText("");
                                    TBentuk.setText(""); TCavumUteri.setText(""); cmbMobilitas.setSelectedIndex(0);
                                    TUkuran.setText(""); cmbNyeriTekan.setSelectedIndex(0);
                                    TAdnexaKanan.setText(""); TAdnexaKiri.setText(""); TCavumDouglas.getText(); KodeDokter.getText();
                            }                            
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break; 
                case 8:
                    if(!Catatan.getText().trim().equals("")){
                        if(tbCatatan.getSelectedRow()>-1){
                            if(Sequel.mengedittf("catatan_perawatan","no_rawat='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),1)+
                                "' and tanggal='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),4)+
                                "' and jam='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),5)+
                                "' and kd_dokter='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),6)+"'",
                                "no_rawat='"+TNoRw.getText()+"',catatan='"+Catatan.getText()+"',"+
                                "kd_dokter='"+KdDok3.getText()+"',tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                "jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"'")==true){
                                    tbCatatan.setValueAt(TNoRw.getText(),tbCatatan.getSelectedRow(), 1);
                                    tbCatatan.setValueAt(TNoRM.getText(),tbCatatan.getSelectedRow(), 2);
                                    tbCatatan.setValueAt(TPasien.getText(),tbCatatan.getSelectedRow(), 3);
                                    tbCatatan.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbCatatan.getSelectedRow(), 4);
                                    tbCatatan.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbCatatan.getSelectedRow(), 5);
                                    tbCatatan.setValueAt(KdDok3.getText(),tbCatatan.getSelectedRow(), 6);
                                    tbCatatan.setValueAt(TDokter3.getText(),tbCatatan.getSelectedRow(), 7);
                                    tbCatatan.setValueAt(Catatan.getText(),tbCatatan.getSelectedRow(), 8);
                                    Catatan.setText("");
                            }                            
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break;
                default:                
                    break;
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void tbRawatDrPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrPrMouseClicked
        if(tabModeDrPr.getRowCount()!=0){
            try {
                getDataDrPr();
            } catch (java.lang.NullPointerException e) {
            }            
        }
    }//GEN-LAST:event_tbRawatDrPrMouseClicked

    private void kdptg2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptg2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TPerawat2.setText(petugas.tampil3(kdptg2.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugas2ActionPerformed(null);
        }else{
            Valid.pindah(evt,KdDok2,BtnSeekPetugas2);
        }    
    }//GEN-LAST:event_kdptg2KeyPressed

    private void BtnSeekPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugas2ActionPerformed
        akses.setform("DlgRawatJalan");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugas2ActionPerformed

    private void KdDok2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDok2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TDokter2.setText(dokter.tampil3(KdDok2.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekDokter2ActionPerformed(null);
        }else{            
            Valid.pindah(evt,TNoRw,kdptg2);
        }
    }//GEN-LAST:event_KdDok2KeyPressed

    private void BtnSeekDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter2ActionPerformed
        akses.setform("DlgRawatJalan");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter2ActionPerformed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,BtnSeekDokter,cmbJam);
    }//GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,TCari);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        akses.setform("DlgRawatJalan");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPasienKeyPressed
        Valid.pindah(evt,TCariPasien,DTPCari1);
    }//GEN-LAST:event_btnPasienKeyPressed

    private void tbRawatDrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrKeyReleased
        if(tabModeDr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataDr();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
        }
    }//GEN-LAST:event_tbRawatDrKeyReleased

    private void tbRawatPrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatPrKeyReleased
        if(tabModePr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPr();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbRawatPrKeyReleased

    private void tbRawatDrPrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrPrKeyReleased
        if(tabModeDrPr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataDrPr();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbRawatDrPrKeyReleased

    private void TabRawatTindakanDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatTindakanDokterMouseClicked
        if(TabRawatTindakanDokter.getSelectedIndex()==0){
            TCari.setText("");
        }else if(TabRawatTindakanDokter.getSelectedIndex()==1){
            TCari.setText("");
            TCariPasien.setText(TNoRM.getText());
        }
        TCari.requestFocus();
        tampilkanPenangananDokter(); 
    }//GEN-LAST:event_TabRawatTindakanDokterMouseClicked

    private void tbTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakanKeyPressed
        if(tbTindakan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbTindakan.getSelectedColumn();
                    if(i==1){
                        if(tbTindakan.getSelectedRow()>-1){
                            tbTindakan.setValueAt(true,tbTindakan.getSelectedRow(),0);
                        }
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbTindakanKeyPressed

    private void tbTindakan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakan2KeyPressed
        if(tbTindakan2.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbTindakan2.getSelectedColumn();
                    if(i==1){
                        if(tbTindakan2.getSelectedRow()>-1){
                            tbTindakan2.setValueAt(true,tbTindakan2.getSelectedRow(),0);
                        }
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbTindakan2KeyPressed

    private void TabRawatTindakanPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatTindakanPetugasMouseClicked
        if(TabRawatTindakanPetugas.getSelectedIndex()==0){
            TCari.setText("");
        }else if(TabRawatTindakanPetugas.getSelectedIndex()==1){
            TCari.setText("");
            TCariPasien.setText(TNoRM.getText());
        }
        TCari.requestFocus();
        tampilkanPenangananPetugas(); 
    }//GEN-LAST:event_TabRawatTindakanPetugasMouseClicked

    private void tbTindakan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakan3KeyPressed
        if(tbTindakan3.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbTindakan3.getSelectedColumn();
                    if(i==1){
                        if(tbTindakan3.getSelectedRow()>-1){
                            tbTindakan3.setValueAt(true,tbTindakan3.getSelectedRow(),0);
                        }
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbTindakan3KeyPressed

    private void TabRawatTindakanDokterPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatTindakanDokterPetugasMouseClicked
        if(TabRawatTindakanDokterPetugas.getSelectedIndex()==0){
            TCari.setText("");
        }else if(TabRawatTindakanDokterPetugas.getSelectedIndex()==1){
            TCari.setText("");
            TCariPasien.setText(TNoRM.getText());
        }
        TCari.requestFocus();
        tampilkanPenangananDokterPetugas(); 
    }//GEN-LAST:event_TabRawatTindakanDokterPetugasMouseClicked

    private void BtnTambahTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahTindakanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanRalan perawatan=new DlgJnsPerawatanRalan(null,false);
        perawatan.emptTeks();
        perawatan.isCek();
        perawatan.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        perawatan.setLocationRelativeTo(internalFrame1);
        perawatan.setAlwaysOnTop(false);
        perawatan.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahTindakanActionPerformed

    private void BtnResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                jmlparsial=0;
                if(aktifkanparsial.equals("yes")){
                    jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kd_pj);
                }
                if(jmlparsial>0){
                    inputResep();
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{ 
                        inputResep();
                    }
                }                     
            }            
        }
    }//GEN-LAST:event_BtnResepObatActionPerformed

    private void BtnObatBhpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObatBhpActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
            dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.isCek();
            dlgrwinap.setNoRm2(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"ralan");
            dlgrwinap.tampilPO();
            dlgrwinap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnObatBhpActionPerformed

    private void BtnBerkasDigitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBerkasDigitalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
            
            try {
                if(akses.gethapus_berkas_digital_perawatan()==true){
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }else{
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2nonhapus.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }   
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }

            berkas.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }            
    }//GEN-LAST:event_BtnBerkasDigitalActionPerformed

    private void BtnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanLabActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{      
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanLaboratorium dlgro=new DlgPermintaanLaboratorium(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());  
            }          
        }
    }//GEN-LAST:event_BtnPermintaanLabActionPerformed

    private void BtnPermintaanRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanRadActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanRadiologi dlgro=new DlgPermintaanRadiologi(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }            
        }
    }//GEN-LAST:event_BtnPermintaanRadActionPerformed

    private void BtnSKDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSKDPActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                SuratKontrol form=new SuratKontrol(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                form.setLocationRelativeTo(internalFrame1);      
                form.emptTeks();      
                form.setNoRm(TNoRM.getText(),TPasien.getText(), kode_poli,Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?",kode_poli),KdDok.getText(),TDokter.getText());
                form.setVisible(true);
            }                
        }
    }//GEN-LAST:event_BtnSKDPActionPerformed

    private void BtnCopyResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCopyResepActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCopyResep daftar=new DlgCopyResep(null,false);
            daftar.isCek();
            daftar.setRM(TNoRw.getText(),TNoRM.getText(),KdDok.getText(),kd_pj,"ralan");
            daftar.tampil();
            daftar.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            daftar.setLocationRelativeTo(internalFrame1);
            daftar.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } 
    }//GEN-LAST:event_BtnCopyResepActionPerformed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKamarActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                }else{ 
                    inputKamar();
                }                     
            }            
        }
    }//GEN-LAST:event_BtnKamarActionPerformed

    private void BtnRujukInternalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRujukInternalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                }else{ 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgRujukanPoliInternal dlgrjk=new DlgRujukanPoliInternal(null,false);
                    dlgrjk.setLocationRelativeTo(internalFrame1);
                    dlgrjk.isCek();
                    dlgrjk.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),this.getWidth()+20,this.getHeight()+20);
                    dlgrjk.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }                     
            }            
        }
    }//GEN-LAST:event_BtnRujukInternalActionPerformed

    private void BtnRujukKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRujukKeluarActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgRujuk dlgrjk=new DlgRujuk(null,false);
                dlgrjk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrjk.setLocationRelativeTo(internalFrame1);
                dlgrjk.emptTeks();
                dlgrjk.isCek();
                dlgrjk.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                dlgrjk.tampil();
                dlgrjk.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }            
        }
    }//GEN-LAST:event_BtnRujukKeluarActionPerformed

    private void BtnCatatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCatatan catatan=new DlgCatatan(null,true);
            catatan.setNoRm(TNoRM.getText());
            catatan.setSize(720,330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanActionPerformed

    private void ChkInput3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput3ActionPerformed
        isForm4();
    }//GEN-LAST:event_ChkInput3ActionPerformed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah(evt,KdDok3,BtnSimpan);
    }//GEN-LAST:event_CatatanKeyPressed

    private void tbCatatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCatatanMouseClicked
        if(TabModeCatatan.getRowCount()!=0){
            try {
                getDataCatatan();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbCatatanMouseClicked

    private void tbCatatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbCatatanKeyReleased
        if(TabModeCatatan.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataCatatan();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbCatatanKeyReleased

    private void KdDok3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDok3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TDokter3.setText(dokter.tampil3(KdDok3.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekDokter3ActionPerformed(null);
        }else{            
            Valid.pindah(evt,TNoRw,BtnSeekDokter3);
        }
    }//GEN-LAST:event_KdDok3KeyPressed

    private void BtnSeekDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter3ActionPerformed
        akses.setform("DlgRawatJalan");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter3ActionPerformed

    private void BtnTriaseIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTriaseIGDActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{  
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTriaseIGD form=new RMTriaseIGD(null,false);
            form.isCek();
            form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTriaseIGDActionPerformed

    private void BtnResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResumeActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataResumePasien resume=new RMDataResumePasien(null,false);
            resume.isCek();
            resume.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            resume.tampil();
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnResumeActionPerformed

    private void BtnResepLuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepLuarActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap...!!!");
            }else {
                InventoryResepLuar resep=new InventoryResepLuar(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(),KdDok.getText(),TDokter.getText(),TNoRM.getText()+" "+TPasien.getText());
                resep.isCek();
                resep.tampilobat();
                resep.setVisible(true);                    
            }            
        }
    }//GEN-LAST:event_BtnResepLuarActionPerformed

    private void BtnAwalMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanDewasa form=new RMPenilaianAwalMedisRalanDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisActionPerformed

    private void BtnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRiwayatActionPerformed

    private void BtnAwalMedisKandunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisKandunganActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanKandungan form=new RMPenilaianAwalMedisRalanKandungan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisKandunganActionPerformed

    private void BtnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJadwalOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{  
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgBookingOperasi form=new DlgBookingOperasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                form.setLocationRelativeTo(internalFrame1);            
                form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?",kode_poli),"Ralan"); 
                form.setVisible(true);
            }           
        }
    }//GEN-LAST:event_BtnJadwalOperasiActionPerformed

    private void BtnAwalMedisIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisIGDActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            AsesmenAwalMedisIGD form=new AsesmenAwalMedisIGD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisIGDActionPerformed

    private void BtnAwalMedisAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanAnak form=new RMPenilaianAwalMedisRalanAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisAnakActionPerformed

    private void BtnAwalFisioterapiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalFisioterapiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianFisioterapi form=new RMPenilaianFisioterapi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalFisioterapiActionPerformed

    private void BtnMedicalCheckUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMedicalCheckUpActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMCU form=new RMMCU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnMedicalCheckUpActionPerformed

    private void BtnUjiFungsiKFRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUjiFungsiKFRActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMUjiFungsiKFR form=new RMUjiFungsiKFR(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnUjiFungsiKFRActionPerformed

    private void BtnCatatanObservasiIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiIGDActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiIGD form=new RMDataCatatanObservasiIGD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanObservasiIGDActionPerformed

    private void BtnAwalMedisTHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisTHTActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanTHT form=new RMPenilaianAwalMedisRalanTHT(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisTHTActionPerformed

    private void BtnPenilaianPsikologActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPsikologActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPsikologi form=new RMPenilaianPsikologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPsikologActionPerformed

    private void BtnAwalMedisPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisPsikiatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanPsikiatrik form=new RMPenilaianAwalMedisRalanPsikiatrik(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisPsikiatriActionPerformed

    private void TNoRwMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TNoRwMouseClicked
        Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.setLocationRelativeTo(internalFrame1);
                win.toFront();
            }
        }
    }//GEN-LAST:event_TNoRwMouseClicked

    private void BtnAwalMedisPenyakitDalamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisPenyakitDalamActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanPenyakitDalam form=new RMPenilaianAwalMedisRalanPenyakitDalam(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisPenyakitDalamActionPerformed

    private void BtnAwalMedisMataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisMataActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanMata form=new RMPenilaianAwalMedisRalanMata(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisMataActionPerformed

    private void BtnAwalMedisNeurologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisNeurologiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanNeurologi form=new RMPenilaianAwalMedisRalanNeurologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisNeurologiActionPerformed

    private void BtnAwalMedisOrthopediActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisOrthopediActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanOrthopedi form=new RMPenilaianAwalMedisRalanOrthopedi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisOrthopediActionPerformed

    private void BtnAwalMedisBedahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisBedahActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanBedah form=new RMPenilaianAwalMedisRalanBedah(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisBedahActionPerformed

    private void BtnPemantauanPEWSAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemantauanPEWSAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanPEWS form=new RMPemantauanPEWS(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanPEWSAnakActionPerformed

    private void BtnPenilaianPreOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPreOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreOperasi form=new RMPenilaianPreOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPreOperasiActionPerformed

    private void BtnPenilaianPreAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPreAnestesiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreAnastesi form=new RMPenilaianPreAnastesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPreAnestesiActionPerformed

    private void BtnPenilaianLanjutanRisikoJatuhDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanRisikoJatuhDewasaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhDewasa form=new RMPenilaianLanjutanRisikoJatuhDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanRisikoJatuhDewasaActionPerformed

    private void BtnPenilaianLanjutanRisikoJatuhAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanRisikoJatuhAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhAnak form=new RMPenilaianLanjutanRisikoJatuhAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanRisikoJatuhAnakActionPerformed

    private void BtnAwalMedisGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisGeriatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanGeriatri form=new RMPenilaianAwalMedisRalanGeriatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisGeriatriActionPerformed

    private void BtnPenilaianTambahanGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanGeriatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanGeriatri form=new RMPenilaianTambahanGeriatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanGeriatriActionPerformed

    private void BtnHasilPemeriksaanUSGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilPemeriksaanUSGActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSG form=new RMHasilPemeriksaanUSG(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnHasilPemeriksaanUSGActionPerformed

    private void BtnSkriningNutrisiDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningNutrisiDewasaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiDewasa form=new RMSkriningNutrisiDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningNutrisiDewasaActionPerformed

    private void BtnSkriningNutrisiLansiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningNutrisiLansiaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiLansia form=new RMSkriningNutrisiLansia(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningNutrisiLansiaActionPerformed

    private void BtnSkriningNutrisiAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningNutrisiAnakActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiAnak form=new RMSkriningNutrisiAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningNutrisiAnakActionPerformed

    private void BtnSkriningGiziLanjutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningGiziLanjutActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataSkriningGiziLanjut form=new RMDataSkriningGiziLanjut(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningGiziLanjutActionPerformed

    private void BtnAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAsuhanGiziActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataAsuhanGizi form=new RMDataAsuhanGizi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAsuhanGiziActionPerformed

    private void BtnMonitoringAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMonitoringAsuhanGiziActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataMonitoringAsuhanGizi form=new RMDataMonitoringAsuhanGizi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnMonitoringAsuhanGiziActionPerformed

    private void BtnKonselingFarmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKonselingFarmasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMKonselingFarmasi form=new RMKonselingFarmasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnKonselingFarmasiActionPerformed

    private void BtnInformasiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInformasiObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanPelayananInformasiObat form=new DlgPermintaanPelayananInformasiObat(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnInformasiObatActionPerformed

    private void BtnTransferAntarRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTransferAntarRuangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTransferPasienAntarRuang form=new RMTransferPasienAntarRuang(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTransferAntarRuangActionPerformed

    private void BtnCatatanCekGDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanCekGDSActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            GdsIgd form=new GdsIgd(null,false);
            form.isCek();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.emptTeks();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanCekGDSActionPerformed

    private void BtnChecklistPreOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistPreOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistPreOperasi form=new RMChecklistPreOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistPreOperasiActionPerformed

    private void BtnSignInSebelumAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSignInSebelumAnestesiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSignInSebelumAnastesi form=new RMSignInSebelumAnastesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSignInSebelumAnestesiActionPerformed

    private void BtnTimeOutSebelumInsisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTimeOutSebelumInsisiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTimeOutSebelumInsisi form=new RMTimeOutSebelumInsisi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTimeOutSebelumInsisiActionPerformed

    private void BtnSignOutSebelumMenutupLukaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSignOutSebelumMenutupLukaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSignOutSebelumMenutupLuka form=new RMSignOutSebelumMenutupLuka(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSignOutSebelumMenutupLukaActionPerformed

    private void BtnChecklistPostOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistPostOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistPostOperasi form=new RMChecklistPostOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistPostOperasiActionPerformed

    private void BtnRekonsiliasiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRekonsiliasiObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRekonsiliasiObat form=new RMRekonsiliasiObat(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRekonsiliasiObatActionPerformed

    private void BtnPenilaianPasienTerminalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPasienTerminalActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienTerminal form=new RMPenilaianPasienTerminal(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPasienTerminalActionPerformed

    private void BtnMonitoringReaksiTranfusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMonitoringReaksiTranfusiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataMonitoringReaksiTranfusi form=new RMDataMonitoringReaksiTranfusi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnMonitoringReaksiTranfusiActionPerformed

    private void BtnPenilaianKorbanKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianKorbanKekerasanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianKorbanKekerasan form=new RMPenilaianKorbanKekerasan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianKorbanKekerasanActionPerformed

    private void BtnPenilaianLanjutanRisikoJatuhLansiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanRisikoJatuhLansiaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhLansia form=new RMPenilaianLanjutanRisikoJatuhLansia(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanRisikoJatuhLansiaActionPerformed

    private void BtnPenilaianPasienPenyakitMenularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPasienPenyakitMenularActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienPenyakitMenular form=new RMPenilaianPasienPenyakitMenular(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPasienPenyakitMenularActionPerformed

    private void BtnEdukasiPasienKeluargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdukasiPasienKeluargaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMEdukasiPasienKeluargaRawatJalan form=new RMEdukasiPasienKeluargaRawatJalan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnEdukasiPasienKeluargaActionPerformed

    private void BtnPemantauanPEWSDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemantauanPEWSDewasaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanPEWSD form=new RMPemantauanPEWSD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanPEWSDewasaActionPerformed

    private void BtnPenilaianTambahanBunuhDiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanBunuhDiriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanBunuhDiri form=new RMPenilaianTambahanBunuhDiri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanBunuhDiriActionPerformed

    private void BtnPenilaianTambahanPerilakuKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanPerilakuKekerasanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanPerilakuKekerasan form=new RMPenilaianTambahanPerilakuKekerasan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanPerilakuKekerasanActionPerformed

    private void BtnPenilaianTambahanMelarikanDiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanMelarikanDiriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanMelarikanDiri form=new RMPenilaianTambahanMelarikanDiri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanMelarikanDiriActionPerformed

    private void BtnOdontogramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOdontogramActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgOdontogram odontogram=new DlgOdontogram(null,false);
            odontogram.isCek();
            odontogram.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            odontogram.setLocationRelativeTo(internalFrame1);
            odontogram.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            odontogram.tampil();
            odontogram.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnOdontogramActionPerformed

    private void BtnOdontogramKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOdontogramKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnOdontogramKeyPressed

    private void BtnTmpResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTmpResepActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgTemplateResep template=new DlgTemplateResep(null,false);
            template.isCek();
            template.setRM(TNoRw.getText(),TNoRM.getText(),KdDok.getText(),kd_pj,"ralan");
            template.tampil();
            template.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            template.setLocationRelativeTo(internalFrame1);
            template.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTmpResepActionPerformed

    private void BtnTmpResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTmpResepKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnTmpResepKeyPressed

    private void BtnResumeRajalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResumeRajalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataResumePasienRajal resumerajal=new RMDataResumePasienRajal(null,false);
            resumerajal.isCek();
            resumerajal.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resumerajal.setLocationRelativeTo(internalFrame1);
            resumerajal.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            resumerajal.tampil();
            resumerajal.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnResumeRajalActionPerformed

    private void BtnInacbgRajalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInacbgRajalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMInacbgRajal inacbgrajal=new RMInacbgRajal(null,false);
            inacbgrajal.isCek();
            inacbgrajal.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            inacbgrajal.setLocationRelativeTo(internalFrame1);
            inacbgrajal.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            inacbgrajal.tampil();
            inacbgrajal.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnInacbgRajalActionPerformed

    private void BtnHasilEKGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilEKGActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilEKG ekgrajal=new RMHasilEKG(null,false);
            ekgrajal.isCek();
            ekgrajal.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            ekgrajal.setLocationRelativeTo(internalFrame1);
            ekgrajal.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            ekgrajal.tampil();
            ekgrajal.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnHasilEKGActionPerformed

    private void BtnICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnICActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataIC ic=new RMDataIC(null,false);
            ic.isCek();
            ic.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            ic.setLocationRelativeTo(internalFrame1);
            ic.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            ic.tampil();
            ic.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnICActionPerformed

    private void BtnIEMRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIEMRActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DataPasienIntegration iemr=new DataPasienIntegration(null,true);
            iemr.setPasien(TNoRM.getText());
            iemr.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            iemr.setPasien(TNoRM.getText());
            //            iemr.tampil();
            iemr.setLocationRelativeTo(internalFrame1);
            iemr.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnIEMRActionPerformed

    private void BtnRujukIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRujukIGDActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgRujukIGD rujukigd=new DlgRujukIGD(null,false);
            rujukigd.isCek();
            rujukigd.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            rujukigd.setLocationRelativeTo(internalFrame1);
            rujukigd.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            rujukigd.tampil();
            rujukigd.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRujukIGDActionPerformed

    private void tbPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanMouseClicked
        if(tabModePemeriksaan.getRowCount()!=0){
            try {
                getDataPemeriksaan();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbPemeriksaanMouseClicked

    private void tbPemeriksaanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanKeyReleased
        if(tabModePemeriksaan.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPemeriksaan();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbPemeriksaanKeyReleased

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyPressed
        Valid.pindah(evt,TindakLanjut,TTensi);
    }//GEN-LAST:event_TSuhuKeyPressed

    private void TSuhuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyTyped
        char kata= evt.getKeyChar();
        if(!((kata>='0') && (kata<='9') || (kata== KeyEvent.VK_PERIOD) || (kata== KeyEvent.VK_BACK_SPACE))){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Hanya diperbolehkan menginputkan Angka dan Titik");
        }
    }//GEN-LAST:event_TSuhuKeyTyped

    private void TTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyPressed
        Valid.pindah(evt,TSuhu,TBerat);
    }//GEN-LAST:event_TTensiKeyPressed

    private void TTensiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyTyped
        char kata= evt.getKeyChar();
        if(!((kata>='0') && (kata<='9') || (kata==KeyEvent.VK_SLASH)  || (kata== KeyEvent.VK_BACK_SPACE))){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Hanya diperbolehkan menginputkan Angka dan Garis Miring");
        }
    }//GEN-LAST:event_TTensiKeyTyped

    private void TBeratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBeratKeyPressed
        Valid.pindah(evt,TTensi,TTinggi);
    }//GEN-LAST:event_TBeratKeyPressed

    private void TNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNadiKeyPressed
        Valid.pindah(evt,cmbImun,TRespirasi);
    }//GEN-LAST:event_TNadiKeyPressed

    private void cmbImunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbImunKeyPressed
        Valid.pindah(evt,TTinggi,TNadi);
    }//GEN-LAST:event_cmbImunKeyPressed

    private void TTinggiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTinggiKeyPressed
        Valid.pindah(evt,TBerat,cmbImun);
    }//GEN-LAST:event_TTinggiKeyPressed

    private void TAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlergiKeyPressed
        Valid.pindah(evt,TPemeriksaan,TindakLanjut);
    }//GEN-LAST:event_TAlergiKeyPressed

    private void TRespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRespirasiKeyPressed
        Valid.pindah(evt,TNadi,TGCS);
    }//GEN-LAST:event_TRespirasiKeyPressed

    private void TGCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TGCSKeyPressed
        Valid.pindah(evt,TRespirasi,BtnSimpan);
    }//GEN-LAST:event_TGCSKeyPressed

    private void TKeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKeluhanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKeluhanKeyPressed

    private void TPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPemeriksaanKeyPressed

    private void TPenilaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPenilaianKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPenilaianKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakLanjutKeyPressed

    private void TSpo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TSpo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TSpo2ActionPerformed

    private void TSpo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSpo2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TSpo2KeyPressed

    private void TSpo2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSpo2KeyTyped
        char kata= evt.getKeyChar();
        if(!((kata>='0') && (kata<='9')  || (kata== KeyEvent.VK_BACK_SPACE))){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Hanya diperbolehkan menginputkan Angka ");
        }
    }//GEN-LAST:event_TSpo2KeyTyped

    private void TRpdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRpdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpdKeyPressed

    private void TRpkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TRpkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpkActionPerformed

    private void TRpkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRpkKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpkKeyPressed

    private void TRpoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TRpoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpoActionPerformed

    private void TRpoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRpoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpoKeyPressed

    private void cmbKesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbKesadaranKeyPressed
        Valid.pindah(evt,TGCS,cmbImun);
    }//GEN-LAST:event_cmbKesadaranKeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        akses.setform("DlgRawatJalan");
        masters.isCek();
        masters.onCari();
        masters.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        masters.setLocationRelativeTo(internalFrame1);
        masters.setVisible(true);
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        akses.setform("DlgRawatJalan");
        mastero.isCek();
        mastero.onCari();
        mastero.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        mastero.setLocationRelativeTo(internalFrame1);
        mastero.setVisible(true);
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        akses.setform("DlgRawatJalan");
        masterp.isCek();
        masterp.onCari();
        masterp.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        masterp.setLocationRelativeTo(internalFrame1);
        masterp.setVisible(true);
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void KodeDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KodeDokterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDokterActionPerformed

    private void KodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterKeyPressed
        Valid.pindah(evt,TCari,TKeluhan);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        //        akses.setform("DlgRawatJalan");
        //        dokter.emptTeks();
        //        dokter.isCek();
        //        dokter.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        //        dokter.setLocationRelativeTo(internalFrame1);
        //        dokter.setVisible(true);
        akses.setform("DlgRawatJalan");
        pegawai.emptTeks();
        //        pegawai.isCek();
        pegawai.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,TCari,TKeluhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnCpptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCpptActionPerformed
        if(TNoRw.getText().trim().equals("") ){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgSOAPOld soap=new DlgSOAPOld(null,false);
            soap.setNoRawat(TNoRw.getText(),TNoRw.getText());
            soap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            soap.setLocationRelativeTo(internalFrame1);
            soap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCpptActionPerformed

    private void BtnCpptKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCpptKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCpptKeyPressed

    private void BtnRiwayatLabRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatLabRadActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
            resume.emptTeks();
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRiwayatLabRadActionPerformed

    private void BtnRiwayatLabRadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRiwayatLabRadKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRiwayatLabRadKeyPressed

    private void OperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OperasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OperasiKeyPressed

    private void BtnEWSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEWSActionPerformed
        if(TNoRw.getText().trim().equals("") ){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgEWS ews=new DlgEWS(null,false);
            ews.setNoRawat(TNoRw.getText(),TNoRw.getText());
            ews.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            ews.setLocationRelativeTo(internalFrame1);
            ews.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnEWSActionPerformed

    private void BtnEWSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEWSKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEWSKeyPressed

    private void AsesmenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsesmenActionPerformed
        penyakit.isCek();
        penyakit.setNoRw(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
        //        penyakit.emptTeks();
        penyakit.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);    // TODO add your handling code here:
    }//GEN-LAST:event_AsesmenActionPerformed

    private void ObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            //if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                //JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            //}else {
                jmlparsial=0;
                if(aktifkanparsial.equals("yes")){
                    jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kd_pj);
                }
                if(jmlparsial>0){
                    inputResep();
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{
                        inputResep();
                    }
                }
            //}
        }
    }//GEN-LAST:event_ObatActionPerformed

    private void BtnEWS1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEWS1ActionPerformed
        if(TNoRw.getText().trim().equals("") ){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPEWS pews=new DlgPEWS(null,false);
            pews.setNoRawat(TNoRw.getText(),TNoRw.getText());
            pews.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            pews.setLocationRelativeTo(internalFrame1);
            pews.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnEWS1ActionPerformed

    private void BtnEWS1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEWS1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEWS1KeyPressed

    private void tbPemeriksaanObstetriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanObstetriMouseClicked
        // TODO add your handling code here:
        if(tabModeObstetri.getRowCount()!=0) {
            try {
                getDataPemeriksaanObstetri();

            } catch (java.lang.NullPointerException e) {

            }
        }
    }//GEN-LAST:event_tbPemeriksaanObstetriMouseClicked

    private void tbPemeriksaanObstetriKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanObstetriKeyReleased
        // TODO add your handling code here:
        if(tabModeObstetri.getRowCount()!=0) {
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)) {
                try {
                    getDataPemeriksaanObstetri();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPemeriksaanObstetriKeyReleased

    private void ChkInput1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput1ActionPerformed
        // TODO add your handling code here:
        isForm2();
    }//GEN-LAST:event_ChkInput1ActionPerformed

    private void TTinggi_uteriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTinggi_uteriKeyPressed
        Valid.pindah(evt,TNoRw,cmbJanin);
    }//GEN-LAST:event_TTinggi_uteriKeyPressed

    private void TLetakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TLetakKeyPressed
        Valid.pindah(evt,cmbJanin,cmbPanggul);
    }//GEN-LAST:event_TLetakKeyPressed

    private void TKualitas_dtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKualitas_dtkKeyPressed
        Valid.pindah(evt,TKualitas_mnt,cmbFluksus);
    }//GEN-LAST:event_TKualitas_dtkKeyPressed

    private void cmbPanggulKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPanggulKeyPressed
        Valid.pindah(evt,TLetak,TDenyut);
    }//GEN-LAST:event_cmbPanggulKeyPressed

    private void TTebalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTebalKeyPressed
        Valid.pindah(evt,cmbDalam,cmbArah);
    }//GEN-LAST:event_TTebalKeyPressed

    private void TDenyutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDenyutKeyPressed
        Valid.pindah(evt,cmbPanggul,cmbKontraksi);
    }//GEN-LAST:event_TDenyutKeyPressed

    private void TDenominatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDenominatorKeyPressed
        Valid.pindah(evt,TPenurunan,cmbFeto);
    }//GEN-LAST:event_TDenominatorKeyPressed

    private void TKualitas_mntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKualitas_mntKeyPressed
        Valid.pindah(evt,cmbKontraksi,TKualitas_dtk);
    }//GEN-LAST:event_TKualitas_mntKeyPressed

    private void cmbFetoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFetoKeyPressed
        Valid.pindah(evt,TDenominator,BtnSimpan);
    }//GEN-LAST:event_cmbFetoKeyPressed

    private void cmbJaninKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJaninKeyPressed
        Valid.pindah(evt,TTinggi_uteri,TLetak);
    }//GEN-LAST:event_cmbJaninKeyPressed

    private void cmbKetubanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbKetubanKeyPressed
        Valid.pindah(evt,cmbAlbus,TVulva);
    }//GEN-LAST:event_cmbKetubanKeyPressed

    private void TPortioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPortioKeyPressed
        Valid.pindah(evt,TVulva,cmbDalam);
    }//GEN-LAST:event_TPortioKeyPressed

    private void TVulvaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TVulvaKeyPressed
        Valid.pindah(evt,cmbKetuban,TPortio);
    }//GEN-LAST:event_TVulvaKeyPressed

    private void cmbKontraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbKontraksiKeyPressed
        Valid.pindah(evt,TDenyut,TKualitas_mnt);
    }//GEN-LAST:event_cmbKontraksiKeyPressed

    private void cmbAlbusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbAlbusKeyPressed
        Valid.pindah(evt,cmbFluksus,cmbKetuban);
    }//GEN-LAST:event_cmbAlbusKeyPressed

    private void cmbFluksusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFluksusKeyPressed
        Valid.pindah(evt,TKualitas_dtk,cmbAlbus);
    }//GEN-LAST:event_cmbFluksusKeyPressed

    private void cmbDalamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDalamKeyPressed
        Valid.pindah(evt,TPortio,TTebal);
    }//GEN-LAST:event_cmbDalamKeyPressed

    private void TPembukaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPembukaanKeyPressed
        Valid.pindah(evt,cmbArah,TPenurunan);
    }//GEN-LAST:event_TPembukaanKeyPressed

    private void TPenurunanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPenurunanKeyPressed
        Valid.pindah(evt,TPembukaan,TDenominator);
    }//GEN-LAST:event_TPenurunanKeyPressed

    private void cmbArahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbArahKeyPressed
        Valid.pindah(evt,TTebal,TPembukaan);
    }//GEN-LAST:event_cmbArahKeyPressed

    private void tbPemeriksaanGinekologiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanGinekologiMouseClicked
        // TODO add your handling code here:
        if(tabModeGinekologi.getRowCount()!=0) {
            try {
                getDataPemeriksaanGinekologi();

            } catch (java.lang.NullPointerException e) {

            }
        }
    }//GEN-LAST:event_tbPemeriksaanGinekologiMouseClicked

    private void tbPemeriksaanGinekologiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanGinekologiKeyReleased
        // TODO add your handling code here:
        if(tabModeGinekologi.getRowCount()!=0) {
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)) {
                try {
                    getDataPemeriksaanGinekologi();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPemeriksaanGinekologiKeyReleased

    private void Scroll5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Scroll5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Scroll5KeyPressed

    private void ChkInput2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput2ActionPerformed
        isForm3();
    }//GEN-LAST:event_ChkInput2ActionPerformed

    private void TInspeksiVulvaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInspeksiVulvaKeyPressed
        Valid.pindah(evt,TInspeksi,TInspekuloGine);
    }//GEN-LAST:event_TInspeksiVulvaKeyPressed

    private void TAdnexaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAdnexaKananKeyPressed
        Valid.pindah(evt,cmbNyeriTekan,TAdnexaKiri);
    }//GEN-LAST:event_TAdnexaKananKeyPressed

    private void cmbMobilitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMobilitasKeyPressed
        Valid.pindah(evt,TCavumUteri,TUkuran);
    }//GEN-LAST:event_cmbMobilitasKeyPressed

    private void TInspekuloGineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInspekuloGineKeyPressed
        Valid.pindah(evt,TInspeksiVulva,cmbFluxusGine);
    }//GEN-LAST:event_TInspekuloGineKeyPressed

    private void TPortioInspekuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPortioInspekuloKeyPressed
        Valid.pindah(evt,TVulvaInspekulo,TSondage);
    }//GEN-LAST:event_TPortioInspekuloKeyPressed

    private void TCavumUteriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCavumUteriKeyPressed
        Valid.pindah(evt,TBentuk,cmbMobilitas);
    }//GEN-LAST:event_TCavumUteriKeyPressed

    private void cmbFluorGineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFluorGineKeyPressed
        Valid.pindah(evt,cmbFluxusGine,TVulvaInspekulo);
    }//GEN-LAST:event_cmbFluorGineKeyPressed

    private void TInspeksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInspeksiKeyPressed
        Valid.pindah(evt,TNoRw,TInspeksiVulva);
    }//GEN-LAST:event_TInspeksiKeyPressed

    private void cmbFluxusGineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFluxusGineKeyPressed
        Valid.pindah(evt,TInspekuloGine,cmbFluorGine);
    }//GEN-LAST:event_cmbFluxusGineKeyPressed

    private void TVulvaInspekuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TVulvaInspekuloKeyPressed
        Valid.pindah(evt,cmbFluorGine,TPortioInspekulo);
    }//GEN-LAST:event_TVulvaInspekuloKeyPressed

    private void TPortioDalamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPortioDalamKeyPressed
        Valid.pindah(evt,TSondage,TBentuk);
    }//GEN-LAST:event_TPortioDalamKeyPressed

    private void TBentukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBentukKeyPressed
        Valid.pindah(evt,TPortioDalam,TCavumUteri);
    }//GEN-LAST:event_TBentukKeyPressed

    private void cmbNyeriTekanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbNyeriTekanKeyPressed
        Valid.pindah(evt,TUkuran,TAdnexaKanan);
    }//GEN-LAST:event_cmbNyeriTekanKeyPressed

    private void TSondageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSondageKeyPressed
        Valid.pindah(evt,TPortioInspekulo,TPortioDalam);
    }//GEN-LAST:event_TSondageKeyPressed

    private void TAdnexaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAdnexaKiriKeyPressed
        Valid.pindah(evt,TAdnexaKanan,TCavumDouglas);
    }//GEN-LAST:event_TAdnexaKiriKeyPressed

    private void TCavumDouglasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCavumDouglasKeyPressed
        Valid.pindah(evt,TAdnexaKiri,BtnSimpan);
    }//GEN-LAST:event_TCavumDouglasKeyPressed

    private void TUkuranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUkuranKeyPressed
        Valid.pindah(evt,cmbMobilitas,cmbNyeriTekan);
    }//GEN-LAST:event_TUkuranKeyPressed

    private void InstruksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InstruksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InstruksiKeyPressed

    private void BtnAwalKeperawatanIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanIGDActionPerformed
     if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanIGD form=new RMPenilaianAwalKeperawatanIGD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanIGDActionPerformed

    private void BtnAwalKeperawatanAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanBayiAnak form=new RMPenilaianAwalKeperawatanBayiAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanAnakActionPerformed

    private void BtnAwalKeperawatanGigiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanGigiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanGigi form=new RMPenilaianAwalKeperawatanGigi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanGigiActionPerformed

    private void BtnAwalKeperawatanKandunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanKandunganActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanKebidanan form=new RMPenilaianAwalKeperawatanKebidanan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanKandunganActionPerformed

    private void BtnAwalKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalan form=new RMPenilaianAwalKeperawatanRalan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanActionPerformed

    private void BtnAwalKeperawatanGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanGeriatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalanGeriatri form=new RMPenilaianAwalKeperawatanRalanGeriatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanGeriatriActionPerformed

    private void BtnAwalKeperawatanPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanPsikiatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalanPsikiatri form=new RMPenilaianAwalKeperawatanRalanPsikiatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanPsikiatriActionPerformed

    private void TSuhuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TSuhuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TSuhuActionPerformed

    private void BtnRiwayat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayat1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRiwayat1ActionPerformed

    private void BtnResepObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepObat1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                jmlparsial=0;
                if(aktifkanparsial.equals("yes")){
                    jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kd_pj);
                }
                if(jmlparsial>0){
                    inputResep();
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{ 
                        inputResep();
                    }
                }                     
            }            
        }
    }//GEN-LAST:event_BtnResepObat1ActionPerformed

    private void BtnCopyResep1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCopyResep1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCopyResep daftar=new DlgCopyResep(null,false);
            daftar.isCek();
            daftar.setRM(TNoRw.getText(),TNoRM.getText(),KdDok.getText(),kd_pj,"ralan");
            daftar.tampil();
            daftar.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            daftar.setLocationRelativeTo(internalFrame1);
            daftar.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCopyResep1ActionPerformed

    private void BtnTmpResep1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTmpResep1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgTemplateResep template=new DlgTemplateResep(null,false);
            template.isCek();
            template.setRM(TNoRw.getText(),TNoRM.getText(),KdDok.getText(),kd_pj,"ralan");
            template.tampil();
            template.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            template.setLocationRelativeTo(internalFrame1);
            template.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTmpResep1ActionPerformed

    private void BtnTmpResep1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTmpResep1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnTmpResep1KeyPressed

    private void BtnResepLuar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepLuar1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap...!!!");
            }else {
                InventoryResepLuar resep=new InventoryResepLuar(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(),KdDok.getText(),TDokter.getText(),TNoRM.getText()+" "+TPasien.getText());
                resep.isCek();
                resep.tampilobat();
                resep.setVisible(true);                    
            }            
        }
    }//GEN-LAST:event_BtnResepLuar1ActionPerformed

    private void BtnObatBhp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObatBhp1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
            dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.isCek();
            dlgrwinap.setNoRm2(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"ralan");
            dlgrwinap.tampilPO();
            dlgrwinap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnObatBhp1ActionPerformed

    private void BtnBerkasDigital1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBerkasDigital1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
            
            try {
                if(akses.gethapus_berkas_digital_perawatan()==true){
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }else{
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2nonhapus.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }   
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }

            berkas.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnBerkasDigital1ActionPerformed

    private void BtnPermintaanLab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanLab1ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{      
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanLaboratorium dlgro=new DlgPermintaanLaboratorium(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());  
            }          
        }
    }//GEN-LAST:event_BtnPermintaanLab1ActionPerformed

    private void BtnPermintaanRad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanRad1ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanRadiologi dlgro=new DlgPermintaanRadiologi(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }            
        }
    }//GEN-LAST:event_BtnPermintaanRad1ActionPerformed

    private void BtnJadwalOperasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJadwalOperasi1ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{  
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgBookingOperasi form=new DlgBookingOperasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                form.setLocationRelativeTo(internalFrame1);            
                form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?",kode_poli),"Ralan"); 
                form.setVisible(true);
            }           
        }
    }//GEN-LAST:event_BtnJadwalOperasi1ActionPerformed

    private void BtnKamar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKamar1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                }else{ 
                    inputKamar();
                }                     
            }            
        }
    }//GEN-LAST:event_BtnKamar1ActionPerformed

    private void BtnIEMR1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIEMR1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DataPasienIntegration iemr=new DataPasienIntegration(null,true);
            iemr.setPasien(TNoRM.getText());
            iemr.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            iemr.setPasien(TNoRM.getText());
            //            iemr.tampil();
            iemr.setLocationRelativeTo(internalFrame1);
            iemr.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnIEMR1ActionPerformed

    private void BtnInformasiObat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInformasiObat2ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanPelayananInformasiObat form=new DlgPermintaanPelayananInformasiObat(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnInformasiObat2ActionPerformed

    private void BtnHasilPemeriksaanUSG1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilPemeriksaanUSG1ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSG form=new RMHasilPemeriksaanUSG(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnHasilPemeriksaanUSG1ActionPerformed

    private void BtnHasilEKG1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilEKG1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilEKG ekgrajal=new RMHasilEKG(null,false);
            ekgrajal.isCek();
            ekgrajal.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            ekgrajal.setLocationRelativeTo(internalFrame1);
            ekgrajal.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            ekgrajal.tampil();
            ekgrajal.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnHasilEKG1ActionPerformed

    private void BtnAwalKeperawatan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatan1ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalan form=new RMPenilaianAwalKeperawatanRalan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatan1ActionPerformed

    private void BtnAwalKeperawatan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatan2ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalan form=new RMPenilaianAwalKeperawatanRalan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatan2ActionPerformed

    private void BtnAwalKeperawatan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatan3ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalan form=new RMPenilaianAwalKeperawatanRalan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatan3ActionPerformed

    private void BtnAwalKeperawatan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatan4ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalan form=new RMPenilaianAwalKeperawatanRalan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatan4ActionPerformed

    private void BtnAwalMedis1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedis1ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanDewasa form=new RMPenilaianAwalMedisRalanDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedis1ActionPerformed

    private void BtnAwalKeperawatan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatan5ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalan form=new RMPenilaianAwalKeperawatanRalan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatan5ActionPerformed

    private void BtnAwalMedis2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedis2ActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){    
        JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanDewasa form=new RMPenilaianAwalMedisRalanDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedis2ActionPerformed

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void BtnTemplatePemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTemplatePemeriksaanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else if(NamaDokter.getText().trim().equals("")||KodeDokter.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dokter pemberi asuhan...!!!");
            TCari.requestFocus();
        }else{
            jmlparsial=0;
            if(aktifkanparsial.equals("yes")){
                jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
            }
            if(jmlparsial>0){
                inputTemplate();
            }else{
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                }else{
                    inputTemplate();
                }
            }
        }
    }//GEN-LAST:event_BtnTemplatePemeriksaanActionPerformed

    private void BtnRujukanFisioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRujukanFisioActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRujukanFisio form=new RMRujukanFisio(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRujukanFisioActionPerformed

    private void BtnPermintaanKonsultasiMedikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanKonsultasiMedikActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
                    TCari.requestFocus();
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanKonsultasiMedik form=new DlgPermintaanKonsultasiMedik(null,false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    form.emptTeks();
                    form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
                    form.tampil();
                    this.setCursor(Cursor.getDefaultCursor());
                }        
    }//GEN-LAST:event_BtnPermintaanKonsultasiMedikActionPerformed

    private void kdptgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kdptgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdptgActionPerformed

    private void TPerawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPerawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPerawatActionPerformed

    private void tbPemeriksaanRMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanRMMouseClicked
               if(tabModePemeriksaanRM.getRowCount()!=0){
            try {
                getDataPemeriksaanRM();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbPemeriksaanRMMouseClicked

    private void tbPemeriksaanRMKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanRMKeyReleased
                if(tabModePemeriksaanRM.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPemeriksaanRM();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbPemeriksaanRMKeyReleased

    private void ChkInput4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkInput4ActionPerformed

    private void TAlergi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlergi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TAlergi1KeyPressed

    private void AnamnesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnamnesaKeyPressed

    private void PemeriksaanFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanFisikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemeriksaanFisikKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void AnjuranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnjuranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnjuranKeyPressed

    private void TRpd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRpd1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpd1KeyPressed

    private void TRpk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TRpk1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpk1ActionPerformed

    private void TRpk1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRpk1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpk1KeyPressed

    private void TRpo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TRpo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpo1ActionPerformed

    private void TRpo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRpo1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRpo1KeyPressed

    private void cmbKesadaran1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbKesadaran1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbKesadaran1KeyPressed

    private void BtnRiwayatRadLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatRadLabActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
            resume.emptTeks();
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRiwayatRadLabActionPerformed

    private void BtnRiwayatRadLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRiwayatRadLabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRiwayatRadLabKeyPressed

    private void Operasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Operasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Operasi1KeyPressed

    private void Asesmen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Asesmen1ActionPerformed
        penyakit.isCek();
        penyakit.setNoRw(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
        penyakit.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_Asesmen1ActionPerformed

    private void Obat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Obat1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            //if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                //JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            //}else {
                jmlparsial=0;
                if(aktifkanparsial.equals("yes")){
                    jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kd_pj);
                }
                if(jmlparsial>0){
                    inputResep();
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{
                        inputResep();
                    }
                }
            //}
        }        // TODO add your handling code here:
    }//GEN-LAST:event_Obat1ActionPerformed

    private void Instruksi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Instruksi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Instruksi1KeyPressed

    private void Asesmen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Asesmen2ActionPerformed
        penyakit.isCek();
        penyakit.setNoRw(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
        penyakit.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);    // TODO add your handling code here:
    }//GEN-LAST:event_Asesmen2ActionPerformed

    private void TatalaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalaksanaKeyPressed
        
    }//GEN-LAST:event_TatalaksanaKeyPressed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void EvaluasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EvaluasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EvaluasiKeyPressed

    private void SuspekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuspekKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuspekKeyPressed

    private void KetSuspekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSuspekKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetSuspekKeyPressed

    private void BtnProsedurKFRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnProsedurKFRActionPerformed
       if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMUjiFungsiKFR form=new RMUjiFungsiKFR(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.tampil();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnProsedurKFRActionPerformed

    private void BtnProsedurKFRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnProsedurKFRKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnProsedurKFRKeyPressed

    private void ICareNIKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ICareNIKActionPerformed
        if(TNoRw.getText().trim().equals("") ){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ICareRiwayatPerawatan dlgki=new ICareRiwayatPerawatan(null,false);
            dlgki.setNoRawat(TNoRw.getText(),TNoRw.getText());
            dlgki.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
//    if(tabModekasir.getRowCount()==0){
//            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
//            TCari.requestFocus();
//        }else if(TNoRw.getText().trim().equals("")){
//            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
//            tbKasirRalan.requestFocus();
//        }else{
//            if(tbKasirRalan.getSelectedRow()!= -1){
//                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                variabel=Sequel.cariIsi("select maping_dokter_dpjpvclaim.kd_dokter_bpjs from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter=?",tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
//                if(!variabel.equals("")){
//                    akses.setform("DlgReg");
//                    ICareRiwayatPerawatan dlgki=new ICareRiwayatPerawatan(null,false);
//                    dlgki.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
//                    dlgki.setLocationRelativeTo(internalFrame1);
//                    dlgki.setPasien(Sequel.cariIsi("select pasien.no_ktp from pasien where pasien.no_rkm_medis=?",TNoRMCari.getText()),variabel);   
//                    dlgki.setVisible(true);
//                }else{
//                    JOptionPane.showMessageDialog(null,"Maaf, Dokter tidak terdaftar di mapping dokter BPJS...!!!");  
//                }
//                this.setCursor(Cursor.getDefaultCursor());
//            }
//        }
    }//GEN-LAST:event_ICareNIKActionPerformed

    private void ICareNIKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ICareNIKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ICareNIKKeyPressed

    private void ICareNoKartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ICareNoKartuActionPerformed
        if(TNoRw.getText().trim().equals("") ){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ICareRiwayatPerawatanFKTP dlgki=new ICareRiwayatPerawatanFKTP(null,false);
            dlgki.setNoRawat(TNoRw.getText(),TNoRw.getText());
            dlgki.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
//        if(tabModekasir.getRowCount()==0){
//            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
//            TCari.requestFocus();
//        }else if(TNoRw.getText().trim().equals("")){
//            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
//            tbKasirRalan.requestFocus();
//        }else{
//            if(tbKasirRalan.getSelectedRow()!= -1){
//                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                variabel=Sequel.cariIsi("select maping_dokter_dpjpvclaim.kd_dokter_bpjs from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter=?",tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
//                if(!variabel.equals("")){
//                    akses.setform("DlgReg");
//                    ICareRiwayatPerawatan dlgki=new ICareRiwayatPerawatan(null,false);
//                    dlgki.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
//                    dlgki.setLocationRelativeTo(internalFrame1);
//                    dlgki.setPasien(Sequel.cariIsi("select pasien.no_peserta from pasien where pasien.no_rkm_medis=?",TNoRMCari.getText()),variabel);   
//                    dlgki.setVisible(true);
//                }else{
//                    JOptionPane.showMessageDialog(null,"Maaf, Dokter tidak terdaftar di mapping dokter BPJS...!!!"); 
//                }
//                this.setCursor(Cursor.getDefaultCursor());
//            }
//        }
    }//GEN-LAST:event_ICareNoKartuActionPerformed

    private void ICareNoKartuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ICareNoKartuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ICareNoKartuKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRawatJalan dialog = new DlgRawatJalan(new javax.swing.JFrame(), true);
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
    private widget.TextArea Anamnesa;
    private widget.TextArea Anjuran;
    private widget.Button Asesmen;
    private widget.Button Asesmen1;
    private widget.Button Asesmen2;
    private widget.Button BtnAll;
    private widget.Button BtnAsuhanGizi;
    private widget.Button BtnAwalFisioterapi;
    private widget.Button BtnAwalKeperawatan;
    private widget.Button BtnAwalKeperawatan1;
    private widget.Button BtnAwalKeperawatan2;
    private widget.Button BtnAwalKeperawatan3;
    private widget.Button BtnAwalKeperawatan4;
    private widget.Button BtnAwalKeperawatan5;
    private widget.Button BtnAwalKeperawatanAnak;
    private widget.Button BtnAwalKeperawatanGeriatri;
    private widget.Button BtnAwalKeperawatanGigi;
    private widget.Button BtnAwalKeperawatanIGD;
    private widget.Button BtnAwalKeperawatanKandungan;
    private widget.Button BtnAwalKeperawatanPsikiatri;
    private widget.Button BtnAwalMedis;
    private widget.Button BtnAwalMedis1;
    private widget.Button BtnAwalMedis2;
    private widget.Button BtnAwalMedisAnak;
    private widget.Button BtnAwalMedisBedah;
    private widget.Button BtnAwalMedisGeriatri;
    private widget.Button BtnAwalMedisIGD;
    private widget.Button BtnAwalMedisKandungan;
    private widget.Button BtnAwalMedisMata;
    private widget.Button BtnAwalMedisNeurologi;
    private widget.Button BtnAwalMedisOrthopedi;
    private widget.Button BtnAwalMedisPenyakitDalam;
    private widget.Button BtnAwalMedisPsikiatri;
    private widget.Button BtnAwalMedisTHT;
    private widget.Button BtnBatal;
    private widget.Button BtnBerkasDigital;
    private widget.Button BtnBerkasDigital1;
    private widget.Button BtnCari;
    private widget.Button BtnCatatan;
    private widget.Button BtnCatatanCekGDS;
    private widget.Button BtnCatatanObservasiIGD;
    private widget.Button BtnChecklistPostOperasi;
    private widget.Button BtnChecklistPreOperasi;
    private widget.Button BtnCopyResep;
    private widget.Button BtnCopyResep1;
    private widget.Button BtnCppt;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter3;
    private widget.Button BtnEWS;
    private widget.Button BtnEWS1;
    private widget.Button BtnEdit;
    private widget.Button BtnEdukasiPasienKeluarga;
    private widget.Button BtnHapus;
    private widget.Button BtnHasilEKG;
    private widget.Button BtnHasilEKG1;
    private widget.Button BtnHasilPemeriksaanUSG;
    private widget.Button BtnHasilPemeriksaanUSG1;
    private widget.Button BtnIC;
    private widget.Button BtnIEMR;
    private widget.Button BtnIEMR1;
    private widget.Button BtnInacbgRajal;
    private widget.Button BtnInformasiObat;
    private widget.Button BtnInformasiObat2;
    private widget.Button BtnJadwalOperasi;
    private widget.Button BtnJadwalOperasi1;
    private widget.Button BtnKamar;
    private widget.Button BtnKamar1;
    private widget.Button BtnKeluar;
    private widget.Button BtnKonselingFarmasi;
    private widget.Button BtnMedicalCheckUp;
    private widget.Button BtnMonitoringAsuhanGizi;
    private widget.Button BtnMonitoringReaksiTranfusi;
    private widget.Button BtnObatBhp;
    private widget.Button BtnObatBhp1;
    private widget.Button BtnOdontogram;
    private widget.Button BtnPemantauanPEWSAnak;
    private widget.Button BtnPemantauanPEWSDewasa;
    private widget.Button BtnPenilaianKorbanKekerasan;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhAnak;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhDewasa;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhLansia;
    private widget.Button BtnPenilaianPasienPenyakitMenular;
    private widget.Button BtnPenilaianPasienTerminal;
    private widget.Button BtnPenilaianPreAnestesi;
    private widget.Button BtnPenilaianPreOperasi;
    private widget.Button BtnPenilaianPsikolog;
    private widget.Button BtnPenilaianTambahanBunuhDiri;
    private widget.Button BtnPenilaianTambahanGeriatri;
    private widget.Button BtnPenilaianTambahanMelarikanDiri;
    private widget.Button BtnPenilaianTambahanPerilakuKekerasan;
    private widget.Button BtnPermintaanKonsultasiMedik;
    private widget.Button BtnPermintaanLab;
    private widget.Button BtnPermintaanLab1;
    private widget.Button BtnPermintaanRad;
    private widget.Button BtnPermintaanRad1;
    private widget.Button BtnPrint;
    private widget.Button BtnProsedurKFR;
    private widget.Button BtnRekonsiliasiObat;
    private widget.Button BtnResepLuar;
    private widget.Button BtnResepLuar1;
    private widget.Button BtnResepObat;
    private widget.Button BtnResepObat1;
    private widget.Button BtnResume;
    private widget.Button BtnResumeRajal;
    private widget.Button BtnRiwayat;
    private widget.Button BtnRiwayat1;
    private widget.Button BtnRiwayatLabRad;
    private widget.Button BtnRiwayatRadLab;
    private widget.Button BtnRujukIGD;
    private widget.Button BtnRujukInternal;
    private widget.Button BtnRujukKeluar;
    private widget.Button BtnRujukanFisio;
    private widget.Button BtnSKDP;
    private widget.Button BtnSeekDokter;
    private widget.Button BtnSeekDokter2;
    private widget.Button BtnSeekDokter3;
    private widget.Button BtnSeekPetugas;
    private widget.Button BtnSeekPetugas2;
    private widget.Button BtnSignInSebelumAnestesi;
    private widget.Button BtnSignOutSebelumMenutupLuka;
    private widget.Button BtnSimpan;
    private widget.Button BtnSkriningGiziLanjut;
    private widget.Button BtnSkriningNutrisiAnak;
    private widget.Button BtnSkriningNutrisiDewasa;
    private widget.Button BtnSkriningNutrisiLansia;
    private widget.Button BtnTambahTindakan;
    private widget.Button BtnTemplatePemeriksaan;
    private widget.Button BtnTimeOutSebelumInsisi;
    private widget.Button BtnTmpResep;
    private widget.Button BtnTmpResep1;
    private widget.Button BtnTransferAntarRuang;
    private widget.Button BtnTriaseIGD;
    private widget.Button BtnUjiFungsiKFR;
    private widget.TextArea Catatan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkInput1;
    private widget.CekBox ChkInput2;
    private widget.CekBox ChkInput3;
    private widget.CekBox ChkInput4;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.TextArea Diagnosis;
    private widget.TextArea Evaluasi;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.Button ICareNIK;
    private widget.Button ICareNoKartu;
    private widget.TextArea Instruksi;
    private widget.TextArea Instruksi1;
    private widget.TextBox KdDok;
    private widget.TextBox KdDok2;
    private widget.TextBox KdDok3;
    private widget.TextBox KetSuspek;
    private widget.TextBox KodeDokter;
    private widget.Label LCount;
    private widget.PanelBiasa MAnak;
    private widget.PanelBiasa MBedah;
    private widget.PanelBiasa MFisio;
    private widget.PanelBiasa MGeriatri;
    private widget.PanelBiasa MGigi;
    private widget.PanelBiasa MGizi;
    private widget.PanelBiasa MIgd;
    private widget.PanelBiasa MMata;
    private widget.PanelBiasa MObgyn;
    private widget.PanelBiasa MPd;
    private widget.PanelBiasa MPsikiatri;
    private widget.PanelBiasa MRalan;
    private widget.PanelBiasa MSyaraf;
    private widget.PanelBiasa MTht;
    private widget.PanelBiasa Mall;
    private widget.TextBox NamaDokter;
    private widget.Button Obat;
    private widget.Button Obat1;
    private widget.TextArea Operasi;
    private widget.TextArea Operasi1;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPanel PanelInput1;
    private javax.swing.JPanel PanelInput2;
    private javax.swing.JPanel PanelInput3;
    private javax.swing.JPanel PanelInput4;
    private widget.TextArea PemeriksaanFisik;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll12;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.ScrollPane ScrollMenu;
    private widget.ComboBox Suspek;
    private widget.TextBox TAdnexaKanan;
    private widget.TextBox TAdnexaKiri;
    private widget.TextBox TAlergi;
    private widget.TextBox TAlergi1;
    private widget.TextBox TBentuk;
    private widget.TextBox TBerat;
    private widget.TextBox TCari;
    private widget.TextBox TCariPasien;
    private widget.TextBox TCavumDouglas;
    private widget.TextBox TCavumUteri;
    private widget.TextBox TDenominator;
    private widget.TextBox TDenyut;
    private widget.TextBox TDokter;
    private widget.TextBox TDokter2;
    private widget.TextBox TDokter3;
    private widget.TextBox TGCS;
    private widget.TextBox TInspeksi;
    private widget.TextBox TInspeksiVulva;
    private widget.TextBox TInspekuloGine;
    private widget.TextArea TKeluhan;
    private widget.TextBox TKualitas_dtk;
    private widget.TextBox TKualitas_mnt;
    private widget.TextBox TLetak;
    private widget.TextBox TNadi;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPembukaan;
    private widget.TextArea TPemeriksaan;
    private widget.TextArea TPenilaian;
    private widget.TextBox TPenurunan;
    private widget.TextBox TPerawat;
    private widget.TextBox TPerawat2;
    private widget.TextBox TPortio;
    private widget.TextBox TPortioDalam;
    private widget.TextBox TPortioInspekulo;
    private widget.TextBox TRespirasi;
    private widget.TextBox TRpd;
    private widget.TextBox TRpd1;
    private widget.TextBox TRpk;
    private widget.TextBox TRpk1;
    private widget.TextBox TRpo;
    private widget.TextBox TRpo1;
    private widget.TextBox TSondage;
    private widget.TextBox TSpo2;
    private widget.TextBox TSuhu;
    private widget.TextBox TTebal;
    private widget.TextBox TTensi;
    private widget.TextBox TTinggi;
    private widget.TextBox TTinggi_uteri;
    private widget.TextBox TUkuran;
    private widget.TextBox TVulva;
    private widget.TextBox TVulvaInspekulo;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRawatTindakanDokter;
    private javax.swing.JTabbedPane TabRawatTindakanDokterPetugas;
    private javax.swing.JTabbedPane TabRawatTindakanPetugas;
    private widget.TextArea Tatalaksana;
    private widget.TextArea TindakLanjut;
    private widget.Button btnPasien;
    private widget.ComboBox cmbAlbus;
    private widget.ComboBox cmbArah;
    private widget.ComboBox cmbDalam;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbFeto;
    private widget.ComboBox cmbFluksus;
    private widget.ComboBox cmbFluorGine;
    private widget.ComboBox cmbFluxusGine;
    private widget.ComboBox cmbImun;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbJanin;
    private widget.ComboBox cmbKesadaran;
    private widget.ComboBox cmbKesadaran1;
    private widget.ComboBox cmbKetuban;
    private widget.ComboBox cmbKontraksi;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox cmbMobilitas;
    private widget.ComboBox cmbNyeriTekan;
    private widget.ComboBox cmbPanggul;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
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
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel3;
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
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel62;
    private widget.Label jLabel64;
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
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private widget.TextBox kdptg;
    private widget.TextBox kdptg2;
    private widget.Label label14;
    private widget.Label label15;
    private laporan.PanelDiagnosa panelDiagnosa1;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass12;
    private widget.panelisi panelGlass13;
    private widget.panelisi panelGlass14;
    private widget.panelisi panelGlass15;
    private widget.panelisi panelGlass16;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbCatatan;
    private widget.Table tbPemeriksaan;
    private widget.Table tbPemeriksaanGinekologi;
    private widget.Table tbPemeriksaanObstetri;
    private widget.Table tbPemeriksaanRM;
    private widget.Table tbRawatDr;
    private widget.Table tbRawatDrPr;
    private widget.Table tbRawatPr;
    private widget.Table tbTindakan;
    private widget.Table tbTindakan2;
    private widget.Table tbTindakan3;
    // End of variables declaration//GEN-END:variables

    private void tampilDr() {
        Valid.tabelKosong(tabModeDr);
        try{
            ps=koneksi.prepareStatement("select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_jl_dr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_dr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.biaya_rawat,rawat_jl_dr.kd_jenis_prw, " +
                   "rawat_jl_dr.tarif_tindakandr,rawat_jl_dr.kso,rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.menejemen "+
                   "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                   "dokter inner join rawat_jl_dr "+
                   "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                   "where rawat_jl_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                   (TCari.getText().trim().equals("")?"":"and (rawat_jl_dr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                   "jns_perawatan.nm_perawatan like ? or rawat_jl_dr.kd_dokter like ? or dokter.nm_dokter like ? )")+
                   " order by rawat_jl_dr.no_rawat,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat desc");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeDr.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),
                        rs.getString(8),rs.getDouble(9),rs.getString("kd_jenis_prw"),rs.getString("tarif_tindakandr"),rs.getString("kso"),
                        rs.getString("material"),rs.getString("bhp"),rs.getString("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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
        LCount.setText(""+tabModeDr.getRowCount());
    }
    
    private void getDataDr() {
        if(tbRawatDr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),1).toString());
            TNoRM.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),2).toString());
            TPasien.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),3).toString());
            KdDok.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),5).toString());
            TDokter.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),6).toString());
            cmbJam.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),8).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),8).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),8).toString().substring(6,8));
            Valid.SetTgl(DTPTgl,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),7).toString());
        }
    }

    private void tampilPr() {
        Valid.tabelKosong(tabModePr);
        try{  
            ps2=koneksi.prepareStatement("select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_jl_pr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_pr.nip,petugas.nama,"+
                   "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat,rawat_jl_pr.biaya_rawat,rawat_jl_pr.kd_jenis_prw, " +
                   "rawat_jl_pr.tarif_tindakanpr,rawat_jl_pr.kso,rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.menejemen "+
                   "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                   "petugas inner join rawat_jl_pr "+
                   "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_pr.nip=petugas.nip where  "+
                   "rawat_jl_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                   (TCari.getText().trim().equals("")?"":"and (rawat_jl_pr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                   "jns_perawatan.nm_perawatan like ? or rawat_jl_pr.nip like ? or petugas.nama like ?) ")+
                   "order by rawat_jl_pr.no_rawat,rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat desc"); 
            try{
                ps2.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps2.setString(4,"%"+TCari.getText().trim()+"%");
                    ps2.setString(5,"%"+TCari.getText().trim()+"%");
                    ps2.setString(6,"%"+TCari.getText().trim()+"%");
                    ps2.setString(7,"%"+TCari.getText().trim()+"%");
                    ps2.setString(8,"%"+TCari.getText().trim()+"%");
                    ps2.setString(9,"%"+TCari.getText().trim()+"%");
                }
                rs=ps2.executeQuery();
                while(rs.next()){
                    tabModePr.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getDouble(9),
                        rs.getString("kd_jenis_prw"),rs.getString("tarif_tindakanpr"),
                        rs.getString("kso"),rs.getString("material"),
                        rs.getString("bhp"),rs.getString("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }                  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModePr.getRowCount());
    }

    private void getDataPr() {
        if(tbRawatPr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),1).toString());
            TNoRM.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),2).toString());
            TPasien.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),3).toString());   
            kdptg.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),5).toString());
            TPerawat.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),6).toString());
            cmbJam.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),8).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),8).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),8).toString().substring(6,8));
            Valid.SetTgl(DTPTgl,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),7).toString());
        }
    }
    
    private void tampilDrPr() {
        Valid.tabelKosong(tabModeDrPr);
        try{
            ps3=koneksi.prepareStatement("select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_jl_drpr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_drpr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.biaya_rawat,rawat_jl_drpr.kd_jenis_prw, " +
                   "rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,rawat_jl_drpr.kso,rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.menejemen  "+
                   "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                   "dokter inner join rawat_jl_drpr inner join petugas on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter and rawat_jl_drpr.nip=petugas.nip "+
                   "where rawat_jl_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                   (TCari.getText().trim().equals("")?"":"and (rawat_jl_drpr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                   "jns_perawatan.nm_perawatan like ? or rawat_jl_drpr.kd_dokter like ? or dokter.nm_dokter like ? or "+
                   "rawat_jl_drpr.nip like ? or petugas.nama like ?)")+
                   " order by rawat_jl_drpr.no_rawat,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat desc");
            try{
                ps3.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps3.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps3.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps3.setString(4,"%"+TCari.getText().trim()+"%");
                    ps3.setString(5,"%"+TCari.getText().trim()+"%");
                    ps3.setString(6,"%"+TCari.getText().trim()+"%");
                    ps3.setString(7,"%"+TCari.getText().trim()+"%");
                    ps3.setString(8,"%"+TCari.getText().trim()+"%");
                    ps3.setString(9,"%"+TCari.getText().trim()+"%");
                    ps3.setString(10,"%"+TCari.getText().trim()+"%");
                    ps3.setString(11,"%"+TCari.getText().trim()+"%");
                }
                rs=ps3.executeQuery();
                while(rs.next()){
                    tabModeDrPr.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),
                        rs.getString(8),rs.getString(9),rs.getString(10),rs.getDouble(11),rs.getString("kd_jenis_prw"),
                        rs.getString("tarif_tindakandr"),rs.getString("tarif_tindakanpr"),rs.getString("kso"),
                        rs.getString("material"),rs.getString("bhp"),rs.getString("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps3!=null){
                    ps3.close();
                }
            }              
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModeDrPr.getRowCount());
    }
    
    private void getDataDrPr() {
        if(tbRawatDrPr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),1).toString());
            TNoRM.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),2).toString());
            TPasien.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),3).toString());
            KdDok2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),5).toString());
            TDokter2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),6).toString());
            kdptg2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),7).toString());
            TPerawat2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),8).toString());
            cmbJam.setSelectedItem(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),10).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),10).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),10).toString().substring(6,8));
            Valid.SetTgl(DTPTgl,tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),9).toString());
        }
    }
    
    private void isRawat(){
        //pemeriksaan_ralan
        Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",TNoRM,TNoRw.getText());
        Sequel.cariIsi("select pemeriksaan_ralan.alergi from pemeriksaan_ralan INNER JOIN reg_periksa ON pemeriksaan_ralan.no_rawat = reg_periksa.no_rawat where no_rkm_medis=? ORDER BY pemeriksaan_ralan.tgl_perawatan desc",TAlergi,TNoRM.getText());
        Sequel.cariIsi("select pemeriksaan_ralan.rpd from pemeriksaan_ralan INNER JOIN reg_periksa ON pemeriksaan_ralan.no_rawat = reg_periksa.no_rawat where no_rkm_medis=? ORDER BY pemeriksaan_ralan.tgl_perawatan desc",TRpd,TNoRM.getText());
        Sequel.cariIsi("select pemeriksaan_ralan.rpk from pemeriksaan_ralan INNER JOIN reg_periksa ON pemeriksaan_ralan.no_rawat = reg_periksa.no_rawat where no_rkm_medis=? ORDER BY pemeriksaan_ralan.tgl_perawatan desc",TRpk,TNoRM.getText());
        Sequel.cariIsi("select pemeriksaan_ralan.rpo from pemeriksaan_ralan INNER JOIN reg_periksa ON pemeriksaan_ralan.no_rawat = reg_periksa.no_rawat where no_rkm_medis=? ORDER BY pemeriksaan_ralan.tgl_perawatan desc",TRpo,TNoRM.getText());
        Sequel.cariIsi("select pemeriksaan_ralan.operasi from pemeriksaan_ralan INNER JOIN reg_periksa ON pemeriksaan_ralan.no_rawat = reg_periksa.no_rawat where no_rkm_medis=? ORDER BY pemeriksaan_ralan.tgl_perawatan desc",Operasi,TNoRM.getText());
        
        //pemeriksaan_ralan_rehab
        Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",TNoRM,TNoRw.getText());
        Sequel.cariIsi("select pemeriksaan_ralan_rehab.alergi from pemeriksaan_ralan_rehab INNER JOIN reg_periksa ON pemeriksaan_ralan_rehab.no_rawat = reg_periksa.no_rawat where no_rkm_medis=? ORDER BY pemeriksaan_ralan_rehab.tgl_perawatan desc",TAlergi1,TNoRM.getText());
        Sequel.cariIsi("select pemeriksaan_ralan_rehab.rpd from pemeriksaan_ralan_rehab INNER JOIN reg_periksa ON pemeriksaan_ralan_rehab.no_rawat = reg_periksa.no_rawat where no_rkm_medis=? ORDER BY pemeriksaan_ralan_rehab.tgl_perawatan desc",TRpd1,TNoRM.getText());
        Sequel.cariIsi("select pemeriksaan_ralan_rehab.rpk from pemeriksaan_ralan_rehab INNER JOIN reg_periksa ON pemeriksaan_ralan_rehab.no_rawat = reg_periksa.no_rawat where no_rkm_medis=? ORDER BY pemeriksaan_ralan_rehab.tgl_perawatan desc",TRpk1,TNoRM.getText());
        Sequel.cariIsi("select pemeriksaan_ralan_rehab.rpo from pemeriksaan_ralan_rehab INNER JOIN reg_periksa ON pemeriksaan_ralan_rehab.no_rawat = reg_periksa.no_rawat where no_rkm_medis=? ORDER BY pemeriksaan_ralan_rehab.tgl_perawatan desc",TRpo1,TNoRM.getText());
        Sequel.cariIsi("select pemeriksaan_ralan_rehab.operasi from pemeriksaan_ralan_rehab INNER JOIN reg_periksa ON pemeriksaan_ralan_rehab.no_rawat = reg_periksa.no_rawat where no_rkm_medis=? ORDER BY pemeriksaan_ralan_rehab.tgl_perawatan desc",Operasi1,TNoRM.getText());
        
        //Menampilkan Subjek
        //if(TKeluhan.getText().isEmpty()){
          //TKeluhan.setText(Sequel.cariIsi("select keluhan from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));  
       // }
        
        //Menampilkan Objek
       // if(TPemeriksaan.getText().isEmpty()){
         // TPemeriksaan.setText(Sequel.cariIsi("select pemeriksaan from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
       // }
        
        //Menampilkan Instruksi
        if(Instruksi.getText().isEmpty()){
          Instruksi.setText(Sequel.cariIsi("select instruksi from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
        }
        //
        //Menampilkan Assesmen
        if(TPenilaian.getText().isEmpty()){
//          TPenilaian.setText(Sequel.cariIsi("select penilaian from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
            try {
            ps=koneksi.prepareStatement(
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat='"+TNoRw.getText()+"' order by diagnosa_pasien.prioritas ASC ");
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                while(rs.next()){
                    Listpenyakit=rs.getString("kd_penyakit")+"-"+rs.getString("nm_penyakit");
                    namaPenyakit=namaPenyakit+""+Listpenyakit+",  "+"\n";
                }
                 TPenilaian.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
         
        //Menapilkan Plan
        if(TindakLanjut.getText().isEmpty()){
          //TindakLanjut.setText(Sequel.cariIsi("select rtl from pemeriksaan_ralan where no_rawat=?",TNoRw.getText())); 
        try {
          ps=koneksi.prepareStatement(
                    " select concat(databarang.nama_brng,' ',resep_dokter.jml,' ',kodesatuan.satuan,' ',resep_dokter.aturan_pakai) as obat FROM resep_dokter INNER JOIN resep_obat ON resep_dokter.no_resep = resep_obat.no_resep INNER JOIN databarang ON resep_dokter.kode_brng = databarang.kode_brng INNER JOIN kodesatuan ON databarang.kode_sat = kodesatuan.kode_sat where resep_obat.no_rawat='"+TNoRw.getText()+"'"
           );
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                while(rs.next()){
                    Listpenyakit=rs.getString("obat");
                   namaPenyakit=namaPenyakit+""+Listpenyakit+",  "+"\n";
                }
                 TindakLanjut.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
        
        //Menapilkan Racikan Obat
        try {
            ps=koneksi.prepareStatement(
                    " select concat(resep_dokter_racikan.nama_racik,' ',resep_dokter_racikan.jml_dr,' ',resep_dokter_racikan.aturan_pakai) as obat FROM resep_dokter_racikan INNER JOIN resep_obat ON resep_dokter_racikan.no_resep = resep_obat.no_resep where resep_obat.no_rawat='"+TNoRw.getText()+"'"
            );
            try {
                rs=ps.executeQuery();
                namaPenyakit="";
                while(rs.next()){
                    Listpenyakit=rs.getString("obat");
                    namaPenyakit=namaPenyakit+""+Listpenyakit+","+"\n";
                }
                 TindakLanjut.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
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
           //Menapilkan Plan Tamplate dokter
       //try {
        //    ps=koneksi.prepareStatement(
        //            " select rtl from Pemertiksaan_ralan where pemeriksaan_ralan.no_rawat='"+TNoRw.getText()+"'"
        //    );
        //    try {
        //        rs=ps.executeQuery();
        //        namaPenyakit="";
        //        while(rs.next()){
        //            Listpenyakit=rs.getString("rtl");
        //            namaPenyakit=namaPenyakit+""+Listpenyakit+",  "+"\n";
        //        }
        //         TindakLanjut.append(namaPenyakit.length()>0 ? namaPenyakit.substring(0,namaPenyakit.length()-1):"");
        //    } catch (Exception e) {
        //        System.out.println("Notif : "+e);
        //    } finally{
        //        if(rs!=null){
        //            rs.close();
        //        }
        //        if(ps!=null){
        //            ps.close();
        //        }
        //    }
        //} catch (Exception e) {
        //    System.out.println("Notif : "+e);
        //}
        
        //
            
            //Menampilkan Keperawatan Ralan
        if(Sequel.cariInteger("select count(no_rawat) from penilaian_awal_keperawatan_ralan where no_rawat='"+TNoRw.getText()+"' ")>0){
            TSuhu.setText(Sequel.cariIsi("select suhu from penilaian_awal_keperawatan_ralan where no_rawat=?",TNoRw.getText()));
            TTensi.setText(Sequel.cariIsi("select td from penilaian_awal_keperawatan_ralan where no_rawat=?",TNoRw.getText()));
            TBerat.setText(Sequel.cariIsi("select bb from penilaian_awal_keperawatan_ralan where no_rawat=?",TNoRw.getText()));
            TTinggi.setText(Sequel.cariIsi("select tb from penilaian_awal_keperawatan_ralan where no_rawat=?",TNoRw.getText()));
            TNadi.setText(Sequel.cariIsi("select nadi from penilaian_awal_keperawatan_ralan where no_rawat=?",TNoRw.getText()));
            TRespirasi.setText(Sequel.cariIsi("select rr from penilaian_awal_keperawatan_ralan where no_rawat=?",TNoRw.getText()));
            TGCS.setText(Sequel.cariIsi("select gcs from penilaian_awal_keperawatan_ralan where no_rawat=?",TNoRw.getText()));
        }else if(Sequel.cariInteger("select count(no_rawat) from penilaian_awal_keperawatan_ralan_bayi where no_rawat='"+TNoRw.getText()+"' ")>0){
             //Menampilkan Keperawatan Ralan Bayi
            TSuhu.setText(Sequel.cariIsi("select suhu from penilaian_awal_keperawatan_ralan_bayi where no_rawat=?",TNoRw.getText()));
            TTensi.setText(Sequel.cariIsi("select td from penilaian_awal_keperawatan_ralan_bayi where no_rawat=?",TNoRw.getText()));
            TBerat.setText(Sequel.cariIsi("select bb from penilaian_awal_keperawatan_ralan_bayi where no_rawat=?",TNoRw.getText()));
            TTinggi.setText(Sequel.cariIsi("select tb from penilaian_awal_keperawatan_ralan_bayi where no_rawat=?",TNoRw.getText()));
            TNadi.setText(Sequel.cariIsi("select nadi from penilaian_awal_keperawatan_ralan_bayi where no_rawat=?",TNoRw.getText()));
            TRespirasi.setText(Sequel.cariIsi("select rr from penilaian_awal_keperawatan_ralan_bayi where no_rawat=?",TNoRw.getText()));
            TGCS.setText(Sequel.cariIsi("select gcs from penilaian_awal_keperawatan_ralan_bayi where no_rawat=?",TNoRw.getText()));
         }else if(Sequel.cariInteger("select count(no_rawat) from penilaian_awal_keperawatan_gigi where no_rawat='"+TNoRw.getText()+"' ")>0){
             //Menampilkan Keperawatan Ralan Gigi
            TSuhu.setText(Sequel.cariIsi("select suhu from penilaian_awal_keperawatan_gigi where no_rawat=?",TNoRw.getText()));
            TTensi.setText(Sequel.cariIsi("select td from penilaian_awal_keperawatan_gigi where no_rawat=?",TNoRw.getText()));
            TBerat.setText(Sequel.cariIsi("select bb from penilaian_awal_keperawatan_gigi where no_rawat=?",TNoRw.getText()));
            TTinggi.setText(Sequel.cariIsi("select tb from penilaian_awal_keperawatan_gigi where no_rawat=?",TNoRw.getText()));
            TNadi.setText(Sequel.cariIsi("select nadi from penilaian_awal_keperawatan_gigi where no_rawat=?",TNoRw.getText()));
            TRespirasi.setText(Sequel.cariIsi("select rr from penilaian_awal_keperawatan_gigi where no_rawat=?",TNoRw.getText()));
         }else if(Sequel.cariInteger("select count(no_rawat) from penilaian_awal_keperawatan_kebidanan where no_rawat='"+TNoRw.getText()+"' ")>0){
       //Menampilkan Keperawatan Ralan Kebidanan
            TSuhu.setText(Sequel.cariIsi("select suhu from penilaian_awal_keperawatan_kebidanan where no_rawat=?",TNoRw.getText()));
            TTensi.setText(Sequel.cariIsi("select td from penilaian_awal_keperawatan_kebidanan where no_rawat=?",TNoRw.getText()));
            TBerat.setText(Sequel.cariIsi("select bb from penilaian_awal_keperawatan_kebidanan where no_rawat=?",TNoRw.getText()));
            TTinggi.setText(Sequel.cariIsi("select tb from penilaian_awal_keperawatan_kebidanan where no_rawat=?",TNoRw.getText()));
            TNadi.setText(Sequel.cariIsi("select nadi from penilaian_awal_keperawatan_kebidanan where no_rawat=?",TNoRw.getText()));
            TRespirasi.setText(Sequel.cariIsi("select rr from penilaian_awal_keperawatan_kebidanan where no_rawat=?",TNoRw.getText()));
            TGCS.setText(Sequel.cariIsi("select gcs from penilaian_awal_keperawatan_kebidanan where no_rawat=?",TNoRw.getText()));
         }else if(Sequel.cariInteger("select count(no_rawat) from penilaian_awal_keperawatan_igdkeb where no_rawat='"+TNoRw.getText()+"' ")>0){
       //Menampilkan Keperawatan IGD Kebidanan
            TSuhu.setText(Sequel.cariIsi("select suhu from penilaian_awal_keperawatan_igdkeb where no_rawat=?",TNoRw.getText()));
            TTensi.setText(Sequel.cariIsi("select td from penilaian_awal_keperawatan_igdkeb where no_rawat=?",TNoRw.getText()));
            TBerat.setText(Sequel.cariIsi("select bb from penilaian_awal_keperawatan_igdkeb where no_rawat=?",TNoRw.getText()));
            TTinggi.setText(Sequel.cariIsi("select tb from penilaian_awal_keperawatan_igdkeb where no_rawat=?",TNoRw.getText()));
            TNadi.setText(Sequel.cariIsi("select nadi from penilaian_awal_keperawatan_igdkeb where no_rawat=?",TNoRw.getText()));
            TRespirasi.setText(Sequel.cariIsi("select rr from penilaian_awal_keperawatan_igdkeb where no_rawat=?",TNoRw.getText()));
            TGCS.setText(Sequel.cariIsi("select gcs from penilaian_awal_keperawatan_igdkeb where no_rawat=?",TNoRw.getText()));
         }else if(Sequel.cariInteger("select count(no_rawat) from penilaian_awal_keperawatan_mata where no_rawat='"+TNoRw.getText()+"' ")>0){
       //Menampilkan Keperawatan Mata
            TSuhu.setText(Sequel.cariIsi("select suhu from penilaian_awal_keperawatan_mata where no_rawat=?",TNoRw.getText()));
            TTensi.setText(Sequel.cariIsi("select td from penilaian_awal_keperawatan_mata where no_rawat=?",TNoRw.getText()));
            TBerat.setText(Sequel.cariIsi("select bb from penilaian_awal_keperawatan_mata where no_rawat=?",TNoRw.getText()));
            TTinggi.setText(Sequel.cariIsi("select tb from penilaian_awal_keperawatan_mata where no_rawat=?",TNoRw.getText()));
            TNadi.setText(Sequel.cariIsi("select nadi from penilaian_awal_keperawatan_mata where no_rawat=?",TNoRw.getText()));
            TRespirasi.setText(Sequel.cariIsi("select rr from penilaian_awal_keperawatan_mata where no_rawat=?",TNoRw.getText()));
            TGCS.setText(Sequel.cariIsi("select gcs from penilaian_awal_keperawatan_mata where no_rawat=?",TNoRw.getText()));
         }else if(Sequel.cariInteger("select count(no_rawat) from penilaian_medis_ralan_geriatri where no_rawat='"+TNoRw.getText()+"' ")>0){
       //Menampilkan Medis Ralan Geritari
            TSuhu.setText(Sequel.cariIsi("select suhu from penilaian_medis_ralan_geriatri where no_rawat=?",TNoRw.getText()));
            TTensi.setText(Sequel.cariIsi("select td from penilaian_medis_ralan_geriatri where no_rawat=?",TNoRw.getText()));
            //TBerat.setText(Sequel.cariIsi("select bb from penilaian_medis_ralan_geriatri where no_rawat=?",TNoRw.getText()));
            //TTinggi.setText(Sequel.cariIsi("select tb from penilaian_medis_ralan_geriatri where no_rawat=?",TNoRw.getText()));
            TNadi.setText(Sequel.cariIsi("select nadi from penilaian_medis_ralan_geriatri where no_rawat=?",TNoRw.getText()));
            TRespirasi.setText(Sequel.cariIsi("select rr from penilaian_medis_ralan_geriatri where no_rawat=?",TNoRw.getText()));
            //TGCS.setText(Sequel.cariIsi("select gcs from penilaian_medis_ralan_geriatri where no_rawat=?",TNoRw.getText()));
         }else if(Sequel.cariInteger("select count(no_rawat) from penilaian_awal_keperawatan_ralan_psikiatri where no_rawat='"+TNoRw.getText()+"' ")>0){
       //Menampilkan Keperawatan Ralan Psikitari
            TSuhu.setText(Sequel.cariIsi("select suhu from penilaian_awal_keperawatan_ralan_psikiatri where no_rawat=?",TNoRw.getText()));
            TTensi.setText(Sequel.cariIsi("select td from penilaian_awal_keperawatan_ralan_psikiatri where no_rawat=?",TNoRw.getText()));
            TBerat.setText(Sequel.cariIsi("select bb from penilaian_awal_keperawatan_ralan_psikiatri where no_rawat=?",TNoRw.getText()));
            TTinggi.setText(Sequel.cariIsi("select tb from penilaian_awal_keperawatan_ralan_psikiatri where no_rawat=?",TNoRw.getText()));
            TNadi.setText(Sequel.cariIsi("select nadi from penilaian_awal_keperawatan_ralan_psikiatri where no_rawat=?",TNoRw.getText()));
            TRespirasi.setText(Sequel.cariIsi("select rr from penilaian_awal_keperawatan_ralan_psikiatri where no_rawat=?",TNoRw.getText()));
            TGCS.setText(Sequel.cariIsi("select gcs from penilaian_awal_keperawatan_ralan_psikiatri where no_rawat=?",TNoRw.getText()));
         }else if(Sequel.cariInteger("select count(no_rawat) from penilaian_medis_igd where no_rawat='"+TNoRw.getText()+"' ")>0){
       //Menampilkan Medis IGD
            TSuhu.setText(Sequel.cariIsi("select suhu from penilaian_medis_igd where no_rawat=?",TNoRw.getText()));
            TTensi.setText(Sequel.cariIsi("select td from penilaian_medis_igd where no_rawat=?",TNoRw.getText()));
            TBerat.setText(Sequel.cariIsi("select bb from penilaian_medis_igd where no_rawat=?",TNoRw.getText()));
            TTinggi.setText(Sequel.cariIsi("select tb from penilaian_medis_igd where no_rawat=?",TNoRw.getText()));
            TNadi.setText(Sequel.cariIsi("select nadi from penilaian_medis_igd where no_rawat=?",TNoRw.getText()));
            TRespirasi.setText(Sequel.cariIsi("select rr from penilaian_medis_igd where no_rawat=?",TNoRw.getText()));
            TGCS.setText(Sequel.cariIsi("select gcs from penilaian_medis_igd where no_rawat=?",TNoRw.getText()));
         }else if(Sequel.cariInteger("select count(no_rawat) from pemeriksaan_ralan where no_rawat='"+TNoRw.getText()+"' ")>0){
       //Menampilkan ttv pemeriksaan
            TSuhu.setText(Sequel.cariIsi("select suhu_tubuh from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
            TTensi.setText(Sequel.cariIsi("select tensi from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
            TBerat.setText(Sequel.cariIsi("select berat from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
            TTinggi.setText(Sequel.cariIsi("select tinggi from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
            TNadi.setText(Sequel.cariIsi("select nadi from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
            TRespirasi.setText(Sequel.cariIsi("select respirasi from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
            TGCS.setText(Sequel.cariIsi("select gcs from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
         }
        TCariPasien.setText(TNoRM.getText());
    }

    private void isPsien(){
        Sequel.cariIsi("select concat(pasien.nm_pasien,' (',pasien.umur,')') from pasien where pasien.no_rkm_medis=? ",TPasien,TNoRM.getText());
    }
    
    public void setNoRm(String norwt,Date tgl1,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText("");
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();  
        KdDok.setText(Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",norwt));
        TDokter.setText(dokter.tampil3(KdDok.getText()));
        KdDok2.setText(KdDok.getText());
        KdDok3.setText(KdDok.getText());
        TDokter2.setText(TDokter.getText()); 
        TDokter3.setText(TDokter.getText()); 
        ChkInput.setSelected(true);
        isForm();
        ChkInput1.setSelected(true);
        isForm2();
        ChkInput2.setSelected(true);
        isForm3(); 
        ChkInput3.setSelected(true);
        isForm4();
        ChkInput4.setSelected(true);
        isForm5();
        TabRawatMouseClicked(null);
    }
    
    public void setNoRm1(String norwt,Date tgl1,Date tgl2) {
        TNoRw.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();  
        KdDok.setText(Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",norwt));
        TDokter.setText(dokter.tampil3(KdDok.getText()));
        KdDok2.setText(KdDok.getText());
        KdDok3.setText(KdDok.getText());
        TDokter2.setText(TDokter.getText()); 
        TDokter3.setText(TDokter.getText()); 
        ChkInput4.setSelected(true);
        isForm5();
        TabRawatMouseClicked(null);
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,400));
            panelGlass12.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass12.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    private void isForm4(){
        if(ChkInput3.isSelected()==true){
            ChkInput3.setVisible(false);
            PanelInput3.setPreferredSize(new Dimension(WIDTH,140));
            panelGlass15.setVisible(true);      
            ChkInput3.setVisible(true);
        }else if(ChkInput3.isSelected()==false){           
            ChkInput3.setVisible(false);            
            PanelInput3.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass15.setVisible(false);      
            ChkInput3.setVisible(true);
        }
    }
    
    private void isForm5(){
        if(ChkInput4.isSelected()==true){
            ChkInput4.setVisible(false);
            PanelInput4.setPreferredSize(new Dimension(WIDTH,400));
            panelGlass16.setVisible(true);      
            ChkInput4.setVisible(true);
        }else if(ChkInput4.isSelected()==false){           
            ChkInput4.setVisible(false);            
            PanelInput4.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass16.setVisible(false);      
            ChkInput4.setVisible(true);
        }
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(300,HEIGHT));
            FormMenu.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }
    
    public void isCek(){
        tinggi=0;
        BtnSimpan.setEnabled(akses.gettindakan_ralan());
        BtnHapus.setEnabled(akses.gettindakan_ralan());
        BtnEdit.setEnabled(akses.gettindakan_ralan());
        BtnPrint.setEnabled(akses.gettindakan_ralan());
        BtnTambahTindakan.setEnabled(akses.gettarif_ralan());    
        BtnResepObat.setVisible(akses.getresep_dokter());
        BtnCopyResep.setVisible(akses.getresep_dokter());
        if(akses.getresep_dokter()==true){
            tinggi=tinggi+48;
        }
        BtnObatBhp.setVisible(akses.getberi_obat());   
        if(akses.getberi_obat()==true){
            tinggi=tinggi+48;
        }
        BtnPermintaanLab.setVisible(akses.getpermintaan_lab());   
        if(akses.getpermintaan_lab()==true){
            tinggi=tinggi+24;
        }
        BtnBerkasDigital.setVisible(akses.getberkas_digital_perawatan());   
        if(akses.getberkas_digital_perawatan()==true){
            tinggi=tinggi+24;
        }
        BtnPermintaanRad.setVisible(akses.getpermintaan_radiologi());  
        if(akses.getpermintaan_radiologi()==true){
            tinggi=tinggi+24;
        }
        BtnKamar.setVisible(akses.getkamar_inap());   
        if(akses.getkamar_inap()==true){
            tinggi=tinggi+24;
        }
        BtnRujukInternal.setVisible(akses.getrujukan_poli_internal());
        if(akses.getrujukan_poli_internal()==true){
            tinggi=tinggi+24;
        }
        BtnRujukKeluar.setVisible(akses.getrujukan_keluar());
        if(akses.getrujukan_keluar()==true){
            tinggi=tinggi+24;
        }
        BtnSKDP.setVisible(akses.getskdp_bpjs());     
        if(akses.getskdp_bpjs()==true){
            tinggi=tinggi+24;
        }
        BtnCatatan.setVisible(akses.getcatatan_pasien());
        if(akses.getcatatan_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnTriaseIGD.setVisible(akses.getdata_triase_igd());  
        if(akses.getdata_triase_igd()==true){
            tinggi=tinggi+24;
        }
//        BtnResume.setVisible(akses.getdata_resume_pasien());   
//        if(akses.getdata_resume_pasien()==true){
//            tinggi=tinggi+24;
//        }
        BtnResepLuar.setVisible(akses.getresep_luar()); 
        if(akses.getresep_luar()==true){
            tinggi=tinggi+24;
        }
//        BtnAwalKeperawatan.setVisible(akses.getpenilaian_awal_keperawatan_ralan());  
//        if(akses.getpenilaian_awal_keperawatan_ralan()==true){
//            tinggi=tinggi+24;
//        }
//        BtnAwalKeperawatanIGD.setVisible(akses.getpenilaian_awal_keperawatan_igd());  
//        if(akses.getpenilaian_awal_keperawatan_igd()==true){
//            tinggi=tinggi+24;
//        }
//        BtnAwalKeperawatanGigi.setVisible(akses.getpenilaian_awal_keperawatan_gigi());   
//        if(akses.getpenilaian_awal_keperawatan_gigi()==true){
//            tinggi=tinggi+24;
//        }
//        BtnAwalKeperawatanKandungan.setVisible(akses.getpenilaian_awal_keperawatan_kebidanan()); 
//        if(akses.getpenilaian_awal_keperawatan_kebidanan()==true){
//            tinggi=tinggi+24;
//        }
//        BtnAwalKeperawatanAnak.setVisible(akses.getpenilaian_awal_keperawatan_anak());
//        if(akses.getpenilaian_awal_keperawatan_anak()==true){
//            tinggi=tinggi+24;
//        }
//        BtnAwalKeperawatanPsikiatri.setVisible(akses.getpenilaian_awal_keperawatan_psikiatri());
//        if(akses.getpenilaian_awal_keperawatan_psikiatri()==true){
//            tinggi=tinggi+24;
//        }
        BtnAwalMedis.setVisible(akses.getpenilaian_awal_medis_ralan()); 
        if(akses.getpenilaian_awal_medis_ralan()==true){
            tinggi=tinggi+24;
        }      
        BtnAwalMedisKandungan.setVisible(akses.getpenilaian_awal_medis_ralan_kebidanan()); 
        if(akses.getpenilaian_awal_medis_ralan_kebidanan()==true){
            tinggi=tinggi+24;
        } 
        BtnRiwayat.setVisible(akses.getresume_pasien());
//        Btn5Soap.setEnabled(akses.getresume_pasien());
        if(akses.getresume_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnJadwalOperasi.setVisible(akses.getbooking_operasi());   
        if(akses.getbooking_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisIGD.setVisible(akses.getpenilaian_awal_medis_igd()); 
        if(akses.getpenilaian_awal_medis_igd()==true){
            tinggi=tinggi+24;
        }  
        BtnAwalMedisAnak.setVisible(akses.getpenilaian_awal_medis_ralan_anak()); 
        if(akses.getpenilaian_awal_medis_ralan_anak()==true){
            tinggi=tinggi+24;
        }
        BtnAwalFisioterapi.setVisible(akses.getpenilaian_fisioterapi()); 
        if(akses.getpenilaian_fisioterapi()==true){
            tinggi=tinggi+24;
        }
        BtnMedicalCheckUp.setVisible(akses.getpenilaian_mcu()); 
        if(akses.getpenilaian_mcu()==true){
            tinggi=tinggi+24;
        }
        BtnUjiFungsiKFR.setVisible(akses.getuji_fungsi_kfr()); 
        if(akses.getuji_fungsi_kfr()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiIGD.setVisible(akses.getcatatan_observasi_igd()); 
        if(akses.getcatatan_observasi_igd()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisTHT.setVisible(akses.getpenilaian_awal_medis_ralan_tht()); 
        if(akses.getpenilaian_awal_medis_ralan_tht()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisPsikiatri.setVisible(akses.getpenilaian_awal_medis_ralan_psikiatri()); 
        if(akses.getpenilaian_awal_medis_ralan_psikiatri()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisPenyakitDalam.setVisible(akses.getpenilaian_awal_medis_ralan_penyakit_dalam()); 
        if(akses.getpenilaian_awal_medis_ralan_penyakit_dalam()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisMata.setVisible(akses.getpenilaian_awal_medis_ralan_mata()); 
        if(akses.getpenilaian_awal_medis_ralan_mata()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisNeurologi.setVisible(akses.getpenilaian_awal_medis_ralan_neurologi()); 
        if(akses.getpenilaian_awal_medis_ralan_neurologi()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisOrthopedi.setVisible(akses.getpenilaian_awal_medis_ralan_orthopedi()); 
        if(akses.getpenilaian_awal_medis_ralan_orthopedi()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisBedah.setVisible(akses.getpenilaian_awal_medis_ralan_bedah()); 
        if(akses.getpenilaian_awal_medis_ralan_bedah()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPsikolog.setVisible(akses.getpenilaian_psikologi()); 
        if(akses.getpenilaian_psikologi()==true){
            tinggi=tinggi+24;
        }
//        BtnPemantauanPEWSAnak.setVisible(akses.getpemantauan_pews_anak()); 
//        if(akses.getpenilaian_psikologi()==true){
//            tinggi=tinggi+24;
//        }
        BtnPenilaianPreOperasi.setVisible(akses.getpenilaian_pre_operasi()); 
        if(akses.getpenilaian_pre_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPreAnestesi.setVisible(akses.getpenilaian_pre_anestesi()); 
        if(akses.getpenilaian_pre_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanRisikoJatuhDewasa.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_dewasa()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_dewasa()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanRisikoJatuhAnak.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_anak()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_anak()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisGeriatri.setVisible(akses.getpenilaian_awal_medis_ralan_geriatri());
        if(akses.getpenilaian_awal_medis_ralan_geriatri()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanGeriatri.setVisible(akses.getpenilaian_tambahan_pasien_geriatri()); 
        if(akses.getpenilaian_tambahan_pasien_geriatri()==true){
            tinggi=tinggi+24;
        }
        
        BtnSkriningNutrisiDewasa.setVisible(akses.getskrining_nutrisi_dewasa()); 
        if(akses.getskrining_nutrisi_dewasa()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningNutrisiLansia.setVisible(akses.getskrining_nutrisi_lansia()); 
        if(akses.getskrining_nutrisi_lansia()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningNutrisiAnak.setVisible(akses.getskrining_nutrisi_anak()); 
        if(akses.getskrining_nutrisi_anak()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningGiziLanjut.setVisible(akses.getskrining_gizi()); 
        if(akses.getskrining_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnAsuhanGizi.setVisible(akses.getasuhan_gizi());
        if(akses.getasuhan_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnMonitoringAsuhanGizi.setVisible(akses.getmonitoring_asuhan_gizi());
        if(akses.getmonitoring_asuhan_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanUSG.setVisible(akses.gethasil_pemeriksaan_usg()); 
        if(akses.gethasil_pemeriksaan_usg()==true){
            tinggi=tinggi+24;
        }
        BtnKonselingFarmasi.setVisible(akses.getkonseling_farmasi()); 
        if(akses.getkonseling_farmasi()==true){
            tinggi=tinggi+24;
        }
        BtnInformasiObat.setVisible(akses.getpelayanan_informasi_obat()); 
        if(akses.getpelayanan_informasi_obat()==true){
            tinggi=tinggi+24;
        }
        BtnTransferAntarRuang.setVisible(akses.gettransfer_pasien_antar_ruang()); 
        if(akses.gettransfer_pasien_antar_ruang()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanCekGDS.setVisible(akses.getcatatan_cek_gds()); 
        if(akses.getcatatan_cek_gds()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistPreOperasi.setVisible(akses.getchecklist_pre_operasi()); 
        if(akses.getchecklist_pre_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnSignInSebelumAnestesi.setVisible(akses.getsignin_sebelum_anestesi()); 
        if(akses.getsignin_sebelum_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnTimeOutSebelumInsisi.setVisible(akses.gettimeout_sebelum_insisi()); 
        if(akses.gettimeout_sebelum_insisi()==true){
            tinggi=tinggi+24;
        }
        BtnSignOutSebelumMenutupLuka.setVisible(akses.getsignout_sebelum_menutup_luka()); 
        if(akses.getsignout_sebelum_menutup_luka()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistPostOperasi.setVisible(akses.getchecklist_post_operasi()); 
        if(akses.getchecklist_post_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnRekonsiliasiObat.setVisible(akses.getrekonsiliasi_obat()); 
        if(akses.getrekonsiliasi_obat()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPasienTerminal.setVisible(akses.getpenilaian_pasien_terminal()); 
        if(akses.getpenilaian_pasien_terminal()==true){
            tinggi=tinggi+24;
        }
        BtnMonitoringReaksiTranfusi.setVisible(akses.getmonitoring_reaksi_tranfusi()); 
        if(akses.getmonitoring_reaksi_tranfusi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianKorbanKekerasan.setVisible(akses.getpenilaian_korban_kekerasan()); 
        if(akses.getpenilaian_korban_kekerasan()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanRisikoJatuhLansia.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_lansia()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_lansia()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPasienPenyakitMenular.setVisible(akses.getpenilaian_pasien_penyakit_menular()); 
        if(akses.getpenilaian_pasien_penyakit_menular()==true){
            tinggi=tinggi+24;
        }
        BtnEdukasiPasienKeluarga.setVisible(akses.getedukasi_pasien_keluarga_rj()); 
        if(akses.getedukasi_pasien_keluarga_rj()==true){
            tinggi=tinggi+24;
        }
//        BtnPemantauanPEWSDewasa.setVisible(akses.getpemantauan_pews_dewasa()); 
//        if(akses.getpemantauan_pews_dewasa()==true){
//            tinggi=tinggi+24;
//        }
        BtnPenilaianTambahanBunuhDiri.setVisible(akses.getpenilaian_tambahan_bunuh_diri()); 
        if(akses.getpenilaian_tambahan_bunuh_diri()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanPerilakuKekerasan.setVisible(akses.getpenilaian_tambahan_perilaku_kekerasan()); 
        if(akses.getpenilaian_tambahan_perilaku_kekerasan()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanMelarikanDiri.setVisible(akses.getpenilaian_tambahan_beresiko_melarikan_diri()); 
        if(akses.getpenilaian_tambahan_beresiko_melarikan_diri()==true){
            tinggi=tinggi+24;
        }
        FormMenu.setPreferredSize(new Dimension(195,(tinggi+500)));
        TCari.setPreferredSize(new Dimension(207,23));
        
        if(akses.getjml2()>=1){
            KodeDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KodeDokter.setText(akses.getkode());
//            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NamaDokter,KodeDokter.getText());
            Sequel.cariIsi("select nama from pegawai where nik=?", NamaDokter,KodeDokter.getText());
        }
      
    }

//    private void tampilPemeriksaan() {
//        Valid.tabelKosong(tabModePemeriksaan);
//        try{  
//            ps4=koneksi.prepareStatement("select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
//                    "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi, " +
//                    "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, " +
//                    "pemeriksaan_ralan.berat,pemeriksaan_ralan.spo2,pemeriksaan_ralan.gcs,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, " +
//                    "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.lingkar_perut,pemeriksaan_ralan.rtl,"+
//                    "pemeriksaan_ralan.penilaian,pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama,pegawai.jbtn "+
//                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
//                    "inner join pemeriksaan_ralan on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat "+
//                    "inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where  "+
//                    "pemeriksaan_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
//                    (TCari.getText().trim().equals("")?"":"and (pemeriksaan_ralan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
//                    "pemeriksaan_ralan.alergi like ? or pemeriksaan_ralan.keluhan like ? or pemeriksaan_ralan.penilaian like ? or "+
//                    "pemeriksaan_ralan.pemeriksaan like ? or pegawai.nama like ?) ")+"order by pemeriksaan_ralan.no_rawat,pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat desc"); 
//            try{
//                ps4.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
//                ps4.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
//                ps4.setString(3,"%"+TCariPasien.getText()+"%");
//                if(!TCari.getText().trim().equals("")){
//                    ps4.setString(4,"%"+TCari.getText().trim()+"%");
//                    ps4.setString(5,"%"+TCari.getText().trim()+"%");
//                    ps4.setString(6,"%"+TCari.getText().trim()+"%");
//                    ps4.setString(7,"%"+TCari.getText().trim()+"%");
//                    ps4.setString(8,"%"+TCari.getText().trim()+"%");
//                    ps4.setString(9,"%"+TCari.getText().trim()+"%");
//                    ps4.setString(10,"%"+TCari.getText().trim()+"%");
//                    ps4.setString(11,"%"+TCari.getText().trim()+"%");
//                }
//                rs=ps4.executeQuery();
//                while(rs.next()){
//                    tabModePemeriksaan.addRow(new Object[]{
//                        false,rs.getString(1),rs.getString(2),rs.getString(3),
//                        rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),
//                        rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),
//                        rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),
//                        rs.getString(16),rs.getString(17),rs.getString(18),rs.getString(19),
//                        rs.getString(20),rs.getString(21),rs.getString(22),rs.getString(23),
//                        rs.getString(24),rs.getString(25)
//                    });
//                }
//            } catch (Exception e) {
//                System.out.println("Notifikasi : "+e);
//            } finally{
//                if(rs!=null){
//                    rs.close();
//                }
//                if(ps4!=null){
//                    ps4.close();
//                }
//            }                  
//        }catch(Exception e){
//            System.out.println("Notifikasi : "+e);
//        }
//        LCount.setText(""+tabModePemeriksaan.getRowCount());
//    }
    
    private void tampilPemeriksaan() {
        Valid.tabelKosong(tabModePemeriksaan);
        try{  
            ps4=koneksi.prepareStatement("select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi, " +
                    "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, " +
                    "pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, " +
                    "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.imun_ke,"+
                    "pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,pemeriksaan_ralan.rpd,pemeriksaan_ralan.rpk,pemeriksaan_ralan.rpo,pemeriksaan_ralan.spo,pemeriksaan_ralan.operasi,pemeriksaan_ralan.nik,pegawai.nama,pemeriksaan_ralan.instruksi from pasien inner join reg_periksa inner join pemeriksaan_ralan "+
                    "on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pemeriksaan_ralan.nik=pegawai.nik where "+
                    "pemeriksaan_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ralan.no_rawat like ? or "+
                    "pemeriksaan_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "pemeriksaan_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or  "+
                    "pemeriksaan_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ralan.alergi like ? or "+
                    "pemeriksaan_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ralan.keluhan like ? or "+
                    "pemeriksaan_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ralan.penilaian like ? or "+
                    "pemeriksaan_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ralan.pemeriksaan like ? "+
                   "order by pemeriksaan_ralan.no_rawat desc"); 
            try{
                ps4.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(3,"%"+TCariPasien.getText()+"%");
                ps4.setString(4,"%"+TCari.getText().trim()+"%");
                ps4.setString(5,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(6,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(7,"%"+TCariPasien.getText()+"%");
                ps4.setString(8,"%"+TCari.getText().trim()+"%");
                ps4.setString(9,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(10,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(11,"%"+TCariPasien.getText()+"%");
                ps4.setString(12,"%"+TCari.getText().trim()+"%");
                ps4.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(15,"%"+TCariPasien.getText()+"%");
                ps4.setString(16,"%"+TCari.getText().trim()+"%");
                ps4.setString(17,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(18,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(19,"%"+TCariPasien.getText()+"%");
                ps4.setString(20,"%"+TCari.getText().trim()+"%");
                ps4.setString(21,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(22,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(23,"%"+TCariPasien.getText()+"%");
                ps4.setString(24,"%"+TCari.getText().trim()+"%");
                ps4.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(27,"%"+TCariPasien.getText()+"%");
                ps4.setString(28,"%"+TCari.getText().trim()+"%");
                rs=ps4.executeQuery();
                while(rs.next()){
                    tabModePemeriksaan.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),
                        rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),
                        rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),
                        rs.getString(16),rs.getString(17),rs.getString(18),rs.getString(19),
                        rs.getString(20),rs.getString(21),rs.getString(22),rs.getString(23),
                        rs.getString(24),rs.getString(25),rs.getString(26),rs.getString(27)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps4!=null){
                    ps4.close();
                }
            }                  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModePemeriksaan.getRowCount());
    }
    
    private void tampilPemeriksaanRM() {
        Valid.tabelKosong(tabModePemeriksaanRM);
        try{  
            ps4=koneksi.prepareStatement("select pemeriksaan_ralan_rehab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_ralan_rehab.tgl_perawatan,pemeriksaan_ralan_rehab.jam_rawat,pemeriksaan_ralan_rehab.kesadaran,pemeriksaan_ralan_rehab.anamnesa,pemeriksaan_ralan_rehab.pemeriksaanfisik, " +
                    "pemeriksaan_ralan_rehab.pemeriksaanpenunjang,pemeriksaan_ralan_rehab.anjuran,pemeriksaan_ralan_rehab.diagnosis, " +
                    "pemeriksaan_ralan_rehab.tatalaksana,pemeriksaan_ralan_rehab.evaluasi,pemeriksaan_ralan_rehab.suspek, pemeriksaan_ralan_rehab.ket_suspek, "+
                    "pemeriksaan_ralan_rehab.rpd,pemeriksaan_ralan_rehab.rpk,pemeriksaan_ralan_rehab.rpo,pemeriksaan_ralan_rehab.alergi,pemeriksaan_ralan_rehab.instruksi,pemeriksaan_ralan_rehab.operasi,pemeriksaan_ralan_rehab.nik,pegawai.nama from pasien inner join reg_periksa inner join pemeriksaan_ralan_rehab "+
                    "on pemeriksaan_ralan_rehab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pemeriksaan_ralan_rehab.nik=pegawai.nik where "+
                    "pemeriksaan_ralan_rehab.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ralan_rehab.no_rawat like ? or "+
                    "pemeriksaan_ralan_rehab.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "pemeriksaan_ralan_rehab.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or  "+
                    "pemeriksaan_ralan_rehab.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ralan_rehab.alergi like ? or "+
                    "pemeriksaan_ralan_rehab.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ralan_rehab.anamnesa like ? or "+
                    "pemeriksaan_ralan_rehab.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ralan_rehab.pemeriksaanfisik like ? or "+
                    "pemeriksaan_ralan_rehab.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ralan_rehab.diagnosis like ? "+
                   "order by pemeriksaan_ralan_rehab.no_rawat desc"); 
            try{
                ps4.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(3,"%"+TCariPasien.getText()+"%");
                ps4.setString(4,"%"+TCari.getText().trim()+"%");
                ps4.setString(5,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(6,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(7,"%"+TCariPasien.getText()+"%");
                ps4.setString(8,"%"+TCari.getText().trim()+"%");
                ps4.setString(9,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(10,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(11,"%"+TCariPasien.getText()+"%");
                ps4.setString(12,"%"+TCari.getText().trim()+"%");
                ps4.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(15,"%"+TCariPasien.getText()+"%");
                ps4.setString(16,"%"+TCari.getText().trim()+"%");
                ps4.setString(17,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(18,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(19,"%"+TCariPasien.getText()+"%");
                ps4.setString(20,"%"+TCari.getText().trim()+"%");
                ps4.setString(21,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(22,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(23,"%"+TCariPasien.getText()+"%");
                ps4.setString(24,"%"+TCari.getText().trim()+"%");
                ps4.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(27,"%"+TCariPasien.getText()+"%");
                ps4.setString(28,"%"+TCari.getText().trim()+"%");
                rs=ps4.executeQuery();
                while(rs.next()){
                    tabModePemeriksaanRM.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),
                        rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),
                        rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),
                        rs.getString(16),rs.getString(17),rs.getString(18),rs.getString(19),
                        rs.getString(20),rs.getString(21),rs.getString(22),rs.getString(23)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps4!=null){
                    ps4.close();
                }
            }                  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModePemeriksaanRM.getRowCount());
    }
    
    private void tampilCatatan() {
        Valid.tabelKosong(TabModeCatatan);
        try{  
            ps4=koneksi.prepareStatement("select catatan_perawatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "catatan_perawatan.tanggal,catatan_perawatan.jam,catatan_perawatan.kd_dokter,dokter.nm_dokter,"+
                    "catatan_perawatan.catatan from pasien inner join reg_periksa inner join catatan_perawatan inner join dokter "+
                    "on catatan_perawatan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and catatan_perawatan.kd_dokter=dokter.kd_dokter where  "+
                    "catatan_perawatan.tanggal between ? and ? and reg_periksa.no_rkm_medis like ? "+
                    (TCari.getText().trim().equals("")?"":"and (catatan_perawatan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or  "+
                    "catatan_perawatan.catatan like ? or catatan_perawatan.kd_dokter like ? or dokter.nm_dokter like ?) ")+
                    "order by catatan_perawatan.no_rawat,catatan_perawatan.tanggal,catatan_perawatan.jam desc"); 
            try{
                ps4.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps4.setString(4,"%"+TCari.getText().trim()+"%");
                    ps4.setString(5,"%"+TCari.getText().trim()+"%");
                    ps4.setString(6,"%"+TCari.getText().trim()+"%");
                    ps4.setString(7,"%"+TCari.getText().trim()+"%");
                    ps4.setString(8,"%"+TCari.getText().trim()+"%");
                    ps4.setString(9,"%"+TCari.getText().trim()+"%");
                }
                rs=ps4.executeQuery();
                while(rs.next()){
                    TabModeCatatan.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Catatan : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps4!=null){
                    ps4.close();
                }
            }                  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+TabModeCatatan.getRowCount());
    }

    private void getDataPemeriksaan() {
//        if(tbPemeriksaan.getSelectedRow()!= -1){
//            TNoRw.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1).toString());
//            TNoRM.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),2).toString());
//            TPasien.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),3).toString());             
//            TSuhu.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),6).toString()); 
//            TTensi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),7).toString()); 
//            TNadi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),8).toString()); 
//            TRespirasi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),9).toString()); 
//            TTinggi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),10).toString()); 
//            TBerat.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),11).toString());  
//            SpO2.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),12).toString()); 
//            TGCS.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),13).toString());   
//            cmbKesadaran.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),14).toString()); 
//            TKeluhan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),15).toString()); 
//            TPemeriksaan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),16).toString()); 
//            TAlergi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),17).toString()); 
//            LingkarPerut.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),18).toString()); 
//            TindakLanjut.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),19).toString()); 
//            TPenilaian.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),20).toString()); 
//            TInstruksi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),21).toString()); 
//            TEvaluasi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),22).toString()); 
//            cmbJam.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(0,2));
//            cmbMnt.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(3,5));
//            cmbDtk.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(6,8));
//            Valid.SetTgl(DTPTgl,tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4).toString());
//        }

              if(tbPemeriksaan.getSelectedRow()!= -1){
            TNoRw.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1).toString());
            TNoRM.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),2).toString());
            TPasien.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),3).toString());             
            TSuhu.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),6).toString()); 
            TTensi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),7).toString()); 
            TNadi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),8).toString()); 
            TRespirasi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),9).toString()); 
            TTinggi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),10).toString()); 
            TBerat.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),11).toString());  
            TGCS.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),12).toString());   
            cmbKesadaran.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),13).toString()); 
            TKeluhan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),14).toString()); 
            TPemeriksaan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),15).toString()); 
            TAlergi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),16).toString()); 
            cmbImun.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),17).toString()); 
            TindakLanjut.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),18).toString()); 
            TPenilaian.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),19).toString());
            TRpd.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),20).toString());
            TRpk.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),21).toString());
            TRpo.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),22).toString());
            TSpo2.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),23).toString());
            Operasi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),24).toString());
//            KodeDokter.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),25).toString());
//            NamaDokter.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),26).toString());
            Instruksi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),27).toString());
            cmbJam.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(6,8));
            Valid.SetTgl(DTPTgl,tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4).toString());
            if(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),25).toString().equals(akses.getkode()))
            {
               BtnHapus.setEnabled(true); 
               BtnEdit.setEnabled(true); 
            }else
            {
               BtnHapus.setEnabled(false); 
               BtnEdit.setEnabled(false); 
            }
        }
    }
    
    private void getDataPemeriksaanRM() {
            if(tbPemeriksaanRM.getSelectedRow()!= -1){
            TNoRw.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),1).toString());
            TNoRM.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),2).toString());
            TPasien.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),3).toString());            
            Valid.SetTgl(DTPTgl,tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),4).toString());
            cmbJam.setSelectedItem(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),5).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),5).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),5).toString().substring(6,8));
            cmbKesadaran.setSelectedItem(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),6).toString()); 
            Anamnesa.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),7).toString()); 
            PemeriksaanFisik.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),8).toString());
            PemeriksaanPenunjang.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),9).toString());
            Anjuran.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),10).toString());
            Diagnosis.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),11).toString());
            Tatalaksana.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),12).toString());
            Evaluasi.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),13).toString());
            Suspek.setSelectedItem(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),14).toString());
            KetSuspek.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),15).toString());
            TRpd1.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),16).toString());
            TRpk1.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),17).toString());
            TRpo1.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),18).toString());
            TAlergi1.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),19).toString());
            Instruksi1.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),20).toString());
            Operasi1.setText(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),21).toString());
            if(tbPemeriksaanRM.getValueAt(tbPemeriksaanRM.getSelectedRow(),22).toString().equals(akses.getkode()))
            {
               BtnHapus.setEnabled(true); 
               BtnEdit.setEnabled(true); 
            }else
            {
               BtnHapus.setEnabled(false); 
               BtnEdit.setEnabled(false); 
            }
        }
    }
    
    private void getDataCatatan() {
        if(tbCatatan.getSelectedRow()!= -1){
            TNoRw.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),1).toString());
            TNoRM.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),2).toString());
            TPasien.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),3).toString()); 
            KdDok3.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),6).toString());  
            TDokter3.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),7).toString());
            Catatan.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),8).toString());             
            cmbJam.setSelectedItem(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),5).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),5).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),5).toString().substring(6,8));
            Valid.SetTgl(DTPTgl,tbCatatan.getValueAt(tbCatatan.getSelectedRow(),4).toString());
        }
    }
    
    private void tampilPemeriksaanObstetri() {
        Valid.tabelKosong(tabModeObstetri);
        try{
            ps5=koneksi.prepareStatement("select pemeriksaan_obstetri_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat,pemeriksaan_obstetri_ralan.tinggi_uteri,pemeriksaan_obstetri_ralan.janin,pemeriksaan_obstetri_ralan.letak, " +
                    "pemeriksaan_obstetri_ralan.panggul,pemeriksaan_obstetri_ralan.denyut,pemeriksaan_obstetri_ralan.kontraksi, " +
                    "pemeriksaan_obstetri_ralan.kualitas_mnt,pemeriksaan_obstetri_ralan.kualitas_dtk,pemeriksaan_obstetri_ralan.fluksus,pemeriksaan_obstetri_ralan.albus, " +
                    "pemeriksaan_obstetri_ralan.vulva,pemeriksaan_obstetri_ralan.portio,pemeriksaan_obstetri_ralan.dalam, pemeriksaan_obstetri_ralan.tebal, pemeriksaan_obstetri_ralan.arah, pemeriksaan_obstetri_ralan.pembukaan," +
                    "pemeriksaan_obstetri_ralan.penurunan, pemeriksaan_obstetri_ralan.denominator, pemeriksaan_obstetri_ralan.ketuban, pemeriksaan_obstetri_ralan.feto, pemeriksaan_obstetri_ralan.kd_dokter,pegawai.nama " +
                    "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ralan "+
                    "on pemeriksaan_obstetri_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on pemeriksaan_obstetri_ralan.kd_dokter=pegawai.nik where  "+
                    "pemeriksaan_obstetri_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                    (TCari.getText().trim().equals("")?"":"and (pemeriksaan_obstetri_ralan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or  "+
                    "pemeriksaan_obstetri_ralan.tinggi_uteri like ? or pemeriksaan_obstetri_ralan.janin like ? or pemeriksaan_obstetri_ralan.letak like ?) ")+
                    "order by pemeriksaan_obstetri_ralan.no_rawat,pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat desc");
            try {
                ps5.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps5.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps5.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps5.setString(4,"%"+TCari.getText().trim()+"%");
                    ps5.setString(5,"%"+TCari.getText().trim()+"%");
                    ps5.setString(6,"%"+TCari.getText().trim()+"%");
                    ps5.setString(7,"%"+TCari.getText().trim()+"%");
                    ps5.setString(8,"%"+TCari.getText().trim()+"%");
                    ps5.setString(9,"%"+TCari.getText().trim()+"%");
                }
                rs=ps5.executeQuery();
                while(rs.next()) {
                    tabModeObstetri.addRow(new Object[] {
                        false, rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("tgl_perawatan"),rs.getString("jam_rawat"),rs.getString("tinggi_uteri"),
                        rs.getString("janin"),rs.getString("letak"),rs.getString("panggul"),
                        rs.getString("denyut"),rs.getString("kontraksi"),rs.getString("kualitas_mnt"),
                        rs.getString("kualitas_dtk"),rs.getString("fluksus"),rs.getString("albus"),
                        rs.getString("vulva"),rs.getString("portio"),rs.getString("dalam"),
                        rs.getString("tebal"),rs.getString("arah"),rs.getString("pembukaan"),
                        rs.getString("penurunan"),rs.getString("denominator"),rs.getString("ketuban"),
                        rs.getString("feto"),rs.getString("kd_dokter"),rs.getString("nama")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);    
            } finally {
                if(rs!=null) {
                    rs.close();
                }
                if(ps5!=null) {
                    ps5.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi :" +e);
        }
        LCount.setText(""+tabModeObstetri.getRowCount());
    }     
        
    private void getDataPemeriksaanObstetri() {
        if(tbPemeriksaanObstetri.getSelectedRow()!= -1) {
            TNoRw.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),1).toString());
            TNoRM.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),2).toString());
            TPasien.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),3).toString());
            Valid.SetTgl(DTPTgl,tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),4).toString());
            cmbJam.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5).toString().substring(6,8));
            TTinggi_uteri.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),6).toString());
            cmbJanin.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),7).toString());
            TLetak.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),8).toString());
            cmbPanggul.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),9).toString());
            TDenyut.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),10).toString());
            cmbKontraksi.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),11).toString());
            TKualitas_mnt.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),12).toString());
            TKualitas_dtk.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),13).toString());
            cmbFluksus.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),14).toString());
            cmbAlbus.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),15).toString());
            TVulva.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),16).toString());
            TPortio.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),17).toString());
            cmbDalam.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),18).toString());
            TTebal.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),19).toString());
            cmbArah.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),20).toString());
            TPembukaan.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),21).toString());
            TPenurunan.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),22).toString());
            TDenominator.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),23).toString());
            cmbKetuban.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),24).toString());
            cmbFeto.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),25).toString());
            KodeDokter.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),26).toString());
            NamaDokter.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),27).toString());
        }
    }
    
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
                    DTPTgl.setDate(new Date());
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
    
    public void setNoRm(String norwt,Date tgl1,Date tgl2,String kodedokter, String namadokter) {
        TNoRw.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();  
        ChkInput.setSelected(true);
        isForm(); 
        ChkInput1.setSelected(true);
        isForm2(); 
        ChkInput2.setSelected(true);
        isForm3(); 
        ChkInput3.setSelected(true);
        isForm4();
        ChkInput4.setSelected(true);
        isForm5();
        KdDok.setText(kodedokter);
        KdDok2.setText(kodedokter);
        KdDok3.setText(kodedokter);
        TDokter.setText(namadokter);
        TDokter2.setText(namadokter); 
        TDokter3.setText(namadokter); 
    }
        
    public void SetPoli(String KodePoli){
        this.kode_poli=KodePoli;
    }
    
    public void SetPj(String KodePj){
        this.kd_pj=KodePj;
    }
    
    private void isForm2(){
        if(ChkInput1.isSelected()==true){
            ChkInput1.setVisible(false);
            PanelInput1.setPreferredSize(new Dimension(WIDTH,190));
            panelGlass13.setVisible(true);      
            ChkInput1.setVisible(true);
        }else if(ChkInput1.isSelected()==false){           
            ChkInput1.setVisible(false);            
            PanelInput1.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass13.setVisible(false);      
            ChkInput1.setVisible(true);
        }
    }
    
    private void tampilPemeriksaanGinekologi() {
        Valid.tabelKosong(tabModeGinekologi);
        try{
            ps6=koneksi.prepareStatement("select pemeriksaan_ginekologi_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat,pemeriksaan_ginekologi_ralan.inspeksi,pemeriksaan_ginekologi_ralan.inspeksi_vulva,pemeriksaan_ginekologi_ralan.inspekulo_gine, " +
                    "pemeriksaan_ginekologi_ralan.fluxus_gine,pemeriksaan_ginekologi_ralan.fluor_gine,pemeriksaan_ginekologi_ralan.vulva_inspekulo, " +
                    "pemeriksaan_ginekologi_ralan.portio_inspekulo,pemeriksaan_ginekologi_ralan.sondage,pemeriksaan_ginekologi_ralan.portio_dalam,pemeriksaan_ginekologi_ralan.bentuk, " +
                    "pemeriksaan_ginekologi_ralan.cavum_uteri,pemeriksaan_ginekologi_ralan.mobilitas,pemeriksaan_ginekologi_ralan.ukuran, pemeriksaan_ginekologi_ralan.nyeri_tekan, pemeriksaan_ginekologi_ralan.adnexa_kanan, pemeriksaan_ginekologi_ralan.adnexa_kiri," +
                    "pemeriksaan_ginekologi_ralan.cavum_douglas,pemeriksaan_ginekologi_ralan.kd_dokter,pegawai.nama " +
                    "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ralan "+
                    "on pemeriksaan_ginekologi_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on pemeriksaan_ginekologi_ralan.kd_dokter=pegawai.nik where  "+
                    "pemeriksaan_ginekologi_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                    (TCari.getText().trim().equals("")?"":"and (pemeriksaan_ginekologi_ralan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or  pemeriksaan_ginekologi_ralan.inspeksi like ? or pemeriksaan_ginekologi_ralan.inspeksi_vulva like ? or "+
                    "pemeriksaan_ginekologi_ralan.inspekulo_gine like ?) ")+
                    "order by pemeriksaan_ginekologi_ralan.no_rawat,pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat desc");
            try {
                ps6.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps6.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps6.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps6.setString(4,"%"+TCari.getText().trim()+"%");
                    ps6.setString(5,"%"+TCari.getText().trim()+"%");
                    ps6.setString(6,"%"+TCari.getText().trim()+"%");
                    ps6.setString(7,"%"+TCari.getText().trim()+"%");
                    ps6.setString(8,"%"+TCari.getText().trim()+"%");
                    ps6.setString(9,"%"+TCari.getText().trim()+"%");
                }
                rs=ps6.executeQuery();
                while(rs.next()) {
                    tabModeGinekologi.addRow(new Object[] {
                        false, rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("tgl_perawatan"),rs.getString("jam_rawat"),rs.getString("inspeksi"),
                        rs.getString("inspeksi_vulva"),rs.getString("inspekulo_gine"),rs.getString("fluxus_gine"),
                        rs.getString("fluor_gine"),rs.getString("vulva_inspekulo"),rs.getString("portio_inspekulo"),
                        rs.getString("sondage"),rs.getString("portio_dalam"),rs.getString("bentuk"),
                        rs.getString("cavum_uteri"),rs.getString("mobilitas"),rs.getString("ukuran"),
                        rs.getString("nyeri_tekan"),rs.getString("adnexa_kanan"),rs.getString("adnexa_kiri"),
                        rs.getString("cavum_douglas"),rs.getString("kd_dokter"),rs.getString("nama")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);    
            } finally {
                if(rs!=null) {
                    rs.close();
                }
                if(ps5!=null) {
                    ps5.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi :" +e);
        }
        LCount.setText(""+tabModeGinekologi.getRowCount());
    }
    
    private void getDataPemeriksaanGinekologi() {
         if(tbPemeriksaanGinekologi.getSelectedRow()!= -1) {
            TNoRw.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),1).toString());
            TNoRM.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),2).toString());
            TPasien.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),3).toString());
            Valid.SetTgl(DTPTgl,tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),4).toString());
            cmbJam.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5).toString().substring(6,8));
            TInspeksi.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),6).toString());
            TInspeksiVulva.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),7).toString());
            TInspekuloGine.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),8).toString());
            cmbFluxusGine.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),9).toString());
            cmbFluorGine.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),10).toString());
            TVulvaInspekulo.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),11).toString());
            TPortioInspekulo.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),12).toString());
            TSondage.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),13).toString());
            TPortioDalam.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),14).toString());
            TBentuk.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),15).toString());
            TCavumUteri.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),16).toString());
            cmbMobilitas.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),17).toString());
            TUkuran.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),18).toString());
            cmbNyeriTekan.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),19).toString());
            TAdnexaKanan.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),20).toString());
            TAdnexaKiri.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),21).toString());
            TCavumDouglas.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),22).toString());
            KodeDokter.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),23).toString());
            NamaDokter.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),24).toString());
        }
    }
    
    private void isForm3(){
        if(ChkInput2.isSelected()==true){
            ChkInput2.setVisible(false);
            PanelInput2.setPreferredSize(new Dimension(WIDTH,260));
            panelGlass14.setVisible(true);      
            ChkInput2.setVisible(true);
        }else if(ChkInput2.isSelected()==false){           
            ChkInput2.setVisible(false);            
            PanelInput2.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass14.setVisible(false);      
            ChkInput2.setVisible(true);
        }
    }
    
    private void tampilTindakanDr() {
        try{     
            jml=0;
            for(i=0;i<TabModeTindakan.getRowCount();i++){
                if(TabModeTindakan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            kategori=null;
            kategori=new String[jml];
            totaltnd=null;
            totaltnd=new double[jml];  
            bagianrs=null;
            bagianrs=new double[jml];
            bhp=null;
            bhp=new double[jml];
            jmdokter=null;
            jmdokter=new double[jml];
            jmperawat=null;
            jmperawat=new double[jml];
            kso=null;
            kso=new double[jml];
            menejemen=null;
            menejemen=new double[jml];

            index=0;        
            for(i=0;i<TabModeTindakan.getRowCount();i++){
                if(TabModeTindakan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=TabModeTindakan.getValueAt(i,1).toString();
                    nama[index]=TabModeTindakan.getValueAt(i,2).toString();
                    kategori[index]=TabModeTindakan.getValueAt(i,3).toString();
                    totaltnd[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,4).toString());
                    bagianrs[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,5).toString());
                    bhp[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,6).toString());
                    jmdokter[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,7).toString());
                    jmperawat[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,8).toString());  
                    kso[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,9).toString());
                    menejemen[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,10).toString());  
                    index++;
                }
            }       

            Valid.tabelKosong(TabModeTindakan);

            for(i=0;i<jml;i++){
                TabModeTindakan.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]
                });
            }
            
            if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    "nd jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");        
            }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");     
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            }
            
            try {
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,kode_poli.trim());
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,kd_pj.trim());
                    pstindakan.setString(5,kode_poli.trim());
                    pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(7,kd_pj.trim());
                    pstindakan.setString(8,kode_poli.trim());
                    pstindakan.setString(9,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,kd_pj.trim());
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(5,kd_pj.trim());
                    pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,kode_poli.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,kode_poli.trim());
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(5,kode_poli.trim());
                    pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }
                
                while(rstindakan.next()){
                    TabModeTindakan.addRow(new Object[] {
                        false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                        rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("material"),
                        rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                        rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                        rstindakan.getDouble("menejemen")
                    });    
                }                   
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rstindakan != null){
                    rstindakan.close();
                }
                if(pstindakan != null){
                    pstindakan.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+TabModeTindakan.getRowCount());
    }
    
    private void tampilTindakanPr() {
        try{     
            jml=0;
            for(i=0;i<TabModeTindakan2.getRowCount();i++){
                if(TabModeTindakan2.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            kategori=null;
            kategori=new String[jml];
            totaltnd=null;
            totaltnd=new double[jml];  
            bagianrs=null;
            bagianrs=new double[jml];
            bhp=null;
            bhp=new double[jml];
            jmdokter=null;
            jmdokter=new double[jml];
            jmperawat=null;
            jmperawat=new double[jml];
            kso=null;
            kso=new double[jml];
            menejemen=null;
            menejemen=new double[jml];

            index=0;        
            for(i=0;i<TabModeTindakan2.getRowCount();i++){
                if(TabModeTindakan2.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=TabModeTindakan2.getValueAt(i,1).toString();
                    nama[index]=TabModeTindakan2.getValueAt(i,2).toString();
                    kategori[index]=TabModeTindakan2.getValueAt(i,3).toString();
                    totaltnd[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,4).toString());
                    bagianrs[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,5).toString());
                    bhp[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,6).toString());
                    jmdokter[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,7).toString());
                    jmperawat[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,8).toString());  
                    kso[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,9).toString());
                    menejemen[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,10).toString());  
                    index++;
                }
            }       

            Valid.tabelKosong(TabModeTindakan2);

            for(i=0;i<jml;i++){
                TabModeTindakan2.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]
                });
            }
            
            if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");        
            }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");     
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            }
            
            try {
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,kode_poli.trim());
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,kd_pj.trim());
                    pstindakan.setString(5,kode_poli.trim());
                    pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(7,kd_pj.trim());
                    pstindakan.setString(8,kode_poli.trim());
                    pstindakan.setString(9,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,kd_pj.trim());
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(5,kd_pj.trim());
                    pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,kode_poli.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,kode_poli.trim());
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(5,kode_poli.trim());
                    pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }
                
                while(rstindakan.next()){
                    TabModeTindakan2.addRow(new Object[] {
                        false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                        rstindakan.getDouble("total_byrpr"),rstindakan.getDouble("material"),
                        rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                        rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                        rstindakan.getDouble("menejemen")
                    });        
                }                      
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rstindakan != null){
                    rstindakan.close();
                }
                if(pstindakan != null){
                    pstindakan.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+TabModeTindakan2.getRowCount());
    }
    
    private void tampilTindakanDrPr() {
        try{     
            jml=0;
            for(i=0;i<TabModeTindakan3.getRowCount();i++){
                if(TabModeTindakan3.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            kategori=null;
            kategori=new String[jml];
            totaltnd=null;
            totaltnd=new double[jml];  
            bagianrs=null;
            bagianrs=new double[jml];
            bhp=null;
            bhp=new double[jml];
            jmdokter=null;
            jmdokter=new double[jml];
            jmperawat=null;
            jmperawat=new double[jml];
            kso=null;
            kso=new double[jml];
            menejemen=null;
            menejemen=new double[jml];

            index=0;        
            for(i=0;i<TabModeTindakan3.getRowCount();i++){
                if(TabModeTindakan3.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=TabModeTindakan3.getValueAt(i,1).toString();
                    nama[index]=TabModeTindakan3.getValueAt(i,2).toString();
                    kategori[index]=TabModeTindakan3.getValueAt(i,3).toString();
                    totaltnd[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,4).toString());
                    bagianrs[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,5).toString());
                    bhp[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,6).toString());
                    jmdokter[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,7).toString());
                    jmperawat[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,8).toString());  
                    kso[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,9).toString());
                    menejemen[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,10).toString());  
                    index++;
                }
            }       

            Valid.tabelKosong(TabModeTindakan3);

            for(i=0;i<jml;i++){
                TabModeTindakan3.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]
                });
            }
            
            if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");        
            }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");     
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            }
            
            try {
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,kode_poli.trim());
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,kd_pj.trim());
                    pstindakan.setString(5,kode_poli.trim());
                    pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(7,kd_pj.trim());
                    pstindakan.setString(8,kode_poli.trim());
                    pstindakan.setString(9,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,kd_pj.trim());
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(5,kd_pj.trim());
                    pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,kode_poli.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,kode_poli.trim());
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(5,kode_poli.trim());
                    pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }
                
                while(rstindakan.next()){
                    TabModeTindakan3.addRow(new Object[] {
                        false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                        rstindakan.getDouble("total_byrdrpr"),rstindakan.getDouble("material"),
                        rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                        rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                        rstindakan.getDouble("menejemen")
                    });    
                }   
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rstindakan != null){
                    rstindakan.close();
                }
                if(pstindakan != null){
                    pstindakan.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+TabModeTindakan3.getRowCount());
    }
    
    private void TampilkanData(){
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampilkanPenangananDokter();
                break;
            case 1:
                tampilkanPenangananPetugas();
                break;
            case 2:
                tampilkanPenangananDokterPetugas();
                break;
            case 3:
                tampilPemeriksaan();
                break;
            case 4:
                tampilPemeriksaanRM();
                break;
            case 5:
                tampilPemeriksaanObstetri();
                break;
            case 6:
                tampilPemeriksaanGinekologi();
                break;
            case 7:
                if(akses.getdiagnosa_pasien()==true){
                    panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""), Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Ralan",TCari.getText().trim());
                    panelDiagnosa1.pilihTab();
                    LCount.setText(panelDiagnosa1.getRecord()+"");
                }  
                break;
            case 8:
                if(akses.getcatatan_perawatan()==true){
                    tampilCatatan();
                }  
                break;
            default:
                break;
        }
    }

    private void tampilkanPenangananDokter() {
        if(TabRawatTindakanDokter.getSelectedIndex()==0){
            tampilTindakanDr();
        }else if(TabRawatTindakanDokter.getSelectedIndex()==1){
            tampilDr();
        }
    }
    
    private void SimpanPenangananDokter(){        
        try {
            ChkJln.setSelected(false);
            Sequel.AutoComitFalse();
            sukses=true;
            ttljmdokter=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
            for(i=0;i<tbTindakan.getRowCount();i++){ 
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){  
                    if(Sequel.menyimpantf("rawat_jl_dr","?,?,?,?,?,?,?,?,?,?,?,'Belum'","Tindakan",11,new String[]{
                        TNoRw.getText(),tbTindakan.getValueAt(i,1).toString(),KdDok.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbTindakan.getValueAt(i,5).toString(),
                        tbTindakan.getValueAt(i,6).toString(),tbTindakan.getValueAt(i,7).toString(),tbTindakan.getValueAt(i,9).toString(),
                        tbTindakan.getValueAt(i,10).toString(),tbTindakan.getValueAt(i,4).toString()
                    })==true){
                        ttljmdokter=ttljmdokter+Double.parseDouble(tbTindakan.getValueAt(i,7).toString());
                        ttlkso=ttlkso+Double.parseDouble(tbTindakan.getValueAt(i,9).toString());
                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbTindakan.getValueAt(i,4).toString());
                        ttljasasarana=ttljasasarana+Double.parseDouble(tbTindakan.getValueAt(i,5).toString());
                        ttlbhp=ttlbhp+Double.parseDouble(tbTindakan.getValueAt(i,6).toString());
                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbTindakan.getValueAt(i,10).toString());
                    }else{
                        sukses=false;
                    }
                }                           
            }
            if(sukses==true){
                Sequel.queryu("delete from tampjurnal");    
                if(ttlpendapatan>0){
                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");    
                    Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'");                             
                }
                if(ttljmdokter>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");                               
                }
                if(ttlkso>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");                              
                }
                if(ttljasasarana>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");                              
                }
                if(ttlbhp>0){
                    Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");      
                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");                           
                }
                if(ttlmenejemen>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");                            
                }
                sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+", DIPOSTING OLEH "+akses.getkode());                                                
            }
            
            if(sukses==true){
                Sequel.Commit();
                for(i=0;i<tbTindakan.getRowCount();i++){ 
                    tbTindakan.setValueAt(false,i,0);
                }
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            ChkJln.setSelected(true);
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    private void SimpanPenangananPetugas(){
        try {
            ChkJln.setSelected(false);
            Sequel.AutoComitFalse();
            sukses=true;
            ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
            for(i=0;i<tbTindakan2.getRowCount();i++){ 
                if(tbTindakan2.getValueAt(i,0).toString().equals("true")){  
                    if(Sequel.menyimpantf("rawat_jl_pr","?,?,?,?,?,?,?,?,?,?,?,'Belum'","Tindakan",11,new String[]{
                        TNoRw.getText(),tbTindakan2.getValueAt(i,1).toString(),kdptg.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbTindakan2.getValueAt(i,5).toString(),
                        tbTindakan2.getValueAt(i,6).toString(),tbTindakan2.getValueAt(i,8).toString(),tbTindakan2.getValueAt(i,9).toString(),
                        tbTindakan2.getValueAt(i,10).toString(),tbTindakan2.getValueAt(i,4).toString()
                    })==true){
                        ttljmperawat=ttljmperawat+Double.parseDouble(tbTindakan2.getValueAt(i,8).toString());
                        ttlkso=ttlkso+Double.parseDouble(tbTindakan2.getValueAt(i,9).toString());
                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbTindakan2.getValueAt(i,4).toString());
                        ttljasasarana=ttljasasarana+Double.parseDouble(tbTindakan2.getValueAt(i,5).toString());
                        ttlbhp=ttlbhp+Double.parseDouble(tbTindakan2.getValueAt(i,6).toString());
                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbTindakan2.getValueAt(i,10).toString());
                    }else{
                        sukses=false;
                    }
                }                           
            }
            if(sukses==true){
                Sequel.queryu("delete from tampjurnal");    
                if(ttlpendapatan>0){
                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");    
                    Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'");                             
                }
                if(ttljmperawat>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");                             
                }
                if(ttlkso>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");                              
                }
                if(ttljasasarana>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");                              
                }
                if(ttlbhp>0){
                    Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");      
                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");                           
                }
                if(ttlmenejemen>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");                            
                }
                sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+", DIPOSTING OLEH "+akses.getkode());                                                
            }
            
            if(sukses==true){
                Sequel.Commit();
                for(i=0;i<tbTindakan2.getRowCount();i++){ 
                    tbTindakan2.setValueAt(false,i,0);
                }
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            ChkJln.setSelected(true);
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    private void SimpanPenangananDokterPetugas(){        
        try {
            ChkJln.setSelected(false);
            Sequel.AutoComitFalse();
            sukses=true;
            ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
            for(i=0;i<tbTindakan3.getRowCount();i++){ 
                if(tbTindakan3.getValueAt(i,0).toString().equals("true")){  
                    if(Sequel.menyimpantf("rawat_jl_drpr","?,?,?,?,?,?,?,?,?,?,?,?,?,'Belum'","Tindakan",13,new String[]{
                        TNoRw.getText(),tbTindakan3.getValueAt(i,1).toString(),KdDok2.getText(),kdptg2.getText(),
                        Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        tbTindakan3.getValueAt(i,5).toString(),tbTindakan3.getValueAt(i,6).toString(),tbTindakan3.getValueAt(i,7).toString(),
                        tbTindakan3.getValueAt(i,8).toString(),tbTindakan3.getValueAt(i,9).toString(),tbTindakan3.getValueAt(i,10).toString(),
                        tbTindakan3.getValueAt(i,4).toString()
                    })==true){
                        ttljmdokter=ttljmdokter+Double.parseDouble(tbTindakan3.getValueAt(i,7).toString());
                        ttljmperawat=ttljmperawat+Double.parseDouble(tbTindakan3.getValueAt(i,8).toString());
                        ttlkso=ttlkso+Double.parseDouble(tbTindakan3.getValueAt(i,9).toString());
                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbTindakan3.getValueAt(i,4).toString());
                        ttljasasarana=ttljasasarana+Double.parseDouble(tbTindakan3.getValueAt(i,5).toString());
                        ttlbhp=ttlbhp+Double.parseDouble(tbTindakan3.getValueAt(i,6).toString());
                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbTindakan3.getValueAt(i,10).toString());
                    }else{
                        sukses=false;
                    }
                }                           
            }
            if(sukses==true){
                Sequel.queryu("delete from tampjurnal");    
                if(ttlpendapatan>0){
                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");    
                    Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'");                             
                }
                if(ttljmdokter>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");                               
                }
                if(ttljmperawat>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");                             
                }
                if(ttlkso>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");                              
                }
                if(ttljasasarana>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");                              
                }
                if(ttlbhp>0){
                    Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");      
                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");                           
                }
                if(ttlmenejemen>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");                            
                }
                sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+", DIPOSTING OLEH "+akses.getkode());                                                
            }
            
            if(sukses==true){
                Sequel.Commit();
                for(i=0;i<tbTindakan3.getRowCount();i++){ 
                    tbTindakan3.setValueAt(false,i,0);
                }
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            ChkJln.setSelected(true);
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    private void tampilkanPenangananPetugas() {
        if(TabRawatTindakanPetugas.getSelectedIndex()==0){
            tampilTindakanPr();
        }else if(TabRawatTindakanPetugas.getSelectedIndex()==1){
            tampilPr();
        }
    }

    private void tampilkanPenangananDokterPetugas() {
        if(TabRawatTindakanDokterPetugas.getSelectedIndex()==0){
            tampilTindakanDrPr();
        }else if(TabRawatTindakanDokterPetugas.getSelectedIndex()==1){
            tampilDrPr();
        }
    }

    private void inputObat() {
        DlgCariObat dlgobt=new DlgCariObat(null,false);
        dlgobt.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem());
        dlgobt.isCek();
        dlgobt.setDokter(KdDok.getText(),TDokter.getText());
        dlgobt.tampilobat();
        dlgobt.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dlgobt.setLocationRelativeTo(internalFrame1);
        dlgobt.setVisible(true);
    }

    private void inputResep() {
        DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
        resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),
                cmbDtk.getSelectedItem().toString(),KdDok.getText(),TDokter.getText(),"ralan");
        resep.isCek();
        resep.tampilobat();
        resep.setVisible(true);
        resep.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
               isRawat();
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
    }

    private void inputKamar() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        akses.setstatus(true);
        DlgKamarInap dlgki=new DlgKamarInap(null,false);
        dlgki.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dlgki.setLocationRelativeTo(internalFrame1);
        dlgki.emptTeks();
        dlgki.isCek();
        dlgki.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());  
        dlgki.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void inputTemplate(){
        if(dokter.tampil3(KodeDokter.getText()).equals("")){
            JOptionPane.showMessageDialog(null,"Template pemeriksaan hanya untuk dokter...!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            MasterCariTemplatePemeriksaan templatepemeriksaan=new MasterCariTemplatePemeriksaan(null,false);
            templatepemeriksaan.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            templatepemeriksaan.setLocationRelativeTo(internalFrame1);
            templatepemeriksaan.isCek();
            templatepemeriksaan.setDokter(KodeDokter.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),TNoRM.getText());
            templatepemeriksaan.tampil();
            templatepemeriksaan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    public void emptTeks(){
        BtnBatalActionPerformed(null);
        TabRawat.setSelectedIndex(3);
    }
    public void emptTeks1(){
        BtnBatalActionPerformed(null);
        TabRawat.setSelectedIndex(4);
    }
}
