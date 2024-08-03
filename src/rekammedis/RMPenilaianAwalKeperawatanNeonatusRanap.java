package rekammedis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import rekammedis.DlgMasterRencanaKeperawatan;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalKeperawatanNeonatusRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeDetailMasalah,tabModeRencana,tabModeDetailRencana;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,jml=0,index=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String pilihan="";
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahkeperawatan="",finger=""; 
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    public DlgMasterRencanaKeperawatan masterr=new DlgMasterRencanaKeperawatan(null,false);
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalKeperawatanNeonatusRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","NIP Pengkaji 1","Nama Pengkaji 1","NIP Pengkaji 2","Nama Pengkaji 2","Kode DPJP","Nama DPJP",
            "Tgl.Asuhan","Cara Masuk","Tiba Diruang Rawat","Anamnesis","Keluhan Utama","G","P","A","UK","Riwayat Penyakit Ibu","Ket.Riwayat Penyakit Ibu",
            "Riwayat Pengobatan Ibu Selama Hamil","Pernah dirawat","Indikasi","Status Gizi Ibu","G","P","A","UK","Tgl Lahir","Kondisi Saat Lahir","APGAR Score",
            "Cara Persalinan","Ket.Cara Persalinan","Letak","Ketuban","Tali Pusat","Antopometri BBL : BB(gr)","Antopometri BBL : PB(cm)","Antopometri BBL : LK(cm)",
            "Antopometri BBL : LD(cm)","Antopometri BBL : LP(cm)","Mayor","Minor","Nutrisi","Ket.Nutrisi","Frekuensi(cc)","/X","BAK","BAB","Alergi",
            "Alergi Obat","Reaksi Alergi Obat","Alergi Makanan","Reaksi Alergi Makanan","Alergi Lainnya","Reaksi Alergi Lainnya","Riwayat Penyakit Keluarga",
            "Riwayat Imunisasi","Obat-obatan","Ket.Obat-obatan","Merokok","Merokok(Batang/Hari)","Alkohol","Alkohol(Gelas/Hari)","Obat Tidur/Narkoba",
            "Kesadaran","Keadaan Umum","GCS","Nadi(x/menit)","RR(x/menit)","Suhu(°C)","SpO2(%)","Downes Score","BB(Kg)","TB(cm)","LK(cm)","LD(cm)","LP(cm)",
            "Golongan Darah/Rh(Bayi)","Rh(Bayi)","Golongan Darah/Rh(Ibu)","Rh(Ibu)","Golongan Darah/Rh(Ayah)","Rh(Ayah)","Gerak Bayi","Kepala","Ket.Kepala",
            "Ubun-ubun","Ket.Ubun-ubun","Wajah","Ket.Wajah","Kejang","Ket.Kejang","Refleks","Ket.Refleks","Tangis Bayi","Ket.Tangis Bayi","Denyut Jantung","Sirkulasi","Ket.Sirkulasi",
            "Pulsasi","Ket.Pulsasi","Pola Nafas","Jenis Pernafasan","Ket.Jenis Pernafasan","Retraksi","Air Entry","Merintih","Suara Nafas","Mulut","Ket.Mulut","Lidah","Ket.Lidah",
            "Tenggorokan","Ket.Tenggorokan","Abdomen","Ket.Abdomen","BAB","Ket.BAB","Warna BAB","Ket.Warna BAB","BAK","Ket.BAK","Warna BAK","Ket.Warna BAK","Posisi Mata","Besar Pupil",
            "Kelopak Mata","Ket.Kelopak Mata","Konjungtiva","Ket.Konjungtiva","Sklera","Ket.Sklera","Pendengaran","Ket.Pendengaran","Penciuman","Ket.Penciuman","Warna Kulit",
            "Vernic kaseosa","Lanugo","Turgor","Kulit","Reproduksi Laki-laki","Ket.Reproduksi Laki-laki","Reproduksi Perempuan","Ket.Reproduksi Perempuan","Lengan","Ket.Lengan","Tungkai","Ket.Tungkai",
            "Rekoil telinga","Garis telapak kaki","a. Kondisi Psikologis","b. Adakah Perilaku","c. Gangguan Jiwa di Masa Lalu","d. Hubungan Pasien",
            "e. Agama","f. Tinggal Dengan","g. Pekerjaan","h. Pembayaran","i. Nilai-nilai Kepercayaan","j. Bahasa Sehari-hari","k. Pendidikan Pasien","l. Pendidikan P.J.",
            "m. Edukasi Diberikan Kepada","n. Penerimaan terhadap kondisi bayi saat ini","o. Masalah Pernikahan","Ket.Masalah Pernikahan","Ekspresi Wajah","Nilai Ekspresi Wajah",
            "Tangisan","Nilai Tangisan","Pola Nafas","Nilai Pola Nafas","Tungkai","Nilai Tungkai","Tingkat kesadaran","Nilai Tingkat kesadaran","Total Nilai","Ket.Skala NIPS",
            "1. Masalah Minum (ASI/PASI) ?","Skor 1","2. Penurunan berat badan > 10% dari BBL (berat badan lahir) ?","Skor 2","3. Penyakit/ kelainan yang menyertai (sepsis, jantung, BBLR, hipoglikemi,diare, lain – lain) ?",
            "Skor 3","Total Skor","Sudah dibaca dan diketahui oleh Dietisen","Jam Dibaca Dietisen","Rencana Keperawatan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 192; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(85);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(85);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(117);
            }else if(i==12){
                column.setPreferredWidth(78);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(110);
            }else if(i==15){
                column.setPreferredWidth(70);
            }else if(i==16){
                column.setPreferredWidth(220);
            }else if(i==17){
                column.setPreferredWidth(170);
            }else if(i==18){
                column.setPreferredWidth(170);
            }else if(i==19){
                column.setPreferredWidth(170);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(125);
            }else if(i==23){
                column.setPreferredWidth(210);
            }else if(i==24){
                column.setPreferredWidth(130);
            }else if(i==25){
                column.setPreferredWidth(130);
            }else if(i==26){
                column.setPreferredWidth(48);
            }else if(i==27){
                column.setPreferredWidth(65);
            }else if(i==28){
                column.setPreferredWidth(44);
            }else if(i==29){
                column.setPreferredWidth(59);
            }else if(i==30){
                column.setPreferredWidth(61);
            }else if(i==31){
                column.setPreferredWidth(59);
            }else if(i==32){
                column.setPreferredWidth(120);
            }else if(i==33){
                column.setPreferredWidth(85);
            }else if(i==34){
                column.setPreferredWidth(64);
            }else if(i==35){
                column.setPreferredWidth(60);
            }else if(i==36){
                column.setPreferredWidth(74);
            }else if(i==37){
                column.setPreferredWidth(67);
            }else if(i==38){
                column.setPreferredWidth(52);
            }else if(i==39){
                column.setPreferredWidth(52);
            }else if(i==40){
                column.setPreferredWidth(44);
            }else if(i==41){
                column.setPreferredWidth(44);
            }else if(i==42){
                column.setPreferredWidth(150);
            }else if(i==43){
                column.setPreferredWidth(150);
            }else if(i==44){
                column.setPreferredWidth(106);
            }else if(i==45){
                column.setPreferredWidth(130);
            }else if(i==46){
                column.setPreferredWidth(65);
            }else if(i==47){
                column.setPreferredWidth(50);
            }else if(i==48){
                column.setPreferredWidth(130);
            }else if(i==49){
                column.setPreferredWidth(72);
            }else if(i==50){
                column.setPreferredWidth(54);
            }else if(i==51){
                column.setPreferredWidth(63);
            }else if(i==52){
                column.setPreferredWidth(69);
            }else if(i==53){
                column.setPreferredWidth(97);
            }else if(i==54){
                column.setPreferredWidth(75);
            }else if(i==55){
                column.setPreferredWidth(170);
            }else if(i==56){
                column.setPreferredWidth(70);
            }else if(i==57){
                column.setPreferredWidth(140);
            }else if(i==58){
                column.setPreferredWidth(140);
            }else if(i==59){
                column.setPreferredWidth(140);
            }else if(i==60){
                column.setPreferredWidth(140);
            }else if(i==61){
                column.setPreferredWidth(140);
            }else if(i==62){
                column.setPreferredWidth(111);
            }else if(i==63){
                column.setPreferredWidth(60);
            }else if(i==64){
                column.setPreferredWidth(60);
            }else if(i==65){
                column.setPreferredWidth(140);
            }else if(i==66){
                column.setPreferredWidth(119);
            }else if(i==67){
                column.setPreferredWidth(65);
            }else if(i==68){
                column.setPreferredWidth(74);
            }else if(i==69){
                column.setPreferredWidth(140);
            }else if(i==70){
                column.setPreferredWidth(41);
            }else if(i==71){
                column.setPreferredWidth(91);
            }else if(i==72){
                column.setPreferredWidth(66);
            }else if(i==73){
                column.setPreferredWidth(44);
            }else if(i==74){
                column.setPreferredWidth(159);
            }else if(i==75){
                column.setPreferredWidth(140);
            }else if(i==76){
                column.setPreferredWidth(94);
            }else if(i==77){
                column.setPreferredWidth(79);
            }else if(i==78){
                column.setPreferredWidth(140);
            }else if(i==79){
                column.setPreferredWidth(140);
            }else if(i==80){
                column.setPreferredWidth(79);
            }else if(i==81){
                column.setPreferredWidth(80);
            }else if(i==82){
                column.setPreferredWidth(85);
            }else if(i==83){
                column.setPreferredWidth(80);
            }else if(i==84){
                column.setPreferredWidth(79);
            }else if(i==85){
                column.setPreferredWidth(80);
            }else if(i==86){
                column.setPreferredWidth(80);
            }else if(i==87){
                column.setPreferredWidth(80);
            }else if(i==88){
                column.setPreferredWidth(103);
            }else if(i==89){
                column.setPreferredWidth(103);
            }else if(i==90){
                column.setPreferredWidth(103);
            }else if(i==91){
                column.setPreferredWidth(103);
            }else if(i==92){
                column.setPreferredWidth(103);
            }else if(i==93){
                column.setPreferredWidth(68);
            }else if(i==94){
                column.setPreferredWidth(90);
            }else if(i==95){
                column.setPreferredWidth(140);
            }else if(i==96){
                column.setPreferredWidth(65);
            }else if(i==97){
                column.setPreferredWidth(108);
            }else if(i==98){
                column.setPreferredWidth(120);
            }else if(i==99){
                column.setPreferredWidth(180);
            }else if(i==100){
                column.setPreferredWidth(67);
            }else if(i==101){
                column.setPreferredWidth(104);
            }else if(i==102){
                column.setPreferredWidth(140);
            }else if(i==103){
                column.setPreferredWidth(140);
            }else if(i==104){
                column.setPreferredWidth(170);
            }else if(i==105){
                column.setPreferredWidth(170);
            }else if(i==106){
                column.setPreferredWidth(161);
            }else if(i==107){
                column.setPreferredWidth(106);
            }else if(i==108){
                column.setPreferredWidth(250);
            }else if(i==109){
                column.setPreferredWidth(157);
            }else if(i==110){
                column.setPreferredWidth(105);
            }else if(i==111){
                column.setPreferredWidth(55);
            }else if(i==112){
                column.setPreferredWidth(140);
            }else if(i==113){
                column.setPreferredWidth(90);
            }else if(i==114){
                column.setPreferredWidth(90);
            }else if(i==115){
                column.setPreferredWidth(150);
            }else if(i==116){
                column.setPreferredWidth(110);
            }else if(i==117){
                column.setPreferredWidth(110);
            }else if(i==118){
                column.setPreferredWidth(95);
            }else if(i==119){
                column.setPreferredWidth(150);
            }else if(i==120){
                column.setPreferredWidth(80);
            }else if(i==121){
                column.setPreferredWidth(140);
            }else if(i==122){
                column.setPreferredWidth(140);
            }else if(i==123){
                column.setPreferredWidth(100);
            }else if(i==124){
                column.setPreferredWidth(85);
            }else if(i==125){
                column.setPreferredWidth(65);
            }else if(i==126){
                column.setPreferredWidth(80);
            }else if(i==127){
                column.setPreferredWidth(140);
            }else if(i==128){
                column.setPreferredWidth(140);
            }else if(i==129){
                column.setPreferredWidth(77);
            }else if(i==130){
                column.setPreferredWidth(40);
            }else if(i==131){
                column.setPreferredWidth(77);
            }else if(i==132){
                column.setPreferredWidth(40);
            }else if(i==133){
                column.setPreferredWidth(177);
            }else if(i==134){
                column.setPreferredWidth(40);
            }else if(i==135){
                column.setPreferredWidth(77);
            }else if(i==136){
                column.setPreferredWidth(40);
            }else if(i==137){
                column.setPreferredWidth(162);
            }else if(i==138){
                column.setPreferredWidth(40);
            }else if(i==139){
                column.setPreferredWidth(162);
            }else if(i==140){
                column.setPreferredWidth(40);
            }else if(i==141){
                column.setPreferredWidth(40);
            }else if(i==142){
                column.setPreferredWidth(82);
            }else if(i==143){
                column.setPreferredWidth(40);
            }else if(i==144){
                column.setPreferredWidth(82);
            }else if(i==145){
                column.setPreferredWidth(40);
            }else if(i==146){
                column.setPreferredWidth(82);
            }else if(i==147){
                column.setPreferredWidth(40);
            }else if(i==148){
                column.setPreferredWidth(82);
            }else if(i==149){
                column.setPreferredWidth(40);
            }else if(i==150){
                column.setPreferredWidth(82);
            }else if(i==151){
                column.setPreferredWidth(40);
            }else if(i==152){
                column.setPreferredWidth(82);
            }else if(i==153){
                column.setPreferredWidth(40);
            }else if(i==154){
                column.setPreferredWidth(82);
            }else if(i==155){
                column.setPreferredWidth(40);
            }else if(i==156){
                column.setPreferredWidth(82);
            }else if(i==157){
                column.setPreferredWidth(40);
            }else if(i==158){
                column.setPreferredWidth(82);
            }else if(i==159){
                column.setPreferredWidth(40);
            }else if(i==160){
                column.setPreferredWidth(86);
            }else if(i==161){
                column.setPreferredWidth(44);
            }else if(i==162){
                column.setPreferredWidth(86);
            }else if(i==163){
                column.setPreferredWidth(44);
            }else if(i==164){
                column.setPreferredWidth(40);
            }else if(i==165){
                column.setPreferredWidth(380);
            }else if(i==166){
                column.setPreferredWidth(40);
            }else if(i==167){
                column.setPreferredWidth(317);
            }else if(i==168){
                column.setPreferredWidth(40);
            }else if(i==169){
                column.setPreferredWidth(58);
            }else if(i==170){
                column.setPreferredWidth(165);
            }else if(i==171){
                column.setPreferredWidth(149);
            }else if(i==172){
                column.setPreferredWidth(209);
            }else if(i==173){
                column.setPreferredWidth(107);
            }else if(i==174){
                column.setPreferredWidth(200);
            }else if(i==175){
                column.setPreferredWidth(200);
            }else if(i==176){
                column.setPreferredWidth(40);
            }else if(i==177){
                column.setPreferredWidth(82);
            }else if(i==178){
                column.setPreferredWidth(40);
            }else if(i==179){
                column.setPreferredWidth(86);
            }else if(i==180){
                column.setPreferredWidth(44);
            }else if(i==181){
                column.setPreferredWidth(86);
            }else if(i==182){
                column.setPreferredWidth(44);
            }else if(i==183){
                column.setPreferredWidth(40);
            }else if(i==184){
                column.setPreferredWidth(380);
            }else if(i==185){
                column.setPreferredWidth(40);
            }else if(i==186){
                column.setPreferredWidth(317);
            }else if(i==187){
                column.setPreferredWidth(40);
            }else if(i==188){
                column.setPreferredWidth(58);
            }else if(i==189){
                column.setPreferredWidth(165);
            }else if(i==190){
                column.setPreferredWidth(149);
            }else if(i==191){
                column.setPreferredWidth(149);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KetAnamnesis.setDocument(new batasInput((byte)30).getKata(KetAnamnesis));
        G.setDocument(new batasInput((byte)10).getKata(G));
        P.setDocument(new batasInput((byte)10).getKata(P));
        A.setDocument(new batasInput((byte)10).getKata(A));
        UK.setDocument(new batasInput((byte)10).getKata(UK));
        RPK.setDocument(new batasInput((int)100).getKata(RPK));
        KetRPI.setDocument(new batasInput((int)255).getKata(KetRPI));
        RPO.setDocument(new batasInput((int)255).getKata(RPO));
        Indikasi.setDocument(new batasInput((int)255).getKata(Indikasi));
        G1.setDocument(new batasInput((byte)10).getKata(G1));
        P1.setDocument(new batasInput((byte)10).getKata(P1));
        A1.setDocument(new batasInput((byte)10).getKata(A1));
        UK1.setDocument(new batasInput((byte)10).getKata(UK1));
        Kondisi.setDocument(new batasInput((int)255).getKata(Kondisi));
        Apgar.setDocument(new batasInput((int)255).getKata(Apgar));
        KetCara.setDocument(new batasInput((int)255).getKata(KetCara));
        Letak.setDocument(new batasInput((int)255).getKata(Letak));
        BB.setDocument(new batasInput((byte)10).getKata(BB));
        PB.setDocument(new batasInput((byte)10).getKata(PB));
        LK.setDocument(new batasInput((byte)10).getKata(LK));
        LD.setDocument(new batasInput((byte)10).getKata(LD));
        LP.setDocument(new batasInput((byte)10).getKata(LP));
        KetNutrisi.setDocument(new batasInput((int)255).getKata(KetNutrisi));
        Frekuensi.setDocument(new batasInput((byte)10).getKata(Frekuensi));
        Kali.setDocument(new batasInput((byte)10).getKata(Kali));
        KeluhanBAK.setDocument(new batasInput((int)255).getKata(KeluhanBAK));
        KeluhanBAB.setDocument(new batasInput((int)255).getKata(KeluhanBAB));
        AlergiObat.setDocument(new batasInput((int)255).getKata(AlergiObat));
        ReaksiObat.setDocument(new batasInput((int)255).getKata(ReaksiObat));
        AlergiMakanan.setDocument(new batasInput((int)255).getKata(AlergiMakanan));
        ReaksiMakanan.setDocument(new batasInput((int)255).getKata(ReaksiMakanan));
        AlergiLainnya.setDocument(new batasInput((int)255).getKata(AlergiLainnya));
        ReaksiLainnya.setDocument(new batasInput((int)255).getKata(ReaksiLainnya));
        RPK.setDocument(new batasInput((int)255).getKata(RPK));
        Imunisasi.setDocument(new batasInput((int)255).getKata(Imunisasi));
        KetObat.setDocument(new batasInput((int)255).getKata(KetObat));
        KebiasaanJumlahRokok.setDocument(new batasInput((byte)5).getKata(KebiasaanJumlahRokok));
        KebiasaanJumlahAlkohol.setDocument(new batasInput((byte)5).getKata(KebiasaanJumlahAlkohol));
        KesadaranMental.setDocument(new batasInput((int)40).getKata(KesadaranMental));
        GCS.setDocument(new batasInput((byte)10).getKata(GCS));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        SpO2.setDocument(new batasInput((byte)5).getKata(SpO2));
        Downes.setDocument(new batasInput((byte)5).getKata(Downes));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((int)50).getKata(TB));
        LK1.setDocument(new batasInput((byte)10).getKata(LK1));
        LD1.setDocument(new batasInput((byte)10).getKata(LD1));
        LP1.setDocument(new batasInput((byte)10).getKata(LP1));
        KetSistemSarafKepala.setDocument(new batasInput((byte)50).getKata(KetSistemSarafKepala));
        KetSistemSarafUbun.setDocument(new batasInput((byte)50).getKata(KetSistemSarafUbun));
        KetSistemSarafWajah.setDocument(new batasInput((byte)50).getKata(KetSistemSarafWajah));
        KetSistemSarafKejang.setDocument(new batasInput((byte)50).getKata(KetSistemSarafKejang));
        KetSistemSarafRefleks.setDocument(new batasInput((byte)50).getKata(KetSistemSarafRefleks));
        KetSistemSarafTangis.setDocument(new batasInput((byte)50).getKata(KetSistemSarafTangis));
        KetKardiovaskularSirkulasi.setDocument(new batasInput((byte)50).getKata(KetKardiovaskularSirkulasi));
        KetKardiovaskularPulsasi.setDocument(new batasInput((byte)50).getKata(KetKardiovaskularPulsasi));
        KetRespirasiJenisPernafasan.setDocument(new batasInput((byte)50).getKata(KetRespirasiJenisPernafasan));
        KetGastrointestinalMulut.setDocument(new batasInput((byte)50).getKata(KetGastrointestinalMulut));
        KetGastrointestinalLidah.setDocument(new batasInput((byte)50).getKata(KetGastrointestinalLidah));
        KetGastrointestinalTenggorakan.setDocument(new batasInput((byte)50).getKata(KetGastrointestinalTenggorakan));
        KetGastrointestinalAbdomen.setDocument(new batasInput((byte)50).getKata(KetGastrointestinalAbdomen));
        KetGastrointestinalBAB.setDocument(new batasInput((byte)50).getKata(KetGastrointestinalBAB));
        KetGastrointestinalWarnaBAB.setDocument(new batasInput((byte)50).getKata(KetGastrointestinalWarnaBAB));
        KetGastrointestinalBAK.setDocument(new batasInput((byte)50).getKata(KetGastrointestinalBAK));
        KetGastrointestinalWarnaBAK.setDocument(new batasInput((byte)50).getKata(KetGastrointestinalWarnaBAK));
        KetNeurologiKelopak.setDocument(new batasInput((byte)50).getKata(KetNeurologiKelopak));
        KetNeurologiKonjungtiva.setDocument(new batasInput((byte)50).getKata(KetNeurologiKonjungtiva));
        KetNeurologiSklera.setDocument(new batasInput((byte)50).getKata(KetNeurologiSklera));
        KetNeurologiPendengaran.setDocument(new batasInput((byte)50).getKata(KetNeurologiPendengaran));
        KetNeurologiPenciuman.setDocument(new batasInput((byte)50).getKata(KetNeurologiPenciuman));
        KetReproduksiLaki.setDocument(new batasInput((byte)50).getKata(KetReproduksiLaki));
        KetReproduksiPerempuan.setDocument(new batasInput((byte)50).getKata(KetReproduksiPerempuan));
        KetMuskuloskeletalLengan.setDocument(new batasInput((byte)50).getKata(KetMuskuloskeletalLengan));
        KetMuskuloskeletalTungkai.setDocument(new batasInput((byte)50).getKata(KetMuskuloskeletalTungkai));
        KeteranganAdakahPerilaku.setDocument(new batasInput((byte)40).getKata(KeteranganAdakahPerilaku));
        KeteranganTinggalDengan.setDocument(new batasInput((byte)40).getKata(KeteranganTinggalDengan));
        KeteranganNilaiKepercayaan.setDocument(new batasInput((byte)40).getKata(KeteranganNilaiKepercayaan));
        KeteranganEdukasiPsikologis.setDocument(new batasInput((byte)40).getKata(KeteranganEdukasiPsikologis));
        KeteranganPernikahan.setDocument(new batasInput((byte)50).getKata(KeteranganPernikahan));
        NilaiNIPS1.setDocument(new batasInput((byte)5).getKata(NilaiNIPS1));
        NilaiNIPS2.setDocument(new batasInput((byte)5).getKata(NilaiNIPS2));
        NilaiNIPS3.setDocument(new batasInput((byte)5).getKata(NilaiNIPS3));
        NilaiNIPS4.setDocument(new batasInput((byte)5).getKata(NilaiNIPS4));
        NilaiNIPS5.setDocument(new batasInput((byte)5).getKata(NilaiNIPS5));
        TotalNIPS.setDocument(new batasInput((byte)5).getKata(TotalNIPS));
        KetNIPS.setDocument(new batasInput((int)50).getKata(KetNIPS));
        NilaiGizi1.setDocument(new batasInput((byte)5).getKata(NilaiGizi1));
        NilaiGizi2.setDocument(new batasInput((byte)5).getKata(NilaiGizi2));
        NilaiGizi3.setDocument(new batasInput((byte)5).getKata(NilaiGizi3));
        KeteranganDiketahuiDietisen.setDocument(new batasInput((byte)10).getKata(KeteranganDiketahuiDietisen));
