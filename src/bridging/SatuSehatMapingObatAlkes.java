/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgBarang;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author dosen
 */
public final class SatuSehatMapingObatAlkes extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabModeListKFA,tabModeMasterObat;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0,j=0;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response, responsename;
     private ApiSatuSehat api = new ApiSatuSehat();
     private String link="https://api-satusehat.kemkes.go.id/kfa-v2";

    /**
     * Creates new form DlgJnsPerawatanRalan
     *
     * @param parent
     * @param modal
     */
    public SatuSehatMapingObatAlkes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8, 1);

        tabMode = new DefaultTableModel(null, new Object[]{
            "KFA Code", "KFA System", "Kode Barang", "Nama Obat/Alkes/BHP", "KFA Display", "Form Code",
            "Form System", "Form Display", "Numerator Code", "Numerator System", "Denominator Code",
            "Denominator System", "Route Code", "Route System", "Route Display"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbJnsPerawatan.setModel(tabMode);

        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(80);
            } else if (i == 1) {
                column.setPreferredWidth(200);
            } else if (i == 2) {
                column.setPreferredWidth(85);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(200);
            } else if (i == 5) {
                column.setPreferredWidth(80);
            } else if (i == 6) {
                column.setPreferredWidth(200);
            } else if (i == 7) {
                column.setPreferredWidth(170);
            } else if (i == 8) {
                column.setPreferredWidth(90);
            } else if (i == 9) {
                column.setPreferredWidth(200);
            } else if (i == 10) {
                column.setPreferredWidth(100);
            } else if (i == 11) {
                column.setPreferredWidth(200);
            } else if (i == 12) {
                column.setPreferredWidth(90);
            } else if (i == 13) {
                column.setPreferredWidth(170);
            } else if (i == 14) {
                column.setPreferredWidth(170);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        tabModeListKFA = new DefaultTableModel(null, new Object[]{
            "Nama KFA 93", "Kode KFA 93", "Nama KFA 92", "Kode KFA 92", "Nama KFA 91", "Kode KFA 91", "Kekuatan Zat", "Kode Satuan", "Nama Satuan"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbListKfa.setModel(tabModeListKFA);

        tbListKfa.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbListKfa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbListKfa.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(250);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(250);
            } else if (i == 3) {
                column.setPreferredWidth(80);
            } else if (i == 4) {
                column.setPreferredWidth(250);
            } else if (i == 5) {
                column.setPreferredWidth(80);
            } else if (i == 6) {
                column.setPreferredWidth(100);
            } else if (i == 7) {
                column.setPreferredWidth(250);
            } else if (i == 8) {
                column.setPreferredWidth(250);
            }
        }
        tbListKfa.setDefaultRenderer(Object.class, new WarnaTable());
        tabModeMasterObat = new DefaultTableModel(null, new Object[]{
            "Kode Barang[1]", "Nama Barang[2]", "Kode Satuan[3]", "Nama Satuan[4]", "Letak Barang[5]",
            "Hrg.Beli(Rp)[6]", "Ralan(Rp)[7]", "Ranap K1(Rp)[8]", "Ranap K2(Rp)[9]", "Ranap K3(Rp)[10]",
            "Kelas Utama/BPJS(Rp)[11]", "Ranap VIP(Rp)[12]", "Ranap VVIP(Rp)[13]", "Beli Luar(Rp)[14]",
            "Jual Bebas(Rp)[15]", "Karyawan(Rp)[16]", "Stok Minimal[17]", "Kode Jenis[18]", "Nama Jenis[19]", "Kapasitas[20]",
            "Kadaluwarsa[21]", "Kode I.F.[22]", "Industri Farmasi[23]", "Kode Kategori[24]", "Kategori[25]", "Kode Golongan[26]",
            "Golongan[27]"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbMasterObat.setModel(tabModeMasterObat);

        tbMasterObat.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbMasterObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 27; i++) {
            TableColumn column = tbMasterObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(85);
            } else if (i == 1) {
                column.setPreferredWidth(200);
            } else if (i == 2) {
                column.setPreferredWidth(73);
            } else if (i == 3) {
                column.setPreferredWidth(120);
            } else if (i == 4) {
                column.setPreferredWidth(140);
            } else if (i == 5) {
                column.setPreferredWidth(85);
            } else if (i == 6) {
                column.setPreferredWidth(85);
            } else if (i == 7) {
                column.setPreferredWidth(85);
            } else if (i == 8) {
                column.setPreferredWidth(85);
            } else if (i == 9) {
                column.setPreferredWidth(85);
            } else if (i == 10) {
                column.setPreferredWidth(85);
            } else if (i == 11) {
                column.setPreferredWidth(85);
            } else if (i == 12) {
                column.setPreferredWidth(85);
            } else if (i == 13) {
                column.setPreferredWidth(85);
            } else if (i == 14) {
                column.setPreferredWidth(85);
            } else if (i == 15) {
                column.setPreferredWidth(85);
            } else if (i == 16) {
                column.setPreferredWidth(95);
            } else if (i == 17) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 18) {
                column.setPreferredWidth(120);
            } else if (i == 19) {
                column.setPreferredWidth(70);
            } else if (i == 20) {
                column.setPreferredWidth(70);
            } else if (i == 21) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 22) {
                column.setPreferredWidth(120);
            } else if (i == 23) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 24) {
                column.setPreferredWidth(120);
            } else if (i == 25) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 26) {
                column.setPreferredWidth(120);
            } 
        }
        tbMasterObat.setDefaultRenderer(Object.class, new WarnaTable());
        KodeBarang.setDocument(new batasInput((byte) 15).getKata(KodeBarang));
        KFACode.setDocument(new batasInput((byte) 15).getKata(KFACode));
        KFADisplay.setDocument(new batasInput((byte) 80).getKata(KFADisplay));
        FormCode.setDocument(new batasInput((byte) 30).getKata(FormCode));
        FormDisplay.setDocument(new batasInput((byte) 80).getKata(FormDisplay));
        NumoratorCode.setDocument(new batasInput((byte) 15).getKata(NumoratorCode));
        DenominatorCode.setDocument(new batasInput((byte) 15).getKata(DenominatorCode));
        RouteCode.setDocument(new batasInput((byte) 30).getKata(RouteCode));
        RouteDisplay.setDocument(new batasInput((byte) 80).getKata(RouteDisplay));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }
        ChkInput.setSelected(false);
        isForm();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DlgKFARestApi = new javax.swing.JDialog();
        internalFrame19 = new widget.InternalFrame();
        internalFrame20 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbListKfa = new widget.Table();
        panelBiasa12 = new widget.PanelBiasa();
        jLabel132 = new widget.Label();
        TCariObatLive = new widget.TextBox();
        BtnCariKFA = new widget.Button();
        BtnKeluarKFA = new widget.Button();
        jLabel24 = new widget.Label();
        LCountTotal = new widget.Label();
        jLabel133 = new widget.Label();
        page = new widget.ComboBox();
        jLabel134 = new widget.Label();
        hal = new widget.ComboBox();
        DlgMasterObat = new javax.swing.JDialog();
        internalFrame23 = new widget.InternalFrame();
        internalFrame24 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbMasterObat = new widget.Table();
        panelBiasa14 = new widget.PanelBiasa();
        jLabel135 = new widget.Label();
        TCariMasterObat = new widget.TextBox();
        BtnCariObat = new widget.Button();
        BtnKeluarMasterObat = new widget.Button();
        jLabel22 = new widget.Label();
        LCountTotalMaster = new widget.Label();
        label14 = new widget.Label();
        CmbJenis = new widget.ComboBox();
        DlgKFADetailObat = new javax.swing.JDialog();
        internalFrame21 = new widget.InternalFrame();
        internalFrame22 = new widget.InternalFrame();
        FormInput1 = new widget.PanelBiasa();
        KFACode1 = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        KFADisplay1 = new widget.TextBox();
        RouteCode1 = new widget.TextBox();
        jLabel36 = new widget.Label();
        RouteSystem1 = new widget.TextBox();
        jLabel37 = new widget.Label();
        RouteDisplay1 = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        desc = new widget.TextArea();
        jLabel40 = new widget.Label();
        jScrollPane2 = new javax.swing.JScrollPane();
        warning = new widget.TextArea();
        jLabel41 = new widget.Label();
        jScrollPane3 = new javax.swing.JScrollPane();
        sideeffect = new widget.TextArea();
        jLabel42 = new widget.Label();
        ucum_code = new widget.TextBox();
        jLabel43 = new widget.Label();
        ucum_name = new widget.TextBox();
        jLabel44 = new widget.Label();
        uom = new widget.TextBox();
        jLabel45 = new widget.Label();
        formcode = new widget.TextBox();
        jLabel46 = new widget.Label();
        formname = new widget.TextBox();
        jLabel47 = new widget.Label();
        controlcode = new widget.TextBox();
        jLabel48 = new widget.Label();
        controlname = new widget.TextBox();
        panelBiasa13 = new widget.PanelBiasa();
        BtnKeluarDetailObat = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        jLabel19 = new widget.Label();
        CapaianObat = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        KodeBarang = new widget.TextBox();
        btnBarang = new widget.Button();
        jLabel5 = new widget.Label();
        FormCode = new widget.TextBox();
        jLabel8 = new widget.Label();
        NumoratorCode = new widget.TextBox();
        KFACode = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel10 = new widget.Label();
        KFADisplay = new widget.TextBox();
        jLabel11 = new widget.Label();
        FormDisplay = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        DenominatorCode = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        RouteCode = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        RouteDisplay = new widget.TextBox();
        BtnSearchViaAPI = new widget.Button();
        BtnSearchViaBrowser = new widget.Button();
        jLabel21 = new widget.Label();
        BtnDetailObat = new widget.Button();
        KFASystem = new widget.ComboBox();
        NemeratorSystem = new widget.ComboBox();
        DenominatorSystem = new widget.ComboBox();
        NamaBarang = new widget.TextBox();
        FormSystem = new widget.ComboBox();
        RouteSystem = new widget.ComboBox();

        DlgKFARestApi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgKFARestApi.setName("DlgKFARestApi"); // NOI18N
        DlgKFARestApi.setUndecorated(true);
        DlgKFARestApi.setResizable(false);

        internalFrame19.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ List Data KFA Browser ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica Neue", 0, 13), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame19.setName("internalFrame19"); // NOI18N
        internalFrame19.setPreferredSize(new java.awt.Dimension(800, 468));
        internalFrame19.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame20.setName("internalFrame20"); // NOI18N
        internalFrame20.setLayout(new java.awt.GridLayout(1, 0));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbListKfa.setName("tbListKfa"); // NOI18N
        tbListKfa.getTableHeader().setReorderingAllowed(false);
        tbListKfa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListKfaMouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbListKfa);

        internalFrame20.add(Scroll1);

        internalFrame19.add(internalFrame20, java.awt.BorderLayout.CENTER);

        panelBiasa12.setName("panelBiasa12"); // NOI18N
        panelBiasa12.setPreferredSize(new java.awt.Dimension(0, 40));
        panelBiasa12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel132.setText("Cari Data :");
        jLabel132.setName("jLabel132"); // NOI18N
        panelBiasa12.add(jLabel132);

        TCariObatLive.setName("TCariObatLive"); // NOI18N
        TCariObatLive.setPreferredSize(new java.awt.Dimension(250, 24));
        TCariObatLive.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariObatLiveKeyPressed(evt);
            }
        });
        panelBiasa12.add(TCariObatLive);

        BtnCariKFA.setBackground(new java.awt.Color(204, 0, 102));
        BtnCariKFA.setForeground(new java.awt.Color(255, 255, 255));
        BtnCariKFA.setMnemonic('K');
        BtnCariKFA.setText("Cari");
        BtnCariKFA.setToolTipText("Alt+K");
        BtnCariKFA.setName("BtnCariKFA"); // NOI18N
        BtnCariKFA.setOpaque(true);
        BtnCariKFA.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCariKFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariKFAActionPerformed(evt);
            }
        });
        panelBiasa12.add(BtnCariKFA);

        BtnKeluarKFA.setBackground(new java.awt.Color(0, 0, 0));
        BtnKeluarKFA.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluarKFA.setMnemonic('K');
        BtnKeluarKFA.setText("Keluar");
        BtnKeluarKFA.setToolTipText("Alt+K");
        BtnKeluarKFA.setName("BtnKeluarKFA"); // NOI18N
        BtnKeluarKFA.setOpaque(true);
        BtnKeluarKFA.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarKFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarKFAActionPerformed(evt);
            }
        });
        panelBiasa12.add(BtnKeluarKFA);

        jLabel24.setText("Record :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(75, 23));
        panelBiasa12.add(jLabel24);

        LCountTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountTotal.setText("0");
        LCountTotal.setName("LCountTotal"); // NOI18N
        LCountTotal.setPreferredSize(new java.awt.Dimension(80, 23));
        panelBiasa12.add(LCountTotal);

        jLabel133.setText("Jumlah Data per Halaman");
        jLabel133.setName("jLabel133"); // NOI18N
        jLabel133.setPreferredSize(new java.awt.Dimension(156, 14));
        panelBiasa12.add(jLabel133);

        page.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10", "50", "100", "200" }));
        page.setName("page"); // NOI18N
        page.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pageItemStateChanged(evt);
            }
        });
        page.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pageKeyPressed(evt);
            }
        });
        panelBiasa12.add(page);

        jLabel134.setText("Halaman :");
        jLabel134.setName("jLabel134"); // NOI18N
        panelBiasa12.add(jLabel134);

        hal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1" }));
        hal.setName("hal"); // NOI18N
        hal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                halItemStateChanged(evt);
            }
        });
        hal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                halKeyPressed(evt);
            }
        });
        panelBiasa12.add(hal);

        internalFrame19.add(panelBiasa12, java.awt.BorderLayout.PAGE_END);

        DlgKFARestApi.getContentPane().add(internalFrame19, java.awt.BorderLayout.CENTER);

        DlgMasterObat.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgMasterObat.setName("DlgMasterObat"); // NOI18N
        DlgMasterObat.setUndecorated(true);
        DlgMasterObat.setResizable(false);

        internalFrame23.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ List Master Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica Neue", 0, 13), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame23.setName("internalFrame23"); // NOI18N
        internalFrame23.setPreferredSize(new java.awt.Dimension(800, 468));
        internalFrame23.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame24.setName("internalFrame24"); // NOI18N
        internalFrame24.setLayout(new java.awt.GridLayout(1, 0));

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbMasterObat.setName("tbMasterObat"); // NOI18N
        tbMasterObat.getTableHeader().setReorderingAllowed(false);
        tbMasterObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMasterObatMouseClicked(evt);
            }
        });
        Scroll2.setViewportView(tbMasterObat);

        internalFrame24.add(Scroll2);

        internalFrame23.add(internalFrame24, java.awt.BorderLayout.CENTER);

        panelBiasa14.setName("panelBiasa14"); // NOI18N
        panelBiasa14.setPreferredSize(new java.awt.Dimension(0, 40));
        panelBiasa14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel135.setText("Cari Data :");
        jLabel135.setName("jLabel135"); // NOI18N
        panelBiasa14.add(jLabel135);

        TCariMasterObat.setName("TCariMasterObat"); // NOI18N
        TCariMasterObat.setPreferredSize(new java.awt.Dimension(250, 24));
        TCariMasterObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasterObatKeyPressed(evt);
            }
        });
        panelBiasa14.add(TCariMasterObat);

        BtnCariObat.setBackground(new java.awt.Color(0, 102, 102));
        BtnCariObat.setForeground(new java.awt.Color(255, 255, 255));
        BtnCariObat.setMnemonic('K');
        BtnCariObat.setText("Cari");
        BtnCariObat.setToolTipText("Alt+K");
        BtnCariObat.setName("BtnCariObat"); // NOI18N
        BtnCariObat.setOpaque(true);
        BtnCariObat.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCariObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariObatActionPerformed(evt);
            }
        });
        panelBiasa14.add(BtnCariObat);

        BtnKeluarMasterObat.setBackground(new java.awt.Color(0, 51, 102));
        BtnKeluarMasterObat.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluarMasterObat.setMnemonic('K');
        BtnKeluarMasterObat.setText("Keluar");
        BtnKeluarMasterObat.setToolTipText("Alt+K");
        BtnKeluarMasterObat.setName("BtnKeluarMasterObat"); // NOI18N
        BtnKeluarMasterObat.setOpaque(true);
        BtnKeluarMasterObat.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarMasterObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarMasterObatActionPerformed(evt);
            }
        });
        panelBiasa14.add(BtnKeluarMasterObat);

        jLabel22.setText("Record :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(75, 23));
        panelBiasa14.add(jLabel22);

        LCountTotalMaster.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountTotalMaster.setText("0");
        LCountTotalMaster.setName("LCountTotalMaster"); // NOI18N
        LCountTotalMaster.setPreferredSize(new java.awt.Dimension(80, 23));
        panelBiasa14.add(LCountTotalMaster);

        label14.setText("Jenis :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(50, 23));
        panelBiasa14.add(label14);

        CmbJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Obat", "Alat kesehatan BMHP", "Semua" }));
        CmbJenis.setName("CmbJenis"); // NOI18N
        CmbJenis.setPreferredSize(new java.awt.Dimension(160, 23));
        CmbJenis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbJenisItemStateChanged(evt);
            }
        });
        CmbJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJenisKeyPressed(evt);
            }
        });
        panelBiasa14.add(CmbJenis);

        internalFrame23.add(panelBiasa14, java.awt.BorderLayout.PAGE_END);

        DlgMasterObat.getContentPane().add(internalFrame23, java.awt.BorderLayout.CENTER);

        DlgKFADetailObat.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgKFADetailObat.setName("DlgKFADetailObat"); // NOI18N
        DlgKFADetailObat.setUndecorated(true);
        DlgKFADetailObat.setResizable(false);

        internalFrame21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Detail Data KFA REST-API ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica Neue", 0, 13), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame21.setName("internalFrame21"); // NOI18N
        internalFrame21.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame22.setName("internalFrame22"); // NOI18N
        internalFrame22.setLayout(new java.awt.BorderLayout());

        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(100, 104));
        FormInput1.setLayout(null);

        KFACode1.setHighlighter(null);
        KFACode1.setName("KFACode1"); // NOI18N
        KFACode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KFACode1KeyPressed(evt);
            }
        });
        FormInput1.add(KFACode1);
        KFACode1.setBounds(109, 10, 170, 23);

        jLabel34.setText("KFA Code :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput1.add(jLabel34);
        jLabel34.setBounds(0, 10, 105, 23);

        jLabel35.setText("KFA Display :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput1.add(jLabel35);
        jLabel35.setBounds(0, 40, 105, 23);

        KFADisplay1.setHighlighter(null);
        KFADisplay1.setName("KFADisplay1"); // NOI18N
        KFADisplay1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KFADisplay1KeyPressed(evt);
            }
        });
        FormInput1.add(KFADisplay1);
        KFADisplay1.setBounds(109, 40, 610, 23);

        RouteCode1.setHighlighter(null);
        RouteCode1.setName("RouteCode1"); // NOI18N
        RouteCode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RouteCode1KeyPressed(evt);
            }
        });
        FormInput1.add(RouteCode1);
        RouteCode1.setBounds(110, 280, 70, 23);

        jLabel36.setText("Route System :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput1.add(jLabel36);
        jLabel36.setBounds(430, 280, 90, 23);

        RouteSystem1.setHighlighter(null);
        RouteSystem1.setName("RouteSystem1"); // NOI18N
        RouteSystem1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RouteSystem1KeyPressed(evt);
            }
        });
        FormInput1.add(RouteSystem1);
        RouteSystem1.setBounds(520, 280, 187, 23);

        jLabel37.setText("Route Display :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput1.add(jLabel37);
        jLabel37.setBounds(180, 280, 91, 23);

        RouteDisplay1.setHighlighter(null);
        RouteDisplay1.setName("RouteDisplay1"); // NOI18N
        RouteDisplay1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RouteDisplay1KeyPressed(evt);
            }
        });
        FormInput1.add(RouteDisplay1);
        RouteDisplay1.setBounds(270, 280, 160, 23);

        jLabel38.setText("Route Code :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput1.add(jLabel38);
        jLabel38.setBounds(0, 280, 105, 23);

        jLabel39.setText("KFA Description :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput1.add(jLabel39);
        jLabel39.setBounds(0, 70, 105, 23);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        desc.setColumns(20);
        desc.setRows(5);
        desc.setName("desc"); // NOI18N
        jScrollPane1.setViewportView(desc);

        FormInput1.add(jScrollPane1);
        jScrollPane1.setBounds(110, 70, 610, 70);

        jLabel40.setText("KFA Warning :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput1.add(jLabel40);
        jLabel40.setBounds(0, 140, 105, 23);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        warning.setColumns(20);
        warning.setRows(5);
        warning.setName("warning"); // NOI18N
        jScrollPane2.setViewportView(warning);

        FormInput1.add(jScrollPane2);
        jScrollPane2.setBounds(110, 140, 610, 70);

        jLabel41.setText("KFA Side Effect :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput1.add(jLabel41);
        jLabel41.setBounds(0, 210, 105, 23);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        sideeffect.setColumns(20);
        sideeffect.setRows(5);
        sideeffect.setName("sideeffect"); // NOI18N
        jScrollPane3.setViewportView(sideeffect);

        FormInput1.add(jScrollPane3);
        jScrollPane3.setBounds(110, 210, 610, 70);

        jLabel42.setText("UCUM Code :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput1.add(jLabel42);
        jLabel42.setBounds(0, 310, 105, 23);

        ucum_code.setHighlighter(null);
        ucum_code.setName("ucum_code"); // NOI18N
        ucum_code.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ucum_codeKeyPressed(evt);
            }
        });
        FormInput1.add(ucum_code);
        ucum_code.setBounds(110, 310, 70, 23);

        jLabel43.setText("UCUM Display :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput1.add(jLabel43);
        jLabel43.setBounds(180, 310, 91, 23);

        ucum_name.setHighlighter(null);
        ucum_name.setName("ucum_name"); // NOI18N
        ucum_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ucum_nameKeyPressed(evt);
            }
        });
        FormInput1.add(ucum_name);
        ucum_name.setBounds(270, 310, 160, 23);

        jLabel44.setText("UOM :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput1.add(jLabel44);
        jLabel44.setBounds(430, 310, 40, 23);

        uom.setHighlighter(null);
        uom.setName("uom"); // NOI18N
        uom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                uomKeyPressed(evt);
            }
        });
        FormInput1.add(uom);
        uom.setBounds(470, 310, 100, 23);

        jLabel45.setText("Form Code :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput1.add(jLabel45);
        jLabel45.setBounds(0, 340, 105, 23);

        formcode.setHighlighter(null);
        formcode.setName("formcode"); // NOI18N
        formcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formcodeKeyPressed(evt);
            }
        });
        FormInput1.add(formcode);
        formcode.setBounds(110, 340, 70, 23);

        jLabel46.setText("Form Display:");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput1.add(jLabel46);
        jLabel46.setBounds(180, 340, 91, 23);

        formname.setHighlighter(null);
        formname.setName("formname"); // NOI18N
        formname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formnameKeyPressed(evt);
            }
        });
        FormInput1.add(formname);
        formname.setBounds(270, 340, 160, 23);

        jLabel47.setText("Control Code :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput1.add(jLabel47);
        jLabel47.setBounds(0, 370, 105, 23);

        controlcode.setHighlighter(null);
        controlcode.setName("controlcode"); // NOI18N
        controlcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                controlcodeKeyPressed(evt);
            }
        });
        FormInput1.add(controlcode);
        controlcode.setBounds(110, 370, 70, 23);

        jLabel48.setText("Control Display:");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput1.add(jLabel48);
        jLabel48.setBounds(180, 370, 91, 23);

        controlname.setHighlighter(null);
        controlname.setName("controlname"); // NOI18N
        controlname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                controlnameKeyPressed(evt);
            }
        });
        FormInput1.add(controlname);
        controlname.setBounds(270, 370, 160, 23);

        internalFrame22.add(FormInput1, java.awt.BorderLayout.CENTER);

        internalFrame21.add(internalFrame22, java.awt.BorderLayout.CENTER);

        panelBiasa13.setName("panelBiasa13"); // NOI18N
        panelBiasa13.setPreferredSize(new java.awt.Dimension(0, 40));
        panelBiasa13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        BtnKeluarDetailObat.setBackground(new java.awt.Color(255, 51, 0));
        BtnKeluarDetailObat.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluarDetailObat.setMnemonic('K');
        BtnKeluarDetailObat.setText("Keluar");
        BtnKeluarDetailObat.setToolTipText("Alt+K");
        BtnKeluarDetailObat.setName("BtnKeluarDetailObat"); // NOI18N
        BtnKeluarDetailObat.setOpaque(true);
        BtnKeluarDetailObat.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarDetailObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarDetailObatActionPerformed(evt);
            }
        });
        panelBiasa13.add(BtnKeluarDetailObat);

        internalFrame21.add(panelBiasa13, java.awt.BorderLayout.PAGE_END);

        DlgKFADetailObat.getContentPane().add(internalFrame21, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(900, 800));
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Mapping Obat/Alkes/BHP Satu Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jLabel19.setText("Progress Mapping :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass9.add(jLabel19);

        CapaianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        CapaianObat.setText("0");
        CapaianObat.setName("CapaianObat"); // NOI18N
        CapaianObat.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass9.add(CapaianObat);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 280));
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 104));
        FormInput.setLayout(null);

        jLabel4.setText("KFA System :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(220, 40, 80, 23);

        KodeBarang.setEditable(false);
        KodeBarang.setHighlighter(null);
        KodeBarang.setName("KodeBarang"); // NOI18N
        FormInput.add(KodeBarang);
        KodeBarang.setBounds(110, 10, 100, 23);

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang.setMnemonic('1');
        btnBarang.setToolTipText("Alt+1");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        btnBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBarangKeyPressed(evt);
            }
        });
        FormInput.add(btnBarang);
        btnBarang.setBounds(590, 10, 28, 23);

        jLabel5.setText("Form Code :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 100, 105, 23);

        FormCode.setHighlighter(null);
        FormCode.setName("FormCode"); // NOI18N
        FormCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FormCodeKeyPressed(evt);
            }
        });
        FormInput.add(FormCode);
        FormCode.setBounds(110, 100, 80, 23);

        jLabel8.setText("Numerator Code :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 160, 105, 23);

        NumoratorCode.setHighlighter(null);
        NumoratorCode.setName("NumoratorCode"); // NOI18N
        NumoratorCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NumoratorCodeKeyPressed(evt);
            }
        });
        FormInput.add(NumoratorCode);
        NumoratorCode.setBounds(110, 160, 70, 23);

        KFACode.setHighlighter(null);
        KFACode.setName("KFACode"); // NOI18N
        KFACode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KFACodeKeyPressed(evt);
            }
        });
        FormInput.add(KFACode);
        KFACode.setBounds(110, 40, 100, 23);

        jLabel9.setText("KFA Code :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 40, 105, 23);

        jLabel10.setText("KFA Display :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 70, 105, 23);

        KFADisplay.setHighlighter(null);
        KFADisplay.setName("KFADisplay"); // NOI18N
        KFADisplay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KFADisplayKeyPressed(evt);
            }
        });
        FormInput.add(KFADisplay);
        KFADisplay.setBounds(110, 70, 620, 23);

        jLabel11.setText("Form System :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(200, 100, 90, 23);

        FormDisplay.setHighlighter(null);
        FormDisplay.setName("FormDisplay"); // NOI18N
        FormDisplay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FormDisplayKeyPressed(evt);
            }
        });
        FormInput.add(FormDisplay);
        FormDisplay.setBounds(110, 130, 620, 23);

        jLabel12.setText("Form Display :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 130, 105, 23);

        jLabel13.setText("Denomina Code :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 190, 105, 23);

        jLabel14.setText("Numerator System :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(180, 160, 130, 23);

        DenominatorCode.setHighlighter(null);
        DenominatorCode.setName("DenominatorCode"); // NOI18N
        DenominatorCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DenominatorCodeKeyPressed(evt);
            }
        });
        FormInput.add(DenominatorCode);
        DenominatorCode.setBounds(110, 190, 70, 23);

        jLabel15.setText("Denominator System :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(180, 190, 130, 23);

        jLabel16.setText("Route Code :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 220, 105, 23);

        RouteCode.setHighlighter(null);
        RouteCode.setName("RouteCode"); // NOI18N
        RouteCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RouteCodeKeyPressed(evt);
            }
        });
        FormInput.add(RouteCode);
        RouteCode.setBounds(110, 220, 70, 23);

        jLabel17.setText("Route System :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(190, 220, 90, 23);

        jLabel18.setText("Route Display :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(540, 220, 91, 23);

        RouteDisplay.setHighlighter(null);
        RouteDisplay.setName("RouteDisplay"); // NOI18N
        RouteDisplay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RouteDisplayKeyPressed(evt);
            }
        });
        FormInput.add(RouteDisplay);
        RouteDisplay.setBounds(640, 220, 160, 23);

        BtnSearchViaAPI.setBackground(new java.awt.Color(204, 0, 102));
        BtnSearchViaAPI.setForeground(new java.awt.Color(255, 255, 255));
        BtnSearchViaAPI.setMnemonic('K');
        BtnSearchViaAPI.setText("Cari via Rest API");
        BtnSearchViaAPI.setToolTipText("Alt+K");
        BtnSearchViaAPI.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BtnSearchViaAPI.setIconTextGap(0);
        BtnSearchViaAPI.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BtnSearchViaAPI.setName("BtnSearchViaAPI"); // NOI18N
        BtnSearchViaAPI.setOpaque(true);
        BtnSearchViaAPI.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSearchViaAPI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSearchViaAPIActionPerformed(evt);
            }
        });
        FormInput.add(BtnSearchViaAPI);
        BtnSearchViaAPI.setBounds(630, 10, 130, 20);

        BtnSearchViaBrowser.setBackground(new java.awt.Color(0, 102, 102));
        BtnSearchViaBrowser.setForeground(new java.awt.Color(255, 255, 255));
        BtnSearchViaBrowser.setMnemonic('K');
        BtnSearchViaBrowser.setText("Cari via Browser");
        BtnSearchViaBrowser.setToolTipText("Alt+K");
        BtnSearchViaBrowser.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BtnSearchViaBrowser.setIconTextGap(0);
        BtnSearchViaBrowser.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BtnSearchViaBrowser.setName("BtnSearchViaBrowser"); // NOI18N
        BtnSearchViaBrowser.setOpaque(true);
        BtnSearchViaBrowser.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSearchViaBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSearchViaBrowserActionPerformed(evt);
            }
        });
        FormInput.add(BtnSearchViaBrowser);
        BtnSearchViaBrowser.setBounds(770, 10, 130, 20);

        jLabel21.setText("Data Obat RS :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(0, 10, 110, 23);

        BtnDetailObat.setBackground(new java.awt.Color(0, 102, 102));
        BtnDetailObat.setForeground(new java.awt.Color(255, 255, 255));
        BtnDetailObat.setMnemonic('K');
        BtnDetailObat.setText("Vew Detail");
        BtnDetailObat.setToolTipText("Alt+K");
        BtnDetailObat.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BtnDetailObat.setIconTextGap(0);
        BtnDetailObat.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BtnDetailObat.setName("BtnDetailObat"); // NOI18N
        BtnDetailObat.setOpaque(true);
        BtnDetailObat.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnDetailObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDetailObatActionPerformed(evt);
            }
        });
        FormInput.add(BtnDetailObat);
        BtnDetailObat.setBounds(600, 40, 100, 20);

        KFASystem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "http://sys-ids.kemkes.go.id/kfa" }));
        KFASystem.setName("KFASystem"); // NOI18N
        KFASystem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KFASystemKeyPressed(evt);
            }
        });
        FormInput.add(KFASystem);
        KFASystem.setBounds(310, 40, 280, 23);

        NemeratorSystem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "http://unitsofmeasure.org", "http://terminology.hl7.org/CodeSystem/v3-orderableDrugForm", "http://terminology.kemkes.go.id/CodeSystem/medication-form" }));
        NemeratorSystem.setName("NemeratorSystem"); // NOI18N
        NemeratorSystem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NemeratorSystemKeyPressed(evt);
            }
        });
        FormInput.add(NemeratorSystem);
        NemeratorSystem.setBounds(310, 160, 230, 23);

        DenominatorSystem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "http://unitsofmeasure.org", "http://terminology.hl7.org/CodeSystem/v3-orderableDrugForm", "http://terminology.kemkes.go.id/CodeSystem/medication-form" }));
        DenominatorSystem.setName("DenominatorSystem"); // NOI18N
        DenominatorSystem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DenominatorSystemKeyPressed(evt);
            }
        });
        FormInput.add(DenominatorSystem);
        DenominatorSystem.setBounds(310, 190, 230, 23);

        NamaBarang.setEditable(false);
        NamaBarang.setHighlighter(null);
        NamaBarang.setName("NamaBarang"); // NOI18N
        FormInput.add(NamaBarang);
        NamaBarang.setBounds(210, 10, 380, 24);

        FormSystem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "http://terminology.kemkes.go.id/CodeSystem/medication-form" }));
        FormSystem.setName("FormSystem"); // NOI18N
        FormSystem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FormSystemKeyPressed(evt);
            }
        });
        FormInput.add(FormSystem);
        FormSystem.setBounds(310, 100, 420, 23);

        RouteSystem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "http://www.whocc.no/atc" }));
        RouteSystem.setName("RouteSystem"); // NOI18N
        RouteSystem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RouteSystemKeyPressed(evt);
            }
        });
        FormInput.add(RouteSystem);
        RouteSystem.setBounds(280, 220, 260, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        tampilObat();
        DlgMasterObat.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        DlgMasterObat.setLocationRelativeTo(internalFrame1);
        DlgMasterObat.setVisible(true);
}//GEN-LAST:event_btnBarangActionPerformed

    private void btnBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBarangKeyPressed
        Valid.pindah(evt, KFASystem, KFADisplay);
}//GEN-LAST:event_btnBarangKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (KFACode.getText().trim().equals("")) {
            Valid.textKosong(KFACode, "KFA Code");
        } else if (NamaBarang.getText().trim().equals("")) {
            Valid.textKosong(NamaBarang, "Obat/Alkes/BHP");
        } else if (KFADisplay.getText().trim().equals("")) {
            Valid.textKosong(KFADisplay, "KFA Display");
        } else if (FormCode.getText().trim().equals("")) {
            Valid.textKosong(FormCode, "Form Code");
        }  else if (FormDisplay.getText().trim().equals("")) {
            Valid.textKosong(FormDisplay, "Form Display");
        } else if (NumoratorCode.getText().trim().equals("")) {
            Valid.textKosong(NumoratorCode, "Numorator Code");
        } else if (DenominatorCode.getText().trim().equals("")) {
            Valid.textKosong(DenominatorCode, "Denominator Code");
        } else if (RouteCode.getText().trim().equals("")) {
            Valid.textKosong(RouteCode, "Route Code");
        } else if (RouteDisplay.getText().trim().equals("")) {
            Valid.textKosong(RouteDisplay, "Route Display");
        } else {
            if (Sequel.menyimpantf("satu_sehat_mapping_obat", "?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Mapping KFA", 14, new String[]{
                KodeBarang.getText(), KFACode.getText(), KFASystem.getSelectedItem().toString(), KFADisplay.getText(), FormCode.getText(),
                FormSystem.getSelectedItem().toString(), FormDisplay.getText(), NumoratorCode.getText(), NemeratorSystem.getSelectedItem().toString(), DenominatorCode.getText(),
                DenominatorSystem.getSelectedItem().toString(), RouteCode.getText(), RouteSystem.getSelectedItem().toString(), RouteDisplay.getText()
            }) == true) {
                tabMode.addRow(new String[]{
                    KFACode.getText(), KFASystem.getSelectedItem().toString(), KodeBarang.getText(), NamaBarang.getText(), KFADisplay.getText(), FormCode.getText(),
                    FormSystem.getSelectedItem().toString(), FormDisplay.getText(), NumoratorCode.getText(), NemeratorSystem.getSelectedItem().toString(), DenominatorCode.getText(),
                    DenominatorSystem.getSelectedItem().toString(), RouteCode.getText(), RouteSystem.getSelectedItem().toString(), RouteDisplay.getText()
                });
                emptTeks();
                LCount.setText("" + tabMode.getRowCount());
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, RouteDisplay, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (Valid.hapusTabletf(tabMode, KodeBarang, "satu_sehat_mapping_obat", "kode_brng") == true) {
            tabMode.removeRow(tbJnsPerawatan.getSelectedRow());
            emptTeks();
            LCount.setText("" + tabMode.getRowCount());
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (KFACode.getText().trim().equals("")) {
            Valid.textKosong(KFACode, "KFA Code");
        } else if (KodeBarang.getText().trim().equals("")) {
            Valid.textKosong(KodeBarang, "Obat/Alkes/BHP");
        } else if (KFADisplay.getText().trim().equals("")) {
            Valid.textKosong(KFADisplay, "KFA Display");
        } else if (FormCode.getText().trim().equals("")) {
            Valid.textKosong(FormCode, "Form Code");
        } else if (FormDisplay.getText().trim().equals("")) {
            Valid.textKosong(FormDisplay, "Form Display");
        } else if (NumoratorCode.getText().trim().equals("")) {
            Valid.textKosong(NumoratorCode, "Numorator Code");
        } else if (DenominatorCode.getText().trim().equals("")) {
            Valid.textKosong(DenominatorCode, "Denominator Code");
        } else if (RouteCode.getText().trim().equals("")) {
            Valid.textKosong(RouteCode, "Route Code");
        }  else if (RouteDisplay.getText().trim().equals("")) {
            Valid.textKosong(RouteDisplay, "Route Display");
        } else {
            if (tbJnsPerawatan.getSelectedRow() > -1) {
                if (Sequel.mengedittf("satu_sehat_mapping_obat", "kode_brng=?", "kode_brng=?,obat_code=?,obat_system=?,obat_display=?,"
                        + "form_code=?,form_system=?,form_display=?,numerator_code=?,numerator_system=?,denominator_code=?,denominator_system=?,"
                        + "route_code=?,route_system=?,route_display=?", 15, new String[]{
                            KodeBarang.getText(), KFACode.getText(), KFASystem.getSelectedItem().toString(), KFADisplay.getText(), FormCode.getText(),
                            FormSystem.getSelectedItem().toString(), FormDisplay.getText(), NumoratorCode.getText(), NemeratorSystem.getSelectedItem().toString(), DenominatorCode.getText(),
                            DenominatorSystem.getSelectedItem().toString(), RouteCode.getText(), RouteSystem.getSelectedItem().toString(), RouteDisplay.getText(),
                            tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 2).toString()
                        }) == true) {
                    tabMode.setValueAt(KFACode.getText(), tbJnsPerawatan.getSelectedRow(), 0);
                    tabMode.setValueAt(KFASystem.getSelectedItem().toString(), tbJnsPerawatan.getSelectedRow(), 1);
                    tabMode.setValueAt(KodeBarang.getText(), tbJnsPerawatan.getSelectedRow(), 2);
                    tabMode.setValueAt(NamaBarang.getText(), tbJnsPerawatan.getSelectedRow(), 3);
                    tabMode.setValueAt(KFADisplay.getText(), tbJnsPerawatan.getSelectedRow(), 4);
                    tabMode.setValueAt(FormCode.getText(), tbJnsPerawatan.getSelectedRow(), 5);
                    tabMode.setValueAt(FormSystem.getSelectedItem().toString(), tbJnsPerawatan.getSelectedRow(), 6);
                    tabMode.setValueAt(FormDisplay.getText(), tbJnsPerawatan.getSelectedRow(), 7);
                    tabMode.setValueAt(NumoratorCode.getText(), tbJnsPerawatan.getSelectedRow(), 8);
                    tabMode.setValueAt(NemeratorSystem.getSelectedItem().toString(), tbJnsPerawatan.getSelectedRow(), 9);
                    tabMode.setValueAt(DenominatorCode.getText(), tbJnsPerawatan.getSelectedRow(), 10);
                    tabMode.setValueAt(DenominatorSystem.getSelectedItem().toString(), tbJnsPerawatan.getSelectedRow(), 11);
                    tabMode.setValueAt(RouteCode.getText(), tbJnsPerawatan.getSelectedRow(), 12);
                    tabMode.setValueAt(RouteSystem.getSelectedItem().toString(), tbJnsPerawatan.getSelectedRow(), 13);
                    tabMode.setValueAt(RouteDisplay.getText(), tbJnsPerawatan.getSelectedRow(), 14);
                    emptTeks();
                }
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            param.put("parameter", "%" + TCari.getText().trim() + "%");
            Valid.MyReport("rptMapingKFASatuSehat.jasper", "report", "::[ Mapping Obat/Alkes/BHP Satu Sehat Kemenkes ]::", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TCari.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbJnsPerawatanKeyReleased

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KFACodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KFACodeKeyPressed
        Valid.pindah(evt, TCari, KFASystem);
    }//GEN-LAST:event_KFACodeKeyPressed

    private void KFADisplayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KFADisplayKeyPressed
        Valid.pindah(evt, KFASystem, FormCode);
    }//GEN-LAST:event_KFADisplayKeyPressed

    private void FormCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormCodeKeyPressed
        Valid.pindah(evt, KFADisplay, FormSystem);
    }//GEN-LAST:event_FormCodeKeyPressed

    private void FormDisplayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormDisplayKeyPressed
        Valid.pindah(evt, FormSystem, NumoratorCode);
    }//GEN-LAST:event_FormDisplayKeyPressed

    private void NumoratorCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NumoratorCodeKeyPressed
        Valid.pindah(evt, FormDisplay, NemeratorSystem);
    }//GEN-LAST:event_NumoratorCodeKeyPressed

    private void DenominatorCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DenominatorCodeKeyPressed
        Valid.pindah(evt, NemeratorSystem, DenominatorSystem);
    }//GEN-LAST:event_DenominatorCodeKeyPressed

    private void RouteCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RouteCodeKeyPressed
        Valid.pindah(evt, DenominatorSystem, RouteSystem);
    }//GEN-LAST:event_RouteCodeKeyPressed

    private void RouteDisplayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RouteDisplayKeyPressed
        Valid.pindah(evt, RouteSystem, BtnSimpan);
    }//GEN-LAST:event_RouteDisplayKeyPressed

    private void BtnSearchViaAPIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSearchViaAPIActionPerformed
        TCariObatLive.setText(NamaBarang.getText().trim());
        tampilListKfa(TCariObatLive.getText().trim());
        DlgKFARestApi.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        DlgKFARestApi.setLocationRelativeTo(internalFrame1);
        DlgKFARestApi.setVisible(true);
        //        DlgKFARestApi.setAlwaysOnTop(true);
        TCariObatLive.requestFocus();
    }//GEN-LAST:event_BtnSearchViaAPIActionPerformed

    private void BtnSearchViaBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSearchViaBrowserActionPerformed
        Browser aplikasi = new Browser(null, false);
        aplikasi.loadURL("https://dto.kemkes.go.id/kfa-browser");
        aplikasi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        aplikasi.setLocationRelativeTo(internalFrame1);
        aplikasi.setVisible(true);
    }//GEN-LAST:event_BtnSearchViaBrowserActionPerformed

    private void BtnDetailObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDetailObatActionPerformed
        tampilDetailObat(KFACode.getText().trim());
        DlgKFADetailObat.setSize(internalFrame1.getWidth(), 500);
        DlgKFADetailObat.setLocationRelativeTo(internalFrame1);
        DlgKFADetailObat.setVisible(true);
    }//GEN-LAST:event_BtnDetailObatActionPerformed

    private void KFASystemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KFASystemKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KFASystemKeyPressed

    private void NemeratorSystemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NemeratorSystemKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NemeratorSystemKeyPressed

    private void DenominatorSystemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DenominatorSystemKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DenominatorSystemKeyPressed

    private void tbListKfaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListKfaMouseClicked

        if (evt.getClickCount() == 2) {
            KFACode.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 1).toString());
            KFADisplay.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 0).toString());
            FormCode.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 7).toString());
            FormDisplay.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 8).toString());
