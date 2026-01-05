package digital_klaim;

//import bpjsvclaim.BPJSApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;
//import javax.swing.JTableHeader;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import bridging.ApiBPJS;
import fungsi.akses;
import simrskhanza.DlgCariPoli;
import integration_idrg.DlgListKlaim;
import java.awt.event.WindowAdapter;
import java.text.MessageFormat;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
// HAPUS / KOMENTARI INI KALAU ADA
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import javax.swing.table.JTableHeader;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author dosen
 */
public final class DlgManagemenFileKlaim extends javax.swing.JDialog {

    private final DefaultTableModel TabModePasienRalan, TabModePasienRanap;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private String berkas = "", pilihtable = "", kd_pj = "", kd_poli = "", sql = "", URL = "";
    private int i = 0, c = 0;
    private JButton button = new JButton();
    private JButton btnResume = new JButton();
    private JButton btnLaboratorium = new JButton();
    private JButton btnRadiologi = new JButton();
    private JButton btnBillingRajal = new JButton();
    private ApiBPJS api = new ApiBPJS();
    private JScrollPane scrollPane;
    private JPanel topPanel;
    private static ZipOutputStream zos;
    private Path sourceDir;
    private List<String> filesListInDir = new ArrayList<String>();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root, subroot, subroot2, subresponse, subresponse2;
    private JsonNode nameNode;
    private JsonNode response, responsename;
    private DlgCariPoli poli=new DlgCariPoli(null,false);

    //private String[] columns = new String[10];
    //private String[][] data = new String[0][0];
    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgManagemenFileKlaim(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10, 2);
        setSize(1400, 650);
        this.sourceDir = sourceDir;

        Object[] columns = new String[]{
    "P","No Rawat","No RM","Nama Pasien","Poli","No SEP","Tgl SEP",
    "Status Dokumen","Status Koding","Status Kirim Online InaCBG",
"Status Klaim",
    "SEP","Resume IGD","Laboratorium","Radiologi","USG",
    "Billing","Individual","Resume Rawat Jalan","Files Uploads","Triase IGD"
};
        TabModePasienRalan = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
    java.lang.Boolean.class, // 0: P
    java.lang.Object.class,  // 1: No Rawat
    java.lang.Object.class,  // 2: No RM
    java.lang.Object.class,  // 3: Nama Pasien
    java.lang.Object.class,  // 4: Poli
    java.lang.Object.class,  // 5: No SEP
    java.lang.Object.class,  // 6: Tgl SEP
    java.lang.Object.class,  // 7: Status Administrasi
    java.lang.Object.class,  // 8: Status Koding
    java.lang.Object.class,  // 9: Status Kirim Online InaCBG
    java.lang.Object.class,  // 10: Status Klaim
    java.lang.Boolean.class, // 11: SEP
    java.lang.Boolean.class, // 12: Resume IGD
    java.lang.Boolean.class, // 13: Laboratorium
    java.lang.Boolean.class, // 14: Radiologi
    java.lang.Boolean.class, // 15: USG
    java.lang.Boolean.class, // 16: Billing
    java.lang.Boolean.class, // 17: Individual
    java.lang.Boolean.class, // 18: Resume Rawat Jalan
    java.lang.Boolean.class, // 19: Files Uploads
    java.lang.Boolean.class  // 20: Triase IGD
};

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbListPasienRajal.setModel(TabModePasienRalan);
        tbListPasienRajal.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbListPasienRajal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
for (int i = 0; i < tbListPasienRajal.getColumnCount(); i++) {
    TableColumn column = tbListPasienRajal.getColumnModel().getColumn(i);
    if (i == 0) {
        column.setPreferredWidth(20);
    } else if (i == 1) {
        column.setPreferredWidth(110);
    } else if (i == 2) {
        column.setPreferredWidth(60);
    } else if (i == 3) {
        column.setPreferredWidth(300);
    } else if (i == 4) {
        column.setPreferredWidth(150);
    } else if (i == 5) {
        column.setPreferredWidth(125);
    } else if (i == 6) {
        column.setPreferredWidth(80);
    } else if (i == 7) {
        column.setPreferredWidth(120); // Status Administrasi
    } else if (i == 8) {
        column.setPreferredWidth(100); // Status Koding
    } else if (i == 9) {
        column.setPreferredWidth(180); // Status Kirim Online InaCBG
    } else if (i == 10) {
        column.setPreferredWidth(120); // Status Klaim
    } else {
        column.setPreferredWidth(80);
    }
}
        tbListPasienRajal.setDefaultRenderer(Object.class, new WarnaTableDigitalClaim());
        // Tambah checkbox "P" di header untuk centang semua Rawat Jalan
        addHeaderCheckBoxForColumnP(tbListPasienRajal);

Object[] columnsRanap = new String[]{
    "P", "No Rawat", "No RM", "Nama Pasien", "Kamar",
    "No SEP", "Tgl SEP",
    "Status Dokumen",        // 7
    "Status Koding",              // 8
    "Status Kirim Online InaCBG", // 9
    "Status Klaim",               // 10
    "SEP",                        // 11
    "Resume",                     // 12
    "Laborat",                    // 13
    "Radiologi",                  // 14
    "USG",                        // 15
    "Billing",                    // 16
    "Individual"                  // 17
};
        TabModePasienRanap = new DefaultTableModel(null, columnsRanap) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
    java.lang.Boolean.class, // 0: P
    java.lang.Object.class,  // 1: No Rawat
    java.lang.Object.class,  // 2: No RM
    java.lang.Object.class,  // 3: Nama Pasien
    java.lang.Object.class,  // 4: Poli (isi: kamar)
    java.lang.Object.class,  // 5: No SEP
    java.lang.Object.class,  // 6: Tgl SEP
    java.lang.Object.class,  // 7: Status Administrasi
    java.lang.Object.class,  // 8: Status Koding
    java.lang.Object.class,  // 9: Status Kirim Online InaCBG
    java.lang.Object.class,  // 10: Status Klaim
    java.lang.Boolean.class, // 11: SEP
    java.lang.Boolean.class, // 12: Resume
    java.lang.Boolean.class, // 13: Laborat
    java.lang.Boolean.class, // 14: Radiologi
    java.lang.Boolean.class, // 15: USG
    java.lang.Boolean.class, // 16: Billing
    java.lang.Boolean.class  // 17: Individual
};

@Override
public Class getColumnClass(int columnIndex) {
    return types[columnIndex];
}
        };

        tbListPasienRanap.setModel(TabModePasienRanap);
        tbListPasienRanap.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbListPasienRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
for (int i = 0; i < tbListPasienRanap.getColumnCount(); i++) {
    TableColumn column = tbListPasienRanap.getColumnModel().getColumn(i);
    if (i == 0) {
        column.setPreferredWidth(20);
    } else if (i == 1) {
        column.setPreferredWidth(110);
    } else if (i == 2) {
        column.setPreferredWidth(60);
    } else if (i == 3) {
        column.setPreferredWidth(300);
    } else if (i == 4) {
        column.setPreferredWidth(150);
    } else if (i == 5) {
        column.setPreferredWidth(125);
    } else if (i == 6) {
        column.setPreferredWidth(80);
    } else if (i == 7) {
        column.setPreferredWidth(120); // Status Administrasi
    } else if (i == 8) {
        column.setPreferredWidth(100); // Status Koding
    } else if (i == 9) {
        column.setPreferredWidth(180); // Status Kirim Online InaCBG
    } else if (i == 10) {
        column.setPreferredWidth(120); // Status Klaim
    } else {
        column.setPreferredWidth(60);
    }
}

        tbListPasienRanap.setDefaultRenderer(Object.class, new WarnaTableDigitalClaim());
        // Tambah checkbox "P" di header untuk centang semua Rawat Inap