//        Rencana.setDocument(new batasInput((int)200).getKata(Rencana));
        
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    if(i==1){
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());  
                    }else{
                        KdPetugas2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());  
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){ 
                    KdDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());  
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
        
        masterr.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("RMPenilaianAwalKeperawatanRanap")){
                    if(masterr.getTable().getSelectedRow()!= -1){
                        Rencana.setText(masterr.getTable().getValueAt(masterr.getTable().getSelectedRow(),3).toString());
                    }  
                    Rencana.requestFocus();
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
        
        masterr.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("RMPenilaianAwalKeperawatanRanap")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        masterr.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        tabModeMasalah=new DefaultTableModel(null,new Object[]{
                "P","KODE","MASALAH KEPERAWATAN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbMasalahKeperawatan.setModel(tabModeMasalah);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahKeperawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMasalahKeperawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 3; i++) {
            TableColumn column = tbMasalahKeperawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbMasalahKeperawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRencana=new DefaultTableModel(null,new Object[]{
                "P","KODE","RENCANA KEPERAWATAN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
//        tbRencanaKeperawatan.setModel(tabModeRencana);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
//        tbRencanaKeperawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
//        tbRencanaKeperawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        
//        for (i = 0; i < 3; i++) {
//            TableColumn column = tbRencanaKeperawatan.getColumnModel().getColumn(i);
//            if(i==0){
//                column.setPreferredWidth(20);
//            }else if(i==1){
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
//            }else if(i==2){
//                column.setPreferredWidth(350);
//            }
//        }
//        tbRencanaKeperawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMasalah=new DefaultTableModel(null,new Object[]{
                "Kode","Masalah Keperawatan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbMasalahDetail.setModel(tabModeDetailMasalah);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahDetail.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMasalahDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbMasalahDetail.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(420);
            }
        }
        tbMasalahDetail.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailRencana=new DefaultTableModel(null,new Object[]{
                "Kode","Rencana Keperawatan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRencanaDetail.setModel(tabModeDetailRencana);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbRencanaDetail.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRencanaDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbRencanaDetail.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(420);
            }
        }
        tbRencanaDetail.setDefaultRenderer(Object.class, new WarnaTable());
        
        ChkAccor.setSelected(false);
        isMenu();
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
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel36 = new widget.Label();
        Anamnesis = new widget.ComboBox();
        TglAsuhan = new widget.Tanggal();
        NmPetugas2 = new widget.TextBox();
        BtnPetugas2 = new widget.Button();
        KdPetugas2 = new widget.TextBox();
        label15 = new widget.Label();
        label16 = new widget.Label();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        BtnDPJP = new widget.Button();
        TibadiRuang = new widget.ComboBox();
        jLabel37 = new widget.Label();
        CaraMasuk = new widget.ComboBox();
        jLabel38 = new widget.Label();
        jLabel94 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel31 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        KetAnamnesis = new widget.TextBox();
        Imunisasi = new widget.TextBox();
        jLabel42 = new widget.Label();
        RPI = new widget.ComboBox();
        KetRPI = new widget.TextBox();
        jLabel46 = new widget.Label();
        jLabel124 = new widget.Label();
        KebiasaanMerokok = new widget.ComboBox();
        KebiasaanJumlahRokok = new widget.TextBox();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        KebiasaanAlkohol = new widget.ComboBox();
        KebiasaanJumlahAlkohol = new widget.TextBox();
        jLabel127 = new widget.Label();
        KebiasaanNarkoba = new widget.ComboBox();
        jLabel128 = new widget.Label();
        jLabel129 = new widget.Label();
        KebiasaanObat = new widget.ComboBox();
        jLabel95 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel47 = new widget.Label();
        KesadaranMental = new widget.TextBox();
        jLabel130 = new widget.Label();
        KeadaanMentalUmum = new widget.ComboBox();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jLabel17 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel26 = new widget.Label();
        RR = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel24 = new widget.Label();
        SpO2 = new widget.TextBox();
        jLabel29 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel15 = new widget.Label();
        TB = new widget.TextBox();
        jLabel48 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel131 = new widget.Label();
        SistemSarafKepala = new widget.ComboBox();
        KetSistemSarafKepala = new widget.TextBox();
        jLabel132 = new widget.Label();
        SistemSarafWajah = new widget.ComboBox();
        KetSistemSarafWajah = new widget.TextBox();
        jLabel135 = new widget.Label();
        SistemSarafKejang = new widget.ComboBox();
        KetSistemSarafKejang = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel136 = new widget.Label();
        KardiovaskularPulsasi = new widget.ComboBox();
        jLabel137 = new widget.Label();
        KardiovaskularSirkulasi = new widget.ComboBox();
        KetKardiovaskularSirkulasi = new widget.TextBox();
        jLabel138 = new widget.Label();
        KardiovaskularDenyutNadi = new widget.ComboBox();
        jLabel35 = new widget.Label();
        jLabel139 = new widget.Label();
        RespirasiRetraksi = new widget.ComboBox();
        RespirasiPolaNafas = new widget.ComboBox();
        jLabel140 = new widget.Label();
        jLabel141 = new widget.Label();
        RespirasiSuaraNafas = new widget.ComboBox();
        jLabel144 = new widget.Label();
        RespirasiJenisPernafasan = new widget.ComboBox();
        KetRespirasiJenisPernafasan = new widget.TextBox();
        jLabel49 = new widget.Label();
        jLabel146 = new widget.Label();
        GastrointestinalMulut = new widget.ComboBox();
        KetGastrointestinalMulut = new widget.TextBox();
        jLabel147 = new widget.Label();
        GastrointestinalLidah = new widget.ComboBox();
        KetGastrointestinalLidah = new widget.TextBox();
        jLabel150 = new widget.Label();
        GastrointestinalTenggorakan = new widget.ComboBox();
        KetGastrointestinalTenggorakan = new widget.TextBox();
        jLabel151 = new widget.Label();
        GastrointestinalAbdomen = new widget.ComboBox();
        KetGastrointestinalAbdomen = new widget.TextBox();
        jLabel50 = new widget.Label();
        jLabel153 = new widget.Label();
        NeurologiMata = new widget.ComboBox();
        jLabel156 = new widget.Label();
        NeurologiSklera = new widget.ComboBox();
        KetNeurologiSklera = new widget.TextBox();
        jLabel158 = new widget.Label();
        NeurologiPendengaran = new widget.ComboBox();
        jLabel51 = new widget.Label();
        jLabel160 = new widget.Label();
        IntegumentKulit = new widget.ComboBox();
        jLabel161 = new widget.Label();
        IntegumentWarnaKulit = new widget.ComboBox();
        jLabel162 = new widget.Label();
        IntegumentTurgor = new widget.ComboBox();
        IntegumentVernic = new widget.ComboBox();
        jLabel163 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel164 = new widget.Label();
        ReproduksiLaki = new widget.ComboBox();
        KetReproduksiLaki = new widget.TextBox();
        KetReproduksiPerempuan = new widget.TextBox();
        ReproduksiPerempuan = new widget.ComboBox();
        jLabel165 = new widget.Label();
        jLabel167 = new widget.Label();
        MuskuloskletalRekoil = new widget.ComboBox();
        MuskuloskletalGaris = new widget.ComboBox();
        jLabel168 = new widget.Label();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel189 = new widget.Label();
        jLabel190 = new widget.Label();
        KondisiPsikologis = new widget.ComboBox();
        jLabel191 = new widget.Label();
        AdakahPerilaku = new widget.ComboBox();
        KeteranganAdakahPerilaku = new widget.TextBox();
        jLabel192 = new widget.Label();
        GangguanJiwa = new widget.ComboBox();
        jLabel193 = new widget.Label();
        HubunganAnggotaKeluarga = new widget.ComboBox();
        jLabel194 = new widget.Label();
        Agama = new widget.TextBox();
        jLabel195 = new widget.Label();
        TinggalDengan = new widget.ComboBox();
        KeteranganTinggalDengan = new widget.TextBox();
        jLabel196 = new widget.Label();
        PekerjaanPasien = new widget.TextBox();
        jLabel197 = new widget.Label();
        CaraBayar = new widget.TextBox();
        jLabel198 = new widget.Label();
        NilaiKepercayaan = new widget.ComboBox();
        KeteranganNilaiKepercayaan = new widget.TextBox();
        jLabel199 = new widget.Label();
        Bahasa = new widget.TextBox();
        jLabel200 = new widget.Label();
        PendidikanPasien = new widget.TextBox();
        jLabel201 = new widget.Label();
        PendidikanPJ = new widget.ComboBox();
        jLabel202 = new widget.Label();
        EdukasiPsikolgis = new widget.ComboBox();
        KeteranganEdukasiPsikologis = new widget.TextBox();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel203 = new widget.Label();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel271 = new widget.Label();
        jLabel272 = new widget.Label();
        SkalaGizi1 = new widget.ComboBox();
        jLabel273 = new widget.Label();
        NilaiGizi1 = new widget.TextBox();
        jLabel274 = new widget.Label();
        SkalaGizi2 = new widget.ComboBox();
        jLabel275 = new widget.Label();
        NilaiGizi2 = new widget.TextBox();
        jLabel276 = new widget.Label();
        NilaiGiziTotal = new widget.TextBox();
        jLabel278 = new widget.Label();
        DiketahuiDietisen = new widget.ComboBox();
        jLabel279 = new widget.Label();
        KeteranganDiketahuiDietisen = new widget.TextBox();
        jSeparator12 = new javax.swing.JSeparator();
        Scroll6 = new widget.ScrollPane();
        tbMasalahKeperawatan = new widget.Table();
        BtnTambahMasalah = new widget.Button();
        BtnAllMasalah = new widget.Button();
        BtnCariMasalah = new widget.Button();
        TCariMasalah = new widget.TextBox();
        label12 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Rencana = new widget.TextArea();
        BtnDokter1 = new widget.Button();
        jLabel79 = new widget.Label();
        jLabel59 = new widget.Label();
        G = new widget.TextBox();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        P = new widget.TextBox();
        jLabel63 = new widget.Label();
        A = new widget.TextBox();
        jLabel64 = new widget.Label();
        UK = new widget.TextBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        RPO = new widget.TextBox();
        jLabel67 = new widget.Label();
        RD = new widget.ComboBox();
        Indikasi = new widget.TextBox();
        jLabel68 = new widget.Label();
        GiziIbu = new widget.ComboBox();
        jLabel69 = new widget.Label();
        Cara = new widget.ComboBox();
        KetCara = new widget.TextBox();
        jLabel70 = new widget.Label();
        BB1 = new widget.TextBox();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        PB = new widget.TextBox();
        jLabel74 = new widget.Label();
        LK = new widget.TextBox();
        jLabel75 = new widget.Label();
        LD = new widget.TextBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        Letak = new widget.TextBox();
        Ketuban = new widget.ComboBox();
        jLabel81 = new widget.Label();
        Tali = new widget.ComboBox();
        jLabel82 = new widget.Label();
        label13 = new widget.Label();
        TanggalLahir = new widget.Tanggal();
        jLabel83 = new widget.Label();
        Kondisi = new widget.TextBox();
        jLabel84 = new widget.Label();
        Apgar = new widget.TextBox();
        jLabel78 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        LP = new widget.TextBox();
        jLabel90 = new widget.Label();
        G1 = new widget.TextBox();
        jLabel91 = new widget.Label();
        P1 = new widget.TextBox();
        jLabel92 = new widget.Label();
        A1 = new widget.TextBox();
        jLabel93 = new widget.Label();
        UK1 = new widget.TextBox();
        jLabel96 = new widget.Label();
        Mayor = new widget.ComboBox();
        jLabel98 = new widget.Label();
        jLabel99 = new widget.Label();
        Minor = new widget.ComboBox();
        jLabel100 = new widget.Label();
        Nutrisi = new widget.ComboBox();
        jLabel101 = new widget.Label();
        jLabel102 = new widget.Label();
        jLabel103 = new widget.Label();
        KetNutrisi = new widget.TextBox();
        jLabel97 = new widget.Label();
        Kali = new widget.TextBox();
        jLabel104 = new widget.Label();
        Frekuensi = new widget.TextBox();
        jLabel105 = new widget.Label();
        jLabel106 = new widget.Label();
        KeluhanBAK = new widget.TextBox();
        jLabel107 = new widget.Label();
        KeluhanBAB = new widget.TextBox();
        Alergi = new widget.ComboBox();
        jLabel109 = new widget.Label();
        jLabel111 = new widget.Label();
        AlergiObat = new widget.TextBox();
        jLabel282 = new widget.Label();
        ReaksiObat = new widget.TextBox();
        jLabel112 = new widget.Label();
        AlergiMakanan = new widget.TextBox();
        jLabel283 = new widget.Label();
        ReaksiMakanan = new widget.TextBox();
        jLabel280 = new widget.Label();
        AlergiLainnya = new widget.TextBox();
        jLabel284 = new widget.Label();
        ReaksiLainnya = new widget.TextBox();
        KetObat = new widget.TextBox();
        jLabel108 = new widget.Label();
        Downes = new widget.TextBox();
        jLabel110 = new widget.Label();
        jLabel281 = new widget.Label();
        LK1 = new widget.TextBox();
        jLabel285 = new widget.Label();
        LD1 = new widget.TextBox();
        jLabel286 = new widget.Label();
        jLabel287 = new widget.Label();
        LP1 = new widget.TextBox();
        jLabel288 = new widget.Label();
        DarahBayi = new widget.ComboBox();
        jLabel289 = new widget.Label();
        RhBayi = new widget.ComboBox();
        jLabel290 = new widget.Label();
        DarahIbu = new widget.ComboBox();
        jLabel291 = new widget.Label();
        RhIbu = new widget.ComboBox();
        jLabel292 = new widget.Label();
        DarahAyah = new widget.ComboBox();
        jLabel293 = new widget.Label();
        RhAyah = new widget.ComboBox();
        jLabel294 = new widget.Label();
        jLabel295 = new widget.Label();
        Gerak = new widget.ComboBox();
        jLabel296 = new widget.Label();
        SistemSarafUbun = new widget.ComboBox();
        KetSistemSarafUbun = new widget.TextBox();
        jLabel133 = new widget.Label();
        SistemSarafRefleks = new widget.ComboBox();
        KetSistemSarafRefleks = new widget.TextBox();
        jLabel297 = new widget.Label();
        SistemSarafTangis = new widget.ComboBox();
        KetSistemSarafTangis = new widget.TextBox();
        KetKardiovaskularPulsasi = new widget.TextBox();
        RespirasiAir = new widget.ComboBox();
        jLabel142 = new widget.Label();
        jLabel143 = new widget.Label();
        RespirasiMerintih = new widget.ComboBox();
        jLabel152 = new widget.Label();
        GastrointestinalBAB = new widget.ComboBox();
        KetGastrointestinalBAB = new widget.TextBox();
        jLabel298 = new widget.Label();
        GastrointestinalWarnaBAB = new widget.ComboBox();
        KetGastrointestinalWarnaBAB = new widget.TextBox();
        jLabel299 = new widget.Label();
        GastrointestinalBAK = new widget.ComboBox();
        KetGastrointestinalBAK = new widget.TextBox();
        jLabel300 = new widget.Label();
        GastrointestinalWarnaBAK = new widget.ComboBox();
        KetGastrointestinalWarnaBAK = new widget.TextBox();
        jLabel154 = new widget.Label();
        NeurologiPupil = new widget.ComboBox();
        jLabel157 = new widget.Label();
        NeurologiKelopak = new widget.ComboBox();
        KetNeurologiKelopak = new widget.TextBox();
        jLabel159 = new widget.Label();
        NeurologiKonjungtiva = new widget.ComboBox();
        KetNeurologiKonjungtiva = new widget.TextBox();
        KetNeurologiPendengaran = new widget.TextBox();
        jLabel301 = new widget.Label();
        NeurologiPenciuman = new widget.ComboBox();
        KetNeurologiPenciuman = new widget.TextBox();
        IntegumentLanugo = new widget.ComboBox();
        jLabel302 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel303 = new widget.Label();
        MuskuloskeletalLengan = new widget.ComboBox();
        KetMuskuloskeletalLengan = new widget.TextBox();
        jLabel305 = new widget.Label();
        MuskuloskeletalTungkai = new widget.ComboBox();
        KetMuskuloskeletalTungkai = new widget.TextBox();
        jLabel304 = new widget.Label();
        Penerimaan = new widget.ComboBox();
        jLabel306 = new widget.Label();
        Pernikahan = new widget.ComboBox();
        KeteranganPernikahan = new widget.TextBox();
        jLabel80 = new widget.Label();
        jLabel307 = new widget.Label();
        NIPS1 = new widget.ComboBox();
        jLabel308 = new widget.Label();
        NilaiNIPS1 = new widget.TextBox();
        jLabel309 = new widget.Label();
        jLabel310 = new widget.Label();
        jLabel311 = new widget.Label();
        NIPS2 = new widget.ComboBox();
        jLabel312 = new widget.Label();
        NilaiNIPS2 = new widget.TextBox();
        jLabel313 = new widget.Label();
        jLabel314 = new widget.Label();
        NIPS3 = new widget.ComboBox();
        jLabel315 = new widget.Label();
        NilaiNIPS3 = new widget.TextBox();
        jLabel316 = new widget.Label();
        jLabel317 = new widget.Label();
        NIPS4 = new widget.ComboBox();
        jLabel318 = new widget.Label();
        NilaiNIPS4 = new widget.TextBox();
        jLabel319 = new widget.Label();
        jLabel320 = new widget.Label();
        NIPS5 = new widget.ComboBox();
        jLabel321 = new widget.Label();
        NilaiNIPS5 = new widget.TextBox();
        jLabel325 = new widget.Label();
        TotalNIPS = new widget.TextBox();
        KetNIPS = new widget.TextBox();
        jLabel322 = new widget.Label();
        jLabel277 = new widget.Label();
        SkalaGizi3 = new widget.ComboBox();
        jLabel323 = new widget.Label();
        NilaiGizi3 = new widget.TextBox();
        jLabel324 = new widget.Label();
        jLabel326 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormMenu = new widget.PanelBiasa();
        jLabel34 = new widget.Label();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        BtnPrint1 = new widget.Button();
        FormMasalahRencana = new widget.PanelBiasa();
        Scroll7 = new widget.ScrollPane();
        tbMasalahDetail = new widget.Table();
        Scroll9 = new widget.ScrollPane();
        tbRencanaDetail = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Keperawatan Rawat Inap Neonatus ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 2900));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Pengkaji 1 :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(74, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(176, 40, 210, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("Alt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(388, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(438, 70, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel36.setText("Anamnesis :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 100, 70, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Orang Tua", "Keluarga", "Lainnya" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(74, 100, 130, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-01-2023 14:02:22" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(512, 70, 135, 23);

        NmPetugas2.setEditable(false);
        NmPetugas2.setName("NmPetugas2"); // NOI18N
        NmPetugas2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas2);
        NmPetugas2.setBounds(614, 40, 210, 23);

        BtnPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas2.setMnemonic('2');
        BtnPetugas2.setToolTipText("Alt+2");
        BtnPetugas2.setName("BtnPetugas2"); // NOI18N
        BtnPetugas2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugas2ActionPerformed(evt);
            }
        });
        BtnPetugas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugas2KeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas2);
        BtnPetugas2.setBounds(826, 40, 28, 23);

        KdPetugas2.setEditable(false);
        KdPetugas2.setName("KdPetugas2"); // NOI18N
        KdPetugas2.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugas2KeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas2);
        KdPetugas2.setBounds(512, 40, 100, 23);

        label15.setText("Pengkaji 2 :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(438, 40, 70, 23);

        label16.setText("DPJP :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(0, 70, 70, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setName("KdDPJP"); // NOI18N
        KdDPJP.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDPJPKeyPressed(evt);
            }
        });
        FormInput.add(KdDPJP);
        KdDPJP.setBounds(74, 70, 110, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setName("NmDPJP"); // NOI18N
        NmDPJP.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDPJP);
        NmDPJP.setBounds(186, 70, 230, 23);

        BtnDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDPJP.setMnemonic('2');
        BtnDPJP.setToolTipText("Alt+2");
        BtnDPJP.setName("BtnDPJP"); // NOI18N
        BtnDPJP.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDPJPActionPerformed(evt);
            }
        });
        BtnDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDPJPKeyPressed(evt);
            }
        });
        FormInput.add(BtnDPJP);
        BtnDPJP.setBounds(418, 70, 28, 23);

        TibadiRuang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Digendong", "Inkubator" }));
        TibadiRuang.setName("TibadiRuang"); // NOI18N
        TibadiRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TibadiRuangKeyPressed(evt);
            }
        });
        FormInput.add(TibadiRuang);
        TibadiRuang.setBounds(780, 70, 155, 23);

        jLabel37.setText("Tiba Di Ruang Rawat :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(650, 70, 120, 23);

        CaraMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Poli", "IGD", "Rujukan dr spesialis/Puskesmas/RS Luar/Bidan/Klinik", "OK", "VK" }));
        CaraMasuk.setName("CaraMasuk"); // NOI18N
        CaraMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraMasukKeyPressed(evt);
            }
        });
        FormInput.add(CaraMasuk);
        CaraMasuk.setBounds(470, 100, 390, 23);

        jLabel38.setText("Cara Masuk :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(390, 100, 70, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("I. RIWAYAT KESEHATAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 130, 180, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(RPS);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(179, 150, 660, 43);

        jLabel30.setText("Keluhan Utama :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 150, 175, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPK);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(190, 890, 640, 43);

        jLabel31.setText("Riwayat Penyakit Keluarga :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 890, 180, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 130, 880, 1);

        KetAnamnesis.setFocusTraversalPolicyProvider(true);
        KetAnamnesis.setName("KetAnamnesis"); // NOI18N
        KetAnamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis);
        KetAnamnesis.setBounds(208, 100, 175, 23);

        Imunisasi.setFocusTraversalPolicyProvider(true);
        Imunisasi.setName("Imunisasi"); // NOI18N
        Imunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImunisasiKeyPressed(evt);
            }
        });
        FormInput.add(Imunisasi);
        Imunisasi.setBounds(190, 950, 280, 23);

        jLabel42.setText("Riwayat Imunisasi :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(23, 950, 156, 23);

        RPI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DM", "Hipertensi", "Jantung", "TBC", "Hep B", "Asma", "PMS", "Lainnya" }));
        RPI.setName("RPI"); // NOI18N
        RPI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPIKeyPressed(evt);
            }
        });
        FormInput.add(RPI);
        RPI.setBounds(300, 240, 90, 23);

        KetRPI.setFocusTraversalPolicyProvider(true);
        KetRPI.setName("KetRPI"); // NOI18N
        KetRPI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRPIKeyPressed(evt);
            }
        });
        FormInput.add(KetRPI);
        KetRPI.setBounds(400, 240, 300, 23);

        jLabel46.setText("Kebiasaan Ibu :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 990, 180, 23);

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel124.setText("batang/hari");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(440, 1020, 70, 23);

        KebiasaanMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KebiasaanMerokok.setName("KebiasaanMerokok"); // NOI18N
        KebiasaanMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanMerokokKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanMerokok);
        KebiasaanMerokok.setBounds(310, 1020, 80, 23);

        KebiasaanJumlahRokok.setFocusTraversalPolicyProvider(true);
        KebiasaanJumlahRokok.setName("KebiasaanJumlahRokok"); // NOI18N
        KebiasaanJumlahRokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanJumlahRokokKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanJumlahRokok);
        KebiasaanJumlahRokok.setBounds(400, 1020, 40, 23);

        jLabel125.setText("Merokok :");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(180, 1020, 60, 23);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("gelas/hari");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(440, 1050, 60, 23);

        KebiasaanAlkohol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KebiasaanAlkohol.setName("KebiasaanAlkohol"); // NOI18N
        KebiasaanAlkohol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanAlkoholKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanAlkohol);
        KebiasaanAlkohol.setBounds(310, 1050, 80, 23);

        KebiasaanJumlahAlkohol.setFocusTraversalPolicyProvider(true);
        KebiasaanJumlahAlkohol.setName("KebiasaanJumlahAlkohol"); // NOI18N
        KebiasaanJumlahAlkohol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanJumlahAlkoholKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanJumlahAlkohol);
        KebiasaanJumlahAlkohol.setBounds(400, 1050, 40, 23);

        jLabel127.setText("Alkohol :");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(185, 1050, 50, 23);

        KebiasaanNarkoba.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KebiasaanNarkoba.setName("KebiasaanNarkoba"); // NOI18N
        KebiasaanNarkoba.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanNarkobaKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanNarkoba);
        KebiasaanNarkoba.setBounds(310, 1080, 80, 23);

        jLabel128.setText("Obat Tidur/Narkoba :");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(175, 1080, 120, 23);

        jLabel129.setText("Obat-Obatan :");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(170, 990, 90, 23);

        KebiasaanObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Obat-obatan yang diminum", "Vitamin", "Jamu-jamuan" }));
        KebiasaanObat.setName("KebiasaanObat"); // NOI18N
        KebiasaanObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanObatKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanObat);
        KebiasaanObat.setBounds(310, 990, 130, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PEMERIKSAAN FISIK");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 1120, 180, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 1120, 880, 1);

        jLabel47.setText("Kesadaran :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 1140, 100, 23);

        KesadaranMental.setFocusTraversalPolicyProvider(true);
        KesadaranMental.setName("KesadaranMental"); // NOI18N
        KesadaranMental.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranMentalKeyPressed(evt);
            }
        });
        FormInput.add(KesadaranMental);
        KesadaranMental.setBounds(100, 1140, 250, 23);

        jLabel130.setText("Keadaan Umum :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(350, 1140, 90, 23);

        KeadaanMentalUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Sedang", "Buruk" }));
        KeadaanMentalUmum.setName("KeadaanMentalUmum"); // NOI18N
        KeadaanMentalUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanMentalUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanMentalUmum);
        KeadaanMentalUmum.setBounds(440, 1140, 90, 23);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(550, 1140, 70, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(630, 1140, 75, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 1170, 73, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(80, 1170, 50, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(130, 1170, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(180, 1170, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(240, 1170, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(290, 1170, 50, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(360, 1170, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                SuhuKeyTyped(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(410, 1170, 50, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(460, 1170, 30, 23);

        jLabel24.setText("SpO2 :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(500, 1170, 40, 23);

        SpO2.setFocusTraversalPolicyProvider(true);
        SpO2.setName("SpO2"); // NOI18N
        SpO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpO2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                SpO2KeyTyped(evt);
            }
        });
        FormInput.add(SpO2);
        SpO2.setBounds(550, 1170, 50, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("%");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(600, 1170, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(40, 1200, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(80, 1200, 50, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(130, 1200, 30, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(160, 1200, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(200, 1200, 50, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("cm");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(250, 1200, 30, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Sistem Susunan Saraf Pusat :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(50, 1330, 187, 23);

        jLabel131.setText("Kepala :");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(210, 1350, 60, 23);

        SistemSarafKepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Hydrocephalus", "Hematoma", "Lain-lain" }));
        SistemSarafKepala.setName("SistemSarafKepala"); // NOI18N
        SistemSarafKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafKepalaKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafKepala);
        SistemSarafKepala.setBounds(270, 1350, 97, 23);

        KetSistemSarafKepala.setFocusTraversalPolicyProvider(true);
        KetSistemSarafKepala.setName("KetSistemSarafKepala"); // NOI18N
        KetSistemSarafKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSistemSarafKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KetSistemSarafKepala);
        KetSistemSarafKepala.setBounds(370, 1350, 184, 23);

        jLabel132.setText("Wajah :");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(20, 1380, 90, 23);

        SistemSarafWajah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Asimetris", "Kelainan Kongenital" }));
        SistemSarafWajah.setName("SistemSarafWajah"); // NOI18N
        SistemSarafWajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafWajahKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafWajah);
        SistemSarafWajah.setBounds(110, 1380, 150, 23);

        KetSistemSarafWajah.setFocusTraversalPolicyProvider(true);
        KetSistemSarafWajah.setName("KetSistemSarafWajah"); // NOI18N
        KetSistemSarafWajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSistemSarafWajahKeyPressed(evt);
            }
        });
        FormInput.add(KetSistemSarafWajah);
        KetSistemSarafWajah.setBounds(260, 1380, 184, 23);

        jLabel135.setText("Kejang :");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(450, 1380, 60, 23);

        SistemSarafKejang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        SistemSarafKejang.setName("SistemSarafKejang"); // NOI18N
        SistemSarafKejang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafKejangKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafKejang);
        SistemSarafKejang.setBounds(510, 1380, 80, 23);

        KetSistemSarafKejang.setFocusTraversalPolicyProvider(true);
        KetSistemSarafKejang.setName("KetSistemSarafKejang"); // NOI18N
        KetSistemSarafKejang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSistemSarafKejangKeyPressed(evt);
            }
        });
        FormInput.add(KetSistemSarafKejang);
        KetSistemSarafKejang.setBounds(590, 1380, 184, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Kardiovaskuler :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(50, 1440, 122, 23);

        jLabel136.setText("Pulsasi :");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(620, 1460, 70, 23);

        KardiovaskularPulsasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kuat", "Lemah", "Lain-lain" }));
        KardiovaskularPulsasi.setName("KardiovaskularPulsasi"); // NOI18N
        KardiovaskularPulsasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KardiovaskularPulsasiKeyPressed(evt);
            }
        });
        FormInput.add(KardiovaskularPulsasi);
        KardiovaskularPulsasi.setBounds(690, 1460, 96, 23);

        jLabel137.setText("Sirkulasi :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(250, 1460, 60, 23);

        KardiovaskularSirkulasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Akral Hangat", "Akral Dingin", "CRT", "Edema" }));
        KardiovaskularSirkulasi.setName("KardiovaskularSirkulasi"); // NOI18N
        KardiovaskularSirkulasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KardiovaskularSirkulasiKeyPressed(evt);
            }
        });
        FormInput.add(KardiovaskularSirkulasi);
        KardiovaskularSirkulasi.setBounds(310, 1460, 120, 23);

        KetKardiovaskularSirkulasi.setFocusTraversalPolicyProvider(true);
        KetKardiovaskularSirkulasi.setName("KetKardiovaskularSirkulasi"); // NOI18N
        KetKardiovaskularSirkulasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKardiovaskularSirkulasiKeyPressed(evt);
            }
        });
        FormInput.add(KetKardiovaskularSirkulasi);
        KetKardiovaskularSirkulasi.setBounds(430, 1460, 184, 23);

        jLabel138.setText("Denyut Nadi :");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(0, 1460, 110, 23);

        KardiovaskularDenyutNadi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teratur", "Tidak Teratur" }));
        KardiovaskularDenyutNadi.setName("KardiovaskularDenyutNadi"); // NOI18N
        KardiovaskularDenyutNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KardiovaskularDenyutNadiKeyPressed(evt);
            }
        });
        FormInput.add(KardiovaskularDenyutNadi);
        KardiovaskularDenyutNadi.setBounds(110, 1460, 120, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("Respirasi :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(50, 1490, 96, 23);

        jLabel139.setText("Retraksi :");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(630, 1510, 70, 23);

        RespirasiRetraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ringan", "Berat" }));
        RespirasiRetraksi.setName("RespirasiRetraksi"); // NOI18N
        RespirasiRetraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiRetraksiKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiRetraksi);
        RespirasiRetraksi.setBounds(700, 1510, 100, 23);

        RespirasiPolaNafas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Bradipnea", "Tachipnea" }));
        RespirasiPolaNafas.setName("RespirasiPolaNafas"); // NOI18N
        RespirasiPolaNafas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiPolaNafasKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiPolaNafas);
        RespirasiPolaNafas.setBounds(110, 1510, 102, 23);

        jLabel140.setText("Pola Nafas :");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(0, 1510, 110, 23);

        jLabel141.setText("Suara Nafas :");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(590, 1540, 80, 23);

        RespirasiSuaraNafas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vesikuler", "Wheezing", "Rhonki", "Stridor" }));
        RespirasiSuaraNafas.setName("RespirasiSuaraNafas"); // NOI18N
        RespirasiSuaraNafas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiSuaraNafasKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiSuaraNafas);
        RespirasiSuaraNafas.setBounds(670, 1540, 100, 23);

        jLabel144.setText("Jenis Pernafasaan :");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(210, 1510, 120, 23);

        RespirasiJenisPernafasan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pernafasan Dada", "Alat Bantu Pernafasaan" }));
        RespirasiJenisPernafasan.setName("RespirasiJenisPernafasan"); // NOI18N
        RespirasiJenisPernafasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiJenisPernafasanKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiJenisPernafasan);
        RespirasiJenisPernafasan.setBounds(330, 1510, 166, 23);

        KetRespirasiJenisPernafasan.setFocusTraversalPolicyProvider(true);
        KetRespirasiJenisPernafasan.setName("KetRespirasiJenisPernafasan"); // NOI18N
        KetRespirasiJenisPernafasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRespirasiJenisPernafasanKeyPressed(evt);
            }
        });
        FormInput.add(KetRespirasiJenisPernafasan);
        KetRespirasiJenisPernafasan.setBounds(500, 1510, 135, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Gastrointestinal :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(50, 1570, 129, 23);

        jLabel146.setText("Mulut :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(0, 1590, 109, 23);

        GastrointestinalMulut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Simetris", "Asimetris", "Bibir Pucat", "Lain-lain" }));
        GastrointestinalMulut.setName("GastrointestinalMulut"); // NOI18N
        GastrointestinalMulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalMulutKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalMulut);
        GastrointestinalMulut.setBounds(110, 1590, 120, 23);

        KetGastrointestinalMulut.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalMulut.setName("KetGastrointestinalMulut"); // NOI18N
        KetGastrointestinalMulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalMulutKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalMulut);
        KetGastrointestinalMulut.setBounds(240, 1590, 190, 23);

        jLabel147.setText("Lidah :");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(430, 1590, 50, 23);

        GastrointestinalLidah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Kotor", "Gerak Asimetris", "Lain-lain" }));
        GastrointestinalLidah.setName("GastrointestinalLidah"); // NOI18N
        GastrointestinalLidah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalLidahKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalLidah);
        GastrointestinalLidah.setBounds(480, 1590, 130, 23);

        KetGastrointestinalLidah.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalLidah.setName("KetGastrointestinalLidah"); // NOI18N
        KetGastrointestinalLidah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalLidahKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalLidah);
        KetGastrointestinalLidah.setBounds(620, 1590, 190, 23);

        jLabel150.setText("Tenggorokan :");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(0, 1620, 110, 23);

        GastrointestinalTenggorakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Gangguan Menelan", "Sakit Menelan", "Lain-lain" }));
        GastrointestinalTenggorakan.setName("GastrointestinalTenggorakan"); // NOI18N
        GastrointestinalTenggorakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalTenggorakanKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalTenggorakan);
        GastrointestinalTenggorakan.setBounds(110, 1620, 150, 23);

        KetGastrointestinalTenggorakan.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalTenggorakan.setName("KetGastrointestinalTenggorakan"); // NOI18N
        KetGastrointestinalTenggorakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalTenggorakanKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalTenggorakan);
        KetGastrointestinalTenggorakan.setBounds(260, 1620, 164, 23);

        jLabel151.setText("Abdomen :");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(430, 1620, 70, 23);

        GastrointestinalAbdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Supel", "Asictes", "Tegang", "BU", "Lain-lain" }));
        GastrointestinalAbdomen.setName("GastrointestinalAbdomen"); // NOI18N
        GastrointestinalAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalAbdomen);
        GastrointestinalAbdomen.setBounds(500, 1620, 150, 23);

        KetGastrointestinalAbdomen.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalAbdomen.setName("KetGastrointestinalAbdomen"); // NOI18N
        KetGastrointestinalAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalAbdomen);
        KetGastrointestinalAbdomen.setBounds(660, 1620, 164, 23);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("Neurologi :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(50, 1710, 98, 23);

        jLabel153.setText("Posisi Mata :");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(0, 1730, 109, 23);

        NeurologiMata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Simetris", "Asimetris" }));
        NeurologiMata.setName("NeurologiMata"); // NOI18N
        NeurologiMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiMataKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiMata);
        NeurologiMata.setBounds(110, 1730, 108, 23);

        jLabel156.setText("Sklera :");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(380, 1760, 50, 23);

        NeurologiSklera.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Ikterik", "Perdarahan", "Lain-lain" }));
        NeurologiSklera.setName("NeurologiSklera"); // NOI18N
        NeurologiSklera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiSkleraKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiSklera);
        NeurologiSklera.setBounds(430, 1760, 115, 23);

        KetNeurologiSklera.setFocusTraversalPolicyProvider(true);
        KetNeurologiSklera.setName("KetNeurologiSklera"); // NOI18N
        KetNeurologiSklera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNeurologiSkleraKeyPressed(evt);
            }
        });
        FormInput.add(KetNeurologiSklera);
        KetNeurologiSklera.setBounds(550, 1760, 150, 23);

        jLabel158.setText("Pendengaran :");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(30, 1790, 80, 23);

        NeurologiPendengaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Asimetris", "Keluar Cairan", "Lain-lain" }));
        NeurologiPendengaran.setName("NeurologiPendengaran"); // NOI18N
        NeurologiPendengaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiPendengaranKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiPendengaran);
        NeurologiPendengaran.setBounds(110, 1790, 117, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("Integument :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(50, 1830, 108, 23);

        jLabel160.setText("Kulit :");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(760, 1860, 40, 23);

        IntegumentKulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Rash/Kemerahan", "Luka", "Memar", "Ptekie", "Bula" }));
        IntegumentKulit.setName("IntegumentKulit"); // NOI18N
        IntegumentKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentKulitKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentKulit);
        IntegumentKulit.setBounds(800, 1860, 134, 23);

        jLabel161.setText("Warna Kulit :");
        jLabel161.setName("jLabel161"); // NOI18N
        FormInput.add(jLabel161);
        jLabel161.setBounds(40, 1860, 70, 23);

        IntegumentWarnaKulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Pucat", "Sianosis", "Lain-lain" }));
        IntegumentWarnaKulit.setName("IntegumentWarnaKulit"); // NOI18N
        IntegumentWarnaKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentWarnaKulitKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentWarnaKulit);
        IntegumentWarnaKulit.setBounds(110, 1860, 92, 23);

        jLabel162.setText("Turgor :");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(620, 1860, 48, 23);

        IntegumentTurgor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Sedang", "Buruk" }));
        IntegumentTurgor.setName("IntegumentTurgor"); // NOI18N
        IntegumentTurgor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentTurgorKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentTurgor);
        IntegumentTurgor.setBounds(670, 1860, 86, 23);

        IntegumentVernic.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Lain-lain" }));
        IntegumentVernic.setName("IntegumentVernic"); // NOI18N
        IntegumentVernic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentVernicKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentVernic);
        IntegumentVernic.setBounds(290, 1860, 100, 23);

        jLabel163.setText("Vernic kaseosa :");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(200, 1860, 90, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Reproduksi :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(50, 1900, 122, 23);

        jLabel164.setText("Laki-laki :");
        jLabel164.setName("jLabel164"); // NOI18N
        FormInput.add(jLabel164);
        jLabel164.setBounds(0, 1920, 109, 23);

        ReproduksiLaki.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Hipospadia", "Epispadia", "Fimosis", "Hidrokel", "Lain-lain" }));
        ReproduksiLaki.setName("ReproduksiLaki"); // NOI18N
        ReproduksiLaki.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReproduksiLakiKeyPressed(evt);
            }
        });
        FormInput.add(ReproduksiLaki);
        ReproduksiLaki.setBounds(110, 1920, 100, 23);

        KetReproduksiLaki.setFocusTraversalPolicyProvider(true);
        KetReproduksiLaki.setName("KetReproduksiLaki"); // NOI18N
        KetReproduksiLaki.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetReproduksiLakiKeyPressed(evt);
            }
        });
        FormInput.add(KetReproduksiLaki);
        KetReproduksiLaki.setBounds(220, 1920, 220, 23);

        KetReproduksiPerempuan.setFocusTraversalPolicyProvider(true);
        KetReproduksiPerempuan.setName("KetReproduksiPerempuan"); // NOI18N
        KetReproduksiPerempuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetReproduksiPerempuanKeyPressed(evt);
            }
        });
        FormInput.add(KetReproduksiPerempuan);
        KetReproduksiPerempuan.setBounds(220, 1950, 220, 23);

        ReproduksiPerempuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Keputihan", "Vagina skintag", "Lain-lain" }));
        ReproduksiPerempuan.setName("ReproduksiPerempuan"); // NOI18N
        ReproduksiPerempuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReproduksiPerempuanKeyPressed(evt);
            }
        });
        FormInput.add(ReproduksiPerempuan);
        ReproduksiPerempuan.setBounds(110, 1950, 100, 23);

        jLabel165.setText("Perempuan :");
        jLabel165.setName("jLabel165"); // NOI18N
        FormInput.add(jLabel165);
        jLabel165.setBounds(0, 1950, 109, 23);

        jLabel167.setText("Rekoil Telinga :");
        jLabel167.setName("jLabel167"); // NOI18N
        FormInput.add(jLabel167);
        jLabel167.setBounds(0, 2040, 109, 23);

        MuskuloskletalRekoil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rekoil lambat", "Rekoil cepat", "Rekoil segera", "Lain-lain" }));
        MuskuloskletalRekoil.setName("MuskuloskletalRekoil"); // NOI18N
        MuskuloskletalRekoil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskuloskletalRekoilKeyPressed(evt);
            }
        });
        FormInput.add(MuskuloskletalRekoil);
        MuskuloskletalRekoil.setBounds(110, 2040, 150, 23);

        MuskuloskletalGaris.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tipis", "Garis transversal anterior", "Garis 2/3 anterior", "Seluruh telapak kaki" }));
        MuskuloskletalGaris.setName("MuskuloskletalGaris"); // NOI18N
        MuskuloskletalGaris.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskuloskletalGarisKeyPressed(evt);
            }
        });
        FormInput.add(MuskuloskletalGaris);
        MuskuloskletalGaris.setBounds(370, 2040, 140, 23);

        jLabel168.setText("Garis Telapak Kaki :");
        jLabel168.setName("jLabel168"); // NOI18N
        FormInput.add(jLabel168);
        jLabel168.setBounds(270, 2040, 100, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 2080, 880, 1);

        jLabel189.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel189.setText("III. RIWAYAT PSIKOLOGIS – SOSIAL – EKONOMI – BUDAYA – SPIRITUAL");
        jLabel189.setName("jLabel189"); // NOI18N
        FormInput.add(jLabel189);
        jLabel189.setBounds(10, 2080, 490, 23);

        jLabel190.setText("a. Kondisi Psikologis :");
        jLabel190.setName("jLabel190"); // NOI18N
        FormInput.add(jLabel190);
        jLabel190.setBounds(0, 2100, 149, 23);

        KondisiPsikologis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Masalah", "Marah", "Takut", "Depresi", "Cepat Lelah", "Cemas", "Gelisah", "Sulit Tidur", "Lain-lain" }));
        KondisiPsikologis.setName("KondisiPsikologis"); // NOI18N
        KondisiPsikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiPsikologisKeyPressed(evt);
            }
        });
        FormInput.add(KondisiPsikologis);
        KondisiPsikologis.setBounds(150, 2100, 142, 23);

        jLabel191.setText("b. Adakah Perilaku :");
        jLabel191.setName("jLabel191"); // NOI18N
        FormInput.add(jLabel191);
        jLabel191.setBounds(300, 2100, 110, 23);

        AdakahPerilaku.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Masalah", "Perilaku Kekerasan", "Gangguan Efek", "Gangguan Memori", "Halusinasi", "Kecenderungan Percobaan Bunuh Diri", "Lain-lain" }));
        AdakahPerilaku.setName("AdakahPerilaku"); // NOI18N
        AdakahPerilaku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AdakahPerilakuKeyPressed(evt);
            }
        });
        FormInput.add(AdakahPerilaku);
        AdakahPerilaku.setBounds(420, 2100, 235, 23);

        KeteranganAdakahPerilaku.setFocusTraversalPolicyProvider(true);
        KeteranganAdakahPerilaku.setName("KeteranganAdakahPerilaku"); // NOI18N
        KeteranganAdakahPerilaku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAdakahPerilakuKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAdakahPerilaku);
        KeteranganAdakahPerilaku.setBounds(660, 2100, 202, 23);

        jLabel192.setText("c. Gangguan Jiwa di Masa Lalu :");
        jLabel192.setName("jLabel192"); // NOI18N
        FormInput.add(jLabel192);
        jLabel192.setBounds(0, 2130, 199, 23);

        GangguanJiwa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        GangguanJiwa.setName("GangguanJiwa"); // NOI18N
        GangguanJiwa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GangguanJiwaKeyPressed(evt);
            }
        });
        FormInput.add(GangguanJiwa);
        GangguanJiwa.setBounds(200, 2130, 77, 23);

        jLabel193.setText("d. Hubungan Pasien dengan Anggota Keluarga :");
        jLabel193.setName("jLabel193"); // NOI18N
        FormInput.add(jLabel193);
        jLabel193.setBounds(290, 2130, 240, 23);

        HubunganAnggotaKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Harmonis", "Kurang Harmonis", "Tidak Harmonis", "Konflik Besar" }));
        HubunganAnggotaKeluarga.setName("HubunganAnggotaKeluarga"); // NOI18N
        HubunganAnggotaKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganAnggotaKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(HubunganAnggotaKeluarga);
        HubunganAnggotaKeluarga.setBounds(530, 2130, 133, 23);

        jLabel194.setText("e. Agama :");
        jLabel194.setName("jLabel194"); // NOI18N
        FormInput.add(jLabel194);
        jLabel194.setBounds(670, 2130, 60, 23);

        Agama.setEditable(false);
        Agama.setFocusTraversalPolicyProvider(true);
        Agama.setName("Agama"); // NOI18N
        FormInput.add(Agama);
        Agama.setBounds(740, 2130, 120, 23);

        jLabel195.setText("f. Tinggal Dengan :");
        jLabel195.setName("jLabel195"); // NOI18N
        FormInput.add(jLabel195);
        jLabel195.setBounds(0, 2160, 137, 23);

        TinggalDengan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sendiri", "Orang Tua", "Suami/Istri", "Keluarga", "Lain-lain" }));
        TinggalDengan.setName("TinggalDengan"); // NOI18N
        TinggalDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalDenganKeyPressed(evt);
            }
        });
        FormInput.add(TinggalDengan);
        TinggalDengan.setBounds(140, 2160, 105, 23);

        KeteranganTinggalDengan.setFocusTraversalPolicyProvider(true);
        KeteranganTinggalDengan.setName("KeteranganTinggalDengan"); // NOI18N
        KeteranganTinggalDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTinggalDenganKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTinggalDengan);
        KeteranganTinggalDengan.setBounds(250, 2160, 137, 23);

        jLabel196.setText("g. Pekerjaan :");
        jLabel196.setName("jLabel196"); // NOI18N
        FormInput.add(jLabel196);
        jLabel196.setBounds(390, 2160, 83, 23);

        PekerjaanPasien.setEditable(false);
        PekerjaanPasien.setFocusTraversalPolicyProvider(true);
        PekerjaanPasien.setName("PekerjaanPasien"); // NOI18N
        FormInput.add(PekerjaanPasien);
        PekerjaanPasien.setBounds(480, 2160, 140, 23);

        jLabel197.setText("h. Pembayaran :");
        jLabel197.setName("jLabel197"); // NOI18N
        FormInput.add(jLabel197);
        jLabel197.setBounds(620, 2160, 90, 23);

        CaraBayar.setEditable(false);
        CaraBayar.setFocusTraversalPolicyProvider(true);
        CaraBayar.setName("CaraBayar"); // NOI18N
        FormInput.add(CaraBayar);
        CaraBayar.setBounds(720, 2160, 140, 23);

        jLabel198.setText("i. Nilai-nilai Kepercayaan/Budaya Yang Perlu Diperhatikan :");
        jLabel198.setName("jLabel198"); // NOI18N
        FormInput.add(jLabel198);
        jLabel198.setBounds(0, 2190, 331, 23);

        NilaiKepercayaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        NilaiKepercayaan.setName("NilaiKepercayaan"); // NOI18N
        NilaiKepercayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiKepercayaanKeyPressed(evt);
            }
        });
        FormInput.add(NilaiKepercayaan);
        NilaiKepercayaan.setBounds(340, 2190, 105, 23);

        KeteranganNilaiKepercayaan.setFocusTraversalPolicyProvider(true);
        KeteranganNilaiKepercayaan.setName("KeteranganNilaiKepercayaan"); // NOI18N
        KeteranganNilaiKepercayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganNilaiKepercayaanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganNilaiKepercayaan);
        KeteranganNilaiKepercayaan.setBounds(450, 2190, 160, 23);

        jLabel199.setText("j. Bahasa Sehari-hari :");
        jLabel199.setName("jLabel199"); // NOI18N
        FormInput.add(jLabel199);
        jLabel199.setBounds(610, 2190, 120, 23);

        Bahasa.setEditable(false);
        Bahasa.setFocusTraversalPolicyProvider(true);
        Bahasa.setName("Bahasa"); // NOI18N
        FormInput.add(Bahasa);
        Bahasa.setBounds(740, 2190, 120, 23);

        jLabel200.setText("k. Pendidikan Pasien :");
        jLabel200.setName("jLabel200"); // NOI18N
        FormInput.add(jLabel200);
        jLabel200.setBounds(0, 2220, 150, 23);

        PendidikanPasien.setEditable(false);
        PendidikanPasien.setFocusTraversalPolicyProvider(true);
        PendidikanPasien.setName("PendidikanPasien"); // NOI18N
        FormInput.add(PendidikanPasien);
        PendidikanPasien.setBounds(160, 2220, 100, 23);

        jLabel201.setText("l. Pendidikan P.J. :");
        jLabel201.setName("jLabel201"); // NOI18N
        FormInput.add(jLabel201);
        jLabel201.setBounds(260, 2220, 100, 23);

        PendidikanPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "TS", "TK", "SD", "SMP", "SMA", "SLTA/SEDERAJAT", "D1", "D2", "D3", "D4", "S1", "S2", "S3" }));
        PendidikanPJ.setName("PendidikanPJ"); // NOI18N
        PendidikanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendidikanPJKeyPressed(evt);
            }
        });
        FormInput.add(PendidikanPJ);
        PendidikanPJ.setBounds(360, 2220, 135, 23);

        jLabel202.setText("m. Edukasi Diberikan Kepada :");
        jLabel202.setName("jLabel202"); // NOI18N
        FormInput.add(jLabel202);
        jLabel202.setBounds(490, 2220, 160, 23);

        EdukasiPsikolgis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasien", "Keluarga" }));
        EdukasiPsikolgis.setName("EdukasiPsikolgis"); // NOI18N
        EdukasiPsikolgis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiPsikolgisKeyPressed(evt);
            }
        });
        FormInput.add(EdukasiPsikolgis);
        EdukasiPsikolgis.setBounds(660, 2220, 95, 23);

        KeteranganEdukasiPsikologis.setFocusTraversalPolicyProvider(true);
        KeteranganEdukasiPsikologis.setName("KeteranganEdukasiPsikologis"); // NOI18N
        KeteranganEdukasiPsikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEdukasiPsikologisKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEdukasiPsikologis);
        KeteranganEdukasiPsikologis.setBounds(760, 2220, 99, 23);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 2280, 880, 1);

        jLabel203.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel203.setText("IV. PENILAIAN TINGKAT NYERI");
        jLabel203.setName("jLabel203"); // NOI18N
        FormInput.add(jLabel203);
        jLabel203.setBounds(10, 2280, 380, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 2520, 880, 1);

        jLabel271.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel271.setText("V. SKRINING GIZI");
        jLabel271.setName("jLabel271"); // NOI18N
        FormInput.add(jLabel271);
        jLabel271.setBounds(10, 2520, 380, 23);

        jLabel272.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel272.setText("1. Masalah Minum (ASI/PASI) ?");
        jLabel272.setName("jLabel272"); // NOI18N
        FormInput.add(jLabel272);
        jLabel272.setBounds(50, 2540, 380, 23);

        SkalaGizi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        SkalaGizi1.setName("SkalaGizi1"); // NOI18N
        SkalaGizi1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaGizi1ItemStateChanged(evt);
            }
        });
        SkalaGizi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaGizi1KeyPressed(evt);
            }
        });
        FormInput.add(SkalaGizi1);
        SkalaGizi1.setBounds(430, 2540, 320, 23);

        jLabel273.setText("Skor :");
        jLabel273.setName("jLabel273"); // NOI18N
        FormInput.add(jLabel273);
        jLabel273.setBounds(750, 2540, 40, 23);

        NilaiGizi1.setEditable(false);
        NilaiGizi1.setFocusTraversalPolicyProvider(true);
        NilaiGizi1.setName("NilaiGizi1"); // NOI18N
        FormInput.add(NilaiGizi1);
        NilaiGizi1.setBounds(800, 2540, 60, 23);

        jLabel274.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel274.setText("2. Penurunan berat badan > 10% dari BBL (berat badan lahir) ?");
        jLabel274.setName("jLabel274"); // NOI18N
        FormInput.add(jLabel274);
        jLabel274.setBounds(50, 2570, 380, 23);

        SkalaGizi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        SkalaGizi2.setName("SkalaGizi2"); // NOI18N
        SkalaGizi2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaGizi2ItemStateChanged(evt);
            }
        });
        SkalaGizi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaGizi2KeyPressed(evt);
            }
        });
        FormInput.add(SkalaGizi2);
        SkalaGizi2.setBounds(430, 2570, 320, 23);

        jLabel275.setText("Skor :");
        jLabel275.setName("jLabel275"); // NOI18N
        FormInput.add(jLabel275);
        jLabel275.setBounds(750, 2570, 40, 23);

        NilaiGizi2.setEditable(false);
        NilaiGizi2.setFocusTraversalPolicyProvider(true);
        NilaiGizi2.setName("NilaiGizi2"); // NOI18N
        FormInput.add(NilaiGizi2);
        NilaiGizi2.setBounds(800, 2570, 60, 23);

        jLabel276.setText("Total Skor :");
        jLabel276.setName("jLabel276"); // NOI18N
        FormInput.add(jLabel276);
        jLabel276.setBounds(680, 2630, 110, 23);

        NilaiGiziTotal.setEditable(false);
        NilaiGiziTotal.setFocusTraversalPolicyProvider(true);
        NilaiGiziTotal.setName("NilaiGiziTotal"); // NOI18N
        FormInput.add(NilaiGiziTotal);
        NilaiGiziTotal.setBounds(800, 2630, 60, 23);

        jLabel278.setText("Jika skor ≥ 2 lakukan asesmen gizi lanjutan oleh dokter Spesialis Gizi Klinik atau ahli gizi");
        jLabel278.setName("jLabel278"); // NOI18N
        FormInput.add(jLabel278);
        jLabel278.setBounds(290, 2630, 430, 23);

        DiketahuiDietisen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        DiketahuiDietisen.setName("DiketahuiDietisen"); // NOI18N
        DiketahuiDietisen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiketahuiDietisenKeyPressed(evt);
            }
        });
        FormInput.add(DiketahuiDietisen);
        DiketahuiDietisen.setBounds(670, 2660, 80, 23);

        jLabel279.setText("Jam  :");
        jLabel279.setName("jLabel279"); // NOI18N
        FormInput.add(jLabel279);
        jLabel279.setBounds(750, 2660, 40, 23);

        KeteranganDiketahuiDietisen.setFocusTraversalPolicyProvider(true);
        KeteranganDiketahuiDietisen.setName("KeteranganDiketahuiDietisen"); // NOI18N
        KeteranganDiketahuiDietisen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganDiketahuiDietisenKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganDiketahuiDietisen);
        KeteranganDiketahuiDietisen.setBounds(800, 2660, 60, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 2700, 880, 1);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbMasalahKeperawatan.setName("tbMasalahKeperawatan"); // NOI18N
        tbMasalahKeperawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMasalahKeperawatanMouseClicked(evt);
            }
        });
        tbMasalahKeperawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbMasalahKeperawatanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbMasalahKeperawatanKeyReleased(evt);
            }
        });
        Scroll6.setViewportView(tbMasalahKeperawatan);

        FormInput.add(Scroll6);
        Scroll6.setBounds(10, 2710, 400, 143);

        BtnTambahMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahMasalah.setMnemonic('3');
        BtnTambahMasalah.setToolTipText("Alt+3");
        BtnTambahMasalah.setName("BtnTambahMasalah"); // NOI18N
        BtnTambahMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahMasalahActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahMasalah);
        BtnTambahMasalah.setBounds(360, 2860, 28, 23);

        BtnAllMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllMasalah.setMnemonic('2');
        BtnAllMasalah.setToolTipText("2Alt+2");
        BtnAllMasalah.setName("BtnAllMasalah"); // NOI18N
        BtnAllMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllMasalahActionPerformed(evt);
            }
        });
        BtnAllMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllMasalahKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllMasalah);
        BtnAllMasalah.setBounds(330, 2860, 28, 23);

        BtnCariMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariMasalah.setMnemonic('1');
        BtnCariMasalah.setToolTipText("Alt+1");
        BtnCariMasalah.setName("BtnCariMasalah"); // NOI18N
        BtnCariMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariMasalahActionPerformed(evt);
            }
        });
        BtnCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariMasalah);
        BtnCariMasalah.setBounds(300, 2860, 28, 23);

        TCariMasalah.setToolTipText("Alt+C");
        TCariMasalah.setName("TCariMasalah"); // NOI18N
        TCariMasalah.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(TCariMasalah);
        TCariMasalah.setBounds(80, 2860, 215, 23);

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label12);
        label12.setBounds(20, 2860, 60, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Rencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Rencana.setColumns(20);
        Rencana.setRows(5);
        Rencana.setName("Rencana"); // NOI18N
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Rencana);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(470, 2730, 400, 143);

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
        BtnDokter1.setBounds(430, 2730, 28, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Rencana Keperawatan :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(470, 2710, 120, 23);

        jLabel59.setText(" G :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(260, 210, 20, 23);

        G.setFocusTraversalPolicyProvider(true);
        G.setName("G"); // NOI18N
        G.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GKeyPressed(evt);
            }
        });
        FormInput.add(G);
        G.setBounds(280, 210, 50, 23);

        jLabel60.setText("Riwayat Prenatal :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(6, 210, 170, 23);

        jLabel61.setText("Riwayat Penyakit Ibu :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(177, 240, 120, 23);

        jLabel62.setText(" P :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(330, 210, 20, 23);

        P.setFocusTraversalPolicyProvider(true);
        P.setName("P"); // NOI18N
        P.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PKeyPressed(evt);
            }
        });
        FormInput.add(P);
        P.setBounds(350, 210, 50, 23);

        jLabel63.setText(" A :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(400, 210, 20, 23);

        A.setFocusTraversalPolicyProvider(true);
        A.setName("A"); // NOI18N
        A.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AKeyPressed(evt);
            }
        });
        FormInput.add(A);
        A.setBounds(420, 210, 50, 23);

        jLabel64.setText(" UK :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(470, 210, 30, 23);

        UK.setFocusTraversalPolicyProvider(true);
        UK.setName("UK"); // NOI18N
        UK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UKKeyPressed(evt);
            }
        });
        FormInput.add(UK);
        UK.setBounds(500, 210, 50, 23);

        jLabel65.setText("Diagnosa Ibu :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(180, 210, 80, 23);

        jLabel66.setText("Riwayat Pengobatan Ibu Selama Hamil :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(175, 270, 210, 23);

        RPO.setFocusTraversalPolicyProvider(true);
        RPO.setName("RPO"); // NOI18N
        RPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOKeyPressed(evt);
            }
        });
        FormInput.add(RPO);
        RPO.setBounds(400, 270, 300, 23);

        jLabel67.setText("Indikasi :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(390, 300, 60, 23);

        RD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RD.setName("RD"); // NOI18N
        RD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RDKeyPressed(evt);
            }
        });
        FormInput.add(RD);
        RD.setBounds(280, 300, 100, 23);

        Indikasi.setFocusTraversalPolicyProvider(true);
        Indikasi.setName("Indikasi"); // NOI18N
        Indikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IndikasiKeyPressed(evt);
            }
        });
        FormInput.add(Indikasi);
        Indikasi.setBounds(460, 300, 240, 23);

        jLabel68.setText("Riwayat Dirawat :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(183, 300, 90, 23);

        GiziIbu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Buruk" }));
        GiziIbu.setName("GiziIbu"); // NOI18N
        GiziIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GiziIbuKeyPressed(evt);
            }
        });
        FormInput.add(GiziIbu);
        GiziIbu.setBounds(280, 330, 100, 23);

        jLabel69.setText("Status Gizi Ibu :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(175, 330, 90, 23);

        Cara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Vacum Ekstraksi", "Forcep Ekstraksi", "Sectio Caesarea", "Lainnya" }));
        Cara.setName("Cara"); // NOI18N
        Cara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraKeyPressed(evt);
            }
        });
        FormInput.add(Cara);
        Cara.setBounds(270, 430, 150, 23);

        KetCara.setFocusTraversalPolicyProvider(true);
        KetCara.setName("KetCara"); // NOI18N
        KetCara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetCaraKeyPressed(evt);
            }
        });
        FormInput.add(KetCara);
        KetCara.setBounds(430, 430, 300, 23);

        jLabel70.setText("cm");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(690, 550, 20, 23);

        BB1.setFocusTraversalPolicyProvider(true);
        BB1.setName("BB1"); // NOI18N
        BB1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BB1KeyPressed(evt);
            }
        });
        FormInput.add(BB1);
        BB1.setBounds(310, 550, 50, 23);

        jLabel71.setText("Riwayat Intranatal :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(10, 370, 170, 23);

        jLabel72.setText("Cara Persalinan :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(177, 430, 90, 23);

        jLabel73.setText(" PB :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(390, 550, 30, 23);

        PB.setFocusTraversalPolicyProvider(true);
        PB.setName("PB"); // NOI18N
        PB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PBKeyPressed(evt);
            }
        });
        FormInput.add(PB);
        PB.setBounds(420, 550, 50, 23);

        jLabel74.setText(" LK :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(500, 550, 30, 23);

        LK.setFocusTraversalPolicyProvider(true);
        LK.setName("LK"); // NOI18N
        LK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LKKeyPressed(evt);
            }
        });
        FormInput.add(LK);
        LK.setBounds(530, 550, 50, 23);

        jLabel75.setText("LD :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(610, 550, 30, 23);

        LD.setFocusTraversalPolicyProvider(true);
        LD.setName("LD"); // NOI18N
        LD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LDKeyPressed(evt);
            }
        });
        FormInput.add(LD);
        LD.setBounds(640, 550, 50, 23);

        jLabel76.setText("Antopometri BBL :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(175, 550, 100, 23);

        jLabel77.setText("Letak :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(180, 460, 40, 23);

        Letak.setFocusTraversalPolicyProvider(true);
        Letak.setName("Letak"); // NOI18N
        Letak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LetakKeyPressed(evt);
            }
        });
        FormInput.add(Letak);
        Letak.setBounds(230, 460, 500, 23);

        Ketuban.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jernih", "Darah", "Putih Keruh", "Hijau", "Meconium" }));
        Ketuban.setName("Ketuban"); // NOI18N
        Ketuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetubanKeyPressed(evt);
            }
        });
        FormInput.add(Ketuban);
        Ketuban.setBounds(240, 490, 150, 23);

        jLabel81.setText("Ketuban :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(175, 490, 60, 23);

        Tali.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Segar", "Layu", "Simpul" }));
        Tali.setName("Tali"); // NOI18N
        Tali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TaliKeyPressed(evt);
            }
        });
        FormInput.add(Tali);
        Tali.setBounds(250, 520, 140, 23);

        jLabel82.setText("Tali Pusat :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(180, 520, 60, 23);

        label13.setText("Tanggal Lahir :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label13);
        label13.setBounds(180, 400, 80, 23);

        TanggalLahir.setForeground(new java.awt.Color(50, 70, 50));
        TanggalLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-01-2023 14:02:24" }));
        TanggalLahir.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalLahir.setName("TanggalLahir"); // NOI18N
        TanggalLahir.setOpaque(false);
        TanggalLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalLahirKeyPressed(evt);
            }
        });
        FormInput.add(TanggalLahir);
        TanggalLahir.setBounds(270, 400, 150, 23);

        jLabel83.setText("Kondisi Saat Lahir:");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(420, 400, 100, 23);

        Kondisi.setFocusTraversalPolicyProvider(true);
        Kondisi.setName("Kondisi"); // NOI18N
        Kondisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKeyPressed(evt);
            }
        });
        FormInput.add(Kondisi);
        Kondisi.setBounds(520, 400, 130, 23);

        jLabel84.setText("APGAR Score :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(660, 400, 71, 23);

        Apgar.setFocusTraversalPolicyProvider(true);
        Apgar.setName("Apgar"); // NOI18N
        Apgar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ApgarKeyPressed(evt);
            }
        });
        FormInput.add(Apgar);
        Apgar.setBounds(730, 400, 110, 23);

        jLabel78.setText(" BB :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(280, 550, 30, 23);

        jLabel85.setText("gr");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(360, 550, 20, 23);

        jLabel86.setText("cm");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(470, 550, 20, 23);

        jLabel87.setText("cm");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(580, 550, 20, 23);

        jLabel88.setText("X");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(670, 660, 10, 23);

        jLabel89.setText("LP :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(720, 550, 30, 23);

        LP.setFocusTraversalPolicyProvider(true);
        LP.setName("LP"); // NOI18N
        LP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LPKeyPressed(evt);
            }
        });
        FormInput.add(LP);
        LP.setBounds(750, 550, 50, 23);

        jLabel90.setText(" G :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(260, 370, 20, 23);

        G1.setFocusTraversalPolicyProvider(true);
        G1.setName("G1"); // NOI18N
        G1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G1KeyPressed(evt);
            }
        });
        FormInput.add(G1);
        G1.setBounds(280, 370, 50, 23);

        jLabel91.setText(" P :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(330, 370, 20, 23);

        P1.setFocusTraversalPolicyProvider(true);
        P1.setName("P1"); // NOI18N
        P1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                P1KeyPressed(evt);
            }
        });
        FormInput.add(P1);
        P1.setBounds(350, 370, 50, 23);

        jLabel92.setText(" A :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(400, 370, 20, 23);

        A1.setFocusTraversalPolicyProvider(true);
        A1.setName("A1"); // NOI18N
        A1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A1KeyPressed(evt);
            }
        });
        FormInput.add(A1);
        A1.setBounds(420, 370, 50, 23);

        jLabel93.setText(" UK :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(470, 370, 30, 23);

        UK1.setFocusTraversalPolicyProvider(true);
        UK1.setName("UK1"); // NOI18N
        UK1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UK1KeyPressed(evt);
            }
        });
        FormInput.add(UK1);
        UK1.setBounds(500, 370, 50, 23);

        jLabel96.setText("Diagnosa Ibu :");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(180, 370, 80, 23);

        Mayor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ibu demam > 38 c", "KPD > 24 Jam", "Ketuban Hijau", "Korioamniotis", "Fetal distress" }));
        Mayor.setName("Mayor"); // NOI18N
        Mayor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MayorKeyPressed(evt);
            }
        });
        FormInput.add(Mayor);
        Mayor.setBounds(230, 590, 170, 23);

        jLabel98.setText("Faktor Resiko Infeksi :");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(10, 590, 170, 23);

        jLabel99.setText("Mayor :");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(180, 590, 50, 23);

        Minor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KPD > 12 Jam", "Asfiksia", "BBLR", "ISK", "UK < 37 mgg", "Gemeli", "Keputihan", "Ibu temp > 37 c" }));
        Minor.setName("Minor"); // NOI18N
        Minor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MinorKeyPressed(evt);
            }
        });
        FormInput.add(Minor);
        Minor.setBounds(230, 620, 170, 23);

        jLabel100.setText("Minor :");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(180, 620, 50, 23);

        Nutrisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ASI", "Lainnya" }));
        Nutrisi.setName("Nutrisi"); // NOI18N
        Nutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NutrisiKeyPressed(evt);
            }
        });
        FormInput.add(Nutrisi);
        Nutrisi.setBounds(230, 660, 100, 23);

        jLabel101.setText("Kebutuhan Biologis :");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 660, 170, 23);

        jLabel102.setText("Nutrisi :");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(180, 660, 50, 23);

        jLabel103.setText("Eliminasi :");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(183, 690, 60, 23);

        KetNutrisi.setFocusTraversalPolicyProvider(true);
        KetNutrisi.setName("KetNutrisi"); // NOI18N
        KetNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(KetNutrisi);
        KetNutrisi.setBounds(340, 660, 130, 23);

        jLabel97.setText("Frekuensi :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(470, 660, 70, 23);

        Kali.setFocusTraversalPolicyProvider(true);
        Kali.setName("Kali"); // NOI18N
        Kali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KaliKeyPressed(evt);
            }
        });
        FormInput.add(Kali);
        Kali.setBounds(620, 660, 50, 23);

        jLabel104.setText("cm");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(800, 550, 20, 23);

        Frekuensi.setFocusTraversalPolicyProvider(true);
        Frekuensi.setName("Frekuensi"); // NOI18N
        Frekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiKeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi);
        Frekuensi.setBounds(540, 660, 50, 23);

        jLabel105.setText("cc/");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(590, 660, 20, 23);

        jLabel106.setText(" BAK, Keluhan:");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(240, 690, 80, 23);

        KeluhanBAK.setFocusTraversalPolicyProvider(true);
        KeluhanBAK.setName("KeluhanBAK"); // NOI18N
        KeluhanBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanBAKKeyPressed(evt);
            }
        });
        FormInput.add(KeluhanBAK);
        KeluhanBAK.setBounds(330, 690, 250, 23);

        jLabel107.setText(" BAB, Keluhan:");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(240, 720, 80, 23);

        KeluhanBAB.setFocusTraversalPolicyProvider(true);
        KeluhanBAB.setName("KeluhanBAB"); // NOI18N
        KeluhanBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanBABKeyPressed(evt);
            }
        });
        FormInput.add(KeluhanBAB);
        KeluhanBAB.setBounds(330, 720, 250, 23);

        Alergi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Alergi", "Tidak Diketahui" }));
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(190, 760, 130, 23);

        jLabel109.setText("Alergi/Reaksi (Pada Orang Tua) :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 760, 170, 23);

        jLabel111.setText("Alergi Obat, Sebutkan :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(185, 790, 120, 23);

        AlergiObat.setFocusTraversalPolicyProvider(true);
        AlergiObat.setName("AlergiObat"); // NOI18N
        AlergiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiObatKeyPressed(evt);
            }
        });
        FormInput.add(AlergiObat);
        AlergiObat.setBounds(330, 790, 240, 23);

        jLabel282.setText("Reaksi:");
        jLabel282.setName("jLabel282"); // NOI18N
        FormInput.add(jLabel282);
        jLabel282.setBounds(570, 790, 50, 23);

        ReaksiObat.setFocusTraversalPolicyProvider(true);
        ReaksiObat.setName("ReaksiObat"); // NOI18N
        ReaksiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiObatKeyPressed(evt);
            }
        });
        FormInput.add(ReaksiObat);
        ReaksiObat.setBounds(630, 790, 200, 23);

        jLabel112.setText("Alergi Makanan, Sebutkan :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(185, 820, 140, 23);

        AlergiMakanan.setFocusTraversalPolicyProvider(true);
        AlergiMakanan.setName("AlergiMakanan"); // NOI18N
        AlergiMakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiMakananKeyPressed(evt);
            }
        });
        FormInput.add(AlergiMakanan);
        AlergiMakanan.setBounds(330, 820, 240, 23);

        jLabel283.setText("Reaksi:");
        jLabel283.setName("jLabel283"); // NOI18N
        FormInput.add(jLabel283);
        jLabel283.setBounds(570, 820, 50, 23);

        ReaksiMakanan.setFocusTraversalPolicyProvider(true);
        ReaksiMakanan.setName("ReaksiMakanan"); // NOI18N
        ReaksiMakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiMakananKeyPressed(evt);
            }
        });
        FormInput.add(ReaksiMakanan);
        ReaksiMakanan.setBounds(630, 820, 200, 23);

        jLabel280.setText("Alergi Lainnya, Sebutkan :");
        jLabel280.setName("jLabel280"); // NOI18N
        FormInput.add(jLabel280);
        jLabel280.setBounds(180, 850, 140, 23);

        AlergiLainnya.setFocusTraversalPolicyProvider(true);
        AlergiLainnya.setName("AlergiLainnya"); // NOI18N
        AlergiLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(AlergiLainnya);
        AlergiLainnya.setBounds(330, 850, 240, 23);

        jLabel284.setText("Reaksi:");
        jLabel284.setName("jLabel284"); // NOI18N
        FormInput.add(jLabel284);
        jLabel284.setBounds(570, 850, 50, 23);

        ReaksiLainnya.setFocusTraversalPolicyProvider(true);
        ReaksiLainnya.setName("ReaksiLainnya"); // NOI18N
        ReaksiLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(ReaksiLainnya);
        ReaksiLainnya.setBounds(630, 850, 200, 23);

        KetObat.setFocusTraversalPolicyProvider(true);
        KetObat.setName("KetObat"); // NOI18N
        KetObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetObatKeyPressed(evt);
            }
        });
        FormInput.add(KetObat);
        KetObat.setBounds(450, 990, 170, 23);

        jLabel108.setText("Downes Score :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(620, 1170, 90, 23);

        Downes.setFocusTraversalPolicyProvider(true);
        Downes.setName("Downes"); // NOI18N
        Downes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DownesKeyPressed(evt);
            }
        });
        FormInput.add(Downes);
        Downes.setBounds(710, 1170, 175, 23);

        jLabel110.setText("cm");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(500, 1200, 20, 23);

        jLabel281.setText(" LK :");
        jLabel281.setName("jLabel281"); // NOI18N
        FormInput.add(jLabel281);
        jLabel281.setBounds(290, 1200, 30, 23);

        LK1.setFocusTraversalPolicyProvider(true);
        LK1.setName("LK1"); // NOI18N
        LK1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LK1KeyPressed(evt);
            }
        });
        FormInput.add(LK1);
        LK1.setBounds(330, 1200, 50, 23);

        jLabel285.setText("LD :");
        jLabel285.setName("jLabel285"); // NOI18N
        FormInput.add(jLabel285);
        jLabel285.setBounds(410, 1200, 30, 23);

        LD1.setFocusTraversalPolicyProvider(true);
        LD1.setName("LD1"); // NOI18N
        LD1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LD1KeyPressed(evt);
            }
        });
        FormInput.add(LD1);
        LD1.setBounds(450, 1200, 50, 23);

        jLabel286.setText("cm");
        jLabel286.setName("jLabel286"); // NOI18N
        FormInput.add(jLabel286);
        jLabel286.setBounds(380, 1200, 20, 23);

        jLabel287.setText("LP :");
        jLabel287.setName("jLabel287"); // NOI18N
        FormInput.add(jLabel287);
        jLabel287.setBounds(530, 1200, 30, 23);

        LP1.setFocusTraversalPolicyProvider(true);
        LP1.setName("LP1"); // NOI18N
        LP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LP1KeyPressed(evt);
            }
        });
        FormInput.add(LP1);
        LP1.setBounds(570, 1200, 50, 23);

        jLabel288.setText("cm");
        jLabel288.setName("jLabel288"); // NOI18N
        FormInput.add(jLabel288);
        jLabel288.setBounds(620, 1200, 20, 23);

        DarahBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "O", "AB" }));
        DarahBayi.setName("DarahBayi"); // NOI18N
        DarahBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahBayiKeyPressed(evt);
            }
        });
        FormInput.add(DarahBayi);
        DarahBayi.setBounds(200, 1230, 60, 23);

        jLabel289.setText("Golongan Darah/RH (Bayi) :");
        jLabel289.setName("jLabel289"); // NOI18N
        FormInput.add(jLabel289);
        jLabel289.setBounds(30, 1230, 160, 23);

        RhBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Positif", "Negatif" }));
        RhBayi.setName("RhBayi"); // NOI18N
        RhBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RhBayiKeyPressed(evt);
            }
        });
        FormInput.add(RhBayi);
        RhBayi.setBounds(310, 1230, 110, 23);

        jLabel290.setText("Rh :");
        jLabel290.setName("jLabel290"); // NOI18N
        FormInput.add(jLabel290);
        jLabel290.setBounds(260, 1230, 40, 23);

        DarahIbu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "O", "AB" }));
        DarahIbu.setName("DarahIbu"); // NOI18N
        DarahIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahIbuKeyPressed(evt);
            }
        });
        FormInput.add(DarahIbu);
        DarahIbu.setBounds(200, 1260, 60, 23);

        jLabel291.setText("Golongan Darah/RH (Ibu) :");
        jLabel291.setName("jLabel291"); // NOI18N
        FormInput.add(jLabel291);
        jLabel291.setBounds(30, 1260, 160, 23);

        RhIbu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Positif", "Negatif" }));
        RhIbu.setName("RhIbu"); // NOI18N
        RhIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RhIbuKeyPressed(evt);
            }
        });
        FormInput.add(RhIbu);
        RhIbu.setBounds(310, 1260, 110, 23);

        jLabel292.setText("Rh :");
        jLabel292.setName("jLabel292"); // NOI18N
        FormInput.add(jLabel292);
        jLabel292.setBounds(260, 1260, 40, 23);

        DarahAyah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "O", "AB" }));
        DarahAyah.setName("DarahAyah"); // NOI18N
        DarahAyah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahAyahKeyPressed(evt);
            }
        });
        FormInput.add(DarahAyah);
        DarahAyah.setBounds(200, 1290, 60, 23);

        jLabel293.setText("Golongan Darah/RH (Ayah) :");
        jLabel293.setName("jLabel293"); // NOI18N
        FormInput.add(jLabel293);
        jLabel293.setBounds(30, 1290, 160, 23);

        RhAyah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Positif", "Negatif" }));
        RhAyah.setName("RhAyah"); // NOI18N
        RhAyah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RhAyahKeyPressed(evt);
            }
        });
        FormInput.add(RhAyah);
        RhAyah.setBounds(310, 1290, 110, 23);

        jLabel294.setText("Rh :");
        jLabel294.setName("jLabel294"); // NOI18N
        FormInput.add(jLabel294);
        jLabel294.setBounds(260, 1290, 40, 23);

        jLabel295.setText("Gerak Bayi :");
        jLabel295.setName("jLabel295"); // NOI18N
        FormInput.add(jLabel295);
        jLabel295.setBounds(0, 1350, 109, 23);

        Gerak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aktif", "Tidak Aktif" }));
        Gerak.setName("Gerak"); // NOI18N
        Gerak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GerakKeyPressed(evt);
            }
        });
        FormInput.add(Gerak);
        Gerak.setBounds(110, 1350, 97, 23);

        jLabel296.setText("Ubun-ubun :");
        jLabel296.setName("jLabel296"); // NOI18N
        FormInput.add(jLabel296);
        jLabel296.setBounds(560, 1350, 70, 23);

        SistemSarafUbun.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Datar", "Cekung", "Menonjol", "Lain-lain" }));
        SistemSarafUbun.setName("SistemSarafUbun"); // NOI18N
        SistemSarafUbun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafUbunKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafUbun);
        SistemSarafUbun.setBounds(630, 1350, 97, 23);

        KetSistemSarafUbun.setFocusTraversalPolicyProvider(true);
        KetSistemSarafUbun.setName("KetSistemSarafUbun"); // NOI18N
        KetSistemSarafUbun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSistemSarafUbunKeyPressed(evt);
            }
        });
        FormInput.add(KetSistemSarafUbun);
        KetSistemSarafUbun.setBounds(730, 1350, 184, 23);

        jLabel133.setText("Refleks :");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(20, 1410, 90, 23);

        SistemSarafRefleks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Moro", "Menelan", "Hisap", "Babinski", "Rooting", "Lain-lain" }));
        SistemSarafRefleks.setName("SistemSarafRefleks"); // NOI18N
        SistemSarafRefleks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafRefleksKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafRefleks);
        SistemSarafRefleks.setBounds(110, 1410, 150, 23);

        KetSistemSarafRefleks.setFocusTraversalPolicyProvider(true);
        KetSistemSarafRefleks.setName("KetSistemSarafRefleks"); // NOI18N
        KetSistemSarafRefleks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSistemSarafRefleksKeyPressed(evt);
            }
        });
        FormInput.add(KetSistemSarafRefleks);
        KetSistemSarafRefleks.setBounds(260, 1410, 184, 23);

        jLabel297.setText("Tangis Bayi :");
        jLabel297.setName("jLabel297"); // NOI18N
        FormInput.add(jLabel297);
        jLabel297.setBounds(450, 1410, 70, 23);

        SistemSarafTangis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kuat", "Melengking", "Lain-lain" }));
        SistemSarafTangis.setName("SistemSarafTangis"); // NOI18N
        SistemSarafTangis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafTangisKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafTangis);
        SistemSarafTangis.setBounds(520, 1410, 80, 23);

        KetSistemSarafTangis.setFocusTraversalPolicyProvider(true);
        KetSistemSarafTangis.setName("KetSistemSarafTangis"); // NOI18N
        KetSistemSarafTangis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSistemSarafTangisKeyPressed(evt);
            }
        });
        FormInput.add(KetSistemSarafTangis);
        KetSistemSarafTangis.setBounds(600, 1410, 184, 23);

        KetKardiovaskularPulsasi.setFocusTraversalPolicyProvider(true);
        KetKardiovaskularPulsasi.setName("KetKardiovaskularPulsasi"); // NOI18N
        KetKardiovaskularPulsasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKardiovaskularPulsasiKeyPressed(evt);
            }
        });
        FormInput.add(KetKardiovaskularPulsasi);
        KetKardiovaskularPulsasi.setBounds(790, 1460, 120, 23);

        RespirasiAir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Udara Masuk", "Penurunan Udara Masuk", "Tidak Ada Udara Masuk" }));
        RespirasiAir.setName("RespirasiAir"); // NOI18N
        RespirasiAir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiAirKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiAir);
        RespirasiAir.setBounds(110, 1540, 200, 23);

        jLabel142.setText("Air Entry :");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(0, 1540, 110, 23);

        jLabel143.setText("Merintih :");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(320, 1540, 70, 23);

        RespirasiMerintih.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Terdengar Dengan Stetoskop", "Terdengar Tanpa Stetoskop" }));
        RespirasiMerintih.setName("RespirasiMerintih"); // NOI18N
        RespirasiMerintih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiMerintihKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiMerintih);
        RespirasiMerintih.setBounds(390, 1540, 200, 23);

        jLabel152.setText("BAB :");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(0, 1650, 110, 23);

        GastrointestinalBAB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Konstipasi", "Melena", "Colostomy", "Diare", "Meco pertama" }));
        GastrointestinalBAB.setName("GastrointestinalBAB"); // NOI18N
        GastrointestinalBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalBABKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalBAB);
        GastrointestinalBAB.setBounds(110, 1650, 150, 23);

        KetGastrointestinalBAB.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalBAB.setName("KetGastrointestinalBAB"); // NOI18N
        KetGastrointestinalBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalBABKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalBAB);
        KetGastrointestinalBAB.setBounds(260, 1650, 164, 23);

        jLabel298.setText("Warna BAB :");
        jLabel298.setName("jLabel298"); // NOI18N
        FormInput.add(jLabel298);
        jLabel298.setBounds(430, 1650, 70, 23);

        GastrointestinalWarnaBAB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kuning", "Dempul", "Coklat", "Hijau", "Lain-lain" }));
        GastrointestinalWarnaBAB.setName("GastrointestinalWarnaBAB"); // NOI18N
        GastrointestinalWarnaBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalWarnaBABKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalWarnaBAB);
        GastrointestinalWarnaBAB.setBounds(500, 1650, 150, 23);

        KetGastrointestinalWarnaBAB.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalWarnaBAB.setName("KetGastrointestinalWarnaBAB"); // NOI18N
        KetGastrointestinalWarnaBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalWarnaBABKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalWarnaBAB);
        KetGastrointestinalWarnaBAB.setBounds(660, 1650, 164, 23);

        jLabel299.setText("BAK :");
        jLabel299.setName("jLabel299"); // NOI18N
        FormInput.add(jLabel299);
        jLabel299.setBounds(0, 1680, 110, 23);

        GastrointestinalBAK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Hematuri", "Urin menetes", "Oliguri", "BAK Pertama" }));
        GastrointestinalBAK.setName("GastrointestinalBAK"); // NOI18N
        GastrointestinalBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalBAKKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalBAK);
        GastrointestinalBAK.setBounds(110, 1680, 150, 23);

        KetGastrointestinalBAK.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalBAK.setName("KetGastrointestinalBAK"); // NOI18N
        KetGastrointestinalBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalBAKKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalBAK);
        KetGastrointestinalBAK.setBounds(260, 1680, 164, 23);

        jLabel300.setText("Warna BAK :");
        jLabel300.setName("jLabel300"); // NOI18N
        FormInput.add(jLabel300);
        jLabel300.setBounds(430, 1680, 70, 23);

        GastrointestinalWarnaBAK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jernih", "Kuning", "Kuning pekat", "Lain-lain" }));
        GastrointestinalWarnaBAK.setName("GastrointestinalWarnaBAK"); // NOI18N
        GastrointestinalWarnaBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalWarnaBAKKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalWarnaBAK);
        GastrointestinalWarnaBAK.setBounds(500, 1680, 150, 23);

        KetGastrointestinalWarnaBAK.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalWarnaBAK.setName("KetGastrointestinalWarnaBAK"); // NOI18N
        KetGastrointestinalWarnaBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalWarnaBAKKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalWarnaBAK);
        KetGastrointestinalWarnaBAK.setBounds(660, 1680, 164, 23);

        jLabel154.setText("Besar Pupil :");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(220, 1730, 80, 23);

        NeurologiPupil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Isokor", "Anisokor" }));
        NeurologiPupil.setName("NeurologiPupil"); // NOI18N
        NeurologiPupil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiPupilKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiPupil);
        NeurologiPupil.setBounds(300, 1730, 108, 23);

        jLabel157.setText("Kelopak Mata :");
        jLabel157.setName("jLabel157"); // NOI18N
        FormInput.add(jLabel157);
        jLabel157.setBounds(420, 1730, 80, 23);

        NeurologiKelopak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Edema", "Cekung", "Lain-lain" }));
        NeurologiKelopak.setName("NeurologiKelopak"); // NOI18N
        NeurologiKelopak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiKelopakKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiKelopak);
        NeurologiKelopak.setBounds(500, 1730, 115, 23);

        KetNeurologiKelopak.setFocusTraversalPolicyProvider(true);
        KetNeurologiKelopak.setName("KetNeurologiKelopak"); // NOI18N
        KetNeurologiKelopak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNeurologiKelopakKeyPressed(evt);
            }
        });
        FormInput.add(KetNeurologiKelopak);
        KetNeurologiKelopak.setBounds(620, 1730, 150, 23);

        jLabel159.setText("Konjungtiva :");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(0, 1760, 110, 23);

        NeurologiKonjungtiva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Anemesis", "Konjungtivitis", "Lain-lain" }));
        NeurologiKonjungtiva.setName("NeurologiKonjungtiva"); // NOI18N
        NeurologiKonjungtiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiKonjungtivaKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiKonjungtiva);
        NeurologiKonjungtiva.setBounds(110, 1760, 115, 23);

        KetNeurologiKonjungtiva.setFocusTraversalPolicyProvider(true);
        KetNeurologiKonjungtiva.setName("KetNeurologiKonjungtiva"); // NOI18N
        KetNeurologiKonjungtiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNeurologiKonjungtivaKeyPressed(evt);
            }
        });
        FormInput.add(KetNeurologiKonjungtiva);
        KetNeurologiKonjungtiva.setBounds(230, 1760, 150, 23);

        KetNeurologiPendengaran.setFocusTraversalPolicyProvider(true);
        KetNeurologiPendengaran.setName("KetNeurologiPendengaran"); // NOI18N
        KetNeurologiPendengaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNeurologiPendengaranKeyPressed(evt);
            }
        });
        FormInput.add(KetNeurologiPendengaran);
        KetNeurologiPendengaran.setBounds(230, 1790, 150, 23);

        jLabel301.setText("Penciuman :");
        jLabel301.setName("jLabel301"); // NOI18N
        FormInput.add(jLabel301);
        jLabel301.setBounds(385, 1790, 70, 23);

        NeurologiPenciuman.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Asimetris", "Pengeluaran Cairan", "Lain-lain" }));
        NeurologiPenciuman.setName("NeurologiPenciuman"); // NOI18N
        NeurologiPenciuman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiPenciumanKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiPenciuman);
        NeurologiPenciuman.setBounds(460, 1790, 117, 23);

        KetNeurologiPenciuman.setFocusTraversalPolicyProvider(true);
        KetNeurologiPenciuman.setName("KetNeurologiPenciuman"); // NOI18N
        KetNeurologiPenciuman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNeurologiPenciumanKeyPressed(evt);
            }
        });
        FormInput.add(KetNeurologiPenciuman);
        KetNeurologiPenciuman.setBounds(580, 1790, 150, 23);

        IntegumentLanugo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak ada", "Banyak", "Tipis", "Bercak-bercak tanpa lanugo", "Sebagian besar tanpa lanugo" }));
        IntegumentLanugo.setName("IntegumentLanugo"); // NOI18N
        IntegumentLanugo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentLanugoKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentLanugo);
        IntegumentLanugo.setBounds(450, 1860, 170, 23);

        jLabel302.setText("Lanugo :");
        jLabel302.setName("jLabel302"); // NOI18N
        FormInput.add(jLabel302);
        jLabel302.setBounds(400, 1860, 50, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Sistem Muskuloskeletal :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(50, 1990, 122, 23);

        jLabel303.setText("Lengan :");
        jLabel303.setName("jLabel303"); // NOI18N
        FormInput.add(jLabel303);
        jLabel303.setBounds(0, 2010, 109, 23);

        MuskuloskeletalLengan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fleksi", "Ekstensi", "Pergerakan aktif", "Pergerakan tidak aktif", "Lain-lain" }));
        MuskuloskeletalLengan.setName("MuskuloskeletalLengan"); // NOI18N
        MuskuloskeletalLengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskuloskeletalLenganKeyPressed(evt);
            }
        });
        FormInput.add(MuskuloskeletalLengan);
        MuskuloskeletalLengan.setBounds(110, 2010, 150, 23);

        KetMuskuloskeletalLengan.setFocusTraversalPolicyProvider(true);
        KetMuskuloskeletalLengan.setName("KetMuskuloskeletalLengan"); // NOI18N
        KetMuskuloskeletalLengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMuskuloskeletalLenganKeyPressed(evt);
            }
        });
        FormInput.add(KetMuskuloskeletalLengan);
        KetMuskuloskeletalLengan.setBounds(270, 2010, 220, 23);

        jLabel305.setText("Tungkai :");
        jLabel305.setName("jLabel305"); // NOI18N
        FormInput.add(jLabel305);
        jLabel305.setBounds(490, 2010, 60, 23);

        MuskuloskeletalTungkai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fleksi", "Ekstensi", "Pergerakan aktif", "Pergerakan tidak aktif", "Lain-lain" }));
        MuskuloskeletalTungkai.setName("MuskuloskeletalTungkai"); // NOI18N
        MuskuloskeletalTungkai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskuloskeletalTungkaiKeyPressed(evt);
            }
        });
        FormInput.add(MuskuloskeletalTungkai);
        MuskuloskeletalTungkai.setBounds(550, 2010, 150, 23);

        KetMuskuloskeletalTungkai.setFocusTraversalPolicyProvider(true);
        KetMuskuloskeletalTungkai.setName("KetMuskuloskeletalTungkai"); // NOI18N
        KetMuskuloskeletalTungkai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMuskuloskeletalTungkaiKeyPressed(evt);
            }
        });
        FormInput.add(KetMuskuloskeletalTungkai);
        KetMuskuloskeletalTungkai.setBounds(710, 2010, 220, 23);

        jLabel304.setText("n. Penerimaan terhadap kondisi bayi saat ini :");
        jLabel304.setName("jLabel304"); // NOI18N
        FormInput.add(jLabel304);
        jLabel304.setBounds(35, 2250, 230, 20);

        Penerimaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menerima", "Tidak menerima" }));
        Penerimaan.setName("Penerimaan"); // NOI18N
        Penerimaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenerimaanKeyPressed(evt);
            }
        });
        FormInput.add(Penerimaan);
        Penerimaan.setBounds(270, 2250, 120, 23);

        jLabel306.setText("o. Masalah Pernikahan :");
        jLabel306.setName("jLabel306"); // NOI18N
        FormInput.add(jLabel306);
        jLabel306.setBounds(390, 2250, 130, 23);

        Pernikahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak ada", "Cerai/Istri Baru", "Simpanan", "Lain-lain" }));
        Pernikahan.setName("Pernikahan"); // NOI18N
        Pernikahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernikahanKeyPressed(evt);
            }
        });
        FormInput.add(Pernikahan);
        Pernikahan.setBounds(525, 2250, 130, 23);

        KeteranganPernikahan.setFocusTraversalPolicyProvider(true);
        KeteranganPernikahan.setName("KeteranganPernikahan"); // NOI18N
        KeteranganPernikahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPernikahanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPernikahan);
        KeteranganPernikahan.setBounds(660, 2250, 140, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Skala NIPS :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(30, 2310, 80, 23);

        jLabel307.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel307.setText("1. Ekspresi Wajah");
        jLabel307.setName("jLabel307"); // NOI18N
        FormInput.add(jLabel307);
        jLabel307.setBounds(50, 2330, 300, 23);

        NIPS1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Otot rileks wajah tenang ekspresi netral", "Meringis otot wajah tegang alis berkerut(ekspresi wajah negatif)" }));
        NIPS1.setName("NIPS1"); // NOI18N
        NIPS1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NIPS1ItemStateChanged(evt);
            }
        });
        NIPS1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPS1KeyPressed(evt);
            }
        });
        FormInput.add(NIPS1);
        NIPS1.setBounds(320, 2330, 370, 23);

        jLabel308.setText("Nilai :");
        jLabel308.setName("jLabel308"); // NOI18N
        FormInput.add(jLabel308);
        jLabel308.setBounds(700, 2330, 75, 23);

        NilaiNIPS1.setEditable(false);
        NilaiNIPS1.setFocusTraversalPolicyProvider(true);
        NilaiNIPS1.setName("NilaiNIPS1"); // NOI18N
        FormInput.add(NilaiNIPS1);
        NilaiNIPS1.setBounds(780, 2330, 60, 23);

        jLabel309.setText("Skala :");
        jLabel309.setName("jLabel309"); // NOI18N
        FormInput.add(jLabel309);
        jLabel309.setBounds(230, 2330, 80, 23);

        jLabel310.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel310.setText("2. Tangisan");
        jLabel310.setName("jLabel310"); // NOI18N
        FormInput.add(jLabel310);
        jLabel310.setBounds(50, 2360, 300, 23);

        jLabel311.setText("Skala :");
        jLabel311.setName("jLabel311"); // NOI18N
        FormInput.add(jLabel311);
        jLabel311.setBounds(230, 2360, 80, 23);

        NIPS2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak menangis tenang", "Merengek mengerang lemah intermiten", "Menangis keras melengking terus menerus(menagis tanpa suara diberi skor bila bayi intubasi)" }));
        NIPS2.setName("NIPS2"); // NOI18N
        NIPS2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NIPS2ItemStateChanged(evt);
            }
        });
        NIPS2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPS2KeyPressed(evt);
            }
        });
        FormInput.add(NIPS2);
        NIPS2.setBounds(320, 2360, 370, 23);

        jLabel312.setText("Nilai :");
        jLabel312.setName("jLabel312"); // NOI18N
        FormInput.add(jLabel312);
        jLabel312.setBounds(700, 2360, 75, 23);

        NilaiNIPS2.setEditable(false);
        NilaiNIPS2.setFocusTraversalPolicyProvider(true);
        NilaiNIPS2.setName("NilaiNIPS2"); // NOI18N
        FormInput.add(NilaiNIPS2);
        NilaiNIPS2.setBounds(780, 2360, 60, 23);

        jLabel313.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel313.setText("3. Pola Nafas");
        jLabel313.setName("jLabel313"); // NOI18N
        FormInput.add(jLabel313);
        jLabel313.setBounds(50, 2390, 300, 23);

        jLabel314.setText("Skala :");
        jLabel314.setName("jLabel314"); // NOI18N
        FormInput.add(jLabel314);
        jLabel314.setBounds(230, 2390, 80, 23);

        NIPS3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Relaks bernafas biasa", "Perubahan nafas tarikan irreguler lebih cepat dibandingkan biasa menahan nafas tersedak" }));
        NIPS3.setName("NIPS3"); // NOI18N
        NIPS3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NIPS3ItemStateChanged(evt);
            }
        });
        NIPS3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPS3KeyPressed(evt);
            }
        });
        FormInput.add(NIPS3);
        NIPS3.setBounds(320, 2390, 370, 23);

        jLabel315.setText("Nilai :");
        jLabel315.setName("jLabel315"); // NOI18N
        FormInput.add(jLabel315);
        jLabel315.setBounds(700, 2390, 75, 23);

        NilaiNIPS3.setEditable(false);
        NilaiNIPS3.setFocusTraversalPolicyProvider(true);
        NilaiNIPS3.setName("NilaiNIPS3"); // NOI18N
        FormInput.add(NilaiNIPS3);
        NilaiNIPS3.setBounds(780, 2390, 60, 23);

        jLabel316.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel316.setText("4. Tungkai");
        jLabel316.setName("jLabel316"); // NOI18N
        FormInput.add(jLabel316);
        jLabel316.setBounds(50, 2420, 300, 23);

        jLabel317.setText("Skala :");
        jLabel317.setName("jLabel317"); // NOI18N
        FormInput.add(jLabel317);
        jLabel317.setBounds(230, 2420, 80, 23);

        NIPS4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Relaks Tidak ada kekakuan otot gerakan tungkai biasa", "Fleksi / ekstensi Tegang kaku" }));
        NIPS4.setName("NIPS4"); // NOI18N
        NIPS4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NIPS4ItemStateChanged(evt);
            }
        });
        NIPS4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPS4KeyPressed(evt);
            }
        });
        FormInput.add(NIPS4);
        NIPS4.setBounds(320, 2420, 370, 23);

        jLabel318.setText("Nilai :");
        jLabel318.setName("jLabel318"); // NOI18N
        FormInput.add(jLabel318);
        jLabel318.setBounds(700, 2420, 75, 23);

        NilaiNIPS4.setEditable(false);
        NilaiNIPS4.setFocusTraversalPolicyProvider(true);
        NilaiNIPS4.setName("NilaiNIPS4"); // NOI18N
        FormInput.add(NilaiNIPS4);
        NilaiNIPS4.setBounds(780, 2420, 60, 23);

        jLabel319.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel319.setText("5. Tingkat Kesadaran");
        jLabel319.setName("jLabel319"); // NOI18N
        FormInput.add(jLabel319);
        jLabel319.setBounds(50, 2450, 300, 23);

        jLabel320.setText("Keterangan :");
        jLabel320.setName("jLabel320"); // NOI18N
        FormInput.add(jLabel320);
        jLabel320.setBounds(230, 2480, 80, 23);

        NIPS5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidur / bangun Tenang tidur lelap atau bangun", "Gelisah Sadar atau gelisah" }));
        NIPS5.setName("NIPS5"); // NOI18N
        NIPS5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NIPS5ItemStateChanged(evt);
            }
        });
        NIPS5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPS5KeyPressed(evt);
            }
        });
        FormInput.add(NIPS5);
        NIPS5.setBounds(320, 2450, 370, 23);

        jLabel321.setText("Nilai :");
        jLabel321.setName("jLabel321"); // NOI18N
        FormInput.add(jLabel321);
        jLabel321.setBounds(700, 2450, 75, 23);

        NilaiNIPS5.setEditable(false);
        NilaiNIPS5.setFocusTraversalPolicyProvider(true);
        NilaiNIPS5.setName("NilaiNIPS5"); // NOI18N
        FormInput.add(NilaiNIPS5);
        NilaiNIPS5.setBounds(780, 2450, 60, 23);

        jLabel325.setText("Total :");
        jLabel325.setName("jLabel325"); // NOI18N
        FormInput.add(jLabel325);
        jLabel325.setBounds(700, 2480, 75, 23);

        TotalNIPS.setEditable(false);
        TotalNIPS.setFocusTraversalPolicyProvider(true);
        TotalNIPS.setName("TotalNIPS"); // NOI18N
        FormInput.add(TotalNIPS);
        TotalNIPS.setBounds(780, 2480, 60, 23);

        KetNIPS.setEditable(false);
        KetNIPS.setFocusTraversalPolicyProvider(true);
        KetNIPS.setName("KetNIPS"); // NOI18N
        FormInput.add(KetNIPS);
        KetNIPS.setBounds(320, 2480, 370, 23);

        jLabel322.setText("Skala :");
        jLabel322.setName("jLabel322"); // NOI18N
        FormInput.add(jLabel322);
        jLabel322.setBounds(230, 2450, 80, 23);

        jLabel277.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel277.setText("diare, lain – lain) ?");
        jLabel277.setName("jLabel277"); // NOI18N
        FormInput.add(jLabel277);
        jLabel277.setBounds(63, 2620, 260, 23);

        SkalaGizi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        SkalaGizi3.setName("SkalaGizi3"); // NOI18N
        SkalaGizi3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaGizi3ItemStateChanged(evt);
            }
        });
        SkalaGizi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaGizi3KeyPressed(evt);
            }
        });
        FormInput.add(SkalaGizi3);
        SkalaGizi3.setBounds(430, 2600, 320, 23);

        jLabel323.setText("Skor :");
        jLabel323.setName("jLabel323"); // NOI18N
        FormInput.add(jLabel323);
        jLabel323.setBounds(750, 2600, 40, 23);

        NilaiGizi3.setEditable(false);
        NilaiGizi3.setFocusTraversalPolicyProvider(true);
        NilaiGizi3.setName("NilaiGizi3"); // NOI18N
        FormInput.add(NilaiGizi3);
        NilaiGizi3.setBounds(800, 2600, 60, 23);

        jLabel324.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel324.setText("3. Penyakit/ kelainan yang menyertai (sepsis, jantung, BBLR, hipoglikemi,");
        jLabel324.setName("jLabel324"); // NOI18N
        FormInput.add(jLabel324);
        jLabel324.setBounds(50, 2600, 380, 23);

        jLabel326.setText("Sudah dibaca dan diketahui oleh Dietisen :");
        jLabel326.setName("jLabel326"); // NOI18N
        FormInput.add(jLabel326);
        jLabel326.setBounds(450, 2660, 220, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-01-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-01-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(470, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel34.setText("Pasien :");
        jLabel34.setName("jLabel34"); // NOI18N
        jLabel34.setPreferredSize(new java.awt.Dimension(55, 23));
        FormMenu.add(jLabel34);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.setPreferredSize(new java.awt.Dimension(100, 23));
        FormMenu.add(TNoRM1);

        TPasien1.setEditable(false);
        TPasien1.setBackground(new java.awt.Color(245, 250, 240));
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(250, 23));
        FormMenu.add(TPasien1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPrint1);

        PanelAccor.add(FormMenu, java.awt.BorderLayout.NORTH);

        FormMasalahRencana.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        FormMasalahRencana.setName("FormMasalahRencana"); // NOI18N
        FormMasalahRencana.setLayout(new java.awt.GridLayout(3, 0, 1, 1));

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbMasalahDetail.setName("tbMasalahDetail"); // NOI18N
        Scroll7.setViewportView(tbMasalahDetail);

        FormMasalahRencana.add(Scroll7);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbRencanaDetail.setName("tbRencanaDetail"); // NOI18N
        Scroll9.setViewportView(tbRencanaDetail);

        FormMasalahRencana.add(Scroll9);

        PanelAccor.add(FormMasalahRencana, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Pengkaji 1");
        }else if(KdPetugas2.getText().trim().equals("")||NmPetugas2.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas2,"Pegkaji 2");
        }else if(KdDPJP.getText().trim().equals("")||NmDPJP.getText().trim().equals("")){
            Valid.textKosong(BtnDPJP,"DPJP");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else{
            if(Sequel.menyimpantf("penilaian_awal_keperawatan_ranap_neonatus","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",185,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),CaraMasuk.getSelectedItem().toString(),TibadiRuang.getSelectedItem().toString(),Anamnesis.getSelectedItem().toString(),KetAnamnesis.getText(), 
                    RPS.getText(),G.getText(),P.getText(),A.getText(),UK.getText(),RPI.getSelectedItem().toString(),KetRPI.getText(),RPO.getText(),RD.getSelectedItem().toString(),Indikasi.getText(),GiziIbu.getSelectedItem().toString(),G1.getText(),P1.getText(),A1.getText(),UK1.getText(), 
                    Valid.SetTgl(TanggalLahir.getSelectedItem()+"")+" "+TanggalLahir.getSelectedItem().toString().substring(11,19),Kondisi.getText(),Apgar.getText(),Cara.getSelectedItem().toString(),KetCara.getText(),Letak.getText(),Ketuban.getSelectedItem().toString(),Tali.getSelectedItem().toString(),
                    BB1.getText(),PB.getText(),LK.getText(),LD.getText(),LP.getText(),Mayor.getSelectedItem().toString(),Minor.getSelectedItem().toString(),Nutrisi.getSelectedItem().toString(),KetNutrisi.getText(),Frekuensi.getText(),Kali.getText(),KeluhanBAK.getText(),KeluhanBAB.getText(),
                    Alergi.getSelectedItem().toString(),AlergiObat.getText(),ReaksiObat.getText(),AlergiMakanan.getText(),ReaksiMakanan.getText(),AlergiLainnya.getText(),ReaksiLainnya.getText(),RPK.getText(),Imunisasi.getText(),KebiasaanObat.getSelectedItem().toString(),KetObat.getText(),
                    KebiasaanMerokok.getSelectedItem().toString(),KebiasaanJumlahRokok.getText(),KebiasaanAlkohol.getSelectedItem().toString(),KebiasaanJumlahAlkohol.getText(),KebiasaanNarkoba.getSelectedItem().toString(),KesadaranMental.getText(),KeadaanMentalUmum.getSelectedItem().toString(),
                    GCS.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),SpO2.getText(),Downes.getText(),BB.getText(),TB.getText(),LK1.getText(),LD1.getText(),LP1.getText(),DarahBayi.getSelectedItem().toString(),RhBayi.getSelectedItem().toString(),
                    DarahIbu.getSelectedItem().toString(),RhIbu.getSelectedItem().toString(),DarahAyah.getSelectedItem().toString(),RhAyah.getSelectedItem().toString(),Gerak.getSelectedItem().toString(),SistemSarafKepala.getSelectedItem().toString(),KetSistemSarafKepala.getText(),
                    SistemSarafUbun.getSelectedItem().toString(),KetSistemSarafUbun.getText(),SistemSarafWajah.getSelectedItem().toString(),KetSistemSarafWajah.getText(),SistemSarafKejang.getSelectedItem().toString(),KetSistemSarafKejang.getText(),
                    SistemSarafRefleks.getSelectedItem().toString(),KetSistemSarafRefleks.getText(),SistemSarafTangis.getSelectedItem().toString(),KetSistemSarafTangis.getText(),KardiovaskularDenyutNadi.getSelectedItem().toString(),KardiovaskularSirkulasi.getSelectedItem().toString(),
                    KetKardiovaskularSirkulasi.getText(),KardiovaskularPulsasi.getSelectedItem().toString(),KetKardiovaskularPulsasi.getText(),RespirasiPolaNafas.getSelectedItem().toString(),RespirasiJenisPernafasan.getSelectedItem().toString(),KetRespirasiJenisPernafasan.getText(), 
                    RespirasiRetraksi.getSelectedItem().toString(),RespirasiAir.getSelectedItem().toString(),RespirasiMerintih.getSelectedItem().toString(),RespirasiSuaraNafas.getSelectedItem().toString(),GastrointestinalMulut.getSelectedItem().toString(),KetGastrointestinalMulut.getText(),
                    GastrointestinalLidah.getSelectedItem().toString(),KetGastrointestinalLidah.getText(),GastrointestinalTenggorakan.getSelectedItem().toString(),KetGastrointestinalTenggorakan.getText(),GastrointestinalAbdomen.getSelectedItem().toString(),KetGastrointestinalAbdomen.getText(),
                    GastrointestinalBAB.getSelectedItem().toString(),KetGastrointestinalBAB.getText(),GastrointestinalWarnaBAB.getSelectedItem().toString(),KetGastrointestinalWarnaBAB.getText(),GastrointestinalBAK.getSelectedItem().toString(),KetGastrointestinalBAK.getText(),
                    GastrointestinalWarnaBAK.getSelectedItem().toString(),KetGastrointestinalWarnaBAK.getText(),NeurologiMata.getSelectedItem().toString(),NeurologiPupil.getSelectedItem().toString(),NeurologiKelopak.getSelectedItem().toString(),KetNeurologiKelopak.getText(),
                    NeurologiKonjungtiva.getSelectedItem().toString(),KetNeurologiKonjungtiva.getText(),NeurologiSklera.getSelectedItem().toString(),KetNeurologiSklera.getText(),NeurologiPendengaran.getSelectedItem().toString(),KetNeurologiPendengaran.getText(),
                    NeurologiPenciuman.getSelectedItem().toString(),KetNeurologiPenciuman.getText(),IntegumentWarnaKulit.getSelectedItem().toString(),IntegumentVernic.getSelectedItem().toString(),IntegumentLanugo.getSelectedItem().toString(),IntegumentTurgor.getSelectedItem().toString(),
                    IntegumentKulit.getSelectedItem().toString(),ReproduksiLaki.getSelectedItem().toString(),KetReproduksiLaki.getText(),ReproduksiPerempuan.getSelectedItem().toString(),KetReproduksiPerempuan.getText(),MuskuloskeletalLengan.getSelectedItem().toString(),
                    KetMuskuloskeletalLengan.getText(),MuskuloskeletalTungkai.getSelectedItem().toString(),KetMuskuloskeletalTungkai.getText(),MuskuloskletalRekoil.getSelectedItem().toString(),MuskuloskletalGaris.getSelectedItem().toString(),
                    KondisiPsikologis.getSelectedItem().toString(),GangguanJiwa.getSelectedItem().toString(),AdakahPerilaku.getSelectedItem().toString(),KeteranganAdakahPerilaku.getText(),HubunganAnggotaKeluarga.getSelectedItem().toString(),TinggalDengan.getSelectedItem().toString(),KeteranganTinggalDengan.getText(),
                    NilaiKepercayaan.getSelectedItem().toString(),KeteranganNilaiKepercayaan.getText(),PendidikanPJ.getSelectedItem().toString(),EdukasiPsikolgis.getSelectedItem().toString(),KeteranganEdukasiPsikologis.getText(),Penerimaan.getSelectedItem().toString(), 
                    Pernikahan.getSelectedItem().toString(),KeteranganPernikahan.getText(),NIPS1.getSelectedItem().toString(),NilaiNIPS1.getText(),NIPS2.getSelectedItem().toString(),NilaiNIPS2.getText(),NIPS3.getSelectedItem().toString(),NilaiNIPS3.getText(),
                    NIPS4.getSelectedItem().toString(),NilaiNIPS4.getText(),NIPS5.getSelectedItem().toString(),NilaiNIPS5.getText(),TotalNIPS.getText(),KetNIPS.getText(),SkalaGizi1.getSelectedItem().toString(),NilaiGizi1.getText(),SkalaGizi2.getSelectedItem().toString(),NilaiGizi2.getText(),
                    SkalaGizi3.getSelectedItem().toString(),NilaiGizi3.getText(),NilaiGiziTotal.getText(),DiketahuiDietisen.getSelectedItem().toString(),KeteranganDiketahuiDietisen.getText(),Rencana.getText(),KdPetugas.getText(),KdPetugas2.getText(),KdDPJP.getText()
                })==true){
                    for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                        if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                            Sequel.menyimpan2("penilaian_awal_keperawatan_ranap_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()});
                        }
                    }