//            komposisi.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 6).toString().split(" ")[0]);
//            satuanKomposisi.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 6).toString().split(" ")[1]);
            getDetailObat(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 1).toString());
            DlgKFARestApi.dispose();
        }
    }//GEN-LAST:event_tbListKfaMouseClicked

    private void TCariObatLiveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariObatLiveKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariObatLiveKeyPressed

    private void BtnCariKFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariKFAActionPerformed
        tampilListKfa(TCariObatLive.getText().trim());
    }//GEN-LAST:event_BtnCariKFAActionPerformed

    private void BtnKeluarKFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarKFAActionPerformed
        DlgKFARestApi.dispose();
    }//GEN-LAST:event_BtnKeluarKFAActionPerformed

    private void pageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pageItemStateChanged
        tampilListKfaSearchPage(TCariObatLive.getText().trim());
    }//GEN-LAST:event_pageItemStateChanged

    private void pageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pageKeyPressed

    }//GEN-LAST:event_pageKeyPressed

    private void halItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_halItemStateChanged
        tampilListKfaSearchPage(TCariObatLive.getText().trim());
    }//GEN-LAST:event_halItemStateChanged

    private void halKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_halKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_halKeyPressed

    private void FormSystemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSystemKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FormSystemKeyPressed

    private void RouteSystemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RouteSystemKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RouteSystemKeyPressed

    private void tbMasterObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMasterObatMouseClicked
        if (evt.getClickCount() == 2) {
            KodeBarang.setText(tbMasterObat.getValueAt(tbMasterObat.getSelectedRow(), 0).toString());
            NamaBarang.setText(tbMasterObat.getValueAt(tbMasterObat.getSelectedRow(), 1).toString());
            DlgMasterObat.dispose();
        }
    }//GEN-LAST:event_tbMasterObatMouseClicked

    private void TCariMasterObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasterObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariMasterObatKeyPressed

    private void BtnCariObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariObatActionPerformed
        tampilObat();
    }//GEN-LAST:event_BtnCariObatActionPerformed

    private void BtnKeluarMasterObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarMasterObatActionPerformed
        DlgMasterObat.dispose();
    }//GEN-LAST:event_BtnKeluarMasterObatActionPerformed

    private void CmbJenisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbJenisItemStateChanged
        BtnCariActionPerformed(null);
    }//GEN-LAST:event_CmbJenisItemStateChanged

    private void CmbJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJenisKeyPressed

    private void KFACode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KFACode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KFACode1KeyPressed

    private void KFADisplay1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KFADisplay1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KFADisplay1KeyPressed

    private void RouteCode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RouteCode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RouteCode1KeyPressed

    private void RouteSystem1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RouteSystem1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RouteSystem1KeyPressed

    private void RouteDisplay1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RouteDisplay1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RouteDisplay1KeyPressed

    private void ucum_codeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ucum_codeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ucum_codeKeyPressed

    private void ucum_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ucum_nameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ucum_nameKeyPressed

    private void uomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_uomKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_uomKeyPressed

    private void formcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formcodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formcodeKeyPressed

    private void formnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formnameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formnameKeyPressed

    private void controlcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_controlcodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_controlcodeKeyPressed

    private void controlnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_controlnameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_controlnameKeyPressed

    private void BtnKeluarDetailObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarDetailObatActionPerformed
        RouteCode.setText(RouteCode1.getText());
        RouteDisplay.setText(RouteDisplay1.getText());
        DlgKFADetailObat.dispose();
    }//GEN-LAST:event_BtnKeluarDetailObatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatMapingObatAlkes dialog = new SatuSehatMapingObatAlkes(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCariKFA;
    private widget.Button BtnCariObat;
    private widget.Button BtnDetailObat;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarDetailObat;
    private widget.Button BtnKeluarKFA;
    private widget.Button BtnKeluarMasterObat;
    private widget.Button BtnPrint;
    private widget.Button BtnSearchViaAPI;
    private widget.Button BtnSearchViaBrowser;
    private widget.Button BtnSimpan;
    private widget.Label CapaianObat;
    private widget.CekBox ChkInput;
    private widget.ComboBox CmbJenis;
    private widget.TextBox DenominatorCode;
    private widget.ComboBox DenominatorSystem;
    private javax.swing.JDialog DlgKFADetailObat;
    private javax.swing.JDialog DlgKFARestApi;
    private javax.swing.JDialog DlgMasterObat;
    private widget.TextBox FormCode;
    private widget.TextBox FormDisplay;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private widget.ComboBox FormSystem;
    private widget.TextBox KFACode;
    private widget.TextBox KFACode1;
    private widget.TextBox KFADisplay;
    private widget.TextBox KFADisplay1;
    private widget.ComboBox KFASystem;
    private widget.TextBox KodeBarang;
    private widget.Label LCount;
    private widget.Label LCountTotal;
    private widget.Label LCountTotalMaster;
    private widget.TextBox NamaBarang;
    private widget.ComboBox NemeratorSystem;
    private widget.TextBox NumoratorCode;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox RouteCode;
    private widget.TextBox RouteCode1;
    private widget.TextBox RouteDisplay;
    private widget.TextBox RouteDisplay1;
    private widget.ComboBox RouteSystem;
    private widget.TextBox RouteSystem1;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.TextBox TCariMasterObat;
    private widget.TextBox TCariObatLive;
    private widget.Button btnBarang;
    private widget.TextBox controlcode;
    private widget.TextBox controlname;
    private widget.TextArea desc;
    private widget.TextBox formcode;
    private widget.TextBox formname;
    private widget.ComboBox hal;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame19;
    private widget.InternalFrame internalFrame20;
    private widget.InternalFrame internalFrame21;
    private widget.InternalFrame internalFrame22;
    private widget.InternalFrame internalFrame23;
    private widget.InternalFrame internalFrame24;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel24;
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
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private widget.Label label14;
    private widget.ComboBox page;
    private widget.PanelBiasa panelBiasa12;
    private widget.PanelBiasa panelBiasa13;
    private widget.PanelBiasa panelBiasa14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.TextArea sideeffect;
    private widget.Table tbJnsPerawatan;
    public widget.Table tbListKfa;
    public widget.Table tbMasterObat;
    private widget.TextBox ucum_code;
    private widget.TextBox ucum_name;
    private widget.TextBox uom;
    private widget.TextArea warning;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select satu_sehat_mapping_obat.kode_brng,databarang.nama_brng,satu_sehat_mapping_obat.obat_code,satu_sehat_mapping_obat.obat_system,"
                    + "satu_sehat_mapping_obat.obat_display,satu_sehat_mapping_obat.form_code,satu_sehat_mapping_obat.form_system,"
                    + "satu_sehat_mapping_obat.form_display,satu_sehat_mapping_obat.numerator_code,satu_sehat_mapping_obat.numerator_system,"
                    + "satu_sehat_mapping_obat.denominator_code,satu_sehat_mapping_obat.denominator_system,satu_sehat_mapping_obat.route_code,"
                    + "satu_sehat_mapping_obat.route_system,satu_sehat_mapping_obat.route_display from satu_sehat_mapping_obat inner join databarang "
                    + "on satu_sehat_mapping_obat.kode_brng=databarang.kode_brng "
                    + (TCari.getText().equals("") ? "" : "where satu_sehat_mapping_obat.kode_brng like ? or databarang.nama_brng like ? or "
                    + "satu_sehat_mapping_obat.obat_code like ? or satu_sehat_mapping_obat.obat_display like ? or satu_sehat_mapping_obat.form_display like ? ")
                    + " order by satu_sehat_mapping_obat.obat_code");
            try {
                if (!TCari.getText().equals("")) {
                    ps.setString(1, "%" + TCari.getText() + "%");
                    ps.setString(2, "%" + TCari.getText() + "%");
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        rs.getString("obat_code"), rs.getString("obat_system"), rs.getString("kode_brng"), rs.getString("nama_brng"), rs.getString("obat_display"),
                        rs.getString("form_code"), rs.getString("form_system"), rs.getString("form_display"), rs.getString("numerator_code"), rs.getString("numerator_system"),
                        rs.getString("denominator_code"), rs.getString("denominator_system"), rs.getString("route_code"), rs.getString("route_system"), rs.getString("route_display")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif Ketersediaan : " + e);
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
        LCount.setText("" + tabMode.getRowCount());
        int obatMaping = Sequel.cariInteger("select count(databarang.kode_brng) from satu_sehat_mapping_obat JOIN databarang ON satu_sehat_mapping_obat.kode_brng=databarang.kode_brng  JOIN jenis  ON  databarang.kdjns = jenis.kdjns  where databarang.STATUS = '1' AND jenis.nama != 'Alat kesehatan BMHP' ");
        int obatTotal = Sequel.cariInteger("select count(databarang.kode_brng) from databarang JOIN jenis  ON  databarang.kdjns = jenis.kdjns  where databarang.STATUS = '1' AND jenis.nama != 'Alat kesehatan BMHP' ");
        double CapaianObatMaping = (double) obatMaping / obatTotal * 100;
        
        CapaianObat.setText(obatMaping + "/" + obatTotal + " [" + Valid.SetAngka4(CapaianObatMaping) + "%]");
    }

    public void emptTeks() {
        KFACode.setText("");
        KFASystem.setSelectedIndex(0);
        KodeBarang.setText("");
        NamaBarang.setText("");
        KFADisplay.setText("");
        FormCode.setText("");
        FormSystem.setSelectedIndex(0);
        FormDisplay.setText("");
        NumoratorCode.setText("");
        NemeratorSystem.setSelectedIndex(0);
        DenominatorCode.setText("");
        DenominatorSystem.setSelectedIndex(0);
        RouteCode.setText("");
        RouteSystem.setSelectedIndex(0);
        RouteDisplay.setText("");
        ChkInput.setSelected(true);
        isForm();
        KFACode.requestFocus();
    }

    private void getData() {
        if (tbJnsPerawatan.getSelectedRow() != -1) {
            KFACode.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 0).toString());
            KFASystem.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 1).toString());
            KodeBarang.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 2).toString());
            NamaBarang.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 3).toString());
            KFADisplay.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 4).toString());
            FormCode.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 5).toString());
            FormSystem.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 6).toString());
            FormDisplay.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 7).toString());
            NumoratorCode.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 8).toString());
            NemeratorSystem.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 9).toString());
            DenominatorCode.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 10).toString());
            DenominatorSystem.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 11).toString());
            RouteCode.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 12).toString());
            RouteSystem.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 13).toString());
            RouteDisplay.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 14).toString());
        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getsatu_sehat_mapping_obat());
        BtnHapus.setEnabled(akses.getsatu_sehat_mapping_obat());
        BtnEdit.setEnabled(akses.getsatu_sehat_mapping_obat());
        BtnPrint.setEnabled(akses.getsatu_sehat_mapping_obat());
    }

    public JTable getTable() {
        return tbJnsPerawatan;
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 280));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    private void tampilListKfa(String search) {
        page.setSelectedIndex(0);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            int hasil, total, perpage;
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(link + "/products/all?size=1&product_type=farmasi&page=1&keyword=" + search, HttpMethod.GET, requestEntity, String.class).getBody());
            j = 1;
            total = Integer.parseInt(root.path("total").asText());
            perpage = Integer.parseInt(page.getSelectedItem().toString());
            hasil = total / perpage;
            hal.removeAllItems();
            for (j = 1; j <= hasil; j++) {
                hal.addItem(j);
            }
            Valid.tabelKosong(tabModeListKFA);
            i = 1;
            for (JsonNode list : root.path("items").path("data")) {
                responsename = list.path("active_ingredients");
                for (JsonNode responsename : responsename) {
                    tabModeListKFA.addRow(new String[]{
                        list.path("name").asText(), list.path("kfa_code").asText(), list.path("product_template").path("name").asText(), list.path("product_template").path("kfa_code").asText(), responsename.path("zat_aktif").asText(), responsename.path("kfa_code").asText(), responsename.path("kekuatan_zat_aktif").asText(), list.path("dosage_form").path("code").asText(), list.path("dosage_form").path("name").asText()
                    });
                }

                i++;
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCountTotal.setText("" + tabModeListKFA.getRowCount());
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void tampilListKfaSearchPage(String search) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            int hasil, total, perpage, halaman;
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(link + "/products/all?size=" + page.getSelectedItem().toString() + "&product_type=farmasi&page=" + hal.getSelectedItem().toString() + "&keyword=" + search, HttpMethod.GET, requestEntity, String.class).getBody());
            response = root.path("entry");
            j = 1;
            total = Integer.parseInt(root.path("total").asText());
            perpage = Integer.parseInt(page.getSelectedItem().toString());
            halaman = (Integer.parseInt(hal.getSelectedItem().toString()) - 1) * perpage;
            hasil = total / perpage;
            LCountTotal.setText(root.path("total").asText());

            Valid.tabelKosong(tabModeListKFA);
            if (hal.getSelectedIndex() != 0) {

                i = halaman + 1;
            } else {
                i = 1;
            }
            for (JsonNode list : root.path("items").path("data")) {
                responsename = list.path("active_ingredients");
                for (JsonNode responsename : responsename) {
                    tabModeListKFA.addRow(new String[]{
                        list.path("name").asText(), list.path("kfa_code").asText(), list.path("product_template").path("name").asText(), list.path("product_template").path("kfa_code").asText(), responsename.path("zat_aktif").asText(), responsename.path("kfa_code").asText(), responsename.path("kekuatan_zat_aktif").asText(), list.path("dosage_form").path("code").asText(), list.path("dosage_form").path("name").asText()
                    });
                }

                i++;
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeListKFA.getRowCount());
        this.setCursor(Cursor.getDefaultCursor());
    }
     private void getDetailObat(String kdObat) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(link + "/products?identifier=kfa&code=" + kdObat, HttpMethod.GET, requestEntity, String.class).getBody());
            RouteCode.setText(root.path("result").path("rute_pemberian").path("code").asText());
            RouteDisplay.setText(root.path("result").path("rute_pemberian").path("name").asText());
            NumoratorCode.setText(root.path("result").path("ucum").path("cs_code").asText());
            DenominatorCode.setText(root.path("result").path("uom").path("name").asText());
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
      private void tampilObat() {
        Valid.tabelKosong(tabModeMasterObat);
        String jenisObat = CmbJenis.getSelectedItem().toString();
        if (jenisObat.equals("Semua")) {
            jenisObat = "";
        } else if (jenisObat.equals("Alat kesehatan BMHP")) {
            jenisObat = " and jenis.nama='Alat kesehatan BMHP' ";
        } else {
            jenisObat = " and jenis.nama!='Alat kesehatan BMHP' ";
        }
        try {
            ps = koneksi.prepareStatement("SELECT databarang.kode_brng,databarang.nama_brng,databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,databarang.h_beli,databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,databarang.karyawan,databarang.stokminimal,databarang.kdjns,jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri,databarang.kode_kategori,kategori_barang.nama AS kategori,databarang.kode_golongan,golongan_barang.nama AS golongan "
                    + "FROM databarang INNER JOIN kodesatuan INNER JOIN jenis INNER JOIN industrifarmasi INNER JOIN golongan_barang INNER JOIN kategori_barang ON databarang.kode_sat=kodesatuan.kode_sat AND databarang.kdjns=jenis.kdjns AND databarang.kode_golongan=golongan_barang.kode AND databarang.kode_kategori=kategori_barang.kode AND databarang.kode_industri=industrifarmasi.kode_industri "
                    + " WHERE databarang.STATUS='1' " + jenisObat + " "
                    + "AND NOT EXISTS (SELECT kode_brng FROM satu_sehat_mapping_obat WHERE databarang.kode_brng=satu_sehat_mapping_obat.kode_brng) AND (databarang.kode_brng LIKE ? OR databarang.nama_brng LIKE ? OR kategori_barang.nama LIKE ?) ORDER BY databarang.nama_brng");
            try {
                ps.setString(1, "%" + TCariMasterObat.getText().trim() + "%");
                ps.setString(2, "%" + TCariMasterObat.getText().trim() + "%");
                ps.setString(3, "%" + TCariMasterObat.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeMasterObat.addRow(new Object[]{
                        rs.getString("kode_brng"),
                        rs.getString("nama_brng"),
                        rs.getString("kode_sat"),
                        rs.getString("satuan"),
                        rs.getString("letak_barang"),
                        rs.getDouble("h_beli"),
                        rs.getDouble("ralan"),
                        rs.getDouble("kelas1"),
                        rs.getDouble("kelas2"),
                        rs.getDouble("kelas3"),
                        rs.getDouble("utama"),
                        rs.getDouble("vip"),
                        rs.getDouble("vvip"),
                        rs.getDouble("beliluar"),
                        rs.getDouble("jualbebas"),
                        rs.getDouble("karyawan"),
                        rs.getString("stokminimal"),
                        rs.getString("kdjns"),
                        rs.getString("nama"),
                        rs.getDouble("kapasitas"),
                        rs.getString("expire"),
                        rs.getString("kode_industri"),
                        rs.getString("nama_industri"),
                        rs.getString("kode_kategori"),
                        rs.getString("kategori"),
                        rs.getString("kode_golongan"),
                        rs.getString("golongan")
                    });
                }
                LCountTotalMaster.setText("" + tabModeMasterObat.getRowCount());
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
      private void tampilDetailObat(String kdObat) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(link + "/products?identifier=kfa&code=" + kdObat, HttpMethod.GET, requestEntity, String.class).getBody());
            KFACode1.setText(root.path("search_code").asText());
            KFADisplay1.setText(root.path("result").path("name").asText());
            RouteCode1.setText(root.path("result").path("rute_pemberian").path("code").asText());
            RouteDisplay1.setText(root.path("result").path("rute_pemberian").path("name").asText());
            desc.setText(root.path("result").path("description").asText().replaceAll("<p>|</p>|<br>", "\n"));
            sideeffect.setText(root.path("result").path("side_effect").asText().replaceAll("<p>|</p>|<br>", "\n"));
            warning.setText(root.path("result").path("warning").asText().replaceAll("<p>|</p>|<br>", "\n"));
            ucum_code.setText(root.path("result").path("ucum").path("cs_code").asText());
            ucum_name.setText(root.path("result").path("ucum").path("name").asText());
            uom.setText(root.path("result").path("uom").path("name").asText());
            formcode.setText(root.path("result").path("dosage_form").path("code").asText());
            formname.setText(root.path("result").path("dosage_form").path("name").asText());
            controlcode.setText(root.path("result").path("controlled_drug").path("code").asText());
            controlname.setText(root.path("result").path("controlled_drug").path("name").asText());
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
}
