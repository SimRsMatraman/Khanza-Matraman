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
package digital_klaim;

import bridging.ApiBPJS;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author dosen
 */
public final class DlgPilihanCreateDokumen extends javax.swing.JDialog {

    private DefaultTableModel TabMode = null;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private ApiBPJS api = new ApiBPJS();
    private int i = 0;
    private String sql, NoRawat = "", NoRm = "", NoSEP = "", NoReg = "", NamaPoli = "", NamaBayar = "", NamaPasien = "",
            NamaDokter = "", AlamatPasien = "", PenanggungJawab = "", TanggalDaftar = "", JenisPelayanan;
    private String StatusKlaim = "", Dokter = "", tglAwal = "", tglAkhir = "", URL = "";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private PreparedStatement ps, ps2, ps3, ps4, psrekening, ps5, pspermintaan;
    private ResultSet rs, rs2, rs3, rs5, rsrekening, rspermintaan;
    private String kamar, namakamar, datapasien = "", nolab, nmbed;
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
//    private SimpleDateFormat tanggalNow = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgPilihanCreateDokumen(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    this.setLocation(10, 2);
    setSize(656, 250);

    // Header kolom
    Object[] header = {"P", "Pilihan Cetak Dokumen"};

    TabMode = new DefaultTableModel(null, header) {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return colIndex == 0; // hanya kolom checkbox
        }

        Class[] types = new Class[]{
            Boolean.class, Object.class
        };