//                    for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
//                        if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
//                            Sequel.menyimpan2("penilaian_awal_keperawatan_ranap_neonatus_rencana","?,?",2,new String[]{TNoRw.getText(),tbRencanaKeperawatan.getValueAt(i,1).toString()});
//                        }
//                    }
                    emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Rencana,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Pengkaji 1");
        }else if(KdPetugas2.getText().trim().equals("")||NmPetugas2.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas2,"Pegkaji 2");
        }else if(KdDPJP.getText().trim().equals("")||NmDPJP.getText().trim().equals("")){
            Valid.textKosong(BtnDPJP,"DPJP");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
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
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f;            
                BufferedWriter bw; 
                
                ps=koneksi.prepareStatement(
                    "select penilaian_awal_keperawatan_ranap_neonatus.no_rawat,penilaian_awal_keperawatan_ranap_neonatus.tanggal,penilaian_awal_keperawatan_ranap_neonatus.informasi,penilaian_awal_keperawatan_ranap_neonatus.ket_informasi,penilaian_awal_keperawatan_ranap_neonatus.tiba_diruang_rawat,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.kasus_trauma,penilaian_awal_keperawatan_ranap_neonatus.cara_masuk,penilaian_awal_keperawatan_ranap_neonatus.rps,penilaian_awal_keperawatan_ranap_neonatus.rpd,penilaian_awal_keperawatan_ranap_neonatus.rpk,penilaian_awal_keperawatan_ranap_neonatus.rpo,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.riwayat_pembedahan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_dirawat_dirs,penilaian_awal_keperawatan_ranap_neonatus.alat_bantu_dipakai,penilaian_awal_keperawatan_ranap_neonatus.riwayat_kehamilan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.riwayat_kehamilan_perkiraan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_tranfusi,penilaian_awal_keperawatan_ranap_neonatus.riwayat_alergi,penilaian_awal_keperawatan_ranap_neonatus.riwayat_merokok,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.riwayat_merokok_jumlah,penilaian_awal_keperawatan_ranap_neonatus.riwayat_alkohol,penilaian_awal_keperawatan_ranap_neonatus.riwayat_alkohol_jumlah,penilaian_awal_keperawatan_ranap_neonatus.riwayat_narkoba,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.riwayat_olahraga,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_mental,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_keadaan_umum,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gcs,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_td,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_nadi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_rr,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_suhu,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_spo2,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_bb,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_tb,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_kepala,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_kepala_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_wajah,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_wajah_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_leher,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_kejang,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_kejang_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_sensorik,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_kardiovaskuler_sirkulasi,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_kardiovaskuler_sirkulasi_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_pola_nafas,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_retraksi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_suara_nafas,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_volume_pernafasan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_jenis_pernafasan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_jenis_pernafasan_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_irama_nafas,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_batuk,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_mulut,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_mulut_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_gigi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_gigi_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_lidah,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_lidah_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_tenggorokan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_tenggorokan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_abdomen,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_abdomen_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_peistatik_usus,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_anus,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_pengelihatan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_pengelihatan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_alat_bantu_penglihatan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_pendengaran,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_bicara,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_bicara_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_sensorik,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_motorik,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_kekuatan_otot,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_integument_warnakulit,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_integument_turgor,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_integument_kulit,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_integument_dekubitas,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_pergerakan_sendi,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_kekauatan_otot,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_nyeri_sendi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_nyeri_sendi_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_oedema,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_oedema_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_fraktur,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_fraktur_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_eliminasi_bab_frekuensi_jumlah,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_eliminasi_bab_frekuensi_durasi,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_eliminasi_bab_konsistensi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_eliminasi_bab_warna,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_eliminasi_bak_frekuensi_jumlah,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_eliminasi_bak_frekuensi_durasi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_eliminasi_bak_warna,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_eliminasi_bak_lainlain,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pola_aktifitas_makanminum,penilaian_awal_keperawatan_ranap_neonatus.pola_aktifitas_mandi,penilaian_awal_keperawatan_ranap_neonatus.pola_aktifitas_eliminasi,penilaian_awal_keperawatan_ranap_neonatus.pola_aktifitas_berpakaian,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pola_aktifitas_berpindah,penilaian_awal_keperawatan_ranap_neonatus.pola_nutrisi_frekuesi_makan,penilaian_awal_keperawatan_ranap_neonatus.pola_nutrisi_jenis_makanan,penilaian_awal_keperawatan_ranap_neonatus.pola_nutrisi_porsi_makan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pola_tidur_lama_tidur,penilaian_awal_keperawatan_ranap_neonatus.pola_tidur_gangguan,penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_kemampuan_sehari,penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_aktifitas,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_berjalan_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_ambulasi,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_ekstrimitas_atas_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_ekstrimitas_bawah,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_ekstrimitas_bawah_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_menggenggam,penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_menggenggam_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_koordinasi_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pengkajian_fungsi_kesimpulan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_kondisi_psiko,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_perilaku,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_hubungan_keluarga,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_tinggal,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_tinggal_keterangan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_nilai_kepercayaan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_edukasi_diberikan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_edukasi_diberikan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri,penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_ket_penyebab,penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_kualitas,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_lokasi,penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_skala,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_waktu,penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_diberitahukan_dokter,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_skala1,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_nilai1,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_skala2,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_nilai2,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_skala3,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_nilai3,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_skala4,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_nilai4,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_skala5,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_nilai5,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_skala6,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_nilai6,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhmorse_totalnilai,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala1,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai1,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala2,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai2,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala3,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai3,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala4,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai4,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala5,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai5,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala6,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai6,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala7,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai7,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala8,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai8,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala9,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai9,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala10,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai10,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_skala11,penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_nilai11,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.penilaian_jatuhsydney_totalnilai,penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi1,penilaian_awal_keperawatan_ranap_neonatus.nilai_gizi1,penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi2,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.nilai_gizi2,penilaian_awal_keperawatan_ranap_neonatus.nilai_total_gizi,penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi_ket_diagnosa_khusus,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap_neonatus.rencana,penilaian_awal_keperawatan_ranap_neonatus.nip1,"+
                    "penilaian_awal_keperawatan_ranap_neonatus.nip2,penilaian_awal_keperawatan_ranap_neonatus.kd_dokter,pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,"+
                    "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab,bahasa_pasien.nama_bahasa "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join penilaian_awal_keperawatan_ranap_neonatus on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap_neonatus.no_rawat "+
                    "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap_neonatus.nip1=pengkaji1.nip "+
                    "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap_neonatus.nip2=pengkaji2.nip "+
                    "inner join dokter on penilaian_awal_keperawatan_ranap_neonatus.kd_dokter=dokter.kd_dokter "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where "+
                    "penilaian_awal_keperawatan_ranap_neonatus.tanggal between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_awal_keperawatan_ranap_neonatus.nip1 like ? or "+
                    "pengkaji1.nama like ? or penilaian_awal_keperawatan_ranap_neonatus.kd_dokter like ? or dokter.nm_dokter like ?)")+
                    " order by penilaian_awal_keperawatan_ranap_neonatus.tanggal");

                try {
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    if(!TCari.getText().equals("")){
                        ps.setString(3,"%"+TCari.getText()+"%");
                        ps.setString(4,"%"+TCari.getText()+"%");
                        ps.setString(5,"%"+TCari.getText()+"%");
                        ps.setString(6,"%"+TCari.getText()+"%");
                        ps.setString(7,"%"+TCari.getText()+"%");
                        ps.setString(8,"%"+TCari.getText()+"%");
                        ps.setString(9,"%"+TCari.getText()+"%");
                    }   
                    rs=ps.executeQuery();
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                    switch (pilihan) {
                        case "Laporan 1 (HTML)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='25px'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>NIP Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>NIP Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kode DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='117px'>Tgl.Asuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='78px'>Macam Kasus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Anamnesis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>Tiba Di Ruang Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Cara Masuk</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='220px'>Riwayat Penyakit Saat Ini</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Penyakit Dahulu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Penyakit Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Penggunaan Obat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Pembedahan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Dirawat Di RS</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alat Bantu Yang Dipakai</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='210px'>Dalam Keadaan Hamil/Sedang Menyusui</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Transfusi Darah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Alergi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='48px'>Merokok</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Batang/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='44px'>Alkohol</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='59px'>Gelas/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='61px'>Obat Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='59px'>Olah Raga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kesadaran Mental</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keadaan Umum</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='64px'>GCS(E,V,M)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>TD(mmHg)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='74px'>Nadi(x/menit)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='67px'>RR(x/menit)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='52px'>Suhu(°C)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='52px'>SpO2(%)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='44px'>BB(Kg)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='44px'>TB(cm)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kepala</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Wajah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='106px'>Leher</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kejang</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Sensorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Pulsasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Sirkulasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='72px'>Denyut Nadi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='54px'>Retraksi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='63px'>Pola Nafas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='69px'>Suara Nafas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='97px'>Batuk & Sekresi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='75px'>Volume</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Jenis Pernafasaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Irama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Mulut</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Lidah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Gigi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tenggorokan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Abdomen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Peistatik Usus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Anus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Sensorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Penglihatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alat Bantu Penglihatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Motorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pendengaran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Bicara</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Otot</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kulit</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Warna Kulit</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Turgor</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Resiko Decubitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Oedema</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pergerakan Sendi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kekuatan Otot</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Fraktur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri Sendi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Frekuensi BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>x/</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Konsistensi BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Warna BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Frekuensi BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>x/</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Warna BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Lain-lain BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Mandi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Makan/Minum</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Berpakaian</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Eliminasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Berpindah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Porsi Makan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Frekuensi Makan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Jenis Makanan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Lama Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Gangguan Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>a. Aktifitas Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>b. Berjalan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>c. Aktifitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>d. Alat Ambulasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>e. Ekstremitas Atas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>f. Ekstremitas Bawah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>g. Kemampuan Menggenggam</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>h. Kemampuan Koordinasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>i. Kesimpulan Gangguan Fungsi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>a. Kondisi Psikologis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>b. Adakah Perilaku</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>c. Gangguan Jiwa di Masa Lalu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>d. Hubungan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>e. Agama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>f. Tinggal Dengan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>g. Pekerjaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>h. Pembayaran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>i. Nilai-nilai Kepercayaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>j. Bahasa Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>k. Pendidikan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>l. Pendidikan P.J.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>m. Edukasi Diberikan Kepada</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Penyebab Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kualitas Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Lokasi Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri Menyebar</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Waktu / Durasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri Hilang Bila</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Diberitahukan Pada Dokter</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>T.M.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 7</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 7</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 8</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 8</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 9</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 9</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 10</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 10</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 11</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 11</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>T.S.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skor 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>2. Apakah asupan makan berkurang karena tidak nafsu makan ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skor 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Total Skor</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pasien dengan diagnosis khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Diagnosa Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Sudah dibaca dan diketahui oleh Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Jam Dibaca Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Rencana Keperawatan Lainnya</td>"+
                                    "</tr>"
                                );
                                while(rs.next()){
                                    htmlContent.append(
                                        "<tr class='isi'>"+
                                            "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                                            "<td valign='top'>"+rs.getString("jk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkaji1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkaji2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                                            "<td valign='top'>"+rs.getString("kasus_trauma")+"</td>"+
                                            "<td valign='top'>"+rs.getString("informasi")+", "+rs.getString("ket_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tiba_diruang_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("cara_masuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rps")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpd")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpo")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_pembedahan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_dirawat_dirs")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alat_bantu_dipakai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kehamilan")+", "+rs.getString("riwayat_kehamilan_perkiraan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_tranfusi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alergi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_merokok")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_merokok_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alkohol")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alkohol_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_narkoba")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_olahraga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_mental")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_keadaan_umum")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gcs")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_td")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_nadi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_rr")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_suhu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_spo2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_bb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_tb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_kepala")+", "+rs.getString("pemeriksaan_susunan_kepala_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_wajah")+", "+rs.getString("pemeriksaan_susunan_wajah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_leher")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_kejang")+", "+rs.getString("pemeriksaan_susunan_kejang_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_sensorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_pulsasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi")+", "+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_denyut_nadi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_retraksi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_pola_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_suara_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_batuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_volume_pernafasan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_jenis_pernafasan")+", "+rs.getString("pemeriksaan_respirasi_jenis_pernafasan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_irama_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_mulut")+", "+rs.getString("pemeriksaan_gastrointestinal_mulut_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_lidah")+", "+rs.getString("pemeriksaan_gastrointestinal_lidah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_gigi")+", "+rs.getString("pemeriksaan_gastrointestinal_gigi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_tenggorokan")+", "+rs.getString("pemeriksaan_gastrointestinal_tenggorokan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_abdomen")+", "+rs.getString("pemeriksaan_gastrointestinal_abdomen_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_peistatik_usus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_anus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_sensorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_pengelihatan")+", "+rs.getString("pemeriksaan_neurologi_pengelihatan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_alat_bantu_penglihatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_motorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_pendengaran")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_bicara")+", "+rs.getString("pemeriksaan_neurologi_bicara_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_kekuatan_otot")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_kulit")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_warnakulit")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_turgor")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_dekubitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_oedema")+", "+rs.getString("pemeriksaan_muskuloskletal_oedema_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_pergerakan_sendi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_kekauatan_otot")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_fraktur")+", "+rs.getString("pemeriksaan_muskuloskletal_fraktur_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi")+", "+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_durasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_konsistensi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_warna")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_durasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_warna")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_lainlain")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_mandi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_makanminum")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_berpakaian")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_eliminasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_berpindah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_porsi_makan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_frekuesi_makan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_jenis_makanan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_tidur_lama_tidur")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_tidur_gangguan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kemampuan_sehari")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_berjalan")+", "+rs.getString("pengkajian_fungsi_berjalan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_aktifitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ambulasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_atas")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_atas_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_bawah")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_bawah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_menggenggam")+", "+rs.getString("pengkajian_fungsi_menggenggam_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_koordinasi")+", "+rs.getString("pengkajian_fungsi_koordinasi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kesimpulan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_kondisi_psiko")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_perilaku")+", "+rs.getString("riwayat_psiko_perilaku_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_gangguan_jiwa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_hubungan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("agama")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_tinggal")+", "+rs.getString("riwayat_psiko_tinggal_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pekerjaan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("png_jawab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_nilai_kepercayaan")+", "+rs.getString("riwayat_psiko_nilai_kepercayaan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama_bahasa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pnd")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_pendidikan_pj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_edukasi_diberikan")+", "+rs.getString("riwayat_psiko_edukasi_diberikan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_penyebab")+", "+rs.getString("penilaian_nyeri_ket_penyebab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_kualitas")+", "+rs.getString("penilaian_nyeri_ket_kualitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_lokasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_menyebar")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_skala")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_waktu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_hilang")+", "+rs.getString("penilaian_nyeri_ket_hilang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_diberitahukan_dokter")+", "+rs.getString("penilaian_nyeri_jam_diberitahukan_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_totalnilai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala7")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai7")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala8")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai8")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala9")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai9")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala10")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai10")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala11")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai11")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_totalnilai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_total_gizi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_ket_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_jam_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rencana")+"</td>"+
                                        "</tr>"
                                    );
                                }
                                f = new File("RMPenilaianAwalKeperawatanRanap.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write("<html>"+
                                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                            "<body>"+
                                                "<table width='18500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                    htmlContent.toString()+
                                                "</table>"+
                                            "</body>"+                   
                                         "</html>"
                                );

                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break;
                        case "Laporan 2 (WPS)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='25px'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>NIP Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>NIP Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kode DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='117px'>Tgl.Asuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='78px'>Macam Kasus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Anamnesis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>Tiba Di Ruang Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Cara Masuk</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='220px'>Riwayat Penyakit Saat Ini</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Penyakit Dahulu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Penyakit Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Penggunaan Obat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Pembedahan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Dirawat Di RS</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alat Bantu Yang Dipakai</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='210px'>Dalam Keadaan Hamil/Sedang Menyusui</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Transfusi Darah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Riwayat Alergi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='48px'>Merokok</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Batang/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='44px'>Alkohol</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='59px'>Gelas/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='61px'>Obat Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='59px'>Olah Raga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kesadaran Mental</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keadaan Umum</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='64px'>GCS(E,V,M)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>TD(mmHg)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='74px'>Nadi(x/menit)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='67px'>RR(x/menit)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='52px'>Suhu(°C)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='52px'>SpO2(%)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='44px'>BB(Kg)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='44px'>TB(cm)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kepala</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Wajah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='106px'>Leher</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kejang</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Sensorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Pulsasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Sirkulasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='72px'>Denyut Nadi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='54px'>Retraksi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='63px'>Pola Nafas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='69px'>Suara Nafas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='97px'>Batuk & Sekresi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='75px'>Volume</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Jenis Pernafasaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Irama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Mulut</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Lidah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Gigi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tenggorokan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Abdomen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Peistatik Usus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Anus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Sensorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Penglihatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alat Bantu Penglihatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Motorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pendengaran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Bicara</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Otot</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kulit</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Warna Kulit</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Turgor</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Resiko Decubitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Oedema</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pergerakan Sendi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kekuatan Otot</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Fraktur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri Sendi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Frekuensi BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>x/</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Konsistensi BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Warna BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Frekuensi BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>x/</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Warna BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Lain-lain BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Mandi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Makan/Minum</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Berpakaian</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Eliminasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Berpindah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Porsi Makan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Frekuensi Makan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Jenis Makanan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Lama Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Gangguan Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>a. Aktifitas Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>b. Berjalan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>c. Aktifitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>d. Alat Ambulasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>e. Ekstremitas Atas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>f. Ekstremitas Bawah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>g. Kemampuan Menggenggam</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>h. Kemampuan Koordinasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>i. Kesimpulan Gangguan Fungsi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>a. Kondisi Psikologis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>b. Adakah Perilaku</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>c. Gangguan Jiwa di Masa Lalu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>d. Hubungan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>e. Agama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>f. Tinggal Dengan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>g. Pekerjaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>h. Pembayaran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>i. Nilai-nilai Kepercayaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>j. Bahasa Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>k. Pendidikan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>l. Pendidikan P.J.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>m. Edukasi Diberikan Kepada</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Penyebab Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kualitas Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Lokasi Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri Menyebar</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Waktu / Durasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri Hilang Bila</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Diberitahukan Pada Dokter</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Morse 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.M. 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>T.M.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 7</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 7</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 8</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 8</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 9</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 9</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 10</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 10</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skala Sydney 11</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>N.S. 11</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>T.S.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skor 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>2. Apakah asupan makan berkurang karena tidak nafsu makan ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Skor 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Total Skor</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pasien dengan diagnosis khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Diagnosa Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Sudah dibaca dan diketahui oleh Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Jam Dibaca Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Rencana Keperawatan Lainnya</td>"+
                                    "</tr>"
                                );
                                while(rs.next()){
                                    htmlContent.append(
                                        "<tr class='isi'>"+
                                            "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                                            "<td valign='top'>"+rs.getString("jk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkaji1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkaji2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                                            "<td valign='top'>"+rs.getString("kasus_trauma")+"</td>"+
                                            "<td valign='top'>"+rs.getString("informasi")+", "+rs.getString("ket_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tiba_diruang_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("cara_masuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rps")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpd")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpo")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_pembedahan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_dirawat_dirs")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alat_bantu_dipakai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kehamilan")+", "+rs.getString("riwayat_kehamilan_perkiraan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_tranfusi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alergi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_merokok")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_merokok_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alkohol")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alkohol_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_narkoba")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_olahraga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_mental")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_keadaan_umum")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gcs")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_td")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_nadi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_rr")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_suhu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_spo2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_bb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_tb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_kepala")+", "+rs.getString("pemeriksaan_susunan_kepala_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_wajah")+", "+rs.getString("pemeriksaan_susunan_wajah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_leher")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_kejang")+", "+rs.getString("pemeriksaan_susunan_kejang_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_sensorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_pulsasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi")+", "+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_denyut_nadi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_retraksi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_pola_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_suara_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_batuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_volume_pernafasan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_jenis_pernafasan")+", "+rs.getString("pemeriksaan_respirasi_jenis_pernafasan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_irama_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_mulut")+", "+rs.getString("pemeriksaan_gastrointestinal_mulut_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_lidah")+", "+rs.getString("pemeriksaan_gastrointestinal_lidah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_gigi")+", "+rs.getString("pemeriksaan_gastrointestinal_gigi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_tenggorokan")+", "+rs.getString("pemeriksaan_gastrointestinal_tenggorokan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_abdomen")+", "+rs.getString("pemeriksaan_gastrointestinal_abdomen_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_peistatik_usus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_anus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_sensorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_pengelihatan")+", "+rs.getString("pemeriksaan_neurologi_pengelihatan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_alat_bantu_penglihatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_motorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_pendengaran")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_bicara")+", "+rs.getString("pemeriksaan_neurologi_bicara_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_kekuatan_otot")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_kulit")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_warnakulit")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_turgor")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_dekubitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_oedema")+", "+rs.getString("pemeriksaan_muskuloskletal_oedema_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_pergerakan_sendi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_kekauatan_otot")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_fraktur")+", "+rs.getString("pemeriksaan_muskuloskletal_fraktur_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi")+", "+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_durasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_konsistensi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_warna")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_durasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_warna")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_lainlain")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_mandi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_makanminum")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_berpakaian")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_eliminasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_berpindah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_porsi_makan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_frekuesi_makan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_jenis_makanan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_tidur_lama_tidur")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_tidur_gangguan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kemampuan_sehari")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_berjalan")+", "+rs.getString("pengkajian_fungsi_berjalan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_aktifitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ambulasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_atas")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_atas_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_bawah")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_bawah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_menggenggam")+", "+rs.getString("pengkajian_fungsi_menggenggam_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_koordinasi")+", "+rs.getString("pengkajian_fungsi_koordinasi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kesimpulan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_kondisi_psiko")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_perilaku")+", "+rs.getString("riwayat_psiko_perilaku_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_gangguan_jiwa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_hubungan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("agama")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_tinggal")+", "+rs.getString("riwayat_psiko_tinggal_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pekerjaan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("png_jawab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_nilai_kepercayaan")+", "+rs.getString("riwayat_psiko_nilai_kepercayaan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama_bahasa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pnd")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_pendidikan_pj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_edukasi_diberikan")+", "+rs.getString("riwayat_psiko_edukasi_diberikan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_penyebab")+", "+rs.getString("penilaian_nyeri_ket_penyebab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_kualitas")+", "+rs.getString("penilaian_nyeri_ket_kualitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_lokasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_menyebar")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_skala")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_waktu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_hilang")+", "+rs.getString("penilaian_nyeri_ket_hilang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_diberitahukan_dokter")+", "+rs.getString("penilaian_nyeri_jam_diberitahukan_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_totalnilai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala7")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai7")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala8")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai8")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala9")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai9")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala10")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai10")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala11")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai11")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_totalnilai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_total_gizi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_ket_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_jam_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rencana")+"</td>"+
                                        "</tr>"
                                    );
                                }
                                f = new File("RMPenilaianAwalKeperawatanRanap.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write("<html>"+
                                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                            "<body>"+
                                                "<table width='18500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                    htmlContent.toString()+
                                                "</table>"+
                                            "</body>"+                   
                                         "</html>"
                                );

                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break;
                        case "Laporan 3 (CSV)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"J.K.\";\"NIP Pengkaji 1\";\"Nama Pengkaji 1\";\"NIP Pengkaji 2\";\"Nama Pengkaji 2\";\"Kode DPJP\";\"Nama DPJP\";\"Tgl.Asuhan\";\"Macam Kasus\";\"Anamnesis\";\"Tiba Di Ruang Rawat\";\"Cara Masuk\";\"Riwayat Penyakit Saat Ini\";\"Riwayat Penyakit Dahulu\";\"Riwayat Penyakit Keluarga\";\"Riwayat Penggunaan Obat\";\"Riwayat Pembedahan\";\"Riwayat Dirawat Di RS\";\"Alat Bantu Yang Dipakai\";\"Dalam Keadaan Hamil/Sedang Menyusui\";\"Riwayat Transfusi Darah\";\"Riwayat Alergi\";\"Merokok\";\"Batang/Hari\";\"Alkohol\";\"Gelas/Hari\";\"Obat Tidur\";\"Olah Raga\";\"Kesadaran Mental\";\"Keadaan Umum\";\"GCS(E,V,M)\";\"TD(mmHg)\";\"Nadi(x/menit)\";\"RR(x/menit)\";\"Suhu(°C)\";\"SpO2(%)\";\"BB(Kg)\";\"TB(cm)\";\"Kepala\";\"Wajah\";\"Leher\";\"Kejang\";\"Sensorik\";\"Pulsasi\";\"Sirkulasi\";\"Denyut Nadi\";\"Retraksi\";\"Pola Nafas\";\"Suara Nafas\";\"Batuk & Sekresi\";\"Volume\";\"Jenis Pernafasaan\";\"Irama\";\"Mulut\";\"Lidah\";\"Gigi\";\"Tenggorokan\";\"Abdomen\";\"Peistatik Usus\";\"Anus\";\"Sensorik\";\"Penglihatan\";\"Alat Bantu Penglihatan\";\"Motorik\";\"Pendengaran\";\"Bicara\";\"Otot\";\"Kulit\";\"Warna Kulit\";\"Turgor\";\"Resiko Decubitas\";\"Oedema\";\"Pergerakan Sendi\";\"Kekuatan Otot\";\"Fraktur\";\"Nyeri Sendi\";\"Frekuensi BAB\";\"x/\";\"Konsistensi BAB\";\"Warna BAB\";\"Frekuensi BAK\";\"x/\";\"Warna BAK\";\"Lain-lain BAK\";\"Mandi\";\"Makan/Minum\";\"Berpakaian\";\"Eliminasi\";\"Berpindah\";\"Porsi Makan\";\"Frekuensi Makan\";\"Jenis Makanan\";\"Lama Tidur\";\"Gangguan Tidur\";\"a. Aktifitas Sehari-hari\";\"b. Berjalan\";\"c. Aktifitas\";\"d. Alat Ambulasi\";\"e. Ekstremitas Atas\";\"f. Ekstremitas Bawah\";\"g. Kemampuan Menggenggam\";\"h. Kemampuan Koordinasi\";\"i. Kesimpulan Gangguan Fungsi\";\"a. Kondisi Psikologis\";\"b. Adakah Perilaku\";\"c. Gangguan Jiwa di Masa Lalu\";\"d. Hubungan Pasien\";\"e. Agama\";\"f. Tinggal Dengan\";\"g. Pekerjaan\";\"h. Pembayaran\";\"i. Nilai-nilai Kepercayaan\";\"j. Bahasa Sehari-hari\";\"k. Pendidikan Pasien\";\"l. Pendidikan P.J.\";\"m. Edukasi Diberikan Kepada\";\"Nyeri\";\"Penyebab Nyeri\";\"Kualitas Nyeri\";\"Lokasi Nyeri\";\"Nyeri Menyebar\";\"Skala Nyeri\";\"Waktu / Durasi\";\"Nyeri Hilang Bila\";\"Diberitahukan Pada Dokter\";\"Skala Morse 1\";\"N.M. 1\";\"Skala Morse 2\";\"N.M. 2\";\"Skala Morse 3\";\"N.M. 3\";\"Skala Morse 4\";\"N.M. 4\";\"Skala Morse 5\";\"N.M. 5\";\"Skala Morse 6\";\"N.M. 6\";\"T.M.\";\"Skala Sydney 1\";\"N.S. 1\";\"Skala Sydney 2\";\"N.S. 2\";\"Skala Sydney 3\";\"N.S. 3\";\"Skala Sydney 4\";\"N.S. 4\";\"Skala Sydney 5\";\"N.S. 5\";\"Skala Sydney 6\";\"N.S. 6\";\"Skala Sydney 7\";\"N.S. 7\";\"Skala Sydney 8\";\"N.S. 8\";\"Skala Sydney 9\";\"N.S. 9\";\"Skala Sydney 10\";\"N.S. 10\";\"Skala Sydney 11\";\"N.S. 11\";\"T.S.\";\"1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?\";\"Skor 1\";\"2. Apakah asupan makan berkurang karena tidak nafsu makan ?\";\"Skor 2\";\"Total Skor\";\"Pasien dengan diagnosis khusus\";\"Keterangan Diagnosa Khusus\";\"Sudah dibaca dan diketahui oleh Dietisen\";\"Jam Dibaca Dietisen\";\"Rencana Keperawatan Lainnya\"\n"
                                ); 
                                while(rs.next()){
                                    htmlContent.append(
                                        "\""+rs.getString("no_rawat")+"\";\""+rs.getString("no_rkm_medis")+"\";\""+rs.getString("nm_pasien")+"\";\""+rs.getString("tgl_lahir")+"\";\""+rs.getString("jk")+"\";\""+rs.getString("nip1")+"\";\""+rs.getString("pengkaji1")+"\";\""+rs.getString("nip2")+"\";\""+rs.getString("pengkaji2")+"\";\""+rs.getString("kd_dokter")+"\";\""+rs.getString("nm_dokter")+"\";\""+rs.getString("tanggal")+"\";\""+rs.getString("kasus_trauma")+"\";\""+rs.getString("informasi")+", "+rs.getString("ket_informasi")+"\";\""+rs.getString("tiba_diruang_rawat")+"\";\""+rs.getString("cara_masuk")+"\";\""+rs.getString("rps")+"\";\""+rs.getString("rpd")+"\";\""+rs.getString("rpk")+"\";\""+rs.getString("rpo")+"\";\""+rs.getString("riwayat_pembedahan")+"\";\""+rs.getString("riwayat_dirawat_dirs")+"\";\""+rs.getString("alat_bantu_dipakai")+"\";\""+rs.getString("riwayat_kehamilan")+", "+rs.getString("riwayat_kehamilan_perkiraan")+"\";\""+rs.getString("riwayat_tranfusi")+"\";\""+rs.getString("riwayat_alergi")+"\";\""+rs.getString("riwayat_merokok")+"\";\""+rs.getString("riwayat_merokok_jumlah")+"\";\""+rs.getString("riwayat_alkohol")+"\";\""+rs.getString("riwayat_alkohol_jumlah")+"\";\""+rs.getString("riwayat_narkoba")+"\";\""+rs.getString("riwayat_olahraga")+"\";\""+rs.getString("pemeriksaan_mental")+"\";\""+rs.getString("pemeriksaan_keadaan_umum")+"\";\""+rs.getString("pemeriksaan_gcs")+"\";\""+rs.getString("pemeriksaan_td")+"\";\""+rs.getString("pemeriksaan_nadi")+"\";\""+rs.getString("pemeriksaan_rr")+"\";\""+rs.getString("pemeriksaan_suhu")+"\";\""+rs.getString("pemeriksaan_spo2")+"\";\""+rs.getString("pemeriksaan_bb")+"\";\""+rs.getString("pemeriksaan_tb")+"\";\""+rs.getString("pemeriksaan_susunan_kepala")+", "+rs.getString("pemeriksaan_susunan_kepala_keterangan")+"\";\""+rs.getString("pemeriksaan_susunan_wajah")+", "+rs.getString("pemeriksaan_susunan_wajah_keterangan")+"\";\""+rs.getString("pemeriksaan_susunan_leher")+"\";\""+rs.getString("pemeriksaan_susunan_kejang")+", "+rs.getString("pemeriksaan_susunan_kejang_keterangan")+"\";\""+rs.getString("pemeriksaan_susunan_sensorik")+"\";\""+rs.getString("pemeriksaan_kardiovaskuler_pulsasi")+"\";\""+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi")+", "+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi_keterangan")+"\";\""+rs.getString("pemeriksaan_kardiovaskuler_denyut_nadi")+"\";\""+rs.getString("pemeriksaan_respirasi_retraksi")+"\";\""+rs.getString("pemeriksaan_respirasi_pola_nafas")+"\";\""+rs.getString("pemeriksaan_respirasi_suara_nafas")+"\";\""+rs.getString("pemeriksaan_respirasi_batuk")+"\";\""+rs.getString("pemeriksaan_respirasi_volume_pernafasan")+"\";\""+rs.getString("pemeriksaan_respirasi_jenis_pernafasan")+", "+rs.getString("pemeriksaan_respirasi_jenis_pernafasan_keterangan")+"\";\""+rs.getString("pemeriksaan_respirasi_irama_nafas")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_mulut")+", "+rs.getString("pemeriksaan_gastrointestinal_mulut_keterangan")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_lidah")+", "+rs.getString("pemeriksaan_gastrointestinal_lidah_keterangan")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_gigi")+", "+rs.getString("pemeriksaan_gastrointestinal_gigi_keterangan")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_tenggorokan")+", "+rs.getString("pemeriksaan_gastrointestinal_tenggorokan_keterangan")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_abdomen")+", "+rs.getString("pemeriksaan_gastrointestinal_abdomen_keterangan")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_peistatik_usus")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_anus")+"\";\""+rs.getString("pemeriksaan_neurologi_sensorik")+"\";\""+rs.getString("pemeriksaan_neurologi_pengelihatan")+", "+rs.getString("pemeriksaan_neurologi_pengelihatan_keterangan")+"\";\""+rs.getString("pemeriksaan_neurologi_alat_bantu_penglihatan")+"\";\""+rs.getString("pemeriksaan_neurologi_motorik")+"\";\""+rs.getString("pemeriksaan_neurologi_pendengaran")+"\";\""+rs.getString("pemeriksaan_neurologi_bicara")+", "+rs.getString("pemeriksaan_neurologi_bicara_keterangan")+"\";\""+rs.getString("pemeriksaan_neurologi_kekuatan_otot")+"\";\""+rs.getString("pemeriksaan_integument_kulit")+"\";\""+rs.getString("pemeriksaan_integument_warnakulit")+"\";\""+rs.getString("pemeriksaan_integument_turgor")+"\";\""+rs.getString("pemeriksaan_integument_dekubitas")+"\";\""+rs.getString("pemeriksaan_muskuloskletal_oedema")+", "+rs.getString("pemeriksaan_muskuloskletal_oedema_keterangan")+"\";\""+rs.getString("pemeriksaan_muskuloskletal_pergerakan_sendi")+"\";\""+rs.getString("pemeriksaan_muskuloskletal_kekauatan_otot")+"\";\""+rs.getString("pemeriksaan_muskuloskletal_fraktur")+", "+rs.getString("pemeriksaan_muskuloskletal_fraktur_keterangan")+"\";\""+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi")+", "+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi_keterangan")+"\";\""+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_jumlah")+"\";\""+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_durasi")+"\";\""+rs.getString("pemeriksaan_eliminasi_bab_konsistensi")+"\";\""+rs.getString("pemeriksaan_eliminasi_bab_warna")+"\";\""+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_jumlah")+"\";\""+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_durasi")+"\";\""+rs.getString("pemeriksaan_eliminasi_bak_warna")+"\";\""+rs.getString("pemeriksaan_eliminasi_bak_lainlain")+"\";\""+rs.getString("pola_aktifitas_mandi")+"\";\""+rs.getString("pola_aktifitas_makanminum")+"\";\""+rs.getString("pola_aktifitas_berpakaian")+"\";\""+rs.getString("pola_aktifitas_eliminasi")+"\";\""+rs.getString("pola_aktifitas_berpindah")+"\";\""+rs.getString("pola_nutrisi_porsi_makan")+"\";\""+rs.getString("pola_nutrisi_frekuesi_makan")+"\";\""+rs.getString("pola_nutrisi_jenis_makanan")+"\";\""+rs.getString("pola_tidur_lama_tidur")+"\";\""+rs.getString("pola_tidur_gangguan")+"\";\""+rs.getString("pengkajian_fungsi_kemampuan_sehari")+"\";\""+rs.getString("pengkajian_fungsi_berjalan")+", "+rs.getString("pengkajian_fungsi_berjalan_keterangan")+"\";\""+rs.getString("pengkajian_fungsi_aktifitas")+"\";\""+rs.getString("pengkajian_fungsi_ambulasi")+"\";\""+rs.getString("pengkajian_fungsi_ekstrimitas_atas")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_atas_keterangan")+"\";\""+rs.getString("pengkajian_fungsi_ekstrimitas_bawah")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_bawah_keterangan")+"\";\""+rs.getString("pengkajian_fungsi_menggenggam")+", "+rs.getString("pengkajian_fungsi_menggenggam_keterangan")+"\";\""+rs.getString("pengkajian_fungsi_koordinasi")+", "+rs.getString("pengkajian_fungsi_koordinasi_keterangan")+"\";\""+rs.getString("pengkajian_fungsi_kesimpulan")+"\";\""+rs.getString("riwayat_psiko_kondisi_psiko")+"\";\""+rs.getString("riwayat_psiko_perilaku")+", "+rs.getString("riwayat_psiko_perilaku_keterangan")+"\";\""+rs.getString("riwayat_psiko_gangguan_jiwa")+"\";\""+rs.getString("riwayat_psiko_hubungan_keluarga")+"\";\""+rs.getString("agama")+"\";\""+rs.getString("riwayat_psiko_tinggal")+", "+rs.getString("riwayat_psiko_tinggal_keterangan")+"\";\""+rs.getString("pekerjaan")+"\";\""+rs.getString("png_jawab")+"\";\""+rs.getString("riwayat_psiko_nilai_kepercayaan")+", "+rs.getString("riwayat_psiko_nilai_kepercayaan_keterangan")+"\";\""+rs.getString("nama_bahasa")+"\";\""+rs.getString("pnd")+"\";\""+rs.getString("riwayat_psiko_pendidikan_pj")+"\";\""+rs.getString("riwayat_psiko_edukasi_diberikan")+", "+rs.getString("riwayat_psiko_edukasi_diberikan_keterangan")+"\";\""+rs.getString("penilaian_nyeri")+"\";\""+rs.getString("penilaian_nyeri_penyebab")+", "+rs.getString("penilaian_nyeri_ket_penyebab")+"\";\""+rs.getString("penilaian_nyeri_kualitas")+", "+rs.getString("penilaian_nyeri_ket_kualitas")+"\";\""+rs.getString("penilaian_nyeri_lokasi")+"\";\""+rs.getString("penilaian_nyeri_menyebar")+"\";\""+rs.getString("penilaian_nyeri_skala")+"\";\""+rs.getString("penilaian_nyeri_waktu")+"\";\""+rs.getString("penilaian_nyeri_hilang")+", "+rs.getString("penilaian_nyeri_ket_hilang")+"\";\""+rs.getString("penilaian_nyeri_diberitahukan_dokter")+", "+rs.getString("penilaian_nyeri_jam_diberitahukan_dokter")+"\";\""+rs.getString("penilaian_jatuhmorse_skala1")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai1")+"\";\""+rs.getString("penilaian_jatuhmorse_skala2")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai2")+"\";\""+rs.getString("penilaian_jatuhmorse_skala3")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai3")+"\";\""+rs.getString("penilaian_jatuhmorse_skala4")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai4")+"\";\""+rs.getString("penilaian_jatuhmorse_skala5")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai5")+"\";\""+rs.getString("penilaian_jatuhmorse_skala6")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai6")+"\";\""+rs.getString("penilaian_jatuhmorse_totalnilai")+"\";\""+rs.getString("penilaian_jatuhsydney_skala1")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai1")+"\";\""+rs.getString("penilaian_jatuhsydney_skala2")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai2")+"\";\""+rs.getString("penilaian_jatuhsydney_skala3")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai3")+"\";\""+rs.getString("penilaian_jatuhsydney_skala4")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai4")+"\";\""+rs.getString("penilaian_jatuhsydney_skala5")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai5")+"\";\""+rs.getString("penilaian_jatuhsydney_skala6")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai6")+"\";\""+rs.getString("penilaian_jatuhsydney_skala7")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai7")+"\";\""+rs.getString("penilaian_jatuhsydney_skala8")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai8")+"\";\""+rs.getString("penilaian_jatuhsydney_skala9")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai9")+"\";\""+rs.getString("penilaian_jatuhsydney_skala10")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai10")+"\";\""+rs.getString("penilaian_jatuhsydney_skala11")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai11")+"\";\""+rs.getString("penilaian_jatuhsydney_totalnilai")+"\";\""+rs.getString("skrining_gizi1")+"\";\""+rs.getString("nilai_gizi1")+"\";\""+rs.getString("skrining_gizi2")+"\";\""+rs.getString("nilai_gizi2")+"\";\""+rs.getString("nilai_total_gizi")+"\";\""+rs.getString("skrining_gizi_diagnosa_khusus")+"\";\""+rs.getString("skrining_gizi_ket_diagnosa_khusus")+"\";\""+rs.getString("skrining_gizi_diketahui_dietisen")+"\";\""+rs.getString("skrining_gizi_jam_diketahui_dietisen")+"\";\""+rs.getString("rencana")+"\"\n"
                                    );
                                }
                                f = new File("RMPenilaianAwalKeperawatanRanap.csv");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(htmlContent.toString());

                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break; 
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
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                ChkAccor.setSelected(true);
                isMenu();
                getMasalah();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    ChkAccor.setSelected(true);
                    isMenu();
                    getMasalah();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
