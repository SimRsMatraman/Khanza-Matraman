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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;


/**
 *
 * @author perpustakaan
 */
public final class RMDataEdukasi extends javax.swing.JDialog {
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
    public RMDataEdukasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Profesi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadEdukasiByTopik();  // Auto update Edukasi1-13
            }
        });
        
        Profesi.addActionListener(e -> {
            loadCheckboxEdukasi();  // Real-time checkbox update
        });
        
        Profesi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadSignaturePhotos();
                loadFormData(); 
            }
        });
        
        // Auto-load saat pilih No.Rawat
        TNoRw.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { loadFormData(); }
            public void removeUpdate(DocumentEvent e) { loadFormData(); }
            public void changedUpdate(DocumentEvent e) { loadFormData(); }
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
        "Merokok",
        "Alkohol",
        "Ketersediaan Pasien",
        "Alasan Kesediaan",
        "Kebutuhan Edukasi",
        "Hubungan",
        "ACC",
        "Status Pasien"
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

        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML1.setEditable(true);
        LoadHTML1.setEditorKit(kit);
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
        jLabel12 = new widget.Label();
        Kebutuhan = new widget.TextBox();
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
        jLabel20 = new widget.Label();
        Status = new widget.ComboBox();
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
        jLabel13 = new widget.Label();
        TanggalSurat = new widget.Tanggal();
        jLabel11 = new widget.Label();
        NoSurat = new widget.TextBox();
        Pengkajian = new widget.ComboBox();
        jLabel24 = new widget.Label();
        jLabel15 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel27 = new widget.Label();
        accep = new widget.TextBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        FormPhoto1 = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        BtnRefreshPhoto1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML1 = new widget.editorpane();
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
        Budaya = new widget.TextBox();
        jLabel36 = new widget.Label();
        Merokok = new widget.ComboBox();
        jLabel37 = new widget.Label();
        Alkohol = new widget.ComboBox();
        jLabel38 = new widget.Label();
        Informasi = new widget.ComboBox();
        Alasan = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel41 = new widget.Label();
        Jelaskan = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        Profesi = new widget.ComboBox();
        jLabel43 = new widget.Label();
        jLabel45 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel61 = new widget.Label();
        jLabel44 = new widget.Label();
        Edukasi1 = new widget.TextBox();
        jLabel51 = new widget.Label();
        Edukasi2 = new widget.TextBox();
        jLabel52 = new widget.Label();
        Edukasi3 = new widget.TextBox();
        jLabel53 = new widget.Label();
        Edukasi4 = new widget.TextBox();
        jLabel54 = new widget.Label();
        Edukasi5 = new widget.TextBox();
        jLabel55 = new widget.Label();
        Edukasi6 = new widget.TextBox();
        jLabel56 = new widget.Label();
        Edukasi7 = new widget.TextBox();
        jLabel57 = new widget.Label();
        Edukasi8 = new widget.TextBox();
        jLabel58 = new widget.Label();
        Edukasi9 = new widget.TextBox();
        jLabel60 = new widget.Label();
        Edukasi10 = new widget.TextBox();
        jLabel62 = new widget.Label();
        Edukasi11 = new widget.TextBox();
        jLabel63 = new widget.Label();
        Edukasi12 = new widget.TextBox();
        jLabel64 = new widget.Label();
        Edukasi13 = new widget.TextBox();
        Ceramah = new javax.swing.JCheckBox();
        Demontrasi = new javax.swing.JCheckBox();
        Diskusi = new javax.swing.JCheckBox();
        Observasi = new javax.swing.JCheckBox();
        Simulasi = new javax.swing.JCheckBox();
        Lefleat = new javax.swing.JCheckBox();
        Pamfleat = new javax.swing.JCheckBox();
        LembarBalik = new javax.swing.JCheckBox();
        Audiovisual = new javax.swing.JCheckBox();
        Mengerti = new javax.swing.JCheckBox();
        ReDemontrasi = new javax.swing.JCheckBox();
        ReEdukasi = new javax.swing.JCheckBox();
        scrollPane = new widget.ScrollPane();
        PemeriksaanRad = new widget.TextArea();
        scrollPane1 = new widget.ScrollPane();
        PemeriksaanLab = new widget.TextArea();
        scrollPane2 = new widget.ScrollPane();
        ObatPulang = new widget.TextArea();
        FormRencana = new widget.PanelBiasa();
        FormPass4 = new widget.PanelBiasa();
        BtnUpdateRencana = new widget.Button();
        BtnBatal1 = new widget.Button();
        scrollPane3 = new widget.ScrollPane();
        RencanaPemeriksaanPenunjang = new widget.TextArea();
        TanggalEdukasi = new widget.Tanggal();
        Btnlink = new widget.Button();

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

        jLabel12.setText("Kebutuhan Pendapat Kedua :");
        jLabel12.setName("jLabel12"); // NOI18N

        Kebutuhan.setEditable(false);
        Kebutuhan.setHighlighter(null);
        Kebutuhan.setName("Kebutuhan"); // NOI18N
        Kebutuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebutuhanKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Edukasi Pasien/Keluarga ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel20.setText("  Status :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel20);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Rawat Jalan", "Rawat Inap" }));
        Status.setName("Status"); // NOI18N
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        panelGlass9.add(Status);

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

        jLabel13.setText("Tanggal Surat :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(630, 40, 90, 23);

        TanggalSurat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2026" }));
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

        Pengkajian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Bahasa", "Gangguan Biasa", "Penglihatan Terganggu", "Budaya", "Kognotif Terbatas", "Pendengaran Terganggu", "Fisik Lemah", "Lain-lain" }));
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

        jLabel15.setText("Hubungan Dgn pasien :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(670, 80, 130, 23);

        Hubungan.setEditable(false);
        Hubungan.setHighlighter(null);
        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(810, 80, 270, 23);

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
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "TTE Sasaran : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormPhoto.setBounds(790, 150, 430, 280);

        FormPhoto1.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "TTE Edukator : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto1.setName("FormPhoto1"); // NOI18N
        FormPhoto1.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto1.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        FormPhoto1.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML1.setBorder(null);
        LoadHTML1.setName("LoadHTML1"); // NOI18N
        Scroll5.setViewportView(LoadHTML1);

        FormPhoto1.add(Scroll5, java.awt.BorderLayout.CENTER);

        FormInput.add(FormPhoto1);
        FormPhoto1.setBounds(1280, 150, 440, 280);

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
        Kepercayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepercayaanKeyPressed(evt);
            }
        });
        FormInput.add(Kepercayaan);
        Kepercayaan.setBounds(190, 230, 490, 23);

        jLabel34.setText("Tingkat Pengetahuan Kesehatan Pasien :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 260, 210, 23);

        Pengetahuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Paham", "Kurang Paham", "Tidak Paham" }));
        Pengetahuan.setName("Pengetahuan"); // NOI18N
        Pengetahuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengetahuanKeyPressed(evt);
            }
        });
        FormInput.add(Pengetahuan);
        Pengetahuan.setBounds(220, 260, 130, 23);

        jLabel35.setText("Budaya :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(370, 260, 70, 23);

        Budaya.setFocusTraversalPolicyProvider(true);
        Budaya.setName("Budaya"); // NOI18N
        Budaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BudayaKeyPressed(evt);
            }
        });
        FormInput.add(Budaya);
        Budaya.setBounds(450, 260, 230, 23);

        jLabel36.setText("Merokok :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 290, 180, 23);

        Merokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Merokok.setName("Merokok"); // NOI18N
        FormInput.add(Merokok);
        Merokok.setBounds(190, 290, 130, 23);

        jLabel37.setText("Alasan :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(320, 320, 70, 23);

        Alkohol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Alkohol.setName("Alkohol"); // NOI18N
        FormInput.add(Alkohol);
        Alkohol.setBounds(400, 290, 130, 23);

        jLabel38.setText("Kesediaan Menerima Informasi :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 320, 180, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Informasi.setName("Informasi"); // NOI18N
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

        jLabel41.setText("Jelaskan :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(110, 350, 70, 23);

        Jelaskan.setFocusTraversalPolicyProvider(true);
        Jelaskan.setName("Jelaskan"); // NOI18N
        Jelaskan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JelaskanKeyPressed(evt);
            }
        });
        FormInput.add(Jelaskan);
        Jelaskan.setBounds(190, 350, 540, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 390, 880, 1);

        Profesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "HAK PASIEN DAN KELUARGA", "ROHANIAWAN", "NILAI - NILAI KEPERCAYAAN", "DOKTER SPESIALIST/DOKTER UMUM", "GELANG IDENTITAS/GELANG RESIKO", "MANAJEMEN NYERI", "REHABILITASI MEDIK", "MANAJEMEN RISIKO JATUH", "CUCI TANGAN", "NUTRISI", "PENGGUNAAN ALAT KESEHATAN", "EDUKASI KOLABORASI", "PENKES UNTUK DIRUMAH" }));
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
        Profesi.setBounds(180, 490, 540, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("METODE EDUKASI");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(870, 530, 110, 23);

        jLabel45.setText("Topik Edukasi :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(50, 490, 120, 23);

        jLabel47.setText("EDUKASI");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(320, 520, 60, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("MEDIA EDUKASI");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(1110, 530, 100, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("EVALUASI RESPON");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(1370, 530, 120, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 1427, 880, 3);

        jLabel61.setText("EDUKASI DILAKUKAN");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(50, 440, 120, 23);

        jLabel44.setText("Edukasi 1 :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(40, 550, 80, 23);

        Edukasi1.setEditable(false);
        Edukasi1.setFocusTraversalPolicyProvider(true);
        Edukasi1.setName("Edukasi1"); // NOI18N
        Edukasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi1KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi1);
        Edukasi1.setBounds(130, 550, 670, 23);

        jLabel51.setText("Edukasi 2 :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(40, 580, 80, 23);

        Edukasi2.setEditable(false);
        Edukasi2.setFocusTraversalPolicyProvider(true);
        Edukasi2.setName("Edukasi2"); // NOI18N
        Edukasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi2KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi2);
        Edukasi2.setBounds(130, 580, 670, 23);

        jLabel52.setText("Edukasi 3 :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(40, 610, 80, 23);

        Edukasi3.setEditable(false);
        Edukasi3.setFocusTraversalPolicyProvider(true);
        Edukasi3.setName("Edukasi3"); // NOI18N
        Edukasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi3KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi3);
        Edukasi3.setBounds(130, 610, 670, 23);

        jLabel53.setText("Edukasi 4 :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(40, 640, 80, 23);

        Edukasi4.setEditable(false);
        Edukasi4.setFocusTraversalPolicyProvider(true);
        Edukasi4.setName("Edukasi4"); // NOI18N
        Edukasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi4KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi4);
        Edukasi4.setBounds(130, 640, 670, 23);

        jLabel54.setText("Edukasi 5 :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(40, 670, 80, 23);

        Edukasi5.setEditable(false);
        Edukasi5.setFocusTraversalPolicyProvider(true);
        Edukasi5.setName("Edukasi5"); // NOI18N
        Edukasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi5KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi5);
        Edukasi5.setBounds(130, 670, 670, 23);

        jLabel55.setText("Edukasi 6 :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(40, 700, 80, 23);

        Edukasi6.setEditable(false);
        Edukasi6.setFocusTraversalPolicyProvider(true);
        Edukasi6.setName("Edukasi6"); // NOI18N
        Edukasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi6KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi6);
        Edukasi6.setBounds(130, 700, 670, 23);

        jLabel56.setText("Edukasi 7 :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(40, 730, 80, 23);

        Edukasi7.setEditable(false);
        Edukasi7.setFocusTraversalPolicyProvider(true);
        Edukasi7.setName("Edukasi7"); // NOI18N
        Edukasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi7KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi7);
        Edukasi7.setBounds(130, 730, 670, 23);

        jLabel57.setText("Edukasi 8 :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(40, 760, 80, 23);

        Edukasi8.setEditable(false);
        Edukasi8.setFocusTraversalPolicyProvider(true);
        Edukasi8.setName("Edukasi8"); // NOI18N
        Edukasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi8KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi8);
        Edukasi8.setBounds(130, 760, 670, 23);

        jLabel58.setText("Edukasi 9 :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(40, 790, 80, 23);

        Edukasi9.setEditable(false);
        Edukasi9.setFocusTraversalPolicyProvider(true);
        Edukasi9.setName("Edukasi9"); // NOI18N
        Edukasi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi9KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi9);
        Edukasi9.setBounds(130, 790, 670, 23);

        jLabel60.setText("Edukasi 10 :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(40, 820, 80, 23);

        Edukasi10.setEditable(false);
        Edukasi10.setFocusTraversalPolicyProvider(true);
        Edukasi10.setName("Edukasi10"); // NOI18N
        Edukasi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi10KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi10);
        Edukasi10.setBounds(130, 820, 670, 23);

        jLabel62.setText("Edukasi 11 :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(40, 850, 80, 23);

        Edukasi11.setEditable(false);
        Edukasi11.setFocusTraversalPolicyProvider(true);
        Edukasi11.setName("Edukasi11"); // NOI18N
        Edukasi11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi11KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi11);
        Edukasi11.setBounds(130, 850, 670, 23);

        jLabel63.setText("Edukasi 12 :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(40, 880, 80, 23);

        Edukasi12.setEditable(false);
        Edukasi12.setFocusTraversalPolicyProvider(true);
        Edukasi12.setName("Edukasi12"); // NOI18N
        Edukasi12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi12KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi12);
        Edukasi12.setBounds(130, 880, 670, 23);

        jLabel64.setText("Edukasi 13 :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(40, 910, 80, 23);

        Edukasi13.setEditable(false);
        Edukasi13.setFocusTraversalPolicyProvider(true);
        Edukasi13.setName("Edukasi13"); // NOI18N
        Edukasi13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi13KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi13);
        Edukasi13.setBounds(130, 910, 670, 23);

        Ceramah.setText("Ceramah");
        Ceramah.setName("Ceramah"); // NOI18N
        FormInput.add(Ceramah);
        Ceramah.setBounds(870, 560, 160, 20);

        Demontrasi.setText("Demontrasi");
        Demontrasi.setName("Demontrasi"); // NOI18N
        Demontrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DemontrasiActionPerformed(evt);
            }
        });
        FormInput.add(Demontrasi);
        Demontrasi.setBounds(870, 580, 170, 20);

        Diskusi.setText("Diskusi");
        Diskusi.setName("Diskusi"); // NOI18N
        FormInput.add(Diskusi);
        Diskusi.setBounds(870, 600, 170, 20);

        Observasi.setText("Observasi");
        Observasi.setName("Observasi"); // NOI18N
        Observasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ObservasiActionPerformed(evt);
            }
        });
        FormInput.add(Observasi);
        Observasi.setBounds(870, 620, 170, 20);

        Simulasi.setText("Simulasi");
        Simulasi.setName("Simulasi"); // NOI18N
        Simulasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SimulasiActionPerformed(evt);
            }
        });
        FormInput.add(Simulasi);
        Simulasi.setBounds(870, 640, 170, 20);

        Lefleat.setText("Lefleat");
        Lefleat.setName("Lefleat"); // NOI18N
        Lefleat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LefleatActionPerformed(evt);
            }
        });
        FormInput.add(Lefleat);
        Lefleat.setBounds(1110, 560, 190, 20);

        Pamfleat.setText("Pamfleat");
        Pamfleat.setName("Pamfleat"); // NOI18N
        Pamfleat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PamfleatActionPerformed(evt);
            }
        });
        FormInput.add(Pamfleat);
        Pamfleat.setBounds(1110, 580, 190, 20);

        LembarBalik.setText("Lembar Balik");
        LembarBalik.setName("LembarBalik"); // NOI18N
        LembarBalik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LembarBalikActionPerformed(evt);
            }
        });
        FormInput.add(LembarBalik);
        LembarBalik.setBounds(1110, 600, 210, 20);

        Audiovisual.setText("Audiovisual");
        Audiovisual.setName("Audiovisual"); // NOI18N
        Audiovisual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AudiovisualActionPerformed(evt);
            }
        });
        FormInput.add(Audiovisual);
        Audiovisual.setBounds(1110, 620, 210, 20);

        Mengerti.setText("Mengerti");
        Mengerti.setName("Mengerti"); // NOI18N
        Mengerti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MengertiActionPerformed(evt);
            }
        });
        FormInput.add(Mengerti);
        Mengerti.setBounds(1370, 560, 170, 20);

        ReDemontrasi.setText("Re Demontrasi");
        ReDemontrasi.setName("ReDemontrasi"); // NOI18N
        ReDemontrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReDemontrasiActionPerformed(evt);
            }
        });
        FormInput.add(ReDemontrasi);
        ReDemontrasi.setBounds(1370, 580, 210, 20);

        ReEdukasi.setText("Re Edukasi");
        ReEdukasi.setName("ReEdukasi"); // NOI18N
        ReEdukasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReEdukasiActionPerformed(evt);
            }
        });
        FormInput.add(ReEdukasi);
        ReEdukasi.setBounds(1370, 600, 170, 20);

        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane.setName("scrollPane"); // NOI18N

        PemeriksaanRad.setEditable(false);
        PemeriksaanRad.setBorder(javax.swing.BorderFactory.createTitledBorder("Hasil pemeriksaan Radiologi"));
        PemeriksaanRad.setColumns(20);
        PemeriksaanRad.setRows(5);
        PemeriksaanRad.setName("PemeriksaanRad"); // NOI18N
        PemeriksaanRad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanRadKeyPressed(evt);
            }
        });
        scrollPane.setViewportView(PemeriksaanRad);

        FormInput.add(scrollPane);
        scrollPane.setBounds(60, 950, 360, 230);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        PemeriksaanLab.setEditable(false);
        PemeriksaanLab.setBorder(javax.swing.BorderFactory.createTitledBorder("Hasil pemeriksaan Laboratorium"));
        PemeriksaanLab.setColumns(20);
        PemeriksaanLab.setRows(5);
        PemeriksaanLab.setName("PemeriksaanLab"); // NOI18N
        PemeriksaanLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanLabKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(PemeriksaanLab);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(430, 950, 370, 230);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        ObatPulang.setEditable(false);
        ObatPulang.setBorder(javax.swing.BorderFactory.createTitledBorder("Obat yang di bawa pulang dan cara penggunaan"));
        ObatPulang.setColumns(20);
        ObatPulang.setRows(5);
        ObatPulang.setName("ObatPulang"); // NOI18N
        ObatPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatPulangKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(ObatPulang);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(430, 1190, 370, 230);

        FormRencana.setBackground(new java.awt.Color(255, 255, 255));
        FormRencana.setBorder(null);
        FormRencana.setName("FormRencana"); // NOI18N
        FormRencana.setPreferredSize(new java.awt.Dimension(115, 73));
        FormRencana.setLayout(new java.awt.BorderLayout());

        FormPass4.setBackground(new java.awt.Color(255, 255, 255));
        FormPass4.setBorder(null);
        FormPass4.setName("FormPass4"); // NOI18N
        FormPass4.setPreferredSize(new java.awt.Dimension(115, 40));

        BtnUpdateRencana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnUpdateRencana.setMnemonic('U');
        BtnUpdateRencana.setText("Update");
        BtnUpdateRencana.setToolTipText("Alt+U");
        BtnUpdateRencana.setName("BtnUpdateRencana"); // NOI18N
        BtnUpdateRencana.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnUpdateRencana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdateRencanaActionPerformed(evt);
            }
        });
        FormPass4.add(BtnUpdateRencana);

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
        FormPass4.add(BtnBatal1);

        FormRencana.add(FormPass4, java.awt.BorderLayout.PAGE_END);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setPreferredSize(new java.awt.Dimension(172, 170));

        RencanaPemeriksaanPenunjang.setBorder(javax.swing.BorderFactory.createTitledBorder("Rencana pemeriksaan penunjang"));
        RencanaPemeriksaanPenunjang.setColumns(20);
        RencanaPemeriksaanPenunjang.setRows(5);
        RencanaPemeriksaanPenunjang.setName("RencanaPemeriksaanPenunjang"); // NOI18N
        RencanaPemeriksaanPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaPemeriksaanPenunjangKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RencanaPemeriksaanPenunjang);

        FormRencana.add(scrollPane3, java.awt.BorderLayout.PAGE_START);

        FormInput.add(FormRencana);
        FormRencana.setBounds(60, 1190, 360, 230);

        TanggalEdukasi.setEditable(false);
        TanggalEdukasi.setForeground(new java.awt.Color(50, 70, 50));
        TanggalEdukasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2026" }));
        TanggalEdukasi.setDisplayFormat("dd-MM-yyyy");
        TanggalEdukasi.setName("TanggalEdukasi"); // NOI18N
        TanggalEdukasi.setOpaque(false);
        TanggalEdukasi.setPreferredSize(new java.awt.Dimension(141, 18));
        TanggalEdukasi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalEdukasiItemStateChanged(evt);
            }
        });
        TanggalEdukasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalEdukasiActionPerformed(evt);
            }
        });
        TanggalEdukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalEdukasiKeyPressed(evt);
            }
        });
        FormInput.add(TanggalEdukasi);
        TanggalEdukasi.setBounds(730, 490, 90, 23);

        Btnlink.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        Btnlink.setMnemonic('2');
        Btnlink.setToolTipText("Alt+2");
        Btnlink.setName("Btnlink"); // NOI18N
        Btnlink.setPreferredSize(new java.awt.Dimension(28, 23));
        Btnlink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnlinkActionPerformed(evt);
            }
        });
        Btnlink.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnlinkKeyPressed(evt);
            }
        });
        FormInput.add(Btnlink);
        Btnlink.setBounds(200, 440, 28, 20);

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
    
    if (KodeDokter.getText().trim().isEmpty()) {
        Valid.textKosong(BtnDokter, "Pengkaji/NIK ");
        KodeDokter.requestFocus();  // Cursor ke field
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
                "pendidikan,agama,nilai_kepercayaan,merokok,alkohol,tingkat_pengetahuan," +
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

                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +  // sampai kebutuhan_edukasi

                "'','-'," +  // Nama_kel, hubungan

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
        ps.setString(i++, Kepercayaan.getSelectedItem().toString());
        ps.setString(i++, Merokok.getSelectedItem().toString());
        ps.setString(i++, Alkohol.getSelectedItem().toString());
        ps.setString(i++, Pengetahuan.getSelectedItem().toString());
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
        if (NoSurat.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit !");
            return;
        }
    
        if (KodeDokter.getText().trim().isEmpty()) {
            Valid.textKosong(BtnDokter, "Pengkaji/NIK ");
            KodeDokter.requestFocus();  // Cursor ke field
            return;
        }

        String tanggalSurat = Valid.SetTgl(TanggalSurat.getSelectedItem()+"");
        String tanggalPengkajian = Valid.SetTgl(TanggalSurat.getSelectedItem()+"");
        String jamSekarang = jamNow.format(new Date());

        if (Sequel.mengedittf(
            "edukasi_pasien",
                "no_surat=?",
                "no_rawat=?,"
                    + "tanggal_surat=?,"
                    + "jam=?,"
                    + "tanggal_pengkajian=?,"
                    + "jam_pengkajian=?,"
                    + "hambatan_edukasi=?,"
                    + "hambatan_edukasi_lain=?,"
                    + "nik=?,"
                    + "bicara=?,"
                    + "kemampuan_membaca=?,"
                    + "bahasa_sehari=?,"
                    + "penerjemah=?,"
                    + "budaya=?,"
                    + "bahasa_isyarat=?,"
                    + "pendidikan=?,"
                    + "agama=?,"
                    + "nilai_kepercayaan=?,"
                    + "tingkat_pengetahuan=?,"
                    + "merokok=?,"
                    + "alkohol=?,"
                    + "ketersediaan_pasiein=?,"
                    + "alasan_kesediaan=?,"
                    + "kebutuhan_edukasi=?,"
                    + "status=?",
            25,
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
                Kepercayaan.getSelectedItem().toString(),
                Pengetahuan.getSelectedItem().toString(),
                Merokok.getSelectedItem().toString(),
                Alkohol.getSelectedItem().toString(),
                Informasi.getSelectedItem().toString(),
                Alasan.getText(),
                Jelaskan.getText(),
                "0",

                // WHERE
                NoSurat.getText()
            }

        )) 
        {
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
        }else if(Profesi.getSelectedIndex()==1){
        }else if(Profesi.getSelectedIndex()==2){
        }else if(Profesi.getSelectedIndex()==3){
        }else if(Profesi.getSelectedIndex()==4){
        }else if(Profesi.getSelectedIndex()==5){
        }else if(Profesi.getSelectedIndex()==6){
        }else if(Profesi.getSelectedIndex()==7){
        }else if(Profesi.getSelectedIndex()==8){
        }else if(Profesi.getSelectedIndex()==9){
        }else if(Profesi.getSelectedIndex()==10){
        }else if(Profesi.getSelectedIndex()==11){
        }else if(Profesi.getSelectedIndex()==12){
        }else if(Profesi.getSelectedIndex()==13){
        }
    }//GEN-LAST:event_ProfesiItemStateChanged

    private void JelaskanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JelaskanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JelaskanKeyPressed

    private void AlasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlasanKeyPressed

    private void PengetahuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengetahuanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengetahuanKeyPressed

    private void AgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AgamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgamaKeyPressed

    private void PendidikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendidikanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendidikanKeyPressed

    private void IsyaratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IsyaratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IsyaratKeyPressed

    private void PenerjemahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenerjemahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenerjemahKeyPressed

    private void MembacaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MembacaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MembacaKeyPressed

    private void BahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BahasaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BahasaKeyPressed

    private void BicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BicaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BicaraKeyPressed

    private void PengkajianLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengkajianLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengkajianLainnyaKeyPressed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
