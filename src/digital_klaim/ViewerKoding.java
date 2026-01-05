package digital_klaim;

import bridging.ApiBPJS;
import bridging.INACBGCariCoderNIK;
import bridging.INACBGHybrid;
import integration_orthanc.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import custom.*;
import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.embed.swing.JFXPanel;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import laporan.DlgDiagnosaPenyakit;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import rekammedis.RMCariDiagnosa1;
import rekammedis.RMCariProsedur1;
import integration_idrg.DlgDetailKlaim;
import javax.swing.JScrollPane;
//import rekammedis.RMRiwayatPerawatanNew;

/**
 *
 * @author perpustakaan
 */
public class ViewerKoding extends javax.swing.JDialog {

    private final DefaultTableModel tabModeDiagnosa, tabModeDiagnosaPilih, tabModeProsedur, tabModeProsedurPilih, tabModeKunjungan;
    private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;
    private int i;
    private final JPanel panel = new JPanel(new BorderLayout());
    private final JLabel lblStatus = new JLabel();
    private final DefaultTableModel tabMode, TabModeCreateDokumen;
    private final JTextField txtURL = new JTextField();
    private final JProgressBar progressBar = new JProgressBar();
    private final Properties prop = new Properties();
    private final validasi Valid = new validasi();
    private ApiBPJS api = new ApiBPJS();
    private sekuel Sequel = new sekuel();
    private String URL = "", status = "", norkmMedis = "", namaPasien, jamHsl, noorder = "", perujuk, halaman = "", norawat = "", auth, authEncrypt, requestJson, pemeriksaan, kdpenjab, kdpetugas, kamar, namakamar, pilihanCetak = "";
    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2;
    private final validasi validasi = new validasi();
    private final Connection koneksi = koneksiDB.condb();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root, subroot, subroot2, subresponse, subresponse2;
    private JsonNode nameNode;
    private JsonNode response, responsename;
    private ApiIntegrationOrthanc apiDicom = new ApiIntegrationOrthanc();
    private SimpleDateFormat dateFormatMulai = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateReg = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
//    private SimpleDateFormat tanggalNow = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    private RMCariDiagnosa1 rmcaridiagnosa1=new RMCariDiagnosa1(null,false);
    private RMCariProsedur1 rmcariprosedur1=new RMCariProsedur1(null,false);
    private String jnsKlaim = "", tglAwal = "", tglAkhir = "", dokter = "", diagPasien = "", prodPasien = "", jkPasien = "", limitKunjungan = "", coder_nik = "", pilihpage = "", judulform = "", listOperasi = "", noSep = "", tglLahir = "", noKartu = "";