addHeaderCheckBoxForColumnP(tbListPasienRanap);
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgManagementFileKlaim")){
                    if(poli.getTable().getSelectedRow()!= -1){   
                            CrPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                            CrPoli.requestFocus();
                            tampilRalan();
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
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppKodingBerkas = new javax.swing.JMenuItem();
        MnPilihCeklis = new javax.swing.JMenu();
        ppPilihSemua = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        TNoRw = new widget.TextBox();
        KeteranganWarna = new javax.swing.JDialog();
        internalFrame13 = new widget.InternalFrame();
        panelBiasa8 = new widget.PanelBiasa();
        BtnCloseInpindah2 = new widget.Button();
        FormInput1 = new widget.PanelBiasa();
        jTextField5 = new javax.swing.JTextField();
        jLabel67 = new widget.Label();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel44 = new widget.Label();
        jTextField4 = new javax.swing.JTextField();
        jLabel46 = new widget.Label();
        jTextField8 = new javax.swing.JTextField();
        jLabel49 = new widget.Label();
        jTextField9 = new javax.swing.JTextField();
        jLabel50 = new widget.Label();
        jTextField10 = new javax.swing.JTextField();
        jLabel51 = new widget.Label();
        jTextField11 = new javax.swing.JTextField();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jLabel55 = new widget.Label();
        jTextField15 = new javax.swing.JTextField();
        jLabel56 = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        SepTerbit = new widget.Label();
        BtnKetWarna = new widget.Button();
        btnKirimEklaim3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        panelisi5 = new widget.panelisi();
        jLabel7 = new widget.Label();
        DTPTglAwal = new widget.Tanggal();
        jLabel8 = new widget.Label();
        DTPTglAkhir = new widget.Tanggal();
        jLabel11 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        label9 = new widget.Label();
        TCariKunjungan = new widget.TextBox();
        BtnCariTindakan = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnMerger1 = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll2 = new widget.ScrollPane();
        tbListPasienRajal = new widget.Table();
        Scroll1 = new widget.ScrollPane();
        tbListPasienRanap = new widget.Table();

        Popup.setName("Popup"); // NOI18N

        ppKodingBerkas.setBackground(new java.awt.Color(255, 255, 254));
        ppKodingBerkas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppKodingBerkas.setForeground(new java.awt.Color(50, 50, 50));
        ppKodingBerkas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        ppKodingBerkas.setText("Koding Berkas");
        ppKodingBerkas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppKodingBerkas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppKodingBerkas.setName("ppKodingBerkas"); // NOI18N
        ppKodingBerkas.setPreferredSize(new java.awt.Dimension(250, 25));
        ppKodingBerkas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppKodingBerkasActionPerformed(evt);
            }
        });
        Popup.add(ppKodingBerkas);

        MnPilihCeklis.setBackground(new java.awt.Color(250, 255, 245));
        MnPilihCeklis.setForeground(new java.awt.Color(70, 70, 70));
        MnPilihCeklis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPilihCeklis.setText("Pilihan Ceklis");
        MnPilihCeklis.setToolTipText("");
        MnPilihCeklis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPilihCeklis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPilihCeklis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPilihCeklis.setName("MnPilihCeklis"); // NOI18N
        MnPilihCeklis.setPreferredSize(new java.awt.Dimension(310, 26));

        ppPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPilihSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        ppPilihSemua.setText("Centang Semua");
        ppPilihSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilihSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilihSemua.setName("ppPilihSemua"); // NOI18N
        ppPilihSemua.setPreferredSize(new java.awt.Dimension(250, 25));
        ppPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihSemuaActionPerformed(evt);
            }
        });
        MnPilihCeklis.add(ppPilihSemua);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        ppBersihkan.setText("Hilangkan Centang/Tindakan Terpilih");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(250, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        MnPilihCeklis.add(ppBersihkan);

        Popup.add(MnPilihCeklis);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N

        KeteranganWarna.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        KeteranganWarna.setName("KeteranganWarna"); // NOI18N
        KeteranganWarna.setUndecorated(true);
        KeteranganWarna.setResizable(false);
        KeteranganWarna.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                KeteranganWarnaWindowActivated(evt);
            }
        });

        internalFrame13.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 225, 205)), "::[ Keterangan Warna ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        internalFrame13.setAlignmentX(1.0F);
        internalFrame13.setAlignmentY(1.0F);
        internalFrame13.setName("internalFrame13"); // NOI18N
        internalFrame13.setWarnaAtas(new java.awt.Color(0, 51, 102));
        internalFrame13.setWarnaBawah(new java.awt.Color(0, 102, 102));
        internalFrame13.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa8.setName("panelBiasa8"); // NOI18N
        panelBiasa8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        BtnCloseInpindah2.setBackground(new java.awt.Color(255, 51, 0));
        BtnCloseInpindah2.setForeground(new java.awt.Color(255, 255, 255));
        BtnCloseInpindah2.setMnemonic('U');
        BtnCloseInpindah2.setText("Keluar");
        BtnCloseInpindah2.setToolTipText("Alt+U");
        BtnCloseInpindah2.setName("BtnCloseInpindah2"); // NOI18N
        BtnCloseInpindah2.setOpaque(true);
        BtnCloseInpindah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInpindah2ActionPerformed(evt);
            }
        });
        BtnCloseInpindah2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInpindah2KeyPressed(evt);
            }
        });
        panelBiasa8.add(BtnCloseInpindah2);

        internalFrame13.add(panelBiasa8, java.awt.BorderLayout.PAGE_END);

        FormInput1.setBorder(null);
        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(865, 137));
        FormInput1.setLayout(null);

        jTextField5.setEditable(false);
        jTextField5.setToolTipText("");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField5.setName("jTextField5"); // NOI18N
        jTextField5.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField5.setRequestFocusEnabled(false);
        FormInput1.add(jTextField5);
        jTextField5.setBounds(50, 20, 40, 20);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("List Pasien");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput1.add(jLabel67);
        jLabel67.setBounds(100, 20, 110, 23);

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(245, 249, 252));
        jTextField6.setToolTipText("");
        jTextField6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextField6.setName("jTextField6"); // NOI18N
        jTextField6.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField6.setRequestFocusEnabled(false);
        FormInput1.add(jTextField6);
        jTextField6.setBounds(10, 20, 40, 20);

        jTextField7.setEditable(false);
        jTextField7.setBackground(new java.awt.Color(255, 249, 196));
        jTextField7.setToolTipText("");
        jTextField7.setName("jTextField7"); // NOI18N
        jTextField7.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField7.setRequestFocusEnabled(false);
        FormInput1.add(jTextField7);
        jTextField7.setBounds(210, 50, 80, 20);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Belum Ada Dokumen");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput1.add(jLabel44);
        jLabel44.setBounds(100, 50, 110, 23);

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(245, 245, 245));
        jTextField4.setToolTipText("");
        jTextField4.setName("jTextField4"); // NOI18N
        jTextField4.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField4.setRequestFocusEnabled(false);
        FormInput1.add(jTextField4);
        jTextField4.setBounds(10, 80, 80, 20);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Sudah Ada Dokumen");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput1.add(jLabel46);
        jLabel46.setBounds(100, 80, 110, 23);

        jTextField8.setEditable(false);
        jTextField8.setBackground(new java.awt.Color(255, 236, 179));
        jTextField8.setToolTipText("");
        jTextField8.setName("jTextField8"); // NOI18N
        jTextField8.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField8.setRequestFocusEnabled(false);
        FormInput1.add(jTextField8);
        jTextField8.setBounds(10, 110, 80, 20);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Belum Koding");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput1.add(jLabel49);
        jLabel49.setBounds(100, 110, 110, 23);

        jTextField9.setEditable(false);
        jTextField9.setBackground(new java.awt.Color(225, 245, 234));
        jTextField9.setToolTipText("");
        jTextField9.setName("jTextField9"); // NOI18N
        jTextField9.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField9.setRequestFocusEnabled(false);
        FormInput1.add(jTextField9);
        jTextField9.setBounds(10, 140, 80, 20);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("Pending");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput1.add(jLabel50);
        jLabel50.setBounds(300, 50, 100, 23);

        jTextField10.setEditable(false);
        jTextField10.setBackground(new java.awt.Color(245, 124, 0));
        jTextField10.setToolTipText("");
        jTextField10.setName("jTextField10"); // NOI18N
        jTextField10.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField10.setRequestFocusEnabled(false);
        FormInput1.add(jTextField10);
        jTextField10.setBounds(10, 170, 80, 20);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("Sudah Koding");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput1.add(jLabel51);
        jLabel51.setBounds(100, 140, 320, 23);

        jTextField11.setEditable(false);
        jTextField11.setBackground(new java.awt.Color(0, 102, 153));
        jTextField11.setToolTipText("");
        jTextField11.setName("jTextField11"); // NOI18N
        jTextField11.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField11.setRequestFocusEnabled(false);
        FormInput1.add(jTextField11);
        jTextField11.setBounds(10, 200, 80, 20);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Belum Kirim Online InaCBG");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput1.add(jLabel52);
        jLabel52.setBounds(100, 170, 320, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Sudah Kirim Online InaCBG");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput1.add(jLabel53);
        jLabel53.setBounds(100, 200, 320, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Terkirim");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput1.add(jLabel54);
        jLabel54.setBounds(300, 20, 100, 23);

        jTextField12.setEditable(false);
        jTextField12.setBackground(new java.awt.Color(255, 235, 238));
        jTextField12.setToolTipText("");
        jTextField12.setName("jTextField12"); // NOI18N
        jTextField12.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField12.setRequestFocusEnabled(false);
        FormInput1.add(jTextField12);
        jTextField12.setBounds(10, 50, 80, 20);

        jTextField13.setEditable(false);
        jTextField13.setBackground(new java.awt.Color(225, 245, 234));
        jTextField13.setToolTipText("");
        jTextField13.setName("jTextField13"); // NOI18N
        jTextField13.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField13.setRequestFocusEnabled(false);
        FormInput1.add(jTextField13);
        jTextField13.setBounds(210, 20, 80, 20);

        jTextField14.setEditable(false);
        jTextField14.setBackground(new java.awt.Color(245, 245, 245));
        jTextField14.setToolTipText("");
        jTextField14.setName("jTextField14"); // NOI18N
        jTextField14.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField14.setRequestFocusEnabled(false);
        FormInput1.add(jTextField14);
        jTextField14.setBounds(210, 80, 80, 20);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Belum Kirim");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput1.add(jLabel55);
        jLabel55.setBounds(300, 80, 100, 23);

        jTextField15.setEditable(false);
        jTextField15.setBackground(new java.awt.Color(211, 47, 47));
        jTextField15.setToolTipText("");
        jTextField15.setName("jTextField15"); // NOI18N
        jTextField15.setPreferredSize(new java.awt.Dimension(60, 26));
        jTextField15.setRequestFocusEnabled(false);
        FormInput1.add(jTextField15);
        jTextField15.setBounds(210, 110, 80, 20);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Tidak Layak");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput1.add(jLabel56);
        jLabel56.setBounds(300, 110, 100, 23);

        internalFrame13.add(FormInput1, java.awt.BorderLayout.CENTER);

        KeteranganWarna.getContentPane().add(internalFrame13, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Manajemen File Klaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 85));
        panelisi3.setLayout(new java.awt.BorderLayout());

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("SEP Terbit :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label11);

        SepTerbit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SepTerbit.setText("0");
        SepTerbit.setName("SepTerbit"); // NOI18N
        SepTerbit.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi4.add(SepTerbit);

        BtnKetWarna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/satuan.png"))); // NOI18N
        BtnKetWarna.setMnemonic('H');
        BtnKetWarna.setText("Keterangan Warna");
        BtnKetWarna.setToolTipText("Alt+H");
        BtnKetWarna.setName("BtnKetWarna"); // NOI18N
        BtnKetWarna.setPreferredSize(new java.awt.Dimension(170, 30));
        BtnKetWarna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKetWarnaActionPerformed(evt);
            }
        });
        BtnKetWarna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKetWarnaKeyPressed(evt);
            }
        });
        panelisi4.add(BtnKetWarna);

        btnKirimEklaim3.setBackground(new java.awt.Color(0, 102, 102));
        btnKirimEklaim3.setForeground(new java.awt.Color(255, 255, 255));
        btnKirimEklaim3.setMnemonic('2');
        btnKirimEklaim3.setText("Create IDRG");
        btnKirimEklaim3.setToolTipText("Alt+2");
        btnKirimEklaim3.setMargin(new java.awt.Insets(1, 7, 1, 7));
        btnKirimEklaim3.setName("btnKirimEklaim3"); // NOI18N
        btnKirimEklaim3.setOpaque(true);
        btnKirimEklaim3.setPreferredSize(new java.awt.Dimension(190, 25));
        btnKirimEklaim3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimEklaim3ActionPerformed(evt);
            }
        });
        panelisi4.add(btnKirimEklaim3);

        jLabel16.setText("Unit :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi4.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(300, 23));
        panelisi4.add(CrPoli);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelisi4.add(BtnSeek4);

        panelisi3.add(panelisi4, java.awt.BorderLayout.CENTER);

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel7.setText("Tanggal :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(46, 23));
        panelisi5.add(jLabel7);

        DTPTglAwal.setForeground(new java.awt.Color(50, 70, 50));
        DTPTglAwal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-12-2025" }));
        DTPTglAwal.setDisplayFormat("dd-MM-yyyy");
        DTPTglAwal.setName("DTPTglAwal"); // NOI18N
        DTPTglAwal.setOpaque(false);
        DTPTglAwal.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPTglAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglAwalKeyPressed(evt);
            }
        });
        panelisi5.add(DTPTglAwal);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("s/d");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi5.add(jLabel8);

        DTPTglAkhir.setForeground(new java.awt.Color(50, 70, 50));
        DTPTglAkhir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-12-2025" }));
        DTPTglAkhir.setDisplayFormat("dd-MM-yyyy");
        DTPTglAkhir.setName("DTPTglAkhir"); // NOI18N
        DTPTglAkhir.setOpaque(false);
        DTPTglAkhir.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPTglAkhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglAkhirKeyPressed(evt);
            }
        });
        panelisi5.add(DTPTglAkhir);

        jLabel11.setText("Limit Data :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi5.add(jLabel11);

        cmbHlm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "50", "100", "200", "300", "400", "500", "1000", "Semua" }));
        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi5.add(cmbHlm);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi5.add(label9);

        TCariKunjungan.setName("TCariKunjungan"); // NOI18N
        TCariKunjungan.setPreferredSize(new java.awt.Dimension(200, 23));
        TCariKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKunjunganKeyPressed(evt);
            }
        });
        panelisi5.add(TCariKunjungan);

        BtnCariTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariTindakan.setMnemonic('1');
        BtnCariTindakan.setToolTipText("Alt+1");
        BtnCariTindakan.setName("BtnCariTindakan"); // NOI18N
        BtnCariTindakan.setPreferredSize(new java.awt.Dimension(46, 23));
        BtnCariTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariTindakanActionPerformed(evt);
            }
        });
        BtnCariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariTindakanKeyPressed(evt);
            }
        });
        panelisi5.add(BtnCariTindakan);

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
        panelisi5.add(BtnAll);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(46, 23));
        panelisi5.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi5.add(LCount);

        BtnMerger1.setBackground(new java.awt.Color(0, 0, 102));
        BtnMerger1.setForeground(new java.awt.Color(255, 255, 255));
        BtnMerger1.setMnemonic('4');
        BtnMerger1.setText("Download File");
        BtnMerger1.setToolTipText("Alt+4");
        BtnMerger1.setName("BtnMerger1"); // NOI18N
        BtnMerger1.setOpaque(true);
        BtnMerger1.setPreferredSize(new java.awt.Dimension(150, 23));
        BtnMerger1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMerger1ActionPerformed(evt);
            }
        });
        panelisi5.add(BtnMerger1);

        BtnPrint.setBackground(new java.awt.Color(0, 0, 102));
        BtnPrint.setForeground(new java.awt.Color(255, 255, 255));
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Print Data");
        BtnPrint.setToolTipText("Alt+4");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setOpaque(true);
        BtnPrint.setPreferredSize(new java.awt.Dimension(150, 23));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        panelisi5.add(BtnPrint);

        BtnKeluar.setBackground(new java.awt.Color(255, 0, 0));
        BtnKeluar.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setOpaque(true);
        BtnKeluar.setPreferredSize(new java.awt.Dimension(120, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi5.add(BtnKeluar);

        panelisi3.add(panelisi5, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFocusCycleRoot(true);
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll2.setComponentPopupMenu(Popup);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbListPasienRajal.setAutoCreateRowSorter(true);
        tbListPasienRajal.setToolTipText("");
        tbListPasienRajal.setComponentPopupMenu(Popup);
        tbListPasienRajal.setName("tbListPasienRajal"); // NOI18N
        tbListPasienRajal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListPasienRajalMouseClicked(evt);
            }
        });
        tbListPasienRajal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbListPasienRajalKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbListPasienRajal);

        TabRawat.addTab("Rawat Jalan", Scroll2);

        Scroll1.setComponentPopupMenu(Popup);
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbListPasienRanap.setAutoCreateRowSorter(true);
        tbListPasienRanap.setToolTipText("");
        tbListPasienRanap.setComponentPopupMenu(Popup);
        tbListPasienRanap.setName("tbListPasienRanap"); // NOI18N
        tbListPasienRanap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListPasienRanapMouseClicked(evt);
            }
        });
        tbListPasienRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbListPasienRanapKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbListPasienRanap);

        TabRawat.addTab("Rawat Inap", Scroll1);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKunjunganKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariTindakanActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