//        loadSasaranPhoto();
//        loadEdukatorPhoto();
        loadSignaturePhotos(); 
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
//        autoNumberX(TanggalSurat.getSelectedItem()+"");
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

    private void KepercayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepercayaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KepercayaanKeyPressed

    private void DemontrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DemontrasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DemontrasiActionPerformed

    private void SimulasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SimulasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SimulasiActionPerformed

    private void ObservasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ObservasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ObservasiActionPerformed

    private void MengertiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MengertiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MengertiActionPerformed

    private void LefleatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LefleatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LefleatActionPerformed

    private void PamfleatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PamfleatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PamfleatActionPerformed

    private void LembarBalikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LembarBalikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LembarBalikActionPerformed

    private void AudiovisualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AudiovisualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AudiovisualActionPerformed

    private void ReDemontrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReDemontrasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReDemontrasiActionPerformed

    private void ReEdukasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReEdukasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReEdukasiActionPerformed

    private void Edukasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi2KeyPressed

    private void Edukasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi3KeyPressed

    private void Edukasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi4KeyPressed

    private void Edukasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi5KeyPressed

    private void Edukasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi6KeyPressed

    private void Edukasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi7KeyPressed

    private void Edukasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi8KeyPressed

    private void Edukasi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi9KeyPressed

    private void Edukasi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi10KeyPressed

    private void Edukasi11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi11KeyPressed

    private void Edukasi12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi12KeyPressed

    private void Edukasi13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi13KeyPressed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void PemeriksaanRadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanRadKeyPressed
        //        Valid.pindah(evt,BMI,RPK);
    }//GEN-LAST:event_PemeriksaanRadKeyPressed

    private void ObatPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ObatPulangKeyPressed

    private void TanggalEdukasiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalEdukasiItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalEdukasiItemStateChanged

    private void TanggalEdukasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalEdukasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalEdukasiActionPerformed

    private void TanggalEdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalEdukasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalEdukasiKeyPressed

    private void BtnlinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnlinkActionPerformed
        try {
            String baseUrl = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB();
            String fullUrl = baseUrl + "/webapps/verified/epri.php";

            // Buka di default browser
            java.awt.Desktop.getDesktop().browse(new java.net.URI(fullUrl));

        } catch (Exception e) {
        }
    }//GEN-LAST:event_BtnlinkActionPerformed

    private void BtnlinkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnlinkKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnlinkKeyPressed

    private void PemeriksaanLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanLabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemeriksaanLabKeyPressed

    private void BudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BudayaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BudayaKeyPressed

    private void RencanaPemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaPemeriksaanPenunjangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RencanaPemeriksaanPenunjangKeyPressed

    private void BtnUpdateRencanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateRencanaActionPerformed
        if(TNoRw.getText().trim().isEmpty() || TNoRM.getText().trim().isEmpty() || TPasien.getText().trim().isEmpty()){
            Valid.textKosong(TNoRw,"Pasien"); return;
        }

        if(tbObat.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(rootPane," Pilih row rencana!"); return;
        }

        int row = tbObat.getSelectedRow();
        String noRawatTabel = tbObat.getValueAt(row, 1).toString();
        String noSuratTabel = tbObat.getValueAt(row, 0).toString();

        if(Sequel.mengedittf(
            "edukasi_pasien", 
            "no_surat=?",          
            "rencana_penunjang=?",
            2,
            new String[]{
                RencanaPemeriksaanPenunjang.getText().trim(),
                noSuratTabel
            }
        )) {
            tampil();
            JOptionPane.showMessageDialog(rootPane,"Rencana berhasil diupdate!");
            String rencana = Sequel.cariIsi("select rencana_penunjang from edukasi_pasien where no_rawat=?", TNoRw.getText());
            RencanaPemeriksaanPenunjang.setText(rencana);
        } else {
            JOptionPane.showMessageDialog(rootPane,"Gagal update! Cek console debug");
        }

    }//GEN-LAST:event_BtnUpdateRencanaActionPerformed

    private void BtnBatal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatal1ActionPerformed
        emptTeksrencana();
    }//GEN-LAST:event_BtnBatal1ActionPerformed

    private void BtnBatal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBatal1KeyPressed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataEdukasi dialog = new RMDataEdukasi(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox Audiovisual;
    private widget.TextBox Bahasa;
    private widget.ComboBox Bicara;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnBatal1;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.Button BtnUpdateRencana;
    private widget.Button Btnlink;
    private widget.TextBox Budaya;
    private javax.swing.JCheckBox Ceramah;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JCheckBox Demontrasi;
    private javax.swing.JCheckBox Diskusi;
    private widget.TextBox Edukasi1;
    private widget.TextBox Edukasi10;
    private widget.TextBox Edukasi11;
    private widget.TextBox Edukasi12;
    private widget.TextBox Edukasi13;
    private widget.TextBox Edukasi2;
    private widget.TextBox Edukasi3;
    private widget.TextBox Edukasi4;
    private widget.TextBox Edukasi5;
    private widget.TextBox Edukasi6;
    private widget.TextBox Edukasi7;
    private widget.TextBox Edukasi8;
    private widget.TextBox Edukasi9;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPass4;
    private widget.PanelBiasa FormPhoto;
    private widget.PanelBiasa FormPhoto1;
    private widget.PanelBiasa FormRencana;
    private widget.TextBox Hubungan;
    private widget.ComboBox Informasi;
    private widget.ComboBox Isyarat;
    private widget.TextBox Jelaskan;
    private widget.TextBox Kebutuhan;
    private widget.ComboBox Kepercayaan;
    private widget.TextBox KodeDokter;
    private widget.Label LCount;
    private javax.swing.JCheckBox Lefleat;
    private javax.swing.JCheckBox LembarBalik;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML1;
    private widget.ComboBox Membaca;
    private javax.swing.JCheckBox Mengerti;
    private widget.ComboBox Merokok;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox NamaDokter;
    private widget.TextBox NoSurat;
    private widget.TextArea ObatPulang;
    private javax.swing.JCheckBox Observasi;
    private javax.swing.JCheckBox Pamfleat;
    private javax.swing.JPanel PanelInput;
    private widget.TextArea PemeriksaanLab;
    private widget.TextArea PemeriksaanRad;
    private widget.TextBox Pendidikan;
    private widget.ComboBox Penerjemah;
    private widget.ComboBox Pengetahuan;
    private widget.ComboBox Pengkajian;
    private widget.TextBox PengkajianLainnya;
    private widget.ComboBox Profesi;
    private javax.swing.JCheckBox ReDemontrasi;
    private javax.swing.JCheckBox ReEdukasi;
    private widget.TextArea RencanaPemeriksaanPenunjang;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private javax.swing.JCheckBox Simulasi;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalEdukasi;
    private widget.Tanggal TanggalSurat;
    private widget.TextBox accep;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
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
    private widget.Label jLabel41;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
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
    private widget.ScrollPane scrollPane;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
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
//            "sb.nama_suku_bangsa AS budaya, " +
            "ep.budaya, " +
            "ep.pendidikan, ep.agama, ep.tingkat_pengetahuan, " +
            "ep.alkohol, ep.merokok, " +
            "ep.ketersediaan_pasiein, ep.alasan_kesediaan, ep.kebutuhan_edukasi, " +
            "ep.hubungan, ep.acc_hpk, rp.status_lanjut " +
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
        // FILTER STATUS
        // =====================
        String statusText = (Status.getSelectedItem() != null) ? Status.getSelectedItem().toString() : "";
        if (!statusText.equals("Semua")) {
            String statusValue = statusText.equals("Rawat Jalan") ? "Ralan" : "Ranap";
            kondisi.add("rp.status_lanjut = ?");
            params.add(statusValue);
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
                rs.getString("merokok"),
                rs.getString("alkohol"),
                rs.getString("ketersediaan_pasiein"),
                rs.getString("alasan_kesediaan"),
                rs.getString("kebutuhan_edukasi"),
                rs.getString("hubungan"),
                rs.getString("acc_hpk"),
                rs.getString("status_lanjut")
            });
        }

    } catch (Exception e) {
        System.out.println("Notifikasi : " + e);
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (Exception ex) {
            System.out.println("Error close: " + ex);
        }
    }
}
    public void emptTeks() {
        KodeDokter.setText("");
        NamaDokter.setText("");
        Jelaskan.setText("");
        Bahasa.setText("");
        Budaya.setText("");
        Pendidikan.setText("");
        Agama.setText("");
        Kepercayaan.setSelectedIndex(0);
        Pengkajian.setSelectedIndex(0);
        PengkajianLainnya.setText("");
        Bicara.setSelectedIndex(0);
        Membaca.setSelectedIndex(0);
        Penerjemah.setSelectedIndex(0);
        Isyarat.setSelectedIndex(0);
        Informasi.setSelectedIndex(0);
        Merokok.setSelectedIndex(0);
        Alkohol.setSelectedIndex(0);
        Alasan.setText("");
        Profesi.setSelectedIndex(0);
        PemeriksaanRad.setText("");
        PemeriksaanLab.setText("");
        ObatPulang.setText("");

//        // Auto nomor surat - DETECT SOURCE SIMPEL
//        String prefix = (getParent() instanceof javax.swing.JDialog) ? "EPRJ" : "EPRI";  // JDialog=Jalan, Frame=Inap
//
//        Valid.autoNomer3(
//            "select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from edukasi_pasien " +
//            "where tanggal_surat='" + Valid.SetTgl(TanggalSurat.getSelectedItem()+"") + "' ",
//            prefix + TanggalSurat.getSelectedItem().toString().substring(6,10)
//                  + TanggalSurat.getSelectedItem().toString().substring(3,5)
//                  + TanggalSurat.getSelectedItem().toString().substring(0,2),
//            4, NoSurat
//        );

        NoSurat.requestFocus();
    } 
    
    public void emptTeksrencana() {
        RencanaPemeriksaanPenunjang.setText("");
        NoSurat.requestFocus();
    } 

    private void getData() {
        emptTeks();
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
            Isyarat.setSelectedItem(tbObat.getValueAt(row, 13).toString());
            Pendidikan.setText(tbObat.getValueAt(row, 14).toString());
            Agama.setText(tbObat.getValueAt(row, 15).toString());
            Pengetahuan.setSelectedItem(tbObat.getValueAt(row, 16).toString());
            Merokok.setSelectedItem(tbObat.getValueAt(row, 17).toString());
            Alkohol.setSelectedItem(tbObat.getValueAt(row, 18).toString());

            Alasan.setText(tbObat.getValueAt(row, 20).toString());
            Jelaskan.setText(tbObat.getValueAt(row, 21).toString());
            Hubungan.setText(tbObat.getValueAt(row, 22).toString());
            accep.setText(tbObat.getValueAt(row, 23).toString());
            
            
            Sequel.cariIsi("select nilai_kepercayaan from edukasi_pasien where no_rawat='"+TNoRw.getText()+"' ",Kepercayaan);

            loadSignaturePhotos();
            
            loadEdukasiByTopik();
            
            loadCheckboxEdukasi();
            
            isRawat();
            
            Budaya.setText(tbObat.getValueAt(row, 12).toString());

            Informasi.setSelectedItem(tbObat.getValueAt(row, 19).toString());

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
            "suku_bangsa.nama_suku_bangsa as budaya " +
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
                Budaya.setText(rs.getString("budaya")); // <-- tambahkan ini
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
    
    //Menapilkan Radiologi
        try {
            ps=koneksi.prepareStatement(
                    "SELECT a.no_rawat, "+
                    "a.no_rkm_medis, "+
                    "IFNULL(d.nm_perawatan,'')as nm_perawatan, "+
                    "b.hasil "+
                    "FROM reg_periksa a "+
                    "LEFT JOIN hasil_radiologi b ON b.no_rawat = a.no_rawat "+
                    "LEFT JOIN periksa_radiologi c ON c.no_rawat = a.no_rawat "+
                    "LEFT JOIN jns_perawatan_radiologi d ON d.kd_jenis_prw = c.kd_jenis_prw "+
                    "WHERE a.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    String nmPerawatan = rs.getString("nm_perawatan");
                    String hasil = rs.getString("hasil");

                    // Skip kosong
                    if (nmPerawatan == null || nmPerawatan.trim().isEmpty()) continue;

                    String line = nmPerawatan.trim();
                    if (hasil != null && !hasil.trim().isEmpty()) {
                        line += " : " + hasil.trim();
                    }

                    sb.append(line).append("\n");
                }

                PemeriksaanRad.setText(sb.toString());
                PemeriksaanRad.setCaretPosition(PemeriksaanRad.getDocument().getLength());

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
                    "SELECT "+
                    "IFNULL(c.pemeriksaan,'') as nm_lab, "+
                    "IFNULL(b.nilai,'') as nilai_lab, "+
                    "IFNULL(b.nilai_rujukan,'') as rujukan "+
                    "FROM reg_periksa a "+
                    "LEFT JOIN detail_periksa_lab b ON b.no_rawat = a.no_rawat "+
                    "LEFT JOIN template_laboratorium c ON c.id_template = b.id_template  "+
                    "WHERE a.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    String nmLab = rs.getString("nm_lab");
                    String nilaiLab = rs.getString("nilai_lab");
                    String rujukan = rs.getString("rujukan");

                    // Skip kosong
                    if (nmLab == null || nmLab.trim().isEmpty()) continue;

                    // Format: "Hemoglobin - 14.5 (12-16 g/dL)"
                    String line = nmLab.trim();
                    if (rujukan != null && !rujukan.trim().isEmpty()) {
                        line += " - " + rujukan.trim();
                    }
                    if (nilaiLab != null && !nilaiLab.trim().isEmpty()) {
                        line += " (" + nilaiLab.trim() + ")";
                    }

                    sb.append(line).append("\n");
                }

                PemeriksaanLab.setText(sb.toString());
                PemeriksaanLab.setCaretPosition(PemeriksaanLab.getDocument().getLength());
                
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
        
        //Menampilkan Resep Obat Pulang
        try {
            ps=koneksi.prepareStatement(
                    "SELECT "+
                    "concat(databarang.nama_brng,' ',resep_pulang.jml_barang,' ',kodesatuan.satuan,' ',resep_pulang.dosis) as obat "+
                    "FROM resep_pulang INNER JOIN databarang ON resep_pulang.kode_brng = databarang.kode_brng "+
                    "INNER JOIN kodesatuan ON databarang.kode_sat = kodesatuan.kode_sat "+
                    "WHERE resep_pulang.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    String obat = rs.getString("obat");

                    // Skip kosong
                    if (obat == null || obat.trim().isEmpty()) continue;

                    // Rapihkan: "Paracetamol 500 mg 1x3 tab @2 tab"
                    sb.append(obat.trim()).append("\n");
                }

                ObatPulang.setText(sb.toString());
                ObatPulang.setCaretPosition(ObatPulang.getDocument().getLength());

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
            String rencana = Sequel.cariIsi("select rencana_penunjang from edukasi_pasien where no_rawat=?", TNoRw.getText());
            RencanaPemeriksaanPenunjang.setText(rencana);
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
        
//        // Auto nomor surat - DETECT SOURCE SIMPEL
//        String prefix = (getParent() instanceof javax.swing.JDialog) ? "EPRJ" : "EPRI";  // JDialog=Jalan, Frame=Inap
//
//        Valid.autoNomer3(
//            "select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from edukasi_pasien " +
//            "where tanggal_surat='" + Valid.SetTgl(TanggalSurat.getSelectedItem()+"") + "' ",
//            prefix + TanggalSurat.getSelectedItem().toString().substring(6,10)
//                  + TanggalSurat.getSelectedItem().toString().substring(3,5)
//                  + TanggalSurat.getSelectedItem().toString().substring(0,2),
//            4, NoSurat
//        );
        
        if(akses.getjml2()>=1){
            KodeDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KodeDokter.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", NamaDokter,KodeDokter.getText());
        }            
    }
    
//    private void autoNumberX(String tglPilih) {
//    // Auto nomor surat - DETECT SOURCE SIMPEL
//        String prefix = (getParent() instanceof javax.swing.JDialog) ? "EPRJ" : "EPRI";  // JDialog=Jalan, Frame=Inap
//
//        Valid.autoNomer3(
//            "select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from edukasi_pasien " +
//            "where tanggal_surat='" + Valid.SetTgl(TanggalSurat.getSelectedItem()+"") + "' ",
//            prefix + TanggalSurat.getSelectedItem().toString().substring(6,10)
//                  + TanggalSurat.getSelectedItem().toString().substring(3,5)
//                  + TanggalSurat.getSelectedItem().toString().substring(0,2),
//            4, NoSurat
//        );
//    }
    
    private void autoNoSurat(boolean isRawatJalan) {
        if(TanggalSurat.getSelectedItem() == null) {
            TanggalSurat.setDate(new Date());  // Set hari ini
            return;
        }

        String prefix = isRawatJalan ? "EPRJ" : "EPRI";
        String tglStr = TanggalSurat.getSelectedItem().toString();

        // 🔥 SAFETY: Cek format tanggal DD-MM-YYYY
        if(tglStr.length() < 10) return;

        String nomorFormat = prefix + 
            tglStr.substring(6,10) +  // YYYY
            tglStr.substring(3,5) +   // MM  
            tglStr.substring(0,2);    // DD

        Valid.autoNomer3(
            "select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from edukasi_pasien " +
            "where tanggal_surat='" + Valid.SetTgl(tglStr) + "' ",
            nomorFormat,
            4, 
            NoSurat
        );
    }
            
    // Tambah PUBLIC method ini:
    public void generateNoSuratRanap() {
        autoNoSurat(false);  // EPRI
    }

    public void generateNoSuratJalan() {
        autoNoSurat(true);   // EPRJ
    }

    private void loadSignaturePhotos() {
        String noRawat = TNoRw.getText().trim();
        String topikProfesi = Profesi.getSelectedItem() != null ? Profesi.getSelectedItem().toString().trim() : "";

        String modulKey = getModulKey(topikProfesi);
        if (modulKey.isEmpty()) {
            setEmptyBoth();
            return;
        }

        try {
            String fieldSasaran = "tte_" + modulKey + "_sasaran";
            String fieldEdukator = "tte_" + modulKey + "_edukator";

            ps = koneksi.prepareStatement(
                "SELECT " + fieldSasaran + ", " + fieldEdukator + " " +
                "FROM edukasi_pasien " +
                "WHERE no_rawat = ? AND no_surat IN (" +
                "  SELECT no_surat FROM detail_edukasi_pasien WHERE topik = ?" +
                ") LIMIT 1"
            );
            ps.setString(1, noRawat);
            ps.setString(2, topikProfesi);
            rs = ps.executeQuery();

            if (rs.next()) {
                String sasaran = rs.getString(fieldSasaran);
                String edukator = rs.getString(fieldEdukator);

                final String baseUrl = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB();
                final long timestamp = System.currentTimeMillis();

                // ANTI-CACHE dengan timestamp unik
                final String sasaranUrl = baseUrl + sasaran + "?t=" + timestamp + "1";
                final String edukatorUrl = baseUrl + edukator + "?t=" + timestamp + "2";

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        // CLEAR DULU
                        LoadHTML.setText("");
                        LoadHTML1.setText("");
                        LoadHTML.repaint();
                        LoadHTML1.repaint();

                        // SASARAN - LoadHTML
                        LoadHTML.setText(
                            "<html><body><center>" +
                            "<img src='" + sasaranUrl + "' " +
                            "width='300' height='280' " +
                            "style='border: 2px solid red;' " +
                            "onerror=\"this.src='https://via.placeholder.com/300x280/ff6b6b/ffffff?text=SASARAN+GAGAL'\" />" +
                            "</center></body></html>"
                        );
                        LoadHTML.revalidate();
                        LoadHTML.repaint();
                        LoadHTML.updateUI();

                        // EDUKATOR - LoadHTML1 (border biru)
                        LoadHTML1.setText(
                            "<html><body><center>" +
                            "<img src='" + edukatorUrl + "' " +
                            "width='300' height='280' " +
                            "style='border: 2px solid blue;' " +
                            "onerror=\"this.src='https://via.placeholder.com/300x280/4ecdc4/ffffff?text=EDUKATOR+GAGAL'\" />" +
                            "</center></body></html>"
                        );
                        LoadHTML1.revalidate();
                        LoadHTML1.repaint();
                        LoadHTML1.updateUI();
                    }
                });
            } else {
                setEmptyBoth();
            }
        } catch (Exception e) {
            System.out.println("Signature Error: " + e);
            setEmptyBoth();
        } finally {
            try{ if(rs!=null) rs.close(); }catch(Exception e2){}
            try{ if(ps!=null) ps.close(); }catch(Exception e2){}
        }
    }

    private void setEmptyBoth() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoadHTML.setText(getEmptyHtml());
                LoadHTML1.setText(getEmptyHtml());
                LoadHTML.repaint();
                LoadHTML1.repaint();
            }
        });
    }

    private String getEmptyHtml() {
        return "<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>";
    }

    private String getModulKey(String topikProfesi) {
        if ("HAK PASIEN DAN KELUARGA".equals(topikProfesi)) return "hpk";
        else if ("ROHANIAWAN".equals(topikProfesi)) return "roh";
        else if ("NILAI - NILAI KEPERCAYAAN".equals(topikProfesi)) return "kep";
        else if ("GELANG IDENTITAS/GELANG RESIKO".equals(topikProfesi)) return "gel";
        else if ("DOKTER SPESIALIST/DOKTER UMUM".equals(topikProfesi)) return "dok";
        else if ("MANAJEMEN NYERI".equals(topikProfesi)) return "nye";
        else if ("REHABILITASI MEDIK".equals(topikProfesi)) return "med";
        else if ("MANAJEMEN RISIKO JATUH".equals(topikProfesi)) return "res";
        else if ("CUCI TANGAN".equals(topikProfesi)) return "cuc";
        else if ("NUTRISI".equals(topikProfesi)) return "nut";
        else if ("FARMASI".equals(topikProfesi)) return "far";
        else if ("PENGGUNAAN ALAT KESEHATAN".equals(topikProfesi)) return "kes";
        else if ("EDUKASI KOLABORASI".equals(topikProfesi)) return "kol";
        else if ("PENKES UNTUK DIRUMAH".equals(topikProfesi)) return "pen";
        return "";
    }
    
    private void loadEdukasiByTopik() {
        String noRawat = TNoRw.getText().trim();
        String topikProfesi = Profesi.getSelectedItem() != null ? Profesi.getSelectedItem().toString().trim() : "";

        if (noRawat.isEmpty()) {
            clearAllEdukasiFields();  // Clear jika no_rawat kosong
            return;
        }

        try {
            ps = koneksi.prepareStatement(
                "SELECT b.kode_edukasi, b.topik " +
                "FROM edukasi_pasien a " +
                "LEFT JOIN detail_edukasi_pasien b ON b.no_surat = a.no_surat " +
                "WHERE a.no_rawat = ? AND b.topik = ?"
            );
            ps.setString(1, noRawat);
            ps.setString(2, topikProfesi);
            rs = ps.executeQuery();

            List<String> kodeList = new ArrayList<>();
            while (rs.next()) {
                kodeList.add(rs.getString("kode_edukasi"));
            }
            setEdukasiFields(kodeList);

        } catch (Exception e) {
            System.out.println("Notif Edukasi: " + e);
            clearAllEdukasiFields();
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception e2) {}
            if (ps != null) try { ps.close(); } catch (Exception e2) {}
        }
    }

    private void clearAllEdukasiFields() {
        Edukasi1.setText(""); Edukasi2.setText(""); Edukasi3.setText("");
        Edukasi4.setText(""); Edukasi5.setText(""); Edukasi6.setText("");
        Edukasi7.setText(""); Edukasi8.setText(""); Edukasi9.setText("");
        Edukasi10.setText(""); Edukasi11.setText(""); Edukasi12.setText("");
        Edukasi13.setText("");
    }

    private void setEdukasiFields(List<String> kodeList) {
        String[] fields = {"Edukasi1", "Edukasi2", "Edukasi3", "Edukasi4", "Edukasi5", 
                           "Edukasi6", "Edukasi7", "Edukasi8", "Edukasi9", "Edukasi10",
                           "Edukasi11", "Edukasi12", "Edukasi13"};

        for (String field : fields) {
            try {
                JTextField tf = (JTextField) this.getClass().getDeclaredField(field).get(this);
                tf.setText("");
            } catch (Exception e) {
                System.out.println("Field not found: " + field);
            }
        }

        // Isi sesuai data
        for (int i = 0; i < kodeList.size() && i < 13; i++) {
            try {
                JTextField tf = (JTextField) this.getClass().getDeclaredField(fields[i]).get(this);
                tf.setText(kodeList.get(i));
            } catch (Exception e) {}
        }
    }

    private void loadCheckboxEdukasi() {
        String noRawat = TNoRw.getText().trim();
        String topikProfesi = Profesi.getSelectedItem() != null ? Profesi.getSelectedItem().toString().trim() : "";

        if (noRawat.isEmpty()) {
            clearAllCheckboxes();
            return;
        }

        try {
            ps = koneksi.prepareStatement(
                "SELECT " +
                "a.metodehpk_edukasi, a.mediahpk_edukasi, a.evaluasihpk_edukasi, " +
                "a.metoderoh_edukasi, a.mediaroh_edukasi, a.evaluasiroh_edukasi, " +
                "a.metodekep_edukasi, a.mediakep_edukasi, a.evaluasikep_edukasi, " +
                "a.metodegel_edukasi, a.mediagel_edukasi, a.evaluasigel_edukasi, " +
                "a.metodedok_edukasi, a.mediadok_edukasi, a.evaluasidok_edukasi, " +
                "a.metodenye_edukasi, a.medianye_edukasi, a.evaluasinye_edukasi, " +
                "a.metodemed_edukasi, a.mediamed_edukasi, a.evaluasimed_edukasi, " +
                "a.metoderes_edukasi, a.mediares_edukasi, a.evaluasires_edukasi, " +
                "a.metodecuc_edukasi, a.mediacuc_edukasi, a.evaluasicuc_edukasi, " +
                "a.metodenut_edukasi, a.medianut_edukasi, a.evaluasinut_edukasi, " +
                "a.metodefar_edukasi, a.mediafar_edukasi, a.evaluasifar_edukasi, " +
                "a.metodekes_edukasi, a.mediakes_edukasi, a.evaluasikes_edukasi, " +
                "a.metodekol_edukasi, a.mediakol_edukasi, a.evaluasikol_edukasi, " +
                "a.metodepen_edukasi, a.mediapen_edukasi, a.evaluasipen_edukasi, " +
                "b.kode_edukasi, b.topik " +
                "FROM edukasi_pasien a " +
                "LEFT JOIN detail_edukasi_pasien b ON b.no_surat = a.no_surat " +
                "WHERE a.no_rawat = ? AND b.topik = ? LIMIT 1"
            );
            ps.setString(1, noRawat);
            ps.setString(2, topikProfesi);
            rs = ps.executeQuery();

            if (rs.next()) {
                setCheckboxByModul(rs);
            } else {
                clearAllCheckboxes();
            }

        } catch (Exception e) {
            clearAllCheckboxes();
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception e2) {}
            if (ps != null) try { ps.close(); } catch (Exception e2) {}
        }
    }

    private void setCheckboxByModul(ResultSet rs) throws SQLException {
        Map<String, String> modulMap = new HashMap<>();
        modulMap.put("HAK PASIEN DAN KELUARGA", "hpk");
        modulMap.put("ROHANIAWAN", "roh");
        modulMap.put("NILAI - NILAI KEPERCAYAAN", "kep");
        modulMap.put("GELANG IDENTITAS/GELANG RESIKO", "gel");
        modulMap.put("DOKTER SPESIALIST/DOKTER UMUM", "dok");
        modulMap.put("MANAJEMEN NYERI", "nye");
        modulMap.put("REHABILITASI MEDIK", "med");
        modulMap.put("MANAJEMEN RISIKO JATUH", "res");
        modulMap.put("CUCI TANGAN", "cuc");
        modulMap.put("NUTRISI", "nut");
        modulMap.put("FARMASI", "far");
        modulMap.put("PENGGUNAAN ALAT KESEHATAN", "kes");
        modulMap.put("EDUKASI KOLABORASI", "kol");
        modulMap.put("PENKES UNTUK DIRUMAH", "pen");

        String topik = rs.getString("topik");
        String modulKey = modulMap.getOrDefault(topik, "pen");

        // ARRAY NAMA CHECKBOX SESUAI FORM ANDA
        String[] metodeOptions = {"Ceramah", "Demontrasi", "Diskusi", "Observasi", "Simulasi"};
        String[] mediaOptions = {"Lefleat", "Pamfleat", "LembarBalik", "Audiovisual"};
        String[] evaluasiOptions = {"Mengerti", "ReDemontrasi", "ReEdukasi"};

        // AMBIL DATA DARI RESULTSET (tanpa alias 'a.')
        String metodeStr = rs.getString("metode" + modulKey + "_edukasi"); 
        String mediaStr = rs.getString("media" + modulKey + "_edukasi");
        String evaluasiStr = rs.getString("evaluasi" + modulKey + "_edukasi");

        // SET CHECKBOX BERDASARKAN KONTEN STRING
        setMultiCheckbox(metodeOptions, metodeStr);
        setMultiCheckbox(mediaOptions, mediaStr);  
        setMultiCheckbox(evaluasiOptions, evaluasiStr);
    }

    // Parse multiple values
    private void setMultiCheckbox(String[] options, String dbValue) {
        if (dbValue == null || dbValue.trim().isEmpty()) return;

        // SPLIT & CLEAN SPASI
        String[] dbValues = dbValue.split(",\\s*");

        for (String option : options) {
            try {
                JCheckBox cb = (JCheckBox) this.getClass().getDeclaredField(option).get(this);

                // FUZZY MATCHING: Hilangkan spasi + case insensitive
                String cleanOption = option.replaceAll("\\s+", "");
                boolean isChecked = false;

                for (String dbVal : dbValues) {
                    String cleanDbVal = dbVal.replaceAll("\\s+", "").trim();

                    // MATCH: "ReDemontrasi" == "ReDemontrasi"
                    if (cleanOption.equalsIgnoreCase(cleanDbVal)) {
                        isChecked = true;
                        break;
                    }
                }

                cb.setSelected(isChecked);
                if (isChecked) {
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCheckboxField(String fieldName, String value) {
        try {
            JCheckBox cb = (JCheckBox) this.getClass().getDeclaredField(fieldName).get(this);
            cb.setSelected("1".equals(value) || "true".equalsIgnoreCase(value));
        } catch (Exception e) {
            System.out.println("Checkbox not found: " + fieldName);
        }
    }

    private void clearAllCheckboxes() {
        String[] checkboxes = {
            "Ceramah", "Demontrasi", "Diskusi", "Observasi", "Simulasi",
            "Lefleat", "Pamfleat", "LembarBalik", "Audiovisual",
            "Mengerti", "ReDemontrasi", "ReEdukasi"
        };

        for (String cbName : checkboxes) {
            try {
                JCheckBox cb = (JCheckBox) this.getClass().getDeclaredField(cbName).get(this);
                cb.setSelected(false);
            } catch (Exception e) {}
        }
    }
    
    private void setCheckBoxesReadonly(String modulKey, boolean readonly) {
        String[] checkBoxNames = {
            "Ceramah", "Demontrasi", "Diskusi", "Observasi", "Simulasi",
            "Lefleat", "Pamfleat", "LembarBalik", "Audiovisual",
            "Mengerti", "ReDemontrasi", "ReEdukasi"
        };

        int successCount = 0;
        for (String chkName : checkBoxNames) {
            try {
                JCheckBox chk = (JCheckBox) this.getClass().getDeclaredField(chkName).get(this);
                setSingleCheckBoxReadonly(chk, readonly);
                successCount++;
            } catch (Exception e) {
            }
        }
    }

    private void setSingleCheckBoxReadonly(JCheckBox chk, boolean readonly) {
        if (readonly) {
            chk.setEnabled(false);
            chk.setOpaque(false);
            chk.setForeground(Color.BLACK);
        } else {
            chk.setEnabled(true);
            chk.setOpaque(true);
        }
    }

    private void loadFormData() {
        String noRawat = TNoRw.getText().trim();
        String topikProfesi = Profesi.getSelectedItem() != null ? Profesi.getSelectedItem().toString().trim() : "";

        String modulKey = getModulKey(topikProfesi);
        if (modulKey.isEmpty() || noRawat.isEmpty()) {
            clearFormFields();
            return;
        }

        try {
            String fieldNik = "nik_" + modulKey + "_edukator";
            String fieldNama = "nm_" + modulKey + "_sasaran";
            String fieldTanggal = "tgl_" + modulKey;
            String fieldHambatan = "hambatan_edukasi";  // FIX 1
            String sqlNik = "SELECT " + fieldNik + " FROM edukasi_pasien WHERE no_rawat = ? LIMIT 1";

            ps = koneksi.prepareStatement(
                "SELECT a.*, b.topik, " +
                "COALESCE(a." + fieldNik + ", c.nik) as nik_terpilih, " +  // Prioritas: field > pegawai
                "COALESCE(c.nama, 'Tidak diketahui') as nama_edukator " +
                "FROM edukasi_pasien a " +
                "LEFT JOIN detail_edukasi_pasien b ON b.no_surat = a.no_surat " +
                "LEFT JOIN pegawai c ON c.nik = a." + fieldNik + " " +  // LEFT JOIN ✓
                "WHERE a.no_rawat = ? AND b.topik = ? " +
                "LIMIT 1"
            );
            ps.setString(1, noRawat);
            ps.setString(2, topikProfesi);
            rs = ps.executeQuery();

            if (rs.next()) {
                // KodeDokter
                KodeDokter.setText(rs.getString("nik_terpilih") != null ? rs.getString("nik_terpilih") : "");

                // NamaDokter (JOIN)
                NamaDokter.setText(rs.getString("nama_edukator") != null ? rs.getString("nama_edukator") : "");

                // Hubungan (STATIK)
                Hubungan.setText(rs.getString("hubungan") != null ? rs.getString("hubungan") : "");

                // accep
                accep.setText(rs.getString(fieldNama) != null ? rs.getString(fieldNama) : "");

                // TanggalEdukasi
                String tgl = rs.getString(fieldTanggal);
                if (tgl != null && !tgl.equals("")) {
                    try {
                        Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(tgl);
                        TanggalEdukasi.setDate(tanggal);
                    } catch (Exception e) {
                        TanggalEdukasi.setDate(new Date());
                    }
                } else {
                    TanggalEdukasi.setDate(new Date());
                }

                // PENGKAJIAN
                String hambatan = rs.getString(fieldHambatan);
                Pengkajian.setSelectedItem(hambatan != null && !hambatan.equals("") ? hambatan : "Tidak Ada Hambatan");
            } else {
                clearFormFields();
            }
        } catch (Exception e) {
            clearFormFields();
        } finally {
            try{ if(rs!=null) rs.close(); }catch(Exception e2){}
            try{ if(ps!=null) ps.close(); }catch(Exception e2){}
        }
    }

    private void clearFormFields() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                KodeDokter.setText("");
                NamaDokter.setText("");
                accep.setText("");
                TanggalEdukasi.setDate(new Date());
            }
        });
    }

}