    public ViewerKoding(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initComponents2();
        btnKirimEklaim.setVisible(false);
        auth = koneksiDB.USERORTHANC() + ":" + koneksiDB.PASSORTHANC();
        byte[] encodedBytes = Base64.encodeBase64(auth.getBytes());
        authEncrypt = new String(encodedBytes);
        Object[] row = {"P", "Pilihan Cetak Dokumen"};
        TabModeCreateDokumen = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        DlgDetailKlaim dlgDetail = new DlgDetailKlaim(null, false);

        JScrollPane sc1 = new JScrollPane(dlgDetail.getPanel());
        sc1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sc1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        jTabbedPane1.addTab("Detail Klaim", sc1);

        tbDataCreateDokumen.setModel(TabModeCreateDokumen);
        tbDataCreateDokumen.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDataCreateDokumen.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 2; i++) {
            TableColumn column = tbDataCreateDokumen.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(400);
            }
        }
        tbDataCreateDokumen.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode = new DefaultTableModel(null, new Object[]{
            "No Rawat", "Jenis Dokumen", "Nama File"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
//                if (colIndex == 0) {
//                    a = true;
//                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbListDokumen.setModel(tabMode);
        tbListDokumen.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbListDokumen.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbListDokumen.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(150);
            } else if (i == 1) {
                column.setPreferredWidth(300);
            } else if (i == 2) {
                column.setPreferredWidth(400);
            }
        }
        tbListDokumen.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeDiagnosaPilih = new DefaultTableModel(null, new Object[]{
            "Kode", "Nama Penyakit", "Status"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
//                if (colIndex == 0) {
//                    a = true;
//                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbDiagnosaPilih.setModel(tabModeDiagnosaPilih);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbDiagnosaPilih.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDiagnosaPilih.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 3; i++) {
            TableColumn column = tbDiagnosaPilih.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(40);
            } else if (i == 1) {
                column.setPreferredWidth(400);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            }
        }
        tbDiagnosaPilih.setDefaultRenderer(Object.class, new WarnaTableDiagnosa());

        tabModeDiagnosa = new DefaultTableModel(null, new Object[]{
            "P", "Kode", "Nama Penyakit", "Ciri-ciri Penyakit", "Keterangan", "Ktg.Penyakit", "Ciri-ciri Umum"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbDiagnosa.setModel(tabModeDiagnosa);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbDiagnosa.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDiagnosa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 7; i++) {
            TableColumn column = tbDiagnosa.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(40);
            } else if (i == 2) {
                column.setPreferredWidth(280);
            } else if (i == 3) {
                column.setPreferredWidth(285);
            } else if (i == 4) {
                column.setPreferredWidth(75);
            } else if (i == 5) {
                column.setPreferredWidth(75);
            } else if (i == 6) {
                column.setPreferredWidth(75);
            }
        }
        tbDiagnosa.setDefaultRenderer(Object.class, new WarnaTable());
        tabModeProsedurPilih = new DefaultTableModel(null, new Object[]{
            "Kode", "Nama Tindakan", "Status"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
//                if (colIndex == 0) {
//                    a = true;
//                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbProsedurPilih.setModel(tabModeProsedurPilih);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbProsedurPilih.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbProsedurPilih.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 3; i++) {
            TableColumn column = tbProsedurPilih.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(40);
            } else if (i == 1) {
                column.setPreferredWidth(400);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            }
        }
        tbProsedurPilih.setDefaultRenderer(Object.class, new WarnaTableDiagnosa());

        tabModeProsedur = new DefaultTableModel(null, new Object[]{
            "P", "Kode", "Deskripsi Panjang", "Deskripsi Pendek"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbProsedur.setModel(tabModeProsedur);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbProsedur.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbProsedur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbProsedur.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(350);
            } else if (i == 3) {
                column.setPreferredWidth(350);
            }
        }
        tbProsedur.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeKunjungan = new DefaultTableModel(null, new Object[]{
            "No Rawat", "Tanggal", "Nama Pasien", "Poliklinik", "Jenis Bayar", "No SEP", "No Rujukan"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbListKunjungan.setModel(tabModeKunjungan);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbListKunjungan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbListKunjungan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 7; i++) {
            TableColumn column = tbListKunjungan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(150);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(280);
            } else if (i == 3) {
                column.setPreferredWidth(285);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            }
        }
        tbListKunjungan.setDefaultRenderer(Object.class, new WarnaTable());
        TCariDiagnosaLive.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (TCariDiagnosaLive.getText().length() > 2) {
                    tampilDiagnosa();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (TCariDiagnosaLive.getText().length() > 2) {
                    tampilDiagnosa();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (TCariDiagnosaLive.getText().length() > 2) {
                    tampilDiagnosa();
                }
            }
        });
        TCariProsedur.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (TCariProsedur.getText().length() > 2) {
                    tampilProsedur();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (TCariProsedur.getText().length() > 2) {
                    tampilProsedur();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (TCariProsedur.getText().length() > 2) {
                    tampilProsedur();
                }
            }
        });
    }

    public void setPasien(String NoRawat, String TglPeriksa, String JamPeriksa, Boolean SimpanBtn, String noRM) {
        this.norkmMedis = noRM;

        BtnSimpan.setVisible(SimpanBtn);

    }

    private void initComponents2() {
        txtURL.addActionListener((ActionEvent e) -> {
            loadURL(txtURL.getText(), "", "", "", "");
        });

        progressBar.setPreferredSize(new Dimension(550, 500));
        progressBar.setStringPainted(true);

        panel.add(jfxPanel, BorderLayout.CENTER);

//        internalFrame1.setLayout(new BorderLayout());
//        internalFrame1.add(panel);
    }

    private void createScene() {
        Platform.runLater(new Runnable() {

            public void run() {
                WebView view = new WebView();

                engine = view.getEngine();
                engine.setJavaScriptEnabled(true);

                engine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
                    @Override
                    public WebEngine call(PopupFeatures p) {
                        Stage stage = new Stage(StageStyle.TRANSPARENT);
                        return view.getEngine();
                    }
                });

                engine.titleProperty().addListener((ObservableValue<? extends String> observable, String oldValue, final String newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        ViewerKoding.this.setTitle(newValue);
                    });
                });

                engine.setOnStatusChanged((final WebEvent<String> event) -> {
                    SwingUtilities.invokeLater(() -> {
                        lblStatus.setText(event.getData());
                    });
                });

                engine.getLoadWorker().workDoneProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(newValue.intValue());
                    });
                });

                engine.getLoadWorker().exceptionProperty().addListener((ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) -> {
                    if (engine.getLoadWorker().getState() == FAILED) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(
                                    panel,
                                    (value != null)
                                            ? engine.getLocation() + "\n" + value.getMessage()
                                            : engine.getLocation() + "\nUnexpected Catatan.",
                                    "Loading Catatan...",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    }
                });

                engine.locationProperty().addListener((ObservableValue<? extends String> ov, String oldValue, final String newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        txtURL.setText(newValue);
                    });
                });

                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
                    @Override
                    public void changed(ObservableValue ov, State oldState, State newState) {
                        if (newState == State.SUCCEEDED) {
                            try {
                                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                                if (engine.getLocation().replaceAll("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + prop.getProperty("PORTWEB") + "/" + prop.getProperty("HYBRIDWEB") + "/", "").contains("gbrpemeriksaan/pages")) {
                                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                    Valid.panggilUrl(engine.getLocation().replaceAll("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + prop.getProperty("PORTWEB") + "/" + prop.getProperty("HYBRIDWEB") + "/gbrpemeriksaan/pages/upload/", "gbrpemeriksaan/").replaceAll("http://" + koneksiDB.HOSTHYBRIDWEB() + "/" + prop.getProperty("HYBRIDWEB") + "/gbrpemeriksaan/pages/upload/", "gbrpemeriksaan/"));
                                    engine.executeScript("history.back()");
                                    setCursor(Cursor.getDefaultCursor());
                                } else if (engine.getLocation().replaceAll("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + prop.getProperty("PORTWEB") + "/" + prop.getProperty("HYBRIDWEB") + "/", "").contains("Keluar")) {
                                    dispose();
                                } else if (engine.getLocation().replaceAll("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + prop.getProperty("PORTWEB") + "/" + prop.getProperty("HYBRIDWEB") + "/", "").contains("GABUNG")) {
                                    norawat = Sequel.cariIsi("select no_rawat from temppanggilnorawat");
                                    ps = koneksi.prepareStatement("SELECT berkas_digital_perawatan.lokasi_file "
                                            + "from berkas_digital_perawatan inner join master_berkas_digital "
                                            + "on berkas_digital_perawatan.kode=master_berkas_digital.kode "
                                            + "where berkas_digital_perawatan.no_rawat=? ORDER BY master_berkas_digital.nama ASC ");
                                    try {
                                        PDFMergerUtility ut = new PDFMergerUtility();
                                        URL url;
                                        ps.setString(1, norawat);
                                        rs = ps.executeQuery();
                                        while (rs.next()) {
                                            url = new URL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + prop.getProperty("PORTWEB") + "/" + prop.getProperty("HYBRIDWEB") + "/berkasrawat/" + rs.getString("lokasi_file"));
                                            InputStream is = url.openStream();
                                            ut.addSource(is);
                                        }
                                        ut.setDestinationFileName("merge.pdf");
                                        ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
                                        JOptionPane.showMessageDialog(null, "Proses gabung file selesai..!");
                                        Properties systemProp = System.getProperties();
                                        String currentDir = systemProp.getProperty("user.dir");
                                        File dir = new File(currentDir);
                                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                        Valid.panggilUrl2(dir + "/merge.pdf");
                                        setCursor(Cursor.getDefaultCursor());
                                    } catch (SQLException e) {
                                        System.out.println("Notif : " + e);
                                    } catch (IOException e) {
                                        System.out.println("Notif : " + e);
                                        JOptionPane.showMessageDialog(null, "Gagal menggabungkan file, cek kembali file apakah sudah dalam bentuk PDF.\nAtau cek kembali hak akses file di server dokumen..!!");
                                    } finally {
                                        if (rs != null) {
                                            rs.close();
                                        }
                                        if (ps != null) {
                                            ps.close();
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex);
                            }
                        }
                    }
                });

                jfxPanel.setScene(new Scene(view));
            }
        });
    }

    public void loadURL(String url, String NoRawat, String TglPeriksa, String JamPeriksa, String UUIDSeries) {
        try {
            createScene();
        } catch (Exception e) {
        }

        Platform.runLater(() -> {
            try {

                engine.getCreatePopupHandler(); //setOnAlert(null);
                engine.setJavaScriptEnabled(true);
                engine.setUserAgent("foo\nAuthorization: Basic " + authEncrypt);
                engine.load(url);

            } catch (Exception exception) {
                engine.load(url);
            }
        });
    }

    public void CloseScane() {
        Platform.setImplicitExit(false);
    }

    public void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupDiagnosa = new javax.swing.JPopupMenu();
        mnUbahPrimer = new javax.swing.JMenuItem();
        mnHapusSatu = new javax.swing.JMenuItem();
        mnHapusSemua = new javax.swing.JMenuItem();
        jPopupProsedur = new javax.swing.JPopupMenu();
        mnUbahPrimer1 = new javax.swing.JMenuItem();
        mnHapusSatu1 = new javax.swing.JMenuItem();
        mnHapusSemua1 = new javax.swing.JMenuItem();
        DlgCreateDokumen = new javax.swing.JDialog();
        internalFrame25 = new widget.InternalFrame();
        internalFrame26 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDataCreateDokumen = new widget.Table();
        panelBiasa15 = new widget.PanelBiasa();
        BtnCreate = new widget.Button();
        BtnKeluarCreateDokumen = new widget.Button();
        internalFrame27 = new widget.InternalFrame();
        internalFrame28 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        detailKlaim = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        internalFrame4 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        internalFrame5 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbListDokumen = new widget.Table();
        jScrollPane1 = new javax.swing.JScrollPane();
        internalFrame1 = new widget.InternalFrame();
        panelGlass9 = new widget.panelisi();
        txtNoSep = new widget.TextBox();
        jLabel4 = new widget.Label();
        txtNoRm = new widget.TextBox();
        txtNoRawat = new widget.TextBox();
        txtNamaPasien = new widget.TextBox();
        jLabel5 = new widget.Label();
        BtnKeluar2 = new widget.Button();
        BtnKeluar3 = new widget.Button();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        internalFrame23 = new widget.InternalFrame();
        internalFrame24 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbListKunjungan = new widget.Table();
        panelBiasa14 = new widget.PanelBiasa();
        cmbRiwayatPasien = new widget.ComboBox();
        BtnCariKunjungan = new widget.Button();
        internalFrame19 = new widget.InternalFrame();
        internalFrame20 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbDiagnosa = new widget.Table();
        Scroll16 = new widget.ScrollPane();
        tbDiagnosaPilih = new widget.Table();
        panelBiasa12 = new widget.PanelBiasa();
        jLabel132 = new widget.Label();
        TCariDiagnosaLive = new widget.TextBox();
        internalFrame21 = new widget.InternalFrame();
        internalFrame22 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbProsedur = new widget.Table();
        Scroll17 = new widget.ScrollPane();
        tbProsedurPilih = new widget.Table();
        panelBiasa13 = new widget.PanelBiasa();
        jLabel133 = new widget.Label();
        TCariProsedur = new widget.TextBox();
        panelGlass8 = new widget.panelisi();
        btnKirimEklaim1 = new widget.Button();
        BtnSimpan2 = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnSimpan1 = new widget.Button();
        btnKirimEklaim2 = new widget.Button();
        BtnKeluar1 = new widget.Button();
        btnKirimEklaim = new widget.Button();

        jPopupDiagnosa.setName("jPopupDiagnosa"); // NOI18N

        mnUbahPrimer.setBackground(new java.awt.Color(255, 255, 254));
        mnUbahPrimer.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnUbahPrimer.setForeground(new java.awt.Color(50, 50, 50));
        mnUbahPrimer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnUbahPrimer.setText("Jadikan Primary");
        mnUbahPrimer.setToolTipText("");
        mnUbahPrimer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnUbahPrimer.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnUbahPrimer.setName("mnUbahPrimer"); // NOI18N
        mnUbahPrimer.setPreferredSize(new java.awt.Dimension(200, 26));
        mnUbahPrimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUbahPrimerActionPerformed(evt);
            }
        });
        jPopupDiagnosa.add(mnUbahPrimer);

        mnHapusSatu.setBackground(new java.awt.Color(255, 255, 254));
        mnHapusSatu.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnHapusSatu.setForeground(new java.awt.Color(50, 50, 50));
        mnHapusSatu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnHapusSatu.setText("Hapus Yang Dipilih");
        mnHapusSatu.setToolTipText("");
        mnHapusSatu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnHapusSatu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnHapusSatu.setName("mnHapusSatu"); // NOI18N
        mnHapusSatu.setPreferredSize(new java.awt.Dimension(200, 26));
        mnHapusSatu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnHapusSatuActionPerformed(evt);
            }
        });
        jPopupDiagnosa.add(mnHapusSatu);

        mnHapusSemua.setBackground(new java.awt.Color(255, 255, 254));
        mnHapusSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnHapusSemua.setForeground(new java.awt.Color(50, 50, 50));
        mnHapusSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnHapusSemua.setText("Hapus Semua");
        mnHapusSemua.setToolTipText("");
        mnHapusSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnHapusSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnHapusSemua.setName("mnHapusSemua"); // NOI18N
        mnHapusSemua.setPreferredSize(new java.awt.Dimension(200, 26));
        mnHapusSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnHapusSemuaActionPerformed(evt);
            }
        });
        jPopupDiagnosa.add(mnHapusSemua);

        jPopupProsedur.setName("jPopupProsedur"); // NOI18N

        mnUbahPrimer1.setBackground(new java.awt.Color(255, 255, 254));
        mnUbahPrimer1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnUbahPrimer1.setForeground(new java.awt.Color(50, 50, 50));
        mnUbahPrimer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnUbahPrimer1.setText("Jadikan Primary");
        mnUbahPrimer1.setToolTipText("");
        mnUbahPrimer1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnUbahPrimer1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnUbahPrimer1.setName("mnUbahPrimer1"); // NOI18N
        mnUbahPrimer1.setPreferredSize(new java.awt.Dimension(200, 26));
        mnUbahPrimer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUbahPrimer1ActionPerformed(evt);
            }
        });
        jPopupProsedur.add(mnUbahPrimer1);

        mnHapusSatu1.setBackground(new java.awt.Color(255, 255, 254));
        mnHapusSatu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnHapusSatu1.setForeground(new java.awt.Color(50, 50, 50));
        mnHapusSatu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnHapusSatu1.setText("Hapus Yang Dipilih");
        mnHapusSatu1.setToolTipText("");
        mnHapusSatu1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnHapusSatu1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnHapusSatu1.setName("mnHapusSatu1"); // NOI18N
        mnHapusSatu1.setPreferredSize(new java.awt.Dimension(200, 26));
        mnHapusSatu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnHapusSatu1ActionPerformed(evt);
            }
        });
        jPopupProsedur.add(mnHapusSatu1);

        mnHapusSemua1.setBackground(new java.awt.Color(255, 255, 254));
        mnHapusSemua1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnHapusSemua1.setForeground(new java.awt.Color(50, 50, 50));
        mnHapusSemua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnHapusSemua1.setText("Hapus Semua");
        mnHapusSemua1.setToolTipText("");
        mnHapusSemua1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnHapusSemua1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnHapusSemua1.setName("mnHapusSemua1"); // NOI18N
        mnHapusSemua1.setPreferredSize(new java.awt.Dimension(200, 26));
        mnHapusSemua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnHapusSemua1ActionPerformed(evt);
            }
        });
        jPopupProsedur.add(mnHapusSemua1);

        DlgCreateDokumen.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgCreateDokumen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        DlgCreateDokumen.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        DlgCreateDokumen.setName("DlgCreateDokumen"); // NOI18N
        DlgCreateDokumen.setUndecorated(true);
        DlgCreateDokumen.setResizable(false);

        internalFrame25.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Daftar Dokumen ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame25.setName("internalFrame25"); // NOI18N
        internalFrame25.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame26.setName("internalFrame26"); // NOI18N
        internalFrame26.setLayout(new java.awt.BorderLayout());

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbDataCreateDokumen.setAutoCreateRowSorter(true);
        tbDataCreateDokumen.setName("tbDataCreateDokumen"); // NOI18N
        Scroll.setViewportView(tbDataCreateDokumen);

        internalFrame26.add(Scroll, java.awt.BorderLayout.CENTER);

        internalFrame25.add(internalFrame26, java.awt.BorderLayout.CENTER);

        panelBiasa15.setName("panelBiasa15"); // NOI18N
        panelBiasa15.setPreferredSize(new java.awt.Dimension(0, 40));
        panelBiasa15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

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
        panelBiasa15.add(BtnCreate);

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
        panelBiasa15.add(BtnKeluarCreateDokumen);

        internalFrame25.add(panelBiasa15, java.awt.BorderLayout.PAGE_END);

        DlgCreateDokumen.getContentPane().add(internalFrame25, java.awt.BorderLayout.CENTER);

        internalFrame27.setBorder(null);
        internalFrame27.setName("internalFrame27"); // NOI18N
        internalFrame27.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame28.setName("internalFrame28"); // NOI18N
        internalFrame28.setLayout(new java.awt.BorderLayout());

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        detailKlaim.setName("detailKlaim"); // NOI18N
        detailKlaim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detailKlaimMouseClicked(evt);
            }
        });
        Scroll5.setViewportView(detailKlaim);

        internalFrame28.add(Scroll5, java.awt.BorderLayout.CENTER);

        internalFrame27.add(internalFrame28, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("::[ About Program ]::");
        setUndecorated(true);
        setResizable(false);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setPreferredSize(new java.awt.Dimension(500, 600));
        internalFrame3.setLayout(new java.awt.BorderLayout());

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setPreferredSize(new java.awt.Dimension(500, 500));
        internalFrame4.setLayout(new java.awt.BorderLayout());

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 500));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setPreferredSize(new java.awt.Dimension(500, 500));
        internalFrame5.setLayout(new java.awt.BorderLayout());

        Scroll4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), " List Dokumen"));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(460, 150));

        tbListDokumen.setName("tbListDokumen"); // NOI18N
        tbListDokumen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListDokumenMouseClicked(evt);
            }
        });
        Scroll4.setViewportView(tbListDokumen);

        internalFrame5.add(Scroll4, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        internalFrame5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(internalFrame5);

        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout());

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 70));
        panelGlass9.setLayout(null);

        txtNoSep.setEditable(false);
        txtNoSep.setHighlighter(null);
        txtNoSep.setName("txtNoSep"); // NOI18N
        txtNoSep.setPreferredSize(new java.awt.Dimension(250, 24));
        txtNoSep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoSepKeyPressed(evt);
            }
        });
        panelGlass9.add(txtNoSep);
        txtNoSep.setBounds(80, 40, 410, 23);

        jLabel4.setText("No SEP :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass9.add(jLabel4);
        jLabel4.setBounds(0, 40, 70, 23);

        txtNoRm.setEditable(false);
        txtNoRm.setHighlighter(null);
        txtNoRm.setName("txtNoRm"); // NOI18N
        txtNoRm.setPreferredSize(new java.awt.Dimension(250, 24));
        txtNoRm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoRmKeyPressed(evt);
            }
        });
        panelGlass9.add(txtNoRm);
        txtNoRm.setBounds(200, 10, 90, 23);

        txtNoRawat.setEditable(false);
        txtNoRawat.setHighlighter(null);
        txtNoRawat.setName("txtNoRawat"); // NOI18N
        txtNoRawat.setPreferredSize(new java.awt.Dimension(250, 24));
        txtNoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoRawatKeyPressed(evt);
            }
        });
        panelGlass9.add(txtNoRawat);
        txtNoRawat.setBounds(80, 10, 120, 23);

        txtNamaPasien.setEditable(false);
        txtNamaPasien.setHighlighter(null);
        txtNamaPasien.setName("txtNamaPasien"); // NOI18N
        txtNamaPasien.setPreferredSize(new java.awt.Dimension(250, 24));
        txtNamaPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaPasienKeyPressed(evt);
            }
        });
        panelGlass9.add(txtNamaPasien);
        txtNamaPasien.setBounds(290, 10, 200, 23);

        jLabel5.setText("Pasien :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass9.add(jLabel5);
        jLabel5.setBounds(0, 10, 70, 23);

        BtnKeluar2.setBackground(new java.awt.Color(102, 0, 153));
        BtnKeluar2.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluar2.setMnemonic('K');
        BtnKeluar2.setText("Prosedur");
        BtnKeluar2.setToolTipText("Alt+K");
        BtnKeluar2.setMargin(new java.awt.Insets(1, 7, 1, 7));
        BtnKeluar2.setName("BtnKeluar2"); // NOI18N
        BtnKeluar2.setOpaque(true);
        BtnKeluar2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar2ActionPerformed(evt);
            }
        });
        BtnKeluar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar2KeyPressed(evt);
            }
        });
        panelGlass9.add(BtnKeluar2);
        BtnKeluar2.setBounds(500, 40, 110, 30);

        BtnKeluar3.setBackground(new java.awt.Color(51, 51, 255));
        BtnKeluar3.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluar3.setMnemonic('K');
        BtnKeluar3.setText("Diagnosa");
        BtnKeluar3.setToolTipText("Alt+K");
        BtnKeluar3.setMargin(new java.awt.Insets(1, 7, 1, 7));
        BtnKeluar3.setName("BtnKeluar3"); // NOI18N
        BtnKeluar3.setOpaque(true);
        BtnKeluar3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar3ActionPerformed(evt);
            }
        });
        BtnKeluar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar3KeyPressed(evt);
            }
        });
        panelGlass9.add(BtnKeluar3);
        BtnKeluar3.setBounds(500, 0, 110, 30);

        internalFrame1.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        internalFrame23.setBorder(null);
        internalFrame23.setName("internalFrame23"); // NOI18N
        internalFrame23.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame24.setName("internalFrame24"); // NOI18N
        internalFrame24.setLayout(new java.awt.BorderLayout());

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbListKunjungan.setName("tbListKunjungan"); // NOI18N
        tbListKunjungan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListKunjunganMouseClicked(evt);
            }
        });
        Scroll3.setViewportView(tbListKunjungan);

        internalFrame24.add(Scroll3, java.awt.BorderLayout.CENTER);

        internalFrame23.add(internalFrame24, java.awt.BorderLayout.CENTER);

        panelBiasa14.setName("panelBiasa14"); // NOI18N
        panelBiasa14.setPreferredSize(new java.awt.Dimension(0, 40));
        panelBiasa14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cmbRiwayatPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5 Riwayat Terakhir", "50 Riwayat Terakhir", "150 Riwayat Terakhir", "Semua" }));
        cmbRiwayatPasien.setName("cmbRiwayatPasien"); // NOI18N
        cmbRiwayatPasien.setPreferredSize(new java.awt.Dimension(150, 28));
        cmbRiwayatPasien.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbRiwayatPasienItemStateChanged(evt);
            }
        });
        cmbRiwayatPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbRiwayatPasienKeyPressed(evt);
            }
        });
        panelBiasa14.add(cmbRiwayatPasien);

        BtnCariKunjungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariKunjungan.setMnemonic('2');
        BtnCariKunjungan.setToolTipText("Alt+2");
        BtnCariKunjungan.setName("BtnCariKunjungan"); // NOI18N
        BtnCariKunjungan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariKunjunganActionPerformed(evt);
            }
        });
        BtnCariKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKunjunganKeyPressed(evt);
            }
        });
        panelBiasa14.add(BtnCariKunjungan);

        internalFrame23.add(panelBiasa14, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Riwayat Kunjungan", internalFrame23);

        internalFrame19.setBorder(null);
        internalFrame19.setName("internalFrame19"); // NOI18N
        internalFrame19.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame20.setName("internalFrame20"); // NOI18N
        internalFrame20.setLayout(new java.awt.GridLayout(1, 0));

        Scroll1.setBorder(javax.swing.BorderFactory.createTitledBorder("List Diagnosa"));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbDiagnosa.setName("tbDiagnosa"); // NOI18N
        tbDiagnosa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDiagnosaMouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbDiagnosa);

        internalFrame20.add(Scroll1);

        Scroll16.setBorder(javax.swing.BorderFactory.createTitledBorder("Diagnosa Dipilih"));
        Scroll16.setName("Scroll16"); // NOI18N
        Scroll16.setOpaque(true);

        tbDiagnosaPilih.setComponentPopupMenu(jPopupDiagnosa);
        tbDiagnosaPilih.setName("tbDiagnosaPilih"); // NOI18N
        Scroll16.setViewportView(tbDiagnosaPilih);

        internalFrame20.add(Scroll16);

        internalFrame19.add(internalFrame20, java.awt.BorderLayout.CENTER);

        panelBiasa12.setName("panelBiasa12"); // NOI18N
        panelBiasa12.setPreferredSize(new java.awt.Dimension(0, 40));
        panelBiasa12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel132.setText("Cari ICD :");
        jLabel132.setName("jLabel132"); // NOI18N
        panelBiasa12.add(jLabel132);

        TCariDiagnosaLive.setName("TCariDiagnosaLive"); // NOI18N
        TCariDiagnosaLive.setPreferredSize(new java.awt.Dimension(250, 24));
        TCariDiagnosaLive.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariDiagnosaLiveKeyPressed(evt);
            }
        });
        panelBiasa12.add(TCariDiagnosaLive);

        internalFrame19.add(panelBiasa12, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Diagnosa", internalFrame19);

        internalFrame21.setBorder(null);
        internalFrame21.setName("internalFrame21"); // NOI18N
        internalFrame21.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame22.setName("internalFrame22"); // NOI18N
        internalFrame22.setLayout(new java.awt.GridLayout(1, 0));

        Scroll2.setBorder(javax.swing.BorderFactory.createTitledBorder("List Prosedur"));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbProsedur.setName("tbProsedur"); // NOI18N
        tbProsedur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProsedurMouseClicked(evt);
            }
        });
        Scroll2.setViewportView(tbProsedur);

        internalFrame22.add(Scroll2);

        Scroll17.setBorder(javax.swing.BorderFactory.createTitledBorder("Prosedur Dipilih"));
        Scroll17.setName("Scroll17"); // NOI18N
        Scroll17.setOpaque(true);

        tbProsedurPilih.setComponentPopupMenu(jPopupProsedur);
        tbProsedurPilih.setName("tbProsedurPilih"); // NOI18N
        Scroll17.setViewportView(tbProsedurPilih);

        internalFrame22.add(Scroll17);

        internalFrame21.add(internalFrame22, java.awt.BorderLayout.CENTER);

        panelBiasa13.setName("panelBiasa13"); // NOI18N
        panelBiasa13.setPreferredSize(new java.awt.Dimension(0, 40));
        panelBiasa13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel133.setText("Cari ICD :");
        jLabel133.setName("jLabel133"); // NOI18N
        panelBiasa13.add(jLabel133);

        TCariProsedur.setName("TCariProsedur"); // NOI18N
        TCariProsedur.setPreferredSize(new java.awt.Dimension(250, 24));
        TCariProsedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariProsedurKeyPressed(evt);
            }
        });
        panelBiasa13.add(TCariProsedur);

        internalFrame21.add(panelBiasa13, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Prosedur", internalFrame21);

        internalFrame1.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(internalFrame1);

        internalFrame4.add(jPanel1, java.awt.BorderLayout.CENTER);

        internalFrame3.add(internalFrame4, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 48));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        btnKirimEklaim1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/tasksgroup.png"))); // NOI18N
        btnKirimEklaim1.setMnemonic('2');
        btnKirimEklaim1.setText("Create Dokumen");
        btnKirimEklaim1.setToolTipText("Alt+2");
        btnKirimEklaim1.setMargin(new java.awt.Insets(1, 7, 1, 7));
        btnKirimEklaim1.setName("btnKirimEklaim1"); // NOI18N
        btnKirimEklaim1.setPreferredSize(new java.awt.Dimension(155, 30));
        btnKirimEklaim1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimEklaim1ActionPerformed(evt);
            }
        });
        panelGlass8.add(btnKirimEklaim1);

        BtnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan2.setMnemonic('S');
        BtnSimpan2.setText("Selesai Admin");
        BtnSimpan2.setToolTipText("Alt+S");
        BtnSimpan2.setMargin(new java.awt.Insets(1, 7, 1, 7));
        BtnSimpan2.setName("BtnSimpan2"); // NOI18N
        BtnSimpan2.setPreferredSize(new java.awt.Dimension(150, 30));
        BtnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan2ActionPerformed(evt);
            }
        });
        BtnSimpan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan2KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan2);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Selesai Koding");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setMargin(new java.awt.Insets(1, 7, 1, 7));
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(150, 30));
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

        BtnSimpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Prescription.png"))); // NOI18N
        BtnSimpan1.setMnemonic('S');
        BtnSimpan1.setText("Riwayat Perawatan");
        BtnSimpan1.setToolTipText("Alt+S");
        BtnSimpan1.setMargin(new java.awt.Insets(1, 7, 1, 7));
        BtnSimpan1.setName("BtnSimpan1"); // NOI18N
        BtnSimpan1.setPreferredSize(new java.awt.Dimension(180, 30));
        BtnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan1ActionPerformed(evt);
            }
        });
        BtnSimpan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan1);

        btnKirimEklaim2.setBackground(new java.awt.Color(0, 102, 102));
        btnKirimEklaim2.setForeground(new java.awt.Color(255, 255, 255));
        btnKirimEklaim2.setMnemonic('2');
        btnKirimEklaim2.setText("Kirim E-klaim");
        btnKirimEklaim2.setToolTipText("Alt+2");
        btnKirimEklaim2.setMargin(new java.awt.Insets(1, 7, 1, 7));
        btnKirimEklaim2.setName("btnKirimEklaim2"); // NOI18N
        btnKirimEklaim2.setOpaque(true);
        btnKirimEklaim2.setPreferredSize(new java.awt.Dimension(128, 30));
        btnKirimEklaim2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimEklaim2ActionPerformed(evt);
            }
        });
        panelGlass8.add(btnKirimEklaim2);

        BtnKeluar1.setBackground(new java.awt.Color(255, 0, 0));
        BtnKeluar1.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Keluar");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setMargin(new java.awt.Insets(1, 7, 1, 7));
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setOpaque(true);
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        BtnKeluar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar1);

        btnKirimEklaim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png"))); // NOI18N
        btnKirimEklaim.setMnemonic('2');
        btnKirimEklaim.setText("Kirim E-klaim OLD");
        btnKirimEklaim.setToolTipText("Alt+2");
        btnKirimEklaim.setMargin(new java.awt.Insets(1, 7, 1, 7));
        btnKirimEklaim.setName("btnKirimEklaim"); // NOI18N
        btnKirimEklaim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimEklaimActionPerformed(evt);
            }
        });
        panelGlass8.add(btnKirimEklaim);

        internalFrame3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Platform.setImplicitExit(false);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        if (this.isActive() == false) {
            Platform.setImplicitExit(false);
        }
    }//GEN-LAST:event_formWindowStateChanged

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        noorder = "";

        BtnKeluar1.requestFocusInWindow(); // ambil fokus penuh

        try { jTabbedPane1.removeAll(); } catch (Exception e) {}

        for (WindowListener wl : this.getWindowListeners()) {
            this.removeWindowListener(wl);
        }

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        dispose();

    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void BtnKeluar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
        }
    }//GEN-LAST:event_BtnKeluar1KeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum koding ini di selesaikan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            Sequel.menyimpan("tt_status_koding", "?,?,?,?,?", "Koding", 5, new String[]{
                norawat, "Sudah Koding",akses.getkode(),tanggalNow.format(new Date()),jamNow.format(new Date())
            });
            dispose();
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        //        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
        //            BtnSimpanActionPerformed(null);
        //        }else{
        //            Valid.pindah(evt, noRawat,BtnBatal);
        //        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void tbListDokumenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListDokumenMouseClicked
        openpdf(tbListDokumen.getValueAt(tbListDokumen.getSelectedRow(), 2).toString());
    }//GEN-LAST:event_tbListDokumenMouseClicked

    private void BtnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan1ActionPerformed

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//            DlgResumePerawatan resume=new DlgResumePerawatan(null,true);
        RMRiwayatPerawatan resume = new RMRiwayatPerawatan(null, true);
        resume.setNoRm(norkmMedis, namaPasien);
        resume.setSize(internalFrame3.getWidth(), internalFrame3.getHeight());
        resume.setLocationRelativeTo(internalFrame3);
        resume.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());

    }//GEN-LAST:event_BtnSimpan1ActionPerformed

    private void BtnSimpan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSimpan1KeyPressed

    private void tbDiagnosaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDiagnosaMouseClicked
        if (tabModeDiagnosa.getRowCount() != 0) {

            try {
                for (i = 0; i < tbDiagnosa.getRowCount(); i++) {
                    if (tbDiagnosa.getValueAt(i, 0).toString().equals("true")) {
                        if (Sequel.cariInteger(
                                "select count(diagnosa_pasien_coding.kd_penyakit) from diagnosa_pasien_coding "
                                + "inner join reg_periksa inner join pasien on "
                                + "diagnosa_pasien_coding.no_rawat=reg_periksa.no_rawat and "
                                + "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "
                                + "diagnosa_pasien_coding.no_rawat='" + norawat + "' and diagnosa_pasien_coding.kd_penyakit='" + tbDiagnosa.getValueAt(i, 1).toString() + "'") > 0) {
                            Sequel.menyimpan("diagnosa_pasien_coding", "?,?,?,?,?", "Penyakit", 5, new String[]{
                                norawat, tbDiagnosa.getValueAt(i, 1).toString(), status,
                                Sequel.cariIsi("select ifnull(MAX(prioritas)+1,1) from diagnosa_pasien_coding where no_rawat=? and status='" + status + "'", norawat), "Lama"
                            });
                        } else {
                            Sequel.menyimpan("diagnosa_pasien_coding", "?,?,?,?,?", "Penyakit", 5, new String[]{
                                norawat, tbDiagnosa.getValueAt(i, 1).toString(), status,
                                Sequel.cariIsi("select ifnull(MAX(prioritas)+1,1) from diagnosa_pasien_coding where no_rawat=? and status='" + status + "'", norawat), "Baru"
                            });
                        }
                    }
                }
                tampilDiagnosa();
                tampilDiagnosaPilih();

            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDiagnosaMouseClicked

    private void TCariDiagnosaLiveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariDiagnosaLiveKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariDiagnosaLiveKeyPressed

    private void tbProsedurMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProsedurMouseClicked
        if (tabModeProsedur.getRowCount() != 0) {
            try {
                for (i = 0; i < tbProsedur.getRowCount(); i++) {
                    if (tbProsedur.getValueAt(i, 0).toString().equals("true")) {
                        Sequel.menyimpan("prosedur_pasien_coding", "?,?,?,?", "ICD 9", 4, new String[]{
                            norawat, tbProsedur.getValueAt(i, 1).toString(), status, Sequel.cariIsi("select ifnull(MAX(prioritas)+1,1) from prosedur_pasien_coding where no_rawat=? and status='" + status + "'", norawat)
                        });
                    }
                }
                tampilProsedur();
                tampilProsedurPilih();

            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbProsedurMouseClicked

    private void TCariProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariProsedurKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariProsedurKeyPressed

    private void mnUbahPrimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUbahPrimerActionPerformed
        String prioritasOld = Sequel.cariIsi("select prioritas from diagnosa_pasien_coding where status='" + status + "' and  no_rawat='" + norawat + "' and  kd_penyakit='" + tbDiagnosaPilih.getValueAt(tbDiagnosaPilih.getSelectedRow(), 0).toString() + "' ");
        Sequel.queryu("Update  diagnosa_pasien_coding set prioritas='" + prioritasOld + "' where status='" + status + "' and  no_rawat='" + norawat + "' and prioritas='1' ");
        Sequel.queryu("Update  diagnosa_pasien_coding set prioritas='1' where status='" + status + "' and  no_rawat='" + norawat + "' and kd_penyakit='" + tbDiagnosaPilih.getValueAt(tbDiagnosaPilih.getSelectedRow(), 0).toString() + "'  ");
        tampilDiagnosa();
        tampilDiagnosaPilih();
    }//GEN-LAST:event_mnUbahPrimerActionPerformed

    private void mnHapusSatuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnHapusSatuActionPerformed
        Sequel.queryu("delete from diagnosa_pasien_coding where no_rawat='" + norawat + "' and kd_penyakit='" + tbDiagnosaPilih.getValueAt(tbDiagnosaPilih.getSelectedRow(), 0).toString() + "'  ");
        tampilDiagnosa();
        tampilDiagnosaPilih();
    }//GEN-LAST:event_mnHapusSatuActionPerformed

    private void mnHapusSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnHapusSemuaActionPerformed
        for (i = 0; i < tbDiagnosaPilih.getRowCount(); i++) {
            Sequel.queryu("delete from diagnosa_pasien_coding where no_rawat='" + norawat + "' and kd_penyakit='" + tbDiagnosaPilih.getValueAt(i, 0).toString() + "'  ");
        }
        tampilDiagnosa();
        tampilDiagnosaPilih();
    }//GEN-LAST:event_mnHapusSemuaActionPerformed

    private void mnUbahPrimer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUbahPrimer1ActionPerformed
        String prioritasOld = Sequel.cariIsi("select prioritas from prosedur_pasien_coding where status='" + status + "' and  no_rawat='" + norawat + "' and  kode='" + tbProsedurPilih.getValueAt(tbProsedurPilih.getSelectedRow(), 0).toString() + "' ");
        Sequel.queryu("Update  prosedur_pasien_coding set prioritas='" + prioritasOld + "' where status='" + status + "' and  no_rawat='" + norawat + "' and prioritas='1' ");
        Sequel.queryu("Update  prosedur_pasien_coding set prioritas='1' where status='" + status + "' and  no_rawat='" + norawat + "' and kode='" + tbProsedurPilih.getValueAt(tbProsedurPilih.getSelectedRow(), 0).toString() + "'  ");
        tampilProsedur();
        tampilProsedurPilih();
    }//GEN-LAST:event_mnUbahPrimer1ActionPerformed

    private void mnHapusSatu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnHapusSatu1ActionPerformed
        Sequel.queryu("delete from prosedur_pasien_coding where no_rawat='" + norawat + "' and kode='" + tbProsedurPilih.getValueAt(tbProsedurPilih.getSelectedRow(), 0).toString() + "'  ");
        tampilProsedur();
        tampilProsedurPilih();
    }//GEN-LAST:event_mnHapusSatu1ActionPerformed

    private void mnHapusSemua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnHapusSemua1ActionPerformed
        for (i = 0; i < tbProsedurPilih.getRowCount(); i++) {
            Sequel.queryu("delete from prosedur_pasien_coding where no_rawat='" + norawat + "' and kode='" + tbProsedurPilih.getValueAt(i, 0).toString() + "'  ");
        }
        tampilProsedur();
        tampilProsedurPilih();
    }//GEN-LAST:event_mnHapusSemua1ActionPerformed

    private void btnKirimEklaimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimEklaimActionPerformed
        INACBGCariCoderNIK cariNIK = new INACBGCariCoderNIK(null, false);
        INACBGHybrid inacbgklaim = new INACBGHybrid(null, false);
        if (akses.getkode().equals("Admin Utama")) {
            pilihpage = "KlaimBaruManual";
            judulform = "::[ Klaim Manual Pasien Baru Dari Data SEP Ke INACBG V2 ]::";
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            cariNIK.getTable().addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        if (cariNIK.getTable().getSelectedRow() != -1) {
                            coder_nik = cariNIK.getTable().getValueAt(cariNIK.getTable().getSelectedRow(), 2).toString();
                            //                        isTutup();
                            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            try {
                                inacbgklaim.loadURL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/" + "inacbg/login.php?act=login&usere=admin&passwordte=akusayangsamakamu&page=KlaimBaruManual&codernik=" + coder_nik + "&keyword=" + norawat);
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex);
                            }
                            inacbgklaim.setJudul(judulform);
                            inacbgklaim.setSize(internalFrame3.getWidth(), internalFrame3.getHeight() - 20);
                            inacbgklaim.setLocationRelativeTo(internalFrame3);
                            inacbgklaim.setVisible(true);
                            //                        DlgHome.dispose();
                            setCursor(Cursor.getDefaultCursor());
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
            cariNIK.setSize(internalFrame3.getWidth(), internalFrame3.getHeight());
            cariNIK.setLocationRelativeTo(internalFrame3);
            cariNIK.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            coder_nik = Sequel.cariIsi("select no_ik from inacbg_coder_nik where nik=?", akses.getkode());
            if (!coder_nik.equals("")) {
                //                isTutup();
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    inacbgklaim.loadURL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/" + "inacbg/login.php?act=login&usere=admin&passwordte=akusayangsamakamu&page=KlaimBaruManual&codernik=" + coder_nik + "&keyword=" + norawat);

                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                }

                inacbgklaim.setSize(internalFrame3.getWidth(), internalFrame3.getHeight() - 20);
                inacbgklaim.setLocationRelativeTo(internalFrame3);
                inacbgklaim.setJudul("::[ Klaim Manual Pasien Dari Data SEP Ke INACBG V2 ]::");
                inacbgklaim.setVisible(true);
                //                DlgHome.dispose();
                this.setCursor(Cursor.getDefaultCursor());
            } else {
                //                isTutup();
                //                DlgHome.dispose();
                JOptionPane.showMessageDialog(null, "Coder NIK tidak ditemukan, silahkan hubungi Admin Utama..!!");
            }
        }

    }//GEN-LAST:event_btnKirimEklaimActionPerformed

    private void tbListKunjunganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListKunjunganMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbListKunjunganMouseClicked

    private void cmbRiwayatPasienItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbRiwayatPasienItemStateChanged
//        if (cmbRiwayatPasien.getSelectedIndex() == 0) {
//            Tgl3.setVisible(false);
//            Tgl4.setVisible(false);
//            label33.setVisible(false);
//            tampilPerawatan();
//        } else if (cmbRiwayatPasien.getSelectedIndex() == 1 || cmbRiwayatPasien.getSelectedIndex() == 2 || cmbRiwayatPasien.getSelectedIndex() == 3 || cmbRiwayatPasien.getSelectedIndex() == 4 || cmbRiwayatPasien.getSelectedIndex() == 5 || cmbRiwayatPasien.getSelectedIndex() == 6) {
//            Tgl3.setVisible(false);
//            Tgl4.setVisible(false);
//            label33.setVisible(false);
//            tampilPerawatan();
//        } else if (cmbRiwayatPasien.getSelectedIndex() == 7) {
//            Tgl3.setVisible(true);
//            Tgl4.setVisible(true);
//            label33.setVisible(true);
//        } else {
//            Tgl3.setVisible(false);
//            Tgl4.setVisible(false);
//            label33.setVisible(false);
//        }
    }//GEN-LAST:event_cmbRiwayatPasienItemStateChanged

    private void cmbRiwayatPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbRiwayatPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRiwayatPasienKeyPressed

    private void BtnCariKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKunjunganKeyPressed

    }//GEN-LAST:event_BtnCariKunjunganKeyPressed

    private void BtnCariKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariKunjunganActionPerformed
        tampilKunjungan();
    }//GEN-LAST:event_BtnCariKunjunganActionPerformed

    private void btnKirimEklaim1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimEklaim1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPilihanCreateDokumen form = new DlgPilihanCreateDokumen(null, true);
        form.tampil(norawat);
        form.setNoRm(norawat,norkmMedis,noSep,namaPasien,status,dokter,tglAwal,tglAkhir,jnsKlaim);
        form.setSize(300, 500);
        form.setLocationRelativeTo(internalFrame4);
        form.setVisible(true);
        form.setAlwaysOnTop(true);
          form.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                 tampilListDokumen(norawat);
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
       
        this.setCursor(Cursor.getDefaultCursor());
        
    }//GEN-LAST:event_btnKirimEklaim1ActionPerformed
    public void tampilDokumenCreate() {
        try {
            Valid.tabelKosong(TabModeCreateDokumen);
            TabModeCreateDokumen.addRow(new Object[]{false, "S E P"});//20,0
            TabModeCreateDokumen.addRow(new Object[]{false, "Billing"});//20,0
            TabModeCreateDokumen.addRow(new Object[]{false, "Resume"});//20,0
            TabModeCreateDokumen.addRow(new Object[]{false, "Laboratorium"});//20,0
            TabModeCreateDokumen.addRow(new Object[]{false, "Radiologi"});//20,0
            TabModeCreateDokumen.addRow(new Object[]{false, "Individual Eklaim"});//20,0
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

    }
    private void BtnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCreateActionPerformed

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (i = 0; i < tbDataCreateDokumen.getRowCount(); i++) {
            if (tbDataCreateDokumen.getValueAt(i, 0).toString().equals("true")) {
                if (tbDataCreateDokumen.getValueAt(i, 1).toString().equals("S E P")) {

                    String FileName, jnsSep;
                    FileName = "sep_" + noSep + ".pdf";
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("prb", Sequel.cariIsi("select prb from bpjs_prb where no_sep='" + noSep + "' "));
                    param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
                    param.put("parameter", noSep);
                    jnsSep = Sequel.cariIsi("select jnspelayanan from bridging_sep where no_sep='" + noSep + "' ");
                    if (jnsSep.equals("2")) {
                        Valid.MyReportPDFWithName("rptBridgingSEP.jasper", "report", "tempfile", FileName, "::[ E-SEP ]::", param);
                        uploadPdf(FileName, "sep");
                        saveFileNameBerkas(norawat, "sep", "sep/" + FileName);
                    } else {
                        Valid.MyReportPDFWithName("rptBridgingSEP2.jasper", "report", "tempfile", FileName, "::[ E-SEP ]::", param);
                        uploadPdf(FileName, "sep");
                        saveFileNameBerkas(norawat, "sep", "sep/" + FileName);
                    }
                    deleteFile();

                } else if (tbDataCreateDokumen.getValueAt(i, 1).toString().equals("Billing")) {
                    String FileName, kodeDokter;
                    kodeDokter = Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat='" + norawat + "'");
                    FileName = "resume_" + norawat.replaceAll("/", "") + ".pdf";
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    param.put("norawat", norawat);
                    param.put("image_ttd", Sequel.cariIsi("select path_ttd from tm_image_ttd_petugas where kd_petugas='" + kodeDokter + "'"));
                    param.put("finger", Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", kodeDokter));
                    param.put("ruang", Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ", norawat));
                    param.put("tanggalkeluar", Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ", norawat));
                    Valid.MyReportPDFWithName("rptLaporanResumeRajalTtd.jasper", "report", "tempfile", FileName, "::[ E-Resume ]::", param);
                    uploadPdf(FileName, "resume");
                    saveFileNameBerkas(norawat, "resume", "resume/" + FileName);

                    deleteFile();
                } else if (tbDataCreateDokumen.getValueAt(i, 1).toString().equals("Resume")) {
                    String FileName, kodeDokter;
                    kodeDokter = Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat='" + norawat + "'");
                    FileName = "resume_" + norawat.replaceAll("/", "") + ".pdf";
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    param.put("norawat", norawat);
                    param.put("image_ttd", Sequel.cariIsi("select path_ttd from tm_image_ttd_petugas where kd_petugas='" + kodeDokter + "'"));
                    param.put("finger", Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", kodeDokter));
                    param.put("ruang", Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ", norawat));
                    param.put("tanggalkeluar", Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ", norawat));
                    Valid.MyReportPDFWithName("rptLaporanResumeRajalTtd.jasper", "report", "tempfile", FileName, "::[ E-Resume ]::", param);
                    uploadPdf(FileName, "resume");
                    saveFileNameBerkas(norawat, "resume", "resume/" + FileName);

                    deleteFile();
                } else if (tbDataCreateDokumen.getValueAt(i, 1).toString().equals("Individual Eklaim")) {
                    try {
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
//                        requestEntity = new HttpEntity(headers);
                        URL = "http://" + koneksiDB.HOSTHYBRIDWEB() + "/" + koneksiDB.HYBRIDWEB() + "/inacbg_idrg_dev/index.php?act=cekSep&noSep=" + noSep;
                        requestEntity = new HttpEntity(headers);
                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                        if (root.path("metadata").path("code").asText().equals("200")) {
                            saveFileNameBerkas(norawat, "data_individual", "data_individual/individual_" + noSep + ".pdf");
//                            JOptionPane.showMessageDialog(null, root.path("metadata").path("message").asText());
                        }
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : " + ex);
                        if (ex.toString().contains("UnknownHostException")) {
                            JOptionPane.showMessageDialog(rootPane, "Koneksi ke server E-klaim terputus...!");
                        }
                    }
                }
            }
        }

        tampilListDokumen(norawat);
        DlgCreateDokumen.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCreateActionPerformed

    private void BtnKeluarCreateDokumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarCreateDokumenActionPerformed
        DlgCreateDokumen.dispose();
    }//GEN-LAST:event_BtnKeluarCreateDokumenActionPerformed

    private void txtNoSepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoSepKeyPressed

    }//GEN-LAST:event_txtNoSepKeyPressed

    private void btnKirimEklaim2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimEklaim2ActionPerformed
        getDiagnosa();
        getProsedure();

        // --- VALIDASI ---
        if (diagPasien == null || diagPasien.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Diagnosa belum dipilih!");
            return;
        }

        if (prodPasien == null || prodPasien.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Prosedur belum dipilih!");
            return;
        }

        if (noSep == null || noSep.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Nomor SEP kosong!");
            return;
        }

        // AKSES KODER
        if (Sequel.cariInteger("Select count(no_ik) from inacbg_coder_nik where nik='" 
                + akses.getkode() + "' ") <= 0) {
            JOptionPane.showMessageDialog(null, "Anda tidak memiliki akses sebagai koder");
            return;
        }

        String noik = Sequel.cariIsi("select no_ik from inacbg_coder_nik where nik='" 
                + akses.getkode() + "'");

        // --- HITUNG BIAYA ---
        String non_bedah     = Sequel.cariIsi("select if(sum(totalbiaya)='','0',sum(totalbiaya)) "
                            + "from billing where no_rawat='" + norawat + "' and status='Ralan Dokter Paramedis'");
        String bedah         = Sequel.cariIsi("select if(sum(totalbiaya)='','0',sum(totalbiaya)) "
                            + "from billing where no_rawat='" + norawat + "' and status='Operasi'");
        String keperawatan   = Sequel.cariIsi("select if(sum(totalbiaya)='','0',sum(totalbiaya)) "
                            + "from billing where no_rawat='" + norawat + "' and status='Ralan Paramedis'");
        String konsultasi    = Sequel.cariIsi("select if(sum(totalbiaya)='','0',sum(totalbiaya)) "
                            + "from billing where no_rawat='" + norawat + "' and status='Ralan Dokter'");
        String radiologi     = Sequel.cariIsi("select if(sum(totalbiaya)='','0',sum(totalbiaya)) "
                            + "from billing where no_rawat='" + norawat + "' and status='Radiologi'");
        String lab           = Sequel.cariIsi("select if(sum(totalbiaya)='','0',sum(totalbiaya)) "
                            + "from billing where no_rawat='" + norawat + "' and status='Laborat'");
        String obat          = Sequel.cariIsi("select if(sum(totalbiaya)='','0',sum(totalbiaya)) "
                            + "from billing where no_rawat='" + norawat + "' and status='Obat'");

        // --- URL KIRIM E-KLAIM ---
        URL = "http://" + koneksiDB.HOSTHYBRIDWEB() + "/" 
                + koneksiDB.HYBRIDWEB() 
                + "/inacbg_idrg_dev/index.php?act=createNgroupingClaim&nikCoder=" + noik;

        // --- JSON REQUEST ---
        requestJson = "{"
                + "\"no_sep\":\"" + noSep + "\","
                + "\"no_jkn\":\"" + noKartu + "\","
                + "\"no_rm\":\"" + norkmMedis + "\","
                + "\"no_reg\":\"" + norawat + "\","
                + "\"nama_pasien\":\"" + namaPasien + "\","
                + "\"tgl_lahir\":\"" + tglLahir + "\","
                + "\"jk\":\"" + jkPasien + "\","
                + "\"diagnosa\":\"" + diagPasien + "\","
                + "\"prosedur\":\"" + prodPasien + "\","
                + "\"dokter\":\"" + dokter + "\","
                + "\"tgl_awal\":\"" + tglAwal + "\","
                + "\"tgl_akhir\":\"" + tglAkhir + "\","
                + "\"konsultasi\":\"" + konsultasi + "\","
                + "\"lab\":\"" + lab + "\","
                + "\"obat\":\"" + obat + "\","
                + "\"non_bedah\":\"" + non_bedah + "\","
                + "\"bedah\":\"" + bedah + "\","
                + "\"keperawatan\":\"" + keperawatan + "\","
                + "\"radiologi\":\"" + radiologi + "\","
                + "\"jenis\":\"" + jnsKlaim + "\""
                + "}";

        System.out.println("REQUEST URL = " + URL);
        System.out.println("REQUEST JSON = " + requestJson);

        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            requestEntity = new HttpEntity(requestJson, headers);

            String responseRaw = api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody();
            System.out.println("RAW RESPONSE:");
            System.out.println(responseRaw);

            root = mapper.readTree(responseRaw);

            String code    = root.path("metadata").path("code").asText();
            String message = root.path("metadata").path("message").asText();

            boolean suksesKlaim = false;

            if (code.equals("200")) {
                suksesKlaim = true;
                SimpanKeDatabase(noik);
                JOptionPane.showMessageDialog(rootPane, "Data BERHASIL dikirim ke E-klaim");
            } 
            else if (code.equals("400") && message.contains("Duplikasi")) {
                suksesKlaim = true;
                SimpanKeDatabase(noik);
                JOptionPane.showMessageDialog(rootPane, 
                    "Nomor SEP sudah pernah dikirim, tetapi data tetap disimpan.");
            } 
            else {
                JOptionPane.showMessageDialog(rootPane, "Gagal kirim E-klaim: " + message);
            }

            // --- JIKA SUKSES, TANYA KIRIM ONLINE ---
            if (suksesKlaim) {
                int reply = JOptionPane.showConfirmDialog(rootPane, 
                        "Apakah anda yakin ingin mengirim klaim ke DC Kemkes?", 
                        "Konfirmasi", JOptionPane.YES_NO_OPTION);

                if (reply == JOptionPane.YES_OPTION) {
                    KirimOnlineKlaim();   // Call function di bawah
                }
            }

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
        }
    }//GEN-LAST:event_btnKirimEklaim2ActionPerformed
    
    private void KirimOnlineKlaim() {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String json = "{ \"no_sep\": \"" + noSep + "\" }";

            String urlOnline = "http://" + koneksiDB.HOSTHYBRIDWEB() + "/" 
                    + koneksiDB.HYBRIDWEB() 
                    + "/inacbg_idrg_dev/index.php?act=kirimOnlineKlaim&nikCoder=" + akses.getkode();

            HttpEntity<String> req = new HttpEntity<>(json, headers);

            String raw = api.getRest().exchange(urlOnline, HttpMethod.POST, req, String.class).getBody();

            System.out.println("RAW ONLINE RESPONSE = " + raw);

            JsonNode r = mapper.readTree(raw);

            if (r.path("metadata").path("code").asText().equals("200")) {
                Sequel.mengedit("tt_status_eklaim", 
                    "no_rawat=? and no_sep=?", 
                    "kirim_online=?",
                    3, new String[]{"true", norawat, noSep});

                JOptionPane.showMessageDialog(null, "Berhasil Kirim Online");
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Gagal kirim online: " + r.path("metadata").path("message").asText());
            }

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server E-klaim terputus...!");
            }
        }
    }

    private void SimpanKeDatabase(String noik) {
        Sequel.menyimpantf2("inacbg_klaim_baru2", "?,?,?,?,?", "No.Rawat", 5,
                new String[]{norawat, noSep, norkmMedis, noik, akses.getkode()});

        Sequel.menyimpantf2("inacbg_data_terkirim2", "?,?", "No.Rawat", 2,
                new String[]{noSep, noik});
    }

    private void txtNoRmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoRmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoRmKeyPressed

    private void txtNoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoRawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoRawatKeyPressed

    private void txtNamaPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaPasienKeyPressed

    private void BtnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan2ActionPerformed
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum administrasi ini di selesaikan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            Sequel.menyimpan("tt_status_administrasi", "?,?,?,?,?", "Administrasi", 5, new String[]{
                norawat, "Sudah Ada",akses.getkode(),tanggalNow.format(new Date()),jamNow.format(new Date())
            });
            dispose();
        }
    }//GEN-LAST:event_BtnSimpan2ActionPerformed

    private void BtnSimpan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSimpan2KeyPressed

    private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            rmcariprosedur1.setNoRawat(norawat);
            rmcariprosedur1.tampil();
            rmcariprosedur1.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcariprosedur1.setLocationRelativeTo(internalFrame1);
            rmcariprosedur1.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnKeluar2ActionPerformed

    private void BtnKeluar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKeluar2KeyPressed

    private void BtnKeluar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar3ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            rmcaridiagnosa1.setNoRawat(norawat);
            rmcaridiagnosa1.tampil();
            rmcaridiagnosa1.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rmcaridiagnosa1.setLocationRelativeTo(internalFrame1);
            rmcaridiagnosa1.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnKeluar3ActionPerformed

    private void BtnKeluar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKeluar3KeyPressed

    private void detailKlaimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailKlaimMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_detailKlaimMouseClicked
    private void getDiagnosa() {
        String listDiagnosa = "";

        try {
            ps = koneksi.prepareStatement(
                "SELECT kd_penyakit FROM diagnosa_pasien WHERE no_rawat=? ORDER BY prioritas ASC"
            );
            ps.setString(1, norawat);

            rs = ps.executeQuery();

            while (rs.next()) {
                String kode = rs.getString("kd_penyakit");

                if (kode != null && !kode.trim().equals("")) {
                    listDiagnosa += kode + "#";
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR getDiagnosa() : " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
        }

        // === FIX: CEGAH ERROR SUBSTRING ===
        if (listDiagnosa.length() > 0) {
            // hapus karakter # di akhir string
            diagPasien = listDiagnosa.substring(0, listDiagnosa.length() - 1);
        } else {
            diagPasien = "";  // kosong → aman
        }

        System.out.println("DIAGNOSA FINAL = " + diagPasien);
    }

    private void getProsedure() {
        String listProsedure = "";

        try {
            ps = koneksi.prepareStatement(
                "SELECT kode FROM prosedur_pasien WHERE no_rawat=? ORDER BY prioritas ASC"
            );
            ps.setString(1, norawat);

            rs = ps.executeQuery();

            while (rs.next()) {
                String kode = rs.getString("kode");

                if (kode != null && !kode.trim().equals("")) {
                    listProsedure += kode + "#";
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR getProsedure(): " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ex) {}
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
        }

        // === FIX: cegah error substring bila kosong ===
        if (listProsedure.length() > 0) {
            prodPasien = listProsedure.substring(0, listProsedure.length() - 1);
        } else {
            prodPasien = "";
        }

        System.out.println("PROSEDURE FINAL = " + prodPasien);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ViewerKoding dialog = new ViewerKoding(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCariKunjungan;
    private widget.Button BtnCreate;
    private widget.Button BtnKeluar1;
    private widget.Button BtnKeluar2;
    private widget.Button BtnKeluar3;
    private widget.Button BtnKeluarCreateDokumen;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan1;
    private widget.Button BtnSimpan2;
    private javax.swing.JDialog DlgCreateDokumen;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll16;
    private widget.ScrollPane Scroll17;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCariDiagnosaLive;
    private widget.TextBox TCariProsedur;
    public widget.Button btnKirimEklaim;
    public widget.Button btnKirimEklaim1;
    public widget.Button btnKirimEklaim2;
    private widget.ComboBox cmbRiwayatPasien;
    public widget.Table detailKlaim;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame19;
    private widget.InternalFrame internalFrame20;
    private widget.InternalFrame internalFrame21;
    private widget.InternalFrame internalFrame22;
    private widget.InternalFrame internalFrame23;
    private widget.InternalFrame internalFrame24;
    private widget.InternalFrame internalFrame25;
    private widget.InternalFrame internalFrame26;
    private widget.InternalFrame internalFrame27;
    private widget.InternalFrame internalFrame28;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupDiagnosa;
    private javax.swing.JPopupMenu jPopupProsedur;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem mnHapusSatu;
    private javax.swing.JMenuItem mnHapusSatu1;
    private javax.swing.JMenuItem mnHapusSemua;
    private javax.swing.JMenuItem mnHapusSemua1;
    private javax.swing.JMenuItem mnUbahPrimer;
    private javax.swing.JMenuItem mnUbahPrimer1;
    private widget.PanelBiasa panelBiasa12;
    private widget.PanelBiasa panelBiasa13;
    private widget.PanelBiasa panelBiasa14;
    private widget.PanelBiasa panelBiasa15;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbDataCreateDokumen;
    public widget.Table tbDiagnosa;
    public widget.Table tbDiagnosaPilih;
    private widget.Table tbListDokumen;
    public widget.Table tbListKunjungan;
    public widget.Table tbProsedur;
    public widget.Table tbProsedurPilih;
    private widget.TextBox txtNamaPasien;
    private widget.TextBox txtNoRawat;
    private widget.TextBox txtNoRm;
    private widget.TextBox txtNoSep;
    // End of variables declaration//GEN-END:variables

    public void setJudul(String Judul, String Pages) {
//        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), Judul, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70)));
        this.halaman = Pages;
    }

    public void setNoOrder(String noOrder) {
        this.noorder = noOrder;
    }

    void uploadPdf(String FileName, String docpath) {
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
//            HttpResponse response = (HttpResponse) 

//            System.out.println("Cookie: " + response);
//            deleteFile();
        } catch (Exception e) {
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

        if (Sequel.cariInteger("Select count(no_rawat) from tt_berkasdigital where jenis_file='" + JenisFile + "' and no_rawat='" + noRawat + "'") > 0) {

        } else {
            Sequel.menyimpantf2("tt_berkasdigital", "?,?,?", "No.Rawat", 3,
                    new String[]{noRawat, JenisFile, NamaFile});
        }

    }

    public void setDataPasien(String noRawat, String noRkmMedis, String nmPasien, String statusKunjungan) {
        this.norawat = noRawat;
        this.noSep = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat='" + noRawat + "'");
        this.norkmMedis = noRkmMedis;
        this.namaPasien = nmPasien;
        this.status = statusKunjungan;
        this.dokter = Sequel.cariIsi("select nm_dokter from reg_periksa JOIN dokter ON reg_periksa.kd_dokter=dokter.kd_dokter where no_rawat='" + noRawat + "'");
        this.tglAwal = Sequel.cariIsi("select concat(tgl_registrasi, ' ',jam_reg) as tglawal from reg_periksa  where no_rawat='" + noRawat + "'");
        this.tglAkhir = Sequel.cariIsi("select concat(tgl_registrasi, ' ',jam_reg) as tglakhir from reg_periksa  where no_rawat='" + noRawat + "'");
        this.jnsKlaim = Sequel.cariIsi("select jnspelayanan from bridging_sep where no_rawat='" + noRawat + "'");
        txtNoSep.setText(noSep);
        txtNoRawat.setText(norawat);
        txtNoRawat.setText(norawat);
        txtNoRm.setText(norkmMedis);
        txtNamaPasien.setText(namaPasien);
        try {
            ps = koneksi.prepareStatement(
                    "select * from pasien where no_rkm_medis='" + noRkmMedis + "'");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    this.tglLahir = rs.getString("tgl_lahir");
                    this.noKartu = rs.getString("no_peserta");
                    this.jkPasien = rs.getString("jk");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

        tampilListDokumen(noRawat);
        tampilDiagnosaPilih();
        tampilProsedurPilih();
        if(Sequel.cariInteger("select count(no_rawat) from tt_status_administrasi where no_rawat='"+norawat+"' ")>0){
            BtnSimpan.setVisible(true);
        }else{
            BtnSimpan.setVisible(false);
        }
        System.out.println("DEBUG noSep = [" + this.noSep + "]");
    }

    public void tampilListDokumen(String noRawatPas) {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement("select * from tt_berkasdigital where no_rawat='" + noRawatPas + "'");
            try {

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        rs.getString(1), rs.getString(2)+ " " +"(" +rs.getString("nik")+ ")" + " " + "(" +rs.getString("tanggal")+ ")" + " " +"("+rs.getString("jam")+")", rs.getString(3)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void openpdf(String file) {
        try {
            URL url = new URL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/webapps/berkasdigital/" + file);
            SwingController ctrl = new SwingController();
            SwingViewBuilder vb = new SwingViewBuilder(ctrl);

            JPanel s = vb.buildViewerPanel();
            s.setPreferredSize(new Dimension(400, 243));
            s.setMaximumSize(new Dimension(400, 243));
            ComponentKeyBinding.install(ctrl, s);
            ctrl.setToolBarVisible(false);
            ctrl.getDocumentViewController().setAnnotationCallback(
                    new org.icepdf.ri.common.MyAnnotationCallback(ctrl.getDocumentViewController())
            );
            ctrl.openDocument(url);
            jScrollPane1.setViewportView(s);
        } catch (Exception e) {

        }
    }

    private void tampilDiagnosa() {
        Valid.tabelKosong(tabModeDiagnosa);
        try {
            ps = koneksi.prepareStatement("select penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "
                    + "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum "
                    + "from  kategori_penyakit inner join penyakit "
                    + "on penyakit.kd_ktg=kategori_penyakit.kd_ktg where NOT EXISTS (select kd_penyakit from diagnosa_pasien_coding where penyakit.kd_penyakit=diagnosa_pasien_coding.kd_penyakit and no_rawat='" + norawat + "') and ("
                    + " penyakit.kd_penyakit like ? or "
                    + " penyakit.nm_penyakit like ? or "
                    + " penyakit.ciri_ciri like ? or "
                    + " penyakit.keterangan like ? or "
                    + " kategori_penyakit.nm_kategori like ? or "
                    + " kategori_penyakit.ciri_umum like ? or "
                    + " REPLACE(penyakit.kd_penyakit,'.','') like ? )  "
                    + "order by penyakit.kd_penyakit  LIMIT 100");
            try {
                ps.setString(1, "%" + TCariDiagnosaLive.getText().trim() + "%");
                ps.setString(2, "%" + TCariDiagnosaLive.getText().trim() + "%");
                ps.setString(3, "%" + TCariDiagnosaLive.getText().trim() + "%");
                ps.setString(4, "%" + TCariDiagnosaLive.getText().trim() + "%");
                ps.setString(5, "%" + TCariDiagnosaLive.getText().trim() + "%");
                ps.setString(6, "%" + TCariDiagnosaLive.getText().trim() + "%");
                ps.setString(7, "%" + TCariDiagnosaLive.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeDiagnosa.addRow(new Object[]{false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilProsedur() {
        Valid.tabelKosong(tabModeProsedur);
        try {
            ps = koneksi.prepareStatement("select * "
                    + "from   icd9 "
                    + " where   NOT EXISTS (select kode from prosedur_pasien_coding  where icd9.kode=prosedur_pasien_coding.kode and no_rawat='" + norawat + "') and ( kode like ? or "
                    + " deskripsi_panjang like ? or  deskripsi_pendek like ? or  REPLACE(kode,'.','') like ? )  "
                    + " order by kode LIMIT 100");
            try {
                ps.setString(1, "%" + TCariProsedur.getText().trim() + "%");
                ps.setString(2, "%" + TCariProsedur.getText().trim() + "%");
                ps.setString(3, "%" + TCariProsedur.getText().trim() + "%");
                ps.setString(4, "%" + TCariProsedur.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeProsedur.addRow(new Object[]{false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilDiagnosaPilih() {
        Valid.tabelKosong(tabModeDiagnosaPilih);
        try {
            ps = koneksi.prepareStatement("select reg_periksa.tgl_registrasi,diagnosa_pasien_coding.no_rawat,reg_periksa.no_rkm_medis,concat(pasien.nm_pasien,' [ ',reg_periksa.umurdaftar,' ',reg_periksa.sttsumur,' ]') ,"
                    + "diagnosa_pasien_coding.kd_penyakit,penyakit.nm_penyakit, diagnosa_pasien_coding.status,diagnosa_pasien_coding.status_penyakit,diagnosa_pasien_coding.prioritas "
                    + "from diagnosa_pasien_coding inner join reg_periksa inner join pasien inner join penyakit "
                    + "on diagnosa_pasien_coding.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and diagnosa_pasien_coding.kd_penyakit=penyakit.kd_penyakit "
                    + "where reg_periksa.no_rawat=? order by diagnosa_pasien_coding.prioritas ASC");
            try {
                ps.setString(1, norawat.trim());
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeDiagnosaPilih.addRow(new Object[]{rs.getString("kd_penyakit"),
                        rs.getString("nm_penyakit"),
                        (rs.getString("prioritas").equals("1") ? "Primary" : "Secondary")});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilProsedurPilih() {
        Valid.tabelKosong(tabModeProsedurPilih);
        try {
            ps = koneksi.prepareStatement("select * "
                    + "from   prosedur_pasien_coding JOIN icd9 ON prosedur_pasien_coding.kode=icd9.kode "
                    + " where no_rawat ='" + norawat + "' order by prosedur_pasien_coding.prioritas ASC ");
            try {

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeProsedurPilih.addRow(new Object[]{rs.getString("kode"),
                        rs.getString("deskripsi_pendek"),
                        (rs.getString("prioritas").equals("1") ? "Primary" : "Secondary")});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilKunjungan() {
        Valid.tabelKosong(tabModeKunjungan);
        if (cmbRiwayatPasien.getSelectedIndex() == 0) {
            limitKunjungan = " LIMIT 5";
        }
        if (cmbRiwayatPasien.getSelectedIndex() == 1) {
            limitKunjungan = " LIMIT 50";
        }
        if (cmbRiwayatPasien.getSelectedIndex() == 2) {
            limitKunjungan = " LIMIT 150";
        }
        if (cmbRiwayatPasien.getSelectedIndex() == 3) {
            limitKunjungan = "";
        }
        try {
            ps = koneksi.prepareStatement("SELECT reg_periksa.no_rawat,reg_periksa.tgl_registrasi,pasien.nm_pasien,poliklinik.nm_poli,penjab.png_jawab,bridging_sep.no_sep,bridging_sep.no_rujukan FROM reg_periksa JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli LEFT JOIN penjab ON reg_periksa.kd_pj=penjab.kd_pj LEFT JOIN bridging_sep ON reg_periksa.no_rawat=bridging_sep.no_rawat where reg_periksa.no_rkm_medis='" + norkmMedis + "' order by reg_periksa.tgl_registrasi DESC " + limitKunjungan + " ");
            try {

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeKunjungan.addRow(new Object[]{
                        rs.getString("no_rawat"),
                        rs.getString("tgl_registrasi"),
                        rs.getString("nm_pasien"),
                        rs.getString("nm_poli"),
                        rs.getString("png_jawab"),
                        rs.getString("no_sep"),
                        rs.getString("no_rujukan")});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

    }
    
    public void setDetailKlaim(String noRawat, String noRkmMedis, String nmPasien, String statusKunjungan) {
        DlgDetailKlaim dlgDetail = new DlgDetailKlaim(null, false);
        dlgDetail.setDataPasien(noRawat, noRkmMedis, nmPasien, statusKunjungan);

        JScrollPane sc1 = new JScrollPane(dlgDetail.getPanel());

        int index = jTabbedPane1.indexOfTab("Detail Klaim");
        if (index != -1) {
            jTabbedPane1.remove(index);
        }

        jTabbedPane1.addTab("Detail Klaim", sc1);
        jTabbedPane1.setSelectedComponent(sc1);
        
        
        this.norawat = noRawat;
        this.noSep = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat='" + noRawat + "'");
        this.norkmMedis = noRkmMedis;
        this.namaPasien = nmPasien;
        this.status = statusKunjungan;
        this.dokter = Sequel.cariIsi("select nm_dokter from reg_periksa JOIN dokter ON reg_periksa.kd_dokter=dokter.kd_dokter where no_rawat='" + noRawat + "'");
        this.tglAwal = Sequel.cariIsi("select concat(tgl_registrasi, ' ',jam_reg) as tglawal from reg_periksa  where no_rawat='" + noRawat + "'");
        this.tglAkhir = Sequel.cariIsi("select concat(tgl_registrasi, ' ',jam_reg) as tglakhir from reg_periksa  where no_rawat='" + noRawat + "'");
        this.jnsKlaim = Sequel.cariIsi("select jnspelayanan from bridging_sep where no_rawat='" + noRawat + "'");
        txtNoSep.setText(noSep);
        txtNoRawat.setText(norawat);
        txtNoRawat.setText(norawat);
        txtNoRm.setText(norkmMedis);
        txtNamaPasien.setText(namaPasien);
        try {
            ps = koneksi.prepareStatement(
                    "select * from pasien where no_rkm_medis='" + noRkmMedis + "'");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    this.tglLahir = rs.getString("tgl_lahir");
                    this.noKartu = rs.getString("no_peserta");
                    this.jkPasien = rs.getString("jk");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

        tampilListDokumen(noRawat);
        tampilDiagnosaPilih();
        tampilProsedurPilih();
        if(Sequel.cariInteger("select count(no_rawat) from tt_status_administrasi where no_rawat='"+norawat+"' ")>0){
            BtnSimpan.setVisible(true);
        }else{
            BtnSimpan.setVisible(false);
        }
    }

}