//            tbListPasienRalan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCariTindakan.requestFocus();
        }
}//GEN-LAST:event_TCariKunjunganKeyPressed

    private void BtnCariTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariTindakanActionPerformed
        if (TabRawat.getSelectedIndex() == 0) {
            tampilRalan();
            getPasienBpjs("Ralan");
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampilRanap();
            getPasienBpjs("Ranap");
        }
}//GEN-LAST:event_BtnCariTindakanActionPerformed
    private void getPasienBpjs(String layanan) {
        String layananBPJS;
        if (layanan.equals("Ralan")) {
            layananBPJS = "2";
        } else {
            layananBPJS = "1";
        }
        int sepTerbit = Sequel.cariInteger("select count(bridging_sep.no_rawat) as total from bridging_sep JOIN reg_periksa ON bridging_sep.no_rawat=reg_periksa.no_rawat where kd_pj='A02' and  status_lanjut='" + layanan + "' and  tglsep BETWEEN '" + Valid.SetTgl(DTPTglAwal.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPTglAkhir.getSelectedItem() + "") + "'");
        int totalReg = Sequel.cariInteger("select count(no_rawat) as total from reg_periksa where kd_pj='A02' and status_lanjut='" + layanan + "' and tgl_registrasi BETWEEN '" + Valid.SetTgl(DTPTglAwal.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPTglAkhir.getSelectedItem() + "") + "'");
        double hasil = (double) sepTerbit / totalReg * 100;
        SepTerbit.setText(sepTerbit + "/" + totalReg + " [" + Valid.SetAngka4(hasil) + "%]");
    }
    private void BtnCariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariTindakanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariTindakanActionPerformed(null);
        } else {
//            Valid.pindah(evt, TCariTindakan, BtnAllTindakan);
        }
}//GEN-LAST:event_BtnCariTindakanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();

    }//GEN-LAST:event_BtnKeluarActionPerformed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    if (TabRawat.getSelectedIndex() == 0) {
        for (i = 0; i < tbListPasienRajal.getRowCount(); i++) {
            tbListPasienRajal.setValueAt(false, i, 0);
        }
    } else if (TabRawat.getSelectedIndex() == 1) {
        for (i = 0; i < tbListPasienRanap.getRowCount(); i++) {
            tbListPasienRanap.setValueAt(false, i, 0);
        }
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        TCariKunjungan.requestFocus();
    }//GEN-LAST:event_formWindowActivated

    private void DTPTglAwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglAwalKeyPressed
//        Valid.pindah(evt,TCariTindakan,cmbJam);
    }//GEN-LAST:event_DTPTglAwalKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// Tambah menu "Update Status Klaim Manual" ke Popup secara dinamis
    try {
        boolean sudahAda = false;
        int compCount = Popup.getComponentCount();
        for (int idx = 0; idx < compCount; idx++) {
            java.awt.Component comp = Popup.getComponent(idx);
            if (comp instanceof javax.swing.JMenuItem) {
                if ("ppUpdateStatusKlaim".equals(comp.getName())) {
                    sudahAda = true;
                    break;
                }
            }
        }

        if (!sudahAda) {
            javax.swing.JMenuItem miUpdateStatus = new javax.swing.JMenuItem("Update Status Klaim Manual");
            miUpdateStatus.setName("ppUpdateStatusKlaim");
            miUpdateStatus.setPreferredSize(new java.awt.Dimension(250, 25));
            miUpdateStatus.setIcon(
                new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))
            );

            miUpdateStatus.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    updateStatusKlaimManual();
                }
            });

            // taruh setelah Koding Berkas (index 1), sebelum "Pilihan Ceklis"
            int indexInsert = 1;
            if (compCount <= indexInsert) {
                Popup.add(miUpdateStatus);
            } else {
                Popup.add(miUpdateStatus, indexInsert);
            }
        }
    } catch (Exception ex) {
        System.out.println("Notif tambah menu Update Status Klaim : " + ex);
    }
    }//GEN-LAST:event_formWindowOpened

    private void DTPTglAkhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglAkhirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPTglAkhirKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 0) {
            tampilRalan();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampilRanap();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbListPasienRanapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListPasienRanapMouseClicked
        if (TabModePasienRanap.getRowCount() != 0) {
            if (evt.getClickCount() == 2) {
                i = tbListPasienRanap.getSelectedColumn();
                if (i == 1) {
                    berkas = "Ranap";
                    ppKodingBerkasActionPerformed(null);
                }
            }

        }
    }//GEN-LAST:event_tbListPasienRanapMouseClicked

    private void tbListPasienRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbListPasienRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbListPasienRanapKeyPressed

    private void tbListPasienRajalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListPasienRajalMouseClicked
        if (TabModePasienRalan.getRowCount() != 0) {
            if (evt.getClickCount() == 2) {
                i = tbListPasienRajal.getSelectedColumn();
                if (i == 1) {
                    berkas = "Ralan";
                    ppKodingBerkasActionPerformed(null);
                }
            }

        }
    }//GEN-LAST:event_tbListPasienRajalMouseClicked

    private void tbListPasienRajalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbListPasienRajalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbListPasienRajalKeyPressed

    private void BtnMerger1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMerger1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String tglAwal = DTPTglAwal.getSelectedItem().toString().replaceAll("-", "");
        String tglAkhir = DTPTglAkhir.getSelectedItem().toString().replaceAll("-", "");
        if (TabRawat.getSelectedIndex() == 0) {
            mergerFile();
            downloadFile("File_Klaim_Rawat_Jalan_period_" + tglAwal + "_sd_" + tglAkhir);
        } else if (TabRawat.getSelectedIndex() == 1) {
            mergerFile();
            downloadFile("File_Klaim_Rawat_Inap_period_" + tglAwal + "_sd_" + tglAkhir);
        }

        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnMerger1ActionPerformed

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihSemuaActionPerformed
        if (TabRawat.getSelectedIndex() == 0) {
            for (i = 0; i < tbListPasienRajal.getRowCount(); i++) {
                tbListPasienRajal.setValueAt(true, i, 0);
            }
        } else if (TabRawat.getSelectedIndex() == 1) {
            for (i = 0; i < tbListPasienRanap.getRowCount(); i++) {
                tbListPasienRanap.setValueAt(true, i, 0);
            }
        }
    }//GEN-LAST:event_ppPilihSemuaActionPerformed

    private void ppKodingBerkasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppKodingBerkasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (TabRawat.getSelectedIndex() == 0) {
            if (TabModePasienRalan.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                TCariKunjungan.requestFocus();
            } else {
                ViewerKoding form = new ViewerKoding(null, false);
                form.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {

                        BtnCariTindakanActionPerformed(null);
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
                form.setSize(this.getWidth(), this.getHeight() + 20);
                form.setDataPasien(tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 1).toString(), tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 2).toString(), tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 3).toString(), "Ralan");
                form.setLocationRelativeTo(this);
                form.setVisible(true);

            }
        } else if (TabRawat.getSelectedIndex() == 1) {
            if (TabModePasienRanap.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                TCariKunjungan.requestFocus();
            } else {
                ViewerKoding form = new ViewerKoding(null, false);
                form.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {

                        BtnCariTindakanActionPerformed(null);
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
                form.setSize(this.getWidth(), this.getHeight() + 20);
                form.setDataPasien(tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 1).toString(), tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 2).toString(), tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 3).toString(), "Ranap");
                form.setLocationRelativeTo(this);
                form.setVisible(true);
            }
        }

        if (TabRawat.getSelectedIndex() == 0) {
            if (TabModePasienRalan.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                TCariKunjungan.requestFocus();
            } else {
                ViewerKoding form = new ViewerKoding(null, false);

                form.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        BtnCariTindakanActionPerformed(null);
                    }
                });

                // KIRIM DATA PASIEN KE VIEWERKODING
                form.setDetailKlaim(
                    tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 1).toString(),
                    tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 2).toString(),
                    tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 3).toString(),
                    "Ralan"
                );

                form.setSize(this.getWidth(), this.getHeight() + 20);
                form.setLocationRelativeTo(this);
                form.setVisible(true);
            }
        } else if (TabRawat.getSelectedIndex() == 1) {

            if (TabModePasienRanap.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                TCariKunjungan.requestFocus();
            } else {

                ViewerKoding form = new ViewerKoding(null, false);

                form.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        BtnCariTindakanActionPerformed(null);
                    }
                });

                // KIRIM DATA PASIEN KE VIEWERKODING
                form.setDetailKlaim(
                    tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 1).toString(),
                    tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 2).toString(),
                    tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 3).toString(),
                    "Ranap"
                );

                form.setSize(this.getWidth(), this.getHeight() + 20);
                form.setLocationRelativeTo(this);
                form.setVisible(true);
            }
        }

        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppKodingBerkasActionPerformed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        akses.setform("DlgManagementFileKlaim");
//        pilihan=2;
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        CrPoli.setText("");
    TCariKunjungan.setText("");
    
    if (TabRawat.getSelectedIndex() == 0) {
        tampilRalan();
    } else if (TabRawat.getSelectedIndex() == 1) {
        tampilRanap();
    }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCariTindakan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void btnKirimEklaim3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimEklaim3ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        isTutup();
        DlgListKlaim form = new DlgListKlaim(null,false);
//        form.emptTeks();
//        form.isCek();
        form.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKirimEklaim3ActionPerformed

    private void BtnKetWarnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKetWarnaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        KeteranganWarna.setSize(800, 400);
        KeteranganWarna.setLocationRelativeTo(internalFrame1);
        KeteranganWarna.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnKetWarnaActionPerformed

    private void BtnKetWarnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKetWarnaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKetWarnaKeyPressed

    private void BtnCloseInpindah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInpindah2ActionPerformed
        KeteranganWarna.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCloseInpindah2ActionPerformed

    private void BtnCloseInpindah2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInpindah2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCloseInpindah2KeyPressed

    private void KeteranganWarnaWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_KeteranganWarnaWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganWarnaWindowActivated

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
        // Dialog pilihan: Print / Export Excel / Batal
        Object[] options = {"Print", "Export Excel", "Batal"};
        int pilih = JOptionPane.showOptionDialog(
                this,
                "Pilih aksi yang ingin dilakukan:",
                "Print / Export",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (pilih == JOptionPane.CANCEL_OPTION || pilih == -1) {
            // batal
            return;
        }

        // Periode untuk judul
        String periode = DTPTglAwal.getSelectedItem() + " s/d " + DTPTglAkhir.getSelectedItem();

        if (pilih == JOptionPane.YES_OPTION) {
            // =============== MODE PRINT ================
            try {
                if (TabRawat.getSelectedIndex() == 0) {
                    // RAWAT JALAN
                    if (tbListPasienRajal.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Tidak ada data Rawat Jalan yang bisa dicetak.");
                        return;
                    }

                    MessageFormat header = new MessageFormat(
                        "Daftar Manajemen File Klaim Rawat Jalan - Periode " + periode
                    );
                    MessageFormat footer = new MessageFormat("Halaman {0}");

                    tbListPasienRajal.print(
                        javax.swing.JTable.PrintMode.FIT_WIDTH,
                        header,
                        footer
                    );

                } else if (TabRawat.getSelectedIndex() == 1) {
                    // RAWAT INAP
                    if (tbListPasienRanap.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Tidak ada data Rawat Inap yang bisa dicetak.");
                        return;
                    }

                    MessageFormat header = new MessageFormat(
                        "Daftar Manajemen File Klaim Rawat Inap - Periode " + periode
                    );
                    MessageFormat footer = new MessageFormat("Halaman {0}");

                    tbListPasienRanap.print(
                        javax.swing.JTable.PrintMode.FIT_WIDTH,
                        header,
                        footer
                    );
                }
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, "Gagal mencetak data.\n" + ex.getMessage());
                System.out.println("Notif Print : " + ex);
            }

        } else if (pilih == JOptionPane.NO_OPTION) {
            // ================ MODE EXPORT EXCEL ================
            if (TabRawat.getSelectedIndex() == 0) {
                if (tbListPasienRajal.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Tidak ada data Rawat Jalan yang bisa diexport.");
                    return;
                }
                String title = "Manajemen File Klaim Rawat Jalan - Periode " + periode;
                exportTableToExcelXlsx(tbListPasienRajal, title);  // <-- CUMA 2 PARAMETER

            } else if (TabRawat.getSelectedIndex() == 1) {
                if (tbListPasienRanap.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Tidak ada data Rawat Inap yang bisa diexport.");
                    return;
                }
                String title = "Manajemen File Klaim Rawat Inap - Periode " + periode;
                exportTableToExcelXlsx(tbListPasienRanap, title);  // <-- CUMA 2 PARAMETER
            }
        }

    } finally {
        this.setCursor(Cursor.getDefaultCursor());
    }
    }//GEN-LAST:event_BtnPrintActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgManagemenFileKlaim dialog = new DlgManagemenFileKlaim(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCariTindakan;
    private widget.Button BtnCloseInpindah2;
    private widget.Button BtnKeluar;
    private widget.Button BtnKetWarna;
    private widget.Button BtnMerger1;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek4;
    private widget.TextBox CrPoli;
    private widget.Tanggal DTPTglAkhir;
    private widget.Tanggal DTPTglAwal;
    private widget.PanelBiasa FormInput1;
    private javax.swing.JDialog KeteranganWarna;
    private widget.Label LCount;
    private javax.swing.JMenu MnPilihCeklis;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.Label SepTerbit;
    private widget.TextBox TCariKunjungan;
    private widget.TextBox TNoRw;
    private javax.swing.JTabbedPane TabRawat;
    public widget.Button btnKirimEklaim3;
    private widget.ComboBox cmbHlm;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame13;
    private widget.Label jLabel11;
    private widget.Label jLabel16;
    private widget.Label jLabel44;
    private widget.Label jLabel46;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel67;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label9;
    private widget.PanelBiasa panelBiasa8;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppKodingBerkas;
    private javax.swing.JMenuItem ppPilihSemua;
    private widget.Table tbListPasienRajal;
    private widget.Table tbListPasienRanap;
    // End of variables declaration//GEN-END:variables

    
    // Helper: convert nilai flag (true/false, 1/0, Ya/Tidak, dll) jadi boolean