tampilMasalah();
//        try {
//            if(Valid.daysOld("./cache/masalahkeperawatan.iyem")<8){
//                tampilMasalah2();
//            }else{
//                tampilMasalah();
//            }
//        } catch (Exception e) {
//        }
//        
//        try {
//            if(Valid.daysOld("./cache/rencanakeperawatan.iyem")>=7){
//                tampilRencana();
//            }
//        } catch (Exception e) {
//        }
    }//GEN-LAST:event_formWindowOpened

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isMenu();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            param.put("nyeri",Sequel.cariGambar("select nyeri from gambar")); 
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),63).toString()));
            try {
                ps=koneksi.prepareStatement("select * from riwayat_persalinan_pasien where no_rkm_medis=? order by tgl_thn");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                    rs=ps.executeQuery();
                    i=1;
                    while(rs.next()){
                        param.put("no"+i,i+"");  
                        param.put("tgl"+i,rs.getString("tgl_thn"));
                        param.put("tempatpersalinan"+i,rs.getString("tempat_persalinan"));
                        param.put("usiahamil"+i,rs.getString("usia_hamil"));
                        param.put("jenispersalinan"+i,rs.getString("jenis_persalinan"));
                        param.put("penolong"+i,rs.getString("penolong"));
                        param.put("penyulit"+i,rs.getString("penyulit"));
                        param.put("jk"+i,rs.getString("jk"));
                        param.put("bbpb"+i,rs.getString("bbpb"));
                        param.put("keadaan"+i,rs.getString("keadaan"));
                        i++;
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
            
            Valid.MyReportqry("rptCetakPenilaianAwalKebidananRanap.jasper","report","::[ Laporan Penilaian Awal Ralan Kebidanan & Kandungan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,penilaian_awal_keperawatan_kebidanan.tanggal,"+
                "penilaian_awal_keperawatan_kebidanan.informasi,penilaian_awal_keperawatan_kebidanan.td,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.bb,"+
                "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.gcs,penilaian_awal_keperawatan_kebidanan.bb,"+
                "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.bmi,penilaian_awal_keperawatan_kebidanan.lila,penilaian_awal_keperawatan_kebidanan.tfu,penilaian_awal_keperawatan_kebidanan.tbj,penilaian_awal_keperawatan_kebidanan.letak,"+
                "penilaian_awal_keperawatan_kebidanan.presentasi,penilaian_awal_keperawatan_kebidanan.penurunan,penilaian_awal_keperawatan_kebidanan.his,penilaian_awal_keperawatan_kebidanan.kekuatan,penilaian_awal_keperawatan_kebidanan.lamanya,penilaian_awal_keperawatan_kebidanan.bjj,"+
                "penilaian_awal_keperawatan_kebidanan.ket_bjj,penilaian_awal_keperawatan_kebidanan.portio,penilaian_awal_keperawatan_kebidanan.serviks,penilaian_awal_keperawatan_kebidanan.ketuban,penilaian_awal_keperawatan_kebidanan.hodge,penilaian_awal_keperawatan_kebidanan.inspekulo,"+
                "penilaian_awal_keperawatan_kebidanan.ket_inspekulo,penilaian_awal_keperawatan_kebidanan.ctg,penilaian_awal_keperawatan_kebidanan.ket_ctg,penilaian_awal_keperawatan_kebidanan.usg,penilaian_awal_keperawatan_kebidanan.ket_usg,penilaian_awal_keperawatan_kebidanan.lab,"+
                "penilaian_awal_keperawatan_kebidanan.ket_lab,penilaian_awal_keperawatan_kebidanan.lakmus,penilaian_awal_keperawatan_kebidanan.ket_lakmus,penilaian_awal_keperawatan_kebidanan.panggul,penilaian_awal_keperawatan_kebidanan.keluhan_utama,penilaian_awal_keperawatan_kebidanan.umur,"+
                "penilaian_awal_keperawatan_kebidanan.lama,penilaian_awal_keperawatan_kebidanan.banyaknya,penilaian_awal_keperawatan_kebidanan.haid,penilaian_awal_keperawatan_kebidanan.siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus1,"+
                "penilaian_awal_keperawatan_kebidanan.status,penilaian_awal_keperawatan_kebidanan.kali,penilaian_awal_keperawatan_kebidanan.usia1,penilaian_awal_keperawatan_kebidanan.ket1,penilaian_awal_keperawatan_kebidanan.usia2,penilaian_awal_keperawatan_kebidanan.ket2,"+
                "penilaian_awal_keperawatan_kebidanan.usia3,penilaian_awal_keperawatan_kebidanan.ket3,penilaian_awal_keperawatan_kebidanan.hpht,penilaian_awal_keperawatan_kebidanan.usia_kehamilan,penilaian_awal_keperawatan_kebidanan.tp,penilaian_awal_keperawatan_kebidanan.imunisasi,"+
                "penilaian_awal_keperawatan_kebidanan.ket_imunisasi,penilaian_awal_keperawatan_kebidanan.g,penilaian_awal_keperawatan_kebidanan.p,penilaian_awal_keperawatan_kebidanan.a,penilaian_awal_keperawatan_kebidanan.hidup,penilaian_awal_keperawatan_kebidanan.ginekologi,"+
                "penilaian_awal_keperawatan_kebidanan.kebiasaan,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan,penilaian_awal_keperawatan_kebidanan.kebiasaan1,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan1,penilaian_awal_keperawatan_kebidanan.kebiasaan2,"+
                "penilaian_awal_keperawatan_kebidanan.ket_kebiasaan2,penilaian_awal_keperawatan_kebidanan.kebiasaan3,penilaian_awal_keperawatan_kebidanan.kb,penilaian_awal_keperawatan_kebidanan.ket_kb,penilaian_awal_keperawatan_kebidanan.komplikasi,"+
                "penilaian_awal_keperawatan_kebidanan.ket_komplikasi,penilaian_awal_keperawatan_kebidanan.berhenti,penilaian_awal_keperawatan_kebidanan.alasan,penilaian_awal_keperawatan_kebidanan.alat_bantu,penilaian_awal_keperawatan_kebidanan.ket_bantu,"+
                "penilaian_awal_keperawatan_kebidanan.prothesa,penilaian_awal_keperawatan_kebidanan.ket_pro,penilaian_awal_keperawatan_kebidanan.adl,penilaian_awal_keperawatan_kebidanan.status_psiko,penilaian_awal_keperawatan_kebidanan.ket_psiko,"+
                "penilaian_awal_keperawatan_kebidanan.hub_keluarga,penilaian_awal_keperawatan_kebidanan.tinggal_dengan,penilaian_awal_keperawatan_kebidanan.ket_tinggal,penilaian_awal_keperawatan_kebidanan.ekonomi,penilaian_awal_keperawatan_kebidanan.budaya,"+
                "penilaian_awal_keperawatan_kebidanan.ket_budaya,penilaian_awal_keperawatan_kebidanan.edukasi,penilaian_awal_keperawatan_kebidanan.ket_edukasi,penilaian_awal_keperawatan_kebidanan.berjalan_a,penilaian_awal_keperawatan_kebidanan.berjalan_b,"+
                "penilaian_awal_keperawatan_kebidanan.berjalan_c,penilaian_awal_keperawatan_kebidanan.hasil,penilaian_awal_keperawatan_kebidanan.lapor,penilaian_awal_keperawatan_kebidanan.ket_lapor,penilaian_awal_keperawatan_kebidanan.sg1,"+
                "penilaian_awal_keperawatan_kebidanan.nilai1,penilaian_awal_keperawatan_kebidanan.sg2,penilaian_awal_keperawatan_kebidanan.nilai2,penilaian_awal_keperawatan_kebidanan.total_hasil,penilaian_awal_keperawatan_kebidanan.nyeri,"+
                "penilaian_awal_keperawatan_kebidanan.provokes,penilaian_awal_keperawatan_kebidanan.ket_provokes,penilaian_awal_keperawatan_kebidanan.quality,penilaian_awal_keperawatan_kebidanan.ket_quality,penilaian_awal_keperawatan_kebidanan.lokasi,"+
                "penilaian_awal_keperawatan_kebidanan.menyebar,penilaian_awal_keperawatan_kebidanan.skala_nyeri,penilaian_awal_keperawatan_kebidanan.durasi,penilaian_awal_keperawatan_kebidanan.nyeri_hilang,penilaian_awal_keperawatan_kebidanan.ket_nyeri,"+
                "penilaian_awal_keperawatan_kebidanan.pada_dokter,penilaian_awal_keperawatan_kebidanan.ket_dokter,penilaian_awal_keperawatan_kebidanan.masalah,penilaian_awal_keperawatan_kebidanan.tindakan,penilaian_awal_keperawatan_kebidanan.nip,petugas.nama "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_kebidanan on reg_periksa.no_rawat=penilaian_awal_keperawatan_kebidanan.no_rawat "+
                "inner join petugas on penilaian_awal_keperawatan_kebidanan.nip=petugas.nip "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            
            Valid.MyReportqry("rptCetakPenilaianAwalKebidananRalan2.jasper","report","::[ Laporan Penilaian Awal Ralan Kebidanan & Kandungan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,penilaian_awal_keperawatan_kebidanan.tanggal,"+
                "penilaian_awal_keperawatan_kebidanan.informasi,penilaian_awal_keperawatan_kebidanan.td,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.bb,"+
                "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.gcs,penilaian_awal_keperawatan_kebidanan.bb,"+
                "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.bmi,penilaian_awal_keperawatan_kebidanan.lila,penilaian_awal_keperawatan_kebidanan.tfu,penilaian_awal_keperawatan_kebidanan.tbj,penilaian_awal_keperawatan_kebidanan.letak,"+
                "penilaian_awal_keperawatan_kebidanan.presentasi,penilaian_awal_keperawatan_kebidanan.penurunan,penilaian_awal_keperawatan_kebidanan.his,penilaian_awal_keperawatan_kebidanan.kekuatan,penilaian_awal_keperawatan_kebidanan.lamanya,penilaian_awal_keperawatan_kebidanan.bjj,"+
                "penilaian_awal_keperawatan_kebidanan.ket_bjj,penilaian_awal_keperawatan_kebidanan.portio,penilaian_awal_keperawatan_kebidanan.serviks,penilaian_awal_keperawatan_kebidanan.ketuban,penilaian_awal_keperawatan_kebidanan.hodge,penilaian_awal_keperawatan_kebidanan.inspekulo,"+
                "penilaian_awal_keperawatan_kebidanan.ket_inspekulo,penilaian_awal_keperawatan_kebidanan.ctg,penilaian_awal_keperawatan_kebidanan.ket_ctg,penilaian_awal_keperawatan_kebidanan.usg,penilaian_awal_keperawatan_kebidanan.ket_usg,penilaian_awal_keperawatan_kebidanan.lab,"+
                "penilaian_awal_keperawatan_kebidanan.ket_lab,penilaian_awal_keperawatan_kebidanan.lakmus,penilaian_awal_keperawatan_kebidanan.ket_lakmus,penilaian_awal_keperawatan_kebidanan.panggul,penilaian_awal_keperawatan_kebidanan.keluhan_utama,penilaian_awal_keperawatan_kebidanan.umur,"+
                "penilaian_awal_keperawatan_kebidanan.lama,penilaian_awal_keperawatan_kebidanan.banyaknya,penilaian_awal_keperawatan_kebidanan.haid,penilaian_awal_keperawatan_kebidanan.siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus1,"+
                "penilaian_awal_keperawatan_kebidanan.status,penilaian_awal_keperawatan_kebidanan.kali,penilaian_awal_keperawatan_kebidanan.usia1,penilaian_awal_keperawatan_kebidanan.ket1,penilaian_awal_keperawatan_kebidanan.usia2,penilaian_awal_keperawatan_kebidanan.ket2,"+
                "penilaian_awal_keperawatan_kebidanan.usia3,penilaian_awal_keperawatan_kebidanan.ket3,penilaian_awal_keperawatan_kebidanan.hpht,penilaian_awal_keperawatan_kebidanan.usia_kehamilan,penilaian_awal_keperawatan_kebidanan.tp,penilaian_awal_keperawatan_kebidanan.imunisasi,"+
                "penilaian_awal_keperawatan_kebidanan.ket_imunisasi,penilaian_awal_keperawatan_kebidanan.g,penilaian_awal_keperawatan_kebidanan.p,penilaian_awal_keperawatan_kebidanan.a,penilaian_awal_keperawatan_kebidanan.hidup,penilaian_awal_keperawatan_kebidanan.ginekologi,"+
                "penilaian_awal_keperawatan_kebidanan.kebiasaan,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan,penilaian_awal_keperawatan_kebidanan.kebiasaan1,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan1,penilaian_awal_keperawatan_kebidanan.kebiasaan2,"+
                "penilaian_awal_keperawatan_kebidanan.ket_kebiasaan2,penilaian_awal_keperawatan_kebidanan.kebiasaan3,penilaian_awal_keperawatan_kebidanan.kb,penilaian_awal_keperawatan_kebidanan.ket_kb,penilaian_awal_keperawatan_kebidanan.komplikasi,"+
                "penilaian_awal_keperawatan_kebidanan.ket_komplikasi,penilaian_awal_keperawatan_kebidanan.berhenti,penilaian_awal_keperawatan_kebidanan.alasan,penilaian_awal_keperawatan_kebidanan.alat_bantu,penilaian_awal_keperawatan_kebidanan.ket_bantu,"+
                "penilaian_awal_keperawatan_kebidanan.prothesa,penilaian_awal_keperawatan_kebidanan.ket_pro,penilaian_awal_keperawatan_kebidanan.adl,penilaian_awal_keperawatan_kebidanan.status_psiko,penilaian_awal_keperawatan_kebidanan.ket_psiko,"+
                "penilaian_awal_keperawatan_kebidanan.hub_keluarga,penilaian_awal_keperawatan_kebidanan.tinggal_dengan,penilaian_awal_keperawatan_kebidanan.ket_tinggal,penilaian_awal_keperawatan_kebidanan.ekonomi,penilaian_awal_keperawatan_kebidanan.budaya,"+
                "penilaian_awal_keperawatan_kebidanan.ket_budaya,penilaian_awal_keperawatan_kebidanan.edukasi,penilaian_awal_keperawatan_kebidanan.ket_edukasi,penilaian_awal_keperawatan_kebidanan.berjalan_a,penilaian_awal_keperawatan_kebidanan.berjalan_b,"+
                "penilaian_awal_keperawatan_kebidanan.berjalan_c,penilaian_awal_keperawatan_kebidanan.hasil,penilaian_awal_keperawatan_kebidanan.lapor,penilaian_awal_keperawatan_kebidanan.ket_lapor,penilaian_awal_keperawatan_kebidanan.sg1,"+
                "penilaian_awal_keperawatan_kebidanan.nilai1,penilaian_awal_keperawatan_kebidanan.sg2,penilaian_awal_keperawatan_kebidanan.nilai2,penilaian_awal_keperawatan_kebidanan.total_hasil,penilaian_awal_keperawatan_kebidanan.nyeri,"+
                "penilaian_awal_keperawatan_kebidanan.provokes,penilaian_awal_keperawatan_kebidanan.ket_provokes,penilaian_awal_keperawatan_kebidanan.quality,penilaian_awal_keperawatan_kebidanan.ket_quality,penilaian_awal_keperawatan_kebidanan.lokasi,"+
                "penilaian_awal_keperawatan_kebidanan.menyebar,penilaian_awal_keperawatan_kebidanan.skala_nyeri,penilaian_awal_keperawatan_kebidanan.durasi,penilaian_awal_keperawatan_kebidanan.nyeri_hilang,penilaian_awal_keperawatan_kebidanan.ket_nyeri,"+
                "penilaian_awal_keperawatan_kebidanan.pada_dokter,penilaian_awal_keperawatan_kebidanan.ket_dokter,penilaian_awal_keperawatan_kebidanan.masalah,penilaian_awal_keperawatan_kebidanan.tindakan,penilaian_awal_keperawatan_kebidanan.nip,petugas.nama "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_kebidanan on reg_periksa.no_rawat=penilaian_awal_keperawatan_kebidanan.no_rawat "+
                "inner join petugas on penilaian_awal_keperawatan_kebidanan.nip=petugas.nip "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }  
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,BtnDPJP,Cara);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,Cara,KetAnamnesis);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,BtnSimpan,BtnPetugas2);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        i=1;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed

    }//GEN-LAST:event_KdPetugasKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnPetugas);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void BtnPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugas2ActionPerformed
        i=2;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugas2ActionPerformed

    private void BtnPetugas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugas2KeyPressed
        Valid.pindah(evt,BtnPetugas,BtnDPJP);
    }//GEN-LAST:event_BtnPetugas2KeyPressed

    private void KdPetugas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugas2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugas2KeyPressed

    private void KdDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDPJPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDPJPKeyPressed

    private void BtnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDPJPActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDPJPActionPerformed

    private void BtnDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDPJPKeyPressed
        Valid.pindah(evt,BtnPetugas2,Cara);
    }//GEN-LAST:event_BtnDPJPKeyPressed

    private void TibadiRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TibadiRuangKeyPressed
        Valid.pindah(evt,KetAnamnesis,CaraMasuk);
    }//GEN-LAST:event_TibadiRuangKeyPressed

    private void CaraMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraMasukKeyPressed
        Valid.pindah(evt,TibadiRuang,RPS);
    }//GEN-LAST:event_CaraMasukKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,CaraMasuk,Cara);
    }//GEN-LAST:event_RPSKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah2(evt,Cara,RPO);
    }//GEN-LAST:event_RPKKeyPressed

    private void KetAnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesisKeyPressed
        Valid.pindah(evt,Anamnesis,TibadiRuang);
    }//GEN-LAST:event_KetAnamnesisKeyPressed

    private void ImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImunisasiKeyPressed
        Valid.pindah(evt,RPO,Cara);
    }//GEN-LAST:event_ImunisasiKeyPressed

    private void RPIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPIKeyPressed
        Valid.pindah(evt,Cara,KetRPI);
    }//GEN-LAST:event_RPIKeyPressed

    private void KetRPIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRPIKeyPressed
        Valid.pindah(evt,RPI,Cara);
    }//GEN-LAST:event_KetRPIKeyPressed

    private void KebiasaanMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanMerokokKeyPressed
        Valid.pindah(evt,Alergi,KebiasaanJumlahRokok);
    }//GEN-LAST:event_KebiasaanMerokokKeyPressed

    private void KebiasaanJumlahRokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanJumlahRokokKeyPressed
        Valid.pindah(evt,KebiasaanMerokok,KebiasaanAlkohol);
    }//GEN-LAST:event_KebiasaanJumlahRokokKeyPressed

    private void KebiasaanAlkoholKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanAlkoholKeyPressed
        Valid.pindah(evt,KebiasaanJumlahRokok,KebiasaanJumlahAlkohol);
    }//GEN-LAST:event_KebiasaanAlkoholKeyPressed

    private void KebiasaanJumlahAlkoholKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanJumlahAlkoholKeyPressed
        Valid.pindah(evt,KebiasaanAlkohol,KebiasaanNarkoba);
    }//GEN-LAST:event_KebiasaanJumlahAlkoholKeyPressed

    private void KebiasaanNarkobaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanNarkobaKeyPressed
        Valid.pindah(evt,KebiasaanJumlahAlkohol,KebiasaanObat);
    }//GEN-LAST:event_KebiasaanNarkobaKeyPressed

    private void KebiasaanObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanObatKeyPressed
        Valid.pindah(evt,KebiasaanNarkoba,KesadaranMental);
    }//GEN-LAST:event_KebiasaanObatKeyPressed

    private void KesadaranMentalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranMentalKeyPressed
        Valid.pindah(evt,KebiasaanObat,KeadaanMentalUmum);
    }//GEN-LAST:event_KesadaranMentalKeyPressed

    private void KeadaanMentalUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanMentalUmumKeyPressed
        Valid.pindah(evt,KesadaranMental,GCS);
    }//GEN-LAST:event_KeadaanMentalUmumKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt,KeadaanMentalUmum,KesadaranMental);
    }//GEN-LAST:event_GCSKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,KesadaranMental,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,SpO2);
    }//GEN-LAST:event_SuhuKeyPressed

    private void SpO2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpO2KeyPressed
        Valid.pindah(evt,Suhu,BB);
    }//GEN-LAST:event_SpO2KeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,SpO2,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,SistemSarafKepala);
    }//GEN-LAST:event_TBKeyPressed

    private void SistemSarafKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafKepalaKeyPressed
        Valid.pindah(evt,TB,KetSistemSarafKepala);
    }//GEN-LAST:event_SistemSarafKepalaKeyPressed

    private void KetSistemSarafKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSistemSarafKepalaKeyPressed
        Valid.pindah(evt,SistemSarafKepala,SistemSarafWajah);
    }//GEN-LAST:event_KetSistemSarafKepalaKeyPressed

    private void SistemSarafWajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafWajahKeyPressed
        Valid.pindah(evt,KetSistemSarafKepala,KetSistemSarafWajah);
    }//GEN-LAST:event_SistemSarafWajahKeyPressed

    private void KetSistemSarafWajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSistemSarafWajahKeyPressed
        Valid.pindah(evt,SistemSarafWajah,KetSistemSarafWajah);
    }//GEN-LAST:event_KetSistemSarafWajahKeyPressed

    private void SistemSarafKejangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafKejangKeyPressed
        Valid.pindah(evt,KetSistemSarafWajah,KetSistemSarafKejang);
    }//GEN-LAST:event_SistemSarafKejangKeyPressed

    private void KetSistemSarafKejangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSistemSarafKejangKeyPressed
        Valid.pindah(evt,SistemSarafKejang,KetSistemSarafWajah);
    }//GEN-LAST:event_KetSistemSarafKejangKeyPressed

    private void KardiovaskularPulsasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KardiovaskularPulsasiKeyPressed
        Valid.pindah(evt,KetSistemSarafWajah,KardiovaskularSirkulasi);
    }//GEN-LAST:event_KardiovaskularPulsasiKeyPressed

    private void KardiovaskularSirkulasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KardiovaskularSirkulasiKeyPressed
        Valid.pindah(evt,KardiovaskularPulsasi,KetKardiovaskularSirkulasi);
    }//GEN-LAST:event_KardiovaskularSirkulasiKeyPressed

    private void KetKardiovaskularSirkulasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKardiovaskularSirkulasiKeyPressed
        Valid.pindah(evt,KardiovaskularSirkulasi,KardiovaskularDenyutNadi);
    }//GEN-LAST:event_KetKardiovaskularSirkulasiKeyPressed

    private void KardiovaskularDenyutNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KardiovaskularDenyutNadiKeyPressed
        Valid.pindah(evt,KetKardiovaskularSirkulasi,RespirasiRetraksi);
    }//GEN-LAST:event_KardiovaskularDenyutNadiKeyPressed

    private void RespirasiRetraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiRetraksiKeyPressed
        Valid.pindah(evt,KardiovaskularDenyutNadi,RespirasiPolaNafas);
    }//GEN-LAST:event_RespirasiRetraksiKeyPressed

    private void RespirasiPolaNafasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiPolaNafasKeyPressed
        Valid.pindah(evt,RespirasiRetraksi,RespirasiSuaraNafas);
    }//GEN-LAST:event_RespirasiPolaNafasKeyPressed

    private void RespirasiSuaraNafasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiSuaraNafasKeyPressed
        Valid.pindah(evt,RespirasiPolaNafas,KetSistemSarafWajah);
    }//GEN-LAST:event_RespirasiSuaraNafasKeyPressed

    private void RespirasiJenisPernafasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiJenisPernafasanKeyPressed
        Valid.pindah(evt,KetSistemSarafWajah,KetRespirasiJenisPernafasan);
    }//GEN-LAST:event_RespirasiJenisPernafasanKeyPressed

    private void KetRespirasiJenisPernafasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRespirasiJenisPernafasanKeyPressed
        Valid.pindah(evt,RespirasiJenisPernafasan,KetSistemSarafWajah);
    }//GEN-LAST:event_KetRespirasiJenisPernafasanKeyPressed

    private void GastrointestinalMulutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalMulutKeyPressed
        Valid.pindah(evt,KetSistemSarafWajah,KetGastrointestinalMulut);
    }//GEN-LAST:event_GastrointestinalMulutKeyPressed

    private void KetGastrointestinalMulutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalMulutKeyPressed
        Valid.pindah(evt,GastrointestinalMulut,GastrointestinalLidah);
    }//GEN-LAST:event_KetGastrointestinalMulutKeyPressed

    private void GastrointestinalLidahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalLidahKeyPressed
        Valid.pindah(evt,KetGastrointestinalMulut,KetGastrointestinalLidah);
    }//GEN-LAST:event_GastrointestinalLidahKeyPressed

    private void KetGastrointestinalLidahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalLidahKeyPressed
        Valid.pindah(evt,GastrointestinalLidah,KetSistemSarafWajah);
    }//GEN-LAST:event_KetGastrointestinalLidahKeyPressed

    private void GastrointestinalTenggorakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalTenggorakanKeyPressed
        Valid.pindah(evt,KetSistemSarafWajah,KetGastrointestinalTenggorakan);
    }//GEN-LAST:event_GastrointestinalTenggorakanKeyPressed

    private void KetGastrointestinalTenggorakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalTenggorakanKeyPressed
        Valid.pindah(evt,GastrointestinalTenggorakan,GastrointestinalAbdomen);
    }//GEN-LAST:event_KetGastrointestinalTenggorakanKeyPressed

    private void GastrointestinalAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalAbdomenKeyPressed
        Valid.pindah(evt,KetGastrointestinalTenggorakan,KetGastrointestinalAbdomen);
    }//GEN-LAST:event_GastrointestinalAbdomenKeyPressed

    private void KetGastrointestinalAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalAbdomenKeyPressed
        Valid.pindah(evt,GastrointestinalAbdomen,KetSistemSarafWajah);
    }//GEN-LAST:event_KetGastrointestinalAbdomenKeyPressed

    private void NeurologiMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiMataKeyPressed
        Valid.pindah(evt,KetSistemSarafWajah,NeurologiSklera);
    }//GEN-LAST:event_NeurologiMataKeyPressed

    private void NeurologiPendengaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiPendengaranKeyPressed
        Valid.pindah(evt,KetSistemSarafWajah,NeurologiSklera);
    }//GEN-LAST:event_NeurologiPendengaranKeyPressed

    private void IntegumentKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentKulitKeyPressed
        Valid.pindah(evt,NeurologiSklera,IntegumentWarnaKulit);
    }//GEN-LAST:event_IntegumentKulitKeyPressed

    private void IntegumentWarnaKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentWarnaKulitKeyPressed
        Valid.pindah(evt,IntegumentKulit,IntegumentTurgor);
    }//GEN-LAST:event_IntegumentWarnaKulitKeyPressed

    private void IntegumentTurgorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentTurgorKeyPressed
        Valid.pindah(evt,IntegumentWarnaKulit,IntegumentVernic);
    }//GEN-LAST:event_IntegumentTurgorKeyPressed

    private void IntegumentVernicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentVernicKeyPressed
        Valid.pindah(evt,IntegumentTurgor,ReproduksiLaki);
    }//GEN-LAST:event_IntegumentVernicKeyPressed

    private void ReproduksiLakiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReproduksiLakiKeyPressed
        Valid.pindah(evt,IntegumentVernic,KetReproduksiLaki);
    }//GEN-LAST:event_ReproduksiLakiKeyPressed

    private void KetReproduksiLakiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetReproduksiLakiKeyPressed
        Valid.pindah(evt,ReproduksiLaki,MuskuloskletalRekoil);
    }//GEN-LAST:event_KetReproduksiLakiKeyPressed

    private void KetReproduksiPerempuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetReproduksiPerempuanKeyPressed
        Valid.pindah(evt,ReproduksiPerempuan,MuskuloskletalRekoil);
    }//GEN-LAST:event_KetReproduksiPerempuanKeyPressed

    private void ReproduksiPerempuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReproduksiPerempuanKeyPressed
        Valid.pindah(evt,MuskuloskletalGaris,KetReproduksiPerempuan);
    }//GEN-LAST:event_ReproduksiPerempuanKeyPressed

    private void MuskuloskletalRekoilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskuloskletalRekoilKeyPressed
        Valid.pindah(evt,KetReproduksiLaki,MuskuloskletalGaris);
    }//GEN-LAST:event_MuskuloskletalRekoilKeyPressed

    private void MuskuloskletalGarisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskuloskletalGarisKeyPressed
        Valid.pindah(evt,MuskuloskletalRekoil,ReproduksiPerempuan);
    }//GEN-LAST:event_MuskuloskletalGarisKeyPressed

    private void KondisiPsikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiPsikologisKeyPressed
        Valid.pindah(evt,MuskuloskletalRekoil,AdakahPerilaku);
    }//GEN-LAST:event_KondisiPsikologisKeyPressed

    private void AdakahPerilakuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdakahPerilakuKeyPressed
        Valid.pindah(evt,KondisiPsikologis,KeteranganAdakahPerilaku);
    }//GEN-LAST:event_AdakahPerilakuKeyPressed

    private void KeteranganAdakahPerilakuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAdakahPerilakuKeyPressed
        Valid.pindah(evt,AdakahPerilaku,GangguanJiwa);
    }//GEN-LAST:event_KeteranganAdakahPerilakuKeyPressed

    private void GangguanJiwaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GangguanJiwaKeyPressed
        Valid.pindah(evt,KeteranganAdakahPerilaku,HubunganAnggotaKeluarga);
    }//GEN-LAST:event_GangguanJiwaKeyPressed

    private void HubunganAnggotaKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganAnggotaKeluargaKeyPressed
        Valid.pindah(evt,GangguanJiwa,TinggalDengan);
    }//GEN-LAST:event_HubunganAnggotaKeluargaKeyPressed

    private void TinggalDenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggalDenganKeyPressed
        Valid.pindah(evt,HubunganAnggotaKeluarga,KeteranganTinggalDengan);
    }//GEN-LAST:event_TinggalDenganKeyPressed

    private void KeteranganTinggalDenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTinggalDenganKeyPressed
        Valid.pindah(evt,TinggalDengan,NilaiKepercayaan);
    }//GEN-LAST:event_KeteranganTinggalDenganKeyPressed

    private void NilaiKepercayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiKepercayaanKeyPressed
        Valid.pindah(evt,KeteranganTinggalDengan,KeteranganNilaiKepercayaan);
    }//GEN-LAST:event_NilaiKepercayaanKeyPressed

    private void KeteranganNilaiKepercayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganNilaiKepercayaanKeyPressed
        Valid.pindah(evt,NilaiKepercayaan,PendidikanPJ);
    }//GEN-LAST:event_KeteranganNilaiKepercayaanKeyPressed

    private void PendidikanPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendidikanPJKeyPressed
        Valid.pindah(evt,KeteranganNilaiKepercayaan,EdukasiPsikolgis);
    }//GEN-LAST:event_PendidikanPJKeyPressed

    private void EdukasiPsikolgisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiPsikolgisKeyPressed
        Valid.pindah(evt,PendidikanPJ,KeteranganEdukasiPsikologis);
    }//GEN-LAST:event_EdukasiPsikolgisKeyPressed

    private void KeteranganEdukasiPsikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEdukasiPsikologisKeyPressed
        Valid.pindah(evt,EdukasiPsikolgis,MuskuloskletalRekoil);
    }//GEN-LAST:event_KeteranganEdukasiPsikologisKeyPressed

    private void SkalaGizi1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaGizi1ItemStateChanged
        if(SkalaGizi1.getSelectedIndex()==0){
            NilaiGizi1.setText("1");
        }else if(SkalaGizi1.getSelectedIndex()==1){
            NilaiGizi1.setText("0");
        }
        isTotalGizi();
    }//GEN-LAST:event_SkalaGizi1ItemStateChanged

    private void SkalaGizi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaGizi1KeyPressed
        Valid.pindah(evt,MuskuloskletalRekoil,SkalaGizi2);
    }//GEN-LAST:event_SkalaGizi1KeyPressed

    private void SkalaGizi2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaGizi2ItemStateChanged
        if(SkalaGizi2.getSelectedIndex()==0){
            NilaiGizi2.setText("1");
        }else if(SkalaGizi2.getSelectedIndex()==1){
            NilaiGizi2.setText("0");
        }else if(SkalaGizi2.getSelectedIndex()==2){
        }
        isTotalGizi();
    }//GEN-LAST:event_SkalaGizi2ItemStateChanged

    private void SkalaGizi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaGizi2KeyPressed
        Valid.pindah(evt,SkalaGizi1,MuskuloskletalRekoil);
    }//GEN-LAST:event_SkalaGizi2KeyPressed

    private void DiketahuiDietisenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiketahuiDietisenKeyPressed
        Valid.pindah(evt,MuskuloskletalRekoil,KeteranganDiketahuiDietisen);
    }//GEN-LAST:event_DiketahuiDietisenKeyPressed

    private void KeteranganDiketahuiDietisenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganDiketahuiDietisenKeyPressed
        Valid.pindah(evt,DiketahuiDietisen,TCariMasalah);
    }//GEN-LAST:event_KeteranganDiketahuiDietisenKeyPressed

    private void tbMasalahKeperawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMasalahKeperawatanMouseClicked
//        if(tabModeMasalah.getRowCount()!=0){
//            try {
//                tampilRencana2();
//            } catch (java.lang.NullPointerException e) {
//            }
//        }
    }//GEN-LAST:event_tbMasalahKeperawatanMouseClicked

    private void tbMasalahKeperawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMasalahKeperawatanKeyPressed
        if(tabModeMasalah.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCariMasalah.setText("");
                TCariMasalah.requestFocus();
            }
        }
    }//GEN-LAST:event_tbMasalahKeperawatanKeyPressed

    private void tbMasalahKeperawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMasalahKeperawatanKeyReleased
//        if(tabModeMasalah.getRowCount()!=0){
//            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
//                try {
//                    tampilRencana2();
//                } catch (java.lang.NullPointerException e) {
//                }
//            }
//        }
    }//GEN-LAST:event_tbMasalahKeperawatanKeyReleased

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterMasalahKeperawatan form=new MasterMasalahKeperawatan(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void BtnAllMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllMasalahActionPerformed
        TCari.setText("");
        //tampilMasalah();
    }//GEN-LAST:event_BtnAllMasalahActionPerformed

    private void BtnAllMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllMasalahActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCariMasalah, TCariMasalah);
        }
    }//GEN-LAST:event_BtnAllMasalahKeyPressed

    private void BtnCariMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariMasalahActionPerformed
        tampilMasalah2();
    }//GEN-LAST:event_BtnCariMasalahActionPerformed

    private void BtnCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KeteranganDiketahuiDietisen.requestFocus();
        }
    }//GEN-LAST:event_BtnCariMasalahKeyPressed

    private void TCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KeteranganDiketahuiDietisen.requestFocus();
        }
    }//GEN-LAST:event_TCariMasalahKeyPressed

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RencanaKeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        akses.setform("RMPenilaianAwalKeperawatanRanap");
        masterr.isCek();
        masterr.onCari();
        masterr.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        masterr.setLocationRelativeTo(internalFrame1);
        masterr.setVisible(true);
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void SuhuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyTyped
        char kata= evt.getKeyChar();
        if(!((kata>='0') && (kata<='9') || (kata== KeyEvent.VK_PERIOD) || (kata== KeyEvent.VK_BACK_SPACE))){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Hanya diperbolehkan menginputkan Angka dan Titik");
        }
    }//GEN-LAST:event_SuhuKeyTyped

    private void SpO2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpO2KeyTyped
        char kata= evt.getKeyChar();
        if(!((kata>='0') && (kata<='9')  || (kata== KeyEvent.VK_BACK_SPACE))){
            evt.consume();
            JOptionPane.showMessageDialog(null,"Hanya diperbolehkan menginputkan Angka ");
        }
    }//GEN-LAST:event_SpO2KeyTyped

    private void GKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GKeyPressed

    private void PKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PKeyPressed

    private void AKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AKeyPressed

    private void UKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UKKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RPOKeyPressed

    private void RDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RDKeyPressed

    private void IndikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IndikasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IndikasiKeyPressed

    private void GiziIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GiziIbuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GiziIbuKeyPressed

    private void CaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CaraKeyPressed

    private void KetCaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetCaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetCaraKeyPressed

    private void BB1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BB1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BB1KeyPressed

    private void PBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PBKeyPressed

    private void LKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LKKeyPressed

    private void LDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LDKeyPressed

    private void LetakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LetakKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LetakKeyPressed

    private void KetubanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetubanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetubanKeyPressed

    private void TaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TaliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TaliKeyPressed

    private void TanggalLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalLahirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalLahirKeyPressed

    private void KondisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KondisiKeyPressed

    private void ApgarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ApgarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ApgarKeyPressed

    private void LPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LPKeyPressed

    private void G1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G1KeyPressed

    private void P1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_P1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_P1KeyPressed

    private void A1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_A1KeyPressed

    private void UK1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UK1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UK1KeyPressed

    private void MayorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MayorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MayorKeyPressed

    private void MinorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MinorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MinorKeyPressed

    private void NutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NutrisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NutrisiKeyPressed

    private void KetNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNutrisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetNutrisiKeyPressed

    private void KaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KaliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KaliKeyPressed

    private void FrekuensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FrekuensiKeyPressed

    private void KeluhanBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeluhanBAKKeyPressed

    private void KeluhanBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeluhanBABKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlergiKeyPressed

    private void AlergiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlergiObatKeyPressed

    private void ReaksiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReaksiObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReaksiObatKeyPressed

    private void AlergiMakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiMakananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlergiMakananKeyPressed

    private void ReaksiMakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReaksiMakananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReaksiMakananKeyPressed

    private void AlergiLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlergiLainnyaKeyPressed

    private void ReaksiLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReaksiLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReaksiLainnyaKeyPressed

    private void KetObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetObatKeyPressed

    private void DownesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DownesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DownesKeyPressed

    private void LK1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LK1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LK1KeyPressed

    private void LD1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LD1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LD1KeyPressed

    private void LP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LP1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LP1KeyPressed

    private void DarahBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DarahBayiKeyPressed

    private void RhBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RhBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RhBayiKeyPressed

    private void DarahIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahIbuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DarahIbuKeyPressed

    private void RhIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RhIbuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RhIbuKeyPressed

    private void DarahAyahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahAyahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DarahAyahKeyPressed

    private void RhAyahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RhAyahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RhAyahKeyPressed

    private void GerakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GerakKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GerakKeyPressed

    private void SistemSarafUbunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafUbunKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SistemSarafUbunKeyPressed

    private void KetSistemSarafUbunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSistemSarafUbunKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetSistemSarafUbunKeyPressed

    private void SistemSarafRefleksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafRefleksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SistemSarafRefleksKeyPressed

    private void KetSistemSarafRefleksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSistemSarafRefleksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetSistemSarafRefleksKeyPressed

    private void SistemSarafTangisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafTangisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SistemSarafTangisKeyPressed

    private void KetSistemSarafTangisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSistemSarafTangisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetSistemSarafTangisKeyPressed

    private void KetKardiovaskularPulsasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKardiovaskularPulsasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetKardiovaskularPulsasiKeyPressed

    private void RespirasiAirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiAirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RespirasiAirKeyPressed

    private void RespirasiMerintihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiMerintihKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RespirasiMerintihKeyPressed

    private void GastrointestinalBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GastrointestinalBABKeyPressed

    private void KetGastrointestinalBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetGastrointestinalBABKeyPressed

    private void GastrointestinalWarnaBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalWarnaBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GastrointestinalWarnaBABKeyPressed

    private void KetGastrointestinalWarnaBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalWarnaBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetGastrointestinalWarnaBABKeyPressed

    private void GastrointestinalBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GastrointestinalBAKKeyPressed

    private void KetGastrointestinalBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetGastrointestinalBAKKeyPressed

    private void GastrointestinalWarnaBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalWarnaBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GastrointestinalWarnaBAKKeyPressed

    private void KetGastrointestinalWarnaBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalWarnaBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetGastrointestinalWarnaBAKKeyPressed

    private void KetNeurologiSkleraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNeurologiSkleraKeyPressed
        Valid.pindah(evt,NeurologiSklera,NeurologiSklera);
    }//GEN-LAST:event_KetNeurologiSkleraKeyPressed

    private void NeurologiSkleraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiSkleraKeyPressed
        Valid.pindah(evt,NeurologiMata,KetNeurologiSklera);
    }//GEN-LAST:event_NeurologiSkleraKeyPressed

    private void NeurologiPupilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiPupilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NeurologiPupilKeyPressed

    private void NeurologiKelopakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiKelopakKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NeurologiKelopakKeyPressed

    private void KetNeurologiKelopakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNeurologiKelopakKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetNeurologiKelopakKeyPressed

    private void NeurologiKonjungtivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiKonjungtivaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NeurologiKonjungtivaKeyPressed

    private void KetNeurologiKonjungtivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNeurologiKonjungtivaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetNeurologiKonjungtivaKeyPressed

    private void KetNeurologiPendengaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNeurologiPendengaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetNeurologiPendengaranKeyPressed

    private void NeurologiPenciumanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiPenciumanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NeurologiPenciumanKeyPressed

    private void KetNeurologiPenciumanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNeurologiPenciumanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetNeurologiPenciumanKeyPressed

    private void IntegumentLanugoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentLanugoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntegumentLanugoKeyPressed

    private void MuskuloskeletalLenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskuloskeletalLenganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MuskuloskeletalLenganKeyPressed

    private void KetMuskuloskeletalLenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMuskuloskeletalLenganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetMuskuloskeletalLenganKeyPressed

    private void MuskuloskeletalTungkaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskuloskeletalTungkaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MuskuloskeletalTungkaiKeyPressed

    private void KetMuskuloskeletalTungkaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMuskuloskeletalTungkaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetMuskuloskeletalTungkaiKeyPressed

    private void PenerimaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenerimaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenerimaanKeyPressed

    private void PernikahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PernikahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PernikahanKeyPressed

    private void KeteranganPernikahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPernikahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganPernikahanKeyPressed

    private void NIPS1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NIPS1ItemStateChanged
        if(NIPS1.getSelectedIndex()==0){
            NilaiNIPS1.setText("0");
        }else if(NIPS1.getSelectedIndex()==1){
            NilaiNIPS1.setText("1");
        }
        isTotalNIPS();
    }//GEN-LAST:event_NIPS1ItemStateChanged

    private void NIPS1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPS1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIPS1KeyPressed

    private void NIPS2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NIPS2ItemStateChanged
        if(NIPS2.getSelectedIndex()==0){
            NilaiNIPS2.setText("0");
        }else if(NIPS2.getSelectedIndex()==1){
            NilaiNIPS2.setText("1");
        }else if(NIPS2.getSelectedIndex()==2){
            NilaiNIPS2.setText("2");
        }
        isTotalNIPS();
    }//GEN-LAST:event_NIPS2ItemStateChanged

    private void NIPS2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPS2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIPS2KeyPressed

    private void NIPS3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NIPS3ItemStateChanged
        if(NIPS3.getSelectedIndex()==0){
            NilaiNIPS3.setText("0");
        }else if(NIPS3.getSelectedIndex()==1){
            NilaiNIPS3.setText("1");
        }
        isTotalNIPS();
    }//GEN-LAST:event_NIPS3ItemStateChanged

    private void NIPS3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPS3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIPS3KeyPressed

    private void NIPS4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NIPS4ItemStateChanged
       if(NIPS4.getSelectedIndex()==0){
            NilaiNIPS4.setText("0");
        }else if(NIPS4.getSelectedIndex()==1){
            NilaiNIPS4.setText("1");
        }
        isTotalNIPS();
    }//GEN-LAST:event_NIPS4ItemStateChanged

    private void NIPS4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPS4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIPS4KeyPressed

    private void NIPS5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NIPS5ItemStateChanged
        if(NIPS5.getSelectedIndex()==0){
            NilaiNIPS5.setText("0");
        }else if(NIPS5.getSelectedIndex()==1){
            NilaiNIPS5.setText("1");
        }
        isTotalNIPS();
    }//GEN-LAST:event_NIPS5ItemStateChanged

    private void NIPS5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPS5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIPS5KeyPressed

    private void SkalaGizi3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaGizi3ItemStateChanged
        if(SkalaGizi3.getSelectedIndex()==0){
            NilaiGizi3.setText("1");
        }else if(SkalaGizi3.getSelectedIndex()==1){
            NilaiGizi3.setText("0");
        }
        isTotalGizi();
    }//GEN-LAST:event_SkalaGizi3ItemStateChanged

    private void SkalaGizi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaGizi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaGizi3KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanNeonatusRanap dialog = new RMPenilaianAwalKeperawatanNeonatusRanap(new javax.swing.JFrame(), true);
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
    private widget.TextBox A;
    private widget.TextBox A1;
    private widget.ComboBox AdakahPerilaku;
    private widget.TextBox Agama;
    private widget.ComboBox Alergi;
    private widget.TextBox AlergiLainnya;
    private widget.TextBox AlergiMakanan;
    private widget.TextBox AlergiObat;
    private widget.ComboBox Anamnesis;
    private widget.TextBox Apgar;
    private widget.TextBox BB;
    private widget.TextBox BB1;
    private widget.TextBox Bahasa;
    private widget.Button BtnAll;
    private widget.Button BtnAllMasalah;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariMasalah;
    private widget.Button BtnDPJP;
    private widget.Button BtnDokter1;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPetugas2;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahMasalah;
    private widget.ComboBox Cara;
    private widget.TextBox CaraBayar;
    private widget.ComboBox CaraMasuk;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox DarahAyah;
    private widget.ComboBox DarahBayi;
    private widget.ComboBox DarahIbu;
    private widget.ComboBox DiketahuiDietisen;
    private widget.TextBox Downes;
    private widget.ComboBox EdukasiPsikolgis;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox Frekuensi;
    private widget.TextBox G;
    private widget.TextBox G1;
    private widget.TextBox GCS;
    private widget.ComboBox GangguanJiwa;
    private widget.ComboBox GastrointestinalAbdomen;
    private widget.ComboBox GastrointestinalBAB;
    private widget.ComboBox GastrointestinalBAK;
    private widget.ComboBox GastrointestinalLidah;
    private widget.ComboBox GastrointestinalMulut;
    private widget.ComboBox GastrointestinalTenggorakan;
    private widget.ComboBox GastrointestinalWarnaBAB;
    private widget.ComboBox GastrointestinalWarnaBAK;
    private widget.ComboBox Gerak;
    private widget.ComboBox GiziIbu;
    private widget.ComboBox HubunganAnggotaKeluarga;
    private widget.TextBox Imunisasi;
    private widget.TextBox Indikasi;
    private widget.ComboBox IntegumentKulit;
    private widget.ComboBox IntegumentLanugo;
    private widget.ComboBox IntegumentTurgor;
    private widget.ComboBox IntegumentVernic;
    private widget.ComboBox IntegumentWarnaKulit;
    private widget.TextBox Jk;
    private widget.TextBox Kali;
    private widget.ComboBox KardiovaskularDenyutNadi;
    private widget.ComboBox KardiovaskularPulsasi;
    private widget.ComboBox KardiovaskularSirkulasi;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugas2;
    private widget.ComboBox KeadaanMentalUmum;
    private widget.ComboBox KebiasaanAlkohol;
    private widget.TextBox KebiasaanJumlahAlkohol;
    private widget.TextBox KebiasaanJumlahRokok;
    private widget.ComboBox KebiasaanMerokok;
    private widget.ComboBox KebiasaanNarkoba;
    private widget.ComboBox KebiasaanObat;
    private widget.TextBox KeluhanBAB;
    private widget.TextBox KeluhanBAK;
    private widget.TextBox KesadaranMental;
    private widget.TextBox KetAnamnesis;
    private widget.TextBox KetCara;
    private widget.TextBox KetGastrointestinalAbdomen;
    private widget.TextBox KetGastrointestinalBAB;
    private widget.TextBox KetGastrointestinalBAK;
    private widget.TextBox KetGastrointestinalLidah;
    private widget.TextBox KetGastrointestinalMulut;
    private widget.TextBox KetGastrointestinalTenggorakan;
    private widget.TextBox KetGastrointestinalWarnaBAB;
    private widget.TextBox KetGastrointestinalWarnaBAK;
    private widget.TextBox KetKardiovaskularPulsasi;
    private widget.TextBox KetKardiovaskularSirkulasi;
    private widget.TextBox KetMuskuloskeletalLengan;
    private widget.TextBox KetMuskuloskeletalTungkai;
    private widget.TextBox KetNIPS;
    private widget.TextBox KetNeurologiKelopak;
    private widget.TextBox KetNeurologiKonjungtiva;
    private widget.TextBox KetNeurologiPenciuman;
    private widget.TextBox KetNeurologiPendengaran;
    private widget.TextBox KetNeurologiSklera;
    private widget.TextBox KetNutrisi;
    private widget.TextBox KetObat;
    private widget.TextBox KetRPI;
    private widget.TextBox KetReproduksiLaki;
    private widget.TextBox KetReproduksiPerempuan;
    private widget.TextBox KetRespirasiJenisPernafasan;
    private widget.TextBox KetSistemSarafKejang;
    private widget.TextBox KetSistemSarafKepala;
    private widget.TextBox KetSistemSarafRefleks;
    private widget.TextBox KetSistemSarafTangis;
    private widget.TextBox KetSistemSarafUbun;
    private widget.TextBox KetSistemSarafWajah;
    private widget.TextBox KeteranganAdakahPerilaku;
    private widget.TextBox KeteranganDiketahuiDietisen;
    private widget.TextBox KeteranganEdukasiPsikologis;
    private widget.TextBox KeteranganNilaiKepercayaan;
    private widget.TextBox KeteranganPernikahan;
    private widget.TextBox KeteranganTinggalDengan;
    private widget.ComboBox Ketuban;
    private widget.TextBox Kondisi;
    private widget.ComboBox KondisiPsikologis;
    private widget.Label LCount;
    private widget.TextBox LD;
    private widget.TextBox LD1;
    private widget.TextBox LK;
    private widget.TextBox LK1;
    private widget.TextBox LP;
    private widget.TextBox LP1;
    private widget.TextBox Letak;
    private widget.ComboBox Mayor;
    private widget.ComboBox Minor;
    private widget.ComboBox MuskuloskeletalLengan;
    private widget.ComboBox MuskuloskeletalTungkai;
    private widget.ComboBox MuskuloskletalGaris;
    private widget.ComboBox MuskuloskletalRekoil;
    private widget.ComboBox NIPS1;
    private widget.ComboBox NIPS2;
    private widget.ComboBox NIPS3;
    private widget.ComboBox NIPS4;
    private widget.ComboBox NIPS5;
    private widget.TextBox Nadi;
    private widget.ComboBox NeurologiKelopak;
    private widget.ComboBox NeurologiKonjungtiva;
    private widget.ComboBox NeurologiMata;
    private widget.ComboBox NeurologiPenciuman;
    private widget.ComboBox NeurologiPendengaran;
    private widget.ComboBox NeurologiPupil;
    private widget.ComboBox NeurologiSklera;
    private widget.TextBox NilaiGizi1;
    private widget.TextBox NilaiGizi2;
    private widget.TextBox NilaiGizi3;
    private widget.TextBox NilaiGiziTotal;
    private widget.ComboBox NilaiKepercayaan;
    private widget.TextBox NilaiNIPS1;
    private widget.TextBox NilaiNIPS2;
    private widget.TextBox NilaiNIPS3;
    private widget.TextBox NilaiNIPS4;
    private widget.TextBox NilaiNIPS5;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas2;
    private widget.ComboBox Nutrisi;
    private widget.TextBox P;
    private widget.TextBox P1;
    private widget.TextBox PB;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox PekerjaanPasien;
    private widget.ComboBox PendidikanPJ;
    private widget.TextBox PendidikanPasien;
    private widget.ComboBox Penerimaan;
    private widget.ComboBox Pernikahan;
    private widget.ComboBox RD;
    private widget.ComboBox RPI;
    private widget.TextArea RPK;
    private widget.TextBox RPO;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.TextBox ReaksiLainnya;
    private widget.TextBox ReaksiMakanan;
    private widget.TextBox ReaksiObat;
    private widget.TextArea Rencana;
    private widget.ComboBox ReproduksiLaki;
    private widget.ComboBox ReproduksiPerempuan;
    private widget.ComboBox RespirasiAir;
    private widget.ComboBox RespirasiJenisPernafasan;
    private widget.ComboBox RespirasiMerintih;
    private widget.ComboBox RespirasiPolaNafas;
    private widget.ComboBox RespirasiRetraksi;
    private widget.ComboBox RespirasiSuaraNafas;
    private widget.ComboBox RhAyah;
    private widget.ComboBox RhBayi;
    private widget.ComboBox RhIbu;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll9;
    private widget.ComboBox SistemSarafKejang;
    private widget.ComboBox SistemSarafKepala;
    private widget.ComboBox SistemSarafRefleks;
    private widget.ComboBox SistemSarafTangis;
    private widget.ComboBox SistemSarafUbun;
    private widget.ComboBox SistemSarafWajah;
    private widget.ComboBox SkalaGizi1;
    private widget.ComboBox SkalaGizi2;
    private widget.ComboBox SkalaGizi3;
    private widget.TextBox SpO2;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TCariMasalah;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabRawat;
    private widget.ComboBox Tali;
    private widget.Tanggal TanggalLahir;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TibadiRuang;
    private widget.ComboBox TinggalDengan;
    private widget.TextBox TotalNIPS;
    private widget.TextBox UK;
    private widget.TextBox UK1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel12;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel13;
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel135;
    private widget.Label jLabel136;
    private widget.Label jLabel137;
    private widget.Label jLabel138;
    private widget.Label jLabel139;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel15;
    private widget.Label jLabel150;
    private widget.Label jLabel151;
    private widget.Label jLabel152;
    private widget.Label jLabel153;
    private widget.Label jLabel154;
    private widget.Label jLabel156;
    private widget.Label jLabel157;
    private widget.Label jLabel158;
    private widget.Label jLabel159;
    private widget.Label jLabel16;
    private widget.Label jLabel160;
    private widget.Label jLabel161;
    private widget.Label jLabel162;
    private widget.Label jLabel163;
    private widget.Label jLabel164;
    private widget.Label jLabel165;
    private widget.Label jLabel167;
    private widget.Label jLabel168;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel189;
    private widget.Label jLabel19;
    private widget.Label jLabel190;
    private widget.Label jLabel191;
    private widget.Label jLabel192;
    private widget.Label jLabel193;
    private widget.Label jLabel194;
    private widget.Label jLabel195;
    private widget.Label jLabel196;
    private widget.Label jLabel197;
    private widget.Label jLabel198;
    private widget.Label jLabel199;
    private widget.Label jLabel20;
    private widget.Label jLabel200;
    private widget.Label jLabel201;
    private widget.Label jLabel202;
    private widget.Label jLabel203;
    private widget.Label jLabel21;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel271;
    private widget.Label jLabel272;
    private widget.Label jLabel273;
    private widget.Label jLabel274;
    private widget.Label jLabel275;
    private widget.Label jLabel276;
    private widget.Label jLabel277;
    private widget.Label jLabel278;
    private widget.Label jLabel279;
    private widget.Label jLabel28;
    private widget.Label jLabel280;
    private widget.Label jLabel281;
    private widget.Label jLabel282;
    private widget.Label jLabel283;
    private widget.Label jLabel284;
    private widget.Label jLabel285;
    private widget.Label jLabel286;
    private widget.Label jLabel287;
    private widget.Label jLabel288;
    private widget.Label jLabel289;
    private widget.Label jLabel29;
    private widget.Label jLabel290;
    private widget.Label jLabel291;
    private widget.Label jLabel292;
    private widget.Label jLabel293;
    private widget.Label jLabel294;
    private widget.Label jLabel295;
    private widget.Label jLabel296;
    private widget.Label jLabel297;
    private widget.Label jLabel298;
    private widget.Label jLabel299;
    private widget.Label jLabel30;
    private widget.Label jLabel300;
    private widget.Label jLabel301;
    private widget.Label jLabel302;
    private widget.Label jLabel303;
    private widget.Label jLabel304;
    private widget.Label jLabel305;
    private widget.Label jLabel306;
    private widget.Label jLabel307;
    private widget.Label jLabel308;
    private widget.Label jLabel309;
    private widget.Label jLabel31;
    private widget.Label jLabel310;
    private widget.Label jLabel311;
    private widget.Label jLabel312;
    private widget.Label jLabel313;
    private widget.Label jLabel314;
    private widget.Label jLabel315;
    private widget.Label jLabel316;
    private widget.Label jLabel317;
    private widget.Label jLabel318;
    private widget.Label jLabel319;
    private widget.Label jLabel320;
    private widget.Label jLabel321;
    private widget.Label jLabel322;
    private widget.Label jLabel323;
    private widget.Label jLabel324;
    private widget.Label jLabel325;
    private widget.Label jLabel326;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel42;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
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
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane5;
    private widget.Table tbMasalahDetail;
    private widget.Table tbMasalahKeperawatan;
    private widget.Table tbObat;
    private widget.Table tbRencanaDetail;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select penilaian_awal_keperawatan_ranap_neonatus.no_rawat,penilaian_awal_keperawatan_ranap_neonatus.tanggal,penilaian_awal_keperawatan_ranap_neonatus.cara_masuk,penilaian_awal_keperawatan_ranap_neonatus.tiba_diruang_rawat,"+
                "penilaian_awal_keperawatan_ranap_neonatus.informasi,penilaian_awal_keperawatan_ranap_neonatus.ket_informasi,penilaian_awal_keperawatan_ranap_neonatus.rps,penilaian_awal_keperawatan_ranap_neonatus.g,"+
                "penilaian_awal_keperawatan_ranap_neonatus.p,penilaian_awal_keperawatan_ranap_neonatus.a,penilaian_awal_keperawatan_ranap_neonatus.uk,penilaian_awal_keperawatan_ranap_neonatus.rpi,penilaian_awal_keperawatan_ranap_neonatus.ket_rpi,"+
                "penilaian_awal_keperawatan_ranap_neonatus.rpo,penilaian_awal_keperawatan_ranap_neonatus.rd,penilaian_awal_keperawatan_ranap_neonatus.indikasi,penilaian_awal_keperawatan_ranap_neonatus.gizi_ibu,penilaian_awal_keperawatan_ranap_neonatus.g1,"+
                "penilaian_awal_keperawatan_ranap_neonatus.p1,penilaian_awal_keperawatan_ranap_neonatus.a1,penilaian_awal_keperawatan_ranap_neonatus.uk1,penilaian_awal_keperawatan_ranap_neonatus.tanggal_lahir,penilaian_awal_keperawatan_ranap_neonatus.kondisi,"+
                "penilaian_awal_keperawatan_ranap_neonatus.apgar,penilaian_awal_keperawatan_ranap_neonatus.cara,penilaian_awal_keperawatan_ranap_neonatus.ket_cara,penilaian_awal_keperawatan_ranap_neonatus.letak,penilaian_awal_keperawatan_ranap_neonatus.ketuban,"+
                "penilaian_awal_keperawatan_ranap_neonatus.tali,penilaian_awal_keperawatan_ranap_neonatus.bb1,penilaian_awal_keperawatan_ranap_neonatus.pb,penilaian_awal_keperawatan_ranap_neonatus.lk,penilaian_awal_keperawatan_ranap_neonatus.ld,"+
                "penilaian_awal_keperawatan_ranap_neonatus.lp,penilaian_awal_keperawatan_ranap_neonatus.mayor,penilaian_awal_keperawatan_ranap_neonatus.minor,penilaian_awal_keperawatan_ranap_neonatus.nutrisi,penilaian_awal_keperawatan_ranap_neonatus.ket_nutrisi,"+
                "penilaian_awal_keperawatan_ranap_neonatus.frekuensi,penilaian_awal_keperawatan_ranap_neonatus.kali,penilaian_awal_keperawatan_ranap_neonatus.keluhan_bak,penilaian_awal_keperawatan_ranap_neonatus.keluhan_bab,penilaian_awal_keperawatan_ranap_neonatus.alergi,"+
                "penilaian_awal_keperawatan_ranap_neonatus.alergi_obat,penilaian_awal_keperawatan_ranap_neonatus.reaksi_obat,penilaian_awal_keperawatan_ranap_neonatus.alergi_makanan,penilaian_awal_keperawatan_ranap_neonatus.reaksi_makanan,penilaian_awal_keperawatan_ranap_neonatus.alergi_lainnya,"+
                "penilaian_awal_keperawatan_ranap_neonatus.reaksi_lainnya,penilaian_awal_keperawatan_ranap_neonatus.rpk,penilaian_awal_keperawatan_ranap_neonatus.imunisasi,penilaian_awal_keperawatan_ranap_neonatus.riwayat_obat,penilaian_awal_keperawatan_ranap_neonatus.ket_obat,"+
                "penilaian_awal_keperawatan_ranap_neonatus.riwayat_merokok,penilaian_awal_keperawatan_ranap_neonatus.riwayat_merokok_jumlah,penilaian_awal_keperawatan_ranap_neonatus.riwayat_alkohol,penilaian_awal_keperawatan_ranap_neonatus.riwayat_alkohol_jumlah,penilaian_awal_keperawatan_ranap_neonatus.riwayat_narkoba,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_mental,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_keadaan_umum,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gcs,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_nadi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_rr,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_suhu,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_spo2,"+
                "penilaian_awal_keperawatan_ranap_neonatus.downes,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_bb,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_tb,penilaian_awal_keperawatan_ranap_neonatus.lk1,penilaian_awal_keperawatan_ranap_neonatus.ld1,"+
                "penilaian_awal_keperawatan_ranap_neonatus.lp1,penilaian_awal_keperawatan_ranap_neonatus.darah_bayi,penilaian_awal_keperawatan_ranap_neonatus.rh_bayi,penilaian_awal_keperawatan_ranap_neonatus.darah_ibu,penilaian_awal_keperawatan_ranap_neonatus.rh_ibu,"+
                "penilaian_awal_keperawatan_ranap_neonatus.darah_ayah,penilaian_awal_keperawatan_ranap_neonatus.rh_ayah,penilaian_awal_keperawatan_ranap_neonatus.gerak,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_kepala,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_kepala_keterangan,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_ubun,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_ubun_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_wajah,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_wajah_keterangan,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_kejang,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_kejang_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_refleks,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_refleks_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_tangis,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_susunan_tangis_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_kardiovaskuler_sirkulasi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_kardiovaskuler_sirkulasi_keterangan,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_kardiovaskuler_pulsasi_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_pola_nafas,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_jenis_pernafasan,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_jenis_pernafasan_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_retraksi,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_air,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_merintih,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_respirasi_suara_nafas,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_mulut,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_mulut_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_lidah,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_lidah_keterangan,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_tenggorokan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_tenggorokan_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_abdomen,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_abdomen_keterangan,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_bab,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_bab_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_warna_bab,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_warna_bab_keterangan,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_bak,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_bak_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_warna_bak,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_gastrointestinal_warna_bak_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_mata,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_pupil,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_kelopak,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_kelopak_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_konjungtiva,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_konjungtiva_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_sklera,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_sklera_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_pendengaran,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_pendengaran_keterangan,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_penciuman,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_neurologi_penciuman_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_integument_warnakulit,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_integument_vernic,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_integument_lanugo,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_integument_turgor,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_integument_kulit,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_reproduksi_laki,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_reproduksi_laki_keterangan,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_reproduksi_perempuan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_reproduksi_perempuan_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_lengan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_lengan_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_tungkai,"+
                "penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_tungkai_keterangan,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_rekoil,penilaian_awal_keperawatan_ranap_neonatus.pemeriksaan_muskuloskletal_garis,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_kondisi_psiko,"+
                "penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_perilaku,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_hubungan_keluarga,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_tinggal,"+
                "penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_tinggal_keterangan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_nilai_kepercayaan_keterangan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_edukasi_diberikan,"+
                "penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_edukasi_diberikan_keterangan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_penerimaan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_pernikahan,penilaian_awal_keperawatan_ranap_neonatus.riwayat_psiko_pernikahan_keterangan,penilaian_awal_keperawatan_ranap_neonatus.nips1,"+
                "penilaian_awal_keperawatan_ranap_neonatus.nilai_nips1,penilaian_awal_keperawatan_ranap_neonatus.nips2,penilaian_awal_keperawatan_ranap_neonatus.nilai_nips2,penilaian_awal_keperawatan_ranap_neonatus.nips3,penilaian_awal_keperawatan_ranap_neonatus.nilai_nips3,penilaian_awal_keperawatan_ranap_neonatus.nips4,"+
                "penilaian_awal_keperawatan_ranap_neonatus.nilai_nips4,penilaian_awal_keperawatan_ranap_neonatus.nips5,penilaian_awal_keperawatan_ranap_neonatus.nilai_nips5,penilaian_awal_keperawatan_ranap_neonatus.total_nips,penilaian_awal_keperawatan_ranap_neonatus.ket_nips,penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi1,"+
                "penilaian_awal_keperawatan_ranap_neonatus.nilai_gizi1,penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi2,penilaian_awal_keperawatan_ranap_neonatus.nilai_gizi2,penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi3,penilaian_awal_keperawatan_ranap_neonatus.nilai_gizi3,penilaian_awal_keperawatan_ranap_neonatus.nilai_total_gizi,"+
                "penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap_neonatus.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap_neonatus.rencana,penilaian_awal_keperawatan_ranap_neonatus.nip1,penilaian_awal_keperawatan_ranap_neonatus.nip2,penilaian_awal_keperawatan_ranap_neonatus.kd_dokter,"+
                "pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab,bahasa_pasien.nama_bahasa "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_ranap_neonatus on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap_neonatus.no_rawat "+
                "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap_neonatus.nip1=pengkaji1.nip "+
                "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap_neonatus.nip2=pengkaji2.nip "+
                "inner join dokter on penilaian_awal_keperawatan_ranap_neonatus.kd_dokter=dokter.kd_dokter "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where "+
                "penilaian_awal_keperawatan_ranap_neonatus.tanggal between ? and ? "+
                (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_awal_keperawatan_ranap_neonatus.nip1 like ? or "+
                "pengkaji1.nama like ? or penilaian_awal_keperawatan_ranap_neonatus.kd_dokter like ? or dokter.nm_dokter like ?)")+
                " order by penilaian_awal_keperawatan_ranap_neonatus.tanggal");
            
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("nip1"),rs.getString("pengkaji1"),rs.getString("nip2"),rs.getString("pengkaji2"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("cara_masuk"),rs.getString("tiba_diruang_rawat"),rs.getString("informasi")+", "+rs.getString("ket_informasi"),rs.getString("rps"),rs.getString("g"),
                        rs.getString("p"),rs.getString("a"),rs.getString("uk"),rs.getString("rpi"),rs.getString("ket_rpi"),rs.getString("rpo"),rs.getString("rd"),rs.getString("indikasi"),rs.getString("gizi_ibu"),rs.getString("g1"),rs.getString("p1"),rs.getString("a1"),rs.getString("uk1"),
                        rs.getString("tanggal_lahir"),rs.getString("kondisi"),rs.getString("apgar"),rs.getString("cara"),rs.getString("ket_cara"),rs.getString("letak"),rs.getString("ketuban"),rs.getString("tali"),rs.getString("bb1"),rs.getString("pb"),rs.getString("lk"),rs.getString("ld"),rs.getString("lp"),
                        rs.getString("mayor"),rs.getString("minor"),rs.getString("nutrisi"),rs.getString("ket_nutrisi"),rs.getString("frekuensi"),rs.getString("kali"),rs.getString("keluhan_bak"),rs.getString("keluhan_bab"),rs.getString("alergi"),rs.getString("alergi_obat"),
                        rs.getString("reaksi_obat"),rs.getString("alergi_makanan"),rs.getString("reaksi_makanan"),rs.getString("alergi_lainnya"),rs.getString("reaksi_lainnya"),rs.getString("rpk"),rs.getString("imunisasi"),rs.getString("riwayat_obat"),rs.getString("ket_obat"),
                        rs.getString("riwayat_merokok"),rs.getString("riwayat_merokok_jumlah"),rs.getString("riwayat_alkohol"),rs.getString("riwayat_alkohol_jumlah"),rs.getString("riwayat_narkoba"),rs.getString("pemeriksaan_mental"),rs.getString("pemeriksaan_keadaan_umum"),
                        rs.getString("pemeriksaan_gcs"),rs.getString("pemeriksaan_nadi"),rs.getString("pemeriksaan_rr"),rs.getString("pemeriksaan_suhu"),rs.getString("pemeriksaan_spo2"),rs.getString("downes"),rs.getString("pemeriksaan_bb"),
                        rs.getString("pemeriksaan_tb"),rs.getString("lk1"),rs.getString("ld1"),rs.getString("lp1"),rs.getString("darah_bayi"),rs.getString("rh_bayi"),rs.getString("darah_ibu"),rs.getString("rh_ibu"),rs.getString("darah_ayah"),rs.getString("rh_ayah"),rs.getString("gerak"),
                        rs.getString("pemeriksaan_susunan_kepala"),rs.getString("pemeriksaan_susunan_kepala_keterangan"),rs.getString("pemeriksaan_susunan_ubun"),rs.getString("pemeriksaan_susunan_ubun_keterangan"),rs.getString("pemeriksaan_susunan_wajah"),
                        rs.getString("pemeriksaan_susunan_wajah_keterangan"),rs.getString("pemeriksaan_susunan_kejang"),rs.getString("pemeriksaan_susunan_kejang_keterangan"),rs.getString("pemeriksaan_susunan_refleks"),rs.getString("pemeriksaan_susunan_refleks_keterangan"),
                        rs.getString("pemeriksaan_susunan_tangis"),rs.getString("pemeriksaan_susunan_tangis_keterangan"),rs.getString("pemeriksaan_kardiovaskuler_denyut_nadi"),rs.getString("pemeriksaan_kardiovaskuler_sirkulasi"),rs.getString("pemeriksaan_kardiovaskuler_sirkulasi_keterangan"),
                        rs.getString("pemeriksaan_kardiovaskuler_pulsasi"),rs.getString("pemeriksaan_kardiovaskuler_pulsasi_keterangan"),rs.getString("pemeriksaan_respirasi_pola_nafas"),rs.getString("pemeriksaan_respirasi_jenis_pernafasan"),
                        rs.getString("pemeriksaan_respirasi_jenis_pernafasan_keterangan"),rs.getString("pemeriksaan_respirasi_retraksi"),rs.getString("pemeriksaan_respirasi_air"),rs.getString("pemeriksaan_respirasi_merintih"),rs.getString("pemeriksaan_respirasi_suara_nafas"),
                        rs.getString("pemeriksaan_gastrointestinal_mulut"),rs.getString("pemeriksaan_gastrointestinal_mulut_keterangan"),rs.getString("pemeriksaan_gastrointestinal_lidah"),rs.getString("pemeriksaan_gastrointestinal_lidah_keterangan"),rs.getString("pemeriksaan_gastrointestinal_tenggorokan"),
                        rs.getString("pemeriksaan_gastrointestinal_tenggorokan_keterangan"),rs.getString("pemeriksaan_gastrointestinal_abdomen"),rs.getString("pemeriksaan_gastrointestinal_abdomen_keterangan"),rs.getString("pemeriksaan_gastrointestinal_bab"),
                        rs.getString("pemeriksaan_gastrointestinal_bab_keterangan"),rs.getString("pemeriksaan_gastrointestinal_warna_bab"),rs.getString("pemeriksaan_gastrointestinal_warna_bab_keterangan"),rs.getString("pemeriksaan_gastrointestinal_bak"),
                        rs.getString("pemeriksaan_gastrointestinal_bak_keterangan"),rs.getString("pemeriksaan_gastrointestinal_warna_bak"),rs.getString("pemeriksaan_gastrointestinal_warna_bak_keterangan"),rs.getString("pemeriksaan_neurologi_mata"),
                        rs.getString("pemeriksaan_neurologi_pupil"),rs.getString("pemeriksaan_neurologi_kelopak"),rs.getString("pemeriksaan_neurologi_kelopak_keterangan"),rs.getString("pemeriksaan_neurologi_konjungtiva"),rs.getString("pemeriksaan_neurologi_konjungtiva_keterangan"),
                        rs.getString("pemeriksaan_neurologi_sklera"),rs.getString("pemeriksaan_neurologi_sklera_keterangan"),rs.getString("pemeriksaan_neurologi_pendengaran"),rs.getString("pemeriksaan_neurologi_pendengaran_keterangan"),rs.getString("pemeriksaan_neurologi_penciuman"),
                        rs.getString("pemeriksaan_neurologi_penciuman_keterangan"),rs.getString("pemeriksaan_integument_warnakulit"),rs.getString("pemeriksaan_integument_vernic"),rs.getString("pemeriksaan_integument_lanugo"),rs.getString("pemeriksaan_integument_turgor"),
                        rs.getString("pemeriksaan_integument_kulit"),rs.getString("pemeriksaan_reproduksi_laki"),rs.getString("pemeriksaan_reproduksi_laki_keterangan"),rs.getString("pemeriksaan_reproduksi_perempuan"),rs.getString("pemeriksaan_reproduksi_perempuan_keterangan"),
                        rs.getString("pemeriksaan_muskuloskletal_lengan"),rs.getString("pemeriksaan_muskuloskletal_lengan_keterangan"),rs.getString("pemeriksaan_muskuloskletal_tungkai"),rs.getString("pemeriksaan_muskuloskletal_tungkai_keterangan"),rs.getString("pemeriksaan_muskuloskletal_rekoil"),rs.getString("pemeriksaan_muskuloskletal_garis"),
                        rs.getString("riwayat_psiko_kondisi_psiko"),rs.getString("riwayat_psiko_perilaku")+", "+rs.getString("riwayat_psiko_perilaku_keterangan"),rs.getString("riwayat_psiko_gangguan_jiwa"),
                        rs.getString("riwayat_psiko_hubungan_keluarga"),rs.getString("agama"),rs.getString("riwayat_psiko_tinggal")+", "+rs.getString("riwayat_psiko_tinggal_keterangan"),rs.getString("pekerjaan"),rs.getString("png_jawab"),
                        rs.getString("riwayat_psiko_nilai_kepercayaan")+", "+rs.getString("riwayat_psiko_nilai_kepercayaan_keterangan"),rs.getString("nama_bahasa"),rs.getString("pnd"),rs.getString("riwayat_psiko_pendidikan_pj"),
                        rs.getString("riwayat_psiko_edukasi_diberikan")+", "+rs.getString("riwayat_psiko_edukasi_diberikan_keterangan"),rs.getString("riwayat_psiko_penerimaan"),rs.getString("riwayat_psiko_pernikahan"),rs.getString("riwayat_psiko_pernikahan_keterangan"),
                        rs.getString("nips1"),rs.getString("nilai_nips1"),rs.getString("nips2"),rs.getString("nilai_nips2"),rs.getString("nips3"),rs.getString("nilai_nips3"),rs.getString("nips4"),rs.getString("nilai_nips4"),rs.getString("nips5"),rs.getString("nilai_nips5"),rs.getString("total_nips"),rs.getString("ket_nips"),
                        rs.getString("skrining_gizi1"),rs.getString("nilai_gizi1"),rs.getString("skrining_gizi2"),rs.getString("nilai_gizi2"),rs.getString("skrining_gizi3"),rs.getString("nilai_gizi3"),rs.getString("nilai_total_gizi"),rs.getString("skrining_gizi_diketahui_dietisen"),rs.getString("skrining_gizi_jam_diketahui_dietisen"),
                        rs.getString("rencana")
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
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TglAsuhan.setDate(new Date());
        CaraMasuk.setSelectedIndex(0);
        TibadiRuang.setSelectedIndex(0);
        Anamnesis.setSelectedIndex(0);
        KetAnamnesis.setText("-");
        RPS.setText("-");
        G.setText("-");
        P.setText("-");
        A.setText("-");
        UK.setText("-");
        RPI.setSelectedIndex(0);
        KetRPI.setText("-");
        RPO.setText("-");
        RD.setSelectedIndex(0);
        Indikasi.setText("-");
        GiziIbu.setSelectedIndex(0);
        G1.setText("-");
        P1.setText("-");
        A1.setText("-");
        UK1.setText("-");
        TanggalLahir.setDate(new Date());
        Kondisi.setText("-");
        Apgar.setText("-");
        Cara.setSelectedIndex(0);
        KetCara.setText("-");
        Letak.setText("-");
        Ketuban.setSelectedIndex(0);
        Tali.setSelectedIndex(0);
        BB1.setText("0");
        PB.setText("0");
        LK.setText("0");
        LD.setText("0");
        LP.setText("0");
        Mayor.setSelectedIndex(0);
        Minor.setSelectedIndex(0);
        Nutrisi.setSelectedIndex(0);
        KetNutrisi.setText("-");
        Frekuensi.setText("0");
        Kali.setText("0");
        KeluhanBAK.setText("-");
        KeluhanBAB.setText("-");
        Alergi.setSelectedIndex(0);
        AlergiObat.setText("-");
        ReaksiObat.setText("-");
        AlergiMakanan.setText("-");
        ReaksiMakanan.setText("-");
        AlergiLainnya.setText("-");
        ReaksiLainnya.setText("-");
        RPK.setText("-");
        Imunisasi.setText("-");
        KebiasaanObat.setSelectedIndex(0);
        KetObat.setText("-");
        KebiasaanMerokok.setSelectedIndex(0);
        KebiasaanJumlahRokok.setText("0");
        KebiasaanAlkohol.setSelectedIndex(0);
        KebiasaanJumlahAlkohol.setText("0");
        KebiasaanNarkoba.setSelectedIndex(0);
        KesadaranMental.setText("-");
        KeadaanMentalUmum.setSelectedIndex(0);
        GCS.setText("0");
        Nadi.setText("0");
        RR.setText("0");
        Suhu.setText("0");
        SpO2.setText("0");
        Downes.setText("0");
        BB.setText("0");
        TB.setText("0");
        LK1.setText("0");
        LD1.setText("0");
        LP1.setText("0");
        DarahBayi.setSelectedIndex(0);
        RhBayi.setSelectedIndex(0);
        DarahIbu.setSelectedIndex(0);
        RhIbu.setSelectedIndex(0);
        DarahAyah.setSelectedIndex(0);
        RhAyah.setSelectedIndex(0);
        Gerak.setSelectedIndex(0);
        SistemSarafKepala.setSelectedIndex(0);
        KetSistemSarafKepala.setText("-");
        SistemSarafUbun.setSelectedIndex(0);
        KetSistemSarafUbun.setText("-");
        SistemSarafWajah.setSelectedIndex(0);
        KetSistemSarafWajah.setText("-");
        SistemSarafKejang.setSelectedIndex(0);
        KetSistemSarafKejang.setText("-");
        SistemSarafRefleks.setSelectedIndex(0);
        KetSistemSarafRefleks.setText("-");
        SistemSarafTangis.setSelectedIndex(0);
        KetSistemSarafTangis.setText("-");
        KardiovaskularDenyutNadi.setSelectedIndex(0);
        KardiovaskularSirkulasi.setSelectedIndex(0);
        KetKardiovaskularSirkulasi.setText("-");
        KardiovaskularPulsasi.setSelectedIndex(0);
        KetKardiovaskularPulsasi.setText("-");
        RespirasiPolaNafas.setSelectedIndex(0);
        RespirasiJenisPernafasan.setSelectedIndex(0);
        KetRespirasiJenisPernafasan.setText("-");
        RespirasiRetraksi.setSelectedIndex(0);
        RespirasiAir.setSelectedIndex(0);
        RespirasiMerintih.setSelectedIndex(0);
        RespirasiSuaraNafas.setSelectedIndex(0);
        GastrointestinalMulut.setSelectedIndex(0);
        KetGastrointestinalMulut.setText("-");
        GastrointestinalLidah.setSelectedIndex(0);
        KetGastrointestinalLidah.setText("-");
        GastrointestinalTenggorakan.setSelectedIndex(0);
        KetGastrointestinalTenggorakan.setText("-");
        GastrointestinalAbdomen.setSelectedIndex(0);
        KetGastrointestinalAbdomen.setText("-");
        GastrointestinalBAB.setSelectedIndex(0);
        KetGastrointestinalBAB.setText("-");
        GastrointestinalWarnaBAB.setSelectedIndex(0);
        KetGastrointestinalWarnaBAB.setText("-");
        GastrointestinalBAK.setSelectedIndex(0);
        KetGastrointestinalBAK.setText("-");
        GastrointestinalWarnaBAK.setSelectedIndex(0);
        KetGastrointestinalWarnaBAK.setText("-");
        NeurologiMata.setSelectedIndex(0);
        NeurologiPupil.setSelectedIndex(0);
        NeurologiKelopak.setSelectedIndex(0);
        KetNeurologiKelopak.setText("-");
        NeurologiKonjungtiva.setSelectedIndex(0);
        KetNeurologiKonjungtiva.setText("-");
        NeurologiSklera.setSelectedIndex(0);
        KetNeurologiSklera.setText("-");
        NeurologiPendengaran.setSelectedIndex(0);
        KetNeurologiPendengaran.setText("-");
        NeurologiPenciuman.setSelectedIndex(0);
        KetNeurologiPenciuman.setText("-");
        IntegumentWarnaKulit.setSelectedIndex(0);
        IntegumentVernic.setSelectedIndex(0);
        IntegumentLanugo.setSelectedIndex(0);
        IntegumentTurgor.setSelectedIndex(0);
        IntegumentKulit.setSelectedIndex(0);
        ReproduksiLaki.setSelectedIndex(0);
        KetReproduksiLaki.setText("-");
        ReproduksiPerempuan.setSelectedIndex(0);
        KetReproduksiPerempuan.setText("-");
        MuskuloskeletalLengan.setSelectedIndex(0);
        KetMuskuloskeletalLengan.setText("-");
        MuskuloskeletalTungkai.setSelectedIndex(0);
        KetMuskuloskeletalTungkai.setText("-");
        MuskuloskletalRekoil.setSelectedIndex(0);
        MuskuloskletalGaris.setSelectedIndex(0);
        KondisiPsikologis.setSelectedIndex(0);
        GangguanJiwa.setSelectedIndex(0);
        AdakahPerilaku.setSelectedIndex(0);
        KeteranganAdakahPerilaku.setText("-");
        HubunganAnggotaKeluarga.setSelectedIndex(0);
        TinggalDengan.setSelectedIndex(0);
        KeteranganTinggalDengan.setText("-");
        NilaiKepercayaan.setSelectedIndex(0);
        KeteranganNilaiKepercayaan.setText("-");
        PendidikanPJ.setSelectedIndex(0);
        EdukasiPsikolgis.setSelectedIndex(0);
        KeteranganEdukasiPsikologis.setText("-");
        Penerimaan.setSelectedIndex(0);
        Pernikahan.setSelectedIndex(0);
        KeteranganPernikahan.setText("-");
        NIPS1.setSelectedIndex(0);
        NilaiNIPS1.setText("0");
        NIPS2.setSelectedIndex(0);
        NilaiNIPS2.setText("0");
        NIPS3.setSelectedIndex(0);
        NilaiNIPS3.setText("0");
        NIPS4.setSelectedIndex(0);
        NilaiNIPS4.setText("0");
        NIPS5.setSelectedIndex(0);
        NilaiNIPS5.setText("0");
        TotalNIPS.setText("0");
        KetNIPS.setText("Tidak Nyeri");
        SkalaGizi1.setSelectedIndex(0);
        NilaiGizi1.setText("1");
        SkalaGizi2.setSelectedIndex(0);
        NilaiGizi2.setText("1");
        SkalaGizi3.setSelectedIndex(0);
        NilaiGizi3.setText("1");
        NilaiGiziTotal.setText("3");
        DiketahuiDietisen.setSelectedIndex(0);
        KeteranganDiketahuiDietisen.setText("");
        Rencana.setText("");
//        KdPetugas.setText("");
        KdPetugas2.setText("");
        KdDPJP.setText("");
        for (i = 0; i < tabModeMasalah.getRowCount(); i++) {
            tabModeMasalah.setValueAt(false,i,0);
        }
        Valid.tabelKosong(tabModeRencana);
        TabRawat.setSelectedIndex(0);
        TNoRw.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            KdPetugas2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            NmPetugas2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            KdDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
            NmDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            CaraMasuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
            TibadiRuang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().contains("Orang Tua")){
                Anamnesis.setSelectedItem("Orang Tua");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().contains("Keluarga")){
                Anamnesis.setSelectedItem("Keluarga");
            }else{
                Anamnesis.setSelectedItem("Lainnya");
            }
            KetAnamnesis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().replaceAll(Anamnesis.getSelectedItem().toString()+", ",""));
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
            G.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            P.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            A.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            UK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            RPI.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KetRPI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            RD.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Indikasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            GiziIbu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            G1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            P1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            A1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            UK1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