        @Override
        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }
    };

    tbData.setModel(TabMode);

    // contoh data
    TabMode.addRow(new Object[]{false, "Dokumen 1"});
    TabMode.addRow(new Object[]{false, "Dokumen 2"});
    TabMode.addRow(new Object[]{false, "Dokumen 3"});

    tbData.getColumnModel().getColumn(0).setPreferredWidth(40);
    tbData.getColumnModel().getColumn(1).setPreferredWidth(300);

    // Tambahkan checkbox all di header
    addHeaderCheckBox();
}

    private void addHeaderCheckBox() {
    JTableHeader header = tbData.getTableHeader();
    TableColumn tc = tbData.getColumnModel().getColumn(0);

    JCheckBox checkAll = new JCheckBox();
    checkAll.setHorizontalAlignment(SwingConstants.CENTER);
    checkAll.setOpaque(false);

    // Renderer header
    tc.setHeaderRenderer((tbl, value, isSelected, hasFocus, row, col) -> checkAll);

    // Listener klik header
    header.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int col = tbData.columnAtPoint(e.getPoint());
            if (col == 0) {
                boolean newState = !checkAll.isSelected();
                checkAll.setSelected(newState);

                // Set semua baris
                for (int i = 0; i < tbData.getRowCount(); i++) {
                    tbData.setValueAt(newState, i, 0);
                }

                tbData.getTableHeader().repaint();
            }
        }
    });
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbData = new widget.Table();
        panelisi3 = new widget.panelisi();
        BtnCreate = new widget.Button();
        BtnKeluarCreateDokumen = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pilihan Create Dokumen ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbData.setAutoCreateRowSorter(true);
        tbData.setName("tbData"); // NOI18N
        Scroll.setViewportView(tbData);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(44, 54));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        BtnCreate.setBackground(new java.awt.Color(0, 51, 102));
        BtnCreate.setForeground(new java.awt.Color(255, 255, 255));
        BtnCreate.setMnemonic('K');
        BtnCreate.setText("Create");
        BtnCreate.setToolTipText("Alt+K");
        BtnCreate.setName("BtnCreate"); // NOI18N
        BtnCreate.setOpaque(true);
        BtnCreate.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCreateActionPerformed(evt);
            }
        });
        panelisi3.add(BtnCreate);

        BtnKeluarCreateDokumen.setBackground(new java.awt.Color(255, 51, 0));
        BtnKeluarCreateDokumen.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluarCreateDokumen.setMnemonic('K');
        BtnKeluarCreateDokumen.setText("Keluar");
        BtnKeluarCreateDokumen.setToolTipText("Alt+K");
        BtnKeluarCreateDokumen.setName("BtnKeluarCreateDokumen"); // NOI18N
        BtnKeluarCreateDokumen.setOpaque(true);
        BtnKeluarCreateDokumen.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarCreateDokumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarCreateDokumenActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluarCreateDokumen);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void BtnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCreateActionPerformed

        for (i = 0; i < tbData.getRowCount(); i++) {
            if (tbData.getValueAt(i, 0).toString().equals("true")) {
                if (tbData.getValueAt(i, 1).toString().equals("S E P")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    String FileName, jnsSep;
                    FileName = "sep_" + NoSEP + ".pdf";
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("prb", Sequel.cariIsi("select prb from bpjs_prb where no_sep='" + NoSEP + "' "));
                    param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
                    param.put("parameter", NoSEP);
                    jnsSep = Sequel.cariIsi("select jnspelayanan from bridging_sep where no_sep='" + NoSEP + "' ");
                    if (jnsSep.equals("2")) {
                        Valid.MyReportPDFWithName("rptBridgingSEP5.jasper", "report", "tempfile", FileName, "::[ E-SEP ]::", param);
//                        uploadPdf(FileName, "sep");
                        uploadPdf(NoRawat, "sep", FileName, "sep");
//                        saveFileNameBerkas(NoRawat, "sep", "sep/" + FileName);
                    } else {
                        Valid.MyReportPDFWithName("rptBridgingSEP6.jasper", "report", "tempfile", FileName, "::[ E-SEP ]::", param);
//                        uploadPdf(FileName, "sep");
                        uploadPdf(NoRawat, "sep", FileName, "sep");
//                        saveFileNameBerkas(NoRawat, "sep", "sep/" + FileName);
                    }
                    deleteFile();
                    this.setCursor(Cursor.getDefaultCursor());
                } else if (tbData.getValueAt(i, 1).toString().equals("Resume IGD")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    String FileName, kodeDokter;
                    kodeDokter = Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat='" + NoRawat + "'");
                    FileName = "resume_" + NoRawat.replaceAll("/", "") + ".pdf";
                    Map<String, Object> param = new HashMap<>();
//                    param.put("namars", akses.getnamars());
//                    param.put("alamatrs", akses.getalamatrs());
//                    param.put("kotars", akses.getkabupatenrs());
//                    param.put("propinsirs", akses.getpropinsirs());
//                    param.put("kontakrs", akses.getkontakrs());
//                    param.put("emailrs", akses.getemailrs());
//                    param.put("logo", Sequel.cariGambar("select logo from setting"));
//                    param.put("norawat", NoRawat);
//                    param.put("image_ttd", Sequel.cariIsi("select path_ttd from tm_image_ttd_petugas where kd_petugas='" + kodeDokter + "'"));
//                    param.put("finger", Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", kodeDokter));
//                    param.put("tanggalkeluar", Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ", NoRawat));
//                    if (StatusKlaim.equals("Ralan")) {
//                        param.put("ruang", Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?", NoRawat));
//                        Valid.MyReportPDFWithName("rptLaporanResumeRajalTtd.jasper", "report", "tempfile", FileName, "::[ E-Resume ]::", param);
//                    } else if (StatusKlaim.equals("Ranap")) {
//                        param.put("ruang", Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ", NoRawat));
//                        param.put("tanggalkeluar", Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ", NoRawat));
//                        param.put("harirawat", Sequel.cariIsi("select sum(lama) from kamar_inap where no_rawat=?", NoRawat) + " Hari");
//                        Valid.MyReportPDFWithName("rptLaporanResumeRanapTtd.jasper", "report", "tempfile", FileName, "::[ E-Resume ]::", param);
//                    }
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    param.put("norawat",NoRawat);
//                    param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())); 
                    if(StatusKlaim.equals("Ralan")){
                        param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='" + NoRawat + "'"));
                        param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat='" + NoRawat + "'"));
                        Valid.MyReportPDFWithName("rptLaporanResume.jasper", "report", "tempfile", FileName, "::[ Laporan Resume IGD ]::", param);
                    }else{
                        param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat='" + NoRawat + "' order by tgl_masuk desc limit 1 "));
                        param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat='" + NoRawat + "' order by tgl_keluar desc limit 1 "));
                        Valid.MyReportPDFWithName("rptLaporanResume.jasper", "report", "tempfile", FileName, "::[ Laporan Resume IGD ]::", param);
                    }
//                    Valid.MyReport("rptLaporanResume.jasper","report","::[ Laporan Resume Pasien ]::",param);
//                    uploadPdf(FileName, "resume");
                    uploadPdf(NoRawat, "resume", FileName, "resume");
//                    saveFileNameBerkas(NoRawat, "resume", "resume/" + FileName);
                    deleteFile();
                    this.setCursor(Cursor.getDefaultCursor());
                } else if (tbData.getValueAt(i, 1).toString().equals("Resume Rawat Jalan")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    String FileName, kodeDokter;
                    kodeDokter = Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat='" + NoRawat + "'");
                    FileName = "resumeralan_" + NoRawat.replaceAll("/", "") + ".pdf";
                    Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    param.put("norawat",NoRawat);
                    param.put("rujuk",Sequel.cariIsi("select rujuk_ke from rujuk where no_rawat=?='" + NoRawat + "' "));
//                    param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())); 
                    if(StatusKlaim.equals("Ralan")){
                        param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='" + NoRawat + "'"));
                        param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat='" + NoRawat + "'"));
                        Valid.MyReportPDFWithName("rptLaporanResumeRajal.jasper", "report", "tempfile", FileName, "::[ Laporan Resume Rawat Jalan ]::", param);
                    }else{
                        param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat='" + NoRawat + "' order by tgl_masuk desc limit 1 "));
                        param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat='" + NoRawat + "' order by tgl_keluar desc limit 1 "));
                        Valid.MyReportPDFWithName("rptLaporanResumeRajal.jasper", "report", "tempfile", FileName, "::[ Laporan Resume Rawat Jalan ]::", param);
                    }
//                    Valid.MyReport("rptInacbgRajal.jasper","report","::[ Laporan INACBG Rawat Jalan ]::",param);
//                    uploadPdf(FileName, "inacbg");
                    uploadPdf(NoRawat, "resumeralan", FileName, "resumeralan");
//                    saveFileNameBerkas(NoRawat, "inacbg", "inacbg/" + FileName);
                    deleteFile();
                    this.setCursor(Cursor.getDefaultCursor());
                } else if (tbData.getValueAt(i, 1).toString().equals("Individual Eklaim")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    try {
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        requestEntity = new HttpEntity(headers);
                        URL = "http://" + koneksiDB.HOSTHYBRIDWEB() + "/" + koneksiDB.HYBRIDWEB() + "/inacbg_idrg_dev/index.php?act=cekSep&noSep=" + NoSEP;
                        requestEntity = new HttpEntity(headers);
                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                        if (root.path("metadata").path("code").asText().equals("200")) {
                            saveFileNameBerkas(NoRawat, "data_individual", "data_individual/individual_" + NoSEP + ".pdf");
//                            JOptionPane.showMessageDialog(null, root.path("metadata").path("message").asText());
                        }
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : " + ex);
                        if (ex.toString().contains("UnknownHostException")) {
                            JOptionPane.showMessageDialog(rootPane, "Koneksi ke server E-klaim terputus...!");
                        }
                    }
                    this.setCursor(Cursor.getDefaultCursor());
                } else if (tbData.getValueAt(i, 1).toString().equals("Billing")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                    if (StatusKlaim.equals("Ralan")) {
                        createNota(NoRawat);
//                    }
                    this.setCursor(Cursor.getDefaultCursor());
                } else if (tbData.getValueAt(i, 1).toString().equals("Laboratorium")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    createLab(NoRawat);

                    this.setCursor(Cursor.getDefaultCursor());
                } else if (tbData.getValueAt(i, 1).toString().equals("Radiologi")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    createRad(NoRawat);
                    this.setCursor(Cursor.getDefaultCursor());
                } else if (tbData.getValueAt(i, 1).toString().equals("Hasil USG")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    String FileName, namaDokter, KodeDOkter, tglLahir, jk;
//                    KodeDOkter = Sequel.cariIsi("select kd_dokter from tt_hasil_usg  where no_rawat='" + NoRawat + "' ");
//                    namaDokter = Sequel.cariIsi("select nm_dokter from  dokter where kd_dokter='" + KodeDOkter + "' ");
//                    tglLahir = Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis='" + NoRm + "' ");
//                    jk = Sequel.cariIsi("select jk from pasien where no_rkm_medis='" + NoRm + "' ");

                    FileName = "usg_" + NoRawat.replaceAll("/", "") + ".pdf";
                    Map<String, Object> param = new HashMap<>();
//                    param.put("namars", akses.getnamars());
//                    param.put("alamatrs", akses.getalamatrs());
//                    param.put("kotars", akses.getkabupatenrs());
//                    param.put("propinsirs", akses.getpropinsirs());
//                    param.put("kontakrs", akses.getkontakrs());
//                    param.put("emailrs", akses.getemailrs());
//                    param.put("logo", Sequel.cariGambar("select logo from setting"));
//                    param.put("namauser", Sequel.cariIsi("select input from aktifitas_userregistrasi   WHERE no_rawat='" + NoRawat + "'"));
//                    param.put("petugas", namaDokter);
//                    param.put("norm", NoRm);
//                    param.put("namapasien", NamaPasien);
//                    param.put("tgllhr", tglLahir.split("-")[2] + "-" + tglLahir.split("-")[1] + "-" + tglLahir.split("-")[0]);
//                    param.put("jnskelamin", (jk.equals("L") ? "Laki-laki" : "Perempuan"));
//                    param.put("image_ttd", Sequel.cariIsi("select path_ttd from tm_image_ttd_petugas where kd_petugas='" + KodeDOkter + "'"));
                    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());          
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String gambarusg=Sequel.cariIsi("select photo from hasil_pemeriksaan_usg_gambar where no_rawat='"+NoRawat+ "'");
        //            param.put("gambarusg",Sequel.cariGambar("select setting.logo from setting"));
                    param.put("tampilgambarusg","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanusg/"+gambarusg+" ");

//                    Valid.MyReportPDFWithNameQry("rptHasilUsgTtd.jasper", "report", "tempfile", FileName, "::[ Cetak Hasil USG ]::",
//                            "select reg_periksa.no_reg,reg_periksa.no_reg as no_antrian,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp,pasien.tgl_lahir, "
//                            + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(year(from_days(datediff(now(), pasien.tgl_lahir))),' Th ',month(from_days(datediff(now(),pasien.tgl_lahir))),' Bl ',day(from_days(datediff(now(),pasien.tgl_lahir))),' Hr')as umur,poliklinik.nm_poli,"
//                            + "pasien.alamat, kelurahan.nm_kel, kecamatan.nm_kec, kabupaten.nm_kab, propinsi.nm_prop, "
//                            + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,tt_hasil_usg.* "
//                            + "from reg_periksa inner join dokter inner join pasien inner join kelurahan inner join kecamatan inner join kabupaten inner join propinsi inner join poliklinik inner join penjab "
//                            + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
//                            + "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec= kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and pasien.kd_prop=propinsi.kd_prop "
//                            + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli join tt_hasil_usg ON reg_periksa.no_rawat=tt_hasil_usg.no_rawat where reg_periksa.no_rawat='" + NoRawat + "' ", param);
                    
                    Valid.MyReportPDFWithNameQry("rptCetakHasilPemeriksaanUSG.jasper","report","tempfile", FileName,"::[ Formulir Hasil Pemeriksaan USG ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_usg.tanggal,"+
                    "hasil_pemeriksaan_usg.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_usg.diagnosa_klinis,hasil_pemeriksaan_usg.kiriman_dari,"+
                    "hasil_pemeriksaan_usg.hta,hasil_pemeriksaan_usg.kantong_gestasi,hasil_pemeriksaan_usg.ukuran_bokongkepala,"+
                    "hasil_pemeriksaan_usg.jenis_prestasi,hasil_pemeriksaan_usg.diameter_biparietal,hasil_pemeriksaan_usg.panjang_femur,"+
                    "hasil_pemeriksaan_usg.lingkar_abdomen,hasil_pemeriksaan_usg.tafsiran_berat_janin,hasil_pemeriksaan_usg.usia_kehamilan,"+
                    "hasil_pemeriksaan_usg.plasenta_berimplatansi,hasil_pemeriksaan_usg.derajat_maturitas,hasil_pemeriksaan_usg.jumlah_air_ketuban,"+
                    "hasil_pemeriksaan_usg.indek_cairan_ketuban,hasil_pemeriksaan_usg.kelainan_kongenital,hasil_pemeriksaan_usg.peluang_sex,"+
                    "hasil_pemeriksaan_usg.kesimpulan from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join hasil_pemeriksaan_usg on reg_periksa.no_rawat=hasil_pemeriksaan_usg.no_rawat "+
                    "inner join dokter on hasil_pemeriksaan_usg.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_usg.no_rawat='"+NoRawat+"'",param);

//                    uploadPdf(FileName, "usg");
                    uploadPdf(NoRawat, "usg", FileName, "usg");
//                    saveFileNameBerkas(NoRawat, "usg", "usg/" + FileName);

                    deleteFile();
                    this.setCursor(Cursor.getDefaultCursor());
                } else if (tbData.getValueAt(i, 1).toString().equals("Hasil ENDOSKOPI THT")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    this.setCursor(Cursor.getDefaultCursor());
//                }else if (tbData.getValueAt(i, 1).toString().equals("Formulir Klaim Rehab Medik")) {
//                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                     
//                    Map<String, Object> param = new HashMap<>();
//                    String kdDokter,noRm,nmPasien,tglLhr,jk,FileName;
//                    FileName = "klaimkfr_" + NoRawat.replaceAll("/", "") + ".pdf";
//                    kdDokter=Sequel.cariIsi("select nik from pemeriksaan_ralan_rehab where no_rawat='" + NoRawat + "'");
//                    noRm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='" + NoRawat + "'");
//                    nmPasien=Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='" + noRm + "'");
//                    tglLhr=Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis='" + noRm + "'");
//                    jk=Sequel.cariIsi("select jk from pasien where no_rkm_medis='" + noRm + "'");
//                    param.put("namars", akses.getnamars());
//                    param.put("alamatrs", akses.getalamatrs());
//                    param.put("kotars", akses.getkabupatenrs());
//                    param.put("propinsirs", akses.getpropinsirs());
//                    param.put("kontakrs", akses.getkontakrs());
//                    param.put("emailrs", akses.getemailrs());
//                    param.put("logo", Sequel.cariGambar("select logo from setting"));
//                    param.put("namauser", "");
//                    param.put("petugas", kdDokter);
//                    param.put("norm", noRm);
//                    param.put("namapasien",nmPasien);
//                    param.put("tgllhr", tglLhr.split("-")[2] + "-" + tglLhr.split("-")[1] + "-" + tglLhr.split("-")[0]);
//                    param.put("jnskelamin", jk);
//                    param.put("image_ttd", Sequel.cariIsi("select path_ttd from tm_image_ttd_petugas where kd_petugas='" + kdDokter + "'"));
//                    Valid.MyReportPDFWithNameQry("rptLembarKalimRehabMedikTtd.jasper", "report", "tempfile", FileName, "::[ Cetak Klaim KFR ]::",
//                            "SELECT tt_klaim_kfr.*,pasien.jk,pasien.nm_pasien,pasien.tgl_lahir,concat(pasien.alamat,' ',nm_kel,' ',nm_kec,' ',nm_kab,' ',nm_prop) AS alamat,pasien.no_rkm_medis,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) AS umur,dokterLayanan.nm_dokter AS dokterPemberiLayanan,dokterPerujuk.nm_dokter AS dokterPerujukLayanan FROM tt_klaim_kfr LEFT JOIN reg_periksa ON tt_klaim_kfr.no_rawat=reg_periksa.no_rawat LEFT JOIN dokter AS dokterLayanan ON tt_klaim_kfr.dokter_pemberi_layanan=dokterLayanan.kd_dokter LEFT JOIN dokter AS dokterPerujuk ON tt_klaim_kfr.dokter_perujuk=dokterPerujuk.kd_dokter LEFT JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis LEFT JOIN kelurahan ON pasien.kd_kel=kelurahan.kd_kel LEFT JOIN kecamatan ON pasien.kd_kec=kecamatan.kd_kec LEFT JOIN kabupaten ON pasien.kd_kab=kabupaten.kd_kab LEFT JOIN propinsi ON pasien.kd_prop=propinsi.kd_prop where tt_klaim_kfr.no_rawat='" + NoRawat + "' ", param);
////                    uploadPdf(FileName, "kfr");
//                    uploadPdf(NoRawat, "kfr", FileName, "kfr");
////                    saveFileNameBerkas(NoRawat, "kfr", "kfr/" + FileName);
//
//                    deleteFile();
//                    this.setCursor(Cursor.getDefaultCursor());
                }else if (tbData.getValueAt(i, 1).toString().equals("Formulir Klaim Rehab Medik")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    try {
                        Map<String, Object> param = new HashMap<>();
                        String kdDokter, noRm, nmPasien, tglLhr, jk, FileName;

                        FileName = "file_rehab_" + NoRawat.replaceAll("/", "") + ".pdf";

                        kdDokter = Sequel.cariIsi(
                                "select nik from pemeriksaan_ralan_rehab where no_rawat='" + NoRawat + "'"
                        );
                        noRm = Sequel.cariIsi(
                                "select no_rkm_medis from reg_periksa where no_rawat='" + NoRawat + "'"
                        );
                        nmPasien = Sequel.cariIsi(
                                "select nm_pasien from pasien where no_rkm_medis='" + noRm + "'"
                        );
                        tglLhr = Sequel.cariIsi(
                                "select tgl_lahir from pasien where no_rkm_medis='" + noRm + "'"
                        );
                        jk = Sequel.cariIsi(
                                "select jk from pasien where no_rkm_medis='" + noRm + "'"
                        );

                        // antisipasi kalau tanggal lahir kosong
                        String tglLahirFormat = "";
                        if (tglLhr != null && !tglLhr.equals("")) {
                            String[] tgl = tglLhr.split("-");
                            if (tgl.length == 3) {
                                tglLahirFormat = tgl[2] + "-" + tgl[1] + "-" + tgl[0];
                            }
                        }

                        param.put("namars", akses.getnamars());
                        param.put("alamatrs", akses.getalamatrs());
                        param.put("kotars", akses.getkabupatenrs());
                        param.put("propinsirs", akses.getpropinsirs());
                        param.put("kontakrs", akses.getkontakrs());
                        param.put("emailrs", akses.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        param.put("namauser", "");
                        param.put("petugas", kdDokter);
                        param.put("norm", noRm);
                        param.put("namapasien", nmPasien);
                        param.put("tgllhr", tglLahirFormat);
                        param.put("jnskelamin", jk);
                        param.put("norawat", NoRawat);
                        // param.put("image_ttd", ...);

                        Valid.MyReportPDFWithName(
                                "rptLembarKalimRehabMedikTtd.jasper",
                                "report",
                                "tempfile",
                                FileName,
                                "::[ Cetak Klaim Pasien Rehab Ralan ]::",
                                param
                        );

                        // upload ke server
                        uploadPdf(NoRawat, "rehab", FileName, "rehab");
                        

                        deleteFile();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Gagal cetak File Rehab : " + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                    
                } else if (tbData.getValueAt(i, 1).toString().equals("Import Berkas Digital Keperawatan")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    try {
                        importBerkasDigitalKeperawatan(NoRawat);
                    } catch (IOException ex) {
                        Logger.getLogger(DlgPilihanCreateDokumen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.setCursor(Cursor.getDefaultCursor());
                } 
//                else if (tbData.getValueAt(i, 1).toString().equals("Triase IGD Zona Merah")) {
//                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                    String FileName, kodeDokter;
//                    kodeDokter = Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat='" + NoRawat + "'");
//                    FileName = "triase_" + NoRawat.replaceAll("/", "") + ".pdf";
//                    Map<String, Object> param = new HashMap<>();    
//                    param.put("namars",akses.getnamars());
//                    param.put("alamatrs",akses.getalamatrs());
//                    param.put("kotars",akses.getkabupatenrs());
//                    param.put("propinsirs",akses.getpropinsirs());
//                    param.put("kontakrs",akses.getkontakrs());
//                    param.put("emailrs",akses.getemailrs());   
//                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
//                    param.put("norawat",NoRawat);
////                    param.put("rujuk",Sequel.cariIsi("select rujuk_ke from rujuk where no_rawat=?='" + NoRawat + "' "));
////                    param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())); 
////                    if(StatusKlaim.equals("Ralan")){
//                        param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='" + NoRawat + "'"));
//                        param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat='" + NoRawat + "'"));
//                        Valid.MyReportPDFWithName("rptLembarTriaseMerah.jasper", "report", "tempfile", FileName, "::[ Laporan Triase Zona Merah ]::", param);
////                    }else{
////                        param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat='" + NoRawat + "' order by tgl_masuk desc limit 1 "));
////                        param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat='" + NoRawat + "' order by tgl_keluar desc limit 1 "));
////                        Valid.MyReportPDFWithName("rptInacbgRajal.jasper", "report", "tempfile", FileName, "::[ Laporan INACBG Rawat Jalan ]::", param);
////                    }
//                    
////                    Valid.MyReport("rptInacbgRajal.jasper","report","::[ Laporan INACBG Rawat Jalan ]::",param);
////                    uploadPdf(FileName, "inacbg");
//                    uploadPdf(NoRawat, "triase", FileName, "triase");
////                    saveFileNameBerkas(NoRawat, "inacbg", "inacbg/" + FileName);
//                    deleteFile();
//                    this.setCursor(Cursor.getDefaultCursor());
//                } 
                else if (tbData.getValueAt(i, 1).toString().equals("Triase IGD Zona Merah")) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    String FileName = "triase_" + NoRawat.replaceAll("/", "") + ".pdf";

                    try {
                        Map<String, Object> param = new HashMap<>();
                        param.put("namars", akses.getnamars());
                        param.put("alamatrs", akses.getalamatrs());
                        param.put("kotars", akses.getkabupatenrs());
                        param.put("propinsirs", akses.getpropinsirs());
                        param.put("kontakrs", akses.getkontakrs());
                        param.put("emailrs", akses.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));

                        // ============================
                        // AMBIL DATA TRIASE DARI DATABASE
                        // ============================
                        ps = koneksi.prepareStatement(
                            "SELECT dp.keluhan_utama, dp.kebutuhan_khusus, dp.catatan, dp.plan, " +
                            "dp.tanggaltriase, dp.kd_dokter, d.nm_dokter, di.tekanan_darah, di.nadi, " +
                            "di.pernapasan, di.suhu, di.saturasi_o2, di.nyeri, di.no_rawat, p.no_rkm_medis, " +
                            "p.nm_pasien, p.jk, p.tgl_lahir, di.tgl_kunjungan, di.cara_masuk, mk.macam_kasus " +
                            "FROM data_triase_igdprimer dp " +
                            "INNER JOIN reg_periksa rp ON rp.no_rawat = dp.no_rawat " +
                            "INNER JOIN data_triase_igd di ON dp.no_rawat = di.no_rawat " +
                            "INNER JOIN pasien p ON p.no_rkm_medis = rp.no_rkm_medis " +
                            "INNER JOIN dokter d ON d.kd_dokter = dp.kd_dokter " +
                            "INNER JOIN master_triase_macam_kasus mk ON mk.kode_kasus = di.kode_kasus " +
                            "WHERE dp.no_rawat = ?"
                        );

                        ps.setString(1, NoRawat);
                        rs = ps.executeQuery();

                        if (rs.next()) {

                            param.put("norawat", rs.getString("no_rawat"));
                            param.put("norm", rs.getString("no_rkm_medis"));
                            param.put("namapasien", rs.getString("nm_pasien"));
                            param.put("jk", rs.getString("jk").equals("L") ? "Laki-Laki" : "Perempuan");
                            param.put("tanggallahir", rs.getDate("tgl_lahir"));

                            param.put("tanggalkunjungan", rs.getDate("tgl_kunjungan"));
                            param.put("jamkunjungan", rs.getString("tgl_kunjungan").substring(11, 19));

                            param.put("caradatang", rs.getString("cara_masuk"));
                            param.put("macamkasus", rs.getString("macam_kasus"));
                            param.put("keluhanutama", rs.getString("keluhan_utama"));
                            param.put("kebutuhankhusus", rs.getString("kebutuhan_khusus"));
                            param.put("plan", rs.getString("plan"));
                            param.put("tanggaltriase", rs.getDate("tanggaltriase"));
                            param.put("jamtriase", rs.getString("tanggaltriase").substring(11, 19));
                            param.put("dokter", rs.getString("nm_dokter"));

                            // tanda vital
                            param.put("tandavital",
                                "Suhu (C): " + rs.getString("suhu") +
                                ", Nyeri: " + rs.getString("nyeri") +
                                ", Tensi: " + rs.getString("tekanan_darah") +
                                ", Nadi: " + rs.getString("nadi") +
                                ", Saturasi O2(%): " + rs.getString("saturasi_o2") +
                                ", Respirasi: " + rs.getString("pernapasan")
                            );

                            // ambil DATA SKALA 2 → masuk ke temporary
                            isiTemporaryTriase(NoRawat);
                        }

                        // langsung buat PDF tanpa pilihan
                        Valid.MyReportPDFWithName(
                            "rptLembarTriaseSkala2.jasper",
                            "report",
                            "tempfile",
                            FileName,
                            "::[ Laporan Triase Skala 2 ]::",
                            param
                        );

                        uploadPdf(NoRawat, "triase", FileName, "triase");
                        deleteFile();

                    } catch (Exception e) {
                        System.out.println("Notif : " + e);
                    }

                    this.setCursor(Cursor.getDefaultCursor());
                }             

            }

        }

        dispose();
    }//GEN-LAST:event_BtnCreateActionPerformed

    private void isiTemporaryTriase(String noRawat) {
        try {
            PreparedStatement ps2 = koneksi.prepareStatement(
                "SELECT mp.nama_pemeriksaan, mp.kode_pemeriksaan " +
                "FROM master_triase_pemeriksaan mp " +
                "INNER JOIN master_triase_skala2 ms ON mp.kode_pemeriksaan = ms.kode_pemeriksaan " +
                "INNER JOIN data_triase_igddetail_skala2 ds ON ms.kode_skala2 = ds.kode_skala2 " +
                "WHERE ds.no_rawat = ? GROUP BY mp.kode_pemeriksaan ORDER BY mp.kode_pemeriksaan"
            );

            ps2.setString(1, noRawat);
            ResultSet rs2 = ps2.executeQuery();

            // Kosongkan tabel temporary
            Sequel.queryu("TRUNCATE TABLE temporary");

            int colCount = getTemporaryColumnCount(); // ← jumlah kolom real di DB

            while (rs2.next()) {

                String pemeriksaan = rs2.getString("nama_pemeriksaan");
                String kode = rs2.getString("kode_pemeriksaan");

                PreparedStatement ps3 = koneksi.prepareStatement(
                    "SELECT ms.pengkajian_skala2 " +
                    "FROM master_triase_skala2 ms " +
                    "INNER JOIN data_triase_igddetail_skala2 ds ON ms.kode_skala2 = ds.kode_skala2 " +
                    "WHERE ms.kode_pemeriksaan = ? AND ds.no_rawat = ? " +
                    "ORDER BY ms.kode_skala2"
                );

                ps3.setString(1, kode);
                ps3.setString(2, noRawat);

                ResultSet rs3 = ps3.executeQuery();
                String detail = "";

                while (rs3.next()) {
                    detail += rs3.getString("pengkajian_skala2") + ", ";
                }

                if (detail.endsWith(", ")) detail = detail.substring(0, detail.length() - 2);

                // ------------------------------
                // 🔥 AUTO generate kolom sesuai jumlah di DB
                // ------------------------------
                StringBuilder values = new StringBuilder();

                for (int c = 0; c < colCount; c++) {
                    if (c == 0) values.append("'0',");
                    else if (c == 1) values.append("'").append(pemeriksaan).append("',");
                    else if (c == 2) values.append("'").append(detail).append("',");
                    else values.append("'',");
                }

                // hapus koma terakhir
                String finalValues = values.substring(0, values.length() - 1);

                Sequel.menyimpan2("temporary", finalValues, "Triase Skala 2");

                rs3.close();
                ps3.close();
            }

            rs2.close();
            ps2.close();

        } catch (Exception e) {
            System.out.println("Notif skala2 : " + e);
        }
    }
    
    private int getTemporaryColumnCount() {
        try {
            PreparedStatement ps = koneksi.prepareStatement("SHOW COLUMNS FROM temporary");
            ResultSet rs = ps.executeQuery();

            int count = 0;
            while (rs.next()) count++;

            rs.close();
            ps.close();
            return count;

        } catch (Exception e) {
            System.out.println("Notif column count : " + e);
            return 0;
        }
    }


    private void BtnKeluarCreateDokumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarCreateDokumenActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarCreateDokumenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPilihanCreateDokumen dialog = new DlgPilihanCreateDokumen(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCreate;
    private widget.Button BtnKeluarCreateDokumen;
    private widget.ScrollPane Scroll;
    private widget.InternalFrame internalFrame1;
    private widget.panelisi panelisi3;
    private widget.Table tbData;
    // End of variables declaration//GEN-END:variables

//    public void tampil() {
//        try {
//            Valid.tabelKosong(TabMode);
//            if(Sequel.cariInteger("select count(no_rawat) from bridging_sep where no_rawat='"+NoRawat+"' ")>0){
//            TabMode.addRow(new Object[]{false, "S E P"});//20,0
//            }else{}
//            if(Sequel.cariInteger("select count(no_rawat) from billing where no_rawat='"+NoRawat+"' ")>0){
//            TabMode.addRow(new Object[]{false, "Billing"});//20,0
//            }else{}
//            if(Sequel.cariInteger("select count(no_rawat) from inacbg_rajal where no_rawat='"+NoRawat+"' ")>0){
//            TabMode.addRow(new Object[]{false, "INACBG"});//20,0
//            }else{}
//            if(Sequel.cariInteger("select count(no_rawat) from resume_pasien where no_rawat='"+NoRawat+"' ")>0){
//            TabMode.addRow(new Object[]{false, "Resume"});//20,0
//            }else{}
//            if(Sequel.cariInteger("select count(no_rawat) from detail_periksa_lab where no_rawat='"+NoRawat+"' ")>0){
//            TabMode.addRow(new Object[]{false, "Laboratorium"});//20,0
//            }else{}
//            if(Sequel.cariInteger("select count(no_rawat) from hasil_radiologi where no_rawat='"+NoRawat+"' ")>0){
//            TabMode.addRow(new Object[]{false, "Radiologi"});//20,0
//            }else{}
//            if(Sequel.cariInteger("select count(no_rawat) from hasil_pemeriksaan_usg where no_rawat='"+NoRawat+"' ")>0){
//            TabMode.addRow(new Object[]{false, "Hasil USG"});//20,0
//            }else{}
////            TabMode.addRow(new Object[]{false, "Hasil ENDOSKOPI THT"});//20,0
//            if(Sequel.cariInteger("select count(no_rawat) from bridging_sep where no_rawat='"+NoRawat+"' ")>0){
//            TabMode.addRow(new Object[]{false, "Individual Eklaim"});//20,0
//            }else{}
//            if(Sequel.cariInteger("select count(no_rawat) from berkas_digital_perawatan where no_rawat='"+NoRawat+"' ")>0){
//            TabMode.addRow(new Object[]{false, "Import Berkas Digital Keperawatan"});//20,0
//            }else{}
////            TabMode.addRow(new Object[]{false, "Formulir Klaim Rehab Medik"});//20,0
//        } catch (Exception e) {
//            System.out.println("Notifikasi : " + e);
//        }
//
//    }
    
    public void tampil(String NoRawat) {
        try {
            Valid.tabelKosong(TabMode);
//            int sep = Sequel.cariInteger("select count(no_rawat) from bridging_sep where no_rawat='"+NoRawat+"' ");
//            System.out.println("Notifikasi No Rawat : " + NoRawat);
//            System.out.println("Notifikasi SEP : " + sep);
//            if(sep < 0){
            TabMode.addRow(new Object[]{false, "S E P"});//20,0
//            }else{}
            int billing = Sequel.cariInteger("select count(no_rawat) from billing where no_rawat='"+NoRawat+"' ");
            if(billing > 0){
            TabMode.addRow(new Object[]{false, "Billing"});//20,0
            }else{}
            int inacbg = Sequel.cariInteger("select count(no_rawat) from resume_pasien_rajal where no_rawat='"+NoRawat+"' ");
            if(inacbg > 0){
            TabMode.addRow(new Object[]{false, "Resume Rawat Jalan"});//20,0
            }else{}
            int resume = Sequel.cariInteger("select count(no_rawat) from resume_pasien where no_rawat='"+NoRawat+"' ");
            if(resume > 0){
            TabMode.addRow(new Object[]{false, "Resume IGD"});//20,0
            }else{}
            int lab = Sequel.cariInteger("select count(no_rawat) from detail_periksa_lab where no_rawat='"+NoRawat+"' ");
            if(lab > 0){
            TabMode.addRow(new Object[]{false, "Laboratorium"});//20,0
            }else{}
            int radiologi = Sequel.cariInteger("select count(no_rawat) from hasil_radiologi where no_rawat='"+NoRawat+"' ");
            if(radiologi > 0){
            TabMode.addRow(new Object[]{false, "Radiologi"});//20,0
            }else{}
            int usg = Sequel.cariInteger("select count(no_rawat) from hasil_pemeriksaan_usg where no_rawat='"+NoRawat+"' ");
            if(usg > 0){
            TabMode.addRow(new Object[]{false, "Hasil USG"});//20,0
            }else{}
//            TabMode.addRow(new Object[]{false, "Hasil ENDOSKOPI THT"});//20,0
            int ie = Sequel.cariInteger("select count(no_rawat) from inacbg_klaim_baru2 where no_rawat='"+NoRawat+"' ");
            if(ie > 0){
            TabMode.addRow(new Object[]{false, "Individual Eklaim"});//20,0
            }else{}
            int digital = Sequel.cariInteger("select count(no_rawat) from berkas_digital_perawatan where no_rawat='"+NoRawat+"' ");
            if(digital > 0){
            TabMode.addRow(new Object[]{false, "Import Berkas Digital Keperawatan"});//20,0
            }else{}
            int triase = Sequel.cariInteger("select count(no_rawat) from data_triase_igdprimer where no_rawat='"+NoRawat+"' ");
            if(triase > 0){
            TabMode.addRow(new Object[]{false, "Triase IGD Zona Merah"});//20,0
            }else{}
            int rehab = Sequel.cariInteger("select count(no_rawat) from pemeriksaan_ralan_rehab where no_rawat='"+NoRawat+"' ");
            if(rehab > 0){
            TabMode.addRow(new Object[]{false, "Formulir Klaim Rehab Medik"});//20,0
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

    }

    public void setNoRm(String NoRawat, String NoRm, String NoSEP, String NamaPasien, String StatusKlaim, String Dokter, String tglAwal, String tglAkhir, String JenisPelayanan) {
        this.NoRawat = NoRawat;
        this.NoRm = NoRm;
        this.NoSEP = NoSEP;
        this.NamaPasien = NamaPasien;
        this.StatusKlaim = StatusKlaim;
        this.Dokter = Dokter;
        this.tglAwal = tglAwal;
        this.tglAkhir = tglAkhir;
        this.JenisPelayanan = JenisPelayanan;
    }

//    void uploadPdf(String FileName, String docpath) {
//        try {
//            File file = new File("tempfile/" + FileName);
//            byte[] data = new byte[(int) file.length()];
//            data = FileUtils.readFileToByteArray(file);
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpPost postRequest = new HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/webapps/berkasdigital/upload.php?doc=" + docpath);
//            ByteArrayBody fileData = new ByteArrayBody(data, FileName);
//            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//            reqEntity.addPart("file", fileData);
//            postRequest.setEntity(reqEntity);
//            httpClient.execute(postRequest);
//            
//        } catch (Exception e) {
//            System.out.println("Error Upload" + e);
//        }
//    }
    
    void uploadPdf(String noRawat, String jnsFile, String FileName, String docpath) {
        try {
            File file = new File("tempfile/" + FileName);
            byte[] data = new byte[(int) file.length()];
            data = FileUtils.readFileToByteArray(file);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/webapps/berkasdigital/upload.php?doc=" + docpath);
            ByteArrayBody fileData = new ByteArrayBody(data, FileName);
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);
            httpClient.execute(postRequest);
            saveFileNameBerkas(noRawat, jnsFile, docpath + "/" + FileName);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal membuat berkas "+jnsFile);
            System.out.println("Error Upload" + e);
        }
    }

    private void deleteFile() {
        File file = new File("tempfile");
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }

    private void saveFileNameBerkas(String noRawat, String JenisFile, String NamaFile) {

        if (Sequel.cariInteger("Select count(no_rawat) from tt_berkasdigital where jenis_file='" + JenisFile + "' and no_rawat='" + noRawat + "' and nama_file='" + NamaFile + "' ") > 0) {
            Sequel.queryu("delete from tt_berkasdigital where jenis_file='" + JenisFile + "' and no_rawat='" + noRawat + "' and nama_file='" + NamaFile + "'");
            Sequel.menyimpantf2("tt_berkasdigital", "?,?,?,?,?,?", "No.Rawat", 6,
                    new String[]{noRawat, JenisFile, NamaFile, akses.getkode(), tanggalNow.format(new Date()), jamNow.format(new Date())});
        } else {
            Sequel.menyimpantf2("tt_berkasdigital", "?,?,?,?,?,?", "No.Rawat", 6,
                    new String[]{noRawat, JenisFile, NamaFile, akses.getkode(), tanggalNow.format(new Date()), jamNow.format(new Date())});
        }
    }

    private void createLab(String noRawat) {
        try {
            ps4 = koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    "from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where periksa_lab.kategori='PK' and "+
                    "periksa_lab.no_rawat=? group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam)");
            try {
                ps4.setString(1, NoRawat);
                rs = ps4.executeQuery();
                while (rs.next()) {

//                    kamar = Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='" + rs.getString("no_rawat") + "' order by tgl_masuk desc limit 1");
//                    if (!kamar.equals("")) {
//                        namakamar = Sequel.cariIsi("select concat(no_bed,' , ',nm_kamar)  from  kamar  "
//                                + " where kamar.kd_kamar='" + kamar + "' ");
//                        kamar = "Kamar";
//                    } else if (kamar.equals("")) {
//                        kamar = "Poli";
//                        namakamar = Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "
//                                + "where reg_periksa.no_rawat='" + rs.getString("no_rawat") + "'");
//                    }

                    kamar=Sequel.cariIsi("select ifnull(kamar_inap.kd_kamar,'') from kamar_inap where kamar_inap.no_rawat='"+rs.getString("no_rawat")+"' order by kamar_inap.tgl_masuk desc limit 1");
                        if(!kamar.equals("")){
                            namakamar=kamar+", "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                                    " where kamar.kd_kamar='"+kamar+"' ");            
                            kamar="Kamar";
                        }else if(kamar.equals("")){
                            kamar="Poli";
                            namakamar=Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                                    "where reg_periksa.no_rawat='"+rs.getString("no_rawat")+"'");
                        }
                    Map<String, Object> param = new HashMap<>();
//                    param.put("noperiksa", rs.getString("no_rawat"));
//                    param.put("norm", rs.getString("no_rkm_medis"));
//                    param.put("namapasien", rs.getString("nm_pasien"));
//                    param.put("jkel", rs.getString("jk"));
//                    param.put("umur", rs.getString("umur"));
//                    param.put("pengirim", Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", rs.getString("dokter_perujuk")));
//                    param.put("tanggal", rs.getString("tgl_periksa"));
//                    param.put("penjab", rs.getString("nm_dokter"));
//                    param.put("petugas", rs.getString("nama"));
//                    param.put("jam", rs.getString("jam"));
//                    param.put("alamat", rs.getString("alamat"));
//                    param.put("kamar", kamar);
//                    param.put("namakamar", namakamar);
//                    param.put("tgl_lahir", rs.getString("tgl_lahir"));
//                    param.put("finger", Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter")));
//                    param.put("finger2", Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("nip")));
//                    param.put("tglsample", Sequel.cariIsi("select concat(DATE_FORMAT(tgl_sampel,'%d-%m-%Y'),' ',jam_sampel) as tglSample from permintaan_lab  where no_rawat='" + rs.getString("no_rawat") + "' and DATE_FORMAT(tgl_permintaan,'%d-%m-%Y')='" + rs.getString("tgl_periksa") + "' and jam_hasil='" + rs.getString("jam") + "'"));
                    
                        param.put("noperiksa",rs.getString("no_rawat"));
                        param.put("norm",rs.getString("no_rkm_medis"));
                        param.put("namapasien",rs.getString("nm_pasien"));
                        param.put("jkel",rs.getString("jk"));
                        param.put("umur",rs.getString("umur"));
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        Sequel.queryu("delete from temporary_lab");
                    ps2 = koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "
                            + "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "
                            + "and periksa_lab.jam=?");
                    try {
                        ps2.setString(1, rs.getString("no_rawat"));
                        ps2.setString(2, Valid.SetTgl(rs.getString("tgl_periksa")));
                        ps2.setString(3, rs.getString("jam"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
//                            Sequel.menyimpan("temporary_lab_digital", "'0','" + rs2.getString("nm_perawatan") + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Data User");
                            Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                            ps3 = koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"
                                    + "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "
                                    + "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                            try {
                                ps3.setString(1, rs.getString("no_rawat"));
                                ps3.setString(2, rs2.getString("kd_jenis_prw"));
                                ps3.setString(3, Valid.SetTgl(rs.getString("tgl_periksa")));
                                ps3.setString(4, rs.getString("jam"));
                                rs3 = ps3.executeQuery();
//                                while (rs3.next()) {
//                                    Sequel.menyimpan("temporary_lab_digital", "'0','  " + rs3.getString("Pemeriksaan") + "','" + rs3.getString("nilai") + "','" + rs3.getString("satuan")
//                                            + "','" + rs3.getString("nilai_rujukan") + "','" + rs3.getString("keterangan") + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Data User");
//                                }
                                while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai").replaceAll("'","`")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                    }
                            } catch (Exception e) {
                                System.out.println("Notif ps3 : " + e);
                            } finally {
                                if (rs3 != null) {
                                    rs3.close();
                                }
                                if (ps3 != null) {
                                    ps3.close();
                                }
                            }

                        }
                    } catch (Exception e) {
                        System.out.println("Notif ps2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
//                    param.put("namars", akses.getnamars());
//                    param.put("alamatrs", akses.getalamatrs());
//                    param.put("kotars", akses.getkabupatenrs());
//                    param.put("propinsirs", akses.getpropinsirs());
//                    param.put("kontakrs", akses.getkontakrs());
//                    param.put("emailrs", akses.getemailrs());
//                    param.put("logo", Sequel.cariGambar("select logo from setting"));
//                    param.put("no_lab", rs.getString("no_lab"));
                        param.put("namars",akses.getnamars());
                        param.put("alamatrs",akses.getalamatrs());
                        param.put("kotars",akses.getkabupatenrs());
                        param.put("propinsirs",akses.getpropinsirs());
                        param.put("kontakrs",akses.getkontakrs());
                        param.put("emailrs",akses.getemailrs());   
                        param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                        pspermintaan=koneksi.prepareStatement(
                                "select noorder,DATE_FORMAT(tgl_permintaan,'%d-%m-%Y') as tgl_permintaan,jam_permintaan from permintaan_lab where "+
                                "no_rawat=? and tgl_hasil=? and jam_hasil=?");
                        try {
                            pspermintaan.setString(1,rs.getString("no_rawat"));
                            pspermintaan.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            pspermintaan.setString(3,rs.getString("jam"));
                            rspermintaan=pspermintaan.executeQuery();
                            if(rspermintaan.next()){
                                param.put("nopermintaan",rspermintaan.getString("noorder"));   
                                param.put("tanggalpermintaan",rspermintaan.getString("tgl_permintaan"));  
                                param.put("jampermintaan",rspermintaan.getString("jam_permintaan"));
                                String FileName;
                                FileName = "laboratorium_" + rs.getString("no_rawat").replaceAll("/", "") + "_" + rspermintaan.getString("noorder") + ".pdf";
                                Valid.MyReportPDFWithName("rptPeriksaLab4Permintaan.jasper", "report", "tempfile", FileName, "::[ Pemeriksaan Laboratorium ]::", param);
//                                uploadPdf(FileName, "laboratorium");
                                uploadPdf(NoRawat, "laboratorium", FileName, "laboratorium");
//                                saveFileNameBerkas(rs.getString("no_rawat"), "laboratorium", "laboratorium/" + FileName);
                                Sequel.queryu("delete from temporary_lab");   
                            }else{
                                Valid.MyReport("rptPeriksaLab4.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rspermintaan!=null){
                                rspermintaan.close();
                            }
                            if(pspermintaan!=null){
                                pspermintaan.close();
                            }
                        }
//                    String FileName;
//                    FileName = "laboratorium_" + rs.getString("no_rawat").replaceAll("/", "") + "_" + rspermintaan.getString("noorder") + ".pdf";
//                    Valid.MyReportPDFWithName("rptPeriksaLab4Permintaan.jasper", "report", "tempfile", FileName, "::[ Pemeriksaan Laboratorium ]::", param);
//                    uploadPdf(FileName, "laboratorium");
//                    saveFileNameBerkas(rs.getString("no_rawat"), "laboratorium", "laboratorium/" + FileName);
//                    Sequel.queryu("delete from temporary_lab");
                }
            } catch (Exception e) {
                System.out.println("Notif ps4 : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void createRad(String noRawat) {
        try {
            ps4 = koneksi.prepareStatement(
                    "SELECT periksa_radiologi.nip,nm_perawatan,periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.dokter_perujuk,periksa_radiologi.kd_dokter,dokter.nm_dokter FROM periksa_radiologi INNER JOIN reg_periksa INNER JOIN pasien INNER JOIN petugas INNER JOIN dokter ON periksa_radiologi.no_rawat=reg_periksa.no_rawat AND reg_periksa.no_rkm_medis=pasien.no_rkm_medis AND periksa_radiologi.kd_dokter=dokter.kd_dokter AND periksa_radiologi.nip=petugas.nip INNER JOIN jns_perawatan_radiologi ON periksa_radiologi.kd_jenis_prw = jns_perawatan_radiologi.kd_jenis_prw  WHERE periksa_radiologi.no_rawat='" + noRawat + "' GROUP BY concat(periksa_radiologi.no_rawat,periksa_radiologi.tgl_periksa,periksa_radiologi.jam) ORDER BY periksa_radiologi.tgl_periksa DESC,periksa_radiologi.jam DESC");
            try {
                rs = ps4.executeQuery();
                while (rs.next()) {
                     System.out.println("Notif ps2 : " + rs.getString("nm_perawatan"));
//                    kamar = Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='" + rs.getString("no_rawat") + "' order by tgl_masuk desc limit 1");
//                    if (!kamar.equals("")) {
//                        namakamar = Sequel.cariIsi("select concat(no_bed,' ',nm_kamar) from  kamar  "
//                                + " where kamar.kd_kamar='" + kamar + "' ");
//                        kamar = "Kamar";
//                    } else if (kamar.equals("")) {
//                        kamar = "Poli";
//                        namakamar = Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "
//                                + "where reg_periksa.no_rawat='" + rs.getString("no_rawat") + "'");
//                    }
                    kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='"+rs.getString("no_rawat")+"' order by tgl_masuk desc limit 1");
                    if(!kamar.equals("")){
                        namakamar=kamar+", "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                                    " where kamar.kd_kamar='"+kamar+"' ");            
                        kamar="Kamar";
                    }else if(kamar.equals("")){
                        kamar="Poli";
                        namakamar=Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                                    "where reg_periksa.no_rawat='"+rs.getString("no_rawat")+"'");
                    }
                    
                    ps2 = koneksi.prepareStatement(
                            "select hasil from hasil_radiologi where hasil_radiologi.no_rawat=? and hasil_radiologi.tgl_periksa=? and hasil_radiologi.jam=?");
                    try {
                        ps2.setString(1, rs.getString("no_rawat"));
                        ps2.setString(2, rs.getString("tgl_periksa"));
                        ps2.setString(3, rs.getString("jam"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                             System.out.println("Notif ps2 : " + rs2.getString("hasil"));
                            Map<String, Object> param = new HashMap<>();
                    param.put("noperiksa", rs.getString("no_rawat"));
                    param.put("norm", rs.getString("no_rkm_medis"));
                    param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", rs.getString("no_rkm_medis")));
                    param.put("jkel", Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ", rs.getString("no_rkm_medis")));
                    param.put("umur", Sequel.cariIsi("select concat(umurdaftar,' ',sttsumur)as umur from reg_periksa where no_rawat=? ", rs.getString("no_rawat")));
                    param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", rs.getString("no_rkm_medis")));
                    param.put("pengirim", rs.getString("dokter_perujuk"));
                    param.put("tanggal", Valid.SetTgl3(rs.getString("tgl_periksa")));
                    param.put("penjab", rs.getString("nm_dokter"));
                    param.put("petugas", rs.getString("nama"));
                    param.put("alamat", Sequel.cariIsi("select alamat from pasien where no_rkm_medis=? ", rs.getString("no_rkm_medis")));
                    param.put("kamar", kamar);
                    param.put("namakamar", namakamar);
                    param.put("pemeriksaan", rs.getString("nm_perawatan"));
                    param.put("jam", rs.getString("jam"));
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("hasil", rs2.getString("hasil"));
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    param.put("finger", Sequel.cariIsi("select sidikjari from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("nip")));
                    param.put("finger2", Sequel.cariIsi("select sidikjari from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("nip")));
                    param.put("norawat", rs.getString("no_rawat"));
                    param.put("tgl_hasil",Sequel.cariIsi("select DATE_FORMAT(tgl_hasil,'%d-%m-%Y') from hasil_radiologi where no_rawat='"+rs.getString("no_rawat")+"' and tgl_periksa='"+rs.getString("tgl_periksa")+"'"));
                    param.put("jam_hasil",Sequel.cariIsi("select jam_hasil from hasil_radiologi where no_rawat='"+rs.getString("no_rawat")+"' and tgl_periksa='"+rs.getString("tgl_periksa")+"'"));
                    param.put("no_ijn_praktek",Sequel.cariIsi("select dokter.no_ijn_praktek,periksa_radiologi.kd_dokter from periksa_radiologi inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter where no_rawat=? ",rs.getString("no_rawat")));

                            String FileName;
                            FileName = "radiologi_" + rs.getString("no_rawat").replaceAll("/", "") + "_" +rs.getString("tgl_periksa").replaceAll("-", "")+""+ rs.getString("jam").replaceAll(":", "") + ".pdf";
                            Valid.MyReportPDFWithName("rptPeriksaRadiologi3.jasper", "report", "tempfile", FileName, "::[ E-Lab ]::", param);
//                            uploadPdf(FileName, "radiologi");
                            uploadPdf(NoRawat, "radiologi", FileName, "radiologi");
//                            saveFileNameBerkas(rs.getString("no_rawat"), "radiologi", "radiologi/" + FileName);
                           
                        }
                    } catch (Exception e) {
                        System.out.println("Notif ps2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }

                }
            } catch (Exception e) {
                System.out.println("Notif ps4 : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void createNota(String noRawat) {
        String FileName;
        FileName = "billing_" + noRawat.replaceAll("/", "") + ".pdf";
        Map<String, Object> param = new HashMap<>();
//        param.put("namars", akses.getnamars());
//        param.put("alamatrs", akses.getalamatrs());
//        param.put("kotars", akses.getkabupatenrs());
//        param.put("propinsirs", akses.getpropinsirs());
//        param.put("kontakrs", akses.getkontakrs());
//        param.put("emailrs", akses.getemailrs());
//        param.put("logo", Sequel.cariGambar("select logo from setting"));
//        param.put("norawat", noRawat);
//        param.put("finger", Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", akses.getkode()));
//        param.put("image_ttd", Sequel.cariIsi("select path_ttd from tm_image_ttd_petugas where kd_petugas='" + akses.getkode() + "'"));
        param.put("namars",akses.getnamars());
        param.put("alamatrs",akses.getalamatrs());
        param.put("kotars",akses.getkabupatenrs());
        param.put("propinsirs",akses.getpropinsirs());
        param.put("kontakrs",akses.getkontakrs());
        param.put("emailrs",akses.getemailrs());   
        param.put("logo",Sequel.cariGambar("select logo from setting")); 
        param.put("norawat",NoRawat);
        param.put("jnsBayar", Sequel.cariIsi("SELECT png_jawab FROM reg_periksa JOIN penjab ON reg_periksa.kd_pj=penjab.kd_pj WHERE no_rawat='" + NoRawat + "' "));
        param.put("user", akses.getkode());

//        param.put("namaUser", Sequel.cariIsi("SELECT pegawai.nama FROM tagihan_sadewa JOIN pegawai ON tagihan_sadewa.petugas=pegawai.nik WHERE no_nota='" + noRawat + "' "));
        Valid.MyReportPDFWithName("rptBillingRalan.jasper", "report", "tempfile", FileName, "::[ Billing ]::", param);
//        uploadPdf(FileName, "billing");
        uploadPdf(NoRawat, "billing", FileName, "billing");
//        saveFileNameBerkas(noRawat, "billing_ralan", "billing/" + FileName);
    }
    
    private void importBerkasDigitalKeperawatan(String noRawat) throws MalformedURLException, IOException {
        deleteFile();
        String fileName = "berkas_digitalkeperawatan_" + noRawat.replaceAll("/", "") + ".pdf";

        try {
            sql = "SELECT lokasi_file FROM berkas_digital_perawatan WHERE no_rawat=? ORDER BY kode ASC";
            ps = koneksi.prepareStatement(sql);
            ps.setString(1, noRawat);

            PDFMergerUtility merger = new PDFMergerUtility();
            rs = ps.executeQuery();

            while (rs.next()) {
                String lokasi = rs.getString("lokasi_file");
                String fileUrl = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB()
                                + "/webapps/berkasrawat/" + lokasi;

                String ext = lokasi.substring(lokasi.lastIndexOf(".") + 1).toLowerCase();

                InputStream sourcePdf;

                if (ext.equals("pdf")) {
                    // langsung merge PDF
                    sourcePdf = new URL(fileUrl).openStream();
                } else if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")) {
                    try {
                        sourcePdf = convertImageToPdfStream(fileUrl);
                    } catch (Exception ex) {
                        System.out.println("Gagal convert image → PDF : " + lokasi + " | " + ex);
                        continue; // skip file rusak
                    }
                } else {
                    System.out.println("Lewati file tidak didukung: " + lokasi);
                    continue;
                }

                merger.addSource(sourcePdf);
            }

            merger.setDestinationFileName("tempfile/" + fileName);
            merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

            uploadPdf(noRawat, "berkas_digitalkeperawatan", fileName, "berkas_digitalkeperawatan");

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(null,
                    "Gagal proses berkas digital!\nCek format file atau hak akses server.");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
        }
    }
    
    private InputStream convertImageToPdfStream(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            InputStream imageStream = url.openStream();

            // Load gambar
            BufferedImage image = ImageIO.read(imageStream);

            // Convert ke PDF
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
            doc.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(doc, page);
            PDImageXObject img = LosslessFactory.createFromImage(doc, image);
            cs.drawImage(img, 0, 0);
            cs.close();

            // Simpan ke memory (byte array)
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            doc.save(baos);
            doc.close();

            return new ByteArrayInputStream(baos.toByteArray());

        } catch (Exception ex) {
            System.out.println("Gagal convert image ke PDF: " + ex);
            return null;
        }
    }

}