private boolean isTrueFlag(String value) {
    if (value == null) {
        return false;
    }
    String v = value.trim();
    return v.equalsIgnoreCase("true")
            || v.equals("1")
            || v.equalsIgnoreCase("ya")
            || v.equalsIgnoreCase("sudah");
}
    private void tampilRalan() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            String hal, statusDatang;
            if (cmbHlm.getSelectedItem().equals("Semua")) {
                hal = "";
            } else {
                hal = " limit " + cmbHlm.getSelectedItem() + "";
            }
            Valid.tabelKosong(TabModePasienRalan);
            sql = "select * from reg_periksa JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli JOIN bridging_sep ON reg_periksa.no_rawat=bridging_sep.no_rawat  where status_lanjut='Ralan'  and  tgl_registrasi BETWEEN ? and ? and (reg_periksa.no_rawat like ? or pasien.nm_pasien like ? or bridging_sep.no_sep like ? or bridging_sep.no_rujukan like ?) and poliklinik.nm_poli like ?" + hal + " ";
            ps = koneksi.prepareStatement(sql);

            try {
                ps.setString(1, Valid.SetTgl(DTPTglAwal.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPTglAkhir.getSelectedItem() + ""));
                ps.setString(3, "%" + TCariKunjungan.getText() + "%");
                ps.setString(4, "%" + TCariKunjungan.getText() + "%");
                ps.setString(5, "%" + TCariKunjungan.getText() + "%");
                ps.setString(6, "%" + TCariKunjungan.getText() + "%");
                 ps.setString(7, "%" + CrPoli.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    String noSep, tglSep, StatusAdmin, StatusKoding, StatusKirim;
                    boolean fileSep, fileResume, fileLaboratorium, fileRadiologi, fileBilling, fileindividual, fileusg, fileInacbg, fileUploads, fileTriase;
                    int sep = Sequel.cariInteger(
        "select count(no_rawat) as total from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'"
);

String StatusKirimOnline;
String StatusKlaim;

if (sep > 0) {
    noSep  = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'");
    tglSep = Sequel.cariIsi("select tglsep from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'");

    int jmlStatus = Sequel.cariInteger(
        "select count(*) from tt_status_eklaim where no_rawat='" + rs.getString("no_rawat") + "'"
    );

    if (jmlStatus == 0) {
        // Tidak ada data eklaim
        StatusKirimOnline = "belum kirim";
        StatusKlaim       = "belum kirim";

    } else {

        // ===== kirim_online =====
        String kirimOnlineRaw = Sequel.cariIsi(
                "select kirim_online from tt_status_eklaim where no_rawat='" + rs.getString("no_rawat") + "'"
        ).trim().toLowerCase();

        if (kirimOnlineRaw.equals("true") || kirimOnlineRaw.equals("1")) {
            StatusKirimOnline = "sudah kirim";
        } else {
            StatusKirimOnline = "belum kirim";
        }

        // ===== status_klaim =====
        String klaimRaw = Sequel.cariIsi(
                "select status_klaim from tt_status_eklaim where no_rawat='" + rs.getString("no_rawat") + "'"
        ).trim().toLowerCase();

        if (klaimRaw.equals("") || klaimRaw.equals("-") || klaimRaw.equals("null")) {
            StatusKlaim = "belum kirim";

        } else if (klaimRaw.equals("pending")) {
            StatusKlaim = "pending";

        } else if (klaimRaw.equals("tidak layak")) {
            StatusKlaim = "tidak layak";

        } else if (klaimRaw.equals("terkirim")) {
            StatusKlaim = "terkirim";

        } else if (klaimRaw.equals("perbaiki")) {
            StatusKlaim = "perbaiki";

        } else {
            StatusKlaim = klaimRaw; // fallback
        }
    }

} else {
    // Tidak ada SEP sama sekali
    noSep             = "-";
    tglSep            = "-";
    StatusKirimOnline = "belum kirim";
    StatusKlaim       = "belum kirim";
}
                    int berkassep = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='sep' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkassep > 0) {
                        fileSep = true;

                    } else {
                        fileSep = false;

                    }
                    int berkasresume = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='resume' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasresume > 0) {
                        fileResume = true;
                    } else {
                        fileResume = false;
                    }
                    int berkaslaboratorium = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='laboratorium' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkaslaboratorium > 0) {
                        fileLaboratorium = true;
                    } else {
                        fileLaboratorium = false;
                    }
                    int berkasradiologi = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='radiologi' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasradiologi > 0) {
                        fileRadiologi = true;
                    } else {
                        fileRadiologi = false;
                    }
                    int berkasbilling = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='billing' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasbilling > 0) {
                        fileBilling = true;
                    } else {
                        fileBilling = false;
                    }
                    int berkasindifidual = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='data_individual' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasindifidual > 0) {
                        fileindividual = true;
                    } else {
                        fileindividual = false;
                    }
                    int berkasUsg = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='usg' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasUsg > 0) {
                        fileusg = true;
                    } else {
                        fileusg = false;
                    }
                    int berkasinacbg = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='resumeralan' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasinacbg > 0) {
                        fileInacbg = true;
                    } else {
                        fileInacbg = false;
                    }
                    int berkasdigital = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='berkas_digitalkeperawatan' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasdigital > 0) {
                        fileUploads = true;
                    } else {
                        fileUploads = false;
                    }
                    int berkastriase = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='triase' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkastriase > 0) {
                        fileTriase = true;
                    } else {
                        fileTriase = false;
                    }

                    // Status koding ikut final_klaim di tt_status_eklaim
String finalKlaimFlag = Sequel.cariIsi(
        "select final_klaim from tt_status_eklaim where no_rawat='" + rs.getString("no_rawat") + "'"
);