//            Valid.SetTgl(TanggalLahir.getSelectedItem()+"")+" "+TanggalLahir.getSelectedItem().toString().substring(11,19),
            Kondisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Apgar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Cara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            KetCara.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Letak.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Ketuban.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Tali.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            BB1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            PB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            LK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            LD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            LP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Mayor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Minor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Nutrisi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            KetNutrisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Frekuensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Kali.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            KeluhanBAK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            KeluhanBAB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Alergi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            AlergiObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            ReaksiObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            AlergiMakanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            ReaksiMakanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            AlergiLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            ReaksiLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Imunisasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            KebiasaanObat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            KetObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            KebiasaanMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            KebiasaanJumlahRokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            KebiasaanAlkohol.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            KebiasaanJumlahAlkohol.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            KebiasaanNarkoba.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            KesadaranMental.setText(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            KeadaanMentalUmum.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            SpO2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            Downes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            LK1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            LD1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            LP1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            DarahBayi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            RhBayi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            DarahIbu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            RhIbu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            DarahAyah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            RhAyah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            Gerak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            SistemSarafKepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            KetSistemSarafKepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            SistemSarafUbun.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            KetSistemSarafUbun.setText(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            SistemSarafWajah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            KetSistemSarafWajah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            SistemSarafKejang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString());
            KetSistemSarafKejang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),93).toString());
            SistemSarafRefleks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),94).toString());
            KetSistemSarafRefleks.setText(tbObat.getValueAt(tbObat.getSelectedRow(),95).toString());
            SistemSarafTangis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),96).toString());
            KetSistemSarafTangis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),97).toString());
            KardiovaskularDenyutNadi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),98).toString());
            KardiovaskularSirkulasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),99).toString());
            KetKardiovaskularSirkulasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),100).toString());
            KardiovaskularPulsasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),101).toString());
            KetKardiovaskularPulsasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),102).toString());
            RespirasiPolaNafas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),103).toString());
            RespirasiJenisPernafasan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),104).toString());
            KetRespirasiJenisPernafasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),105).toString());
            RespirasiRetraksi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),106).toString());
            RespirasiAir.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),107).toString());
            RespirasiMerintih.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),108).toString());
            RespirasiSuaraNafas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),109).toString());
            GastrointestinalMulut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),110).toString());
            KetGastrointestinalMulut.setText(tbObat.getValueAt(tbObat.getSelectedRow(),111).toString());
            GastrointestinalLidah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),112).toString());
            KetGastrointestinalLidah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),113).toString());
            GastrointestinalTenggorakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),114).toString());
            KetGastrointestinalTenggorakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),115).toString());
            GastrointestinalAbdomen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),116).toString());
            KetGastrointestinalAbdomen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),117).toString());
            GastrointestinalBAB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),118).toString());
            KetGastrointestinalBAB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),119).toString());
            GastrointestinalWarnaBAB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),120).toString());
            KetGastrointestinalWarnaBAB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),121).toString());
            GastrointestinalBAK.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),122).toString());
            KetGastrointestinalBAK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),123).toString());
            GastrointestinalWarnaBAK.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),124).toString());
            KetGastrointestinalWarnaBAK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),125).toString());
            NeurologiMata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),126).toString());
            NeurologiPupil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),127).toString());
            NeurologiKelopak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),128).toString());
            KetNeurologiKelopak.setText(tbObat.getValueAt(tbObat.getSelectedRow(),129).toString());
            NeurologiKonjungtiva.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),130).toString());
            KetNeurologiKonjungtiva.setText(tbObat.getValueAt(tbObat.getSelectedRow(),131).toString());
            NeurologiSklera.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),132).toString());
            KetNeurologiSklera.setText(tbObat.getValueAt(tbObat.getSelectedRow(),133).toString());
            NeurologiPendengaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),134).toString());
            KetNeurologiPendengaran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),135).toString());
            NeurologiPenciuman.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),136).toString());
            KetNeurologiPenciuman.setText(tbObat.getValueAt(tbObat.getSelectedRow(),137).toString());
            IntegumentWarnaKulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),138).toString());
            IntegumentVernic.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),139).toString());
            IntegumentLanugo.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),140).toString());
            IntegumentTurgor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),141).toString());
            IntegumentKulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),142).toString());
            ReproduksiLaki.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),143).toString());
            KetReproduksiLaki.setText(tbObat.getValueAt(tbObat.getSelectedRow(),144).toString());
            ReproduksiPerempuan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),145).toString());
            KetReproduksiPerempuan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),146).toString());
            MuskuloskeletalLengan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),147).toString());
            KetMuskuloskeletalLengan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),148).toString());
            MuskuloskeletalTungkai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),149).toString());
            KetMuskuloskeletalTungkai.setText(tbObat.getValueAt(tbObat.getSelectedRow(),150).toString());
            MuskuloskletalRekoil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),151).toString());
            MuskuloskletalGaris.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),152).toString());
            KondisiPsikologis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),153).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),154).toString().contains("Tidak Ada Masalah")){
                AdakahPerilaku.setSelectedItem("Tidak Ada Masalah");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),154).toString().contains("Perilaku Kekerasan")){
                AdakahPerilaku.setSelectedItem("Perilaku Kekerasan");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),154).toString().contains("Gangguan Efek")){
                AdakahPerilaku.setSelectedItem("Gangguan Efek");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),154).toString().contains("Gangguan Memori")){
                AdakahPerilaku.setSelectedItem("Gangguan Memori");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),154).toString().contains("Halusinasi")){
                AdakahPerilaku.setSelectedItem("Halusinasi");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),154).toString().contains("Kecenderungan Percobaan Bunuh Diri")){
                AdakahPerilaku.setSelectedItem("Kecenderungan Percobaan Bunuh Diri");
            }else{
                AdakahPerilaku.setSelectedItem("Lain-lain");
            }
            KeteranganAdakahPerilaku.setText(tbObat.getValueAt(tbObat.getSelectedRow(),154).toString().replaceAll(AdakahPerilaku.getSelectedItem().toString()+", ",""));
            GangguanJiwa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),155).toString());
            HubunganAnggotaKeluarga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),156).toString());
            Agama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),157).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),158).toString().contains("Sendiri")){
                TinggalDengan.setSelectedItem("Sendiri");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),158).toString().contains("Orang Tua")){
                TinggalDengan.setSelectedItem("Orang Tua");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),158).toString().contains("Suami/Istri")){
                TinggalDengan.setSelectedItem("Suami/Istri");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),158).toString().contains("Keluarga")){
                TinggalDengan.setSelectedItem("Keluarga");
            }else{
                TinggalDengan.setSelectedItem("Lain-lain");
            }
            KeteranganTinggalDengan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),158).toString().replaceAll(TinggalDengan.getSelectedItem().toString()+", ",""));
            PekerjaanPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),159).toString());
            CaraBayar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),160).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),161).toString().contains("Tidak Ada")){
                NilaiKepercayaan.setSelectedItem("Tidak Ada");
            }else{
                NilaiKepercayaan.setSelectedItem("Ada");
            }
            KeteranganNilaiKepercayaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),161).toString().replaceAll(NilaiKepercayaan.getSelectedItem().toString()+", ",""));
            Bahasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),162).toString());
            PendidikanPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),163).toString());
            PendidikanPJ.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),164).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),165).toString().contains("Pasien")){
                EdukasiPsikolgis.setSelectedItem("Pasien");
            }else{
                EdukasiPsikolgis.setSelectedItem("Keluarga");
            }
            KeteranganEdukasiPsikologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),165).toString().replaceAll(EdukasiPsikolgis.getSelectedItem().toString()+", ",""));
            Penerimaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),166).toString());
            Pernikahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),167).toString());
            KeteranganPernikahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),168).toString());
            NIPS1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),169).toString());
            NilaiNIPS1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),170).toString());
            NIPS2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),171).toString());
            NilaiNIPS2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),172).toString());
            NIPS3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),173).toString());
            NilaiNIPS3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),174).toString());
            NIPS4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),175).toString());
            NilaiNIPS4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),176).toString());
            NIPS5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),178).toString());
            NilaiNIPS5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),179).toString());
            TotalNIPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),180).toString());
            KetNIPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),181).toString());
            SkalaGizi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),182).toString());
            NilaiGizi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),183).toString());
            SkalaGizi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),184).toString());
            NilaiGizi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),185).toString());
            SkalaGizi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),186).toString());
            NilaiGizi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),187).toString());
            NilaiGiziTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),188).toString());
            DiketahuiDietisen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),189).toString());
            KeteranganDiketahuiDietisen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),190).toString());
            Rencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),191).toString());
            
            try {
                Valid.tabelKosong(tabModeMasalah);
                
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan_neonatus.kode_masalah,master_masalah_keperawatan_neonatus.nama_masalah from master_masalah_keperawatan_neonatus "+
                        "inner join penilaian_awal_keperawatan_ranap_neonatus_masalah on penilaian_awal_keperawatan_ranap_neonatus_masalah.kode_masalah=master_masalah_keperawatan_neonatus.kode_masalah "+
                        "where penilaian_awal_keperawatan_ranap_neonatus_masalah.no_rawat=? order by penilaian_awal_keperawatan_ranap_neonatus_masalah.kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeMasalah.addRow(new Object[]{true,rs.getString(1),rs.getString(2)});
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
            
//            try {
//                Valid.tabelKosong(tabModeRencana);
//                
//                ps=koneksi.prepareStatement(
//                        "select master_rencana_keperawatan.kode_rencana,master_rencana_keperawatan.rencana_keperawatan from master_rencana_keperawatan "+
//                        "inner join penilaian_awal_keperawatan_ranap_neonatus_rencana on penilaian_awal_keperawatan_ranap_neonatus_rencana.kode_rencana=master_rencana_keperawatan.kode_rencana "+
//                        "where penilaian_awal_keperawatan_ranap_neonatus_rencana.no_rawat=? order by penilaian_awal_keperawatan_ranap_neonatus_rencana.kode_rencana");
//                try {
//                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
//                    rs=ps.executeQuery();
//                    while(rs.next()){
//                        tabModeRencana.addRow(new Object[]{true,rs.getString(1),rs.getString(2)});
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : "+e);
//                } finally{
//                    if(rs!=null){
//                        rs.close();
//                    }
//                    if(ps!=null){
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : "+e);
//            }
            
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,"+
                    "bahasa_pasien.nama_bahasa,pasien.pnd,pasien.pekerjaan "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "where pasien.no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Agama.setText(rs.getString("agama"));
                    Bahasa.setText(rs.getString("nama_bahasa"));
                    PendidikanPasien.setText(rs.getString("pnd"));
                    PekerjaanPasien.setText(rs.getString("pekerjaan"));
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
    
    public void setNoRm(String norwt, Date tgl2,String carabayar,String norm) {
        TNoRw.setText(norwt);
        TNoRM.setText(norm);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        CaraBayar.setText(carabayar);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ranapkebidanan());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ranapkebidanan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranapkebidanan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranapkebidanan()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", NmPetugas,KdPetugas.getText());
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
            FormMenu.setVisible(true);  
            FormMasalahRencana.setVisible(true);
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);  
            FormMasalahRencana.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void getMasalah() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRM1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
//            DetailRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),174).toString());
            
            try {
                Valid.tabelKosong(tabModeDetailMasalah);
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan_neonatus.kode_masalah,master_masalah_keperawatan_neonatus.nama_masalah from master_masalah_keperawatan_neonatus "+
                        "inner join penilaian_awal_keperawatan_ranap_neonatus_masalah on penilaian_awal_keperawatan_ranap_neonatus_masalah.kode_masalah=master_masalah_keperawatan_neonatus.kode_masalah "+
                        "where penilaian_awal_keperawatan_ranap_neonatus_masalah.no_rawat=? order by penilaian_awal_keperawatan_ranap_neonatus_masalah.kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeDetailMasalah.addRow(new Object[]{rs.getString(1),rs.getString(2)});
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
            
//            try {
//                Valid.tabelKosong(tabModeDetailRencana);
//                ps=koneksi.prepareStatement(
//                        "select master_rencana_keperawatan.kode_rencana,master_rencana_keperawatan.rencana_keperawatan from master_rencana_keperawatan "+
//                        "inner join penilaian_awal_keperawatan_ranap_neonatus_rencana on penilaian_awal_keperawatan_ranap_neonatus_rencana.kode_rencana=master_rencana_keperawatan.kode_rencana "+
//                        "where penilaian_awal_keperawatan_ranap_neonatus_rencana.no_rawat=? order by penilaian_awal_keperawatan_ranap_neonatus_rencana.kode_rencana");
//                try {
//                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
//                    rs=ps.executeQuery();
//                    while(rs.next()){
//                        tabModeDetailRencana.addRow(new Object[]{rs.getString(1),rs.getString(2)});
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : "+e);
//                } finally{
//                    if(rs!=null){
//                        rs.close();
//                    }
//                    if(ps!=null){
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : "+e);
//            }
        }
    }
   
    

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_awal_keperawatan_ranap_neonatus where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            TNoRM1.setText("");
            TPasien1.setText("");
            ChkAccor.setSelected(false);
            isMenu();
            tampil();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_awal_keperawatan_ranap_neonatus","no_rawat=?","no_rawat=?,tanggal=?,cara_masuk=?,tiba_diruang_rawat=?,informasi=?,ket_informasi=?,rps=?,g=?,p=?,a=?,uk=?,rpi=?,ket_rpi=?,rpo=?,rd=?,indikasi=?,gizi_ibu=?,g1=?,p1=?,a1=?,uk1=?,tanggal_lahir=?,kondisi=?,apgar=?,cara=?,ket_cara=?,letak=?,ketuban=?,tali=?,bb1=?,pb=?,lk=?,ld=?,lp=?,mayor=?,minor=?,nutrisi=?,ket_nutrisi=?,frekuensi=?,kali=?,keluhan_bak=?,keluhan_bab=?,alergi=?,alergi_obat=?,reaksi_obat=?,alergi_makanan=?,reaksi_makanan=?,alergi_lainnya=?,reaksi_lainnya=?,rpk=?,imunisasi=?,riwayat_obat=?,ket_obat=?,riwayat_merokok=?,riwayat_merokok_jumlah=?,riwayat_alkohol=?,riwayat_alkohol_jumlah=?,riwayat_narkoba=?,pemeriksaan_mental=?,pemeriksaan_keadaan_umum=?,pemeriksaan_gcs=?,pemeriksaan_nadi=?,pemeriksaan_rr=?,pemeriksaan_suhu=?,pemeriksaan_spo2=?,downes=?,pemeriksaan_bb=?,pemeriksaan_tb=?,lk1=?,ld1=?,lp1=?,darah_bayi=?,rh_bayi=?,darah_ibu=?,rh_ibu=?,darah_ayah=?,rh_ayah=?,gerak=?,pemeriksaan_susunan_kepala=?,pemeriksaan_susunan_kepala_keterangan=?,pemeriksaan_susunan_ubun=?,pemeriksaan_susunan_ubun_keterangan=?,pemeriksaan_susunan_wajah=?,pemeriksaan_susunan_wajah_keterangan=?,pemeriksaan_susunan_kejang=?,pemeriksaan_susunan_kejang_keterangan=?,pemeriksaan_susunan_refleks=?,pemeriksaan_susunan_refleks_keterangan=?,pemeriksaan_susunan_tangis=?,pemeriksaan_susunan_tangis_keterangan=?,pemeriksaan_kardiovaskuler_denyut_nadi=?,pemeriksaan_kardiovaskuler_sirkulasi=?,pemeriksaan_kardiovaskuler_sirkulasi_keterangan=?,pemeriksaan_kardiovaskuler_pulsasi=?,pemeriksaan_kardiovaskuler_pulsasi_keterangan=?,pemeriksaan_respirasi_pola_nafas=?,pemeriksaan_respirasi_jenis_pernafasan=?,pemeriksaan_respirasi_jenis_pernafasan_keterangan=?,pemeriksaan_respirasi_retraksi=?,pemeriksaan_respirasi_air=?,pemeriksaan_respirasi_merintih=?,pemeriksaan_respirasi_suara_nafas=?,pemeriksaan_gastrointestinal_mulut=?,pemeriksaan_gastrointestinal_mulut_keterangan=?,pemeriksaan_gastrointestinal_lidah=?,pemeriksaan_gastrointestinal_lidah_keterangan=?,pemeriksaan_gastrointestinal_tenggorokan=?,pemeriksaan_gastrointestinal_tenggorokan_keterangan=?,pemeriksaan_gastrointestinal_abdomen=?,pemeriksaan_gastrointestinal_abdomen_keterangan=?,pemeriksaan_gastrointestinal_bab=?,pemeriksaan_gastrointestinal_bab_keterangan=?,pemeriksaan_gastrointestinal_warna_bab=?,pemeriksaan_gastrointestinal_warna_bab_keterangan=?,pemeriksaan_gastrointestinal_bak=?,pemeriksaan_gastrointestinal_bak_keterangan=?,pemeriksaan_gastrointestinal_warna_bak=?,pemeriksaan_gastrointestinal_warna_bak_keterangan=?,pemeriksaan_neurologi_mata=?,pemeriksaan_neurologi_pupil=?,pemeriksaan_neurologi_kelopak=?,pemeriksaan_neurologi_kelopak_keterangan=?,pemeriksaan_neurologi_konjungtiva=?,pemeriksaan_neurologi_konjungtiva_keterangan=?,pemeriksaan_neurologi_sklera=?,pemeriksaan_neurologi_sklera_keterangan=?,pemeriksaan_neurologi_pendengaran=?,pemeriksaan_neurologi_pendengaran_keterangan=?,pemeriksaan_neurologi_penciuman=?,pemeriksaan_neurologi_penciuman_keterangan=?,pemeriksaan_integument_warnakulit=?,pemeriksaan_integument_vernic=?,pemeriksaan_integument_lanugo=?,pemeriksaan_integument_turgor=?,pemeriksaan_integument_kulit=?,pemeriksaan_reproduksi_laki=?,pemeriksaan_reproduksi_laki_keterangan=?,pemeriksaan_reproduksi_perempuan=?,pemeriksaan_reproduksi_perempuan_keterangan=?,pemeriksaan_muskuloskletal_lengan=?,pemeriksaan_muskuloskletal_lengan_keterangan=?,pemeriksaan_muskuloskletal_tungkai=?,pemeriksaan_muskuloskletal_tungkai_keterangan=?,pemeriksaan_muskuloskletal_rekoil=?,pemeriksaan_muskuloskletal_garis=?,riwayat_psiko_kondisi_psiko=?,riwayat_psiko_gangguan_jiwa=?,riwayat_psiko_perilaku=?,riwayat_psiko_perilaku_keterangan=?,riwayat_psiko_hubungan_keluarga=?,riwayat_psiko_tinggal=?,riwayat_psiko_tinggal_keterangan=?,riwayat_psiko_nilai_kepercayaan=?,riwayat_psiko_nilai_kepercayaan_keterangan=?,riwayat_psiko_pendidikan_pj=?,riwayat_psiko_edukasi_diberikan=?,riwayat_psiko_edukasi_diberikan_keterangan=?,riwayat_psiko_penerimaan=?,riwayat_psiko_pernikahan=?,riwayat_psiko_pernikahan_keterangan=?,nips1=?,nilai_nips1=?,nips2=?,nilai_nips2=?,nips3=?,nilai_nips3=?,nips4=?,nilai_nips4=?,nips5=?,nilai_nips5=?,total_nips=?,ket_nips=?,skrining_gizi1=?,nilai_gizi1=?,skrining_gizi2=?,nilai_gizi2=?,skrining_gizi3=?,nilai_gizi3=?,nilai_total_gizi=?,skrining_gizi_diketahui_dietisen=?,skrining_gizi_jam_diketahui_dietisen=?,rencana=?,nip1=?,nip2=?,kd_dokter=?",186,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),CaraMasuk.getSelectedItem().toString(),TibadiRuang.getSelectedItem().toString(),Anamnesis.getSelectedItem().toString(),KetAnamnesis.getText(), 
                    RPS.getText(),G.getText(),P.getText(),A.getText(),UK.getText(),RPI.getSelectedItem().toString(),KetRPI.getText(),RPO.getText(),RD.getSelectedItem().toString(),Indikasi.getText(),GiziIbu.getSelectedItem().toString(),G1.getText(),P1.getText(),A1.getText(),UK1.getText(), 
                    Valid.SetTgl(TanggalLahir.getSelectedItem()+"")+" "+TanggalLahir.getSelectedItem().toString().substring(11,19),Kondisi.getText(),Apgar.getText(),Cara.getSelectedItem().toString(),KetCara.getText(),Letak.getText(),Ketuban.getSelectedItem().toString(),Tali.getSelectedItem().toString(),
                    BB1.getText(),PB.getText(),LK.getText(),LD.getText(),LP.getText(),Mayor.getSelectedItem().toString(),Minor.getSelectedItem().toString(),Nutrisi.getSelectedItem().toString(),KetNutrisi.getText(),Frekuensi.getText(),Kali.getText(),KeluhanBAK.getText(),KeluhanBAB.getText(),
                    Alergi.getSelectedItem().toString(),AlergiObat.getText(),ReaksiObat.getText(),AlergiMakanan.getText(),ReaksiMakanan.getText(),AlergiLainnya.getText(),ReaksiLainnya.getText(),RPK.getText(),Imunisasi.getText(),KebiasaanObat.getSelectedItem().toString(),KetObat.getText(),
                    KebiasaanMerokok.getSelectedItem().toString(),KebiasaanJumlahRokok.getText(),KebiasaanAlkohol.getSelectedItem().toString(),KebiasaanJumlahAlkohol.getText(),KebiasaanNarkoba.getSelectedItem().toString(),KesadaranMental.getText(),KeadaanMentalUmum.getSelectedItem().toString(),
                    GCS.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),SpO2.getText(),Downes.getText(),BB.getText(),TB.getText(),LK1.getText(),LD1.getText(),LP1.getText(),DarahBayi.getSelectedItem().toString(),RhBayi.getSelectedItem().toString(),
                    DarahIbu.getSelectedItem().toString(),RhIbu.getSelectedItem().toString(),DarahAyah.getSelectedItem().toString(),RhAyah.getSelectedItem().toString(),Gerak.getSelectedItem().toString(),SistemSarafKepala.getSelectedItem().toString(),KetSistemSarafKepala.getText(),
                    SistemSarafUbun.getSelectedItem().toString(),KetSistemSarafUbun.getText(),SistemSarafWajah.getSelectedItem().toString(),KetSistemSarafWajah.getText(),SistemSarafKejang.getSelectedItem().toString(),KetSistemSarafKejang.getText(),
                    SistemSarafRefleks.getSelectedItem().toString(),KetSistemSarafRefleks.getText(),SistemSarafTangis.getSelectedItem().toString(),KetSistemSarafTangis.getText(),KardiovaskularDenyutNadi.getSelectedItem().toString(),KardiovaskularSirkulasi.getSelectedItem().toString(),
                    KetKardiovaskularSirkulasi.getText(),KardiovaskularPulsasi.getSelectedItem().toString(),KetKardiovaskularPulsasi.getText(),RespirasiPolaNafas.getSelectedItem().toString(),RespirasiJenisPernafasan.getSelectedItem().toString(),KetRespirasiJenisPernafasan.getText(), 
                    RespirasiRetraksi.getSelectedItem().toString(),RespirasiAir.getSelectedItem().toString(),RespirasiMerintih.getSelectedItem().toString(),RespirasiSuaraNafas.getSelectedItem().toString(),GastrointestinalMulut.getSelectedItem().toString(),KetGastrointestinalMulut.getText(),
                    GastrointestinalLidah.getSelectedItem().toString(),KetGastrointestinalLidah.getText(),GastrointestinalTenggorakan.getSelectedItem().toString(),KetGastrointestinalTenggorakan.getText(),GastrointestinalAbdomen.getSelectedItem().toString(),KetGastrointestinalAbdomen.getText(),
                    GastrointestinalBAB.getSelectedItem().toString(),KetGastrointestinalBAB.getText(),GastrointestinalWarnaBAB.getSelectedItem().toString(),KetGastrointestinalWarnaBAB.getText(),GastrointestinalBAK.getSelectedItem().toString(),KetGastrointestinalBAK.getText(),
                    GastrointestinalWarnaBAK.getSelectedItem().toString(),KetGastrointestinalWarnaBAK.getText(),NeurologiMata.getSelectedItem().toString(),NeurologiPupil.getSelectedItem().toString(),NeurologiKelopak.getSelectedItem().toString(),KetNeurologiKelopak.getText(),
                    NeurologiKonjungtiva.getSelectedItem().toString(),KetNeurologiKonjungtiva.getText(),NeurologiSklera.getSelectedItem().toString(),KetNeurologiSklera.getText(),NeurologiPendengaran.getSelectedItem().toString(),KetNeurologiPendengaran.getText(),
                    NeurologiPenciuman.getSelectedItem().toString(),KetNeurologiPenciuman.getText(),IntegumentWarnaKulit.getSelectedItem().toString(),IntegumentVernic.getSelectedItem().toString(),IntegumentLanugo.getSelectedItem().toString(),IntegumentTurgor.getSelectedItem().toString(),
                    IntegumentKulit.getSelectedItem().toString(),ReproduksiLaki.getSelectedItem().toString(),KetReproduksiLaki.getText(),ReproduksiPerempuan.getSelectedItem().toString(),KetReproduksiPerempuan.getText(),MuskuloskeletalLengan.getSelectedItem().toString(),
                    KetMuskuloskeletalLengan.getText(),MuskuloskeletalTungkai.getSelectedItem().toString(),KetMuskuloskeletalTungkai.getText(),MuskuloskletalRekoil.getSelectedItem().toString(),MuskuloskletalGaris.getSelectedItem().toString(),
                    KondisiPsikologis.getSelectedItem().toString(),GangguanJiwa.getSelectedItem().toString(),AdakahPerilaku.getSelectedItem().toString(),KeteranganAdakahPerilaku.getText(),HubunganAnggotaKeluarga.getSelectedItem().toString(),TinggalDengan.getSelectedItem().toString(),KeteranganTinggalDengan.getText(),
                    NilaiKepercayaan.getSelectedItem().toString(),KeteranganNilaiKepercayaan.getText(),PendidikanPJ.getSelectedItem().toString(),EdukasiPsikolgis.getSelectedItem().toString(),KeteranganEdukasiPsikologis.getText(),Penerimaan.getSelectedItem().toString(), 
                    Pernikahan.getSelectedItem().toString(),KeteranganPernikahan.getText(),NIPS1.getSelectedItem().toString(),NilaiNIPS1.getText(),NIPS2.getSelectedItem().toString(),NilaiNIPS2.getText(),NIPS3.getSelectedItem().toString(),NilaiNIPS3.getText(),
                    NIPS4.getSelectedItem().toString(),NilaiNIPS4.getText(),NIPS5.getSelectedItem().toString(),NilaiNIPS5.getText(),TotalNIPS.getText(),KetNIPS.getText(),SkalaGizi1.getSelectedItem().toString(),NilaiGizi1.getText(),SkalaGizi2.getSelectedItem().toString(),NilaiGizi2.getText(),
                    SkalaGizi3.getSelectedItem().toString(),NilaiGizi3.getText(),NilaiGiziTotal.getText(),DiketahuiDietisen.getSelectedItem().toString(),KeteranganDiketahuiDietisen.getText(),Rencana.getText(),KdPetugas.getText(),KdPetugas2.getText(),KdDPJP.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
             })==true){
                Sequel.meghapus("penilaian_awal_keperawatan_ranap_neonatus_masalah","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                    if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan2("penilaian_awal_keperawatan_ranap_neonatus_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()});
                    }
                }