if (isTrueFlag(finalKlaimFlag)) {
    StatusKoding = "Sudah Koding";
} else {
    StatusKoding = "Belum Koding";
}
                    
                    int statusAdmin = Sequel.cariInteger("select count(no_rawat) as total from tt_status_administrasi where no_rawat='" + rs.getString("no_rawat") + "'");
                    if (statusAdmin > 0) {
                        StatusAdmin = Sequel.cariIsi("select status from tt_status_administrasi where no_rawat='" + rs.getString("no_rawat") + "'");
                    } else {
                        StatusAdmin = "Belum Ada Dokumen";
                    }

                    TabModePasienRalan.addRow(new Object[]{
    false,
    rs.getString("no_rawat"),
    rs.getString("no_rkm_medis"),
    rs.getString("nm_pasien"),
    rs.getString("nm_poli"),
    noSep,
    tglSep,
    StatusAdmin,       // 7
    StatusKoding,      // 8
    StatusKirimOnline, // 9
    StatusKlaim,       // 10
    fileSep,           // 11
    fileResume,        // 12
    fileLaboratorium,  // 13
    fileRadiologi,     // 14
    fileusg,           // 15
    fileBilling,       // 16
    fileindividual,    // 17
    fileInacbg,        // 18
    fileUploads,       // 19
    fileTriase         // 20
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
        LCount.setText("" + tbListPasienRajal.getRowCount());
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void tampilRanap() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
        Valid.tabelKosong(TabModePasienRanap);
        sql = "select * from reg_periksa "
            + "JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
            + "JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli "
            + "JOIN bridging_sep ON reg_periksa.no_rawat=bridging_sep.no_rawat  "
            + "where status_lanjut='Ranap' and  tgl_registrasi BETWEEN ? and ? "
            + "and (reg_periksa.no_rawat like ? or pasien.nm_pasien like ? "
            + "or bridging_sep.no_sep like ? or bridging_sep.no_rujukan like ?) ";
        ps = koneksi.prepareStatement(sql);
        try {
            ps.setString(1, Valid.SetTgl(DTPTglAwal.getSelectedItem() + ""));
            ps.setString(2, Valid.SetTgl(DTPTglAkhir.getSelectedItem() + ""));
            ps.setString(3, "%" + TCariKunjungan.getText() + "%");
            ps.setString(4, "%" + TCariKunjungan.getText() + "%");
            ps.setString(5, "%" + TCariKunjungan.getText() + "%");
            ps.setString(6, "%" + TCariKunjungan.getText() + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                String noSep, tglSep, kamar;
                String StatusAdmin, StatusKoding, StatusKirim;
                boolean fileSep, fileResume, fileLaboratorium, fileRadiologi, fileBilling, fileindividual, fileusg;

kamar = Sequel.cariIsi(
    "select kamar_inap.kd_kamar from kamar_inap " +
    "where kamar_inap.no_rawat='" + rs.getString("no_rawat") + "' " +
    "order by kamar_inap.tgl_keluar DESC limit 1"
);

                int sep = Sequel.cariInteger(
        "select count(no_rawat) as total from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'"
);

String StatusKirimOnline;
String StatusKlaim;

if (sep > 0) {
    noSep  = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'");
    tglSep = Sequel.cariIsi("select tglsep from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'");

    int jmlStatus = Sequel.cariInteger(
        "select count(*) from tt_status_eklaim where no_rawat='" + rs.getString("no_rawat") + "'"
    );

    if (jmlStatus == 0) {
        // Tidak ada data eklaim
        StatusKirimOnline = "belum kirim";
        StatusKlaim       = "belum kirim";

    } else {

        // ===== kirim_online =====
        String kirimOnlineRaw = Sequel.cariIsi(
                "select kirim_online from tt_status_eklaim where no_rawat='" + rs.getString("no_rawat") + "'"
        ).trim().toLowerCase();

        if (kirimOnlineRaw.equals("true") || kirimOnlineRaw.equals("1")) {
            StatusKirimOnline = "sudah kirim";
        } else {
            StatusKirimOnline = "belum kirim";
        }

        // ===== status_klaim =====
        String klaimRaw = Sequel.cariIsi(
                "select status_klaim from tt_status_eklaim where no_rawat='" + rs.getString("no_rawat") + "'"
        ).trim().toLowerCase();

        if (klaimRaw.equals("") || klaimRaw.equals("-") || klaimRaw.equals("null")) {
            StatusKlaim = "belum kirim";

        } else if (klaimRaw.equals("pending")) {
            StatusKlaim = "pending";

        } else if (klaimRaw.equals("tidak layak")) {
            StatusKlaim = "tidak layak";

        } else if (klaimRaw.equals("terkirim")) {
            StatusKlaim = "terkirim";

        } else if (klaimRaw.equals("perbaiki")) {
            StatusKlaim = "perbaiki";

        } else {
            StatusKlaim = klaimRaw; // fallback
        }
    }

} else {
    // Tidak ada SEP sama sekali
    noSep             = "-";
    tglSep            = "-";
    StatusKirimOnline = "belum kirim";
    StatusKlaim       = "belum kirim";
}

                int berkassep = Sequel.cariInteger(
                    "select count(no_rawat) as total from tt_berkasdigital "
                    + "where jenis_file='sep' and no_rawat='" + rs.getString("no_rawat") + "'"
                );
                fileSep = berkassep > 0;

                int berkasresume = Sequel.cariInteger(
                    "select count(no_rawat) as total from tt_berkasdigital "
                    + "where jenis_file='resume' and no_rawat='" + rs.getString("no_rawat") + "'"
                );
                fileResume = berkasresume > 0;

                int berkaslaboratorium = Sequel.cariInteger(
                    "select count(no_rawat) as total from tt_berkasdigital "
                    + "where jenis_file='laboratorium' and no_rawat='" + rs.getString("no_rawat") + "'"
                );
                fileLaboratorium = berkaslaboratorium > 0;

                int berkasradiologi = Sequel.cariInteger(
                    "select count(no_rawat) as total from tt_berkasdigital "
                    + "where jenis_file='radiologi' and no_rawat='" + rs.getString("no_rawat") + "'"
                );
                fileRadiologi = berkasradiologi > 0;

                int berkasbilling = Sequel.cariInteger(
                    "select count(no_rawat) as total from tt_berkasdigital "
                    + "where jenis_file='billing' and no_rawat='" + rs.getString("no_rawat") + "'"
                );
                fileBilling = berkasbilling > 0;

                int berkasindifidual = Sequel.cariInteger(
                    "select count(no_rawat) as total from tt_berkasdigital "
                    + "where jenis_file='data_individual' and no_rawat='" + rs.getString("no_rawat") + "'"
                );
                fileindividual = berkasindifidual > 0;

                int berkasUsg = Sequel.cariInteger(
                    "select count(no_rawat) as total from tt_berkasdigital "
                    + "where jenis_file='usg' and no_rawat='" + rs.getString("no_rawat") + "'"
                );
                fileusg = berkasUsg > 0;

                // Status koding ikut final_klaim di tt_status_eklaim
                String finalKlaimFlag = Sequel.cariIsi(
                    "select final_klaim from tt_status_eklaim where no_rawat='" + rs.getString("no_rawat") + "'"
                );
                if (isTrueFlag(finalKlaimFlag)) {
                    StatusKoding = "Sudah Koding";
                } else {
                    StatusKoding = "Belum Koding";
                }

                // ====== Status Administrasi (sama kayak tampilRalan) ======
                int statusAdmin = Sequel.cariInteger(
                    "select count(no_rawat) as total from tt_status_administrasi "
                    + "where no_rawat='" + rs.getString("no_rawat") + "'"
                );
                if (statusAdmin > 0) {
                    StatusAdmin = Sequel.cariIsi(
                        "select status from tt_status_administrasi "
                        + "where no_rawat='" + rs.getString("no_rawat") + "'"
                    );
                } else {
                    StatusAdmin = "Belum Ada Dokumen";
                }

TabModePasienRanap.addRow(new Object[]{
    false,                        // 0: P
    rs.getString("no_rawat"),     // 1
    rs.getString("no_rkm_medis"), // 2
    rs.getString("nm_pasien"),    // 3
    kamar,                        // 4
    noSep,                        // 5
    tglSep,                       // 6
    StatusAdmin,                  // 7
    StatusKoding,                 // 8
    StatusKirimOnline,            // 9
    StatusKlaim,                  // 10
    fileSep,                      // 11
    fileResume,                   // 12
    fileLaboratorium,             // 13
    fileRadiologi,                // 14
    fileusg,                      // 15
    fileBilling,                  // 16
    fileindividual                // 17
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
    LCount.setText("" + tbListPasienRanap.getRowCount());
    this.setCursor(Cursor.getDefaultCursor());
}
    // Checkbox di header kolom "P" untuk centang semua / hapus semua
// Checkbox di header kolom "P" untuk centang semua / hapus semua
private void addHeaderCheckBoxForColumnP(final JTable table) {
    // Ambil header & kolom pertama (index 0 = kolom "P")
    final JTableHeader header = table.getTableHeader();
    final TableColumn columnP = table.getColumnModel().getColumn(0);

    // Komponen checkbox yang akan tampil di header
    final JCheckBox headerCheckBox = new JCheckBox("P");
    headerCheckBox.setHorizontalAlignment(SwingConstants.CENTER);

    // Renderer untuk menampilkan checkbox di header kolom P
    columnP.setHeaderRenderer(new TableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable tbl, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int col) {
            headerCheckBox.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            return headerCheckBox;
        }
    });

    // Mouse listener untuk klik di header kolom P
    header.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int viewColumn = header.columnAtPoint(e.getPoint());
            int modelColumn = table.convertColumnIndexToModel(viewColumn);

            // Hanya respon jika kolom yang diklik adalah kolom P (index model 0)
            if (modelColumn == 0) {
                boolean selectAll = !headerCheckBox.isSelected();
                headerCheckBox.setSelected(selectAll);

                // Set semua baris di kolom P menjadi true/false sesuai checkbox header
                for (int row = 0; row < table.getRowCount(); row++) {
                    table.setValueAt(selectAll, row, 0);
                }

                header.repaint();
            }
        }
    });
}
    
private void updateStatusKlaimManual() {
    int row = -1;
    String noRawat = "";
    String noSep   = "";

    // Tentukan baris & sumber tabel (Ralan atau Ranap)
    if (TabRawat.getSelectedIndex() == 0) { // Rawat Jalan
        row = tbListPasienRajal.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Silakan pilih pasien dulu di tabel Rawat Jalan.");
            return;
        }
        noRawat = tbListPasienRajal.getValueAt(row, 1).toString();
        noSep   = tbListPasienRajal.getValueAt(row, 5).toString();
    } else { // Rawat Inap
        row = tbListPasienRanap.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Silakan pilih pasien dulu di tabel Rawat Inap.");
            return;
        }
        noRawat = tbListPasienRanap.getValueAt(row, 1).toString();
        noSep   = tbListPasienRanap.getValueAt(row, 5).toString();
    }

    if (noSep == null || noSep.trim().equals("-") || noSep.trim().equals("")) {
        JOptionPane.showMessageDialog(null, "No. SEP belum ada, tidak bisa update status klaim.");
        return;
    }

    // Pastikan sudah ada record di tt_status_eklaim
    int adaStatus = Sequel.cariInteger(
        "SELECT COUNT(no_sep) AS total FROM tt_status_eklaim WHERE no_sep='" + noSep + "'"
    );
    if (adaStatus == 0) {
        JOptionPane.showMessageDialog(null,
            "Data di tt_status_eklaim untuk SEP ini belum ada.\n"
          + "Silakan proses kirim eklaim dulu, atau buat datanya di modul terkait.");
        return;
    }

    // Ambil status_klaim yang sekarang (kalau ada)
    String statusSaatIni = Sequel.cariIsi(
        "SELECT status_klaim FROM tt_status_eklaim WHERE no_sep='" + noSep + "'"
    );

    // Pilihan status manual (bisa kamu ubah teksnya sesuai kebutuhan)
Object[] pilihanStatus = new Object[]{
    "belum kirim",
    "pending",
    "tidak layak",
    "terkirim"
};

Object defaultOption = "pending";
    if (statusSaatIni != null && !statusSaatIni.trim().equals("")) {
        for (Object opt : pilihanStatus) {
            if (opt.toString().equalsIgnoreCase(statusSaatIni.trim())) {
                defaultOption = opt;
                break;
            }
        }
    }

    // Tampilkan "form kecil" pakai JOptionPane
    String hasil = (String) JOptionPane.showInputDialog(
        this,
        "Pilih status klaim manual untuk SEP:\n" + noSep,
        "Update Status Klaim Manual",
        JOptionPane.QUESTION_MESSAGE,
        null,
        pilihanStatus,
        defaultOption
    );

    if (hasil == null) {
        // user cancel
        return;
    }

    hasil = hasil.trim();

    // Update ke tt_status_eklaim
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
        Sequel.mengedit(
            "tt_status_eklaim",
            "no_sep='" + noSep + "'",
            "status_klaim='" + hasil + "'"
        );

        // Refresh tampilan tabel
        BtnCariTindakanActionPerformed(null);

        JOptionPane.showMessageDialog(null, "Status klaim berhasil diupdate menjadi : " + hasil);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Gagal update status klaim.\n" + ex.getMessage());
        System.out.println("Notif updateStatusKlaimManual : " + ex);
    } finally {
        this.setCursor(Cursor.getDefaultCursor());
    }
}
    public void isCek() {
//        BtnTambahTindakan.setEnabled(akses.gettarif_ralan());
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Show SEP" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "Show SEP" : value.toString();
            button.setText(label);
            return button;
        }

        public Object getCellEditorValue() {
            return new String(label);
        }
    }

    class ButtonRendererResume extends JButton implements TableCellRenderer {

        public ButtonRendererResume() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table2, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Show Resume" : value.toString());
            return this;
        }

    }

    class ButtonResume extends DefaultCellEditor {

        private String labelResume;

        public ButtonResume(JCheckBox checkBox2) {
            super(checkBox2);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            labelResume = (value == null) ? "Show Resume" : value.toString();
            btnResume.setText(labelResume);
            return btnResume;
        }

        public Object getCellEditorValue() {
            return new String(labelResume);
        }
    }

    class ButtonRendererLaboratorium extends JButton implements TableCellRenderer {

        public ButtonRendererLaboratorium() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table2, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Show Laboratorium" : value.toString());
            return this;
        }

    }

    class ButtonLaboratorium extends DefaultCellEditor {

        private String labelLaboratorium;

        public ButtonLaboratorium(JCheckBox checkBox2) {
            super(checkBox2);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            labelLaboratorium = (value == null) ? "Show Laboratorium" : value.toString();
            btnLaboratorium.setText(labelLaboratorium);
            return btnLaboratorium;
        }

        public Object getCellEditorValue() {
            return new String(labelLaboratorium);
        }
    }

    class ButtonRendererRadiologi extends JButton implements TableCellRenderer {

        public ButtonRendererRadiologi() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table2, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Show Radiologi" : value.toString());
            return this;
        }

    }

    class ButtonRadiologi extends DefaultCellEditor {

        private String labelRadiologi;

        public ButtonRadiologi(JCheckBox checkBox2) {
            super(checkBox2);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            labelRadiologi = (value == null) ? "Show Radiologi" : value.toString();
            btnRadiologi.setText(labelRadiologi);
            return btnRadiologi;
        }

        public Object getCellEditorValue() {
            return new String(labelRadiologi);
        }
    }

    class ButtonRendererBillingRajal extends JButton implements TableCellRenderer {

        public ButtonRendererBillingRajal() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table2, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Show Billing Rajal" : value.toString());
            return this;
        }

    }

    class ButtonBillingRajal extends DefaultCellEditor {

        private String labelBillingRajal;

        public ButtonBillingRajal(JCheckBox checkBox2) {
            super(checkBox2);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            labelBillingRajal = (value == null) ? "Show Billing Rajal" : value.toString();
            btnBillingRajal.setText(labelBillingRajal);
            return btnBillingRajal;
        }

        public Object getCellEditorValue() {
            return new String(labelBillingRajal);
        }
    }

    private void openFile(String FileName) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgViewPdfDigitalClaim berkas = new DlgViewPdfDigitalClaim(null, true);
        berkas.tampilPdf(FileName, "berkasdigital");
        berkas.setButton(false);
        berkas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        berkas.setLocationRelativeTo(internalFrame1);
        berkas.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }

    class ZipFiles {

        private void populateFilesList(File dir) throws IOException {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    filesListInDir.add(file.getAbsolutePath());
                } else {
                    populateFilesList(file);
                }
            }
        }

        private void zipDirectory(File dir, String zipDirName) {
            try {
                populateFilesList(dir);
                FileOutputStream fos = new FileOutputStream(zipDirName);
                ZipOutputStream zos = new ZipOutputStream(fos);
                for (String filePath : filesListInDir) {
                    ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
                    zos.putNextEntry(ze);
                    FileInputStream fis = new FileInputStream(filePath);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    zos.closeEntry();
                    fis.close();
                }
                zos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                filesListInDir.clear(); // Clear the files 
            }
        }
    }

    void deleteFile(String pathFile) {
        File file = new File(pathFile);
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }

    void deleteDir(String destination) throws IOException {
        FileUtils.deleteDirectory(new File(destination));
//    File[] contents = file.listFiles();
//    if (contents != null) {
//        for (File f : contents) {
//            if (! Files.isSymbolicLink(f.toPath())) {
//                deleteDir(f);
//            }
//        }
//    }
//    file.delete();
    }

    private void mergerFile() {
        try {
            deleteDir("berkasklaim");
            deleteDir("berkasklaimzip");
        } catch (IOException ex) {
            Logger.getLogger(DlgManagemenFileKlaim.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar start = Calendar.getInstance();
        start.setTime(DTPTglAwal.getDate());
        Calendar end = Calendar.getInstance();
        end.setTime(DTPTglAkhir.getDate());
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            new File("berkasklaim").mkdirs();
            if (TabRawat.getSelectedIndex() == 0) {
                for (i = 0; i < tbListPasienRajal.getRowCount(); i++) {
                    if (tbListPasienRajal.getValueAt(i, 0).toString().equals("true")) {
                        if (!tbListPasienRajal.getValueAt(i, 5).toString().equals("-")) {
                            if (tbListPasienRajal.getValueAt(i, 6).toString().equals(formattedDate)) {
                                try {
                                    sql = "select * from tt_berkasdigital where no_rawat='" + tbListPasienRajal.getValueAt(i, 1).toString() + "' order by (case when jenis_file ='data_individual' then 1 when jenis_file ='sep' then 2 when jenis_file ='resume' then 3 when jenis_file ='billing'   then 4  else 5 END) ASC";
                                    ps = koneksi.prepareStatement(sql);
                                    try {
                                        PDFMergerUtility ut = new PDFMergerUtility();
                                        URL url;
                                        String pathFile;
                                        rs = ps.executeQuery();
                                        while (rs.next()) {
                                            url = new URL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/webapps/berkasdigital/" + rs.getString("nama_file"));
                                            InputStream is = url.openStream();
                                            ut.addSource(is);
                                        }
//                                        pathFile = "berkasklaim/" + formattedDate + "/" + tbListPasienRajal.getValueAt(i, 5).toString();
                                        pathFile = "berkasklaim/" + formattedDate + "/";
                                        new File(pathFile).mkdirs();
                                        ut.setDestinationFileName(pathFile + "/" + tbListPasienRajal.getValueAt(i, 5).toString() + ".pdf");
                                        ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
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
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : " + e);
                                }
                            }
                        }
                    }
                }
            } else if (TabRawat.getSelectedIndex() == 1) {
                for (i = 0; i < tbListPasienRanap.getRowCount(); i++) {
                    if (tbListPasienRanap.getValueAt(i, 0).toString().equals("true")) {
                        if (!tbListPasienRanap.getValueAt(i, 5).toString().equals("-")) {
                            if (tbListPasienRanap.getValueAt(i, 6).toString().equals(formattedDate)) {
                                try {
                                    sql = "select * from tt_berkasdigital where no_rawat='" + tbListPasienRanap.getValueAt(i, 1).toString() + "' order by (case when jenis_file ='data_individual' then 1 when jenis_file ='sep' then 2 when jenis_file ='resume' then 3 when jenis_file ='billing_ranap'   then 4  else 5 END) ASC";
                                    ps = koneksi.prepareStatement(sql);

                                    try {
                                        PDFMergerUtility ut = new PDFMergerUtility();
                                        URL url;
                                        String pathFile;
                                        rs = ps.executeQuery();
                                        while (rs.next()) {
                                            url = new URL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/webapps/berkasdigital/" + rs.getString("nama_file"));
                                            InputStream is = url.openStream();
//                                         System.out.println("Notif : "+url);    
                                            ut.addSource(is);
                                        }
//                                        pathFile = "berkasklaim/" + formattedDate + "/" + tbListPasienRanap.getValueAt(i, 5).toString();
                                        pathFile = "berkasklaim/" + formattedDate + "/";
                                        new File(pathFile).mkdirs();
                                        ut.setDestinationFileName(pathFile + "/" + tbListPasienRanap.getValueAt(i, 5).toString() + ".pdf");
                                        ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
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
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : " + e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void downloadFile(String namaFile) {

        String pathFile = "berkasklaimzip";
        new File(pathFile).mkdirs();
        File dir = new File("berkasklaim");
        String zipDirName = "berkasklaimzip/" + namaFile + ".zip";
        ZipFiles zipFiles = new ZipFiles();
        zipFiles.zipDirectory(dir, zipDirName);
        JFrame parentFrame = new JFrame();
        File srcFile = new File("berkasklaimzip/" + namaFile + ".zip");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setSelectedFile(new File(srcFile.getName()));
        int userSelection = fileChooser.showSaveDialog(parentFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            File destFile = new File(fileToSave.getAbsolutePath());
            try {
                FileUtils.copyFile(srcFile, destFile);
                JOptionPane.showMessageDialog(null, "Data berhasil didownload.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Gagal mendownload .");
            }
        }
        try {
            deleteDir("berkasklaim");
            deleteDir("berkasklaimzip");
        } catch (IOException ex) {
            Logger.getLogger(DlgManagemenFileKlaim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exportTableToExcelXlsx(javax.swing.JTable table, String title) {
    Workbook workbook = new HSSFWorkbook(); // gunakan HSSF (xls)
    Sheet sheet = workbook.createSheet("Data");

    // ---------- STYLE (header & body) ----------
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    Font headerFont = workbook.createFont();
    headerFont.setBold(true);
    headerStyle.setFont(headerFont);

    CellStyle dataLeftStyle = workbook.createCellStyle();
    dataLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    CellStyle dataCenterStyle = workbook.createCellStyle();
    dataCenterStyle.setAlignment(HorizontalAlignment.CENTER);
    dataCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    // ---------- JUDUL / TITLE ----------
    int rowIndex = 0;
    Row titleRow = sheet.createRow(rowIndex++);
    Cell titleCell = titleRow.createCell(0);
    titleCell.setCellValue(title);
    CellStyle titleStyle = workbook.createCellStyle();
    titleStyle.setAlignment(HorizontalAlignment.CENTER);
    Font titleFont = workbook.createFont();
    titleFont.setBold(true);
    titleFont.setFontHeightInPoints((short) 14);
    titleStyle.setFont(titleFont);
    titleCell.setCellStyle(titleStyle);

    int colCount = table.getColumnCount();
    if (colCount > 1) {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colCount - 1));
    }

    // ---------- HEADER TABEL ----------
    Row headerRow = sheet.createRow(rowIndex++);
    for (int col = 0; col < colCount; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(table.getColumnName(col));
        cell.setCellStyle(headerStyle);
    }

    // ---------- ISI DATA ----------
    int rowCount = table.getRowCount();
    for (int row = 0; row < rowCount; row++) {
        Row excelRow = sheet.createRow(rowIndex++);

        for (int col = 0; col < colCount; col++) {
            Object value = table.getValueAt(row, col);
            Cell cell = excelRow.createCell(col);

            if (value == null) {
                cell.setCellValue("");
                cell.setCellStyle(dataLeftStyle);
                continue;
            }

            // BOOLEAN → ikon "✓" atau kosong
            if (table.getColumnClass(col) == Boolean.class) {
                boolean b = Boolean.TRUE.equals(value);
                String colName = table.getColumnName(col);
                String text;

                if (colName.equalsIgnoreCase("SEP")
                        || colName.toLowerCase().contains("resume")
                        || colName.toLowerCase().contains("laborat")
                        || colName.toLowerCase().contains("radiologi")
                        || colName.equalsIgnoreCase("USG")
                        || colName.toLowerCase().contains("billing")
                        || colName.toLowerCase().contains("individual")
                        || colName.toLowerCase().contains("files")
                        || colName.toLowerCase().contains("triase")) {

                    text = b ? "✓" : "";
                } else if (colName.equalsIgnoreCase("P")) {
                    text = b ? "✓" : "";
                } else {
                    text = b ? "Ya" : "Tidak";
                }

                cell.setCellValue(text);
                cell.setCellStyle(dataCenterStyle);
            }
            // ANGKA
            else if (value instanceof Number) {
                cell.setCellValue(((Number) value).doubleValue());
                cell.setCellStyle(dataCenterStyle);
            }
            // TEKS
            else {
                cell.setCellValue(value.toString());
                cell.setCellStyle(dataLeftStyle);
            }
        }
    }

    // ---------- AUTO SIZE COLUMN ----------
    for (int col = 0; col < colCount; col++) {
        sheet.autoSizeColumn(col);
    }

    // ---------- SIMPAN FILE ----------
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    chooser.setDialogTitle("Simpan ke Excel");
    chooser.setSelectedFile(new java.io.File(title.replace(" ", "_") + ".xls")); // .xls

    int userSelection = chooser.showSaveDialog(this);
    if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File fileToSave = chooser.getSelectedFile();
        String path = fileToSave.getAbsolutePath();

        if (!path.toLowerCase().endsWith(".xls")) {
            fileToSave = new java.io.File(path + ".xls");
        }

        try (java.io.FileOutputStream out = new java.io.FileOutputStream(fileToSave)) {
            workbook.write(out);
            JOptionPane.showMessageDialog(this,
                    "Data berhasil disimpan ke Excel:\n" + fileToSave.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan Excel.\n" + ex.getMessage());
            System.out.println("Notif export Excel : " + ex);
        }
    }

    try {
        workbook.close();
    } catch (Exception e) {
        // ignore
    }
}
    
    public void manipulatePdf(String src, String dest) throws DocumentException, IOException {
        float FACTOR = 0.5f;
        PdfReader reader = new PdfReader(src);
        int n = reader.getXrefSize();
//        int n = reader . getNumberOfPages() + 1; 
        PdfObject object;
        PRStream stream;
        // Look for image and manipulate image stream
        for (int i = 0; i < n; i++) {
            object = reader.getPdfObject(i);
            if (object == null || !object.isStream()) {
                continue;
            }
            stream = (PRStream) object;
            if (!PdfName.IMAGE.equals(stream.getAsName(PdfName.SUBTYPE))) {
                continue;
            }
            if (!PdfName.DCTDECODE.equals(stream.getAsName(PdfName.FILTER))) {
                continue;
            }
            PdfImageObject image = new PdfImageObject(stream);
            BufferedImage bi = image.getBufferedImage();
            if (bi == null) {
                continue;
            }
            int width = (int) (bi.getWidth() * FACTOR);
            int height = (int) (bi.getHeight() * FACTOR);
            if (width <= 0 || height <= 0) {
                continue;
            }
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            AffineTransform at = AffineTransform.getScaleInstance(FACTOR, FACTOR);
            Graphics2D g = img.createGraphics();
            g.drawRenderedImage(bi, at);
            ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
            ImageIO.write(img, "JPG", imgBytes);
            stream.clear();
            stream.setData(imgBytes.toByteArray(), true, PRStream.BEST_COMPRESSION);
            stream.put(PdfName.TYPE, PdfName.XOBJECT);
            stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
            stream.put(PdfName.FILTER, PdfName.DCTDECODE);
            stream.put(PdfName.WIDTH, new PdfNumber(width));
            stream.put(PdfName.HEIGHT, new PdfNumber(height));
            stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
            stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
        }
//         String src = "/Users/fanjithama/Documents/0901R0050424V003541.pdf";
//        String dest = "/Users/fanjithama/Documents/output.pdf";
//        try {
//            manipulatePdf( src,  dest) ;
//        } catch (DocumentException ex) {
//            Logger.getLogger(RMCariLabRalan.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(RMCariLabRalan.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        PdfStamper stamper = new PdfStamper(reader, fs,PdfWriter.VERSION_1_5);
//        PdfWriter writer = stamper;
//writer.SetPdfVersion(PdfWriter.PDF_VERSION_1_5);
//writer.CompressionLevel = pdf.PdfStream.BEST_COMPRESSION;
//reader.RemoveFields();
//reader.RemoveUnusedObjects();
//stamper.Reader.RemoveUnusedObjects();
//        reader.removeUnusedObjects();
        // Save altered PDF
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.setFullCompression();
        stamper.close();
        reader.close();
    }

    private void saveFileNameBerkas(String noRawat, String JenisFile, String NamaFile) {

        if (Sequel.cariInteger("Select count(no_rawat) from tt_berkasdigital where jenis_file='" + JenisFile + "' and no_rawat='" + noRawat + "'") > 0) {

        } else {
            Sequel.menyimpantf2("tt_berkasdigital", "?,?,?", "No.Rawat", 3,
                    new String[]{noRawat, JenisFile, NamaFile});
        }

    }
}