//                Sequel.meghapus("penilaian_awal_keperawatan_ranap_neonatus_rencana","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
//                for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
//                    if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
//                        Sequel.menyimpan2("penilaian_awal_keperawatan_ranap_neonatus_rencana","?,?",2,new String[]{TNoRw.getText(),tbRencanaKeperawatan.getValueAt(i,1).toString()});
//                    }
//                }
                getMasalah();
                tampil();
//                DetailRencana.setText(Rencana.getText());
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }
    
    private void isTotalNIPS(){
        try {
            TotalNIPS.setText((Integer.parseInt(NilaiNIPS1.getText())+Integer.parseInt(NilaiNIPS2.getText())+Integer.parseInt(NilaiNIPS3.getText())+Integer.parseInt(NilaiNIPS4.getText())+Integer.parseInt(NilaiNIPS5.getText()))+"");
            if(Integer.parseInt(TotalNIPS.getText())<1){
                KetNIPS.setText("Tidak Nyeri");
            }else if(Integer.parseInt(TotalNIPS.getText())<3){
                KetNIPS.setText("Nyeri Ringan");
            }else if(Integer.parseInt(TotalNIPS.getText())<4){
                KetNIPS.setText("Nyeri Sedang");
            }else if(Integer.parseInt(TotalNIPS.getText())>4){
                KetNIPS.setText("Nyeri Hebat");
            }
        } catch (Exception e) {
            TotalNIPS.setText("0");
            KetNIPS.setText("Tidak Nyeri");
        }
    }
    
    private void isTotalGizi(){
        try {
            NilaiGiziTotal.setText((Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText()))+"");
        } catch (Exception e) {
            NilaiGiziTotal.setText("3");
        }
    }
    
    private void tampilMasalah() {
        try{
            jml=0;
            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            masalah=null;
            masalah=new String[jml];

            index=0;        
            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbMasalahKeperawatan.getValueAt(i,1).toString();
                    masalah[index]=tbMasalahKeperawatan.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeMasalah);

            for(i=0;i<jml;i++){
                tabModeMasalah.addRow(new Object[] {
                    pilih[i],kode[i],masalah[i]
                });
            }
            ps=koneksi.prepareStatement("select * from master_masalah_keperawatan_neonatus where kode_masalah like ? or nama_masalah like ? order by kode_masalah");
            try {
                ps.setString(1,"%"+TCariMasalah.getText().trim()+"%");
                ps.setString(2,"%"+TCariMasalah.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeMasalah.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilMasalah2() {
        try{
            jml=0;
            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            masalah=null;
            masalah=new String[jml];

            index=0;        
            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbMasalahKeperawatan.getValueAt(i,1).toString();
                    masalah[index]=tbMasalahKeperawatan.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeMasalah);

            for(i=0;i<jml;i++){
                tabModeMasalah.addRow(new Object[] {
                    pilih[i],kode[i],masalah[i]
                });
            }
            
            ps=koneksi.prepareStatement("select * from master_masalah_keperawatan_neonatus where kode_masalah like ? or nama_masalah like ? order by kode_masalah");
            try {
                ps.setString(1,"%"+TCariMasalah.getText().trim()+"%");
                ps.setString(2,"%"+TCariMasalah.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeMasalah.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
//    private void tampilRencana() {
//        try{
//            file=new File("./cache/rencanakeperawatan.iyem");
//            file.createNewFile();
//            fileWriter = new FileWriter(file);
//            iyem="";
//            ps=koneksi.prepareStatement("select * from master_rencana_keperawatan order by master_rencana_keperawatan.kode_rencana");
//            try {
//                rs=ps.executeQuery();
//                while(rs.next()){
//                    iyem=iyem+"{\"KodeMasalah\":\""+rs.getString(1)+"\",\"KodeRencana\":\""+rs.getString(2)+"\",\"NamaRencana\":\""+rs.getString(3)+"\"},";
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
//            fileWriter.write("{\"rencanakeperawatan\":["+iyem.substring(0,iyem.length()-1)+"]}");
//            fileWriter.flush();
//            fileWriter.close();
//            iyem=null;
//        }catch(Exception e){
//            System.out.println("Notifikasi : "+e);
//        }
//    }
    
//    private void tampilRencana2() {
//        try{
//            jml=0;
//            for(i=0;i<tbRencanaKeperawatan.getRowCount();i++){
//                if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
//                    jml++;
//                }
//            }
//
//            pilih=null;
//            pilih=new boolean[jml]; 
//            kode=null;
//            kode=new String[jml];
//            masalah=null;
//            masalah=new String[jml];
//
//            index=0;        
//            for(i=0;i<tbRencanaKeperawatan.getRowCount();i++){
//                if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
//                    pilih[index]=true;
//                    kode[index]=tbRencanaKeperawatan.getValueAt(i,1).toString();
//                    masalah[index]=tbRencanaKeperawatan.getValueAt(i,2).toString();
//                    index++;
//                }
//            } 
//
//            Valid.tabelKosong(tabModeRencana);
//
//            for(i=0;i<jml;i++){
//                tabModeRencana.addRow(new Object[] {
//                    pilih[i],kode[i],masalah[i]
//                });
//            }
//
//            myObj = new FileReader("./cache/rencanakeperawatan.iyem");
//            root = mapper.readTree(myObj);
//            response = root.path("rencanakeperawatan");
//            if(response.isArray()){
//                for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
//                    if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
//                        for(JsonNode list:response){
//                            if(list.path("KodeMasalah").asText().toLowerCase().equals(tbMasalahKeperawatan.getValueAt(i,1).toString())&&
//                                    list.path("NamaRencana").asText().toLowerCase().contains(TCariRencana.getText().toLowerCase())){
//                                tabModeRencana.addRow(new Object[]{
//                                    false,list.path("KodeRencana").asText(),list.path("NamaRencana").asText()
//                                });                    
//                            }
//                        }
//                    }
//                }
//            }
//            myObj.close();
//        }catch(Exception e){
//            System.out.println("Notifikasi : "+e);
//        }
//    }

    private void isTotalResikoSydney() {
//        try {
//            NilaiSydneyTotal.setText((Integer.parseInt(NilaiSydney1.getText())+Integer.parseInt(NilaiSydney2.getText())+Integer.parseInt(NilaiSydney3.getText())+Integer.parseInt(NilaiSydney4.getText())+Integer.parseInt(NilaiSydney5.getText())+Integer.parseInt(NilaiSydney6.getText())+Integer.parseInt(NilaiSydney7.getText())+Integer.parseInt(NilaiSydney8.getText())+Integer.parseInt(NilaiSydney9.getText())+Integer.parseInt(NilaiSydney10.getText())+Integer.parseInt(NilaiSydney11.getText()))+"");
//            if(Integer.parseInt(NilaiSydneyTotal.getText())<4){
//                TingkatSydney.setText("Tingkat Resiko : Risiko Rendah (1-3), Tindakan : Intervensi pencegahan risiko standar");
//            }else if(Integer.parseInt(NilaiSydneyTotal.getText())>=4){
//                TingkatSydney.setText("Tingkat Resiko : Risiko Sedang (> 4), Tindakan : Intervensi pencegahan risiko tinggi");
//            }
//        } catch (Exception e) {
//            NilaiSydneyTotal.setText("0");
//            TingkatSydney.setText("Tingkat Resiko : Risiko Rendah (1-3), Tindakan : Intervensi pencegahan risiko standar");
//        }
    }
}
